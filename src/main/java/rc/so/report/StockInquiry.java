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
import static com.itextpdf.text.Element.ALIGN_CENTER;
import static com.itextpdf.text.Element.ALIGN_LEFT;
import static com.itextpdf.text.Element.ALIGN_RIGHT;
import com.itextpdf.text.Font;
import static com.itextpdf.text.Font.BOLD;
import static com.itextpdf.text.Font.BOLDITALIC;
import static com.itextpdf.text.Font.NORMAL;
import static com.itextpdf.text.FontFactory.getFont;
import static com.itextpdf.text.PageSize.A4;
import com.itextpdf.text.Paragraph;
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
import static rc.so.util.Constant.is_IT;
import static rc.so.util.Engine.insertTR;
import static rc.so.util.Utility.fd;
import static rc.so.util.Utility.formatMysqltoDisplay;
import static rc.so.util.Utility.generaId;
import static rc.so.util.Utility.roundDoubleandFormat;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
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
 * @author srotella
 */
public class StockInquiry {

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
    final String intestazionePdf = "Stock Inquiry";
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
    public StockInquiry() {

        this.f0_bold = getFont(HELVETICA, WINANSI, 14.04f, BOLD);
        this.f1_bold = getFont(HELVETICA, WINANSI, 9.96f, BOLD);
        this.f2_bold = getFont(HELVETICA, WINANSI, 8f, BOLD);
        this.f3_bold = getFont(HELVETICA, WINANSI, 8f, BOLDITALIC);
        this.f4_bold = getFont(HELVETICA, WINANSI, 6.96f, BOLDITALIC);
        this.f1_normal = getFont(HELVETICA, WINANSI, 9.96f, NORMAL);
        this.f2_normal = getFont(HELVETICA, WINANSI, 8f, NORMAL);
        this.f3_normal = getFont(HELVETICA, WINANSI, 6.96f, NORMAL);

    }

    /**
     *
     * @param al
     * @param parametro
     * @return
     */
    public String controllo(ArrayList<StockInquiry_value> al, String parametro) {
        double notes = 0;
        for (int i = 0; i < al.size(); i++) {
            if (al.get(i).getCurrency().equals(parametro)) {
                notes = notes + fd(al.get(i).getNotes());
            }
        }
        return notes + "";
    }

