package com.opencharge.opencharge.presentation.fragments;

//import android.app.Fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.opencharge.opencharge.R;
import com.opencharge.opencharge.domain.Entities.Point;
import com.opencharge.opencharge.domain.Entities.Reserve;
import com.opencharge.opencharge.domain.Entities.User;
import com.opencharge.opencharge.domain.Entities.UserPointSummary;
import com.opencharge.opencharge.domain.use_cases.DeleteUserUseCase;
import com.opencharge.opencharge.domain.use_cases.GetCurrentUserUseCase;
import com.opencharge.opencharge.domain.use_cases.PointByIdUseCase;
import com.opencharge.opencharge.domain.use_cases.ReservesUserInvolvedUseCase;
import com.opencharge.opencharge.domain.use_cases.UserByIdUseCase;
import com.opencharge.opencharge.presentation.activities.NavigationActivity;
import com.opencharge.opencharge.presentation.activities.SignInActivity;
import com.opencharge.opencharge.presentation.adapters.CustomUserPointsAdapter;
import com.opencharge.opencharge.presentation.locators.UseCasesLocator;
import com.squareup.picasso.Picasso;


import java.util.List;

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
    public static UserInfoFragment newInstance(String userId) {
        UserInfoFragment fragment = new UserInfoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_USER_ID, userId);
        fragment.setArguments(args);

        return fragment;
    }

    public static UserInfoFragment newInstance() {
        return new UserInfoFragment();
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

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("El meu perfil");

        imatgeUsuari = (CircleImageView) view.findViewById(R.id.perfil_usuari_imatge);
        nomUsuari = (TextView) view.findViewById(R.id.perfil_usuari_nom);
        emailUsuari = (TextView) view.findViewById(R.id.perfil_usuari_email);
        minutsUsuari = (TextView) view.findViewById(R.id.perfil_usuari_minuts);
        puntsUsuari = (ListView) view.findViewById(R.id.perfil_usuari_punts);
        botoReservesClient = (Button) view.findViewById(R.id.perfil_usuari_boto_reserves_client);
        botoReservesProveidor = (Button) view.findViewById(R.id.perfil_usuari_boto_reserves_proveidor);
        botoEliminarCompta = (Button) view.findViewById(R.id.perfil_usuari_boto_eliminar);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final UseCasesLocator useCasesLocator = UseCasesLocator.getInstance();
                GetCurrentUserUseCase getCurrentUserUseCase = useCasesLocator.getGetCurrentUserUseCase(getActivity(), new GetCurrentUserUseCase.Callback() {
                    @Override
                    public void onCurrentUserRetrieved(final User user) {
                        if (user.getId().equals(userId)) {
                            currentUser = user;
                            setUpViewForUser(user);
                        } else {
                            //Com que es mostrarà el perfil d'un altre usuari, s'amaguen els botons de reserves i d'eliminar usuari
                            UserByIdUseCase userByIdUseCase = useCasesLocator.getUserByIdUseCase(new UserByIdUseCase.Callback() {
                            @Override
                            public void onUserRetrieved(User user) {
                                setUpViewForUser(user);
                                botoReservesClient.setVisibility(View.GONE);
                                botoReservesProveidor.setVisibility(View.GONE);
                                botoEliminarCompta.setVisibility(View.GONE);
                        }
                    });
                    userByIdUseCase.setUserId(userId);
                    userByIdUseCase.execute();
                }

            }
        });
        getCurrentUserUseCase.execute();
    }

    private void setUpViewForUser(final User user) {
        Picasso.with(getActivity().getApplicationContext()).load(user.getPhoto()).into(imatgeUsuari);
        nomUsuari.setText(user.getUsername());
        emailUsuari.setText(user.getEmail());
        minutsUsuari.setText(user.getMinutes().toString());

        final CustomUserPointsAdapter customUserPointsAdapter = new CustomUserPointsAdapter(getActivity().getApplicationContext(), user.getPoints());
        puntsUsuari.setAdapter(customUserPointsAdapter);
        setListViewHeightBasedOnChildren(puntsUsuari);

        puntsUsuari.setOnTouchListener(new View.OnTouchListener() {
            // Setting on Touch Listener for handling the touch inside ScrollView
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // Disallow the touch request for parent scroll on touch of child view
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });

        //Log.e("TAG: ", "" + customUserPointsAdapter.size());

        puntsUsuari.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> listView, View itemView, int itemPosition, long itemId) {
                try {
                    String pointId = customUserPointsAdapter.getPointID(itemPosition);
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    PointInfoFragment fragment = PointInfoFragment.newInstance(pointId);
                    ft.replace(R.id.content_frame, fragment).addToBackStack(null).commit();
                } catch (NullPointerException e) {
                    Toast.makeText(getActivity().getApplicationContext(), "El punt ha estat eliminat del sistema", Toast.LENGTH_SHORT).show();
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
                alertDialog.setTitle("Segur que vols esborrar el teu compte d'usuari?");
                alertDialog.setIcon(R.drawable.ic_warning_black_24dp);
                alertDialog.setMessage("S'esborraran tots els teus punts i reserves. Aquesta acció no es pot desfer.");

                alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        alertDialog.dismiss();
                    }
                });
                alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Continuar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        final UseCasesLocator useCasesLocator = UseCasesLocator.getInstance();
                        GetCurrentUserUseCase getCreateUsersUseCase = useCasesLocator.getGetCurrentUserUseCase(getActivity(), new GetCurrentUserUseCase.Callback() {
                            @Override
                            public void onCurrentUserRetrieved(User currentUser) {
                                List<UserPointSummary> points = currentUser.getPoints();
                                for (UserPointSummary point : points) {
                                    PointByIdUseCase pointByIdUseCase = useCasesLocator.getPointByIdUseCase(new PointByIdUseCase.Callback() {
                                        @Override
                                        public void onPointRetrieved(Point point) {
                                            if (point.getAccessType().equals(Point.PARTICULAR_ACCESS))
                                                FirebaseDatabase.getInstance().getReference("Points").child(point.getId()).removeValue();
                                        }
                                    });
                                    pointByIdUseCase.setPointId(point.getPointId());
                                    pointByIdUseCase.execute();
                                }
                                FirebaseDatabase.getInstance().getReference("Users").child(currentUser.getId()).removeValue();
                                ReservesUserInvolvedUseCase reservesUserInvolvedUseCase = useCasesLocator.getReservesUserInvolvedUseCaseImpl(new ReservesUserInvolvedUseCase.Callback() {
                                    @Override
                                    public void onReservesRetrieved(Reserve[] reserves) {
                                        for (Reserve reserve : reserves) {
                                            FirebaseDatabase.getInstance().getReference("Reserves").child(reserve.getId()).removeValue();
                                        }
                                        Toast.makeText(getActivity().getApplicationContext(), "Usuari eliminat", Toast.LENGTH_SHORT).show();
                                    }
                                });
                                reservesUserInvolvedUseCase.setPointParameters(currentUser.getId());
                                reservesUserInvolvedUseCase.execute();
                            }
                        });
                        getCreateUsersUseCase.execute();
                        signOut();

                    }
                });

                alertDialog.show();
            }

        });
    }


    /**** Method for Setting the Height of the ListView dynamically.
     **** Hack to fix the issue of not showing all the items of the ListView
     **** when placed inside a ScrollView  ****/
    private static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }

        int totalHeight = listView.getPaddingTop() + listView.getPaddingBottom();

        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            if (listItem instanceof ViewGroup) {
                listItem.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
            }

            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

    private void signOut() {
        Intent intent = new Intent(getActivity(), SignInActivity.class);
        startActivity(intent);
    }
}
