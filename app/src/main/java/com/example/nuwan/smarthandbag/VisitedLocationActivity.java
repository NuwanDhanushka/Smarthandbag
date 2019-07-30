package com.example.nuwan.smarthandbag;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class VisitedLocationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visited_location);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerviewvisitedlocation);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Query query = FirebaseDatabase.getInstance()
                .getReference()
                .child("VisitedLocationLog")
                .limitToLast(50);
        FirebaseRecyclerOptions<visitedlocationdetails > options =
                new FirebaseRecyclerOptions.Builder<visitedlocationdetails >()
                        .setQuery(query,visitedlocationdetails  .class)
                        .build();
        FirebaseRecyclerAdapter<visitedlocationdetails ,visitedlocationdetailsviewholder> adapter = new FirebaseRecyclerAdapter<visitedlocationdetails , visitedlocationdetailsviewholder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull visitedlocationdetailsviewholder holder, int position, @NonNull visitedlocationdetails  model) {
                holder.setVisitedlocation(model.getVisitedLocation());
                holder.setTimeStamp(String.valueOf(model.getTimeStamp()));
                holder.setTime(String.valueOf(model.getTimeStamp()));
                holder.setDate(String.valueOf(model.getTimeStamp()));
                holder.setBagstatus(String.valueOf(model.getDeviceLock()));
            }

            @NonNull
            @Override
            public visitedlocationdetailsviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.visitedlocationview, parent, false);
                return new visitedlocationdetailsviewholder(view);
            }
        };
        adapter.startListening();
        recyclerView.setAdapter(adapter);
    }
}
