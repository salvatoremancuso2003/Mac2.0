/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rc.so.excel;

import rc.so.db.Db_Master;
import static rc.so.util.Utility.fd;
import static rc.so.util.Utility.generaId;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.ResultSet;
import static java.sql.ResultSet.CONCUR_UPDATABLE;
import static java.sql.ResultSet.TYPE_SCROLL_INSENSITIVE;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import static java.util.stream.Collectors.toList;
import static org.apache.commons.codec.binary.Base64.encodeBase64;
import static org.apache.commons.io.FileUtils.readFileToByteArray;
import static org.apache.commons.lang3.exception.ExceptionUtils.getStackTrace;
import static org.apache.poi.ss.usermodel.BorderStyle.THIN;
import static org.apache.poi.ss.usermodel.FontUnderline.SINGLE;
import static org.apache.poi.ss.usermodel.HorizontalAlignment.LEFT;
import static org.apache.poi.ss.usermodel.HorizontalAlignment.RIGHT;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFDataFormat;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author rcosco
 */
public class Dailyerror {

    public String create(String path, ArrayList<String> branch, String datad1, String datad2) {
        List<Rep> out = new ArrayList<>();
        try {
            Db_Master db1 = new Db_Master();
            try ( Statement st1 = db1.getC().createStatement(TYPE_SCROLL_INSENSITIVE, CONCUR_UPDATABLE)) {
                String sql = "SELECT * FROM macreport.dailyerror WHERE filiale<>'000' ";
                String filwhere = "";
                for (int i = 0; i < branch.size(); i++) {
                    filwhere = filwhere + "(filiale='" + branch.get(i) + "') OR ";
                }
                if (filwhere.length() > 3) {
                    sql = sql + " AND (" + filwhere.substring(0, filwhere.length() - 3).trim() + ") ";
                }
                sql = sql + "AND STR_TO_DATE(DATA, '%d/%c/%Y') >= '" + datad1 + " 00:00:00' ";
                sql = sql + "AND STR_TO_DATE(DATA, '%d/%c/%Y') <= '" + datad2 + " 23:59:59' ";
                sql = sql + " ORDER BY filiale,STR_TO_DATE(DATA, '%d/%c/%Y')";
                ResultSet rs1 = st1.executeQuery(sql);
                while (rs1.next()) {
                    out.add(new Rep(rs1.getString(1), rs1.getString(2), rs1.getString(3), rs1.getString(4), rs1.getString(5), rs1.getString(6), rs1.getString(7)));
                }
                rs1.close();
            }
            db1.closeDB();
        } catch (Exception e) {
            System.err.println("ERR: " + getStackTrace(e));
            return null;
        }

        List<String> solofiliali = out.stream().map(r -> r.getFiliale()).distinct().collect(toList());

        if (!solofiliali.isEmpty()) {
            XSSFWorkbook workbook = new XSSFWorkbook();

            XSSFFont font1 = workbook.createFont();
            font1.setFontName("Calibri");
            font1.setFontHeight(11.00);
            font1.setBold(true);
            font1.setUnderline(SINGLE);

            XSSFFont font2 = workbook.createFont();
            font2.setFontName("Calibri");
            font2.setFontHeight(11.00);
            font2.setBold(false);

            XSSFCellStyle style1 = workbook.createCellStyle();
            style1.setFont(font1);
            style1.setAlignment(LEFT);
            style1.setBorderTop(THIN);
            style1.setBorderRight(THIN);
            style1.setBorderBottom(THIN);
            style1.setBorderLeft(THIN);

            XSSFCellStyle style2 = workbook.createCellStyle();
            style2.setAlignment(LEFT);
            style2.setBorderTop(THIN);
            style2.setBorderRight(THIN);
            style2.setBorderBottom(THIN);
            style2.setBorderLeft(THIN);
            style2.setFont(font2);
            XSSFCellStyle style3 = workbook.createCellStyle();
            XSSFDataFormat hssfDataFormat = workbook.createDataFormat();
            style3.setDataFormat(hssfDataFormat.getFormat("#,#.00"));
            style3.setAlignment(RIGHT);
            style3.setBorderTop(THIN);
            style3.setBorderRight(THIN);
            style3.setBorderBottom(THIN);
            style3.setBorderLeft(THIN);
            style3.setFont(font2);

            solofiliali.forEach(f -> {

                List<Rep> content = out.stream().filter(r -> r.getFiliale().equals(f)).collect(toList());
                if (!content.isEmpty()) {

                    XSSFSheet sheet = workbook.createSheet(f);
                    //INDICE

                    XSSFRow row0 = sheet.createRow(0);
                    XSSFCell c0 = row0.createCell(1);
                    c0.setCellStyle(style1);
                    c0.setCellValue("FILIALE: " + f);

                    XSSFRow rowP = sheet.createRow(2);
                    XSSFCell c1 = rowP.createCell(1);
                    c1.setCellStyle(style1);
                    c1.setCellValue("DATA");
                    XSSFCell c2 = rowP.createCell(2);
                    c2.setCellValue("REPORT BSI");
                    c2.setCellStyle(style1);
                    XSSFCell c3 = rowP.createCell(3);
                    c3.setCellValue("REPORT DAILY");
                    c3.setCellStyle(style1);
                    XSSFCell c4 = rowP.createCell(4);
                    c4.setCellValue("DIFFERENZA €");
                    c4.setCellStyle(style1);
                    XSSFCell c5 = rowP.createCell(5);
                    c5.setCellValue("TIPO TR");
                    c5.setCellStyle(style1);
                    XSSFCell c6 = rowP.createCell(6);
                    c6.setCellValue("NOTE");
                    c6.setCellStyle(style1);
                    AtomicInteger int1 = new AtomicInteger(3);
                    content.forEach(c -> {
                        XSSFRow rowD = sheet.createRow(int1.get());
                        int1.addAndGet(1);
                        XSSFCell ca1 = rowD.createCell(1);
                        ca1.setCellStyle(style2);
                        ca1.setCellValue(c.getData());
                        XSSFCell ca2 = rowD.createCell(2);
                        ca2.setCellStyle(style3);
                        ca2.setCellValue(fd(c.getBsi()));
                        XSSFCell ca3 = rowD.createCell(3);
                        ca3.setCellStyle(style3);
                        ca3.setCellValue(fd(c.getDai()));
                        XSSFCell ca4 = rowD.createCell(4);
                        ca4.setCellStyle(style3);
                        ca4.setCellValue(fd(c.getDiff()));
                        XSSFCell ca5 = rowD.createCell(5);
                        ca5.setCellStyle(style2);
                        ca5.setCellValue("");
                        XSSFCell ca6 = rowD.createCell(6);
                        ca6.setCellStyle(style2);
                        ca6.setCellValue("");
                    });

                    sheet.autoSizeColumn(1);
                    sheet.autoSizeColumn(2);
                    sheet.autoSizeColumn(3);
                    sheet.autoSizeColumn(4);
                    sheet.autoSizeColumn(5);
                    sheet.autoSizeColumn(6);
                }
            });
            try {
                File pdffile = new File(path + generaId(50) + "C_Error Recap.xlsx");
                String base64;
                try ( FileOutputStream xls = new FileOutputStream(pdffile)) {
                    workbook.write(xls);
                    base64 = new String(encodeBase64(readFileToByteArray(pdffile)));
                }
                pdffile.delete();
                return base64;
            } catch (Exception e) {
                System.err.println("ERR: " + getStackTrace(e));
            }

        }
        return null;
    }
}

class Rep {

    String filiale, data, bsi, dai, diff, tipo, note;

    public Rep(String filiale, String data, String bsi, String dai, String diff, String tipo, String note) {
        this.filiale = filiale;
        this.data = data;
        this.bsi = bsi;
        this.dai = dai;
        this.diff = diff;
        this.tipo = tipo;
        this.note = note;
    }

    public String getFiliale() {
        return filiale;
    }

    public void setFiliale(String filiale) {
        this.filiale = filiale;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getBsi() {
        return bsi;
    }

    public void setBsi(String bsi) {
        this.bsi = bsi;
    }

    public String getDai() {
        return dai;
    }

    public void setDai(String dai) {
        this.dai = dai;
    }

    public String getDiff() {
        return diff;
    }

    public void setDiff(String diff) {
        this.diff = diff;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

}
