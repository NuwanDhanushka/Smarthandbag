package com.example.nuwan.smarthandbag;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.goodiebag.pinview.Pinview;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PinunlockActivity extends AppCompatActivity {
    private DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pinunlock);
       Pinview pin = (Pinview) findViewById(R.id.pinview);
        databaseReference= FirebaseDatabase.getInstance().getReference();
        pin.setPinViewEventListener(new Pinview.PinViewEventListener() {
            @Override
            public void onDataEntered(Pinview pinview, boolean b) {
                SharedPreferences Preferences = getSharedPreferences(AppConstants.PREF_SESSION_DATA,MODE_PRIVATE);
                String savedpin=Preferences.getString("pin","0");
                if (savedpin.equals(pinview.getValue())){
                    Toast.makeText(PinunlockActivity.this, "Pin correct",
                            Toast.LENGTH_LONG).show();
                    visitedlocationmethods v1 = new visitedlocationmethods(getApplication());
                    v1.loglocation("Unlocked by pin");
                    databaseReference.child("DeviceLock").child("LockStatus").setValue("deviceunlocked");
                    finish();
                }else {
                    Toast.makeText(PinunlockActivity.this, "Pin incorrect",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
