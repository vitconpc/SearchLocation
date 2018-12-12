package vn.com.example.locationbase.data.source;

import android.content.Context;
import android.support.annotation.NonNull;

import com.google.android.gms.maps.model.LatLng;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.com.example.locationbase.common.Utils;
import vn.com.example.locationbase.data.model.body.SaveLocationBody;
import vn.com.example.locationbase.data.model.direction.DirectionResultResponse;
import vn.com.example.locationbase.data.model.place.GoogleAddressResponse;
import vn.com.example.locationbase.data.model.place.Location;
import vn.com.example.locationbase.data.model.place_detail.PlaceDetail;
import vn.com.example.locationbase.data.model.place_detail.PlaceDetailResponse;
import vn.com.example.locationbase.data.model.response.ResponseBody;
import vn.com.example.locationbase.data.model.response.User;
import vn.com.example.locationbase.data.source.remote.HomeDataSource;
import vn.com.example.locationbase.service.GoogleMapApiUtils;
import vn.com.example.locationbase.service.GoogleMapInterfaceAPI;
import vn.com.example.locationbase.service.ServerApiUtils;
import vn.com.example.locationbase.service.ServiceInterfaceAPI;

public class HomeRemoteDataSource implements HomeDataSource.HomeRemote {
    private static HomeRemoteDataSource instance;
    private String current_user_name, current_user_email, current_user_avatarUrl;
    private GoogleMapInterfaceAPI mApi;
    private String KEY = "AIzaSyBqo1CVj_866CQTVT1lCwXVBog4fCVtUGc";

    private ServiceInterfaceAPI serviceInterfaceAPI;

    public HomeRemoteDataSource() {
        mApi = GoogleMapApiUtils.getInstance();
        serviceInterfaceAPI = ServerApiUtils.getInstance();
    }


    public static synchronized HomeRemoteDataSource getInstance() {
        if (instance == null) {
            instance = new HomeRemoteDataSource();
        }
        return instance;
    }

    @Override
    public void getData(final HomeDataSource.HomeFetchData listener, Context context) {
        //context
        serviceInterfaceAPI.getLogin(Utils.getBearerAccessToken(context)).enqueue(new Callback<ResponseBody<User>>() {
            @Override
            public void onResponse(Call<ResponseBody<User>> call, Response<ResponseBody<User>> response) {
                switch (response.code()){
                    case 200:
                        listener.getDataSuccess(response.body().getData());
                        break;
                }
            }

            @Override
            public void onFailure(Call<ResponseBody<User>> call, Throwable t) {
                listener.getDataError("Kiểm tra lại mạng");
            }
        });
        //get login thanh cong hay ko?
        //trước là check ở firebase h lam tren kia thi get trong shareprefer ak?
//
//        auth = FirebaseAuth.getInstance();
//        user = auth.getCurrentUser();
//        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users");
//        databaseReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                if (user != null) {
//                    current_user_name = dataSnapshot.child(user.getUid()).child("name").getValue(String.class);
//                    current_user_email = dataSnapshot.child(user.getUid()).child("email").getValue(String.class);
//                    current_user_avatarUrl = dataSnapshot.child(user.getUid()).child("avatarUri").getValue(String.class);
//                    listener.getDataSuccess(current_user_name, current_user_email, current_user_avatarUrl);
//                } else listener.getDataError("Không có người dùng");
////                txtName.setText(current_user_name);
////                txtEmail.setText(current_user_email);
////                Picasso.get().load(current_user_avatarUrl).into(imgAvatar);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
    }

    @Override
    public void getPlaceDetail(String placeId, final HomeDataSource.HomeFetchData listener) {
        String fileds = "name,rating,type,vicinity,formatted_address,geometry,place_id,formatted_phone_number";
        mApi.getPlaceDetail(placeId, fileds, KEY).enqueue(new Callback<PlaceDetailResponse>() {
            @Override
            public void onResponse(Call<PlaceDetailResponse> call, Response<PlaceDetailResponse> response) {
                listener.getPlaceDetailSuccess(response.body().getResult());
            }

            @Override
            public void onFailure(Call<PlaceDetailResponse> call, Throwable t) {
                listener.getPlaceDetailError();
            }
        });
    }

    @Override
    public void getAddress(LatLng location, final HomeDataSource.HomeFetchData listener) {
        String locate = location.latitude + "," + location.longitude;
        mApi.getAddress(locate, KEY).enqueue(new Callback<GoogleAddressResponse>() {
            @Override
            public void onResponse(Call<GoogleAddressResponse> call, Response<GoogleAddressResponse> response) {
                listener.getAddressSucess(response.body());
            }

            @Override
            public void onFailure(Call<GoogleAddressResponse> call, Throwable t) {
                listener.getAddressFail();
            }
        });
    }

    @Override
    public void getDirection(LatLng origin, LatLng destination, final HomeDataSource.HomeFetchData listener) {
        String startLocation = origin.latitude + "," + origin.longitude;
        String endLocation = destination.latitude + "," + destination.longitude;

        mApi.getDirection(startLocation, endLocation, "driving", KEY)
                .enqueue(new Callback<DirectionResultResponse>() {
                    @Override
                    public void onResponse(Call<DirectionResultResponse> call, Response<DirectionResultResponse> response) {
                        listener.getDirectionSuccess(response.body());
                    }

                    @Override
                    public void onFailure(Call<DirectionResultResponse> call, Throwable t) {
                        listener.getDirectionFail();
                    }
                });
    }

    @Override
    public void savePlaceDetail(PlaceDetail detail, final HomeDataSource.HomeFetchData listener, Context context) {
        serviceInterfaceAPI.saveLocation(Utils.getBearerAccessToken(context), new SaveLocationBody(detail))
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        switch (response.code()) {
                            case 200:
                                listener.saveSuccess();
                                break;
                            default:
                                listener.saveFail();
                                break;
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        listener.getPlaceDetailError();
                    }
                });
    }

    @Override
    public void getProfile(final HomeDataSource.HomeFetchData listener, Context context) {
        serviceInterfaceAPI.getProfile(Utils.getBearerAccessToken(context)).enqueue(new Callback<ResponseBody<User>>() {
            @Override
            public void onResponse(Call<ResponseBody<User>> call, Response<ResponseBody<User>> response) {
                switch (response.code()) {
                    case 200: {
                        listener.getProfileSuccess(response.body().getData());
                    }
                    break;
                    default: {
                        listener.getProfileError();
                    }
                    break;
                }
            }

            @Override
            public void onFailure(Call<ResponseBody<User>> call, Throwable t) {
                listener.getProfileError();
            }
        });
    }
}
