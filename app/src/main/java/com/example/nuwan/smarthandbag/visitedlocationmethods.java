package com.example.nuwan.smarthandbag;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class visitedlocationmethods {
    private Context myContext;
    private Geocoder geocoder;
    public visitedlocationmethods(Context context)
    {
        myContext = context;
        //geocoder = new Geocoder(myContext);
    }
    public void loglocation(final String unlockmethod){
        final Long tsLong = System.currentTimeMillis()/1000;
        final String timenow = tsLong.toString();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("/DeviceLocation");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                DeviceLocation  deviceLocation=dataSnapshot.getValue(DeviceLocation.class);
                double lat =deviceLocation.getLatitude();
                double lon =deviceLocation.getLongitude();
                Geocoder gcd = new Geocoder(myContext, Locale.getDefault());
                List<Address> addresses = null;
                try {

                    addresses = gcd.getFromLocation(lat,lon , 1);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (addresses.size() > 0) {
                  String wat =addresses.get(0).getAddressLine(0);
                    DatabaseReference mDatabase;
                    mDatabase = FirebaseDatabase.getInstance().getReference();
                    visitedlocationdetails visitedlocationdetails = new visitedlocationdetails(wat,tsLong,unlockmethod);
                    mDatabase.child("VisitedLocationLog").child(timenow).setValue(visitedlocationdetails );
                }
                else {
                    // do your stuff
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // ...
            }
        });


}
}
