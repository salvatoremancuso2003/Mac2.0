/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rc.so.pdf;

import static com.itextpdf.text.BaseColor.LIGHT_GRAY;
import com.itextpdf.text.Chunk;
import static com.itextpdf.text.Chunk.TABBING;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import static com.itextpdf.text.Element.ALIGN_CENTER;
import static com.itextpdf.text.Element.ALIGN_LEFT;
import static com.itextpdf.text.Element.ALIGN_RIGHT;
import com.itextpdf.text.Font;
import static com.itextpdf.text.Font.BOLD;
import static com.itextpdf.text.Font.BOLDITALIC;
import static com.itextpdf.text.Font.NORMAL;
import static com.itextpdf.text.FontFactory.getFont;
import static com.itextpdf.text.PageSize.A4;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import static com.itextpdf.text.Rectangle.BOTTOM;
import static com.itextpdf.text.Rectangle.NO_BORDER;
import static com.itextpdf.text.Rectangle.TOP;
import com.itextpdf.text.TabSettings;
import static com.itextpdf.text.pdf.BaseFont.HELVETICA;
import static com.itextpdf.text.pdf.BaseFont.WINANSI;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;
import static com.itextpdf.text.pdf.PdfWriter.getInstance;
import com.itextpdf.text.pdf.draw.LineSeparator;
import rc.so.db.Db_Master;
import rc.so.entity.Branch;
import rc.so.entity.ET_change;
import static rc.so.entity.ET_change.format_tofrom_brba;
import rc.so.report.FromBranchingSheet_value;
import rc.so.report.NoChangeFromBranchingSheet_value;
import rc.so.report.NoChangeToBranchingSheet_value;
import rc.so.report.ToBankingSheet_value;
import rc.so.report.ToBranchingSheet_value;
import static rc.so.util.Constant.formatdataCell;
import static rc.so.util.Constant.formatdataCellRate;
import static rc.so.util.Constant.formatdataCellint;
import static rc.so.util.Constant.patternnormdate;
import static rc.so.util.Constant.patternsqldate;
import static rc.so.util.Engine.formatBankBranch;
import static rc.so.util.Engine.formatBankBranchReport;
import static rc.so.util.Engine.insertTR;
import static rc.so.util.Utility.fd;
import static rc.so.util.Utility.formatMysqltoDisplay;
import static rc.so.util.Utility.formatStringtoStringDate;
import static rc.so.util.Utility.generaId;
import static rc.so.util.Utility.roundDoubleandFormat;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import static java.lang.Thread.currentThread;
import java.util.ArrayList;
import java.util.List;
import static org.apache.commons.codec.binary.Base64.decodeBase64;
import static org.apache.commons.codec.binary.Base64.encodeBase64;
import static org.apache.commons.io.FileUtils.readFileToByteArray;
import static org.apache.poi.hssf.usermodel.HSSFFont.FONT_ARIAL;
import static org.apache.poi.ss.usermodel.BorderStyle.THIN;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import static org.apache.poi.ss.usermodel.HorizontalAlignment.CENTER;
import static org.apache.poi.ss.usermodel.HorizontalAlignment.LEFT;
import static org.apache.poi.ss.usermodel.HorizontalAlignment.RIGHT;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFDataFormat;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author rcosco
 */
public class NewETReceipt {

    private static final List<String> colonne_change = new ArrayList<String>() {
        {
            add("Currency");
            add("Amount");
            add("Branch Rate");
            add("Amount");
            add("Branch Rate");
            add("Branch Total");
        }
    };

    private static final List<String> colonne_nochange = new ArrayList<String>() {
        {
            add("Category");
            add("Quantity");
            add("Amount");
            add("Total");
        }
    };

    private static final List<String> colonne_change_sp = new ArrayList<String>() {
        {
            add("Currency");
            add("Amount");
            add("Bank Rate");
            add("Amount");
            add("Bank Rate");
            add("Bank Total");
            add("Spread");
            add("%");
        }
    };

    private static final List<String> colonne_pos = new ArrayList<String>() {
        {
            add("Currency");
            add("Amount");
            add("Amount");
            add("Amount");
            add("Amount");
            add("Bank Total");
        }
    };

    private static final Font f0_bold = getFont(HELVETICA, WINANSI, 14.04f, BOLD);
    private static final Font f1_bold = getFont(HELVETICA, WINANSI, 9.00f, BOLD);
    private static final Font f2_bold = getFont(HELVETICA, WINANSI, 8f, BOLD);
    private static final Font f3_bold = getFont(HELVETICA, WINANSI, 8f, BOLDITALIC);
    private static final Font f4_bold = getFont(HELVETICA, WINANSI, 6.96f, BOLD);
    private static final Font f1_normal = getFont(HELVETICA, WINANSI, 9.96f, NORMAL);
    private static final Font f2_normal = getFont(HELVETICA, WINANSI, 8f, NORMAL);
    private static final Font f3_normal = getFont(HELVETICA, WINANSI, 6.96f, NORMAL);
    private static final Font f5_bold = getFont(HELVETICA, WINANSI, 6f, BOLD);

    private static final float[] columnWidths0 = new float[]{70f, 30f};
    private static final float[] columnWidths1 = new float[]{18f, 21f, 20f, 10f, 25f, 25f};
    private static final float[] columnWidths3 = new float[]{18f, 15f, 19f, 5f, 5f, 25f};
    private static final float[] columnWidths4 = new float[]{18f, 18f, 18f, 18f, 18f, 18f};

    //TO BRANCH CHANGE
    /**
     *
     * @param path
     * @param siq
     * @param datereport
     * @return
     */
    public static String TOBRANCH_CHANGE_pdf(String path, ToBranchingSheet_value siq, String datereport) {

        float[] columnWidths2 = new float[colonne_change.size()];
        for (int c = 0; c < colonne_change.size(); c++) {
            columnWidths2[c] = 10f;
        }
        String intestazionePdf = "To Branching Sheet";
        Phrase vuoto = new Phrase("\n");

        Phrase vuoto1 = new Phrase("\n");
        PdfPCell cellavuota = new PdfPCell(vuoto1);
        cellavuota.setBorder(NO_BORDER);

        try {

            File pdf = new File(path + generaId(50) + "FromBranchingSheet.pdf");
            Document document = new Document(A4, 20, 20, 20, 20);
            try(OutputStream ou = new FileOutputStream(pdf)){
            PdfWriter wr = getInstance(document, ou);
            document.open();

            PdfPTable table = new PdfPTable(2);
            table.setWidths(columnWidths0);
            table.setWidthPercentage(100);

            Phrase phrase1 = new Phrase();
            phrase1.add(new Chunk(intestazionePdf + " ", f0_bold));
            PdfPCell cell1 = new PdfPCell(phrase1);
            cell1.setBorder(NO_BORDER);

            Paragraph pa1 = new Paragraph(new Phrase("Transfer " + siq.getNumTransfer() + " " + datereport, f3_bold));
            pa1.setAlignment(ALIGN_CENTER);
            PdfPCell cell2 = new PdfPCell(pa1);
            cell2.setBorder(NO_BORDER);
            cell2.setHorizontalAlignment(ALIGN_RIGHT);

            Phrase phrase3 = new Phrase();
            phrase3.add(new Chunk(siq.getId_filiale() + " - " + siq.getDe_filiale(), f1_bold));
            PdfPCell cell3 = new PdfPCell(phrase3);
            cell3.setBorder(NO_BORDER);

            Phrase phrase4 = new Phrase();
            phrase4.add(new Chunk("\n From", f2_bold));
            phrase4.setTabSettings(new TabSettings(30f));
            phrase4.add(TABBING);
            phrase4.add(new Chunk(": " + siq.getFromsafe() + "\n To", f2_bold));
            phrase4.setTabSettings(new TabSettings(30f));
            phrase4.add(TABBING);
            phrase4.add(new Chunk(": " + siq.getTobranch() + "\n User", f2_bold));
            phrase4.setTabSettings(new TabSettings(30f));
            phrase4.add(TABBING);
            phrase4.add(new Chunk(": " + siq.getPinuser(), f2_bold));

            PdfPCell cell4 = new PdfPCell(phrase4);

            cell4.setBorder(NO_BORDER);

            table.addCell(cell1);
            table.addCell(cell2);
            table.addCell(cell3);
            table.addCell(cellavuota);
            table.addCell(cell4);
            table.addCell(cellavuota);
            document.add(table);

            vuoto.setFont(f3_normal);
            document.add(vuoto);

            if (siq.getDati().size() > 0) {

                PdfPTable table2 = new PdfPTable(colonne_change.size());
                table2.setWidths(columnWidths2);
                table2.setWidthPercentage(100);

                PdfPCell[] list = new PdfPCell[colonne_change.size()];
                //mi scandisco le colonne
                for (int j = 0; j < colonne_change.size(); j++) {
                    Phrase phraset1 = new Phrase();
                    phraset1.add(new Chunk(colonne_change.get(j), f5_bold));
                    PdfPCell cellt1 = new PdfPCell(phraset1);
                    cellt1.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt1.setBorder(BOTTOM | TOP);

                    cellt1.setBackgroundColor(LIGHT_GRAY);
                    //cellt1.setBorder(Rectangle.BOTTOM);
                    cellt1.setBorderWidth(0.7f);
                    if (j == 0) {
                        cellt1.setHorizontalAlignment(ALIGN_LEFT);
                    }
                    list[j] = cellt1;

                }

                PdfPTable table6 = new PdfPTable(columnWidths1);
                table6.setWidthPercentage(100);

                for (int j = 0; j < 6; j++) {

                    if (j == 2) {
                        Phrase phraset1 = new Phrase();
                        phraset1.add(new Chunk("Notes", f5_bold));
                        PdfPCell cellt1 = new PdfPCell(phraset1);
                        cellt1.setHorizontalAlignment(ALIGN_CENTER);
                        cellt1.setBorder(TOP);
                        cellt1.setBackgroundColor(LIGHT_GRAY);
                        //cellt1.setBorder(Rectangle.BOTTOM);
                        cellt1.setBorderWidth(0.7f);
                        table6.addCell(cellt1);
                    } else if (j == 3) {
                        Phrase phraset1 = new Phrase();
                        phraset1.add(new Chunk("", f5_bold));
                        PdfPCell cellt1 = new PdfPCell(phraset1);
                        cellt1.setHorizontalAlignment(ALIGN_RIGHT);
                        cellt1.setBorder(TOP);
                        cellt1.setBackgroundColor(LIGHT_GRAY);
                        //cellt1.setBorder(Rectangle.BOTTOM);
                        cellt1.setBorderWidth(0.7f);
                        table6.addCell(cellt1);
                    } else if (j == 4) {
                        Phrase phraset1 = new Phrase();
                        phraset1.add(new Chunk("Euro TC / Travel Cheques", f5_bold));
                        PdfPCell cellt1 = new PdfPCell(phraset1);
                        cellt1.setHorizontalAlignment(ALIGN_RIGHT);
                        cellt1.setBorder(TOP);
                        cellt1.setBackgroundColor(LIGHT_GRAY);
                        //cellt1.setBorder(Rectangle.BOTTOM);
                        cellt1.setBorderWidth(0.7f);
                        table6.addCell(cellt1);
                    } else {
                        Phrase phraset1 = new Phrase();
                        phraset1.add(new Chunk("", f5_bold));
                        PdfPCell cellt1 = new PdfPCell(phraset1);
                        cellt1.setHorizontalAlignment(ALIGN_LEFT);
                        cellt1.setBorder(TOP);

                        cellt1.setBackgroundColor(LIGHT_GRAY);
                        //cellt1.setBorder(Rectangle.BOTTOM);
                        cellt1.setBorderWidth(0.7f);
                        table6.addCell(cellt1);
                    }
                }

                document.add(table6);

                PdfPTable table3 = new PdfPTable(colonne_change.size());
                table3.setWidths(columnWidths2);
                table3.setWidthPercentage(100);

                for (PdfPCell list1 : list) {
                    PdfPCell temp = (PdfPCell) (list1);
                    table3.addCell(temp);
                }

                document.add(table3);

                LineSeparator sep = new LineSeparator();
                sep.setOffset(-2);
                sep.setLineWidth((float) 0.5);

                //  document.add(table2);
                //  document.add(sep);
                ArrayList<ToBranchingSheet_value> dati = siq.getDati();

                Phrase phraset;
                PdfPCell cellt;
                table3 = new PdfPTable(colonne_change.size());
                table3.setWidths(columnWidths2);
                table3.setWidthPercentage(100);

                double branchtotal = 0, spreadtotal = 0, perctotal = 0;

                for (int i = 0; i < dati.size(); i++) {
                    ToBranchingSheet_value temp = (ToBranchingSheet_value) dati.get(i);

                    ArrayList<String> datitemp = temp.getDati_string();

                    phraset = new Phrase();
                    phraset.add(new Chunk(temp.getCurrency(), f3_normal));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_LEFT);
                    cellt.setBorder(BOTTOM);
                    table3.addCell(cellt);
                    boolean perc = false;

                    for (int n = 1; n < colonne_change.size(); n++) {

                        if (colonne_change.get(n).equals("Branch Total")) {
                            branchtotal += fd((datitemp.get(n)));
                        }
                        phraset = new Phrase();
                        if (!perc) {
                            phraset.add(new Chunk(formatMysqltoDisplay(datitemp.get(n)), f3_normal));
                        } else {
                            phraset.add(new Chunk(formatMysqltoDisplay(datitemp.get(n)) + " %", f3_normal));
                        }
                        cellt = new PdfPCell(phraset);
                        cellt.setHorizontalAlignment(ALIGN_RIGHT);
                        cellt.setBorder(BOTTOM);
                        table3.addCell(cellt);

                    }

                }

                document.add(table3);

                LineSeparator ls = new LineSeparator();
                ls.setLineWidth(0.7f);
                ls.setOffset(-1f);
                document.add(ls);

                table3 = new PdfPTable(colonne_change.size());
                table3.setWidths(columnWidths2);
                table3.setWidthPercentage(100);

                phraset = new Phrase();
                phraset.add(new Chunk("", f1_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table3.addCell(cellt);

                for (int n = 1; n < colonne_change.size(); n++) {

                    if (n == 1) {
                        phraset = new Phrase();
                        phraset.add(new Chunk("Total", f4_bold));
                        cellt = new PdfPCell(phraset);
                        cellt.setHorizontalAlignment(ALIGN_RIGHT);
                        cellt.setBorder(BOTTOM);
                        table3.addCell(cellt);
                    } else if (colonne_change.get(n).equals("Branch Total")) {
                        phraset = new Phrase();
                        phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(branchtotal, 2)), f4_bold));
                        cellt = new PdfPCell(phraset);
                        cellt.setHorizontalAlignment(ALIGN_RIGHT);
                        cellt.setBorder(BOTTOM);
                        table3.addCell(cellt);

                    } else if (colonne_change.get(n).equals("Spread")) {
                        phraset = new Phrase();
                        phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(spreadtotal, 2)), f4_bold));
                        cellt = new PdfPCell(phraset);
                        cellt.setHorizontalAlignment(ALIGN_RIGHT);
                        cellt.setBorder(BOTTOM);
                        table3.addCell(cellt);

                    } else if (colonne_change.get(n).equals("%")) {
                        phraset = new Phrase();
                        phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(perctotal, 2)) + "%", f4_bold));
                        cellt = new PdfPCell(phraset);
                        cellt.setHorizontalAlignment(ALIGN_RIGHT);
                        cellt.setBorder(BOTTOM);
                        table3.addCell(cellt);

                    } else {
                        phraset = new Phrase();
                        phraset.add(new Chunk("", f3_normal));
                        cellt = new PdfPCell(phraset);
                        cellt.setHorizontalAlignment(ALIGN_RIGHT);
                        cellt.setBorder(BOTTOM);
                        table3.addCell(cellt);
                    }

                }

                document.add(table3);

            }
            document.add(vuoto1);
            document.add(new Paragraph("Notes:", f2_bold));
            document.add(new Paragraph(siq.getNote(), f3_normal));

            document.close();
            wr.close();
            ou.close();
            String base64 = new String(encodeBase64(readFileToByteArray(pdf)));
