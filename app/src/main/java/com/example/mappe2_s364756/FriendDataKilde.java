package com.example.mappe2_s364756;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class FriendDataKilde {
    private SQLiteDatabase database;
    final DatabaseHjelper dbHelper;

    public FriendDataKilde (Context context) {
        dbHelper = new DatabaseHjelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Friend addNewFriend(String friendName, String friendPhone){
        ContentValues values = new ContentValues();
        values.put(DatabaseHjelper.KOLONNE_FRIEND_NAME, friendName);
        values.put(DatabaseHjelper.KOLONNE_FRIEND_PHONE, friendPhone);

        long insertId = database.insert(DatabaseHjelper.TABELL_FRIENDS, null, values);
        Cursor cursor = database.query(DatabaseHjelper.TABELL_FRIENDS, null, DatabaseHjelper.KOLONNE_FRIEND_ID + " = " + insertId, null, null, null, null);
        cursor.moveToFirst();
        Friend newFriend = cursorToFriend(cursor);
        cursor.close();
        return newFriend;
    }

    private Friend cursorToFriend(Cursor cursor){
        Friend friend = new Friend();
        friend.setIdFriend(cursor.getLong(cursor.getColumnIndexOrThrow(DatabaseHjelper.KOLONNE_FRIEND_ID)));
        friend.setNameFriend(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHjelper.KOLONNE_FRIEND_NAME)));
        friend.setPhoneFriend(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHjelper.KOLONNE_FRIEND_PHONE)));
        return friend;
    }

    public List<Friend> findAllFriends(){
        List<Friend> friendList = new ArrayList<>();
        Cursor cursor = database.query(DatabaseHjelper.TABELL_FRIENDS,
                null, null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            Friend friend = cursorToFriend(cursor);
            friendList.add(friend);
            cursor.moveToNext();
        }
        cursor.close();
        return friendList;
    }

    public void deleteFriend(long friendId){
        database.delete(DatabaseHjelper.TABELL_FRIENDS,
                DatabaseHjelper.KOLONNE_FRIEND_ID +" =? ",
                new String[]{Long.toString(friendId)});
    }
}
