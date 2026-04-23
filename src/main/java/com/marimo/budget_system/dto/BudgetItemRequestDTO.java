package com.marimo.budget_system.dto;

import java.math.BigDecimal;

public record BudgetItemRequestDTO(
        String description,
        String material,
        BigDecimal quantity,
        BigDecimal unitCost,
        BigDecimal profitMargin
) {}