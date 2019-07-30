package com.example.nuwan.smarthandbag;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class VerifyUserActivity extends AppCompatActivity {
    EditText edEmail, edPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_user);
        edEmail = (EditText) findViewById(R.id.tiEmaillvri);
        edPassword = (EditText) findViewById(R.id.tiPasswordvri);

    }

    public void goback(View view) {
        onBackPressed();

    }

    public void verify(View view) {
        SharedPreferences Preferences = getSharedPreferences(AppConstants.PREF_SESSION_DATA,MODE_PRIVATE);
        String semail=Preferences.getString("email","0");
        String spassword=Preferences.getString("password","0");
        final String email = edEmail.getText().toString().trim();
        final String password = edPassword.getText().toString().trim();

        if (email.isEmpty()) {
            edEmail.setError("Email is required");
            edEmail.requestFocus();
            return;
        }


        if (password.isEmpty()) {
            edPassword.setError("Password is required");
            edPassword.requestFocus();
            return;
        }

        if (password.length() < 6) {
                edPassword.setError("Minimum lenght of password should be 6");
                edPassword.requestFocus();
                return;
        }
        if (semail.equals(email)&&spassword.equals(password)){
            Toast.makeText(VerifyUserActivity.this, "User verified", Toast.LENGTH_LONG).show();
            startActivity(new Intent(this, SecuritySettingsActivity.class));
        }
        else{
            Toast.makeText(VerifyUserActivity.this, "User verified unsuccessful", Toast.LENGTH_LONG).show();
        }
    }
}
