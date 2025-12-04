package util;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.collections.ObservableList;
import model.dto.CartItem;
import model.dto.Item;

import java.io.File;
import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class BillUtil {

    private static File newestBill;

    public static File generateBill(String billId, ObservableList<CartItem> items){
        try {

            // Create bills folder
            File folder = new File("bills");
            if (!folder.exists()) folder.mkdir();

            String filePath = "bills/BILL_" + billId + ".pdf";

            Document document = new Document(PageSize.A5);
            PdfWriter.getInstance(document, new FileOutputStream(filePath));

            document.open();

            // ----------- HEADER -----------
            Paragraph title = new Paragraph("FABRICO STYLES", new Font(Font.FontFamily.HELVETICA, 20, Font.BOLD));
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            document.add(new Paragraph(" "));
            document.add(new Paragraph("No.21, Central Road, Colombo 10", new Font(Font.FontFamily.HELVETICA, 11)));
            document.add(new Paragraph("Contact: 071-2345678"));
            document.add(new Paragraph("Bill ID: " + billId));
            document.add(new Paragraph("Date: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))));
            document.add(new Paragraph(" "));

            // ----------- TABLE -----------
            PdfPTable table = new PdfPTable(5);
            table.setWidthPercentage(100);
            table.setWidths(new float[]{8, 42, 10, 20, 20});

            addCell(table, "No", true);
            addCell(table, "Item", true);
            addCell(table, "QTY", true);
            addCell(table, "Price", true);
            addCell(table, "Total", true);

            int count = 1;
            double grandTotal = 0.0;

            for (CartItem item : items) {

                double lineTotal = item.getItem().getSellingPrice() * item.getItemQty();

                addCell(table, String.valueOf(count++), false);
                addCell(table, item.getItem().getName(), false);
                addCell(table, String.valueOf(item.getItemQty()), false);
                addCell(table, String.format("%.2f", item.getItem().getSellingPrice()), false);
                addCell(table, String.format("%.2f", lineTotal), false);

                grandTotal += lineTotal;
            }

            document.add(table);

            // ----------- TOTAL SECTION -----------
            document.add(new Paragraph("---------------------------------------------------------------------------------------"));

            Paragraph total = new Paragraph(
                    "Grand Total : Rs. " + String.format("%.2f", grandTotal),
                    new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD)
            );

            total.setAlignment(Element.ALIGN_RIGHT);
            document.add(total);

            document.add(new Paragraph(" "));

            Paragraph thankYou = new Paragraph("*** --- Thank You! Come Again --- ***", new Font(Font.FontFamily.COURIER, 10));
            thankYou.setAlignment(Element.ALIGN_CENTER);
            document.add(thankYou);

            document.close();

            File bill = new File(filePath);
            newestBill = bill;

            return bill;

        } catch (Exception e){
            return null;
        }
    }

    private static void addCell(PdfPTable table, String text, boolean isHeader) {
        PdfPCell cell = new PdfPCell(new Phrase(text, new Font(Font.FontFamily.HELVETICA, 11)));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setPadding(5);

        if (isHeader) {
            cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
            cell.setPhrase(new Phrase(text, new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD)));
        }

        table.addCell(cell);

    }

    private static File getNewestBill(){
        return newestBill;
    }
}
