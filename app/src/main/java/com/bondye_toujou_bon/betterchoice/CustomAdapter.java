package com.bondye_toujou_bon.betterchoice;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Krissandra Charmant on 3/13/2018.
 */

public class CustomAdapter extends BaseAdapter {

    ArrayList<String> username;
    ArrayList<Integer> score;
    ArrayList<String> comment;
    ArrayList<Integer> imgId;
    Context context;
    private static LayoutInflater inflater=null;

    public CustomAdapter(HalfReviews mainActivity, ArrayList<String> username, ArrayList<Integer> score, ArrayList<String> comment, ArrayList<Integer> imgId ) {
        context=mainActivity;

        this.username=username;
        this.score=score;
        this.comment=comment;
        this.imgId=imgId;

        inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount(){
        return username.size();
    }

    @Override
    public Object getItem(int position){
        return position;
    }

    @Override
    public long getItemId(int position){
        return position;
    }

    public class Holder{
        TextView tvusername;
        TextView tvscore;
        TextView tvcomment;
        ImageView imv;
    }

    @Override
    public View getView(final int position,View convertView,ViewGroup parent){
        Holder holder =new Holder();
        View rowView;
        rowView=inflater.inflate(R.layout.listviewlayout,null);
        holder.tvusername=(TextView)rowView.findViewById(R.id.username);
        holder.tvscore=(TextView)rowView.findViewById(R.id.score);
        holder.tvcomment=(TextView)rowView.findViewById(R.id.comment);
        holder.imv=(ImageView)rowView.findViewById(R.id.imageView);

        holder.tvusername.setText(username.get(position));
        holder.tvscore.setText((String.valueOf(score.get(position))));
        holder.tvcomment.setText(comment.get(position));
        holder.imv.setImageResource(imgId.get(position));

        return rowView;
    }


}
