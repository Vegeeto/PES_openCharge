package com.opencharge.opencharge.presentation.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.opencharge.opencharge.R;
import com.opencharge.opencharge.domain.Entities.MockUser;
import com.opencharge.opencharge.domain.Entities.Point;
import com.opencharge.opencharge.domain.use_cases.PointByIdUseCase;
import com.opencharge.opencharge.domain.use_cases.UserByIdUseCase;
import com.opencharge.opencharge.presentation.adapters.CustomUserPointsAdapter;
import com.opencharge.opencharge.presentation.adapters.ItemDecoration;
import com.opencharge.opencharge.presentation.adapters.PointsAdapter;
import com.opencharge.opencharge.presentation.locators.UseCasesLocator;

/**
 * Created by DmnT on 17/05/2017.
 */

public class UserInfoFragment extends Fragment {

    private PointsAdapter pointsAdapter;
    private TextView nomUsuari;
    private TextView emailUsuari;
    private TextView minutsUsuari;
    private ListView puntsUsuari;
    private ListView puntsResUsuari;

    private static final String ARG_USER_ID = "user_id";
    private String userId;

    public UserInfoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param userId String
     * @return A new instance of fragment UserInfoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UserInfoFragment newInstance(String userId) {
        UserInfoFragment fragment = new UserInfoFragment();
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
        View view = inflater.inflate(R.layout.fragment_user_info, container, false);
        nomUsuari = (TextView)view.findViewById(R.id.perfil_usuari_nom2);
        emailUsuari = (TextView)view.findViewById(R.id.perfil_usuari_email2);
        minutsUsuari = (TextView)view.findViewById(R.id.perfil_usuari_nom2);
        puntsUsuari = (ListView)view.findViewById(R.id.perfil_usuari_punts2);
        puntsResUsuari = (ListView)view.findViewById(R.id.perfil_usuari_puntsReser2);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        UseCasesLocator useCasesLocator = UseCasesLocator.getInstance();
        UserByIdUseCase getUserUseCase = useCasesLocator.getUserByIdUseCase(new UserByIdUseCase.Callback() {
            @Override
            public void onUserRetrieved(MockUser user) {

                nomUsuari.setText(user.getUsername());
                emailUsuari.setText(user.getEmail());
                minutsUsuari.setText(user.getUsername());
                //possible fallo que el context no s'estigui passant correctament?:
                puntsUsuari.setAdapter(new CustomUserPointsAdapter(getActivity().getApplicationContext(),user.getPunts()));
                puntsResUsuari.setAdapter(new CustomUserPointsAdapter(getActivity().getApplicationContext(),user.getPuntsReservats()));

            }
        });

        getUserUseCase.setUserId(userId);
        getUserUseCase.execute();
    }
}
