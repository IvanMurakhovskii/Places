package com.murik.places;

import com.murik.places.model.PlaceResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface googlePlacesApi {

    @GET("maps/api/place/nearbysearch/json?")
    Call<PlaceResponse> getPlaces(@Query("location") String location,
                                  @Query("radius") int radius,
                                  @Query("type") String typePlace,
                                  @Query("key") String apiPlacesKey);

}
//location=51.661535,39.200287