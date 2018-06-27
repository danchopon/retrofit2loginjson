package example.loginapidemo.services;

import example.loginapidemo.models.Message;
import example.loginapidemo.models.User;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {
    @POST("/login")
    Call<Message> login(@Body RequestBody jsonString);

//    @POST("/login")
//    Call<Message> login(@Body User body);
}
