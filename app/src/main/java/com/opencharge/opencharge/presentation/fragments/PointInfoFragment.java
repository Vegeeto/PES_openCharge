package com.opencharge.opencharge.presentation.fragments;

import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.google.firebase.database.FirebaseDatabase;
import com.opencharge.opencharge.R;
import com.opencharge.opencharge.domain.Entities.Point;
import com.opencharge.opencharge.domain.Entities.Reserve;
import com.opencharge.opencharge.domain.Entities.User;
import com.opencharge.opencharge.domain.Entities.UserPointSummary;
import com.opencharge.opencharge.domain.use_cases.DeletePointUseCase;
import com.opencharge.opencharge.domain.use_cases.GetCurrentUserUseCase;
import com.opencharge.opencharge.domain.use_cases.PointByIdUseCase;
import com.opencharge.opencharge.domain.use_cases.ReserveRejectUseCase;
import com.opencharge.opencharge.presentation.adapters.ItemDecoration;
import com.opencharge.opencharge.presentation.adapters.PointsAdapter;
import com.opencharge.opencharge.presentation.locators.UseCasesLocator;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PointInfoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PointInfoFragment extends Fragment {

    private PointsAdapter pointsAdapter;
    private RecyclerView recyclerView;
    private FloatingActionButton horari;

    private static final String ARG_POINT_ID = "point_id";
    private String pointId;

    public PointInfoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param pointId String
     * @return A new instance of fragment PointInfoFragment.
     */
    public static PointInfoFragment newInstance(String pointId) {
        PointInfoFragment fragment = new PointInfoFragment();
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
        View view = inflater.inflate(R.layout.fragment_point_info, container, false);
        setHasOptionsMenu(true);

        recyclerView = (RecyclerView) view.findViewById(R.id.rv);
        horari = (FloatingActionButton) view.findViewById(R.id.horari);

        RelativeLayout datePickerButton = (RelativeLayout) getActivity().findViewById(R.id.date_picker_button);
        datePickerButton.setVisibility(View.GONE);

        UseCasesLocator useCasesLocator = UseCasesLocator.getInstance();
        PointByIdUseCase getPointUseCase = useCasesLocator.getPointByIdUseCase(new PointByIdUseCase.Callback() {
            @Override
            public void onPointRetrieved(Point point) {
                pointsAdapter = new PointsAdapter(getActivity(), point);

                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext(), LinearLayoutManager.VERTICAL, false));
                recyclerView.setHasFixedSize(true);
                recyclerView.setAdapter(pointsAdapter);
                recyclerView.addItemDecoration(new ItemDecoration(getActivity().getApplicationContext(), LinearLayoutManager.VERTICAL));
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setNestedScrollingEnabled(false);

                if (point.getAccessType().equals(Point.PARTICULAR_ACCESS)) {
                    horari.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            try {
                                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                                ft.setCustomAnimations(R.anim.slide_left, R.anim.nothing, R.anim.nothing, R.anim.slide_right);
                                DaysPagerFragment fragment = DaysPagerFragment.newInstance(pointId);
                                ft.add(R.id.content_frame, fragment).addToBackStack(null).commit();
                            } catch (NullPointerException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                } else {
                    horari.setVisibility(View.GONE);
                }

            }
        });

        getPointUseCase.setPointId(pointId);
        getPointUseCase.execute();

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        menuInflater.inflate(R.menu.point_navigation, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        final FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        switch (item.getItemId()) {
            case R.id.go_edit_button:
                EditPointFragment fragment = EditPointFragment.newInstance(pointId);
                ft.replace(R.id.content_frame, fragment).addToBackStack(null).commit();
                return true;
            case R.id.go_delete_button:
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("ESBORRAR PUNT");
                builder.setIcon(R.drawable.ic_warning_black_24dp);
                builder.setMessage("Segur que vols esborrar aquest punt?")
                        .setCancelable(false)
                        .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                UseCasesLocator useCasesLocator = UseCasesLocator.getInstance();
                                GetCurrentUserUseCase getCurrentUserUseCase = useCasesLocator.getGetCurrentUserUseCase(getActivity(), new GetCurrentUserUseCase.Callback() {
                                    @Override
                                    public void onCurrentUserRetrieved(User currentUser) {
                                        List<UserPointSummary>  points = currentUser.getPoints();
                                        for (UserPointSummary point : points) {
                                            if (point.getPointId().equals(pointId)) {
                                                FirebaseDatabase.getInstance().getReference("Users").child(currentUser.getId()).child("points").child(String.valueOf(points.indexOf(point))).removeValue();
                                            }
                                        }
                                        currentUser.setPoints(points);
                                    }
                                });
                                getCurrentUserUseCase.execute();
                                FirebaseDatabase.getInstance().getReference("Points").child(pointId).removeValue();
                                ft.replace(R.id.content_frame, new MapsFragment()).commit();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                builder.show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
