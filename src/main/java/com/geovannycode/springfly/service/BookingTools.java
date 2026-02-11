package com.geovannycode.springfly.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Service;

import com.geovannycode.springfly.model.BookingDetails;

@Service
public class BookingTools {
private static final Logger log = LoggerFactory.getLogger(BookingTools.class);
    private final BookingService bookingService;

    public BookingTools(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @Tool(description = """
        Retrieve booking details for a customer. Returns flight information including date,
        origin, destination, seat number, booking class, and status.
        Use this tool first before any booking modifications to verify booking exists.
        """)
    public BookingDetails getBookingDetails(String bookingNumber, String firstName, String lastName) {
        log.info("AI Agent calling getBookingDetails for: {}", bookingNumber);
        try {
            return bookingService.getBookingDetails(bookingNumber, firstName, lastName);
        } catch (Exception e) {
            log.error("Error getting booking details: {}", e.getMessage());
            return new BookingDetails(
                bookingNumber, firstName, lastName,
                null, null, null, null, null,
                "ERROR: " + e.getMessage()
            );
        }
    }

    @Tool(description = """
        Change ONLY the flight date for an existing booking.
        Use this when the customer wants to fly on a different date but keep the same route.
        The customer should be informed of the change fee based on their booking class before calling this tool.
        Returns confirmation with the updated booking details.
        Date format must be: YYYY-MM-DD (e.g., 2025-03-15).
        """)
    public String changeFlightDate(String bookingNumber, String firstName, String lastName, String newDate) {
        log.info("AI Agent calling changeFlightDate for booking {} to {}", bookingNumber, newDate);
        try {
            bookingService.changeFlightDate(bookingNumber, firstName, lastName, newDate);
            BookingDetails updated = bookingService.getBookingDetails(bookingNumber, firstName, lastName);
            return String.format(
                "SUCCESS: Flight date changed to %s. Updated booking: %s -> %s on %s, Status: %s",
                newDate, updated.from(), updated.to(), updated.date(), updated.bookingStatus()
            );
        } catch (Exception e) {
            log.error("Error changing flight date: {}", e.getMessage());
            return "FAILED: Could not change flight date. Reason: " + e.getMessage();
        }
    }

    @Tool(description = """
        Change ONLY the flight route (origin and/or destination) for an existing booking.
        Use this when the customer wants to change where they are flying from or to, but keep the same date.
        The customer should be informed of the change fee based on their booking class before calling this tool.
        Returns confirmation with the updated booking details.
        Airport codes must be 3 letters (e.g., JFK, LAX, ORD).
        """)
    public String changeFlightRoute(String bookingNumber, String firstName, String lastName,
                                   String from, String to) {
        log.info("AI Agent calling changeFlightRoute for booking {} to {} -> {}", bookingNumber, from, to);
        try {
            bookingService.changeFlightRoute(bookingNumber, firstName, lastName, from, to);
            BookingDetails updated = bookingService.getBookingDetails(bookingNumber, firstName, lastName);
            return String.format(
                "SUCCESS: Flight route changed to %s -> %s. Updated booking: %s on %s, Status: %s",
                from, to, updated.from() + " -> " + updated.to(), updated.date(), updated.bookingStatus()
            );
        } catch (Exception e) {
            log.error("Error changing flight route: {}", e.getMessage());
            return "FAILED: Could not change flight route. Reason: " + e.getMessage();
        }
    }

    @Tool(description = """
        Change both the flight date AND route for an existing booking.
        Use this ONLY when the customer explicitly wants to change both the date and the route.
        The customer should be informed of the change fee based on their booking class before calling this tool.
        Returns confirmation with the updated booking details.
        Date format: YYYY-MM-DD, Airport codes: 3 letters (e.g., JFK).
        """)
    public String changeBooking(String bookingNumber, String firstName, String lastName,
                               String newDate, String from, String to) {
        log.info("AI Agent calling changeBooking for {} to {} -> {} on {}", bookingNumber, from, to, newDate);
        try {
            bookingService.changeBooking(bookingNumber, firstName, lastName, newDate, from, to);
            BookingDetails updated = bookingService.getBookingDetails(bookingNumber, firstName, lastName);
            return String.format(
                "SUCCESS: Booking changed. New flight: %s -> %s on %s, Status: %s",
                updated.from(), updated.to(), updated.date(), updated.bookingStatus()
            );
        } catch (Exception e) {
            log.error("Error changing booking: {}", e.getMessage());
            return "FAILED: Could not change booking. Reason: " + e.getMessage();
        }
    }

    @Tool(description = """
        Cancel an existing booking.
        The customer should be informed of the cancellation fee based on their booking class
        and must confirm before calling this tool.
        Cancellations must be made at least 48 hours before departure.
        Returns confirmation of the cancellation.
        """)
    public String cancelBooking(String bookingNumber, String firstName, String lastName) {
        log.info("AI Agent calling cancelBooking for: {}", bookingNumber);
        try {
            bookingService.cancelBooking(bookingNumber, firstName, lastName);
            return String.format("SUCCESS: Booking %s has been cancelled for %s %s.",
                bookingNumber, firstName, lastName);
        } catch (Exception e) {
            log.error("Error cancelling booking: {}", e.getMessage());
            return "FAILED: Could not cancel booking. Reason: " + e.getMessage();
        }
    }
}
