package com.opencharge.opencharge.presentation.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.opencharge.opencharge.R;
import com.opencharge.opencharge.domain.Entities.UserPointSummary;

import java.util.List;

/**
 * Created by DmnT on 18/05/2017.
 */

public class CustomUserPointsAdapter extends ArrayAdapter {

    List<UserPointSummary> pointsList;

    private static class ViewHolder {
        private TextView text;

        public ViewHolder(View v) {
            text = (TextView) v.findViewById(R.id.rowTextView);
        }

        public final void bindPosition(String address) {
            text.setText(address);
        }

    }


    public CustomUserPointsAdapter(Context context, List<UserPointSummary> list) {
        super(context, 0, list);
        pointsList = list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        UserPointSummary pointSummary = pointsList.get(position);

        ViewHolder holder;

        //Check if an existing view is being reused, otherwise inflate the view.
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.row, parent, false);
            holder = new ViewHolder(convertView);
            holder.bindPosition(pointSummary.getPointAddress());
            convertView.setTag(holder);
        } else {

            holder = (ViewHolder) convertView.getTag();
        }

        holder.bindPosition(pointSummary.getPointAddress());

        return convertView;

    }

    public String getPointID(int position) {
        return this.pointsList.get(position).getPointId();
    }

}