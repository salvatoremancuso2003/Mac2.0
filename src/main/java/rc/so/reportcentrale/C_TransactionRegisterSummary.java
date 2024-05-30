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
import static rc.so.util.Engine.insertTR;
import static rc.so.util.Utility.fd;
import static rc.so.util.Utility.formatMysqltoDisplay;
import static rc.so.util.Utility.generaId;
import static rc.so.util.Utility.roundDoubleandFormat;
import java.awt.Color;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import static java.lang.String.format;
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
import static org.apache.poi.ss.usermodel.BorderStyle.MEDIUM;
import static org.apache.poi.ss.usermodel.BorderStyle.THIN;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import static org.apache.poi.ss.usermodel.HorizontalAlignment.CENTER;
/**
 *
 * @author fplacanica
 */
public class C_TransactionRegisterSummary {

    //column

    /**
     *
     */
    public static final float[] columnWidths0 = new float[]{70f, 30f};

    /**
     *
     */
    public static final float[] columnWidths0intestaz = new float[]{90f, 10f, 10f, 10f, 10f, 10f, 10f, 10f, 10f, 10f};

    /**
     *
     */
    public static final float[] columnWidths1 = new float[]{5f, 5f, 5f, 5f, 5f, 5f, 60f};

    /**
     *
     */
    public static float[] columnWidths2 = new float[]{10f, 10f, 10f, 10f, 10f, 10f, 10f, 10f, 10f, 10f, 10f, 10f, 10f, 10f, 10f, 10f, 10f, 10f, 10f, 10f, 10f, 10f, 10f, 10f, 10f, 10f, 10f, 10f, 10f, 10f, 10f};

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
    final String intestazionePdf = "Transaction Register Summary";
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

    Font f0_bold, f1_bold, f2_bold, f1_normal, f2_normal, f3_normal, f3_normal2, f3_bold, f4_bold;

    //float tot_equiv = 0, tot_comm = 0;

    /**
     ** Constructor
     */
    public C_TransactionRegisterSummary() {

        this.f0_bold = getFont(HELVETICA, WINANSI, 14.04f, BOLD);
        this.f1_bold = getFont(HELVETICA, WINANSI, 9.96f, BOLD);
        this.f2_bold = getFont(HELVETICA, WINANSI, 8f, BOLD);
        this.f3_bold = getFont(HELVETICA, WINANSI, 3.96f, BOLDITALIC);
        this.f4_bold = getFont(HELVETICA, WINANSI, 4.5f, BOLD);
        this.f1_normal = getFont(HELVETICA, WINANSI, 9.96f, NORMAL);
        this.f2_normal = getFont(HELVETICA, WINANSI, 8f, NORMAL);
        this.f3_normal = getFont(HELVETICA, WINANSI, 3.96f, NORMAL);
        this.f3_normal2 = getFont(HELVETICA, WINANSI, 5.96f, NORMAL);

    }

