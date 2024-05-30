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
import static com.itextpdf.text.Element.ALIGN_BOTTOM;
import static com.itextpdf.text.Element.ALIGN_CENTER;
import static com.itextpdf.text.Element.ALIGN_LEFT;
import static com.itextpdf.text.Element.ALIGN_MIDDLE;
import static com.itextpdf.text.Element.ALIGN_RIGHT;
import com.itextpdf.text.Font;
import static com.itextpdf.text.Font.BOLD;
import static com.itextpdf.text.Font.BOLDITALIC;
import static com.itextpdf.text.Font.NORMAL;
import static com.itextpdf.text.FontFactory.getFont;
import static com.itextpdf.text.PageSize.A4;
import com.itextpdf.text.Phrase;
import static com.itextpdf.text.Rectangle.BOTTOM;
import static com.itextpdf.text.Rectangle.BOX;
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
import static rc.so.util.Constant.patternnormdate;
import static rc.so.util.Constant.patternsqldate;
import static rc.so.util.Engine.insertTR;
import static rc.so.util.Utility.fd;
import static rc.so.util.Utility.formatMysqltoDisplay;
import static rc.so.util.Utility.formatStringtoStringDate;
import static rc.so.util.Utility.generaId;
import static rc.so.util.Utility.roundDouble;
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
import static org.apache.poi.hssf.usermodel.HSSFFont.FONT_ARIAL;
import static org.apache.poi.ss.usermodel.BorderStyle.THIN;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import static org.apache.poi.ss.usermodel.HorizontalAlignment.LEFT;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author rcosco
 */
public class TillTransactionListBB {

    //column

    /**
     *
     */
    public static final float[] columnWidths0 = new float[]{80f, 20f};

    /**
     *
     */
    public static final float[] columnWidths1 = new float[]{60f, 30f};

    /**
     *
     */
    public static final float[] columnWidths2 = new float[]{4f, 3f, 3f,6f, 8f, 8f, 3f, 6f, 6f, 6f, 6f, 6.5f, 3f, 5f, 5f, 11f, 5f, 7f};

    /**
     *
     */
    public static final float[] columnWidths3 = new float[]{25f, 7.5f, 19f, 8f, 20f, 7f, 16f, 9f, 10f, 9f, 9f, 9f};

    /**
     *
     */
    public static final float[] columnWidths4 = new float[]{3f, 6f, 6f, 5f, 3.5f, 6f, 6f, 6f, 3.5f, 6f, 6f, 6f, 3.5f, 8f, 8f, 4f, 5f, 3.5f, 5f, 5f, 5f};

    /**
     *
     */
    public static final float[] columnWidths5 = new float[]{25f, 25f, 25f, 25f};

    /**
     *
     */
    public static final float[] columnWidths6 = new float[]{20f, 20f, 20f, 20f, 20f};

