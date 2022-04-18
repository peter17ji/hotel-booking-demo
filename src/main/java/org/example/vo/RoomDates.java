package org.example.vo;

import org.example.entities.Booking;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class RoomDates {
    private final int roomId;
    private final ConcurrentHashMap<Date, Booking> bookings;

    public RoomDates(int roomId) {
        this.roomId = roomId;
        this.bookings = new ConcurrentHashMap<>();
    }

    public int getRoomId() {
        return roomId;
    }

    public Booking getBookingByDate(Date date) {
        return bookings.get(date);
    }

    public List<Booking> getBookingByConsumerName(String consumerName) {
        Map<String, List<Booking>> bookingsByConsumerName = bookings.values().stream().collect(Collectors.groupingBy(Booking::getConsumerName));
        return bookingsByConsumerName.get(consumerName);
    }


     public void addBookingWithoutCheck(Booking newBooking) {
        bookings.put(newBooking.getDate(), newBooking);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("RoomDates{");
        sb.append("roomId=").append(roomId);
        sb.append(",\n bookings=");
        bookings.values().stream().map(Booking::toString).forEach(str->sb.append(str).append(','));
        sb.deleteCharAt(sb.length() -1);
        sb.append('}');
        return sb.toString();
    }
}