//            pdf.delete();
            return base64;
        }
        } catch (IOException | DocumentException ex) {
            insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
        }
        return null;
    }

    /**
     *
     * @param path
     * @param siq
     * @param datereport
     * @return
     */
    public static String TOBRANCH_CHANGE_xls(String path, ToBranchingSheet_value siq, String datereport) {
        try {
            String intestazionePdf = "To Branching Sheet";
            File pdf = new File(path + generaId(50) + "_ToBranchingSheet.xlsx");
            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet("ToBranchingSheet");
            //CREAZIONE FONT
            XSSFFont font = workbook.createFont();
            font.setFontName(FONT_ARIAL);
            font.setFontHeightInPoints((short) 16);
            font.setBold(true);

            XSSFCellStyle style1 = (XSSFCellStyle) workbook.createCellStyle();
            style1.setFont(font);

            XSSFFont font2 = workbook.createFont();
            font2.setFontName(FONT_ARIAL);
            font2.setFontHeightInPoints((short) 12);
            font2.setBold(true);

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

            XSSFCellStyle style3center = (XSSFCellStyle) workbook.createCellStyle();
            style3center.setFont(font3);
            style3center.setWrapText(true);
            style3center.setAlignment(CENTER);

            style3center.setBorderTop(THIN);
            style3center.setBorderBottom(THIN);

            XSSFCellStyle style3left = (XSSFCellStyle) workbook.createCellStyle();
            style3left.setFont(font3);
            style3left.setAlignment(LEFT);

            style3left.setBorderTop(THIN);
            style3left.setBorderBottom(THIN);

            XSSFFont font4 = workbook.createFont();
            font4.setFontName(FONT_ARIAL);
            font4.setFontHeightInPoints((short) 10);

            XSSFCellStyle style4 = (XSSFCellStyle) workbook.createCellStyle();
            style4.setAlignment(RIGHT);
            style4.setBorderTop(THIN);
            style4.setBorderBottom(THIN);

            XSSFCellStyle cellStylenum = (XSSFCellStyle) workbook.createCellStyle();
            XSSFDataFormat hssfDataFormat = (XSSFDataFormat) workbook.createDataFormat();
            cellStylenum.setDataFormat(hssfDataFormat.getFormat(formatdataCell));
            cellStylenum.setAlignment(RIGHT);
            cellStylenum.setBorderTop(THIN);
            cellStylenum.setBorderBottom(THIN);

            XSSFCellStyle cellStylenumRATE = (XSSFCellStyle) workbook.createCellStyle();
            cellStylenumRATE.setDataFormat(hssfDataFormat.getFormat(formatdataCellRate));
            cellStylenumRATE.setAlignment(RIGHT);
            cellStylenumRATE.setBorderTop(THIN);
            cellStylenumRATE.setBorderBottom(THIN);

            XSSFCellStyle style3num = (XSSFCellStyle) workbook.createCellStyle();
            style3num.setFont(font3);
            style3num.setAlignment(RIGHT);
            style3num.setBorderTop(THIN);
            style3num.setBorderBottom(THIN);
            style3num.setDataFormat(hssfDataFormat.getFormat(formatdataCell));

            XSSFCellStyle style4left = (XSSFCellStyle) workbook.createCellStyle();
            style4left.setAlignment(LEFT);
            style4left.setBorderTop(THIN);
            style4left.setBorderBottom(THIN);

            Row rowP = sheet.createRow((short) 1);

            Cell cl = rowP.createCell(1);
            cl.setCellStyle(style1);
            cl.setCellValue(intestazionePdf);

            Cell cl32 = rowP.createCell(3);
            cl32.setCellStyle(style2);
            cl32.setCellValue("Transfer " + siq.getNumTransfer() + " " + datereport);

            Row rowP4 = sheet.createRow((short) 5);

            Cell cl4 = rowP4.createCell(1);
            cl4.setCellStyle(style2);
            cl4.setCellValue("From : ");

            Cell cl42 = rowP4.createCell(2);
            cl42.setCellStyle(style2);
            cl42.setCellValue(siq.getFromsafe());

            Row rowP5 = sheet.createRow((short) 6);

            Cell cl55 = rowP5.createCell(1);
            cl55.setCellStyle(style2);
            cl55.setCellValue("To: ");

            Cell cl552 = rowP5.createCell(2);
            cl552.setCellStyle(style2);
            cl552.setCellValue(siq.getTobranch());

            Row rowP7 = sheet.createRow((short) 7);

            Cell cl7 = rowP7.createCell(1);
            cl7.setCellStyle(style2);
            cl7.setCellValue("User: ");

            Cell cl72 = rowP7.createCell(2);
            cl72.setCellStyle(style2);
            cl72.setCellValue(siq.getPinuser());

            Row row = sheet.createRow((short) 2);
            Cell c1l7 = row.createCell(1);
            c1l7.setCellStyle(style2);
            c1l7.setCellValue(siq.getId_filiale() + " - " + siq.getDe_filiale());

            Row row66 = sheet.createRow((short) 10);

            int cntriga = 11;

            if (siq.getDati().size() > 0) {

                //mi scandisco le colonne
                for (int j = 0; j < colonne_change.size(); j++) {
                    Cell cl5 = row66.createCell(j + 1);
                    cl5.setCellStyle(style3);
                    if (j == 0) {
                        cl5.setCellStyle(style3left);
                    }
                    cl5.setCellValue(colonne_change.get(j));

                }

                Row row77 = sheet.createRow((short) 9);

                row77.setHeight((short) 555);
                for (int j = 0; j < 6; j++) {

                    if (j == 1) {

                        Cell cl5 = row77.createCell(j + 1);
                        cl5.setCellStyle(style3center);
                        cl5.setCellValue("Notes");

                    } else if (j == 2) {
                        Cell cl5 = row77.createCell(j + 2);
                        cl5.setCellStyle(style3center);
                        cl5.setCellValue("Euro TC / Travel Cheques");
                    } else {
                    }
                }

                CellRangeAddress cellRangeAddress = new CellRangeAddress(9, 9, 2, 3);
                sheet.addMergedRegion(cellRangeAddress);

                cellRangeAddress = new CellRangeAddress(9, 9, 4, 5);
                sheet.addMergedRegion(cellRangeAddress);

                ArrayList<ToBranchingSheet_value> dati = siq.getDati();

                double branchtotal = 0;

                for (int i = 0; i < dati.size(); i++) {
                    cntriga++;

                    Row row6 = sheet.createRow((short) cntriga);

                    ToBranchingSheet_value temp = (ToBranchingSheet_value) dati.get(i);

                    ArrayList<String> datitemp = temp.getDati_string();

                    Cell f1 = row6.createCell(1);
                    f1.setCellStyle(style4left);
                    f1.setCellValue(temp.getCurrency());

                    for (int n = 1; n < colonne_change.size(); n++) {

                        if (colonne_change.get(n).equals("Branch Total")) {
                            branchtotal += fd((datitemp.get(n)));
                        }
                        Cell f2 = row6.createCell(n + 1);
                        if (colonne_change.get(n).equals("Branch Rate")) {
                            f2.setCellStyle(cellStylenumRATE);
                        } else {
                            f2.setCellStyle(cellStylenum);
                        }
                        f2.setCellValue(fd(datitemp.get(n)));
                    }

                }

                cntriga++;
                cntriga++;

                Row row9 = sheet.createRow((short) cntriga);

                for (int n = 1; n < colonne_change.size(); n++) {

                    if (n == 1) {

                        Cell f7 = row9.createCell(n + 1);
                        f7.setCellStyle(style3);
                        f7.setCellValue("Total");

                    } else if (colonne_change.get(n).equals("Branch Total")) {
                        Cell f7 = row9.createCell(n + 1);
                        f7.setCellStyle(style3num);
                        f7.setCellValue(branchtotal);
                    }

                }

            }
            cntriga++;
            cntriga++;
            Row row10 = sheet.createRow((short) cntriga);
            Cell f7 = row10.createCell(1);
            f7.setCellStyle(style3);
            f7.setCellValue("Notes:");
            Cell f71 = row10.createCell(2);
            f71.setCellStyle(style3left);
            f71.setCellValue(siq.getNote());
            if (siq.getAuto() != null && siq.getAuto().equals("M")) {
                cntriga++;
                cntriga++;
                Row row12 = sheet.createRow((short) cntriga);
                Cell f15 = row12.createCell(1);
                f15.setCellStyle(style3left);
                f15.setCellValue("OPERATION MANUAL");
            }

            for (int c = 0; c < 20; c++) {
                sheet.autoSizeColumn(c, true);
            }

            try (FileOutputStream out = new FileOutputStream(pdf)) {
                workbook.write(out);
            }
            String base64 = new String(encodeBase64(readFileToByteArray(pdf)));
            pdf.delete();
            return base64;
        } catch (IOException ex) {
            insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
        }
        return null;
    }
    //TO BRANCH CHANGE

    /**
     *
     * @param path
     * @param siq
     * @param datereport
     * @param dati2
     * @return
     */
    public static String TOBRANCH_NOCHANGE_pdf(String path, ToBranchingSheet_value siq, String datereport, ArrayList<NoChangeToBranchingSheet_value> dati2) {

        Phrase vuoto1 = new Phrase("\n");
        PdfPCell cellavuota = new PdfPCell(vuoto1);
        cellavuota.setBorder(NO_BORDER);
        try {
            File pdf = new File(path + generaId(50) + "_ToBranchingSheet.pdf");
            Document document = new Document(A4, 20, 20, 20, 20);
            try(OutputStream ou = new FileOutputStream(pdf)){
            PdfWriter wr = getInstance(document, ou);
            document.open();
            String intestazionePdf = "To Branching Sheet";
            Phrase vuoto = new Phrase("\n");

            PdfPTable table = new PdfPTable(2);
            table.setWidths(columnWidths0);
            table.setWidthPercentage(100);

            Phrase phrase1 = new Phrase();
            phrase1.add(new Chunk(intestazionePdf + " ", f0_bold));
            PdfPCell cell1 = new PdfPCell(phrase1);
            cell1.setBorder(NO_BORDER);

            Paragraph pa1 = new Paragraph(new Phrase("Transfer " + siq.getNumTransfer() + " " + datereport, f3_bold));
            pa1.setAlignment(ALIGN_CENTER);
            PdfPCell cell2 = new PdfPCell(pa1);
            cell2.setBorder(NO_BORDER);
            cell2.setHorizontalAlignment(ALIGN_RIGHT);

            Phrase phrase3 = new Phrase();
            phrase3.add(new Chunk(siq.getId_filiale() + " - " + siq.getDe_filiale(), f1_bold));
            PdfPCell cell3 = new PdfPCell(phrase3);
            cell3.setBorder(NO_BORDER);

            Phrase phrase4 = new Phrase();
            phrase4.add(new Chunk("\n From", f2_bold));
            phrase4.setTabSettings(new TabSettings(30f));
            phrase4.add(TABBING);
            phrase4.add(new Chunk(": " + siq.getFromsafe() + "\n To", f2_bold));
            phrase4.setTabSettings(new TabSettings(30f));
            phrase4.add(TABBING);
            phrase4.add(new Chunk(": " + siq.getTobranch() + "\n User", f2_bold));
            phrase4.setTabSettings(new TabSettings(30f));
            phrase4.add(TABBING);
            phrase4.add(new Chunk(": " + siq.getPinuser(), f2_bold));

            PdfPCell cell4 = new PdfPCell(phrase4);

            cell4.setBorder(NO_BORDER);

            table.addCell(cell1);
            table.addCell(cell2);
            table.addCell(cell3);
            table.addCell(cellavuota);
            table.addCell(cell4);
            table.addCell(cellavuota);
            document.add(table);

            vuoto.setFont(f3_normal);
            document.add(vuoto);

            float[] columnWidths2 = new float[colonne_nochange.size()];

            for (int c = 0; c < colonne_nochange.size(); c++) {
                columnWidths2[c] = 10f;
            }

            if (dati2.size() > 0) {

                PdfPCell[] list = new PdfPCell[colonne_nochange.size()];
                //mi scandisco le colonne
                for (int j = 0; j < colonne_nochange.size(); j++) {
                    Phrase phraset1 = new Phrase();
                    phraset1.add(new Chunk(colonne_nochange.get(j), f5_bold));
                    PdfPCell cellt1 = new PdfPCell(phraset1);
                    cellt1.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt1.setBorder(BOTTOM);

                    cellt1.setBackgroundColor(LIGHT_GRAY);
                    //cellt1.setBorder(Rectangle.BOTTOM);
                    cellt1.setBorderWidth(0.7f);
                    if (j == 0) {
                        cellt1.setHorizontalAlignment(ALIGN_LEFT);
                    }
                    list[j] = cellt1;

                }

                PdfPTable table3 = new PdfPTable(colonne_nochange.size());
                table3.setWidths(columnWidths2);
                table3.setWidthPercentage(100);

                for (PdfPCell list1 : list) {
                    PdfPCell temp = (PdfPCell) (list1);
                    table3.addCell(temp);
                }

                document.add(table3);

                LineSeparator sep = new LineSeparator();
                sep.setOffset(-2);
                sep.setLineWidth((float) 0.5);

                table3 = new PdfPTable(colonne_nochange.size());
                table3.setWidths(columnWidths2);
                table3.setWidthPercentage(100);

                float totQuantity = 0, total = 0;

                for (int i = 0; i < dati2.size(); i++) {
                    NoChangeToBranchingSheet_value temp = (NoChangeToBranchingSheet_value) dati2.get(i);

                    Phrase phraset = new Phrase();
                    phraset.add(new Chunk(temp.getCategory(), f3_normal));
                    PdfPCell cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_LEFT);
                    cellt.setBorder(BOTTOM);
                    table3.addCell(cellt);

                    phraset = new Phrase();
                    phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(fd(temp.getQuantity()), 0)), f3_normal));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setBorder(BOTTOM);
                    table3.addCell(cellt);

                    phraset = new Phrase();
                    phraset.add(new Chunk(formatMysqltoDisplay(temp.getAmount()), f3_normal));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setBorder(BOTTOM);
                    table3.addCell(cellt);

                    double d = fd(temp.getQuantity()) * fd(temp.getAmount());

                    phraset = new Phrase();
                    phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(d, 2)), f2_normal));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setBorder(BOTTOM);
                    table3.addCell(cellt);

                    totQuantity += fd(temp.getQuantity());
                    total += fd(temp.getQuantity()) * fd(temp.getAmount());

                }

                document.add(table3);

                LineSeparator ls = new LineSeparator();
                ls.setLineWidth(0.7f);
                //ls.setOffset(-1f);
                document.add(ls);

                table3 = new PdfPTable(colonne_nochange.size());
                table3.setWidths(columnWidths2);
                table3.setWidthPercentage(100);

                Phrase phraset = new Phrase();
                phraset.add(new Chunk("Total", f4_bold));
                PdfPCell cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_LEFT);
                cellt.setBorder(BOTTOM);
                table3.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(totQuantity, 0)), f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table3.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk("", f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table3.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(total, 2)) + "", f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table3.addCell(cellt);

                document.add(table3);
                ls = new LineSeparator();
                ls.setLineWidth(0.7f);
                // ls.setOffset(-1f);
                document.add(ls);

            }

            document.add(vuoto1);
            document.add(new Paragraph("Notes:", f2_bold));
            document.add(new Paragraph(siq.getNote(), f3_normal));

