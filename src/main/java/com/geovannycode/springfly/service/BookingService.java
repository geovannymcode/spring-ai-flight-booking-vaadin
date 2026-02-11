package com.geovannycode.springfly.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.geovannycode.springfly.model.Booking;
import com.geovannycode.springfly.model.BookingDetails;
import com.geovannycode.springfly.model.BookingStatus;
import com.geovannycode.springfly.model.SpringFlyDB;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class BookingService {

    private static final Logger log = LoggerFactory.getLogger(BookingService.class);
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE;

    private final SpringFlyDB springFlyDB;

    public BookingService(SpringFlyDB springFlyDB) {
        this.springFlyDB = springFlyDB;
    }

    public List<BookingDetails> getAllBookings() {
        log.info("Retrieving all bookings");
        return springFlyDB.getBookings().stream()
                .map(BookingDetails::new)
                .toList();
    }

    public BookingDetails getBookingDetails(String bookingNumber, String firstName, String lastName) {
        log.info("Getting booking details for: {}, passenger: {} {}", bookingNumber, firstName, lastName);

        var booking = findBooking(bookingNumber, firstName, lastName);
        return new BookingDetails(booking);
    }

    public void changeFlightDate(String bookingNumber, String firstName, String lastName, String newDate) {
        log.info("Changing flight date for booking {} to {}", bookingNumber, newDate);

        var booking = findBooking(bookingNumber, firstName, lastName);

        if (booking.getBookingTo().isBefore(LocalDate.now().plusDays(1))) {
            throw new IllegalArgumentException("Booking cannot be changed within 24 hours of the start date.");
        }
        booking.setBookingTo(LocalDate.parse(newDate, DATE_FORMATTER));

        log.info("Flight date changed successfully for booking {}", bookingNumber);
    }

    public void changeFlightRoute(String bookingNumber, String firstName, String lastName, String from, String to) {
        log.info("Changing flight route for booking {} to {} -> {}", bookingNumber, from, to);

        var booking = findBooking(bookingNumber, firstName, lastName);
        if (booking.getBookingTo().isBefore(LocalDate.now().plusDays(1))) {
            throw new IllegalArgumentException("Booking cannot be changed within 24 hours of the start date.");
        }
        booking.setFrom(from);
        booking.setTo(to);

        log.info("Flight route changed successfully for booking {}", bookingNumber);
    }

    public void cancelBooking(String bookingNumber, String firstName, String lastName) {
        log.info("Cancelling booking {}", bookingNumber);

        var booking = findBooking(bookingNumber, firstName, lastName);
        if (booking.getBookingTo().isBefore(LocalDate.now().plusDays(2))) {
            throw new IllegalArgumentException("Booking cannot be cancelled within 48 hours of the start date.");
        }
        booking.setBookingStatus(BookingStatus.CANCELLED);

        log.info("Booking {} cancelled successfully", bookingNumber);
    }

    public void changeBooking(String bookingNumber, String firstName, String lastName,
            String newDate, String from, String to) {
        log.info("Changing booking {} to {} -> {} on {}", bookingNumber, from, to, newDate);

        var booking = findBooking(bookingNumber, firstName, lastName);
		if (booking.getBookingTo().isBefore(LocalDate.now().plusDays(1))) {
			throw new IllegalArgumentException("Booking cannot be changed within 24 hours of the start date.");
		}
		booking.setBookingTo(LocalDate.parse(newDate));
		booking.setFrom(from);
		booking.setTo(to);

        log.info("Booking changed successfully for {}", bookingNumber);
    }

    private Booking findBooking(String bookingNumber, String firstName, String lastName) {
        return springFlyDB.getBookings()
                .stream()
                .filter(b -> b.getBookingNumber().equalsIgnoreCase(bookingNumber))
                .filter(b -> b.getPassenger().firstName().equalsIgnoreCase(firstName))
                .filter(b -> b.getPassenger().lastName().equalsIgnoreCase(lastName))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Booking not found"));
    }

    public BookingDetails toBookingDetails(Booking booking) {
        return new BookingDetails(booking);
    }

}
