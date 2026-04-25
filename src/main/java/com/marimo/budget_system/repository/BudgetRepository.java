package com.marimo.budget_system.repository;

import com.marimo.budget_system.entity.Budget;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BudgetRepository extends JpaRepository<Budget, Long> {

    List<Budget> findAllByOrderByCreatedAtDesc();
}