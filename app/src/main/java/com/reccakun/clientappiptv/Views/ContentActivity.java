package com.reccakun.clientappiptv.Views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.reccakun.clientappiptv.Controllers.DBConnect;
import com.reccakun.clientappiptv.Models.Dream;
import com.reccakun.clientappiptv.Controllers.ViewPagerAdapter;
import com.reccakun.clientappiptv.R;


import android.annotation.TargetApi;
        import android.os.Build;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import java.util.List;

public class ContentActivity extends AppCompatActivity {
    private ViewPager viewPager;
      private Context mContext=ContentActivity.this;
     List<Dream> listDreams;
    DBConnect dbDreams ;
    int idC,idD,idItem;
    boolean isFavActivity;
     ImageView imgBack;
    private AdView mAdView;
    private InterstitialAd mInterstitialAd;

    @SuppressLint("WrongConstant")
    @TargetApi(Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);
        dbDreams=new DBConnect(getApplicationContext());
try{


    MobileAds.initialize(this, new OnInitializationCompleteListener() {
        @Override
        public void onInitializationComplete(InitializationStatus initializationStatus) {
        }
    });


    mAdView = findViewById(R.id.adView2);
    AdRequest adRequest = new AdRequest.Builder().build();
    mAdView.loadAd(adRequest);


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


        }else {
            viewPager = (ViewPager) findViewById(R.id.pager);

          listDreams=dbDreams.getDreamsByCat(idC);
            ViewPagerAdapter adapter = new ViewPagerAdapter(mContext,listDreams,idD,false,viewPager);
            viewPager.setAdapter(adapter);
            viewPager.setCurrentItem(idItem );


        }



     }catch (Exception e){
         Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_SHORT).show();
    }
    }
    private SearchView mSearchView;



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        this.getSupportActionBar().setElevation(11);

        menu.getItem(0).setVisible(false);

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            

            case R.id.action_search: {

                break;
            }
            case R.id.action_fave: {
                Intent i = new Intent(ContentActivity.this, FavoriteActivity.class);

                startActivity(i);

                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }



}