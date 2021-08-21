package com.example.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    EditText email_input,password_input;
    TextView register;
    Button btnLogin;


    final String loginURL = "http://192.168.0.105/android-users/login.php";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        email_input = findViewById(R.id.loginEmail);
        password_input = findViewById(R.id.loginPassword);
        register = findViewById(R.id.register);
        btnLogin = findViewById(R.id.btnLogin);


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateUserData();
            }
        });


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),activity_register.class);
                startActivity(intent);
            }
        });

    }
    private  void validateUserData()
    {
        final String email = email_input.getText().toString();
        final String password = password_input.getText().toString();

           if(TextUtils.isEmpty(email)){
               email_input.setError("Please Enter your Email");
               email_input.requestFocus();

               btnLogin.setEnabled(true);
               return;
           }
           if(TextUtils.isEmpty(password)){
               password_input.setError("Please Enter Password");
               password_input.requestFocus();

               btnLogin.setEnabled(true);
               return;

           }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            email_input.setError("Enter a valid email");
            email_input.requestFocus();
            //Vibrate for 100 milliseconds

            btnLogin.setEnabled(true);
            return;
        }

        loginUser();

    }
  private  void loginUser()
  {
      final String email = email_input.getText().toString();
      final String password = password_input.getText().toString();


      StringRequest stringRequest = new StringRequest(Request.Method.POST,loginURL,
              new Response.Listener<String>() {
                  @Override
                  public void onResponse(String response) {

                      try {
//                            Toast.makeText(getApplicationContext(),response.toString(), Toast.LENGTH_SHORT).show();

                          JSONObject obj = new JSONObject(response);
                          if (obj.getBoolean("error")) {
                              Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                          } else {

                              //getting user name
                              String Username = obj.getString("username");
                              Toast.makeText(getApplicationContext(),Username, Toast.LENGTH_SHORT).show();

                              //storing the user in shared preferences
                              SharedPref.getInstance(getApplicationContext()).storeUserName(Username);
                              //starting the profile activity
                              finish();
                              startActivity(new Intent(getApplicationContext(), ProfileActivity.class));

                          }



                      } catch (JSONException e) {
                          e.printStackTrace();
                      }
                  }
              },
              new Response.ErrorListener() {
                  @Override
                  public void onErrorResponse(VolleyError error) {
                      Toast.makeText(getApplicationContext(),"Connection Error"+error, Toast.LENGTH_SHORT).show();
                      error.printStackTrace();
                  }
              }) {
          @Override
          protected Map<String, String> getParams() throws AuthFailureError {
              Map<String, String> params = new HashMap<>();
              params.put("email", email);
              params.put("password", password);

              return params;
          }
      };
      VolleySingleton.getInstance(MainActivity.this).addToRequestQueue(stringRequest);



  }


}