/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rc.so.report;

import static com.itextpdf.text.BaseColor.LIGHT_GRAY;
import com.itextpdf.text.Chunk;
import static com.itextpdf.text.Chunk.TABBING;
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
import com.itextpdf.text.TabSettings;
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
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import static org.apache.poi.ss.usermodel.BorderStyle.THIN;
import static org.apache.poi.ss.usermodel.HorizontalAlignment.CENTER;
import static org.apache.poi.ss.usermodel.HorizontalAlignment.LEFT;
import static org.apache.poi.ss.usermodel.HorizontalAlignment.RIGHT;

/**
 *
 * @author vcrugliano
 */
public class ToPOSBASheet {

    //column

    /**
     *
     */
    public static final float[] columnWidths0 = new float[]{70f, 30f};

    /**
     *
     */
    public static final float[] columnWidths1 = new float[]{60f, 30f};
//    public static final float[] columnWidths3 = new float[]{18f, 15f, 19f, 5f, 5f, 25f};

    /**
     *
     */
    public static final float[] columnWidths4 = new float[]{18f, 18f, 18f, 18f, 18f, 18f};

    /**
     *
     */
    public static float[] columnWidths2 = null;
    final String intestazionePdf = "To Banking POS/Bank Account Sheet";
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

    Font f0_bold, f1_bold, f2_bold, f1_normal, f2_normal, f3_normal, f3_bold, f4_bold, f5_normal, f5_bold, f6_normal, f6_bold;

