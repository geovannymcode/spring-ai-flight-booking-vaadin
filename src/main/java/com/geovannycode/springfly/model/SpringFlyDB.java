package com.geovannycode.springfly.model;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.List;

public class SpringFlyDB {
    private static final ConcurrentMap<String, Booking> bookings = new ConcurrentHashMap<>();

    public static void addBooking(Booking booking) {
        bookings.put(booking.getBookingNumber(), booking);
    }

    public static Booking getBooking(String bookingNumber) {
        return bookings.get(bookingNumber);
    }

    public static List<Booking> getAllBookings() {
        return List.copyOf(bookings.values());
    }

    public static void updateBooking(Booking booking) {
        bookings.put(booking.getBookingNumber(), booking);
    }

    public static void removeBooking(String bookingNumber) {
        bookings.remove(bookingNumber);
    }

    public static void clear() {
        bookings.clear();
    }
}
