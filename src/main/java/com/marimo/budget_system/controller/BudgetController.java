package com.marimo.budget_system.controller;

import com.marimo.budget_system.dto.BudgetRequestDTO;
import com.marimo.budget_system.entity.Budget;
import com.marimo.budget_system.entity.BudgetItem;
import com.marimo.budget_system.entity.Customer;
import com.marimo.budget_system.repository.BudgetRepository;
import com.marimo.budget_system.repository.CustomerRepository;
import com.marimo.budget_system.service.BudgetService;
import com.marimo.budget_system.service.PdfService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/budgets")
public class BudgetController {

    private final PdfService pdfService;
    private final BudgetService budgetService;
    private final BudgetRepository budgetRepository;
    private final CustomerRepository customerRepository;

    public BudgetController(
            PdfService pdfService,
            BudgetService budgetService,
            BudgetRepository budgetRepository,
            CustomerRepository customerRepository
    ) {
        this.pdfService = pdfService;
        this.budgetService = budgetService;
        this.budgetRepository = budgetRepository;
        this.customerRepository = customerRepository;
    }

    @GetMapping("/{id}/pdf")
    public ResponseEntity<byte[]> generatePdf(@PathVariable Long id) {

        Budget budget = budgetRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Budget not found"));

        byte[] pdf = pdfService.generateBudgetPdf(budget);

        return ResponseEntity.ok()
                .header("Content-Disposition", "inline; filename=budget.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }

    @PostMapping
    public Budget createBudget(@RequestBody BudgetRequestDTO dto) {

        Customer customer = customerRepository.findById(dto.customerId())
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        Budget budget = new Budget();
        budget.setCustomer(customer);
        budget.setLaborCost(dto.laborCost());
        budget.setCreatedAt(LocalDateTime.now());
        budget.setStatus("CREATED");

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

        budget.setItems(items);

        budgetService.updateBudgetValues(budget);

        return budgetRepository.save(budget);
    }
}