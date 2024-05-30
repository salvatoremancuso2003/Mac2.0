/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rc.so.util;

import rc.so.db.Db_Master;
import rc.so.entity.Agency;
import rc.so.entity.BlacklistM;
import rc.so.entity.Booking;
import rc.so.entity.Branch;
import rc.so.entity.Ch_transaction;
import static rc.so.entity.Ch_transaction.formatType;
import rc.so.entity.Ch_transaction_doc;
import rc.so.entity.Ch_transaction_value;
import rc.so.entity.Client;
import rc.so.entity.Company;
import rc.so.entity.Currency;
import rc.so.entity.CustomerKind;
import rc.so.entity.ET_change;
import rc.so.entity.Figures;
import rc.so.entity.IT_change;
import rc.so.entity.NC_category;
import rc.so.entity.NC_causal;
import rc.so.entity.NC_transaction;
import rc.so.entity.Newsletters;
import rc.so.entity.Openclose;
import rc.so.entity.Rate_history;
import rc.so.entity.Taglio;
import rc.so.entity.Till;
import rc.so.entity.Users;
import static rc.so.entity.Users.formatValidity;
import rc.so.entity.VATcode;
import static rc.so.util.Constant.is_IT;
import static rc.so.util.Constant.patternnormdate;
import static rc.so.util.Constant.patternnormdate_filter;
import static rc.so.util.Constant.patternsql;
import static rc.so.util.Constant.patternsqldate;
import static rc.so.util.Engine.category_nations;
import static rc.so.util.Engine.country;
import static rc.so.util.Engine.formatALCurrency;
import static rc.so.util.Engine.formatALNC_category;
import static rc.so.util.Engine.formatALNC_causal;
import static rc.so.util.Engine.formatALNC_causal_ncde;
import static rc.so.util.Engine.formatBankBranch;
import static rc.so.util.Engine.formatCountry_cru;
import static rc.so.util.Engine.formatSex;
import static rc.so.util.Engine.formatStatus_general_cru;
import static rc.so.util.Engine.getConf;
import static rc.so.util.Engine.getFil;
import static rc.so.util.Engine.getNC_category;
import static rc.so.util.Engine.getStringCurrency;
import static rc.so.util.Engine.getStringFigures;
import static rc.so.util.Engine.get_Branch;
import static rc.so.util.Engine.get_ET_change_value;
import static rc.so.util.Engine.get_ET_nochange_value;
import static rc.so.util.Engine.get_user;
import static rc.so.util.Engine.insertTR;
import static rc.so.util.Engine.isAvaliable;
import static rc.so.util.Engine.isPast;
import static rc.so.util.Engine.isToday;
import static rc.so.util.Engine.isTomorrow;
import static rc.so.util.Engine.list_all_kind;
import static rc.so.util.Engine.list_type_customer;
import static rc.so.util.Engine.list_type_kind;
import static rc.so.util.Engine.list_type_till;
import static rc.so.util.Engine.nc_kind;
import static rc.so.util.Engine.query_LOY_transaction;
import static rc.so.util.Engine.query_transaction_ch;
import static rc.so.util.Engine.query_transaction_value;
import static rc.so.util.Engine.selectgroupbranch;
import static rc.so.util.Utility.correggi;
import static rc.so.util.Utility.fd;
import static rc.so.util.Utility.formatAL;
import static rc.so.util.Utility.formatALN;
import static rc.so.util.Utility.formatDoubleforMysql;
import static rc.so.util.Utility.formatMysqltoDisplay;
import static rc.so.util.Utility.formatStringtoStringDate;
import static rc.so.util.Utility.formatStringtoStringDate_null;
import static rc.so.util.Utility.parseDoubleR;
import static rc.so.util.Utility.roundDoubleandFormat;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import static java.lang.Thread.currentThread;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.StringTokenizer;
import static org.apache.commons.codec.binary.Base64.decodeBase64;
import static org.apache.commons.codec.binary.Base64.encodeBase64;
import static org.apache.commons.io.FileUtils.readFileToByteArray;
import static org.apache.poi.hssf.usermodel.HSSFFont.FONT_ARIAL;
import static org.apache.poi.ss.usermodel.BorderStyle.THIN;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import static org.apache.poi.ss.usermodel.CellType.NUMERIC;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Drawing;
import static org.apache.poi.ss.usermodel.FillPatternType.SOLID_FOREGROUND;
import org.apache.poi.ss.usermodel.Font;
import static org.apache.poi.ss.usermodel.HorizontalAlignment.CENTER;
import org.apache.poi.ss.usermodel.Picture;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import static org.apache.poi.ss.usermodel.Workbook.PICTURE_TYPE_PNG;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.IndexedColorMap;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFDataFormat;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.joda.time.DateTime;
import rc.so.entity.Marketing;
import static rc.so.util.Utility.parseStringDate;

/**
 *
 * @author rcosco
 */
public class Excel {

    private static final String filiale = getFil()[0];

    private static String getValueCell(Cell c1) {
        switch (c1.getCellType()) {
            case BOOLEAN:
                return (c1.getBooleanCellValue() + "").trim();
            case NUMERIC:
                return (c1.getNumericCellValue() + "").trim();
            case STRING:
                return c1.getStringCellValue().trim() + "";
        }
        return "";
    }

    /**
     *
     * @param f
     * @return
     */
    public static ArrayList<String[]> getReadEXCELCURRENCY(File f) {
        try {
            ArrayList<String[]> al;
            try (FileInputStream file = new FileInputStream(f)) {
                XSSFWorkbook workbook = new XSSFWorkbook(file);
                XSSFSheet sheet = workbook.getSheetAt(0);
                Iterator<Row> rowIterator = sheet.iterator();
                al = new ArrayList<>();
                while (rowIterator.hasNext()) {
                    Row row = rowIterator.next();
                    Iterator<Cell> cellIterator = row.cellIterator();
                    String v[] = new String[2];
                    while (cellIterator.hasNext()) {
                        Cell cell = cellIterator.next();
                        if (cell.getColumnIndex() == 0) {
                            v[0] = getValueCell(cell);
                        }
                        if (cell.getColumnIndex() == 1) {
                            v[1] = getValueCell(cell);
                        }
                    }
                    if (!v[0].trim().equals("")) {
                        al.add(v);
                    }
                }
                workbook.close();
            }
            return al;
        } catch (Exception ex) {
            insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
        }
        return null;
    }

    /**
     *
     * @param pathout
     * @param listcod
     * @param date
     * @param user
     * @return
     */
    public String print_code_unlock(String pathout, String listcod, String date, String user) {
        try {
            String outputfile = new DateTime().toString("yyMMddhhmmssSSS") + "_code_unlock.xlsx";
            File out = new File(pathout + outputfile);
            try (Workbook wb = new XSSFWorkbook()) {
                Sheet sheet = wb.createSheet("code_unlock");
                byte[] bytes = decodeBase64(getConf("path.logocl"));
                int pictureIdx = wb.addPicture(bytes, PICTURE_TYPE_PNG);
                CreationHelper helper = wb.getCreationHelper();
                Drawing drawing = sheet.createDrawingPatriarch();
                ClientAnchor anchor = helper.createClientAnchor();
                anchor.setCol1(1); //Column B
                anchor.setRow1(1); //Row 3
                anchor.setCol2(4); //Column C
                anchor.setRow2(5); //Row 4
                Picture pict = drawing.createPicture(anchor, pictureIdx);
                Row row = sheet.createRow((short) 5);
                Cell cell = row.createCell(1);
                cell.setCellValue("Date: ");
                Cell cell1 = row.createCell(4);
                cell1.setCellValue(date);
                Row row2 = sheet.createRow((short) 8);
                Cell cell5 = row.createCell(1);
                cell5.setCellValue("LIST UNLOCK CODES");
                int cnttoken = 10;
                if (listcod != null) {
                    StringTokenizer tok = new StringTokenizer(listcod, ";");
                    if (tok.countTokens() > 0) {
                        while (tok.hasMoreElements()) {
                            Row row1 = sheet.createRow((short) cnttoken);
                            Cell cel2 = row1.createCell(1);
                            cel2.setCellValue(tok.nextToken());
                            cnttoken++;
                        }
                    }
                }
                Row row4 = sheet.createRow((short) cnttoken + 3);
                Cell cell6 = row4.createCell(1);
                cell6.setCellValue("Assigned To: ");
                Cell cell7 = row4.createCell(4);
                cell7.setCellValue(user);
                sheet.autoSizeColumn(1);
                sheet.autoSizeColumn(4);
                try(FileOutputStream fileOut = new FileOutputStream(outputfile)){
                wb.write(fileOut);
                fileOut.close();
            }
        }

            String base64 = new String(encodeBase64(readFileToByteArray(out)));
            out.delete();
            return base64;
        } catch (Exception ex) {
            insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
        }
        return null;

    }

    /**
     *
     * @param outputfile
     * @param result
     * @return
     */
    public static String excel_users_list(File outputfile, ArrayList<Users> result) {
        try {
            Db_Master db1 = new Db_Master(true);

            ArrayList<String[]> out1 = db1.get_list_codice_ATL();

            db1.closeDB();

            try ( //File out = new File(pathout + outputfile);
                    Workbook wb = new XSSFWorkbook()) {
                Sheet sheet = wb.createSheet("users_list");
                XSSFCellStyle style = (XSSFCellStyle) wb.createCellStyle();
                style.setBorderBottom(THIN);
                style.setBorderTop(THIN);
                style.setBorderRight(THIN);
                style.setBorderLeft(THIN);
                XSSFCellStyle style1 = (XSSFCellStyle) wb.createCellStyle();
                style1.setBorderBottom(THIN);
                style1.setBorderTop(THIN);
                style1.setBorderRight(THIN);
                style1.setBorderLeft(THIN);
                style1.setAlignment(CENTER);
                XSSFFont font = (XSSFFont) wb.createFont();
                font.setFontName(FONT_ARIAL);
                font.setFontHeightInPoints((short) 12);
                font.setBold(true);
                //  font.setColor(HSSFColor.BLUE.index);
                style1.setFont(font);
                XSSFCellStyle style2 = (XSSFCellStyle) wb.createCellStyle();
                style2.setAlignment(CENTER);
                XSSFFont font2 = (XSSFFont) wb.createFont();
                font2.setFontName(FONT_ARIAL);
                font2.setFontHeightInPoints((short) 14);
                font2.setBold(true);
                style2.setFont(font2);
                Row row0 = sheet.createRow(0);
                Cell cell0 = row0.createCell(1);
                cell0.setCellStyle(style2);
                sheet.addMergedRegion(new CellRangeAddress(0, 0, 1, 9));
                cell0.setCellValue("Users List - Branch: " + filiale);
                Row row = sheet.createRow(1);
                Cell cell = row.createCell(1);
                cell.setCellValue("Code");
                cell.setCellStyle(style1);
                Cell cell2 = row.createCell(2);
                cell2.setCellValue("Username");
                cell2.setCellStyle(style1);
                Cell cell3 = row.createCell(3);
                cell3.setCellValue("Surname");
                cell3.setCellStyle(style1);
                Cell cell4 = row.createCell(4);
                cell4.setCellValue("Name");
                cell4.setCellStyle(style1);
                Cell cell5 = row.createCell(5);
                cell5.setCellValue("Type");
                cell5.setCellStyle(style1);
                Cell cell6 = row.createCell(6);
                cell6.setCellValue("Status");
                cell6.setCellStyle(style1);
                Cell cell7 = row.createCell(7);
                cell7.setCellValue("Validity Password (Day)");
                cell7.setCellStyle(style1);
                Cell cell8 = row.createCell(8);
                cell8.setCellValue("Email Address");
                cell8.setCellStyle(style1);
                Cell cell9 = row.createCell(9);
                cell9.setCellValue("Accounting Code");
                cell9.setCellStyle(style1);
                if (is_IT) {
                    Cell cell10 = row.createCell(10);
                    cell10.setCellValue("Code Atlante");
                    cell10.setCellStyle(style1);
                }
                for (int i = 0; i < result.size(); i++) {
                    Row row2 = sheet.createRow(i + 3);
                    Users res = (Users) result.get(i);
                    //                String val = res.getValidita();
//                if (val.equalsIgnoreCase("0")) {
//                    val = "Unlimited";
//                }else{
//                    
//                }
                    Cell cell1 = row2.createCell(1);
                    cell1.setCellValue(res.getCod());
                    cell1.setCellStyle(style);
                    Cell cell22 = row2.createCell(2);
                    cell22.setCellValue(res.getUsername());
                    cell22.setCellStyle(style);
                    Cell cell33 = row2.createCell(3);
                    cell33.setCellValue(res.getDe_cognome());
                    cell33.setCellStyle(style);
                    Cell cell44 = row2.createCell(4);
                    cell44.setCellValue(res.getDe_nome());
                    cell44.setCellStyle(style);
                    Cell cell55 = row2.createCell(5);
                    cell55.setCellValue((res.formatTypeuserExcel(res.getFg_tipo())));
                    cell55.setCellStyle(style);
                    Cell cell66 = row2.createCell(6);
                    cell66.setCellValue(res.formatStatususerExcel(res.getFg_stato()));
                    cell66.setCellStyle(style);
                    Cell cell77 = row2.createCell(7);
                    cell77.setCellValue(formatValidity(res.getValidita()));
                    cell77.setCellStyle(style);
                    Cell cell88 = row2.createCell(8);
                    cell88.setCellValue(res.getEmail());
                    cell88.setCellStyle(style);
                    Cell cell99 = row2.createCell(9);
                    cell99.setCellValue(res.getConto());
                    cell99.setCellStyle(style);
                    if (is_IT) {
                        Cell cell990 = row2.createCell(10);
                        cell990.setCellValue(formatAL(res.getCod(), out1, 1));
                        cell990.setCellStyle(style);
                    }
                }
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
                try(FileOutputStream fileOut = new FileOutputStream(outputfile)){
                    wb.write(fileOut);
                    fileOut.close();
                }
            }

            String base64 = new String(encodeBase64(readFileToByteArray(outputfile)));
            outputfile.delete();
            return base64;
        } catch (IOException ex) {
            insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
        }
        return null;

    }

    /**
     *
     * @param outputfile
     * @param result
     * @param filiale
     * @return
     */
    public static String excel_figures_list(File outputfile, ArrayList<Figures> result, String filiale) {
        try {

            Db_Master db = new Db_Master();
            ArrayList<String[]> elencokind = db.list_selectkind();
            db.closeDB();

            try ( //File out = new File(pathout + outputfile);
                    Workbook wb = new XSSFWorkbook()) {
                Sheet sheet = wb.createSheet("figures_list");
                XSSFCellStyle style = (XSSFCellStyle) wb.createCellStyle();
                style.setBorderBottom(THIN);
                style.setBorderTop(THIN);
                style.setBorderRight(THIN);
                style.setBorderLeft(THIN);
                XSSFCellStyle style1 = (XSSFCellStyle) wb.createCellStyle();
                style1.setBorderBottom(THIN);
                style1.setBorderTop(THIN);
                style1.setBorderRight(THIN);
                style1.setBorderLeft(THIN);
                style1.setAlignment(CENTER);
                XSSFFont font = (XSSFFont) wb.createFont();
                font.setFontName(FONT_ARIAL);
                font.setFontHeightInPoints((short) 12);
                font.setBold(true);
                //  font.setColor(HSSFColor.BLUE.index);
                style1.setFont(font);
                XSSFCellStyle style2 = (XSSFCellStyle) wb.createCellStyle();
                style2.setAlignment(CENTER);
                XSSFFont font2 = (XSSFFont) wb.createFont();
                font2.setFontName(FONT_ARIAL);
                font2.setFontHeightInPoints((short) 14);
                font2.setBold(true);
                style2.setFont(font2);
                Row row0 = sheet.createRow(0);
                Cell cell0 = row0.createCell(1);
                cell0.setCellStyle(style2);
                sheet.addMergedRegion(new CellRangeAddress(0, 0, 1, 9));
                cell0.setCellValue("Figures List - Branch: " + filiale);
                Row row = sheet.createRow(1);
                Cell cell = row.createCell(1);
                cell.setCellValue("Code");
                cell.setCellStyle(style1);
                Cell cell2 = row.createCell(2);
                cell2.setCellValue("Description");
                cell2.setCellStyle(style1);
                Cell cell3 = row.createCell(3);
                cell3.setCellValue("Kind");
                cell3.setCellStyle(style1);
                Cell cell4 = row.createCell(4);
                cell4.setCellValue("Buy Commission");
                cell4.setCellStyle(style1);
                Cell cell5 = row.createCell(5);
                cell5.setCellValue("Threshold Buy");
                cell5.setCellStyle(style1);
                Cell cell6 = row.createCell(6);
                cell6.setCellValue("Buy Fix Commission");
                cell6.setCellStyle(style1);
                Cell cell7 = row.createCell(7);
                cell7.setCellValue("Sell Commission");
                cell7.setCellStyle(style1);
                Cell cell8 = row.createCell(8);
                cell8.setCellValue("Threshold Sell");
                cell8.setCellStyle(style1);
                Cell cell9 = row.createCell(9);
                cell9.setCellValue("Sell Fix Commission");
                cell9.setCellStyle(style1);
                Cell cell10 = row.createCell(10);
                cell10.setCellValue("Sell Back Commission");
                cell10.setCellStyle(style1);
                Cell cell11 = row.createCell(11);
                cell11.setCellValue("Payment Mode");
                cell11.setCellStyle(style1);
                Cell cell12 = row.createCell(12);
                cell12.setCellValue("Sell Kind Resident");
                cell12.setCellStyle(style1);
                Cell cell13 = row.createCell(13);
                cell13.setCellValue("Upload Required");
                cell13.setCellStyle(style1);
                Cell cell14 = row.createCell(14);
                cell14.setCellValue("Upload Threshold");
                cell14.setCellStyle(style1);
                for (int i = 0; i < result.size(); i++) {
                    Row row2 = sheet.createRow(i + 3);
                    Figures res = (Figures) result.get(i);

                    String kind = "";
                    for (int x = 0; x < elencokind.size(); x++) {
                        if (elencokind.get(x)[0].equals(res.getFg_sys_trans())) {
                            kind = elencokind.get(x)[1];
                        }
                    }

                    Cell cell1 = row2.createCell(1);
                    cell1.setCellValue(res.getSupporto());
                    cell1.setCellStyle(style);
                    Cell cell22 = row2.createCell(2);
                    cell22.setCellValue(res.getDe_supporto());
                    cell22.setCellStyle(style);
                    Cell cell33 = row2.createCell(3);
                    cell33.setCellValue(kind);
                    cell33.setCellStyle(style);
                    Cell cell44 = row2.createCell(4);
                    cell44.setCellValue(res.getCommissione_acquisto());
                    cell44.setCellStyle(style);
                    Cell cell55 = row2.createCell(5);
                    cell55.setCellValue(res.getBuy_comm_soglia_causale());
                    cell55.setCellStyle(style);
                    Cell cell66 = row2.createCell(6);
                    cell66.setCellValue(res.getFix_buy_commission());
                    cell66.setCellStyle(style);
                    Cell cell77 = row2.createCell(7);
                    cell77.setCellValue(res.getCommissione_vendita());
                    cell77.setCellStyle(style);
                    Cell cell88 = row2.createCell(8);
                    cell88.setCellValue(res.getSell_comm_soglia_causale());
                    cell88.setCellStyle(style);
                    Cell cell99 = row2.createCell(9);
                    cell99.setCellValue(res.getFix_sell_commission());
                    cell99.setCellStyle(style);
                    Cell cell100 = row2.createCell(10);
                    cell100.setCellValue(res.getSell_back_commission());
                    cell100.setCellStyle(style);

                    Cell cell110 = row2.createCell(11);
                    if (res.getFg_tipo_incasso().equals("0")) {
                        cell110.setCellValue("Disabled");
                    } else {
                        cell110.setCellValue("Enabled");
                    }
                    cell110.setCellStyle(style);

                    Cell cell120 = row2.createCell(12);
                    if (res.getResidenti().equals("0")) {
                        cell120.setCellValue("Disabled");
                    } else {
                        cell120.setCellValue("Enabled");
                    }
                    cell120.setCellStyle(style);

                    Cell cell130 = row2.createCell(13);
                    if (res.getFg_uploadobbl().equals("0")) {
                        cell130.setCellValue("Disabled");
                    } else {
                        cell130.setCellValue("Enabled");
                    }
                    cell130.setCellStyle(style);

                    Cell cell140 = row2.createCell(14);
                    cell140.setCellValue(res.getUpload_thr());
                    cell140.setCellStyle(style);

                }
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
                try(FileOutputStream fileOut = new FileOutputStream(outputfile)){
                    wb.write(fileOut);
                    fileOut.close();
                }
            }
            String base64 = new String(encodeBase64(readFileToByteArray(outputfile)));
            outputfile.delete();
            return base64;
        } catch (Exception ex) {
            insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
        }
        return null;

    }

    /**
     *
     * @param outputfile
     * @param result
     * @return
     */
    public static String excel_currency_list(File outputfile, ArrayList<Currency> result) {
        try {

            try (//            Db_Master db = new Db_Master();
                    //            ArrayList<String[]> elencokind = db.list_selectkind();
                    //            db.closeDB();
                    //File out = new File(pathout + outputfile);
                    Workbook wb = new XSSFWorkbook()) {
                Sheet sheet = wb.createSheet("currency_list");
                XSSFCellStyle style = (XSSFCellStyle) wb.createCellStyle();
                style.setBorderBottom(THIN);
                style.setBorderTop(THIN);
                style.setBorderRight(THIN);
                style.setBorderLeft(THIN);
                XSSFCellStyle style1 = (XSSFCellStyle) wb.createCellStyle();
                style1.setBorderBottom(THIN);
                style1.setBorderTop(THIN);
                style1.setBorderRight(THIN);
                style1.setBorderLeft(THIN);
                style1.setAlignment(CENTER);
                XSSFFont font = (XSSFFont) wb.createFont();
                font.setFontName(FONT_ARIAL);
                font.setFontHeightInPoints((short) 12);
                font.setBold(true);
                //  font.setColor(HSSFColor.BLUE.index);
                style1.setFont(font);
                XSSFCellStyle style2 = (XSSFCellStyle) wb.createCellStyle();
                style2.setAlignment(CENTER);
                XSSFFont font2 = (XSSFFont) wb.createFont();
                font2.setFontName(FONT_ARIAL);
                font2.setFontHeightInPoints((short) 14);
                font2.setBold(true);
                style2.setFont(font2);
                Row row0 = sheet.createRow(0);
                Cell cell0 = row0.createCell(1);
                cell0.setCellStyle(style2);
                sheet.addMergedRegion(new CellRangeAddress(0, 0, 1, 9));
                cell0.setCellValue("Currency List - Branch: " + filiale);
                Row row = sheet.createRow(1);
                Cell cell = row.createCell(1);
                cell.setCellValue("Code");
                cell.setCellStyle(style1);
                Cell cell2 = row.createCell(2);
                cell2.setCellValue("Description");
                cell2.setCellStyle(style1);
                Cell cell3 = row.createCell(3);
                cell3.setCellValue("BCE Rate");
                cell3.setCellStyle(style1);
                Cell cell4 = row.createCell(4);
                cell4.setCellValue("Enable Buy");
                cell4.setCellStyle(style1);
                Cell cell5 = row.createCell(5);
                cell5.setCellValue("Enable Sell");
                cell5.setCellStyle(style1);
                Cell cell6 = row.createCell(6);
                cell6.setCellValue("Buy Std Type");
                cell6.setCellStyle(style1);
                Cell cell7 = row.createCell(7);
                cell7.setCellValue("Sell Std Type");
                cell7.setCellStyle(style1);
                for (int i = 0; i < result.size(); i++) {
                    Row row2 = sheet.createRow(i + 3);
                    Currency res = (Currency) result.get(i);

                    Cell cell1 = row2.createCell(1);
                    cell1.setCellValue(res.getCode());
                    cell1.setCellStyle(style);
                    Cell cell22 = row2.createCell(2);
                    cell22.setCellValue(res.getDescrizione());
                    cell22.setCellStyle(style);
                    Cell cell33 = row2.createCell(3);
                    cell33.setCellValue(res.getCambio_bce());
                    cell33.setCellStyle(style);
                    Cell cell44 = row2.createCell(4);
                    cell44.setCellValue(res.getEnable_buy());
                    cell44.setCellStyle(style);
                    Cell cell55 = row2.createCell(5);
                    cell55.setCellValue(res.getEnable_sell());
                    cell55.setCellStyle(style);

                    Cell cell66 = row2.createCell(6);
                    if (res.getBuy_std_type().equals("0")) {
                        cell66.setCellValue("Disabled");
                    } else {
                        cell66.setCellValue("Enabled");
                    }
                    cell66.setCellStyle(style);

                    Cell cell77 = row2.createCell(7);
                    if (res.getSell_std_type().equals("0")) {
                        cell77.setCellValue("Disabled");
                    } else {
                        cell77.setCellValue("Enabled");
                    }
                    cell77.setCellStyle(style);

                }
                sheet.autoSizeColumn(1);
                sheet.autoSizeColumn(2);
                sheet.autoSizeColumn(3);
                sheet.autoSizeColumn(4);
                sheet.autoSizeColumn(5);
                sheet.autoSizeColumn(6);
                sheet.autoSizeColumn(7);
                try(FileOutputStream fileOut = new FileOutputStream(outputfile)){
                    wb.write(fileOut);
                    fileOut.close();
                }
            }

            String base64 = new String(encodeBase64(readFileToByteArray(outputfile)));
            outputfile.delete();
            return base64;
        } catch (Exception ex) {
            insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
        }
        return null;

    }

