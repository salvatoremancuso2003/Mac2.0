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
import static rc.so.util.Constant.localcurrency;
import static rc.so.util.Engine.insertTR;
import static rc.so.util.Utility.fd;
import static rc.so.util.Utility.ff;
import static rc.so.util.Utility.formatMysqltoDisplay;
import static rc.so.util.Utility.parseIntR;
import static rc.so.util.Utility.roundDoubleandFormat;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import static java.lang.Integer.parseInt;
import static java.lang.String.valueOf;
import static java.lang.Thread.currentThread;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import static org.apache.commons.codec.binary.Base64.encodeBase64;
import static org.apache.commons.io.FileUtils.readFileToByteArray;
import static org.apache.poi.hssf.usermodel.HSSFFont.FONT_ARIAL;
import static org.apache.poi.ss.usermodel.BorderStyle.THIN;
import org.apache.poi.ss.usermodel.Cell;
import static org.apache.poi.ss.usermodel.HorizontalAlignment.CENTER;
import static org.apache.poi.ss.usermodel.HorizontalAlignment.LEFT;
import static org.apache.poi.ss.usermodel.HorizontalAlignment.RIGHT;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author fplacanica
 */
public class Daily {

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
    public static float[] columnWidths2 = new float[]{20f, 20f, 20f, 20f, 20f};

    /**
     *
     */
    public static float[] columnWidths2a = new float[]{20f, 20f, 20f, 20f, 20f, 20f};

    /**
     *
     */
    public static float[] columnWidths3 = new float[]{20f, 20f, 20f, 20f, 20f};

    /**
     *
     */
    public static float[] columnWidths3b = new float[]{10f, 20f, 20f, 20f, 20f};

    /**
     *
     */
    public static float[] columnWidths3d = new float[]{10f, 20f, 20f, 20f, 20f, 20f, 20f};

    /**
     *
     */
    public static float[] columnWidths3c = new float[]{20f, 20f, 20f, 20f};

    /**
     *
     */
    public static float[] columnWidths3a = new float[]{20f, 20f, 20f, 20f, 20f, 20f, 20f, 20f, 20f};

    /**
     *
     */
    public static float[] columnWidthsBank = new float[]{40f, 20f, 20f, 20f, 20f, 20f};

    /**
     *
     */
    public static float[] columnWidthsBank1 = new float[]{20f, 20f, 20f, 20f, 20f, 20f, 20f};

    /**
     *
     */
    public static float[] columnWidths4 = new float[]{20f, 20f, 20f, 20f, 20f, 20f};

    /**
     *
     */
    public static float[] columnWidths5 = new float[]{20f, 20f, 20f, 20f, 20f};

    /**
     *
     */
    public static float[] columnWidths6 = new float[]{20f, 20f, 20f, 20f, 20f, 20f, 20f};

    /**
     *
     */
    public static float[] columnWidths7 = new float[]{30f, 20f, 20f, 20f, 30f, 20f};

    /**
     *
     */
    public static float[] columnWidths8 = new float[]{30f, 20f, 20f, 20f, 20f, 20f, 20f};
    final String intestazionePdf = "Daily Result";
    Phrase vuoto = new Phrase("\n", getFont(HELVETICA, WINANSI, 6f, NORMAL));

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
    public Daily() {

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
     * @param listsiq
     * @param datereport1
     * @param datereport2
     * @param central
     * @return
     */
    public String receipt(String path, ArrayList<Daily_value> listsiq, String datereport1, String datereport2, boolean central) {

        //String outputfile = "Daily.pdf";
        try {
            vuoto.setLeading(8f);
            DateFormat dateFormat = new SimpleDateFormat("ddddMMyyyy");
            Date date = new Date();
            new File(path).mkdirs();
            File pdf = new File(path + "Daily" + "_" + dateFormat.format(date) + ".pdf");
            //File pdf = new File(path + Utility.generaId(50) + "Daily.pdf");
            Document document = new Document(A4, 20, 20, 20, 20);
            try(OutputStream ou = new FileOutputStream(pdf)){
            PdfWriter wr = getInstance(document, ou);
            document.open();

            for (int index = 0; index < listsiq.size(); index++) {

                Daily_value siq = listsiq.get(index);

                PdfPTable table = new PdfPTable(2);
                table.setWidths(columnWidths0);
                table.setWidthPercentage(100);

                Phrase phrase1 = new Phrase();
                phrase1.add(new Chunk(intestazionePdf + " From " + datereport1 + " To " + datereport2, f3_bold));
                PdfPCell cell1 = new PdfPCell(phrase1);
                cell1.setBorder(NO_BORDER);

                //Paragraph pa1 = new Paragraph(new Phrase(datereport, f2_normal));
                Paragraph pa1 = new Paragraph(new Phrase("", f2_normal));
                pa1.setAlignment(ALIGN_CENTER);
                PdfPCell cell2 = new PdfPCell(pa1);
                cell2.setBorder(NO_BORDER);
                cell2.setHorizontalAlignment(ALIGN_RIGHT);

                Phrase phrase3 = new Phrase();

                String d1 = "\n " + siq.getId_filiale() + " " + siq.getDe_filiale();
                if (central) {
                    d1 = "\n " + siq.getId_filiale() + " " + siq.getDe_filiale() + " - Date: " + siq.getData();
                }
                phrase3.add(new Chunk(d1, f3_normal));
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
                table.setSpacingAfter(5f);
                document.add(table);
//
//                vuoto.setFont(f3_normal);
//                document.add(vuoto);

                //1 tabella
                PdfPTable table2 = new PdfPTable(5);
                table2.setWidths(columnWidths2);
                table2.setWidthPercentage(100);

                PdfPTable table3 = new PdfPTable(6);
                table3.setWidths(columnWidths2a);
                table3.setWidthPercentage(100);

                //colonne
                Phrase phraset1 = new Phrase();
                phraset1.add(new Chunk("Till", f4_bold));
                PdfPCell cellt1 = new PdfPCell(phraset1);
                cellt1.setHorizontalAlignment(ALIGN_LEFT);
                cellt1.setBorder(BOTTOM | TOP);
                cellt1.setFixedHeight(10f);
                cellt1.setBackgroundColor(LIGHT_GRAY);
                //cellt1.setBorder(Rectangle.BOTTOM);
                cellt1.setBorderWidth(0.7f);
                table3.addCell(cellt1);

                phraset1 = new Phrase();
                phraset1.add(new Chunk("Pay IN/OUT", f4_bold));
                cellt1 = new PdfPCell(phraset1);
                cellt1.setHorizontalAlignment(ALIGN_RIGHT);
                cellt1.setBorder(BOTTOM | TOP);
                cellt1.setFixedHeight(10f);
                cellt1.setBackgroundColor(LIGHT_GRAY);
                //cellt1.setBorder(Rectangle.BOTTOM);
                cellt1.setBorderWidth(0.7f);
                table3.addCell(cellt1);

                phraset1 = new Phrase();
                phraset1.add(new Chunk("Commissions and Round off", f4_bold));
                cellt1 = new PdfPCell(phraset1);
                cellt1.setHorizontalAlignment(ALIGN_RIGHT);
                cellt1.setBorder(BOTTOM | TOP);
                cellt1.setFixedHeight(15f);
                cellt1.setBackgroundColor(LIGHT_GRAY);
                //cellt1.setBorder(Rectangle.BOTTOM);
                cellt1.setBorderWidth(0.7f);
                table3.addCell(cellt1);

                phraset1 = new Phrase();
                phraset1.add(new Chunk("Gross/Net Total", f4_bold));
                cellt1 = new PdfPCell(phraset1);
                cellt1.setHorizontalAlignment(ALIGN_RIGHT);
                cellt1.setBorder(BOTTOM | TOP);
                cellt1.setFixedHeight(10f);
                cellt1.setBackgroundColor(LIGHT_GRAY);
                //cellt1.setBorder(Rectangle.BOTTOM);
                cellt1.setBorderWidth(0.7f);
                table3.addCell(cellt1);

                phraset1 = new Phrase();
                phraset1.add(new Chunk("Spread", f4_bold));
                cellt1 = new PdfPCell(phraset1);
                cellt1.setHorizontalAlignment(ALIGN_RIGHT);
                cellt1.setBorder(BOTTOM | TOP);
                cellt1.setFixedHeight(10f);
                cellt1.setBackgroundColor(LIGHT_GRAY);
                //cellt1.setBorder(Rectangle.BOTTOM);
                cellt1.setBorderWidth(0.7f);
                table3.addCell(cellt1);

                phraset1 = new Phrase();
                phraset1.add(new Chunk("Profit", f4_bold));
                cellt1 = new PdfPCell(phraset1);
                cellt1.setHorizontalAlignment(ALIGN_RIGHT);
                cellt1.setBorder(BOTTOM | TOP);
                cellt1.setFixedHeight(15f);
                cellt1.setBackgroundColor(LIGHT_GRAY);
                //cellt1.setBorder(Rectangle.BOTTOM);
                cellt1.setBorderWidth(0.7f);
                table3.addCell(cellt1);

                document.add(table3);

                LineSeparator sep = new LineSeparator();
                sep.setOffset(-2);
                sep.setLineWidth((float) 0.5);

//                ArrayList<String> dati = siq.getDati();
//                PdfPCell cell;
                Phrase phraset;
                PdfPCell cellt;

                table3 = new PdfPTable(6);
                table3.setWidths(columnWidths2a);
                table3.setWidthPercentage(100);

                ArrayList<String[]> tot = new ArrayList<>();

                //popolamento tabella 1
                //1 riga
                phraset1 = new Phrase();
                phraset1.add(new Chunk("Purchases", f4_bold));
                cellt1 = new PdfPCell(phraset1);
                cellt1.setHorizontalAlignment(ALIGN_LEFT);
                cellt1.setBorder(BOTTOM | TOP);
//                cellt1.setFixedHeight(10f);
                cellt1.setBackgroundColor(LIGHT_GRAY);
                table3.addCell(cellt1);

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(siq.getPurchTotal()), f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table3.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(siq.getPurchComm()), f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table3.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(siq.getPurchGrossTot()), f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table3.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(siq.getPurchSpread()), f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table3.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(siq.getPurchProfit()), f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table3.addCell(cellt);
                //fine 1 riga

                //2 riga
                phraset1 = new Phrase();
                phraset1.add(new Chunk("Sales", f4_bold));
                cellt1 = new PdfPCell(phraset1);
                cellt1.setHorizontalAlignment(ALIGN_LEFT);
                cellt1.setBorder(BOTTOM | TOP);
//                cellt1.setFixedHeight(10f);
                cellt1.setBackgroundColor(LIGHT_GRAY);
                table3.addCell(cellt1);

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(siq.getSalesTotal()), f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table3.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(siq.getSalesComm()), f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table3.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(siq.getSalesGrossTot()), f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table3.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(siq.getSalesSpread()), f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table3.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(siq.getSalesProfit()), f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table3.addCell(cellt);
                //fine 2 riga

                //3 riga
                phraset1 = new Phrase();
                phraset1.add(new Chunk("Cash Advance", f4_bold));
                cellt1 = new PdfPCell(phraset1);
                cellt1.setHorizontalAlignment(ALIGN_LEFT);
                cellt1.setBorder(BOTTOM | TOP);
//                cellt1.setFixedHeight(10f);
                cellt1.setBackgroundColor(LIGHT_GRAY);
                table3.addCell(cellt1);

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(siq.getCashAdNetTot()), f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table3.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(siq.getCashAdComm()), f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table3.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(siq.getCashAdGrossTot()), f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table3.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(siq.getCashAdSpread()), f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table3.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(siq.getCashAdProfit()), f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table3.addCell(cellt);
                //fine 3 riga

                //4 riga totali
                double totnet, totcomm, totgross, totspread, totprofit;

                totnet = fd(siq.getPurchTotal()) + ff(siq.getSalesTotal()) + fd(siq.getCashAdNetTot());
                totcomm = fd(siq.getPurchComm()) + fd(siq.getSalesComm()) + fd(siq.getCashAdComm());
                totgross = fd(siq.getPurchGrossTot()) + fd(siq.getSalesGrossTot()) + fd(siq.getCashAdGrossTot());
                totspread = fd(siq.getPurchSpread()) + fd(siq.getSalesSpread()) + fd(siq.getCashAdSpread());
                totprofit = fd(siq.getPurchProfit()) + fd(siq.getSalesProfit()) + fd(siq.getCashAdProfit());

                phraset1 = new Phrase();
                phraset1.add(new Chunk("Total", f4_bold));
                cellt1 = new PdfPCell(phraset1);
                cellt1.setHorizontalAlignment(ALIGN_LEFT);
                cellt1.setBorder(BOTTOM | TOP);
//                cellt1.setFixedHeight(10f);
                cellt1.setBackgroundColor(LIGHT_GRAY);
                table3.addCell(cellt1);

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(totnet, 2)) + "", f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table3.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(totcomm, 2)) + "", f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table3.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(totgross, 2)) + "", f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table3.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(totspread, 2)) + "", f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table3.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(totprofit, 2)) + "", f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table3.addCell(cellt);

                //fine 4 riga
                document.add(table3);
                //fine 1 tabella

                vuoto.setFont(f3_normal);
                document.add(vuoto);

                //2 tabella
                table3 = new PdfPTable(5);
                table3.setWidths(columnWidths3b);
                table3.setWidthPercentage(100);

                //colonne
                phraset1 = new Phrase();
                phraset1.add(new Chunk("POS", f4_bold));
                cellt1 = new PdfPCell(phraset1);
                cellt1.setHorizontalAlignment(ALIGN_LEFT);
                cellt1.setBorder(TOP);
//                cellt1.setFixedHeight(15f);
                cellt1.setBackgroundColor(LIGHT_GRAY);
                //cellt1.setBorder(Rectangle.BOTTOM);
                cellt1.setBorderWidth(0.7f);
                table3.addCell(cellt1);

                phraset1 = new Phrase();
                phraset1.add(new Chunk("04 - Cash Advance", f4_bold));
                cellt1 = new PdfPCell(phraset1);
                cellt1.setHorizontalAlignment(ALIGN_RIGHT);
                cellt1.setBorder(TOP);
//                cellt1.setFixedHeight(15f);
                cellt1.setBackgroundColor(LIGHT_GRAY);
                //cellt1.setBorder(Rectangle.BOTTOM);
                cellt1.setBorderWidth(0.7f);
                table3.addCell(cellt1);

                phraset1 = new Phrase();
                phraset1.add(new Chunk("06 - Credit Card - CHANGE", f4_bold));
                cellt1 = new PdfPCell(phraset1);
                cellt1.setHorizontalAlignment(ALIGN_RIGHT);
                cellt1.setBorder(TOP);
//                cellt1.setFixedHeight(15f);
                cellt1.setBackgroundColor(LIGHT_GRAY);
                //cellt1.setBorder(Rectangle.BOTTOM);
                cellt1.setBorderWidth(0.7f);
                table3.addCell(cellt1);

                phraset1 = new Phrase();
                phraset1.add(new Chunk("06 - Credit Card - NO CHANGE", f4_bold));
                cellt1 = new PdfPCell(phraset1);
                cellt1.setHorizontalAlignment(ALIGN_RIGHT);
                cellt1.setBorder(TOP);
//                cellt1.setFixedHeight(15f);
                cellt1.setBackgroundColor(LIGHT_GRAY);
                //cellt1.setBorder(Rectangle.BOTTOM);
                cellt1.setBorderWidth(0.7f);
                table3.addCell(cellt1);

                phraset1 = new Phrase();
                phraset1.add(new Chunk("06 - Credit Card Total", f4_bold));
                cellt1 = new PdfPCell(phraset1);
                cellt1.setHorizontalAlignment(ALIGN_RIGHT);
                cellt1.setBorder(TOP);
//                cellt1.setFixedHeight(15f);
                cellt1.setBackgroundColor(LIGHT_GRAY);
                //cellt1.setBorder(Rectangle.BOTTOM);
                cellt1.setBorderWidth(0.7f);
                table3.addCell(cellt1);

                document.add(table3);

