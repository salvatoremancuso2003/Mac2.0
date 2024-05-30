/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rc.so.report;

import static com.itextpdf.text.BaseColor.LIGHT_GRAY;
import com.itextpdf.text.Chunk;
import static com.itextpdf.text.Chunk.TABBING;
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
import com.itextpdf.text.TabSettings;
import static com.itextpdf.text.pdf.BaseFont.HELVETICA;
import static com.itextpdf.text.pdf.BaseFont.WINANSI;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import static com.itextpdf.text.pdf.PdfWriter.getInstance;
import com.itextpdf.text.pdf.draw.LineSeparator;
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
import org.apache.poi.ss.util.CellRangeAddress;
import static org.apache.poi.ss.usermodel.BorderStyle.THIN;
import static org.apache.poi.ss.usermodel.HorizontalAlignment.CENTER;
import static org.apache.poi.ss.usermodel.HorizontalAlignment.LEFT;
import static org.apache.poi.ss.usermodel.HorizontalAlignment.RIGHT;

/**
 *
 * @author vcrugliano
 */
public class ToBranchingSheet {

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
    public static final float[] columnWidths3 = new float[]{18f, 15f, 19f, 5f, 5f, 25f};

    /**
     *
     */
    public static float[] columnWidths2 = null;
    final String intestazionePdf = "To Branching Sheet";
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
    public ToBranchingSheet() {

        this.f0_bold = getFont(HELVETICA, WINANSI, 14.04f, BOLD);
        this.f1_bold = getFont(HELVETICA, WINANSI, 9.00f, BOLD);
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
     * @param datereport
     * @param colonne2
     * @param dati2
     * @return
     */
    public String receipt(String path, ToBranchingSheet_value siq,
            ArrayList<String> colonne, String datereport,
            ArrayList<String> colonne2,
            ArrayList<NoChangeToBranchingSheet_value> dati2) {

        Phrase vuoto1 = new Phrase("\n");
        PdfPCell cellavuota = new PdfPCell(vuoto1);
        cellavuota.setBorder(NO_BORDER);

        //String outputfile = "ToBranchingSheet.pdf";
        columnWidths2 = new float[colonne.size()];

        for (int c = 0; c < colonne.size(); c++) {
            columnWidths2[c] = 10f;
        }

        try {
            File pdf = new File(path + generaId(50) + "ToBranchingSheet.pdf");
            Document document = new Document(A4, 20, 20, 20, 20);
            try(OutputStream ou = new FileOutputStream(pdf)){
            PdfWriter wr = getInstance(document, ou);
            document.open();

            PdfPTable table = new PdfPTable(2);
            table.setWidths(columnWidths0);
            table.setWidthPercentage(100);

            Phrase phrase1 = new Phrase();
            phrase1.add(new Chunk(intestazionePdf + " ", f0_bold));
            PdfPCell cell1 = new PdfPCell(phrase1);
            cell1.setBorder(NO_BORDER);

            Paragraph pa1 = new Paragraph(new Phrase("Transfer " + siq.getNumTransfer() + " " + datereport, f3_bold));
            pa1.setAlignment(ALIGN_CENTER);
            PdfPCell cell2 = new PdfPCell(pa1);
            cell2.setBorder(NO_BORDER);
            cell2.setHorizontalAlignment(ALIGN_RIGHT);

            Phrase phrase3 = new Phrase();
            phrase3.add(new Chunk(siq.getId_filiale() + " - " + siq.getDe_filiale(), f1_bold));
            PdfPCell cell3 = new PdfPCell(phrase3);
            cell3.setBorder(NO_BORDER);

            String ggg = " \n From \t\t : " + siq.getFromsafe() + " \n To \t\t :" + siq.getTobranch() + "\n User \t\t: " + siq.getPinuser();

            Phrase phrase4 = new Phrase();
            phrase4.add(new Chunk("\n From", f2_bold));
            phrase4.setTabSettings(new TabSettings(30f));
            phrase4.add(TABBING);
            phrase4.add(new Chunk(": " + siq.getFromsafe() + "\n To", f2_bold));
            phrase4.setTabSettings(new TabSettings(30f));
            phrase4.add(TABBING);
            phrase4.add(new Chunk(": " + siq.getTobranch() + "\n User", f2_bold));
            phrase4.setTabSettings(new TabSettings(30f));
            phrase4.add(TABBING);
            phrase4.add(new Chunk(": " + siq.getPinuser(), f2_bold));

            PdfPCell cell4 = new PdfPCell(phrase4);

            cell4.setBorder(NO_BORDER);

            table.addCell(cell1);
            table.addCell(cell2);
            table.addCell(cell3);
            table.addCell(cellavuota);
            table.addCell(cell4);
            table.addCell(cellavuota);
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
                    cellt1.setBorder(BOTTOM);

                    cellt1.setBackgroundColor(LIGHT_GRAY);
                    //cellt1.setBorder(Rectangle.BOTTOM);
                    cellt1.setBorderWidth(0.7f);
                    if (j == 0) {
                        cellt1.setHorizontalAlignment(ALIGN_LEFT);
                    }

                    list[j] = cellt1;

                }

                PdfPTable table6 = new PdfPTable(columnWidths3);
                table6.setWidthPercentage(100);

                for (int j = 0; j < 6; j++) {

                    if (j == 1) {
                        Phrase phraset1 = new Phrase();
                        phraset1.add(new Chunk("Notes", f5_bold));
                        PdfPCell cellt1 = new PdfPCell(phraset1);
                        cellt1.setHorizontalAlignment(ALIGN_CENTER);
                        cellt1.setBorder(TOP);
                        cellt1.setBackgroundColor(LIGHT_GRAY);
                        //cellt1.setBorder(Rectangle.BOTTOM);
                        cellt1.setBorderWidth(0.7f);
                        table6.addCell(cellt1);
                    } else if (j == 2) {
                        Phrase phraset1 = new Phrase();
                        phraset1.add(new Chunk("Euro TC / Travel Cheques", f5_bold));
                        PdfPCell cellt1 = new PdfPCell(phraset1);
                        cellt1.setHorizontalAlignment(ALIGN_RIGHT);
                        cellt1.setBorder(TOP);
                        cellt1.setBackgroundColor(LIGHT_GRAY);
                        //cellt1.setBorder(Rectangle.BOTTOM);
                        cellt1.setBorderWidth(0.7f);
                        table6.addCell(cellt1);
                    } else {
                        Phrase phraset1 = new Phrase();
                        phraset1.add(new Chunk("", f5_bold));
                        PdfPCell cellt1 = new PdfPCell(phraset1);
                        cellt1.setHorizontalAlignment(ALIGN_LEFT);
                        cellt1.setBorder(TOP);

                        cellt1.setBackgroundColor(LIGHT_GRAY);
                        //cellt1.setBorder(Rectangle.BOTTOM);
                        cellt1.setBorderWidth(0.7f);
                        table6.addCell(cellt1);
                    }
                }

                document.add(table6);

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
                ArrayList<ToBranchingSheet_value> dati = siq.getDati();

                PdfPCell cell;
                Phrase phraset;
                PdfPCell cellt;

                table3 = new PdfPTable(colonne.size());
                table3.setWidths(columnWidths2);
                table3.setWidthPercentage(100);

                double branchtotal = 0, spreadtotal = 0;

//                double num = 0.00;
                for (int i = 0; i < dati.size(); i++) {
                    ToBranchingSheet_value temp = dati.get(i);

                    ArrayList<String> datitemp = temp.getDati_string();

                    phraset = new Phrase();
                    phraset.add(new Chunk(temp.getCurrency(), f3_normal));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_LEFT);
                    cellt.setBorder(BOTTOM);
                    table3.addCell(cellt);
                    boolean perc = false;

                    for (int n = 1; n < colonne.size(); n++) {

                        switch (colonne.get(n)) {
                            case "Branch Total":
                                branchtotal += fd((datitemp.get(n)));
                                break;
                            case "Spread":
                                spreadtotal += fd((datitemp.get(n)));
                                break;
                            case "%":
                                //                            num = num + 1.00;
                                perc = true;
                                break;
                            default:
                                break;
                        }

                        phraset = new Phrase();
                        if (!perc) {
                            phraset.add(new Chunk(formatMysqltoDisplay(datitemp.get(n)), f3_normal));
                        } else {
                            phraset.add(new Chunk(formatMysqltoDisplay(datitemp.get(n)) + " %", f3_normal));
                        }
                        cellt = new PdfPCell(phraset);
                        cellt.setHorizontalAlignment(ALIGN_RIGHT);
                        cellt.setBorder(BOTTOM);
                        table3.addCell(cellt);

                    }

                }

                document.add(table3);

                LineSeparator ls = new LineSeparator();
                ls.setLineWidth(0.7f);
                ls.setOffset(-1f);
                document.add(ls);

                table3 = new PdfPTable(colonne.size());
                table3.setWidths(columnWidths2);
                table3.setWidthPercentage(100);

                phraset = new Phrase();
                phraset.add(new Chunk("", f1_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_LEFT);
                cellt.setBorder(BOTTOM);
                table3.addCell(cellt);

                for (int n = 1; n < colonne.size(); n++) {

                    if (n == 1) {
                        phraset = new Phrase();
                        phraset.add(new Chunk("Total", f4_bold));
                        cellt = new PdfPCell(phraset);
                        cellt.setHorizontalAlignment(ALIGN_RIGHT);
                        cellt.setBorder(BOTTOM);
                        table3.addCell(cellt);
                    } else if (colonne.get(n).equals("Branch Total")) {
                        phraset = new Phrase();
                        phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(branchtotal, 2)), f4_bold));
                        cellt = new PdfPCell(phraset);
                        cellt.setHorizontalAlignment(ALIGN_RIGHT);
                        cellt.setBorder(BOTTOM);
                        table3.addCell(cellt);

                    } else if (colonne.get(n).equals("Spread")) {
                        phraset = new Phrase();
                        phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(spreadtotal, 2)), f4_bold));
                        cellt = new PdfPCell(phraset);
                        cellt.setHorizontalAlignment(ALIGN_RIGHT);
                        cellt.setBorder(BOTTOM);
                        table3.addCell(cellt);

                    } else if (colonne.get(n).equals("%")) {
                        phraset = new Phrase();
                        double toperc = 0.0;
                        if (branchtotal != 0) {
                            toperc = (spreadtotal / branchtotal) * 100.00;
                        }
                        phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(toperc, 2)) + "%", f4_bold));
