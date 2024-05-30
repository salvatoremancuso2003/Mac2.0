/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rc.so.reportcentrale;

import static com.itextpdf.text.BaseColor.LIGHT_GRAY;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
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
import static rc.so.util.Constant.formatdataCell;
import static rc.so.util.Constant.formatdataCellint;
import static rc.so.util.Engine.formatBankBranch;
import static rc.so.util.Engine.insertTR;
import static rc.so.util.Utility.fd;
import static rc.so.util.Utility.formatMysqltoDisplay;
import static rc.so.util.Utility.generaId;
import static rc.so.util.Utility.parseIntR;
import static rc.so.util.Utility.roundDouble;
import static rc.so.util.Utility.roundDoubleandFormat;
import java.awt.Color;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import static java.lang.Integer.parseInt;
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
import static org.apache.poi.ss.usermodel.HorizontalAlignment.LEFT;
import static org.apache.poi.ss.usermodel.HorizontalAlignment.RIGHT;

/**
 *
 * @author fplacanica
 */
public class C_ChangeInternetBookingForBranches {

    // column
    /**
     *
     */
    public static final float[] columnWidths0 = new float[] { 70f, 30f };

    /**
     *
     */
    public static final float[] columnWidths1 = new float[] { 60f, 30f };

    /**
     *
     */
    public static float[] columnWidths2 = new float[] { 15f, 15f, 10f, 10f, 10f, 10f, 10f, 10f, 10f, 10f, 10f, 10f, 10f,
            10f, 10f, 10f };

    /**
     *
     */
    public static float[] columnWidths2total = new float[] { 15f, 15f, 5f, 15f, 10f, 10f, 10f, 10f, 10f, 10f, 10f, 10f,
            10f, 10f, 10f, 10f };

    /**
     *
     */
    public static float[] columnWidths3 = new float[] { 5f, 20f, 50f, 10f, 10f, 15f, 10f, 10f };

    /**
     *
     */
    public static float[] columnWidths4 = new float[] { 30f, 20f, 15f, 15f, 10f, 15f };

    /**
     *
     */
    public static float[] columnWidths5 = new float[] { 30f, 20f, 15f, 15f, 10f, 15f };

    /**
     *
     */
    public static float[] columnWidths6 = new float[] { 30f, 20f, 15f, 15f, 10f, 15f };
    final String intestazionePdf = "Change Internet Booking For Branches";
    Phrase vuoto = new Phrase("\n");

    Color c = new Color(23, 32, 43);

    // resource
    // public static final String logo = "web/resource/logocl.png";
    // other
    /**
     *
     */
    public static final String br = "\n";

    /**
     *
     */
    public static final String blank = " ";

    Font f0_bold, f1_bold, f2_bold, f1_normal, f2_normal, f3_normal, f3_bold, f4_bold, f5_bold;

    int totalNTrans;
    int totalNTransFINALE = 0;
    double totalQty;
    double totalCurrency;
    double totalPercCom;
    double totalCom;
    double totalNet;
    double totalBuy;
    double totalSpread;
    double totalComFix;

    int S_totalNTrans;
    int S_totalNTransFINALE = 0;
    double S_totalQty;
    double S_totalCurrency;
    double S_totalPercCom;
    double S_totalCom;
    double S_totalNet;
    double S_totalBuy;
    double S_totalSpread;
    double S_totalComFix;

    int B_totalNTrans;
    int B_totalNTransFINALE = 0;
    double B_totalQty;
    double B_totalCurrency;
    double B_totalPercCom;
    double B_totalCom;
    double B_totalNet;
    double B_totalBuy;
    double B_totalSpread;
    double B_totalComFix;

    ArrayList<Canale> totaliCanali = new ArrayList<>();

    /**
     ** Constructor
     */
    public C_ChangeInternetBookingForBranches() {

        this.f0_bold = getFont(HELVETICA, WINANSI, 14.04f, BOLD);
        this.f1_bold = getFont(HELVETICA, WINANSI, 9.96f, BOLD);
        this.f2_bold = getFont(HELVETICA, WINANSI, 8f, BOLD);
        this.f3_bold = getFont(HELVETICA, WINANSI, 8f, BOLDITALIC);
        this.f4_bold = getFont(HELVETICA, WINANSI, 5f, BOLD);
        this.f1_normal = getFont(HELVETICA, WINANSI, 9.96f, NORMAL);
        this.f2_normal = getFont(HELVETICA, WINANSI, 8f, NORMAL);
        this.f3_normal = getFont(HELVETICA, WINANSI, 5f, NORMAL);
        this.f5_bold = getFont(HELVETICA, WINANSI, 6f, BOLD);

    }