                //CRU ////////
////////                phraset1 = new Phrase();
////////                phraset1.add(new Chunk("07 - Bancomat", f4_bold));
////////                cellt1 = new PdfPCell(phraset1);
////////                cellt1.setHorizontalAlignment(Element.ALIGN_RIGHT);
////////                cellt1.setBorder(Rectangle.TOP);
////////                cellt1.setFixedHeight(15f);
////////                cellt1.setBackgroundColor(BaseColor.LIGHT_GRAY);
////////                //cellt1.setBorder(Rectangle.BOTTOM);
////////                cellt1.setBorderWidth(0.7f);
////////                table3.addCell(cellt1);
////////
////////                phraset1 = new Phrase();
////////                phraset1.add(new Chunk("Total", f4_bold));
////////                cellt1 = new PdfPCell(phraset1);
////////                cellt1.setHorizontalAlignment(Element.ALIGN_RIGHT);
////////                cellt1.setBorder(Rectangle.TOP);
////////                cellt1.setFixedHeight(15f);
////////                cellt1.setBackgroundColor(BaseColor.LIGHT_GRAY);
////////                //cellt1.setBorder(Rectangle.BOTTOM);
////////                cellt1.setBorderWidth(0.7f);
////////                table3.addCell(cellt1);
////////
////////                document.add(table3);
                /// CRU ///
                // 2 riga intestazione
                //2 tabella
                table3 = new PdfPTable(9);
                table3.setWidths(columnWidths3a);
                table3.setWidthPercentage(100);

                //colonne
                phraset1 = new Phrase();
                phraset1.add(new Chunk("", f3_normal));
                cellt1 = new PdfPCell(phraset1);
                cellt1.setHorizontalAlignment(ALIGN_CENTER);
                cellt1.setBorder(BOTTOM);
//                cellt1.setFixedHeight(15f);
                cellt1.setBackgroundColor(LIGHT_GRAY);
                //cellt1.setBorder(Rectangle.BOTTOM);
                cellt1.setBorderWidth(0.7f);
                table3.addCell(cellt1);

                phraset1 = new Phrase();
                phraset1.add(new Chunk("No.", f3_normal));
                cellt1 = new PdfPCell(phraset1);
                cellt1.setHorizontalAlignment(ALIGN_RIGHT);
                cellt1.setBorder(BOTTOM);
//                cellt1.setFixedHeight(15f);
                cellt1.setBackgroundColor(LIGHT_GRAY);
                //cellt1.setBorder(Rectangle.BOTTOM);
                cellt1.setBorderWidth(0.7f);
                table3.addCell(cellt1);

                phraset1 = new Phrase();
                phraset1.add(new Chunk("Amount", f3_normal));
                cellt1 = new PdfPCell(phraset1);
                cellt1.setHorizontalAlignment(ALIGN_RIGHT);
                cellt1.setBorder(BOTTOM);
//                cellt1.setFixedHeight(15f);
                cellt1.setBackgroundColor(LIGHT_GRAY);
                //cellt1.setBorder(Rectangle.BOTTOM);
                cellt1.setBorderWidth(0.7f);
                table3.addCell(cellt1);

                phraset1 = new Phrase();
                phraset1.add(new Chunk("No.", f3_normal));
                cellt1 = new PdfPCell(phraset1);
                cellt1.setHorizontalAlignment(ALIGN_RIGHT);
                cellt1.setBorder(BOTTOM);
//                cellt1.setFixedHeight(15f);
                cellt1.setBackgroundColor(LIGHT_GRAY);
                //cellt1.setBorder(Rectangle.BOTTOM);
                cellt1.setBorderWidth(0.7f);
                table3.addCell(cellt1);

                phraset1 = new Phrase();
                phraset1.add(new Chunk("Amount", f3_normal));
                cellt1 = new PdfPCell(phraset1);
                cellt1.setHorizontalAlignment(ALIGN_RIGHT);
                cellt1.setBorder(BOTTOM);
//                cellt1.setFixedHeight(15f);
                cellt1.setBackgroundColor(LIGHT_GRAY);
                //cellt1.setBorder(Rectangle.BOTTOM);
                cellt1.setBorderWidth(0.7f);
                table3.addCell(cellt1);

                phraset1 = new Phrase();
                phraset1.add(new Chunk("No.", f3_normal));
                cellt1 = new PdfPCell(phraset1);
                cellt1.setHorizontalAlignment(ALIGN_RIGHT);
                cellt1.setBorder(BOTTOM);
//                cellt1.setFixedHeight(15f);
                cellt1.setBackgroundColor(LIGHT_GRAY);
                //cellt1.setBorder(Rectangle.BOTTOM);
                cellt1.setBorderWidth(0.7f);
                table3.addCell(cellt1);

                phraset1 = new Phrase();
                phraset1.add(new Chunk("Amount", f3_normal));
                cellt1 = new PdfPCell(phraset1);
                cellt1.setHorizontalAlignment(ALIGN_RIGHT);
                cellt1.setBorder(BOTTOM);
//                cellt1.setFixedHeight(15f);
                cellt1.setBackgroundColor(LIGHT_GRAY);
                //cellt1.setBorder(Rectangle.BOTTOM);
                cellt1.setBorderWidth(0.7f);
                table3.addCell(cellt1);

                phraset1 = new Phrase();
                phraset1.add(new Chunk("No.", f3_normal));
                cellt1 = new PdfPCell(phraset1);
                cellt1.setHorizontalAlignment(ALIGN_RIGHT);
                cellt1.setBorder(BOTTOM);
//                cellt1.setFixedHeight(15f);
                cellt1.setBackgroundColor(LIGHT_GRAY);
                //cellt1.setBorder(Rectangle.BOTTOM);
                cellt1.setBorderWidth(0.7f);
                table3.addCell(cellt1);

                phraset1 = new Phrase();
                phraset1.add(new Chunk("Amount", f3_normal));
                cellt1 = new PdfPCell(phraset1);
                cellt1.setHorizontalAlignment(ALIGN_RIGHT);
                cellt1.setBorder(BOTTOM);
//                cellt1.setFixedHeight(15f);
                cellt1.setBackgroundColor(LIGHT_GRAY);
                //cellt1.setBorder(Rectangle.BOTTOM);
                cellt1.setBorderWidth(0.7f);
                table3.addCell(cellt1);

                document.add(table3);

                //CRU   /// 
////////               phraset1 = new Phrase();
////////                phraset1.add(new Chunk("N.", f3_normal));
////////                cellt1 = new PdfPCell(phraset1);
////////                cellt1.setHorizontalAlignment(Element.ALIGN_RIGHT);
////////                cellt1.setBorder(Rectangle.BOTTOM);
////////                cellt1.setFixedHeight(15f);
////////                cellt1.setBackgroundColor(BaseColor.LIGHT_GRAY);
////////                //cellt1.setBorder(Rectangle.BOTTOM);
////////                cellt1.setBorderWidth(0.7f);
////////                table3.addCell(cellt1);
////////
////////                phraset1 = new Phrase();
////////                phraset1.add(new Chunk("Amount", f3_normal));
////////                cellt1 = new PdfPCell(phraset1);
////////                cellt1.setHorizontalAlignment(Element.ALIGN_RIGHT);
////////                cellt1.setBorder(Rectangle.BOTTOM);
////////                cellt1.setFixedHeight(15f);
////////                cellt1.setBackgroundColor(BaseColor.LIGHT_GRAY);
////////                //cellt1.setBorder(Rectangle.BOTTOM);
////////                cellt1.setBorderWidth(0.7f);
////////                table3.addCell(cellt1);
////////
////////                document.add(table3);
                /// CRU ////
                //fine intestazione 2 tabella
                sep = new LineSeparator();
                sep.setOffset(-2);
                sep.setLineWidth((float) 0.5);

                table3 = new PdfPTable(9);
                table3.setWidths(columnWidths3a);
                table3.setWidthPercentage(100);

                //tot predisp.
                int totNTransCashAd = 0;
                int totNTransCC = 0;
                int totNCNTransCC = 0;
                int totNTransBank = 0;
                int totNCNTransBank = 0;
                int totncnttransbank = 0;
                int totNTransTot = 0;

                float totAmountCashAd = 0;
                double totNamountBank = 0;
                double totNCNamountBank = 0;
                double totncamountbank = 0;
                float totAmountCC = 0;
                float totNCAmountCC = 0;
                float totAmountBank = 0;
                float totNCAmountBank = 0;
                float totAmountTot = 0;

                //popolamento tabella 2
                for (int i = 0; i < siq.getDatiCOP().size(); i++) {
                    DailyCOP dc = (DailyCOP) siq.getDatiCOP().get(i);

                    phraset1 = new Phrase();
                    phraset1.add(new Chunk(dc.getPos(), f4_bold));
                    cellt1 = new PdfPCell(phraset1);
                    cellt1.setHorizontalAlignment(ALIGN_LEFT);
                    cellt1.setBorder(BOTTOM | TOP);
//                    cellt1.setFixedHeight(10f);
                    cellt1.setBackgroundColor(LIGHT_GRAY);
                    table3.addCell(cellt1);

                    phraset = new Phrase();
                    phraset.add(new Chunk(dc.getCashAdNtrans(), f3_normal));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setBorder(BOTTOM);
                    table3.addCell(cellt);

                    phraset = new Phrase();
                    phraset.add(new Chunk(formatMysqltoDisplay(dc.getCashAdAmount()), f3_normal));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setBorder(BOTTOM);
                    table3.addCell(cellt);

                    phraset = new Phrase();
                    phraset.add(new Chunk(dc.getCcNtrans(), f3_normal));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setBorder(BOTTOM);
                    table3.addCell(cellt);

                    phraset = new Phrase();
                    phraset.add(new Chunk(formatMysqltoDisplay(dc.getCcAmount()), f3_normal));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setBorder(BOTTOM);
                    table3.addCell(cellt);

                    phraset = new Phrase();
                    phraset.add(new Chunk(dc.getNC_ccNtrans(), f3_normal));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setBorder(BOTTOM);
                    table3.addCell(cellt);

                    phraset = new Phrase();
                    phraset.add(new Chunk(formatMysqltoDisplay(dc.getNC_ccAmount()), f3_normal));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setBorder(BOTTOM);
                    table3.addCell(cellt);

                    double totNtrans = fd(dc.getCcNtrans()) + fd(dc.getNC_ccNtrans());
                    double totAmount = fd(dc.getCcAmount()) + fd(dc.getNC_ccAmount());

                    phraset = new Phrase();
                    phraset.add(new Chunk(roundDoubleandFormat(totNtrans, 0) + "", f3_normal));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setBorder(BOTTOM);
                    table3.addCell(cellt);

                    phraset = new Phrase();
                    phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(totAmount, 2)) + "", f3_normal));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setBorder(BOTTOM);
                    table3.addCell(cellt);

//                    
//                    phraset = new Phrase();
//                    phraset.add(new Chunk(dc.getBankNtrans(), f3_normal));
//                    cellt = new PdfPCell(phraset);
//                    cellt.setHorizontalAlignment(Element.ALIGN_RIGHT);
//                    cellt.setBorder(Rectangle.BOTTOM);
//                    table3.addCell(cellt);
//
//                    phraset = new Phrase();
//                    phraset.add(new Chunk(Utility.formatMysqltoDisplay(dc.getBankAmount()), f3_normal));
//                    cellt = new PdfPCell(phraset);
//                    cellt.setHorizontalAlignment(Element.ALIGN_RIGHT);
//                    cellt.setBorder(Rectangle.BOTTOM);
//                    table3.addCell(cellt);
//
//                   
//                   
                    totNTransCashAd += parseIntR(dc.getCashAdNtrans());
                    totNTransCC += parseIntR(dc.getCcNtrans());
                    totNCNTransCC += parseIntR(dc.getNC_ccNtrans());

                    totNTransTot += totNtrans;

                    totAmountCashAd += fd(dc.getCashAdAmount());
                    totAmountCC += fd(dc.getCcAmount());
                    totNCAmountCC += fd(dc.getNC_ccAmount());

                    totAmountTot += totAmount;

                }

                //fine 2 riga
                //totali
                phraset1 = new Phrase();
                phraset1.add(new Chunk("Total", f4_bold));
                cellt1 = new PdfPCell(phraset1);
                cellt1.setHorizontalAlignment(ALIGN_LEFT);
                cellt1.setBorder(BOTTOM | TOP);
