package com.example.nuwan.smarthandbag;

        import android.content.Intent;
        import android.content.SharedPreferences;
        import android.support.annotation.NonNull;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.util.Log;
        import android.view.View;
        import android.widget.EditText;
        import android.widget.Toast;

        import com.google.firebase.FirebaseApp;
        import com.google.firebase.auth.FirebaseAuth;
        import com.google.firebase.database.DataSnapshot;
        import com.google.firebase.database.DatabaseError;
        import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.database.FirebaseDatabase;
        import com.google.firebase.database.ValueEventListener;
        import com.google.zxing.integration.android.IntentIntegrator;
        import com.google.zxing.integration.android.IntentResult;

public class QrActivity extends AppCompatActivity {
    String code;
    String gdeviceid;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr);
        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() != null) {
            finish();
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        }
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        DatabaseReference myRef = database.getReference("DeviceInfo");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                 gdeviceid = dataSnapshot.child("DeviceID").getValue(String.class);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });






    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent=null;
        if(isregisterd()) {
            intent = new Intent(QrActivity.this,LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }


    public void scanqr(View view) {
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setOrientationLocked(false);
        integrator.initiateScan();


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if(result.getContents() == null) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Scanned : " + result.getContents(), Toast.LENGTH_LONG).show();
                code=result.getContents();
                if (code.equals(gdeviceid)){
                        goToRegister();}
                else {
                    Toast.makeText(this, "No device found", Toast.LENGTH_LONG).show();
                }
                        }
            }
        else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
    public void goToRegister(){
        SharedPreferences Preferences = getSharedPreferences(AppConstants.PREF_SESSION_DATA,MODE_PRIVATE);
        SharedPreferences.Editor editor = Preferences.edit();
        editor.putString("deviceid",code);
        editor.commit();
        Intent intent = new Intent(QrActivity.this,RegisterActivity.class);
        startActivity(intent);
    }
    public boolean isregisterd(){
        SharedPreferences Preferences = getSharedPreferences(AppConstants.PREF_SESSION_DATA,MODE_PRIVATE);
        return  Preferences.getBoolean("registerd", false);
    }

}