//            
            document.close();
            wr.close();
            ou.close();
            String base64 = new String(encodeBase64(readFileToByteArray(pdf)));
            pdf.delete();
            return base64;
        }
        } catch (IOException | DocumentException ex) {
            insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
        }
        return null;
    }

    /**
     *
     * @param path
     * @param siq
     * @param datereport
     * @param dati2
     * @return
     */
    public static String TOBRANCH_NOCHANGE_xls(String path, ToBranchingSheet_value siq,
            String datereport,
            ArrayList<NoChangeToBranchingSheet_value> dati2) {
        try {
            String intestazionePdf = "To Branching Sheet";
            File pdf = new File(path + generaId(50) + "_ToBranchingSheet.xlsx");
            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet("ToBranchingSheet");
            //CREAZIONE FONT
            XSSFFont font = workbook.createFont();
            font.setFontName(FONT_ARIAL);
            font.setFontHeightInPoints((short) 16);
            font.setBold(true);

            XSSFCellStyle style1 = (XSSFCellStyle) workbook.createCellStyle();
            style1.setFont(font);

            XSSFFont font2 = workbook.createFont();
            font2.setFontName(FONT_ARIAL);
            font2.setFontHeightInPoints((short) 12);
            font2.setBold(true);

            XSSFCellStyle style2 = (XSSFCellStyle) workbook.createCellStyle();
            style2.setFont(font2);

            XSSFDataFormat hssfDataFormat = (XSSFDataFormat) workbook.createDataFormat();

            XSSFFont font3 = workbook.createFont();
            font3.setFontName(FONT_ARIAL);
            font3.setFontHeightInPoints((short) 10);
            font3.setBold(true);

            XSSFCellStyle style3 = workbook.createCellStyle();
            style3.setFont(font3);
            style3.setAlignment(RIGHT);
            style3.setBorderTop(THIN);
            style3.setBorderBottom(THIN);

            XSSFCellStyle style3INT = workbook.createCellStyle();
            style3INT.setFont(font3);
            style3INT.setAlignment(RIGHT);
            style3INT.setBorderTop(THIN);
            style3INT.setBorderBottom(THIN);
            style3INT.setDataFormat(hssfDataFormat.getFormat(formatdataCellint));

            XSSFCellStyle style3num = workbook.createCellStyle();
            style3num.setFont(font3);
            style3num.setAlignment(RIGHT);
            style3num.setBorderTop(THIN);
            style3num.setBorderBottom(THIN);
            style3num.setDataFormat(hssfDataFormat.getFormat(formatdataCell));

            XSSFCellStyle style3center = workbook.createCellStyle();
            style3center.setFont(font3);
            style3center.setWrapText(true);
            style3center.setAlignment(CENTER);
            style3center.setBorderTop(THIN);
            style3center.setBorderBottom(THIN);

            XSSFCellStyle style3left = (XSSFCellStyle) workbook.createCellStyle();
            style3left.setFont(font3);
            style3left.setAlignment(LEFT);
            style3left.setBorderTop(THIN);
            style3left.setBorderBottom(THIN);

            XSSFFont font4 = workbook.createFont();
            font4.setFontName(FONT_ARIAL);
            font4.setFontHeightInPoints((short) 10);

            XSSFCellStyle style4 = (XSSFCellStyle) workbook.createCellStyle();
            style4.setAlignment(RIGHT);
            style4.setBorderTop(THIN);
            style4.setBorderBottom(THIN);

            XSSFCellStyle style4INT = (XSSFCellStyle) workbook.createCellStyle();
            style4INT.setAlignment(RIGHT);
            style4INT.setBorderTop(THIN);
            style4INT.setBorderBottom(THIN);
            style4INT.setDataFormat(hssfDataFormat.getFormat(formatdataCellint));

            XSSFCellStyle style4num = (XSSFCellStyle) workbook.createCellStyle();
            style4num.setAlignment(RIGHT);
            style4num.setBorderTop(THIN);
            style4num.setBorderBottom(THIN);
            style4num.setDataFormat(hssfDataFormat.getFormat(formatdataCell));

            XSSFCellStyle style4left = (XSSFCellStyle) workbook.createCellStyle();
            style4left.setAlignment(LEFT);
            style4left.setBorderTop(THIN);
            style4left.setBorderBottom(THIN);

            Row rowP = sheet.createRow((short) 1);

            Cell cl = rowP.createCell(1);
            cl.setCellStyle(style1);
            cl.setCellValue(intestazionePdf);

            Cell cl32 = rowP.createCell(3);
            cl32.setCellStyle(style2);
            cl32.setCellValue("Transfer " + siq.getNumTransfer() + " " + datereport);

//           
//            Row rowP3 = sheet.createRow((short) 3);
//            Cell cl2 = rowP3.createCell(1);
//            cl2.setCellStyle(style2);
//            cl2.setCellValue("Prepared by: " );
//             Cell cl21 = rowP3.createCell(1);
//            cl21.setCellStyle(style2);
//            cl21.setCellValue(siq.getId_filiale() + " " + siq.getDe_filiale());
            Row rowP4 = sheet.createRow((short) 5);

            Cell cl4 = rowP4.createCell(1);
            cl4.setCellStyle(style2);
            cl4.setCellValue("From : ");

            Cell cl42 = rowP4.createCell(2);
            cl42.setCellStyle(style2);
            cl42.setCellValue(siq.getFromsafe());

            Row rowP5 = sheet.createRow((short) 6);

            Cell cl55 = rowP5.createCell(1);
            cl55.setCellStyle(style2);
            cl55.setCellValue("To: ");

            Cell cl552 = rowP5.createCell(2);
            cl552.setCellStyle(style2);
            cl552.setCellValue(siq.getTobranch());

            Row rowP7 = sheet.createRow((short) 7);

            Cell cl7 = rowP7.createCell(1);
            cl7.setCellStyle(style2);
            cl7.setCellValue("User: ");

            Cell cl72 = rowP7.createCell(2);
            cl72.setCellStyle(style2);
            cl72.setCellValue(siq.getPinuser());

            Row row = sheet.createRow((short) 2);
            Cell c1l7 = row.createCell(1);
            c1l7.setCellStyle(style2);
            c1l7.setCellValue(siq.getId_filiale() + " - " + siq.getDe_filiale());

//            Row row66 = sheet.createRow((short) 10);
            int cntriga = 11;

            if (dati2.size() > 0) {

                cntriga++;
                Row row10 = sheet.createRow((short) cntriga);

                //mi scandisco le colonne
                for (int j = 0; j < colonne_nochange.size(); j++) {
                    Cell f6 = row10.createCell(j + 1);
                    f6.setCellStyle(style3);
                    if (j == 0) {
                        f6.setCellStyle(style3left);
                    }
                    f6.setCellValue(colonne_nochange.get(j));

                }

                cntriga++;

                double totQuantity = 0, total = 0;

                for (int i = 0; i < dati2.size(); i++) {

                    cntriga++;
                    Row row11 = sheet.createRow((short) cntriga);

                    NoChangeToBranchingSheet_value temp = (NoChangeToBranchingSheet_value) dati2.get(i);

                    Cell f7 = row11.createCell(1);
                    f7.setCellStyle(style4left);
                    f7.setCellValue(temp.getCategory());

                    Cell f8 = row11.createCell(2);
                    f8.setCellStyle(style4);
                    f8.setCellStyle(style4INT);
                    f8.setCellValue(fd(roundDoubleandFormat(fd(temp.getQuantity()), 0)));

                    Cell f9 = row11.createCell(3);
                    f9.setCellStyle(style4num);
                    f9.setCellValue(fd(temp.getAmount()));

                    double d = fd(temp.getQuantity()) * fd(temp.getAmount());

                    Cell f10 = row11.createCell(4);
                    f10.setCellStyle(style4num);
                    f10.setCellValue(fd(roundDoubleandFormat(d, 2)));

                    totQuantity += fd(temp.getQuantity());
                    total += fd(temp.getQuantity()) * fd(temp.getAmount());

                    cntriga++;
                    cntriga++;
                }

                cntriga++;
                Row row12 = sheet.createRow((short) cntriga);

                Cell f15 = row12.createCell(1);
                f15.setCellStyle(style3);
                f15.setCellValue("Total");

                Cell f16 = row12.createCell(2);
                f16.setCellStyle(style3INT);
                f16.setCellValue(fd(roundDoubleandFormat(totQuantity, 0)));

                Cell f17 = row12.createCell(4);
                f17.setCellStyle(style3num);
                f17.setCellValue(fd(roundDoubleandFormat(total, 2)));

            }

            cntriga++;
            cntriga++;
            Row row10 = sheet.createRow((short) cntriga);
            Cell f7 = row10.createCell(1);
            f7.setCellStyle(style3);
            f7.setCellValue("Notes:");
            Cell f71 = row10.createCell(2);
            f71.setCellStyle(style3left);
            f71.setCellValue(siq.getNote());

            if (siq.getAuto() != null && siq.getAuto().equals("M")) {
                cntriga++;
                cntriga++;
                Row row12 = sheet.createRow((short) cntriga);
                Cell f15 = row12.createCell(1);
                f15.setCellStyle(style3left);
                f15.setCellValue("OPERATION MANUAL");
            }

            for (int c = 0; c < 20; c++) {
                sheet.autoSizeColumn(c, true);
            }

            try (FileOutputStream out = new FileOutputStream(pdf)) {
                workbook.write(out);
            }
            String base64 = new String(encodeBase64(readFileToByteArray(pdf)));
            pdf.delete();
            return base64;
        } catch (IOException ex) {
            insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
        }
        return null;
    }

    //FROM BRANCH NO CHANGE
    /**
     *
     * @param path
     * @param siq
     * @param datereport
     * @param dati2
     * @return
     */
    public static String FROMBRANCH_NOCHANGE_pdf(String path, FromBranchingSheet_value siq, String datereport, ArrayList<NoChangeFromBranchingSheet_value> dati2) {

        Phrase vuoto1 = new Phrase("\n");
        PdfPCell cellavuota = new PdfPCell(vuoto1);
        cellavuota.setBorder(NO_BORDER);
        String intestazionePdf = "From Branching Sheet";
        Phrase vuoto = new Phrase("\n");

        try {
            File pdf = new File(path + generaId(50) + "_FromBranchingSheet.pdf");
            Document document = new Document(A4, 20, 20, 20, 20);
            try (OutputStream ou = new FileOutputStream(pdf)){
            PdfWriter wr = getInstance(document, ou);
            document.open();

            PdfPTable table = new PdfPTable(2);
            table.setWidths(columnWidths0);
            table.setWidthPercentage(100);

            Phrase phrase1 = new Phrase();
            phrase1.add(new Chunk(intestazionePdf + " ", f0_bold));
            PdfPCell cell1 = new PdfPCell(phrase1);
            cell1.setBorder(NO_BORDER);

            Paragraph pa1 = new Paragraph(new Phrase("Transfer " + siq.getNumTransfer() + " " + datereport, f3_bold));
            pa1.setAlignment(ALIGN_CENTER);
            PdfPCell cell2 = new PdfPCell(pa1);
            cell2.setBorder(NO_BORDER);
            cell2.setHorizontalAlignment(ALIGN_RIGHT);

            Phrase phrase3 = new Phrase();
            phrase3.add(new Chunk(siq.getId_filiale() + " - " + siq.getDe_filiale(), f1_bold));
            PdfPCell cell3 = new PdfPCell(phrase3);
            cell3.setBorder(NO_BORDER);

            Phrase phrase4 = new Phrase();
            phrase4.add(new Chunk("\n From", f2_bold));
            phrase4.setTabSettings(new TabSettings(30f));
            phrase4.add(TABBING);
            phrase4.add(new Chunk(": " + siq.getTobranch() + "\n To", f2_bold));
            phrase4.setTabSettings(new TabSettings(30f));
            phrase4.add(TABBING);
            phrase4.add(new Chunk(": " + siq.getFromsafe() + "\n User", f2_bold));
            phrase4.setTabSettings(new TabSettings(30f));
            phrase4.add(TABBING);
            phrase4.add(new Chunk(": " + siq.getPinuser(), f2_bold));

            PdfPCell cell4 = new PdfPCell(phrase4);

            cell4.setBorder(NO_BORDER);

            table.addCell(cell1);
            table.addCell(cell2);
            table.addCell(cell3);
            table.addCell(cellavuota);
            table.addCell(cell4);
            table.addCell(cellavuota);
            document.add(table);

            vuoto.setFont(f3_normal);
            document.add(vuoto);

            float[] columnWidths2 = new float[colonne_nochange.size()];
            for (int c = 0; c < colonne_nochange.size(); c++) {
                columnWidths2[c] = 10f;
            }

            if (dati2.size() > 0) {

                PdfPTable table2 = new PdfPTable(colonne_nochange.size());
                table2.setWidths(columnWidths2);
                table2.setWidthPercentage(100);

                PdfPCell[] list = new PdfPCell[colonne_nochange.size()];
                //mi scandisco le colonne
                for (int j = 0; j < colonne_nochange.size(); j++) {
                    Phrase phraset1 = new Phrase();
                    phraset1.add(new Chunk(colonne_nochange.get(j), f5_bold));
                    PdfPCell cellt1 = new PdfPCell(phraset1);
                    cellt1.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt1.setBorder(TOP | BOTTOM);

                    cellt1.setBackgroundColor(LIGHT_GRAY);
                    //cellt1.setBorder(Rectangle.BOTTOM);
                    cellt1.setBorderWidth(0.7f);
                    if (j == 0) {
                        cellt1.setHorizontalAlignment(ALIGN_LEFT);
                    }
                    list[j] = cellt1;

                }

                PdfPTable table3 = new PdfPTable(colonne_nochange.size());
                table3.setWidths(columnWidths2);
                table3.setWidthPercentage(100);

                for (PdfPCell list1 : list) {
                    PdfPCell temp = (PdfPCell) (list1);
                    table3.addCell(temp);
                }

                document.add(table3);

                LineSeparator sep = new LineSeparator();
                sep.setOffset(-2);
                sep.setLineWidth((float) 0.5);

                //  document.add(table2);
                //  document.add(sep);
                table3 = new PdfPTable(colonne_nochange.size());
                table3.setWidths(columnWidths2);
                table3.setWidthPercentage(100);

                double totQuantity = 0, total = 0;

                for (int i = 0; i < dati2.size(); i++) {
                    NoChangeFromBranchingSheet_value temp = (NoChangeFromBranchingSheet_value) dati2.get(i);

                    Phrase phraset = new Phrase();
                    phraset.add(new Chunk(temp.getCategory(), f3_normal));
                    PdfPCell cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_LEFT);
                    cellt.setBorder(BOTTOM);
                    table3.addCell(cellt);

                    phraset = new Phrase();
                    phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(fd(temp.getQuantity()), 0)), f3_normal));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setBorder(BOTTOM);
                    table3.addCell(cellt);

                    phraset = new Phrase();
                    phraset.add(new Chunk(formatMysqltoDisplay(temp.getAmount()), f3_normal));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setBorder(BOTTOM);
                    table3.addCell(cellt);

                    double d = fd(temp.getQuantity()) * fd(temp.getAmount());

                    phraset = new Phrase();
                    phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(d, 2)), f3_normal));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setBorder(BOTTOM);
                    table3.addCell(cellt);

                    totQuantity += fd(temp.getQuantity());
                    total += fd(temp.getQuantity()) * fd(temp.getAmount());

                }

                document.add(table3);

                LineSeparator ls = new LineSeparator();
                ls.setLineWidth(0.7f);
                //ls.setOffset(-1f);
                document.add(ls);

                table3 = new PdfPTable(colonne_nochange.size());
                table3.setWidths(columnWidths2);
                table3.setWidthPercentage(100);

                Phrase phraset = new Phrase();
                phraset.add(new Chunk("Total", f4_bold));
                PdfPCell cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_LEFT);
                cellt.setBorder(BOTTOM);
                table3.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(totQuantity, 0)) + "", f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table3.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk("", f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table3.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(total, 2)) + "", f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table3.addCell(cellt);

                document.add(table3);
                ls = new LineSeparator();
                ls.setLineWidth(0.7f);
                // ls.setOffset(-1f);
                document.add(ls);

            }
