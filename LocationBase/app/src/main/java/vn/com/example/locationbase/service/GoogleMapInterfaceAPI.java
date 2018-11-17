package vn.com.example.locationbase.service;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import vn.com.example.locationbase.data.model.direction.DirectionResultResponse;
import vn.com.example.locationbase.data.model.place.GoogleAddressResponse;
import vn.com.example.locationbase.data.model.place.PlaceResultResponse;

public interface GoogleMapInterfaceAPI {
    @GET("place/nearbysearch/json?")
    Call<PlaceResultResponse> searchNearBy(@Query("location") String locate, @Query("rankby") String rankby
            , @Query("type") String type, @Query("keyword") String keyword, @Query("key") String API_KEY,
                                           @Query("pagetoken") String token);

    @GET("directions/json?")
    Call<DirectionResultResponse> getDirection(@Query("origin") String origin, @Query("destination") String destination
            , @Query("mode") String vehicle, @Query("key") String API_KEY);

    @GET("geocode/json?")
    Call<GoogleAddressResponse> getAddress(@Query("latlng") String latlon, @Query("key") String apiKey);
}
