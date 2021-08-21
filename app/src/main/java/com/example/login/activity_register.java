package com.example.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.text.TextUtils;
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

public class activity_register extends AppCompatActivity {

    final String registerUrl = "http://192.168.0.105/android-users/register.php";

    EditText username,email,password,cpassword;
    TextView login;
    Button btnRegister;
    Vibrator v;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        username = (EditText) findViewById(R.id.registerName);
        email = (EditText) findViewById(R.id.registerEmail);
        password = (EditText) findViewById(R.id.confirmpassword);
        cpassword = (EditText) findViewById(R.id.confirmpassword);
        btnRegister = (Button) findViewById(R.id.btnRegister);
        login = (TextView) findViewById(R.id.login);
        v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);


        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateUserData();
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });

    }
     private void   validateUserData()
     {

         final String reg_name = username.getText().toString();
         final String reg_email = email.getText().toString();
         final String reg_password = cpassword.getText().toString();
         final String reg_cpassword = cpassword.getText().toString();

         if (TextUtils.isEmpty(reg_name)) {
             username.setError("Please enter username");
             username.requestFocus();

             return;
         }
         //checking if email is empty
         if (TextUtils.isEmpty(reg_email)) {
             email.setError("Please enter email");
             email.requestFocus();

             return;
         }
         //checking if password is empty
         if (TextUtils.isEmpty(reg_password)) {
             password.setError("Please enter password");
             password.requestFocus();
             //Vibrate for 100 milliseconds

             return;
         }
         //validating email
         if (!android.util.Patterns.EMAIL_ADDRESS.matcher(reg_email).matches()) {
             email.setError("Enter a valid email");
             email.requestFocus();

             return;
         }
         //checking if password matches
         if (!reg_password.equals(reg_cpassword)) {
             password.setError("Password Does not Match");
             password.requestFocus();

             return;
         }
         registerUser();


     }
    private void registerUser()
    {
        final String reg_username = username.getText().toString();
        final String reg_email = email.getText().toString();
        final String reg_password = cpassword.getText().toString();

        StringRequest stringRequest = new StringRequest(Request.Method.POST,registerUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {

                            JSONObject obj = new JSONObject(response);
                            if (obj.getBoolean("error")) {
                                Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                            } else {


                                //starting the login activity
                                startActivity(new Intent(getApplicationContext(),MainActivity.class));

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
                params.put("username",reg_username);
                params.put("email", reg_email);
                params.put("password", reg_password);

                return params;
            }
        };
        VolleySingleton.getInstance(activity_register.this).addToRequestQueue(stringRequest);

    }

}