//            

            document.add(vuoto1);
            document.add(new Paragraph("Notes:", f2_bold));
            document.add(new Paragraph(siq.getNote(), f3_normal));

            if (siq.getNoteFROM() != null) {
                if (!siq.getNoteFROM().trim().equals("")) {
                    document.add(vuoto1);
                    document.add(new Paragraph("Notes: (FROM)", f2_bold));
                    document.add(new Paragraph(siq.getNoteFROM(), f3_normal));
                }
            }

            if (siq.getAuto().equals("M")) {
                document.add(vuoto1);
                document.add(new Paragraph("OPERATION MANUAL", f2_bold));
            }

            document.close();
            wr.close();
            ou.close();
            String base64 = new String(encodeBase64(readFileToByteArray(pdf)));
            pdf.delete();
            return base64;
        }
        } catch (IOException | DocumentException ex) {
            insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
        }
        return null;

    }

    /**
     *
     * @param path
     * @param siq
     * @param datereport
     * @param dati2
     * @return
     */
    public static String FROMBRANCH_NOCHANGE_xls(String path, FromBranchingSheet_value siq, String datereport, ArrayList<NoChangeFromBranchingSheet_value> dati2) {

        //String outputfile = "FromBranchingSheet.pdf";
        try {

            int cntriga = 12;
            String intestazionePdf = "From Branching Sheet";
            File pdf = new File(path + generaId(50) + "_FromBranchingSheet.xlsx");
            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet(intestazionePdf);
            //CREAZIONE FONT
            XSSFFont font = workbook.createFont();
            font.setFontName(FONT_ARIAL);
            font.setFontHeightInPoints((short) 16);
            font.setBold(true);

            XSSFCellStyle style1 = workbook.createCellStyle();
            style1.setFont(font);

            XSSFDataFormat hssfDataFormat = workbook.createDataFormat();

            XSSFFont font2 = workbook.createFont();
            font2.setFontName(FONT_ARIAL);
            font2.setFontHeightInPoints((short) 12);
            font2.setBold(true);

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

            XSSFCellStyle style3INT = workbook.createCellStyle();
            style3INT.setFont(font3);
            style3INT.setAlignment(RIGHT);
            style3INT.setBorderTop(THIN);
            style3INT.setBorderBottom(THIN);
            style3INT.setDataFormat(hssfDataFormat.getFormat(formatdataCellint));

            XSSFCellStyle style3num = workbook.createCellStyle();
            style3num.setFont(font3);
            style3num.setAlignment(RIGHT);
            style3num.setBorderTop(THIN);
            style3num.setBorderBottom(THIN);
            style3num.setDataFormat(hssfDataFormat.getFormat(formatdataCell));

            XSSFCellStyle style3center = workbook.createCellStyle();
            style3center.setFont(font3);
            style3center.setWrapText(true);
            style3center.setAlignment(CENTER);
            style3center.setBorderTop(THIN);
            style3center.setBorderBottom(THIN);

            XSSFCellStyle style3left = workbook.createCellStyle();
            style3left.setFont(font3);
            style3left.setAlignment(LEFT);
            style3left.setBorderTop(THIN);
            style3left.setBorderBottom(THIN);

            XSSFFont font4 = workbook.createFont();
            font4.setFontName(FONT_ARIAL);
            font4.setFontHeightInPoints((short) 10);

            XSSFCellStyle style4 = workbook.createCellStyle();
            style4.setAlignment(RIGHT);
            style4.setBorderTop(THIN);
            style4.setBorderBottom(THIN);

            XSSFCellStyle style4INT = workbook.createCellStyle();
            style4INT.setAlignment(RIGHT);
            style4INT.setBorderTop(THIN);
            style4INT.setBorderBottom(THIN);
            style4INT.setDataFormat(hssfDataFormat.getFormat(formatdataCellint));

            XSSFCellStyle style4num = workbook.createCellStyle();
            style4num.setAlignment(RIGHT);
            style4num.setBorderTop(THIN);
            style4num.setBorderBottom(THIN);
            style4num.setDataFormat(hssfDataFormat.getFormat(formatdataCell));

            XSSFCellStyle style4left = workbook.createCellStyle();
            style4left.setAlignment(LEFT);
            style4left.setBorderTop(THIN);
            style4left.setBorderBottom(THIN);

            Row rowP = sheet.createRow((short) 1);

            Cell cl = rowP.createCell(1);
            cl.setCellStyle(style1);
            cl.setCellValue(intestazionePdf);

            Cell cl32 = rowP.createCell(3);
            cl32.setCellStyle(style2);
            cl32.setCellValue("Transfer " + siq.getNumTransfer() + " " + datereport);
            Row rowP4 = sheet.createRow((short) 5);

            Cell cl4 = rowP4.createCell(1);
            cl4.setCellStyle(style2);
            cl4.setCellValue("From : ");

            Cell cl42 = rowP4.createCell(2);
            cl42.setCellStyle(style2);
            cl42.setCellValue(siq.getTobranch());

            Row rowP5 = sheet.createRow((short) 6);

            Cell cl55 = rowP5.createCell(1);
            cl55.setCellStyle(style2);
            cl55.setCellValue("To: ");

            Cell cl552 = rowP5.createCell(2);
            cl552.setCellStyle(style2);
            cl552.setCellValue(siq.getFromsafe());

            Row rowP7 = sheet.createRow((short) 7);

            Cell cl7 = rowP7.createCell(1);
            cl7.setCellStyle(style2);
            cl7.setCellValue("User: ");

            Cell cl72 = rowP7.createCell(2);
            cl72.setCellStyle(style2);
            cl72.setCellValue(siq.getPinuser());

            Row row = sheet.createRow((short) 2);
            Cell c1l7 = row.createCell(1);
            c1l7.setCellStyle(style2);
            c1l7.setCellValue(siq.getId_filiale() + " - " + siq.getDe_filiale());

            if (dati2.size() > 0) {

                cntriga++;
                Row row10 = sheet.createRow((short) cntriga);

                //mi scandisco le colonne
                for (int j = 0; j < colonne_nochange.size(); j++) {
                    Cell f6 = row10.createCell(j + 1);
                    f6.setCellStyle(style3);
                    if (j == 0) {
                        f6.setCellStyle(style3left);
                    }
                    f6.setCellValue(colonne_nochange.get(j));

                }

                cntriga++;

                //  document.add(table2);
                //  document.add(sep);
                double totQuantity = 0, total = 0;

                for (int i = 0; i < dati2.size(); i++) {

                    Row row11 = sheet.createRow((short) cntriga);

                    NoChangeFromBranchingSheet_value temp = (NoChangeFromBranchingSheet_value) dati2.get(i);

                    Cell f7 = row11.createCell(1);
                    f7.setCellStyle(style4left);
                    f7.setCellValue(temp.getCategory());

                    Cell f8 = row11.createCell(2);
                    f8.setCellStyle(style4INT);
                    f8.setCellValue(fd(roundDoubleandFormat(fd(temp.getQuantity()), 0)));

                    Cell f9 = row11.createCell(3);
                    f9.setCellStyle(style4num);
                    f9.setCellValue(fd(temp.getAmount()));

                    double d = fd(temp.getQuantity()) * fd(temp.getAmount());

                    Cell f10 = row11.createCell(4);
                    f10.setCellStyle(style4num);
                    f10.setCellValue(fd(roundDoubleandFormat(d, 2)));

                    totQuantity += fd(temp.getQuantity());
                    total += fd(temp.getQuantity()) * fd(temp.getAmount());
                    cntriga++;
                }

                cntriga++;
                Row row12 = sheet.createRow((short) cntriga);

                Cell f15 = row12.createCell(1);
                f15.setCellStyle(style3left);
                f15.setCellValue("Total");

                Cell f16 = row12.createCell(2);
                f16.setCellStyle(style3INT);
                f16.setCellValue(fd(roundDoubleandFormat(totQuantity, 0)));

                Cell f17 = row12.createCell(4);
                f17.setCellStyle(style3num);
                f17.setCellValue(fd(roundDoubleandFormat(total, 2)));

            }

            cntriga++;
            cntriga++;

            Row row10 = sheet.createRow((short) cntriga);
            Cell f7 = row10.createCell(1);
            f7.setCellStyle(style3);
            f7.setCellValue("Notes:");
            Cell f71 = row10.createCell(2);
            f71.setCellStyle(style3left);
            f71.setCellValue(siq.getNote());

            if (siq.getAuto().equals("M")) {
                cntriga++;
                cntriga++;
                Row row12 = sheet.createRow((short) cntriga);
                Cell f15 = row12.createCell(1);
                f15.setCellStyle(style3left);
                f15.setCellValue("OPERATION MANUAL");
            }

//            
            for (int c = 0; c < 20; c++) {
                sheet.autoSizeColumn(c, true);
            }

            try (FileOutputStream out = new FileOutputStream(pdf)) {
                workbook.write(out);
            }
            String base64 = new String(encodeBase64(readFileToByteArray(pdf)));
            pdf.delete();
            return base64;
        } catch (IOException ex) {
            insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
        }
        return null;
    }
    //FROM BRANCH NO CHANGE

    //FROM BRANCH CHANGE
    /**
     *
     * @param path
     * @param siq
     * @param datereport
     * @return
     */
    public static String FROMBRANCH_CHANGE_pdf(String path, FromBranchingSheet_value siq, String datereport) {

        float[] columnWidths2 = new float[colonne_change.size()];
        for (int c = 0; c < colonne_change.size(); c++) {
            columnWidths2[c] = 10f;
        }
        String intestazionePdf = "From Branching Sheet";
        Phrase vuoto = new Phrase("\n");

        Phrase vuoto1 = new Phrase("\n");
        PdfPCell cellavuota = new PdfPCell(vuoto1);
        cellavuota.setBorder(NO_BORDER);

        try {

            File pdf = new File(path + generaId(50) + "FromBranchingSheet.pdf");
            Document document = new Document(A4, 20, 20, 20, 20);
            try(OutputStream ou = new FileOutputStream(pdf)){
            PdfWriter wr = getInstance(document, ou);
            document.open();

            PdfPTable table = new PdfPTable(2);
            table.setWidths(columnWidths0);
            table.setWidthPercentage(100);

            Phrase phrase1 = new Phrase();
            phrase1.add(new Chunk(intestazionePdf + " ", f0_bold));
            PdfPCell cell1 = new PdfPCell(phrase1);
            cell1.setBorder(NO_BORDER);

            Paragraph pa1 = new Paragraph(new Phrase("Transfer " + siq.getNumTransfer() + " " + datereport, f3_bold));
            pa1.setAlignment(ALIGN_CENTER);
            PdfPCell cell2 = new PdfPCell(pa1);
            cell2.setBorder(NO_BORDER);
            cell2.setHorizontalAlignment(ALIGN_RIGHT);

            Phrase phrase3 = new Phrase();
            phrase3.add(new Chunk(siq.getId_filiale() + " - " + siq.getDe_filiale(), f1_bold));
            PdfPCell cell3 = new PdfPCell(phrase3);
            cell3.setBorder(NO_BORDER);

            String ggg = " \n From \t\t : " + siq.getTobranch() + " \n To \t\t :" + siq.getFromsafe() + "\n User \t\t: " + siq.getPinuser();

            Phrase phrase4 = new Phrase();
            phrase4.add(new Chunk("\n From", f2_bold));
            phrase4.setTabSettings(new TabSettings(30f));
            phrase4.add(TABBING);
            phrase4.add(new Chunk(": " + siq.getTobranch() + "\n To", f2_bold));
            phrase4.setTabSettings(new TabSettings(30f));
            phrase4.add(TABBING);
            phrase4.add(new Chunk(": " + siq.getFromsafe() + "\n User", f2_bold));
            phrase4.setTabSettings(new TabSettings(30f));
            phrase4.add(TABBING);
            phrase4.add(new Chunk(": " + siq.getPinuser(), f2_bold));

            PdfPCell cell4 = new PdfPCell(phrase4);

            cell4.setBorder(NO_BORDER);

            table.addCell(cell1);
            table.addCell(cell2);
            table.addCell(cell3);
            table.addCell(cellavuota);
            table.addCell(cell4);
            table.addCell(cellavuota);
            document.add(table);

            vuoto.setFont(f3_normal);
            document.add(vuoto);

            if (siq.getDati().size() > 0) {

                PdfPTable table2 = new PdfPTable(colonne_change.size());
                table2.setWidths(columnWidths2);
                table2.setWidthPercentage(100);

                PdfPCell[] list = new PdfPCell[colonne_change.size()];
                //mi scandisco le colonne
                for (int j = 0; j < colonne_change.size(); j++) {
                    Phrase phraset1 = new Phrase();
                    phraset1.add(new Chunk(colonne_change.get(j), f5_bold));
                    PdfPCell cellt1 = new PdfPCell(phraset1);
                    cellt1.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt1.setBorder(BOTTOM | TOP);

                    cellt1.setBackgroundColor(LIGHT_GRAY);
                    //cellt1.setBorder(Rectangle.BOTTOM);
                    cellt1.setBorderWidth(0.7f);
                    if (j == 0) {
                        cellt1.setHorizontalAlignment(ALIGN_LEFT);
                    }
                    list[j] = cellt1;

                }

                PdfPTable table6 = new PdfPTable(columnWidths1);
                table6.setWidthPercentage(100);

                for (int j = 0; j < 6; j++) {

                    if (j == 2) {
                        Phrase phraset1 = new Phrase();
                        phraset1.add(new Chunk("Notes", f5_bold));
                        PdfPCell cellt1 = new PdfPCell(phraset1);
                        cellt1.setHorizontalAlignment(ALIGN_CENTER);
                        cellt1.setBorder(TOP);
                        cellt1.setBackgroundColor(LIGHT_GRAY);
                        //cellt1.setBorder(Rectangle.BOTTOM);
                        cellt1.setBorderWidth(0.7f);
                        table6.addCell(cellt1);
                    } else if (j == 3) {
                        Phrase phraset1 = new Phrase();
                        phraset1.add(new Chunk("", f5_bold));
                        PdfPCell cellt1 = new PdfPCell(phraset1);
                        cellt1.setHorizontalAlignment(ALIGN_RIGHT);
                        cellt1.setBorder(TOP);
                        cellt1.setBackgroundColor(LIGHT_GRAY);
                        //cellt1.setBorder(Rectangle.BOTTOM);
                        cellt1.setBorderWidth(0.7f);
                        table6.addCell(cellt1);
                    } else if (j == 4) {
                        Phrase phraset1 = new Phrase();
                        phraset1.add(new Chunk("Euro TC / Travel Cheques", f5_bold));
                        PdfPCell cellt1 = new PdfPCell(phraset1);
                        cellt1.setHorizontalAlignment(ALIGN_RIGHT);
                        cellt1.setBorder(TOP);
                        cellt1.setBackgroundColor(LIGHT_GRAY);
                        //cellt1.setBorder(Rectangle.BOTTOM);
                        cellt1.setBorderWidth(0.7f);
                        table6.addCell(cellt1);
                    } else {
                        Phrase phraset1 = new Phrase();
                        phraset1.add(new Chunk("", f5_bold));
                        PdfPCell cellt1 = new PdfPCell(phraset1);
                        cellt1.setHorizontalAlignment(ALIGN_LEFT);
                        cellt1.setBorder(TOP);

                        cellt1.setBackgroundColor(LIGHT_GRAY);
                        //cellt1.setBorder(Rectangle.BOTTOM);
                        cellt1.setBorderWidth(0.7f);
                        table6.addCell(cellt1);
                    }
                }

                document.add(table6);

                PdfPTable table3 = new PdfPTable(colonne_change.size());
                table3.setWidths(columnWidths2);
                table3.setWidthPercentage(100);

                for (PdfPCell list1 : list) {
                    PdfPCell temp = (PdfPCell) (list1);
                    table3.addCell(temp);
                }

                document.add(table3);

                LineSeparator sep = new LineSeparator();
                sep.setOffset(-2);
                sep.setLineWidth((float) 0.5);

                //  document.add(table2);
                //  document.add(sep);
                ArrayList<FromBranchingSheet_value> dati = siq.getDati();

                PdfPCell cell;
                Phrase phraset;
                PdfPCell cellt;

                table3 = new PdfPTable(colonne_change.size());
                table3.setWidths(columnWidths2);
                table3.setWidthPercentage(100);

                double branchtotal = 0, spreadtotal = 0, perctotal = 0;

                for (int i = 0; i < dati.size(); i++) {
                    FromBranchingSheet_value temp = (FromBranchingSheet_value) dati.get(i);

                    ArrayList<String> datitemp = temp.getDati_string();

                    phraset = new Phrase();
                    phraset.add(new Chunk(temp.getCurrency(), f3_normal));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_LEFT);
                    cellt.setBorder(BOTTOM);
                    table3.addCell(cellt);
                    boolean perc = false;

                    for (int n = 1; n < colonne_change.size(); n++) {

                        if (colonne_change.get(n).equals("Branch Total")) {
                            branchtotal += fd((datitemp.get(n)));
                        }

                        phraset = new Phrase();
                        if (!perc) {
                            phraset.add(new Chunk(formatMysqltoDisplay(datitemp.get(n)), f3_normal));
                        } else {
                            phraset.add(new Chunk(formatMysqltoDisplay(datitemp.get(n)) + " %", f3_normal));
                        }
                        cellt = new PdfPCell(phraset);
                        cellt.setHorizontalAlignment(ALIGN_RIGHT);
                        cellt.setBorder(BOTTOM);
                        table3.addCell(cellt);

                    }

                }

                document.add(table3);

                LineSeparator ls = new LineSeparator();
                ls.setLineWidth(0.7f);
                ls.setOffset(-1f);
                document.add(ls);

                table3 = new PdfPTable(colonne_change.size());
                table3.setWidths(columnWidths2);
                table3.setWidthPercentage(100);

                phraset = new Phrase();
                phraset.add(new Chunk("", f1_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table3.addCell(cellt);

                for (int n = 1; n < colonne_change.size(); n++) {

                    if (n == 1) {
                        phraset = new Phrase();
                        phraset.add(new Chunk("Total", f4_bold));
                        cellt = new PdfPCell(phraset);
                        cellt.setHorizontalAlignment(ALIGN_RIGHT);
                        cellt.setBorder(BOTTOM);
                        table3.addCell(cellt);
                    } else if (colonne_change.get(n).equals("Branch Total")) {
                        phraset = new Phrase();
                        phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(branchtotal, 2)), f4_bold));
                        cellt = new PdfPCell(phraset);
                        cellt.setHorizontalAlignment(ALIGN_RIGHT);
                        cellt.setBorder(BOTTOM);
                        table3.addCell(cellt);

                    } else if (colonne_change.get(n).equals("Spread")) {
                        phraset = new Phrase();
                        phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(spreadtotal, 2)), f4_bold));
                        cellt = new PdfPCell(phraset);
                        cellt.setHorizontalAlignment(ALIGN_RIGHT);
                        cellt.setBorder(BOTTOM);
                        table3.addCell(cellt);

                    } else if (colonne_change.get(n).equals("%")) {
                        phraset = new Phrase();
                        phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(perctotal, 2)) + "%", f4_bold));
                        cellt = new PdfPCell(phraset);
                        cellt.setHorizontalAlignment(ALIGN_RIGHT);
                        cellt.setBorder(BOTTOM);
                        table3.addCell(cellt);

                    } else {
                        phraset = new Phrase();
                        phraset.add(new Chunk("", f3_normal));
                        cellt = new PdfPCell(phraset);
                        cellt.setHorizontalAlignment(ALIGN_RIGHT);
                        cellt.setBorder(BOTTOM);
                        table3.addCell(cellt);
                    }

                }

                document.add(table3);

            }
            document.add(vuoto1);
            document.add(new Paragraph("Notes:", f2_bold));
            document.add(new Paragraph(siq.getNote(), f3_normal));

            if (siq.getNoteFROM() != null) {
                if (!siq.getNoteFROM().trim().equals("")) {
                    document.add(vuoto1);
                    document.add(new Paragraph("Notes: (FROM)", f2_bold));
                    document.add(new Paragraph(siq.getNoteFROM(), f3_normal));
                }
            }

            if (siq.getAuto().equals("M")) {
                document.add(vuoto1);
                document.add(new Paragraph("OPERATION MANUAL", f2_bold));
            }

            document.close();
            wr.close();
            ou.close();
            String base64 = new String(encodeBase64(readFileToByteArray(pdf)));
