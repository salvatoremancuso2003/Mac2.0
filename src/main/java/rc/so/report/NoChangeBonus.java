/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rc.so.report;

import static com.itextpdf.text.BaseColor.LIGHT_GRAY;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import static com.itextpdf.text.Element.ALIGN_LEFT;
import static com.itextpdf.text.Element.ALIGN_RIGHT;
import com.itextpdf.text.Font;
import static com.itextpdf.text.Font.BOLD;
import static com.itextpdf.text.Font.BOLDITALIC;
import static com.itextpdf.text.Font.NORMAL;
import static com.itextpdf.text.FontFactory.getFont;
import static com.itextpdf.text.PageSize.A4;
import com.itextpdf.text.Phrase;
import static com.itextpdf.text.Rectangle.BOTTOM;
import static com.itextpdf.text.Rectangle.NO_BORDER;
import static com.itextpdf.text.Rectangle.TOP;
import static com.itextpdf.text.pdf.BaseFont.HELVETICA;
import static com.itextpdf.text.pdf.BaseFont.WINANSI;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import static com.itextpdf.text.pdf.PdfWriter.getInstance;
import com.itextpdf.text.pdf.draw.LineSeparator;
import rc.so.entity.Branch;
import static rc.so.util.Engine.formatBankBranch;
import static rc.so.util.Engine.insertTR;
import static rc.so.util.Utility.formatMysqltoDisplay;
import static rc.so.util.Utility.generaId;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import static java.lang.Integer.parseInt;
import static java.lang.String.valueOf;
import static java.lang.Thread.currentThread;
import java.util.ArrayList;
import static org.apache.commons.codec.binary.Base64.encodeBase64;
import static org.apache.commons.io.FileUtils.readFileToByteArray;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import static org.apache.poi.hssf.usermodel.HSSFFont.FONT_ARIAL;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import static org.apache.poi.ss.usermodel.BorderStyle.THIN;
import static org.apache.poi.ss.usermodel.HorizontalAlignment.LEFT;
import static org.apache.poi.ss.usermodel.HorizontalAlignment.RIGHT;

/**
 *
 * @author vcrugliano
 */
public class NoChangeBonus {

    //column

    /**
     *
     */
    public static final float[] columnWidths0 = new float[]{70f, 30f};

    /**
     *
     */
    public static final float[] columnWidths1 = new float[]{60f, 30f};

    /**
     *
     */
    public static float[] columnWidths2 = null;
    final String intestazionePdf = "No Change Bonus";
    Phrase vuoto = new Phrase("\n");

    //resource
//    public static final String logo = "web/resource/logocl.png";
    //other

    /**
     *
     */
    public static final String br = "\n";

    /**
     *
     */
    public static final String blank = " ";

    Font f0_bold, f1_bold, f2_bold, f1_normal, f2_normal, f3_normal, f3_bold, f4_bold;

    /**
     * Costructor
     */
    public NoChangeBonus() {

        this.f0_bold = getFont(HELVETICA, WINANSI, 14.04f, BOLD);
        this.f1_bold = getFont(HELVETICA, WINANSI, 9.96f, BOLD);
        this.f2_bold = getFont(HELVETICA, WINANSI, 8f, BOLD);
        this.f3_bold = getFont(HELVETICA, WINANSI, 8f, BOLDITALIC);
        this.f4_bold = getFont(HELVETICA, WINANSI, 6.96f, BOLD);
        this.f1_normal = getFont(HELVETICA, WINANSI, 9.96f, NORMAL);
        this.f2_normal = getFont(HELVETICA, WINANSI, 8f, NORMAL);
        this.f3_normal = getFont(HELVETICA, WINANSI, 6.96f, NORMAL);

    }

