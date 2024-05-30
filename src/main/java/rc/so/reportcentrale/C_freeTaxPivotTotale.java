/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rc.so.reportcentrale;

import com.itextpdf.text.Font;
import static com.itextpdf.text.Font.BOLD;
import static com.itextpdf.text.Font.BOLDITALIC;
import static com.itextpdf.text.Font.NORMAL;
import static com.itextpdf.text.FontFactory.getFont;
import com.itextpdf.text.Phrase;
import static com.itextpdf.text.pdf.BaseFont.HELVETICA;
import static com.itextpdf.text.pdf.BaseFont.WINANSI;
import static rc.so.util.Engine.insertTR;
import static rc.so.util.Utility.fd;
import static rc.so.util.Utility.formatMysqltoDisplay;
import static rc.so.util.Utility.generaId;
import static rc.so.util.Utility.roundDoubleandFormat;
import java.awt.Color;
import java.io.File;
import java.io.FileOutputStream;
import static java.lang.Thread.currentThread;
import java.util.ArrayList;
import static org.apache.commons.codec.binary.Base64.encodeBase64;
import static org.apache.commons.io.FileUtils.readFileToByteArray;
import static org.apache.poi.hssf.usermodel.HSSFFont.FONT_ARIAL;
import static org.apache.poi.ss.usermodel.BorderStyle.MEDIUM;
import static org.apache.poi.ss.usermodel.BorderStyle.THIN;
import org.apache.poi.ss.usermodel.Cell;
import static org.apache.poi.ss.usermodel.HorizontalAlignment.CENTER;
import static org.apache.poi.ss.usermodel.HorizontalAlignment.LEFT;
import static org.apache.poi.ss.usermodel.HorizontalAlignment.RIGHT;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author srotella
 */
public class C_freeTaxPivotTotale {

    /**
     *
     */
    public static final float[] columnWidths0 = new float[]{30f, 80f};

    /**
     *
     */
    public static final float[] columnWidths1 = new float[]{60f, 30f};

    /**
     *
     */
    public static float[] columnWidths2 = new float[]{40f, 15f, 10f, 10f, 10f, 10f, 10f, 10f, 10f, 10f};

    /**
     *
     */
    public static float[] columnWidths2int = new float[]{50f, 40f, 40f};

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
    final String intestazionePdf = "VAT Refund - Pivot Totale";
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

    double totaleVolPrevYear = 0;
    double totaleVolYear = 0;
    double totaleVolVal = 0;
    double totaleVolPerc = 0;
    double totaleqtyPrevYear = 0;
    double totaleqtyYear = 0;
    double totaleqtyVal = 0;
    double totaleqtyPerc = 0;

    double totaleEUROtotVolPrevYear = 0;
    double totaleEUROtotVolYear = 0;
    double totaleEUROtotVolVal = 0;
    double totaleEUROtotVolPerc = 0;
    double totaleEUROqtyPrevYear = 0;
    double totaleEUROqtyYear = 0;
    double totaleEUROqtyVal = 0;
    double totaleEUROqtyPerc = 0;

    double totaleVALUTAtotVolPrevYear = 0;
    double totaleVALUTAtotVolYear = 0;
    double totaleVALUTAtotVolVal = 0;
    double totaleVALUTAtotVolPerc = 0;
    double totaleVALUTAqtyPrevYear = 0;
    double totaleVALUTAqtyYear = 0;
    double totaleVALUTAqtyVal = 0;
    double totaleVALUTAqtyPerc = 0;

