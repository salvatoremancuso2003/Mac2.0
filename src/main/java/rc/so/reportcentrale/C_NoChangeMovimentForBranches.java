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
import static rc.so.util.Engine.formatBankBranchReport;
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
import static java.lang.Integer.parseInt;
import static java.lang.Thread.currentThread;
import java.util.ArrayList;
import static org.apache.commons.codec.binary.Base64.encodeBase64;
import static org.apache.commons.io.FileUtils.readFileToByteArray;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import static org.apache.poi.hssf.usermodel.HSSFFont.FONT_ARIAL;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import static org.apache.poi.ss.usermodel.BorderStyle.THIN;
import org.apache.poi.ss.usermodel.Cell;
import static org.apache.poi.ss.usermodel.HorizontalAlignment.LEFT;
import static org.apache.poi.ss.usermodel.HorizontalAlignment.RIGHT;
import org.apache.poi.ss.usermodel.Row;

/**
 *
 * @author fplacanica
 */
public class C_NoChangeMovimentForBranches {

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
    public static float[] columnWidths2 = new float[]{10f, 40f, 30f, 30f, 30f};

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
    final String intestazionePdf = "Analysis Transactions No Change For Branches";
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

    int totaleTransazioni = 0;
    int totaleQuantita = 0;
    double totaleGenerale = 0;

