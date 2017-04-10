package com.opencharge.opencharge.presentation.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.opencharge.opencharge.R;
import com.opencharge.opencharge.domain.Entities.Points;

import java.util.List;

/**
 * Created by Oriol on 10/4/2017.
 */

public class PointsAdapter extends RecyclerView.Adapter<PointsAdapter.ViewHolder> implements View.OnClickListener {

    private List<Points> items;
    private View.OnClickListener listener;
    private Context context;

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
            //Agafar els elements de la vista
        }

        public void bindPoints(Points p) {
            //Posar la informació d'un punt a la vista
        }
    }

    public PointsAdapter(Context context, List<Points> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public PointsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.content_recycler, parent, false);
        v.setOnClickListener(this);

        return new ViewHolder(v);
    }

    public void setOnClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View view) {
        if (listener != null) {
            listener.onClick(view);
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (items.get(position) != null) {
            holder.bindPoints(items.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

}
