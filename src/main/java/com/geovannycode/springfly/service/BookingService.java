package com.geovannycode.springfly.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.geovannycode.springfly.model.Booking;
import com.geovannycode.springfly.model.BookingDetails;
import com.geovannycode.springfly.model.BookingSnapshot;
import com.geovannycode.springfly.model.BookingStatus;
import com.geovannycode.springfly.model.Passenger;
import com.geovannycode.springfly.model.SpringFlyDB;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class BookingService {

    private static final Logger log = LoggerFactory.getLogger(BookingService.class);
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE;

    public List<Booking> getAllBookings() {
        log.info("Retrieving all bookings");
        return SpringFlyDB.getAllBookings();
    }

    public BookingDetails getBookingDetails(String bookingNumber, String firstName, String lastName) {
        log.info("Getting booking details for: {}, passenger: {} {}", bookingNumber, firstName, lastName);

        Booking booking = SpringFlyDB.getBooking(bookingNumber);
        if (booking == null) {
            throw new IllegalArgumentException("Booking not found: " + bookingNumber);
        }

        Passenger passenger = booking.getPassenger();
        if (!passenger.firstName().equalsIgnoreCase(firstName) ||
            !passenger.lastName().equalsIgnoreCase(lastName)) {
            throw new IllegalArgumentException("Passenger name does not match booking");
        }

        return new BookingDetails(
            booking.getBookingNumber(),
            passenger.firstName(),
            passenger.lastName(),
            booking.getFrom(),
            booking.getTo(),
            booking.getDate().toString(),
            booking.getSeatNumber(),
            booking.getBookingClass().toString(),
            booking.getBookingStatus().toString()
        );
    }

    public void changeFlightDate(String bookingNumber, String firstName, String lastName, String newDate) {
        log.info("Changing flight date for booking {} to {}", bookingNumber, newDate);

        Booking booking = getValidatedBooking(bookingNumber, firstName, lastName);
        LocalDate parsedDate = LocalDate.parse(newDate, DATE_FORMATTER);

        if (parsedDate.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Cannot change to a past date");
        }

        booking.setDate(parsedDate);
        booking.setBookingStatus(BookingStatus.MODIFIED);
        SpringFlyDB.updateBooking(booking);

        log.info("Flight date changed successfully for booking {}", bookingNumber);
    }

    public void changeFlightRoute(String bookingNumber, String firstName, String lastName, String from, String to) {
        log.info("Changing flight route for booking {} to {} -> {}", bookingNumber, from, to);

        Booking booking = getValidatedBooking(bookingNumber, firstName, lastName);

        if (from.equalsIgnoreCase(to)) {
            throw new IllegalArgumentException("Origin and destination cannot be the same");
        }

        booking.setFrom(from);
        booking.setTo(to);
        booking.setBookingStatus(BookingStatus.MODIFIED);
        SpringFlyDB.updateBooking(booking);

        log.info("Flight route changed successfully for booking {}", bookingNumber);
    }

    private Booking getValidatedBooking(String bookingNumber, String firstName, String lastName) {
        Booking booking = SpringFlyDB.getBooking(bookingNumber);

        if (booking == null) {
            throw new IllegalArgumentException("Booking not found: " + bookingNumber);
        }

        Passenger passenger = booking.getPassenger();
        if (!passenger.firstName().equalsIgnoreCase(firstName) ||
            !passenger.lastName().equalsIgnoreCase(lastName)) {
            throw new IllegalArgumentException("Passenger name does not match booking");
        }

        if (booking.getBookingStatus() == BookingStatus.CANCELLED) {
            throw new IllegalArgumentException("Cannot modify cancelled booking");
        }

        return booking;
    }

    public void cancelBooking(String bookingNumber, String firstName, String lastName) {
        log.info("Cancelling booking {}", bookingNumber);

        Booking booking = getValidatedBooking(bookingNumber, firstName, lastName);

        if (booking.getDate().isBefore(LocalDate.now().plusDays(2))) {
            throw new IllegalArgumentException(
                "Cannot cancel booking - must be done at least 48 hours before departure"
            );
        }

        booking.setBookingStatus(BookingStatus.CANCELLED);
        SpringFlyDB.updateBooking(booking);

        log.info("Booking {} cancelled successfully", bookingNumber);
    }

    public void changeBooking(String bookingNumber, String firstName, String lastName,
                            String newDate, String from, String to) {
        log.info("Changing booking {} to {} -> {} on {}", bookingNumber, from, to, newDate);

        Booking booking = getValidatedBooking(bookingNumber, firstName, lastName);
        LocalDate parsedDate = LocalDate.parse(newDate, DATE_FORMATTER);

        if (parsedDate.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Cannot change to a past date");
        }

        if (from.equalsIgnoreCase(to)) {
            throw new IllegalArgumentException("Origin and destination cannot be the same");
        }

        booking.setDate(parsedDate);
        booking.setFrom(from);
        booking.setTo(to);
        booking.setBookingStatus(BookingStatus.MODIFIED);
        SpringFlyDB.updateBooking(booking);

        log.info("Booking changed successfully for {}", bookingNumber);
    }

     public BookingSnapshot createSnapshot(Booking booking, LocalDate newDate, String newFrom, String newTo) {
        return new BookingSnapshot(
            booking.getBookingNumber(),
            booking.getDate(),
            booking.getFrom(),
            booking.getTo(),
            newDate != null ? newDate : booking.getDate(),
            newFrom != null ? newFrom : booking.getFrom(),
            newTo != null ? newTo : booking.getTo()
        );
    }
}
