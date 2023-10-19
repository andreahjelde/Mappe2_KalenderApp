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

public class FriendsActivity extends AppCompatActivity {

    private EventDataKilde dataKilde;
    private ArrayAdapter<Event_Item> eventItemArrayAdapter;
    private List<Event_Item> eventItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);


        Button btn_newFriend = findViewById(R.id.btn_newFriend);


        btn_newFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(FriendsActivity.this, NewFriendsActivity.class);
                startActivity(i);
            }
        });



    }
}