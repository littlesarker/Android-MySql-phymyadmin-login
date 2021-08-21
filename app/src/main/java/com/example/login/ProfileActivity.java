package com.example.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ProfileActivity extends AppCompatActivity {

    TextView username;
    Button btnLogout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        username = findViewById(R.id.username);
        btnLogout = findViewById(R.id.btnLogout);

        username = findViewById(R.id.username);
        btnLogout = findViewById(R.id.btnLogout);

        //check if user is logged in
        if (!SharedPref.getInstance(this).isLoggedIn()) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }

        //getting logged in user name
        String loggedUsename = SharedPref.getInstance(this).LoggedInUser();
        username.setText("Username : "+loggedUsename);

        //logging out
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                SharedPref.getInstance(getApplicationContext()).logout();
            }
        });

    }
}