//                cellt1.setFixedHeight(10f);
                cellt1.setBackgroundColor(LIGHT_GRAY);
                table3.addCell(cellt1);

                phraset = new Phrase();
                phraset.add(new Chunk(((totNTransCashAd)) + "", f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table3.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(totAmountCashAd, 2)) + "", f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table3.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(((totNTransCC)) + "", f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table3.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(totAmountCC, 2)) + "", f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table3.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(totNCNTransCC + "", f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table3.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(totNCAmountCC, 2)) + "", f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table3.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(totNCNTransCC + totNTransCC + "", f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table3.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(totAmountCC + totNCAmountCC, 2)), f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table3.addCell(cellt);
                //fine tot

                document.add(table3);

                vuoto.setFont(f3_normal);
                document.add(vuoto);

                table3 = new PdfPTable(5);
                //table3.setWidths(columnWidths3b);
                table3.setWidths(columnWidths3b);
                table3.setWidthPercentage(100);

                //colonne
                phraset1 = new Phrase();
                phraset1.add(new Chunk("POS", f4_bold));
                cellt1 = new PdfPCell(phraset1);
                cellt1.setHorizontalAlignment(ALIGN_LEFT);
                cellt1.setBorder(TOP);
//                cellt1.setFixedHeight(15f);
                cellt1.setBackgroundColor(LIGHT_GRAY);
                //cellt1.setBorder(Rectangle.BOTTOM);
                cellt1.setBorderWidth(0.7f);
                table3.addCell(cellt1);

                phraset1 = new Phrase();
                phraset1.add(new Chunk("07 - Bancomat - CHANGE", f4_bold));
                cellt1 = new PdfPCell(phraset1);
                cellt1.setHorizontalAlignment(ALIGN_RIGHT);
                cellt1.setBorder(TOP);
//                cellt1.setFixedHeight(15f);
                cellt1.setBackgroundColor(LIGHT_GRAY);
                //cellt1.setBorder(Rectangle.BOTTOM);
                cellt1.setBorderWidth(0.7f);
                table3.addCell(cellt1);

                phraset1 = new Phrase();
                phraset1.add(new Chunk("07 - Bancomat - NO CHANGE", f4_bold));
                cellt1 = new PdfPCell(phraset1);
                cellt1.setHorizontalAlignment(ALIGN_RIGHT);
                cellt1.setBorder(TOP);
//                cellt1.setFixedHeight(15f);
                cellt1.setBackgroundColor(LIGHT_GRAY);
                //cellt1.setBorder(Rectangle.BOTTOM);
                cellt1.setBorderWidth(0.7f);
                table3.addCell(cellt1);

                phraset1 = new Phrase();
                phraset1.add(new Chunk("07 - Bancomat Total", f4_bold));
                cellt1 = new PdfPCell(phraset1);
                cellt1.setHorizontalAlignment(ALIGN_RIGHT);
                cellt1.setBorder(TOP);
//                cellt1.setFixedHeight(15f);
                cellt1.setBackgroundColor(LIGHT_GRAY);
                //cellt1.setBorder(Rectangle.BOTTOM);
                cellt1.setBorderWidth(0.7f);
                table3.addCell(cellt1);

                phraset1 = new Phrase();
                phraset1.add(new Chunk("POS Total", f4_bold));
                cellt1 = new PdfPCell(phraset1);
                cellt1.setHorizontalAlignment(ALIGN_RIGHT);
                cellt1.setBorder(TOP);
//                cellt1.setFixedHeight(15f);
                cellt1.setBackgroundColor(LIGHT_GRAY);
                //cellt1.setBorder(Rectangle.BOTTOM);
                cellt1.setBorderWidth(0.7f);
                table3.addCell(cellt1);

                document.add(table3);

                table3 = new PdfPTable(9);
                table3.setWidths(columnWidths3a);
                table3.setWidthPercentage(100);
                table3.setHorizontalAlignment(ALIGN_LEFT);

                //colonne              
                phraset1 = new Phrase();
                phraset1.add(new Chunk("", f3_normal));
                cellt1 = new PdfPCell(phraset1);
                cellt1.setHorizontalAlignment(ALIGN_RIGHT);
                cellt1.setBorder(BOTTOM);
//                cellt1.setFixedHeight(15f);
                cellt1.setBackgroundColor(LIGHT_GRAY);
                //cellt1.setBorder(Rectangle.BOTTOM);
                cellt1.setBorderWidth(0.7f);
                table3.addCell(cellt1);

                phraset1 = new Phrase();
                phraset1.add(new Chunk("No.", f3_normal));
                cellt1 = new PdfPCell(phraset1);
                cellt1.setHorizontalAlignment(ALIGN_RIGHT);
                cellt1.setBorder(BOTTOM);
//                cellt1.setFixedHeight(15f);
                cellt1.setBackgroundColor(LIGHT_GRAY);
                //cellt1.setBorder(Rectangle.BOTTOM);
                cellt1.setBorderWidth(0.7f);
                table3.addCell(cellt1);

                phraset1 = new Phrase();
                phraset1.add(new Chunk("Amount", f3_normal));
                cellt1 = new PdfPCell(phraset1);
                cellt1.setHorizontalAlignment(ALIGN_RIGHT);
                cellt1.setBorder(BOTTOM);
//                cellt1.setFixedHeight(15f);
                cellt1.setBackgroundColor(LIGHT_GRAY);
                //cellt1.setBorder(Rectangle.BOTTOM);
                cellt1.setBorderWidth(0.7f);
                table3.addCell(cellt1);

                phraset1 = new Phrase();
                phraset1.add(new Chunk("No.", f3_normal));
                cellt1 = new PdfPCell(phraset1);
                cellt1.setHorizontalAlignment(ALIGN_RIGHT);
                cellt1.setBorder(BOTTOM);
//                cellt1.setFixedHeight(15f);
                cellt1.setBackgroundColor(LIGHT_GRAY);
                //cellt1.setBorder(Rectangle.BOTTOM);
                cellt1.setBorderWidth(0.7f);
                table3.addCell(cellt1);

                phraset1 = new Phrase();
                phraset1.add(new Chunk("Amount", f3_normal));
                cellt1 = new PdfPCell(phraset1);
                cellt1.setHorizontalAlignment(ALIGN_RIGHT);
                cellt1.setBorder(BOTTOM);
//                cellt1.setFixedHeight(15f);
                cellt1.setBackgroundColor(LIGHT_GRAY);
                //cellt1.setBorder(Rectangle.BOTTOM);
                cellt1.setBorderWidth(0.7f);
                table3.addCell(cellt1);

                phraset1 = new Phrase();
                phraset1.add(new Chunk("No.", f3_normal));
                cellt1 = new PdfPCell(phraset1);
                cellt1.setHorizontalAlignment(ALIGN_RIGHT);
                cellt1.setBorder(BOTTOM);
//                cellt1.setFixedHeight(15f);
                cellt1.setBackgroundColor(LIGHT_GRAY);
                //cellt1.setBorder(Rectangle.BOTTOM);
                cellt1.setBorderWidth(0.7f);
                table3.addCell(cellt1);

                phraset1 = new Phrase();
                phraset1.add(new Chunk("Amount", f3_normal));
                cellt1 = new PdfPCell(phraset1);
                cellt1.setHorizontalAlignment(ALIGN_RIGHT);
                cellt1.setBorder(BOTTOM);
//                cellt1.setFixedHeight(15f);
                cellt1.setBackgroundColor(LIGHT_GRAY);
                //cellt1.setBorder(Rectangle.BOTTOM);
                cellt1.setBorderWidth(0.7f);
                table3.addCell(cellt1);

                phraset1 = new Phrase();
                phraset1.add(new Chunk("No.", f3_normal));
                cellt1 = new PdfPCell(phraset1);
                cellt1.setHorizontalAlignment(ALIGN_RIGHT);
                cellt1.setBorder(BOTTOM);
//                cellt1.setFixedHeight(15f);
                cellt1.setBackgroundColor(LIGHT_GRAY);
                //cellt1.setBorder(Rectangle.BOTTOM);
                cellt1.setBorderWidth(0.7f);
                table3.addCell(cellt1);

                phraset1 = new Phrase();
                phraset1.add(new Chunk("Amount", f3_normal));
                cellt1 = new PdfPCell(phraset1);
                cellt1.setHorizontalAlignment(ALIGN_RIGHT);
                cellt1.setBorder(BOTTOM);
//                cellt1.setFixedHeight(15f);
                cellt1.setBackgroundColor(LIGHT_GRAY);
                //cellt1.setBorder(Rectangle.BOTTOM);
                cellt1.setBorderWidth(0.7f);
                table3.addCell(cellt1);

                for (int i = 0; i < siq.getDatiCOP().size(); i++) {
                    DailyCOP dc = (DailyCOP) siq.getDatiCOP().get(i);

                    phraset1 = new Phrase();
                    phraset1.add(new Chunk(dc.getPos(), f4_bold));
                    cellt1 = new PdfPCell(phraset1);
                    cellt1.setHorizontalAlignment(ALIGN_LEFT);
                    cellt1.setBorder(BOTTOM | TOP);
//                    cellt1.setFixedHeight(10f);
                    cellt1.setBackgroundColor(LIGHT_GRAY);
                    table3.addCell(cellt1);

//                    phraset = new Phrase();                    
//                    phraset.add(new Chunk(dc.getCashAdNtrans(), f3_normal));
//                    cellt = new PdfPCell(phraset);
//                    cellt.setHorizontalAlignment(Element.ALIGN_RIGHT);
//                    cellt.setBorder(Rectangle.BOTTOM);
//                    table3.addCell(cellt);
//
//                    phraset = new Phrase();
//                    phraset.add(new Chunk(Utility.formatMysqltoDisplay(dc.getCashAdAmount()), f3_normal));
//                    cellt = new PdfPCell(phraset);
//                    cellt.setHorizontalAlignment(Element.ALIGN_RIGHT);
//                    cellt.setBorder(Rectangle.BOTTOM);
//                    table3.addCell(cellt);
//
//                    phraset = new Phrase();
//                    phraset.add(new Chunk(dc.getCcNtrans(), f3_normal));
//                    cellt = new PdfPCell(phraset);
//                    cellt.setHorizontalAlignment(Element.ALIGN_RIGHT);
//                    cellt.setBorder(Rectangle.BOTTOM);
//                    table3.addCell(cellt);
//
//                    phraset = new Phrase();
//                    phraset.add(new Chunk(Utility.formatMysqltoDisplay(dc.getCcAmount()), f3_normal));
//                    cellt = new PdfPCell(phraset);
//                    cellt.setHorizontalAlignment(Element.ALIGN_RIGHT);
//                    cellt.setBorder(Rectangle.BOTTOM);
//                    table3.addCell(cellt);
                    phraset = new Phrase();
                    phraset.add(new Chunk(dc.getBankNtrans(), f3_normal));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setBorder(BOTTOM);
                    table3.addCell(cellt);

                    phraset = new Phrase();
                    phraset.add(new Chunk(formatMysqltoDisplay(dc.getBankAmount()), f3_normal));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setBorder(BOTTOM);
                    table3.addCell(cellt);

                    phraset = new Phrase();
                    phraset.add(new Chunk(dc.getNC_bankNtrans(), f3_normal));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setBorder(BOTTOM);
                    table3.addCell(cellt);

                    phraset = new Phrase();
                    phraset.add(new Chunk(formatMysqltoDisplay(dc.getNC_bankAmount()), f3_normal));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setBorder(BOTTOM);
                    table3.addCell(cellt);

                    double totNtrans = fd(dc.getBankNtrans()) + fd(dc.getNC_bankNtrans());
                    double totAmount = fd(dc.getBankAmount()) + fd(dc.getNC_bankAmount());

                    phraset = new Phrase();
                    phraset.add(new Chunk(roundDoubleandFormat(totNtrans, 0) + "", f3_normal));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setBorder(BOTTOM);
                    table3.addCell(cellt);

                    phraset = new Phrase();
                    phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(totAmount, 2)) + "", f3_normal));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setBorder(BOTTOM);
                    table3.addCell(cellt);

                    totNTransBank += parseIntR(dc.getBankNtrans());
//                     totNTransBank += parseIntR(dc.getBankNtrans()) + parseIntR(dc.getNC_bankNtrans());

                    //  totNTransTot += totNtrans;
//                    totAmountCashAd += ff(dc.getCashAdAmount());
//                    totAmountCC += ff(dc.getCcAmount());
                    totAmountBank += fd(dc.getBankAmount());
                    totAmountTot += totAmount;

                    int totNTransTotFinal = parseIntR(dc.getCcNtrans()) + parseIntR(dc.getCashAdNtrans())
                            + parseIntR(dc.getNC_ccNtrans()) + parseIntR(dc.getBankNtrans())
                            + parseIntR(dc.getNC_bankNtrans());

                    double totAmountTotfinal = fd(dc.getCcAmount()) + fd(dc.getCashAdAmount())
                            + fd(dc.getNC_ccAmount()) + fd(dc.getBankAmount()) + fd(dc.getNC_bankAmount());

                    phraset = new Phrase();
                    phraset.add(new Chunk(roundDoubleandFormat(totNTransTotFinal, 0) + "", f3_normal));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setBorder(BOTTOM);
                    table3.addCell(cellt);

                    phraset = new Phrase();
                    phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(totAmountTotfinal, 2)), f3_normal));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setBorder(BOTTOM);
                    table3.addCell(cellt);

                    //totNTransCashAd += Integer.parseInt(dc.getCashAdNtrans());
                    //totNTransBank += parseIntR(dc.getBankNtrans());
                    totNCNTransBank += parseIntR(dc.getNC_bankNtrans());
                    totncnttransbank = totncnttransbank + parseIntR(dc.getBankNtrans()) + parseIntR(dc.getNC_bankNtrans());

                    totNamountBank += fd(dc.getBankAmount());
                    totNCNamountBank += fd(dc.getNC_bankAmount());
                    totncamountbank = totncamountbank + fd(dc.getBankAmount()) + fd(dc.getNC_bankAmount());

                }

//                 document.add(table3);
                phraset1 = new Phrase();
                phraset1.add(new Chunk("Total", f4_bold));
                cellt1 = new PdfPCell(phraset1);
                cellt1.setHorizontalAlignment(ALIGN_LEFT);
                cellt1.setBorder(BOTTOM | TOP);
//                cellt1.setFixedHeight(10f);
                cellt1.setBackgroundColor(LIGHT_GRAY);
                table3.addCell(cellt1);

                phraset = new Phrase();
                phraset.add(new Chunk(totNTransBank + "", f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table3.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(totNamountBank, 2)) + "", f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table3.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(totNCNTransBank + "", f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table3.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(totNCNamountBank, 2)) + "", f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table3.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(totncnttransbank + "", f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table3.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(totncamountbank, 2)) + "", f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table3.addCell(cellt);

                phraset = new Phrase();

                phraset.add(new Chunk(valueOf(totncnttransbank + totNCNTransCC + totNTransCC + totNTransCashAd), f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table3.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(totncamountbank + totAmountCC + totNCAmountCC + totAmountCashAd, 2)) + "", f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table3.addCell(cellt);

                //fine tot
                document.add(table3);

                vuoto.setFont(f3_normal);
                document.add(vuoto);

                //3 tabella
                //tabella bankaccount
                table3 = new PdfPTable(6);
                table3.setWidths(columnWidthsBank);
                table3.setWidthPercentage(100);

                //colonne
                phraset1 = new Phrase();
                phraset1.add(new Chunk("08 - Bank Account", f4_bold));
                cellt1 = new PdfPCell(phraset1);
                cellt1.setHorizontalAlignment(ALIGN_RIGHT);
                cellt1.setBorder(TOP);
//                cellt1.setFixedHeight(15f);
                cellt1.setBackgroundColor(LIGHT_GRAY);
                //cellt1.setBorder(Rectangle.BOTTOM);
                cellt1.setBorderWidth(0.7f);
                table3.addCell(cellt1);

                phraset1 = new Phrase();
                phraset1.add(new Chunk("Total", f4_bold));
                cellt1 = new PdfPCell(phraset1);
                cellt1.setHorizontalAlignment(ALIGN_RIGHT);
                cellt1.setBorder(TOP);
//                cellt1.setFixedHeight(15f);
                cellt1.setBackgroundColor(LIGHT_GRAY);
                //cellt1.setBorder(Rectangle.BOTTOM);
                cellt1.setBorderWidth(0.7f);
                table3.addCell(cellt1);

                phraset1 = new Phrase();
                phraset1.add(new Chunk("", f4_bold));
                cellt1 = new PdfPCell(phraset1);
                cellt1.setHorizontalAlignment(ALIGN_CENTER);
                cellt1.setBorder(NO_BORDER);
//                cellt1.setFixedHeight(15f);
                table3.addCell(cellt1);

                phraset1 = new Phrase();
                phraset1.add(new Chunk("", f4_bold));
                cellt1 = new PdfPCell(phraset1);
                cellt1.setHorizontalAlignment(ALIGN_CENTER);
                cellt1.setBorder(NO_BORDER);
//                cellt1.setFixedHeight(15f);
                table3.addCell(cellt1);

                phraset1 = new Phrase();
                phraset1.add(new Chunk("", f4_bold));
                cellt1 = new PdfPCell(phraset1);
                cellt1.setHorizontalAlignment(ALIGN_CENTER);
                cellt1.setBorder(NO_BORDER);
//                cellt1.setFixedHeight(15f);
                table3.addCell(cellt1);

                phraset1 = new Phrase();
                phraset1.add(new Chunk("", f4_bold));
                cellt1 = new PdfPCell(phraset1);
                cellt1.setHorizontalAlignment(ALIGN_CENTER);
                cellt1.setBorder(NO_BORDER);
//                cellt1.setFixedHeight(15f);
                table3.addCell(cellt1);

                document.add(table3);

                // 2 riga intestazione
                //2 tabella
                table3 = new PdfPTable(7);
                table3.setWidths(columnWidthsBank1);
                table3.setWidthPercentage(100);

                //colonne
                phraset1 = new Phrase();
                phraset1.add(new Chunk("", f3_normal));
                cellt1 = new PdfPCell(phraset1);
                cellt1.setHorizontalAlignment(ALIGN_CENTER);
                cellt1.setBorder(BOTTOM);
//                cellt1.setFixedHeight(15f);
                cellt1.setBackgroundColor(LIGHT_GRAY);
                //cellt1.setBorder(Rectangle.BOTTOM);
                cellt1.setBorderWidth(0.7f);
                table3.addCell(cellt1);

                phraset1 = new Phrase();
                phraset1.add(new Chunk("No.", f3_normal));
                cellt1 = new PdfPCell(phraset1);
                cellt1.setHorizontalAlignment(ALIGN_RIGHT);
                cellt1.setBorder(BOTTOM);
//                cellt1.setFixedHeight(15f);
                cellt1.setBackgroundColor(LIGHT_GRAY);
                //cellt1.setBorder(Rectangle.BOTTOM);
                cellt1.setBorderWidth(0.7f);
                table3.addCell(cellt1);

                phraset1 = new Phrase();
                phraset1.add(new Chunk("Amount", f3_normal));
                cellt1 = new PdfPCell(phraset1);
                cellt1.setHorizontalAlignment(ALIGN_RIGHT);
                cellt1.setBorder(BOTTOM);
//                cellt1.setFixedHeight(15f);
                cellt1.setBackgroundColor(LIGHT_GRAY);
                //cellt1.setBorder(Rectangle.BOTTOM);
                cellt1.setBorderWidth(0.7f);
                table3.addCell(cellt1);

                phraset1 = new Phrase();
                phraset1.add(new Chunk("", f4_bold));
                cellt1 = new PdfPCell(phraset1);
                cellt1.setHorizontalAlignment(ALIGN_CENTER);
                cellt1.setBorder(NO_BORDER);
//                cellt1.setFixedHeight(15f);
                table3.addCell(cellt1);

                phraset1 = new Phrase();
                phraset1.add(new Chunk("", f4_bold));
                cellt1 = new PdfPCell(phraset1);
                cellt1.setHorizontalAlignment(ALIGN_CENTER);
                cellt1.setBorder(NO_BORDER);
//                cellt1.setFixedHeight(15f);
                table3.addCell(cellt1);

                phraset1 = new Phrase();
                phraset1.add(new Chunk("", f4_bold));
                cellt1 = new PdfPCell(phraset1);
                cellt1.setHorizontalAlignment(ALIGN_CENTER);
                cellt1.setBorder(NO_BORDER);
//                cellt1.setFixedHeight(15f);
                table3.addCell(cellt1);

                phraset1 = new Phrase();
                phraset1.add(new Chunk("", f4_bold));
                cellt1 = new PdfPCell(phraset1);
                cellt1.setHorizontalAlignment(ALIGN_CENTER);
                cellt1.setBorder(NO_BORDER);
//                cellt1.setFixedHeight(15f);
                table3.addCell(cellt1);

                document.add(table3);

                //fine intestazione tabella bank account
                sep = new LineSeparator();
                sep.setOffset(-2);
                sep.setLineWidth((float) 0.5);

                table3 = new PdfPTable(7);
                table3.setWidths(columnWidthsBank1);
                table3.setWidthPercentage(100);

                //tot predisp.
                int bankTotNTransTot = 0;
                float bankTotAmountTot = 0;

                //popolamento tabella 2
                for (int i = 0; i < siq.getDatiBank().size(); i++) {
                    DailyBank db = (DailyBank) siq.getDatiBank().get(i);

                    phraset1 = new Phrase();
                    phraset1.add(new Chunk(db.getBank(), f4_bold));
                    cellt1 = new PdfPCell(phraset1);
                    cellt1.setHorizontalAlignment(ALIGN_LEFT);
                    cellt1.setBorder(BOTTOM | TOP);
//                    cellt1.setFixedHeight(10f);
                    cellt1.setBackgroundColor(LIGHT_GRAY);
                    table3.addCell(cellt1);

                    phraset = new Phrase();
                    phraset.add(new Chunk(db.getNtrans(), f3_normal));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setBorder(BOTTOM);
                    table3.addCell(cellt);

                    phraset = new Phrase();
                    phraset.add(new Chunk(formatMysqltoDisplay(db.getAmount()), f3_normal));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setBorder(BOTTOM);
                    table3.addCell(cellt);

                    phraset1 = new Phrase();
                    phraset1.add(new Chunk("", f4_bold));
                    cellt1 = new PdfPCell(phraset1);
                    cellt1.setHorizontalAlignment(ALIGN_CENTER);
                    cellt1.setBorder(NO_BORDER);
//                    cellt1.setFixedHeight(15f);
                    table3.addCell(cellt1);
                    phraset1 = new Phrase();
                    phraset1.add(new Chunk("", f4_bold));
                    cellt1 = new PdfPCell(phraset1);
                    cellt1.setHorizontalAlignment(ALIGN_CENTER);
                    cellt1.setBorder(NO_BORDER);
//                    cellt1.setFixedHeight(15f);
                    table3.addCell(cellt1);

                    phraset1 = new Phrase();
                    phraset1.add(new Chunk("", f4_bold));
                    cellt1 = new PdfPCell(phraset1);
                    cellt1.setHorizontalAlignment(ALIGN_CENTER);
                    cellt1.setBorder(NO_BORDER);
//                    cellt1.setFixedHeight(15f);
                    table3.addCell(cellt1);

                    phraset1 = new Phrase();
                    phraset1.add(new Chunk("", f4_bold));
                    cellt1 = new PdfPCell(phraset1);
                    cellt1.setHorizontalAlignment(ALIGN_CENTER);
                    cellt1.setBorder(NO_BORDER);
//                    cellt1.setFixedHeight(15f);
                    table3.addCell(cellt1);

                    bankTotNTransTot += parseInt(db.getNtrans());
                    bankTotAmountTot += ff(db.getAmount());

                }

                //fine 2 riga
                //totali
                phraset1 = new Phrase();
                phraset1.add(new Chunk("Total", f4_bold));
                cellt1 = new PdfPCell(phraset1);
                cellt1.setHorizontalAlignment(ALIGN_LEFT);
                cellt1.setBorder(BOTTOM | TOP);
//                cellt1.setFixedHeight(10f);
                cellt1.setBackgroundColor(LIGHT_GRAY);
                table3.addCell(cellt1);

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(bankTotNTransTot, 0)) + "", f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table3.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(bankTotAmountTot, 2)) + "", f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table3.addCell(cellt);

                phraset1 = new Phrase();
                phraset1.add(new Chunk("", f4_bold));
                cellt1 = new PdfPCell(phraset1);
                cellt1.setHorizontalAlignment(ALIGN_CENTER);
                cellt1.setBorder(NO_BORDER);
