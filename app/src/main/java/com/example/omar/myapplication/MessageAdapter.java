package com.example.omar.myapplication;


import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class MessageAdapter extends ArrayAdapter<Message> {


    ArrayList<Message> item;
    Context context;
    int resource;
    String from_id ;

    public MessageAdapter(Context context, int resource , ArrayList<Message> item , String from_id) {
        super(context, resource , item);
        this.context = context ;
        this.resource = resource ;
        this.item = item ;
        this.from_id = from_id ;
    }


    public View getView(int position, View view, ViewGroup parent)
    {
        if (view == null){
            LayoutInflater layoutInflater = (LayoutInflater) getContext()
                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.row_message_chat_box, null, true);

        }




        TextView TV1 = view.findViewById(R.id.tv_message);
        TextView TV2 = view.findViewById(R.id.tv_sentat);


        TextView TV3 = view.findViewById(R.id.tv_message2);
        TextView TV4 = view.findViewById(R.id.tv_sentat2);


        if(item.get(position).getFrom_id().equals(from_id)) {



            TV1.setText(item.get(position).getText());
            TV2.setText(item.get(position).getSentat());

            TV1.setVisibility(View.VISIBLE);
            TV2.setVisibility(View.VISIBLE);
            TV3.setVisibility(View.GONE);
            TV4.setVisibility(View.GONE);


        }

        else{
            TV3.setText(item.get(position).getText());
            TV4.setText(item.get(position).getSentat());

            TV1.setVisibility(View.GONE);
            TV2.setVisibility(View.GONE);
            TV3.setVisibility(View.VISIBLE);
            TV4.setVisibility(View.VISIBLE);
        }
        /////////////////////////////////////////////////////////////////

        /*
        Picasso.with(context)
                .load(course.getImgURL())
                .into(imageView);
                */


        return view;
    }



}
