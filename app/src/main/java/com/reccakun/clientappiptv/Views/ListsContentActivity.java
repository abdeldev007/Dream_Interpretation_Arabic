package com.reccakun.clientappiptv.Views;

import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.Context;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
import com.reccakun.clientappiptv.Controllers.DBConnect;
import com.reccakun.clientappiptv.Models.Dream;
import com.reccakun.clientappiptv.Controllers.DreamAdapter;
import com.reccakun.clientappiptv.R;

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
    private SearchView mSearchView;
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






               //     Intent i=new Intent(ListsContentActivity.this,MainActivity.class);
                 //   startActivity(i);

         //   back=findViewById(R.id.backitem);


              //      Intent i=new Intent(ListsContentActivity.this,MainActivity.class);
                //    startActivity(i);

           // ImageView imgFav=view.findViewById(R.id.favitem);

            //    Intent intent=new Intent(ListsContentActivity.this,FavoriteActivity.class);
          //      startActivity(intent);

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

        }catch (Exception e){
            Toast.makeText(mContext,"main " +e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }
     private void setupSearchView() {

        mSearchView.setIconifiedByDefault(true);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        if (searchManager != null) {
            List<SearchableInfo> searchables = searchManager.getSearchablesInGlobalSearch();

            // Try to use the "applications" global search provider
            SearchableInfo info = searchManager.getSearchableInfo(getComponentName());
            for (SearchableInfo inf : searchables) {
                if (inf.getSuggestAuthority() != null
                        && inf.getSuggestAuthority().startsWith("applications")) {
                    info = inf;
                }
            }
            mSearchView.setSearchableInfo(info);

            mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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
                        // Toast.makeText(mContext," copy empty"+listsCat.get(0).getTvTitle(), Toast.LENGTH_SHORT).show();


                 }
            });
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        this.getSupportActionBar().setElevation(11);

        mSearchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        setupSearchView();

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_home: {
                Intent i = new Intent(ListsContentActivity.this, MainActivity.class);

                startActivity(i);
                break;
            }

            case R.id.action_search: {

                break;
            }
            case R.id.action_fave: {
                Intent i = new Intent(ListsContentActivity.this, FavoriteActivity.class);

                startActivity(i);

                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }


}
