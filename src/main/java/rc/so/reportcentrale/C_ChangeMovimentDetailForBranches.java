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
import static rc.so.util.Constant.formatdataCellRate;
import static rc.so.util.Engine.insertTR;
import static rc.so.util.Utility.fd;
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
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import static org.apache.poi.hssf.usermodel.HSSFFont.FONT_ARIAL;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import static org.apache.poi.ss.usermodel.BorderStyle.THIN;
import org.apache.poi.ss.usermodel.Cell;
import static org.apache.poi.ss.usermodel.CellType.NUMERIC;
import static org.apache.poi.ss.usermodel.HorizontalAlignment.LEFT;
import static org.apache.poi.ss.usermodel.HorizontalAlignment.RIGHT;
import org.apache.poi.ss.usermodel.Row;

/**
 *
 * @author fplacanica
 */
public class C_ChangeMovimentDetailForBranches {

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
    public static float[] columnWidths2 = new float[]{20f, 40f, 40f, 30f, 30f, 30f, 30f, 30f, 30f, 30f, 30f, 30f, 30f, 30f, 30f, 30f, 30f, 30f};

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
    final String intestazionePdf = "Analysis Transactions Change For Branches Details";
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

    double tot_qta = 0, tot_amount = 0, tot_comm = 0, tot_net = 0, tot_buy = 0, tot_spread = 0, tot_commFix = 0;

