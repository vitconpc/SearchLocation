package vn.com.example.locationbase.data.reponsitory;

import vn.com.example.locationbase.data.model.body.RegisterBody;
import vn.com.example.locationbase.data.model.response.User;
import vn.com.example.locationbase.data.source.RegisterRemoteDataSource;
import vn.com.example.locationbase.data.source.remote.RegisterDataSource;

public class RegisterReponsitory {
    private static RegisterReponsitory instance;
    private RegisterDataSource.RegisterRemote datasource;

    public RegisterReponsitory(RegisterDataSource.RegisterRemote datasource) {
        this.datasource = datasource;
    }

    public static RegisterReponsitory getInstance() {
        if (instance == null) {
            instance = new RegisterReponsitory(RegisterRemoteDataSource.getInstance());
        }
        return instance;
    }

    public void registerUser(RegisterBody body, RegisterDataSource.RegisterFetchData listener) {
        datasource.registerUser(body, listener);
    }
}
