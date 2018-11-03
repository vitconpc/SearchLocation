package vn.com.example.locationbase.view.search_near_by;

import vn.com.example.locationbase.data.model.place.Location;
import vn.com.example.locationbase.data.model.place.PlaceResult;

public interface SearchKeyContact {
    interface View {
        void onSearchSuccess(PlaceResult result);

        void onSearchFail(String error);

        void onSearchStop();
    }

    interface Presenter {
        void onSearchPlace(Location location, int time, String keyword, String vehicle, String type, float rate,
                           String nextPageToken);
    }
}
