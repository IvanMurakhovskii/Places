package com.murik.places;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.murik.places.adapter.PlacesAdapter;
import com.murik.places.model.PlaceResponse;
import com.murik.places.model.Result;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback,
        GoogleMap.OnMapClickListener, View.OnClickListener{

    private GoogleMap mMap;
    private RecyclerView mRecycler;
    private FloatingActionButton fab_bottom_sheet;
    BottomSheetBehavior bottomSheetBehavior;

    Marker marker;
    Marker markerFindPlace;
    LinearLayout layoutBotoom;


    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    private String location = "51.661535,39.200287";
    private String typePlace = "bar";

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);


        layoutBotoom =  findViewById(R.id.bottom_sheet);
        fab_bottom_sheet = findViewById(R.id.fab_bottom_sheet);
        fab_bottom_sheet.setOnClickListener(this);

        bottomSheetBehavior = BottomSheetBehavior.from(layoutBotoom);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);

        Intent intent = getIntent();
        typePlace = intent.getStringExtra(MainActivity.TYPE_PLACE__KEY);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    public void initRecyclerView(PlaceResponse placeResponse) {
        mRecycler = findViewById(R.id.placeRecyclerView);
        mRecycler.setLayoutManager(new LinearLayoutManager(this));
        PlacesAdapter recAdapter = new PlacesAdapter(placeResponse.getResults());
        mRecycler.setAdapter(recAdapter);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMapClickListener(this);
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        LatLng voronezh = new LatLng(51.661535, 39.200287);
        marker = mMap.addMarker(new MarkerOptions().position(voronezh));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(voronezh));
        mMap.moveCamera(CameraUpdateFactory.zoomTo(13));


    }

    @Override
    public void onMapClick(LatLng latLng) {
        mMap.clear();
        location = latLng.latitude + "," + latLng.longitude;
        fab_bottom_sheet.setVisibility(View.VISIBLE);
        marker.remove();
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("Current Position");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
        marker = mMap.addMarker(markerOptions);
        //addInflateFragment();


    }

    public void startRetrofit() {
        Call<PlaceResponse> message =
                RetrofitClient
                        .getInstance()
                        .getRetrofit()
                        .create(googlePlacesApi.class).getPlaces(location, 1000, typePlace, RetrofitClient.API_PLACES_KEY);

        message.enqueue(new Callback<PlaceResponse>() {

            @Override
            public void onResponse(Call<PlaceResponse> call, Response<PlaceResponse> response) {
                if(!response.body().getResults().isEmpty()){
                    createMarkerPlace(response.body().getResults());
                    initRecyclerView(response.body());
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                }
                else {
                    Toast.makeText(getApplicationContext(),  typePlace + " Рядом нет", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<PlaceResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "failure " + t, Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fab_bottom_sheet:
                fab_bottom_sheet.setVisibility(View.GONE);
                startRetrofit();
                break;
        }
    }
    public void createMarkerPlace(List<Result> result){
        if(markerFindPlace != null) {
            markerFindPlace.remove();
        }
        for(Result res : result){
            LatLng latLng = new LatLng(res.getGeometry().getLocation().getLat(), res.getGeometry().getLocation().getLng());
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(latLng);
            markerOptions.title(res.getName());
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
            markerFindPlace = mMap.addMarker(markerOptions);
        }
    }
}

/*  private void checkLocationPermission () {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {

                // Should we show an explanation?
                    new AlertDialog.Builder(this)
                            .setTitle("Location Permission Needed")
                            .setMessage("This app needs the Location permission, please accept to use location functionality")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    //Prompt the user once explanation has been shown
                                    ActivityCompat.requestPermissions(MapsActivity.this,
                                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                            MY_PERMISSIONS_REQUEST_LOCATION);
                                }
                            })
                            .create()
                            .show();


                } else {
                    // No explanation needed, we can request the permission.
                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                            MY_PERMISSIONS_REQUEST_LOCATION);
                }
            }
        }*/


