package com.example.mappe2_s364756;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AlertDialog;
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


        //------------------Dialogboks for å slette venn når man trykker på et element fra listen
        friendListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Friend selectedItem = (Friend) parent.getItemAtPosition(position);

                AlertDialog.Builder builder = new AlertDialog.Builder(FriendsActivity.this);
                builder.setTitle("Bekreft sletting");
                builder.setMessage("Er du sikker på at du vil slette denne vennen " + selectedItem.getNameFriend() + "?");
                builder.setPositiveButton("Ja", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dataKilde.deleteFriend(selectedItem.getIdFriend());
                        friendArrayAdapter.remove(selectedItem); // Fjern elementet fra adapteren
                        friendArrayAdapter.notifyDataSetChanged();
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
}