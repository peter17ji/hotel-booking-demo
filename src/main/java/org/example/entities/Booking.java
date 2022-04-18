package org.example.entities;

import java.util.Date;


public class Booking {
    private final String consumerName;
    private final int roomId;
    private final Date date;

    public Booking(String consumerName, int roomId, Date date) {
        this.consumerName = consumerName;
        this.roomId = roomId;
        this.date = date;
    }

    @Override
    public String toString() {

        return "Booking{" +
                "consumerName='" + consumerName + '\'' +
                ", roomId=" + roomId +
                ", date=" + date.toString() +
                '}';
    }

    public String getConsumerName() {
        return consumerName;
    }

    public int getRoomId() {
        return roomId;
    }

    public Date getDate() {
        return date;
    }
}
