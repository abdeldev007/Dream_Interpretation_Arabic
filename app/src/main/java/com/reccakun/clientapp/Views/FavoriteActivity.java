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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.reccakun.clientapp.Controllers.DBConnect;
import com.reccakun.clientapp.Models.CostumMenu;
import com.reccakun.clientapp.Models.Dream;
import com.reccakun.clientapp.Controllers.FavDreamAdapter;
import com.reccakun.clientapp.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class FavoriteActivity extends AppCompatActivity {
    FavDreamAdapter mAdapter;
    Context mContext= FavoriteActivity.this;
    List<Dream> listDreams;
    List<Dream> copyList;
    DBConnect dbDreams ;
    int id;
    ImageView home;
    ImageView imgBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        try{


            this.getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
            getSupportActionBar().setDisplayShowCustomEnabled(true);
            getSupportActionBar().setCustomView(R.layout.custom_action_bar);
            getSupportActionBar().setElevation(5);
            this.getSupportActionBar().setBackgroundDrawable(new ColorDrawable( Color.parseColor("#3F51B5")));
             View view = getSupportActionBar().getCustomView();
              imgBack=view.findViewById(R.id.backitem);
            imgBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
            ImageView imgFav=view.findViewById(R.id.favitem);
            imgFav.setVisibility(View.GONE);
            listDreams=new ArrayList<>();
            copyList =  new ArrayList<>();
            ImageView popUp=findViewById(R.id.triple_dots);
            CostumMenu costumMenu=new CostumMenu(mContext,popUp);
            costumMenu.PopUpMenu();
            dbDreams=new DBConnect(getApplicationContext());
            listDreams=dbDreams.getFavDreams();
            copyList=dbDreams.getFavDreams();
            mAdapter=new FavDreamAdapter(mContext,R.layout.courses_item, listDreams);
            ListView listViewCourses=findViewById(R.id.listview_fav_dreams);
            listViewCourses.setAdapter(mAdapter);
              home=findViewById(R.id.homeBtn);
            home.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent i=new Intent(FavoriteActivity.this,MainActivity.class);
                    startActivity(i);
                }
            });
            //Toast.makeText(mContext, ""+dbDreams.isFavorite(333), Toast.LENGTH_SHORT).show();
            listViewCourses.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long idL) {

                    Intent i =new Intent(FavoriteActivity.this,ContentActivity.class);
                    int idItem=  position;
                    i.putExtra("idDream", listDreams.get(position).getDream_ID());
                    i.putExtra("idCat",   id);
                    i.putExtra("idItem",   idItem);
                    i.putExtra("isFav",true);
                    startActivity(i);
                }
            });

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


                    imgBack.setVisibility(View.GONE);
                    home.setVisibility(View.GONE);


            }
        });
        simpleSearchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                 imgBack.setVisibility(View.VISIBLE);
                home.setVisibility(View.VISIBLE);

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
                imgBack.setVisibility(View.GONE);
                home.setVisibility(View.GONE);
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
