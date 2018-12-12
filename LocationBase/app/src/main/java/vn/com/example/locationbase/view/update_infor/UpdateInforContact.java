package vn.com.example.locationbase.view.update_infor;

import vn.com.example.locationbase.data.model.body.PersonalInfoBody;
import vn.com.example.locationbase.data.model.response.User;

public interface UpdateInforContact {
    interface View {
        void UpdateInforSuccess(User user);

        void UpdateInforFail(String message);
    }

    interface Presenter {
        void UpdateInfor(String userName, PersonalInfoBody body);
    }
}
