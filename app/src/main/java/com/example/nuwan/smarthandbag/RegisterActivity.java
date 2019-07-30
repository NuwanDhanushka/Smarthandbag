package com.example.nuwan.smarthandbag;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{
 private EditText edFname,edLname,edMobile,edEmail,edPass;
 private String deviceId;
 ProgressBar progressBar;
 private FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        edFname=findViewById(R.id.tiFname);
        edLname=findViewById(R.id.tiLname);
        edMobile=findViewById(R.id.tiMobile);
        edEmail=findViewById(R.id.tiEmail);
        edPass=findViewById(R.id.tiPassword);
        progressBar=findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
        mAuth = FirebaseAuth.getInstance();
        findViewById(R.id.button_register).setOnClickListener(this);
        SharedPreferences preferences =getSharedPreferences(AppConstants.PREF_SESSION_DATA,MODE_PRIVATE);
        deviceId=preferences.getString("deviceid","");

    }

    private void registerUser(){
        final String Fname = edFname.getText().toString().trim();
        final String Lname = edLname.getText().toString().trim();
        final String mobile = edMobile.getText().toString().trim();
        final String email = edEmail.getText().toString().trim();
        String password=edPass.getText().toString().trim();

        if (Fname.isEmpty()) {
            edFname.setError(getString(R.string.input_error_name));
            edFname.requestFocus();
            return;
        }
        if (Lname.isEmpty()) {
            edLname.setError(getString(R.string.input_error_name));
            edLname.requestFocus();
            return;
        }

        if (email.isEmpty()) {
            edEmail.setError(getString(R.string.input_error_email));
            edEmail.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            edPass.setError(getString(R.string.input_error_password));
            edPass.requestFocus();
            return;
        }

        if (password.length() < 6) {
            edPass.setError(getString(R.string.input_error_password_length));
            edPass.requestFocus();
            return;
        }

        if (mobile.isEmpty()) {
            edMobile.setError(getString(R.string.input_error_phone));
            edMobile.requestFocus();
            return;
        }

        if (mobile.length() != 10) {
            edMobile.setError(getString(R.string.input_error_phone_invalid));
            edMobile.requestFocus();
            return;
        }


        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {

                            user user = new user(
                                    Fname,
                                    Lname,
                                    email,
                                    mobile,
                                    deviceId
                            );

                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    progressBar.setVisibility(View.GONE);
                                    if (task.isSuccessful()) {
                                        Toast.makeText(RegisterActivity.this, getString(R.string.registration_success), Toast.LENGTH_LONG).show();
                                        SharedPreferences preferences =getSharedPreferences(AppConstants.PREF_SESSION_DATA,MODE_PRIVATE);
                                        SharedPreferences.Editor editor = preferences.edit();
                                        editor.putBoolean("registerd", true);
                                        editor.commit();
                                        Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(intent);
                                    } else {
                                        Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                        } else {
                            Toast.makeText(RegisterActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_register:
                registerUser();
                break;
            case R.id.button_login:
                loginusr();
                break;
        }
    }

    private void loginusr() {
        startActivity(new Intent(this,LoginActivity.class));
        SharedPreferences preferences =getSharedPreferences(AppConstants.PREF_SESSION_DATA,MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("registerd", true);
        editor.commit();

    }
}

