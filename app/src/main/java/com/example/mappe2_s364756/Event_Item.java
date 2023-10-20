package com.example.mappe2_s364756;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;

public class Event_Item {
    private String nameEvent;
    private String dateEvent;
    private String timeEvent;
    private String placeEvent;
    private long id;

    @NonNull
    @Override
    public String toString() {
        return "Avtale: " + nameEvent + "           Sted: " + placeEvent;
    }

    public Event_Item(){
    }

    public String getNameEvent() {
        return nameEvent;
    }

    public String getDateEvent() {
        return dateEvent;
    }

    public String getTimeEvent() {
        return timeEvent;
    }

    public String getPlaceEvent() {
        return placeEvent;
    }

    public long getId() {
        return id;
    }

    public void setNameEvent(String nameEvent) {
        this.nameEvent = nameEvent;
    }

    public void setDateEvent(String dateEvent) {
        this.dateEvent = dateEvent;
    }

    public void setTimeEvent(String timeEvent) {
        this.timeEvent = timeEvent;
    }

    public void setPlaceEvent(String placeEvent) {
        this.placeEvent = placeEvent;
    }

    public void setId(long id) {
        this.id = id;
    }
}
