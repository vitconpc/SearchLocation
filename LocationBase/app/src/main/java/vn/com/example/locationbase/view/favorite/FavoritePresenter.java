package vn.com.example.locationbase.view.favorite;

import android.content.Context;

import java.util.List;

import vn.com.example.locationbase.data.model.place_detail.PlaceDetail;
import vn.com.example.locationbase.data.model.response.SaveLocation;
import vn.com.example.locationbase.data.reponsitory.FavoriteResponsitory;
import vn.com.example.locationbase.data.source.remote.FavoriteDataSource;

public class FavoritePresenter implements FavoriteContact.Presenter,FavoriteDataSource.FavoriteFetchData {

    private Context context;
    private FavoriteContact.View view;
    private FavoriteResponsitory responsitory;

    public FavoritePresenter(Context context, FavoriteContact.View view) {
        this.context = context;
        this.view = view;
        this.responsitory = FavoriteResponsitory.getInstance();
    }

    @Override
    public void getListPlace() {
        responsitory.getListPlace(context,this);
    }

    @Override
    public void getListSuccess(List<SaveLocation> locations) {
        view.getDataSuccess(locations);
    }

    @Override
    public void getListError() {
        view.getDataError();
    }

    @Override
    public void deletePlace(String id) {
        responsitory.deletePlace(id,context,this);
    }

    @Override
    public void deletePlaceSuccess() {
        view.deleteSuccess();
    }

    @Override
    public void deletePlaceError() {
        view.deleteError();
    }
}
