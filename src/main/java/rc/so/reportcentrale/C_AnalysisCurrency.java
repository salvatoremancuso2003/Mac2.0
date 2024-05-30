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
import static rc.so.util.Constant.formatdataCell;
import static rc.so.util.Engine.formatBankBranchReport;
import static rc.so.util.Engine.insertTR;
import static rc.so.util.Utility.fd;
import static rc.so.util.Utility.ff;
import static rc.so.util.Utility.formatMysqltoDisplay;
import static rc.so.util.Utility.generaId;
import static rc.so.util.Utility.roundDouble;
import static rc.so.util.Utility.roundDoubleandFormat;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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
import org.apache.poi.ss.util.CellRangeAddress;
import static org.apache.poi.ss.usermodel.BorderStyle.MEDIUM;
import static org.apache.poi.ss.usermodel.BorderStyle.THIN;
import static org.apache.poi.ss.usermodel.CellType.NUMERIC;
import static org.apache.poi.ss.usermodel.HorizontalAlignment.CENTER;
import static org.apache.poi.ss.usermodel.HorizontalAlignment.LEFT;
import static org.apache.poi.ss.usermodel.HorizontalAlignment.RIGHT;
import static org.apache.poi.ss.util.RegionUtil.setBorderBottom;
import static org.apache.poi.ss.util.RegionUtil.setBorderTop;

/**
 *
 * @author fplacanica
 */
public class C_AnalysisCurrency {

    //column

    /**
     *
     */
    public static final float[] columnWidths0 = new float[]{30f, 80f};

    /**
     *
     */
    public static final float[] columnWidths1 = new float[]{60f, 30f};

    /**
     *
     */
    public static float[] columnWidths2 = new float[]{10f, 20f, 25f, 25f, 20f, 20f, 10f, 20f, 20f, 20f, 10f, 10f};

    /**
     *
     */
    public static float[] columnWidths3 = new float[]{10f, 15f, 15f, 15f, 15f, 15f, 15f, 15f, 15f, 15f, 15f, 15f, 15f, 15f, 15f, 10f, 10f, 10f, 10f};

    /**
     *
     */
    public static float[] columnWidths3bis = new float[]{18f, 12f, 12f, 15f, 15f, 15f, 15f, 15f, 15f, 12f, 12f, 15f, 15f, 15f, 15f, 10f, 10f, 10f, 10f};

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
    final String intestazionePdf = "Analysis Currency";
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

    Font f0_bold, f1_bold, f2_bold, f1_normal, f2_normal, f3_normal, f3_bold, f4_bold;

    float tot_qta = 0, tot_amount = 0, tot_comm = 0, tot_net = 0, tot_buy = 0, tot_spread = 0, tot_commFix = 0;

