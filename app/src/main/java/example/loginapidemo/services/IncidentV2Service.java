package example.loginapidemo.services;

import example.loginapidemo.models.IncidentV2List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface IncidentV2Service {
    @GET("/incidents/{battalionId}")
    Call<IncidentV2List> getIncidentV2List(@Path("battalionId") int battalionId);
}
