package com.opencharge.opencharge.presentation.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.opencharge.opencharge.R;

public class DaysPagerFragment extends Fragment {

    private ViewPager mPager;
    private PagerAdapter mPagerAdapter;

    private static final String ARG_POINT_ID = "point_id";
    private String pointId;

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
        // Inflate the layout for this fragment
        View parentView = inflater.inflate(R.layout.fragment_days_pager, container, false);

        mPager = (ViewPager) parentView.findViewById(R.id.pager);
        mPagerAdapter = new DaysPagerAdapter(getChildFragmentManager());
        mPager.setAdapter(mPagerAdapter);

        return parentView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPager.setCurrentItem(500, false);
    }

    private class DaysPagerAdapter extends FragmentStatePagerAdapter {

        public DaysPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            //TODO: calcular data per position i passarla al fragment
            return new ReservesShiftsFragment();
        }

        @Override
        public int getCount() {
            return 1000;
        }
    }
}
