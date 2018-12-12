package vn.com.example.locationbase.view.splash;

import android.content.Context;

import vn.com.example.locationbase.data.reponsitory.SplashResponsitory;
import vn.com.example.locationbase.data.source.remote.SplashDataSource;

public class SplashPresenter implements SplashContact.Presenter,SplashDataSource.SplashFetchData {

    private Context context;
    private SplashContact.View view;
    private SplashResponsitory responsitory;

    public SplashPresenter(Context context, SplashContact.View view) {
        this.context = context;
        this.view = view;
        responsitory = SplashResponsitory.getInstance();
    }

    @Override
    public void refreshToken() {
        responsitory.refreshData(context,this);
    }

    @Override
    public void refreshSuccess() {
        view.refreshSuccess();
    }

    @Override
    public void refreshError() {
        view.refreshError();
    }
}