    /**
     *
     * @param outputfile
     * @param result
     * @param filiale
     * @return
     */
    public static String excel_safetill_list(File outputfile, ArrayList<String[]> result, String filiale) {
        try {
            ArrayList<String[]> array_type_till = list_type_till();

            try ( //File out = new File(pathout + outputfile);
                    Workbook wb = new XSSFWorkbook()) {
                Sheet sheet = wb.createSheet("safetill_list");
                XSSFCellStyle style = (XSSFCellStyle) wb.createCellStyle();
                style.setBorderBottom(THIN);
                style.setBorderTop(THIN);
                style.setBorderRight(THIN);
                style.setBorderLeft(THIN);
                XSSFCellStyle style1 = (XSSFCellStyle) wb.createCellStyle();
                style1.setBorderBottom(THIN);
                style1.setBorderTop(THIN);
                style1.setBorderRight(THIN);
                style1.setBorderLeft(THIN);
                style1.setAlignment(CENTER);
                XSSFFont font = (XSSFFont) wb.createFont();
                font.setFontName(FONT_ARIAL);
                font.setFontHeightInPoints((short) 12);
                font.setBold(true);
                //  font.setColor(HSSFColor.BLUE.index);
                style1.setFont(font);
                XSSFCellStyle style2 = (XSSFCellStyle) wb.createCellStyle();
                style2.setAlignment(CENTER);
                XSSFFont font2 = (XSSFFont) wb.createFont();
                font2.setFontName(FONT_ARIAL);
                font2.setFontHeightInPoints((short) 14);
                font2.setBold(true);
                style2.setFont(font2);
                Row row0 = sheet.createRow(0);
                Cell cell0 = row0.createCell(1);
                cell0.setCellStyle(style2);
                sheet.addMergedRegion(new CellRangeAddress(0, 0, 1, 9));
                cell0.setCellValue("Safe Till List - Branch: " + filiale);
                Row row = sheet.createRow(1);
                Cell cell = row.createCell(1);
                cell.setCellValue("Code");
                cell.setCellStyle(style1);
                Cell cell2 = row.createCell(2);
                cell2.setCellValue("Description");
                cell2.setCellStyle(style1);
                Cell cell3 = row.createCell(3);
                cell3.setCellValue("Type");
                cell3.setCellStyle(style1);
                Cell cell4 = row.createCell(4);
                cell4.setCellValue("Status");
                cell4.setCellStyle(style1);
                for (int i = 0; i < result.size(); i++) {
                    Row row2 = sheet.createRow(i + 3);
                    String[] res = (String[]) result.get(i);
                    Cell cell1 = row2.createCell(1);
                    cell1.setCellValue(res[0]);
                    cell1.setCellStyle(style);
                    Cell cell22 = row2.createCell(2);
                    cell22.setCellValue(res[1]);
                    cell22.setCellStyle(style);
                    Cell cell33 = row2.createCell(3);
                    cell33.setCellValue(formatAL(res[2], array_type_till, 1));
                    cell33.setCellStyle(style);
                    Cell cell44 = row2.createCell(4);
                    cell44.setCellValue(formatStatus_general_cru(res[3]));
                    cell44.setCellStyle(style);
                }
                sheet.autoSizeColumn(1);
                sheet.autoSizeColumn(2);
                sheet.autoSizeColumn(3);
                sheet.autoSizeColumn(4);
                try(FileOutputStream fileOut = new FileOutputStream(outputfile)){
                    wb.write(fileOut);
                    fileOut.close();
                }
            }

            String base64 = new String(encodeBase64(readFileToByteArray(outputfile)));
            outputfile.delete();
            return base64;
        } catch (Exception ex) {
            insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
        }
        return null;

    }

    /**
     *
     * @param outputfile
     * @param result
     * @return
     */
    public static String excel_bank_list(File outputfile, ArrayList<String[]> result) {
        try {
            try ( //File out = new File(pathout + outputfile);
                    Workbook wb = new XSSFWorkbook()) {
                Sheet sheet = wb.createSheet("Bank_list");
                XSSFCellStyle style = (XSSFCellStyle) wb.createCellStyle();
                style.setBorderBottom(THIN);
                style.setBorderTop(THIN);
                style.setBorderRight(THIN);
                style.setBorderLeft(THIN);
                XSSFCellStyle style1 = (XSSFCellStyle) wb.createCellStyle();
                style1.setBorderBottom(THIN);
                style1.setBorderTop(THIN);
                style1.setBorderRight(THIN);
                style1.setBorderLeft(THIN);
                style1.setAlignment(CENTER);
                XSSFFont font = (XSSFFont) wb.createFont();
                font.setFontName(FONT_ARIAL);
                font.setFontHeightInPoints((short) 12);
                font.setBold(true);
                //  font.setColor(HSSFColor.BLUE.index);
                style1.setFont(font);
                XSSFCellStyle style2 = (XSSFCellStyle) wb.createCellStyle();
                style2.setAlignment(CENTER);
                XSSFFont font2 = (XSSFFont) wb.createFont();
                font2.setFontName(FONT_ARIAL);
                font2.setFontHeightInPoints((short) 14);
                font2.setBold(true);
                style2.setFont(font2);
                Row row0 = sheet.createRow(0);
                Cell cell0 = row0.createCell(1);
                cell0.setCellStyle(style2);
                sheet.addMergedRegion(new CellRangeAddress(0, 0, 1, 9));
                cell0.setCellValue("Bank List - Branch: " + filiale);
                Row row = sheet.createRow(1);
                Cell cell = row.createCell(1);
                cell.setCellValue("Code");
                cell.setCellStyle(style1);
                Cell cell2 = row.createCell(2);
                cell2.setCellValue("Description");
                cell2.setCellStyle(style1);
                Cell cell3 = row.createCell(3);
                cell3.setCellValue("Accounting Code");
                cell3.setCellStyle(style1);
                Cell cell4 = row.createCell(4);
                cell4.setCellValue("Status");
                cell4.setCellStyle(style1);
                Cell cell5 = row.createCell(5);
                cell5.setCellValue("Date Of Cancellation");
                cell5.setCellStyle(style1);
                for (int i = 0; i < result.size(); i++) {
                    Row row2 = sheet.createRow(i + 3);
                    String[] res = (String[]) result.get(i);
                    Cell cell1 = row2.createCell(1);
                    cell1.setCellValue(res[0]);
                    cell1.setCellStyle(style);
                    Cell cell22 = row2.createCell(2);
                    cell22.setCellValue(res[1]);
                    cell22.setCellStyle(style);
                    Cell cell33 = row2.createCell(3);
                    cell33.setCellValue(res[2]);
                    cell33.setCellStyle(style);
                    Cell cell44 = row2.createCell(4);
                    cell44.setCellValue(formatStatus_general_cru(res[4]));
                    cell44.setCellStyle(style);
                    Cell cell55 = row2.createCell(5);
                    cell55.setCellValue(res[3]);
                    cell55.setCellStyle(style);
                }
                sheet.autoSizeColumn(1);
                sheet.autoSizeColumn(2);
                sheet.autoSizeColumn(3);
                sheet.autoSizeColumn(4);
                sheet.autoSizeColumn(5);
                try(FileOutputStream fileOut = new FileOutputStream(outputfile)){
                    wb.write(fileOut);
                    fileOut.close();
                }
            }

            String base64 = new String(encodeBase64(readFileToByteArray(outputfile)));
            outputfile.delete();
            return base64;
        } catch (Exception ex) {
            insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
        }
        return null;
    }

    /**
     *
     * @param outputfile
     * @param result
     * @return
     */
    public static String excel_fixcomm_list(File outputfile, ArrayList<String[]> result) {
        try {
            ArrayList<String[]> array_kind = list_all_kind(filiale);
            try ( //File out = new File(pathout + outputfile);
                    Workbook wb = new XSSFWorkbook()) {
                Sheet sheet = wb.createSheet("Fixcomm_list");
                XSSFCellStyle style = (XSSFCellStyle) wb.createCellStyle();
                style.setBorderBottom(THIN);
                style.setBorderTop(THIN);
                style.setBorderRight(THIN);
                style.setBorderLeft(THIN);
                XSSFCellStyle style1 = (XSSFCellStyle) wb.createCellStyle();
                style1.setBorderBottom(THIN);
                style1.setBorderTop(THIN);
                style1.setBorderRight(THIN);
                style1.setBorderLeft(THIN);
                style1.setAlignment(CENTER);
                XSSFFont font = (XSSFFont) wb.createFont();
                font.setFontName(FONT_ARIAL);
                font.setFontHeightInPoints((short) 12);
                font.setBold(true);
                //  font.setColor(HSSFColor.BLUE.index);
                style1.setFont(font);
                XSSFCellStyle style2 = (XSSFCellStyle) wb.createCellStyle();
                style2.setAlignment(CENTER);
                XSSFFont font2 = (XSSFFont) wb.createFont();
                font2.setFontName(FONT_ARIAL);
                font2.setFontHeightInPoints((short) 14);
                font2.setBold(true);
                style2.setFont(font2);
                Row row0 = sheet.createRow(0);
                Cell cell0 = row0.createCell(1);
                cell0.setCellStyle(style2);
                sheet.addMergedRegion(new CellRangeAddress(0, 0, 1, 9));
                cell0.setCellValue("Fix Commission List - Branch: " + filiale);
                Row row = sheet.createRow(1);
                Cell cell = row.createCell(1);
                cell.setCellValue("Kind");
                cell.setCellStyle(style1);
                Cell cell2 = row.createCell(2);
                cell2.setCellValue("Min");
                cell2.setCellStyle(style1);
                Cell cell3 = row.createCell(3);
                cell3.setCellValue("Max");
                cell3.setCellStyle(style1);
                Cell cell4 = row.createCell(4);
                cell4.setCellValue("Buy");
                cell4.setCellStyle(style1);
                Cell cell5 = row.createCell(5);
                cell5.setCellValue("Sell");
                cell5.setCellStyle(style1);
                Cell cell6 = row.createCell(6);
                cell6.setCellValue("Status");
                cell6.setCellStyle(style1);
                for (int i = 0; i < result.size(); i++) {
                    Row row2 = sheet.createRow(i + 3);
                    String[] res = (String[]) result.get(i);
                    Cell cell1 = row2.createCell(1);
                    cell1.setCellValue(formatAL(res[0], array_kind, 1));
                    cell1.setCellStyle(style);
                    Cell cell22 = row2.createCell(2);
                    cell22.setCellValue(res[1]);
                    cell22.setCellStyle(style);
                    Cell cell33 = row2.createCell(3);
                    cell33.setCellValue(res[2]);
                    cell33.setCellStyle(style);
                    Cell cell44 = row2.createCell(4);
                    cell44.setCellValue(res[3]);
                    cell44.setCellStyle(style);
                    Cell cell55 = row2.createCell(5);
                    cell55.setCellValue(res[4]);
                    cell55.setCellStyle(style);
                    Cell cell66 = row2.createCell(6);
                    cell66.setCellValue(formatStatus_general_cru(res[5]));
                    cell66.setCellStyle(style);
                }
                sheet.autoSizeColumn(1);
                sheet.autoSizeColumn(2);
                sheet.autoSizeColumn(3);
                sheet.autoSizeColumn(4);
                sheet.autoSizeColumn(5);
                sheet.autoSizeColumn(6);
                try(FileOutputStream fileOut = new FileOutputStream(outputfile)){
                    wb.write(fileOut);
                    fileOut.close();
                }
            }

            String base64 = new String(encodeBase64(readFileToByteArray(outputfile)));
            outputfile.delete();
            return base64;
        } catch (Exception ex) {
            insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
        }
        return null;
    }

    /**
     *
     * @param outputfile
     * @param result
     * @return
     */
    public static String excel_kindfixcomm_list(File outputfile, ArrayList<String[]> result) {
        try {
            ArrayList<String[]> array_kind = list_all_kind(filiale);
            try ( //File out = new File(pathout + outputfile);
                    Workbook wb = new XSSFWorkbook()) {
                Sheet sheet = wb.createSheet("KindFixcomm_list");
                XSSFCellStyle style = (XSSFCellStyle) wb.createCellStyle();
                style.setBorderBottom(THIN);
                style.setBorderTop(THIN);
                style.setBorderRight(THIN);
                style.setBorderLeft(THIN);
                XSSFCellStyle style1 = (XSSFCellStyle) wb.createCellStyle();
                style1.setBorderBottom(THIN);
                style1.setBorderTop(THIN);
                style1.setBorderRight(THIN);
                style1.setBorderLeft(THIN);
                style1.setAlignment(CENTER);
                XSSFFont font = (XSSFFont) wb.createFont();
                font.setFontName(FONT_ARIAL);
                font.setFontHeightInPoints((short) 12);
                font.setBold(true);
                //  font.setColor(HSSFColor.BLUE.index);
                style1.setFont(font);
                XSSFCellStyle style2 = (XSSFCellStyle) wb.createCellStyle();
                style2.setAlignment(CENTER);
                XSSFFont font2 = (XSSFFont) wb.createFont();
                font2.setFontName(FONT_ARIAL);
                font2.setFontHeightInPoints((short) 14);
                font2.setBold(true);
                style2.setFont(font2);
                Row row0 = sheet.createRow(0);
                Cell cell0 = row0.createCell(1);
                cell0.setCellStyle(style2);
                sheet.addMergedRegion(new CellRangeAddress(0, 0, 1, 9));
                cell0.setCellValue("Kind Fix Commission List - Branch: " + filiale);
                Row row = sheet.createRow(1);
                Cell cell = row.createCell(1);
                cell.setCellValue("Code");
                cell.setCellStyle(style1);
                Cell cell2 = row.createCell(2);
                cell2.setCellValue("Description");
                cell2.setCellStyle(style1);
                Cell cell3 = row.createCell(3);
                cell3.setCellValue("Value");
                cell3.setCellStyle(style1);
                Cell cell4 = row.createCell(4);
                cell4.setCellValue("Status");
                cell4.setCellStyle(style1);
                for (int i = 0; i < result.size(); i++) {
                    Row row2 = sheet.createRow(i + 3);
                    String[] res = (String[]) result.get(i);
                    Cell cell1 = row2.createCell(1);
                    cell1.setCellValue(res[0]);
                    cell1.setCellStyle(style);
                    Cell cell22 = row2.createCell(2);
                    cell22.setCellValue(res[1]);
                    cell22.setCellStyle(style);
                    Cell cell33 = row2.createCell(3);
                    cell33.setCellValue(res[2]);
                    cell33.setCellStyle(style);
                    Cell cell44 = row2.createCell(4);
                    cell44.setCellValue(formatStatus_general_cru(res[3]));
                    cell44.setCellStyle(style);
                }
                sheet.autoSizeColumn(1);
                sheet.autoSizeColumn(2);
                sheet.autoSizeColumn(3);
                sheet.autoSizeColumn(4);
                try(FileOutputStream fileOut = new FileOutputStream(outputfile)){
                wb.write(fileOut);
                fileOut.close();
            }
        }

            String base64 = new String(encodeBase64(readFileToByteArray(outputfile)));
            outputfile.delete();
            return base64;
        } catch (Exception ex) {
            insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
        }
        return null;
    }

    /**
     *
     * @param outputfile
     * @param result
     * @param filiale
     * @return
     */
    public static String excel_raterange_list(File outputfile, ArrayList<String[]> result, String filiale) {
        try {
            ArrayList<String[]> array_kind = list_all_kind(filiale);
            try ( //File out = new File(pathout + outputfile);
                    Workbook wb = new XSSFWorkbook()) {
                Sheet sheet = wb.createSheet("LevelRate_list");
                XSSFCellStyle style = (XSSFCellStyle) wb.createCellStyle();
                style.setBorderBottom(THIN);
                style.setBorderTop(THIN);
                style.setBorderRight(THIN);
                style.setBorderLeft(THIN);
                XSSFCellStyle style1 = (XSSFCellStyle) wb.createCellStyle();
                style1.setBorderBottom(THIN);
                style1.setBorderTop(THIN);
                style1.setBorderRight(THIN);
                style1.setBorderLeft(THIN);
                style1.setAlignment(CENTER);
                XSSFFont font = (XSSFFont) wb.createFont();
                font.setFontName(FONT_ARIAL);
                font.setFontHeightInPoints((short) 12);
                font.setBold(true);
                //  font.setColor(HSSFColor.BLUE.index);
                style1.setFont(font);
                XSSFCellStyle style2 = (XSSFCellStyle) wb.createCellStyle();
                style2.setAlignment(CENTER);
                XSSFFont font2 = (XSSFFont) wb.createFont();
                font2.setFontName(FONT_ARIAL);
                font2.setFontHeightInPoints((short) 14);
                font2.setBold(true);
                style2.setFont(font2);
                Row row0 = sheet.createRow(0);
                Cell cell0 = row0.createCell(1);
                cell0.setCellStyle(style2);
                sheet.addMergedRegion(new CellRangeAddress(0, 0, 1, 9));
                cell0.setCellValue("Level Rate List - Branch: " + filiale);
                Row row = sheet.createRow(1);
                Cell cell = row.createCell(1);
                cell.setCellValue("Kind");
                cell.setCellStyle(style1);
                Cell cell2 = row.createCell(2);
                cell2.setCellValue("Min");
                cell2.setCellStyle(style1);
                Cell cell3 = row.createCell(3);
                cell3.setCellValue("Max");
                cell3.setCellStyle(style1);
                Cell cell4 = row.createCell(4);
                cell4.setCellValue("Buy");
                cell4.setCellStyle(style1);
                Cell cell5 = row.createCell(5);
                cell5.setCellValue("Sell");
                cell5.setCellStyle(style1);
                Cell cell6 = row.createCell(6);
                cell6.setCellValue("Status");
                cell6.setCellStyle(style1);
                for (int i = 0; i < result.size(); i++) {
                    Row row2 = sheet.createRow(i + 3);
                    String[] res = (String[]) result.get(i);
                    Cell cell1 = row2.createCell(1);
                    cell1.setCellValue(formatAL(res[0], array_kind, 1));
                    cell1.setCellStyle(style);
                    Cell cell22 = row2.createCell(2);
                    cell22.setCellValue(res[1]);
                    cell22.setCellStyle(style);
                    Cell cell33 = row2.createCell(3);
                    cell33.setCellValue(res[2]);
                    cell33.setCellStyle(style);
                    Cell cell44 = row2.createCell(4);
                    cell44.setCellValue(res[3]);
                    cell44.setCellStyle(style);
                    Cell cell55 = row2.createCell(5);
                    cell55.setCellValue(res[4]);
                    cell55.setCellStyle(style);
                    Cell cell66 = row2.createCell(6);
                    cell66.setCellValue(formatStatus_general_cru(res[5]));
                    cell66.setCellStyle(style);
                }
                sheet.autoSizeColumn(1);
                sheet.autoSizeColumn(2);
                sheet.autoSizeColumn(3);
                sheet.autoSizeColumn(4);
                sheet.autoSizeColumn(5);
                sheet.autoSizeColumn(6);
                try(FileOutputStream fileOut = new FileOutputStream(outputfile)){
                wb.write(fileOut);
                fileOut.close();
            }
        }

            String base64 = new String(encodeBase64(readFileToByteArray(outputfile)));
            outputfile.delete();
            return base64;
        } catch (Exception ex) {
            insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
        }
        return null;
    }

    /**
     *
     * @param outputfile
     * @param liout
     * @return
     */
    public static String excel_transaction_allnccat(File outputfile, ArrayList<String[]> liout) {
        try {
            try (XSSFWorkbook wb = new XSSFWorkbook()) {
                IndexedColorMap colorMap = wb.getStylesSource().getIndexedColors();
                XSSFColor color = new XSSFColor(new java.awt.Color(192, 192, 192), colorMap);
                Sheet sheet = wb.createSheet("All_Nochange");
                XSSFDataFormat hssfDataFormat = (XSSFDataFormat) wb.createDataFormat();
                XSSFCellStyle style = (XSSFCellStyle) wb.createCellStyle();
                style.setBorderBottom(THIN);
                style.setBorderTop(THIN);
                style.setBorderRight(THIN);
                style.setBorderLeft(THIN);
                XSSFCellStyle stylenum = (XSSFCellStyle) wb.createCellStyle();
                stylenum.setBorderBottom(THIN);
                stylenum.setBorderTop(THIN);
                stylenum.setBorderRight(THIN);
                stylenum.setBorderLeft(THIN);
                stylenum.setDataFormat(hssfDataFormat.getFormat("#,#.00"));
                XSSFCellStyle stylenumG = (XSSFCellStyle) wb.createCellStyle();
                stylenumG.setBorderBottom(THIN);
                stylenumG.setBorderTop(THIN);
                stylenumG.setBorderRight(THIN);
                stylenumG.setBorderLeft(THIN);
                stylenumG.setFillForegroundColor(color);
                stylenumG.setFillPattern(SOLID_FOREGROUND);
                stylenumG.setDataFormat(hssfDataFormat.getFormat("#,#.00"));
                XSSFCellStyle styleG = (XSSFCellStyle) wb.createCellStyle();
                styleG.setBorderBottom(THIN);
                styleG.setBorderTop(THIN);
                styleG.setBorderRight(THIN);
                styleG.setBorderLeft(THIN);
                styleG.setFillForegroundColor(color);
                styleG.setFillPattern(SOLID_FOREGROUND);
                XSSFCellStyle style1 = (XSSFCellStyle) wb.createCellStyle();
                style1.setBorderBottom(THIN);
                style1.setBorderTop(THIN);
                style1.setBorderRight(THIN);
                style1.setBorderLeft(THIN);
                style1.setAlignment(CENTER);
                XSSFFont font = (XSSFFont) wb.createFont();
                font.setFontName(FONT_ARIAL);
                font.setFontHeightInPoints((short) 12);
                font.setBold(true);
                style1.setFont(font);
                Row row = sheet.createRow(1);
                Cell cell14 = row.createCell(1);
                cell14.setCellValue("Branch ID");
                cell14.setCellStyle(style1);
                Cell cell12 = row.createCell(2);
                cell12.setCellValue("Category");
                cell12.setCellStyle(style1);
                Cell cell = row.createCell(3);
                cell.setCellValue("Causal");
                cell.setCellStyle(style1);
                Cell cell2 = row.createCell(4);
                cell2.setCellValue("Description");
                cell2.setCellStyle(style1);
                Cell cell3 = row.createCell(5);
                cell3.setCellValue("Kind");
                cell3.setCellStyle(style1);
                Cell cell4 = row.createCell(6);
                cell4.setCellValue("Price");
                cell4.setCellStyle(style1);
                Cell cell6 = row.createCell(7);
                cell6.setCellValue("Status");
                cell6.setCellStyle(style1);
                for (int i = 0; i < liout.size(); i++) {
                    Row row2 = sheet.createRow(i + 3);
                    XSSFCellStyle st = style;
                    if (liout.get(i)[1].equals("")) {
                        st = styleG;
                    }
                    Cell cell881 = row2.createCell(1);
                    cell881.setCellValue(liout.get(i)[6]);
                    cell881.setCellStyle(st);
                    Cell cell882 = row2.createCell(2);
                    cell882.setCellValue(liout.get(i)[0]);
                    cell882.setCellStyle(st);
                    Cell cell883 = row2.createCell(3);
                    cell883.setCellValue(liout.get(i)[1]);
                    cell883.setCellStyle(st);
                    Cell cell884 = row2.createCell(4);
                    cell884.setCellValue(liout.get(i)[2]);
                    cell884.setCellStyle(st);
                    Cell cell885 = row2.createCell(5);
                    cell885.setCellValue(liout.get(i)[3]);
                    cell885.setCellStyle(st);
                    Cell cell886 = row2.createCell(6, NUMERIC);
                    cell886.setCellValue(fd(formatDoubleforMysql(liout.get(i)[4])));
                    if (liout.get(i)[1].equals("")) {
                        cell886.setCellStyle(stylenumG);
                    } else {
                        cell886.setCellStyle(stylenum);
                    }
                    String v1;
                    if (liout.get(i)[5].contains("Enabled")) {
                        v1 = "Enabled";
                    } else {
                        v1 = "Disabled";
                    }
                    Cell cell887 = row2.createCell(7);
                    cell887.setCellValue(v1);
                    cell887.setCellStyle(st);
                }
                sheet.autoSizeColumn(1);
                sheet.autoSizeColumn(2);
                sheet.autoSizeColumn(3);
                sheet.autoSizeColumn(4);
                sheet.autoSizeColumn(5);
                sheet.autoSizeColumn(6);
                sheet.autoSizeColumn(7);
                try(FileOutputStream fileOut = new FileOutputStream(outputfile)){
                wb.write(fileOut);
                fileOut.close();
            }
        }

            String base64 = new String(encodeBase64(readFileToByteArray(outputfile)));
            outputfile.delete();
            return base64;
        } catch (Exception ex) {
            insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
        }
        return null;
    }

