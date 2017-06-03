package com.opencharge.opencharge.presentation.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;

import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.opencharge.opencharge.R;
import com.opencharge.opencharge.domain.Entities.Comment;
import com.opencharge.opencharge.domain.use_cases.CommentsListUseCase;
import com.opencharge.opencharge.presentation.adapters.CommentsAdapter;
import com.opencharge.opencharge.presentation.locators.UseCasesLocator;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShowCommentsFragment extends Fragment {

    private CommentsAdapter commentsAdapter;
    private RecyclerView recyclerView;

    private String pointId;
    private static final String ARG_POINT_ID = "point_id";

    public ShowCommentsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param pointId String
     * @return A new instance of fragment PointInfoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ShowCommentsFragment newInstance(String pointId) {
        ShowCommentsFragment fragment = new ShowCommentsFragment();
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
        recyclerView = (RecyclerView)view.findViewById(R.id.rv);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        UseCasesLocator useCasesLocator = UseCasesLocator.getInstance();
        CommentsListUseCase getCommentsUseCase = useCasesLocator.getCommentsListUseCase(new CommentsListUseCase.Callback() {
            @Override
            public void onCommentsRetrieved(Comment[] comments) {
                commentsAdapter = new CommentsAdapter(getActivity(), new ArrayList<>(Arrays.asList(comments)));

                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                recyclerView.setHasFixedSize(true);
                recyclerView.setAdapter(commentsAdapter);
                //recyclerView.addItemDecoration(new ItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
                recyclerView.setItemAnimator(new DefaultItemAnimator());
            }
        });

        getCommentsUseCase.setPointId(pointId);
        getCommentsUseCase.execute();

    }

}
