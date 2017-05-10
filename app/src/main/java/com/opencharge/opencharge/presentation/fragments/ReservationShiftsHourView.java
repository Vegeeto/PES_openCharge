package com.opencharge.opencharge.presentation.fragments;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.opencharge.opencharge.R;

/**
 * Created by ferran on 3/5/17.
 */

public class ReservationShiftsHourView extends LinearLayout {
    private TextView mHora;
    private View mLine;

    public ReservationShiftsHourView(Context context) {
        super(context);
        initializeView(context);
    }

    public ReservationShiftsHourView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initializeView(context);
    }

    public ReservationShiftsHourView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
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
        inflater.inflate(R.layout.reservation_shifts_hour_view, this);

        mHora = (TextView)this.findViewById(R.id.hora);
        mLine = this.findViewById(R.id.line);

        mHora.setText("12:00");

        this.setMinimumHeight(150);
        this.setBackgroundColor(Color.parseColor("#FFFFFF"));
    }


    public void setHour(String hour) {
        mHora.setText(hour);
    }
}
