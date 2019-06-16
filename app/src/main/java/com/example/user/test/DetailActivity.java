package com.example.user.test;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.user.test.entity.EventTimeLine;
import com.example.user.test.useful.AdapterTimeLine;

import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        recyclerView = findViewById(R.id.RecycleTimeLine);
        ArrayList<EventTimeLine> events = new ArrayList<>();
        for(int i = 0;i<15;i++){
            EventTimeLine event = new EventTimeLine(true);
            events.add(event);
        }
        addRecycleItems(recyclerView, events);
    }

    public void addRecycleItems(RecyclerView recyclerView,ArrayList<EventTimeLine> events){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(new AdapterTimeLine(events,R.layout.time_line,this));
    }

    public void changeIntent(View view){

        Intent intent = new Intent(this,AddEventActivity.class);
        startActivity(intent);

    }

}
