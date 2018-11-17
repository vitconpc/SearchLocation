package vn.com.example.locationbase.data.source.remote;

import vn.com.example.locationbase.data.model.place.Location;
import vn.com.example.locationbase.data.model.place.PlaceResult;
import vn.com.example.locationbase.data.model.place.PlaceResultResponse;

public interface SearchNearByRemote {
    interface SearchNearByDataSource {
        void searchPlaceNearBy(Location location, int time, String keyword, String vehicle, String type, float rate,
                                      SearchNearByRemote.SearchNearByFetchData listener, String nextPageToken);

        void searchPlaceTime(Location location,PlaceResult result,String vehicle, int time,
                             SearchNearByRemote.SearchNearByFetchData listener);
    }

    interface SearchNearByFetchData {
        void searchPlaceSuccess(PlaceResultResponse response, Location location, int time, String keyword, String vehicle,
                                String type, float rate);

        void searchPlaceError();

        void placeSuccess(PlaceResult result);
    }
}
