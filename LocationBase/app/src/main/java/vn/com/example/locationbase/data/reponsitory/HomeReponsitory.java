package vn.com.example.locationbase.data.reponsitory;

import com.google.android.gms.maps.model.LatLng;

import vn.com.example.locationbase.data.source.HomeRemoteDataSource;
import vn.com.example.locationbase.data.source.remote.HomeDataSource;

public class HomeReponsitory {
    private static HomeReponsitory instance;
    private HomeDataSource.HomeRemote datasource;

    public HomeReponsitory(HomeDataSource.HomeRemote datasource) {
        this.datasource = datasource;
    }

    public static HomeReponsitory getInstance() {
        if (instance == null){
            instance = new HomeReponsitory(HomeRemoteDataSource.getInstance());
        }
        return instance;
    }

    public void Logout_User(HomeDataSource.HomeFetchData listener){
        datasource.LogOut(listener);
    }

    public void getData(HomeDataSource.HomeFetchData listener){
        datasource.getData(listener);
    }

    public void LOgin_User(HomeDataSource.HomeFetchData listener){
        datasource.checkLogin(listener);
    }

    public void searchNearBy(LatLng location,float range,String type, String keyword
            ,HomeDataSource.HomeFetchData listener){
        datasource.searchNearBy(location,range,type,keyword,listener);
    }
}
