package com.geovannycode.springfly.model;

public record BookingDetails(
    String bookingNumber,
    String firstName,
    String lastName,
    String from,
    String to,
    String date,
    String seatNumber,
    String bookingClass,
    String bookingStatus
) {
}
