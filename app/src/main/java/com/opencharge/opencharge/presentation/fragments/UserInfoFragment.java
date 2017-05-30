package com.opencharge.opencharge.presentation.fragments;

//import android.app.Fragment;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.opencharge.opencharge.R;
import com.opencharge.opencharge.domain.Entities.User;
import com.opencharge.opencharge.domain.use_cases.UserByIdUseCase;
import com.opencharge.opencharge.presentation.adapters.CustomUserPointsAdapter;
import com.opencharge.opencharge.presentation.adapters.PointsAdapter;
import com.opencharge.opencharge.presentation.locators.UseCasesLocator;

/**
 * Created by DmnT on 17/05/2017.
 */

public class UserInfoFragment extends Fragment {

    private PointsAdapter pointsAdapter;
    private ImageView imatgeUsuari;
    private TextView nomUsuari;
    private TextView emailUsuari;
    private TextView minutsUsuari;
    private ListView puntsUsuari;
    private ListView puntsResUsuari;
    private Button botoEliminarCompta;

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
        imatgeUsuari = (ImageView)view.findViewById(R.id.perfil_usuari_imatge);
        nomUsuari = (TextView)view.findViewById(R.id.perfil_usuari_nom2);
        emailUsuari = (TextView)view.findViewById(R.id.perfil_usuari_email2);
        minutsUsuari = (TextView)view.findViewById(R.id.perfil_usuari_nom2);
        puntsUsuari = (ListView)view.findViewById(R.id.perfil_usuari_punts2);
        puntsResUsuari = (ListView)view.findViewById(R.id.perfil_usuari_puntsReser2);
        botoEliminarCompta = (Button)view.findViewById(R.id.perfil_usuari_boto_eliminar);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        UseCasesLocator useCasesLocator = UseCasesLocator.getInstance();
        UserByIdUseCase getUserUseCase = useCasesLocator.getUserByIdUseCase(new UserByIdUseCase.Callback() {
            @Override
            public void onUserRetrieved(final User user) {

                //TODO importar la llibreria externa corresponent i descomentar aquesta part
                //Picasso.with(getActivity().getApplicationContext()).load(user.getPhoto()).into(imatgeUsuari);
                nomUsuari.setText(user.getUsername());
                emailUsuari.setText(user.getEmail());
                minutsUsuari.setText(user.getUsername());
                //possible fallo que el context no s'estigui passant correctament?:
                puntsUsuari.setAdapter(new CustomUserPointsAdapter(getActivity().getApplicationContext(),user.getPunts()));
                puntsUsuari.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?> listView, View itemView, int itemPosition, long itemId)
                    {
                        String pointId=user.getPunts().get(itemPosition).first;
                        try {
                            FragmentTransaction ft = getFragmentManager().beginTransaction();
                            //ft.setCustomAnimations(R.animator.slide_up, R.animator.slide_down);
                            PointInfoFragment fragment = PointInfoFragment.newInstance(pointId);
                            ft.replace(R.id.content_frame, fragment).commit();
                        } catch (NullPointerException e) {
                            e.printStackTrace();
                        }
                    }
                });
                puntsResUsuari.setAdapter(new CustomUserPointsAdapter(getActivity().getApplicationContext(),user.getPuntsReservats()));
                puntsResUsuari.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?> listView, View itemView, int itemPosition, long itemId)
                    {
                        String pointId=user.getPuntsReservats().get(itemPosition).first;
                        try {
                            FragmentTransaction ft = getFragmentManager().beginTransaction();
                            //ft.setCustomAnimations(R.animator.slide_up, R.animator.slide_down);
                            PointInfoFragment fragment = PointInfoFragment.newInstance(pointId);
                            ft.replace(R.id.content_frame, fragment).commit();
                        } catch (NullPointerException e) {
                            e.printStackTrace();
                        }
                    }
                });
                botoEliminarCompta.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {

                        final AlertDialog alertDialog = new AlertDialog.Builder(getActivity().getApplicationContext()).create();
                        alertDialog.setTitle("Segur?");
                        alertDialog.setMessage("Es perdràn tots els punts, minuts i reserves. Aquesta acció no es pot desfer.");

                        alertDialog.setButton(1,"Cancelar", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                alertDialog.dismiss();
                            }
                        });
                        alertDialog.setButton(2,"Continuar", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                //TODO aqui s'ha de cridar la funció que esborri l'usuari
                            }
                        });

                        alertDialog.show();
                    }

                });

            }
        });

        getUserUseCase.setUserId(userId);
        getUserUseCase.execute();
    }
}
