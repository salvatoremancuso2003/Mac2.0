/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rc.so.excel;

import rc.so.entity.Branch;
import static rc.so.util.Constant.formatdataCell;
import static rc.so.util.Engine.insertTR;
import static rc.so.util.Utility.fd;
import static rc.so.util.Utility.formatMysqltoDisplay;
import static rc.so.util.Utility.generaId;
import static rc.so.util.Utility.roundDoubleandFormat;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import static java.lang.Thread.currentThread;
import java.util.ArrayList;
import static org.apache.commons.codec.binary.Base64.encodeBase64;
import static org.apache.commons.io.FileUtils.readFileToByteArray;
import static org.apache.commons.lang3.StringUtils.capitalize;
import static org.apache.poi.hssf.usermodel.HSSFFont.FONT_ARIAL;
import static org.apache.poi.ss.usermodel.BorderStyle.DOUBLE;
import static org.apache.poi.ss.usermodel.BorderStyle.THIN;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import static org.apache.poi.ss.usermodel.CellType.NUMERIC;
import static org.apache.poi.ss.usermodel.FillPatternType.SOLID_FOREGROUND;
import static org.apache.poi.ss.usermodel.IndexedColors.YELLOW;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFDataFormat;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author rcosco
 */
public class BudgetRep {

    /**
     *
     * @param pathout
     * @param primatabella
     * @param secondatabella
     * @param terzatabella
     * @param li
     * @param fil1
     * @param giornidacontrollare
     * @param giornidacontrollarestring
     * @param gruppidascrivere
     * @param list_group
     * @return
     */
    public static String create(String pathout, ArrayList<String> primatabella, ArrayList<String> secondatabella,
            ArrayList<String> terzatabella, ArrayList<BudgetTill> li, ArrayList<Branch> fil1, ArrayList<String> giornidacontrollare,
            ArrayList<String> giornidacontrollarestring, ArrayList<String> gruppidascrivere, ArrayList<String[]> list_group) {
        try {

            File out;
            try (XSSFWorkbook wb = new XSSFWorkbook()) {
                Sheet sheet = wb.createSheet("BUDGET DI CASSA");
                XSSFFont font = (XSSFFont) wb.createFont();
                font.setFontName(FONT_ARIAL);
                font.setFontHeightInPoints((short) 12);
                font.setBold(true);
                CellStyle style = wb.createCellStyle(); //Create new style
                style.setWrapText(true); //Set wordwrap
                style.setFont(font);
                style.setBorderBottom(DOUBLE);
                style.setBorderTop(DOUBLE);
                style.setFillForegroundColor(YELLOW.getIndex());
                style.setFillPattern(SOLID_FOREGROUND);
                XSSFCellStyle cellStylenum = wb.createCellStyle();
                XSSFDataFormat hssfDataFormat = wb.createDataFormat();
                cellStylenum.setDataFormat(hssfDataFormat.getFormat(formatdataCell));
                cellStylenum.setBorderBottom(THIN);
                CellStyle styleNORM = wb.createCellStyle();
                styleNORM.setBorderBottom(THIN);
                int riga = 0;
                Row row = sheet.createRow(riga);
                for (int i = 0; i < primatabella.size(); i++) {
                    Cell cell = row.createCell(i);
                    String complete = primatabella.get(i);
                    XSSFRichTextString richString = new XSSFRichTextString(complete);
                    cell.setCellValue(richString);
                    cell.setCellStyle(style);
                }   for (int i = 0; i < giornidacontrollare.size(); i++) {
                    riga++;
                    row = sheet.createRow(riga);
                    String giorno1 = giornidacontrollare.get(i);
                    Cell cell0 = row.createCell(0);
                    cell0.setCellValue(giorno1);
                    cell0.setCellStyle(styleNORM);
                    Cell cell1 = row.createCell(1);
                    cell1.setCellValue(capitalize(giornidacontrollarestring.get(i)));
                    cell1.setCellStyle(styleNORM);
                    int sta = 2;
                    for (int y = 0; y < gruppidascrivere.size(); y++) {
                        String gr = gruppidascrivere.get(y);
                        double totgr = 0.0;
                        double totgr_pv = 0.0;
                        for (int x = 0; x < fil1.size(); x++) {
                            Branch b = fil1.get(x);
                            if (b.getBrgr_01().equals(gr)) {
                                for (int r = 0; r < li.size(); r++) {
                                    BudgetTill lins = li.get(r);
                                    if (lins.getFiliale().equals(b.getCod()) && lins.getGiorno().equals(giorno1)) {
                                        cell1 = row.createCell(sta, NUMERIC);
                                        cell1.setCellValue(fd(lins.getCop()));
                                        cell1.setCellStyle(cellStylenum);
                                        totgr += fd(lins.getCop());
                                        totgr_pv += fd(lins.getCop_ap());
                                        sta++;
                                    }
                                }
                            }
                        }
                        cell1 = row.createCell(sta, NUMERIC);
                        cell1.setCellValue(totgr);
                        cell1.setCellStyle(cellStylenum);
                        sta++;
                        cell1 = row.createCell(sta, NUMERIC);
                        cell1.setCellValue(totgr_pv);
                        cell1.setCellStyle(cellStylenum);
                        sta++;
                        cell1 = row.createCell(sta, NUMERIC);
                        cell1.setCellValue(totgr - totgr_pv);
                        cell1.setCellStyle(cellStylenum);
                        sta++;
                    }
                }
                riga++;
                riga++;
                row = sheet.createRow(riga);
                for (int i = 0; i < secondatabella.size(); i++) {
                    Cell cell = row.createCell(i);
                    cell.setCellValue(secondatabella.get(i));
                    cell.setCellStyle(style);
                }   for (int i = 0; i < giornidacontrollare.size(); i++) {
                    riga++;
                    row = sheet.createRow(riga);
                    String giorno1 = giornidacontrollare.get(i);
                    Cell cell0 = row.createCell(0);
                    cell0.setCellValue(giorno1);
                    cell0.setCellStyle(styleNORM);
                    Cell cell1 = row.createCell(1);
                    cell1.setCellValue(capitalize(giornidacontrollarestring.get(i)));
                    cell1.setCellStyle(styleNORM);
                    int sta = 2;
                    for (int y = 0; y < gruppidascrivere.size(); y++) {
                        String gr = gruppidascrivere.get(y);
                        double totgr = 0.0;
                        double totgr_pv = 0.0;
                        for (int x = 0; x < fil1.size(); x++) {
                            Branch b = fil1.get(x);
                            if (b.getBrgr_01().equals(gr)) {
                                for (int r = 0; r < li.size(); r++) {
                                    BudgetTill lins = li.get(r);
                                    if (lins.getFiliale().equals(b.getCod()) && lins.getGiorno().equals(giorno1)) {
                                        cell1 = row.createCell(sta, NUMERIC);
                                        cell1.setCellValue(fd(lins.getFx()));
                                        cell1.setCellStyle(cellStylenum);
                                        totgr += fd(lins.getFx());
                                        totgr_pv += fd(lins.getFx_ap());
                                        sta++;
                                    }
                                }
                            }
                        }
                        cell1 = row.createCell(sta, NUMERIC);
                        cell1.setCellValue(totgr);
                        cell1.setCellStyle(cellStylenum);
                        sta++;
                        cell1 = row.createCell(sta, NUMERIC);
                        cell1.setCellValue(totgr_pv);
                        cell1.setCellStyle(cellStylenum);
                        sta++;
                        cell1 = row.createCell(sta, NUMERIC);
                        cell1.setCellValue(totgr - totgr_pv);
                        cell1.setCellStyle(cellStylenum);
                        sta++;
                    }
                }
                riga++;
                riga++;
                row = sheet.createRow(riga);
                for (int i = 0; i < terzatabella.size(); i++) {
                    Cell cell = row.createCell(i);
                    cell.setCellValue(terzatabella.get(i));
                    cell.setCellStyle(style);
                }   for (int i = 0; i < giornidacontrollare.size(); i++) {
                    riga++;
                    row = sheet.createRow(riga);
                    String giorno1 = giornidacontrollare.get(i);
                    Cell cell0 = row.createCell(0);
                    cell0.setCellValue(giorno1);
                    cell0.setCellStyle(styleNORM);
                    Cell cell1 = row.createCell(1);
                    cell1.setCellValue(capitalize(giornidacontrollarestring.get(i)));
                    cell1.setCellStyle(styleNORM);
                    int sta = 2;
                    for (int y = 0; y < gruppidascrivere.size(); y++) {
                        String gr = gruppidascrivere.get(y);
                        double totgr = 0.0;
                        double totgr_pv = 0.0;
                        double totbdg = 0.0;
                        for (int x = 0; x < fil1.size(); x++) {
                            Branch b = fil1.get(x);
                            if (b.getBrgr_01().equals(gr)) {
                                for (int r = 0; r < li.size(); r++) {
                                    BudgetTill lins = li.get(r);
                                    if (lins.getFiliale().equals(b.getCod()) && lins.getGiorno().equals(giorno1)) {
                                        cell1 = row.createCell(sta, NUMERIC);
                                        cell1.setCellValue(fd(lins.getTot()));
                                        cell1.setCellStyle(cellStylenum);
                                        totgr += fd(lins.getTot());
                                        totgr_pv += fd(lins.getTot_ap());
                                        totbdg += fd(lins.getBudget());
                                        sta++;
                                    }
                                }
                            }
                        }
                        cell1 = row.createCell(sta);
                        cell1.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(totgr, 2)));
                        cell1.setCellStyle(styleNORM);
                        sta++;
                        cell1 = row.createCell(sta);
                        cell1.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(totgr_pv, 2)));
                        cell1.setCellStyle(styleNORM);
                        sta++;
                        cell1 = row.createCell(sta);
                        cell1.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(totgr - totgr_pv, 2)));
                        cell1.setCellStyle(styleNORM);
                        sta++;
                        cell1 = row.createCell(sta);
                        cell1.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(totbdg, 2)));
                        cell1.setCellStyle(styleNORM);
                        sta++;
                        cell1 = row.createCell(sta);
                        cell1.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(totgr - totbdg, 2)));
                        cell1.setCellStyle(styleNORM);
                        sta++;
                    }
                }
                riga++;
                riga++;
                for (int i = 0; i < terzatabella.size(); i++) {
                    sheet.autoSizeColumn(i);
                }   out = new File(pathout + generaId(75) + ".xlsx");
                try(FileOutputStream fileOut = new FileOutputStream(out)){
                wb.write(fileOut);
                fileOut.close();
                }
            }
            String base64 = new String(encodeBase64(readFileToByteArray(out)));
            out.delete();
            return base64;
        } catch (IOException ex) {
            insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
        }
        return null;
    }
}