    /**
     *
     * @param outputfile
     * @param result
     * @return
     */
    public static String excel_transaction_listEVO(File outputfile, ArrayList<Ch_transaction> result, List<Marketing> list_consensi_attivi) {
        try {
            try ( //File out = new File(pathout + outputfile);

                    Workbook wb = new XSSFWorkbook()) {
                Sheet sheet = wb.createSheet("Transaction_list_E");
                XSSFCellStyle style = (XSSFCellStyle) wb.createCellStyle();
                style.setBorderBottom(THIN);
                style.setBorderTop(THIN);
                style.setBorderRight(THIN);
                style.setBorderLeft(THIN);
                XSSFCellStyle style1 = (XSSFCellStyle) wb.createCellStyle();
                style1.setBorderBottom(THIN);
                style1.setBorderTop(THIN);
                style1.setBorderRight(THIN);
                style1.setBorderLeft(THIN);
                style1.setAlignment(CENTER);
                XSSFFont font = (XSSFFont) wb.createFont();
                font.setFontName(FONT_ARIAL);
                font.setFontHeightInPoints((short) 12);
                font.setBold(true);
                //  font.setColor(HSSFColor.BLUE.index);
                style1.setFont(font);
                XSSFCellStyle style2 = (XSSFCellStyle) wb.createCellStyle();
                style2.setAlignment(CENTER);
                XSSFFont font2 = (XSSFFont) wb.createFont();
                font2.setFontName(FONT_ARIAL);
                font2.setFontHeightInPoints((short) 14);
                font2.setBold(true);
                style2.setFont(font2);
                Row row0 = sheet.createRow(0);
                Cell cell0 = row0.createCell(1);
                cell0.setCellStyle(style2);
                sheet.addMergedRegion(new CellRangeAddress(0, 0, 1, 9));
                cell0.setCellValue("Transaction List");
                XSSFCellStyle cellStylenum = (XSSFCellStyle) wb.createCellStyle();
                XSSFDataFormat hssfDataFormat = (XSSFDataFormat) wb.createDataFormat();
                cellStylenum.setDataFormat(hssfDataFormat.getFormat("#,#.00"));
                //private static final String formatdataCell = "#.#0,0";
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
                Row row = sheet.createRow(1);
                Cell cell14 = row.createCell(1);
                cell14.setCellValue("Status");
                cell14.setCellStyle(style1);
                Cell cell12 = row.createCell(2);
                cell12.setCellValue("Branch ID");
                cell12.setCellStyle(style1);
                Cell cell = row.createCell(3);
                cell.setCellValue("Code");
                cell.setCellStyle(style1);
                Cell cell2 = row.createCell(4);
                cell2.setCellValue("Date");
                cell2.setCellStyle(style1);
                Cell cell3 = row.createCell(5);
                cell3.setCellValue("Till");
                cell3.setCellStyle(style1);
                Cell cell4 = row.createCell(6);
                cell4.setCellValue("Operator");
                cell4.setCellStyle(style1);
                Cell cell6 = row.createCell(7);
                cell6.setCellValue("Type");
                cell6.setCellStyle(style1);
                Cell cell7 = row.createCell(8);
                cell7.setCellValue("Total");
                cell7.setCellStyle(style1);
                Cell cell8 = row.createCell(9);
                cell8.setCellValue("Net");
                cell8.setCellStyle(style1);
                Cell cell9 = row.createCell(10);
                cell9.setCellValue("Commission");
                cell9.setCellStyle(style1);
                Cell cell10 = row.createCell(11);
                cell10.setCellValue("Spread");
                cell10.setCellStyle(style1);
                Cell cell11 = row.createCell(12);
                cell11.setCellValue("Invoice");
                cell11.setCellStyle(style1);
                Cell cell122 = row.createCell(13);
                cell122.setCellValue("Credit Note");
                cell122.setCellStyle(style1);
                Cell cell13 = row.createCell(14);
                cell13.setCellValue("Date Deleted");
                cell13.setCellStyle(style1);
                Cell cell131 = row.createCell(15);
                cell131.setCellValue("Client Surname");
                cell131.setCellStyle(style1);
                Cell cell132 = row.createCell(16);
                cell132.setCellValue("Client Name");
                cell132.setCellStyle(style1);
                Cell cell133 = row.createCell(17);
                cell133.setCellValue("Client Tax Code");
                cell133.setCellStyle(style1);
                Cell cell133a = row.createCell(18);
                cell133a.setCellValue("Pos/Bank Account");
                cell133a.setCellStyle(style1);
                Cell celln1 = row.createCell(19);
                celln1.setCellValue("Currency");
                celln1.setCellStyle(style1);
                Cell celln2 = row.createCell(20);
                celln2.setCellValue("Figures");
                celln2.setCellStyle(style1);
                Cell celln3 = row.createCell(21);
                celln3.setCellValue("Nation of Birth");
                celln3.setCellStyle(style1);
                Cell celln4 = row.createCell(22);
                celln4.setCellValue("Nation of Residence");
                celln4.setCellStyle(style1);
                Cell celln5 = row.createCell(23);
                celln5.setCellValue("Address");
                celln5.setCellStyle(style1);
                Cell celln6 = row.createCell(24);
                celln6.setCellValue("Internet Booking");
                celln6.setCellStyle(style1);
                Cell celln_0 = row.createCell(25);
                celln_0.setCellValue("Date Of Birth");
                celln_0.setCellStyle(style1);
                Cell celln_1 = row.createCell(26);
                celln_1.setCellValue("Sex");
                celln_1.setCellStyle(style1);
                Cell celln_2 = row.createCell(27);
                celln_2.setCellValue("Phone Number");
                celln_2.setCellStyle(style1);
                Cell celln_3 = row.createCell(28);
                celln_3.setCellValue("Email");
                celln_3.setCellStyle(style1);
                Cell celln_4 = row.createCell(29);
                celln_4.setCellValue("Authorization to the processing of personal data");
                celln_4.setCellStyle(style1);
                Cell celln_5 = row.createCell(30);
                celln_5.setCellValue("Loyalty Code");
                celln_5.setCellStyle(style1);
                Cell celln_6 = row.createCell(31);
                celln_6.setCellValue("% Com.");
                celln_6.setCellStyle(style1);
                Cell celln_7 = row.createCell(32);
                celln_7.setCellValue("Zip Code");
                celln_7.setCellStyle(style1);
                Db_Master db = new Db_Master();
                ArrayList<Figures> figu = db.list_all_figures();
                ArrayList<Currency> curr = db.list_currency(filiale);
                ArrayList<String[]> city = db.city_Italy_APM();
                ArrayList<String[]> country = db.country();
                db.closeDB();
                Db_Master db2 = new Db_Master();
                for (int i = 0; i < result.size(); i++) {
                    Row row2 = sheet.createRow(i + 3);
                    Ch_transaction res = (Ch_transaction) result.get(i);
                    Client cl = db2.query_Client_transaction(res.getCod(), res.getCl_cod());
                    String loy = db2.query_LOY_transaction(res.getCod());
                    if (loy == null) {
                        loy = "";
                    }
                    String dt_del = "";
                    if (res.getDel_fg().equals("1")) {
                        dt_del = formatStringtoStringDate(res.getDel_dt().split("\\.")[0], "yyyy-MM-dd HH:mm:ss", "dd/MM/yyyy HH:mm:ss");
                    }
                    String pos = res.getPos();
                    String ca = "";
                    ArrayList<String> cur = new ArrayList<>();
                    ArrayList<String> fig = new ArrayList<>();
                    ArrayList<Ch_transaction_value> va  = query_transaction_value(res.getCod());
                    for (int f = 0; f < va.size(); f++) {
                        cur.add(va.get(f).getValuta());
                        fig.add(va.get(f).getSupporto());
                        if (va.get(f).getSupporto().equals("04")) {
                            pos = va.get(f).getPos();
                            ca = " CA ";
                        }
                    }
                    Cell cell140 = row2.createCell(1);
                    cell140.setCellValue(res.formatStatus_cru(res.getDel_fg()));
                    cell140.setCellStyle(style);
                    Cell cell1b = row2.createCell(2);
                    cell1b.setCellValue(res.getFiliale());
                    cell1b.setCellStyle(style);
                    Cell cell1 = row2.createCell(3);
                    cell1.setCellValue(res.getId());
                    cell1.setCellStyle(style);
                    Cell cell22 = row2.createCell(4);
                    cell22.setCellValue(formatStringtoStringDate(res.getData().split("\\.")[0], "yyyy-MM-dd HH:mm:ss", "dd/MM/yyyy HH:mm:ss"));
                    cell22.setCellStyle(style);
                    Cell cell33 = row2.createCell(5);
                    cell33.setCellValue(res.getTill());
                    cell33.setCellStyle(style);
                    Cell cell44 = row2.createCell(6);
                    cell44.setCellValue(res.getUser());
                    cell44.setCellStyle(style);
                    Cell cell66 = row2.createCell(7);
                    cell66.setCellValue(formatType(res.getTipotr()) + ca);
                    cell66.setCellStyle(style);
                    Cell cell77 = row2.createCell(8, NUMERIC);
                    cell77.setCellValue(fd(res.getTotal()));
                    cell77.setCellStyle(cellStylenum);
                    Cell cell88 = row2.createCell(9, NUMERIC);
                    cell88.setCellValue(fd(res.getPay()));
                    cell88.setCellStyle(cellStylenum);
                    Cell cell99 = row2.createCell(10, NUMERIC);
                    cell99.setCellValue(fd(res.getCommission()));
                    cell99.setCellStyle(cellStylenum);
                    Cell cell100 = row2.createCell(11, NUMERIC);
                    cell100.setCellValue(fd(res.getSpread_total()));
                    cell100.setCellStyle(cellStylenum);
                    Cell cell110 = row2.createCell(12);
                    cell110.setCellValue(res.getFa_number());
                    cell110.setCellStyle(style);
                    Cell cell120 = row2.createCell(13);
                    cell120.setCellValue(res.getCn_number());
                    cell120.setCellStyle(style);
                    Cell cell130 = row2.createCell(14);
                    cell130.setCellValue(dt_del);
                    cell130.setCellStyle(style);
                    Cell cell1301 = row2.createCell(15);
                    cell1301.setCellValue(cl.getCognome().toUpperCase());
                    cell1301.setCellStyle(style);
                    Cell cell1302 = row2.createCell(16);
                    cell1302.setCellValue(cl.getNome().toUpperCase());
                    cell1302.setCellStyle(style);
                    Cell cell1303 = row2.createCell(17);
                    cell1303.setCellValue(cl.getCodfisc().toUpperCase());
                    cell1303.setCellStyle(style);
                    Cell cell1303a = row2.createCell(18);
                    cell1303a.setCellValue(pos);
                    cell1303a.setCellStyle(style);
                    Cell celln11 = row2.createCell(19);
                    celln11.setCellValue(getStringCurrency(cur, curr));
                    celln11.setCellStyle(style);
                    Cell celln12 = row2.createCell(20);
                    celln12.setCellValue(getStringFigures(fig, figu));
                    celln12.setCellStyle(style);
                    Cell celln13 = row2.createCell(21);
                    celln13.setCellValue(formatCountry_cru(cl.getNazione_nascita(), country).toUpperCase());
                    celln13.setCellStyle(style);
                    Cell celln14 = row2.createCell(22);
                    celln14.setCellValue(formatCountry_cru(cl.getNazione(), country).toUpperCase());
                    celln14.setCellStyle(style);
                    Cell celln15 = row2.createCell(23);
                    celln15.setCellValue(cl.getIndirizzo() + " - " + formatALN(cl.getCitta(), city, 1).toUpperCase());
                    celln15.setCellStyle(style);
                    Cell celln16 = row2.createCell(24);
                    if (res.getIntbook().equals("0")) {
                        celln16.setCellValue("NO");
                    } else {
                        celln16.setCellValue("SI");
                    }
                    celln16.setCellStyle(style);
                    Cell celln1_0 = row2.createCell(25);
                    celln1_0.setCellValue(cl.getDt_nascita());
                    celln1_0.setCellStyle(style);
                    Cell celln1_1 = row2.createCell(26);
                    celln1_1.setCellValue(formatSex(cl.getSesso()).toUpperCase());
                    celln1_1.setCellStyle(style);
                    Cell celln1_2 = row2.createCell(27);
                    celln1_2.setCellValue(cl.getTelefono());
                    celln1_2.setCellStyle(style);
                    Cell celln1_3 = row2.createCell(28);
                    celln1_3.setCellValue(cl.getEmail());
                    celln1_3.setCellStyle(style);

                    Cell celln1_4 = row2.createCell(29);

                    String consenso = "NO";
                    try {
                        if (list_consensi_attivi.stream().anyMatch(cons -> cons.getCodtr().equals(res.getCod()))) {
                            consenso = "SI";
                        } else if (list_consensi_attivi.stream().anyMatch(
                                cons -> cons.getCl().getCode().equals(cl.getCode())
                        )) {
                            Optional<Marketing> optionalCons = list_consensi_attivi.stream()
                                    .filter(cons -> cons.getCl().getCode().equals(cl.getCode()))
                                    .findAny();
                                    
                            if (optionalCons.isPresent()) {
                                DateTime cons1 = optionalCons.get().getDt();
                                DateTime tr = parseStringDate(res.getData(), patternsqldate);
                    
                                if (cons1.isBefore(tr)) {
                                    consenso = "";
                                    // consenso = "SI (DATA CONSENSO CLIENTE "+cons1.toString(patternnormdate)+")";
                                }
                            }
                        }
                    } catch (Exception e) {
                        consenso = "NO";
                        e.printStackTrace();
                    }
                    

                    celln1_4.setCellValue(consenso);
                    celln1_4.setCellStyle(style);

                    Cell celln1_5 = row2.createCell(30);
                    celln1_5.setCellValue(loy);
                    celln1_5.setCellStyle(style);
                    Cell celln1_6 = row2.createCell(31, NUMERIC);
                    celln1_6.setCellValue(fd(res.getCom()));
                    celln1_6.setCellStyle(cellStylenum);
                    Cell celln1_7 = row2.createCell(32);
                    celln1_7.setCellValue(cl.getCap());
                    celln1_7.setCellStyle(style);
                }
                db2.closeDB();
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
                try(FileOutputStream fileOut = new FileOutputStream(outputfile)){
                wb.write(fileOut);
                fileOut.close();
            }}

            String base64 = new String(encodeBase64(readFileToByteArray(outputfile)));
            outputfile.delete();
            return base64;
        } catch (IOException ex) {
            insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
        }
        return null;
    }

    /**
     *
     * @param outputfile
     * @param result
     * @return
     */
    public static String excel_transaction_list(File outputfile, ArrayList<Ch_transaction> result) {
        try {
            String fil = getFil()[0];

            try ( //File out = new File(pathout + outputfile);
                    Workbook wb = new XSSFWorkbook()) {
                Sheet sheet = wb.createSheet("Transaction_list");
                XSSFCellStyle style = (XSSFCellStyle) wb.createCellStyle();
                style.setBorderBottom(THIN);
                style.setBorderTop(THIN);
                style.setBorderRight(THIN);
                style.setBorderLeft(THIN);
                XSSFCellStyle style1 = (XSSFCellStyle) wb.createCellStyle();
                style1.setBorderBottom(THIN);
                style1.setBorderTop(THIN);
                style1.setBorderRight(THIN);
                style1.setBorderLeft(THIN);
                style1.setAlignment(CENTER);
                XSSFFont font = (XSSFFont) wb.createFont();
                font.setFontName(FONT_ARIAL);
                font.setFontHeightInPoints((short) 12);
                font.setBold(true);
                //  font.setColor(HSSFColor.BLUE.index);
                style1.setFont(font);
                XSSFCellStyle style2 = (XSSFCellStyle) wb.createCellStyle();
                style2.setAlignment(CENTER);
                XSSFFont font2 = (XSSFFont) wb.createFont();
                font2.setFontName(FONT_ARIAL);
                font2.setFontHeightInPoints((short) 14);
                font2.setBold(true);
                style2.setFont(font2);
                Row row0 = sheet.createRow(0);
                Cell cell0 = row0.createCell(1);
                cell0.setCellStyle(style2);
                sheet.addMergedRegion(new CellRangeAddress(0, 0, 1, 9));
                cell0.setCellValue("Transaction List");
                XSSFCellStyle cellStylenum = (XSSFCellStyle) wb.createCellStyle();
                XSSFDataFormat hssfDataFormat = (XSSFDataFormat) wb.createDataFormat();
                cellStylenum.setDataFormat(hssfDataFormat.getFormat("#,#.00"));
                //private static final String formatdataCell = "#.#0,0";
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
                Row row = sheet.createRow(1);
                Cell cell14 = row.createCell(1);
                cell14.setCellValue("Status");
                cell14.setCellStyle(style1);
                Cell cell12 = row.createCell(2);
                cell12.setCellValue("Branch ID");
                cell12.setCellStyle(style1);
                Cell cell = row.createCell(3);
                cell.setCellValue("Code");
                cell.setCellStyle(style1);
                Cell cell2 = row.createCell(4);
                cell2.setCellValue("Date");
                cell2.setCellStyle(style1);
                Cell cell3 = row.createCell(5);
                cell3.setCellValue("Till");
                cell3.setCellStyle(style1);
                Cell cell4 = row.createCell(6);
                cell4.setCellValue("Operator");
                cell4.setCellStyle(style1);
                Cell cell6 = row.createCell(7);
                cell6.setCellValue("Type");
                cell6.setCellStyle(style1);
                Cell cell7 = row.createCell(8);
                cell7.setCellValue("Total");
                cell7.setCellStyle(style1);
                Cell cell8 = row.createCell(9);
                cell8.setCellValue("Net");
                cell8.setCellStyle(style1);
                Cell cell9 = row.createCell(10);
                cell9.setCellValue("Commission");
                cell9.setCellStyle(style1);
                Cell cell10 = row.createCell(11);
                cell10.setCellValue("Spread");
                cell10.setCellStyle(style1);
                Cell cell11 = row.createCell(12);
                cell11.setCellValue("Invoice");
                cell11.setCellStyle(style1);
                Cell cell122 = row.createCell(13);
                cell122.setCellValue("Credit Note");
                cell122.setCellStyle(style1);
                Cell cell13 = row.createCell(14);
                cell13.setCellValue("Date Deleted");
                cell13.setCellStyle(style1);
                Cell cell131 = row.createCell(15);
                cell131.setCellValue("Client Surname");
                cell131.setCellStyle(style1);
                Cell cell132 = row.createCell(16);
                cell132.setCellValue("Client Name");
                cell132.setCellStyle(style1);
                Cell cell133 = row.createCell(17);
                cell133.setCellValue("Client Tax Code");
                cell133.setCellStyle(style1);
                Cell cell133a = row.createCell(18);
                cell133a.setCellValue("Pos/Bank Account");
                cell133a.setCellStyle(style1);
                Cell cell133b = row.createCell(19);
                cell133b.setCellValue("Loyalty Code");
                cell133b.setCellStyle(style1);
                Db_Master db2 = new Db_Master();
                for (int i = 0; i < result.size(); i++) {
                    Row row2 = sheet.createRow(i + 3);
                    Ch_transaction res = (Ch_transaction) result.get(i);
                    if (db2.getC() == null) {
                        db2 = new Db_Master();
                    }
                    Client cl = db2.query_Client_transaction(res.getCod(), res.getCl_cod());
                    String dt_del = "";
                    if (res.getDel_fg().equals("1")) {
                        dt_del = formatStringtoStringDate(res.getDel_dt().split("\\.")[0], "yyyy-MM-dd HH:mm:ss", "dd/MM/yyyy HH:mm:ss");
                    }
                    String pos = res.getPos();
                    String ca = "";
                    if (res.getTipotr().equals("B")) {
                        if (db2.getC() == null) {
                            db2 = new Db_Master();
                        }
                        ArrayList<Ch_transaction_value> va  = db2.query_transaction_value(res.getCod());
                        for (int f = 0; f < va.size(); f++) {
                            if (va.get(f).getSupporto().equals("04")) {
                                pos = va.get(f).getPos();
                                ca = " CA ";
                                break;
                            }
                        }
                    }
                    Cell cell140 = row2.createCell(1);
                    cell140.setCellValue(res.formatStatus_cru(res.getDel_fg()));
                    cell140.setCellStyle(style);
                    Cell cell1b = row2.createCell(2);
                    cell1b.setCellValue(res.getFiliale());
                    cell1b.setCellStyle(style);
                    Cell cell1 = row2.createCell(3);
                    cell1.setCellValue(res.getId());
                    cell1.setCellStyle(style);
                    Cell cell22 = row2.createCell(4);
                    cell22.setCellValue(formatStringtoStringDate(res.getData().split("\\.")[0], "yyyy-MM-dd HH:mm:ss", "dd/MM/yyyy HH:mm:ss"));
                    cell22.setCellStyle(style);
                    Cell cell33 = row2.createCell(5);
                    cell33.setCellValue(res.getTill());
                    cell33.setCellStyle(style);
                    Cell cell44 = row2.createCell(6);
                    cell44.setCellValue(res.getUser());
                    cell44.setCellStyle(style);
                    Cell cell66 = row2.createCell(7);
                    cell66.setCellValue(formatType(res.getTipotr()) + ca);
                    cell66.setCellStyle(style);
                    Cell cell77 = row2.createCell(8, NUMERIC);
                    cell77.setCellValue(fd(res.getTotal()));
                    cell77.setCellStyle(cellStylenum);
                    Cell cell88 = row2.createCell(9, NUMERIC);
                    cell88.setCellValue(fd(res.getPay()));
                    cell88.setCellStyle(cellStylenum);
                    Cell cell99 = row2.createCell(10, NUMERIC);
                    cell99.setCellValue(fd(res.getCommission()));
                    cell99.setCellStyle(cellStylenum);
                    Cell cell100 = row2.createCell(11, NUMERIC);
                    cell100.setCellValue(fd(res.getSpread_total()));
                    cell100.setCellStyle(cellStylenum);
                    Cell cell110 = row2.createCell(12);
                    cell110.setCellValue(res.getFa_number());
                    cell110.setCellStyle(style);
                    Cell cell120 = row2.createCell(13);
                    cell120.setCellValue(res.getCn_number());
                    cell120.setCellStyle(style);
                    Cell cell130 = row2.createCell(14);
                    cell130.setCellValue(dt_del);
                    cell130.setCellStyle(style);
                    Cell cell1301 = row2.createCell(15);
                    cell1301.setCellValue(cl.getCognome().toUpperCase());
                    cell1301.setCellStyle(style);
                    Cell cell1302 = row2.createCell(16);
                    cell1302.setCellValue(cl.getNome().toUpperCase());
                    cell1302.setCellStyle(style);
                    Cell cell1303 = row2.createCell(17);
                    cell1303.setCellValue(cl.getCodfisc().toUpperCase());
                    cell1303.setCellStyle(style);
                    Cell cell1303a = row2.createCell(18);
                    cell1303a.setCellValue(pos);
                    cell1303a.setCellStyle(style);
                    //                String loy = Engine.query_LOY_transaction(res.getCod(), "central", fil);
                    if (db2.getC() == null) {
                        db2 = new Db_Master();
                    }
                    String loy = db2.query_LOY_transaction(res.getCod());
                    if (loy == null) {
                        loy = "";
                    }
                    Cell cell1303b = row2.createCell(19);
                    cell1303b.setCellValue(loy);
                    cell1303b.setCellStyle(style);
                }
                db2.closeDB();
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
                try(FileOutputStream fileOut = new FileOutputStream(outputfile)){
                wb.write(fileOut);
                fileOut.close();
            }
        }

            String base64 = new String(encodeBase64(readFileToByteArray(outputfile)));
            outputfile.delete();
            return base64;
        } catch (IOException ex) {
            insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
        }
        return null;
    }

    /**
     *
     * @param outputfile
     * @param result
     * @return
     */
    public static String excel_kycList(File outputfile, ArrayList<Ch_transaction_doc> result) {
        try {
            try ( //File out = new File(pathout + outputfile);
                    Workbook wb = new XSSFWorkbook()) {
                Sheet sheet = wb.createSheet("KYC_list");
                XSSFCellStyle style = (XSSFCellStyle) wb.createCellStyle();
                style.setBorderBottom(THIN);
                style.setBorderTop(THIN);
                style.setBorderRight(THIN);
                style.setBorderLeft(THIN);
                XSSFCellStyle style1 = (XSSFCellStyle) wb.createCellStyle();
                style1.setBorderBottom(THIN);
                style1.setBorderTop(THIN);
                style1.setBorderRight(THIN);
                style1.setBorderLeft(THIN);
                style1.setAlignment(CENTER);
                XSSFFont font = (XSSFFont) wb.createFont();
                font.setFontName(FONT_ARIAL);
                font.setFontHeightInPoints((short) 12);
                font.setBold(true);
                //  font.setColor(HSSFColor.BLUE.index);
                style1.setFont(font);
                XSSFCellStyle style2 = (XSSFCellStyle) wb.createCellStyle();
                style2.setAlignment(CENTER);
                XSSFFont font2 = (XSSFFont) wb.createFont();
                font2.setFontName(FONT_ARIAL);
                font2.setFontHeightInPoints((short) 14);
                font2.setBold(true);
                style2.setFont(font2);
                Row row0 = sheet.createRow(0);
                Cell cell0 = row0.createCell(1);
                cell0.setCellStyle(style2);
                sheet.addMergedRegion(new CellRangeAddress(0, 0, 1, 9));
                cell0.setCellValue("KYC List");
                Row row = sheet.createRow(1);
                Cell cellb = row.createCell(1);
                cellb.setCellValue("Branch ID");
                cellb.setCellStyle(style1);
                Cell cell = row.createCell(2);
                cell.setCellValue("Client Surname");
                cell.setCellStyle(style1);
                Cell cell2 = row.createCell(3);
                cell2.setCellValue("Client Name");
                cell2.setCellStyle(style1);
                Cell cell2a = row.createCell(4);
                cell2a.setCellValue("Motivation");
                cell2a.setCellStyle(style1);
                Cell cell3 = row.createCell(5);
                cell3.setCellValue("Document Date");
                cell3.setCellStyle(style1);
                for (int i = 0; i < result.size(); i++) {
                    Row row2 = sheet.createRow(i + 3);
                    Ch_transaction_doc res = (Ch_transaction_doc) result.get(i);
                    Ch_transaction tr = query_transaction_ch(res.getCodtr());
                    Db_Master dbm = new Db_Master();
                    Client cl = dbm.query_Client_transaction(res.getCodtr(), res.getClient());
                    dbm.closeDB();
                    Cell cell1b = row2.createCell(1);
                    cell1b.setCellValue(tr.getFiliale());
                    cell1b.setCellStyle(style);
                    Cell cell1 = row2.createCell(2);
                    cell1.setCellValue(cl.getCognome().toUpperCase());
                    cell1.setCellStyle(style);
                    Cell cell22 = row2.createCell(3);
                    cell22.setCellValue(cl.getNome().toUpperCase());
                    cell22.setCellStyle(style);
                    String type;
                    if (cl.getPep().equalsIgnoreCase("YES")) {
                        type = "PEP";
                    } else {
                        type = "THRESHOLD";
                    }
                    Cell cell22a = row2.createCell(4);
                    cell22a.setCellValue(type);
                    cell22a.setCellStyle(style);
                    Cell cell33 = row2.createCell(5);
                    cell33.setCellValue(formatStringtoStringDate_null(res.getData_load(), patternsqldate, patternnormdate));
                    cell33.setCellStyle(style);
                }
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
                try(FileOutputStream fileOut = new FileOutputStream(outputfile)){
                wb.write(fileOut);
                fileOut.close();
            }
        }

            String base64 = new String(encodeBase64(readFileToByteArray(outputfile)));
            outputfile.delete();
            return base64;
        } catch (Exception ex) {
            insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
        }
        return null;
    }

    private static final String formatdataCell = "#.#0,0";
//    private static final String formatdataCellNC = "#.0#,0";
    private static final String formatdataCelINT = "0";

