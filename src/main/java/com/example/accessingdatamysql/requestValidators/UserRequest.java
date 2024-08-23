package com.example.accessingdatamysql.requestValidators;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public class UserRequest {
    @NotEmpty(message = "Name is not empty.")
    private String name;

    @NotEmpty(message = "Email is not empty")
    @Email(message = "Email is Invalid")
    private String email;

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return "UserRequest{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
