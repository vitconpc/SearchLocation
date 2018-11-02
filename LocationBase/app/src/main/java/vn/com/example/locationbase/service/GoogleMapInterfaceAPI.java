package vn.com.example.locationbase.service;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import vn.com.example.locationbase.data.model.PlaceResultResponse;

public interface GoogleMapInterfaceAPI {
    @GET("place/nearbysearch/json?")
    Call<PlaceResultResponse> searchNearBy(@Query("location") String locate, @Query("radius") float range
            , @Query("type") String type,@Query("keyword") String keyword, @Query("key") String API_KEY);
}
