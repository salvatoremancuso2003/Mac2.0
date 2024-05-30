/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rc.so.excel;

import com.google.common.util.concurrent.AtomicDouble;
import rc.so.db.Db_Master;
import rc.so.entity.Branch;
import rc.so.report.DailyCOP;
import rc.so.report.DailyKind;
import rc.so.report.Daily_value;
import static rc.so.util.Constant.patternnormdate_filter;
import static rc.so.util.Constant.patternsql;
import static rc.so.util.Engine.getConf;
import static rc.so.util.Engine.get_Branch;
import static rc.so.util.Engine.insertTR;
import static rc.so.util.Utility.divisione_controllozero;
import static rc.so.util.Utility.fd;
import static rc.so.util.Utility.formatStringtoStringDate;
import static rc.so.util.Utility.generaId;
import static rc.so.util.Utility.getCell;
import static rc.so.util.Utility.parseIntR;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import static java.lang.Thread.currentThread;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import static org.apache.commons.codec.binary.Base64.decodeBase64;
import static org.apache.commons.codec.binary.Base64.encodeBase64;
import static org.apache.commons.io.FileUtils.readFileToByteArray;
import static org.apache.poi.hssf.usermodel.HSSFFont.FONT_ARIAL;
import static org.apache.poi.ss.usermodel.BorderStyle.THICK;
import static org.apache.poi.ss.usermodel.BorderStyle.THIN;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import static org.apache.poi.ss.usermodel.CellType.NUMERIC;
import static org.apache.poi.ss.usermodel.CellType.STRING;
import static org.apache.poi.ss.usermodel.FillPatternType.SOLID_FOREGROUND;
import static org.apache.poi.ss.usermodel.HorizontalAlignment.CENTER;
import static org.apache.poi.ss.usermodel.IndexedColors.BLACK;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.IndexedColorMap;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFDataFormat;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author rcosco
 */
public class DailyChangeExcel {

    private static Row getRow(Sheet sheet, int index) {
        Row row1 = sheet.getRow(index);
        if (row1 == null) {
            row1 = sheet.createRow(index);
        }
        return row1;
    }

