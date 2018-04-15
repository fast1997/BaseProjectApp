package com.sp18.ssu370.WasteYourTime.ui.activities;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sp18.ssu370.WasteYourTime.model.ImgurImageList;
import com.sp18.ssu370.WasteYourTime.network.ImgurImageAsyncTask;
import com.sp18.ssu370.WasteYourTime.ui.view.RecyclerViewAdapter;
import com.sp18.ssu370.baseprojectapp.R;
import com.squareup.picasso.Picasso;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchEditText = findViewById(R.id.search_edit_text);
        searchButton = findViewById(R.id.my_search_button);
        gallery = findViewById(R.id.recycler_view_id);
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

    public void initBottomNavigation(){
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home_nav:
                        Toast.makeText(thisContext,"Home",Toast.LENGTH_SHORT).show();
                        type = "home";
                        getHomeGalleries(type,section,sort,page,window);
                        break;
                    case R.id.favorite_nav:
                        Toast.makeText(thisContext,"Favorite",Toast.LENGTH_SHORT).show();
                        break;
                }
                return true;
            }
        });
    }

    public void setUpSortMenu(){
        sortMenu.addDrawerListener(menuToggle);
        menuToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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
            Toast.makeText(this,"Show GIF Only",Toast.LENGTH_SHORT).show();

        }
        if(id == R.id.img_only_id)
        {
            Toast.makeText(this,"Show IMG Only",Toast.LENGTH_SHORT).show();
        }
        return false;
    }
}
