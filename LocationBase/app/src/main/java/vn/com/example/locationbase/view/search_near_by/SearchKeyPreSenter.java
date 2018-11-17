package vn.com.example.locationbase.view.search_near_by;

import vn.com.example.locationbase.data.model.place.Location;
import vn.com.example.locationbase.data.model.place.PlaceResult;
import vn.com.example.locationbase.data.model.place.PlaceResultResponse;
import vn.com.example.locationbase.data.reponsitory.SearchNearByReponsitory;
import vn.com.example.locationbase.data.source.remote.SearchNearByRemote;

public class SearchKeyPreSenter implements SearchKeyContact.Presenter, SearchNearByRemote.SearchNearByFetchData {

    private SearchKeyContact.View view;
    private SearchNearByReponsitory reponsitory;

    public SearchKeyPreSenter(SearchKeyContact.View view) {
        this.view = view;
        reponsitory = SearchNearByReponsitory.getInstance();
    }

    @Override
    public void onSearchPlace(Location location, int time, String keyword, String vehicle, String type, float rate,
                              String nextPageToken) {
        //todo call reponsitory
        reponsitory.searchPlaceNearBy(location, time, keyword, vehicle, type, rate, this, nextPageToken);
    }

    @Override
    public void searchPlaceSuccess(PlaceResultResponse response, Location location, int time,
                                   String keyword, String vehicle, String type, float rate) {
        for (int i = 0; i < response.getResults().size(); i++) {
            PlaceResult result = response.getResults().get(i);
            if (result.getRating() >= rate) {
                reponsitory.searchTimeDirection(location,result,vehicle,time,this);
            }
        }
        if (response.getNextPageToken() != null) {
            reponsitory.searchPlaceNearBy(location, time, keyword, vehicle, type, rate, this, response.getNextPageToken());
        }else {
            view.onSearchStop();
        }
    }

    @Override
    public void searchPlaceError() {
        view.onSearchFail("Vui lòng kiểm tra lại kết nối mạng");
    }

    @Override
    public void placeSuccess(PlaceResult result) {
        view.onSearchSuccess(result);
    }
}
