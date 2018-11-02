package vn.com.example.locationbase.view.login;

public interface LoginContact {
    interface View {
        void LoginSuccess();
        void LoginFail(String error);
    }

    interface Presenter {
        void Login(String user, String Password);
    }
}
