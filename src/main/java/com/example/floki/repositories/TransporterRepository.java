package com.example.floki.repositories;

import com.example.floki.entities.TransporterEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransporterRepository extends JpaRepository<TransporterEntity, Long>{
    TransporterEntity findByEmail(String email);
}