//                cellt1.setFixedHeight(15f);
                table3.addCell(cellt1);

                phraset1 = new Phrase();
                phraset1.add(new Chunk("", f4_bold));
                cellt1 = new PdfPCell(phraset1);
                cellt1.setHorizontalAlignment(ALIGN_CENTER);
                cellt1.setBorder(NO_BORDER);
//                cellt1.setFixedHeight(15f);
                table3.addCell(cellt1);

                phraset1 = new Phrase();
                phraset1.add(new Chunk("", f4_bold));
                cellt1 = new PdfPCell(phraset1);
                cellt1.setHorizontalAlignment(ALIGN_CENTER);
                cellt1.setBorder(NO_BORDER);
//                cellt1.setFixedHeight(15f);
                table3.addCell(cellt1);

                phraset1 = new Phrase();
                phraset1.add(new Chunk("", f4_bold));
                cellt1 = new PdfPCell(phraset1);
                cellt1.setHorizontalAlignment(ALIGN_CENTER);
                cellt1.setBorder(NO_BORDER);
//                cellt1.setFixedHeight(15f);
                table3.addCell(cellt1);

                //fine tot
                document.add(table3);

                //fine tabella bankaccount
                vuoto.setFont(f3_normal);
                document.add(vuoto);

                table3 = new PdfPTable(6);
                table3.setWidths(columnWidths4);
                table3.setWidthPercentage(100);

                //colonne
                phraset1 = new Phrase();
                phraset1.add(new Chunk("Bank", f4_bold));
                cellt1 = new PdfPCell(phraset1);
                cellt1.setHorizontalAlignment(ALIGN_LEFT);
                cellt1.setBorder(BOTTOM | TOP);
                cellt1.setFixedHeight(15f);
                cellt1.setBackgroundColor(LIGHT_GRAY);
                cellt1.setBorderWidth(0.7f);
                table3.addCell(cellt1);

                phraset1 = new Phrase();
                phraset1.add(new Chunk("Other Currencies", f4_bold));
                cellt1 = new PdfPCell(phraset1);
                cellt1.setHorizontalAlignment(ALIGN_RIGHT);
                cellt1.setBorder(BOTTOM | TOP);
                cellt1.setFixedHeight(15f);
                cellt1.setBackgroundColor(LIGHT_GRAY);
                //cellt1.setBorder(Rectangle.BOTTOM);
                cellt1.setBorderWidth(0.7f);
                table3.addCell(cellt1);

                phraset1 = new Phrase();
                phraset1.add(new Chunk("Spread", f4_bold));
                cellt1 = new PdfPCell(phraset1);
                cellt1.setHorizontalAlignment(ALIGN_RIGHT);
                cellt1.setBorder(BOTTOM | TOP);
                cellt1.setFixedHeight(15f);
                cellt1.setBackgroundColor(LIGHT_GRAY);
                //cellt1.setBorder(Rectangle.BOTTOM);
                cellt1.setBorderWidth(0.7f);
                table3.addCell(cellt1);

                phraset1 = new Phrase();
                phraset1.add(new Chunk("Cash Advance", f4_bold));
                cellt1 = new PdfPCell(phraset1);
                cellt1.setHorizontalAlignment(ALIGN_RIGHT);
                cellt1.setBorder(BOTTOM | TOP);
                cellt1.setFixedHeight(15f);
                cellt1.setBackgroundColor(LIGHT_GRAY);
                //cellt1.setBorder(Rectangle.BOTTOM);
                cellt1.setBorderWidth(0.7f);
                table3.addCell(cellt1);

                phraset1 = new Phrase();
                phraset1.add(new Chunk(localcurrency, f4_bold)); //valuta corr
                cellt1 = new PdfPCell(phraset1);
                cellt1.setHorizontalAlignment(ALIGN_RIGHT);
                cellt1.setBorder(BOTTOM | TOP);
                cellt1.setFixedHeight(15f);
                cellt1.setBackgroundColor(LIGHT_GRAY);
                cellt1.setBorderWidth(0.7f);
                table3.addCell(cellt1);

                phraset1 = new Phrase();
                phraset1.add(new Chunk("Other Figures", f4_bold));
                cellt1 = new PdfPCell(phraset1);
                cellt1.setHorizontalAlignment(ALIGN_RIGHT);
                cellt1.setBorder(BOTTOM | TOP);
                cellt1.setFixedHeight(15f);
                cellt1.setBackgroundColor(LIGHT_GRAY);
                cellt1.setBorderWidth(0.7f);
                table3.addCell(cellt1);

                document.add(table3);

                sep = new LineSeparator();
                sep.setOffset(-2);
                sep.setLineWidth((float) 0.5);

                table3 = new PdfPTable(6);
                table3.setWidths(columnWidths4);
                table3.setWidthPercentage(100);

                //popolamento tabella 3
                //1 riga
                phraset1 = new Phrase();
                phraset1.add(new Chunk("Purchases", f4_bold));
                cellt1 = new PdfPCell(phraset1);
                cellt1.setHorizontalAlignment(ALIGN_LEFT);
                cellt1.setBorder(BOTTOM | TOP);
                cellt1.setFixedHeight(10f);
                cellt1.setBackgroundColor(LIGHT_GRAY);
                table3.addCell(cellt1);

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(siq.getBaPurchTotal()), f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table3.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(siq.getBaPurchSpread()), f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table3.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(siq.getBaPurchCreditCard()), f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table3.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(siq.getBaPurchTransfNotes()), f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table3.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(siq.getBaPurchTransfOther()), f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table3.addCell(cellt);

                //fine 1 riga
                //2 riga
                phraset1 = new Phrase();
                phraset1.add(new Chunk("Sales", f4_bold));
                cellt1 = new PdfPCell(phraset1);
                cellt1.setHorizontalAlignment(ALIGN_LEFT);
                cellt1.setBorder(BOTTOM | TOP);
                cellt1.setFixedHeight(10f);
                cellt1.setBackgroundColor(LIGHT_GRAY);
                table3.addCell(cellt1);

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(siq.getBaSalesTotal()), f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table3.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(siq.getBaSalesSpread()), f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table3.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(siq.getBaSalesCreditCard()), f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table3.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(siq.getBaSalesTransfNotes()), f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table3.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(siq.getBaSalesTransfOther()), f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table3.addCell(cellt);

                document.add(table3);

                //fine 2 riga
                //fine tabella 3
                //4 tabella
                vuoto.setFont(f3_normal);
                document.add(vuoto);

                table3 = new PdfPTable(5);
                table3.setWidths(columnWidths5);
                table3.setWidthPercentage(100);

                //colonne
                phraset1 = new Phrase();
                phraset1.add(new Chunk("Branch", f4_bold));
                cellt1 = new PdfPCell(phraset1);
                cellt1.setHorizontalAlignment(ALIGN_LEFT);
                cellt1.setBorder(BOTTOM | TOP);
                cellt1.setFixedHeight(15f);
                cellt1.setBackgroundColor(LIGHT_GRAY);
                cellt1.setBorderWidth(0.7f);
                table3.addCell(cellt1);

                phraset1 = new Phrase();
                phraset1.add(new Chunk("Other Currencies", f4_bold));
                cellt1 = new PdfPCell(phraset1);
                cellt1.setHorizontalAlignment(ALIGN_RIGHT);
                cellt1.setBorder(BOTTOM | TOP);
                cellt1.setFixedHeight(15f);
                cellt1.setBackgroundColor(LIGHT_GRAY);
                //cellt1.setBorder(Rectangle.BOTTOM);
                cellt1.setBorderWidth(0.7f);
                table3.addCell(cellt1);

                phraset1 = new Phrase();
                phraset1.add(new Chunk("Spread", f4_bold));
                cellt1 = new PdfPCell(phraset1);
                cellt1.setHorizontalAlignment(ALIGN_RIGHT);
                cellt1.setBorder(BOTTOM | TOP);
                cellt1.setFixedHeight(15f);
                cellt1.setBackgroundColor(LIGHT_GRAY);
                //cellt1.setBorder(Rectangle.BOTTOM);
                cellt1.setBorderWidth(0.7f);
                table3.addCell(cellt1);

                phraset1 = new Phrase();
                phraset1.add(new Chunk(localcurrency, f4_bold)); //sost con valuta locale
                cellt1 = new PdfPCell(phraset1);
                cellt1.setHorizontalAlignment(ALIGN_RIGHT);
                cellt1.setBorder(BOTTOM | TOP);
                cellt1.setFixedHeight(15f);
                cellt1.setBackgroundColor(LIGHT_GRAY);
                //cellt1.setBorder(Rectangle.BOTTOM);
                cellt1.setBorderWidth(0.7f);
                table3.addCell(cellt1);

                phraset1 = new Phrase();
                phraset1.add(new Chunk("", f4_bold));
                cellt1 = new PdfPCell(phraset1);
                cellt1.setHorizontalAlignment(ALIGN_CENTER);
                cellt1.setBorder(NO_BORDER);
                cellt1.setFixedHeight(15f);
                table3.addCell(cellt1);

                document.add(table3);

                sep = new LineSeparator();
                sep.setOffset(-2);
                sep.setLineWidth((float) 0.5);

                table3 = new PdfPTable(5);
                table3.setWidths(columnWidths5);
                table3.setWidthPercentage(100);

                //popolamento tabella 4
                //1 riga
                phraset1 = new Phrase();
                phraset1.add(new Chunk("Purchases", f4_bold));
                cellt1 = new PdfPCell(phraset1);
                cellt1.setHorizontalAlignment(ALIGN_LEFT);
                cellt1.setBorder(BOTTOM | TOP);
                cellt1.setFixedHeight(10f);
                cellt1.setBackgroundColor(LIGHT_GRAY);
                table3.addCell(cellt1);

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(siq.getBraPurchTotal()), f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table3.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(siq.getBraPurchSpread()), f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table3.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(siq.getBraPurchLocalCurr()), f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table3.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk("", f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(NO_BORDER);
                table3.addCell(cellt);

                //fine 1 riga
                //2 riga
                phraset1 = new Phrase();
                phraset1.add(new Chunk("Sales", f4_bold));
                cellt1 = new PdfPCell(phraset1);
                cellt1.setHorizontalAlignment(ALIGN_LEFT);
                cellt1.setBorder(BOTTOM | TOP);
                cellt1.setFixedHeight(10f);
                cellt1.setBackgroundColor(LIGHT_GRAY);
                table3.addCell(cellt1);

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(siq.getBraSalesTotal()), f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table3.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(siq.getBraSalesSpread()), f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table3.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(siq.getBraSalesLocalCurr()), f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table3.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk("", f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(NO_BORDER);
                table3.addCell(cellt);

                document.add(table3);

                //fine 2 riga
                //fine tabella 4
                //5 tabella
                vuoto.setFont(f3_normal);
                document.add(vuoto);

//                vuoto.setFont(f3_normal);
//                document.add(vuoto);
                table3 = new PdfPTable(7);
                table3.setWidths(columnWidths8);
                table3.setWidthPercentage(100);

                //colonne
                phraset1 = new Phrase();
                phraset1.add(new Chunk("", f4_bold));
                cellt1 = new PdfPCell(phraset1);
                cellt1.setHorizontalAlignment(ALIGN_CENTER);
                cellt1.setBorder(BOTTOM);
                cellt1.setFixedHeight(15f);
                cellt1.setBorderWidth(0.7f);
                table3.addCell(cellt1);

                phraset1 = new Phrase();
                phraset1.add(new Chunk("", f4_bold));
                cellt1 = new PdfPCell(phraset1);
                cellt1.setHorizontalAlignment(ALIGN_CENTER);
                cellt1.setBorder(BOTTOM);
                cellt1.setFixedHeight(15f);
                cellt1.setBorderWidth(0.7f);
                table3.addCell(cellt1);

                phraset1 = new Phrase();
                phraset1.add(new Chunk("No. Tran.", f4_bold));
                cellt1 = new PdfPCell(phraset1);
                cellt1.setHorizontalAlignment(ALIGN_RIGHT);
                cellt1.setBorder(BOTTOM | TOP);
                cellt1.setFixedHeight(15f);
                cellt1.setBackgroundColor(LIGHT_GRAY);
                //cellt1.setBorder(Rectangle.BOTTOM);
                cellt1.setBorderWidth(0.7f);
                table3.addCell(cellt1);

                phraset1 = new Phrase();
                phraset1.add(new Chunk("Total", f4_bold));
                cellt1 = new PdfPCell(phraset1);
                cellt1.setHorizontalAlignment(ALIGN_RIGHT);
                cellt1.setBorder(BOTTOM | TOP);
                cellt1.setFixedHeight(15f);
                cellt1.setBackgroundColor(LIGHT_GRAY);
                //cellt1.setBorder(Rectangle.BOTTOM);
                cellt1.setBorderWidth(0.7f);
                table3.addCell(cellt1);

                phraset1 = new Phrase();
                phraset1.add(new Chunk("Notes", f4_bold));
                cellt1 = new PdfPCell(phraset1);
                cellt1.setHorizontalAlignment(ALIGN_RIGHT);
                cellt1.setBorder(BOTTOM | TOP);
                cellt1.setFixedHeight(15f);
                cellt1.setBackgroundColor(LIGHT_GRAY);
                cellt1.setBorderWidth(0.7f);
                table3.addCell(cellt1);

                phraset1 = new Phrase();
                phraset1.add(new Chunk("Credit Card", f4_bold));
                cellt1 = new PdfPCell(phraset1);
                cellt1.setHorizontalAlignment(ALIGN_RIGHT);
                cellt1.setBorder(BOTTOM | TOP);
                cellt1.setFixedHeight(15f);
                cellt1.setBackgroundColor(LIGHT_GRAY);
                cellt1.setBorderWidth(0.7f);
                table3.addCell(cellt1);

                phraset1 = new Phrase();
                phraset1.add(new Chunk("Bancomat", f4_bold));
                cellt1 = new PdfPCell(phraset1);
                cellt1.setHorizontalAlignment(ALIGN_RIGHT);
                cellt1.setBorder(BOTTOM | TOP);
                cellt1.setFixedHeight(15f);
                cellt1.setBackgroundColor(LIGHT_GRAY);
                cellt1.setBorderWidth(0.7f);
                table3.addCell(cellt1);

                document.add(table3);

                sep = new LineSeparator();
                sep.setOffset(-2);
                sep.setLineWidth((float) 0.5);

                table3 = new PdfPTable(7);
                table3.setWidths(columnWidths8);
                table3.setWidthPercentage(100);

                //popolamento tabella 7
                ArrayList<DailyKind> array = siq.getDati();

                int ntr = 0;
                float totKind = 0;
                float localCurr = 0;
                float cc = 0;
                float b = 0;

                for (int i = 0; i < array.size(); i++) {

                    DailyKind d = array.get(i);

                    if (d.isTo()) {
                        phraset1 = new Phrase();
                        phraset1.add(new Chunk(d.getKind(), f4_bold));
                        cellt1 = new PdfPCell(phraset1);
                        cellt1.setHorizontalAlignment(ALIGN_LEFT);
                        cellt1.setBorder(BOTTOM | TOP);
                        cellt1.setFixedHeight(10f);
                        cellt1.setBackgroundColor(LIGHT_GRAY);
                        table3.addCell(cellt1);

                        phraset1 = new Phrase();
                        phraset1.add(new Chunk(d.getEtichetta1(), f4_bold));
                        cellt1 = new PdfPCell(phraset1);
                        cellt1.setHorizontalAlignment(ALIGN_LEFT);
                        cellt1.setBorder(BOTTOM | TOP);
                        cellt1.setFixedHeight(10f);
                        cellt1.setBackgroundColor(LIGHT_GRAY);
                        table3.addCell(cellt1);

                        phraset = new Phrase();
                        phraset.add(new Chunk(d.getToNrTran(), f3_normal));
                        cellt = new PdfPCell(phraset);
                        cellt.setHorizontalAlignment(ALIGN_RIGHT);
                        cellt.setBorder(BOTTOM);
                        table3.addCell(cellt);

                        ntr += parseInt(d.getToNrTran());

                        phraset = new Phrase();
                        phraset.add(new Chunk(formatMysqltoDisplay(d.getToTotal()), f3_normal));
                        cellt = new PdfPCell(phraset);
                        cellt.setHorizontalAlignment(ALIGN_RIGHT);
                        cellt.setBorder(BOTTOM);
                        table3.addCell(cellt);

                        totKind += ff(d.getToTotal());

                        phraset = new Phrase();
                        phraset.add(new Chunk(formatMysqltoDisplay(d.getToLocalCurr()), f3_normal));
                        cellt = new PdfPCell(phraset);
                        cellt.setHorizontalAlignment(ALIGN_RIGHT);
                        cellt.setBorder(BOTTOM);
                        table3.addCell(cellt);

                        localCurr += ff(d.getToLocalCurr());

                        phraset = new Phrase();
                        phraset.add(new Chunk(formatMysqltoDisplay(d.getToCC()), f3_normal));
                        cellt = new PdfPCell(phraset);
                        cellt.setHorizontalAlignment(ALIGN_RIGHT);
                        cellt.setBorder(BOTTOM);
                        table3.addCell(cellt);

                        cc += ff(d.getToCC());

                        phraset = new Phrase();
                        phraset.add(new Chunk(formatMysqltoDisplay(d.getToB()), f3_normal));
                        cellt = new PdfPCell(phraset);
                        cellt.setHorizontalAlignment(ALIGN_RIGHT);
                        cellt.setBorder(BOTTOM);
                        table3.addCell(cellt);

                        b += ff(d.getToB());

                    }

                    if (d.isFrom()) {

                        if (d.isTo()) {
                            phraset1 = new Phrase();
                            phraset1.add(new Chunk("", f4_bold));
                            cellt1 = new PdfPCell(phraset1);
                            cellt1.setHorizontalAlignment(ALIGN_CENTER);
                            cellt1.setBorder(BOTTOM | TOP);
                            cellt1.setFixedHeight(10f);
                            table3.addCell(cellt1);
                        } else {
                            phraset1 = new Phrase();
                            phraset1.add(new Chunk(d.getKind(), f4_bold));
                            cellt1 = new PdfPCell(phraset1);
                            cellt1.setHorizontalAlignment(ALIGN_CENTER);
                            cellt1.setBorder(BOTTOM | TOP);
                            cellt1.setFixedHeight(10f);
                            cellt1.setBackgroundColor(LIGHT_GRAY);
                            table3.addCell(cellt1);

                        }

                        //
                        phraset1 = new Phrase();
                        phraset1.add(new Chunk(d.getEtichetta2(), f4_bold));
                        cellt1 = new PdfPCell(phraset1);
                        cellt1.setHorizontalAlignment(ALIGN_LEFT);
                        cellt1.setBorder(BOTTOM | TOP);
                        cellt1.setFixedHeight(10f);
                        cellt1.setBackgroundColor(LIGHT_GRAY);
                        table3.addCell(cellt1);

                        phraset = new Phrase();
                        phraset.add(new Chunk(d.getFromNrTran(), f3_normal));
                        cellt = new PdfPCell(phraset);
                        cellt.setHorizontalAlignment(ALIGN_RIGHT);
                        cellt.setBorder(BOTTOM);
                        table3.addCell(cellt);

                        ntr += parseInt(d.getFromNrTran());

                        phraset = new Phrase();
                        phraset.add(new Chunk(formatMysqltoDisplay(d.getFromTotal()), f3_normal));
                        cellt = new PdfPCell(phraset);
                        cellt.setHorizontalAlignment(ALIGN_RIGHT);
                        cellt.setBorder(BOTTOM);
                        table3.addCell(cellt);

                        totKind += ff(d.getFromTotal());

                        phraset = new Phrase();
                        phraset.add(new Chunk(formatMysqltoDisplay(d.getFromLocalCurr()), f3_normal));
                        cellt = new PdfPCell(phraset);
                        cellt.setHorizontalAlignment(ALIGN_RIGHT);
                        cellt.setBorder(BOTTOM);
                        table3.addCell(cellt);

                        localCurr += ff(d.getFromLocalCurr());

                        phraset = new Phrase();
                        phraset.add(new Chunk(formatMysqltoDisplay(d.getFromCC()), f3_normal));
                        cellt = new PdfPCell(phraset);
                        cellt.setHorizontalAlignment(ALIGN_RIGHT);
                        cellt.setBorder(BOTTOM);
                        table3.addCell(cellt);

                        cc += ff(d.getFromCC());

                        phraset = new Phrase();
                        phraset.add(new Chunk(formatMysqltoDisplay(d.getFromB()), f3_normal));
                        cellt = new PdfPCell(phraset);
                        cellt.setHorizontalAlignment(ALIGN_RIGHT);
                        cellt.setBorder(BOTTOM);
                        table3.addCell(cellt);

                        b += ff(d.getFromB());

                    }

                }

                //totali
                phraset1 = new Phrase();
                phraset1.add(new Chunk("", f4_bold));
                cellt1 = new PdfPCell(phraset1);
                cellt1.setHorizontalAlignment(ALIGN_CENTER);
                cellt1.setBorder(BOTTOM);
                cellt1.setFixedHeight(10f);
                table3.addCell(cellt1);

                phraset1 = new Phrase();
                phraset1.add(new Chunk("Total", f4_bold));
                cellt1 = new PdfPCell(phraset1);
                cellt1.setHorizontalAlignment(ALIGN_LEFT);
                cellt1.setBorder(BOTTOM | TOP);
                cellt1.setFixedHeight(15f);
                cellt1.setBackgroundColor(LIGHT_GRAY);
                table3.addCell(cellt1);

                phraset = new Phrase();
                phraset.add(new Chunk(((ntr) + ""), f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table3.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(totKind, 2) + ""), f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table3.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(localCurr, 2) + ""), f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table3.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(cc, 2) + ""), f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table3.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(b, 2) + ""), f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table3.addCell(cellt);
                //fine totali

                document.add(table3);
                //fine tabella 5

                //6 tabella
                vuoto.setFont(f3_normal);
                document.add(vuoto);

                table3 = new PdfPTable(6);
                table3.setWidths(columnWidths7);
                table3.setWidthPercentage(100);

                //popolamento tabella 6
                //1 riga
                phraset1 = new Phrase();
                phraset1.add(new Chunk("Gross Turnover", f4_bold));
                cellt1 = new PdfPCell(phraset1);
                cellt1.setHorizontalAlignment(ALIGN_LEFT);
                cellt1.setBorder(BOTTOM | TOP);
                cellt1.setFixedHeight(10f);
                cellt1.setBackgroundColor(LIGHT_GRAY);
                table3.addCell(cellt1);

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(siq.getGroffTurnover()), f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM | TOP);
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

                //fine 1 riga
                //2 riga
                phraset1 = new Phrase();
                phraset1.add(new Chunk("Gross Profit", f4_bold));
                cellt1 = new PdfPCell(phraset1);
                cellt1.setHorizontalAlignment(ALIGN_LEFT);
                cellt1.setBorder(BOTTOM | TOP);
                cellt1.setFixedHeight(10f);
                cellt1.setBackgroundColor(LIGHT_GRAY);
                table3.addCell(cellt1);

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(siq.getGrossProfit()), f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM | TOP);
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

                //fine 2 riga
                //3 riga
                phraset1 = new Phrase();
                phraset1.add(new Chunk("Last Cash On Prem.", f4_bold));
                cellt1 = new PdfPCell(phraset1);
                cellt1.setHorizontalAlignment(ALIGN_LEFT);
                cellt1.setBorder(BOTTOM | TOP);
                cellt1.setFixedHeight(10f);
                cellt1.setBackgroundColor(LIGHT_GRAY);
                table3.addCell(cellt1);

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(siq.getLastCashOnPrem()), f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM | TOP);
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

                phraset1 = new Phrase();
                phraset1.add(new Chunk("Cash On Prem. from transaction", f4_bold));
                cellt1 = new PdfPCell(phraset1);
                cellt1.setHorizontalAlignment(ALIGN_LEFT);
                cellt1.setBorder(BOTTOM | TOP);
                cellt1.setFixedHeight(10f);
                cellt1.setBackgroundColor(LIGHT_GRAY);
                table3.addCell(cellt1);

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(siq.getCashOnPremFromTrans()), f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM | TOP);
                table3.addCell(cellt);

                //fine 3 riga
                //4 riga
                phraset1 = new Phrase();
                phraset1.add(new Chunk("Cash On Prem.", f4_bold));
                cellt1 = new PdfPCell(phraset1);
                cellt1.setHorizontalAlignment(ALIGN_LEFT);
                cellt1.setBorder(BOTTOM | TOP);
                cellt1.setFixedHeight(10f);
                cellt1.setBackgroundColor(LIGHT_GRAY);
                table3.addCell(cellt1);

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(siq.getCashOnPrem()), f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM | TOP);
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

                phraset1 = new Phrase();
                phraset1.add(new Chunk("Cash On Prem. Error", f4_bold));
                cellt1 = new PdfPCell(phraset1);
                cellt1.setHorizontalAlignment(ALIGN_LEFT);
                cellt1.setBorder(BOTTOM | TOP);
                cellt1.setFixedHeight(10f);
                cellt1.setBackgroundColor(LIGHT_GRAY);
                table3.addCell(cellt1);

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(siq.getCashOnPremError()), f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM | TOP);
                table3.addCell(cellt);

                //fine 4 riga
                //5 riga
                phraset1 = new Phrase();
                phraset1.add(new Chunk("Fx", f4_bold));
                cellt1 = new PdfPCell(phraset1);
                cellt1.setHorizontalAlignment(ALIGN_LEFT);
                cellt1.setBorder(BOTTOM | TOP);
                cellt1.setFixedHeight(10f);
                cellt1.setBackgroundColor(LIGHT_GRAY);
                table3.addCell(cellt1);

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(siq.getFx()), f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM | TOP);
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

                phraset1 = new Phrase();
                phraset1.add(new Chunk("Fx closure error declared", f4_bold));
                cellt1 = new PdfPCell(phraset1);
                cellt1.setHorizontalAlignment(ALIGN_LEFT);
                cellt1.setBorder(BOTTOM | TOP);
                cellt1.setFixedHeight(10f);
                cellt1.setBackgroundColor(LIGHT_GRAY);
                table3.addCell(cellt1);

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(siq.getFxClosureErrorDeclared()), f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM | TOP);
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

                phraset1 = new Phrase();
                phraset1.add(new Chunk("Customer Care Refund", f4_bold));
                cellt1 = new PdfPCell(phraset1);
                cellt1.setHorizontalAlignment(ALIGN_LEFT);
                cellt1.setBorder(BOTTOM | TOP);
                cellt1.setFixedHeight(10f);
                cellt1.setBackgroundColor(LIGHT_GRAY);
                table3.addCell(cellt1);

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(siq.getRefund()), f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM | TOP);
                table3.addCell(cellt);

                //fine 5 riga
                document.add(table3);
                //fine tabella 6

                vuoto.setFont(f3_normal);
                document.add(vuoto);

                table3 = new PdfPTable(7);
                table3.setWidths(columnWidths8);
                table3.setWidthPercentage(100);

                //colonne
                phraset1 = new Phrase();
                phraset1.add(new Chunk("", f4_bold));
                cellt1 = new PdfPCell(phraset1);
                cellt1.setHorizontalAlignment(ALIGN_CENTER);
                cellt1.setBorder(BOTTOM);
                cellt1.setFixedHeight(15f);
                cellt1.setBorderWidth(0.7f);
                table3.addCell(cellt1);

                phraset1 = new Phrase();
                phraset1.add(new Chunk("Purchases", f4_bold));
                cellt1 = new PdfPCell(phraset1);
                cellt1.setHorizontalAlignment(ALIGN_RIGHT);
                cellt1.setBorder(BOTTOM | TOP);
                cellt1.setFixedHeight(15f);
                cellt1.setBackgroundColor(LIGHT_GRAY);
                //cellt1.setBorder(Rectangle.BOTTOM);
                cellt1.setBorderWidth(0.7f);
                table3.addCell(cellt1);

                phraset1 = new Phrase();
                phraset1.add(new Chunk("Cash Advance", f4_bold));
                cellt1 = new PdfPCell(phraset1);
                cellt1.setHorizontalAlignment(ALIGN_RIGHT);
                cellt1.setBorder(BOTTOM | TOP);
                cellt1.setFixedHeight(15f);
                cellt1.setBackgroundColor(LIGHT_GRAY);
                //cellt1.setBorder(Rectangle.BOTTOM);
                cellt1.setBorderWidth(0.7f);
                table3.addCell(cellt1);

                phraset1 = new Phrase();
                phraset1.add(new Chunk("Sales", f4_bold));
                cellt1 = new PdfPCell(phraset1);
                cellt1.setHorizontalAlignment(ALIGN_RIGHT);
                cellt1.setBorder(BOTTOM | TOP);
                cellt1.setFixedHeight(15f);
                cellt1.setBackgroundColor(LIGHT_GRAY);
                //cellt1.setBorder(Rectangle.BOTTOM);
                cellt1.setBorderWidth(0.7f);
                table3.addCell(cellt1);

                phraset1 = new Phrase();
                phraset1.add(new Chunk("POS", f4_bold));
                cellt1 = new PdfPCell(phraset1);
                cellt1.setHorizontalAlignment(ALIGN_RIGHT);
                cellt1.setBorder(BOTTOM | TOP);
                cellt1.setFixedHeight(15f);
                cellt1.setBackgroundColor(LIGHT_GRAY);
                cellt1.setBorderWidth(0.7f);
                table3.addCell(cellt1);

                phraset1 = new Phrase();
                phraset1.add(new Chunk("Bank Credit Account", f4_bold));
                cellt1 = new PdfPCell(phraset1);
                cellt1.setHorizontalAlignment(ALIGN_RIGHT);
                cellt1.setBorder(BOTTOM | TOP);
                cellt1.setFixedHeight(15f);
                cellt1.setBackgroundColor(LIGHT_GRAY);
                cellt1.setBorderWidth(0.7f);
                table3.addCell(cellt1);

                phraset1 = new Phrase();
                phraset1.add(new Chunk("Total", f4_bold));
                cellt1 = new PdfPCell(phraset1);
                cellt1.setHorizontalAlignment(ALIGN_RIGHT);
                cellt1.setBorder(BOTTOM | TOP);
                cellt1.setFixedHeight(15f);
                cellt1.setBackgroundColor(LIGHT_GRAY);
                cellt1.setBorderWidth(0.7f);
                table3.addCell(cellt1);

                document.add(table3);

