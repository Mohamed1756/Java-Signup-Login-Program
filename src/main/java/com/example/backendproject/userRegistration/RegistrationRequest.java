package com.example.backendproject.userRegistration;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

//Bean class
@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString

public class RegistrationRequest {

    private final String firstName;
    private final String lastName;
    private final String password;
    private final String email;
}
