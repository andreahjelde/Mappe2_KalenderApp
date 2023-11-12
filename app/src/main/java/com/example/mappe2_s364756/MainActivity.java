package com.example.mappe2_s364756;

import static androidx.core.content.ContentProviderCompat.requireContext;


import android.content.BroadcastReceiver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Calendar calendar;
    private int currentYear, currentMonth, currentDay;
    private int currentHour, currentMinute;
    private ArrayAdapter<Friend> friendArrayAdapter;
    private EditText input_event_name;
    private EditText input_event_date, input_event_time, input_event_place;
    private EventDataKilde dataKilde;
    private ArrayAdapter<Event_Item> eventItemArrayAdapter;
    List<Event_Item> eventItems;
    private static final int SEND_SMS_PERMISSION_REQUEST_CODE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        BroadcastReceiver myBroadcastReceiver = new MinBroadcastReceiver();
        IntentFilter filter = new IntentFilter("com.example.service.MITTSIGNAL");
        filter.addAction("com.example.service.MITTSIGNAL");
        this.registerReceiver(myBroadcastReceiver, filter);


        //--------Dialogboks for å tillate sms-tjeneste
        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new
                            String[]{android.Manifest.permission.SEND_SMS},
                    SEND_SMS_PERMISSION_REQUEST_CODE);
        }


    Button btn_friends = findViewById(R.id.btn_friends);
        Button btn_preferences = findViewById(R.id.btn_preferences);
        Button btn_newEvent = findViewById(R.id.btn_newEvent);


        calendar = Calendar.getInstance();
        currentYear = calendar.get(Calendar.YEAR);
        currentMonth = calendar.get(Calendar.MONTH);
        currentDay = calendar.get(Calendar.DAY_OF_MONTH);
        currentHour = calendar.get(Calendar.HOUR_OF_DAY);
        currentMinute = calendar.get(Calendar.MINUTE);


        dataKilde = new EventDataKilde(this);
        dataKilde.open();

        ListView eventItemListView = findViewById(R.id.listView);

        eventItems = dataKilde.findAllEvents();

        eventItemArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, eventItems);
        eventItemListView.setAdapter(eventItemArrayAdapter);


        //_______________________________________________________________________________________
        btn_newEvent.setOnClickListener(view -> {
            Intent i = new Intent(MainActivity.this, NewEventActivity.class);
            startActivity(i);
        });

        btn_friends.setOnClickListener(view -> {
            Intent i = new Intent(MainActivity.this, FriendsActivity.class);
            startActivity(i);
        });

        btn_preferences.setOnClickListener(view -> {
            Intent i = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(i);
        });


        //------------------Dialogboks for å slette avtale når man trykker på et element fra listen
        eventItemListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Event_Item selectedItem = (Event_Item) parent.getItemAtPosition(position);

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Bekreft sletting");
                builder.setMessage("Er du sikker på at du vil slette avtalen " + selectedItem.getNameEvent() + "?");
                builder.setPositiveButton("Ja", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dataKilde.deleteEventItem(selectedItem.getIdEvent());
                        eventItemArrayAdapter.remove(selectedItem); // Fjern elementet fra adapteren
                        eventItemArrayAdapter.notifyDataSetChanged();
                    }
                });
                builder.setNegativeButton("Avbryt", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                builder.show();
            }
        });
    }

    //__________________________________________________________________________
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == SEND_SMS_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, you can now send SMS
            } else {
                Toast.makeText(
                        this,
                        "SMS tillatelse ikke gitt. Du kan ikke sende SMS.",
                        Toast.LENGTH_SHORT
                ).show();
                // Assuming send is a button, you should disable it using the following code:
                //send.setEnabled(false);
            }
        }
    }
}