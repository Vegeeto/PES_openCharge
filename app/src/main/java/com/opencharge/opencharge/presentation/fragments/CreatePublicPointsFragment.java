package com.opencharge.opencharge.presentation.fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.opencharge.opencharge.R;
import com.opencharge.opencharge.domain.Entities.Point;
import com.opencharge.opencharge.domain.use_cases.PointByIdUseCase;
import com.opencharge.opencharge.domain.use_cases.PointsCreateUseCase;
import com.opencharge.opencharge.presentation.adapters.ItemDecoration;
import com.opencharge.opencharge.presentation.adapters.PointsAdapter;
import com.opencharge.opencharge.presentation.locators.UseCasesLocator;

/**
 * A simple {@link Fragment} subclass.
 */
public class CreatePublicPointsFragment extends Fragment {

    private String MapsFragmentId;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_public_points, container, false);
        final Button saveButton = (Button) view.findViewById(R.id.GuardarBtn);
        saveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                guardarPunt(v);
            }
        });

        final Button cancelButton = (Button) view.findViewById(R.id.GuardarBtn);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                cancelar(v);
            }
        });
        // Inflate the layout for this fragment
        return view;
    }



    public void guardarPunt(View view) {
        String town = ((EditText) view.findViewById(R.id.Poblacio)).getText().toString();
        String street = ((EditText) view.findViewById(R.id.Street)).getText().toString();
        String number = ((EditText) view.findViewById(R.id.Number)).getText().toString();
        String schedule = ((EditText) view.findViewById(R.id.Horari)).getText().toString();
        RadioGroup rdg = (RadioGroup) view.findViewById(R.id.Public_or_private);
        RadioGroup rdgTipus = (RadioGroup) view.findViewById(R.id.tipus_connector);
        String accesType = "unkown";
        if (rdg.getCheckedRadioButtonId() == R.id.Public) {
            accesType = "public";
        } else if (rdg.getCheckedRadioButtonId() == R.id.Privat) {
            accesType = "private";
        } else if (rdg.getCheckedRadioButtonId() == R.id.Particular) {
            accesType = "individual";
        }
        String connectorType = "unkown";
        if (rdgTipus.getCheckedRadioButtonId() == R.id.Slow) {
            connectorType = "slow";
        } else if (rdgTipus.getCheckedRadioButtonId() == R.id.Fast) {
            connectorType = "fast";
        } else if (rdgTipus.getCheckedRadioButtonId() == R.id.Rapid) {
            connectorType = "rapid";
        }
        UseCasesLocator useCasesLocator = UseCasesLocator.getInstance();
        PointsCreateUseCase getCreatePointsUseCase = useCasesLocator.getPointsCreateUseCase(new PointsCreateUseCase.Callback(){
            @Override
            public void onPointCreated(String id) {
                android.app.FragmentManager fm = getFragmentManager();
                PointInfoFragment fragment = PointInfoFragment.newInstance(id);
                fm.beginTransaction().replace(R.id.content_frame, fragment).commit();
            }

        });
        getCreatePointsUseCase.setPointParameters(1.0,1.0, town,street,number,accesType,connectorType,schedule);
        getCreatePointsUseCase.execute();

    }

    public void cancelar(View view) {
        android.app.FragmentManager fm = getFragmentManager();
        MapsFragment mp = new MapsFragment();
        fm.beginTransaction().replace(R.id.content_frame, mp).commit();
    }
}
