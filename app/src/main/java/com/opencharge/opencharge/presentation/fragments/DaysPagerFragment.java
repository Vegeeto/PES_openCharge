package com.opencharge.opencharge.presentation.fragments;

import android.content.Context;
import android.net.Uri;
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

    public DaysPagerFragment() {
        // Required empty public constructor
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
            return new ReservationShiftsFragment();
        }

        @Override
        public int getCount() {
            return 1000;
        }
    }
}
