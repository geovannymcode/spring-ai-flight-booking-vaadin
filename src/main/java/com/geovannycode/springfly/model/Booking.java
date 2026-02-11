package com.geovannycode.springfly.model;

import java.time.LocalDate;
import java.util.Objects;

public class Booking {
private String bookingNumber;
    private LocalDate date;
    private LocalDate bookingTo;
    private Passenger passenger;
    private String from;
    private String to;
    private BookingStatus bookingStatus;
    private String seatNumber;
    private BookingClass bookingClass;

    public Booking() {
    }

    public Booking(String bookingNumber, LocalDate date, LocalDate bookingTo,
                   Passenger passenger, String from, String to,
                   BookingStatus bookingStatus, String seatNumber,
                   BookingClass bookingClass) {
        this.bookingNumber = bookingNumber;
        this.date = date;
        this.bookingTo = bookingTo;
        this.passenger = passenger;
        this.from = from;
        this.to = to;
        this.bookingStatus = bookingStatus;
        this.seatNumber = seatNumber;
        this.bookingClass = bookingClass;
    }

    // Getters and Setters
    public String getBookingNumber() {
        return bookingNumber;
    }

    public void setBookingNumber(String bookingNumber) {
        this.bookingNumber = bookingNumber;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalDate getBookingTo() {
        return bookingTo;
    }

    public void setBookingTo(LocalDate bookingTo) {
        this.bookingTo = bookingTo;
    }

    public Passenger getPassenger() {
        return passenger;
    }

    public void setPassenger(Passenger passenger) {
        this.passenger = passenger;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public BookingStatus getBookingStatus() {
        return bookingStatus;
    }

    public void setBookingStatus(BookingStatus bookingStatus) {
        this.bookingStatus = bookingStatus;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    public BookingClass getBookingClass() {
        return bookingClass;
    }

    public void setBookingClass(BookingClass bookingClass) {
        this.bookingClass = bookingClass;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Booking booking = (Booking) o;
        return Objects.equals(bookingNumber, booking.bookingNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bookingNumber);
    }

    @Override
    public String toString() {
        return "Booking{" +
               "bookingNumber='" + bookingNumber + '\'' +
               ", date=" + date +
               ", passenger=" + passenger +
               ", from='" + from + '\'' +
               ", to='" + to + '\'' +
               ", status=" + bookingStatus +
               ", class=" + bookingClass +
               '}';
    }
}
