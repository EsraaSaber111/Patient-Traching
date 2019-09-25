package com.example.omar.myapplication;

import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

public class Home_patient extends AppCompatActivity implements LocationListener {
ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_patient);
        listView=findViewById(R.id.select_doctor);

    }


    public void doctor(View view) {

        Intent intent=new Intent(this,chat_doctor.class);
        startActivity(intent);
    }

    public void help(View view) {
        Intent intent=new Intent(this,Patient_request.class);
        startActivity(intent);
    }
    public void signal(View view) {
        Intent intent=new Intent(this,MainActivity.class);
        startActivity(intent);
    }

    public void hosp(View view) {
        Intent intent=new Intent(this,NearHosipital.class);
        startActivity(intent);
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
        if (id == R.id.setting) {

            startActivity(new Intent(this,Register_next.class));
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {
        Toast.makeText( getApplicationContext(),"Gps Enabled",Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onProviderDisabled(String provider) {
        Toast.makeText( getApplicationContext(),"Gps Disabled ",Toast.LENGTH_SHORT ).show();

    }
}


