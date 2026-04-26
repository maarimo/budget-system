package com.marimo.budget_system.service;

import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.io.source.ByteArrayOutputStream;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
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

            // ================= HEADER =================

            Table header = new Table(UnitValue.createPercentArray(new float[]{1, 3}))
                    .useAllAvailableWidth();

            // Logo
            try {
                Image logo = new Image(ImageDataFactory.create("src/main/resources/logo.png"))
                        .scaleToFit(80, 80);
                header.addCell(new Cell().add(logo).setBorder(null));
            } catch (Exception e) {
                header.addCell(new Cell().add(new Paragraph("LOGO")).setBorder(null));
            }

            // Nome empresa
            header.addCell(
                    new Cell().add(new Paragraph("NOME DA EMPRESA")
                                    .setBold()
                                    .setFontSize(16))
                            .add(new Paragraph("Orçamentos de Serviços"))
                            .setBorder(null)
                            .setTextAlignment(TextAlignment.RIGHT)
            );

            document.add(header);
            document.add(new Paragraph("\n"));

            // ================= TÍTULO =================

            document.add(new Paragraph("ORÇAMENTO")
                    .setBold()
                    .setFontSize(20)
                    .setTextAlignment(TextAlignment.CENTER));

            document.add(new Paragraph("\n"));

            // ================= DADOS =================

            String customerName = budget.getCustomer() != null
                    ? budget.getCustomer().getName()
                    : "N/A";

            String date = budget.getCreatedAt() != null
                    ? budget.getCreatedAt().format(formatter)
                    : "N/A";

            String service = budget.getServiceDescription() != null
                    ? budget.getServiceDescription()
                    : "N/A";

            document.add(new Paragraph("Cliente: " + customerName));
            document.add(new Paragraph("Data: " + date));
            document.add(new Paragraph("Serviço: " + service));

            document.add(new Paragraph("\n"));

            // ================= TABELA =================

            Table table = new Table(UnitValue.createPercentArray(new float[]{4, 2, 2, 2}))
                    .useAllAvailableWidth();

            // Cabeçalho
            table.addHeaderCell(createHeaderCell("Material"));
            table.addHeaderCell(createHeaderCell("Qtd"));
            table.addHeaderCell(createHeaderCell("Unitário"));
            table.addHeaderCell(createHeaderCell("Total"));

            // Itens
            if (budget.getItems() != null) {
                for (BudgetItem item : budget.getItems()) {

                    table.addCell(createCell(item.getMaterial()));
                    table.addCell(createCell(String.valueOf(item.getQuantity())));
                    table.addCell(createCell(currency.format(item.getUnitCost())));
                    table.addCell(createCell(currency.format(item.getTotalAmount())));
                }
            }

            document.add(table);

            document.add(new Paragraph("\n"));

            // ================= RESUMO =================

            document.add(new Paragraph("Mão de obra: " +
                    currency.format(budget.getLaborCost()))
                    .setTextAlignment(TextAlignment.RIGHT));

            document.add(new Paragraph("TOTAL: " +
                    currency.format(budget.getTotalAmount()))
                    .setBold()
                    .setFontSize(16)
                    .setTextAlignment(TextAlignment.RIGHT));

            document.add(new Paragraph("\n"));

            // ================= RODAPÉ =================

            document.add(new Paragraph("Contato: (71) 99999-9999")
                    .setFontSize(10)
                    .setTextAlignment(TextAlignment.CENTER));

            document.add(new Paragraph("Email: contato@empresa.com")
                    .setFontSize(10)
                    .setTextAlignment(TextAlignment.CENTER));

            document.add(new Paragraph("Obrigado pela preferência!")
                    .setItalic()
                    .setFontSize(10)
                    .setTextAlignment(TextAlignment.CENTER));

            document.close();

        } catch (Exception e) {
            throw new RuntimeException("Erro ao gerar PDF", e);
        }

        return out.toByteArray();
    }

    // ================= HELPERS =================

    private Cell createHeaderCell(String text) {
        return new Cell()
                .add(new Paragraph(text).setBold().setFontColor(ColorConstants.WHITE))
                .setBackgroundColor(ColorConstants.BLACK)
                .setTextAlignment(TextAlignment.CENTER);
    }

    private Cell createCell(String text) {
        return new Cell()
                .add(new Paragraph(text != null ? text : "-"))
                .setTextAlignment(TextAlignment.CENTER);
    }
}