    /**
     *
     * @param outputfile
     * @param result
     * @return
     */
    public static String excel_reportpending(File outputfile, ArrayList<String[]> result) {
        try {

            try (Workbook wb = new XSSFWorkbook()) {
                Sheet sheet = wb.createSheet("Unlock Pending Operations");
                XSSFCellStyle style = (XSSFCellStyle) wb.createCellStyle();
                style.setBorderBottom(THIN);
                style.setBorderTop(THIN);
                style.setBorderRight(THIN);
                style.setBorderLeft(THIN);
                XSSFCellStyle style1 = (XSSFCellStyle) wb.createCellStyle();
                style1.setBorderBottom(THIN);
                style1.setBorderTop(THIN);
                style1.setBorderRight(THIN);
                style1.setBorderLeft(THIN);
                style1.setAlignment(CENTER);
                XSSFFont font = (XSSFFont) wb.createFont();
                font.setFontName(FONT_ARIAL);
                font.setFontHeightInPoints((short) 12);
                font.setBold(true);
                //  font.setColor(HSSFColor.BLUE.index);
                style1.setFont(font);
                XSSFCellStyle style2 = (XSSFCellStyle) wb.createCellStyle();
                style2.setAlignment(CENTER);
                XSSFFont font2 = (XSSFFont) wb.createFont();
                font2.setFontName(FONT_ARIAL);
                font2.setFontHeightInPoints((short) 14);
                font2.setBold(true);
                style2.setFont(font2);
                Row row0 = sheet.createRow(0);
                Cell cell0 = row0.createCell(1);
                cell0.setCellStyle(style2);
                sheet.addMergedRegion(new CellRangeAddress(0, 0, 1, 3));
                cell0.setCellValue("Unlock Pending Operations");
                Row row = sheet.createRow(1);
                Cell cell = row.createCell(1);
                cell.setCellValue("Branch ID");
                cell.setCellStyle(style1);
                Cell cell2 = row.createCell(2);
                cell2.setCellValue("User ID");
                cell2.setCellStyle(style1);
                Cell cell3 = row.createCell(3);
                cell3.setCellValue("Date");
                cell3.setCellStyle(style1);
                for (int i = 0; i < result.size(); i++) {
                    Row row2 = sheet.createRow(i + 3);

                    Cell cell1 = row2.createCell(1);
                    cell1.setCellValue(result.get(i)[0]);
                    cell1.setCellStyle(style);
                    Cell cell22 = row2.createCell(2);
                    cell22.setCellValue(result.get(i)[1]);
                    cell22.setCellStyle(style);
                    Cell cell33 = row2.createCell(3);
                    cell33.setCellValue(result.get(i)[2]);
                    cell33.setCellStyle(style);

                }
                sheet.autoSizeColumn(1);
                sheet.autoSizeColumn(2);
                sheet.autoSizeColumn(3);
                try(FileOutputStream fileOut = new FileOutputStream(outputfile)){
                wb.write(fileOut);
                fileOut.close();
            }
        }

            String base64 = new String(encodeBase64(readFileToByteArray(outputfile)));
            outputfile.delete();
            return base64;
        } catch (Exception ex) {
            insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
        }
        return null;
    }

    /**
     *
     * @param outputfile
     * @param result
     * @return
     */
    public static String excel_transactionnc_list(File outputfile, ArrayList<NC_transaction> result) {
        try {

            Db_Master db = new Db_Master();
            ArrayList<Branch> libr = db.list_branch();
            ArrayList<String[]> country = db.country();
            ArrayList<NC_category> array_nc_cat = db.list_ALL_nc_category();
            ArrayList<NC_causal> array_nc_caus = db.list_nc_causal_all();
            ArrayList<String[]> array_nc_descr = db.list_nc_descr();
            boolean iscentral = db.getCodLocal(false)[0].equals("000");

            db.closeDB();

            try ( //ArrayList<String[]> array_kind = Engine.list_all_kind(filiale);
                    //File out = new File(pathout + outputfile);
                    Workbook wb = new XSSFWorkbook()) {
                Sheet sheet = wb.createSheet("TransactionNC_list");
                XSSFCellStyle cellStylenum = (XSSFCellStyle) wb.createCellStyle();
                XSSFDataFormat hssfDataFormat = (XSSFDataFormat) wb.createDataFormat();
                cellStylenum.setDataFormat(hssfDataFormat.getFormat("#,#.00"));
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
                XSSFCellStyle style = (XSSFCellStyle) wb.createCellStyle();
                style.setBorderBottom(THIN);
                style.setBorderTop(THIN);
                style.setBorderRight(THIN);
                style.setBorderLeft(THIN);
                XSSFCellStyle style1 = (XSSFCellStyle) wb.createCellStyle();
                style1.setBorderBottom(THIN);
                style1.setBorderTop(THIN);
                style1.setBorderRight(THIN);
                style1.setBorderLeft(THIN);
                style1.setAlignment(CENTER);
                XSSFFont font = (XSSFFont) wb.createFont();
                font.setFontName(FONT_ARIAL);
                font.setFontHeightInPoints((short) 12);
                font.setBold(true);
                //  font.setColor(HSSFColor.BLUE.index);
                style1.setFont(font);
                XSSFCellStyle style2 = (XSSFCellStyle) wb.createCellStyle();
                style2.setAlignment(CENTER);
                XSSFFont font2 = (XSSFFont) wb.createFont();
                font2.setFontName(FONT_ARIAL);
                font2.setFontHeightInPoints((short) 14);
                font2.setBold(true);
                style2.setFont(font2);
                Row row0 = sheet.createRow(0);
                Cell cell0 = row0.createCell(1);
                cell0.setCellStyle(style2);
                sheet.addMergedRegion(new CellRangeAddress(0, 0, 1, 9));
                cell0.setCellValue("Transaction No Change List");
                Row row = sheet.createRow(1);
                Cell cell13 = row.createCell(1);
                cell13.setCellValue("Status");
                cell13.setCellStyle(style1);
                Cell cellb = row.createCell(2);
                cellb.setCellValue("Branch ID");
                cellb.setCellStyle(style1);
                Cell cell = row.createCell(3);
                cell.setCellValue("Code");
                cell.setCellStyle(style1);
                Cell cell2 = row.createCell(4);
                cell2.setCellValue("Date");
                cell2.setCellStyle(style1);
                Cell cell3 = row.createCell(5);
                cell3.setCellValue("Till");
                cell3.setCellStyle(style1);
                Cell cell4 = row.createCell(6);
                cell4.setCellValue("User");
                cell4.setCellStyle(style1);
                Cell cell6 = row.createCell(7);
                cell6.setCellValue("Total");
                cell6.setCellStyle(style1);
                Cell cell7 = row.createCell(8);
                cell7.setCellValue("Quantity");
                cell7.setCellStyle(style1);
                Cell cell8 = row.createCell(9);
                cell8.setCellValue("Price");
                cell8.setCellStyle(style1);
                Cell cell9 = row.createCell(10);
                cell9.setCellValue("Fee");
                cell9.setCellStyle(style1);
                Cell cell9a = row.createCell(11);
                cell9a.setCellValue("Category");
                cell9a.setCellStyle(style1);
                Cell cell10 = row.createCell(12);
                cell10.setCellValue("Causal");
                cell10.setCellStyle(style1);
                Cell cell11 = row.createCell(13);
                cell11.setCellValue("Client");
                cell11.setCellStyle(style1);
                Cell cell12 = row.createCell(14);
                cell12.setCellValue("Country");
                cell12.setCellStyle(style1);
                Cell cell121 = row.createCell(15);
                cell121.setCellValue("Type");
                cell121.setCellStyle(style1);
                Cell cell111 = row.createCell(16);
                cell111.setCellValue("Pos/Bank Account");
                cell111.setCellStyle(style1);
                cell111 = row.createCell(17);
                cell111.setCellValue("Fix Com");
                cell111.setCellStyle(style1);
                cell111 = row.createCell(18);
                cell111.setCellValue("perc Com");
                cell111.setCellStyle(style1);
                cell111 = row.createCell(19);
                cell111.setCellValue("Round");
                cell111.setCellStyle(style1);
                cell111 = row.createCell(20);
                cell111.setCellValue("Total Com");
                cell111.setCellStyle(style1);
                cell111 = row.createCell(21);
                cell111.setCellValue("Estimate Commissions");
                cell111.setCellStyle(style1);
                cell111 = row.createCell(22);
                cell111.setCellValue("GM");
                cell111.setCellStyle(style1);
                cell111 = row.createCell(23);
                cell111.setCellValue("Branch Description");
                cell111.setCellStyle(style1);
                cell111 = row.createCell(24);
                cell111.setCellValue("Loyalty Code");
                cell111.setCellStyle(style1);
                cell111 = row.createCell(25);
                cell111.setCellValue("Phone number");
                cell111.setCellStyle(style1);
                cell111 = row.createCell(26);
                cell111.setCellValue("Email");
                cell111.setCellStyle(style1);
                cell111 = row.createCell(27);
                cell111.setCellValue("Authorization to the processing of personal data");
                cell111.setCellStyle(style1);
                for (int i = 0; i < result.size(); i++) {
                    Row row2 = sheet.createRow(i + 3);
                    NC_transaction res = (NC_transaction) result.get(i);
                    String q1 = (roundDoubleandFormat(fd(res.getQuantita()), 0));
                    String p1 = (res.getPrezzo());
                    String f1 = ("0.00");
                    String pos = res.getPos();
                    switch (res.getFg_tipo_transazione_nc()) {
                        case "1":
                            q1 = "1";
                            p1 = (res.getNetto());
                            f1 = (res.getCommissione());
                            break;
                        case "3":
                            q1 = (roundDoubleandFormat(fd(res.getRicevuta()), 0));
                            p1 = (res.getQuantita());
                            break;
                        case "21":
                            String comm;
                            if (fd(res.getCommissione()) > 0) {
                                comm = res.getCommissione();
                            } else {
                                comm = res.getTi_ticket_fee();
                            }
                            if (res.getTotal().contains("-")) {
                                comm = "-" + comm;
                            }
                            f1 = (comm);
                            break;
                        default:
                            break;
                    }
                    Cell cell130 = row2.createCell(1);
                    cell130.setCellValue(res.formatStatus_cru(res.getDel_fg()));
                    cell130.setCellStyle(style);
                    Cell cell1b = row2.createCell(2);
                    cell1b.setCellValue(res.getFiliale());
                    cell1b.setCellStyle(style);
                    Cell cell1 = row2.createCell(3);
                    cell1.setCellValue(res.getId());
                    cell1.setCellStyle(style);
                    Cell cell22 = row2.createCell(4);
                    cell22.setCellValue(formatStringtoStringDate(res.getData(), patternsqldate, patternnormdate));
                    cell22.setCellStyle(style);
                    Cell cell33 = row2.createCell(5);
                    cell33.setCellValue(res.getTill());
                    cell33.setCellStyle(style);
                    Cell cell44 = row2.createCell(6);
                    cell44.setCellValue(res.getUser());
                    cell44.setCellStyle(style);
                    Cell cell66 = row2.createCell(7, NUMERIC);
                    cell66.setCellValue(fd(res.getTotal()));
                    //                cell66.setCellValue(formatMysqltoDisplay(res.getTotal()));
                    cell66.setCellStyle(cellStylenum);
                    Cell cell77 = row2.createCell(8, NUMERIC);
                    cell77.setCellValue(fd(q1));
                    cell77.setCellStyle(cellStyleint);
                    Cell cell88 = row2.createCell(9, NUMERIC);
                    cell88.setCellValue(fd(p1));
                    //                cell66.setCellValue(formatMysqltoDisplay(res.getTotal()));
                    cell88.setCellStyle(cellStylenum);
                    Cell cell88a = row2.createCell(10, NUMERIC);
                    cell88a.setCellValue(fd(f1));
                    cell88a.setCellStyle(cellStylenum);
                    Cell cell99 = row2.createCell(11);
                    cell99.setCellValue(res.getGruppo_nc() + " - " + formatALNC_category(res.getGruppo_nc(), array_nc_cat));
                    cell99.setCellStyle(style);
                    Cell cell100 = row2.createCell(12);
                    cell100.setCellValue(res.getCausale_nc() + " - " + formatALNC_causal(res.getCausale_nc(), array_nc_caus));
                    cell100.setCellStyle(style);
                    Cell cell110 = row2.createCell(13);
                    cell110.setCellValue(res.getCl_cognome() + " " + res.getCl_nome());
                    cell110.setCellStyle(style);
                    Cell cell120 = row2.createCell(14);
                    cell120.setCellValue(formatAL(res.getCl_nazione(), country, 1));
                    cell120.setCellStyle(style);
                    Cell cell1201 = row2.createCell(15);
                    cell1201.setCellValue(formatALNC_causal_ncde(res.getCausale_nc(), array_nc_caus, array_nc_descr));
                    cell1201.setCellStyle(style);
                    Cell cell1202 = row2.createCell(16);
                    cell1202.setCellValue(pos);
                    cell1202.setCellStyle(style);
                    Ch_transaction cht = null;
                    if (!res.getCh_transaction().equals("-")) {
                        Db_Master db1 = new Db_Master();
                        cht = db1.query_transaction_ch_reportNC(res.getCh_transaction());
//                    cht = db1.query_transaction_ch(res.getCh_transaction());
                        db1.closeDB();
                    }
                    if (cht != null) {
                        double GM = fd(cht.getCommission()) + parseDoubleR(cht.getRound()) + fd(cht.getSpread_total());
                        double stimaCO = parseDoubleR(res.getTotal()) * (parseDoubleR(cht.getCommission()) + parseDoubleR(cht.getRound())) / parseDoubleR(cht.getPay());
                        double stimaGM = (GM * parseDoubleR(res.getTotal())) / parseDoubleR(cht.getPay());
                        cell1202 = row2.createCell(17, NUMERIC);
                        cell1202.setCellValue(fd(cht.getFix()));
                        cell1202.setCellStyle(cellStylenum);
                        cell1202 = row2.createCell(18, NUMERIC);
                        cell1202.setCellValue(fd(cht.getCom()));
                        cell1202.setCellStyle(cellStylenum);
                        cell1202 = row2.createCell(19, NUMERIC);
                        cell1202.setCellValue(fd(cht.getRound()));
                        cell1202.setCellStyle(cellStylenum);
                        cell1202 = row2.createCell(20, NUMERIC);
                        cell1202.setCellValue(fd(cht.getCommission()));
                        cell1202.setCellStyle(cellStylenum);
                        cell1202 = row2.createCell(21, NUMERIC);
                        cell1202.setCellValue(stimaCO);
                        cell1202.setCellStyle(cellStylenum);
                        cell1202 = row2.createCell(22, NUMERIC);
                        cell1202.setCellValue(stimaGM);
                        cell1202.setCellStyle(cellStylenum);
                    } else {
                        cell1202 = row2.createCell(17);
                        cell1202.setCellValue("");
                        cell1202.setCellStyle(style);
                        cell1202 = row2.createCell(18);
                        cell1202.setCellValue("");
                        cell1202.setCellStyle(style);
                        cell1202 = row2.createCell(19);
                        cell1202.setCellValue("");
                        cell1202.setCellStyle(style);
                        cell1202 = row2.createCell(20);
                        cell1202.setCellValue("");
                        cell1202.setCellStyle(style);
                        cell1202 = row2.createCell(21);
                        cell1202.setCellValue("");
                        cell1202.setCellStyle(style);
                        cell1202 = row2.createCell(22);
                        cell1202.setCellValue("");
                        cell1202.setCellStyle(style);
                    }
                    cell1202 = row2.createCell(23);
                    cell1202.setCellValue(get_Branch(res.getFiliale(), libr).getDe_branch().toUpperCase());
                    cell1202.setCellStyle(style);
                    String loy;
                    if (iscentral) {
                        loy = query_LOY_transaction(res.getCod(), null, "000");
                    } else {
                        loy = query_LOY_transaction(res.getCod(), "central", "010");
                    }
                    if (loy == null) {
                        loy = "";
                    }
                    cell1202 = row2.createCell(24);
                    cell1202.setCellValue(loy);
                    cell1202.setCellStyle(style);
                    cell1202 = row2.createCell(25);
                    cell1202.setCellValue(res.getCl_telefono());
                    cell1202.setCellStyle(style);
                    cell1202 = row2.createCell(26);
                    cell1202.setCellValue(res.getCl_email());
                    cell1202.setCellStyle(style);
                    cell1202 = row2.createCell(27);
                    cell1202.setCellValue("");
                    cell1202.setCellStyle(style);
                }
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
                try(FileOutputStream fileOut = new FileOutputStream(outputfile)){
                wb.write(fileOut);
                fileOut.close();
            }
        }

            String base64 = new String(encodeBase64(readFileToByteArray(outputfile)));
            outputfile.delete();
            return base64;
        } catch (Exception ex) {
            insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
        }
        return null;
    }

    /**
     *
     * @param outputfile
     * @param result
     * @return
     */
    public static String nc_category_cat(File outputfile, ArrayList<NC_category> result) {
        try {
            ArrayList<String[]> array_nc_kind = nc_kind();

            try ( //File out = new File(pathout + outputfile);
                    Workbook wb = new XSSFWorkbook()) {
                Sheet sheet = wb.createSheet("NoChangeCategoryList");
                XSSFCellStyle style = (XSSFCellStyle) wb.createCellStyle();
                style.setBorderBottom(THIN);
                style.setBorderTop(THIN);
                style.setBorderRight(THIN);
                style.setBorderLeft(THIN);
                XSSFCellStyle style1 = (XSSFCellStyle) wb.createCellStyle();
                style1.setBorderBottom(THIN);
                style1.setBorderTop(THIN);
                style1.setBorderRight(THIN);
                style1.setBorderLeft(THIN);
                style1.setAlignment(CENTER);
                XSSFFont font = (XSSFFont) wb.createFont();
                font.setFontName(FONT_ARIAL);
                font.setFontHeightInPoints((short) 12);
                font.setBold(true);
                //  font.setColor(HSSFColor.BLUE.index);
                style1.setFont(font);
                XSSFCellStyle style2 = (XSSFCellStyle) wb.createCellStyle();
                style2.setAlignment(CENTER);
                XSSFFont font2 = (XSSFFont) wb.createFont();
                font2.setFontName(FONT_ARIAL);
                font2.setFontHeightInPoints((short) 14);
                font2.setBold(true);
                style2.setFont(font2);
                Row row0 = sheet.createRow(0);
                Cell cell0 = row0.createCell(1);
                cell0.setCellStyle(style2);
                sheet.addMergedRegion(new CellRangeAddress(0, 0, 1, 9));
                cell0.setCellValue("No Change Category List");
                Row row = sheet.createRow(1);
                Cell cell = row.createCell(1);
                cell.setCellValue("Code");
                cell.setCellStyle(style1);
                Cell cell2 = row.createCell(2);
                cell2.setCellValue("Description");
                cell2.setCellStyle(style1);
                Cell cell3 = row.createCell(3);
                cell3.setCellValue("Kind");
                cell3.setCellStyle(style1);
                Cell cell4 = row.createCell(4);
                cell4.setCellValue("Price");
                cell4.setCellStyle(style1);
                Cell cell5 = row.createCell(5);
                cell5.setCellValue("Status");
                cell5.setCellStyle(style1);
                Cell cell6 = row.createCell(6);
                cell6.setCellValue("Accountign Code #1");
                cell6.setCellStyle(style1);
                Cell cell7 = row.createCell(7);
                cell7.setCellValue("Accountign Code #2");
                cell7.setCellStyle(style1);
                Cell cell8 = row.createCell(8);
                cell8.setCellValue("Ticket Fee");
                cell8.setCellStyle(style1);
                Cell cell9 = row.createCell(9);
                cell9.setCellValue("Currency Percent");
                cell9.setCellStyle(style1);
                Cell cell10 = row.createCell(10);
                cell10.setCellValue("Max Ticket");
                cell10.setCellStyle(style1);
                Cell cell11 = row.createCell(11);
                cell11.setCellValue("Edit Ticket Fee");
                cell11.setCellStyle(style1);
                Cell cell12 = row.createCell(12);
                cell12.setCellValue("Receipt Header");
                cell12.setCellStyle(style1);
                Cell cell13 = row.createCell(13);
                cell13.setCellValue("Receipt Test");
                cell13.setCellStyle(style1);
                for (int i = 0; i < result.size(); i++) {
                    Row row2 = sheet.createRow(i + 3);
                    NC_category res = (NC_category) result.get(i);
                    String tty = "";
                    if (res.getTicket_fee_type().equals("1")) {
                        tty = "checked";
                    }
                    String editfee = "Disabled";
                    if (res.getTicket_enabled().equals("1")) {
                        editfee = "Enabled";
                    }
                    Cell cell1 = row2.createCell(1);
                    cell1.setCellValue(res.getGruppo_nc());
                    cell1.setCellStyle(style);
                    Cell cell22 = row2.createCell(2);
                    cell22.setCellValue(res.getDe_gruppo_nc());
                    cell22.setCellStyle(style);
                    Cell cell33 = row2.createCell(3);
                    cell33.setCellValue(formatAL(res.getFg_tipo_transazione_nc(), array_nc_kind, 1));
                    cell33.setCellStyle(style);
                    Cell cell44 = row2.createCell(4);
                    cell44.setCellValue(formatMysqltoDisplay(res.getIp_prezzo_nc()));
                    cell44.setCellStyle(style);
                    Cell cell55 = row2.createCell(5);
                    cell55.setCellValue(res.formatStatus_cru(res.getAnnullato()));
                    cell55.setCellStyle(style);
                    Cell cell66 = row2.createCell(6);
                    cell66.setCellValue(res.getConto_coge_01());
                    cell66.setCellStyle(style);
                    Cell cell77 = row2.createCell(7);
                    cell77.setCellValue(res.getConto_coge_02());
                    cell77.setCellStyle(style);
                    Cell cell88 = row2.createCell(8);
                    cell88.setCellValue(res.getTicket_fee());
                    cell88.setCellStyle(style);
                    Cell cell99 = row2.createCell(9);
                    cell99.setCellValue(tty);
                    cell99.setCellStyle(style);
                    Cell cell100 = row2.createCell(10);
                    cell100.setCellValue(res.getMax_ticket());
                    cell100.setCellStyle(style);
                    Cell cell110 = row2.createCell(11);
                    cell110.setCellValue(editfee);
                    cell110.setCellStyle(style);
                    Cell cell120 = row2.createCell(12);
                    cell120.setCellValue(res.getDe_scontrino());
                    cell120.setCellStyle(style);
                    Cell cell130 = row2.createCell(13);
                    cell130.setCellValue(res.getDe_riga());
                    cell130.setCellStyle(style);
                }
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
                try(FileOutputStream fileOut = new FileOutputStream(outputfile)){
                wb.write(fileOut);
                fileOut.close();
            }
        }

            String base64 = new String(encodeBase64(readFileToByteArray(outputfile)));
            outputfile.delete();
            return base64;
        } catch (Exception ex) {
            insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
        }
        return null;
    }

    /**
     *
     * @param outputfile
     * @param result
     * @return
     */
    public static String nc_viewbranch(File outputfile, ArrayList<String[]> result) {
        try {

            try ( //File out = new File(pathout + outputfile);
                    Workbook wb = new XSSFWorkbook()) {
                Sheet sheet = wb.createSheet("NoChangeviewbranchList");
                XSSFFont font3 = (XSSFFont) wb.createFont();
                font3.setFontName(FONT_ARIAL);
                font3.setFontHeightInPoints((short) 12);
                font3.setBold(true);
                XSSFCellStyle style3 = (XSSFCellStyle) wb.createCellStyle();
                style3.setFont(font3);
                XSSFCellStyle style = (XSSFCellStyle) wb.createCellStyle();
                style.setBorderBottom(THIN);
                style.setBorderTop(THIN);
                style.setBorderRight(THIN);
                style.setBorderLeft(THIN);
                XSSFCellStyle style1 = (XSSFCellStyle) wb.createCellStyle();
                style1.setBorderBottom(THIN);
                style1.setBorderTop(THIN);
                style1.setBorderRight(THIN);
                style1.setBorderLeft(THIN);
                style1.setAlignment(CENTER);
                XSSFFont font = (XSSFFont) wb.createFont();
                font.setFontName(FONT_ARIAL);
                font.setFontHeightInPoints((short) 12);
                font.setBold(true);
                //  font.setColor(HSSFColor.BLUE.index);
                style1.setFont(font);
                XSSFCellStyle style2 = (XSSFCellStyle) wb.createCellStyle();
                style2.setAlignment(CENTER);
                XSSFFont font2 = (XSSFFont) wb.createFont();
                font2.setFontName(FONT_ARIAL);
                font2.setFontHeightInPoints((short) 14);
                font2.setBold(true);
                style2.setFont(font2);
                Row row0 = sheet.createRow(0);
                Cell cell0 = row0.createCell(1);
                cell0.setCellStyle(style2);
                sheet.addMergedRegion(new CellRangeAddress(0, 0, 1, 9));
                cell0.setCellValue("No Change View Branch List");
                Row row = sheet.createRow(1);
                Cell cell = row.createCell(1);
                cell.setCellValue("Category");
                cell.setCellStyle(style1);
                Cell cell2 = row.createCell(2);
                cell2.setCellValue("Causal");
                cell2.setCellStyle(style1);
                Cell cell3 = row.createCell(3);
                cell3.setCellValue("Description");
                cell3.setCellStyle(style1);
                Cell cell4 = row.createCell(4);
                cell4.setCellValue("Kind");
                cell4.setCellStyle(style1);
                Cell cell5 = row.createCell(5);
                cell5.setCellValue("Price");
                cell5.setCellStyle(style1);
                Cell cell6 = row.createCell(6);
                cell6.setCellValue("Status");
                cell6.setCellStyle(style1);
                for (int i = 0; i < result.size(); i++) {
                    Row row2 = sheet.createRow(i + 3);
                    String[] res = result.get(i);

                    String status;
                    if (res[5].contains("Disabled")) {
                        status = "Disabled";
                    } else {
                        status = "Enabled";
                    }
                    boolean bold = false;
                    if (res[1].equals("")) {
                        bold = true;
                    }

                    Cell cell1 = row2.createCell(1);

                    cell1.setCellValue(res[0]);

                    if (bold) {
                        cell1.setCellStyle(style3);
                    } else {
                        cell1.setCellStyle(style);
                    }

                    Cell cell22 = row2.createCell(2);
                    cell22.setCellValue(res[1]);
                    cell22.setCellStyle(style);

                    Cell cell33 = row2.createCell(3);
                    cell33.setCellValue(res[2]);
                    if (bold) {
                        cell33.setCellStyle(style3);
                    } else {
                        cell33.setCellStyle(style);
                    }

                    Cell cell44 = row2.createCell(4);
                    cell44.setCellValue(res[3]);
                    if (bold) {
                        cell44.setCellStyle(style3);
                    } else {
                        cell44.setCellStyle(style);
                    }

                    Cell cell55 = row2.createCell(5);
                    cell55.setCellValue(res[4]);
                    if (bold) {
                        cell55.setCellStyle(style3);
                    } else {
                        cell55.setCellStyle(style);
                    }

                    Cell cell66 = row2.createCell(6);
                    cell66.setCellValue(status);
                    if (bold) {
                        cell66.setCellStyle(style3);
                    } else {
                        cell66.setCellStyle(style);
                    }

                }
                sheet.autoSizeColumn(1);
                sheet.autoSizeColumn(2);
                sheet.autoSizeColumn(3);
                sheet.autoSizeColumn(4);
                sheet.autoSizeColumn(5);
                sheet.autoSizeColumn(6);
                try(FileOutputStream fileOut = new FileOutputStream(outputfile)){
                wb.write(fileOut);
                fileOut.close();
            }}

            String base64 = new String(encodeBase64(readFileToByteArray(outputfile)));
            outputfile.delete();
            return base64;
        } catch (Exception ex) {
            insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
        }
        return null;
    }

