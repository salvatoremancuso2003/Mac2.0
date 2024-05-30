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
import static rc.so.util.Constant.formatdataCell;
import static rc.so.util.Constant.formatdataCellRate;
import static rc.so.util.Constant.formatdataCellint;
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
import static org.apache.poi.ss.usermodel.BorderStyle.THIN;
import static org.apache.poi.ss.usermodel.CellType.NUMERIC;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import static org.apache.poi.ss.usermodel.HorizontalAlignment.CENTER;
import static org.apache.poi.ss.usermodel.HorizontalAlignment.LEFT;

/**
 *
 * @author rcosco
 */
public class TillTransactionList {

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
    public static final float[] columnWidths2 = new float[]{5f, 3f, 3f, 8f, 8f, 5f, 10f, 6f, 6f, 6f, 3.5f, 4.5f, 5f, 5f, 7f, 5f, 6f, 4f};

    /**
     *
     */
    public static final float[] columnWidths3 = new float[]{25f, 7.5f, 19f, 8f, 20f, 7f, 16f, 9f, 10f, 9f, 9f, 9f};

    /**
     *
     */
    public static final float[] columnWidths4 = new float[]{5f, 6f, 6f, 5f, 3.5f, 6f, 6f, 6f, 3.5f, 6f, 6f, 6f, 3.5f, 8f, 8f, 4f, 5f, 3.5f, 5f, 5f, 5f};

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

    boolean aggiungidelete = false;

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
    private static final String formatdataCellRATE = "#,#.00000000";

    Font f0_bold, f1_bold, f2_bold, f1_normal, f2_normal, f3_normal, f3_bold, f4_bold, f5_normal, f5_bold, f6_normal, f6_bold;

