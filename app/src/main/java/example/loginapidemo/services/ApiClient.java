package example.loginapidemo.services;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static Retrofit retrofit;
    public static final String BASE_URL = "http://192.168.137.224:8080/";
    //public static final String BASE_URL = "http://demo3445197.mockable.io/";

    public static Retrofit getClientInstance() {
        if (retrofit == null) {
            retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;

    }
}