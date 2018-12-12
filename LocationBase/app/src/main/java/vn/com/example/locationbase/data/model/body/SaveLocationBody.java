package vn.com.example.locationbase.data.model.body;


import vn.com.example.locationbase.data.model.place_detail.PlaceDetail;

public class SaveLocationBody {
    private Double lat;
    private Double lon;
    private String formattedAddress;
    private String formattedPhoneNumber;
    private String name;
    private String placeId;
    private float rating;

    public SaveLocationBody(PlaceDetail detail) {
        setLat(detail.getGeometry().getLocation().getLat());
        setLon(detail.getGeometry().getLocation().getLng());
        setFormattedAddress(detail.getFormattedAddress());
        setFormattedPhoneNumber(detail.getFormattedPhoneNumber());
        setName(detail.getName());
        setPlaceId(detail.getPlaceId());
        setRating(detail.getRating());
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
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
}
