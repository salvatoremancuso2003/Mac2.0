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
import static rc.so.util.Constant.formatdataCellint;
import static rc.so.util.Engine.insertTR;
import static rc.so.util.Utility.fd;
import static rc.so.util.Utility.formatDoubleforMysql;
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
 * @author fplacanica
 */
public class TotalStockReport {

    //column
    /**
     *
     */
    public static final float[] columnWidths0 = new float[]{70f, 30f};

    /**
     *
     */
    public static final float[] columnWidths1 = new float[]{40f, 10f};

    /**
     *
     */
    public static float[] columnWidths2 = new float[]{40f, 10f};

    /**
     *
     */
    public static float[] columnWidths3 = new float[]{40f, 10f};

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
    final String intestazionePdf = "No Change Total Stock Report";
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
    public TotalStockReport() {

        this.f0_bold = getFont(HELVETICA, WINANSI, 14.04f, BOLD);
        this.f1_bold = getFont(HELVETICA, WINANSI, 9.96f, BOLD);
        this.f2_bold = getFont(HELVETICA, WINANSI, 8f, BOLD);
        this.f3_bold = getFont(HELVETICA, WINANSI, 8f, BOLDITALIC);
        this.f4_bold = getFont(HELVETICA, WINANSI, 6.96f, BOLD);
        this.f1_normal = getFont(HELVETICA, WINANSI, 9.96f, NORMAL);
        this.f2_normal = getFont(HELVETICA, WINANSI, 8f, NORMAL);
        this.f3_normal = getFont(HELVETICA, WINANSI, 6.96f, NORMAL);

    }

    private void addvaluetolist(ArrayList<Totalrow> tabfinale, Totalrow valore) {

        boolean contains = false;
        int index = 0;

        for (int x = 0; x < tabfinale.size(); x++) {
            Totalrow check = tabfinale.get(x);
            if (check.getCod().equals(valore.getCod())) {
                contains = true;
                index = x;
                break;
            }
        }

        if (contains) {
            Totalrow vdaaagg = tabfinale.get(index);
            double t = vdaaagg.getTotal() + valore.getTotal();
            Totalrow agg = new Totalrow(valore.getCod(), t);
            tabfinale.set(index, agg);
        } else {
            tabfinale.add(valore);
        }

    }

