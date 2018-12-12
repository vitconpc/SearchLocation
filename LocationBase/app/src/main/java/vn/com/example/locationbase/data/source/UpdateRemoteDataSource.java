package vn.com.example.locationbase.data.source;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;

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
import vn.com.example.locationbase.common.Utils;
import vn.com.example.locationbase.data.model.body.PersonalInfoBody;
import vn.com.example.locationbase.data.model.response.ResponseBody;
import vn.com.example.locationbase.data.model.response.User;
import vn.com.example.locationbase.data.source.remote.UpdateInforDataSource;
import vn.com.example.locationbase.service.ServerApiUtils;
import vn.com.example.locationbase.service.ServiceInterfaceAPI;

public class UpdateRemoteDataSource implements UpdateInforDataSource.UpdateRemote {
    private static UpdateRemoteDataSource instance;
    private ServiceInterfaceAPI api;
    private StorageReference storageReference;

    private UpdateRemoteDataSource() {
        api = ServerApiUtils.getInstance();
    }

    public static UpdateRemoteDataSource getInstance() {
        if (instance == null) {
            instance = new UpdateRemoteDataSource();
        }
        return instance;
    }

    @Override
    public void updateInfor(final String userName, final PersonalInfoBody body,
                            final UpdateInforDataSource.UpdateFetchData listener, final Context context) {
        storageReference = FirebaseStorage.getInstance().getReference();
        final StorageReference sr = storageReference.child(userName + ".jpg");
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
                if (task.isSuccessful()) {
                    body.setAvatarUrl(task.getResult().toString());
                    updateUser(body, listener, context);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                listener.updateInforFail("Tải ảnh lên không thành công");
            }
        });
    }

    private void updateUser(PersonalInfoBody body,
                            final UpdateInforDataSource.UpdateFetchData listener, Context context) {
        api.updateProfile(Utils.getBearerAccessToken(context), body).enqueue(new Callback<ResponseBody<User>>() {
            @Override
            public void onResponse(Call<ResponseBody<User>> call, Response<ResponseBody<User>> response) {
                switch (response.code()) {
                    case 200:
                        listener.updateInforSuccess(response.body().getData());
                        break;
                    default: {
                        listener.updateInforFail("Vui lòng kiểm tra lại mạng");
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody<User>> call, Throwable t) {
                listener.updateInforFail("Vui lòng kiểm tra lại mạng");
            }
        });
    }
}
