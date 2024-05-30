/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rc.so.reportcentrale;

import static com.google.common.collect.Iterators.size;
import static com.itextpdf.text.BaseColor.LIGHT_GRAY;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
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
import static rc.so.util.Engine.formatBankBranch;
import static rc.so.util.Engine.insertTR;
import static rc.so.util.Utility.fd;
import static rc.so.util.Utility.formatMysqltoDisplay;
import static rc.so.util.Utility.generaId;
import static rc.so.util.Utility.roundDouble;
import static rc.so.util.Utility.roundDoubleandFormat;
import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import static java.lang.Integer.parseInt;
import static java.lang.String.valueOf;
import static java.lang.Thread.currentThread;
import java.util.ArrayList;
import java.util.Iterator;
import static org.apache.commons.codec.binary.Base64.encodeBase64;
import static org.apache.commons.io.FileUtils.readFileToByteArray;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFFont;
import static org.apache.poi.hssf.usermodel.HSSFFont.FONT_ARIAL;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFPicture;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.Row;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import static org.apache.poi.ss.usermodel.BorderStyle.THIN;
import static org.apache.poi.ss.usermodel.CellType.NUMERIC;
import static org.apache.poi.ss.usermodel.CellType.STRING;
import static org.apache.poi.ss.usermodel.HorizontalAlignment.LEFT;
import static org.apache.poi.ss.usermodel.HorizontalAlignment.RIGHT;
import static org.apache.poi.ss.usermodel.Workbook.PICTURE_TYPE_PNG;
import static org.jfree.chart.ChartFactory.createBarChart;
import static org.jfree.chart.ChartUtils.writeChartAsPNG;
import static org.jfree.chart.plot.PlotOrientation.VERTICAL;
/**
 *
 * @author fplacanica
 */
public class C_CashierPerformance {

    //column

    /**
     *
     */
    public static final float[] columnWidths0 = new float[]{70f, 30f};

    /**
     *
     */
    public static final float[] columnWidths0bis = new float[]{100f, 0f};

    /**
     *
     */
    public static final float[] columnWidths1 = new float[]{60f, 30f};

    /**
     *
     */
    public static float[] columnWidths2 = null;

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
    final String intestazionePdf = "Cashier Performance";
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

    Font f0_bold, f1_bold, f2_bold, f1_normal, f2_normal, f3_normal, f3_bold, f4_bold;

    float tot_qta = 0, tot_amount = 0, tot_comm = 0, tot_net = 0, tot_buy = 0, tot_spread = 0, tot_commFix = 0;

    ArrayList<C_CashierPerformance_value> listaTot;

    ArrayList<String> cpTot;

    /**
     ** Constructor
     */
    public C_CashierPerformance() {

        this.f0_bold = getFont(HELVETICA, WINANSI, 14.04f, BOLD);
        this.f1_bold = getFont(HELVETICA, WINANSI, 9.96f, BOLD);
        this.f2_bold = getFont(HELVETICA, WINANSI, 8f, BOLD);
        this.f3_bold = getFont(HELVETICA, WINANSI, 6f, BOLDITALIC);
        this.f4_bold = getFont(HELVETICA, WINANSI, 5f, BOLD);
        this.f1_normal = getFont(HELVETICA, WINANSI, 9.96f, NORMAL);
        this.f2_normal = getFont(HELVETICA, WINANSI, 8f, NORMAL);
        this.f3_normal = getFont(HELVETICA, WINANSI, 5f, NORMAL);

    }

