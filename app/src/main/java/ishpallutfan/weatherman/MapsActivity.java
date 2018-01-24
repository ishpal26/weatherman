package ishpallutfan.weatherman;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Handler;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.android.volley.VolleyError;
//import com.google.android.gms.analytics.HitBuilders;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.MapStyleOptions;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback,
        NavigationView.OnNavigationItemSelectedListener, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    private GoogleMap mMap;
    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    Marker mCurrLocationMarker;
    Marker currentRainMarker;
    LocationRequest mLocationRequest;
    Bundle extraBundle;
    Location location;
    FloatingActionButton fab;

    private static final String URL_PRODUCTS = "http://lidapplications.000webhostapp.com/retrieve.php";
    private static final int ZOOM_INDEX = 12;

    //Creates the map, initializes the toolbar, fab, drawer, navigation view
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        Intent intentExtras = getIntent();
        extraBundle = intentExtras.getExtras();

        fab = (FloatingActionButton) findViewById(R.id.fab);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        //Toast.makeText(this, "isRaining = " +extraBundle.getBoolean("isRaining") , Toast.LENGTH_LONG).show();
    }

    //App drawer behavior
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    //Item selected behaviour
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    //Navigation bar behaviour
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.North_Text) {

        } else if (id == R.id.South_Text) {

        } else if (id == R.id.East_Text) {

        } else if (id == R.id.West_Text) {

        } else if (id == R.id.Central_Text) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */

    //Builds the Google maps
    @Override
    public void onMapReady(GoogleMap googleMap) {

        MapStyleOptions style = MapStyleOptions.loadRawResourceStyle(this, R.raw.style_json);
        googleMap.setMapStyle(style);
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        //Initialize Google Play Services
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                buildGoogleApiClient();
                mMap.setMyLocationEnabled(true);
            } else {
                requestPermissions(new String[] {  android.Manifest.permission.ACCESS_FINE_LOCATION }, MY_PERMISSIONS_REQUEST_LOCATION);
                mMap.setMyLocationEnabled(true);
            }
        }

        else {
            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);
        }
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    //Get the current position
    //Add the marker
    //Moves the camera to current location
    //Inserts the weather data
    @Override
    public void onConnected(Bundle bundle) {

        //Toast.makeText(this, "Connected!" , Toast.LENGTH_LONG).show();
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }

        location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        // if location is not turned on by the user
        if(location == null){

        }

        //Place current location marker
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("Current Position");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
        mCurrLocationMarker = mMap.addMarker(markerOptions);

        //move map camera
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(ZOOM_INDEX));

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveMap();
            }
        });


        // create entry if raining
        if(extraBundle.getBoolean("isRaining") == true) {
            double lat = location.getLatitude();
            double lon = location.getLongitude();
            DateFormat df = new SimpleDateFormat("dd/MM/yyyy-HH:mm");
            Calendar calobj = Calendar.getInstance();
            System.out.println(df.format(calobj.getTime()));
            String DandT = df.format(calobj.getTime()).toString();
            String[] arr = DandT.split("-");
            String date = arr[0];
            String time = arr[1];

            Response.Listener<String> responseListener = new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        boolean success = jsonObject.getBoolean("success");

                        if (success == true)
                            System.out.print("great success");
                        else
                            System.out.print("failed");

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            };


            //Should we put this in the splash screen?
            RainingRequest req = new RainingRequest(1, lat, lon, date, time, responseListener);
            RequestQueue queue = Volley.newRequestQueue(MapsActivity.this);
            queue.add(req);

        }

        int count = 0;
        final List<Data> weatherData = new ArrayList<Data>();
        Toast.makeText(this, "after weatherData", Toast.LENGTH_LONG).show();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_PRODUCTS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(MapsActivity.this, "response = " + response, Toast.LENGTH_LONG).show();
                        while(true) {if(response != null) break;}
                        try {
                            //converting the string to json array object
                            JSONArray array = new JSONArray(response);

                            Toast.makeText(MapsActivity.this, "array.length = " + array.length(), Toast.LENGTH_LONG).show();

                            //traversing through all the object
                            for (int i = 0; i < array.length(); i++) {
                                //getting product object from json array
                                JSONObject data = array.getJSONObject(i);

                                double currentLatitude = Double.parseDouble(data.getString("latitude"));
                                double currentLongtitude = Double.parseDouble(data.getString("longtitude"));
                                MarkerOptions currentRain = new MarkerOptions();
                                currentRain.position(new LatLng(currentLatitude, currentLongtitude));
                                currentRain.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
                                currentRainMarker = mMap.addMarker(currentRain);

                                //adding the product to product list
                                /*weatherData.add(new Data(
                                        data.getInt("id"),
                                        data.getString("time"),
                                        data.getString("latitude"),
                                        data.getString("longtitude")
                                ));*/

                            }

                            //creating adapter object and setting it to recyclerview
                            //ProductsAdapter adapter = new ProductsAdapter(MainActivity.this, productList);
                            //recyclerView.setAdapter(adapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        //adding our stringrequest to queue
        Volley.newRequestQueue(this).add(stringRequest);

        /*for(Data currentData : weatherData) {
            double currentLatitude = Double.parseDouble(currentData.getLatitude());
            double currentLongtitude = Double.parseDouble(currentData.getLongtitude());
            Toast.makeText(this, "currentLong = " + currentLongtitude , Toast.LENGTH_LONG).show();
            MarkerOptions currentRain = new MarkerOptions();
            currentRain.position(new LatLng(currentLatitude, currentLongtitude));
            currentRain.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
            currentRainMarker = mMap.addMarker(currentRain);
        }*/
    }


    @Override
    public void onConnectionSuspended(int i) {
        //Toast.makeText(this, "Connection Suspended" , Toast.LENGTH_LONG).show();
    }


    @Override
    public void onLocationChanged(Location location) {

        //Toast.makeText(this, "Location Changed" , Toast.LENGTH_LONG).show();
        mLastLocation = location;
        if (mCurrLocationMarker != null) {
            mCurrLocationMarker.remove();
        }

        //Place current location marker
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("Current Position");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
        mCurrLocationMarker = mMap.addMarker(markerOptions);

        //move map camera
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(ZOOM_INDEX));

        //stop location updates
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        //Toast.makeText(this, "Connection Failed" , Toast.LENGTH_LONG).show();
    }

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    public boolean checkLocationPermission(){

        //Toast.makeText(this, "Check Location Permission" , Toast.LENGTH_LONG).show();
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Asking user if explanation is needed
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                 //Prompt the user once explanation has been shown
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);

                } else {
                    // No explanation needed, we can request the permission.
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                            MY_PERMISSIONS_REQUEST_LOCATION);
                }
                return false;
            } else {
                return true;
            }
        }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        //Toast.makeText(this, "On request Permission Result" , Toast.LENGTH_LONG).show();
        if(mGoogleApiClient == null) {
            //Toast.makeText(this, "There is no API client" , Toast.LENGTH_LONG).show();
        }
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                //Toast.makeText(this, "request code is MY_PERMISSIONS_REQUEST" , Toast.LENGTH_LONG).show();
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted. Do the
                    // contacts-related task you need to do.
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        if (mGoogleApiClient == null) {
                            buildGoogleApiClient();
                            mMap.setMyLocationEnabled(true);

                            //Toast.makeText(this, "Just built Google Api Client" , Toast.LENGTH_LONG).show();

                        }
                        mMap.setMyLocationEnabled(true);
                    }

                } else {

                    // Permission denied, Disable the functionality that depends on this permission.
                    //Toast.makeText(this, "Location services denied. \nPlease re-open app to continue.", Toast.LENGTH_LONG).show();
                }
                return;
            }

            // other 'case' lines to check for other permissions this app might request.
            // You can add here other case statements according to your requirement.
        }
    }

    public void moveMap() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }

        Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

        //Place current location marker
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(ZOOM_INDEX)); //11
    }

}
