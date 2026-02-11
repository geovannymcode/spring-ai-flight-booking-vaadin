package com.geovannycode.springfly.model;

import java.time.LocalDate;

public record BookingSnapshot(
    String bookingNumber,
    LocalDate originalDate,
    String originalFrom,
    String originalTo,
    LocalDate newDate,
    String newFrom,
    String newTo
) {
}
