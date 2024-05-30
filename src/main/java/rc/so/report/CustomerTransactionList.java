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
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import static org.apache.poi.hssf.usermodel.HSSFFont.FONT_ARIAL;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import static org.apache.poi.ss.usermodel.BorderStyle.THIN;
import static org.apache.poi.ss.usermodel.CellType.NUMERIC;
import static org.apache.poi.ss.usermodel.HorizontalAlignment.LEFT;
import static org.apache.poi.ss.usermodel.HorizontalAlignment.RIGHT;

/**
 *
 * @author rcosco
 */
public class CustomerTransactionList {

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
    public static final float[] columnWidths2 = new float[]{4f, 3f, 3f, 4f, 8f, 8f, 6f, 6f, 6f, 6f, 6.5f, 3f, 5f, 5f, 5f, 5f, 7f};

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
     ** Constructor
     */
    public CustomerTransactionList() {

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
            PdfPTable table2 = new PdfPTable(17);
            table2.setWidths(columnWidths2);
            table2.setWidthPercentage(100);

            Phrase phraset2 = new Phrase();
            phraset2.add(new Chunk("Branch", f5_bold));
            PdfPCell cellt2 = new PdfPCell(phraset2);
            cellt2.setHorizontalAlignment(ALIGN_RIGHT);
            cellt2.setVerticalAlignment(ALIGN_MIDDLE);
            cellt2.setBorder(BOTTOM | TOP);
            cellt2.setBackgroundColor(LIGHT_GRAY);
            table2.addCell(cellt2);

            phraset2 = new Phrase();
            phraset2.add(new Chunk("Type", f5_bold));
            cellt2 = new PdfPCell(phraset2);
            cellt2.setHorizontalAlignment(ALIGN_RIGHT);
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
            phraset2.add(new Chunk("No.Tr.", f5_bold));
            cellt2 = new PdfPCell(phraset2);
            cellt2.setHorizontalAlignment(ALIGN_RIGHT);
            cellt2.setVerticalAlignment(ALIGN_MIDDLE);
            cellt2.setBorder(BOTTOM | TOP);
            cellt2.setBackgroundColor(LIGHT_GRAY);
            table2.addCell(cellt2);

            phraset2 = new Phrase();
            phraset2.add(new Chunk("Time", f5_bold));
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
            cellt2.setHorizontalAlignment(ALIGN_LEFT);
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
     * @param siqlist
     * @param datereport1
     * @param datereport2
     * @param intestazionePdf
     * @param frequency
     * @param f_ORIG
     * @return
     */
    public String receipt(String path, ArrayList<CustomerTransactionList_value> siqlist, String datereport1, String datereport2, String intestazionePdf, String frequency, String f_ORIG) {

        //   String outputfile = "TillTransactionList.pdf";
        try {
            File pdf = new File(path + generaId(50) + "CustomerTransactionList.pdf");
            Document document = new Document(A4.rotate(), 20, 20, 20, 20);
            try(OutputStream ou = new FileOutputStream(pdf)){
            PdfWriter wr = getInstance(document, ou);
            document.open();

            PdfPTable table = new PdfPTable(2);
            table.setWidths(columnWidths0);
            table.setWidthPercentage(100);

            Phrase phrase1 = new Phrase();
            phrase1.add(new Chunk(intestazionePdf + " From " + datereport1 + " To " + datereport2 + "     " + frequency, f1_bold));
            PdfPCell cell1 = new PdfPCell(phrase1);
            cell1.setBorder(NO_BORDER);

            Phrase phrase3 = new Phrase();
            phrase3.add(new Chunk("\n ", f3_normal));
            PdfPCell cell3 = new PdfPCell(phrase3);
            cell3.setBorder(NO_BORDER);

            table.addCell(cell1);
            table.addCell(cellavuota);
            table.addCell(cell3);
            table.addCell(cellavuota);
            document.add(table);

            for (int z = 0; z < siqlist.size(); z++) {

                CustomerTransactionList_value siq = siqlist.get(z);

                ArrayList<CustomerTransactionList_value> dati = siq.getDati();

                table = new PdfPTable(2);
                table.setWidths(columnWidths0);
                table.setWidthPercentage(100);

                phrase1 = new Phrase();
                phrase1.add(new Chunk("", f3_bold));
                cell1 = new PdfPCell(phrase1);
                cell1.setBorder(NO_BORDER);

                phrase3 = new Phrase();
                if (f_ORIG.equals("D") || f_ORIG.equals("W") || f_ORIG.equals("M") || f_ORIG.equals("B")) {
                    phrase3.add(new Chunk(siq.getCustomer() + "\nNumber Of Transaction: " + dati.size(), f3_bold));
                } else if (f_ORIG.equals("M1") || f_ORIG.equals("Q") || f_ORIG.equals("RC") || f_ORIG.equals("TH")) {
                    phrase3.add(new Chunk(siq.getCustomer() + "\nVolume Of Transaction : " + formatMysqltoDisplay(siq.getTotal()) + " €", f3_bold));
                }

                cell3 = new PdfPCell(phrase3);
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

                Phrase phraset;
                PdfPCell cellt;

                table3 = new PdfPTable(17);
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

                    CustomerTransactionList_value actual = (CustomerTransactionList_value) dati.get(i);
                    CustomerTransactionList_value prossimo = (CustomerTransactionList_value) dati.get(i);

                    phraset = new Phrase();
                    phraset.add(new Chunk(actual.getBranch(), f5_normal));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setVerticalAlignment(ALIGN_BOTTOM);
                    cellt.setBorder(NO_BORDER);
                    table3.addCell(cellt);

                    phraset = new Phrase();
                    phraset.add(new Chunk(actual.getType(), f5_normal));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
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

                    totcommfee = totcommfee + fd((valueOf(actual.getComfree())));

                    phraset = new Phrase();
                    phraset.add(new Chunk(formatMysqltoDisplay(actual.getRound()), f5_normal));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setVerticalAlignment(ALIGN_BOTTOM);
                    cellt.setBorder(NO_BORDER);
                    table3.addCell(cellt);

                    totround = totround + fd((valueOf(actual.getRound())));

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
                    } else {

                        phraset = new Phrase();
                        phraset.add(new Chunk("-" + formatMysqltoDisplay(actual.getPayinpayout()), f5_normal));
                        cellt = new PdfPCell(phraset);
                        cellt.setHorizontalAlignment(ALIGN_RIGHT);
                        cellt.setVerticalAlignment(ALIGN_BOTTOM);
                        cellt.setBorder(NO_BORDER);
                        table3.addCell(cellt);

                        totalepayinoutgeneral -= fd(actual.getPayinpayoutSenzaFormattazione());
                    }

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
                            cellt.setHorizontalAlignment(ALIGN_LEFT);
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

                table3 = new PdfPTable(17);
                table3.setWidths(columnWidths2);
                table3.setWidthPercentage(100);

                for (int v = 0; v < columnWidths2.length; v++) {
                    if (v == 10) {
                        double totalegenerale = totaleresidentbuy + totaleresidentsell + totalenonresidentbuy + totalenonresidentsell;
                        phraset = new Phrase();
                        phraset.add(new Chunk((formatMysqltoDisplay(roundDoubleandFormat(totalegenerale, 2))), f5_bold));
                        cellt = new PdfPCell(phraset);
                        cellt.setHorizontalAlignment(ALIGN_RIGHT);
                        cellt.setVerticalAlignment(ALIGN_BOTTOM);
                        cellt.setBorder(NO_BORDER);
                        table3.addCell(cellt);
                    } else if (v == 12) {
                        phraset = new Phrase();
                        phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(totcommfee, 2)), f5_bold));
                        cellt = new PdfPCell(phraset);
                        cellt.setHorizontalAlignment(ALIGN_RIGHT);
                        cellt.setVerticalAlignment(ALIGN_BOTTOM);
                        cellt.setBorder(NO_BORDER);
                        table3.addCell(cellt);
                    } else if (v == 13) {
                        phraset = new Phrase();
                        phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(totround, 2)), f5_bold));
                        cellt = new PdfPCell(phraset);
                        cellt.setHorizontalAlignment(ALIGN_RIGHT);
                        cellt.setVerticalAlignment(ALIGN_BOTTOM);
                        cellt.setBorder(NO_BORDER);
                        table3.addCell(cellt);
                    } else if (v == 14) {
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

            }
