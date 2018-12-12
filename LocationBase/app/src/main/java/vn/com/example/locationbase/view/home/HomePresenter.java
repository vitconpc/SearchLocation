package vn.com.example.locationbase.view.home;

import android.content.Context;

import com.google.android.gms.maps.model.LatLng;

import vn.com.example.locationbase.data.model.direction.DirectionResultResponse;
import vn.com.example.locationbase.data.model.place.GoogleAddressResponse;
import vn.com.example.locationbase.data.model.place.Location;
import vn.com.example.locationbase.data.model.place.PlaceResultResponse;
import vn.com.example.locationbase.data.model.place_detail.PlaceDetail;
import vn.com.example.locationbase.data.model.response.User;
import vn.com.example.locationbase.data.reponsitory.HomeReponsitory;
import vn.com.example.locationbase.data.source.remote.HomeDataSource;

public class HomePresenter implements HomeContact.Presenter, HomeDataSource.HomeFetchData {
    private HomeReponsitory reponsitory;
    private HomeContact.View view;
    private Context context;

    public HomePresenter(HomeContact.View view, Context context) {
        this.view = view;
        this.context = context;
        this.reponsitory = HomeReponsitory.getInstance();
    }

    //todo get information
    @Override
    public void getData() {
        reponsitory.getData(this, context);
    }

    @Override
    public void getDataSuccess(User user) {
        view.getDataSuccess(user);
    }

    @Override
    public void getDataError(String error) {
        view.getDataError(error);
    }

    //get address
    @Override
    public void getAddress(LatLng latLng) {
        reponsitory.getAddress(latLng, this);
    }

    //todo get place detail
    public void getPlaceDetail(String placeID) {
        reponsitory.getPlaceDetail(placeID, this);
    }

    @Override
    public void getAddressSucess(GoogleAddressResponse response) {
        view.getAddressSucess(response);
    }

    @Override
    public void getAddressFail() {
        view.getAddressFail();
    }


    @Override
    public void getDirectionSuccess(DirectionResultResponse response) {
        view.getDirectionSuccess(response);
    }

    @Override
    public void getDirectionFail() {
        view.getDirectionFail();
    }

    @Override
    public void direction(LatLng origin, LatLng destination) {
        reponsitory.getDirection(origin, destination, this);
    }

    @Override
    public void getPlaceDetailSuccess(PlaceDetail placeDetail) {
        view.getPlaceDetailSuccess(placeDetail);
    }

    @Override
    public void getPlaceDetailError() {
        view.getPlaceDetailFail();
    }

    @Override
    public void savePlaceDetail(PlaceDetail detail) {
        reponsitory.savePlaceDetail(detail, this, context);
    }

    @Override
    public void saveSuccess() {
        view.saveSuccess();
    }

    @Override
    public void saveFail() {
        view.savrFail();
    }

    @Override
    public void getProfile() {
        reponsitory.getProfile(this, context);
    }

    @Override
    public void getProfileSuccess(User user) {
        view.getProfileSuccess(user);
    }

    @Override
    public void getProfileError() {
        view.getProfileFail();
    }
}
