package com.opencharge.opencharge.presentation.fragments;


import android.content.Context;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.view.inputmethod.InputMethodManager;

import com.google.android.gms.maps.model.LatLng;
import com.opencharge.opencharge.R;
import com.opencharge.opencharge.domain.Entities.Point;
import com.opencharge.opencharge.domain.helpers.AddressConversion;
import com.opencharge.opencharge.domain.helpers.impl.AddressConversionImpl;
import com.opencharge.opencharge.domain.use_cases.PointsCreateUseCase;
import com.opencharge.opencharge.presentation.locators.UseCasesLocator;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class CreatePublicPointsFragment extends Fragment {

    private EditText editTown;
    private EditText editStreet;
    private EditText editNumber;
    private EditText editSchedule;
    private RadioGroup rdgAcces;
    private LinearLayout connectorTypeLayourParent;

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_new_point, container, false);

        editTown = (EditText) view.findViewById(R.id.Poblacio);
        editStreet = (EditText) view.findViewById(R.id.Street);
        editNumber = (EditText) view.findViewById(R.id.Number);
        editSchedule = (EditText) view.findViewById(R.id.Horari);
        rdgAcces = (RadioGroup) view.findViewById(R.id.Public_or_private);
        connectorTypeLayourParent = (LinearLayout) view.findViewById(R.id.connector_type_parent);

        rdgAcces.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                hideKeyboard();
            }
        });

        Button saveButton = (Button) view.findViewById(R.id.GuardarBtn);
        saveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Log.d("CrearPunt","onClick Guardar!");
                hideKeyboard();
                guardarPunt();
            }
        });

        Button cancelButton = (Button) view.findViewById(R.id.CancelarBtn);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                hideKeyboard();
                cancel();
            }
        });

        RelativeLayout datePickerButton = (RelativeLayout) getActivity().findViewById(R.id.date_picker_button);
        datePickerButton.setVisibility(View.GONE);

        final LinearLayout scheduleLayout = (LinearLayout) view.findViewById(R.id.horari_layout);
        scheduleLayout.setVisibility(View.GONE);
        rdgAcces.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                switch (radioGroup.getCheckedRadioButtonId()) {
                    case R.id.Privat: scheduleLayout.setVisibility(View.VISIBLE); break;
                    case R.id.Public: scheduleLayout.setVisibility(View.VISIBLE); break;
                    default: scheduleLayout.setVisibility(View.GONE); break;
                }
            }
        });

        ImageButton addMoreConnectors = (ImageButton) view.findViewById(R.id.add_more_connectors_button);
        addMoreConnectors.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Duplicar layout
                LayoutInflater inflater = (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View view = inflater.inflate(R.layout.radiogroup, null);
                connectorTypeLayourParent.addView(view);
            }
        });

        return view;
    }
    
    public void guardarPunt() {

        String town = editTown.getText().toString();
        if (town.matches("")) {
            Toast.makeText(getActivity(), "Ha d'indicar la població", Toast.LENGTH_SHORT).show();
            return;
        }

        String street = editStreet.getText().toString();
        if (street.matches("")) {
            Toast.makeText(getActivity(), "Ha d'indicar el carrer", Toast.LENGTH_SHORT).show();
            return;
        }

        String number = editNumber.getText().toString();
        if (number.matches("")) {
            Toast.makeText(getActivity(), "Ha d'indicar el número", Toast.LENGTH_SHORT).show();
            return;
        }

        String accesType;
        String schedule = editSchedule.getText().toString();
        switch (rdgAcces.getCheckedRadioButtonId()) {
            case R.id.Particular: accesType = Point.PARTICULAR_ACCESS; break;
            case R.id.Privat: accesType = Point.PRIVATE_ACCESS;
                if (schedule.matches("")) {
                    Toast.makeText(getActivity(), "Ha d'indicar l'horari", Toast.LENGTH_SHORT).show();
                    return;
                }
                break;
            case R.id.Public: accesType = Point.PUBLIC_ACCESS;
                if (schedule.matches("")) {
                    Toast.makeText(getActivity(), "Ha d'indicar l'horari", Toast.LENGTH_SHORT).show();
                    return;
                }
                break;
            default: accesType = Point.UNKNOWN_ACCESS; break;
        }


        int childCount = connectorTypeLayourParent.getChildCount();
        int lastIndexInserted = 0;
        List<String> connectorTypeList = new ArrayList<>();
        String connectorType;
        for (int i = 0; i < childCount; i++) {
            LinearLayout linearLayoutChild = (LinearLayout) connectorTypeLayourParent.getChildAt(i);
            for(int j = 0; j < linearLayoutChild.getChildCount(); ++j) {
                RadioGroup rdgbuton = (RadioGroup) linearLayoutChild.getChildAt(j);
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

        UseCasesLocator useCasesLocator = UseCasesLocator.getInstance();
        PointsCreateUseCase getCreatePointsUseCase = useCasesLocator.getPointsCreateUseCase(getActivity(), new PointsCreateUseCase.Callback(){
            @Override
            public void onPointCreated(String id) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                PointInfoFragment fragment = PointInfoFragment.newInstance(id);
                fm.beginTransaction().replace(R.id.content_frame, fragment).commit();
            }

        });
        
        Geocoder geocoder = new Geocoder(getActivity().getApplicationContext(), Locale.getDefault());
        AddressConversion addressConversion = new AddressConversionImpl(geocoder);
        LatLng latlng = addressConversion.AddressToLatLng(town, street, number);
        if (latlng == null) {
            Toast.makeText(getActivity(), "Adreça invàlida", Toast.LENGTH_SHORT).show();
            return;
        }

        getCreatePointsUseCase.setPointParameters(latlng.latitude,latlng.longitude, town,street,number,accesType,connectorTypeList, schedule);
        getCreatePointsUseCase.execute();

    }

    public void cancel() {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        MapsFragment mp = new MapsFragment();
        fm.beginTransaction().replace(R.id.content_frame, mp).commit();
    }

    private void hideKeyboard() {
        InputMethodManager inputManager = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }
}
