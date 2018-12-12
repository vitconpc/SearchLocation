package vn.com.example.locationbase.view.splash;

public interface SplashContact {
    interface View {
        void refreshSuccess();
        void refreshError();
    }

    interface Presenter {
        void refreshToken();
    }
}
