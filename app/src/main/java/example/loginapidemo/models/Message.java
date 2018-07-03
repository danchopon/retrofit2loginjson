package example.loginapidemo.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Message {

    @SerializedName("Message")
    @Expose
    private String message;

    public String getMessage() {
        return message;
    }
}
