/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rc.so.report;

import com.google.common.util.concurrent.AtomicDouble;
import static com.itextpdf.text.BaseColor.LIGHT_GRAY;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
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
import static com.itextpdf.text.Rectangle.BOX;
import static com.itextpdf.text.Rectangle.LEFT;
import static com.itextpdf.text.Rectangle.NO_BORDER;
import static com.itextpdf.text.Rectangle.RIGHT;
import static com.itextpdf.text.Rectangle.TOP;
import static com.itextpdf.text.pdf.BaseFont.HELVETICA;
import static com.itextpdf.text.pdf.BaseFont.WINANSI;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import static com.itextpdf.text.pdf.PdfWriter.getInstance;
import com.itextpdf.text.pdf.draw.LineSeparator;
import static rc.so.util.Constant.formatdataCell;
import static rc.so.util.Constant.formatdataCellRate;
import static rc.so.util.Constant.is_IT;
import static rc.so.util.Engine.insertTR;
import static rc.so.util.Utility.fd;
import static rc.so.util.Utility.formatMysqltoDisplay;
import static rc.so.util.Utility.generaId;
import static rc.so.util.Utility.roundDoubleandFormat;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import static java.lang.Thread.currentThread;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import static org.apache.commons.codec.binary.Base64.encodeBase64;
import static org.apache.commons.io.FileUtils.readFileToByteArray;
import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import static org.apache.poi.hssf.usermodel.HSSFFont.FONT_ARIAL;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import static org.apache.poi.ss.usermodel.BorderStyle.THIN;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFDataFormat;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author vcrugliano
 */
public class BranchStockInquiry {

    //column
    /**
     *
     */
    public static final float[] columnWidths0 = new float[]{70f, 30f};

    /**
     *
     */
    public static final float[] columnWidths1 = new float[]{60f, 30f};

    /**
     *
     */
    public static float[] columnWidths2 = null;
    final String intestazionePdf = "Branch Stock Inquiry";
    Phrase vuoto = new Phrase("\n");

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

    /**
     ** Constructor
     */
    public BranchStockInquiry() {

        this.f0_bold = getFont(HELVETICA, WINANSI, 14.04f, BOLD);
        this.f1_bold = getFont(HELVETICA, WINANSI, 9.96f, BOLD);
        this.f2_bold = getFont(HELVETICA, WINANSI, 8f, BOLD);
        this.f3_bold = getFont(HELVETICA, WINANSI, 8f, BOLDITALIC);
        this.f4_bold = getFont(HELVETICA, WINANSI, 6.96f, BOLD);
        this.f1_normal = getFont(HELVETICA, WINANSI, 9.96f, NORMAL);
        this.f2_normal = getFont(HELVETICA, WINANSI, 8f, NORMAL);
        this.f3_normal = getFont(HELVETICA, WINANSI, 6.96f, NORMAL);

    }