    // final String intestazionePdf = "TillTransactionList ";
    Phrase vuoto = new Phrase("\n");
    PdfPCell cellavuota = new PdfPCell(vuoto);

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
    public TillTransactionListBB() {

        cellavuota.setBorder(NO_BORDER);

        this.f0_bold = getFont(HELVETICA, WINANSI, 14.04f, BOLD);
        this.f1_bold = getFont(HELVETICA, WINANSI, 9.96f, BOLD);
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
     * @param document
     */
    public void scriviIntestazioneColonne(Document document) {

        try {
            PdfPTable table2 = new PdfPTable(18);
            table2.setWidths(columnWidths2);
            table2.setWidthPercentage(100);

            Phrase phraset2 = new Phrase();
            phraset2.add(new Chunk("Check", f5_bold));
            PdfPCell cellt2 = new PdfPCell(phraset2);
            cellt2.setHorizontalAlignment(ALIGN_LEFT);
            cellt2.setVerticalAlignment(ALIGN_MIDDLE);
            cellt2.setBorder(BOTTOM | TOP);
            cellt2.setBackgroundColor(LIGHT_GRAY);
            table2.addCell(cellt2);

            phraset2 = new Phrase();
            phraset2.add(new Chunk("Till", f5_bold));
            cellt2 = new PdfPCell(phraset2);
            cellt2.setHorizontalAlignment(ALIGN_RIGHT);
            cellt2.setVerticalAlignment(ALIGN_MIDDLE);
            cellt2.setBorder(BOTTOM | TOP);
            cellt2.setBackgroundColor(LIGHT_GRAY);
            table2.addCell(cellt2);

            phraset2 = new Phrase();
            phraset2.add(new Chunk("User", f5_bold));
            cellt2 = new PdfPCell(phraset2);
            cellt2.setHorizontalAlignment(ALIGN_RIGHT);
            cellt2.setVerticalAlignment(ALIGN_MIDDLE);
            cellt2.setBorder(BOTTOM | TOP);
            cellt2.setBackgroundColor(LIGHT_GRAY);
            table2.addCell(cellt2);
            
             phraset2 = new Phrase();
            phraset2.add(new Chunk("Branch ID", f5_bold));
            cellt2 = new PdfPCell(phraset2);
            cellt2.setHorizontalAlignment(ALIGN_RIGHT);
            cellt2.setVerticalAlignment(ALIGN_MIDDLE);
            cellt2.setBorder(BOTTOM | TOP);
            cellt2.setBackgroundColor(LIGHT_GRAY);
            table2.addCell(cellt2);

            phraset2 = new Phrase();
            phraset2.add(new Chunk("No.Tr.", f5_bold));
            cellt2 = new PdfPCell(phraset2);
            cellt2.setHorizontalAlignment(ALIGN_RIGHT);
            cellt2.setVerticalAlignment(ALIGN_MIDDLE);
            cellt2.setBorder(BOTTOM | TOP);
            cellt2.setBackgroundColor(LIGHT_GRAY);
            table2.addCell(cellt2);

            phraset2 = new Phrase();
            phraset2.add(new Chunk("Date / Time", f5_bold));
            cellt2 = new PdfPCell(phraset2);
            cellt2.setHorizontalAlignment(ALIGN_LEFT);
            cellt2.setVerticalAlignment(ALIGN_MIDDLE);
            cellt2.setBorder(BOTTOM | TOP);
            cellt2.setBackgroundColor(LIGHT_GRAY);
            table2.addCell(cellt2);

            phraset2 = new Phrase();
            phraset2.add(new Chunk("Cur", f5_bold));
            cellt2 = new PdfPCell(phraset2);
            cellt2.setHorizontalAlignment(ALIGN_LEFT);
            cellt2.setVerticalAlignment(ALIGN_MIDDLE);
            cellt2.setBorder(BOTTOM | TOP);
            cellt2.setBackgroundColor(LIGHT_GRAY);
            table2.addCell(cellt2);

            phraset2 = new Phrase();
            phraset2.add(new Chunk("Kind", f5_bold));
            cellt2 = new PdfPCell(phraset2);
            cellt2.setHorizontalAlignment(ALIGN_LEFT);
            cellt2.setVerticalAlignment(ALIGN_MIDDLE);
            cellt2.setBorder(BOTTOM | TOP);
            cellt2.setBackgroundColor(LIGHT_GRAY);
            table2.addCell(cellt2);

            phraset2 = new Phrase();
            phraset2.add(new Chunk("Amount", f5_bold));
            cellt2 = new PdfPCell(phraset2);
            cellt2.setHorizontalAlignment(ALIGN_RIGHT);
            cellt2.setVerticalAlignment(ALIGN_MIDDLE);
            cellt2.setBorder(BOTTOM | TOP);
            cellt2.setBackgroundColor(LIGHT_GRAY);
            table2.addCell(cellt2);

            phraset2 = new Phrase();
            phraset2.add(new Chunk("Rate", f5_bold));
            cellt2 = new PdfPCell(phraset2);
            cellt2.setHorizontalAlignment(ALIGN_RIGHT);
            cellt2.setVerticalAlignment(ALIGN_MIDDLE);
            cellt2.setBorder(BOTTOM | TOP);
            cellt2.setBackgroundColor(LIGHT_GRAY);
            table2.addCell(cellt2);

            phraset2 = new Phrase();
            phraset2.add(new Chunk("Total", f5_bold));
            cellt2 = new PdfPCell(phraset2);
            cellt2.setHorizontalAlignment(ALIGN_RIGHT);
            cellt2.setVerticalAlignment(ALIGN_MIDDLE);
            cellt2.setBorder(BOTTOM | TOP);
            cellt2.setBackgroundColor(LIGHT_GRAY);
            table2.addCell(cellt2);

            phraset2 = new Phrase();
            phraset2.add(new Chunk("%", f5_bold));
            cellt2 = new PdfPCell(phraset2);
            cellt2.setHorizontalAlignment(ALIGN_RIGHT);
            cellt2.setVerticalAlignment(ALIGN_MIDDLE);
            cellt2.setBorder(BOTTOM | TOP);
            cellt2.setBackgroundColor(LIGHT_GRAY);
            table2.addCell(cellt2);

            phraset2 = new Phrase();
            phraset2.add(new Chunk("Comm.Fee", f5_bold));
            cellt2 = new PdfPCell(phraset2);
            cellt2.setHorizontalAlignment(ALIGN_RIGHT);
            cellt2.setVerticalAlignment(ALIGN_MIDDLE);
            cellt2.setBorder(BOTTOM | TOP);
            cellt2.setBackgroundColor(LIGHT_GRAY);
            table2.addCell(cellt2);

            phraset2 = new Phrase();
            phraset2.add(new Chunk("Round", f5_bold));
            cellt2 = new PdfPCell(phraset2);
            cellt2.setHorizontalAlignment(ALIGN_RIGHT);
            cellt2.setVerticalAlignment(ALIGN_MIDDLE);
            cellt2.setBorder(BOTTOM | TOP);
            cellt2.setBackgroundColor(LIGHT_GRAY);
            table2.addCell(cellt2);

            phraset2 = new Phrase();
            phraset2.add(new Chunk("Pay In / Pay Out", f5_bold));
            cellt2 = new PdfPCell(phraset2);
            cellt2.setHorizontalAlignment(ALIGN_RIGHT);
            cellt2.setVerticalAlignment(ALIGN_MIDDLE);
            cellt2.setBorder(BOTTOM | TOP);
            cellt2.setBackgroundColor(LIGHT_GRAY);
            table2.addCell(cellt2);

            phraset2 = new Phrase();
            phraset2.add(new Chunk("Customer", f5_bold));
            cellt2 = new PdfPCell(phraset2);
            cellt2.setHorizontalAlignment(ALIGN_LEFT);
            cellt2.setVerticalAlignment(ALIGN_MIDDLE);
            cellt2.setBorder(BOTTOM | TOP);
            cellt2.setBackgroundColor(LIGHT_GRAY);
            table2.addCell(cellt2);

            phraset2 = new Phrase();
            phraset2.add(new Chunk("Spread", f5_bold));
            cellt2 = new PdfPCell(phraset2);
            cellt2.setHorizontalAlignment(ALIGN_RIGHT);
            cellt2.setVerticalAlignment(ALIGN_MIDDLE);
            cellt2.setBorder(BOTTOM | TOP);
            cellt2.setBackgroundColor(LIGHT_GRAY);
            table2.addCell(cellt2);

            phraset2 = new Phrase();
            phraset2.add(new Chunk("Pos / Bank Acc", f5_bold));
            cellt2 = new PdfPCell(phraset2);
            cellt2.setHorizontalAlignment(ALIGN_RIGHT);
            cellt2.setVerticalAlignment(ALIGN_MIDDLE);
            cellt2.setBorder(BOTTOM | TOP);
            cellt2.setBackgroundColor(LIGHT_GRAY);
            table2.addCell(cellt2);

            document.add(table2);

        } catch (DocumentException ex) {
            insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
        }
    }

    /**
     *
     * @param path
     * @param siq
     * @param datereport1
     * @param datereport2
     * @param intestazionePdf
     * @return
     */
    public String receipt(String path, TillTransactionListBB_value siq, String datereport1, String datereport2, String intestazionePdf) {

        //   String outputfile = "TillTransactionList.pdf";
        try {

            File pdf = new File(path + generaId(50) + "TillTransactionList.pdf");
            Document document = new Document(A4.rotate(), 20, 20, 20, 20);
            try(OutputStream ou = new FileOutputStream(pdf)){
            PdfWriter wr = getInstance(document, ou);
            document.open();

            PdfPTable table = new PdfPTable(2);
            table.setWidths(columnWidths0);
            table.setWidthPercentage(100);

            Phrase phrase1 = new Phrase();
            phrase1.add(new Chunk(intestazionePdf + " From " + datereport1 + " To " + datereport2, f3_bold));
            PdfPCell cell1 = new PdfPCell(phrase1);
            cell1.setBorder(NO_BORDER);

            Phrase phrase3 = new Phrase();
            if (siq != null) {
                phrase3.add(new Chunk("\n " + siq.getId_filiale() + " " + siq.getDe_filiale(), f3_normal));
            } 
            PdfPCell cell3 = new PdfPCell(phrase3);
            cell3.setBorder(NO_BORDER);

            table.addCell(cell1);
            table.addCell(cellavuota);
            table.addCell(cell3);
            table.addCell(cellavuota);
            document.add(table);
            vuoto.setFont(f3_normal);
            document.add(cellavuota);

            document.add(vuoto);

            scriviIntestazioneColonne(document);

            //Popolo la tabella
            PdfPTable table3, table6;

            if (siq != null) {

                ArrayList<TillTransactionListBB_value> dati = siq.getDati();
                Phrase phraset;
                PdfPCell cellt;

                table3 = new PdfPTable(18);
                table3.setWidths(columnWidths2);
                table3.setWidthPercentage(100);

                double totaleresidentbuy = 0;
                double totalenonresidentbuy = 0;
                double totaleresidentcommfreebuy = 0;
                double totalenonresidentcommfreebuy = 0;

                double totaleresidentsell = 0;
                double totalenonresidentsell = 0;
                double totaleresidentcommfreesell = 0;
                double totalenonresidentcommfreesell = 0;

                double totalepayinoutgeneral = 0;
                double totround = 0;
                double totcommfee = 0;

                for (int i = 0; i < dati.size(); i++) {

                    TillTransactionListBB_value actual = (TillTransactionListBB_value) dati.get(i);
                   
                    phraset = new Phrase();
                    phraset.add(new Chunk(actual.getType(), f5_normal));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_LEFT);
                    cellt.setVerticalAlignment(ALIGN_BOTTOM);
                    cellt.setBorder(NO_BORDER);
                    table3.addCell(cellt);

                    phraset = new Phrase();
                    phraset.add(new Chunk(actual.getTill(), f5_normal));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setVerticalAlignment(ALIGN_BOTTOM);
                    cellt.setBorder(NO_BORDER);
                    table3.addCell(cellt);

                    phraset = new Phrase();
                    phraset.add(new Chunk(actual.getUser(), f5_normal));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setVerticalAlignment(ALIGN_BOTTOM);
                    cellt.setBorder(NO_BORDER);
                    table3.addCell(cellt);
                    
                    phraset = new Phrase();
                    phraset.add(new Chunk(actual.getId_filiale(), f5_normal));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setVerticalAlignment(ALIGN_BOTTOM);
                    cellt.setBorder(NO_BORDER);
                    table3.addCell(cellt);

                    phraset = new Phrase();
                    phraset.add(new Chunk(actual.getNotr(), f5_normal));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setVerticalAlignment(ALIGN_BOTTOM);
                    cellt.setBorder(NO_BORDER);
                    table3.addCell(cellt);

                    phraset = new Phrase();
                    phraset.add(new Chunk(formatStringtoStringDate(actual.getTime(), patternsqldate, patternnormdate), f5_normal));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_LEFT);
                    cellt.setVerticalAlignment(ALIGN_BOTTOM);
                    cellt.setBorder(NO_BORDER);
                    table3.addCell(cellt);

                    phraset = new Phrase();
                    phraset.add(new Chunk(actual.getCur(), f5_normal));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_LEFT);
                    cellt.setVerticalAlignment(ALIGN_BOTTOM);
                    cellt.setBorder(NO_BORDER);
                    table3.addCell(cellt);

                    phraset = new Phrase();
                    phraset.add(new Chunk(actual.getKind(), f5_normal));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_LEFT);
                    cellt.setVerticalAlignment(ALIGN_BOTTOM);
                    cellt.setBorder(NO_BORDER);
                    table3.addCell(cellt);

                    if (actual.getKind().contains("Buy")) {
                        if (actual.getResidentnonresident().equalsIgnoreCase("Resident")) {
                            totaleresidentbuy += fd(actual.getTotalSenzaFormattazione());
                            totaleresidentcommfreebuy += fd(actual.getComfreeSenzaFormattazione());

                        } else {
                            totalenonresidentcommfreebuy += fd(actual.getComfreeSenzaFormattazione());
                            totalenonresidentbuy += fd(actual.getTotalSenzaFormattazione());
                        }
                    }
                    if (actual.getKind().contains("Sell")) {
                        if (actual.getResidentnonresident().equalsIgnoreCase("Resident")) {

                            totaleresidentsell += fd(actual.getTotalSenzaFormattazione());
                            totaleresidentcommfreesell += fd(actual.getComfreeSenzaFormattazione());
                        } else {
                            totalenonresidentsell += fd(actual.getTotalSenzaFormattazione());

                            totalenonresidentcommfreesell += fd(actual.getComfreeSenzaFormattazione());
                        }
                    }

                    totaleresidentbuy = roundDouble(totaleresidentbuy, 2);
                    totaleresidentcommfreebuy = roundDouble(totaleresidentcommfreebuy, 2);
                    totalenonresidentcommfreebuy = roundDouble(totalenonresidentcommfreebuy, 2);
                    totalenonresidentbuy = roundDouble(totalenonresidentbuy, 2);
                    totaleresidentsell = roundDouble(totaleresidentsell, 2);
                    totaleresidentcommfreesell = roundDouble(totaleresidentcommfreesell, 2);
                    totalenonresidentsell = roundDouble(totalenonresidentsell, 2);
                    totalenonresidentcommfreesell = roundDouble(totalenonresidentcommfreesell, 2);

                    phraset = new Phrase();
                    phraset.add(new Chunk(formatMysqltoDisplay(actual.getAmount()), f5_normal));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setVerticalAlignment(ALIGN_BOTTOM);
                    cellt.setBorder(NO_BORDER);
                    table3.addCell(cellt);

                    phraset = new Phrase();
                    phraset.add(new Chunk(formatMysqltoDisplay(actual.getRate()), f5_normal));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setVerticalAlignment(ALIGN_BOTTOM);
                    cellt.setBorder(NO_BORDER);
                    table3.addCell(cellt);

                    phraset = new Phrase();
                    phraset.add(new Chunk(formatMysqltoDisplay(actual.getTotal()), f5_normal));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setVerticalAlignment(ALIGN_BOTTOM);
                    cellt.setBorder(NO_BORDER);
                    table3.addCell(cellt);

                    phraset = new Phrase();
                    phraset.add(new Chunk(formatMysqltoDisplay(actual.getPerc()), f5_normal));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setVerticalAlignment(ALIGN_BOTTOM);
                    cellt.setBorder(NO_BORDER);
                    table3.addCell(cellt);

                    phraset = new Phrase();
                    phraset.add(new Chunk(formatMysqltoDisplay(actual.getComfree()), f5_normal));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setVerticalAlignment(ALIGN_BOTTOM);
                    cellt.setBorder(NO_BORDER);
                    table3.addCell(cellt);

                    totcommfee = totcommfee + ((fd(actual.getComfree())));

                    totcommfee = roundDouble(totcommfee, 2);

                    phraset = new Phrase();
                    phraset.add(new Chunk(formatMysqltoDisplay(actual.getRound()), f5_normal));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setVerticalAlignment(ALIGN_BOTTOM);
                    cellt.setBorder(NO_BORDER);
                    table3.addCell(cellt);

                    totround = totround + ((fd(actual.getRound())));

                    totround = roundDouble(totround, 2);

                    if (actual.getKind().startsWith("S")) {

                        phraset = new Phrase();
                        phraset.add(new Chunk("+" + formatMysqltoDisplay(actual.getPayinpayout()), f5_normal));
                        cellt = new PdfPCell(phraset);
                        cellt.setHorizontalAlignment(ALIGN_RIGHT);
                        cellt.setVerticalAlignment(ALIGN_BOTTOM);
                        cellt.setBorder(NO_BORDER);
                        table3.addCell(cellt);

                        totalepayinoutgeneral += fd(actual.getPayinpayoutSenzaFormattazione());

                        totalepayinoutgeneral = roundDouble(totalepayinoutgeneral, 2);
                    } else if (actual.getKind().startsWith("B")) {

                        phraset = new Phrase();
                        phraset.add(new Chunk("-" + formatMysqltoDisplay(actual.getPayinpayout()), f5_normal));
                        cellt = new PdfPCell(phraset);
                        cellt.setHorizontalAlignment(ALIGN_RIGHT);
                        cellt.setVerticalAlignment(ALIGN_BOTTOM);
                        cellt.setBorder(NO_BORDER);
                        table3.addCell(cellt);

                        totalepayinoutgeneral -= fd(actual.getPayinpayoutSenzaFormattazione());

                        totalepayinoutgeneral = roundDouble(totalepayinoutgeneral, 2);
                    } else  {

                        phraset = new Phrase();
                        phraset.add(new Chunk(" ", f5_normal));
                        cellt = new PdfPCell(phraset);
                        cellt.setHorizontalAlignment(ALIGN_RIGHT);
                        cellt.setVerticalAlignment(ALIGN_BOTTOM);
                        cellt.setBorder(NO_BORDER);
                        table3.addCell(cellt);
                    }

                    phraset = new Phrase();
                    phraset.add(new Chunk(actual.getCustomer(), f5_normal));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_LEFT);
                    cellt.setVerticalAlignment(ALIGN_BOTTOM);
                    cellt.setBorder(NO_BORDER);
                    table3.addCell(cellt);

                    phraset = new Phrase();
                    phraset.add(new Chunk(formatMysqltoDisplay(actual.getSpread()), f5_normal));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setVerticalAlignment(ALIGN_BOTTOM);
                    cellt.setBorder(NO_BORDER);
                    table3.addCell(cellt);

                    phraset = new Phrase();
                    phraset.add(new Chunk(actual.getPos(), f5_normal));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setVerticalAlignment(ALIGN_BOTTOM);
                    cellt.setBorder(NO_BORDER);
                    table3.addCell(cellt);

                    if (intestazionePdf.contains("Delete")) {

                        for (int t = 0; t < 14; t++) {

                            phraset = new Phrase();
                            phraset.add(new Chunk("", f5_normal));
                            cellt = new PdfPCell(phraset);
                            cellt.setHorizontalAlignment(ALIGN_RIGHT);
                            cellt.setVerticalAlignment(ALIGN_BOTTOM);
                            cellt.setBorder(NO_BORDER);
                            table3.addCell(cellt);
                        }

                        phraset = new Phrase();
                        phraset.add(new Chunk(actual.getDelete1() + "", f5_normal));
                        cellt = new PdfPCell(phraset);
                        cellt.setHorizontalAlignment(ALIGN_LEFT);
                        cellt.setVerticalAlignment(ALIGN_BOTTOM);
                        cellt.setBorder(NO_BORDER);
                        table3.addCell(cellt);

                        phraset = new Phrase();
                        phraset.add(new Chunk(actual.getDelete2() + "", f5_normal));
                        cellt = new PdfPCell(phraset);
                        cellt.setHorizontalAlignment(ALIGN_LEFT);
                        cellt.setVerticalAlignment(ALIGN_BOTTOM);
                        cellt.setBorder(NO_BORDER);
                        table3.addCell(cellt);

                        phraset = new Phrase();
                        phraset.add(new Chunk("", f5_normal));
                        cellt = new PdfPCell(phraset);
                        cellt.setHorizontalAlignment(ALIGN_RIGHT);
                        cellt.setVerticalAlignment(ALIGN_BOTTOM);
                        cellt.setBorder(NO_BORDER);
                        table3.addCell(cellt);

                    }

                }

                document.add(table3);

                LineSeparator ls = new LineSeparator();
                ls.setLineWidth(0.7f);
                document.add(ls);

                table3 = new PdfPTable(18);
                table3.setWidths(columnWidths2);
                table3.setWidthPercentage(100);

                for (int v = 0; v < columnWidths2.length; v++) {
                    if (v == 9) {
                        double totalegenerale = totaleresidentbuy + totaleresidentsell + totalenonresidentbuy + totalenonresidentsell;
                        phraset = new Phrase();
                        phraset.add(new Chunk((formatMysqltoDisplay(roundDoubleandFormat(totalegenerale, 2))), f5_bold));
                        cellt = new PdfPCell(phraset);
                        cellt.setHorizontalAlignment(ALIGN_RIGHT);
                        cellt.setVerticalAlignment(ALIGN_BOTTOM);
                        cellt.setBorder(NO_BORDER);
                        table3.addCell(cellt);
                    } else if (v == 11) {
                        phraset = new Phrase();
                        phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(totcommfee, 2)), f5_bold));
                        cellt = new PdfPCell(phraset);
                        cellt.setHorizontalAlignment(ALIGN_RIGHT);
                        cellt.setVerticalAlignment(ALIGN_BOTTOM);
                        cellt.setBorder(NO_BORDER);
                        table3.addCell(cellt);
                    } else if (v == 12) {
                        phraset = new Phrase();
                        phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(totround, 2)), f5_bold));
                        cellt = new PdfPCell(phraset);
                        cellt.setHorizontalAlignment(ALIGN_RIGHT);
                        cellt.setVerticalAlignment(ALIGN_BOTTOM);
                        cellt.setBorder(NO_BORDER);
                        table3.addCell(cellt);

                    } else if (v == 13) {
                        phraset = new Phrase();
                        phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(totalepayinoutgeneral, 2)), f5_bold));
                        cellt = new PdfPCell(phraset);
                        cellt.setHorizontalAlignment(ALIGN_RIGHT);
                        cellt.setVerticalAlignment(ALIGN_BOTTOM);
                        cellt.setBorder(NO_BORDER);
                        table3.addCell(cellt);
                    } else {
                        phraset = new Phrase();
                        phraset.add(new Chunk("", f5_bold));
                        cellt = new PdfPCell(phraset);
                        cellt.setHorizontalAlignment(ALIGN_RIGHT);
                        cellt.setVerticalAlignment(ALIGN_BOTTOM);
                        cellt.setBorder(NO_BORDER);
                        table3.addCell(cellt);
                    }

                }

                document.add(table3);

                document.add(ls);

                ls = new LineSeparator();
                ls.setLineWidth(0.7f);
                document.add(ls);

            }

