package example.loginapidemo.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by nam on 31/03/2017.
 */

public class User {

    @SerializedName("username")
    public String username;
    @SerializedName("password")
    public String password;

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User(String username, String password){
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }
}
