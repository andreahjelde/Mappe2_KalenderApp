package com.example.mappe2_s364756;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class FriendsActivity extends AppCompatActivity {

    private FriendDataKilde dataKilde;
    private ArrayAdapter <Friend> friendArrayAdapter;
    List<Friend> friend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);


        Button btn_newFriend = findViewById(R.id.btn_newFriend);
        ListView friendListView = findViewById(R.id.listViewFriends);


        dataKilde = new FriendDataKilde(this);
        dataKilde.open();
        friend = dataKilde.findAllFriends();
        friendArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, friend);
        friendListView.setAdapter(friendArrayAdapter);





        btn_newFriend.setOnClickListener(view -> {
            Intent i = new Intent(FriendsActivity.this, NewFriendsActivity.class);
            startActivity(i);
        });



    }
}