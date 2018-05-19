package com.quintus.labs.androidwithvolly;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class Registration extends AppCompatActivity {
    private static String S_URL = "http://192.168.0.4/php/signup.php";
    EditText signUpEmail, signUpName, signUpPassword;
    Button signupButton;
    ProgressDialog progressDialog;
    CheckBox checkBoxTerms;
    private Snackbar snackbar;
    private ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        pd = new ProgressDialog(Registration.this);
        signupButton = (Button) findViewById(R.id.signupButton);
        signUpEmail = (EditText) findViewById(R.id.signUpEmail);
        signUpName = (EditText) findViewById(R.id.signUpName);
        signUpPassword = (EditText) findViewById(R.id.signUpPassword);
        checkBoxTerms = (CheckBox) findViewById(R.id.checkBoxTerms);
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkBoxTerms.isChecked() == true) {
                    signupRequest();
                } else {
                    Toast.makeText(getApplicationContext(), "Please Accept Terms & Services", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void signupRequest() {
        pd.setMessage("Signing Up . . .");
        pd.show();
        RequestQueue queue = Volley.newRequestQueue(Registration.this);
        String response = null;

        final String finalResponse = response;

        StringRequest postRequest = new StringRequest(Request.Method.POST, S_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        pd.hide();

                        showSnackbar(response);

                        if (response.equals("Successfully Signed In")) {
                            Toast.makeText(getApplicationContext(), "Registration Successfull !!", Toast.LENGTH_LONG).show();

                            startActivity(new Intent(getApplicationContext(), Login.class));

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("ErrorResponse", finalResponse);


                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("email", signUpEmail.getText().toString());
                params.put("password", signUpPassword.getText().toString());
                params.put("name", signUpName.getText().toString());
                return params;
            }
        };
        postRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(postRequest);

    }

    public void showSnackbar(String stringSnackbar) {
        Snackbar.make(findViewById(android.R.id.content), stringSnackbar.toString(), Snackbar.LENGTH_SHORT)
                .setActionTextColor(getResources().getColor(R.color.colorPrimary))
                .show();
    }
}
