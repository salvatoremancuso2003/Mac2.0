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
import static rc.so.util.Engine.insertTR;
import static rc.so.util.Utility.ff;
import static rc.so.util.Utility.formatDoubleforMysql;
import static rc.so.util.Utility.formatMysqltoDisplay;
import static rc.so.util.Utility.generaId;
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
import static org.apache.poi.ss.usermodel.BorderStyle.THIN;
import static org.apache.poi.ss.usermodel.HorizontalAlignment.CENTER;
/**
 *
 * @author vcrugliano
 */
public class ToPosBankSheet {

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
    public static final float[] columnWidths3 = new float[]{18f, 15f,19f,5f,5f,25f}; 

    /**
     *
     */
    public static float[] columnWidths2 = null;
    final String intestazionePdf = "To Banking POS Sheet";
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

    Font f0_bold, f1_bold, f2_bold, f1_normal, f2_normal, f3_normal, f3_bold, f4_bold, f5_normal, f5_bold, f6_normal, f6_bold;

    /**
     * Costructor
     */
    public ToPosBankSheet() {

        this.f0_bold = getFont(HELVETICA, WINANSI, 14.04f, BOLD);
        this.f1_bold = getFont(HELVETICA, WINANSI, 9.96f, BOLD);
        this.f2_bold = getFont(HELVETICA, WINANSI, 8f, BOLD);
        this.f3_bold = getFont(HELVETICA, WINANSI, 8f, BOLDITALIC);
        this.f4_bold = getFont(HELVETICA, WINANSI, 6.96f, BOLD);
        this.f1_normal = getFont(HELVETICA, WINANSI, 9.96f, NORMAL);
        this.f2_normal = getFont(HELVETICA, WINANSI, 8f, NORMAL);
        this.f3_normal = getFont(HELVETICA, WINANSI, 6.96f, NORMAL);
        this.f5_normal = getFont(HELVETICA, WINANSI, 6f, NORMAL);
        this.f5_bold = getFont(HELVETICA, WINANSI, 6f, BOLD);
        this.f6_normal = getFont(HELVETICA, WINANSI, 5.5f, NORMAL);
        this.f6_bold = getFont(HELVETICA, WINANSI, 5.5f, BOLD);

    }

