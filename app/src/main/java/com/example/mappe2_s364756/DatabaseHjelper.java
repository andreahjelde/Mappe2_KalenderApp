package com.example.mappe2_s364756;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHjelper extends SQLiteOpenHelper {
        private static final String DATABASE_NAVN = "events.db";
        private static final int DATABASE_VERSION = 1;

        //Tabeller
        public static final String TABELL_EVENTS = "events";
        public static final String TABELL_FRIENDS = "friends";

        //Kolonner for tabell "events"
        public static final String KOLONNE_EVENT_ID = "idEvent";
        public static final String KOLONNE_EVENT_NAME = "nameEvent";
        public static final String KOLONNE_EVENT_DATE = "dateEvent";
        public static final String KOLONNE_EVENT_TIME = "timeEvent";
        public static final String KOLONNE_EVENT_PLACE = "placeEvent";
        public static final String KOLONNE_EVENT_FRIEND_ID = "friendId";

        //Kolonner for tabell "friends"
        public static final String KOLONNE_FRIEND_ID = "idFriend";
        public static final String KOLONNE_FRIEND_NAME = "nameFriend";
        public static final String KOLONNE_FRIEND_PHONE = "phoneFriend";

    // Definer utenlandsk nøkkel for å referere til vennetabellen
    private static final String FOREIGN_KEY_FRIEND = "FOREIGN KEY (" + KOLONNE_EVENT_FRIEND_ID + ") REFERENCES " + TABELL_FRIENDS + " (" + KOLONNE_FRIEND_ID + ")";
    private static final String CREATE_TABLE_FRIEND = "CREATE TABLE " +
            TABELL_FRIENDS + "(" +
            KOLONNE_FRIEND_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            KOLONNE_FRIEND_NAME + " TEXT NOT NULL, " +
            KOLONNE_FRIEND_PHONE + " TEXT)" ;
    private static final String CREATE_TABLE_TASKS = "CREATE TABLE " +
            TABELL_EVENTS + "(" +
            KOLONNE_EVENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            KOLONNE_EVENT_NAME + " TEXT NOT NULL, " +
            KOLONNE_EVENT_DATE + " TEXT, " +
            KOLONNE_EVENT_TIME + " TEXT, " +
            KOLONNE_EVENT_PLACE + " TEXT, " +
            KOLONNE_EVENT_FRIEND_ID + " INTEGER, " + // Legg til kolonnen for utenlandsk nøkkel
            FOREIGN_KEY_FRIEND + ")";


    public DatabaseHjelper(Context context) {
        super(context, DATABASE_NAVN, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_TASKS );
        db.execSQL(CREATE_TABLE_FRIEND);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }
}


