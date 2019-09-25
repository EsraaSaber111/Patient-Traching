package com.example.omar.myapplication;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alexzh.circleimageview.CircleImageView;

import java.util.ArrayList;


public class mycustom_doctor extends BaseAdapter {

    public ArrayList<doctor_model> item = new ArrayList<>();

    Context context;

    public mycustom_doctor(Context context,ArrayList<doctor_model> item) {
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
        View view1 = inf.inflate(R.layout.layout_item_doctor_inflate, null);
        TextView tname = (TextView) view1.findViewById(R.id.name);
        LinearLayout relativeLayout=view1.findViewById(R.id.doctor_item);
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context,Chat_box_doctor.class);
                intent.putExtra("id",item.get(i).getAge());
                context.startActivity(intent);
            }
        });
        tname.setText(item.get(i).names);

        view1.setTag(item.get(i));
        CircleImageView img = (CircleImageView) view1.findViewById(R.id.myphoto);
     //   img.setImageResource(item.get(i).img);
        return view1;
    }
}
