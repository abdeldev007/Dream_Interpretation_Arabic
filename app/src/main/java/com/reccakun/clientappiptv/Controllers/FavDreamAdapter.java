package com.reccakun.clientappiptv.Controllers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.reccakun.clientappiptv.Models.Dream;
import com.reccakun.clientappiptv.R;

import java.util.List;

public class FavDreamAdapter extends ArrayAdapter<Dream> {
    private int resourceId;
    private Context mContext;


    public FavDreamAdapter(@NonNull Context context, int resource, @NonNull List<Dream> objects) {
        super(context, resource, objects);
        resourceId = resource;
        this.mContext = context;
    }


    @NonNull
    @Override
    public View getView(int position, @NonNull View convertView, @NonNull ViewGroup parent) {
        try {
            View view = null;
            ViewHolder viewHolder = new ViewHolder();
            final Dream c = getItem(position);

            if (convertView == null) {
                view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
                viewHolder.CourseTitle = view.findViewById(R.id.txt_title);
                viewHolder.CourseDesc = view.findViewById(R.id.txt_desc);
                viewHolder.layoutDream = view.findViewById(R.id.layoutDream);

                view.setTag(viewHolder);
            } else {
                view = convertView;
                viewHolder = (ViewHolder) view.getTag();
            }
            viewHolder.CourseDesc.setText(c.getDescription());
            viewHolder.CourseTitle.setText(c.getTitle());

            return view;

        } catch (Exception e) {

            Toast.makeText(mContext, "err" + e.getMessage(), Toast.LENGTH_SHORT).show();
            return null;
        }


    }

    class ViewHolder {
        TextView CourseTitle;
        TextView CourseDesc;
        ConstraintLayout layoutDream;

    }
}
