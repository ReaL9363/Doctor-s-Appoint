package com.example.android.doctorsappointment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    EditText etUser, etPassword;
    Button btnLogin, btnCancel;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etUser = (EditText) findViewById(R.id.etUserName);
        etPassword = (EditText) findViewById(R.id.etPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnCancel = (Button) findViewById(R.id.btnCancel);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = etUser.getText().toString();
                intent = new Intent(getApplicationContext(), DoctorActivity.class);
                //intent.putExtra("salesRepresentative", (Parcelable) salesRepresentative);
                startActivity(intent);
                Toast.makeText(LoginActivity.this, "Welcome " + userName, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
