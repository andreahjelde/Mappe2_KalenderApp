package com.example.mappe2_s364756;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHjelper extends SQLiteOpenHelper {
        private static final String DATABASE_NAVN = "events1.db";
        private static final int DATABASE_VERSION = 1;
        public static final String TABELL_EVENTS = "events";
        public static final String KOLONNE_ID = "id";
        public static final String KOLONNE_EVENT_NAME = "nameEvent";
        public static final String KOLONNE_EVENT_DATE = "dateEvent";
        public static final String KOLONNE_EVENT_TIME = "timeEvent";
        public static final String KOLONNE_EVENT_PLACE = "placeEvent";


    private static final String CREATE_TABLE_TASKS = "CREATE TABLE " +
            TABELL_EVENTS +
            "(" + KOLONNE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            KOLONNE_EVENT_NAME + " TEXT NOT NULL," + KOLONNE_EVENT_DATE + " TEXT NOT NULL," +
            KOLONNE_EVENT_TIME + " TEXT NOT NULL," + KOLONNE_EVENT_PLACE +"TEXT NOT NULL)";
    public DatabaseHjelper(Context context) {
        super(context, DATABASE_NAVN, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_TASKS);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    { onCreate(db);
    }
}


