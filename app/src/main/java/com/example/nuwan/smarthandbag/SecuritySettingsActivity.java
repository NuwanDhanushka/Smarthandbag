package com.example.nuwan.smarthandbag;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class SecuritySettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_security_settings);
    }

    public void setPin(View view) {
        startActivity(new Intent(this, CreatePinActivity.class));
    }

    public void setPattern(View view) {
        startActivity(new Intent(this, CreatePatternUnlockActivity.class));
    }

}
