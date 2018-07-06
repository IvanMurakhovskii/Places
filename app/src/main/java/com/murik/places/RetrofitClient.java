package com.murik.places;

import com.murik.places.model.PlaceResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    public static final String  BASE_URL = "https://maps.googleapis.com/maps/api/place/nearbysearch/";
    public static final String  API_PLACES_KEY = "AIzaSyAjZVQoj8mKQDalDGHG8D1JNNSQA_rQKcs";
    private int radius = 500;
    private String typePlace = "";


    public RetrofitClient() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://maps.googleapis.com/maps/api/place/nearbysearch/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        googlePlacesApi mGooglePlacesApi = retrofit.create(googlePlacesApi.class);

        Call<PlaceResponse> message = mGooglePlacesApi.getPlaces(radius,typePlace, API_PLACES_KEY);

        message.enqueue(new Callback<PlaceResponse>() {
            @Override
            public void onResponse(Call<PlaceResponse> call, Response<PlaceResponse> response) {

                PlaceResponse result = response.body();


            }

            @Override
            public void onFailure(Call<PlaceResponse> call, Throwable t) {
                //Log.d(LOG_TAG,"failure " + t);
            }

        });
    }


}

