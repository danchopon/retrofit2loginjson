package example.loginapidemo.models;

import com.google.gson.annotations.SerializedName;

public class IncidentItem {
    @SerializedName("number")
    private String number;
    @SerializedName("applicant_address")
    private String address;
    @SerializedName("id")
    private int id;
    @SerializedName("user_id")
    private int userId;
    @SerializedName("incident_v2_id")
    private int incidentV2Id;
    @SerializedName("latitude")
    private String latitude;
    @SerializedName("longitude")
    private String longitude;
    @SerializedName("status")
    private boolean status;

    public IncidentItem(String latitude, String longitude, boolean status) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.status = status;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getIncidentV2Id() {
        return incidentV2Id;
    }

    public void setIncidentV2Id(int incidentV2Id) {
        this.incidentV2Id = incidentV2Id;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
