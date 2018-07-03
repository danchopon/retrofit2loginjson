package example.loginapidemo.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import example.loginapidemo.R;
import example.loginapidemo.models.IncidentV2;

public class IncidentV2Adapter extends RecyclerView.Adapter<IncidentV2Adapter.IncidentV2ViewHolder> {

    private ArrayList<IncidentV2> dataList;

    public IncidentV2Adapter(ArrayList<IncidentV2> dataList) {
        this.dataList = dataList;
    }

    @Override
    public IncidentV2ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.row_incident, parent, false);
        return new IncidentV2ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(IncidentV2ViewHolder holder, int position) {
        holder.txtIncV2Number.setText(dataList.get(position).getNumber());
        holder.txtIncV2Address.setText(dataList.get(position).getAddress());
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class IncidentV2ViewHolder extends RecyclerView.ViewHolder {

        TextView txtIncV2Number, txtIncV2Address;

        IncidentV2ViewHolder(View itemView) {
            super(itemView);
            txtIncV2Number = (TextView) itemView.findViewById(R.id.txt_incidentV2_number);
            txtIncV2Address = (TextView) itemView.findViewById(R.id.txt_incidentV2_address);
        }
    }
}
