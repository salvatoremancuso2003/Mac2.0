/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rc.so.pdf;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import static com.itextpdf.text.Element.ALIGN_CENTER;
import static com.itextpdf.text.Element.ALIGN_LEFT;
import static com.itextpdf.text.Element.ALIGN_RIGHT;
import com.itextpdf.text.Font;
import static com.itextpdf.text.Font.BOLD;
import static com.itextpdf.text.Font.BOLDITALIC;
import static com.itextpdf.text.Font.NORMAL;
import static com.itextpdf.text.FontFactory.getFont;
import com.itextpdf.text.Image;
import static com.itextpdf.text.Image.getInstance;
import static com.itextpdf.text.PageSize.A4;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import static com.itextpdf.text.Rectangle.BOX;
import static com.itextpdf.text.pdf.BaseFont.HELVETICA;
import static com.itextpdf.text.pdf.BaseFont.WINANSI;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import static com.itextpdf.text.pdf.PdfWriter.getInstance;
import rc.so.entity.Branch;
import rc.so.entity.Ch_transaction;
import rc.so.entity.Ch_transaction_value;
import rc.so.entity.Client;
import rc.so.entity.Office;
import static rc.so.util.Constant.czeet;
import static rc.so.util.Constant.patternhours_d1;
import static rc.so.util.Constant.patternnormdate_filter;
import static rc.so.util.Constant.patternsqldate;
import static rc.so.util.Engine.getConf;
import static rc.so.util.Engine.get_national_office;
import static rc.so.util.Engine.insertTR;
import static rc.so.util.Utility.fd;
import static rc.so.util.Utility.formatMysqltoDisplay;
import static rc.so.util.Utility.formatStringtoStringDate;
import static rc.so.util.Utility.getBase64;
import static rc.so.util.Utility.parseHTMLtoPDF;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import static java.lang.Thread.currentThread;
import java.util.ArrayList;
import java.util.List;
import static org.apache.commons.codec.binary.Base64.decodeBase64;
import org.joda.time.DateTime;

/**
 *
 * @author rcosco
 */
public class NewReceipt_CZ {

    Phrase vuoto = new Phrase("\n");
    PdfPCell cellempty;

    /**
     *
     */
    public static final String br = "\n";

    /**
     *
     */
    public static final String blank = " ";

    /**
     *
     */
    public static final String nbsp = " ";

    /**
     *
     */
    public static final String comma = ",";

    /**
     *
     */
    public static final String dot = ".";

    /**
     *
     */
    public static final String score = "-";

    /**
     *
     */
    public static final String percent = "%";

    Font f0_bold, f1_bold, f2_bold, f1_normal, f2_normal, f3_normal, f3_bold, f4_bold, f5_normal, f5_bold, f6_normal, f6_bold;
    Font f3_italic;

    /**
     *
     */
    public static final float[] columnWidths3 = new float[]{45f, 40f, 40f};

    /**
     *
     */
    public static final float[] columnWidths3C = new float[]{30f, 50f, 30f};

    /**
     *
     */
    public static final float[] columnWidths7 = new float[]{30f, 20f, 30f, 30f, 20f, 35f, 30f};

    /**
     * Constructor
     */
    public NewReceipt_CZ() {
        this.f0_bold = getFont(HELVETICA, WINANSI, 14.04f, BOLD);
        this.f1_bold = getFont(HELVETICA, WINANSI, 9.96f, BOLD);
        this.f2_bold = getFont(HELVETICA, WINANSI, 8f, BOLD);
        this.f3_bold = getFont(HELVETICA, WINANSI, 8f, BOLDITALIC);
        this.f4_bold = getFont(HELVETICA, WINANSI, 6.96f, BOLD);
        this.f5_bold = getFont(HELVETICA, WINANSI, 6f, BOLD);
        this.f1_normal = getFont(HELVETICA, WINANSI, 9.96f, NORMAL);
        this.f2_normal = getFont(HELVETICA, WINANSI, 8f, NORMAL);
        this.f3_normal = getFont(HELVETICA, WINANSI, 6.96f, NORMAL);
        this.f5_normal = getFont(HELVETICA, WINANSI, 6f, NORMAL);

        this.f6_normal = getFont(HELVETICA, WINANSI, 5.5f, NORMAL);
        this.f6_bold = getFont(HELVETICA, WINANSI, 5.5f, BOLD);
        this.f3_italic = getFont("Helvetica", "Cp1252", 6.96F, 2);
        this.cellempty = new PdfPCell(new Phrase("\n"));
        this.cellempty.setBorder(0);

    }

    private String formatSupportoPAG_CZ(String su01) {
        switch (su01) {
            case "01":
            case "-":
                return "HOTOVOST / CASH";
            case "04":
            case "06":
                return "CREDIT CARD";
            default:
                return su01;
        }
    }

    private String formatSupporto_CZ(String su01) {
        switch (su01) {
            case "01":
                return "BANKOVKY / NOTES";
            case "03":
                return "TRAVELLERS CHEQUE / TC";
            case "04":
                return "CASH ADVANCE / CC";
            default:
                return su01;
        }
    }

