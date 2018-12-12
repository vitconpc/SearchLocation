package vn.com.example.locationbase.view.Register;

import vn.com.example.locationbase.data.model.body.RegisterBody;

public interface RegisterContact {
    interface View{
        void showRegisterSuccess(String userName);
        void showRegisterError(String error);
    }

    interface Presenter{
        void registerUser(RegisterBody body);
    }
}
