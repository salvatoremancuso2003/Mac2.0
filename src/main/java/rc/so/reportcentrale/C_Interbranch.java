/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rc.so.reportcentrale;

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
import static rc.so.util.Constant.formatdataCell;
import static rc.so.util.Engine.insertTR;
import static rc.so.util.Utility.fd;
import static rc.so.util.Utility.ff;
import static rc.so.util.Utility.formatMysqltoDisplay;
import static rc.so.util.Utility.generaId;
import java.awt.Color;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import static java.lang.String.valueOf;
import static java.lang.Thread.currentThread;
import java.util.ArrayList;
import static org.apache.commons.codec.binary.Base64.encodeBase64;
import static org.apache.commons.io.FileUtils.readFileToByteArray;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import static org.apache.poi.hssf.usermodel.HSSFFont.FONT_ARIAL;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import static org.apache.poi.ss.usermodel.BorderStyle.THIN;
import org.apache.poi.ss.usermodel.Cell;
import static org.apache.poi.ss.usermodel.CellType.NUMERIC;
import static org.apache.poi.ss.usermodel.HorizontalAlignment.LEFT;
import static org.apache.poi.ss.usermodel.HorizontalAlignment.RIGHT;
import org.apache.poi.ss.usermodel.Row;

/**
 *
 * @author fplacanica
 */
public class C_Interbranch {

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
    public static float[] columnWidths2 = new float[]{30f, 30f, 25f, 25f, 30f, 30f, 30f, 30f, 30f, 30f};

    /**
     *
     */
    public static float[] columnWidths3 = new float[]{5f, 20f, 50f, 10f, 10f, 15f, 10f, 10f};

    /**
     *
     */
    public static float[] columnWidths4 = new float[]{30f, 20f, 15f, 15f, 10f, 15f};

    /**
     *
     */
    public static float[] columnWidths5 = new float[]{30f, 20f, 15f, 15f, 10f, 15f};

    /**
     *
     */
    public static float[] columnWidths6 = new float[]{30f, 20f, 15f, 15f, 10f, 15f};
    final String intestazionePdf = "Analysis Interbranch";
    Phrase vuoto = new Phrase("\n");

    Color c = new Color(23, 32, 43);

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
     ** Constructor
     */
    public C_Interbranch() {

        this.f0_bold = getFont(HELVETICA, WINANSI, 14.04f, BOLD);
        this.f1_bold = getFont(HELVETICA, WINANSI, 9.96f, BOLD);
        this.f2_bold = getFont(HELVETICA, WINANSI, 8f, BOLD);
        this.f3_bold = getFont(HELVETICA, WINANSI, 6f, BOLDITALIC);
        this.f4_bold = getFont(HELVETICA, WINANSI, 5f, BOLD);
        this.f1_normal = getFont(HELVETICA, WINANSI, 9.96f, NORMAL);
        this.f2_normal = getFont(HELVETICA, WINANSI, 8f, NORMAL);
        this.f3_normal = getFont(HELVETICA, WINANSI, 5f, NORMAL);

    }