//            
            document.close();
            wr.close();
            ou.close();
            String base64 = new String(encodeBase64(readFileToByteArray(pdf)));
            pdf.delete();
            return base64;
        }
        } catch (Exception ex) {
            ex.printStackTrace();
            insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
        }
        return null;
    }

    /**
     *
     * @param path
     * @param siqlist
     * @param datereport1
     * @param datereport2
     * @param intestazionePdf
     * @param frequency
     * @param f_ORIG
     * @return
     */
    public String receiptexcel(String path, ArrayList<CustomerTransactionList_value> siqlist, String datereport1, String datereport2, String intestazionePdf, String frequency, String f_ORIG) {

        //   String outputfile = "TillTransactionList.pdf";
        try {
            File pdf = new File(path + generaId(50) + "RiskAssessmentIndex.xls");
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet("RiskAssessmentIndex");

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
            cellStylenum.setBorderBottom(THIN);
            cellStylenum.setBorderTop(THIN);

            HSSFCellStyle cellStylenumB = (HSSFCellStyle) workbook.createCellStyle();
            cellStylenumB.setFont(font3);
            cellStylenumB.setDataFormat(hssfDataFormat.getFormat(formatdataCell));
            cellStylenumB.setBorderBottom(THIN);
            cellStylenumB.setBorderTop(THIN);

            HSSFCellStyle cellStylenumrate = (HSSFCellStyle) workbook.createCellStyle();
            cellStylenumrate.setDataFormat(hssfDataFormat.getFormat(formatdataCellRate));
            cellStylenumrate.setBorderBottom(THIN);
            cellStylenumrate.setBorderTop(THIN);

            Row rowP = sheet.createRow((short) 1);

            Cell cl = rowP.createCell(1);
            cl.setCellStyle(style1);
            cl.setCellValue(intestazionePdf + " From " + datereport1 + " To " + datereport2 + "     " + frequency);

            sheet.addMergedRegion(new CellRangeAddress(1, 1, 1, 15));

            int cntriga = 2;

            for (int z = 0; z < siqlist.size(); z++) {

                CustomerTransactionList_value siq = siqlist.get(z);

                cntriga++;
                cntriga++;

                ArrayList<CustomerTransactionList_value> dati = siq.getDati();

                Row row = sheet.createRow((short) cntriga);
                Cell cla = row.createCell(1);
                cla.setCellStyle(style3left);
                cla.setCellValue(siq.getCustomer());

                cntriga++;

                if (f_ORIG.equals("D") || f_ORIG.equals("W") || f_ORIG.equals("M") || f_ORIG.equals("B")) {
                    row = sheet.createRow((short) cntriga);
                    Cell cl2 = row.createCell(1);
                    cl2.setCellStyle(style3left);
                    cl2.setCellValue("Number Of Transaction: " + dati.size());
                } else if (f_ORIG.equals("M1") || f_ORIG.equals("Q") || f_ORIG.equals("RC") || f_ORIG.equals("TH")) {
                    row = sheet.createRow((short) cntriga);
                    Cell cl2 = row.createCell(1);
                    cl2.setCellStyle(style3left);
                    cl2.setCellValue("Volume Of Transaction : " + formatMysqltoDisplay(siq.getTotal()) + " €");
                }

                cntriga++;
                cntriga++;

                Row row6 = sheet.createRow((short) cntriga);

                Cell f1 = row6.createCell(1);
                f1.setCellStyle(style3);
                f1.setCellValue("Branch");

                Cell f2 = row6.createCell(2);
                f2.setCellStyle(style3);
                f2.setCellValue("Type");

                Cell f3 = row6.createCell(3);
                f3.setCellStyle(style3);
                f3.setCellValue("Till");

                Cell f4 = row6.createCell(4);
                f4.setCellStyle(style3);
                f4.setCellValue("User");

                Cell f5 = row6.createCell(5);
                f5.setCellStyle(style3);
                f5.setCellValue("No.Tr.");

                Cell f6 = row6.createCell(6);
                f6.setCellStyle(style3left);
                f6.setCellValue("Time");

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

                Cell f16 = row6.createCell(16);
                f16.setCellStyle(style3);
                f16.setCellValue("Spread");

                Cell f17 = row6.createCell(17);
                f17.setCellStyle(style3left);
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

                cntriga++;
                cntriga++;

                for (int i = 0; i < dati.size(); i++) {

                    cntriga++;
                    Row row7 = sheet.createRow((short) cntriga);

                    CustomerTransactionList_value actual = (CustomerTransactionList_value) dati.get(i);
//                    CustomerTransactionList_value prossimo = (CustomerTransactionList_value) dati.get(i);

                    Cell f18b = row7.createCell(1);
                    f18b.setCellStyle(style4);
                    f18b.setCellValue(actual.getBranch());

                    Cell f18 = row7.createCell(2);
                    f18.setCellStyle(style4);
                    f18.setCellValue(actual.getType());

                    Cell f19 = row7.createCell(3);
                    f19.setCellStyle(style4);
                    f19.setCellValue(actual.getTill());

                    Cell f20 = row7.createCell(4);
                    f20.setCellStyle(style4);
                    f20.setCellValue(actual.getUser());

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

                    Cell f25 = row7.createCell(9, NUMERIC);
                    f25.setCellValue(fd(actual.getAmount()));
                    f25.setCellStyle(cellStylenum);

                    Cell f26 = row7.createCell(10, NUMERIC);
                    f26.setCellStyle(cellStylenumrate);
                    f26.setCellValue(fd(actual.getRate()));

                    Cell f27 = row7.createCell(11, NUMERIC);
                    f27.setCellStyle(cellStylenum);
                    f27.setCellValue(fd(actual.getTotal()));

                    Cell f28 = row7.createCell(12, NUMERIC);
                    f28.setCellStyle(cellStylenum);
                    f28.setCellValue(fd(actual.getPerc()));

                    Cell f29 = row7.createCell(13, NUMERIC);
                    f29.setCellStyle(cellStylenum);
                    f29.setCellValue(fd(actual.getComfree()));

                    totcommfee = totcommfee + fd((valueOf(actual.getComfree())));

                    Cell f29bis = row7.createCell(14, NUMERIC);

                    f29bis.setCellStyle(cellStylenum);
                    f29bis.setCellValue(fd(actual.getRound()));

                    totround = totround + fd((valueOf(actual.getRound())));
                    totround = roundDouble(totround, 2);

                    Cell f30 = row7.createCell(15, NUMERIC);
                    f30.setCellStyle(cellStylenum);
                    if (actual.getKind().startsWith("S")) {
                        f30.setCellValue(fd(actual.getPayinpayout()));
                        totalepayinoutgeneral += fd(actual.getPayinpayoutSenzaFormattazione());
                    } else {
                        f30.setCellValue(-fd(actual.getPayinpayout()));
                        totalepayinoutgeneral -= fd(actual.getPayinpayoutSenzaFormattazione());
                    }

                    Cell f32 = row7.createCell(16, NUMERIC);
                    f32.setCellStyle(cellStylenum);
                    f32.setCellValue(fd(actual.getSpread()));

                    Cell f33 = row7.createCell(17);
                    f33.setCellStyle(style4);
                    f33.setCellValue(actual.getPos());

                    if (intestazionePdf.contains("Delete")) {
                        cntriga++;
                        Row row8 = sheet.createRow((short) cntriga);
                        Cell f331 = row8.createCell(15);
                        f331.setCellStyle(style4left);
                        f331.setCellValue(actual.getDelete1());

                        Cell f332 = row8.createCell(16);
                        f332.setCellStyle(style4left);
                        f332.setCellValue(actual.getDelete2());

                    }

                    cntriga++;
                    cntriga++;

                }

                cntriga++;
                Row row8 = sheet.createRow((short) cntriga);

                for (int v = 0; v < columnWidths2.length; v++) {

                    if (v == 10) {
                        double totalegenerale = totaleresidentbuy + totaleresidentsell + totalenonresidentbuy + totalenonresidentsell;

                        Cell f34 = row8.createCell(11, NUMERIC);
                        f34.setCellStyle(cellStylenumB);
                        f34.setCellValue(totalegenerale);

                    } else if (v == 14) {

                        Cell f35 = row8.createCell(13, NUMERIC);
                        f35.setCellStyle(cellStylenumB);
                        f35.setCellValue(totcommfee);

                    } else if (v == 15) {

                        Cell f35 = row8.createCell(14, NUMERIC);
                        f35.setCellStyle(cellStylenumB);
                        f35.setCellValue(totround);

                    } else if (v == 16) {

                        Cell f36 = row8.createCell(15, NUMERIC);
                        f36.setCellStyle(cellStylenumB);
                        f36.setCellValue(totalepayinoutgeneral);
                    } else if (v == 9) {
                        Cell f36 = row8.createCell(10);
                        f36.setCellStyle(style3);
                        f36.setCellValue("Total: ");

                    } else {

                        Cell f37 = row8.createCell(v + 1);
                        f37.setCellStyle(style4);
                        f37.setCellValue("");

                    }

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
