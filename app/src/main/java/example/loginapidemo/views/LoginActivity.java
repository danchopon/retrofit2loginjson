package example.loginapidemo.views;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import example.loginapidemo.R;
import example.loginapidemo.models.User;
import example.loginapidemo.services.UserClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {
    EditText etUserName;
    EditText etPassword;
    Button btnLogin;

    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
//        initUI();

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
                .baseUrl("http://192.168.137.98:8080/")
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();

        // get client & call object for the request
        UserClient client = retrofit.create(UserClient.class);
        Call<User> call = client.loginUser(user);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                Log.d("me", "duhudh = "+response.body());
                Toast.makeText(LoginActivity.this, "Welcome"+response.body().getUsername(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.d("me", "errow = " + t.getMessage().toString());

                Toast.makeText(LoginActivity.this, "something went wrong" + t.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}
