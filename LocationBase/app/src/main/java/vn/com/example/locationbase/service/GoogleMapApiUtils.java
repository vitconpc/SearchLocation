package vn.com.example.locationbase.service;

public class GoogleMapApiUtils {
    private static final String BASE_URL = "https://maps.googleapis.com/maps/api/";
    private static GoogleMapInterfaceAPI instance;

    public static GoogleMapInterfaceAPI getInstance() {
        if (instance == null) {
            instance = RetrofitClient.getClient(BASE_URL).create(GoogleMapInterfaceAPI.class);
        }
        return instance;
    }
}
