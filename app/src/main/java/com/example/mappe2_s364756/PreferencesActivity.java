package com.example.mappe2_s364756;


import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.InputType;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import org.w3c.dom.Text;

import java.util.Calendar;
import java.util.Locale;

public class PreferencesActivity extends AppCompatActivity {


    CheckBox btnOf;
    private Calendar calendar;
    private int currentHour, currentMinute;
    TextView defaultMessage;
    TextView setTimeMessageTxt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);
        //picker.setIs24HourView(true);

        btnOf = findViewById(R.id.btnOf);
        Button openTimePickerButton = findViewById(R.id.chooseTimeButton);
        defaultMessage = findViewById(R.id.defaultMessage);
        setTimeMessageTxt = findViewById(R.id.setTimeMessageTxt);


        calendar = Calendar.getInstance();
        currentHour = calendar.get(Calendar.HOUR_OF_DAY);
        currentMinute = calendar.get(Calendar.MINUTE);

        SharedPreferences sharedPreferences = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
        String savedMessage = sharedPreferences.getString("customMessage", "");
        String savedTime = sharedPreferences.getString("setTime", "");

        if (!savedMessage.isEmpty()  ) {
            defaultMessage.setText(savedMessage);

        }
        if(!savedTime.isEmpty()){
            setTimeMessageTxt.setText(savedTime);
        }


        openTimePickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(PreferencesActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                //Knappen oppdateres til den valgte tiden
                                setTimeMessageTxt.setText(String.format("%02d:%02d", hourOfDay, minute));

                                SharedPreferences sharedPreferences = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();

                                // Lagre tiden
                                editor.putString("setTime", String.format(Locale.getDefault(), "%02d:%02d", hourOfDay, minute));
                                editor.apply();
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
}













