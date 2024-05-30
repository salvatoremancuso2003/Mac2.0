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
import static com.itextpdf.text.pdf.ColumnText.showTextAligned;
import com.itextpdf.text.pdf.PdfContentByte;
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
public class StockReport {

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
    public static float[] columnWidths2 = new float[]{2f, 5f, 15f, 40f, 25f, 10f, 8f, 8f};

    /**
     *
     */
    public static float[] columnWidths3 = new float[]{5f, 15f, 25f, 25f, 10f, 8f, 8f};

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
    final String intestazionePdf = "No Change Stock Report";
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
    public StockReport() {

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
     * @param list_st
     * @param colonne
     * @param datereport1
     * @param datereport2
     * @return
     */
    public String receipt(String path, ArrayList<StockReport_value> list_st, ArrayList<String> colonne, String datereport1, String datereport2) {

        // String outputfile = "StockReport.pdf";
        try {
            File pdf = new File(path + generaId(50) + "StockReport.pdf");
            Document document = new Document(A4, 20, 20, 20, 20);
            try(OutputStream ou = new FileOutputStream(pdf)){
            PdfWriter wr = getInstance(document, ou);
            document.open();
            boolean contains = false;
            for (int o = 0; o < list_st.size(); o++) {
                int numtranin = 0, numdeletran = 0, numtranout = 0, numtotaltran = 0;
                StockReport_value st = list_st.get(o);

                if (st.getDati().size() > 0) {
                    contains = true;
                    PdfPTable table = new PdfPTable(2);
                    table.setWidths(columnWidths0);
                    table.setWidthPercentage(100);

                    Phrase phrase1 = new Phrase();
                    phrase1.add(new Chunk(intestazionePdf + " From " + datereport1 + " To  " + datereport2, f3_bold));
                    PdfPCell cell1 = new PdfPCell(phrase1);
                    cell1.setBorder(NO_BORDER);

                    Paragraph pa1 = new Paragraph(new Phrase("", f2_normal));
                    pa1.setAlignment(ALIGN_RIGHT);
                    PdfPCell cell2 = new PdfPCell(pa1);
                    cell2.setBorder(NO_BORDER);
                    cell2.setHorizontalAlignment(ALIGN_RIGHT);

                    Phrase phrase3 = new Phrase();
                    phrase3.add(new Chunk("\n " + list_st.get(o).getId_filiale() + " " + list_st.get(o).getDe_filiale(), f3_normal));
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

                    PdfPTable table0 = new PdfPTable(2);
                    table0.setWidths(columnWidths0);
                    table0.setWidthPercentage(100);

                    Phrase phraset0;
                    PdfPCell cellt0;
                    phraset0 = new Phrase();
                    phraset0.add(new Chunk(st.getCategoryTrans(), f4_bold));
                    cellt0 = new PdfPCell(phraset0);
                    cellt0.setHorizontalAlignment(ALIGN_LEFT);
                    cellt0.setBorder(BOTTOM);
                    table0.addCell(cellt0);
                    phraset0 = new Phrase();
                    phraset0.add(new Chunk("Initial Stock: " + formatMysqltoDisplay(roundDoubleandFormat(fd(formatDoubleforMysql(st.getInitialStock())), 0)),
                            f4_bold));
                    cellt0 = new PdfPCell(phraset0);
                    cellt0.setHorizontalAlignment(ALIGN_LEFT);
                    cellt0.setBorder(BOTTOM);
                    table0.addCell(cellt0);

                    document.add(table0);

                    vuoto.setFont(f3_normal);
                    document.add(vuoto);

                    ArrayList<StockReport_value> dati = st.getDati();
                    if (!dati.isEmpty()) {
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
                            if (j == 2 || j == 3) {
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
                        Phrase phraset;
                        PdfPCell cellt;

                        double totQuantity = 0.0;
                        double total = 0.0;

                        float availableSpace;

                        for (int i = 0; i < dati.size(); i++) {

                            availableSpace = wr.getVerticalPosition(true) - document.bottomMargin();

                            table3 = new PdfPTable(colonne.size());
                            table3.setWidths(columnWidths2);
                            table3.setWidthPercentage(100);

                            StockReport_value temp = dati.get(i);

                            if (temp.getInout().equals("1") || temp.getInout().equals("3")) {
                                numtranout++;
                            } else if (temp.getInout().equals("2") || temp.getInout().equals("4")) {
                                numtranin++;
                            }

                            if (temp.getAnnullato().equals("1")) {
                                numdeletran++;
                            } else {
                                if (!temp.getKindTrans().toLowerCase().contains("acquisto") && !temp.getKindTrans().toLowerCase().contains("buy")) {
                                    total += fd(formatDoubleforMysql(temp.getTotal()));
                                }
                                totQuantity += fd(formatDoubleforMysql(temp.getQuantity()));
                            }

                            if (temp.getAnnullato().equals("1")) {
                                phraset = new Phrase();
                                phraset.add(new Chunk("D", f3_bold));
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

                            phraset = new Phrase();
                            phraset.add(new Chunk(temp.getTill(), f3_normal));
                            cellt = new PdfPCell(phraset);
                            cellt.setHorizontalAlignment(ALIGN_RIGHT);
                            cellt.setBorder(BOTTOM);
                            table3.addCell(cellt);

                            phraset = new Phrase();
                            phraset.add(new Chunk(temp.getDateTime(), f3_normal));
                            cellt = new PdfPCell(phraset);
                            cellt.setHorizontalAlignment(ALIGN_LEFT);
                            cellt.setBorder(BOTTOM);
                            table3.addCell(cellt);

                            phraset = new Phrase();
                            phraset.add(new Chunk(temp.getKindTrans(), f3_normal));
                            cellt = new PdfPCell(phraset);
                            cellt.setHorizontalAlignment(ALIGN_LEFT);
                            cellt.setBorder(BOTTOM);
                            table3.addCell(cellt);

                            phraset = new Phrase();
                            phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(fd(formatDoubleforMysql(temp.getQuantity())), 0)), f3_normal));
                            cellt = new PdfPCell(phraset);
                            cellt.setHorizontalAlignment(ALIGN_RIGHT);
                            cellt.setBorder(BOTTOM);
                            table3.addCell(cellt);

                            phraset = new Phrase();
                            phraset.add(new Chunk(temp.getPrice(), f3_normal));
                            cellt = new PdfPCell(phraset);
                            cellt.setHorizontalAlignment(ALIGN_RIGHT);
                            cellt.setBorder(BOTTOM);
                            table3.addCell(cellt);

                            phraset = new Phrase();
                            phraset.add(new Chunk(temp.getTotal(), f3_normal));
                            cellt = new PdfPCell(phraset);
                            cellt.setHorizontalAlignment(ALIGN_RIGHT);
                            cellt.setBorder(BOTTOM);
                            table3.addCell(cellt);

                            phraset = new Phrase();
                            phraset.add(new Chunk(temp.getUser(), f3_normal));
                            cellt = new PdfPCell(phraset);
                            cellt.setHorizontalAlignment(ALIGN_RIGHT);
                            cellt.setBorder(BOTTOM);
                            table3.addCell(cellt);

                            document.add(table3);

                            if ((availableSpace < 80) || i == dati.size() - 1) {

                                //footer
                                PdfContentByte cb = wr.getDirectContent();
                                showTextAligned(cb, ALIGN_RIGHT, footer(valueOf(numtranin), valueOf(numdeletran)),
                                        (document.right() - document.left()) / 2 + document.leftMargin(),
                                        document.bottom() + 10, 0);

                                showTextAligned(cb, ALIGN_RIGHT, footer2(valueOf(numtranout), valueOf(numtranin + numtranout - numdeletran)),
                                        (document.right() - document.left()) / 2 + document.leftMargin(),
                                        document.bottom(), 0);

                                //
                            }

                        }

                        //linea totali
                        PdfPTable table4 = new PdfPTable(colonne.size());
                        table4.setWidths(columnWidths2);
                        table4.setWidthPercentage(100);

                        //linea totale
                        LineSeparator ls = new LineSeparator();
                        ls.setLineWidth(0.7f);
                        document.add(ls);

                        phraset = new Phrase();
                        phraset.add(new Chunk("", f3_normal));
                        cellt = new PdfPCell(phraset);
                        cellt.setHorizontalAlignment(ALIGN_RIGHT);
                        cellt.setBorder(BOTTOM);
                        table4.addCell(cellt);

                        phraset = new Phrase();
                        phraset.add(new Chunk("", f3_normal));
                        cellt = new PdfPCell(phraset);
                        cellt.setHorizontalAlignment(ALIGN_RIGHT);
                        cellt.setBorder(BOTTOM);
                        table4.addCell(cellt);

                        phraset = new Phrase();
                        phraset.add(new Chunk("", f3_normal));
                        cellt = new PdfPCell(phraset);
                        cellt.setHorizontalAlignment(ALIGN_RIGHT);
                        cellt.setBorder(BOTTOM);
                        table4.addCell(cellt);

                        phraset = new Phrase();
                        phraset.add(new Chunk("Total from transaction listed", f4_bold));
                        cellt = new PdfPCell(phraset);
                        cellt.setHorizontalAlignment(ALIGN_LEFT);
                        cellt.setBorder(BOTTOM);
                        table4.addCell(cellt);

                        phraset = new Phrase();
                        phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(totQuantity, 0)) + "", f4_bold));
                        cellt = new PdfPCell(phraset);
                        cellt.setHorizontalAlignment(ALIGN_RIGHT);
                        cellt.setBorder(BOTTOM);
                        table4.addCell(cellt);

                        phraset = new Phrase();
                        phraset.add(new Chunk("", f3_normal));
                        cellt = new PdfPCell(phraset);
                        cellt.setHorizontalAlignment(ALIGN_RIGHT);
                        cellt.setBorder(BOTTOM);
                        table4.addCell(cellt);

                        phraset = new Phrase();
                        phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(total, 2)) + "", f4_bold));
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
                        phraset.add(new Chunk("", f3_normal));
                        cellt = new PdfPCell(phraset);
                        cellt.setHorizontalAlignment(ALIGN_RIGHT);
                        cellt.setBorder(BOTTOM);
                        table4.addCell(cellt);

