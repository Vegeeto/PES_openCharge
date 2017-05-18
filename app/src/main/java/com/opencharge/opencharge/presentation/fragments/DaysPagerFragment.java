package com.opencharge.opencharge.presentation.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toolbar;


import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.opencharge.opencharge.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class DaysPagerFragment extends Fragment {

    private ViewPager mPager;
    private PagerAdapter mPagerAdapter;

    private SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM yyyy", Locale.getDefault());
    private CompactCalendarView mCompactCalendarView;
    private boolean isExpanded = false;

    private static final String ARG_POINT_ID = "point_id";
    private String pointId;

    private static final int TOTAL_DAYS = 1000;

    public DaysPagerFragment() {
        // Required empty public constructor
    }

    public static DaysPagerFragment newInstance(String pointId) {
        DaysPagerFragment fragment = new DaysPagerFragment();
        Bundle args = new Bundle();
        args.putString(ARG_POINT_ID, pointId);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.pointId = getArguments().getString(ARG_POINT_ID);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        setHasOptionsMenu(true);
        View parentView = inflater.inflate(R.layout.fragment_days_pager, container, false);

        final AppBarLayout appBar = (AppBarLayout) getActivity().findViewById(R.id.app_bar_layout);
        /*CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) getActivity().findViewById(R.id.collapsingToolbarLayout);
        AppBarLayout.LayoutParams p = (AppBarLayout.LayoutParams) collapsingToolbar.getLayoutParams();
        p.setScrollFlags(0);
        collapsingToolbar.setLayoutParams(p);
        appBar.setExpanded(false, false);*/

        // Set up the CompactCalendarView
        mCompactCalendarView = (CompactCalendarView) getActivity().findViewById(R.id.compactcalendar_view);

        // Force English
        mCompactCalendarView.setLocale(TimeZone.getDefault(), Locale.ENGLISH);

        mCompactCalendarView.setShouldDrawDaysHeader(true);

        mCompactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                setTitle(dateFormat.format(dateClicked));
            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                setTitle(dateFormat.format(firstDayOfNewMonth));
            }
        });

        // Set current date to today
        setCurrentDate(new Date());

        final ImageView arrow = (ImageView) getActivity().findViewById(R.id.date_picker_arrow);

        RelativeLayout datePickerButton = (RelativeLayout) getActivity().findViewById(R.id.date_picker_button);

        datePickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isExpanded) {
                    ViewCompat.animate(arrow).rotation(0).start();
                    appBar.setExpanded(false, true);
                    isExpanded = false;
                } else {
                    ViewCompat.animate(arrow).rotation(180).start();
                    appBar.setExpanded(true, true);
                    isExpanded = true;
                }
            }
        });

        mPager = (ViewPager) parentView.findViewById(R.id.pager);
        mPagerAdapter = new DaysPagerAdapter(getChildFragmentManager());
        mPager.setAdapter(mPagerAdapter);

        return parentView;
    }

    public void setCurrentDate(Date date) {
        setTitle(dateFormat.format(date));
        if (mCompactCalendarView != null) {
            mCompactCalendarView.setCurrentDate(date);
        }

    }


    public void setTitle(String title) {
        TextView datePickerTextView = (TextView) getActivity().findViewById(R.id.date_picker_title);

        if (datePickerTextView != null) {
            datePickerTextView.setText(title);
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPager.setCurrentItem(TOTAL_DAYS/2, false);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        menuInflater.inflate(R.menu.calendar_navigation, menu);

        final AppBarLayout appBar = (AppBarLayout)  getActivity().findViewById(R.id.app_bar_layout);
        appBar.setExpanded(false, false);

        final CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) getActivity().findViewById(R.id.collapsingToolbarLayout);
        AppBarLayout.LayoutParams p = (AppBarLayout.LayoutParams) collapsingToolbar.getLayoutParams();
        p.setScrollFlags(0);
        collapsingToolbar.setLayoutParams(p);
        final RelativeLayout relativeLayout = (RelativeLayout) getActivity().findViewById(R.id.date_picker_button);
        relativeLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.go_today_button:
                mPager.setCurrentItem(TOTAL_DAYS/2, true);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private class DaysPagerAdapter extends FragmentStatePagerAdapter {

        public DaysPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Date today = new Date();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(today);
            calendar.add(Calendar.DATE, position - (TOTAL_DAYS/2));

            long itemTime = calendar.getTimeInMillis();
            return ReservesShiftsFragment.newInstance(itemTime);
        }

        @Override
        public int getCount() {
            return TOTAL_DAYS;
        }
    }
}