////////////////////            if (siqbuy != null) {
////////////////////                ///////////////   BUY  /////////////////////////////////
//////////////////////            PdfPTable table3, table6;
////////////////////                ArrayList<String> dati = siqbuy.getDati();
//////////////////////            Phrase phraset;
//////////////////////            PdfPCell cellt;ddd
////////////////////
////////////////////                table3 = new PdfPTable(17);
////////////////////                table3.setWidths(columnWidths2);
////////////////////                table3.setWidthPercentage(100);
////////////////////
////////////////////                double totaleresidentbuy1 = 0;
////////////////////                double totalenonresidentbuy1 = 0;
////////////////////                double totaleresidentcommfreebuy1 = 0;
////////////////////                double totalenonresidentcommfreebuy1 = 0;
////////////////////
////////////////////                double totaleresidentsell1 = 0;
////////////////////                double totalenonresidentsell1 = 0;
////////////////////                double totaleresidentcommfreesell1 = 0;
////////////////////                double totalenonresidentcommfreesell1 = 0;
////////////////////
////////////////////                double totalepayinoutgeneral1 = 0;
////////////////////                double totround1 = 0;
////////////////////                double totcommfee1 = 0;
////////////////////
////////////////////                for (int i = 0; i < dati.size(); i++) {
////////////////////
////////////////////                    TillTransactionList_value actual = (TillTransactionList_value) dati.get(i);
////////////////////                    TillTransactionList_value prossimo = (TillTransactionList_value) dati.get(i);
////////////////////
////////////////////                    Phrase phraset = new Phrase();
////////////////////                    phraset.add(new Chunk(actual.getType(), f5_normal));
////////////////////                    PdfPCell cellt = new PdfPCell(phraset);
////////////////////                    cellt.setHorizontalAlignment(Element.ALIGN_LEFT);
////////////////////                    cellt.setVerticalAlignment(Element.ALIGN_BOTTOM);
////////////////////                    cellt.setBorder(Rectangle.NO_BORDER);
////////////////////                    table3.addCell(cellt);
////////////////////
////////////////////                    phraset = new Phrase();
////////////////////                    phraset.add(new Chunk(actual.getTill(), f5_normal));
////////////////////                    cellt = new PdfPCell(phraset);
////////////////////                    cellt.setHorizontalAlignment(Element.ALIGN_RIGHT);
////////////////////                    cellt.setVerticalAlignment(Element.ALIGN_BOTTOM);
////////////////////                    cellt.setBorder(Rectangle.NO_BORDER);
////////////////////                    table3.addCell(cellt);
////////////////////
////////////////////                    phraset = new Phrase();
////////////////////                    phraset.add(new Chunk(actual.getUser(), f5_normal));
////////////////////                    cellt = new PdfPCell(phraset);
////////////////////                    cellt.setHorizontalAlignment(Element.ALIGN_RIGHT);
////////////////////                    cellt.setVerticalAlignment(Element.ALIGN_BOTTOM);
////////////////////                    cellt.setBorder(Rectangle.NO_BORDER);
////////////////////                    table3.addCell(cellt);
////////////////////
////////////////////                    phraset = new Phrase();
////////////////////                    phraset.add(new Chunk(actual.getNotr(), f5_normal));
////////////////////                    cellt = new PdfPCell(phraset);
////////////////////                    cellt.setHorizontalAlignment(Element.ALIGN_RIGHT);
////////////////////                    cellt.setVerticalAlignment(Element.ALIGN_BOTTOM);
////////////////////                    cellt.setBorder(Rectangle.NO_BORDER);
////////////////////                    table3.addCell(cellt);
////////////////////
////////////////////                    phraset = new Phrase();
////////////////////                    phraset.add(new Chunk(Utility.formatStringtoStringDate(actual.getTime(), Constant.patternsqldate, Constant.patternnormdate), f5_normal));
////////////////////                    cellt = new PdfPCell(phraset);
////////////////////                    cellt.setHorizontalAlignment(Element.ALIGN_LEFT);
////////////////////                    cellt.setVerticalAlignment(Element.ALIGN_BOTTOM);
////////////////////                    cellt.setBorder(Rectangle.NO_BORDER);
////////////////////                    table3.addCell(cellt);
////////////////////
////////////////////                    phraset = new Phrase();
////////////////////                    phraset.add(new Chunk(actual.getCur(), f5_normal));
////////////////////                    cellt = new PdfPCell(phraset);
////////////////////                    cellt.setHorizontalAlignment(Element.ALIGN_LEFT);
////////////////////                    cellt.setVerticalAlignment(Element.ALIGN_BOTTOM);
////////////////////                    cellt.setBorder(Rectangle.NO_BORDER);
////////////////////                    table3.addCell(cellt);
////////////////////
////////////////////                    phraset = new Phrase();
////////////////////                    phraset.add(new Chunk(actual.getKind(), f5_normal));
////////////////////                    cellt = new PdfPCell(phraset);
////////////////////                    cellt.setHorizontalAlignment(Element.ALIGN_LEFT);
////////////////////                    cellt.setVerticalAlignment(Element.ALIGN_BOTTOM);
////////////////////                    cellt.setBorder(Rectangle.NO_BORDER);
////////////////////                    table3.addCell(cellt);
////////////////////
////////////////////                    if (actual.getKind().contains("Buy")) {
////////////////////                        if (actual.getResidentnonresident().equalsIgnoreCase("Resident")) {
////////////////////                            totaleresidentbuy1 += Utility.fd(actual.getTotalSenzaFormattazione());
////////////////////                            totaleresidentcommfreebuy1 += Utility.fd(actual.getComfreeSenzaFormattazione());
////////////////////
////////////////////                        } else {
////////////////////                            totalenonresidentcommfreebuy1 += Utility.fd(actual.getComfreeSenzaFormattazione());
////////////////////                            totalenonresidentbuy1 += Utility.fd(actual.getTotalSenzaFormattazione());
////////////////////                        }
////////////////////                    }
////////////////////                    if (actual.getKind().contains("Sell")) {
////////////////////                        if (actual.getResidentnonresident().equalsIgnoreCase("Resident")) {
////////////////////
////////////////////                            totaleresidentsell1 += Utility.fd(actual.getTotalSenzaFormattazione());
////////////////////                            totaleresidentcommfreesell1 += Utility.fd(actual.getComfreeSenzaFormattazione());
////////////////////                        } else {
////////////////////                            totalenonresidentsell1 += Utility.fd(actual.getTotalSenzaFormattazione());
////////////////////
////////////////////                            totalenonresidentcommfreesell1 += Utility.fd(actual.getComfreeSenzaFormattazione());
////////////////////                        }
////////////////////                    }
////////////////////
////////////////////                    totaleresidentbuy1 = Utility.roundDouble(totaleresidentbuy1, 2);
////////////////////                    totaleresidentcommfreebuy1 = Utility.roundDouble(totaleresidentcommfreebuy1, 2);
////////////////////                    totalenonresidentcommfreebuy1 = Utility.roundDouble(totalenonresidentcommfreebuy1, 2);
////////////////////                    totalenonresidentbuy1 = Utility.roundDouble(totalenonresidentbuy1, 2);
////////////////////                    totaleresidentsell1 = Utility.roundDouble(totaleresidentsell1, 2);
////////////////////                    totaleresidentcommfreesell1 = Utility.roundDouble(totaleresidentcommfreesell1, 2);
////////////////////                    totalenonresidentsell1 = Utility.roundDouble(totalenonresidentsell1, 2);
////////////////////                    totalenonresidentcommfreesell1 = Utility.roundDouble(totalenonresidentcommfreesell1, 2);
////////////////////
////////////////////                    phraset = new Phrase();
////////////////////                    phraset.add(new Chunk(Utility.formatMysqltoDisplay(actual.getAmount()), f5_normal));
////////////////////                    cellt = new PdfPCell(phraset);
////////////////////                    cellt.setHorizontalAlignment(Element.ALIGN_RIGHT);
////////////////////                    cellt.setVerticalAlignment(Element.ALIGN_BOTTOM);
////////////////////                    cellt.setBorder(Rectangle.NO_BORDER);
////////////////////                    table3.addCell(cellt);
////////////////////
////////////////////                    phraset = new Phrase();
////////////////////                    phraset.add(new Chunk(Utility.formatMysqltoDisplay(actual.getRate()), f5_normal));
////////////////////                    cellt = new PdfPCell(phraset);
////////////////////                    cellt.setHorizontalAlignment(Element.ALIGN_RIGHT);
////////////////////                    cellt.setVerticalAlignment(Element.ALIGN_BOTTOM);
////////////////////                    cellt.setBorder(Rectangle.NO_BORDER);
////////////////////                    table3.addCell(cellt);
////////////////////
////////////////////                    phraset = new Phrase();
////////////////////                    phraset.add(new Chunk(Utility.formatMysqltoDisplay(actual.getTotal()), f5_normal));
////////////////////                    cellt = new PdfPCell(phraset);
////////////////////                    cellt.setHorizontalAlignment(Element.ALIGN_RIGHT);
////////////////////                    cellt.setVerticalAlignment(Element.ALIGN_BOTTOM);
////////////////////                    cellt.setBorder(Rectangle.NO_BORDER);
////////////////////                    table3.addCell(cellt);
////////////////////
////////////////////                    phraset = new Phrase();
////////////////////                    phraset.add(new Chunk(Utility.formatMysqltoDisplay(actual.getPerc()), f5_normal));
////////////////////                    cellt = new PdfPCell(phraset);
////////////////////                    cellt.setHorizontalAlignment(Element.ALIGN_RIGHT);
////////////////////                    cellt.setVerticalAlignment(Element.ALIGN_BOTTOM);
////////////////////                    cellt.setBorder(Rectangle.NO_BORDER);
////////////////////                    table3.addCell(cellt);
////////////////////
////////////////////                    phraset = new Phrase();
////////////////////                    phraset.add(new Chunk(Utility.formatMysqltoDisplay(actual.getComfree()), f5_normal));
////////////////////                    cellt = new PdfPCell(phraset);
////////////////////                    cellt.setHorizontalAlignment(Element.ALIGN_RIGHT);
////////////////////                    cellt.setVerticalAlignment(Element.ALIGN_BOTTOM);
////////////////////                    cellt.setBorder(Rectangle.NO_BORDER);
////////////////////                    table3.addCell(cellt);
////////////////////
////////////////////                    totcommfee1 = totcommfee1 + Utility.fd((String.valueOf(actual.getComfree())));
////////////////////
////////////////////                    totcommfee1 = Utility.roundDouble(totcommfee1, 2);
////////////////////
////////////////////                    phraset = new Phrase();
////////////////////                    phraset.add(new Chunk(Utility.formatMysqltoDisplay(actual.getRound()), f5_normal));
////////////////////                    cellt = new PdfPCell(phraset);
////////////////////                    cellt.setHorizontalAlignment(Element.ALIGN_RIGHT);
////////////////////                    cellt.setVerticalAlignment(Element.ALIGN_BOTTOM);
////////////////////                    cellt.setBorder(Rectangle.NO_BORDER);
////////////////////                    table3.addCell(cellt);
////////////////////
////////////////////                    totround1 = totround1 + Utility.fd((String.valueOf(actual.getRound())));
////////////////////
////////////////////                    totround1 = Utility.roundDouble(totround1, 2);
////////////////////
////////////////////                    if (actual.getKind().contains("S")) {
////////////////////
////////////////////                        phraset = new Phrase();
////////////////////                        phraset.add(new Chunk("+" + Utility.formatMysqltoDisplay(actual.getPayinpayout()), f5_normal));
////////////////////                        cellt = new PdfPCell(phraset);
////////////////////                        cellt.setHorizontalAlignment(Element.ALIGN_RIGHT);
////////////////////                        cellt.setVerticalAlignment(Element.ALIGN_BOTTOM);
////////////////////                        cellt.setBorder(Rectangle.NO_BORDER);
////////////////////                        table3.addCell(cellt);
////////////////////
////////////////////                        totalepayinoutgeneral1 += Utility.fd(actual.getPayinpayoutSenzaFormattazione());
////////////////////
////////////////////                        totalepayinoutgeneral1 = Utility.roundDouble(totalepayinoutgeneral1, 2);
////////////////////                    } else {
////////////////////
////////////////////                        phraset = new Phrase();
////////////////////                        phraset.add(new Chunk("-" + Utility.formatMysqltoDisplay(actual.getPayinpayout()), f5_normal));
////////////////////                        cellt = new PdfPCell(phraset);
////////////////////                        cellt.setHorizontalAlignment(Element.ALIGN_RIGHT);
////////////////////                        cellt.setVerticalAlignment(Element.ALIGN_BOTTOM);
////////////////////                        cellt.setBorder(Rectangle.NO_BORDER);
////////////////////                        table3.addCell(cellt);
////////////////////
////////////////////                        totalepayinoutgeneral1 -= Utility.fd(actual.getPayinpayoutSenzaFormattazione());
////////////////////                        totalepayinoutgeneral1 = Utility.roundDouble(totalepayinoutgeneral1, 2);
////////////////////                    }
////////////////////
////////////////////                    phraset = new Phrase();
////////////////////                    phraset.add(new Chunk(actual.getCustomer(), f5_normal));
////////////////////                    cellt = new PdfPCell(phraset);
////////////////////                    cellt.setHorizontalAlignment(Element.ALIGN_LEFT);
////////////////////                    cellt.setVerticalAlignment(Element.ALIGN_BOTTOM);
////////////////////                    cellt.setBorder(Rectangle.NO_BORDER);
////////////////////                    table3.addCell(cellt);
////////////////////
////////////////////                    phraset = new Phrase();
////////////////////                    phraset.add(new Chunk(actual.getSpread(), f5_normal));
////////////////////                    cellt = new PdfPCell(phraset);
////////////////////                    cellt.setHorizontalAlignment(Element.ALIGN_RIGHT);
////////////////////                    cellt.setVerticalAlignment(Element.ALIGN_BOTTOM);
////////////////////                    cellt.setBorder(Rectangle.NO_BORDER);
////////////////////                    table3.addCell(cellt);
////////////////////
////////////////////                    phraset = new Phrase();
////////////////////                    phraset.add(new Chunk(actual.getPos(), f5_normal));
////////////////////                    cellt = new PdfPCell(phraset);
////////////////////                    cellt.setHorizontalAlignment(Element.ALIGN_RIGHT);
////////////////////                    cellt.setVerticalAlignment(Element.ALIGN_BOTTOM);
////////////////////                    cellt.setBorder(Rectangle.NO_BORDER);
////////////////////                    table3.addCell(cellt);
////////////////////
////////////////////                    if (intestazionePdf.contains("Delete")) {
////////////////////
////////////////////                        for (int t = 0; t < 14; t++) {
////////////////////
////////////////////                            phraset = new Phrase();
////////////////////                            phraset.add(new Chunk("", f5_normal));
////////////////////                            cellt = new PdfPCell(phraset);
////////////////////                            cellt.setHorizontalAlignment(Element.ALIGN_RIGHT);
////////////////////                            cellt.setVerticalAlignment(Element.ALIGN_BOTTOM);
////////////////////                            cellt.setBorder(Rectangle.NO_BORDER);
////////////////////                            table3.addCell(cellt);
////////////////////                        }
////////////////////
////////////////////                        phraset = new Phrase();
////////////////////                        phraset.add(new Chunk(actual.getDelete1() + "", f5_normal));
////////////////////                        cellt = new PdfPCell(phraset);
////////////////////                        cellt.setHorizontalAlignment(Element.ALIGN_LEFT);
////////////////////                        cellt.setVerticalAlignment(Element.ALIGN_BOTTOM);
////////////////////                        cellt.setBorder(Rectangle.NO_BORDER);
////////////////////                        table3.addCell(cellt);
////////////////////
////////////////////                        phraset = new Phrase();
////////////////////                        phraset.add(new Chunk(actual.getDelete2() + "", f5_normal));
////////////////////                        cellt = new PdfPCell(phraset);
////////////////////                        cellt.setHorizontalAlignment(Element.ALIGN_LEFT);
////////////////////                        cellt.setVerticalAlignment(Element.ALIGN_BOTTOM);
////////////////////                        cellt.setBorder(Rectangle.NO_BORDER);
////////////////////                        table3.addCell(cellt);
////////////////////
////////////////////                        phraset = new Phrase();
////////////////////                        phraset.add(new Chunk("", f5_normal));
////////////////////                        cellt = new PdfPCell(phraset);
////////////////////                        cellt.setHorizontalAlignment(Element.ALIGN_RIGHT);
////////////////////                        cellt.setVerticalAlignment(Element.ALIGN_BOTTOM);
////////////////////                        cellt.setBorder(Rectangle.NO_BORDER);
////////////////////                        table3.addCell(cellt);
////////////////////
////////////////////                    }
////////////////////
////////////////////                }
////////////////////
////////////////////                document.add(table3);
////////////////////
////////////////////                LineSeparator ls = new LineSeparator();
////////////////////                ls.setLineWidth(0.7f);
////////////////////                document.add(ls);
////////////////////
////////////////////                table3 = new PdfPTable(17);
////////////////////                table3.setWidths(columnWidths2);
////////////////////                table3.setWidthPercentage(100);
////////////////////
////////////////////                for (int v = 0; v < columnWidths2.length; v++) {
////////////////////                    if (v == 9) {
////////////////////                        double totalegenerale = totaleresidentbuy1 + totaleresidentsell1 + totalenonresidentbuy1 + totalenonresidentsell1;
////////////////////                        totalegenerale = Utility.roundDouble(totalegenerale, 2);
////////////////////                        Phrase phraset = new Phrase();
////////////////////                        phraset.add(new Chunk((formatMysqltoDisplay(Utility.roundDoubleandFormat(totalegenerale, 2))), f5_bold));
////////////////////                        PdfPCell cellt = new PdfPCell(phraset);
////////////////////                        cellt.setHorizontalAlignment(Element.ALIGN_RIGHT);
////////////////////                        cellt.setVerticalAlignment(Element.ALIGN_BOTTOM);
////////////////////                        cellt.setBorder(Rectangle.NO_BORDER);
////////////////////                        table3.addCell(cellt);
////////////////////                    } else if (v == 11) {
////////////////////                        Phrase phraset = new Phrase();
////////////////////                        totcommfee1 = Utility.roundDouble(totcommfee1, 2);
////////////////////                        phraset.add(new Chunk(formatMysqltoDisplay(Utility.roundDoubleandFormat(totcommfee1, 2)), f5_bold));
////////////////////                        PdfPCell cellt = new PdfPCell(phraset);
////////////////////                        cellt.setHorizontalAlignment(Element.ALIGN_RIGHT);
////////////////////                        cellt.setVerticalAlignment(Element.ALIGN_BOTTOM);
////////////////////                        cellt.setBorder(Rectangle.NO_BORDER);
////////////////////                        table3.addCell(cellt);
////////////////////                    } else if (v == 12) {
////////////////////                        Phrase phraset = new Phrase();
////////////////////                        phraset.add(new Chunk(formatMysqltoDisplay(Utility.roundDoubleandFormat(totround1, 2)), f5_bold));
////////////////////                        PdfPCell cellt = new PdfPCell(phraset);
////////////////////                        cellt.setHorizontalAlignment(Element.ALIGN_RIGHT);
////////////////////                        cellt.setVerticalAlignment(Element.ALIGN_BOTTOM);
////////////////////                        cellt.setBorder(Rectangle.NO_BORDER);
////////////////////                        table3.addCell(cellt);
////////////////////
////////////////////                    } else if (v == 13) {
////////////////////                        totalepayinoutgeneral1 = Utility.roundDouble(totalepayinoutgeneral1, 2);
////////////////////                        Phrase phraset = new Phrase();
////////////////////                        phraset.add(new Chunk(formatMysqltoDisplay(Utility.roundDoubleandFormat(totalepayinoutgeneral1, 2)), f5_bold));
////////////////////                        PdfPCell cellt = new PdfPCell(phraset);
////////////////////                        cellt.setHorizontalAlignment(Element.ALIGN_RIGHT);
////////////////////                        cellt.setVerticalAlignment(Element.ALIGN_BOTTOM);
////////////////////                        cellt.setBorder(Rectangle.NO_BORDER);
////////////////////                        table3.addCell(cellt);
////////////////////                    } else {
////////////////////                        Phrase phraset = new Phrase();
////////////////////                        phraset.add(new Chunk("", f5_bold));
////////////////////                        PdfPCell cellt = new PdfPCell(phraset);
////////////////////                        cellt.setHorizontalAlignment(Element.ALIGN_RIGHT);
////////////////////                        cellt.setVerticalAlignment(Element.ALIGN_BOTTOM);
////////////////////                        cellt.setBorder(Rectangle.NO_BORDER);
////////////////////                        table3.addCell(cellt);
////////////////////                    }
////////////////////
////////////////////                }
////////////////////
////////////////////                document.add(table3);
////////////////////
////////////////////                document.add(ls);
////////////////////
////////////////////            }

            /// fine buy /////////////////
            table3 = new PdfPTable(21);
            table3.setWidths(columnWidths4);
            table3.setWidthPercentage(100);

            Phrase phraset = new Phrase();
            phraset.add(new Chunk("Transaction value", f5_bold));
            PdfPCell cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_CENTER);
            cellt.setVerticalAlignment(ALIGN_BOTTOM);
            cellt.setBorder(NO_BORDER);
            cellt.setColspan(4);
            table3.addCell(cellt);

            phraset = new Phrase();
            phraset.add(new Chunk("Commissions value", f5_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_CENTER);
            cellt.setVerticalAlignment(ALIGN_BOTTOM);
            cellt.setBorder(NO_BORDER);
            cellt.setColspan(4);
            table3.addCell(cellt);

            phraset = new Phrase();
            phraset.add(new Chunk("Transaction number", f5_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_CENTER);
            cellt.setVerticalAlignment(ALIGN_BOTTOM);
            cellt.setBorder(NO_BORDER);
            cellt.setColspan(4);
            table3.addCell(cellt);

            phraset = new Phrase();
            phraset.add(new Chunk("Sell / Internet booking", f5_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_CENTER);
            cellt.setVerticalAlignment(ALIGN_BOTTOM);
            cellt.setBorder(NO_BORDER);
            cellt.setColspan(3);
            table3.addCell(cellt);

            phraset = new Phrase();
            phraset.add(new Chunk("POS", f5_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_CENTER);
            cellt.setVerticalAlignment(ALIGN_BOTTOM);
            cellt.setBorder(NO_BORDER);
            cellt.setColspan(3);
            table3.addCell(cellt);

            phraset = new Phrase();
            phraset.add(new Chunk("Bank Account", f5_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_CENTER);
            cellt.setVerticalAlignment(ALIGN_BOTTOM);
            cellt.setBorder(NO_BORDER);
            cellt.setColspan(3);
            table3.addCell(cellt);

            table3.setSpacingBefore(15);

            document.add(table3);

            table3 = new PdfPTable(21);
            table3.setWidths(columnWidths4);
            table3.setWidthPercentage(100);

            phraset = new Phrase();
            phraset.add(new Chunk("", f5_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setVerticalAlignment(ALIGN_BOTTOM);
            cellt.setBorder(NO_BORDER);
            table3.addCell(cellt);

            phraset = new Phrase();
            phraset.add(new Chunk("Resident", f5_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setVerticalAlignment(ALIGN_BOTTOM);
            cellt.setBorder(BOX);
            table3.addCell(cellt);

            phraset = new Phrase();
            phraset.add(new Chunk("Non resident", f5_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setVerticalAlignment(ALIGN_BOTTOM);
            cellt.setBorder(BOX);
            table3.addCell(cellt);

            phraset = new Phrase();
            phraset.add(new Chunk("Total", f5_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setVerticalAlignment(ALIGN_BOTTOM);
            cellt.setBorder(BOX);
            table3.addCell(cellt);

            phraset = new Phrase();
            phraset.add(new Chunk("", f5_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setVerticalAlignment(ALIGN_BOTTOM);
            cellt.setBorder(NO_BORDER);
            table3.addCell(cellt);

            phraset = new Phrase();
            phraset.add(new Chunk("Resident", f5_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setVerticalAlignment(ALIGN_BOTTOM);
            cellt.setBorder(BOX);
            table3.addCell(cellt);

            phraset = new Phrase();
            phraset.add(new Chunk("Non resident", f5_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setVerticalAlignment(ALIGN_BOTTOM);
            cellt.setBorder(BOX);
            table3.addCell(cellt);

            phraset = new Phrase();
            phraset.add(new Chunk("Total", f5_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setVerticalAlignment(ALIGN_BOTTOM);
            cellt.setBorder(BOX);
            table3.addCell(cellt);

            phraset = new Phrase();
            phraset.add(new Chunk("", f5_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setVerticalAlignment(ALIGN_BOTTOM);
            cellt.setBorder(NO_BORDER);
            table3.addCell(cellt);

            phraset = new Phrase();
            phraset.add(new Chunk("Resident", f5_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setVerticalAlignment(ALIGN_BOTTOM);
            cellt.setBorder(BOX);
            table3.addCell(cellt);

            phraset = new Phrase();
            phraset.add(new Chunk("Non resident", f5_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setVerticalAlignment(ALIGN_BOTTOM);
            cellt.setBorder(BOX);
            table3.addCell(cellt);

            phraset = new Phrase();
            phraset.add(new Chunk("Total", f5_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setVerticalAlignment(ALIGN_BOTTOM);
            cellt.setBorder(BOX);
            table3.addCell(cellt);

            phraset = new Phrase();
            phraset.add(new Chunk("", f5_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setVerticalAlignment(ALIGN_BOTTOM);
            cellt.setBorder(NO_BORDER);
            table3.addCell(cellt);

            phraset = new Phrase();
            phraset.add(new Chunk("Amount", f5_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setVerticalAlignment(ALIGN_BOTTOM);
            cellt.setBorder(BOX);
            table3.addCell(cellt);

            phraset = new Phrase();
            phraset.add(new Chunk("#", f5_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setVerticalAlignment(ALIGN_BOTTOM);
            cellt.setBorder(BOX);
            table3.addCell(cellt);

            phraset = new Phrase();
            phraset.add(new Chunk("", f5_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setVerticalAlignment(ALIGN_BOTTOM);
            cellt.setBorder(NO_BORDER);
            table3.addCell(cellt);

            phraset = new Phrase();
            phraset.add(new Chunk("Amount", f5_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setVerticalAlignment(ALIGN_BOTTOM);
            cellt.setBorder(BOX);
            table3.addCell(cellt);

            phraset = new Phrase();
            phraset.add(new Chunk("#", f5_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setVerticalAlignment(ALIGN_BOTTOM);
            cellt.setBorder(BOX);
            table3.addCell(cellt);

            phraset = new Phrase();
            phraset.add(new Chunk("", f5_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setVerticalAlignment(ALIGN_BOTTOM);
            cellt.setBorder(NO_BORDER);
            table3.addCell(cellt);

            phraset = new Phrase();
            phraset.add(new Chunk("Amount", f5_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setVerticalAlignment(ALIGN_BOTTOM);
            cellt.setBorder(BOX);
            table3.addCell(cellt);

            phraset = new Phrase();
            phraset.add(new Chunk("#", f5_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setVerticalAlignment(ALIGN_BOTTOM);
            cellt.setBorder(BOX);
            table3.addCell(cellt);

            document.add(table3);

            table3 = new PdfPTable(21);
            table3.setWidths(columnWidths4);
            table3.setWidthPercentage(100);
            int cntvalue = 0;
            ArrayList<String> datifooter;
            if (siq != null) {
                datifooter = siq.getFooterdati();
            }else{
                return null;
            }
//            } else {
//                datifooter = siqbuy.getFooterdati();
//            }

            ArrayList<Double> totaleresidentnonresperbuy = new ArrayList<>();
            ArrayList<Double> totaleresidentnonrespersell = new ArrayList<>();
            ArrayList<Double> totaledeitotalibuy = new ArrayList<>();
            ArrayList<Double> totaledeitotalisell = new ArrayList<>();

            int cnt = 0;

            for (int n = 0; n < columnWidths4.length; n++) {

                if (n == 4 || n == 8) {
                    phraset = new Phrase();
                    phraset.add(new Chunk("", f5_bold));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setVerticalAlignment(ALIGN_BOTTOM);
                    cellt.setBorder(BOX);
                    table3.addCell(cellt);

                } else if (n == 0) {

                    phraset = new Phrase();
                    phraset.add(new Chunk("buy", f5_bold));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setVerticalAlignment(ALIGN_BOTTOM);
                    cellt.setBorder(BOX);
                    table3.addCell(cellt);

                } else if (n == 12) {
                    phraset = new Phrase();
                    phraset.add(new Chunk("yes", f5_bold));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setVerticalAlignment(ALIGN_BOTTOM);
                    cellt.setBorder(BOX);
                    table3.addCell(cellt);

                } else if (n == 15 || n == 18) {
                    phraset = new Phrase();
                    phraset.add(new Chunk("buy", f5_bold));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setVerticalAlignment(ALIGN_BOTTOM);
                    cellt.setBorder(BOX);
                    table3.addCell(cellt);

                } //else (cntvalue % 2 == 1) {                    
                else if (n == 11) {

                    double s2 = fd(datifooter.get(cntvalue - 2));
                    double s1 = fd(datifooter.get(cntvalue - 1));
                    double tot = s1 + s2;

                    totaledeitotalibuy.add(tot);
                    //    totaleresidentnonresperbuy.add(s2);
                    //  totaleresidentnonresperbuy.add(s1);

                    //devo metttere il totale
                    phraset = new Phrase();
                    phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(tot, 0)), f6_bold));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setVerticalAlignment(ALIGN_BOTTOM);
                    cellt.setBorder(NO_BORDER);
                    table3.addCell(cellt);
                } else if (n == 1) {

                    phraset = new Phrase();
                    //  phraset.add(new Chunk(formatMysqltoDisplay(String.valueOf(totaleresidentbuy1)), f6_bold));
                    phraset.add(new Chunk(formatMysqltoDisplay(valueOf(siq.getTransvalueresidentbuy())), f6_bold));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setVerticalAlignment(ALIGN_BOTTOM);
                    cellt.setBorder(NO_BORDER);
                    if (n == columnWidths4.length - 1) {
                        cellt.setBorder(RIGHT);
                    }
                    table3.addCell(cellt);

                } else if (n == 2) {

                    phraset = new Phrase();
                    //phraset.add(new Chunk(formatMysqltoDisplay(String.valueOf(totalenonresidentbuy1)), f6_bold));
                    phraset.add(new Chunk(formatMysqltoDisplay(valueOf(siq.getTransvaluenonresidentbuy())), f6_bold));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setVerticalAlignment(ALIGN_BOTTOM);
                    cellt.setBorder(NO_BORDER);
                    if (n == columnWidths4.length - 1) {
                        cellt.setBorder(RIGHT);
                    }
                    table3.addCell(cellt);

                } else if (n == 3) {

//                    double totalebuytrans1 = totaleresidentbuy1 + totalenonresidentbuy1;
                    double totalebuytrans1 = fd(siq.getTransvalueresidentbuy()) + fd(siq.getTransvaluenonresidentbuy());
                    phraset = new Phrase();
                    phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(totalebuytrans1, 2)), f6_bold));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setVerticalAlignment(ALIGN_BOTTOM);
                    cellt.setBorder(NO_BORDER);
                    if (n == columnWidths4.length - 1) {
                        cellt.setBorder(RIGHT);
                    }
                    table3.addCell(cellt);

                } else if (n == 5) {

                    phraset = new Phrase();
                    //phraset.add(new Chunk(formatMysqltoDisplay(String.valueOf(totaleresidentcommfreebuy1)), f6_bold));
                    phraset.add(new Chunk(formatMysqltoDisplay(valueOf(siq.getCommisionvaluetresidentbuy())), f6_bold));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setVerticalAlignment(ALIGN_BOTTOM);
                    cellt.setBorder(NO_BORDER);
                    if (n == columnWidths4.length - 1) {
                        cellt.setBorder(RIGHT);
                    }
                    table3.addCell(cellt);

                } else if (n == 6) {

                    phraset = new Phrase();
                    phraset.add(new Chunk(formatMysqltoDisplay(valueOf(siq.getCommisionvaluenonresidentbuy())), f6_bold));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setVerticalAlignment(ALIGN_BOTTOM);
                    cellt.setBorder(NO_BORDER);
                    if (n == columnWidths4.length - 1) {
                        cellt.setBorder(RIGHT);
                    }
                    table3.addCell(cellt);

                } else if (n == 7) {
                    double totalebuycommfee = fd(siq.getCommisionvaluetresidentbuy()) + fd(siq.getCommisionvaluenonresidentbuy());
                    phraset = new Phrase();
                    phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(totalebuycommfee, 2)), f6_bold));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setVerticalAlignment(ALIGN_BOTTOM);
                    cellt.setBorder(NO_BORDER);
                    if (n == columnWidths4.length - 1) {
                        cellt.setBorder(RIGHT);
                    }
                    table3.addCell(cellt);

                } else {

                    totaleresidentnonresperbuy.add(fd(datifooter.get(cntvalue)));
                    phraset = new Phrase();
                    phraset.add(new Chunk(formatMysqltoDisplay(datifooter.get(cntvalue)), f6_bold));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setVerticalAlignment(ALIGN_BOTTOM);
                    cellt.setBorder(NO_BORDER);
                    if (n == columnWidths4.length - 1) {
                        cellt.setBorder(RIGHT);
                    }
                    table3.addCell(cellt);
                    cntvalue++;
                }

            }

            document.add(table3);

            table3 = new PdfPTable(21);
            table3.setWidths(columnWidths4);
            table3.setWidthPercentage(100);

            for (int n = 0; n < columnWidths4.length; n++) {

                if (n == 4 || n == 8) {
                    phraset = new Phrase();
                    phraset.add(new Chunk("", f5_bold));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setVerticalAlignment(ALIGN_BOTTOM);
                    cellt.setBorder(BOX);
                    table3.addCell(cellt);
                } else if (n == 0) {

                    phraset = new Phrase();
                    phraset.add(new Chunk("sell", f5_bold));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setVerticalAlignment(ALIGN_BOTTOM);
                    cellt.setBorder(BOX);
                    table3.addCell(cellt);
                } else if (n == 12) {
                    phraset = new Phrase();
                    phraset.add(new Chunk("no", f5_bold));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setVerticalAlignment(ALIGN_BOTTOM);
                    cellt.setBorder(BOX);
                    table3.addCell(cellt);
                } else if (n == 15 || n == 18) {
                    phraset = new Phrase();
                    phraset.add(new Chunk("sell", f5_bold));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setVerticalAlignment(ALIGN_BOTTOM);
                    cellt.setBorder(BOX);
                    table3.addCell(cellt);
                } else if (n == 11) {
                    double s2 = fd(datifooter.get(cntvalue - 2));
                    double s1 = fd(datifooter.get(cntvalue - 1));
                    double tot = s1 + s2;

                    totaledeitotalisell.add(tot);
                    //  totaleresidentnonrespersell.add(s2);
                    //totaleresidentnonrespersell.add(s1);

                    //devo metttere il totale
                    phraset = new Phrase();
                    phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(tot, 0)), f6_bold));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setVerticalAlignment(ALIGN_BOTTOM);
                    cellt.setBorder(NO_BORDER);
                    table3.addCell(cellt);
                } else if (n == 1) {

                    phraset = new Phrase();
                    phraset.add(new Chunk(formatMysqltoDisplay(valueOf(siq.getTransvalueresidentsell())), f6_bold));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setVerticalAlignment(ALIGN_BOTTOM);
                    cellt.setBorder(NO_BORDER);
                    if (n == columnWidths4.length - 1) {
                        cellt.setBorder(RIGHT);
                    }
                    table3.addCell(cellt);
                } else if (n == 2) {

                    phraset = new Phrase();
                    phraset.add(new Chunk(formatMysqltoDisplay(valueOf(siq.getTransvaluenonresidentsell())), f6_bold));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setVerticalAlignment(ALIGN_BOTTOM);
                    cellt.setBorder(NO_BORDER);
                    if (n == columnWidths4.length - 1) {
                        cellt.setBorder(RIGHT);
                    }
                    table3.addCell(cellt);
                } else if (n == 3) {

                    double totaleselltrans = fd(siq.getTransvalueresidentsell()) + fd(siq.getTransvaluenonresidentsell());
                    phraset = new Phrase();
                    phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(totaleselltrans, 2)), f6_bold));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setVerticalAlignment(ALIGN_BOTTOM);
                    cellt.setBorder(NO_BORDER);
                    if (n == columnWidths4.length - 1) {
                        cellt.setBorder(RIGHT);
                    }
                    table3.addCell(cellt);
                } else if (n == 5) {

                    phraset = new Phrase();
                    phraset.add(new Chunk(formatMysqltoDisplay(valueOf(siq.getCommisionvaluetresidentsell())), f6_bold));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setVerticalAlignment(ALIGN_BOTTOM);
                    cellt.setBorder(NO_BORDER);
                    if (n == columnWidths4.length - 1) {
                        cellt.setBorder(RIGHT);
                    }
                    table3.addCell(cellt);
                } else if (n == 6) {

                    phraset = new Phrase();
                    phraset.add(new Chunk(formatMysqltoDisplay(valueOf(siq.getCommisionvaluenonresidentsell())), f6_bold));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setVerticalAlignment(ALIGN_BOTTOM);
                    cellt.setBorder(NO_BORDER);
                    if (n == columnWidths4.length - 1) {
                        cellt.setBorder(RIGHT);
                    }
                    table3.addCell(cellt);
                } else if (n == 7) {
                    double totalesellcommfee = fd(siq.getCommisionvaluenonresidentsell()) + fd(siq.getCommisionvaluetresidentsell());
                    phraset = new Phrase();
                    phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(totalesellcommfee, 2)), f6_bold));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setVerticalAlignment(ALIGN_BOTTOM);
                    cellt.setBorder(NO_BORDER);
                    if (n == columnWidths4.length - 1) {
                        cellt.setBorder(RIGHT);
                    }
                    table3.addCell(cellt);
                } else {
                    totaleresidentnonrespersell.add(fd(datifooter.get(cntvalue)));
                    phraset = new Phrase();
                    phraset.add(new Chunk(formatMysqltoDisplay(datifooter.get(cntvalue)), f6_bold));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setVerticalAlignment(ALIGN_BOTTOM);
                    cellt.setBorder(NO_BORDER);
                    if (n == columnWidths4.length - 1) {
                        cellt.setBorder(RIGHT);
                    }
                    table3.addCell(cellt);
                    cntvalue++;
                }

            }

            document.add(table3);

            table3 = new PdfPTable(21);
            table3.setWidths(columnWidths4);
            table3.setWidthPercentage(100);

            int cnttotali = 0;
            int cntresnonres = 0;

            for (int n = 0; n < columnWidths4.length; n++) {

                if (n == 4 || n == 8) {
                    phraset = new Phrase();
                    phraset.add(new Chunk("", f5_bold));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setVerticalAlignment(ALIGN_BOTTOM);
                    cellt.setBorder(BOX);
                    table3.addCell(cellt);

                } else if (n == 0) {
                    phraset = new Phrase();
                    phraset.add(new Chunk("total", f5_bold));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setVerticalAlignment(ALIGN_BOTTOM);
                    cellt.setBorder(BOX);
                    table3.addCell(cellt);
                } else if (n == 12) {
                    phraset = new Phrase();
                    phraset.add(new Chunk("total", f5_bold));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setVerticalAlignment(ALIGN_BOTTOM);
                    cellt.setBorder(BOX);
                    table3.addCell(cellt);
                } else if (n == 15 || n == 18) {
                    phraset = new Phrase();
                    phraset.add(new Chunk("total", f5_bold));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setVerticalAlignment(ALIGN_BOTTOM);
                    cellt.setBorder(BOX);
                    table3.addCell(cellt);
                } else if (n == 11) {

                    double tot1 = (double) totaledeitotalibuy.get(cnttotali);
                    double tot2 = (double) totaledeitotalisell.get(cnttotali);

                    phraset = new Phrase();
                    phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(tot1 + tot2, 0)), f6_bold));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setVerticalAlignment(ALIGN_BOTTOM);
                    cellt.setBorder(BOX);
                    if (n == columnWidths4.length - 1) {
                        cellt.setBorder(RIGHT | BOX);
                    }
                    table3.addCell(cellt);
                    cnttotali++;
                } else if (n == 1) {

                    double totaleresidentsellbuy = fd(siq.getTransvalueresidentbuy()) + fd(siq.getTransvalueresidentsell());
                    phraset = new Phrase();
                    phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(totaleresidentsellbuy, 2)), f6_bold));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setVerticalAlignment(ALIGN_BOTTOM);
                    cellt.setBorder(BOX);
                    if (n == columnWidths4.length - 1) {
                        cellt.setBorder(RIGHT);
                    }
                    table3.addCell(cellt);

                } else if (n == 2) {

                    double totalenonresidentbuysell = fd(siq.getTransvaluenonresidentbuy()) + fd(siq.getTransvaluenonresidentsell());
                    phraset = new Phrase();
                    phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(totalenonresidentbuysell, 2)), f6_bold));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setVerticalAlignment(ALIGN_BOTTOM);
                    cellt.setBorder(BOX);
                    if (n == columnWidths4.length - 1) {
                        cellt.setBorder(RIGHT);
                    }
                    table3.addCell(cellt);

                } else if (n == 3) {

                    double totalebuysellgenerale = fd(siq.getTransvaluenonresidentbuy()) + fd(siq.getTransvaluenonresidentsell()) + fd(siq.getTransvalueresidentbuy()) + fd(siq.getTransvalueresidentsell());
                    phraset = new Phrase();
                    phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(totalebuysellgenerale, 2)), f6_bold));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setVerticalAlignment(ALIGN_BOTTOM);
                    cellt.setBorder(BOX);
                    if (n == columnWidths4.length - 1) {
                        cellt.setBorder(RIGHT);
                    }
                    table3.addCell(cellt);

                } else if (n == 5) {

                    double totaleresidentcommfeebuysell = fd(siq.getCommisionvaluetresidentbuy()) + fd(siq.getCommisionvaluetresidentsell());
                    phraset = new Phrase();
                    phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(totaleresidentcommfeebuysell, 2)), f6_bold));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setVerticalAlignment(ALIGN_BOTTOM);
                    cellt.setBorder(BOX);
                    if (n == columnWidths4.length - 1) {
                        cellt.setBorder(RIGHT);
                    }
                    table3.addCell(cellt);

                } else if (n == 6) {
                    double totalenonresidentcommfeebuysell = fd(siq.getCommisionvaluenonresidentbuy()) + fd(siq.getCommisionvaluenonresidentsell());
                    phraset = new Phrase();
                    phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(totalenonresidentcommfeebuysell, 2)), f6_bold));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setVerticalAlignment(ALIGN_BOTTOM);
                    cellt.setBorder(BOX);
                    if (n == columnWidths4.length - 1) {
                        cellt.setBorder(RIGHT);
                    }
                    table3.addCell(cellt);
                } else if (n == 7) {
                    double totalegeneralecommfee = fd(siq.getCommisionvaluenonresidentbuy()) + fd(siq.getCommisionvaluenonresidentsell()) + fd(siq.getCommisionvaluetresidentbuy()) + fd(siq.getCommisionvaluetresidentsell());
                    phraset = new Phrase();
                    phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(totalegeneralecommfee, 2)), f6_bold));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setVerticalAlignment(ALIGN_BOTTOM);
                    cellt.setBorder(BOX);
                    if (n == columnWidths4.length - 1) {
                        cellt.setBorder(RIGHT);
                    }
                    table3.addCell(cellt);
                } else {
                    double tot3 = (double) (totaleresidentnonresperbuy.get(cntresnonres));
                    double tot4 = (double) (totaleresidentnonrespersell.get(cntresnonres));
                    phraset = new Phrase();
                    if (n == 9 || n == 10 || n == 14 || n == 17 || n == 20) {
                        phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(tot3 + tot4, 0)), f6_bold));
                    } else {
                        phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(tot3 + tot4, 2)), f6_bold));
                    }
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setVerticalAlignment(ALIGN_BOTTOM);
                    cellt.setBorder(BOX);
                    if (n == columnWidths4.length - 1) {
                        cellt.setBorder(RIGHT | BOX);
                    }
                    table3.addCell(cellt);
                    cntresnonres++;
                }
            }
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
     * @param siq
     * @param datereport1
     * @param datereport2
     * @param intestazionePdf
     * @return
     */
    public String receiptexcel(String path, TillTransactionListBB_value siq, String datereport1, String datereport2, String intestazionePdf) {

        //   String outputfile = "TillTransactionList.pdf";
        try {
            File pdf = new File(path + generaId(50) + "TillTransactionList.xlsx");
            
            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet("TillTransactionList");
            //CREAZIONE FONT
            XSSFFont font = workbook.createFont();
            font.setFontName(FONT_ARIAL);
            font.setFontHeightInPoints((short)12);
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
            style3.setAlignment(HorizontalAlignment.RIGHT);
            style3.setBorderTop(THIN);
            style3.setBorderBottom(THIN);

            XSSFCellStyle style3left = (XSSFCellStyle) workbook.createCellStyle();
            style3left.setFont(font3);
            style3left.setAlignment(LEFT);
            style3left.setBorderTop(THIN);
            style3left.setBorderBottom(THIN);

            XSSFFont font4 = workbook.createFont();
            font4.setFontName(FONT_ARIAL);
            font4.setFontHeightInPoints((short) 10);

            XSSFCellStyle style4 = (XSSFCellStyle) workbook.createCellStyle();
            style4.setAlignment(HorizontalAlignment.RIGHT);
            style4.setBorderTop(THIN);
            style4.setBorderBottom(THIN);

            XSSFCellStyle style4left = (XSSFCellStyle) workbook.createCellStyle();
            style4left.setAlignment(LEFT);
            style4left.setBorderTop(THIN);
            style4left.setBorderBottom(THIN);

            Row rowP = sheet.createRow(1);

            Cell cl = rowP.createCell(1);
            sheet.addMergedRegion(new CellRangeAddress(1,1,1,6));
            cl.setCellStyle(style1);
            
            
            
            cl.setCellValue(intestazionePdf + " From " + datereport1 + " To " + datereport2);

//            Cell cl2 = rowP.createCell(6);
//            cl2.setCellStyle(style2);
//            cl2.setCellValue(datereport1);
            Row row = sheet.createRow(3);
            row.createCell(1).setCellValue(siq.getId_filiale() + " " + siq.getDe_filiale());

            ArrayList<TillTransactionListBB_value> dati = siq.getDati();

            Row row6 = sheet.createRow( 5);

            Cell f2 = row6.createCell(1);
            f2.setCellStyle(style3left);
            f2.setCellValue("Type");

            Cell f3 = row6.createCell(2);
            f3.setCellStyle(style3);
            f3.setCellValue("Till");

            Cell f4 = row6.createCell(3);
            f4.setCellStyle(style3);
            f4.setCellValue("User");
            
             Cell f4bb = row6.createCell(4);
            f4bb.setCellStyle(style3);
            f4bb.setCellValue("Branch ID");

            Cell f5 = row6.createCell(5);
            f5.setCellStyle(style3);
            f5.setCellValue("No.Tr.");

            Cell f6 = row6.createCell(6);
            f6.setCellStyle(style3left);
            f6.setCellValue("Date / Time");

            Cell f7 = row6.createCell(7);
            f7.setCellStyle(style3left);
            f7.setCellValue("Cur");

            Cell f8 = row6.createCell(8);
            f8.setCellStyle(style3left);
            f8.setCellValue("Kind");

            Cell f9 = row6.createCell(9);
            f9.setCellStyle(style3);
            f9.setCellValue("Amount");

            Cell f10 = row6.createCell(10);
            f10.setCellStyle(style3);
            f10.setCellValue("Rate");

            Cell f11 = row6.createCell(11);
            f11.setCellStyle(style3);
            f11.setCellValue("Total");

            Cell f12 = row6.createCell(12);
            f12.setCellStyle(style3);
            f12.setCellValue("%");

            Cell f13 = row6.createCell(13);
            f13.setCellStyle(style3);
            f13.setCellValue("Comm.Fee");

            Cell f13bis = row6.createCell(14);
            f13bis.setCellStyle(style3);
            f13bis.setCellValue("Round");

            Cell f14 = row6.createCell(15);
            f14.setCellStyle(style3);
            f14.setCellValue("Pay In / Pay Out");

            Cell f15 = row6.createCell(16);
            f15.setCellStyle(style3left);
            f15.setCellValue("Customer");

            Cell f16 = row6.createCell(17);
            f16.setCellStyle(style3);
            f16.setCellValue("Spread");

            Cell f17 = row6.createCell(18);
            f17.setCellStyle(style3);
            f17.setCellValue("Pos / Bank Acc");

            double totaleresidentbuy = 0;
            double totalenonresidentbuy = 0;
            double totaleresidentcommfreebuy = 0;
            double totalenonresidentcommfreebuy = 0;

            double totaleresidentsell = 0;
            double totalenonresidentsell = 0;
            double totaleresidentcommfreesell = 0;
            double totalenonresidentcommfreesell = 0;

            double totalepayinoutgeneral = 0;

            double totround = 0;
            double totcommfee = 0;

            int cntriga = 7;

            for (int i = 0; i < dati.size(); i++) {

                cntriga++;
                XSSFRow row7 = sheet.createRow(cntriga);

                TillTransactionListBB_value actual = dati.get(i);
                
                Cell f18 = row7.createCell(1);
                f18.setCellStyle(style4left);
                f18.setCellValue(actual.getType());

                Cell f19 = row7.createCell(2);
                f19.setCellStyle(style4);
                f19.setCellValue(actual.getTill());

                Cell f20 = row7.createCell(3);
                f20.setCellStyle(style4);
                f20.setCellValue(actual.getUser());
                
                 Cell f204 = row7.createCell(4);
                f204.setCellStyle(style4);
                f204.setCellValue(actual.getId_filiale());

                Cell f21 = row7.createCell(5);
                f21.setCellStyle(style4);
                f21.setCellValue(actual.getNotr());

                Cell f22 = row7.createCell(6);
                f22.setCellStyle(style4left);
                f22.setCellValue(formatStringtoStringDate(actual.getTime(), patternsqldate, patternnormdate));

                Cell f23 = row7.createCell(7);
                f23.setCellStyle(style4left);
                f23.setCellValue(actual.getCur());

                Cell f24 = row7.createCell(8);
                f24.setCellStyle(style4left);
                f24.setCellValue(actual.getKind());

                if (actual.getKind().contains("Buy")) {
                    if (actual.getResidentnonresident().equalsIgnoreCase("Resident")) {
                        totaleresidentbuy += fd(actual.getTotalSenzaFormattazione());
                        totaleresidentcommfreebuy += fd(actual.getComfreeSenzaFormattazione());

                    } else {
                        totalenonresidentcommfreebuy += fd(actual.getComfreeSenzaFormattazione());
                        totalenonresidentbuy += fd(actual.getTotalSenzaFormattazione());
                    }
                }
                if (actual.getKind().contains("Sell")) {
                    if (actual.getResidentnonresident().equalsIgnoreCase("Resident")) {

                        totaleresidentsell += fd(actual.getTotalSenzaFormattazione());
                        totaleresidentcommfreesell += fd(actual.getComfreeSenzaFormattazione());
                    } else {
                        totalenonresidentsell += fd(actual.getTotalSenzaFormattazione());

                        totalenonresidentcommfreesell += fd(actual.getComfreeSenzaFormattazione());
                    }
                }

                Cell f25 = row7.createCell(9);
                f25.setCellStyle(style4);
                f25.setCellValue(formatMysqltoDisplay(actual.getAmount()));

                Cell f26 = row7.createCell(10);
                f26.setCellStyle(style4);
                f26.setCellValue(formatMysqltoDisplay(actual.getRate()));

                Cell f27 = row7.createCell(11);
                f27.setCellStyle(style4);
                f27.setCellValue(formatMysqltoDisplay(actual.getTotal()));

                Cell f28 = row7.createCell(12);
                f28.setCellStyle(style4);
                f28.setCellValue(formatMysqltoDisplay(actual.getPerc()));

                Cell f29 = row7.createCell(13);
                f29.setCellStyle(style4);
                f29.setCellValue(formatMysqltoDisplay(actual.getComfree()));

                totcommfee = totcommfee + ((fd(actual.getComfree())));

                Cell f29bis = row7.createCell(14);
                f29bis.setCellStyle(style4);
                f29bis.setCellValue(actual.getRound());

                totround = totround + ((fd(actual.getRound())));
                totround = roundDouble(totround, 2);

                Cell f30 = row7.createCell(15);
                f30.setCellStyle(style4);

                if (actual.getKind().contains("S")) {
                    f30.setCellValue("+" + formatMysqltoDisplay(actual.getPayinpayout()));

                    totalepayinoutgeneral += fd(actual.getPayinpayoutSenzaFormattazione());
                } else {

                    f30.setCellValue("-" + formatMysqltoDisplay(actual.getPayinpayout()));

                    totalepayinoutgeneral -= fd(actual.getPayinpayoutSenzaFormattazione());
                }

                Cell f31 = row7.createCell(16);
                f31.setCellStyle(style4left);
                f31.setCellValue(actual.getCustomer());

                Cell f32 = row7.createCell(17);
                f32.setCellStyle(style4);
                f32.setCellValue(formatMysqltoDisplay(actual.getSpread()));

                Cell f33 = row7.createCell(18);
                f33.setCellStyle(style4);
                f33.setCellValue(actual.getPos());

                if (intestazionePdf.contains("Delete")) {
                    cntriga++;
                    Row row8 = sheet.createRow(cntriga);
                    Cell f331 = row8.createCell(16);
                    f331.setCellStyle(style4left);
                    f331.setCellValue(actual.getDelete1());

                    Cell f332 = row8.createCell(17);
                    f332.setCellStyle(style4left);
                    f332.setCellValue(actual.getDelete2());

                }

                cntriga++;
                cntriga++;

            }

            cntriga++;
            XSSFRow row8 = sheet.createRow(cntriga);

            if (dati.size() > 0) {

                for (int v = 0; v < columnWidths2.length; v++) {
                    if (v == 9) {
                        double totalegenerale = totaleresidentbuy + totaleresidentsell + totalenonresidentbuy + totalenonresidentsell;

                        Cell f34 = row8.createCell(9 + 1);
                        f34.setCellStyle(style3);
                        f34.setCellValue((formatMysqltoDisplay(roundDoubleandFormat(totalegenerale, 2))));

                    } else if (v == 12 + 1) {

                        Cell f35 = row8.createCell(12);
                        f35.setCellStyle(style3);
                        f35.setCellValue((formatMysqltoDisplay(roundDoubleandFormat(totcommfee, 2))));

                    } else if (v == 13 + 1) {

                        Cell f35 = row8.createCell(13);
                        f35.setCellStyle(style3);
                        f35.setCellValue((formatMysqltoDisplay(roundDoubleandFormat(totround, 2))));

                    } else if (v == 14 + 1) {

                        Cell f36 = row8.createCell(14);
                        f36.setCellStyle(style3);
                        f36.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(totalepayinoutgeneral, 2)));

                    } else {

                        Cell f37 = row8.createCell(v + 1);
                        f37.setCellStyle(style3);
                        f37.setCellValue("");

                    }

                }
            }

//            ///BUY////////////
//            dati = siq.getDati();
//
//            double totaleresidentbuy1 = 0;
//            double totalenonresidentbuy1 = 0;
//            double totaleresidentcommfreebuy1 = 0;
//            double totalenonresidentcommfreebuy1 = 0;
//
//            double totaleresidentsell1 = 0;
//            double totalenonresidentsell1 = 0;
//            double totaleresidentcommfreesell1 = 0;
//            double totalenonresidentcommfreesell1 = 0;
//
//            double totalepayinoutgeneral1 = 0;
//
//            double totround1 = 0;
//            double totcommfee1 = 0;
//
//            //int cntriga = 7;
//            cntriga++;
//            cntriga++;
//            cntriga++;
//            cntriga++;
//            cntriga++;
//
//            for (int i = 0; i < dati.size(); i++) {
//
//                cntriga++;
//                Row row7 = sheet.createRow((short) cntriga);
//
//                TillTransactionListBB_value actual = (TillTransactionListBB_value) dati.get(i);
//                TillTransactionListBB_value prossimo = (TillTransactionListBB_value) dati.get(i);
//
//                Cell f18 = row7.createCell(1);
//                f18.setCellStyle(style4left);
//                f18.setCellValue(actual.getType());
//
//                Cell f19 = row7.createCell(2);
//                f19.setCellStyle(style4);
//                f19.setCellValue(actual.getTill());
//
//                Cell f20 = row7.createCell(3);
//                f20.setCellStyle(style4);
//                f20.setCellValue(actual.getUser());
//
//                Cell f21 = row7.createCell(4);
//                f21.setCellStyle(style4);
//                f21.setCellValue(actual.getNotr());
//
//                Cell f22 = row7.createCell(5);
//                f22.setCellStyle(style4left);
//                f22.setCellValue(Utility.formatStringtoStringDate(actual.getTime(), Constant.patternsqldate, Constant.patternnormdate));
//
//                Cell f23 = row7.createCell(6);
//                f23.setCellStyle(style4left);
//                f23.setCellValue(actual.getCur());
//
//                Cell f24 = row7.createCell(7);
//                f24.setCellStyle(style4left);
//                f24.setCellValue(actual.getKind());
//
//                if (actual.getKind().contains("Buy")) {
//                    if (actual.getResidentnonresident().equalsIgnoreCase("Resident")) {
//                        totaleresidentbuy1 += fd(actual.getTotalSenzaFormattazione());
//                        totaleresidentcommfreebuy1 += fd(actual.getComfreeSenzaFormattazione());
//
//                    } else {
//                        totalenonresidentcommfreebuy1 += fd(actual.getComfreeSenzaFormattazione());
//                        totalenonresidentbuy1 += fd(actual.getTotalSenzaFormattazione());
//                    }
//                }
//                if (actual.getKind().contains("Sell")) {
//                    if (actual.getResidentnonresident().equalsIgnoreCase("Resident")) {
//
//                        totaleresidentsell1 += fd(actual.getTotalSenzaFormattazione());
//                        totaleresidentcommfreesell1 += fd(actual.getComfreeSenzaFormattazione());
//                    } else {
//                        totalenonresidentsell1 += fd(actual.getTotalSenzaFormattazione());
//                        totalenonresidentcommfreesell1 += fd(actual.getComfreeSenzaFormattazione());
//                    }
//                }
//
//                Cell f25 = row7.createCell(8);
//                f25.setCellStyle(style4);
//                f25.setCellValue(Utility.formatMysqltoDisplay(actual.getAmount()));
//
//                Cell f26 = row7.createCell(9);
//                f26.setCellStyle(style4);
//                f26.setCellValue(Utility.formatMysqltoDisplay(actual.getRate()));
//
//                Cell f27 = row7.createCell(10);
//                f27.setCellStyle(style4);
//                f27.setCellValue(Utility.formatMysqltoDisplay(actual.getTotal()));
//
//                Cell f28 = row7.createCell(11);
//                f28.setCellStyle(style4);
//                f28.setCellValue(Utility.formatMysqltoDisplay(actual.getPerc()));
//
//                Cell f29 = row7.createCell(12);
//                f29.setCellStyle(style4);
//                f29.setCellValue(Utility.formatMysqltoDisplay(actual.getComfree()));
//
//                totcommfee1 = totcommfee1 + fd((String.valueOf(actual.getComfree())));
//
//                Cell f29bis = row7.createCell(13);
//                f29bis.setCellStyle(style4);
//                f29bis.setCellValue(Utility.formatMysqltoDisplay(actual.getRound()));
//
//                totround1 = totround1 + fd((String.valueOf(actual.getRound())));
//                totround1 = Utility.roundDouble(totround1, 2);
//
//                Cell f30 = row7.createCell(14);
//                f30.setCellStyle(style4);
//
//                if (actual.getKind().startsWith("S")) {
//                    f30.setCellValue("+" + Utility.formatMysqltoDisplay(actual.getPayinpayout()));
//
//                    totalepayinoutgeneral1 += fd(actual.getPayinpayoutSenzaFormattazione());
//                } else  if (actual.getKind().startsWith("B")) {
//
//                    f30.setCellValue("-" + Utility.formatMysqltoDisplay(actual.getPayinpayout()));
//
//                    totalepayinoutgeneral1 -= fd(actual.getPayinpayoutSenzaFormattazione());
//                }
//                else   {
//
//                    f30.setCellValue(" ");
//
//                    
//                }
//
//                Cell f31 = row7.createCell(15);
//                f31.setCellStyle(style4left);
//                f31.setCellValue(actual.getCustomer());
//
//                Cell f32 = row7.createCell(16);
//                f32.setCellStyle(style4);
//                f32.setCellValue(Utility.formatMysqltoDisplay(actual.getSpread()));
//
//                Cell f33 = row7.createCell(17);
//                f33.setCellStyle(style4);
//                f33.setCellValue(actual.getPos());
//
//                if (intestazionePdf.contains("Delete")) {
//                    cntriga++;
//                    row8 = sheet.createRow((short) cntriga);
//                    Cell f331 = row8.createCell(15);
//                    f331.setCellStyle(style4left);
//                    f331.setCellValue(actual.getDelete1());
//
//                    Cell f332 = row8.createCell(16);
//                    f332.setCellStyle(style4left);
//                    f332.setCellValue(actual.getDelete2());
//
//                }
//
//                cntriga++;
//                cntriga++;
//
//            }
//
//            if (dati.size() > 0) {
//
//                cntriga++;
//                row8 = sheet.createRow((short) cntriga);
//
//                for (int v = 0; v < columnWidths2.length; v++) {
//                    if (v == 9) {
//                        double totalegenerale1 = totaleresidentbuy1 + totaleresidentsell1 + totalenonresidentbuy1 + totalenonresidentsell1;
//
//                        Cell f34 = row8.createCell(9 + 1);
//                        f34.setCellStyle(style3);
//                        f34.setCellValue((formatMysqltoDisplay(Utility.roundDoubleandFormat(totalegenerale1, 2))));
//
//                    } else if (v == 12 + 1) {
//
//                        Cell f35 = row8.createCell(12);
//                        f35.setCellStyle(style3);
//                        f35.setCellValue((formatMysqltoDisplay(Utility.roundDoubleandFormat(totcommfee1, 2))));
//
//                    } else if (v == 13 + 1) {
//
//                        Cell f35 = row8.createCell(13);
//                        f35.setCellStyle(style3);
//                        f35.setCellValue((formatMysqltoDisplay(Utility.roundDoubleandFormat(totround1, 2))));
//
//                    } else if (v == 14 + 1) {
//
//                        Cell f36 = row8.createCell(14);
//                        f36.setCellStyle(style3);
//                        f36.setCellValue(formatMysqltoDisplay(Utility.roundDoubleandFormat(totalepayinoutgeneral1, 2)));
//
//                    } else {
//
//                        Cell f37 = row8.createCell(v + 1);
//                        f37.setCellStyle(style3);
//                        f37.setCellValue("");
//
//                    }
//
//                }
//            }
//
//            //FINE BUY  ///////7
            cntriga++;
            cntriga++;
            cntriga++;
            cntriga++;
            Row row9 = sheet.createRow(cntriga);

            Cell f38 = row9.createCell(3);
            f38.setCellStyle(style3);
            f38.setCellValue("Transaction value");

            Cell f41 = row9.createCell(7);
            f41.setCellStyle(style3);
            f41.setCellValue("Commission Value");

            Cell f42 = row9.createCell(14);
            f42.setCellStyle(style3);
            f42.setCellValue("Transacion Number");

            Cell f421 = row9.createCell(11);
            f421.setCellStyle(style3);
            f421.setCellValue("Sell / Internet Booking");

            Cell f422 = row9.createCell(17);
            f422.setCellStyle(style3);
            f422.setCellValue("POS");

            Cell f4221 = row9.createCell(21);
            f4221.setCellStyle(style3);
            f4221.setCellValue("Bank Account");

            cntriga++;
            Row row10 = sheet.createRow(cntriga);

            Cell f43 = row10.createCell(2);
            f43.setCellStyle(style3);
            f43.setCellValue("Resident");

            Cell f44 = row10.createCell(3);
            f44.setCellStyle(style3);
            f44.setCellValue("Non resident");

            Cell f45 = row10.createCell(4);
            f45.setCellStyle(style3);
            f45.setCellValue("Total");

            Cell f46 = row10.createCell(6);
            f46.setCellStyle(style3);
            f46.setCellValue("Resident");

            Cell f47 = row10.createCell(7);
            f47.setCellStyle(style3);
            f47.setCellValue("Non resident");

            Cell f48 = row10.createCell(8);
            f48.setCellStyle(style3);
            f48.setCellValue("Total");

            Cell f49 = row10.createCell(10);
            f49.setCellStyle(style3);
            f49.setCellValue("Resident");

            Cell f50 = row10.createCell(11);
            f50.setCellStyle(style3);
            f50.setCellValue("Non resident");

            Cell f51 = row10.createCell(12);
            f51.setCellStyle(style3);
            f51.setCellValue("Total");

            Cell f52 = row10.createCell(14);
            f52.setCellStyle(style3);
            f52.setCellValue("Amount");

            Cell f53 = row10.createCell(15);
            f53.setCellStyle(style3);
            f53.setCellValue("#");

            Cell f54 = row10.createCell(17);
            f54.setCellStyle(style3);
            f54.setCellValue("Amount");

            Cell f55 = row10.createCell(18);
            f55.setCellStyle(style3);
            f55.setCellValue("#");

            Cell f541 = row10.createCell(20);
            f541.setCellStyle(style3);
            f541.setCellValue("Amount");

            Cell f551 = row10.createCell(21);
            f551.setCellStyle(style3);
            f551.setCellValue("#");

            cntriga++;
            Row row11 = sheet.createRow(cntriga);

            int cntvalue = 0;

            ArrayList<String> datifooter = siq.getFooterdati();

            ArrayList<Double> totaleresidentnonresperbuy = new ArrayList<>();
            ArrayList<Double> totaleresidentnonrespersell = new ArrayList<>();
            ArrayList<Double> totaledeitotalibuy = new ArrayList<>();
            ArrayList<Double> totaledeitotalisell = new ArrayList<>();

            int cnt = 0;

            for (int n = 0; n < columnWidths4.length; n++) {

                if (n == 4 || n == 8) {

//                    
//                    phraset = new Phrase();
//                    phraset.add(new Chunk("", f5_bold));
//                    cellt = new PdfPCell(phraset);
//                    cellt.setHorizontalAlignment(Element.ALIGN_RIGHT);
//                    cellt.setVerticalAlignment(Element.ALIGN_BOTTOM);
//                    cellt.setBorder(Rectangle.BOX);
//                    table3.addCell(cellt);
                } else if (n == 0) {

                    Cell f56 = row11.createCell(n + 1);
                    f56.setCellStyle(style3);
                    f56.setCellValue("buy");

                } else if (n == 12) {

                    Cell f56 = row11.createCell(n + 1);
                    f56.setCellStyle(style3);
                    f56.setCellValue("yes");

                } else if (n == 15 || n == 18) {

                    Cell f56 = row11.createCell(n + 1);
                    f56.setCellStyle(style3);
                    f56.setCellValue("buy");

                } //else (cntvalue % 2 == 1) {                    
                else if (n == 11) {

                    double s2 = fd(datifooter.get(cntvalue - 2));
                    double s1 = fd(datifooter.get(cntvalue - 1));
                    double tot = s1 + s2;

                    totaledeitotalibuy.add(tot);
                    //    totaleresidentnonresperbuy.add(s2);
                    //  totaleresidentnonresperbuy.add(s1);

                    Cell f56 = row11.createCell(n + 1);
                    f56.setCellStyle(style4);
                    f56.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(tot, 0)));

                } else if (n == 1) {

                    Cell f56 = row11.createCell(n + 1);
                    f56.setCellStyle(style4);
                    f56.setCellValue(formatMysqltoDisplay(valueOf(siq.getTransvalueresidentbuy())));

                } else if (n == 2) {

                    Cell f56 = row11.createCell(n + 1);
                    f56.setCellStyle(style4);
                    f56.setCellValue(formatMysqltoDisplay(valueOf(siq.getTransvaluenonresidentbuy())));

                } else if (n == 3) {

                    double totalebuytrans = fd(siq.getTransvaluenonresidentbuy()) + fd(siq.getTransvalueresidentbuy());

                    Cell f56 = row11.createCell(n + 1);
                    f56.setCellStyle(style4);
                    f56.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(totalebuytrans, 2)));

                } else if (n == 5) {

                    Cell f56 = row11.createCell(n + 1);
                    f56.setCellStyle(style4);
                    f56.setCellValue(formatMysqltoDisplay(valueOf(siq.getCommisionvaluetresidentbuy())));

                } else if (n == 6) {

                    Cell f56 = row11.createCell(n + 1);
                    f56.setCellStyle(style4);
                    f56.setCellValue(formatMysqltoDisplay(valueOf(siq.getCommisionvaluenonresidentbuy())));

                } else if (n == 7) {
                    double totalebuycommfee = fd(siq.getCommisionvaluenonresidentbuy()) + fd(siq.getCommisionvaluetresidentbuy());

                    Cell f56 = row11.createCell(n + 1);
                    f56.setCellStyle(style4);
                    f56.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(totalebuycommfee, 2)));

                } else {

                    totaleresidentnonresperbuy.add(fd(datifooter.get(cntvalue)));

                    Cell f56 = row11.createCell(n + 1);
                    f56.setCellStyle(style4);
                    f56.setCellValue(formatMysqltoDisplay(datifooter.get(cntvalue)));

                    cntvalue++;
                }

            }

            cntriga++;
            Row row12 = sheet.createRow(cntriga);

            for (int n = 0; n < columnWidths4.length; n++) {

                if (n == 4 || n == 8) {
//                    phraset = new Phrase();
//                    phraset.add(new Chunk("", f5_bold));
//                    cellt = new PdfPCell(phraset);
//                    cellt.setHorizontalAlignment(Element.ALIGN_RIGHT);
//                    cellt.setVerticalAlignment(Element.ALIGN_BOTTOM);
//                    cellt.setBorder(Rectangle.BOX);
//                    table3.addCell(cellt);
                } else if (n == 0) {

                    Cell f56 = row12.createCell(n + 1);
                    f56.setCellStyle(style3);
                    f56.setCellValue("sell");

                } else if (n == 12) {

                    Cell f56 = row12.createCell(n + 1);
                    f56.setCellStyle(style3);
                    f56.setCellValue("no");

                } else if (n == 15 || n == 18) {

                    Cell f56 = row12.createCell(n + 1);
                    f56.setCellStyle(style3);
                    f56.setCellValue("sell");

                } else if (n == 11) {
                    double s2 = fd(datifooter.get(cntvalue - 2));
                    double s1 = fd(datifooter.get(cntvalue - 1));
                    double tot = s1 + s2;

                    totaledeitotalisell.add(tot);
                    //  totaleresidentnonrespersell.add(s2);
                    //totaleresidentnonrespersell.add(s1);

                    Cell f56 = row12.createCell(n + 1);
                    f56.setCellStyle(style4);
                    f56.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(tot, 0)));

                } else if (n == 1) {

                    Cell f56 = row12.createCell(n + 1);
                    f56.setCellStyle(style4);
                    f56.setCellValue(formatMysqltoDisplay(valueOf(siq.getTransvalueresidentsell())));

                } else if (n == 2) {

                    Cell f56 = row12.createCell(n + 1);
                    f56.setCellStyle(style4);
                    f56.setCellValue(formatMysqltoDisplay(valueOf(siq.getTransvaluenonresidentsell())));

                } else if (n == 3) {

                    double totaleselltrans = fd(siq.getCommisionvaluetresidentsell()) + fd(siq.getCommisionvaluenonresidentsell());
                    Cell f56 = row12.createCell(n + 1);
                    f56.setCellStyle(style4);
                    f56.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(totaleselltrans, 2)));

                } else if (n == 5) {

                    Cell f56 = row12.createCell(n + 1);
                    f56.setCellStyle(style4);
                    f56.setCellValue(formatMysqltoDisplay(valueOf(siq.getCommisionvaluetresidentsell())));

                } else if (n == 6) {

                    Cell f56 = row12.createCell(n + 1);
                    f56.setCellStyle(style4);
                    f56.setCellValue(formatMysqltoDisplay(valueOf(siq.getCommisionvaluenonresidentsell())));

                } else if (n == 7) {
                    double totalesellcommfee = fd(siq.getCommisionvaluenonresidentsell()) + fd(siq.getCommisionvaluetresidentsell());

                    Cell f56 = row12.createCell(n + 1);
                    f56.setCellStyle(style4);
                    f56.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(totalesellcommfee, 2)));

                } else {
                    totaleresidentnonrespersell.add(fd(datifooter.get(cntvalue)));

                    Cell f56 = row12.createCell(n + 1);
                    f56.setCellStyle(style4);
                    f56.setCellValue(formatMysqltoDisplay(datifooter.get(cntvalue)));

                    cntvalue++;
                }

            }

            cntriga++;
            Row row13 = sheet.createRow(cntriga);

            int cnttotali = 0;
            int cntresnonres = 0;

            for (int n = 0; n < columnWidths4.length; n++) {

                if (n == 4 || n == 8) {
//                    phraset = new Phrase();
//                    phraset.add(new Chunk("", f5_bold));
//                    cellt = new PdfPCell(phraset);
//                    cellt.setHorizontalAlignment(Element.ALIGN_RIGHT);
//                    cellt.setVerticalAlignment(Element.ALIGN_BOTTOM);
//                    cellt.setBorder(Rectangle.BOX);
//                    table3.addCell(cellt);

                } else if (n == 0) {

                    Cell f56 = row13.createCell(n + 1);
                    f56.setCellStyle(style3);
                    f56.setCellValue("total");

                } else if (n == 12) {

                    Cell f56 = row13.createCell(n + 1);
                    f56.setCellStyle(style3);
                    f56.setCellValue("total");

                } else if (n == 15 || n == 18) {

                    Cell f56 = row13.createCell(n + 1);
                    f56.setCellStyle(style3);
                    f56.setCellValue("total");

                } else if (n == 11) {

                    double tot1 = (double) totaledeitotalibuy.get(cnttotali);
                    double tot2 = (double) totaledeitotalisell.get(cnttotali);

                    Cell f56 = row13.createCell(n + 1);
                    f56.setCellStyle(style4);
                    f56.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(tot1 + tot2, 0)));

                    cnttotali++;
                } else if (n == 1) {

                    double totaleresidentsellbuy = fd(siq.getTransvalueresidentbuy()) + fd(siq.getTransvalueresidentsell());

                    Cell f56 = row13.createCell(n + 1);
                    f56.setCellStyle(style4);
                    f56.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(totaleresidentsellbuy, 2)));

                } else if (n == 2) {

                    double totalenonresidentbuysell = fd(siq.getTransvaluenonresidentbuy()) + fd(siq.getTransvaluenonresidentsell());

                    Cell f56 = row13.createCell(n + 1);
                    f56.setCellStyle(style4);
                    f56.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(totalenonresidentbuysell, 2)));

                } else if (n == 3) {

                    double totalebuysellgenerale = fd(siq.getTransvaluenonresidentbuy()) + fd(siq.getTransvaluenonresidentsell()) + fd(siq.getTransvalueresidentbuy()) + fd(siq.getTransvalueresidentsell());

                    Cell f56 = row13.createCell(n + 1);
                    f56.setCellStyle(style4);
                    f56.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(totalebuysellgenerale, 2)));

                } else if (n == 5) {

                    double totaleresidentcommfeebuysell = fd(siq.getCommisionvaluetresidentbuy()) + fd(siq.getCommisionvaluetresidentsell());

                    Cell f56 = row13.createCell(n + 1);
                    f56.setCellStyle(style4);
                    f56.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(totaleresidentcommfeebuysell, 2)));

                } else if (n == 6) {
                    double totalenonresidentcommfeebuysell = fd(siq.getCommisionvaluenonresidentbuy()) + fd(siq.getCommisionvaluenonresidentsell());

                    Cell f56 = row13.createCell(n + 1);
                    f56.setCellStyle(style4);
                    f56.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(totalenonresidentcommfeebuysell, 2)));

                } else if (n == 7) {
                    double totalegeneralecommfee = fd(siq.getCommisionvaluenonresidentbuy()) + fd(siq.getCommisionvaluenonresidentsell()) + fd(siq.getCommisionvaluetresidentbuy()) + fd(siq.getCommisionvaluetresidentsell());

                    Cell f56 = row13.createCell(n + 1);
                    f56.setCellStyle(style4);
                    f56.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(totalegeneralecommfee, 2)));

                } else {
                    double tot3 = (double) (totaleresidentnonresperbuy.get(cntresnonres));
                    double tot4 = (double) (totaleresidentnonrespersell.get(cntresnonres));

                    Cell f56 = row13.createCell(n + 1);
                    f56.setCellStyle(style4);
                    if (n == 9 || n == 10 || n == 14 || n == 17 || n == 20) {
                        f56.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(tot3 + tot4, 0)));
                    } else {
                        f56.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(tot3 + tot4, 2)));
                    }

                    cntresnonres++;
                }
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