    /**
     *
     * @param filialidacontrollare
     * @param giornidacontrollare
     * @return
     */
    public static String create_uk_contabilita(List<String> filialidacontrollare, List<String> giornidacontrollare) {

        Db_Master db = new Db_Master();
        String pathout = db.getPath("temp");
        ArrayList<Branch> all_branch = db.list_branch_completeAFTER311217();

        XSSFWorkbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet("ACCOUNTANCY ");

        XSSFCellStyle cellStylenum = (XSSFCellStyle) wb.createCellStyle();
        XSSFDataFormat hssfDataFormat = (XSSFDataFormat) wb.createDataFormat();
        cellStylenum.setDataFormat(hssfDataFormat.getFormat(formatdataCell));
        cellStylenum.setBorderBottom(THIN);
        cellStylenum.setBorderTop(THIN);
        cellStylenum.setBorderRight(THIN);
        cellStylenum.setBorderLeft(THIN);

        XSSFFont font1 = wb.createFont();
        font1.setFontName(FONT_ARIAL);
        font1.setFontHeightInPoints((short) 13);
        font1.setBold(true);

        XSSFFont font2 = wb.createFont();
        font2.setFontName(FONT_ARIAL);
        font2.setFontHeightInPoints((short) 11);
        font2.setBold(true);

        XSSFFont font3 = wb.createFont();
        font3.setFontName(FONT_ARIAL);
        font3.setFontHeightInPoints((short) 10);
        font3.setBold(true);

        XSSFCellStyle cellStylenumBO = (XSSFCellStyle) wb.createCellStyle();
        cellStylenumBO.setDataFormat(hssfDataFormat.getFormat(formatdataCell));
        cellStylenumBO.setBorderBottom(THIN);
        cellStylenumBO.setBorderTop(THIN);
        cellStylenumBO.setBorderRight(THIN);
        cellStylenumBO.setBorderLeft(THIN);
        cellStylenumBO.setFont(font2);

        XSSFCellStyle cellTitle = (XSSFCellStyle) wb.createCellStyle();
        cellTitle.setBorderBottom(THIN);
        cellTitle.setBorderTop(THIN);
        cellTitle.setBorderRight(THIN);
        cellTitle.setBorderLeft(THIN);
        cellTitle.setFont(font1);
        cellTitle.setVerticalAlignment(VerticalAlignment.CENTER);
        cellTitle.setAlignment(CENTER);
        cellTitle.setWrapText(true);

        XSSFCellStyle cellTitleMi = (XSSFCellStyle) wb.createCellStyle();
        cellTitleMi.setBorderBottom(THIN);
        cellTitleMi.setBorderTop(THIN);
        cellTitleMi.setBorderRight(THIN);
        cellTitleMi.setBorderLeft(THIN);
        cellTitleMi.setFont(font2);
        cellTitleMi.setWrapText(true);

        Row start = getRow(sheet, 1);

        Cell c11 = getCell(start, 2, STRING);
        c11.setCellValue("PURCHASES (PAY OUT)");
        c11.setCellStyle(cellTitle);
        c11 = getCell(start, 3, STRING);
        c11.setCellValue("CASH ADVANCE (PAY OUT)");
        c11.setCellStyle(cellTitle);
        c11 = getCell(start, 4, STRING);
        c11.setCellValue("SELL (PAYIN)");
        c11.setCellStyle(cellTitle);
        c11 = getCell(start, 5, STRING);
        c11.setCellValue("COMM PURCH");
        c11.setCellStyle(cellTitle);
        c11 = getCell(start, 6, STRING);
        c11.setCellValue("COMM CASH ADV");
        c11.setCellStyle(cellTitle);
        c11 = getCell(start, 7, STRING);
        c11.setCellValue("COMM SELL");
        c11.setCellStyle(cellTitle);
        c11 = getCell(start, 8, STRING);
        c11.setCellValue("ERRORS GBP");
        c11.setCellStyle(cellTitle);
        c11 = getCell(start, 9, STRING);
        c11.setCellValue("TTT + PERS CHEQUE BK\r\nGBP\r\nFROM BK");
        c11.setCellStyle(cellTitle);
        c11.setCellStyle(cellTitle);
        c11 = getCell(start, 10, STRING);
        c11.setCellValue("TTT + PERS CHEQUE BK\r\nGBP\r\nTO BK");
        c11.setCellStyle(cellTitle);
        c11 = getCell(start, 11, STRING);
        c11.setCellValue("TTT + PERS CHEQUE BK\r\nCURRENCY\r\nBK PURCHASES");
        c11.setCellStyle(cellTitle);
        c11 = getCell(start, 12, STRING);
        c11.setCellValue("TTT + PERS CHEQUE BK\r\nCURRENCY\r\nBK SELL");
        c11.setCellStyle(cellTitle);
        c11 = getCell(start, 13, STRING);
        c11.setCellValue("GBP\r\nFROM BRANCH");
        c11.setCellStyle(cellTitle);
        c11 = getCell(start, 14, STRING);
        c11.setCellValue("GBP\r\nTO BRANCH");
        c11.setCellStyle(cellTitle);
        c11 = getCell(start, 13, STRING);
        c11.setCellValue("GBP\r\nFROM BRANCH");
        c11.setCellStyle(cellTitle);
        c11 = getCell(start, 14, STRING);
        c11.setCellValue("GBP\r\nTO BRANCH");
        c11.setCellStyle(cellTitle);
        c11 = getCell(start, 15, STRING);
        c11.setCellValue("ATTN: BUYBACK+CONCESSIONARI\r\nGBP\r\nNOCHANGE FROM NO STOCK");
        c11.setCellStyle(cellTitle);
        c11 = getCell(start, 16, STRING);
        c11.setCellValue("PETTY CASH\r\nCLAIRE DETTAGLIO AL COMMERC\r\nNOCHANGE TO NO STOCK");
        c11.setCellStyle(cellTitle);
        c11 = getCell(start, 17, STRING);
        c11.setCellValue("GBP\r\nWE RECEIVE");
        c11.setCellStyle(cellTitle);
        c11 = getCell(start, 18, STRING);
        c11.setCellValue("GBP\r\nWE SEND");
        c11.setCellStyle(cellTitle);
        c11 = getCell(start, 19, STRING);
        c11.setCellValue("GBP\r\nPOS (cash in of currencies sell)");
        c11.setCellStyle(cellTitle);
        c11 = getCell(start, 20, STRING);
        c11.setCellValue("CASHBACK\r\nVAT REFUND");
        c11.setCellStyle(cellTitle);
        c11 = getCell(start, 21, STRING);
        c11.setCellValue("COP");
        c11.setCellStyle(cellTitle);

        AtomicDouble to_02 = new AtomicDouble(0.0);
        AtomicDouble to_03 = new AtomicDouble(0.0);
        AtomicDouble to_04 = new AtomicDouble(0.0);
        AtomicDouble to_05 = new AtomicDouble(0.0);
        AtomicDouble to_06 = new AtomicDouble(0.0);
        AtomicDouble to_07 = new AtomicDouble(0.0);
        AtomicDouble to_08 = new AtomicDouble(0.0);
        AtomicDouble to_09 = new AtomicDouble(0.0);
        AtomicDouble to_10 = new AtomicDouble(0.0);
        AtomicDouble to_11 = new AtomicDouble(0.0);
        AtomicDouble to_12 = new AtomicDouble(0.0);
        AtomicDouble to_13 = new AtomicDouble(0.0);
        AtomicDouble to_14 = new AtomicDouble(0.0);
        AtomicDouble to_15 = new AtomicDouble(0.0);
        AtomicDouble to_16 = new AtomicDouble(0.0);
        AtomicDouble to_17 = new AtomicDouble(0.0);
        AtomicDouble to_18 = new AtomicDouble(0.0);
        AtomicDouble to_19 = new AtomicDouble(0.0);
        AtomicDouble to_20 = new AtomicDouble(0.0);
        AtomicDouble to_21 = new AtomicDouble(0.0);

        AtomicInteger indexrow = new AtomicInteger(4);

        filialidacontrollare.forEach(br1 -> {

            Branch br = get_Branch(br1, all_branch);
            Row row = getRow(sheet, indexrow.get());
            indexrow.addAndGet(1);
            indexrow.addAndGet(1);
            indexrow.addAndGet(1);
            Cell c1 = getCell(row, 0, STRING);
            c1.setCellValue(br.getCod());
            c1.setCellStyle(cellTitle);
            c1 = getCell(row, 1, STRING);
            c1.setCellValue(br.getDe_branch());
            c1.setCellStyle(cellTitle);

            AtomicDouble ad_02 = new AtomicDouble(0.0);
            AtomicDouble ad_03 = new AtomicDouble(0.0);
            AtomicDouble ad_04 = new AtomicDouble(0.0);
            AtomicDouble ad_05 = new AtomicDouble(0.0);
            AtomicDouble ad_06 = new AtomicDouble(0.0);
            AtomicDouble ad_07 = new AtomicDouble(0.0);
            AtomicDouble ad_08 = new AtomicDouble(0.0);
            AtomicDouble ad_09 = new AtomicDouble(0.0);
            AtomicDouble ad_10 = new AtomicDouble(0.0);
            AtomicDouble ad_11 = new AtomicDouble(0.0);
            AtomicDouble ad_12 = new AtomicDouble(0.0);
            AtomicDouble ad_13 = new AtomicDouble(0.0);
            AtomicDouble ad_14 = new AtomicDouble(0.0);
            AtomicDouble ad_15 = new AtomicDouble(0.0);
            AtomicDouble ad_16 = new AtomicDouble(0.0);
            AtomicDouble ad_17 = new AtomicDouble(0.0);
            AtomicDouble ad_18 = new AtomicDouble(0.0);
            AtomicDouble ad_19 = new AtomicDouble(0.0);
            AtomicDouble ad_20 = new AtomicDouble(0.0);
            AtomicDouble ad_21 = new AtomicDouble(0.0);

            giornidacontrollare.forEach(day -> {

                String[] ff = {br.getCod(), br.getDe_branch()};
                String datasql = formatStringtoStringDate(day, patternnormdate_filter, patternsql);

                Daily_value siq = db.list_Daily_value(ff, datasql + " 00:00", datasql + " 23:59", false, true);

                Row row1 = getRow(sheet, indexrow.get());
                indexrow.addAndGet(1);
                Cell c1a = getCell(row1, 1, NUMERIC);
                c1a.setCellValue(day);
                c1a.setCellStyle(cellStylenum);

                c1a = getCell(row1, 2, NUMERIC);
                c1a.setCellValue(fd(siq.getPurchTotal()));
                c1a.setCellStyle(cellStylenum);
                ad_02.addAndGet(fd(siq.getPurchTotal()));

                c1a = getCell(row1, 3, NUMERIC);
                c1a.setCellValue(fd(siq.getCashAdNetTot()));
                c1a.setCellStyle(cellStylenum);
                ad_03.addAndGet(fd(siq.getCashAdNetTot()));

                c1a = getCell(row1, 4, NUMERIC);
                c1a.setCellValue(fd(siq.getSalesTotal()));
                c1a.setCellStyle(cellStylenum);
                ad_04.addAndGet(fd(siq.getSalesTotal()));

                c1a = getCell(row1, 5, NUMERIC);
                c1a.setCellValue(fd(siq.getPurchComm()));
                c1a.setCellStyle(cellStylenum);
                ad_05.addAndGet(fd(siq.getPurchComm()));

                c1a = getCell(row1, 6, NUMERIC);
                c1a.setCellValue(fd(siq.getCashAdComm()));
                c1a.setCellStyle(cellStylenum);
                ad_06.addAndGet(fd(siq.getCashAdComm()));

                c1a = getCell(row1, 7, NUMERIC);
                c1a.setCellValue(fd(siq.getSalesComm()));
                c1a.setCellStyle(cellStylenum);
                ad_07.addAndGet(fd(siq.getSalesComm()));

                c1a = getCell(row1, 8, NUMERIC);
                c1a.setCellValue(fd(siq.getCashOnPremError()));
                c1a.setCellStyle(cellStylenum);
                ad_08.addAndGet(fd(siq.getCashOnPremError()));

                c1a = getCell(row1, 9, NUMERIC);
                c1a.setCellValue(fd(siq.getBaPurchTransfNotes()));
                c1a.setCellStyle(cellStylenum);
                ad_09.addAndGet(fd(siq.getBaPurchTransfNotes()));

                c1a = getCell(row1, 10, NUMERIC);
                c1a.setCellValue(fd(siq.getBaSalesTransfNotes()));
                c1a.setCellStyle(cellStylenum);
                ad_10.addAndGet(fd(siq.getBaSalesTransfNotes()));

                c1a = getCell(row1, 11, NUMERIC);
                c1a.setCellValue(fd(siq.getBaPurchTotal()));
                c1a.setCellStyle(cellStylenum);
                ad_11.addAndGet(fd(siq.getBaPurchTotal()));

                c1a = getCell(row1, 12, NUMERIC);
                c1a.setCellValue(fd(siq.getBaSalesTotal()));
                c1a.setCellStyle(cellStylenum);
                ad_12.addAndGet(fd(siq.getBaSalesTotal()));

                c1a = getCell(row1, 13, NUMERIC);
                c1a.setCellValue(fd(siq.getBraPurchLocalCurr()));
                c1a.setCellStyle(cellStylenum);
                ad_13.addAndGet(fd(siq.getBraPurchLocalCurr()));

                c1a = getCell(row1, 14, NUMERIC);
                c1a.setCellValue(fd(siq.getBraSalesLocalCurr()));
                c1a.setCellStyle(cellStylenum);
                ad_14.addAndGet(fd(siq.getBraSalesLocalCurr()));

                List<DailyKind> array = siq.getDati();

                AtomicDouble ot_15 = new AtomicDouble(0.0);
                AtomicDouble pe_16 = new AtomicDouble(0.0);
                AtomicDouble wu_17 = new AtomicDouble(0.0);
                AtomicDouble wu_18 = new AtomicDouble(0.0);
                AtomicDouble va_19 = new AtomicDouble(0.0);

                array.forEach(noch -> {
                    if (noch.getKind().contains("Western")) {
                        wu_17.addAndGet(fd(noch.getToTotal()));
                        wu_18.addAndGet(fd(noch.getFromTotal()));
                    } else if (noch.getKind().contains("VAT")) {
                        if (noch.getEtichetta1().contains("Refund")) {
                            va_19.addAndGet(fd(noch.getToTotal()));
                        }
                    } else if (noch.getKind().contains("Stock") || noch.getKind().contains("stock")) {
                        ot_15.addAndGet(fd(noch.getFromLocalCurr()));
                        ot_15.addAndGet(fd(noch.getToLocalCurr()));
                    } else if (noch.getKind().contains("Petty")) {
                        if (noch.getEtichetta1().contains("OUT")) {
                            pe_16.addAndGet(fd(noch.getToTotal()));
                        }
                    }
                });

                c1a = getCell(row1, 15, NUMERIC);
                c1a.setCellValue(ot_15.get());
                c1a.setCellStyle(cellStylenum);
                ad_15.addAndGet(ot_15.get());

                c1a = getCell(row1, 16, NUMERIC);
                c1a.setCellValue(pe_16.get());
                c1a.setCellStyle(cellStylenum);
                ad_16.addAndGet(pe_16.get());

                c1a = getCell(row1, 17, NUMERIC);
                c1a.setCellValue(wu_17.get());
                c1a.setCellStyle(cellStylenum);
                ad_17.addAndGet(wu_17.get());

                c1a = getCell(row1, 18, NUMERIC);
                c1a.setCellValue(wu_18.get());
                c1a.setCellStyle(cellStylenum);
                ad_18.addAndGet(wu_18.get());

                c1a = getCell(row1, 20, NUMERIC);
                c1a.setCellValue(va_19.get());
                c1a.setCellStyle(cellStylenum);
                ad_19.addAndGet(va_19.get());

                AtomicDouble cc_20 = new AtomicDouble(0.0);
                List<DailyCOP> arraycc = siq.getDatiCOP();
                arraycc.forEach(cc -> {
                    cc_20.addAndGet(fd(cc.getCcAmount()));
                });

                c1a = getCell(row1, 19, NUMERIC);
                c1a.setCellValue(cc_20.get());
                c1a.setCellStyle(cellStylenum);
                ad_20.addAndGet(cc_20.get());

                c1a = getCell(row1, 21, NUMERIC);
                c1a.setCellValue(fd(siq.getCashOnPrem()));
                c1a.setCellStyle(cellStylenum);
                ad_21.set(fd(siq.getCashOnPrem()));

            });

            indexrow.addAndGet(1);
            row = getRow(sheet, indexrow.get());
            c1 = getCell(row, 1);
            c1.setCellValue("TOTALE");
            c1.setCellStyle(cellTitle);

            c1 = getCell(row, 2, NUMERIC);
            c1.setCellValue(ad_02.get());
            c1.setCellStyle(cellStylenumBO);
            c1 = getCell(row, 3, NUMERIC);
            c1.setCellValue(ad_03.get());
            c1.setCellStyle(cellStylenumBO);
            c1 = getCell(row, 4, NUMERIC);
            c1.setCellValue(ad_04.get());
            c1.setCellStyle(cellStylenumBO);
            c1 = getCell(row, 5, NUMERIC);
            c1.setCellValue(ad_05.get());
            c1.setCellStyle(cellStylenumBO);
            c1 = getCell(row, 6, NUMERIC);
            c1.setCellValue(ad_06.get());
            c1.setCellStyle(cellStylenumBO);
            c1 = getCell(row, 7, NUMERIC);
            c1.setCellValue(ad_07.get());
            c1.setCellStyle(cellStylenumBO);
            c1 = getCell(row, 8, NUMERIC);
            c1.setCellValue(ad_08.get());
            c1.setCellStyle(cellStylenumBO);
            c1 = getCell(row, 9);
            c1.setCellValue(ad_09.get());
            c1.setCellStyle(cellStylenumBO);
            c1 = getCell(row, 10);
            c1.setCellValue(ad_10.get());
            c1.setCellStyle(cellStylenumBO);
            c1 = getCell(row, 11);
            c1.setCellValue(ad_11.get());
            c1.setCellStyle(cellStylenumBO);
            c1 = getCell(row, 12);
            c1.setCellValue(ad_12.get());
            c1.setCellStyle(cellStylenumBO);
            c1 = getCell(row, 13);
            c1.setCellValue(ad_13.get());
            c1.setCellStyle(cellStylenumBO);
            c1 = getCell(row, 14);
            c1.setCellValue(ad_14.get());
            c1.setCellStyle(cellStylenumBO);
            c1 = getCell(row, 15);
            c1.setCellValue(ad_15.get());
            c1.setCellStyle(cellStylenumBO);
            c1 = getCell(row, 16);
            c1.setCellValue(ad_16.get());
            c1.setCellStyle(cellStylenumBO);
            c1 = getCell(row, 17);
            c1.setCellValue(ad_17.get());
            c1.setCellStyle(cellStylenumBO);

            c1 = getCell(row, 18);
            c1.setCellValue(ad_18.get());
            c1.setCellStyle(cellStylenumBO);
            c1 = getCell(row, 20);
            c1.setCellValue(ad_19.get());
            c1.setCellStyle(cellStylenumBO);
            c1 = getCell(row, 19);
            c1.setCellValue(ad_20.get());
            c1.setCellStyle(cellStylenumBO);
            c1 = getCell(row, 21);
            c1.setCellValue(ad_21.get());
            c1.setCellStyle(cellStylenumBO);

            indexrow.addAndGet(1);
            indexrow.addAndGet(1);
            indexrow.addAndGet(1);

            to_02.addAndGet(ad_02.get());
            to_03.addAndGet(ad_03.get());
            to_04.addAndGet(ad_04.get());
            to_05.addAndGet(ad_05.get());
            to_06.addAndGet(ad_06.get());
            to_07.addAndGet(ad_07.get());
            to_08.addAndGet(ad_08.get());
            to_09.addAndGet(ad_09.get());
            to_10.addAndGet(ad_10.get());
            to_11.addAndGet(ad_11.get());
            to_12.addAndGet(ad_12.get());
            to_13.addAndGet(ad_13.get());
            to_14.addAndGet(ad_14.get());
            to_15.addAndGet(ad_15.get());
            to_16.addAndGet(ad_16.get());
            to_17.addAndGet(ad_17.get());
            to_18.addAndGet(ad_18.get());
            to_19.addAndGet(ad_19.get());
            to_20.addAndGet(ad_20.get());
            to_21.addAndGet(ad_21.get());
        });

        db.closeDB();

        Row row = getRow(sheet, indexrow.get());
        Cell c1 = getCell(row, 1);
        c1.setCellValue("TOTALE UK");
        c1.setCellStyle(cellTitle);
        c1 = getCell(row, 2);
        c1.setCellValue(to_02.get());
        c1.setCellStyle(cellStylenumBO);
        c1 = getCell(row, 3);
        c1.setCellValue(to_03.get());
        c1.setCellStyle(cellStylenumBO);
        c1 = getCell(row, 4);
        c1.setCellValue(to_04.get());
        c1.setCellStyle(cellStylenumBO);
        c1 = getCell(row, 5);
        c1.setCellValue(to_05.get());
        c1.setCellStyle(cellStylenumBO);
        c1 = getCell(row, 6);
        c1.setCellValue(to_06.get());
        c1.setCellStyle(cellStylenumBO);
        c1 = getCell(row, 7);
        c1.setCellValue(to_07.get());
        c1.setCellStyle(cellStylenumBO);
        c1 = getCell(row, 8);
        c1.setCellValue(to_08.get());
        c1.setCellStyle(cellStylenumBO);
        c1 = getCell(row, 9);
        c1.setCellValue(to_09.get());
        c1.setCellStyle(cellStylenumBO);
        c1 = getCell(row, 10);
        c1.setCellValue(to_10.get());
        c1.setCellStyle(cellStylenumBO);
        c1 = getCell(row, 11);
        c1.setCellValue(to_11.get());
        c1.setCellStyle(cellStylenumBO);
        c1 = getCell(row, 12);
        c1.setCellValue(to_12.get());
        c1.setCellStyle(cellStylenumBO);
        c1 = getCell(row, 13);
        c1.setCellValue(to_13.get());
        c1.setCellStyle(cellStylenumBO);
        c1 = getCell(row, 14);
        c1.setCellValue(to_14.get());
        c1.setCellStyle(cellStylenumBO);
        c1 = getCell(row, 15);
        c1.setCellValue(to_15.get());
        c1.setCellStyle(cellStylenumBO);
        c1 = getCell(row, 16);
        c1.setCellValue(to_16.get());
        c1.setCellStyle(cellStylenumBO);
        c1 = getCell(row, 17);
        c1.setCellValue(to_17.get());
        c1.setCellStyle(cellStylenumBO);
        c1 = getCell(row, 18);
        c1.setCellValue(to_18.get());
        c1.setCellStyle(cellStylenumBO);
        c1 = getCell(row, 20);
        c1.setCellValue(to_19.get());
        c1.setCellStyle(cellStylenumBO);
        c1 = getCell(row, 19);
        c1.setCellValue(to_20.get());
        c1.setCellStyle(cellStylenumBO);
        c1 = getCell(row, 21);
        c1.setCellValue(to_21.get());
        c1.setCellStyle(cellStylenumBO);

        indexrow.addAndGet(1);
        indexrow.addAndGet(1);
        indexrow.addAndGet(1);

        XSSFCellStyle backgroundStyle = wb.createCellStyle();
        IndexedColorMap colorMap = wb.getStylesSource().getIndexedColors();
        XSSFColor myColor = new XSSFColor(new java.awt.Color(153, 255, 255), colorMap);
        backgroundStyle.setFillForegroundColor(myColor);
        backgroundStyle.setFillPattern(SOLID_FOREGROUND);
        backgroundStyle.setBorderBottom(THIN);
        backgroundStyle.setBorderTop(THIN);
        backgroundStyle.setBorderRight(THIN);
        backgroundStyle.setBorderLeft(THIN);
        backgroundStyle.setAlignment(CENTER);
        backgroundStyle.setFont(font2);

        XSSFCellStyle backgroundStyleNB = wb.createCellStyle();
        backgroundStyleNB.setFillForegroundColor(myColor);
        backgroundStyleNB.setFillPattern(SOLID_FOREGROUND);
        backgroundStyleNB.setBorderBottom(THIN);
        backgroundStyleNB.setBorderTop(THIN);
        backgroundStyleNB.setBorderRight(THIN);
        backgroundStyleNB.setBorderLeft(THIN);

        XSSFCellStyle backgroundStyle2 = wb.createCellStyle();
        backgroundStyle2.setFillForegroundColor(myColor);
        backgroundStyle2.setFillPattern(SOLID_FOREGROUND);
        backgroundStyle2.setFillPattern(SOLID_FOREGROUND);
        backgroundStyle2.setBorderBottom(THIN);
        backgroundStyle2.setBorderTop(THIN);
        backgroundStyle2.setBorderRight(THIN);
        backgroundStyle2.setBorderLeft(THIN);
        backgroundStyle2.setFont(font3);

        XSSFCellStyle backgroundStyleNum = (XSSFCellStyle) wb.createCellStyle();
        backgroundStyleNum.setDataFormat(hssfDataFormat.getFormat(formatdataCell));
        backgroundStyleNum.setBorderBottom(THIN);
        backgroundStyleNum.setBorderTop(THIN);
        backgroundStyleNum.setBorderRight(THIN);
        backgroundStyleNum.setBorderLeft(THIN);
        backgroundStyleNum.setFillForegroundColor(myColor);
        backgroundStyleNum.setFillPattern(SOLID_FOREGROUND);

        row = getRow(sheet, indexrow.get());
        c1 = getCell(row, 1);
        c1.setCellValue("ACCOUNTANCY");
        c1.setCellStyle(backgroundStyle);

        c1 = getCell(row, 2);
        c1.setCellValue("");
        c1.setCellStyle(backgroundStyleNB);
        c1 = getCell(row, 3);
        c1.setCellValue("");
        c1.setCellStyle(backgroundStyleNB);
        c1 = getCell(row, 4);
        c1.setCellValue("");
        c1.setCellStyle(backgroundStyleNB);
        c1 = getCell(row, 5);
        c1.setCellValue("");
        c1.setCellStyle(backgroundStyleNB);
        sheet.addMergedRegion(new CellRangeAddress(indexrow.get(), indexrow.get(), 1, 5));
        indexrow.addAndGet(1);

        row = getRow(sheet, indexrow.get());
        c1 = getCell(row, 3);
        c1.setCellValue("COST");
        c1.setCellStyle(backgroundStyle2);
        c1 = getCell(row, 4);
        c1.setCellValue("REVENUE");
        c1.setCellStyle(backgroundStyle2);
        c1 = getCell(row, 2);
        c1.setCellValue("");
        c1.setCellStyle(backgroundStyleNB);
        c1 = getCell(row, 1);
        c1.setCellValue("");
        c1.setCellStyle(backgroundStyleNB);
        c1 = getCell(row, 5);
        c1.setCellValue("");
        c1.setCellStyle(backgroundStyleNB);
        indexrow.addAndGet(1);

        row = getRow(sheet, indexrow.get());
        c1 = getCell(row, 1);
        c1.setCellValue("Commisions received on currency sales");
        c1.setCellStyle(backgroundStyle2);
        c1 = getCell(row, 2);
        c1.setCellValue("");
        c1.setCellStyle(backgroundStyleNB);
        c1 = getCell(row, 3);
        c1.setCellValue("");
        c1.setCellStyle(backgroundStyleNB);
        c1 = getCell(row, 4);
        c1.setCellValue(to_07.get());
        c1.setCellStyle(backgroundStyleNum);
        c1 = getCell(row, 5);
        c1.setCellValue("");
        c1.setCellStyle(backgroundStyleNB);
        indexrow.addAndGet(1);

        row = getRow(sheet, indexrow.get());
        c1 = getCell(row, 1);
        c1.setCellValue("Commissions received on currency purchases");
        c1.setCellStyle(backgroundStyle2);
        c1 = getCell(row, 2);
        c1.setCellValue("");
        c1.setCellStyle(backgroundStyleNB);
        c1 = getCell(row, 3);
        c1.setCellValue("");
        c1.setCellStyle(backgroundStyleNB);
        c1 = getCell(row, 4);
        c1.setCellValue(to_06.get() + to_05.get());
        c1.setCellStyle(backgroundStyleNum);
        c1 = getCell(row, 5);
        c1.setCellValue("");
        c1.setCellStyle(backgroundStyleNB);

        indexrow.addAndGet(1);
        row = getRow(sheet, indexrow.get());
        c1 = getCell(row, 1);
        c1.setCellValue("Currency purchases from customers");
        c1.setCellStyle(backgroundStyle2);
        c1 = getCell(row, 2);
        c1.setCellValue("");
        c1.setCellStyle(backgroundStyleNB);
        c1 = getCell(row, 3);

        c1.setCellValue(to_02.get() + to_05.get());
//        c1.setCellValue(to_02.get() + to_03.get() + to_06.get() + to_05.get());

        c1.setCellStyle(backgroundStyleNum);
        c1 = getCell(row, 4);
        c1.setCellValue("");
        c1.setCellStyle(backgroundStyleNB);
        c1 = getCell(row, 5);
        c1.setCellValue("");
        c1.setCellStyle(backgroundStyleNB);
        indexrow.addAndGet(1);
        row = getRow(sheet, indexrow.get());
        c1 = getCell(row, 1);
        c1.setCellValue("Currency sales to customers");
        c1.setCellStyle(backgroundStyle2);
        c1 = getCell(row, 2);
        c1.setCellValue("");
        c1.setCellStyle(backgroundStyleNB);
        c1 = getCell(row, 3);
        c1.setCellValue("");
        c1.setCellStyle(backgroundStyleNB);
        c1 = getCell(row, 4);
        c1.setCellValue(to_04.get() - to_07.get());
        c1.setCellStyle(backgroundStyleNum);
        c1 = getCell(row, 5);

        double f165 = (to_07.get())
                + (to_06.get() + to_05.get()) //E163      
                + (to_04.get() - to_07.get()) ///E165
                - (to_02.get() + to_05.get()) //D164
                //                - (to_02.get() + to_03.get() + to_06.get() + to_05.get()) //D164
                ;

        c1.setCellValue(
                f165
        //                to_04.get() - to_07.get() +
        //                to_06.get() + to_05.get() + to_07.get() 
        //                - to_02.get() + to_03.get() + to_06.get() + to_05.get()
        );
        c1.setCellStyle(backgroundStyleNum);
        indexrow.addAndGet(1);

        row = getRow(sheet, indexrow.get());
        c1 = getCell(row, 1);
        c1.setCellValue("");
        c1.setCellStyle(backgroundStyleNB);
        c1 = getCell(row, 2);
        c1.setCellValue("");
        c1.setCellStyle(backgroundStyleNB);
        c1 = getCell(row, 3);
        c1.setCellValue("");
        c1.setCellStyle(backgroundStyleNB);
        c1 = getCell(row, 4);
        c1.setCellValue("");
        c1.setCellStyle(backgroundStyleNB);
        c1 = getCell(row, 5);
        c1.setCellValue("");
        c1.setCellStyle(backgroundStyleNB);

        indexrow.addAndGet(1);
        row = getRow(sheet, indexrow.get());
        c1 = getCell(row, 1);
        c1.setCellValue("Currency purchased from MoneyCorp");
        c1.setCellStyle(backgroundStyle2);
        c1 = getCell(row, 2);
        c1.setCellValue("");
        c1.setCellStyle(backgroundStyleNB);
        c1 = getCell(row, 3);
        c1.setCellValue(to_11.get());
        c1.setCellStyle(backgroundStyleNum);
        c1 = getCell(row, 4);
        c1.setCellValue("");
        c1.setCellStyle(backgroundStyleNB);
        c1 = getCell(row, 5);
        c1.setCellValue("");
        c1.setCellStyle(backgroundStyleNB);
        indexrow.addAndGet(1);
        row = getRow(sheet, indexrow.get());
        c1 = getCell(row, 1);
        c1.setCellValue("Currency sales to MoneyCorp");
        c1.setCellStyle(backgroundStyle2);
        c1 = getCell(row, 2);
        c1.setCellValue("");
        c1.setCellStyle(backgroundStyleNB);
        c1 = getCell(row, 3);
        c1.setCellValue("");
        c1.setCellStyle(backgroundStyleNB);
        c1 = getCell(row, 4);
        c1.setCellValue(to_12.get());
        c1.setCellStyle(backgroundStyleNum);
        c1 = getCell(row, 5);
        c1.setCellValue(
                f165 + to_12.get() - to_11.get()
        //                to_04.get() - to_07.get() + to_06.get() + to_05.get() + to_07.get() - to_02.get() + to_03.get() + to_06.get() + to_05.get() + to_12.get() - to_11.get()
        );
        c1.setCellStyle(backgroundStyleNum);

        for (int r = 0; r < 30; r++) {
            sheet.autoSizeColumn(r);
        }

        try {
            File out = new File(pathout + generaId(75) + ".xlsx");
            try ( FileOutputStream fileOut = new FileOutputStream(out)) {
                wb.write(fileOut);
            }
            wb.close();
            String base64 = new String(encodeBase64(readFileToByteArray(out)));
            out.delete();
            return base64;
        } catch (IOException ex) {
            insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
        }

        return null;
    }

