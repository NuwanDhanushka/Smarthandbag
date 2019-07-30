package com.example.nuwan.smarthandbag;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.SystemClock;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.Chronometer;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LastlocationMapsActivity extends FragmentActivity implements OnMapReadyCallback {
    private Chronometer chronometer;
    private boolean running;
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lastlocation_maps);
        chronometer = findViewById(R.id.chronometer);
        chronometer.setFormat("     Last location is updated %s ago.");
        chronometer.setBase(SystemClock.elapsedRealtime());
        chronometer.start();
        running = true;
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        DatabaseReference currentDBcordinates = FirebaseDatabase.getInstance().getReference().child("LastLocations");
        double latt = 6.9270786;
        double lngg = 79.86124300000006;
        final Circle circle = mMap.addCircle(new CircleOptions()
                .center(new LatLng(latt, lngg))
                .radius(20)
                .strokeColor(Color.RED)
                .fillColor(0x220000FF));
        MarkerOptions a = new MarkerOptions().position(new LatLng(latt, lngg)).title("Location1").icon(BitmapDescriptorFactory.fromResource(R.drawable.lastlocation1));
        MarkerOptions b = new MarkerOptions().position(new LatLng(latt, lngg)).title("Location2").icon(BitmapDescriptorFactory.fromResource(R.drawable.lastlocation2));
        MarkerOptions c = new MarkerOptions().position(new LatLng(latt, lngg)).title("Location3").icon(BitmapDescriptorFactory.fromResource(R.drawable.lastlocation3));

        final Marker m = mMap.addMarker(a);
        final Marker n = mMap.addMarker(b);
        final Marker o = mMap.addMarker(c);
        currentDBcordinates.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String location1 = dataSnapshot.child("location1").getValue(String.class);
                String location2 = dataSnapshot.child("location2").getValue(String.class);
                String location3 = dataSnapshot.child("location3").getValue(String.class);
                String[] separatedlatlag1 = location1.split(",");
                double latt =Double.valueOf(separatedlatlag1 [0].trim());
                double lngg =Double.valueOf(separatedlatlag1 [1].trim());
                String[] separatedlatlag2 = location2.split(",");
                double latt1 =Double.valueOf(separatedlatlag2 [0].trim());
                double lngg1 =Double.valueOf(separatedlatlag2 [1].trim());
                String[] separatedlatlag3 = location3.split(",");
                double latt2 =Double.valueOf(separatedlatlag3 [0].trim());
                double lngg2 =Double.valueOf(separatedlatlag3 [1].trim());

                m.setPosition(new LatLng(latt, lngg));
                n.setPosition(new LatLng(latt1, lngg1));
                o.setPosition(new LatLng(latt2, lngg2));

                chronometer.setBase(SystemClock.elapsedRealtime());
                CameraPosition cameraPosition = new CameraPosition.Builder().zoom(20).target(new LatLng(latt2, lngg2)).build();
                mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(latt2, lngg2)));
                mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                circle.setCenter(new LatLng(latt2, lngg2));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
