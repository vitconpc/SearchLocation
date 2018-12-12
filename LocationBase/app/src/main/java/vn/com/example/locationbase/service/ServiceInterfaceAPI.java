package vn.com.example.locationbase.service;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import vn.com.example.locationbase.common.Constants;
import vn.com.example.locationbase.data.model.body.PersonalInfoBody;
import vn.com.example.locationbase.data.model.body.RegisterBody;
import vn.com.example.locationbase.data.model.body.SaveLocationBody;
import vn.com.example.locationbase.data.model.response.AccessToken;
import vn.com.example.locationbase.data.model.response.AccessTokenGroup;
import vn.com.example.locationbase.data.model.response.ResponseBody;
import vn.com.example.locationbase.data.model.response.SaveLocation;
import vn.com.example.locationbase.data.model.response.User;

public interface ServiceInterfaceAPI {
    @POST("api/auth/customer/login")
    Call<ResponseBody<AccessToken>> login(@Header(Constants.AUTHORIZATION) String accountBase64Encode);

    //dang ky : tra ve ten dang nhap .cai nay dug thi dug k thi cug k sao
    @POST("api/auth/register")
    Call<ResponseBody<String>> register(@Body RegisterBody registerBody);

    //cap nhat profile
    @PUT("api/customers/personalInfo")
    Call<ResponseBody<User>> updateProfile(@Header(Constants.AUTHORIZATION) String token,
                                           @Body PersonalInfoBody personalInfoBody);
    //lay profile
    @GET("api/customers/profile")
    Call<ResponseBody<User>> getProfile(@Header(Constants.AUTHORIZATION) String token);

    @GET("api/customers/profile")
    Call<ResponseBody<User>> getLogin(@Header(Constants.AUTHORIZATION) String token);

    @POST("api/auth/accessToken/refresh")
    Call<ResponseBody<AccessTokenGroup>> refreshToken(@Body AccessTokenGroup accessTokenGroup);

    @POST("api/customers/save_location")
    Call<ResponseBody> saveLocation(@Header(Constants.AUTHORIZATION) String token,
                                    @Body SaveLocationBody body);

    @GET("api/customers/save_location")
    Call<ResponseBody<List<SaveLocation>>> getListSaveLocation(@Header(Constants.AUTHORIZATION) String token);

    @DELETE("api/customers/save_location/{id}")
    Call<ResponseBody> deleteSaveLocation(@Header(Constants.AUTHORIZATION) String token,
                                          @Path("id") String saveLocationID);

}
