package com.example.floki.entities;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpRequestTransporter {
    private String code;
    private TransporterEntity transporter;
}
