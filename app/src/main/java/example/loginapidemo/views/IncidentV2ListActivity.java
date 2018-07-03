package example.loginapidemo.views;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

import example.loginapidemo.R;
import example.loginapidemo.adapters.IncidentV2Adapter;
import example.loginapidemo.models.IncidentV2;
import example.loginapidemo.models.IncidentV2List;
import example.loginapidemo.services.ApiClient;
import example.loginapidemo.services.IncidentV2Service;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class IncidentV2ListActivity extends AppCompatActivity {

    private IncidentV2Adapter incidentV2Adapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_incidents);

        /*Create handle for the RetrofitInstance interface*/
        IncidentV2Service service = ApiClient.getClientInstance().create(IncidentV2Service.class);

        /*Call the method with parameter in the interface to get the employee data*/
        Call<IncidentV2List> call = service.getIncidentV2List(100);

        /*Log the URL called*/
        Log.wtf("URL Called", call.request().url() + "");

        call.enqueue(new Callback<IncidentV2List>() {
            @Override
            public void onResponse(Call<IncidentV2List> call, Response<IncidentV2List> response) {
                generateIncidentV2List(response.body().getIncidentV2List());
            }

            @Override
            public void onFailure(Call<IncidentV2List> call, Throwable t) {
                Toast.makeText(IncidentV2ListActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void generateIncidentV2List(ArrayList<IncidentV2> incV2DataList) {
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_incidentV2_list);

        incidentV2Adapter = new IncidentV2Adapter(incV2DataList);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(IncidentV2ListActivity.this);

        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(incidentV2Adapter);
    }

}
