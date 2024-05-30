/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rc.so.reportcentrale;

import com.itextpdf.text.BaseColor;
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
import static com.itextpdf.text.Rectangle.BOX;
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
import static rc.so.util.Engine.insertTR;
import static rc.so.util.Utility.ff;
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
import static org.apache.commons.codec.binary.Base64.encodeBase64;
import static org.apache.commons.io.FileUtils.readFileToByteArray;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import static org.apache.poi.hssf.usermodel.HSSFFont.FONT_ARIAL;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import static org.apache.poi.ss.usermodel.BorderStyle.MEDIUM;
import static org.apache.poi.ss.usermodel.BorderStyle.THIN;
import static org.apache.poi.ss.usermodel.HorizontalAlignment.CENTER;
import static org.apache.poi.ss.usermodel.HorizontalAlignment.LEFT;
import static org.apache.poi.ss.usermodel.HorizontalAlignment.RIGHT;
import static org.apache.poi.ss.util.RegionUtil.setBorderBottom;
import static org.apache.poi.ss.util.RegionUtil.setBorderLeft;
import static org.apache.poi.ss.util.RegionUtil.setBorderRight;
import static org.apache.poi.ss.util.RegionUtil.setBorderTop;

/**
 *
 * @author fplacanica
 */
public class C_FreeTax_Branch {

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
    public static float[] columnWidths2 = new float[]{40f, 15f, 18f, 18f, 18f, 18f, 18f, 18f, 18f, 18f, 18f, 18f, 18f, 18f};

    /**
     *
     */
    public static float[] columnWidths2int = new float[]{40f, 40f, 40f, 40f};

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
    final String intestazionePdf = "VAT Refund - Branch";
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