    /**
     *
     * @param outputfile
     * @param result
     * @return
     */
    public static String es_paymat(File outputfile, ArrayList<Taglio> result) {
        try {

            try ( //File out = new File(pathout + outputfile);
                    Workbook wb = new XSSFWorkbook()) {
                Sheet sheet = wb.createSheet("ExternalServicesPaymatList");
                XSSFFont font3 = (XSSFFont) wb.createFont();
                font3.setFontName(FONT_ARIAL);
                font3.setFontHeightInPoints((short) 12);
                font3.setBold(true);
                XSSFCellStyle style3 = (XSSFCellStyle) wb.createCellStyle();
                style3.setFont(font3);
                XSSFCellStyle style = (XSSFCellStyle) wb.createCellStyle();
                style.setBorderBottom(THIN);
                style.setBorderTop(THIN);
                style.setBorderRight(THIN);
                style.setBorderLeft(THIN);
                XSSFCellStyle style1 = (XSSFCellStyle) wb.createCellStyle();
                style1.setBorderBottom(THIN);
                style1.setBorderTop(THIN);
                style1.setBorderRight(THIN);
                style1.setBorderLeft(THIN);
                style1.setAlignment(CENTER);
                XSSFFont font = (XSSFFont) wb.createFont();
                font.setFontName(FONT_ARIAL);
                font.setFontHeightInPoints((short) 12);
                font.setBold(true);
                //  font.setColor(HSSFColor.BLUE.index);
                style1.setFont(font);
                XSSFCellStyle style2 = (XSSFCellStyle) wb.createCellStyle();
                style2.setAlignment(CENTER);
                XSSFFont font2 = (XSSFFont) wb.createFont();
                font2.setFontName(FONT_ARIAL);
                font2.setFontHeightInPoints((short) 14);
                font2.setBold(true);
                style2.setFont(font2);
                Row row0 = sheet.createRow(0);
                Cell cell0 = row0.createCell(1);
                cell0.setCellStyle(style2);
                sheet.addMergedRegion(new CellRangeAddress(0, 0, 1, 9));
                cell0.setCellValue("External Services Paymat List");
                Row row = sheet.createRow(1);
                Cell cell = row.createCell(1);
                cell.setCellValue("Brand");
                cell.setCellStyle(style1);
                Cell cell2 = row.createCell(2);
                cell2.setCellValue("Description");
                cell2.setCellStyle(style1);
                Cell cell3 = row.createCell(3);
                cell3.setCellValue("Code");
                cell3.setCellStyle(style1);
                for (int i = 0; i < result.size(); i++) {
                    Row row2 = sheet.createRow(i + 3);
                    Taglio res = result.get(i);

                    String tip = res.getTipologia();
                    if (res.getTipologia().equals("0")) {
                        tip = "";
                    }

                    Cell cell1 = row2.createCell(1);
                    cell1.setCellValue(res.getBrand());
                    cell1.setCellStyle(style);

                    Cell cell22 = row2.createCell(2);
                    cell22.setCellValue(res.getDescrizione() + " " + tip);
                    cell22.setCellStyle(style);

                    Cell cell33 = row2.createCell(3);
                    cell33.setCellValue(res.getCodiceTaglio());
                    cell33.setCellStyle(style);

                }
                sheet.autoSizeColumn(1);
                sheet.autoSizeColumn(2);
                sheet.autoSizeColumn(3);
                try(FileOutputStream fileOut = new FileOutputStream(outputfile)){
                wb.write(fileOut);
                fileOut.close();
            }
        }

            String base64 = new String(encodeBase64(readFileToByteArray(outputfile)));
            outputfile.delete();
            return base64;
        } catch (Exception ex) {
            insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
        }
        return null;
    }

    /**
     *
     * @param outputfile
     * @param result
     * @return
     */
    public static String nc_category_cas(File outputfile, ArrayList<NC_causal> result) {
        try {

            ArrayList<String[]> array_nc_kind = nc_kind();

            try ( //File out = new File(pathout + outputfile);
                    Workbook wb = new XSSFWorkbook()) {
                Sheet sheet = wb.createSheet("NoChangeCausalist");
                XSSFCellStyle style = (XSSFCellStyle) wb.createCellStyle();
                style.setBorderBottom(THIN);
                style.setBorderTop(THIN);
                style.setBorderRight(THIN);
                style.setBorderLeft(THIN);
                XSSFCellStyle style1 = (XSSFCellStyle) wb.createCellStyle();
                style1.setBorderBottom(THIN);
                style1.setBorderTop(THIN);
                style1.setBorderRight(THIN);
                style1.setBorderLeft(THIN);
                style1.setAlignment(CENTER);
                XSSFFont font = (XSSFFont) wb.createFont();
                font.setFontName(FONT_ARIAL);
                font.setFontHeightInPoints((short) 12);
                font.setBold(true);
                //  font.setColor(HSSFColor.BLUE.index);
                style1.setFont(font);
                XSSFCellStyle style2 = (XSSFCellStyle) wb.createCellStyle();
                style2.setAlignment(CENTER);
                XSSFFont font2 = (XSSFFont) wb.createFont();
                font2.setFontName(FONT_ARIAL);
                font2.setFontHeightInPoints((short) 14);
                font2.setBold(true);
                style2.setFont(font2);
                Row row0 = sheet.createRow(0);
                Cell cell0 = row0.createCell(1);
                cell0.setCellStyle(style2);
                sheet.addMergedRegion(new CellRangeAddress(0, 0, 1, 9));
                cell0.setCellValue("No Change Causal List");
                Row row = sheet.createRow(1);
                Cell cell = row.createCell(1);
                cell.setCellValue("Code");
                cell.setCellStyle(style1);
                Cell cell2 = row.createCell(2);
                cell2.setCellValue("Description");
                cell2.setCellStyle(style1);
                Cell cell3 = row.createCell(3);
                cell3.setCellValue("Price");
                cell3.setCellStyle(style1);
                Cell cell4 = row.createCell(4);
                cell4.setCellValue("Status");
                cell4.setCellStyle(style1);
                Cell cell5 = row.createCell(5);
                cell5.setCellValue("Kind");
                cell5.setCellStyle(style1);
                Cell cell6 = row.createCell(6);
                cell6.setCellValue("Category");
                cell6.setCellStyle(style1);
                Cell cell7 = row.createCell(7);
                cell7.setCellValue("Ticket Fee");
                cell7.setCellStyle(style1);
                Cell cell8 = row.createCell(8);
                cell8.setCellValue("Max Ticket");
                cell8.setCellStyle(style1);
                Cell cell9 = row.createCell(9);
                cell9.setCellValue("External Services");
                cell9.setCellStyle(style1);
                Cell cell10 = row.createCell(10);
                cell10.setCellValue("Bonus");
                cell10.setCellStyle(style1);
                Cell cell11 = row.createCell(11);
                cell11.setCellValue("Print Receipt");
                cell11.setCellStyle(style1);
                for (int i = 0; i < result.size(); i++) {
                    Row row2 = sheet.createRow(i + 3);
                    NC_causal res = (NC_causal) result.get(i);
                    NC_category ncc = getNC_category(res.getGruppo_nc());
                    String cat = ncc.getDe_gruppo_nc();
                    String bns = "Disabled";
                    if (res.getBonus().equals("1")) {
                        bns = "Enabled";
                    }
                    String btc = "Disabled";
                    if (res.getFg_batch().equals("1")) {
                        btc = "Enabled";
                    }
                    String sct = "Disabled";
                    if (res.getFg_scontrino().equals("1")) {
                        sct = "Enabled";
                    }
                    String pm = "Disabled";
                    if (res.getPaymat().equals("1")) {
                        pm = "Enabled";
                    }
                    Cell cell1 = row2.createCell(1);
                    cell1.setCellValue(res.getCausale_nc());
                    cell1.setCellStyle(style);
                    Cell cell22 = row2.createCell(2);
                    cell22.setCellValue(res.getDe_causale_nc());
                    cell22.setCellStyle(style);
                    Cell cell33 = row2.createCell(3);
                    cell33.setCellValue(formatMysqltoDisplay(res.getIp_prezzo_nc()));
                    cell33.setCellStyle(style);
                    Cell cell44 = row2.createCell(4);
                    cell44.setCellValue(res.formatStatus_cru(res.getAnnullato()));
                    cell44.setCellStyle(style);
                    Cell cell55 = row2.createCell(5);
                    cell55.setCellValue(formatAL(res.getFg_tipo_transazione_nc(), array_nc_kind, 1));
                    cell55.setCellStyle(style);
                    Cell cell66 = row2.createCell(6);
                    cell66.setCellValue(cat);
                    cell66.setCellStyle(style);
                    Cell cell77 = row2.createCell(7);
                    cell77.setCellValue(res.getTicket_fee());
                    cell77.setCellStyle(style);
                    Cell cell88 = row2.createCell(8);
                    cell88.setCellValue(res.getMax_ticket());
                    cell88.setCellStyle(style);
                    Cell cell99 = row2.createCell(9);
                    cell99.setCellValue(pm);
                    cell99.setCellStyle(style);
                    Cell cell100 = row2.createCell(10);
                    cell100.setCellValue(bns);
                    cell100.setCellStyle(style);
                    Cell cell110 = row2.createCell(11);
                    cell110.setCellValue(sct);
                    cell110.setCellStyle(style);
                }
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
                try(FileOutputStream fileOut = new FileOutputStream(outputfile)){
                wb.write(fileOut);
                fileOut.close();
            }
        }

            String base64 = new String(encodeBase64(readFileToByteArray(outputfile)));
            outputfile.delete();
            return base64;
        } catch (Exception ex) {
            insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
        }
        return null;
    }

    /**
     *
     * @param outputfile
     * @param result
     * @return
     */
    public static String excel_it_list(File outputfile, ArrayList<IT_change> result) {
        try {

            try ( //File out = new File(pathout + outputfile);
                    Workbook wb = new XSSFWorkbook()) {
                Sheet sheet = wb.createSheet("InternalTransfer_list");
                XSSFCellStyle style = (XSSFCellStyle) wb.createCellStyle();
                style.setBorderBottom(THIN);
                style.setBorderTop(THIN);
                style.setBorderRight(THIN);
                style.setBorderLeft(THIN);
                XSSFCellStyle style1 = (XSSFCellStyle) wb.createCellStyle();
                style1.setBorderBottom(THIN);
                style1.setBorderTop(THIN);
                style1.setBorderRight(THIN);
                style1.setBorderLeft(THIN);
                style1.setAlignment(CENTER);
                XSSFFont font = (XSSFFont) wb.createFont();
                font.setFontName(FONT_ARIAL);
                font.setFontHeightInPoints((short) 12);
                font.setBold(true);
                //  font.setColor(HSSFColor.BLUE.index);
                style1.setFont(font);
                XSSFCellStyle style2 = (XSSFCellStyle) wb.createCellStyle();
                style2.setAlignment(CENTER);
                XSSFFont font2 = (XSSFFont) wb.createFont();
                font2.setFontName(FONT_ARIAL);
                font2.setFontHeightInPoints((short) 14);
                font2.setBold(true);
                style2.setFont(font2);
                Row row0 = sheet.createRow(0);
                Cell cell0 = row0.createCell(1);
                cell0.setCellStyle(style2);
                sheet.addMergedRegion(new CellRangeAddress(0, 0, 1, 9));
                cell0.setCellValue("Internal Transfer List");
                Row row = sheet.createRow(1);
                Cell cellb = row.createCell(1);
                cellb.setCellValue("Branch ID");
                cellb.setCellStyle(style1);
                Cell cell = row.createCell(2);
                cell.setCellValue("Code");
                cell.setCellStyle(style1);
                Cell cell2 = row.createCell(3);
                cell2.setCellValue("Date");
                cell2.setCellStyle(style1);
                Cell cell3 = row.createCell(4);
                cell3.setCellValue("Operator");
                cell3.setCellStyle(style1);
                Cell cell4 = row.createCell(5);
                cell4.setCellValue("From");
                cell4.setCellStyle(style1);
                Cell cell5 = row.createCell(6);
                cell5.setCellValue("Id open till - from");
                cell5.setCellStyle(style1);
                Cell cell6 = row.createCell(7);
                cell6.setCellValue("To");
                cell6.setCellStyle(style1);
                Cell cell7 = row.createCell(8);
                cell7.setCellValue("Id open till - to");
                cell7.setCellStyle(style1);
                Cell cell8 = row.createCell(9);
                cell8.setCellValue("Date Deleted");
                cell8.setCellStyle(style1);
                Cell cell9 = row.createCell(10);
                cell9.setCellValue("Status");
                cell9.setCellStyle(style1);
                for (int i = 0; i < result.size(); i++) {
                    Row row2 = sheet.createRow(i + 3);
                    IT_change res = (IT_change) result.get(i);
                    String dt_del = "";
                    if (res.getFg_annullato().equals("1")) {
                        dt_del = formatStringtoStringDate(res.getDel_dt(), patternsqldate, patternnormdate);
                    }
                    Db_Master db2 = new Db_Master();
                    String fr = "-";
                    String to = "-";
                    Openclose oc_fr = db2.query_oc(res.getIdopen_from());
                    if (oc_fr != null) {
                        fr = oc_fr.getId();
                    }
                    Openclose oc_to = db2.query_oc(res.getIdopen_to());
                    if (oc_to != null) {
                        to = oc_to.getId();
                    }
                    db2.closeDB();
                    Cell cell1b = row2.createCell(1);
                    cell1b.setCellValue(res.getFiliale());
                    cell1b.setCellStyle(style);
                    Cell cell1 = row2.createCell(2);
                    cell1.setCellValue(res.getId());
                    cell1.setCellStyle(style);
                    Cell cell22 = row2.createCell(3);
                    cell22.setCellValue(formatStringtoStringDate(res.getDt_it(), patternsqldate, patternnormdate));
                    cell22.setCellStyle(style);
                    Cell cell33 = row2.createCell(4);
                    cell33.setCellValue(res.getUser());
                    cell33.setCellStyle(style);
                    Cell cell44 = row2.createCell(5);
                    cell44.setCellValue(res.getTill_from());
                    cell44.setCellStyle(style);
                    Cell cell55 = row2.createCell(6);
                    cell55.setCellValue(fr);
                    cell55.setCellStyle(style);
                    Cell cell66 = row2.createCell(7);
                    cell66.setCellValue(res.getTill_to());
                    cell66.setCellStyle(style);
                    Cell cell77 = row2.createCell(8);
                    cell77.setCellValue(to);
                    cell77.setCellStyle(style);
                    Cell cell88 = row2.createCell(9);
                    cell88.setCellValue(dt_del);
                    cell88.setCellStyle(style);
                    Cell cell99 = row2.createCell(10);
                    cell99.setCellValue(res.formatStatus_cru(res.getFg_annullato()));
                    cell99.setCellStyle(style);
                }
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
                try(FileOutputStream fileOut = new FileOutputStream(outputfile)){
                    wb.write(fileOut);
                    fileOut.close();
                }
            }

            String base64 = new String(encodeBase64(readFileToByteArray(outputfile)));
            outputfile.delete();
            return base64;
        } catch (Exception ex) {
            insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
        }
        return null;
    }

    /**
     *
     * @param outputfile
     * @param result
     * @param typeop
     * @param nc_cat
     * @return
     */
    public static String excel_et_list(File outputfile, ArrayList<ET_change> result, String typeop, ArrayList<NC_category> nc_cat) {
        try {

            try ( //File out = new File(pathout + outputfile);
                    Workbook wb = new XSSFWorkbook()) {
                Sheet sheet = wb.createSheet("ExternalTransfer_list");
                XSSFCellStyle cellStylenum = (XSSFCellStyle) wb.createCellStyle();
                XSSFDataFormat hssfDataFormat = (XSSFDataFormat) wb.createDataFormat();
                cellStylenum.setDataFormat(hssfDataFormat.getFormat("#,#.00"));
                //private static final String formatdataCell = "#.#0,0";
                cellStylenum.setBorderBottom(THIN);
                cellStylenum.setBorderTop(THIN);
                cellStylenum.setBorderRight(THIN);
                cellStylenum.setBorderLeft(THIN);
                XSSFCellStyle style = (XSSFCellStyle) wb.createCellStyle();
                style.setBorderBottom(THIN);
                style.setBorderTop(THIN);
                style.setBorderRight(THIN);
                style.setBorderLeft(THIN);
                XSSFCellStyle style1 = (XSSFCellStyle) wb.createCellStyle();
                style1.setBorderBottom(THIN);
                style1.setBorderTop(THIN);
                style1.setBorderRight(THIN);
                style1.setBorderLeft(THIN);
                style1.setAlignment(CENTER);
                XSSFFont font = (XSSFFont) wb.createFont();
                font.setFontName(FONT_ARIAL);
                font.setFontHeightInPoints((short) 12);
                font.setBold(true);
                //  font.setColor(HSSFColor.BLUE.index);
                style1.setFont(font);
                XSSFCellStyle style2 = (XSSFCellStyle) wb.createCellStyle();
                style2.setAlignment(CENTER);
                XSSFFont font2 = (XSSFFont) wb.createFont();
                font2.setFontName(FONT_ARIAL);
                font2.setFontHeightInPoints((short) 14);
                font2.setBold(true);
                style2.setFont(font2);
                Row row0 = sheet.createRow(0);
                Cell cell0 = row0.createCell(1);
                cell0.setCellStyle(style2);
                sheet.addMergedRegion(new CellRangeAddress(0, 0, 1, 9));
                cell0.setCellValue("External Transfer List");
                Row row = sheet.createRow(1);
                Cell cellb = row.createCell(1);
                cellb.setCellValue("Branch ID");
                cellb.setCellStyle(style1);
                Cell cell = row.createCell(2);
                cell.setCellValue("Code");
                cell.setCellStyle(style1);
                Cell cell2 = row.createCell(3);
                cell2.setCellValue("Date");
                cell2.setCellStyle(style1);
                Cell cell3 = row.createCell(4);
                cell3.setCellValue("Operator");
                cell3.setCellStyle(style1);
                Cell cell4 = row.createCell(5);
                cell4.setCellValue("Type");
                cell4.setCellStyle(style1);
                Cell cell5 = row.createCell(6);
                cell5.setCellValue("Other Branch/Bank/POS ID");
                cell5.setCellStyle(style1);
                Cell cell6 = row.createCell(7);
                cell6.setCellValue("Total");
                cell6.setCellStyle(style1);
                Cell cell7 = row.createCell(8);
                cell7.setCellValue("Date Deleted");
                cell7.setCellStyle(style1);
                Cell cell8 = row.createCell(9);
                cell8.setCellValue("Status");
                cell8.setCellStyle(style1);
                for (int i = 0; i < result.size(); i++) {
                    Row row2 = sheet.createRow(i + 3);
                    ET_change res = (ET_change) result.get(i);
                    String dt_del = "";
                    if (res.getFg_annullato().equals("1")) {
                        dt_del = formatStringtoStringDate(res.getDel_dt(), patternsqldate, patternnormdate);
                    }
                    String total;
                    if (typeop.equals("CH")) {
                        double t1 = 0.00;
                        ArrayList<ET_change> val = get_ET_change_value(res.getCod());
                        for (int x = 0; x < val.size(); x++) {
                            t1 += fd(val.get(x).getIp_total());
                        }
                        total = (roundDoubleandFormat(t1, 2));
                    } else {
                        double t1 = 0.00;
                        ArrayList<ET_change> val = get_ET_nochange_value(res.getCod());
                        for (int x = 0; x < val.size(); x++) {
                            NC_category nc1 = getNC_category(nc_cat, val.get(x).getNc_causal());
                            double t2 = fd(val.get(x).getIp_quantity()) * fd(nc1.getIp_prezzo_nc());
                            t1 += t2;
                        }
                        total = (roundDoubleandFormat(t1, 2));
                    }
                    Cell cell1b = row2.createCell(1);
                    cell1b.setCellValue(res.getFiliale());
                    cell1b.setCellStyle(style);
                    Cell cell1 = row2.createCell(2);
                    cell1.setCellValue(res.getId());
                    cell1.setCellStyle(style);
                    Cell cell22 = row2.createCell(3);
                    cell22.setCellValue(formatStringtoStringDate(res.getDt_it(), patternsqldate, patternnormdate));
                    cell22.setCellStyle(style);
                    Cell cell33 = row2.createCell(4);
                    cell33.setCellValue(res.getUser());
                    cell33.setCellStyle(style);
                    Cell cell44 = row2.createCell(5);
                    cell44.setCellValue(res.format_tofrom_brba(res.getFg_tofrom(), res.getFg_brba()));
                    cell44.setCellStyle(style);
                    Cell cell55 = row2.createCell(6);
                    cell55.setCellValue(res.getCod_dest());
                    cell55.setCellStyle(style);
                    Cell cell66 = row2.createCell(7, NUMERIC);
                    cell66.setCellValue(fd(total));
                    cell66.setCellStyle(cellStylenum);
                    Cell cell66a = row2.createCell(8);
                    cell66a.setCellValue(dt_del);
                    cell66a.setCellStyle(style);
                    Cell cell77 = row2.createCell(9);
                    cell77.setCellValue(res.formatStatus_cru(res.getFg_annullato()));
                    cell77.setCellStyle(style);
                }
                sheet.autoSizeColumn(1);
                sheet.autoSizeColumn(2);
                sheet.autoSizeColumn(3);
                sheet.autoSizeColumn(4);
                sheet.autoSizeColumn(5);
                sheet.autoSizeColumn(6);
                sheet.autoSizeColumn(7);
                sheet.autoSizeColumn(8);
                try(FileOutputStream fileOut = new FileOutputStream(outputfile)){
                    wb.write(fileOut);
                    fileOut.close();
                }
            }

            String base64 = new String(encodeBase64(readFileToByteArray(outputfile)));
            outputfile.delete();
            return base64;
        } catch (Exception ex) {
            insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
        }
        return null;
    }

    /**
     *
     * @param outputfile
     * @param result
     * @param br
     * @param cu
     * @return
     */
    public static String excel_webtrans_list(File outputfile, ArrayList<Booking> result, ArrayList<Branch> br, ArrayList<Currency> cu) {
        try {

            try ( //File out = new File(pathout + outputfile);
                    Workbook wb = new XSSFWorkbook()) {
                Sheet sheet = wb.createSheet("ExternalServices_list");
                XSSFCellStyle style = (XSSFCellStyle) wb.createCellStyle();
                style.setBorderBottom(THIN);
                style.setBorderTop(THIN);
                style.setBorderRight(THIN);
                style.setBorderLeft(THIN);
                XSSFCellStyle style1 = (XSSFCellStyle) wb.createCellStyle();
                style1.setBorderBottom(THIN);
                style1.setBorderTop(THIN);
                style1.setBorderRight(THIN);
                style1.setBorderLeft(THIN);
                style1.setAlignment(CENTER);
                XSSFFont font = (XSSFFont) wb.createFont();
                font.setFontName(FONT_ARIAL);
                font.setFontHeightInPoints((short) 12);
                font.setBold(true);
                //  font.setColor(HSSFColor.BLUE.index);
                style1.setFont(font);
                XSSFCellStyle style2 = (XSSFCellStyle) wb.createCellStyle();
                style2.setAlignment(CENTER);
                XSSFFont font2 = (XSSFFont) wb.createFont();
                font2.setFontName(FONT_ARIAL);
                font2.setFontHeightInPoints((short) 14);
                font2.setBold(true);
                style2.setFont(font2);
                Row row0 = sheet.createRow(0);
                Cell cell0 = row0.createCell(1);
                cell0.setCellStyle(style2);
                sheet.addMergedRegion(new CellRangeAddress(0, 0, 1, 9));
                cell0.setCellValue("External Services List");
                Row row = sheet.createRow(1);
                Cell cell = row.createCell(1);
                cell.setCellValue("Alert");
                cell.setCellStyle(style1);
                Cell cell2 = row.createCell(2);
                cell2.setCellValue("Branch");
                cell2.setCellStyle(style1);
                Cell cell3 = row.createCell(3);
                cell3.setCellValue("Currency");
                cell3.setCellStyle(style1);
                Cell cell4 = row.createCell(4);
                cell4.setCellValue("Total");
                cell4.setCellStyle(style1);
                Cell cell5 = row.createCell(5);
                cell5.setCellValue("Date");
                cell5.setCellStyle(style1);
                Cell cell6 = row.createCell(6);
                cell6.setCellValue("Surname");
                cell6.setCellStyle(style1);
                Cell cell7 = row.createCell(7);
                cell7.setCellValue("Name");
                cell7.setCellStyle(style1);
                Cell cell8 = row.createCell(8);
                cell8.setCellValue("Status");
                cell8.setCellStyle(style1);
                for (int i = 0; i < result.size(); i++) {
                    Row row2 = sheet.createRow(i + 3);
                    Booking res = (Booking) result.get(i);
                    String alert = "";
                    if (!res.getStato().equals("1")) {
                    } else {
                        alert = res.formatAlert_cru(isAvaliable(res), isToday(res.getDt_ritiro()), isTomorrow(res.getDt_ritiro()), isPast(res.getDt_ritiro()));
                    }
                    Cell cell1 = row2.createCell(1);
                    cell1.setCellValue(alert);
                    cell1.setCellStyle(style);
                    Cell cell22 = row2.createCell(2);
                    cell22.setCellValue(formatBankBranch(res.getFiliale(), "BR", null, br, null));
                    cell22.setCellStyle(style);
                    Cell cell33 = row2.createCell(3);
                    cell33.setCellValue(res.getCurrency() + " - " + formatALCurrency(res.getCurrency(), cu));
                    cell33.setCellStyle(style);
                    Cell cell44 = row2.createCell(4);
                    cell44.setCellValue(formatMysqltoDisplay(res.getTotal()));
                    cell44.setCellStyle(style);
                    Cell cell55 = row2.createCell(5);
                    cell55.setCellValue(formatStringtoStringDate(res.getDt_ritiro(), patternsql, patternnormdate_filter));
                    cell55.setCellStyle(style);
                    Cell cell66 = row2.createCell(6);
                    cell66.setCellValue(res.getCl_cognome());
                    cell66.setCellStyle(style);
                    Cell cell77 = row2.createCell(7);
                    cell77.setCellValue(res.getCl_nome());
                    cell77.setCellStyle(style);
                    Cell cell88 = row2.createCell(8);
                    cell88.setCellValue(res.formatStatus_cru(res.getStato()));
                    cell88.setCellStyle(style);
                }
                sheet.autoSizeColumn(1);
                sheet.autoSizeColumn(2);
                sheet.autoSizeColumn(3);
                sheet.autoSizeColumn(4);
                sheet.autoSizeColumn(5);
                sheet.autoSizeColumn(6);
                sheet.autoSizeColumn(7);
                sheet.autoSizeColumn(8);
                try(FileOutputStream fileOut = new FileOutputStream(outputfile)){
                    wb.write(fileOut);
                    fileOut.close();
                }
            }

            String base64 = new String(encodeBase64(readFileToByteArray(outputfile)));
            outputfile.delete();
            return base64;
        } catch (Exception ex) {
            insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
        }
        return null;
    }

