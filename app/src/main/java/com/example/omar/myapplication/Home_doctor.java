package com.example.omar.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Home_doctor extends AppCompatActivity {

    ArrayList<doctor_model> doctors;
    ListView listView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_doctor);
        ArrayList<doctor_model> item = new ArrayList<doctor_model>();

        doctors = new ArrayList<>();
        listView = findViewById(R.id.doctor_chat);
        CatagoryID(String.valueOf(sharedprefmanger.getInstance(this).getId()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {

            sharedprefmanger.getInstance(this).islogout();
            startActivity(new Intent(this, Login.class));
            finish();

        }

        return super.onOptionsItemSelected(item);
    }
    private void CatagoryID(final String id) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.show_patient, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //   progressDialog.dismiss();
                try {
                    JSONObject jsonObject;
                    JSONArray array1 = new JSONArray(response);

                    //traversing through all the object
                    for (int i = 0; i < array1.length(); i++) {
                        JSONObject catagory1 = array1.getJSONObject(i);

                        doctors.add(new doctor_model(
                                catagory1.getString("NAME"),
                                catagory1.getString("p_id"),
                                catagory1.getString("id")
                        ));
                    }
                    mycustom_doctor adapter3 = new mycustom_doctor(Home_doctor.this, doctors);
                    listView.setAdapter(adapter3);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Home_doctor.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parms = new HashMap<>();
                parms.put("id", id);
                return parms;

            }
        };
        RequestHandler.getInstance(Home_doctor.this).addToRequestQueue(stringRequest);

    }
    }