package vn.com.example.locationbase.data.reponsitory;

import android.util.Log;

import vn.com.example.locationbase.data.model.place.Location;
import vn.com.example.locationbase.data.model.place.PlaceResult;
import vn.com.example.locationbase.data.source.SearchNearByDataSource;
import vn.com.example.locationbase.data.source.remote.SearchNearByRemote;

public class SearchNearByReponsitory {
    private static SearchNearByReponsitory instance;
    private SearchNearByRemote.SearchNearByDataSource dataSource;

    public SearchNearByReponsitory(SearchNearByRemote.SearchNearByDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public synchronized static SearchNearByReponsitory getInstance() {
        if (instance == null) {
            instance = new SearchNearByReponsitory(SearchNearByDataSource.getInstance());
        }
        return instance;
    }
    //todo call dataSource
    public void searchPlaceNearBy(Location location, int time, String keyword, String vehicle, String type, float rate,
                                  SearchNearByRemote.SearchNearByFetchData listener, String nextPageToken) {
        dataSource.searchPlaceNearBy(location, time, keyword, vehicle, type, rate, listener, nextPageToken);
    }

    public void searchTimeDirection(Location location, PlaceResult result, String vehicle, int time,
                                    SearchNearByRemote.SearchNearByFetchData listener) {
        dataSource.searchPlaceTime(location, result, vehicle, time, listener);
    }
}
