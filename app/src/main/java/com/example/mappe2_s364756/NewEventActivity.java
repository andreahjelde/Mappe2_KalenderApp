package com.example.mappe2_s364756;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class NewEventActivity extends AppCompatActivity {
    private EventDataKilde dataKilde;
    private ArrayAdapter <Event_Item> eventItemArrayAdapter;
    private EditText input_event_name;
    private EditText input_event_date, input_event_time, input_event_place;
    private List<Event_Item> eventItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_events);

        dataKilde = new EventDataKilde(this);
        dataKilde.open();

        input_event_name = findViewById(R.id.input_event_name);
        input_event_date = findViewById(R.id.input_event_date);
        input_event_time = findViewById(R.id.input_event_time);
        input_event_place = findViewById(R.id.input_event_place);


        eventItems = dataKilde.findAllEvents();

        eventItemArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, eventItems);


        Button btn_saveNewEvent = findViewById(R.id.btn_saveNewEvent);

        btn_saveNewEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String eventName = input_event_name.getText().toString();
                String eventDate = input_event_date.getText().toString();
                String eventTime = input_event_time.getText().toString();
                String eventPlace = input_event_place.getText().toString();
                if(!eventName.isEmpty() ){
                    Event_Item eventItem = dataKilde.addNewEvent(eventName);
                    eventItemArrayAdapter.add(eventItem);
                }

                Intent i= new Intent(NewEventActivity.this, MainActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });
    };
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