    /**
     *
     * @param path
     * @param siq
     * @param colonne
     * @param datereport1
     * @param datereport2
     * @param br1
     * @param allbr
     * @return
     */
    public String receipt(String path, NoChangeBonus_value siq, ArrayList<String> colonne, String datereport1, String datereport2, ArrayList<String> br1, ArrayList<Branch> allbr) {

//        String outputfile = "NoChangeBonus.pdf";
        columnWidths2 = new float[colonne.size()];

        for (int c = 0; c < colonne.size(); c++) {
            columnWidths2[c] = 10f;
        }

        try {
            File pdf = new File(path + generaId(50) + "NoChangeBonus.pdf");
            Document document = new Document(A4.rotate(), 20, 20, 20, 20);
            try(OutputStream ou = new FileOutputStream(pdf)){
            PdfWriter wr = getInstance(document, ou);
            document.open();

            PdfPTable table = new PdfPTable(1);
            //table.setWidths(columnWidths0);
            table.setWidthPercentage(100);

            Phrase phrase1 = new Phrase();
            phrase1.add(new Chunk(intestazionePdf + " from " + datereport1 + " to " + datereport2, f3_bold));
            PdfPCell cell1 = new PdfPCell(phrase1);
            cell1.setBorder(NO_BORDER);

            // Paragraph pa1 = new Paragraph(new Phrase("Periodo " + datereport1, f2_normal));
//            Paragraph pa1 = new Paragraph(new Phrase("", f2_normal));
//            pa1.setAlignment(Element.ALIGN_CENTER);
//            PdfPCell cell2 = new PdfPCell(pa1);
//            cell2.setBorder(Rectangle.NO_BORDER);
//            cell2.setHorizontalAlignment(Element.ALIGN_RIGHT);
            Phrase phrase3 = new Phrase();

            String defil = "";
            for (int x = 0; x < br1.size(); x++) {
                defil += formatBankBranch(br1.get(x), "BR", null, allbr, null) + ";";
            }

            phrase3.add(new Chunk(defil, f3_normal));

            PdfPCell cell3 = new PdfPCell(phrase3);
            cell3.setBorder(NO_BORDER);

//            Phrase phrase4 = new Phrase();
//            phrase4.add(new Chunk("", f3_normal));
//            PdfPCell cell4 = new PdfPCell(phrase4);
//            cell4.setBorder(Rectangle.NO_BORDER);
            table.addCell(cell1);
//            table.addCell(cell2);
            table.addCell(cell3);
//            table.addCell(cell4);
            document.add(table);

            vuoto.setFont(f3_normal);
            document.add(vuoto);

            PdfPTable table2 = new PdfPTable(colonne.size());
            table2.setWidths(columnWidths2);
            table2.setWidthPercentage(100);

            PdfPCell[] list = new PdfPCell[colonne.size()];
            //mi scandisco le colonne
            for (int j = 0; j < colonne.size(); j++) {
                Phrase phraset1 = new Phrase();
                phraset1.add(new Chunk(colonne.get(j), f4_bold));
                PdfPCell cellt1 = new PdfPCell(phraset1);
                cellt1.setHorizontalAlignment(ALIGN_RIGHT);
                cellt1.setBorder(BOTTOM | TOP);
                cellt1.setFixedHeight(20f);
                cellt1.setBackgroundColor(LIGHT_GRAY);
                //cellt1.setBorder(Rectangle.BOTTOM);
                cellt1.setBorderWidth(0.7f);
                if (j == 0) {
                    cellt1.setHorizontalAlignment(ALIGN_LEFT);
                }
                list[j] = cellt1;

            }

            PdfPTable table3 = new PdfPTable(colonne.size());
            table3.setWidths(columnWidths2);
            table3.setWidthPercentage(100);

            for (PdfPCell list1 : list) {
                PdfPCell temp = (PdfPCell) (list1);
                table3.addCell(temp);
            }

            document.add(table3);

            LineSeparator sep = new LineSeparator();
            sep.setOffset(-2);
            sep.setLineWidth((float) 0.5);

            //  document.add(table2);
            //  document.add(sep);
            ArrayList<NoChangeBonus_value> datiTOT = siq.getDati();

            PdfPCell cell;
            Phrase phraset;
            PdfPCell cellt;

            table3 = new PdfPTable(colonne.size());
            table3.setWidths(columnWidths2);
            table3.setWidthPercentage(100);

            ArrayList<String[]> tot = new ArrayList<>();

            for (int i = 0; i < datiTOT.size(); i++) {
                NoChangeBonus_value temp = datiTOT.get(i);
                ArrayList<String> datitemp = temp.getDati_string();
                phraset = new Phrase();
                phraset.add(new Chunk(temp.getUser(), f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_LEFT);
                cellt.setBorder(BOTTOM);
                table3.addCell(cellt);

                for (int n = 0; n < colonne.size() - 1; n++) {
                    String[] t = {valueOf(n), datitemp.get(n)};
                    tot.add(t);
                    phraset = new Phrase();
                    phraset.add(new Chunk(formatMysqltoDisplay(datitemp.get(n)), f3_normal));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setBorder(BOTTOM);
                    table3.addCell(cellt);
                }

            }

            document.add(table3);
//          

            // linea totali
            PdfPTable table4 = new PdfPTable(colonne.size());
            table4.setWidths(columnWidths2);
            table4.setWidthPercentage(100);

            //linea totale per categoria
            LineSeparator ls = new LineSeparator();
            ls.setLineWidth(0.7f);
            document.add(ls);

            phraset = new Phrase();
            phraset.add(new Chunk("Total", f4_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_LEFT);
            cellt.setBorder(BOTTOM);
            table4.addCell(cellt);

            for (int n = 0; n < colonne.size() - 1; n++) {
                int tt = 0;
                for (int x = 0; x < tot.size(); x++) {
                    if ((n + "").equals(tot.get(x)[0])) {
                        tt = tt + (parseInt(tot.get(x)[1]));
                    }
                }

                phraset = new Phrase();
                phraset.add(new Chunk(tt + "", f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);

            }

            document.add(table4);
            ls.setLineWidth(0.7f);
            document.add(ls);
            //fine totali

            document.close();
            wr.close();
            ou.close();
            String base64 = new String(encodeBase64(readFileToByteArray(pdf)));
            pdf.delete();
            return base64;
        }
        } catch (IOException | DocumentException ex) {
            insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
        }
        return null;
    }

    /**
     *
     * @param path
     * @param siq
     * @param colonne
     * @param datereport1
     * @param datereport2
     * @param br1
     * @param allbr
     * @return
     */
    public String receiptexcel(String path, NoChangeBonus_value siq, ArrayList<String> colonne, String datereport1, String datereport2, ArrayList<String> br1, ArrayList<Branch> allbr) {

//        String outputfile = "NoChangeBonus.pdf";
        columnWidths2 = new float[colonne.size()];

        for (int c = 0; c < colonne.size(); c++) {
            columnWidths2[c] = 10f;
        }

        try {
            File pdf = new File(path + generaId(50) + "NoChangeBonus.xls");
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet("NoChangeBonus");
            //CREAZIONE FONT
            HSSFFont font = workbook.createFont();
            font.setFontName(FONT_ARIAL);
            font.setFontHeightInPoints((short) 12);
            font.setBold(true);

            HSSFCellStyle style1 = (HSSFCellStyle) workbook.createCellStyle();
            style1.setFont(font);

            HSSFFont font31 = workbook.createFont();
            font31.setFontName(FONT_ARIAL);
            font31.setFontHeightInPoints((short) 10);
            font31.setBold(true);

            HSSFCellStyle style1bis = (HSSFCellStyle) workbook.createCellStyle();
            style1bis.setFont(font31);
            style1bis.setAlignment(LEFT);
            style1bis.setBorderTop(THIN);
            style1bis.setBorderBottom(THIN);
            style1bis.setWrapText(true);

            HSSFFont font2 = workbook.createFont();
            font2.setFontName(FONT_ARIAL);
            font2.setFontHeightInPoints((short) 12);

            HSSFCellStyle style2 = (HSSFCellStyle) workbook.createCellStyle();
            style2.setFont(font2);

            HSSFFont font3 = workbook.createFont();
            font3.setFontName(FONT_ARIAL);
            font3.setFontHeightInPoints((short) 10);
            font3.setBold(true);

            HSSFCellStyle style3 = (HSSFCellStyle) workbook.createCellStyle();
            style3.setFont(font3);
            style3.setAlignment(RIGHT);
            style3.setBorderTop(THIN);
            style3.setBorderBottom(THIN);

            HSSFCellStyle style3left = (HSSFCellStyle) workbook.createCellStyle();
            style3left.setFont(font3);
            style3left.setAlignment(LEFT);
            style3left.setBorderTop(THIN);
            style3left.setBorderBottom(THIN);

            HSSFFont font4 = workbook.createFont();
            font4.setFontName(FONT_ARIAL);
            font4.setFontHeightInPoints((short) 10);

            HSSFCellStyle style4 = (HSSFCellStyle) workbook.createCellStyle();
            style4.setAlignment(RIGHT);
            style4.setBorderTop(THIN);
            style4.setBorderBottom(THIN);

            HSSFCellStyle style4left = (HSSFCellStyle) workbook.createCellStyle();
            style4left.setAlignment(LEFT);
            style4left.setBorderTop(THIN);
            style4left.setBorderBottom(THIN);

            Row rowP = sheet.createRow((short) 1);

            Cell cl = rowP.createCell(1);
            cl.setCellStyle(style1);
            cl.setCellValue(intestazionePdf + " from " + datereport1 + " to " + datereport2);

//            Cell cl2 = rowP.createCell(6);
//            cl2.setCellStyle(style2);
//            cl2.setCellValue(datereport1);
            String defil = "";
            for (int x = 0; x < br1.size(); x++) {
                defil += formatBankBranch(br1.get(x), "BR", null, allbr, null) + "; \n";
            }

            Row row = sheet.createRow((short) 3);
            Cell ccc = row.createCell(1);
            ccc.setCellStyle(style1bis);
            ccc.setCellValue(defil);

            Row row66 = sheet.createRow((short) 7);

            int cntriga = 10;

            //mi scandisco le colonne
            for (int j = 0; j < colonne.size(); j++) {
                Cell cl5 = row66.createCell(j + 1);
                cl5.setCellStyle(style3);
                if (j == 0) {
                    cl5.setCellStyle(style3left);
                }
                cl5.setCellValue(colonne.get(j));

            }

            //  document.add(table2);
            //  document.add(sep);
            ArrayList<NoChangeBonus_value> datiTOT = siq.getDati();

            ArrayList<String[]> tot = new ArrayList<>();

            for (int i = 0; i < datiTOT.size(); i++) {
                NoChangeBonus_value temp = (NoChangeBonus_value) datiTOT.get(i);
                ArrayList<String> datitemp = temp.getDati_string();
                cntriga++;
                Row row6 = sheet.createRow((short) cntriga);
                Cell f1bis = row6.createCell(1);
                f1bis.setCellStyle(style4left);
                f1bis.setCellValue(temp.getUser());

                for (int n = 0; n < colonne.size() - 1; n++) {
                    String[] t = {n + "", datitemp.get(n)};
                    tot.add(t);
                    Cell f2 = row6.createCell(n + 2);
                    f2.setCellStyle(style4);
                    f2.setCellValue(formatMysqltoDisplay(datitemp.get(n)));

                }

                cntriga++;

            }

            cntriga++;
            Row row7 = sheet.createRow((short) cntriga);
//          

            Cell f18 = row7.createCell(1);
            f18.setCellStyle(style3left);
            f18.setCellValue("Total");

            for (int n = 0; n < colonne.size() - 1; n++) {
                int tt = 0;
                for (int x = 0; x < tot.size(); x++) {
                    if ((n + "").equals(tot.get(x)[0])) {
                        tt = tt + (parseInt(tot.get(x)[1]));
                    }
                }

                Cell f19 = row7.createCell(n + 2);
                f19.setCellStyle(style3);
                f19.setCellValue(tt + "");

            }

            sheet.autoSizeColumn(0);
            sheet.autoSizeColumn(1);
            sheet.autoSizeColumn(2);
            sheet.autoSizeColumn(3);
            sheet.autoSizeColumn(4);
            sheet.autoSizeColumn(5);
            sheet.autoSizeColumn(6);
            sheet.autoSizeColumn(7);
            sheet.autoSizeColumn(8);
            sheet.autoSizeColumn(9);
            sheet.autoSizeColumn(10);
            sheet.autoSizeColumn(11);
            sheet.autoSizeColumn(12);
            sheet.autoSizeColumn(13);
            sheet.autoSizeColumn(14);

            try (FileOutputStream out = new FileOutputStream(pdf)) {
                workbook.write(out);
            }
            String base64 = new String(encodeBase64(readFileToByteArray(pdf)));
            pdf.delete();
            return base64;

        } catch (IOException ex) {
            insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
        }
        return null;
    }

}
