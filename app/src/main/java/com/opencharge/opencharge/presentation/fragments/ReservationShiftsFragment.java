package com.opencharge.opencharge.presentation.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.opencharge.opencharge.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ReservationShiftsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class ReservationShiftsFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    public ReservationShiftsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View parentView = inflater.inflate(R.layout.fragment_reservation_shifts, container, false);
        RelativeLayout wrapper = (RelativeLayout) parentView.findViewById(R.id.hours_wrapper);
        createDayLayout(wrapper);

        return parentView;
    }

    private void createDayLayout(RelativeLayout wrapper) {
        int previousId = 0;
        for (int i = 0; i < 24; i++) {
            int height = (int) getResources().getDimension(R.dimen.day_view_hour_height);

            ReservationShiftsHourView hourView = new ReservationShiftsHourView(getActivity().getApplicationContext());
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

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
