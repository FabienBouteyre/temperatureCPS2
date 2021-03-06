package com.nurbol.android.tempmonitor;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {
    public static boolean admin = false;
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
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

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
                if (checkDataEntered() == true) {
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
                    }, 3000);

                }
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

                            JSONArray roles = response.getJSONArray("roles");
                            String admin_str = roles.getString(1);
                            admin = admin_str.equals("ADMIN");

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

    boolean isEmail(EditText text) {
        CharSequence email = text.getText().toString();
        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }

    boolean isEmpty(EditText text) {
        CharSequence str = text.getText().toString();
        return TextUtils.isEmpty(str);
    }

    boolean checkDataEntered() {
        if (isEmail(et_username) == false) {
            Toast.makeText(this, "Enter valid email!", Toast.LENGTH_SHORT).show();
            et_username.setError("Enter valid email!");
            return false;
        }

        if (isEmpty(et_password)) {
            Toast.makeText(this, "Enter password!", Toast.LENGTH_SHORT).show();
            et_password.setError("Enter password!");
            return false;
        }

        return true;
    }
}
