package com.example.mappe2_s364756;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn_friends = findViewById(R.id.btn_friends);
        Button btn_preferences = findViewById(R.id.btn_preferences);
        Button btn_newEvent = findViewById(R.id.btn_newEvent);
        listView = findViewById(R.id.listView);

        //---------------KNAPP FOR Å ÅPNE DIALOGBOKS - LEGG TIL AVTALE
        btn_newEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCustomDialog();
                ;
            }
        });

        //---------------KNAPP FOR Å GÅ TIL VENN-AKTIVITET
        btn_friends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, FriendsActivity.class);
                startActivity(i);
            }
        });

        //---------------KNAPP FOR Å GÅ TIL PREFERANSE-AKTIVITET
        btn_preferences.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, PreferencesActivity.class);
                startActivity(i);
            }
        });
    }

    //---------------FUNKSJON FOR VISDIALOG - oppretter innhold

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

        //---------------KNAPP FOR Å LEGGE TIL EN NY AKTIVITET
        dialogButtonAddEvent.setOnClickListener((view -> {
            String name = dialogInputNameOfEvent.getText().toString();


            String date = dialogInputDate.getText().toString();
            String time = dialogInputTime.getText().toString();
            String place = dialogInputPlace.getText().toString();


            List<Oppgave> oppgaveList = new ArrayList<>();
            oppgaveList.add(new Oppgave(name, date, time, place));
            ArrayAdapter<Oppgave> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, oppgaveList);
            listView.setAdapter(adapter);

            //---------------KNAPP SOM AKTIVERES NÅR MAN TRYKKER PÅ ET OBJEKT I LISTEN
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Oppgave selectedOppgave = (Oppgave) parent.getItemAtPosition(position);
                    // Handle item click here
                    Toast.makeText(MainActivity.this, "Clicked on: " + selectedOppgave.getId(),
                            Toast.LENGTH_LONG).show();
                }
            });

            dialog.dismiss();
        }));
        dialog.show();

//---------------KNAPP FOR Å LUKKE DIALOGBOKS
        dialogButtonExit.setOnClickListener((view -> {
            dialog.dismiss();
        }));
    }


}