    /**
     ** Constructor
     */
    public C_ChangeMovimentDetailForBranches() {

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
    public Document receipt(C_ChangeMovimentDetailForBranches_value cmfb, ArrayList<String> colonne, boolean firstTime, boolean lastTime, Document document) {

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

            //  document.add(table2);
            // document.add(sep);
            ArrayList<C_ChangeMovimentDetailForBranches_value> dati = cmfb.getDati();
            Phrase phraset;
            PdfPCell cellt;
            PdfPTable table3;

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
                if (c == 2 || c == 4 || c == 5 || c == 14 || c == 15 || c == 16) {
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

            for (int j = 0; j < dati.size(); j++) {

                C_ChangeMovimentDetailForBranches_value temp = dati.get(j);

                sep = new LineSeparator();
                sep.setOffset(-2);
                sep.setLineWidth((float) 0.5);

                PdfPTable table4 = new PdfPTable(colonne.size());
                table4.setWidths(columnWidths2);
                table4.setWidthPercentage(100);

                phraset = new Phrase();
                phraset.add(new Chunk(temp.getBranch(), f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(temp.getTransaction(), f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
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
                phraset.add(new Chunk(temp.getKind(), f3_normal));
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
                phraset.add(new Chunk(formatMysqltoDisplay(temp.getRate()), f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(temp.getAmount()), f3_normal));
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
                phraset.add(new Chunk(formatMysqltoDisplay(temp.getCom()), f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(temp.getNet()), f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(temp.getBuy()), f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(temp.getSpread()), f3_normal));
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
                phraset.add(new Chunk(temp.getCustomer(), f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_LEFT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(temp.getDelete(), f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_LEFT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(temp.getIntBook(), f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_LEFT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);

                document.add(table4);

                tot_qta += ff(temp.getQty());
                tot_amount += ff(temp.getAmount());
                tot_comm += ff(temp.getCom());
                tot_net += ff(temp.getNet());
                tot_buy += ff(temp.getBuy());
                tot_spread += ff(temp.getSpread());
                tot_commFix += ff(temp.getComFix());

            }

            tot_qta = roundDouble(tot_qta, 2);
            tot_amount = roundDouble(tot_amount, 2);
            tot_comm = roundDouble(tot_comm, 2);
            tot_net = roundDouble(tot_net, 2);
            tot_buy = roundDouble(tot_buy, 2);
            tot_spread = roundDouble(tot_spread, 2);
            tot_commFix = roundDouble(tot_commFix, 2);

            if (lastTime) {

                vuoto.setFont(f3_normal);
                document.add(vuoto);

                //Totale finale generale
                //linea totali
                PdfPTable table4 = new PdfPTable(colonne.size());
                table4.setWidths(columnWidths2);
                table4.setWidthPercentage(100);

                //linea totale 
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
                // phraset.add(new Chunk(Utility.formatMysqltoDisplay(String.valueOf(tot_qta)), f4_bold));
                phraset.add(new Chunk("", f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);

                phraset = new Phrase();
                // phraset.add(new Chunk(Utility.formatMysqltoDisplay(String.valueOf(tot_qta)), f4_bold));
                phraset.add(new Chunk("", f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(tot_amount, 2)), f4_bold));
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

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(tot_net, 2)), f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(tot_buy, 2)), f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(tot_spread, 6)), f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(tot_commFix, 2)), f4_bold));
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
                //linea totale
                ls.setLineWidth(1f);
                document.add(ls);
                //
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
     * @param allenabledbr
     * @return
     */
    public String main(String path, String d3, String d4, String data1, String data2, ArrayList<String> alcolonne, ArrayList<String> filiali,
            ArrayList<Branch> allenabledbr) {
        try {
            C_ChangeMovimentDetailForBranches nctl = new C_ChangeMovimentDetailForBranches();

            C_ChangeMovimentDetailForBranches_value pdf = new C_ChangeMovimentDetailForBranches_value();

            boolean firstTime;
            boolean lastTime;
            Db_Master db = new Db_Master();
            File pdffile = new File(path + generaId(50) + "C_ChangeMovimentDetailForBranches.pdf");
            try(OutputStream ou = new FileOutputStream(pdffile)){
            Document document = new Document(A4.rotate(), 20, 20, 20, 20);
            PdfWriter wr = getInstance(document, ou);
            document.open();

            ArrayList<C_ChangeMovimentDetailForBranches_value> dati = db.list_C_ChangeMovimentDetailForBranches_value(data1, data2, filiali, allenabledbr);

            pdf.setDataDa(d3);
            pdf.setDataA(d4);
            pdf.setDati(dati);

            firstTime = true;
            lastTime = true;

            if (dati.size() > 0) {

                document = nctl.receipt(pdf, alcolonne, firstTime, lastTime, document);
                //chiusura documento
                document.close();
                wr.close();
                ou.close();
                db.closeDB();
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
     * @param cellStylenum
     * @param style3num
     * @param style3rate
     * @return
     */
    public int receiptexcel(C_ChangeMovimentDetailForBranches_value cmfb, ArrayList<String> colonne, boolean firstTime, boolean lastTime, HSSFSheet sheet, int cntriga, HSSFCellStyle style1,
            HSSFCellStyle style2, HSSFCellStyle style3, HSSFCellStyle style4, HSSFCellStyle style3left, HSSFCellStyle style4left, HSSFCellStyle cellStylenum,
            HSSFCellStyle style3num, HSSFCellStyle style3rate) {

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

            //  document.add(table2);
            // document.add(sep);
            ArrayList<C_ChangeMovimentDetailForBranches_value> dati = cmfb.getDati();

            Row row66 = sheet.createRow((short) cntriga);

            //mi scandisco le colonne
            for (int c = 0; c < colonne.size(); c++) {
                Cell cl7 = row66.createCell(c + 1);
                cl7.setCellStyle(style3);
                if (c == 2 || c == 4 || c == 5 || c == 14 || c == 15 || c == 16) {
                    cl7.setCellStyle(style3left);
                }
                cl7.setCellValue(colonne.get(c));
            }

            for (int j = 0; j < dati.size(); j++) {

                cntriga++;

                Row row6 = sheet.createRow((short) cntriga);

                C_ChangeMovimentDetailForBranches_value temp = dati.get(j);

                Cell f1bis = row6.createCell(1);
                f1bis.setCellStyle(style4);
                f1bis.setCellValue(temp.getBranch());

                Cell f2 = row6.createCell(2);
                f2.setCellStyle(style4);
                f2.setCellValue(temp.getTransaction());

                Cell f3 = row6.createCell(3);
                f3.setCellStyle(style4left);
                f3.setCellValue(temp.getDate());

                Cell f4 = row6.createCell(4);
                f4.setCellStyle(style4);
                f4.setCellValue(temp.getUser());

                Cell f5 = row6.createCell(5);
                f5.setCellStyle(style4left);
                f5.setCellValue(temp.getCurrency());

                Cell f6 = row6.createCell(6);
                f6.setCellStyle(style4left);
                f6.setCellValue(temp.getKind());

                Cell f7 = row6.createCell(7, NUMERIC);
                f7.setCellStyle(cellStylenum);
                f7.setCellValue(fd(temp.getQty()));

                Cell f8a = row6.createCell(8, NUMERIC);
                f8a.setCellStyle(style3rate);
                f8a.setCellValue(fd(temp.getRate()));

                Cell f8 = row6.createCell(9, NUMERIC);
                f8.setCellStyle(cellStylenum);
                f8.setCellValue(fd(temp.getAmount()));

                Cell f9 = row6.createCell(10, NUMERIC);
                f9.setCellStyle(cellStylenum);
                f9.setCellValue(fd(temp.getPercCom()));

                Cell f10 = row6.createCell(11, NUMERIC);
                f10.setCellStyle(cellStylenum);
                f10.setCellValue(fd(temp.getCom()));

                Cell f11 = row6.createCell(12, NUMERIC);
                f11.setCellStyle(cellStylenum);
                f11.setCellValue(fd(temp.getNet()));

                Cell f12 = row6.createCell(13, NUMERIC);
                f12.setCellStyle(cellStylenum);
                f12.setCellValue(fd(temp.getBuy()));

                Cell f13 = row6.createCell(14, NUMERIC);
                f13.setCellStyle(cellStylenum);
                f13.setCellValue(fd(temp.getSpread()));

                Cell f14 = row6.createCell(15, NUMERIC);
                f14.setCellStyle(cellStylenum);
                f14.setCellValue(fd(temp.getComFix()));

                Cell f15 = row6.createCell(16);
                f15.setCellStyle(style4left);
                f15.setCellValue(temp.getCustomer());

                Cell f16 = row6.createCell(17);
                f16.setCellStyle(style4left);
                f16.setCellValue(temp.getDelete());

                Cell f17 = row6.createCell(18);
                f17.setCellStyle(style4left);
                f17.setCellValue(temp.getIntBook());

                tot_qta += fd(temp.getQty());
                tot_amount += fd(temp.getAmount());
                tot_comm += fd(temp.getCom());
                tot_net += fd(temp.getNet());
                tot_buy += fd(temp.getBuy());
                tot_spread += fd(temp.getSpread());
                tot_commFix += fd(temp.getComFix());

            }

            tot_qta = roundDouble(tot_qta, 2);
            tot_amount = roundDouble(tot_amount, 2);
            tot_comm = roundDouble(tot_comm, 2);
            tot_net = roundDouble(tot_net, 2);
            tot_buy = roundDouble(tot_buy, 2);
            tot_spread = roundDouble(tot_spread, 2);
            tot_commFix = roundDouble(tot_commFix, 2);

            if (lastTime) {

                cntriga++;

                Row row7 = sheet.createRow((short) cntriga);

                Cell f6 = row7.createCell(6);
                f6.setCellStyle(style3left);
                f6.setCellValue("Total");

                Cell f8 = row7.createCell(9, NUMERIC);
                f8.setCellStyle(style3num);
                f8.setCellValue(fd(roundDoubleandFormat(tot_amount, 2)));

                Cell f10 = row7.createCell(11, NUMERIC);
                f10.setCellStyle(style3num);
                f10.setCellValue(fd(roundDoubleandFormat(tot_comm, 2)));

                Cell f11 = row7.createCell(12, NUMERIC);
                f11.setCellStyle(style3num);
                f11.setCellValue(fd(roundDoubleandFormat(tot_net, 2)));

                Cell f12 = row7.createCell(13, NUMERIC);
                f12.setCellStyle(style3num);
                f12.setCellValue(fd(roundDoubleandFormat(tot_buy, 2)));

                Cell f13 = row7.createCell(14, NUMERIC);
                f13.setCellStyle(style3num);
                f13.setCellValue(fd(roundDoubleandFormat(tot_spread, 2)));

                Cell f14 = row7.createCell(15, NUMERIC);
                f14.setCellStyle(style3num);
                f14.setCellValue(fd(roundDoubleandFormat(tot_commFix, 2)));

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
    public String mainexcel(String path, String d3, String d4, String data1, String data2, ArrayList<String> alcolonne, ArrayList<String> filiali,
            ArrayList<Branch> allenabledbr) {
        try {
            File pdffile = new File(path + generaId(50) + "C_ChangeMovimentDetailForBranches.xls");
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet("C_ChangeMovimentDetailForBranches");

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

//            HSSFFont font4 = workbook.createFont();
//            font4.setFontName(HSSFFont.FONT_ARIAL);
//            font4.setFontHeightInPoints((short) 10);
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
            cellStylenum.setAlignment(RIGHT);
            cellStylenum.setBorderTop(THIN);
            cellStylenum.setBorderBottom(THIN);

            HSSFCellStyle style3num = (HSSFCellStyle) workbook.createCellStyle();
            style3num.setFont(font3);
            style3num.setAlignment(RIGHT);
            style3num.setBorderTop(THIN);
            style3num.setBorderBottom(THIN);
            style3num.setDataFormat(hssfDataFormat.getFormat(formatdataCell));

            HSSFCellStyle style3rate = (HSSFCellStyle) workbook.createCellStyle();
            style3rate.setAlignment(RIGHT);
            style3rate.setBorderTop(THIN);
            style3rate.setBorderBottom(THIN);
            style3rate.setDataFormat(hssfDataFormat.getFormat(formatdataCellRate));

            C_ChangeMovimentDetailForBranches nctl = new C_ChangeMovimentDetailForBranches();

            C_ChangeMovimentDetailForBranches_value pdf = new C_ChangeMovimentDetailForBranches_value();

            boolean firstTime;
            boolean lastTime;
            Db_Master db = new Db_Master();

            ArrayList<C_ChangeMovimentDetailForBranches_value> dati = db.list_C_ChangeMovimentDetailForBranches_value(data1, data2, filiali, allenabledbr);

            pdf.setDataDa(d3);
            pdf.setDataA(d4);
            pdf.setDati(dati);

            firstTime = true;
            lastTime = true;

            int nriga = 1;

            if (dati.size() > 0) {

                nriga = nctl.receiptexcel(pdf, alcolonne, firstTime, lastTime, sheet, nriga, style1, style2, style3, style4, style3left, style4left, cellStylenum, style3num, style3rate);
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
                sheet.autoSizeColumn(20);

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

            //chiusura documento
        } catch (Exception ex) {
            insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
        }
        return null;

    }
}
