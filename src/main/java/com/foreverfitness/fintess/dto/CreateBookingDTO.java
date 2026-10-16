package com.foreverfitness.fintess.dto;

import jakarta.validation.constraints.NotNull;

public class CreateBookingDTO {
    @NotNull(message = "ID клиента обязателен")
    private Long memberId;

    @NotNull(message = "ID занятия обязателен")
    private Long workoutId;

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public Long getWorkoutId() {
        return workoutId;
    }

    public void setWorkoutId(Long workoutId) {
        this.workoutId = workoutId;
    }

    public CreateBookingDTO() {}
}