//            pdf.delete();
            return base64;
        }
        } catch (IOException | DocumentException ex) {
            insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
        }
        return null;
    }

    /**
     *
     * @param path
     * @param siq
     * @param datereport
     * @return
     */
    public static String FROMBRANCH_CHANGE_xls(String path, FromBranchingSheet_value siq, String datereport) {
        try {
            String intestazionePdf = "From Branching Sheet";
            int cntriga = 12;
            File pdf = new File(path + generaId(50) + "FromBranchingSheet.xlsx");
            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet("FromBranchingSheet");
            //CREAZIONE FONT
            XSSFFont font = workbook.createFont();
            font.setFontName(FONT_ARIAL);
            font.setFontHeightInPoints((short) 16);
            font.setBold(true);

            XSSFCellStyle style1 = workbook.createCellStyle();
            style1.setFont(font);

            XSSFFont font2 = workbook.createFont();
            font2.setFontName(FONT_ARIAL);
            font2.setFontHeightInPoints((short) 12);
            font2.setBold(true);

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

            XSSFCellStyle style3center = workbook.createCellStyle();
            style3center.setFont(font3);
            style3center.setWrapText(true);
            style3center.setAlignment(CENTER);
            style3center.setBorderTop(THIN);
            style3center.setBorderBottom(THIN);

            XSSFCellStyle style3left = workbook.createCellStyle();
            style3left.setFont(font3);
            style3left.setAlignment(LEFT);

            style3left.setBorderTop(THIN);
            style3left.setBorderBottom(THIN);

            XSSFFont font4 = workbook.createFont();
            font4.setFontName(FONT_ARIAL);
            font4.setFontHeightInPoints((short) 10);

            XSSFCellStyle style4 = workbook.createCellStyle();
            style4.setAlignment(RIGHT);
            style4.setBorderTop(THIN);
            style4.setBorderBottom(THIN);

            XSSFCellStyle cellStylenum = workbook.createCellStyle();
            XSSFDataFormat hssfDataFormat = workbook.createDataFormat();
            cellStylenum.setDataFormat(hssfDataFormat.getFormat(formatdataCell));
            cellStylenum.setAlignment(RIGHT);
            cellStylenum.setBorderTop(THIN);
            cellStylenum.setBorderBottom(THIN);

            XSSFCellStyle cellStylenumRATE = workbook.createCellStyle();
            cellStylenumRATE.setDataFormat(hssfDataFormat.getFormat(formatdataCellRate));
            cellStylenumRATE.setAlignment(RIGHT);
            cellStylenumRATE.setBorderTop(THIN);
            cellStylenumRATE.setBorderBottom(THIN);

            XSSFCellStyle style3num = workbook.createCellStyle();
            style3num.setFont(font3);
            style3num.setAlignment(RIGHT);
            style3num.setBorderTop(THIN);
            style3num.setBorderBottom(THIN);
            style3num.setDataFormat(hssfDataFormat.getFormat(formatdataCell));

            XSSFCellStyle style4left = workbook.createCellStyle();
            style4left.setAlignment(LEFT);
            style4left.setBorderTop(THIN);
            style4left.setBorderBottom(THIN);

            Row rowP = sheet.createRow((short) 1);

            Cell cl = rowP.createCell(1);
            cl.setCellStyle(style1);
            cl.setCellValue(intestazionePdf);

            Cell cl32 = rowP.createCell(3);
            cl32.setCellStyle(style2);
            cl32.setCellValue("Transfer " + siq.getNumTransfer() + " " + datereport);

            Row rowP4 = sheet.createRow((short) 5);

            Cell cl4 = rowP4.createCell(1);
            cl4.setCellStyle(style2);
            cl4.setCellValue("From : ");

            Cell cl42 = rowP4.createCell(2);
            cl42.setCellStyle(style2);
            cl42.setCellValue(siq.getTobranch());

            Row rowP5 = sheet.createRow((short) 6);

            Cell cl55 = rowP5.createCell(1);
            cl55.setCellStyle(style2);
            cl55.setCellValue("To: ");

            Cell cl552 = rowP5.createCell(2);
            cl552.setCellStyle(style2);
            cl552.setCellValue(siq.getFromsafe());

            Row rowP7 = sheet.createRow((short) 7);

            Cell cl7 = rowP7.createCell(1);
            cl7.setCellStyle(style2);
            cl7.setCellValue("User: ");

            Cell cl72 = rowP7.createCell(2);
            cl72.setCellStyle(style2);
            cl72.setCellValue(siq.getPinuser());

            Row row = sheet.createRow((short) 2);
            Cell c1l7 = row.createCell(1);
            c1l7.setCellStyle(style2);
            c1l7.setCellValue(siq.getId_filiale() + " - " + siq.getDe_filiale());
            Row row66 = sheet.createRow((short) 10);

            if (siq.getDati().size() > 0) {

                //mi scandisco le colonne
                for (int j = 0; j < colonne_change.size(); j++) {
                    Cell cl5 = row66.createCell(j + 1);
                    cl5.setCellStyle(style3);
                    if (j == 0) {
                        cl5.setCellStyle(style3left);
                    }
                    cl5.setCellValue(colonne_change.get(j));
                }

                Row row77 = sheet.createRow((short) 8);
                row77.setHeight((short) 555);
                for (int j = 0; j < 6; j++) {

                    if (j == 1) {

                        Cell cl5 = row77.createCell(j + 1);
                        cl5.setCellStyle(style3center);
                        cl5.setCellValue("Notes");

                    } else if (j == 2) {
                        Cell cl5 = row77.createCell(j + 2);
                        cl5.setCellStyle(style3center);
                        cl5.setCellValue("Euro TC / Travel Cheques");
                    }
                }

                CellRangeAddress cellRangeAddress = new CellRangeAddress(8, 8, 2, 3);
                sheet.addMergedRegion(cellRangeAddress);

                cellRangeAddress = new CellRangeAddress(8, 8, 4, 5);
                sheet.addMergedRegion(cellRangeAddress);

                ArrayList<FromBranchingSheet_value> dati = siq.getDati();

                double branchtotal = 0;

                for (int i = 0; i < dati.size(); i++) {

                    cntriga++;
                    Row row6 = sheet.createRow((short) cntriga);

                    FromBranchingSheet_value temp = (FromBranchingSheet_value) dati.get(i);

                    ArrayList<String> datitemp = temp.getDati_string();

                    Cell f1 = row6.createCell(1);
                    f1.setCellStyle(style4left);
                    f1.setCellValue(temp.getCurrency());
                    for (int n = 1; n < colonne_change.size(); n++) {

                        if (colonne_change.get(n).equals("Branch Total")) {
                            branchtotal += fd((datitemp.get(n)));
                        }

                        Cell f2 = row6.createCell(n + 1);
                        if (colonne_change.get(n).equals("Branch Rate")) {
                            f2.setCellStyle(cellStylenumRATE);
                        } else {
                            f2.setCellStyle(cellStylenum);
                        }
                        f2.setCellValue(fd(datitemp.get(n)));
                    }

                }

                cntriga++;
                cntriga++;

                Row row9 = sheet.createRow((short) cntriga);

                for (int n = 1; n < colonne_change.size(); n++) {

                    if (n == 1) {
                        Cell f7 = row9.createCell(n + 1);
                        f7.setCellStyle(style3);
                        f7.setCellValue("Total");
                    } else if (colonne_change.get(n).equals("Branch Total")) {
                        Cell f7 = row9.createCell(n + 1);
                        f7.setCellStyle(style3num);
                        f7.setCellValue(fd(roundDoubleandFormat(branchtotal, 2)));
                    }

                }

                cntriga++;
                cntriga++;
                cntriga++;
                cntriga++;

            }

            cntriga++;
            cntriga++;

            Row row10 = sheet.createRow((short) cntriga);
            Cell f7 = row10.createCell(1);
            f7.setCellStyle(style3);
            f7.setCellValue("Notes:");
            Cell f71 = row10.createCell(2);
            f71.setCellStyle(style3left);
            f71.setCellValue(siq.getNote());

            if (siq.getAuto().equals("M")) {
                cntriga++;
                cntriga++;
                Row row12 = sheet.createRow((short) cntriga);
                Cell f15 = row12.createCell(1);
                f15.setCellStyle(style3left);
                f15.setCellValue("OPERATION MANUAL");
            }

//            
            for (int c = 0; c < 20; c++) {
                sheet.autoSizeColumn(c, true);
            }

            try (FileOutputStream out = new FileOutputStream(pdf)) {
                workbook.write(out);
            }
            String base64 = new String(encodeBase64(readFileToByteArray(pdf)));
            pdf.delete();
            return base64;
        } catch (IOException ex) {
            insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
        }
        return null;
    }
    //FROM BRANCH CHANGE

    //TO BANK
    /**
     *
     * @param path
     * @param siq
     * @param datereport
     * @return
     */
    public static String TOBANK_xls(String path, ToBankingSheet_value siq, String datereport) {
        try {
            String intestazionePdf = "To Banking Sheet";
            File xls = new File(path + generaId(50) + "_ToBankingSheet.xlsx");
            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet("ToBankingSheet");
            //CREAZIONE FONT
            XSSFFont font = workbook.createFont();
            font.setFontName(FONT_ARIAL);
            font.setFontHeightInPoints((short) 16);
            font.setBold(true);

            XSSFCellStyle style1 = workbook.createCellStyle();
            style1.setFont(font);

            XSSFFont font2 = workbook.createFont();
            font2.setFontName(FONT_ARIAL);
            font2.setFontHeightInPoints((short) 12);
            font2.setBold(true);

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

            XSSFCellStyle style3center = workbook.createCellStyle();
            style3center.setFont(font3);
            style3center.setWrapText(true);
            style3center.setAlignment(CENTER);
            style3center.setBorderTop(THIN);
            style3center.setBorderBottom(THIN);

            XSSFCellStyle style3left = workbook.createCellStyle();
            style3left.setFont(font3);
            style3left.setAlignment(LEFT);
            style3left.setBorderTop(THIN);
            style3left.setBorderBottom(THIN);

            XSSFFont font4 = workbook.createFont();
            font4.setFontName(FONT_ARIAL);
            font4.setFontHeightInPoints((short) 10);

            XSSFCellStyle style4 = workbook.createCellStyle();
            style4.setAlignment(RIGHT);
            style4.setBorderTop(THIN);
            style4.setBorderBottom(THIN);

            XSSFCellStyle cellStylenum = workbook.createCellStyle();
            XSSFDataFormat hssfDataFormat = workbook.createDataFormat();
            cellStylenum.setDataFormat(hssfDataFormat.getFormat(formatdataCell));
            cellStylenum.setAlignment(RIGHT);
            cellStylenum.setBorderTop(THIN);
            cellStylenum.setBorderBottom(THIN);

            XSSFCellStyle cellStylenumRATE = workbook.createCellStyle();
            cellStylenumRATE.setDataFormat(hssfDataFormat.getFormat(formatdataCellRate));
            cellStylenumRATE.setAlignment(RIGHT);
            cellStylenumRATE.setBorderTop(THIN);
            cellStylenumRATE.setBorderBottom(THIN);

            XSSFCellStyle style3num = workbook.createCellStyle();
            style3num.setFont(font3);
            style3num.setAlignment(RIGHT);
            style3num.setBorderTop(THIN);
            style3num.setBorderBottom(THIN);
            style3num.setDataFormat(hssfDataFormat.getFormat(formatdataCell));

            XSSFCellStyle style4left = workbook.createCellStyle();
            style4left.setAlignment(LEFT);
            style4left.setBorderTop(THIN);
            style4left.setBorderBottom(THIN);

            Row rowP = sheet.createRow((short) 1);

            Cell cl = rowP.createCell(1);
            cl.setCellStyle(style1);
            cl.setCellValue(intestazionePdf);

            Cell cl32 = rowP.createCell(3);
            cl32.setCellStyle(style2);
            cl32.setCellValue("Transfer " + siq.getNumTransfer() + " " + datereport);

            Row rowP4 = sheet.createRow((short) 5);

            Cell cl4 = rowP4.createCell(1);
            cl4.setCellStyle(style2);
            cl4.setCellValue("From : ");

            Cell cl42 = rowP4.createCell(2);
            cl42.setCellStyle(style2);
            cl42.setCellValue(siq.getFromsafe());

            Row rowP5 = sheet.createRow((short) 6);

            Cell cl55 = rowP5.createCell(1);
            cl55.setCellStyle(style2);
            cl55.setCellValue("To: ");

            Cell cl552 = rowP5.createCell(2);
            cl552.setCellStyle(style2);
            cl552.setCellValue(siq.getTobank());

            Row rowP7 = sheet.createRow((short) 7);

            Cell cl7 = rowP7.createCell(1);
            cl7.setCellStyle(style2);
            cl7.setCellValue("User: ");

            Cell cl72 = rowP7.createCell(2);
            cl72.setCellStyle(style2);
            cl72.setCellValue(siq.getPinuser());

            Row row = sheet.createRow((short) 2);
            Cell c1l7 = row.createCell(1);
            c1l7.setCellStyle(style2);
            c1l7.setCellValue(siq.getId_filiale() + " - " + siq.getDe_filiale());

            Row row66 = sheet.createRow((short) 10);

            //mi scandisco le colonne
            for (int j = 0; j < colonne_change_sp.size(); j++) {
                Cell cl5 = row66.createCell(j + 1);
                cl5.setCellStyle(style3);
                if (j == 0) {
                    cl5.setCellStyle(style3left);
                }
                cl5.setCellValue(colonne_change_sp.get(j));

            }

            Row row77 = sheet.createRow((short) 8);
            row77.setHeight((short) 555);
            for (int j = 0; j < 6; j++) {

                if (j == 1) {
                    Cell cl5 = row77.createCell(j + 1);
                    cl5.setCellStyle(style3center);
                    cl5.setCellValue("Notes");
                } else if (j == 2) {
                    Cell cl5 = row77.createCell(j + 2);
                    cl5.setCellStyle(style3center);
                    cl5.setCellValue("Euro TC / Travel Cheques");
                }
            }

            CellRangeAddress cellRangeAddress = new CellRangeAddress(8, 8, 2, 3);
            sheet.addMergedRegion(cellRangeAddress);

            cellRangeAddress = new CellRangeAddress(8, 8, 4, 5);
            sheet.addMergedRegion(cellRangeAddress);

            int cntriga = 12;

            //  document.add(table2);
            //  document.add(sep);
            ArrayList<ToBankingSheet_value> dati = siq.getDati();

            double branchtotal = 0, spreadtotal = 0;

            for (int i = 0; i < dati.size(); i++) {
                cntriga++;
                Row row6 = sheet.createRow((short) cntriga);

                ToBankingSheet_value temp = (ToBankingSheet_value) dati.get(i);

                ArrayList<String> datitemp = temp.getDati_string();

                Cell f1 = row6.createCell(1);
                f1.setCellStyle(style4left);
                f1.setCellValue(temp.getCurrency());

                boolean perc = false;

                for (int n = 1; n < colonne_change_sp.size(); n++) {

                    if (colonne_change_sp.get(n).equals("Bank Total")) {
                        branchtotal += fd((datitemp.get(n)));
                    } else if (colonne_change_sp.get(n).equals("Spread")) {
                        spreadtotal += fd((datitemp.get(n)));
                    }

                    Cell f2 = row6.createCell(n + 1);

                    if (colonne_change_sp.get(n).contains("Rate")) {
                        f2.setCellStyle(cellStylenumRATE);
                    } else {
                        f2.setCellStyle(cellStylenum);
                    }

                    f2.setCellValue(fd(datitemp.get(n)));

                }

            }

            cntriga++;
            cntriga++;

            Row row9 = sheet.createRow((short) cntriga);

            for (int n = 1; n < colonne_change_sp.size(); n++) {

                if (n == 1) {
                    Cell f7 = row9.createCell(n + 1);
                    f7.setCellStyle(style3);
                    f7.setCellValue("Total");
                } else if (colonne_change_sp.get(n).equals("Bank Total")) {

                    Cell f7 = row9.createCell(n + 1);
                    f7.setCellStyle(style3num);
                    f7.setCellValue(fd(roundDoubleandFormat(branchtotal, 2)));

                } else if (colonne_change_sp.get(n).equals("Spread")) {

                    Cell f7 = row9.createCell(n + 1);
                    f7.setCellStyle(style3num);
                    f7.setCellValue(fd(roundDoubleandFormat(spreadtotal, 2)));
                } else if (colonne_change_sp.get(n).equals("%")) {
                    Cell f7 = row9.createCell(n + 1);
                    f7.setCellStyle(style3num);
                    double toperc = 0.0;
                    if (branchtotal != 0) {
                        toperc = (spreadtotal / branchtotal) * 100.00;
                    }
                    f7.setCellValue(fd(roundDoubleandFormat(toperc, 2)));
                }

            }

            cntriga++;
            cntriga++;
            Row row10 = sheet.createRow((short) cntriga);
            Cell f7 = row10.createCell(1);
            f7.setCellStyle(style3);
            f7.setCellValue("Notes:");
            Cell f71 = row10.createCell(2);
            f71.setCellStyle(style3left);
            f71.setCellValue(siq.getNote());

            if (siq.getAuto().equals("M")) {
                cntriga++;
                cntriga++;
                Row row12 = sheet.createRow((short) cntriga);
                Cell f15 = row12.createCell(1);
                f15.setCellStyle(style3left);
                f15.setCellValue("OPERATION MANUAL");
            }

            for (int c = 0; c < 11; c++) {
                sheet.autoSizeColumn(c, true);
            }

            try (FileOutputStream out = new FileOutputStream(xls)) {
                workbook.write(out);
            }
            String base64 = new String(encodeBase64(readFileToByteArray(xls)));
            xls.delete();
            return base64;

        } catch (IOException ex) {
            insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
        }
        return null;
    }

    /**
     *
     * @param path
     * @param siq
     * @param datereport
     * @return
     */
    public static String TOBANK_pdf(String path, ToBankingSheet_value siq, String datereport) {

        float[] columnWidths2 = new float[colonne_change_sp.size()];

        for (int c = 0; c < colonne_change_sp.size(); c++) {
            columnWidths2[c] = 10f;
        }
        String intestazionePdf = "To Banking Sheet";
        Phrase vuoto = new Phrase("\n");
        String br = "\n";
        Phrase vuoto1 = new Phrase(br);
        PdfPCell cellavuota = new PdfPCell(vuoto1);
        cellavuota.setBorder(NO_BORDER);

        try {

            File pdf = new File(path + generaId(50) + "_ToBankingSheet.pdf");
            Document document = new Document(A4, 20, 20, 20, 20);
            try(OutputStream ou = new FileOutputStream(pdf)){
            PdfWriter wr = getInstance(document, ou);
            document.open();

            PdfPTable table = new PdfPTable(2);
            table.setWidths(columnWidths0);
            table.setWidthPercentage(100);

            Phrase phrase1 = new Phrase();
            phrase1.add(new Chunk(intestazionePdf + " ", f0_bold));
            PdfPCell cell1 = new PdfPCell(phrase1);
            cell1.setBorder(NO_BORDER);

            Paragraph pa1 = new Paragraph(new Phrase("Transfer " + siq.getNumTransfer() + " " + datereport, f3_bold));
            pa1.setAlignment(ALIGN_CENTER);
            PdfPCell cell2 = new PdfPCell(pa1);
            cell2.setBorder(NO_BORDER);
            cell2.setHorizontalAlignment(ALIGN_RIGHT);

            Phrase phrase3 = new Phrase();
            phrase3.add(new Chunk(siq.getId_filiale() + " - " + siq.getDe_filiale(), f1_bold));
            PdfPCell cell3 = new PdfPCell(phrase3);
            cell3.setBorder(NO_BORDER);

            Phrase phrase4 = new Phrase();
            phrase4.add(new Chunk("\n From", f2_bold));
            phrase4.setTabSettings(new TabSettings(30f));
            phrase4.add(TABBING);
            phrase4.add(new Chunk(": " + siq.getFromsafe() + "\n To", f2_bold));
            phrase4.setTabSettings(new TabSettings(30f));
            phrase4.add(TABBING);
            phrase4.add(new Chunk(": " + siq.getTobank() + "\n User", f2_bold));
            phrase4.setTabSettings(new TabSettings(30f));
            phrase4.add(TABBING);
            phrase4.add(new Chunk(": " + siq.getPinuser(), f2_bold));

            PdfPCell cell4 = new PdfPCell(phrase4);

            cell4.setBorder(NO_BORDER);

            table.addCell(cell1);
            table.addCell(cell2);
            table.addCell(cell3);
            table.addCell(cellavuota);
            table.addCell(cell4);
            table.addCell(cellavuota);
            document.add(table);

            vuoto.setFont(f3_normal);
            document.add(vuoto);

            PdfPTable table2 = new PdfPTable(colonne_change_sp.size());
            table2.setWidths(columnWidths2);
            table2.setWidthPercentage(100);

            PdfPCell[] list = new PdfPCell[colonne_change_sp.size()];
            for (int j = 0; j < colonne_change_sp.size(); j++) {
                Phrase phraset1 = new Phrase();
                phraset1.add(new Chunk(colonne_change_sp.get(j), f5_bold));
                PdfPCell cellt1 = new PdfPCell(phraset1);
                cellt1.setHorizontalAlignment(ALIGN_RIGHT);
                cellt1.setBorder(TOP | BOTTOM);
                cellt1.setBackgroundColor(LIGHT_GRAY);
                cellt1.setBorderWidth(0.7f);
                if (j == 0) {
                    cellt1.setHorizontalAlignment(ALIGN_LEFT);
                }
                list[j] = cellt1;
            }

            PdfPTable table6 = new PdfPTable(columnWidths3);
            table6.setWidthPercentage(100);

            for (int j = 0; j < 6; j++) {

                if (j == 1) {
                    Phrase phraset1 = new Phrase();
                    phraset1.add(new Chunk("Notes", f5_bold));
                    PdfPCell cellt1 = new PdfPCell(phraset1);
                    cellt1.setHorizontalAlignment(ALIGN_CENTER);
                    cellt1.setBorder(TOP | BOTTOM);
                    cellt1.setBackgroundColor(LIGHT_GRAY);
                    cellt1.setBorderWidth(0.7f);
                    table6.addCell(cellt1);
                } else if (j == 2) {
                    Phrase phraset1 = new Phrase();
                    phraset1.add(new Chunk("Euro TC / Travel Cheques", f5_bold));
                    PdfPCell cellt1 = new PdfPCell(phraset1);
                    cellt1.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt1.setBorder(TOP | BOTTOM);
                    cellt1.setBackgroundColor(LIGHT_GRAY);
                    cellt1.setBorderWidth(0.7f);
                    table6.addCell(cellt1);
                } else {
                    Phrase phraset1 = new Phrase();
                    phraset1.add(new Chunk("", f5_bold));
                    PdfPCell cellt1 = new PdfPCell(phraset1);
                    cellt1.setHorizontalAlignment(ALIGN_LEFT);
                    cellt1.setBorder(TOP | BOTTOM);
                    cellt1.setBackgroundColor(LIGHT_GRAY);
                    cellt1.setBorderWidth(0.7f);
                    table6.addCell(cellt1);
                }
            }

            document.add(table6);

            PdfPTable table3 = new PdfPTable(colonne_change_sp.size());
            table3.setWidths(columnWidths2);
            table3.setWidthPercentage(100);

            for (PdfPCell list1 : list) {
                PdfPCell temp = (PdfPCell) (list1);
                table3.addCell(temp);
            }

            document.add(table3);

            LineSeparator sep = new LineSeparator();
            sep.setOffset(-2);
            sep.setLineWidth((float) 0.5);

            //  document.add(table2);
            //  document.add(sep);
            ArrayList<ToBankingSheet_value> dati = siq.getDati();

            PdfPCell cell;
            Phrase phraset;
            PdfPCell cellt;

            table3 = new PdfPTable(colonne_change_sp.size());
            table3.setWidths(columnWidths2);
            table3.setWidthPercentage(100);
            double branchtotal = 0, spreadtotal = 0;
            double num = 0.0;
            for (int i = 0; i < dati.size(); i++) {
                ToBankingSheet_value temp = (ToBankingSheet_value) dati.get(i);

                ArrayList<String> datitemp = temp.getDati_string();

                phraset = new Phrase();
                phraset.add(new Chunk(temp.getCurrency(), f2_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_LEFT);
                cellt.setBorder(BOTTOM);
                table3.addCell(cellt);
                boolean perc = false;

                for (int n = 1; n < colonne_change_sp.size(); n++) {

                    switch (colonne_change_sp.get(n)) {
                        case "Bank Total":
                            branchtotal += fd(datitemp.get(n));
                            break;
                        case "Spread":
                            spreadtotal += fd(datitemp.get(n));
                            break;
                        case "%":
                            perc = true;
                            num = num + 1.0;
                            break;
                        default:
                            break;
                    }

                    phraset = new Phrase();
                    if (!perc) {
                        phraset.add(new Chunk(formatMysqltoDisplay(datitemp.get(n)), f2_normal));
                    } else {
                        phraset.add(new Chunk(formatMysqltoDisplay(datitemp.get(n)) + " %", f2_normal));
                    }
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setBorder(BOTTOM);
                    table3.addCell(cellt);

                }

            }

            document.add(table3);

            LineSeparator ls = new LineSeparator();
            ls.setLineWidth(0.7f);
            ls.setOffset(-1f);
            document.add(ls);

            table3 = new PdfPTable(colonne_change_sp.size());
            table3.setWidths(columnWidths2);
            table3.setWidthPercentage(100);

            phraset = new Phrase();
            phraset.add(new Chunk("", f1_normal));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_LEFT);
            cellt.setBorder(BOTTOM);
            table3.addCell(cellt);

            for (int n = 1; n < colonne_change_sp.size(); n++) {

                if (n == 1) {
                    phraset = new Phrase();
                    phraset.add(new Chunk("Total", f4_bold));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_LEFT);
                    cellt.setBorder(BOTTOM);
                    table3.addCell(cellt);
                } else if (colonne_change_sp.get(n).equals("Bank Total")) {
                    phraset = new Phrase();
                    phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(branchtotal, 2)), f3_normal));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setBorder(BOTTOM);
                    table3.addCell(cellt);

                } else if (colonne_change_sp.get(n).equals("Spread")) {
                    phraset = new Phrase();
                    phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(spreadtotal, 2)), f3_normal));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setBorder(BOTTOM);
                    table3.addCell(cellt);

                } else if (colonne_change_sp.get(n).equals("%")) {
                    phraset = new Phrase();

                    double toperc = 0.0;
                    if (branchtotal != 0) {
                        toperc = (spreadtotal / branchtotal) * 100.00;
                    }
                    phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(toperc, 2)) + "%", f3_normal));

                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setBorder(BOTTOM);
                    table3.addCell(cellt);

                } else {
                    phraset = new Phrase();
                    phraset.add(new Chunk("", f3_normal));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_LEFT);
                    cellt.setBorder(BOTTOM);
                    table3.addCell(cellt);
                }

            }

            document.add(table3);
            document.add(vuoto1);
            document.add(new Paragraph("Notes:", f2_bold));
            document.add(new Paragraph(siq.getNote(), f3_normal));

            document.close();
            wr.close();
            ou.close();
            String base64 = new String(encodeBase64(readFileToByteArray(pdf)));
