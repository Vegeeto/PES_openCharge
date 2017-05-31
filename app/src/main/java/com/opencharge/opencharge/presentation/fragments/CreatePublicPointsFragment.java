package com.opencharge.opencharge.presentation.fragments;


import android.content.Context;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.view.inputmethod.InputMethodManager;

import com.google.android.gms.maps.model.LatLng;
import com.opencharge.opencharge.R;
import com.opencharge.opencharge.domain.Entities.Point;
import com.opencharge.opencharge.domain.Entities.Reserve;
import com.opencharge.opencharge.domain.helpers.AddressConversion;
import com.opencharge.opencharge.domain.helpers.impl.AddressConversionImpl;
import com.opencharge.opencharge.domain.use_cases.PointsCreateUseCase;
import com.opencharge.opencharge.domain.use_cases.impl.ReserveUserInvolvedUseCaseImpl;
import com.opencharge.opencharge.presentation.locators.UseCasesLocator;

import java.util.ArrayList;
import java.util.List;
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
    private LinearLayout connectorTypeLayout;
    private LinearLayout connectorTypeLayourParent;
    private Button addMoreConnectors;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_new_point, container, false);
        final Button saveButton = (Button) view.findViewById(R.id.GuardarBtn);
        editTown = (EditText) view.findViewById(R.id.Poblacio);
        editStreet = (EditText) view.findViewById(R.id.Street);
        editNumber = (EditText) view.findViewById(R.id.Number);
        editSchedule = (EditText) view.findViewById(R.id.Horari);
        rdgAcces = (RadioGroup) view.findViewById(R.id.Public_or_private);
        connectorTypeLayout = (LinearLayout) view.findViewById(R.id.connector_type_layout);
        addMoreConnectors = (Button) view.findViewById(R.id.add_more_connectors_button);
        connectorTypeLayourParent = (LinearLayout) view.findViewById(R.id.connector_type_parent);

        rdgAcces.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                amagarTeclat();
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Log.d("CrearPunt","onClick Guardar!");
                amagarTeclat();
                guardarPunt();
            }
        });

        final Button cancelButton = (Button) view.findViewById(R.id.CancelarBtn);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                amagarTeclat();
                cancelar();
            }
        });

        RelativeLayout datePickerButton = (RelativeLayout) getActivity().findViewById(R.id.date_picker_button);
        datePickerButton.setVisibility(View.GONE);

        UseCasesLocator useCasesLocator = UseCasesLocator.getInstance();
        Log.d("TESTING", "Declaring the use case");
        ReserveUserInvolvedUseCaseImpl teset = useCasesLocator.getReservesUserInvolvedUseCaseImpl(new ReserveUserInvolvedUseCaseImpl.Callback(){

            @Override
            public void onReservesRetrieved(Reserve[] reserves) {
                Log.d("TESTING", (reserves[0].getId()));
            }
        });
        teset.setPointParameters("-Kkw8SpHrn22Esxgd7F1");
        Log.d("TESTING", "Going to call execute");
        teset.execute();

        return view;
    }
    
    public void guardarPunt() {

        String town = editTown.getText().toString();
        if (town.matches("")) {
            Toast.makeText(getActivity(), "Ha d'indicar la població", Toast.LENGTH_SHORT).show();
            return;
        }
        //Log.d("CrearPunt","town: "+town);
        String street = editStreet.getText().toString();
        if (street.matches("")) {
            Toast.makeText(getActivity(), "Ha d'indicar el carrer", Toast.LENGTH_SHORT).show();
            return;
        }
        //Log.d("CrearPunt","street: "+street);
        String number = editNumber.getText().toString();
        if (number.matches("")) {
            Toast.makeText(getActivity(), "Ha d'indicar el número", Toast.LENGTH_SHORT).show();
            return;
        }
        //Log.d("CrearPunt","num: " +number);
        String schedule = editSchedule.getText().toString();
        if (schedule.matches("")) {
            Toast.makeText(getActivity(), "Ha d'indicar l'horari", Toast.LENGTH_SHORT).show();
            return;
        }
        //Log.d("CrearPunt","sch: "+schedule);

        String accesType;
        switch (rdgAcces.getCheckedRadioButtonId()) {
            case R.id.Particular: accesType = Point.PARTICULAR_ACCESS; break;
            case R.id.Privat: accesType = Point.PRIVATE_ACCESS; break;
            case R.id.Public: accesType = Point.PUBLIC_ACCESS; break;
            default: accesType = Point.UNKNOWN_ACCESS; break;
        }
        if (rdgAcces.getCheckedRadioButtonId() == R.id.Public) {
            accesType = "Public";
        } else if (rdgAcces.getCheckedRadioButtonId() == R.id.Privat) {
            accesType = "Privat";
        } else if (rdgAcces.getCheckedRadioButtonId() == R.id.Particular) {
            accesType = "Particular";
        }
        //Log.d("CrearPunt","accestype: "+accesType);
        int childCount = connectorTypeLayourParent.getChildCount();
        Log.d("SAVE-POINT", "Count: "+childCount);
        int lastIndexInserted = 0;
        List<String> connectorTypeList = new ArrayList<>();
        String connectorType;
        for (int i = 0; i < childCount; i++) {
            LinearLayout linearLayoutChild = (LinearLayout) connectorTypeLayourParent.getChildAt(i);
            for(int j = 0; j < linearLayoutChild.getChildCount(); ++j) {
                RadioGroup rdgbuton = (RadioGroup) linearLayoutChild.getChildAt(j);
                if(rdgbuton instanceof RadioGroup) {
                    switch(rdgbuton.getCheckedRadioButtonId()) {
                        case R.id.Slow: connectorType = Point.SLOW_CONNECTOR; break;
                        case R.id.Fast: connectorType = Point.FAST_CONNECTOR; break;
                        case R.id.Rapid: connectorType = Point.RAPID_CONNECTOR; break;
                        default: connectorType = Point.UNKNOWN_CONNECTOR; break;
                    }
                    connectorTypeList.add(lastIndexInserted, connectorType);
                    ++lastIndexInserted;
                }
            }

        }

        UseCasesLocator useCasesLocator = UseCasesLocator.getInstance();
        PointsCreateUseCase getCreatePointsUseCase = useCasesLocator.getPointsCreateUseCase(new PointsCreateUseCase.Callback(){
            @Override
            public void onPointCreated(String id) {
                //Log.d("CrearPunt","onPointCreatedCallback");
                FragmentManager fm = getFragmentManager();
                PointInfoFragment fragment = PointInfoFragment.newInstance(id);
                fm.beginTransaction().replace(R.id.content_frame, fragment).commit();
            }

        });
        
        Geocoder   geocoder = new Geocoder(getActivity().getApplicationContext(), Locale.getDefault());
        AddressConversion addressConversion = new AddressConversionImpl(geocoder);
        LatLng latlng = addressConversion.AddressToLatLng(town, street, number);
        if (latlng == null) {
            Toast.makeText(getActivity(), "Adreça invàlida", Toast.LENGTH_SHORT).show();
            return;
        }

        getCreatePointsUseCase.setPointParameters(latlng.latitude,latlng.longitude, town,street,number,accesType,connectorTypeList,schedule);
        getCreatePointsUseCase.execute();

    }

    public void cancelar() {
        FragmentManager fm = getFragmentManager();
        MapsFragment mp = new MapsFragment();
        fm.beginTransaction().replace(R.id.content_frame, mp).commit();
    }

    private void amagarTeclat() {
        InputMethodManager inputManager = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }
}
