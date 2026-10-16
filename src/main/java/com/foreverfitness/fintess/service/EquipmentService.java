package com.foreverfitness.fintess.service;

import com.foreverfitness.fintess.entity.Equipment;
import java.util.List;
import java.util.Optional;

public interface EquipmentService {
    List<Equipment> findAll();
    Optional<Equipment> findById(Long id);
    Equipment save(Equipment equipment);
    void deleteById(Long id);
}
