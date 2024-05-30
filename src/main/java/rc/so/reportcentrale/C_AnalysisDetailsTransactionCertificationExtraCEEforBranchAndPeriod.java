/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rc.so.reportcentrale;

import static com.itextpdf.text.BaseColor.LIGHT_GRAY;
import com.itextpdf.text.Image;
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
import static com.itextpdf.text.Image.getInstance;
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
import static rc.so.util.Engine.getConf;
import static rc.so.util.Engine.insertTR;
import static rc.so.util.Utility.fd;
import static rc.so.util.Utility.formatMysqltoDisplay;
import static rc.so.util.Utility.generaId;
import static rc.so.util.Utility.roundDouble;
import static rc.so.util.Utility.roundDoubleandFormat;
import java.awt.Color;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import static java.lang.String.valueOf;
import static java.lang.Thread.currentThread;
import java.util.ArrayList;
import static org.apache.commons.codec.binary.Base64.decodeBase64;
import static org.apache.commons.codec.binary.Base64.encodeBase64;
import static org.apache.commons.io.FileUtils.readFileToByteArray;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import static org.apache.poi.hssf.usermodel.HSSFFont.FONT_ARIAL;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import static org.apache.poi.ss.usermodel.BorderStyle.THIN;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Drawing;
import static org.apache.poi.ss.usermodel.HorizontalAlignment.LEFT;
import static org.apache.poi.ss.usermodel.HorizontalAlignment.RIGHT;
import org.apache.poi.ss.usermodel.Picture;
import org.apache.poi.ss.usermodel.Row;
import static org.apache.poi.ss.usermodel.Workbook.PICTURE_TYPE_PNG;

/**
 *
 * @author fplacanica
 */
public class C_AnalysisDetailsTransactionCertificationExtraCEEforBranchAndPeriod {

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
    public static float[] columnWidths2 = new float[]{55f, 15f, 30f, 30f, 15f, 15f, 30f, 30f, 30f, 30f, 30f};

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
    final String intestazionePdf = "Analysis Details Transaction Certification Non Resident ITA";
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

    double tot_equiv = 0, tot_comm = 0;

    /**
     * Constructor
     */
    public C_AnalysisDetailsTransactionCertificationExtraCEEforBranchAndPeriod() {

        this.f0_bold = getFont(HELVETICA, WINANSI, 14.04f, BOLD);
        this.f1_bold = getFont(HELVETICA, WINANSI, 9.96f, BOLD);
        this.f2_bold = getFont(HELVETICA, WINANSI, 8f, BOLD);
        this.f3_bold = getFont(HELVETICA, WINANSI, 8f, BOLDITALIC);
        this.f4_bold = getFont(HELVETICA, WINANSI, 6.96f, BOLD);
        this.f1_normal = getFont(HELVETICA, WINANSI, 9.96f, NORMAL);
        this.f2_normal = getFont(HELVETICA, WINANSI, 8f, NORMAL);
        this.f3_normal = getFont(HELVETICA, WINANSI, 6.96f, NORMAL);

    }

