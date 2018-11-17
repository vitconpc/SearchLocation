package vn.com.example.locationbase.service;

public class ServerApiUtils {
    private static final String BASE_URL = "192.168.1.1:9000/";
    private static GoogleMapInterfaceAPI instance;

    public static synchronized GoogleMapInterfaceAPI getInstance() {
        if (instance == null) {
            instance = RetrofitClient.getClient(BASE_URL).create(GoogleMapInterfaceAPI.class);
        }
        return instance;
    }
}
