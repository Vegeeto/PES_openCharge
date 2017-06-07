package com.opencharge.opencharge.presentation.fragments;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.opencharge.opencharge.R;

/**
 * Created by ferran on 3/5/17.
 */

public class HourDayView extends LinearLayout {
    private TextView mHora;

    public HourDayView(Context context) {
        super(context);
        initializeView(context);
    }

    public HourDayView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initializeView(context);
    }

    public HourDayView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initializeView(context);
    }

    /**
     * Inflates the views in the layout.
     *
     * @param context the current context for the view.
     */
    private void initializeView(Context context) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.hour_day_view, this);

        mHora = (TextView)this.findViewById(R.id.hora);

        this.setBackgroundColor(Color.parseColor("#FFFFFF"));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int height = (int) getResources().getDimension(R.dimen.day_view_hour_height);
        setMeasuredDimension(widthMeasureSpec, height);
    }

    public void setHour(String hour) {
        mHora.setText(hour);
    }
}
