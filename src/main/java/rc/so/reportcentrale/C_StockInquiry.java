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
import rc.so.report.StockInquiry_value;
import static rc.so.util.Constant.formatdataCell;
import static rc.so.util.Engine.insertTR;
import static rc.so.util.Utility.fd;
import static rc.so.util.Utility.formatMysqltoDisplay;
import static rc.so.util.Utility.generaId;
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
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import static org.apache.poi.hssf.usermodel.HSSFFont.FONT_ARIAL;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import static org.apache.poi.ss.usermodel.BorderStyle.THIN;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import static org.apache.poi.ss.usermodel.CellType.NUMERIC;
import static org.apache.poi.ss.usermodel.HorizontalAlignment.LEFT;
import static org.apache.poi.ss.usermodel.HorizontalAlignment.RIGHT;
import org.apache.poi.ss.usermodel.Row;

/**
 *
 * @author fplacanica
 */
public class C_StockInquiry {

    //column

    /**
     *
     */
    public static final float[] columnWidths0 = new float[]{70f, 30f};

    /**
     *
     */
    public static final float[] columnWidths1 = new float[]{60f, 30f};
    // public static float[] columnWidths2 = new float[]{40f, 30f, 30f, 30f, 40f, 30f, 30f, 30f, 30f, 30f};

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
    final String intestazionePdf = "Stock Inquiry for Branch";
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

    private float[] elencosomme;

