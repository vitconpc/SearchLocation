package vn.com.example.locationbase.data.model.place_detail;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import vn.com.example.locationbase.data.model.place.Geometry;

public class PlaceDetail implements Parcelable {
    @SerializedName("formatted_address")
    @Expose
    private String formattedAddress;
    @SerializedName("formatted_phone_number")
    @Expose
    private String formattedPhoneNumber;
    @SerializedName("geometry")
    @Expose
    private Geometry geometry;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("place_id")
    @Expose
    private String placeId;

    public PlaceDetail() {
    }

    protected PlaceDetail(Parcel in) {
        formattedAddress = in.readString();
        formattedPhoneNumber = in.readString();
        geometry = in.readParcelable(Geometry.class.getClassLoader());
        name = in.readString();
        placeId = in.readString();
        rating = in.readFloat();
        types = in.createStringArrayList();
        vicinity = in.readString();
    }

    public static final Creator<PlaceDetail> CREATOR = new Creator<PlaceDetail>() {
        @Override
        public PlaceDetail createFromParcel(Parcel in) {
            return new PlaceDetail(in);
        }

        @Override
        public PlaceDetail[] newArray(int size) {
            return new PlaceDetail[size];
        }
    };

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

    public Geometry getGeometry() {
        return geometry;
    }

    public void setGeometry(Geometry geometry) {
        this.geometry = geometry;
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

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public List<String> getTypes() {
        return types;
    }

    public void setTypes(List<String> types) {
        this.types = types;
    }

    public String getVicinity() {
        return vicinity;
    }

    public void setVicinity(String vicinity) {
        this.vicinity = vicinity;
    }

    @SerializedName("rating")
    @Expose
    private float rating;
    @SerializedName("types")
    @Expose
    private List<String> types = null;
    @SerializedName("vicinity")
    @Expose
    private String vicinity;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(formattedAddress);
        dest.writeString(formattedPhoneNumber);
        dest.writeParcelable(geometry, flags);
        dest.writeString(name);
        dest.writeString(placeId);
        dest.writeFloat(rating);
        dest.writeStringList(types);
        dest.writeString(vicinity);
    }
}
