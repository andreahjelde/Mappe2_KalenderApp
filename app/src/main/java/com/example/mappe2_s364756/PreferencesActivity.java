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





        btnSmsOn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // Sjekk og be om SEND_SMS tillatelse
                    if (ContextCompat.checkSelfPermission(PreferencesActivity.this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(PreferencesActivity.this, new String[]{Manifest.permission.SEND_SMS}, SEND_SMS_PERMISSION_REQUEST_CODE);
                    } else {
                        // Tillatelse allerede gitt, send SMS og start tjenester
                        Log.d("CHECKBOXES", "Send sms-knapp er på");
                        sendMessage();
                        startService(buttonView);
                        sendBroadcast(buttonView);
                        setPeriodisk(buttonView);
                    }
                } else {
                    Log.d("CHECKBOXES", "Send sms-knapp er av");
                    stoppPeriodisk(buttonView);
                    stoppService(buttonView);
                }
            }
        });







        calendar = Calendar.getInstance();
        currentHour = calendar.get(Calendar.HOUR_OF_DAY);
        currentMinute = calendar.get(Calendar.MINUTE);




        //-------------Dialogboks hvor man kan velge klokkeslett en melding skal sendes
        openTimePickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(PreferencesActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                //Knappen oppdateres til den valgte tiden
                                setTimeMessageTxt.setText(String.format("%02d:%02d", hourOfDay, minute));



                            }
                        },
                        currentHour, currentMinute, true);
                timePickerDialog.show();
            }
        });


        Button setMessageBtn = findViewById(R.id.setMessageBtn);
        setMessageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCustomDialog();
            }
        });

    }

    //-----------Dialogboks for å endre melding som skal sendes på sms
        void showCustomDialog(){
            final Dialog dialog = new Dialog(PreferencesActivity.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(true);
            dialog.setContentView(R.layout.dialog);

                Button dialogButtonAdd = dialog.findViewById(R.id.dialogButtonAddEvent);
                EditText dialogInput = dialog.findViewById(R.id.dialogInput);

                //---------------KNAPP FOR Å LEGGE TIL EN NY AKTIVITET
            dialogButtonAdd.setOnClickListener((view -> {
                String nyMelding = dialogInput.getText().toString();
                defaultMessage.setText(nyMelding);

                SharedPreferences sharedPreferences = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                editor.putString("customMessage", nyMelding);
                editor.apply();


                dialog.dismiss();
            }));
            dialog.show();
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


    public void startService(View v) {
        Intent intent = new Intent(this, MinService.class);
        this.startService(intent);
    }
    public void stoppService(View v) {
        Intent i = new Intent(this, MinService.class);
        stopService(i);
    }

    public void sendBroadcast(View v) {
        Intent intent = new Intent();
        intent.setAction("com.example.service.MITTSIGNAL");
        sendBroadcast(intent);
    }

    public void setPeriodisk(View v) {
        Intent intent = new Intent(this,SettPeriodiskService.class);
        this.startService(intent);
    }
    public void stoppPeriodisk(View v) {
        Intent i = new Intent(this, MinService.class);
        PendingIntent pintent = PendingIntent.getService(this, 0, i,
                PendingIntent.FLAG_IMMUTABLE
        ); AlarmManager alarm =
                (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        if (alarm != null) {
            alarm.cancel(pintent);
        }
    }


}













