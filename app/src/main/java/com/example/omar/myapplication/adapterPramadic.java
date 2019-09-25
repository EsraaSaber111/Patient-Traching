package com.example.omar.myapplication;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alexzh.circleimageview.CircleImageView;
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


public class adapterPramadic extends BaseAdapter {

    public ArrayList<modelPramdic> item = new ArrayList<>();

    Context context;

    public adapterPramadic(Context context,ArrayList<modelPramdic> item) {
        this.item=item;
        this.context=context;
    }

    @Override
    public int getCount() {
        return item.size();
    }

    @Override
    public Object getItem(int i) {
        return item.get(i).names;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, final ViewGroup viewGroup) {
        LayoutInflater inf = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view1 = inf.inflate(R.layout.layout_inflate_para, null);
        TextView tname = (TextView) view1.findViewById(R.id.name);
        Button button= view1.findViewById(R.id.btnmoss);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CatagoryID(item.get(i).getId());
            }
        });
        RelativeLayout relativeLayout=view1.findViewById(R.id.paralist);
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context,Patient_request.class);
                context.startActivity(intent);
            }
        });
        tname.setText(item.get(i).names);

        view1.setTag(item.get(i));
        ImageView img = (ImageView) view1.findViewById(R.id.myphoto);
        //   img.setImageResource(item.get(i).img);
        return view1;
    }
    private void CatagoryID(final String id) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.cancel_patient, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //   progressDialog.dismiss();


                    Toast.makeText(context, "Done", Toast.LENGTH_SHORT).show();


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parms = new HashMap<>();
                parms.put("id", id);
                return parms;

            }
        };
        RequestHandler.getInstance(context).addToRequestQueue(stringRequest);

    }

}
