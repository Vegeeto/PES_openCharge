package com.opencharge.opencharge.presentation.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.opencharge.opencharge.R;
import com.opencharge.opencharge.domain.Entities.Reserve;
import com.opencharge.opencharge.domain.use_cases.ReservesUserAsSupplierUseCase;
import com.opencharge.opencharge.presentation.adapters.ItemDecoration;
import com.opencharge.opencharge.presentation.adapters.SupplierReservesAdapter;
import com.opencharge.opencharge.presentation.adapters.UserReservesAdapter;
import com.opencharge.opencharge.presentation.locators.UseCasesLocator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class SupplierReservesFragment extends Fragment {


    private SupplierReservesAdapter reservesAdapter;
    private RecyclerView recyclerView;

    private static final String ARG_USER_ID = "user_id";
    private String userId;


    public SupplierReservesFragment() {
        // Required empty public constructor
    }

    public static SupplierReservesFragment newInstance(String userId) {
        SupplierReservesFragment fragment = new SupplierReservesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_USER_ID, userId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.userId = getArguments().getString(ARG_USER_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_reserves, container, false);

        setHasOptionsMenu(true);
        recyclerView = (RecyclerView) view.findViewById(R.id.userReservesRV);

        UseCasesLocator useCasesLocator = UseCasesLocator.getInstance();
        ReservesUserAsSupplierUseCase reservesUserAsSupplierUseCase = useCasesLocator.getReservesUserAsSupplierUseCaseImpl(new ReservesUserAsSupplierUseCase.Callback() {
            @Override
            public void onReservesRetrieved(Reserve[] reserves) {
                List<Reserve> reserve = new ArrayList<>(Arrays.asList(reserves));
                Integer r = reserve.size();
                // Toast.makeText(getActivity().getApplicationContext(), r.toString(), Toast.LENGTH_SHORT).show();
                reservesAdapter = new SupplierReservesAdapter(getActivity(), reserve);
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext(), LinearLayoutManager.VERTICAL, false));
                recyclerView.setHasFixedSize(true);
                recyclerView.setAdapter(reservesAdapter);
                recyclerView.addItemDecoration(new ItemDecoration(getActivity().getApplicationContext(), LinearLayoutManager.VERTICAL));
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setNestedScrollingEnabled(false);

            }
        });
        reservesUserAsSupplierUseCase.setUserId(userId);
        reservesUserAsSupplierUseCase.execute();

        return view;
    }

}
