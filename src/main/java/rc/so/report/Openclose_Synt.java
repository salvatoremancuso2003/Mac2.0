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
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import static com.itextpdf.text.pdf.PdfWriter.getInstance;
import com.itextpdf.text.pdf.draw.LineSeparator;
import static rc.so.util.Engine.insertTR;
import static rc.so.util.Utility.generaId;
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
import static org.apache.poi.ss.usermodel.BorderStyle.THIN;
import org.apache.poi.ss.usermodel.Cell;
import static org.apache.poi.ss.usermodel.HorizontalAlignment.CENTER;
import static org.apache.poi.ss.usermodel.HorizontalAlignment.LEFT;
import static org.apache.poi.ss.usermodel.HorizontalAlignment.RIGHT;
import org.apache.poi.ss.usermodel.Row;

/**
 *
 * @author vcrugliano
 */
public class Openclose_Synt {

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
    public static float[] columnWidths2 = new float[]{10f, 10f, 25f, 15f, 10f, 10f};

    /**
     *
     */
    public static float[] columnWidths3 = new float[]{30f, 20f, 15f, 15f, 10f, 15f};
    final String intestazionePdf = "Open / Close Synthetical ";
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
    public Openclose_Synt() {

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
     * @param datereport
     * @return
     */
    public String receipt(String path, Openclose_Synt_value osp, ArrayList<String> colonne, String datereport) {
        try {
            Document document = new Document(A4, 20, 20, 20, 20);
            File pdf = new File(path + generaId(50) + "OpenCloseSintetica.pdf");
            try(OutputStream ou = new FileOutputStream(pdf)){
            PdfWriter wr = getInstance(document, ou);
            document.open();

            PdfPTable table = new PdfPTable(2);
            table.setWidths(columnWidths0);
            table.setWidthPercentage(100);

            Phrase phrase1 = new Phrase();
            phrase1.add(new Chunk(intestazionePdf + " " + datereport, f3_bold));
            PdfPCell cell1 = new PdfPCell(phrase1);
            cell1.setBorder(NO_BORDER);

            //   Paragraph pa1 = new Paragraph(new Phrase(datereport, f2_normal));
            Paragraph pa1 = new Paragraph(new Phrase("", f2_normal));
            pa1.setAlignment(ALIGN_RIGHT);
            PdfPCell cell2 = new PdfPCell(pa1);
            cell2.setBorder(NO_BORDER);
            cell2.setHorizontalAlignment(ALIGN_RIGHT);

            Phrase phrase3 = new Phrase();
            phrase3.add(new Chunk("\n " + osp.getId_filiale() + " " + osp.getDe_filiale(), f3_normal));
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
                if (j == 2 || j == 4) {
                    cellt1.setHorizontalAlignment(ALIGN_LEFT);
                }
                cellt1.setBorderWidth(0.7f);
//                if(j==2 || j==3){
//                    cellt1.setHorizontalAlignment(Element.ALIGN_CENTER);
//                }

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
            ArrayList<Openclose_Synt_value> dati = osp.getDati();
            Phrase phraset;
            PdfPCell cellt;

            table3 = new PdfPTable(colonne.size());
            table3.setWidths(columnWidths2);
            table3.setWidthPercentage(100);

            double totalecop = 0; //totale controvalore della valuta locale
            double totalefx = 0;//totale controvalore delle valute diverse dalla valuta locale
            double totalegenerale = 0; //totale generale

            for (int i = 0; i < dati.size(); i++) {
                Openclose_Synt_value temp =  dati.get(i);

                phraset = new Phrase();
                phraset.add(new Chunk(temp.getUser(), f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table3.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(temp.getSafetill(), f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
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
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table3.addCell(cellt);

                String tipoesteso;
                if (temp.getTipo().equals("C")) {
                    tipoesteso = "Close";
                } else {
                    tipoesteso = "Open";
                }
                phraset = new Phrase();
                phraset.add(new Chunk(tipoesteso, f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_LEFT);
                cellt.setBorder(BOTTOM);
                table3.addCell(cellt);
                phraset = new Phrase();
                phraset.add(new Chunk(temp.getNumerrori(), f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table3.addCell(cellt);
            }
            document.add(table3);
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
     * @param datereport
     * @return
     */
    public String receiptExcel(String path, Openclose_Synt_value osp, ArrayList<String> colonne, String datereport) {
        try {
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet("OpenClose");
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

            HSSFCellStyle style3int = (HSSFCellStyle) workbook.createCellStyle();
            style3int.setFont(font3);
            style3int.setAlignment(CENTER);
            style3int.setBorderTop(THIN);
            style3int.setBorderBottom(THIN);

            HSSFFont font4 = workbook.createFont();
            font4.setFontName(FONT_ARIAL);
            font4.setFontHeightInPoints((short) 10);

            HSSFCellStyle style3bis = (HSSFCellStyle) workbook.createCellStyle();
            style3bis.setFont(font4);
            style3bis.setAlignment(CENTER);
            style3bis.setBorderTop(THIN);
            style3bis.setBorderBottom(THIN);

            HSSFCellStyle style4 = (HSSFCellStyle) workbook.createCellStyle();
            style4.setAlignment(RIGHT);
            style4.setBorderTop(THIN);
            style4.setBorderBottom(THIN);
            
            HSSFCellStyle style4left = (HSSFCellStyle) workbook.createCellStyle();
            style4left.setAlignment(LEFT);
            style4left.setBorderTop(THIN);
            style4left.setBorderBottom(THIN);

            //FINE CREAZIONE FONT
            Row rowP = sheet.createRow((short) 1);

            Cell cl = rowP.createCell(1);
            cl.setCellStyle(style1);
            cl.setCellValue(intestazionePdf + " " + datereport);

//            Cell cl2 = rowP.createCell(6);
//            cl2.setCellStyle(style2);
//            cl2.setCellValue(datereport);
            Row row = sheet.createRow((short) 3);
            row.createCell(1).setCellValue(osp.getId_filiale() + " " + osp.getDe_filiale());
            row = sheet.createRow((short) 5);
            Cell cl3 = row.createCell(1);
            cl3.setCellStyle(style3);
            cl3.setCellValue("User");
            Cell cl4 = row.createCell(2);
            cl4.setCellStyle(style3);
            cl4.setCellValue("Safe/Till");
            Cell cl5 = row.createCell(3);
            cl5.setCellStyle(style3left);
            cl5.setCellValue("Date");
            Cell cl6 = row.createCell(4);
            cl6.setCellStyle(style3);
            cl6.setCellValue("Operation");
            Cell cl7 = row.createCell(5);
            cl7.setCellStyle(style3left);
            cl7.setCellValue("Type");
            Cell cl8 = row.createCell(6);
            cl8.setCellStyle(style3);
            cl8.setCellValue("No.Error");
            ArrayList<Openclose_Synt_value> datiosp = osp.getDati();
            int vrow = 7;

            for (int i = 0; i < datiosp.size(); i++) {
                row = sheet.createRow((short) vrow);
                Openclose_Synt_value temp = datiosp.get(i);
                String tipoesteso;
                if (temp.getTipo().equals("C")) {
                    tipoesteso = "Close";
                } else {
                    tipoesteso = "Open";
                }
                Cell cla = row.createCell(1);
                cla.setCellStyle(style4);
                cla.setCellValue(temp.getUser());

                Cell clb = row.createCell(2);
                clb.setCellStyle(style4);
                clb.setCellValue(temp.getSafetill());

                Cell clc = row.createCell(3);
                clc.setCellStyle(style4left);
                clc.setCellValue(temp.getData());

                Cell cld = row.createCell(4);
                cld.setCellStyle(style4);
                cld.setCellValue(temp.getOperazione());

                Cell cle = row.createCell(5);
                cle.setCellStyle(style4left);
                cle.setCellValue(tipoesteso);

                Cell clf = row.createCell(6);
                clf.setCellStyle(style4);
                clf.setCellValue(temp.getNumerrori());

                vrow++;
            }
            sheet.autoSizeColumn(0);
            sheet.autoSizeColumn(1);
            sheet.autoSizeColumn(2);
            sheet.autoSizeColumn(3);
            sheet.autoSizeColumn(4);
            sheet.autoSizeColumn(5);
            sheet.autoSizeColumn(6);
            File f = new File(path + generaId(50) + "OpenCloseSintetica.xls");
            try (FileOutputStream out = new FileOutputStream(f)) {
                workbook.write(out);
            }
            String base64 = new String(encodeBase64(readFileToByteArray(f)));
            f.delete();
            return base64;
        } catch (IOException ex) {
            insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
        }
        return null;
    }

//    public static void main(String[] args) {
//        
//        ArrayList<Openclose_Synt_value> dati = new ArrayList<>();
//        Db_Master dbm = new Db_Master();
//                
//        String[] fil = dbm.getCodLocal(false);
//        dati = dbm.list_Openclose_Synt_value(fil[0], "23/11/2016 14:12:40");
//        dbm.closeDB();
//          Openclose_Synt osp = new Openclose_Synt();
//                Openclose_Synt_value pdf = new Openclose_Synt_value();
//                ArrayList<String> alcolonne = new ArrayList<>();
//                alcolonne.add("User");
//                alcolonne.add("Safe/Till");
//                alcolonne.add("Date");
//                alcolonne.add("Operation");
//                alcolonne.add("Type");
//                alcolonne.add("No.Error");
//
//                pdf.setId_filiale(fil[0]);
//                pdf.setDe_filiale(fil[1]);
//                pdf.setDati(dati);
//               osp.receiptExcel("", pdf, alcolonne, "23/11/2016 14:12:40");
//    }
//    public static void genera(){
//        Openclose_Synt osp = new Openclose_Synt();
//        Openclose_Synt_value pdf = new Openclose_Synt_value();
//        ArrayList<String> alcolonne = new ArrayList<>();
//        alcolonne.add("User");
//        alcolonne.add("Safe/Till");
//        alcolonne.add("Date");
//        alcolonne.add("Operation");
//        alcolonne.add("Type");
//        alcolonne.add("No.Error");
//        
//        String data = "08/02/2017";
//        ArrayList<Openclose_Synt_value> dati = new ArrayList<>();
//        Db_Master dbm = new Db_Master();
//        dati = dbm.list_Openclose_Synt_value(data);
//        dbm.closeDB();
//
//        pdf.setId_filiale("079");
//        pdf.setDe_filiale("Milano Duomo");
//        pdf.setDati(dati);
//
//        osp.receipt(pdf, alcolonne,data);
//    }
}
