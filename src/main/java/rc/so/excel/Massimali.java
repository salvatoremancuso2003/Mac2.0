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
import static rc.so.util.Utility.generaId;
import static rc.so.util.Utility.getCell;
import static rc.so.util.Utility.getRow;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import static java.lang.Thread.currentThread;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;
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
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFDataFormat;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author rcosco
 */
public class Massimali {

    /**
     *
     * @param pathout
     * @param primatabella
     * @param secondatabella
     * @param terzatabella
     * @param quartatabella
     * @param li
     * @param fil1
     * @param giornidacontrollare
     * @param giornidacontrollarestring
     * @return
     */
    public static String create(String pathout, ArrayList<String> primatabella, ArrayList<String> secondatabella,
            ArrayList<String> terzatabella, ArrayList<String> quartatabella, ArrayList<LimitInsur> li, ArrayList<Branch> fil1,
            ArrayList<String> giornidacontrollare,
            ArrayList<String> giornidacontrollarestring) {
        try {

            File out;
            try (XSSFWorkbook wb = new XSSFWorkbook()) {
                Sheet sheet = wb.createSheet("CONTROLLO MASSIMALI");
                XSSFFont font = (XSSFFont) wb.createFont();
                font.setFontName(FONT_ARIAL);
                font.setFontHeightInPoints((short) 12);
                font.setBold(true);
                XSSFDataFormat hssfDataFormat = wb.createDataFormat();
                CellStyle style = wb.createCellStyle(); //Create new style
                //            style.setWrapText(true); //Set wordwrap
                style.setFont(font);
                style.setBorderBottom(DOUBLE);
                style.setBorderTop(DOUBLE);
                style.setFillForegroundColor(YELLOW.getIndex());
                style.setFillPattern(SOLID_FOREGROUND);
                CellStyle styleW = wb.createCellStyle(); //Create new style
                styleW.setWrapText(true); //Set wordwrap
                styleW.setFont(font);
                styleW.setBorderBottom(DOUBLE);
                styleW.setBorderTop(DOUBLE);
                styleW.setFillForegroundColor(YELLOW.getIndex());
                styleW.setFillPattern(SOLID_FOREGROUND);
                CellStyle styleN = wb.createCellStyle(); //Create new style
                //            style.setWrapText(true); //Set wordwrap
                styleN.setFont(font);
                styleN.setBorderBottom(DOUBLE);
                styleN.setBorderTop(DOUBLE);
                styleN.setFillForegroundColor(YELLOW.getIndex());
                styleN.setFillPattern(SOLID_FOREGROUND);
                styleN.setDataFormat(hssfDataFormat.getFormat(formatdataCell));
                XSSFCellStyle cellStylenum = wb.createCellStyle();
                cellStylenum.setDataFormat(hssfDataFormat.getFormat(formatdataCell));
                cellStylenum.setBorderBottom(THIN);
                CellStyle styleNORM = wb.createCellStyle();
                styleNORM.setBorderBottom(THIN);
                int riga = 0;
                Row row;
                AtomicInteger ind = new AtomicInteger(riga);
                AtomicInteger first = new AtomicInteger(0);
                AtomicInteger ind_x = new AtomicInteger(0);
                primatabella.forEach(string -> {
                    Row r1 = getRow(sheet, ind.get());
                    Cell cell = getCell(r1, ind_x.get());
                    XSSFRichTextString richString = new XSSFRichTextString(string);
                    if (ind.get() == 0) {
                        cell.setCellValue(richString);
                        cell.setCellStyle(style);
                        if (first.get() == 0) {
                            ind_x.addAndGet(2);
                            sheet.addMergedRegion(new CellRangeAddress(first.get(), first.get() + 2, 0, 1));
                        } else {
                            ind.addAndGet(1);
                        }
                        
                        first.addAndGet(1);
                    } else if (ind.get() % 2 == 0) {
                        cell = getCell(r1, ind_x.get(), NUMERIC);
                        ind.set(0);
                        ind_x.addAndGet(1);
                        cell.setCellValue(fd(string));
                        cell.setCellStyle(styleN);
                    } else {
                        ind.addAndGet(1);
                        cell.setCellValue(richString);
                        cell.setCellStyle(style);
                    }
                });
                riga = 2;
//            for (int i = 0; i < primatabella.size(); i++) {
//                Cell cell = row.createCell(i);
//
//                String complete = primatabella.get(i);
//
//                XSSFRichTextString richString = new XSSFRichTextString(complete);
//                cell.setCellValue(richString);
//                cell.setCellStyle(style);
//            }
                for (int i = 0; i < giornidacontrollare.size(); i++) {
                    riga++;
                    row = sheet.createRow(riga);
                    String giorno1 = giornidacontrollare.get(i);
                    Cell cell0 = row.createCell(0);
                    cell0.setCellValue(giorno1);
                    cell0.setCellStyle(styleNORM);
                    Cell cell1 = row.createCell(1);
                    cell1.setCellValue(capitalize(giornidacontrollarestring.get(i)));
                    cell1.setCellStyle(styleNORM);
                    for (int x = 0; x < fil1.size(); x++) {
                        Branch b = fil1.get(x);
                        for (int r = 0; r < li.size(); r++) {
                            LimitInsur lins = li.get(r);
                            if (lins.getFiliale().equals(b.getCod()) && lins.getGiorno().equals(giorno1)) {
                                cell1 = row.createCell(2 + x, NUMERIC);
                                cell1.setCellValue(fd(lins.getCop()));
                                cell1.setCellStyle(cellStylenum);
                            }
                        }
                    }
                }
                riga = riga + 34 - giornidacontrollare.size();
                AtomicInteger start = new AtomicInteger(riga);
                first.set(0);
                ind.set(start.get());
                ind_x.set(0);
                secondatabella.forEach(string -> {
                    Row r1 = getRow(sheet, ind.get());
                    Cell cell = getCell(r1, ind_x.get());
                    XSSFRichTextString richString = new XSSFRichTextString(string);
                    if (ind.get() == start.get()) {
                        cell.setCellValue(richString);
                        cell.setCellStyle(style);
                        if (first.get() == 0) {
                            ind_x.addAndGet(2);
                            sheet.addMergedRegion(new CellRangeAddress(start.get(), start.get() + 2, 0, 1));
                        } else {
                            ind.addAndGet(1);
                        }
                        first.addAndGet(1);
                    } else if (ind.get() % 2 == 0) {
                        cell = getCell(r1, ind_x.get(), NUMERIC);
                        ind.set(start.get());
                        ind_x.addAndGet(1);
                        cell.setCellValue(fd(string));
                        cell.setCellStyle(styleN);
                    } else {
                        ind.addAndGet(1);
                        cell.setCellValue(richString);
                        cell.setCellStyle(style);
                    }
                });
                riga = 38;
//            row = sheet.createRow(riga);
//            for (int i = 0; i < secondatabella.size(); i++) {
//                Cell cell = row.createCell(i);
//                cell.setCellValue(secondatabella.get(i));
//                cell.setCellStyle(style);
//            }
                for (int i = 0; i < giornidacontrollare.size(); i++) {
                    riga++;
                    row = sheet.createRow(riga);
                    String giorno1 = giornidacontrollare.get(i);
                    Cell cell0 = row.createCell(0);
                    cell0.setCellValue(giorno1);
                    cell0.setCellStyle(styleNORM);
                    Cell cell1 = row.createCell(1);
                    cell1.setCellValue(capitalize(giornidacontrollarestring.get(i)));
                    cell1.setCellStyle(styleNORM);
                    for (int x = 0; x < fil1.size(); x++) {
                        Branch b = fil1.get(x);
                        for (int r = 0; r < li.size(); r++) {
                            LimitInsur lins = li.get(r);
                            if (lins.getFiliale().equals(b.getCod()) && lins.getGiorno().equals(giorno1)) {
                                cell1 = row.createCell(2 + x, NUMERIC);
                                cell1.setCellValue(fd(lins.getFx()));
                                cell1.setCellStyle(cellStylenum);
                            }
                        }
                    }
                }
                riga = riga + 34 - giornidacontrollare.size();
                start.set(riga);
                first.set(0);
                ind.set(start.get());
                ind_x.set(0);
                terzatabella.forEach(string -> {
                    Row r1 = getRow(sheet, ind.get());
                    Cell cell = getCell(r1, ind_x.get());
                    XSSFRichTextString richString = new XSSFRichTextString(string);
                    if (ind.get() == start.get()) {
                        cell.setCellValue(richString);
                        cell.setCellStyle(style);
                        if (first.get() == 0) {
                            ind_x.addAndGet(2);
                            sheet.addMergedRegion(new CellRangeAddress(start.get(), start.get() + 2, 0, 1));
                        } else {
                            ind.addAndGet(1);
                        }
                        first.addAndGet(1);
                    } else if (ind.get() % 2 == 0) {
                        cell = getCell(r1, ind_x.get(), NUMERIC);
                        ind.set(start.get());
                        ind_x.addAndGet(1);
                        cell.setCellValue(fd(string));
                        cell.setCellStyle(styleN);
                    } else {
                        ind.addAndGet(1);
                        cell.setCellValue(richString);
                        cell.setCellStyle(style);
                    }
                });
                riga = 74;
                for (int i = 0; i < giornidacontrollare.size(); i++) {
                    riga++;
                    row = sheet.createRow(riga);
                    String giorno1 = giornidacontrollare.get(i);
                    Cell cell0 = row.createCell(0);
                    cell0.setCellValue(giorno1);
                    cell0.setCellStyle(styleNORM);
                    Cell cell1 = row.createCell(1);
                    cell1.setCellValue(capitalize(giornidacontrollarestring.get(i)));
                    cell1.setCellStyle(styleNORM);
                    for (int x = 0; x < fil1.size(); x++) {
                        Branch b = fil1.get(x);
                        for (int r = 0; r < li.size(); r++) {
                            LimitInsur lins = li.get(r);
                            if (lins.getFiliale().equals(b.getCod()) && lins.getGiorno().equals(giorno1)) {
                                cell1 = row.createCell(2 + x, NUMERIC);
                                cell1.setCellValue(fd(lins.getTot()));
                                cell1.setCellStyle(cellStylenum);
                            }
                        }
                    }
                }
                riga = riga + 34 - giornidacontrollare.size();
                start.set(riga);
                first.set(0);
                ind.set(start.get());
                ind_x.set(0);
                quartatabella.forEach(string -> {
                    Row r1 = getRow(sheet, ind.get());
                    Cell cell = getCell(r1, ind_x.get());
                    XSSFRichTextString richString = new XSSFRichTextString(string);
                    if (ind.get() == start.get()) {
                        cell.setCellValue(richString);
                        cell.setCellStyle(styleW);
                        if (first.get() == 0) {
                            ind_x.addAndGet(2);
                            sheet.addMergedRegion(new CellRangeAddress(start.get(), start.get() + 2, 0, 1));
                        } else {
                            ind.addAndGet(1);
                        }
                        first.addAndGet(1);
                    } else if (ind.get() % 2 == 0) {
                        cell = getCell(r1, ind_x.get(), NUMERIC);
                        ind.set(start.get());
                        ind_x.addAndGet(1);
                        cell.setCellValue(fd(string));
                        cell.setCellStyle(styleN);
                    } else {
                        ind.addAndGet(1);
                        cell.setCellValue(richString);
                        cell.setCellStyle(style);
                    }
                });
                riga = 110;
                for (int i = 0; i < giornidacontrollare.size(); i++) {
                    riga++;
                    row = sheet.createRow(riga);
                    String giorno1 = giornidacontrollare.get(i);
                    Cell cell0 = row.createCell(0);
                    cell0.setCellValue(giorno1);
                    cell0.setCellStyle(styleNORM);
                    Cell cell1 = row.createCell(1);
                    cell1.setCellValue(capitalize(giornidacontrollarestring.get(i)));
                    cell1.setCellStyle(styleNORM);
                    for (int x = 0; x < fil1.size(); x++) {
                        Branch b = fil1.get(x);
                        for (int r = 0; r < li.size(); r++) {
                            LimitInsur lins = li.get(r);
                            if (lins.getFiliale().equals(b.getCod()) && lins.getGiorno().equals(giorno1)) {
                                cell1 = row.createCell(2 + x, NUMERIC);
                                cell1.setCellValue(fd(lins.getDelta()));
                                cell1.setCellStyle(cellStylenum);
                            }
                        }
                    }
                }
                for (int i = 0; i < quartatabella.size(); i++) {
                    sheet.autoSizeColumn(i);
                }   out = new File(pathout + generaId(75) + ".xlsx");
                 try(OutputStream fileOut = new FileOutputStream(out)){
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