    /**
     *
     * @param outputfile
     * @param result
     * @return
     */
    public static String excel_nl_view(File outputfile, ArrayList<Newsletters> result) {
        try {

            try ( //File out = new File(pathout + outputfile);
                    Workbook wb = new XSSFWorkbook()) {
                Sheet sheet = wb.createSheet("Newsletter_list");
                XSSFCellStyle style = (XSSFCellStyle) wb.createCellStyle();
                style.setBorderBottom(THIN);
                style.setBorderTop(THIN);
                style.setBorderRight(THIN);
                style.setBorderLeft(THIN);
                XSSFCellStyle style1 = (XSSFCellStyle) wb.createCellStyle();
                style1.setBorderBottom(THIN);
                style1.setBorderTop(THIN);
                style1.setBorderRight(THIN);
                style1.setBorderLeft(THIN);
                style1.setAlignment(CENTER);
                XSSFFont font = (XSSFFont) wb.createFont();
                font.setFontName(FONT_ARIAL);
                font.setFontHeightInPoints((short) 12);
                font.setBold(true);
                //  font.setColor(HSSFColor.BLUE.index);
                style1.setFont(font);
                XSSFCellStyle style2 = (XSSFCellStyle) wb.createCellStyle();
                style2.setAlignment(CENTER);
                XSSFFont font2 = (XSSFFont) wb.createFont();
                font2.setFontName(FONT_ARIAL);
                font2.setFontHeightInPoints((short) 14);
                font2.setBold(true);
                style2.setFont(font2);
                Row row0 = sheet.createRow(0);
                Cell cell0 = row0.createCell(1);
                cell0.setCellStyle(style2);
                sheet.addMergedRegion(new CellRangeAddress(0, 0, 1, 9));
                cell0.setCellValue("Newsletter List");
                Row row = sheet.createRow(1);
                Cell cell = row.createCell(1);
                cell.setCellValue("Title");
                cell.setCellStyle(style1);
                Cell cell2 = row.createCell(2);
                cell2.setCellValue("Description");
                cell2.setCellStyle(style1);
                Cell cell3 = row.createCell(3);
                cell3.setCellValue("Date");
                cell3.setCellStyle(style1);
                Cell cell4 = row.createCell(4);
                cell4.setCellValue("Status");
                cell4.setCellStyle(style1);
                for (int i = 0; i < result.size(); i++) {
                    Row row2 = sheet.createRow(i + 3);
                    Newsletters res = (Newsletters) result.get(i);
                    String st = res.formatStatus_cru(res.getStatus());
                    if (res.getStatus().equals("R")) {
                        st = st + formatStringtoStringDate(res.getDt_read(), "yyyy-MM-dd HH:mm:ss", "dd/MM/yyyy HH:mm:ss") + " ";
                    }
                    Cell cell1 = row2.createCell(1);
                    cell1.setCellValue(correggi(res.getTitolo()));
                    cell1.setCellStyle(style);
                    Cell cell22 = row2.createCell(2);
                    cell22.setCellValue(correggi(res.getDescr()));
                    cell22.setCellStyle(style);
                    Cell cell33 = row2.createCell(3);
                    cell33.setCellValue(formatStringtoStringDate(res.getDt_upload(), "yyyy-MM-dd HH:mm:ss", "dd/MM/yyyy HH:mm:ss"));
                    cell33.setCellStyle(style);
                    Cell cell44 = row2.createCell(4);
                    cell44.setCellValue(st);
                    cell44.setCellStyle(style);
                }
                sheet.autoSizeColumn(1);
                sheet.autoSizeColumn(2);
                sheet.autoSizeColumn(3);
                sheet.autoSizeColumn(4);
                try(FileOutputStream fileOut = new FileOutputStream(outputfile)){
                    wb.write(fileOut);
                    fileOut.close();
                }
            }

            String base64 = new String(encodeBase64(readFileToByteArray(outputfile)));
            outputfile.delete();
            return base64;
        } catch (Exception ex) {
            insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
        }
        return null;
    }

    /**
     *
     * @param outputfile
     * @param result
     * @param listTill
     * @return
     */
    public static String excel_oc_list(File outputfile, ArrayList<Openclose> result, ArrayList<Till> listTill) {
        try {
            ArrayList<String[]> array_kind = list_all_kind(filiale);
            try ( //File out = new File(pathout + outputfile);
                    Workbook wb = new XSSFWorkbook()) {
                Sheet sheet = wb.createSheet("OpenClose_List");
                XSSFCellStyle style = (XSSFCellStyle) wb.createCellStyle();
                style.setBorderBottom(THIN);
                style.setBorderTop(THIN);
                style.setBorderRight(THIN);
                style.setBorderLeft(THIN);
                XSSFCellStyle style1 = (XSSFCellStyle) wb.createCellStyle();
                style1.setBorderBottom(THIN);
                style1.setBorderTop(THIN);
                style1.setBorderRight(THIN);
                style1.setBorderLeft(THIN);
                style1.setAlignment(CENTER);
                XSSFFont font = (XSSFFont) wb.createFont();
                font.setFontName(FONT_ARIAL);
                font.setFontHeightInPoints((short) 12);
                font.setBold(true);
                //  font.setColor(HSSFColor.BLUE.index);
                style1.setFont(font);
                XSSFCellStyle style2 = (XSSFCellStyle) wb.createCellStyle();
                style2.setAlignment(CENTER);
                XSSFFont font2 = (XSSFFont) wb.createFont();
                font2.setFontName(FONT_ARIAL);
                font2.setFontHeightInPoints((short) 14);
                font2.setBold(true);
                style2.setFont(font2);
                Row row0 = sheet.createRow(0);
                Cell cell0 = row0.createCell(1);
                cell0.setCellStyle(style2);
                sheet.addMergedRegion(new CellRangeAddress(0, 0, 1, 9));
                cell0.setCellValue("Open Close List");
                Row row = sheet.createRow(1);
                Cell cell = row.createCell(1);
                cell.setCellValue("Branch ID");
                cell.setCellStyle(style1);
                Cell cell12 = row.createCell(2);
                cell12.setCellValue("Code");
                cell12.setCellStyle(style1);
                Cell cell2 = row.createCell(3);
                cell2.setCellValue("Date");
                cell2.setCellStyle(style1);
                Cell cell3 = row.createCell(4);
                cell3.setCellValue("Operator");
                cell3.setCellStyle(style1);
                Cell cell4 = row.createCell(5);
                cell4.setCellValue("Till");
                cell4.setCellStyle(style1);
                Cell cell5 = row.createCell(6);
                cell5.setCellValue("Type");
                cell5.setCellStyle(style1);
                Cell cell6 = row.createCell(7);
                cell6.setCellValue("Errors");
                cell6.setCellStyle(style1);
                for (int i = 0; i < result.size(); i++) {
                    Row row2 = sheet.createRow(i + 3);
                    Openclose res = (Openclose) result.get(i);
                    Cell cell1 = row2.createCell(1);
                    cell1.setCellValue(res.getFiliale());
                    cell1.setCellStyle(style);
                    Cell cell111 = row2.createCell(2);
                    cell111.setCellValue(res.getId());
                    cell111.setCellStyle(style);
                    Cell cell22 = row2.createCell(3);
                    cell22.setCellValue(formatStringtoStringDate(res.getData(), patternsqldate, patternnormdate));
                    cell22.setCellStyle(style);
                    Cell cell33 = row2.createCell(4);
                    cell33.setCellValue(res.getUser());
                    cell33.setCellStyle(style);
                    Cell cell44 = row2.createCell(5);
                    cell44.setCellValue(res.formatDescTill(listTill, res.getTill()));
                    cell44.setCellStyle(style);
                    Cell cell55 = row2.createCell(6);
                    cell55.setCellValue(res.formatType_cru(res.getFg_tipo()));
                    cell55.setCellStyle(style);
                    Cell cell66 = row2.createCell(7);
                    cell66.setCellValue(res.formatErrors_cru(res.getErrors()));
                    cell66.setCellStyle(style);
                }
                sheet.autoSizeColumn(1);
                sheet.autoSizeColumn(2);
                sheet.autoSizeColumn(3);
                sheet.autoSizeColumn(4);
                sheet.autoSizeColumn(5);
                sheet.autoSizeColumn(6);
                try(FileOutputStream fileOut = new FileOutputStream(outputfile)){
                    wb.write(fileOut);
                    fileOut.close();
                }
            }

            String base64 = new String(encodeBase64(readFileToByteArray(outputfile)));
            outputfile.delete();
            return base64;
        } catch (Exception ex) {
            insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
        }
        return null;
    }

    /**
     *
     * @param outputfile
     * @param result
     * @return
     */
    public static String excel_lowcomm_list(File outputfile, ArrayList<String[]> result) {
        try {
            try ( //File out = new File(pathout + outputfile);
                    Workbook wb = new XSSFWorkbook()) {
                Sheet sheet = wb.createSheet("LowCommissionJustify_list");
                XSSFCellStyle style = (XSSFCellStyle) wb.createCellStyle();
                style.setBorderBottom(THIN);
                style.setBorderTop(THIN);
                style.setBorderRight(THIN);
                style.setBorderLeft(THIN);
                XSSFCellStyle style1 = (XSSFCellStyle) wb.createCellStyle();
                style1.setBorderBottom(THIN);
                style1.setBorderTop(THIN);
                style1.setBorderRight(THIN);
                style1.setBorderLeft(THIN);
                style1.setAlignment(CENTER);
                XSSFFont font = (XSSFFont) wb.createFont();
                font.setFontName(FONT_ARIAL);
                font.setFontHeightInPoints((short) 12);
                font.setBold(true);
                //  font.setColor(HSSFColor.BLUE.index);
                style1.setFont(font);
                XSSFCellStyle style2 = (XSSFCellStyle) wb.createCellStyle();
                style2.setAlignment(CENTER);
                XSSFFont font2 = (XSSFFont) wb.createFont();
                font2.setFontName(FONT_ARIAL);
                font2.setFontHeightInPoints((short) 14);
                font2.setBold(true);
                style2.setFont(font2);
                Row row0 = sheet.createRow(0);
                Cell cell0 = row0.createCell(1);
                cell0.setCellStyle(style2);
                sheet.addMergedRegion(new CellRangeAddress(0, 0, 1, 9));
                cell0.setCellValue("Low Commission Justify List - Branch: " + filiale);
                Row row = sheet.createRow(1);
                Cell cell = row.createCell(1);
                cell.setCellValue("Code");
                cell.setCellStyle(style1);
                Cell cell2 = row.createCell(2);
                cell2.setCellValue("Description");
                cell2.setCellStyle(style1);
                Cell cell3 = row.createCell(3);
                cell3.setCellValue("Status");
                cell3.setCellStyle(style1);
                for (int i = 0; i < result.size(); i++) {
                    Row row2 = sheet.createRow(i + 3);
                    String[] res = (String[]) result.get(i);
                    Cell cell1 = row2.createCell(1);
                    cell1.setCellValue(res[0]);
                    cell1.setCellStyle(style);
                    Cell cell22 = row2.createCell(2);
                    cell22.setCellValue(res[1]);
                    cell22.setCellStyle(style);
                    Cell cell33 = row2.createCell(3);
                    cell33.setCellValue(formatStatus_general_cru(res[2]));
                    cell33.setCellStyle(style);
                }
                sheet.autoSizeColumn(1);
                sheet.autoSizeColumn(2);
                sheet.autoSizeColumn(3);
                try(FileOutputStream fileOut = new FileOutputStream(outputfile)){
                    wb.write(fileOut);
                    fileOut.close();
                }
            }

            String base64 = new String(encodeBase64(readFileToByteArray(outputfile)));
            outputfile.delete();
            return base64;
        } catch (Exception ex) {
            insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
        }
        return null;
    }

    /**
     *
     * @param outputfile
     * @param result
     * @return
     */
    public static String excel_doctype_list(File outputfile, ArrayList<String[]> result) {
        try {
            try ( //File out = new File(pathout + outputfile);
                    Workbook wb = new XSSFWorkbook()) {
                Sheet sheet = wb.createSheet("IdentityDocument_list");
                XSSFCellStyle style = (XSSFCellStyle) wb.createCellStyle();
                style.setBorderBottom(THIN);
                style.setBorderTop(THIN);
                style.setBorderRight(THIN);
                style.setBorderLeft(THIN);
                XSSFCellStyle style1 = (XSSFCellStyle) wb.createCellStyle();
                style1.setBorderBottom(THIN);
                style1.setBorderTop(THIN);
                style1.setBorderRight(THIN);
                style1.setBorderLeft(THIN);
                style1.setAlignment(CENTER);
                XSSFFont font = (XSSFFont) wb.createFont();
                font.setFontName(FONT_ARIAL);
                font.setFontHeightInPoints((short) 12);
                font.setBold(true);
                //  font.setColor(HSSFColor.BLUE.index);
                style1.setFont(font);
                XSSFCellStyle style2 = (XSSFCellStyle) wb.createCellStyle();
                style2.setAlignment(CENTER);
                XSSFFont font2 = (XSSFFont) wb.createFont();
                font2.setFontName(FONT_ARIAL);
                font2.setFontHeightInPoints((short) 14);
                font2.setBold(true);
                style2.setFont(font2);
                Row row0 = sheet.createRow(0);
                Cell cell0 = row0.createCell(1);
                cell0.setCellStyle(style2);
                sheet.addMergedRegion(new CellRangeAddress(0, 0, 1, 9));
                cell0.setCellValue("Identity Document List - Branch: " + filiale);
                Row row = sheet.createRow(1);
                Cell cell = row.createCell(1);
                cell.setCellValue("Code");
                cell.setCellStyle(style1);
                Cell cell2 = row.createCell(2);
                cell2.setCellValue("Description");
                cell2.setCellStyle(style1);
                Cell cell3 = row.createCell(3);
                cell3.setCellValue("OAM Code");
                cell3.setCellStyle(style1);
                for (int i = 0; i < result.size(); i++) {
                    Row row2 = sheet.createRow(i + 3);
                    String[] res = (String[]) result.get(i);
                    Cell cell1 = row2.createCell(1);
                    cell1.setCellValue(res[0]);
                    cell1.setCellStyle(style);
                    Cell cell22 = row2.createCell(2);
                    cell22.setCellValue(res[1]);
                    cell22.setCellStyle(style);
                    Cell cell33 = row2.createCell(3);
                    cell33.setCellValue(res[2]);
                    cell33.setCellStyle(style);

                }
                sheet.autoSizeColumn(1);
                sheet.autoSizeColumn(2);
                sheet.autoSizeColumn(3);
                try(FileOutputStream fileOut = new FileOutputStream(outputfile)){
                    wb.write(fileOut);
                    fileOut.close();
                }
            }

            String base64 = new String(encodeBase64(readFileToByteArray(outputfile)));
            outputfile.delete();
            return base64;
        } catch (Exception ex) {
            insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
        }
        return null;
    }

    /**
     *
     * @param outputfile
     * @param result
     * @return
     */
    public static String excel_nations_list(File outputfile, ArrayList<String[]> result) {
        try {
            ArrayList<String[]> array_category_nations = category_nations();
            try ( //File out = new File(pathout + outputfile);
                    Workbook wb = new XSSFWorkbook()) {
                Sheet sheet = wb.createSheet("Country_list");
                XSSFCellStyle style = (XSSFCellStyle) wb.createCellStyle();
                style.setBorderBottom(THIN);
                style.setBorderTop(THIN);
                style.setBorderRight(THIN);
                style.setBorderLeft(THIN);
                XSSFCellStyle style1 = (XSSFCellStyle) wb.createCellStyle();
                style1.setBorderBottom(THIN);
                style1.setBorderTop(THIN);
                style1.setBorderRight(THIN);
                style1.setBorderLeft(THIN);
                style1.setAlignment(CENTER);
                XSSFFont font = (XSSFFont) wb.createFont();
                font.setFontName(FONT_ARIAL);
                font.setFontHeightInPoints((short) 12);
                font.setBold(true);
                //  font.setColor(HSSFColor.BLUE.index);
                style1.setFont(font);
                XSSFCellStyle style2 = (XSSFCellStyle) wb.createCellStyle();
                style2.setAlignment(CENTER);
                XSSFFont font2 = (XSSFFont) wb.createFont();
                font2.setFontName(FONT_ARIAL);
                font2.setFontHeightInPoints((short) 14);
                font2.setBold(true);
                style2.setFont(font2);
                Row row0 = sheet.createRow(0);
                Cell cell0 = row0.createCell(1);
                cell0.setCellStyle(style2);
                sheet.addMergedRegion(new CellRangeAddress(0, 0, 1, 9));
                cell0.setCellValue("Country List - Branch: " + filiale);
                Row row = sheet.createRow(1);
                Cell cell = row.createCell(1);
                cell.setCellValue("Code");
                cell.setCellStyle(style1);
                Cell cell2 = row.createCell(2);
                cell2.setCellValue("Description");
                cell2.setCellStyle(style1);
                Cell cell3 = row.createCell(3);
                cell3.setCellValue("ISO Code");
                cell3.setCellStyle(style1);
                Cell cell4 = row.createCell(4);
                cell4.setCellValue("Area");
                cell4.setCellStyle(style1);
                for (int i = 0; i < result.size(); i++) {
                    Row row2 = sheet.createRow(i + 3);
                    String[] res = (String[]) result.get(i);
                    Cell cell1 = row2.createCell(1);
                    cell1.setCellValue(res[0]);
                    cell1.setCellStyle(style);
                    Cell cell22 = row2.createCell(2);
                    cell22.setCellValue(res[1]);
                    cell22.setCellStyle(style);
                    Cell cell33 = row2.createCell(3);
                    cell33.setCellValue(res[2]);
                    cell33.setCellStyle(style);
                    Cell cell44 = row2.createCell(4);
                    cell44.setCellValue(formatAL(res[3], array_category_nations, 1));
                    cell44.setCellStyle(style);
                }
                sheet.autoSizeColumn(1);
                sheet.autoSizeColumn(2);
                sheet.autoSizeColumn(3);
                sheet.autoSizeColumn(4);
                try(FileOutputStream fileOut = new FileOutputStream(outputfile)){
                    wb.write(fileOut);
                    fileOut.close();
                }
            }

            String base64 = new String(encodeBase64(readFileToByteArray(outputfile)));
            outputfile.delete();
            return base64;
        } catch (Exception ex) {
            insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
        }
        return null;
    }

    /**
     *
     * @param outputfile
     * @param result
     * @return
     */
    public static String excel_district_list(File outputfile, ArrayList<String[]> result) {
        try {

            try ( //File out = new File(pathout + outputfile);
                    Workbook wb = new XSSFWorkbook()) {
                Sheet sheet = wb.createSheet("Districtlist");
                XSSFCellStyle style = (XSSFCellStyle) wb.createCellStyle();
                style.setBorderBottom(THIN);
                style.setBorderTop(THIN);
                style.setBorderRight(THIN);
                style.setBorderLeft(THIN);
                XSSFCellStyle style1 = (XSSFCellStyle) wb.createCellStyle();
                style1.setBorderBottom(THIN);
                style1.setBorderTop(THIN);
                style1.setBorderRight(THIN);
                style1.setBorderLeft(THIN);
                style1.setAlignment(CENTER);
                XSSFFont font = (XSSFFont) wb.createFont();
                font.setFontName(FONT_ARIAL);
                font.setFontHeightInPoints((short) 12);
                font.setBold(true);
                //  font.setColor(HSSFColor.BLUE.index);
                style1.setFont(font);
                XSSFCellStyle style2 = (XSSFCellStyle) wb.createCellStyle();
                style2.setAlignment(CENTER);
                XSSFFont font2 = (XSSFFont) wb.createFont();
                font2.setFontName(FONT_ARIAL);
                font2.setFontHeightInPoints((short) 14);
                font2.setBold(true);
                style2.setFont(font2);
                Row row0 = sheet.createRow(0);
                Cell cell0 = row0.createCell(1);
                cell0.setCellStyle(style2);
                sheet.addMergedRegion(new CellRangeAddress(0, 0, 1, 9));
                cell0.setCellValue("District List - Branch: " + filiale);
                Row row = sheet.createRow(1);
                Cell cell = row.createCell(1);
                cell.setCellValue("Code");
                cell.setCellStyle(style1);
                Cell cell2 = row.createCell(2);
                cell2.setCellValue("Description");
                cell2.setCellStyle(style1);
                for (int i = 0; i < result.size(); i++) {
                    Row row2 = sheet.createRow(i + 3);
                    String[] res = (String[]) result.get(i);
                    Cell cell1 = row2.createCell(1);
                    cell1.setCellValue(res[0]);
                    cell1.setCellStyle(style);
                    Cell cell22 = row2.createCell(2);
                    cell22.setCellValue(res[1]);
                    cell22.setCellStyle(style);
                }
                sheet.autoSizeColumn(1);
                sheet.autoSizeColumn(2);
                try(FileOutputStream fileOut = new FileOutputStream(outputfile)){
                    wb.write(fileOut);
                    fileOut.close();
                }
            }

            String base64 = new String(encodeBase64(readFileToByteArray(outputfile)));
            outputfile.delete();
            return base64;
        } catch (Exception ex) {
            insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
        }
        return null;
    }

    /**
     *
     * @param outputfile
     * @param result
     * @return
     */
    public static String excel_city_list(File outputfile, ArrayList<String[]> result) {
        try {

            try ( //File out = new File(pathout + outputfile);
                    Workbook wb = new XSSFWorkbook()) {
                Sheet sheet = wb.createSheet("Citylist");
                XSSFCellStyle style = (XSSFCellStyle) wb.createCellStyle();
                style.setBorderBottom(THIN);
                style.setBorderTop(THIN);
                style.setBorderRight(THIN);
                style.setBorderLeft(THIN);
                XSSFCellStyle style1 = (XSSFCellStyle) wb.createCellStyle();
                style1.setBorderBottom(THIN);
                style1.setBorderTop(THIN);
                style1.setBorderRight(THIN);
                style1.setBorderLeft(THIN);
                style1.setAlignment(CENTER);
                XSSFFont font = (XSSFFont) wb.createFont();
                font.setFontName(FONT_ARIAL);
                font.setFontHeightInPoints((short) 12);
                font.setBold(true);
                //  font.setColor(HSSFColor.BLUE.index);
                style1.setFont(font);
                XSSFCellStyle style2 = (XSSFCellStyle) wb.createCellStyle();
                style2.setAlignment(CENTER);
                XSSFFont font2 = (XSSFFont) wb.createFont();
                font2.setFontName(FONT_ARIAL);
                font2.setFontHeightInPoints((short) 14);
                font2.setBold(true);
                style2.setFont(font2);
                Row row0 = sheet.createRow(0);
                Cell cell0 = row0.createCell(1);
                cell0.setCellStyle(style2);
                sheet.addMergedRegion(new CellRangeAddress(0, 0, 1, 9));
                cell0.setCellValue("City List - Branch: " + filiale);
                Row row = sheet.createRow(1);
                Cell cell = row.createCell(1);
                cell.setCellValue("Code");
                cell.setCellStyle(style1);
                Cell cell2 = row.createCell(2);
                cell2.setCellValue("Description");
                cell2.setCellStyle(style1);
                for (int i = 0; i < result.size(); i++) {
                    Row row2 = sheet.createRow(i + 3);
                    String[] res = (String[]) result.get(i);
                    Cell cell1 = row2.createCell(1);
                    cell1.setCellValue(res[0]);
                    cell1.setCellStyle(style);
                    Cell cell22 = row2.createCell(2);
                    cell22.setCellValue(res[1]);
                    cell22.setCellStyle(style);
                }
                sheet.autoSizeColumn(1);
                sheet.autoSizeColumn(2);
                try(FileOutputStream fileOut = new FileOutputStream(outputfile)){
                    wb.write(fileOut);
                    fileOut.close();
                }
            }

            String base64 = new String(encodeBase64(readFileToByteArray(outputfile)));
            outputfile.delete();
            return base64;
        } catch (Exception ex) {
            insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
        }
        return null;
    }

    /**
     *
     * @param outputfile
     * @param result
     * @return
     */
    public static String excel_intbook_list(File outputfile, ArrayList<String[]> result) {
        try {

            try ( //File out = new File(pathout + outputfile);
                    Workbook wb = new XSSFWorkbook()) {
                Sheet sheet = wb.createSheet("InternetBooking_list");
                XSSFCellStyle style = (XSSFCellStyle) wb.createCellStyle();
                style.setBorderBottom(THIN);
                style.setBorderTop(THIN);
                style.setBorderRight(THIN);
                style.setBorderLeft(THIN);
                XSSFCellStyle style1 = (XSSFCellStyle) wb.createCellStyle();
                style1.setBorderBottom(THIN);
                style1.setBorderTop(THIN);
                style1.setBorderRight(THIN);
                style1.setBorderLeft(THIN);
                style1.setAlignment(CENTER);
                XSSFFont font = (XSSFFont) wb.createFont();
                font.setFontName(FONT_ARIAL);
                font.setFontHeightInPoints((short) 12);
                font.setBold(true);
                //  font.setColor(HSSFColor.BLUE.index);
                style1.setFont(font);
                XSSFCellStyle style2 = (XSSFCellStyle) wb.createCellStyle();
                style2.setAlignment(CENTER);
                XSSFFont font2 = (XSSFFont) wb.createFont();
                font2.setFontName(FONT_ARIAL);
                font2.setFontHeightInPoints((short) 14);
                font2.setBold(true);
                style2.setFont(font2);
                Row row0 = sheet.createRow(0);
                Cell cell0 = row0.createCell(1);
                cell0.setCellStyle(style2);
                sheet.addMergedRegion(new CellRangeAddress(0, 0, 1, 9));
                cell0.setCellValue("Internet Booking List - Branch: " + filiale);
                Row row = sheet.createRow(1);
                Cell cell = row.createCell(1);
                cell.setCellValue("Code");
                cell.setCellStyle(style1);
                Cell cell2 = row.createCell(2);
                cell2.setCellValue("Description");
                cell2.setCellStyle(style1);
                for (int i = 0; i < result.size(); i++) {
                    Row row2 = sheet.createRow(i + 3);
                    String[] res = (String[]) result.get(i);
                    Cell cell1 = row2.createCell(1);
                    cell1.setCellValue(res[0]);
                    cell1.setCellStyle(style);
                    Cell cell22 = row2.createCell(2);
                    cell22.setCellValue(res[1]);
                    cell22.setCellStyle(style);
                }
                sheet.autoSizeColumn(1);
                sheet.autoSizeColumn(2);
                try(FileOutputStream fileOut = new FileOutputStream(outputfile)){
                    wb.write(fileOut);
                    fileOut.close();
                }
            }

            String base64 = new String(encodeBase64(readFileToByteArray(outputfile)));
            outputfile.delete();
            return base64;
        } catch (Exception ex) {
            insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
        }
        return null;
    }

