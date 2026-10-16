package com.foreverfitness.fintess.service;

import com.foreverfitness.fintess.entity.Trainer;
import com.foreverfitness.fintess.repository.TrainerRepository;
import com.foreverfitness.fintess.service.impl.TrainerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TrainerServiceTest {

    @Mock
    private TrainerRepository trainerRepository;

    @InjectMocks
    private TrainerServiceImpl trainerService;

    private Trainer testTrainer;

    @BeforeEach
    void setUp() {
        testTrainer = new Trainer();
        testTrainer.setId(1L);
        testTrainer.setFirstName("Анна");
        testTrainer.setLastName("Смирнова");
        testTrainer.setSpecialization("Йога");
    }

    @Test
    void findAll_ShouldReturnAllTrainers() {
        when(trainerRepository.findAll()).thenReturn(List.of(testTrainer));

        List<Trainer> result = trainerService.findAll();

        assertEquals(1, result.size());
        assertEquals("Смирнова", result.get(0).getLastName());
        verify(trainerRepository).findAll();
    }

    @Test
    void findById_WhenTrainerExists_ShouldReturnTrainer() {
        when(trainerRepository.findById(1L)).thenReturn(Optional.of(testTrainer));

        Optional<Trainer> result = trainerService.findById(1L);

        assertTrue(result.isPresent());
        assertEquals("Анна", result.get().getFirstName());
    }

    @Test
    void findById_WhenTrainerNotExists_ShouldReturnEmpty() {
        when(trainerRepository.findById(999L)).thenReturn(Optional.empty());

        Optional<Trainer> result = trainerService.findById(999L);

        assertFalse(result.isPresent());
    }

    @Test
    void save_ShouldReturnSavedTrainer() {
        when(trainerRepository.save(testTrainer)).thenReturn(testTrainer);

        Trainer result = trainerService.save(testTrainer);

        assertEquals("Йога", result.getSpecialization());
        verify(trainerRepository).save(testTrainer);
    }

    @Test
    void deleteById_ShouldCallRepository() {
        trainerService.deleteById(1L);

        verify(trainerRepository).deleteById(1L);
    }
}
