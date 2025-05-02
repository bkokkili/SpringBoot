package com.store.seafoodveggies.util;

import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
import com.store.seafoodveggies.dto.AddressDTO;
import com.store.seafoodveggies.dto.OrderItemResponseDTO;
import com.store.seafoodveggies.dto.OrderResponseDTO;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.stream.Stream;

public class PdfGeneratorUtil {

    public static byte[] generateOrderInvoice(OrderResponseDTO order) throws Exception {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Document document = new Document(PageSize.A4, 40, 40, 50, 50);
        PdfWriter.getInstance(document, out);
        document.open();

        // Fonts
        Font headerFont = new Font(Font.HELVETICA, 22, Font.BOLD, new Color(0, 102, 204));
        Font subHeaderFont = new Font(Font.HELVETICA, 14, Font.BOLD);
        Font normalFont = new Font(Font.HELVETICA, 12);
        Font boldFont = new Font(Font.HELVETICA, 12, Font.BOLD);

        // Title
        Paragraph title = new Paragraph("ORDER INVOICE", headerFont);
        title.setAlignment(Element.ALIGN_CENTER);
        title.setSpacingAfter(20);
        document.add(title);

        // Two-column table for Order Info and Shipping Address
        PdfPTable infoTable = new PdfPTable(2);
        infoTable.setWidthPercentage(100);
        infoTable.setSpacingAfter(20);
        infoTable.setWidths(new float[]{1, 1});

        // Order Details (Left Column)
        PdfPTable orderDetails = new PdfPTable(1);
        orderDetails.getDefaultCell().setBorder(Rectangle.NO_BORDER);
        orderDetails.addCell(new Phrase("Order Details", subHeaderFont));
        orderDetails.addCell(new Phrase("Order ID: " + order.getOrderId(), normalFont));
        orderDetails.addCell(new Phrase("Order Date: " + order.getOrderDate(), normalFont));
        orderDetails.addCell(new Phrase("Customer: " + order.getUserName(), normalFont));
        orderDetails.addCell(new Phrase("Email: " + order.getEmail(), normalFont));
        orderDetails.addCell(new Phrase("Status: " + order.getOrderStatus(), normalFont));

        // Shipping Address (Right Column)
        AddressDTO addr = order.getShippingAddress();
        PdfPTable shippingDetails = new PdfPTable(1);
        shippingDetails.getDefaultCell().setBorder(Rectangle.NO_BORDER);
        shippingDetails.addCell(new Phrase("Shipping Address", subHeaderFont));
        shippingDetails.addCell(new Phrase(addr.getFullName(), normalFont));
        shippingDetails.addCell(new Phrase(addr.getFlatOrHouseNumber() + ", " + addr.getAddressLine1(), normalFont));
        if (addr.getAddressLine2() != null && !addr.getAddressLine2().isEmpty()) {
            shippingDetails.addCell(new Phrase(addr.getAddressLine2(), normalFont));
        }
        shippingDetails.addCell(new Phrase(addr.getCity() + ", " + addr.getState() + " - " + addr.getPostalCode(), normalFont));
        shippingDetails.addCell(new Phrase(addr.getCountry(), normalFont));

        // Wrap into main table cells
        PdfPCell orderCell = new PdfPCell(orderDetails);
        orderCell.setBorder(Rectangle.BOX);
        orderCell.setPadding(10);
        PdfPCell addressCell = new PdfPCell(shippingDetails);
        addressCell.setBorder(Rectangle.BOX);
        addressCell.setPadding(10);

        infoTable.addCell(orderCell);
        infoTable.addCell(addressCell);
        document.add(infoTable);

        // Product Table Header
        PdfPTable table = new PdfPTable(4);
        table.setWidthPercentage(100);
        table.setSpacingBefore(10);
        table.setWidths(new float[]{4f, 1.5f, 1.5f, 2f});

        Stream.of("Item", "Qty", "Price", "Total").forEach(col -> {
            PdfPCell cell = new PdfPCell(new Phrase(col, boldFont));
            cell.setBackgroundColor(new Color(230, 230, 250));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setPadding(8);
            table.addCell(cell);
        });

        // Product Rows
        for (OrderItemResponseDTO item : order.getItems()) {
            double unitPrice = item.getPriceAtPurchase() / item.getQuantity();

            PdfPCell productCell = new PdfPCell(new Phrase(item.getProductName(), normalFont));
            PdfPCell qtyCell = new PdfPCell(new Phrase(String.valueOf(item.getQuantity()), normalFont));
            PdfPCell priceCell = new PdfPCell(new Phrase(String.format("₹ %.2f", unitPrice), normalFont));
            PdfPCell totalCell = new PdfPCell(new Phrase(String.format("₹ %.2f", item.getPriceAtPurchase()), normalFont));

            Stream.of(productCell, qtyCell, priceCell, totalCell).forEach(c -> {
                c.setHorizontalAlignment(Element.ALIGN_CENTER);
                c.setPadding(6);
                table.addCell(c);
            });
        }

        // Total row
        PdfPCell totalLabel = new PdfPCell(new Phrase("Grand Total", boldFont));
        totalLabel.setColspan(3);
        totalLabel.setHorizontalAlignment(Element.ALIGN_RIGHT);
        totalLabel.setPadding(8);
        table.addCell(totalLabel);

        PdfPCell totalValue = new PdfPCell(new Phrase("₹ " + String.format("%.2f", order.getTotalAmount()), boldFont));
        totalValue.setHorizontalAlignment(Element.ALIGN_CENTER);
        totalValue.setPadding(8);
        table.addCell(totalValue);

        document.add(table);
        document.close();

        return out.toByteArray();
    }
}
