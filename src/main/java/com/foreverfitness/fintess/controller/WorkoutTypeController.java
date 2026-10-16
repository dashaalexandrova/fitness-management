package com.foreverfitness.fintess.controller;

import com.foreverfitness.fintess.dto.CreateWorkoutTypeDTO;
import com.foreverfitness.fintess.dto.WorkoutTypeDTO;
import com.foreverfitness.fintess.entity.WorkoutType;
import com.foreverfitness.fintess.service.WorkoutTypeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/workout-types")
public class WorkoutTypeController {

    private final WorkoutTypeService workoutTypeService;

    public WorkoutTypeController(WorkoutTypeService workoutTypeService) {
        this.workoutTypeService = workoutTypeService;
    }

    @GetMapping
    public List<WorkoutTypeDTO> getAll() {
        return workoutTypeService.findAll().stream()
                .map(this::toDTO)
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<WorkoutTypeDTO> getById(@PathVariable Long id) {
        return workoutTypeService.findById(id)
                .map(t -> ResponseEntity.ok(toDTO(t)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<WorkoutTypeDTO> create(@Valid @RequestBody CreateWorkoutTypeDTO dto) {
        WorkoutType saved = workoutTypeService.save(toEntity(dto));
        return ResponseEntity.status(HttpStatus.CREATED).body(toDTO(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<WorkoutTypeDTO> update(@PathVariable Long id,
                                                 @Valid @RequestBody CreateWorkoutTypeDTO dto) {
        if (workoutTypeService.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        WorkoutType workoutType = toEntity(dto);
        workoutType.setId(id);
        return ResponseEntity.ok(toDTO(workoutTypeService.save(workoutType)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (workoutTypeService.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        workoutTypeService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    private WorkoutTypeDTO toDTO(WorkoutType type) {
        WorkoutTypeDTO dto = new WorkoutTypeDTO();
        dto.setId(type.getId());
        dto.setName(type.getName());
        dto.setDescription(type.getDescription());
        return dto;
    }

    private WorkoutType toEntity(CreateWorkoutTypeDTO dto) {
        WorkoutType type = new WorkoutType();
        type.setName(dto.getName());
        type.setDescription(dto.getDescription());
        return type;
    }
}
