package example.loginapidemo.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import example.loginapidemo.R;
import example.loginapidemo.models.IncidentV2;
import example.loginapidemo.views.IncidentV2Activity;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {

    private Context mContext;
    private List<IncidentV2> incidentV2List;

    public RecyclerAdapter(Context mContext, List<IncidentV2> incidentV2List) {
        this.mContext = mContext;
        this.incidentV2List = incidentV2List;
    }

    @Override
    public RecyclerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.row_incident, parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerAdapter.MyViewHolder holder, int position) {

        holder.txtIncV2Address.setText(incidentV2List.get(position).getAddress());
        holder.txtIncV2BattalionId.setText(incidentV2List.get(position).getBattalionId());

        holder.parentLayout.setOnClickListener((view) -> {

            Intent intent = new Intent(mContext, IncidentV2Activity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("incidentV2Id", incidentV2List.get(position).getIncidentV2Id());
            intent.putExtra("battalionId", incidentV2List.get(position).getBattalionId());
            mContext.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return incidentV2List.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView txtIncV2Address, txtIncV2BattalionId;
        CardView parentLayout;

        public MyViewHolder(View itemView) {
            super(itemView);
            txtIncV2Address = (TextView) itemView.findViewById(R.id.txt_incidentV2_address);
            txtIncV2BattalionId = (TextView) itemView.findViewById(R.id.txt_incidentV2_battalionId);
            parentLayout = (CardView) itemView.findViewById(R.id.parent_layout);
        }
    }
}
