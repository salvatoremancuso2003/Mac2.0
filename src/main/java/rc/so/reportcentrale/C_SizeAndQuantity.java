/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rc.so.reportcentrale;

import com.itextpdf.text.BaseColor;
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
import rc.so.db.Db_Master;
import rc.so.entity.Branch;
import static rc.so.util.Constant.formatdataCell;
import static rc.so.util.Engine.formatBankBranchReport;
import static rc.so.util.Engine.insertTR;
import static rc.so.util.Utility.fd;
import static rc.so.util.Utility.ff;
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
public class C_SizeAndQuantity {

    // column

    /**
     *
     */
    public static final float[] columnWidths0 = new float[] { 70f, 30f };

    /**
     *
     */
    public static final float[] columnWidths1 = new float[] { 60f, 30f };

    /**
     *
     */
    public static float[] columnWidths2 = new float[] { 10f, 10f, 10f, 10f };

    /**
     *
     */
    public static float[] columnWidths3 = new float[] { 5f, 20f, 50f, 10f, 10f, 15f, 10f, 10f };

    /**
     *
     */
    public static float[] columnWidths4 = new float[] { 30f, 20f, 15f, 15f, 10f, 15f };

    /**
     *
     */
    public static float[] columnWidths5 = new float[] { 30f, 20f, 15f, 15f, 10f, 15f };

    /**
     *
     */
    public static float[] columnWidths6 = new float[] { 30f, 20f, 15f, 15f, 10f, 15f };
    final String intestazionePdf = "Sizes and Quantities";
    Phrase vuoto = new Phrase("\n");

    // resource
    // public static final String logo = "web/resource/logocl.png";
    // other

    /**
     *
     */
    public static final String br = "\n";

    /**
     *
     */
    public static final String blank = " ";

    Font f0_bold, f1_bold, f2_bold, f1_normal, f2_normal, f3_normal, f3_bold, f4_bold;

    float tot_qta = 0, tot_amount = 0, tot_comm = 0, tot_net = 0, tot_buy = 0, tot_spread = 0, tot_commFix = 0;

