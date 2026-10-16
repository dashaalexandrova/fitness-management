package com.foreverfitness.fintess.service.impl;

import com.foreverfitness.fintess.entity.WorkoutType;
import com.foreverfitness.fintess.repository.WorkoutTypeRepository;
import com.foreverfitness.fintess.service.WorkoutTypeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class WorkoutTypeServiceImpl implements WorkoutTypeService {

    private final WorkoutTypeRepository workoutTypeRepository;

    public WorkoutTypeServiceImpl(WorkoutTypeRepository workoutTypeRepository) {
        this.workoutTypeRepository = workoutTypeRepository;
    }

    @Override
    public List<WorkoutType> findAll() {
        return workoutTypeRepository.findAll();
    }

    @Override
    public Optional<WorkoutType> findById(Long id) {
        return workoutTypeRepository.findById(id);
    }

    @Override
    public WorkoutType save(WorkoutType workoutType) {
        return workoutTypeRepository.save(workoutType);
    }

    @Override
    public void deleteById(Long id) {
        workoutTypeRepository.deleteById(id);
    }
}
