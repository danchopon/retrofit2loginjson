package example.loginapidemo.models;

import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("username")
    public String username;
    @SerializedName("password")
    public String password;
    @SerializedName("battalionId")
    public String battalionId;

    public User(String username, String password){
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBattalionId() {
        return battalionId;
    }

    public void setBattalionId(String battalionId) {
        this.battalionId = battalionId;
    }
}
