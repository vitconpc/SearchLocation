package vn.com.example.locationbase.view.Register;

import vn.com.example.locationbase.data.model.User;

public interface RegisterContact {
    interface View{
        void showRegisterSuccess();
        void showRegisterError(String error);
    }

    interface Presenter{
        void registerUser(User user);
    }
}
