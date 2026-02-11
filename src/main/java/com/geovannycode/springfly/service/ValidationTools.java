package com.geovannycode.springfly.service;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Service;

import com.geovannycode.springfly.model.BookingClass;
import com.geovannycode.springfly.model.ValidationResult;

import java.time.LocalDate;

@Service
public class ValidationTools {

    private final BookingService bookingService;

    public ValidationTools(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @Tool(description = """
        Validates if a booking change is allowed based on company policies.
        Checks: booking exists, passenger match, cancellation status, date validity.
        Returns validation result with error message if invalid.
        """)
    public ValidationResult validateBookingChange(String bookingNumber, String firstName, String lastName) {
        try {
            bookingService.getBookingDetails(bookingNumber, firstName, lastName);
            return ValidationResult.valid();
        } catch (Exception e) {
            return ValidationResult.invalid(e.getMessage());
        }
    }

    @Tool(description = """
        Validates if a new flight date is acceptable.
        Checks: date is not in the past, at least 24 hours from now.
        Returns validation result with error message if invalid.
        """)
    public ValidationResult validateNewDate(String newDate) {
        try {
            LocalDate parsedDate = LocalDate.parse(newDate);
            if (parsedDate.isBefore(LocalDate.now())) {
                return ValidationResult.invalid("Cannot book flights in the past");
            }
            if (parsedDate.isBefore(LocalDate.now().plusDays(1))) {
                return ValidationResult.invalid("Flights must be booked at least 24 hours in advance");
            }
            return ValidationResult.valid();
        } catch (Exception e) {
            return ValidationResult.invalid("Invalid date format. Use YYYY-MM-DD");
        }
    }

    @Tool(description = """
        Validates if a route change (from/to airports) is valid.
        Checks: origin and destination are different, valid airport codes.
        Returns validation result with error message if invalid.
        """)
    public ValidationResult validateRoute(String from, String to) {
        if (from == null || to == null || from.isBlank() || to.isBlank()) {
            return ValidationResult.invalid("Airport codes cannot be empty");
        }

        if (from.equalsIgnoreCase(to)) {
            return ValidationResult.invalid("Origin and destination must be different");
        }

        if (from.length() != 3 || to.length() != 3) {
            return ValidationResult.invalid("Airport codes must be 3 letters (e.g., JFK, LAX)");
        }

        return ValidationResult.valid();
    }

    @Tool(description = """
        Calculates the change fee based on booking class.
        Economy: $150, Premium Economy: $100, Business: $50, First Class: Free.
        Returns the fee amount as a string.
        """)
    public String getChangeFee(String bookingClass) {
        try {
            BookingClass bc = BookingClass.valueOf(bookingClass.toUpperCase().replace(" ", "_"));
            return switch (bc) {
                case ECONOMY -> "$150";
                case PREMIUM_ECONOMY -> "$100";
                case BUSINESS -> "$50";
                case FIRST_CLASS -> "Free";
            };
        } catch (Exception e) {
            return "Unable to determine fee - invalid booking class";
        }
    }

    @Tool(description = """
        Gets the cancellation policy based on booking class.
        Returns policy details as a formatted string.
        """)
    public String getCancellationPolicy(String bookingClass) {
        try {
            BookingClass bc = BookingClass.valueOf(bookingClass.toUpperCase().replace(" ", "_"));
            return switch (bc) {
                case ECONOMY -> "Cancellation fee: $200. Must cancel 48+ hours before departure.";
                case PREMIUM_ECONOMY -> "Cancellation fee: $100. Must cancel 48+ hours before departure.";
                case BUSINESS -> "Cancellation fee: $50. Must cancel 24+ hours before departure.";
                case FIRST_CLASS -> "Free cancellation up to 24 hours before departure.";
            };
        } catch (Exception e) {
            return "Unable to determine policy - invalid booking class";
        }
    }
}