//                        if (num > 0) {
//                            phraset.add(new Chunk(formatMysqltoDisplay(Utility.roundDoubleandFormat(perctotal / num, 2)) + "%", f4_bold));
//                        } else {
//                            phraset.add(new Chunk(formatMysqltoDisplay(Utility.roundDoubleandFormat(perctotal, 2)) + "%", f4_bold));
//                        }
                        cellt = new PdfPCell(phraset);
                        cellt.setHorizontalAlignment(ALIGN_RIGHT);
                        cellt.setBorder(BOTTOM);
                        table3.addCell(cellt);

                    } else {
                        phraset = new Phrase();
                        phraset.add(new Chunk("", f3_normal));
                        cellt = new PdfPCell(phraset);
                        cellt.setHorizontalAlignment(ALIGN_RIGHT);
                        cellt.setBorder(BOTTOM);
                        table3.addCell(cellt);
                    }

                }

                document.add(table3);

            }

            columnWidths2 = new float[colonne2.size()];

            for (int c = 0; c < colonne2.size(); c++) {
                columnWidths2[c] = 10f;
            }

            if (dati2.size() > 0) {

                PdfPCell[] list = new PdfPCell[colonne2.size()];
                //mi scandisco le colonne
                for (int j = 0; j < colonne2.size(); j++) {
                    Phrase phraset1 = new Phrase();
                    phraset1.add(new Chunk(colonne2.get(j), f5_bold));
                    PdfPCell cellt1 = new PdfPCell(phraset1);
                    cellt1.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt1.setBorder(BOTTOM);

                    cellt1.setBackgroundColor(LIGHT_GRAY);
                    //cellt1.setBorder(Rectangle.BOTTOM);
                    cellt1.setBorderWidth(0.7f);
                    if (j == 0) {
                        cellt1.setHorizontalAlignment(ALIGN_LEFT);
                    }
                    list[j] = cellt1;

                }

                PdfPTable table3 = new PdfPTable(colonne2.size());
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

                table3 = new PdfPTable(colonne2.size());
                table3.setWidths(columnWidths2);
                table3.setWidthPercentage(100);

                float totQuantity = 0, total = 0;

                for (int i = 0; i < dati2.size(); i++) {
                    NoChangeToBranchingSheet_value temp = (NoChangeToBranchingSheet_value) dati2.get(i);

                    Phrase phraset = new Phrase();
                    phraset.add(new Chunk(temp.getCategory(), f3_normal));
                    PdfPCell cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_LEFT);
                    cellt.setBorder(BOTTOM);
                    table3.addCell(cellt);

                    phraset = new Phrase();
                    phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(fd(temp.getQuantity()), 0)), f3_normal));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setBorder(BOTTOM);
                    table3.addCell(cellt);

                    phraset = new Phrase();
                    phraset.add(new Chunk(formatMysqltoDisplay(temp.getAmount()), f3_normal));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setBorder(BOTTOM);
                    table3.addCell(cellt);

                    double d = fd(temp.getQuantity()) * fd(temp.getAmount());

                    phraset = new Phrase();
                    phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(d, 2)), f2_normal));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setBorder(BOTTOM);
                    table3.addCell(cellt);

                    totQuantity += fd(temp.getQuantity());
                    total += fd(temp.getQuantity()) * fd(temp.getAmount());

                }

                document.add(table3);

                LineSeparator ls = new LineSeparator();
                ls.setLineWidth(0.7f);
                //ls.setOffset(-1f);
                document.add(ls);

                table3 = new PdfPTable(colonne2.size());
                table3.setWidths(columnWidths2);
                table3.setWidthPercentage(100);

                Phrase phraset = new Phrase();
                phraset.add(new Chunk("Total", f4_bold));
                PdfPCell cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_LEFT);
                cellt.setBorder(BOTTOM);
                table3.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(totQuantity, 0)), f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table3.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk("", f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table3.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(total, 2)) + "", f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table3.addCell(cellt);

                document.add(table3);
                ls = new LineSeparator();
                ls.setLineWidth(0.7f);
                // ls.setOffset(-1f);
                document.add(ls);

            }

            document.add(vuoto1);
            document.add(new Paragraph("Notes:", f2_bold));
            document.add(new Paragraph(siq.getNote(), f3_normal));

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
     * @param datereport
     * @param colonne2
     * @param dati2
     * @return
     */
    public String receiptexcel(String path, ToBranchingSheet_value siq,
            ArrayList<String> colonne, String datereport,
            ArrayList<String> colonne2,
            ArrayList<NoChangeToBranchingSheet_value> dati2) {

        //String outputfile = "ToBranchingSheet.pdf";
        columnWidths2 = new float[colonne.size()];

        for (int c = 0; c < colonne.size(); c++) {
            columnWidths2[c] = 10f;
        }

        try {
            File pdf = new File(path + generaId(50) + "ToBranchingSheet.xls");
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet("ToBranchingSheet");
            //CREAZIONE FONT
            HSSFFont font = workbook.createFont();
            font.setFontName(FONT_ARIAL);
            font.setFontHeightInPoints((short) 16);
            font.setBold(true);

            HSSFCellStyle style1 = (HSSFCellStyle) workbook.createCellStyle();
            style1.setFont(font);

            HSSFFont font2 = workbook.createFont();
            font2.setFontName(FONT_ARIAL);
            font2.setFontHeightInPoints((short) 12);
            font2.setBold(true);

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

            HSSFCellStyle style3center = (HSSFCellStyle) workbook.createCellStyle();
            style3center.setFont(font3);
            style3center.setWrapText(true);
            style3center.setAlignment(CENTER);
            style3center.setBorderTop(THIN);
            style3center.setBorderBottom(THIN);

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
            cl.setCellValue(intestazionePdf);

            Cell cl32 = rowP.createCell(3);
            cl32.setCellStyle(style2);
            cl32.setCellValue("Transfer " + siq.getNumTransfer() + " " + datereport);

//           
            Row rowP3 = sheet.createRow((short) 3);

//            Cell cl2 = rowP3.createCell(1);
//            cl2.setCellStyle(style2);
//            cl2.setCellValue("Prepared by: " );
//             Cell cl21 = rowP3.createCell(1);
//            cl21.setCellStyle(style2);
//            cl21.setCellValue(siq.getId_filiale() + " " + siq.getDe_filiale());
            Row rowP4 = sheet.createRow((short) 5);

            Cell cl4 = rowP4.createCell(1);
            cl4.setCellStyle(style2);
            cl4.setCellValue("From : ");

            Cell cl42 = rowP4.createCell(2);
            cl42.setCellStyle(style2);
            cl42.setCellValue(siq.getFromsafe());

            Row rowP5 = sheet.createRow((short) 6);

            Cell cl55 = rowP5.createCell(1);
            cl55.setCellStyle(style2);
            cl55.setCellValue("To: ");

            Cell cl552 = rowP5.createCell(2);
            cl552.setCellStyle(style2);
            cl552.setCellValue(siq.getTobranch());

            Row rowP7 = sheet.createRow((short) 7);

            Cell cl7 = rowP7.createCell(1);
            cl7.setCellStyle(style2);
            cl7.setCellValue("User: ");

            Cell cl72 = rowP7.createCell(2);
            cl72.setCellStyle(style2);
            cl72.setCellValue(siq.getPinuser());

            Row row = sheet.createRow((short) 2);
            Cell c1l7 = row.createCell(1);
            c1l7.setCellStyle(style2);
            c1l7.setCellValue(siq.getId_filiale() + " - " + siq.getDe_filiale());

            Row row66 = sheet.createRow((short) 10);

            int cntriga = 11;

            if (siq.getDati().size() > 0) {

                //mi scandisco le colonne
                for (int j = 0; j < colonne.size(); j++) {
                    Cell cl5 = row66.createCell(j + 1);
                    cl5.setCellStyle(style3);
                    if (j == 0) {
                        cl5.setCellStyle(style3left);
                    }
                    cl5.setCellValue(colonne.get(j));

                }

                Row row77 = sheet.createRow((short) 9);

                row77.setHeight((short) 555);
                for (int j = 0; j < 6; j++) {

                    if (j == 1) {

                        Cell cl5 = row77.createCell(j + 1);
                        cl5.setCellStyle(style3center);
                        cl5.setCellValue("Notes");

                    } else if (j == 2) {
                        Cell cl5 = row77.createCell(j + 2);
                        cl5.setCellStyle(style3center);
                        cl5.setCellValue("Euro TC / Travel Cheques");
                    } else {
//               Phrase phraset1 = new Phrase();
//                phraset1.add(new Chunk("", f5_bold));
//                PdfPCell cellt1 = new PdfPCell(phraset1);
//                cellt1.setHorizontalAlignment(Element.ALIGN_LEFT);
//                cellt1.setBorder( Rectangle.TOP);
//
//                cellt1.setBackgroundColor(BaseColor.LIGHT_GRAY);
//                //cellt1.setBorder(Rectangle.BOTTOM);
//                cellt1.setBorderWidth(0.7f);
//                table6.addCell(cellt1);
                    }
                }

                CellRangeAddress cellRangeAddress = new CellRangeAddress(9, 9, 2, 3);
                sheet.addMergedRegion(cellRangeAddress);

                cellRangeAddress = new CellRangeAddress(9, 9, 4, 5);
                sheet.addMergedRegion(cellRangeAddress);

                ArrayList<ToBranchingSheet_value> dati = siq.getDati();

                double branchtotal = 0, spreadtotal = 0;

                for (int i = 0; i < dati.size(); i++) {
                    cntriga++;

                    Row row6 = sheet.createRow((short) cntriga);

                    ToBranchingSheet_value temp = dati.get(i);

                    ArrayList<String> datitemp = temp.getDati_string();

                    Cell f1 = row6.createCell(1);
                    f1.setCellStyle(style4left);
                    f1.setCellValue(temp.getCurrency());

                    boolean perc = false;

                    for (int n = 1; n < colonne.size(); n++) {

                        switch (colonne.get(n)) {
                            case "Branch Total":
                                branchtotal += fd((datitemp.get(n)));
                                break;
                            case "Spread":
                                spreadtotal += fd((datitemp.get(n)));
                                break;
                            case "%":
                                perc = true;
                                break;
                            default:
                                break;
                        }

                        Cell f2 = row6.createCell(n + 1);
                        f2.setCellStyle(style4);

                        if (!perc) {
                            f2.setCellValue(formatMysqltoDisplay(datitemp.get(n)));
                        } else {
                            f2.setCellValue(formatMysqltoDisplay(datitemp.get(n)) + " %");

                        }

                    }

                }

                cntriga++;
                cntriga++;

                Row row9 = sheet.createRow((short) cntriga);

                for (int n = 1; n < colonne.size(); n++) {

                    if (n == 1) {

                        Cell f7 = row9.createCell(n + 1);
                        f7.setCellStyle(style4);
                        f7.setCellValue("Total");

                    } else if (colonne.get(n).equals("Branch Total")) {

                        Cell f7 = row9.createCell(n + 1);
                        f7.setCellStyle(style4);
                        f7.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(branchtotal, 2)));

                    } else if (colonne.get(n).equals("Spread")) {

                        Cell f7 = row9.createCell(n + 1);
                        f7.setCellStyle(style4);
                        f7.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(spreadtotal, 2)));

                    } else if (colonne.get(n).equals("%")) {

                        Cell f7 = row9.createCell(n + 1);
                        f7.setCellStyle(style4);
                        double toperc = 0.0;
                        if (branchtotal != 0) {
                            toperc = (spreadtotal / branchtotal) * 100.00;
                        }

                        f7.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(toperc, 2)) + " %");

                    } else {

//                     Cell f7 = row9.createCell(n+1);
//                        f7.setCellStyle(style4);
//                        f7.setCellValue(formatMysqltoDisplay(String.valueOf(perctotal))+"%");
//                    
//                     phraset = new Phrase();
//                phraset.add(new Chunk("", f3_normal));
//                cellt = new PdfPCell(phraset);
//                cellt.setHorizontalAlignment(Element.ALIGN_LEFT);
//                cellt.setBorder(Rectangle.BOTTOM);
//                table3.addCell(cellt);
                    }

                }

            }

            columnWidths2 = new float[colonne2.size()];

            for (int c = 0; c < colonne2.size(); c++) {
                columnWidths2[c] = 10f;
            }

            if (dati2.size() > 0) {

                cntriga++;
                Row row10 = sheet.createRow((short) cntriga);

                //mi scandisco le colonne
                for (int j = 0; j < colonne2.size(); j++) {
                    Cell f6 = row10.createCell(j + 1);
                    f6.setCellStyle(style3);
                    if (j == 0) {
                        f6.setCellStyle(style3left);
                    }
                    f6.setCellValue(colonne2.get(j));

                }

                cntriga++;

                double totQuantity = 0, total = 0;

                for (int i = 0; i < dati2.size(); i++) {

                    cntriga++;
                    Row row11 = sheet.createRow((short) cntriga);

                    NoChangeToBranchingSheet_value temp = (NoChangeToBranchingSheet_value) dati2.get(i);

                    Cell f7 = row11.createCell(1);
                    f7.setCellStyle(style4left);
                    f7.setCellValue(temp.getCategory());

                    Cell f8 = row11.createCell(2);
                    f8.setCellStyle(style4);

                    f8.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(fd(temp.getQuantity()), 0)));

                    Cell f9 = row11.createCell(3);
                    f9.setCellStyle(style4);
                    f9.setCellValue(formatMysqltoDisplay(temp.getAmount()));

                    double d = fd(temp.getQuantity()) * fd(temp.getAmount());

                    Cell f10 = row11.createCell(4);
                    f10.setCellStyle(style4);
                    f10.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(d, 2)));

                    totQuantity += fd(temp.getQuantity());
                    total += fd(temp.getQuantity()) * fd(temp.getAmount());

                    cntriga++;
                    cntriga++;
                }

                cntriga++;
                Row row12 = sheet.createRow((short) cntriga);

                Cell f15 = row12.createCell(1);
                f15.setCellStyle(style3);
                f15.setCellValue("Total");

                Cell f16 = row12.createCell(2);
                f16.setCellStyle(style3);
                f16.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(totQuantity, 0)));

                Cell f17 = row12.createCell(4);
                f17.setCellStyle(style3);
                f17.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(total, 2)) + "");

            }

            cntriga++;
            cntriga++;
            Row row10 = sheet.createRow((short) cntriga);
            Cell f7 = row10.createCell(1);
            f7.setCellStyle(style3);
            f7.setCellValue("Notes:");
            Cell f71 = row10.createCell(2);
            f71.setCellStyle(style3left);
            f71.setCellValue(siq.getNote());
            if (siq.getAuto() != null && siq.getAuto().equals("M")) {
                cntriga++;
                cntriga++;
                Row row12 = sheet.createRow((short) cntriga);
                Cell f15 = row12.createCell(1);
                f15.setCellStyle(style3left);
                f15.setCellValue("OPERATION MANUAL");
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
            sheet.autoSizeColumn(15);
            sheet.autoSizeColumn(16);
            sheet.autoSizeColumn(17);
            sheet.autoSizeColumn(18);
            sheet.autoSizeColumn(19);

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
