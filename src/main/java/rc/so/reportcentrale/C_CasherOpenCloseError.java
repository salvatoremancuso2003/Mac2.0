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
import static com.itextpdf.text.Rectangle.NO_BORDER;
import static com.itextpdf.text.Rectangle.TOP;
import static com.itextpdf.text.pdf.BaseFont.HELVETICA;
import static com.itextpdf.text.pdf.BaseFont.WINANSI;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import static com.itextpdf.text.pdf.PdfWriter.getInstance;
import com.itextpdf.text.pdf.draw.LineSeparator;
import static rc.so.util.Constant.formatdataCell;
import static rc.so.util.Engine.insertTR;
import static rc.so.util.Utility.fd;
import static rc.so.util.Utility.ff;
import static rc.so.util.Utility.formatMysqltoDisplay;
import static rc.so.util.Utility.generaId;
import static rc.so.util.Utility.roundDouble;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import static java.lang.String.valueOf;
import static java.lang.Thread.currentThread;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import static org.apache.poi.ss.usermodel.HorizontalAlignment.RIGHT;

/**
 *
 * @author vcrugliano
 */
public class C_CasherOpenCloseError {

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
    public static float[] columnWidths2 = new float[]{10f, 15f, 40f, 25f, 20f};

    /**
     *
     */
    public static float[] columnWidths3 = new float[]{10f, 10f, 10f, 10f, 15f, 15f, 20f, 10f};
    final String intestazionePdf = "Casher Open / Close Error ";
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

