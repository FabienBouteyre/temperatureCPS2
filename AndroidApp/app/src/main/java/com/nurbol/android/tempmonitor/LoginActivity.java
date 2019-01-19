package com.nurbol.android.tempmonitor;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {
    private RequestQueue mQueue;
    Button btn_login;
    EditText et_username, et_password;
    String REQUEST_URL = "http://192.168.137.1:8080/api/users/user/login/";
    String password, newUrl;
    String edt_username, edt_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        TextView signup = (TextView) findViewById(R.id.signUpTextView);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent numbersIntent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(numbersIntent);
            }
        });
        btn_login = (Button) findViewById(R.id.email_sign_in_button);
        et_username = (EditText) findViewById(R.id.email);
        et_password = (EditText) findViewById(R.id.password);

        mQueue = Volley.newRequestQueue(this);
        password = "!@#$%^&*";


        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edt_username = et_username.getText().toString();
                edt_password = et_password.getText().toString();
                newUrl = REQUEST_URL + edt_username;

                jsonParse();

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        if (edt_password.equals(password)) {
                            Intent intent = new Intent(LoginActivity.this, SearchActivity.class);
                            startActivity(intent);
                        } else {
                            Toast toast = Toast.makeText(getApplicationContext(),
                                    "Wrong username or password",
                                    Toast.LENGTH_SHORT);
                            toast.show();
                        }
                    }
                }, 4000);


            }
        });
    }

    private void jsonParse() {

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, newUrl, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            String get_password = response.getString("pass");
                            password = get_password;

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        mQueue.add(request);
    }
}
