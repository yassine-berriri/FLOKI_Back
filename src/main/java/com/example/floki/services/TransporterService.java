package com.example.floki.services;

import com.example.floki.entities.TransporterEntity;
import com.example.floki.repositories.TransporterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransporterService {
    @Autowired
    private TransporterRepository transporterRepository;

    public TransporterEntity saveTransporter(TransporterEntity transporter) {
        return transporterRepository.save(transporter);
    }

    public TransporterEntity findByEmail(String email) {
        return transporterRepository.findByEmail(email);
    }
}
