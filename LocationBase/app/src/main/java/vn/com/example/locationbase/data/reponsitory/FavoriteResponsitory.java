package vn.com.example.locationbase.data.reponsitory;

import android.content.Context;

import vn.com.example.locationbase.data.source.FavoriteRemoteDataSource;
import vn.com.example.locationbase.data.source.remote.FavoriteDataSource;

public class FavoriteResponsitory {
    private static FavoriteResponsitory instance;
    private FavoriteDataSource.FavoriteRemoteData dataSource;

    private FavoriteResponsitory(FavoriteDataSource.FavoriteRemoteData dataSource) {
        this.dataSource = dataSource;
    }

    public synchronized static FavoriteResponsitory getInstance() {
        if (instance == null) {
            instance = new FavoriteResponsitory(FavoriteRemoteDataSource.getInstance());
        }
        return instance;
    }

    public void getListPlace(Context context, FavoriteDataSource.FavoriteFetchData listener) {
        dataSource.getListPlace(context, listener);
    }

    public void deletePlace(String id, Context context, FavoriteDataSource.FavoriteFetchData listener) {
        dataSource.deletePlace(id,context,listener);
    }
}
