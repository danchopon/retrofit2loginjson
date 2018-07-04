package example.loginapidemo.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import example.loginapidemo.R;
import example.loginapidemo.models.IncidentV2;
import example.loginapidemo.views.IncidentV2Activity;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {

    private static final String TAG = "RecyclerAdapter";

    private Context context;
    private List<IncidentV2> incidentV2List;

    public RecyclerAdapter(Context context, List<IncidentV2> incidentV2List) {
        this.context = context;
        this.incidentV2List = incidentV2List;
    }

    @Override
    public RecyclerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.row_incident, parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerAdapter.MyViewHolder holder, int position) {
        Log.d("me","onBindViewHolder: called " + incidentV2List.get(position).getId() + " " + incidentV2List.get(position).getAddress());

        holder.txtIncV2Address.setText(incidentV2List.get(position).getAddress());
        holder.txtIncV2BattalionId.setText(incidentV2List.get(position).getBattalionId());

        holder.parentLayout.setOnClickListener((view) -> {

            Log.d("me", "onClick: clicked on " + incidentV2List.get(position));

            Toast.makeText(context, "" + incidentV2List.get(position), Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(context, IncidentV2Activity.class);
            Bundle bundle = new Bundle();
            bundle.putInt("id", incidentV2List.get(position).getId());
            intent.putExtras(bundle);
            context.startActivity(intent);
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
