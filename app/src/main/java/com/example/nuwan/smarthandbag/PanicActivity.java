package com.example.nuwan.smarthandbag;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.util.prefs.Preferences;

public class PanicActivity extends AppCompatActivity {
    private EditText Ename,Enumb,Eemail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_panic);
        Ename = (EditText)findViewById(R.id.etEname);
        Enumb = (EditText)findViewById(R.id.etEnum);
        Eemail = (EditText)findViewById(R.id.etEemail);
        SharedPreferences Preferences1 = getSharedPreferences(AppConstants.PREF_SESSION_DATA, MODE_PRIVATE);
        if(Preferences1.getBoolean("emrgencyset",false)) {
                SharedPreferences Preferences = getSharedPreferences(AppConstants.PREF_SESSION_DATA, MODE_PRIVATE);
                String emname = Preferences.getString("emergencycontactName ", null);
                String emnum = Preferences.getString("emergencycontactNumber", null);
                String emmail = Preferences.getString("emergencycontactEmail", null);
                Ename.setText(emname);
                Enumb.setText(emnum);
                Eemail.setText(emmail);
            }

    }

    public void setemergencycontact(View view) {
        SharedPreferences Preferences = getSharedPreferences(AppConstants.PREF_SESSION_DATA, MODE_PRIVATE);
        SharedPreferences.Editor editor = Preferences.edit();
        editor.putBoolean("emrgencyset",true);
        editor.putString("emergencycontactName ",Ename.getText().toString());
        editor.putString("emergencycontactNumber",Enumb.getText().toString());
        editor.putString("emergencycontactEmail",Eemail.getText().toString());
        editor.commit();
        finish();
    }
}
