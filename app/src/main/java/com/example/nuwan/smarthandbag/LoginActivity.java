package com.example.nuwan.smarthandbag;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity  {
    FirebaseAuth mAuth;
    EditText edEmail, edPassword;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        edEmail = (EditText) findViewById(R.id.tiEmaillog);
        edPassword = (EditText) findViewById(R.id.tiPasswordlog);

    }
    private void userLogin() {
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

     //   progressBar.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
               // progressBar.setVisibility(View.GONE);
                if (task.isSuccessful()) {
                    SharedPreferences Preferences = getSharedPreferences(AppConstants.PREF_SESSION_DATA,MODE_PRIVATE);
                    SharedPreferences.Editor editor = Preferences.edit();
                    editor.putString("email",email);
                    editor.putString("password",password);
                    editor.commit();
                    Toast.makeText(LoginActivity.this, getString(R.string.login_success), Toast.LENGTH_LONG).show();
                    finish();
                    Intent intent = new Intent(LoginActivity.this, MainMenuActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mAuth.getCurrentUser() != null) {
            finish();
            startActivity(new Intent(this, MainMenuActivity.class));
        }
    }

    public void login(View view) {
        userLogin();
    }
}