    /**
     *
     * @param path
     * @param cmfb
     * @param colonne
     * @param pagestart
     * @return
     */
    public String receipt(String path, C_TransactionRegisterSummary_value cmfb, ArrayList<String> colonne, int pagestart) {

        try {

            File pdffile = new File(path + generaId(50) + "C_TransactionRegisterSummary.pdf");
            try (OutputStream ou = new FileOutputStream(pdffile)) {
                Document document = new Document(A4.rotate(), 20, 20, 20, 20);
                PdfWriter wr = getInstance(document, ou);
                document.open();
                if (true) {
                    //                 Image image1 = Image.getInstance((("logocl.png")));
//            image1.scalePercent(60f);
//            document.add(image1);
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
                ArrayList<C_TransactionRegisterSummary_value> dati = cmfb.getDati();
                PdfPCell cell;
                Phrase phraset;
                PdfPCell cellt;
                PdfPTable table3;
                PdfPTable tablepreint = new PdfPTable(31);
                tablepreint.setWidths(columnWidths2);
                tablepreint.setWidthPercentage(100);
                phraset = new Phrase();
                phraset.add(new Chunk("Purchase", f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setColspan(3);
                cellt.setHorizontalAlignment(ALIGN_CENTER);
                cellt.setBorder(LEFT | BOTTOM | TOP);
                cellt.setBackgroundColor(new BaseColor(242, 242, 242));
                tablepreint.addCell(cellt);
                phraset = new Phrase();
                phraset.add(new Chunk("Sales", f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setColspan(3);
                cellt.setHorizontalAlignment(ALIGN_CENTER);
                cellt.setBorder(LEFT | BOTTOM | TOP);
                cellt.setBackgroundColor(new BaseColor(242, 242, 242));
                tablepreint.addCell(cellt);
                phraset = new Phrase();
                phraset.add(new Chunk("Cash Adance", f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setColspan(3);
                cellt.setHorizontalAlignment(ALIGN_CENTER);
                cellt.setBorder(LEFT | BOTTOM | TOP);
                cellt.setBackgroundColor(new BaseColor(242, 242, 242));
                tablepreint.addCell(cellt);
                phraset = new Phrase();
                phraset.add(new Chunk("Western Union ", f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setColspan(4);
                cellt.setHorizontalAlignment(ALIGN_CENTER);
                cellt.setBorder(LEFT | BOTTOM | TOP);
                cellt.setBackgroundColor(new BaseColor(242, 242, 242));
                tablepreint.addCell(cellt);
                phraset = new Phrase();
                phraset.add(new Chunk("Stock ", f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setColspan(4);
                cellt.setHorizontalAlignment(ALIGN_CENTER);
                cellt.setBorder(LEFT | BOTTOM | TOP);
                cellt.setBackgroundColor(new BaseColor(242, 242, 242));
                tablepreint.addCell(cellt);
                phraset = new Phrase();
                phraset.add(new Chunk("VAT Refund", f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setColspan(2);
                cellt.setHorizontalAlignment(ALIGN_CENTER);
                cellt.setBorder(BOTTOM | TOP | LEFT | RIGHT);
                cellt.setBackgroundColor(new BaseColor(242, 242, 242));
                tablepreint.addCell(cellt);
                phraset = new Phrase();
                phraset.add(new Chunk("Other No Stock ", f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setColspan(4);
                cellt.setHorizontalAlignment(ALIGN_CENTER);
                cellt.setBorder(LEFT | BOTTOM | TOP);
                cellt.setBackgroundColor(new BaseColor(242, 242, 242));
                tablepreint.addCell(cellt);
                phraset = new Phrase();
                phraset.add(new Chunk("Tickets ", f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setColspan(4);
                cellt.setHorizontalAlignment(ALIGN_CENTER);
                cellt.setBorder(LEFT | BOTTOM | TOP);
                cellt.setBackgroundColor(new BaseColor(242, 242, 242));
                tablepreint.addCell(cellt);
                phraset = new Phrase();
                phraset.add(new Chunk("Insurance ", f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setColspan(4);
                cellt.setHorizontalAlignment(ALIGN_CENTER);
                cellt.setBorder(LEFT | BOTTOM | TOP);
                cellt.setBackgroundColor(new BaseColor(242, 242, 242));
                tablepreint.addCell(cellt);
                phraset = new Phrase();
                phraset.add(new Chunk("", f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setColspan(9);
                cellt.setHorizontalAlignment(ALIGN_CENTER);
                cellt.setBorder(LEFT | BOTTOM | TOP | RIGHT);
                cellt.setBackgroundColor(new BaseColor(242, 242, 242));
                tablepreint.addCell(cellt);
                phraset = new Phrase();
                phraset.add(new Chunk("Send", f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setColspan(2);
                cellt.setHorizontalAlignment(ALIGN_CENTER);
                cellt.setBorder(LEFT | BOTTOM | TOP | RIGHT);
                cellt.setBackgroundColor(new BaseColor(242, 242, 242));
                tablepreint.addCell(cellt);
                phraset = new Phrase();
                phraset.add(new Chunk("Receive", f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setColspan(2);
                cellt.setHorizontalAlignment(ALIGN_CENTER);
                cellt.setBorder(RIGHT | BOTTOM | TOP);
                cellt.setBackgroundColor(new BaseColor(242, 242, 242));
                tablepreint.addCell(cellt);
                phraset = new Phrase();
                phraset.add(new Chunk("Buy", f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setColspan(2);
                cellt.setHorizontalAlignment(ALIGN_CENTER);
                cellt.setBorder(RIGHT | BOTTOM | TOP);
                cellt.setBackgroundColor(new BaseColor(242, 242, 242));
                tablepreint.addCell(cellt);
                phraset = new Phrase();
                phraset.add(new Chunk("Sell", f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setColspan(2);
                cellt.setHorizontalAlignment(ALIGN_CENTER);
                cellt.setBorder(RIGHT | BOTTOM | TOP);
                cellt.setBackgroundColor(new BaseColor(242, 242, 242));
                tablepreint.addCell(cellt);
                phraset = new Phrase();
                phraset.add(new Chunk("", f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_CENTER);
                cellt.setBackgroundColor(new BaseColor(242, 242, 242));
                tablepreint.addCell(cellt);
                phraset = new Phrase();
                phraset.add(new Chunk("", f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_CENTER);
                cellt.setBackgroundColor(new BaseColor(242, 242, 242));
                tablepreint.addCell(cellt);
                phraset = new Phrase();
                phraset.add(new Chunk("In", f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setColspan(2);
                cellt.setHorizontalAlignment(ALIGN_CENTER);
                cellt.setBorder(RIGHT | BOTTOM | TOP);
                cellt.setBackgroundColor(new BaseColor(242, 242, 242));
                tablepreint.addCell(cellt);
                phraset = new Phrase();
                phraset.add(new Chunk("Out", f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setColspan(2);
                cellt.setHorizontalAlignment(ALIGN_CENTER);
                cellt.setBorder(RIGHT | BOTTOM | TOP);
                cellt.setBackgroundColor(new BaseColor(242, 242, 242));
                tablepreint.addCell(cellt);
                phraset = new Phrase();
                phraset.add(new Chunk("In", f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setColspan(2);
                cellt.setHorizontalAlignment(ALIGN_CENTER);
                cellt.setBorder(RIGHT | BOTTOM | TOP);
                cellt.setBackgroundColor(new BaseColor(242, 242, 242));
                tablepreint.addCell(cellt);
                phraset = new Phrase();
                phraset.add(new Chunk("Out", f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setColspan(2);
                cellt.setHorizontalAlignment(ALIGN_CENTER);
                cellt.setBorder(RIGHT | BOTTOM | TOP);
                cellt.setBackgroundColor(new BaseColor(242, 242, 242));
                tablepreint.addCell(cellt);
                phraset = new Phrase();
                phraset.add(new Chunk("In", f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setColspan(2);
                cellt.setHorizontalAlignment(ALIGN_CENTER);
                cellt.setBorder(RIGHT | BOTTOM | TOP);
                cellt.setBackgroundColor(new BaseColor(242, 242, 242));
                tablepreint.addCell(cellt);
                phraset = new Phrase();
                phraset.add(new Chunk("Out", f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setColspan(2);
                cellt.setHorizontalAlignment(ALIGN_CENTER);
                cellt.setBorder(RIGHT | BOTTOM | TOP);
                cellt.setBackgroundColor(new BaseColor(242, 242, 242));
                tablepreint.addCell(cellt);
                //terza riga colonna intestazione
                PdfPTable table2 = new PdfPTable(colonne.size());
                table2.setWidths(columnWidths2);
                table2.setWidthPercentage(100);
                PdfPCell[] list = new PdfPCell[colonne.size()];
                //mi scandisco le colonne
                for (int c = 0; c < colonne.size(); c++) {
                    Phrase phraset1 = new Phrase();
                    phraset1.add(new Chunk(colonne.get(c), f4_bold));
                    PdfPCell cellt1 = new PdfPCell(phraset1);
                    cellt1.setHorizontalAlignment(ALIGN_CENTER);
                    cellt1.setBorder(LEFT | RIGHT);
                    //cellt1.setFixedHeight(20f);
                    cellt1.setBackgroundColor(LIGHT_GRAY);
                    cellt1.setBorderWidth(0.7f);
                    list[c] = cellt1;
                }
                table3 = new PdfPTable(colonne.size());
                table3.setWidths(columnWidths2);
                table3.setWidthPercentage(100);
                for (PdfPCell list1 : list) {
                    PdfPCell temp = (PdfPCell) (list1);
                    tablepreint.addCell(temp);
//                    table3.addCell(temp);
                }
                document.add(tablepreint);
//            float qta = 0, equiv = 0, comm = 0;
                //if (ft) {
                if (true) {
                    LineSeparator sep = new LineSeparator();
                    sep.setOffset(-2);
                    sep.setLineWidth((float) 0.5);
                    PdfPTable table4 = new PdfPTable(2);
                    table4.setWidths(columnWidths0);
                    table4.setWidthPercentage(100);
                    phraset = new Phrase();
                    phraset.add(new Chunk("", f3_bold));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_LEFT);
                    cellt.setBorder(BOTTOM);
                    table4.addCell(cellt);
                    phraset = new Phrase();
                    phraset.add(new Chunk("", f4_bold));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_CENTER);
                    cellt.setBorder(BOTTOM);
                    table4.addCell(cellt);
                    document.add(table4);
                }
                float availableSpace;
                double purchasenum = 0.0;
                double purchasetot = 0.0;
                double purchasecomm = 0.0;
                double salesnum = 0.0;
                double salestot = 0.0;
                double salescomm = 0.0;
                double cashadvnum = 0.0;
                double cashadvtot = 0.0;
                double cashadvcomm = 0.0;
                double westernunionsensnum = 0.0;
                double westernunionsensamount = 0.0;
                double westernunionreceivenum = 0.0;
                double westernunionreceiveamount = 0.0;
                double stockbuynum = 0.0;
                double stockbuyamount = 0.0;
                double stocksellnum = 0.0;
                double stocksellamount = 0.0;
                double vatrefundnum = 0.0;
                double vatrefundamount = 0.0;
                double othernostockinnum = 0.0;
                double othernostockinamount = 0.0;
                double othernostockoutnum = 0.0;
                double othernostockoutamount = 0.0;
                double ticketsinnum = 0.0;
                double ticketsinamount = 0.0;
                double ticketsoutnum = 0.0;
                double ticketsoutamount = 0.0;
                double insuranceinnum = 0.0;
                double insuranceinamount = 0.0;
                double insuranceoutnum = 0.0;
                double insuranceoutamount = 0.0;
                for (int j = 0; j < dati.size(); j++) {
                    availableSpace = wr.getVerticalPosition(true) - document.bottomMargin();
                    C_TransactionRegisterSummary_value actual = dati.get(j);
                    PdfPTable table4bis = new PdfPTable(10);
                    table4bis.setWidths(columnWidths0intestaz);
                    table4bis.setWidthPercentage(100);
                    phraset = new Phrase();
                    phraset.add(new Chunk(actual.getId_filiale() + " " + actual.getDe_filiale(), f4_bold));
                    cellt = new PdfPCell(phraset);
                    cellt.setColspan(10);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setBorder(BOTTOM);
                    table4bis.addCell(cellt);
                    document.add(vuoto);
                    //document.add(table4bis);
//                document.add(tablepreint);
// document.add(table3);
//                LineSeparator sep = new LineSeparator();
//                sep.setOffset(-2);
//                sep.setLineWidth((float) 0.5);
PdfPTable table4 = new PdfPTable(31);
table4.setWidths(columnWidths2);
table4.setWidthPercentage(100);
phraset = new Phrase();
phraset.add(new Chunk(actual.getId_filiale(), f3_normal2));
cellt = new PdfPCell(phraset);
cellt.setHorizontalAlignment(ALIGN_RIGHT);
cellt.setBorder(BOTTOM);
table4.addCell(cellt);
phraset = new Phrase();
phraset.add(new Chunk(actual.getDe_filiale(), f3_normal2));
cellt = new PdfPCell(phraset);
cellt.setColspan(30);
cellt.setHorizontalAlignment(ALIGN_LEFT);
cellt.setBorder(BOTTOM);
table4.addCell(cellt);
phraset = new Phrase();
phraset.add(new Chunk(formatMysqltoDisplay(actual.getPurchnum()), f3_normal));
cellt = new PdfPCell(phraset);
cellt.setHorizontalAlignment(ALIGN_RIGHT);
cellt.setBorder(BOTTOM);
table4.addCell(cellt);
phraset = new Phrase();
phraset.add(new Chunk(formatMysqltoDisplay(actual.getPurchtot()), f3_normal));
cellt = new PdfPCell(phraset);
cellt.setHorizontalAlignment(ALIGN_RIGHT);
cellt.setBorder(BOTTOM);
table4.addCell(cellt);
phraset = new Phrase();
phraset.add(new Chunk(formatMysqltoDisplay(actual.getPurchcomm()), f3_normal));
cellt = new PdfPCell(phraset);
cellt.setHorizontalAlignment(ALIGN_RIGHT);
cellt.setBorder(BOTTOM);
table4.addCell(cellt);
phraset = new Phrase();
phraset.add(new Chunk(formatMysqltoDisplay(actual.getSalesnum()), f3_normal));
cellt = new PdfPCell(phraset);
cellt.setHorizontalAlignment(ALIGN_RIGHT);
cellt.setBorder(BOTTOM);
table4.addCell(cellt);
phraset = new Phrase();
phraset.add(new Chunk(formatMysqltoDisplay(actual.getSalestotal()), f3_normal));
cellt = new PdfPCell(phraset);
cellt.setHorizontalAlignment(ALIGN_RIGHT);
cellt.setBorder(BOTTOM);
table4.addCell(cellt);
phraset = new Phrase();
phraset.add(new Chunk(formatMysqltoDisplay(actual.getSalescomm()), f3_normal));
cellt = new PdfPCell(phraset);
cellt.setHorizontalAlignment(ALIGN_RIGHT);
cellt.setBorder(BOTTOM);
table4.addCell(cellt);
phraset = new Phrase();
phraset.add(new Chunk(formatMysqltoDisplay(actual.getCashnum()), f3_normal));
cellt = new PdfPCell(phraset);
cellt.setHorizontalAlignment(ALIGN_RIGHT);
cellt.setBorder(BOTTOM);
table4.addCell(cellt);
phraset = new Phrase();
phraset.add(new Chunk(formatMysqltoDisplay(actual.getCashtot()), f3_normal));
cellt = new PdfPCell(phraset);
cellt.setHorizontalAlignment(ALIGN_RIGHT);
cellt.setBorder(BOTTOM);
table4.addCell(cellt);
phraset = new Phrase();
phraset.add(new Chunk(formatMysqltoDisplay(actual.getCashcomm()), f3_normal));
cellt = new PdfPCell(phraset);
cellt.setHorizontalAlignment(ALIGN_RIGHT);
cellt.setBorder(BOTTOM);
table4.addCell(cellt);
phraset = new Phrase();
phraset.add(new Chunk(formatMysqltoDisplay(actual.getWusnum()), f3_normal));
cellt = new PdfPCell(phraset);
cellt.setHorizontalAlignment(ALIGN_RIGHT);
cellt.setBorder(BOTTOM);
table4.addCell(cellt);
phraset = new Phrase();
phraset.add(new Chunk(formatMysqltoDisplay(actual.getWustot()), f3_normal));
cellt = new PdfPCell(phraset);
cellt.setHorizontalAlignment(ALIGN_RIGHT);
cellt.setBorder(BOTTOM);
table4.addCell(cellt);
phraset = new Phrase();
phraset.add(new Chunk(formatMysqltoDisplay(actual.getWurnum()), f3_normal));
cellt = new PdfPCell(phraset);
cellt.setHorizontalAlignment(ALIGN_RIGHT);
cellt.setBorder(BOTTOM);
table4.addCell(cellt);
phraset = new Phrase();
phraset.add(new Chunk(formatMysqltoDisplay(actual.getWurtot()), f3_normal));
cellt = new PdfPCell(phraset);
cellt.setHorizontalAlignment(ALIGN_RIGHT);
cellt.setBorder(BOTTOM);
table4.addCell(cellt);
phraset = new Phrase();
phraset.add(new Chunk(formatMysqltoDisplay(actual.getStockbuynum()), f3_normal));
cellt = new PdfPCell(phraset);
cellt.setHorizontalAlignment(ALIGN_RIGHT);
cellt.setBorder(BOTTOM);
table4.addCell(cellt);
phraset = new Phrase();
phraset.add(new Chunk(formatMysqltoDisplay(actual.getStockbuytot()), f3_normal));
cellt = new PdfPCell(phraset);
cellt.setHorizontalAlignment(ALIGN_RIGHT);
cellt.setBorder(BOTTOM);
table4.addCell(cellt);
phraset = new Phrase();
phraset.add(new Chunk(formatMysqltoDisplay(actual.getStocksellnum()), f3_normal));
cellt = new PdfPCell(phraset);
cellt.setHorizontalAlignment(ALIGN_RIGHT);
cellt.setBorder(BOTTOM);
table4.addCell(cellt);
phraset = new Phrase();
phraset.add(new Chunk(formatMysqltoDisplay(actual.getStockselltot()), f3_normal));
cellt = new PdfPCell(phraset);
cellt.setHorizontalAlignment(ALIGN_RIGHT);
cellt.setBorder(BOTTOM);
table4.addCell(cellt);
phraset = new Phrase();
phraset.add(new Chunk(formatMysqltoDisplay(actual.getVatrefnum()), f3_normal));
cellt = new PdfPCell(phraset);
cellt.setHorizontalAlignment(ALIGN_RIGHT);
cellt.setBorder(BOTTOM);
table4.addCell(cellt);
phraset = new Phrase();
phraset.add(new Chunk(formatMysqltoDisplay(actual.getVatrefundtot()), f3_normal));
cellt = new PdfPCell(phraset);
cellt.setHorizontalAlignment(ALIGN_RIGHT);
cellt.setBorder(BOTTOM);
table4.addCell(cellt);
phraset = new Phrase();
phraset.add(new Chunk(formatMysqltoDisplay(actual.getOnsinum()), f3_normal));
cellt = new PdfPCell(phraset);
cellt.setHorizontalAlignment(ALIGN_RIGHT);
cellt.setBorder(BOTTOM);
table4.addCell(cellt);
phraset = new Phrase();
phraset.add(new Chunk(formatMysqltoDisplay(actual.getOnsitot()), f3_normal));
cellt = new PdfPCell(phraset);
cellt.setHorizontalAlignment(ALIGN_RIGHT);
cellt.setBorder(BOTTOM);
table4.addCell(cellt);
phraset = new Phrase();
phraset.add(new Chunk(formatMysqltoDisplay(actual.getOnsonum()), f3_normal));
cellt = new PdfPCell(phraset);
cellt.setHorizontalAlignment(ALIGN_RIGHT);
cellt.setBorder(BOTTOM);
table4.addCell(cellt);
phraset = new Phrase();
phraset.add(new Chunk(formatMysqltoDisplay(actual.getOnsotot()), f3_normal));
cellt = new PdfPCell(phraset);
cellt.setHorizontalAlignment(ALIGN_RIGHT);
cellt.setBorder(BOTTOM);
table4.addCell(cellt);
phraset = new Phrase();
phraset.add(new Chunk(formatMysqltoDisplay(actual.getTikinnum()), f3_normal));
cellt = new PdfPCell(phraset);
cellt.setHorizontalAlignment(ALIGN_RIGHT);
cellt.setBorder(BOTTOM);
table4.addCell(cellt);
phraset = new Phrase();
phraset.add(new Chunk(formatMysqltoDisplay(actual.getTikintot()), f3_normal));
cellt = new PdfPCell(phraset);
cellt.setHorizontalAlignment(ALIGN_RIGHT);
cellt.setBorder(BOTTOM);
table4.addCell(cellt);
phraset = new Phrase();
phraset.add(new Chunk(formatMysqltoDisplay(actual.getTikounum()), f3_normal));
cellt = new PdfPCell(phraset);
cellt.setHorizontalAlignment(ALIGN_RIGHT);
cellt.setBorder(BOTTOM);
table4.addCell(cellt);
phraset = new Phrase();
phraset.add(new Chunk(formatMysqltoDisplay(actual.getTikoutot()), f3_normal));
cellt = new PdfPCell(phraset);
cellt.setHorizontalAlignment(ALIGN_RIGHT);
cellt.setBorder(BOTTOM);
table4.addCell(cellt);
phraset = new Phrase();
phraset.add(new Chunk(formatMysqltoDisplay(actual.getInsinnum()), f3_normal));
cellt = new PdfPCell(phraset);
cellt.setHorizontalAlignment(ALIGN_RIGHT);
cellt.setBorder(BOTTOM);
table4.addCell(cellt);
phraset = new Phrase();
phraset.add(new Chunk(formatMysqltoDisplay(actual.getInsintot()), f3_normal));
cellt = new PdfPCell(phraset);
cellt.setHorizontalAlignment(ALIGN_RIGHT);
cellt.setBorder(BOTTOM);
table4.addCell(cellt);
phraset = new Phrase();
phraset.add(new Chunk(formatMysqltoDisplay(actual.getInsounum()), f3_normal));
cellt = new PdfPCell(phraset);
cellt.setHorizontalAlignment(ALIGN_RIGHT);
cellt.setBorder(BOTTOM);
table4.addCell(cellt);
phraset = new Phrase();
phraset.add(new Chunk(formatMysqltoDisplay(actual.getInsoutot()), f3_normal));
cellt = new PdfPCell(phraset);
cellt.setHorizontalAlignment(ALIGN_RIGHT);
cellt.setBorder(BOTTOM);
table4.addCell(cellt);
document.add(table4);
purchasenum += fd(actual.getPurchnum());
purchasetot += fd(actual.getPurchtot());
purchasecomm += fd(actual.getPurchcomm());
salesnum += fd(actual.getSalesnum());
salestot += fd(actual.getSalestotal());
salescomm += fd(actual.getSalescomm());
cashadvnum += fd(actual.getCashnum());
cashadvtot += fd(actual.getCashtot());
cashadvcomm += fd(actual.getCashcomm());
westernunionsensnum += fd(actual.getWusnum());
westernunionsensamount += fd(actual.getWustot());
westernunionreceivenum += fd(actual.getWurnum());
westernunionreceiveamount += fd(actual.getWurtot());
stockbuynum += fd(actual.getStockbuynum());
stockbuyamount += fd(actual.getStockbuytot());
stocksellnum += fd(actual.getStocksellnum());
stocksellamount += fd(actual.getStockselltot());
vatrefundnum += fd(actual.getVatrefnum());
vatrefundamount += fd(actual.getVatrefundtot());
othernostockinnum += fd(actual.getOnsinum());
othernostockinamount += fd(actual.getOnsitot());
othernostockoutnum += fd(actual.getOnsonum());
othernostockoutamount += fd(actual.getOnsotot());
ticketsinnum += fd(actual.getTikinnum());
ticketsinamount += fd(actual.getTikintot());
ticketsoutnum += fd(actual.getTikounum());
ticketsoutamount += fd(actual.getTikoutot());
insuranceinnum += fd(actual.getInsinnum());
insuranceinamount += fd(actual.getInsintot());
insuranceoutnum += fd(actual.getInsounum());
insuranceoutamount += fd(actual.getInsoutot());
                    //  if ((availableSpace < 80) || j == dati.size() - 1) {
                    if (availableSpace < 80) {
                        PdfPTable table = new PdfPTable(3);
                        table.setWidths(new int[]{24, 24, 2});
                        table.getDefaultCell().setFixedHeight(10);
                        table.getDefaultCell().setBorder(TOP);
                        cell = new PdfPCell();
                        cell.setBorder(0);
                        cell.setBorderWidthTop(1);
                        cell.setHorizontalAlignment(ALIGN_LEFT);
                        cell.setPhrase(new Phrase("", f3_normal));
                        table.addCell(cell);
                        cell = new PdfPCell();
                        cell.setBorder(0);
                        cell.setBorderWidthTop(1);
                        cell.setHorizontalAlignment(ALIGN_RIGHT);
                        cell.setPhrase(new Phrase(format("Page %d", pagestart + 1), f3_normal));
                        table.addCell(cell);
                        cell = new PdfPCell();
                        cell.setBorder(0);
                        cell.setBorderWidthTop(1);
                        table.addCell(cell);
                        table.setTotalWidth(document.getPageSize().getWidth()
                                - document.leftMargin() - document.rightMargin());
                        table.writeSelectedRows(0, -1, document.leftMargin(),
                                document.bottomMargin() - 1, wr.getDirectContent());
                        //  document.add(table);
                        ///////////////////////////////////////////////////
                        document.newPage();
                        document.add(tablepreint);
                        pagestart++;
                        //////////////////////////////////////////
                    }
                }
                availableSpace = wr.getVerticalPosition(true) - document.bottomMargin();
                if (availableSpace < 80) {
                    document.newPage();
                    pagestart++;
                }
                //totali
                PdfPTable table4 = new PdfPTable(31);
                table4.setWidths(columnWidths2);
                table4.setWidthPercentage(100);
                phraset = new Phrase();
                phraset.add(new Chunk("Total", f3_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);
                phraset = new Phrase();
                phraset.add(new Chunk("", f3_normal2));
                cellt = new PdfPCell(phraset);
                cellt.setColspan(30);
                cellt.setHorizontalAlignment(ALIGN_LEFT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);
                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(purchasenum, 0)), f3_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);
                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(purchasetot, 2)), f3_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);
                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(purchasecomm, 2)), f3_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);
                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(salesnum, 0)), f3_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);
                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(salestot, 2)), f3_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);
                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(salescomm, 2)), f3_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);
                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(cashadvnum, 0)), f3_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);
                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(cashadvtot, 2)), f3_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);
                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(cashadvcomm, 2)), f3_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);
                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(westernunionsensnum, 0)), f3_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);
                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(westernunionsensamount, 2)), f3_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);
                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(westernunionreceivenum, 0)), f3_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);
                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(westernunionreceiveamount, 2)), f3_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);
                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(stockbuynum, 0)), f3_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);
                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(stockbuyamount, 2)), f3_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);
                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(stocksellnum, 0)), f3_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);
                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(stocksellamount, 2)), f3_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);
                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(vatrefundnum, 0)), f3_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);
                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(vatrefundamount, 2)), f3_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);
                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(othernostockinnum, 0)), f3_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);
                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(othernostockinamount, 2)), f3_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);
                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(othernostockoutnum, 0)), f3_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);
                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(othernostockoutamount, 2)), f3_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);
                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(ticketsinnum, 0)), f3_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);
                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(ticketsinamount, 2)), f3_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);
                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(ticketsoutnum, 0)), f3_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);
                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(ticketsoutamount, 2)), f3_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);
                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(insuranceinnum, 0)), f3_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);
                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(insuranceinamount, 2)), f3_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);
                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(insuranceoutnum, 0)), f3_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);
                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(insuranceoutamount, 2)), f3_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);
                document.add(table4);
                PdfPTable table = new PdfPTable(3);
                table.setWidths(new int[]{24, 24, 2});
                table.getDefaultCell().setFixedHeight(10);
                table.getDefaultCell().setBorder(TOP);
                cell = new PdfPCell();
                cell.setBorder(0);
                cell.setBorderWidthTop(1);
                cell.setHorizontalAlignment(ALIGN_LEFT);
                cell.setPhrase(new Phrase("", f3_normal));
                table.addCell(cell);
                cell = new PdfPCell();
                cell.setBorder(0);
                cell.setBorderWidthTop(1);
                cell.setHorizontalAlignment(ALIGN_RIGHT);
                cell.setPhrase(new Phrase(format("Page %d", pagestart + 1), f3_normal));
                table.addCell(cell);
                cell = new PdfPCell();
                cell.setBorder(0);
                cell.setBorderWidthTop(1);
                table.addCell(cell);
                table.setTotalWidth(document.getPageSize().getWidth()
                        - document.leftMargin() - document.rightMargin());
                table.writeSelectedRows(0, -1, document.leftMargin(),
                        document.bottomMargin() - 1, wr.getDirectContent());
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
     * @param path
     * @param cmfb
     * @param colonne
     * @param pagestart
     * @return
     */
    public String receiptexcel(String path, C_TransactionRegisterSummary_value cmfb, ArrayList<String> colonne, int pagestart) {

        try {

            File pdf = new File(path + generaId(50) + "C_TransactionRegisterSummary.xls");
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet("C_TransactionRegisterSummary");
            //CREAZIONE FONT
            HSSFFont font = workbook.createFont();
            font.setFontName(FONT_ARIAL);
            font.setFontHeightInPoints((short) 12);
            font.setBold(true);

            HSSFCellStyle style1 = (HSSFCellStyle) workbook.createCellStyle();
            style1.setFont(font);
            style1.setAlignment(CENTER);
            style1.setBorderRight(MEDIUM);
            style1.setBorderLeft(MEDIUM);

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
            style3.setAlignment(CENTER);
            style3.setBorderRight(MEDIUM);
            style3.setBorderLeft(MEDIUM);
            style3.setBorderTop(MEDIUM);
            style3.setBorderBottom(MEDIUM);

            HSSFFont font4 = workbook.createFont();
            font4.setFontName(FONT_ARIAL);
            font4.setFontHeightInPoints((short) 10);

            HSSFCellStyle style4 = (HSSFCellStyle) workbook.createCellStyle();
            style4.setAlignment(HorizontalAlignment.RIGHT);
            style4.setBorderTop(THIN);
            style4.setBorderBottom(THIN);

            if (true) {

//                 Image image1 = Image.getInstance((("logocl.png")));
//            image1.scalePercent(60f);
//            document.add(image1);
                Row rowP = sheet.createRow((short) 1);

                Cell cl = rowP.createCell(1);
                cl.setCellStyle(style1);
                cl.setCellValue(intestazionePdf + " from " + cmfb.getDataDa() + " to  " + cmfb.getDataA());

            }

            //  document.add(table2);
            // document.add(sep);
            ArrayList<C_TransactionRegisterSummary_value> dati = cmfb.getDati();

            boolean ft = true;

//            float qta = 0, equiv = 0, comm = 0;
            //if (ft) {
            if (true) {

                Row rowP = sheet.createRow((short) 3);

                Cell cl = rowP.createCell(2);
                cl.setCellStyle(style1);
                cl.setCellValue("Purchase");

                Cell c1l = rowP.createCell(5);
                c1l.setCellStyle(style1);
                c1l.setCellValue("Sales");

                Cell c1l1 = rowP.createCell(8);
                c1l1.setCellStyle(style1);
                c1l1.setCellValue("Cash Adance");

                Cell c1l1w = rowP.createCell(11);
                c1l1w.setCellStyle(style1);
                c1l1w.setCellValue("Western Union");

                Cell c1l15 = rowP.createCell(15);
                c1l15.setCellStyle(style1);
                c1l15.setCellValue("Stock");

                Cell c1l18 = rowP.createCell(19);
                c1l18.setCellStyle(style1);
                c1l18.setCellValue("VAT Refund");

                Cell c1l21 = rowP.createCell(21);
                c1l21.setCellStyle(style1);
                c1l21.setCellValue("Other No Stock");

                Cell c1l24 = rowP.createCell(25);
                c1l24.setCellStyle(style1);
                c1l24.setCellValue("Tickets");

                Cell c1l28 = rowP.createCell(29);
                c1l28.setCellStyle(style1);
                c1l28.setCellValue("Insurance");

                Row rowP2 = sheet.createRow((short) 4);

                Cell c1l110 = rowP2.createCell(11);
                c1l110.setCellStyle(style1);
                c1l110.setCellValue("Send");

                Cell c1l112 = rowP2.createCell(13);
                c1l112.setCellStyle(style1);
                c1l112.setCellValue("Receive");

                Cell c1l114 = rowP2.createCell(15);
                c1l114.setCellStyle(style1);
                c1l114.setCellValue("Buy");

                Cell c1l116 = rowP2.createCell(17);
                c1l116.setCellStyle(style1);
                c1l116.setCellValue("Sell");

                Cell c1l120 = rowP2.createCell(21);
                c1l120.setCellStyle(style1);
                c1l120.setCellValue("In");

                Cell c1l122 = rowP2.createCell(23);
                c1l122.setCellStyle(style1);
                c1l122.setCellValue("Out");

                Cell c1l124 = rowP2.createCell(25);
                c1l124.setCellStyle(style1);
                c1l124.setCellValue("In");

                Cell c1l126 = rowP2.createCell(27);
                c1l126.setCellStyle(style1);
                c1l126.setCellValue("Out");

                Cell c1l128 = rowP2.createCell(29);
                c1l128.setCellStyle(style1);
                c1l128.setCellValue("In");

                Cell c1l130 = rowP2.createCell(31);
                c1l130.setCellStyle(style1);
                c1l130.setCellValue("Out");

                //terza riga colonna intestazione 
                Row row66 = sheet.createRow((short) 5);

                //mi scandisco le colonne
                for (int j = 0; j < colonne.size(); j++) {
                    Cell cl7 = row66.createCell(j + 2);
                    cl7.setCellStyle(style3);
                    cl7.setCellValue(colonne.get(j));
                }
                ft = false;

                ft = false;
            }

            int cntriga = 7;

            double purchasenum = 0.0;
            double purchasetot = 0.0;
            double purchasecomm = 0.0;
            double salesnum = 0.0;
            double salestot = 0.0;
            double salescomm = 0.0;
            double cashadvnum = 0.0;
            double cashadvtot = 0.0;
            double cashadvcomm = 0.0;
            double westernunionsensnum = 0.0;
            double westernunionsensamount = 0.0;
            double westernunionreceivenum = 0.0;
            double westernunionreceiveamount = 0.0;
            double stockbuynum = 0.0;
            double stockbuyamount = 0.0;
            double stocksellnum = 0.0;
            double stocksellamount = 0.0;
            double vatrefundnum = 0.0;
            double vatrefundamount = 0.0;
            double othernostockinnum = 0.0;
            double othernostockinamount = 0.0;
            double othernostockoutnum = 0.0;
            double othernostockoutamount = 0.0;
            double ticketsinnum = 0.0;
            double ticketsinamount = 0.0;
            double ticketsoutnum = 0.0;
            double ticketsoutamount = 0.0;
            double insuranceinnum = 0.0;
            double insuranceinamount = 0.0;
            double insuranceoutnum = 0.0;
            double insuranceoutamount = 0.0;

            for (int j = 0; j < dati.size(); j++) {

                cntriga++;
                cntriga++;
                cntriga++;
                Row row6b = sheet.createRow((short) cntriga);

                C_TransactionRegisterSummary_value actual = dati.get(j);

                Cell f1bis = row6b.createCell(1);
                f1bis.setCellStyle(style3);
                f1bis.setCellValue(actual.getId_filiale() + "  - " + actual.getDe_filiale());

//                cntriga++;
//                cntriga++;
//                Row row62 = sheet.createRow((short) cntriga);
//
//                Cell f1bis1 = row62.createCell(1);
//                f1bis1.setCellStyle(style4);
//                f1bis1.setCellValue(actual.getId_filiale());
//
//                Cell f2 = row62.createCell(2);
//                f2.setCellStyle(style4);
//                f2.setCellValue(actual.getDe_filiale());
                cntriga++;
                cntriga++;
                Row row6 = sheet.createRow((short) cntriga);

                Cell f1bis2 = row6.createCell(2);
                f1bis2.setCellStyle(style4);
                f1bis2.setCellValue(formatMysqltoDisplay(actual.getPurchnum()));

                Cell f2b = row6.createCell(3);
                f2b.setCellStyle(style4);
                f2b.setCellValue(formatMysqltoDisplay(actual.getPurchtot()));

                Cell f3 = row6.createCell(4);
                f3.setCellStyle(style4);
                f3.setCellValue(formatMysqltoDisplay(actual.getPurchcomm()));

                Cell f4 = row6.createCell(5);
                f4.setCellStyle(style4);
                f4.setCellValue(formatMysqltoDisplay(actual.getSalesnum()));

                Cell f6 = row6.createCell(6);
                f6.setCellStyle(style4);
                f6.setCellValue(formatMysqltoDisplay(actual.getSalestotal()));

                Cell f7 = row6.createCell(7);
                f7.setCellStyle(style4);
                f7.setCellValue(formatMysqltoDisplay(actual.getSalescomm()));

                Cell f7bis = row6.createCell(8);
                f7bis.setCellStyle(style4);
                f7bis.setCellValue(formatMysqltoDisplay(actual.getCashnum()));

                Cell f8 = row6.createCell(9);
                f8.setCellStyle(style4);
                f8.setCellValue(formatMysqltoDisplay(actual.getCashtot()));

                Cell f9 = row6.createCell(10);
                f9.setCellStyle(style4);
                f9.setCellValue(formatMysqltoDisplay(actual.getCashcomm()));

                Cell f10 = row6.createCell(11);
                f10.setCellStyle(style4);
                f10.setCellValue(formatMysqltoDisplay(actual.getWusnum()));

                Cell f11 = row6.createCell(12);
                f11.setCellStyle(style4);
                f11.setCellValue(formatMysqltoDisplay(actual.getWustot()));

                Cell f12 = row6.createCell(13);
                f12.setCellStyle(style4);
                f12.setCellValue(formatMysqltoDisplay(actual.getWurnum()));

                Cell f13 = row6.createCell(14);
                f13.setCellStyle(style4);
                f13.setCellValue(formatMysqltoDisplay(actual.getWurtot()));

                Cell f14 = row6.createCell(15);
                f14.setCellStyle(style4);
                f14.setCellValue(formatMysqltoDisplay(actual.getStockbuynum()));

                Cell f15 = row6.createCell(16);
                f15.setCellStyle(style4);
                f15.setCellValue(formatMysqltoDisplay(actual.getStockbuytot()));

                Cell f16 = row6.createCell(17);
                f16.setCellStyle(style4);
                f16.setCellValue(formatMysqltoDisplay(actual.getStocksellnum()));

                Cell f17 = row6.createCell(18);
                f17.setCellStyle(style4);
                f17.setCellValue(formatMysqltoDisplay(actual.getStockselltot()));

                Cell f18 = row6.createCell(19);
                f18.setCellStyle(style4);
                f18.setCellValue(formatMysqltoDisplay(actual.getVatrefnum()));

                Cell f19 = row6.createCell(20);
                f19.setCellStyle(style4);
                f19.setCellValue(formatMysqltoDisplay(actual.getVatrefundtot()));

                Cell f20 = row6.createCell(21);
                f20.setCellStyle(style4);
                f20.setCellValue(formatMysqltoDisplay(actual.getOnsinum()));

                Cell f21 = row6.createCell(22);
                f21.setCellStyle(style4);
                f21.setCellValue(formatMysqltoDisplay(actual.getOnsitot()));

                Cell f22 = row6.createCell(23);
                f22.setCellStyle(style4);
                f22.setCellValue(formatMysqltoDisplay(actual.getOnsonum()));

                Cell f23 = row6.createCell(24);
                f23.setCellStyle(style4);
                f23.setCellValue(formatMysqltoDisplay(actual.getOnsotot()));

                Cell f24 = row6.createCell(25);
                f24.setCellStyle(style4);
                f24.setCellValue(formatMysqltoDisplay(actual.getTikinnum()));

                Cell f25 = row6.createCell(26);
                f25.setCellStyle(style4);
                f25.setCellValue(formatMysqltoDisplay(actual.getTikintot()));

                Cell f26 = row6.createCell(27);
                f26.setCellStyle(style4);
                f26.setCellValue(formatMysqltoDisplay(actual.getTikounum()));

                Cell f27 = row6.createCell(28);
                f27.setCellStyle(style4);
                f27.setCellValue(formatMysqltoDisplay(actual.getTikoutot()));

                Cell f28 = row6.createCell(29);
                f28.setCellStyle(style4);
                f28.setCellValue(formatMysqltoDisplay(actual.getInsinnum()));

                Cell f29 = row6.createCell(30);
                f29.setCellStyle(style4);
                f29.setCellValue(formatMysqltoDisplay(actual.getInsintot()));

                Cell f30 = row6.createCell(31);
                f30.setCellStyle(style4);
                f30.setCellValue(formatMysqltoDisplay(actual.getInsounum()));

                Cell f31 = row6.createCell(32);
                f31.setCellStyle(style4);
                f31.setCellValue(formatMysqltoDisplay(actual.getInsoutot()));

                purchasenum += fd(actual.getPurchnum());
                purchasetot += fd(actual.getPurchtot());
                purchasecomm += fd(actual.getPurchcomm());
                salesnum += fd(actual.getSalesnum());
                salestot += fd(actual.getSalestotal());
                salescomm += fd(actual.getSalescomm());
                cashadvnum += fd(actual.getCashnum());
                cashadvtot += fd(actual.getCashtot());
                cashadvcomm += fd(actual.getCashcomm());
                westernunionsensnum += fd(actual.getWusnum());
                westernunionsensamount += fd(actual.getWustot());
                westernunionreceivenum += fd(actual.getWurnum());
                westernunionreceiveamount += fd(actual.getWurtot());
                stockbuynum += fd(actual.getStockbuynum());
                stockbuyamount += fd(actual.getStockbuytot());
                stocksellnum += fd(actual.getStocksellnum());
                stocksellamount += fd(actual.getStockselltot());
                vatrefundnum += fd(actual.getVatrefnum());
                vatrefundamount += fd(actual.getVatrefundtot());
                othernostockinnum += fd(actual.getOnsinum());
                othernostockinamount += fd(actual.getOnsitot());
                othernostockoutnum += fd(actual.getOnsonum());
                othernostockoutamount += fd(actual.getOnsotot());
                ticketsinnum += fd(actual.getTikinnum());
                ticketsinamount += fd(actual.getTikintot());
                ticketsoutnum += fd(actual.getTikounum());
                ticketsoutamount += fd(actual.getTikoutot());
                insuranceinnum += fd(actual.getInsinnum());
                insuranceinamount += fd(actual.getInsintot());
                insuranceoutnum += fd(actual.getInsounum());
                insuranceoutamount += fd(actual.getInsoutot());

            }

            cntriga++;
            cntriga++;
            Row row6b = sheet.createRow((short) cntriga);

            Cell f1bis = row6b.createCell(1);
            f1bis.setCellStyle(style3);
            f1bis.setCellValue("Total");

//                cntriga++;
//                cntriga++;
//                Row row62 = sheet.createRow((short) cntriga);
//
//                Cell f1bis1 = row62.createCell(1);
//                f1bis1.setCellStyle(style4);
//                f1bis1.setCellValue(actual.getId_filiale());
//
//                Cell f2 = row62.createCell(2);
//                f2.setCellStyle(style4);
//                f2.setCellValue(actual.getDe_filiale());
            cntriga++;
            cntriga++;
            Row row6 = sheet.createRow((short) cntriga);

            Cell f1bis2 = row6.createCell(2);
            f1bis2.setCellStyle(style3);
            f1bis2.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(purchasenum, 0)));

            Cell f2b = row6.createCell(3);
            f2b.setCellStyle(style3);
            f2b.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(purchasetot, 2)));

            Cell f3 = row6.createCell(4);
            f3.setCellStyle(style3);
            f3.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(purchasecomm, 2)));

            Cell f4 = row6.createCell(5);
            f4.setCellStyle(style3);
            f4.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(salesnum, 2)));

            Cell f6 = row6.createCell(6);
            f6.setCellStyle(style3);
            f6.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(salestot, 2)));

            Cell f7 = row6.createCell(7);
            f7.setCellStyle(style3);
            f7.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(salescomm, 2)));

            Cell f7bis = row6.createCell(8);
            f7bis.setCellStyle(style3);
            f7bis.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(cashadvnum, 2)));

            Cell f8 = row6.createCell(9);
            f8.setCellStyle(style3);
            f8.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(cashadvtot, 2)));

            Cell f9 = row6.createCell(10);
            f9.setCellStyle(style3);
            f9.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(cashadvcomm, 2)));

            Cell f10 = row6.createCell(11);
            f10.setCellStyle(style3);
            f10.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(westernunionsensnum, 2)));

            Cell f11 = row6.createCell(12);
            f11.setCellStyle(style3);
            f11.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(westernunionsensamount, 2)));

            Cell f12 = row6.createCell(13);
            f12.setCellStyle(style3);
            f12.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(westernunionreceivenum, 2)));

            Cell f13 = row6.createCell(14);
            f13.setCellStyle(style3);
            f13.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(westernunionreceiveamount, 2)));

            Cell f14 = row6.createCell(15);
            f14.setCellStyle(style3);
            f14.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(stockbuynum, 2)));

            Cell f15 = row6.createCell(16);
            f15.setCellStyle(style3);
            f15.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(stockbuyamount, 2)));

            Cell f16 = row6.createCell(17);
            f16.setCellStyle(style3);
            f16.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(stocksellnum, 2)));

            Cell f17 = row6.createCell(18);
            f17.setCellStyle(style3);
            f17.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(stocksellamount, 2)));

            Cell f18 = row6.createCell(19);
            f18.setCellStyle(style3);
            f18.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(vatrefundnum, 2)));

            Cell f19 = row6.createCell(20);
            f19.setCellStyle(style3);
            f19.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(vatrefundamount, 2)));

            Cell f20 = row6.createCell(21);
            f20.setCellStyle(style3);
            f20.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(othernostockinnum, 2)));

            Cell f21 = row6.createCell(22);
            f21.setCellStyle(style3);
            f21.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(othernostockinamount, 2)));

            Cell f22 = row6.createCell(23);
            f22.setCellStyle(style3);
            f22.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(othernostockoutnum, 2)));

            Cell f23 = row6.createCell(24);
            f23.setCellStyle(style3);
            f23.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(othernostockoutamount, 2)));

            Cell f24 = row6.createCell(25);
            f24.setCellStyle(style3);
            f24.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(ticketsinnum, 2)));

            Cell f25 = row6.createCell(26);
            f25.setCellStyle(style3);
            f25.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(ticketsinamount, 2)));

            Cell f26 = row6.createCell(27);
            f26.setCellStyle(style3);
            f26.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(ticketsoutnum, 2)));

            Cell f27 = row6.createCell(28);
            f27.setCellStyle(style3);
            f27.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(ticketsoutamount, 2)));

            Cell f28 = row6.createCell(29);
            f28.setCellStyle(style3);
            f28.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(insuranceinnum, 2)));

            Cell f29 = row6.createCell(30);
            f29.setCellStyle(style3);
            f29.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(insuranceinamount, 2)));

            Cell f30 = row6.createCell(31);
            f30.setCellStyle(style3);
            f30.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(insuranceoutnum, 2)));

            Cell f31 = row6.createCell(32);
            f31.setCellStyle(style3);
            f31.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(insuranceoutamount, 2)));

            CellRangeAddress cellRangeAddress = new CellRangeAddress(3, 3, 2, 4);
            sheet.addMergedRegion(cellRangeAddress);
            cellRangeAddress = new CellRangeAddress(3, 3, 5, 7);
            sheet.addMergedRegion(cellRangeAddress);
            cellRangeAddress = new CellRangeAddress(3, 3, 8, 10);
            sheet.addMergedRegion(cellRangeAddress);
            cellRangeAddress = new CellRangeAddress(3, 3, 11, 14);
            sheet.addMergedRegion(cellRangeAddress);
            cellRangeAddress = new CellRangeAddress(3, 3, 15, 18);
            sheet.addMergedRegion(cellRangeAddress);
            cellRangeAddress = new CellRangeAddress(3, 3, 19, 20);
            sheet.addMergedRegion(cellRangeAddress);
            cellRangeAddress = new CellRangeAddress(3, 3, 21, 24);
            sheet.addMergedRegion(cellRangeAddress);
            cellRangeAddress = new CellRangeAddress(3, 3, 25, 28);
            sheet.addMergedRegion(cellRangeAddress);
            cellRangeAddress = new CellRangeAddress(3, 3, 29, 32);
            sheet.addMergedRegion(cellRangeAddress);
            cellRangeAddress = new CellRangeAddress(3, 3, 33, 36);
            sheet.addMergedRegion(cellRangeAddress);

            cellRangeAddress = new CellRangeAddress(4, 4, 11, 12);
            sheet.addMergedRegion(cellRangeAddress);
            cellRangeAddress = new CellRangeAddress(4, 4, 13, 14);
            sheet.addMergedRegion(cellRangeAddress);
            cellRangeAddress = new CellRangeAddress(4, 4, 15, 16);
            sheet.addMergedRegion(cellRangeAddress);
            cellRangeAddress = new CellRangeAddress(4, 4, 17, 18);
            sheet.addMergedRegion(cellRangeAddress);
            cellRangeAddress = new CellRangeAddress(4, 4, 19, 20);
            sheet.addMergedRegion(cellRangeAddress);
            cellRangeAddress = new CellRangeAddress(4, 4, 21, 22);
            sheet.addMergedRegion(cellRangeAddress);
            cellRangeAddress = new CellRangeAddress(4, 4, 23, 24);
            sheet.addMergedRegion(cellRangeAddress);
            cellRangeAddress = new CellRangeAddress(4, 4, 25, 26);
            sheet.addMergedRegion(cellRangeAddress);
            cellRangeAddress = new CellRangeAddress(4, 4, 27, 28);
            sheet.addMergedRegion(cellRangeAddress);
            cellRangeAddress = new CellRangeAddress(4, 4, 29, 30);
            sheet.addMergedRegion(cellRangeAddress);
            cellRangeAddress = new CellRangeAddress(4, 4, 31, 32);
            sheet.addMergedRegion(cellRangeAddress);

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
