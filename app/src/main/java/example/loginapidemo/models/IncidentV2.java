package example.loginapidemo.models;

import com.google.gson.annotations.SerializedName;

public class IncidentV2 {

    @SerializedName("id")
    private int incidentV2Id;
    @SerializedName("incidentAddress")
    private String address;
    @SerializedName("battalionId")
    private String battalionId;

    public IncidentV2(int incidentV2Id, String address, String battalionId) {
        this.incidentV2Id = incidentV2Id;
        this.address = address;
        this.battalionId = battalionId;
    }

    public int getIncidentV2Id() {
        return incidentV2Id;
    }

    public void setIncidentV2Id(int incidentV2Id) {
        this.incidentV2Id = incidentV2Id;
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
                "incidentV2Id='" + incidentV2Id + '\'' +
                ", address='" + address + '\'' +
                ", battalionId='" + battalionId + '\'' +
                '}';
    }
}
