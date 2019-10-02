package com.reccakun.clientapp.Views;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.reccakun.clientapp.Controllers.DBConnect;
import com.reccakun.clientapp.Controllers.CategoriesAdapter;
import com.reccakun.clientapp.Controllers.ads;
import com.reccakun.clientapp.Models.Category;
import com.reccakun.clientapp.Models.CostumMenu;
import com.reccakun.clientapp.R;

import java.util.List;

public class MainActivity extends AppCompatActivity {
     CategoriesAdapter mAdapter;
    Context mContext= MainActivity.this;
    List<Category> listsCat;
    DBConnect dbDreams ;

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

             this.getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
             getSupportActionBar().setDisplayShowCustomEnabled(true);
             getSupportActionBar().setCustomView(R.layout.custom_action_bar);

             getSupportActionBar().setElevation(5);
             View view = getSupportActionBar().getCustomView();
             ImageView imgBack=view.findViewById(R.id.backitem);
             this.getSupportActionBar().setBackgroundDrawable(new ColorDrawable( Color.parseColor("#3F51B5")));

             imgBack.setVisibility(View.GONE);
            dbDreams=new DBConnect(getApplicationContext());
             ImageView imgFav=view.findViewById(R.id.favitem);
             imgFav.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {

                     Intent intent=new Intent(MainActivity.this,FavoriteActivity.class);
                     startActivity(intent);
                 }
             });
             ImageView home=findViewById(R.id.homeBtn);
             home.setVisibility(View.GONE);
             listsCat =dbDreams.getAllCategory();
             mAdapter=new CategoriesAdapter(mContext,R.layout.category_item, listsCat);
             ListView ViewCat=findViewById(R.id.listCat);
             ViewCat.setAdapter(mAdapter);
             ViewCat.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                 @Override
                 public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                     checkClickedItem(position);

                 }
             });
            ImageView popUp=findViewById(R.id.triple_dots);
             CostumMenu costumMenu=new CostumMenu(mContext,popUp);
             costumMenu.PopUpMenu();
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
}
