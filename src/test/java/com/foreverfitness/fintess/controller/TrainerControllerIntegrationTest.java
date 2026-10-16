package com.foreverfitness.fintess.controller;

import com.foreverfitness.fintess.entity.Trainer;
import com.foreverfitness.fintess.repository.TrainerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class TrainerControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TrainerRepository trainerRepository;

    @Test
    void getAllTrainers_WithoutLogin_ShouldReturn401() throws Exception {
        mockMvc.perform(get("/api/trainers"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser
    void getAllTrainers_AfterLogin_ShouldReturnArray() throws Exception {
        mockMvc.perform(get("/api/trainers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    @WithMockUser
    void getTrainerById_WhenExists_ShouldReturnTrainer() throws Exception {
        Trainer trainer = new Trainer();
        trainer.setFirstName("Олег");
        trainer.setLastName("Иванов");
        trainer.setSpecialization("Кроссфит");
        trainer = trainerRepository.save(trainer);

        mockMvc.perform(get("/api/trainers/{id}", trainer.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Олег"))
                .andExpect(jsonPath("$.specialization").value("Кроссфит"));
    }

    @Test
    @WithMockUser
    void getTrainerById_WhenNotExists_ShouldReturn404() throws Exception {
        mockMvc.perform(get("/api/trainers/999999"))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser
    void createTrainer_WithValidData_ShouldReturn201() throws Exception {
        String json = """
                {
                  "firstName": "Мария",
                  "lastName": "Козлова",
                  "specialization": "Пилатес"
                }
                """;

        mockMvc.perform(post("/api/trainers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName").value("Мария"))
                .andExpect(jsonPath("$.id").exists());
    }

    @Test
    @WithMockUser
    void createTrainer_WithEmptyName_ShouldReturn400() throws Exception {
        String json = """
                {
                  "firstName": "",
                  "lastName": "Козлова"
                }
                """;

        mockMvc.perform(post("/api/trainers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest());
    }
}
