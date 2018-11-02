package vn.com.example.locationbase.data.source;

import android.support.annotation.NonNull;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.com.example.locationbase.data.model.PlaceResultResponse;
import vn.com.example.locationbase.data.source.remote.HomeDataSource;
import vn.com.example.locationbase.service.APIUtils;
import vn.com.example.locationbase.service.GoogleMapInterfaceAPI;

public class HomeRemoteDataSource implements HomeDataSource.HomeRemote {
    private static HomeRemoteDataSource instance;
    FirebaseAuth auth;
    private FirebaseUser user;
    private DatabaseReference databaseReference;
    private String current_user_name, current_user_email, current_user_avatarUrl;
    private GoogleMapInterfaceAPI mApi;
    private String KEY = "AIzaSyBqo1CVj_866CQTVT1lCwXVBog4fCVtUGc";

    public HomeRemoteDataSource() {
        mApi = APIUtils.getInstance();
    }

    public static synchronized HomeRemoteDataSource getInstance() {
        if (instance == null) {
            instance = new HomeRemoteDataSource();
        }
        return instance;
    }

    @Override
    public void LogOut(HomeDataSource.HomeFetchData listener) {
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        if (user != null) {
            auth.signOut();
            listener.LogOutSuccess();
        }
    }

    @Override
    public void getData(final HomeDataSource.HomeFetchData listener) {
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (user != null) {
                    current_user_name = dataSnapshot.child(user.getUid()).child("name").getValue(String.class);
                    current_user_email = dataSnapshot.child(user.getUid()).child("email").getValue(String.class);
                    current_user_avatarUrl = dataSnapshot.child(user.getUid()).child("avatarUri").getValue(String.class);
                    listener.getDataSuccess(current_user_name,current_user_email,current_user_avatarUrl);
                }
                else listener.getDataError("Không có người dùng");
//                txtName.setText(current_user_name);
//                txtEmail.setText(current_user_email);
//                Picasso.get().load(current_user_avatarUrl).into(imgAvatar);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void checkLogin(HomeDataSource.HomeFetchData listener) {
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        if (user == null){
            listener.LoginSuccess();
        }else {
            listener.LoginFail();
        }
    }

    @Override
    public void searchNearBy(LatLng location, float range, String type, String keyWord, final HomeDataSource.HomeFetchData listener) {
        String locate = location.latitude+","+location.longitude;
        mApi.searchNearBy(locate,range,type,keyWord,KEY).enqueue(new Callback<PlaceResultResponse>() {
            @Override
            public void onResponse(Call<PlaceResultResponse> call, Response<PlaceResultResponse> response) {
                listener.searchNearBySuccess(response.body());
            }

            @Override
            public void onFailure(Call<PlaceResultResponse> call, Throwable t) {
                listener.searchNearByFail("Vui lòng kiểm tra lại kết nối mạng");
            }
        });
    }
}
