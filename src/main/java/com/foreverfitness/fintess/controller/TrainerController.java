package com.foreverfitness.fintess.controller;

import com.foreverfitness.fintess.dto.CreateTrainerDTO;
import com.foreverfitness.fintess.dto.TrainerDTO;
import com.foreverfitness.fintess.entity.Trainer;
import com.foreverfitness.fintess.service.TrainerService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/trainers")
public class TrainerController {

    private final TrainerService trainerService;

    public TrainerController(TrainerService trainerService) {
        this.trainerService = trainerService;
    }

    // Получить всех тренеров
    @GetMapping
    public List<TrainerDTO> getAll() {
        return trainerService.findAll().stream()
                .map(this::toDTO)
                .toList();
    }

    // Получить тренера по id
    @GetMapping("/{id}")
    public ResponseEntity<TrainerDTO> getById(@PathVariable Long id) {
        Optional<Trainer> trainer = trainerService.findById(id);
        return trainer.map(t -> ResponseEntity.ok(toDTO(t)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Создать тренера
    @PostMapping
    public ResponseEntity<TrainerDTO> create(@Valid @RequestBody CreateTrainerDTO dto) {
        Trainer saved = trainerService.save(toEntity(dto));
        return ResponseEntity.status(HttpStatus.CREATED).body(toDTO(saved));
    }

    // Обновить тренера
    @PutMapping("/{id}")
    public ResponseEntity<TrainerDTO> update(@PathVariable Long id,
                                             @Valid @RequestBody CreateTrainerDTO dto) {
        if (trainerService.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Trainer trainer = toEntity(dto);
        trainer.setId(id);
        return ResponseEntity.ok(toDTO(trainerService.save(trainer)));
    }

    // Удалить тренера
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (trainerService.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        trainerService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // Entity -> DTO
    private TrainerDTO toDTO(Trainer trainer) {
        TrainerDTO dto = new TrainerDTO();
        dto.setId(trainer.getId());
        dto.setFirstName(trainer.getFirstName());
        dto.setLastName(trainer.getLastName());
        dto.setSpecialization(trainer.getSpecialization());
        return dto;
    }

    // DTO -> Entity
    private Trainer toEntity(CreateTrainerDTO dto) {
        Trainer trainer = new Trainer();
        trainer.setFirstName(dto.getFirstName());
        trainer.setLastName(dto.getLastName());
        trainer.setSpecialization(dto.getSpecialization());
        return trainer;
    }
}
