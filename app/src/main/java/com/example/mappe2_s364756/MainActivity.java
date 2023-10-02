package com.example.mappe2_s364756;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements MinDialog.MittInterface{
    @Override
    public void onYesClick() {
        finish();
    }
    @Override
    public void onNoClick() {
        return;
    }

    public void visDialog(View v)
    {
        DialogFragment dialog = new MinDialog();
        dialog.show(getSupportFragmentManager(),"Tittel");

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn_friends = findViewById(R.id.btn_friends);
        Button btn_preferences = findViewById(R.id.btn_preferences);
        Button btn_newEvent = findViewById(R.id.btn_newEvent);

        btn_newEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                visDialog(view);
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