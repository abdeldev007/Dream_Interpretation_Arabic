package com.reccakun.clientapp.Views;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.reccakun.clientapp.Controllers.DBConnect;
import com.reccakun.clientapp.Controllers.CategoriesAdapter;
import com.reccakun.clientapp.Models.Category;
import com.reccakun.clientapp.Models.CostumMenu;
import com.reccakun.clientapp.R;

import java.util.List;

public class MainActivity extends AppCompatActivity {
     CategoriesAdapter mAdapter;
    Context mContext= MainActivity.this;
    List<Category> listsCat;
    DBConnect dbDreams ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

    }
}