    /**
     *
     * @param cmfb
     * @param colonne
     * @param firstTime
     * @param lastTime
     * @param document
     * @return
     */
    public Document receipt(C_ChangeInternetBookingForBranches_value cmfb, ArrayList<String> colonne, boolean firstTime,
            boolean lastTime, Document document) {

        try {

            if (firstTime) {
                PdfPTable table = new PdfPTable(2);
                table.setWidths(columnWidths0);
                table.setWidthPercentage(100);
                Phrase phrase1 = new Phrase();
                phrase1.add(
                        new Chunk(intestazionePdf + " From " + cmfb.getDataDa() + " to " + cmfb.getDataA(), f3_bold));
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

            ArrayList<C_ChangeInternetBookingForBranches_value> dati = cmfb.getDati();
            PdfPCell cell;
            Phrase phraset;
            PdfPCell cellt;
            PdfPTable table3;

            ArrayList<Canale> canali = new ArrayList<>();

            LineSeparator sep = new LineSeparator();
            sep.setOffset(-2);
            sep.setLineWidth((float) 0.5);

            PdfPTable table4 = new PdfPTable(2);
            table4.setWidths(columnWidths0);
            table4.setWidthPercentage(100);

            phraset = new Phrase();
            phraset.add(new Chunk("\n " + cmfb.getDe_filiale(), f3_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_LEFT);
            cellt.setBorder(BOTTOM);
            table4.addCell(cellt);

            phraset = new Phrase();
            phraset.add(new Chunk("", f4_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_LEFT);
            cellt.setBorder(BOTTOM);
            table4.addCell(cellt);

            document.add(table4);

            PdfPTable table2 = new PdfPTable(colonne.size());
            table2.setWidths(columnWidths2);
            table2.setWidthPercentage(100);

            PdfPCell[] list = new PdfPCell[colonne.size()];
            // mi scandisco le colonne
            for (int c_1 = 0; c_1 < colonne.size(); c_1++) {
                Phrase phraset1 = new Phrase();
                phraset1.add(new Chunk(colonne.get(c_1), f4_bold));
                PdfPCell cellt1 = new PdfPCell(phraset1);
                cellt1.setHorizontalAlignment(ALIGN_RIGHT);
                cellt1.setBorder(BOTTOM | TOP);
                cellt1.setFixedHeight(20f);
                cellt1.setBackgroundColor(LIGHT_GRAY);
                cellt1.setBorderWidth(0.7f);
                if (c_1 == 0 || c_1 == 1 || c_1 == 3 || c_1 == 4 || c_1 == 13 || c_1 == 14 || c_1 == 15) {
                    cellt1.setHorizontalAlignment(ALIGN_LEFT);
                }
                list[c_1] = cellt1;
            }

            table3 = new PdfPTable(colonne.size());
            table3.setWidths(columnWidths2);
            table3.setWidthPercentage(100);

            for (PdfPCell list1 : list) {
                PdfPCell temp = (PdfPCell) (list1);
                table3.addCell(temp);
            }

            document.add(table3);

            ArrayList<C_ChangeInternetBookingForBranches_value> lista = cmfb.getDati();

            // lista totali per canali
            for (int x = 0; x < lista.size(); x++) {

                C_ChangeInternetBookingForBranches_value ogg = lista.get(x);
                String canale = ogg.getDescription();
                String tipotr = ogg.getTipotr();

                // scorro la lista per vedere se è presente la descrizione in un altro oggetto
                Canale cccc = presenteDescrizione(canale, canali, tipotr);
                if (cccc != null) {
                    // System.out.println(canale + " -
                    // rc.so.reportcentrale.C_ChangeInternetBookingForBranches.receipt() " +
                    // canali.toString());
                    cccc.setNtrans((parseInt(cccc.getNtrans()) + 1) + "");
                    cccc.setQty(fd(ogg.getQty()) + fd(cccc.getQty()) + "");
                    cccc.setTotalCurrency(fd(ogg.getTotalCurrency()) + fd(cccc.getTotalCurrency()) + "");
                    cccc.setPercCom(fd(ogg.getPercCom()) + fd(cccc.getPercCom()) + "");
                    cccc.setTotalCom(fd(ogg.getTotalCom()) + fd(cccc.getTotalCom()) + "");
                    cccc.setTotalNet(fd(ogg.getTotalNet()) + fd(cccc.getTotalNet()) + "");
                    cccc.setTotalBuy(fd(ogg.getTotalBuy()) + fd(cccc.getTotalBuy()) + "");
                    cccc.setTotalSpread(fd(ogg.getTotalSpread()) + fd(cccc.getTotalSpread()) + "");
                    cccc.setTotalComFix(fd(ogg.getTotalComFix()) + fd(cccc.getTotalComFix()) + "");
                } else {
                    Canale cx = new Canale();
                    cx.setDescription(canale);
                    cx.setNtrans("1");
                    cx.setQty(ogg.getQty());
                    cx.setTotalCurrency(ogg.getTotalCurrency());
                    cx.setPercCom(ogg.getPercCom());
                    cx.setTotalCom(ogg.getTotalCom());
                    cx.setTotalNet(ogg.getTotalNet());
                    cx.setTotalBuy(ogg.getTotalBuy());
                    cx.setTotalSpread(ogg.getTotalSpread());
                    cx.setTotalComFix(ogg.getTotalComFix());
                    cx.setTipotr(ogg.getTipotr());
                    canali.add(cx);

                }

            }

            for (int j = 0; j < dati.size(); j++) {

                C_ChangeInternetBookingForBranches_value temp = (C_ChangeInternetBookingForBranches_value) dati.get(j);

                sep = new LineSeparator();
                sep.setOffset(-2);
                sep.setLineWidth((float) 0.5);
                // System.out.println("rc.so.reportcentrale.C_ChangeInternetBookingForBranches.receipt()
                // " + temp.getDe_filiale());
                table4 = new PdfPTable(colonne.size());
                table4.setWidths(columnWidths2);
                table4.setWidthPercentage(100);

                phraset = new Phrase();
                phraset.add(new Chunk(temp.getTransaction(), f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_LEFT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(temp.getDate(), f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_LEFT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(temp.getUser(), f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(temp.getCurrency(), f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_LEFT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(temp.getSupport(), f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_LEFT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(temp.getQty()), f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(fd(temp.getTotalCurrency()), 2)),
                        f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(temp.getPercCom()), f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(temp.getTotalCom()), f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(temp.getTotalNet()), f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(temp.getTotalBuy()), f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(temp.getTotalSpread()), f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(temp.getTotalComFix()), f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(temp.getKindClient(), f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_LEFT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(temp.getCode(), f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_LEFT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(temp.getDescription(), f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_LEFT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);

                document.add(table4);

            }

            int nTrans = 0;
            double totQty = 0;
            double totCurrency = 0;
            double percCom = 0;
            double totCom = 0;
            double totNet = 0;
            double totBuy = 0;
            double totSpread = 0;
            double totComFix = 0;
            int S_nTrans = 0;
            double S_totQty = 0;
            double S_totCurrency = 0;
            double S_percCom = 0;
            double S_totCom = 0;
            double S_totNet = 0;
            double S_totBuy = 0;
            double S_totSpread = 0;
            double S_totComFix = 0;
            int B_nTrans = 0;
            double B_totQty = 0;
            double B_totCurrency = 0;
            double B_percCom = 0;
            double B_totCom = 0;
            double B_totNet = 0;
            double B_totBuy = 0;
            double B_totSpread = 0;
            double B_totComFix = 0;

            // totale canale
            for (int c = 0; c < canali.size(); c++) {

                sep = new LineSeparator();
                sep.setLineWidth(0.7f);
                document.add(sep);

                table4 = new PdfPTable(colonne.size());
                table4.setWidths(columnWidths2total);
                table4.setWidthPercentage(100);

                phraset = new Phrase();
                phraset.add(new Chunk("", f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk("", f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(canali.get(c).getTipotr().equals("B") ? "BUY" : "SELL", f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(canali.get(c).getDescription(), f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_LEFT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(canali.get(c).getNtrans(), f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);

                phraset = new Phrase();
                // phraset.add(new Chunk(Utility.formatMysqltoDisplay(canali.get(c).getQty()),
                // f4_bold));
                phraset.add(new Chunk("", f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(
                        formatMysqltoDisplay(roundDoubleandFormat(fd(canali.get(c).getTotalCurrency()), 2)), f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);

                phraset = new Phrase();
                double d = fd(canali.get(c).getPercCom()) / fd(canali.get(c).getNtrans());
                phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(d, 2)), f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(fd(canali.get(c).getTotalCom()), 2)),
                        f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(fd(canali.get(c).getTotalNet()), 2)),
                        f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(fd(canali.get(c).getTotalBuy()), 2)),
                        f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(fd(canali.get(c).getTotalSpread()), 2)),
                        f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(fd(canali.get(c).getTotalComFix()), 2)),
                        f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk("", f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk("", f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk("", f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);

                document.add(table4);

                nTrans += parseIntR(canali.get(c).getNtrans());
                totQty += fd(canali.get(c).getQty());
                totCurrency += fd(canali.get(c).getTotalCurrency());
                percCom += fd(canali.get(c).getPercCom()) / fd(canali.get(c).getNtrans());
                totCom += fd(canali.get(c).getTotalCom());
                totNet += fd(canali.get(c).getTotalNet());
                totBuy += fd(canali.get(c).getTotalBuy());
                totSpread += fd(canali.get(c).getTotalSpread());
                totComFix += fd(canali.get(c).getTotalComFix());

                if (canali.get(c).getTipotr().equals("B")) {
                    B_nTrans += parseIntR(canali.get(c).getNtrans());
                    B_totQty += fd(canali.get(c).getQty());
                    B_totCurrency += fd(canali.get(c).getTotalCurrency());
                    B_percCom += percCom;
                    B_totCom += fd(canali.get(c).getTotalCom());
                    B_totNet += fd(canali.get(c).getTotalNet());
                    B_totBuy += fd(canali.get(c).getTotalBuy());
                    B_totSpread += fd(canali.get(c).getTotalSpread());
                    B_totComFix += fd(canali.get(c).getTotalComFix());
                } else {
                    S_nTrans += parseIntR(canali.get(c).getNtrans());
                    S_totQty += fd(canali.get(c).getQty());
                    S_totCurrency += fd(canali.get(c).getTotalCurrency());
                    S_percCom += percCom;
                    S_totCom += fd(canali.get(c).getTotalCom());
                    S_totNet += fd(canali.get(c).getTotalNet());
                    S_totBuy += fd(canali.get(c).getTotalBuy());
                    S_totSpread += fd(canali.get(c).getTotalSpread());
                    S_totComFix += fd(canali.get(c).getTotalComFix());
                }
                // linea totale
                sep.setLineWidth(0.7f);
                document.add(sep);
                totaliCanali.add(canali.get(c));
            }

            totQty = roundDouble(totQty, 2);
            totCurrency = roundDouble(totCurrency, 2);
            percCom = roundDouble(percCom, 2);
            totCom = roundDouble(totCom, 2);
            totNet = roundDouble(totNet, 2);
            totBuy = roundDouble(totBuy, 2);
            totSpread = roundDouble(totSpread, 2);
            totComFix = roundDouble(totComFix, 2);

            S_totQty = roundDouble(S_totQty, 2);
            S_totCurrency = roundDouble(S_totCurrency, 2);
            S_percCom = roundDouble(S_percCom, 2);
            S_totCom = roundDouble(S_totCom, 2);
            S_totNet = roundDouble(S_totNet, 2);
            S_totBuy = roundDouble(S_totBuy, 2);
            S_totSpread = roundDouble(S_totSpread, 2);
            S_totComFix = roundDouble(S_totComFix, 2);

            B_totQty = roundDouble(B_totQty, 2);
            B_totCurrency = roundDouble(B_totCurrency, 2);
            B_percCom = roundDouble(B_percCom, 2);
            B_totCom = roundDouble(B_totCom, 2);
            B_totNet = roundDouble(B_totNet, 2);
            B_totBuy = roundDouble(B_totBuy, 2);
            B_totSpread = roundDouble(B_totSpread, 2);
            B_totComFix = roundDouble(B_totComFix, 2);

            // totale canale fine
            // linea totale per branch
            sep = new LineSeparator();
            sep.setLineWidth(0.7f);
            document.add(sep);

            if (B_totQty > 0) {
                table4 = new PdfPTable(colonne.size());
                table4.setWidths(columnWidths2total);
                table4.setWidthPercentage(100);

                phraset = new Phrase();
                phraset.add(new Chunk("", f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk("", f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk("BUY", f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk("Total", f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_LEFT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(String.valueOf(B_nTrans), f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);

                phraset = new Phrase();
                // phraset.add(new Chunk(Utility.formatMysqltoDisplay(String.valueOf(totQty)),
                // f4_bold));
                phraset.add(new Chunk("", f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(B_totCurrency, 2)), f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);

                phraset = new Phrase();
                double B_totalPercCom = 0.0;
                if (B_nTrans != 0) {
                    B_totalPercCom = (B_percCom / B_nTrans) * 100.00;
                }
                phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(B_totalPercCom, 2)), f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(B_totCom, 2)), f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(B_totNet, 2)), f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(B_totBuy, 2)), f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(B_totSpread, 2)), f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(B_totComFix, 2)), f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk("", f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk("", f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk("", f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);

                document.add(table4);
            }

            if (S_totQty > 0) {
                table4 = new PdfPTable(colonne.size());
                table4.setWidths(columnWidths2total);
                table4.setWidthPercentage(100);

                phraset = new Phrase();
                phraset.add(new Chunk("", f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk("", f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk("SELL", f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk("Total", f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_LEFT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(String.valueOf(S_nTrans), f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk("", f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(S_totCurrency, 2)), f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);
                phraset = new Phrase();
                double S_totalPercCom = 0.0;
                if (S_nTrans != 0) {
                    S_totalPercCom = (S_percCom / S_nTrans) * 100.00;
                }
                phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(S_totalPercCom, 2)), f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(S_totCom, 2)), f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(S_totNet, 2)), f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(S_totBuy, 2)), f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(S_totSpread, 2)), f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(S_totComFix, 2)), f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk("", f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk("", f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk("", f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);

                document.add(table4);
            }

            table4 = new PdfPTable(colonne.size());
            table4.setWidths(columnWidths2total);
            table4.setWidthPercentage(100);

            phraset = new Phrase();
            phraset.add(new Chunk("", f4_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setBorder(BOTTOM);
            table4.addCell(cellt);

            phraset = new Phrase();
            phraset.add(new Chunk("", f4_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setBorder(BOTTOM);
            table4.addCell(cellt);

            phraset = new Phrase();
            phraset.add(new Chunk("", f4_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setBorder(BOTTOM);
            table4.addCell(cellt);

            phraset = new Phrase();
            phraset.add(new Chunk("Total", f4_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_LEFT);
            cellt.setBorder(BOTTOM);
            table4.addCell(cellt);

            phraset = new Phrase();
            phraset.add(new Chunk(nTrans + "", f4_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setBorder(BOTTOM);
            table4.addCell(cellt);

            phraset = new Phrase();
            // phraset.add(new Chunk(Utility.formatMysqltoDisplay(String.valueOf(totQty)),
            // f4_bold));
            phraset.add(new Chunk("", f4_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setBorder(BOTTOM);
            table4.addCell(cellt);

            phraset = new Phrase();
            phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(totCurrency, 2)), f4_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setBorder(BOTTOM);
            table4.addCell(cellt);

            phraset = new Phrase();
            phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(percCom / nTrans, 2)), f4_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setBorder(BOTTOM);
            table4.addCell(cellt);

            phraset = new Phrase();
            phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(totCom, 2)), f4_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setBorder(BOTTOM);
            table4.addCell(cellt);

            phraset = new Phrase();
            phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(totNet, 2)), f4_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setBorder(BOTTOM);
            table4.addCell(cellt);

            phraset = new Phrase();
            phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(totBuy, 2)), f4_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setBorder(BOTTOM);
            table4.addCell(cellt);

            phraset = new Phrase();
            phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(totSpread, 2)), f4_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setBorder(BOTTOM);
            table4.addCell(cellt);

            phraset = new Phrase();
            phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(totComFix, 2)), f4_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setBorder(BOTTOM);
            table4.addCell(cellt);

            phraset = new Phrase();
            phraset.add(new Chunk("", f4_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setBorder(BOTTOM);
            table4.addCell(cellt);

            phraset = new Phrase();
            phraset.add(new Chunk("", f4_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setBorder(BOTTOM);
            table4.addCell(cellt);

            phraset = new Phrase();
            phraset.add(new Chunk("", f4_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setBorder(BOTTOM);
            table4.addCell(cellt);

            document.add(table4);

            // linea totale
            sep.setLineWidth(0.7f);
            document.add(sep);

            totalNTrans += nTrans;
            totalQty += totQty;
            totalCurrency += totCurrency;
            totalPercCom += percCom / nTrans;
            totalCom += totCom;
            totalNet += totNet;
            totalBuy += totBuy;
            totalSpread += totSpread;
            totalComFix += totComFix;

            B_totalNTrans += B_nTrans;
            B_totalQty += B_totQty;
            B_totalCurrency += B_totCurrency;
            double B_totalPercCom = 0.0;
            if (B_nTrans != 0) {
                B_totalPercCom = (B_percCom / B_nTrans) * 100.00;
            }
            B_totalCom += B_totCom;
            B_totalNet += B_totNet;
            B_totalBuy += B_totBuy;
            B_totalSpread += B_totSpread;
            B_totalComFix += B_totComFix;

            S_totalNTrans += S_nTrans;
            S_totalQty += S_totQty;
            S_totalCurrency += S_totCurrency;
            double S_totalPercCom = 0.0;
            if (S_nTrans != 0) {
                S_totalPercCom = (S_percCom / S_nTrans) * 100.00;
            }
            S_totalCom += S_totCom;
            S_totalNet += S_totNet;
            S_totalBuy += S_totBuy;
            S_totalSpread += S_totSpread;
            S_totalComFix += S_totComFix;

            if (lastTime) {

                // sistemare ArrayList<String> totali per canali
                ArrayList<Canale> totaleCanaliFinali = new ArrayList<>();

                for (int x = 0; x < totaliCanali.size(); x++) {

                    Canale ogg = totaliCanali.get(x);
                    String canale = ogg.getDescription();
                    String tipotr = ogg.getTipotr();

                    // scorro la lista per vedere se è presente la descrizione in un altro oggetto
                    Canale cccc = presenteDescrizione(canale, totaleCanaliFinali, tipotr);
                    if (cccc != null) {
                        // da modificare NTrans
                        cccc.setNtrans(String.valueOf(parseIntR(ogg.getNtrans()) + parseIntR(cccc.getNtrans())));
                        cccc.setQty(roundDoubleandFormat(fd(ogg.getQty()) + fd(cccc.getQty()), 2));
                        cccc.setTotalCurrency(
                                roundDoubleandFormat(fd(ogg.getTotalCurrency()) + fd(cccc.getTotalCurrency()), 2));
                        cccc.setPercCom(roundDoubleandFormat(fd(ogg.getPercCom()) + fd(cccc.getPercCom()), 2));
                        cccc.setTotalCom(roundDoubleandFormat(fd(ogg.getTotalCom()) + fd(cccc.getTotalCom()), 2));
                        cccc.setTotalNet(roundDoubleandFormat(fd(ogg.getTotalNet()) + fd(cccc.getTotalNet()), 2));
                        cccc.setTotalBuy(roundDoubleandFormat(fd(ogg.getTotalBuy()) + fd(cccc.getTotalBuy()), 2));
                        cccc.setTotalSpread(
                                roundDoubleandFormat(fd(ogg.getTotalSpread()) + fd(cccc.getTotalSpread()), 2));
                        cccc.setTotalComFix(
                                roundDoubleandFormat(fd(ogg.getTotalComFix()) + fd(cccc.getTotalComFix()), 2));
                    } else {
                        Canale c = new Canale();
                        c.setDescription(canale);
                        c.setNtrans(ogg.getNtrans());
                        c.setQty(ogg.getQty());
                        c.setTotalCurrency(ogg.getTotalCurrency());
                        c.setPercCom(ogg.getPercCom());
                        c.setTotalCom(ogg.getTotalCom());
                        c.setTotalNet(ogg.getTotalNet());
                        c.setTotalBuy(ogg.getTotalBuy());
                        c.setTotalSpread(ogg.getTotalSpread());
                        c.setTotalComFix(ogg.getTotalComFix());
                        c.setTipotr(ogg.getTipotr());
                        totaleCanaliFinali.add(c);

                    }

                }

                // fine ArrayList<String> totali
                vuoto.setFont(f3_normal);
                document.add(vuoto);
                document.add(vuoto);
                document.add(vuoto);

                PdfPTable table = new PdfPTable(2);
                table.setWidths(columnWidths0);
                table.setWidthPercentage(100);
                Phrase phrase1 = new Phrase();
                phrase1.add(new Chunk("Total for selected branches", f5_bold));
                PdfPCell cell1 = new PdfPCell(phrase1);
                cell1.setBorder(NO_BORDER);

                Phrase phrase4 = new Phrase();
                phrase4.add(new Chunk("", f3_normal));
                PdfPCell cell4 = new PdfPCell(phrase4);
                cell4.setBorder(NO_BORDER);
                table.addCell(cell1);
                table.addCell(cell4);
                document.add(table);
                vuoto.setFont(f3_normal);
                document.add(vuoto);

                // totale canale
                for (int c = 0; c < totaleCanaliFinali.size(); c++) {

                    sep = new LineSeparator();
                    sep.setLineWidth(0.7f);
                    document.add(sep);

                    table4 = new PdfPTable(colonne.size());
                    table4.setWidths(columnWidths2total);
                    table4.setWidthPercentage(100);

                    phraset = new Phrase();
                    phraset.add(new Chunk("", f4_bold));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setBorder(BOTTOM);
                    table4.addCell(cellt);

                    phraset = new Phrase();
                    phraset.add(new Chunk("", f4_bold));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setBorder(BOTTOM);
                    table4.addCell(cellt);

                    phraset = new Phrase();
                    phraset.add(new Chunk(totaleCanaliFinali.get(c).getTipotr().equals("B") ? "BUY" : "SELL", f4_bold));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setBorder(BOTTOM);
                    table4.addCell(cellt);

                    phraset = new Phrase();
                    phraset.add(new Chunk(totaleCanaliFinali.get(c).getDescription(), f4_bold));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_LEFT);
                    cellt.setBorder(BOTTOM);
                    table4.addCell(cellt);

                    phraset = new Phrase();
                    phraset.add(new Chunk(totaleCanaliFinali.get(c).getNtrans(), f4_bold));
                    totalNTransFINALE += parseIntR(totaleCanaliFinali.get(c).getNtrans());
                    //// System.out.println(nTrans + "
                    //// rc.so.reportcentrale.C_ChangeInternetBookingForBranches.receipt() " +
                    //// totalNTransFINALE);

                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setBorder(BOTTOM);
                    table4.addCell(cellt);

                    phraset = new Phrase();
                    // phraset.add(new
                    // Chunk(Utility.formatMysqltoDisplay(totaleCanaliFinali.get(c).getQty()),
                    // f4_bold));
                    phraset.add(new Chunk("", f4_bold));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setBorder(BOTTOM);
                    table4.addCell(cellt);

                    phraset = new Phrase();
                    phraset.add(new Chunk(
                            formatMysqltoDisplay(
                                    roundDoubleandFormat(fd(totaleCanaliFinali.get(c).getTotalCurrency()), 2)),
                            f4_bold));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setBorder(BOTTOM);
                    table4.addCell(cellt);

                    phraset = new Phrase();
                    phraset.add(
                            new Chunk(
                                    roundDoubleandFormat(
                                            fd(totaleCanaliFinali.get(c).getPercCom()) / totaleCanaliFinali.size(), 2),
                                    f4_bold));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setBorder(BOTTOM);
                    table4.addCell(cellt);

                    phraset = new Phrase();
                    phraset.add(new Chunk(
                            formatMysqltoDisplay(roundDoubleandFormat(fd(totaleCanaliFinali.get(c).getTotalCom()), 2)),
                            f4_bold));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setBorder(BOTTOM);
                    table4.addCell(cellt);

                    phraset = new Phrase();
                    phraset.add(new Chunk(
                            formatMysqltoDisplay(roundDoubleandFormat(fd(totaleCanaliFinali.get(c).getTotalNet()), 2)),
                            f4_bold));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setBorder(BOTTOM);
                    table4.addCell(cellt);

                    phraset = new Phrase();
                    phraset.add(new Chunk(
                            formatMysqltoDisplay(roundDoubleandFormat(fd(totaleCanaliFinali.get(c).getTotalBuy()), 2)),
                            f4_bold));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setBorder(BOTTOM);
                    table4.addCell(cellt);

                    phraset = new Phrase();
                    phraset.add(
                            new Chunk(
                                    formatMysqltoDisplay(
                                            roundDoubleandFormat(fd(totaleCanaliFinali.get(c).getTotalSpread()), 2)),
                                    f4_bold));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setBorder(BOTTOM);
                    table4.addCell(cellt);

                    phraset = new Phrase();
                    phraset.add(
                            new Chunk(
                                    formatMysqltoDisplay(
                                            roundDoubleandFormat(fd(totaleCanaliFinali.get(c).getTotalComFix()), 2)),
                                    f4_bold));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setBorder(BOTTOM);
                    table4.addCell(cellt);

                    phraset = new Phrase();
                    phraset.add(new Chunk("", f4_bold));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setBorder(BOTTOM);
                    table4.addCell(cellt);

                    phraset = new Phrase();
                    phraset.add(new Chunk("", f4_bold));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setBorder(BOTTOM);
                    table4.addCell(cellt);

                    phraset = new Phrase();
                    phraset.add(new Chunk("", f4_bold));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setBorder(BOTTOM);
                    table4.addCell(cellt);

                    document.add(table4);

                    // linea totale
                    sep.setLineWidth(0.7f);
                    document.add(sep);

                }

                // totale canale fine
                totalQty = roundDouble(totalQty, 2);
                totalCurrency = roundDouble(totalCurrency, 2);
                totalPercCom = roundDouble(totalPercCom, 2);
                totalCom = roundDouble(totalCom, 2);
                totalNet = roundDouble(totalNet, 2);
                totalBuy = roundDouble(totalBuy, 2);
                totalSpread = roundDouble(totalSpread, 2);
                totalComFix = roundDouble(totalComFix, 2);

                vuoto.setFont(f3_normal);
                document.add(vuoto);

                if (B_totalNTrans > 0) {
                    table4 = new PdfPTable(colonne.size());
                    table4.setWidths(columnWidths2total);
                    table4.setWidthPercentage(100);

                    // linea totale
                    LineSeparator ls = new LineSeparator();
                    ls.setLineWidth(1f);
                    document.add(ls);

                    phraset = new Phrase();
                    phraset.add(new Chunk("", f4_bold));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setBorder(BOTTOM);
                    table4.addCell(cellt);

                    phraset = new Phrase();
                    phraset.add(new Chunk("", f4_bold));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setBorder(BOTTOM);
                    table4.addCell(cellt);

                    phraset = new Phrase();
                    phraset.add(new Chunk("BUY", f4_bold));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setBorder(BOTTOM);
                    table4.addCell(cellt);

                    phraset = new Phrase();
                    phraset.add(new Chunk("Total", f4_bold));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_LEFT);
                    cellt.setBorder(BOTTOM);
                    table4.addCell(cellt);

                    phraset = new Phrase();
                    phraset.add(new Chunk(B_totalNTrans + "", f4_bold));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setBorder(BOTTOM);
                    table4.addCell(cellt);

                    phraset = new Phrase();
                    phraset.add(new Chunk("", f4_bold));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setBorder(BOTTOM);
                    table4.addCell(cellt);

                    phraset = new Phrase();
                    phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(B_totalCurrency, 2)), f4_bold));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setBorder(BOTTOM);
                    table4.addCell(cellt);

                    phraset = new Phrase();
                    phraset.add(new Chunk(formatMysqltoDisplay("0.00"), f4_bold));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setBorder(BOTTOM);
                    table4.addCell(cellt);

                    phraset = new Phrase();
                    phraset.add(new Chunk(formatMysqltoDisplay("0.00"), f4_bold));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setBorder(BOTTOM);
                    table4.addCell(cellt);

                    phraset = new Phrase();
                    phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(B_totalNet, 2)), f4_bold));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setBorder(BOTTOM);
                    table4.addCell(cellt);

                    phraset = new Phrase();
                    phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(B_totalBuy, 2)), f4_bold));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setBorder(BOTTOM);
                    table4.addCell(cellt);

                    phraset = new Phrase();
                    phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(B_totalSpread, 2)), f4_bold));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setBorder(BOTTOM);
                    table4.addCell(cellt);

                    phraset = new Phrase();
                    phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(B_totalComFix, 2)), f4_bold));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setBorder(BOTTOM);
                    table4.addCell(cellt);

                    phraset = new Phrase();
                    phraset.add(new Chunk("", f4_bold));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setBorder(BOTTOM);
                    table4.addCell(cellt);

                    phraset = new Phrase();
                    phraset.add(new Chunk("", f4_bold));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setBorder(BOTTOM);
                    table4.addCell(cellt);

                    phraset = new Phrase();
                    phraset.add(new Chunk("", f4_bold));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setBorder(BOTTOM);
                    table4.addCell(cellt);

                    document.add(table4);
                }

                if (S_totalNTrans > 0) {
                    table4 = new PdfPTable(colonne.size());
                    table4.setWidths(columnWidths2total);
                    table4.setWidthPercentage(100);

                    // linea totale
                    LineSeparator ls = new LineSeparator();
                    ls.setLineWidth(1f);
                    document.add(ls);

                    phraset = new Phrase();
                    phraset.add(new Chunk("", f4_bold));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setBorder(BOTTOM);
                    table4.addCell(cellt);

                    phraset = new Phrase();
                    phraset.add(new Chunk("", f4_bold));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setBorder(BOTTOM);
                    table4.addCell(cellt);

                    phraset = new Phrase();
                    phraset.add(new Chunk("SELL", f4_bold));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setBorder(BOTTOM);
                    table4.addCell(cellt);

                    phraset = new Phrase();
                    phraset.add(new Chunk("Total", f4_bold));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_LEFT);
                    cellt.setBorder(BOTTOM);
                    table4.addCell(cellt);

                    phraset = new Phrase();
                    phraset.add(new Chunk(S_totalNTrans + "", f4_bold));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setBorder(BOTTOM);
                    table4.addCell(cellt);

                    phraset = new Phrase();
                    phraset.add(new Chunk("", f4_bold));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setBorder(BOTTOM);
                    table4.addCell(cellt);

                    phraset = new Phrase();
                    phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(S_totalCurrency, 2)), f4_bold));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setBorder(BOTTOM);
                    table4.addCell(cellt);

                    phraset = new Phrase();
                    phraset.add(new Chunk(formatMysqltoDisplay("0.00"), f4_bold));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setBorder(BOTTOM);
                    table4.addCell(cellt);

                    phraset = new Phrase();
                    phraset.add(new Chunk(formatMysqltoDisplay("0.00"), f4_bold));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setBorder(BOTTOM);
                    table4.addCell(cellt);

                    phraset = new Phrase();
                    phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(S_totalNet, 2)), f4_bold));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setBorder(BOTTOM);
                    table4.addCell(cellt);

                    phraset = new Phrase();
                    phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(S_totalBuy, 2)), f4_bold));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setBorder(BOTTOM);
                    table4.addCell(cellt);

                    phraset = new Phrase();
                    phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(S_totalSpread, 2)), f4_bold));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setBorder(BOTTOM);
                    table4.addCell(cellt);

                    phraset = new Phrase();
                    phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(S_totalComFix, 2)), f4_bold));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setBorder(BOTTOM);
                    table4.addCell(cellt);

                    phraset = new Phrase();
                    phraset.add(new Chunk("", f4_bold));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setBorder(BOTTOM);
                    table4.addCell(cellt);

                    phraset = new Phrase();
                    phraset.add(new Chunk("", f4_bold));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setBorder(BOTTOM);
                    table4.addCell(cellt);

                    phraset = new Phrase();
                    phraset.add(new Chunk("", f4_bold));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setBorder(BOTTOM);
                    table4.addCell(cellt);

                    document.add(table4);
                }

                // Totale finale generale
                // linea totali
                table4 = new PdfPTable(colonne.size());
                table4.setWidths(columnWidths2total);
                table4.setWidthPercentage(100);

                // linea totale
                LineSeparator ls = new LineSeparator();
                ls.setLineWidth(1f);
                document.add(ls);

                phraset = new Phrase();
                phraset.add(new Chunk("", f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk("", f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk("", f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk("Total", f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_LEFT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(totalNTransFINALE + "", f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);

                phraset = new Phrase();
                // phraset.add(new Chunk(totalQty+"", f4_bold));
                phraset.add(new Chunk("", f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(totalCurrency, 2)), f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(totalPercCom / dati.size(), 2)) + "",
                        f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(totalCom, 2)) + "", f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(totalNet, 2)) + "", f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(totalBuy, 2)) + "", f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(totalSpread, 2)) + "", f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(totalComFix, 2)) + "", f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk("", f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk("", f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk("", f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);

                document.add(table4);
                // linea totale
                ls.setLineWidth(1f);
                document.add(ls);
                //
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
        }
        return document;
    }

    /**
     *
     * @param path
     * @param d3
     * @param d4
     * @param data1
     * @param data2
     * @param alcolonne
     * @param filiali
     * @param allenabledbr
     * @return
     */
    public String main(String path, String d3, String d4, String data1, String data2,
            ArrayList<String> alcolonne, ArrayList<String> filiali,
            ArrayList<Branch> allenabledbr) {
        try {
            C_ChangeInternetBookingForBranches nctl = new C_ChangeInternetBookingForBranches();

            C_ChangeInternetBookingForBranches_value pdf = new C_ChangeInternetBookingForBranches_value();

            boolean firstTime = true;
            boolean lastTime = false;

            File pdffile = new File(path + generaId(50) + "C_ChangeInternetBookingForBranches.pdf");
            try (OutputStream ou = new FileOutputStream(pdffile)) {
                Document document = new Document(A4.rotate(), 20, 20, 20, 20);
                PdfWriter wr = getInstance(document, ou);
                document.open();

                Db_Master db = new Db_Master();
                int lastindex = 0;

                for (int i = 0; i < filiali.size(); i++) {
                    ArrayList<C_ChangeInternetBookingForBranches_value> dati = db
                            .list_C_ChangeInternetBookingForBranches_value(filiali.get(i), data1, data2, allenabledbr);
                    pdf.setDe_filiale(formatBankBranch(filiali.get(i), "BR", null, allenabledbr, null));
                    pdf.setDataDa(d3);
                    pdf.setDataA(d4);
                    pdf.setDati(dati);

                    if (dati.size() > 0) {
                        lastindex = i;
                    }
                }

                // ciclo per ogni filiale
                for (int i = 0; i < filiali.size(); i++) {
                    ArrayList<C_ChangeInternetBookingForBranches_value> dati = db
                            .list_C_ChangeInternetBookingForBranches_value(filiali.get(i), data1, data2, allenabledbr);
                    pdf.setDe_filiale(formatBankBranch(filiali.get(i), "BR", null, allenabledbr, null));
                    pdf.setDataDa(d3);
                    pdf.setDataA(d4);
                    pdf.setDati(dati);

                    if (dati.size() > 0) {

                        if (i == lastindex) {
                            lastTime = true;
                        }
                        if (dati.size() > 0) {
                            document = nctl.receipt(pdf, alcolonne, firstTime, lastTime, document);
                            firstTime = false;
                        }
                    }
                }
                db.closeDB();

                if (!firstTime) {
                    // chiusura documento
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
        } catch (Exception ex) {
            insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
        }
        return null;

    }

    /**
     *
     * @param path
     * @param d3
     * @param d4
     * @param data1
     * @param data2
     * @param alcolonne
     * @param filiali
     * @param allenabledbr
     * @return
     */
    public String mainexcel(String path, String d3, String d4, String data1, String data2,
            ArrayList<String> alcolonne, ArrayList<String> filiali,
            ArrayList<Branch> allenabledbr) {
        try {
            File pdffile = new File(path + generaId(50) + "C_ChangeMovimentDetailForBranches.xls");
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet("C_ChangeMovimentDetailForBranches");

            // CREAZIONE FONT
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

            HSSFDataFormat hssfDataFormat = (HSSFDataFormat) workbook.createDataFormat();
            HSSFCellStyle style4num = (HSSFCellStyle) workbook.createCellStyle();
            style4num.setAlignment(RIGHT);
            style4num.setBorderTop(THIN);
            style4num.setBorderBottom(THIN);
            style4num.setDataFormat(hssfDataFormat.getFormat(formatdataCell));

            HSSFCellStyle cellStylenumint = (HSSFCellStyle) workbook.createCellStyle();
            cellStylenumint.setFont(font3);
            cellStylenumint.setAlignment(RIGHT);
            cellStylenumint.setBorderTop(THIN);
            cellStylenumint.setBorderBottom(THIN);
            cellStylenumint.setDataFormat(hssfDataFormat.getFormat(formatdataCellint));

            HSSFCellStyle style3NN = (HSSFCellStyle) workbook.createCellStyle();
            style3NN.setFont(font3);
            style3NN.setAlignment(RIGHT);
            style3NN.setBorderTop(THIN);
            style3NN.setBorderBottom(THIN);
            style3NN.setDataFormat(hssfDataFormat.getFormat(formatdataCell));

            HSSFCellStyle style4left = (HSSFCellStyle) workbook.createCellStyle();
            style4left.setAlignment(LEFT);
            style4left.setBorderTop(THIN);
            style4left.setBorderBottom(THIN);

            C_ChangeInternetBookingForBranches nctl = new C_ChangeInternetBookingForBranches();
            C_ChangeInternetBookingForBranches_value pdf = new C_ChangeInternetBookingForBranches_value();

            boolean firstTime = true;
            boolean lastTime = false;

            Db_Master db = new Db_Master();
            int lastindex = 0;

            for (int i = 0; i < filiali.size(); i++) {
                ArrayList<C_ChangeInternetBookingForBranches_value> dati = db
                        .list_C_ChangeInternetBookingForBranches_value(filiali.get(i), data1, data2, allenabledbr);
                pdf.setDe_filiale(formatBankBranch(filiali.get(i), "BR", null, allenabledbr, null));
                pdf.setDataDa(d3);
                pdf.setDataA(d4);
                pdf.setDati(dati);

                if (dati.size() > 0) {
                    lastindex = i;
                }
            }

            // ciclo per ogni filiale
            int nriga = 3;
            for (int i = 0; i < filiali.size(); i++) {
                ArrayList<C_ChangeInternetBookingForBranches_value> dati = db
                        .list_C_ChangeInternetBookingForBranches_value(filiali.get(i), data1, data2, allenabledbr);
                pdf.setDe_filiale(formatBankBranch(filiali.get(i), "BR", null, allenabledbr, null));
                pdf.setDataDa(d3);
                pdf.setDataA(d4);
                pdf.setDati(dati);

                if (dati.size() > 0) {

                    if (i == lastindex) {
                        lastTime = true;
                    }
                    if (dati.size() > 0) {
                        nriga = nctl.receiptexcel(pdf, alcolonne, firstTime, lastTime, sheet, nriga, style1, style2,
                                style3, style4, style3left, style4left, style4num, cellStylenumint, style3NN);
                        firstTime = false;
                    }
                }
            }

            if (!firstTime) {

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

    /**
     *
     * @param cmfb
     * @param colonne
     * @param firstTime
     * @param lastTime
     * @param sheet
     * @param cntriga
     * @param style1
     * @param style2
     * @param style3
     * @param style4
     * @param style3left
     * @param style4left
     * @param style4num
     * @param cellStylenumint
     * @param style3NN
     * @return
     */
    public int receiptexcel(C_ChangeInternetBookingForBranches_value cmfb, ArrayList<String> colonne, boolean firstTime,
            boolean lastTime,
            HSSFSheet sheet, int cntriga, HSSFCellStyle style1, HSSFCellStyle style2, HSSFCellStyle style3,
            HSSFCellStyle style4,
            HSSFCellStyle style3left, HSSFCellStyle style4left, HSSFCellStyle style4num, HSSFCellStyle cellStylenumint,
            HSSFCellStyle style3NN) {

        try {

            if (firstTime) {
                Row rowP = sheet.createRow((short) cntriga);
                Cell cl = rowP.createCell(1);
                cl.setCellStyle(style1);
                cl.setCellValue(intestazionePdf + " From " + cmfb.getDataDa() + " to " + cmfb.getDataA());
                cntriga++;
                cntriga++;
                cntriga++;
            }

            ArrayList<C_ChangeInternetBookingForBranches_value> dati = cmfb.getDati();

            ArrayList<Canale> canali = new ArrayList<>();

            Row row66c = sheet.createRow((short) cntriga);
            cntriga++;
            cntriga++;
            Cell cl0 = row66c.createCell(1);
            cl0.setCellStyle(style3left);
            cl0.setCellValue("\n " + cmfb.getDe_filiale());

            Row row66 = sheet.createRow((short) cntriga);
            // mi scandisco le colonne
            for (int c_1 = 0; c_1 < colonne.size(); c_1++) {
                Cell cl7 = row66.createCell(c_1 + 1);
                cl7.setCellStyle(style3);
                if (c_1 == 0 || c_1 == 1 || c_1 == 3 || c_1 == 4 || c_1 == 13 || c_1 == 14 || c_1 == 15) {
                    cl7.setCellStyle(style3left);
                }
                cl7.setCellValue(colonne.get(c_1));
            }

            // ArrayList<C_ChangeInternetBookingForBranches_value> lista = new
            // ArrayList<C_ChangeInternetBookingForBranches_value>(dati);
            ArrayList<C_ChangeInternetBookingForBranches_value> lista = cmfb.getDati();

            // lista totali per canali
            for (int x = 0; x < lista.size(); x++) {

                C_ChangeInternetBookingForBranches_value ogg = lista.get(x);
                String canale = ogg.getDescription();
                String tipotr = ogg.getTipotr();

                // scorro la lista per vedere se è presente la descrizione in un altro oggetto
                Canale cccc = presenteDescrizione(canale, canali, tipotr);
                if (cccc != null) {
                    cccc.setNtrans((parseInt(cccc.getNtrans()) + 1) + "");
                    cccc.setQty(roundDoubleandFormat(fd(ogg.getQty()) + fd(cccc.getQty()), 2));
                    cccc.setTotalCurrency(
                            roundDoubleandFormat(fd(ogg.getTotalCurrency()) + fd(cccc.getTotalCurrency()), 2));
                    cccc.setPercCom(roundDoubleandFormat(fd(ogg.getPercCom()) + fd(cccc.getPercCom()), 2));
                    cccc.setTotalCom(roundDoubleandFormat(fd(ogg.getTotalCom()) + fd(cccc.getTotalCom()), 2));
                    cccc.setTotalNet(roundDoubleandFormat(fd(ogg.getTotalNet()) + fd(cccc.getTotalNet()), 2));
                    cccc.setTotalBuy(roundDoubleandFormat(fd(ogg.getTotalBuy()) + fd(cccc.getTotalBuy()), 2));
                    cccc.setTotalSpread(roundDoubleandFormat(fd(ogg.getTotalSpread()) + fd(cccc.getTotalSpread()), 2));
                    cccc.setTotalComFix(roundDoubleandFormat(fd(ogg.getTotalComFix()) + fd(cccc.getTotalComFix()), 2));
                } else {
                    Canale c_1 = new Canale();
                    c_1.setDescription(canale);
                    c_1.setNtrans("1");
                    c_1.setQty(ogg.getQty());
                    c_1.setTotalCurrency(ogg.getTotalCurrency());
                    c_1.setPercCom(ogg.getPercCom());
                    c_1.setTotalCom(ogg.getTotalCom());
                    c_1.setTotalNet(ogg.getTotalNet());
                    c_1.setTotalBuy(ogg.getTotalBuy());
                    c_1.setTotalSpread(ogg.getTotalSpread());
                    c_1.setTotalComFix(ogg.getTotalComFix());
                    c_1.setTipotr(ogg.getTipotr());
                    canali.add(c_1);
                }

            }

            for (int j = 0; j < dati.size(); j++) {

                C_ChangeInternetBookingForBranches_value temp = dati.get(j);

                cntriga++;

                Row row6 = sheet.createRow((short) cntriga);

                Cell f1bis = row6.createCell(1);
                f1bis.setCellStyle(style4left);
                f1bis.setCellValue(temp.getTransaction());

                Cell f2 = row6.createCell(2);
                f2.setCellStyle(style4left);
                f2.setCellValue(temp.getDate());

                Cell f3 = row6.createCell(3);
                f3.setCellStyle(style4);
                f3.setCellValue(temp.getUser());

                Cell f4 = row6.createCell(4);
                f4.setCellStyle(style4left);
                f4.setCellValue(temp.getCurrency());

                Cell f5 = row6.createCell(5);
                f5.setCellStyle(style4left);
                f5.setCellValue(temp.getSupport());

                Cell f6 = row6.createCell(6, NUMERIC);
                f6.setCellStyle(style4num);
                f6.setCellValue(fd(temp.getQty()));

                Cell f7 = row6.createCell(7, NUMERIC);
                f7.setCellStyle(style4num);
                f7.setCellValue(fd(temp.getTotalCurrency()));

                Cell f8 = row6.createCell(8, NUMERIC);
                f8.setCellStyle(style4num);
                f8.setCellValue(fd(temp.getPercCom()));

                Cell f9 = row6.createCell(9, NUMERIC);
                f9.setCellStyle(style4num);
                f9.setCellValue(fd(temp.getTotalCom()));

                Cell f10 = row6.createCell(10, NUMERIC);
                f10.setCellStyle(style4num);
                f10.setCellValue(fd(temp.getTotalNet()));

                Cell f11 = row6.createCell(11, NUMERIC);
                f11.setCellStyle(style4num);
                f11.setCellValue(fd(temp.getTotalBuy()));

                Cell f12 = row6.createCell(12, NUMERIC);
                f12.setCellStyle(style4num);
                f12.setCellValue(fd(temp.getTotalSpread()));

                Cell f13 = row6.createCell(13, NUMERIC);
                f13.setCellStyle(style4num);
                f13.setCellValue(fd(temp.getTotalComFix()));

                Cell f14 = row6.createCell(14);
                f14.setCellStyle(style4left);
                f14.setCellValue(temp.getKindClient());

                Cell f15 = row6.createCell(15);
                f15.setCellStyle(style4left);
                f15.setCellValue(temp.getCode());

                Cell f16 = row6.createCell(16);
                f16.setCellStyle(style4left);
                f16.setCellValue(temp.getDescription());

            }

            int nTrans = 0;
            double totQty = 0;
            double totCurrency = 0;
            double percCom = 0;
            double totCom = 0;
            double totNet = 0;
            double totBuy = 0;
            double totSpread = 0;
            double totComFix = 0;

            int B_nTrans = 0;
            double B_totQty = 0;
            double B_totCurrency = 0;
            double B_percCom = 0;
            double B_totCom = 0;
            double B_totNet = 0;
            double B_totBuy = 0;
            double B_totSpread = 0;
            double B_totComFix = 0;

            int S_nTrans = 0;
            double S_totQty = 0;
            double S_totCurrency = 0;
            double S_percCom = 0;
            double S_totCom = 0;
            double S_totNet = 0;
            double S_totBuy = 0;
            double S_totSpread = 0;
            double S_totComFix = 0;

            cntriga++;

            // totale canale
            for (int c = 0; c < canali.size(); c++) {

                cntriga++;

                Row row7 = sheet.createRow((short) cntriga);

                Cell f6 = row7.createCell(4);
                f6.setCellStyle(style3left);
                f6.setCellValue((canali.get(c).getTipotr().equals("B") ? "BUY" : "SELL") + " "
                        + canali.get(c).getDescription());

                Cell f8 = row7.createCell(5, NUMERIC);
                f8.setCellStyle(cellStylenumint);
                f8.setCellValue(parseIntR(canali.get(c).getNtrans()));

                Cell f87 = row7.createCell(7, NUMERIC);
                f87.setCellStyle(style3NN);
                f87.setCellValue(fd(canali.get(c).getTotalCurrency()));

                Cell f10 = row7.createCell(8, NUMERIC);
                double d = fd(canali.get(c).getPercCom()) / fd(canali.get(c).getNtrans());
                f10.setCellStyle(style3NN);
                f10.setCellValue(fd(roundDoubleandFormat(d, 2)));

                Cell f11 = row7.createCell(9, NUMERIC);
                f11.setCellStyle(style3NN);

                f11.setCellValue(fd(canali.get(c).getTotalCom()));

                Cell f12 = row7.createCell(10, NUMERIC);
                f12.setCellStyle(style3NN);

                f12.setCellValue(fd(canali.get(c).getTotalNet()));

                Cell f13 = row7.createCell(11, NUMERIC);
                f13.setCellStyle(style3NN);

                f13.setCellValue(fd(canali.get(c).getTotalBuy()));

                Cell f14 = row7.createCell(12, NUMERIC);
                f14.setCellStyle(style3NN);
                f14.setCellValue(fd(canali.get(c).getTotalSpread()));

                Cell f15 = row7.createCell(13, NUMERIC);
                f15.setCellStyle(style3NN);

                f15.setCellValue(fd(canali.get(c).getTotalComFix()));

                nTrans += parseIntR(canali.get(c).getNtrans());
                totQty += fd(canali.get(c).getQty());
                totCurrency += fd(canali.get(c).getTotalCurrency());
                percCom += fd(canali.get(c).getPercCom()) / fd(canali.get(c).getNtrans());
                totCom += fd(canali.get(c).getTotalCom());
                totNet += fd(canali.get(c).getTotalNet());
                totBuy += fd(canali.get(c).getTotalBuy());
                totSpread += fd(canali.get(c).getTotalSpread());
                totComFix += fd(canali.get(c).getTotalComFix());

                if (canali.get(c).getTipotr().startsWith("B")) {
                    B_nTrans += parseIntR(canali.get(c).getNtrans());
                    B_totQty += fd(canali.get(c).getQty());
                    B_totCurrency += fd(canali.get(c).getTotalCurrency());
                    B_totCom += fd(canali.get(c).getTotalCom());
                    B_totNet += fd(canali.get(c).getTotalNet());
                    B_totBuy += fd(canali.get(c).getTotalBuy());
                    B_totSpread += fd(canali.get(c).getTotalSpread());
                    B_totComFix += fd(canali.get(c).getTotalComFix());
                } else {
                    S_nTrans += parseIntR(canali.get(c).getNtrans());
                    S_totQty += fd(canali.get(c).getQty());
                    S_totCurrency += fd(canali.get(c).getTotalCurrency());
                    S_totCom += fd(canali.get(c).getTotalCom());
                    S_totNet += fd(canali.get(c).getTotalNet());
                    S_totBuy += fd(canali.get(c).getTotalBuy());
                    S_totSpread += fd(canali.get(c).getTotalSpread());
                    S_totComFix += fd(canali.get(c).getTotalComFix());
                }

                totaliCanali.add(canali.get(c));

            }

            totQty = roundDouble(totQty, 2);
            totCurrency = roundDouble(totCurrency, 2);
            percCom = roundDouble(percCom, 2);
            totCom = roundDouble(totCom, 2);
            totNet = roundDouble(totNet, 2);
            totBuy = roundDouble(totBuy, 2);
            totSpread = roundDouble(totSpread, 2);
            totComFix = roundDouble(totComFix, 2);

            // totale canale fine
            cntriga++;

            if (B_nTrans > 0) {
                Row row7 = sheet.createRow((short) cntriga);

                Cell f6 = row7.createCell(4);
                f6.setCellStyle(style3left);
                f6.setCellValue("BUY TOTAL");

                Cell f8 = row7.createCell(5, NUMERIC);
                f8.setCellStyle(cellStylenumint);
                f8.setCellValue(B_nTrans);

                Cell f87 = row7.createCell(7, NUMERIC);
                f87.setCellStyle(style3NN);
                f87.setCellValue(fd(roundDoubleandFormat(B_totCurrency, 2)));

                Cell f10 = row7.createCell(8, NUMERIC);
                f10.setCellStyle(style3NN);
                f10.setCellValue(fd(roundDoubleandFormat(B_percCom / B_nTrans, 2)));

                Cell f11 = row7.createCell(9, NUMERIC);
                f11.setCellStyle(style3NN);
                f11.setCellValue(fd(roundDoubleandFormat(B_totCom, 2)));

                Cell f12 = row7.createCell(10, NUMERIC);
                f12.setCellStyle(style3NN);
                f12.setCellValue(fd(roundDoubleandFormat(B_totNet, 2)));

                Cell f13 = row7.createCell(11, NUMERIC);
                f13.setCellStyle(style3NN);
                f13.setCellValue(fd(roundDoubleandFormat(B_totBuy, 2)));

                Cell f14 = row7.createCell(12, NUMERIC);
                f14.setCellStyle(style3NN);
                f14.setCellValue(fd(roundDoubleandFormat(B_totSpread, 2)));

                Cell f15 = row7.createCell(13, NUMERIC);
                f15.setCellStyle(style3NN);
                f15.setCellValue(fd(roundDoubleandFormat(B_totComFix, 2)));
                cntriga++;

            }

            if (B_nTrans > 0) {
                Row row7 = sheet.createRow((short) cntriga);

                Cell f6 = row7.createCell(4);
                f6.setCellStyle(style3left);
                f6.setCellValue("SELL TOTAL");

                Cell f8 = row7.createCell(5, NUMERIC);
                f8.setCellStyle(cellStylenumint);
                f8.setCellValue(S_nTrans);

                Cell f87 = row7.createCell(7, NUMERIC);
                f87.setCellStyle(style3NN);
                f87.setCellValue(fd(roundDoubleandFormat(S_totCurrency, 2)));

                Cell f10 = row7.createCell(8, NUMERIC);
                f10.setCellStyle(style3NN);
                double S_totalPercCom = 0.0;
                if (S_nTrans != 0) {
                    S_totalPercCom = (S_percCom / S_nTrans) * 100.00;
                }
                f10.setCellValue(fd(roundDoubleandFormat(S_totalPercCom, 2)));

                Cell f11 = row7.createCell(9, NUMERIC);
                f11.setCellStyle(style3NN);
                f11.setCellValue(fd(roundDoubleandFormat(S_totCom, 2)));

                Cell f12 = row7.createCell(10, NUMERIC);
                f12.setCellStyle(style3NN);
                f12.setCellValue(fd(roundDoubleandFormat(S_totNet, 2)));

                Cell f13 = row7.createCell(11, NUMERIC);
                f13.setCellStyle(style3NN);
                f13.setCellValue(fd(roundDoubleandFormat(S_totBuy, 2)));

                Cell f14 = row7.createCell(12, NUMERIC);
                f14.setCellStyle(style3NN);
                f14.setCellValue(fd(roundDoubleandFormat(S_totSpread, 2)));

                Cell f15 = row7.createCell(13, NUMERIC);
                f15.setCellStyle(style3NN);
                f15.setCellValue(fd(roundDoubleandFormat(S_totComFix, 2)));
                cntriga++;

            }

            Row row7 = sheet.createRow((short) cntriga);

            Cell f6 = row7.createCell(4);
            f6.setCellStyle(style3left);
            f6.setCellValue("TOTAL");

            Cell f8 = row7.createCell(5, NUMERIC);
            f8.setCellStyle(cellStylenumint);
            f8.setCellValue(nTrans);

            Cell f87 = row7.createCell(7, NUMERIC);
            f87.setCellStyle(style3NN);
            f87.setCellValue(fd(roundDoubleandFormat(totCurrency, 2)));

            Cell f10 = row7.createCell(8, NUMERIC);
            f10.setCellStyle(style3NN);
            double S_totalPercCom = 0.0;
            if (nTrans != 0) {
                S_totalPercCom = (percCom / nTrans) * 100.00;
            }
            f10.setCellValue(fd(roundDoubleandFormat(S_totalPercCom, 2)));

            Cell f11 = row7.createCell(9, NUMERIC);
            f11.setCellStyle(style3NN);
            f11.setCellValue(fd(roundDoubleandFormat(totCom, 2)));

            Cell f12 = row7.createCell(10, NUMERIC);
            f12.setCellStyle(style3NN);
            f12.setCellValue(fd(roundDoubleandFormat(totNet, 2)));

            Cell f13 = row7.createCell(11, NUMERIC);
            f13.setCellStyle(style3NN);
            f13.setCellValue(fd(roundDoubleandFormat(totBuy, 2)));

            Cell f14 = row7.createCell(12, NUMERIC);
            f14.setCellStyle(style3NN);
            f14.setCellValue(fd(roundDoubleandFormat(totSpread, 2)));

            Cell f15 = row7.createCell(13, NUMERIC);
            f15.setCellStyle(style3NN);
            f15.setCellValue(fd(roundDoubleandFormat(totComFix, 2)));

            totalNTrans += nTrans;
            totalQty += totQty;
            totalCurrency += totCurrency;
            totalPercCom += percCom / nTrans;
            totalCom += totCom;
            totalNet += totNet;
            totalBuy += totBuy;
            totalSpread += totSpread;
            totalComFix += totComFix;

            B_totalNTrans += B_nTrans;
            B_totalQty += B_totQty;
            B_totalCurrency += B_totCurrency;
            B_totalCom += B_totCom;
            B_totalNet += B_totNet;
            B_totalBuy += B_totBuy;
            B_totalSpread += B_totSpread;
            B_totalComFix += B_totComFix;

            S_totalNTrans += S_nTrans;
            S_totalQty += S_totQty;
            S_totalCurrency += S_totCurrency;
            S_totalCom += S_totCom;
            S_totalNet += S_totNet;
            S_totalBuy += S_totBuy;
            S_totalSpread += S_totSpread;
            S_totalComFix += S_totComFix;

            if (lastTime) {

                // sistemare ArrayList<String> totali per canali
                ArrayList<Canale> totaleCanaliFinali = new ArrayList<>();

                for (int x = 0; x < totaliCanali.size(); x++) {

                    Canale ogg = totaliCanali.get(x);
                    String canale = ogg.getDescription();
                    String tipotr = ogg.getTipotr();

                    // scorro la lista per vedere se è presente la descrizione in un altro oggetto
                    Canale cccc = presenteDescrizione(canale, totaleCanaliFinali, tipotr);
                    if (cccc != null) {
                        // da modificare NTrans
                        cccc.setNtrans(valueOf(parseIntR(cccc.getNtrans()) + parseIntR(ogg.getNtrans())));
                        cccc.setQty(roundDoubleandFormat(fd(ogg.getQty()) + fd(cccc.getQty()), 2));
                        cccc.setTotalCurrency(
                                roundDoubleandFormat(fd(ogg.getTotalCurrency()) + fd(cccc.getTotalCurrency()), 2));

                        cccc.setPercCom(roundDoubleandFormat(fd(ogg.getPercCom()) + fd(cccc.getPercCom()), 2));
                        cccc.setTotalCom(roundDoubleandFormat(fd(ogg.getTotalCom()) + fd(cccc.getTotalCom()), 2));
                        cccc.setTotalNet(roundDoubleandFormat(fd(ogg.getTotalNet()) + fd(cccc.getTotalNet()), 2));
                        cccc.setTotalBuy(roundDoubleandFormat(fd(ogg.getTotalBuy()) + fd(cccc.getTotalBuy()), 2));
                        cccc.setTotalSpread(
                                roundDoubleandFormat(fd(ogg.getTotalSpread()) + fd(cccc.getTotalSpread()), 2));
                        cccc.setTotalComFix(
                                roundDoubleandFormat(fd(ogg.getTotalComFix()) + fd(cccc.getTotalComFix()), 2));

                    } else {
                        Canale c = new Canale();
                        c.setDescription(canale);
                        c.setNtrans(ogg.getNtrans());
                        c.setQty(ogg.getQty());
                        c.setTotalCurrency(ogg.getTotalCurrency());
                        c.setPercCom(ogg.getPercCom());
                        c.setTotalCom(ogg.getTotalCom());
                        c.setTotalNet(ogg.getTotalNet());
                        c.setTotalBuy(ogg.getTotalBuy());
                        c.setTotalSpread(ogg.getTotalSpread());
                        c.setTotalComFix(ogg.getTotalComFix());
                        c.setTipotr(ogg.getTipotr());
                        totaleCanaliFinali.add(c);
                    }

                }

                // fine ArrayList<String> totali
                cntriga++;
                cntriga++;
                cntriga++;
                cntriga++;

                cntriga++;

                Row row8 = sheet.createRow((short) cntriga);

                Cell c8 = row8.createCell(1);
                c8.setCellStyle(style3left);
                c8.setCellValue("Total for selected branches");

                cntriga++;

                // totale canale
                for (int c = 0; c < totaleCanaliFinali.size(); c++) {

                    cntriga++;

                    Row row9 = sheet.createRow((short) cntriga);

                    Cell r9 = row9.createCell(4);
                    r9.setCellStyle(style3left);
                    r9.setCellValue((totaleCanaliFinali.get(c).getTipotr().equals("B") ? "BUY" : "SELL") + " "
                            + totaleCanaliFinali.get(c).getDescription());

                    Cell r95 = row9.createCell(5, NUMERIC);
                    r95.setCellStyle(cellStylenumint);
                    r95.setCellValue(parseIntR(totaleCanaliFinali.get(c).getNtrans()));
                    totalNTransFINALE += parseIntR(totaleCanaliFinali.get(c).getNtrans());
                    Cell r97 = row9.createCell(7, NUMERIC);
                    r97.setCellStyle(style3NN);
                    r97.setCellValue(fd(totaleCanaliFinali.get(c).getTotalCurrency()));

                    Cell r98 = row9.createCell(8, NUMERIC);
                    r98.setCellStyle(style3NN);
                    r98.setCellValue(roundDoubleandFormat(
                            fd(totaleCanaliFinali.get(c).getPercCom()) / fd(valueOf(totaleCanaliFinali.size())), 2));

                    Cell r99 = row9.createCell(9, NUMERIC);
                    r99.setCellStyle(style3NN);
                    r99.setCellValue(fd(totaleCanaliFinali.get(c).getTotalCom()));

                    Cell r910 = row9.createCell(10, NUMERIC);
                    r910.setCellStyle(style3NN);
                    r910.setCellValue(fd(totaleCanaliFinali.get(c).getTotalNet()));

                    Cell r911 = row9.createCell(11, NUMERIC);
                    r911.setCellStyle(style3NN);
                    r911.setCellValue(fd(totaleCanaliFinali.get(c).getTotalBuy()));

                    Cell r912 = row9.createCell(12, NUMERIC);
                    r912.setCellStyle(style3NN);
                    r912.setCellValue(fd(totaleCanaliFinali.get(c).getTotalSpread()));

                    Cell r913 = row9.createCell(13, NUMERIC);
                    r913.setCellStyle(style3NN);
                    r913.setCellValue(fd(totaleCanaliFinali.get(c).getTotalComFix()));
                }

                // totale canale fine
                totalQty = roundDouble(totalQty, 2);
                totalCurrency = roundDouble(totalCurrency, 2);
                totalPercCom = roundDouble(totalPercCom, 2);
                totalCom = roundDouble(totalCom, 2);
                totalNet = roundDouble(totalNet, 2);
                totalBuy = roundDouble(totalBuy, 2);
                totalSpread = roundDouble(totalSpread, 2);
                totalComFix = roundDouble(totalComFix, 2);

                if (B_totalNTrans > 0) {
                    cntriga++;

                    Row row10 = sheet.createRow((short) cntriga);

                    Cell r10 = row10.createCell(4);
                    r10.setCellStyle(style3left);
                    r10.setCellValue("BUY TOTAL");

                    // Totale finale generale
                    // linea totali
                    Cell r15 = row10.createCell(5, NUMERIC);
                    r15.setCellStyle(cellStylenumint);
                    r15.setCellValue(B_totalNTrans);

                    Cell r17 = row10.createCell(7, NUMERIC);
                    r17.setCellStyle(style3NN);
                    r17.setCellValue(fd(roundDoubleandFormat(B_totalCurrency, 2)));

                    Cell r18 = row10.createCell(8, NUMERIC);
                    r18.setCellStyle(style3NN);
                    r18.setCellValue(fd(roundDoubleandFormat(B_totalPercCom, 2)));

                    Cell r19 = row10.createCell(9, NUMERIC);
                    r19.setCellStyle(style3NN);
                    r19.setCellValue(fd(roundDoubleandFormat(B_totalCom, 2)));

                    Cell r110 = row10.createCell(10, NUMERIC);
                    r110.setCellStyle(style3NN);
                    r110.setCellValue(fd(roundDoubleandFormat(B_totalNet, 2)));

                    Cell r111 = row10.createCell(11, NUMERIC);
                    r111.setCellStyle(style3NN);
                    r111.setCellValue(fd(roundDoubleandFormat(B_totalBuy, 2)));

                    Cell r112 = row10.createCell(12, NUMERIC);
                    r112.setCellStyle(style3NN);
                    r112.setCellValue(fd(roundDoubleandFormat(B_totalSpread, 2)));

                    Cell r113 = row10.createCell(13, NUMERIC);
                    r113.setCellStyle(style3NN);
                    r113.setCellValue(fd(roundDoubleandFormat(B_totalComFix, 2)));
                }

                if (S_totalNTrans > 0) {
                    cntriga++;

                    Row row10 = sheet.createRow((short) cntriga);

                    Cell r10 = row10.createCell(4);
                    r10.setCellStyle(style3left);
                    r10.setCellValue("SELL TOTAL");

                    // Totale finale generale
                    // linea totali
                    Cell r15 = row10.createCell(5, NUMERIC);
                    r15.setCellStyle(cellStylenumint);
                    r15.setCellValue(S_totalNTrans);

                    Cell r17 = row10.createCell(7, NUMERIC);
                    r17.setCellStyle(style3NN);
                    r17.setCellValue(fd(roundDoubleandFormat(S_totalCurrency, 2)));

                    Cell r18 = row10.createCell(8, NUMERIC);
                    r18.setCellStyle(style3NN);
                    r18.setCellValue(roundDoubleandFormat(S_totalPercCom, 2));

                    Cell r19 = row10.createCell(9, NUMERIC);
                    r19.setCellStyle(style3NN);
                    r19.setCellValue(fd(roundDoubleandFormat(S_totalCom, 2)));

                    Cell r110 = row10.createCell(10, NUMERIC);
                    r110.setCellStyle(style3NN);
                    r110.setCellValue(fd(roundDoubleandFormat(S_totalNet, 2)));

                    Cell r111 = row10.createCell(11, NUMERIC);
                    r111.setCellStyle(style3NN);
                    r111.setCellValue(fd(roundDoubleandFormat(S_totalBuy, 2)));

                    Cell r112 = row10.createCell(12, NUMERIC);
                    r112.setCellStyle(style3NN);
                    r112.setCellValue(fd(roundDoubleandFormat(S_totalSpread, 2)));

                    Cell r113 = row10.createCell(13, NUMERIC);
                    r113.setCellStyle(style3NN);
                    r113.setCellValue(fd(roundDoubleandFormat(S_totalComFix, 2)));
                }

                cntriga++;

                Row row10 = sheet.createRow((short) cntriga);

                Cell r10 = row10.createCell(4);
                r10.setCellStyle(style3left);
                r10.setCellValue("Total");

                // Totale finale generale
                // linea totali
                Cell r15 = row10.createCell(5, NUMERIC);
                r15.setCellStyle(cellStylenumint);
                r15.setCellValue(totalNTransFINALE);

                Cell r17 = row10.createCell(7, NUMERIC);
                r17.setCellStyle(style3NN);
                r17.setCellValue(fd(roundDoubleandFormat(totalCurrency, 2)));

                Cell r18 = row10.createCell(8, NUMERIC);
                r18.setCellStyle(style3NN);
                r18.setCellValue(fd(roundDoubleandFormat(totalPercCom / dati.size(), 2)));

                Cell r19 = row10.createCell(9, NUMERIC);
                r19.setCellStyle(style3NN);
                r19.setCellValue(fd(roundDoubleandFormat(totalCom, 2)));

                Cell r110 = row10.createCell(10, NUMERIC);
                r110.setCellStyle(style3NN);
                r110.setCellValue(fd(roundDoubleandFormat(totalNet, 2)));

                Cell r111 = row10.createCell(11, NUMERIC);
                r111.setCellStyle(style3NN);
                r111.setCellValue(fd(roundDoubleandFormat(totalBuy, 2)));

                Cell r112 = row10.createCell(12, NUMERIC);
                r112.setCellStyle(style3NN);
                r112.setCellValue(fd(roundDoubleandFormat(totalSpread, 2)));

                Cell r113 = row10.createCell(13, NUMERIC);
                r113.setCellStyle(style3NN);
                r113.setCellValue(fd(roundDoubleandFormat(totalComFix, 2)));

                //
            }

        } catch (Exception ex) {
            insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
        }

        cntriga++;
        cntriga++;
        cntriga++;
        cntriga++;

        return cntriga;
    }

    /**
     *
     * @param kind
     * @param dati
     * @return
     */
    public ArrayList<C_ChangeInternetBookingForBranches_value> getListKind(String kind,
            ArrayList<C_ChangeInternetBookingForBranches_value> dati) {
        ArrayList<C_ChangeInternetBookingForBranches_value> list = new ArrayList<>();

        for (int i = 0; i < dati.size(); i++) {
            C_ChangeInternetBookingForBranches_value temp = dati.get(i);
            if (temp.getKindClient().equals(kind)) {
                list.add(dati.get(i));
            }
        }

        return list;
    }

    private class Canale implements Comparable {

        String ntrans, qty, totalCurrency, percCom, totalCom, totalNet, totalBuy, totalSpread, totalComFix, description,
                tipotr;

        public String getTipotr() {
            return tipotr;
        }

        public void setTipotr(String tipotr) {
            this.tipotr = tipotr;
        }

        public String getNtrans() {
            return ntrans;
        }

        public void setNtrans(String ntrans) {
            this.ntrans = ntrans;
        }

        public String getQty() {
            return qty;
        }

        public void setQty(String qty) {
            this.qty = qty;
        }

        public String getTotalCurrency() {
            return totalCurrency;
        }

        public void setTotalCurrency(String totalCurrency) {
            this.totalCurrency = totalCurrency;
        }

        public String getPercCom() {
            return percCom;
        }

        public void setPercCom(String percCom) {
            this.percCom = percCom;
        }

        public String getTotalCom() {
            return totalCom;
        }

        public void setTotalCom(String totalCom) {
            this.totalCom = totalCom;
        }

        public String getTotalNet() {
            return totalNet;
        }

        public void setTotalNet(String totalNet) {
            this.totalNet = totalNet;
        }

        public String getTotalBuy() {
            return totalBuy;
        }

        public void setTotalBuy(String totalBuy) {
            this.totalBuy = totalBuy;
        }

        public String getTotalSpread() {
            return totalSpread;
        }

        public void setTotalSpread(String totalSpread) {
            this.totalSpread = totalSpread;
        }

        public String getTotalComFix() {
            return totalComFix;
        }

        public void setTotalComFix(String totalComFix) {
            this.totalComFix = totalComFix;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        @Override
        public int compareTo(Object o) {
            Canale temp = (Canale) o;

            return this.getDescription().compareToIgnoreCase(temp.getDescription());
        }

    }

    /**
     *
     * @param descrizione
     * @param lista
     * @param tipotr
     * @return
     */
    public Canale presenteDescrizione(String descrizione, ArrayList<Canale> lista, String tipotr) {
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getDescription().equals(descrizione)
                    && lista.get(i).getTipotr().equals(tipotr)) {
                return lista.get(i);
            }
        }
        return null;
    }

}
