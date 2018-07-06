package example.loginapidemo.services;

import java.util.List;

import example.loginapidemo.models.IncidentV2;
import example.loginapidemo.models.IncidentV2List;
import example.loginapidemo.models.LocationModel;
import example.loginapidemo.models.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface IncidentV2Service {
//    @GET("/incidents/{battalionId}")
//    Call<IncidentV2List> getIncidentV2List(@Path("battalionId") int battalionId);

//    @GET("/incidents/{id}")
//    Call<List<IncidentV2>> getIncidentV2ById(@Path("id") int id);

    @GET("/incidents/{battalionId}")
    Call<List<IncidentV2>> getIncidentV2ByBattalionId(@Path("battalionId") String battalionId);

    @POST("/locations/addUpdate")
    Call<LocationModel> createLocation(@Body LocationModel locationModel);

    @PUT("/locations/{id}")
    Call<LocationModel>  updateLocation(@Path("id") @Body LocationModel locationModel);
}
