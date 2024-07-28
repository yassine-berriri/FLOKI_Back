package com.example.floki.services;

import com.example.floki.entities.SenderEntity;
import com.example.floki.repositories.SenderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SenderService {

    @Autowired
    private SenderRepository senderRepository;

    public SenderEntity saveSender(SenderEntity sender) {
        return senderRepository.save(sender);
    }

    public SenderEntity findByEmail(String email) {
        return senderRepository.findByEmail(email);
    }

}