//            pdf.delete();
            return base64;
        }
        } catch (IOException | DocumentException ex) {
            insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
        }
        return null;
    }
    //TO BANK

    //to bank POS
    /**
     *
     * @param path
     * @param siq
     * @param datereport
     * @return
     */
    public static String TOBANK_POS_pdf(String path, ToBankingSheet_value siq, String datereport) {

        String intestazionePdf = "To Banking POS/Bank Account Sheet";
        Phrase vuoto = new Phrase("\n");

        Phrase vuoto1 = new Phrase("\n");
        PdfPCell cellavuota = new PdfPCell(vuoto1);
        cellavuota.setBorder(NO_BORDER);

        float[] columnWidths2 = new float[colonne_pos.size()];
        for (int c = 0; c < colonne_pos.size(); c++) {
            columnWidths2[c] = 10f;
        }

        try {

            File pdf = new File(path + generaId(50) + "ToPosBankAccountSheet.pdf");
            Document document = new Document(A4, 20, 20, 20, 20);
            try(OutputStream ou = new FileOutputStream(pdf)){
            PdfWriter wr = getInstance(document, ou);
            document.open();

            PdfPTable table = new PdfPTable(2);
            table.setWidths(columnWidths0);
            table.setWidthPercentage(100);

            Phrase phrase1 = new Phrase();
            phrase1.add(new Chunk(intestazionePdf + " ", f0_bold));
            PdfPCell cell1 = new PdfPCell(phrase1);
            cell1.setBorder(NO_BORDER);

            Paragraph pa1 = new Paragraph(new Phrase("Transfer " + siq.getNumTransfer() + " " + datereport, f3_bold));
            pa1.setAlignment(ALIGN_CENTER);
            PdfPCell cell2 = new PdfPCell(pa1);
            cell2.setBorder(NO_BORDER);
            cell2.setHorizontalAlignment(ALIGN_RIGHT);

            Phrase phrase3 = new Phrase();
            phrase3.add(new Chunk(siq.getId_filiale() + " - " + siq.getDe_filiale(), f1_bold));
            PdfPCell cell3 = new PdfPCell(phrase3);
            cell3.setBorder(NO_BORDER);

            Phrase phrase4 = new Phrase();
            phrase4.add(new Chunk("\n From", f2_bold));
            phrase4.setTabSettings(new TabSettings(30f));
            phrase4.add(TABBING);
            phrase4.add(new Chunk(": " + siq.getFromsafe() + "\n To", f2_bold));
            phrase4.setTabSettings(new TabSettings(30f));
            phrase4.add(TABBING);
            phrase4.add(new Chunk(": " + siq.getTobank() + "\n User", f2_bold));
            phrase4.setTabSettings(new TabSettings(30f));
            phrase4.add(TABBING);
            phrase4.add(new Chunk(": " + siq.getPinuser(), f2_bold));

            PdfPCell cell4 = new PdfPCell(phrase4);

            cell4.setBorder(NO_BORDER);

            table.addCell(cell1);
            table.addCell(cell2);
            table.addCell(cell3);
            table.addCell(cellavuota);
            table.addCell(cell4);
            table.addCell(cellavuota);
            document.add(table);

            vuoto.setFont(f3_normal);
            document.add(vuoto);

            PdfPTable table2 = new PdfPTable(colonne_pos.size());
            table2.setWidths(columnWidths2);
            table2.setWidthPercentage(100);

            PdfPCell[] list = new PdfPCell[colonne_pos.size()];
            //mi scandisco le colonne
            for (int j = 0; j < colonne_pos.size(); j++) {
                Phrase phraset1 = new Phrase();
                phraset1.add(new Chunk(colonne_pos.get(j), f5_bold));
                PdfPCell cellt1 = new PdfPCell(phraset1);
                cellt1.setHorizontalAlignment(ALIGN_RIGHT);
                cellt1.setBorder(BOTTOM);

                cellt1.setBackgroundColor(LIGHT_GRAY);
                //cellt1.setBorder(Rectangle.BOTTOM);
                cellt1.setBorderWidth(0.7f);
                if (j == 0) {
                    cellt1.setHorizontalAlignment(ALIGN_LEFT);
                }
                list[j] = cellt1;

            }

            PdfPTable table6 = new PdfPTable(columnWidths4);
            table6.setWidthPercentage(100);

            for (int j = 0; j < 6; j++) {

                if (j == 1) {
                    Phrase phraset1 = new Phrase();
                    phraset1.add(new Chunk("04 – Cash Advance", f5_bold));
                    PdfPCell cellt1 = new PdfPCell(phraset1);
                    cellt1.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt1.setBorder(TOP);
                    cellt1.setBackgroundColor(LIGHT_GRAY);
                    //cellt1.setBorder(Rectangle.BOTTOM);
                    cellt1.setBorderWidth(0.7f);
                    table6.addCell(cellt1);
                } else if (j == 2) {
                    Phrase phraset1 = new Phrase();
                    phraset1.add(new Chunk("06 – Credit Card COP", f5_bold));
                    PdfPCell cellt1 = new PdfPCell(phraset1);
                    cellt1.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt1.setBorder(TOP);
                    cellt1.setBackgroundColor(LIGHT_GRAY);
                    //cellt1.setBorder(Rectangle.BOTTOM);
                    cellt1.setBorderWidth(0.7f);
                    table6.addCell(cellt1);
                } else if (j == 3) {
                    Phrase phraset1 = new Phrase();
                    phraset1.add(new Chunk("07 – Bancomat COP", f5_bold));
                    PdfPCell cellt1 = new PdfPCell(phraset1);
                    cellt1.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt1.setBorder(TOP);
                    cellt1.setBackgroundColor(LIGHT_GRAY);
                    //cellt1.setBorder(Rectangle.BOTTOM);
                    cellt1.setBorderWidth(0.7f);
                    table6.addCell(cellt1);
                } else if (j == 4) {
                    Phrase phraset1 = new Phrase();
                    phraset1.add(new Chunk("08 –  Bank Account", f5_bold));
                    PdfPCell cellt1 = new PdfPCell(phraset1);
                    cellt1.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt1.setBorder(TOP);
                    cellt1.setBackgroundColor(LIGHT_GRAY);
                    //cellt1.setBorder(Rectangle.BOTTOM);
                    cellt1.setBorderWidth(0.7f);
                    table6.addCell(cellt1);
                } else {
                    Phrase phraset1 = new Phrase();
                    phraset1.add(new Chunk("", f5_bold));
                    PdfPCell cellt1 = new PdfPCell(phraset1);
                    cellt1.setHorizontalAlignment(ALIGN_LEFT);
                    cellt1.setBorder(TOP);

                    cellt1.setBackgroundColor(LIGHT_GRAY);
                    //cellt1.setBorder(Rectangle.BOTTOM);
                    cellt1.setBorderWidth(0.7f);
                    table6.addCell(cellt1);
                }
            }

            document.add(table6);

            PdfPTable table3 = new PdfPTable(colonne_pos.size());
            table3.setWidths(columnWidths2);
            table3.setWidthPercentage(100);

            for (PdfPCell list1 : list) {
                PdfPCell temp = (PdfPCell) (list1);
                table3.addCell(temp);
            }

            document.add(table3);

            LineSeparator sep = new LineSeparator();
            sep.setOffset(-2);
            sep.setLineWidth((float) 0.5);

            ArrayList<ToBankingSheet_value> dati = siq.getDati();

            Phrase phraset;
            PdfPCell cellt;

            table3 = new PdfPTable(colonne_pos.size());
            table3.setWidths(columnWidths2);
            table3.setWidthPercentage(100);

            double branchtotal = 0;
            double num = 0.0;
            for (int i = 0; i < dati.size(); i++) {
                ToBankingSheet_value temp = (ToBankingSheet_value) dati.get(i);
                ArrayList<String> datitemp = temp.getDati_string();

                phraset = new Phrase();
                phraset.add(new Chunk(temp.getCurrency(), f2_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_LEFT);
                cellt.setBorder(BOTTOM);
                table3.addCell(cellt);
                for (int n = 1; n < colonne_pos.size(); n++) {
                    if (colonne_pos.get(n).equals("Bank Total")) {
                        branchtotal += fd(datitemp.get(n));
                    }
                    phraset = new Phrase();
                    phraset.add(new Chunk(formatMysqltoDisplay(datitemp.get(n)), f2_normal));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setBorder(BOTTOM);
                    table3.addCell(cellt);
                }
            }

            document.add(table3);

            LineSeparator ls = new LineSeparator();
            ls.setLineWidth(0.7f);
            ls.setOffset(-1f);
            document.add(ls);

            table3 = new PdfPTable(colonne_pos.size());
            table3.setWidths(columnWidths2);
            table3.setWidthPercentage(100);

            phraset = new Phrase();
            phraset.add(new Chunk("", f1_normal));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_LEFT);
            cellt.setBorder(BOTTOM);
            table3.addCell(cellt);

            for (int n = 1; n < colonne_pos.size(); n++) {

                if (n == 1) {
                    phraset = new Phrase();
                    phraset.add(new Chunk("Total", f4_bold));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_LEFT);
                    cellt.setBorder(BOTTOM);
                    table3.addCell(cellt);
                } else if (colonne_pos.get(n).equals("Bank Total")) {
                    phraset = new Phrase();
                    phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(branchtotal, 2)), f3_normal));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setBorder(BOTTOM);
                    table3.addCell(cellt);

                } else {
                    phraset = new Phrase();
                    phraset.add(new Chunk("", f3_normal));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_LEFT);
                    cellt.setBorder(BOTTOM);
                    table3.addCell(cellt);
                }

            }

            document.add(table3);

