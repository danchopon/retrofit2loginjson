package example.loginapidemo.views;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import example.loginapidemo.R;
import example.loginapidemo.models.Message;
import example.loginapidemo.models.User;
import example.loginapidemo.services.ApiClient;
import example.loginapidemo.services.ApiService;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    EditText etUserName;
    EditText etPassword;
    Button btnLogin;

    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();
    }

    void initUI() {
        etUserName = (EditText) findViewById(R.id.etUserName);
        etPassword = (EditText) findViewById(R.id.etPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etUserName.getText().toString();
                String password = etPassword.getText().toString();

                user = new User(username, password);
                Gson gson = new Gson();
                String json = gson.toJson(user);

                RequestBody jsonString = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json.toString());

                ApiService getResponse = ApiClient.getClient().create(ApiService.class);
                Call<Message> call = getResponse.login(jsonString);
                try {
                    call.enqueue(new Callback<Message>() {
                        @Override
                        public void onResponse(Call<Message> call, Response<Message> response) {
                            Message message = response.body();
                            if (message != null) {
                                Toast.makeText(getApplicationContext(), message.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Message> call, Throwable t) {
                            Toast.makeText(MainActivity.this, "Fail", Toast.LENGTH_SHORT).show();
                        }
                    });
                } catch (Exception e) {
                    Log.d("Crash", "onClick: " + e.toString());
                }
            }
        });

    }
}
