package vn.com.example.locationbase.view.Register;

import vn.com.example.locationbase.data.model.User;
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
    public void registerUser(User user) {
        reponsitory.registerUser(user,this);
    }

    @Override
    public void registerSuccess() {
        view.showRegisterSuccess();
    }

    @Override
    public void registerError(String error) {
        view.showRegisterError(error);
    }
}
