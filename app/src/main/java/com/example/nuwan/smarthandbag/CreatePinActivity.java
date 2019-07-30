package com.example.nuwan.smarthandbag;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.goodiebag.pinview.Pinview;

public class CreatePinActivity extends AppCompatActivity {
    String userpin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_pin);
        Pinview pin = (Pinview) findViewById(R.id.pinview2);
        pin.setPinViewEventListener(new Pinview.PinViewEventListener() {
            @Override
            public void onDataEntered(Pinview pinview, boolean b) {

                 userpin = pinview.getValue();
            }
        });
        Button btnSet = (Button) findViewById(R.id.btnSetPin);
        btnSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences Preferences = getSharedPreferences(AppConstants.PREF_SESSION_DATA, MODE_PRIVATE);
                SharedPreferences.Editor editor = Preferences.edit();
                editor.putString("pin",userpin );
                Toast.makeText(CreatePinActivity.this, "Pin set successfully",
                        Toast.LENGTH_LONG).show();
                editor.commit();
                finish();
            }
        });
    }
}