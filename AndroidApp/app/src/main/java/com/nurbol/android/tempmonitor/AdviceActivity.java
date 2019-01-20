package com.nurbol.android.tempmonitor;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AdviceActivity extends AppCompatActivity {
    private AdviceAdapter adapter;
    private static final String LOG_TAG = AdviceActivity.class.getName();
    private static final String REQUEST_URL = "http://192.168.137.1:8080/api/advice";
    List<Advice> advices = new ArrayList<>();
    private RequestQueue mQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advice);

        ListView adviceListView = (ListView) findViewById(R.id.list_advice);
        adapter = new AdviceAdapter(this, new ArrayList<Advice>());
        adviceListView.setAdapter(adapter);
        mQueue = Volley.newRequestQueue(this);
        jsonParse();
    }

    private void jsonParse() {

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, REQUEST_URL, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        try {

                            for (int i = 0; i < response.length(); i++) {

                                JSONObject data = response.getJSONObject(i);
                                String room = data.getString("room");
                                long id = data.getLong("id");
                                String description = data.getString("description");
                                String date = data.getString("date");

                                Advice advice = new Advice(id, description, room, date);
                                advices.add(advice);
                            }

                            adapter.clear();
                            adapter.addAll(advices);
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
