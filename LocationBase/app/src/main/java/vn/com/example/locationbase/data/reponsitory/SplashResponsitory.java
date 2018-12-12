package vn.com.example.locationbase.data.reponsitory;

import android.content.Context;

import vn.com.example.locationbase.data.source.SplashRemoteDataSource;
import vn.com.example.locationbase.data.source.remote.SplashDataSource;

public class SplashResponsitory {
    private static SplashResponsitory instance;
    private SplashDataSource.SplashRemote dataSource;

    private SplashResponsitory(SplashDataSource.SplashRemote dataSource) {
        this.dataSource = dataSource;
    }

    public static SplashResponsitory getInstance() {
        if (instance == null){
            instance = new SplashResponsitory(SplashRemoteDataSource.getInstance());
        }
        return instance;
    }

    public void refreshData(Context context, SplashDataSource.SplashFetchData listener){
        dataSource.refreshToken(context,listener);
    }
}
