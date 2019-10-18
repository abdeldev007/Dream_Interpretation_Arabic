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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.reccakun.clientappiptv.Controllers.DBConnect;
import com.reccakun.clientappiptv.Models.Dream;
import com.reccakun.clientappiptv.Controllers.FavDreamAdapter;
import com.reccakun.clientappiptv.R;

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
    private SearchView mSearchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        try{





             listDreams=new ArrayList<>();
            copyList =  new ArrayList<>();
               dbDreams=new DBConnect(getApplicationContext());
            listDreams=dbDreams.getFavDreams();
            copyList=dbDreams.getFavDreams();
            mAdapter=new FavDreamAdapter(mContext,R.layout.dreams_item, listDreams);
            ListView listViewCourses=findViewById(R.id.listview_fav_dreams);
            listViewCourses.setAdapter(mAdapter);

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
        menu.getItem(1).setVisible(false);
        mSearchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        setupSearchView();

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

                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

}
