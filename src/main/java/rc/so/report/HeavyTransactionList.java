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
import static rc.so.util.Engine.insertTR;
import static rc.so.util.Utility.ff;
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
import org.apache.poi.hssf.usermodel.HSSFFont;
import static org.apache.poi.hssf.usermodel.HSSFFont.FONT_ARIAL;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import static org.apache.poi.ss.usermodel.BorderStyle.THIN;
import static org.apache.poi.ss.usermodel.HorizontalAlignment.LEFT;
import static org.apache.poi.ss.usermodel.HorizontalAlignment.RIGHT;
/**
 *
 * @author vcrugliano
 */
public class HeavyTransactionList {

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
    public static final float[] columnWidths2 = new float[]{15f, 15f, 5f, 15f, 15f, 15f, 15f};

    /**
     *
     */
    public static final float[] columnWidths3 = new float[]{20f, 9f, 9f, 9f, 9f, 9f, 9f, 9f, 9f, 9f, 9f};

    /**
     *
     */
    public static final float[] columnWidths4 = new float[]{3f, 6f, 6f, 5f, 3.5f, 6f, 6f, 6f, 6f, 3.5f, 6f, 6f, 3.5f, 8f, 8f};

    /**
     *
     */
    public static final float[] columnWidths5 = new float[]{25f, 25f, 25f, 25f};

    /**
     *
     */
    public static final float[] columnWidths6 = new float[]{20f, 20f, 20f, 20f, 20f};

