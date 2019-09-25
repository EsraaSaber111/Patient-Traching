package com.example.omar.myapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {
    EditText name,pass;
    ProgressDialog progress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);
        this.getSupportActionBar().hide();
        name = findViewById(R.id.editpohone1);
        pass = findViewById(R.id.editpassword1);
        progress = new ProgressDialog(this);
        if (sharedprefmanger.getInstance(this).islogin()) {
            if (sharedprefmanger.getInstance(this).getGroupId() == 1) {

                Intent intent = new Intent(Login.this, Home_doctor.class);
                startActivity(intent);
                finish();

            } else if(sharedprefmanger.getInstance(this).getGroupId() == 2){

                Intent intent = new Intent(Login.this, Home_patient.class);
                startActivity(intent);
                finish();
            }
            else if(sharedprefmanger.getInstance(this).getGroupId() == 3){

                Intent intent = new Intent(Login.this, Home_pramdic.class);
                startActivity(intent);
                finish();
            }

        }
    }




    public void singin(View view) {
        userLogin();

    }

    public void sinup(View view) {
        Intent intent=new Intent(Login.this,Register.class);
        startActivity(intent);
        finish();

    }
    private void userLogin(){
        final String Name=name.getText().toString().trim();
        final String Pass=pass.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                Constants.URL_LOGIN ,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);
                            if (obj.getBoolean("success")) {
                                Intent intent = new Intent(Login.this, vaildation.class);
                                startActivity(intent);
                            }
                                if (obj.getBoolean("Mesag")) {
                                    sharedprefmanger.getInstance(Login.this)
                                            .userIlogin(
                                                    obj.getInt("id"),
                                                    obj.getString("name"),
                                                    obj.getString("email"),
                                                    obj.getString("image"),
                                                    obj.getInt("group_id")
                                            );

                                    if (obj.getInt("group_id") == 1) {
                                        Toast.makeText(Login.this, "login sucuccessful", Toast.LENGTH_LONG).show();

                                        Intent intent = new Intent(Login.this, Home_doctor.class);
                                        startActivity(intent);
                                        finish();

                                    } else if (obj.getInt("group_id") == 2) {
                                        Toast.makeText(Login.this, "login sucuccessful", Toast.LENGTH_LONG).show();

                                        Intent intent = new Intent(Login.this, Home_patient.class);
                                        startActivity(intent);
                                        finish();
                                    } else if (obj.getInt("group_id") == 3) {

                                        Toast.makeText(Login.this, "login sucuccessful", Toast.LENGTH_LONG).show();

                                        Intent intent = new Intent(Login.this, Home_pramdic.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                } else if (!obj.getBoolean("Mesag")) {
                                    Toast.makeText(Login.this, "user not found", Toast.LENGTH_SHORT).show();
                                }

                            else if (obj.getBoolean("success")){
                                Toast.makeText(Login.this, obj.getString("message"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progress.dismiss();

                        Toast.makeText(
                               Login.this,
                                error.getMessage(),
                                Toast.LENGTH_LONG
                        ).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("user",Name);
                params.put("pass",Pass);
                return params;
            }

        };

        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }
}
