package com.opencharge.opencharge.presentation.fragments;

import android.app.DatePickerDialog;
import android.app.Fragment;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import com.opencharge.opencharge.R;
import com.opencharge.opencharge.domain.Entities.Service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Oriol on 5/5/2017.
 */

/**
 * A simple {@link Fragment} subclass.
 */
public class CreateServiceFragment extends Fragment {

    final Calendar calendar = Calendar.getInstance();
    int year, month, day, hour, min;
    private EditText date;
    private EditText dateEnd;
    private EditText inici;
    private EditText fi;

    public CreateServiceFragment() {
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        min = calendar.get(Calendar.MINUTE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_service, container, false);

        date = (EditText) view.findViewById(R.id.date);
        date.setInputType(InputType.TYPE_NULL);
        date.setFocusable(false);
        showDate(year, month, day, date);

        dateEnd = (EditText) view.findViewById(R.id.dateEnd);
        dateEnd.setFocusable(false);
        dateEnd.setInputType(InputType.TYPE_NULL);

        inici = (EditText) view.findViewById(R.id.ini);
        inici.setFocusable(false);
        inici.setInputType(InputType.TYPE_NULL);
        
        fi = (EditText) view.findViewById(R.id.fi);
        fi.setFocusable(false);
        fi.setInputType(InputType.TYPE_NULL);

        Button save = (Button) view.findViewById(R.id.saveBtn);
        final Button cancel = (Button) view.findViewById(R.id.cancelBtn);

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

        inici.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog timePicker = new TimePickerDialog(getActivity(), timePickerListener1, hour+1, 0, true);
                createTimePicker(timePicker);
            }
        });

        fi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog timePicker = new TimePickerDialog(getActivity(), timePickerListener2, hour+2, 0, true);
                createTimePicker(timePicker);
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                save();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideInput();
                cancel();
            }
        });

        return view;
    }

    private  DatePickerDialog.OnDateSetListener datePickerListener1 = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
            showDate(i, i1, i2, date);
        }
    };

    private  DatePickerDialog.OnDateSetListener datePickerListener2 = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
            showDate(i, i1, i2, dateEnd);
        }
    };

    private void createTimePicker(TimePickerDialog timePicker) {
        timePicker.setCancelable(true);
        timePicker.setTitle("Seleccionar hora");
        timePicker.show();
    }

    private TimePickerDialog.OnTimeSetListener timePickerListener1 = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker timePicker, int i, int i1) {
            showTime(i, i1, inici);
        }
    };

    private TimePickerDialog.OnTimeSetListener timePickerListener2 = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker timePicker, int i, int i1) {
            showTime(i, i1, fi);
        }
    };

    private void showDate(int year, int month, int day, EditText text) {
        text.setText(day + "/" + (month+1) + "/" + year);
    }

    private void showTime(int h, int m, EditText text) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        String time = simpleDateFormat.format(new Date(0, 0, 0, h, m));
        text.setText(time);
    }

    private void save() {
        //Service s = new Service();
    }

    private void cancel() {
        android.app.FragmentManager fm = getFragmentManager();
        MapsFragment mp = new MapsFragment();
        fm.beginTransaction().replace(R.id.content_frame, mp).commit();
    }

    private void hideInput() {
        InputMethodManager inputManager = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

}
