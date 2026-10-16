package com.foreverfitness.fintess.repository;

import com.foreverfitness.fintess.entity.Trainer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TrainerRepository extends JpaRepository<Trainer, Long> {
    List<Trainer> findByLastNameContainingIgnoreCase(String lastName);
}