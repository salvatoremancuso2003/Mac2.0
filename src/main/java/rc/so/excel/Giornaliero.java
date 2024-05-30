package rc.so.excel;

import static rc.so.util.Engine.getConf;
import static rc.so.util.Engine.insertTR;
import static rc.so.util.Utility.fd;
import static rc.so.util.Utility.formatDoubleforMysql;
import static rc.so.util.Utility.generaId;
import static rc.so.util.Utility.getCell;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import static java.lang.Thread.currentThread;
import java.util.ArrayList;
import static org.apache.commons.codec.binary.Base64.decodeBase64;
import static org.apache.commons.codec.binary.Base64.encodeBase64;
import static org.apache.commons.io.FileUtils.readFileToByteArray;
import static org.apache.poi.hssf.usermodel.HSSFFont.FONT_ARIAL;
import static org.apache.poi.ss.usermodel.BorderStyle.DOUBLE;
import org.apache.poi.ss.usermodel.Cell;
import static org.apache.poi.ss.usermodel.CellType.NUMERIC;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import static org.apache.poi.ss.util.CellReference.convertNumToColString;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFDataFormat;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author rcosco
 */
public class Giornaliero {

//    private static final String formatdataCell = "#,##0.00";
    private static final String formatdataCell = "#,#.00";
    private static final String formatdataCellRATE = "#,##0.00000000";
    private static final String formatdataPerc = "0.00%";

