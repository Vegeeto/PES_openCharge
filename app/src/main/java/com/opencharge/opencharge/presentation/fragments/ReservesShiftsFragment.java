package com.opencharge.opencharge.presentation.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.opencharge.opencharge.R;

public class ReservesShiftsFragment extends Fragment {

    public ReservesShiftsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View parentView = inflater.inflate(R.layout.fragment_reserves_shifts, container, false);
        RelativeLayout wrapper = (RelativeLayout) parentView.findViewById(R.id.hours_wrapper);
        createDayLayout(wrapper);
        createReservationShiftsViews(wrapper);

        return parentView;
    }

    private void createDayLayout(RelativeLayout wrapper) {
        int previousId = 0;
        for (int i = 0; i < 24; i++) {
            int height = (int) getResources().getDimension(R.dimen.day_view_hour_height);

            HourDayView hourView = new HourDayView(getActivity().getApplicationContext());
            int newId = View.generateViewId();
            hourView.setId(newId);

            String hourFormatted = String.format("%02d", i) + ":00";
            hourView.setHour(hourFormatted);

            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height);
            if (i == 0) {
                params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
            } else {
                params.addRule(RelativeLayout.BELOW, previousId);
            }
            hourView.setLayoutParams(params);

            wrapper.addView(hourView);
            previousId = newId;
        }
    }

    private void createReservationShiftsViews(RelativeLayout wrapper) {
        createReservationShiftView(1, 0, 60, wrapper);
        createReservationShiftView(6, 0, 30, wrapper);
        createReservationShiftView(8, 0, 120, wrapper);
    }

    private void createReservationShiftView(int hourStart, int minutesStart, int minutesDuration, RelativeLayout wrapper) {
        int hourHeight = (int) getResources().getDimension(R.dimen.day_view_hour_height);
        float minutesHeight = hourHeight/(float)60;

        int horizontalMargins = (int) getResources().getDimension(R.dimen.day_view_hour_padding);
        int leftMargin = (int) getResources().getDimension(R.dimen.day_view_hour_width);

        int topMargin = hourHeight/2;
        int minuteToStart = (hourStart*60) + minutesStart;

        int Y = (int)(topMargin + (minuteToStart*minutesHeight));
        int shiftViewHeight = (int)(minutesDuration*minutesHeight);

        View shiftView = new View(getActivity().getApplicationContext());
        shiftView.setBackgroundColor(Color.parseColor("#FF0000"));

        shiftView.setY(Y);
        shiftView.setMinimumHeight(shiftViewHeight);

        wrapper.addView(shiftView);

        ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) shiftView.getLayoutParams();
        p.setMargins(leftMargin, 0, horizontalMargins, 0);
        shiftView.requestLayout();

        shiftView.setClickable(true);
        shiftView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("shiftView", "CLIKED!!! at " + view.getY() + " + " + view.getHeight());
            }
        });
    }
}