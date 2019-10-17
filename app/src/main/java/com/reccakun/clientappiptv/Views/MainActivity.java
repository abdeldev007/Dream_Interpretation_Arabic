package com.reccakun.clientappiptv.Views;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.reccakun.clientappiptv.Controllers.DBConnect;
import com.reccakun.clientappiptv.Controllers.CategoriesAdapter;
import com.reccakun.clientappiptv.Models.Category;
import com.reccakun.clientappiptv.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
     CategoriesAdapter mAdapter;
    Context mContext= MainActivity.this;
    List<Category> listsCat=new ArrayList<>();
    DBConnect dbDreams ;
    List<Category> copyList;
    private AdView mAdView;

    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        try{
            dbDreams=new DBConnect(mContext);

            copyList =  new ArrayList<>();

             listsCat =dbDreams.getAllCategory();
            copyList =dbDreams.getAllCategory();
             mAdapter=new CategoriesAdapter(mContext,R.layout.category_item, listsCat);
             ListView ViewCat=findViewById(R.id.listCat);
             ViewCat.setAdapter(mAdapter);
             ViewCat.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                 @Override
                 public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                     checkClickedItem(position);

                 }
             });

             ViewCat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                 @Override
                 public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                     listsCat.get(position).setSelected(true);
                     mAdapter.notifyDataSetChanged();
                 }

                 @Override
                 public void onNothingSelected(AdapterView<?> parent) {

                 }
             });

        }catch (Exception e){
            Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }


    private void checkClickedItem(int position) {
        try{


            int idCat=  listsCat.get(position).getId();

            //calculate distance

            Intent intent = new Intent(this, ListsContentActivity.class);
            intent.putExtra("id", idCat);

             startActivity(intent);
            //   overridePendingTransition(R.anim.anim_show,R.anim.sd_scale_fade_and_translate_in);

        }catch (Exception e){
            Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    public void onBackPressed () {
// alertdialog for exit the app
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

// set the title of the Alert Dialog
        alertDialogBuilder.setTitle("الخروج من التطبيق");

// set dialog message
        alertDialogBuilder
                .setMessage("هل أنت مأكد!!")
                .setCancelable(false)
                .setPositiveButton("نعم",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int id) {
                        // what to do if YES is tapped
                        finishAffinity();
                        System.exit(0);
                    }
                    } )
                    .setNegativeButton("لا",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int id) {
                        // code to do on NO tapped
                        dialog.cancel();
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();

        alertDialog.show();
    }

    SearchView mSearchView;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        this.getSupportActionBar().setElevation(11);
        menu.getItem(2).setVisible(false);
        mSearchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
     menu.getItem(0).setVisible(false);

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_home: {

                break;
            }

            case R.id.action_search: {

                break;
            }
            case R.id.action_fave: {
                Intent i = new Intent(MainActivity.this, FavoriteActivity.class);

                startActivity(i);
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }



}
