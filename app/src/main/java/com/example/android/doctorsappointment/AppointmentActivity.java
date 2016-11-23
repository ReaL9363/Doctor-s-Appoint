package com.example.android.doctorsappointment;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class AppointmentActivity extends AppCompatActivity implements View.OnClickListener {
    DoctorActivity doctorActivity;
    Button btvAppointmentSend;
    EditText etAppointmentDate;
    EditText etAppointmentTime;
    TextView tvDoctorName;
    TextView tvUser;
    Calendar myCalendar;
    Spinner spnAmPm;
    private int mYear, mMonth, mDay, mHour, mMinute;
    RequestQueue mRequestQueue;
    private String url = "http://120.50.8.57/Test/appointment_up.php";
    AlertDialog.Builder builder;
    String userNameFix;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);
        myCalendar = Calendar.getInstance();

        btvAppointmentSend = (Button) findViewById(R.id.btnAppointmentSubmit);
        etAppointmentDate = (EditText) findViewById(R.id.etAppointmentDate);
        etAppointmentTime = (EditText) findViewById(R.id.etAppointmentTime);
        tvDoctorName = (TextView) findViewById(R.id.tvDoctorNameFromLV);
        //tvUser=(TextView) findViewById(R.id.tvUserName);
        builder = new AlertDialog.Builder(this);
        // Start the queue
        // mRequestQueue.start();
        mRequestQueue = Volley.newRequestQueue(this);
        //spnAmPm = (Spinner) findViewById(R.id.spnrAMPM);

        String doctorNameFix = getIntent().getStringExtra("doctorName");
        tvDoctorName.setText(doctorNameFix);
        userNameFix = getIntent().getStringExtra("userName").toString();
        Toast.makeText(AppointmentActivity.this, "hello "+userNameFix, Toast.LENGTH_SHORT).show();
        //tvUser.setText(userNameFix);

        etAppointmentDate.setOnClickListener(this);
        etAppointmentTime.setOnClickListener(this);

        btvAppointmentSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AppointmentActivity.this, "Data send successfully", Toast.LENGTH_SHORT).show();
                sendDataToServer();
                //etAppointmentDate.setText(" ");
                //etAppointmentTime.setText(R.string.time);
                // etAppointmentTime.setText(" ");
            }
        });

    }

    private void sendDataToServer() {
        final String doctorName, patientName, time, date;
        doctorName = getIntent().getStringExtra("doctorName".toString());
        patientName = getIntent().getStringExtra("userName").toString();
        time = etAppointmentTime.getText().toString();
        date = etAppointmentDate.getText().toString();


        // Formulate the request and handle the response.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        builder.setTitle("Server response");
                        builder.setMessage("response " +
                                ":" + response);
                        builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                etAppointmentTime.setText(" ");
                                etAppointmentTime.setText(" ");

                            }
                        });
                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();

                    }
                },
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.e("Error: ", error.getMessage());
                        Toast.makeText(AppointmentActivity.this, "Something went wrong ... :( ", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<String, String>();
                params.put("doctor_name", doctorName);
                params.put("patient_name", patientName);
                params.put("time", time);

                params.put("date", date);
                return params;
            }
        };

        // Add the request to the RequestQueue.
        mRequestQueue.add(stringRequest);
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