    /**
     *
     * @param path
     * @param list_siq
     * @param colonne
     * @param datereport
     * @return
     */
    public String receipt_new_multi(String path, List<BranchStockInquiry_value> list_siq, ArrayList<String> colonne, String datereport) {
        columnWidths2 = new float[colonne.size()];
        for (int c = 0; c < colonne.size(); c++) {
            columnWidths2[c] = 10f;
        }

        List<File> pdflist = new ArrayList<>();

        AtomicDouble totalmac = new AtomicDouble(0.0);

        list_siq.forEach(siq -> {
            try {
                File pdf = new File(path + generaId(50) + "BrachStockInquiry_NEW.pdf");
                Document document = new Document(A4, 20, 20, 20, 20);
                try(OutputStream ou = new FileOutputStream(pdf)){
                PdfWriter wr = getInstance(document, ou);
                document.open();
                PdfPTable table = new PdfPTable(2);
                table.setWidths(columnWidths0);
                table.setWidthPercentage(100);
                Phrase phrase1 = new Phrase();
                String title = intestazionePdf + " And  Countervalue";
                phrase1.add(new Chunk(title + " " + datereport, f3_bold));
                PdfPCell cell1 = new PdfPCell(phrase1);
                cell1.setBorder(NO_BORDER);
                Paragraph pa1 = new Paragraph("");
                pa1.setAlignment(ALIGN_RIGHT);
                PdfPCell cell2 = new PdfPCell(pa1);
                cell2.setBorder(NO_BORDER);
                cell2.setHorizontalAlignment(ALIGN_RIGHT);
                Phrase phrase3 = new Phrase();
                phrase3.add(new Chunk("\n " + siq.getId_filiale() + " " + siq.getDe_filiale(), f3_normal));
                PdfPCell cell3 = new PdfPCell(phrase3);
                cell3.setBorder(NO_BORDER);
                Phrase phrase4 = new Phrase();
                phrase4.add(new Chunk("", f3_normal));
                PdfPCell cell4 = new PdfPCell(phrase4);
                cell4.setBorder(NO_BORDER);
                table.addCell(cell1);
                table.addCell(cell2);
                table.addCell(cell3);
                table.addCell(cell4);
                document.add(table);
                vuoto.setFont(f3_normal);
                document.add(vuoto);
                PdfPTable table2 = new PdfPTable(colonne.size());
                table2.setWidths(columnWidths2);
                table2.setWidthPercentage(100);
                PdfPCell[] list = new PdfPCell[colonne.size()];
                for (int j = 0; j < colonne.size(); j++) {
                    Phrase phraset1 = new Phrase();
                    phraset1.add(new Chunk(colonne.get(j), f4_bold));
                    PdfPCell cellt1 = new PdfPCell(phraset1);
                    cellt1.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt1.setBorder(BOTTOM | TOP);
                    cellt1.setFixedHeight(20f);
                    cellt1.setBackgroundColor(LIGHT_GRAY);
                    if (j == 0) {
                        cellt1.setHorizontalAlignment(ALIGN_LEFT);
                    }
                    cellt1.setBorderWidth(0.7f);
                    list[j] = cellt1;
                }
                PdfPTable table3 = new PdfPTable(colonne.size());
                table3.setWidths(columnWidths2);
                table3.setWidthPercentage(100);
                for (PdfPCell list1 : list) {
                    PdfPCell temp = (PdfPCell) (list1);
                    table3.addCell(temp);
                }
                document.add(table3);
                LineSeparator sep = new LineSeparator();
                sep.setLineWidth((float) 0.5);
                ArrayList<BranchStockInquiry_value> dati = siq.getDati();
                Phrase phraset;
                PdfPCell cellt;
                table3 = new PdfPTable(colonne.size());
                table3.setWidths(columnWidths2);
                table3.setWidthPercentage(100);
                double totalcv = 0.0;
                for (int i = 0; i < dati.size(); i++) {
                    BranchStockInquiry_value temp = dati.get(i);
                    ArrayList<String> datitemp = temp.getDati_string();
                    phraset = new Phrase();
                    phraset.add(new Chunk(temp.getCurrency(), f3_normal));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_LEFT);
                    cellt.setBorder(BOTTOM);
                    table3.addCell(cellt);
                    for (int n = 0; n < colonne.size() - 1; n++) {
                        phraset = new Phrase();
                        if (!is_IT && n == 1) {
                            phraset.add(new Chunk("", f3_normal));
                        } else {
                            if (n == 4) {
                                totalcv += fd(datitemp.get(n));
                            }
                            phraset.add(new Chunk(formatMysqltoDisplay(datitemp.get(n)), f3_normal));
                        }
                        cellt = new PdfPCell(phraset);
                        cellt.setHorizontalAlignment(ALIGN_RIGHT);
                        cellt.setBorder(BOTTOM);
                        table3.addCell(cellt);
                    }
                }
                phraset = new Phrase();
                phraset.add(new Chunk("", f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(NO_BORDER);
                table3.addCell(cellt);
                table3.addCell(cellt);
                table3.addCell(cellt);
                table3.addCell(cellt);
                phraset = new Phrase();
                phraset.add(new Chunk("Total", f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBackgroundColor(LIGHT_GRAY);
                cellt.setBorder(RIGHT | LEFT | BOTTOM);
                table3.addCell(cellt);
                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(totalcv, 2)), f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(RIGHT | BOTTOM);
                table3.addCell(cellt);
                document.add(table3);
                document.close();
                wr.close();
                ou.close();
                pdflist.add(pdf);
                totalmac.addAndGet(totalcv);
            }
            } catch (IOException | DocumentException ex) {
                insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
            }
        });

        if (pdflist.isEmpty()) {
            return null;
        } else {
            try {
                File pdf = new File(path + generaId(50) + "BrachStockInquiry_NEW.pdf");
                PDFMergerUtility obj = new PDFMergerUtility();
                obj.setDestinationFileName(pdf.getPath());
                pdflist.forEach(pdf1 -> {
                    try {
                        obj.addSource(pdf1);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                });
                //CREO ultima pagina

                File pdftemp = new File(path + generaId(50) + "BrachStockInquiry_last.pdf");
                Document document = new Document(A4, 20, 20, 20, 20);
                try(OutputStream ou = new FileOutputStream(pdftemp)){
                PdfWriter wr = getInstance(document, ou);
                document.open();
                PdfPTable table = new PdfPTable(2);
                table.setWidths(columnWidths0);
                table.setWidthPercentage(100);
                Phrase phrase1 = new Phrase();
                String title = intestazionePdf + " And  Countervalue";
                phrase1.add(new Chunk(title + " " + datereport, f3_bold));
                PdfPCell cell1 = new PdfPCell(phrase1);
                cell1.setBorder(NO_BORDER);
                Paragraph pa1 = new Paragraph("");
                pa1.setAlignment(ALIGN_RIGHT);
                PdfPCell cell2 = new PdfPCell(pa1);
                cell2.setBorder(NO_BORDER);
                cell2.setHorizontalAlignment(ALIGN_RIGHT);
                table.addCell(cell1);
                table.addCell(cell2);
                document.add(table);
                vuoto.setFont(f3_normal);
                document.add(vuoto);

                table = new PdfPTable(2);
                table.setWidths(columnWidths0);
                table.setWidthPercentage(100);

                Phrase phraset = new Phrase();
                phraset.add(new Chunk("Total System", f4_bold));
                PdfPCell cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBackgroundColor(LIGHT_GRAY);
                cellt.setBorder(BOX);
                table.addCell(cellt);
                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(totalmac.get(), 2)), f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOX);
                table.addCell(cellt);
                document.add(table);
                document.close();
                wr.close();
                ou.close();
                obj.addSource(pdftemp);
                obj.mergeDocuments(null);
                String base64 = new String(encodeBase64(readFileToByteArray(pdf)));
                return base64;
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return null;
        }

    }

    public String receipt_new(String path, BranchStockInquiry_value siq, ArrayList<String> colonne, String datereport) {
        columnWidths2 = new float[colonne.size()];
        for (int c = 0; c < colonne.size(); c++) {
            columnWidths2[c] = 10f;
        }

        try {
            File pdf = new File(path + generaId(50) + "BrachStockInquiry_NEW.pdf");
            Document document = new Document(A4, 20, 20, 20, 20);
            try(OutputStream ou = new FileOutputStream(pdf)){
            PdfWriter wr = getInstance(document, ou);
            document.open();
            PdfPTable table = new PdfPTable(2);
            table.setWidths(columnWidths0);
            table.setWidthPercentage(100);
            Phrase phrase1 = new Phrase();
            String title = intestazionePdf + " And  Countervalue";
            phrase1.add(new Chunk(title + " " + datereport, f3_bold));
            PdfPCell cell1 = new PdfPCell(phrase1);
            cell1.setBorder(NO_BORDER);
            Paragraph pa1 = new Paragraph("");
            pa1.setAlignment(ALIGN_RIGHT);
            PdfPCell cell2 = new PdfPCell(pa1);
            cell2.setBorder(NO_BORDER);
            cell2.setHorizontalAlignment(ALIGN_RIGHT);
            Phrase phrase3 = new Phrase();
            phrase3.add(new Chunk("\n " + siq.getId_filiale() + " " + siq.getDe_filiale(), f3_normal));
            PdfPCell cell3 = new PdfPCell(phrase3);
            cell3.setBorder(NO_BORDER);
            Phrase phrase4 = new Phrase();
            phrase4.add(new Chunk("", f3_normal));
            PdfPCell cell4 = new PdfPCell(phrase4);
            cell4.setBorder(NO_BORDER);
            table.addCell(cell1);
            table.addCell(cell2);
            table.addCell(cell3);
            table.addCell(cell4);
            document.add(table);
            vuoto.setFont(f3_normal);
            document.add(vuoto);
            PdfPTable table2 = new PdfPTable(colonne.size());
            table2.setWidths(columnWidths2);
            table2.setWidthPercentage(100);
            PdfPCell[] list = new PdfPCell[colonne.size()];
            for (int j = 0; j < colonne.size(); j++) {
                Phrase phraset1 = new Phrase();
                phraset1.add(new Chunk(colonne.get(j), f4_bold));
                PdfPCell cellt1 = new PdfPCell(phraset1);
                cellt1.setHorizontalAlignment(ALIGN_RIGHT);
                cellt1.setBorder(BOTTOM | TOP);
                cellt1.setFixedHeight(20f);
                cellt1.setBackgroundColor(LIGHT_GRAY);
                if (j == 0) {
                    cellt1.setHorizontalAlignment(ALIGN_LEFT);
                }
                cellt1.setBorderWidth(0.7f);
                list[j] = cellt1;
            }
            PdfPTable table3 = new PdfPTable(colonne.size());
            table3.setWidths(columnWidths2);
            table3.setWidthPercentage(100);
            for (PdfPCell list1 : list) {
                PdfPCell temp = (PdfPCell) (list1);
                table3.addCell(temp);
            }
            document.add(table3);
            LineSeparator sep = new LineSeparator();
            sep.setLineWidth((float) 0.5);
            ArrayList<BranchStockInquiry_value> dati = siq.getDati();
            Phrase phraset;
            PdfPCell cellt;
            table3 = new PdfPTable(colonne.size());
            table3.setWidths(columnWidths2);
            table3.setWidthPercentage(100);
            double totalcv = 0.0;
            for (int i = 0; i < dati.size(); i++) {
                BranchStockInquiry_value temp = dati.get(i);
                ArrayList<String> datitemp = temp.getDati_string();
                phraset = new Phrase();
                phraset.add(new Chunk(temp.getCurrency(), f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_LEFT);
                cellt.setBorder(BOTTOM);
                table3.addCell(cellt);
                for (int n = 0; n < colonne.size() - 1; n++) {
                    phraset = new Phrase();
                    if (!is_IT && n == 1) {
                        phraset.add(new Chunk("", f3_normal));
                    } else {
                        if (n == 4) {
                            totalcv += fd(datitemp.get(n));
                        }
                        phraset.add(new Chunk(formatMysqltoDisplay(datitemp.get(n)), f3_normal));
                    }
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setBorder(BOTTOM);
                    table3.addCell(cellt);
                }
            }
            phraset = new Phrase();
            phraset.add(new Chunk("", f3_normal));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setBorder(NO_BORDER);
            table3.addCell(cellt);
            table3.addCell(cellt);
            table3.addCell(cellt);
            table3.addCell(cellt);
            phraset = new Phrase();
            phraset.add(new Chunk("Total", f4_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setBackgroundColor(LIGHT_GRAY);
            cellt.setBorder(RIGHT | LEFT | BOTTOM);
            table3.addCell(cellt);
            phraset = new Phrase();
            phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(totalcv, 2)), f4_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setBorder(RIGHT | BOTTOM);
            table3.addCell(cellt);
            document.add(table3);
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

    public String receipt(String path, BranchStockInquiry_value siq, ArrayList<String> colonne, String datereport) {

        columnWidths2 = new float[colonne.size()];

        for (int c = 0; c < colonne.size(); c++) {
            columnWidths2[c] = 10f;
        }

        try {
            File pdf = new File(path + generaId(50) + "BrachStockInquiry_OK.pdf");
            Document document = new Document(A4, 20, 20, 20, 20);
            try(OutputStream ou = new FileOutputStream(pdf)){
            PdfWriter wr = getInstance(document, ou);
            document.open();

            PdfPTable table = new PdfPTable(2);
            table.setWidths(columnWidths0);
            table.setWidthPercentage(100);

            Phrase phrase1 = new Phrase();
            phrase1.add(new Chunk(intestazionePdf + " " + datereport, f3_bold));
            PdfPCell cell1 = new PdfPCell(phrase1);
            cell1.setBorder(NO_BORDER);

            //Paragraph pa1 = new Paragraph(new Phrase(datereport, f2_normal));
            Paragraph pa1 = new Paragraph("");
            pa1.setAlignment(ALIGN_RIGHT);
            PdfPCell cell2 = new PdfPCell(pa1);
            cell2.setBorder(NO_BORDER);
            cell2.setHorizontalAlignment(ALIGN_RIGHT);

            Phrase phrase3 = new Phrase();
            phrase3.add(new Chunk("\n " + siq.getId_filiale() + " " + siq.getDe_filiale(), f3_normal));
            PdfPCell cell3 = new PdfPCell(phrase3);
            cell3.setBorder(NO_BORDER);

            Phrase phrase4 = new Phrase();
            phrase4.add(new Chunk("", f3_normal));
            PdfPCell cell4 = new PdfPCell(phrase4);
            cell4.setBorder(NO_BORDER);

            table.addCell(cell1);
            table.addCell(cell2);
            table.addCell(cell3);
            table.addCell(cell4);
            document.add(table);

            vuoto.setFont(f3_normal);
            document.add(vuoto);

            PdfPTable table2 = new PdfPTable(colonne.size());
            table2.setWidths(columnWidths2);
            table2.setWidthPercentage(100);

            PdfPCell[] list = new PdfPCell[colonne.size()];
            //mi scandisco le colonne
            for (int j = 0; j < colonne.size(); j++) {
                Phrase phraset1 = new Phrase();
                phraset1.add(new Chunk(colonne.get(j), f4_bold));
                PdfPCell cellt1 = new PdfPCell(phraset1);
                cellt1.setHorizontalAlignment(ALIGN_RIGHT);
                cellt1.setBorder(BOTTOM | TOP);
                cellt1.setFixedHeight(20f);
                cellt1.setBackgroundColor(LIGHT_GRAY);
                if (j == 0) {
                    cellt1.setHorizontalAlignment(ALIGN_LEFT);
                }
                //cellt1.setBorder(Rectangle.BOTTOM);
                cellt1.setBorderWidth(0.7f);

                list[j] = cellt1;

            }

            PdfPTable table3 = new PdfPTable(colonne.size());
            table3.setWidths(columnWidths2);
            table3.setWidthPercentage(100);

            for (PdfPCell list1 : list) {
                PdfPCell temp = (PdfPCell) (list1);
                table3.addCell(temp);
            }

            document.add(table3);

            LineSeparator sep = new LineSeparator();
            sep.setLineWidth((float) 0.5);
            ArrayList<BranchStockInquiry_value> dati = siq.getDati();
            Phrase phraset;
            PdfPCell cellt;

            table3 = new PdfPTable(colonne.size());
            table3.setWidths(columnWidths2);
            table3.setWidthPercentage(100);
            for (int i = 0; i < dati.size(); i++) {
                BranchStockInquiry_value temp = dati.get(i);

                ArrayList<String> datitemp = temp.getDati_string();

                phraset = new Phrase();
                phraset.add(new Chunk(temp.getCurrency(), f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_LEFT);
                cellt.setBorder(BOTTOM);
                table3.addCell(cellt);

                for (int n = 0; n < colonne.size() - 1; n++) {

                    phraset = new Phrase();
                    if (!is_IT && n == 1) {
                        phraset.add(new Chunk("", f3_normal));
                    } else {
                        phraset.add(new Chunk(formatMysqltoDisplay(datitemp.get(n)), f3_normal));
                    }
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
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
     * @param list_siq
     * @param colonne
     * @param datereport
     * @return
     */
    public String receiptexcel_new(String path, List<BranchStockInquiry_value> list_siq, ArrayList<String> colonne, String datereport) {

        try {
            File pdf = new File(path + generaId(50) + "BrachStockInquiry_NEW.xlsx");

            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet("BranchStockInquiry");
            //CREAZIONE FONT
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
            style3.setAlignment(HorizontalAlignment.RIGHT);
            style3.setBorderTop(THIN);
            style3.setBorderBottom(THIN);

            XSSFDataFormat hssfDataFormat = workbook.createDataFormat();

            XSSFCellStyle style3num = workbook.createCellStyle();
            style3num.setFont(font3);
            style3num.setAlignment(HorizontalAlignment.RIGHT);
            style3num.setBorderTop(THIN);
            style3num.setBorderBottom(THIN);
            style3num.setDataFormat(hssfDataFormat.getFormat(formatdataCell));

            XSSFCellStyle style3left = workbook.createCellStyle();
            style3left.setFont(font3);
            style3left.setAlignment(HorizontalAlignment.LEFT);
            style3left.setBorderTop(THIN);
            style3left.setBorderBottom(THIN);

            XSSFFont font4 = workbook.createFont();
            font4.setFontName(FONT_ARIAL);
            font4.setFontHeightInPoints((short) 10);

            XSSFCellStyle style4 = workbook.createCellStyle();
            style4.setAlignment(HorizontalAlignment.RIGHT);
            style4.setBorderTop(THIN);
            style4.setBorderBottom(THIN);

            XSSFCellStyle style4num = workbook.createCellStyle();
            style4num.setAlignment(HorizontalAlignment.RIGHT);
            style4num.setBorderTop(THIN);
            style4num.setBorderBottom(THIN);

            style4num.setDataFormat(hssfDataFormat.getFormat(formatdataCell));

            XSSFCellStyle style4rate = workbook.createCellStyle();
            style4rate.setAlignment(HorizontalAlignment.RIGHT);
            style4rate.setBorderTop(THIN);
            style4rate.setBorderBottom(THIN);
            style4rate.setDataFormat(hssfDataFormat.getFormat(formatdataCellRate));

            XSSFCellStyle style4left = workbook.createCellStyle();
            style4left.setAlignment(HorizontalAlignment.LEFT);
            style4left.setBorderTop(THIN);
            style4left.setBorderBottom(THIN);

            XSSFRow rowP = sheet.createRow((short) 1);

            XSSFCell cl = rowP.createCell(1);
            cl.setCellStyle(style1);
            String title = intestazionePdf + " And  Countervalue";
            cl.setCellValue(title + " " + datereport);

            AtomicInteger startrow = new AtomicInteger(0);
            AtomicDouble totalsys = new AtomicDouble(0.0);

            list_siq.forEach(siq -> {

                startrow.addAndGet(3);
                XSSFRow row = sheet.createRow(startrow.get());
                XSSFCell cl1 = row.createCell(1);
                cl1.setCellStyle(style2);
                cl1.setCellValue(siq.getId_filiale() + " " + siq.getDe_filiale());

                startrow.addAndGet(2);

                XSSFRow row66 = sheet.createRow(startrow.get());
                startrow.addAndGet(2);
                //int cntriga = 7;

                //mi scandisco le colonne
                for (int j = 0; j < colonne.size(); j++) {
                    XSSFCell cl5 = row66.createCell(j + 1);
                    cl5.setCellStyle(style3);
                    if (j == 0) {
                        cl5.setCellStyle(style3left);
                    }
                    cl5.setCellValue(colonne.get(j));
                }

                ArrayList<BranchStockInquiry_value> dati = siq.getDati();
                double totalcv = 0.00;

                for (int i = 0; i < dati.size(); i++) {

                    startrow.addAndGet(1);
                    XSSFRow row6 = sheet.createRow(startrow.get());

                    BranchStockInquiry_value temp = dati.get(i);

                    ArrayList<String> datitemp = temp.getDati_string();

                    XSSFCell f1 = row6.createCell(1);
                    f1.setCellStyle(style4left);
                    f1.setCellValue(temp.getCurrency());

                    for (int n = 0; n < colonne.size() - 1; n++) {

                        XSSFCell f3 = row6.createCell(n + 2);

                        if (!is_IT && n == 1) {
                            f3.setCellValue("");
                            f3.setCellStyle(style4);
                        } else {
                            if (n == 3) {
                                f3.setCellStyle(style4rate);
                            } else {
                                f3.setCellStyle(style4num);
                            }
                            if (n == 4) {
                                totalcv += fd(datitemp.get(n));
                            }
                            f3.setCellValue(fd(datitemp.get(n)));
                        }

                    }
                }

                startrow.addAndGet(2);

                XSSFRow row6 = sheet.createRow(startrow.get());
                XSSFCell cl5 = row6.createCell(5);
                cl5.setCellStyle(style3left);
                cl5.setCellValue("Total");
                cl5 = row6.createCell(6);
                cl5.setCellStyle(style3num);
                cl5.setCellValue(totalcv);
                startrow.addAndGet(4);
                totalsys.addAndGet(totalcv);
            });

            if (list_siq.size() > 1) {
                XSSFRow row6 = sheet.createRow(startrow.get());
                XSSFCell cl5 = row6.createCell(5);
                cl5.setCellStyle(style1);
                cl5.setCellValue("Total System");
                cl5 = row6.createCell(6);
                cl5.setCellStyle(style3num);
                cl5.setCellValue(totalsys.get());
            }
            
            
            for(int x = 0; x<15;x++){
                sheet.autoSizeColumn(x);
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

    public String receiptexcel(String path, BranchStockInquiry_value siq, ArrayList<String> colonne, String datereport) {

        try {
            File pdf = new File(path + generaId(50) + "BranchStockInquiry.xls");
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet("BranchStockInquiry");
            //CREAZIONE FONT
            HSSFFont font = workbook.createFont();
            font.setFontName(FONT_ARIAL);
            font.setFontHeightInPoints((short) 12);
            font.setBold(true);

            HSSFCellStyle style1 = (HSSFCellStyle) workbook.createCellStyle();
            style1.setFont(font);

            HSSFFont font2 = workbook.createFont();
            font2.setFontName(FONT_ARIAL);
            font2.setFontHeightInPoints((short) 12);

            HSSFCellStyle style2 = (HSSFCellStyle) workbook.createCellStyle();
            style2.setFont(font2);

            HSSFFont font3 = workbook.createFont();
            font3.setFontName(FONT_ARIAL);
            font3.setFontHeightInPoints((short) 10);
            font3.setBold(true);

            HSSFCellStyle style3 = (HSSFCellStyle) workbook.createCellStyle();
            style3.setFont(font3);
            style3.setAlignment(HorizontalAlignment.RIGHT);
            style3.setBorderTop(THIN);
            style3.setBorderBottom(THIN);

            HSSFCellStyle style3left = (HSSFCellStyle) workbook.createCellStyle();
            style3left.setFont(font3);
            style3left.setAlignment(HorizontalAlignment.LEFT);
            style3left.setBorderTop(THIN);
            style3left.setBorderBottom(THIN);

            HSSFFont font4 = workbook.createFont();
            font4.setFontName(FONT_ARIAL);
            font4.setFontHeightInPoints((short) 10);

            HSSFCellStyle style4 = (HSSFCellStyle) workbook.createCellStyle();
            style4.setAlignment(HorizontalAlignment.RIGHT);
            style4.setBorderTop(THIN);
            style4.setBorderBottom(THIN);

            HSSFCellStyle style4left = (HSSFCellStyle) workbook.createCellStyle();
            style4left.setAlignment(HorizontalAlignment.LEFT);
            style4left.setBorderTop(THIN);
            style4left.setBorderBottom(THIN);

            Row rowP = sheet.createRow((short) 1);

            Cell cl = rowP.createCell(1);
            cl.setCellStyle(style1);
            cl.setCellValue(intestazionePdf + " " + datereport);

//            Cell cl2 = rowP.createCell(6);
//            cl2.setCellStyle(style2);
//            cl2.setCellValue(datereport);
            Row row = sheet.createRow((short) 3);
            row.createCell(1).setCellValue(siq.getId_filiale() + " " + siq.getDe_filiale());

            // ArrayList<String> dati = siq.getDati();
            Row row66 = sheet.createRow((short) 5);

            int cntriga = 7;

            //mi scandisco le colonne
            for (int j = 0; j < colonne.size(); j++) {
                Cell cl5 = row66.createCell(j + 1);
                cl5.setCellStyle(style3);
                if (j == 0) {
                    cl5.setCellStyle(style3left);
                }
                cl5.setCellValue(colonne.get(j));

            }

            ArrayList<BranchStockInquiry_value> dati = siq.getDati();

            for (int i = 0; i < dati.size(); i++) {

                cntriga++;

                Row row6 = sheet.createRow((short) cntriga);

                BranchStockInquiry_value temp = dati.get(i);

                ArrayList<String> datitemp = temp.getDati_string();

                Cell f1 = row6.createCell(1);
                f1.setCellStyle(style4left);
                f1.setCellValue(temp.getCurrency());

                for (int n = 0; n < colonne.size() - 1; n++) {

                    Cell f3 = row6.createCell(n + 2);

                    f3.setCellStyle(style4);
                    if (!is_IT && n == 1) {
                        f3.setCellValue("");
                    } else {
                        f3.setCellValue(formatMysqltoDisplay(datitemp.get(n)));
                    }

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

}