    /**
     *
     * @param path
     * @param al
     * @param colonne
     * @param datereport
     * @return
     */
    public String receipt(String path, ArrayList<StockInquiry_value> al, ArrayList<String> colonne, String datereport) {

//        String outputfile = "StockInquiry.pdf";
        columnWidths2 = new float[colonne.size()];

        for (int c = 0; c < colonne.size(); c++) {
            columnWidths2[c] = 10f;
        }

        try {
            File pdf = new File(path + generaId(50) + "StockInquiry.pdf");
            Document document = new Document(A4, 20, 20, 20, 20);
            try(OutputStream ou = new FileOutputStream(pdf)){
            PdfWriter wr = getInstance(document, ou);
            document.open();

            PdfPTable table = new PdfPTable(2);
            table.setWidths(columnWidths0);
            table.setWidthPercentage(100);

            Phrase phrase1 = new Phrase();
            phrase1.add(new Chunk(intestazionePdf + " " + datereport, f3_bold));
            PdfPCell cell1 = new PdfPCell(phrase1);
            cell1.setBorder(NO_BORDER);

            Paragraph pa1 = new Paragraph(new Phrase("", f2_normal));
            pa1.setAlignment(ALIGN_CENTER);
            PdfPCell cell2 = new PdfPCell(pa1);
            cell2.setBorder(NO_BORDER);
            cell2.setHorizontalAlignment(ALIGN_RIGHT);

            Phrase phrase3 = new Phrase();
            phrase3.add(new Chunk("\n " + al.get(0).getId_filiale() + " " + al.get(0).getDe_filiale(), f3_normal));
            PdfPCell cell3 = new PdfPCell(phrase3);
            cell3.setBorder(NO_BORDER);

            Phrase phrase4 = new Phrase();
            phrase4.add(new Chunk("", f3_normal));
            PdfPCell cell4 = new PdfPCell(phrase4);
            cell4.setBorder(NO_BORDER);

            table.addCell(cell1);
            table.addCell(cell2);
            table.addCell(cell3);
            table.addCell(cell4);
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
                if (j == 0 || j == 1) {
                    cellt1.setHorizontalAlignment(ALIGN_LEFT);
                }
                cellt1.setBorderWidth(0.7f);

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

            double sommavalori[] = new double[colonne.size() - 2];
            for (int k = 0; k < colonne.size() - 2; k++) {
                sommavalori[k] = 0;
            }

            for (int i = 0; i < al.size(); i++) {

                table2 = new PdfPTable(colonne.size());
                table2.setWidths(columnWidths2);
                table2.setWidthPercentage(100);

                StockInquiry_value actual = al.get(i);
//                StockInquiry_value prossimo=al.get(i+1);

                ArrayList<String> ttt = al.get(i).getDati_string();
                Phrase phraset111 = new Phrase();

                phraset111.add(new Chunk(actual.getTill(), f3_normal));
                PdfPCell cellt111 = new PdfPCell(phraset111);
                cellt111.setHorizontalAlignment(ALIGN_LEFT);
                cellt111.setBorder(BOTTOM);

                table2.addCell(cellt111);

                phraset111 = new Phrase();
                phraset111.add(new Chunk(actual.getCurrency(), f3_normal));
                cellt111 = new PdfPCell(phraset111);
                cellt111.setHorizontalAlignment(ALIGN_LEFT);
                cellt111.setBorder(BOTTOM);

                table2.addCell(cellt111);

                for (int k = 0; k < colonne.size() - 2; k++) {
                    phraset111 = new Phrase();
                    if (!is_IT && k == 1) {
                        phraset111.add(new Chunk("", f3_normal));
                    } else {
                        phraset111.add(new Chunk(formatMysqltoDisplay(ttt.get(k)), f3_normal));
                    }

                    cellt111 = new PdfPCell(phraset111);
                    cellt111.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt111.setBorder(BOTTOM);

                    table2.addCell(cellt111);

                    double d = (sommavalori[k]);
                    double d1 = fd(ttt.get(k));
                    double tot = d + d1;

                    sommavalori[k] = tot;
                }

                document.add(table2);

                if (i != al.size() - 1) {

                    table2 = new PdfPTable(colonne.size());
                    table2.setWidths(columnWidths2);
                    table2.setWidthPercentage(100);

                    if (!al.get(i).getCurrency().equals(al.get(i + 1).getCurrency())) {

                        table2 = new PdfPTable(colonne.size());
                        table2.setWidths(columnWidths2);
                        table2.setWidthPercentage(100);

                        phraset111 = new Phrase();

                        phraset111.add(new Chunk("", f4_bold));
                        cellt111 = new PdfPCell(phraset111);
                        cellt111.setHorizontalAlignment(ALIGN_LEFT);
                        cellt111.setBorder(BOTTOM);
                        cellt111.setBorderWidth(0.7f);

                        table2.addCell(cellt111);

                        phraset111 = new Phrase();
                        phraset111.add(new Chunk("", f4_bold));
                        cellt111 = new PdfPCell(phraset111);
                        cellt111.setHorizontalAlignment(ALIGN_LEFT);
                        cellt111.setBorder(BOTTOM);
                        cellt111.setBorderWidth(0.7f);

                        table2.addCell(cellt111);

                        for (int j = 0; j < sommavalori.length; j++) {

                            Phrase phraset11 = new Phrase();
                            if (!is_IT && j == 1) {
                                phraset111.add(new Chunk("", f3_normal));
                            } else {
                                phraset11.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(sommavalori[j], 2)), f4_bold));
                            }
                            PdfPCell cellt11 = new PdfPCell(phraset11);
                            cellt11.setHorizontalAlignment(ALIGN_RIGHT);
                            cellt11.setBorder(BOTTOM);
                            cellt11.setBorderWidth(0.7f);

                            table2.addCell(cellt11);

                        }

                        sommavalori = new double[colonne.size() - 2];
                        for (int k = 0; k < colonne.size() - 2; k++) {
                            sommavalori[k] = 0;
                        }

                        LineSeparator ls = new LineSeparator();
                        ls.setLineWidth(0.7f);

                        document.add(ls);

                        document.add(table2);

                    } else {
                        table2 = new PdfPTable(colonne.size());
                        table2.setWidths(columnWidths2);
                        table2.setWidthPercentage(100);

                        phraset111.add(new Chunk(al.get(i + 1).getTill(), f3_normal));
                        cellt111 = new PdfPCell(phraset111);
                        cellt111.setHorizontalAlignment(ALIGN_LEFT);
                        cellt111.setBorder(BOTTOM);

                        table2.addCell(cellt111);

                        phraset111 = new Phrase();
                        phraset111.add(new Chunk(al.get(i + 1).getCurrency(), f3_normal));
                        cellt111 = new PdfPCell(phraset111);
                        cellt111.setHorizontalAlignment(ALIGN_LEFT);
                        cellt111.setBorder(BOTTOM);

                        table2.addCell(cellt111);

                        for (int j = 0; j < sommavalori.length; j++) {

                            Phrase phraset11 = new Phrase();

                            if (!is_IT && j == 1) {
                                phraset111.add(new Chunk("", f3_normal));
                            } else {
                                phraset11.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(sommavalori[j], 2)), f4_bold));
                            }

                            PdfPCell cellt11 = new PdfPCell(phraset11);
                            cellt11.setHorizontalAlignment(ALIGN_RIGHT);
                            cellt11.setBorder(BOTTOM);
                            cellt11.setBorderWidth(0.7f);

                            table2.addCell(cellt11);

                        }

//                        sommavalori = new double[colonne.size() - 2];
//                        for (int k = 0; k < colonne.size() - 2; k++) {
//                            sommavalori[k] = 0;
//                        }
                        LineSeparator ls = new LineSeparator();
                        ls.setLineWidth(0.7f);