                        document.add(table4);

                        //linea totali
                        table4 = new PdfPTable(colonne.size());
                        table4.setWidths(columnWidths2);
                        table4.setWidthPercentage(100);

                        phraset = new Phrase();
                        phraset.add(new Chunk("", f3_normal));
                        cellt = new PdfPCell(phraset);
                        cellt.setHorizontalAlignment(ALIGN_RIGHT);
                        cellt.setBorder(BOTTOM);
                        table4.addCell(cellt);

                        phraset = new Phrase();
                        phraset.add(new Chunk("", f3_normal));
                        cellt = new PdfPCell(phraset);
                        cellt.setHorizontalAlignment(ALIGN_RIGHT);
                        cellt.setBorder(BOTTOM);
                        table4.addCell(cellt);

                        phraset = new Phrase();
                        phraset.add(new Chunk("", f3_normal));
                        cellt = new PdfPCell(phraset);
                        cellt.setHorizontalAlignment(ALIGN_RIGHT);
                        cellt.setBorder(BOTTOM);
                        table4.addCell(cellt);

                        phraset = new Phrase();
                        phraset.add(new Chunk("Actual Stock", f4_bold));
                        cellt = new PdfPCell(phraset);
                        cellt.setHorizontalAlignment(ALIGN_LEFT);
                        cellt.setBorder(BOTTOM);
                        table4.addCell(cellt);

