package com.example.nuwan.smarthandbag;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Findmybagactivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_findmybagactivity);
    }
    public void baglocation(View view) {
        startActivity(new Intent(this, BagLocationActivity.class));
    }
    public void visitedlocationvw(View view) {
        startActivity(new Intent(this, VisitedLocationActivity.class));
    }

    public void lastlocationmap(View view) {
        startActivity(new Intent(this, LastlocationMapsActivity.class));
    }
}
