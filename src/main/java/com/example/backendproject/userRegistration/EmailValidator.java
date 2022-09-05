package com.example.backendproject.userRegistration;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.function.Predicate;

@Service
@AllArgsConstructor
public class EmailValidator implements Predicate<String> {
    @Override
    public boolean test(String s) {
        // TO DO: REGEX to validate email

        return true;
    }
}
