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
import com.opencharge.opencharge.domain.Entities.Reserve;
import com.opencharge.opencharge.domain.Entities.Service;
import com.opencharge.opencharge.domain.helpers.DateConversion;
import com.opencharge.opencharge.domain.helpers.impl.DateConversionImpl;
import com.opencharge.opencharge.domain.use_cases.ReservesListByPointAndDayUseCase;
import com.opencharge.opencharge.domain.use_cases.ServiceListByPointAndDayUseCase;
import com.opencharge.opencharge.presentation.locators.UseCasesLocator;

import org.joda.time.DateTime;
import org.joda.time.LocalTime;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ReservesShiftsFragment extends Fragment {

    private static final String ARG_DAY_TIME = "day_time";
    private Date dayDate;

    private static final String ARG_POINT_ID = "point_id";
    private String pointId;

    private RelativeLayout mHoursWrapper;
    private TextView mDayMonthLabel;
    private TextView mDayWeekLabel;
    private FloatingActionButton mAddButton;

    private Service[] services;
    private Reserve[] reserves;

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

        loadServices();
        loadReserves();

        return parentView;
    }

    private void loadServices() {
        ServiceListByPointAndDayUseCase useCase = UseCasesLocator.getInstance().getServiceListByPointAndDayUseCase(new ServiceListByPointAndDayUseCase.Callback() {
            @Override
            public void onServicesRetrieved(Service[] services) {
                setServices(services);
            }

            @Override
            public void onError() {

            }
        });
        useCase.setDay(dayDate);
        useCase.setPointId(pointId);
        useCase.execute();
    }
    private void setServices(Service[] services) {
        this.services = services;
        if (reserves != null) {
            createServicesAndReservesViews();
        }
    }

    private void loadReserves() {
        ReservesListByPointAndDayUseCase useCase = UseCasesLocator.getInstance().getReservesListByPointAndDayUseCase(new ReservesListByPointAndDayUseCase.Callback() {
            @Override
            public void onReservesRetrieved(Reserve[] reserves) {
                setReserves(reserves);
            }

            @Override
            public void onError() {

            }
        });
        useCase.setDay(dayDate);
        useCase.setPointId(pointId);
        useCase.execute();
    }
    private void setReserves(Reserve[] reserves) {
        this.reserves = reserves;
        if (services != null) {
            createServicesAndReservesViews();
        }
    }

    private void setUpAddButton() {
        this.mAddButton.setRippleColor(Color.BLUE);
        this.mAddButton.setBackgroundColor(Color.RED);
        this.mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToCreateServiceAtHour("10:00");
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
            hourView.setTag(i);

            String hourFormatted = String.format("%02d", i) + ":00";
            hourView.setHour(hourFormatted);

            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height);
            if (i == 0) {
                params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
            } else {
                params.addRule(RelativeLayout.BELOW, previousId);
            }
            hourView.setLayoutParams(params);

            hourView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int hour = (int) view.getTag();
                    goToCreateServiceAtHour(hour + ":00");
                }
            });

            this.mHoursWrapper.addView(hourView);
            previousId = newId;
        }
    }

    private void goToCreateServiceAtHour(String hour) {
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        DateConversion dc = new DateConversionImpl();
        CreateServiceFragment fragment = CreateServiceFragment.newInstance(pointId, dc.ConvertDateToString(dayDate), hour);
        ft.replace(R.id.content_frame, fragment).addToBackStack(null).commit();
    }

    private void createServicesAndReservesViews() {
        for (Service service : services) {
            Reserve[] reservesInService = getReservesInRange(service.getStartHour(), service.getEndHour());
            if (reservesInService.length > 0) {
                splitAndCreateServiceViews(service, reserves);
                createReservesViews(reservesInService);
            }
            else {
                createCompleteServiceView(service);
            }
        }
    }

    private Reserve[] getReservesInRange(Date startHour, Date endHour) {
        LocalTime start = new LocalTime(startHour);
        LocalTime end = new LocalTime(endHour);
        List<Reserve> filteredReserves = new ArrayList<>();
        for (Reserve reserve : reserves) {
            if (reserveIsInRange(start, end, reserve)) {
                filteredReserves.add(reserve);
            }
        }

        Reserve[] reservesArr = new Reserve[filteredReserves.size()];
        reservesArr = filteredReserves.toArray(reservesArr);
        return reservesArr;
    }

    private boolean reserveIsInRange(LocalTime start, LocalTime end, Reserve reserve) {
        LocalTime targetStartHour = new LocalTime(reserve.getStartHour());
        LocalTime targetEndHour = new LocalTime(reserve.getEndHour());

        boolean startIsInRange = isBetweenInclusive(start, end, targetStartHour);
        boolean endIsInRange = isBetweenInclusive(start, end, targetEndHour);

        return startIsInRange && endIsInRange;
    }

    private boolean isBetweenInclusive(LocalTime start, LocalTime end, LocalTime target) {
        return !target.isBefore(start) && !target.isAfter(end);
    }

    private void createReservesViews(Reserve[] reserves) {
        for (Reserve reserve : reserves) {
            DateTime start = new DateTime(reserve.getStartHour());
            DateTime end = new DateTime(reserve.getEndHour());
            int startHour = start.getHourOfDay();
            int startMinute = start.getMinuteOfHour();
            int duration = end.getMinuteOfDay() - start.getMinuteOfDay();

            createReserveView(startHour, startMinute, duration);
        }
    }

    private void createReserveView(final int hourStart, int minutesStart, final int minutesDuration) {
        createRectangleView(hourStart, minutesStart, minutesDuration, Color.RED, new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        Log.d("createRectangleView", "RESERVE at " + dayDate + " || " + hourStart + ":" + minutesStart + " - " + minutesDuration);
    }

    private void createCompleteServiceView(Service service) {
        DateTime start = new DateTime(service.getStartHour());
        DateTime end = new DateTime(service.getEndHour());
        int startHour = start.getHourOfDay();
        int startMinute = start.getMinuteOfHour();
        int duration = end.getMinuteOfDay() - start.getMinuteOfDay();

        createServiceView(startHour, startMinute, duration);
    }

    private void splitAndCreateServiceViews(Service service, Reserve[] reserves) {
        int sliceStart = new DateTime(service.getStartHour()).getMinuteOfDay();

        for (Reserve reserve : reserves) {
            int reserveStart = new DateTime(reserve.getStartHour()).getMinuteOfDay();

            int duration = reserveStart - sliceStart;
            if (duration > 0) {
                int startHour = getHourOfDayFromMinuteOfDay(sliceStart);
                int startMinute = getMinuteOfHourFromMinuteOfDay(sliceStart);
                createServiceView(startHour, startMinute, duration);
            }

            sliceStart = new DateTime(reserve.getEndHour()).getMinuteOfDay();
        }

        //Print last slice (if needed)
        int serviceEnd = new DateTime(service.getEndHour()).getMinuteOfDay();
        int duration = serviceEnd - sliceStart;
        if (duration > 0) {
            int startHour = getHourOfDayFromMinuteOfDay(sliceStart);
            int startMinute = getMinuteOfHourFromMinuteOfDay(sliceStart);
            createServiceView(startHour, startMinute, duration);
        }
    }

    private int getHourOfDayFromMinuteOfDay(int minuteOfDay) {
        return minuteOfDay/60;
    }

    private int getMinuteOfHourFromMinuteOfDay(int minuteOfDay) {
        return minuteOfDay%60;
    }

    private void createServiceView(final int hourStart, int minutesStart, final int minutesDuration) {
        createRectangleView(hourStart, minutesStart, minutesDuration, Color.GREEN, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                DateConversion dc = new DateConversionImpl();
                int hourEnd = hourStart + minutesDuration / 60;
                int minEnd = minutesDuration % 60;
                CreateReserveFragment fragment = CreateReserveFragment.newInstance(pointId, dc.ConvertDateToString(dayDate), dc.ConvertHourAndMinutesToString(hourStart, 0), dc.ConvertHourAndMinutesToString(hourEnd, minEnd));
                ft.replace(R.id.content_frame, fragment).addToBackStack(null).commit();
            }
        });
        Log.d("createRectangleView", "SERVICE at " + dayDate + " || " + hourStart + ":" + minutesStart + " - " + minutesDuration);
    }


    private void createRectangleView(final int hourStart,
                                     int minutesStart,
                                     final int minutesDuration,
                                     int color,
                                     View.OnClickListener clickListener) {

        int hourHeight = (int) getResources().getDimension(R.dimen.day_view_hour_height);
        float minutesHeight = hourHeight / (float) 60;

        int horizontalMargins = (int) getResources().getDimension(R.dimen.day_view_hour_padding);
        int leftMargin = (int) getResources().getDimension(R.dimen.day_view_hour_width);

        int topMargin = hourHeight / 2;
        int minuteToStart = (hourStart * 60) + minutesStart;

        int Y = (int) (topMargin + (minuteToStart * minutesHeight));
        int shiftViewHeight = (int) (minutesDuration * minutesHeight);

        View shiftView = new View(getActivity().getApplicationContext());
        shiftView.setBackgroundColor(color);

        shiftView.setY(Y);
        shiftView.setMinimumHeight(shiftViewHeight);

        mHoursWrapper.addView(shiftView);

        ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) shiftView.getLayoutParams();
        p.setMargins(leftMargin, 0, horizontalMargins, 0);
        shiftView.requestLayout();

        shiftView.setClickable(true);
        shiftView.setOnClickListener(clickListener);
    }
}
