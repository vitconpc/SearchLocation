package vn.com.example.locationbase.data.model.response;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class User implements Parcelable {
    private String id;
    private String username;
    private String password;
    private String fullName;
    private long birthDay;
    private String avatarUrl;


    protected User(Parcel in) {
        id = in.readString();
        username = in.readString();
        password = in.readString();
        fullName = in.readString();
        avatarUrl = in.readString();
        birthDay = in.readLong();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public long getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(long birthDay) {
        this.birthDay = birthDay;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User() {
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        if (username != null && !username.isEmpty()) {
            this.username = username;
        }
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        if (password != null && !password.isEmpty()) {
            this.password = password;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(username);
        dest.writeString(password);
        dest.writeString(fullName);
        dest.writeString(avatarUrl);
        dest.writeLong(birthDay);
    }
}
