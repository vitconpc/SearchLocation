package vn.com.example.locationbase.view.login;

import android.content.Context;

public interface LoginContact {
    interface View {
        void LoginSuccess();
        void LoginFail(String error);
    }

    interface Presenter {
        void Login(String user, String Password);
    }
}