    /**
     *
     * @param cmfb
     * @param colonne
     * @param firstTime
     * @param document
     * @return
     */
    public Document receipt(C_AnalysisDetailsTransactionCertificationExtraCEEforBranchAndPeriod_value cmfb, ArrayList<String> colonne, boolean firstTime, Document document) {

        try {

            if (firstTime) {

                Image image1 = getInstance(decodeBase64(getConf("path.logocl")));
                image1.scalePercent(60f);
                document.add(image1);

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

            }

            //  document.add(table2);
            // document.add(sep);
            ArrayList<C_AnalysisDetailsTransactionCertificationExtraCEEforBranchAndPeriod_value> dati = cmfb.getDati();
            PdfPCell cell;
            Phrase phraset;
            PdfPCell cellt;
            PdfPTable table3;

            boolean ft = true;

            double qta = 0, equiv = 0, comm = 0;

            if (ft) {

                LineSeparator sep = new LineSeparator();
                sep.setOffset(-2);
                sep.setLineWidth((float) 0.5);

                PdfPTable table4 = new PdfPTable(2);
                table4.setWidths(columnWidths0);
                table4.setWidthPercentage(100);

                phraset = new Phrase();
                phraset.add(new Chunk("", f3_bold));
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
                    if (c == 0 || c == 1 || c == 3 || c == 5 || c == 6 || c == 9) {
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

            if (dati.size() == 1) {
                C_AnalysisDetailsTransactionCertificationExtraCEEforBranchAndPeriod_value actual = dati.get(0);

                LineSeparator sep = new LineSeparator();
                sep.setOffset(-2);
                sep.setLineWidth((float) 0.5);

                PdfPTable table4 = new PdfPTable(11);
                table4.setWidths(columnWidths2);
                table4.setWidthPercentage(100);

                phraset = new Phrase();
                phraset.add(new Chunk(actual.getBranch(), f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_LEFT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(actual.getType(), f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_LEFT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(actual.getTransaction(), f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(actual.getDate(), f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_LEFT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(actual.getUser(), f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(actual.getCurrency(), f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_LEFT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(actual.getKind(), f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_LEFT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(actual.getQty()), f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(actual.getEquivalent()), f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(actual.getClient(), f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_LEFT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(actual.getCommission()), f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);

                document.add(table4);

                qta += fd(actual.getQty());
                equiv += fd(actual.getEquivalent());
                comm += fd(actual.getCommission());

                tot_equiv = tot_equiv + equiv;
                tot_comm = tot_comm + comm;
            } else {
                for (int j = 0; j < dati.size(); j++) {

                    C_AnalysisDetailsTransactionCertificationExtraCEEforBranchAndPeriod_value actual = (C_AnalysisDetailsTransactionCertificationExtraCEEforBranchAndPeriod_value) dati.get(j);

                    LineSeparator sep = new LineSeparator();
                    sep.setOffset(-2);
                    sep.setLineWidth((float) 0.5);

                    PdfPTable table4 = new PdfPTable(11);
                    table4.setWidths(columnWidths2);
                    table4.setWidthPercentage(100);

                    phraset = new Phrase();
                    phraset.add(new Chunk(actual.getBranch(), f3_normal));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_LEFT);
                    cellt.setBorder(BOTTOM);
                    table4.addCell(cellt);

                    phraset = new Phrase();
                    phraset.add(new Chunk(actual.getType(), f3_normal));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_LEFT);
                    cellt.setBorder(BOTTOM);
                    table4.addCell(cellt);

                    phraset = new Phrase();
                    phraset.add(new Chunk(actual.getTransaction(), f3_normal));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setBorder(BOTTOM);
                    table4.addCell(cellt);

                    phraset = new Phrase();
                    phraset.add(new Chunk(actual.getDate(), f3_normal));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_LEFT);
                    cellt.setBorder(BOTTOM);
                    table4.addCell(cellt);

                    phraset = new Phrase();
                    phraset.add(new Chunk(actual.getUser(), f3_normal));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setBorder(BOTTOM);
                    table4.addCell(cellt);

                    phraset = new Phrase();
                    phraset.add(new Chunk(actual.getCurrency(), f3_normal));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_LEFT);
                    cellt.setBorder(BOTTOM);
                    table4.addCell(cellt);

                    phraset = new Phrase();
                    phraset.add(new Chunk(actual.getKind(), f3_normal));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_LEFT);
                    cellt.setBorder(BOTTOM);
                    table4.addCell(cellt);

                    phraset = new Phrase();
                    phraset.add(new Chunk(formatMysqltoDisplay(actual.getQty()), f3_normal));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setBorder(BOTTOM);
                    table4.addCell(cellt);

                    phraset = new Phrase();
                    phraset.add(new Chunk(formatMysqltoDisplay(actual.getEquivalent()), f3_normal));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setBorder(BOTTOM);
                    table4.addCell(cellt);

                    phraset = new Phrase();
                    phraset.add(new Chunk(actual.getClient(), f3_normal));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_LEFT);
                    cellt.setBorder(BOTTOM);
                    table4.addCell(cellt);

                    phraset = new Phrase();
                    phraset.add(new Chunk(formatMysqltoDisplay(actual.getCommission()), f3_normal));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setBorder(BOTTOM);
                    table4.addCell(cellt);

                    document.add(table4);

                    qta += fd(actual.getQty());
                    equiv += fd(actual.getEquivalent());
                    comm += fd(actual.getCommission());

                    if (j < dati.size() - 1) {

                        C_AnalysisDetailsTransactionCertificationExtraCEEforBranchAndPeriod_value prossimo = (C_AnalysisDetailsTransactionCertificationExtraCEEforBranchAndPeriod_value) dati.get(j + 1);
                        //controllo se il prossimo elemento è della stessa valuta

                        if (!(prossimo.getCurrency().equals(actual.getCurrency()))) {

                            PdfPTable table5 = new PdfPTable(11);
                            table5.setWidths(columnWidths2);
                            table5.setWidthPercentage(100);

                            phraset = new Phrase();
                            phraset.add(new Chunk("", f4_bold));
                            cellt = new PdfPCell(phraset);
                            cellt.setHorizontalAlignment(ALIGN_RIGHT);
                            cellt.setBorder(BOTTOM);
                            table5.addCell(cellt);

                            phraset = new Phrase();
                            phraset.add(new Chunk("", f4_bold));
                            cellt = new PdfPCell(phraset);
                            cellt.setHorizontalAlignment(ALIGN_RIGHT);
                            cellt.setBorder(BOTTOM);
                            table5.addCell(cellt);

                            phraset = new Phrase();
                            phraset.add(new Chunk("", f4_bold));
                            cellt = new PdfPCell(phraset);
                            cellt.setHorizontalAlignment(ALIGN_RIGHT);
                            cellt.setBorder(BOTTOM);
                            table5.addCell(cellt);

                            phraset = new Phrase();
                            phraset.add(new Chunk("", f4_bold));
                            cellt = new PdfPCell(phraset);
                            cellt.setHorizontalAlignment(ALIGN_RIGHT);
                            cellt.setBorder(BOTTOM);
                            table5.addCell(cellt);

                            phraset = new Phrase();
                            phraset.add(new Chunk("", f4_bold));
                            cellt = new PdfPCell(phraset);
                            cellt.setHorizontalAlignment(ALIGN_RIGHT);
                            cellt.setBorder(BOTTOM);
                            table5.addCell(cellt);

                            phraset = new Phrase();
                            //phraset.add(new Chunk(actual.getCurrency(), f4_bold));
                            phraset.add(new Chunk(actual.getCurrency(), f4_bold));
                            cellt = new PdfPCell(phraset);
                            cellt.setHorizontalAlignment(ALIGN_LEFT);
                            cellt.setBorder(BOTTOM);
                            table5.addCell(cellt);

                            phraset = new Phrase();
                            phraset.add(new Chunk("", f4_bold));
                            cellt = new PdfPCell(phraset);
                            cellt.setHorizontalAlignment(ALIGN_RIGHT);
                            cellt.setBorder(BOTTOM);
                            table5.addCell(cellt);

                            phraset = new Phrase();
                            phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(qta, 2) + ""), f4_bold));
                            cellt = new PdfPCell(phraset);
                            cellt.setHorizontalAlignment(ALIGN_RIGHT);
                            cellt.setBorder(BOTTOM);
                            table5.addCell(cellt);

                            phraset = new Phrase();
                            phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(equiv, 2) + ""), f4_bold));
                            cellt = new PdfPCell(phraset);
                            cellt.setHorizontalAlignment(ALIGN_RIGHT);
                            cellt.setBorder(BOTTOM);
                            table5.addCell(cellt);

                            phraset = new Phrase();
                            phraset.add(new Chunk("", f4_bold));
                            cellt = new PdfPCell(phraset);
                            cellt.setHorizontalAlignment(ALIGN_RIGHT);
                            cellt.setBorder(BOTTOM);
                            table5.addCell(cellt);

                            phraset = new Phrase();
                            phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(comm, 2) + ""), f4_bold));
                            cellt = new PdfPCell(phraset);
                            cellt.setHorizontalAlignment(ALIGN_RIGHT);
                            cellt.setBorder(BOTTOM);
                            table5.addCell(cellt);

                            document.add(table5);

                            tot_equiv = tot_equiv + equiv;
                            tot_comm = tot_comm + comm;

                            qta = 0;
                            equiv = 0;
                            comm = 0;

                        }
                    } else {
                        PdfPTable table5 = new PdfPTable(11);
                        table5.setWidths(columnWidths2);
                        table5.setWidthPercentage(100);

                        phraset = new Phrase();
                        phraset.add(new Chunk("", f4_bold));
                        cellt = new PdfPCell(phraset);
                        cellt.setHorizontalAlignment(ALIGN_RIGHT);
                        cellt.setBorder(BOTTOM);
                        table5.addCell(cellt);

                        phraset = new Phrase();
                        phraset.add(new Chunk("", f4_bold));
                        cellt = new PdfPCell(phraset);
                        cellt.setHorizontalAlignment(ALIGN_RIGHT);
                        cellt.setBorder(BOTTOM);
                        table5.addCell(cellt);

                        phraset = new Phrase();
                        phraset.add(new Chunk("", f4_bold));
                        cellt = new PdfPCell(phraset);
                        cellt.setHorizontalAlignment(ALIGN_RIGHT);
                        cellt.setBorder(BOTTOM);
                        table5.addCell(cellt);

                        phraset = new Phrase();
                        phraset.add(new Chunk("", f4_bold));
                        cellt = new PdfPCell(phraset);
                        cellt.setHorizontalAlignment(ALIGN_RIGHT);
                        cellt.setBorder(BOTTOM);
                        table5.addCell(cellt);

                        phraset = new Phrase();
                        phraset.add(new Chunk("", f4_bold));
                        cellt = new PdfPCell(phraset);
                        cellt.setHorizontalAlignment(ALIGN_RIGHT);
                        cellt.setBorder(BOTTOM);
                        table5.addCell(cellt);

                        phraset = new Phrase();
                        //phraset.add(new Chunk(actual.getCurrency(), f4_bold));
                        phraset.add(new Chunk(actual.getCurrency(), f4_bold));
                        cellt = new PdfPCell(phraset);
                        cellt.setHorizontalAlignment(ALIGN_LEFT);
                        cellt.setBorder(BOTTOM);
                        table5.addCell(cellt);

                        phraset = new Phrase();
                        phraset.add(new Chunk("", f4_bold));
                        cellt = new PdfPCell(phraset);
                        cellt.setHorizontalAlignment(ALIGN_RIGHT);
                        cellt.setBorder(BOTTOM);
                        table5.addCell(cellt);

                        phraset = new Phrase();
                        phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(qta, 2) + ""), f4_bold));
                        cellt = new PdfPCell(phraset);
                        cellt.setHorizontalAlignment(ALIGN_RIGHT);
                        cellt.setBorder(BOTTOM);
                        table5.addCell(cellt);

                        phraset = new Phrase();
                        phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(equiv, 2) + ""), f4_bold));
                        cellt = new PdfPCell(phraset);
                        cellt.setHorizontalAlignment(ALIGN_RIGHT);
                        cellt.setBorder(BOTTOM);
                        table5.addCell(cellt);

                        phraset = new Phrase();
                        phraset.add(new Chunk("", f4_bold));
                        cellt = new PdfPCell(phraset);
                        cellt.setHorizontalAlignment(ALIGN_RIGHT);
                        cellt.setBorder(BOTTOM);
                        table5.addCell(cellt);

                        phraset = new Phrase();
                        phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(comm, 2) + ""), f4_bold));
                        cellt = new PdfPCell(phraset);
                        cellt.setHorizontalAlignment(ALIGN_RIGHT);
                        cellt.setBorder(BOTTOM);
                        table5.addCell(cellt);

                        document.add(table5);
                        tot_equiv = tot_equiv + equiv;
                        tot_comm = tot_comm + comm;
                    }

                }

            }

            tot_equiv = roundDouble(tot_equiv, 2);
            tot_comm = roundDouble(tot_comm, 2);

            //linea totale per branch
            LineSeparator sep = new LineSeparator();
            sep.setLineWidth(0.7f);
            document.add(sep);

            PdfPTable table4 = new PdfPTable(11);
            table4.setWidths(columnWidths2);
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
            phraset.add(new Chunk("Grand Total", f4_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_LEFT);
            cellt.setBorder(BOTTOM);
            table4.addCell(cellt);

            phraset = new Phrase();
            phraset.add(new Chunk("", f4_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setBorder(BOTTOM);
            table4.addCell(cellt);

            phraset = new Phrase();
            phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(tot_equiv, 2)), f4_bold));
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
            phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(tot_comm, 2)), f4_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setBorder(BOTTOM);
            table4.addCell(cellt);

            document.add(table4);

            //linea totale
            sep.setLineWidth(0.7f);
            document.add(sep);

        } catch (DocumentException | IOException ex) {
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
     * @param br
     * @param tipotr
     * @return
     */
    public String main(String path, String d3, String d4, String data1, String data2, ArrayList<String> alcolonne,
            ArrayList<String> filiali, ArrayList<Branch> br, String tipotr) {
        try {
            C_AnalysisDetailsTransactionCertificationExtraCEEforBranchAndPeriod nctl = new C_AnalysisDetailsTransactionCertificationExtraCEEforBranchAndPeriod();

            C_AnalysisDetailsTransactionCertificationExtraCEEforBranchAndPeriod_value pdf = new C_AnalysisDetailsTransactionCertificationExtraCEEforBranchAndPeriod_value();
            boolean firstTime = true;

            File pdffile = new File(path + generaId(50) + "C_AnalysisDetailsTransactionCertificationExtraCEEforBranchAndPeriod.pdf");
            try(OutputStream ou = new FileOutputStream(pdffile)){
            Document document = new Document(A4.rotate(), 20, 20, 20, 20);
            PdfWriter wr = getInstance(document, ou);
            document.open();
            Db_Master dbm = new Db_Master();
            //ciclo per ogni filiale
            ArrayList<C_AnalysisDetailsTransactionCertificationExtraCEEforBranchAndPeriod_value> dati
                    = dbm.list_C_AnalysisDetailsTransactionCertificationExtraCEEforBranchAndPeriod_new(data1, data2,
                            filiali, tipotr, br);
            dbm.closeDB();
            pdf.setDataDa(d3);
            pdf.setDataA(d4);
            pdf.setDati(dati);

            if (dati.size() > 0) {

                document = nctl.receipt(pdf, alcolonne, firstTime, document);

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
     * @param sheet
     * @param cntriga
     * @param style1
     * @param style2
     * @param style3
     * @param style4
     * @param style3left
     * @param style4left
     * @return
     */
    public int receiptexcel(C_AnalysisDetailsTransactionCertificationExtraCEEforBranchAndPeriod_value cmfb, ArrayList<String> colonne, boolean firstTime, HSSFSheet sheet, int cntriga, HSSFCellStyle style1, HSSFCellStyle style2, HSSFCellStyle style3, HSSFCellStyle style4, HSSFCellStyle style3left, HSSFCellStyle style4left) {
        {

            try {

                if (firstTime) {

                    Row rowP = sheet.createRow((short) cntriga);
                    Cell cl = rowP.createCell(1);
                    cl.setCellStyle(style1);
                    cl.setCellValue(intestazionePdf + " From " + cmfb.getDataDa() + " To " + cmfb.getDataA());

                    cntriga++;
                    cntriga++;
                    cntriga++;

                }

                //  document.add(table2);
                // document.add(sep);
                ArrayList<C_AnalysisDetailsTransactionCertificationExtraCEEforBranchAndPeriod_value> dati = cmfb.getDati();

                boolean ft = true;

                float qta = 0, equiv = 0, comm = 0;

                if (ft) {

                    Row row66 = sheet.createRow((short) cntriga);
                    //mi scandisco le colonne
                    for (int c = 0; c < colonne.size(); c++) {
                        Cell cl7 = row66.createCell(c + 1);
                        cl7.setCellStyle(style3);

                        if (c == 0 || c == 1 || c == 3 || c == 5 || c == 6 || c == 9) {
                            cl7.setCellStyle(style3left);
                        }

                        cl7.setCellValue(colonne.get(c));
                    }

                    cntriga++;

                }

                if (dati.size() == 1) {

                    cntriga++;
                    Row row6 = sheet.createRow((short) cntriga);

                    C_AnalysisDetailsTransactionCertificationExtraCEEforBranchAndPeriod_value actual = (C_AnalysisDetailsTransactionCertificationExtraCEEforBranchAndPeriod_value) dati.get(0);

                    Cell f1bis = row6.createCell(1);
                    f1bis.setCellStyle(style4left);
                    f1bis.setCellValue(actual.getBranch());

                    Cell f2 = row6.createCell(2);
                    f2.setCellStyle(style4left);
                    f2.setCellValue(actual.getType());

                    Cell f3 = row6.createCell(3);
                    f3.setCellStyle(style4);
                    f3.setCellValue(actual.getTransaction());

                    Cell f4 = row6.createCell(4);
                    f4.setCellStyle(style4left);
                    f4.setCellValue(actual.getDate());

                    Cell f5 = row6.createCell(5);
                    f5.setCellStyle(style4);
                    f5.setCellValue(actual.getUser());

                    Cell f6 = row6.createCell(6);
                    f6.setCellStyle(style4left);
                    f6.setCellValue(actual.getCurrency());

                    Cell f7 = row6.createCell(7);
                    f7.setCellStyle(style4left);
                    f7.setCellValue(actual.getKind());

                    Cell f8 = row6.createCell(8);
                    f8.setCellStyle(style4);
                    f8.setCellValue(formatMysqltoDisplay(actual.getQty()));

                    Cell f9 = row6.createCell(9);
                    f9.setCellStyle(style4);
                    f9.setCellValue(formatMysqltoDisplay(actual.getEquivalent()));

                    Cell f10 = row6.createCell(10);
                    f10.setCellStyle(style4left);
                    f10.setCellValue(actual.getClient());

                    Cell f11 = row6.createCell(11);
                    f11.setCellStyle(style4);
                    f11.setCellValue(formatMysqltoDisplay(actual.getCommission()));

                    qta += fd(actual.getQty());
                    equiv += fd(actual.getEquivalent());
                    comm += fd(actual.getCommission());

                    tot_equiv = tot_equiv + equiv;
                    tot_comm = tot_comm + comm;
                } else {
                    for (int j = 0; j < dati.size(); j++) {

                        cntriga++;
                        Row row6 = sheet.createRow((short) cntriga);

                        C_AnalysisDetailsTransactionCertificationExtraCEEforBranchAndPeriod_value actual = (C_AnalysisDetailsTransactionCertificationExtraCEEforBranchAndPeriod_value) dati.get(j);

                        Cell f1bis = row6.createCell(1);
                        f1bis.setCellStyle(style4left);
                        f1bis.setCellValue(actual.getBranch());

                        Cell f2 = row6.createCell(2);
                        f2.setCellStyle(style4left);
                        f2.setCellValue(actual.getType());

                        Cell f3 = row6.createCell(3);
                        f3.setCellStyle(style4);
                        f3.setCellValue(actual.getTransaction());

                        Cell f4 = row6.createCell(4);
                        f4.setCellStyle(style4left);
                        f4.setCellValue(actual.getDate());

                        Cell f5 = row6.createCell(5);
                        f5.setCellStyle(style4);
                        f5.setCellValue(actual.getUser());

                        Cell f6 = row6.createCell(6);
                        f6.setCellStyle(style4left);
                        f6.setCellValue(actual.getCurrency());

                        Cell f7 = row6.createCell(7);
                        f7.setCellStyle(style4left);
                        f7.setCellValue(actual.getKind());

                        Cell f8 = row6.createCell(8);
                        f8.setCellStyle(style4);
                        f8.setCellValue(formatMysqltoDisplay(actual.getQty()));

                        Cell f9 = row6.createCell(9);
                        f9.setCellStyle(style4);
                        f9.setCellValue(formatMysqltoDisplay(actual.getEquivalent()));

                        Cell f10 = row6.createCell(10);
                        f10.setCellStyle(style4left);
                        f10.setCellValue(actual.getClient());

                        Cell f11 = row6.createCell(11);
                        f11.setCellStyle(style4);
                        f11.setCellValue(formatMysqltoDisplay(actual.getCommission()));

                        qta += fd(actual.getQty());
                        equiv += fd(actual.getEquivalent());
                        comm += fd(actual.getCommission());

                        

                        //controllo se il prossimo elemento è della stessa valuta
                        if (j < dati.size() - 1) {

                            C_AnalysisDetailsTransactionCertificationExtraCEEforBranchAndPeriod_value prossimo = (C_AnalysisDetailsTransactionCertificationExtraCEEforBranchAndPeriod_value) dati.get(j + 1);
                            //controllo se il prossimo elemento è della stessa valuta
                            if (!(prossimo.getCurrency().equals(actual.getCurrency()))) {

                                cntriga++;
                                Row row7 = sheet.createRow((short) cntriga);

                                Cell f70 = row7.createCell(6);
                                f70.setCellStyle(style3left);
                                f70.setCellValue(actual.getCurrency());

                                Cell f78 = row7.createCell(8);
                                f78.setCellStyle(style3);
                                f78.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(qta, 2) + ""));

                                Cell f79 = row7.createCell(9);
                                f79.setCellStyle(style3);
                                f79.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(equiv, 2) + ""));

                                Cell f711 = row7.createCell(11);
                                f711.setCellStyle(style3);
                                f711.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(comm, 2) + ""));
                                tot_equiv = tot_equiv + equiv;
                        tot_comm = tot_comm + comm;
                                qta = 0;
                                equiv = 0;
                                comm = 0;

                            }
                        }else{
                            cntriga++;
                                Row row7 = sheet.createRow((short) cntriga);

                                Cell f70 = row7.createCell(6);
                                f70.setCellStyle(style3left);
                                f70.setCellValue(actual.getCurrency());

                                Cell f78 = row7.createCell(8);
                                f78.setCellStyle(style3);
                                f78.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(qta, 2) + ""));

                                Cell f79 = row7.createCell(9);
                                f79.setCellStyle(style3);
                                f79.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(equiv, 2) + ""));

                                Cell f711 = row7.createCell(11);
                                f711.setCellStyle(style3);
                                f711.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(comm, 2) + ""));
                                tot_equiv = tot_equiv + equiv;
                        tot_comm = tot_comm + comm;
                                qta = 0;
                                equiv = 0;
                                comm = 0;
                        }

                    }
                }

                tot_equiv = roundDouble(tot_equiv, 2);
                tot_comm = roundDouble(tot_comm, 2);

                cntriga++;
                Row row7 = sheet.createRow((short) cntriga);

                Cell f1bis = row7.createCell(7);
                f1bis.setCellStyle(style3left);
                f1bis.setCellValue("Grand Total");

                Cell f2 = row7.createCell(9);
                f2.setCellStyle(style3);
                f2.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(tot_equiv, 2)));

                Cell f3 = row7.createCell(11);
                f3.setCellStyle(style3);
                f3.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(tot_comm, 2)));

                //linea totale
            } catch (Exception ex) {
                insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
            }
            cntriga++;
            cntriga++;
            cntriga++;
            cntriga++;
            return cntriga++;
        }

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
     * @param br
     * @param tipotr
     * @return
     */
    public String mainexcel(String path, String d3, String d4, String data1, String data2, ArrayList<String> alcolonne,
            ArrayList<String> filiali, ArrayList<Branch> br, String tipotr) {
        try {
            File pdffile = new File(path + generaId(50) + "C_AnalysisDetailsTransactionCertificationExtraCEEforBranchAndPeriod.xls");
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet("C_AnalysisDetailsTransactionCertificationExtraCEEforBranchAndPeriod");

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

            HSSFFont font3bold = workbook.createFont();
            font3bold.setFontName(FONT_ARIAL);
            font3bold.setFontHeightInPoints((short) 10);
            font3bold.setBold(true);

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

            HSSFCellStyle style3leftbold = (HSSFCellStyle) workbook.createCellStyle();
            style3leftbold.setFont(font3);
            style3leftbold.setAlignment(LEFT);
            style3leftbold.setBorderTop(THIN);
            style3leftbold.setBorderBottom(THIN);

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

            byte[] bytes = decodeBase64(getConf("path.logocl"));
            int pictureIdx = workbook.addPicture(bytes, PICTURE_TYPE_PNG);
            CreationHelper helper = workbook.getCreationHelper();
            Drawing drawing = sheet.createDrawingPatriarch();
            ClientAnchor anchor = helper.createClientAnchor();
            anchor.setCol1(2); //Column B
            anchor.setRow1(1); //Row 3
            anchor.setCol2(5); //Column C
            anchor.setRow2(3); //Row 4
            Picture pict = drawing.createPicture(anchor, pictureIdx);

            C_AnalysisDetailsTransactionCertificationExtraCEEforBranchAndPeriod nctl = new C_AnalysisDetailsTransactionCertificationExtraCEEforBranchAndPeriod();

            C_AnalysisDetailsTransactionCertificationExtraCEEforBranchAndPeriod_value pdf = new C_AnalysisDetailsTransactionCertificationExtraCEEforBranchAndPeriod_value();
            boolean firstTime = true;

            Db_Master dbm = new Db_Master();
            //ciclo per ogni filiale
            ArrayList<C_AnalysisDetailsTransactionCertificationExtraCEEforBranchAndPeriod_value> dati
                    = dbm.list_C_AnalysisDetailsTransactionCertificationExtraCEEforBranchAndPeriod(data1, data2,
                            filiali, tipotr, br);
            dbm.closeDB();
            pdf.setDataDa(d3);
            pdf.setDataA(d4);
            pdf.setDati(dati);
            int cntriga = 8;

            if (dati.size() > 0) {
                cntriga = nctl.receiptexcel(pdf, alcolonne, firstTime, sheet, cntriga, style1, style2, style3, style4, style3left, style4left);
                insertTR("I", "PDF", valueOf(cntriga));
            } else {
                pdffile.delete();
                return null;
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

            try (FileOutputStream out = new FileOutputStream(pdffile)) {
                workbook.write(out);
            }
            String base64 = new String(encodeBase64(readFileToByteArray(pdffile)));
            pdffile.delete();
            return base64;
        } catch (IOException ex) {
            insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
        }
        return null;

    }
}
