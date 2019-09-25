package com.example.omar.myapplication;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.directions.route.AbstractRouting;
import com.directions.route.Route;
import com.directions.route.RouteException;
import com.directions.route.Routing;
import com.directions.route.RoutingListener;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.SphericalUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Patient_request extends FragmentActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        GoogleMap.OnMarkerDragListener,
        GoogleMap.OnMapLongClickListener,
        View.OnClickListener,
        RoutingListener ,LocationListener{
    MyLocationListener myLocationListener=new MyLocationListener(this);
    double total_cost;
    private static final int REQUEST_LOCATION_CODE=1000;
    LatLng latLngt;
    //Our Map
    int id_moss=0;
    private GoogleMap mMap;
    TextView adressinfo, dist, durt,textCost;
    //To store longitude and latitude from map
    private double longitude;
    private double latitude;
    String ditance, duration,cost = null;
    TextView name,phone,email;
    private ImageButton buttonSave;
    private Button buttonCurrent,buttonConform;
    private ImageButton buttonView;
    public String distinition;
    LatLng latLnglocation;
    LatLng storelocation;
    LatLng newlocation,send_order;
    private List<Polyline> polylines;
    private static final int[] COLORS = new int[]{R.color.colorPrimary, R.color.colorPrimaryDark, R.color.colorAccent, R.color.colorAccent, R.color.primary_dark_material_light};

    //Google ApiClient
    private GoogleApiClient googleApiClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_request);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        dist = findViewById(R.id.dist);
        durt = findViewById(R.id.duration);
        name = findViewById(R.id.nameRequest);
        phone = findViewById(R.id.phoneRequest);
        email = findViewById(R.id.emailRequest);


        Intent intent=getIntent();

        //Initializing googleapi client
        storelocation = new LatLng(29.9550304,30.9113914);
        ;

        polylines = new ArrayList<>();
        googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        //Initializing views and adding onclick listeners
        buttonCurrent =  findViewById(R.id.buttonCurrent);
        buttonConform =  findViewById(R.id.buttonConform);
        buttonConform.setEnabled(false);
        buttonCurrent.setOnClickListener(this);
        buttonConform.setOnClickListener(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            CheckLocationPermission();
        }

       // userRequest();

    }

    private Boolean CheckLocationPermission() {

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.ACCESS_FINE_LOCATION)) {
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION_CODE);
            } else {
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION_CODE);

            }
            return false;
        } else
            return true;

    }
    @Override
    protected void onStart() {
        googleApiClient.connect();
        super.onStart();
    }

    @Override
    protected void onStop() {
        googleApiClient.disconnect();
        super.onStop();
    }

    //Getting current location
    private void getCurrentLocation() {
        mMap.clear();
        //Creating a location object
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location location = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
        if (location != null) {
            //Getting longitude and latitude
            longitude = location.getLongitude();
            latitude = location.getLatitude();

            //moving the map to location
            moveMap();


        }
    }

    //Function to move the map
    private void moveMap() {
        //String to display current latitude and longitude
        String msg = latitude + ", " + longitude;

        //Creating a LatLng Object to store Coordinates
        latLnglocation = new LatLng(latitude, longitude);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        //Adding marker to map
        mMap.addMarker(new MarkerOptions()
                .position(latLnglocation) //setting position
                .draggable(true) //Making the marker draggable
                .title("Current Location")); //Adding a title

        //Moving the camera
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLnglocation));

        //Animating the camera
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
        //Displaying current coordinates in toast
        //   Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
        getRouteToMarker(latLnglocation);



    }

    private void getRouteToMarker(LatLng latLng) {
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("Pramadic");
        Routing routing = new Routing.Builder()
                .travelMode(AbstractRouting.TravelMode.DRIVING)
                .withListener(this)
                .alternativeRoutes(false)
                .waypoints(latLnglocation, latLng)
                .build();
        routing.execute();
        mMap.addMarker(markerOptions);
        //Constant.lat= String.valueOf(latLng.latitude);
        //Constant.log= String.valueOf(latLng.longitude);
    }
    public LatLng nearest(LatLng latLng){
        List<LatLng>latLngs=new ArrayList<>();
        List<Double>distance=new ArrayList<>();

        latLngs.add(storelocation);
        for (int i = 0; i<latLngs.size(); i++)
        {
            distance.set(i, SphericalUtil.computeDistanceBetween(latLng, latLngs.get(i)));

        }


        return null;
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng latLng = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(latLng).draggable(true));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.setOnMarkerDragListener(this);
        mMap.setOnMapLongClickListener(this);

    }

    @Override
    public void onConnected(Bundle bundle) {
        getCurrentLocation();
      //  userRequest();

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    public void onMapLongClick(LatLng latLng) {
        //Clearing all the markers
        mMap.clear();

        //Adding a new marker to the current pressed position
        mMap.addMarker(new MarkerOptions()
                .position(latLng)
                .draggable(true));
    }

    @Override
    public void onMarkerDragStart(Marker marker) {

    }

    @Override
    public void onMarkerDrag(Marker marker) {

    }

    @Override
    public void onMarkerDragEnd(Marker marker) {
        //Getting the coordinates
        latitude = marker.getPosition().latitude;
        longitude = marker.getPosition().longitude;

        //Moving the map
        moveMap();
    }

    @Override
    public void onClick(View v) {
        if (v == buttonCurrent) {
            getCurrentLocation();
            moveMap();
            userRequest();

        }
        else   if (v == buttonConform) {

            userConfirm();
        }
    }

    @Override
    public void onRoutingFailure(RouteException e) {
        if (e != null) {
            //   Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
        } else {
            //  Toast.makeText(this, "Something went wrong, Try again", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRoutingStart() {

    }

    @Override
    public void onRoutingSuccess(ArrayList<Route> route, int shortridindex) {

        if (polylines.size() > 0) {
            for (Polyline poly : polylines) {
                poly.remove();
            }
        }

        polylines = new ArrayList<>();
        //add route(s) to the map.
        for (int i = 0; i < route.size(); i++) {

            //In case of more than 5 alternative routes
            int colorIndex = i % COLORS.length;

            PolylineOptions polyOptions = new PolylineOptions();
            polyOptions.color(getResources().getColor(COLORS[colorIndex]));
            polyOptions.width(10 + i * 3);
            polyOptions.addAll(route.get(i).getPoints());
            Polyline polyline = mMap.addPolyline(polyOptions);
            polylines.add(polyline);

            //    Toast.makeText(getApplicationContext(), "Route " + (i + 1) + ": distance - " + route.get(i).getDistanceValue() + ": duration - " + route.get(i).getDurationValue(), Toast.LENGTH_SHORT).show();
            StringBuilder sb = new StringBuilder();
            StringBuilder pp = new StringBuilder();
            StringBuilder cos = new StringBuilder();
int dst= (int) (route.get(i).getDistanceValue()*0.001);
            sb.append(dst);
            pp.append(route.get(i).getDurationValue()/60);
            total_cost=(route.get(i).getDistanceValue()*0.001)*2;

            cos.append(total_cost);
            ditance = sb.toString();
            duration = pp.toString();

            dist.setText(ditance+" KM ");
            durt.setText(duration+" Min");

//            textCost.setText(cos.toString()+" EGP");
            // textCost.setText();


        }

    }

    @Override
    public void onRoutingCancelled() {

    }


    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderDisabled(String provider) {
        Toast.makeText( getApplicationContext(),"Gps Disabled  please Enable",Toast.LENGTH_LONG ).show();
    }


    @Override
    public void onProviderEnabled(String provider) {
        Toast.makeText( getApplicationContext(),"Gps Enabled",Toast.LENGTH_SHORT).show();
    }

    private void userRequest(){

        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                Constants.show_Map ,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);
                            id_moss=obj.getInt("id");
                                   name.setText( "Name : "+obj.getString("name"));
                                   phone.setText( "Phone : 0"+obj.getString("phone"));
                                   email.setText( "Email : "+obj.getString("email"));

                             latLngt=new LatLng( obj.getDouble("lat"),obj.getDouble("lang"));
                            getRouteToMarker(latLngt);
                            buttonConform.setEnabled(true);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(
                                getApplicationContext(),
                                error.getMessage(),
                                Toast.LENGTH_LONG
                        ).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id",String.valueOf(sharedprefmanger.getInstance(Patient_request.this).getId()));
                params.put("lat", String.valueOf(latitude));
                params.put("lang", String.valueOf(longitude));

                return params;
            }

        };

        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }

    private void userConfirm(){

        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                Constants.ConfirM_Map ,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);
                            Toast.makeText(
                                    getApplicationContext(),
                                    obj.getString("Mesag"),
                                    Toast.LENGTH_LONG
                            ).show();



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(
                                getApplicationContext(),
                                error.getMessage(),
                                Toast.LENGTH_LONG
                        ).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id",String.valueOf(sharedprefmanger.getInstance(Patient_request.this).getId()));
                params.put("id_P",String.valueOf(id_moss));
                params.put("lat", String.valueOf(myLocationListener.latitude));
                params.put("lang", String.valueOf(myLocationListener.longitude));

                return params;
            }

        };

        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }

}