//                sep = new LineSeparator();
////                sep.setOffset(-2);
//                sep.setLineWidth((float) 0.5);
                table3 = new PdfPTable(7);
                table3.setWidths(columnWidths8);
                table3.setWidthPercentage(100);

                //popolamento tabella 7
                //1 riga
                phraset1 = new Phrase();
                phraset1.add(new Chunk("Number of Transaction", f4_bold));
                cellt1 = new PdfPCell(phraset1);
                cellt1.setHorizontalAlignment(ALIGN_LEFT);
                cellt1.setBorder(BOTTOM | TOP);
                cellt1.setFixedHeight(10f);
                cellt1.setBackgroundColor(LIGHT_GRAY);
                table3.addCell(cellt1);

                phraset = new Phrase();
                phraset.add(new Chunk((siq.getNoTransPurch()), f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table3.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(siq.getNoTransCC(), f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table3.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(siq.getNoTransSales(), f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table3.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(siq.getTotPos()), f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table3.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(siq.getTotAcc()), f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table3.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(siq.getTotal()), f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table3.addCell(cellt);

                //fine 1 riga
                document.add(table3);
                //fine tabella 7

//          
                // linea totali
//            PdfPTable table4 = new PdfPTable(5);
//            table4.setWidths(columnWidths2);
//            table4.setWidthPercentage(100);
//
//            //linea totale per categoria
//            LineSeparator ls = new LineSeparator();
//            ls.setLineWidth(0.7f);
//            document.add(ls);
//
//            phraset = new Phrase();
//            phraset.add(new Chunk("Total", f4_bold));
//            cellt = new PdfPCell(phraset);
//            cellt.setHorizontalAlignment(Element.ALIGN_CENTER);
//            cellt.setBorder(Rectangle.BOTTOM);
//            table4.addCell(cellt);
//
//            for (int n = 0; n < 5 - 1; n++) {
//                int tt = 0;
//                for (int x = 0; x < tot.size(); x++) {
//                    if ((n + "").equals(tot.get(x)[0])) {
//                        tt = tt + (Integer.parseInt(tot.get(x)[1]));
//                    }
//                }
//
//                phraset = new Phrase();
//                phraset.add(new Chunk(tt + "", f4_bold));
//                cellt = new PdfPCell(phraset);
//                cellt.setHorizontalAlignment(Element.ALIGN_CENTER);
//                cellt.setBorder(Rectangle.BOTTOM);
//                table4.addCell(cellt);
//
//            }
//
//            document.add(table4);
//            ls.setLineWidth(0.7f);
//            document.add(ls);
                document.newPage();
            }

            //fine totali
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
     * @param listsiq
     * @param datereport1
     * @param datereport2
     * @return
     */
    public String receiptexcel(String path, ArrayList<Daily_value> listsiq, String datereport1, String datereport2) {

        //String outputfile = "Daily.pdf";
        try {

            DateFormat dateFormat = new SimpleDateFormat("ddddMMyyyy");
            Date date = new Date();
            File pdf = new File(path + "Daily" + "_" + dateFormat.format(date) + ".xlsx");
            //File pdf = new File(path + Utility.generaId(50) + "Daily.xls");
            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheet = null;

            int cntriga = 1;
            String myfilactual = "";

            boolean div;

            for (int index = 0; index < listsiq.size(); index++) {

                Daily_value siq = listsiq.get(index);

                div = myfilactual.equalsIgnoreCase(siq.getId_filiale());
                sheet = workbook.createSheet(siq.getId_filiale());
                if (index == 0) {

                    myfilactual = siq.getId_filiale();
                }

                //CREAZIONE FONT
                XSSFFont font = workbook.createFont();
                font.setFontName(FONT_ARIAL);
                font.setFontHeightInPoints((short) 12);
                font.setBold(true);
                XSSFCellStyle style1 = (XSSFCellStyle) workbook.createCellStyle();
                style1.setFont(font);

                XSSFFont font2 = workbook.createFont();
                font2.setFontName(FONT_ARIAL);
                font2.setFontHeightInPoints((short) 12);

                XSSFCellStyle style2 = (XSSFCellStyle) workbook.createCellStyle();
                style2.setFont(font2);

                XSSFFont font3 = workbook.createFont();
                font3.setFontName(FONT_ARIAL);
                font3.setFontHeightInPoints((short) 10);
                font3.setBold(true);

                XSSFCellStyle style3 = (XSSFCellStyle) workbook.createCellStyle();
                style3.setFont(font3);
                style3.setAlignment(RIGHT);
                style3.setBorderTop(THIN);
                style3.setBorderBottom(THIN);

                XSSFCellStyle style3center = (XSSFCellStyle) workbook.createCellStyle();
                style3center.setFont(font3);
                style3center.setAlignment(CENTER);
                style3center.setBorderTop(THIN);
                style3center.setBorderBottom(THIN);
                style3center.setBorderLeft(THIN);
                style3center.setBorderRight(THIN);

                XSSFCellStyle style3left = (XSSFCellStyle) workbook.createCellStyle();
                style3left.setFont(font3);
                style3left.setAlignment(LEFT);
                style3left.setBorderTop(THIN);
                style3left.setBorderBottom(THIN);

                XSSFFont font4 = workbook.createFont();
                font4.setFontName(FONT_ARIAL);
                font4.setFontHeightInPoints((short) 10);

                XSSFCellStyle style4 = (XSSFCellStyle) workbook.createCellStyle();
                style4.setAlignment(RIGHT);
                style4.setBorderTop(THIN);
                style4.setBorderBottom(THIN);

                XSSFCellStyle style4left = (XSSFCellStyle) workbook.createCellStyle();
                style4left.setAlignment(LEFT);
                style4left.setBorderTop(THIN);
                style4left.setBorderBottom(THIN);

                if (!div && index > 0) {
                    myfilactual = siq.getId_filiale();
                    cntriga = 1;
                }

                Row rowP = sheet.createRow(cntriga);
                cntriga++;
                Cell cl = rowP.createCell(1);
                cl.setCellStyle(style1);
                cl.setCellValue(intestazionePdf + " From " + datereport1 + " To " + datereport2);

//                Cell cl2 = rowP.createCell(6);
//                cl2.setCellStyle(style2);
//                cl2.setCellValue(datereport);
                Row row = sheet.createRow((short) cntriga);
                row.createCell(1).setCellValue(siq.getId_filiale() + " " + siq.getDe_filiale());
                cntriga++;
                cntriga++;

                Row row6 = sheet.createRow((short) cntriga);
                cntriga++;
                cntriga++;

                Cell f2 = row6.createCell(1);
                f2.setCellStyle(style3left);
                f2.setCellValue("Till");

                //colonne
                Cell f3 = row6.createCell(2);
                f3.setCellStyle(style3);
                f3.setCellValue("Pay IN/OUT");

                Cell f4 = row6.createCell(3);
                f4.setCellStyle(style3);
                f4.setCellValue("Commissions and Round off");

                Cell f5 = row6.createCell(4);
                f5.setCellStyle(style3);
                f5.setCellValue("Gross/Net Total");

                Cell f6 = row6.createCell(5);
                f6.setCellStyle(style3);
                f6.setCellValue("Spread");

                Cell f7 = row6.createCell(6);
                f7.setCellStyle(style3);
                f7.setCellValue("Profit");

                //popolamento tabella 1
                //1 riga
                cntriga++;
                Row row7 = sheet.createRow((short) cntriga);

                Cell f18 = row7.createCell(1);
                f18.setCellStyle(style3left);
                f18.setCellValue("Purchases");

                Cell f19 = row7.createCell(2);
                f19.setCellStyle(style4);
                f19.setCellValue(formatMysqltoDisplay(siq.getPurchTotal()));

                Cell f20 = row7.createCell(3);
                f20.setCellStyle(style4);
                f20.setCellValue(formatMysqltoDisplay(siq.getPurchComm()));

                Cell f21 = row7.createCell(4);
                f21.setCellStyle(style4);
                f21.setCellValue(formatMysqltoDisplay(siq.getPurchGrossTot()));

                Cell f22 = row7.createCell(5);
                f22.setCellStyle(style4);
                f22.setCellValue(formatMysqltoDisplay(siq.getPurchSpread()));

                Cell f23 = row7.createCell(6);
                f23.setCellStyle(style4);
                f23.setCellValue(formatMysqltoDisplay(siq.getPurchProfit()));

                //fine 1 riga
                cntriga++;
                Row row8 = sheet.createRow((short) cntriga);
                //2 riga

                Cell f24 = row8.createCell(1);
                f24.setCellStyle(style3left);
                f24.setCellValue("Sales");

                Cell f25 = row8.createCell(2);
                f25.setCellStyle(style4);
                f25.setCellValue(formatMysqltoDisplay(siq.getSalesTotal()));

                Cell f26 = row8.createCell(3);
                f26.setCellStyle(style4);
                f26.setCellValue(formatMysqltoDisplay(siq.getSalesComm()));

                Cell f27 = row8.createCell(4);
                f27.setCellStyle(style4);
                f27.setCellValue(formatMysqltoDisplay(siq.getSalesGrossTot()));

                Cell f28 = row8.createCell(5);
                f28.setCellStyle(style4);
                f28.setCellValue(formatMysqltoDisplay(siq.getSalesSpread()));

                Cell f29 = row8.createCell(6);
                f29.setCellStyle(style4);
                f29.setCellValue(formatMysqltoDisplay(siq.getSalesProfit()));

                //fine 2 riga
                cntriga++;
                Row row9 = sheet.createRow((short) cntriga);

                //3 riga
                Cell f30 = row9.createCell(1);
                f30.setCellStyle(style3left);
                f30.setCellValue("Cash Advance");

                Cell f31 = row9.createCell(2);
                f31.setCellStyle(style4);
                f31.setCellValue(formatMysqltoDisplay(siq.getCashAdNetTot()));

                Cell f32 = row9.createCell(3);
                f32.setCellStyle(style4);
                f32.setCellValue(formatMysqltoDisplay(siq.getCashAdComm()));

                Cell f33 = row9.createCell(4);
                f33.setCellStyle(style4);
                f33.setCellValue(formatMysqltoDisplay(siq.getCashAdGrossTot()));

                Cell f34 = row9.createCell(5);
                f34.setCellStyle(style4);
                f34.setCellValue(formatMysqltoDisplay(siq.getCashAdSpread()));

                Cell f35 = row9.createCell(6);
                f35.setCellStyle(style4);
                f35.setCellValue(formatMysqltoDisplay(siq.getCashAdProfit()));

                //fine 3 riga
                cntriga++;
                Row row10 = sheet.createRow((short) cntriga);

                //4 riga totali
                float totnet, totcomm, totgross, totspread, totprofit;

                totnet = ff(siq.getPurchTotal()) + ff(siq.getSalesTotal()) + ff(siq.getCashAdNetTot());
                totcomm = ff(siq.getPurchComm()) + ff(siq.getSalesComm()) + ff(siq.getCashAdComm());
                totgross = ff(siq.getPurchGrossTot()) + ff(siq.getSalesGrossTot()) + ff(siq.getCashAdGrossTot());
                totspread = ff(siq.getPurchSpread()) + ff(siq.getSalesSpread()) + ff(siq.getCashAdSpread());
                totprofit = ff(siq.getPurchProfit()) + ff(siq.getSalesProfit()) + ff(siq.getCashAdProfit());

                Cell f36 = row10.createCell(1);
                f36.setCellStyle(style3left);
                f36.setCellValue("Total");

                Cell f37 = row10.createCell(2);
                f37.setCellStyle(style3);
                f37.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(totnet, 2)) + "");

                Cell f38 = row10.createCell(3);
                f38.setCellStyle(style3);
                f38.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(totcomm, 2)) + "");

                Cell f39 = row10.createCell(4);
                f39.setCellStyle(style3);
                f39.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(totgross, 2)) + "");

                Cell f40 = row10.createCell(5);
                f40.setCellStyle(style3);
                f40.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(totspread, 2)) + "");

                Cell f41 = row10.createCell(6);
                f41.setCellStyle(style3);
                f41.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(totprofit, 2)) + "");

                //fine 4 riga
                cntriga++;
                cntriga++;
                cntriga++;

                //fine 1 tabella
                //2 tabella
                cntriga++;
                Row row11 = sheet.createRow((short) cntriga);

                CellRangeAddress cellRangeAddress = new CellRangeAddress(cntriga, cntriga, 2, 3);
                sheet.addMergedRegion(cellRangeAddress);
                cellRangeAddress = new CellRangeAddress(cntriga, cntriga, 4, 5);
                sheet.addMergedRegion(cellRangeAddress);
                cellRangeAddress = new CellRangeAddress(cntriga, cntriga, 6, 7);
                sheet.addMergedRegion(cellRangeAddress);
                cellRangeAddress = new CellRangeAddress(cntriga, cntriga, 8, 9);
                sheet.addMergedRegion(cellRangeAddress);

                Cell f42 = row11.createCell(1);
                f42.setCellStyle(style3left);
                f42.setCellValue("POS");

                Cell f43 = row11.createCell(2);
                f43.setCellStyle(style3center);
                f43.setCellValue("04 - Cash Advance");

                Cell f4b3 = row11.createCell(3);
                f4b3.setCellStyle(style3center);

                Cell f44 = row11.createCell(4);
                f44.setCellStyle(style3center);
                f44.setCellValue("06 - Credit Card - CHANGE");

                Cell f44b = row11.createCell(5);
                f44b.setCellStyle(style3center);

                Cell f45 = row11.createCell(6);
                f45.setCellStyle(style3center);
                f45.setCellValue("06 - Credit Card - NO CHANGE");

                Cell f45b = row11.createCell(7);
                f45b.setCellStyle(style3center);

                Cell f46 = row11.createCell(8);
                f46.setCellStyle(style3center);
                f46.setCellValue("06 - Credit Card - Total");

                Cell f46b = row11.createCell(9);
                f46b.setCellStyle(style3center);

                // 2 riga intestazione
                cntriga++;
                Row row12 = sheet.createRow((short) cntriga);

                //2 tabella
                Cell f47 = row12.createCell(2);
                f47.setCellStyle(style3);
                f47.setCellValue("No.");

                Cell f48 = row12.createCell(3);
                f48.setCellStyle(style3);
                f48.setCellValue("Amount");

                Cell f49 = row12.createCell(4);
                f49.setCellStyle(style3);
                f49.setCellValue("No.");

                Cell f50 = row12.createCell(5);
                f50.setCellStyle(style3);
                f50.setCellValue("Amount");

                Cell f51 = row12.createCell(6);
                f51.setCellStyle(style3);
                f51.setCellValue("No.");

                Cell f52 = row12.createCell(7);
                f52.setCellStyle(style3);
                f52.setCellValue("Amount");

                Cell f53 = row12.createCell(8);
                f53.setCellStyle(style3);
                f53.setCellValue("No.");

                Cell f54 = row12.createCell(9);
                f54.setCellStyle(style3);
                f54.setCellValue("Amount");

                //tot predisp.
                int totNTransCashAd = 0;
                int totNTransCC = 0;
                int totNCNTransCC = 0;
                int totNTransBank;
                int totNCNTransBank = 0;
                int totncnttransbank = 0;
                int totNTransTot = 0;

                float totAmountCashAd = 0;
                double totNamountBank = 0;
                double totNCNamountBank = 0;
                double totncamountbank = 0;
                float totAmountCC = 0;
                float totNCAmountCC = 0;

                //popolamento tabella 2
                cntriga++;

                for (int i = 0; i < siq.getDatiCOP().size(); i++) {
                    DailyCOP dc = (DailyCOP) siq.getDatiCOP().get(i);

                    cntriga++;
                    Row row13 = sheet.createRow((short) cntriga);

                    Cell f55 = row13.createCell(1);
                    f55.setCellStyle(style4left);
                    f55.setCellValue(dc.getPos());

                    Cell f56 = row13.createCell(2);
                    f56.setCellStyle(style4);
                    f56.setCellValue(dc.getCashAdNtrans());

                    Cell f57 = row13.createCell(3);
                    f57.setCellStyle(style4);
                    f57.setCellValue(formatMysqltoDisplay(dc.getCashAdAmount()));

                    Cell f58 = row13.createCell(4);
                    f58.setCellStyle(style4);
                    f58.setCellValue(dc.getCcNtrans());

                    Cell f59 = row13.createCell(5);
                    f59.setCellStyle(style4);
                    f59.setCellValue(formatMysqltoDisplay(dc.getCcAmount()));

                    Cell f60 = row13.createCell(6);
                    f60.setCellStyle(style4);
                    f60.setCellValue(dc.getNC_ccNtrans());

                    Cell f61 = row13.createCell(7);
                    f61.setCellStyle(style4);
                    f61.setCellValue(formatMysqltoDisplay(dc.getNC_ccAmount()));

//                    Cell f60 = row13.createCell(6);
//                    f60.setCellStyle(style4);
//                    f60.setCellValue(dc.getBankNtrans());
//
//                    Cell f61 = row13.createCell(7);
//                    f61.setCellStyle(style4);
//                    f61.setCellValue(Utility.formatMysqltoDisplay(dc.getBankAmount()));
                    float totNtrans = ff(dc.getCcNtrans()) + ff(dc.getNC_ccNtrans());
                    float totAmount = ff(dc.getCcAmount()) + ff(dc.getNC_ccAmount());

//                    
//                    int totNtrans = Integer.parseInt(dc.getCashAdNtrans()) + Integer.parseInt(dc.getCcNtrans()) + Integer.parseInt(dc.getBankNtrans());
//                    float totAmount = ff(dc.getCashAdAmount()) + ff(dc.getCcAmount()) + ff(dc.getBankAmount());
                    Cell f62 = row13.createCell(8);
                    f62.setCellStyle(style4);
                    f62.setCellValue(roundDoubleandFormat(totNtrans, 0) + "");

                    Cell f63 = row13.createCell(9);
                    f63.setCellStyle(style4);
                    f63.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(totAmount, 2)) + "");

                    totNTransCashAd += parseInt(dc.getCashAdNtrans());
                    totNTransCC += parseInt(dc.getCcNtrans());
                    totNCNTransCC += parseInt(dc.getNC_ccNtrans());

                    totNTransTot += totNtrans;

                    totAmountCashAd += ff(dc.getCashAdAmount());
                    totAmountCC += ff(dc.getCcAmount());
                    totNCAmountCC += ff(dc.getNC_ccAmount());

                }

                //fine 2 riga
                cntriga++;
                Row row14 = sheet.createRow((short) cntriga);

                Cell f64 = row14.createCell(1);
                f64.setCellStyle(style3left);
                f64.setCellValue("Total");

                Cell f65 = row14.createCell(2);
                f65.setCellStyle(style3);
                f65.setCellValue(totNTransCashAd + "");

                Cell f66 = row14.createCell(3);
                f66.setCellStyle(style3);
                f66.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(totAmountCashAd, 2)) + "");

                Cell f67 = row14.createCell(4);
                f67.setCellStyle(style3);
                f67.setCellValue(totNTransCC + "");

                Cell f68 = row14.createCell(5);
                f68.setCellStyle(style3);
                f68.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(totAmountCC, 2)) + "");

                Cell f69 = row14.createCell(6);
                f69.setCellStyle(style3);
                f69.setCellValue(totNCNTransCC + "");

                Cell f70 = row14.createCell(7);
                f70.setCellStyle(style3);
                f70.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(totNCNTransCC + totNTransCC, 2)) + "");

                Cell f71 = row14.createCell(8);
                f71.setCellStyle(style3);
                f71.setCellValue(totNTransTot + "");

                Cell f72 = row14.createCell(9);
                f72.setCellStyle(style3);
                f72.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(totAmountCC + totNCAmountCC, 2)) + "");

                //totali
                // CRUuu
                cntriga++;
                cntriga++;
                cntriga++;
                Row row15bis = sheet.createRow((short) cntriga);

                cellRangeAddress = new CellRangeAddress(cntriga, cntriga, 2, 3);
                sheet.addMergedRegion(cellRangeAddress);
                cellRangeAddress = new CellRangeAddress(cntriga, cntriga, 4, 5);
                sheet.addMergedRegion(cellRangeAddress);
                cellRangeAddress = new CellRangeAddress(cntriga, cntriga, 6, 7);
                sheet.addMergedRegion(cellRangeAddress);
                cellRangeAddress = new CellRangeAddress(cntriga, cntriga, 8, 9);
                sheet.addMergedRegion(cellRangeAddress);

                Cell f42b = row15bis.createCell(1);
                f42b.setCellStyle(style3left);
                f42b.setCellValue("POS");

                Cell f43b = row15bis.createCell(2);
                f43b.setCellStyle(style3center);
                f43b.setCellValue("07 - Bancomat - CHANGE");

                Cell f43bb = row15bis.createCell(3);
                f43bb.setCellStyle(style3center);

                Cell f44bb = row15bis.createCell(4);
                f44bb.setCellStyle(style3center);
                f44bb.setCellValue("07 - Bancomat - NO CHANGE");

                Cell f44bbb = row15bis.createCell(5);
                f44bbb.setCellStyle(style3center);

                Cell f45bb = row15bis.createCell(6);
                f45bb.setCellStyle(style3center);
                f45bb.setCellValue("07 - Bancomat Total");

                Cell f45bbb = row15bis.createCell(7);
                f45bbb.setCellStyle(style3center);

                Cell f46bb = row15bis.createCell(8);
                f46bb.setCellStyle(style3center);
                f46bb.setCellValue("POS Total");

                Cell f46bbb = row15bis.createCell(9);
                f46bbb.setCellStyle(style3center);

                // 2 riga intestazione
                cntriga++;
                Row row12bis = sheet.createRow((short) cntriga);

                //2 tabella
                Cell f47b = row12bis.createCell(2);
                f47b.setCellStyle(style3);
                f47b.setCellValue("No.");

                Cell f48b = row12bis.createCell(3);
                f48b.setCellStyle(style3);
                f48b.setCellValue("Amount");

                Cell f49b = row12bis.createCell(4);
                f49b.setCellStyle(style3);
                f49b.setCellValue("No.");

                Cell f50b = row12bis.createCell(5);
                f50b.setCellStyle(style3);
                f50b.setCellValue("Amount");

                Cell f51b = row12bis.createCell(6);
                f51b.setCellStyle(style3);
                f51b.setCellValue("No.");

                Cell f52b = row12bis.createCell(7);
                f52b.setCellStyle(style3);
                f52b.setCellValue("Amount");

                Cell f53b = row12bis.createCell(8);
                f53b.setCellStyle(style3);
                f53b.setCellValue("No.");

                Cell f54b = row12bis.createCell(9);
                f54b.setCellStyle(style3);
                f54b.setCellValue("Amount");

                //tot predisp.
                totNTransCashAd = 0;
                totNTransCC = 0;
                totNTransBank = 0;
                totAmountCC = 0;

                //popolamento tabella 2
                cntriga++;

                for (int i = 0; i < siq.getDatiCOP().size(); i++) {
                    DailyCOP dc = (DailyCOP) siq.getDatiCOP().get(i);

                    cntriga++;
                    Row row13 = sheet.createRow((short) cntriga);

                    Cell f55 = row13.createCell(1);
                    f55.setCellStyle(style4left);
                    f55.setCellValue(dc.getPos());

                    Cell f56 = row13.createCell(2);
                    f56.setCellStyle(style4);
                    f56.setCellValue(dc.getBankNtrans());

                    Cell f57 = row13.createCell(3);
                    f57.setCellStyle(style4);
                    f57.setCellValue(formatMysqltoDisplay(dc.getBankAmount()));

                    Cell f58 = row13.createCell(4);
                    f58.setCellStyle(style4);
                    f58.setCellValue(dc.getNC_bankNtrans());

                    Cell f59 = row13.createCell(5);
                    f59.setCellStyle(style4);
                    f59.setCellValue(formatMysqltoDisplay(dc.getNC_bankAmount()));

                    float totNtrans = ff(dc.getBankNtrans()) + ff(dc.getNC_bankNtrans());
                    float totAmount = ff(dc.getBankAmount()) + ff(dc.getNC_bankAmount());

                    Cell f60 = row13.createCell(6);
                    f60.setCellStyle(style4);
                    f60.setCellValue(totNtrans);

                    Cell f61 = row13.createCell(7);
                    f61.setCellStyle(style4);
                    f61.setCellValue(totAmount);

//                    Cell f60 = row13.createCell(6);
//                    f60.setCellStyle(style4);
//                    f60.setCellValue(dc.getBankNtrans());
//
//                    Cell f61 = row13.createCell(7);
//                    f61.setCellStyle(style4);
//                    f61.setCellValue(Utility.formatMysqltoDisplay(dc.getBankAmount()));
                    totNTransBank += parseInt(dc.getBankNtrans()) + parseInt(dc.getNC_bankNtrans());

                    int totNTransTotFinal = parseIntR(dc.getCcNtrans()) + parseIntR(dc.getCashAdNtrans())
                            + parseIntR(dc.getNC_ccNtrans()) + parseIntR(dc.getBankNtrans())
                            + parseIntR(dc.getNC_bankNtrans());

                    double totAmountTotfinal = fd(dc.getCcAmount()) + fd(dc.getCashAdAmount()) + fd(dc.getNC_ccAmount())
                            + fd(dc.getBankAmount()) + fd(dc.getNC_bankAmount());

                    Cell f62 = row13.createCell(8);
                    f62.setCellStyle(style4);
                    f62.setCellValue(roundDoubleandFormat(totNTransTotFinal, 0) + "");

                    Cell f63 = row13.createCell(9);
                    f63.setCellStyle(style4);
                    f63.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(totAmountTotfinal, 2)) + "");

                    //totNTransCashAd += Integer.parseInt(dc.getCashAdNtrans());
                    totNTransBank += parseInt(dc.getBankNtrans());
                    totNCNTransBank += parseInt(dc.getNC_bankNtrans());
                    totncnttransbank = totncnttransbank + parseInt(dc.getBankNtrans()) + parseInt(dc.getNC_bankNtrans());

                    totNamountBank += fd(dc.getBankAmount());
                    totNCNamountBank += fd(dc.getNC_bankAmount());
                    totncamountbank = totncamountbank + fd(dc.getBankAmount()) + fd(dc.getNC_bankAmount());

                }

                //fine 2 riga
                cntriga++;
                Row row14bis = sheet.createRow((short) cntriga);

                Cell f64b = row14bis.createCell(1);
                f64b.setCellStyle(style3left);
                f64b.setCellValue("Total");

                Cell f69b = row14bis.createCell(2);
                f69b.setCellStyle(style3);
                f69b.setCellValue(totNTransBank + "");

                Cell f70b = row14bis.createCell(3);
                f70b.setCellStyle(style3);
                f70b.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(totNamountBank, 2)) + "");

                Cell f65b = row14bis.createCell(4);
                f65b.setCellStyle(style3);
                f65b.setCellValue(totNCNTransBank);

                Cell f66b = row14bis.createCell(5);
                f66b.setCellStyle(style3);
                f66b.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(totNCNamountBank, 2)) + "");

                Cell f71b = row14bis.createCell(6);
                f71b.setCellStyle(style3);
                f71b.setCellValue(totncnttransbank + "");

                Cell f72b = row14bis.createCell(7);
                f72b.setCellStyle(style3);
                f72b.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(totncamountbank, 2)) + "");

                Cell f71bb = row14bis.createCell(8);
                f71bb.setCellStyle(style3);
                f71bb.setCellValue(valueOf(totncnttransbank + totNTransCashAd + totNTransCC));

                Cell f72bb = row14bis.createCell(9);
                f72bb.setCellStyle(style3);
                f72bb.setCellValue(totncamountbank + totAmountCC + totNCAmountCC);

                cntriga++;
                cntriga++;
                cntriga++;
                Row row15 = sheet.createRow((short) cntriga);

                cellRangeAddress = new CellRangeAddress(cntriga, cntriga, 2, 3);
                sheet.addMergedRegion(cellRangeAddress);

                Cell f73 = row15.createCell(1);
                f73.setCellStyle(style3left);
                f73.setCellValue("08 - Bank Account");

                Cell f74 = row15.createCell(2);
                f74.setCellStyle(style3center);
                f74.setCellValue("Total");

                Cell f74b = row15.createCell(3);
                f74b.setCellStyle(style3center);

                //colonne
                cntriga++;
                Row row16 = sheet.createRow((short) cntriga);

                //colonne
                Cell f75 = row16.createCell(2);
                f75.setCellStyle(style3);
                f75.setCellValue("N. trans");

                Cell f76 = row16.createCell(3);
                f76.setCellStyle(style3);
                f76.setCellValue("Amount");

                cntriga++;

                //tot predisp.
                int bankTotNTransTot = 0;
                float bankTotAmountTot = 0;

                //popolamento tabella 2
                for (int i = 0; i < siq.getDatiBank().size(); i++) {

                    cntriga++;
                    Row row17 = sheet.createRow((short) cntriga);

                    DailyBank db = (DailyBank) siq.getDatiBank().get(i);

                    Cell f77 = row17.createCell(1);
                    f77.setCellStyle(style4left);
                    f77.setCellValue(db.getBank());

                    Cell f78 = row17.createCell(2);
                    f78.setCellStyle(style4);
                    f78.setCellValue(db.getNtrans());

                    Cell f79 = row17.createCell(3);
                    f79.setCellStyle(style4);
                    f79.setCellValue(formatMysqltoDisplay(db.getAmount()));

                    bankTotNTransTot += parseInt(db.getNtrans());
                    bankTotAmountTot += ff(db.getAmount());

                }

                //fine 2 riga
                cntriga++;
                Row row18 = sheet.createRow((short) cntriga);

                Cell f80 = row18.createCell(1);
                f80.setCellStyle(style3left);
                f80.setCellValue("Total");

                Cell f81 = row18.createCell(2);
                f81.setCellStyle(style3);
                f81.setCellValue(bankTotNTransTot + "");

                Cell f82 = row18.createCell(3);
                f82.setCellStyle(style3);
                f82.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(bankTotAmountTot, 2)) + "");

                //totali
                //fine tabella bankaccount
                cntriga++;
                cntriga++;
                cntriga++;
                Row row19 = sheet.createRow((short) cntriga);

                //colonne
                Cell f83 = row19.createCell(1);
                f83.setCellStyle(style3left);
                f83.setCellValue("Bank");

                Cell f84 = row19.createCell(2);
                f84.setCellStyle(style3);
                f84.setCellValue("Other Currencies");

                Cell f85 = row19.createCell(3);
                f85.setCellStyle(style3);
                f85.setCellValue("Spread");

                Cell f86 = row19.createCell(4);
                f86.setCellStyle(style3);
                f86.setCellValue("Cash Advance");

                Cell f87 = row19.createCell(5);
                f87.setCellStyle(style3);
                f87.setCellValue(localcurrency);

                Cell f88 = row19.createCell(6);
                f88.setCellStyle(style3);
                f88.setCellValue("Other Figures");

                cntriga++;
                Row row20 = sheet.createRow((short) cntriga);

                Cell f89 = row20.createCell(1);
                f89.setCellStyle(style3left);
                f89.setCellValue("Purchases");

                Cell f90 = row20.createCell(2);
                f90.setCellStyle(style4);
                f90.setCellValue(formatMysqltoDisplay(siq.getBaPurchTotal()));

                Cell f91 = row20.createCell(3);
                f91.setCellStyle(style4);
                f91.setCellValue(formatMysqltoDisplay(siq.getBaPurchSpread()));

                Cell f92 = row20.createCell(4);
                f92.setCellStyle(style4);
                f92.setCellValue(formatMysqltoDisplay(siq.getBaPurchCreditCard()));

                Cell f93 = row20.createCell(5);
                f93.setCellStyle(style4);
                f93.setCellValue(formatMysqltoDisplay(siq.getBaPurchTransfNotes()));

                Cell f94 = row20.createCell(6);
                f94.setCellStyle(style4);
                f94.setCellValue(formatMysqltoDisplay(siq.getBaPurchTransfOther()));

                //popolamento tabella 3
                //1 riga
                //fine 1 riga
                cntriga++;
                Row row21 = sheet.createRow((short) cntriga);

                //2 riga
                Cell f95 = row21.createCell(1);
                f95.setCellStyle(style3left);
                f95.setCellValue("Sales");

                Cell f96 = row21.createCell(2);
                f96.setCellStyle(style4);
                f96.setCellValue(formatMysqltoDisplay(siq.getBaSalesTotal()));

                Cell f97 = row21.createCell(3);
                f97.setCellStyle(style4);
                f97.setCellValue(formatMysqltoDisplay(siq.getBaSalesSpread()));

                Cell f98 = row21.createCell(4);
                f98.setCellStyle(style4);
                f98.setCellValue(formatMysqltoDisplay(siq.getBaSalesCreditCard()));

                Cell f99 = row21.createCell(5);
                f99.setCellStyle(style4);
                f99.setCellValue(formatMysqltoDisplay(siq.getBaSalesTransfNotes()));

                Cell f100 = row21.createCell(6);
                f100.setCellStyle(style4);
                f100.setCellValue(formatMysqltoDisplay(siq.getBaSalesTransfOther()));

                cntriga++;
                cntriga++;
                cntriga++;
                Row row22 = sheet.createRow((short) cntriga);

                //fine 2 riga
                //fine tabella 3
                //4 tabella
                //colonne
                Cell f101 = row22.createCell(1);
                f101.setCellStyle(style3left);
                f101.setCellValue("Branch");

                Cell f102 = row22.createCell(2);
                f102.setCellStyle(style3);
                f102.setCellValue("Other Currencies");

                Cell f103 = row22.createCell(3);
                f103.setCellStyle(style3);
                f103.setCellValue("Spread");

                Cell f104 = row22.createCell(4);
                f104.setCellStyle(style3);
                f104.setCellValue(localcurrency);

                cntriga++;
                Row row23 = sheet.createRow((short) cntriga);

                Cell f105 = row23.createCell(1);
                f105.setCellStyle(style3left);
                f105.setCellValue("Purchases");

                Cell f106 = row23.createCell(2);
                f106.setCellStyle(style4);
                f106.setCellValue(formatMysqltoDisplay(siq.getBraPurchTotal()));

                Cell f107 = row23.createCell(3);
                f107.setCellStyle(style4);
                f107.setCellValue(formatMysqltoDisplay(siq.getBraPurchSpread()));

                Cell f108 = row23.createCell(4);
                f108.setCellStyle(style4);
                f108.setCellValue(formatMysqltoDisplay(siq.getBraPurchLocalCurr()));

                //popolamento tabella 4
                //1 riga
                //fine 1 riga
                cntriga++;
                Row row24 = sheet.createRow((short) cntriga);

                Cell f109 = row24.createCell(1);
                f109.setCellStyle(style3left);
                f109.setCellValue("Sales");

                Cell f110 = row24.createCell(2);
                f110.setCellStyle(style4);
                f110.setCellValue(formatMysqltoDisplay(siq.getBraSalesTotal()));

                Cell f111 = row24.createCell(3);
                f111.setCellStyle(style4);
                f111.setCellValue(formatMysqltoDisplay(siq.getBraSalesSpread()));

                Cell f112 = row24.createCell(4);
                f112.setCellStyle(style4);
                f112.setCellValue(formatMysqltoDisplay(siq.getBraSalesLocalCurr()));

                //2 riga
                cntriga++;
                cntriga++;
                cntriga++;
                Row row25 = sheet.createRow((short) cntriga);

                Cell f113 = row25.createCell(3);
                f113.setCellStyle(style3);
                f113.setCellValue("No. Tran.");

                Cell f114 = row25.createCell(4);
                f114.setCellStyle(style3);
                f114.setCellValue("Total");

                Cell f115 = row25.createCell(5);
                f115.setCellStyle(style3);
                f115.setCellValue("Notes");

                Cell f116 = row25.createCell(6);
                f116.setCellStyle(style3);
                f116.setCellValue("Credit Card");

                Cell f117 = row25.createCell(7);
                f117.setCellStyle(style3);
                f117.setCellValue("Bancomat");

                //colonne
                cntriga++;

                //popolamento tabella 7
                ArrayList<DailyKind> array = siq.getDati();

                int ntr = 0;
                float totKind = 0;
                float localCurr = 0;
                float cc = 0;
                float b = 0;

                for (int i = 0; i < array.size(); i++) {

                    DailyKind d = array.get(i);

                    if (d.isTo()) {

                        cntriga++;
                        Row row27 = sheet.createRow((short) cntriga);

                        Cell f121 = row27.createCell(1);
                        f121.setCellStyle(style3left);
                        f121.setCellValue(d.getKind());

                        Cell f122 = row27.createCell(2);
                        f122.setCellStyle(style3left);
                        f122.setCellValue(d.getEtichetta1());

                        Cell f123 = row27.createCell(3);
                        f123.setCellStyle(style4);
                        f123.setCellValue(d.getToNrTran());

                        ntr += parseInt(d.getToNrTran());

                        Cell f124 = row27.createCell(4);
                        f124.setCellStyle(style4);
                        f124.setCellValue(formatMysqltoDisplay(d.getToTotal()));

                        totKind += ff(d.getToTotal());

                        Cell f125 = row27.createCell(5);
                        f125.setCellStyle(style4);
                        f125.setCellValue(formatMysqltoDisplay(d.getToLocalCurr()));

                        localCurr += ff(d.getToLocalCurr());

                        Cell f126 = row27.createCell(6);
                        f126.setCellStyle(style4);
                        f126.setCellValue(formatMysqltoDisplay(d.getToCC()));

                        cc += ff(d.getToCC());

                        Cell f127 = row27.createCell(7);
                        f127.setCellStyle(style4);
                        f127.setCellValue(formatMysqltoDisplay(d.getToB()));

                        b += ff(d.getToB());

                    }

                    if (d.isFrom()) {
                        cntriga++;
                        Row row27 = sheet.createRow((short) cntriga);

                        if (d.isTo()) {
//                            phraset1 = new Phrase();
//                            phraset1.add(new Chunk("", f4_bold));
//                            cellt1 = new PdfPCell(phraset1);
//                            cellt1.setHorizontalAlignment(Element.ALIGN_CENTER);
//                            cellt1.setBorder(Rectangle.BOTTOM | Rectangle.TOP);
//                            cellt1.setFixedHeight(10f);
//                            table3.addCell(cellt1);
                        } else {

                            Cell f127 = row27.createCell(1);
                            f127.setCellStyle(style3left);
                            f127.setCellValue(d.getKind());

                        }

                        //
                        Cell f128 = row27.createCell(2);
                        f128.setCellStyle(style3left);
                        f128.setCellValue(d.getEtichetta2());

                        Cell f129 = row27.createCell(3);
                        f129.setCellStyle(style4);
                        f129.setCellValue(d.getFromNrTran());

                        ntr += parseInt(d.getFromNrTran());

                        Cell f130 = row27.createCell(4);
                        f130.setCellStyle(style4);
                        f130.setCellValue(formatMysqltoDisplay(d.getFromTotal()));

                        totKind += ff(d.getFromTotal());

                        Cell f131 = row27.createCell(5);
                        f131.setCellStyle(style4);
                        f131.setCellValue(formatMysqltoDisplay(d.getFromLocalCurr()));

                        localCurr += ff(d.getFromLocalCurr());

                        Cell f132 = row27.createCell(6);
                        f132.setCellStyle(style4);
                        f132.setCellValue(formatMysqltoDisplay(d.getFromCC()));

                        cc += ff(d.getFromCC());

                        Cell f133 = row27.createCell(7);
                        f133.setCellStyle(style4);
                        f133.setCellValue(formatMysqltoDisplay(d.getFromB()));

                        b += ff(d.getFromB());

                    }

                }

                cntriga++;
                Row row28 = sheet.createRow((short) cntriga);

                Cell f118 = row28.createCell(2);
                f118.setCellStyle(style3left);
                f118.setCellValue("Total");

                Cell f119 = row28.createCell(3);
                f119.setCellStyle(style3);
                f119.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(ntr, 0) + ""));

                Cell f120 = row28.createCell(4);
                f120.setCellStyle(style3);
                f120.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(totKind, 2) + ""));

                Cell f121 = row28.createCell(5);
                f121.setCellStyle(style3);
                f121.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(localCurr, 2) + ""));

                Cell f122 = row28.createCell(6);
                f122.setCellStyle(style3);
                f122.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(cc, 2) + ""));

                Cell f123 = row28.createCell(7);
                f123.setCellStyle(style3);
                f123.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(b, 2) + ""));

                //totali
                //fine totali
                cntriga++;
                cntriga++;
                cntriga++;
                Row row29 = sheet.createRow((short) cntriga);

                Cell f130 = row29.createCell(1);
                f130.setCellStyle(style3left);
                f130.setCellValue("Gross Turnover");

                Cell f131 = row29.createCell(2);
                f131.setCellStyle(style4);
                f131.setCellValue(formatMysqltoDisplay(siq.getGroffTurnover()));

                //popolamento tabella 6
                //1 riga
                //fine 1 riga
                cntriga++;
                Row row30 = sheet.createRow((short) cntriga);

                Cell f132 = row30.createCell(1);
                f132.setCellStyle(style3left);
                f132.setCellValue("Gross Profit");

                Cell f133 = row30.createCell(2);
                f133.setCellStyle(style4);
                f133.setCellValue(formatMysqltoDisplay(siq.getGrossProfit()));

                //2 riga
                //fine 2 riga
                //3 riga
                cntriga++;
                Row row31 = sheet.createRow((short) cntriga);

                Cell f140 = row31.createCell(1);
                f140.setCellStyle(style3left);
                f140.setCellValue("Last Cash On Prem.");

                Cell f141 = row31.createCell(2);
                f141.setCellStyle(style4);
                f141.setCellValue(formatMysqltoDisplay(siq.getLastCashOnPrem()));

                Cell f142 = row31.createCell(5);
                f142.setCellStyle(style3left);
                f142.setCellValue("Cash On Prem. from transaction");

                Cell f143 = row31.createCell(6);
                f143.setCellStyle(style4);
                f143.setCellValue(formatMysqltoDisplay(siq.getCashOnPremFromTrans()));

                //fine 3 riga
                cntriga++;
                Row row32 = sheet.createRow((short) cntriga);

                //4 riga
                Cell f144 = row32.createCell(1);
                f144.setCellStyle(style3left);
                f144.setCellValue("Cash On Prem.");

                Cell f145 = row32.createCell(2);
                f145.setCellStyle(style4);
                f145.setCellValue(formatMysqltoDisplay(siq.getCashOnPrem()));

                Cell f146 = row32.createCell(5);
                f146.setCellStyle(style3left);
                f146.setCellValue("Cash On Prem. Error");

                Cell f147 = row32.createCell(6);
                f147.setCellStyle(style4);
                f147.setCellValue(formatMysqltoDisplay(siq.getCashOnPremError()));

                //fine 4 riga
                //5 riga
                cntriga++;
                Row row33 = sheet.createRow((short) cntriga);

                Cell f148 = row33.createCell(1);
                f148.setCellStyle(style3left);
                f148.setCellValue("Fx");

                Cell f149 = row33.createCell(2);
                f149.setCellStyle(style4);
                f149.setCellValue(formatMysqltoDisplay(siq.getFx()));

                Cell f150 = row33.createCell(5);
                f150.setCellStyle(style3left);
                f150.setCellValue("Fx closure error declared");

                Cell f151 = row33.createCell(6);
                f151.setCellStyle(style4);
                f151.setCellValue(formatMysqltoDisplay(siq.getFxClosureErrorDeclared()));

                //fine 5 riga
                //fine tabella 6
                //7 tabella
                cntriga++;
                cntriga++;
                cntriga++;
                cntriga++;
                Row row34 = sheet.createRow((short) cntriga);

                //colonne
                Cell f152 = row34.createCell(2);
                f152.setCellStyle(style3);
                f152.setCellValue("Purchases");

                Cell f153 = row34.createCell(3);
                f153.setCellStyle(style3);
                f153.setCellValue("Cash Advance");

                Cell f154 = row34.createCell(4);
                f154.setCellStyle(style3);
                f154.setCellValue("Sales");

                Cell f155 = row34.createCell(5);
                f155.setCellStyle(style3);
                f155.setCellValue("POS");

                Cell f156 = row34.createCell(6);
                f156.setCellStyle(style3);
                f156.setCellValue("Bank Credit Account");

                Cell f157 = row34.createCell(7);
                f157.setCellStyle(style3);
                f157.setCellValue("Total");

                cntriga++;
                cntriga++;
                cntriga++;
                Row row35 = sheet.createRow((short) cntriga);

                //popolamento tabella 7
                //1 riga
                Cell f158 = row35.createCell(1);
                f158.setCellStyle(style3left);
                f158.setCellValue("Number of Transaction");

                Cell f159 = row35.createCell(2);
                f159.setCellStyle(style4);
                f159.setCellValue(siq.getNoTransPurch());

                Cell f160 = row35.createCell(3);
                f160.setCellStyle(style4);
                f160.setCellValue(siq.getNoTransCC());

                Cell f161 = row35.createCell(4);
                f161.setCellStyle(style4);
                f161.setCellValue(siq.getNoTransSales());

                Cell f162 = row35.createCell(5);
                f162.setCellStyle(style4);
                f162.setCellValue(siq.getTotPos());

                Cell f163 = row35.createCell(6);
                f163.setCellStyle(style4);
                f163.setCellValue(siq.getTotAcc());

                Cell f164 = row35.createCell(7);
                f164.setCellStyle(style4);
                f164.setCellValue(siq.getTotal());

                //fine 1 riga
                //fine tabella 7
