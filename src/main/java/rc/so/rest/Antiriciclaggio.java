package rc.so.rest;

import rc.so.db.Db_Master;
import rc.so.entity.Client;
import static rc.so.util.Constant.codnaz;
import static rc.so.util.Engine.getConf;
import static rc.so.util.Engine.insertTR;
import static rc.so.util.Utility.formatAL;
import static rc.so.util.Utility.formatALN;
import static rc.so.util.Utility.formatStringtoStringDate;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import static java.lang.Thread.currentThread;
import java.sql.ResultSet;
import java.util.ArrayList;
import static org.apache.commons.codec.binary.Base64.decodeBase64;
import static org.apache.commons.codec.binary.Base64.encodeBase64;
import static org.apache.commons.io.FileUtils.readFileToByteArray;
import static org.apache.commons.lang3.StringUtils.leftPad;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import static org.apache.poi.ss.usermodel.WorkbookFactory.create;
import org.joda.time.DateTime;

/**
 *
 * @author rcosco
 */
public class Antiriciclaggio {
        
    /**
     *
     * @param pathtemp
     * @param data_da
     * @param data_a
     * @return
     */
    public static String registrazione(String pathtemp,String data_da, String data_a) {
        try {
            InputStream inp = new ByteArrayInputStream(decodeBase64(getConf("path.anti.regi")));
            Workbook wb = create(inp);
            Sheet sheet = wb.getSheetAt(0);
            Db_Master db1 = new Db_Master();
            ResultSet rsCl = db1.getSogliaTipologiaCLRegistrazione();
            int indiceRiga = 1;
            while (rsCl.next()) {
                
                ResultSet rs = db1.getTransazioni(rsCl.getDouble(2), leftPad(rsCl.getString(1),3,"0"), data_da, data_a);
                while (rs.next()) {
                    ResultSet rsCommission = db1.getCommissione(rs.getString("cod"));
                    while (rsCommission.next()) {
                        Row row = sheet.getRow(indiceRiga);
                        if (row == null) {
                            row = sheet.createRow(indiceRiga);
                        }
                        Cell cell0 = row.createCell(0);
                        cell0.setCellValue("");
                        Cell cell = row.createCell(1);
                        cell.setCellValue(formatStringtoStringDate(rs.getString("data"), "yyyy-MM-dd HH:mm:ss", "dd/MM/yyyy"));
                        Cell cell2 = row.createCell(2);
                        cell2.setCellValue("10");
                        Cell cell3 = row.createCell(3);
                        Cell cell4 = row.createCell(4);
                        if (rs.getString("tipotr").equalsIgnoreCase("S")) {
                            cell3.setCellValue("DB");
                            cell4.setCellValue("A");
                        } else {// per il BUY
                            cell3.setCellValue("DC");
                            cell4.setCellValue("D");
                        }
                        Cell cell5 = row.createCell(5);
                        cell5.setCellValue(""); // VOCE ==> SEMPRE VUOTOP
                        Cell cell6 = row.createCell(6);
                        cell6.setCellValue(db1.getCodiceValuta(rsCommission.getString("valuta"))); // CODICE VALUTA
                        Cell cell7 = row.createCell(7);
                        cell7.setCellValue(rsCommission.getString("net").replaceAll("\\.", ",")); // 
                        Cell cell8 = row.createCell(8);
                        cell8.setCellValue(rsCommission.getString("net").replaceAll("\\.", ","));
                        Cell cell9 = row.createCell(9);
                        cell9.setCellValue("");
                        Cell cell10 = row.createCell(10);
                        cell10.setCellValue("");
                        Cell cell11 = row.createCell(11);
                        Cell cell12 = row.createCell(12);
                        if (rs.getString("tipocliente").equals("003")) {
                            cell11.setCellValue(db1.getNDGSocieta(rs.getString("cl_cod")));
                            cell12.setCellValue(rs.getString("cl_cod"));
                        } else {
                            cell11.setCellValue("1" + rs.getString("filiale") + rs.getString("id"));
                            cell12.setCellValue("");
                        }

                        Cell cell13 = row.createCell(13);
                        cell13.setCellValue("0");
                        Cell cell14 = row.createCell(14);
                        cell14.setCellValue("0");
                        Cell cell15 = row.createCell(15);
                        cell15.setCellValue("");
                        Cell cell16 = row.createCell(16);
                        cell16.setCellValue("");
                        Cell cell17 = row.createCell(17);
                        cell17.setCellValue("");
                        Cell cell18 = row.createCell(18);
                        cell18.setCellValue("");
                        Cell cell19 = row.createCell(19);
                        cell19.setCellValue("");
                        Cell cell20 = row.createCell(20);
                        cell20.setCellValue("");
                        Cell cell21 = row.createCell(21);
                        cell21.setCellValue("");
                        Cell cell22 = row.createCell(22);
                        cell22.setCellValue("");
                        Cell cell23 = row.createCell(23);
                        cell23.setCellValue("");
                        Cell cell24 = row.createCell(24);
                        cell24.setCellValue("");
                        Cell cell25 = row.createCell(25);
                        cell25.setCellValue("");
                        Cell cell26 = row.createCell(26);
                        cell26.setCellValue("");
                        Cell cell27 = row.createCell(27);
                        cell27.setCellValue("");
                        Cell cell28 = row.createCell(28);
                        cell28.setCellValue("");
                        Cell cell29 = row.createCell(29);
                        cell29.setCellValue("");
                        Cell cell30 = row.createCell(30);
                        cell30.setCellValue("");
                        Cell cell31 = row.createCell(31);
                        cell31.setCellValue("");
                        Cell cell32 = row.createCell(32);
                        cell32.setCellValue("");
                        Cell cell33 = row.createCell(33);
                        cell33.setCellValue("");
                        Cell cell34 = row.createCell(34);
                        cell34.setCellValue("");
                        Cell cell35 = row.createCell(35);
                        cell35.setCellValue("1" + rs.getString("filiale"));
                        indiceRiga++;
                    }
                }
            }
            db1.closeDB();
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
            // Write the output to a file
            String outputfile = new DateTime().toString("yyMMddhhmmssSSS") + "_ana.xls";
            File xlsout = new File(pathtemp + outputfile);
            try (FileOutputStream fileOut = new FileOutputStream(xlsout)) {
                wb.write(fileOut);
            }
            String base64 = new String(encodeBase64(readFileToByteArray(xlsout)));
            xlsout.delete();
            return base64;
        } catch (Exception ex) {
            insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
        }
        return null;
    }

