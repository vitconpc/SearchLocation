package vn.com.example.locationbase.view.home;

import com.google.android.gms.maps.model.LatLng;

import vn.com.example.locationbase.data.model.direction.DirectionResultResponse;
import vn.com.example.locationbase.data.model.place.GoogleAddressResponse;
import vn.com.example.locationbase.data.model.place.Location;
import vn.com.example.locationbase.data.model.place.PlaceResultResponse;
import vn.com.example.locationbase.data.reponsitory.HomeReponsitory;
import vn.com.example.locationbase.data.source.remote.HomeDataSource;

public class HomePresenter implements HomeContact.Presenter, HomeDataSource.HomeFetchData {
    private HomeReponsitory reponsitory;
    private HomeContact.View view;

    public HomePresenter(HomeContact.View view) {
        this.view = view;
        this.reponsitory = HomeReponsitory.getInstance();
    }

    //todo logout
    @Override
    public void LogOut() {
        reponsitory.Logout_User(this);
    }

    @Override
    public void LogOutSuccess() {
        view.LogOutSuccess();
    }

    //todo get information
    @Override
    public void getData() {
        reponsitory.getData(this);
    }

    @Override
    public void getDataSuccess(String name, String email, String uri) {
        view.getDataSuccess(name, email, uri);
    }

    @Override
    public void getDataError(String error) {
        view.getDataError(error);
    }

    //todo Login
    @Override
    public void Login() {
        reponsitory.LOgin_User(this);
    }

    //get address
    @Override
    public void getAddress(LatLng latLng) {
        reponsitory.getAddress(latLng,this);
    }

    @Override
    public void LoginSuccess() {
        view.LoginSuccess();
    }

    @Override
    public void LoginFail() {
        view.LoginFail();
    }

    //todo search near by
    @Override
    public void searchNearBy(LatLng location, float range, String type, String keyword) {
        reponsitory.searchNearBy(location, range, type, keyword, this);
    }

    @Override
    public void searchNearBySuccess(PlaceResultResponse response) {
        view.searchNearBySuccess(response);
    }

    @Override
    public void searchNearByFail(String error) {
        view.searchNearByError(error);
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
        reponsitory.getDirection(origin,destination,this);
    }
}
