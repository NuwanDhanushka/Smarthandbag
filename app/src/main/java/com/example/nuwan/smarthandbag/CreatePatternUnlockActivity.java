package com.example.nuwan.smarthandbag;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.andrognito.patternlockview.PatternLockView;
import com.andrognito.patternlockview.listener.PatternLockViewListener;
import com.andrognito.patternlockview.utils.PatternLockUtils;

import java.util.List;

public class CreatePatternUnlockActivity extends AppCompatActivity {
    private PatternLockView mPatternLockView;
    String Pattern;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_pattern_unlock);
        mPatternLockView = (PatternLockView) findViewById(R.id.pattern_lock_view);
        mPatternLockView.addPatternLockListener(new PatternLockViewListener() {
            @Override
            public void onStarted() {

            }

            @Override
            public void onProgress(List<PatternLockView.Dot> progressPattern) {

            }

            @Override
            public void onComplete(List<PatternLockView.Dot> pattern) {
                Pattern = PatternLockUtils.patternToString(mPatternLockView,pattern);
            }

            @Override
            public void onCleared() {

            }
        });
        Button btnSet = (Button)findViewById(R.id.btnSet);
        btnSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences Preferences = getSharedPreferences(AppConstants.PREF_SESSION_DATA,MODE_PRIVATE);
                SharedPreferences.Editor editor = Preferences.edit();
                editor.putString("pattern",Pattern);
                Toast.makeText(CreatePatternUnlockActivity.this, "Pattern set successfully",
                        Toast.LENGTH_LONG).show();
                editor.commit();
                finish();
            }
        });

    }

}
