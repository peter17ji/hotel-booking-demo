package org.example;

import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

public class HotelUtils {
    /**
     * Room count for internal use.
     * <b>Starts at 0.</b>
     */
    private Integer roomCount;
    /**
     * key=roomId,value=TODO
     */
    private ConcurrentHashMap<Integer, Integer> roomAvailability = new ConcurrentHashMap<Integer, Integer>(roomCount);

    /**
     * Instantiate a hotel w/ given room count.
     *
     * @param roomCount how many room is in the hotel. <b>Starts at 1.</b>
     */
    protected HotelUtils(Integer roomCount) {
        if (roomCount < 1) {
            throw new IllegalArgumentException("Room count starts at 1");
        }
        this.roomCount = roomCount - 1;
    }

    public void addBooking(String consumerName, Integer roomId, Date date) {

    }

}
