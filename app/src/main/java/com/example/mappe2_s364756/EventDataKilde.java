package com.example.mappe2_s364756;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class EventDataKilde {
    private SQLiteDatabase database;
    final DatabaseHjelper dbHelper;

    public EventDataKilde(Context context) {
        dbHelper = new DatabaseHjelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Event_Item addNewEvent(String eventName, String eventDate, String eventTime, String eventPlace) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHjelper.KOLONNE_EVENT_NAME, eventName);
        values.put(DatabaseHjelper.KOLONNE_EVENT_DATE, eventDate);
        values.put(DatabaseHjelper.KOLONNE_EVENT_TIME, eventTime);
        values.put(DatabaseHjelper.KOLONNE_EVENT_PLACE, eventPlace);

        long insertId = database.insert(DatabaseHjelper.TABELL_EVENTS, null,
                values);
        Cursor cursor = database.query(DatabaseHjelper.TABELL_EVENTS, null,
                DatabaseHjelper.KOLONNE_ID + " = " + insertId, null, null, null, null);
        cursor.moveToFirst();
        Event_Item newEvent = cursorToEvent(cursor);
        cursor.close();
        return newEvent;
    }

    private Event_Item cursorToEvent(Cursor cursor) {
        Event_Item eventItem = new Event_Item();

        eventItem.setId(cursor.getLong(cursor.getColumnIndexOrThrow(DatabaseHjelper.KOLONNE_ID)));
        eventItem.setNameEvent(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHjelper.KOLONNE_EVENT_NAME)));
        eventItem.setDateEvent(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHjelper.KOLONNE_EVENT_DATE)));
        eventItem.setTimeEvent(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHjelper.KOLONNE_EVENT_TIME)));
        eventItem.setPlaceEvent(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHjelper.KOLONNE_EVENT_PLACE)));
        return eventItem;
    }

    public List<Event_Item> findAllEvents() {
        List<Event_Item> eventItemList = new ArrayList<>();
        Cursor cursor = database.query(DatabaseHjelper.TABELL_EVENTS,
                null, null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Event_Item eventItem = cursorToEvent(cursor);
            eventItemList.add(eventItem);
            cursor.moveToNext();
        }
        cursor.close();
        return eventItemList;
    }

    public void deleteEventItem(long eventId) {
        database.delete(DatabaseHjelper.TABELL_EVENTS,
                DatabaseHjelper.KOLONNE_ID + " =? ",
                new String[]{Long.toString(eventId)});
    }



}



