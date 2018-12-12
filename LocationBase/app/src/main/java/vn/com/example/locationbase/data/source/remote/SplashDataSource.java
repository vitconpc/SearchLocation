package vn.com.example.locationbase.data.source.remote;

import android.content.Context;

public interface SplashDataSource {
    interface SplashRemote {
        void refreshToken(Context context,SplashFetchData listener);
    }

    interface SplashFetchData {
        void refreshSuccess();
        void refreshError();
    }
}
