package vn.com.example.locationbase.data.source;


import android.net.Uri;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.ProviderQueryResult;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


import vn.com.example.locationbase.data.model.user.User;
import vn.com.example.locationbase.data.source.remote.RegisterDataSource;

public class RegisterRemoteDataSource implements RegisterDataSource.RegisterRemote {

    private static RegisterRemoteDataSource instance;
    private FirebaseAuth auth;
    private FirebaseUser firebaseUser;
    private String userID;
    private DatabaseReference reference;
    private StorageReference storageReference;

    public static RegisterRemoteDataSource getInstance() {
        if (instance == null) {
            instance = new RegisterRemoteDataSource();
        }
        return instance;
    }

    @Override
    public void registerUser(final User user, final RegisterDataSource.RegisterFetchData listener) {
        auth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference().child("Users");
        storageReference = FirebaseStorage.getInstance().getReference().child("User_images");
        auth.fetchProvidersForEmail(user.getEmail())
                .addOnCompleteListener(new OnCompleteListener<ProviderQueryResult>() {
                    @Override
                    public void onComplete(@NonNull Task<ProviderQueryResult> task) {
                        if (task.isSuccessful()) {
                            boolean check = task.getResult().getProviders().isEmpty();
                            if (check) {
                                auth.createUserWithEmailAndPassword(user.getEmail(), user.getPassword())
                                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                            @Override
                                            public void onComplete(@NonNull Task<AuthResult> task) {
                                                if (task.isSuccessful()) {
                                                    creatUser(user,listener);
                                                } else {
                                                    listener.registerError("Vui lòng kiểm tra lại tài khoản email");
                                                }
                                            }
                                        });
                            } else {
                                listener.registerError("tài khoản này đã có người sử dụng");
                            }
                        } else {
                            listener.registerError("Vui lòng kiểm tra lại tài khoản email");
                        }
                    }
                });
    }

    private void sendVerificationEmail(final RegisterDataSource.RegisterFetchData listener) {
        firebaseUser.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                auth.signOut();
                listener.registerSuccess();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                listener.registerError("Could not send Email");
            }
        });
    }

    private void creatUser(final User user, final RegisterDataSource.RegisterFetchData listener) {
        firebaseUser = auth.getCurrentUser();
        userID = firebaseUser.getUid();
        user.setUserID(userID);
        reference.child(userID).setValue(user)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            //save image in storage
                            listener.registerError("Tạo thành công");
                            StorageReference sr = storageReference.child(firebaseUser.getUid() + ".jpg");
                            sr.putFile(Uri.parse(user.getAvatarUri()))
                                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                        @Override
                                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                            sendVerificationEmail(listener);
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    listener.registerError("Lỗi khi lưu ảnh");
                                }
                            });
//                            .addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
//                                @Override
//                                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
//                                    if (task.isSuccessful()) {
//                                        sendVerificationEmail(listener);
//                                        listener.registerError("Lưu ảnh thành công");
//                                    } else {
//                                        listener.registerError("Lỗi khi lưu ảnh");
//                                    }
//                                }
//                            });
                        } else {
                            listener.registerError("Tài khoản này đã có người sử dụng");
                        }
                    }
                });
    }
}