    /**
     ** Constructor
     */
    public C_freeTaxPivotTotale() {

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
     * @param path
     * @param cmfb
     * @param colonne
     * @param colonneanno
     * @param colonneetich
     * @return
     */
    public String receiptexcel(String path, C_freeTaxPivotTotale_value cmfb, ArrayList<String> colonne, ArrayList<String> colonneanno,
            ArrayList<String> colonneetich) {

        try {
            File pdffile = new File(path + generaId(50) + "C_VatRefundPivotTotale.xls");
            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet("C_VatRefundPivotTotale");
            XSSFFont font = workbook.createFont();
            font.setFontName(FONT_ARIAL);
            font.setFontHeightInPoints((short) 12);
            font.setBold(true);

            XSSFCellStyle style1 = (XSSFCellStyle) workbook.createCellStyle();
            style1.setFont(font);
            style1.setAlignment(CENTER);
            style1.setBorderRight(MEDIUM);
            style1.setBorderLeft(MEDIUM);

            XSSFFont font2 = workbook.createFont();
            font2.setFontName(FONT_ARIAL);
            font2.setFontHeightInPoints((short) 12);

            XSSFCellStyle style2 = (XSSFCellStyle) workbook.createCellStyle();
            style2.setFont(font2);

            XSSFFont font3 = workbook.createFont();
            font3.setFontName(FONT_ARIAL);
            font3.setFontHeightInPoints((short) 10);
            font3.setBold(true);

            XSSFCellStyle style3 = (XSSFCellStyle) workbook.createCellStyle();
            style3.setFont(font3);
            style3.setAlignment(RIGHT);
            style3.setBorderTop(THIN);
            style3.setBorderBottom(THIN);

            XSSFFont font4 = workbook.createFont();
            font4.setFontName(FONT_ARIAL);
            font4.setFontHeightInPoints((short) 10);

            XSSFCellStyle style4 = (XSSFCellStyle) workbook.createCellStyle();
            style4.setAlignment(RIGHT);
            style4.setBorderTop(THIN);
            style4.setBorderBottom(THIN);

            XSSFCellStyle style5 = (XSSFCellStyle) workbook.createCellStyle();
            style5.setAlignment(LEFT);
            style5.setBorderTop(THIN);
            style5.setBorderBottom(THIN);

            int cntriga = 1;
            Row rowP = sheet.createRow((short) cntriga);

            Cell cl = rowP.createCell(2);
            cl.setCellStyle(style1);
            cl.setCellValue(intestazionePdf + " From " + cmfb.getDataDa() + " to " + cmfb.getDataA());

            cntriga++;
            cntriga++;

            ArrayList<C_freeTaxPivotTotale_value> dati = cmfb.getDati();

            boolean ft = true;

            if (ft) {

                cntriga++;

                Row row22 = sheet.createRow((short) cntriga);
                //mi scandisco le colonne
                int offset = 3;
                for (int c = 0; c < colonne.size(); c++) {
                    Cell cl7 = row22.createCell(offset);
                    cl7.setCellStyle(style1);
                    cl7.setCellValue(colonne.get(c));
                    CellRangeAddress cellRangeAddress = new CellRangeAddress(cntriga, cntriga, offset, offset + 3);
                    sheet.addMergedRegion(cellRangeAddress);

                    offset = offset + 4;
                }

                cntriga++;

                Row row23 = sheet.createRow((short) cntriga);
                offset = 3;
                for (int c = 0; c < colonneanno.size(); c++) {
                    Cell cl7 = row23.createCell(offset);
                    cl7.setCellStyle(style1);
                    cl7.setCellValue(colonneanno.get(c));

                    CellRangeAddress cellRangeAddress = new CellRangeAddress(cntriga, cntriga, offset, offset + 1);
                    sheet.addMergedRegion(cellRangeAddress);

                    offset = offset + 2;

                }
                cntriga++;

                Row row24 = sheet.createRow((short) cntriga);
                for (int c = 0; c < colonneetich.size(); c++) {
                    Cell cl7 = row24.createCell(c + 1);
                    cl7.setCellStyle(style3);
                    cl7.setCellValue(colonneetich.get(c));
                }
                cntriga++;
                cntriga++;

            }

            double[] totali = new double[(colonne.size() * 4) + 2];

            for (int j = 0; j < dati.size(); j++) {

                cntriga++;
                Row row6 = sheet.createRow((short) cntriga);

                C_freeTaxPivotTotale_value temp = dati.get(j);
                ArrayList<String> values = temp.getValues();

                for (int h = 0; h < values.size(); h++) {

                    Cell f1bis = row6.createCell(h + 1);
                    f1bis.setCellStyle(style4);
                    if (h == 0 || h == 1 || h == 2) {
                        f1bis.setCellValue(values.get(h));
                    } else {
                        f1bis.setCellValue(formatMysqltoDisplay(values.get(h)));
                    }

                    if (h == 1) {
                        f1bis.setCellStyle(style5);
                    }

                    if (h > 2) {
                        totali[h] = totali[h] + fd(values.get(h));
                    }

                }

            }

            //total EURO
            cntriga++;
            cntriga++;
            Row row6 = sheet.createRow((short) cntriga);

            Cell f2 = row6.createCell(1);
            f2.setCellStyle(style3);
            f2.setCellValue("Total");

            for (int h = 2; h < (colonne.size() * 4) + 2; h++) {
                Cell f1bis = row6.createCell(h + 1);
                f1bis.setCellStyle(style3);
                if (h % 2 != 0) {
                    f1bis.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(totali[h], 0)));
                } else {
                    f1bis.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(totali[h], 2)));
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
            sheet.autoSizeColumn(21);
            sheet.autoSizeColumn(22);
            sheet.autoSizeColumn(23);
            sheet.autoSizeColumn(24);
            sheet.autoSizeColumn(25);
            sheet.autoSizeColumn(26);
            sheet.autoSizeColumn(27);
            sheet.autoSizeColumn(28);
            sheet.autoSizeColumn(29);
            sheet.autoSizeColumn(30);
            sheet.autoSizeColumn(31);
            sheet.autoSizeColumn(32);
            sheet.autoSizeColumn(33);
            sheet.autoSizeColumn(34);
            sheet.autoSizeColumn(35);
            sheet.autoSizeColumn(36);
            sheet.autoSizeColumn(37);
            sheet.autoSizeColumn(38);
            sheet.autoSizeColumn(39);
            sheet.autoSizeColumn(40);

            try (FileOutputStream out = new FileOutputStream(pdffile)) {
                workbook.write(out);
            }
            String base64 = new String(encodeBase64(readFileToByteArray(pdffile)));
            pdffile.delete();
            return base64;

        } catch (Exception ex) {
            insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
        }
        return null;

    }

}
