package com.enigma.futsal_rental.util;

import com.enigma.futsal_rental.entity.Transaction;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.CMYKColor;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

@AllArgsConstructor
public class PDFGenerator {
    // List to hold all Transactions
    private List<Transaction> transactions;
    protected String dateReport;

    public void generate(HttpServletResponse response) throws DocumentException, IOException {

        Document document = new Document(PageSize.A4);

        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();

        Font fontTitle = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        fontTitle.setSize(18);
        fontTitle.isBold();

        Font fontDate = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        fontDate.setSize(14);
        fontDate.isBold();
        String[] splitDate = dateReport.split("-");
        String actualDate = splitDate[2] + "-" + splitDate[1] + "-" + splitDate[0];
        Paragraph paragraph = new Paragraph("Report Income In a Day", fontTitle);
        Paragraph date = new Paragraph(actualDate, fontDate);

        paragraph.setAlignment(Paragraph.ALIGN_CENTER);
        date.setAlignment(Paragraph.ALIGN_CENTER);

        document.add(paragraph);
        document.add(date);

        PdfPTable table = new PdfPTable(6);

        table.setWidthPercentage(100f);
        table.setWidths(new float[]{0.5f, 3f, 2f, 1.5f, 1.5f, 1.5f});
        table.setSpacingBefore(8);

        PdfPCell cell = new PdfPCell();

        cell.setBackgroundColor(CMYKColor.darkGray);
        cell.setPadding(5);

        Font font = FontFactory.getFont(FontFactory.TIMES_ROMAN);
        font.setColor(CMYKColor.WHITE);
        font.isBold();
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        cell.setPhrase(new Phrase("No", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("ID Transaction", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Field Type", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Price /hour", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Play Time", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Total Price", font));
        table.addCell(cell);
        font.isStandardFont();
        cell.setBackgroundColor(CMYKColor.WHITE);

        Font fontTable = FontFactory.getFont(FontFactory.TIMES_ROMAN);
        fontTable.setColor(CMYKColor.BLACK);
        fontTable.setSize(11f);
        Locale localeID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
        NumberFormat nf = NumberFormat.getNumberInstance(new Locale("in", "ID"));
        int i = 1;
        Long totalIncome = 0L;
        for (Transaction trx : transactions) {
            int playTime = (int) ((trx.getEndTime().getTime() - trx.getStartTime().getTime()) / 3600000);
            cell.setPhrase(new Phrase(String.valueOf(i++), fontTable));
            table.addCell(cell);
            cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
            cell.setPhrase(new Phrase(String.valueOf(trx.getIdTrx()), fontTable));
            table.addCell(cell);
            cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            cell.setPhrase(new Phrase(trx.getField().getType(), fontTable));
            table.addCell(cell);
            cell.setPhrase(new Phrase(formatRupiah.format(trx.getPrice() / playTime), fontTable));
            table.addCell(cell);
            String hourPlayTime = playTime + " hour";
            cell.setPhrase(new Phrase(String.valueOf(hourPlayTime), fontTable));
            table.addCell(cell);
            cell.setPhrase(new Phrase(formatRupiah.format(trx.getPrice()), fontTable));
            table.addCell(cell);
            totalIncome += trx.getPrice();
        }
        String totalIncomeString = "Total Income : " + formatRupiah.format(totalIncome);
        Paragraph paragraphIncome = new Paragraph(totalIncomeString, fontDate);
        paragraphIncome.setSpacingBefore(5f);
        document.add(table);
        document.add(paragraphIncome);

        document.close();
    }
}
