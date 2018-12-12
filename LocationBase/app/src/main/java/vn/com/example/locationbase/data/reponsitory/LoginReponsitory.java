package vn.com.example.locationbase.data.reponsitory;


import android.content.Context;

import vn.com.example.locationbase.data.source.LoginRemoteDataSource;
import vn.com.example.locationbase.data.source.remote.LoginDataSource;

public class LoginReponsitory {
    private static LoginReponsitory reponsitory;
    private LoginDataSource.LoginRemote loginRemote;

    public LoginReponsitory(LoginDataSource.LoginRemote loginRemote) {
        this.loginRemote = loginRemote;
    }

    public synchronized static LoginReponsitory getInstance() {
        if (reponsitory == null) {
            reponsitory = new LoginReponsitory(LoginRemoteDataSource.getInstance());
        }
        return reponsitory;
    }

    public void checkLogin(String email, String password, LoginDataSource.LoginFetchData listener, Context context) {
        loginRemote.checkLogin(email, password, listener, context);
    }
}
