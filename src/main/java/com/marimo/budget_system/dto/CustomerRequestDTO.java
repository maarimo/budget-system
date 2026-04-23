package com.marimo.budget_system.dto;

public record CustomerRequestDTO(
        String name,
        String phone,
        String email
) {}