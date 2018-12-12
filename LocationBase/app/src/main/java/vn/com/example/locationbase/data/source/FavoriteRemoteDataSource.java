package vn.com.example.locationbase.data.source;

import android.content.Context;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.com.example.locationbase.common.Utils;
import vn.com.example.locationbase.data.model.response.ResponseBody;
import vn.com.example.locationbase.data.model.response.SaveLocation;
import vn.com.example.locationbase.data.source.remote.FavoriteDataSource;
import vn.com.example.locationbase.service.ServerApiUtils;
import vn.com.example.locationbase.service.ServiceInterfaceAPI;

public class FavoriteRemoteDataSource implements FavoriteDataSource.FavoriteRemoteData {
    private static FavoriteRemoteDataSource instance;
    private ServiceInterfaceAPI api;

    public FavoriteRemoteDataSource() {
        api = ServerApiUtils.getInstance();
    }

    public synchronized static FavoriteRemoteDataSource getInstance() {
        if (instance == null) {
            instance = new FavoriteRemoteDataSource();
        }
        return instance;
    }

    @Override
    public void getListPlace(Context context, final FavoriteDataSource.FavoriteFetchData listener) {
        api.getListSaveLocation(Utils.getBearerAccessToken(context)).enqueue(new Callback<ResponseBody<List<SaveLocation>>>() {
            @Override
            public void onResponse(Call<ResponseBody<List<SaveLocation>>> call, Response<ResponseBody<List<SaveLocation>>> response) {
                switch (response.code()) {
                    case 200:
                        listener.getListSuccess(response.body().getData());
                        break;
                    default:
                        listener.getListError();
                        break;
                }
            }

            @Override
            public void onFailure(Call<ResponseBody<List<SaveLocation>>> call, Throwable t) {
                listener.getListError();
            }
        });
    }

    @Override
    public void deletePlace(String id, Context context, final FavoriteDataSource.FavoriteFetchData listener) {
        api.deleteSaveLocation(Utils.getBearerAccessToken(context), id).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                switch (response.code()) {
                    case 200:
                        listener.deletePlaceSuccess();
                        break;
                    default:
                        listener.deletePlaceError();
                        break;
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                listener.deletePlaceError();
            }
        });
    }
}
