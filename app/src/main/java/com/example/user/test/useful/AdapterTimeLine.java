package com.example.user.test.useful;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user.test.entity.EventTimeLine;
import com.example.user.test.entity.HolderTimeLine;

import java.util.ArrayList;

public class AdapterTimeLine extends RecyclerView.Adapter<HolderTimeLine>{

    private int resource;
    private ArrayList<EventTimeLine> events;
    private Activity activity;

    public AdapterTimeLine(ArrayList<EventTimeLine> events,int resource, Activity activity){
        this.events = events;
        this.activity = activity;
        this.resource = resource;
    }

    @NonNull
    @Override
    public HolderTimeLine onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(resource,viewGroup,false);
        return new HolderTimeLine(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderTimeLine holder, int i) {
        EventTimeLine eventTimeLine = events.get(i);
        if(events.size() - 1 == i){
            holder.line.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return events.size();
    }
}
