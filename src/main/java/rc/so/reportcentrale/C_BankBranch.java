/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rc.so.reportcentrale;

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
import rc.so.db.Db_Master;
import rc.so.entity.Branch;
import rc.so.entity.ET_change;
import static rc.so.entity.ET_change.format_tofrom_brba;
import rc.so.report.FromBranchingSheet_value;
import rc.so.report.NoChangeFromBranchingSheet_value;
import rc.so.report.NoChangeToBranchingSheet_value;
import rc.so.report.ToBankingSheet_value;
import rc.so.report.ToBranchingSheet_value;
import rc.so.report.ToPOSBASheet;
import static rc.so.util.Constant.formatdataCell;
import static rc.so.util.Constant.formatdataCellRate;
import static rc.so.util.Constant.patternnormdate;
import static rc.so.util.Constant.patternsqldate;
import static rc.so.util.Engine.formatBankBranch;
import static rc.so.util.Engine.formatBankBranchReport;
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
import static java.lang.Thread.currentThread;
import java.util.ArrayList;
import static org.apache.commons.codec.binary.Base64.encodeBase64;
import static org.apache.commons.io.FileUtils.readFileToByteArray;
import static org.apache.poi.hssf.usermodel.HSSFFont.FONT_ARIAL;
import static org.apache.poi.ss.usermodel.BorderStyle.THIN;
import org.apache.poi.ss.usermodel.Cell;
import static org.apache.poi.ss.usermodel.CellType.NUMERIC;
import static org.apache.poi.ss.usermodel.HorizontalAlignment.CENTER;
import static org.apache.poi.ss.usermodel.HorizontalAlignment.LEFT;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFDataFormat;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author vcrugliano
 */
public class C_BankBranch {

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
    public static final float[] columnWidths3 = new float[]{18f, 15f, 19f, 5f, 5f, 25f};

    /**
     *
     */
    public static float[] columnWidths2 = null;

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

    Font f0_bold, f1_bold, f2_bold, f1_normal, f2_normal, f2_normalbis, f3_normal, f3_bold, f4_bold, f5_normal, f5_bold, f6_normal, f6_bold;

    /**
     * Constructor
     */
    public C_BankBranch() {

        this.f0_bold = getFont(HELVETICA, WINANSI, 14.04f, BOLD);
        this.f1_bold = getFont(HELVETICA, WINANSI, 9.96f, BOLD);
        this.f2_bold = getFont(HELVETICA, WINANSI, 8f, BOLD);
        this.f3_bold = getFont(HELVETICA, WINANSI, 8f, BOLDITALIC);
        this.f4_bold = getFont(HELVETICA, WINANSI, 6.96f, BOLD);
        this.f1_normal = getFont(HELVETICA, WINANSI, 9.96f, NORMAL);
        this.f2_normal = getFont(HELVETICA, WINANSI, 8f, NORMAL);
        this.f2_normalbis = getFont(HELVETICA, WINANSI, 8f, BOLD);
        this.f3_normal = getFont(HELVETICA, WINANSI, 6.96f, NORMAL);
        this.f5_normal = getFont(HELVETICA, WINANSI, 6f, NORMAL);
        this.f5_bold = getFont(HELVETICA, WINANSI, 6f, BOLD);
        this.f6_normal = getFont(HELVETICA, WINANSI, 5.5f, NORMAL);
        this.f6_bold = getFont(HELVETICA, WINANSI, 5.5f, BOLD);

    }

    /**
     *
     * @param intestazionePdf
     * @param siq
     * @param datereport
     * @param document
     * @return
     */
    public Document receiptbank(String intestazionePdf, ToBankingSheet_value siq, String datereport, Document document) {

        ArrayList<String> colonne = new ArrayList<>();
        colonne.add("Currency");
        colonne.add("Amount");
        colonne.add("Bank Rate");
        colonne.add("Amount");
        colonne.add("Bank Rate");
        colonne.add("Bank Total");
        colonne.add("Spread");
        colonne.add("%");

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
            phrase1.add(new Chunk(intestazionePdf + " ", f3_bold));
            PdfPCell cell1 = new PdfPCell(phrase1);
            cell1.setBorder(NO_BORDER);

            Paragraph pa1 = new Paragraph(new Phrase("Transfer " + siq.getNumTransfer() + " " + datereport, f2_normal));
            pa1.setAlignment(ALIGN_CENTER);
            PdfPCell cell2 = new PdfPCell(pa1);
            cell2.setBorder(NO_BORDER);
            cell2.setHorizontalAlignment(ALIGN_RIGHT);

            Phrase phrase3 = new Phrase();
            phrase3.add(new Chunk(siq.getId_filiale() + " " + siq.getDe_filiale(), f2_normalbis));
            PdfPCell cell3 = new PdfPCell(phrase3);
            cell3.setBorder(NO_BORDER);

            Phrase phrase4 = new Phrase();
            phrase4.add(new Chunk("\n From : " + siq.getFromsafe() + " \n To : " + siq.getTobank() + " \n User: " + siq.getPinuser(), f2_normalbis));
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
                cellt1.setBorder(NO_BORDER);

                cellt1.setBackgroundColor(LIGHT_GRAY);
                //cellt1.setBorder(Rectangle.BOTTOM);
                cellt1.setBorderWidth(0.7f);

                list[j] = cellt1;

            }

            PdfPTable table6 = new PdfPTable(columnWidths3);
            table6.setWidthPercentage(100);

//            for (int j = 0; j < 6; j++) {
//                if (j == 1) {
            Phrase phraset1 = new Phrase();
            phraset1.add(new Chunk("Notes", f5_bold));
            PdfPCell cellt1 = new PdfPCell(phraset1);
            cellt1.setHorizontalAlignment(ALIGN_CENTER);
            cellt1.setBorder(TOP);
            cellt1.setBackgroundColor(LIGHT_GRAY);
            //cellt1.setBorder(Rectangle.BOTTOM);
            cellt1.setBorderWidth(0.7f);
            cellt1.setColspan(3);
            table6.addCell(cellt1);
//                } else if (j == 2) {
            phraset1 = new Phrase();
            phraset1.add(new Chunk("Euro TC / Travel Cheques", f5_bold));
            cellt1 = new PdfPCell(phraset1);
            cellt1.setHorizontalAlignment(ALIGN_CENTER);
            cellt1.setBorder(TOP);
            cellt1.setBackgroundColor(LIGHT_GRAY);
            //cellt1.setBorder(Rectangle.BOTTOM);
            cellt1.setBorderWidth(0.7f);
            cellt1.setColspan(2);
            table6.addCell(cellt1);
//                } else {
            phraset1 = new Phrase();
            phraset1.add(new Chunk("", f5_bold));
            cellt1 = new PdfPCell(phraset1);
            cellt1.setHorizontalAlignment(ALIGN_CENTER);
            cellt1.setBorder(TOP);

            cellt1.setBackgroundColor(LIGHT_GRAY);
            //cellt1.setBorder(Rectangle.BOTTOM);
            cellt1.setBorderWidth(0.7f);
            cellt1.setColspan(3);
            table6.addCell(cellt1);
//                }
//            }

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

            double branchtotal = 0, spreadtotal = 0;

