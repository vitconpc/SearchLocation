package vn.com.example.locationbase.data.source.remote;

import android.content.Context;

import java.util.List;

import vn.com.example.locationbase.data.model.response.SaveLocation;

public interface FavoriteDataSource {
    interface FavoriteRemoteData {
        void getListPlace(Context context,FavoriteFetchData listener);

        void deletePlace(String id, Context context,FavoriteFetchData listener);
    }

    interface FavoriteFetchData {
        void getListSuccess(List<SaveLocation> locations);
        void getListError();

        void deletePlaceSuccess();
        void deletePlaceError();
    }
}
