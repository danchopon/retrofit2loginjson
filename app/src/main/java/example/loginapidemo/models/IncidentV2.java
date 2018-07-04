package example.loginapidemo.models;

import com.google.gson.annotations.SerializedName;

public class IncidentV2 {

    @SerializedName("id")
    private int id;
    @SerializedName("incidentAddress")
    private String address;
    @SerializedName("battalionId")
    private String battalionId;


    public IncidentV2(int id, String address, String battalionId) {
        this.id = id;
        this.address = address;
        this.battalionId = battalionId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBattalionId() {
        return battalionId;
    }

    public void setBattalionId(String battalionId) {
        this.battalionId = battalionId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "IncidentV2{" +
                "id='" + id + '\'' +
                ", address='" + address + '\'' +
                ", battalionId='" + battalionId + '\'' +
                '}';
    }
}
