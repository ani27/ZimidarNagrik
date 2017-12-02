package com.maxtron.zimedarnagrik;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by anish on 03/12/17.
 */

public class IncidentListAdapter extends RecyclerView.Adapter<IncidentListAdapter.MyViewHolder> {

    Context context;
    ArrayList<String>description;
    public IncidentListAdapter(Context context, ArrayList<String>des){

        this.context = context;
        this.description = des;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.incident_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public MyViewHolder(View itemView) {
            super(itemView);
        }
    }
}
