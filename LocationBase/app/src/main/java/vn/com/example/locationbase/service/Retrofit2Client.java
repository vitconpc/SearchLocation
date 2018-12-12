package vn.com.example.locationbase.service;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Retrofit2Client {
    private static Retrofit retrofit = null;

    public static synchronized Retrofit getClient(String baseURL){
            if (retrofit == null) {
                OkHttpClient client = new OkHttpClient.Builder()
                        .readTimeout(8, TimeUnit.SECONDS)
                        .connectTimeout(8, TimeUnit.SECONDS)
                        .build();

                retrofit = new Retrofit.Builder()
                        .baseUrl(baseURL)
                        .client(client)
//                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//                        .addConverterFactory(ScalarsConverterFactory.create())//MUST set before GsonConverter
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
            }
            return retrofit;
    }
}
