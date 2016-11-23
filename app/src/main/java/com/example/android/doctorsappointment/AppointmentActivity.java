package com.example.android.doctorsappointment;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class AppointmentActivity extends AppCompatActivity implements View.OnClickListener {
    DoctorActivity doctorActivity;
    Button btvAppointmentSend;
    EditText etAppointmentDate;
    EditText etAppointmentTime;
    TextView tvDoctorName;
    Calendar myCalendar;
    Spinner spnAmPm;
    private int mYear, mMonth, mDay, mHour, mMinute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);
        myCalendar = Calendar.getInstance();

        btvAppointmentSend = (Button) findViewById(R.id.btnAppointmentSubmit);
        etAppointmentDate = (EditText) findViewById(R.id.etAppointmentDate);
        etAppointmentTime = (EditText) findViewById(R.id.etAppointmentTime);
        tvDoctorName = (TextView) findViewById(R.id.tvDoctorNameFromLV);
        //spnAmPm = (Spinner) findViewById(R.id.spnrAMPM);

        String doctorName = getIntent().getStringExtra("doctorName");
        tvDoctorName.setText(doctorName);

        etAppointmentDate.setOnClickListener(this);
        etAppointmentTime.setOnClickListener(this);

        btvAppointmentSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AppointmentActivity.this, "Data send successfully", Toast.LENGTH_SHORT).show();
                etAppointmentDate.setText(" ");
                //etAppointmentTime.setText(R.string.time);
                etAppointmentTime.setText(" ");
            }
        });

    }


    @Override
    public void onClick(View v) {
        if (v == etAppointmentDate) {

            // Get Current Date
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {

                            etAppointmentDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }
        if (v == etAppointmentTime) {

            // Get Current Time
            final Calendar c = Calendar.getInstance();
            mHour = c.get(Calendar.HOUR_OF_DAY);
            mMinute = c.get(Calendar.MINUTE);

            // Launch Time Picker Dialog
            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {
                            updateTime(hourOfDay, minute);

                            // etTime.setText(hourOfDay + ":" + minute);
                        }
                    }, mHour, mMinute, false);
            timePickerDialog.show();
        }

    }

    //Used to convert 24hr format to 12hr format with AM/PM values
    private void updateTime(int hours, int mins) {

        String timeSet = "";
        if (hours > 12) {
            hours -= 12;
            timeSet = "PM";
        } else if (hours == 0) {
            hours += 12;
            timeSet = "AM";
        } else if (hours == 12)
            timeSet = "PM";
        else
            timeSet = "AM";


        String minutes = "";
        if (mins < 10)
            minutes = "0" + mins;
        else
            minutes = String.valueOf(mins);

        // Append in a StringBuilder
        String aTime = new StringBuilder().append(hours).append(':')
                .append(minutes).append(" ").append(timeSet).toString();

        etAppointmentTime.setText(aTime);
    }
}
