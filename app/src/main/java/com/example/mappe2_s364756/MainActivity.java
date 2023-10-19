package com.example.mappe2_s364756;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class MainActivity extends AppCompatActivity {


    private EventDataKilde dataKilde;
    private ArrayAdapter <Event_Item> eventItemArrayAdapter;
    private List<Event_Item> eventItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn_friends = findViewById(R.id.btn_friends);
        Button btn_preferences = findViewById(R.id.btn_preferences);
        Button btn_newEvent = findViewById(R.id.btn_newEvent);




        dataKilde = new EventDataKilde(this);
        dataKilde.open();

        ListView eventItemListView = findViewById(R.id.listView);

        eventItems = dataKilde.findAllEvents();

        eventItemArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, eventItems);
        eventItemListView.setAdapter(eventItemArrayAdapter);

        eventItemListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Event_Item selectedItem = (Event_Item) parent.getItemAtPosition(position);
                Toast.makeText(MainActivity.this, "Avtale " + selectedItem.getNameEvent() + " blir slettet fra listen",
                        Toast.LENGTH_LONG).show();
                dataKilde.deleteEventItem(selectedItem.getId());
               eventItemArrayAdapter.notifyDataSetChanged();

            }
            });
                //_______________________________________________________________________________________
                btn_newEvent.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent i = new Intent(MainActivity.this, NewEventActivity.class);
                        startActivity(i);
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
        }