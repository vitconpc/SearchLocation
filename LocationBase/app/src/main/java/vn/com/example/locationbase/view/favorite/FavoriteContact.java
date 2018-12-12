package vn.com.example.locationbase.view.favorite;

import java.util.List;

import vn.com.example.locationbase.data.model.response.SaveLocation;

public interface FavoriteContact {
    interface View {
        void getDataSuccess(List<SaveLocation> place);
        void getDataError();
        void deleteSuccess();
        void deleteError();
    }

    interface Presenter {
        void getListPlace();

        void deletePlace(String id);
    }
}
