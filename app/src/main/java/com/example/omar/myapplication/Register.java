package com.example.omar.myapplication;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Register extends AppCompatActivity {

    ImageView imageView;
    TextView textView;
String Specification,experience,diseases;

    EditText name,email,pass1,age,phone;
    RadioGroup rgender,rspec;
    Button button6;
    private ProgressDialog progressDialog;
   Spinner spinner;
    List<String> citys;
    int group;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_register);
        this.getSupportActionBar().setTitle("Register");
        spinner=findViewById(R.id.spinner1);

        citys=new ArrayList<>();
        final List<String> c=new ArrayList<>();
        c.add("Regiter as ");
        c.add("Doctor");
        c.add("Paramadic");
        c.add("Patient");
        ArrayAdapter<String> adapter=new ArrayAdapter<>(Register.this,android.R.layout.simple_spinner_item,
                c);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        button6 = (Button) findViewById(R.id.button6);
        name=(EditText)findViewById(R.id.edit1);
        email=(EditText)findViewById(R.id.editText);
        pass1=(EditText)findViewById(R.id.editText2);
        phone=(EditText)findViewById(R.id.editText3);
        age=(EditText)findViewById(R.id.tv5);
        rgender=(RadioGroup) findViewById(R.id.radio);
        progressDialog = new ProgressDialog(this);

    }

    private void registerUser(final int group) {

        final String Name=name.getText().toString().trim();
        final String Email=email.getText().toString().trim();
        final String password1=pass1.getText().toString().trim();
        final String Phone=phone.getText().toString().trim();
        final String Age=age.getText().toString().trim();
        final String Gender = ((RadioButton) findViewById(rgender.getCheckedRadioButtonId())).getText().toString();
        progressDialog.setMessage("Registering user...");
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Constants.URL_DREGISTER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            Toast.makeText(Register.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                            if (jsonObject.getBoolean("success")) {
                                Intent intent = new Intent(Register.this, vaildation.class);
                                startActivity(intent);
                                finish();
                           }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.hide();
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("name",Name);
                params.put("email",Email);
                params.put("password",password1);
                params.put("age",Age);
                params.put("phone",Phone);
                params.put("gender",Gender);

                params.put("group_id", String.valueOf(group));
                return params;
            }
        };

        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);


    }
    public void reg_btn(View view)
    {
        if (spinner.getSelectedItem().toString().equalsIgnoreCase("Doctor"))
        {
            group=1;
        }
        else
        if (spinner.getSelectedItem().toString().equalsIgnoreCase("Paramadic"))
        {
            group=3;

        }
        else
        if (spinner.getSelectedItem().toString().equalsIgnoreCase("Patient"))
        {
            group=2;

        }

        registerUser(group);
    }


}



