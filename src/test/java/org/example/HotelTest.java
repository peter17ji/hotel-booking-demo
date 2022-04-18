package org.example;

import org.example.entities.Booking;
import org.example.exceptions.DateAlreadyBookedException;
import org.example.utils.DateUtils;
import org.junit.*;
import org.junit.runners.MethodSorters;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class HotelTest {

    private static final Hotel hotel= new Hotel(16);

    @Test
    public void addBooking() {
        Calendar calendar = Calendar.getInstance();
        DateUtils.eraseDateRange(calendar, Calendar.HOUR);
        hotel.addBooking("Alice", 1, new Date());

        Date date = calendar.getTime();
        Booking booking4Charlie = new Booking("Charlie", 3, date);
        hotel.addBooking(booking4Charlie);

        calendar.add(Calendar.DAY_OF_MONTH, 1);
        hotel.addBooking("Charlie", 3, calendar.getTime());

        hotel.printDebugString();
    }

    @Test(expected = DateAlreadyBookedException.class)
    public void addBookingFailed() {
        hotel.addBooking("Bob", 1, new Date());
    }

    @Test
    public void findBooking() {
        List<Booking> charlie = hotel.findBooking("Charlie");
        Assert.assertEquals(2, charlie.size());
        for (Booking booking : charlie) {
            System.out.println(booking.toString());
        }
    }

    @Test
    public void getVacantRooms() {
        Calendar calendar = Calendar.getInstance();
        DateUtils.eraseDateRange(calendar, Calendar.HOUR);
        Date date = calendar.getTime();

        List<Integer> vacantRoomIds = hotel.getVacantRooms(date);
        Assert.assertEquals(14, vacantRoomIds.size());
        System.out.println("Vacant rooms on " + date + ":");
        String collect = vacantRoomIds.stream().map(String::valueOf).collect(Collectors.joining(","));
        System.out.println(collect);

        calendar.add(Calendar.DAY_OF_MONTH, 1);
        Date date2 = calendar.getTime();

        List<Integer> vacantRoomIds2 = hotel.getVacantRooms(date2);
        Assert.assertEquals(15, vacantRoomIds2.size());

        System.out.println("Vacant rooms on " + date2 + ":");
        String collect2 = vacantRoomIds2.stream().map(String::valueOf).collect(Collectors.joining(","));
        System.out.println(collect2);
    }
}