    /**
     * C_AnalysisCurrency
     */
    public C_AnalysisCurrency() {

        this.f0_bold = getFont(HELVETICA, WINANSI, 14.04f, BOLD);
        this.f1_bold = getFont(HELVETICA, WINANSI, 9.96f, BOLD);
        this.f2_bold = getFont(HELVETICA, WINANSI, 8f, BOLD);
        this.f3_bold = getFont(HELVETICA, WINANSI, 8f, BOLDITALIC);
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
    public Document receipt(C_AnalysisCurrency_value cmfb, ArrayList<String> colonne, boolean firstTime, boolean lastTime, Document document) {

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

            Phrase phraset;
            PdfPCell cellt;
            PdfPTable table3;

            double qta = 0, amount = 0;

            qta = 0;
            amount = 0;

            double totalFromBranch = 0;
            double totalBuy = 0;
            double totalFromBank = 0;
            double totalOpenCloseError = 0;
            double totBuy = 0;
            double totalAmountBuy = 0;
            double totalToBranch = 0;
            double totalSell = 0;
            double totalToBanck = 0;
            double totalAmountSell = 0;

            LineSeparator sep = new LineSeparator();
            sep.setOffset(-2);
            sep.setLineWidth((float) 0.5);

            PdfPTable table4 = new PdfPTable(2);
            table4.setWidths(columnWidths0);
            table4.setWidthPercentage(100);

            phraset = new Phrase();
            phraset.add(new Chunk(cmfb.getDe_filiale(), f3_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_LEFT);
            cellt.setBorder(BOTTOM);
            cellt.setBackgroundColor(new BaseColor(242, 242, 242));
            table4.addCell(cellt);

            phraset = new Phrase();
            phraset.add(new Chunk("", f3_normal));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_LEFT);
            cellt.setBorder(BOTTOM);
            cellt.setBackgroundColor(new BaseColor(242, 242, 242));
            table4.addCell(cellt);

            document.add(table4);

            ArrayList<String> colonne2 = new ArrayList<>();

            colonne2.add("");
            colonne2.add("Tot");
            colonne2.add("Amount");
            colonne2.add("Tot");
            colonne2.add("Amount");
            colonne2.add("Tot");
            colonne2.add("Amount");
            colonne2.add("Tot");
            colonne2.add("Amount");
            colonne2.add("");
            colonne2.add("");
            colonne2.add("Tot");
            colonne2.add("Amount");
            colonne2.add("Tot");
            colonne2.add("Amount");
            colonne2.add("Tot");
            colonne2.add("Amount");
            colonne2.add("");
            colonne2.add("");

            PdfPTable table2 = new PdfPTable(colonne2.size());
            table2.setWidths(columnWidths3);
            table2.setWidthPercentage(100);

            table3 = new PdfPTable(colonne2.size());
            table3.setWidths(columnWidths3);
            table3.setWidthPercentage(100);

            PdfPCell[] list = new PdfPCell[colonne2.size()];
            //mi scandisco le colonne
            int d = 0;
            for (int c = 0; c < colonne.size(); c++) {
                Phrase phraset1 = new Phrase();
                phraset1.add(new Chunk(colonne.get(c), f4_bold));
                PdfPCell cellt1 = new PdfPCell(phraset1);
                cellt1.setHorizontalAlignment(ALIGN_RIGHT);
                cellt1.setBorder(BOTTOM | TOP);
                cellt1.setBorder(BOX);
                cellt1.setFixedHeight(15f);
                cellt1.setBackgroundColor(LIGHT_GRAY);
                cellt1.setBorderWidth(0.7f);
                if (c != 0 && c != 5 && c != 6 && c != 10 && c != 11) {
                    cellt1.setColspan(2);
                    cellt1.setHorizontalAlignment(ALIGN_CENTER);
                }
                list[c] = cellt1;
                table3.addCell(cellt1);
            }

//            for (int z = 0; z < list.length; z++) {
//                PdfPCell temp = (PdfPCell) (list[z]);
//                table3.addCell(temp);
//            }
            document.add(table3);

            //intestazione seconda linea
            table2 = new PdfPTable(colonne2.size());
            table2.setWidths(columnWidths3);
            table2.setWidthPercentage(100);

            list = new PdfPCell[colonne2.size()];
            //mi scandisco le colonne
            for (int c = 0; c < colonne2.size(); c++) {
                Phrase phraset1 = new Phrase();
                phraset1.add(new Chunk(colonne2.get(c), f4_bold));
                PdfPCell cellt1 = new PdfPCell(phraset1);
                cellt1.setHorizontalAlignment(ALIGN_RIGHT);
                cellt1.setBorder(BOTTOM | TOP);
                cellt1.setFixedHeight(15f);
                cellt1.setBackgroundColor(new BaseColor(242, 242, 242));
                cellt1.setBorderWidth(0.7f);

                list[c] = cellt1;
            }

            table3 = new PdfPTable(colonne2.size());
            table3.setWidths(columnWidths3);
            table3.setWidthPercentage(100);

            for (PdfPCell list1 : list) {
                PdfPCell temp = (PdfPCell) (list1);
                table3.addCell(temp);
            }

            document.add(table3);
            //fine intestazione seconda linea

            ArrayList<C_AnalysisCurrency_value> lista = cmfb.getDati();

            for (int x = 0; x < lista.size(); x++) {

                C_AnalysisCurrency_value temp = (C_AnalysisCurrency_value) lista.get(x);

                sep = new LineSeparator();
                sep.setOffset(-2);
                sep.setLineWidth((float) 0.5);

                table4 = new PdfPTable(colonne2.size());
                table4.setWidths(columnWidths3);
                table4.setWidthPercentage(100);

                phraset = new Phrase();
                phraset.add(new Chunk(temp.getCurrency(), f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_LEFT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(temp.getFromBranch_qty()), f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(temp.getFromBranch_amount()), f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(temp.getBuy_qty()), f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(temp.getBuy_amount()), f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(temp.getFromBank_qty()), f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(temp.getFromBank_amount()), f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(temp.getOpenCloseError_qty()), f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(temp.getOpenCloseError_amount()), f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(temp.getTotalIn()), f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(temp.getTotalAmountIn()), f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(temp.getToBranch_qty()), f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(temp.getToBranch_amount()), f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(temp.getSell_qty()), f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(temp.getSell_amount()), f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(temp.getToBank_qty()), f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(temp.getToBank_amount()), f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(temp.getTotOut()), f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(temp.getTotAmountOut()), f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);

                totalFromBranch += ff(temp.getFromBranch_amount());
                totalBuy += ff(temp.getBuy_amount());
                totalFromBank += ff(temp.getFromBank_amount());
                totalOpenCloseError += ff(temp.getOpenCloseError_amount());
                totBuy += ff(temp.getTotalIn());
                totalAmountBuy += ff(temp.getTotalAmountIn());
                totalToBranch += ff(temp.getToBranch_amount());
                totalSell += ff(temp.getSell_amount());
                totalToBanck += ff(temp.getToBank_amount());
                totalAmountSell += ff(temp.getTotAmountOut());

                document.add(table4);

            }

            totalFromBranch = roundDouble(totalFromBranch, 2);
            totalBuy = roundDouble(totalBuy, 2);
            totalFromBank = roundDouble(totalFromBank, 2);
            totalOpenCloseError = roundDouble(totalOpenCloseError, 2);
            totBuy = roundDouble(totBuy, 2);
            totalAmountBuy = roundDouble(totalAmountBuy, 2);
            totalToBranch = roundDouble(totalToBranch, 2);
            totalSell = roundDouble(totalSell, 2);
            totalToBanck = roundDouble(totalToBanck, 2);
            totalAmountSell = roundDouble(totalAmountSell, 2);

            String totalFromBranch_s = roundDoubleandFormat(totalFromBranch, 2);
            String totalBuy_s = roundDoubleandFormat(totalBuy, 2);
            String totalFromBank_s = roundDoubleandFormat(totalFromBank, 2);
            String totalOpenCloseError_s = roundDoubleandFormat(totalOpenCloseError, 2);
            String totBuy_s = roundDoubleandFormat(totBuy, 2);
            String totalAmountBuy_s = roundDoubleandFormat(totalAmountBuy, 2);
            String totalToBranch_s = roundDoubleandFormat(totalToBranch, 2);
            String totalSell_s = roundDoubleandFormat(totalSell, 2);
            String totalToBanck_s = roundDoubleandFormat(totalToBanck, 2);
            String totalAmountSell_s = roundDoubleandFormat(totalAmountSell, 2);
            //linea totale per valuta
            sep = new LineSeparator();
            sep.setLineWidth(0.7f);
            document.add(sep);

            table4 = new PdfPTable(colonne2.size());
            table4.setWidths(columnWidths3);
            table4.setWidthPercentage(100);

            phraset = new Phrase();
            phraset.add(new Chunk("Totale", f4_bold));
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
            phraset.add(new Chunk((formatMysqltoDisplay(totalFromBranch_s)), f4_bold));
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
            phraset.add(new Chunk((formatMysqltoDisplay(totalBuy_s)), f4_bold));
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
            phraset.add(new Chunk((formatMysqltoDisplay(totalFromBank_s)), f4_bold));
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
            phraset.add(new Chunk((formatMysqltoDisplay(totalOpenCloseError_s)), f4_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setBorder(BOTTOM);
            table4.addCell(cellt);

            phraset = new Phrase();
            //phraset.add(new Chunk(Util.formatValue(String.valueOf(totBuy)), f4_bold));
            phraset.add(new Chunk("", f4_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setBorder(BOTTOM);
            table4.addCell(cellt);

            phraset = new Phrase();
            phraset.add(new Chunk((formatMysqltoDisplay(totalAmountBuy_s)), f4_bold));
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
            phraset.add(new Chunk((formatMysqltoDisplay(totalToBranch_s)), f4_bold));
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
            phraset.add(new Chunk((formatMysqltoDisplay(totalSell_s)), f4_bold));
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
            phraset.add(new Chunk((formatMysqltoDisplay(totalToBanck_s)), f4_bold));
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
            phraset.add(new Chunk((formatMysqltoDisplay(totalAmountSell_s)), f4_bold));
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

//                qta += ff(temp.getQty());
//                amount += ff(temp.getTotal());
            document.add(table4);

            //fine totale
            vuoto.setFont(f3_normal);
            document.add(vuoto);

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
    public String main(String path, String d3, String d4, String data1, String data2, ArrayList<String> alcolonne, ArrayList<String> filiali, ArrayList<Branch> br) {

        try {
            C_AnalysisCurrency nctl = new C_AnalysisCurrency();
//
            C_AnalysisCurrency_value pdf = new C_AnalysisCurrency_value();

            boolean firstTime = true;
            boolean lastTime = false;
             
            File pdffile = new File(path + generaId(50) + "C_AnalysisCurrency.pdf");
             try(OutputStream ou = new FileOutputStream(pdffile)){
            Document document = new Document(A4.rotate(), 20, 20, 20, 20);
            PdfWriter wr = getInstance(document, ou);
            document.open();

            Db_Master dbm = new Db_Master();

            boolean ris = false;

            //ciclo per ogni filiale
            for (int i = 0; i < filiali.size(); i++) {
                ArrayList<C_AnalysisCurrency_value> dati = dbm.list_C_AnalysisCurrency_value(data1, data2, filiali.get(i), br);

                if (dati.size() > 0) {
                    ris = true;
                }

                pdf.setDe_filiale(filiali.get(i) + " " + formatBankBranchReport(filiali.get(i), "BR", null, br));
                pdf.setDataDa(d3);
                pdf.setDataA(d4);
                pdf.setDati(dati);

                if (i == filiali.size() - 1) {
                    lastTime = true;
                }

                document = nctl.receipt(pdf, alcolonne, firstTime, lastTime, document);
                firstTime = false;

            }
            dbm.closeDB();

            if (ris) {

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
     * @param style3bis
     * @param cellStylenum
     * @param style3num
     * @return
     */
    public int receiptexcel(C_AnalysisCurrency_value cmfb, ArrayList<String> colonne, boolean firstTime, boolean lastTime,
            HSSFSheet sheet, int cntriga, HSSFCellStyle style1, HSSFCellStyle style2, HSSFCellStyle style3, HSSFCellStyle style4,
            HSSFCellStyle style3left, HSSFCellStyle style4left, HSSFCellStyle style3center, HSSFCellStyle style3bis,
            HSSFCellStyle cellStylenum, HSSFCellStyle style3num) {

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

            float qta = 0, amount = 0;
            double totalFromBranch = 0;
            double totalBuy = 0;
            double totalFromBank = 0;
            double totalOpenCloseError = 0;
            double totBuy = 0;
            double totalAmountBuy = 0;
            double totalToBranch = 0;
            double totalSell = 0;
            double totalToBanck = 0;
            double totalAmountSell = 0;

            Row row = sheet.createRow((short) cntriga);

            Cell cl = row.createCell(1);
            cl.setCellStyle(style1);
            cl.setCellValue(cmfb.getDe_filiale());

            cntriga++;
            cntriga++;

            Row row66 = sheet.createRow((short) cntriga);

            //mi scandisco le colonne
            int z = 1;
            for (int c = 0; c < colonne.size(); c++) {
                boolean merged = false;
                if (c != 0 && c != 5 && c != 6 && c != 10 && c != 11) {
                    merged = true;
                }

                Cell cl7 = row66.createCell(z);
                cl7.setCellStyle(style3center);
                if (!merged) {
                    cl7.setCellStyle(style3bis);
                }
                if (c == 0) {
                    cl7.setCellStyle(style3left);
                }
                cl7.setCellValue(colonne.get(c));

                if (merged) {
                    z = z + 2;
                } else {
                    z++;
                }

            }

            CellRangeAddress cellRangeAddress = new CellRangeAddress(cntriga, cntriga, 2, 3);
            setBorderTop(MEDIUM, cellRangeAddress, sheet);
            setBorderBottom(MEDIUM, cellRangeAddress, sheet);
            sheet.addMergedRegion(cellRangeAddress);
            cellRangeAddress = new CellRangeAddress(cntriga, cntriga, 4, 5);
            setBorderTop(MEDIUM, cellRangeAddress, sheet);
            setBorderBottom(MEDIUM, cellRangeAddress, sheet);
            sheet.addMergedRegion(cellRangeAddress);
            cellRangeAddress = new CellRangeAddress(cntriga, cntriga, 6, 7);
            setBorderTop(MEDIUM, cellRangeAddress, sheet);
            setBorderBottom(MEDIUM, cellRangeAddress, sheet);
            sheet.addMergedRegion(cellRangeAddress);
            cellRangeAddress = new CellRangeAddress(cntriga, cntriga, 8, 9);
            setBorderTop(MEDIUM, cellRangeAddress, sheet);
            setBorderBottom(MEDIUM, cellRangeAddress, sheet);
            sheet.addMergedRegion(cellRangeAddress);
            cellRangeAddress = new CellRangeAddress(cntriga, cntriga, 12, 13);
            setBorderTop(MEDIUM, cellRangeAddress, sheet);
            setBorderBottom(MEDIUM, cellRangeAddress, sheet);
            sheet.addMergedRegion(cellRangeAddress);
            cellRangeAddress = new CellRangeAddress(cntriga, cntriga, 14, 15);
            setBorderTop(MEDIUM, cellRangeAddress, sheet);
            setBorderBottom(MEDIUM, cellRangeAddress, sheet);
            sheet.addMergedRegion(cellRangeAddress);
            cellRangeAddress = new CellRangeAddress(cntriga, cntriga, 16, 17);
            setBorderTop(MEDIUM, cellRangeAddress, sheet);
            setBorderBottom(MEDIUM, cellRangeAddress, sheet);
            sheet.addMergedRegion(cellRangeAddress);

            cntriga++;
            cntriga++;

            //intestazione seconda linea
            ArrayList<String> colonne2 = new ArrayList<>();

            colonne2.add("");
            colonne2.add("Tot");
            colonne2.add("Amount");
            colonne2.add("Tot");
            colonne2.add("Amount");
            colonne2.add("Tot");
            colonne2.add("Amount");
            colonne2.add("Tot");
            colonne2.add("Amount");
            colonne2.add("");
            colonne2.add("");
            colonne2.add("Tot");
            colonne2.add("Amount");
            colonne2.add("Tot");
            colonne2.add("Amount");
            colonne2.add("Tot");
            colonne2.add("Amount");
            colonne2.add("");
            colonne2.add("");

            Row row77 = sheet.createRow((short) cntriga);
            //mi scandisco le colonne
            for (int c = 0; c < colonne2.size(); c++) {
                Cell cl7 = row77.createCell(c + 1);
                cl7.setCellStyle(style3);
                if (c == 0) {
                    cl7.setCellStyle(style3left);
                }
                cl7.setCellValue(colonne2.get(c));
            }

            //fine intestazione seconda linea
            ArrayList<C_AnalysisCurrency_value> lista = cmfb.getDati();

            for (int x = 0; x < lista.size(); x++) {

                cntriga++;

                Row row6 = sheet.createRow((short) cntriga);

                C_AnalysisCurrency_value temp = (C_AnalysisCurrency_value) lista.get(x);

                Cell f1bis = row6.createCell(1);
                f1bis.setCellStyle(style4left);
                f1bis.setCellValue(temp.getCurrency());

                Cell f2 = row6.createCell(2, NUMERIC);
                f2.setCellStyle(cellStylenum);
                f2.setCellValue(fd(temp.getFromBranch_qty()));

                Cell f3 = row6.createCell(3, NUMERIC);
                f3.setCellStyle(cellStylenum);
                f3.setCellValue(fd(temp.getFromBranch_amount()));

                Cell f4 = row6.createCell(4, NUMERIC);
                f4.setCellStyle(cellStylenum);
                f4.setCellValue(fd(temp.getBuy_qty()));

                Cell f5 = row6.createCell(5, NUMERIC);
                f5.setCellStyle(cellStylenum);
                f5.setCellValue(fd(temp.getBuy_amount()));

                Cell f6 = row6.createCell(6, NUMERIC);
                f6.setCellStyle(cellStylenum);
                f6.setCellValue(fd(temp.getFromBank_qty()));

                Cell f7 = row6.createCell(7, NUMERIC);
                f7.setCellStyle(cellStylenum);
                f7.setCellValue(fd(temp.getFromBank_amount()));

                Cell f8 = row6.createCell(8, NUMERIC);
                f8.setCellStyle(cellStylenum);
                f8.setCellValue(fd(temp.getOpenCloseError_qty()));

                Cell f9 = row6.createCell(9, NUMERIC);
                f9.setCellStyle(cellStylenum);
                f9.setCellValue(fd(temp.getOpenCloseError_amount()));

                Cell f10 = row6.createCell(10, NUMERIC);
                f10.setCellStyle(cellStylenum);
                f10.setCellValue(fd(temp.getTotalIn()));

                Cell f11 = row6.createCell(11, NUMERIC);
                f11.setCellStyle(cellStylenum);
                f11.setCellValue(fd(temp.getTotalAmountIn()));

                Cell f12 = row6.createCell(12, NUMERIC);
                f12.setCellStyle(cellStylenum);
                f12.setCellValue(fd(temp.getToBranch_qty()));

                Cell f13 = row6.createCell(13, NUMERIC);
                f13.setCellStyle(cellStylenum);
                f13.setCellValue(fd(temp.getToBranch_amount()));

                Cell f14 = row6.createCell(14, NUMERIC);
                f14.setCellStyle(cellStylenum);
                f14.setCellValue(fd(temp.getSell_qty()));

                Cell f15 = row6.createCell(15, NUMERIC);
                f15.setCellStyle(cellStylenum);
                f15.setCellValue(fd(temp.getSell_amount()));

                Cell f16 = row6.createCell(16, NUMERIC);
                f16.setCellStyle(cellStylenum);
                f16.setCellValue(fd(temp.getToBank_qty()));

                Cell f17 = row6.createCell(17, NUMERIC);
                f17.setCellStyle(cellStylenum);
                f17.setCellValue(fd(temp.getToBank_amount()));

                Cell f18 = row6.createCell(18, NUMERIC);
                f18.setCellStyle(cellStylenum);
                f18.setCellValue(fd(temp.getTotOut()));

                Cell f19 = row6.createCell(19, NUMERIC);
                f19.setCellStyle(cellStylenum);
                f19.setCellValue(fd(temp.getTotAmountOut()));

                totalFromBranch += fd(temp.getFromBranch_amount());
                totalBuy += fd(temp.getBuy_amount());
                totalFromBank += fd(temp.getFromBank_amount());
                totalOpenCloseError += fd(temp.getOpenCloseError_amount());
                totBuy += fd(temp.getTotalIn());
                totalAmountBuy += fd(temp.getTotalAmountIn());
                totalToBranch += fd(temp.getToBranch_amount());
                totalSell += fd(temp.getSell_amount());
                totalToBanck += fd(temp.getToBank_amount());
                totalAmountSell += fd(temp.getTotAmountOut());

            }

            totalFromBranch = roundDouble(totalFromBranch, 2);
            totalBuy = roundDouble(totalBuy, 2);
            totalFromBank = roundDouble(totalFromBank, 2);
            totalOpenCloseError = roundDouble(totalOpenCloseError, 2);
            totBuy = roundDouble(totBuy, 2);
            totalAmountBuy = roundDouble(totalAmountBuy, 2);
            totalToBranch = roundDouble(totalToBranch, 2);
            totalSell = roundDouble(totalSell, 2);
            totalToBanck = roundDouble(totalToBanck, 2);
            totalAmountSell = roundDouble(totalAmountSell, 2);

            String totalFromBranch_s = roundDoubleandFormat(totalFromBranch, 2);
            String totalBuy_s = roundDoubleandFormat(totalBuy, 2);
            String totalFromBank_s = roundDoubleandFormat(totalFromBank, 2);
            String totalOpenCloseError_s = roundDoubleandFormat(totalOpenCloseError, 2);
            String totBuy_s = roundDoubleandFormat(totBuy, 2);
            String totalAmountBuy_s = roundDoubleandFormat(totalAmountBuy, 2);
            String totalToBranch_s = roundDoubleandFormat(totalToBranch, 2);
            String totalSell_s = roundDoubleandFormat(totalSell, 2);
            String totalToBanck_s = roundDoubleandFormat(totalToBanck, 2);
            String totalAmountSell_s = roundDoubleandFormat(totalAmountSell, 2);
//            

            cntriga++;

            Row row7 = sheet.createRow((short) cntriga);

            //linea totale per valuta
            Cell f1 = row7.createCell(1);
            f1.setCellStyle(style3left);
            f1.setCellValue("Totale");

            Cell f3 = row7.createCell(3, NUMERIC);
            f3.setCellStyle(style3num);
            f3.setCellValue(((fd(totalFromBranch_s))));

            Cell f5 = row7.createCell(5, NUMERIC);
            f5.setCellStyle(style3num);
            f5.setCellValue(((fd(totalBuy_s))));

            Cell f7 = row7.createCell(7, NUMERIC);
            f7.setCellStyle(style3num);
            f7.setCellValue(((fd(totalFromBank_s))));

            Cell f9 = row7.createCell(9, NUMERIC);
            f9.setCellStyle(style3num);
            f9.setCellValue((fd(totalOpenCloseError_s)));

            Cell f11 = row7.createCell(11, NUMERIC);
            f11.setCellStyle(style3num);
            f11.setCellValue((fd(totalAmountBuy_s)));

            Cell f13 = row7.createCell(13, NUMERIC);
            f13.setCellStyle(style3num);
            f13.setCellValue((fd(totalToBranch_s)));

            Cell f15 = row7.createCell(15, NUMERIC);
            f15.setCellStyle(style3num);
            f15.setCellValue((fd(totalSell_s)));

            Cell f17 = row7.createCell(17, NUMERIC);
            f17.setCellStyle(style3num);
            f17.setCellValue((fd(totalToBanck_s)));

            Cell f19 = row7.createCell(19, NUMERIC);
            f19.setCellStyle(style3num);
            f19.setCellValue((fd(totalAmountSell_s)));
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
     * @param d3
     * @param d4
     * @param data1
     * @param data2
     * @param alcolonne
     * @param filiali
     * @param br
     * @return
     */
    public String mainexcel(String path, String d3, String d4, String data1, String data2, ArrayList<String> alcolonne, ArrayList<String> filiali, ArrayList<Branch> br) {
        try {
            File pdffile = new File(path + generaId(50) + "C_AnalysisCurrency.xls");
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet("C_AnalysisCurrency");

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
            style3center.setBorderTop(MEDIUM);
            style3center.setBorderBottom(MEDIUM);
            style3center.setBorderLeft(MEDIUM);
            style3center.setBorderRight(MEDIUM);

            HSSFCellStyle style3bis = (HSSFCellStyle) workbook.createCellStyle();
            style3bis.setFont(font3);
            style3bis.setAlignment(RIGHT);
            style3bis.setBorderTop(MEDIUM);
            style3bis.setBorderBottom(MEDIUM);
            style3bis.setBorderLeft(MEDIUM);
            style3bis.setBorderRight(MEDIUM);

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

            HSSFCellStyle cellStylenum = (HSSFCellStyle) workbook.createCellStyle();
            HSSFDataFormat hssfDataFormat = (HSSFDataFormat) workbook.createDataFormat();
            cellStylenum.setDataFormat(hssfDataFormat.getFormat(formatdataCell));
            cellStylenum.setAlignment(CENTER);
            cellStylenum.setBorderTop(THIN);
            cellStylenum.setBorderBottom(THIN);
            cellStylenum.setBorderLeft(THIN);
            cellStylenum.setBorderRight(THIN);

            HSSFCellStyle style3num = (HSSFCellStyle) workbook.createCellStyle();
            style3num.setFont(font3);
            style3num.setAlignment(RIGHT);
            style3num.setBorderTop(THIN);
            style3num.setBorderBottom(THIN);
            style3num.setDataFormat(hssfDataFormat.getFormat(formatdataCell));

            //MAIN
            C_AnalysisCurrency nctl = new C_AnalysisCurrency();
//
            C_AnalysisCurrency_value pdf = new C_AnalysisCurrency_value();

            boolean firstTime = true;
            boolean lastTime = false;

            Document document = null;

            Db_Master dbm = new Db_Master();
            int nriga = 1;
            //ciclo per ogni filiale
            for (int i = 0; i < filiali.size(); i++) {
                ArrayList<C_AnalysisCurrency_value> dati = dbm.list_C_AnalysisCurrency_value(data1, data2, filiali.get(i), br);

                pdf.setDe_filiale(filiali.get(i) + " " + formatBankBranchReport(filiali.get(i), "BR", null, br));
                pdf.setDataDa(d3);
                pdf.setDataA(d4);
                pdf.setDati(dati);

                if (i == filiali.size() - 1) {
                    lastTime = true;
                }

                nriga = nctl.receiptexcel(pdf, alcolonne, firstTime, lastTime, sheet, nriga, style1, style2, style3,
                        style4, style3left, style4left, style3center, style3bis, cellStylenum, style3num);
                firstTime = false;

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
        } catch (IOException ex) {
            insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
        }
        return null;

    }

    /**
     *
     * @param valuta
     * @param dati
     * @return
     */
    public ArrayList<C_AnalysisCurrency_value> getListCurrency(String valuta, ArrayList<C_AnalysisCurrency_value> dati) {
        ArrayList<C_AnalysisCurrency_value> list = new ArrayList<>();
        for (int i = 0; i < dati.size(); i++) {
            C_AnalysisCurrency_value temp = dati.get(i);
            if (temp.getCurrency().equals(valuta)) {
                list.add(dati.get(i));
            }
        }
        return list;
    }

}
