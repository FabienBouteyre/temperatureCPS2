package com.nurbol.android.tempmonitor;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class SignupActivity extends AppCompatActivity {
    Button btn_signup;
    EditText edt_name, edt_surname, edt_email, edt_password;
    String url = "http://192.168.137.1:8080/api/users/user/add";
    private RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        TextView login = (TextView) findViewById(R.id.loginTextView);
        edt_name = (EditText) findViewById(R.id.reg_name);
        edt_surname = (EditText) findViewById(R.id.reg_surname);
        edt_email = (EditText) findViewById(R.id.reg_email);
        edt_password = (EditText) findViewById(R.id.reg_password);
        btn_signup = (Button) findViewById(R.id.email_sign_up_button);

        queue = Volley.newRequestQueue(SignupActivity.this);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent numbersIntent = new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(numbersIntent);
            }
        });

        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkDataEntered() == true) {
                    Map<String, String> params = new HashMap();
                    params.put("name", edt_name.getText().toString());
                    params.put("surn", edt_surname.getText().toString());
                    params.put("email", edt_email.getText().toString());
                    params.put("pass", edt_password.getText().toString());

                    JSONObject parameters = new JSONObject(params);

                    JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, url, parameters, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Toast.makeText(getApplicationContext(), "User registered successfully!", Toast.LENGTH_SHORT).show();
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            error.printStackTrace();

                        }
                    });
                    queue.add(jsonRequest);
                    Toast.makeText(getApplicationContext(), "User registered successfully!", Toast.LENGTH_LONG).show();
                    Intent numbersIntent = new Intent(SignupActivity.this, LoginActivity.class);
                    startActivity(numbersIntent);
                }
            }
        });
    }

    boolean isEmail(EditText text) {
        CharSequence email = text.getText().toString();
        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }

    boolean isEmpty(EditText text) {
        CharSequence str = text.getText().toString();
        return TextUtils.isEmpty(str);
    }

    boolean checkDataEntered() {
        if (isEmpty(edt_name)) {
            Toast.makeText(this, "You must enter your Name!", Toast.LENGTH_SHORT).show();
            edt_name.setError("Name is required!");
            return false;
        }

        if (isEmpty(edt_surname)) {
            Toast.makeText(this, "You must enter your Surname!", Toast.LENGTH_SHORT).show();
            edt_surname.setError("Surname is required!");
            return false;
        }

        if (isEmail(edt_email) == false) {
            Toast.makeText(this, "Enter valid email!", Toast.LENGTH_SHORT).show();
            edt_email.setError("Enter valid email!");
            return false;
        }

        if (isEmpty(edt_password)) {
            Toast.makeText(this, "Enter password!", Toast.LENGTH_SHORT).show();
            edt_password.setError("Enter password!");
            return false;
        }

        return true;
    }
}
