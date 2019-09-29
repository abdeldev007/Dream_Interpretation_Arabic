package com.reccakun.clientapp.Views;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
 import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.reccakun.clientapp.Controllers.DBConnect;
import com.reccakun.clientapp.Models.CostumMenu;
import com.reccakun.clientapp.Models.Dream;
import com.reccakun.clientapp.Controllers.ViewPagerAdapter;
import com.reccakun.clientapp.R;


import android.annotation.TargetApi;
        import android.os.Build;
 import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import java.util.List;

public class ContentActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private Context mContext=ContentActivity.this;
    List<Dream> ld;
    List<Dream> listDreams;
    DBConnect dbDreams ;
    int idC,idD,idItem;
    boolean isFavActivity;
     ImageView imgBack;
    @TargetApi(Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);
        dbDreams=new DBConnect(getApplicationContext());
try{
      this.getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
    getSupportActionBar().setDisplayShowCustomEnabled(true);
    getSupportActionBar().setCustomView(R.layout.custom_action_bar);

    getSupportActionBar().setElevation(5);
    View view = getSupportActionBar().getCustomView();
      imgBack=view.findViewById(R.id.backitem);

    this.getSupportActionBar().setBackgroundDrawable(new ColorDrawable( Color.parseColor("#3F51B5")));
    ImageView imgFav=view.findViewById(R.id.favitem);
    imgFav.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            Intent intent=new Intent(ContentActivity.this,FavoriteActivity.class);
            startActivity(intent);
        }
    });
    ImageView home=findViewById(R.id.homeBtn);
    home.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            Intent i=new Intent(ContentActivity.this,MainActivity.class);
            startActivity(i);
        }
    });
        dbDreams=new DBConnect(getApplicationContext());
          Intent intent = getIntent();
          idC = (int)  intent.getSerializableExtra("idCat");
          idD  = (int)   intent.getSerializableExtra("idDream");
          idItem  = (int)   intent.getSerializableExtra("idItem");
    isFavActivity  = (boolean)   intent.getSerializableExtra("isFav");
        if (isFavActivity){
            listDreams=dbDreams.getFavDreams();
            viewPager = (ViewPager) findViewById(R.id.pager);

            ViewPagerAdapter adapter = new ViewPagerAdapter(mContext,listDreams,idD,true,viewPager);
            viewPager.setAdapter(adapter);
            viewPager.setCurrentItem(idItem );
            imgBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    onBackPressed();

                }
            });

        }else {
            viewPager = (ViewPager) findViewById(R.id.pager);

          listDreams=dbDreams.getDreamsByCat(idC);
            ViewPagerAdapter adapter = new ViewPagerAdapter(mContext,listDreams,idD,false,viewPager);
            viewPager.setAdapter(adapter);
            viewPager.setCurrentItem(idItem );
            imgBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    onBackPressed();

                }
            });

        }
    ImageView popUp=findViewById(R.id.triple_dots);
    CostumMenu costumMenu=new CostumMenu(mContext,popUp);
    costumMenu.PopUpMenu();


        SearchView searchView=findViewById(R.id.searchBar);
        searchView.setVisibility(View.GONE);
     }catch (Exception e){
         Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_SHORT).show();
    }
    }




}