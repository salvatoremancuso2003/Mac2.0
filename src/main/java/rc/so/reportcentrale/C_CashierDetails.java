/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rc.so.reportcentrale;

import com.itextpdf.text.Font;
import com.itextpdf.text.Phrase;
import rc.so.db.Db_Master;
import rc.so.entity.Branch;
import rc.so.entity.Ch_transaction;
import static rc.so.entity.Ch_transaction.formatType;
import rc.so.entity.Openclose;
import static rc.so.util.Engine.insertTR;
import static rc.so.util.Utility.fd;
import static rc.so.util.Utility.formatMysqltoDisplay;
import static rc.so.util.Utility.generaId;
import static rc.so.util.Utility.roundDoubleandFormat;
import java.awt.Color;
import java.io.File;
import java.io.FileOutputStream;
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
 * @author srotella
 */
public class C_CashierDetails {

    /**
     *
     */
    public static final float[] columnWidths0 = new float[]{70f, 30f};

    /**
     *
     */
    public static final float[] columnWidths0bis = new float[]{100f, 0f};

    /**
     *
     */
    public static final float[] columnWidths1 = new float[]{60f, 30f};

    /**
     *
     */
    public static float[] columnWidths2 = null;

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
    final String intestazionePdf = "Cashier Details";
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

    float tot_qta = 0, tot_amount = 0, tot_comm = 0, tot_net = 0, tot_buy = 0, tot_spread = 0, tot_commFix = 0;

    ArrayList<C_CashierPerformance_value> listaTot;

    ArrayList<String> cpTot;

    /**
     *
     * @param ccdt
     * @param colonnetran
     * @param colonneopenclose
     * @param sheet
     * @param cntriga
     * @param style1
     * @param style2
     * @param style3
     * @param style4
     * @param style1bis
     * @param style3left
     * @param style4left
     * @return
     */
    public int receiptexcel(C_CashierDetails_value ccdt, ArrayList<String> colonnetran, ArrayList<String> colonneopenclose,
            HSSFSheet sheet, int cntriga, HSSFCellStyle style1, HSSFCellStyle style2, HSSFCellStyle style3, HSSFCellStyle style4, HSSFCellStyle style1bis,HSSFCellStyle style3left, HSSFCellStyle style4left) {

        try {

            //creo un array di appoggio per sommarmi i totali parametrizzati
            ArrayList<Ch_transaction> datitran = ccdt.getDatitran();
            ArrayList<Openclose> datiopeclose = ccdt.getDatierr();

            Row rowP = sheet.createRow((short) cntriga);

            Cell cl = rowP.createCell(1);
            cl.setCellStyle(style1);
            cl.setCellValue(intestazionePdf + " From " + ccdt.getDataDa() + " to " + ccdt.getDataA());

            cntriga++;
            cntriga++;

            Row rowP2 = sheet.createRow((short) cntriga);

            Cell cl22 = rowP2.createCell(1);
            cl22.setCellStyle(style1);
            cl22.setCellValue(ccdt.getUser());

            cntriga++;
            cntriga++;

            Row row66 = sheet.createRow((short) cntriga);

            //mi scandisco le colonne
            for (int c = 0; c < colonnetran.size(); c++) {
                Cell cl8 = row66.createCell(c + 1);
                cl8.setCellStyle(style3);
                if(c==2 ||c==3 || c==10 ||c==11){
                cl8.setCellStyle(style3left);    
                }
                cl8.setCellValue(colonnetran.get(c));
            }

            for (int j = 0; j < datitran.size(); j++) {

                cntriga++;

                Row row6 = sheet.createRow((short) cntriga);

                Ch_transaction temp = (Ch_transaction) datitran.get(j);

                Cell f1bis = row6.createCell(1);
                f1bis.setCellStyle(style4);
                f1bis.setCellValue(temp.getFiliale());

                Cell f2 = row6.createCell(2);
                f2.setCellStyle(style4);
                f2.setCellValue(temp.getUser());

                Cell f3 = row6.createCell(3);
                f3.setCellStyle(style4left);
                f3.setCellValue(temp.getId() + " " + temp.getData());

                Cell f4 = row6.createCell(4);
                f4.setCellStyle(style4left);
                f4.setCellValue(formatType(temp.getTipotr()));

                Cell f5 = row6.createCell(5);
                f5.setCellStyle(style4);
                f5.setCellValue(formatMysqltoDisplay((temp.getTotal())));

                Cell f6 = row6.createCell(6);
                f6.setCellStyle(style4);
                f6.setCellValue(formatMysqltoDisplay(temp.getCom()));

                double percomm = (fd(temp.getCom()) / 100) * fd(temp.getTotal());

                Cell f7 = row6.createCell(7);
                f7.setCellStyle(style4);
                f7.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(percomm, 2)));

                Cell f8 = row6.createCell(8);
                f8.setCellStyle(style4);
                f8.setCellValue(formatMysqltoDisplay((temp.getCommission())));

                Cell f9 = row6.createCell(9);
                f9.setCellStyle(style4);
                f9.setCellValue(formatMysqltoDisplay(temp.getRound()));

                Cell f10 = row6.createCell(10);
                f10.setCellStyle(style4);
                f10.setCellValue(formatMysqltoDisplay(temp.getFix()));

                Cell f11 = row6.createCell(11);
                f11.setCellStyle(style4left);
                if (temp.getDel_fg().equals("0")) {
                    f11.setCellValue("");
                } else {
                    f11.setCellValue(temp.getDel_dt() + " " + temp.getDel_motiv());
                }

                Cell f12 = row6.createCell(12);
                f12.setCellStyle(style4left);
                f12.setCellValue(temp.getCl_cod());

            }

