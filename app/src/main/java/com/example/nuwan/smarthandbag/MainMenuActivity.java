package com.example.nuwan.smarthandbag;

import android.Manifest;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainMenuActivity extends AppCompatActivity {
    FirebaseAuth mAuth;
    double latt = 6.9270786;
    double lngg = 79.86124300000006;
    String emname,emmail;

    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.SEND_SMS},
                MY_PERMISSIONS_REQUEST_SEND_SMS);
    }




    public void findmybagview(View view) {
        startActivity(new Intent(this, Findmybagactivity.class));
    }

    public void unlockview(View view) {
        startActivity(new Intent(this, UnlockDeviceActivity.class));
    }

    public void settingsview(View view) {
        startActivity(new Intent(this, SettingsActivity.class));
    }

    public void mandatorylistview(View view) {
        startActivity(new Intent(this,MandatoryItemsDetailsActivity.class));
    }

    public void panicbuttonclick(View view) {

        if(issetpanic()){
            SharedPreferences Preferences = getSharedPreferences(AppConstants.PREF_SESSION_DATA,MODE_PRIVATE);
            emname =Preferences.getString("emergencycontactName ",null);
            String emnum =Preferences.getString("emergencycontactNumber",null);
            emmail =Preferences.getString("emergencycontactEmail",null);
          /*  Intent intent = new Intent(Intent.ACTION_VIEW, Uri.fromParts("sms", emnum, null ));
            intent.putExtra("sms_body" , "Hello dear !");
            startActivity(intent);*/
            DatabaseReference currentDBcordinates = FirebaseDatabase.getInstance().getReference().child("DeviceLocation");
            currentDBcordinates.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                     latt = dataSnapshot.child("latitude").getValue(Double.class);
                     lngg = dataSnapshot.child("longitude").getValue(Double.class);
                } @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            sendSMS(emnum,"Hi "+emname+" Im in an emergency situation  my location is latitude : "+latt+" longitude : "+lngg+"");
            new Thread(new Runnable() {

                @Override
                public void run() {
                    try {
                        GMailSender sender = new GMailSender("smarthandbagtm@gmail.com",
                                "123456nuwan");
                        sender.sendMail("Hello from Smarthandbag", "Hi "+emname+" Im in an emergency situation  my location is latitude : "+latt+"longitude : "+lngg+"",
                                "smarthandbagtm@gmail.com", ""+emmail);
                    } catch (Exception e) {
                        Log.e("SendMail", e.getMessage(), e);
                    }
                }

            }).start();
           /* Intent intent2  = new Intent(Intent.ACTION_SEND);
            intent2.putExtra(Intent.EXTRA_EMAIL, new String[]{emmail});
            intent2.putExtra(Intent.EXTRA_SUBJECT, "PanicEmail");
            intent2.putExtra(Intent.EXTRA_TEXT, "This is body");
            intent2.setType("message/rfc822");
            startActivity(intent2);*/
        }else {
            startActivity(new Intent(this,PanicActivity.class));
        }
    }
    public boolean issetpanic(){
        SharedPreferences Preferences = getSharedPreferences(AppConstants.PREF_SESSION_DATA,MODE_PRIVATE);
        return  Preferences.getBoolean("emrgencyset", false);
    }
    private void sendSMS2(String phoneNumber, String message) {
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(phoneNumber, null, message, null, null);
    }
    void sendSMS(String phoneNumber, String message)
    {
        String SENT = "SMS_SENT";
        String DELIVERED = "SMS_DELIVERED";

        PendingIntent sentPI = PendingIntent.getBroadcast(this, 0,
                new Intent(SENT), 0);

        PendingIntent deliveredPI = PendingIntent.getBroadcast(this, 0,
                new Intent(DELIVERED), 0);

        //---when the SMS has been sent---
        registerReceiver(new BroadcastReceiver(){
            @Override
            public void onReceive(Context arg0, Intent arg1) {
                switch (getResultCode())
                {
                    case Activity.RESULT_OK:
                        Toast.makeText(getBaseContext(), "SMS sent",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                        Toast.makeText(getBaseContext(), "Generic failure",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NO_SERVICE:
                        Toast.makeText(getBaseContext(), "No service",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NULL_PDU:
                        Toast.makeText(getBaseContext(), "Null PDU",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_RADIO_OFF:
                        Toast.makeText(getBaseContext(), "Radio off",
                                Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        }, new IntentFilter(SENT));

        //---when the SMS has been delivered---
        registerReceiver(new BroadcastReceiver(){
            @Override
            public void onReceive(Context arg0, Intent arg1) {
                switch (getResultCode())
                {
                    case Activity.RESULT_OK:
                        Toast.makeText(getBaseContext(), "SMS delivered",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case Activity.RESULT_CANCELED:
                        Toast.makeText(getBaseContext(), "SMS not delivered",
                                Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        }, new IntentFilter(DELIVERED));

        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(phoneNumber, null, message, sentPI, deliveredPI);
    }
}
