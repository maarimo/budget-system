package com.marimo.budget_system.service;

import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.io.source.ByteArrayOutputStream;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.properties.TextAlignment;
import com.marimo.budget_system.entity.Budget;
import com.marimo.budget_system.entity.BudgetItem;
import org.springframework.stereotype.Service;

import java.text.NumberFormat;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Service
public class PdfService {

    public byte[] generateBudgetPdf(Budget budget) {

        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            PdfWriter writer = new PdfWriter(out);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            NumberFormat currency = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            // LOGO
            try {
                Image logo = new Image(ImageDataFactory.create("src/main/resources/logo.png"))
                        .scaleToFit(120, 60);
                document.add(logo);
            } catch (Exception ignored) {}

            // Título
            document.add(new Paragraph("ORÇAMENTO")
                    .setBold()
                    .setFontSize(20)
                    .setTextAlignment(TextAlignment.CENTER));

            document.add(new Paragraph("\n"));

            // Cliente
            String customerName = budget.getCustomer() != null
                    ? budget.getCustomer().getName()
                    : "N/A";

            document.add(new Paragraph("Cliente: " + customerName));

            // Data
            String date = budget.getCreatedAt() != null
                    ? budget.getCreatedAt().format(formatter)
                    : "N/A";

            document.add(new Paragraph("Data: " + date));

            // Serviço
            String service = budget.getServiceDescription() != null
                    ? budget.getServiceDescription()
                    : "N/A";

            document.add(new Paragraph("Serviço: " + service));

            document.add(new Paragraph("\n"));

            // TABELA
            float[] columnWidths = {200F, 80F, 100F, 100F};
            Table table = new Table(columnWidths);

            table.addHeaderCell("Material");
            table.addHeaderCell("Qtd");
            table.addHeaderCell("Unitário");
            table.addHeaderCell("Total");

            if (budget.getItems() != null) {
                for (BudgetItem item : budget.getItems()) {

                    table.addCell(item.getMaterial() != null ? item.getMaterial() : "-");
                    table.addCell(String.valueOf(item.getQuantity()));
                    table.addCell(currency.format(item.getUnitCost()));
                    table.addCell(currency.format(item.getTotalAmount()));
                }
            }

            document.add(table);

            document.add(new Paragraph("\n"));

            // Mão de obra
            document.add(new Paragraph("Mão de obra: " +
                    currency.format(budget.getLaborCost())));

            // TOTAL
            document.add(new Paragraph("TOTAL: " +
                    currency.format(budget.getTotalAmount()))
                    .setBold()
                    .setFontSize(14)
                    .setTextAlignment(TextAlignment.RIGHT));

            document.close();

        } catch (Exception e) {
            throw new RuntimeException("Error generating PDF", e);
        }

        return out.toByteArray();
    }
}