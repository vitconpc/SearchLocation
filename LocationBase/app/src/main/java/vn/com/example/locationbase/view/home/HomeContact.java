package vn.com.example.locationbase.view.home;

import com.google.android.gms.maps.model.LatLng;

import vn.com.example.locationbase.data.model.direction.DirectionResultResponse;
import vn.com.example.locationbase.data.model.place.GoogleAddressResponse;
import vn.com.example.locationbase.data.model.place.Location;
import vn.com.example.locationbase.data.model.place.PlaceResultResponse;
import vn.com.example.locationbase.data.model.place_detail.PlaceDetail;
import vn.com.example.locationbase.data.model.response.User;

public interface HomeContact {
    interface View {
        //login
        void getDataSuccess(User user);

        void getDataError(String error);

        void getAddressSucess(GoogleAddressResponse response);

        void getAddressFail();

        void getDirectionSuccess(DirectionResultResponse response);

        void getDirectionFail();

        void getPlaceDetailSuccess(PlaceDetail detail);

        void getPlaceDetailFail();

        void saveSuccess();

        void savrFail();

        void getProfileSuccess(User user);

        void getProfileFail();

    }

    interface Presenter {

        void getData();

        void getAddress(LatLng latLng);

        void direction(LatLng origin, LatLng destination);

        void savePlaceDetail(PlaceDetail detail);

        void getProfile();
    }

}