    /**
     ** Constructor
     */
    public C_StockInquiry() {

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
     * @param si
     * @param colonne
     * @param firstTime
     * @param lastTime
     * @param document
     * @return
     * @throws DocumentException
     */
    public Document receipt(C_StockInquiry_value si, ArrayList<String> colonne, boolean firstTime, boolean lastTime, Document document) throws DocumentException {

        float[] columnWidths2 = new float[colonne.size()];

        for (int c = 0; c < colonne.size(); c++) {
            columnWidths2[c] = 10f;
        }

        if (firstTime) {
            elencosomme = new float[colonne.size() - 2];
            PdfPTable table = new PdfPTable(2);
            table.setWidths(columnWidths0);
            table.setWidthPercentage(100);
            Phrase phrase1 = new Phrase();
            phrase1.add(new Chunk(intestazionePdf + " " + si.getDate(), f3_bold));
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
        Phrase phraset;
        PdfPCell cellt;
        PdfPTable table3;

        boolean ft = true;

        float qta = 0, amount = 0, comm = 0, net = 0, buy = 0, spread = 0, commFix = 0;

        if (ft) {

            LineSeparator sep = new LineSeparator();
            sep.setOffset(-2);
            sep.setLineWidth((float) 0.5);

            PdfPTable table4 = new PdfPTable(2);
            table4.setWidths(columnWidths0);
            table4.setWidthPercentage(100);

            phraset = new Phrase();
            phraset.add(new Chunk("\n " + si.getDe_filiale(), f3_bold));
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
                if(c==1){
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

        // StockInquiry per una filiale
        double sommavalori[] = new double[1];
        for (int k = 0; k < 1; k++) {
            sommavalori[k] = 0;
        }

        ArrayList<StockInquiry_value> lista = si.getData();

        for (int i = 0; i < lista.size(); i++) {

            StockInquiry_value temp =  lista.get(i);
            PdfPTable table2 = new PdfPTable(colonne.size());
            table2.setWidths(columnWidths2);
            table2.setWidthPercentage(100);

            ArrayList<String> ttt = temp.getDati_string();
            Phrase phraset111 = new Phrase();

            phraset111.add(new Chunk(temp.getTill(), f3_normal));
            PdfPCell cellt111 = new PdfPCell(phraset111);
            cellt111.setHorizontalAlignment(ALIGN_RIGHT);
            cellt111.setBorder(BOTTOM);

            table2.addCell(cellt111);

            phraset111 = new Phrase();
            phraset111.add(new Chunk(temp.getCurrency(), f3_normal));
            cellt111 = new PdfPCell(phraset111);
            cellt111.setHorizontalAlignment(ALIGN_LEFT);
            cellt111.setBorder(BOTTOM);

            table2.addCell(cellt111);

            for (int k = 0; !ttt.isEmpty() && k < colonne.size() - 2; k++) {

                phraset111 = new Phrase();

                phraset111.add(new Chunk(formatMysqltoDisplay(ttt.get(k)), f3_normal));
                cellt111 = new PdfPCell(phraset111);
                cellt111.setHorizontalAlignment(ALIGN_RIGHT);
                cellt111.setBorder(BOTTOM);

                table2.addCell(cellt111);

                if (k == 1) {
                    double d = (sommavalori[0]);
                    double d1 = (fd((String) ttt.get(k)));
                    double tot = d + d1;

                    sommavalori[0] = tot;
                }

            }

            document.add(table2);

            if (i != lista.size() - 1) {

                StockInquiry_value temp2 = lista.get(i);

                table2 = new PdfPTable(colonne.size());
                table2.setWidths(columnWidths2);
                table2.setWidthPercentage(100);

                if (!temp.getCurrency().equals(temp2.getCurrency())) {

                    table2 = new PdfPTable(colonne.size());
                    table2.setWidths(columnWidths2);
                    table2.setWidthPercentage(100);

                    phraset111 = new Phrase();

                    phraset111.add(new Chunk("", f4_bold));
                    cellt111 = new PdfPCell(phraset111);
                    cellt111.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt111.setBorder(BOTTOM);
                    cellt111.setBorderWidth(0.7f);

                    table2.addCell(cellt111);

                    phraset111 = new Phrase();
                    phraset111.add(new Chunk("", f4_bold));
                    cellt111 = new PdfPCell(phraset111);
                    cellt111.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt111.setBorder(BOTTOM);
                    cellt111.setBorderWidth(0.7f);

                    table2.addCell(cellt111);

                    for (int j = 0; j < sommavalori.length; j++) {

                        Phrase phraset11 = new Phrase();
                        phraset11.add(new Chunk("", f4_bold));
                        PdfPCell cellt11 = new PdfPCell(phraset11);
                        cellt11.setHorizontalAlignment(ALIGN_RIGHT);
                        cellt11.setBorder(BOTTOM);
                        cellt11.setBorderWidth(0.7f);
                        table2.addCell(cellt11);

                        phraset11 = new Phrase();
                        phraset11.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(sommavalori[j],2)), f4_bold));
                        cellt11 = new PdfPCell(phraset11);
                        cellt11.setHorizontalAlignment(ALIGN_RIGHT);
                        cellt11.setBorder(BOTTOM);
                        cellt11.setBorderWidth(0.7f);
                        table2.addCell(cellt11);

                        phraset11 = new Phrase();
                        phraset11.add(new Chunk("", f4_bold));
                        cellt11 = new PdfPCell(phraset11);
                        cellt11.setHorizontalAlignment(ALIGN_RIGHT);
                        cellt11.setBorder(BOTTOM);
                        cellt11.setBorderWidth(0.7f);
                        table2.addCell(cellt11);

                        elencosomme[j] += sommavalori[j];

                    }

                    sommavalori = new double[1];
                    for (int k = 0; k < 1; k++) {
                        sommavalori[k] = 0;
                    }

                    LineSeparator ls = new LineSeparator();
                    ls.setLineWidth(0.7f);

                    document.add(ls);

                    document.add(table2);

                } else {
                    //memorizzo i valori per la somma

                }
            } else {

                table2 = new PdfPTable(colonne.size());
                table2.setWidths(columnWidths2);
                table2.setWidthPercentage(100);

                //inserisco la somma delle colonne
                phraset111 = new Phrase();

                phraset111.add(new Chunk("", f4_bold));
                cellt111 = new PdfPCell(phraset111);
                cellt111.setHorizontalAlignment(ALIGN_RIGHT);
                cellt111.setBorder(BOTTOM);
                cellt111.setBorderWidth(0.7f);

                table2.addCell(cellt111);

                phraset111 = new Phrase();
                phraset111.add(new Chunk("", f4_bold));
                cellt111 = new PdfPCell(phraset111);
                cellt111.setHorizontalAlignment(ALIGN_RIGHT);
                cellt111.setBorder(BOTTOM);
                cellt111.setBorderWidth(0.7f);

                table2.addCell(cellt111);

                for (int b = 0; b < sommavalori.length; b++) {

                    phraset111 = new Phrase();
                    //  ArrayList<String> ttt = al.get(i).getData();
                    phraset111.add(new Chunk("", f4_bold));
                    PdfPCell cellt11 = new PdfPCell(phraset111);
                    cellt11.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt11.setBorder(BOTTOM);
                    cellt11.setBorderWidth(0.7f);
                    table2.addCell(cellt11);

                    phraset111 = new Phrase();
                    //  ArrayList<String> ttt = al.get(i).getData();
                    phraset111.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(sommavalori[b], 2)), f4_bold));
                    cellt11 = new PdfPCell(phraset111);
                    cellt11.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt11.setBorder(BOTTOM);
                    cellt11.setBorderWidth(0.7f);
                    table2.addCell(cellt11);

                    phraset111 = new Phrase();
                    //  ArrayList<String> ttt = al.get(i).getData();
                    phraset111.add(new Chunk("", f4_bold));
                    cellt11 = new PdfPCell(phraset111);
                    cellt11.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt11.setBorder(BOTTOM);
                    cellt11.setBorderWidth(0.7f);
                    table2.addCell(cellt11);

                    elencosomme[b] += sommavalori[b];


                }

                LineSeparator ls = new LineSeparator();
                ls.setLineWidth(0.7f);
                document.add(ls);

            }

