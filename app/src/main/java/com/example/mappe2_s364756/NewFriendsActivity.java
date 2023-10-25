package com.example.mappe2_s364756;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class NewFriendsActivity extends AppCompatActivity {

    private FriendDataKilde dataKilde;
    private ArrayAdapter<Friend> friendArrayAdapter;
    List<Friend> friend;
    private EditText input_friend_name;
    private EditText input_friend_phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_friends);

        dataKilde = new FriendDataKilde(this);
        dataKilde.open();

        input_friend_name = findViewById(R.id.input_friend_name);
        input_friend_phone = findViewById(R.id.input_friend_phone);


        ListView friendListView = findViewById(R.id.listViewFriends);
        friend = dataKilde.findAllFriends();
        friendArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, friend);


        Button btn_saveNewFriend = findViewById(R.id.btn_saveNewFriend);

        btn_saveNewFriend.setOnClickListener(view -> {
            String friendName = input_friend_name.getText().toString();
            String friendPhone = input_friend_phone.getText().toString();
            if(!friendName.isEmpty() ){
                Friend friend = dataKilde.addNewFriend(friendName, friendPhone);
                friendArrayAdapter.add(friend);
            }

            Intent i= new Intent(NewFriendsActivity.this, FriendsActivity.class);
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