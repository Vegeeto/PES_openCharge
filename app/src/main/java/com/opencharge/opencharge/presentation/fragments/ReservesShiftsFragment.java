package com.opencharge.opencharge.presentation.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.opencharge.opencharge.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ReservesShiftsFragment extends Fragment {

    private static final String ARG_DAY_TIME = "point_id";
    private Date dayDate;

    private RelativeLayout mHoursWrapper;
    private TextView mDayMonthLabel;
    private TextView mDayWeekLabel;

    public ReservesShiftsFragment() {
        // Required empty public constructor
    }

    public static ReservesShiftsFragment newInstance(long dayTime) {
        ReservesShiftsFragment fragment = new ReservesShiftsFragment();
        Bundle args = new Bundle();
        args.putLong(ARG_DAY_TIME, dayTime);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            long dayTime = getArguments().getLong(ARG_DAY_TIME);
            this.dayDate = new Date();
            this.dayDate.setTime(dayTime);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View parentView = inflater.inflate(R.layout.fragment_reserves_shifts, container, false);

        this.mHoursWrapper = (RelativeLayout) parentView.findViewById(R.id.hours_wrapper);
        this.mDayMonthLabel = (TextView) parentView.findViewById(R.id.calendar_day_month_label);
        this.mDayWeekLabel = (TextView) parentView.findViewById(R.id.calendar_day_week_label);

        setUpDayLabels();
        createDayLayout();
        createReservationShiftsViews();

        return parentView;
    }

    private void setUpDayLabels() {
        SimpleDateFormat dayMonthFormat = new SimpleDateFormat("d");
        String dayOfMonth = dayMonthFormat.format(this.dayDate);
        this.mDayMonthLabel.setText(dayOfMonth);

        SimpleDateFormat dayWeekFormat = new SimpleDateFormat("E");
        String dayOfWeek = dayWeekFormat.format(this.dayDate);
        this.mDayWeekLabel.setText(dayOfWeek);
    }

    private void createDayLayout() {
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

            this.mHoursWrapper.addView(hourView);
            previousId = newId;
        }
    }


    private void createReservationShiftsViews() {
        createReservationShiftView(1, 0, 60);
        createReservationShiftView(6, 0, 30);
        createReservationShiftView(8, 0, 120);
    }

    private void createReservationShiftView(int hourStart, int minutesStart, int minutesDuration) {
        int hourHeight = (int) getResources().getDimension(R.dimen.day_view_hour_height);
        float minutesHeight = hourHeight / (float) 60;

        int horizontalMargins = (int) getResources().getDimension(R.dimen.day_view_hour_padding);
        int leftMargin = (int) getResources().getDimension(R.dimen.day_view_hour_width);

        int topMargin = hourHeight / 2;
        int minuteToStart = (hourStart * 60) + minutesStart;

        int Y = (int) (topMargin + (minuteToStart * minutesHeight));
        int shiftViewHeight = (int) (minutesDuration * minutesHeight);

        View shiftView = new View(getActivity().getApplicationContext());
        shiftView.setBackgroundColor(Color.parseColor("#FF0000"));

        shiftView.setY(Y);
        shiftView.setMinimumHeight(shiftViewHeight);

        mHoursWrapper.addView(shiftView);

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
