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

public class Home_pramdic extends AppCompatActivity {
    ArrayList<modelPramdic> doctors;
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_pramdic);
        doctors = new ArrayList<>();
        listView = findViewById(R.id.Pramadic);
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
            finish();
            startActivity(new Intent(this,Login.class));
        }

        return super.onOptionsItemSelected(item);
    }
    private void CatagoryID(final String id) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.getShow_patient, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //   progressDialog.dismiss();
                try {
                    JSONObject jsonObject;
                    JSONArray array1 = new JSONArray(response);

                    //traversing through all the object
                    for (int i = 0; i < array1.length(); i++) {
                        JSONObject catagory1 = array1.getJSONObject(i);

                        doctors.add(new modelPramdic(
                                catagory1.getString("id"),
                                catagory1.getString("id_d"),
                                catagory1.getString("id_p")
                        ));
                    }
                    adapterPramadic adapter3 = new adapterPramadic(Home_pramdic.this, doctors);
                    listView.setAdapter(adapter3);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Home_pramdic.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parms = new HashMap<>();
                parms.put("id", id);
                return parms;

            }
        };
        RequestHandler.getInstance(Home_pramdic.this).addToRequestQueue(stringRequest);

    }

}
