package com.sp18.ssu370.WasteYourTime.ui.activities;

import android.content.Context;
import android.support.annotation.NonNull;
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

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private EditText searchEditText;
    private Button searchButton;
    private ImgurImageAsyncTask task;
    private RecyclerView gallery;
    private RecyclerViewAdapter myAdapter;

    private Context thisContext = this;

    private DrawerLayout sortMenu;
    private ActionBarDrawerToggle menuToggle;

    //for sorting
    private ImgurImageList imgurImageList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchEditText = findViewById(R.id.search_edit_text);
        searchButton = findViewById(R.id.my_search_button);
        gallery = findViewById(R.id.recycler_view_id);
        sortMenu = findViewById(R.id.sort_menu_id);
        menuToggle = new ActionBarDrawerToggle(this, sortMenu, R.string.open, R.string.close);


        setUpSortMenu();
        getHomeGalleries();
        searchClicked();
    }

    public void setUpSortMenu(){
        sortMenu.addDrawerListener(menuToggle);
        menuToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        NavigationView navigationView = findViewById(R.id.menu_nav_id);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(menuToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void getHomeGalleries(){
        task = new ImgurImageAsyncTask();
        task.setListener(new ImgurImageAsyncTask.OnImgurImageFetchResponse() {
            @Override
            public void onCallback(ImgurImageList imgurImageList) {

                myAdapter = new RecyclerViewAdapter(thisContext,imgurImageList);

                gallery.setLayoutManager(new GridLayoutManager(thisContext, 2));
                gallery.setAdapter(myAdapter);
            }

        });

        String home = "home";
        task.execute(home);
    }

    public void searchClicked(){
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                task = new ImgurImageAsyncTask();
                task.setListener(new ImgurImageAsyncTask.OnImgurImageFetchResponse() {
                    @Override
                    public void onCallback(ImgurImageList imgurImageList) {

                        myAdapter = new RecyclerViewAdapter(thisContext,imgurImageList);

                        gallery.setLayoutManager(new GridLayoutManager(thisContext, 2));
                        gallery.setAdapter(myAdapter);


                    }

                });

                String searchTerms = searchEditText.getText().toString();
                task.execute(searchTerms);
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.viral_id)
        {
            Toast.makeText(this,"Sort by Viral",Toast.LENGTH_SHORT).show();
        }
        if(id == R.id.newest_id)
        {
            Toast.makeText(this,"Sort by Newest",Toast.LENGTH_SHORT).show();
        }
        if(id == R.id.oldest_id)
        {
            Toast.makeText(this,"Sort by Oldest",Toast.LENGTH_SHORT).show();
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
