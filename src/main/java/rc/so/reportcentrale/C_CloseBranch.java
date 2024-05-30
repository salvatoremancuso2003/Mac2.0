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
import static rc.so.util.Utility.formatMysqltoDisplay;
import static rc.so.util.Utility.generaId;
import static rc.so.util.Utility.roundDouble;
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
public class C_CloseBranch {

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
    public static float[] columnWidths2 = new float[]{20f, 14f, 10f, 10f, 10f, 10f, 10f, 10f, 10f, 10f, 10f, 10f, 10f, 10f, 10f, 10f, 10f, 10f, 10f};

    /**
     *
     */
    public static float[] columnWidths3 = new float[]{5f, 15f, 10f, 10f, 10f, 10f, 8f, 8f, 8f};

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
    final String intestazionePdf = "Branches Closures";
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
     ** Constructor
     */
    public C_CloseBranch() {

        this.f0_bold = getFont(HELVETICA, WINANSI, 14.04f, BOLD);
        this.f1_bold = getFont(HELVETICA, WINANSI, 9.96f, BOLD);
        this.f2_bold = getFont(HELVETICA, WINANSI, 8f, BOLD);
        this.f3_bold = getFont(HELVETICA, WINANSI, 8f, BOLDITALIC);
        this.f4_bold = getFont(HELVETICA, WINANSI, 5f, BOLD);
        this.f1_normal = getFont(HELVETICA, WINANSI, 9.96f, NORMAL);
        this.f2_normal = getFont(HELVETICA, WINANSI, 8f, NORMAL);
        this.f3_normal = getFont(HELVETICA, WINANSI, 5f, NORMAL);

    }