            cntriga++;
            cntriga++;
            row66 = sheet.createRow((short) cntriga);
            for (int c = 0; c < colonneopenclose.size(); c++) {
                Cell cl8 = row66.createCell(c + 1);
                cl8.setCellStyle(style3);
                if(c==2){
                cl8.setCellStyle(style3left);    
                }
                cl8.setCellValue(colonneopenclose.get(c));
            }

            for (int j = 0; j < datiopeclose.size(); j++) {

                cntriga++;

                Row row6 = sheet.createRow((short) cntriga);

                Openclose temp = (Openclose) datiopeclose.get(j);

                Cell f1bis = row6.createCell(1);
                f1bis.setCellStyle(style4);
                f1bis.setCellValue(temp.getFiliale());

                Cell f2 = row6.createCell(2);
                f2.setCellStyle(style4);
                f2.setCellValue(temp.getUser());

//                Cell f3 = row6.createCell(3);
//                f3.setCellStyle(style4);
//                f3.setCellValue(temp.getKind());
                Cell f4 = row6.createCell(3);
                f4.setCellStyle(style4left);
                f4.setCellValue(temp.getId() + " " + temp.getData());

                Cell f5 = row6.createCell(4);
                f5.setCellStyle(style4);
                f5.setCellValue(formatMysqltoDisplay(temp.getTotal_diff()));

            }

            cntriga++;
            cntriga++;
            cntriga++;
            cntriga++;
            return cntriga;

        } catch (Exception ex) {
            insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
        }
        return cntriga;
    }

    /**
     *
     * @param path
     * @param d3
     * @param d4
     * @param data1
     * @param data2
     * @param alcolonnetran
     * @param alcolonneopenclose
     * @param filiali
     * @param br
     * @return
     */
    public String mainexcel(String path, String d3, String d4, String data1, String data2,
            ArrayList<String> alcolonnetran, ArrayList<String> alcolonneopenclose, ArrayList<String> filiali, ArrayList<Branch> br) {
        try {
            File pdffile = new File(path + generaId(50) + "C_AnalysisDetailsTransactionCertificationforBranchAndPeriod.xls");
            HSSFWorkbook workbook = new HSSFWorkbook();

            Db_Master dbm = new Db_Master();
            ArrayList<C_CashierDetails_value> list = dbm.list_C_CashierDetails_value(d3, d4, data1, data2, filiali);

            dbm.closeDB();
            
           

            for (int i = 0; i < list.size(); i++) {
                C_CashierDetails_value pdf = list.get(i);
                
                
                
                HSSFSheet sheet = workbook.createSheet(pdf.getUser());

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

                HSSFCellStyle style1bis = (HSSFCellStyle) workbook.createCellStyle();
                style1bis.setFont(font3);
                style1bis.setAlignment(RIGHT);
                style1bis.setBorderTop(THIN);
                style1bis.setBorderBottom(THIN);
                style1bis.setWrapText(true);

                C_CashierDetails nctl = new C_CashierDetails();

                int nriga = 1;
                nriga = nctl.receiptexcel(pdf, alcolonnetran, alcolonneopenclose, sheet, nriga, style1, style2, style3, style4, style1bis,style3left, style4left);
                insertTR("I", "PDF", valueOf(nriga));
//chiusura documento

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
            }

            if (list.size() > 0) {

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
        } catch (Exception ex) {
            insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
        }
        return null;

    }

}
