package com.foreverfitness.fintess.repository;

import com.foreverfitness.fintess.entity.Workout;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface WorkoutRepository extends JpaRepository<Workout, Long> {
    List<Workout> findByTrainerId(Long trainerId);
    List<Workout> findByWorkoutTypeId(Long workoutTypeId);
    List<Workout> findByStartTimeAfter(LocalDateTime time);
}