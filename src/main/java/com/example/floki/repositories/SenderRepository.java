package com.example.floki.repositories;


import com.example.floki.entities.SenderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SenderRepository extends JpaRepository<SenderEntity, Long> {

    SenderEntity findByEmail(String email);


}
