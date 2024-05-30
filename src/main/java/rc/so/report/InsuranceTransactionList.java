/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rc.so.report;

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
import static com.itextpdf.text.pdf.ColumnText.showTextAligned;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import static com.itextpdf.text.pdf.PdfWriter.getInstance;
import com.itextpdf.text.pdf.draw.LineSeparator;
import static rc.so.util.Engine.insertTR;
import static rc.so.util.Utility.ff;
import static rc.so.util.Utility.formatDoubleforMysql;
import static rc.so.util.Utility.formatMysqltoDisplay;
import static rc.so.util.Utility.generaId;
import static rc.so.util.Utility.roundDoubleandFormat;
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
import static org.apache.poi.ss.usermodel.BorderStyle.THIN;
import org.apache.poi.ss.usermodel.Cell;
import static org.apache.poi.ss.usermodel.HorizontalAlignment.LEFT;
import static org.apache.poi.ss.usermodel.HorizontalAlignment.RIGHT;
import org.apache.poi.ss.usermodel.Row;

/**
 *
 * @author fplacanica
 */
public class InsuranceTransactionList {

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
    public static float[] columnWidths2 = new float[]{5f, 4f, 20f, 20f, 20f, 20f, 25f, 8f, 8f};

    /**
     *
     */
    public static float[] columnWidths3 = new float[]{4f, 15f, 20f, 20f, 20f, 25f, 8f, 8f};

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
    final String intestazionePdf = "Insurance Transaction List";
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
     * Costructor
     */
    public InsuranceTransactionList() {

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
     * @param wuslist
     * @param colonne
     * @param datereport1
     * @param datereport2
     * @return
     */
    public String receipt(String path, ArrayList<InsuranceTransactionList_value> wuslist, ArrayList<String> colonne, String datereport1, String datereport2) {

        //String outputfile = "InsuranceTransacionList.pdf";
        try {
            File pdf = new File(path + generaId(50) + "InsuranceTransacionList.pdf");
            Document document = new Document(A4, 20, 20, 20, 20);
            try(OutputStream ou = new FileOutputStream(pdf)){
            PdfWriter wr = getInstance(document, ou);
            document.open();

            for(int w=0;w<wuslist.size();w++){
                
                InsuranceTransactionList_value wus = wuslist.get(w);
            
            PdfPTable table = new PdfPTable(2);
            table.setWidths(columnWidths0);
            table.setWidthPercentage(100);

    
            Phrase phrase1 = new Phrase();
            phrase1.add(new Chunk(intestazionePdf + " From " + datereport1 + " To " + datereport2, f3_bold));
            PdfPCell cell1 = new PdfPCell(phrase1);
            cell1.setBorder(NO_BORDER);

            //Paragraph pa1 = new Paragraph(new Phrase("Insurance Transaction List" + datereport1 + "  " + datereport2, f2_normal));
            Paragraph pa1 = new Paragraph(new Phrase("", f2_normal));
            pa1.setAlignment(ALIGN_RIGHT);
            PdfPCell cell2 = new PdfPCell(pa1);
            cell2.setBorder(NO_BORDER);
            cell2.setHorizontalAlignment(ALIGN_RIGHT);

            Phrase phrase3 = new Phrase();
            phrase3.add(new Chunk("\n " + wus.getId_filiale() + " " + wus.getDe_filiale(), f3_normal));
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

            PdfPTable table2 = new PdfPTable(colonne.size());
            table2.setWidths(columnWidths2);
            table2.setWidthPercentage(100);

            PdfPCell[] list = new PdfPCell[colonne.size()];
            //mi scandisco le colonne
            for (int j = 0; j < colonne.size(); j++) {
                Phrase phraset1 = new Phrase();
                phraset1.add(new Chunk(colonne.get(j), f4_bold));
                PdfPCell cellt1 = new PdfPCell(phraset1);
                cellt1.setHorizontalAlignment(ALIGN_RIGHT);
                cellt1.setBorder(BOTTOM | TOP);
                cellt1.setFixedHeight(20f);
                cellt1.setBackgroundColor(LIGHT_GRAY);
                //cellt1.setBorder(Rectangle.BOTTOM);
                cellt1.setBorderWidth(0.7f);
                if (j == 1 || j == 2 || j == 3 || j == 4 || j == 5 || j == 6) {
                    cellt1.setHorizontalAlignment(ALIGN_LEFT);
                }
                list[j] = cellt1;

            }

            PdfPTable table3 = new PdfPTable(colonne.size());
            table3.setWidths(columnWidths2);
            table3.setWidthPercentage(100);

                for (PdfPCell list1 : list) {
                    PdfPCell temp = (PdfPCell) (list1);
                    table3.addCell(temp);
                }

            document.add(table3);

            LineSeparator sep = new LineSeparator();
            sep.setOffset(-2);
            sep.setLineWidth((float) 0.5);

            //  document.add(table2);
            // document.add(sep);
            ArrayList<InsuranceTransactionList_value> dati = wus.getDati();
            PdfPCell cell;
            Phrase phraset;
            PdfPCell cellt;

            float total = 0;

            int numtranin = 0, numdeletran = 0, numtranout = 0, numtotaltran = 0;
            float availableSpace;

            for (int i = 0; i < dati.size(); i++) {

                table3 = new PdfPTable(colonne.size());
                table3.setWidths(columnWidths2);
                table3.setWidthPercentage(100);

                availableSpace = wr.getVerticalPosition(true) - document.bottomMargin();

                InsuranceTransactionList_value temp = dati.get(i);

                if (temp.getInout().equals("1") || temp.getInout().equals("3")) {
                    numtranin++;
                } else if (temp.getInout().equals("2") || temp.getInout().equals("4")) {
                    numtranout++;
                }

                if (temp.getAnnullato().equals("1")) {
                    numdeletran++;
                }

                if(temp.getAnnullato().equals("1")){
                    
                }else{
                total += ff(formatDoubleforMysql(temp.getTotal()));
                }

                if (temp.getAnnullato().equals("1")) {
                    phraset = new Phrase();
                    phraset.add(new Chunk("D", f3_bold));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setBorder(NO_BORDER);
                    table3.addCell(cellt);
                } else {
                    phraset = new Phrase();
                    phraset.add(new Chunk("", f3_normal));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setBorder(NO_BORDER);
                    table3.addCell(cellt);

                }
                phraset = new Phrase();
                phraset.add(new Chunk(temp.getTill(), f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(NO_BORDER);
                table3.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(temp.getDateTime(), f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_LEFT);
                cellt.setBorder(NO_BORDER);
                table3.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(temp.getCategoryOfTransaction(), f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_LEFT);
                cellt.setBorder(NO_BORDER);
                table3.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(temp.getInsuranceNumber(), f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_LEFT);
                cellt.setBorder(NO_BORDER);
                table3.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(temp.getDescr1(), f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_LEFT);
                cellt.setBorder(NO_BORDER);
                table3.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(temp.getStartDate(), f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_LEFT);
                cellt.setBorder(NO_BORDER);
                table3.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(temp.getTotal(), f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(NO_BORDER);
                table3.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(temp.getUser(), f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(NO_BORDER);
                table3.addCell(cellt);

                /////2 linea
                phraset = new Phrase();
                phraset.add(new Chunk("", f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(NO_BORDER);
                table3.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk("", f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(NO_BORDER);
                table3.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk("", f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(NO_BORDER);
                table3.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk("", f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(NO_BORDER);
                table3.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(temp.getDescr2(), f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_LEFT);
                cellt.setBorder(NO_BORDER);
                table3.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(temp.getEndDate(), f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_LEFT);
                cellt.setBorder(NO_BORDER);
                table3.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk("", f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(NO_BORDER);
                table3.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk("", f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(NO_BORDER);
                table3.addCell(cellt);
                //// fine 2 linea

                /////3 linea
                phraset = new Phrase();
                phraset.add(new Chunk("", f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(NO_BORDER);
                table3.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk("", f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(NO_BORDER);
                table3.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk("", f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(NO_BORDER);
                table3.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk("", f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(NO_BORDER);
                table3.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(temp.getDescr3(), f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_LEFT);
                cellt.setBorder(NO_BORDER);
                table3.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk("", f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(NO_BORDER);
                table3.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk("", f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(NO_BORDER);
                table3.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk("", f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(NO_BORDER);
                table3.addCell(cellt);
                //// fine 3 linea

                document.add(table3);
                
                LineSeparator ls = new LineSeparator();
                 ls.setLineWidth(0.7f);
            document.add(ls);
                

                if ((availableSpace < 80) || i == dati.size() - 1) {

                    //footer
                    PdfContentByte cb = wr.getDirectContent();
                        showTextAligned(cb, ALIGN_RIGHT, footer(valueOf(numtranin), valueOf(numdeletran)),
                            (document.right() - document.left()) / 2 + document.leftMargin(),
                            document.bottom() + 10, 0);

                        showTextAligned(cb, ALIGN_RIGHT, footer2(valueOf(numtranout), valueOf(numtranin + numtranout - numdeletran)),
                            (document.right() - document.left()) / 2 + document.leftMargin(),
                            document.bottom(), 0);

                    //
                    numtranin = 0;
                    numdeletran = 0;
                    numtranout = 0;
                    numtotaltran = 0;

                }

            }

            //linea totali
            PdfPTable table4 = new PdfPTable(colonne.size());
            table4.setWidths(columnWidths2);
            table4.setWidthPercentage(100);

            //linea totale
            LineSeparator ls = new LineSeparator();
            ls.setLineWidth(0.7f);
            document.add(ls);

            phraset = new Phrase();
            phraset.add(new Chunk("", f3_normal));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setBorder(BOTTOM);
            table4.addCell(cellt);

            phraset = new Phrase();
            phraset.add(new Chunk("", f3_normal));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setBorder(BOTTOM);
            table4.addCell(cellt);

            phraset = new Phrase();
            phraset.add(new Chunk("", f3_normal));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setBorder(BOTTOM);
            table4.addCell(cellt);

            phraset = new Phrase();
            phraset.add(new Chunk("", f3_normal));
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
            phraset.add(new Chunk("Total from transaction listed", f4_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_LEFT);
            cellt.setBorder(BOTTOM);
            table4.addCell(cellt);

            phraset = new Phrase();
            phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(total, 2)), f4_bold));
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
            ls.setLineWidth(0.7f);
            document.add(ls);
            
            document.newPage();
            
            }

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
     * @param wuslist
     * @param colonne
     * @param datereport1
     * @param datereport2
     * @return
     */
    public String receiptexcel(String path, ArrayList<InsuranceTransactionList_value> wuslist, ArrayList<String> colonne, String datereport1, String datereport2) {

        //String outputfile = "InsuranceTransacionList.pdf";
        try {
            File pdf = new File(path + generaId(50) + "InsuranceTransactionlist.xls");
            HSSFWorkbook workbook = new HSSFWorkbook();
            
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

            
            for(int w=0;w<wuslist.size();w++){
                
                InsuranceTransactionList_value wus = wuslist.get(w);
            
                HSSFSheet sheet = workbook.createSheet(wus.getId_filiale());
                
            Row rowP = sheet.createRow((short) 1);

            Cell cl = rowP.createCell(1);
            cl.setCellStyle(style1);
            cl.setCellValue(intestazionePdf + " From " + datereport1 + " to " + datereport2);

//            Cell cl2 = rowP.createCell(6);
//            cl2.setCellStyle(style2);
//            cl2.setCellValue("");
            Row row = sheet.createRow((short) 3);
            row.createCell(1).setCellValue(wus.getId_filiale() + " " + wus.getDe_filiale());

            Row row66 = sheet.createRow((short) 7);

            int cntriga = 10;

            //mi scandisco le colonne
            for (int j = 0; j < colonne.size(); j++) {
                Cell cl5 = row66.createCell(j + 1);
                cl5.setCellStyle(style3);
                if (j == 1 || j == 2 || j == 3 || j == 4 || j == 5 || j == 6) {
                    cl5.setCellStyle(style3left);
                }
                cl5.setCellValue(colonne.get(j));

            }

            ArrayList<InsuranceTransactionList_value> dati = wus.getDati();

            float total = 0;

            int numtranin = 0, numdeletran = 0, numtranout = 0, numtotaltran = 0;

            for (int i = 0; i < dati.size(); i++) {

                cntriga++;

                Row row6 = sheet.createRow((short) cntriga);

                InsuranceTransactionList_value temp = dati.get(i);

                if (temp.getInout().equals("1") || temp.getInout().equals("3")) {
                    numtranin++;
                } else if (temp.getInout().equals("2") || temp.getInout().equals("4")) {
                    numtranout++;
                }

                if (temp.getAnnullato().equals("1")) {
                    numdeletran++;
                    Cell f1 = row6.createCell(1);
                    f1.setCellStyle(style3);
                    f1.setCellValue("D");
                }

                if(temp.getAnnullato().equals("1")){
                
                } else{
                total += ff(formatDoubleforMysql(temp.getTotal()));
                }

                Cell f1 = row6.createCell(2);
                f1.setCellStyle(style4);
                f1.setCellValue(temp.getTill());

                Cell f2 = row6.createCell(3);
                f2.setCellStyle(style4left);
                f2.setCellValue(temp.getDateTime());

                Cell f3 = row6.createCell(4);
                f3.setCellStyle(style4left);
                f3.setCellValue(temp.getCategoryOfTransaction());

                Cell f5 = row6.createCell(5);
                f5.setCellStyle(style4left);
                f5.setCellValue(temp.getInsuranceNumber());

                Cell f7 = row6.createCell(6);
                f7.setCellStyle(style4left);
                f7.setCellValue(temp.getDescr1() + " " + temp.getDescr2() + " " + temp.getDescr3());

                Cell f8 = row6.createCell(7);
                f8.setCellStyle(style4left);
                f8.setCellValue(temp.getStartDate() + " " + temp.getEndDate());

                Cell f8bis = row6.createCell(8);
                f8bis.setCellStyle(style4);
                f8bis.setCellValue(temp.getTotal());

                Cell f9 = row6.createCell(9);
                f9.setCellStyle(style4);
                f9.setCellValue(temp.getUser());

                /////3 linea
                //// fine 3 linea
                cntriga++;
                cntriga++;
            }

            //linea totali
            cntriga++;
            cntriga++;
            cntriga++;

            Row row777bis = sheet.createRow((short) cntriga);

            Cell f307 = row777bis.createCell(7);
            f307.setCellStyle(style3left);
            f307.setCellValue("Total from transaction listed");

            Cell f3072 = row777bis.createCell(8);
            f3072.setCellStyle(style3);
            f3072.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(total,2)));

            cntriga++;
            cntriga++;
            cntriga++;
            cntriga++;

            Row row777bis1 = sheet.createRow((short) cntriga);

            Cell f307b = row777bis1.createCell(5);
            f307b.setCellStyle(style3);
            f307b.setCellValue("No. of Transaction In");

            Cell f3072b = row777bis1.createCell(6);
            f3072b.setCellStyle(style3);
            f3072b.setCellValue("No. of Deleted Transaction");

            cntriga++;

            Row row777 = sheet.createRow((short) cntriga);

            Cell f30 = row777.createCell(5);
            f30.setCellStyle(style3);
            f30.setCellValue(valueOf(numtranin));

            Cell f301 = row777.createCell(6);
            f301.setCellStyle(style3);
            f301.setCellValue(valueOf(numdeletran));

            cntriga++;
            cntriga++;
            Row row888bis = sheet.createRow((short) cntriga);

            Cell f318 = row888bis.createCell(5);
            f318.setCellStyle(style3);
            f318.setCellValue("No. of Transaction Out");

            Cell f3182 = row888bis.createCell(6);
            f3182.setCellStyle(style3);
            f3182.setCellValue("No. of Total Transaction ");

            cntriga++;
            Row row888 = sheet.createRow((short) cntriga);

            Cell f31 = row888.createCell(5);
            f31.setCellStyle(style3);
            f31.setCellValue(valueOf(numtranout));

            Cell f312 = row888.createCell(6);
            f312.setCellStyle(style3);
            f312.setCellValue(valueOf(numtranin + numtranout - numdeletran));

            cntriga++;
            cntriga++;

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

            }
            
            try (FileOutputStream out = new FileOutputStream(pdf)) {
                workbook.write(out);
            }
            String base64 = new String(encodeBase64(readFileToByteArray(pdf)));
            pdf.delete();
            return base64;

        } catch (IOException ex) {
            insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
        }
        return null;
    }

    //sistemare il footer e rendere i totali corretti
    private Phrase footer(String tot1, String tot2) {
        Font ffont = new Font(f3_normal);
        Phrase p = new Phrase("No. of Transaction In        " + tot1 + "         No. of Deleted Transaction      " + tot2 + " ", ffont);
        return p;
    }

    private Phrase footer2(String tot1, String tot2) {
        Font ffont = new Font(f3_normal);
        Phrase p = new Phrase("No. of Transaction Out      " + tot1 + "         No. of Total Transaction        " + tot2 + " ", ffont);
        return p;
    }

    // fine footer
}
