package com.opencharge.opencharge.presentation.fragments;


import android.content.Context;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.opencharge.opencharge.R;
import com.opencharge.opencharge.domain.Entities.Point;
import com.opencharge.opencharge.domain.helpers.AddressConversion;
import com.opencharge.opencharge.domain.helpers.impl.AddressConversionImpl;
import com.opencharge.opencharge.domain.use_cases.PointByIdUseCase;
import com.opencharge.opencharge.domain.use_cases.PointsCreateUseCase;
import com.opencharge.opencharge.presentation.adapters.ItemDecoration;
import com.opencharge.opencharge.presentation.adapters.PointsAdapter;
import com.opencharge.opencharge.presentation.locators.UseCasesLocator;

import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class CreatePublicPointsFragment extends Fragment {

    private String MapsFragmentId;
    private EditText editTown;
    private EditText editStreet;
    private EditText editNumber;
    private EditText editSchedule;
    private RadioGroup rdgAcces;
    private RadioGroup rdgTipus;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_public_points, container, false);
        final Button saveButton = (Button) view.findViewById(R.id.GuardarBtn);
        editTown = (EditText) view.findViewById(R.id.Poblacio);
        editStreet = (EditText) view.findViewById(R.id.Street);
        editNumber = (EditText) view.findViewById(R.id.Number);
        editSchedule = (EditText) view.findViewById(R.id.Horari);
        rdgAcces = (RadioGroup) view.findViewById(R.id.Public_or_private);
        rdgTipus = (RadioGroup) view.findViewById(R.id.tipus_connector);


        saveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Log.d("CrearPunt","onClick Guardar!");
                guardarPunt(v);
            }
        });

        final Button cancelButton = (Button) view.findViewById(R.id.CancelarBtn);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                cancelar(v);
            }
        });
        // Inflate the layout for this fragment
        return view;
    }



    public void guardarPunt(View view) {

        String town = editTown.getText().toString();
        if (town.matches("")) {
            Toast.makeText(getActivity(), "No has indicat la poblacio", Toast.LENGTH_SHORT).show();
            return;
        }
        //Log.d("CrearPunt","town: "+town);
        String street = editStreet.getText().toString();
        if (street.matches("")) {
            Toast.makeText(getActivity(), "No has indicat el carrer", Toast.LENGTH_SHORT).show();
            return;
        }
        //Log.d("CrearPunt","street: "+street);
        String number = editNumber.getText().toString();
        if (number.matches("")) {
            Toast.makeText(getActivity(), "No has indicat el numero", Toast.LENGTH_SHORT).show();
            return;
        }
        //Log.d("CrearPunt","num: " +number);
        String schedule = editSchedule.getText().toString();
        if (schedule.matches("")) {
            Toast.makeText(getActivity(), "No has indicat el horari", Toast.LENGTH_SHORT).show();
            return;
        }
        //Log.d("CrearPunt","sch: "+schedule);

        String accesType = "unkown";
        if (rdgAcces.getCheckedRadioButtonId() == R.id.Public) {
            accesType = "public";
        } else if (rdgAcces.getCheckedRadioButtonId() == R.id.Privat) {
            accesType = "private";
        } else if (rdgAcces.getCheckedRadioButtonId() == R.id.Particular) {
            accesType = "individual";
        }
        //Log.d("CrearPunt","accestype: "+accesType);
        String connectorType = "unkown";
        if (rdgTipus.getCheckedRadioButtonId() == R.id.Slow) {
            connectorType = "slow";
        } else if (rdgTipus.getCheckedRadioButtonId() == R.id.Fast) {
            connectorType = "fast";
        } else if (rdgTipus.getCheckedRadioButtonId() == R.id.Rapid) {
            connectorType = "rapid";
        }
        //Log.d("CrearPunt","connector: "+connectorType);
        //Log.d("CrearPunt","Pre llamada usecase");
        UseCasesLocator useCasesLocator = UseCasesLocator.getInstance();
        PointsCreateUseCase getCreatePointsUseCase = useCasesLocator.getPointsCreateUseCase(new PointsCreateUseCase.Callback(){
            @Override
            public void onPointCreated(String id) {
                //Log.d("CrearPunt","onPointCreatedCallback");
                android.app.FragmentManager fm = getFragmentManager();
                PointInfoFragment fragment = PointInfoFragment.newInstance(id);
                fm.beginTransaction().replace(R.id.content_frame, fragment).commit();
            }

        });
        Geocoder   geocoder = new Geocoder(getActivity().getApplicationContext(), Locale.getDefault());
        AddressConversion addressConversion = new AddressConversionImpl(geocoder);
        LatLng latlng = addressConversion.AddressToLatLng(town, street, number);
        getCreatePointsUseCase.setPointParameters(latlng.latitude,latlng.longitude, town,street,number,accesType,connectorType,schedule);
        //getCreatePointsUseCase.setPointParameters(1.0,1.0, "a","a","a","a","a","a");
        getCreatePointsUseCase.execute();

    }

    public void cancelar(View view) {
        android.app.FragmentManager fm = getFragmentManager();
        MapsFragment mp = new MapsFragment();
        fm.beginTransaction().replace(R.id.content_frame, mp).commit();
    }
}