    /**
     *
     * @param cmfb
     * @param colonne
     * @param firstTime
     * @param lastTime
     * @param document
     * @param showdifference
     * @return
     */
    public Document receipt(C_Interbranch_value cmfb, ArrayList<String> colonne, boolean firstTime, boolean lastTime, Document document, boolean showdifference) {

        try {

            if (firstTime) {
                PdfPTable table = new PdfPTable(2);
                table.setWidths(columnWidths0);
                table.setWidthPercentage(100);
                Phrase phrase1 = new Phrase();
                phrase1.add(new Chunk(intestazionePdf + " From " + cmfb.getDataDa() + " to " + cmfb.getDataA(), f3_bold));
                PdfPCell cell1 = new PdfPCell(phrase1);
                cell1.setBorder(NO_BORDER);
                Paragraph pa1 = new Paragraph(new Phrase("", f3_bold));
                pa1.setAlignment(ALIGN_RIGHT);
                PdfPCell cell2 = new PdfPCell(pa1);
                cell2.setBorder(NO_BORDER);
                cell2.setHorizontalAlignment(ALIGN_RIGHT);

                Phrase phrase4 = new Phrase();
                phrase4.add(new Chunk("", f3_normal));
                PdfPCell cell4 = new PdfPCell(phrase4);
                cell4.setBorder(NO_BORDER);
                table.addCell(cell1);
                table.addCell(cell2);
                table.addCell(cell4);
                document.add(table);
                vuoto.setFont(f3_normal);
                document.add(vuoto);

            }

            //  document.add(table2);
            // document.add(sep);
            ArrayList<C_Interbranch_value> dati = cmfb.getDati_2();
            Phrase phraset;
            PdfPCell cellt;
            PdfPTable table3;

            boolean ft = true;

            if (ft) {

                LineSeparator sep = new LineSeparator();
                sep.setOffset(-2);
                sep.setLineWidth((float) 0.5);

                PdfPTable table2 = new PdfPTable(colonne.size());
                table2.setWidths(columnWidths2);
                table2.setWidthPercentage(100);

                PdfPCell[] list = new PdfPCell[colonne.size()];
                //mi scandisco le colonne
                for (int c = 0; c < colonne.size(); c++) {
                    Phrase phraset1 = new Phrase();
                    phraset1.add(new Chunk(colonne.get(c), f4_bold));
                    PdfPCell cellt1 = new PdfPCell(phraset1);
                    cellt1.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt1.setBorder(BOTTOM | TOP);
                    cellt1.setFixedHeight(20f);
                    cellt1.setBackgroundColor(LIGHT_GRAY);
                    cellt1.setBorderWidth(0.7f);
                    if (c == 0 || c == 1 || c == 5) {
                        cellt1.setHorizontalAlignment(ALIGN_LEFT);
                    }
                    list[c] = cellt1;
                }

                table3 = new PdfPTable(colonne.size());
                table3.setWidths(columnWidths2);
                table3.setWidthPercentage(100);

                for (PdfPCell list1 : list) {
                    PdfPCell temp = (PdfPCell) (list1);
                    table3.addCell(temp);
                }

                document.add(table3);
    
            }

            //sistemare lista
            ArrayList<C_Interbranch_value> listaTo = new ArrayList<>();
            ArrayList<C_Interbranch_value> listaFrom = new ArrayList<>();

            for (int j = 0; j < dati.size(); j++) {
                C_Interbranch_value temp = dati.get(j);

                if (temp.getType().equals("T")) {
                    listaTo.add(temp);
                } else {
                    listaTo.add(temp);
                }
            }

            //fine sistemazione lista
            for (int j = 0; j < listaTo.size(); j++) {

                C_Interbranch_value temp = (C_Interbranch_value) listaTo.get(j);

                String difference;

                LineSeparator sep = new LineSeparator();
                sep.setOffset(-2);
                sep.setLineWidth((float) 0.5);

                PdfPTable table4 = new PdfPTable(colonne.size());
                table4.setWidths(columnWidths2);
                table4.setWidthPercentage(100);

                if (!temp.getTotFrom().equals("-")) {
                    if (!temp.getTotTo().equals("-")) {
                        difference = (ff(temp.getTotTo()) - ff(temp.getTotFrom())) + "";
                    } else {
                        difference = temp.getTotFrom();
                    }
                } else {
                    difference = temp.getTotTo();
                }

                if (fd(difference) == 0 && showdifference) {
                    continue;
                }

                if (!(temp.getState().equalsIgnoreCase("ok"))) {

                    phraset = new Phrase();
                    phraset.add(new Chunk(temp.getState().toUpperCase(), f3_normal));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_LEFT);
                    cellt.setBorder(BOTTOM);
                    table4.addCell(cellt);
                } else {
                    phraset = new Phrase();
                    phraset.add(new Chunk(temp.getState().toUpperCase(), f3_normal));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_LEFT);
                    cellt.setBorder(BOTTOM);
                    table4.addCell(cellt);
                }

                phraset = new Phrase();
                phraset.add(new Chunk(temp.getDateTo(), f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_LEFT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(temp.getTotransfno(), f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(temp.getBranchTo(), f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay((temp.getTotTo())), f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);

                //scorro lista From
                phraset = new Phrase();
                phraset.add(new Chunk(temp.getDateFrom(), f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_LEFT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(temp.getBranchFrom(), f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(temp.getFromtransno(), f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(temp.getTotFrom()), f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(difference), f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);

                document.add(table4);

            }

//            for (int i = 0; i < listaFrom.size(); i++) {
//                C_Interbranch_value temp = (C_Interbranch_value) listaFrom.get(i);
//
//                LineSeparator sep = new LineSeparator();
//                sep.setOffset(-2);
//                sep.setLineWidth((float) 0.5);
//
//                PdfPTable table4 = new PdfPTable(colonne.size());
//                table4.setWidths(columnWidths2);
//                table4.setWidthPercentage(100);
//
//                phraset = new Phrase();
//                phraset.add(new Chunk("-", f3_normal));
//                cellt = new PdfPCell(phraset);
//                cellt.setHorizontalAlignment(Element.ALIGN_RIGHT);
//                cellt.setBorder(Rectangle.BOTTOM);
//                table4.addCell(cellt);
//
//                phraset = new Phrase();
//                phraset.add(new Chunk("-", f3_normal));
//                cellt = new PdfPCell(phraset);
//                cellt.setHorizontalAlignment(Element.ALIGN_RIGHT);
//                cellt.setBorder(Rectangle.BOTTOM);
//                table4.addCell(cellt);
//
//                phraset = new Phrase();
//                phraset.add(new Chunk("-", f3_normal));
//                cellt = new PdfPCell(phraset);
//                cellt.setHorizontalAlignment(Element.ALIGN_RIGHT);
//                cellt.setBorder(Rectangle.BOTTOM);
//                table4.addCell(cellt);
//
//                phraset = new Phrase();
//                phraset.add(new Chunk("-", f3_normal));
//                cellt = new PdfPCell(phraset);
//                cellt.setHorizontalAlignment(Element.ALIGN_RIGHT);
//                cellt.setBorder(Rectangle.BOTTOM);
//                table4.addCell(cellt);
//
//                phraset = new Phrase();
//                phraset.add(new Chunk(temp.getDateFrom(), f3_normal));
//                cellt = new PdfPCell(phraset);
//                cellt.setHorizontalAlignment(Element.ALIGN_RIGHT);
//                cellt.setBorder(Rectangle.BOTTOM);
//                table4.addCell(cellt);
//
//                phraset = new Phrase();
//                phraset.add(new Chunk(temp.getBranchFrom(), f3_normal));
//                cellt = new PdfPCell(phraset);
//                cellt.setHorizontalAlignment(Element.ALIGN_RIGHT);
//                cellt.setBorder(Rectangle.BOTTOM);
//                table4.addCell(cellt);
//
//                phraset = new Phrase();
//                phraset.add(new Chunk(temp.getTotFrom(), f3_normal));
//                cellt = new PdfPCell(phraset);
//                cellt.setHorizontalAlignment(Element.ALIGN_RIGHT);
//                cellt.setBorder(Rectangle.BOTTOM);
//                table4.addCell(cellt);
//
//                phraset = new Phrase();
//                phraset.add(new Chunk(temp.getTotFrom(), f3_normal));
//                cellt = new PdfPCell(phraset);
//                cellt.setHorizontalAlignment(Element.ALIGN_RIGHT);
//                cellt.setBorder(Rectangle.BOTTOM);
//                table4.addCell(cellt);
//
//                document.add(table4);
//            }
        } catch (DocumentException ex) {
            insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
        }
        return document;
    }

    /**
     *
     * @param path
     * @param pdf
     * @param alcolonne
     * @param showdifference
     * @return
     */
    public String main(String path, C_Interbranch_value pdf, ArrayList<String> alcolonne, boolean showdifference) {
        try {
            C_Interbranch nctl = new C_Interbranch();

            boolean firstTime;
            boolean lastTime;

            File pdffile = new File(path + generaId(50) + "C_Interbranch.pdf");
            try (OutputStream ou = new FileOutputStream(pdffile)) {
                Document document = new Document(A4.rotate(), 20, 20, 20, 20);
                PdfWriter wr = getInstance(document, ou);
                document.open();
                firstTime = true;
                lastTime = true;
                // boolean showdiff=true;
                document = nctl.receipt(pdf, alcolonne, firstTime, lastTime, document, showdifference);
                //chiusura documento
                document.close();
                wr.close();
            }
            String base64 = new String(encodeBase64(readFileToByteArray(pdffile)));
            pdffile.delete();
            return base64;
        } catch (DocumentException | IOException ex) {
            insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
        }
        return null;

    }

    /**
     *
     * @param cmfb
     * @param colonne
     * @param firstTime
     * @param lastTime
     * @param showdifference
     * @param sheet
     * @param cntriga
     * @param style1
     * @param style2
     * @param style3
     * @param style4
     * @param style3left
     * @param style4left
     * @param cellStylenum
     * @return
     */
    public int receiptexcel(C_Interbranch_value cmfb, ArrayList<String> colonne, boolean firstTime, boolean lastTime, boolean showdifference,
            HSSFSheet sheet, int cntriga, HSSFCellStyle style1, HSSFCellStyle style2, HSSFCellStyle style3, HSSFCellStyle style4, HSSFCellStyle style3left,
            HSSFCellStyle style4left, HSSFCellStyle cellStylenum) {

        try {

            if (firstTime) {
                Row rowP = sheet.createRow((short) cntriga);

                Cell cl = rowP.createCell(1);
                cl.setCellStyle(style1);
                cl.setCellValue(intestazionePdf + " From " + cmfb.getDataDa() + " to " + cmfb.getDataA());

                cntriga++;
                cntriga++;
                cntriga++;

            }

            //  document.add(table2);
            // document.add(sep);
            ArrayList<C_Interbranch_value> dati = cmfb.getDati_2();

            boolean ft = true;

            if (ft) {

                cntriga++;

                Row row66 = sheet.createRow((short) cntriga);
                //mi scandisco le colonne
                for (int c = 0; c < colonne.size(); c++) {
                    Cell cl8 = row66.createCell(c + 1);
                    cl8.setCellStyle(style3);
                    if (c == 0 || c == 1 || c == 5) {
                        cl8.setCellStyle(style3left);
                    }
                    cl8.setCellValue(colonne.get(c));
                }

            }

            //sistemare lista
            ArrayList<C_Interbranch_value> listaTo = new ArrayList<>();

            for (int j = 0; j < dati.size(); j++) {
                C_Interbranch_value temp = (C_Interbranch_value) dati.get(j);

                if (temp.getType().equals("T")) {
                    listaTo.add(temp);
                } else {
                    listaTo.add(temp);
                }
            }

            //fine sistemazione lista
            for (int j = 0; j < listaTo.size(); j++) {

                cntriga++;

                Row row6 = sheet.createRow((short) cntriga);

                C_Interbranch_value temp = (C_Interbranch_value) listaTo.get(j);

                String difference;

                if (!temp.getTotFrom().equals("-")) {
                    if (!temp.getTotTo().equals("-")) {
                        difference = (ff(temp.getTotTo()) - ff(temp.getTotFrom())) + "";
                    } else {
                        difference = temp.getTotFrom();
                    }
                } else {
                    difference = temp.getTotTo();
                }

                if (fd(difference) == 0 && showdifference) {
                    continue;
                }

                if (!(temp.getState().equalsIgnoreCase("ok"))) {

                    Cell f1bis = row6.createCell(1);
                    f1bis.setCellStyle(style4left);
                    f1bis.setCellValue(temp.getState().toUpperCase());

                } else {
                    Cell f1bis = row6.createCell(1);
                    f1bis.setCellStyle(style4left);
                    f1bis.setCellValue(temp.getState().toUpperCase());
                }

                Cell f2 = row6.createCell(2);
                f2.setCellStyle(style4left);
                f2.setCellValue(temp.getDateTo());

                Cell f3 = row6.createCell(3);
                f3.setCellStyle(style4);
                f3.setCellValue(temp.getTotransfno());

                Cell f4 = row6.createCell(4);
                f4.setCellStyle(style4);
                f4.setCellValue(temp.getBranchTo());

                Cell f5 = row6.createCell(5, NUMERIC);
                f5.setCellStyle(cellStylenum);
                f5.setCellValue(fd(temp.getTotTo()));

                Cell f6 = row6.createCell(6);
                f6.setCellStyle(style4left);
                f6.setCellValue(temp.getDateFrom());

                //scorro lista From
                Cell f7 = row6.createCell(7);
                f7.setCellStyle(style4);
                f7.setCellValue(temp.getBranchFrom());

                Cell f8 = row6.createCell(8);
                f8.setCellStyle(style4);
                f8.setCellValue(temp.getFromtransno());

                Cell f9 = row6.createCell(9, NUMERIC);
                f9.setCellStyle(cellStylenum);
                f9.setCellValue(fd(temp.getTotFrom()));

                Cell f10 = row6.createCell(10, NUMERIC);
                f10.setCellStyle(cellStylenum);
                f10.setCellValue(fd(difference));

            }
        } catch (Exception ex) {
            insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
        }
        cntriga++;
        cntriga++;
        cntriga++;
        cntriga++;
        return cntriga;
    }

    /**
     *
     * @param path
     * @param pdf
     * @param alcolonne
     * @param showdifference
     * @return
     */
    public String mainexcel(String path, C_Interbranch_value pdf, ArrayList<String> alcolonne, boolean showdifference) {
        try {
            File pdffile = new File(path + generaId(50) + "C_Interbranch.xls");
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet("C_Interbranch");

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

            HSSFCellStyle cellStylenum = (HSSFCellStyle) workbook.createCellStyle();
            HSSFDataFormat hssfDataFormat = (HSSFDataFormat) workbook.createDataFormat();
            cellStylenum.setDataFormat(hssfDataFormat.getFormat(formatdataCell));
            cellStylenum.setBorderTop(THIN);
            cellStylenum.setBorderBottom(THIN);
            cellStylenum.setBorderLeft(THIN);
            cellStylenum.setBorderRight(THIN);

            C_Interbranch nctl = new C_Interbranch();

            boolean firstTime;
            boolean lastTime;

            int nriga = 1;
            firstTime = true;
            lastTime = true;
            // boolean showdiff=true;
            nriga = nctl.receiptexcel(pdf, alcolonne, firstTime, lastTime, showdifference, sheet, nriga, style1, style2, style3, style4, style3left, style4left, cellStylenum);
            insertTR("I", "PDF", valueOf(nriga));

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

            try (FileOutputStream out = new FileOutputStream(pdffile)) {
                workbook.write(out);
            }
            String base64 = new String(encodeBase64(readFileToByteArray(pdffile)));
            pdffile.delete();
            return base64;
        } catch (Exception ex) {
            insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
        }
        return null;

    }

}
