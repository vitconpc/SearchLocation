package vn.com.example.locationbase.data.source.remote;

import vn.com.example.locationbase.data.model.user.User;

public interface RegisterDataSource {
    interface RegisterRemote {
        void registerUser(User user,RegisterFetchData listener);
    }

    interface RegisterFetchData {
        void registerSuccess();
        void registerError(String error);
    }
}
