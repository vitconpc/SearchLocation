package vn.com.example.locationbase.data.source.remote;

public interface LoginDataSource {
    interface LoginRemote {
        void checkLogin(String email, String password, LoginFetchData listener);
    }

    interface LoginFetchData {
        void loginSuccess();

        void wrongEmailorPassword(String error);

        void loginNotVerified(String error);
    }
}
