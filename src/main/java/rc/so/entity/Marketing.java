/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rc.so.entity;

import rc.so.db.Db_Master;
import static rc.so.util.Constant.formatdataCell;
import static rc.so.util.Constant.formatdataCellint;
import static rc.so.util.Engine.formatSex;
import static rc.so.util.Engine.insertTR;
import static rc.so.util.Utility.formatAL;
import static rc.so.util.Utility.formatAL_city;
import static rc.so.util.Utility.generaId;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import static java.lang.Thread.currentThread;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import static org.apache.commons.codec.binary.Base64.encodeBase64;
import static org.apache.commons.io.FileUtils.readFileToByteArray;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import static org.apache.commons.lang3.builder.ToStringStyle.JSON_STYLE;
import static org.apache.poi.hssf.usermodel.HSSFFont.FONT_ARIAL;
import static org.apache.poi.ss.usermodel.BorderStyle.THIN;
import static org.apache.poi.ss.usermodel.HorizontalAlignment.LEFT;
import static org.apache.poi.ss.usermodel.HorizontalAlignment.RIGHT;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFDataFormat;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.joda.time.DateTime;

/**
 *
 * @author rcosco
 */
public class Marketing {

    String codcl, filiale, data, codtr;

    Client cl;
    DateTime dt;

    public DateTime getDt() {
        return dt;
    }

    public void setDt(DateTime dt) {
        this.dt = dt;
    }

    public String getCodtr() {
        return codtr;
    }

    public void setCodtr(String codtr) {
        this.codtr = codtr;
    }

    public String getCodcl() {
        return codcl;
    }

