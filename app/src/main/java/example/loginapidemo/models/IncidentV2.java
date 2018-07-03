package example.loginapidemo.models;

import com.google.gson.annotations.SerializedName;

public class IncidentV2 {
    @SerializedName("number")
    private String number;
    @SerializedName("address")
    private String address;

    public IncidentV2(String number, String address) {
        this.number = number;
        this.address = address;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
