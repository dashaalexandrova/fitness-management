package com.foreverfitness.fintess.service;

import com.foreverfitness.fintess.entity.Workout;
import java.util.List;
import java.util.Optional;

public interface WorkoutService {
    List<Workout> findAll();
    Optional<Workout> findById(Long id);
    Workout save(Workout workout);
    void deleteById(Long id);
    List<Workout> findByTrainerId(Long trainerId);
}
