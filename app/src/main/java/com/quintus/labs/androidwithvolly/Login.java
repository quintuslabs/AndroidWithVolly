package com.quintus.labs.androidwithvolly;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

public class Login extends AppCompatActivity {
    private static String URL = "http://192.168.0.4/php/login.php";
    Button loginButton;
    EditText loginUserName, loginPassword;
    TextView registerTextView;
    private Snackbar snackbar;
    private ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginUserName = (EditText) findViewById(R.id.loginEmail);
        loginPassword = (EditText) findViewById(R.id.loginPassword);
        pd = new ProgressDialog(Login.this);
        registerTextView = (TextView) findViewById(R.id.textViewEmailRegister);
        loginButton = (Button) findViewById(R.id.loginButton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginRequest();
            }
        });

        registerTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Registration.class));
            }
        });
    }

    private void loginRequest() {
        pd.setMessage("Signing In . . .");
        pd.show();
        RequestQueue queue = Volley.newRequestQueue(Login.this);
        String response = null;

        final String finalResponse = response;

        StringRequest postRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        pd.hide();


                        showSnackbar(response);

                        if (response.equals("Login")) {
                            Toast.makeText(getApplicationContext(), "Registration Successfull !!", Toast.LENGTH_LONG).show();

                            startActivity(new Intent(getApplicationContext(), Welcome.class));
                        }


                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        pd.hide();
                        Log.d("ErrorResponse", finalResponse);

                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("email", loginUserName.getText().toString());
                params.put("password", loginPassword.getText().toString());
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
