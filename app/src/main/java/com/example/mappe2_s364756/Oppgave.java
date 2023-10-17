package com.example.mappe2_s364756;

import androidx.annotation.NonNull;

public class Oppgave {
    private String nameEvent;
    private String dateEvent;
    private String timeEvent;
    private String placeEvent;
    private long id;

    @NonNull
    @Override
    public String toString() {
        return "Avtale: " + nameEvent + "\n" + "Dato: " + dateEvent ;
    }

    public Oppgave(String nameEvent, String dateEvent, String timeEvent, String placeEvent)
    {
        this.nameEvent = nameEvent;
        this.dateEvent = dateEvent;
        this.timeEvent = timeEvent;
        this.placeEvent = placeEvent;
        this.id = id;
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

    public long getId(){ return id;}

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
