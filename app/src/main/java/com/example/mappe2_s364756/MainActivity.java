package com.example.mappe2_s364756;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView innholdTest;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn_friends = findViewById(R.id.btn_friends);
        Button btn_preferences = findViewById(R.id.btn_preferences);


        Button btn_newEvent = findViewById(R.id.btn_newEvent);
        innholdTest = findViewById(R.id.innholdTest);

        btn_newEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCustomDialog();
                ;
            }
        });



        btn_friends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, FriendsActivity.class);
                startActivity(i);
            }
        });

        btn_preferences.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, PreferencesActivity.class);
                startActivity(i);
            }
        });
    }

    void showCustomDialog(){
        final Dialog dialog = new Dialog(MainActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.custom_dialog);

        final EditText dialogInputNameOfEvent = dialog.findViewById(R.id.dialogInputNameOfEvent);
        final EditText dialogInputDate = dialog.findViewById(R.id.dialogInputDate);
        final EditText dialogInputTime = dialog.findViewById(R.id.dialogInputTime);
        final EditText dialogInputPlace = dialog.findViewById(R.id.dialogInputPlace);
        Button dialogButtonAddEvent = dialog.findViewById(R.id.dialogButtonAddEvent);
        Button dialogButtonExit = dialog.findViewById(R.id.dialogButtonExit);

        dialogButtonAddEvent.setOnClickListener((view -> {
            String nameEvent = dialogInputNameOfEvent.getText().toString();
            String date = dialogInputDate.getText().toString();
            String time = dialogInputTime.getText().toString();
            String place = dialogInputPlace.getText().toString();
            innholdTest.setText("Avtale: " + nameEvent + " Dato: " + date + " Klokkeslett: " + time + " Sted: " + place);
            dialog.dismiss();
        }));
        dialog.show();
    }


}