    /**
     *
     * @param cmfb
     * @param colonne
     * @param firstTime
     * @param lastTime
     * @param document
     * @param opType
     * @param filialisel
     * @param filialifull
     * @return
     */
    public Document receipt(C_CashierPerformance_value cmfb, ArrayList<String> colonne, boolean firstTime,
            boolean lastTime, Document document, String opType, ArrayList<String> filialisel, ArrayList<Branch> filialifull) {

        columnWidths2 = new float[colonne.size()];

        for (int c = 0; c < colonne.size(); c++) {
            columnWidths2[c] = 10f;
        }

        try {

            if (firstTime) {
                listaTot = new ArrayList<>();

                //creo un array di appoggio per sommarmi i totali parametrizzati
//                C_CashierPerformance_value t = (C_CashierPerformance_value) cmfb.getDati().get(0);
                int size = cmfb.getDati().size();
                
                cpTot = new ArrayList<>();

                for (int i = 0; i < size; i++) {
                    cpTot.add("0");
                }

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

                PdfPTable tablebis = new PdfPTable(2);
                tablebis.setWidths(columnWidths0bis);
                tablebis.setWidthPercentage(100);

                String opTypeesteso;

                switch (opType) {
                    case "BS":
                        opTypeesteso = "Buy / Sell";
                        break;
                    case "B":
                        opTypeesteso = "Buy";
                        break;
                    default:
                        opTypeesteso = "Sell";
                        break;
                }

                phrase1 = new Phrase();
                phrase1.add(new Chunk("Operation Type: " + opTypeesteso, f3_bold));
                cell1 = new PdfPCell(phrase1);
                cell1.setBorder(NO_BORDER);
                tablebis.addCell(cell1);

                pa1 = new Paragraph(new Phrase("", f2_normal));
                pa1.setAlignment(ALIGN_RIGHT);
                cell2 = new PdfPCell(pa1);
                cell2.setBorder(NO_BORDER);
                cell2.setHorizontalAlignment(ALIGN_RIGHT);
                tablebis.addCell(cell2);

                document.add(tablebis);

                String elencofiliali = "";
                for (int v = 0; v < filialisel.size(); v++) {
                    elencofiliali = elencofiliali + " - " + formatBankBranch(filialisel.get(v), "BR", null, filialifull,null);
                }

                tablebis = new PdfPTable(2);
                tablebis.setWidths(columnWidths0bis);
                tablebis.setWidthPercentage(100);

                phrase1 = new Phrase();
                phrase1.add(new Chunk(elencofiliali, f3_bold));
                cell1 = new PdfPCell(phrase1);
                cell1.setBorder(NO_BORDER);
                tablebis.addCell(cell1);

                pa1 = new Paragraph(new Phrase("", f2_normal));
                pa1.setAlignment(ALIGN_RIGHT);
                cell2 = new PdfPCell(pa1);
                cell2.setBorder(NO_BORDER);
                cell2.setHorizontalAlignment(ALIGN_RIGHT);
                tablebis.addCell(cell2);

                document.add(tablebis);

            }

            //  document.add(table2);
            // document.add(sep);
            ArrayList<C_CashierPerformance_value> dati = cmfb.getDati();

            PdfPCell cell;
            Phrase phraset;
            PdfPCell cellt;
            PdfPTable table3;

            int totfull = 0;
            int totNtrans = 0;
            int totNff = 0;
            int totDel = 0;
            double totVolume = 0;
            double totComFix = 0;
            int totErr = 0;
            double totTotErr = 0;
            double totPerc = 0;

            boolean ft = true;

            if (ft) {

                LineSeparator sep = new LineSeparator();
                sep.setOffset(-2);
                sep.setLineWidth((float) 0.5);

                PdfPTable table2 = new PdfPTable(colonne.size());
                table2.setWidths(columnWidths2);
                table2.setWidthPercentage(100);

                PdfPCell[] list = new PdfPCell[colonne.size()];
                //mi scandisco le colonne
                for (int c = 0; c < colonne.size(); c++) {
                    Phrase phraset1 = new Phrase();
                    phraset1.add(new Chunk(colonne.get(c), f4_bold));
                    PdfPCell cellt1 = new PdfPCell(phraset1);
                    cellt1.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt1.setBorder(BOTTOM | TOP);
                    cellt1.setFixedHeight(20f);
                    cellt1.setBackgroundColor(LIGHT_GRAY);
                    cellt1.setBorderWidth(0.7f);
                    if (c == 0) {
                        cellt1.setHorizontalAlignment(ALIGN_LEFT);
                    }

                    list[c] = cellt1;
                }

                table3 = new PdfPTable(colonne.size());
                table3.setWidths(columnWidths2);
                table3.setWidthPercentage(100);

                for (PdfPCell list1 : list) {
                    PdfPCell temp = (PdfPCell) (list1);
                    table3.addCell(temp);
                }

                document.add(table3);
            }

            for (int j = 0; j < dati.size(); j++) {

                C_CashierPerformance_value temp = (C_CashierPerformance_value) dati.get(j);

                listaTot.add(temp);

                LineSeparator sep = new LineSeparator();
                sep.setOffset(-2);
                sep.setLineWidth((float) 0.5);

                PdfPTable table4 = new PdfPTable(colonne.size());
                table4.setWidths(columnWidths2);
                table4.setWidthPercentage(100);

                phraset = new Phrase();
                phraset.add(new Chunk(temp.getUser(), f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_LEFT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);

                ArrayList<String> cp = temp.getDati2();

                for (int x = 0; x < cp.size(); x++) {

                    phraset = new Phrase();
                    phraset.add(new Chunk(cp.get(x), f3_normal));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setBorder(BOTTOM);
                    table4.addCell(cellt);
                    cpTot.set(x, (parseInt(cpTot.get(x)) + parseInt(cp.get(x))) + "");
                }

                phraset = new Phrase();
                phraset.add(new Chunk(temp.getFull(), f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(temp.getnTrans(), f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(temp.getNff(), f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(temp.getDel(), f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(temp.getVolume()), f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(temp.getComFix()), f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);

                phraset = new Phrase();
                if (fd(temp.getVolume()) == 0) {
                    phraset.add(new Chunk("0.00", f3_normal));
                } else {
                    double a = (fd(temp.getComFix()) / fd(temp.getVolume()) * 100);
                    phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(a, 2) + ""), f3_normal));
                }
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);

                phraset = new Phrase();
                if (fd(temp.getnTrans()) == 0) {
                    phraset.add(new Chunk("0", f3_normal));
                } else {
                    double a = (fd(temp.getVolume()) / fd(temp.getnTrans()));
                    phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(a, 2) + ""), f3_normal));
                }
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);

