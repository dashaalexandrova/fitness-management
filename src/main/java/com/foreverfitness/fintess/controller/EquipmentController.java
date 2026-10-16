package com.foreverfitness.fintess.controller;

import com.foreverfitness.fintess.dto.CreateEquipmentDTO;
import com.foreverfitness.fintess.dto.EquipmentDTO;
import com.foreverfitness.fintess.entity.Equipment;
import com.foreverfitness.fintess.service.EquipmentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/equipment")
public class EquipmentController {

    private final EquipmentService equipmentService;

    public EquipmentController(EquipmentService equipmentService) {
        this.equipmentService = equipmentService;
    }

    @GetMapping
    public List<EquipmentDTO> getAll() {
        return equipmentService.findAll().stream()
                .map(this::toDTO)
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<EquipmentDTO> getById(@PathVariable Long id) {
        return equipmentService.findById(id)
                .map(e -> ResponseEntity.ok(toDTO(e)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<EquipmentDTO> create(@Valid @RequestBody CreateEquipmentDTO dto) {
        Equipment saved = equipmentService.save(toEntity(dto));
        return ResponseEntity.status(HttpStatus.CREATED).body(toDTO(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EquipmentDTO> update(@PathVariable Long id,
                                               @Valid @RequestBody CreateEquipmentDTO dto) {
        if (equipmentService.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Equipment equipment = toEntity(dto);
        equipment.setId(id);
        return ResponseEntity.ok(toDTO(equipmentService.save(equipment)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (equipmentService.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        equipmentService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    private EquipmentDTO toDTO(Equipment equipment) {
        EquipmentDTO dto = new EquipmentDTO();
        dto.setId(equipment.getId());
        dto.setName(equipment.getName());
        dto.setQuantity(equipment.getQuantity());
        dto.setCondition(equipment.getCondition());
        return dto;
    }

    private Equipment toEntity(CreateEquipmentDTO dto) {
        Equipment equipment = new Equipment();
        equipment.setName(dto.getName());
        equipment.setQuantity(dto.getQuantity());
        equipment.setCondition(dto.getCondition());
        return equipment;
    }
}
