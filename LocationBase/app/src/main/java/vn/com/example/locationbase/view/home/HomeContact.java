package vn.com.example.locationbase.view.home;

import com.google.android.gms.maps.model.LatLng;

import vn.com.example.locationbase.data.model.direction.DirectionResultResponse;
import vn.com.example.locationbase.data.model.place.GoogleAddressResponse;
import vn.com.example.locationbase.data.model.place.Location;
import vn.com.example.locationbase.data.model.place.PlaceResultResponse;

public interface HomeContact {
    interface View {
        void LogOutSuccess();
        //login
        void getDataSuccess(String name, String email, String uri);
        void getDataError(String error);
        //check Login
        void LoginSuccess();
        void LoginFail();
        //search near by
        void searchNearBySuccess(PlaceResultResponse response);
        void searchNearByError(String error);

        void getAddressSucess(GoogleAddressResponse response);
        void getAddressFail();

        void getDirectionSuccess(DirectionResultResponse response);
        void getDirectionFail();

    }

    interface Presenter {
        void LogOut();
        void getData();
        void Login();
        void getAddress(LatLng latLng);
        void searchNearBy(LatLng location,float range, String type, String keyword);

        void direction(LatLng origin, LatLng destination);
    }

}