    /**
     *
     * @param path
     * @param cb
     * @return
     */
    public String receipt(String path, C_CloseBranch_value cb) {
        double totPurch = 0, totSales = 0, totFx = 0, totPos = 0, totAccCC = 0, totcashadv = 0, totcashonprem = 0;
        ArrayList<String> tot = new ArrayList<>();
        C_CloseBranch_value fi = (C_CloseBranch_value) cb.getDati().get(0);
        ArrayList<String> colonne = fi.getAlcolonne();

        try {
            File pdffile = new File(path + generaId(50) + "C_CloseBranch.pdf");
            try (OutputStream ou = new FileOutputStream(pdffile)) {
                Document document = new Document(A4.rotate(), 20, 20, 20, 20);
                PdfWriter wr = getInstance(document, ou);
                document.open();
                PdfPTable table = new PdfPTable(2);
                table.setWidths(columnWidths0);
                table.setWidthPercentage(100);
                Phrase phrase1 = new Phrase();
                phrase1.add(new Chunk(intestazionePdf + "  " + cb.getData_da(), f3_bold));
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
                    if (j == 2) {
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
                // document.add(sep);
                ArrayList<C_CloseBranch_value> dati = cb.getDati();
                PdfPCell cell;
                Phrase phraset;
                PdfPCell cellt;
                for (int i = 0; i < dati.size(); i++) {
                    table3 = new PdfPTable(colonne.size());
                    table3.setWidths(columnWidths2);
                    table3.setWidthPercentage(100);
                    C_CloseBranch_value temp = dati.get(i);
                    totPurch += fd(temp.getPurch());
                    totSales += fd(temp.getSales());
                    totFx += fd(temp.getFx());
                    totPos += fd(temp.getPos());
                    totAccCC += fd(temp.getAccCC());
                    totcashadv += fd(temp.getCashadvance());
                    totcashonprem += fd(temp.getCashonprem());
                    phraset = new Phrase();
                    phraset.add(new Chunk(temp.getCodBranch() + " - " + temp.getDescrBranch(), f3_normal));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setBorder(NO_BORDER);
                    table3.addCell(cellt);
                    phraset = new Phrase();
                    phraset.add(new Chunk("", f3_normal));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setBorder(NO_BORDER);
                    table3.addCell(cellt);
                    phraset = new Phrase();
                    phraset.add(new Chunk(temp.gethClose(), f3_normal));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_LEFT);
                    cellt.setBorder(NO_BORDER);
                    table3.addCell(cellt);
                    phraset = new Phrase();
                    phraset.add(new Chunk(formatMysqltoDisplay(temp.getPurch()), f3_normal));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setBorder(NO_BORDER);
                    table3.addCell(cellt);
                    phraset = new Phrase();
                    phraset.add(new Chunk(formatMysqltoDisplay(temp.getSales()), f3_normal));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setBorder(NO_BORDER);
                    table3.addCell(cellt);
                    phraset = new Phrase();
                    phraset.add(new Chunk(formatMysqltoDisplay(temp.getCashadvance()), f3_normal));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setBorder(NO_BORDER);
                    table3.addCell(cellt);
                    phraset = new Phrase();
                    phraset.add(new Chunk(formatMysqltoDisplay(temp.getCashonprem()), f3_normal));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setBorder(NO_BORDER);
                    table3.addCell(cellt);
                    phraset = new Phrase();
                    phraset.add(new Chunk(formatMysqltoDisplay(temp.getFx()), f3_normal));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setBorder(NO_BORDER);
                    table3.addCell(cellt);
                    phraset = new Phrase();
                    phraset.add(new Chunk(formatMysqltoDisplay(temp.getPos()), f3_normal));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setBorder(NO_BORDER);
                    table3.addCell(cellt);
                    phraset = new Phrase();
                    phraset.add(new Chunk(formatMysqltoDisplay(temp.getAccCC()), f3_normal));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setBorder(NO_BORDER);
                    table3.addCell(cellt);
                    for (int x = 0; x < temp.getDati_string().size(); x++) {
                        phraset = new Phrase();
                        phraset.add(new Chunk(formatMysqltoDisplay(temp.getDati_string().get(x)), f3_normal));
                        cellt = new PdfPCell(phraset);
                        cellt.setHorizontalAlignment(ALIGN_RIGHT);
                        cellt.setBorder(NO_BORDER);
                        table3.addCell(cellt);
                        double t;
                        if (tot.isEmpty()) {
                            t = fd(temp.getDati_string().get(x));
                            tot.add(x, roundDoubleandFormat(t, 2));
                        } else {
                            try {
                                t = fd(tot.get(x)) + fd(temp.getDati_string().get(x));
                                tot.set(x, roundDoubleandFormat(t, 2));
                            } catch (IndexOutOfBoundsException e) {
                                t = fd(temp.getDati_string().get(x));
                                tot.add(x, roundDoubleandFormat(t, 2));
                            }
                        }
                    }
                    document.add(table3);
                }
                PdfPTable table4 = new PdfPTable(colonne.size());
                table4.setWidths(columnWidths2);
                table4.setWidthPercentage(100);
                //linea totale
                LineSeparator ls = new LineSeparator();
                ls.setLineWidth(0.7f);
                document.add(ls);
                phraset = new Phrase();
                phraset.add(new Chunk("", f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);
                phraset = new Phrase();
                phraset.add(new Chunk("", f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);
                phraset = new Phrase();
                phraset.add(new Chunk("Total", f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_LEFT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);
                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(totPurch, 2)), f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);
                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(totSales, 2)), f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);
                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(totcashadv, 2)), f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);
                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(totcashonprem, 2)), f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);
                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(totFx, 2)), f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);
                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(totPos, 2)), f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);
                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(totAccCC, 2)), f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);
                for (int x = 0; x < tot.size(); x++) {
                    phraset = new Phrase();
                    phraset.add(new Chunk(formatMysqltoDisplay(tot.get(x)), f4_bold));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setBorder(BOTTOM);
                    table4.addCell(cellt);
                }
                document.add(table4);
                //linea totale
                ls.setLineWidth(0.7f);
                document.add(ls);
                document.close();
                wr.close();
            }
            String base64 = new String(encodeBase64(readFileToByteArray(pdffile)));
            pdffile.delete();
            return base64;
        } catch (IOException | DocumentException ex) {
            insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
        }
        return null;
    }

    /**
     *
     * @param path
     * @param cb
     * @return
     */
    public String receiptexcel(String path, C_CloseBranch_value cb) {
        double totPurch = 0, totSales = 0, totFx = 0, totPos = 0, totAccCC = 0, totcashadv = 0, totcashonprem = 0;
        ArrayList<String> tot = new ArrayList<>();
        C_CloseBranch_value fi = (C_CloseBranch_value) cb.getDati().get(0);
        ArrayList<String> colonne = fi.getAlcolonne();

        try {
            File pdffile = new File(path + generaId(50) + "C_CloseBranch.xls");
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet("C_CloseBranch");

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

            HSSFCellStyle style3num = (HSSFCellStyle) workbook.createCellStyle();
            style3num.setFont(font3);
            style3num.setAlignment(RIGHT);
            style3num.setBorderTop(THIN);
            style3num.setBorderBottom(THIN);
            style3num.setDataFormat(hssfDataFormat.getFormat(formatdataCell));

            int cntriga = 1;

            Row rowP = sheet.createRow((short) cntriga);

            Cell cl = rowP.createCell(1);
            cl.setCellStyle(style1);
            cl.setCellValue(intestazionePdf + "  " + cb.getData_da());

            cntriga++;
            cntriga++;
            cntriga++;

            Row row66 = sheet.createRow((short) cntriga);

            //mi scandisco le colonne
            for (int j = 0; j < colonne.size(); j++) {
                Cell cl7 = row66.createCell(j + 1);
                cl7.setCellStyle(style3);
                if (j == 2) {
                    cl7.setCellStyle(style3left);
                }
                cl7.setCellValue(colonne.get(j));

            }

            //  document.add(table2);
            // document.add(sep);
            ArrayList<C_CloseBranch_value> dati = cb.getDati();

            for (int i = 0; i < dati.size(); i++) {

                cntriga++;

                Row row6 = sheet.createRow((short) cntriga);

                C_CloseBranch_value temp = dati.get(i);

                totPurch += fd(temp.getPurch());
                totSales += fd(temp.getSales());
                totFx += fd(temp.getFx());
                totPos += fd(temp.getPos());
                totAccCC += fd(temp.getAccCC());
                totcashadv += fd(temp.getCashadvance());
                totcashonprem += fd(temp.getCashonprem());

                Cell f1bis = row6.createCell(1);
                f1bis.setCellStyle(style4);
                f1bis.setCellValue(temp.getCodBranch() + " - " + temp.getDescrBranch());

//                Cell f2 = row6.createCell(2);
//                f2.setCellStyle(style4);
//                f2.setCellValue();
                Cell f3 = row6.createCell(3);
                f3.setCellStyle(style4left);
                f3.setCellValue(temp.gethClose());

                Cell f4 = row6.createCell(4, NUMERIC);
                f4.setCellStyle(cellStylenum);
                f4.setCellValue(fd(temp.getPurch()));

                Cell f5 = row6.createCell(5, NUMERIC);
                f5.setCellStyle(cellStylenum);
                f5.setCellValue(fd(temp.getSales()));

                Cell f6 = row6.createCell(6, NUMERIC);
                f6.setCellStyle(cellStylenum);
                f6.setCellValue(fd(temp.getCashadvance()));

                Cell f7 = row6.createCell(7, NUMERIC);
                f7.setCellStyle(cellStylenum);
                f7.setCellValue(fd(temp.getCashonprem()));

                Cell f8 = row6.createCell(8, NUMERIC);
                f8.setCellStyle(cellStylenum);
                f8.setCellValue(fd(temp.getFx()));

                Cell f9 = row6.createCell(9, NUMERIC);
                f9.setCellStyle(cellStylenum);
                f9.setCellValue(fd(temp.getPos()));

                Cell f10 = row6.createCell(10, NUMERIC);
                f10.setCellStyle(cellStylenum);
                f10.setCellValue(fd(temp.getAccCC()));

                for (int x = 0; x < temp.getDati_string().size(); x++) {
                    
                    Cell f11 = row6.createCell(x + 11, NUMERIC);
                    f11.setCellStyle(cellStylenum);
                    f11.setCellValue(fd(temp.getDati_string().get(x)));
                    double t;
                    if (tot.isEmpty()) {
                        t = fd(temp.getDati_string().get(x));
                        tot.add(x, roundDoubleandFormat(t, 2));
                    } else {
                        try {
                            tot.get(x);
                            t = fd(tot.get(x)) + fd(temp.getDati_string().get(x));
                            tot.set(x, roundDoubleandFormat(t, 2));
                        } catch (IndexOutOfBoundsException e) {
                            t = fd(temp.getDati_string().get(x));
                            tot.add(x, roundDoubleandFormat(t, 2));
                        }
                    }

                }

            }

            totPurch = roundDouble(totPurch, 2);
            totSales = roundDouble(totSales, 2);
            totFx = roundDouble(totFx, 2);
            totPos = roundDouble(totPos, 2);
            totAccCC = roundDouble(totAccCC, 2);
            totcashadv = roundDouble(totcashadv, 2);
            totcashonprem = roundDouble(totcashonprem, 2);

            cntriga++;

            Row row7 = sheet.createRow((short) cntriga);

            Cell f53 = row7.createCell(3);
            f53.setCellStyle(style3left);
            f53.setCellValue("Total");

            Cell f54 = row7.createCell(4, NUMERIC);
            f54.setCellStyle(style3num);
            f54.setCellValue(fd(roundDoubleandFormat(totPurch, 2)));

            Cell f55 = row7.createCell(5, NUMERIC);
            f55.setCellStyle(style3num);
            f55.setCellValue(fd(roundDoubleandFormat(totSales, 2)));

            Cell f56 = row7.createCell(6, NUMERIC);
            f56.setCellStyle(style3num);
            f56.setCellValue(fd(roundDoubleandFormat(totcashadv, 2)));

            Cell f57 = row7.createCell(7, NUMERIC);
            f57.setCellStyle(style3num);
            f57.setCellValue(fd(roundDoubleandFormat(totcashonprem, 2)));

            Cell f58 = row7.createCell(8, NUMERIC);
            f58.setCellStyle(style3num);
            f58.setCellValue(fd(roundDoubleandFormat(totFx, 2)));

            Cell f59 = row7.createCell(9, NUMERIC);
            f59.setCellStyle(style3num);
            f59.setCellValue(fd(roundDoubleandFormat(totPos, 2)));

            Cell f60 = row7.createCell(10, NUMERIC);
            f60.setCellStyle(style3num);
            f60.setCellValue(fd(roundDoubleandFormat(totAccCC, 2)));

            for (int x = 0; x < tot.size(); x++) {

                Cell f61 = row7.createCell(x + 11, NUMERIC);
                f61.setCellStyle(style3num);
                f61.setCellValue(fd(tot.get(x)));

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

//    public static void main(String[] args) {
//        C_CloseBranch nctl = new C_CloseBranch();
//
//       C_CloseBranch_value pdf = new C_CloseBranch_value();
//
//        
//
//        ArrayList<C_CloseBranch_value> dati = new ArrayList<>();
//        dati = Engine.getC_CloseBranch();
//        pdf.setData_da("19/05/2016");
//        pdf.setData_a("20/05/2016");
//        pdf.setDati(dati);
//
//        nctl.receipt(pdf, alcolonne);
//
//    }
}
