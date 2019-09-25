package com.example.omar.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class vaildation extends AppCompatActivity {
EditText vaild_code;
    EditText e1,e2,e3,e4,e5,e6;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vaildation);
        e1=findViewById(R.id.editText);
        e2=findViewById(R.id.editText2);
        e3=findViewById(R.id.editText3);
        e4=findViewById(R.id.editText4);
        e5=findViewById(R.id.editText5);
        e6=findViewById(R.id.editText6);
    }

    public void cliv(View view) {
        String x=e1.getText().toString()+e2.getText().toString()+e3.getText().toString()+e4.getText().toString()+e5.getText().toString()+e6.getText().toString();

        CatagoryID(x);
    }
    private void CatagoryID(final String s) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.Vaid, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //   progressDialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getBoolean("Mesag")) {
                        sharedprefmanger.getInstance(getApplicationContext())
                                .userIlogin(
                                        jsonObject.getInt("id"),
                                        jsonObject.getString("email"),
                                        jsonObject.getString("name"),
                                        jsonObject.getString("image"),
                                        jsonObject.getInt("group_id")

                                );
                        startActivity(new Intent(vaildation.this,Register_next.class));
                    }
                    else if (jsonObject.getBoolean("success")) {

                        Toast.makeText(vaildation.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                    }

                    } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(vaildation.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parms = new HashMap<>();
                parms.put("activation_code", s);
                return parms;

            }
        };
        RequestHandler.getInstance(vaildation.this).addToRequestQueue(stringRequest);

    }

}