//          
                // linea totali
//            PdfPTable table4 = new PdfPTable(5);
//            table4.setWidths(columnWidths2);
//            table4.setWidthPercentage(100);
//
//            //linea totale per categoria
//            LineSeparator ls = new LineSeparator();
//            ls.setLineWidth(0.7f);
//            document.add(ls);
//
//            phraset = new Phrase();
//            phraset.add(new Chunk("Total", f4_bold));
//            cellt = new PdfPCell(phraset);
//            cellt.setHorizontalAlignment(Element.ALIGN_CENTER);
//            cellt.setBorder(Rectangle.BOTTOM);
//            table4.addCell(cellt);
//
//            for (int n = 0; n < 5 - 1; n++) {
//                int tt = 0;
//                for (int x = 0; x < tot.size(); x++) {
//                    if ((n + "").equals(tot.get(x)[0])) {
//                        tt = tt + (Integer.parseInt(tot.get(x)[1]));
//                    }
//                }
//
//                phraset = new Phrase();
//                phraset.add(new Chunk(tt + "", f4_bold));
//                cellt = new PdfPCell(phraset);
//                cellt.setHorizontalAlignment(Element.ALIGN_CENTER);
//                cellt.setBorder(Rectangle.BOTTOM);
//                table4.addCell(cellt);
//
//            }
//
//            document.add(table4);
//            ls.setLineWidth(0.7f);
//            document.add(ls);
                cntriga++;
                cntriga++;

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

            }

            //fine totali
            //sheet.autoSizeColumn(0);
            if (sheet != null) {
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
            }
        } catch (IOException ex) {
            insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
        }
        return null;
    }

}