    /**
     * Constructor
     */
    public C_CasherOpenCloseError() {

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
     * @param path
     * @param osp
     * @param colonne
     * @param colonnedettaglio
     * @param colonnedettaglioNoChange
     * @param colonnedettaglioPOS
     * @param data1
     * @param data2
     * @return
     */
    public String receipt(String path, C_CasherOpenCloseError_value osp, ArrayList<String> colonne, ArrayList<String> colonnedettaglio,
            ArrayList<String> colonnedettaglioNoChange, ArrayList<String> colonnedettaglioPOS, String data1, String data2) {

//        String outputfile = "C_CasherOpenCloseError.pdf";
        try {

            DateFormat dateFormat = new SimpleDateFormat("ddddMMyyyy");
            Date date = new Date();
            File pdf = new File(path + "C_CasherOpenCloseError" + "_" + dateFormat.format(date) + ".pdf");
            //File pdf = new File(path + Utility.generaId(50) + "C_CasherOpenCloseError.pdf");
            Document document = new Document(A4, 20, 20, 20, 20);
            try(OutputStream ou = new FileOutputStream(pdf)){
            PdfWriter wr = getInstance(document, ou);
            document.open();

            PdfPTable table = new PdfPTable(2);
            table.setWidths(columnWidths0);
            table.setWidthPercentage(100);

            Phrase phrase1 = new Phrase();
            phrase1.add(new Chunk(intestazionePdf + " From " + data1 + " to " + data2, f4_bold));
            PdfPCell cell1 = new PdfPCell(phrase1);
            cell1.setBorder(NO_BORDER);

            // Paragraph pa1 = new Paragraph(new Phrase(datereport, f3_normal));
            Paragraph pa1 = new Paragraph(new Phrase("", f3_normal));
            pa1.setAlignment(ALIGN_CENTER);
            PdfPCell cell2 = new PdfPCell(pa1);
            cell2.setBorder(NO_BORDER);
            cell2.setHorizontalAlignment(ALIGN_RIGHT);

            Phrase phrase3 = new Phrase();
            phrase3.add(new Chunk("\n Casher " + osp.getUser() + " ", f3_normal));
            PdfPCell cell3 = new PdfPCell(phrase3);
            cell3.setBorder(NO_BORDER);

            Phrase phrase4 = new Phrase();
            phrase4.add(new Chunk("", f3_normal));
            PdfPCell cell4 = new PdfPCell(phrase4);
            cell4.setBorder(NO_BORDER);

            table.addCell(cell1);
            table.addCell(cell2);
            table.addCell(cell3);
            table.addCell(cell4);
            document.add(table);

            vuoto.setFont(f3_normal);
            document.add(vuoto);

            ArrayList<C_CasherOpenCloseError_value> dati = osp.getDati();
            Phrase phraset;
            PdfPCell cellt;

            PdfPTable table3 = new PdfPTable(colonne.size());
            table3.setWidths(columnWidths2);
            table3.setWidthPercentage(100);

            table3 = new PdfPTable(colonne.size());
            table3.setWidths(columnWidths2);
            table3.setWidthPercentage(100);

            double totalecop = 0; //totale controvalore della valuta locale
            double totalefx = 0;//totale controvalore delle valute diverse dalla valuta locale
            double totalegenerale = 0; //totale generale

            for (int i = 0; i < dati.size(); i++) {
                C_CasherOpenCloseError_value temp = (C_CasherOpenCloseError_value) dati.get(i);

                PdfPTable table2 = new PdfPTable(colonne.size());
                table2.setWidths(columnWidths2);
                table2.setWidthPercentage(100);

                PdfPCell[] list = new PdfPCell[colonne.size()];
                //mi scandisco le colonne
                for (int j = 0; j < colonne.size(); j++) {
                    Phrase phraset1 = new Phrase();
                    phraset1.add(new Chunk(colonne.get(j), f3_normal));
                    PdfPCell cellt1 = new PdfPCell(phraset1);
                    if (j == 2) {
                        cellt1.setHorizontalAlignment(ALIGN_CENTER);
                    } else {
                        cellt1.setHorizontalAlignment(ALIGN_LEFT);
                    }
                    cellt1.setBorder(BOTTOM | TOP);
                    // cellt1.setFixedHeight(20f);
                    cellt1.setBackgroundColor(LIGHT_GRAY);
                    //cellt1.setBorder(Rectangle.BOTTOM);
                    cellt1.setBorderWidth(0.7f);

                    list[j] = cellt1;

                }

                table3 = new PdfPTable(colonne.size());
                table3.setWidths(columnWidths2);
                table3.setWidthPercentage(50);
                table3.setHorizontalAlignment(ALIGN_LEFT);

                for (PdfPCell list1 : list) {
                    PdfPCell tempcell = (PdfPCell) (list1);
                    table3.addCell(tempcell);
                }

                document.add(table3);

                LineSeparator sep = new LineSeparator();
                sep.setOffset(-2);
                sep.setLineWidth((float) 0.5);

                //  document.add(table2);
                // document.add(sep);
                table3 = new PdfPTable(colonne.size());
                table3.setWidths(columnWidths2);
                table3.setWidthPercentage(50);
                table3.setHorizontalAlignment(ALIGN_LEFT);

                phraset = new Phrase();
                phraset.add(new Chunk(temp.getUser(), f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_LEFT);
                cellt.setBorder(BOTTOM);
                table3.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(temp.getSafetill(), f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_LEFT);
                cellt.setBorder(BOTTOM);
                table3.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(temp.getData(), f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_LEFT);
                cellt.setBorder(BOTTOM);
                table3.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(temp.getOperazione(), f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_LEFT);
                cellt.setBorder(BOTTOM);
                table3.addCell(cellt);

                String tipoesteso;
                if (temp.getTipo().equals("C")) {
                    tipoesteso = "Close";
                } else {
                    tipoesteso = "Open";
                }

                phraset = new Phrase();
                phraset.add(new Chunk(tipoesteso + " " + osp.getIdfiliale() + " " + osp.getDefiliale(), f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_LEFT);
                cellt.setBorder(BOTTOM);
                table3.addCell(cellt);

                document.add(table3);

                ArrayList<C_CasherOpenCloseError_errore> listaerrori = temp.getDettaglierrore();
                boolean firstime = true;

                ArrayList<Double> elencodiff = new ArrayList<>();

                for (int x = 0; x < listaerrori.size(); x++) {
                    PdfPTable table4 = new PdfPTable(8);
                    table4.setWidths(columnWidths3);
                    table4.setWidthPercentage(100);
                    table4.setHorizontalAlignment(ALIGN_LEFT);

                    C_CasherOpenCloseError_errore derr = (C_CasherOpenCloseError_errore) listaerrori.get(x);

                    if (firstime) {
                        firstime = false;
                        for (int g = 0; g < colonnedettaglio.size(); g++) {
                            phraset = new Phrase();
                            phraset.add(new Chunk((String) colonnedettaglio.get(g), f4_bold));
                            cellt = new PdfPCell(phraset);
                            cellt.setHorizontalAlignment(ALIGN_LEFT);
                            cellt.setBorder(BOTTOM);
                            table4.addCell(cellt);
                        }
                    }

                    //mi calcolo i valori da inserire
                    double diff;
                    double qtautentediv = ff(derr.getQtautente());
                    double qtasistdiv = ff(derr.getQtasistema());
                    double cambio = ff(derr.getCambio());

                    totalecop = roundDouble(totalecop, 2);
                    totalefx = roundDouble(totalefx, 2);
                    totalegenerale = roundDouble(totalegenerale, 2);

                    qtautentediv = roundDouble(qtautentediv, 2);
                    qtasistdiv = roundDouble(qtasistdiv, 2);
                    cambio = roundDouble(cambio, 2);

                    if (qtautentediv > 0) {
                        qtautentediv = qtautentediv / cambio;
                    }
                    if (qtasistdiv > 0) {
                        qtasistdiv = qtasistdiv / cambio;
                    }

                    diff = qtautentediv - qtasistdiv;

                    elencodiff.add(diff);

                    phraset = new Phrase();
                    phraset.add(new Chunk(derr.getValuta(), f3_normal));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_LEFT);
                    cellt.setBorder(BOTTOM);
                    table4.addCell(cellt);

                    phraset = new Phrase();
                    phraset.add(new Chunk(derr.getSupporto(), f3_normal));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_LEFT);
                    cellt.setBorder(BOTTOM);
                    table4.addCell(cellt);

                    phraset = new Phrase();
                    phraset.add(new Chunk(formatMysqltoDisplay(derr.getQtautente()), f3_normal));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setBorder(BOTTOM);
                    table4.addCell(cellt);

                    phraset = new Phrase();
                    phraset.add(new Chunk(formatMysqltoDisplay(derr.getQtasistema()), f3_normal));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setBorder(BOTTOM);
                    table4.addCell(cellt);

                    phraset = new Phrase();
                    phraset.add(new Chunk(formatMysqltoDisplay(diff + ""), f3_normal));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setBorder(BOTTOM);
                    table4.addCell(cellt);

                    phraset = new Phrase();
                    phraset.add(new Chunk(formatMysqltoDisplay(derr.getCambio()), f3_normal));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setBorder(BOTTOM);
                    table4.addCell(cellt);

                    phraset = new Phrase();
                    phraset.add(new Chunk(formatMysqltoDisplay(valueOf(diff)), f3_normal));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setBorder(BOTTOM);
                    table4.addCell(cellt);

                    phraset = new Phrase();
                    phraset.add(new Chunk(derr.getNote(), f3_normal));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setBorder(BOTTOM);
                    table4.addCell(cellt);

                    document.add(table4);

                }

                float totdiff = 0;

                for (int t = 0; t < elencodiff.size(); t++) {
                    totdiff += elencodiff.get(t);
                }

                PdfPTable table5 = new PdfPTable(8);
                table5.setWidths(columnWidths3);
                table5.setWidthPercentage(100);
                table5.setHorizontalAlignment(ALIGN_LEFT);

                phraset = new Phrase();
                phraset.add(new Chunk(" ", f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_LEFT);
                cellt.setBorder(NO_BORDER);
                table5.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(" ", f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_LEFT);
                cellt.setBorder(NO_BORDER);
                table5.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(" ", f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_LEFT);
                cellt.setBorder(NO_BORDER);
                table5.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(" ", f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_LEFT);
                cellt.setBorder(NO_BORDER);
                table5.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(" ", f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_LEFT);
                cellt.setBorder(NO_BORDER);
                table5.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk("Total difference", f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_LEFT);
                cellt.setBorder(BOTTOM);
                table5.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk((valueOf(totdiff)), f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table5.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(" ", f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_LEFT);
                cellt.setBorder(NO_BORDER);
                table5.addCell(cellt);

                document.add(table5);

                vuoto.setFont(f3_normal);
                document.add(vuoto);

            }

            //   document.add(table3);
//            
            document.close();
            wr.close();
            ou.close();
            String base64 = new String(encodeBase64(readFileToByteArray(pdf)));
            pdf.delete();
            return base64;
        }
        } catch (IOException | DocumentException ex) {
            insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
        }
        return null;
    }

    /**
     *
     * @param path
     * @param osp
     * @param colonne
     * @param colonnedettaglio
     * @param colonnedettaglioNoChange
     * @param colonnedettaglioPOS
     * @param data1
     * @param data2
     * @return
     */
    public String receiptexcel(String path, C_CasherOpenCloseError_value osp, ArrayList<String> colonne, ArrayList<String> colonnedettaglio,
            ArrayList<String> colonnedettaglioNoChange, ArrayList<String> colonnedettaglioPOS, String data1, String data2) {

//        String outputfile = "C_CasherOpenCloseError.pdf";
        try {

            File pdf = new File(path + generaId(50) + "C_CasherOpenCloseError.xls");
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet("C_CasherOpenCloseError");
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

            HSSFFont font4 = workbook.createFont();
            font4.setFontName(FONT_ARIAL);
            font4.setFontHeightInPoints((short) 10);

            HSSFCellStyle style4 = (HSSFCellStyle) workbook.createCellStyle();
            style4.setAlignment(RIGHT);
            style4.setBorderTop(THIN);
            style4.setBorderBottom(THIN);

            HSSFCellStyle cellStylenum = (HSSFCellStyle) workbook.createCellStyle();
            HSSFDataFormat hssfDataFormat = (HSSFDataFormat) workbook.createDataFormat();
            cellStylenum.setAlignment(RIGHT);
            cellStylenum.setBorderTop(THIN);
            cellStylenum.setBorderBottom(THIN);
            cellStylenum.setDataFormat(hssfDataFormat.getFormat(formatdataCell));

            Row rowP = sheet.createRow((short) 1);

            Cell cl = rowP.createCell(1);
            cl.setCellStyle(style1);
            cl.setCellValue(intestazionePdf + " from " + data1 + " to  " + data2);

            Row rowP3 = sheet.createRow((short) 3);

            Cell cl2 = rowP3.createCell(1);
            cl2.setCellStyle(style1);
            cl2.setCellValue("\n Casher " + osp.getUser() + " ");

            ArrayList<C_CasherOpenCloseError_value> dati = osp.getDati();

            PdfPTable table3 = new PdfPTable(colonne.size());
            table3.setWidths(columnWidths2);
            table3.setWidthPercentage(100);

            table3 = new PdfPTable(colonne.size());
            table3.setWidths(columnWidths2);
            table3.setWidthPercentage(100);

            double totalecop = 0; //totale controvalore della valuta locale
            double totalefx = 0;//totale controvalore delle valute diverse dalla valuta locale
            double totalegenerale = 0; //totale generale

            int cntriga = 7;

            for (int i = 0; i < dati.size(); i++) {

                C_CasherOpenCloseError_value temp = dati.get(i);

                Row row66 = sheet.createRow((short) cntriga);

                //mi scandisco le colonne
                for (int j = 0; j < colonne.size(); j++) {
                    Cell cl7 = row66.createCell(j + 1);
                    cl7.setCellStyle(style3);
                    cl7.setCellValue(colonne.get(j));
                }

                cntriga++;
                Row row6 = sheet.createRow((short) cntriga);

                LineSeparator sep = new LineSeparator();
                sep.setOffset(-2);
                sep.setLineWidth((float) 0.5);

                //  document.add(table2);
                // document.add(sep);
                Cell f1bis = row6.createCell(1);
                f1bis.setCellStyle(style4);
                f1bis.setCellValue(temp.getUser());

                Cell f2 = row6.createCell(2);
                f2.setCellStyle(style4);
                f2.setCellValue(temp.getSafetill());

                Cell f3 = row6.createCell(3);
                f3.setCellStyle(style4);
                f3.setCellValue(temp.getData());

                Cell f4 = row6.createCell(4);
                f4.setCellStyle(style4);
                f4.setCellValue(temp.getOperazione());

                String tipoesteso;
                if (temp.getTipo().equals("C")) {
                    tipoesteso = "Close";
                } else {
                    tipoesteso = "Open";
                }

                Cell f6 = row6.createCell(5);
                f6.setCellStyle(style4);
                f6.setCellValue(tipoesteso + " " + osp.getIdfiliale() + " " + osp.getDefiliale());

                cntriga++;

                Row row7 = sheet.createRow((short) cntriga);

                ArrayList<C_CasherOpenCloseError_errore> listaerrori = temp.getDettaglierrore();
                boolean firstime = true;

                ArrayList<Double> elencodiff = new ArrayList<>();

                for (int x = 0; x < listaerrori.size(); x++) {
                    cntriga++;

                    C_CasherOpenCloseError_errore derr = listaerrori.get(x);
                    Row row61 = sheet.createRow((short) cntriga);
                    int cnttemp = colonnedettaglio.size();
                    if (firstime) {
                        firstime = false;

                        for (int g = 0; g < colonnedettaglio.size(); g++) {
                            Cell cl7 = row61.createCell(g + 1);
                            cl7.setCellStyle(style3);
                            cl7.setCellValue(colonnedettaglio.get(g));

                        }
                    }

                    //mi calcolo i valori da inserire
                    double diff;
                    double qtautentediv = ff(derr.getQtautente());
                    double qtasistdiv = ff(derr.getQtasistema());
                    double cambio = ff(derr.getCambio());

                    totalecop = roundDouble(totalecop, 2);
                    totalefx = roundDouble(totalefx, 2);
                    totalegenerale = roundDouble(totalegenerale, 2);

                    qtautentediv = roundDouble(qtautentediv, 2);
                    qtasistdiv = roundDouble(qtasistdiv, 2);
                    cambio = roundDouble(cambio, 2);

                    if (qtautentediv > 0) {
                        qtautentediv = qtautentediv / cambio;
                    }
                    if (qtasistdiv > 0) {
                        qtasistdiv = qtasistdiv / cambio;
                    }

                    diff = qtautentediv - qtasistdiv;

                    elencodiff.add(diff);

                    Cell f31 = row61.createCell(cnttemp + 2);
                    f31.setCellStyle(style4);
                    f31.setCellValue(derr.getValuta());

                    Cell f33 = row61.createCell(cnttemp + 3);
                    f33.setCellStyle(style4);
                    f33.setCellValue(derr.getSupporto());

                    Cell f34 = row61.createCell(cnttemp + 4);
                    f34.setCellStyle(style4);
                    f34.setCellValue(derr.getQtautente());

                    Cell f35 = row61.createCell(cnttemp + 5, NUMERIC);
                    f35.setCellStyle(cellStylenum);
                    f35.setCellValue(fd(derr.getQtasistema()));

                    Cell f36 = row61.createCell(cnttemp + 6, NUMERIC);
                    f36.setCellStyle(cellStylenum);
                    f36.setCellValue(fd(valueOf(diff)));

                    Cell f37 = row61.createCell(cnttemp + 7);
                    f37.setCellStyle(style4);
                    f37.setCellValue(derr.getCambio());

                    Cell f38 = row61.createCell(cnttemp + 8, NUMERIC);
                    f38.setCellStyle(cellStylenum);
                    f38.setCellValue(fd(valueOf(diff)));

                    Cell f39 = row61.createCell(cnttemp + 9);
                    f39.setCellStyle(style4);
                    f39.setCellValue(derr.getNote());

                }

                float totdiff = 0;

                for (int t = 0; t < elencodiff.size(); t++) {
                    totdiff += elencodiff.get(t);
                }

                cntriga++;
                cntriga++;
                Row row8 = sheet.createRow((short) cntriga);

                Cell f66 = row8.createCell(6);
                f66.setCellStyle(style4);
                f66.setCellValue("Total difference");

                Cell f67 = row8.createCell(7);
                f67.setCellStyle(style4);
                f67.setCellValue((valueOf(totdiff)));

            }

            //   document.add(table3);
//            
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

            try (FileOutputStream out = new FileOutputStream(pdf)) {
                workbook.write(out);
            }
            String base64 = new String(encodeBase64(readFileToByteArray(pdf)));
            pdf.delete();
            return base64;
        } catch (IOException | DocumentException ex) {
            insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
        }
        return null;
    }

}
