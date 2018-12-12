package vn.com.example.locationbase.service;

public class ServerApiUtils {
    private static final String BASE_URL = "http://35.202.46.112:9000/";
    private static ServiceInterfaceAPI instance;

    public static ServiceInterfaceAPI getInstance() {
        if (instance == null) {
            instance = Retrofit2Client.getClient(BASE_URL).create(ServiceInterfaceAPI.class);
        }
        return instance;
    }
}