                        phraset = new Phrase();

                        phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(fd(formatDoubleforMysql(st.getInitialStock())) + totQuantity, 0)) + "", f4_bold));
                        cellt = new PdfPCell(phraset);
                        cellt.setHorizontalAlignment(ALIGN_RIGHT);
                        cellt.setBorder(BOTTOM);
                        table4.addCell(cellt);

                        phraset = new Phrase();
                        phraset.add(new Chunk("", f3_normal));
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
                        phraset.add(new Chunk("", f4_bold));
                        cellt = new PdfPCell(phraset);
                        cellt.setHorizontalAlignment(ALIGN_RIGHT);
                        cellt.setBorder(BOTTOM);
                        table4.addCell(cellt);

                        phraset = new Phrase();
                        phraset.add(new Chunk("", f3_normal));
                        cellt = new PdfPCell(phraset);
                        cellt.setHorizontalAlignment(ALIGN_RIGHT);
                        cellt.setBorder(BOTTOM);
                        table4.addCell(cellt);

                        document.add(table4);

                        //linea totale
                        ls.setLineWidth(0.7f);
                        document.add(ls);

                    }

                }

                document.newPage();
            }

            if (!contains) {
                ou.close();
                return null;
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
     * @param list_st
     * @param colonne
     * @param datereport1
     * @param datereport2
     * @return
     */
    public String receiptexcel(String path, ArrayList<StockReport_value> list_st, ArrayList<String> colonne, String datereport1, String datereport2) {

        // String outputfile = "StockReport.pdf";
        try {

            File pdf = new File(path + generaId(50) + "StockReport.xls");
            HSSFWorkbook workbook = new HSSFWorkbook();

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
            cellStylenum.setAlignment(RIGHT);
            cellStylenum.setBorderTop(THIN);
            cellStylenum.setBorderBottom(THIN);

            HSSFCellStyle cellStylenumint = (HSSFCellStyle) workbook.createCellStyle();
            cellStylenumint.setDataFormat(hssfDataFormat.getFormat(formatdataCellint));
            cellStylenumint.setAlignment(RIGHT);
            cellStylenumint.setBorderTop(THIN);
            cellStylenumint.setBorderBottom(THIN);

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

            int cnt_sheet = 1;

            for (int o = 0; o < list_st.size(); o++) {
                int cntriga = 1;
                int numtranin = 0, numdeletran = 0, numtranout = 0, numtotaltran = 0;
                StockReport_value st = list_st.get(o);
                if (st.getDati().size() > 0) {

                    HSSFSheet sheet = workbook.createSheet(list_st.get(o).getId_filiale() + "_" + cnt_sheet);
                    cnt_sheet++;

                    Row rowP = sheet.createRow((short) cntriga);
                    cntriga++;
                    cntriga++;

                    Cell cl = rowP.createCell(1);
                    cl.setCellStyle(style1);
                    cl.setCellValue(intestazionePdf + " From " + datereport1 + " To " + datereport2);

                    Row row = sheet.createRow((short) cntriga);
                    cntriga++;
                    cntriga++;
                    cntriga++;
                    cntriga++;
                    row.createCell(1).setCellValue("\n " + list_st.get(o).getId_filiale() + " " + list_st.get(o).getDe_filiale());

                    Row row61 = sheet.createRow((short) cntriga);

                    cntriga++;
                    cntriga++;

                    Cell cl5 = row61.createCell(1);
                    cl5.setCellStyle(style3left);
                    cl5.setCellValue(st.getCategoryTrans());

                    Cell cl6 = row61.createCell(2);
                    cl6.setCellStyle(style3);
                    cl6.setCellValue("Initial Stock: " + formatMysqltoDisplay(roundDoubleandFormat(fd(formatDoubleforMysql(st.getInitialStock())), 0)));

                    ArrayList<StockReport_value> dati = st.getDati();
                    if (!dati.isEmpty()) {

                        Row row66 = sheet.createRow((short) cntriga);

                        cntriga++;

                        //mi scandisco le colonne
                        for (int j = 0; j < colonne.size(); j++) {
                            Cell cl7 = row66.createCell(j + 1);
                            cl7.setCellStyle(style3);
                            if (j == 2 || j == 3) {
                                cl7.setCellStyle(style3left);
                            }
                            cl7.setCellValue(colonne.get(j));
                        }

                        double totQuantity = 0.0;
                        double total = 0.0;

                        float availableSpace = 0;

                        for (int i = 0; i < dati.size(); i++) {

                            StockReport_value temp = dati.get(i);

                            if (temp.getInout().equals("1") || temp.getInout().equals("3")) {
                                numtranout++;
                            } else if (temp.getInout().equals("2") || temp.getInout().equals("4")) {
                                numtranin++;
                            }

                            if (temp.getAnnullato().equals("1")) {
                                numdeletran++;
                            } else {

                                if (!temp.getKindTrans().toLowerCase().contains("acquisto") && !temp.getKindTrans().toLowerCase().contains("buy")) {
                                    total += fd(formatDoubleforMysql(temp.getTotal()));
                                }

                                totQuantity += fd(formatDoubleforMysql(temp.getQuantity()));
                            }

                            cntriga++;
                            Row row6 = sheet.createRow((short) cntriga);

                            if (temp.getAnnullato().equals("1")) {
                                // numdeletran++;
                                Cell f0 = row6.createCell(1);
                                f0.setCellStyle(style3);
                                f0.setCellValue("D");
                            }

                            Cell f1bis = row6.createCell(2);
                            f1bis.setCellStyle(style4);
                            f1bis.setCellValue(temp.getTill());

                            Cell f2 = row6.createCell(3);
                            f2.setCellStyle(style4left);
                            f2.setCellValue(temp.getDateTime());

                            Cell f3 = row6.createCell(4);
                            f3.setCellStyle(style4left);
                            f3.setCellValue(temp.getKindTrans());

                            Cell f4 = row6.createCell(5, NUMERIC);
                            f4.setCellStyle(style4);
                            f4.setCellStyle(cellStylenumint);

                            f4.setCellValue(fd(roundDoubleandFormat(fd(formatDoubleforMysql(temp.getQuantity())), 0)));

                            Cell f6 = row6.createCell(6, NUMERIC);
                            f6.setCellStyle(cellStylenum);
                            f6.setCellValue(fd(formatDoubleforMysql(temp.getPrice())));

                            Cell f7 = row6.createCell(7, NUMERIC);
                            f7.setCellStyle(cellStylenum);
                            f7.setCellValue(fd(formatDoubleforMysql(temp.getTotal())));

                            Cell f8 = row6.createCell(8, NUMERIC);
                            f8.setCellStyle(cellStylenum);
                            f8.setCellValue(temp.getUser());

                            cntriga++;
                            cntriga++;
                        }

                        Row row7 = sheet.createRow((short) cntriga);

                        //linea totale
                        Cell f18 = row7.createCell(4);
                        f18.setCellStyle(style3left);
                        f18.setCellValue("Total from transaction listed");

                        Cell f19 = row7.createCell(5, NUMERIC);
                        f19.setCellStyle(style3numint);
                        f19.setCellValue(fd(roundDoubleandFormat(totQuantity, 0)));

                        Cell f20 = row7.createCell(7, NUMERIC);
                        f20.setCellStyle(style3num);
                        f20.setCellValue(fd(roundDoubleandFormat(total, 2)));

                        cntriga++;
                        Row row8 = sheet.createRow((short) cntriga);

                        Cell f20bis = row8.createCell(4);
                        f20bis.setCellStyle(style3left);
                        f20bis.setCellValue("Actual Stock");

                        Cell f21 = row8.createCell(5, NUMERIC);
                        f21.setCellStyle(style3numint);
                        f21.setCellValue(fd(roundDoubleandFormat(fd(formatDoubleforMysql(st.getInitialStock())) + totQuantity, 0)));

                    }

                    cntriga++;
                    cntriga++;
                    cntriga++;
                    cntriga++;
                    cntriga++;

                    Row row777bis = sheet.createRow((short) cntriga);

                    Cell f307 = row777bis.createCell(5);
                    f307.setCellStyle(style3);
                    f307.setCellValue("No. of Transaction In");

                    Cell f3072 = row777bis.createCell(6);
                    f3072.setCellStyle(style3);
                    f3072.setCellValue("No. of Deleted Transaction");

                    cntriga++;

                    Row row777 = sheet.createRow((short) cntriga);

                    Cell f30 = row777.createCell(5, NUMERIC);
                    f30.setCellStyle(style3numint);
                    f30.setCellValue(fd(roundDoubleandFormat(numtranin, 0)));

                    Cell f301 = row777.createCell(6, NUMERIC);
                    f301.setCellStyle(style3numint);
                    f301.setCellValue(fd(roundDoubleandFormat(numdeletran, 0)));

                    cntriga++;
                    cntriga++;
                    Row row888bis = sheet.createRow((short) cntriga);

                    Cell f318 = row888bis.createCell(5);
                    f318.setCellStyle(style3);
                    f318.setCellValue("No. of Transaction Out");

                    Cell f3182 = row888bis.createCell(6);
                    f3182.setCellStyle(style3);
                    f3182.setCellValue("No. of Total Transaction ");

                    cntriga++;
                    Row row888 = sheet.createRow((short) cntriga);

                    Cell f31 = row888.createCell(5, NUMERIC);
                    f31.setCellStyle(style3numint);
                    f31.setCellValue(fd(roundDoubleandFormat(numtranout, 0)));

                    Cell f312 = row888.createCell(6, NUMERIC);
                    f312.setCellStyle(style3numint);
                    f312.setCellValue(fd(roundDoubleandFormat(numtranin + numtranout - numdeletran, 0)));

                    cntriga++;
                    cntriga++;
                    cntriga++;
                    cntriga++;
                    cntriga++;
                    cntriga++;
                    cntriga++;

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

            }

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

    //sistemare il footer e rendere i totali corretti
    private Phrase footer(String tot1, String tot2) {
        Font ffont = new Font(f3_normal);
        Phrase p = new Phrase("No. of Transaction In        " + tot1 + "         No. of Deleted Transaction      " + tot2 + " ", ffont);
        return p;
    }

    private Phrase footer2(String tot1, String tot2) {
        Font ffont = new Font(f3_normal);
        Phrase p = new Phrase("No. of Transaction Out      " + tot1 + "         No. of Total Transaction        " + tot2 + " ", ffont);
        return p;
    }

    // fine footer
}
