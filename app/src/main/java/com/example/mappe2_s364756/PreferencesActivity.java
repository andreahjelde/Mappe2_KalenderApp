package com.example.mappe2_s364756;


import android.Manifest;
import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.CheckBox;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.preference.PreferenceManager;


import org.w3c.dom.Text;

import java.util.Calendar;
import java.util.Locale;

public class PreferencesActivity extends AppCompatActivity {

    private static final int SEND_SMS_PERMISSION_REQUEST_CODE = 1;
    private EditText telefon;
    private EditText melding;
    private Button send;

    Button btnSmsOf;
    CheckBox btnSmsOn;
    private Calendar calendar;
    private int currentHour, currentMinute;
    TextView defaultMessage;
    TextView setTimeMessageTxt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);

        // Få tilgang til preferanseverdier
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String time = preferences.getString("time", "");
        String message = preferences.getString("message", "");
        boolean notifications = preferences.getBoolean("notifications", true);

        //picker.setIs24HourView(true);
        BroadcastReceiver myBroadcastReceiver = new MinBroadcastReceiver();
        IntentFilter filter = new IntentFilter("com.example.service.MITTSIGNAL");
        filter.addAction("com.example.service.MITTSIGNAL");
        this.registerReceiver(myBroadcastReceiver, filter);

        Button openTimePickerButton = findViewById(R.id.chooseTimeButton);
        defaultMessage = findViewById(R.id.defaultMessage);
        setTimeMessageTxt = findViewById(R.id.setTimeMessageTxt);
        btnSmsOn = findViewById(R.id.btnSmsOn);
        telefon = findViewById(R.id.telefon);






    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == SEND_SMS_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED) {
        // Permission granted, you can now send SMS
            } else {
                Toast.makeText(
                        this,
                        "SMS tillatelse ikke gitt. Du kan ikke sende SMS.",
                        Toast.LENGTH_SHORT
                ).show();
                send.setEnabled(false);
            }
        }
    }

    private void sendMessage() {
        String phoneNumber = telefon.getText().toString();
        String message = defaultMessage.getText().toString();
        if (!phoneNumber.isEmpty() && !message.isEmpty()) {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNumber, null, message, null, null);
            Toast.makeText(this, "SMS sendt", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Skriv inn telefon og melding.",
                    Toast.LENGTH_SHORT).show();
        }
    }





}













