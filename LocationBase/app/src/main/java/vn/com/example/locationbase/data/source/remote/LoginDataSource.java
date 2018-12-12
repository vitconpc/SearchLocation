package vn.com.example.locationbase.data.source.remote;

import android.content.Context;

public interface LoginDataSource {
    interface LoginRemote {
        void checkLogin(String email, String password, LoginFetchData listener, Context context);
    }

    interface LoginFetchData {
        void loginSuccess();

        void wrongEmailorPassword(String error);

        void loginNotVerified(String error);
    }
}
