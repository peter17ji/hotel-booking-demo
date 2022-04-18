package org.example;

import org.example.entities.Booking;
import org.example.exceptions.DateAlreadyBookedException;
import org.example.utils.DateUtils;
import org.example.vo.RoomDates;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Hotel {

    /**
     * key=roomId,value=RoomDates
     */
    private final ConcurrentHashMap<Integer, RoomDates> roomAvailability;
    /**
     * Room count for internal use.
     * <b>Starts at 0.</b>
     */
    private final Integer roomCount;

    /**
     * Instantiate a hotel w/ given room count.
     *
     * @param roomCount how many room is in the hotel. <b>Starts at 1.</b>
     */
    protected Hotel(Integer roomCount) {
        if (roomCount < 1) {
            throw new IllegalArgumentException("Room count starts at 1");
        }
        this.roomCount = roomCount - 1;
        roomAvailability = new ConcurrentHashMap<>(roomCount);
    }

    protected void printDebugString() {
        System.out.println("Hotel w/ " + (roomCount + 1) + " rooms");
        List<String> strings = roomAvailability.values().stream().map(RoomDates::toString).collect(Collectors.toList());
        for (String str : strings) {
            System.out.println(str);
        }
    }

    /**
     * Turn datetime into just date and try to save
     */
    public void addBooking(String consumerName, int roomId, Date date) {
        // Timezone option reserved for further dev
        date = DateUtils.eraseDateRange(date, TimeZone.getTimeZone("Asia/Shanghai"), Calendar.HOUR_OF_DAY);
        Booking newBooking = new Booking(consumerName, roomId, date);
        addBooking(newBooking);
    }

    /**
     * Date doesn't get preprocess if inside Booking object
     *
     * @param newBooking Booking to save
     */
    public void addBooking(Booking newBooking) {
        int roomId = newBooking.getRoomId();
        RoomDates dates4Room = roomAvailability.get(roomId);
        if (dates4Room == null) {
            dates4Room = new RoomDates(roomId);
            dates4Room.addBookingWithoutCheck(newBooking);
            roomAvailability.put(roomId, dates4Room);
            return;
        }
        Date date = newBooking.getDate();
        if (dates4Room.getBookingByDate(date) != null) {
            throw new DateAlreadyBookedException("Date already booked");
        }
        dates4Room.addBookingWithoutCheck(newBooking);
    }

    /**
     * Find booking record by consumer's full name
     * Time-consuming process as transforming data structure is needed
     *
     * @param consumerName Consumer's full name
     * @return result list
     */
    public List<Booking> findBooking(String consumerName) {
        return roomAvailability.values().parallelStream()
                .map(roomDates -> roomDates.getBookingByConsumerName(consumerName))
                .filter(Objects::nonNull).flatMap(Collection::stream).collect(Collectors.toList());
    }

    public List<Integer> getVacantRooms(Date date) {
        List<Integer> allRoomIds = Stream.iterate(0, (a) -> a + 1)
                .limit(roomCount + 1).collect(Collectors.toList());
        Set<Integer> roomsNeverBooked = new HashSet<>(allRoomIds);
        roomsNeverBooked.removeAll(roomAvailability.keySet());
        List<Integer> vacantRooms = allRoomIds.stream()
                .filter(roomId -> !roomsNeverBooked.contains(roomId))
                .filter(roomId -> {
                    RoomDates roomDates = roomAvailability.get(roomId);
                    return roomDates.getBookingByDate(date) == null;
                }).collect(Collectors.toList());
        vacantRooms.addAll(roomsNeverBooked);
        return vacantRooms;
    }

}
