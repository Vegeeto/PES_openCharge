package com.opencharge.opencharge.presentation.adapters;

import android.app.Activity;
import android.content.Context;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.opencharge.opencharge.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DmnT on 18/05/2017.
 */

public class CustomUserPointsAdapter extends ArrayAdapter
{

    List<Pair<String,String>> pointsList;
    private static LayoutInflater inflater = null;


    public CustomUserPointsAdapter(Context context, List<Pair<String,String>> list)
    {
        super(context,0,list);
        pointsList = list;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.row,parent,false);
// inflate custom layout called row
            holder = new ViewHolder();
            holder.tv =(TextView) convertView.findViewById(R.id.rowTextView);
// initialize textview
            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder)convertView.getTag();
        }
        Pair<String,String> in = pointsList.get(position);
        holder.tv.setText(in.second);
        // set the name to the text;

        return convertView;

    }

    static class ViewHolder
    {
        TextView tv;
    }
}