    /**
     *
     * @param pathout
     * @param numgiornimese
     * @param nummese
     * @param elencovalori
     * @param elencovaloririgafoglio2
     * @param elencovaloririgafoglio3
     * @param elencovaloririgafoglio4
     * @param elencovaloririgafoglio5
     * @param elencovaloririgafoglio6
     * @param elencovaloririgafoglio7
     * @return
     */
    public static String giornalieroCG(String pathout, int numgiornimese, int nummese,
            ArrayList<Colonna> elencovalori,
            ArrayList<Riga> elencovaloririgafoglio2,
            ArrayList<Riga> elencovaloririgafoglio3,
            ArrayList<Riga> elencovaloririgafoglio4,
            ArrayList<Riga> elencovaloririgafoglio5,
            ArrayList<Riga> elencovaloririgafoglio6,
            ArrayList<Riga> elencovaloririgafoglio7
    ) {
        try {
//            InputStream is = new FileInputStream(new File("C:\\Users\\rcosco\\Documents\\Mac2.0\\maccorp_template.xlsx"));

            InputStream is = new ByteArrayInputStream(decodeBase64(getConf("path.giorn")));

            XSSFWorkbook wb = new XSSFWorkbook(is);
            // Sheet sheet = wb.createSheet("RC");
            Sheet sheet = wb.getSheetAt(0);
            XSSFFont font = (XSSFFont) wb.createFont();
            font.setFontName(FONT_ARIAL);
            font.setFontHeightInPoints((short) 12);
            font.setBold(true);
            XSSFFont font2 = (XSSFFont) wb.createFont();
            font2.setFontName(FONT_ARIAL);
            font2.setFontHeightInPoints((short) 10);

            XSSFDataFormat hssfDataFormat = wb.createDataFormat();

            XSSFCellStyle cellStyle = wb.createCellStyle();
            cellStyle.setDataFormat(hssfDataFormat.getFormat(formatdataCell));
            cellStyle.setFont(font2);

            XSSFCellStyle cellStyleRATE = wb.createCellStyle();
            cellStyleRATE.setDataFormat(hssfDataFormat.getFormat(formatdataCellRATE));
            cellStyleRATE.setFont(font2);

            XSSFCellStyle stylesottosinistra = wb.createCellStyle();
            stylesottosinistra.setBorderBottom(DOUBLE);

            stylesottosinistra.setBorderLeft(DOUBLE);
            stylesottosinistra.setFont(font);

            XSSFCellStyle stylesinsitraaltrodestra = wb.createCellStyle();
            stylesinsitraaltrodestra.setBorderTop(DOUBLE);
            stylesinsitraaltrodestra.setBorderLeft(DOUBLE);
            stylesinsitraaltrodestra.setBorderRight(DOUBLE);
            stylesinsitraaltrodestra.setFont(font);

            XSSFCellStyle stylealtosinistra = wb.createCellStyle();
            stylealtosinistra.setBorderTop(DOUBLE);
            stylealtosinistra.setBorderLeft(DOUBLE);
            stylealtosinistra.setFont(font2);

            XSSFCellStyle stylesinistra = wb.createCellStyle();
            stylesinistra.setBorderLeft(DOUBLE);
            stylesinistra.setFont(font2);

            XSSFCellStyle styledestra = wb.createCellStyle();
            styledestra.setBorderRight(DOUBLE);
            styledestra.setFont(font2);
            styledestra.setDataFormat(hssfDataFormat.getFormat(formatdataCell));

            XSSFCellStyle stylevuoto = wb.createCellStyle();
            stylevuoto.setFont(font2);
            stylevuoto.setDataFormat(hssfDataFormat.getFormat(formatdataCell));

            XSSFCellStyle stylevuotonoformat = wb.createCellStyle();
            stylevuotonoformat.setFont(font2);

            XSSFCellStyle stylepercent = wb.createCellStyle();
            stylepercent.setFont(font2);
            stylepercent.setDataFormat(hssfDataFormat.getFormat(formatdataCell));

            XSSFCellStyle stylealto = wb.createCellStyle();
            stylealto.setBorderTop(DOUBLE);
            stylealto.setFont(font2);

            XSSFCellStyle stylebasso = wb.createCellStyle();
            stylebasso.setBorderBottom(DOUBLE);
            stylebasso.setFont(font2);
            stylebasso.setDataFormat(hssfDataFormat.getFormat(formatdataCell));

            XSSFCellStyle stylebassoaltosinistra = wb.createCellStyle();
            stylebassoaltosinistra.setBorderBottom(DOUBLE);
            stylebassoaltosinistra.setBorderTop(DOUBLE);
            stylebassoaltosinistra.setBorderLeft(DOUBLE);
            stylebassoaltosinistra.setFont(font2);
            stylebassoaltosinistra.setDataFormat(hssfDataFormat.getFormat(formatdataCell));

            XSSFCellStyle stylebassoalto = wb.createCellStyle();
            stylebassoalto.setBorderBottom(DOUBLE);
            stylebassoalto.setBorderTop(DOUBLE);
            stylebassoalto.setFont(font2);
            stylebassoalto.setDataFormat(hssfDataFormat.getFormat(formatdataCell));

            XSSFCellStyle stylebassodestra = wb.createCellStyle();
            stylebassodestra.setBorderBottom(DOUBLE);
            stylebassodestra.setBorderRight(DOUBLE);
            stylebassodestra.setFont(font2);
            stylebassodestra.setDataFormat(hssfDataFormat.getFormat(formatdataCell));

            XSSFCellStyle stylebassosinistra = wb.createCellStyle();
            stylebassosinistra.setBorderBottom(DOUBLE);
            stylebassosinistra.setBorderLeft(DOUBLE);
            stylebassosinistra.setFont(font2);

            XSSFCellStyle stylealtodestra = wb.createCellStyle();
            stylealtodestra.setBorderTop(DOUBLE);
            stylealtodestra.setBorderRight(DOUBLE);
            stylealtodestra.setFont(font2);

            int totind = 35;
            int indicecolonnacambio = -1;
            int indicecolonnatotalegenerale = -1;

            Row row = sheet.createRow(0);
            Cell cell = row.createCell(0);
            cell.setCellValue(numgiornimese);

            ArrayList<Integer> elencoindicinoformule = new ArrayList<>();

            for (int a = 0; a < elencovalori.size(); a++) {

                Colonna temp = elencovalori.get(a);
                boolean rate = false;
                if (temp.getFormula().equals("N")) {
                    elencoindicinoformule.add(a + 1);
                }
                if (temp.getDesc().equalsIgnoreCase("CAMBIO")) {
                    indicecolonnacambio = a + 1;

                    rate = true;

                }

                if (temp.getDesc().equalsIgnoreCase("TOTALE GENERALE")) {
                    indicecolonnatotalegenerale = a + 1;
                }

                ArrayList<String> elencovaloricolonna = temp.getValori();

                for (int x = 0; x < elencovaloricolonna.size(); x++) {

                    Row row1 = sheet.getRow(x);
                    if (a == 0) {
                        row1 = sheet.createRow(x);
                    }
                    if (x > 2 && a > 0) {
                        Cell cell1 = getCell(row1, a + 1);
                        if (!elencovaloricolonna.get(x).equals("")) {
                            cell1 = getCell(row1, a + 1, NUMERIC);
                            if (rate) {
                                cell1.setCellStyle(cellStyleRATE);
                            } else {
                                cell1.setCellStyle(cellStyle);

                            }
                            cell1.setCellValue(fd(elencovaloricolonna.get(x)));
                        } else {
                            cell1.setCellValue("");
                        }

                        if (a == elencovalori.size() - 1 || a == indicecolonnatotalegenerale - 1) {
                            cell1.setCellStyle(styledestra);
                        }

                        if (temp.getDesc().startsWith("%")) {
                            cell1 = getCell(row1, a + 1, NUMERIC);
                            cell1.setCellStyle(stylepercent);
                            cell1.setCellValue(fd(formatDoubleforMysql(elencovaloricolonna.get(x).replaceAll("%", "").trim())));
                        }

                        if (elencovaloricolonna.get(x).equals("Totale")) {
                            cell1.setCellStyle(stylebassoaltosinistra);
                        }

                        if (x == totind - 1) {
                            cell1.setCellStyle(stylebassoalto);
                        }

                    } else {
                        Cell cell1 = getCell(row1, a + 1);
                        cell1.setCellValue(elencovaloricolonna.get(x));
// cell1.setCellStyle(stylebasso);
                        if (a == 0) {
                            cell1.setCellStyle(stylesinistra);
                            if (x == totind || x == totind + 4 || x == totind + 6 || x == totind + 9 || x == totind + 13 || x == totind + 16) {
                                cell1.setCellStyle(stylebassosinistra);
                            }

                            if (elencovaloricolonna.get(x).equals("Totale")) {
                                cell1.setCellStyle(stylebassoaltosinistra);
                            }

                        }
                        if (a == elencovalori.size() - 1 || a == indicecolonnatotalegenerale - 1) {
                            cell1.setCellStyle(styledestra);
                        }

                        if (x == totind - 1) {
                            cell1.setCellStyle(stylebassoalto);
                        }

                    }
                }
            }

//faccio la parte dei totali in basso
            Row row3 = sheet.getRow(totind);
//for (int y = 0; y < elencovalori.size(); y++) {
            for (int y = 0; y < indicecolonnacambio; y++) {

                Cell cell1 = getCell(row3, y + 2);

                int colindex = cell1.getColumnIndex();
                String colletter = convertNumToColString(colindex);
                String formula = "SUM(" + colletter + "4:" + colletter + "34)";

                if (!elencoindicinoformule.contains(y + 2)) {
                    cell1.setCellFormula(formula);
                }
                cell1.setCellStyle(stylebassoalto);
                if (y == indicecolonnacambio - 1) {
                    cell1.setCellStyle(stylebassodestra);
                }

            }

            totind++;
            Row row4 = sheet.getRow(totind);
            for (int y = 0; y < indicecolonnacambio; y++) {

                Cell cell1 = getCell(row4, y + 2);

                int colindex = cell1.getColumnIndex();
                String colletter = convertNumToColString(colindex);
                String formula = "+" + colletter + "36/" + colletter + "42*100%";
                if (!elencoindicinoformule.contains(y + 2)) {
                    cell1.setCellFormula(formula);
                    cell1.setCellStyle(stylevuoto);
                }
                if (y == indicecolonnacambio - 1) {
                    cell1.setCellStyle(styledestra);
                }

            }

            totind++;
            Row row44 = sheet.getRow(totind);
            for (int y = 0; y < indicecolonnacambio; y++) {

                Cell cell1 = getCell(row44, y + 2);
// int colindex = cell1.getColumnInde);
// String colletter = CellReference.convertNumToColString(colindex);
// String formula = "+" + colletter + "36/" + colletter + "43*100%";
// cell1.setCellFormula(formula);
                if (y == indicecolonnacambio - 1) {
                    cell1.setCellStyle(styledestra);
                }

            }

            totind++;
            row4 = sheet.getRow(totind);

            for (int y = 0; y < indicecolonnacambio; y++) {

                Cell cell1 = getCell(row4, y + 2);
                int colindex = cell1.getColumnIndex();
                String colletter = convertNumToColString(colindex);
                String formula = "+" + colletter + "37-" + colletter + "38";
                if (!elencoindicinoformule.contains(y + 2)) {
                    cell1.setCellFormula(formula);
                    cell1.setCellStyle(stylevuoto);
                }
                if (y == indicecolonnacambio - 1) {
                    cell1.setCellStyle(styledestra);
                }

            }

            totind++;
            row4 = sheet.getRow(totind);

            for (int y = 0; y < indicecolonnacambio; y++) {

                Cell cell1 = getCell(row4, y + 2);
                int colindex = cell1.getColumnIndex();
                String colletter = convertNumToColString(colindex);
                String formula = colletter + "39/" + colletter + "38";
                if (!elencoindicinoformule.contains(y + 2)) {
                    cell1.setCellFormula(formula);
                }
                cell1.setCellStyle(stylebasso);
                if (y == indicecolonnacambio - 1) {
                    cell1.setCellStyle(stylebassodestra);
                }

            }

            totind++;
            row4 = sheet.getRow(totind);

            for (int y = 0; y < indicecolonnacambio; y++) {

                Cell cell1 = getCell(row4, y + 2);
// int colindex = cell1.getColumnInde);
// String colletter = CellReference.convertNumToColString(colindex);
// String formula = "+" + colletter + "44/" + colletter + "42";
// cell1.setCellFormula(formula);
// cell1.setCellStyle(stylebasso); 

                if (y == indicecolonnacambio - 1) {
                    cell1.setCellStyle(styledestra);
                }

            }

            totind++;
            row4 = sheet.getRow(totind);

            for (int y = 0; y < indicecolonnacambio; y++) {

                Cell cell1 = getCell(row4, y + 2);
                int colindex = cell1.getColumnIndex();
                String colletter = convertNumToColString(colindex);
                String formula = "+" + colletter + "43/" + colletter + "41";
                if (!elencoindicinoformule.contains(y + 2)) {
                    cell1.setCellFormula(formula);
                }
                cell1.setCellStyle(stylebasso);
                if (y == indicecolonnacambio - 1) {
                    cell1.setCellStyle(stylebassodestra);
                }

            }

            totind++;
            row4 = sheet.getRow(totind);

            for (int y = 0; y < indicecolonnacambio; y++) {

                Cell cell1 = getCell(row4, y + 2);
// int colindex = cell1.getColumnInde);
// String colletter = CellReference.convertNumToColString(colindex);
// String formula = "+" + colletter + "36-" + colletter + "44";
// cell1.setCellFormula(formula); 

                if (y == indicecolonnacambio - 1) {
                    cell1.setCellStyle(styledestra);
                }

            }

            totind++;
            row4 = sheet.getRow(totind);

            for (int y = 0; y < indicecolonnacambio; y++) {

                Cell cell1 = getCell(row4, y + 2);
                int colindex = cell1.getColumnIndex();
                String colletter = convertNumToColString(colindex);
                String formula = "+" + colletter + "36-" + colletter + "43";
                if (!elencoindicinoformule.contains(y + 2)) {
                    cell1.setCellFormula(formula);
                    cell1.setCellStyle(stylevuoto);
                }

                if (y == indicecolonnacambio - 1) {
                    cell1.setCellStyle(styledestra);
                }

            }

            totind++;
            row4 = sheet.getRow(totind);

            for (int y = 0; y < indicecolonnacambio; y++) {

                Cell cell1 = getCell(row4, y + 2);
                int colindex = cell1.getColumnIndex();
                String colletter = convertNumToColString(colindex);
                String formula = colletter + "44/" + colletter + "43";
                if (!elencoindicinoformule.contains(y + 2)) {
                    cell1.setCellFormula(formula);
                }
                cell1.setCellStyle(stylebasso);
                if (y == indicecolonnacambio - 1) {
                    cell1.setCellStyle(stylebassodestra);
                }

            }

            totind++;
            row4 = sheet.getRow(totind);

            for (int y = 0; y < indicecolonnacambio; y++) {

                Cell cell1 = getCell(row4, y + 2);
// int colindex = cell1.getColumnInde);
// String colletter = CellReference.convertNumToColString(colindex);
// String formula = "+" + colletter + "47-" + colletter + "48";
// cell1.setCellFormula(formula); 

                if (y == indicecolonnacambio - 1) {
                    cell1.setCellStyle(styledestra);
                }

            }

            totind++;
            row4 = sheet.getRow(totind);

            for (int y = 0; y < indicecolonnacambio; y++) {

                Cell cell1 = getCell(row4, y + 2);
// int colindex = cell1.getColumnInde);
// String colletter = CellReference.convertNumToColString(colindex);
// String formula = "+" + colletter + "47-" + colletter + "48";
// cell1.setCellFormula(formula); 

                if (y == indicecolonnacambio - 1) {
                    cell1.setCellStyle(styledestra);
                }

            }

            totind++;
            row4 = sheet.getRow(totind);

            for (int y = 0; y < indicecolonnacambio; y++) {

                Cell cell1 = getCell(row4, y + 2);
                int colindex = cell1.getColumnIndex();
                String colletter = convertNumToColString(colindex);
                String formula = "+" + colletter + "46-" + colletter + "47";
                if (!elencoindicinoformule.contains(y + 2)) {
                    cell1.setCellFormula(formula);
                    cell1.setCellStyle(stylevuoto);
                }

                if (y == indicecolonnacambio - 1) {
                    cell1.setCellStyle(styledestra);
                }

            }

            totind++;
            row4 = sheet.getRow(totind);

            for (int y = 0; y < indicecolonnacambio; y++) {

                Cell cell1 = getCell(row4, y + 2);
                int colindex = cell1.getColumnIndex();
                String colletter = convertNumToColString(colindex);
                String formula = colletter + "48/" + colletter + "47";
                if (!elencoindicinoformule.contains(y + 2)) {
                    cell1.setCellFormula(formula);
                }
                cell1.setCellStyle(stylebasso);
                if (y == indicecolonnacambio - 1) {
                    cell1.setCellStyle(stylebassodestra);
                }

            }

            totind++;
            row4 = sheet.getRow(totind);

            for (int y = 0; y < indicecolonnacambio; y++) {

                Cell cell1 = getCell(row4, y + 2);
// int colindex = cell1.getColumnInde);
// String colletter = CellReference.convertNumToColString(colindex);
// String formula = "+" + colletter + "47-" + colletter + "51";
// cell1.setCellFormula(formula); 

                if (y == indicecolonnacambio - 1) {
                    cell1.setCellStyle(styledestra);
                }

            }

            totind++;
            row4 = sheet.getRow(totind);

            for (int y = 0; y < indicecolonnacambio; y++) {
                Cell cell1 = getCell(row4, y + 2);
                int colindex = cell1.getColumnIndex();
                String colletter = convertNumToColString(colindex);
                String formula = "+" + colletter + "46-" + colletter + "50";
                if (!elencoindicinoformule.contains(y + 2)) {
                    cell1.setCellFormula(formula);
                    cell1.setCellStyle(stylevuoto);
                }

                if (y == indicecolonnacambio - 1) {
                    cell1.setCellStyle(styledestra);
                }

            }

            totind++;
            row4 = sheet.getRow(totind);

            for (int y = 0; y < indicecolonnacambio; y++) {
                Cell cell1 = getCell(row4, y + 2);
                int colindex = cell1.getColumnIndex();
                String colletter = convertNumToColString(colindex);
                String formula = colletter + "51/" + colletter + "50";
                if (!elencoindicinoformule.contains(y + 2)) {
                    cell1.setCellFormula(formula);
                }
                cell1.setCellStyle(stylebasso);
                if (y == indicecolonnacambio - 1) {
                    cell1.setCellStyle(stylebassodestra);
                }
            }

            Row startrow = sheet.getRow(0);
            Cell startcell = startrow.createCell(0);
            startcell.setCellValue(numgiornimese);
            Row startrow2 = sheet.getRow(1);
            Cell startcell2 = startrow2.createCell(0);
            startcell2.setCellValue(nummese);

//            for (int r = 0; r < elencovalori.size() + 5; r++) {
//
//                sheet.autoSizeColumn(r);
//            }
//---------------- FOGLIO 2 ------------ //////////////////
//DATI CAMBIO MTD
//Sheet sheet2 = wb.createSheet("DATI CAMBIO MTD");
// Sheet sheet2 = wb.createSheet("DATI CAMBIO MTD");
            Sheet sheet2 = wb.getSheetAt(1);

            for (int a = 0; a < elencovaloririgafoglio2.size(); a++) {
                Row rw = sheet2.getRow(a);
                if (rw == null) {
                    rw = sheet2.createRow(a);
                }
                Riga temp = elencovaloririgafoglio2.get(a);
                ArrayList<String> elencovalorisingolariga = temp.getValori();

                for (int x = 0; x < elencovalorisingolariga.size(); x++) {
                    Cell cell1 = getCell(rw, x);
                    if (a > 3 && x > 1) {
                        cell1 = getCell(rw, x, NUMERIC);
                        cell1.setCellValue(fd(elencovalorisingolariga.get(x)));
                        cell1.setCellStyle(cellStyle);
                    } else {
                        cell1.setCellValue(elencovalorisingolariga.get(x));
                    }
                }
            }

//            for (int r1 = 0; r1 < 500; r1++) {
//
//                sheet2.autoSizeColumn(r1);
//            }
//---------------- FOGLIO 3 ------------ //////////////////
//DATI CAMBIO YTD
            Sheet sheet3 = wb.getSheetAt(2);

            for (int a = 0; a < elencovaloririgafoglio3.size(); a++) {
                Row rw = sheet3.getRow(a);
                if (rw == null) {
                    rw = sheet3.createRow(a);
                }
                
                Riga temp = elencovaloririgafoglio3.get(a);
                ArrayList<String> elencovalorisingolariga = temp.getValori();

                for (int x = 0; x < elencovalorisingolariga.size(); x++) {
                    Cell cell1 = getCell(rw, x);
                    if (a > 3 && x > 1) {
                        cell1 = getCell(rw, x, NUMERIC);
                        cell1.setCellValue(fd(elencovalorisingolariga.get(x)));
                        cell1.setCellStyle(cellStyle);
                    } else {
                        cell1.setCellValue(elencovalorisingolariga.get(x));
                    }
//                    cell1.setCellValue(elencovalorisingolariga.get(x));
                }
            }

//            for (int r1 = 0; r1 < 500; r1++) {
//
//                sheet3.autoSizeColumn(r1);
//            }
//---------------- FOGLIO 4 ------------ //////////////////
//DATI WU MTD
            Sheet sheet4 = wb.getSheetAt(3);

            for (int a = 0; a < elencovaloririgafoglio4.size(); a++) {
                Row rw = sheet4.getRow(a);
                if (rw == null) {
                    rw = sheet4.createRow(a);
                }
                Riga temp = elencovaloririgafoglio4.get(a);
                ArrayList<String> elencovalorisingolariga = temp.getValori();

                for (int x = 0; x < elencovalorisingolariga.size(); x++) {
                    Cell cell1 = getCell(rw, x);
                    if (a > 3 && x > 1) {
                        cell1 = getCell(rw, x, NUMERIC);
                        cell1.setCellValue(fd(elencovalorisingolariga.get(x)));
                        cell1.setCellStyle(cellStyle);
                    } else {
                        cell1.setCellValue(elencovalorisingolariga.get(x));
                    }
                }
            }

//            for (int r1 = 0; r1 < 500; r1++) {
//
//                sheet4.autoSizeColumn(r1);
//            }
//---------------- FOGLIO 5 ------------ //////////////////
//DATI WU YTD
            Sheet sheet5 = wb.getSheetAt(4);

            for (int a = 0; a < elencovaloririgafoglio5.size(); a++) {
                Row rw = sheet5.getRow(a);
                if (rw == null) {
                    rw = sheet5.createRow(a);
                }
                Riga temp = elencovaloririgafoglio5.get(a);
                ArrayList<String> elencovalorisingolariga = temp.getValori();

                for (int x = 0; x < elencovalorisingolariga.size(); x++) {
                    Cell cell1 = getCell(rw, x);
                    if (a > 3 && x > 1) {
                        cell1 = getCell(rw, x, NUMERIC);
                        cell1.setCellValue(fd(elencovalorisingolariga.get(x)));
                        cell1.setCellStyle(cellStyle);
                    } else {
                        cell1.setCellValue(elencovalorisingolariga.get(x));
                    }
                }
            }

//            for (int r1 = 0; r1 < 500; r1++) {
//
//                sheet5.autoSizeColumn(r1);
//            }
//---------------- FOGLIO 6 ------------ //////////////////
//SHEET NO CHANGE MTD
            Sheet sheet6 = wb.getSheetAt(5);
            for (int a = 0; a < elencovaloririgafoglio6.size(); a++) {
                Row rw = sheet6.getRow(a);
                if (rw == null) {
                    rw = sheet6.createRow(a);
                }
                Riga temp = elencovaloririgafoglio6.get(a);
                ArrayList<String> elencovalorisingolariga = temp.getValori();
                for (int x = 0; x < elencovalorisingolariga.size(); x++) {
                    Cell cell1 = getCell(rw, x);
                    if (a > 4 && x > 1) {
                        cell1 = getCell(rw, x, NUMERIC);
                        cell1.setCellValue(fd(elencovalorisingolariga.get(x)));
                        cell1.setCellStyle(cellStyle);
                    } else {
                        cell1.setCellValue(elencovalorisingolariga.get(x));
                    }
                }
            }

//            for (int r1 = 0; r1 < 500; r1++) {
//
//                sheet6.autoSizeColumn(r1);
//            }
//---------------- FOGLIO 7 ------------ //////////////////
//SHEET NO CHANGE YTD
            Sheet sheet7 = wb.getSheetAt(6);
            for (int a = 0; a < elencovaloririgafoglio7.size(); a++) {
                Row rw = sheet7.getRow(a);
                if (rw == null) {
                    rw = sheet7.createRow(a);
                }
                Riga temp = elencovaloririgafoglio7.get(a);
                ArrayList<String> elencovalorisingolariga = temp.getValori();

                for (int x = 0; x < elencovalorisingolariga.size(); x++) {
                    Cell cell1 = getCell(rw, x);
                    if (a > 4 && x > 1) {
                        cell1 = getCell(rw, x, NUMERIC);
                        cell1.setCellValue(fd(elencovalorisingolariga.get(x)));
                        cell1.setCellStyle(cellStyle);
                    } else {
                        cell1.setCellValue(elencovalorisingolariga.get(x));
                    }
                }
            }

//            for (int r1 = 0; r1 < 500; r1++) {
//
//                sheet7.autoSizeColumn(r1);
//            }
// Write the output to a file
            try {
                File out = new File(pathout + generaId(75) + ".xlsx");
                try (FileOutputStream fileOut = new FileOutputStream(out)) {
                    wb.write(fileOut);
                }
                is.close();
                wb.close();

                String base64 = new String(encodeBase64(readFileToByteArray(out)));
                out.delete();
                return base64;
            } catch (IOException ex) {
                insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
            }
        } catch (IOException ex) {
            insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
        }
        return null;
    }
}