    /**
     *
     * @param path
     * @param siq
     * @param colonne
     * @param datereport
     * @return
     */
    public String receipt(String path,ToBankingSheet_value siq, ArrayList<String> colonne,String datereport) {
        
         Phrase vuoto1 = new Phrase("\n");
        PdfPCell cellavuota = new PdfPCell(vuoto1);
        cellavuota.setBorder(NO_BORDER);

       // String outputfile = "ToBankingSheet.pdf";

        columnWidths2 = new float[colonne.size()];

        for (int c = 0; c < colonne.size(); c++) {
            columnWidths2[c] = 10f;
        }

        try {
            
          
              File pdf = new File(path + generaId(50) + "ToBankingSheet.pdf");
            Document document = new Document(A4, 20, 20, 20, 20);
            try(OutputStream ou = new FileOutputStream(pdf)){
            PdfWriter wr = getInstance(document, ou);
            document.open();

            PdfPTable table = new PdfPTable(2);
            table.setWidths(columnWidths0);
            table.setWidthPercentage(100);

            Phrase phrase1 = new Phrase();
            phrase1.add(new Chunk(intestazionePdf + " ", f3_bold));
            PdfPCell cell1 = new PdfPCell(phrase1);
            cell1.setBorder(NO_BORDER);

            Paragraph pa1 = new Paragraph(new Phrase("Transfer "+siq.getNumTransfer() +" "+ datereport, f2_normal));
            pa1.setAlignment(ALIGN_CENTER);
            PdfPCell cell2 = new PdfPCell(pa1);
            cell2.setBorder(NO_BORDER);
            cell2.setHorizontalAlignment(ALIGN_RIGHT);

            Phrase phrase3 = new Phrase();
            phrase3.add(new Chunk(siq.getId_filiale() + " " + siq.getDe_filiale(), f2_normal));
            PdfPCell cell3 = new PdfPCell(phrase3);
            cell3.setBorder(NO_BORDER);

            Phrase phrase4 = new Phrase();
            phrase4.add(new Chunk("\n From : " + siq.getFromsafe() + " \n To : " + siq.getTobank() + " \n User: " + siq.getPinuser(), f2_normal));
            PdfPCell cell4 = new PdfPCell(phrase4);
            cell4.setBorder(NO_BORDER);

            table.addCell(cell1);            
            table.addCell(cell2);            
            table.addCell(cell3);
            table.addCell(cellavuota);
            table.addCell(cell4);
            table.addCell(cellavuota);
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
                phraset1.add(new Chunk(colonne.get(j), f5_bold));
                PdfPCell cellt1 = new PdfPCell(phraset1);
                cellt1.setHorizontalAlignment(ALIGN_CENTER);
                cellt1.setBorder(BOTTOM);

                cellt1.setBackgroundColor(LIGHT_GRAY);
                //cellt1.setBorder(Rectangle.BOTTOM);
                cellt1.setBorderWidth(0.7f);

                list[j] = cellt1;

            }
            
            
            PdfPTable table6 = new PdfPTable(columnWidths3);           
            table6.setWidthPercentage(100);

            for (int j = 0; j < 6; j++) {

                
                if(j==1){
                   Phrase phraset1 = new Phrase();
                phraset1.add(new Chunk("04-Cash Advance", f5_bold));
                PdfPCell cellt1 = new PdfPCell(phraset1);
                cellt1.setHorizontalAlignment(ALIGN_CENTER);
                cellt1.setBorder(TOP);
                cellt1.setBackgroundColor(LIGHT_GRAY);
                //cellt1.setBorder(Rectangle.BOTTOM);
                cellt1.setBorderWidth(0.7f);
                table6.addCell(cellt1);
                }
               else if(j==2){
                    Phrase phraset1 = new Phrase();
                phraset1.add(new Chunk("06-Credit Card COP", f5_bold));
                PdfPCell cellt1 = new PdfPCell(phraset1);
                cellt1.setHorizontalAlignment(ALIGN_CENTER);
                cellt1.setBorder(TOP);
                cellt1.setBackgroundColor(LIGHT_GRAY);
                //cellt1.setBorder(Rectangle.BOTTOM);
                cellt1.setBorderWidth(0.7f);
                table6.addCell(cellt1);
                }
                 else if(j==3){
                    Phrase phraset1 = new Phrase();
                phraset1.add(new Chunk("07-Bancomat COP", f5_bold));
                PdfPCell cellt1 = new PdfPCell(phraset1);
                cellt1.setHorizontalAlignment(ALIGN_CENTER);
                cellt1.setBorder(TOP);
                cellt1.setBackgroundColor(LIGHT_GRAY);
                //cellt1.setBorder(Rectangle.BOTTOM);
                cellt1.setBorderWidth(0.7f);
                table6.addCell(cellt1);
                }
                   else if(j==4){
                    Phrase phraset1 = new Phrase();
                phraset1.add(new Chunk("08-Bank Account", f5_bold));
                PdfPCell cellt1 = new PdfPCell(phraset1);
                cellt1.setHorizontalAlignment(ALIGN_CENTER);
                cellt1.setBorder(TOP);
                cellt1.setBackgroundColor(LIGHT_GRAY);
                //cellt1.setBorder(Rectangle.BOTTOM);
                cellt1.setBorderWidth(0.7f);
                table6.addCell(cellt1);
                }
               else{
               Phrase phraset1 = new Phrase();
                phraset1.add(new Chunk("", f5_bold));
                PdfPCell cellt1 = new PdfPCell(phraset1);
                cellt1.setHorizontalAlignment(ALIGN_LEFT);
                cellt1.setBorder(TOP);

                cellt1.setBackgroundColor(LIGHT_GRAY);
                //cellt1.setBorder(Rectangle.BOTTOM);
                cellt1.setBorderWidth(0.7f);
                table6.addCell(cellt1);
               }
            }
            
             document.add(table6);
            

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
            //  document.add(sep);
            ArrayList<ToBankingSheet_value> dati = siq.getDati();

            PdfPCell cell;
            Phrase phraset;
            PdfPCell cellt;

            table3 = new PdfPTable(colonne.size());
            table3.setWidths(columnWidths2);
            table3.setWidthPercentage(100);

            float branchtotal = 0, spreadtotal = 0, perctotal = 0;

            for (int i = 0; i < dati.size(); i++) {
                ToBankingSheet_value temp =  dati.get(i);

                ArrayList<String> datitemp = temp.getDati_string();

                phraset = new Phrase();
                phraset.add(new Chunk(temp.getCurrency(), f2_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_LEFT);
                cellt.setBorder(BOTTOM);
                table3.addCell(cellt);
                boolean perc = false;

                for (int n = 1; n < colonne.size(); n++) {

                    switch (colonne.get(n)) {
                        case "Branch Total":
                            branchtotal += ff(formatDoubleforMysql(datitemp.get(n)));
                            break;
                        case "Spread":
                            spreadtotal += ff(formatDoubleforMysql(datitemp.get(n)));
                            break;
                        case "%":
                            perctotal += ff(formatDoubleforMysql(datitemp.get(n)));
                            perc = true;
                            break;
                        default:
                            break;
                    }

                    phraset = new Phrase();
                    if (!perc) {
                        phraset.add(new Chunk((datitemp.get(n)), f2_normal));
                    } else {
                        phraset.add(new Chunk((datitemp.get(n)) + " %", f2_normal));
                    }
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setBorder(BOTTOM);
                    table3.addCell(cellt);

                }

            }

            document.add(table3);
            
            LineSeparator ls = new LineSeparator();
            ls.setLineWidth(0.7f);
            ls.setOffset(-1f);
            document.add(ls);

            table3 = new PdfPTable(colonne.size());
            table3.setWidths(columnWidths2);
            table3.setWidthPercentage(100);
           
             phraset = new Phrase();
                phraset.add(new Chunk("", f1_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_LEFT);
                cellt.setBorder(BOTTOM);
                table3.addCell(cellt);

            for (int n = 1; n < colonne.size(); n++) {

                
                if(n==1){
                    phraset = new Phrase();
                phraset.add(new Chunk("Total", f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_LEFT);
                cellt.setBorder(BOTTOM);
                table3.addCell(cellt);
                }
                
               else if (colonne.get(n).equals("Branch Total")) {
                     phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(valueOf(branchtotal)), f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table3.addCell(cellt);

                } else if (colonne.get(n).equals("Spread")) {
                     phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(valueOf(spreadtotal)), f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table3.addCell(cellt);

                } else if (colonne.get(n).equals("%")) {
                     phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(valueOf(perctotal))+"%", f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table3.addCell(cellt);

                }
                else{
                     phraset = new Phrase();
                phraset.add(new Chunk("", f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_LEFT);
                cellt.setBorder(BOTTOM);
                table3.addCell(cellt);
                }
                
             
                
            }
            
               document.add(table3);

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
     * @param siq
     * @param colonne
     * @param datereport
     * @return
     */
    public String receiptexcel(String path,ToBankingSheet_value siq, ArrayList<String> colonne,String datereport) {
        
       

       // String outputfile = "ToBankingSheet.pdf";

        columnWidths2 = new float[colonne.size()];

        for (int c = 0; c < colonne.size(); c++) {
            columnWidths2[c] = 10f;
        }

        try {
             File pdf = new File(path + generaId(50) + "ToBankingSheet.xls");
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet("ToBankingSheet");
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
            style3.setAlignment(CENTER);
            style3.setBorderTop(THIN);
            style3.setBorderBottom(THIN);

            HSSFFont font4 = workbook.createFont();
            font4.setFontName(FONT_ARIAL);
            font4.setFontHeightInPoints((short) 10);

            HSSFCellStyle style4 = (HSSFCellStyle) workbook.createCellStyle();
            style4.setAlignment(CENTER);
            style4.setBorderTop(THIN);
            style4.setBorderBottom(THIN);

            Row rowP = sheet.createRow((short) 1);

            Cell cl = rowP.createCell(1);
            cl.setCellStyle(style1);
            cl.setCellValue(intestazionePdf + " "+ datereport);

//            Cell cl2 = rowP.createCell(6);
//            cl2.setCellStyle(style2);
//            cl2.setCellValue(datereport);

            Row row = sheet.createRow((short) 3);
            row.createCell(1).setCellValue(siq.getId_filiale() + " " + siq.getDe_filiale());

            Row row66 = sheet.createRow((short) 7);

           
            //mi scandisco le colonne
            for (int j = 0; j < colonne.size(); j++) {
               Cell cl5 = row66.createCell(j + 1);
                    cl5.setCellStyle(style3);
                    cl5.setCellValue(colonne.get(j));

            }
            
            
            Row row77 = sheet.createRow((short) 5);
            for (int j = 0; j < 6; j++) {

                
                if(j==1){
                    
                     Cell cl5 = row77.createCell(j + 1);
                        cl5.setCellStyle(style3);
                        cl5.setCellValue("Notes");
                    
                 
                }
               else if(j==2){
                     Cell cl5 = row77.createCell(j + 2);
                        cl5.setCellStyle(style3);
                        cl5.setCellValue("Euro TC / Travel Cheques");
                }
               else{
//               Phrase phraset1 = new Phrase();
//                phraset1.add(new Chunk("", f5_bold));
//                PdfPCell cellt1 = new PdfPCell(phraset1);
//                cellt1.setHorizontalAlignment(Element.ALIGN_LEFT);
//                cellt1.setBorder( Rectangle.TOP);
//
//                cellt1.setBackgroundColor(BaseColor.LIGHT_GRAY);
//                //cellt1.setBorder(Rectangle.BOTTOM);
//                cellt1.setBorderWidth(0.7f);
//                table6.addCell(cellt1);
               }
            }
            
            int cntriga=8;

          

            //  document.add(table2);
            //  document.add(sep);
            ArrayList<ToBankingSheet_value> dati = siq.getDati();


            float branchtotal = 0, spreadtotal = 0, perctotal = 0;

            for (int i = 0; i < dati.size(); i++) {
                cntriga++;
                 Row row6 = sheet.createRow((short) cntriga);
                
                ToBankingSheet_value temp = dati.get(i);

                ArrayList<String> datitemp = temp.getDati_string();

                
                 Cell f1 = row6.createCell(1);
                    f1.setCellStyle(style4);
                    f1.setCellValue(temp.getCurrency());
                
              
                boolean perc = false;

                for (int n = 1; n < colonne.size(); n++) {

                    switch (colonne.get(n)) {
                        case "Branch Total":
                            branchtotal += ff(formatDoubleforMysql(datitemp.get(n)));
                            break;
                        case "Spread":
                            spreadtotal += ff(formatDoubleforMysql(datitemp.get(n)));
                            break;
                        case "%":
                            perctotal += ff(formatDoubleforMysql(datitemp.get(n)));
                            perc = true;
                            break;
                        default:
                            break;
                    }

                      Cell f2 = row6.createCell(n+1);
                        f2.setCellStyle(style4);
                    
                    
                    if (!perc) {
                         f2.setCellValue((datitemp.get(n)));
                       
                    } else {
                         f2.setCellValue((datitemp.get(n)) + " %");
                       
                    }
                    

                }

            }

             cntriga++;
                cntriga++;

                Row row9 = sheet.createRow((short) cntriga);

            for (int n = 1; n < colonne.size(); n++) {

                
                if(n==1){
                     Cell f7 = row9.createCell(n + 1);
                        f7.setCellStyle(style4);
                        f7.setCellValue("Total");
                }
                
               else if (colonne.get(n).equals("Branch Total")) {
                   
                   
                      Cell f7 = row9.createCell(n + 1);
                        f7.setCellStyle(style4);
                        f7.setCellValue(formatMysqltoDisplay(valueOf(branchtotal)));
                   
                    

                } else if (colonne.get(n).equals("Spread")) {
                    
                     Cell f7 = row9.createCell(n + 1);
                        f7.setCellStyle(style4);
                        f7.setCellValue(formatMysqltoDisplay(valueOf(spreadtotal)));
                    
                    

                } else if (colonne.get(n).equals("%")) {
                    
                     Cell f7 = row9.createCell(n + 1);
                        f7.setCellStyle(style4);
                        f7.setCellValue(formatMysqltoDisplay(valueOf(perctotal))+"%");
                    
                   

                }
                else{
//                     phraset = new Phrase();
//                phraset.add(new Chunk("", f3_normal));
//                cellt = new PdfPCell(phraset);
//                cellt.setHorizontalAlignment(Element.ALIGN_LEFT);
//                cellt.setBorder(Rectangle.BOTTOM);
//                table3.addCell(cellt);
                }
                
             
                
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
            
            try (FileOutputStream out = new FileOutputStream(pdf)) {
                workbook.write(out);
            }
            String base64 = new String(encodeBase64(readFileToByteArray(pdf)));
            pdf.delete();
            return base64;
            
        } catch (IOException  ex) {
            insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
        }
        return null;
    }


}
