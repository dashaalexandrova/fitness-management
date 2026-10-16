package com.foreverfitness.fintess.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public class CreateEquipmentDTO {
    @NotBlank(message = "Название обязательно")
    private String name;

    @NotNull(message = "Количество обязательно")
    @PositiveOrZero(message = "Количество не может быть отрицательным")
    private Integer quantity;

    private String condition;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public CreateEquipmentDTO() {}
}
