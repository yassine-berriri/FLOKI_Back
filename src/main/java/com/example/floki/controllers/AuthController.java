package com.example.floki.controllers;

import com.example.floki.entities.LoginRequestEntity;
import com.example.floki.entities.SenderEntity;
import com.example.floki.entities.TransporterEntity;
import com.example.floki.services.SenderService;
import com.example.floki.services.TransporterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private SenderService senderService;

    @Autowired
    private TransporterService transporterService;


    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/signup/sender")
    public ResponseEntity<SenderEntity> signupSender(@RequestBody SenderEntity sender) {
        sender.setPassword(passwordEncoder.encode(sender.getPassword()));
        return ResponseEntity.ok(senderService.saveSender(sender));
    }


    @PostMapping("/signup/transporter")
    public ResponseEntity<TransporterEntity> signupTransporter(@RequestBody TransporterEntity transporter) {
        transporter.setPassword(passwordEncoder.encode(transporter.getPassword()));
        return ResponseEntity.ok(transporterService.saveTransporter(transporter));
    }


    @PostMapping("/login/sender")
    public ResponseEntity<String> loginSender(@RequestBody LoginRequestEntity loginRequest) {
        SenderEntity sender = senderService.findByEmail(loginRequest.getEmail());
        if (sender != null && passwordEncoder.matches(loginRequest.getPassword(), sender.getPassword())) {
            return ResponseEntity.ok("Login successful");
        }
        return ResponseEntity.status(401).body("Login failed");
    }

    @PostMapping("/login/transporter")
    public ResponseEntity<String> loginTransporter(@RequestBody LoginRequestEntity loginRequest) {
        TransporterEntity transporter = transporterService.findByEmail(loginRequest.getEmail());
        if (transporter != null && passwordEncoder.matches(loginRequest.getPassword(), transporter.getPassword())) {
            return ResponseEntity.ok("Login successful");
        }
        return ResponseEntity.status(401).body("Login failed");
    }
}