    /**
     *
     * @param pathtemp
     * @param data_da
     * @param data_a
     * @return
     */
    public static String anagrafica(String pathtemp, String data_da, String data_a) {
        try {
           
            InputStream inp = new ByteArrayInputStream(decodeBase64(getConf("path.anti.anag")));
            Workbook wb = create(inp);
            Sheet sheet = wb.getSheetAt(0);
            Db_Master db1 = new Db_Master();
            ArrayList<String[]> coddoc = db1.identificationCard();
            ArrayList<String[]> city = db1.city_Italy_APM();
            ResultSet rsCl = db1.getSogliaTipologiaCL();
            int indiceRiga = 1;
            while (rsCl.next()) {
                ResultSet rs = db1.getTransazioni(rsCl.getDouble(2), leftPad(rsCl.getString(1),3,"0"), data_da, data_a);
                while (rs.next()) {
                    Row row = sheet.getRow(indiceRiga);
                    if (row == null) {
                        row = sheet.createRow(indiceRiga);
                    }
                    Cell cell0 = row.createCell(0);
                    cell0.setCellValue("1" + rs.getString("filiale") + rs.getString("id"));
                    Cell cell = row.createCell(1);
                    if (rs.getString("tipocliente").equals("003")) {
                        cell.setCellValue("PNF");
                    } else {
                        cell.setCellValue("PF");
                    }
                    //String cliente[] = db1.getCliente(rs.getString("cl_cod"));
                    Client cl = db1.query_Client_transaction(rs.getString("cod"), rs.getString("cl_cod"));
                    
                    String cap = cl.getCap();
                    if(!cl.getNazione().equals(codnaz)){
                        cap = "";
                    }
                    Cell cell2 = row.createCell(2);
                    cell2.setCellValue(cl.getCodfisc().replaceAll("---", ""));
                    Cell cell3 = row.createCell(3);
                    cell3.setCellValue(cl.getCognome() + " " + cl.getNome());
                    Cell cell4 = row.createCell(4);
                    cell4.setCellValue(cl.getIndirizzo());
                    Cell cell5 = row.createCell(5);
                    cell5.setCellValue(cap);
                    Cell cell6 = row.createCell(6);
                    cell6.setCellValue(formatALN(cl.getCitta(), city, 1).toUpperCase());
                    Cell cell7 = row.createCell(7);
                    cell7.setCellValue(cl.getProvincia());
                    Cell cell8 = row.createCell(8);
                    cell8.setCellValue(cl.getNazione());
                    Cell cell9 = row.createCell(9);
                    cell9.setCellValue(cl.getSesso());
                    Cell cell10 = row.createCell(10);
                    cell10.setCellValue(cl.getDt_nascita());
                    Cell cell11 = row.createCell(11);
                    cell11.setCellValue(cl.getCitta_nascita());
                    Cell cell12 = row.createCell(12);
                    String td = formatAL(cl.getTipo_documento(), coddoc, 2);
                    if (td == null) {
                        cell12.setCellValue("");
                    } else {
                        cell12.setCellValue(td);
                    }
                    Cell cell13 = row.createCell(13);
                    cell13.setCellValue(cl.getNumero_documento());
                    Cell cell14 = row.createCell(14);
                    cell14.setCellValue(cl.getDt_rilascio_documento());
                    Cell cell15 = row.createCell(15);
                    cell15.setCellValue(cl.getRilasciato_da_documento());
                    Cell cell16 = row.createCell(16);
                    cell16.setCellValue("0");
                    Cell cell17 = row.createCell(17);
                    cell17.setCellValue("600");
                    Cell cell18 = row.createCell(18);
                    cell18.setCellValue("600");
                    indiceRiga++;                    
                }
            }
            db1.closeDB();
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
// Write the output to a file
            String outputfile = new DateTime().toString("yyMMddhhmmssSSS") + "_reg.xls";
            File xlsout = new File(pathtemp + outputfile);
            try (FileOutputStream fileOut = new FileOutputStream(xlsout)) {
                wb.write(fileOut);
            }
            String base64 = new String(encodeBase64(readFileToByteArray(xlsout)));
            xlsout.delete();
            return base64;
        } catch (Exception ex) {
            insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
        }
        return null;
    }
    
}