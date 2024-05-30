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
import static rc.so.util.Engine.insertTR;
import static rc.so.util.Utility.fd;
import static rc.so.util.Utility.ff;
import static rc.so.util.Utility.formatMysqltoDisplay;
import static rc.so.util.Utility.generaId;
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
import org.apache.poi.hssf.usermodel.HSSFFont;
import static org.apache.poi.hssf.usermodel.HSSFFont.FONT_ARIAL;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import static org.apache.poi.ss.usermodel.BorderStyle.THIN;
import org.apache.poi.ss.usermodel.HorizontalAlignment;

/**
 *
 * @author vcrugliano
 */
public class DeleteTransactionList {

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
    public static final float[] columnWidths2 = new float[]{3f, 3f, 4f, 5f, 3f, 6f, 6f, 6f, 4f, 6f, 6f, 5f, 5f, 7f, 5f, 3f};

    /**
     *
     */
    public static final float[] columnWidths3 = new float[]{25f, 7.5f, 19f, 8f, 20f};

    /**
     *
     */
    public static final float[] columnWidths4 = new float[]{3f, 6f, 6f, 5f, 3.5f, 6f, 6f, 6f, 3.5f, 6f, 6f, 6f};

    /**
     *
     */
    public static final float[] columnWidths5 = new float[]{25f, 25f, 25f, 25f};

    /**
     *
     */
    public static final float[] columnWidths6 = new float[]{20f, 20f, 20f, 20f, 20f};

    /**
     *
     */
    public static final float[] columnWidths7 = new float[]{3f, 3f, 4f, 5f, 3f, 6f, 6f, 6f, 5.5f, 6f, 6f, 5f, 5f, 7f, 5f, 3f};

