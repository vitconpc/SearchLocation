package vn.com.example.locationbase.data.source.remote;

import android.content.Context;

import vn.com.example.locationbase.data.model.body.PersonalInfoBody;
import vn.com.example.locationbase.data.model.response.User;

public interface UpdateInforDataSource {
    interface UpdateRemote {
        void updateInfor(String userName, PersonalInfoBody body,
                         UpdateInforDataSource.UpdateFetchData listener, Context context);
    }

    interface UpdateFetchData {
        void updateInforSuccess(User user);

        void updateInforFail(String message);
    }
}
