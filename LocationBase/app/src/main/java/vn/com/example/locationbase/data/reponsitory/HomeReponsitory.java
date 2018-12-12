package vn.com.example.locationbase.data.reponsitory;

import android.content.Context;

import com.google.android.gms.maps.model.LatLng;

import vn.com.example.locationbase.data.model.place.Location;
import vn.com.example.locationbase.data.model.place_detail.PlaceDetail;
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

    public void getData(HomeDataSource.HomeFetchData listener, Context context){
        datasource.getData(listener,context);
    }


    public void getAddress(LatLng latLng, HomeDataSource.HomeFetchData listener){
        datasource.getAddress(latLng,listener);
    }

    public void getDirection(LatLng origin, LatLng destination, HomeDataSource.HomeFetchData listener){
        datasource.getDirection(origin,destination,listener);
    }

    public void getPlaceDetail(String placeID, HomeDataSource.HomeFetchData listener){
        datasource.getPlaceDetail(placeID,listener);
    }

    public void savePlaceDetail(PlaceDetail detail, HomeDataSource.HomeFetchData listener,Context context){
        datasource.savePlaceDetail(detail,listener,context);
    }

    public void getProfile(HomeDataSource.HomeFetchData listener, Context context){
        datasource.getProfile(listener,context);
    }
}
