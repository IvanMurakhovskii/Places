package com.murik.places;

import com.murik.places.model.PlaceResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface googlePlacesApi {

    @GET("json?location=51.661535,39.200287")
    Call<PlaceResponse> getPlaces(@Query("radius") int radius,
                                  @Query("type") String typePlace,
                                  @Query("key") String apiPlacesKey);

}
