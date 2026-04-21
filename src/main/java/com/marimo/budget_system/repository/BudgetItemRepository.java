package com.marimo.budget_system.repository;

import com.marimo.budget_system.entity.BudgetItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BudgetItemRepository extends JpaRepository<BudgetItem, Long> {
}
