package com.marimo.budget_system.service;

import com.marimo.budget_system.entity.Budget;
import com.marimo.budget_system.entity.BudgetItem;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class BudgetService {

    public BigDecimal calculateItemTotal(BudgetItem item) {
        BigDecimal costTotal = item.getUnitCost()
                .multiply(item.getQuantity());

        BigDecimal margin = item.getProfitMargin()
                .divide(BigDecimal.valueOf(100));

        return costTotal.multiply(BigDecimal.ONE.add(margin));
    }

    public BigDecimal calculateBudgetTotal(Budget budget) {

        BigDecimal itemsTotal = budget.getItems().stream()
                .map(this::calculateItemTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal laborCost = budget.getLaborCost() != null
                ? budget.getLaborCost()
                : BigDecimal.ZERO;

        return itemsTotal.add(laborCost);
    }

    public void updateBudgetValues(Budget budget) {

        for (BudgetItem item : budget.getItems()) {
            BigDecimal itemTotal = calculateItemTotal(item);
            item.setTotalAmount(itemTotal);
        }

        BigDecimal total = calculateBudgetTotal(budget);
        budget.setTotalAmount(total);
    }
}
