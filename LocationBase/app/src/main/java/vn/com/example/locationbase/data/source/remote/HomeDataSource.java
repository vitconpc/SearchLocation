package vn.com.example.locationbase.data.source.remote;

import com.google.android.gms.maps.model.LatLng;

import vn.com.example.locationbase.data.model.direction.DirectionResultResponse;
import vn.com.example.locationbase.data.model.place.GoogleAddressResponse;
import vn.com.example.locationbase.data.model.place.Location;
import vn.com.example.locationbase.data.model.place.PlaceResultResponse;

public interface HomeDataSource {
    interface HomeRemote {
        void LogOut(HomeDataSource.HomeFetchData listener);

        void getData(HomeDataSource.HomeFetchData listener);

        void checkLogin(HomeDataSource.HomeFetchData listener);

        void searchNearBy(LatLng location, float range, String type
                , String keyWord, HomeDataSource.HomeFetchData listener);

        void getAddress(LatLng location, HomeDataSource.HomeFetchData listener);

        void getDirection(LatLng origin, LatLng destination, HomeFetchData listener);
    }

    interface HomeFetchData {
        //logout
        void LogOutSuccess();

        //get information
        void getDataSuccess(String name, String email, String uri);

        void getDataError(String error);

        //login
        void LoginSuccess();

        void LoginFail();

        //search near by
        void searchNearBySuccess(PlaceResultResponse response);

        void searchNearByFail(String error);

        void getAddressSucess(GoogleAddressResponse response);

        void getAddressFail();

        void getDirectionSuccess(DirectionResultResponse response);

        void getDirectionFail();
    }
}