    /**
     *
     * @param pathout
     * @param output
     * @param array_unlockrate
     * @return
     */
    public static String create_cdc(String pathout, ArrayList<DailyChange_CG> output, ArrayList<String[]> array_unlockrate) {

        try {
//            InputStream is = new FileInputStream(new File("C:\\Maccorp\\modificareport2022.xlsx"));
            InputStream is = new ByteArrayInputStream(decodeBase64(getConf("path.rep1cdc.23")));
            XSSFWorkbook wb = new XSSFWorkbook(is);
            XSSFCellStyle cellStylenum = (XSSFCellStyle) wb.createCellStyle();
            XSSFDataFormat hssfDataFormat = (XSSFDataFormat) wb.createDataFormat();
            cellStylenum.setDataFormat(hssfDataFormat.getFormat(formatdataCell));
            cellStylenum.setBorderBottom(THIN);
            cellStylenum.setBorderTop(THIN);
            cellStylenum.setBorderRight(THIN);
            cellStylenum.setBorderLeft(THIN);

            XSSFCellStyle cellStyle = (XSSFCellStyle) wb.createCellStyle();
            cellStyle.setBorderBottom(THIN);
            cellStyle.setBorderTop(THIN);
            cellStyle.setBorderRight(THIN);
            cellStyle.setBorderLeft(THIN);

            XSSFCellStyle cellStylenumrate = (XSSFCellStyle) wb.createCellStyle();
            cellStylenumrate.setDataFormat(hssfDataFormat.getFormat(formatdataCellRATE));
            cellStylenumrate.setBorderBottom(THIN);
            cellStylenumrate.setBorderTop(THIN);
            cellStylenumrate.setBorderRight(THIN);
            cellStylenumrate.setBorderLeft(THIN);

            XSSFCellStyle cellStyleint = (XSSFCellStyle) wb.createCellStyle();
            cellStyleint.setDataFormat(hssfDataFormat.getFormat(formatdataCelINT));
            cellStyleint.setBorderBottom(THIN);
            cellStyleint.setBorderTop(THIN);
            cellStyleint.setBorderRight(THIN);
            cellStyleint.setBorderLeft(THIN);

            Sheet sheet = wb.getSheetAt(0);

            CellStyle backgroundStyle = wb.createCellStyle();
            IndexedColorMap colorMap = wb.getStylesSource().getIndexedColors();
            XSSFColor color = new XSSFColor(new java.awt.Color(146, 208, 80), colorMap); //accepts a short value

            XSSFFont font = wb.createFont();
            font.setFontHeightInPoints((short) 11);
            font.setFontName("Calibri");
            font.setColor(BLACK.getIndex());
            font.setBold(true);
            font.setItalic(false);
            ((XSSFCellStyle) backgroundStyle).setFillForegroundColor(color);
            backgroundStyle.setFillPattern(SOLID_FOREGROUND);
            backgroundStyle.setBorderBottom(THIN);
            backgroundStyle.setBorderTop(THIN);
            backgroundStyle.setBorderRight(THIN);
            backgroundStyle.setBorderLeft(THIN);
            backgroundStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            backgroundStyle.setAlignment(CENTER);

            backgroundStyle.setFont(font);

            int st = 2;
            for (int i = 0; i < output.size(); i++) {
                DailyChange_CG dc = output.get(i);
                Row row = getRow(sheet, st + i);
                Cell c1 = getCell(row, 1);
                c1.setCellValue(dc.getCDC());
                c1 = getCell(row, 2);
                c1.setCellValue(dc.getSPORTELLO());
                c1 = getCell(row, 3);
                c1.setCellValue(dc.getID());
                c1 = getCell(row, 4);
                c1.setCellValue(dc.getDELETE());
                c1 = getCell(row, 5);
                c1.setCellValue(dc.getAREA());
                c1 = getCell(row, 6);
                c1.setCellValue(dc.getCITTA());
                c1 = getCell(row, 7);
                c1.setCellValue(dc.getUBICAZIONE());
                c1 = getCell(row, 8);
                c1.setCellValue(dc.getGRUPPO());
                c1 = getCell(row, 9);
                c1.setCellValue(dc.getDATA());
                c1 = getCell(row, 10);
                c1.setCellValue(dc.getORA());
                c1 = getCell(row, 11);
                c1.setCellValue(dc.getMESE());
                c1 = getCell(row, 12);
                c1.setCellValue(dc.getANNO());
                c1 = getCell(row, 13);
                c1.setCellValue(dc.getCODUSER());
                c1 = getCell(row, 14);
                c1.setCellValue(dc.getUSERNOME());
                c1 = getCell(row, 15);
                c1.setCellValue(dc.getUSERCOGNOME());
                c1 = getCell(row, 16);
                c1.setCellValue(dc.getMETODOPAGAMENTO());
                c1 = getCell(row, 17);
                c1.setCellValue(dc.getRESIDENZACLIENTE());
                c1 = getCell(row, 18);
                c1.setCellValue(dc.getNAZIONALITACLIENTE());
                c1 = getCell(row, 19);
                c1.setCellValue(dc.getCOMMENTI());
                c1 = getCell(row, 20);
                c1.setCellValue(dc.getACQUISTOVENDITA());
                c1 = getCell(row, 21);
                c1.setCellValue(dc.getTIPOLOGIAACQOVEND());
                c1 = getCell(row, 22);
                c1.setCellValue(dc.getVALUTA());
                c1 = getCell(row, 23, NUMERIC);
                c1.setCellValue(fd(dc.getQUANTITA()));
                c1.setCellStyle(cellStylenum);

                c1 = getCell(row, 24, NUMERIC);
                c1.setCellValue(fd(dc.getTASSODICAMBIO()));
                c1.setCellStyle(cellStylenumrate);

                c1 = getCell(row, 25, NUMERIC);
                c1.setCellValue(fd(dc.getCONTROVALORE()));
                c1.setCellStyle(cellStylenum);
                c1 = getCell(row, 26, NUMERIC);
                c1.setCellValue(fd(dc.getCOMMVARIABILE()));
                c1.setCellStyle(cellStylenum);
                c1 = getCell(row, 27, NUMERIC);
                c1.setCellValue(fd(dc.getCOMMFISSA()));
                c1.setCellStyle(cellStylenum);

                c1 = getCell(row, 28, NUMERIC);
                c1.setCellValue(fd(dc.getSPREADBRANCH()));
                c1.setCellStyle(cellStylenum);
                c1 = getCell(row, 29, NUMERIC);
                c1.setCellValue(fd(dc.getSPREADBANK()));
                c1.setCellStyle(cellStylenum);
                c1 = getCell(row, 30, NUMERIC);
                c1.setCellValue(fd(dc.getSPREADVEND()));

                c1.setCellStyle(cellStylenum);
                c1 = getCell(row, 31, NUMERIC);
                c1.setCellValue(fd(dc.getTOTGM()));

                c1.setCellStyle(cellStylenum);

                c1 = getCell(row, 32, NUMERIC);
                c1.setCellValue(fd(dc.getPERCCOMM()));
                c1.setCellStyle(cellStylenum);

                c1 = getCell(row, 33, NUMERIC);
                c1.setCellValue(fd(dc.getPERCSPREADVENDITA()));
                c1.setCellStyle(cellStylenum);

                if (dc.getVENDITABUYBACK().equals("") || dc.getVENDITABUYBACK().startsWith("F")) {
                    c1 = getCell(row, 34);
                    c1.setCellValue(dc.getVENDITABUYBACK());
                } else {
                    c1 = getCell(row, 34, NUMERIC);
                    c1.setCellValue(fd(dc.getVENDITABUYBACK()));
                    c1.setCellStyle(cellStylenum);
                }
                if (dc.getVENDITASELLBACK().equals("") || dc.getVENDITASELLBACK().startsWith("F")) {
                    c1 = getCell(row, 35);
                    c1.setCellValue(dc.getVENDITASELLBACK());
                } else {
                    c1 = getCell(row, 35, NUMERIC);
                    c1.setCellValue(fd(dc.getVENDITASELLBACK()));
                    c1.setCellStyle(cellStylenum);
                }

                c1 = getCell(row, 36);
                c1.setCellValue(dc.getCODICEINTERNETBOOKING());
                c1 = getCell(row, 37);
                c1.setCellValue(dc.getMOTIVOPERRIDUZIONEDELLACOMM());
                c1 = getCell(row, 38);
                c1.setCellValue(dc.getMOTIVOPERRIDUZIONEDELLACOMMFISSA());

                c1 = getCell(row, 39);
                c1.setCellValue(dc.getCODICESBLOCCO());

                c1 = getCell(row, 40);
                c1.setCellValue(dc.getLOYALTYCODE());
                c1.setCellStyle(cellStyle);
                
                c1 = getCell(row, 41);
                c1.setCellValue(dc.getAGCODE());
                c1.setCellStyle(cellStyle);
                c1 = getCell(row, 42);
                c1.setCellValue(dc.getAGNUMBER());
                c1.setCellStyle(cellStyle);
                
                c1 = getCell(row, 43);
                c1.setCellValue(dc.getIBCHAN());
                c1.setCellStyle(cellStyle);
                
                if (dc.getCBCOMM().trim().equals("")) {
                    c1 = getCell(row, 44);
                    c1.setCellValue("");
                } else {
                    c1 = getCell(row, 44, NUMERIC);
                    c1.setCellValue(fd(dc.getCBCOMM()));
                    c1.setCellStyle(cellStylenum);
                }
                
            }

            for (int r = 0; r < 45; r++) {
                sheet.autoSizeColumn(r);
            }

            try {
                File out = new File(pathout + generaId(75) + ".xlsx");
                try ( FileOutputStream fileOut = new FileOutputStream(out)) {
                    wb.write(fileOut);
                }
                is.close();
                wb.close();

                String base64 = new String(encodeBase64(readFileToByteArray(out)));
                out.delete();
                return base64;
            } catch (Exception ex) {
                insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
            }

        } catch (Exception ex) {
            insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
        }

        return null;
    }

