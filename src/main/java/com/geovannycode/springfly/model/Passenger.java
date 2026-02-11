package com.geovannycode.springfly.model;

import java.util.Objects;

public record Passenger(
    String firstName,
    String lastName,
    String email,
    String phoneNumber
) {
    public Passenger {
        Objects.requireNonNull(firstName, "First name cannot be null");
        Objects.requireNonNull(lastName, "Last name cannot be null");
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }
}
