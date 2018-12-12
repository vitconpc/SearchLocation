package vn.com.example.locationbase.data.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AccessToken {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("accessToken")
    @Expose
    private String accessToken;
    @SerializedName("refreshToken")
    @Expose
    private String refreshToken;
    @SerializedName("expireIn")
    @Expose
    private Integer expireIn;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public Integer getExpireIn() {
        return expireIn;
    }

    public void setExpireIn(Integer expireIn) {
    }
}