    public void setCodcl(String codcl) {
        this.codcl = codcl;
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

    public Client getCl() {
        return cl;
    }

    public void setCl(Client cl) {
        this.cl = cl;
    }

    public static String receiptexcel(String path, List<Marketing> complete) {

        try {

            Db_Master db = new Db_Master();
            ArrayList<String[]> nazioni = db.country();
            ArrayList<String[]> city = db.city_Italy_APM();
            ArrayList<String[]> array_identificationCard = db.identificationCard();
            db.closeDB();

            File xlsx = new File(path + generaId(50) + "MarketingConsent.xls");
            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet("Marketing Consent");

            XSSFFont font = workbook.createFont();
            font.setFontName(FONT_ARIAL);
            font.setFontHeightInPoints((short) 12);
            font.setBold(true);

            XSSFCellStyle style1 = workbook.createCellStyle();
            style1.setFont(font);

            XSSFFont font2 = workbook.createFont();
            font2.setFontName(FONT_ARIAL);
            font2.setFontHeightInPoints((short) 12);

            XSSFCellStyle style2 = workbook.createCellStyle();
            style2.setFont(font2);

            XSSFFont font3 = workbook.createFont();
            font3.setFontName(FONT_ARIAL);
            font3.setFontHeightInPoints((short) 10);
            font3.setBold(true);

            XSSFCellStyle style3 = workbook.createCellStyle();
            style3.setFont(font3);
            style3.setAlignment(RIGHT);
            style3.setBorderTop(THIN);
            style3.setBorderBottom(THIN);

            XSSFCellStyle style3left = workbook.createCellStyle();
            style3left.setFont(font3);
            style3left.setAlignment(LEFT);
            style3left.setBorderTop(THIN);
            style3left.setBorderBottom(THIN);

            XSSFFont font4 = workbook.createFont();
            font4.setFontName(FONT_ARIAL);
            font4.setFontHeightInPoints((short) 10);

            XSSFCellStyle style4 = workbook.createCellStyle();
            style4.setFont(font4);
            style4.setAlignment(RIGHT);
            style4.setBorderTop(THIN);
            style4.setBorderBottom(THIN);

            XSSFCellStyle style4left = workbook.createCellStyle();
            style4left.setFont(font4);
            style4left.setAlignment(LEFT);
            style4left.setBorderTop(THIN);
            style4left.setBorderBottom(THIN);

            XSSFCellStyle cellStylenum = workbook.createCellStyle();

            XSSFDataFormat hssfDataFormat = workbook.createDataFormat();
            cellStylenum.setFont(font4);
            cellStylenum.setDataFormat(hssfDataFormat.getFormat(formatdataCell));
            cellStylenum.setAlignment(RIGHT);
            cellStylenum.setBorderTop(THIN);
            cellStylenum.setBorderBottom(THIN);

            XSSFCellStyle cellStylenumint = workbook.createCellStyle();
            cellStylenumint.setFont(font4);
            cellStylenumint.setDataFormat(hssfDataFormat.getFormat(formatdataCellint));
            cellStylenumint.setAlignment(RIGHT);
            cellStylenumint.setBorderTop(THIN);
            cellStylenumint.setBorderBottom(THIN);

            XSSFCellStyle style3num = workbook.createCellStyle();
            style3num.setFont(font3);
            style3num.setAlignment(RIGHT);
            style3num.setBorderTop(THIN);
            style3num.setBorderBottom(THIN);
            style3num.setDataFormat(hssfDataFormat.getFormat(formatdataCell));

            XSSFCellStyle style3numint = workbook.createCellStyle();
            style3numint.setFont(font3);
            style3numint.setAlignment(RIGHT);
            style3numint.setBorderTop(THIN);
            style3numint.setBorderBottom(THIN);
            style3numint.setDataFormat(hssfDataFormat.getFormat(formatdataCellint));

            XSSFRow rowP = sheet.createRow(1);
            XSSFCell cl = rowP.createCell(1);
            cl.setCellStyle(style1);
            cl.setCellValue("MARKETING CONSENT");

            XSSFRow intest = sheet.createRow(5);

            for (int i = 0; i < 24; i++) {
                XSSFCell f2 = intest.createCell(i);
                f2.setCellStyle(style3left);

                switch (i) {
                    case 0:
                        f2.setCellValue("Branch");
                        break;
                    case 1:
                        f2.setCellValue("Date");
                        break;
                    case 2:
                        f2.setCellValue("Surname");
                        break;
                    case 3:
                        f2.setCellValue("Name");
                        break;
                    case 4:
                        f2.setCellValue("Sex");
                        break;
                    case 5:
                        f2.setCellValue("Tax Code");
                        break;
                    case 6:
                        f2.setCellValue("Country");
                        break;
                    case 7:
                        f2.setCellValue("City");
                        break;
                    case 8:
                        f2.setCellValue("Address");
                        break;
                    case 9:
                        f2.setCellValue("Zip Code");
                        break;
                    case 10:
                        f2.setCellValue("District");
                        break;
                    case 11:
                        f2.setCellValue("Client Is PEP");
                        break;
                    case 12:
                        f2.setCellValue("Place of Birth - City");
                        break;
                    case 13:
                        f2.setCellValue("Place of Birth - District");
                        break;
                    case 14:
                        f2.setCellValue("Place of Birth - Country");
                        break;
                    case 15:
                        f2.setCellValue("Place of Birth - Date");
                        break;
                    case 16:
                        f2.setCellValue("Identification Card");
                        break;
                    case 17:
                        f2.setCellValue("Identification Card - Number");
                        break;
                    case 18:
                        f2.setCellValue("Identification Card - Issue Date");
                        break;
                    case 19:
                        f2.setCellValue("Identification Card - Expiration Date");
                        break;
                    case 20:
                        f2.setCellValue("Identification Card - Issued By");
                        break;
                    case 21:
                        f2.setCellValue("Identification Card - Place of Issue");
                        break;
                    case 22:
                        f2.setCellValue("Email");
                        break;
                    case 23:
                        f2.setCellValue("Phone Number");
                        break;
                }

            }

            AtomicInteger indicecontenuto = new AtomicInteger(6);

            for (int x = 0; x < complete.size(); x++) {
                Marketing clientok = complete.get(x);
                Client finalcl = clientok.getCl();
                XSSFRow contentrow = sheet.createRow(indicecontenuto.get());
                indicecontenuto.addAndGet(1);
                for (int i = 0; i < 24; i++) {
                    XSSFCell contentcell = contentrow.createCell(i);
                    switch (i) {
                        case 0:
                            contentcell.setCellStyle(style4left);
                            contentcell.setCellValue(clientok.getFiliale());
                            break;
                        case 1:
                            contentcell.setCellStyle(style4left);
                            contentcell.setCellValue(clientok.getData());
                            break;
                        case 2:
                            contentcell.setCellStyle(style4left);
                            contentcell.setCellValue(finalcl.getCognome().toUpperCase());
                            break;
                        case 3:
                            contentcell.setCellStyle(style4left);
                            contentcell.setCellValue(finalcl.getNome().toUpperCase());
                            break;
                        case 4:
                            contentcell.setCellStyle(style4left);
                            contentcell.setCellValue(formatSex(finalcl.getSesso()).toUpperCase());
                            break;
                        case 5:
                            contentcell.setCellStyle(style4left);
                            contentcell.setCellValue(finalcl.getCodfisc().toUpperCase());
                            break;
                        case 6:
                            String country = formatAL(finalcl.getNazione(), nazioni, 1);
                            contentcell.setCellStyle(style4left);
                            contentcell.setCellValue(country.toUpperCase());
                            break;
                        case 7:
                            String citycl = formatAL_city(finalcl.getCitta(), city);
                            contentcell.setCellStyle(style4left);
                            contentcell.setCellValue(citycl.toUpperCase());
                            break;
                        case 8:
                            contentcell.setCellStyle(style4left);
                            contentcell.setCellValue(finalcl.getIndirizzo().toUpperCase());
                            break;
                        case 9:
                            contentcell.setCellStyle(style4left);
                            contentcell.setCellValue(finalcl.getCap().toUpperCase());
                            break;
                        case 10:
                            contentcell.setCellStyle(style4left);
                            contentcell.setCellValue(finalcl.getProvincia().toUpperCase());
                            break;
                        case 11:
                            contentcell.setCellStyle(style4left);
                            contentcell.setCellValue(finalcl.getPep().toUpperCase());
                            break;
                        case 12:
                            String citycl_na = formatAL_city(finalcl.getCitta_nascita(), city);
                            contentcell.setCellStyle(style4left);
                            contentcell.setCellValue(citycl_na.toUpperCase());
                            break;
                        case 13:
                            contentcell.setCellStyle(style4left);
                            contentcell.setCellValue(finalcl.getProvincia_nascita().toUpperCase());
                            break;
                        case 14:
                            String country_na = formatAL(finalcl.getNazione_nascita(), nazioni, 1);
                            contentcell.setCellStyle(style4left);
                            contentcell.setCellValue(country_na.toUpperCase());
                            break;
                        case 15:
                            contentcell.setCellStyle(style4left);
                            contentcell.setCellValue(finalcl.getDt_nascita());
                            break;
                        case 16:
                            contentcell.setCellStyle(style4left);
                            contentcell.setCellValue(formatAL(finalcl.getTipo_documento(), array_identificationCard, 1));
                            break;
                        case 17:
                            contentcell.setCellStyle(style4left);
                            contentcell.setCellValue(finalcl.getNumero_documento().toUpperCase());
                            break;
                        case 18:
                            contentcell.setCellStyle(style4left);
                            contentcell.setCellValue(finalcl.getDt_rilascio_documento().toUpperCase());
                            break;
                        case 19:
                            contentcell.setCellStyle(style4left);
                            contentcell.setCellValue(finalcl.getDt_scadenza_documento().toUpperCase());
                            break;
                        case 20:
                            contentcell.setCellStyle(style4left);
                            contentcell.setCellValue(finalcl.getRilasciato_da_documento().toUpperCase());
                            break;
                        case 21:
                            contentcell.setCellStyle(style4left);
                            contentcell.setCellValue(finalcl.getLuogo_rilascio_documento().toUpperCase());
                            break;
                        case 22:
                            contentcell.setCellStyle(style4left);
                            contentcell.setCellValue(finalcl.getEmail().toLowerCase());
                            break;
                        case 23:
                            contentcell.setCellStyle(style4left);
                            contentcell.setCellValue(finalcl.getTelefono().toUpperCase());
                            break;
                    }
                }

            }
            
            
            for (int i = 0; i < 24; i++) {
                sheet.autoSizeColumn(i);
            }

            try (FileOutputStream out = new FileOutputStream(xlsx)) {
                workbook.write(out);
            }
            String base64 = new String(encodeBase64(readFileToByteArray(xlsx)));
            xlsx.delete();
            return base64;
        } catch (IOException ex) {
            insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
        }
        return null;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, JSON_STYLE);
    }
}
