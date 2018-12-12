package vn.com.example.locationbase.data.model.response;


import android.os.Parcel;
import android.os.Parcelable;

public class SaveLocation implements Parcelable {
    private String id;
    private Double lat;
    private Double lon;
    private String formattedAddress;
    private String formattedPhoneNumber;
    private String name;
    private String placeId;
    private float rating;

    protected SaveLocation(Parcel in) {
        id = in.readString();
        if (in.readByte() == 0) {
            lat = null;
        } else {
            lat = in.readDouble();
        }
        if (in.readByte() == 0) {
            lon = null;
        } else {
            lon = in.readDouble();
        }
        formattedAddress = in.readString();
        formattedPhoneNumber = in.readString();
        name = in.readString();
        placeId = in.readString();
        rating = in.readFloat();
    }

    public static final Creator<SaveLocation> CREATOR = new Creator<SaveLocation>() {
        @Override
        public SaveLocation createFromParcel(Parcel in) {
            return new SaveLocation(in);
        }

        @Override
        public SaveLocation[] newArray(int size) {
            return new SaveLocation[size];
        }
    };

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public SaveLocation() {
    }


    public String getFormattedAddress() {
        return formattedAddress;
    }

    public void setFormattedAddress(String formattedAddress) {
        this.formattedAddress = formattedAddress;
    }

    public String getFormattedPhoneNumber() {
        return formattedPhoneNumber;
    }

    public void setFormattedPhoneNumber(String formattedPhoneNumber) {
        this.formattedPhoneNumber = formattedPhoneNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        if (lat == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(lat);
        }
        if (lon == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(lon);
        }
        dest.writeString(formattedAddress);
        dest.writeString(formattedPhoneNumber);
        dest.writeString(name);
        dest.writeString(placeId);
        dest.writeFloat(rating);
    }
}
