package example.loginapidemo.views;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import example.loginapidemo.R;
import example.loginapidemo.adapters.RecyclerAdapter;
import example.loginapidemo.models.IncidentV2;
import example.loginapidemo.services.ApiClient;
import example.loginapidemo.services.IncidentV2Service;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class IncidentV2ListActivity extends AppCompatActivity {
    private static final String TAG = "IncidentV2ListActivity";

    List<IncidentV2> incidentV2List;
    RecyclerView recyclerView;
    RecyclerAdapter recyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_incidents);

        incidentV2List = new ArrayList<>();

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_incidentV2_list);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        IncidentV2Service incidentV2Service = ApiClient.getClientInstance().create(IncidentV2Service.class);

        Call<List<IncidentV2>> call = incidentV2Service.getIncidentV2ByBattalionId(getIntent().getStringExtra("battalionId" ));

        Log.d(TAG, "-----URL----- : "+call.request().url());

        call.enqueue(new Callback<List<IncidentV2>>() {
            @Override
            public void onResponse(Call<List<IncidentV2>> call, Response<List<IncidentV2>> response) {

                incidentV2List = response.body();

                recyclerAdapter = new RecyclerAdapter(getApplicationContext(), incidentV2List);
                recyclerView.setAdapter(recyclerAdapter);

            }

            @Override
            public void onFailure(Call<List<IncidentV2>> call, Throwable t) {

                Log.d(TAG, "-----FAIL----- : " + t.getMessage());

            }
        });
    }
}
