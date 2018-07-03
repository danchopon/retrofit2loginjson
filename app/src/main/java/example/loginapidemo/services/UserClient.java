package example.loginapidemo.services;

import example.loginapidemo.models.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserClient {

    @POST("/login")
    Call<User> loginUser(@Body User user);

}