//            
            document.close();
            wr.close();
            ou.close();
            String base64 = new String(encodeBase64(readFileToByteArray(pdf)));
            pdf.delete();
            return base64;
        }
        } catch (IOException | DocumentException ex) {
            insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
        }

        return null;
    }

    /**
     *
     * @param path
     * @param siq
     * @param datereport
     * @return
     */
    public static String TOBANK_POS_xls(String path, ToBankingSheet_value siq, String datereport) {
        try {
            File xls = new File(path + generaId(50) + "_ToPosBankAccountSheet.xlsx");
            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet("ToPosBankAccountSheet");
            //CREAZIONE FONT
            XSSFFont font = workbook.createFont();
            font.setFontName(FONT_ARIAL);
            font.setFontHeightInPoints((short) 16);
            font.setBold(true);

            XSSFCellStyle style1 = (XSSFCellStyle) workbook.createCellStyle();
            style1.setFont(font);

            XSSFFont font2 = workbook.createFont();
            font2.setFontName(FONT_ARIAL);
            font2.setFontHeightInPoints((short) 12);
            font2.setBold(true);

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

            XSSFCellStyle style3center = (XSSFCellStyle) workbook.createCellStyle();
            style3center.setFont(font3);
            style3center.setWrapText(true);
            style3center.setAlignment(CENTER);

            style3center.setBorderTop(THIN);
            style3center.setBorderBottom(THIN);

            XSSFCellStyle style3left = (XSSFCellStyle) workbook.createCellStyle();
            style3left.setFont(font3);
            style3left.setAlignment(LEFT);

            style3left.setBorderTop(THIN);
            style3left.setBorderBottom(THIN);

            XSSFFont font4 = workbook.createFont();
            font4.setFontName(FONT_ARIAL);
            font4.setFontHeightInPoints((short) 10);

            XSSFCellStyle style4 = (XSSFCellStyle) workbook.createCellStyle();
            style4.setAlignment(RIGHT);
            style4.setBorderTop(THIN);
            style4.setBorderBottom(THIN);

            XSSFCellStyle cellStylenum = (XSSFCellStyle) workbook.createCellStyle();
            XSSFDataFormat hssfDataFormat = (XSSFDataFormat) workbook.createDataFormat();
            cellStylenum.setDataFormat(hssfDataFormat.getFormat(formatdataCell));
            cellStylenum.setAlignment(RIGHT);
            cellStylenum.setBorderTop(THIN);
            cellStylenum.setBorderBottom(THIN);

            XSSFCellStyle cellStylenumRATE = (XSSFCellStyle) workbook.createCellStyle();
            cellStylenumRATE.setDataFormat(hssfDataFormat.getFormat(formatdataCellRate));
            cellStylenumRATE.setAlignment(RIGHT);
            cellStylenumRATE.setBorderTop(THIN);
            cellStylenumRATE.setBorderBottom(THIN);

            XSSFCellStyle style3num = (XSSFCellStyle) workbook.createCellStyle();
            style3num.setFont(font3);
            style3num.setAlignment(RIGHT);
            style3num.setBorderTop(THIN);
            style3num.setBorderBottom(THIN);
            style3num.setDataFormat(hssfDataFormat.getFormat(formatdataCell));

            XSSFCellStyle style4left = (XSSFCellStyle) workbook.createCellStyle();
            style4left.setAlignment(LEFT);
            style4left.setBorderTop(THIN);
            style4left.setBorderBottom(THIN);

            Row rowP = sheet.createRow((short) 1);

            String intestazionePdf = "To Banking POS/Bank Account Sheet";

            Cell cl = rowP.createCell(1);
            cl.setCellStyle(style1);
            cl.setCellValue(intestazionePdf);

            Cell cl32 = rowP.createCell(6);
            cl32.setCellStyle(style2);
            cl32.setCellValue("Transfer " + siq.getNumTransfer() + " " + datereport);

            Row rowP4 = sheet.createRow((short) 5);

            Cell cl4 = rowP4.createCell(1);
            cl4.setCellStyle(style2);
            cl4.setCellValue("From : ");

            Cell cl42 = rowP4.createCell(2);
            cl42.setCellStyle(style2);
            cl42.setCellValue(siq.getFromsafe());

            Row rowP5 = sheet.createRow((short) 6);

            Cell cl55 = rowP5.createCell(1);
            cl55.setCellStyle(style2);
            cl55.setCellValue("To: ");

            Cell cl552 = rowP5.createCell(2);
            cl552.setCellStyle(style2);
            cl552.setCellValue(siq.getTobank());

            Row rowP7 = sheet.createRow((short) 7);

            Cell cl7 = rowP7.createCell(1);
            cl7.setCellStyle(style2);
            cl7.setCellValue("User: ");

            Cell cl72 = rowP7.createCell(2);
            cl72.setCellStyle(style2);
            cl72.setCellValue(siq.getPinuser());

            Row row = sheet.createRow((short) 2);
            Cell c1l7 = row.createCell(1);
            c1l7.setCellStyle(style2);
            c1l7.setCellValue(siq.getId_filiale() + " - " + siq.getDe_filiale());

            Row row66 = sheet.createRow((short) 10);

            //mi scandisco le colonne
            for (int j = 0; j < colonne_pos.size(); j++) {
                Cell cl5 = row66.createCell(j + 1);
                cl5.setCellStyle(style3);
                if (j == 0) {
                    cl5.setCellStyle(style3left);
                }
                cl5.setCellValue(colonne_pos.get(j));

            }

            Row row77 = sheet.createRow((short) 8);
            row77.setHeight((short) 555);
            for (int j = 0; j < 6; j++) {

                if (j == 1) {

                    Cell cl5 = row77.createCell(j + 1);
                    cl5.setCellStyle(style3center);
                    cl5.setCellValue("04 – Cash Advance");

                } else if (j == 2) {
                    Cell cl5 = row77.createCell(j + 1);
                    cl5.setCellStyle(style3center);
                    cl5.setCellValue("06 – Credit Card COP");
                } else if (j == 3) {
                    Cell cl5 = row77.createCell(j + 1);
                    cl5.setCellStyle(style3center);
                    cl5.setCellValue("06 – Credit Card COP");
                } else if (j == 4) {
                    Cell cl5 = row77.createCell(j + 1);
                    cl5.setCellStyle(style3center);
                    cl5.setCellValue("08 – Bank Account");
                } else {

                }
            }
            int cntriga = 12;
            ArrayList<ToBankingSheet_value> dati = siq.getDati();

            double branchtotal = 0;

            for (int i = 0; i < dati.size(); i++) {
                cntriga++;
                Row row6 = sheet.createRow((short) cntriga);

                ToBankingSheet_value temp = dati.get(i);

                ArrayList<String> datitemp = temp.getDati_string();

                Cell f1 = row6.createCell(1);
                f1.setCellStyle(style4left);
                f1.setCellValue(temp.getCurrency());

                for (int n = 1; n < colonne_pos.size(); n++) {
                    if (colonne_pos.get(n).equals("Bank Total")) {
                        branchtotal += fd((datitemp.get(n)));
                    }
                    Cell f2 = row6.createCell(n + 1);
                    f2.setCellStyle(cellStylenum);
                    f2.setCellValue(fd(datitemp.get(n)));

                }

            }

            cntriga++;
            cntriga++;

            Row row9 = sheet.createRow((short) cntriga);

            for (int n = 1; n < colonne_pos.size(); n++) {

                if (n == 1) {
                    Cell f7 = row9.createCell(n + 1);
                    f7.setCellStyle(style3);
                    f7.setCellValue("Total");
                } else if (colonne_pos.get(n).equals("Bank Total")) {

                    Cell f7 = row9.createCell(n + 1);
                    f7.setCellStyle(style3num);
                    f7.setCellValue(fd(roundDoubleandFormat(branchtotal, 2)));

                }

            }

            for (int c = 0; c < 11; c++) {
                sheet.autoSizeColumn(c, true);
            }
            try (FileOutputStream out = new FileOutputStream(xls)) {
                workbook.write(out);
            }
            String base64 = new String(encodeBase64(readFileToByteArray(xls)));
            xls.delete();
            return base64;
        } catch (IOException ex) {
            insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
        }
        return null;
    }
    //to bank POS

    //FROM BANK
    /**
     *
     * @param path
     * @param siq
     * @param datereport
     * @return
     */
    public static String FROMBANK_pdf(String path, ToBankingSheet_value siq, String datereport) {

        float[] columnWidths2 = new float[colonne_change_sp.size()];

        for (int c = 0; c < colonne_change_sp.size(); c++) {
            columnWidths2[c] = 10f;
        }
        String intestazionePdf = "From Banking Sheet";
        Phrase vuoto = new Phrase("\n");
        String br = "\n";
        Phrase vuoto1 = new Phrase(br);
        PdfPCell cellavuota = new PdfPCell(vuoto1);
        cellavuota.setBorder(NO_BORDER);

        try {

            File pdf = new File(path + generaId(50) + "_FromBankingSheet.pdf");
            Document document = new Document(A4, 20, 20, 20, 20);
            try(OutputStream ou = new FileOutputStream(pdf)){
            PdfWriter wr = getInstance(document, ou);
            document.open();

            PdfPTable table = new PdfPTable(2);
            table.setWidths(columnWidths0);
            table.setWidthPercentage(100);

            Phrase phrase1 = new Phrase();
            phrase1.add(new Chunk(intestazionePdf + " ", f0_bold));
            PdfPCell cell1 = new PdfPCell(phrase1);
            cell1.setBorder(NO_BORDER);

            Paragraph pa1 = new Paragraph(new Phrase("Transfer " + siq.getNumTransfer() + " " + datereport, f3_bold));
            pa1.setAlignment(ALIGN_CENTER);
            PdfPCell cell2 = new PdfPCell(pa1);
            cell2.setBorder(NO_BORDER);
            cell2.setHorizontalAlignment(ALIGN_RIGHT);

            Phrase phrase3 = new Phrase();
            phrase3.add(new Chunk(siq.getId_filiale() + " - " + siq.getDe_filiale(), f1_bold));
            PdfPCell cell3 = new PdfPCell(phrase3);
            cell3.setBorder(NO_BORDER);

            Phrase phrase4 = new Phrase();
            phrase4.add(new Chunk("\n From", f2_bold));
            phrase4.setTabSettings(new TabSettings(30f));
            phrase4.add(TABBING);
            phrase4.add(new Chunk(": " + siq.getTobank() + "\n To", f2_bold));
            phrase4.setTabSettings(new TabSettings(30f));
            phrase4.add(TABBING);
            phrase4.add(new Chunk(": " + siq.getFromsafe() + "\n User", f2_bold));
            phrase4.setTabSettings(new TabSettings(30f));
            phrase4.add(TABBING);
            phrase4.add(new Chunk(": " + siq.getPinuser(), f2_bold));

            PdfPCell cell4 = new PdfPCell(phrase4);

            cell4.setBorder(NO_BORDER);

            table.addCell(cell1);
            table.addCell(cell2);
            table.addCell(cell3);
            table.addCell(cellavuota);
            table.addCell(cell4);
            table.addCell(cellavuota);
            document.add(table);

            vuoto.setFont(f3_normal);
            document.add(vuoto);

            PdfPTable table2 = new PdfPTable(colonne_change_sp.size());
            table2.setWidths(columnWidths2);
            table2.setWidthPercentage(100);

            PdfPCell[] list = new PdfPCell[colonne_change_sp.size()];
            for (int j = 0; j < colonne_change_sp.size(); j++) {
                Phrase phraset1 = new Phrase();
                phraset1.add(new Chunk(colonne_change_sp.get(j), f5_bold));
                PdfPCell cellt1 = new PdfPCell(phraset1);
                cellt1.setHorizontalAlignment(ALIGN_RIGHT);
                cellt1.setBorder(TOP | BOTTOM);
                cellt1.setBackgroundColor(LIGHT_GRAY);
                cellt1.setBorderWidth(0.7f);

                if (j == 0) {
                    cellt1.setHorizontalAlignment(ALIGN_LEFT);
                }
                list[j] = cellt1;
            }

            PdfPTable table6 = new PdfPTable(columnWidths3);
            table6.setWidthPercentage(100);

            for (int j = 0; j < 6; j++) {

                if (j == 1) {
                    Phrase phraset1 = new Phrase();
                    phraset1.add(new Chunk("Notes", f5_bold));
                    PdfPCell cellt1 = new PdfPCell(phraset1);
                    cellt1.setHorizontalAlignment(ALIGN_CENTER);
                    cellt1.setBorder(TOP | BOTTOM);
                    cellt1.setBackgroundColor(LIGHT_GRAY);
                    cellt1.setBorderWidth(0.7f);
                    table6.addCell(cellt1);
                } else if (j == 2) {
                    Phrase phraset1 = new Phrase();
                    phraset1.add(new Chunk("Euro TC / Travel Cheques", f5_bold));
                    PdfPCell cellt1 = new PdfPCell(phraset1);
                    cellt1.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt1.setBorder(TOP | BOTTOM);
                    cellt1.setBackgroundColor(LIGHT_GRAY);
                    cellt1.setBorderWidth(0.7f);
                    table6.addCell(cellt1);
                } else {
                    Phrase phraset1 = new Phrase();
                    phraset1.add(new Chunk("", f5_bold));
                    PdfPCell cellt1 = new PdfPCell(phraset1);
                    cellt1.setHorizontalAlignment(ALIGN_LEFT);
                    cellt1.setBorder(TOP | BOTTOM);
                    cellt1.setBackgroundColor(LIGHT_GRAY);
                    cellt1.setBorderWidth(0.7f);
                    table6.addCell(cellt1);
                }
            }

            document.add(table6);

            PdfPTable table3 = new PdfPTable(colonne_change_sp.size());
            table3.setWidths(columnWidths2);
            table3.setWidthPercentage(100);

            for (PdfPCell list1 : list) {
                PdfPCell temp = (PdfPCell) (list1);
                table3.addCell(temp);
            }

            document.add(table3);

            LineSeparator sep = new LineSeparator();
            sep.setOffset(-2);
            sep.setLineWidth((float) 0.5);

            //  document.add(table2);
            //  document.add(sep);
            ArrayList<ToBankingSheet_value> dati = siq.getDati();

            PdfPCell cell;
            Phrase phraset;
            PdfPCell cellt;

            table3 = new PdfPTable(colonne_change_sp.size());
            table3.setWidths(columnWidths2);
            table3.setWidthPercentage(100);
            double branchtotal = 0, spreadtotal = 0;
            double num = 0.0;
            for (int i = 0; i < dati.size(); i++) {
                ToBankingSheet_value temp = (ToBankingSheet_value) dati.get(i);
                ArrayList<String> datitemp = temp.getDati_string();

                phraset = new Phrase();
                phraset.add(new Chunk(temp.getCurrency(), f2_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_LEFT);
                cellt.setBorder(BOTTOM);
                table3.addCell(cellt);
                boolean perc = false;

                for (int n = 1; n < colonne_change_sp.size(); n++) {

                    switch (colonne_change_sp.get(n)) {
                        case "Bank Total":
                            branchtotal += fd(datitemp.get(n));
                            break;
                        case "Spread":
                            spreadtotal += fd((datitemp.get(n)));
                            break;
                        case "%":
                            perc = true;
                            num = num + 1.0;
                            break;
                        default:
                            break;
                    }

                    phraset = new Phrase();
                    if (!perc) {
                        phraset.add(new Chunk(formatMysqltoDisplay(datitemp.get(n)), f2_normal));
                    } else {
                        phraset.add(new Chunk(formatMysqltoDisplay(datitemp.get(n)) + " %", f2_normal));
                    }
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setBorder(BOTTOM);
                    table3.addCell(cellt);

                }

            }

            document.add(table3);

            LineSeparator ls = new LineSeparator();
            ls.setLineWidth(0.7f);
            ls.setOffset(-1f);
            document.add(ls);

            table3 = new PdfPTable(colonne_change_sp.size());
            table3.setWidths(columnWidths2);
            table3.setWidthPercentage(100);

            phraset = new Phrase();
            phraset.add(new Chunk("", f1_normal));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_LEFT);
            cellt.setBorder(BOTTOM);
            table3.addCell(cellt);

            for (int n = 1; n < colonne_change_sp.size(); n++) {

                if (n == 1) {
                    phraset = new Phrase();
                    phraset.add(new Chunk("Total", f3_bold));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_LEFT);
                    cellt.setBorder(BOTTOM);
                    table3.addCell(cellt);
                } else if (colonne_change_sp.get(n).equals("Bank Total")) {
                    phraset = new Phrase();
                    phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(branchtotal, 2)), f3_normal));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setBorder(BOTTOM);
                    table3.addCell(cellt);

                } else if (colonne_change_sp.get(n).equals("Spread")) {
                    phraset = new Phrase();
                    phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(spreadtotal, 2)), f3_normal));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setBorder(BOTTOM);
                    table3.addCell(cellt);

                } else if (colonne_change_sp.get(n).equals("%")) {
                    phraset = new Phrase();

                    double toperc = 0.0;
                    if (branchtotal != 0) {
                        toperc = (spreadtotal / branchtotal) * 100.00;
                    }
                    phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(toperc, 2)) + "%", f3_normal));

                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setBorder(BOTTOM);
                    table3.addCell(cellt);

                } else {
                    phraset = new Phrase();
                    phraset.add(new Chunk("", f3_normal));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_LEFT);
                    cellt.setBorder(BOTTOM);
                    table3.addCell(cellt);
                }

            }

            document.add(table3);
            document.add(vuoto1);
            document.add(new Paragraph("Notes:", f2_bold));
            document.add(new Paragraph(siq.getNote(), f3_normal));

            document.close();
            wr.close();
            ou.close();
            String base64 = new String(encodeBase64(readFileToByteArray(pdf)));