    /**
     * Costructor
     */
    public TillTransactionList() {

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
     * @param aggiungidelete
     */
    public TillTransactionList(boolean aggiungidelete) {

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

        this.aggiungidelete = aggiungidelete;

    }

    /**
     *
     * @param document
     * @param bbtl
     */
    public void scriviIntestazioneColonne(Document document, int bbtl) {

        try {
            PdfPTable table2 = new PdfPTable(18);
            table2.setWidths(columnWidths2);
            table2.setWidthPercentage(100);

            Phrase phraset2 = new Phrase();
            if (bbtl == 1) {
                phraset2.add(new Chunk("BB Code", f5_bold));
            } else {
                phraset2.add(new Chunk("Type", f5_bold));
            }
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

            phraset2 = new Phrase();
            phraset2.add(new Chunk("Fig", f5_bold));
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
     * @param bbtl
     * @return
     */
    public String receipt(String path, TillTransactionList_value siq, String datereport1, String datereport2, String intestazionePdf, int bbtl) {

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

            scriviIntestazioneColonne(document, bbtl);

            //Popolo la tabella
            PdfPTable table3, table6;
            ArrayList<TillTransactionList_value> dati = siq.getDati();
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

                TillTransactionList_value actual = dati.get(i);

                phraset = new Phrase();
                phraset.add(new Chunk(actual.getType(), f5_bold));
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

                if (actual.getKind().contains("Buy") && (!actual.getType().contains("D") || this.aggiungidelete)) {
                    if (actual.getResidentnonresident().equalsIgnoreCase("Resident")) {
                        totaleresidentbuy += fd(actual.getTotalSenzaFormattazione());
                        totaleresidentcommfreebuy += (fd(actual.getComfreeSenzaFormattazione()) + fd(actual.getRound()));

                    } else {
                        totalenonresidentcommfreebuy += (fd(actual.getComfreeSenzaFormattazione()) + fd(actual.getRound()));
                        totalenonresidentbuy += fd(actual.getTotalSenzaFormattazione());
                    }
                }
                if (actual.getKind().contains("Sell") && (!actual.getType().contains("D") || this.aggiungidelete)) {
                    if (actual.getResidentnonresident().equalsIgnoreCase("Resident")) {

                        totaleresidentsell += fd(actual.getTotalSenzaFormattazione());
                        totaleresidentcommfreesell += (fd(actual.getComfreeSenzaFormattazione()) + fd(actual.getRound()));
                    } else {
                        totalenonresidentsell += fd(actual.getTotalSenzaFormattazione());
                        totalenonresidentcommfreesell += (fd(actual.getComfreeSenzaFormattazione()) + fd(actual.getRound()));
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

                if (!actual.getType().contains("D") || this.aggiungidelete) {
                    totcommfee = totcommfee + fd((valueOf(actual.getComfree())));
                }
                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(actual.getRound()), f5_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setVerticalAlignment(ALIGN_BOTTOM);
                cellt.setBorder(NO_BORDER);
                table3.addCell(cellt);
                if (!actual.getType().contains("D") || this.aggiungidelete) {
                    totround = totround + fd((valueOf(actual.getRound())));
                }

                totround = roundDouble(totround, 2);

                if (actual.getKind().startsWith("S")) {

                    phraset = new Phrase();
                    phraset.add(new Chunk("+" + formatMysqltoDisplay(actual.getPayinpayout()), f5_normal));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setVerticalAlignment(ALIGN_BOTTOM);
                    cellt.setBorder(NO_BORDER);
                    table3.addCell(cellt);
                    if (!actual.getType().contains("D") || this.aggiungidelete) {
                        totalepayinoutgeneral += fd(actual.getPayinpayoutSenzaFormattazione());
                    }
                } else {

                    phraset = new Phrase();
                    phraset.add(new Chunk("-" + formatMysqltoDisplay(actual.getPayinpayout()), f5_normal));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setVerticalAlignment(ALIGN_BOTTOM);
                    cellt.setBorder(NO_BORDER);
                    table3.addCell(cellt);
                    if (!actual.getType().contains("D") || this.aggiungidelete) {
                        totalepayinoutgeneral -= fd(actual.getPayinpayoutSenzaFormattazione());
                    }
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

                phraset = new Phrase();
                phraset.add(new Chunk(actual.getFig(), f5_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setVerticalAlignment(ALIGN_BOTTOM);
                cellt.setBorder(NO_BORDER);
                table3.addCell(cellt);

                if (intestazionePdf.contains("Delete")) {

                    for (int t = 0; t < 15; t++) {

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
                    cellt.setColspan(2);
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

            table3 = new PdfPTable(21);
            table3.setWidths(columnWidths4);
            table3.setWidthPercentage(100);

            phraset = new Phrase();
            phraset.add(new Chunk("Transaction value", f5_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_CENTER);
            cellt.setVerticalAlignment(ALIGN_BOTTOM);
            cellt.setBorder(NO_BORDER);
            cellt.setColspan(4);
            table3.addCell(cellt);

//            phraset = new Phrase();
//            phraset.add(new Chunk("", f5_bold));
//            cellt = new PdfPCell(phraset);
//            cellt.setHorizontalAlignment(Element.ALIGN_RIGHT);
//            cellt.setVerticalAlignment(Element.ALIGN_BOTTOM);
//            cellt.setBorder(Rectangle.NO_BORDER);
//            table3.addCell(cellt);
            phraset = new Phrase();
            phraset.add(new Chunk("Commissions value + Round", f5_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_CENTER);
            cellt.setVerticalAlignment(ALIGN_BOTTOM);
            cellt.setBorder(NO_BORDER);
            cellt.setColspan(4);
            table3.addCell(cellt);

//            phraset = new Phrase();
//            phraset.add(new Chunk("", f5_bold));
//            cellt = new PdfPCell(phraset);
//            cellt.setHorizontalAlignment(Element.ALIGN_RIGHT);
//            cellt.setVerticalAlignment(Element.ALIGN_BOTTOM);
//            cellt.setBorder(Rectangle.NO_BORDER);
//            table3.addCell(cellt);
            phraset = new Phrase();
            phraset.add(new Chunk("Transaction number", f5_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_CENTER);
            cellt.setVerticalAlignment(ALIGN_BOTTOM);
            cellt.setBorder(NO_BORDER);
            cellt.setColspan(4);
            table3.addCell(cellt);

//            phraset = new Phrase();
//            phraset.add(new Chunk("", f5_bold));
//            cellt = new PdfPCell(phraset);
//            cellt.setHorizontalAlignment(Element.ALIGN_RIGHT);
//            cellt.setVerticalAlignment(Element.ALIGN_BOTTOM);
//            cellt.setBorder(Rectangle.NO_BORDER);
//            table3.addCell(cellt);
            phraset = new Phrase();
            phraset.add(new Chunk("Sell / Internet booking", f5_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_CENTER);
            cellt.setVerticalAlignment(ALIGN_BOTTOM);
            cellt.setBorder(NO_BORDER);
            cellt.setColspan(3);
            table3.addCell(cellt);

//            phraset = new Phrase();
//            phraset.add(new Chunk("", f5_bold));
//            cellt = new PdfPCell(phraset);
//            cellt.setHorizontalAlignment(Element.ALIGN_RIGHT);
//            cellt.setVerticalAlignment(Element.ALIGN_BOTTOM);
//            cellt.setBorder(Rectangle.NO_BORDER);
//            table3.addCell(cellt);
//              phraset = new Phrase();
//            phraset.add(new Chunk("", f5_bold));
//            cellt = new PdfPCell(phraset);
//            cellt.setHorizontalAlignment(Element.ALIGN_RIGHT);
//            cellt.setVerticalAlignment(Element.ALIGN_BOTTOM);
//            cellt.setBorder(Rectangle.NO_BORDER);
//            table3.addCell(cellt);
            phraset = new Phrase();
            phraset.add(new Chunk("POS", f5_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_CENTER);
            cellt.setVerticalAlignment(ALIGN_BOTTOM);
            cellt.setBorder(NO_BORDER);
            cellt.setColspan(3);
            table3.addCell(cellt);

//
//            phraset = new Phrase();
//            phraset.add(new Chunk("", f5_bold));
//            cellt = new PdfPCell(phraset);
//            cellt.setHorizontalAlignment(Element.ALIGN_RIGHT);
//            cellt.setVerticalAlignment(Element.ALIGN_BOTTOM);
//            cellt.setBorder(Rectangle.NO_BORDER);
//            table3.addCell(cellt);
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

            ArrayList<String> datifooter = siq.getFooterdati();

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
                    phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(totaleresidentbuy, 2)), f6_bold));
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
                    phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(totalenonresidentbuy, 2)), f6_bold));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setVerticalAlignment(ALIGN_BOTTOM);
                    cellt.setBorder(NO_BORDER);
                    if (n == columnWidths4.length - 1) {
                        cellt.setBorder(RIGHT);
                    }
                    table3.addCell(cellt);

                } else if (n == 3) {

                    double totalebuytrans = totaleresidentbuy + totalenonresidentbuy;
                    phraset = new Phrase();
                    phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(totalebuytrans, 2)), f6_bold));
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
                    phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(totaleresidentcommfreebuy, 2)), f6_bold));
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
                    phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(totalenonresidentcommfreebuy, 2)), f6_bold));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setVerticalAlignment(ALIGN_BOTTOM);
                    cellt.setBorder(NO_BORDER);
                    if (n == columnWidths4.length - 1) {
                        cellt.setBorder(RIGHT);
                    }
                    table3.addCell(cellt);

                } else if (n == 7) {
                    double totalebuycommfee = totaleresidentcommfreebuy + totalenonresidentcommfreebuy;
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
                    phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(totaleresidentsell, 2)), f6_bold));
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
                    phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(totalenonresidentsell, 2)), f6_bold));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setVerticalAlignment(ALIGN_BOTTOM);
                    cellt.setBorder(NO_BORDER);
                    if (n == columnWidths4.length - 1) {
                        cellt.setBorder(RIGHT);
                    }
                    table3.addCell(cellt);
                } else if (n == 3) {

                    double totaleselltrans = totaleresidentsell + totalenonresidentsell;
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
                    phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(totaleresidentcommfreesell, 2)), f6_bold));
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
                    phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(totalenonresidentcommfreesell, 2)), f6_bold));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setVerticalAlignment(ALIGN_BOTTOM);
                    cellt.setBorder(NO_BORDER);
                    if (n == columnWidths4.length - 1) {
                        cellt.setBorder(RIGHT);
                    }
                    table3.addCell(cellt);
                } else if (n == 7) {
                    double totalesellcommfee = totaleresidentcommfreesell + totalenonresidentcommfreesell;
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

                    double totaleresidentsellbuy = totaleresidentbuy + totaleresidentsell;
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

                    double totalenonresidentbuysell = totalenonresidentbuy + totalenonresidentsell;
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

                    double totalebuysellgenerale = totaleresidentbuy + totalenonresidentbuy + totaleresidentsell + totalenonresidentsell;
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

                    double totaleresidentcommfeebuysell = totaleresidentcommfreebuy + totaleresidentcommfreesell;
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
                    double totalenonresidentcommfeebuysell = totalenonresidentcommfreebuy + totalenonresidentcommfreesell;
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
                    double totalegeneralecommfee = totaleresidentcommfreesell + totaleresidentcommfreebuy + totalenonresidentcommfreebuy + totalenonresidentcommfreesell;
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
     * @param bbtl
     * @return
     */
    public String receiptexcel(String path, TillTransactionList_value siq, String datereport1, String datereport2, String intestazionePdf, int bbtl) {

        //   String outputfile = "TillTransactionList.pdf";
        try {
            File pdf = new File(path + generaId(50) + "TillTransactionList.xls");
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet("TillTransactionList");
            //CREAZIONE FONT
            HSSFFont font = workbook.createFont();
            font.setFontName(FONT_ARIAL);
            font.setFontHeightInPoints((short) 16);
            font.setBold(true);

            HSSFCellStyle style1 = (HSSFCellStyle) workbook.createCellStyle();
            style1.setFont(font);

            HSSFFont font2 = workbook.createFont();
            font2.setFontName(FONT_ARIAL);
            font2.setFontHeightInPoints((short) 12);
            font2.setBold(true);

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

            HSSFCellStyle style3center = (HSSFCellStyle) workbook.createCellStyle();
            style3center.setFont(font3);
            style3center.setWrapText(true);
            style3center.setAlignment(CENTER);

            style3center.setBorderTop(THIN);
            style3center.setBorderBottom(THIN);

            HSSFCellStyle style3left = (HSSFCellStyle) workbook.createCellStyle();
            style3left.setFont(font3);
            style3left.setAlignment(LEFT);

            style3left.setBorderTop(THIN);
            style3left.setBorderBottom(THIN);

            HSSFFont font4 = workbook.createFont();
            font4.setFontName(FONT_ARIAL);
            font4.setFontHeightInPoints((short) 10);

            HSSFCellStyle style4 = (HSSFCellStyle) workbook.createCellStyle();
            style4.setAlignment(HorizontalAlignment.RIGHT);
            style4.setBorderTop(THIN);
            style4.setBorderBottom(THIN);

            HSSFCellStyle cellStylenum = (HSSFCellStyle) workbook.createCellStyle();
            HSSFDataFormat hssfDataFormat = (HSSFDataFormat) workbook.createDataFormat();
            cellStylenum.setDataFormat(hssfDataFormat.getFormat(formatdataCell));
            cellStylenum.setAlignment(HorizontalAlignment.RIGHT);
            cellStylenum.setBorderTop(THIN);
            cellStylenum.setBorderBottom(THIN);

            HSSFCellStyle cellStyleint = (HSSFCellStyle) workbook.createCellStyle();
            cellStyleint.setDataFormat(hssfDataFormat.getFormat(formatdataCellint));
            cellStyleint.setAlignment(HorizontalAlignment.RIGHT);
            cellStyleint.setBorderTop(THIN);
            cellStyleint.setBorderBottom(THIN);

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
//            cl2.setCellValue(datereport1);
            Row row = sheet.createRow((short) 3);
            row.createCell(1).setCellValue(siq.getId_filiale() + " " + siq.getDe_filiale());

            ArrayList<TillTransactionList_value> dati = siq.getDati();

            Row row6 = sheet.createRow((short) 5);

            Cell f2 = row6.createCell(1);
            f2.setCellStyle(style3left);
            if (bbtl == 1) {
                f2.setCellValue("BB Code");
            } else {
                f2.setCellValue("Type");
            }

            Cell f3 = row6.createCell(2);
            f3.setCellStyle(style3);
            f3.setCellValue("Till");

            Cell f4 = row6.createCell(3);
            f4.setCellStyle(style3);
            f4.setCellValue("User");

            Cell f5 = row6.createCell(4);
            f5.setCellStyle(style3);
            f5.setCellValue("No.Tr.");

            Cell f6 = row6.createCell(5);
            f6.setCellStyle(style3left);
            f6.setCellValue("Date / Time");

            Cell f7 = row6.createCell(6);
            f7.setCellStyle(style3left);
            f7.setCellValue("Cur");

            Cell f8 = row6.createCell(7);
            f8.setCellStyle(style3left);
            f8.setCellValue("Kind");

            Cell f9 = row6.createCell(8);
            f9.setCellStyle(style3);
            f9.setCellValue("Amount");

            Cell f10 = row6.createCell(9);
            f10.setCellStyle(style3);
            f10.setCellValue("Rate");

            Cell f11 = row6.createCell(10);
            f11.setCellStyle(style3);
            f11.setCellValue("Total");

            Cell f12 = row6.createCell(11);
            f12.setCellStyle(style3);
            f12.setCellValue("%");

            Cell f13 = row6.createCell(12);
            f13.setCellStyle(style3);
            f13.setCellValue("Comm.Fee");

            Cell f13bis = row6.createCell(13);
            f13bis.setCellStyle(style3);
            f13bis.setCellValue("Round");

            Cell f14 = row6.createCell(14);
            f14.setCellStyle(style3);
            f14.setCellValue("Pay In / Pay Out");

            Cell f15 = row6.createCell(15);
            f15.setCellStyle(style3left);
            f15.setCellValue("Customer");

            Cell f16 = row6.createCell(16);
            f16.setCellStyle(style3);
            f16.setCellValue("Spread");

            Cell f17 = row6.createCell(17);
            f17.setCellStyle(style3);
            f17.setCellValue("Pos / Bank Acc");

            Cell f18b = row6.createCell(18);
            f18b.setCellStyle(style3);
            f18b.setCellValue("Fig");

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
                Row row7 = sheet.createRow((short) cntriga);

                TillTransactionList_value actual = dati.get(i);

                Cell f18 = row7.createCell(1);
                f18.setCellStyle(style4left);
                f18.setCellValue(actual.getType());

                Cell f19 = row7.createCell(2);
                f19.setCellStyle(style4);
                f19.setCellValue(actual.getTill());

                Cell f20 = row7.createCell(3);
                f20.setCellStyle(style4);
                f20.setCellValue(actual.getUser());

                Cell f21 = row7.createCell(4);
                f21.setCellStyle(style4);
                f21.setCellValue(actual.getNotr());

                Cell f22 = row7.createCell(5);
                f22.setCellStyle(style4left);
                f22.setCellValue(formatStringtoStringDate(actual.getTime(), patternsqldate, patternnormdate));

                Cell f23 = row7.createCell(6);
                f23.setCellStyle(style4left);
                f23.setCellValue(actual.getCur());

                Cell f24 = row7.createCell(7);
                f24.setCellStyle(style4left);
                f24.setCellValue(actual.getKind());

                if (actual.getKind().contains("Buy") && (!actual.getType().contains("D") || this.aggiungidelete)) {
                    if (actual.getResidentnonresident().equalsIgnoreCase("Resident")) {
                        totaleresidentbuy += fd(actual.getTotalSenzaFormattazione());
                        totaleresidentcommfreebuy += (fd(actual.getComfreeSenzaFormattazione()) + fd(actual.getRound()));

                    } else {
                        totalenonresidentcommfreebuy += (fd(actual.getComfreeSenzaFormattazione()) + fd(actual.getRound()));
                        totalenonresidentbuy += fd(actual.getTotalSenzaFormattazione());
                    }
                }
                if (actual.getKind().contains("Sell") && (!actual.getType().contains("D") || this.aggiungidelete)) {
                    if (actual.getResidentnonresident().equalsIgnoreCase("Resident")) {

                        totaleresidentsell += fd(actual.getTotalSenzaFormattazione());
                        totaleresidentcommfreesell += (fd(actual.getComfreeSenzaFormattazione()) + fd(actual.getRound()));
                    } else {
                        totalenonresidentsell += fd(actual.getTotalSenzaFormattazione());

                        totalenonresidentcommfreesell += (fd(actual.getComfreeSenzaFormattazione()) + fd(actual.getRound()));
                    }
                }

                Cell f25 = row7.createCell(8);
                f25.setCellStyle(cellStylenum);
                f25.setCellValue(fd(actual.getAmount()));

                Cell f26 = row7.createCell(9);
                f26.setCellStyle(cellStylenumRATE);
                f26.setCellValue(fd(actual.getRate()));

                Cell f27 = row7.createCell(10);
                f27.setCellStyle(cellStylenum);
                f27.setCellValue(fd(actual.getTotal()));

                Cell f28 = row7.createCell(11);
                f28.setCellStyle(cellStylenum);
                f28.setCellValue(fd(actual.getPerc()));

                Cell f29 = row7.createCell(12);
                f29.setCellStyle(cellStylenum);
                f29.setCellValue(fd(actual.getComfree()));

                if (!actual.getType().contains("D") || this.aggiungidelete) {

                    totcommfee = totcommfee + fd((valueOf(actual.getComfree())));
                }

                Cell f29bis = row7.createCell(13);
                f29bis.setCellStyle(cellStylenum);
                f29bis.setCellValue(fd(actual.getRound()));

                if (!actual.getType().contains("D") || this.aggiungidelete) {
                    totround = totround + fd((valueOf(actual.getRound())));
                    totround = roundDouble(totround, 2);
                }

                Cell f30 = row7.createCell(14);
                f30.setCellStyle(cellStylenum);

                if (actual.getKind().startsWith("S")) {
                    f30.setCellValue(fd(actual.getPayinpayout()));
                    if (!actual.getType().contains("D") || this.aggiungidelete) {
                        totalepayinoutgeneral += fd(actual.getPayinpayoutSenzaFormattazione());
                    }
                } else {

                    f30.setCellValue(-fd(actual.getPayinpayout()));
                    if (!actual.getType().contains("D") || this.aggiungidelete) {
                        totalepayinoutgeneral -= fd(actual.getPayinpayoutSenzaFormattazione());
                    }
                }

                Cell f31 = row7.createCell(15);
                f31.setCellStyle(style4left);
                f31.setCellValue(actual.getCustomer());

                Cell f32 = row7.createCell(16);
                f32.setCellStyle(cellStylenum);
                f32.setCellValue(fd(actual.getSpread()));

                Cell f33 = row7.createCell(17);
                f33.setCellStyle(style4);
                f33.setCellValue(actual.getPos());

                Cell f33b = row7.createCell(18);
                f33b.setCellStyle(style4);
                f33b.setCellValue(actual.getFig());

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
                if (v == 9) {
                    double totalegenerale = totaleresidentbuy + totaleresidentsell + totalenonresidentbuy + totalenonresidentsell;

                    Cell f34 = row8.createCell(9 + 1);
                    f34.setCellStyle(style3num);
                    f34.setCellValue((fd(roundDoubleandFormat(totalegenerale, 2))));

                } else if (v == 12 + 1) {

                    Cell f35 = row8.createCell(12);
                    f35.setCellStyle(style3num);
                    f35.setCellValue((fd(roundDoubleandFormat(totcommfee, 2))));

                } else if (v == 13 + 1) {

                    Cell f35 = row8.createCell(13);
                    f35.setCellStyle(style3num);
                    f35.setCellValue((fd(roundDoubleandFormat(totround, 2))));

                } else if (v == 14 + 1) {

                    Cell f36 = row8.createCell(14);
                    f36.setCellStyle(style3num);
                    f36.setCellValue(fd(roundDoubleandFormat(totalepayinoutgeneral, 2)));

                } else {

                    Cell f37 = row8.createCell(v + 1);
                    f37.setCellStyle(style3);
                    f37.setCellValue("");

                }

            }

            cntriga++;
            cntriga++;
            Row row9 = sheet.createRow((short) cntriga);

            Cell f38 = row9.createCell(3);
            f38.setCellStyle(style3);
            f38.setCellValue("Transaction value");

            Cell f41 = row9.createCell(7);
            f41.setCellStyle(style3);
            f41.setCellValue("Commission Value + Round");

            Cell f42 = row9.createCell(11);
            f42.setCellStyle(style3);
            f42.setCellValue("Transacion Number");

            Cell f421 = row9.createCell(14);
            f421.setCellStyle(style3);
            f421.setCellValue("Sell / Internet Booking");

            Cell f422 = row9.createCell(17);
            f422.setCellStyle(style3);
            f422.setCellValue("POS");

            Cell f4221 = row9.createCell(21);
            f4221.setCellStyle(style3);
            f4221.setCellValue("Bank Account");

            cntriga++;
            Row row10 = sheet.createRow((short) cntriga);

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
            Row row11 = sheet.createRow((short) cntriga);

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
                    f56.setCellStyle(cellStyleint);
                    f56.setCellValue(fd(roundDoubleandFormat(tot, 0)));

                } else if (n == 1) {

                    Cell f56 = row11.createCell(n + 1);
                    f56.setCellStyle(cellStylenum);
                    f56.setCellValue(fd(roundDoubleandFormat(totaleresidentbuy, 2)));

                } else if (n == 2) {

                    Cell f56 = row11.createCell(n + 1);
                    f56.setCellStyle(cellStylenum);
                    f56.setCellValue(fd(roundDoubleandFormat(totalenonresidentbuy, 2)));

                } else if (n == 3) {

                    double totalebuytrans = totaleresidentbuy + totalenonresidentbuy;

                    Cell f56 = row11.createCell(n + 1);
                    f56.setCellStyle(cellStylenum);
                    f56.setCellValue(fd(roundDoubleandFormat(totalebuytrans, 2)));

                } else if (n == 5) {

                    Cell f56 = row11.createCell(n + 1);
                    f56.setCellStyle(cellStylenum);
                    f56.setCellValue(fd(roundDoubleandFormat(totaleresidentcommfreebuy, 2)));

                } else if (n == 6) {

                    Cell f56 = row11.createCell(n + 1);
                    f56.setCellStyle(cellStylenum);
                    f56.setCellValue(fd(roundDoubleandFormat(totalenonresidentcommfreebuy, 2)));

                } else if (n == 7) {
                    double totalebuycommfee = totaleresidentcommfreebuy + totalenonresidentcommfreebuy;

                    Cell f56 = row11.createCell(n + 1);
                    f56.setCellStyle(cellStylenum);
                    f56.setCellValue(fd(roundDoubleandFormat(totalebuycommfee, 2)));

                } else {

                    totaleresidentnonresperbuy.add(fd(datifooter.get(cntvalue)));

                    Cell f56 = row11.createCell(n + 1);
                    if (n == 9 || n == 10 || n == 14 || n == 17 || n == 20) {
                        f56.setCellStyle(cellStyleint);
                    }else {
                        f56.setCellStyle(cellStylenum);
                    }
                    
                    f56.setCellValue(fd(datifooter.get(cntvalue)));

                    cntvalue++;
                }

            }

            cntriga++;
            Row row12 = sheet.createRow((short) cntriga);

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
                    f56.setCellStyle(cellStyleint);
                    f56.setCellValue(fd(roundDoubleandFormat(tot, 0)));

                } else if (n == 1) {

                    Cell f56 = row12.createCell(n + 1);
                    f56.setCellStyle(cellStylenum);
                    f56.setCellValue(fd(roundDoubleandFormat(totaleresidentsell, 2)));

                } else if (n == 2) {

                    Cell f56 = row12.createCell(n + 1);
                    f56.setCellStyle(cellStylenum);
                    f56.setCellValue(fd(roundDoubleandFormat(totalenonresidentsell, 2)));

                } else if (n == 3) {

                    double totaleselltrans = totaleresidentsell + totalenonresidentsell;
                    Cell f56 = row12.createCell(n + 1);
                    f56.setCellStyle(cellStylenum);
                    f56.setCellValue(fd(roundDoubleandFormat(totaleselltrans, 2)));

                } else if (n == 5) {

                    Cell f56 = row12.createCell(n + 1);
                    f56.setCellStyle(cellStylenum);
                    f56.setCellValue(fd(roundDoubleandFormat(totaleresidentcommfreesell, 2)));

                } else if (n == 6) {

                    Cell f56 = row12.createCell(n + 1);
                    f56.setCellStyle(cellStylenum);
                    f56.setCellValue(fd(roundDoubleandFormat(totalenonresidentcommfreesell, 2)));

                } else if (n == 7) {
                    double totalesellcommfee = totaleresidentcommfreesell + totalenonresidentcommfreesell;

                    Cell f56 = row12.createCell(n + 1);
                    f56.setCellStyle(cellStylenum);
                    f56.setCellValue(fd(roundDoubleandFormat(totalesellcommfee, 2)));

                } else {
                    totaleresidentnonrespersell.add(fd(datifooter.get(cntvalue)));

                    Cell f56 = row12.createCell(n + 1);

                    if (n == 9 || n == 10 || n == 14 || n == 17 || n == 20) {
                        f56.setCellStyle(cellStyleint);
                    } else {
                        f56.setCellStyle(cellStylenum);
                    }

                    f56.setCellValue(fd(datifooter.get(cntvalue)));

                    cntvalue++;
                }

            }

            cntriga++;
            Row row13 = sheet.createRow((short) cntriga);

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

                    double tot1 = totaledeitotalibuy.get(cnttotali);
                    double tot2 = totaledeitotalisell.get(cnttotali);

                    Cell f56 = row13.createCell(n + 1);
                    f56.setCellStyle(cellStyleint);
                    f56.setCellValue(fd(roundDoubleandFormat(tot1 + tot2, 0)));

                    cnttotali++;
                } else if (n == 1) {

                    double totaleresidentsellbuy = totaleresidentbuy + totaleresidentsell;

                    Cell f56 = row13.createCell(n + 1);
                    f56.setCellStyle(cellStylenum);
                    f56.setCellValue(fd(roundDoubleandFormat(totaleresidentsellbuy, 2)));

                } else if (n == 2) {

                    double totalenonresidentbuysell = totalenonresidentbuy + totalenonresidentsell;

                    Cell f56 = row13.createCell(n + 1);
                    f56.setCellStyle(cellStylenum);
                    f56.setCellValue(fd(roundDoubleandFormat(totalenonresidentbuysell, 2)));

                } else if (n == 3) {

                    double totalebuysellgenerale = totaleresidentbuy + totalenonresidentbuy + totaleresidentsell + totalenonresidentsell;

                    Cell f56 = row13.createCell(n + 1);
                    f56.setCellStyle(cellStylenum);
                    f56.setCellValue(fd(roundDoubleandFormat(totalebuysellgenerale, 2)));

                } else if (n == 5) {

                    double totaleresidentcommfeebuysell = totaleresidentcommfreebuy + totaleresidentcommfreesell;

                    Cell f56 = row13.createCell(n + 1);
                    f56.setCellStyle(cellStylenum);
                    f56.setCellValue(fd(roundDoubleandFormat(totaleresidentcommfeebuysell, 2)));

                } else if (n == 6) {
                    double totalenonresidentcommfeebuysell = totalenonresidentcommfreebuy + totalenonresidentcommfreesell;

                    Cell f56 = row13.createCell(n + 1);
                    f56.setCellStyle(cellStylenum);
                    f56.setCellValue(fd(roundDoubleandFormat(totalenonresidentcommfeebuysell, 2)));

                } else if (n == 7) {
                    double totalegeneralecommfee = totaleresidentcommfreesell + totaleresidentcommfreebuy + totalenonresidentcommfreebuy + totalenonresidentcommfreesell;

                    Cell f56 = row13.createCell(n + 1);
                    f56.setCellStyle(cellStylenum);
                    f56.setCellValue(fd(roundDoubleandFormat(totalegeneralecommfee, 2)));

                } else {
                    double tot3 = (double) (totaleresidentnonresperbuy.get(cntresnonres));
                    double tot4 = (double) (totaleresidentnonrespersell.get(cntresnonres));

                    Cell f56 = row13.createCell(n + 1);
    
                    if (n == 9 || n == 10 || n == 14 || n == 17 || n == 20) {
                        f56.setCellStyle(cellStyleint);
                        f56.setCellValue(fd(roundDoubleandFormat(tot3 + tot4, 0)));
                    } else {
                        f56.setCellStyle(cellStylenum);
                        f56.setCellValue(fd(roundDoubleandFormat(tot3 + tot4, 2)));
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

    /**
     *
     * @param path
     * @param siq
     * @param datereport1
     * @param datereport2
     * @param intestazionePdf
     * @param bbtl
     * @return
     */
    public String receiptexcel_newreport(String path, TillTransactionList_value siq, String datereport1, String datereport2, String intestazionePdf, int bbtl) {

        try {
            File pdf = new File(path + generaId(50) + "ListTransactionPOS.xls");
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet("ListTransactionPOS");
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
            style3left.setAlignment(LEFT);
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
            style4left.setAlignment(LEFT);
            style4left.setBorderTop(THIN);
            style4left.setBorderBottom(THIN);

            HSSFCellStyle cellStylenum = (HSSFCellStyle) workbook.createCellStyle();
            HSSFDataFormat hssfDataFormat = (HSSFDataFormat) workbook.createDataFormat();
            cellStylenum.setDataFormat(hssfDataFormat.getFormat(formatdataCell));
            cellStylenum.setAlignment(HorizontalAlignment.RIGHT);
            cellStylenum.setBorderTop(THIN);
            cellStylenum.setBorderBottom(THIN);

            HSSFCellStyle cellStylenumint = (HSSFCellStyle) workbook.createCellStyle();
            cellStylenumint.setDataFormat(hssfDataFormat.getFormat(formatdataCellint));
            cellStylenumint.setAlignment(HorizontalAlignment.RIGHT);
            cellStylenumint.setBorderTop(THIN);
            cellStylenumint.setBorderBottom(THIN);

            HSSFCellStyle style3num = (HSSFCellStyle) workbook.createCellStyle();
            style3num.setFont(font3);
            style3num.setAlignment(HorizontalAlignment.RIGHT);
            style3num.setBorderTop(THIN);
            style3num.setBorderBottom(THIN);
            style3num.setDataFormat(hssfDataFormat.getFormat(formatdataCell));

            HSSFCellStyle style3numint = (HSSFCellStyle) workbook.createCellStyle();
            style3numint.setFont(font3);
            style3numint.setAlignment(HorizontalAlignment.RIGHT);
            style3numint.setBorderTop(THIN);
            style3numint.setBorderBottom(THIN);
            style3numint.setDataFormat(hssfDataFormat.getFormat(formatdataCellint));

            HSSFCellStyle cellStylenumrate = (HSSFCellStyle) workbook.createCellStyle();
            cellStylenumrate.setDataFormat(hssfDataFormat.getFormat(formatdataCellRATE));
            cellStylenumrate.setBorderBottom(THIN);
            cellStylenumrate.setBorderTop(THIN);
            cellStylenumrate.setBorderRight(THIN);
            cellStylenumrate.setBorderLeft(THIN);

            Row rowP = sheet.createRow((short) 1);

            Cell cl = rowP.createCell(1);
            cl.setCellStyle(style1);
            cl.setCellValue(intestazionePdf + " From " + datereport1 + " To " + datereport2);

            ArrayList<TillTransactionList_value> dati = siq.getDati();

            Row row6 = sheet.createRow((short) 5);

            Cell f2 = row6.createCell(0);
            f2.setCellStyle(style3left);
            f2.setCellValue("Branch");

            Cell f3 = row6.createCell(1);
            f3.setCellStyle(style3);
            f3.setCellValue("Type");

            Cell f4 = row6.createCell(2);
            f4.setCellStyle(style3);
            f4.setCellValue("User");

            Cell f5 = row6.createCell(3);
            f5.setCellStyle(style3);
            f5.setCellValue("No.Tr.");

            Cell f6 = row6.createCell(4);
            f6.setCellStyle(style3left);
            f6.setCellValue("Date / Time");

            Cell f7 = row6.createCell(5);
            f7.setCellStyle(style3left);
            f7.setCellValue("Cur");

            Cell f8 = row6.createCell(6);
            f8.setCellStyle(style3left);
            f8.setCellValue("Transaction Type");

            Cell f9 = row6.createCell(7);
            f9.setCellStyle(style3);
            f9.setCellValue("Amount / Quantity");

            Cell f10 = row6.createCell(8);
            f10.setCellStyle(style3);
            f10.setCellValue("Rate");

            Cell f11 = row6.createCell(9);
            f11.setCellStyle(style3);
            f11.setCellValue("Total");

            Cell f13 = row6.createCell(10);
            f13.setCellStyle(style3);
            f13.setCellValue("Fee");

            Cell f13bis = row6.createCell(11);
            f13bis.setCellStyle(style3);
            f13bis.setCellValue("Round");

            Cell f14 = row6.createCell(12);
            f14.setCellStyle(style3);
            f14.setCellValue("Pay In / Pay Out");

            Cell f15 = row6.createCell(13);
            f15.setCellStyle(style3left);
            f15.setCellValue("Customer");

            Cell f16 = row6.createCell(14);
            f16.setCellStyle(style3);
            f16.setCellValue("Spread");

            Cell f17 = row6.createCell(15);
            f17.setCellStyle(style3);
            f17.setCellValue("Pos / Bank Acc");

            Cell f18b = row6.createCell(16);
            f18b.setCellStyle(style3);
            f18b.setCellValue("Fig");

            double totalepayinoutgeneral = 0;

            double totround = 0;
            double totcommfee = 0;

            int cntriga = 5;

            for (int i = 0; i < dati.size(); i++) {
                cntriga++;
                Row row7 = sheet.createRow((short) cntriga);
                TillTransactionList_value actual = dati.get(i);
                Cell f18 = row7.createCell(0);
                f18.setCellStyle(style4left);
                f18.setCellValue(actual.getId_filiale());

                Cell f19 = row7.createCell(1);
                f19.setCellStyle(style4);
                f19.setCellValue(actual.getType());

                Cell f20 = row7.createCell(2);
                f20.setCellStyle(style4);
                f20.setCellValue(actual.getUser());

                Cell f21 = row7.createCell(3);
                f21.setCellStyle(style4);
                f21.setCellValue(actual.getNotr());

                Cell f22 = row7.createCell(4);
                f22.setCellStyle(style4left);
                f22.setCellValue(formatStringtoStringDate(actual.getTime(), patternsqldate, patternnormdate));

                Cell f23 = row7.createCell(5);
                f23.setCellStyle(style4left);
                f23.setCellValue(actual.getCur());

                Cell f24 = row7.createCell(6);
                f24.setCellStyle(style4left);
                f24.setCellValue(actual.getKind());

                Cell f25 = row7.createCell(7, NUMERIC);
                f25.setCellStyle(cellStylenum);
                f25.setCellValue(fd(actual.getAmount()));

                Cell f26 = row7.createCell(8, NUMERIC);
                f26.setCellStyle(cellStylenumrate);
                f26.setCellValue(fd(actual.getRate()));

                Cell f27 = row7.createCell(9, NUMERIC);
                f27.setCellStyle(cellStylenum);
                f27.setCellValue(fd(actual.getTotal()));

                Cell f29 = row7.createCell(10, NUMERIC);
                f29.setCellStyle(cellStylenum);
                f29.setCellValue(fd(actual.getComfree()));

                totcommfee = totcommfee + fd((valueOf(actual.getComfree())));

                Cell f29bis = row7.createCell(11, NUMERIC);
                f29bis.setCellStyle(cellStylenum);
                f29bis.setCellValue(fd(actual.getRound()));

                totround = totround + fd((valueOf(actual.getRound())));
                totround = roundDouble(totround, 2);

                Cell f30 = row7.createCell(12, NUMERIC);
                f30.setCellStyle(cellStylenum);

                if (actual.getType().contains("NO CH")) {
                    f30.setCellValue(fd("+" + (actual.getPayinpayout())));
                    totalepayinoutgeneral += fd(actual.getPayinpayoutSenzaFormattazione());

                } else {
                    if (actual.getKind().startsWith("S")) {
                        f30.setCellValue(fd("+" + (actual.getPayinpayout())));
                        totalepayinoutgeneral += fd(actual.getPayinpayoutSenzaFormattazione());
                    } else {
                        f30.setCellValue(fd("-" + (actual.getPayinpayout())));
                        totalepayinoutgeneral -= fd(actual.getPayinpayoutSenzaFormattazione());
                    }
                }

                Cell f31 = row7.createCell(13);
                f31.setCellStyle(style4left);
                f31.setCellValue(actual.getCustomer());

                Cell f32 = row7.createCell(14, NUMERIC);
                f32.setCellStyle(cellStylenum);
                f32.setCellValue(fd(actual.getSpread()));

                Cell f33 = row7.createCell(15);
                f33.setCellStyle(style4);
                f33.setCellValue(actual.getPos());

                Cell f33b = row7.createCell(16);
                f33b.setCellStyle(style4);
                f33b.setCellValue(actual.getFig());

            }

            cntriga++;
            Row row8 = sheet.createRow((short) cntriga);

            for (int v = 0; v < columnWidths2.length; v++) {

                if (v == 10) {

                    Cell f35 = row8.createCell(10, NUMERIC);
                    f35.setCellStyle(style3num);
                    f35.setCellValue((fd(roundDoubleandFormat(totcommfee, 2))));

                } else if (v == 11) {

                    Cell f35 = row8.createCell(11, NUMERIC);
                    f35.setCellStyle(style3num);
                    f35.setCellValue((fd(roundDoubleandFormat(totround, 2))));

                } else if (v == 12) {

                    Cell f36 = row8.createCell(12, NUMERIC);
                    f36.setCellStyle(style3num);
                    f36.setCellValue(fd(roundDoubleandFormat(totalepayinoutgeneral, 2)));

                } else {

                    Cell f37 = row8.createCell(v + 1);
                    f37.setCellValue("");

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
