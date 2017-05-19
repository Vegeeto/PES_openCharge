package com.opencharge.opencharge.presentation.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.opencharge.opencharge.R;
import com.opencharge.opencharge.domain.helpers.DateConversion;
import com.opencharge.opencharge.domain.helpers.impl.DateConversionImpl;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ReservesShiftsFragment extends Fragment {

    private static final String ARG_DAY_TIME = "day_time";
    private Date dayDate;

    private static final String ARG_POINT_ID = "point_id";
    private String pointId;

    private RelativeLayout mHoursWrapper;
    private TextView mDayMonthLabel;
    private TextView mDayWeekLabel;
    private FloatingActionButton mAddButton;

    public ReservesShiftsFragment() {
        // Required empty public constructor
    }

    public static ReservesShiftsFragment newInstance(long dayTime, String pointId) {
        ReservesShiftsFragment fragment = new ReservesShiftsFragment();
        Bundle args = new Bundle();
        args.putLong(ARG_DAY_TIME, dayTime);
        args.putString(ARG_POINT_ID, pointId);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.pointId = getArguments().getString(ARG_POINT_ID);
            
            long dayTime = getArguments().getLong(ARG_DAY_TIME);
            this.dayDate = new Date();
            this.dayDate.setTime(dayTime);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View parentView = inflater.inflate(R.layout.fragment_calendar_day, container, false);

        this.mHoursWrapper = (RelativeLayout) parentView.findViewById(R.id.hours_wrapper);
        this.mDayMonthLabel = (TextView) parentView.findViewById(R.id.calendar_day_month_label);
        this.mDayWeekLabel = (TextView) parentView.findViewById(R.id.calendar_day_week_label);
        this.mAddButton = (FloatingActionButton) parentView.findViewById(R.id.calendar_new_service);

        setUpAddButton();
        setUpDayLabels();
        createDayLayout();
        createReservationShiftsViews();

        return parentView;
    }

    private void setUpAddButton() {
        this.mAddButton.setRippleColor(Color.BLUE);
        this.mAddButton.setBackgroundColor(Color.RED);
        this.mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                DateConversion dc = new DateConversionImpl();
                CreateServiceFragment fragment = CreateServiceFragment.newInstance(pointId, dc.ConvertDateToString(dayDate), "10:00");
                ft.replace(R.id.content_frame, fragment).addToBackStack(null).commit();
            }
        });
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

    private void createReservationShiftView(final int hourStart, int minutesStart, int minutesDuration) {
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
                //Log.d("shiftView", "CLIKED!!! at " + view.getY() + " + " + view.getHeight());
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                DateConversion dc = new DateConversionImpl();
                CreateReserveFragment fragment = CreateReserveFragment.newInstance(pointId, dc.ConvertDateToString(dayDate), dc.ConvertIntToTimeString(hourStart, 0));
                ft.replace(R.id.content_frame, fragment).addToBackStack(null).commit();
            }
        });
    }
}
