package com.example.nuwan.smarthandbag;

import android.app.AlarmManager;
import android.app.DialogFragment;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class AddMandatoryItemActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {
    private NotificationManagerCompat notificationManager;
    private TextView refidid,time;
    private EditText imName,imDesc;
    private String IDtag,itenme,des;
    private CheckBox checkBox1;
    private DatabaseReference databaseReference;
    public static final String CHANNEL_1_ID = "channel1";
    private static int starth=0;
    private static int startmin=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_mandatory_item);
        TextView txt= (TextView) findViewById(R.id.rfidid);
        checkBox1 = (CheckBox) findViewById(R.id.checkBox);

        imName = (EditText)findViewById(R.id.tvItemName);
        imDesc = (EditText)findViewById(R.id.tvItemDescription);

        time=(TextView) findViewById(R.id.textView21);
        notificationManager = NotificationManagerCompat.from(this);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        Button button = (Button) findViewById(R.id.button8);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.support.v4.app.DialogFragment timepicker = new TimePickerFragment();
                timepicker.show(getSupportFragmentManager(),"time picker");

            }
        });

        Bundle extras = getIntent().getExtras();
        if (extras != null)
        {
            IDtag = extras.getString("id");

            txt.setText(IDtag);
        }
        else
        {
            //..oops!
        }


        checkBox1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if ( isChecked )
                {
                    Notification notification = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_1_ID)
                            .setSmallIcon(R.drawable.loginhandbagimage)
                            .setContentTitle("Set a time")
                            .setContentText("Click Time Button")
                            .setPriority(NotificationCompat.PRIORITY_HIGH)
                            .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                            .build();

                    notificationManager.notify(1, notification);
                }

            }
        });



    }

    public void additems(View view) {
             Long t = Long.valueOf(IDtag);
        final String itenme = imName.getText().toString().trim();
        if (itenme.isEmpty()) {
            imName.setError(getString(R.string.input_error_name));
            imName.requestFocus();
            return;
        }
        final String des = imDesc.getText().toString().trim();
        if (des.isEmpty()) {
            imDesc.setError(getString(R.string.input_error_name));
            imDesc.requestFocus();
            return;
        }

             mandatoryitemsdetails manatory = new mandatoryitemsdetails(itenme,"OutOfBag",des,"normal","inuse",t);
             databaseReference.child("MandatoryItemList").child(IDtag).setValue(manatory);
        if(checkBox1.isChecked()){
             SharedPreferences Preferences = getSharedPreferences(AppConstants.PREF_SESSION_DATA, MODE_PRIVATE);
             SharedPreferences.Editor editor = Preferences.edit();
             editor.putString("itemDescription",itenme+" "+des);
             editor.commit();
             SetFutureStartTime(starth, startmin);}
             finish();
    }



    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        TextView textView = (TextView)findViewById(R.id.textView21);
        textView.setText(""+hourOfDay+":"+minute);
        starth=hourOfDay;
        startmin=minute;

    }
    public void SetFutureStartTime(int startHour, int startMin)
    {
        Context context = this;

        AlarmManager m_alarmMgr = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, ItemsAlarm.class);
        PendingIntent m_alarmIntent = PendingIntent.getBroadcast(context, (int) System.currentTimeMillis(), intent, 0);
        // Set the alarm to start at 21:32 PM
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, startHour);
        calendar.set(Calendar.MINUTE, startMin);

        m_alarmMgr.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), m_alarmIntent);
    }

}