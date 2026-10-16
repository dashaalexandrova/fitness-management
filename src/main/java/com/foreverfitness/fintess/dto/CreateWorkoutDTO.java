package com.foreverfitness.fintess.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.time.LocalDateTime;

public class CreateWorkoutDTO {
    @NotBlank(message = "Название занятия обязательно")
    private String title;

    @NotNull(message = "Время начала обязательно")
    private LocalDateTime startTime;

    @NotNull(message = "Длительность обязательна")
    @Positive(message = "Длительность должна быть положительной")
    private Integer durationMinutes;

    @NotNull(message = "Максимум участников обязателен")
    @Positive(message = "Максимум участников должен быть положительным")
    private Integer maxParticipants;

    @NotNull(message = "ID тренера обязателен")
    private Long trainerId;

    @NotNull(message = "ID типа занятия обязателен")
    private Long workoutTypeId;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public Integer getDurationMinutes() {
        return durationMinutes;
    }

    public void setDurationMinutes(Integer durationMinutes) {
        this.durationMinutes = durationMinutes;
    }

    public Integer getMaxParticipants() {
        return maxParticipants;
    }

    public void setMaxParticipants(Integer maxParticipants) {
        this.maxParticipants = maxParticipants;
    }

    public Long getTrainerId() {
        return trainerId;
    }

    public void setTrainerId(Long trainerId) {
        this.trainerId = trainerId;
    }

    public Long getWorkoutTypeId() {
        return workoutTypeId;
    }

    public void setWorkoutTypeId(Long workoutTypeId) {
        this.workoutTypeId = workoutTypeId;
    }

    public CreateWorkoutDTO() {}
}
