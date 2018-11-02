package vn.com.example.locationbase.data.source;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import vn.com.example.locationbase.data.source.remote.LoginDataSource;

public class LoginRemoteDataSource implements LoginDataSource.LoginRemote {
    private static LoginRemoteDataSource dataSource;
    private FirebaseAuth auth;

    public LoginRemoteDataSource() {
        auth = FirebaseAuth.getInstance();
    }

    public synchronized static LoginRemoteDataSource getInstance(){
        if (dataSource == null){
            dataSource = new LoginRemoteDataSource();
        }
        return dataSource;
    }


    @Override
    public void checkLogin(String email, String password, final LoginDataSource.LoginFetchData listener) {
        auth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            FirebaseUser user = auth.getCurrentUser();
                            if (user.isEmailVerified()){
                                listener.loginSuccess();
                                return;
                            }
                            listener.loginNotVerified("Email chưa được xác minh");
                        }else {
                            listener.wrongEmailorPassword("Sai Email hoặc mật khẩu");
                        }
                    }
                });
    }
}
