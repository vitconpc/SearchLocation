package vn.com.example.locationbase.data.model.direction;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import vn.com.example.locationbase.data.model.place.Northeast;
import vn.com.example.locationbase.data.model.place.Southwest;

public class Bounds {
    @SerializedName("northeast")
    @Expose
    private Northeast northeast;
    @SerializedName("southwest")
    @Expose
    private Southwest southwest;

    public Northeast getNortheast() {
        return northeast;
    }

    public void setNortheast(Northeast northeast) {
        this.northeast = northeast;
    }

    public Southwest getSouthwest() {
        return southwest;
    }

    public void setSouthwest(Southwest southwest) {
        this.southwest = southwest;
    }
}
