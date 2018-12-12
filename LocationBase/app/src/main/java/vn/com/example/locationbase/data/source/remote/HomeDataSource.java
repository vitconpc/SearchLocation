package vn.com.example.locationbase.data.source.remote;

import android.content.Context;

import com.google.android.gms.maps.model.LatLng;

import vn.com.example.locationbase.data.model.direction.DirectionResultResponse;
import vn.com.example.locationbase.data.model.place.GoogleAddressResponse;
import vn.com.example.locationbase.data.model.place.Location;
import vn.com.example.locationbase.data.model.place.PlaceResultResponse;
import vn.com.example.locationbase.data.model.place_detail.PlaceDetail;
import vn.com.example.locationbase.data.model.response.User;

public interface HomeDataSource {
    interface HomeRemote {

        void getData(HomeDataSource.HomeFetchData listener, Context context);

        void getAddress(LatLng location, HomeDataSource.HomeFetchData listener);

        void getPlaceDetail(String placeId, HomeDataSource.HomeFetchData listener);

        void getDirection(LatLng origin, LatLng destination, HomeFetchData listener);

        void savePlaceDetail(PlaceDetail detail, HomeFetchData listener, Context context);

        void getProfile(HomeFetchData listener, Context context);

    }

    interface HomeFetchData {
        //get information
        void getDataSuccess(User user);

        void getDataError(String error);

        void getAddressSucess(GoogleAddressResponse response);

        void getAddressFail();

        void getDirectionSuccess(DirectionResultResponse response);

        void getDirectionFail();

        void getPlaceDetailSuccess(PlaceDetail placeDetail);

        void getPlaceDetailError();

        void saveSuccess();

        void saveFail();

        void getProfileSuccess(User user);

        void getProfileError();
    }
}
