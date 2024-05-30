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
import static rc.so.util.Engine.insertTR;
import static rc.so.util.Utility.formatMysqltoDisplay;
import static rc.so.util.Utility.generaId;
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
import static org.apache.poi.ss.usermodel.BorderStyle.THIN;
import org.apache.poi.ss.usermodel.Cell;
import static org.apache.poi.ss.usermodel.HorizontalAlignment.LEFT;
import static org.apache.poi.ss.usermodel.HorizontalAlignment.RIGHT;
import org.apache.poi.ss.usermodel.Row;

/**
 *
 * @author vcrugliano/fplacanica
 */
public class InternalTransferList {

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
    public static final float[] columnWidths2bis = new float[]{60f};

    /**
     *
     */
    public static final float[] columnWidths2tri = new float[]{5f, 5f, 5f, 10f, 10f, 5f, 5f, 25f};

    /**
     *
     */
    public static float[] columnWidths2 = null;
    final String intestazionePdf = "Internal Transfer List";
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

    Font f0_bold, f1_bold, f2_bold, f1_normal, f2_normal, f3_normal, f3_bold, f4_bold, f5_normal, f5_bold, f6_normal, f6_bold;

    /**
     * Costructor
     */
    public InternalTransferList() {

        this.f0_bold = getFont(HELVETICA, WINANSI, 14.04f, BOLD);
        this.f1_bold = getFont(HELVETICA, WINANSI, 9.96f, BOLD);
        this.f2_bold = getFont(HELVETICA, WINANSI, 8f, BOLD);
        this.f3_bold = getFont(HELVETICA, WINANSI, 8f, BOLDITALIC);
        this.f4_bold = getFont(HELVETICA, WINANSI, 6.96f, BOLD);
        this.f1_normal = getFont(HELVETICA, WINANSI, 9.96f, NORMAL);
        this.f2_normal = getFont(HELVETICA, WINANSI, 8f, NORMAL);
        this.f3_normal = getFont(HELVETICA, WINANSI, 6.96f, NORMAL);
        this.f5_normal = getFont(HELVETICA, WINANSI, 6f, NORMAL);
        this.f5_bold = getFont(HELVETICA, WINANSI, 6f, BOLD);
        this.f6_normal = getFont(HELVETICA, WINANSI, 5.5f, NORMAL);
        this.f6_bold = getFont(HELVETICA, WINANSI, 5.5f, BOLD);

    }

