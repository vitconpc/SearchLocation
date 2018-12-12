package vn.com.example.locationbase.data.reponsitory;

import android.content.Context;

import vn.com.example.locationbase.data.model.body.PersonalInfoBody;
import vn.com.example.locationbase.data.source.UpdateRemoteDataSource;
import vn.com.example.locationbase.data.source.remote.UpdateInforDataSource;

public class UpdateInforResponsitory {
    private static UpdateInforResponsitory instance;
    private UpdateInforDataSource.UpdateRemote dataSource;

    private UpdateInforResponsitory(UpdateInforDataSource.UpdateRemote dataSource) {
        this.dataSource = dataSource;
    }

    public static UpdateInforResponsitory getInstance() {
        if (instance == null) {
            instance = new UpdateInforResponsitory(UpdateRemoteDataSource.getInstance());
        }
        return instance;
    }

    public void updateInfor(String userName, PersonalInfoBody body,
                            UpdateInforDataSource.UpdateFetchData listener, Context context) {
        dataSource.updateInfor(userName, body, listener, context);
    }
}
