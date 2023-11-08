package com.example.mappe2_s364756;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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

    public Event_Item addNewEvent(String eventName, String eventDate, String eventTime, String eventPlace, long eventFriendId) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHjelper.KOLONNE_EVENT_NAME, eventName);
        values.put(DatabaseHjelper.KOLONNE_EVENT_DATE, eventDate);
        values.put(DatabaseHjelper.KOLONNE_EVENT_TIME, eventTime);
        values.put(DatabaseHjelper.KOLONNE_EVENT_PLACE, eventPlace);
        values.put(DatabaseHjelper.KOLONNE_EVENT_FRIEND_ID, eventFriendId);

        long insertId = database.insert(DatabaseHjelper.TABELL_EVENTS, null,
                values);
        Cursor cursor = database.query(DatabaseHjelper.TABELL_EVENTS, null,
                DatabaseHjelper.KOLONNE_EVENT_ID + " = " + insertId, null, null, null, null);
        cursor.moveToFirst();
        Event_Item newEvent = cursorToEvent(cursor);
        cursor.close();
        return newEvent;
    }

    private Event_Item cursorToEvent(Cursor cursor) {
        Event_Item eventItem = new Event_Item();
        eventItem.setIdEvent(cursor.getLong(cursor.getColumnIndexOrThrow(DatabaseHjelper.KOLONNE_EVENT_ID)));
        eventItem.setNameEvent(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHjelper.KOLONNE_EVENT_NAME)));
        eventItem.setDateEvent(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHjelper.KOLONNE_EVENT_DATE)));
        eventItem.setTimeEvent(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHjelper.KOLONNE_EVENT_TIME)));
        eventItem.setPlaceEvent(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHjelper.KOLONNE_EVENT_PLACE)));
        eventItem.setIdEventFriend(cursor.getLong(cursor.getColumnIndexOrThrow(DatabaseHjelper.KOLONNE_EVENT_FRIEND_ID)));
        return eventItem;
    }

    public List<Event_Item> findAllEvents() {
        List<Event_Item> eventItemList = new ArrayList<>();
        Cursor cursor = database.query(DatabaseHjelper.TABELL_EVENTS,
                null,
                null, null, null, null, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            Event_Item eventItem = cursorToEvent(cursor);
            eventItemList.add(eventItem);
            cursor.moveToNext();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

            Comparator<Event_Item> eventComparator = new Comparator<Event_Item>() {
                @Override
                public int compare(Event_Item event1, Event_Item event2) {
                    LocalDate date1 = LocalDate.parse(event1.getDateEvent(), formatter);
                    LocalDate date2 = LocalDate.parse(event2.getDateEvent(), formatter);
                    return date1.compareTo(date2);

                }
            };

            Collections.sort(eventItemList, eventComparator);

        }
        cursor.close();
        return eventItemList;
    }


    public void deleteEventItem(long eventId) {
        database.delete(DatabaseHjelper.TABELL_EVENTS,
                DatabaseHjelper.KOLONNE_EVENT_ID + " =? ",
                new String[]{Long.toString(eventId)});
    }

}



