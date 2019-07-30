package com.example.nuwan.smarthandbag;

import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.andrognito.patternlockview.PatternLockView;
import com.andrognito.patternlockview.listener.PatternLockViewListener;
import com.andrognito.patternlockview.utils.PatternLockUtils;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.List;
import java.util.Locale;

public class PatternUnlockActivity extends AppCompatActivity {
    private PatternLockView mPatternLockView;
    private DatabaseReference databaseReference;
    private PatternLockViewListener mPatternLockViewListener = new PatternLockViewListener() {
        @Override
        public void onStarted() {

        }

        @Override
        public void onProgress(List<PatternLockView.Dot> progressPattern) {

        }

        @Override
        public void onComplete(List<PatternLockView.Dot> pattern) {
            SharedPreferences Preferences = getSharedPreferences(AppConstants.PREF_SESSION_DATA,MODE_PRIVATE);
            String savedpattern=Preferences.getString("pattern","0");
            if (savedpattern.equals(PatternLockUtils.patternToString(mPatternLockView, pattern))){
                Toast.makeText(PatternUnlockActivity.this, "Pattern correct",
                        Toast.LENGTH_LONG).show();
                mPatternLockView.setViewMode(PatternLockView.PatternViewMode.CORRECT);
                visitedlocationmethods v1 = new visitedlocationmethods(getApplication());
                v1.loglocation("Unlocked by patten");
                    databaseReference.child("DeviceLock").child("LockStatus").setValue("deviceunlocked");
                    finish();
            }else {
                Toast.makeText(PatternUnlockActivity.this, "Pattern incorrect",
                        Toast.LENGTH_LONG).show();
                mPatternLockView.setViewMode(PatternLockView.PatternViewMode.WRONG);
            }

        }

        @Override
        public void onCleared() {


        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pattern_unlock);
        mPatternLockView = (PatternLockView) findViewById(R.id.pattern_lock_view);
        mPatternLockView.addPatternLockListener(mPatternLockViewListener);
        databaseReference= FirebaseDatabase.getInstance().getReference();
    }

}
