package com.foreverfitness.fintess.service.impl;

import com.foreverfitness.fintess.entity.Workout;
import com.foreverfitness.fintess.repository.WorkoutRepository;
import com.foreverfitness.fintess.service.WorkoutService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class WorkoutServiceImpl implements WorkoutService {

    private final WorkoutRepository workoutRepository;

    public WorkoutServiceImpl(WorkoutRepository workoutRepository) {
        this.workoutRepository = workoutRepository;
    }

    @Override
    public List<Workout> findAll() {
        return workoutRepository.findAll();
    }

    @Override
    public Optional<Workout> findById(Long id) {
        return workoutRepository.findById(id);
    }

    @Override
    public Workout save(Workout workout) {
        return workoutRepository.save(workout);
    }

    @Override
    public void deleteById(Long id) {
        workoutRepository.deleteById(id);
    }

    @Override
    public List<Workout> findByTrainerId(Long trainerId) {
        return workoutRepository.findByTrainerId(trainerId);
    }
}
