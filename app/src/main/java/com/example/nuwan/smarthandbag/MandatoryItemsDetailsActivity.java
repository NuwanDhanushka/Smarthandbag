package com.example.nuwan.smarthandbag;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class MandatoryItemsDetailsActivity extends AppCompatActivity{
    TextView Tid;
    String Tids;
    RecyclerView recyclerView;
    private Context mContex;
    private DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContex = this;
        setContentView(R.layout.activity_mandatory_items_details);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        Tid = (TextView) findViewById(R.id.tagid);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Query query = FirebaseDatabase.getInstance()
                .getReference()
                .child("MandatoryItemList")
                .limitToLast(50);
        FirebaseRecyclerOptions<mandatoryitemsdetails> options =
                new FirebaseRecyclerOptions.Builder<mandatoryitemsdetails>()
                        .setQuery(query, mandatoryitemsdetails.class)
                        .build();
        FirebaseRecyclerAdapter<mandatoryitemsdetails, mandatoryitemsdetailsviewholder> adapter = new FirebaseRecyclerAdapter<mandatoryitemsdetails, mandatoryitemsdetailsviewholder>(options) {
            @Override
            protected void onBindViewHolder(final mandatoryitemsdetailsviewholder holder, final int position, @NonNull mandatoryitemsdetails model) {

                holder.setItemName(model.getItemName());
                holder.setItemStatus(model.getItemStatus());
                holder.setTagID(String.valueOf(model.getTagId()));
                holder.setDescriptions(model.getDescription());
                holder.setPriority(model.getPriority());
                holder.setTagInuse(model.getTagInuse());
                holder.mandatroeyoptionsbtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        PopupMenu popupMenu = new PopupMenu(mContex,holder.mandatroeyoptionsbtn);
                        popupMenu.inflate(R.menu.mandatoryoptionsmenu);
                        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem item) {
                                switch (item.getItemId()) {
                                    case R.id.additem:
                                        Tids = ((TextView) recyclerView.findViewHolderForAdapterPosition(position).itemView.findViewById(R.id.tagid)).getText().toString();
                                        gotoactivity(Tids);
                                        return true;
                                    case R.id.removeitem:
                                        Tids = ((TextView) recyclerView.findViewHolderForAdapterPosition(position).itemView.findViewById(R.id.tagid)).getText().toString();
                                        removenameanddescription(Tids);
                                        return true;
                                    default:
                                        return false;
                                }
                            }
                        });
                        popupMenu.show();
                    }
                });
            }

            @NonNull
            @Override
            public mandatoryitemsdetailsviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.mandatoryitemsview, parent, false);
                return new mandatoryitemsdetailsviewholder(view);
            }
        };
        adapter.startListening();
        recyclerView.setAdapter(adapter);
    }

    private void gotoactivity(String tids) {
        startActivity(new Intent(this, AddMandatoryItemActivity.class).putExtra("id",tids));
    }
    public void removenameanddescription(String tagid){
        databaseReference = FirebaseDatabase.getInstance().getReference();
        Long t = Long.valueOf(tagid);
        mandatoryitemsdetails manatory = new mandatoryitemsdetails("Empty","OutOfBag","Empty","normal","inuse",t);
        databaseReference.child("MandatoryItemList").child(tagid).setValue(manatory);}



}