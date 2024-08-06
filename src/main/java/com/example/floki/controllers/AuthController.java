package com.example.floki.controllers;

import com.example.floki.entities.LoginRequestEntity;
import com.example.floki.entities.SenderEntity;
import com.example.floki.entities.SignUpRequestTransporter;
import com.example.floki.entities.TransporterEntity;
import com.example.floki.services.CodeGenerateService;
import com.example.floki.services.EmailService;
import com.example.floki.services.SenderService;
import com.example.floki.services.TransporterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private SenderService senderService;

    @Autowired
    private TransporterService transporterService;


    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmailService emailService;

    @Autowired
    private CodeGenerateService codeGeneratorService;

    private Map<String, String> verificationCodes = new HashMap<>();

    @PostMapping("/signup/sender")
    public ResponseEntity<SenderEntity> signupSender(@RequestBody SenderEntity sender) {
        sender.setPassword(passwordEncoder.encode(sender.getPassword()));
        return ResponseEntity.ok(senderService.saveSender(sender));
    }


    @PostMapping("/signup/transporter")
    public ResponseEntity<String> signupTransporter(@RequestBody TransporterEntity transporter) {
        // Validation des données du transporteur
        if (transporter.getEmail() == null || transporter.getEmail().isEmpty()) {
            return ResponseEntity.badRequest().body("Email is required.");
        }
        if (transporter.getPassword() == null || transporter.getPassword().isEmpty()) {
            return ResponseEntity.badRequest().body("Password is required.");
        }
        // Vous pouvez ajouter d'autres validations si nécessaire (par exemple, format de l'email)

        try {
            String verificationCode = codeGeneratorService.generateCode();
            verificationCodes.put(transporter.getEmail(), verificationCode);
            emailService.sendVerificationCode(transporter.getEmail(), verificationCode);
            return ResponseEntity.ok("Verification code sent to your email.");
        } catch (Exception e) {
            // Gestion des exceptions spécifiques à la génération du code ou à l'envoi d'email
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while processing your request.");
        }
    }


    @PostMapping("/verify")
    public ResponseEntity<String> verifyCode( @RequestBody SignUpRequestTransporter request) {
        String expectedCode = verificationCodes.get(request.getTransporter().getEmail());
        if (expectedCode != null && expectedCode.equals(request.getCode())) {
            verificationCodes.remove(request.getTransporter().getEmail());
            if (request.getTransporter().getEmail() == null || request.getTransporter().getPassword() == null) {
                return ResponseEntity.badRequest().body("Invalid request.");
            }

            request.getTransporter().setPassword(passwordEncoder.encode(request.getTransporter().getPassword()));
            transporterService.saveTransporter(request.getTransporter());
            return ResponseEntity.ok("code verified successfully and Transporter registered successfully.");
        }
        return ResponseEntity.status(400).body("Invalid code.");
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



