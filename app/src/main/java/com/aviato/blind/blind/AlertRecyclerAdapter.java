package com.aviato.blind.blind;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class AlertRecyclerAdapter extends RecyclerView.Adapter<AlertRecyclerAdapter.ViewHolder> {
    private ArrayList<RoadInfo> roadInfoArrayList;
    private Context context;

    public AlertRecyclerAdapter(ArrayList<RoadInfo> roadInfoArrayList, Context context) {
        this.roadInfoArrayList = roadInfoArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.alert_item_layout, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.tvAlert.setText(roadInfoArrayList.get(i).getRoadSign());
    }

    @Override
    public int getItemCount() {
        return roadInfoArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView tvAlert;
        public View layout;

        public ViewHolder(View v) {
            super(v);
            layout = v;
            tvAlert = v.findViewById(R.id.tv_alert);
        }
    }
}