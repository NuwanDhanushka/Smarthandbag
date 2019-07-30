package com.example.nuwan.smarthandbag;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class UnlockDeviceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unlock_device);
    }
    public void unlockpattern(View view) {
        startActivity(new Intent(this, PatternUnlockActivity.class));
    }

    public void unlockpin(View view) {
        startActivity(new Intent(this, PinunlockActivity.class));
    }


    public void fingerunlock(View view) {
        startActivity(new Intent(this, fingerunlockActivity.class));
    }
}