    final String intestazionePdf = "Heavy Transaction List ";
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
    public HeavyTransactionList() {

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
            PdfPTable table2 = new PdfPTable(7);
            table2.setWidths(columnWidths2);
            table2.setWidthPercentage(100);

            Phrase phraset2 = new Phrase();
            phraset2.add(new Chunk("Client Name", f5_bold));
            PdfPCell cellt2 = new PdfPCell(phraset2);
            cellt2.setHorizontalAlignment(ALIGN_LEFT);
            cellt2.setVerticalAlignment(ALIGN_MIDDLE);
            cellt2.setBorder(BOTTOM | TOP);
            cellt2.setBackgroundColor(LIGHT_GRAY);
            table2.addCell(cellt2);

            phraset2 = new Phrase();
            phraset2.add(new Chunk("Address", f5_bold));
            cellt2 = new PdfPCell(phraset2);
            cellt2.setHorizontalAlignment(ALIGN_LEFT);
            cellt2.setVerticalAlignment(ALIGN_MIDDLE);
            cellt2.setBorder(BOTTOM | TOP);
            cellt2.setBackgroundColor(LIGHT_GRAY);
            table2.addCell(cellt2);

            phraset2 = new Phrase();
            phraset2.add(new Chunk("", f5_bold));
            cellt2 = new PdfPCell(phraset2);
            cellt2.setHorizontalAlignment(ALIGN_LEFT);
            cellt2.setVerticalAlignment(ALIGN_MIDDLE);
            cellt2.setBorder(BOTTOM | TOP);
            cellt2.setBackgroundColor(LIGHT_GRAY);
            table2.addCell(cellt2);

            phraset2 = new Phrase();
            phraset2.add(new Chunk("Birth place and date", f5_bold));
            cellt2 = new PdfPCell(phraset2);
            cellt2.setHorizontalAlignment(ALIGN_LEFT);
            cellt2.setVerticalAlignment(ALIGN_MIDDLE);
            cellt2.setBorder(BOTTOM | TOP);
            cellt2.setBackgroundColor(LIGHT_GRAY);
            table2.addCell(cellt2);

            phraset2 = new Phrase();
            phraset2.add(new Chunk("Tax Code", f5_bold));
            cellt2 = new PdfPCell(phraset2);
            cellt2.setHorizontalAlignment(ALIGN_LEFT);
            cellt2.setVerticalAlignment(ALIGN_MIDDLE);
            cellt2.setBorder(BOTTOM | TOP);
            cellt2.setBackgroundColor(LIGHT_GRAY);
            table2.addCell(cellt2);

            phraset2 = new Phrase();
            phraset2.add(new Chunk("Document data", f5_bold));
            cellt2 = new PdfPCell(phraset2);
            cellt2.setHorizontalAlignment(ALIGN_LEFT);
            cellt2.setVerticalAlignment(ALIGN_MIDDLE);
            cellt2.setBorder(BOTTOM | TOP);
            cellt2.setBackgroundColor(LIGHT_GRAY);
            table2.addCell(cellt2);

            phraset2 = new Phrase();
            phraset2.add(new Chunk("", f5_bold));
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
     * @return
     */
    public String receipt(String path, HeavyTransactionList_value siq, String datereport1, String datereport2) {

        // String outputfile = "HeavyTransactionList.pdf";
        try {
            File pdf = new File(path + generaId(50) + "HeavyTransactionList.pdf");
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
            phrase3.add(new Chunk("\n " + siq.getId_filiale() + " " + siq.getDe_filiale(), f3_normal));
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
            PdfPTable table3, table2;
            ArrayList<HeavyTransactionList_value> dati = siq.getDati();
            Phrase phraset;
            PdfPCell cellt;

            table3 = new PdfPTable(7);
            table3.setWidths(columnWidths2);
            table3.setWidthPercentage(100);

            for (int i = 0; i < dati.size(); i++) {

                float totale = 0;

                HeavyTransactionList_value actual = dati.get(i);

                String codicenazione = actual.getCodicenazione();

                phraset = new Phrase();
                phraset.add(new Chunk(actual.getClinetname(), f5_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_LEFT);
                cellt.setVerticalAlignment(ALIGN_BOTTOM);
                cellt.setBorder(NO_BORDER);
                table3.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(actual.getAddress(), f5_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_LEFT);
                cellt.setVerticalAlignment(ALIGN_BOTTOM);
                cellt.setBorder(NO_BORDER);
                table3.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk("", f5_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_LEFT);
                cellt.setVerticalAlignment(ALIGN_BOTTOM);
                cellt.setBorder(NO_BORDER);
                table3.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(actual.getBirthplaceday(), f5_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_LEFT);
                cellt.setVerticalAlignment(ALIGN_BOTTOM);
                cellt.setBorder(NO_BORDER);
                table3.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(actual.getTaxcode(), f5_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_LEFT);
                cellt.setVerticalAlignment(ALIGN_BOTTOM);
                cellt.setBorder(NO_BORDER);
                table3.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(actual.getDocumentnumber(), f5_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_LEFT);
                cellt.setVerticalAlignment(ALIGN_BOTTOM);
                cellt.setBorder(NO_BORDER);
                table3.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(actual.getDocumentdataente(), f5_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_LEFT);
                cellt.setVerticalAlignment(ALIGN_BOTTOM);
                cellt.setBorder(NO_BORDER);
                table3.addCell(cellt);

                table3.setSpacingAfter(13f);

                ArrayList<TransactionforHeavyTransactionList> trlist = actual.getTransactionlist();

                table2 = new PdfPTable(11);
                table2.setWidths(columnWidths3);
                table2.setWidthPercentage(100);

                Paragraph ptemp = new Paragraph(new Chunk("Transaction List", f5_bold));

                phraset = new Phrase();
                phraset.add(new Chunk("Date", f5_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_LEFT);
                cellt.setVerticalAlignment(ALIGN_BOTTOM);
                cellt.setBorder(NO_BORDER);
                table2.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk("", f5_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setVerticalAlignment(ALIGN_BOTTOM);
                cellt.setBorder(NO_BORDER);
                table2.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk("", f5_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setVerticalAlignment(ALIGN_BOTTOM);
                cellt.setBorder(NO_BORDER);
                table2.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk("", f5_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setVerticalAlignment(ALIGN_BOTTOM);
                cellt.setBorder(NO_BORDER);
                table2.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk("Currency", f5_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_LEFT);
                cellt.setVerticalAlignment(ALIGN_BOTTOM);
                cellt.setBorder(NO_BORDER);
                table2.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk("Quantity", f5_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setVerticalAlignment(ALIGN_BOTTOM);
                cellt.setBorder(NO_BORDER);
                table2.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk("Amount " + actual.getCurrencylocale(), f5_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setVerticalAlignment(ALIGN_BOTTOM);
                cellt.setBorder(NO_BORDER);
                table2.addCell(cellt);

                if (codicenazione.equals("051")) {

                    phraset = new Phrase();
                    phraset.add(new Chunk("Sanction", f5_bold));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setVerticalAlignment(ALIGN_BOTTOM);
                    cellt.setBorder(NO_BORDER);
                    table2.addCell(cellt);

                    phraset = new Phrase();
                    phraset.add(new Chunk("Client is PEP", f5_bold));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_LEFT);
                    cellt.setVerticalAlignment(ALIGN_BOTTOM);
                    cellt.setBorder(NO_BORDER);
                    table2.addCell(cellt);

                    phraset = new Phrase();
                    phraset.add(new Chunk("Money Source", f5_bold));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_LEFT);
                    cellt.setVerticalAlignment(ALIGN_BOTTOM);
                    cellt.setBorder(NO_BORDER);
                    table2.addCell(cellt);

                } else {

                    phraset = new Phrase();
                    phraset.add(new Chunk("", f5_bold));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setVerticalAlignment(ALIGN_BOTTOM);
                    cellt.setBorder(NO_BORDER);
                    table2.addCell(cellt);

                    phraset = new Phrase();
                    phraset.add(new Chunk("", f5_bold));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setVerticalAlignment(ALIGN_BOTTOM);
                    cellt.setBorder(NO_BORDER);
                    table2.addCell(cellt);

                }

                if (codicenazione.equals("051")) {
                    phraset = new Phrase();
                    phraset.add(new Chunk("Trans. Reason", f5_bold));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_LEFT);
                    cellt.setVerticalAlignment(ALIGN_BOTTOM);
                    cellt.setBorder(NO_BORDER);
                    table2.addCell(cellt);
                } else {
                    phraset = new Phrase();
                    phraset.add(new Chunk("", f5_bold));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_LEFT);
                    cellt.setVerticalAlignment(ALIGN_BOTTOM);
                    cellt.setBorder(NO_BORDER);
                    table2.addCell(cellt);

                    phraset = new Phrase();
                    phraset.add(new Chunk("", f5_bold));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setVerticalAlignment(ALIGN_BOTTOM);
                    cellt.setBorder(NO_BORDER);
                    table2.addCell(cellt);
                }

                for (int c = 0; c < trlist.size(); c++) {

                    TransactionforHeavyTransactionList trtemp = trlist.get(c);

                    phraset = new Phrase();
                    phraset.add(new Chunk(trtemp.getDate(), f5_normal));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_LEFT);
                    cellt.setVerticalAlignment(ALIGN_BOTTOM);
                    cellt.setBorder(NO_BORDER);
                    table2.addCell(cellt);

                    phraset = new Phrase();
                    phraset.add(new Chunk(trtemp.getRs(), f5_normal));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_LEFT);
                    cellt.setVerticalAlignment(ALIGN_BOTTOM);
                    cellt.setBorder(NO_BORDER);
                    table2.addCell(cellt);

                    phraset = new Phrase();
                    phraset.add(new Chunk(trtemp.getBuysell(), f5_normal));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_LEFT);
                    cellt.setVerticalAlignment(ALIGN_BOTTOM);
                    cellt.setBorder(NO_BORDER);
                    table2.addCell(cellt);

                    phraset = new Phrase();
                    phraset.add(new Chunk(trtemp.getB(), f5_normal));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_LEFT);
                    cellt.setVerticalAlignment(ALIGN_BOTTOM);
                    cellt.setBorder(NO_BORDER);
                    table2.addCell(cellt);

                    phraset = new Phrase();
                    phraset.add(new Chunk(trtemp.getCurrency(), f5_normal));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_LEFT);
                    cellt.setVerticalAlignment(ALIGN_BOTTOM);
                    cellt.setBorder(NO_BORDER);
                    table2.addCell(cellt);

                    phraset = new Phrase();
                    phraset.add(new Chunk(formatMysqltoDisplay(trtemp.getQuantity()), f5_normal));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setVerticalAlignment(ALIGN_BOTTOM);
                    cellt.setBorder(NO_BORDER);
                    table2.addCell(cellt);

                    phraset = new Phrase();
                    phraset.add(new Chunk(formatMysqltoDisplay(trtemp.getAmount()), f5_normal));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setVerticalAlignment(ALIGN_BOTTOM);
                    cellt.setBorder(NO_BORDER);
                    table2.addCell(cellt);

                    if (codicenazione.equals("051")) {

                        phraset = new Phrase();
                        phraset.add(new Chunk(trtemp.getSanction(), f5_normal));
                        cellt = new PdfPCell(phraset);
                        cellt.setHorizontalAlignment(ALIGN_RIGHT);
                        cellt.setVerticalAlignment(ALIGN_BOTTOM);
                        cellt.setBorder(NO_BORDER);
                        table2.addCell(cellt);

                        phraset = new Phrase();
                        phraset.add(new Chunk(trtemp.getClientpep(), f5_normal));
                        cellt = new PdfPCell(phraset);
                        cellt.setHorizontalAlignment(ALIGN_LEFT);
                        cellt.setVerticalAlignment(ALIGN_BOTTOM);
                        cellt.setBorder(NO_BORDER);
                        table2.addCell(cellt);

                        phraset = new Phrase();
                        phraset.add(new Chunk(trtemp.getMoneysource(), f5_normal));
                        cellt = new PdfPCell(phraset);
                        cellt.setHorizontalAlignment(ALIGN_LEFT);
                        cellt.setVerticalAlignment(ALIGN_BOTTOM);
                        cellt.setBorder(NO_BORDER);
                        table2.addCell(cellt);

                        phraset = new Phrase();
                        phraset.add(new Chunk(trtemp.getTransactionreason(), f5_normal));
                        cellt = new PdfPCell(phraset);
                        cellt.setHorizontalAlignment(ALIGN_LEFT);
                        cellt.setVerticalAlignment(ALIGN_BOTTOM);
                        cellt.setBorder(NO_BORDER);
                        table2.addCell(cellt);

                    } else {
                        phraset = new Phrase();
                        phraset.add(new Chunk("", f5_normal));
                        cellt = new PdfPCell(phraset);
                        cellt.setHorizontalAlignment(ALIGN_LEFT);
                        cellt.setVerticalAlignment(ALIGN_BOTTOM);
                        cellt.setBorder(NO_BORDER);
                        table2.addCell(cellt);

                        phraset = new Phrase();
                        phraset.add(new Chunk("", f5_normal));
                        cellt = new PdfPCell(phraset);
                        cellt.setHorizontalAlignment(ALIGN_LEFT);
                        cellt.setVerticalAlignment(ALIGN_BOTTOM);
                        cellt.setBorder(NO_BORDER);
                        table2.addCell(cellt);

                        phraset = new Phrase();
                        phraset.add(new Chunk("", f5_normal));
                        cellt = new PdfPCell(phraset);
                        cellt.setHorizontalAlignment(ALIGN_LEFT);
                        cellt.setVerticalAlignment(ALIGN_BOTTOM);
                        cellt.setBorder(NO_BORDER);
                        table2.addCell(cellt);

                        phraset = new Phrase();
                        phraset.add(new Chunk("", f5_normal));
                        cellt = new PdfPCell(phraset);
                        cellt.setHorizontalAlignment(ALIGN_LEFT);
                        cellt.setVerticalAlignment(ALIGN_BOTTOM);
                        cellt.setBorder(NO_BORDER);
                        table2.addCell(cellt);

                    }

                    totale += ff(trtemp.getAmountSenzaFormattazione());

                }

                document.add(table3);

                ptemp.setSpacingAfter(2f);
                document.add(ptemp);

                document.add(table2);

                table2 = new PdfPTable(11);
                table2.setWidths(columnWidths3);
                table2.setWidthPercentage(100);

                for (int w = 0; w < columnWidths3.length; w++) {

                    if (w == 0) {
                        phraset = new Phrase();
                        phraset.add(new Chunk("Total", f5_bold));
                        cellt = new PdfPCell(phraset);
                        cellt.setHorizontalAlignment(ALIGN_RIGHT);
                        cellt.setVerticalAlignment(ALIGN_BOTTOM);
                        cellt.setBorder(BOTTOM | TOP);
                        table2.addCell(cellt);
                    } else if (w == 5) {
                        phraset = new Phrase();
                        phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(totale, 2)), f5_bold));
                        cellt = new PdfPCell(phraset);
                        cellt.setHorizontalAlignment(ALIGN_RIGHT);
                        cellt.setVerticalAlignment(ALIGN_BOTTOM);
                        cellt.setBorder(BOTTOM | TOP);
                        table2.addCell(cellt);
                    }
                    if (w < 5) {
                        phraset = new Phrase();
                        phraset.add(new Chunk("", f5_normal));
                        cellt = new PdfPCell(phraset);
                        cellt.setHorizontalAlignment(ALIGN_RIGHT);
                        cellt.setVerticalAlignment(ALIGN_BOTTOM);
                        cellt.setBorder(BOTTOM | TOP);
                        table2.addCell(cellt);
                    } else {
                        phraset = new Phrase();
                        phraset.add(new Chunk("", f5_normal));
                        cellt = new PdfPCell(phraset);
                        cellt.setHorizontalAlignment(ALIGN_RIGHT);
                        cellt.setVerticalAlignment(ALIGN_BOTTOM);
                        cellt.setBorder(NO_BORDER);
                        table2.addCell(cellt);
                    }

                }

                document.add(table2);

                LineSeparator ls = new LineSeparator();
                ls.setLineWidth(0.7f);
                document.add(ls);

                table3 = new PdfPTable(7);
                table3.setWidths(columnWidths2);
                table3.setWidthPercentage(100);

                table2 = new PdfPTable(11);
                table2.setWidths(columnWidths3);
                table2.setWidthPercentage(100);
            }

            LineSeparator ls = new LineSeparator();
            ls.setLineWidth(0.7f);
            document.add(ls);

            table3 = new PdfPTable(7);
            table3.setWidths(columnWidths2);
            table3.setWidthPercentage(100);

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
     * @return
     */
    public String receiptexcel(String path, HeavyTransactionList_value siq, String datereport1, String datereport2) {

        // String outputfile = "HeavyTransactionList.pdf";
        try {
            File pdf = new File(path + generaId(50) + "HeavyTransactionList.xls");
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet("HeavyTransactionList");
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

            Row rowP = sheet.createRow((short) 1);

            Cell cl = rowP.createCell(1);
            cl.setCellStyle(style1);
            cl.setCellValue(intestazionePdf + " From " + datereport1 + " To " + datereport2);

//            Cell cl2 = rowP.createCell(6);
//            cl2.setCellStyle(style2);
//            cl2.setCellValue(datereport);
            Row row = sheet.createRow((short) 3);
            row.createCell(1).setCellValue(siq.getId_filiale() + " " + siq.getDe_filiale());

            Row row6 = sheet.createRow((short) 5);

            Cell f2 = row6.createCell(1);
            f2.setCellStyle(style3left);
            f2.setCellValue("Client Name");

            Cell f3 = row6.createCell(2);
            f3.setCellStyle(style3left);
            f3.setCellValue("Address");

            Cell f5 = row6.createCell(4);
            f5.setCellStyle(style3left);
            f5.setCellValue("Birth place and date");

            Cell f6 = row6.createCell(5);
            f6.setCellStyle(style3left);
            f6.setCellValue("Tax Code");

            Cell f7 = row6.createCell(6);
            f7.setCellStyle(style3left);
            f7.setCellValue("Document data");

            //Popolo la tabella
            ArrayList<HeavyTransactionList_value> dati = siq.getDati();

            int cntriga = 7;

            for (int i = 0; i < dati.size(); i++) {

                Row row7 = sheet.createRow((short) cntriga);

                float totale = 0;

                HeavyTransactionList_value actual = dati.get(i);

                String codicenazione = actual.getCodicenazione();

                Cell f18 = row7.createCell(1);
                f18.setCellStyle(style4left);
                f18.setCellValue(actual.getClinetname());

                Cell f19 = row7.createCell(2);
                f19.setCellStyle(style4left);
                f19.setCellValue(actual.getAddress());

                Cell f20 = row7.createCell(3);
                f20.setCellStyle(style4left);
                f20.setCellValue("");

                Cell f21 = row7.createCell(4);
                f21.setCellStyle(style4left);
                f21.setCellValue(actual.getBirthplaceday());

                Cell f22 = row7.createCell(5);
                f22.setCellStyle(style4left);
                f22.setCellValue(actual.getTaxcode());

                Cell f23 = row7.createCell(6);
                f23.setCellStyle(style4left);
                f23.setCellValue(actual.getDocumentnumber());

                Cell f24 = row7.createCell(7);
                f24.setCellStyle(style4left);
                f24.setCellValue(actual.getDocumentdataente());

                ArrayList<TransactionforHeavyTransactionList> trlist = actual.getTransactionlist();

                cntriga++;
                cntriga++;

                Row row8 = sheet.createRow((short) cntriga);
                Cell f25 = row8.createCell(1);
                f25.setCellStyle(style3left);
                f25.setCellValue("Transaction List");

                cntriga++;
                Row row9 = sheet.createRow((short) cntriga);

                Cell f26 = row9.createCell(1);
                f26.setCellStyle(style3left);
                f26.setCellValue("Date");

                Cell f27 = row9.createCell(5);
                f27.setCellStyle(style3left);
                f27.setCellValue("Currency");

                Cell f28 = row9.createCell(6);
                f28.setCellStyle(style3);
                f28.setCellValue("Quantity");

                Cell f29 = row9.createCell(7);
                f29.setCellStyle(style3);
                f29.setCellValue("Amount " + actual.getCurrencylocale());

                if (codicenazione.equals("051")) {

                    Cell f30 = row9.createCell(8);
                    f30.setCellStyle(style3);
                    f30.setCellValue("Sanction ");

                    Cell f31 = row9.createCell(9);
                    f31.setCellStyle(style3left);
                    f31.setCellValue("Client is PEP ");

                    Cell f32 = row9.createCell(10);
                    f32.setCellStyle(style3left);
                    f32.setCellValue("Money Source");

                    Cell f33 = row9.createCell(11);
                    f33.setCellStyle(style3left);
                    f33.setCellValue("Trans. Reason");

                }

                cntriga++;
                cntriga++;

                for (int c = 0; c < trlist.size(); c++) {

                    Row row10 = sheet.createRow((short) cntriga);

                    TransactionforHeavyTransactionList trtemp = (TransactionforHeavyTransactionList) trlist.get(c);

                    Cell f34 = row10.createCell(1);
                    f34.setCellStyle(style4left);
                    f34.setCellValue(trtemp.getDate());

                    Cell f35 = row10.createCell(2);
                    f35.setCellStyle(style4left);
                    f35.setCellValue(trtemp.getRs());

                    Cell f36 = row10.createCell(3);
                    f36.setCellStyle(style4left);
                    f36.setCellValue(trtemp.getBuysell());

                    Cell f37 = row10.createCell(4);
                    f37.setCellStyle(style4left);
                    f37.setCellValue(trtemp.getB());

                    Cell f38 = row10.createCell(5);
                    f38.setCellStyle(style4left);
                    f38.setCellValue(trtemp.getCurrency());

                    Cell f39 = row10.createCell(6);
                    f39.setCellStyle(style4);
                    f39.setCellValue(formatMysqltoDisplay(trtemp.getQuantity()));

                    Cell f40 = row10.createCell(7);
                    f40.setCellStyle(style4);
                    f40.setCellValue(formatMysqltoDisplay(trtemp.getAmount()));

                    if (codicenazione.equals("051")) {

                        Cell f41 = row10.createCell(8);
                        f41.setCellStyle(style4);
                        f41.setCellValue(trtemp.getSanction());

                        Cell f42 = row10.createCell(9);
                        f42.setCellStyle(style4left);
                        f42.setCellValue(trtemp.getClientpep());

                        Cell f43 = row10.createCell(10);
                        f43.setCellStyle(style4left);
                        f43.setCellValue(trtemp.getMoneysource());

                        Cell f44 = row10.createCell(11);
                        f44.setCellStyle(style4left);
                        f44.setCellValue(trtemp.getTransactionreason());

                    }

                    totale += ff(trtemp.getAmountSenzaFormattazione());

                }

                cntriga++;
                cntriga++;
                cntriga++;
                Row row11 = sheet.createRow((short) cntriga);

                for (int w = 0; w < columnWidths3.length; w++) {

                    if (w == 0) {

                        Cell f45 = row11.createCell(w + 1);
                        f45.setCellStyle(style3);
                        f45.setCellValue("Total");

                    } else if (w == 5) {

                        Cell f45 = row11.createCell(w + 2);
                        f45.setCellStyle(style3);
                        f45.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(totale, 2)));

                    }
                    if (w < 5) {

                    } else {

                    }

                }

                cntriga++;
                Row row12 = sheet.createRow((short) cntriga);

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
