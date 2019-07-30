package com.example.nuwan.smarthandbag;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
            }
    public void logout(View view) {
        FirebaseAuth.getInstance().signOut();
        finish();
        startActivity(new Intent(this, LoginActivity.class));
    }
    public void setgeofence(View view) {
        startActivity(new Intent(this, GeofenceActivity.class));
    }

    public void gosecuritysettings(View view) {
        startActivity(new Intent(this, VerifyUserActivity.class));
    }

    public void gopanicsettings(View view) {
        startActivity(new Intent(this, PanicActivity.class));
    }
}
