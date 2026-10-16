package com.foreverfitness.fintess.repository;

import com.foreverfitness.fintess.entity.WorkoutType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface WorkoutTypeRepository extends JpaRepository<WorkoutType, Long> {
    Optional<WorkoutType> findByName(String name);
}
