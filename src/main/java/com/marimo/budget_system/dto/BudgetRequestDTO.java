package com.marimo.budget_system.dto;

import java.math.BigDecimal;
import java.util.List;

public record BudgetRequestDTO(
        Long customerId,
        BigDecimal laborCost,
        List<BudgetItemRequestDTO> items
) {}