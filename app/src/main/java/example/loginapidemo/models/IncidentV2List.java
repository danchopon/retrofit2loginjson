package example.loginapidemo.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class IncidentV2List {
    @SerializedName("incidentV2List")
    private ArrayList<IncidentV2> incidentV2List;

    public ArrayList<IncidentV2> getIncidentV2List() {
        return incidentV2List;
    }

    public void setIncidentV2List(ArrayList<IncidentV2> incidentV2List) {
        this.incidentV2List = incidentV2List;
    }
}
