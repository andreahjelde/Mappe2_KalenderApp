package com.example.mappe2_s364756;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.util.Calendar;
import java.util.List;

public class NewEventActivity extends AppCompatActivity {
    private EventDataKilde dataKilde;
    private FriendDataKilde friendDataKilde;
    private ArrayAdapter <Event_Item> eventItemArrayAdapter;
    private ArrayAdapter<Friend> friendArrayAdapter;
    private EditText input_event_name;
    private EditText input_event_date, input_event_time, input_event_place;
    List<Event_Item> eventItems;
    List<Friend> friends;
    ListView listView;
    private Calendar calendar;
    private int currentYear, currentMonth, currentDay;
    private int currentHour, currentMinute;
    private long selectedFriendId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_events);

        dataKilde = new EventDataKilde(this);
        friendDataKilde = new FriendDataKilde(this);
        dataKilde.open();
        friendDataKilde.open();

        input_event_name = findViewById(R.id.input_event_name);
        input_event_date = findViewById(R.id.input_event_date);
        input_event_time = findViewById(R.id.input_event_time);
        input_event_place = findViewById(R.id.input_event_place);
        listView = findViewById(R.id.listView);

        eventItems = dataKilde.findAllEvents();
        friends = friendDataKilde.findAllFriends();

        eventItemArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, eventItems);
        friendArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, friends);

        Button btn_saveNewEvent = findViewById(R.id.btn_saveNewEvent);

        listView.setAdapter(friendArrayAdapter);


        listView.setOnItemClickListener((parent, view, position, id) -> {
            Friend selectedFriend = (Friend) parent.getItemAtPosition(position);

            Toast.makeText(NewEventActivity.this,  selectedFriend.getNameFriend() + " har blitt valgt, med id " + selectedFriend.getIdFriend()
                    ,
                    Toast.LENGTH_LONG).show();


        });

        selectedFriendId = -1;

        calendar = Calendar.getInstance();
        currentYear = calendar.get(Calendar.YEAR);
        currentMonth = calendar.get(Calendar.MONTH);
        currentDay = calendar.get(Calendar.DAY_OF_MONTH);
        currentHour = calendar.get(Calendar.HOUR_OF_DAY);
        currentMinute = calendar.get(Calendar.MINUTE);

        input_event_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(NewEventActivity.this,new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int day) {

                            input_event_date.setText(String.format(day + "-" + (monthOfYear + 1) + "-" + year));
                    }
                }, currentYear, currentMonth, currentDay);
                datePickerDialog.show();
            }
        });

        input_event_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(NewEventActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                //Knappen oppdateres til den valgte tiden
                                input_event_time.setText(String.format("%02d:%02d", hourOfDay, minute));
                            }
                        },
                        currentHour, currentMinute, true);
                timePickerDialog.show();
            }
        });

        listView.setOnItemClickListener((parent, view, position, id) -> {
            Friend selectedFriend = (Friend) parent.getItemAtPosition(position);
            selectedFriendId = selectedFriend.getIdFriend(); // Oppdater valgt venns ID
            Toast.makeText(NewEventActivity.this, selectedFriend.getNameFriend() + " har blitt valgt, med id " + selectedFriend.getIdFriend(),
                    Toast.LENGTH_LONG).show();
        });

        btn_saveNewEvent.setOnClickListener(view -> {
            String eventName = input_event_name.getText().toString();
            String eventDate = input_event_date.getText().toString();
            String eventTime = input_event_time.getText().toString();
            String eventPlace = input_event_place.getText().toString();


            if (!eventName.isEmpty() && selectedFriendId != -1) {
                Event_Item eventItem = dataKilde.addNewEvent(eventName, eventDate, eventTime, eventPlace, selectedFriendId);
                eventItemArrayAdapter.add(eventItem);
            } else {
                Event_Item eventItem = dataKilde.addNewEvent(eventName, eventDate, eventTime, eventPlace, selectedFriendId);
                eventItemArrayAdapter.add(eventItem);
            }

            Intent i= new Intent(NewEventActivity.this, MainActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
        });
    }



    @Override
    protected void onResume() {
        dataKilde.open();
        super.onResume();
    }
    @Override
    protected void onPause() {
        dataKilde.close();
        super.onPause();
    }
}


