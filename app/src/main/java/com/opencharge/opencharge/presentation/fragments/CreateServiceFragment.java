package com.opencharge.opencharge.presentation.fragments;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.text.InputType;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TimePicker;
import android.widget.Toast;

import com.opencharge.opencharge.R;
import com.opencharge.opencharge.domain.Entities.Service;
import com.opencharge.opencharge.domain.helpers.DateConversion;
import com.opencharge.opencharge.domain.helpers.impl.DateConversionImpl;
import com.opencharge.opencharge.domain.use_cases.ServiceCreateUseCase;
import com.opencharge.opencharge.presentation.locators.UseCasesLocator;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Oriol on 5/5/2017.
 */

/**
 * A simple {@link Fragment} subclass.
 */
public class CreateServiceFragment extends Fragment implements CheckBox.OnCheckedChangeListener  {

    final Calendar calendar = Calendar.getInstance();
    int year, month, day, hour, min;
    private EditText date;
    private EditText dateEnd;
    private EditText inici;
    private EditText fi;
    private CheckBox mon;
    private CheckBox tue;
    private CheckBox wed;
    private CheckBox thu;
    private CheckBox fri;
    private CheckBox sat;
    private CheckBox sun;
    private LinearLayout endLayout;

    private static final String ARG_POINT_ID = "point_id";
    private String pointId;
    private static final String ARG_DAY = "day";
    private String day_arg;
    private static final String ARG_START_TIME = "start_time";
    private String start_arg;

    Service s;

    public CreateServiceFragment() {
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        min = calendar.get(Calendar.MINUTE);
        s = new Service();
    }

    public static CreateServiceFragment newInstance(String pointId, String date, String start) {
        CreateServiceFragment fragment = new CreateServiceFragment();
        Bundle args = new Bundle();
        args.putString(ARG_POINT_ID, pointId);
        args.putString(ARG_DAY, date);
        args.putString(ARG_START_TIME, start);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.pointId = getArguments().getString(ARG_POINT_ID);
            this.day_arg = getArguments().getString(ARG_DAY);
            this.start_arg = getArguments().getString(ARG_START_TIME);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_service, container, false);

        date = (EditText) view.findViewById(R.id.date);
        date.setInputType(InputType.TYPE_NULL);
        date.setFocusable(false);
        date.setText(day_arg);

        dateEnd = (EditText) view.findViewById(R.id.dateEnd);
        dateEnd.setFocusable(false);
        dateEnd.setInputType(InputType.TYPE_NULL);

        inici = (EditText) view.findViewById(R.id.ini);
        inici.setFocusable(false);
        inici.setInputType(InputType.TYPE_NULL);
        inici.setText(start_arg);
        
        fi = (EditText) view.findViewById(R.id.fi);
        fi.setFocusable(false);
        fi.setInputType(InputType.TYPE_NULL);

        endLayout = (LinearLayout) view.findViewById(R.id.endLayout);

        mon = (CheckBox) view.findViewById(R.id.mon);
        tue = (CheckBox) view.findViewById(R.id.tue);
        wed = (CheckBox) view.findViewById(R.id.wed);
        thu = (CheckBox) view.findViewById(R.id.thu);
        fri = (CheckBox) view.findViewById(R.id.fri);
        sat = (CheckBox) view.findViewById(R.id.sat);
        sun = (CheckBox) view.findViewById(R.id.sun);

