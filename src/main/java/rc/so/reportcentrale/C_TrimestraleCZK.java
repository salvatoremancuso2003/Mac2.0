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
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfWriter;
import static com.itextpdf.text.pdf.PdfWriter.getInstance;
import com.itextpdf.text.pdf.draw.LineSeparator;
import static rc.so.util.Engine.insertTR;
import static rc.so.util.Utility.fd;
import static rc.so.util.Utility.formatMysqltoDisplay;
import static rc.so.util.Utility.generaId;
import static rc.so.util.Utility.roundDoubleandFormat;
import java.io.File;
import java.io.FileNotFoundException;
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
import static org.apache.poi.ss.usermodel.BorderStyle.THIN;
import static org.apache.poi.ss.usermodel.HorizontalAlignment.LEFT;
import static org.apache.poi.ss.usermodel.HorizontalAlignment.RIGHT;
/**
 *
 * @author fplacanica
 */
public class C_TrimestraleCZK {

    //column

    /**
     *
     */
    public static final float[] columnWidths0 = new float[]{50f, 50f};

    /**
     *
     */
    public static final float[] columnWidths1 = new float[]{100f};

    /**
     *
     */
    public static float[] columnWidths2 = new float[]{10f, 10f, 10f, 10f};

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
    final String intestazionePdf = "NÁKUP A PRODEJ CIZÍ MENY";
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
    public C_TrimestraleCZK() {

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
     * @param tczk
     * @param colonne
     * @param anno
     * @param startpage
     * @return
     * @throws FileNotFoundException
     * @throws IOException
     */
    public String receipt(String path, C_TrimestraleCZK_value tczk, ArrayList<String> colonne, String anno, int startpage) throws FileNotFoundException, IOException {

        // String outputfile = "C_TrimestraleCZK.pdf";
        try {
            File pdffile = new File(path + generaId(50) + "C_TrimestraleCZK.pdf");
            try (OutputStream ou = new FileOutputStream(pdffile)) {
                Document document = new Document(A4, 20, 20, 20, 20);
                PdfWriter wr = getInstance(document, ou);
                document.open();
                PdfPTable table = new PdfPTable(2);
                table.setWidths(columnWidths0);
                table.setWidthPercentage(100);
                Phrase phrase1 = new Phrase();
                phrase1.add(new Chunk(intestazionePdf + " od " + tczk.getDataDa() + " do  " + tczk.getDataA(), f2_bold));
                PdfPCell cell1 = new PdfPCell(phrase1);
                cell1.setBorder(NO_BORDER);
                Paragraph pa1 = new Paragraph(new Phrase("", f2_bold));
                pa1.setAlignment(ALIGN_RIGHT);
                PdfPCell cell2 = new PdfPCell(pa1);
                cell2.setBorder(NO_BORDER);
                cell2.setHorizontalAlignment(ALIGN_RIGHT);
                //            Phrase phrase4 = new Phrase();
//            phrase4.add(new Chunk("", f3_normal));
//            PdfPCell cell4 = new PdfPCell(phrase4);
//            cell4.setBorder(Rectangle.NO_BORDER);
//  document.add(table);
Phrase phraset11 = new Phrase();
phraset11.add(new Chunk(tczk.getBranch(), f2_bold));
PdfPCell cellt11 = new PdfPCell(phraset11);
cellt11.setHorizontalAlignment(ALIGN_LEFT);
cellt11.setBorder(NO_BORDER);
phraset11 = new Phrase();
phraset11.add(new Chunk("", f2_bold));
PdfPCell cellt12 = new PdfPCell(phraset11);
cellt12.setHorizontalAlignment(ALIGN_RIGHT);
cellt12.setBorder(NO_BORDER);
table.addCell(cell1);
table.addCell(cell2);
table.addCell(cellt11);
table.addCell(cellt12);
document.add(table);
vuoto.setFont(f3_normal);
document.add(vuoto);
PdfPTable table111 = new PdfPTable(1);
table111.setWidths(columnWidths1);
table111.setWidthPercentage(100);
Phrase phrase111 = new Phrase();
phrase111.add(new Chunk("Držitel registrace predá vyplnený formulár  CNB, a to vždy do 15 kalendárních dnu  po ukoncení vykazovaného ctvrtletí."
        + " Formulár se vyplnuje za operace provedené ve vykazovaném ctvrtletí.", f2_bold));
PdfPCell cell111 = new PdfPCell(phrase111);
cell111.setHorizontalAlignment(ALIGN_LEFT);
cell111.setBorder(NO_BORDER);
table111.addCell(cell111);
document.add(table111);
////
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
                    if (j==0) {
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
                ArrayList<C_TrimestraleCZK_value> dati = tczk.getDati();
                PdfPCell cell;
                Phrase phraset;
                PdfPCell cellt;
                double availableSpace = 0;
                for (int i = 0; i < dati.size(); i++) {
                    availableSpace = wr.getVerticalPosition(true) - document.bottomMargin();
                    table3 = new PdfPTable(colonne.size());
                    table3.setWidths(columnWidths2);
                    table3.setWidthPercentage(100);
                    C_TrimestraleCZK_value temp = dati.get(i);
                    phraset = new Phrase();
                    phraset.add(new Chunk(temp.getValuta(), f3_normal));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_LEFT);
                    cellt.setBorder(BOTTOM);
                    table3.addCell(cellt);
                    phraset = new Phrase();
                    phraset.add(new Chunk(formatMysqltoDisplay(temp.getAcquisti()), f3_normal));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setBorder(BOTTOM);
                    table3.addCell(cellt);
                    phraset = new Phrase();
                    phraset.add(new Chunk(formatMysqltoDisplay(temp.getVendita()), f3_normal));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setBorder(BOTTOM);
                    table3.addCell(cellt);
                    phraset = new Phrase();
                    double d = fd(temp.getAcquisti()) - fd(temp.getVendita());
                    phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(d, 2) + ""), f3_normal));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setBorder(BOTTOM);
                    table3.addCell(cellt);
                    document.add(table3);
                    
//                if ((availableSpace < 80) || i == dati.size() - 1) {
//                    //test numero di pagina
//                    table = new PdfPTable(3);
//
//                    table.setWidths(new int[]{24, 24, 2});
//                    table.getDefaultCell().setFixedHeight(10);
//                    table.getDefaultCell().setBorder(Rectangle.TOP);
//                    cell = new PdfPCell();
//                    cell.setBorder(0);
//                    cell.setBorderWidthTop(1);
//                    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
//                    cell.setPhrase(new Phrase("", f3_normal));
//                    table.addCell(cell);
//
//                    cell = new PdfPCell();
//                    cell.setBorder(0);
//                    cell.setBorderWidthTop(1);
//                    cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//                    cell.setPhrase(new Phrase(String.format("Pag. %d/" + anno, startpage + 1), f3_normal));
//                    table.addCell(cell);
//
//                    cell = new PdfPCell();
//                    cell.setBorder(0);
//                    cell.setBorderWidthTop(1);
//                    table.addCell(cell);
//                    table.setTotalWidth(document.getPageSize().getWidth()
//                            - document.leftMargin() - document.rightMargin());
//                    table.writeSelectedRows(0, -1, document.leftMargin(),
//                            document.bottomMargin() - 1, wr.getDirectContent());
//
//                    startpage++;
//                }
                }
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
     * @param tczk
     * @param colonne
     * @param anno
     * @param startpage
     * @return
     * @throws FileNotFoundException
     * @throws IOException
     */
    public String receiptexcel(String path, C_TrimestraleCZK_value tczk, ArrayList<String> colonne, String anno, int startpage) throws FileNotFoundException, IOException {

        // String outputfile = "C_TrimestraleCZK.pdf";
        try {
            File pdf = new File(path + generaId(50) + "QuarterlyCZK.xls");
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet("QuarterlyCZK");
            //CREAZIONE FONT
            HSSFFont font = workbook.createFont();
            font.setFontName(FONT_ARIAL);
            font.setFontHeightInPoints((short) 12);
            font.setBold(true);

            HSSFCellStyle style1 = (HSSFCellStyle) workbook.createCellStyle();
            style1.setFont(font);
            
            HSSFCellStyle style1bis = (HSSFCellStyle) workbook.createCellStyle();
            style1bis.setFont(font);
              style1bis.setWrapText(true);

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
            cl.setCellValue(intestazionePdf + " from " + tczk.getDataDa() + " to  " + tczk.getDataA());

            Row rowP2 = sheet.createRow((short) 2);

            Cell cl2 = rowP2.createCell(1);
            cl2.setCellStyle(style1);
            cl2.setCellValue(tczk.getBranch());


            Row rowP3 = sheet.createRow((short) 3);
            Cell cl3b = rowP3.createCell(1);
            cl3b.setCellStyle(style1bis);
            cl3b.setCellValue("Držitel registrace predá vyplnený formulár  CNB,  \n a to vždy do 15 kalendárních dnu  po ukoncení \n vykazovaného ctvrtletí. \n"
                    + " Formulár se vyplnuje za operace \n provedené ve vykazovaném ctvrtletí.");
            
            Row row66 = sheet.createRow((short) 5);

            int cntriga = 5;
            //mi scandisco le colonne
            for (int j = 0; j < colonne.size(); j++) {
                Cell cl7 = row66.createCell(j + 1);
                cl7.setCellStyle(style3);
                if(j==0){
                cl7.setCellStyle(style3left);    
                }
                cl7.setCellValue(colonne.get(j));
            }

            //  document.add(table2);
            // document.add(sep);
            ArrayList<C_TrimestraleCZK_value> dati = tczk.getDati();

            for (int i = 0; i < dati.size(); i++) {
                cntriga++;
                Row row6 = sheet.createRow((short) cntriga);

                C_TrimestraleCZK_value temp = dati.get(i);

                Cell f1bis = row6.createCell(1);
                f1bis.setCellStyle(style4left);
                f1bis.setCellValue(temp.getValuta());

                Cell f2 = row6.createCell(2);
                f2.setCellStyle(style4);
                f2.setCellValue(formatMysqltoDisplay(temp.getAcquisti()));

                Cell f3 = row6.createCell(3);
                f3.setCellStyle(style4);
                f3.setCellValue(formatMysqltoDisplay(temp.getVendita()));

                Cell f4 = row6.createCell(4);
                f4.setCellStyle(style4);
                double d=fd(temp.getAcquisti()) - fd(temp.getVendita());
                f4.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(d,2) + ""));

            }
            
            
            
//            CellRangeAddress cellRangeAddress = new CellRangeAddress(3,3,2,4);              
//               sheet.addMergedRegion(cellRangeAddress);   
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

//    public static void main(String[] args) throws FileNotFoundException, DocumentException, IOException {
//        C_TrimestraleCZK nctl = new C_TrimestraleCZK();
//        
//        //parametro da passare che indica da quale pagine stampare
//        int pag = 1;
//
//        C_TrimestraleCZK_value pdf = new C_TrimestraleCZK_value();
//
//        
//
////        Document document = null;
////        OutputStream ou = null;
////
////        String outputfile = "C_TrimestraleCZK.pdf";
////        document = new Document(PageSize.A4.rotate(), 20, 20, 20, 20);
////        ou = new FileOutputStream(outputfile);
////        PdfWriter wr = PdfWriter.getInstance(document, ou);
////        document.open();
//
//        ArrayList<C_TrimestraleCZK_value> dati = new ArrayList<>();
//        dati = Engine.getC_TrimestraleCZK();
//        pdf.setDataDa("19/05/2016");
//        pdf.setDataA("20/05/2016");
//        pdf.setBranch("305 Praha Hlavni Nadrazi");
//        pdf.setDati(dati);
//
//        nctl.receipt(pdf, alcolonne);
//
////
//        PdfReader reader = new PdfReader("C_TrimestraleCZK.pdf");
//        int n = reader.getNumberOfPages();
//        
//        ArrayList<Integer> pages = new ArrayList<>();
//        for (int i = pag; i < n; i++) {
//            pages.add(i);
//        }
//
//        ExtractPages("C_TrimestraleCZK.pdf", pages);
//
//        //chiusura documento
////        document.close();
////        wr.close();
////        ou.close();
//    }

    /**
     *
     * @param filename
     * @param pages
     * @return
     */
    public static File ExtractPages(String filename, ArrayList<Integer> pages) {

        if (filename.contains("temporaneo")) {
            return new File(filename);
        } else if (pages.isEmpty()) {
            return new File(filename);
        } else {

            File out = new File("C_page" + filename);
            try {

                PdfReader reader = new PdfReader(filename);
                int n = reader.getNumberOfPages();
                reader.close();
                String path;
                PdfStamper stamper;
                for (int i = 1; i <= n; i++) {
                    reader = new PdfReader(filename);
                    reader.selectPages(pages);
                    stamper = new PdfStamper(reader, new FileOutputStream(out));
                    stamper.close();
                    reader.close();
                }

            } catch (IOException | DocumentException ex) {
                insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
            }
            return out;
        }
    }

}
