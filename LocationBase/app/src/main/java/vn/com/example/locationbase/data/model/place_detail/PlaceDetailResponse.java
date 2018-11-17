package vn.com.example.locationbase.data.model.place_detail;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PlaceDetailResponse {
    @SerializedName("result")
    @Expose
    private PlaceDetail result;

    public PlaceDetail getResult() {
        return result;
    }

    public void setResult(PlaceDetail result) {
        this.result = result;
    }
}