        mon.setOnCheckedChangeListener(this);
        tue.setOnCheckedChangeListener(this);
        wed.setOnCheckedChangeListener(this);
        thu.setOnCheckedChangeListener(this);
        fri.setOnCheckedChangeListener(this);
        sat.setOnCheckedChangeListener(this);
        tue.setOnCheckedChangeListener(this);
        sun.setOnCheckedChangeListener(this);

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
                TimePickerDialog timePicker = new TimePickerDialog(getActivity(), timePickerListener2, hour+2, 0, true) {
                    @Override
                    public void updateTime(int hourOfDay, int minuteOfHour) {
                        minuteOfHour = + 15;
                        super.updateTime(hourOfDay, minuteOfHour);
                    }
                };
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
                cancel();
            }
        });

        RelativeLayout datePickerButton = (RelativeLayout) getActivity().findViewById(R.id.date_picker_button);
        datePickerButton.setVisibility(View.GONE);

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
        UseCasesLocator useCasesLocator = UseCasesLocator.getInstance();
        ServiceCreateUseCase getServiceCreateUseCase = useCasesLocator.getServiceCreateUseCase(new ServiceCreateUseCase.Callback() {
            @Override
            public void onServiceCreated() {
                FragmentManager fm = getFragmentManager();
                PointInfoFragment fragment = PointInfoFragment.newInstance(pointId);
                fm.beginTransaction().replace(R.id.content_frame, fragment).commit();
            }

            @Override
            public void onError() {
                Toast.makeText(getActivity(), "Hi ha hagut algun error al guardar les dades!", Toast.LENGTH_SHORT).show();
            }
        });

        String day = date.getText().toString();
        if (day.isEmpty()) {
            Toast.makeText(getActivity(), "Ha d'indicar el dia de la reserva!", Toast.LENGTH_SHORT).show();
            return;
        }
        String startHour = inici.getText().toString();
        if (startHour.isEmpty()) {
            Toast.makeText(getActivity(), "Ha d'indicar l'hora d'inici!", Toast.LENGTH_SHORT).show();
            return;
        }
        String endHour = fi.getText().toString();
        if (endHour.isEmpty()) {
            Toast.makeText(getActivity(), "Ha d'indicar l'hora de finalització!", Toast.LENGTH_SHORT).show();
            return;
        }

        DateConversion dateConversion = new DateConversionImpl();
        Date startDay = dateConversion.ConvertStringToDate(day);
        Date startTime = dateConversion.ConvertStringToTime(startHour);
        Date endTime = dateConversion.ConvertStringToTime(endHour);

        if (endTime.before(startTime)) {
            Toast.makeText(getActivity(), "Hora de finalització incorrecte!", Toast.LENGTH_SHORT).show();
            return;
        }

        getServiceCreateUseCase.setServiceParameters(pointId, startDay, startTime, endTime);

        String endRepeat = dateEnd.getText().toString();
        if (!endRepeat.isEmpty()) {

            Date lastRepeat = dateConversion.ConvertStringToDate(endRepeat);
            if (lastRepeat.before(startDay)) {
                Toast.makeText(getActivity(), "Data de finalització incorrecte!", Toast.LENGTH_SHORT).show();
                return;
            }

            getServiceCreateUseCase.setLastRepeat(lastRepeat);

            boolean selected = false;
            if (mon.isChecked()) {
                selected = true;
                getServiceCreateUseCase.setRepeatMonday();
            }
            if (tue.isChecked()) {
                selected = true;
                getServiceCreateUseCase.setRepeatTuesday();
            }
            if (wed.isChecked()) {
                selected = true;
                getServiceCreateUseCase.setRepeatWednesday();
            }
            if (thu.isChecked()) {
                selected = true;
                getServiceCreateUseCase.setRepeatThursday();
            }
            if (fri.isChecked()) {
                selected = true;
                getServiceCreateUseCase.setRepeatFriday();
            }
            if (sat.isChecked()) {
                selected = true;
                getServiceCreateUseCase.setRepeatSaturday();
            }
            if (sun.isChecked()) {
                selected = true;
                getServiceCreateUseCase.setRepeatSunday();
            }

            if (!selected) {
                Toast.makeText(getActivity(), "Seleccioni quins dies vol repetir", Toast.LENGTH_SHORT).show();
                return;
            }

        } else if (mon.isChecked() || tue.isChecked() || wed.isChecked() || thu.isChecked() || fri.isChecked() || sat.isChecked() || sun.isChecked()) {
            Toast.makeText(getActivity(), "Ha d'indicar data de finalització de repeticions!", Toast.LENGTH_SHORT).show();
            return;
        }

        getServiceCreateUseCase.execute();
    }

    private void cancel() {
        FragmentManager fm = getFragmentManager();
        fm.popBackStackImmediate();
        /*MapsFragment mp = new MapsFragment();
        fm.beginTransaction().replace(R.id.content_frame, mp).commit();*/
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        if (!mon.isChecked() && !tue.isChecked() && !wed.isChecked() && !thu.isChecked() && !fri.isChecked() && !sat.isChecked() && !sun.isChecked()) {
            endLayout.setVisibility(View.INVISIBLE);
        } else {
            endLayout.setVisibility(View.VISIBLE);
        }
    }

}
