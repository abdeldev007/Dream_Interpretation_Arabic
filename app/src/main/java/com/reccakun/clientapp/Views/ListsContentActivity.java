package com.reccakun.clientapp.Views;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.reccakun.clientapp.Controllers.DBConnect;
import com.reccakun.clientapp.Models.CostumMenu;
import com.reccakun.clientapp.Models.Dream;
import com.reccakun.clientapp.Controllers.DreamAdapter;
import com.reccakun.clientapp.R;

import java.util.List;
import java.util.Locale;

public class ListsContentActivity extends AppCompatActivity {
DreamAdapter mAdapter;
Context mContext= ListsContentActivity.this;
List<Dream> listDreams;
    DBConnect dbDreams ;
    private EditText search;
    List<Dream> copyList;
    int id;
    ImageView home;
    private ImageView back;
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dreams);
        try{
            MobileAds.initialize(this, new OnInitializationCompleteListener() {
                @Override
                public void onInitializationComplete(InitializationStatus initializationStatus) {
                }
            });

            mAdView = findViewById(R.id.adView2);
            AdRequest adRequest = new AdRequest.Builder().build();
            mAdView.loadAd(adRequest);
            this.getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
            getSupportActionBar().setDisplayShowCustomEnabled(true);
            getSupportActionBar().setCustomView(R.layout.custom_action_bar);

            getSupportActionBar().setElevation(5);

            this.getSupportActionBar().setBackgroundDrawable(new ColorDrawable( Color.parseColor("#3F51B5")));

            View view = getSupportActionBar().getCustomView();
            ImageView imgBack=view.findViewById(R.id.backitem);
             imgBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    onBackPressed();
                }
            });

              home=findViewById(R.id.homeBtn);
            home.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent i=new Intent(ListsContentActivity.this,MainActivity.class);
                    startActivity(i);
                }
            });

            back=findViewById(R.id.backitem);
            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent i=new Intent(ListsContentActivity.this,MainActivity.class);
                    startActivity(i);
                }
            });
            ImageView imgFav=view.findViewById(R.id.favitem);
            imgFav.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                Intent intent=new Intent(ListsContentActivity.this,FavoriteActivity.class);
                startActivity(intent);
                }
        });
            Intent intent = getIntent();
            id = (int)  intent.getSerializableExtra("id");

            dbDreams=new DBConnect(getApplicationContext());
            listDreams=dbDreams.getDreamsByCat(id);
            copyList =  dbDreams.getDreamsByCat(id);
            mAdapter=new DreamAdapter(mContext,R.layout.dreams_item, listDreams);
            ListView listViewCourses=findViewById(R.id.listview_courses);
            listViewCourses.setAdapter(mAdapter);
            listViewCourses.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long idL) {

                  Intent i =new Intent(ListsContentActivity.this,ContentActivity.class);
                  int idItem=  position;
                  i.putExtra("idDream", listDreams.get(position).getDream_ID());
                  i.putExtra("idCat",   id);
                  i.putExtra("idItem",   idItem);
                  i.putExtra("isFav",   false);

                    startActivity(i);
          }
          });
            ImageView popUp=findViewById(R.id.triple_dots);
            CostumMenu costumMenu=new CostumMenu(mContext,popUp);
            costumMenu.PopUpMenu();
            searchFunc();
        }catch (Exception e){
            Toast.makeText(mContext,"main " +e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }


    private void searchFunc(){
        SearchView simpleSearchView = (SearchView) findViewById(R.id.searchBar); // inititate a search view
        CharSequence query = simpleSearchView.getQuery(); // get the query string currently in the text field

        simpleSearchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                    back.setVisibility(View.GONE);
                    home.setVisibility(View.GONE);



            }
        });
        simpleSearchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                home.setVisibility(View.VISIBLE);
                back.setVisibility(View.VISIBLE);

                return false;
            }
        });
      // perform set on query text listener event
        simpleSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
        // do something on text submit
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
          // do something when text changes
                if (newText.length() != 0) {
                    if (listDreams.size() != 0) {
                        listDreams.clear();
                        for (Dream dream : copyList) {
                            if (dream.getTitle().toLowerCase(Locale.getDefault()).contains(newText)) {
                                listDreams.add(dream);
                            }
                        }
                    }
                } else {
                    listDreams.clear();
                    listDreams.addAll(copyList);
                }

                mAdapter.notifyDataSetChanged();
                return false;
            }
        });

    }

}
