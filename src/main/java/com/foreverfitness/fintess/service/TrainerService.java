package com.foreverfitness.fintess.service;

import com.foreverfitness.fintess.entity.Trainer;
import java.util.List;
import java.util.Optional;

public interface TrainerService {
    List<Trainer> findAll();
    Optional<Trainer> findById(Long id);
    Trainer save(Trainer trainer);
    void deleteById(Long id);
}