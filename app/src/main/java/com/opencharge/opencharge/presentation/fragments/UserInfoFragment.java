package com.opencharge.opencharge.presentation.fragments;

//import android.app.Fragment;

import android.app.AlertDialog;
import android.content.Context;
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
import android.widget.Toast;

import com.opencharge.opencharge.R;
import com.opencharge.opencharge.domain.Entities.User;
import com.opencharge.opencharge.domain.use_cases.GetCurrentUserUseCase;
import com.opencharge.opencharge.domain.use_cases.UserByIdUseCase;
import com.opencharge.opencharge.presentation.adapters.CustomUserPointsAdapter;
import com.opencharge.opencharge.presentation.locators.UseCasesLocator;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by DmnT on 17/05/2017.
 */

public class UserInfoFragment extends Fragment {

    private CircleImageView imatgeUsuari;
    private TextView nomUsuari;
    private TextView emailUsuari;
    private TextView minutsUsuari;
    private ListView puntsUsuari;
    private Button botoReservesClient;
    private Button botoReservesProveidor;
    private Button botoEliminarCompta;

    private static final String ARG_USER_ID = "user_id";
    private String userId;
    private User currentUser;

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

    public static UserInfoFragment newInstance() {
        UserInfoFragment fragment = new UserInfoFragment();
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
        imatgeUsuari = (CircleImageView) view.findViewById(R.id.perfil_usuari_imatge);
        nomUsuari = (TextView) view.findViewById(R.id.perfil_usuari_nom2);
        emailUsuari = (TextView) view.findViewById(R.id.perfil_usuari_email2);
        minutsUsuari = (TextView) view.findViewById(R.id.perfil_usuari_minuts2);
        puntsUsuari = (ListView) view.findViewById(R.id.perfil_usuari_punts2);
        botoReservesClient = (Button) view.findViewById(R.id.perfil_usuari_boto_reserves_client);
        botoReservesProveidor = (Button) view.findViewById(R.id.perfil_usuari_boto_reserves_proveidor);
        botoEliminarCompta = (Button) view.findViewById(R.id.perfil_usuari_boto_eliminar);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        UseCasesLocator useCasesLocator = UseCasesLocator.getInstance();
        if (needsToShowCurrentUser()) {

            Context context = getActivity();
            GetCurrentUserUseCase getCurrentUserUseCase = useCasesLocator.getGetCurrentUserUseCase(context, new GetCurrentUserUseCase.Callback() {
                @Override
                public void onCurrentUserRetrieved(User user) {
                    currentUser = user;
                    setUpViewForUser(user);
                }
            });
            getCurrentUserUseCase.execute();

        }
        else {

            //com que es mostrarà el perfil d'un altre usuari, s'amaguen els botons de reserves i d'eliminar usuari
            botoReservesClient.setVisibility(View.GONE);
            botoReservesProveidor.setVisibility(View.GONE);
            botoEliminarCompta.setVisibility(View.GONE);

            UserByIdUseCase userByIdUseCase = useCasesLocator.getUserByIdUseCase(new UserByIdUseCase.Callback() {
                @Override
                public void onUserRetrieved(User user) {
                    setUpViewForUser(user);
                }
            });
            userByIdUseCase.setUserId(userId);
            userByIdUseCase.execute();

        }

    }

    private boolean needsToShowCurrentUser() {
        return (userId == null);
    }

    private void setUpViewForUser(final User user) {
        Picasso.with(getActivity().getApplicationContext()).load(user.getPhoto()).into(imatgeUsuari);
        nomUsuari.setText(user.getUsername());
        emailUsuari.setText(user.getEmail());
        minutsUsuari.setText(user.getMinutes().toString());
        //possible fallo que el context no s'estigui passant correctament?:


        puntsUsuari.setAdapter(new CustomUserPointsAdapter(getActivity().getApplicationContext(), user.getPoints()));

        puntsUsuari.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> listView, View itemView, int itemPosition, long itemId) {
                String pointId = user.getPoints().get(itemPosition).getPointId();
                try {
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    PointInfoFragment fragment = PointInfoFragment.newInstance(pointId);
                    ft.replace(R.id.content_frame, fragment).commit();
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            }
        });

        botoReservesClient.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (currentUser != null) {
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    UserReservesFragment fragment = UserReservesFragment.newInstance(currentUser.getId());
                    ft.replace(R.id.content_frame, fragment).addToBackStack(null).commit();
                }
            }

        });

        botoReservesProveidor.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                SupplierReservesFragment fragment = SupplierReservesFragment.newInstance(currentUser.getId());
                ft.replace(R.id.content_frame, fragment).addToBackStack(null).commit();
            }

        });

        botoEliminarCompta.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                final AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
                alertDialog.setTitle("Segur?");
                alertDialog.setMessage("Es perdràn tots els punts, minuts i reserves. Aquesta acció no es pot desfer.");

                alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        alertDialog.dismiss();
                    }
                });
                alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Continuar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //TODO aqui s'ha de cridar la funció que esborri l'usuari
                        // això és un placeholder per així tenir una resposta, un cop
                        // implementat correctament es pot deixar, o treure
                        Toast toast = Toast.makeText(getActivity().getApplicationContext(), "Usuari eliminat", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                });

                alertDialog.show();
            }

        });

        if (!needsToShowCurrentUser()) {
            botoReservesClient.setVisibility(View.GONE);
            botoReservesProveidor.setVisibility(View.GONE);
            botoEliminarCompta.setVisibility(View.GONE);
        }
    }
}
