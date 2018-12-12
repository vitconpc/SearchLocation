package vn.com.example.locationbase.view.Register;

import vn.com.example.locationbase.data.model.body.RegisterBody;
import vn.com.example.locationbase.data.model.response.User;
import vn.com.example.locationbase.data.reponsitory.RegisterReponsitory;
import vn.com.example.locationbase.data.source.remote.RegisterDataSource;

public class RegisterPresenter implements RegisterContact.Presenter, RegisterDataSource.RegisterFetchData {

    private RegisterContact.View view;
    private RegisterReponsitory reponsitory;

    public RegisterPresenter(RegisterContact.View view) {
        this.view = view;
        reponsitory = RegisterReponsitory.getInstance();
    }

    @Override
    public void registerUser(RegisterBody body) {
        reponsitory.registerUser(body, this);
    }

    @Override
    public void registerSuccess(String userName) {
        view.showRegisterSuccess(userName);
    }

    @Override
    public void registerError(String error) {
        view.showRegisterError(error);
    }
}
