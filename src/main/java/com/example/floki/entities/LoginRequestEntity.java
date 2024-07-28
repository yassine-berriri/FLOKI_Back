package com.example.floki.entities;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class LoginRequestEntity {
    private String email;
    private String password;

    // Getters and setters...
}