    /**
     * Costructor
     */
    public ToPOSBASheet() {

        this.f0_bold = getFont(HELVETICA, WINANSI, 14.04f, BOLD);
        this.f1_bold = getFont(HELVETICA, WINANSI, 9.00f, BOLD);
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
     * @param siq
     * @param colonne
     * @param datereport
     * @param document
     * @return
     */
    public Document receipt(ToBankingSheet_value siq, ArrayList<String> colonne, String datereport, Document document) {
        Phrase vuoto1 = new Phrase("\n");
        PdfPCell cellavuota = new PdfPCell(vuoto1);
        cellavuota.setBorder(NO_BORDER);

        // String outputfile = "ToBankingSheet.pdf";
        columnWidths2 = new float[colonne.size()];

        for (int c = 0; c < colonne.size(); c++) {
            columnWidths2[c] = 10f;
        }

        try {

            PdfPTable table = new PdfPTable(2);
            table.setWidths(columnWidths0);
            table.setWidthPercentage(100);

            Phrase phrase1 = new Phrase();
            phrase1.add(new Chunk(intestazionePdf + " ", f0_bold));
            PdfPCell cell1 = new PdfPCell(phrase1);
            cell1.setBorder(NO_BORDER);

            Paragraph pa1 = new Paragraph(new Phrase("Transfer " + siq.getNumTransfer() + " " + datereport, f3_bold));
            pa1.setAlignment(ALIGN_CENTER);
            PdfPCell cell2 = new PdfPCell(pa1);
            cell2.setBorder(NO_BORDER);
            cell2.setHorizontalAlignment(ALIGN_RIGHT);

            Phrase phrase3 = new Phrase();
            phrase3.add(new Chunk(siq.getId_filiale() + " - " + siq.getDe_filiale(), f1_bold));
            PdfPCell cell3 = new PdfPCell(phrase3);
            cell3.setBorder(NO_BORDER);

            String ggg = " \n From \t\t : " + siq.getFromsafe() + " \n To \t\t :" + siq.getTobank() + "\n User \t\t: " + siq.getPinuser();

            Phrase phrase4 = new Phrase();
            phrase4.add(new Chunk("\n From", f2_bold));
            phrase4.setTabSettings(new TabSettings(30f));
            phrase4.add(TABBING);
            phrase4.add(new Chunk(": " + siq.getFromsafe() + "\n To", f2_bold));
            phrase4.setTabSettings(new TabSettings(30f));
            phrase4.add(TABBING);
            phrase4.add(new Chunk(": " + siq.getTobank() + "\n User", f2_bold));
            phrase4.setTabSettings(new TabSettings(30f));
            phrase4.add(TABBING);
            phrase4.add(new Chunk(": " + siq.getPinuser(), f2_bold));

            PdfPCell cell4 = new PdfPCell(phrase4);

            cell4.setBorder(NO_BORDER);

            table.addCell(cell1);
            table.addCell(cell2);
            table.addCell(cell3);
            table.addCell(cellavuota);
            table.addCell(cell4);
            table.addCell(cellavuota);
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
                phraset1.add(new Chunk(colonne.get(j), f5_bold));
                PdfPCell cellt1 = new PdfPCell(phraset1);
                cellt1.setHorizontalAlignment(ALIGN_RIGHT);
                cellt1.setBorder(BOTTOM);

                cellt1.setBackgroundColor(LIGHT_GRAY);
                //cellt1.setBorder(Rectangle.BOTTOM);
                cellt1.setBorderWidth(0.7f);
                if (j == 0) {
                    cellt1.setHorizontalAlignment(ALIGN_LEFT);
                }
                list[j] = cellt1;

            }

            PdfPTable table6 = new PdfPTable(columnWidths4);
            table6.setWidthPercentage(100);

            for (int j = 0; j < 6; j++) {

                if (j == 1) {
                    Phrase phraset1 = new Phrase();
                    phraset1.add(new Chunk("04 – Cash Advance", f5_bold));
                    PdfPCell cellt1 = new PdfPCell(phraset1);
                    cellt1.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt1.setBorder(TOP);
                    cellt1.setBackgroundColor(LIGHT_GRAY);
                    //cellt1.setBorder(Rectangle.BOTTOM);
                    cellt1.setBorderWidth(0.7f);
                    table6.addCell(cellt1);
                } else if (j == 2) {
                    Phrase phraset1 = new Phrase();
                    phraset1.add(new Chunk("06 – Credit Card COP", f5_bold));
                    PdfPCell cellt1 = new PdfPCell(phraset1);
                    cellt1.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt1.setBorder(TOP);
                    cellt1.setBackgroundColor(LIGHT_GRAY);
                    //cellt1.setBorder(Rectangle.BOTTOM);
                    cellt1.setBorderWidth(0.7f);
                    table6.addCell(cellt1);
                } else if (j == 3) {
                    Phrase phraset1 = new Phrase();
                    phraset1.add(new Chunk("07 – Bancomat COP", f5_bold));
                    PdfPCell cellt1 = new PdfPCell(phraset1);
                    cellt1.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt1.setBorder(TOP);
                    cellt1.setBackgroundColor(LIGHT_GRAY);
                    //cellt1.setBorder(Rectangle.BOTTOM);
                    cellt1.setBorderWidth(0.7f);
                    table6.addCell(cellt1);
                } else if (j == 4) {
                    Phrase phraset1 = new Phrase();
                    phraset1.add(new Chunk("08 –  Bank Account", f5_bold));
                    PdfPCell cellt1 = new PdfPCell(phraset1);
                    cellt1.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt1.setBorder(TOP);
                    cellt1.setBackgroundColor(LIGHT_GRAY);
                    //cellt1.setBorder(Rectangle.BOTTOM);
                    cellt1.setBorderWidth(0.7f);
                    table6.addCell(cellt1);
                } else {
                    Phrase phraset1 = new Phrase();
                    phraset1.add(new Chunk("", f5_bold));
                    PdfPCell cellt1 = new PdfPCell(phraset1);
                    cellt1.setHorizontalAlignment(ALIGN_LEFT);
                    cellt1.setBorder(TOP);

                    cellt1.setBackgroundColor(LIGHT_GRAY);
                    //cellt1.setBorder(Rectangle.BOTTOM);
                    cellt1.setBorderWidth(0.7f);
                    table6.addCell(cellt1);
                }
            }

            document.add(table6);

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
            //  document.add(sep);
            ArrayList<ToBankingSheet_value> dati = siq.getDati();

            PdfPCell cell;
            Phrase phraset;
            PdfPCell cellt;

            table3 = new PdfPTable(colonne.size());
            table3.setWidths(columnWidths2);
            table3.setWidthPercentage(100);

            double branchtotal = 0, spreadtotal = 0, perctotal = 0;
            double num = 0.0;
            for (int i = 0; i < dati.size(); i++) {
                ToBankingSheet_value temp = dati.get(i);

                ArrayList<String> datitemp = temp.getDati_string();

                phraset = new Phrase();
                phraset.add(new Chunk(temp.getCurrency(), f2_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_LEFT);
                cellt.setBorder(BOTTOM);
                table3.addCell(cellt);
                for (int n = 1; n < colonne.size(); n++) {
                    if (colonne.get(n).equals("Bank Total")) {
                        branchtotal += fd(datitemp.get(n));
                    }
                    phraset = new Phrase();
                    phraset.add(new Chunk(formatMysqltoDisplay(datitemp.get(n)), f2_normal));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setBorder(BOTTOM);
                    table3.addCell(cellt);

                }

            }

            document.add(table3);

            LineSeparator ls = new LineSeparator();
            ls.setLineWidth(0.7f);
            ls.setOffset(-1f);
            document.add(ls);

            table3 = new PdfPTable(colonne.size());
            table3.setWidths(columnWidths2);
            table3.setWidthPercentage(100);

            phraset = new Phrase();
            phraset.add(new Chunk("", f1_normal));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_LEFT);
            cellt.setBorder(BOTTOM);
            table3.addCell(cellt);

            for (int n = 1; n < colonne.size(); n++) {

                if (n == 1) {
                    phraset = new Phrase();
                    phraset.add(new Chunk("Total", f3_bold));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_LEFT);
                    cellt.setBorder(BOTTOM);
                    table3.addCell(cellt);
                } else if (colonne.get(n).equals("Bank Total")) {
                    phraset = new Phrase();
                    phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(branchtotal, 2)), f3_normal));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setBorder(BOTTOM);
                    table3.addCell(cellt);

                } else if (colonne.get(n).equals("Spread")) {
                    phraset = new Phrase();
                    phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(spreadtotal, 2)), f3_normal));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setBorder(BOTTOM);
                    table3.addCell(cellt);

                } else if (colonne.get(n).equals("%")) {
                    phraset = new Phrase();
                    if (num > 0) {
                        phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat((perctotal / num), 2)) + " %", f3_normal));
                    } else {
                        phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(perctotal, 2)) + " %", f3_normal));
                    }
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setBorder(BOTTOM);
                    table3.addCell(cellt);

                } else {
                    phraset = new Phrase();
                    phraset.add(new Chunk("", f3_normal));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_LEFT);
                    cellt.setBorder(BOTTOM);
                    table3.addCell(cellt);
                }

            }

            document.add(table3);

            document.newPage();

            return document;

        } catch (DocumentException ex) {
            insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
        }
        return null;
    }

    /**
     *
     * @param path
     * @param siq
     * @param colonne
     * @param datereport
     * @return
     */
    public String receipt(String path, ToBankingSheet_value siq, ArrayList<String> colonne, String datereport) {

        Phrase vuoto1 = new Phrase("\n");
        PdfPCell cellavuota = new PdfPCell(vuoto1);
        cellavuota.setBorder(NO_BORDER);

        // String outputfile = "ToBankingSheet.pdf";
        columnWidths2 = new float[colonne.size()];

        for (int c = 0; c < colonne.size(); c++) {
            columnWidths2[c] = 10f;
        }

        try {

            File pdf = new File(path + generaId(50) + "ToPosBankAccountSheet.pdf");
            Document document = new Document(A4, 20, 20, 20, 20);
            try(OutputStream ou = new FileOutputStream(pdf)){
            PdfWriter wr = getInstance(document, ou);
            document.open();

            PdfPTable table = new PdfPTable(2);
            table.setWidths(columnWidths0);
            table.setWidthPercentage(100);

            Phrase phrase1 = new Phrase();
            phrase1.add(new Chunk(intestazionePdf + " ", f0_bold));
            PdfPCell cell1 = new PdfPCell(phrase1);
            cell1.setBorder(NO_BORDER);

            Paragraph pa1 = new Paragraph(new Phrase("Transfer " + siq.getNumTransfer() + " " + datereport, f3_bold));
            pa1.setAlignment(ALIGN_CENTER);
            PdfPCell cell2 = new PdfPCell(pa1);
            cell2.setBorder(NO_BORDER);
            cell2.setHorizontalAlignment(ALIGN_RIGHT);

            Phrase phrase3 = new Phrase();
            phrase3.add(new Chunk(siq.getId_filiale() + " - " + siq.getDe_filiale(), f1_bold));
            PdfPCell cell3 = new PdfPCell(phrase3);
            cell3.setBorder(NO_BORDER);

            String ggg = " \n From \t\t : " + siq.getFromsafe() + " \n To \t\t :" + siq.getTobank() + "\n User \t\t: " + siq.getPinuser();

            Phrase phrase4 = new Phrase();
            phrase4.add(new Chunk("\n From", f2_bold));
            phrase4.setTabSettings(new TabSettings(30f));
            phrase4.add(TABBING);
            phrase4.add(new Chunk(": " + siq.getFromsafe() + "\n To", f2_bold));
            phrase4.setTabSettings(new TabSettings(30f));
            phrase4.add(TABBING);
            phrase4.add(new Chunk(": " + siq.getTobank() + "\n User", f2_bold));
            phrase4.setTabSettings(new TabSettings(30f));
            phrase4.add(TABBING);
            phrase4.add(new Chunk(": " + siq.getPinuser(), f2_bold));

            PdfPCell cell4 = new PdfPCell(phrase4);

            cell4.setBorder(NO_BORDER);

            table.addCell(cell1);
            table.addCell(cell2);
            table.addCell(cell3);
            table.addCell(cellavuota);
            table.addCell(cell4);
            table.addCell(cellavuota);
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
                phraset1.add(new Chunk(colonne.get(j), f5_bold));
                PdfPCell cellt1 = new PdfPCell(phraset1);
                cellt1.setHorizontalAlignment(ALIGN_RIGHT);
                cellt1.setBorder(BOTTOM);

                cellt1.setBackgroundColor(LIGHT_GRAY);
                //cellt1.setBorder(Rectangle.BOTTOM);
                cellt1.setBorderWidth(0.7f);
                if (j == 0) {
                    cellt1.setHorizontalAlignment(ALIGN_LEFT);
                }
                list[j] = cellt1;

            }

            PdfPTable table6 = new PdfPTable(columnWidths4);
            table6.setWidthPercentage(100);

            for (int j = 0; j < 6; j++) {

                if (j == 1) {
                    Phrase phraset1 = new Phrase();
                    phraset1.add(new Chunk("04 – Cash Advance", f5_bold));
                    PdfPCell cellt1 = new PdfPCell(phraset1);
                    cellt1.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt1.setBorder(TOP);
                    cellt1.setBackgroundColor(LIGHT_GRAY);
                    //cellt1.setBorder(Rectangle.BOTTOM);
                    cellt1.setBorderWidth(0.7f);
                    table6.addCell(cellt1);
                } else if (j == 2) {
                    Phrase phraset1 = new Phrase();
                    phraset1.add(new Chunk("06 – Credit Card COP", f5_bold));
                    PdfPCell cellt1 = new PdfPCell(phraset1);
                    cellt1.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt1.setBorder(TOP);
                    cellt1.setBackgroundColor(LIGHT_GRAY);
                    //cellt1.setBorder(Rectangle.BOTTOM);
                    cellt1.setBorderWidth(0.7f);
                    table6.addCell(cellt1);
                } else if (j == 3) {
                    Phrase phraset1 = new Phrase();
                    phraset1.add(new Chunk("07 – Bancomat COP", f5_bold));
                    PdfPCell cellt1 = new PdfPCell(phraset1);
                    cellt1.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt1.setBorder(TOP);
                    cellt1.setBackgroundColor(LIGHT_GRAY);
                    //cellt1.setBorder(Rectangle.BOTTOM);
                    cellt1.setBorderWidth(0.7f);
                    table6.addCell(cellt1);
                } else if (j == 4) {
                    Phrase phraset1 = new Phrase();
                    phraset1.add(new Chunk("08 –  Bank Account", f5_bold));
                    PdfPCell cellt1 = new PdfPCell(phraset1);
                    cellt1.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt1.setBorder(TOP);
                    cellt1.setBackgroundColor(LIGHT_GRAY);
                    //cellt1.setBorder(Rectangle.BOTTOM);
                    cellt1.setBorderWidth(0.7f);
                    table6.addCell(cellt1);
                } else {
                    Phrase phraset1 = new Phrase();
                    phraset1.add(new Chunk("", f5_bold));
                    PdfPCell cellt1 = new PdfPCell(phraset1);
                    cellt1.setHorizontalAlignment(ALIGN_LEFT);
                    cellt1.setBorder(TOP);

                    cellt1.setBackgroundColor(LIGHT_GRAY);
                    //cellt1.setBorder(Rectangle.BOTTOM);
                    cellt1.setBorderWidth(0.7f);
                    table6.addCell(cellt1);
                }
            }

            document.add(table6);

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
            //  document.add(sep);
            ArrayList<ToBankingSheet_value> dati = siq.getDati();

            PdfPCell cell;
            Phrase phraset;
            PdfPCell cellt;

            table3 = new PdfPTable(colonne.size());
            table3.setWidths(columnWidths2);
            table3.setWidthPercentage(100);

            double branchtotal = 0, spreadtotal = 0, perctotal = 0;
            double num = 0.0;
            for (int i = 0; i < dati.size(); i++) {
                ToBankingSheet_value temp =  dati.get(i);

                ArrayList<String> datitemp = temp.getDati_string();

                phraset = new Phrase();
                phraset.add(new Chunk(temp.getCurrency(), f2_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_LEFT);
                cellt.setBorder(BOTTOM);
                table3.addCell(cellt);
                for (int n = 1; n < colonne.size(); n++) {
                    if (colonne.get(n).equals("Bank Total")) {
                        branchtotal += fd(datitemp.get(n));
                    }
                    phraset = new Phrase();
                    phraset.add(new Chunk(formatMysqltoDisplay(datitemp.get(n)), f2_normal));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setBorder(BOTTOM);
                    table3.addCell(cellt);

                }

            }

            document.add(table3);

            LineSeparator ls = new LineSeparator();
            ls.setLineWidth(0.7f);
            ls.setOffset(-1f);
            document.add(ls);

            table3 = new PdfPTable(colonne.size());
            table3.setWidths(columnWidths2);
            table3.setWidthPercentage(100);

            phraset = new Phrase();
            phraset.add(new Chunk("", f1_normal));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_LEFT);
            cellt.setBorder(BOTTOM);
            table3.addCell(cellt);

            for (int n = 1; n < colonne.size(); n++) {

                if (n == 1) {
                    phraset = new Phrase();
                    phraset.add(new Chunk("Total", f3_bold));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_LEFT);
                    cellt.setBorder(BOTTOM);
                    table3.addCell(cellt);
                } else if (colonne.get(n).equals("Bank Total")) {
                    phraset = new Phrase();
                    phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(branchtotal, 2)), f3_normal));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setBorder(BOTTOM);
                    table3.addCell(cellt);

                } else if (colonne.get(n).equals("Spread")) {
                    phraset = new Phrase();
                    phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(spreadtotal, 2)), f3_normal));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setBorder(BOTTOM);
                    table3.addCell(cellt);

                } else if (colonne.get(n).equals("%")) {
                    phraset = new Phrase();
                    if (num > 0) {
                        phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat((perctotal / num), 2)) + " %", f3_normal));
                    } else {
                        phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(perctotal, 2)) + " %", f3_normal));
                    }
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setBorder(BOTTOM);
                    table3.addCell(cellt);

                } else {
                    phraset = new Phrase();
                    phraset.add(new Chunk("", f3_normal));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_LEFT);
                    cellt.setBorder(BOTTOM);
                    table3.addCell(cellt);
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
     * @param siq
     * @param colonne
     * @param datereport
     * @param workbook
     * @param sheet
     * @param cntriga
     * @param style1
     * @param style2
     * @param style3
     * @param style3left
     * @param style3center
     * @param style4
     * @param style4left
     * @return
     */
    public int receiptexcel(ToBankingSheet_value siq, ArrayList<String> colonne, String datereport, 
            XSSFWorkbook workbook, 
            XSSFSheet sheet, int cntriga,
            XSSFCellStyle style1,
            XSSFCellStyle style2,
            XSSFCellStyle style3,
            XSSFCellStyle style3left,
            XSSFCellStyle style3center,
            XSSFCellStyle style4,
            XSSFCellStyle style4left) {

        try {

            Row rowP = sheet.createRow(cntriga);

            Cell cl = rowP.createCell(1);
            cl.setCellStyle(style1);
            cl.setCellValue(intestazionePdf);

            Cell cl32 = rowP.createCell(6);
            cl32.setCellStyle(style1);
            cl32.setCellValue("Transfer " + siq.getNumTransfer() + " " + datereport);
            
            cntriga++;
            
            Row row = sheet.createRow(cntriga);
            Cell c1l7 = row.createCell(1);
            c1l7.setCellStyle(style3);
            c1l7.setCellValue(siq.getId_filiale() + " - " + siq.getDe_filiale());
            
            
            cntriga++;
            cntriga++;
            cntriga++;

            Row rowP4 = sheet.createRow(cntriga);
            

            Cell cl4 = rowP4.createCell(1);
            cl4.setCellStyle(style3);
            cl4.setCellValue("From : ");
            
            Cell cl42 = rowP4.createCell(2);
            cl42.setCellStyle(style3);
            cl42.setCellValue(siq.getFromsafe());
            
            cntriga++;
            Row rowP5 = sheet.createRow(cntriga);

            Cell cl55 = rowP5.createCell(1);
            cl55.setCellStyle(style3);
            cl55.setCellValue("To: ");

            Cell cl552 = rowP5.createCell(2);
            cl552.setCellStyle(style3);
            cl552.setCellValue(siq.getTobank());
            cntriga++;
            Row rowP7 = sheet.createRow(cntriga);

            Cell cl7 = rowP7.createCell(1);
            cl7.setCellStyle(style3);
            cl7.setCellValue("User: ");

            Cell cl72 = rowP7.createCell(2);
            cl72.setCellStyle(style3);
            cl72.setCellValue(siq.getPinuser());

            
            cntriga++;
            Row row66 = sheet.createRow(cntriga);

            //mi scandisco le colonne
            for (int j = 0; j < colonne.size(); j++) {
                Cell cl5 = row66.createCell(j + 1);
                cl5.setCellStyle(style3);
                if (j == 0) {
                    cl5.setCellStyle(style3left);
                }
                cl5.setCellValue(colonne.get(j));

            }
            cntriga++;
            Row row77 = sheet.createRow(cntriga);
            row77.setHeight((short) 555);
            for (int j = 0; j < 6; j++) {

                if (j == 1) {

                    Cell cl5 = row77.createCell(j + 1);
                    cl5.setCellStyle(style3center);
                    cl5.setCellValue("04 – Cash Advance");

                } else if (j == 2) {
                    Cell cl5 = row77.createCell(j + 1);
                    cl5.setCellStyle(style3center);
                    cl5.setCellValue("06 – Credit Card COP");
                } else if (j == 3) {
                    Cell cl5 = row77.createCell(j + 1);
                    cl5.setCellStyle(style3center);
                    cl5.setCellValue("06 – Credit Card COP");
                } else if (j == 4) {
                    Cell cl5 = row77.createCell(j + 1);
                    cl5.setCellStyle(style3center);
                    cl5.setCellValue("08 – Bank Account");
                } else {

                }
            }

//            CellRangeAddress cellRangeAddress = new CellRangeAddress(8, 8, 2, 3);
//            sheet.addMergedRegion(cellRangeAddress);
//
//            cellRangeAddress = new CellRangeAddress(8, 8, 4, 5);
//            sheet.addMergedRegion(cellRangeAddress);

            cntriga++;

            //  document.add(table2);
            //  document.add(sep);
            ArrayList<ToBankingSheet_value> dati = siq.getDati();

            double branchtotal = 0;

            for (int i = 0; i < dati.size(); i++) {
                cntriga++;
                Row row6 = sheet.createRow((short) cntriga);

                ToBankingSheet_value temp = dati.get(i);

                ArrayList<String> datitemp = temp.getDati_string();

                Cell f1 = row6.createCell(1);
                f1.setCellStyle(style4left);
                f1.setCellValue(temp.getCurrency());

                for (int n = 1; n < colonne.size(); n++) {

                    if (colonne.get(n).equals("Bank Total")) {
                        branchtotal += fd((datitemp.get(n)));
                    }
                    Cell f2 = row6.createCell(n + 1);
                    f2.setCellStyle(style4);
                    f2.setCellValue(formatMysqltoDisplay(datitemp.get(n)));

                }

            }

            cntriga++;
            cntriga++;

            Row row9 = sheet.createRow((short) cntriga);

            for (int n = 1; n < colonne.size(); n++) {

                if (n == 1) {
                    Cell f7 = row9.createCell(n + 1);
                    f7.setCellStyle(style3);
                    f7.setCellValue("Total");
                } else if (colonne.get(n).equals("Bank Total")) {

                    Cell f7 = row9.createCell(n + 1);
                    f7.setCellStyle(style3);
                    f7.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(branchtotal, 2)));

                } 
            }

        } catch (Exception ex) {
            insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
        }
        cntriga++;
        cntriga++;
        cntriga++;
        cntriga++;
        cntriga++;
        return cntriga;
    }

    /**
     *
     * @param path
     * @param siq
     * @param colonne
     * @param datereport
     * @return
     */
    public String receiptexcel(String path, ToBankingSheet_value siq, ArrayList<String> colonne, String datereport) {

        // String outputfile = "ToBankingSheet.pdf";
        columnWidths2 = new float[colonne.size()];

        for (int c = 0; c < colonne.size(); c++) {
            columnWidths2[c] = 10f;
        }

        try {
            File pdf = new File(path + generaId(50) + "ToPosBankAccountSheet.xls");
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet("ToPosBankAccountSheet");
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
            style3.setAlignment(RIGHT);
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
            cl.setCellValue(intestazionePdf);

            Cell cl32 = rowP.createCell(6);
            cl32.setCellStyle(style2);
            cl32.setCellValue("Transfer " + siq.getNumTransfer() + " " + datereport);

//           
            Row rowP3 = sheet.createRow((short) 3);

//            Cell cl2 = rowP3.createCell(1);
//            cl2.setCellStyle(style2);
//            cl2.setCellValue("Prepared by: " );
//             Cell cl21 = rowP3.createCell(1);
//            cl21.setCellStyle(style2);
//            cl21.setCellValue(siq.getId_filiale() + " " + siq.getDe_filiale());
            Row rowP4 = sheet.createRow((short) 5);

            Cell cl4 = rowP4.createCell(1);
            cl4.setCellStyle(style2);
            cl4.setCellValue("From : ");

            Cell cl42 = rowP4.createCell(2);
            cl42.setCellStyle(style2);
            cl42.setCellValue(siq.getFromsafe());

            Row rowP5 = sheet.createRow((short) 6);

            Cell cl55 = rowP5.createCell(1);
            cl55.setCellStyle(style2);
            cl55.setCellValue("To: ");

            Cell cl552 = rowP5.createCell(2);
            cl552.setCellStyle(style2);
            cl552.setCellValue(siq.getTobank());

            Row rowP7 = sheet.createRow((short) 7);

            Cell cl7 = rowP7.createCell(1);
            cl7.setCellStyle(style2);
            cl7.setCellValue("User: ");

            Cell cl72 = rowP7.createCell(2);
            cl72.setCellStyle(style2);
            cl72.setCellValue(siq.getPinuser());

            Row row = sheet.createRow((short) 2);
            Cell c1l7 = row.createCell(1);
            c1l7.setCellStyle(style2);
            c1l7.setCellValue(siq.getId_filiale() + " - " + siq.getDe_filiale());

            Row row66 = sheet.createRow((short) 10);

            //mi scandisco le colonne
            for (int j = 0; j < colonne.size(); j++) {
                Cell cl5 = row66.createCell(j + 1);
                cl5.setCellStyle(style3);
                if (j == 0) {
                    cl5.setCellStyle(style3left);
                }
                cl5.setCellValue(colonne.get(j));

            }

            Row row77 = sheet.createRow((short) 8);
            row77.setHeight((short) 555);
            for (int j = 0; j < 6; j++) {

                if (j == 1) {

                    Cell cl5 = row77.createCell(j + 1);
                    cl5.setCellStyle(style3center);
                    cl5.setCellValue("04 – Cash Advance");

                } else if (j == 2) {
                    Cell cl5 = row77.createCell(j + 1);
                    cl5.setCellStyle(style3center);
                    cl5.setCellValue("06 – Credit Card COP");
                } else if (j == 3) {
                    Cell cl5 = row77.createCell(j + 1);
                    cl5.setCellStyle(style3center);
                    cl5.setCellValue("06 – Credit Card COP");
                } else if (j == 4) {
                    Cell cl5 = row77.createCell(j + 1);
                    cl5.setCellStyle(style3center);
                    cl5.setCellValue("08 – Bank Account");
                } else {

                }
            }

//            CellRangeAddress cellRangeAddress = new CellRangeAddress(8, 8, 2, 3);
//            sheet.addMergedRegion(cellRangeAddress);
//
//            cellRangeAddress = new CellRangeAddress(8, 8, 4, 5);
//            sheet.addMergedRegion(cellRangeAddress);
            int cntriga = 12;

            //  document.add(table2);
            //  document.add(sep);
            ArrayList<ToBankingSheet_value> dati = siq.getDati();

            double branchtotal = 0;

            for (int i = 0; i < dati.size(); i++) {
                cntriga++;
                Row row6 = sheet.createRow((short) cntriga);

                ToBankingSheet_value temp = dati.get(i);

                ArrayList<String> datitemp = temp.getDati_string();

                Cell f1 = row6.createCell(1);
                f1.setCellStyle(style4left);
                f1.setCellValue(temp.getCurrency());

                for (int n = 1; n < colonne.size(); n++) {

                    if (colonne.get(n).equals("Bank Total")) {
                        branchtotal += fd((datitemp.get(n)));
                    }
                    Cell f2 = row6.createCell(n + 1);
                    f2.setCellStyle(style4);
                    f2.setCellValue(formatMysqltoDisplay(datitemp.get(n)));

                }

            }

            cntriga++;
            cntriga++;

            Row row9 = sheet.createRow((short) cntriga);

            for (int n = 1; n < colonne.size(); n++) {

                if (n == 1) {
                    Cell f7 = row9.createCell(n + 1);
                    f7.setCellStyle(style3);
                    f7.setCellValue("Total");
                } else if (colonne.get(n).equals("Bank Total")) {

                    Cell f7 = row9.createCell(n + 1);
                    f7.setCellStyle(style3);
                    f7.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(branchtotal, 2)));

                } else {
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
