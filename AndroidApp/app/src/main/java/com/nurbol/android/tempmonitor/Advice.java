package com.nurbol.android.tempmonitor;

public class Advice {
    private long mId;
    private String mDescription;
    private String mRoom;
    private String mDate;

    public Advice(long id, String description, String room, String date) {
        mId = id;
        mDescription = description;
        mRoom = room;
        mDate = date;
    }

    public long getId() {
        return mId;
    }

    public String getDescription() {
        return mDescription;
    }

    public String getRoom() {
        return mRoom;
    }

    public String getDate() {
        return mDate;
    }
}
