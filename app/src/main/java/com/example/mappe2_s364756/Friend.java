package com.example.mappe2_s364756;

import androidx.annotation.NonNull;

public class Friend {
    private String nameFriend;
    private String phoneFriend;
    private long id;

    @NonNull
    @Override
    public String toString() {
        return  nameFriend +  "     Tlf: " + phoneFriend ;
    }

    public Friend(){}

    public String getNameFriend() {
        return nameFriend;
    }

    public String getPhoneFriend() {
        return phoneFriend;
    }

    public long getId() {
        return id;
    }

    public void setNameFriend(String nameFriend) {
        this.nameFriend = nameFriend;
    }

    public void setPhoneFriend(String phoneFriend) {
        this.phoneFriend = phoneFriend;
    }

    public void setId(long id) {
        this.id = id;
    }
}
