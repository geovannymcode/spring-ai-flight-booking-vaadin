package com.geovannycode.springfly.service;

import java.time.LocalDate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;

import com.geovannycode.springfly.model.Booking;
import com.geovannycode.springfly.model.BookingClass;
import com.geovannycode.springfly.model.BookingStatus;
import com.geovannycode.springfly.model.Passenger;
import com.geovannycode.springfly.model.SpringFlyDB;

public class DataInitializationService implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(DataInitializationService.class);

    @Override
    public void run(String... args) {
        log.info("Initializing sample booking data...");

        // Sample passengers
        Passenger passenger1 = new Passenger(
            "Geovanny", "Mendoza",
            "geovanny.mendoza@email.com",
            "+1-555-0101"
        );

        Passenger passenger2 = new Passenger(
            "Omar", "Berroteran",
            "omar.berroteran@email.com",
            "+1-555-0102"
        );

        Passenger passenger3 = new Passenger(
            "Robert", "Johnson",
            "robert.j@email.com",
            "+1-555-0103"
        );

        Passenger passenger4 = new Passenger(
            "Maria", "Gomez",
            "maria.gomez@email.com",
            "+1-555-0104"
        );

        Passenger passenger5 = new Passenger(
            "Elena", "Aguirre",
            "elena.aguirre@email.com",
            "+1-555-0105"
        );

        // Sample bookings
        Booking booking1 = new Booking(
            "SF001",
            LocalDate.now().plusDays(15),
            LocalDate.now().plusDays(15),
            passenger1,
            "JFK",
            "LAX",
            BookingStatus.CONFIRMED,
            "12A",
            BookingClass.ECONOMY
        );

        Booking booking2 = new Booking(
            "SF002",
            LocalDate.now().plusDays(30),
            LocalDate.now().plusDays(30),
            passenger2,
            "ORD",
            "MIA",
            BookingStatus.CONFIRMED,
            "5B",
            BookingClass.BUSINESS
        );

        Booking booking3 = new Booking(
            "SF003",
            LocalDate.now().plusDays(7),
            LocalDate.now().plusDays(7),
            passenger3,
            "SFO",
            "SEA",
            BookingStatus.CONFIRMED,
            "22C",
            BookingClass.PREMIUM_ECONOMY
        );

        Booking booking4 = new Booking(
            "SF004",
            LocalDate.now().plusDays(45),
            LocalDate.now().plusDays(45),
            passenger4,
            "ATL",
            "DEN",
            BookingStatus.CONFIRMED,
            "1A",
            BookingClass.FIRST_CLASS
        );

        Booking booking5 = new Booking(
            "SF005",
            LocalDate.now().plusDays(20),
            LocalDate.now().plusDays(20),
            passenger5,
            "BOS",
            "PHX",
            BookingStatus.CONFIRMED,
            "18D",
            BookingClass.ECONOMY
        );

        Booking booking6 = new Booking(
            "SF006",
            LocalDate.now().plusDays(60),
            LocalDate.now().plusDays(60),
            passenger1,
            "LAX",
            "JFK",
            BookingStatus.CONFIRMED,
            "8F",
            BookingClass.BUSINESS
        );

        // Add bookings to database
        SpringFlyDB.addBooking(booking1);
        SpringFlyDB.addBooking(booking2);
        SpringFlyDB.addBooking(booking3);
        SpringFlyDB.addBooking(booking4);
        SpringFlyDB.addBooking(booking5);
        SpringFlyDB.addBooking(booking6);

        log.info("Successfully initialized {} sample bookings", SpringFlyDB.getAllBookings().size());
        log.info("Sample booking numbers: SF001, SF002, SF003, SF004, SF005, SF006");
        log.info("Test passenger: John Doe (SF001), Jane Smith (SF002)");
    }
}
