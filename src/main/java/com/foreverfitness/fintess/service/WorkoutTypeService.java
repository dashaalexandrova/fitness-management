package com.foreverfitness.fintess.service;

import com.foreverfitness.fintess.entity.WorkoutType;
import java.util.List;
import java.util.Optional;

public interface WorkoutTypeService {
    List<WorkoutType> findAll();
    Optional<WorkoutType> findById(Long id);
    WorkoutType save(WorkoutType workoutType);
    void deleteById(Long id);
}