                phraset = new Phrase();
                if (fd(temp.getnTrans()) == 0) {
                    phraset.add(new Chunk("0", f3_normal));
                } else {
                    double a = (fd(temp.getComFix()) / fd(temp.getnTrans()));
                    phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(a, 2) + ""), f3_normal));
                }
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(temp.getErr(), f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(temp.getTotErr()), f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);

                document.add(table4);

                totfull += parseInt(temp.getFull());
                totNtrans += parseInt(temp.getnTrans());
                totNff += parseInt(temp.getNff());
                totDel += parseInt(temp.getDel());
                totVolume += fd(temp.getVolume());
                totComFix += fd(temp.getComFix());
                totErr += parseInt(temp.getErr());
                totTotErr += fd(temp.getTotErr());

                if (fd(temp.getnTrans()) == 0) {
                    totPerc += 0;
                } else {
                    totPerc += (fd(temp.getComFix()) / fd(temp.getnTrans()));
                }

            }

//              totfull = Utility.roundDouble(totfull, 2);
//             totNtrans = Utility.roundDouble(totNtrans, 2);
//             totNff = Utility.roundDouble(totNff, 2);
//             totDel = Utility.roundDouble(totDel, 2);
            totVolume = roundDouble(totVolume, 2);
            totComFix = roundDouble(totComFix, 2);