    /**
     ** Constructor
     */
    public C_SizeAndQuantity() {

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
     * @param cmfb
     * @param colonne
     * @param firstTime
     * @param lastTime
     * @param document
     * @return
     */
    public Document receipt(C_SizeAndQuantity_value cmfb, ArrayList<String> colonne, boolean firstTime,
            boolean lastTime, Document document) {

        try {

            if (firstTime) {
                PdfPTable table = new PdfPTable(2);
                table.setWidths(columnWidths0);
                table.setWidthPercentage(100);
                Phrase phrase1 = new Phrase();
                phrase1.add(new Chunk(intestazionePdf + " " + cmfb.getDate(), f3_bold));
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

            ArrayList<C_SizeAndQuantity_value> dati = cmfb.getDati();
            PdfPCell cell;
            Phrase phraset;
            PdfPCell cellt;
            PdfPTable table3;

            double amount;
            int qta;
            // inserire il nome della valuta
            ArrayList<String> valute = new ArrayList<>();

            for (int x = 0; x < dati.size(); x++) {
                C_SizeAndQuantity_value temp = dati.get(x);
                if (!valute.contains(temp.getCurrency())) {
                    valute.add(temp.getCurrency());
                }
            }

            LineSeparator sep = new LineSeparator();
            sep.setOffset(-2);
            sep.setLineWidth((float) 0.5);

            PdfPTable table4 = new PdfPTable(2);
            table4.setWidths(columnWidths0);
            table4.setWidthPercentage(100);

            phraset = new Phrase();
            phraset.add(new Chunk(cmfb.getId_filiale() + " " + cmfb.getDe_filiale(), f3_normal));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_LEFT);
            cellt.setBorder(BOTTOM);
            cellt.setBackgroundColor(new BaseColor(242, 242, 242));
            table4.addCell(cellt);

            phraset = new Phrase();
            phraset.add(new Chunk(cmfb.getDate(), f3_normal));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_LEFT);
            cellt.setBorder(BOTTOM);
            cellt.setBackgroundColor(new BaseColor(242, 242, 242));
            table4.addCell(cellt);

            document.add(table4);

            for (int j = 0; j < valute.size(); j++) {

                qta = 0;
                amount = 0;

                sep = new LineSeparator();
                sep.setOffset(-2);
                sep.setLineWidth((float) 0.5);

                table4 = new PdfPTable(2);
                table4.setWidths(columnWidths0);
                table4.setWidthPercentage(100);

                phraset = new Phrase();
                phraset.add(new Chunk("\n " + valute.get(j), f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_LEFT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk("", f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);

                document.add(table4);

                ArrayList<C_SizeAndQuantity_value> listTemp = getListCurrency(valute.get(j), dati);

                //
                PdfPTable table2 = new PdfPTable(colonne.size());
                table2.setWidths(columnWidths2);
                table2.setWidthPercentage(100);

                PdfPCell[] list = new PdfPCell[colonne.size()];
                // mi scandisco le colonne
                for (int c = 0; c < colonne.size(); c++) {
                    Phrase phraset1 = new Phrase();
                    phraset1.add(new Chunk(colonne.get(c), f4_bold));
                    PdfPCell cellt1 = new PdfPCell(phraset1);
                    cellt1.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt1.setBorder(BOTTOM | TOP);
                    cellt1.setFixedHeight(15f);
                    cellt1.setBackgroundColor(LIGHT_GRAY);
                    cellt1.setBorderWidth(0.7f);

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

                for (int x = 0; x < listTemp.size(); x++) {

                    C_SizeAndQuantity_value temp = (C_SizeAndQuantity_value) listTemp.get(x);

                    sep = new LineSeparator();
                    sep.setOffset(-2);
                    sep.setLineWidth((float) 0.5);

                    table4 = new PdfPTable(colonne.size());
                    table4.setWidths(columnWidths2);
                    table4.setWidthPercentage(100);

                    phraset = new Phrase();
                    phraset.add(new Chunk(temp.getKind(), f3_normal));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setBorder(BOTTOM);
                    table4.addCell(cellt);

                    phraset = new Phrase();
                    phraset.add(new Chunk(formatMysqltoDisplay(temp.getSize()), f3_normal));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setBorder(BOTTOM);
                    table4.addCell(cellt);

                    phraset = new Phrase();
                    phraset.add(new Chunk(formatMysqltoDisplay(temp.getQty()), f3_normal));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setBorder(BOTTOM);
                    table4.addCell(cellt);

                    phraset = new Phrase();
                    phraset.add(new Chunk(formatMysqltoDisplay(temp.getTotal()), f3_normal));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setBorder(BOTTOM);
                    table4.addCell(cellt);

                    qta += ff(temp.getQty());
                    amount += ff(temp.getTotal());

                    document.add(table4);

                }

                amount = roundDouble(amount, 2);

                // linea totale per valuta
                sep = new LineSeparator();
                sep.setLineWidth(0.7f);
                document.add(sep);

                table4 = new PdfPTable(colonne.size());
                table4.setWidths(columnWidths2);
                table4.setWidthPercentage(100);

                phraset = new Phrase();
                phraset.add(new Chunk("", f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk("Total", f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(qta, 0)), f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(amount, 2)), f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);

                document.add(table4);

            }

            vuoto.setFont(f3_normal);
            document.add(vuoto);

        } catch (DocumentException ex) {
            insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
        }
        return document;
    }

    /**
     *
     * @param path
     * @param d3
     * @param data1
     * @param alcolonne
     * @param filiali
     * @param br
     * @param currency
     * @return
     */
    public String main(String path, String d3, String data1,
            ArrayList<String> alcolonne, ArrayList<String> filiali, ArrayList<Branch> br, String currency) {
        
                String base64 = null;
        C_SizeAndQuantity nctl = new C_SizeAndQuantity();
        C_SizeAndQuantity_value pdf = new C_SizeAndQuantity_value();
        boolean firstTime = true;
        boolean lastTime = false;
        File pdffile = new File(path + generaId(50) + "C_SizeAndQuantity.pdf");
        try (OutputStream ou = new FileOutputStream(pdffile)) {
            Document document = new Document(A4.rotate(), 20, 20, 20, 20);
            PdfWriter wr = getInstance(document, ou);
            document.open();
            // ciclo per ogni filiale
            boolean find = false;

            Db_Master dbm = new Db_Master();
            for (int i = 0; i < filiali.size(); i++) {

                ArrayList<C_SizeAndQuantity_value> dati = dbm.list_C_SizeAndQuantity_value(data1, filiali.get(i),
                        currency);
                if (dati.size() > 0) {
                    find = true;
                }
                pdf.setId_filiale(filiali.get(i));
                pdf.setDe_filiale(formatBankBranchReport(filiali.get(i), "BR", null, br));
                pdf.setDate(d3);
                pdf.setDati(dati);
                if (i == filiali.size() - 1) {
                    lastTime = true;
                }
                document = nctl.receipt(pdf, alcolonne, firstTime, lastTime, document);
                firstTime = false;
            }
            dbm.closeDB();

            if (find) {
                // chiusura documento
                document.close();
                wr.close();
                base64 = new String(encodeBase64(readFileToByteArray(pdffile)));
                pdffile.delete();
            } else {
                document.add(new Paragraph("CZZ"));
                document.close();
                wr.close();
                pdffile.delete();
            }
        } catch (Exception ex) {
            insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
        }
        return base64;
    }

    /**
     *
     * @param cmfb
     * @param colonne
     * @param firstTime
     * @param lastTime
     * @param sheet
     * @param cntriga
     * @param style1
     * @param style2
     * @param style3
     * @param style4
     * @param style4left
     * @param cellStylenum
     * @param style3num
     * @return
     */
    public int receiptexcel(C_SizeAndQuantity_value cmfb, ArrayList<String> colonne, boolean firstTime,
            boolean lastTime, HSSFSheet sheet,
            int cntriga, HSSFCellStyle style1, HSSFCellStyle style2, HSSFCellStyle style3, HSSFCellStyle style4,
            HSSFCellStyle style4left,
            HSSFCellStyle cellStylenum, HSSFCellStyle style3num) {

        try {

            if (firstTime) {
                Row rowP = sheet.createRow((short) cntriga);

                Cell cl = rowP.createCell(1);
                cl.setCellStyle(style1);
                cl.setCellValue(intestazionePdf + " From " + cmfb.getDate());

                cntriga++;
                cntriga++;

            }

            ArrayList<C_SizeAndQuantity_value> dati = cmfb.getDati();

            double amount;
            int qta;
            // inserire il nome della valuta
            ArrayList<String> valute = new ArrayList<>();

            for (int x = 0; x < dati.size(); x++) {
                C_SizeAndQuantity_value temp = dati.get(x);
                if (!valute.contains(temp.getCurrency())) {
                    valute.add(temp.getCurrency());
                }
            }

            cntriga++;
            Row rowP = sheet.createRow((short) cntriga);

            Cell cl = rowP.createCell(1);
            cl.setCellStyle(style1);
            cl.setCellValue(cmfb.getId_filiale() + " " + cmfb.getDe_filiale());

            Cell cl2 = rowP.createCell(2);
            cl2.setCellStyle(style1);
            cl2.setCellValue(cmfb.getDate());

            cntriga++;

            for (int j = 0; j < valute.size(); j++) {

                cntriga++;
                Row row88 = sheet.createRow((short) cntriga);

                qta = 0;
                amount = 0;

                Cell f1bis = row88.createCell(1);
                f1bis.setCellStyle(style4left);
                f1bis.setCellValue("\n " + valute.get(j));

                ArrayList<C_SizeAndQuantity_value> listTemp = getListCurrency(valute.get(j), dati);

                //
                cntriga++;

                Row row66 = sheet.createRow((short) cntriga);

                // mi scandisco le colonne
                for (int c = 0; c < colonne.size(); c++) {
                    Cell cl7 = row66.createCell(c + 1);
                    cl7.setCellStyle(style3);
                    cl7.setCellValue(colonne.get(c));
                }

                cntriga++;

                for (int x = 0; x < listTemp.size(); x++) {

                    cntriga++;
                    Row row6 = sheet.createRow((short) cntriga);

                    C_SizeAndQuantity_value temp = (C_SizeAndQuantity_value) listTemp.get(x);

                    Cell f1bis1 = row6.createCell(1);
                    f1bis1.setCellStyle(style4);
                    f1bis1.setCellValue(temp.getKind());

                    Cell f2 = row6.createCell(2, NUMERIC);
                    f2.setCellStyle(cellStylenum);
                    f2.setCellValue(fd(temp.getSize()));

                    Cell f3 = row6.createCell(3, NUMERIC);
                    f3.setCellStyle(cellStylenum);
                    f3.setCellValue(fd(temp.getQty()));

                    Cell f4 = row6.createCell(4, NUMERIC);
                    f4.setCellStyle(cellStylenum);
                    f4.setCellValue(fd(temp.getTotal()));

                    qta += ff(temp.getQty());
                    amount += ff(temp.getTotal());

                }

                amount = roundDouble(amount, 2);

                cntriga++;
                cntriga++;
                Row row7 = sheet.createRow((short) cntriga);

                Cell f70 = row7.createCell(2);
                f70.setCellStyle(style3);
                f70.setCellValue("Total");

                Cell f71 = row7.createCell(3, NUMERIC);
                f71.setCellStyle(style3num);
                f71.setCellValue(fd(roundDoubleandFormat(qta, 2)));

                Cell f72 = row7.createCell(4, NUMERIC);
                f72.setCellStyle(style3num);
                f72.setCellValue(fd(roundDoubleandFormat(amount, 2)));

                cntriga++;
                cntriga++;

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
     * @param d3
     * @param data1
     * @param alcolonne
     * @param filiali
     * @param br
     * @param currency
     * @return
     */
    public String mainexcel(String path, String d3, String data1,
            ArrayList<String> alcolonne, ArrayList<String> filiali, ArrayList<Branch> br, String currency) {
        try {
            File pdffile = new File(path + generaId(50) + "C_SizeAndQuantity.xls");
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet("C_SizeAndQuantity");

            // CREAZIONE FONT
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
            cellStylenum.setAlignment(RIGHT);
            cellStylenum.setBorderTop(THIN);
            cellStylenum.setBorderBottom(THIN);

            HSSFCellStyle style3num = (HSSFCellStyle) workbook.createCellStyle();
            style3num.setFont(font3);
            style3num.setAlignment(RIGHT);
            style3num.setBorderTop(THIN);
            style3num.setBorderBottom(THIN);
            style3num.setDataFormat(hssfDataFormat.getFormat(formatdataCell));

            C_SizeAndQuantity nctl = new C_SizeAndQuantity();
            C_SizeAndQuantity_value pdf = new C_SizeAndQuantity_value();

            boolean firstTime = true;
            boolean lastTime = false;

            boolean find = false;

            // ciclo per ogni filiale
            Db_Master dbm = new Db_Master();
            int cntriga = 1;
            for (int i = 0; i < filiali.size(); i++) {
                ArrayList<C_SizeAndQuantity_value> dati = dbm.list_C_SizeAndQuantity_value(data1, filiali.get(i),
                        currency);
                if (dati.size() > 0) {
                    find = true;
                }
                pdf.setId_filiale(filiali.get(i));
                pdf.setDe_filiale(formatBankBranchReport(filiali.get(i), "BR", null, br));
                pdf.setDate(d3);
                pdf.setDati(dati);
                if (i == filiali.size() - 1) {
                    lastTime = true;
                }

                cntriga = nctl.receiptexcel(pdf, alcolonne, firstTime, lastTime, sheet, cntriga, style1, style2, style3,
                        style4, style4left, cellStylenum, style3num);
                firstTime = false;
            }
            dbm.closeDB();

            if (find) {

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
            } else {
                pdffile.delete();
                return null;
            }
        } catch (IOException ex) {
            insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
        }
        return null;

    }

    /**
     *
     * @param valuta
     * @param dati
     * @return
     */
    public ArrayList<C_SizeAndQuantity_value> getListCurrency(String valuta, ArrayList<C_SizeAndQuantity_value> dati) {
        ArrayList<C_SizeAndQuantity_value> list = new ArrayList<>();

        for (int i = 0; i < dati.size(); i++) {
            C_SizeAndQuantity_value temp = dati.get(i);
            if (temp.getCurrency().equals(valuta)) {
                list.add(dati.get(i));
            }
        }

        return list;
    }

}
