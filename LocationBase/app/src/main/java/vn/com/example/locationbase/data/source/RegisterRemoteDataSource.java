package vn.com.example.locationbase.data.source;


import android.net.Uri;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.com.example.locationbase.R;
import vn.com.example.locationbase.data.model.body.RegisterBody;
import vn.com.example.locationbase.data.model.response.ResponseBody;
import vn.com.example.locationbase.data.model.response.User;
import vn.com.example.locationbase.data.source.remote.RegisterDataSource;
import vn.com.example.locationbase.service.ServerApiUtils;
import vn.com.example.locationbase.service.ServiceInterfaceAPI;

public class RegisterRemoteDataSource implements RegisterDataSource.RegisterRemote {

    private static RegisterRemoteDataSource instance;
    private StorageReference storageReference;
    private ServiceInterfaceAPI api;

    public RegisterRemoteDataSource() {
        api = ServerApiUtils.getInstance();
    }

    public static RegisterRemoteDataSource getInstance() {
        if (instance == null) {
            instance = new RegisterRemoteDataSource();
        }
        return instance;
    }

    @Override
    public void registerUser(final RegisterBody body, final RegisterDataSource.RegisterFetchData listener) {
        storageReference = FirebaseStorage.getInstance().getReference();
        final StorageReference sr = storageReference.child(body.getUsername() + ".jpg");
        UploadTask uploadTask = sr.putFile(Uri.parse(body.getAvatarUrl()));

        final Task<Uri> task = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }

                // Continue with the task to get the download URL
                return sr.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if(task.isSuccessful()){
                    body.setAvatarUrl(task.getResult().toString());
                    createUser(body,listener);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                listener.registerError("Tải ảnh lên không thành công");
            }
        });


//        createUser(body, listener);
    }

    private void createUser(RegisterBody body, final RegisterDataSource.RegisterFetchData listener) {

        api.register(body).enqueue(new Callback<ResponseBody<String>>() {
            @Override
            public void onResponse(Call<ResponseBody<String>> call, Response<ResponseBody<String>> response) {
                switch (response.code()) {
                    case 200:
                        listener.registerSuccess(response.body().getData());
                        break;
                    default:
                        listener.registerError("Không thể đăng ký");
                        break;
                }
            }

            @Override
            public void onFailure(Call<ResponseBody<String>> call, Throwable t) {
                    listener.registerError("Vui lòng kiểm tra lại mạng");
            }
        });
    }
}
