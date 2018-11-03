package vn.com.example.locationbase.view.home;

import com.google.android.gms.maps.model.LatLng;

import vn.com.example.locationbase.data.model.place.PlaceResultResponse;

public interface HomeContact {
    interface View {
        void LogOutSuccess();
        void getDataSuccess(String name, String email, String uri);
        void getDataError(String error);
        //check Login
        void LoginSuccess();
        void LoginFail();
        //search near by
        void searchNearBySuccess(PlaceResultResponse response);
        void searchNearByError(String error);
    }

    interface Presenter {
        void LogOut();
        void getData();
        void Login();
        void searchNearBy(LatLng location,float range, String type, String keyword);
    }

}