            for (int i = 0; i < dati.size(); i++) {
                ToBankingSheet_value temp = (ToBankingSheet_value) dati.get(i);
                ArrayList<String> datitemp = temp.getDati_string();

                phraset = new Phrase();
                phraset.add(new Chunk(temp.getCurrency(), f2_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table3.addCell(cellt);
                boolean perc = false;

                for (int n = 1; n < colonne.size(); n++) {

                    switch (colonne.get(n)) {
                        case "Bank Total":
                            branchtotal += fd((datitemp.get(n)));
                            break;
                        case "Spread":
                            spreadtotal += fd((datitemp.get(n)));
                            break;
                        case "%":
                            perc = true;
                            break;
                        default:
                            break;
                    }

                    phraset = new Phrase();
                    if (!perc) {
                        phraset.add(new Chunk((formatMysqltoDisplay(datitemp.get(n))), f2_normal));
                    } else {
                        phraset.add(new Chunk((formatMysqltoDisplay(datitemp.get(n))) + " %", f2_normal));
                    }
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setBorder(BOTTOM);
                    table3.addCell(cellt);

                }

            }

            document.add(table3);

            branchtotal = roundDouble(branchtotal, 2);
            spreadtotal = roundDouble(spreadtotal, 2);

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
                    phraset.add(new Chunk("Total", f3_normal));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
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
                    double toperc = 0.0;
                    if (branchtotal != 0) {
                        toperc = (spreadtotal / branchtotal) * 100.00;
                    }
                    phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(toperc, 2)) + "%", f3_normal));

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
            document.add(vuoto1);
            document.add(new Paragraph("Notes:", f2_bold));
            document.add(new Paragraph(siq.getNote(), f3_normal));

            document.newPage();
            return document;
        } catch (DocumentException ex) {
            insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
        }
        return null;
    }

    /**
     *
     * @param intestazionePdf
     * @param siq
     * @param datereport
     * @param document
     * @return
     */
    public Document receiptfrombanking(String intestazionePdf, FromBranchingSheet_value siq, String datereport, Document document) {

        ArrayList<String> colonne = new ArrayList<>();
        colonne.add("Currency");
        colonne.add("Amount");
        colonne.add("Bank Rate");
        colonne.add("Amount");
        colonne.add("Bank Rate");
        colonne.add("Bank Total");

        Phrase vuoto1 = new Phrase("\n");
        PdfPCell cellavuota = new PdfPCell(vuoto1);
        cellavuota.setBorder(NO_BORDER);

        //String outputfile = "FromBranchingSheet.pdf";
        columnWidths2 = new float[colonne.size()];

        for (int c = 0; c < colonne.size(); c++) {
            columnWidths2[c] = 10f;
        }

        try {

            PdfPTable table = new PdfPTable(2);
            table.setWidths(columnWidths0);
            table.setWidthPercentage(100);

            Phrase phrase1 = new Phrase();
            phrase1.add(new Chunk(intestazionePdf + " ", f3_bold));
            PdfPCell cell1 = new PdfPCell(phrase1);
            cell1.setBorder(NO_BORDER);

            Paragraph pa1 = new Paragraph(new Phrase("Transfer " + siq.getNumTransfer() + " " + datereport, f2_normal));
            pa1.setAlignment(ALIGN_CENTER);
            PdfPCell cell2 = new PdfPCell(pa1);
            cell2.setBorder(NO_BORDER);
            cell2.setHorizontalAlignment(ALIGN_RIGHT);

            Phrase phrase3 = new Phrase();
            phrase3.add(new Chunk(siq.getId_filiale() + " " + siq.getDe_filiale(), f2_normalbis));
            PdfPCell cell3 = new PdfPCell(phrase3);
            cell3.setBorder(NO_BORDER);

            Phrase phrase4 = new Phrase();
            phrase4.add(new Chunk("\n From: " + siq.getFromsafe() + " \n TO: " + siq.getTobranch() + " \n User: " + siq.getPinuser(), f2_normalbis));
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

            if (siq.getDati().size() > 0) {

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
                    cellt1.setBorder(NO_BORDER);

                    cellt1.setBackgroundColor(LIGHT_GRAY);
                    //cellt1.setBorder(Rectangle.BOTTOM);
                    cellt1.setBorderWidth(0.7f);

                    list[j] = cellt1;

                }

                PdfPTable table6 = new PdfPTable(columnWidths3);
                table6.setWidthPercentage(100);

//                for (int j = 0; j < 6; j++) {
//                    if (j == 1) {
                Phrase phraset1 = new Phrase();
                phraset1.add(new Chunk("Notes", f5_bold));
                PdfPCell cellt1 = new PdfPCell(phraset1);
                cellt1.setHorizontalAlignment(ALIGN_CENTER);
                cellt1.setBorder(TOP);
                cellt1.setBackgroundColor(LIGHT_GRAY);
                //cellt1.setBorder(Rectangle.BOTTOM);
                cellt1.setBorderWidth(0.7f);
                cellt1.setColspan(3);
                table6.addCell(cellt1);
//                    } else if (j == 3) {
                phraset1 = new Phrase();
                phraset1.add(new Chunk("Euro TC / Travel Cheques ", f5_bold));
                cellt1 = new PdfPCell(phraset1);
                cellt1.setHorizontalAlignment(ALIGN_CENTER);
                cellt1.setBorder(TOP);
                cellt1.setBackgroundColor(LIGHT_GRAY);
                //cellt1.setBorder(Rectangle.BOTTOM);
                cellt1.setBorderWidth(0.7f);
                cellt1.setColspan(2);
                table6.addCell(cellt1);
//                   
//                    } else {
                phraset1 = new Phrase();
                phraset1.add(new Chunk("", f5_bold));
                cellt1 = new PdfPCell(phraset1);
                cellt1.setHorizontalAlignment(ALIGN_CENTER);
                cellt1.setBorder(TOP);
                cellt1.setColspan(1);
                cellt1.setBackgroundColor(LIGHT_GRAY);
                //cellt1.setBorder(Rectangle.BOTTOM);
                cellt1.setBorderWidth(0.7f);
                table6.addCell(cellt1);
//                    }
//                }

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
                ArrayList<FromBranchingSheet_value> dati = siq.getDati();

                PdfPCell cell;
                Phrase phraset;
                PdfPCell cellt;

                table3 = new PdfPTable(colonne.size());
                table3.setWidths(columnWidths2);
                table3.setWidthPercentage(100);

                double branchtotal = 0, spreadtotal = 0;

                for (int i = 0; i < dati.size(); i++) {
                    FromBranchingSheet_value temp = dati.get(i);
                    ArrayList<String> datitemp = temp.getDati_string();
                    phraset = new Phrase();
                    phraset.add(new Chunk(temp.getCurrency(), f3_normal));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setBorder(BOTTOM);
                    table3.addCell(cellt);
                    boolean perc = false;

                    for (int n = 1; n < colonne.size(); n++) {

                        if (colonne.get(n).equals("Bank Total")) {
                            branchtotal += fd((datitemp.get(n)));
                        }

                        phraset = new Phrase();
                        if (!perc) {
                            phraset.add(new Chunk((formatMysqltoDisplay(datitemp.get(n))), f3_normal));
                        } else {
                            phraset.add(new Chunk((formatMysqltoDisplay(datitemp.get(n))) + " %", f3_normal));
                        }
                        cellt = new PdfPCell(phraset);
                        cellt.setHorizontalAlignment(ALIGN_RIGHT);
                        cellt.setBorder(BOTTOM);
                        table3.addCell(cellt);

                    }

                }

                document.add(table3);

                branchtotal = roundDouble(branchtotal, 2);
                spreadtotal = roundDouble(spreadtotal, 2);

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
                        phraset.add(new Chunk("Total", f3_normal));
                        cellt = new PdfPCell(phraset);
                        cellt.setHorizontalAlignment(ALIGN_RIGHT);
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
                        double toperc = 0.0;
                        if (branchtotal != 0) {
                            toperc = (spreadtotal / branchtotal) * 100.00;
                        }
                        phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(toperc, 2)) + "%", f3_normal));
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

                }

                document.add(table3);
                document.add(vuoto1);
                document.add(vuoto1);
                document.add(new Paragraph("Notes:", f2_bold));
                document.add(new Paragraph(siq.getNote(), f3_normal));

            }

            document.newPage();
            return document;
        } catch (DocumentException ex) {
            insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
        }
        return null;
    }

    /**
     *
     * @param intestazionePdf
     * @param siq
     * @param datereport
     * @param document
     * @param dati2
     * @return
     */
    public Document receiptfrombranching(String intestazionePdf, FromBranchingSheet_value siq, String datereport, Document document, ArrayList<NoChangeFromBranchingSheet_value> dati2) {
        ArrayList<String> colonne = new ArrayList<>();
        colonne.add("Currency");
        colonne.add("Amount");
        colonne.add("Branch Rate");
        colonne.add("Amount");
        colonne.add("Branch Rate");
        colonne.add("Branch Total");

        Phrase vuoto1 = new Phrase("\n");
        PdfPCell cellavuota = new PdfPCell(vuoto1);
        cellavuota.setBorder(NO_BORDER);

        //String outputfile = "FromBranchingSheet.pdf";
        columnWidths2 = new float[colonne.size()];

        for (int c = 0; c < colonne.size(); c++) {
            columnWidths2[c] = 10f;
        }

        try {

            PdfPTable table = new PdfPTable(2);
            table.setWidths(columnWidths0);
            table.setWidthPercentage(100);

            Phrase phrase1 = new Phrase();
            phrase1.add(new Chunk(intestazionePdf + " ", f3_bold));
            PdfPCell cell1 = new PdfPCell(phrase1);
            cell1.setBorder(NO_BORDER);

            Paragraph pa1 = new Paragraph(new Phrase("Transfer " + siq.getNumTransfer() + " " + datereport, f2_normal));
            pa1.setAlignment(ALIGN_CENTER);
            PdfPCell cell2 = new PdfPCell(pa1);
            cell2.setBorder(NO_BORDER);
            cell2.setHorizontalAlignment(ALIGN_RIGHT);

            Phrase phrase3 = new Phrase();
            phrase3.add(new Chunk(siq.getId_filiale() + " " + siq.getDe_filiale(), f2_normalbis));
            PdfPCell cell3 = new PdfPCell(phrase3);
            cell3.setBorder(NO_BORDER);

            Phrase phrase4 = new Phrase();
            phrase4.add(new Chunk("\n From: " + siq.getFromsafe() + " \n To: " + siq.getTobranch() + " \n User: " + siq.getPinuser(), f2_normalbis));
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

            if (siq.getDati().size() > 0) {

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
                    cellt1.setBorder(NO_BORDER);

                    cellt1.setBackgroundColor(LIGHT_GRAY);
                    //cellt1.setBorder(Rectangle.BOTTOM);
                    cellt1.setBorderWidth(0.7f);

                    list[j] = cellt1;

                }

                PdfPTable table6 = new PdfPTable(columnWidths3);
                table6.setWidthPercentage(100);

//                for (int j = 0; j < 6; j++) {
//                    if (j == 1) {
                Phrase phraset1 = new Phrase();
                phraset1.add(new Chunk("Notes", f5_bold));
                PdfPCell cellt1 = new PdfPCell(phraset1);
                cellt1.setHorizontalAlignment(ALIGN_CENTER);
                cellt1.setBorder(TOP);
                cellt1.setBackgroundColor(LIGHT_GRAY);
                //cellt1.setBorder(Rectangle.BOTTOM);
                cellt1.setBorderWidth(0.7f);
                cellt1.setColspan(3);
                table6.addCell(cellt1);
//                    } else if (j == 3) {
                phraset1 = new Phrase();
                phraset1.add(new Chunk("Euro TC / Travel Cheques ", f5_bold));
                cellt1 = new PdfPCell(phraset1);
                cellt1.setHorizontalAlignment(ALIGN_CENTER);
                cellt1.setBorder(TOP);
                cellt1.setBackgroundColor(LIGHT_GRAY);
                //cellt1.setBorder(Rectangle.BOTTOM);
                cellt1.setBorderWidth(0.7f);
                cellt1.setColspan(2);
                table6.addCell(cellt1);
//                    } else if (j == 4) {

//                    } else {
                phraset1 = new Phrase();
                phraset1.add(new Chunk("", f5_bold));
                cellt1 = new PdfPCell(phraset1);
                cellt1.setHorizontalAlignment(ALIGN_CENTER);
                cellt1.setBorder(TOP);
                cellt1.setBackgroundColor(LIGHT_GRAY);
                //cellt1.setBorder(Rectangle.BOTTOM);
                cellt1.setBorderWidth(0.7f);
                cellt1.setColspan(1);
                table6.addCell(cellt1);
//                    }
//                }

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
                ArrayList<FromBranchingSheet_value> dati = siq.getDati();

                PdfPCell cell;
                Phrase phraset;
                PdfPCell cellt;

                table3 = new PdfPTable(colonne.size());
                table3.setWidths(columnWidths2);
                table3.setWidthPercentage(100);

                double branchtotal = 0, spreadtotal = 0;

                for (int i = 0; i < dati.size(); i++) {
                    FromBranchingSheet_value temp = dati.get(i);

                    ArrayList<String> datitemp = temp.getDati_string();

                    phraset = new Phrase();
                    phraset.add(new Chunk(temp.getCurrency(), f3_normal));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setBorder(BOTTOM);
                    table3.addCell(cellt);
                    boolean perc = false;

                    for (int n = 1; n < colonne.size(); n++) {
                        if (colonne.get(n).equals("Branch Total")) {
                            branchtotal += fd((datitemp.get(n)));
                        }
                        phraset = new Phrase();
                        if (!perc) {
                            phraset.add(new Chunk(formatMysqltoDisplay(datitemp.get(n)), f3_normal));
                        } else {
                            phraset.add(new Chunk(formatMysqltoDisplay(datitemp.get(n)) + " %", f3_normal));
                        }
                        cellt = new PdfPCell(phraset);
                        cellt.setHorizontalAlignment(ALIGN_RIGHT);
                        cellt.setBorder(BOTTOM);
                        table3.addCell(cellt);

                    }

                }

                document.add(table3);

                branchtotal = roundDouble(branchtotal, 2);
                spreadtotal = roundDouble(spreadtotal, 2);

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
                        phraset.add(new Chunk("Total", f3_normal));
                        cellt = new PdfPCell(phraset);
                        cellt.setHorizontalAlignment(ALIGN_RIGHT);
                        cellt.setBorder(BOTTOM);
                        table3.addCell(cellt);
                    } else if (colonne.get(n).equals("Branch Total")) {
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
                        double toperc = 0.0;
                        if (branchtotal != 0) {
                            toperc = (spreadtotal / branchtotal) * 100.00;
                        }
                        phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(toperc, 2)) + "%", f3_normal));
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

                }

                document.add(table3);
                document.add(vuoto1);
                document.add(vuoto1);
                document.add(new Paragraph("Notes:", f2_bold));
                document.add(new Paragraph(siq.getNote(), f3_normal));

            }

            ArrayList<String> colonne2 = new ArrayList<>();

            colonne2.add("Category");
            colonne2.add("Quantity");
            colonne2.add("Amount");
            colonne2.add("Total");
            columnWidths2 = new float[colonne2.size()];

            for (int c = 0; c < colonne2.size(); c++) {
                columnWidths2[c] = 10f;
            }

            if (dati2.size() > 0) {
                document.newPage();
            }
            return document;
        } catch (DocumentException ex) {
            insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
        }
        return null;
    }

    /**
     *
     * @param intestazionePdf
     * @param siq
     * @param datereport
     * @param document
     * @param dati2
     * @return
     */
    public Document receipttobranching(String intestazionePdf, ToBranchingSheet_value siq, String datereport, Document document, ArrayList<NoChangeToBranchingSheet_value> dati2) {

        ArrayList<String> colonne = new ArrayList<>();
        colonne.add("Currency");
        colonne.add("Amount");
        colonne.add("Branch Rate");
        colonne.add("Amount");
        colonne.add("Branch Rate");
        colonne.add("Branch Total");
        colonne.add("Spread");
        colonne.add("%");

        ArrayList<String> colonne2 = new ArrayList<>();
        colonne2.add("Category");
        colonne2.add("Quantity");
        colonne2.add("Amount");
        colonne2.add("Total");

        Phrase vuoto1 = new Phrase("\n");
        PdfPCell cellavuota = new PdfPCell(vuoto1);
        cellavuota.setBorder(NO_BORDER);

        //String outputfile = "ToBranchingSheet.pdf";
        columnWidths2 = new float[colonne.size()];

        for (int c = 0; c < colonne.size(); c++) {
            columnWidths2[c] = 10f;
        }

        try {

            PdfPTable table = new PdfPTable(2);
            table.setWidths(columnWidths0);
            table.setWidthPercentage(100);

            Phrase phrase1 = new Phrase();
            phrase1.add(new Chunk(intestazionePdf + "  ", f3_bold));
            PdfPCell cell1 = new PdfPCell(phrase1);
            cell1.setBorder(NO_BORDER);

            Paragraph pa1 = new Paragraph(new Phrase("Transfer " + siq.getNumTransfer() + " " + datereport, f2_normal));
            pa1.setAlignment(ALIGN_CENTER);
            PdfPCell cell2 = new PdfPCell(pa1);
            cell2.setBorder(NO_BORDER);
            cell2.setHorizontalAlignment(ALIGN_RIGHT);

            Phrase phrase3 = new Phrase();
            phrase3.add(new Chunk(siq.getId_filiale() + " " + siq.getDe_filiale(), f2_normalbis));
            PdfPCell cell3 = new PdfPCell(phrase3);
            cell3.setBorder(NO_BORDER);

            Phrase phrase4 = new Phrase();
            phrase4.add(new Chunk("\n From: " + siq.getFromsafe() + " \n To: " + siq.getTobranch() + " \n User: " + siq.getPinuser(), f2_normalbis));
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

            if (siq.getDati().size() > 0) {

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

                    list[j] = cellt1;

                }

                PdfPTable table6 = new PdfPTable(columnWidths3);
                table6.setWidthPercentage(100);

//                for (int j = 0; j < 6; j++) {
//                    if (j == 1) {
                Phrase phraset1 = new Phrase();
                phraset1.add(new Chunk("Notes", f5_bold));
                PdfPCell cellt1 = new PdfPCell(phraset1);
                cellt1.setHorizontalAlignment(ALIGN_CENTER);
                cellt1.setBorder(TOP);
                cellt1.setBackgroundColor(LIGHT_GRAY);
                //cellt1.setBorder(Rectangle.BOTTOM);
                cellt1.setBorderWidth(0.7f);
                cellt1.setColspan(3);
                table6.addCell(cellt1);
//                    } else if (j == 2) {
                phraset1 = new Phrase();
                phraset1.add(new Chunk("Euro TC / Travel Cheques", f5_bold));
                cellt1 = new PdfPCell(phraset1);
                cellt1.setHorizontalAlignment(ALIGN_CENTER);
                cellt1.setBorder(TOP);
                cellt1.setBackgroundColor(LIGHT_GRAY);
                //cellt1.setBorder(Rectangle.BOTTOM);
                cellt1.setBorderWidth(0.7f);
                cellt1.setColspan(2);
                table6.addCell(cellt1);
//                    } else {
                phraset1 = new Phrase();
                phraset1.add(new Chunk("", f5_bold));
                cellt1 = new PdfPCell(phraset1);
                cellt1.setHorizontalAlignment(ALIGN_CENTER);
                cellt1.setBorder(TOP);
                cellt1.setBackgroundColor(LIGHT_GRAY);
                //cellt1.setBorder(Rectangle.BOTTOM);
                cellt1.setBorderWidth(0.7f);
                cellt1.setColspan(3);
                table6.addCell(cellt1);
//                    }
//                }

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
                ArrayList<ToBranchingSheet_value> dati = siq.getDati();

                PdfPCell cell;
                Phrase phraset;
                PdfPCell cellt;

                table3 = new PdfPTable(colonne.size());
                table3.setWidths(columnWidths2);
                table3.setWidthPercentage(100);

                double branchtotal = 0, spreadtotal = 0;

                for (int i = 0; i < dati.size(); i++) {
                    ToBranchingSheet_value temp = (ToBranchingSheet_value) dati.get(i);

                    ArrayList<String> datitemp = temp.getDati_string();

                    phraset = new Phrase();
                    phraset.add(new Chunk(temp.getCurrency(), f2_normal));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setBorder(BOTTOM);
                    table3.addCell(cellt);
                    boolean perc = false;

                    for (int n = 1; n < colonne.size(); n++) {

                        switch (colonne.get(n)) {
                            case "Branch Total":
                                branchtotal += fd((datitemp.get(n)));
                                break;
                            case "Spread":
                                spreadtotal += fd((datitemp.get(n)));
                                break;
                            case "%":
                                perc = true;
                                break;
                            default:
                                break;
                        }

                        phraset = new Phrase();
                        if (!perc) {
                            phraset.add(new Chunk((formatMysqltoDisplay(datitemp.get(n))), f2_normal));
                        } else {
                            phraset.add(new Chunk(formatMysqltoDisplay(datitemp.get(n)) + " %", f2_normal));
                        }
                        cellt = new PdfPCell(phraset);
                        cellt.setHorizontalAlignment(ALIGN_RIGHT);
                        cellt.setBorder(BOTTOM);
                        table3.addCell(cellt);

                    }

                }

                document.add(table3);

//                branchtotal = Utility.roundDouble(branchtotal, 2);
//                spreadtotal = Utility.roundDouble(spreadtotal, 2);
//                perctotal = Utility.roundDouble(perctotal, 2);
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
                        phraset.add(new Chunk("Total", f3_normal));
                        cellt = new PdfPCell(phraset);
                        cellt.setHorizontalAlignment(ALIGN_RIGHT);
                        cellt.setBorder(BOTTOM);
                        table3.addCell(cellt);
                    } else if (colonne.get(n).equals("Branch Total")) {
                        phraset = new Phrase();
                        phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(branchtotal, 2)), f3_normal));
                        cellt = new PdfPCell(phraset);
                        cellt.setHorizontalAlignment(ALIGN_RIGHT);
                        cellt.setBorder(BOTTOM);
                        table3.addCell(cellt);

                    } else if (colonne.get(n).equals("Spread")) {
                        phraset = new Phrase();
                        phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(spreadtotal, 6)), f3_normal));
                        cellt = new PdfPCell(phraset);
                        cellt.setHorizontalAlignment(ALIGN_RIGHT);
                        cellt.setBorder(BOTTOM);
                        table3.addCell(cellt);

                    } else if (colonne.get(n).equals("%")) {
                        phraset = new Phrase();
                        double toperc = 0.0;
                        if (branchtotal != 0) {
                            toperc = (spreadtotal / branchtotal) * 100.00;
                        }
                        phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(toperc, 2)) + "%", f3_normal));
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

                }

                document.add(table3);
                document.add(vuoto1);
                document.add(vuoto1);
                document.add(new Paragraph("Notes:", f2_bold));
                document.add(new Paragraph(siq.getNote(), f3_normal));

            }

            columnWidths2 = new float[colonne2.size()];

            for (int c = 0; c < colonne2.size(); c++) {
                columnWidths2[c] = 10f;
            }

            if (dati2.size() > 0) {
                document.newPage();
            }

            return document;
        } catch (DocumentException ex) {
            insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
        }
        return null;
    }

    /**
     *
     * @param path
     * @param codlist
     * @param br
     * @param array_bank
     * @param array_creditcard
     * @return
     */
    public String main(String path, ArrayList<ET_change> codlist, ArrayList<Branch> br, ArrayList<String[]> array_bank, ArrayList<String[]> array_creditcard) {
        try {
            C_BankBranch nctl = new C_BankBranch();

            File pdffile = new File(path + generaId(50) + "C_BankBranch.pdf");
            try(OutputStream ou = new FileOutputStream(pdffile)){
            Document document = new Document(A4.rotate(), 20, 20, 20, 20);
            PdfWriter wr = getInstance(document, ou);
            document.open();
            Db_Master dbm = new Db_Master();
            boolean find = false;
            for (int i = 0; i < codlist.size(); i++) {
                ET_change et = codlist.get(i);

                if (format_tofrom_brba(et.getFg_tofrom(), et.getFg_brba(), et.getCod_dest(), array_creditcard).contains("POS")) {
                    ArrayList<ToBankingSheet_value> datiPOS = dbm.list_POSBASheet_value(et);
                    if (datiPOS.size() > 0) {
                        find = true;
                    }
                    String ba = formatBankBranch(et.getCod_dest(), "BA", array_bank, null, array_creditcard);
                    ArrayList<Branch> allfill = dbm.list_branch_completeAFTER311217();
                    ToPOSBASheet bsi2 = new ToPOSBASheet();
                    ArrayList<String> alcolonne = new ArrayList<>();
                    ToBankingSheet_value pdf = new ToBankingSheet_value();
                    pdf.setAuto(et.getAuto());
                    pdf.setId_filiale(et.getFiliale());
                    pdf.setDe_filiale(formatBankBranchReport(et.getFiliale(), "BR", null, allfill));
                    pdf.setFromsafe(et.getFiliale() + " - " + formatBankBranchReport(et.getFiliale(), "BR", null, allfill));
                    pdf.setTobank(et.getCod_dest() + " - " + ba);
                    pdf.setPinuser(et.getUser());
                    pdf.setNumtranfer(et.getId());
                    pdf.setNote(et.getNote());
                    alcolonne.add("Currency");
                    alcolonne.add("Amount");
                    alcolonne.add("Amount");
                    alcolonne.add("Amount");
                    alcolonne.add("Amount");
                    alcolonne.add("Bank Total");
                    pdf.setDati(datiPOS);

                    document = bsi2.receipt(pdf, alcolonne, formatStringtoStringDate(et.getDt_it(), patternsqldate, patternnormdate), document);

                } else if (et.getFg_brba().equals("BA") && et.getFg_tofrom().equals("T")) {
                    ArrayList<ToBankingSheet_value> dati = dbm.list_ToBankingSheet_value(et);
                    if (dati.size() > 0) {
                        find = true;
                    }
                    ToBankingSheet_value pdf = new ToBankingSheet_value();
                    String ba = formatBankBranch(et.getCod_dest(), "BA", array_bank, null, array_creditcard);
                    pdf.setId_filiale(et.getFiliale());
                    pdf.setDe_filiale(formatBankBranchReport(et.getFiliale(), "BR", null, br));
                    pdf.setFromsafe(formatBankBranch(et.getFiliale(), "BR", null, br, null));
                    pdf.setTobank(et.getCod_dest() + " " + ba);
                    pdf.setPinuser(et.getUser());
                    pdf.setNumtranfer(et.getId());
                    pdf.setNote(et.getNote());
                    pdf.setDati(dati);
                    document = nctl.receiptbank("To Banking Sheet ", pdf, formatStringtoStringDate(et.getDt_it(), patternsqldate, patternnormdate), document);
                } else if (et.getFg_brba().equals("BA") && et.getFg_tofrom().equals("F")) {
                    ArrayList<FromBranchingSheet_value> dati1 = dbm.list_FromBranchingSheet_value(et);
                    if (dati1.size() > 0) {
                        find = true;
                    }
                    FromBranchingSheet_value pdf = new FromBranchingSheet_value();
                    pdf.setId_filiale(et.getFiliale());
                    pdf.setDe_filiale(formatBankBranchReport(et.getFiliale(), "BR", null, br));
                    String ba = formatBankBranch(et.getCod_dest(), "BA", array_bank, null, array_creditcard);
                    pdf.setFromsafe(et.getCod_dest() + " " + ba);
                    pdf.setTobranch(formatBankBranch(et.getFiliale(), "BR", null, br, null));
                    pdf.setPinuser(et.getUser());
                    pdf.setNumtranfer(et.getId());
                    pdf.setNote(et.getNote());
                    pdf.setDati(dati1);
                    document = nctl.receiptfrombanking("From Banking Sheet ", pdf, formatStringtoStringDate(et.getDt_it(), patternsqldate, patternnormdate), document);
                } else if (et.getFg_brba().equals("BR") && et.getFg_tofrom().equals("F")) {
                    ArrayList<FromBranchingSheet_value> dati = dbm.list_FromBranchingSheet_value(et);
                    ArrayList<NoChangeFromBranchingSheet_value> dati2 = dbm.list_FromBranchingSheet_valueNC(et);
                    if (dati.size() > 0 || dati2.size() > 0) {
                        find = true;
                    }

                    Branch dest = dbm.get_branch(et.getCod_dest());
                    FromBranchingSheet_value pdf = new FromBranchingSheet_value();
                    pdf.setId_filiale(et.getFiliale());
                    pdf.setDe_filiale(formatBankBranchReport(et.getFiliale(), "BR", null, br));
                    pdf.setFromsafe(dest.getCod() + " - " + dest.getDe_branch());
                    pdf.setTobranch(formatBankBranch(et.getFiliale(), "BR", null, br, null));
                    pdf.setPinuser(et.getUser());
                    pdf.setNumtranfer(et.getId());
                    pdf.setNote(et.getNote());
                    pdf.setDati(dati);
                    document = nctl.receiptfrombranching("From Branching Sheet ", pdf, formatStringtoStringDate(et.getDt_it(), patternsqldate, patternnormdate), document, dati2);
                } else if (et.getFg_brba().equals("BR") && et.getFg_tofrom().equals("T")) {
                    ArrayList<ToBranchingSheet_value> dati = dbm.list_ToBranchingSheet_value(et);
                    ArrayList<NoChangeToBranchingSheet_value> dati2 = dbm.list_ToBranchingSheet_valueNC(et);
                    if (dati.size() > 0 || dati2.size() > 0) {
                        find = true;
                    }

                    Branch dest = dbm.get_branch(et.getCod_dest());
                    ToBranchingSheet_value pdf = new ToBranchingSheet_value();
                    pdf.setId_filiale(et.getFiliale());
                    pdf.setDe_filiale(formatBankBranchReport(et.getFiliale(), "BR", null, br));
                    pdf.setFromsafe(formatBankBranch(et.getFiliale(), "BR", null, br, null));
                    pdf.setTobranch(dest.getCod() + " - " + dest.getDe_branch());
                    pdf.setPinuser(et.getUser());
                    pdf.setNumtranfer(et.getId());
                    pdf.setNote(et.getNote());
                    pdf.setDati(dati);
                    document = nctl.receipttobranching("To Branching Sheet ", pdf, formatStringtoStringDate(et.getDt_it(), patternsqldate, patternnormdate), document, dati2);
                }
            }
            dbm.closeDB();

            if (find) {
//chiusura documento
                document.close();
                wr.close();
                ou.close();
                String base64 = new String(encodeBase64(readFileToByteArray(pdffile)));
                pdffile.delete();
                return base64;
            } else {
                document.add(new Paragraph("CZZ"));
                document.close();
                wr.close();
                ou.close();
                pdffile.delete();
                return null;
            }
        }
        } catch (DocumentException | IOException ex) {
            insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
        }
        return null;

    }

    /**
     *
     * @param intestazionePdf
     * @param siq
     * @param datereport
     * @param sheet
     * @param cntriga
     * @param style1
     * @param style2
     * @param style3
     * @param style4
     * @param cellStylenum
     * @param cellStylenumRATE
     * @return
     */
    public int receiptexcelfrombanking(String intestazionePdf, FromBranchingSheet_value siq, String datereport, XSSFSheet sheet, int cntriga,
            XSSFCellStyle style1, XSSFCellStyle style2, XSSFCellStyle style3, XSSFCellStyle style4,
            XSSFCellStyle cellStylenum, XSSFCellStyle cellStylenumRATE) {

        Phrase vuoto1 = new Phrase("\n");
        PdfPCell cellavuota = new PdfPCell(vuoto1);
        cellavuota.setBorder(NO_BORDER);

        //String outputfile = "FromBranchingSheet.pdf";
        try {

            ArrayList<String> colonne = new ArrayList<>();
            colonne.add("Currency");
            colonne.add("Amount");
            colonne.add("Bank Rate");
            colonne.add("Amount");
            colonne.add("Bank Rate");
            colonne.add("Bank Total");

            XSSFRow rowP = sheet.createRow(cntriga);
            cntriga++;

            Cell cl = rowP.createCell(1);
            cl.setCellStyle(style1);
            cl.setCellValue(intestazionePdf);

            Cell cl32 = rowP.createCell(3);
            cl32.setCellStyle(style1);
            cl32.setCellValue("Transfer " + siq.getNumTransfer() + " " + datereport);
//           
            XSSFRow rowP3 = sheet.createRow(cntriga);
            cntriga++;

            Cell cl2 = rowP3.createCell(1);
            cl2.setCellStyle(style3);
            cl2.setCellValue(siq.getId_filiale() + " " + siq.getDe_filiale());

            XSSFRow rowP4 = sheet.createRow(cntriga);
            cntriga++;

            Cell cl4 = rowP4.createCell(1);
            cl4.setCellStyle(style3);
            cl4.setCellValue("\n From: " + siq.getFromsafe());

            XSSFRow rowP5 = sheet.createRow(cntriga);
            cntriga++;

            Cell cl55 = rowP5.createCell(1);
            cl55.setCellStyle(style3);
            cl55.setCellValue(" \n TO: " + siq.getTobranch());

            XSSFRow rowP7 = sheet.createRow(cntriga);
            cntriga++;

            Cell cl7 = rowP7.createCell(1);
            cl7.setCellStyle(style3);
            cl7.setCellValue(" \n User: " + siq.getPinuser());

//            Row row = sheet.createRow((short) cntriga);
//            row.createCell(1).setCellValue(siq.getId_filiale() + " " + siq.getDe_filiale());
            cntriga++;
            cntriga++;
            cntriga++;
            cntriga++;
            cntriga++;

            XSSFRow row77 = sheet.createRow(cntriga);
            cntriga++;

            for (int j = 0; j < 6; j++) {

                if (j == 1) {

                    Cell cl5 = row77.createCell(j + 2);
                    cl5.setCellStyle(style3);
                    cl5.setCellValue("Notes");

                } else if (j == 3) {

                    Cell cl5 = row77.createCell(j + 2);
                    cl5.setCellStyle(style3);
                    cl5.setCellValue("Euro TC / Travel Cheques");

                } else {

//                   Cell cl5 = row7.createCell(j+1);
//                    cl5.setCellStyle(style3);
//                    cl5.setCellValue("Euro TC / Travel Cheques");
//                   
//               Phrase phraset1 = new Phrase();
//                phraset1.add(new Chunk("", f5_bold));
//                PdfPCell cellt1 = new PdfPCell(phraset1);
//                cellt1.setHorizontalAlignment(Element.ALIGN_LEFT);
//                cellt1.setBorder( Rectangle.TOP);
//
//                cellt1.setBackgroundColor(BaseColor.LIGHT_GRAY);
//                //cellt1.setBorder(Rectangle.BOTTOM);
//                cellt1.setBorderWidth(0.7f);
//                table6.addCell(cellt1);
                }
            }

            cntriga++;

            XSSFRow row66 = sheet.createRow(cntriga);

            if (siq.getDati().size() > 0) {

                //mi scandisco le colonne
                for (int j = 0; j < colonne.size(); j++) {
                    Cell cl5 = row66.createCell(j + 1);
                    cl5.setCellStyle(style3);
                    cl5.setCellValue(colonne.get(j));

                }

                cntriga++;

                ArrayList<FromBranchingSheet_value> dati = siq.getDati();

                double branchtotal = 0, spreadtotal = 0;
                cntriga++;
                cntriga++;
                cntriga++;
                cntriga++;

                for (int i = 0; i < dati.size(); i++) {

                    cntriga++;
                    XSSFRow row6 = sheet.createRow(cntriga);

                    FromBranchingSheet_value temp = dati.get(i);

                    ArrayList<String> datitemp = temp.getDati_string();

                    Cell f1 = row6.createCell(1);
                    f1.setCellStyle(style4);
                    f1.setCellValue(temp.getCurrency());
                    boolean perc = false;

                    for (int n = 1; n < colonne.size(); n++) {

                        switch (colonne.get(n)) {
                            case "Bank Total":
                                branchtotal += fd((datitemp.get(n)));
                                break;
                            case "Spread":
                                spreadtotal += fd((datitemp.get(n)));
                                break;
                            case "%":
                                perc = true;
                                break;
                            default:
                                break;
                        }

                        Cell f2 = row6.createCell(n + 1, NUMERIC);
                        f2.setCellStyle(cellStylenumRATE);

                        if (!perc) {
                            f2.setCellValue(fd(datitemp.get(n)));

                        } else {
                            f2.setCellValue(fd(datitemp.get(n)) + " %");

                        }

                    }

                }

                cntriga++;
                cntriga++;

                XSSFRow row9 = sheet.createRow(cntriga);

                for (int n = 1; n < colonne.size(); n++) {

                    if (n == 1) {
                        Cell f7 = row9.createCell(n + 1);
                        f7.setCellStyle(style4);
                        f7.setCellValue("Total");
                    } else if (colonne.get(n).equals("Bank Total")) {

                        Cell f7 = row9.createCell(n + 1, NUMERIC);
                        f7.setCellStyle(cellStylenum);
                        f7.setCellValue(fd(roundDoubleandFormat(branchtotal, 2)));

                    } else if (colonne.get(n).equals("Spread")) {

                        Cell f7 = row9.createCell(n + 1, NUMERIC);
                        f7.setCellStyle(cellStylenum);
                        f7.setCellValue(fd(roundDoubleandFormat(spreadtotal, 2)));

                    } else if (colonne.get(n).equals("%")) {

                        Cell f7 = row9.createCell(n + 1, NUMERIC);
                        f7.setCellStyle(cellStylenum);
                        double toperc = 0.0;
                        if (branchtotal != 0) {
                            toperc = (spreadtotal / branchtotal) * 100.00;
                        }
                        f7.setCellValue(fd(roundDoubleandFormat(toperc, 2)) + "%");

                    } else {
//                     phraset = new Phrase();
//                phraset.add(new Chunk("", f3_normal));
//                cellt = new PdfPCell(phraset);
//                cellt.setHorizontalAlignment(Element.ALIGN_LEFT);
//                cellt.setBorder(Rectangle.BOTTOM);
//                table3.addCell(cellt);
                    }

                }

                cntriga++;
                cntriga++;
                cntriga++;
                cntriga++;

                XSSFRow row10 = sheet.createRow(cntriga);
                Cell f7 = row10.createCell(1);
                f7.setCellStyle(style3);
                f7.setCellValue("Notes:");
                Cell f71 = row10.createCell(2);
                f71.setCellStyle(style3);
                f71.setCellValue(siq.getNote());
                cntriga++;
                cntriga++;

            }

//           
//            
        } catch (Exception ex) {
            insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
        }
        cntriga++;
        cntriga++;
        cntriga++;
        return cntriga;
    }

    /**
     *
     * @param intestazionePdf
     * @param siq
     * @param datereport
     * @param dati2
     * @param sheet
     * @param cntriga
     * @param style1
     * @param style2
     * @param style3
     * @param style4
     * @param cellStylenum
     * @param cellStylenumRATE
     * @return
     */
    public int receiptexcelfrombranching(String intestazionePdf, FromBranchingSheet_value siq, String datereport,
            ArrayList<NoChangeFromBranchingSheet_value> dati2, XSSFSheet sheet, int cntriga, XSSFCellStyle style1,
            XSSFCellStyle style2, XSSFCellStyle style3, XSSFCellStyle style4, XSSFCellStyle cellStylenum, XSSFCellStyle cellStylenumRATE) {

        Phrase vuoto1 = new Phrase("\n");
        PdfPCell cellavuota = new PdfPCell(vuoto1);
        cellavuota.setBorder(NO_BORDER);

        //String outputfile = "FromBranchingSheet.pdf";
        try {

            ArrayList<String> colonne = new ArrayList<>();
            colonne.add("Currency");
            colonne.add("Amount");
            colonne.add("Branch Rate");
            colonne.add("Amount");
            colonne.add("Branch Rate");
            colonne.add("Branch Total");

            XSSFRow rowP = sheet.createRow(cntriga);
            cntriga++;

            Cell cl = rowP.createCell(1);
            cl.setCellStyle(style1);
            cl.setCellValue(intestazionePdf);

            Cell cl32 = rowP.createCell(3);
            cl32.setCellStyle(style1);
            cl32.setCellValue("Transfer " + siq.getNumTransfer() + " " + datereport);
//           
            XSSFRow rowP3 = sheet.createRow(cntriga);
            cntriga++;

            Cell cl2 = rowP3.createCell(1);
            cl2.setCellStyle(style3);
            cl2.setCellValue(siq.getId_filiale() + " " + siq.getDe_filiale());

            XSSFRow rowP4 = sheet.createRow(cntriga);
            cntriga++;

            Cell cl4 = rowP4.createCell(1);
            cl4.setCellStyle(style3);
            cl4.setCellValue("\n From: " + siq.getFromsafe());

            XSSFRow rowP5 = sheet.createRow(cntriga);
            cntriga++;

            Cell cl55 = rowP5.createCell(1);
            cl55.setCellStyle(style3);
            cl55.setCellValue(" \n TO: " + siq.getTobranch());

            XSSFRow rowP7 = sheet.createRow(cntriga);
            cntriga++;

            Cell cl7 = rowP7.createCell(1);
            cl7.setCellStyle(style3);
            cl7.setCellValue(" \n User: " + siq.getPinuser());

            XSSFRow row = sheet.createRow(cntriga);
            cntriga++;
//            row.createCell(1).setCellValue(siq.getId_filiale() + " " + siq.getDe_filiale());

            XSSFRow row77 = sheet.createRow(cntriga);

            cntriga++;

            for (int j = 0; j < 6; j++) {

                if (j == 1) {

                    Cell cl5 = row77.createCell(j + 2);
                    cl5.setCellStyle(style3);
                    cl5.setCellValue("Notes");

                } else if (j == 3) {

                    Cell cl5 = row77.createCell(j + 2);
                    cl5.setCellStyle(style3);
                    cl5.setCellValue("Euro TC / Travel Cheques");

                } else {

//                   Cell cl5 = row7.createCell(j+1);
//                    cl5.setCellStyle(style3);
//                    cl5.setCellValue("Euro TC / Travel Cheques");
//                   
//               Phrase phraset1 = new Phrase();
//                phraset1.add(new Chunk("", f5_bold));
//                PdfPCell cellt1 = new PdfPCell(phraset1);
//                cellt1.setHorizontalAlignment(Element.ALIGN_LEFT);
//                cellt1.setBorder( Rectangle.TOP);
//
//                cellt1.setBackgroundColor(BaseColor.LIGHT_GRAY);
//                //cellt1.setBorder(Rectangle.BOTTOM);
//                cellt1.setBorderWidth(0.7f);
//                table6.addCell(cellt1);
                }
            }

            cntriga++;

            XSSFRow row66 = sheet.createRow(cntriga);
            cntriga++;

            if (siq.getDati().size() > 0) {

                //mi scandisco le colonne
                for (int j = 0; j < colonne.size(); j++) {
                    Cell cl5 = row66.createCell(j + 1);
                    cl5.setCellStyle(style3);
                    cl5.setCellValue(colonne.get(j));

                }

                ArrayList<FromBranchingSheet_value> dati = siq.getDati();

                double branchtotal = 0, spreadtotal = 0;
                cntriga++;
                cntriga++;
                cntriga++;

                for (int i = 0; i < dati.size(); i++) {

                    cntriga++;
                    XSSFRow row6 = sheet.createRow(cntriga);

                    FromBranchingSheet_value temp = dati.get(i);

                    ArrayList<String> datitemp = temp.getDati_string();

                    Cell f1 = row6.createCell(1);
                    f1.setCellStyle(style4);
                    f1.setCellValue(temp.getCurrency());
                    boolean perc = false;

                    for (int n = 1; n < colonne.size(); n++) {

                        switch (colonne.get(n)) {
                            case "Branch Total":
                                branchtotal += fd((datitemp.get(n)));
                                break;
                            case "Spread":
                                spreadtotal += fd((datitemp.get(n)));
                                break;
                            case "%":
                                perc = true;
                                break;
                            default:
                                break;
                        }

                        Cell f2 = row6.createCell(n + 1, NUMERIC);
                        f2.setCellStyle(cellStylenumRATE);

                        if (!perc) {

                            f2.setCellValue(fd(datitemp.get(n)));

                        } else {
                            f2.setCellValue(fd(datitemp.get(n)) + " %");

                        }

                    }

                }

                cntriga++;
                cntriga++;

                branchtotal = roundDouble(branchtotal, 2);
                spreadtotal = roundDouble(spreadtotal, 2);

                XSSFRow row9 = sheet.createRow(cntriga);

                for (int n = 1; n < colonne.size(); n++) {

                    if (n == 1) {
                        Cell f7 = row9.createCell(n + 1);
                        f7.setCellStyle(style4);
                        f7.setCellValue("Total");
                    } else if (colonne.get(n).equals("Branch Total")) {

                        Cell f7 = row9.createCell(n + 1, NUMERIC);
                        f7.setCellStyle(cellStylenum);
                        f7.setCellValue(fd(roundDoubleandFormat(branchtotal, 2)));

                    } else if (colonne.get(n).equals("Spread")) {

                        Cell f7 = row9.createCell(n + 1, NUMERIC);
                        f7.setCellStyle(cellStylenum);
                        f7.setCellValue(fd(roundDoubleandFormat(spreadtotal, 2)));

                    } else if (colonne.get(n).equals("%")) {

                        Cell f7 = row9.createCell(n + 1, NUMERIC);
                        f7.setCellStyle(cellStylenum);
                        double toperc = 0.0;
                        if (branchtotal != 0) {
                            toperc = (spreadtotal / branchtotal) * 100.00;
                        }
                        f7.setCellValue(fd(roundDoubleandFormat(toperc, 2)) + "%");

                    }

                }

                cntriga++;
                cntriga++;
                cntriga++;
                cntriga++;

                XSSFRow row10 = sheet.createRow(cntriga);
                Cell f7 = row10.createCell(1);
                f7.setCellStyle(style3);
                f7.setCellValue("Notes:");
                Cell f71 = row10.createCell(2);
                f71.setCellStyle(style3);
                f71.setCellValue(siq.getNote());
                cntriga++;
                cntriga++;

            }

            ArrayList<String> colonne2 = new ArrayList<>();

            colonne2.add("Category");
            colonne2.add("Quantity");
            colonne2.add("Amount");
            colonne2.add("Total");

            if (dati2.size() > 0) {

                cntriga++;
                XSSFRow row10 = sheet.createRow(cntriga);

                //mi scandisco le colonne
                for (int j = 0; j < colonne2.size(); j++) {
                    Cell f6 = row10.createCell(j + 1);
                    f6.setCellStyle(style3);
                    f6.setCellValue(colonne2.get(j));

                }

                cntriga++;

                //  document.add(table2);
                //  document.add(sep);
                double totQuantity = 0, total = 0;

                for (int i = 0; i < dati2.size(); i++) {

                    XSSFRow row11 = sheet.createRow(cntriga);

                    NoChangeFromBranchingSheet_value temp = (NoChangeFromBranchingSheet_value) dati2.get(i);

                    Cell f7 = row11.createCell(1);
                    f7.setCellStyle(style4);
                    f7.setCellValue(temp.getCategory());

                    Cell f8 = row11.createCell(2, NUMERIC);
                    f8.setCellStyle(cellStylenum);
                    f8.setCellValue(fd(temp.getQuantity()));

                    Cell f9 = row11.createCell(3, NUMERIC);
                    f9.setCellStyle(cellStylenum);
                    f9.setCellValue(fd(temp.getAmount()));

                    Cell f10 = row11.createCell(4, NUMERIC);
                    f10.setCellStyle(cellStylenum);
                    double d = fd(temp.getQuantity()) * fd(temp.getAmount());
                    f10.setCellValue(fd(roundDoubleandFormat(d, 2)));

                    totQuantity += fd(temp.getQuantity());
                    total += fd(temp.getQuantity()) * fd(temp.getAmount());

                }

                totQuantity = roundDouble(totQuantity, 2);
                total = roundDouble(total, 2);

                cntriga++;
                XSSFRow row12 = sheet.createRow(cntriga);

                Cell f15 = row12.createCell(1);
                f15.setCellStyle(style3);
                f15.setCellValue("Total");

                Cell f16 = row12.createCell(2);
                f16.setCellStyle(style3);
                f16.setCellValue(roundDoubleandFormat(totQuantity, 2) + "");

                Cell f17 = row12.createCell(4);
                f17.setCellStyle(style3);
                f17.setCellValue(roundDoubleandFormat(total, 2) + "");

                cntriga++;
                XSSFRow row101 = sheet.createRow(cntriga);
                Cell f7 = row101.createCell(1);
                f7.setCellStyle(style3);
                f7.setCellValue("Notes:");
                Cell f71 = row101.createCell(2);
                f71.setCellStyle(style3);
                f71.setCellValue(siq.getNote());

                cntriga++;

            }
//            
        } catch (NumberFormatException ex) {
            insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
        }
        cntriga++;
        cntriga++;
        cntriga++;
        return cntriga;
    }

    /**
     *
     * @param intestazionePdf
     * @param siq
     * @param datereport
     * @param sheet
     * @param cntriga
     * @param style1
     * @param style2
     * @param style3
     * @param style4
     * @param cellStylenum
     * @param cellStylenumRATE
     * @return
     */
    public int receiptexceltobanking(String intestazionePdf, ToBankingSheet_value siq, String datereport,
            XSSFSheet sheet, int cntriga, XSSFCellStyle style1, XSSFCellStyle style2, XSSFCellStyle style3,
            XSSFCellStyle style4, XSSFCellStyle cellStylenum, XSSFCellStyle cellStylenumRATE) {

        // String outputfile = "ToBankingSheet.pdf";
        ArrayList<String> colonne = new ArrayList<>();
        colonne.add("Currency");
        colonne.add("Amount");
        colonne.add("Bank Rate");
        colonne.add("Amount");
        colonne.add("Bank Rate");
        colonne.add("Bank Total");
        colonne.add("Spread");
        colonne.add("%");

        try {

            XSSFRow rowP = sheet.createRow(cntriga);
            cntriga++;

            Cell cl = rowP.createCell(1);
            cl.setCellStyle(style1);
            cl.setCellValue(intestazionePdf);

            Cell cl32 = rowP.createCell(3);
            cl32.setCellStyle(style1);
            cl32.setCellValue("Transfer " + siq.getNumTransfer() + " " + datereport);
//           
            XSSFRow rowP3 = sheet.createRow(cntriga);
            cntriga++;

            Cell cl2 = rowP3.createCell(1);
            cl2.setCellStyle(style3);
            cl2.setCellValue(siq.getId_filiale() + " " + siq.getDe_filiale());

            XSSFRow rowP4 = sheet.createRow(cntriga);
            cntriga++;

            Cell cl4 = rowP4.createCell(1);
            cl4.setCellStyle(style3);
            cl4.setCellValue("\n From : " + siq.getFromsafe());

            XSSFRow rowP5 = sheet.createRow(cntriga);
            cntriga++;

            Cell cl55 = rowP5.createCell(1);
            cl55.setCellStyle(style3);
            cl55.setCellValue(" \n TO: " + siq.getTobank());

            XSSFRow rowP7 = sheet.createRow(cntriga);

            cntriga++;

            Cell cl7 = rowP7.createCell(1);
            cl7.setCellStyle(style3);
            cl7.setCellValue(" \n User: " + siq.getPinuser());

//            Row row = sheet.createRow((short) cntriga);
//            row.createCell(1).setCellValue(siq.getId_filiale() + " " + siq.getDe_filiale());
            XSSFRow row77 = sheet.createRow(cntriga);
            cntriga++;
            for (int j = 0; j < 6; j++) {

                if (j == 1) {

                    Cell cl5 = row77.createCell(j + 2);
                    cl5.setCellStyle(style3);
                    cl5.setCellValue("Notes");

                } else if (j == 2) {
                    Cell cl5 = row77.createCell(j + 3);
                    cl5.setCellStyle(style3);
                    cl5.setCellValue("Euro TC / Travel Cheques");
                } else {
//               Phrase phraset1 = new Phrase();
//                phraset1.add(new Chunk("", f5_bold));
//                PdfPCell cellt1 = new PdfPCell(phraset1);
//                cellt1.setHorizontalAlignment(Element.ALIGN_LEFT);
//                cellt1.setBorder( Rectangle.TOP);
//
//                cellt1.setBackgroundColor(BaseColor.LIGHT_GRAY);
//                //cellt1.setBorder(Rectangle.BOTTOM);
//                cellt1.setBorderWidth(0.7f);
//                table6.addCell(cellt1);
                }
            }

            cntriga++;
            XSSFRow row66 = sheet.createRow(cntriga);

            //mi scandisco le colonne
            for (int j = 0; j < colonne.size(); j++) {
                Cell cl5 = row66.createCell(j + 1);
                cl5.setCellStyle(style3);
                cl5.setCellValue(colonne.get(j));

            }
            cntriga++;

            //  document.add(table2);
            //  document.add(sep);
            ArrayList<ToBankingSheet_value> dati = siq.getDati();

            double branchtotal = 0, spreadtotal = 0;

            cntriga++;
            cntriga++;
            cntriga++;
            cntriga++;

            for (int i = 0; i < dati.size(); i++) {
                cntriga++;
                XSSFRow row6 = sheet.createRow(cntriga);

                ToBankingSheet_value temp = (ToBankingSheet_value) dati.get(i);

                ArrayList<String> datitemp = temp.getDati_string();

                Cell f1 = row6.createCell(1);
                f1.setCellStyle(style4);
                f1.setCellValue(temp.getCurrency());

                boolean perc = false;

                for (int n = 1; n < colonne.size(); n++) {

                    switch (colonne.get(n)) {
                        case "Branch Total":
                            branchtotal += fd((datitemp.get(n)));
                            break;
                        case "Spread":
                            spreadtotal += fd((datitemp.get(n)));
                            break;
                        case "%":
                            perc = true;
                            break;
                        default:
                            break;
                    }

                    Cell f2 = row6.createCell(n + 1, NUMERIC);
                    f2.setCellStyle(cellStylenumRATE);

                    if (!perc) {
                        f2.setCellValue(fd(datitemp.get(n)));

                    } else {
                        f2.setCellValue(fd(datitemp.get(n)) + " %");

                    }

                }

            }

            cntriga++;
            cntriga++;

            branchtotal = roundDouble(branchtotal, 2);
            spreadtotal = roundDouble(spreadtotal, 2);

            XSSFRow row9 = sheet.createRow(cntriga);

            for (int n = 1; n < colonne.size(); n++) {

                if (n == 1) {
                    Cell f7 = row9.createCell(n + 1);
                    f7.setCellStyle(style4);
                    f7.setCellValue("Total");
                } else if (colonne.get(n).equals("Branch Total")) {

                    Cell f7 = row9.createCell(n + 1, NUMERIC);
                    f7.setCellStyle(cellStylenum);
                    f7.setCellValue(fd(roundDoubleandFormat(branchtotal, 2)));

                } else if (colonne.get(n).equals("Spread")) {

                    Cell f7 = row9.createCell(n + 1, NUMERIC);
                    f7.setCellStyle(cellStylenum);
                    f7.setCellValue(fd(roundDoubleandFormat(spreadtotal, 2)));

                } else if (colonne.get(n).equals("%")) {

                    Cell f7 = row9.createCell(n + 1, NUMERIC);
                    f7.setCellStyle(cellStylenum);
                    double toperc = 0.0;
                    if (branchtotal != 0) {
                        toperc = (spreadtotal / branchtotal) * 100.00;
                    }
                    f7.setCellValue(fd(roundDoubleandFormat(toperc, 2)) + "%");

                }

            }

        } catch (Exception ex) {
            insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
        }
        cntriga++;
        cntriga++;
        cntriga++;

        XSSFRow row10 = sheet.createRow(cntriga);
        XSSFCell f7 = row10.createCell(1);
        f7.setCellStyle(style3);
        f7.setCellValue("Notes:");
        XSSFCell f71 = row10.createCell(2);
        f71.setCellStyle(style3);
        f71.setCellValue(siq.getNote());
        cntriga++;
        cntriga++;

        return cntriga;
    }

    /**
     *
     * @param intestazionePdf
     * @param siq
     * @param datereport
     * @param dati2
     * @param sheet
     * @param cntriga
     * @param style1
     * @param style2
     * @param style3
     * @param style4
     * @param cellStylenum
     * @param cellStylenumRATE
     * @return
     */
    public int receiptexceltobranching(String intestazionePdf, ToBranchingSheet_value siq, String datereport, ArrayList<NoChangeToBranchingSheet_value> dati2,
            XSSFSheet sheet, int cntriga, XSSFCellStyle style1,
            XSSFCellStyle style2, XSSFCellStyle style3, XSSFCellStyle style4,
            XSSFCellStyle cellStylenum, XSSFCellStyle cellStylenumRATE) {

        //String outputfile = "ToBranchingSheet.pdf";
        ArrayList<String> colonne = new ArrayList<>();
        colonne.add("Currency");
        colonne.add("Amount");
        colonne.add("Branch Rate");
        colonne.add("Amount");
        colonne.add("Branch Rate");
        colonne.add("Branch Total");
        colonne.add("Spread");
        colonne.add("%");

        ArrayList<String> colonne2 = new ArrayList<>();
        colonne2.add("Category");
        colonne2.add("Quantity");
        colonne2.add("Amount");
        colonne2.add("Total");

        try {

            XSSFRow rowP = sheet.createRow(cntriga);
            cntriga++;

            Cell cl = rowP.createCell(1);
            cl.setCellStyle(style1);
            cl.setCellValue(intestazionePdf);

            Cell cl32 = rowP.createCell(3);
            cl32.setCellStyle(style1);
            cl32.setCellValue("Transfer " + siq.getNumTransfer() + " " + datereport);
//           
            XSSFRow rowP3 = sheet.createRow(cntriga);
            cntriga++;

            Cell cl2 = rowP3.createCell(1);
            cl2.setCellStyle(style3);
            cl2.setCellValue(siq.getId_filiale() + " " + siq.getDe_filiale());

            XSSFRow rowP4 = sheet.createRow(cntriga);

            cntriga++;

            Cell cl4 = rowP4.createCell(1);
            cl4.setCellStyle(style3);
            cl4.setCellValue("\n From: " + siq.getFromsafe());

            XSSFRow rowP5 = sheet.createRow((short) cntriga);

            cntriga++;

            Cell cl55 = rowP5.createCell(1);
            cl55.setCellStyle(style3);
            cl55.setCellValue(" \n TO: " + siq.getTobranch());

            XSSFRow rowP7 = sheet.createRow(cntriga);

            cntriga++;

            Cell cl7 = rowP7.createCell(1);
            cl7.setCellStyle(style3);
            cl7.setCellValue(" \n User: " + siq.getPinuser());

            XSSFRow row = sheet.createRow(cntriga);
            cntriga++;
//            row.createCell(1).setCellValue(siq.getId_filiale() + " " + siq.getDe_filiale());

            XSSFRow row77 = sheet.createRow(cntriga);

            cntriga++;

            for (int j = 0; j < 6; j++) {

                if (j == 1) {

                    Cell cl5 = row77.createCell(j + 2);
                    cl5.setCellStyle(style3);
                    cl5.setCellValue("Notes");

                } else if (j == 2) {

                    Cell cl5 = row77.createCell(j + 3);
                    cl5.setCellStyle(style3);
                    cl5.setCellValue("Euro TC / Travel Cheques");

                } else {

//                   Cell cl5 = row7.createCell(j+1);
//                    cl5.setCellStyle(style3);
//                    cl5.setCellValue("Euro TC / Travel Cheques");
//                   
//               Phrase phraset1 = new Phrase();
//                phraset1.add(new Chunk("", f5_bold));
//                PdfPCell cellt1 = new PdfPCell(phraset1);
//                cellt1.setHorizontalAlignment(Element.ALIGN_LEFT);
//                cellt1.setBorder( Rectangle.TOP);
//
//                cellt1.setBackgroundColor(BaseColor.LIGHT_GRAY);
//                //cellt1.setBorder(Rectangle.BOTTOM);
//                cellt1.setBorderWidth(0.7f);
//                table6.addCell(cellt1);
                }
            }

            cntriga++;

            XSSFRow row66 = sheet.createRow(cntriga);

            cntriga++;

            if (siq.getDati().size() > 0) {

                //mi scandisco le colonne
                for (int j = 0; j < colonne.size(); j++) {
                    Cell cl5 = row66.createCell(j + 1);
                    cl5.setCellStyle(style3);
                    cl5.setCellValue(colonne.get(j));

                }

                ArrayList<ToBranchingSheet_value> dati = siq.getDati();

                double branchtotal = 0, spreadtotal = 0;
                cntriga++;
                cntriga++;
                cntriga++;

                for (int i = 0; i < dati.size(); i++) {
                    cntriga++;

                    XSSFRow row6 = sheet.createRow(cntriga);

                    ToBranchingSheet_value temp = dati.get(i);

                    ArrayList<String> datitemp = temp.getDati_string();

                    Cell f1 = row6.createCell(1);
                    f1.setCellStyle(style4);
                    f1.setCellValue(temp.getCurrency());

                    boolean perc = false;

                    for (int n = 1; n < colonne.size(); n++) {

                        switch (colonne.get(n)) {
                            case "Branch Total":
                                branchtotal += fd((datitemp.get(n)));
                                break;
                            case "Spread":
                                spreadtotal += fd((datitemp.get(n)));
                                break;
                            case "%":
                                perc = true;
                                break;
                            default:
                                break;
                        }

                        Cell f2 = row6.createCell(n + 1, NUMERIC);
                        f2.setCellStyle(cellStylenumRATE);

                        if (!perc) {
                            f2.setCellValue(fd(datitemp.get(n)));

                        } else {
                            f2.setCellValue(fd(datitemp.get(n)) + " %");

                        }

                    }

                }

                cntriga++;
                cntriga++;

                branchtotal = roundDouble(branchtotal, 2);
                spreadtotal = roundDouble(spreadtotal, 2);

                XSSFRow row9 = sheet.createRow(cntriga);

                for (int n = 1; n < colonne.size(); n++) {

                    if (n == 1) {

                        Cell f7 = row9.createCell(n + 1);
                        f7.setCellStyle(style4);
                        f7.setCellValue("Total");

                    } else if (colonne.get(n).equals("Branch Total")) {

                        Cell f7 = row9.createCell(n + 1, NUMERIC);
                        f7.setCellStyle(cellStylenum);
                        f7.setCellValue(fd(roundDoubleandFormat(branchtotal, 2)));

                    } else if (colonne.get(n).equals("Spread")) {

                        Cell f7 = row9.createCell(n + 1, NUMERIC);
                        f7.setCellStyle(cellStylenum);
                        f7.setCellValue(fd(roundDoubleandFormat(spreadtotal, 2)));

                    } else if (colonne.get(n).equals("%")) {

                        Cell f7 = row9.createCell(n + 1, NUMERIC);
                        f7.setCellStyle(cellStylenum);
                        double toperc = 0.0;
                        if (branchtotal != 0) {
                            toperc = (spreadtotal / branchtotal) * 100.00;
                        }
                        f7.setCellValue(fd(roundDoubleandFormat(toperc, 2)) + "%");

                    }

                }

                cntriga++;
                cntriga++;
                cntriga++;
                cntriga++;
                cntriga++;
                cntriga++;

                XSSFRow row10 = sheet.createRow(cntriga);
                Cell f7 = row10.createCell(1);
                f7.setCellStyle(style3);
                f7.setCellValue("Notes 2:");

                Cell f71 = row10.createCell(2);
                f71.setCellStyle(style3);
                f71.setCellValue(siq.getNote());

                cntriga++;

            }

            columnWidths2 = new float[colonne2.size()];

            for (int c = 0; c < colonne2.size(); c++) {
                columnWidths2[c] = 10f;
            }

            if (dati2.size() > 0) {

                cntriga++;
                XSSFRow row10 = sheet.createRow(cntriga);

                PdfPCell[] list = new PdfPCell[colonne2.size()];
                //mi scandisco le colonne
                for (int j = 0; j < colonne2.size(); j++) {
                    Cell f6 = row10.createCell(j + 1);
                    f6.setCellStyle(style3);
                    f6.setCellValue(colonne2.get(j));

                }

                cntriga++;

                double totQuantity = 0, total = 0;

                for (int i = 0; i < dati2.size(); i++) {

                    cntriga++;
                    XSSFRow row11 = sheet.createRow(cntriga);

                    NoChangeToBranchingSheet_value temp = (NoChangeToBranchingSheet_value) dati2.get(i);

                    Cell f7 = row11.createCell(1);
                    f7.setCellStyle(style4);
                    f7.setCellValue(temp.getCategory());

                    Cell f8 = row11.createCell(2, NUMERIC);
                    f8.setCellStyle(cellStylenum);
                    f8.setCellValue(fd(temp.getQuantity()));

                    Cell f9 = row11.createCell(3, NUMERIC);
                    f9.setCellStyle(cellStylenum);
                    f9.setCellValue(fd(temp.getAmount()));

                    Cell f10 = row11.createCell(4, NUMERIC);
                    f10.setCellStyle(cellStylenum);
                    double d = fd(temp.getQuantity()) * fd(temp.getAmount());
                    f10.setCellValue(fd(roundDoubleandFormat(d, 2)) + "");

                    totQuantity += fd(temp.getQuantity());
                    total += fd(temp.getQuantity()) * fd(temp.getAmount());

                    cntriga++;
                    cntriga++;
                }

                totQuantity = roundDouble(totQuantity, 2);
                total = roundDouble(total, 2);

                cntriga++;
                XSSFRow row12 = sheet.createRow(cntriga);

                Cell f15 = row12.createCell(1);
                f15.setCellStyle(style4);
                f15.setCellValue("Total");

                Cell f16 = row12.createCell(2, NUMERIC);
                f16.setCellStyle(cellStylenum);
                f16.setCellValue(fd(roundDoubleandFormat(totQuantity, 2)) + "");

                Cell f17 = row12.createCell(4, NUMERIC);
                f17.setCellStyle(cellStylenum);
                f17.setCellValue(fd(roundDoubleandFormat(total, 2)) + "");

                XSSFRow row101 = sheet.createRow(cntriga);
                Cell f7 = row101.createCell(cntriga);
                f7.setCellStyle(style3);
                f7.setCellValue("Notes 3:");

                cntriga++;

                XSSFRow row1011 = sheet.createRow(cntriga);
                Cell f71 = row1011.createCell(cntriga);
                f71.setCellStyle(style3);
                f71.setCellValue(siq.getNote());
                cntriga++;
            }

        } catch (Exception ex) {
            insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
        }
        cntriga++;
        cntriga++;
        cntriga++;
        return cntriga;
    }

    /**
     *
     * @param path
     * @param codlist
     * @param br
     * @param array_bank
     * @param array_creditcard
     * @return
     */
    public String mainexcel(String path, ArrayList<ET_change> codlist, ArrayList<Branch> br, ArrayList<String[]> array_bank, ArrayList<String[]> array_creditcard) {
        try {
            File pdffile = new File(path + generaId(50) + "C_BankBranch.xlsx");
            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet("C_BankBranch");
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
            style3.setAlignment(CENTER);
            style3.setBorderTop(THIN);
            style3.setBorderBottom(THIN);

            XSSFFont font4 = workbook.createFont();
            font4.setFontName(FONT_ARIAL);
            font4.setFontHeightInPoints((short) 10);

            XSSFCellStyle style4 = (XSSFCellStyle) workbook.createCellStyle();
            style4.setAlignment(CENTER);
            style4.setBorderTop(THIN);
            style4.setBorderBottom(THIN);

            XSSFCellStyle cellStylenum = (XSSFCellStyle) workbook.createCellStyle();
            XSSFDataFormat hssfDataFormat = (XSSFDataFormat) workbook.createDataFormat();
            cellStylenum.setAlignment(CENTER);
            cellStylenum.setBorderTop(THIN);
            cellStylenum.setBorderBottom(THIN);
            cellStylenum.setDataFormat(hssfDataFormat.getFormat(formatdataCell));

            XSSFCellStyle cellStylenumRATE = (XSSFCellStyle) workbook.createCellStyle();
            XSSFDataFormat hssfDataFormatRATE = (XSSFDataFormat) workbook.createDataFormat();
            cellStylenumRATE.setAlignment(CENTER);
            cellStylenumRATE.setBorderTop(THIN);
            cellStylenumRATE.setBorderBottom(THIN);
            cellStylenumRATE.setDataFormat(hssfDataFormatRATE.getFormat(formatdataCellRate));

            XSSFCellStyle style3center = (XSSFCellStyle) workbook.createCellStyle();
            style3center.setFont(font3);
            style3center.setWrapText(true);
            style3center.setAlignment(CENTER);
            style3center.setBorderTop(THIN);
            style3center.setBorderBottom(THIN);

            XSSFCellStyle style3left = (XSSFCellStyle) workbook.createCellStyle();
            style3left.setFont(font3);
            style3left.setAlignment(LEFT);
            style3left.setBorderTop(THIN);
            style3left.setBorderBottom(THIN);

            XSSFCellStyle style4left = (XSSFCellStyle) workbook.createCellStyle();
            style4left.setAlignment(LEFT);
            style4left.setBorderTop(THIN);
            style4left.setBorderBottom(THIN);

            //MAIN
            C_BankBranch nctl = new C_BankBranch();

            Db_Master dbm = new Db_Master();
            int nriga = 1;
            boolean find = false;
            for (int i = 0; i < codlist.size(); i++) {
                ET_change et = codlist.get(i);

                if (format_tofrom_brba(et.getFg_tofrom(), et.getFg_brba(), et.getCod_dest(), array_creditcard).contains("POS")) {
                    ArrayList<ToBankingSheet_value> datiPOS = dbm.list_POSBASheet_value(et);
                    if (datiPOS.size() > 0) {
                        find = true;
                    }
                    String ba = formatBankBranch(et.getCod_dest(), "BA", array_bank, null, array_creditcard);
                    ArrayList<Branch> allfill = dbm.list_branch_completeAFTER311217();
                    ToPOSBASheet bsi2 = new ToPOSBASheet();
                    ArrayList<String> alcolonne = new ArrayList<>();
                    ToBankingSheet_value pdf = new ToBankingSheet_value();
                    pdf.setAuto(et.getAuto());
                    pdf.setId_filiale(et.getFiliale());
                    pdf.setDe_filiale(formatBankBranchReport(et.getFiliale(), "BR", null, allfill));
                    pdf.setFromsafe(et.getFiliale() + " - " + formatBankBranchReport(et.getFiliale(), "BR", null, allfill));
                    pdf.setTobank(et.getCod_dest() + " - " + ba);
                    pdf.setPinuser(et.getUser());
                    pdf.setNumtranfer(et.getId());
                    pdf.setNote(et.getNote());
                    alcolonne.add("Currency");
                    alcolonne.add("Amount");
                    alcolonne.add("Amount");
                    alcolonne.add("Amount");
                    alcolonne.add("Amount");
                    alcolonne.add("Bank Total");
                    pdf.setDati(datiPOS);

                    nriga = bsi2.receiptexcel(pdf, alcolonne, formatStringtoStringDate(et.getDt_it(), patternsqldate, patternnormdate),
                            workbook, sheet, nriga,
                            style1, style2, style3, style3left, style3center, style4, style4left);

                } else if (et.getFg_brba().equals("BA") && et.getFg_tofrom().equals("T")) {
                    ArrayList<ToBankingSheet_value> dati = dbm.list_ToBankingSheet_value(et);
                    if (dati.size() > 0) {
                        find = true;
                    }
                    ToBankingSheet_value pdf = new ToBankingSheet_value();
                    String ba = formatBankBranch(et.getCod_dest(), "BA", array_bank, null, array_creditcard);
                    pdf.setId_filiale(et.getFiliale());
                    pdf.setDe_filiale(formatBankBranchReport(et.getFiliale(), "BR", null, br));
                    pdf.setFromsafe(formatBankBranch(et.getFiliale(), "BR", null, br, null));
                    pdf.setTobank(et.getCod_dest() + " " + ba);
                    pdf.setPinuser(et.getUser());
                    pdf.setNumtranfer(et.getId());
                    pdf.setDati(dati);
                    nriga = nctl.receiptexceltobanking("To Banking Sheet ", pdf, formatStringtoStringDate(et.getDt_it(),
                            patternsqldate, patternnormdate), sheet, nriga,
                            style1, style2, style3, style4, cellStylenum, cellStylenumRATE);
                } else if (et.getFg_brba().equals("BA") && et.getFg_tofrom().equals("F")) {
                    ArrayList<FromBranchingSheet_value> dati1 = dbm.list_FromBranchingSheet_value(et);
                    if (dati1.size() > 0) {
                        find = true;
                    }
                    FromBranchingSheet_value pdf = new FromBranchingSheet_value();
                    pdf.setId_filiale(et.getFiliale());
                    pdf.setDe_filiale(formatBankBranchReport(et.getFiliale(), "BR", null, br));
                    String ba = formatBankBranch(et.getCod_dest(), "BA", array_bank, null, array_creditcard);
                    pdf.setFromsafe(et.getCod_dest() + " " + ba);
                    pdf.setTobranch(formatBankBranch(et.getFiliale(), "BR", null, br, null));
                    pdf.setPinuser(et.getUser());
                    pdf.setNumtranfer(et.getId());
                    pdf.setDati(dati1);
                    nriga = nctl.receiptexcelfrombanking("From Banking Sheet ", pdf,
                            formatStringtoStringDate(et.getDt_it(), patternsqldate, patternnormdate), sheet, nriga,
                            style1, style2, style3, style4, cellStylenum, cellStylenumRATE);
                } else if (et.getFg_brba().equals("BR") && et.getFg_tofrom().equals("F")) {
                    ArrayList<FromBranchingSheet_value> dati = dbm.list_FromBranchingSheet_value(et);
                    if (dati.size() > 0) {
                        find = true;
                    }
                    ArrayList<NoChangeFromBranchingSheet_value> dati2 = dbm.list_FromBranchingSheet_valueNC(et);
                    Branch dest = dbm.get_branch(et.getCod_dest());
                    FromBranchingSheet_value pdf = new FromBranchingSheet_value();
                    pdf.setId_filiale(et.getFiliale());
                    pdf.setDe_filiale(formatBankBranchReport(et.getFiliale(), "BR", null, br));
                    pdf.setFromsafe(dest.getCod() + " - " + dest.getDe_branch());
                    pdf.setTobranch(formatBankBranch(et.getFiliale(), "BR", null, br, null));
                    pdf.setPinuser(et.getUser());
                    pdf.setNumtranfer(et.getId());
                    pdf.setDati(dati);
                    nriga = nctl.receiptexcelfrombranching("From Branching Sheet ", pdf, formatStringtoStringDate(et.getDt_it(),
                            patternsqldate, patternnormdate), dati2, sheet, nriga,
                            style1, style2, style3, style4, cellStylenum, cellStylenumRATE);
                } else if (et.getFg_brba().equals("BR") && et.getFg_tofrom().equals("T")) {
                    ArrayList<ToBranchingSheet_value> dati = dbm.list_ToBranchingSheet_value(et);
                    if (dati.size() > 0) {
                        find = true;
                    }
                    ArrayList<NoChangeToBranchingSheet_value> dati2 = dbm.list_ToBranchingSheet_valueNC(et);
                    Branch dest = dbm.get_branch(et.getCod_dest());
                    ToBranchingSheet_value pdf = new ToBranchingSheet_value();
                    pdf.setId_filiale(et.getFiliale());
                    pdf.setDe_filiale(formatBankBranchReport(et.getFiliale(), "BR", null, br));
                    pdf.setFromsafe(formatBankBranch(et.getFiliale(), "BR", null, br, null));
                    pdf.setTobranch(dest.getCod() + " - " + dest.getDe_branch());
                    pdf.setPinuser(et.getUser());
                    pdf.setNumtranfer(et.getId());
                    pdf.setDati(dati);
                    nriga = nctl.receiptexceltobranching("To Branching Sheet ", pdf, formatStringtoStringDate(et.getDt_it(), patternsqldate, patternnormdate),
                            dati2, sheet, nriga,
                            style1, style2, style3, style4, cellStylenum, cellStylenumRATE);
                }
            }
            dbm.closeDB();

            if (find) {

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

                try (FileOutputStream out = new FileOutputStream(pdffile)) {
                    workbook.write(out);
                }
                String base64 = new String(encodeBase64(readFileToByteArray(pdffile)));
                pdffile.delete();
                return base64;
            } else {
                pdffile.delete();
                return null;
            }
        } catch (IOException ex) {
            insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
        }
        return null;

    }

}