    /**
     *
     * @param pathout
     * @param ma
     * @param tra
     * @param cl
     * @param livalue
     * @param br
     * @return
     */
    public String prericevuta_CZ(String pathout, Office ma, Ch_transaction tra, Client cl, ArrayList<Ch_transaction_value> livalue, Branch br) {

        try {
            String outputfile = new DateTime().toString("yyMMddhhmmssSSS") + "PRE_CZ_" + tra.getTipotr() + ".pdf";

            File pdf = new File(pathout + outputfile);
            Document document = new Document(A4, 20, 20, 20, 20);
            try(OutputStream ou = new FileOutputStream(pdf)){
            PdfWriter wr = getInstance(document, ou);
            document.open();
            PdfPTable table = new PdfPTable(3);
            table.setWidths(columnWidths3);
            table.setWidthPercentage(100.0F);

            PdfPTable table1 = new PdfPTable(1);
            table1.setWidthPercentage(100.0F);
            PdfPCell cell1 = new PdfPCell();
            cell1.setBorder(0);
            cell1.setPadding(0);
            cell1.addElement(new Phrase(ma.getDe_office().toUpperCase(), f4_bold));
            cell1.addElement(new Phrase("sidlo / official address".toUpperCase(), f4_bold));
            cell1.addElement(new Phrase((ma.getAdd_via() + comma + ma.getAdd_cap() + comma + ma.getAdd_city()).toUpperCase(), f4_bold));
            cell1.addElement(new Phrase(("IC / company ID: " + ma.getVat()).toUpperCase(), f4_bold));
            table1.addCell(cell1);
            cell1 = new PdfPCell();
            cell1.setBorder(0);
            cell1.setPadding(0);
            cell1.addElement(table1);
            table.addCell(cell1);
            table1 = new PdfPTable(1);
            table1.setWidthPercentage(100.0F);
            cell1 = new PdfPCell();
            cell1.setBorder(0);
            cell1.setPadding(0);
            cell1.addElement(new Phrase("provozovna / branch ".toUpperCase() + br.getCod(), f4_bold));
            cell1.addElement(new Phrase((br.getAdd_via() + comma + br.getAdd_cap() + nbsp + br.getAdd_city()).toUpperCase(), f4_bold));

            if (br.getOpening().equals("") || br.getOpening().equals("-")) {

            } else {
                cell1.addElement(new Phrase(("OTEVIRACI DOBA / OPENING HOURS:").toUpperCase(), f4_bold));
                cell1.addElement(new Phrase((br.getOpening()).toUpperCase(), f4_bold));
            }

            cell1.addElement(new Phrase("WWW.FOREXCHANGE.CZ, INFO@MACCORP.CZ".toLowerCase(), f4_bold));
            table1.addCell(cell1);
            cell1 = new PdfPCell();
            cell1.setBorder(0);
            cell1.setPadding(0);
            cell1.addElement(table1);
            table.addCell(cell1);

            Image img = getInstance(decodeBase64(getConf("path.logocl")));
            img.scalePercent(40.0F);
            cell1 = new PdfPCell(img);
            cell1.setBorder(0);
            cell1.setHorizontalAlignment(ALIGN_RIGHT);
            table.addCell(cell1);

            table.addCell(cellempty);
            table.addCell(cellempty);
            table.addCell(cellempty);

            table.addCell(cellempty);
            cell1 = new PdfPCell();
            cell1.setBorder(0);
            cell1.setPadding(0);
            cell1.addElement(new Phrase(formatSupportoPAG_CZ(tra.getLocalfigures()) + "    " + tra.formatType_CZ(tra.getTipotr()).toUpperCase(), f4_bold));
            table.addCell(cell1);
            table.addCell(cellempty);

            cell1 = new PdfPCell();
            cell1.setBorder(0);
            cell1.setPadding(0);
            cell1.addElement(new Phrase("Datum/date: " + formatStringtoStringDate(tra.getData(), patternsqldate, patternnormdate_filter) + "      Time: " + formatStringtoStringDate(tra.getData(), patternsqldate, patternhours_d1), f4_bold));
            table.addCell(cell1);
            table.addCell(cellempty);

            cell1 = new PdfPCell();
            cell1.setBorder(0);
            cell1.setPadding(0);
            cell1.addElement(new Phrase("CELKOVA CISTA CASTKA / TOTAL NET: " + formatMysqltoDisplay(tra.getPay()), f4_bold));
            cell1.setHorizontalAlignment(ALIGN_RIGHT);
            table.addCell(cell1);
            table.addCell(cellempty);
            table.addCell(cellempty);
            table.addCell(cellempty);
            document.add(table);

            table = new PdfPTable(3);
            table.setWidths(columnWidths3C);
            table.setWidthPercentage(100.0F);
            table.addCell(cellempty);

            cell1 = new PdfPCell();
            cell1.setPadding(0);
            cell1.setBorder(BOX);
            cell1.setBorderWidth(0.8f);
            Paragraph p1 = new Paragraph(new Phrase("PREDSMLUVNÍ INFORMACE  / PRE-CONTRACTUAL INFORMATION", f4_bold));
            p1.setAlignment(ALIGN_CENTER);
            cell1.addElement(p1);
            cell1.setHorizontalAlignment(ALIGN_CENTER);

            table.addCell(cell1);

            table.addCell(cellempty);
            table.addCell(cellempty);
            table.addCell(cellempty);
            table.addCell(cellempty);

            document.add(table);

            table = new PdfPTable(7);
            table.setWidths(columnWidths7);
            table.setWidthPercentage(100.0F);

            cell1 = new PdfPCell();
            cell1.setPadding(0);
            cell1.setBorder(0);
            p1 = new Paragraph(new Phrase("KATEGORIE / CATEGORY", f5_bold));
            p1.setAlignment(ALIGN_LEFT);
            cell1.addElement(p1);
            table.addCell(cell1);

            cell1 = new PdfPCell();
            cell1.setPadding(0);
            cell1.setBorder(0);
            p1 = new Paragraph(new Phrase("MENA / CURRENCY", f5_bold));
            p1.setAlignment(ALIGN_CENTER);
            cell1.addElement(p1);

            table.addCell(cell1);
            cell1 = new PdfPCell();
            cell1.setPadding(0);
            cell1.setBorder(0);
            p1 = new Paragraph(new Phrase("MNOZSTVI / AMOUNT", f5_bold));
            p1.setAlignment(ALIGN_CENTER);
            cell1.addElement(p1);
            table.addCell(cell1);

            cell1 = new PdfPCell();
            cell1.setPadding(0);
            cell1.setBorder(0);
            p1 = new Paragraph(new Phrase("KURZ / EXCHANGE RATE", f5_bold));
            p1.setAlignment(ALIGN_CENTER);
            cell1.addElement(p1);
            table.addCell(cell1);

            cell1 = new PdfPCell();
            cell1.setPadding(0);
            cell1.setBorder(0);
            p1 = new Paragraph(new Phrase("POPLATEK / FEE", f5_bold));
            p1.setAlignment(ALIGN_CENTER);
            cell1.addElement(p1);
            table.addCell(cell1);

            cell1 = new PdfPCell();
            cell1.setPadding(0);
            cell1.setBorder(0);
            p1 = new Paragraph(new Phrase("ZAOKROUHLENI/ROUND OFF", f5_bold));
            p1.setAlignment(ALIGN_CENTER);
            cell1.addElement(p1);
            table.addCell(cell1);

            cell1 = new PdfPCell();
            cell1.setPadding(0);
            cell1.setBorder(0);
            p1 = new Paragraph(new Phrase("CISTA CASTKA / NET", f5_bold));
            p1.setAlignment(ALIGN_RIGHT);
            cell1.addElement(p1);
            cell1.setHorizontalAlignment(ALIGN_RIGHT);
            table.addCell(cell1);

            String typeround = "+";
            if (fd(tra.getRound()) < 0) {
                typeround = "";
            }

            for (int i = 0; i < livalue.size(); i++) {
                Ch_transaction_value chv = livalue.get(i);
                cell1 = new PdfPCell();
                cell1.setPadding(0);
                cell1.setBorder(0);
                p1 = new Paragraph(new Phrase(formatSupporto_CZ(chv.getSupporto()), f5_normal));
                p1.setAlignment(ALIGN_LEFT);
                cell1.addElement(p1);
                table.addCell(cell1);

                cell1 = new PdfPCell();
                cell1.setPadding(0);
                cell1.setBorder(0);
                p1 = new Paragraph(new Phrase(chv.getValuta(), f5_normal));
                p1.setAlignment(ALIGN_CENTER);
                cell1.addElement(p1);
                table.addCell(cell1);

                cell1 = new PdfPCell();
                cell1.setPadding(0);
                cell1.setBorder(0);
                p1 = new Paragraph(new Phrase(formatMysqltoDisplay(chv.getQuantita()), f5_normal));
                p1.setAlignment(ALIGN_CENTER);
                cell1.addElement(p1);
                table.addCell(cell1);

                cell1 = new PdfPCell();
                cell1.setPadding(0);
                cell1.setBorder(0);
                p1 = new Paragraph(new Phrase(formatMysqltoDisplay(chv.getRate()), f5_normal));
                p1.setAlignment(ALIGN_CENTER);
                cell1.addElement(p1);
                table.addCell(cell1);

                cell1 = new PdfPCell();
                cell1.setPadding(0);
                cell1.setBorder(0);
                p1 = new Paragraph(new Phrase(formatMysqltoDisplay(chv.getTot_com()), f5_normal));
                p1.setAlignment(ALIGN_CENTER);
                cell1.addElement(p1);
                table.addCell(cell1);

                cell1 = new PdfPCell();
                cell1.setPadding(0);
                cell1.setBorder(0);
                p1 = new Paragraph(new Phrase(typeround + formatMysqltoDisplay(chv.getRoundvalue()), f5_normal));
                p1.setAlignment(ALIGN_CENTER);
                cell1.addElement(p1);
                table.addCell(cell1);

                cell1 = new PdfPCell();
                cell1.setPadding(0);
                cell1.setBorder(0);
                p1 = new Paragraph(new Phrase(formatMysqltoDisplay(chv.getNet()), f5_normal));
                p1.setAlignment(ALIGN_RIGHT);
                cell1.addElement(p1);
                table.addCell(cell1);

            }

            document.add(table);

            document.add(vuoto);
            document.add(vuoto);

            table1 = new PdfPTable(1);
            table1.setWidthPercentage(95.0F);
            cell1 = new PdfPCell();
            cell1.setBorder(BOX);
            cell1.setBorderWidth(0.8f);
//            cell1.setPadding(0);
            Office of = get_national_office();
            List<Element> p = parseHTMLtoPDF(of.getTxt_ricev_1());
            for (int k = 0; k < p.size(); k++) {
                cell1.addElement(p.get(k));
            }
            table1.addCell(cell1);
            document.add(table1);

            document.add(vuoto);

            table = new PdfPTable(3);
            table.setWidths(columnWidths3);
            table.setWidthPercentage(100.0F);
            table1 = new PdfPTable(1);
            table1.setWidthPercentage(100.0F);
            cell1 = new PdfPCell();
            cell1.setBorder(0);
            cell1.setPadding(0);
            cell1.addElement(new Phrase("_________________________", f4_bold));
            cell1.addElement(new Phrase("Podpis / Signature", f5_normal));

            table.addCell(cellempty);
            table.addCell(cell1);
            table.addCell(cellempty);
            document.add(table);

            document.close();
            wr.close();
            ou.close();
            String base64 = getBase64(pdf, tra);
            pdf.delete();
            return base64;
        }
        } catch (FileNotFoundException ex) {
            insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
        } catch (DocumentException | IOException ex) {
            insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
        }
        return null;
    }

//    public String ricevuta_CZ(String pathout, Office ma, Ch_transaction tra, Client cl, ArrayList<Ch_transaction_value> livalue, Branch br) {
//
//        try {
//            String outputfile = new DateTime().toString("yyMMddhhmmssSSS") + "_CZ_" + tra.getTipotr() + ".pdf";
//
//            File pdf = new File(pathout + outputfile);
//            Document document = new Document(PageSize.A4, 20, 20, 20, 20);
//            OutputStream ou = new FileOutputStream(pdf);
//            PdfWriter wr = PdfWriter.getInstance(document, ou);
//            document.open();
//            PdfPTable table = new PdfPTable(3);
//            table.setWidths(columnWidths3);
//            table.setWidthPercentage(100.0F);
//
//            PdfPTable table1 = new PdfPTable(1);
//            table1.setWidthPercentage(100.0F);
//            PdfPCell cell1 = new PdfPCell();
//            cell1.setBorder(0);
//            cell1.setPadding(0);
//            cell1.addElement(new Phrase(ma.getDe_office().toUpperCase(), f4_bold));
//            cell1.addElement(new Phrase("sidlo / official address".toUpperCase(), f4_bold));
//            cell1.addElement(new Phrase((ma.getAdd_via() + comma + ma.getAdd_cap() + comma + ma.getAdd_city()).toUpperCase(), f4_bold));
//            cell1.addElement(new Phrase(("IC / company ID: " + ma.getVat()).toUpperCase(), f4_bold));
//            table1.addCell(cell1);
//            cell1 = new PdfPCell();
//            cell1.setBorder(0);
//            cell1.setPadding(0);
//            cell1.addElement(table1);
//            table.addCell(cell1);
//            table1 = new PdfPTable(1);
//            table1.setWidthPercentage(100.0F);
//            cell1 = new PdfPCell();
//            cell1.setBorder(0);
//            cell1.setPadding(0);
//            cell1.addElement(new Phrase("provozovna / branch ".toUpperCase() + br.getCod(), f4_bold));
//            cell1.addElement(new Phrase((br.getAdd_via() + comma + br.getAdd_cap() + nbsp + br.getAdd_city()).toUpperCase(), f4_bold));
//
//            if (br.getOpening().equals("") || br.getOpening().equals("-")) {
//
//            } else {
//                cell1.addElement(new Phrase(("OTEVIRACI DOBA / OPENING HOURS:").toUpperCase(), f4_bold));
//                cell1.addElement(new Phrase((br.getOpening()).toUpperCase(), f4_bold));
//            }
//
//            cell1.addElement(new Phrase("WWW.FOREXCHANGE.CZ, INFO@MACCORP.CZ".toLowerCase(), f4_bold));
//            table1.addCell(cell1);
//            cell1 = new PdfPCell();
//            cell1.setBorder(0);
//            cell1.setPadding(0);
//            cell1.addElement(table1);
//            table.addCell(cell1);
//
//            Image img = Image.getInstance(Base64.decodeBase64(Engine.getConf("path.logocl")));
//            img.scalePercent(40.0F);
//            cell1 = new PdfPCell(img);
//            cell1.setBorder(0);
//            cell1.setHorizontalAlignment(Rectangle.ALIGN_RIGHT);
//            table.addCell(cell1);
//
//            table.addCell(cellempty);
//            table.addCell(cellempty);
//            table.addCell(cellempty);
//
//            table.addCell(cellempty);
//            cell1 = new PdfPCell();
//            cell1.setBorder(0);
//            cell1.setPadding(0);
//            cell1.addElement(new Phrase(formatSupportoPAG_CZ(tra.getLocalfigures()) + "    " + tra.formatType_CZ(tra.getTipotr()).toUpperCase(), f4_bold));
//            table.addCell(cell1);
//            table.addCell(cellempty);
//
//            cell1 = new PdfPCell();
//            cell1.setBorder(0);
//            cell1.setPadding(0);
//            cell1.addElement(new Phrase("Datum/date: " + formatStringtoStringDate(tra.getData(), patternsqldate, patternnormdate_filter) + "      Time: " + formatStringtoStringDate(tra.getData(), patternsqldate, patternhours_d1), f4_bold));
//            table.addCell(cell1);
//            table.addCell(cellempty);
//
//            cell1 = new PdfPCell();
//            cell1.setBorder(0);
//            cell1.setPadding(0);
//            cell1.addElement(new Phrase("CELKOVA CISTA CASTKA / TOTAL NET: " + formatMysqltoDisplay(tra.getPay()), f4_bold));
//            cell1.setHorizontalAlignment(Rectangle.ALIGN_RIGHT);
//            table.addCell(cell1);
//            table.addCell(cellempty);
//            table.addCell(cellempty);
//            table.addCell(cellempty);
//            document.add(table);
//
//            table = new PdfPTable(3);
//            table.setWidths(columnWidths3C);
//            table.setWidthPercentage(100.0F);
//            table.addCell(cellempty);
//
//            cell1 = new PdfPCell();
//            cell1.setPadding(0);
//            cell1.setBorder(Rectangle.BOX);
//            cell1.setBorderWidth(0.8f);
//            Paragraph p1 = new Paragraph(new Phrase("DOKLAD  / RECEIPT", f4_bold));
//            p1.setAlignment(Rectangle.ALIGN_CENTER);
//            cell1.addElement(p1);
//            cell1.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
//
//            table.addCell(cell1);
//
//            table.addCell(cellempty);
//            table.addCell(cellempty);
//            table.addCell(cellempty);
//            table.addCell(cellempty);
//
//            document.add(table);
//
//            table = new PdfPTable(7);
//            table.setWidths(columnWidths7);
//            table.setWidthPercentage(100.0F);
//
//            cell1 = new PdfPCell();
//            cell1.setPadding(0);
//            cell1.setBorder(0);
//            p1 = new Paragraph(new Phrase("KATEGORIE / CATEGORY", f5_bold));
//            p1.setAlignment(Rectangle.ALIGN_LEFT);
//            cell1.addElement(p1);
//            table.addCell(cell1);
//
//            cell1 = new PdfPCell();
//            cell1.setPadding(0);
//            cell1.setBorder(0);
//            p1 = new Paragraph(new Phrase("MENA / CURRENCY", f5_bold));
//            p1.setAlignment(Rectangle.ALIGN_CENTER);
//            cell1.addElement(p1);
//
//            table.addCell(cell1);
//            cell1 = new PdfPCell();
//            cell1.setPadding(0);
//            cell1.setBorder(0);
//            p1 = new Paragraph(new Phrase("MNOZSTVI / AMOUNT", f5_bold));
//            p1.setAlignment(Rectangle.ALIGN_CENTER);
//            cell1.addElement(p1);
//            table.addCell(cell1);
//
//            cell1 = new PdfPCell();
//            cell1.setPadding(0);
//            cell1.setBorder(0);
//            p1 = new Paragraph(new Phrase("KURZ / EXCHANGE RATE", f5_bold));
//            p1.setAlignment(Rectangle.ALIGN_CENTER);
//            cell1.addElement(p1);
//            table.addCell(cell1);
//
//            cell1 = new PdfPCell();
//            cell1.setPadding(0);
//            cell1.setBorder(0);
//            p1 = new Paragraph(new Phrase("POPLATEK / FEE", f5_bold));
//            p1.setAlignment(Rectangle.ALIGN_CENTER);
//            cell1.addElement(p1);
//            table.addCell(cell1);
//
//            cell1 = new PdfPCell();
//            cell1.setPadding(0);
//            cell1.setBorder(0);
//            p1 = new Paragraph(new Phrase("ZAOKROUHLENI/ROUND OFF", f5_bold));
//            p1.setAlignment(Rectangle.ALIGN_CENTER);
//            cell1.addElement(p1);
//            table.addCell(cell1);
//
//            cell1 = new PdfPCell();
//            cell1.setPadding(0);
//            cell1.setBorder(0);
//            p1 = new Paragraph(new Phrase("CISTA CASTKA / NET", f5_bold));
//            p1.setAlignment(Rectangle.ALIGN_RIGHT);
//            cell1.addElement(p1);
//            cell1.setHorizontalAlignment(Rectangle.ALIGN_RIGHT);
//            table.addCell(cell1);
//
//            String typeround = "+";
//            if (fd(tra.getRound()) < 0) {
//                typeround = "";
//            }
//
//            for (int i = 0; i < livalue.size(); i++) {
//                Ch_transaction_value chv = livalue.get(i);
//                cell1 = new PdfPCell();
//                cell1.setPadding(0);
//                cell1.setBorder(0);
//                p1 = new Paragraph(new Phrase(formatSupporto_CZ(chv.getSupporto()), f5_normal));
//                p1.setAlignment(Rectangle.ALIGN_LEFT);
//                cell1.addElement(p1);
//                table.addCell(cell1);
//
//                cell1 = new PdfPCell();
//                cell1.setPadding(0);
//                cell1.setBorder(0);
//                p1 = new Paragraph(new Phrase(chv.getValuta(), f5_normal));
//                p1.setAlignment(Rectangle.ALIGN_CENTER);
//                cell1.addElement(p1);
//                table.addCell(cell1);
//
//                cell1 = new PdfPCell();
//                cell1.setPadding(0);
//                cell1.setBorder(0);
//                p1 = new Paragraph(new Phrase(formatMysqltoDisplay(chv.getQuantita()), f5_normal));
//                p1.setAlignment(Rectangle.ALIGN_CENTER);
//                cell1.addElement(p1);
//                table.addCell(cell1);
//
//                cell1 = new PdfPCell();
//                cell1.setPadding(0);
//                cell1.setBorder(0);
//                p1 = new Paragraph(new Phrase(formatMysqltoDisplay(chv.getRate()), f5_normal));
//                p1.setAlignment(Rectangle.ALIGN_CENTER);
//                cell1.addElement(p1);
//                table.addCell(cell1);
//
//                cell1 = new PdfPCell();
//                cell1.setPadding(0);
//                cell1.setBorder(0);
//                p1 = new Paragraph(new Phrase(formatMysqltoDisplay(chv.getTot_com()), f5_normal));
//                p1.setAlignment(Rectangle.ALIGN_CENTER);
//                cell1.addElement(p1);
//                table.addCell(cell1);
//
//                cell1 = new PdfPCell();
//                cell1.setPadding(0);
//                cell1.setBorder(0);
//                p1 = new Paragraph(new Phrase(typeround + formatMysqltoDisplay(chv.getRoundvalue()), f5_normal));
//                p1.setAlignment(Rectangle.ALIGN_CENTER);
//                cell1.addElement(p1);
//                table.addCell(cell1);
//
//                cell1 = new PdfPCell();
//                cell1.setPadding(0);
//                cell1.setBorder(0);
//                p1 = new Paragraph(new Phrase(formatMysqltoDisplay(chv.getNet()), f5_normal));
//                p1.setAlignment(Rectangle.ALIGN_RIGHT);
//                cell1.addElement(p1);
//                table.addCell(cell1);
//
//            }
//
//            document.add(table);
//
//            document.add(vuoto);
//            document.add(vuoto);
//
//            table1 = new PdfPTable(1);
//            table1.setWidthPercentage(95.0F);
//            cell1 = new PdfPCell();
//            cell1.setBorder(Rectangle.BOX);
//            cell1.setBorderWidth(0.8f);
////            cell1.setPadding(0);
//            Office of = Engine.get_national_office();
//            List<Element> p = parseHTMLtoPDF(of.getTxt_ricev_2());
//            for (int k = 0; k < p.size(); k++) {
//                cell1.addElement(p.get(k));
//            }
//            table1.addCell(cell1);
//            document.add(table1);
//
//            document.add(vuoto);
//
//            table = new PdfPTable(3);
//            table.setWidths(columnWidths3);
//            table.setWidthPercentage(100.0F);
//            table1 = new PdfPTable(1);
//            table1.setWidthPercentage(100.0F);
////            cell1 = new PdfPCell();
////            cell1.setBorder(0);
////            cell1.setPadding(0);
////            cell1.addElement(new Phrase("_________________________", f4_bold));
////            cell1.addElement(new Phrase("Podpis / Signature", f5_normal));
////
////            table.addCell(cellempty);
////            table.addCell(cell1);
//            table.addCell(cellempty);
//            document.add(table);
//
//            document.close();
//            wr.close();
//            ou.close();
//            String base64 = getBase64(pdf, tra);
//            pdf.delete();
//            return base64;
//        } catch (FileNotFoundException ex) {
//            insertTR("E", "System", Thread.currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
//        } catch (DocumentException | IOException ex) {
//            insertTR("E", "System", Thread.currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
//        }
//        return null;
//    }
    /**
     *
     * @param pathout
     * @param ma
     * @param tra
     * @param cl
     * @param livalue
     * @param br
     * @return
     */
    public String ricevuta_CZ_DOUBLE(String pathout, Office ma, Ch_transaction tra, Client cl, ArrayList<Ch_transaction_value> livalue, Branch br) {

//        List<String> eet_code = get_row_codici(null);
        List<String> eet_code = new ArrayList<>();
        eet_code.add("");
        eet_code.add("");
//        if (czeet) {
//            EET_send es1 = invia(tra, null);
//            eet_code = get_row_codici(es1);
//        }
        try {
            String outputfile = new DateTime().toString("yyMMddhhmmssSSS") + "_CZ_" + tra.getTipotr() + ".pdf";

            File pdf = new File(pathout + outputfile);
            Document document = new Document(A4, 20, 20, 20, 20);
            try(OutputStream ou = new FileOutputStream(pdf)){
            PdfWriter wr = getInstance(document, ou);
            document.open();

            PdfPTable table = new PdfPTable(3);
            table.setWidths(columnWidths3);
            table.setWidthPercentage(100.0F);

            PdfPTable table1 = new PdfPTable(1);
            table1.setWidthPercentage(100.0F);
            PdfPCell cell1 = new PdfPCell();
            cell1.setBorder(0);
            cell1.setPadding(0);
            cell1.addElement(new Phrase(ma.getDe_office().toUpperCase(), f4_bold));
            cell1.addElement(new Phrase("sidlo / official address".toUpperCase(), f4_bold));
            cell1.addElement(new Phrase((ma.getAdd_via() + comma + ma.getAdd_cap() + comma + ma.getAdd_city()).toUpperCase(), f4_bold));
            cell1.addElement(new Phrase(("IC / company ID: " + ma.getVat()).toUpperCase(), f4_bold));
            table1.addCell(cell1);
            cell1 = new PdfPCell();
            cell1.setBorder(0);
            cell1.setPadding(0);
            cell1.addElement(table1);
            table.addCell(cell1);
            table1 = new PdfPTable(1);
            table1.setWidthPercentage(100.0F);
            cell1 = new PdfPCell();
            cell1.setBorder(0);
            cell1.setPadding(0);
            cell1.addElement(new Phrase("provozovna / branch ".toUpperCase() + br.getCod(), f4_bold));
            cell1.addElement(new Phrase((br.getAdd_via() + comma + br.getAdd_cap() + nbsp + br.getAdd_city()).toUpperCase(), f4_bold));

            if (br.getOpening().equals("") || br.getOpening().equals("-")) {

            } else {
                cell1.addElement(new Phrase(("OTEVIRACI DOBA / OPENING HOURS:").toUpperCase(), f4_bold));
                cell1.addElement(new Phrase((br.getOpening()).toUpperCase(), f4_bold));
            }

            cell1.addElement(new Phrase("WWW.FOREXCHANGE.CZ, INFO@MACCORP.CZ".toLowerCase(), f4_bold));
            table1.addCell(cell1);
            cell1 = new PdfPCell();
            cell1.setBorder(0);
            cell1.setPadding(0);
            cell1.addElement(table1);
            table.addCell(cell1);

            Image img = getInstance(decodeBase64(getConf("path.logocl")));
            img.scalePercent(40.0F);
            cell1 = new PdfPCell(img);
            cell1.setBorder(0);
            cell1.setHorizontalAlignment(ALIGN_RIGHT);
            table.addCell(cell1);

            table.addCell(cellempty);
            table.addCell(cellempty);
            table.addCell(cellempty);

            table.addCell(cellempty);
            cell1 = new PdfPCell();
            cell1.setBorder(0);
            cell1.setPadding(0);
            cell1.addElement(new Phrase(formatSupportoPAG_CZ(tra.getLocalfigures()) + "    " + tra.formatType_CZ(tra.getTipotr()).toUpperCase(), f4_bold));
            table.addCell(cell1);
            table.addCell(cellempty);

            cell1 = new PdfPCell();
            cell1.setBorder(0);
            cell1.setPadding(0);
            cell1.addElement(new Phrase("Datum/date: " + formatStringtoStringDate(tra.getData(), patternsqldate, patternnormdate_filter) + "      Time: " + formatStringtoStringDate(tra.getData(), patternsqldate, patternhours_d1), f4_bold));
            table.addCell(cell1);
            table.addCell(cellempty);

            cell1 = new PdfPCell();
            cell1.setBorder(0);
            cell1.setPadding(0);
            cell1.addElement(new Phrase("CELKOVA CISTA CASTKA / TOTAL NET: " + formatMysqltoDisplay(tra.getPay()), f4_bold));
            cell1.setHorizontalAlignment(ALIGN_RIGHT);
            table.addCell(cell1);

            //NUOVA
            if (czeet) {
                cell1 = new PdfPCell();
                cell1.setBorder(0);
                cell1.setPadding(0);
                cell1.setColspan(3);
                cell1.addElement(new Phrase(eet_code.get(0), f4_bold));
                cell1.setHorizontalAlignment(ALIGN_LEFT);
                table.addCell(cell1);
                cell1 = new PdfPCell();
                cell1.setBorder(0);
                cell1.setPadding(0);
                cell1.setColspan(3);
                cell1.addElement(new Phrase(eet_code.get(1), f4_bold));
                cell1.setHorizontalAlignment(ALIGN_LEFT);
                table.addCell(cell1);

            }
            table.addCell(cellempty);
            table.addCell(cellempty);
            table.addCell(cellempty);
            document.add(table);
            table = new PdfPTable(3);
            table.setWidths(columnWidths3C);
            table.setWidthPercentage(100.0F);
            table.addCell(cellempty);

            cell1 = new PdfPCell();
            cell1.setPadding(0);
            cell1.setBorder(BOX);
            cell1.setBorderWidth(0.8f);
            Paragraph p1 = new Paragraph(new Phrase("DOKLAD  / RECEIPT", f4_bold));
            p1.setAlignment(ALIGN_CENTER);
            cell1.addElement(p1);
            cell1.setHorizontalAlignment(ALIGN_CENTER);

            table.addCell(cell1);

            table.addCell(cellempty);
            table.addCell(cellempty);
            table.addCell(cellempty);
            table.addCell(cellempty);

            document.add(table);

            table = new PdfPTable(7);
            table.setWidths(columnWidths7);
            table.setWidthPercentage(100.0F);

            cell1 = new PdfPCell();
            cell1.setPadding(0);
            cell1.setBorder(0);
            p1 = new Paragraph(new Phrase("KATEGORIE / CATEGORY", f5_bold));
            p1.setAlignment(ALIGN_LEFT);
            cell1.addElement(p1);
            table.addCell(cell1);

            cell1 = new PdfPCell();
            cell1.setPadding(0);
            cell1.setBorder(0);
            p1 = new Paragraph(new Phrase("MENA / CURRENCY", f5_bold));
            p1.setAlignment(ALIGN_CENTER);
            cell1.addElement(p1);

            table.addCell(cell1);
            cell1 = new PdfPCell();
            cell1.setPadding(0);
            cell1.setBorder(0);
            p1 = new Paragraph(new Phrase("MNOZSTVI / AMOUNT", f5_bold));
            p1.setAlignment(ALIGN_CENTER);
            cell1.addElement(p1);
            table.addCell(cell1);

            cell1 = new PdfPCell();
            cell1.setPadding(0);
            cell1.setBorder(0);
            p1 = new Paragraph(new Phrase("KURZ / EXCHANGE RATE", f5_bold));
            p1.setAlignment(ALIGN_CENTER);
            cell1.addElement(p1);
            table.addCell(cell1);

            cell1 = new PdfPCell();
            cell1.setPadding(0);
            cell1.setBorder(0);
            p1 = new Paragraph(new Phrase("POPLATEK / FEE", f5_bold));
            p1.setAlignment(ALIGN_CENTER);
            cell1.addElement(p1);
            table.addCell(cell1);

            cell1 = new PdfPCell();
            cell1.setPadding(0);
            cell1.setBorder(0);
            p1 = new Paragraph(new Phrase("ZAOKROUHLENI/ROUND OFF", f5_bold));
            p1.setAlignment(ALIGN_CENTER);
            cell1.addElement(p1);
            table.addCell(cell1);

            cell1 = new PdfPCell();
            cell1.setPadding(0);
            cell1.setBorder(0);
            p1 = new Paragraph(new Phrase("CISTA CASTKA / NET", f5_bold));
            p1.setAlignment(ALIGN_RIGHT);
            cell1.addElement(p1);
            cell1.setHorizontalAlignment(ALIGN_RIGHT);
            table.addCell(cell1);

            String typeround = "+";
            if (fd(tra.getRound()) < 0) {
                typeround = "";
            }

            for (int i = 0; i < livalue.size(); i++) {
                Ch_transaction_value chv = livalue.get(i);
                cell1 = new PdfPCell();
                cell1.setPadding(0);
                cell1.setBorder(0);
                p1 = new Paragraph(new Phrase(formatSupporto_CZ(chv.getSupporto()), f5_normal));
                p1.setAlignment(ALIGN_LEFT);
                cell1.addElement(p1);
                table.addCell(cell1);

                cell1 = new PdfPCell();
                cell1.setPadding(0);
                cell1.setBorder(0);
                p1 = new Paragraph(new Phrase(chv.getValuta(), f5_normal));
                p1.setAlignment(ALIGN_CENTER);
                cell1.addElement(p1);
                table.addCell(cell1);

                cell1 = new PdfPCell();
                cell1.setPadding(0);
                cell1.setBorder(0);
                p1 = new Paragraph(new Phrase(formatMysqltoDisplay(chv.getQuantita()), f5_normal));
                p1.setAlignment(ALIGN_CENTER);
                cell1.addElement(p1);
                table.addCell(cell1);

                cell1 = new PdfPCell();
                cell1.setPadding(0);
                cell1.setBorder(0);
                p1 = new Paragraph(new Phrase(formatMysqltoDisplay(chv.getRate()), f5_normal));
                p1.setAlignment(ALIGN_CENTER);
                cell1.addElement(p1);
                table.addCell(cell1);

                cell1 = new PdfPCell();
                cell1.setPadding(0);
                cell1.setBorder(0);
                p1 = new Paragraph(new Phrase(formatMysqltoDisplay(chv.getTot_com()), f5_normal));
                p1.setAlignment(ALIGN_CENTER);
                cell1.addElement(p1);
                table.addCell(cell1);

                cell1 = new PdfPCell();
                cell1.setPadding(0);
                cell1.setBorder(0);
                p1 = new Paragraph(new Phrase(typeround + formatMysqltoDisplay(chv.getRoundvalue()), f5_normal));
                p1.setAlignment(ALIGN_CENTER);
                cell1.addElement(p1);
                table.addCell(cell1);

                cell1 = new PdfPCell();
                cell1.setPadding(0);
                cell1.setBorder(0);
                p1 = new Paragraph(new Phrase(formatMysqltoDisplay(chv.getNet()), f5_normal));
                p1.setAlignment(ALIGN_RIGHT);
                cell1.addElement(p1);
                table.addCell(cell1);

            }

            document.add(table);

            document.add(vuoto);
            document.add(vuoto);

            table1 = new PdfPTable(1);
            table1.setWidthPercentage(95.0F);
            cell1 = new PdfPCell();
            cell1.setBorder(BOX);
            cell1.setBorderWidth(0.8f);
//            cell1.setPadding(0);
            Office of = get_national_office();
            List<Element> p = parseHTMLtoPDF(of.getTxt_ricev_2());
            for (int k = 0; k < p.size(); k++) {
                cell1.addElement(p.get(k));
            }
            table1.addCell(cell1);
            document.add(table1);

            document.add(vuoto);
            document.add(vuoto);
//            Chunk linebreak = new Chunk(new LineSeparator());
//            document.add(linebreak);
            document.add(vuoto);
            document.add(vuoto);
            document.add(vuoto);
            document.add(vuoto);
            document.add(vuoto);
//            document.add(vuoto);
//            document.add(vuoto);

            ///////////////////////////////////////////////////////////
            table = new PdfPTable(3);
            table.setWidths(columnWidths3);
            table.setWidthPercentage(100.0F);

            table1 = new PdfPTable(1);
            table1.setWidthPercentage(100.0F);
            cell1 = new PdfPCell();
            cell1.setBorder(0);
            cell1.setPadding(0);
            cell1.addElement(new Phrase(ma.getDe_office().toUpperCase(), f4_bold));
            cell1.addElement(new Phrase("sidlo / official address".toUpperCase(), f4_bold));
            cell1.addElement(new Phrase((ma.getAdd_via() + comma + ma.getAdd_cap() + comma + ma.getAdd_city()).toUpperCase(), f4_bold));
            cell1.addElement(new Phrase(("IC / company ID: " + ma.getVat()).toUpperCase(), f4_bold));
            table1.addCell(cell1);
            cell1 = new PdfPCell();
            cell1.setBorder(0);
            cell1.setPadding(0);
            cell1.addElement(table1);
            table.addCell(cell1);
            table1 = new PdfPTable(1);
            table1.setWidthPercentage(100.0F);
            cell1 = new PdfPCell();
            cell1.setBorder(0);
            cell1.setPadding(0);
            cell1.addElement(new Phrase("provozovna / branch ".toUpperCase() + br.getCod(), f4_bold));
            cell1.addElement(new Phrase((br.getAdd_via() + comma + br.getAdd_cap() + nbsp + br.getAdd_city()).toUpperCase(), f4_bold));

            if (br.getOpening().equals("") || br.getOpening().equals("-")) {

            } else {
                cell1.addElement(new Phrase(("OTEVIRACI DOBA / OPENING HOURS:").toUpperCase(), f4_bold));
                cell1.addElement(new Phrase((br.getOpening()).toUpperCase(), f4_bold));
            }

            cell1.addElement(new Phrase("WWW.FOREXCHANGE.CZ, INFO@MACCORP.CZ".toLowerCase(), f4_bold));
            table1.addCell(cell1);
            cell1 = new PdfPCell();
            cell1.setBorder(0);
            cell1.setPadding(0);
            cell1.addElement(table1);
            table.addCell(cell1);

            cell1 = new PdfPCell(img);
            cell1.setBorder(0);
            cell1.setHorizontalAlignment(ALIGN_RIGHT);
            table.addCell(cell1);

            table.addCell(cellempty);
            table.addCell(cellempty);
            table.addCell(cellempty);

            table.addCell(cellempty);
            cell1 = new PdfPCell();
            cell1.setBorder(0);
            cell1.setPadding(0);
            cell1.addElement(new Phrase(formatSupportoPAG_CZ(tra.getLocalfigures()) + "    " + tra.formatType_CZ(tra.getTipotr()).toUpperCase(), f4_bold));
            table.addCell(cell1);
            table.addCell(cellempty);

            cell1 = new PdfPCell();
            cell1.setBorder(0);
            cell1.setPadding(0);
            cell1.addElement(new Phrase("Datum/date: " + formatStringtoStringDate(tra.getData(), patternsqldate, patternnormdate_filter) + "      Time: " + formatStringtoStringDate(tra.getData(), patternsqldate, patternhours_d1), f4_bold));
            table.addCell(cell1);
            table.addCell(cellempty);

            cell1 = new PdfPCell();
            cell1.setBorder(0);
            cell1.setPadding(0);
            cell1.addElement(new Phrase("CELKOVA CISTA CASTKA / TOTAL NET: " + formatMysqltoDisplay(tra.getPay()), f4_bold));
            cell1.setHorizontalAlignment(ALIGN_RIGHT);
            table.addCell(cell1);

            if (czeet) {
                //NUOVA
                cell1 = new PdfPCell();
                cell1.setBorder(0);
                cell1.setPadding(0);
                cell1.setColspan(3);
                cell1.addElement(new Phrase(eet_code.get(0), f4_bold));
                cell1.setHorizontalAlignment(ALIGN_LEFT);
                table.addCell(cell1);
                cell1 = new PdfPCell();
                cell1.setBorder(0);
                cell1.setPadding(0);
                cell1.setColspan(3);
                cell1.addElement(new Phrase(eet_code.get(1), f4_bold));
                cell1.setHorizontalAlignment(ALIGN_LEFT);
                table.addCell(cell1);
                table.addCell(cellempty);
                table.addCell(cellempty);
                table.addCell(cellempty);
            }
            document.add(table);

            table = new PdfPTable(3);
            table.setWidths(columnWidths3C);
            table.setWidthPercentage(100.0F);
            table.addCell(cellempty);

            cell1 = new PdfPCell();
            cell1.setPadding(0);
            cell1.setBorder(BOX);
            cell1.setBorderWidth(0.8f);
            p1 = new Paragraph(new Phrase("DOKLAD  / RECEIPT", f4_bold));
            p1.setAlignment(ALIGN_CENTER);
            cell1.addElement(p1);
            cell1.setHorizontalAlignment(ALIGN_CENTER);

            table.addCell(cell1);

            table.addCell(cellempty);
            table.addCell(cellempty);
            table.addCell(cellempty);
            table.addCell(cellempty);

            document.add(table);

            table = new PdfPTable(7);
            table.setWidths(columnWidths7);
            table.setWidthPercentage(100.0F);

            cell1 = new PdfPCell();
            cell1.setPadding(0);
            cell1.setBorder(0);
            p1 = new Paragraph(new Phrase("KATEGORIE / CATEGORY", f5_bold));
            p1.setAlignment(ALIGN_LEFT);
            cell1.addElement(p1);
            table.addCell(cell1);

            cell1 = new PdfPCell();
            cell1.setPadding(0);
            cell1.setBorder(0);
            p1 = new Paragraph(new Phrase("MENA / CURRENCY", f5_bold));
            p1.setAlignment(ALIGN_CENTER);
            cell1.addElement(p1);

            table.addCell(cell1);
            cell1 = new PdfPCell();
            cell1.setPadding(0);
            cell1.setBorder(0);
            p1 = new Paragraph(new Phrase("MNOZSTVI / AMOUNT", f5_bold));
            p1.setAlignment(ALIGN_CENTER);
            cell1.addElement(p1);
            table.addCell(cell1);

            cell1 = new PdfPCell();
            cell1.setPadding(0);
            cell1.setBorder(0);
            p1 = new Paragraph(new Phrase("KURZ / EXCHANGE RATE", f5_bold));
            p1.setAlignment(ALIGN_CENTER);
            cell1.addElement(p1);
            table.addCell(cell1);

            cell1 = new PdfPCell();
            cell1.setPadding(0);
            cell1.setBorder(0);
            p1 = new Paragraph(new Phrase("POPLATEK / FEE", f5_bold));
            p1.setAlignment(ALIGN_CENTER);
            cell1.addElement(p1);
            table.addCell(cell1);

            cell1 = new PdfPCell();
            cell1.setPadding(0);
            cell1.setBorder(0);
            p1 = new Paragraph(new Phrase("ZAOKROUHLENI/ROUND OFF", f5_bold));
            p1.setAlignment(ALIGN_CENTER);
            cell1.addElement(p1);
            table.addCell(cell1);

            cell1 = new PdfPCell();
            cell1.setPadding(0);
            cell1.setBorder(0);
            p1 = new Paragraph(new Phrase("CISTA CASTKA / NET", f5_bold));
            p1.setAlignment(ALIGN_RIGHT);
            cell1.addElement(p1);
            cell1.setHorizontalAlignment(ALIGN_RIGHT);
            table.addCell(cell1);

            for (int i = 0; i < livalue.size(); i++) {
                Ch_transaction_value chv = livalue.get(i);
                cell1 = new PdfPCell();
                cell1.setPadding(0);
                cell1.setBorder(0);
                p1 = new Paragraph(new Phrase(formatSupporto_CZ(chv.getSupporto()), f5_normal));
                p1.setAlignment(ALIGN_LEFT);
                cell1.addElement(p1);
                table.addCell(cell1);

                cell1 = new PdfPCell();
                cell1.setPadding(0);
                cell1.setBorder(0);
                p1 = new Paragraph(new Phrase(chv.getValuta(), f5_normal));
                p1.setAlignment(ALIGN_CENTER);
                cell1.addElement(p1);
                table.addCell(cell1);

                cell1 = new PdfPCell();
                cell1.setPadding(0);
                cell1.setBorder(0);
                p1 = new Paragraph(new Phrase(formatMysqltoDisplay(chv.getQuantita()), f5_normal));
                p1.setAlignment(ALIGN_CENTER);
                cell1.addElement(p1);
                table.addCell(cell1);

                cell1 = new PdfPCell();
                cell1.setPadding(0);
                cell1.setBorder(0);
                p1 = new Paragraph(new Phrase(formatMysqltoDisplay(chv.getRate()), f5_normal));
                p1.setAlignment(ALIGN_CENTER);
                cell1.addElement(p1);
                table.addCell(cell1);

                cell1 = new PdfPCell();
                cell1.setPadding(0);
                cell1.setBorder(0);
                p1 = new Paragraph(new Phrase(formatMysqltoDisplay(chv.getTot_com()), f5_normal));
                p1.setAlignment(ALIGN_CENTER);
                cell1.addElement(p1);
                table.addCell(cell1);

                cell1 = new PdfPCell();
                cell1.setPadding(0);
                cell1.setBorder(0);
                p1 = new Paragraph(new Phrase(typeround + formatMysqltoDisplay(chv.getRoundvalue()), f5_normal));
                p1.setAlignment(ALIGN_CENTER);
                cell1.addElement(p1);
                table.addCell(cell1);

                cell1 = new PdfPCell();
                cell1.setPadding(0);
                cell1.setBorder(0);
                p1 = new Paragraph(new Phrase(formatMysqltoDisplay(chv.getNet()), f5_normal));
                p1.setAlignment(ALIGN_RIGHT);
                cell1.addElement(p1);
                table.addCell(cell1);

            }

            document.add(table);

            document.add(vuoto);
            document.add(vuoto);

            table1 = new PdfPTable(1);
            table1.setWidthPercentage(95.0F);
            cell1 = new PdfPCell();
            cell1.setBorder(BOX);
            cell1.setBorderWidth(0.8f);
//            cell1.setPadding(0);
            p = parseHTMLtoPDF(of.getTxt_ricev_2());
            for (int k = 0; k < p.size(); k++) {
                cell1.addElement(p.get(k));
            }
            table1.addCell(cell1);
            document.add(table1);

            document.close();
            wr.close();
            ou.close();
            String base64 = getBase64(pdf, tra);
            pdf.delete();
            return base64;
        }
        } catch (FileNotFoundException ex) {
            insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
        } catch (DocumentException | IOException ex) {
            insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
        }
        return null;
    }

}
