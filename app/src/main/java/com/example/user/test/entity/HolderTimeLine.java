package com.example.user.test.entity;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import com.example.user.test.R;

public class HolderTimeLine extends RecyclerView.ViewHolder{
    public FrameLayout line;

    public HolderTimeLine(@NonNull View itemView) {
        super(itemView);
        this.line = itemView.findViewById(R.id.line);
    }
}