                        //document.add(ls);
                        //document.add(table2);
                    }
                } else {

                    table2 = new PdfPTable(colonne.size());
                    table2.setWidths(columnWidths2);
                    table2.setWidthPercentage(100);

                    //inserisco la somma delle colonne
                    phraset111 = new Phrase();

                    phraset111.add(new Chunk("", f4_bold));
                    cellt111 = new PdfPCell(phraset111);
                    cellt111.setHorizontalAlignment(ALIGN_LEFT);
                    cellt111.setBorder(BOTTOM);
                    cellt111.setBorderWidth(0.7f);

                    table2.addCell(cellt111);

                    phraset111 = new Phrase();
                    phraset111.add(new Chunk("", f4_bold));
                    cellt111 = new PdfPCell(phraset111);
                    cellt111.setHorizontalAlignment(ALIGN_LEFT);
                    cellt111.setBorder(BOTTOM);
                    cellt111.setBorderWidth(0.7f);

                    table2.addCell(cellt111);

                    for (int b = 0; b < sommavalori.length; b++) {

                        phraset111 = new Phrase();
                        if (!is_IT && b == 1) {
                            phraset111.add(new Chunk("", f4_bold));
                        } else {
                            phraset111.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(sommavalori[b], 2)), f4_bold));
                        }

                        PdfPCell cellt11 = new PdfPCell(phraset111);
                        cellt11.setHorizontalAlignment(ALIGN_RIGHT);
                        cellt11.setBorder(BOTTOM);
                        cellt11.setBorderWidth(0.7f);

                        table2.addCell(cellt11);


                    }

                    LineSeparator ls = new LineSeparator();
                    ls.setLineWidth(0.7f);
                    document.add(ls);

                    document.add(table2);

                }

            }

            //document.add(table2);
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
     * @param al
     * @param colonne
     * @param datereport
     * @return
     */
    public String receiptexcel(String path, ArrayList<StockInquiry_value> al, ArrayList<String> colonne, String datereport) {

//        String outputfile = "StockInquiry.pdf";
        try {
            File pdf = new File(path + generaId(50) + "StockInquiry.xls");
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet("StockInquiry");
            //CREAZIONE FONT
            HSSFFont font = workbook.createFont();
            font.setFontName(FONT_ARIAL);
            font.setFontHeightInPoints((short) 12);
            font.setBold(true);

            HSSFCellStyle style1 = (HSSFCellStyle) workbook.createCellStyle();
            style1.setFont(font);

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
            cl.setCellValue(intestazionePdf + " " + datereport);

//            Cell cl2 = rowP.createCell(6);
//            cl2.setCellStyle(style2);
//            cl2.setCellValue(datereport);
            Row row = sheet.createRow((short) 3);
            row.createCell(1).setCellValue(al.get(0).getId_filiale() + " " + al.get(0).getDe_filiale());

            // ArrayList<String> dati = siq.getDati();
            Row row66 = sheet.createRow((short) 5);

            int cntriga = 7;

            //mi scandisco le colonne
            for (int j = 0; j < colonne.size(); j++) {
                Cell cl5 = row66.createCell(j + 1);
                cl5.setCellStyle(style3);
                if (j == 0 || j == 1) {
                    cl5.setCellStyle(style3left);
                }
                cl5.setCellValue(colonne.get(j));

            }

            double sommavalori[] = new double[colonne.size() - 2];
            double sommavalorifine[] = new double[colonne.size() - 2];
            for (int k = 0; k < colonne.size() - 2; k++) {
                sommavalori[k] = 0;
            }

            for (int i = 0; i < al.size(); i++) {

                cntriga++;

                Row row6 = sheet.createRow(cntriga);

                ArrayList<String> ttt = al.get(i).getDati_string();

                Cell f1 = row6.createCell(1);
                f1.setCellStyle(style4left);
                f1.setCellValue((al.get(i)).getTill());

                Cell f2 = row6.createCell(2);
                f2.setCellStyle(style4left);
                f2.setCellValue((al.get(i)).getCurrency());

                for (int k = 0; k < colonne.size() - 2; k++) {

                    Cell f3 = row6.createCell(k + 3);
                    f3.setCellStyle(style4);
                    if (!is_IT && k == 1) {
                        f3.setCellValue("");
                    } else {
                        f3.setCellValue(formatMysqltoDisplay(ttt.get(k)));
                        
                    }
                    double d = (sommavalori[k]);
                    double d1 = fd(ttt.get(k));
                    double tot = d + d1;

                    sommavalori[k] = tot;
                    sommavalorifine[k] = tot;

                }

                if (i != al.size() - 1) {

                    cntriga++;

                    Row row777bis = sheet.createRow((short) cntriga);

                    if (!al.get(i).getCurrency().equals(al.get(i + 1).getCurrency())) {

                        for (int j = 0; j < sommavalori.length; j++) {

                            Cell f307 = row777bis.createCell(j + 3);
                            f307.setCellStyle(style3);
                            if (!is_IT && j == 1) {
                                f307.setCellValue("");
                            } else {
                                f307.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(sommavalori[j], 2)));
                            }
                        }
                        cntriga++;

                        sommavalori = new double[colonne.size() - 2];
                        for (int k = 0; k < colonne.size() - 2; k++) {
                            sommavalori[k] = 0;
                        }

                    } else {
//                       Cell f1bis = row777bis.createCell(1);
//                f1bis.setCellStyle(style4);
//                f1bis.setCellValue((al.get(i+1)).getTill());
//
//                Cell f2bis = row777bis.createCell(2);
//                f2bis.setCellStyle(style4);
//                f2bis.setCellValue((al.get(i+1)).getCurrency());
//                
//                
//
//                        for (int j = 0; j < sommavalori.length; j++) {
//
//                            Cell f307 = row777bis.createCell(j + 3);
//                            f307.setCellStyle(style3);
//                            f307.setCellValue(formatMysqltoDisplay(String.valueOf(sommavalori[j])));
//
//                        }

                    }
                } else {

                    cntriga++;

                    Row row8 = sheet.createRow((short) cntriga);
                    for (int b = 0; b < sommavalorifine.length; b++) {

                        // cntriga++;
                        Cell f307 = row8.createCell(b + 3);
                        f307.setCellStyle(style3);
                        if (!is_IT && b == 1) {
                            f307.setCellValue("");
                        } else {
                            f307.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(sommavalorifine[b], 2)));
                        }

                    }

                }

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
