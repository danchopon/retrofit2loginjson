package example.loginapidemo.views;

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import example.loginapidemo.R;
import example.loginapidemo.models.LocationModel;
import example.loginapidemo.services.ApiClient;
import example.loginapidemo.services.IncidentV2Service;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class IncidentV2Activity extends AppCompatActivity implements LocationListener {

    private static final String TAG = "IncidentV2Activity";
    private TextView latitudePosition;
    private TextView longitudePosition;
    private TextView currentAddress;
    private LocationManager locationManager;
    private Location location;
    private final int REQUEST_LOCATION = 200;

    private TextView incV2Id;
    private TextView battId;
    private TextView incV2Number;
    private TextView incV2Address;

    private Button startButton;
    private Button finishButton;
    private TextView taskFinished;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incident);
        getIncomingIntent();

        latitudePosition = (TextView) findViewById(R.id.latitude);
        longitudePosition = (TextView) findViewById(R.id.longitude);
        currentAddress = (TextView) findViewById(R.id.current_address);
        locationManager = (LocationManager) getSystemService(Service.LOCATION_SERVICE);
//        locationSendButton = (Button) findViewById(R.id.button_location_send);

        ScheduledExecutorService exec = Executors.newSingleThreadScheduledExecutor();


        if(ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)!=PackageManager.PERMISSION_GRANTED &&ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION)!=PackageManager.PERMISSION_GRANTED)

        {
            ActivityCompat.requestPermissions(IncidentV2Activity.this, new String[] {Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        } else

        {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 100, 2, this);
            if (locationManager != null) {
                location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            }
        }
        if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))

        {
            if (location != null) {
                latitudePosition.setText(String.valueOf(location.getLatitude()));
                longitudePosition.setText(String.valueOf(location.getLongitude()));
                getAddressFromLocation(location, getApplicationContext(), new GeoCoderHandler());
            }
        } else

        {
            showGPSDisabledAlertToUser();
        }

        startButton = (Button) findViewById(R.id.startTask);
        finishButton = (Button) findViewById(R.id.finishTask);
        taskFinished = (TextView) findViewById(R.id.taskFinished);

        startButton.setVisibility(View.VISIBLE);
        finishButton.setVisibility(View.GONE);
        taskFinished.setVisibility(View.GONE);

        startButton.setOnClickListener((view) -> {

            Log.d(TAG, "onLocationChanged: " + incV2Id.getText());

            LocationModel firstRequest = new LocationModel(battId.getText().toString(),Integer.parseInt(incV2Id.getText().toString())
                    ,latitudePosition.getText().toString(),longitudePosition.getText().toString(),false);
            sendNetworkRequest(firstRequest);

            startButton.setVisibility(View.GONE);
            finishButton.setVisibility(View.VISIBLE);

            exec.scheduleAtFixedRate(new Runnable() {
                @Override
                public void run() {
                    LocationModel request = new LocationModel(Integer.parseInt(incV2Id.getText().toString())
                            ,latitudePosition.getText().toString(),longitudePosition.getText().toString(),false);

                    Retrofit.Builder builder = new Retrofit.Builder()
                            .baseUrl(ApiClient.BASE_URL)
                            .addConverterFactory(GsonConverterFactory.create());

                    Retrofit retrofit = builder.build();

                    IncidentV2Service client = retrofit.create(IncidentV2Service.class);
                    Call<LocationModel> call = client.createLocation(request);

                    call.enqueue(new Callback<LocationModel>() {
                        @Override
                        public void onResponse(Call<LocationModel> call, Response<LocationModel> response) {

                            Log.d(TAG, "onResponse: " + response.message());
                            Log.d(TAG, "onResponse: " + response.body().getLocationId());

                        }

                        @Override
                        public void onFailure(Call<LocationModel> call, Throwable t) {
                            Toast.makeText(IncidentV2Activity.this, "Something went wrong: " + t.getMessage().toString(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }, 0, 5, TimeUnit.MINUTES);

        });

        finishButton.setOnClickListener((view) -> {

            Log.d(TAG, "onLocationChanged: " + incV2Id.getText());

            LocationModel lastRequest = new LocationModel(battId.getText().toString(),Integer.parseInt(incV2Id.getText().toString())
                    ,latitudePosition.getText().toString(),longitudePosition.getText().toString(),true);
            sendNetworkRequest(lastRequest);

            finishButton.setVisibility(View.GONE);
            taskFinished.setVisibility(View.VISIBLE);

            exec.shutdown();
        });
    }

    private void getIncomingIntent() {
        if (getIntent().hasExtra("incidentV2Id") && getIntent().hasExtra("battalionId")) {
            Log.d(TAG, "getIncomingIntent: found intent extras");

            int incidentV2Id = getIntent().getIntExtra("incidentV2Id", 0);
            String userBattalionId = getIntent().getStringExtra("battalionId");
            String incidentV2Number = getIntent().getStringExtra("number");
            String incidentV2Address = getIntent().getStringExtra("address");

            setId( incidentV2Number, incidentV2Address);

        }
    }

    private void setId(String incidentV2Number, String incidentV2Address) {



        incV2Number = (TextView) findViewById(R.id.incidentV2_number);
        incV2Number.setText("" + incidentV2Number);
        incV2Address = (TextView) findViewById(R.id.incidentV2_address);
        incV2Address.setText(""+ incidentV2Address);

    }

    @Override
    public void onLocationChanged(Location location) {
        latitudePosition.setText(String.valueOf(location.getLatitude()));
        longitudePosition.setText(String.valueOf(location.getLongitude()));
        getAddressFromLocation(location, getApplicationContext(), new IncidentV2Activity.GeoCoderHandler());

        LocationModel request = new LocationModel(Integer.parseInt(incV2Id.getText().toString())
                ,latitudePosition.getText().toString(),longitudePosition.getText().toString(),false);

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(ApiClient.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();

        IncidentV2Service client = retrofit.create(IncidentV2Service.class);
        Call<LocationModel> call = client.createLocation(request);

        call.enqueue(new Callback<LocationModel>() {
            @Override
            public void onResponse(Call<LocationModel> call, Response<LocationModel> response) {

                Log.d(TAG, "onResponse: " + response.message());
                Log.d(TAG, "onResponse: " + response.body().getLocationId());

            }

            @Override
            public void onFailure(Call<LocationModel> call, Throwable t) {
                Toast.makeText(IncidentV2Activity.this, "Something went wrong: " + t.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onProviderDisabled(String provider) {
        if (provider.equals(LocationManager.GPS_PROVIDER)) {
            showGPSDisabledAlertToUser();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == REQUEST_LOCATION) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            }
        }
    }

    private void showGPSDisabledAlertToUser() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("GPS is disabled in your device. Would you like to enable it?")
                .setCancelable(false)
                .setPositiveButton("Goto Settings Page To Enable GPS", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent callGPSSettingIntent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(callGPSSettingIntent);
                    }
                });
        alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }

    public static void getAddressFromLocation(final Location location, final Context context, final Handler handler) {
        Thread thread = new Thread() {
            @Override public void run() {
                Geocoder geocoder = new Geocoder(context, Locale.getDefault());
                String result = null;
                try {
                    List<Address> list = geocoder.getFromLocation(
                            location.getLatitude(), location.getLongitude(), 1);
                    if (list != null && list.size() > 0) {
                        Address address = list.get(0);
                        // sending back first address line and locality
                        result = address.getAddressLine(0) + ", " + address.getLocality() + ", " +  address.getCountryName() ;
                    }
                } catch (IOException e) {
                    Log.e(TAG, "Impossible to connect to Geocoder", e);
                } finally {
                    Message msg = Message.obtain();
                    msg.setTarget(handler);
                    if (result != null) {
                        msg.what = 1;
                        Bundle bundle = new Bundle();
                        bundle.putString("address", result);
                        msg.setData(bundle);
                    } else
                        msg.what = 0;
                    msg.sendToTarget();
                }
            }
        };
        thread.start();
    }

    private class GeoCoderHandler extends Handler {
        @Override
        public void handleMessage(Message message) {
            String result;
            switch (message.what) {
                case 1:
                    Bundle bundle = message.getData();
                    result = bundle.getString("address");
                    break;
                default:
                    result = null;
            }
            currentAddress.setText(result);
        }
    }

    private void sendNetworkRequest(LocationModel locationModel) {

        //Create Retrofit instance
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(ApiClient.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();

        // get client & call object for the request
        IncidentV2Service client = retrofit.create(IncidentV2Service.class);
        Call<LocationModel> call = client.createLocation(locationModel);

        call.enqueue(new Callback<LocationModel>() {
            @Override
            public void onResponse(Call<LocationModel> call, Response<LocationModel> response) {

                Log.d(TAG, "onResponse: " + response.message());


            }

            @Override
            public void onFailure(Call<LocationModel> call, Throwable t) {
                Toast.makeText(IncidentV2Activity.this, "Something went wrong: " + t.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}