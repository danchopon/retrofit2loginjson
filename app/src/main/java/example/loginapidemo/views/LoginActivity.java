package example.loginapidemo.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import example.loginapidemo.R;
import example.loginapidemo.models.User;
import example.loginapidemo.services.ApiClient;
import example.loginapidemo.services.UserClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {
    EditText etUserName;
    EditText etPassword;

    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUserName = (EditText) findViewById(R.id.etUserName);
        etPassword = (EditText) findViewById(R.id.etPassword);

        Button loginButton = (Button) findViewById(R.id.btnLogin);
        loginButton.setOnClickListener((view) -> {

            user = new User(
                    etUserName.getText().toString(),
                    etPassword.getText().toString()
            );

            sendNetworkRequest(user);
        });
    }

    private void sendNetworkRequest(User user) {

        //Create Retrofit instance
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(ApiClient.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();

        // get client & call object for the request
        UserClient client = retrofit.create(UserClient.class);
        Call<User> call = client.loginUser(user);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                Toast.makeText(LoginActivity.this, "Welcome "+response.body().getUsername(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(LoginActivity.this, IncidentV2ListActivity.class);
                intent.putExtra("battalionId", response.body().getBattalionId().toString());
                startActivity(intent);
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Something went wrong: " + t.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }


}
