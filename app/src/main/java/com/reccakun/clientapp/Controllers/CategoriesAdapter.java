package com.reccakun.clientapp.Controllers;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import androidx.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.reccakun.clientapp.Models.Category;
import com.reccakun.clientapp.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CategoriesAdapter extends ArrayAdapter<Category>  {
    private int resourceId;
    private Context mContext;
    public List<String> txt_list ;
      private List<Category> categoryArrayList = new ArrayList<>();
    private Map<Integer, ObjectAnimator> animators;
    private Map<Integer, Boolean> animating;
    public boolean isScrolling;
    private AnimationDrawable animationDrawable2;

    public CategoriesAdapter(@NonNull Context context, int resource, @NonNull  List<Category> objects) {
        super(context, resource, objects);
        resourceId = resource;
        this.mContext = context;
            categoryArrayList.addAll(objects);

    }


    @NonNull
    @Override
    public View getView(int position,  @NonNull View convertView,  @NonNull  ViewGroup parent) {

        View view = null;
        ViewHolder viewHolder=new ViewHolder();
        final Category c = categoryArrayList.get(position) ;
            try{
        if (convertView==null){
            view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
            viewHolder.catTitle =view.findViewById(R.id.txt_catTile);
            viewHolder.catDes =view.findViewById(R.id.txt_CatDesc);
            viewHolder.catImg =view.findViewById(R.id.img_Alphabet);
            viewHolder.frameLayout=view.findViewById(R.id.itemGrid);


            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }


         String img="image_part_003";



        // viewHolder.catDes.setText(""+c.getImg());
        viewHolder.catTitle.setText(c.getTitle());


        if (c.getImg().length()<=5){

            String uri = "@drawable/image_part_00"+c.getImg().substring(0,1);  // where myresource (without the extension) is the file
            int imageResource = mContext.getResources().getIdentifier(uri, null, mContext.getPackageName());
            Drawable res = mContext.getResources().getDrawable(imageResource);
            viewHolder.catImg.setImageDrawable(res);
        }else {
            String uri = "@drawable/image_part_0"+c.getImg().substring(0,2);  // where myresource (without the extension) is the file
            int imageResource = mContext.getResources().getIdentifier(uri, null, mContext.getPackageName());
            Drawable res = mContext.getResources().getDrawable(imageResource);
            viewHolder.catImg.setImageDrawable(res);
        }

        }catch (Exception e){
            Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_SHORT).show();

        }


        return  view ;
    }

    class ViewHolder{
        ImageView catImg;
        TextView catTitle;
        TextView catDes;

        FrameLayout frameLayout ;

    }


}
