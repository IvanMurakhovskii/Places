package com.murik.places;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.murik.places.adapter.TypePlaceAdapter;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    private RecyclerView mRecycler;

    private static final int PERMISSION_REQUEST_CODE = 100;
    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    public static final String TYPE_PLACE__KEY = "TYPE_PLACE_KEY";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //btnCurrent = findViewById(R.id.btnCurrentPlace);
        //tvinfo = findViewById(R.id.tvInfo);
        List<String> list = Arrays.asList(getResources().getStringArray(R.array.placesType));
        initRecyclerView(list);


    }

    public void initRecyclerView( List<String> list) {

        mRecycler = findViewById(R.id.typePlaceRecyclerView);
        mRecycler.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL));
        mRecycler.setLayoutManager(new LinearLayoutManager(this));
        TypePlaceAdapter recAdapter = new TypePlaceAdapter(this,list);
        mRecycler.setAdapter(recAdapter);
    }





    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                }
                break;
        }
    }


    private boolean checkPlayServices() {
        GoogleApiAvailability googleAPI = GoogleApiAvailability.getInstance();
        int result = googleAPI.isGooglePlayServicesAvailable(this);
        if (result != ConnectionResult.SUCCESS) {
            if (googleAPI.isUserResolvableError(result)) {
                googleAPI.getErrorDialog(this, result, PLAY_SERVICES_RESOLUTION_REQUEST).show();
            }

            return false;
        }
        return true;
    }

}


