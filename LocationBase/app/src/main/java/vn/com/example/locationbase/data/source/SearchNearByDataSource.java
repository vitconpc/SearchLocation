package vn.com.example.locationbase.data.source;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.com.example.locationbase.data.model.direction.DirectionResultResponse;
import vn.com.example.locationbase.data.model.place.Location;
import vn.com.example.locationbase.data.model.place.PlaceResult;
import vn.com.example.locationbase.data.model.place.PlaceResultResponse;
import vn.com.example.locationbase.data.source.remote.SearchNearByRemote;
import vn.com.example.locationbase.service.APIUtils;
import vn.com.example.locationbase.service.GoogleMapInterfaceAPI;

public class SearchNearByDataSource implements SearchNearByRemote.SearchNearByDataSource {

    private static SearchNearByDataSource instance;
    private GoogleMapInterfaceAPI interfaceAPI;
    private String KEY = "AIzaSyBqo1CVj_866CQTVT1lCwXVBog4fCVtUGc";

    public SearchNearByDataSource() {
        interfaceAPI = APIUtils.getInstance();
    }

    public synchronized static SearchNearByDataSource getInstance() {
        if (instance == null) {
            instance = new SearchNearByDataSource();
        }
        return instance;
    }

    @Override
    public void searchPlaceNearBy(final Location location, final int time, final String keyword, final String vehicle,
                                  final String type, final float rate,
                                  final SearchNearByRemote.SearchNearByFetchData listener, final String nextPageToken) {
        String place = location.getLat() + "," + location.getLng();
        interfaceAPI.searchNearBy(place, "distance", type, keyword, KEY, nextPageToken).enqueue(
                new Callback<PlaceResultResponse>() {
                    @Override
                    public void onResponse(Call<PlaceResultResponse> call, Response<PlaceResultResponse> response) {
                        if (response.body().getResults().isEmpty()) {
                            listener.searchPlaceEmpty();
                        } else {
                            listener.searchPlaceSuccess(response.body(), location, time, keyword, vehicle,
                                    type, rate, nextPageToken);
                        }
                    }

                    @Override
                    public void onFailure(Call<PlaceResultResponse> call, Throwable t) {
                        listener.searchPlaceError();
                    }
                });
    }

    @Override
    public void searchPlaceTime(Location location, final PlaceResult result, String vehicle, final int time,
                                final SearchNearByRemote.SearchNearByFetchData listener) {
        String startLocation = location.getLat() + "," + location.getLng();
        String endLocation = result.getGeometry().getLocation().getLat() + "," +
                result.getGeometry().getLocation().getLng();
        interfaceAPI.getDirection(startLocation, endLocation, vehicle, KEY).enqueue(
                new Callback<DirectionResultResponse>() {
                    @Override
                    public void onResponse(Call<DirectionResultResponse> call, Response<DirectionResultResponse> response) {
                        if (response.body().getRoutes().get(0).getLegs().get(0).getDuration().getValue() < time * 60) {
                            listener.placeSuccess(result);
                        }
                    }

                    @Override
                    public void onFailure(Call<DirectionResultResponse> call, Throwable t) {
                        listener.searchPlaceError();
                    }
                }
        );
    }
}
