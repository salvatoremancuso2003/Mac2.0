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
import static rc.so.util.Engine.insertTR;
import static rc.so.util.Utility.ff;
import static rc.so.util.Utility.formatMysqltoDisplay;
import static rc.so.util.Utility.generaId;
import static rc.so.util.Utility.roundDouble;
import static rc.so.util.Utility.roundDoubleandFormat;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import static java.lang.Integer.parseInt;
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
import static org.apache.poi.ss.usermodel.BorderStyle.THIN;
import static org.apache.poi.ss.usermodel.HorizontalAlignment.LEFT;
import static org.apache.poi.ss.usermodel.HorizontalAlignment.RIGHT;
/**
 *
 * @author fplacanica
 */
public class C_WesternUnion {

    //column

    /**
     *
     */
    public static final float[] columnWidths0 = new float[]{80f, 20f};

    /**
     *
     */
    public static final float[] columnWidths0bis = new float[]{90f, 10f};

    /**
     *
     */
    public static final float[] columnWidths1 = new float[]{60f, 30f};

    /**
     *
     */
    public static float[] columnWidths2 = new float[]{10f, 10f, 10f, 10f, 10f, 10f};

    /**
     *
     */
    public static float[] columnWidths3 = new float[]{5f, 15f, 10f, 10f, 10f, 10f, 8f, 8f, 8f};

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
    final String intestazionePdf = "Western Union";
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
     ** Constructor
     */
    public C_WesternUnion() {

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
     * @param wus
     * @param colonne
     * @param filialisel
     * @return
     */
    public String receipt(String path, C_WesternUnion_value wus, ArrayList<String> colonne, ArrayList<String[]> filialisel) {

        try {
            File pdf = new File(path + generaId(50) + "C_WesternUnion.pdf");
            Document document = new Document(A4.rotate(), 20, 20, 20, 20);
            try(OutputStream ou = new FileOutputStream(pdf)){
            PdfWriter wr = getInstance(document, ou);
            document.open();

            PdfPTable table = new PdfPTable(2);
            table.setWidths(columnWidths0);
            table.setWidthPercentage(100);

            Phrase phrase1 = new Phrase();
            phrase1.add(new Chunk(intestazionePdf + " from " + wus.getDataDa() + " to  " + wus.getDataA(), f3_bold));
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

            String elencofiliali = "";
            if (wus.isFull()) {
                elencofiliali = "All Branches";
            } else {
                for (int v = 0; v < filialisel.size(); v++) {
                    String[] temp = filialisel.get(v);
                    elencofiliali = elencofiliali + " - " + temp[1];
                }
            }

            PdfPTable tablebis = new PdfPTable(2);
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
if(j==0){
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
            ArrayList<C_WesternUnion_value> dati = wus.getDati();
            PdfPCell cell;
            Phrase phraset;
            PdfPCell cellt;

            double totSend = 0;
            int totTransSend = 0;
            double toReceive = 0;
            int totTransReceive = 0;
            double total = 0;

            for (int i = 0; i < dati.size(); i++) {

                table3 = new PdfPTable(colonne.size());
                table3.setWidths(columnWidths2);
                table3.setWidthPercentage(100);

                C_WesternUnion_value temp = dati.get(i);

                totSend += ff(temp.getToSend());
                totTransSend += parseInt(temp.getnTransSend());
                toReceive += ff(temp.getToReceive());
                totTransReceive += parseInt(temp.getnTransReceive());
                total += ff(temp.getTotal());

                phraset = new Phrase();
                phraset.add(new Chunk(temp.getData(), f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_LEFT);
                cellt.setBorder(NO_BORDER);
                table3.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(temp.getToSend()), f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(NO_BORDER);
                table3.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(temp.getnTransSend(), f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(NO_BORDER);
                table3.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(temp.getToReceive()), f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(NO_BORDER);
                table3.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(temp.getnTransReceive(), f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(NO_BORDER);
                table3.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(temp.getTotal()), f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(NO_BORDER);
                table3.addCell(cellt);

                document.add(table3);

            }
            
            
             totSend =roundDouble(totSend, 2);
                totTransSend =(totTransSend);
                toReceive =roundDouble(toReceive, 2);
                totTransReceive =(totTransReceive);
                total =roundDouble(total, 2);
            

            //linea totali
            PdfPTable table4 = new PdfPTable(colonne.size());
            table4.setWidths(columnWidths2);
            table4.setWidthPercentage(100);

            //linea totale
            LineSeparator ls = new LineSeparator();
            ls.setLineWidth(0.7f);
            document.add(ls);

            phraset = new Phrase();
            phraset.add(new Chunk("Total", f4_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_LEFT);
            cellt.setBorder(BOTTOM);
            table4.addCell(cellt);

            
            
            phraset = new Phrase();
            phraset.add(new Chunk(formatMysqltoDisplay((roundDoubleandFormat(totSend,2))), f4_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setBorder(BOTTOM);
            table4.addCell(cellt);

            phraset = new Phrase();
            phraset.add(new Chunk(formatMysqltoDisplay(valueOf((totTransSend))), f4_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setBorder(BOTTOM);
            table4.addCell(cellt);

            phraset = new Phrase();
            phraset.add(new Chunk(formatMysqltoDisplay((roundDoubleandFormat(toReceive,2))), f4_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setBorder(BOTTOM);
            table4.addCell(cellt);

            phraset = new Phrase();
            phraset.add(new Chunk(formatMysqltoDisplay(valueOf((totTransReceive))), f4_bold));
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

            table4 = new PdfPTable(colonne.size());
            table4.setWidths(columnWidths2);
            table4.setWidthPercentage(100);

            phraset = new Phrase();
            phraset.add(new Chunk("Cash Balance", f4_bold));
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
            phraset.add(new Chunk(formatMysqltoDisplay((roundDoubleandFormat(total,2))), f4_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setBorder(BOTTOM);
            table4.addCell(cellt);

            document.add(table4);

            //linea totale
            ls.setLineWidth(0.7f);
            document.add(ls);

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
     * @param wus
     * @param colonne
     * @param filialisel
     * @return
     */
    public String receiptexcel(String path, C_WesternUnion_value wus, ArrayList<String> colonne, ArrayList<String[]> filialisel) {

        try {
            File pdf = new File(path + generaId(50) + "C_WesternUnion.xls");
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet("C_WesternUnion");
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

            Row rowP = sheet.createRow((short) 1);

            Cell cl = rowP.createCell(1);
            cl.setCellStyle(style1);
            cl.setCellValue(intestazionePdf + " from " + wus.getDataDa() + " to  " + wus.getDataA());

            String elencofiliali = "";
            if (wus.isFull()) {
                elencofiliali = "All Branches";
            } else {
                for (int v = 0; v < filialisel.size(); v++) {
                    String[] temp = filialisel.get(v);
                    elencofiliali = elencofiliali + " - "  + temp[1];
                }
            }

            Row rowB = sheet.createRow((short) 3);
            Cell clb = rowB.createCell(1);
            clb.setCellStyle(style1);
            clb.setCellValue(elencofiliali);

            Row row66 = sheet.createRow((short) 5);

           
            //mi scandisco le colonne
            for (int j = 0; j < colonne.size(); j++) {
                Cell cl7 = row66.createCell(j + 1);
                cl7.setCellStyle(style3);
                if(j==0){
                cl7.setCellStyle(style3left);    
                }
                cl7.setCellValue(colonne.get(j));
            }

            ArrayList<C_WesternUnion_value> dati = wus.getDati();
            PdfPCell cell;
            Phrase phraset;
            PdfPCell cellt;

            double totSend = 0;
            int totTransSend = 0;
            double toReceive = 0;
            int totTransReceive = 0;
            double total = 0;
            int cntriga = 7;

            for (int i = 0; i < dati.size(); i++) {
                cntriga++;
                Row row6 = sheet.createRow((short) cntriga);

                C_WesternUnion_value temp =  dati.get(i);

                totSend += ff(temp.getToSend());
                totTransSend += parseInt(temp.getnTransSend());
                toReceive += ff(temp.getToReceive());
                totTransReceive += parseInt(temp.getnTransReceive());
                total += ff(temp.getTotal());
                
                

                Cell f1bis = row6.createCell(1);
                f1bis.setCellStyle(style4left);
                f1bis.setCellValue(temp.getData());

                Cell f2 = row6.createCell(2);
                f2.setCellStyle(style4);
                f2.setCellValue(formatMysqltoDisplay(temp.getToSend()));

                Cell f3 = row6.createCell(3);
                f3.setCellStyle(style4);
                f3.setCellValue(temp.getnTransSend());

                Cell f4 = row6.createCell(4);
                f4.setCellStyle(style4);
                f4.setCellValue(formatMysqltoDisplay(temp.getToReceive()));

                Cell f6 = row6.createCell(5);
                f6.setCellStyle(style4);
                f6.setCellValue(temp.getnTransReceive());

                Cell f7 = row6.createCell(6);
                f7.setCellStyle(style4);
                f7.setCellValue(formatMysqltoDisplay(temp.getTotal()));

            }
            
               totSend =roundDouble(totSend, 2);
                totTransSend =(totTransSend);
                toReceive =roundDouble(toReceive, 2);
                totTransReceive =(totTransReceive);
                total =roundDouble(total, 2);

            cntriga++;
            cntriga++;

            Row row7 = sheet.createRow((short) cntriga);

            Cell f18 = row7.createCell(1);
            f18.setCellStyle(style3left);
            f18.setCellValue("Total");

            Cell f19 = row7.createCell(2);
            f19.setCellStyle(style3);
            f19.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(totSend,2)));

            Cell f20 = row7.createCell(3);
            f20.setCellStyle(style3);
            f20.setCellValue(formatMysqltoDisplay(valueOf(totTransSend)));

            Cell f21 = row7.createCell(4);
            f21.setCellStyle(style3);
            f21.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(toReceive,2)));

            Cell f22 = row7.createCell(5);
            f22.setCellStyle(style3);
            f22.setCellValue(formatMysqltoDisplay(valueOf(totTransReceive)));

            cntriga++;

            Row row7bis = sheet.createRow((short) cntriga);

            Cell f23 = row7bis.createCell(1);
            f23.setCellStyle(style3left);
            f23.setCellValue("Cash Balance");

            Cell f24 = row7bis.createCell(6);
            f24.setCellStyle(style3);
            f24.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(total,2)));

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
        } catch (IOException ex) {
            insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
        }
        return null;

    }

//    public static void main(String[] args) {
//        C_WesternUnion nctl = new C_WesternUnion();
//
//       C_WesternUnion_value pdf = new C_WesternUnion_value();
//
//        ArrayList<String> alcolonne = new ArrayList<>();
//
//        alcolonne.add("Date");
//        alcolonne.add("To Send");
//        alcolonne.add("N. Trans.");
//        alcolonne.add("To Receive");
//        alcolonne.add("N. Trans");
//        alcolonne.add("W.U. Cash Balance");
//        ArrayList<C_WesternUnion_value> dati = new ArrayList<>();
//        dati = Engine.getC_WesternUnion();
//        pdf.setDataDa("19/05/2016");
//        pdf.setDataA("20/05/2016");
//        pdf.setDati(dati);
//        ArrayList<String[]> filiali= new ArrayList<>();        
//        String[]a = new String[2];
//        a[0]="117";
//        a[1]="Milano Malpensa";        
//        String[]b = new String[2];
//        b[0]="111";
//        b[1]="Milano piazza luifi di savoia";        
//        filiali.add(a);
//        filiali.add(b);
//
//        nctl.receipt(pdf, alcolonne,filiali);
//
//    }
}