//            pdf.delete();
            return base64;
        }
        } catch (IOException | DocumentException ex) {
            insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
        }
        return null;
    }

    /**
     *
     * @param path
     * @param siq
     * @param datereport
     * @return
     */
    public static String FROMBANK_xls(String path, ToBankingSheet_value siq, String datereport) {
        try {

            String intestazionePdf = "From Banking Sheet";
            File xls = new File(path + generaId(50) + "_FromBankingSheet.xlsx");
            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet("FromBankingSheet");
            XSSFFont font = workbook.createFont();
            font.setFontName(FONT_ARIAL);
            font.setFontHeightInPoints((short) 16);
            font.setBold(true);

            XSSFCellStyle style1 = (XSSFCellStyle) workbook.createCellStyle();
            style1.setFont(font);

            XSSFFont font2 = workbook.createFont();
            font2.setFontName(FONT_ARIAL);
            font2.setFontHeightInPoints((short) 12);
            font2.setBold(true);

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

            XSSFCellStyle style3center = (XSSFCellStyle) workbook.createCellStyle();
            style3center.setFont(font3);
            style3center.setWrapText(true);
            style3center.setAlignment(CENTER);

            style3center.setBorderTop(THIN);
            style3center.setBorderBottom(THIN);

            XSSFCellStyle style3left = (XSSFCellStyle) workbook.createCellStyle();
            style3left.setFont(font3);
            style3left.setAlignment(LEFT);

            style3left.setBorderTop(THIN);
            style3left.setBorderBottom(THIN);

            XSSFFont font4 = workbook.createFont();
            font4.setFontName(FONT_ARIAL);
            font4.setFontHeightInPoints((short) 10);

            XSSFCellStyle style4 = (XSSFCellStyle) workbook.createCellStyle();
            style4.setAlignment(RIGHT);
            style4.setBorderTop(THIN);
            style4.setBorderBottom(THIN);

            XSSFCellStyle cellStylenum = (XSSFCellStyle) workbook.createCellStyle();
            XSSFDataFormat hssfDataFormat = (XSSFDataFormat) workbook.createDataFormat();
            cellStylenum.setDataFormat(hssfDataFormat.getFormat(formatdataCell));
            cellStylenum.setAlignment(RIGHT);
            cellStylenum.setBorderTop(THIN);
            cellStylenum.setBorderBottom(THIN);

            XSSFCellStyle cellStylenumRATE = (XSSFCellStyle) workbook.createCellStyle();
            cellStylenumRATE.setDataFormat(hssfDataFormat.getFormat(formatdataCellRate));
            cellStylenumRATE.setAlignment(RIGHT);
            cellStylenumRATE.setBorderTop(THIN);
            cellStylenumRATE.setBorderBottom(THIN);

            XSSFCellStyle style3num = (XSSFCellStyle) workbook.createCellStyle();
            style3num.setFont(font3);
            style3num.setAlignment(RIGHT);
            style3num.setBorderTop(THIN);
            style3num.setBorderBottom(THIN);
            style3num.setDataFormat(hssfDataFormat.getFormat(formatdataCell));

            XSSFCellStyle style4left = (XSSFCellStyle) workbook.createCellStyle();
            style4left.setAlignment(LEFT);
            style4left.setBorderTop(THIN);
            style4left.setBorderBottom(THIN);

            Row rowP = sheet.createRow((short) 1);

            Cell cl = rowP.createCell(1);
            cl.setCellStyle(style1);
            cl.setCellValue(intestazionePdf);

            Cell cl32 = rowP.createCell(3);
            cl32.setCellStyle(style2);
            cl32.setCellValue("Transfer " + siq.getNumTransfer() + " " + datereport);

            Row rowP4 = sheet.createRow((short) 5);

            Cell cl4 = rowP4.createCell(1);
            cl4.setCellStyle(style2);
            cl4.setCellValue("From : ");

            Cell cl42 = rowP4.createCell(2);
            cl42.setCellStyle(style2);
            cl42.setCellValue(siq.getTobank());

            Row rowP5 = sheet.createRow((short) 6);

            Cell cl55 = rowP5.createCell(1);
            cl55.setCellStyle(style2);
            cl55.setCellValue("To: ");

            Cell cl552 = rowP5.createCell(2);
            cl552.setCellStyle(style2);
            cl552.setCellValue(siq.getFromsafe());

            Row rowP7 = sheet.createRow((short) 7);

            Cell cl7 = rowP7.createCell(1);
            cl7.setCellStyle(style2);
            cl7.setCellValue("User: ");

            Cell cl72 = rowP7.createCell(2);
            cl72.setCellStyle(style2);
            cl72.setCellValue(siq.getPinuser());

            Row row = sheet.createRow((short) 2);
            Cell c1l7 = row.createCell(1);
            c1l7.setCellStyle(style2);
            c1l7.setCellValue(siq.getId_filiale() + " - " + siq.getDe_filiale());

            Row row66 = sheet.createRow((short) 10);

            //mi scandisco le colonne
            for (int j = 0; j < colonne_change_sp.size(); j++) {
                Cell cl5 = row66.createCell(j + 1);
                cl5.setCellStyle(style3);
                if (j == 0) {
                    cl5.setCellStyle(style3left);
                }
                cl5.setCellValue(colonne_change_sp.get(j));

            }

            Row row77 = sheet.createRow((short) 8);
            row77.setHeight((short) 555);
            for (int j = 0; j < 6; j++) {

                if (j == 1) {

                    Cell cl5 = row77.createCell(j + 1);
                    cl5.setCellStyle(style3center);
                    cl5.setCellValue("Notes");

                } else if (j == 2) {
                    Cell cl5 = row77.createCell(j + 2);
                    cl5.setCellStyle(style3center);
                    cl5.setCellValue("Euro TC / Travel Cheques");
                } else {
                }
            }

            CellRangeAddress cellRangeAddress = new CellRangeAddress(8, 8, 2, 3);
            sheet.addMergedRegion(cellRangeAddress);

            cellRangeAddress = new CellRangeAddress(8, 8, 4, 5);
            sheet.addMergedRegion(cellRangeAddress);

            int cntriga = 12;

            //  document.add(table2);
            //  document.add(sep);
            ArrayList<ToBankingSheet_value> dati = siq.getDati();

            double branchtotal = 0, spreadtotal = 0;

            for (int i = 0; i < dati.size(); i++) {
                cntriga++;
                Row row6 = sheet.createRow((short) cntriga);

                ToBankingSheet_value temp = dati.get(i);
                ArrayList<String> datitemp = temp.getDati_string();

                Cell f1 = row6.createCell(1);
                f1.setCellStyle(style4left);
                f1.setCellValue(temp.getCurrency());

                for (int n = 1; n < colonne_change_sp.size(); n++) {

                    if (colonne_change_sp.get(n).equals("Bank Total")) {
                        branchtotal += fd((datitemp.get(n)));
                    } else if (colonne_change_sp.get(n).equals("Spread")) {
                        spreadtotal += fd((datitemp.get(n)));
                    }

                    Cell f2 = row6.createCell(n + 1);

                    if (colonne_change_sp.get(n).contains("Rate")) {
                        f2.setCellStyle(cellStylenumRATE);
                    } else {
                        f2.setCellStyle(cellStylenum);
                    }

                    f2.setCellValue(fd(datitemp.get(n)));

                }

            }

            cntriga++;
            cntriga++;

            Row row9 = sheet.createRow((short) cntriga);

            for (int n = 1; n < colonne_change_sp.size(); n++) {

                if (n == 1) {
                    Cell f7 = row9.createCell(n + 1);
                    f7.setCellStyle(style3);
                    f7.setCellValue("Total");
                } else if (colonne_change_sp.get(n).equals("Bank Total")) {

                    Cell f7 = row9.createCell(n + 1);
                    f7.setCellStyle(style3num);
                    f7.setCellValue(fd(roundDoubleandFormat(branchtotal, 2)));

                } else if (colonne_change_sp.get(n).equals("Spread")) {

                    Cell f7 = row9.createCell(n + 1);
                    f7.setCellStyle(style3num);
                    f7.setCellValue(fd(roundDoubleandFormat(spreadtotal, 2)));

                } else if (colonne_change_sp.get(n).equals("%")) {

                    Cell f7 = row9.createCell(n + 1);
                    f7.setCellStyle(style3num);
                    double toperc = 0.0;
                        if (branchtotal != 0) {
                            toperc = (spreadtotal / branchtotal) * 100.00;
                        }
                    f7.setCellValue(fd(roundDoubleandFormat(toperc, 2)));

                }

            }

            cntriga++;
            cntriga++;
            Row row10 = sheet.createRow((short) cntriga);
            Cell f7 = row10.createCell(1);
            f7.setCellStyle(style3);
            f7.setCellValue("Notes:");
            Cell f71 = row10.createCell(2);
            f71.setCellStyle(style3left);
            f71.setCellValue(siq.getNote());

            if (siq.getAuto().equals("M")) {
                cntriga++;
                cntriga++;
                Row row12 = sheet.createRow((short) cntriga);
                Cell f15 = row12.createCell(1);
                f15.setCellStyle(style3left);
                f15.setCellValue("OPERATION MANUAL");
            }

            sheet.autoSizeColumn(0, true);
            sheet.autoSizeColumn(1, true);
            sheet.autoSizeColumn(2, true);
            sheet.autoSizeColumn(3, true);
            sheet.autoSizeColumn(4, true);
            sheet.autoSizeColumn(5, true);
            sheet.autoSizeColumn(6, true);
            sheet.autoSizeColumn(7, true);
            sheet.autoSizeColumn(8, true);
            sheet.autoSizeColumn(9, true);
            sheet.autoSizeColumn(10, true);

            try (FileOutputStream out = new FileOutputStream(xls)) {
                workbook.write(out);
            }
            String base64 = new String(encodeBase64(readFileToByteArray(xls)));
            xls.delete();
            return base64;

        } catch (IOException ex) {
            insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
        }
        return null;
    }
    //FROM BANK

    private static PdfReader getPDF(String pdf) {
        try {
            PdfReader reader = new PdfReader(new ByteArrayInputStream(decodeBase64(pdf)));
            return reader;
        } catch (IOException ex) {
            return null;
        }
    }

    private static XSSFSheet getXLS(String xls) {
        try {
            InputStream is = new ByteArrayInputStream(decodeBase64(xls));
            XSSFWorkbook workbook = new XSSFWorkbook(is);
            XSSFSheet sheet = workbook.getSheetAt(0);
            return sheet;
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     *
     * @param path
     * @param codlist
     * @param br
     * @param array_bank
     * @param array_creditcard
     * @param ispdf
     * @return
     */
    public static String complete(String path, ArrayList<ET_change> codlist, ArrayList<Branch> br,
            ArrayList<String[]> array_bank, ArrayList<String[]> array_creditcard, boolean ispdf) {
        try {
            List<PdfReader> listr = new ArrayList<>();
            List<XSSFSheet> listx = new ArrayList<>();

            codlist.forEach(et -> {
                String datereport = formatStringtoStringDate(et.getDt_it(), patternsqldate, patternnormdate);
                String defil = formatBankBranchReport(et.getFiliale(), "BR", null, br);
                String ba = formatBankBranch(et.getCod_dest(), "BA", array_bank, null, array_creditcard);
                if (et.getFg_tofrom().equals("T") && format_tofrom_brba(et.getFg_tofrom(), et.getFg_brba(), et.getCod_dest(), array_creditcard).contains("POS")) { //TO POS

                    Db_Master db = new Db_Master();
                    ArrayList<ToBankingSheet_value> datiPOS = db.list_POSBASheet_value(et);
                    db.closeDB();

                    ToBankingSheet_value pdf = new ToBankingSheet_value();
                    pdf.setAuto(et.getAuto());
                    pdf.setId_filiale(et.getFiliale());
                    pdf.setDe_filiale(defil);
                    pdf.setFromsafe(et.getFiliale() + " - " + defil);
                    pdf.setTobank(et.getCod_dest() + " - " + ba);
                    pdf.setPinuser(et.getUser());
                    pdf.setNumtranfer(et.getId());
                    pdf.setNote(et.getNote());
                    pdf.setDati(datiPOS);

                    if (ispdf) {
                        String base64 = TOBANK_POS_pdf(path, pdf, datereport);
                        if (base64 != null) {
                            PdfReader def = getPDF(base64);
                            if (def != null) {
                                listr.add(def);
                            }
                        }
                    } else {
                        String base64 = TOBANK_POS_xls(path, pdf, datereport);
                        if (base64 != null) {
                            XSSFSheet def = getXLS(base64);
                            if (def != null) {
                                listx.add(def);
                            }
                        }
                    }
                } else if (et.getFg_brba().equals("BA") && et.getFg_tofrom().equals("T")) { //TO BANK
                    Db_Master db = new Db_Master();
                    ArrayList<ToBankingSheet_value> dati = db.list_ToBankingSheet_value(et);
                    db.closeDB();
                    ToBankingSheet_value pdf = new ToBankingSheet_value();
                    pdf.setId_filiale(et.getFiliale());
                    pdf.setDe_filiale(defil);
                    pdf.setAuto(et.getAuto());
                    pdf.setFromsafe(et.getFiliale() + " - " + defil);
                    pdf.setTobank(et.getCod_dest() + " - " + ba);
                    pdf.setPinuser(et.getUser());
                    pdf.setNumtranfer(et.getId());
                    pdf.setNote(et.getNote());
                    pdf.setDati(dati);
                    if (ispdf) {
                        String base64 = TOBANK_pdf(path, pdf, datereport);
                        if (base64 != null) {
                            PdfReader def = getPDF(base64);
                            if (def != null) {
                                listr.add(def);
                            }
                        }
                    } else {
                        String base64 = TOBANK_xls(path, pdf, datereport);
                        if (base64 != null) {
                            XSSFSheet def = getXLS(base64);
                            if (def != null) {
                                listx.add(def);
                            }
                        }
                    }
                } else if (et.getFg_brba().equals("BA") && et.getFg_tofrom().equals("F")) { //FROM BANK
                    Db_Master db = new Db_Master();
                    ArrayList<ToBankingSheet_value> dati = db.list_ToBankingSheet_value(et);
                    db.closeDB();
                    ToBankingSheet_value pdf = new ToBankingSheet_value();
                    pdf.setAuto(et.getAuto());
                    pdf.setId_filiale(et.getFiliale());
                    pdf.setDe_filiale(defil);
                    pdf.setFromsafe(et.getFiliale() + " - " + defil);
                    pdf.setTobank(et.getCod_dest() + " - " + ba);
                    pdf.setPinuser(et.getUser());
                    pdf.setNumtranfer(et.getId());
                    pdf.setNote(et.getNote());
                    pdf.setDati(dati);
                    if (ispdf) {
                        String base64 = FROMBANK_pdf(path, pdf, datereport);
                        if (base64 != null) {
                            PdfReader def = getPDF(base64);
                            if (def != null) {
                                listr.add(def);
                            }
                        }
                    } else {
                        String base64 = FROMBANK_xls(path, pdf, datereport);
                        if (base64 != null) {
                            XSSFSheet def = getXLS(base64);
                            if (def != null) {
                                listx.add(def);
                            }
                        }
                    }
                } else if (et.getFg_brba().equals("BR") && et.getFg_tofrom().equals("F")) { //FROM BRANCH
                    Db_Master db = new Db_Master();
                    String note_fromBranch = db.note_fromBranch(et.getCod_in());
                    Branch from = db.get_branch(et.getFiliale());
                    Branch dest = db.get_branch(et.getCod_dest());
                    ArrayList<FromBranchingSheet_value> dati = db.list_FromBranchingSheet_value(et);
                    ArrayList<NoChangeFromBranchingSheet_value> dati2 = db.list_FromBranchingSheet_valueNC(et);
                    db.closeDB();
                    FromBranchingSheet_value pdf = new FromBranchingSheet_value();
                    pdf.setAuto(et.getAuto());
                    pdf.setId_filiale(from.getCod());
                    pdf.setDe_filiale(from.getDe_branch());
                    pdf.setFromsafe(from.getCod() + " - " + from.getDe_branch());
                    pdf.setTobranch(dest.getCod() + " - " + dest.getDe_branch());
                    pdf.setPinuser(et.getUser());
                    pdf.setNumtranfer(et.getId());
                    pdf.setNote(et.getNote());
                    pdf.setNoteFROM(note_fromBranch);
                    pdf.setDati(dati);
                    String base64;
                    if (dati.size() > 0) {
                        if (ispdf) {
                            base64 = FROMBRANCH_CHANGE_pdf(path, pdf, datereport);
                        } else {
                            base64 = FROMBRANCH_CHANGE_xls(path, pdf, datereport);
                        }
                    } else {
                        if (ispdf) {
                            base64 = FROMBRANCH_NOCHANGE_pdf(path, pdf, datereport, dati2);
                        } else {
                            base64 = FROMBRANCH_NOCHANGE_xls(path, pdf, datereport, dati2);
                        }
                    }
                    if (base64 != null) {
                        if (ispdf) {
                            PdfReader def = getPDF(base64);
                            if (def != null) {
                                listr.add(def);
                            }
                        } else {
                            XSSFSheet def = getXLS(base64);
                            if (def != null) {
                                listx.add(def);
                            }
                        }
                    }
                } else if (et.getFg_brba().equals("BR") && et.getFg_tofrom().equals("T")) { //TO BRANCH
                    Db_Master db = new Db_Master();
                    Branch from = db.get_branch(et.getFiliale());
                    Branch dest = db.get_branch(et.getCod_dest());
                    ArrayList<ToBranchingSheet_value> dati = db.list_ToBranchingSheet_value(et);

                    ArrayList<NoChangeToBranchingSheet_value> dati2 = db.list_ToBranchingSheet_valueNC(et);

                    db.closeDB();
                    ToBranchingSheet_value pdf = new ToBranchingSheet_value();
                    pdf.setId_filiale(from.getCod());
                    pdf.setDe_filiale(from.getDe_branch());
                    pdf.setAuto(et.getAuto());
                    pdf.setFromsafe(from.getCod() + " - " + from.getDe_branch());
                    pdf.setTobranch(dest.getCod() + " - " + dest.getDe_branch());
                    pdf.setPinuser(et.getUser());
                    pdf.setNumtranfer(et.getId());
                    pdf.setNote(et.getNote());
                    pdf.setDati(dati);
                    String base64;
                    if (dati.size() > 0) {
                        if (ispdf) {
                            base64 = TOBRANCH_CHANGE_pdf(path, pdf, datereport);
                        } else {
                            base64 = TOBRANCH_CHANGE_xls(path, pdf, datereport);
                        }
                    } else {
                        if (ispdf) {
                            base64 = TOBRANCH_NOCHANGE_pdf(path, pdf, datereport, dati2);
                        } else {
                            base64 = TOBRANCH_NOCHANGE_xls(path, pdf, datereport, dati2);
                        }
                    }
                    if (base64 != null) {
                        if (ispdf) {
                            PdfReader def = getPDF(base64);
                            if (def != null) {
                                listr.add(def);
                            }
                        } else {
                            XSSFSheet def = getXLS(base64);
                            if (def != null) {
                                listx.add(def);
                            }
                        }
                    }
                }
            });

            if (ispdf) {

                if (!listr.isEmpty()) {
                    Document document = new Document();
                    File pdffile = new File(path + generaId(50) + "_Central_ET_Report.pdf");
                    try(OutputStream outputStream = new FileOutputStream(pdffile)){
                    PdfWriter writer = getInstance(document, outputStream);
                    document.open();
                    PdfContentByte cb = writer.getDirectContent();
                    for (PdfReader rd : listr) {
                        for (int i = 1; i <= rd.getNumberOfPages(); i++) {
                            document.newPage();
                            PdfImportedPage page = writer.getImportedPage(rd, i);
                            cb.addTemplate(page, 0, 0);
                        }
                    }
                    document.close();
                    writer.close();
                    outputStream.close();
                    String base64 = new String(encodeBase64(readFileToByteArray(pdffile)));
                    pdffile.delete();
                    return base64;
                }
            } else {

                if (!listx.isEmpty()) {

                    File xlsx = new File(path + generaId(50) + "_Central_ET_Report.xlsx");
                    XSSFWorkbook workbook = new XSSFWorkbook();
                    XSSFSheet dstSheet_ = workbook.createSheet("Central_ET_Report");
                    int startrow = 1;
                    int mx = 0;
                    for (XSSFSheet srcSheet_ : listx) {
                        for (int x = 0; x < srcSheet_.getNumMergedRegions(); x++) {
                            CellRangeAddress mergedRegion = srcSheet_.getMergedRegion(x);
                            int st = startrow - 1;
                            CellRangeAddress mergedRegionnew = new CellRangeAddress(
                                    st + mergedRegion.getFirstRow(),
                                    st + mergedRegion.getLastRow(),
                                    mergedRegion.getFirstColumn(),
                                    mergedRegion.getLastColumn());
                            dstSheet_.addMergedRegion(mergedRegionnew);
                        }

                        int maxColumnNum = 0;
                        for (int i = srcSheet_.getFirstRowNum(); i <= srcSheet_.getLastRowNum(); i++) {
                            Row srcRow = srcSheet_.getRow(i);
                            Row destRow = dstSheet_.createRow(startrow);
                            startrow++;
                            if (srcRow != null) {
                                copyRow(srcRow, destRow, srcSheet_);
                                if (srcRow.getLastCellNum() > maxColumnNum) {
                                    maxColumnNum = srcRow.getLastCellNum();
                                }
                            }
                        }

                        for (int i = 0; i <= maxColumnNum; i++) {
                            dstSheet_.setColumnWidth(i, srcSheet_.getColumnWidth(i));
                        }
                        if (maxColumnNum > mx) {
                            mx = maxColumnNum;
                        }

                        startrow++;
                        startrow++;
                        startrow++;

                    }

                    try (FileOutputStream out = new FileOutputStream(xlsx)) {
                        workbook.write(out);
                    }

                    String base64 = new String(encodeBase64(readFileToByteArray(xlsx)));
                    xlsx.delete();
                    return base64;
                }
            }
        }
        } catch (DocumentException | IOException ex) {
            ex.printStackTrace();
            insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
        }
        return null;
    }

    private static void copyRow(Row srcRow,
            Row dstRow, XSSFSheet srcSheet_) {
        if (srcRow.isFormatted()) {
            dstRow.setRowStyle(srcRow.getRowStyle());
        }

        short dh = srcSheet_.getDefaultRowHeight();
        if (srcRow.getHeight() != dh) {
            dstRow.setHeight(srcRow.getHeight());
        }

        int j = srcRow.getFirstCellNum();
        if (j < 0) {
            j = 0;
        }
        for (; j <= srcRow.getLastCellNum(); j++) {
            Cell srcCell = srcRow.getCell(j); // ancienne cell
            Cell dstCell = dstRow.getCell(j); // new cell
            if (srcCell != null) {
                if (dstCell == null) {
                    dstCell = dstRow.createCell(j, srcCell.getCellType());
                }
                copyCell(srcCell, dstCell);
            }
        }

    }

    private static XSSFCellStyle clonestyle(XSSFCell origCell, Workbook wb_new) {
        XSSFCellStyle origCellStyle = origCell.getCellStyle();
        XSSFCellStyle copyCellStyle = (XSSFCellStyle) wb_new.createCellStyle();
        copyCellStyle.cloneStyleFrom(origCellStyle);
        return copyCellStyle;
    }

//    private static XSSFCellStyle clonestyle(HSSFCellStyle st1, Workbook wb_new, Workbook wb_old) {
//        XSSFCellStyle _xssfStyle = (XSSFCellStyle) wb_new.createCellStyle();
//        _xssfStyle.setBorderLeft(st1.getBorderLeftEnum());
//        _xssfStyle.setBorderRight(st1.getBorderRightEnum());
//        _xssfStyle.setBorderTop(st1.getBorderTopEnum());
//        _xssfStyle.setBorderBottom(st1.getBorderBottomEnum());
//        _xssfStyle.setFillForegroundColor(st1.getFillForegroundColor());
//        _xssfStyle.setFillBackgroundColor(st1.getFillBackgroundColor());
//        _xssfStyle.setFillPattern(st1.getFillPatternEnum());
//        _xssfStyle.setWrapText(st1.getWrapText());
//        _xssfStyle.setVerticalAlignment(st1.getVerticalAlignmentEnum());
//        _xssfStyle.setFont(getFont(st1, wb_new, wb_old));
//        if (!st1.getDataFormatString().equals("General")) {
//            _xssfStyle.setDataFormat(st1.getDataFormat());
//        }
//        return _xssfStyle;
//    }
//    private static XSSFFont getFont(HSSFCellStyle style, Workbook wb_new, Workbook wb_old) {
//        HSSFFont cellFont = style.getFont(wb_old);
//
//        XSSFFont defaultFont = (XSSFFont) wb_new.createFont();
//        defaultFont.setFontHeight(cellFont.getFontHeight());
//        defaultFont.setFontName(cellFont.getFontName());
//        defaultFont.setColor(cellFont.getColor());
//        defaultFont.setBold(cellFont.getBold());
//        defaultFont.setItalic(cellFont.getItalic());
//        return (defaultFont);
//    }
    private static void copyCell(Cell srcCell, Cell dstCell) {

        CellStyle newStyle = dstCell.getSheet().getWorkbook().createCellStyle();
        newStyle.cloneStyleFrom(srcCell.getCellStyle());
        dstCell.setCellStyle(newStyle);

//        dstCell.setCellStyle(srcCell.getCellStyle());
//        dstCell.setCellStyle(clonestyle((XSSFCell) srcCell, dstCell.getSheet().getWorkbook()));
        switch (dstCell.getCellType()) {
            case STRING:
                dstCell.setCellValue(srcCell.getStringCellValue());
                break;
            case NUMERIC:
                dstCell.setCellValue(srcCell.getNumericCellValue());
                break;
            case BLANK:
                dstCell.setCellValue("");
                break;
            case BOOLEAN:
                dstCell.setCellValue(srcCell.getBooleanCellValue());
                break;
            case ERROR:
                dstCell.setCellErrorValue(srcCell.getErrorCellValue());
                break;
            case FORMULA:
                dstCell.setCellFormula(srcCell.getCellFormula());
                break;
            default:
                break;
        }
    }

}
