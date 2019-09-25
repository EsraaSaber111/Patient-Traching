package com.example.omar.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class chat_doctor extends AppCompatActivity {
    ArrayList<doctor_model> doctors;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_doctor);
        doctors=new ArrayList<>();
        listView=findViewById(R.id.select_doctor);
        loadcatagory();
    }
    private void loadcatagory() {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constants.show_doctor,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //converting the string to json array object
                            JSONArray array1 = new JSONArray(response);

                            //traversing through all the object

                            for (int i = 0; i < array1.length(); i++) {
                                JSONObject catagory1 = array1.getJSONObject(i);

                                doctors.add(new doctor_model(
                                        catagory1.getString("name"),
                                        catagory1.getString("id"),
                                        catagory1.getString("email")
                                ));
                            }
                           mycustom adapter3 = new mycustom(chat_doctor.this, doctors);
                            listView.setAdapter(adapter3);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        //adding our stringrequest to queue
        Volley.newRequestQueue(chat_doctor.this).add(stringRequest);
    }
}
