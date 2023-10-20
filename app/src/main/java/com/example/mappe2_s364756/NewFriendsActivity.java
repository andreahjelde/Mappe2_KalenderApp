package com.example.mappe2_s364756;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class NewFriendsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_friends);

        Button btn_saveNewFriend = findViewById(R.id.btn_saveNewFriend);

        btn_saveNewFriend.setOnClickListener(view -> {
            Intent i= new Intent(NewFriendsActivity.this, FriendsActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
        });
    };
}