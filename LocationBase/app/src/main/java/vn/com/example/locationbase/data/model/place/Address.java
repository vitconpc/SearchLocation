package vn.com.example.locationbase.data.model.place;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import vn.com.example.locationbase.common.Constants;


public class Address {
    @SerializedName("formatted_address")
    @Expose
    private String formattedAddress;
    @SerializedName("geometry")
    @Expose
    private Geometry geometry;
    @SerializedName("place_id")
    @Expose
    private String placeId;

    public String getFormattedAddress() {
        formattedAddress = formattedAddress.replaceAll(Constants.UNNAMED_ROAD + ", ", "");
        formattedAddress = formattedAddress.replaceAll(", " + Constants.VIETNAM, "");
        return formattedAddress;
    }

    public void setFormattedAddress(String formattedAddress) {
        this.formattedAddress = formattedAddress;
    }

    public Geometry getGeometry() {
        return geometry;
    }

    public void setGeometry(Geometry geometry) {
        this.geometry = geometry;
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }
}
