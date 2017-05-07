package com.opencharge.opencharge.presentation.fragments;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Fragment;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;

import com.opencharge.opencharge.R;

import java.util.Calendar;

/**
 * Created by Oriol on 5/5/2017.
 */

/**
 * A simple {@link Fragment} subclass.
 */
public class CreateServiceFragment extends Fragment {

    final Calendar calendar = Calendar.getInstance();
    int year;
    int month;
    int day;
    private EditText date;
    private EditText dateEnd;
    private EditText

    public CreateServiceFragment() {
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_service, container, false);

        date = (EditText) view.findViewById(R.id.date);
        date.setInputType(InputType.TYPE_NULL);
        date.requestFocus();
        showDate(date);

        dateEnd = (EditText) view.findViewById(R.id.dateEnd);
        dateEnd.setFocusable(false);
        dateEnd.setInputType(InputType.TYPE_NULL);

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePicker = new DatePickerDialog(getActivity(),datePickerListener1, year, month, day);
                datePicker.setCancelable(false);
                datePicker.setTitle("Seleccionar data");
                datePicker.show();
            }
        });

        dateEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePicker = new DatePickerDialog(getActivity(), datePickerListener2, year, month, day);
                datePicker.setCancelable(true);
                datePicker.setTitle("Seleccionar data");
                datePicker.show();
            }
        });



        return view;
    }

    private  DatePickerDialog.OnDateSetListener datePickerListener1 = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
            setDate(i, i1, i2);
            showDate(date);
        }
    };

    private  DatePickerDialog.OnDateSetListener datePickerListener2 = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
            setDate(i, i1, i2);
            showDate(dateEnd);
        }
    };

    private void setDate(int i, int i1, int i2) {
        year = i;
        month = i1;
        day = i2;
    }

    private void showDate(EditText text) {
        text.setText(day + "/" + (month+1) + "/" + year);
    }

}
