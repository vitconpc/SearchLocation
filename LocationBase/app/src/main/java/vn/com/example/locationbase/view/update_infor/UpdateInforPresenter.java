package vn.com.example.locationbase.view.update_infor;

import android.content.Context;

import vn.com.example.locationbase.data.model.body.PersonalInfoBody;
import vn.com.example.locationbase.data.model.response.User;
import vn.com.example.locationbase.data.reponsitory.UpdateInforResponsitory;
import vn.com.example.locationbase.data.source.remote.UpdateInforDataSource;

public class UpdateInforPresenter implements UpdateInforContact.Presenter, UpdateInforDataSource.UpdateFetchData {

    private UpdateInforContact.View view;
    private Context context;
    private UpdateInforResponsitory responsitory;

    public UpdateInforPresenter(UpdateInforContact.View view, Context context) {
        this.view = view;
        this.context = context;
        responsitory = UpdateInforResponsitory.getInstance();
    }

    @Override
    public void UpdateInfor(String userName, PersonalInfoBody body) {
        responsitory.updateInfor(userName, body, this, context);
    }

    @Override
    public void updateInforSuccess(User user) {
        view.UpdateInforSuccess(user);
    }

    @Override
    public void updateInforFail(String message) {
        view.UpdateInforFail(message);
    }
}
