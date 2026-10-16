package com.foreverfitness.fintess.controller;

import com.foreverfitness.fintess.dto.CreateWorkoutDTO;
import com.foreverfitness.fintess.dto.WorkoutDTO;
import com.foreverfitness.fintess.entity.Trainer;
import com.foreverfitness.fintess.entity.Workout;
import com.foreverfitness.fintess.entity.WorkoutType;
import com.foreverfitness.fintess.service.TrainerService;
import com.foreverfitness.fintess.service.WorkoutService;
import com.foreverfitness.fintess.service.WorkoutTypeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/workouts")
public class WorkoutController {

    private final WorkoutService workoutService;
    private final TrainerService trainerService;
    private final WorkoutTypeService workoutTypeService;

    public WorkoutController(WorkoutService workoutService,
                             TrainerService trainerService,
                             WorkoutTypeService workoutTypeService) {
        this.workoutService = workoutService;
        this.trainerService = trainerService;
        this.workoutTypeService = workoutTypeService;
    }

    @GetMapping
    public List<WorkoutDTO> getAll() {
        return workoutService.findAll().stream()
                .map(this::toDTO)
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<WorkoutDTO> getById(@PathVariable Long id) {
        return workoutService.findById(id)
                .map(w -> ResponseEntity.ok(toDTO(w)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Все занятия одного тренера
    @GetMapping("/trainer/{trainerId}")
    public List<WorkoutDTO> getByTrainer(@PathVariable Long trainerId) {
        return workoutService.findByTrainerId(trainerId).stream()
                .map(this::toDTO)
                .toList();
    }

    @PostMapping
    public ResponseEntity<WorkoutDTO> create(@Valid @RequestBody CreateWorkoutDTO dto) {
        Optional<Trainer> trainer = trainerService.findById(dto.getTrainerId());
        Optional<WorkoutType> type = workoutTypeService.findById(dto.getWorkoutTypeId());
        if (trainer.isEmpty() || type.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        Workout saved = workoutService.save(toEntity(dto, trainer.get(), type.get()));
        return ResponseEntity.status(HttpStatus.CREATED).body(toDTO(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<WorkoutDTO> update(@PathVariable Long id,
                                             @Valid @RequestBody CreateWorkoutDTO dto) {
        if (workoutService.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Optional<Trainer> trainer = trainerService.findById(dto.getTrainerId());
        Optional<WorkoutType> type = workoutTypeService.findById(dto.getWorkoutTypeId());
        if (trainer.isEmpty() || type.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        Workout workout = toEntity(dto, trainer.get(), type.get());
        workout.setId(id);
        return ResponseEntity.ok(toDTO(workoutService.save(workout)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (workoutService.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        workoutService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    private WorkoutDTO toDTO(Workout workout) {
        WorkoutDTO dto = new WorkoutDTO();
        dto.setId(workout.getId());
        dto.setTitle(workout.getTitle());
        dto.setStartTime(workout.getStartTime());
        dto.setDurationMinutes(workout.getDurationMinutes());
        dto.setMaxParticipants(workout.getMaxParticipants());
        if (workout.getTrainer() != null) {
            dto.setTrainerId(workout.getTrainer().getId());
            dto.setTrainerName(workout.getTrainer().getFirstName() + " "
                    + workout.getTrainer().getLastName());
        }
        if (workout.getWorkoutType() != null) {
            dto.setWorkoutTypeId(workout.getWorkoutType().getId());
            dto.setWorkoutTypeName(workout.getWorkoutType().getName());
        }
        return dto;
    }

    private Workout toEntity(CreateWorkoutDTO dto, Trainer trainer, WorkoutType type) {
        Workout workout = new Workout();
        workout.setTitle(dto.getTitle());
        workout.setStartTime(dto.getStartTime());
        workout.setDurationMinutes(dto.getDurationMinutes());
        workout.setMaxParticipants(dto.getMaxParticipants());
        workout.setTrainer(trainer);
        workout.setWorkoutType(type);
        return workout;
    }
}