    /**
     *
     * @param path
     * @param siq
     * @param colonne
     * @param datereport1
     * @param datereport2
     * @param colonne2
     * @param siq2
     * @return
     */
    public String receipt(String path, InternalTransferList_value siq, ArrayList<String> colonne, String datereport1, String datereport2,
            ArrayList<String> colonne2, NoChangeInternalTransferList_value siq2) {

        //  String outputfile = "InternalTransferList.pdf";
        columnWidths2 = new float[colonne.size()];

        for (int c = 0; c < colonne.size(); c++) {
            if (c == 0 || c == 1 || c == 4 || c == 5 || c == 7) {
                columnWidths2[c] = 5f;
            } else if (c == 2) {
                columnWidths2[c] = 15f;
            } else if (c == 3) {
                columnWidths2[c] = 10f;
            } else if (c == 6) {
                columnWidths2[c] = 10f;
            } else if (c == 8) {
                columnWidths2[c] = 20f;
            } else {
                columnWidths2[c] = 20f;
            }
        }

        try {
            File pdf = new File(path + generaId(50) + "InternalTransferList.pdf");
            Document document = new Document(A4.rotate(), 20, 20, 20, 20);
            try(OutputStream ou = new FileOutputStream(pdf)){
            PdfWriter wr = getInstance(document, ou);
            document.open();

            PdfPTable table = new PdfPTable(2);
            table.setWidths(columnWidths0);
            table.setWidthPercentage(100);

            Phrase phrase1 = new Phrase();
            phrase1.add(new Chunk(intestazionePdf + " From " + datereport1 + " To " + datereport2, f3_bold));
            PdfPCell cell1 = new PdfPCell(phrase1);
            cell1.setBorder(NO_BORDER);

            //Paragraph pa1 = new Paragraph(new Phrase("Selected Interval " + datereport1 + " "+datereport2, f2_normal));
            Paragraph pa1 = new Paragraph(new Phrase("", f2_normal));
            pa1.setAlignment(ALIGN_CENTER);
            PdfPCell cell2 = new PdfPCell(pa1);
            cell2.setBorder(NO_BORDER);
            cell2.setHorizontalAlignment(ALIGN_RIGHT);

            Phrase phrase3 = new Phrase();
//            phrase3.add(new Chunk("\n " + siq.getId_filiale() + " " + siq.getDe_filiale(), f3_normal));
            PdfPCell cell3 = new PdfPCell(phrase3);
            cell3.setBorder(NO_BORDER);
            Phrase phrase4 = new Phrase();

            phrase4.add(new Chunk("\n " + siq.getId_filiale() + " " + siq.getDe_filiale(), f5_normal));
            PdfPCell cell4 = new PdfPCell(phrase4);
            cell4.setBorder(NO_BORDER);
            cell3.setHorizontalAlignment(ALIGN_RIGHT);

            table.addCell(cell1);
            table.addCell(cell2);
            table.addCell(cell4);
            table.addCell(cell3);
            document.add(table);

            vuoto.setFont(f3_normal);
            document.add(vuoto);

            if (siq.getDati().size() > 0) {

                PdfPTable table2 = new PdfPTable(colonne.size());
                table2.setWidths(columnWidths2);
                table2.setWidthPercentage(100);

                PdfPCell[] list = new PdfPCell[colonne.size()];
                //mi scandisco le colonne
                for (int j = 0; j < colonne.size(); j++) {
                    Phrase phraset1 = new Phrase();
                    phraset1.add(new Chunk(colonne.get(j), f5_bold));
                    PdfPCell cellt1 = new PdfPCell(phraset1);
                    cellt1.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt1.setBorder(BOTTOM | TOP);

                    cellt1.setBackgroundColor(LIGHT_GRAY);
                    //cellt1.setBorder(Rectangle.BOTTOM);
                    cellt1.setBorderWidth(0.7f);
                    if (j == 4 || j == 3 || j == 8) {
                        cellt1.setHorizontalAlignment(ALIGN_LEFT);
                    }
                    list[j] = cellt1;

                }

                PdfPTable tablesezione = new PdfPTable(1);
                tablesezione.setWidths(columnWidths2bis);
                tablesezione.setWidthPercentage(100);

                Phrase phraset2 = new Phrase();
                phraset2.add(new Chunk("CHANGE", f2_bold));
                PdfPCell cellt2 = new PdfPCell(phraset2);
                cellt2.setHorizontalAlignment(ALIGN_LEFT);
                cellt2.setBorder(BOTTOM);
                tablesezione.addCell(cellt2);

                document.add(tablesezione);

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
                ArrayList<InternalTransferList_value> dati = siq.getDati();

                PdfPCell cell;
                Phrase phraset;
                PdfPCell cellt;

                table3 = new PdfPTable(colonne.size());
                table3.setWidths(columnWidths2);
                table3.setWidthPercentage(100);

                for (int i = 0; i < dati.size(); i++) {
                    InternalTransferList_value temp = dati.get(i);

                    ArrayList<String> datitemp = temp.getDati_string();

                    for (int n = 0; n < colonne.size(); n++) {

                        phraset = new Phrase();
                        if (n == 5 || n == 6) {
                            phraset.add(new Chunk(formatMysqltoDisplay(datitemp.get(n)), f2_normal));
                        } else {
                            phraset.add(new Chunk((datitemp.get(n)), f2_normal));
                        }
                        cellt = new PdfPCell(phraset);
                        cellt.setHorizontalAlignment(ALIGN_RIGHT);
                        cellt.setBorder(BOTTOM);
                        if (n == 4 || n == 3 || n == 8) {
                            cellt.setHorizontalAlignment(ALIGN_LEFT);
                        }
                        table3.addCell(cellt);

                    }

                }

                document.add(table3);
                document.add(vuoto);
                document.add(vuoto);
                document.add(vuoto);

            }

//            LineSeparator ls = new LineSeparator();
//            ls.setLineWidth(0.7f);
//            ls.setOffset(-1f);
//            document.add(ls);
            columnWidths2 = new float[colonne2.size()];

            for (int c = 0; c < colonne2.size(); c++) {
                if (c == 0 || c == 1 || c == 5 || c == 6) {
                    columnWidths2tri[c] = 5f;
                } else if (c == 2) {
                    columnWidths2tri[c] = 15f;
                } else if (c == 3) {
                    columnWidths2tri[c] = 10f;
                } else if (c == 7) {
                    columnWidths2tri[c] = 20f;
                } else if (c == 8 || c == 4) {
                    columnWidths2tri[c] = 20f;
                } else {
                    columnWidths2tri[c] = 20f;
                }

            }

            if (siq2.getDati().size() > 0) {

                PdfPTable table2 = new PdfPTable(colonne2.size());
                table2.setWidths(columnWidths2tri);
                table2.setWidthPercentage(100);

                PdfPCell[] list = new PdfPCell[colonne2.size()];
                //mi scandisco le colonne
                for (int j = 0; j < colonne2.size(); j++) {
                    Phrase phraset1 = new Phrase();
                    phraset1.add(new Chunk(colonne2.get(j), f5_bold));
                    PdfPCell cellt1 = new PdfPCell(phraset1);
                    cellt1.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt1.setBorder(BOTTOM | TOP);

                    cellt1.setBackgroundColor(LIGHT_GRAY);
                    //cellt1.setBorder(Rectangle.BOTTOM);
                    cellt1.setBorderWidth(0.7f);
                    if (j == 3 || j == 4 || j == 7) {
                        cellt1.setHorizontalAlignment(ALIGN_LEFT);
                    }
                    list[j] = cellt1;

                }

                PdfPTable tablesezione = new PdfPTable(1);
                tablesezione.setWidths(columnWidths2bis);
                tablesezione.setWidthPercentage(100);

                Phrase phraset = new Phrase();
                phraset.add(new Chunk("NO CHANGE", f2_bold));
                PdfPCell cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_LEFT);
                cellt.setBorder(BOTTOM);
                tablesezione.addCell(cellt);

                document.add(tablesezione);

                PdfPTable table3 = new PdfPTable(colonne2.size());
                table3.setWidths(columnWidths2tri);
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
                ArrayList<NoChangeInternalTransferList_value> dati2 = siq2.getDati();

                table3 = new PdfPTable(colonne2.size());
                table3.setWidths(columnWidths2tri);
                table3.setWidthPercentage(100);

                for (int i = 0; i < dati2.size(); i++) {
                    NoChangeInternalTransferList_value temp = dati2.get(i);
                    ArrayList<String> datitemp = temp.getDati_string();

                    for (int n = 0; n < colonne2.size(); n++) {

                        Phrase phraset2 = new Phrase();
                        phraset2.add(new Chunk((datitemp.get(n)), f2_normal));
                        PdfPCell cellt2 = new PdfPCell(phraset2);
                        cellt2.setHorizontalAlignment(ALIGN_RIGHT);
                        cellt2.setBorder(BOTTOM);
                        if (n == 3 || n == 4 || n == 7) {
                            cellt2.setHorizontalAlignment(ALIGN_LEFT);
                        }
                        table3.addCell(cellt2);

                    }

                }

                document.add(table3);

            }
//            
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
     * @param colonne2
     * @param siq2
     * @return
     */
    public String receiptexcel(String path, InternalTransferList_value siq, ArrayList<String> colonne, String datereport1, String datereport2,
            ArrayList<String> colonne2, NoChangeInternalTransferList_value siq2) {

        //  String outputfile = "InternalTransferList.pdf";
        columnWidths2 = new float[colonne.size()];

        for (int c = 0; c < colonne.size(); c++) {
            if (c == 0 || c == 1) {
                columnWidths2[c] = 12f;
            } else if (c == 2) {
                columnWidths2[c] = 18f;
            } else if (c == 3) {
                columnWidths2[c] = 25f;
            } else {
                columnWidths2[c] = 10f;
            }
        }

        try {
            File pdf = new File(path + generaId(50) + "InternalTransferList.xls");
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet("InternalTransferList");
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
            cl.setCellValue(intestazionePdf + " From " + datereport1 + " To " + datereport2);

//            Cell cl2 = rowP.createCell(6);
//            cl2.setCellStyle(style2);
//            cl2.setCellValue(datereport);
            Row row = sheet.createRow((short) 3);
            row.createCell(1).setCellValue(siq.getId_filiale() + " " + siq.getDe_filiale());

            int cntriga = 7;

            if (siq.getDati().size() > 0) {

                Row row66bis = sheet.createRow((short) cntriga);

                Cell cl55 = row66bis.createCell(1);
                cl55.setCellStyle(style3left);
                cl55.setCellValue("CHANGE");

                cntriga++;
                cntriga++;

                Row row66 = sheet.createRow((short) cntriga);

                //mi scandisco le colonne
                for (int j = 0; j < colonne.size(); j++) {
                    Cell cl5 = row66.createCell(j + 1);
                    cl5.setCellStyle(style3);
                    if (j == 3 || j == 4 || j == 8) {
                        cl5.setCellStyle(style3left);
                    }
                    cl5.setCellValue(colonne.get(j));

                }

                Row row77 = sheet.createRow((short) 5);

                ArrayList<InternalTransferList_value> dati = siq.getDati();

                for (int i = 0; i < dati.size(); i++) {

                    cntriga++;

                    Row row6 = sheet.createRow((short) cntriga);

                    InternalTransferList_value temp = (InternalTransferList_value) dati.get(i);

                    ArrayList<String> datitemp = temp.getDati_string();

                    for (int n = 0; n < colonne.size(); n++) {

                        Cell f1 = row6.createCell(n + 1);
                        f1.setCellStyle(style4);
                        if (n == 3 || n == 4 || n == 8) {
                            f1.setCellStyle(style4left);
                        }
                        if (n == 5 || n == 6) {
                            f1.setCellValue(formatMysqltoDisplay(datitemp.get(n)));
                        } else {
                            f1.setCellValue((datitemp.get(n)));
                        }

                    }

                }

                cntriga++;
                cntriga++;
                cntriga++;
                cntriga++;

            }

//            LineSeparator ls = new LineSeparator();
//            ls.setLineWidth(0.7f);
//            ls.setOffset(-1f);
//            document.add(ls);
            columnWidths2 = new float[colonne2.size()];

            for (int c = 0; c < colonne2.size(); c++) {
                if (c == 0 || c == 1) {
                    columnWidths2[c] = 8f;
                } else if (c == colonne2.size() - 2 || c == colonne2.size() - 1 || c == colonne2.size() - 3) {
                    columnWidths2[c] = 8f;
                } else {
                    columnWidths2[c] = 20f;
                }

            }

            if (siq2.getDati().size() > 0) {

                Row row66bis = sheet.createRow((short) cntriga);

                Cell cl55 = row66bis.createCell(1);
                cl55.setCellStyle(style3left);
                cl55.setCellValue("NO CHANGE");

                cntriga++;
                cntriga++;

                cntriga++;
                Row row10 = sheet.createRow((short) cntriga);

                //mi scandisco le colonne
                for (int j = 0; j < colonne2.size(); j++) {
                    Cell f6 = row10.createCell(j + 1);
                    f6.setCellStyle(style3);
                    if (j == 3 || j == 4 || j == 7) {
                        f6.setCellStyle(style3left);
                    }
                    f6.setCellValue(colonne2.get(j));

                }

                cntriga++;

                ArrayList<NoChangeInternalTransferList_value> dati2 = siq2.getDati();

                for (int i = 0; i < dati2.size(); i++) {

                    cntriga++;
                    Row row11 = sheet.createRow((short) cntriga);
                    NoChangeInternalTransferList_value temp = (NoChangeInternalTransferList_value) dati2.get(i);
                    ArrayList<String> datitemp = temp.getDati_string();

                    for (int n = 0; n < colonne2.size(); n++) {

                        Cell f7 = row11.createCell(n + 1);
                        f7.setCellStyle(style4);
                        if (n == 3 || n == 4 || n == 7) {
                            f7.setCellStyle(style4left);
                        }
                        f7.setCellValue((datitemp.get(n)));

                    }

                }

            }
//            
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
