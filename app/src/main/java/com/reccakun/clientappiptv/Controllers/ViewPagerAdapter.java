package com.reccakun.clientappiptv.Controllers;


import android.content.Context;
 import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.reccakun.clientappiptv.Models.Dream;
import com.reccakun.clientappiptv.R;

import java.util.ArrayList;
import java.util.List;


public class ViewPagerAdapter extends PagerAdapter {

    Context mContext;
    LayoutInflater mLayoutInflater;
    List<Dream> ld=new ArrayList<>();
    Integer idDream;
    DBConnect dbDreams ;
 boolean  isFavActivity;
ViewPager vp;
    public ViewPagerAdapter(Context context, List<Dream> ld, Integer idDream, boolean isFavActivity, ViewPager vp ) {
        mContext = context;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.idDream=idDream;
        this.ld=ld;
        this.isFavActivity=isFavActivity;
this.vp=vp;
    }

    @Override
    public int getCount() {
        return ld.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((LinearLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {

               View itemView = mLayoutInflater.inflate(R.layout.pager_item, container, false);

        TextView txtContent = (TextView) itemView.findViewById(R.id.txt_content);
        txtContent.setText(ld.get(position).getContent());
        TextView title = (TextView) itemView.findViewById(R.id.txt_pageTitle);
        title.setText(ld.get(position).getTitle());
        container.addView(itemView);
        dbDreams=new DBConnect(mContext.getApplicationContext());
        final ImageView btnAddFav = itemView.findViewById(R.id.btnAddFavorite);
        if (isFavActivity){
            try{
                ImageView btnNext=itemView.findViewById(R.id.btnNext);
                ImageView btnPrev=itemView.findViewById(R.id.btnPrev);
                btnNext.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ads.countShowAds++;

                        vp.setCurrentItem(vp.getCurrentItem()+1);
                    }
                });
                btnPrev.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ads.countShowAds++;

                        vp.setCurrentItem(vp.getCurrentItem()-1);
                    }
                });
            }catch (Exception e){
                Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
            if (dbDreams.isFavorite(ld.get(position).getDream_ID())){
                btnAddFav.setImageResource(R.drawable.addheart );
              //  Toast.makeText(mContext, ""+dbDreams.isFavorite(ld.get(position).getDream_ID()), Toast.LENGTH_SHORT).show();
            }
            else{
                //Toast.makeText(mContext, ""+ld.get(position).getDream_ID()+""+dbDreams.isFavorite(ld.get(position).getDream_ID()), Toast.LENGTH_SHORT).show();
                //btnAddFav.setText("اضافة للمفضلة");

            }





            btnAddFav.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ads.countShowAds++;
                    if (dbDreams.isFavorite(ld.get(position).getDream_ID())){

                        if (dbDreams.updateHandler( ld.get(position).getDream_ID(),-1 )){

                        }
                        btnAddFav.setImageResource(R.drawable.heart );
                    }else {
                        if (dbDreams.updateHandler( ld.get(position).getDream_ID(),1 )){

                        }
                        btnAddFav.setImageResource(R.drawable.addheart );
                    }

                    //Toast.makeText(mContext, "failed", Toast.LENGTH_SHORT).show();

                }
            });
        }else {
            try{
                ImageView btnNext=itemView.findViewById(R.id.btnNext);
                ImageView btnPrev=itemView.findViewById(R.id.btnPrev);
                btnNext.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        vp.setCurrentItem(vp.getCurrentItem()+1);
                    }
                });
                btnPrev.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        vp.setCurrentItem(vp.getCurrentItem()-1);
                    }
                });
            }catch (Exception e){
                Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_SHORT).show();
            }

            if (dbDreams.isFavorite(ld.get(position).getDream_ID()))
                btnAddFav.setImageResource(R.drawable.addheart );
            else
                btnAddFav.setImageResource(R.drawable.heart );
            btnAddFav.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (dbDreams.isFavorite(ld.get(position).getDream_ID())){
                        if (dbDreams.updateHandler( ld.get(position).getDream_ID(),-1 )){
                            btnAddFav.setImageResource(R.drawable.heart );

                        }
                    }else {
                        if (dbDreams.updateHandler( ld.get(position).getDream_ID(),1 )){
                            btnAddFav.setImageResource(R.drawable.addheart );

                        }
                    }

                    //Toast.makeText(mContext, "failed", Toast.LENGTH_SHORT).show();

                }
            });
        }



        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }
}