    private static final String formatdataCell = "#,#.00";
    private static final String formatdataCellRATE = "#,#.00000000";
    private static final String formatdataCelINT = "0";

    /**
     *
     * @param pathout
     * @param output
     * @return
     */
    public static String create_changeacc1(String pathout, ArrayList<DailyChange> output) {

        try {

            //InputStream is = new FileInputStream(new File("C:\\Maccorp\\Report1 CDC T.xlsx"));
//            InputStream is = new ByteArrayInputStream(Base64.decodeBase64(Engine.getConf("path.rep1")));
            InputStream is = new ByteArrayInputStream(decodeBase64(getConf("path.rep1.2023")));
            XSSFWorkbook wb = new XSSFWorkbook(is);
            // Sheet sheet = wb.createSheet("RC");
            Sheet sheet = wb.getSheetAt(0);

            XSSFCellStyle cellStylenum = (XSSFCellStyle) wb.createCellStyle();
            XSSFDataFormat hssfDataFormat = (XSSFDataFormat) wb.createDataFormat();
            cellStylenum.setDataFormat(hssfDataFormat.getFormat(formatdataCell));
            cellStylenum.setBorderBottom(THIN);
            cellStylenum.setBorderTop(THIN);
            cellStylenum.setBorderRight(THIN);
            cellStylenum.setBorderLeft(THIN);

            XSSFCellStyle cellStyleint = (XSSFCellStyle) wb.createCellStyle();
            cellStyleint.setDataFormat(hssfDataFormat.getFormat(formatdataCelINT));
            cellStyleint.setBorderBottom(THIN);
            cellStyleint.setBorderTop(THIN);
            cellStyleint.setBorderRight(THIN);
            cellStyleint.setBorderLeft(THIN);

            double getVOLUMEAC = 0.00;
            double getVOLUMECA = 0.00;
            double getVOLUMETC = 0.00;
            double getTRANSAC = 0.00;
            double getTRANSCA = 0.00;
            double getTRANSTC = 0.00;
            double getCOMMAC = 0.00;
            double getCOMMCA = 0.00;
            double getCOMMTC = 0.00;

            double getSPREADAC = 0.00;
            double getSPREADCA = 0.00;
            double getSPREADBR = 0.00;
            double getSPREADBA = 0.00;
            int getTOTTRANSACQ = 0;
            double getTOTVOLACQ = 0.00;
            double getTOTGMACQ = 0.00;
            double getPERCACQ = 0.00;
            double getVOLUMEVENDOFF = 0.00;
            double getVOLUMEONL = 0.00;
            double getVOLUMERIVA = 0.00;

            int getTRANSVENDOFF = 0;
            int getTRANSONL = 0;
            int getTRANSRIVA = 0;

            double getCOMMVENDOFF = 0.00;
            double getCOMMONL = 0.00;
            double getCOMMRIVA = 0.00;
            double getSPREADVEND = 0.00;
            double getTOTVOLVEN = 0.00;

            int getTOTTRANSVEN = 0;

            double getTOTGMVEN = 0.00;
            double getPERCVEN = 0.00;
            double getTOTVOL = 0.00;

            int getTOTTRANS = 0;

            double getTOTGM = 0.0;
            double getPERCVEND = 0.0;
            double getCOP = 0.0;
            double getTOBANKCOP = 0.0;
            double getFRBANKCOP = 0.0;
            double getTOBRCOP = 0.0;
            double getFRBRCOP = 0.0;
            double getOCERRCOP = 0.0;
            double getFX = 0.0;

            double getTOBANKFX = 0.0;
            double getFRBANKFX = 0.0;
            double getTOBRFX = 0.0;
            double getFRBRFX = 0.0;
            double getOCERRFX = 0.0;

            int st = 4;
            int lastindex = 0;
            for (int i = 0; i < output.size(); i++) {
                DailyChange dc = output.get(i);
                lastindex = st + i;
                Row row = getRow(sheet, lastindex);
                Cell c1 = getCell(row, 1);
                c1.setCellValue(dc.getFiliale());

                c1 = getCell(row, 2);
                c1.setCellValue(dc.getDescr());

                c1 = getCell(row, 3);
                c1.setCellValue(dc.getData());
                c1 = getCell(row, 4, NUMERIC);
                c1.setCellValue(fd(dc.getVOLUMEAC()));
                c1.setCellStyle(cellStylenum);

                c1 = getCell(row, 5, NUMERIC);
                c1.setCellValue(fd(dc.getVOLUMECA()));
                c1.setCellStyle(cellStylenum);

                c1 = getCell(row, 6, NUMERIC);
                c1.setCellValue(fd(dc.getVOLUMETC()));

                c1.setCellStyle(cellStylenum);

                c1 = getCell(row, 7, NUMERIC);
                c1.setCellValue(parseIntR(dc.getTRANSAC()));

                c1.setCellStyle(cellStyleint);

                c1 = getCell(row, 8, NUMERIC);
                c1.setCellValue(parseIntR(dc.getTRANSCA()));

                c1.setCellStyle(cellStyleint);

                c1 = getCell(row, 9, NUMERIC);
                c1.setCellValue(parseIntR(dc.getTRANSTC()));
                c1.setCellStyle(cellStyleint);

                c1 = getCell(row, 10, NUMERIC);
                c1.setCellValue(fd(dc.getCOMMAC()));
                c1.setCellStyle(cellStylenum);
                c1 = getCell(row, 11, NUMERIC);
                c1.setCellValue(fd(dc.getCOMMCA()));
                c1.setCellStyle(cellStylenum);
                c1 = getCell(row, 12, NUMERIC);
                c1.setCellValue(fd(dc.getCOMMTC()));
                c1.setCellStyle(cellStylenum);

                c1 = getCell(row, 13, NUMERIC);
                c1.setCellValue(fd(dc.getSPREADAC()));
                c1.setCellStyle(cellStylenum);
                c1 = getCell(row, 14, NUMERIC);
                c1.setCellValue(fd(dc.getSPREADCA()));
                c1.setCellStyle(cellStylenum);

                c1 = getCell(row, 15, NUMERIC);
                c1.setCellValue(fd(dc.getSPREADBR()));
                c1.setCellStyle(cellStylenum);
                c1 = getCell(row, 16, NUMERIC);
                c1.setCellValue(fd(dc.getSPREADBA()));
                c1.setCellStyle(cellStylenum);

                c1 = getCell(row, 17, NUMERIC);
                c1.setCellValue(parseIntR(dc.getTOTTRANSACQ()));
                c1.setCellStyle(cellStyleint);

                c1 = getCell(row, 18, NUMERIC);
                c1.setCellValue(fd(dc.getTOTVOLACQ()));
                c1.setCellStyle(cellStylenum);
                c1 = getCell(row, 19, NUMERIC);
                c1.setCellValue(fd(dc.getTOTGMACQ()));
                c1.setCellStyle(cellStylenum);
                c1 = getCell(row, 20, NUMERIC);
                c1.setCellValue(fd(dc.getPERCACQ()));
                c1.setCellStyle(cellStylenum);
                c1 = getCell(row, 21, NUMERIC);
                c1.setCellValue(fd(dc.getVOLUMEVENDOFF()));
                c1.setCellStyle(cellStylenum);
                c1 = getCell(row, 22, NUMERIC);
                c1.setCellValue(fd(dc.getVOLUMEONL()));
                c1.setCellStyle(cellStylenum);
                c1 = getCell(row, 23, NUMERIC);
                c1.setCellValue(fd(dc.getVOLUMERIVA()));
                c1.setCellStyle(cellStylenum);
                c1 = getCell(row, 24, NUMERIC);
                c1.setCellValue(parseIntR(dc.getTRANSVENDOFF()));
                c1.setCellStyle(cellStyleint);
                c1 = getCell(row, 25, NUMERIC);
                c1.setCellValue(parseIntR(dc.getTRANSONL()));
                c1.setCellStyle(cellStyleint);
                c1 = getCell(row, 26, NUMERIC);
                c1.setCellValue(parseIntR(dc.getTRANSRIVA()));
                c1.setCellStyle(cellStyleint);
                c1 = getCell(row, 27, NUMERIC);
                c1.setCellValue(fd(dc.getCOMMVENDOFF()));
                c1.setCellStyle(cellStylenum);
                c1 = getCell(row, 28, NUMERIC);
                c1.setCellValue(fd(dc.getCOMMONL()));
                c1.setCellStyle(cellStylenum);
                c1 = getCell(row, 29, NUMERIC);
                c1.setCellValue(fd(dc.getCOMMRIVA()));
                c1.setCellStyle(cellStylenum);
                c1 = getCell(row, 30, NUMERIC);
                c1.setCellValue(fd(dc.getSPREADVEND()));
                c1.setCellStyle(cellStylenum);
                c1 = getCell(row, 31, NUMERIC);
                c1.setCellValue(fd(dc.getTOTVOLVEN()));
                c1.setCellStyle(cellStylenum);
                c1 = getCell(row, 32, NUMERIC);
                c1.setCellValue(parseIntR(dc.getTOTTRANSVEN()));
                c1.setCellStyle(cellStyleint);
                c1 = getCell(row, 33, NUMERIC);
                c1.setCellValue(fd(dc.getTOTGMVEN()));
                c1.setCellStyle(cellStylenum);
                c1 = getCell(row, 34, NUMERIC);
                c1.setCellValue(fd(dc.getPERCVEN()));
                c1.setCellStyle(cellStylenum);
                c1 = getCell(row, 35, NUMERIC);
                c1.setCellValue(fd(dc.getTOTVOL()));
                c1.setCellStyle(cellStylenum);
                c1 = getCell(row, 36, NUMERIC);
                c1.setCellValue(parseIntR(dc.getTOTTRANS()));
                c1.setCellStyle(cellStyleint);
                c1 = getCell(row, 37, NUMERIC);
                c1.setCellValue(fd(dc.getTOTGM()));
                c1.setCellStyle(cellStylenum);
                c1 = getCell(row, 38, NUMERIC);
                c1.setCellValue(fd(dc.getPERCVEND()));
                c1.setCellStyle(cellStylenum);
                c1 = getCell(row, 39, NUMERIC);
                c1.setCellValue(fd(dc.getCOP()));
                c1.setCellStyle(cellStylenum);
                c1 = getCell(row, 40, NUMERIC);
                c1.setCellValue(fd(dc.getTOBANKCOP()));
                c1.setCellStyle(cellStylenum);
                c1 = getCell(row, 41, NUMERIC);
                c1.setCellValue(fd(dc.getFRBANKCOP()));
                c1.setCellStyle(cellStylenum);
                c1 = getCell(row, 42, NUMERIC);
                c1.setCellValue(fd(dc.getTOBRCOP()));
                c1.setCellStyle(cellStylenum);
                c1 = getCell(row, 43, NUMERIC);
                c1.setCellValue(fd(dc.getFRBRCOP()));
                c1.setCellStyle(cellStylenum);
                c1 = getCell(row, 44, NUMERIC);
                c1.setCellValue(fd(dc.getOCERRCOP()));
                c1.setCellStyle(cellStylenum);
                c1 = getCell(row, 45, NUMERIC);
                c1.setCellValue(fd(dc.getFX()));
                c1.setCellStyle(cellStylenum);
                c1 = getCell(row, 46, NUMERIC);
                c1.setCellValue(fd(dc.getTOBANKFX()));
                c1.setCellStyle(cellStylenum);
                c1 = getCell(row, 47, NUMERIC);
                c1.setCellValue(fd(dc.getFRBANKFX()));
                c1.setCellStyle(cellStylenum);
                c1 = getCell(row, 48, NUMERIC);
                c1.setCellValue(fd(dc.getTOBRFX()));
                c1.setCellStyle(cellStylenum);
                c1 = getCell(row, 49, NUMERIC);
                c1.setCellValue(fd(dc.getFRBRFX()));
                c1.setCellStyle(cellStylenum);
                c1 = getCell(row, 50, NUMERIC);
                c1.setCellValue(fd(dc.getOCERRFX()));
                c1.setCellStyle(cellStylenum);

                getVOLUMEAC += fd(dc.getVOLUMEAC());
                getVOLUMECA += fd(dc.getVOLUMECA());
                getVOLUMETC += fd(dc.getVOLUMETC());
                getTRANSAC += parseIntR(dc.getTRANSAC());
                getTRANSCA += parseIntR(dc.getTRANSCA());
                getTRANSTC += parseIntR(dc.getTRANSTC());
                getCOMMAC += fd(dc.getCOMMAC());
                getCOMMCA += fd(dc.getCOMMCA());
                getCOMMTC += fd(dc.getCOMMTC());

                getSPREADAC += fd(dc.getSPREADAC());
                getSPREADCA += fd(dc.getSPREADCA());

                getSPREADBR += fd(dc.getSPREADBR());
                getSPREADBA += fd(dc.getSPREADBA());
                getTOTTRANSACQ += parseIntR(dc.getTOTTRANSACQ());
                getTOTVOLACQ += fd(dc.getTOTVOLACQ());
                getTOTGMACQ += fd(dc.getTOTGMACQ());
                getPERCACQ += fd(dc.getPERCACQ());
                getVOLUMEVENDOFF += fd(dc.getVOLUMEVENDOFF());
                getVOLUMEONL += fd(dc.getVOLUMEONL());
                getVOLUMERIVA += fd(dc.getVOLUMERIVA());
                getTRANSVENDOFF += parseIntR(dc.getTRANSVENDOFF());
                getTRANSONL += parseIntR(dc.getTRANSONL());
                getTRANSRIVA += parseIntR(dc.getTRANSRIVA());
                getCOMMVENDOFF += fd(dc.getCOMMVENDOFF());
                getCOMMONL += fd(dc.getCOMMONL());
                getCOMMRIVA += fd(dc.getCOMMRIVA());
                getSPREADVEND += fd(dc.getSPREADVEND());
                getTOTVOLVEN += fd(dc.getTOTVOLVEN());
                getTOTTRANSVEN += parseIntR(dc.getTOTTRANSVEN());
                getTOTGMVEN += fd(dc.getTOTGMVEN());
                getPERCVEN += fd(dc.getPERCVEN());
                getTOTVOL += fd(dc.getTOTVOL());
                getTOTTRANS += parseIntR(dc.getTOTTRANS());
                getTOTGM += fd(dc.getTOTGM());
                getPERCVEND += fd(dc.getPERCVEND());
                getCOP += fd(dc.getCOP());
                getTOBANKCOP += fd(dc.getTOBANKCOP());
                getFRBANKCOP += fd(dc.getFRBANKCOP());
                getTOBRCOP += fd(dc.getTOBRCOP());
                getFRBRCOP += fd(dc.getFRBRCOP());
                getOCERRCOP += fd(dc.getOCERRCOP());
                getFX += fd(dc.getFX());
                getTOBANKFX += fd(dc.getTOBANKFX());
                getFRBANKFX += fd(dc.getFRBANKFX());
                getTOBRFX += fd(dc.getTOBRFX());
                getFRBRFX += fd(dc.getFRBRFX());
                getOCERRFX += fd(dc.getOCERRFX());

            }

            XSSFFont font = (XSSFFont) wb.createFont();
            font.setFontName(FONT_ARIAL);
            font.setFontHeightInPoints((short) 11);
            font.setBold(true);

            XSSFCellStyle cellStyleblanktot = (XSSFCellStyle) wb.createCellStyle();
            cellStyleblanktot.setBorderRight(THIN);
            cellStyleblanktot.setBorderBottom(THICK);

            cellStyleblanktot.setBorderTop(THICK);
            cellStyleblanktot.setBorderLeft(THIN);
            cellStyleblanktot.setFont(font);

            XSSFCellStyle cellStylenumtot = (XSSFCellStyle) wb.createCellStyle();
            cellStylenumtot.setDataFormat(hssfDataFormat.getFormat(formatdataCell));
            cellStylenumtot.setBorderRight(THIN);
            cellStylenumtot.setBorderBottom(THICK);
            cellStylenumtot.setBorderTop(THICK);
            cellStylenumtot.setBorderLeft(THIN);
            cellStylenumtot.setFont(font);

            XSSFCellStyle cellStyleinttot = (XSSFCellStyle) wb.createCellStyle();
            cellStyleinttot.setDataFormat(hssfDataFormat.getFormat(formatdataCelINT));
            cellStyleinttot.setBorderRight(THIN);
            cellStyleinttot.setBorderBottom(THICK);
            cellStyleinttot.setBorderTop(THICK);
            cellStyleinttot.setBorderLeft(THIN);
            cellStyleinttot.setFont(font);

            Row row = getRow(sheet, lastindex + 3);
            Cell c1 = getCell(row, 1);
            c1.setCellStyle(cellStyleblanktot);
            c1.setCellValue("");
            c1 = getCell(row, 2);
            c1.setCellStyle(cellStyleblanktot);
            c1.setCellValue("TOTALE");
            c1 = getCell(row, 3);
            c1.setCellStyle(cellStyleblanktot);
            c1.setCellValue("");
            c1 = getCell(row, 4, NUMERIC);
            c1.setCellValue(getVOLUMEAC);
            c1.setCellStyle(cellStylenumtot);
            c1 = getCell(row, 5, NUMERIC);
            c1.setCellValue(getVOLUMECA);
            c1.setCellStyle(cellStylenumtot);

            c1 = getCell(row, 6, NUMERIC);
            c1.setCellValue(getVOLUMETC);
            c1.setCellStyle(cellStylenumtot);

            c1 = getCell(row, 7, NUMERIC);
            c1.setCellValue(getTRANSAC);
            c1.setCellStyle(cellStyleinttot);

            c1 = getCell(row, 8, NUMERIC);
            c1.setCellValue(getTRANSCA);
            c1.setCellStyle(cellStyleinttot);

            c1 = getCell(row, 9, NUMERIC);
            c1.setCellValue(getTRANSTC);
            c1.setCellStyle(cellStyleinttot);

            c1 = getCell(row, 10, NUMERIC);
            c1.setCellValue(getCOMMAC);
            c1.setCellStyle(cellStylenumtot);

            c1 = getCell(row, 11, NUMERIC);
            c1.setCellValue(getCOMMCA);
            c1.setCellStyle(cellStylenumtot);

            c1 = getCell(row, 12, NUMERIC);
            c1.setCellValue(getCOMMTC);
            c1.setCellStyle(cellStylenumtot);

            //NEW
            c1 = getCell(row, 13, NUMERIC);
            c1.setCellValue(getSPREADAC);
            c1.setCellStyle(cellStylenumtot);
            c1 = getCell(row, 14, NUMERIC);
            c1.setCellValue(getSPREADCA);
            c1.setCellStyle(cellStylenumtot);
            //NEW

            c1 = getCell(row, 15, NUMERIC);
            c1.setCellValue(getSPREADBR);
            c1.setCellStyle(cellStylenumtot);
            c1 = getCell(row, 16, NUMERIC);
            c1.setCellValue(getSPREADBA);
            c1.setCellStyle(cellStylenumtot);

            c1 = getCell(row, 17, NUMERIC);
            c1.setCellValue(getTOTTRANSACQ);
            c1.setCellStyle(cellStyleinttot);

            c1 = getCell(row, 18, NUMERIC);
            c1.setCellValue(getTOTVOLACQ);
            c1.setCellStyle(cellStylenumtot);
            c1 = getCell(row, 19, NUMERIC);
            c1.setCellValue(getTOTGMACQ);
            c1.setCellStyle(cellStylenumtot);
            c1 = getCell(row, 20, NUMERIC);
            c1.setCellValue(divisione_controllozero(getPERCACQ, output.size()));
            c1.setCellStyle(cellStylenumtot);
            c1 = getCell(row, 21, NUMERIC);
            c1.setCellValue(getVOLUMEVENDOFF);

            c1.setCellStyle(cellStylenumtot);
            c1 = getCell(row, 22, NUMERIC);
            c1.setCellValue(getVOLUMEONL);
            c1.setCellStyle(cellStylenumtot);
            c1 = getCell(row, 23, NUMERIC);
            c1.setCellValue(getVOLUMERIVA);
            c1.setCellStyle(cellStylenumtot);
            c1 = getCell(row, 24, NUMERIC);
            c1.setCellValue(getTRANSVENDOFF);
            c1.setCellStyle(cellStyleinttot);
            c1 = getCell(row, 25, NUMERIC);
            c1.setCellValue(getTRANSONL);
            c1.setCellStyle(cellStyleinttot);
            c1 = getCell(row, 26, NUMERIC);
            c1.setCellValue(getTRANSRIVA);
            c1.setCellStyle(cellStyleinttot);
            c1 = getCell(row, 27, NUMERIC);
            c1.setCellValue(getCOMMVENDOFF);
            c1.setCellStyle(cellStylenumtot);
            c1 = getCell(row, 28, NUMERIC);
            c1.setCellValue(getCOMMONL);
            c1.setCellStyle(cellStylenumtot);
            c1 = getCell(row, 29, NUMERIC);
            c1.setCellValue(getCOMMRIVA);
            c1.setCellStyle(cellStylenumtot);
            c1 = getCell(row, 30, NUMERIC);
            c1.setCellValue(getSPREADVEND);
            c1.setCellStyle(cellStylenumtot);
            c1 = getCell(row, 31, NUMERIC);
            c1.setCellValue(getTOTVOLVEN);
            c1.setCellStyle(cellStylenumtot);
            c1 = getCell(row, 32, NUMERIC);
            c1.setCellValue(getTOTTRANSVEN);
            c1.setCellStyle(cellStyleinttot);
            c1 = getCell(row, 33, NUMERIC);
            c1.setCellValue(getTOTGMVEN);
            c1.setCellStyle(cellStylenumtot);
            c1 = getCell(row, 34, NUMERIC);
            c1.setCellValue(divisione_controllozero(getPERCVEN, output.size()));
            c1.setCellStyle(cellStylenumtot);
            c1 = getCell(row, 35, NUMERIC);
            c1.setCellValue(getTOTVOL);
            c1.setCellStyle(cellStylenumtot);
            c1 = getCell(row, 36, NUMERIC);
            c1.setCellValue(getTOTTRANS);
            c1.setCellStyle(cellStyleinttot);
            c1 = getCell(row, 37, NUMERIC);
            c1.setCellValue(getTOTGM);
            c1.setCellStyle(cellStylenumtot);
            c1 = getCell(row, 38, NUMERIC);
            c1.setCellValue(divisione_controllozero(getPERCVEND, output.size()));
            c1.setCellStyle(cellStylenumtot);
            c1 = getCell(row, 39, NUMERIC);
            c1.setCellValue(getCOP);
            c1.setCellStyle(cellStylenumtot);
            c1 = getCell(row, 40, NUMERIC);
            c1.setCellValue(getTOBANKCOP);
            c1.setCellStyle(cellStylenumtot);
            c1 = getCell(row, 41, NUMERIC);
            c1.setCellValue(getFRBANKCOP);
            c1.setCellStyle(cellStylenumtot);
            c1 = getCell(row, 42, NUMERIC);
            c1.setCellValue(getTOBRCOP);
            c1.setCellStyle(cellStylenumtot);
            c1 = getCell(row, 43, NUMERIC);
            c1.setCellValue(getFRBRCOP);
            c1.setCellStyle(cellStylenumtot);
            c1 = getCell(row, 44, NUMERIC);
            c1.setCellValue(getOCERRCOP);
            c1.setCellStyle(cellStylenumtot);
            c1 = getCell(row, 45, NUMERIC);
            c1.setCellValue(getFX);
            c1.setCellStyle(cellStylenumtot);
            c1 = getCell(row, 46, NUMERIC);
            c1.setCellValue(getTOBANKFX);
            c1.setCellStyle(cellStylenumtot);
            c1 = getCell(row, 47, NUMERIC);
            c1.setCellValue(getFRBANKFX);
            c1.setCellStyle(cellStylenumtot);
            c1 = getCell(row, 48, NUMERIC);
            c1.setCellValue(getTOBRFX);
            c1.setCellStyle(cellStylenumtot);
            c1 = getCell(row, 49, NUMERIC);
            c1.setCellValue(getFRBRFX);
            c1.setCellStyle(cellStylenumtot);
            c1 = getCell(row, 50, NUMERIC);
            c1.setCellValue(getOCERRFX);
            c1.setCellStyle(cellStylenumtot);

            for (int r = 0; r < 51; r++) {
                sheet.autoSizeColumn(r);
            }

            try {
                File out = new File(pathout + generaId(75) + ".xlsx");
                try ( FileOutputStream fileOut = new FileOutputStream(out)) {
                    wb.write(fileOut);
                }
                is.close();
                wb.close();

                String base64 = new String(encodeBase64(readFileToByteArray(out)));
                out.delete();
                return base64;
            } catch (Exception ex) {
                insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
            }
        } catch (Exception ex) {
            insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
        }

        return null;
    }

}
