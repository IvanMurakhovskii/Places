package com.murik.places;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    public static final String  BASE_URL = "https://maps.googleapis.com/";
    public static final String  API_PLACES_KEY = "AIzaSyAjZVQoj8mKQDalDGHG8D1JNNSQA_rQKcs";

    private static final RetrofitClient uniqueRetrofitClient = new RetrofitClient();
    private static Retrofit retrofit;

    private RetrofitClient() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    public static RetrofitClient getInstance() {
        return uniqueRetrofitClient;
    }

    public  Retrofit getRetrofit() {
        return retrofit;
    }
}

