package com.reccakun.clientapp.Models;

import android.content.Context;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.reccakun.clientapp.R;

public class CostumMenu {
    private Context context;
    ImageView tripledots ;

    public CostumMenu(Context context ,ImageView  tripledots) {
        this.context = context;
        this.tripledots=tripledots;
    }

    public  void  PopUpMenu(){
        tripledots.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(context, v);
                MenuInflater inflater = popup.getMenuInflater();
                inflater.inflate(R.menu.popup_menu, popup.getMenu());
                popup.show();
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId())
                        {
                            case R.id.gdbr_menu:
                                Toast.makeText(context, "GDBR", Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.about_pop:
                                Toast.makeText(context, "ABOUT", Toast.LENGTH_SHORT).show();
                                break;
                        }


                        return false;
                    }
                });
             }
        });



    }
}
