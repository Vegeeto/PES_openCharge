package com.opencharge.opencharge.presentation.fragments;

//import android.app.Fragment;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.opencharge.opencharge.R;
import com.opencharge.opencharge.domain.Entities.User;
import com.opencharge.opencharge.domain.use_cases.UserByIdUseCase;
import com.opencharge.opencharge.presentation.adapters.CustomUserPointsAdapter;
import com.opencharge.opencharge.presentation.adapters.PointsAdapter;
import com.opencharge.opencharge.presentation.locators.UseCasesLocator;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

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
    //private ListView puntsResUsuari;
    private Button botoReservesClient;
    private Button botoReservesProveidor;
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
        minutsUsuari = (TextView)view.findViewById(R.id.perfil_usuari_minuts2);
        puntsUsuari = (ListView)view.findViewById(R.id.perfil_usuari_punts2);
        //puntsResUsuari = (ListView)view.findViewById(R.id.perfil_usuari_puntsReser2);
        botoReservesClient = (Button)view.findViewById(R.id.perfil_usuari_boto_reserves_client);
        botoReservesProveidor = (Button)view.findViewById(R.id.perfil_usuari_boto_reserves_proveidor);
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

                Picasso.with(getActivity().getApplicationContext()).load(user.getPhoto()).into(imatgeUsuari);
                nomUsuari.setText(user.getUsername());
                emailUsuari.setText(user.getEmail());
                minutsUsuari.setText(user.getMinutes().toString());
                //possible fallo que el context no s'estigui passant correctament?:


                /*
                ArrayList<String> nomsPunts = new ArrayList<String>();

                for(int x=0;x<user.getPunts().size();x=x+1){
                    nomsPunts.add(user.getPunts().get(x).second);
                }
                Log.i("nomsPunts",nomsPunts.toString());

                ArrayAdapter<String> arrayAdapter1 = new ArrayAdapter<String>(
                        getActivity().getApplicationContext(),
                        android.R.layout.simple_list_item_1,
                        nomsPunts );

                puntsUsuari.setAdapter(arrayAdapter1);
                */


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


                /*
                ArrayList<String> nomsPuntsRes = new ArrayList<String>();

                String puntsResSize = ""+user.getPuntsReservats().size();
                Log.i("Mida puntsRes",puntsResSize);

                for(int x=0;x<user.getPuntsReservats().size();x=x+1){
                    String striX = ""+x;
                    Log.i("Afegint punt amb index",striX);
                    nomsPuntsRes.add(user.getPuntsReservats().get(x).second);
                    Log.i("El nom es",user.getPuntsReservats().get(x).second);
                }

                Log.i("nomsPuntsRes",nomsPuntsRes.toString());

                ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<String>(
                        getActivity().getApplicationContext(),
                        android.R.layout.simple_list_item_1,
                        nomsPuntsRes );

                puntsResUsuari.setAdapter(arrayAdapter2);
                */


                //aquesta és la part que mostrava els punts on l'usuari tenia una reserva
                // es pot eliminar si es considera oportú
                /*
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
                */

                botoReservesClient.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        //aquí s'obrirà el fragment amb les reserves que l'usuari ha fet a punts d'altres usuaris
                        Toast toast = Toast.makeText(getActivity().getApplicationContext(), "Reserves com a client", Toast.LENGTH_SHORT);
                        toast.show();
                    }

                });

                botoReservesProveidor.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        //aquí s'obrirà el fragment amb les reserves que altres usuaris han fet a punts de l'usuari
                        Toast toast = Toast.makeText(getActivity().getApplicationContext(), "Reserves com a provider", Toast.LENGTH_SHORT);
                        toast.show();
                    }

                });


                botoEliminarCompta.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {

                        final AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
                        alertDialog.setTitle("Segur?");
                        alertDialog.setMessage("Es perdràn tots els punts, minuts i reserves. Aquesta acció no es pot desfer.");

                        alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE,"Cancelar", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                alertDialog.dismiss();
                            }
                        });
                        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE,"Continuar", new DialogInterface.OnClickListener() {
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

            }
        });

        getUserUseCase.setUserId(userId);
        getUserUseCase.execute();
    }
}
