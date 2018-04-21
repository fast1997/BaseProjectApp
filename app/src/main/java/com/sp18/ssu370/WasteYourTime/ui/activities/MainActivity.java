package com.sp18.ssu370.WasteYourTime.ui.activities;

import android.Manifest;
import android.app.VoiceInteractor;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sp18.ssu370.WasteYourTime.model.ImgurImage;
import com.sp18.ssu370.WasteYourTime.model.ImgurImageList;
import com.sp18.ssu370.WasteYourTime.model.Memes;
import com.sp18.ssu370.WasteYourTime.network.ImgurImageAsyncTask;
import com.sp18.ssu370.WasteYourTime.ui.util.DatabaseHelper;
import com.sp18.ssu370.WasteYourTime.ui.view.RecyclerViewAdapter;
import com.sp18.ssu370.baseprojectapp.R;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private EditText searchEditText;
    private Button searchButton;
    private ImgurImageAsyncTask task;
    private RecyclerView gallery;
    private RecyclerViewAdapter myAdapter;

    private Context thisContext = this;

    //sort menu
    private DrawerLayout sortMenu;
    private ActionBarDrawerToggle menuToggle;

    //bottom nav
    BottomNavigationView bottomNavigationView;

    //for sorting
    private ImgurImageList currentImgurImageList;
    private String type;
    private String section;
    private String sort;
    private String page;
    private int pageNum;
    private String window;
    private String currentSearch;

    private DatabaseHelper databaseHelper = new DatabaseHelper(this);
    private static int favIdx = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //keyboard only show when edite text is clicked
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        //saving permission
        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);

        searchEditText = findViewById(R.id.search_edit_text);
        searchButton = findViewById(R.id.my_search_button);

        gallery = findViewById(R.id.recycler_view_id);
        gallery.setHasFixedSize(true);
        gallery.setItemViewCacheSize(20);
        gallery.setDrawingCacheEnabled(true);
        gallery.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);

        sortMenu = findViewById(R.id.sort_menu_id);
        menuToggle = new ActionBarDrawerToggle(this, sortMenu, R.string.open, R.string.close);



        type = "home";
        section = "user/";
        sort = "time/";
        //Random random = new Random();
        //pageNum = random.nextInt(10) + 5;
        pageNum = 0;
        page = "";
        page += pageNum + "/";
        window = "all/";
        setUpSortMenu();
        initBottomNavigation();
        getHomeGalleries(type,section,sort,page,window);
        currentSearch = "not searched";
        searchClicked(type,section,sort,page,window, currentSearch);
    }

    private static String getGalleryPath() {
        return Environment.getExternalStorageDirectory() + "/" + Environment.DIRECTORY_PICTURES + "/";

    }
    public void initBottomNavigation(){
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){

                    case R.id.home_nav:
                        Toast.makeText(thisContext,"Home",Toast.LENGTH_SHORT).show();
                        pageNum = 0;
                        type = "home";
                        page = "";
                        page += pageNum + "/";
                        getHomeGalleries(type,section,sort,page,window);
                        break;

                    case R.id.favorite_nav:
                        Toast.makeText(thisContext,"Favorite",Toast.LENGTH_SHORT).show();
                        populateFavorites();
                        setUpRecyclerView();
                        break;

                    case R.id.next_nav:

                        if(pageNum >= 0) {
                            pageNum = pageNum + 1;
                            page = "";
                            page += pageNum + "/";
                        }
                        if(type.equals("home")){
                            getHomeGalleries(type,section,sort,page,window);
                        }
                        else{
                            Toast.makeText(thisContext,"Next Page",Toast.LENGTH_SHORT).show();
                            getSearch(type,section,sort,page,window, currentSearch);
                        }
                        Toast.makeText(thisContext,"Next Page",Toast.LENGTH_SHORT).show();
                        break;

                    case R.id.prev_nav:

                        if( pageNum > 0) {
                            pageNum = pageNum - 1;
                            page = "";
                            page += pageNum + "/";
                        }
                        else {
                            Toast.makeText(thisContext,"Already at First",Toast.LENGTH_SHORT).show();
                            break;
                        }
                        if(type.equals("home")){
                            getHomeGalleries(type,section,sort,page,window);
                        }
                        else{
                            getSearch(type,section,sort,page,window, currentSearch);
                        }
                        Toast.makeText(thisContext,"Previous Page",Toast.LENGTH_SHORT).show();
                        break;

                    case R.id.upload_nav:
                        Intent upload = new Intent(thisContext, UploadActivity.class);
                        thisContext.startActivity(upload);
                        break;
                }
                return true;
            }
        });
    }

    public void populateFavorites(){
        Cursor data = databaseHelper.getData();
        ArrayList<Memes> favMemes = new ArrayList<>();
        ArrayList<ImgurImage> favImg;


        while( data.moveToNext() ){
            String favoriteTitle = "Favorite " + favIdx;

            favImg = new ArrayList<>();
            ImgurImage temp;
            if( data.getInt(2) == 1){
                temp = new ImgurImage(data.getString(1), true);
            }
            else {
                temp = new ImgurImage(data.getString(1), false);
            }
            favImg.add(temp);

            Memes memes = new Memes(favImg, favoriteTitle);
            memes.setFavorite(true);
            favMemes.add(memes);
            favIdx++;
        }

        currentImgurImageList = new ImgurImageList(favMemes);

    }

    public void setUpSortMenu(){
        sortMenu.addDrawerListener(menuToggle);
        menuToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_sort);
        NavigationView navigationView = findViewById(R.id.menu_nav_id);
        navigationView.setNavigationItemSelectedListener(this);
    }

    public void setUpRecyclerView(){
        myAdapter = new RecyclerViewAdapter(thisContext,currentImgurImageList);
        gallery.setLayoutManager(new GridLayoutManager(thisContext, 2));
        gallery.setAdapter(myAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(menuToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void getHomeGalleries(String... strings){

        type = strings[0];
        section = strings[1];
        sort = strings[2];
        page = strings[3];
        window = strings[4];

        task = new ImgurImageAsyncTask();
        task.setListener(new ImgurImageAsyncTask.OnImgurImageFetchResponse() {
            @Override
            public void onCallback(ImgurImageList imgurImageList) {

                currentImgurImageList = imgurImageList;
                setUpRecyclerView();
            }

        });

        task.execute(type, section, sort, page, window);
    }

    public void getSearch(String... strings){
        type = strings[0];
        section = strings[1];
        sort = strings[2];
        page = strings[3];
        window = strings[4];
        task = new ImgurImageAsyncTask();
        task.setListener(new ImgurImageAsyncTask.OnImgurImageFetchResponse() {
            @Override
            public void onCallback(ImgurImageList imgurImageList) {

                currentImgurImageList = imgurImageList;
                setUpRecyclerView();
            }

        });

        if( strings[5].equals("not searched"))
            currentSearch = searchEditText.getText().toString();
        else
            currentSearch = strings[5];
        task.execute(type,section, sort, page, window, currentSearch);
    }

    public void searchClicked(final String... strings){

        type = strings[0];
        section = strings[1];
        sort = strings[2];
        page = strings[3];
        window = strings[4];
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = "search";
                page = "0";
                currentSearch = searchEditText.getText().toString();
                getSearch(type,section, sort, page, window, currentSearch);
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.viral_id)
        {
            Toast.makeText(this,"Sort by Viral",Toast.LENGTH_SHORT).show();
            for(int i = 0; i < currentImgurImageList.getData().size(); i++){
                currentImgurImageList.getData().get(i).setNewest(false);
                currentImgurImageList.getData().get(i).setOldest(false);
                currentImgurImageList.getData().get(i).setViral(true);
            }

            Collections.sort(currentImgurImageList.getData());
            setUpRecyclerView();
        }
        if(id == R.id.newest_id)
        {
            Toast.makeText(this,"Sort by Newest",Toast.LENGTH_SHORT).show();
            for(int i = 0; i < currentImgurImageList.getData().size(); i++){
                currentImgurImageList.getData().get(i).setNewest(true);
                currentImgurImageList.getData().get(i).setOldest(false);
                currentImgurImageList.getData().get(i).setViral(false);
            }

            Collections.sort(currentImgurImageList.getData());
            setUpRecyclerView();
        }
        if(id == R.id.oldest_id)
        {
            Toast.makeText(this,"Sort by Oldest",Toast.LENGTH_SHORT).show();
            for(int i = 0; i < currentImgurImageList.getData().size(); i++){
                currentImgurImageList.getData().get(i).setNewest(false);
                currentImgurImageList.getData().get(i).setOldest(true);
                currentImgurImageList.getData().get(i).setViral(false);
            }

            Collections.sort(currentImgurImageList.getData());
            setUpRecyclerView();
        }
        if(id == R.id.gif_only_id)
        {
            ImgurImageList all = new ImgurImageList(currentImgurImageList);
            Toast.makeText(this,"Show GIF Only",Toast.LENGTH_SHORT).show();
            ArrayList<Memes> memesContent = new ArrayList<>();
            for(int i = 0; i < currentImgurImageList.getData().size(); i++){
                if( currentImgurImageList.getData().get(i).getImages().get(0).isAnimated() ){
                    memesContent.add(currentImgurImageList.getData().get(i));
                }
            }
            ImgurImageList gifOnlyContent = new ImgurImageList(memesContent);
            currentImgurImageList = gifOnlyContent;
            setUpRecyclerView();
            currentImgurImageList = all;
        }
        if(id == R.id.img_only_id)
        {
            ImgurImageList all = new ImgurImageList(currentImgurImageList);
            Toast.makeText(this,"Show IMG Only",Toast.LENGTH_SHORT).show();
            ArrayList<Memes> picContent = new ArrayList<>();
            for(int i = 0; i < currentImgurImageList.getData().size(); i++){
                if( !currentImgurImageList.getData().get(i).getImages().get(0).isAnimated() ){
                    picContent.add(currentImgurImageList.getData().get(i));
                }
            }
            ImgurImageList picOnlyContent = new ImgurImageList(picContent);
            currentImgurImageList = picOnlyContent;
            setUpRecyclerView();
            currentImgurImageList = all;
        }
        return false;
    }
}