    /**
     *
     * @param outputfile
     * @param result
     * @return
     */
    public static String excel_vatcode_list(File outputfile, ArrayList<VATcode> result) {
        try {

            try ( //File out = new File(pathout + outputfile);
                    Workbook wb = new XSSFWorkbook()) {
                Sheet sheet = wb.createSheet("VATCode_list");
                XSSFCellStyle style = (XSSFCellStyle) wb.createCellStyle();
                style.setBorderBottom(THIN);
                style.setBorderTop(THIN);
                style.setBorderRight(THIN);
                style.setBorderLeft(THIN);
                XSSFCellStyle style1 = (XSSFCellStyle) wb.createCellStyle();
                style1.setBorderBottom(THIN);
                style1.setBorderTop(THIN);
                style1.setBorderRight(THIN);
                style1.setBorderLeft(THIN);
                style1.setAlignment(CENTER);
                XSSFFont font = (XSSFFont) wb.createFont();
                font.setFontName(FONT_ARIAL);
                font.setFontHeightInPoints((short) 12);
                font.setBold(true);
                //  font.setColor(HSSFColor.BLUE.index);
                style1.setFont(font);
                XSSFCellStyle style2 = (XSSFCellStyle) wb.createCellStyle();
                style2.setAlignment(CENTER);
                XSSFFont font2 = (XSSFFont) wb.createFont();
                font2.setFontName(FONT_ARIAL);
                font2.setFontHeightInPoints((short) 14);
                font2.setBold(true);
                style2.setFont(font2);
                Row row0 = sheet.createRow(0);
                Cell cell0 = row0.createCell(1);
                cell0.setCellStyle(style2);
                sheet.addMergedRegion(new CellRangeAddress(0, 0, 1, 9));
                cell0.setCellValue("VAT Code List - Branch: " + filiale);
                Row row = sheet.createRow(1);
                Cell cell = row.createCell(1);
                cell.setCellValue("Code");
                cell.setCellStyle(style1);
                Cell cell2 = row.createCell(2);
                cell2.setCellValue("Description");
                cell2.setCellStyle(style1);
                Cell cell3 = row.createCell(3);
                cell3.setCellValue("Rate");
                cell3.setCellStyle(style1);
                Cell cell4 = row.createCell(4);
                cell4.setCellValue("Status");
                cell4.setCellStyle(style1);
                for (int i = 0; i < result.size(); i++) {
                    Row row2 = sheet.createRow(i + 3);
                    VATcode res = (VATcode) result.get(i);
                    Cell cell1 = row2.createCell(1);
                    cell1.setCellValue(res.getCodice());
                    cell1.setCellStyle(style);
                    Cell cell22 = row2.createCell(2);
                    cell22.setCellValue(res.getDescrizione());
                    cell22.setCellStyle(style);
                    Cell cell33 = row2.createCell(3);
                    cell33.setCellValue(formatMysqltoDisplay(res.getAliquota()));
                    cell33.setCellStyle(style);
                    Cell cell44 = row2.createCell(4);
                    cell44.setCellValue(formatStatus_general_cru(res.getFg_annullato()));
                    cell44.setCellStyle(style);
                }
                sheet.autoSizeColumn(1);
                sheet.autoSizeColumn(2);
                sheet.autoSizeColumn(3);
                sheet.autoSizeColumn(4);
                try(FileOutputStream fileOut = new FileOutputStream(outputfile)){
                    wb.write(fileOut);
                    fileOut.close();
                }
            }

            String base64 = new String(encodeBase64(readFileToByteArray(outputfile)));
            outputfile.delete();
            return base64;
        } catch (Exception ex) {
            insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
        }
        return null;
    }

    /**
     *
     * @param outputfile
     * @param result
     * @param resultagent
     * @return
     */
    public static String excel_company_list(File outputfile, ArrayList<Company> result, ArrayList<Company> resultagent) {
        try {
            ArrayList<String[]> country = country();
            try ( //File out = new File(pathout + outputfile);
                    Workbook wb = new XSSFWorkbook()) {
                Sheet sheet = wb.createSheet("Company_list");
                XSSFCellStyle style = (XSSFCellStyle) wb.createCellStyle();
                style.setBorderBottom(THIN);
                style.setBorderTop(THIN);
                style.setBorderRight(THIN);
                style.setBorderLeft(THIN);
                XSSFCellStyle style1 = (XSSFCellStyle) wb.createCellStyle();
                style1.setBorderBottom(THIN);
                style1.setBorderTop(THIN);
                style1.setBorderRight(THIN);
                style1.setBorderLeft(THIN);
                style1.setAlignment(CENTER);
                XSSFFont font = (XSSFFont) wb.createFont();
                font.setFontName(FONT_ARIAL);
                font.setFontHeightInPoints((short) 12);
                font.setBold(true);
                //  font.setColor(HSSFColor.BLUE.index);
                style1.setFont(font);
                XSSFCellStyle style2 = (XSSFCellStyle) wb.createCellStyle();
                style2.setAlignment(CENTER);
                XSSFFont font2 = (XSSFFont) wb.createFont();
                font2.setFontName(FONT_ARIAL);
                font2.setFontHeightInPoints((short) 14);
                font2.setBold(true);
                style2.setFont(font2);
                Row row000 = sheet.createRow(0);
                Cell cell000 = row000.createCell(1);
                cell000.setCellStyle(style2);
                sheet.addMergedRegion(new CellRangeAddress(0, 0, 1, 9));
                cell000.setCellValue("Company List - Branch: " + filiale);
                int cnt = 2;
                for (int i = 0; i < result.size(); i++) {
                    cnt++;
                    Row row0 = sheet.createRow(cnt);
                    Cell cell0 = row0.createCell(1);
                    cell0.setCellStyle(style2);
                    sheet.addMergedRegion(new CellRangeAddress(cnt, cnt, 1, 9));
                    cell0.setCellValue("Company ");
                    cnt++;
                    Row row = sheet.createRow(cnt);
                    Cell cell = row.createCell(1);
                    cell.setCellValue("Status");
                    cell.setCellStyle(style1);
                    Cell cell2 = row.createCell(2);
                    cell2.setCellValue("Code");
                    cell2.setCellStyle(style1);
                    Cell cell3 = row.createCell(3);
                    cell3.setCellValue("Description");
                    cell3.setCellStyle(style1);
                    Cell cell4 = row.createCell(4);
                    cell4.setCellValue("VAT Code");
                    cell4.setCellStyle(style1);
                    Cell cell5 = row.createCell(5);
                    cell5.setCellValue("Address");
                    cell5.setCellStyle(style1);
                    Cell cell6 = row.createCell(6);
                    cell6.setCellValue("Zip Code");
                    cell6.setCellStyle(style1);
                    Cell cell7 = row.createCell(7);
                    cell7.setCellValue("District");
                    cell7.setCellStyle(style1);
                    Cell cell8 = row.createCell(8);
                    cell8.setCellValue("CAB City");
                    cell8.setCellStyle(style1);
                    Cell cell9 = row.createCell(9);
                    cell9.setCellValue("Country");
                    cell9.setCellStyle(style1);
                    cnt++;
                    Row row2 = sheet.createRow(cnt);
                    Company res = (Company) result.get(i);
                    Cell cell1 = row2.createCell(1);
                    cell1.setCellValue(formatStatus_general_cru(res.getFg_annullato()));
                    cell1.setCellStyle(style);
                    Cell cell22 = row2.createCell(2);
                    cell22.setCellValue(res.getNdg());
                    cell22.setCellStyle(style);
                    Cell cell33 = row2.createCell(3);
                    cell33.setCellValue(res.getRagione_sociale());
                    cell33.setCellStyle(style);
                    Cell cell44 = row2.createCell(4);
                    cell44.setCellValue(res.getCodice_fiscale());
                    cell44.setCellStyle(style);
                    Cell cell55 = row2.createCell(5);
                    cell55.setCellValue(res.getIndirizzo());
                    cell55.setCellStyle(style);
                    Cell cell66 = row2.createCell(6);
                    cell66.setCellValue(res.getCap());
                    cell66.setCellStyle(style);
                    Cell cell77 = row2.createCell(7);
                    cell77.setCellValue(res.getProvincia());
                    cell77.setCellStyle(style);
                    Cell cell88 = row2.createCell(8);
                    cell88.setCellValue(res.getCab_comune() + " - " + res.getCitta());
                    cell88.setCellStyle(style);
                    Cell cell99 = row2.createCell(9);
                    cell99.setCellValue(formatCountry_cru(res.getPaese_estero_residenza(), country));
                    cell99.setCellStyle(style);
                    boolean firsttime = true;
                    for (int j = 0; j < resultagent.size(); j++) {
                        if (resultagent.get(j).getNdg_rappresentante().equals(res.getNdg())) {
                            Company resagent = resultagent.get(j);
                            if (firsttime) {
                                firsttime = false;
                                cnt++;
                                Row row3 = sheet.createRow(cnt);
                                Cell cell00 = row3.createCell(1);
                                cell00.setCellStyle(style2);
                                sheet.addMergedRegion(new CellRangeAddress(cnt, cnt, 1, 9));
                                cell00.setCellValue("Agent");
                                cnt++;
                                Row row5 = sheet.createRow(cnt);
                                Cell cella = row5.createCell(1);
                                cella.setCellValue("Code");
                                cella.setCellStyle(style1);
                                Cell cella2 = row5.createCell(2);
                                cella2.setCellValue("Surname");
                                cella2.setCellStyle(style1);
                                Cell cella3 = row5.createCell(3);
                                cella3.setCellValue("Name");
                                cella3.setCellStyle(style1);
                                Cell cella4 = row5.createCell(4);
                                cella4.setCellValue("Sex");
                                cella4.setCellStyle(style1);
                                Cell cella5 = row5.createCell(5);
                                cella5.setCellValue("Tax Code");
                                cella5.setCellStyle(style1);
                                Cell cella6 = row5.createCell(6);
                                cella6.setCellValue("Address");
                                cella6.setCellStyle(style1);
                                Cell cella7 = row5.createCell(7);
                                cella7.setCellValue("Zip Code");
                                cella7.setCellStyle(style1);
                                Cell cella8 = row5.createCell(8);
                                cella8.setCellValue("District");
                                cella8.setCellStyle(style1);
                                Cell cella9 = row5.createCell(9);
                                cella9.setCellValue("CAB - City");
                                cella9.setCellStyle(style1);
                                Cell cella10 = row5.createCell(10);
                                cella10.setCellValue("Country");
                                cella10.setCellStyle(style1);
                                Cell cella11 = row5.createCell(11);
                                cella11.setCellValue("Date Of Birth");
                                cella11.setCellStyle(style1);
                                Cell cella12 = row5.createCell(12);
                                cella12.setCellValue("City Of Birth");
                                cella12.setCellStyle(style1);
                                Cell cella13 = row5.createCell(13);
                                cella13.setCellValue("District Of Birth");
                                cella13.setCellStyle(style1);
                                Cell cella14 = row5.createCell(14);
                                cella14.setCellValue("Document IDentity");
                                cella14.setCellStyle(style1);
                                Cell cella15 = row5.createCell(15);
                                cella15.setCellValue("Number Doc ID");
                                cella15.setCellStyle(style1);
                                Cell cella16 = row5.createCell(16);
                                cella16.setCellValue("Issue Date");
                                cella16.setCellStyle(style1);
                                Cell cella17 = row5.createCell(17);
                                cella17.setCellValue("Expiration Date");
                                cella17.setCellStyle(style1);
                                Cell cella18 = row5.createCell(18);
                                cella18.setCellValue("Issued by");
                                cella18.setCellStyle(style1);
                                Cell cella19 = row5.createCell(19);
                                cella19.setCellValue("Place OF issue");
                                cella19.setCellStyle(style1);
                            }
                            cnt++;
                            Row row4 = sheet.createRow(cnt);
                            Cell cella1 = row4.createCell(1);
                            cella1.setCellValue(resagent.getNdg());
                            cella1.setCellStyle(style);
                            Cell cella22 = row4.createCell(2);
                            cella22.setCellValue(resagent.getCognome());
                            cella22.setCellStyle(style);
                            Cell cella33 = row4.createCell(3);
                            cella33.setCellValue(resagent.getNome());
                            cella33.setCellStyle(style);
                            Cell cella44 = row4.createCell(4);
                            cella44.setCellValue(resagent.getSesso());
                            cella44.setCellStyle(style);
                            Cell cella55 = row4.createCell(5);
                            cella55.setCellValue(resagent.getCodice_fiscale());
                            cella55.setCellStyle(style);
                            Cell cella66 = row4.createCell(6);
                            cella66.setCellValue(resagent.getCap());
                            cella66.setCellStyle(style);
                            Cell cella77 = row4.createCell(7);
                            cella77.setCellValue(resagent.getIndirizzo());
                            cella77.setCellStyle(style);
                            Cell cella88 = row4.createCell(8);
                            cella88.setCellValue(resagent.getProvincia());
                            cella88.setCellStyle(style);
                            Cell cella99 = row4.createCell(9);
                            cella99.setCellValue(resagent.getCab_comune() + " - " + resagent.getCitta());
                            cella99.setCellStyle(style);
                            Cell cella100 = row4.createCell(10);
                            cella100.setCellValue(formatCountry_cru(res.getPaese_estero_residenza(), country));
                            cella100.setCellStyle(style);
                            Cell cella110 = row4.createCell(11);
                            cella110.setCellValue(resagent.getDt_nascita());
                            cella110.setCellStyle(style);
                            Cell cella120 = row4.createCell(12);
                            cella120.setCellValue(resagent.getComune_nascita());
                            cella120.setCellStyle(style);
                            Cell cella130 = row4.createCell(13);
                            cella130.setCellValue(resagent.getCod_provincia_nascita());
                            cella130.setCellStyle(style);
                            Cell cella140 = row4.createCell(14);
                            cella140.setCellValue(resagent.getTipo_documento());
                            cella140.setCellStyle(style);
                            Cell cella150 = row4.createCell(15);
                            cella150.setCellValue(resagent.getNumero_documento());
                            cella150.setCellStyle(style);
                            Cell cella160 = row4.createCell(16);
                            cella160.setCellValue(resagent.getDt_rilascio());
                            cella160.setCellStyle(style);
                            Cell cella170 = row4.createCell(17);
                            cella170.setCellValue(resagent.getDt_scadenza());
                            cella170.setCellStyle(style);
                            Cell cella180 = row4.createCell(18);
                            cella180.setCellValue(resagent.getAutorita_rilascio());
                            cella180.setCellStyle(style);
                            Cell cella190 = row4.createCell(19);
                            cella190.setCellValue(resagent.getLuogo_rilascio_documento());
                            cella190.setCellStyle(style);
                        }
                    }
                }
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
                try(FileOutputStream fileOut = new FileOutputStream(outputfile)){
                    wb.write(fileOut);
                    fileOut.close();
                }
            }

            String base64 = new String(encodeBase64(readFileToByteArray(outputfile)));
            outputfile.delete();
            return base64;
        } catch (Exception ex) {
            insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
        }
        return null;
    }

    /**
     *
     * @param outputfile
     * @param result
     * @return
     */
    public static String excel_agency_list(File outputfile, ArrayList<Agency> result) {
        try {

            try ( //File out = new File(pathout + outputfile);
                    Workbook wb = new XSSFWorkbook()) {
                Sheet sheet = wb.createSheet("Agency_ist");
                XSSFCellStyle style = (XSSFCellStyle) wb.createCellStyle();
                style.setBorderBottom(THIN);
                style.setBorderTop(THIN);
                style.setBorderRight(THIN);
                style.setBorderLeft(THIN);
                XSSFCellStyle style1 = (XSSFCellStyle) wb.createCellStyle();
                style1.setBorderBottom(THIN);
                style1.setBorderTop(THIN);
                style1.setBorderRight(THIN);
                style1.setBorderLeft(THIN);
                style1.setAlignment(CENTER);
                XSSFFont font = (XSSFFont) wb.createFont();
                font.setFontName(FONT_ARIAL);
                font.setFontHeightInPoints((short) 12);
                font.setBold(true);
                //  font.setColor(HSSFColor.BLUE.index);
                style1.setFont(font);
                XSSFCellStyle style2 = (XSSFCellStyle) wb.createCellStyle();
                style2.setAlignment(CENTER);
                XSSFFont font2 = (XSSFFont) wb.createFont();
                font2.setFontName(FONT_ARIAL);
                font2.setFontHeightInPoints((short) 14);
                font2.setBold(true);
                style2.setFont(font2);
                Row row0 = sheet.createRow(0);
                Cell cell0 = row0.createCell(1);
                cell0.setCellStyle(style2);
                sheet.addMergedRegion(new CellRangeAddress(0, 0, 1, 9));
                cell0.setCellValue("Agency List - Branch: " + filiale);
                Row row = sheet.createRow(1);
                Cell cell = row.createCell(1);
                cell.setCellValue("Code");
                cell.setCellStyle(style1);
                Cell cell2 = row.createCell(2);
                cell2.setCellValue("Description");
                cell2.setCellStyle(style1);
                Cell cell3 = row.createCell(3);
                cell3.setCellValue("Address");
                cell3.setCellStyle(style1);
                Cell cell4 = row.createCell(4);
                cell4.setCellValue("Zip Code");
                cell4.setCellStyle(style1);
                Cell cell5 = row.createCell(5);
                cell5.setCellValue("City");
                cell5.setCellStyle(style1);
                Cell cell6 = row.createCell(6);
                cell6.setCellValue("Phone Number");
                cell6.setCellStyle(style1);
                Cell cell7 = row.createCell(7);
                cell7.setCellValue("Fax Number");
                cell7.setCellStyle(style1);
                Cell cell8 = row.createCell(8);
                cell8.setCellValue("Email Address");
                cell8.setCellStyle(style1);
                Cell cell9 = row.createCell(9);
                cell9.setCellValue("Status");
                cell9.setCellStyle(style1);
                for (int i = 0; i < result.size(); i++) {
                    Row row2 = sheet.createRow(i + 3);
                    Agency res = (Agency) result.get(i);
                    Cell cell1 = row2.createCell(1);
                    cell1.setCellValue(res.getAgenzia());
                    cell1.setCellStyle(style);
                    Cell cell22 = row2.createCell(2);
                    cell22.setCellValue(res.getDe_agenzia());
                    cell22.setCellStyle(style);
                    Cell cell33 = row2.createCell(3);
                    cell33.setCellValue(res.getIndirizzo());
                    cell33.setCellStyle(style);
                    Cell cell44 = row2.createCell(4);
                    cell44.setCellValue(res.getCap());
                    cell44.setCellStyle(style);
                    Cell cell55 = row2.createCell(5);
                    cell55.setCellValue(res.getCitta());
                    cell55.setCellStyle(style);
                    Cell cell66 = row2.createCell(6);
                    cell66.setCellValue(res.getTelefono());
                    cell66.setCellStyle(style);
                    Cell cell77 = row2.createCell(7);
                    cell77.setCellValue(res.getFax());
                    cell77.setCellStyle(style);
                    Cell cell88 = row2.createCell(8);
                    cell88.setCellValue(res.getEmail());
                    cell88.setCellStyle(style);
                    Cell cell99 = row2.createCell(9);
                    cell99.setCellValue(formatStatus_general_cru(res.getFg_annullato()));
                    cell99.setCellStyle(style);
                }
                sheet.autoSizeColumn(1);
                sheet.autoSizeColumn(2);
                sheet.autoSizeColumn(3);
                sheet.autoSizeColumn(4);
                sheet.autoSizeColumn(5);
                sheet.autoSizeColumn(6);
                sheet.autoSizeColumn(7);
                sheet.autoSizeColumn(8);
                sheet.autoSizeColumn(9);
                try(FileOutputStream fileOut = new FileOutputStream(outputfile)){
                    wb.write(fileOut);
                    fileOut.close();
                }
            }
            String base64 = new String(encodeBase64(readFileToByteArray(outputfile)));
            outputfile.delete();
            return base64;
        } catch (Exception ex) {
            insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
        }
        return null;
    }

    /**
     *
     * @param outputfile
     * @param result
     * @return
     */
    public static String excel_blackm_list(File outputfile, ArrayList<BlacklistM> result) {
        try {

            try ( //File out = new File(pathout + outputfile);
                    Workbook wb = new XSSFWorkbook()) {
                Sheet sheet = wb.createSheet("Black_ist");
                XSSFCellStyle style = (XSSFCellStyle) wb.createCellStyle();
                style.setBorderBottom(THIN);
                style.setBorderTop(THIN);
                style.setBorderRight(THIN);
                style.setBorderLeft(THIN);
                XSSFCellStyle style1 = (XSSFCellStyle) wb.createCellStyle();
                style1.setBorderBottom(THIN);
                style1.setBorderTop(THIN);
                style1.setBorderRight(THIN);
                style1.setBorderLeft(THIN);
                style1.setAlignment(CENTER);
                XSSFFont font = (XSSFFont) wb.createFont();
                font.setFontName(FONT_ARIAL);
                font.setFontHeightInPoints((short) 12);
                font.setBold(true);
                //  font.setColor(HSSFColor.BLUE.index);
                style1.setFont(font);
                XSSFCellStyle style2 = (XSSFCellStyle) wb.createCellStyle();
                style2.setAlignment(CENTER);
                XSSFFont font2 = (XSSFFont) wb.createFont();
                font2.setFontName(FONT_ARIAL);
                font2.setFontHeightInPoints((short) 14);
                font2.setBold(true);
                style2.setFont(font2);
                Row row0 = sheet.createRow(0);
                Cell cell0 = row0.createCell(1);
                cell0.setCellStyle(style2);
                sheet.addMergedRegion(new CellRangeAddress(0, 0, 1, 9));
                cell0.setCellValue("Black List - Branch: " + filiale);
                Row row = sheet.createRow(1);
                Cell cell = row.createCell(1);
                cell.setCellValue("Status");
                cell.setCellStyle(style1);
                Cell cell2 = row.createCell(2);
                cell2.setCellValue("Code");
                cell2.setCellStyle(style1);
                Cell cell3 = row.createCell(3);
                cell3.setCellValue("Surname");
                cell3.setCellStyle(style1);
                Cell cell4 = row.createCell(4);
                cell4.setCellValue("Name");
                cell4.setCellStyle(style1);
                Cell cell5 = row.createCell(5);
                cell5.setCellValue("Sex");
                cell5.setCellStyle(style1);
                Cell cell6 = row.createCell(6);
                cell6.setCellValue("Tax");
                cell6.setCellStyle(style1);
                Cell cell7 = row.createCell(7);
                cell7.setCellValue("Country");
                cell7.setCellStyle(style1);
                Cell cell8 = row.createCell(8);
                cell8.setCellValue("City");
                cell8.setCellStyle(style1);
                Cell cell10 = row.createCell(9);
                cell10.setCellValue("Address");
                cell10.setCellStyle(style1);
                Cell cell11 = row.createCell(10);
                cell11.setCellValue("Email");
                cell11.setCellStyle(style1);
                Cell cell12 = row.createCell(11);
                cell12.setCellValue("Phone number");
                cell12.setCellStyle(style1);
                Cell cell13 = row.createCell(12);
                cell13.setCellValue("City Birth");
                cell13.setCellStyle(style1);
                Cell cell14 = row.createCell(13);
                cell14.setCellValue("Country Birth");
                cell14.setCellStyle(style1);
                Cell cell15 = row.createCell(14);
                cell15.setCellValue("Date Birth");
                cell15.setCellStyle(style1);
                Cell cell16 = row.createCell(15);
                cell16.setCellValue("Identification card");
                cell16.setCellStyle(style1);
                Cell cell17 = row.createCell(16);
                cell17.setCellValue("Number");
                cell17.setCellStyle(style1);
                Cell cell18 = row.createCell(17);
                cell18.setCellValue("Issue Date");
                cell18.setCellStyle(style1);
                Cell cell19 = row.createCell(18);
                cell19.setCellValue("Expiration Date");
                cell19.setCellStyle(style1);
                Cell cell20 = row.createCell(19);
                cell20.setCellValue("Issued by");
                cell20.setCellStyle(style1);
                Cell cell21 = row.createCell(20);
                cell21.setCellValue("Place Of Issue");
                cell21.setCellStyle(style1);
                Cell cell22 = row.createCell(21);
                cell22.setCellValue("Message");
                cell22.setCellStyle(style1);
                for (int i = 0; i < result.size(); i++) {
                    Row row2 = sheet.createRow(i + 3);
                    BlacklistM res = (BlacklistM) result.get(i);
                    Cell cell1 = row2.createCell(1);
                    cell1.setCellValue(formatStatus_general_cru(res.getFg_annullato()));
                    cell1.setCellStyle(style);
                    Cell cell222 = row2.createCell(2);
                    cell222.setCellValue(res.getCode());
                    cell222.setCellStyle(style);
                    Cell cell33 = row2.createCell(3);
                    cell33.setCellValue(res.getCognome());
                    cell33.setCellStyle(style);
                    Cell cell44 = row2.createCell(4);
                    cell44.setCellValue(res.getNome());
                    cell44.setCellStyle(style);
                    Cell cell55 = row2.createCell(5);
                    cell55.setCellValue(res.getSesso());
                    cell55.setCellStyle(style);
                    Cell cell66 = row2.createCell(6);
                    cell66.setCellValue(res.getCodfisc());
                    cell66.setCellStyle(style);
                    Cell cell77 = row2.createCell(7);
                    cell77.setCellValue(res.getNazione());
                    cell77.setCellStyle(style);
                    Cell cell88 = row2.createCell(8);
                    cell88.setCellValue(res.getCitta());
                    cell88.setCellStyle(style);
                    Cell cell99 = row2.createCell(9);
                    cell99.setCellValue(res.getIndirizzo());
                    cell99.setCellStyle(style);
                    Cell cell100 = row2.createCell(10);
                    cell100.setCellValue(res.getEmail());
                    cell100.setCellStyle(style);
                    Cell cell110 = row2.createCell(11);
                    cell110.setCellValue(res.getTelefono());
                    cell110.setCellStyle(style);
                    Cell cell120 = row2.createCell(12);
                    cell120.setCellValue(res.getCitta_nascita());
                    cell120.setCellStyle(style);
                    Cell cell130 = row2.createCell(13);
                    cell130.setCellValue(res.getNazione_nascita());
                    cell130.setCellStyle(style);
                    Cell cell140 = row2.createCell(14);
                    cell140.setCellValue(res.getDt_nascita());
                    cell140.setCellStyle(style);
                    Cell cell150 = row2.createCell(15);
                    cell150.setCellValue(res.getTipo_documento());
                    cell150.setCellStyle(style);
                    Cell cell160 = row2.createCell(16);
                    cell160.setCellValue(res.getNumero_documento());
                    cell160.setCellStyle(style);
                    Cell cell170 = row2.createCell(17);
                    cell170.setCellValue(res.getDt_rilascio_documento());
                    cell170.setCellStyle(style);
                    Cell cell180 = row2.createCell(18);
                    cell180.setCellValue(res.getDt_scadenza_documento());
                    cell180.setCellStyle(style);
                    Cell cell190 = row2.createCell(19);
                    cell190.setCellValue(res.getRilasciato_da_documento());
                    cell190.setCellStyle(style);
                    Cell cell200 = row2.createCell(20);
                    cell200.setCellValue(res.getLuogo_rilascio_documento());
                    cell200.setCellStyle(style);
                    Cell cell210 = row2.createCell(21);
                    cell210.setCellValue(res.getText_cru());
                    cell210.setCellStyle(style);
                }
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
                try(FileOutputStream fileOut = new FileOutputStream(outputfile)){
                    wb.write(fileOut);
                    fileOut.close();
                }
            }

            String base64 = new String(encodeBase64(readFileToByteArray(outputfile)));
            outputfile.delete();
            return base64;
        } catch (Exception ex) {
            insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
        }
        return null;
    }

