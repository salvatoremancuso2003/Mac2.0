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
import rc.so.entity.Currency;
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
public class C_FreeTax_AmountBranch {

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
    public static float[] columnWidths2 = new float[]{40f, 10f, 10f, 10f, 10f, 10f, 10f, 10f, 10f, 10f};

    /**
     *
     */
    public static float[] columnWidths2int = new float[]{50f, 40f, 40f};

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
    final String intestazionePdf = "VAT Refund - Currency & Branch";
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

    double totaleVolPrevYear = 0;
    double totaleVolYear = 0;
    double totaleVolVal = 0;
    double totaleVolPerc = 0;
    double totaleqtyPrevYear = 0;
    double totaleqtyYear = 0;
    double totaleqtyVal = 0;
    double totaleqtyPerc = 0;

    double totaleEUROtotVolPrevYear = 0;
    double totaleEUROtotVolYear = 0;
    double totaleEUROtotVolVal = 0;
    double totaleEUROtotVolPerc = 0;
    double totaleEUROqtyPrevYear = 0;
    double totaleEUROqtyYear = 0;
    double totaleEUROqtyVal = 0;
    double totaleEUROqtyPerc = 0;

    double totaleVALUTAtotVolPrevYear = 0;
    double totaleVALUTAtotVolYear = 0;
    double totaleVALUTAtotVolVal = 0;
    double totaleVALUTAtotVolPerc = 0;
    double totaleVALUTAqtyPrevYear = 0;
    double totaleVALUTAqtyYear = 0;
    double totaleVALUTAqtyVal = 0;
    double totaleVALUTAqtyPerc = 0;

