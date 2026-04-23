package com.marimo.budget_system.controller;

import com.marimo.budget_system.dto.BudgetRequestDTO;
import com.marimo.budget_system.entity.Budget;
import com.marimo.budget_system.entity.BudgetItem;
import com.marimo.budget_system.entity.Customer;
import com.marimo.budget_system.repository.BudgetRepository;
import com.marimo.budget_system.repository.CustomerRepository;
import com.marimo.budget_system.service.BudgetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/budgets")
public class BudgetController {

    @Autowired
    private BudgetService budgetService;

    @Autowired
    private BudgetRepository budgetRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @PostMapping
    public Budget createBudget(@RequestBody BudgetRequestDTO dto) {

        // Buscar cliente
        Customer customer = customerRepository.findById(dto.customerId())
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        // Criar orçamento
        Budget budget = new Budget();
        budget.setCustomer(customer);
        budget.setLaborCost(dto.laborCost());
        budget.setCreatedAt(LocalDateTime.now());
        budget.setStatus("CREATED");

        // Criar itens
        List<BudgetItem> items = dto.items().stream().map(itemDto -> {
            BudgetItem item = new BudgetItem();
            item.setDescription(itemDto.description());
            item.setMaterial(itemDto.material());
            item.setQuantity(itemDto.quantity());
            item.setUnitCost(itemDto.unitCost());
            item.setProfitMargin(itemDto.profitMargin());
            item.setBudget(budget);
            return item;
        }).toList();

        // Associar itens ao orçamento
        budget.setItems(items);

        // Aplicar regras de negócio (cálculo)
        budgetService.updateBudgetValues(budget);

        // Salvar no banco
        return budgetRepository.save(budget);
    }
}