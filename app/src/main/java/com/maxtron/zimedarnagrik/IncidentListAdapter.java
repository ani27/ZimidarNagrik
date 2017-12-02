package com.maxtron.zimedarnagrik;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.koushikdutta.ion.Ion;

import java.util.ArrayList;

/**
 * Created by anish on 03/12/17.
 */

public class IncidentListAdapter extends RecyclerView.Adapter<IncidentListAdapter.MyViewHolder> {

    Context context;
    ArrayList<Incidents>descriptionList;
    public IncidentListAdapter(Context context, ArrayList<Incidents>des){

        this.context = context;
        this.descriptionList = des;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.incident_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        Incidents incident = descriptionList.get(position);
        holder.description.setText(incident.description);
        holder.distance.setText(incident.distance+" mtrs away");
        holder.timestamp.setText(incident.timestamp);
        Ion.with(holder.crash_image)
                .placeholder(R.drawable.overturnedcar2)
                .error(R.drawable.overturnedcar2)
                .load(incident.imageurl);

        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                
            }
        });

    }

    @Override
    public int getItemCount() {
        return descriptionList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView description;
        TextView distance;
        ImageView crash_image;
        TextView timestamp;
        RelativeLayout item;

        public MyViewHolder(View itemView) {
            super(itemView);
            description = (TextView)itemView.findViewById(R.id.description);
            distance = (TextView)itemView.findViewById(R.id.distance);
            crash_image = (ImageView)itemView.findViewById(R.id.image);
            timestamp  = (TextView)itemView.findViewById(R.id.time);
            item = itemView.findViewById(R.id.list_item);
        }
    }
}
