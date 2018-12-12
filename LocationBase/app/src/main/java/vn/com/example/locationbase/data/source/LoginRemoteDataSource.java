package vn.com.example.locationbase.data.source;

import android.content.Context;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.com.example.locationbase.common.Base64AccountUtils;
import vn.com.example.locationbase.common.Utils;
import vn.com.example.locationbase.data.model.response.AccessToken;
import vn.com.example.locationbase.data.model.response.ResponseBody;
import vn.com.example.locationbase.data.source.remote.LoginDataSource;
import vn.com.example.locationbase.service.ServerApiUtils;
import vn.com.example.locationbase.service.ServiceInterfaceAPI;

public class LoginRemoteDataSource implements LoginDataSource.LoginRemote {
    private static LoginRemoteDataSource dataSource;
    private ServiceInterfaceAPI Api;

    public LoginRemoteDataSource() {
        Api = ServerApiUtils.getInstance();
    }

    public synchronized static LoginRemoteDataSource getInstance() {
        if (dataSource == null) {
            dataSource = new LoginRemoteDataSource();
        }
        return dataSource;
    }


    @Override
    public void checkLogin(String email, String password,
                           final LoginDataSource.LoginFetchData listener, final Context context) {
        Api.login(Base64AccountUtils.getBase64Account(email, password)).enqueue(new Callback<ResponseBody<AccessToken>>() {
            @Override
            public void onResponse(Call<ResponseBody<AccessToken>> call, Response<ResponseBody<AccessToken>> response) {
                switch (response.code()) {
                    case 200:{
                        Utils.saveAccessToken(context, response.body().getData());
                        listener.loginSuccess();
                    }
                    break;
                    case 401:{
                        listener.loginNotVerified("Vui lòng vào email để xác nhận");
                    }
                    break;
                    default:{
                        listener.loginNotVerified("Đăng nhập thất bại");
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody<AccessToken>> call, Throwable t) {
                listener.loginNotVerified("Vui lòng kiểm tra lại kết nối");
            }
        });
    }
}
