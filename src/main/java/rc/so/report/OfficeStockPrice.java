/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rc.so.report;

import static com.itextpdf.text.BaseColor.LIGHT_GRAY;
import com.itextpdf.text.Chunk;
import static com.itextpdf.text.Chunk.NEWLINE;
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
import static com.itextpdf.text.Rectangle.LEFT;
import static com.itextpdf.text.Rectangle.NO_BORDER;
import static com.itextpdf.text.Rectangle.RIGHT;
import static com.itextpdf.text.Rectangle.TOP;
import static com.itextpdf.text.pdf.BaseFont.HELVETICA;
import static com.itextpdf.text.pdf.BaseFont.WINANSI;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import static com.itextpdf.text.pdf.PdfWriter.getInstance;
import com.itextpdf.text.pdf.draw.LineSeparator;
import static rc.so.util.Constant.formatdataCell;
import static rc.so.util.Constant.formatdataCellRate;
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
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import static org.apache.poi.ss.usermodel.BorderStyle.THIN;
import static org.apache.poi.ss.usermodel.CellType.NUMERIC;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
/**
 *
 * @author vcrugliano
 */
public class OfficeStockPrice {

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
    public static float[] columnWidths2 = new float[]{30f, 20f, 15f, 15f, 15f};

    /**
     *
     */
    public static float[] columnWidths3 = new float[]{30f, 20f, 15f, 15f, 15f};
    final String intestazionePdf = "Office Stock Price";
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
    public OfficeStockPrice() {

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
     * @param osp
     * @param colonne
     * @param datareport
     * @return
     */
    public String receipt(String path, OfficeStockPrice_value osp, ArrayList<String> colonne, String datareport) {

//        String outputfile = "OfficeStockPrice.pdf";
        String valutalocale = osp.getLocalcurrency();

        try {
            File pdf = new File(path + generaId(50) + "OfficeStockPrice.pdf");
            Document document = new Document(A4, 20, 20, 20, 20);
            try(OutputStream ou = new FileOutputStream(pdf)){
            PdfWriter wr = getInstance(document, ou);
            document.open();

            PdfPTable table = new PdfPTable(2);
            table.setWidths(columnWidths0);
            table.setWidthPercentage(100);

            Phrase phrase1 = new Phrase();
            phrase1.add(new Chunk(intestazionePdf + " " + datareport, f3_bold));
            PdfPCell cell1 = new PdfPCell(phrase1);
            cell1.setBorder(NO_BORDER);

            //Paragraph pa1 = new Paragraph(new Phrase(datareport, f3_normal));
            Paragraph pa1 = new Paragraph(new Phrase("", f3_normal));
            pa1.setAlignment(ALIGN_RIGHT);
            PdfPCell cell2 = new PdfPCell(pa1);
            cell2.setBorder(NO_BORDER);
            cell2.setHorizontalAlignment(ALIGN_RIGHT);

            Phrase phrase3 = new Phrase();
            phrase3.add(new Chunk("\n " + osp.getId_filiale() + " " + osp.getDe_filiale(), f3_normal));
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

            LineSeparator sep = new LineSeparator();
            sep.setOffset(-2);
            sep.setLineWidth((float) 0.5);

            //  document.add(table2);
            //   document.add(sep);
            ArrayList<OfficeStockPrice_value> dati = osp.getDati();
            Phrase phraset;
            PdfPCell cellt;

            table3 = new PdfPTable(colonne.size());
            table3.setWidths(columnWidths2);
            table3.setWidthPercentage(100);

            double totalecop = 0; //totale controvalore della valuta locale
            double totalefx = 0;//totale controvalore delle valute diverse dalla valuta locale
            double totalegenerale = 0; //totale generale

            for (int i = 0; i < dati.size(); i++) {
                OfficeStockPrice_value temp = dati.get(i);

                if (temp.getCurrency().equals(valutalocale) && temp.getSupporto().contains("01")) {

                } else {

                    phraset = new Phrase();
                    phraset.add(new Chunk(temp.getCurrency() + " " + temp.getDecurrency(), f3_normal));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_LEFT);
                    cellt.setBorder(BOTTOM);
                    table3.addCell(cellt);

                    phraset = new Phrase();
                    phraset.add(new Chunk(temp.getSupporto(), f3_normal));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_LEFT);
                    cellt.setBorder(BOTTOM);
                    table3.addCell(cellt);

                    phraset = new Phrase();
                    phraset.add(new Chunk(formatMysqltoDisplay(temp.getQta()), f3_normal));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setBorder(BOTTOM);
                    table3.addCell(cellt);

                    phraset = new Phrase();
                    phraset.add(new Chunk(formatMysqltoDisplay(temp.getMedioacq()), f3_normal));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setBorder(BOTTOM);
                    table3.addCell(cellt);

                    phraset = new Phrase();
                    phraset.add(new Chunk(formatMysqltoDisplay(temp.getControvalore()), f3_normal));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setBorder(BOTTOM);
                    table3.addCell(cellt);
                }
                if (temp.getCurrency().equals(valutalocale) && temp.getSupporto().contains("01")) {
                    totalecop += ff(temp.getControvaloreSenzaFormattazione());
                } else {
                    totalefx += ff(temp.getControvaloreSenzaFormattazione());
                }
                totalegenerale += ff(temp.getControvaloreSenzaFormattazione());
            }

            totalefx = roundDouble(totalefx, 2);
            totalecop = roundDouble(totalecop, 2);
            totalegenerale = roundDouble(totalegenerale, 2);

            document.add(table3);

            table3 = new PdfPTable(colonne.size());
            table3.setWidths(columnWidths3);
            table3.setWidthPercentage(100);

            phraset = new Phrase();
            phraset.add(new Chunk("", f3_normal));
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
            phraset.add(new Chunk("", f3_normal));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setBorder(NO_BORDER);
            table3.addCell(cellt);

            phraset = new Phrase();
            phraset.add(new Chunk("Total FX", f4_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setBorder(RIGHT | LEFT | TOP);
            cellt.setBorderWidth(0.7f);
            table3.addCell(cellt);

            phraset = new Phrase();
            phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(totalefx, 2)), f4_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setBorder(NO_BORDER);
            table3.addCell(cellt);

            document.add(table3);

            table3 = new PdfPTable(colonne.size());
            table3.setWidths(columnWidths3);
            table3.setWidthPercentage(100);

            phraset = new Phrase();
            phraset.add(new Chunk("", f3_normal));
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
            phraset.add(new Chunk("", f3_normal));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setBorder(NO_BORDER);
            table3.addCell(cellt);

            phraset = new Phrase();
            phraset.add(new Chunk("Total C.O.P.", f4_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setBorder(RIGHT | LEFT);
            cellt.setBorderWidth(0.7f);
            table3.addCell(cellt);

            phraset = new Phrase();
            phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(totalecop, 2)), f4_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setBorder(NO_BORDER);
            table3.addCell(cellt);

            document.add(table3);

            table3 = new PdfPTable(colonne.size());
            table3.setWidths(columnWidths3);
            table3.setWidthPercentage(100);

            phraset = new Phrase();
            phraset.add(new Chunk("", f3_normal));
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
            phraset.add(new Chunk("", f3_normal));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setBorder(NO_BORDER);
            table3.addCell(cellt);

            phraset = new Phrase();
            phraset.add(new Chunk("Grand Total", f4_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setBorder(RIGHT | LEFT | BOTTOM);
            cellt.setBorderWidth(0.7f);
            table3.addCell(cellt);

            phraset = new Phrase();
            phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(totalegenerale, 2)), f4_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setBorder(NO_BORDER);
            table3.addCell(cellt);

            document.add(table3);

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
     * @param osp
     * @param colonne
     * @param datareport
     * @return
     */
    public String receiptexcel(String path, OfficeStockPrice_value osp, ArrayList<String> colonne,
            String datareport) {
        String valutalocale = osp.getLocalcurrency();
        try {
            File pdf = new File(path + generaId(50) + "OfficeStockPrice.xls");
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet("OfficeStockPrice");
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
            style3.setAlignment(HorizontalAlignment.RIGHT);
            style3.setBorderTop(THIN);
            style3.setBorderBottom(THIN);

            HSSFCellStyle style3left = (HSSFCellStyle) workbook.createCellStyle();
            style3left.setFont(font3);
            style3left.setAlignment(HorizontalAlignment.LEFT);
            style3left.setBorderTop(THIN);
            style3left.setBorderBottom(THIN);

            HSSFCellStyle style3bis = (HSSFCellStyle) workbook.createCellStyle();
            style3bis.setFont(font3);
            style3bis.setAlignment(HorizontalAlignment.LEFT);
            style3bis.setBorderTop(THIN);
            style3bis.setBorderBottom(THIN);

            HSSFFont font4 = workbook.createFont();
            font4.setFontName(FONT_ARIAL);
            font4.setFontHeightInPoints((short) 10);

            HSSFCellStyle style4 = (HSSFCellStyle) workbook.createCellStyle();
            style4.setAlignment(HorizontalAlignment.RIGHT);
            style4.setBorderTop(THIN);
            style4.setBorderBottom(THIN);

            HSSFCellStyle style4left = (HSSFCellStyle) workbook.createCellStyle();
            style4left.setAlignment(HorizontalAlignment.LEFT);
            style4left.setBorderTop(THIN);
            style4left.setBorderBottom(THIN);

            HSSFCellStyle cellStylenum = (HSSFCellStyle) workbook.createCellStyle();
            HSSFDataFormat hssfDataFormat = (HSSFDataFormat) workbook.createDataFormat();
            cellStylenum.setDataFormat(hssfDataFormat.getFormat(formatdataCell));
            cellStylenum.setAlignment(HorizontalAlignment.RIGHT);
            cellStylenum.setBorderTop(THIN);
            cellStylenum.setBorderBottom(THIN);

            HSSFCellStyle cellStylenumRATE = (HSSFCellStyle) workbook.createCellStyle();
            cellStylenumRATE.setDataFormat(hssfDataFormat.getFormat(formatdataCellRate));
            cellStylenumRATE.setAlignment(HorizontalAlignment.RIGHT);
            cellStylenumRATE.setBorderTop(THIN);
            cellStylenumRATE.setBorderBottom(THIN);

            HSSFCellStyle style3num = (HSSFCellStyle) workbook.createCellStyle();
            style3num.setFont(font3);
            style3num.setAlignment(HorizontalAlignment.RIGHT);
            style3num.setBorderTop(THIN);
            style3num.setBorderBottom(THIN);
            style3num.setDataFormat(hssfDataFormat.getFormat(formatdataCell));

            Row rowP = sheet.createRow((short) 1);

            Cell cl = rowP.createCell(1);
            cl.setCellStyle(style1);
            cl.setCellValue(intestazionePdf + " " + datareport);

//            Cell cl2 = rowP.createCell(6);
//            cl2.setCellStyle(style2);
//            cl2.setCellValue(datareport);
            Row row = sheet.createRow((short) 3);
            row.createCell(1).setCellValue(osp.getId_filiale() + " " + osp.getDe_filiale());

            Row row66 = sheet.createRow((short) 7);

            int cntriga = 10;

            //mi scandisco le colonne
            for (int j = 0; j < colonne.size(); j++) {
                Cell cl5 = row66.createCell(j + 1);
                cl5.setCellStyle(style3);
                if (j == 0 || j == 1) {
                    cl5.setCellStyle(style3left);
                }

                cl5.setCellValue(colonne.get(j));

            }

            //  document.add(table2);
            //   document.add(sep);
            ArrayList<OfficeStockPrice_value> dati = osp.getDati();

            double totalecop = 0; //totale controvalore della valuta locale
            double totalefx = 0;//totale controvalore delle valute diverse dalla valuta locale
            double totalegenerale = 0; //totale generale

            for (int i = 0; i < dati.size(); i++) {

                

                OfficeStockPrice_value temp = dati.get(i);

                if (temp.getCurrency().equals(valutalocale) && temp.getSupporto().contains("01")) {

                } else {
                    cntriga++;
                    Row row6 = sheet.createRow((short) cntriga);
                    Cell f1bis = row6.createCell(1);
                    f1bis.setCellStyle(style4left);
                    f1bis.setCellValue(temp.getCurrency() + " " + temp.getDecurrency());

                    Cell f2 = row6.createCell(2);
                    f2.setCellStyle(style4left);
                    f2.setCellValue(temp.getSupporto());

                    Cell f3 = row6.createCell(3, NUMERIC);
                    f3.setCellStyle(cellStylenum);
                    f3.setCellValue(fd(temp.getQta()));

                    Cell f5 = row6.createCell(4, NUMERIC);
                    f5.setCellStyle(cellStylenumRATE);
                    f5.setCellValue(fd(temp.getMedioacq()));

                    Cell f6 = row6.createCell(5, NUMERIC);
                    f6.setCellStyle(cellStylenum);
                    f6.setCellValue(fd(temp.getControvalore()));

                }
                if (temp.getCurrency().equals(valutalocale) && temp.getSupporto().contains("01")) {
                    totalecop += fd(temp.getControvaloreSenzaFormattazione());
                } else {
                    totalefx += fd(temp.getControvaloreSenzaFormattazione());

                }
                totalegenerale += fd(temp.getControvaloreSenzaFormattazione());
            }

            totalefx = roundDouble(totalefx, 2);
            totalecop = roundDouble(totalecop, 2);
            totalegenerale = roundDouble(totalegenerale, 2);

            cntriga++;
            cntriga++;
            Row row7 = sheet.createRow((short) cntriga);

            Cell f18 = row7.createCell(4);
            f18.setCellStyle(style3);
            f18.setCellValue("Total FX");

            Cell f7 = row7.createCell(5, NUMERIC);
            f7.setCellStyle(style3num);
            f7.setCellValue(fd(roundDoubleandFormat(totalefx, 2)));

            cntriga++;
            Row row8 = sheet.createRow((short) cntriga);

            Cell f20 = row8.createCell(4);
            f20.setCellStyle(style3);
            f20.setCellValue("Total C.O.P.");

            Cell f21 = row8.createCell(5, NUMERIC);
            f21.setCellStyle(style3num);
            f21.setCellValue(fd(roundDoubleandFormat(totalecop, 2)));

            cntriga++;
            Row row9 = sheet.createRow((short) cntriga);

            Cell f22 = row9.createCell(4);
            f22.setCellStyle(style3);
            f22.setCellValue("Grand Total");

            Cell f23 = row9.createCell(5, NUMERIC);
            f23.setCellStyle(style3num);
            f23.setCellValue(fd(roundDoubleandFormat(totalegenerale, 2)));

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

    /**
     *
     * @param path
     * @param osplist
     * @param colonne
     * @param datereport
     * @return
     */
    public String receiptcentrale(String path, ArrayList<OfficeStockPrice_value> osplist, ArrayList<String> colonne, String datereport) {

//        String outputfile = "StockPriceReportPerFiliali.pdf";
        try {
            File pdf = new File(path + generaId(50) + "StockPriceReportPerFiliali.pdf");
            Document document = new Document(A4, 20, 20, 20, 20);
            try(OutputStream ou = new FileOutputStream(pdf)){
            PdfWriter wr = getInstance(document, ou);
            document.open();

            PdfPTable table = new PdfPTable(2);
            table.setWidths(columnWidths0);
            table.setWidthPercentage(100);

             
            Phrase phrase1 = new Phrase();
            phrase1.add(new Chunk(intestazionePdf, f3_bold));
            PdfPCell cell1 = new PdfPCell(phrase1);
            cell1.setBorder(NO_BORDER);

            Paragraph pa1 = new Paragraph(new Phrase(datereport, f3_normal));
            pa1.setAlignment(ALIGN_RIGHT);
            PdfPCell cell2 = new PdfPCell(pa1);
            cell2.setBorder(NO_BORDER);
            cell2.setHorizontalAlignment(ALIGN_RIGHT);

            Phrase phrase3 = new Phrase();
            phrase3.add(new Chunk("\n ", f3_normal));
            //phrase3.add(new Chunk("\n " + osp.getId_filiale() + " " + osp.getDe_filiale(), f3_normal));
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

            int numerofilialipergruppo = 0;
            double totalecopgruppo = 0; //totale controvalore della valuta locale gruppo
            double totalefxgruppo = 0;//totale controvalore delle valute diverse dalla valuta locale gruppo
            double totalegeneralegruppo = 0; //totale generale gruppo

            for (int q = 0; q < osplist.size(); q++) {

                OfficeStockPrice_value ospnext;

                OfficeStockPrice_value osp = osplist.get(q);

                if (q < osplist.size() - 1) {
                    ospnext = osplist.get(q + 1);
                } else {
                    ospnext = osplist.get(q);
                }

                String valutalocale = osp.getLocalcurrency();
                String gruppo = osp.getGruppo();

                table = new PdfPTable(2);
                table.setWidths(columnWidths0);
                table.setWidthPercentage(100);

                phrase1 = new Phrase();
                phrase1.add(new Chunk("", f3_bold));
                cell1 = new PdfPCell(phrase1);
                cell1.setBorder(NO_BORDER);

                //  pa1 = new Paragraph(new Phrase(Engine.getDataITA(), f3_normal));
                pa1 = new Paragraph(new Phrase("", f3_normal));
                pa1.setAlignment(ALIGN_RIGHT);
                cell2 = new PdfPCell(pa1);
                cell2.setBorder(NO_BORDER);
                cell2.setHorizontalAlignment(ALIGN_RIGHT);

                phrase3 = new Phrase();
                // phrase3.add(new Chunk("\n ", f3_normal));                
                phrase3.add(new Chunk("\n " + osp.getId_filiale() + " " + osp.getDe_filiale() + "  -  " + osp.getData(), f3_normal));
                cell3 = new PdfPCell(phrase3);
                cell3.setBorder(NO_BORDER);

                phrase4 = new Phrase();
                phrase4.add(new Chunk("", f3_normal));
                cell4 = new PdfPCell(phrase4);
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
                    cellt1.setBorderWidth(0.7f);
                    if (j == 0 || j == 1) {
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
                //   document.add(sep);
                ArrayList<OfficeStockPrice_value> dati = osp.getDati();
                PdfPCell cell;
                Phrase phraset;
                PdfPCell cellt;

                table3 = new PdfPTable(colonne.size());
                table3.setWidths(columnWidths2);
                table3.setWidthPercentage(100);

                double totalecop = 0; //totale controvalore della valuta locale
                double totalefx = 0;//totale controvalore delle valute diverse dalla valuta locale
                double totalegenerale = 0; //totale generale

                for (int i = 0; i < dati.size(); i++) {
                    OfficeStockPrice_value temp = dati.get(i);

                    if (temp.getCurrency().equals(valutalocale) && temp.getSupporto().contains("01")) {

                    } else {

                        phraset = new Phrase();
                        phraset.add(new Chunk(temp.getCurrency() + " " + temp.getDecurrency(), f3_normal));
                        cellt = new PdfPCell(phraset);
                        cellt.setHorizontalAlignment(ALIGN_LEFT);
                        cellt.setBorder(BOTTOM);
                        table3.addCell(cellt);

                        phraset = new Phrase();
                        phraset.add(new Chunk(temp.getSupporto(), f3_normal));
                        cellt = new PdfPCell(phraset);
                        cellt.setHorizontalAlignment(ALIGN_LEFT);
                        cellt.setBorder(BOTTOM);
                        table3.addCell(cellt);

                        phraset = new Phrase();
                        phraset.add(new Chunk(formatMysqltoDisplay(temp.getQta()), f3_normal));
                        cellt = new PdfPCell(phraset);
                        cellt.setHorizontalAlignment(ALIGN_RIGHT);
                        cellt.setBorder(BOTTOM);
                        table3.addCell(cellt);

                        phraset = new Phrase();
                        phraset.add(new Chunk(formatMysqltoDisplay(temp.getMedioacq()), f3_normal));
                        cellt = new PdfPCell(phraset);
                        cellt.setHorizontalAlignment(ALIGN_RIGHT);
                        cellt.setBorder(BOTTOM);
                        table3.addCell(cellt);

                        phraset = new Phrase();
                        phraset.add(new Chunk(formatMysqltoDisplay(temp.getControvalore()), f3_normal));
                        cellt = new PdfPCell(phraset);
                        cellt.setHorizontalAlignment(ALIGN_RIGHT);
                        cellt.setBorder(BOTTOM);
                        table3.addCell(cellt);

                    }

                    if (temp.getCurrency().equals(valutalocale) && temp.getSupporto().contains("01")) {
                        totalecop += fd(temp.getControvaloreSenzaFormattazione());
                    } else {
                        totalefx += fd(temp.getControvaloreSenzaFormattazione());
                    }

                    totalegenerale += fd(temp.getControvaloreSenzaFormattazione());

                }

                totalefxgruppo = roundDouble(totalefxgruppo, 2);
                totalecopgruppo = roundDouble(totalecopgruppo, 2);
                totalegeneralegruppo = roundDouble(totalegeneralegruppo, 2);

                document.add(table3);

                table3 = new PdfPTable(colonne.size());
                table3.setWidths(columnWidths3);
                table3.setWidthPercentage(100);

                phraset = new Phrase();
                phraset.add(new Chunk("", f3_normal));
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
                phraset.add(new Chunk("", f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(NO_BORDER);
                table3.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk("Total FX", f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(RIGHT | LEFT | TOP);
                cellt.setBorderWidth(0.5f);
                table3.addCell(cellt);

                phraset = new Phrase();

                phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(totalefx, 2)), f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(NO_BORDER);
                table3.addCell(cellt);

                document.add(table3);

                table3 = new PdfPTable(colonne.size());
                table3.setWidths(columnWidths3);
                table3.setWidthPercentage(100);

                phraset = new Phrase();
                phraset.add(new Chunk("", f3_normal));
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
                phraset.add(new Chunk("", f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(NO_BORDER);
                table3.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk("Total C.O.P.", f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(RIGHT | LEFT);
                cellt.setBorderWidth(0.5f);
                table3.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(totalecop, 2)), f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(NO_BORDER);
                table3.addCell(cellt);

                document.add(table3);

                table3 = new PdfPTable(colonne.size());
                table3.setWidths(columnWidths3);
                table3.setWidthPercentage(100);

                phraset = new Phrase();
                phraset.add(new Chunk("", f3_normal));
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
                phraset.add(new Chunk("", f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(NO_BORDER);
                table3.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk("General Total", f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(RIGHT | LEFT | BOTTOM);
                cellt.setBorderWidth(0.5f);
                table3.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(totalegenerale, 2)), f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(NO_BORDER);
                table3.addCell(cellt);

                document.add(table3);

                numerofilialipergruppo++;
                totalefxgruppo += totalefx;
                totalecopgruppo += totalecop;
                totalegeneralegruppo += totalegenerale;
                //mi chiedo se il prossimo fa parte dello stesso gruppo o no, se no scrivo il totale del gruppo
                //presuppongo che mi vengano in ordine
                if (((!(ospnext.getGruppo().equals(gruppo)))) || (q == osplist.size() - 1)) {

                    if (numerofilialipergruppo > 1) {

                        document.add(new Paragraph(NEWLINE));
                        document.add(new Paragraph(NEWLINE));

                        //scrivo il totale del gruppo
                        table3 = new PdfPTable(colonne.size());
                        table3.setWidths(columnWidths3);
                        table3.setWidthPercentage(100);

                        phraset = new Phrase();
                        phraset.add(new Chunk("", f3_normal));
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
                        phraset.add(new Chunk("", f3_normal));
                        cellt = new PdfPCell(phraset);
                        cellt.setHorizontalAlignment(ALIGN_RIGHT);
                        cellt.setBorder(NO_BORDER);
                        table3.addCell(cellt);

                        phraset = new Phrase();
                        phraset.add(new Chunk("Total", f2_bold));
                        cellt = new PdfPCell(phraset);
                        cellt.setHorizontalAlignment(ALIGN_RIGHT);
                        cellt.setBorder(RIGHT | LEFT | TOP);
                        cellt.setBorderWidth(0.7f);
                        table3.addCell(cellt);

                        phraset = new Phrase();

                        phraset.add(new Chunk("", f4_bold));

                        cellt = new PdfPCell(phraset);
                        cellt.setHorizontalAlignment(ALIGN_RIGHT);
                        cellt.setBorder(NO_BORDER);
                        table3.addCell(cellt);

                        document.add(table3);

                        //scrivo il totale del gruppo
                        table3 = new PdfPTable(colonne.size());
                        table3.setWidths(columnWidths3);
                        table3.setWidthPercentage(100);

                        phraset = new Phrase();
                        phraset.add(new Chunk("", f3_normal));
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
                        phraset.add(new Chunk("", f3_normal));
                        cellt = new PdfPCell(phraset);
                        cellt.setHorizontalAlignment(ALIGN_RIGHT);
                        cellt.setBorder(NO_BORDER);
                        table3.addCell(cellt);

                        phraset = new Phrase();
                        phraset.add(new Chunk("Total FX", f4_bold));
                        cellt = new PdfPCell(phraset);
                        cellt.setHorizontalAlignment(ALIGN_RIGHT);
                        cellt.setBorder(RIGHT | LEFT | TOP);
                        cellt.setBorderWidth(0.7f);
                        table3.addCell(cellt);

                        phraset = new Phrase();

                        phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(totalefxgruppo, 2)), f4_bold));

                        cellt = new PdfPCell(phraset);
                        cellt.setHorizontalAlignment(ALIGN_RIGHT);
                        cellt.setBorder(NO_BORDER);
                        table3.addCell(cellt);

                        document.add(table3);

                        table3 = new PdfPTable(colonne.size());
                        table3.setWidths(columnWidths3);
                        table3.setWidthPercentage(100);

                        phraset = new Phrase();
                        phraset.add(new Chunk("", f3_normal));
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
                        phraset.add(new Chunk("", f3_normal));
                        cellt = new PdfPCell(phraset);
                        cellt.setHorizontalAlignment(ALIGN_RIGHT);
                        cellt.setBorder(NO_BORDER);
                        table3.addCell(cellt);

                        phraset = new Phrase();
                        phraset.add(new Chunk("Total C.O.P.", f4_bold));
                        cellt = new PdfPCell(phraset);
                        cellt.setHorizontalAlignment(ALIGN_RIGHT);
                        cellt.setBorder(RIGHT | LEFT);
                        cellt.setBorderWidth(0.7f);
                        table3.addCell(cellt);

                        phraset = new Phrase();
                        phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(totalecopgruppo, 2)), f4_bold));
                        cellt = new PdfPCell(phraset);
                        cellt.setHorizontalAlignment(ALIGN_RIGHT);
                        cellt.setBorder(NO_BORDER);
                        table3.addCell(cellt);

                        document.add(table3);

                        table3 = new PdfPTable(colonne.size());
                        table3.setWidths(columnWidths3);
                        table3.setWidthPercentage(100);

                        phraset = new Phrase();
                        phraset.add(new Chunk("", f3_normal));
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
                        phraset.add(new Chunk("", f3_normal));
                        cellt = new PdfPCell(phraset);
                        cellt.setHorizontalAlignment(ALIGN_RIGHT);
                        cellt.setBorder(NO_BORDER);
                        table3.addCell(cellt);

                        phraset = new Phrase();
                        phraset.add(new Chunk("General Total", f4_bold));
                        cellt = new PdfPCell(phraset);
                        cellt.setHorizontalAlignment(ALIGN_RIGHT);
                        cellt.setBorder(RIGHT | LEFT | BOTTOM);
                        cellt.setBorderWidth(0.7f);
                        table3.addCell(cellt);

                        phraset = new Phrase();
                        phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(totalegeneralegruppo, 2)), f4_bold));
                        cellt = new PdfPCell(phraset);
                        cellt.setHorizontalAlignment(ALIGN_RIGHT);
                        cellt.setBorder(NO_BORDER);
                        table3.addCell(cellt);

                        document.add(table3);

                        totalefxgruppo = 0;
                        totalecopgruppo = 0;
                        totalegeneralegruppo = 0;
                        //numerofilialipergruppo=0;
                    }
                    numerofilialipergruppo = 0;
                }

//            
            }

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
     * @param osplist
     * @param colonne
     * @param datereport
     * @return
     */
    public String receiptcentraleexcel(String path, ArrayList<OfficeStockPrice_value> osplist, ArrayList<String> colonne, String datereport) {

        try {
            File pdf = new File(path + generaId(50) + "OfficeStockPrice.xls");
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet("OfficeStockPrice");
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
            style3.setAlignment(HorizontalAlignment.RIGHT);
            style3.setBorderTop(THIN);
            style3.setBorderBottom(THIN);

            HSSFCellStyle style3left = (HSSFCellStyle) workbook.createCellStyle();
            style3left.setFont(font3);
            style3left.setAlignment(HorizontalAlignment.LEFT);
            style3left.setBorderTop(THIN);
            style3left.setBorderBottom(THIN);

            HSSFFont font4 = workbook.createFont();
            font4.setFontName(FONT_ARIAL);
            font4.setFontHeightInPoints((short) 10);

            HSSFCellStyle style4 = (HSSFCellStyle) workbook.createCellStyle();
            style4.setAlignment(HorizontalAlignment.RIGHT);
            style4.setBorderTop(THIN);
            style4.setBorderBottom(THIN);

            HSSFCellStyle style4left = (HSSFCellStyle) workbook.createCellStyle();
            style4left.setAlignment(HorizontalAlignment.LEFT);
            style4left.setBorderTop(THIN);
            style4left.setBorderBottom(THIN);

            HSSFCellStyle cellStylenum = (HSSFCellStyle) workbook.createCellStyle();
            HSSFDataFormat hssfDataFormat = (HSSFDataFormat) workbook.createDataFormat();
            cellStylenum.setDataFormat(hssfDataFormat.getFormat(formatdataCell));
            cellStylenum.setAlignment(HorizontalAlignment.RIGHT);
            cellStylenum.setBorderTop(THIN);
            cellStylenum.setBorderBottom(THIN);

            HSSFCellStyle cellStylenumRATE = (HSSFCellStyle) workbook.createCellStyle();
            cellStylenumRATE.setDataFormat(hssfDataFormat.getFormat(formatdataCellRate));
            cellStylenumRATE.setAlignment(HorizontalAlignment.RIGHT);
            cellStylenumRATE.setBorderTop(THIN);
            cellStylenumRATE.setBorderBottom(THIN);

            HSSFCellStyle style3num = (HSSFCellStyle) workbook.createCellStyle();
            style3num.setFont(font3);
            style3num.setAlignment(HorizontalAlignment.RIGHT);
            style3num.setBorderTop(THIN);
            style3num.setBorderBottom(THIN);
            style3num.setDataFormat(hssfDataFormat.getFormat(formatdataCell));

            Row rowP = sheet.createRow((short) 1);

            Cell cl = rowP.createCell(1);
            cl.setCellStyle(style1);
            cl.setCellValue(intestazionePdf + " " + datereport);

            int cntriga = 5;
            int numerofilialipergruppo = 0;
            double totalecopgruppo = 0; //totale controvalore della valuta locale gruppo
            double totalefxgruppo = 0;//totale controvalore delle valute diverse dalla valuta locale gruppo
            double totalegeneralegruppo = 0; //totale generale gruppo
            for (int q = 0; q < osplist.size(); q++) {

                cntriga++;
                Row row6b = sheet.createRow((short) cntriga);

                OfficeStockPrice_value ospnext;

                OfficeStockPrice_value osp = osplist.get(q);

                if (q < osplist.size() - 1) {
                    ospnext = osplist.get(q + 1);
                } else {
                    ospnext = osplist.get(q);
                }

                String valutalocale = osp.getLocalcurrency();
                String gruppo = osp.getGruppo();

                Cell f1bis = row6b.createCell(1);
                f1bis.setCellStyle(style4left);
                f1bis.setCellValue("\n " + osp.getId_filiale() + " " + osp.getDe_filiale());

                cntriga++;
                cntriga++;

                Row row66 = sheet.createRow((short) cntriga);

                cntriga++;
                cntriga++;

                //mi scandisco le colonne
                for (int j = 0; j < colonne.size(); j++) {
                    Cell cl5 = row66.createCell(j + 1);
                    cl5.setCellStyle(style3);
                    if (j == 0 || j == 1) {
                        cl5.setCellStyle(style3left);
                    }
                    cl5.setCellValue(colonne.get(j));

                }

                //  document.add(table2);
                //   document.add(sep);
                ArrayList<OfficeStockPrice_value> dati = osp.getDati();

                double totalecop = 0; //totale controvalore della valuta locale
                double totalefx = 0;//totale controvalore delle valute diverse dalla valuta locale
                double totalegenerale = 0; //totale generale

                for (int i = 0; i < dati.size(); i++) {
                    cntriga++;

                    Row row6 = sheet.createRow((short) cntriga);

                    OfficeStockPrice_value temp = dati.get(i);

                    if (temp.getCurrency().equals(valutalocale) && temp.getSupporto().contains("01")) {

                    } else {

                        Cell f1 = row6.createCell(1);
                        f1.setCellStyle(style4left);
                        f1.setCellValue(temp.getCurrency() + " " + temp.getDecurrency());

                        Cell f2 = row6.createCell(2);
                        f2.setCellStyle(style4left);
                        f2.setCellValue(temp.getSupporto());

                        Cell f3 = row6.createCell(3, NUMERIC);
                        f3.setCellStyle(cellStylenum);
                        f3.setCellValue(fd(temp.getQta()));

                        Cell f5 = row6.createCell(4, NUMERIC);
                        f5.setCellStyle(cellStylenumRATE);
                        f5.setCellValue(fd(temp.getMedioacq()));

                        Cell f6 = row6.createCell(5, NUMERIC);
                        f6.setCellStyle(cellStylenum);
                        f6.setCellValue(fd(temp.getControvalore()));

                    }
                    if (temp.getCurrency().equals(valutalocale) && temp.getSupporto().contains("01")) {
                        totalecop += fd(temp.getControvaloreSenzaFormattazione());
                    } else {
                        totalefx += fd(temp.getControvaloreSenzaFormattazione());
                    }
                    totalegenerale += fd(temp.getControvaloreSenzaFormattazione());

                }

                cntriga++;
                cntriga++;

                Row row7 = sheet.createRow((short) cntriga);

                Cell f18 = row7.createCell(4);
                f18.setCellStyle(style3);
                f18.setCellValue("Total FX");

                Cell f15 = row7.createCell(5, NUMERIC);
                f15.setCellStyle(style3num);
                f15.setCellValue(fd(roundDoubleandFormat(totalefx, 2)));

                cntriga++;
                Row row8 = sheet.createRow((short) cntriga);

                Cell f28 = row8.createCell(4);
                f28.setCellStyle(style3);
                f28.setCellValue("Total C.O.P.");

                Cell f25 = row8.createCell(5, NUMERIC);
                f25.setCellStyle(style3num);
                f25.setCellValue(fd(roundDoubleandFormat(totalecop, 2)));

                cntriga++;
                Row row9 = sheet.createRow((short) cntriga);

                Cell f38 = row9.createCell(4);
                f38.setCellStyle(style3);
                f38.setCellValue("General Total");

                Cell f35 = row9.createCell(5, NUMERIC);
                f35.setCellStyle(style3num);
                f35.setCellValue(fd(roundDoubleandFormat(totalegenerale, 2)));

                cntriga++;
                cntriga++;

                numerofilialipergruppo++;
                totalefxgruppo += totalefx;
                totalecopgruppo += totalecop;
                totalegeneralegruppo += totalegenerale;
                //mi chiedo se il prossimo fa parte dello stesso gruppo o no, se no scrivo il totale del gruppo
                //presuppongo che mi vengano in ordine
                if (((!(ospnext.getGruppo().equals(gruppo)))) || (q == osplist.size() - 1)) {

                    if (numerofilialipergruppo > 1) {

                        cntriga++;
                        cntriga++;
                        Row row10 = sheet.createRow((short) cntriga);

                        Cell f118 = row10.createCell(4);
                        f118.setCellStyle(style3);
                        f118.setCellValue("Total");

                        cntriga++;
                        row10 = sheet.createRow((short) cntriga);

                        f118 = row10.createCell(4);
                        f118.setCellStyle(style3);
                        f118.setCellValue("Total FX");

                        Cell f115 = row10.createCell(5, NUMERIC);
                        f115.setCellStyle(style3);
                        f115.setCellValue(fd(roundDoubleandFormat(totalefxgruppo, 2)));

                        cntriga++;
                        Row row11 = sheet.createRow((short) cntriga);

                        Cell f218 = row11.createCell(4);
                        f218.setCellStyle(style3);
                        f218.setCellValue("Total C.O.P.");

                        Cell f215 = row11.createCell(5, NUMERIC);
                        f215.setCellStyle(style3num);
                        f215.setCellValue(fd(roundDoubleandFormat(totalecopgruppo, 2)));

                        cntriga++;
                        Row row12 = sheet.createRow((short) cntriga);

                        Cell f318 = row12.createCell(4);
                        f318.setCellStyle(style3);
                        f318.setCellValue("General Total");

                        Cell f315 = row12.createCell(5, NUMERIC);
                        f315.setCellStyle(style3num);
                        f315.setCellValue(fd(roundDoubleandFormat(totalegeneralegruppo, 2)));

                        totalefxgruppo = 0;
                        totalecopgruppo = 0;
                        totalegeneralegruppo = 0;
                        //numerofilialipergruppo=0;
                    }
                    numerofilialipergruppo = 0;
                }

//            
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