    /**
     *
     * @param outputfile
     * @param result
     * @return
     */
    public static String excel_groupbr_list(File outputfile, ArrayList<String[]> result) {
        try {
            ArrayList<String[]> typegroup = selectgroupbranch();
            try ( //File out = new File(pathout + outputfile);
                    Workbook wb = new XSSFWorkbook()) {
                Sheet sheet = wb.createSheet("BranchGroup_list");
                XSSFCellStyle style = (XSSFCellStyle) wb.createCellStyle();
                style.setBorderBottom(THIN);
                style.setBorderTop(THIN);
                style.setBorderRight(THIN);
                style.setBorderLeft(THIN);
                XSSFCellStyle style1 = (XSSFCellStyle) wb.createCellStyle();
                style1.setBorderBottom(THIN);
                style1.setBorderTop(THIN);
                style1.setBorderRight(THIN);
                style1.setBorderLeft(THIN);
                style1.setAlignment(CENTER);
                XSSFFont font = (XSSFFont) wb.createFont();
                font.setFontName(FONT_ARIAL);
                font.setFontHeightInPoints((short) 12);
                font.setBold(true);
                //  font.setColor(HSSFColor.BLUE.index);
                style1.setFont(font);
                XSSFCellStyle style2 = (XSSFCellStyle) wb.createCellStyle();
                style2.setAlignment(CENTER);
                XSSFFont font2 = (XSSFFont) wb.createFont();
                font2.setFontName(FONT_ARIAL);
                font2.setFontHeightInPoints((short) 14);
                font2.setBold(true);
                style2.setFont(font2);
                Row row0 = sheet.createRow(0);
                Cell cell0 = row0.createCell(1);
                cell0.setCellStyle(style2);
                sheet.addMergedRegion(new CellRangeAddress(0, 0, 1, 9));
                cell0.setCellValue("BranchGroup List - Branch: " + filiale);
                Row row = sheet.createRow(1);
                Cell cell = row.createCell(1);
                cell.setCellValue("Code");
                cell.setCellStyle(style1);
                Cell cell2 = row.createCell(2);
                cell2.setCellValue("Description");
                cell2.setCellStyle(style1);
                Cell cell3 = row.createCell(3);
                cell3.setCellValue("Type");
                cell3.setCellStyle(style1);
                for (int i = 0; i < result.size(); i++) {
                    Row row2 = sheet.createRow(i + 3);
                    String[] res = (String[]) result.get(i);
                    String type = formatAL(res[0], result, 3);
                    String typedes = formatAL(type, typegroup, 1);
                    Cell cell1 = row2.createCell(1);
                    cell1.setCellValue(res[0]);
                    cell1.setCellStyle(style);
                    Cell cell22 = row2.createCell(2);
                    cell22.setCellValue(res[1]);
                    cell22.setCellStyle(style);
                    Cell cell33 = row2.createCell(3);
                    cell33.setCellValue(typedes);
                    cell33.setCellStyle(style);
                }
                sheet.autoSizeColumn(1);
                sheet.autoSizeColumn(2);
                sheet.autoSizeColumn(3);
                try(FileOutputStream fileOut = new FileOutputStream(outputfile)){
                    wb.write(fileOut);
                    fileOut.close();
                }
            }

            String base64 = new String(encodeBase64(readFileToByteArray(outputfile)));
            outputfile.delete();
            return base64;
        } catch (Exception ex) {
            insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
        }
        return null;
    }

    /**
     *
     * @param outputfile
     * @param result
     * @param filiale
     * @return
     */
    public static String excel_creditcard_list(File outputfile, ArrayList<String[]> result, String filiale) {
        try {
            ArrayList<String[]> typegroup = selectgroupbranch();
            try ( //File out = new File(pathout + outputfile);
                    Workbook wb = new XSSFWorkbook()) {
                Sheet sheet = wb.createSheet("CreditCard_list");
                XSSFCellStyle style = (XSSFCellStyle) wb.createCellStyle();
                style.setBorderBottom(THIN);
                style.setBorderTop(THIN);
                style.setBorderRight(THIN);
                style.setBorderLeft(THIN);
                XSSFCellStyle style1 = (XSSFCellStyle) wb.createCellStyle();
                style1.setBorderBottom(THIN);
                style1.setBorderTop(THIN);
                style1.setBorderRight(THIN);
                style1.setBorderLeft(THIN);
                style1.setAlignment(CENTER);
                XSSFFont font = (XSSFFont) wb.createFont();
                font.setFontName(FONT_ARIAL);
                font.setFontHeightInPoints((short) 12);
                font.setBold(true);
                //  font.setColor(HSSFColor.BLUE.index);
                style1.setFont(font);
                XSSFCellStyle style2 = (XSSFCellStyle) wb.createCellStyle();
                style2.setAlignment(CENTER);
                XSSFFont font2 = (XSSFFont) wb.createFont();
                font2.setFontName(FONT_ARIAL);
                font2.setFontHeightInPoints((short) 14);
                font2.setBold(true);
                style2.setFont(font2);
                Row row0 = sheet.createRow(0);
                Cell cell0 = row0.createCell(1);
                cell0.setCellStyle(style2);
                sheet.addMergedRegion(new CellRangeAddress(0, 0, 1, 9));
                cell0.setCellValue("Credit Card List - Branch: " + filiale);
                Row row = sheet.createRow(1);
                Cell cell = row.createCell(1);
                cell.setCellValue("Code");
                cell.setCellStyle(style1);
                Cell cell2 = row.createCell(2);
                cell2.setCellValue("Description");
                cell2.setCellStyle(style1);
                Cell cell3 = row.createCell(3);
                cell3.setCellValue("Status");
                cell3.setCellStyle(style1);
                for (int i = 0; i < result.size(); i++) {
                    Row row2 = sheet.createRow(i + 3);
                    String[] res = (String[]) result.get(i);
                    Cell cell1 = row2.createCell(1);
                    cell1.setCellValue(res[0]);
                    cell1.setCellStyle(style);
                    Cell cell22 = row2.createCell(2);
                    cell22.setCellValue(res[1]);
                    cell22.setCellStyle(style);
                    Cell cell33 = row2.createCell(3);
                    cell33.setCellValue(formatStatus_general_cru(res[2]));
                    cell33.setCellStyle(style);
                }
                sheet.autoSizeColumn(1);
                sheet.autoSizeColumn(2);
                sheet.autoSizeColumn(3);
                try(FileOutputStream fileOut = new FileOutputStream(outputfile)){
                    wb.write(fileOut);
                    fileOut.close();
                }
            }

            String base64 = new String(encodeBase64(readFileToByteArray(outputfile)));
            outputfile.delete();
            return base64;
        } catch (Exception ex) {
            insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
        }
        return null;
    }

    /**
     *
     * @param outputfile
     * @param result
     * @return
     */
    public static String excel_branch_list(File outputfile, ArrayList<Branch> result) {
        try {
            ArrayList<String[]> tipogruppi = Engine.list_group_branch();
            ArrayList<String[]> gruppi = Engine.list_branch_group();
            try ( //File out = new File(pathout + outputfile);
                    Workbook wb = new XSSFWorkbook()) {
                Sheet sheet = wb.createSheet("Branch_list");
                XSSFCellStyle style = (XSSFCellStyle) wb.createCellStyle();
                style.setBorderBottom(THIN);
                style.setBorderTop(THIN);
                style.setBorderRight(THIN);
                style.setBorderLeft(THIN);
                XSSFCellStyle style1 = (XSSFCellStyle) wb.createCellStyle();
                style1.setBorderBottom(THIN);
                style1.setBorderTop(THIN);
                style1.setBorderRight(THIN);
                style1.setBorderLeft(THIN);
                style1.setAlignment(CENTER);
                XSSFFont font = (XSSFFont) wb.createFont();
                font.setFontName(FONT_ARIAL);
                font.setFontHeightInPoints((short) 12);
                font.setBold(true);
                //  font.setColor(HSSFColor.BLUE.index);
                style1.setFont(font);
                XSSFCellStyle style2 = (XSSFCellStyle) wb.createCellStyle();
                style2.setAlignment(CENTER);
                XSSFFont font2 = (XSSFFont) wb.createFont();
                font2.setFontName(FONT_ARIAL);
                font2.setFontHeightInPoints((short) 14);
                font2.setBold(true);
                style2.setFont(font2);
                Row row0 = sheet.createRow(0);
                Cell cell0 = row0.createCell(1);
                cell0.setCellStyle(style2);
                sheet.addMergedRegion(new CellRangeAddress(0, 0, 1, 9));
                cell0.setCellValue("Branch List - Branch: " + filiale);
                Row row = sheet.createRow(1);
                Cell cell = row.createCell(1);
                cell.setCellValue("Code");
                cell.setCellStyle(style1);
                Cell cell2 = row.createCell(2);
                cell2.setCellValue("Description");
                cell2.setCellStyle(style1);
                Cell cell3 = row.createCell(3);
                cell3.setCellValue("Status");
                cell3.setCellStyle(style1);
                Cell cell4 = row.createCell(4);
                cell4.setCellValue("Address");
                cell4.setCellStyle(style1);
                Cell cell5 = row.createCell(5);
                cell5.setCellValue("City");
                cell5.setCellStyle(style1);
                Cell cell66 = row.createCell(6);
                cell66.setCellValue("Zip Code");
                cell66.setCellStyle(style1);
                Cell cell77 = row.createCell(7);
                cell77.setCellValue("Company Operation");
                cell77.setCellStyle(style1);
                Cell cell88 = row.createCell(8);
                cell88.setCellValue("Edit Rate");
                cell88.setCellStyle(style1);
                Cell cell99 = row.createCell(9);
                cell99.setCellValue("CRM");
                cell99.setCellStyle(style1);

                Cell cell140 = row.createCell(10);
                cell140.setCellValue("Date Of Cancellation");
                cell140.setCellStyle(style1);

                for (int x = 0; x < tipogruppi.size(); x++) {
                    Cell cell1gr = row.createCell(x + 11);
                    cell1gr.setCellValue(tipogruppi.get(x)[1]);
                    cell1gr.setCellStyle(style1);
                }

                for (int i = 0; i < result.size(); i++) {
                    Row row2 = sheet.createRow(i + 3);
                    Branch res = (Branch) result.get(i);
                    Cell cell1 = row2.createCell(1);
                    cell1.setCellValue(res.getCod());
                    cell1.setCellStyle(style);
                    Cell cell22 = row2.createCell(2);
                    cell22.setCellValue(res.getDe_branch());
                    cell22.setCellStyle(style);
                    Cell cell33 = row2.createCell(3);
                    cell33.setCellValue(formatStatus_general_cru(res.getFg_annullato()));
                    cell33.setCellStyle(style);
                    Cell cell44 = row2.createCell(4);
                    cell44.setCellValue(res.getAdd_via());
                    cell44.setCellStyle(style);
                    Cell cell55 = row2.createCell(5);
                    cell55.setCellValue(res.getAdd_city());
                    cell55.setCellStyle(style);
                    Cell cell666 = row2.createCell(6);
                    cell666.setCellValue(res.getAdd_cap());
                    cell666.setCellStyle(style);
                    Cell cell777 = row2.createCell(7);
                    if (res.getFg_persgiur().equals("0")) {
                        cell777.setCellValue("Disabled");
                    } else {
                        cell777.setCellValue("Enabled");
                    }
                    cell777.setCellStyle(style);
                    Cell cell888 = row2.createCell(8);
                    if (res.getFg_modrate().equals("0")) {
                        cell888.setCellValue("Disabled");
                    } else {
                        cell888.setCellValue("Enabled");
                    }
                    cell888.setCellStyle(style);
                    Cell cell999 = row2.createCell(9);
                    if (res.getFg_crm().equals("0")) {
                        cell999.setCellValue("Disabled");
                    } else {
                        cell999.setCellValue("Enabled");
                    }
                    cell999.setCellStyle(style);
                    Cell cell1400 = row2.createCell(10);
                    cell1400.setCellValue(res.getDa_annull());
                    cell1400.setCellStyle(style);

                    for (int x = 0; x < tipogruppi.size(); x++) {
                        String select = " ";
                        for (int j = 0; j < gruppi.size(); j++) {
                            if (gruppi.get(j)[2].equals(tipogruppi.get(x)[0])) {
                                String listagruppiValue = res.getListagruppi().get(gruppi.get(j)[2]);
                                if (listagruppiValue != null && listagruppiValue.equals(gruppi.get(j)[0])) {
                                    select = gruppi.get(j)[0] + " - " + gruppi.get(j)[1];
                                    break;
                                }
                            }
                        }
                    
                        Cell cell1gr = row2.createCell(x + 11);
                        cell1gr.setCellValue(select);
                        cell1gr.setCellStyle(style);
                    }
                }
                for (int i = 1; i < 22; i++) {
                    sheet.autoSizeColumn(i);
                }
                try (FileOutputStream fileOut = new FileOutputStream(outputfile)) {
                    wb.write(fileOut);
                }
            }

            String base64 = new String(encodeBase64(readFileToByteArray(outputfile)));
            outputfile.delete();
            return base64;
        } catch (Exception ex) {
            insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
        }
        return null;

    }

    /**
     *
     * @param outputfile
     * @param result
     * @return
     */
    public static String excel_kindtrans_list(File outputfile, ArrayList<CustomerKind> result) {
        try {
            ArrayList<String[]> list_type_customer = list_type_customer();
            ArrayList<String[]> list_type_kind = list_type_kind();
            ArrayList<String[]> list_category_nations = category_nations();
            try ( //File out = new File(pathout + outputfile);
                    Workbook wb = new XSSFWorkbook()) {
                Sheet sheet = wb.createSheet("KindOfTransaction_list");
                XSSFCellStyle style = (XSSFCellStyle) wb.createCellStyle();
                style.setBorderBottom(THIN);
                style.setBorderTop(THIN);
                style.setBorderRight(THIN);
                style.setBorderLeft(THIN);
                XSSFCellStyle style1 = (XSSFCellStyle) wb.createCellStyle();
                style1.setBorderBottom(THIN);
                style1.setBorderTop(THIN);
                style1.setBorderRight(THIN);
                style1.setBorderLeft(THIN);
                style1.setAlignment(CENTER);
                XSSFFont font = (XSSFFont) wb.createFont();
                font.setFontName(FONT_ARIAL);
                font.setFontHeightInPoints((short) 12);
                font.setBold(true);
                //  font.setColor(HSSFColor.BLUE.index);
                style1.setFont(font);
                XSSFCellStyle style2 = (XSSFCellStyle) wb.createCellStyle();
                style2.setAlignment(CENTER);
                XSSFFont font2 = (XSSFFont) wb.createFont();
                font2.setFontName(FONT_ARIAL);
                font2.setFontHeightInPoints((short) 14);
                font2.setBold(true);
                style2.setFont(font2);
                Row row0 = sheet.createRow(0);
                Cell cell0 = row0.createCell(1);
                cell0.setCellStyle(style2);
                sheet.addMergedRegion(new CellRangeAddress(0, 0, 1, 9));
                cell0.setCellValue("KindOfTransaction List - Branch: " + filiale);
                Row row = sheet.createRow(1);
                Cell cell = row.createCell(1);
                cell.setCellValue("Code");
                cell.setCellStyle(style1);
                Cell cell2 = row.createCell(2);
                cell2.setCellValue("Description");
                cell2.setCellStyle(style1);
                Cell cell3 = row.createCell(3);
                cell3.setCellValue("Resident/Not Resident");
                cell3.setCellStyle(style1);
                Cell cell4 = row.createCell(4);
                cell4.setCellValue("Customer Type");
                cell4.setCellStyle(style1);
                Cell cell5 = row.createCell(5);
                cell5.setCellValue("KYC Threshold");
                cell5.setCellStyle(style1);
                Cell cell66 = row.createCell(6);
                cell66.setCellValue("Max Weekly");
                cell66.setCellStyle(style1);
                Cell cell77 = row.createCell(7);
                cell77.setCellValue("Status");
                cell77.setCellStyle(style1);
                Cell cell88 = row.createCell(8);
                cell88.setCellValue("AML Threshold");
                cell88.setCellStyle(style1);
                Cell cell99 = row.createCell(9);
                cell99.setCellValue("Geographic Area");
                cell99.setCellStyle(style1);
                Cell cell100 = row.createCell(10);
                cell100.setCellValue("Print ExtraUE Certification");
                cell100.setCellStyle(style1);
                Cell cell110bis = row.createCell(11);
                cell110bis.setCellValue("ExtraUE Certification Threshold");
                cell110bis.setCellStyle(style1);
                Cell cell110 = row.createCell(12);
                cell110.setCellValue("Upload Required");
                cell110.setCellStyle(style1);
                Cell cell120 = row.createCell(13);
                cell120.setCellValue("Sales Type");
                cell120.setCellStyle(style1);
                Cell cell130 = row.createCell(14);
                cell130.setCellValue("VAT Code");
                cell130.setCellStyle(style1);
                Cell cell140 = row.createCell(15);
                cell140.setCellValue("Stamp Duty Thershold");
                cell140.setCellStyle(style1);
                Cell cell150 = row.createCell(16);
                cell150.setCellValue("Stamp Duty Value");
                cell150.setCellStyle(style1);
                Cell cell160 = row.createCell(17);
                cell160.setCellValue("Stamp Duty Desxcription");
                cell160.setCellStyle(style1);
                for (int i = 0; i < result.size(); i++) {
                    Row row2 = sheet.createRow(i + 3);
                    CustomerKind res = (CustomerKind) result.get(i);
                    Cell cell1 = row2.createCell(1);
                    cell1.setCellValue(res.getTipologia_clienti());
                    cell1.setCellStyle(style);
                    Cell cell22 = row2.createCell(2);
                    cell22.setCellValue(res.getDe_tipologia_clienti());
                    cell22.setCellStyle(style);
                    Cell cell33 = row2.createCell(3);
                    cell33.setCellValue(formatAL(res.getFg_nazionalita(), list_type_kind, 1));
                    cell33.setCellStyle(style);
                    Cell cell44 = row2.createCell(4);
                    cell44.setCellValue(formatAL(res.getFg_tipo_cliente(), list_type_customer, 1));
                    cell44.setCellStyle(style);
                    Cell cell55 = row2.createCell(5);
                    cell55.setCellValue(res.getIp_max_singola_transazione());
                    cell55.setCellStyle(style);
                    Cell cell666 = row2.createCell(6);
                    cell666.setCellValue(res.getIp_max_settimanale());
                    cell666.setCellStyle(style);
                    Cell cell777 = row2.createCell(7);
                    cell777.setCellValue(formatStatus_general_cru(res.getFg_annullato()));
                    cell777.setCellStyle(style);
                    Cell cell888 = row2.createCell(8);
                    cell888.setCellValue(res.getIp_soglia_antiriciclaggio());
                    cell888.setCellStyle(style);
                    Cell cell999 = row2.createCell(9);
                    cell999.setCellValue(formatAL(res.getFg_area_geografica(), list_category_nations, 1));
                    cell999.setCellStyle(style);
                    Cell cell1000 = row2.createCell(10);
                    cell1000.setCellValue(res.getStampa_autocertificazione());
                    cell1000.setCellStyle(style);
                    Cell cell1100 = row2.createCell(11);
                    cell1100.setCellValue(res.getIp_soglia_extraCEE_certification());
                    cell1100.setCellStyle(style);
                    Cell cell1200 = row2.createCell(12);
                    cell1200.setCellValue(res.getFg_uploadobbl());
                    cell1200.setCellStyle(style);
                    Cell cell1300 = row2.createCell(13);
                    cell1300.setCellValue(res.getTipofat());
                    cell1300.setCellStyle(style);
                    Cell cell1400 = row2.createCell(14);
                    cell1400.setCellValue(res.getVatcode());
                    cell1400.setCellStyle(style);
                    Cell cell1500 = row2.createCell(15);
                    cell1500.setCellValue(res.getIp_soglia_bollo());
                    cell1500.setCellStyle(style);
                    Cell cell1600 = row2.createCell(16);
                    cell1600.setCellValue(res.getIp_value_bollo());
                    cell1600.setCellStyle(style);
                    Cell cell1700 = row2.createCell(17);
                    cell1700.setCellValue(res.getDescr_bollo());
                    cell1700.setCellStyle(style);
                }
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
                try(FileOutputStream fileOut = new FileOutputStream(outputfile)){
                    wb.write(fileOut);
                    fileOut.close();
                }
            }

            String base64 = new String(encodeBase64(readFileToByteArray(outputfile)));
            outputfile.delete();
            return base64;
        } catch (Exception ex) {
            insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
        }
        return null;

    }

    /**
     *
     * @param wb
     * @param cell
     */
    public static void setCellColorAndFontColor(Workbook wb, Cell cell) {
        CellStyle style = wb.createCellStyle();
        Font font = wb.createFont();
        font.setBold(true);
        style.setFont(font);
        cell.setCellStyle(style);
    }

    /**
     *
     * @param pathout
     * @param li
     * @param date
     * @param libr
     * @param lius
     * @param cu
     * @return
     */
    public String print_excel_historyrate(String pathout, ArrayList<Rate_history> li, String date, ArrayList<Branch> libr, ArrayList<Users> lius, Currency cu) {
        try {
            String outputfile = new DateTime().toString("yyMMddhhmmssSSS") + "_excel_historyrate.xlsx";
            File out = new File(pathout + outputfile);
            try (XSSFWorkbook wb = new XSSFWorkbook()) {
                XSSFCellStyle bold = wb.createCellStyle();
                XSSFFont font = wb.createFont();
                font.setBold(true);
                bold.setFont(font);
                CellStyle cs = wb.createCellStyle();
                cs.setWrapText(true);
                XSSFSheet sheet = wb.createSheet("excel_historyrate");
                byte[] bytes = decodeBase64(getConf("path.logocl"));
                int pictureIdx = wb.addPicture(bytes, PICTURE_TYPE_PNG);
                CreationHelper helper = wb.getCreationHelper();
                Drawing drawing = sheet.createDrawingPatriarch();
                ClientAnchor anchor = helper.createClientAnchor();
                anchor.setCol1(1); //Column B
                anchor.setRow1(1); //Row 3
                anchor.setCol2(3); //Column C
                anchor.setRow2(5); //Row 4
                Picture pict = drawing.createPicture(anchor, pictureIdx);
                XSSFRow row = sheet.createRow((short) 5);
                XSSFCell cell = row.createCell(1);
                cell.setCellStyle(bold);
                cell.setCellValue("Date Extraction: " + date);
                sheet.addMergedRegion(new CellRangeAddress(5, 5, 1, 3));
                row = sheet.createRow((short) 6);
                cell = row.createCell(1);
                cell.setCellStyle(bold);
                cell.setCellValue("Currency: " + cu.getCode() + " - " + cu.getDescrizione());
                sheet.addMergedRegion(new CellRangeAddress(6, 6, 1, 3));
                row = sheet.createRow((short) 7);
                cell = row.createCell(1);
                cell.setCellStyle(bold);
                cell.setCellValue("Branch");
                cell = row.createCell(2);
                cell.setCellStyle(bold);
                cell.setCellValue("Type");
                cell = row.createCell(3);
                cell.setCellStyle(bold);
                cell.setCellValue("User");
                cell = row.createCell(4);
                cell.setCellStyle(bold);
                cell.setCellValue("Description");
                cell = row.createCell(5);
                cell.setCellStyle(bold);
                cell.setCellValue("Date");
                int rowindex = 7;
                for (int i = 0; i < li.size(); i++) {
                    rowindex++;
                    row = sheet.createRow((short) rowindex);
                    Rate_history rh = li.get(i);
                    cell = row.createCell(1);
                    cell.setCellValue(formatBankBranch(rh.getFiliale(), "BR", null, libr, null).trim());
                    cell = row.createCell(2);
                    cell.setCellValue(rh.formatType(rh.getTipomod()).trim());
                    cell = row.createCell(3);
                    Users us = get_user(rh.getUser().split(" ")[0].trim());
                    cell.setCellValue(us.getCod() + " - " + us.getDe_cognome() + " " + us.getDe_nome());
                    cell = row.createCell(4);
                    cell.setCellValue(rh.getModify().replaceAll("<br>", "\n"));
                    cell.setCellStyle(cs);
                    cell = row.createCell(5);
                    cell.setCellValue(formatStringtoStringDate(rh.getDt_mod(), patternsqldate, patternnormdate));
                }
                sheet.autoSizeColumn(1);
                sheet.autoSizeColumn(2);
                sheet.autoSizeColumn(4);
                sheet.autoSizeColumn(5);
                sheet.setColumnWidth(3, 10000);
                try(FileOutputStream fileOut = new FileOutputStream(outputfile)){
                    wb.write(fileOut);
                    fileOut.close();
                }
            }
            String base64 = new String(encodeBase64(readFileToByteArray(out)));

            out.delete();
            return base64;
        } catch (Exception ex) {
            insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
        }
        return null;
    }

    //vins
}