    /**
     ** Constructor
     */
    public C_FreeTax_AmountBranch() {

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
    public Document receipt(C_FreeTax_AmountBranch_value cmfb, ArrayList<String> colonne, boolean firstTime, boolean lastTime, Document document) {

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

            ArrayList<C_FreeTax_AmountBranch_value> dati = cmfb.getDati();

            Phrase phraset;
            PdfPCell cellt;
            PdfPTable table3;

            double totVolPrevYear = 0;
            double totVolYear = 0;
            double totVolVal = 0;
            double qtyPrevYear = 0;
            double qtyYear = 0;
            double qtyVal = 0;

            double EUROtotVolPrevYear = 0;
            double EUROtotVolYear = 0;
            double EUROtotVolVal = 0;
            double EUROqtyPrevYear = 0;
            double EUROqtyYear = 0;
            double EUROqtyVal = 0;

            double VALUTAtotVolPrevYear = 0;
            double VALUTAtotVolYear = 0;
            double VALUTAtotVolVal = 0;
            double VALUTAqtyPrevYear = 0;
            double VALUTAqtyYear = 0;
            double VALUTAqtyVal = 0;

            boolean ft = true;

            if (ft) {

                //intestazione Volume - Quantità
                LineSeparator sep = new LineSeparator();
                sep.setOffset(-2);
                sep.setLineWidth((float) 0.5);

                PdfPTable table4 = new PdfPTable(3);
                table4.setWidths(columnWidths2int);
                table4.setWidthPercentage(100);

                phraset = new Phrase();
                phraset.add(new Chunk(cmfb.getCurrency(), f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_LEFT);
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

                EUROtotVolPrevYear = 0;
                EUROtotVolYear = 0;
                EUROtotVolVal = 0;
                EUROqtyPrevYear = 0;
                EUROqtyYear = 0;
                EUROqtyVal = 0;

                VALUTAtotVolPrevYear = 0;
                VALUTAtotVolYear = 0;
                VALUTAtotVolVal = 0;
                VALUTAqtyPrevYear = 0;
                VALUTAqtyYear = 0;
                VALUTAqtyVal = 0;

                C_FreeTax_AmountBranch_value temp = dati.get(j);

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
                    cellt.setHorizontalAlignment(ALIGN_LEFT);
                    cellt.setBorder(BOTTOM);
                    table4.addCell(cellt);
                } else {
                    phraset = new Phrase();
                    phraset.add(new Chunk("Local Currency", f3_normal));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_LEFT);
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

                document.add(table4);

                totVolPrevYear += ff(temp.getVolPrevYear());
                totVolYear += ff(temp.getVolYear());
                totVolVal += ff(temp.getVolVal());
                qtyPrevYear += ff(temp.getQtyPrevYear());
                qtyYear += ff(temp.getQtyYear());
                qtyVal += ff(temp.getQtyVal());

                if (temp.isValuta()) {
                    VALUTAtotVolPrevYear += ff(temp.getVolPrevYear());
                    VALUTAtotVolYear += ff(temp.getVolYear());
                    VALUTAtotVolVal += ff(temp.getVolVal());
                    VALUTAqtyPrevYear += ff(temp.getQtyPrevYear());
                    VALUTAqtyYear += ff(temp.getQtyYear());
                    VALUTAqtyVal += ff(temp.getQtyVal());
                } else {
                    EUROtotVolPrevYear += ff(temp.getVolPrevYear());
                    EUROtotVolYear += ff(temp.getVolYear());
                    EUROtotVolVal += ff(temp.getVolVal());
                    EUROqtyPrevYear += ff(temp.getQtyPrevYear());
                    EUROqtyYear += ff(temp.getQtyYear());
                    EUROqtyVal += ff(temp.getQtyVal());
                }

                //totale generale
                totaleVolPrevYear += ff(temp.getVolPrevYear());
                totaleVolYear += ff(temp.getVolYear());
                totaleVolVal += ff(temp.getVolVal());
                totaleVolPerc += ff(temp.getVolPerc());
                totaleqtyPrevYear += ff(temp.getQtyPrevYear());
                totaleqtyYear += ff(temp.getQtyYear());
                totaleqtyVal += ff(temp.getQtyVal());
                totaleqtyPerc += ff(temp.getQtyPerc());

                if (temp.isValuta()) {
                    totaleVALUTAtotVolPrevYear += ff(temp.getVolPrevYear());
                    totaleVALUTAtotVolYear += ff(temp.getVolYear());
                    totaleVALUTAtotVolVal += ff(temp.getVolVal());
                    totaleVALUTAtotVolPerc += ff(temp.getVolPerc());
                    totaleVALUTAqtyPrevYear += ff(temp.getQtyPrevYear());
                    totaleVALUTAqtyYear += ff(temp.getQtyYear());
                    totaleVALUTAqtyVal += ff(temp.getQtyVal());
                    totaleVALUTAqtyPerc += ff(temp.getQtyPerc());
                } else {
                    totaleEUROtotVolPrevYear += ff(temp.getVolPrevYear());
                    totaleEUROtotVolYear += ff(temp.getVolYear());
                    totaleEUROtotVolVal += ff(temp.getVolVal());
                    totaleEUROtotVolPerc += ff(temp.getVolPerc());
                    totaleEUROqtyPrevYear += ff(temp.getQtyPrevYear());
                    totaleEUROqtyYear += ff(temp.getQtyYear());
                    totaleEUROqtyVal += ff(temp.getQtyVal());
                    totaleEUROqtyPerc += ff(temp.getQtyPerc());
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

            totaleVolPrevYear = roundDouble(totaleVolPrevYear, 2);
            totaleVolYear = roundDouble(totaleVolYear, 2);
            totaleVolVal = roundDouble(totaleVolVal, 2);
            totaleVolPerc = roundDouble(totaleVolPerc, 2);
            totaleqtyPrevYear = roundDouble(totaleqtyPrevYear, 2);
            totaleqtyYear = roundDouble(totaleqtyYear, 2);
            totaleqtyVal = roundDouble(totaleqtyVal, 2);
            totaleqtyPerc = roundDouble(totaleqtyPerc, 2);

            totaleVALUTAtotVolPrevYear = roundDouble(totaleVALUTAtotVolPrevYear, 2);
            totaleVALUTAtotVolYear = roundDouble(totaleVALUTAtotVolYear, 2);
            totaleVALUTAtotVolVal = roundDouble(totaleVALUTAtotVolVal, 2);
            totaleVALUTAtotVolPerc = roundDouble(totaleVALUTAtotVolPerc, 2);
            totaleVALUTAqtyPrevYear = roundDouble(totaleVALUTAqtyPrevYear, 2);
            totaleVALUTAqtyYear = roundDouble(totaleVALUTAqtyYear, 2);
            totaleVALUTAqtyVal = roundDouble(totaleVALUTAqtyVal, 2);
            totaleVALUTAqtyPerc = roundDouble(totaleVALUTAqtyPerc, 2);

            totaleEUROtotVolPrevYear = roundDouble(totaleEUROtotVolPrevYear, 2);
            totaleEUROtotVolYear = roundDouble(totaleEUROtotVolYear, 2);
            totaleEUROtotVolVal = roundDouble(totaleEUROtotVolVal, 2);
            totaleEUROtotVolPerc = roundDouble(totaleEUROtotVolPerc, 2);
            totaleEUROqtyPrevYear = roundDouble(totaleEUROqtyPrevYear, 2);
            totaleEUROqtyYear = roundDouble(totaleEUROqtyYear, 2);
            totaleEUROqtyVal = roundDouble(totaleEUROqtyVal, 2);
            totaleEUROqtyPerc = roundDouble(totaleEUROqtyPerc, 2);

            //total EURO
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
            phraset.add(new Chunk("Total Local Currency", f4_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_LEFT);
            cellt.setBorder(NO_BORDER);
            cellt.setBackgroundColor(new BaseColor(242, 242, 242));
            table4.addCell(cellt);

            phraset = new Phrase();
            phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(EUROtotVolPrevYear, 2)) + "", f4_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setBorder(NO_BORDER);
            cellt.setBackgroundColor(new BaseColor(242, 242, 242));
            table4.addCell(cellt);

            phraset = new Phrase();
            phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(EUROtotVolYear, 2)) + "", f4_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setBorder(NO_BORDER);
            cellt.setBackgroundColor(new BaseColor(242, 242, 242));
            table4.addCell(cellt);

            phraset = new Phrase();
            phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(EUROtotVolVal, 2)) + "", f4_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setBorder(NO_BORDER);
            cellt.setBackgroundColor(new BaseColor(242, 242, 242));
            table4.addCell(cellt);

            phraset = new Phrase();
            if (EUROtotVolPrevYear > 0 && EUROtotVolYear > 0) {
                phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat((EUROtotVolVal / EUROtotVolPrevYear) * 100, 2)) + "", f4_bold));
            } else {
                phraset.add(new Chunk("0,00", f4_bold));
            }
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setBorder(NO_BORDER);
            cellt.setBackgroundColor(new BaseColor(242, 242, 242));
            table4.addCell(cellt);

            phraset = new Phrase();
            phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(EUROqtyPrevYear, 2)) + "", f4_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setBorder(NO_BORDER);
            cellt.setBackgroundColor(new BaseColor(242, 242, 242));
            table4.addCell(cellt);

            phraset = new Phrase();
            phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(EUROqtyYear, 2)) + "", f4_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setBorder(NO_BORDER);
            cellt.setBackgroundColor(new BaseColor(242, 242, 242));
            table4.addCell(cellt);

            phraset = new Phrase();
            phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(EUROqtyVal, 2)) + "", f4_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setBorder(NO_BORDER);
            cellt.setBackgroundColor(new BaseColor(242, 242, 242));
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
            cellt.setBackgroundColor(new BaseColor(242, 242, 242));
            table4.addCell(cellt);

            document.add(table4);

            //fine total
            //total VALUTA
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
            phraset.add(new Chunk("Total Currency", f4_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_LEFT);
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

            document.add(table4);

            //fine total
            //total
            table4 = new PdfPTable(colonne.size());
            table4.setWidths(columnWidths2);
            table4.setWidthPercentage(100);

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
            phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(qtyPrevYear, 2)) + "", f4_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setBackgroundColor(new BaseColor(242, 242, 242));
            cellt.setBorder(BOTTOM);
            table4.addCell(cellt);

            phraset = new Phrase();
            phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(qtyYear, 2)) + "", f4_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setBackgroundColor(new BaseColor(242, 242, 242));
            cellt.setBorder(BOTTOM);
            table4.addCell(cellt);

            phraset = new Phrase();
            phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(qtyVal, 2)) + "", f4_bold));
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

            document.add(table4);

            //fine total
            vuoto.setFont(f3_normal);
            document.add(vuoto);
            document.add(vuoto);

            if (lastTime) {

                //totale generale                   
                //total EURO                     
                LineSeparator sep = new LineSeparator();
                sep.setOffset(-1);
                sep.setLineWidth((float) 0.7);
                document.add(sep);

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
                phraset.add(new Chunk("Total Local Currency", f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_LEFT);
                cellt.setBorder(NO_BORDER);
                cellt.setBackgroundColor(new BaseColor(242, 242, 242));
                table4.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(totaleEUROtotVolPrevYear, 2)) + "", f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(NO_BORDER);
                cellt.setBackgroundColor(new BaseColor(242, 242, 242));
                table4.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(totaleEUROtotVolYear, 2)) + "", f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(NO_BORDER);
                cellt.setBackgroundColor(new BaseColor(242, 242, 242));
                table4.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(totaleEUROtotVolVal, 2)) + "", f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(NO_BORDER);
                cellt.setBackgroundColor(new BaseColor(242, 242, 242));
                table4.addCell(cellt);

                phraset = new Phrase();
                if (totaleEUROtotVolPrevYear > 0 && totaleEUROtotVolYear > 0) {
                    phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat((totaleEUROtotVolVal / totaleEUROtotVolPrevYear) * 100, 2)) + "", f4_bold));
                } else {
                    phraset.add(new Chunk("0,00", f4_bold));
                }
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(NO_BORDER);
                cellt.setBackgroundColor(new BaseColor(242, 242, 242));
                table4.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(totaleEUROqtyPrevYear, 2)) + "", f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(NO_BORDER);
                cellt.setBackgroundColor(new BaseColor(242, 242, 242));
                table4.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(totaleEUROqtyYear, 2)) + "", f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(NO_BORDER);
                cellt.setBackgroundColor(new BaseColor(242, 242, 242));
                table4.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(totaleEUROqtyVal, 2)) + "", f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(NO_BORDER);
                cellt.setBackgroundColor(new BaseColor(242, 242, 242));
                table4.addCell(cellt);

                phraset = new Phrase();
                if (totaleEUROqtyPrevYear > 0 && totaleEUROqtyYear > 0) {
                    phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat((totaleEUROqtyVal / totaleEUROqtyPrevYear) * 100, 2)) + "", f4_bold));
                } else {
                    phraset.add(new Chunk("0,00", f4_bold));
                }
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(NO_BORDER);
                cellt.setBackgroundColor(new BaseColor(242, 242, 242));
                table4.addCell(cellt);

                document.add(table4);

                //fine total
                //total VALUTA
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
                phraset.add(new Chunk("Total Currency", f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_LEFT);
                cellt.setBorder(NO_BORDER);
                table4.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(totaleVALUTAtotVolPrevYear, 2)) + "", f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(NO_BORDER);
                table4.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(totaleVALUTAtotVolYear, 2)) + "", f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(NO_BORDER);
                table4.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(totaleVALUTAtotVolVal, 2)) + "", f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(NO_BORDER);
                table4.addCell(cellt);

                phraset = new Phrase();
                if (totaleVALUTAtotVolPrevYear > 0 && totaleVALUTAtotVolYear > 0) {
                    phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat((totaleVALUTAtotVolVal / totaleVALUTAtotVolPrevYear) * 100, 2)) + "", f4_bold));
                } else {
                    phraset.add(new Chunk("0,00", f4_bold));
                }
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(NO_BORDER);
                table4.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(totaleVALUTAqtyPrevYear, 2)) + "", f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(NO_BORDER);
                table4.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(totaleVALUTAqtyYear, 2)) + "", f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(NO_BORDER);
                table4.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(totaleVALUTAqtyVal, 2)) + "", f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(NO_BORDER);
                table4.addCell(cellt);

                phraset = new Phrase();
                if (totaleVALUTAqtyPrevYear > 0 && totaleVALUTAqtyYear > 0) {
                    phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat((totaleVALUTAqtyVal / totaleVALUTAqtyPrevYear) * 100, 2)) + "", f4_bold));
                } else {
                    phraset.add(new Chunk("0,00", f4_bold));
                }
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(NO_BORDER);
                table4.addCell(cellt);

                document.add(table4);

                //fine total
                //total
                table4 = new PdfPTable(colonne.size());
                table4.setWidths(columnWidths2);
                table4.setWidthPercentage(100);

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
                cellt.setBackgroundColor(new BaseColor(242, 242, 242));
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(totaleVolPrevYear, 2)) + "", f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBackgroundColor(new BaseColor(242, 242, 242));
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(totaleVolYear, 2)) + "", f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBackgroundColor(new BaseColor(242, 242, 242));
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(totaleVolVal, 2)) + "", f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBackgroundColor(new BaseColor(242, 242, 242));
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);

                phraset = new Phrase();
                if (totaleVolPrevYear > 0 && totaleVolYear > 0) {
                    phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat((totaleVolVal / totaleVolPrevYear) * 100, 2)) + "", f4_bold));
                } else {
                    phraset.add(new Chunk("0,00", f4_bold));
                }
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBackgroundColor(new BaseColor(242, 242, 242));
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(totaleqtyPrevYear, 2)) + "", f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBackgroundColor(new BaseColor(242, 242, 242));
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(totaleqtyYear, 2)) + "", f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBackgroundColor(new BaseColor(242, 242, 242));
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(totaleqtyVal, 2)) + "", f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                cellt.setBackgroundColor(new BaseColor(242, 242, 242));
                table4.addCell(cellt);

                phraset = new Phrase();
                if (totaleqtyPrevYear > 0 && totaleqtyYear > 0) {
                    phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat((totaleqtyVal / totaleqtyPrevYear) * 100, 2)) + "", f4_bold));
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
            }

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
            C_FreeTax_AmountBranch nctl = new C_FreeTax_AmountBranch();

            C_FreeTax_AmountBranch_value pdf = new C_FreeTax_AmountBranch_value();

            boolean firstTime = true;
            boolean lastTime = false;

            File pdffile = new File(path + generaId(50) + "C_VatRefund_CurrencyBranch.pdf");
            try(OutputStream ou = new FileOutputStream(pdffile)){
            Document document = new Document(A4.rotate(), 20, 20, 20, 20);
            PdfWriter wr = getInstance(document, ou);
            document.open();

////        ArrayList<String> currency=Engine.getC_Currency_FreeTax_AmountBranch();
            Db_Master dbm = new Db_Master();
            ArrayList<C_FreeTax_AmountBranch_value> dati = dbm.list_C_FreeTax_AmountBranch_value(filiali, data1, data2, br);
            ArrayList<Currency> cu = dbm.list_figures(true);

            int lastindex = 0;

            if (dati.size() > 0) {

                for (int i = 0; i < cu.size(); i++) {

                    ArrayList<C_FreeTax_AmountBranch_value> dati_cu = new ArrayList<>();

                    for (int j = 0; j < dati.size(); j++) {
                        if (dati.get(j).getCurrency().equals(cu.get(i).getCode())) {
                            dati_cu.add(dati.get(j));
                        }
                    }

                    if (!dati_cu.isEmpty()) {
                        lastindex = i;
                    }

                }

                for (int i = 0; i < cu.size(); i++) {

                    ArrayList<C_FreeTax_AmountBranch_value> dati_cu = new ArrayList<>();

                    for (int j = 0; j < dati.size(); j++) {
                        if (dati.get(j).getCurrency().equals(cu.get(i).getCode())) {
                            dati_cu.add(dati.get(j));
                        }
                    }

                    pdf.setDataDa(d3);
                    pdf.setDataA(d4);
                    pdf.setDati(dati_cu);
                    pdf.setCurrency(cu.get(i).getCode() + " " + cu.get(i).getDescrizione());

                    if (i == lastindex) {
                        lastTime = true;
                    }
                    if (!dati_cu.isEmpty()) {
                        document = nctl.receipt(pdf, alcolonne, firstTime, lastTime, document);
                        firstTime = false;
                    }

                }

                dbm.closeDB();

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
    public int receiptexcel(C_FreeTax_AmountBranch_value cmfb, ArrayList<String> colonne, boolean firstTime, boolean lastTime, HSSFSheet sheet, int cntriga, HSSFCellStyle style1, HSSFCellStyle style2, HSSFCellStyle style3, HSSFCellStyle style4, HSSFCellStyle style3left, HSSFCellStyle style4left, HSSFCellStyle style3center) {

        try {

            if (firstTime) {
                Row rowP = sheet.createRow((short) cntriga);

                Cell cl = rowP.createCell(1);
                cl.setCellStyle(style1);
                cl.setCellValue(intestazionePdf + " From " + cmfb.getDataDa() + " to " + cmfb.getDataA());

                cntriga++;
                cntriga++;

            }

            ArrayList<C_FreeTax_AmountBranch_value> dati = cmfb.getDati();

            double totVolPrevYear = 0;
            double totVolYear = 0;
            double totVolVal = 0;
            double qtyPrevYear = 0;
            double qtyYear = 0;
            double qtyVal = 0;
            double qtyPerc = 0;

            double EUROtotVolPrevYear = 0;
            double EUROtotVolYear = 0;
            double EUROtotVolVal = 0;
            double EUROqtyPrevYear = 0;
            double EUROqtyYear = 0;
            double EUROqtyVal = 0;

            double VALUTAtotVolPrevYear = 0;
            double VALUTAtotVolYear = 0;
            double VALUTAtotVolVal = 0;
            double VALUTAqtyPrevYear = 0;
            double VALUTAqtyYear = 0;
            double VALUTAqtyVal = 0;

            boolean ft = true;

            if (ft) {

                //intestazione Volume - Quantità
                cntriga++;
                Row row66 = sheet.createRow((short) cntriga);

                Cell f1bis = row66.createCell(1);
                f1bis.setCellStyle(style3left);
                f1bis.setCellValue(cmfb.getBranch());

                Cell f1bis1 = row66.createCell(3);
                f1bis1.setCellStyle(style3center);
                f1bis1.setCellValue("Amount");

                Cell f1bis2 = row66.createCell(7);
                f1bis2.setCellStyle(style3center);
                f1bis2.setCellValue("Forms Quantity");

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

                cntriga++;

                cntriga++;

                Row row22 = sheet.createRow((short) cntriga);
                //mi scandisco le colonne
                for (int c = 0; c < colonne.size(); c++) {
                    Cell cl7 = row22.createCell(c + 1);
                    cl7.setCellStyle(style3);
                    cl7.setCellValue(colonne.get(c));
                }

                cntriga++;
            }

            for (int j = 0; j < dati.size(); j++) {

                cntriga++;
                Row row6 = sheet.createRow((short) cntriga);

                EUROtotVolPrevYear = 0;
                EUROtotVolYear = 0;
                EUROtotVolVal = 0;
                EUROqtyPrevYear = 0;
                EUROqtyYear = 0;
                EUROqtyVal = 0;

                VALUTAtotVolPrevYear = 0;
                VALUTAtotVolYear = 0;
                VALUTAtotVolVal = 0;
                VALUTAqtyPrevYear = 0;
                VALUTAqtyYear = 0;
                VALUTAqtyVal = 0;

                C_FreeTax_AmountBranch_value temp = dati.get(j);

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

                totVolPrevYear += ff(temp.getVolPrevYear());
                totVolYear += ff(temp.getVolYear());
                totVolVal += ff(temp.getVolVal());
                qtyPrevYear += ff(temp.getQtyPrevYear());
                qtyYear += ff(temp.getQtyYear());
                qtyVal += ff(temp.getQtyVal());
                qtyPerc += ff(temp.getQtyPerc());

                if (temp.isValuta()) {
                    VALUTAtotVolPrevYear += ff(temp.getVolPrevYear());
                    VALUTAtotVolYear += ff(temp.getVolYear());
                    VALUTAtotVolVal += ff(temp.getVolVal());
                    VALUTAqtyPrevYear += ff(temp.getQtyPrevYear());
                    VALUTAqtyYear += ff(temp.getQtyYear());
                    VALUTAqtyVal += ff(temp.getQtyVal());
                } else {
                    EUROtotVolPrevYear += ff(temp.getVolPrevYear());
                    EUROtotVolYear += ff(temp.getVolYear());
                    EUROtotVolVal += ff(temp.getVolVal());
                    EUROqtyPrevYear += ff(temp.getQtyPrevYear());
                    EUROqtyYear += ff(temp.getQtyYear());
                    EUROqtyVal += ff(temp.getQtyVal());
                }

                //totale generale
                totaleVolPrevYear += ff(temp.getVolPrevYear());
                totaleVolYear += ff(temp.getVolYear());
                totaleVolVal += ff(temp.getVolVal());
                totaleVolPerc += ff(temp.getVolPerc());
                totaleqtyPrevYear += ff(temp.getQtyPrevYear());
                totaleqtyYear += ff(temp.getQtyYear());
                totaleqtyVal += ff(temp.getQtyVal());
                totaleqtyPerc += ff(temp.getQtyPerc());

                if (temp.isValuta()) {
                    totaleVALUTAtotVolPrevYear += ff(temp.getVolPrevYear());
                    totaleVALUTAtotVolYear += ff(temp.getVolYear());
                    totaleVALUTAtotVolVal += ff(temp.getVolVal());
                    totaleVALUTAtotVolPerc += ff(temp.getVolPerc());
                    totaleVALUTAqtyPrevYear += ff(temp.getQtyPrevYear());
                    totaleVALUTAqtyYear += ff(temp.getQtyYear());
                    totaleVALUTAqtyVal += ff(temp.getQtyVal());
                    totaleVALUTAqtyPerc += ff(temp.getQtyPerc());
                } else {
                    totaleEUROtotVolPrevYear += ff(temp.getVolPrevYear());
                    totaleEUROtotVolYear += ff(temp.getVolYear());
                    totaleEUROtotVolVal += ff(temp.getVolVal());
                    totaleEUROtotVolPerc += ff(temp.getVolPerc());
                    totaleEUROqtyPrevYear += ff(temp.getQtyPrevYear());
                    totaleEUROqtyYear += ff(temp.getQtyYear());
                    totaleEUROqtyVal += ff(temp.getQtyVal());
                    totaleEUROqtyPerc += ff(temp.getQtyPerc());
                }

            }

            //total EURO
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

            totaleVolPrevYear = roundDouble(totaleVolPrevYear, 2);
            totaleVolYear = roundDouble(totaleVolYear, 2);
            totaleVolVal = roundDouble(totaleVolVal, 2);
            totaleVolPerc = roundDouble(totaleVolPerc, 2);
            totaleqtyPrevYear = roundDouble(totaleqtyPrevYear, 2);
            totaleqtyYear = roundDouble(totaleqtyYear, 2);
            totaleqtyVal = roundDouble(totaleqtyVal, 2);
            totaleqtyPerc = roundDouble(totaleqtyPerc, 2);

            totaleVALUTAtotVolPrevYear = roundDouble(totaleVALUTAtotVolPrevYear, 2);
            totaleVALUTAtotVolYear = roundDouble(totaleVALUTAtotVolYear, 2);
            totaleVALUTAtotVolVal = roundDouble(totaleVALUTAtotVolVal, 2);
            totaleVALUTAtotVolPerc = roundDouble(totaleVALUTAtotVolPerc, 2);
            totaleVALUTAqtyPrevYear = roundDouble(totaleVALUTAqtyPrevYear, 2);
            totaleVALUTAqtyYear = roundDouble(totaleVALUTAqtyYear, 2);
            totaleVALUTAqtyVal = roundDouble(totaleVALUTAqtyVal, 2);
            totaleVALUTAqtyPerc = roundDouble(totaleVALUTAqtyPerc, 2);

            totaleEUROtotVolPrevYear = roundDouble(totaleEUROtotVolPrevYear, 2);
            totaleEUROtotVolYear = roundDouble(totaleEUROtotVolYear, 2);
            totaleEUROtotVolVal = roundDouble(totaleEUROtotVolVal, 2);
            totaleEUROtotVolPerc = roundDouble(totaleEUROtotVolPerc, 2);
            totaleEUROqtyPrevYear = roundDouble(totaleEUROqtyPrevYear, 2);
            totaleEUROqtyYear = roundDouble(totaleEUROqtyYear, 2);
            totaleEUROqtyVal = roundDouble(totaleEUROqtyVal, 2);
            totaleEUROqtyPerc = roundDouble(totaleEUROqtyPerc, 2);

            cntriga++;
            Row row6 = sheet.createRow((short) cntriga);

            Cell f2 = row6.createCell(2);
            f2.setCellStyle(style3left);
            f2.setCellValue("Total Local Currency");

            Cell f3 = row6.createCell(3);
            f3.setCellStyle(style4);
            f3.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(EUROtotVolPrevYear, 2)));

            Cell f4 = row6.createCell(4);
            f4.setCellStyle(style4);
            f4.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(EUROtotVolYear, 2)));

            Cell f5 = row6.createCell(5);
            f5.setCellStyle(style4);
            f5.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(EUROtotVolVal, 2)));

            Cell f6 = row6.createCell(6);
            f6.setCellStyle(style4);
            if (EUROtotVolPrevYear > 0 && EUROtotVolYear > 0) {
                f6.setCellValue(formatMysqltoDisplay(roundDoubleandFormat((EUROtotVolVal / EUROtotVolPrevYear) * 100, 2)));
            } else {
                f6.setCellValue("0,00");
            }

            Cell f6b = row6.createCell(7);
            f6b.setCellStyle(style4);
            f6b.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(EUROqtyPrevYear, 2)));

            Cell f7 = row6.createCell(8);
            f7.setCellStyle(style4);
            f7.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(EUROqtyYear, 2)));

            Cell f8 = row6.createCell(9);
            f8.setCellStyle(style4);
            f8.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(EUROqtyVal, 2)));

            Cell f9 = row6.createCell(10);
            f9.setCellStyle(style4);
            if (EUROqtyPrevYear > 0 && EUROqtyYear > 0) {
                f9.setCellValue(formatMysqltoDisplay(roundDoubleandFormat((EUROqtyVal / EUROqtyPrevYear) * 100, 2)));
            } else {
                f9.setCellValue("0,00");
            }

            //fine total
            //total VALUTA
            cntriga++;

            Row row10 = sheet.createRow((short) cntriga);

            Cell f32 = row10.createCell(2);
            f32.setCellStyle(style3left);
            f32.setCellValue("Total Currency");

            Cell f43 = row10.createCell(3);
            f43.setCellStyle(style4);
            f43.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(VALUTAtotVolPrevYear, 2)));

            Cell f44 = row10.createCell(4);
            f44.setCellStyle(style4);
            f44.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(VALUTAtotVolYear, 2)));

            Cell f45 = row10.createCell(5);
            f45.setCellStyle(style4);
            f45.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(VALUTAtotVolVal, 2)));

            Cell f46 = row10.createCell(6);
            f46.setCellStyle(style4);
            if (VALUTAtotVolPrevYear > 0 && VALUTAtotVolYear > 0) {
                f46.setCellValue(formatMysqltoDisplay(roundDoubleandFormat((VALUTAtotVolVal / VALUTAtotVolPrevYear) * 100, 2)));
            } else {
                f46.setCellValue("0,00");
            }

            Cell f47 = row10.createCell(7);
            f47.setCellStyle(style4);
            f47.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(VALUTAqtyPrevYear, 2)));

            Cell f48 = row10.createCell(8);
            f48.setCellStyle(style4);
            f48.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(VALUTAqtyYear, 2)));

            Cell f49 = row10.createCell(9);
            f49.setCellStyle(style4);
            f49.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(VALUTAqtyVal, 2)));

            Cell f50 = row10.createCell(10);
            f50.setCellStyle(style4);
            if (VALUTAqtyPrevYear > 0 && VALUTAqtyYear > 0) {
                f50.setCellValue(formatMysqltoDisplay(roundDoubleandFormat((VALUTAqtyVal / VALUTAqtyPrevYear) * 100, 2)));
            } else {
                f50.setCellValue("0,00");
            }

            //fine total
            //total
            cntriga++;
            Row row12 = sheet.createRow((short) cntriga);

            Cell f62 = row12.createCell(2);
            f62.setCellStyle(style3left);
            f62.setCellValue("Grand Total");

            Cell f63 = row12.createCell(3);
            f63.setCellStyle(style4);
            f63.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(totVolPrevYear, 2)));

            Cell f64 = row12.createCell(4);
            f64.setCellStyle(style4);
            f64.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(totVolYear, 2)));

            Cell f65 = row12.createCell(5);
            f65.setCellStyle(style4);
            f65.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(totVolVal, 2)));

            Cell f66 = row12.createCell(6);
            f66.setCellStyle(style4);
            if (totVolPrevYear > 0 && totVolYear > 0) {
                f66.setCellValue(formatMysqltoDisplay(roundDoubleandFormat((totVolVal / totVolPrevYear) * 100, 2)));
            } else {
                f66.setCellValue("0,00");
            }

            Cell f67 = row12.createCell(7);
            f67.setCellStyle(style4);
            f67.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(qtyPrevYear, 2)));

            Cell f68 = row12.createCell(8);
            f68.setCellStyle(style4);
            f68.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(qtyYear, 2)));

            Cell f69 = row12.createCell(9);
            f69.setCellStyle(style4);
            f69.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(qtyVal, 2)));

            Cell f70 = row12.createCell(10);
            f70.setCellStyle(style4);
            if (qtyPrevYear > 0 && qtyYear > 0) {
                f70.setCellValue(formatMysqltoDisplay(roundDoubleandFormat((qtyVal / qtyPrevYear) * 100, 2)));
            } else {
                f70.setCellValue("0,00");
            }

            //fine total
            if (lastTime) {
                //totale generale

                //total EURO                     
                cntriga++;
                cntriga++;
                cntriga++;
                cntriga++;
                Row row13 = sheet.createRow((short) cntriga);

                Cell f72 = row13.createCell(2);
                f72.setCellStyle(style3left);
                f72.setCellValue("Total Local Currency");

                Cell f73 = row13.createCell(3);
                f73.setCellStyle(style4);
                f73.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(totaleEUROtotVolPrevYear, 2)));

                Cell f74 = row13.createCell(4);
                f74.setCellStyle(style4);
                f74.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(totaleEUROtotVolYear, 2)));

                Cell f75 = row13.createCell(5);
                f75.setCellStyle(style4);
                f75.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(totaleEUROtotVolVal, 2)));

                Cell f76 = row13.createCell(6);
                f76.setCellStyle(style4);
                if (totaleEUROtotVolPrevYear > 0 && totaleEUROtotVolYear > 0) {
                    f76.setCellValue(formatMysqltoDisplay(roundDoubleandFormat((totaleEUROtotVolVal / totaleEUROtotVolPrevYear) * 100, 2)));
                } else {
                    f76.setCellValue("0,00");
                }

                Cell f77 = row13.createCell(7);
                f77.setCellStyle(style4);
                f77.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(totaleEUROqtyPrevYear, 2)));

                Cell f78 = row13.createCell(8);
                f78.setCellStyle(style4);
                f78.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(totaleEUROqtyYear, 2)));

                Cell f79 = row13.createCell(9);
                f79.setCellStyle(style4);
                f79.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(totaleEUROqtyVal, 2)));

                Cell f80 = row13.createCell(10);
                f80.setCellStyle(style4);
                if (totaleEUROqtyPrevYear > 0 && totaleEUROqtyYear > 0) {
                    f80.setCellValue(formatMysqltoDisplay(roundDoubleandFormat((totaleEUROqtyVal / totaleEUROqtyPrevYear) * 100, 2)));
                } else {
                    f80.setCellValue("0,00");
                }

                //fine total
                //total VALUTA
                cntriga++;
                Row row15 = sheet.createRow((short) cntriga);

                Cell f442 = row15.createCell(2);
                f442.setCellStyle(style3left);
                f442.setCellValue("Total Currency");

                Cell f443 = row15.createCell(3);
                f443.setCellStyle(style4);
                f443.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(totaleVALUTAtotVolPrevYear, 2)));

                Cell f444 = row15.createCell(4);
                f444.setCellStyle(style4);
                f444.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(totaleVALUTAtotVolYear, 2)));

                Cell f445 = row15.createCell(5);
                f445.setCellStyle(style4);
                f445.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(totaleVALUTAtotVolVal, 2)));

                Cell f446 = row15.createCell(6);
                f446.setCellStyle(style4);
                if (totaleVALUTAtotVolPrevYear > 0 && totaleVALUTAtotVolYear > 0) {
                    f446.setCellValue(formatMysqltoDisplay(roundDoubleandFormat((totaleVALUTAtotVolVal / totaleVALUTAtotVolPrevYear) * 100, 2)));
                } else {
                    f446.setCellValue("0,00");
                }

                Cell f447 = row15.createCell(7);
                f447.setCellStyle(style4);
                f447.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(totaleVALUTAqtyPrevYear, 2)));

                Cell f448 = row15.createCell(8);
                f448.setCellStyle(style4);
                f448.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(totaleVALUTAqtyYear, 2)));

                Cell f449 = row15.createCell(9);
                f449.setCellStyle(style4);
                f449.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(totaleVALUTAqtyVal, 2)));

                Cell f450 = row15.createCell(10);
                f450.setCellStyle(style4);
                if (totaleVALUTAqtyPrevYear > 0 && totaleVALUTAqtyYear > 0) {
                    f450.setCellValue(formatMysqltoDisplay(roundDoubleandFormat((totaleVALUTAqtyVal / totaleVALUTAqtyPrevYear) * 100, 2)));
                } else {
                    f450.setCellValue("0,00");
                }

                //fine total
                //total
                cntriga++;
                Row row17 = sheet.createRow((short) cntriga);

                Cell f432 = row17.createCell(2);
                f432.setCellStyle(style3left);
                f432.setCellValue("Grand Total");

                Cell f433 = row17.createCell(3);
                f433.setCellStyle(style4);
                f433.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(totaleVolPrevYear, 2)));

                Cell f434 = row17.createCell(4);
                f434.setCellStyle(style4);
                f434.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(totaleVolYear, 2)));

                Cell f435 = row17.createCell(5);
                f435.setCellStyle(style4);
                f435.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(totaleVolVal, 2)));

                Cell f436 = row17.createCell(6);
                f436.setCellStyle(style4);
                if (totaleVolPrevYear > 0 && totaleVolYear > 0) {
                    f436.setCellValue(formatMysqltoDisplay(roundDoubleandFormat((totaleVolVal / totaleVolPrevYear) * 100, 2)));
                } else {
                    f436.setCellValue("0,00");
                }

                Cell f437 = row17.createCell(7);
                f437.setCellStyle(style4);
                f437.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(totaleqtyPrevYear, 2)));

                Cell f438 = row17.createCell(8);
                f438.setCellStyle(style4);
                f438.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(totaleqtyYear, 2)));

                Cell f439 = row17.createCell(9);
                f439.setCellStyle(style4);
                f439.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(totaleqtyVal, 2)));

                Cell f440 = row17.createCell(10);
                f440.setCellStyle(style4);
                if (totaleqtyPrevYear > 0 && totaleqtyYear > 0) {
                    f440.setCellValue(formatMysqltoDisplay(roundDoubleandFormat((totaleqtyVal / totaleqtyPrevYear) * 100, 2)));
                } else {
                    f440.setCellValue("0,00");
                }

                //fine total
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
            File pdffile = new File(path + generaId(50) + "C_VatRefund_CurrencyBranch.xls");
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet("C_VatRefund_CurrencyBranch");

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

            C_FreeTax_AmountBranch nctl = new C_FreeTax_AmountBranch();

            C_FreeTax_AmountBranch_value pdf = new C_FreeTax_AmountBranch_value();

            boolean firstTime = true;
            boolean lastTime = false;

