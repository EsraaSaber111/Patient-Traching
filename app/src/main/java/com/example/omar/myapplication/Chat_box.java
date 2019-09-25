package com.example.omar.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.omar.myapplication.Singleton.MySingleton;
import com.example.omar.myapplication.Singleton.ServerDetails;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class Chat_box extends AppCompatActivity {

    EditText editTextMessage;
    ListView listView ;
    String from_id  = String.valueOf(sharedprefmanger.getInstance(this).getId());
    String to_id  = "1";
    Intent intent;

    ArrayList<Message> arrayList ;
    int count=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_box);

        editTextMessage = (EditText)findViewById(R.id.editTextMessage);
        listView = (ListView)findViewById(R.id.lv);
        arrayList = new ArrayList<Message>();
       intent=getIntent();
        to_id=intent.getExtras().getString("id","1");
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                while(true) {
                    try {
                        onSelect();
                    }catch (Exception e){

                    }
                    try {
                        Thread.sleep(4000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        //onSelect();
        t.start();
        //getMessage("1","2");
    }

    public void sendMessage(View view) {

        final String message = editTextMessage.getText().toString().trim();
        if (message.equalsIgnoreCase(""))
            return;

        try {


            StringRequest stringRequest = new StringRequest(Request.Method.POST,   ServerDetails.sendMessage,
                    new Response.Listener<String>() {




                        @Override
                        public void onResponse(String response) {
                            if (response.equalsIgnoreCase(""))
                                return;


                            Toast.makeText(Chat_box.this, "send success", Toast.LENGTH_SHORT).show();
                            //getMessage("1","2");
                            onSelect();


                            editTextMessage.setText("");
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(Chat_box.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    editTextMessage.setText(error.getMessage());
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> map = new HashMap();
                    map.put("id",  from_id);
                    map.put("idto",intent.getExtras().getString("id","1"));
                    map.put("m", editTextMessage.getText().toString());

                    return map;
                }
            };

            MySingleton.getMinstance(this).addRequestQueuex(stringRequest);
        }catch (Exception e){
            Toast.makeText(this, "error 12"+ e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }


    public void onSelect()
    {
        String serever_url = ServerDetails.getMessage;
        try {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, serever_url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            showJson(response);
                            // Toast.makeText(MainActivity.this , "search success"+response,Toast.LENGTH_LONG).show();
                        }
                    }
                    , new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(Chat_box.this ,error.getMessage(),Toast.LENGTH_LONG).show();

                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String , String> params = new HashMap<String ,String>();
                    params.put("from",from_id);
                    params.put("to",to_id);
                    return params;
                }
            };
            MySingleton.getMinstance(this).addRequestQueuex(stringRequest);

        }catch (Exception e){ Toast.makeText(this,e.getMessage(),Toast.LENGTH_SHORT).show();}

    }

    private void showJson(String response ) {


        try {

            Message m ;
        //    JSONObject jsonObject = new JSONObject(response);
            JSONArray result  = new JSONArray(response);
            String message = "";
            String sentat = "";
            String from_id_json ;

            arrayList = new ArrayList<Message>();
            for(int i = 0 ; i < result.length() ;i++) {


                JSONObject collegeData = result.getJSONObject(i);
                message = collegeData.getString("message");
                sentat = collegeData.getString("id");
                from_id_json = collegeData.getString("p_id");

                m = new Message(message , sentat , from_id_json);
                arrayList.add(m);
                //arrayList.add(message);
            }


            //ArrayAdapter arrayAdapter = new ArrayAdapter(this,android.R.layout.activity_list_item  , android.R.id.text1, arrayList);


            MessageAdapter messageAdapter = new MessageAdapter(this , R.layout.row_message_chat_box , arrayList ,from_id);
            listView.setAdapter(messageAdapter);

            listView.setSelection(messageAdapter.getCount()-1);




        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
