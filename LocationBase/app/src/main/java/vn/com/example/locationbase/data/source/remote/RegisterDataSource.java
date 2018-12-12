package vn.com.example.locationbase.data.source.remote;

import vn.com.example.locationbase.data.model.body.RegisterBody;

public interface RegisterDataSource {
    interface RegisterRemote {
        void registerUser(RegisterBody body, RegisterFetchData listener);
    }

    interface RegisterFetchData {
        void registerSuccess(String userName);
        void registerError(String error);
    }
}
