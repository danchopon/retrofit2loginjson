//package example.loginapidemo.views;
//
//import android.Manifest;
//import android.app.Service;
//import android.content.Context;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.content.pm.PackageManager;
//import android.location.Address;
//import android.location.Geocoder;
//import android.location.Location;
//import android.location.LocationListener;
//import android.location.LocationManager;
//import android.os.Bundle;
//import android.os.Handler;
//import android.os.Message;
//import android.support.v4.app.ActivityCompat;
//import android.support.v7.app.AlertDialog;
//import android.support.v7.app.AppCompatActivity;
//import android.util.Log;
//import android.view.View;
//import android.widget.Button;
//import android.widget.ListView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import java.io.IOException;
//import java.util.List;
//import java.util.Locale;
//
//import example.loginapidemo.R;
//import example.loginapidemo.models.IncidentItem;
//import example.loginapidemo.models.IncidentV2;
//import example.loginapidemo.models.User;
//import example.loginapidemo.services.IncidentV2Service;
//import example.loginapidemo.services.UserClient;
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//import retrofit2.Retrofit;
//import retrofit2.converter.gson.GsonConverterFactory;
//
//public class LocationActivity extends AppCompatActivity implements LocationListener {
//    private TextView latitudePosition;
//    private TextView longitudePosition;
//    private TextView currentCity;
//    private LocationManager locationManager;
//    private Location location;
//    private final int REQUEST_LOCATION = 200;
//    private static final String TAG = "MainActivity";
//    private Button locationSendButton;
//    Handler h;
//    IncidentItem incidentItem;
//    private boolean status;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_incident);
//        latitudePosition = (TextView) findViewById(R.id.latitude);
//        longitudePosition = (TextView) findViewById(R.id.longitude);
//        currentCity = (TextView) findViewById(R.id.city);
//        locationManager = (LocationManager) getSystemService(Service.LOCATION_SERVICE);
//        locationSendButton = (Button) findViewById(R.id.button_location_send);
//
////        h = new Handler() {
////            public void handleMessage(android.os.Message msg) {
////                new LocationUpdater().start();
////            };
////        };
//
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(LocationActivity.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
//        } else {
//            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 100, 2, this);
//            if (locationManager != null) {
//                location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
//            }
//        }
//        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
//            if (location != null) {
//                latitudePosition.setText(String.valueOf(location.getLatitude()));
//                longitudePosition.setText(String.valueOf(location.getLongitude()));
//                getAddressFromLocation(location, getApplicationContext(), new GeoCoderHandler());
//            }
//        } else {
//            showGPSDisabledAlertToUser();
//        }
//
//        String latitude = latitudePosition.getText().toString();
//        String longitude = longitudePosition.getText().toString();
//        status = true;
//
//
//        locationSendButton.setOnClickListener((view) -> {
//
//            Toast.makeText(LocationActivity.this, "latitude: " + latitude + "\nlongitude: " + longitude , Toast.LENGTH_SHORT).show();
//            System.out.println("latitude: " + latitude + "\nlongitude: " + longitude );
//
//            incidentItem = new IncidentItem(
//                    latitude,
//                    longitude,
//                    status
//            );
//
//            sendNetworkRequest(incidentItem);
//        });
//
//    }
//
//    @Override
//    public void onLocationChanged(Location location) {
//        latitudePosition.setText(String.valueOf(location.getLatitude()));
//        longitudePosition.setText(String.valueOf(location.getLongitude()));
//        getAddressFromLocation(location, getApplicationContext(), new GeoCoderHandler());
//    }
//    @Override
//
//    public void onStatusChanged(String provider, int status, Bundle extras) {
//    }
//
//    @Override
//    public void onProviderEnabled(String provider) {
//    }
//
//    @Override
//    public void onProviderDisabled(String provider) {
//        if (provider.equals(LocationManager.GPS_PROVIDER)) {
//            showGPSDisabledAlertToUser();
//        }
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
//        if (requestCode == REQUEST_LOCATION) {
//            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//            }
//        }
//    }
//
//    private void showGPSDisabledAlertToUser() {
//        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
//        alertDialogBuilder.setMessage("GPS is disabled in your device. Would you like to enable it?")
//                .setCancelable(false)
//                .setPositiveButton("Goto Settings Page To Enable GPS", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//                        Intent callGPSSettingIntent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
//                        startActivity(callGPSSettingIntent);
//                    }
//                });
//        alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int id) {
//                dialog.cancel();
//            }
//        });
//        AlertDialog alert = alertDialogBuilder.create();
//        alert.show();
//    }
//
//    public static void getAddressFromLocation(final Location location, final Context context, final Handler handler) {
//        Thread thread = new Thread() {
//            @Override public void run() {
//                Geocoder geocoder = new Geocoder(context, Locale.getDefault());
//                String result = null;
//                try {
//                    List<Address> list = geocoder.getFromLocation(
//                            location.getLatitude(), location.getLongitude(), 1);
//                    if (list != null && list.size() > 0) {
//                        Address address = list.get(0);
//                        // sending back first address line and locality
//                        result = address.getAddressLine(0) + ", " + address.getLocality() + ", " +  address.getCountryName() ;
//                    }
//                } catch (IOException e) {
//                    Log.e(TAG, "Impossible to connect to Geocoder", e);
//                } finally {
//                    Message msg = Message.obtain();
//                    msg.setTarget(handler);
//                    if (result != null) {
//                        msg.what = 1;
//                        Bundle bundle = new Bundle();
//                        bundle.putString("address", result);
//                        msg.setData(bundle);
//                    } else
//                        msg.what = 0;
//                    msg.sendToTarget();
//                }
//            }
//        };
//        thread.start();
//    }
//
//    private class GeoCoderHandler extends Handler {
//        @Override
//        public void handleMessage(Message message) {
//            String result;
//            switch (message.what) {
//                case 1:
//                    Bundle bundle = message.getData();
//                    result = bundle.getString("address");
//                    break;
//                default:
//                    result = null;
//            }
//            currentCity.setText(result);
//        }
//    }
//
//    private class LocationUpdater extends Thread {
//
//        public void run(){
//
//            System.out.println("latitude" + latitudePosition + "\nlongtitude : " + longitudePosition );
//            Toast.makeText(LocationActivity.this, "latitude : " + latitudePosition + "\nlongtitude : " + longitudePosition, Toast.LENGTH_SHORT).show();
//
//            try {
//                Thread.sleep(5000);
//            } catch (InterruptedException ex){
//                //ignore
//            }
//
//
//        }
//
//    }
//
//    private void sendNetworkRequest(IncidentItem incidentItem) {
//
//        //Create Retrofit instance
//        Retrofit.Builder builder = new Retrofit.Builder()
//                .baseUrl("https://jsonplaceholder.typicode.com/")
//                .addConverterFactory(GsonConverterFactory.create());
//
//        Retrofit retrofit = builder.build();
//
//        // get client & call object for the request
//        IncidentV2Service client = retrofit.create(IncidentV2Service.class);
//        Call<IncidentItem> call = client.sendLocation(incidentItem);
//
//        call.enqueue(new Callback<IncidentItem>() {
//            @Override
//            public void onResponse(Call<IncidentItem> call, Response<IncidentItem> response) {
//                Toast.makeText(LocationActivity.this, "Sent " + response.body().getLatitude(), Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onFailure(Call<IncidentItem> call, Throwable t) {
//                Toast.makeText(LocationActivity.this, "Something went wrong: " + t.getMessage().toString(), Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
//}