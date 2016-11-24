package com.example.android.doctorsappointment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    EditText etUser, etPassword;
    Button btnLogin, btnCancel;
    private Intent intent = new Intent();
    String userName;
    String password;
    private static final String url = "http://120.50.8.57/Test/loginSimple.php";
    RequestQueue mRequestQueue;
    // private String url = "http://120.50.8.57/Test/appointment_up.php";
    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etUser = (EditText) findViewById(R.id.etUserName);
        etPassword = (EditText) findViewById(R.id.etPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnCancel = (Button) findViewById(R.id.btnCancel);
        userName = etUser.getText().toString();

        builder = new AlertDialog.Builder(this);
        mRequestQueue = Volley.newRequestQueue(this);

        buttonAction();


    }

    private void buttonAction() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String userName, password;
                userName = etUser.getText().toString().trim();
                password = etPassword.getText().toString().trim();
                // Formulate the request and handle the response.
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(final String response) {
                                if (response.equalsIgnoreCase("Login success")) {
                                    builder.setTitle("Login Status");
                                    builder.setMessage(response);
                                    builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                            intent.setClass(getApplicationContext(), DoctorActivity.class);
                                            intent.putExtra("userName", etUser.getText().toString());
                                            //intent.putExtra("salesRepresentative", (Parcelable) salesRepresentative);
                                            startActivity(intent);
                                            // Toast.makeText(LoginActivity.this, "Welcome " + etUser.getText().toString(), Toast.LENGTH_SHORT).show();
                                            etUser.setText("");
                                            etPassword.setText("");


                                        }
                                    });
                                    AlertDialog alertDialog = builder.create();
                                    alertDialog.show();

                                } else {
                                    builder.setTitle("Login Status");
                                    builder.setMessage(response);
                                    builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            // Toast.makeText(LoginActivity.this, "Welcome " + etUser.getText().toString(), Toast.LENGTH_SHORT).show();
                                            etUser.setText("");
                                            etPassword.setText("");


                                        }
                                    });
                                    AlertDialog alertDialog = builder.create();
                                    alertDialog.show();
                                }


                            }
                        },
                        new Response.ErrorListener() {

                            @Override
                            public void onErrorResponse(VolleyError error) {
                                VolleyLog.e("Error: ", error.getMessage());
                                Toast.makeText(LoginActivity.this, "Something went wrong ... :( ", Toast.LENGTH_SHORT).show();
                            }
                        }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        HashMap<String, String> params = new HashMap<String, String>();
                        params.put("user_name", userName);
                        params.put("password", password);

                        return params;
                    }
                };

                // Add the request to the RequestQueue.
                mRequestQueue.add(stringRequest);

            }
        });
    }


}