    /**
     ** Constructor
     */
    public C_NoChangeMovimentForBranches() {

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
     * @param nctl
     * @param colonne
     * @param firstTime
     * @param lastTime
     * @param document
     * @return
     */
    public Document receipt(C_NoChangeMovimentForBranches_value nctl, ArrayList<String> colonne, boolean firstTime, boolean lastTime, Document document) {

        try {

            if (firstTime) {
                PdfPTable table = new PdfPTable(2);
                table.setWidths(columnWidths0);
                table.setWidthPercentage(100);
                Phrase phrase1 = new Phrase();
                phrase1.add(new Chunk(intestazionePdf + " From " + nctl.getDataDa() + " to " + nctl.getDataA(), f3_bold));
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
            ArrayList<C_NoChangeMovimentForBranches_value> dati = nctl.getDati();
            Phrase phraset;
            PdfPCell cellt;
            PdfPTable table3;

            boolean ft = true;
            double totale = 0;
            int transazioni = 0;
            int quantita = 0;

            ArrayList<String> kind = new ArrayList<>();
            for (int x = 0; x < dati.size(); x++) {
                C_NoChangeMovimentForBranches_value temp = dati.get(x);
                if (!kind.contains(temp.getKind())) {
                    kind.add(temp.getKind());
                }
            }

            // scorro per kind
            for (int j = 0; j < kind.size(); j++) {

                if (ft) {

                    LineSeparator sep = new LineSeparator();
                    sep.setOffset(-2);
                    sep.setLineWidth((float) 0.5);

                    PdfPTable table4 = new PdfPTable(2);
                    table4.setWidths(columnWidths0);
                    table4.setWidthPercentage(100);

                    phraset = new Phrase();
                    phraset.add(new Chunk("\n " + nctl.getId_filiale() + " " + nctl.getDe_filiale(), f3_normal));
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
                        if (c == 0 || c == 1) {
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
                    ft = false;
                }

                LineSeparator sep = new LineSeparator();
                sep.setOffset(-2);
                sep.setLineWidth((float) 0.5);

                PdfPTable table4 = new PdfPTable(2);
                table4.setWidths(columnWidths0);
                table4.setWidthPercentage(100);

                phraset = new Phrase();
                phraset.add(new Chunk(kind.get(j), f4_bold));
                PdfPCell cellt1 = new PdfPCell(phraset);
                cellt1.setHorizontalAlignment(ALIGN_LEFT);
                cellt1.setBorder(BOTTOM | TOP);
                cellt1.setFixedHeight(15f);
                cellt1.setBackgroundColor(new BaseColor(242, 242, 242));
                cellt1.setBorderWidth(0.7f);
                table4.addCell(cellt1);

                phraset = new Phrase();
                phraset.add(new Chunk("", f4_bold));
                cellt1 = new PdfPCell(phraset);
                cellt1.setHorizontalAlignment(ALIGN_LEFT);
                cellt1.setBorder(BOTTOM | TOP);
                cellt1.setFixedHeight(15f);
                cellt1.setBackgroundColor(new BaseColor(242, 242, 242));
                cellt1.setBorderWidth(0.7f);
                table4.addCell(cellt1);

                document.add(table4);

                ArrayList<C_NoChangeMovimentForBranches_value> listTemp = getListKind(kind.get(j), dati);

                ArrayList<String> category = new ArrayList<>();
                for (int i = 0; i < listTemp.size(); i++) {
                    C_NoChangeMovimentForBranches_value temp = (C_NoChangeMovimentForBranches_value) listTemp.get(i);
                    if (!category.contains(temp.getCategory())) {
                        category.add(temp.getCategory());
                    }
                }

                double totQt = 0;
                int totTrans = 0;
                double tot = 0;

                for (int a = 0; a < category.size(); a++) {

                    //intestazione Categorie
                    sep = new LineSeparator();
                    sep.setOffset(-2);
                    sep.setLineWidth((float) 0.5);

                    table4 = new PdfPTable(2);
                    table4.setWidths(columnWidths0);
                    table4.setWidthPercentage(100);

                    phraset = new Phrase();
                    phraset.add(new Chunk(category.get(a), f4_bold));
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

                    for (int y = 0; y < listTemp.size(); y++) {

                        C_NoChangeMovimentForBranches_value temp = (C_NoChangeMovimentForBranches_value) listTemp.get(y);
                        if (temp.getCategory().equals(category.get(a))) {

                            table3 = new PdfPTable(colonne.size());
                            table3.setWidths(columnWidths2);
                            table3.setWidthPercentage(100);

                            phraset = new Phrase();
                            phraset.add(new Chunk("", f3_normal));
                            cellt = new PdfPCell(phraset);
                            cellt.setHorizontalAlignment(ALIGN_RIGHT);
                            cellt.setBorder(BOTTOM);
                            table3.addCell(cellt);

                            phraset = new Phrase();
                            phraset.add(new Chunk(temp.getCausal(), f3_normal));
                            cellt = new PdfPCell(phraset);
                            cellt.setHorizontalAlignment(ALIGN_LEFT);
                            cellt.setBorder(BOTTOM);
                            table3.addCell(cellt);

                            phraset = new Phrase();
                            phraset.add(new Chunk(temp.getnTrans(), f3_normal));
                            cellt = new PdfPCell(phraset);
                            cellt.setHorizontalAlignment(ALIGN_RIGHT);
                            cellt.setBorder(BOTTOM);
                            table3.addCell(cellt);

                            phraset = new Phrase();
                            phraset.add(new Chunk(formatMysqltoDisplay(temp.getQt()), f3_normal));
                            cellt = new PdfPCell(phraset);
                            cellt.setHorizontalAlignment(ALIGN_RIGHT);
                            cellt.setBorder(BOTTOM);
                            table3.addCell(cellt);

                            phraset = new Phrase();
                            phraset.add(new Chunk(formatMysqltoDisplay(temp.getTotal()), f3_normal));
                            cellt = new PdfPCell(phraset);
                            cellt.setHorizontalAlignment(ALIGN_RIGHT);
                            cellt.setBorder(BOTTOM);
                            table3.addCell(cellt);

                            document.add(table3);

                            if (!temp.getQt().equals("-")) {
                                totQt += ff(temp.getQt());
                            }

                            if (!temp.getnTrans().equals("-")) {
                                totTrans += parseInt(temp.getnTrans());
                            }

                            if (!temp.getTotal().equals("-")) {
                                tot += ff(temp.getTotal());
                            }

                        }
                    }

                    //fine intestazione Categorie
                }

                totQt = roundDouble(totQt, 2);

                tot = roundDouble(tot, 2);

                //linea totale parziale per categoria
                LineSeparator ls = new LineSeparator();
                ls.setLineWidth(0.7f);
                document.add(ls);

                PdfPTable table5 = new PdfPTable(colonne.size());
                table5.setWidths(columnWidths2);
                table5.setWidthPercentage(100);

                phraset = new Phrase();
                phraset.add(new Chunk("", f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table5.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk("Total for category", f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_LEFT);
                cellt.setBorder(BOTTOM);
                table5.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(totTrans + "", f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table5.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(totQt, 2)), f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table5.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(tot, 2)), f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table5.addCell(cellt);

                document.add(table5);

                //linea totale
                ls.setLineWidth(0.7f);
                document.add(ls);

                totale += tot;
                quantita += totQt;
                transazioni += totTrans;

            }

            totale = roundDouble(totale, 2);

            //Totale finale
            //linea totali
            PdfPTable table4 = new PdfPTable(colonne.size());
            table4.setWidths(columnWidths2);
            table4.setWidthPercentage(100);

            //linea totale parziale per categoria
            LineSeparator ls = new LineSeparator();
            ls.setLineWidth(0.7f);
            document.add(ls);

            phraset = new Phrase();
            phraset.add(new Chunk("", f4_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setBorder(BOTTOM);
            table4.addCell(cellt);

            phraset = new Phrase();
            phraset.add(new Chunk("Total Branch", f4_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_LEFT);
            cellt.setBorder(BOTTOM);
            table4.addCell(cellt);

            phraset = new Phrase();
            phraset.add(new Chunk(transazioni + "", f4_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setBorder(BOTTOM);
            table4.addCell(cellt);

            phraset = new Phrase();
            phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(quantita, 2)), f4_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setBorder(BOTTOM);
            table4.addCell(cellt);

            phraset = new Phrase();
            phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(totale, 2)), f4_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setBorder(BOTTOM);
            table4.addCell(cellt);

            document.add(table4);

            //linea totale
            ls.setLineWidth(0.7f);
            document.add(ls);
            //

            totaleGenerale += totale;
            totaleQuantita += quantita;
            totaleTransazioni += transazioni;

            if (lastTime) {

                vuoto.setFont(f3_normal);
                document.add(vuoto);

                //Totale finale generale
                //linea totali
                table4 = new PdfPTable(colonne.size());
                table4.setWidths(columnWidths2);
                table4.setWidthPercentage(100);

                //linea totale parziale per categoria
                ls = new LineSeparator();
                ls.setLineWidth(1f);
                document.add(ls);

                phraset = new Phrase();
                phraset.add(new Chunk("Total", f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_LEFT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(totaleTransazioni + "", f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(totaleQuantita, 2)), f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(totaleGenerale, 2)), f4_bold));
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
     * @param br
     * @return
     */
    public String main(String path, String d3, String d4, String data1, String data2,
            ArrayList<String> alcolonne, ArrayList<String> filiali, ArrayList<Branch> br) {
        try {
            C_NoChangeMovimentForBranches nctl = new C_NoChangeMovimentForBranches();

            C_NoChangeMovimentForBranches_value pdf = new C_NoChangeMovimentForBranches_value();

//        ArrayList<String> filiali = Engine.getC_Filiali_NoChangeMovimentForBranches();
            boolean firstTime = true;
            boolean lastTime = false;

            File pdffile = new File(path + generaId(50) + "C_NoChangeMovimentForBranches.pdf");
            try(OutputStream ou = new FileOutputStream(pdffile)){
            Document document = new Document(A4.rotate(), 20, 20, 20, 20);
            PdfWriter wr = getInstance(document, ou);
            document.open();

            boolean find = false;

            //ciclo per ogni filiale
            Db_Master dbm = new Db_Master();
            for (int i = 0; i < filiali.size(); i++) {
                ArrayList<C_NoChangeMovimentForBranches_value> dati = dbm.list_C_NoChangeMovimentForBranches_value(data1, data2, filiali.get(i), br);

                if (dati.size() > 0) {
                    find = true;
                }

                pdf.setId_filiale(filiali.get(i));
                pdf.setDe_filiale(formatBankBranchReport(filiali.get(i), "BR", null, br));
                pdf.setDataDa(d3);
                pdf.setDataA(d4);
                pdf.setDati(dati);

                if (dati.size() > 0) {
                    if (i == filiali.size() - 1) {
                        lastTime = true;
                    }
                    if (!dati.isEmpty()) {
                        document = nctl.receipt(pdf, alcolonne, firstTime, lastTime, document);
                        firstTime = false;
                    }
                }

            }
            dbm.closeDB();
            if (!firstTime) {
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
     * @param nctl
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
     * @return
     */
    public int receiptexcel(C_NoChangeMovimentForBranches_value nctl, ArrayList<String> colonne, boolean firstTime, boolean lastTime, HSSFSheet sheet, int cntriga, HSSFCellStyle style1, HSSFCellStyle style2, HSSFCellStyle style3, HSSFCellStyle style4, HSSFCellStyle style3left, HSSFCellStyle style4left) {

        try {

            if (firstTime) {
                Row rowP = sheet.createRow((short) cntriga);

                Cell cl = rowP.createCell(1);
                cl.setCellStyle(style1);
                cl.setCellValue(intestazionePdf + " From " + nctl.getDataDa() + " To " + nctl.getDataA());

                cntriga++;
            }

            //  document.add(table2);
            // document.add(sep);
            ArrayList<C_NoChangeMovimentForBranches_value> dati = nctl.getDati();

            boolean ft = true;
            float totale = 0;
            int transazioni = 0;
            int quantita = 0;

            ArrayList<String> kind = new ArrayList<>();
            for (int x = 0; x < dati.size(); x++) {
                C_NoChangeMovimentForBranches_value temp = dati.get(x);
                if (!kind.contains(temp.getKind())) {
                    kind.add(temp.getKind());
                }
            }

            // scorro per kind
            for (int j = 0; j < kind.size(); j++) {

                if (ft) {

                    Row row = sheet.createRow((short) cntriga);

                    Cell cl = row.createCell(1);
                    cl.setCellStyle(style1);
                    cl.setCellValue("\n " + nctl.getId_filiale() + " " + nctl.getDe_filiale());

                    cntriga++;

                    Row row66 = sheet.createRow((short) cntriga);
                    //mi scandisco le colonne
                    for (int c = 0; c < colonne.size(); c++) {
                        Cell cl7 = row66.createCell(c + 1);
                        cl7.setCellStyle(style3);
                        cl7.setCellValue(colonne.get(c));
                        if (c == 0 || c == 1) {
                            cl7.setCellStyle(style3left);
                        }
                    }

                    cntriga++;

                    ft = false;

                }

                cntriga++;

                Row row6 = sheet.createRow((short) cntriga);

                Cell c40 = row6.createCell(1);
                c40.setCellStyle(style3left);
                c40.setCellValue(kind.get(j));

                ArrayList<C_NoChangeMovimentForBranches_value> listTemp = getListKind(kind.get(j), dati);

                ArrayList<String> category = new ArrayList<>();
                for (int i = 0; i < listTemp.size(); i++) {
                    C_NoChangeMovimentForBranches_value temp = (C_NoChangeMovimentForBranches_value) listTemp.get(i);
                    if (!category.contains(temp.getCategory())) {
                        category.add(temp.getCategory());
                    }
                }

                float totQt = 0;
                int totTrans = 0;
                float tot = 0;

                for (int a = 0; a < category.size(); a++) {

                    cntriga++;
                    cntriga++;

                    Row row11 = sheet.createRow((short) cntriga);

                    Cell c41 = row11.createCell(1);
                    c41.setCellStyle(style3left);
                    c41.setCellValue(category.get(a));

                    for (int y = 0; y < listTemp.size(); y++) {

                        C_NoChangeMovimentForBranches_value temp = (C_NoChangeMovimentForBranches_value) listTemp.get(y);
                        if (temp.getCategory().equals(category.get(a))) {

                            cntriga++;
                            cntriga++;

                            Row row12 = sheet.createRow((short) cntriga);

                            Cell c42 = row12.createCell(2);
                            c42.setCellStyle(style3left);
                            c42.setCellValue(temp.getCausal());

                            Cell c43 = row12.createCell(3);
                            c43.setCellStyle(style3);
                            c43.setCellValue(temp.getnTrans());

                            Cell c44 = row12.createCell(4);
                            c44.setCellStyle(style3);
                            c44.setCellValue(formatMysqltoDisplay(temp.getQt()));

                            Cell c45 = row12.createCell(5);
                            c45.setCellStyle(style3);
                            c45.setCellValue(formatMysqltoDisplay(temp.getTotal()));

                            if (!temp.getQt().equals("-")) {
                                totQt += ff(temp.getQt());
                            }

                            if (!temp.getnTrans().equals("-")) {
                                totTrans += parseInt(temp.getnTrans());
                            }

                            if (!temp.getTotal().equals("-")) {
                                tot += ff(temp.getTotal());
                            }

                        }
                    }

                    //fine intestazione Categorie
                }

                cntriga++;

                Row row12 = sheet.createRow((short) cntriga);

                Cell c42 = row12.createCell(2);
                c42.setCellStyle(style3left);
                c42.setCellValue("Total for category");

                Cell c43 = row12.createCell(3);
                c43.setCellStyle(style3);
                c43.setCellValue((totTrans));

                Cell c44 = row12.createCell(4);
                c44.setCellStyle(style3);
                c44.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(totQt, 2)));

                Cell c45 = row12.createCell(5);
                c45.setCellStyle(style3);
                c45.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(tot, 2)));

                totale += tot;
                quantita += totQt;
                transazioni += totTrans;

                cntriga++;
                cntriga++;
                cntriga++;

            }

            cntriga++;
            cntriga++;

            Row row12 = sheet.createRow((short) cntriga);

            Cell c42 = row12.createCell(2);
            c42.setCellStyle(style3left);
            c42.setCellValue("Total Branch");

            Cell c43 = row12.createCell(3);
            c43.setCellStyle(style3);
            c43.setCellValue(transazioni + "");

            Cell c44 = row12.createCell(4);
            c44.setCellStyle(style3);
            c44.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(quantita, 2)));

            Cell c45 = row12.createCell(5);
            c45.setCellStyle(style3);
            c45.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(totale, 2)));

            //
            totaleGenerale += totale;
            totaleQuantita += quantita;
            totaleTransazioni += transazioni;

            if (lastTime) {

                cntriga++;

                Row row13 = sheet.createRow((short) cntriga);

                Cell c51 = row13.createCell(2);
                c51.setCellStyle(style3left);
                c51.setCellValue("Total");

                Cell c52 = row13.createCell(3);
                c52.setCellStyle(style3);
                c52.setCellValue(totaleTransazioni + "");

                Cell c53 = row13.createCell(4);
                c53.setCellStyle(style3);
                c53.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(totaleQuantita, 2)));

                Cell c54 = row13.createCell(5);
                c54.setCellStyle(style3);
                c54.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(totaleGenerale, 2)));

                cntriga++;
                cntriga++;
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
    public String mainexcel(String path, String d3, String d4, String data1, String data2,
            ArrayList<String> alcolonne, ArrayList<String> filiali, ArrayList<Branch> br) {
        try {
            File pdffile = new File(path + generaId(50) + "C_NoChangeMovimentForBranches.xls");
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet("C_NoChangeMovimentForBranches");

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

            //MAIN
            C_NoChangeMovimentForBranches nctl = new C_NoChangeMovimentForBranches();

            C_NoChangeMovimentForBranches_value pdf = new C_NoChangeMovimentForBranches_value();

//        ArrayList<String> filiali = Engine.getC_Filiali_NoChangeMovimentForBranches();
            boolean firstTime = true;
            boolean lastTime = false;

            //ciclo per ogni filiale
            Db_Master dbm = new Db_Master();
            int nriga = 1;
            for (int i = 0; i < filiali.size(); i++) {
                ArrayList<C_NoChangeMovimentForBranches_value> dati = dbm.list_C_NoChangeMovimentForBranches_value(data1, data2, filiali.get(i), br);

                pdf.setId_filiale(filiali.get(i));
                pdf.setDe_filiale(formatBankBranchReport(filiali.get(i), "BR", null, br));
                pdf.setDataDa(d3);
                pdf.setDataA(d4);
                pdf.setDati(dati);

                if (dati.size() > 0) {
                    if (i == filiali.size() - 1) {
                        lastTime = true;
                    }
                    if (!dati.isEmpty()) {
                        nriga = nctl.receiptexcel(pdf, alcolonne, firstTime, lastTime, sheet, nriga, style1, style2, style3, style4, style3left, style4left);
                        firstTime = false;
                    }
                }
            }
            dbm.closeDB();

            //chiusura documento
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
     * @param kind
     * @param dati
     * @return
     */
    public ArrayList<C_NoChangeMovimentForBranches_value> getListKind(String kind, ArrayList<C_NoChangeMovimentForBranches_value> dati) {
        ArrayList<C_NoChangeMovimentForBranches_value> list = new ArrayList<>();

        for (int i = 0; i < dati.size(); i++) {
            C_NoChangeMovimentForBranches_value temp = dati.get(i);
            if (temp.getKind().equals(kind)) {
                list.add(dati.get(i));
            }
        }

        return list;
    }

}
