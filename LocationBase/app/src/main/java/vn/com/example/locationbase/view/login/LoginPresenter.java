package vn.com.example.locationbase.view.login;

import vn.com.example.locationbase.data.reponsitory.LoginReponsitory;
import vn.com.example.locationbase.data.source.remote.LoginDataSource;

public class LoginPresenter implements LoginContact.Presenter, LoginDataSource.LoginFetchData {

    private LoginContact.View view;
    private LoginReponsitory reponsitory;

    public LoginPresenter(LoginContact.View view) {
        this.view = view;
        reponsitory = LoginReponsitory.getInstance();
    }

    @Override
    public void Login(String email, String password) {
        reponsitory.checkLogin(email,password,this);
    }

    @Override
    public void loginSuccess() {
        view.LoginSuccess();
    }

    @Override
    public void wrongEmailorPassword(String error) {
        view.LoginFail(error);
    }

    @Override
    public void loginNotVerified(String error) {
        view.LoginFail(error);
    }
}