//            totErr = Utility.roundDouble(totErr, 0);
            totTotErr = roundDouble(totTotErr, 2);

            //total
            LineSeparator sep = new LineSeparator();
            sep.setOffset(-2);
            sep.setLineWidth((float) 0.5);

            PdfPTable table4 = new PdfPTable(colonne.size());
            table4.setWidths(columnWidths2);
            table4.setWidthPercentage(100);

            phraset = new Phrase();
            phraset.add(new Chunk("Total", f4_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_LEFT);
            cellt.setBorder(BOTTOM);
            table4.addCell(cellt);

            for (int x = 0; x < cpTot.size(); x++) {

                phraset = new Phrase();
                phraset.add(new Chunk(cpTot.get(x), f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);
            }

            phraset = new Phrase();
            phraset.add(new Chunk((totfull + ""), f4_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setBorder(BOTTOM);
            table4.addCell(cellt);

            phraset = new Phrase();
            phraset.add(new Chunk((totNtrans + ""), f4_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setBorder(BOTTOM);
            table4.addCell(cellt);

            phraset = new Phrase();
            phraset.add(new Chunk((totNff + ""), f4_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setBorder(BOTTOM);
            table4.addCell(cellt);

            phraset = new Phrase();
            phraset.add(new Chunk((totDel + ""), f4_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setBorder(BOTTOM);
            table4.addCell(cellt);

            phraset = new Phrase();
            phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(totVolume, 2) + ""), f4_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setBorder(BOTTOM);
            table4.addCell(cellt);

            phraset = new Phrase();
            phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(totComFix, 2) + ""), f4_bold));
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
            double a = ((totVolume) / (totNtrans));
            phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(a, 2) + ""), f4_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setBorder(BOTTOM);
            table4.addCell(cellt);

            phraset = new Phrase();
            double a1 = (totComFix) / (totNtrans);
            phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(a1, 2) + ""), f4_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setBorder(BOTTOM);
            table4.addCell(cellt);

            phraset = new Phrase();
            phraset.add(new Chunk((totErr + ""), f4_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setBorder(BOTTOM);
            table4.addCell(cellt);

            phraset = new Phrase();
            phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(totTotErr, 2) + ""), f4_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setBorder(BOTTOM);
            table4.addCell(cellt);

            document.add(table4);

            //fine total
            //media
            sep = new LineSeparator();
            sep.setOffset(-2);
            sep.setLineWidth((float) 0.5);

            table4 = new PdfPTable(colonne.size());
            table4.setWidths(columnWidths2);
            table4.setWidthPercentage(100);

            phraset = new Phrase();
            phraset.add(new Chunk("Average", f4_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_LEFT);
            cellt.setBorder(BOTTOM);
            table4.addCell(cellt);

            // da calcolare  float mediaBonus=
            double mediaFull = totfull / dati.size();
            double mediaNtrans = totNtrans / dati.size();
            double mediaNff = totNff / dati.size();
            double mediaDel = totDel / dati.size();
            double mediaVolume = totVolume / dati.size();
            double mediaComFix = totComFix / dati.size();
            double mediaErr = totErr / dati.size();
            double mediaTotErr = totTotErr / dati.size();

            mediaFull = roundDouble(mediaFull, 2);
            mediaNtrans = roundDouble(mediaNtrans, 2);
            mediaNff = roundDouble(mediaNff, 2);
            mediaDel = roundDouble(mediaDel, 2);
            mediaVolume = roundDouble(mediaVolume, 2);
            mediaComFix = roundDouble(mediaComFix, 2);
            mediaErr = roundDouble(mediaErr, 2);
            mediaTotErr = roundDouble(mediaTotErr, 2);

            for (int x = 0; x < cpTot.size(); x++) {

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat((fd(cpTot.get(x)) / dati.size()), 2) + ""), f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);
            }

            phraset = new Phrase();
            phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(mediaFull, 2) + ""), f4_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setBorder(BOTTOM);
            table4.addCell(cellt);

            phraset = new Phrase();
            phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(mediaNtrans, 2) + ""), f4_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setBorder(BOTTOM);
            table4.addCell(cellt);

            phraset = new Phrase();
            phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(mediaNff, 2) + ""), f4_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setBorder(BOTTOM);
            table4.addCell(cellt);

            phraset = new Phrase();
            phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(mediaDel, 2) + ""), f4_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setBorder(BOTTOM);
            table4.addCell(cellt);

            phraset = new Phrase();
            phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(mediaVolume, 2) + ""), f4_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setBorder(BOTTOM);
            table4.addCell(cellt);

            phraset = new Phrase();
            phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(mediaComFix, 2) + ""), f4_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setBorder(BOTTOM);
            table4.addCell(cellt);

            phraset = new Phrase();
            double b = ((mediaComFix / mediaVolume) * 100);
            phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(b, 2) + ""), f4_bold));
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
            phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(mediaErr, 2) + ""), f4_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setBorder(BOTTOM);
            table4.addCell(cellt);

            phraset = new Phrase();
            phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(mediaTotErr, 2) + ""), f4_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setBorder(BOTTOM);
            table4.addCell(cellt);

            document.add(table4);

            //fine media
        } catch (DocumentException ex) {
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
     * @param bss
     * @param fasce
     * @param alcolonne
     * @param filiali
     * @param br
     * @return
     */
    public String main(String path, String d3, String d4, String data1, String data2, String bss, ArrayList<String[]> fasce,
            ArrayList<String> alcolonne, ArrayList<String> filiali, ArrayList<Branch> br) {
        try {

            C_CashierPerformance nctl = new C_CashierPerformance();

            C_CashierPerformance_value pdf = new C_CashierPerformance_value();

            boolean firstTime = true;
            boolean lastTime = true;

            File pdffile = new File(path + generaId(50) + "C_CashierPerformance.pdf");
            try(OutputStream ou = new FileOutputStream(pdffile)){
            Document document = new Document(A4.rotate(), 20, 20, 20, 20);
            PdfWriter wr = getInstance(document, ou);
            document.open();
            Db_Master dbm = new Db_Master();
            ArrayList<C_CashierPerformance_value> dati = dbm.list_C_CashierPerformance_value(data1, data2, bss, filiali, fasce);
            dbm.closeDB();
            pdf.setDataDa(d3);
            pdf.setDataA(d4);
            pdf.setDati(dati);
            if (dati.size() > 0) {
                document = nctl.receipt(pdf, alcolonne, firstTime, lastTime, document, bss, filiali, br);
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
     * @param cmfb
     * @param colonne
     * @param firstTime
     * @param lastTime
     * @param opType
     * @param filialisel
     * @param filialifull
     * @param sheet
     * @param cntriga
     * @param style1
     * @param style2
     * @param style3
     * @param style4
     * @param style1bis
     * @param style3left
     * @param style4left
     * @return
     */
    public int receiptexcel(C_CashierPerformance_value cmfb, ArrayList<String> colonne, boolean firstTime,
            boolean lastTime, String opType, ArrayList<String> filialisel, ArrayList<Branch> filialifull, HSSFSheet sheet, int cntriga, HSSFCellStyle style1, HSSFCellStyle style2, HSSFCellStyle style3, HSSFCellStyle style4, HSSFCellStyle style1bis, HSSFCellStyle style3left, HSSFCellStyle style4left) {

        columnWidths2 = new float[colonne.size()];

        for (int c = 0; c < colonne.size(); c++) {
            columnWidths2[c] = 10f;
        }

        try {

            if (firstTime) {
                listaTot = new ArrayList<>();

                //creo un array di appoggio per sommarmi i totali parametrizzati
                C_CashierPerformance_value t = (C_CashierPerformance_value) cmfb.getDati().get(0);
                int size = t.getDati().size();
                cpTot = new ArrayList<>();

                for (int i = 0; i < size; i++) {
                    cpTot.add("0");
                }

                Row rowP = sheet.createRow((short) cntriga);

                Cell cl = rowP.createCell(1);
                cl.setCellStyle(style1);
                cl.setCellValue(intestazionePdf + " From " + cmfb.getDataDa() + " to " + cmfb.getDataA());

                cntriga++;
                cntriga++;

                String opTypeesteso;

                switch (opType) {
                    case "BS":
                        opTypeesteso = "Buy / Sell";
                        break;
                    case "B":
                        opTypeesteso = "Buy";
                        break;
                    default:
                        opTypeesteso = "Sell";
                        break;
                }

                Row rowP2 = sheet.createRow((short) cntriga);

                Cell cl2 = rowP2.createCell(1);
                cl2.setCellStyle(style1);
                cl2.setCellValue("Operation Type: " + opTypeesteso);

                cntriga++;
                cntriga++;

                String elencofiliali = "";
                for (int v = 0; v < filialisel.size(); v++) {
                    elencofiliali = elencofiliali + " - " + formatBankBranch(filialisel.get(v), "BR", null, filialifull,null);
                }

                Row rowP3 = sheet.createRow((short) cntriga);

                Cell cl3 = rowP3.createCell(1);
                cl3.setCellStyle(style1bis);
                cl3.setCellValue(elencofiliali);

                cntriga++;
                cntriga++;

            }

            ArrayList<C_CashierPerformance_value> dati = cmfb.getDati();

            int totfull = 0;
            int totNtrans = 0;
            int totNff = 0;
            int totDel = 0;
            double totVolume = 0;
            double totComFix = 0;
            int totErr = 0;
            double totTotErr = 0;
            double totPerc = 0;

            boolean ft = true;

            if (ft) {

                Row row66 = sheet.createRow((short) cntriga);

                //mi scandisco le colonne
                for (int c = 0; c < colonne.size(); c++) {
                    Cell cl8 = row66.createCell(c + 1);
                    cl8.setCellStyle(style3);
                    if (c == 0) {
                        cl8.setCellStyle(style3left);
                    }
                    cl8.setCellValue(colonne.get(c));
                }

            }

            for (int j = 0; j < dati.size(); j++) {

                cntriga++;

                Row row6 = sheet.createRow((short) cntriga);

                C_CashierPerformance_value temp = (C_CashierPerformance_value) dati.get(j);
                listaTot.add(temp);

                Cell f1bis = row6.createCell(1, STRING);
                f1bis.setCellStyle(style4left);
                f1bis.setCellValue(temp.getUser());

                ArrayList<String> cp = temp.getDati2();

                for (int x = 0; x < cp.size(); x++) {

                    Cell fx = row6.createCell(x + 2);
                    fx.setCellStyle(style4);
                    fx.setCellValue(cp.get(x));

//                    cpTot.set(x, (ff(cpTot.get(x)) + ff(cp.get(x))) + "");
                    cpTot.set(x, (parseInt(cpTot.get(x)) + parseInt(cp.get(x))) + "");
                }

                Cell f2 = row6.createCell(cp.size() + 2);
                f2.setCellStyle(style4);
                f2.setCellValue(temp.getFull());

                Cell f3 = row6.createCell(cp.size() + 3);
                f3.setCellStyle(style4);
                f3.setCellValue(temp.getnTrans());

                Cell f4 = row6.createCell(cp.size() + 4);
                f4.setCellStyle(style4);
                f4.setCellValue(temp.getNff());

                Cell f5 = row6.createCell(cp.size() + 5);
                f5.setCellStyle(style4);
                f5.setCellValue(temp.getDel());

                Cell f6 = row6.createCell(cp.size() + 6);
                f6.setCellStyle(style4);
                f6.setCellValue(temp.getVolume());

                Cell f7 = row6.createCell(cp.size() + 7, NUMERIC);
                f7.setCellStyle(style4);
                f7.setCellValue(fd(temp.getComFix()));

                Cell f8 = row6.createCell(cp.size() + 8);
                f8.setCellStyle(style4);

                if (fd(temp.getVolume()) == 0) {
                    f8.setCellValue("0.00");
                } else {
                    double a = (fd(temp.getComFix()) / fd(temp.getVolume()) * 100);
                    f8.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(a, 2)));
                }

                Cell f9 = row6.createCell(cp.size() + 9);
                f9.setCellStyle(style4);

                if (fd(temp.getnTrans()) == 0) {
                    f9.setCellValue("0");
                } else {
                    double a = (fd(temp.getVolume()) / fd(temp.getnTrans()));
                    f9.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(a, 2)) + "");
                }

                Cell f10 = row6.createCell(cp.size() + 10);
                f10.setCellStyle(style4);

                if (fd(temp.getnTrans()) == 0) {
                    f10.setCellValue("0");
                } else {
                    double a = (fd(temp.getComFix()) / fd(temp.getnTrans()));
                    f10.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(a, 2)) + "");
                }

                Cell f11 = row6.createCell(cp.size() + 11);
                f11.setCellStyle(style4);
                f11.setCellValue(temp.getErr());

                Cell f12 = row6.createCell(cp.size() + 12);
                f12.setCellStyle(style4);
                f12.setCellValue(formatMysqltoDisplay(temp.getTotErr()));

                totfull += parseInt(temp.getFull());
                totNtrans += parseInt(temp.getnTrans());
                totNff += parseInt(temp.getNff());
                totDel += parseInt(temp.getDel());
                totVolume += fd(temp.getVolume());
                totComFix += fd(temp.getComFix());
                totErr += parseInt(temp.getErr());
                totTotErr += fd(temp.getTotErr());

                if (fd(temp.getnTrans()) == 0) {
                    totPerc += 0;
                } else {
                    totPerc += (fd(temp.getComFix()) / fd(temp.getnTrans()));
                }

            }

//            
//              totfull = Utility.roundDouble(totfull, 2);
//             totNtrans = Utility.roundDouble(totNtrans, 2);
//             totNff = Utility.roundDouble(totNff, 2);
//             totDel = Utility.roundDouble(totDel, 2);
            totVolume = roundDouble(totVolume, 2);
            totComFix = roundDouble(totComFix, 2);
//             totErr = Utility.roundDouble(totErr, 2);
            totTotErr = roundDouble(totTotErr, 2);
            totPerc = roundDouble(totPerc, 2);

            cntriga++;

            Row row7 = sheet.createRow((short) cntriga);

            Cell f2 = row7.createCell(1);
            f2.setCellStyle(style3left);
            f2.setCellValue("Total");

            //total
            for (int x = 0; x < cpTot.size(); x++) {
                Cell f8 = row7.createCell(x + 2);
                f8.setCellStyle(style3);
                f8.setCellValue(cpTot.get(x));

            }

            Cell f10 = row7.createCell(cpTot.size() + 2);
            f10.setCellStyle(style3);
            f10.setCellValue(roundDoubleandFormat(totfull, 0));

            Cell f11 = row7.createCell(cpTot.size() + 3);
            f11.setCellStyle(style3);
            f11.setCellValue(roundDoubleandFormat(totNtrans, 0));

            Cell f12 = row7.createCell(cpTot.size() + 4);
            f12.setCellStyle(style3);
            f12.setCellValue(roundDoubleandFormat(totNff, 0));

            Cell f13 = row7.createCell(cpTot.size() + 5);
            f13.setCellStyle(style3);
            f13.setCellValue(roundDoubleandFormat(totDel, 0));

            Cell f14 = row7.createCell(cpTot.size() + 6);
            f14.setCellStyle(style3);
            f14.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(totVolume, 2)));

            Cell f15 = row7.createCell(cpTot.size() + 7);
            f15.setCellStyle(style3);
            f15.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(totComFix, 2)));

            Cell f16 = row7.createCell(cpTot.size() + 9);
            f16.setCellStyle(style3);
            double a = 0.0;
                        if (totNtrans != 0) {
                            a = (totVolume / totNtrans);
                        }
            f16.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(a, 2)));

            Cell f17 = row7.createCell(cpTot.size() + 10);
            f17.setCellStyle(style3);
            double a1 = (totComFix) / (totNtrans);
            f17.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(a1, 2)));

            Cell f18 = row7.createCell(cpTot.size() + 11);
            f18.setCellStyle(style3);
            f18.setCellValue(totErr);

            Cell f19 = row7.createCell(cpTot.size() + 12);
            f19.setCellStyle(style3);
            f19.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(totTotErr, 2)));

            //fine total
            //media
            cntriga++;
            Row row8 = sheet.createRow((short) cntriga);

            Cell f8 = row8.createCell(1);
            f8.setCellStyle(style3left);
            f8.setCellValue("Average");

            // da calcolare  float mediaBonus=
            double mediaFull = totfull / dati.size();
            double mediaNtrans = totNtrans / dati.size();
            double mediaNff = totNff / dati.size();
            double mediaDel = totDel / dati.size();
            double mediaVolume = totVolume / dati.size();
            double mediaComFix = totComFix / dati.size();
            double mediaPerc = totPerc / dati.size();
            double mediaErr = totErr / dati.size();
            double mediaTotErr = totTotErr / dati.size();

            mediaFull = roundDouble(mediaFull, 2);
            mediaNtrans = roundDouble(mediaNtrans, 2);
            mediaNff = roundDouble(mediaNff, 2);
            mediaDel = roundDouble(mediaDel, 2);
            mediaVolume = roundDouble(mediaVolume, 2);
            mediaComFix = roundDouble(mediaComFix, 2);
            mediaErr = roundDouble(mediaErr, 2);
            mediaTotErr = roundDouble(mediaTotErr, 2);


            for (int x = 0; x < cpTot.size(); x++) {

                Cell fx = row8.createCell(x + 2);
                fx.setCellStyle(style3);
                fx.setCellValue((fd(cpTot.get(x)) / dati.size()));

            }

            Cell f70 = row8.createCell(cpTot.size() + 2);
            f70.setCellStyle(style3);
            f70.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(mediaFull, 2)));

            Cell f71 = row8.createCell(cpTot.size() + 3);
            f71.setCellStyle(style3);
            f71.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(mediaNtrans, 2)));

            Cell f72 = row8.createCell(cpTot.size() + 4);
            f72.setCellStyle(style3);
            f72.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(mediaNff, 2)));

            Cell f73 = row8.createCell(cpTot.size() + 5);
            f73.setCellStyle(style3);
            f73.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(mediaDel, 2)));

            Cell f74 = row8.createCell(cpTot.size() + 6);
            f74.setCellStyle(style3);
            f74.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(mediaVolume, 2)));

            Cell f75 = row8.createCell(cpTot.size() + 7);
            f75.setCellStyle(style3);
            f75.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(mediaComFix, 2)));

            Cell f76 = row8.createCell(cpTot.size() + 8);
            f76.setCellStyle(style3);
            double b = ((mediaComFix / mediaVolume) * 100);
            f76.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(b, 2)));

            Cell f77 = row8.createCell(cpTot.size() + 11);
            f77.setCellStyle(style3);
            f77.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(mediaErr, 2)));

            Cell f78 = row8.createCell(cpTot.size() + 12);
            f78.setCellStyle(style3);
            f78.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(mediaTotErr, 2)));

            //fine media
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
     * @param path
     * @param d3
     * @param d4
     * @param data1
     * @param data2
     * @param bss
     * @param fasce
     * @param alcolonne
     * @param filiali
     * @param br
     * @return
     */
    public String mainexcel(String path, String d3, String d4, String data1, String data2, String bss, ArrayList<String[]> fasce,
            ArrayList<String> alcolonne, ArrayList<String> filiali, ArrayList<Branch> br) {
        try {
            File pdffile = new File(path + generaId(50) + "C_CashierPerformance.xls");
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet("C_CashierPerformance");

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

            HSSFCellStyle style1bis = (HSSFCellStyle) workbook.createCellStyle();
            style1bis.setFont(font3);
            style1bis.setAlignment(RIGHT);
            style1bis.setBorderTop(THIN);
            style1bis.setBorderBottom(THIN);
            style1bis.setWrapText(true);

            C_CashierPerformance nctl = new C_CashierPerformance();
            C_CashierPerformance_value pdf = new C_CashierPerformance_value();

            boolean firstTime = true;
            boolean lastTime = true;

            Db_Master dbm = new Db_Master();
            ArrayList<C_CashierPerformance_value> dati = dbm.list_C_CashierPerformance_value(data1, data2, bss, filiali, fasce);
            dbm.closeDB();
            pdf.setDataDa(d3);
            pdf.setDataA(d4);
            pdf.setDati(dati);
            int nriga = 1;

            if (dati.size() > 0) {

                nriga = nctl.receiptexcel(pdf, alcolonne, firstTime, lastTime, bss, filiali, br, sheet, nriga, style1, style2, style3, style4, style1bis, style3left, style4left);
                insertTR("I", "PDF", valueOf(nriga));
//chiusura documento

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
                String base64;
                if (alcolonne.size() > 14) {
                    base64 = generagrafico(pdffile, 1);
                } else {
                    base64 = generagrafico(pdffile, 2);
                }
                pdffile.delete();
                return base64;
            } else {
                return null;
            }
        } catch (IOException ex) {
            insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
        }
        return null;

    }

    /**
     *
     * @param filename
     * @param type
     * @return
     */
    public static String generagrafico(File filename, int type) {
        try {
            try(FileInputStream chart_file_input = new FileInputStream((filename))){
            HSSFWorkbook my_workbook = new HSSFWorkbook(chart_file_input);
            HSSFSheet my_sheet = my_workbook.getSheetAt(0);
            DefaultCategoryDataset my_bar_chart_dataset = new DefaultCategoryDataset();
            Iterator<Row> rowIterator = my_sheet.iterator();
            int numrow = size(rowIterator) + 4;
            rowIterator = my_sheet.iterator();
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                if (row.getRowNum() >= 8 && row.getRowNum() < numrow - 2) {
                    Cell c1 = row.getCell(1);
                    String chart_label = c1.getStringCellValue();
                    Cell c2 = row.getCell(14);
                    if (type == 2) {
                        c2 = row.getCell(9);
                    }
                    Number chart_data = c2.getNumericCellValue();
                    my_bar_chart_dataset.addValue(chart_data.doubleValue(), "Cashier", chart_label);
                }
            }
            JFreeChart BarChartObject = createBarChart("Cashier Performance", "Cashier", "Com+Fix",
                    my_bar_chart_dataset, VERTICAL, true, true, false);
            int width = 1000;
            int height = 250;
            ByteArrayOutputStream chart_out = new ByteArrayOutputStream();
            writeChartAsPNG(chart_out, BarChartObject, width, height);
            int my_picture_id = my_workbook.addPicture(chart_out.toByteArray(), PICTURE_TYPE_PNG);
            chart_out.close();
            HSSFPatriarch drawing = my_sheet.createDrawingPatriarch();
            ClientAnchor my_anchor = new HSSFClientAnchor();
            my_anchor.setCol1(21);
            my_anchor.setRow1(7);
            HSSFPicture my_picture = drawing.createPicture(my_anchor, my_picture_id);
            my_picture.resize();
            chart_file_input.close();
            try(FileOutputStream out = new FileOutputStream(filename)){
            my_workbook.write(out);
            chart_file_input.close();
            out.close();
            return new String(encodeBase64(readFileToByteArray(filename)));
            }
        }
        } catch (FileNotFoundException ex) {
            insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
        } catch (IOException ex) {
            insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
        }
        return null;
    }

}
