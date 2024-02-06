package com.rava.learning.demo.customer;

import java.time.LocalDate;

public record CustomerRequest(
        Long id,
        String name,
        String email,
        LocalDate dob,
        Integer age
) {
}
