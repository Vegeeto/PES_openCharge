package com.opencharge.opencharge;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Oriol on 15/3/2017.
 */

public abstract class MyFragment extends Fragment {

    protected FragmentActivity fActivity;

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        fActivity = (FragmentActivity) activity;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public abstract View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

}
