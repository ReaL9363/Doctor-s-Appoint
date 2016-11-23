package com.example.android.doctorsappointment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.android.doctorsappointment.Model.Doctor;
import com.example.android.doctorsappointment.Volley.MySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DoctorActivity extends AppCompatActivity {
    // Log tag
    private static final String TAG = DoctorActivity.class.getSimpleName();

    // Billionaires json url

    private static final String url = "http://120.50.8.57/Test/doctor.php";
    //shahriar vai.need to parse
    // private static final String url = "http://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&orderby=time&minmag=5&limit=10";
    //private static final String url = "https://raw.githubusercontent.com/mobilesiri/Android-Custom-Listview-Using-Volley/master/richman.json";
    private ProgressDialog pDialog;
    private List<Doctor> doctorList = new ArrayList<>();
    private ListView listView;
    private DoctorAdapter doctorAdapter;
    String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor);
        //setTitle("Doctor's List");
        pDialog = new ProgressDialog(this);
        // Showing progress dialog before making http request
        pDialog.setMessage("Loading...");
        //pDialog.show();
        userName = getIntent().getStringExtra("userName");
        Toast.makeText(this, "Hi i m " + userName, Toast.LENGTH_SHORT).show();

        /*final ArrayList<Doctor> doctorArrayList = new ArrayList<Doctor>();
        doctorArrayList.add(new Doctor("Abul"));
        doctorArrayList.add(new Doctor("babul"));
        doctorArrayList.add(new Doctor("Kabul"));
        doctorArrayList.add(new Doctor("Rana"));
        doctorArrayList.add(new Doctor("Mostafiz"));
        doctorArrayList.add(new Doctor("Karim"));
        doctorArrayList.add(new Doctor("Saagir"));
        doctorArrayList.add(new Doctor("Chandrima"));
        doctorArrayList.add(new Doctor("Nishat"));*/

        doctorAdapter = new DoctorAdapter(this, doctorList);
        listView = (ListView) findViewById(R.id.listview_Doctor);
        listView.setAdapter(doctorAdapter);
        // makeJsonArrayRequest();

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        String name = jsonObject.optString("name");
                        Doctor doctor = new Doctor(name);
                        doctorList.add(doctor);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                doctorAdapter.notifyDataSetChanged();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Toast.makeText(DoctorActivity.this, "Something wrong...", Toast.LENGTH_SHORT).show();
            }
        });
        MySingleton.getInstance(this).addToRequestQueue(jsonArrayRequest);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Doctor doctor = doctorList.get(position);
                Intent intent = new Intent(DoctorActivity.this, AppointmentActivity.class);
                intent.putExtra("doctorName", doctor.getmDoctorName().toString());
                intent.putExtra("userName", userName);
                startActivity(intent);
                // Toast.makeText(DoctorActivity.this, "You clicked" + doctor.getmDoctorName(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void makeJsonArrayRequest() {
        // Creating volley request obj

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        hidePDialog();
    }

    private void hidePDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
    }
}
