package example.loginapidemo.models;

import com.google.gson.annotations.SerializedName;

public class LocationModel {
    @SerializedName("id")
    private int locationId;
    @SerializedName("user_id")
    private String userBattalionId;
    @SerializedName("incidentV2Id")
    private int locationIncidentV2Id;
    @SerializedName("latitude")
    private String latitude;
    @SerializedName("longitude")
    private String longitude;
    @SerializedName("status")
    private boolean status;

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public String getUserBattalionId() {
        return userBattalionId;
    }

    public void setUserBattalionId(String userBattalionId) {
        this.userBattalionId = userBattalionId;
    }

    public LocationModel(String  userBattalionId, int locationIncidentV2Id, String latitude, String longitude, boolean status) {
        this.userBattalionId = userBattalionId;
        this.locationIncidentV2Id = locationIncidentV2Id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.status = status;
    }

    public LocationModel(int locationIncidentV2Id, String latitude, String longitude, boolean status) {
        this.locationIncidentV2Id = locationIncidentV2Id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.status = status;
    }

    public int getLocationIncidentV2Id() {
        return locationIncidentV2Id;
    }

    public void setLocationIncidentV2Id(int locationIncidentV2Id) {
        this.locationIncidentV2Id = locationIncidentV2Id;
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