    /**
     ** Constructor
     */
    public C_FreeTax_Branch() {

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
     * @return
     */
    public Document receipt(C_FreeTax_Branch_value cmfb, ArrayList<String> colonne, boolean firstTime, boolean lastTime, Document document) {

        try {

            if (firstTime) {
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

            ArrayList<C_FreeTax_Branch_value> dati = cmfb.getDati();

            Phrase phraset;
            PdfPCell cellt;
            PdfPTable table3;

            double totVolPrevYear = 0;
            double totVolYear = 0;
            double totVolVal = 0;
            double totVolPerc = 0;
            double qtyPrevYear = 0;
            double qtyYear = 0;
            double qtyVal = 0;
            double qtyPerc = 0;

            double qtyTransPrevYear = 0;
            double qtyTransYear = 0;
            double qtyTransVal = 0;
            double qtyTransPerc = 0;

            double EUROtotVolPrevYear = 0;
            double EUROtotVolYear = 0;
            double EUROtotVolVal = 0;
            double EUROtotVolPerc = 0;
            double EUROqtyPrevYear = 0;
            double EUROqtyYear = 0;
            double EUROqtyVal = 0;
            double EUROqtyPerc = 0;

            double EUROqtyTransPrevYear = 0;
            double EUROqtyTransYear = 0;
            double EUROqtyTransVal = 0;
            double EUROqtyTransPerc = 0;

            double VALUTAtotVolPrevYear = 0;
            double VALUTAtotVolYear = 0;
            double VALUTAtotVolVal = 0;
            double VALUTAtotVolPerc = 0;
            double VALUTAqtyPrevYear = 0;
            double VALUTAqtyYear = 0;
            double VALUTAqtyVal = 0;
            double VALUTAqtyPerc = 0;

            double VALUTAqtyTransPrevYear = 0;
            double VALUTAqtyTransYear = 0;
            double VALUTAqtyTransVal = 0;
            double VALUTAqtyTransPerc = 0;

            boolean ft = true;

            if (ft) {

                //intestazione Volume - Quantità
                LineSeparator sep = new LineSeparator();
                sep.setOffset(-2);
                sep.setLineWidth((float) 0.5);

                PdfPTable table4 = new PdfPTable(4);
                table4.setWidths(columnWidths2int);
                table4.setWidthPercentage(100);

                phraset = new Phrase();
                phraset.add(new Chunk("", f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk("Amount", f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_CENTER);
                cellt.setBorder(BOX);
                cellt.setBackgroundColor(new BaseColor(242, 242, 242));
                table4.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk("Forms Quantity", f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_CENTER);
                cellt.setBackgroundColor(new BaseColor(242, 242, 242));
                cellt.setBorder(BOX);
                table4.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk("Transaction Quantity", f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_CENTER);
                cellt.setBackgroundColor(new BaseColor(242, 242, 242));
                cellt.setBorder(BOX);
                table4.addCell(cellt);

                document.add(table4);

                //fine intestazione Volume - Quantità
                sep = new LineSeparator();
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
                    cellt1.setFixedHeight(10f);
                    cellt1.setBackgroundColor(LIGHT_GRAY);
                    cellt1.setBorderWidth(0.7f);

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

                C_FreeTax_Branch_value temp = dati.get(j);

                //   listaTot.add(temp);
                LineSeparator sep = new LineSeparator();
                sep.setOffset(-2);
                sep.setLineWidth((float) 0.5);

                PdfPTable table4 = new PdfPTable(colonne.size());
                table4.setWidths(columnWidths2);
                table4.setWidthPercentage(100);

                phraset = new Phrase();
                phraset.add(new Chunk(temp.getBranch(), f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_LEFT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);

                if (temp.isValuta()) {
                    phraset = new Phrase();
                    phraset.add(new Chunk("Currency", f3_normal));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setBorder(BOTTOM);
                    table4.addCell(cellt);
                } else {
                    phraset = new Phrase();
                    phraset.add(new Chunk("Local Currency", f3_normal));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setBorder(BOTTOM);
                    table4.addCell(cellt);
                }

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(temp.getVolPrevYear()), f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(temp.getVolYear()), f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(temp.getVolVal()), f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(temp.getVolPerc()), f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(temp.getQtyPrevYear()), f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(temp.getQtyYear()), f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(temp.getQtyVal()), f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(temp.getQtyPerc()), f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(temp.getQtyTransPrevYear()), f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(temp.getQtyTransYear()), f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(temp.getQtyTransVal()), f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(temp.getQtyTransPerc()), f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);

                document.add(table4);

                totVolPrevYear += ff(temp.getVolPrevYear());
                totVolYear += ff(temp.getVolYear());
                totVolVal += ff(temp.getVolVal());
                totVolPerc += ff(temp.getVolPerc());
                qtyPrevYear += ff(temp.getQtyPrevYear());
                qtyYear += ff(temp.getQtyPrevYear());
                qtyVal += ff(temp.getQtyPrevYear());
                qtyPerc += ff(temp.getQtyPrevYear());
                qtyTransPrevYear += ff(temp.getQtyTransPrevYear());
                qtyTransYear += ff(temp.getQtyTransYear());
                qtyTransVal += ff(temp.getQtyTransVal());
                qtyTransPerc += ff(temp.getQtyTransPerc());

                if (temp.isValuta()) {
                    VALUTAtotVolPrevYear += ff(temp.getVolPrevYear());
                    VALUTAtotVolYear += ff(temp.getVolYear());
                    VALUTAtotVolVal += ff(temp.getVolVal());
                    VALUTAtotVolPerc += ff(temp.getVolPerc());
                    VALUTAqtyPrevYear += ff(temp.getQtyPrevYear());
                    VALUTAqtyYear += ff(temp.getQtyPrevYear());
                    VALUTAqtyVal += ff(temp.getQtyPrevYear());
                    VALUTAqtyPerc += ff(temp.getQtyPrevYear());
                    VALUTAqtyTransPrevYear += ff(temp.getQtyTransPrevYear());
                    VALUTAqtyTransYear += ff(temp.getQtyTransYear());
                    VALUTAqtyTransVal += ff(temp.getQtyTransVal());
                    VALUTAqtyTransPerc += ff(temp.getQtyTransPerc());
                } else {
                    EUROtotVolPrevYear += ff(temp.getVolPrevYear());
                    EUROtotVolYear += ff(temp.getVolYear());
                    EUROtotVolVal += ff(temp.getVolVal());
                    EUROtotVolPerc += ff(temp.getVolPerc());
                    EUROqtyPrevYear += ff(temp.getQtyPrevYear());
                    EUROqtyYear += ff(temp.getQtyPrevYear());
                    EUROqtyVal += ff(temp.getQtyPrevYear());
                    EUROqtyPerc += ff(temp.getQtyPrevYear());
                    EUROqtyTransPrevYear += ff(temp.getQtyTransPrevYear());
                    EUROqtyTransYear += ff(temp.getQtyTransYear());
                    EUROqtyTransVal += ff(temp.getQtyTransVal());
                    EUROqtyTransPerc += ff(temp.getQtyTransPerc());
                }

            }

            totVolPrevYear = roundDouble(totVolPrevYear, 2);
            totVolYear = roundDouble(totVolYear, 2);
            totVolVal = roundDouble(totVolVal, 2);
            qtyPrevYear = roundDouble(qtyPrevYear, 2);
            qtyYear = roundDouble(qtyYear, 2);
            qtyVal = roundDouble(qtyVal, 2);

            VALUTAtotVolPrevYear = roundDouble(VALUTAtotVolPrevYear, 2);
            VALUTAtotVolYear = roundDouble(VALUTAtotVolYear, 2);
            VALUTAtotVolVal = roundDouble(VALUTAtotVolVal, 2);
            VALUTAqtyPrevYear = roundDouble(VALUTAqtyPrevYear, 2);
            VALUTAqtyYear = roundDouble(VALUTAqtyYear, 2);
            VALUTAqtyVal = roundDouble(VALUTAqtyVal, 2);

            EUROtotVolPrevYear = roundDouble(EUROtotVolPrevYear, 2);
            EUROtotVolYear = roundDouble(EUROtotVolYear, 2);
            EUROtotVolVal = roundDouble(EUROtotVolVal, 2);
            EUROtotVolPerc = roundDouble(EUROtotVolPerc, 2);
            EUROqtyPrevYear = roundDouble(EUROqtyPrevYear, 2);
            EUROqtyYear = roundDouble(EUROqtyYear, 2);
            EUROqtyVal = roundDouble(EUROqtyVal, 2);

            //total EURO
//            LineSeparator sep = new LineSeparator();
//            sep.setOffset(-2);
//            sep.setLineWidth((float) 0.5);
            PdfPTable table4 = new PdfPTable(colonne.size());
            table4.setWidths(columnWidths2);
            table4.setWidthPercentage(100);

            phraset = new Phrase();
            phraset.add(new Chunk("", f4_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setBorder(NO_BORDER);
            table4.addCell(cellt);

            phraset = new Phrase();
            phraset.add(new Chunk("Total Currency", f4_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setBorder(NO_BORDER);
            table4.addCell(cellt);

            phraset = new Phrase();
            phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(VALUTAtotVolPrevYear, 2)) + "", f4_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setBorder(NO_BORDER);
            table4.addCell(cellt);

            phraset = new Phrase();
            phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(VALUTAtotVolYear, 2)) + "", f4_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setBorder(NO_BORDER);
            table4.addCell(cellt);

            phraset = new Phrase();
            phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(VALUTAtotVolVal, 2)) + "", f4_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setBorder(NO_BORDER);
            table4.addCell(cellt);

            phraset = new Phrase();
            if (VALUTAtotVolPrevYear > 0 && VALUTAtotVolYear > 0) {
                phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat((VALUTAtotVolVal / VALUTAtotVolPrevYear) * 100, 2)) + "", f4_bold));
            } else {
                phraset.add(new Chunk("0,00", f4_bold));
            }
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setBorder(NO_BORDER);
            table4.addCell(cellt);

            phraset = new Phrase();
            phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(VALUTAqtyPrevYear, 2)) + "", f4_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setBorder(NO_BORDER);
            table4.addCell(cellt);

            phraset = new Phrase();
            phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(VALUTAqtyYear, 2)) + "", f4_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setBorder(NO_BORDER);
            table4.addCell(cellt);

            phraset = new Phrase();
            phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(VALUTAqtyVal, 2)) + "", f4_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setBorder(NO_BORDER);
            table4.addCell(cellt);

            phraset = new Phrase();
            if (VALUTAqtyPrevYear > 0 && VALUTAqtyYear > 0) {
                phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat((VALUTAqtyVal / VALUTAqtyPrevYear) * 100, 2)) + "", f4_bold));
            } else {
                phraset.add(new Chunk("0,00", f4_bold));
            }
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setBorder(NO_BORDER);
            table4.addCell(cellt);

            phraset = new Phrase();
            phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(VALUTAqtyTransPrevYear, 2)) + "", f4_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setBorder(NO_BORDER);
            table4.addCell(cellt);

            phraset = new Phrase();
            phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(VALUTAqtyTransYear, 2)) + "", f4_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setBorder(NO_BORDER);
            table4.addCell(cellt);

            phraset = new Phrase();
            phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(VALUTAqtyTransVal, 2)) + "", f4_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setBorder(NO_BORDER);
            table4.addCell(cellt);

            phraset = new Phrase();
            if (VALUTAqtyTransPrevYear > 0 && VALUTAqtyTransYear > 0) {
                phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(VALUTAqtyTransPerc, 2)) + "", f4_bold));
            } else {
                phraset.add(new Chunk("0,00", f4_bold));
            }
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setBorder(NO_BORDER);
            table4.addCell(cellt);

            document.add(table4);

            table4 = new PdfPTable(colonne.size());
            table4.setWidths(columnWidths2);
            table4.setWidthPercentage(100);

            phraset = new Phrase();
            phraset.add(new Chunk("", f4_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setBorder(NO_BORDER);
            table4.addCell(cellt);

            phraset = new Phrase();
            phraset.add(new Chunk("Tot Local Currency", f4_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setBorder(NO_BORDER);
            table4.addCell(cellt);

            phraset = new Phrase();
            phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(EUROtotVolPrevYear, 2)) + "", f4_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setBorder(NO_BORDER);
            table4.addCell(cellt);

            phraset = new Phrase();
            phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(EUROtotVolYear, 2)) + "", f4_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setBorder(NO_BORDER);
            table4.addCell(cellt);

            phraset = new Phrase();
            phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(EUROtotVolVal, 2)) + "", f4_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setBorder(NO_BORDER);
            table4.addCell(cellt);

            phraset = new Phrase();
            if (EUROtotVolPrevYear > 0 && EUROtotVolYear > 0) {
                phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(EUROtotVolPerc, 2)) + "", f4_bold));
            } else {
                phraset.add(new Chunk("0,00", f4_bold));
            }
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setBorder(NO_BORDER);
            table4.addCell(cellt);

            phraset = new Phrase();
            phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(EUROqtyPrevYear, 0)) + "", f4_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setBorder(NO_BORDER);
            table4.addCell(cellt);

            phraset = new Phrase();
            phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(EUROqtyYear, 0)) + "", f4_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setBorder(NO_BORDER);
            table4.addCell(cellt);

            phraset = new Phrase();
            phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(EUROqtyVal, 0)) + "", f4_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setBorder(NO_BORDER);
            table4.addCell(cellt);

            phraset = new Phrase();
            if (EUROqtyPrevYear > 0 && EUROqtyYear > 0) {
                phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat((EUROqtyVal / EUROqtyPrevYear) * 100, 2)) + "", f4_bold));
            } else {
                phraset.add(new Chunk("0,00", f4_bold));
            }
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setBorder(NO_BORDER);
            table4.addCell(cellt);

            phraset = new Phrase();
            phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(EUROqtyTransPrevYear, 0)) + "", f4_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setBorder(NO_BORDER);
            table4.addCell(cellt);

            phraset = new Phrase();
            phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(EUROqtyTransYear, 0)) + "", f4_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setBorder(NO_BORDER);
            table4.addCell(cellt);

            phraset = new Phrase();
            phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(EUROqtyTransVal, 0)) + "", f4_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setBorder(NO_BORDER);
            table4.addCell(cellt);

            phraset = new Phrase();
            if (EUROqtyTransPrevYear > 0 && EUROqtyTransYear > 0) {
                phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat((EUROqtyTransVal / EUROqtyTransPrevYear) * 100, 2)) + "", f4_bold));
            } else {
                phraset.add(new Chunk("0,00", f4_bold));
            }
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setBorder(NO_BORDER);
            table4.addCell(cellt);

            document.add(table4);

            //fine total
            //total VALUTA
            //fine total
            //total
            table4 = new PdfPTable(colonne.size());
            table4.setWidths(columnWidths2);
            table4.setWidthPercentage(100);

            phraset = new Phrase();
            phraset.add(new Chunk("", f4_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setBorder(NO_BORDER);
            table4.addCell(cellt);

            phraset = new Phrase();
            phraset.add(new Chunk("Total", f4_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setBackgroundColor(new BaseColor(242, 242, 242));
            cellt.setBorder(BOTTOM);
            table4.addCell(cellt);

            phraset = new Phrase();
            phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(totVolPrevYear, 2)) + "", f4_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setBackgroundColor(new BaseColor(242, 242, 242));
            cellt.setBorder(BOTTOM);
            table4.addCell(cellt);

            phraset = new Phrase();
            phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(totVolYear, 2)) + "", f4_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setBackgroundColor(new BaseColor(242, 242, 242));
            cellt.setBorder(BOTTOM);
            table4.addCell(cellt);

            phraset = new Phrase();
            phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(totVolVal, 2)) + "", f4_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setBackgroundColor(new BaseColor(242, 242, 242));
            cellt.setBorder(BOTTOM);
            table4.addCell(cellt);

            phraset = new Phrase();
            if (totVolPrevYear > 0 && totVolYear > 0) {
                phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat((totVolVal / totVolPrevYear) * 100, 2)) + "", f4_bold));
            } else {
                phraset.add(new Chunk("0,00", f4_bold));
            }
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setBackgroundColor(new BaseColor(242, 242, 242));
            cellt.setBorder(BOTTOM);
            table4.addCell(cellt);

            phraset = new Phrase();
            phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(qtyPrevYear, 0)) + "", f4_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setBackgroundColor(new BaseColor(242, 242, 242));
            cellt.setBorder(BOTTOM);
            table4.addCell(cellt);

            phraset = new Phrase();
            phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(qtyYear, 0)) + "", f4_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setBackgroundColor(new BaseColor(242, 242, 242));
            cellt.setBorder(BOTTOM);
            table4.addCell(cellt);

            phraset = new Phrase();
            phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(qtyVal, 0)) + "", f4_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setBorder(BOTTOM);
            cellt.setBackgroundColor(new BaseColor(242, 242, 242));
            table4.addCell(cellt);

            phraset = new Phrase();
            if (qtyPrevYear > 0 && qtyYear > 0) {
                phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat((qtyVal / qtyPrevYear) * 100, 2)) + "", f4_bold));
            } else {
                phraset.add(new Chunk("0,00", f4_bold));
            }
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setBorder(BOTTOM);
            cellt.setBackgroundColor(new BaseColor(242, 242, 242));
            table4.addCell(cellt);

            phraset = new Phrase();
            phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(qtyTransPrevYear, 0)) + "", f4_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setBackgroundColor(new BaseColor(242, 242, 242));
            cellt.setBorder(BOTTOM);
            table4.addCell(cellt);

            phraset = new Phrase();
            phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(qtyTransYear, 0)) + "", f4_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setBackgroundColor(new BaseColor(242, 242, 242));
            cellt.setBorder(BOTTOM);
            table4.addCell(cellt);

            phraset = new Phrase();
            phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(qtyTransVal, 0)) + "", f4_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setBorder(BOTTOM);
            cellt.setBackgroundColor(new BaseColor(242, 242, 242));
            table4.addCell(cellt);

            phraset = new Phrase();
            if (qtyTransPrevYear > 0 && qtyTransYear > 0) {
                phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat((qtyTransVal / qtyTransPrevYear) * 100, 2)) + "", f4_bold));
            } else {
                phraset.add(new Chunk("0,00", f4_bold));
            }
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setBorder(BOTTOM);
            cellt.setBackgroundColor(new BaseColor(242, 242, 242));
            table4.addCell(cellt);

            document.add(table4);

            //fine total
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
     * @param alcolonne
     * @param filiali
     * @param br
     * @return
     */
    public String main(String path, String d3, String d4,
            String data1, String data2,
            ArrayList<String> alcolonne, ArrayList<String> filiali, ArrayList<Branch> br) {
        try {
            C_FreeTax_Branch nctl = new C_FreeTax_Branch();

            C_FreeTax_Branch_value pdf = new C_FreeTax_Branch_value();

            boolean firstTime;
            boolean lastTime;

            File pdffile = new File(path + generaId(50) + "C_FreeTax_Branch.pdf");
            try(OutputStream ou = new FileOutputStream(pdffile)){
            Document document = new Document(A4.rotate(), 20, 20, 20, 20);
            PdfWriter wr = getInstance(document, ou);
            document.open();
            Db_Master dbm = new Db_Master();
            ArrayList<C_FreeTax_Branch_value> dati = dbm.list_C_FreeTax_Branch(filiali, data1, data2, br);

            dbm.closeDB();
            if (dati.size() > 0) {

                pdf.setDataDa(d3);
                pdf.setDataA(d4);
                pdf.setDati(dati);

                firstTime = true;
                lastTime = true;

                document = nctl.receipt(pdf, alcolonne, firstTime, lastTime, document);
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
     * @param sheet
     * @param cntriga
     * @param style1
     * @param style2
     * @param style3
     * @param style4
     * @param style3left
     * @param style4left
     * @param style3center
     * @return
     */
    public int receiptexcel(C_FreeTax_Branch_value cmfb, ArrayList<String> colonne, boolean firstTime, boolean lastTime, HSSFSheet sheet, int cntriga, HSSFCellStyle style1, HSSFCellStyle style2, HSSFCellStyle style3, HSSFCellStyle style4, HSSFCellStyle style3left, HSSFCellStyle style4left, HSSFCellStyle style3center) {

        try {

            if (firstTime) {
                Row rowP = sheet.createRow((short) cntriga);

                Cell cl = rowP.createCell(1);
                cl.setCellStyle(style1);
                cl.setCellValue(intestazionePdf + " From " + cmfb.getDataDa() + " to " + cmfb.getDataA());

                cntriga++;
                cntriga++;

            }

            ArrayList<C_FreeTax_Branch_value> dati = cmfb.getDati();

            double totVolPrevYear = 0;
            double totVolYear = 0;
            double totVolVal = 0;
            double totVolPerc = 0;
            double qtyPrevYear = 0;
            double qtyYear = 0;
            double qtyVal = 0;
            double qtyPerc = 0;

            double qtyTransPrevYear = 0;
            double qtyTransYear = 0;
            double qtyTransVal = 0;
            double qtyTransPerc = 0;

            double EUROtotVolPrevYear = 0;
            double EUROtotVolYear = 0;
            double EUROtotVolVal = 0;
            double EUROtotVolPerc = 0;
            double EUROqtyPrevYear = 0;
            double EUROqtyYear = 0;
            double EUROqtyVal = 0;
            double EUROqtyPerc = 0;

            double EUROqtyTransPrevYear = 0;
            double EUROqtyTransYear = 0;
            double EUROqtyTransVal = 0;
            double EUROqtyTransPerc = 0;

            double VALUTAtotVolPrevYear = 0;
            double VALUTAtotVolYear = 0;
            double VALUTAtotVolVal = 0;
            double VALUTAtotVolPerc = 0;
            double VALUTAqtyPrevYear = 0;
            double VALUTAqtyYear = 0;
            double VALUTAqtyVal = 0;
            double VALUTAqtyPerc = 0;

            double VALUTAqtyTransPrevYear = 0;
            double VALUTAqtyTransYear = 0;
            double VALUTAqtyTransVal = 0;
            double VALUTAqtyTransPerc = 0;

            boolean ft = true;

            if (ft) {

                //intestazione Volume - Quantità
                cntriga++;
                Row row66 = sheet.createRow((short) cntriga);

                Cell f1bis = row66.createCell(3);
                f1bis.setCellStyle(style3center);
                f1bis.setCellValue("Amount");

                Cell f1bis1 = row66.createCell(7);
                f1bis1.setCellStyle(style3center);
                f1bis1.setCellValue("Forms Quantity");

                Cell f1bis2 = row66.createCell(11);
                f1bis2.setCellStyle(style3center);
                f1bis2.setCellValue("Transaction Quantity");

                CellRangeAddress cellRangeAddress = new CellRangeAddress(cntriga, cntriga, 3, 6);
                setBorderTop(MEDIUM, cellRangeAddress, sheet);
                setBorderBottom(MEDIUM, cellRangeAddress, sheet);
                setBorderLeft(MEDIUM, cellRangeAddress, sheet);
                setBorderRight(MEDIUM, cellRangeAddress, sheet);
                sheet.addMergedRegion(cellRangeAddress);
                cellRangeAddress = new CellRangeAddress(cntriga, cntriga, 7, 10);
                setBorderTop(MEDIUM, cellRangeAddress, sheet);
                setBorderBottom(MEDIUM, cellRangeAddress, sheet);
                setBorderLeft(MEDIUM, cellRangeAddress, sheet);
                setBorderRight(MEDIUM, cellRangeAddress, sheet);
                sheet.addMergedRegion(cellRangeAddress);
                cellRangeAddress = new CellRangeAddress(cntriga, cntriga, 11, 14);
                setBorderTop(MEDIUM, cellRangeAddress, sheet);
                setBorderBottom(MEDIUM, cellRangeAddress, sheet);
                setBorderLeft(MEDIUM, cellRangeAddress, sheet);
                setBorderRight(MEDIUM, cellRangeAddress, sheet);
                sheet.addMergedRegion(cellRangeAddress);

                cntriga++;

                cntriga++;

                Row row22 = sheet.createRow((short) cntriga);
                //mi scandisco le colonne
                for (int c = 0; c < colonne.size(); c++) {
                    Cell cl7 = row22.createCell(c + 1);
                    cl7.setCellStyle(style3);
                    if (c == 0 || c == 1) {
                        cl7.setCellStyle(style3left);
                    }
                    cl7.setCellValue(colonne.get(c));
                }

                cntriga++;
            }

            for (int j = 0; j < dati.size(); j++) {

                cntriga++;
                Row row6 = sheet.createRow((short) cntriga);

                C_FreeTax_Branch_value temp = dati.get(j);

                //   listaTot.add(temp);
                Cell f1bis = row6.createCell(1);
                f1bis.setCellStyle(style4left);
                f1bis.setCellValue(temp.getBranch());

                Cell f2 = row6.createCell(2);
                f2.setCellStyle(style4left);

                if (temp.isValuta()) {
                    f2.setCellValue("Currency");

                } else {
                    f2.setCellValue("Local Currency");

                }

                Cell f3 = row6.createCell(3);
                f3.setCellStyle(style4);
                f3.setCellValue(formatMysqltoDisplay(temp.getVolPrevYear()));

                Cell f4 = row6.createCell(4);
                f4.setCellStyle(style4);
                f4.setCellValue(formatMysqltoDisplay(temp.getVolYear()));

                Cell f4b = row6.createCell(5);
                f4b.setCellStyle(style4);
                f4b.setCellValue(formatMysqltoDisplay(temp.getVolVal()));

                Cell f5 = row6.createCell(6);
                f5.setCellStyle(style4);
                f5.setCellValue(formatMysqltoDisplay(temp.getVolPerc()));

                Cell f6 = row6.createCell(7);
                f6.setCellStyle(style4);
                f6.setCellValue(formatMysqltoDisplay(temp.getQtyPrevYear()));

                Cell f7 = row6.createCell(8);
                f7.setCellStyle(style4);
                f7.setCellValue(formatMysqltoDisplay(temp.getQtyYear()));

                Cell f8 = row6.createCell(9);
                f8.setCellStyle(style4);
                f8.setCellValue(formatMysqltoDisplay(temp.getQtyVal()));

                Cell f9 = row6.createCell(10);
                f9.setCellStyle(style4);
                f9.setCellValue(formatMysqltoDisplay(temp.getQtyPerc()));

                Cell f11 = row6.createCell(11);
                f11.setCellStyle(style4);
                f11.setCellValue(formatMysqltoDisplay(temp.getQtyTransPrevYear()));

                Cell f12 = row6.createCell(12);
                f12.setCellStyle(style4);
                f12.setCellValue(formatMysqltoDisplay(temp.getQtyTransYear()));

                Cell f13 = row6.createCell(13);
                f13.setCellStyle(style4);
                f13.setCellValue(formatMysqltoDisplay(temp.getQtyTransVal()));

                Cell f14 = row6.createCell(14);
                f14.setCellStyle(style4);
                f14.setCellValue(formatMysqltoDisplay(temp.getQtyTransPerc()));

                totVolPrevYear += ff(temp.getVolPrevYear());
                totVolYear += ff(temp.getVolYear());
                totVolVal += ff(temp.getVolVal());
                totVolPerc += ff(temp.getVolPerc());
                qtyPrevYear += ff(temp.getQtyPrevYear());
                qtyYear += ff(temp.getQtyYear());
                qtyVal += ff(temp.getQtyVal());
                qtyPerc += ff(temp.getQtyPerc());
                qtyTransPrevYear += ff(temp.getQtyTransPrevYear());
                qtyTransYear += ff(temp.getQtyTransYear());
                qtyTransVal += ff(temp.getQtyTransVal());
                qtyTransPerc += ff(temp.getQtyTransPerc());

                if (temp.isValuta()) {
                    VALUTAtotVolPrevYear += ff(temp.getVolPrevYear());
                    VALUTAtotVolYear += ff(temp.getVolYear());
                    VALUTAtotVolVal += ff(temp.getVolVal());
                    VALUTAtotVolPerc += ff(temp.getVolPerc());
                    VALUTAqtyPrevYear += ff(temp.getQtyPrevYear());
                    VALUTAqtyYear += ff(temp.getQtyYear());
                    VALUTAqtyVal += ff(temp.getQtyVal());
                    VALUTAqtyPerc += ff(temp.getQtyPerc());
                    VALUTAqtyTransPrevYear += ff(temp.getQtyTransPrevYear());
                    VALUTAqtyTransYear += ff(temp.getQtyTransYear());
                    VALUTAqtyTransVal += ff(temp.getQtyTransVal());
                    VALUTAqtyTransPerc += ff(temp.getQtyTransPerc());
                } else {
                    EUROtotVolPrevYear += ff(temp.getVolPrevYear());
                    EUROtotVolYear += ff(temp.getVolYear());
                    EUROtotVolVal += ff(temp.getVolVal());
                    EUROtotVolPerc += ff(temp.getVolPerc());
                    EUROqtyPrevYear += ff(temp.getQtyPrevYear());
                    EUROqtyYear += ff(temp.getQtyYear());
                    EUROqtyVal += ff(temp.getQtyVal());
                    EUROqtyPerc += ff(temp.getQtyPerc());
                    EUROqtyTransPrevYear += ff(temp.getQtyTransPrevYear());
                    EUROqtyTransYear += ff(temp.getQtyTransYear());
                    EUROqtyTransVal += ff(temp.getQtyTransVal());
                    EUROqtyTransPerc += ff(temp.getQtyTransPerc());
                }

            }

            totVolPrevYear = roundDouble(totVolPrevYear, 2);
            totVolYear = roundDouble(totVolYear, 2);
            totVolVal = roundDouble(totVolVal, 2);
            qtyPrevYear = roundDouble(qtyPrevYear, 2);
            qtyYear = roundDouble(qtyYear, 2);
            qtyVal = roundDouble(qtyVal, 2);

            VALUTAtotVolPrevYear = roundDouble(VALUTAtotVolPrevYear, 2);
            VALUTAtotVolYear = roundDouble(VALUTAtotVolYear, 2);
            VALUTAtotVolVal = roundDouble(VALUTAtotVolVal, 2);
            VALUTAqtyPrevYear = roundDouble(VALUTAqtyPrevYear, 2);
            VALUTAqtyYear = roundDouble(VALUTAqtyYear, 2);
            VALUTAqtyVal = roundDouble(VALUTAqtyVal, 2);

            EUROtotVolPrevYear = roundDouble(EUROtotVolPrevYear, 2);
            EUROtotVolYear = roundDouble(EUROtotVolYear, 2);
            EUROtotVolVal = roundDouble(EUROtotVolVal, 2);
            EUROqtyPrevYear = roundDouble(EUROqtyPrevYear, 2);
            EUROqtyYear = roundDouble(EUROqtyYear, 2);
            EUROqtyVal = roundDouble(EUROqtyVal, 2);

            //total EURO
//            LineSeparator sep = new LineSeparator();
//            sep.setOffset(-2);
//            sep.setLineWidth((float) 0.5);
            //fine total
            //total VALUTA
            cntriga++;
            Row row9 = sheet.createRow((short) cntriga);

            Cell f92 = row9.createCell(2);
            f92.setCellStyle(style3left);
            f92.setCellValue("Total Currency");

            Cell f93 = row9.createCell(3);
            f93.setCellStyle(style3);
            f93.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(VALUTAtotVolPrevYear, 2)));

            Cell f94 = row9.createCell(4);
            f94.setCellStyle(style3);
            f94.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(VALUTAtotVolYear, 2)));

            Cell f95 = row9.createCell(5);
            f95.setCellStyle(style3);
            f95.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(VALUTAtotVolVal, 2)));

            Cell f96 = row9.createCell(6);
            f96.setCellStyle(style3);
            if (VALUTAtotVolPrevYear > 0 && VALUTAtotVolYear > 0) {
                f96.setCellValue(formatMysqltoDisplay(roundDoubleandFormat((VALUTAtotVolVal / VALUTAtotVolPrevYear) * 100, 2)));
            } else {
                f96.setCellValue("0,00");
            }

            Cell f97 = row9.createCell(7);
            f97.setCellStyle(style3);
            f97.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(VALUTAqtyPrevYear, 0)));

            Cell f98 = row9.createCell(8);
            f98.setCellStyle(style3);
            f98.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(VALUTAqtyYear, 0)));

            Cell f99 = row9.createCell(9);
            f99.setCellStyle(style3);
            f99.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(VALUTAqtyVal, 0)));

            Cell f100 = row9.createCell(10);
            f100.setCellStyle(style3);
            if (VALUTAqtyPrevYear > 0 && VALUTAqtyYear > 0) {
                f100.setCellValue(formatMysqltoDisplay(roundDoubleandFormat((VALUTAqtyVal / VALUTAqtyPrevYear) * 100, 2)));
            } else {
                f100.setCellValue("0,00");
            }

            Cell f101 = row9.createCell(11);
            f101.setCellStyle(style3);
            f101.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(VALUTAqtyTransPrevYear, 0)));

            Cell f102 = row9.createCell(12);
            f102.setCellStyle(style3);
            f102.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(VALUTAqtyTransYear, 0)));

            Cell f104 = row9.createCell(13);
            f104.setCellStyle(style3);
            f104.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(VALUTAqtyTransVal, 0)));

            Cell f105 = row9.createCell(14);
            f105.setCellStyle(style3);
            if (VALUTAqtyTransPrevYear > 0 && VALUTAqtyTransYear > 0) {
                f105.setCellValue(formatMysqltoDisplay(roundDoubleandFormat((VALUTAqtyTransVal / VALUTAqtyTransPrevYear) * 100, 2)));
            } else {
                f105.setCellValue("0,00");
            }

            cntriga++;
            Row row6 = sheet.createRow((short) cntriga);

            Cell f2 = row6.createCell(2);
            f2.setCellStyle(style3left);
            f2.setCellValue("Total Local Currency");

            Cell f3 = row6.createCell(3);
            f3.setCellStyle(style3);
            f3.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(EUROtotVolPrevYear, 2)));

            Cell f4 = row6.createCell(4);
            f4.setCellStyle(style3);
            f4.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(EUROtotVolYear, 2)));

            Cell f5 = row6.createCell(5);
            f5.setCellStyle(style3);
            f5.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(EUROtotVolVal, 2)));

            Cell f6 = row6.createCell(6);
            f6.setCellStyle(style3);
            if (EUROtotVolPrevYear > 0 && EUROtotVolYear > 0) {
                f6.setCellValue(formatMysqltoDisplay(roundDoubleandFormat((EUROtotVolVal / EUROtotVolPrevYear) * 100, 2)));
            } else {
                f6.setCellValue("0,00");
            }

            Cell f6b = row6.createCell(7);
            f6b.setCellStyle(style3);
            f6b.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(EUROqtyPrevYear, 0)));

            Cell f7 = row6.createCell(8);
            f7.setCellStyle(style3);
            f7.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(EUROqtyYear, 0)));

            Cell f8 = row6.createCell(9);
            f8.setCellStyle(style3);
            f8.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(EUROqtyVal, 0)));

            Cell f9 = row6.createCell(10);
            f9.setCellStyle(style3);
            if (EUROqtyPrevYear > 0 && EUROqtyYear > 0) {
                f9.setCellValue(formatMysqltoDisplay(roundDoubleandFormat((EUROqtyVal / EUROqtyPrevYear) * 100, 2)));
            } else {
                f9.setCellValue("0,00");
            }

            Cell f51 = row6.createCell(11);
            f51.setCellStyle(style3);
            f51.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(EUROqtyTransPrevYear, 0)));

            Cell f52 = row6.createCell(12);
            f52.setCellStyle(style3);
            f52.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(EUROqtyTransYear, 0)));

            Cell f53 = row6.createCell(13);
            f53.setCellStyle(style3);
            f53.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(EUROqtyTransVal, 0)));

            Cell f54 = row6.createCell(14);
            f54.setCellStyle(style3);
            if (EUROqtyTransPrevYear > 0 && EUROqtyTransYear > 0) {
                f54.setCellValue(formatMysqltoDisplay(roundDoubleandFormat((EUROqtyTransVal / EUROqtyTransPrevYear) * 100, 2)));
            } else {
                f54.setCellValue("0,00");
            }

            //fine total
            //total
            cntriga++;
            Row row10 = sheet.createRow((short) cntriga);

            Cell f32 = row10.createCell(2);
            f32.setCellStyle(style3left);
            f32.setCellValue("Total");

            Cell f33 = row10.createCell(3);
            f33.setCellStyle(style3);
            f33.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(totVolPrevYear, 2)));

            Cell f34 = row10.createCell(4);
            f34.setCellStyle(style3);
            f34.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(totVolYear, 2)));

            Cell f35 = row10.createCell(5);
            f35.setCellStyle(style3);
            f35.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(totVolVal, 2)));

            Cell f36 = row10.createCell(6);
            f36.setCellStyle(style3);
            if (totVolPrevYear > 0 && totVolYear > 0) {
                f36.setCellValue(formatMysqltoDisplay(roundDoubleandFormat((totVolVal / totVolPrevYear) * 100, 2)));
            } else {
                f36.setCellValue("0,00");
            }

            Cell f37 = row10.createCell(7);
            f37.setCellStyle(style3);
            f37.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(qtyPrevYear, 0)));

            Cell f38 = row10.createCell(8);
            f38.setCellStyle(style3);
            f38.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(qtyYear, 0)));

            Cell f39 = row10.createCell(9);
            f39.setCellStyle(style3);
            f39.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(qtyVal, 0)));

            Cell f40 = row10.createCell(10);
            f40.setCellStyle(style3);
            if (qtyPrevYear > 0 && qtyYear > 0) {
                f40.setCellValue(formatMysqltoDisplay(roundDoubleandFormat((qtyVal / qtyPrevYear) * 100, 2)));
            } else {
                f40.setCellValue("0,00");
            }

            Cell f41 = row10.createCell(11);
            f41.setCellStyle(style3);
            f41.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(qtyTransPrevYear, 0)));

            Cell f42 = row10.createCell(12);
            f42.setCellStyle(style3);
            f42.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(qtyTransYear, 0)));

            Cell f43 = row10.createCell(13);
            f43.setCellStyle(style3);
            f43.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(qtyTransVal, 0)));

            Cell f44 = row10.createCell(14);
            f44.setCellStyle(style3);
            if (qtyTransPrevYear > 0 && qtyTransYear > 0) {
                f44.setCellValue(formatMysqltoDisplay(roundDoubleandFormat((qtyTransVal / qtyTransPrevYear) * 100, 2)));
            } else {
                f44.setCellValue("0,00");
            }

            //fine total
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
     * @param alcolonne
     * @param filiali
     * @param br
     * @return
     */
    public String mainexcel(String path, String d3, String d4,
            String data1, String data2,
            ArrayList<String> alcolonne, ArrayList<String> filiali, ArrayList<Branch> br) {
        try {
            File pdffile = new File(path + generaId(50) + "C_VatRefund_Branch.xls");
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet("C_VatRefund_Branch");

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

            HSSFCellStyle style3center = (HSSFCellStyle) workbook.createCellStyle();
            style3center.setFont(font3);
            style3center.setAlignment(CENTER);
            style3center.setBorderTop(THIN);
            style3center.setBorderBottom(THIN);

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

            C_FreeTax_Branch nctl = new C_FreeTax_Branch();

            C_FreeTax_Branch_value pdf = new C_FreeTax_Branch_value();

            boolean firstTime;
            boolean lastTime;

            Db_Master dbm = new Db_Master();
            ArrayList<C_FreeTax_Branch_value> dati = dbm.list_C_FreeTax_Branch(filiali, data1, data2, br);

            dbm.closeDB();

            if (dati.size() > 0) {

                pdf.setDataDa(d3);
                pdf.setDataA(d4);
                pdf.setDati(dati);

                firstTime = true;
                lastTime = true;
                int nriga = 1;
                nriga = nctl.receiptexcel(pdf, alcolonne, firstTime, lastTime, sheet, nriga, style1, style2, style3, style4, style3left, style4left, style3center);
                insertTR("I", "PDF", valueOf(nriga));
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
