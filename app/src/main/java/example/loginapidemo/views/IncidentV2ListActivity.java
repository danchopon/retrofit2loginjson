package example.loginapidemo.views;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import example.loginapidemo.R;
import example.loginapidemo.adapters.IncidentV2Adapter;
import example.loginapidemo.adapters.RecyclerAdapter;
import example.loginapidemo.models.IncidentV2;
import example.loginapidemo.models.IncidentV2List;
import example.loginapidemo.services.ApiClient;
import example.loginapidemo.services.IncidentV2Service;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class IncidentV2ListActivity extends AppCompatActivity {

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

        Call<List<IncidentV2>> call = incidentV2Service.getIncidentV2ByBattalionId(getIntent().getStringExtra("battalionId"));

        Log.d("IncidentV2ListActivity", "f "+call.request().url());


        call.enqueue(new Callback<List<IncidentV2>>() {
            @Override
            public void onResponse(Call<List<IncidentV2>> call, Response<List<IncidentV2>> response) {

                incidentV2List = response.body();

                Log.d("me", "successssss");

                Log.d("me", incidentV2List.toString());

                recyclerAdapter = new RecyclerAdapter(getApplicationContext(), incidentV2List);
                recyclerView.setAdapter(recyclerAdapter);

            }

            @Override
            public void onFailure(Call<List<IncidentV2>> call, Throwable t) {

                Log.d("me", "fail = " + t.getMessage());

            }

        });

//        /*Create handle for the RetrofitInstance interface*/
//        IncidentV2Service service = ApiClient.getClientInstance().create(IncidentV2Service.class);
//
//        /*Call the method with parameter in the interface to get the employee data*/
//        Call<IncidentV2List> call = service.getIncidentV2List(1);
//
//        /*Log the URL called*/
//        Log.wtf("URL Called", call.request().url() + "");
//        Toast.makeText(this, "URL called" + call.request().url().toString(), Toast.LENGTH_SHORT).show();
//
//        call.enqueue(new Callback<IncidentV2List>() {
//            @Override
//            public void onResponse(Call<IncidentV2List> call, Response<IncidentV2List> response) {
//                Toast.makeText(IncidentV2ListActivity.this, "Success", Toast.LENGTH_SHORT).show();
//                generateIncidentV2List(response.body().getIncidentV2List());
//            }
//
//            @Override
//            public void onFailure(Call<IncidentV2List> call, Throwable t) {
//                Toast.makeText(IncidentV2ListActivity.this, "Fail:  " + t.getMessage().toString(), Toast.LENGTH_SHORT).show();
//            }
//        });
    }

//    private void generateIncidentV2List(ArrayList<IncidentV2> incV2DataList) {
//        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_incidentV2_list);
//
//        incidentV2Adapter = new IncidentV2Adapter(incV2DataList);
//
//        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(IncidentV2ListActivity.this);
//
//        recyclerView.setLayoutManager(layoutManager);
//
//        recyclerView.setAdapter(incidentV2Adapter);
//    }

}