    /**
     *
     * @param path
     * @param tsrlist
     * @param colonne
     * @param daterport
     * @return
     */
    public String receipt(String path, ArrayList<TotalStockReport_value> tsrlist, ArrayList<String> colonne, String daterport) {

        //   String outputfile = "TotalStockReport.pdf";
        try {
            File pdf = new File(path + generaId(50) + "TotalStockReport.pdf");
            Document document = new Document(A4, 20, 20, 20, 20);
            try(OutputStream ou = new FileOutputStream(pdf)){
            PdfWriter wr = getInstance(document, ou);
            document.open();

            ArrayList<Totalrow> tabfinale = new ArrayList<>();
            for (int w = 0; w < tsrlist.size(); w++) {

//            tabfinale.forEach(tsr -> {
                TotalStockReport_value tsr = tsrlist.get(w);

                PdfPTable table = new PdfPTable(2);
                table.setWidths(columnWidths0);
                table.setWidthPercentage(100);

                Phrase phrase1 = new Phrase();
                phrase1.add(new Chunk(intestazionePdf + " " + daterport, f3_bold));
                PdfPCell cell1 = new PdfPCell(phrase1);
                cell1.setBorder(NO_BORDER);

                Paragraph pa1 = new Paragraph(new Phrase("", f2_normal));
                pa1.setAlignment(ALIGN_RIGHT);
                PdfPCell cell2 = new PdfPCell(pa1);
                cell2.setBorder(NO_BORDER);
                cell2.setHorizontalAlignment(ALIGN_RIGHT);

                Phrase phrase3 = new Phrase();
                phrase3.add(new Chunk("\n " + tsr.getId_filiale() + " " + tsr.getDe_filiale(), f3_normal));
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
                    cellt1.setFixedHeight(13f);
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

                ArrayList<TotalStockReport_value> dati = tsr.getDati();
                PdfPCell cell;
                Phrase phraset;
                PdfPCell cellt;

                ArrayList<String> category = new ArrayList<>();
                for (int x = 0; x < dati.size(); x++) {
                    TotalStockReport_value temp = (TotalStockReport_value) dati.get(x);
                    if (!category.contains(temp.getCategoryTrans())) {
                        category.add(temp.getCategoryTrans());
                    }
                }

                //
                // scorro per categoria
                for (int j = 0; j < category.size(); j++) {

                    double totParz = 0;

                    PdfPTable table4 = new PdfPTable(2);
                    table4.setWidths(columnWidths1);
                    table4.setWidthPercentage(100);

                    phraset = new Phrase();
                    phraset.add(new Chunk(category.get(j), f4_bold));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_LEFT);
                    cellt.setBorder(BOTTOM);
                    table4.addCell(cellt);

                    phraset = new Phrase();
                    phraset.add(new Chunk("", f4_bold));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_LEFT);
                    cellt.setBorder(BOTTOM);
                    table4.addCell(cellt);

                    document.add(table4);

                    for (int i = 0; i < dati.size(); i++) {

                        TotalStockReport_value temp = (TotalStockReport_value) dati.get(i);

                        if (temp.getCategoryTrans().equals(category.get(j))) {

                            totParz += fd(formatDoubleforMysql(temp.getStock()));
                            table3 = new PdfPTable(colonne.size());
                            table3.setWidths(columnWidths3);
                            table3.setWidthPercentage(100);

                            phraset = new Phrase();
                            phraset.add(new Chunk(temp.getTill(), f3_normal));
                            cellt = new PdfPCell(phraset);
                            cellt.setHorizontalAlignment(ALIGN_LEFT);
                            cellt.setBorder(BOTTOM);
                            table3.addCell(cellt);

                            phraset = new Phrase();
                            phraset.add(new Chunk(temp.getStock(), f3_normal));
                            cellt = new PdfPCell(phraset);
                            cellt.setHorizontalAlignment(ALIGN_RIGHT);
                            cellt.setBorder(BOTTOM);
                            table3.addCell(cellt);

                            document.add(table3);

                        }

                    }

                    //linea totali parziali
                    PdfPTable table5 = new PdfPTable(colonne.size());
                    table5.setWidths(columnWidths2);
                    table5.setWidthPercentage(100);

                    //linea totale parziale per categoria
                    LineSeparator ls = new LineSeparator();
                    ls.setLineWidth(0.7f);
                    document.add(ls);

                    phraset = new Phrase();
                    phraset.add(new Chunk("Total of Category", f4_bold));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_LEFT);
                    cellt.setBorder(BOTTOM);
                    table5.addCell(cellt);

                    phraset = new Phrase();
                    phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(totParz, 0) + ""), f4_bold));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setBorder(BOTTOM);
                    table5.addCell(cellt);

                    document.add(table5);

                    //linea totale
                    ls.setLineWidth(0.7f);
                    document.add(ls);

                    //String[] v4 = {category.get(j), Utility.formatMysqltoDisplay(Utility.roundDoubleandFormat(totParz, 0))};
                    Totalrow v4 = new Totalrow(category.get(j), totParz);
                    addvaluetolist(tabfinale, v4);

                }

                //Totale finale
                //linea totali
                PdfPTable table4 = new PdfPTable(colonne.size());
                table4.setWidths(columnWidths2);
                table4.setWidthPercentage(100);

                //linea totale parziale per categoria
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

                document.add(table4);

                //linea totale
                ls.setLineWidth(0.7f);
                document.add(ls);
                //

                document.newPage();
            }

            //document.newPage();
            PdfPTable table4 = new PdfPTable(colonne.size());
            table4.setWidths(columnWidths2);
            table4.setWidthPercentage(100);
            Phrase phraset = new Phrase();
            phraset.add(new Chunk("", f0_bold));
            PdfPCell cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setBorder(BOTTOM);
            table4.addCell(cellt);
            phraset = new Phrase();
            phraset.add(new Chunk("", f0_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setBorder(BOTTOM);
            table4.addCell(cellt);
            phraset = new Phrase();
            phraset.add(new Chunk("Total", f0_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_LEFT);
            cellt.setBorder(BOTTOM);
            table4.addCell(cellt);

            phraset = new Phrase();
            phraset.add(new Chunk("", f0_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setBorder(BOTTOM);
            table4.addCell(cellt);

            for (int x = 0; x < tabfinale.size(); x++) {
                Totalrow b1 = tabfinale.get(x);
                phraset = new Phrase();
                phraset.add(new Chunk(b1.getCod(), f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_LEFT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);
                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(b1.getTotal(), 0)),
                        f4_bold));
//                phraset.add(new Chunk(b1[1], f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);
            }
            document.add(table4);

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
     * @param tsrlist
     * @param colonne
     * @param daterport
     * @return
     */
    public String receiptexcel(String path, ArrayList<TotalStockReport_value> tsrlist, ArrayList<String> colonne, String daterport) {

        //   String outputfile = "TotalStockReport.pdf";
        try {
            File pdf = new File(path + generaId(50) + "TotalStockReport.xls");
            HSSFWorkbook workbook = new HSSFWorkbook();

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

            HSSFCellStyle style3bis = (HSSFCellStyle) workbook.createCellStyle();
            style3bis.setFont(font3);
            style3bis.setAlignment(LEFT);
            style3bis.setBorderTop(THIN);
            style3bis.setBorderBottom(THIN);

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
            HSSFCellStyle style3num = (HSSFCellStyle) workbook.createCellStyle();
            style3num.setFont(font3);
            style3num.setAlignment(RIGHT);
            style3num.setBorderTop(THIN);
            style3num.setBorderBottom(THIN);
            style3num.setDataFormat(hssfDataFormat.getFormat(formatdataCell));

            HSSFCellStyle style3numint = (HSSFCellStyle) workbook.createCellStyle();
            style3numint.setFont(font3);
            style3numint.setAlignment(RIGHT);
            style3numint.setBorderTop(THIN);
            style3numint.setBorderBottom(THIN);
            style3numint.setDataFormat(hssfDataFormat.getFormat(formatdataCellint));

            ArrayList<Totalrow> tabfinale = new ArrayList<>();

            for (int w = 0; w < tsrlist.size(); w++) {

                TotalStockReport_value tsr = tsrlist.get(w);

                HSSFSheet sheet = workbook.createSheet("TotalStockReport " + tsr.getId_filiale());

                Row rowP = sheet.createRow((short) 1);

                Cell cl = rowP.createCell(1);
                cl.setCellStyle(style1);
                cl.setCellValue(intestazionePdf + " " + daterport);

//            Cell cl2 = rowP.createCell(6);
//            cl2.setCellStyle(style2);
//            cl2.setCellValue(daterport);
                Row row = sheet.createRow((short) 3);
                row.createCell(1).setCellValue(tsr.getId_filiale() + " " + tsr.getDe_filiale());

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

                ArrayList<TotalStockReport_value> dati = tsr.getDati();

                ArrayList<String> category = new ArrayList<>();
                for (int x = 0; x < dati.size(); x++) {
                    TotalStockReport_value temp = (TotalStockReport_value) dati.get(x);
                    if (!category.contains(temp.getCategoryTrans())) {
                        category.add(temp.getCategoryTrans());
                    }
                }

                //
                int tot = 0;

                // scorro per categoria
                for (int j = 0; j < category.size(); j++) {

                    double totParz = 0;

                    cntriga++;
                    Row row6bis = sheet.createRow((short) cntriga);

                    Cell f1 = row6bis.createCell(1);
                    f1.setCellStyle(style3bis);
                    f1.setCellValue(category.get(j));

                    for (int i = 0; i < dati.size(); i++) {

                        TotalStockReport_value temp = (TotalStockReport_value) dati.get(i);

                        if (temp.getCategoryTrans().equals(category.get(j))) {
                            totParz += fd(formatDoubleforMysql(temp.getStock()));
                            cntriga++;
                            Row row6 = sheet.createRow((short) cntriga);
                            Cell f1bis = row6.createCell(1);
                            f1bis.setCellStyle(style3);
                            f1bis.setCellValue(temp.getTill());
                            Cell f2 = row6.createCell(2, NUMERIC);
                            f2.setCellStyle(style3numint);
                            f2.setCellValue(fd(formatDoubleforMysql(temp.getStock())));
                            cntriga++;
                        }

                    }

                    cntriga++;
                    Row row7 = sheet.createRow((short) cntriga);

                    Cell f18 = row7.createCell(1);
                    f18.setCellStyle(style3left);
                    f18.setCellValue("Total of Category");

                    Cell f19 = row7.createCell(2, NUMERIC);
                    f19.setCellStyle(style3numint);
//                    System.out.println("rc.so.report.TotalStockReport.receiptexcel() "+totParz);
                    f19.setCellValue(fd(roundDoubleandFormat(totParz, 0)));

//                    String[] v4 = {category.get(j), Utility.formatMysqltoDisplay(Utility.roundDoubleandFormat(totParz, 0))};
                    Totalrow v4 = new Totalrow(category.get(j), totParz);
                    addvaluetolist(tabfinale, v4);

                    tot += totParz;

                    cntriga++;
                    cntriga++;
                }

                //Totale finale
                //linea totali
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
            }

            HSSFSheet sheet = workbook.createSheet("Total");

            int cntriga = 0;

            cntriga++;
            cntriga++;
            cntriga++;
            Row row8 = sheet.createRow((short) cntriga);

            Cell f18 = row8.createCell(1);
            f18.setCellStyle(style3left);
            f18.setCellValue("TOTAL");

            cntriga++;

            for (int x = 0; x < tabfinale.size(); x++) {

                Totalrow b1 = tabfinale.get(x);
                Row row81 = sheet.createRow((short) cntriga);
                Cell f181 = row81.createCell(1);
                f181.setCellStyle(style3left);
                f181.setCellValue(b1.getCod());
                Cell f182 = row81.createCell(2, NUMERIC);
                f182.setCellStyle(style3numint);
//                System.out.println("rc.so.report.TotalStockReport.receiptexcel(4) "+fd(b1[1]));
                f182.setCellValue(fd(roundDoubleandFormat(b1.getTotal(), 0)));
                cntriga++;
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

class Totalrow {

    String cod;
    double total;

    public Totalrow(String cod, double total) {
        this.cod = cod;
        this.total = total;
    }

    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

}