            document.add(table2);
        }

        //
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

            //
            PdfPTable table2 = new PdfPTable(colonne.size());
            table2.setWidths(columnWidths2);
            table2.setWidthPercentage(100);

            //inserisco la somma delle colonne
            Phrase phraset111 = new Phrase();

            phraset111.add(new Chunk("", f4_bold));
            PdfPCell cellt111 = new PdfPCell(phraset111);
            cellt111.setHorizontalAlignment(ALIGN_RIGHT);
            cellt111.setBorder(BOTTOM);
            cellt111.setBorderWidth(0.7f);

            table2.addCell(cellt111);

            phraset111 = new Phrase();
            phraset111.add(new Chunk("Total", f4_bold));
            cellt111 = new PdfPCell(phraset111);
            cellt111.setHorizontalAlignment(ALIGN_RIGHT);
            cellt111.setBorder(BOTTOM);
            cellt111.setBorderWidth(0.7f);

            table2.addCell(cellt111);

            for (int b = 0; b < 1; b++) {

                phraset111 = new Phrase();
                //  ArrayList<String> ttt = al.get(i).getData();
                phraset111.add(new Chunk("", f4_bold));
                PdfPCell cellt11 = new PdfPCell(phraset111);
                cellt11.setHorizontalAlignment(ALIGN_RIGHT);
                cellt11.setBorder(BOTTOM);
                cellt11.setBorderWidth(0.7f);
                table2.addCell(cellt11);

                phraset111 = new Phrase();
                //  ArrayList<String> ttt = al.get(i).getData();
                phraset111.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(elencosomme[0],2)), f4_bold));
                cellt11 = new PdfPCell(phraset111);
                cellt11.setHorizontalAlignment(ALIGN_RIGHT);
                cellt11.setBorder(BOTTOM);
                cellt11.setBorderWidth(0.7f);
                table2.addCell(cellt11);

                phraset111 = new Phrase();
                //  ArrayList<String> ttt = al.get(i).getData();
                phraset111.add(new Chunk("", f4_bold));
                cellt11 = new PdfPCell(phraset111);
                cellt11.setHorizontalAlignment(ALIGN_RIGHT);
                cellt11.setBorder(BOTTOM);
                cellt11.setBorderWidth(0.7f);
                table2.addCell(cellt11);

                //

                //linea totale
                ls.setLineWidth(1f);
                document.add(ls);
                //
            }

            document.add(table2);
        }

        return document;
    }

    /**
     *
     * @param path
     * @param d1
     * @param date
     * @param alcolonne
     * @param filiali
     * @return
     */
    public String main(String path, String d1, String date, ArrayList<String> alcolonne, ArrayList<String[]> filiali) {
        try {

            C_StockInquiry nctl = new C_StockInquiry();
            C_StockInquiry_value pdf = new C_StockInquiry_value();
            boolean firstTime = true;
            boolean lastTime = false;
            File pdffile = new File(path + generaId(50) + "C_StockInquiry.pdf");
            try (OutputStream ou = new FileOutputStream(pdffile)) {
                Document document = new Document(A4.rotate(), 20, 20, 20, 20);
                PdfWriter wr = getInstance(document, ou);
                document.open();
                Db_Master dbm = new Db_Master();
                for (int i = 0; i < filiali.size(); i++) {
                    ArrayList<StockInquiry_value> dati = dbm.list_C_StockInquiry_value(d1, filiali.get(i));
                    pdf.setId_filiale(filiali.get(i)[0]);
                    pdf.setDe_filiale(filiali.get(i)[1]);
                    pdf.setDate(date);
                    
                    pdf.setData(dati);
                    if (i == filiali.size() - 1) {
                        lastTime = true;
                    }
                    if (dati.size() > 0) {
                        document = nctl.receipt(pdf, alcolonne, firstTime, lastTime, document);
                        firstTime = false;
                    }
                }   if (firstTime) {
                    document.add(new Paragraph("CZZ"));
                    document.close();
                    wr.close();
                    ou.close();
                    pdffile.delete();
                    return null;
                }   dbm.closeDB();
                document.close();
                wr.close();
            }
            String base64 = new String(encodeBase64(readFileToByteArray(pdffile)));
            pdffile.delete();
            return base64;
        } catch (DocumentException | IOException ex) {
            insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
        }
        return null;

    }

    /**
     *
     * @param path
     * @param d1
     * @param date
     * @param alcolonne
     * @param filiali
     * @return
     */
    public String mainexcel(String path, String d1, String date, ArrayList<String> alcolonne, ArrayList<String[]> filiali) {
        try {
            File pdffile = new File(path + generaId(50) + "C_StockInquiry.xls");
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet("C_StockInquiry");

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

            C_StockInquiry nctl = new C_StockInquiry();
            C_StockInquiry_value pdf = new C_StockInquiry_value();
            boolean firstTime = true;
            boolean lastTime = false;
            Db_Master dbm = new Db_Master();
            int nriga = 1;
            for (int i = 0; i < filiali.size(); i++) {
                ArrayList<StockInquiry_value> dati = dbm.list_C_StockInquiry_value(d1, filiali.get(i));
                pdf.setId_filiale(filiali.get(i)[0]);
                pdf.setDe_filiale(filiali.get(i)[1]);
                pdf.setDate(date);
                pdf.setData(dati);
                if (i == filiali.size() - 1) {
                    lastTime = true;
                }
                nriga = nctl.receiptexcel(pdf, alcolonne, firstTime, lastTime, sheet, nriga, style1, style2, style3, style4,style3left, style4left, cellStylenum);
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
     * @param si
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
     * @return
     */
    public int receiptexcel(C_StockInquiry_value si, ArrayList<String> colonne, boolean firstTime, boolean lastTime, HSSFSheet sheet, int cntriga, HSSFCellStyle style1, HSSFCellStyle style2, HSSFCellStyle style3, HSSFCellStyle style4,HSSFCellStyle style3left, HSSFCellStyle style4left, CellStyle cellStylenum) {

        float[] columnWidths2 = new float[colonne.size()];

        if (firstTime) {
            elencosomme = new float[colonne.size() - 2];

            Row rowP = sheet.createRow((short) cntriga);

            Cell cl = rowP.createCell(1);
            cl.setCellStyle(style1);
            cl.setCellValue(intestazionePdf + " " + si.getDate());

            cntriga++;

        }

        //  document.add(table2);
        // document.add(sep);
        boolean ft = true;

        float qta = 0, amount = 0, comm = 0, net = 0, buy = 0, spread = 0, commFix = 0;

        if (ft) {

            Row row = sheet.createRow((short) cntriga);

            Cell cl = row.createCell(1);
            cl.setCellStyle(style1);
            cl.setCellValue("\n " + si.getDe_filiale());

            cntriga++;

            Row row66 = sheet.createRow((short) cntriga);
            //mi scandisco le colonne
            for (int c = 0; c < colonne.size(); c++) {
                Cell cl7 = row66.createCell(c + 1);
                cl7.setCellStyle(style3);
                if(c==1){
                cl7.setCellStyle(style3left);    
                }
                cl7.setCellValue(colonne.get(c));
            }

            cntriga++;

        }

        // StockInquiry per una filiale
        double sommavalori[] = new double[1];
        for (int k = 0; k < 1; k++) {
            sommavalori[k] = 0;
        }

        ArrayList<StockInquiry_value> lista = si.getData();

        for (int i = 0; i < lista.size(); i++) {

            cntriga++;
            StockInquiry_value temp = lista.get(i);

            ArrayList<String> ttt = temp.getDati_string();

            Row row6 = sheet.createRow((short) cntriga);

            Cell f1bis = row6.createCell(1);
            f1bis.setCellStyle(style4);
            f1bis.setCellValue(temp.getTill());

            Cell f2 = row6.createCell(2);
            f2.setCellStyle(style4left);
            f2.setCellValue(temp.getCurrency());

            for (int k = 0; k < colonne.size() - 2; k++) {

                Cell f3 = row6.createCell(k + 3, NUMERIC);
                f3.setCellStyle(cellStylenum);
                f3.setCellValue(fd(ttt.get(k)));

                if (k == 1) {
                    double d = (sommavalori[0]);
                    double d1 = (fd((String) ttt.get(k)));
                    double tot = d + d1;

                    sommavalori[0] = tot;
                }

            }

            if (i != lista.size() - 1) {

                cntriga++;

                Row row7 = sheet.createRow((short) cntriga);
                StockInquiry_value temp2 = lista.get(i);

                if (!temp.getCurrency().equals(temp2.getCurrency())) {

                    for (int j = 0; j < sommavalori.length; j++) {

                        Cell f5 = row7.createCell(4, NUMERIC);
                        f5.setCellStyle(cellStylenum);
                        f5.setCellValue(fd(roundDoubleandFormat(sommavalori[j],2)));

                        elencosomme[j] += sommavalori[j];

                    }

                    sommavalori = new double[1];
                    for (int k = 0; k < 1; k++) {
                        sommavalori[k] = 0;
                    }

                } else {
                    //memorizzo i valori per la somma

                }
            } else {

                cntriga++;
                Row row7 = sheet.createRow((short) cntriga);

                for (int b = 0; b < sommavalori.length; b++) {

                    Cell f5 = row7.createCell(4, NUMERIC);
                    f5.setCellStyle(cellStylenum);
                    f5.setCellValue(fd(roundDoubleandFormat(sommavalori[b], 2)));

                    elencosomme[b] += sommavalori[b];

                }

            }

        }

        //
        if (lastTime) {

            cntriga++;
            cntriga++;
            Row row8 = sheet.createRow((short) cntriga);
            //Totale finale generale
            //linea totali
            Cell f5 = row8.createCell(2);
            f5.setCellStyle(style3);
            f5.setCellValue("Total");

            for (int b = 0; b < 1; b++) {

                Cell f6 = row8.createCell(4);
                f6.setCellStyle(cellStylenum);
                f6.setCellValue(fd(roundDoubleandFormat(elencosomme[0],2)));
                //
            }

        }
        cntriga++;
        cntriga++;
        cntriga++;
        return cntriga;
    }

}
