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
import static rc.so.util.Constant.formatdataCellRate;
import static rc.so.util.Constant.newpread;
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
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import static org.apache.poi.ss.usermodel.BorderStyle.THIN;
import static org.apache.poi.ss.usermodel.CellType.NUMERIC;
import static org.apache.poi.ss.usermodel.HorizontalAlignment.LEFT;
import static org.apache.poi.ss.usermodel.HorizontalAlignment.RIGHT;

/**
 *
 * @author vcrugliano
 */
public class OfficeStockPriceBranch {

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
    public static float[] columnWidths2 = new float[]{30f, 20f, 15f, 15f};

    /**
     *
     */
    public static float[] columnWidths3 = new float[]{30f, 20f, 15f, 15f, 15f};
    final String intestazionePdf = "Office Stock Price Report Currency";
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
    public OfficeStockPriceBranch() {

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
     * @param osplist
     * @param colonne
     * @param datereport
     * @return
     */
    public String receiptcentrale(String path, ArrayList<OfficeStockPriceBranch_value> osplist, ArrayList<String> colonne, String datereport) {

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

            for (int q = 0; q < osplist.size(); q++) {

                OfficeStockPriceBranch_value osp = osplist.get(q);

//                String valutalocale = osp.getLocalcurrency();
//                String gruppo = osp.getGruppo();
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
                phrase3.add(new Chunk("\n " + osp.getCurrency() + " " + osp.getSupporto(), f3_bold));
                cell3 = new PdfPCell(phrase3);
                cell3.setBorder(BOTTOM);

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
//                document.add(vuoto);

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
                sep.setOffset(-1);
                sep.setLineWidth((float) 0.5);

                //  document.add(table2);
                //   document.add(sep);
                ArrayList<String[]> dati = osp.getDati();
                PdfPCell cell;
                Phrase phraset;
                PdfPCell cellt;

                table3 = new PdfPTable(colonne.size());
                table3.setWidths(columnWidths2);
                table3.setWidthPercentage(100);

                double totaleqty = 0; //totale controvalore della valuta locale
                double totaleavg = 0; //
                double totalecontrovalore = 0; //totale controvalore della valuta locale

                for (int i = 0; i < dati.size(); i++) {

                    String[] temp = dati.get(i);

                    phraset = new Phrase();
                    phraset.add(new Chunk(temp[0], f3_normal));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_LEFT);
                    cellt.setBorder(BOTTOM);
                    table3.addCell(cellt);

                    phraset = new Phrase();
                    phraset.add(new Chunk(formatMysqltoDisplay(temp[1]), f3_normal));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setBorder(BOTTOM);
                    table3.addCell(cellt);

                    phraset = new Phrase();
                    phraset.add(new Chunk(formatMysqltoDisplay(temp[2]), f3_normal));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setBorder(BOTTOM);
                    table3.addCell(cellt);

                    phraset = new Phrase();
                    phraset.add(new Chunk(formatMysqltoDisplay(temp[3]), f3_normal));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setBorder(BOTTOM);
                    table3.addCell(cellt);

//                    phraset = new Phrase();
//                    phraset.add(new Chunk(temp.getControvalore(), f3_normal));
//                    cellt = new PdfPCell(phraset);
//                    cellt.setHorizontalAlignment(Element.ALIGN_RIGHT);
//                    cellt.setBorder(Rectangle.BOTTOM);
//                    table3.addCell(cellt);
                    totaleqty += fd(temp[1]);
                    totaleavg += fd(temp[2]);
                    totalecontrovalore += fd(temp[3]);

                }

                totaleqty = roundDouble(totaleqty, 2);
                totaleavg = roundDouble(totaleavg, 8);
                totalecontrovalore = roundDouble(totalecontrovalore, 2);

                document.add(table3);

                table3 = new PdfPTable(colonne.size());
                table3.setWidths(columnWidths2);
                table3.setWidthPercentage(100);

                phraset = new Phrase();
                phraset.add(new Chunk("Total", f3_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_LEFT);
                cellt.setBorder(NO_BORDER);
                table3.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(totaleqty, 2)), f3_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(NO_BORDER);
                table3.addCell(cellt);

                phraset = new Phrase();
                if (newpread && dati.size() > 0) {
                    phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(totaleavg / dati.size(), 8)), f3_bold));
                } else if (totalecontrovalore > 0) {
                    phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(totaleqty / totalecontrovalore, 8)), f3_bold));
                }
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(NO_BORDER);
                cellt.setBorderWidth(0.5f);
                table3.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(totalecontrovalore, 2)), f3_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(NO_BORDER);
                table3.addCell(cellt);

                document.add(table3);

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
    public String receiptcentraleexcel(String path, ArrayList<OfficeStockPriceBranch_value> osplist, ArrayList<String> colonne, String datereport) {

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
            style1.setAlignment(LEFT);
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
            
            HSSFDataFormat hssfDataFormat = (HSSFDataFormat) workbook.createDataFormat();
            
            HSSFCellStyle style4numRATE = (HSSFCellStyle) workbook.createCellStyle();
            style4numRATE.setAlignment(RIGHT);
            style4numRATE.setBorderTop(THIN);
            style4numRATE.setBorderBottom(THIN);
            style4numRATE.setDataFormat(hssfDataFormat.getFormat(formatdataCellRate));

            HSSFCellStyle cellStylenum = (HSSFCellStyle) workbook.createCellStyle();
            
            cellStylenum.setDataFormat(hssfDataFormat.getFormat(formatdataCell));
            cellStylenum.setAlignment(RIGHT);
            cellStylenum.setBorderTop(THIN);
            cellStylenum.setBorderBottom(THIN);

            HSSFCellStyle style3numRATE = (HSSFCellStyle) workbook.createCellStyle();
            style3numRATE.setFont(font3);
            style3numRATE.setAlignment(RIGHT);
            style3numRATE.setBorderTop(THIN);
            style3numRATE.setBorderBottom(THIN);
            style3numRATE.setDataFormat(hssfDataFormat.getFormat(formatdataCellRate));

            HSSFCellStyle style3num = (HSSFCellStyle) workbook.createCellStyle();
            style3num.setFont(font3);
            style3num.setAlignment(RIGHT);
            style3num.setBorderTop(THIN);
            style3num.setBorderBottom(THIN);
            style3num.setDataFormat(hssfDataFormat.getFormat(formatdataCell));

            Row rowP = sheet.createRow((short) 1);

            Cell cl = rowP.createCell(1);
            cl.setCellStyle(style1);
            cl.setCellValue(intestazionePdf + " " + datereport);

            int cntriga = 5;

            for (int q = 0; q < osplist.size(); q++) {

                OfficeStockPriceBranch_value osp = osplist.get(q);

                cntriga++;
                cntriga++;
                Row rowP1 = sheet.createRow((short) cntriga);

                Cell cl1 = rowP1.createCell(1);
                cl1.setCellStyle(style1);
                cl1.setCellValue("\n " + osp.getCurrency() + " " + osp.getSupporto());

                cntriga++;

                Row row66 = sheet.createRow((short) cntriga);

                //mi scandisco le colonne
                for (int c = 0; c < colonne.size(); c++) {
                    Cell cl7 = row66.createCell(c + 1);
                    cl7.setCellStyle(style3);
                    if (c == 0) {
                        cl7.setCellStyle(style3left);
                    }
                    cl7.setCellValue(colonne.get(c));
                }

                cntriga++;

                //   document.add(sep);
                ArrayList<String[]> dati = osp.getDati();

                double totaleqty = 0;
                double totaleavg = 0;
                double totalecontrovalore = 0;

                for (int i = 0; i < dati.size(); i++) {

                    String[] temp = dati.get(i);

                    cntriga++;
                    Row row6 = sheet.createRow((short) cntriga);

                    Cell f1bis1 = row6.createCell(1);
                    f1bis1.setCellStyle(style4left);
                    f1bis1.setCellValue(temp[0]);

                    Cell f2 = row6.createCell(2, NUMERIC);
                    f2.setCellStyle(cellStylenum);
                    f2.setCellValue(fd(temp[1]));

                    Cell f3 = row6.createCell(3, NUMERIC);
                    f3.setCellStyle(style4numRATE);
                    f3.setCellValue(fd(temp[2]));

                    Cell f4 = row6.createCell(4, NUMERIC);
                    f4.setCellStyle(cellStylenum);
                    f4.setCellValue(fd(temp[3]));

                    totaleqty += fd(temp[1]);
                    totaleavg += fd(temp[2]);
                    totalecontrovalore += fd(temp[3]);

                }

                totaleqty = roundDouble(totaleqty, 2);
                totaleavg = roundDouble(totaleavg / dati.size(), 8);
                totalecontrovalore = roundDouble(totalecontrovalore, 2);

                cntriga++;
                cntriga++;
                Row row7 = sheet.createRow((short) cntriga);

                Cell f70 = row7.createCell(1);
                f70.setCellStyle(style3left);
                f70.setCellValue("Total");

                Cell f701 = row7.createCell(2, NUMERIC);
                f701.setCellStyle(style3num);
                f701.setCellValue(fd(roundDoubleandFormat(totaleqty, 2)));

                Cell f71 = row7.createCell(3, NUMERIC);
                f71.setCellStyle(style3numRATE);
                if (newpread) {
                    f71.setCellValue(fd(roundDoubleandFormat(totaleavg, 8)));
                } else {
                    if(totalecontrovalore != 0){
                    f71.setCellValue(fd(roundDoubleandFormat(totaleqty / totalecontrovalore, 8)));
                }
                Cell f72 = row7.createCell(4, NUMERIC);
                f72.setCellStyle(style3num);
                f72.setCellValue(fd(roundDoubleandFormat(totalecontrovalore, 2)));

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
        }
        } catch (IOException ex) {
            insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
        }
        return null;
    }

}
