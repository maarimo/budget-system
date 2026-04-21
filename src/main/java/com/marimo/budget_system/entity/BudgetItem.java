package com.marimo.budget_system.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class BudgetItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;
    private String material;
    private BigDecimal quantity;
    private BigDecimal unitCost;
    private BigDecimal profitMargin;
    private BigDecimal totalAmount;

    @ManyToOne
    @JoinColumn(name = "budget_id")
    private Budget budget;

}
