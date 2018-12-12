package vn.com.example.locationbase.data.source;

import android.content.Context;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.com.example.locationbase.common.Constants;
import vn.com.example.locationbase.common.Utils;
import vn.com.example.locationbase.data.model.response.AccessTokenGroup;
import vn.com.example.locationbase.data.model.response.ResponseBody;
import vn.com.example.locationbase.data.source.remote.SplashDataSource;
import vn.com.example.locationbase.service.ServerApiUtils;
import vn.com.example.locationbase.service.ServiceInterfaceAPI;

public class SplashRemoteDataSource implements SplashDataSource.SplashRemote {

    private ServiceInterfaceAPI api;
    private static SplashRemoteDataSource instance;

    private SplashRemoteDataSource() {
        api = ServerApiUtils.getInstance();
    }

    public static SplashRemoteDataSource getInstance() {
        if (instance == null) {
            instance = new SplashRemoteDataSource();
        }
        return instance;
    }

    @Override
    public void refreshToken(final Context context, final SplashDataSource.SplashFetchData listener) {
        String accessToken = Utils.getValueFromPreferences(Constants.PRE_ACCESS_TOKEN_LOGIN, context);
        String refreshToken = Utils.getValueFromPreferences(Constants.PRE_REFRESH_TOKEN_LOGIN, context);
        AccessTokenGroup accessTokenGroup = new AccessTokenGroup(accessToken, refreshToken);
        api.refreshToken(accessTokenGroup).enqueue(new Callback<ResponseBody<AccessTokenGroup>>() {
            @Override
            public void onResponse(Call<ResponseBody<AccessTokenGroup>> call, Response<ResponseBody<AccessTokenGroup>> response) {
                switch (response.code()) {
                    case 200: {
                        listener.refreshSuccess();
                        Utils.saveAccessTokenGroup(context, response.body().getData());
                        break;
                    }
                    default: {
                        listener.refreshError();
                    }
                }

            }

            @Override
            public void onFailure(Call<ResponseBody<AccessTokenGroup>> call, Throwable t) {
                listener.refreshError();
            }
        });
    }
}