    final String intestazionePdf = "Deleted TransactionList ";
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
    public DeleteTransactionList() {

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
            PdfPTable table2 = new PdfPTable(16);
            table2.setWidths(columnWidths2);
            table2.setWidthPercentage(100);

            Phrase phraset2 = new Phrase();
            phraset2.add(new Chunk("Type", f5_bold));
            PdfPCell cellt2 = new PdfPCell(phraset2);
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
            cellt2.setHorizontalAlignment(ALIGN_RIGHT);
            cellt2.setVerticalAlignment(ALIGN_MIDDLE);
            cellt2.setBorder(BOTTOM | TOP);
            cellt2.setBackgroundColor(LIGHT_GRAY);
            table2.addCell(cellt2);

            phraset2 = new Phrase();
            phraset2.add(new Chunk("Cur", f5_bold));
            cellt2 = new PdfPCell(phraset2);
            cellt2.setHorizontalAlignment(ALIGN_RIGHT);
            cellt2.setVerticalAlignment(ALIGN_MIDDLE);
            cellt2.setBorder(BOTTOM | TOP);
            cellt2.setBackgroundColor(LIGHT_GRAY);
            table2.addCell(cellt2);

            phraset2 = new Phrase();
            phraset2.add(new Chunk("Kind", f5_bold));
            cellt2 = new PdfPCell(phraset2);
            cellt2.setHorizontalAlignment(ALIGN_RIGHT);
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
            phraset2.add(new Chunk("Pos / Bank Account", f5_bold));
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
    public String receipt(String path, DeleteTransactionList_value siq, String datereport1, String datereport2) {

        // String outputfile = "DeleteTransactionList.pdf";
        try {
            File pdf = new File(path + generaId(50) + "DeleteTransactionList.pdf");
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
            PdfPTable table3, table6;
            ArrayList<DeleteTransactionList_value> dati = siq.getDati();
            Phrase phraset;
            PdfPCell cellt;

            table3 = new PdfPTable(16);
            table3.setWidths(columnWidths2);
            table3.setWidthPercentage(100);

            double totalesellresident = 0;
            double totalesellnonresident = 0;
            double totalebuyresident = 0;
            double totalebuynonresident = 0;
            double totalecommsellnonresident = 0;
            double totalecommbuynonresident = 0;
            double totalecommsellresident = 0;
            double totalecommbuyresident = 0;
            double totalepayinbuynonresident = 0;
            double totalepayoutsellnonresident = 0;
            double totalepayinbuyresident = 0;
            double totalepayoutsellresident = 0;

            for (int i = 0; i < dati.size(); i++) {

                DeleteTransactionList_value actual = dati.get(i);

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
                phraset.add(new Chunk(actual.getTime(), f5_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setVerticalAlignment(ALIGN_BOTTOM);
                cellt.setBorder(NO_BORDER);
                table3.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(actual.getCur(), f5_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setVerticalAlignment(ALIGN_BOTTOM);
                cellt.setBorder(NO_BORDER);
                table3.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(actual.getKind(), f5_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setVerticalAlignment(ALIGN_BOTTOM);
                cellt.setBorder(NO_BORDER);
                table3.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(actual.getAmount(), f5_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setVerticalAlignment(ALIGN_BOTTOM);
                cellt.setBorder(NO_BORDER);
                table3.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(actual.getRate(), f5_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setVerticalAlignment(ALIGN_BOTTOM);
                cellt.setBorder(NO_BORDER);
                table3.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(actual.getTotal(), f5_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setVerticalAlignment(ALIGN_BOTTOM);
                cellt.setBorder(NO_BORDER);
                table3.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(actual.getPerc(), f5_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setVerticalAlignment(ALIGN_BOTTOM);
                cellt.setBorder(NO_BORDER);
                table3.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(actual.getComfree(), f5_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setVerticalAlignment(ALIGN_BOTTOM);
                cellt.setBorder(NO_BORDER);
                table3.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(actual.getPayin(), f5_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setVerticalAlignment(ALIGN_BOTTOM);
                cellt.setBorder(NO_BORDER);
                table3.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(actual.getPayout(), f5_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setVerticalAlignment(ALIGN_BOTTOM);
                cellt.setBorder(NO_BORDER);
                table3.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(actual.getCustomer(), f5_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setVerticalAlignment(ALIGN_BOTTOM);
                cellt.setBorder(NO_BORDER);
                table3.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(actual.getSpread(), f5_normal));
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

                if (actual.getKind().contains("B")) {

                    if (actual.getResidentnoresident().equals("non resident")) {

                        totalebuynonresident += fd(actual.getTotalSenzaFormattazione());
                        totalecommbuynonresident += (fd(actual.getComfreeSenzaFormattazione()));
                        totalepayinbuynonresident += fd(actual.getPayinSenzaFormattazione());

                        siq.setTransvaluenonresidentbuy(valueOf(totalebuynonresident));
                    } else {

                        totalebuyresident += fd(actual.getTotalSenzaFormattazione());
                        totalecommbuyresident += fd(actual.getComfreeSenzaFormattazione());
                        totalepayinbuyresident += fd(actual.getPayinSenzaFormattazione());

                        siq.setTransvalueresidentbuy(valueOf(totalebuyresident));
                    }

                } else if (actual.getResidentnoresident().equals("non resident")) {

                    totalesellnonresident += fd(actual.getTotalSenzaFormattazione());
                    totalecommsellnonresident += fd(actual.getComfreeSenzaFormattazione());
                    totalepayoutsellnonresident += fd(actual.getPayoutSenzaFormattazione());

                    siq.setTransvaluenonresidentsell(valueOf(totalesellnonresident));
                } else {

                    totalesellresident += fd(actual.getTotalSenzaFormattazione());
                    totalecommsellresident += fd(actual.getComfreeSenzaFormattazione());
                    totalepayoutsellresident += fd(actual.getPayoutSenzaFormattazione());

                    siq.setTransvalueresidentsell(valueOf(totalesellresident));
                }

            }

            document.add(table3);

            LineSeparator ls = new LineSeparator();
            ls.setLineWidth(0.7f);
            document.add(ls);
            ls.setOffset(-1.5f);
            document.add(ls);

            table3 = new PdfPTable(16);
            table3.setWidths(columnWidths7);
            table3.setWidthPercentage(100);

            double totalegenerale = totalebuynonresident + totalesellnonresident + totalebuyresident + totalesellresident;
            double totalegeneralepayinout = totalepayinbuynonresident + totalepayoutsellnonresident + totalepayinbuyresident + totalepayoutsellresident;
            double totalegeneralecommfee = totalecommbuynonresident + totalecommbuyresident + totalecommsellnonresident + totalecommsellresident;

            for (int v = 0; v < 16; v++) {

                if (v == 8) {
                    phraset = new Phrase();
                    phraset.add(new Chunk(formatMysqltoDisplay(valueOf((totalegenerale))), f5_bold));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setVerticalAlignment(ALIGN_BOTTOM);
                    cellt.setBorder(NO_BORDER);
                    table3.addCell(cellt);
                } else {

                }
                if (v == 9) {
                    phraset = new Phrase();
                    phraset.add(new Chunk(formatMysqltoDisplay(valueOf((totalegeneralecommfee))), f5_bold));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setVerticalAlignment(ALIGN_BOTTOM);
                    cellt.setBorder(NO_BORDER);
                    table3.addCell(cellt);

                } else if (v == 11) {
                    phraset = new Phrase();
                    phraset.add(new Chunk(formatMysqltoDisplay(valueOf((totalegeneralepayinout))), f5_bold));
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

            table3 = new PdfPTable(5);
            table3.setWidths(columnWidths3);
            table3.setWidthPercentage(100);

            phraset = new Phrase();
            phraset.add(new Chunk("Transaction value", f5_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setVerticalAlignment(ALIGN_BOTTOM);
            cellt.setBorder(NO_BORDER);
            table3.addCell(cellt);

            phraset = new Phrase();
            phraset.add(new Chunk("", f5_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setVerticalAlignment(ALIGN_BOTTOM);
            cellt.setBorder(NO_BORDER);
            table3.addCell(cellt);

            phraset = new Phrase();
            phraset.add(new Chunk("Commissions value", f5_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setVerticalAlignment(ALIGN_BOTTOM);
            cellt.setBorder(NO_BORDER);
            table3.addCell(cellt);

            phraset = new Phrase();
            phraset.add(new Chunk("", f5_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setVerticalAlignment(ALIGN_BOTTOM);
            cellt.setBorder(NO_BORDER);
            table3.addCell(cellt);

            phraset = new Phrase();
            phraset.add(new Chunk("Transaction number", f5_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setVerticalAlignment(ALIGN_BOTTOM);
            cellt.setBorder(NO_BORDER);
            table3.addCell(cellt);

            table3.setSpacingBefore(15);

            document.add(table3);

            table3 = new PdfPTable(12);
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

            document.add(table3);

            table3 = new PdfPTable(12);
            table3.setWidths(columnWidths4);
            table3.setWidthPercentage(100);
            int cntvalue = 0;

            ArrayList<Double> datifoot = siq.getFooterdati();

            float transnumbuynores = 0, transnumbuyres = 0;

            int cnt = 0;
            for (int n = 0; n < columnWidths4.length; n++) {

                if (n == 0 || n == 4 || n == 8) {

                    phraset = new Phrase();
                    phraset.add(new Chunk("buy", f5_bold));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setVerticalAlignment(ALIGN_BOTTOM);
                    cellt.setBorder(BOX);
                    table3.addCell(cellt);

                } else if (n == 3) {
                    double tottemp = totalebuynonresident + totalebuyresident;
                    //devo metttere il totale
                    phraset = new Phrase();
                    phraset.add(new Chunk(formatMysqltoDisplay(valueOf(tottemp)), f6_bold));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setVerticalAlignment(ALIGN_BOTTOM);
                    cellt.setBorder(NO_BORDER);
                    table3.addCell(cellt);
                } else if (n == 7) {
                    double tottemp = totalecommbuyresident + totalecommbuynonresident;
                    //devo metttere il totale
                    phraset = new Phrase();
                    phraset.add(new Chunk(formatMysqltoDisplay(valueOf(tottemp)), f6_bold));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setVerticalAlignment(ALIGN_BOTTOM);
                    cellt.setBorder(NO_BORDER);
                    table3.addCell(cellt);
                } else if (n == 11) {
                    float tottemp = transnumbuyres + transnumbuynores;

                    //devo metttere il totale
                    phraset = new Phrase();
                    phraset.add(new Chunk(formatMysqltoDisplay(valueOf(tottemp)), f6_bold));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setVerticalAlignment(ALIGN_BOTTOM);
                    cellt.setBorder(RIGHT);
                    table3.addCell(cellt);
                } else {

                    float tottemp = ff(datifoot.get(cnt).toString());
                    cnt++;

                    if (n == 9) {
                        transnumbuyres = tottemp;
                    }
                    if (n == 10) {
                        transnumbuynores = tottemp;
                    }

                    phraset = new Phrase();
                    phraset.add(new Chunk(formatMysqltoDisplay(valueOf(tottemp)), f6_bold));
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

            table3 = new PdfPTable(12);
            table3.setWidths(columnWidths4);
            table3.setWidthPercentage(100);

            datifoot = new ArrayList<>();
            datifoot.add(totalesellresident);
            datifoot.add(totalesellnonresident);
            datifoot.add(totalecommsellresident);
            datifoot.add(totalecommsellnonresident);
            datifoot.add(-1.0);
            datifoot.add(-1.0);
            cnt = 0;

            float transnumsellres = 0;
            float transnumsellnores = 0;

            for (int n = 0; n < columnWidths4.length; n++) {

                if (n == 0 || n == 4 || n == 8) {

                    phraset = new Phrase();
                    phraset.add(new Chunk("sell", f5_bold));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setVerticalAlignment(ALIGN_BOTTOM);
                    cellt.setBorder(BOX);
                    table3.addCell(cellt);
                } else if (n == 3) {
                    double tottemp = totalesellnonresident + totalesellresident;
                    //devo metttere il totale
                    phraset = new Phrase();
                    phraset.add(new Chunk(formatMysqltoDisplay(valueOf(tottemp)), f6_bold));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setVerticalAlignment(ALIGN_BOTTOM);
                    cellt.setBorder(NO_BORDER);
                    table3.addCell(cellt);
                } else if (n == 7) {
                    double tottemp = totalecommsellresident + totalecommsellnonresident;
                    //devo metttere il totale
                    phraset = new Phrase();
                    phraset.add(new Chunk(formatMysqltoDisplay(valueOf(tottemp)), f6_bold));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setVerticalAlignment(ALIGN_BOTTOM);
                    cellt.setBorder(NO_BORDER);
                    table3.addCell(cellt);
                } else if (n == 11) {
                    float tottemp = transnumsellres + transnumsellnores;
                    //devo metttere il totale
                    phraset = new Phrase();
                    phraset.add(new Chunk(formatMysqltoDisplay(valueOf(tottemp)), f6_bold));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setVerticalAlignment(ALIGN_BOTTOM);
                    cellt.setBorder(RIGHT);
                    table3.addCell(cellt);
                } else {
                    float tottemp = ff(datifoot.get(cnt).toString());
                    cnt++;

                    if (n == 9) {
                        transnumsellres = tottemp;
                    }
                    if (n == 10) {
                        transnumsellnores = tottemp;
                    }

                    phraset = new Phrase();
                    phraset.add(new Chunk(formatMysqltoDisplay(valueOf(tottemp)), f6_bold));
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

            table3 = new PdfPTable(12);
            table3.setWidths(columnWidths4);
            table3.setWidthPercentage(100);

            int cnttotali = 0;
            int cntresnonres = 0;

            datifoot = new ArrayList<>();
            datifoot.add(totalesellresident);
            datifoot.add(totalesellnonresident);
            datifoot.add(totalecommsellresident);
            datifoot.add(totalecommsellnonresident);
            datifoot.add(fd(valueOf(transnumbuyres + transnumsellres)));
            datifoot.add(fd(valueOf(transnumbuynores + transnumsellnores)));
            cnt = 0;

            for (int n = 0; n < columnWidths4.length; n++) {

                if (n == 0 || n == 4 || n == 8) {

                    phraset = new Phrase();
                    phraset.add(new Chunk("total", f5_bold));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setVerticalAlignment(ALIGN_BOTTOM);
                    cellt.setBorder(BOX);
                    table3.addCell(cellt);
                } else if (n == 3) {

                    double tottemp = totalebuyresident + totalebuynonresident + totalesellresident + totalesellnonresident;

                    phraset = new Phrase();
                    phraset.add(new Chunk(formatMysqltoDisplay(valueOf(tottemp)), f6_bold));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setVerticalAlignment(ALIGN_BOTTOM);
                    cellt.setBorder(BOX);
                    if (n == columnWidths4.length - 1) {
                        cellt.setBorder(RIGHT | BOX);
                    }
                    table3.addCell(cellt);
                    cnttotali++;
                } else if (n == 7) {

                    double tottemp = totalecommbuyresident + totalecommbuynonresident + totalecommsellresident + totalecommsellnonresident;

                    phraset = new Phrase();
                    phraset.add(new Chunk(formatMysqltoDisplay(valueOf(tottemp)), f6_bold));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setVerticalAlignment(ALIGN_BOTTOM);
                    cellt.setBorder(BOX);
                    if (n == columnWidths4.length - 1) {
                        cellt.setBorder(RIGHT | BOX);
                    }
                    table3.addCell(cellt);
                    cnttotali++;
                } else if (n == 11) {

                    float tottemp = transnumbuyres + transnumsellres + transnumbuynores + transnumsellnores;

                    phraset = new Phrase();
                    phraset.add(new Chunk(formatMysqltoDisplay(valueOf(tottemp)), f6_bold));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setVerticalAlignment(ALIGN_BOTTOM);
                    cellt.setBorder(BOX);
                    if (n == columnWidths4.length - 1) {
                        cellt.setBorder(RIGHT | BOX);
                    }
                    table3.addCell(cellt);
                    cnttotali++;
                } else {

                    float t = ff(datifoot.get(cnt).toString());
                    cnt++;

                    phraset = new Phrase();
                    phraset.add(new Chunk(formatMysqltoDisplay(valueOf(t)), f6_bold));
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
     * @return
     */
    public String receiptexcel(String path, DeleteTransactionList_value siq, String datereport1, String datereport2) {

        // String outputfile = "DeleteTransactionList.pdf";
        try {
            File pdf = new File(path + generaId(50) + "HeavyTransactionList.xls");
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet("DeleteTransactionList");
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

            HSSFFont font4 = workbook.createFont();
            font4.setFontName(FONT_ARIAL);
            font4.setFontHeightInPoints((short) 10);

            HSSFCellStyle style4 = (HSSFCellStyle) workbook.createCellStyle();
            style4.setAlignment(HorizontalAlignment.RIGHT);
            style4.setBorderTop(THIN);
            style4.setBorderBottom(THIN);

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
            f2.setCellStyle(style3);
            f2.setCellValue("Till");

            Cell f3 = row6.createCell(2);
            f3.setCellStyle(style3);
            f3.setCellValue("User");

            Cell f4 = row6.createCell(3);
            f4.setCellStyle(style3);
            f4.setCellValue("No.Tr.");

            Cell f5 = row6.createCell(4);
            f5.setCellStyle(style3);
            f5.setCellValue("Time");

            Cell f6 = row6.createCell(5);
            f6.setCellStyle(style3);
            f6.setCellValue("Cur");

            Cell f7 = row6.createCell(6);
            f7.setCellStyle(style3);
            f7.setCellValue("Kind");

            Cell f8 = row6.createCell(7);
            f8.setCellStyle(style3);
            f8.setCellValue("Amount");

            Cell f9 = row6.createCell(8);
            f9.setCellStyle(style3);
            f9.setCellValue("Rate");

            Cell f10 = row6.createCell(9);
            f10.setCellStyle(style3);
            f10.setCellValue("Total");

            Cell f11 = row6.createCell(10);
            f11.setCellStyle(style3);
            f11.setCellValue("%");

            Cell f12 = row6.createCell(11);
            f12.setCellStyle(style3);
            f12.setCellValue("Comm.Fee");

            Cell f13 = row6.createCell(12);
            f13.setCellStyle(style3);
            f13.setCellValue("Pay In");

            Cell f14 = row6.createCell(13);
            f14.setCellStyle(style3);
            f14.setCellValue("Pay Out");

            Cell f15 = row6.createCell(14);
            f15.setCellStyle(style3);
            f15.setCellValue("Customer");

            Cell f16 = row6.createCell(15);
            f16.setCellStyle(style3);
            f16.setCellValue("Spread");

            Cell f17 = row6.createCell(16);
            f17.setCellStyle(style3);
            f17.setCellValue("Fig");

            ArrayList<DeleteTransactionList_value> dati = siq.getDati();

            float totalesellresident = 0;
            float totalesellnonresident = 0;
            float totalebuyresident = 0;
            float totalebuynonresident = 0;
            float totalecommsellnonresident = 0;
            float totalecommbuynonresident = 0;
            float totalecommsellresident = 0;
            float totalecommbuyresident = 0;
            float totalepayinbuynonresident = 0;
            float totalepayoutsellnonresident = 0;
            float totalepayinbuyresident = 0;
            float totalepayoutsellresident = 0;

            int cntriga = 7;

            for (int i = 0; i < dati.size(); i++) {
                cntriga++;
                Row row7 = sheet.createRow((short) cntriga);

                DeleteTransactionList_value actual = (DeleteTransactionList_value) dati.get(i);

                Cell f18 = row7.createCell(1);
                f18.setCellStyle(style4);
                f18.setCellValue(actual.getTill());

                Cell f19 = row7.createCell(2);
                f19.setCellStyle(style4);
                f19.setCellValue(actual.getUser());

                Cell f20 = row7.createCell(3);
                f20.setCellStyle(style4);
                f20.setCellValue(actual.getNotr());

                Cell f21 = row7.createCell(4);
                f21.setCellStyle(style4);
                f21.setCellValue(actual.getTime());

                Cell f22 = row7.createCell(5);
                f22.setCellStyle(style4);
                f22.setCellValue(actual.getCur());

                Cell f23 = row7.createCell(6);
                f23.setCellStyle(style4);
                f23.setCellValue(actual.getKind());

                Cell f24 = row7.createCell(7);
                f24.setCellStyle(style4);
                f24.setCellValue(actual.getAmount());

                Cell f25 = row7.createCell(8);
                f25.setCellStyle(style4);
                f25.setCellValue(actual.getRate());

                Cell f26 = row7.createCell(9);
                f26.setCellStyle(style4);
                f26.setCellValue(actual.getTotal());

                Cell f27 = row7.createCell(10);
                f27.setCellStyle(style4);
                f27.setCellValue(actual.getPerc());

                Cell f28 = row7.createCell(11);
                f28.setCellStyle(style4);
                f28.setCellValue(actual.getComfree());

                Cell f29 = row7.createCell(12);
                f29.setCellStyle(style4);
                f29.setCellValue(actual.getPayin());

                Cell f31 = row7.createCell(13);
                f31.setCellStyle(style4);
                f31.setCellValue(actual.getPayout());

                Cell f32 = row7.createCell(14);
                f32.setCellStyle(style4);
                f32.setCellValue(actual.getCustomer());

                Cell f33 = row7.createCell(15);
                f33.setCellStyle(style4);
                f33.setCellValue(actual.getSpread());

                Cell f34 = row7.createCell(16);
                f34.setCellStyle(style4);
                f34.setCellValue(actual.getFig());

                if (actual.getKind().contains("B")) {

                    if (actual.getResidentnoresident().equals("non resident")) {

                        totalebuynonresident += ff(actual.getTotalSenzaFormattazione());
                        totalecommbuynonresident += ff(actual.getComfreeSenzaFormattazione());
                        totalepayinbuynonresident += ff(actual.getPayinSenzaFormattazione());

                        siq.setTransvaluenonresidentbuy(valueOf(totalebuynonresident));
                    } else {

                        totalebuyresident += ff(actual.getTotalSenzaFormattazione());
                        totalecommbuyresident += ff(actual.getComfreeSenzaFormattazione());
                        totalepayinbuyresident += ff(actual.getPayinSenzaFormattazione());

                        siq.setTransvalueresidentbuy(valueOf(totalebuyresident));
                    }

                } else if (actual.getResidentnoresident().equals("non resident")) {

                    totalesellnonresident += ff(actual.getTotalSenzaFormattazione());
                    totalecommsellnonresident += ff(actual.getComfreeSenzaFormattazione());
                    totalepayoutsellnonresident += ff(actual.getPayoutSenzaFormattazione());

                    siq.setTransvaluenonresidentsell(valueOf(totalesellnonresident));
                } else {

                    totalesellresident += ff(actual.getTotalSenzaFormattazione());
                    totalecommsellresident += ff(actual.getComfreeSenzaFormattazione());
                    totalepayoutsellresident += ff(actual.getPayoutSenzaFormattazione());

                    siq.setTransvalueresidentsell(valueOf(totalesellresident));
                }

            }

            cntriga++;
            cntriga++;

            Row row8 = sheet.createRow((short) cntriga);

            float totalegenerale = totalebuynonresident + totalesellnonresident + totalebuyresident + totalesellresident;
            float totalegeneralepayinout = totalepayinbuynonresident + totalepayoutsellnonresident + totalepayinbuyresident + totalepayoutsellresident;
            float totalegeneralecommfee = totalecommbuynonresident + totalecommbuyresident + totalecommsellnonresident + totalecommsellresident;

            for (int v = 0; v < 16; v++) {

                if (v == 8) {

                    Cell f35 = row8.createCell(v + 1);
                    f35.setCellStyle(style3);
                    f35.setCellValue(formatMysqltoDisplay(valueOf((totalegenerale))));

                } else {

                }
                if (v == 9) {
                    Cell f35 = row8.createCell(v + 2);
                    f35.setCellStyle(style3);
                    f35.setCellValue(formatMysqltoDisplay(valueOf((totalegeneralecommfee))));

                } else if (v == 11) {
                    Cell f35 = row8.createCell(v + 2);
                    f35.setCellStyle(style3);
                    f35.setCellValue(formatMysqltoDisplay(valueOf((totalegeneralepayinout))));

                } else {
//                    phraset = new Phrase();
//                    phraset.add(new Chunk("", f5_bold));
//                    cellt = new PdfPCell(phraset);
//                    cellt.setHorizontalAlignment(Element.ALIGN_RIGHT);
//                    cellt.setVerticalAlignment(Element.ALIGN_BOTTOM);
//                    cellt.setBorder(Rectangle.NO_BORDER);
//                    table3.addCell(cellt);
                }

            }

            cntriga++;
            cntriga++;
            Row row9 = sheet.createRow((short) cntriga);

            Cell f38 = row9.createCell(3);
            f38.setCellStyle(style3);
            f38.setCellValue("Transaction value");

            Cell f39 = row9.createCell(6);
            f39.setCellStyle(style3);
            f39.setCellValue("Commissions value");

            Cell f40 = row9.createCell(10);
            f40.setCellStyle(style3);
            f40.setCellValue("Transaction number");

            cntriga++;
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

            int cntvalue = 0;

            ArrayList<Double> datifoot = siq.getFooterdati();

            cntriga++;
            Row row11 = sheet.createRow((short) cntriga);

            int cnt = 0;
            float transnumbuyres = 0, transnumbuynores = 0;
            for (int n = 0; n < columnWidths4.length; n++) {

                if (n == 0 || n == 4 || n == 8) {

                    Cell f56 = row11.createCell(n + 1);
                    f56.setCellStyle(style3);
                    f56.setCellValue("buy");

                } else if (n == 3) {
                    float tottemp = totalebuynonresident + totalebuyresident;

                    Cell f56 = row11.createCell(n + 1);
                    f56.setCellStyle(style4);
                    f56.setCellValue(formatMysqltoDisplay(valueOf(tottemp)));

                    //devo metttere il totale
                } else if (n == 7) {
                    float tottemp = totalecommbuyresident + totalecommbuynonresident;

                    Cell f56 = row11.createCell(n + 1);
                    f56.setCellStyle(style4);
                    f56.setCellValue(formatMysqltoDisplay(valueOf(tottemp)));

                } else if (n == 11) {
                    float tottemp = transnumbuyres + transnumbuynores;

                    Cell f56 = row11.createCell(n + 1);
                    f56.setCellStyle(style3);
                    f56.setCellValue(formatMysqltoDisplay(valueOf(tottemp)));

                } else {

                    float tottemp = ff(datifoot.get(cnt).toString());
                    cnt++;

                    if (n == 9) {
                        transnumbuyres = tottemp;
                    }
                    if (n == 10) {
                        transnumbuynores = tottemp;
                    }

                    Cell f56 = row11.createCell(n + 1);
                    f56.setCellStyle(style4);
                    f56.setCellValue(formatMysqltoDisplay(valueOf(tottemp)));

                    cntvalue++;
                }

            }

            cntriga++;
            Row row12 = sheet.createRow((short) cntriga);

            datifoot = new ArrayList<>();
            datifoot.add(fd(valueOf(totalesellresident)));
            datifoot.add(fd(valueOf(totalesellnonresident)));
            datifoot.add(fd(valueOf(totalecommsellresident)));
            datifoot.add(fd(valueOf(totalecommsellnonresident)));
            datifoot.add(-1.0);
            datifoot.add(-1.0);
            cnt = 0;

            float transnumsellnores = 0, transnumsellres = 0;

            for (int n = 0; n < columnWidths4.length; n++) {

                if (n == 0 || n == 4 || n == 8) {

                    Cell f56 = row12.createCell(n + 1);
                    f56.setCellStyle(style3);
                    f56.setCellValue("sell");

                } else if (n == 3) {
                    float tottemp = totalesellnonresident + totalesellresident;

                    Cell f56 = row12.createCell(n + 1);
                    f56.setCellStyle(style4);
                    f56.setCellValue(formatMysqltoDisplay(valueOf(tottemp)));

                    //devo metttere il totale
                } else if (n == 7) {
                    float tottemp = totalecommsellresident + totalecommsellnonresident;

                    Cell f56 = row12.createCell(n + 1);
                    f56.setCellStyle(style4);
                    f56.setCellValue(formatMysqltoDisplay(valueOf(tottemp)));

                    //devo metttere il totale
                } else if (n == 11) {
                    float tottemp = transnumsellres + transnumsellnores;

                    Cell f56 = row12.createCell(n + 1);
                    f56.setCellStyle(style4);
                    f56.setCellValue(formatMysqltoDisplay(valueOf(tottemp)));

                    //devo metttere il totale
                } else {
                    float tottemp = ff(datifoot.get(cnt).toString());
                    cnt++;

                    if (n == 9) {
                        transnumsellres = tottemp;
                    }
                    if (n == 10) {
                        transnumsellnores = tottemp;
                    }

                    Cell f56 = row12.createCell(n + 1);
                    f56.setCellStyle(style4);
                    f56.setCellValue(formatMysqltoDisplay(valueOf(tottemp)));

//                  
                    cntvalue++;
                }

            }

            cntriga++;
            Row row13 = sheet.createRow((short) cntriga);

            int cnttotali = 0;
            int cntresnonres = 0;

            datifoot = new ArrayList<>();
            datifoot.add(fd(valueOf(totalesellresident)));
            datifoot.add(fd(valueOf(totalesellnonresident)));
            datifoot.add(fd(valueOf(totalecommsellresident)));
            datifoot.add(fd(valueOf(totalecommsellnonresident)));
            datifoot.add(fd(valueOf(transnumbuyres + transnumsellres)));
            datifoot.add(fd(valueOf(transnumbuynores + transnumsellnores)));
            cnt = 0;

            for (int n = 0; n < columnWidths4.length; n++) {

                if (n == 0 || n == 4 || n == 8) {

                    Cell f56 = row13.createCell(n + 1);
                    f56.setCellStyle(style3);
                    f56.setCellValue("total");
                } else if (n == 3) {

                    float tottemp = totalebuyresident + totalebuynonresident + totalesellresident + totalesellnonresident;

                    Cell f56 = row13.createCell(n + 1);
                    f56.setCellStyle(style4);
                    f56.setCellValue(formatMysqltoDisplay(valueOf(tottemp)));

                    cnttotali++;
                } else if (n == 7) {

                    float tottemp = totalecommbuyresident + totalecommbuynonresident + totalecommsellresident + totalecommsellnonresident;

                    Cell f56 = row13.createCell(n + 1);
                    f56.setCellStyle(style4);
                    f56.setCellValue(formatMysqltoDisplay(valueOf(tottemp)));

                    cnttotali++;
                } else if (n == 11) {

                    float tottemp = transnumbuyres + transnumbuynores + transnumsellres + transnumsellnores;

                    Cell f56 = row13.createCell(n + 1);
                    f56.setCellStyle(style4);
                    f56.setCellValue(formatMysqltoDisplay(valueOf(tottemp)));

                    cnttotali++;
                } else {

                    float t = ff(datifoot.get(cnt).toString());
                    cnt++;

                    Cell f56 = row13.createCell(n + 1);
                    f56.setCellStyle(style4);
                    f56.setCellValue(formatMysqltoDisplay(valueOf(t)));

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
