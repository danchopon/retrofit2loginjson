package example.loginapidemo.views;

import android.app.Service;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import example.loginapidemo.R;
import example.loginapidemo.adapters.RecyclerAdapter;
import example.loginapidemo.models.IncidentV2;
import example.loginapidemo.services.ApiClient;
import example.loginapidemo.services.IncidentV2Service;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class IncidentV2Activity extends AppCompatActivity {

    private static final String TAG = "IncidentV2Activity";
    private TextView latitudePosition;
    private TextView longitudePosition;
    private TextView currentCity;
    private LocationManager locationManager;
    private Location location;
    private Button locationSendButton;
    List<IncidentV2> incidentV2List;
    RecyclerView recyclerView;
    RecyclerAdapter recyclerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incident);
        getIncomingIntent();

        IncidentV2Service incidentV2Service = ApiClient.getClientInstance().create(IncidentV2Service.class);

        Call<List<IncidentV2>> call = incidentV2Service.getIncidentV2ById(getIntent().getIntExtra("id", 0));
        call.enqueue(new Callback<List<IncidentV2>>() {
            @Override
            public void onResponse(Call<List<IncidentV2>> call, Response<List<IncidentV2>> response) {
                incidentV2List = response.body();

                recyclerAdapter = new RecyclerAdapter(getApplicationContext(), incidentV2List);
                recyclerView.setAdapter(recyclerAdapter);
            }

            @Override
            public void onFailure(Call<List<IncidentV2>> call, Throwable t) {

            }
        });
    }

    private void getIncomingIntent() {

        if (getIntent().hasExtra("id")) {


            int id = getIntent().getIntExtra("id", 0);
            Log.d(TAG, "0getIncomingIntent: checking for incoming intents = " + id);

            setId(id);
            Log.d(TAG, "1getIncomingIntent: checking for incoming intents = " + getIntent().getIntExtra("id",0));


        }
    }

    private void setId(int id) {

        Log.d(TAG, "2tent: checking for incoming intents = " + getIntent().getIntExtra("id",0));

        TextView name = (TextView)findViewById(R.id.id_inc);
        Log.d(TAG, "4getIncomingIntent: checking for incoming intents = " + getIntent().getIntExtra("id",0));

        name.setText(""+id);
        Log.d(TAG, "3getIncomingIntent: checking for incoming intents = " + getIntent().getIntExtra("id",0));


    }

}