////        ArrayList<String> currency=Engine.getC_Currency_FreeTax_AmountBranch();
            Db_Master dbm = new Db_Master();
            ArrayList<C_FreeTax_AmountBranch_value> dati = dbm.list_C_FreeTax_AmountBranch_value(filiali, data1, data2, br);
            ArrayList<Currency> cu = dbm.list_figures();
            int lastindex = 0;

            if (dati.size() > 0) {

                for (int i = 0; i < cu.size(); i++) {

                    ArrayList<C_FreeTax_AmountBranch_value> dati_cu = new ArrayList<>();

                    for (int j = 0; j < dati.size(); j++) {
                        if (dati.get(j).getCurrency().equals(cu.get(i).getCode())) {
                            dati_cu.add(dati.get(j));
                        }
                    }

                    if (!dati_cu.isEmpty()) {
                        lastindex = i;
                    }
                }

                int nriga = 1;
                for (int i = 0; i < cu.size(); i++) {

                    ArrayList<C_FreeTax_AmountBranch_value> dati_cu = new ArrayList<>();

                    for (int j = 0; j < dati.size(); j++) {
                        if (dati.get(j).getCurrency().equals(cu.get(i).getCode())) {
                            dati_cu.add(dati.get(j));
                        }
                    }

                    pdf.setDataDa(d3);
                    pdf.setDataA(d4);
                    pdf.setDati(dati_cu);
                    pdf.setCurrency(cu.get(i).getCode() + " " + cu.get(i).getDescrizione());

                    if (i == lastindex) {
                        lastTime = true;
                    }
                    if (!dati_cu.isEmpty()) {
                        nriga = nctl.receiptexcel(pdf, alcolonne, firstTime, lastTime, sheet, nriga, style1, style2, style3, style4, style3left, style4left, style3center);
                        firstTime = false;
                    }

                }
                dbm.closeDB();

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
