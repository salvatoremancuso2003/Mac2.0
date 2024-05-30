/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rc.so.reportcentrale;

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
import static com.itextpdf.text.Rectangle.NO_BORDER;
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
import static rc.so.util.Constant.formatdataCellint;
import static rc.so.util.Engine.insertTR;
import static rc.so.util.Utility.fd;
import static rc.so.util.Utility.formatMysqltoDisplay;
import static rc.so.util.Utility.generaId;
import java.awt.Color;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import static java.lang.String.format;
import static java.lang.String.valueOf;
import static java.lang.Thread.currentThread;
import java.util.ArrayList;
import java.util.List;
import static org.apache.commons.codec.binary.Base64.encodeBase64;
import static org.apache.commons.io.FileUtils.readFileToByteArray;
import static org.apache.commons.lang3.StringUtils.substring;
import static org.apache.poi.hssf.usermodel.HSSFFont.FONT_ARIAL;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFDataFormat;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import static org.apache.poi.ss.usermodel.BorderStyle.THIN;
import static org.apache.poi.ss.usermodel.CellType.NUMERIC;
import static org.apache.poi.ss.usermodel.HorizontalAlignment.CENTER;
import static org.apache.poi.ss.usermodel.HorizontalAlignment.LEFT;
import static org.apache.poi.ss.usermodel.HorizontalAlignment.RIGHT;

/**
 *
 * @author fplacanica
 */
public class C_TransactionRegisterDetail {

    //column
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
    public static float[] columnWidths2 = new float[]{20f, 10f, 10f, 15f, 50f, 15f, 40f, 30f, 30f, 30f, 25f, 25f, 25f, 25f};

    /**
     *
     */
    public static float[] columnWidths3 = new float[]{5f, 20f, 50f, 10f, 10f, 15f, 10f, 10f};

    /**
     *
     */
    public static float[] columnWidths4 = new float[]{30f, 20f, 15f, 15f, 10f, 15f};

    /**
     *
     */
    public static float[] columnWidths5 = new float[]{30f, 20f, 15f, 15f, 10f, 15f};

    /**
     *
     */
    public static float[] columnWidths6 = new float[]{30f, 20f, 15f, 15f, 10f, 15f};

    public static final String intestazionePdf = "Maccorp Italiana S.p.A. P.I. 12951210157 - Registro Transazioni";
    Phrase vuoto = new Phrase("\n");

    Color c = new Color(23, 32, 43);

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
    public C_TransactionRegisterDetail() {

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
     * @param cmfb
     * @param colonne
     * @param progressivostart
     * @param pagestart
     * @return
     */
//    public String receipt_2020(String path, C_TransactionRegisterDetail_value cmfb, ArrayList<String> colonne, int progressivostart, int pagestart) {
//        try {
//
//            File pdffile = new File(path + Utility.generaId(50) + "C_TransactionRegisterDetail.pdf");
//            OutputStream ou = new FileOutputStream(pdffile);
//            Document document = new Document(PageSize.A4, 20, 20, 20, 20);
//            PdfWriter wr = PdfWriter.getInstance(document, ou);
//            document.open();
//
//            PdfPTable table = new PdfPTable(2);
//            table.setWidths(columnWidths0);
//            table.setWidthPercentage(100);
//            Phrase phrase1 = new Phrase();
//            phrase1.add(new Chunk(intestazionePdf, f3_bold));
//            PdfPCell cell1 = new PdfPCell(phrase1);
//            cell1.setBorder(Rectangle.NO_BORDER);
//            Paragraph pa1 = new Paragraph(new Phrase("", f3_bold));
//            pa1.setAlignment(Element.ALIGN_RIGHT);
//            PdfPCell cell2 = new PdfPCell(pa1);
//            cell2.setBorder(Rectangle.NO_BORDER);
//            cell2.setHorizontalAlignment(Element.ALIGN_RIGHT);
//
//            Phrase phrase4 = new Phrase();
//            phrase4.add(new Chunk("", f3_normal));
//            PdfPCell cell4 = new PdfPCell(phrase4);
//            cell4.setBorder(Rectangle.NO_BORDER);
//            table.addCell(cell1);
//            table.addCell(cell2);
//            table.addCell(cell4);
//            document.add(table);
//            vuoto.setFont(f3_normal);
//            document.add(vuoto);
//
//            ArrayList<C_TransactionRegisterDetail_value> dati = cmfb.getDati();
//
//            int startrow = 1;
//
//            if (dati.size() >= startrow) {
//
//                PdfPCell cell;
//                Phrase phraset;
//                PdfPCell cellt;
//                PdfPTable table3;
//
//                LineSeparator sep = new LineSeparator();
//                sep.setOffset(-2);
//                sep.setLineWidth((float) 0.5);
//
//                PdfPTable table4 = new PdfPTable(2);
//                table4.setWidths(columnWidths0);
//                table4.setWidthPercentage(100);
//
//                phraset = new Phrase();
//                phraset.add(new Chunk("", f3_bold));
//                cellt = new PdfPCell(phraset);
//                cellt.setHorizontalAlignment(Element.ALIGN_LEFT);
//                cellt.setBorder(Rectangle.BOTTOM);
//                table4.addCell(cellt);
//
//                phraset = new Phrase();
//                phraset.add(new Chunk("", f4_bold));
//                cellt = new PdfPCell(phraset);
//                cellt.setHorizontalAlignment(Element.ALIGN_LEFT);
//                cellt.setBorder(Rectangle.BOTTOM);
//                table4.addCell(cellt);
//
//                document.add(table4);
//
//                PdfPTable table2 = new PdfPTable(colonne.size());
//                table2.setWidths(columnWidths2);
//                table2.setWidthPercentage(100);
//
//                PdfPCell[] list = new PdfPCell[colonne.size()];
//                //mi scandisco le colonne
//                for (int c = 0; c < colonne.size(); c++) {
//                    Phrase phraset1 = new Phrase();
//                    phraset1.add(new Chunk(colonne.get(c), f4_bold));
//                    PdfPCell cellt1 = new PdfPCell(phraset1);
//                    cellt1.setHorizontalAlignment(Element.ALIGN_RIGHT);
//                    cellt1.setBorder(Rectangle.BOTTOM | Rectangle.TOP);
//                    cellt1.setFixedHeight(20f);
//                    cellt1.setBackgroundColor(BaseColor.LIGHT_GRAY);
//                    //   cellt1.setBorderWidth(0.7f);
//                    if (c == 4 || c == 5 || c == 6) {
//                        cellt1.setHorizontalAlignment(Element.ALIGN_LEFT);
//                    }
//                    list[c] = cellt1;
//                }
//
//                table3 = new PdfPTable(colonne.size());
//                table3.setWidths(columnWidths2);
//                table3.setWidthPercentage(100);
//
//                for (int z = 0; z < list.length; z++) {
//                    PdfPCell temp = (PdfPCell) (list[z]);
//                    table3.addCell(temp);
//                }
//
//                document.add(table3);
//
//                float availableSpace;
//
//                table4 = new PdfPTable(14);
//                table4.setWidths(columnWidths2);
//                table4.setWidthPercentage(100);
//
//                //for (int j = startrow - 1; j < dati.size(); j++) {
//                for (int j = 0; j < dati.size(); j++) {
//
//                    availableSpace = wr.getVerticalPosition(true) - document.bottomMargin();
//
//                    C_TransactionRegisterDetail_value actual = (C_TransactionRegisterDetail_value) dati.get(j);
//
//                    table4 = new PdfPTable(14);
//                    table4.setWidths(columnWidths2);
//                    table4.setWidthPercentage(100);
//
//                    phraset = new Phrase();
//                    phraset.add(new Chunk(String.valueOf(progressivostart + 1), f3_normal));
//                    cellt = new PdfPCell(phraset);
//                    cellt.setHorizontalAlignment(Element.ALIGN_RIGHT);
//                    cellt.setBorder(Rectangle.BOTTOM);
//                    table4.addCell(cellt);
//
//                    phraset = new Phrase();
//                    phraset.add(new Chunk(actual.getFiliale(), f3_normal));
//                    cellt = new PdfPCell(phraset);
//                    cellt.setHorizontalAlignment(Element.ALIGN_RIGHT);
//                    cellt.setBorder(Rectangle.BOTTOM);
//                    table4.addCell(cellt);
//
//                    phraset = new Phrase();
//                    phraset.add(new Chunk(actual.getTill(), f3_normal));
//                    cellt = new PdfPCell(phraset);
//                    cellt.setHorizontalAlignment(Element.ALIGN_RIGHT);
//                    cellt.setBorder(Rectangle.BOTTOM);
//                    table4.addCell(cellt);
//
//                    phraset = new Phrase();
//                    phraset.add(new Chunk(actual.getUser(), f3_normal));
//                    cellt = new PdfPCell(phraset);
//                    cellt.setHorizontalAlignment(Element.ALIGN_RIGHT);
//                    cellt.setBorder(Rectangle.BOTTOM);
//                    table4.addCell(cellt);
//
//                    phraset = new Phrase();
//                    phraset.add(new Chunk(actual.getDate(), f3_normal));
//                    cellt = new PdfPCell(phraset);
//                    cellt.setHorizontalAlignment(Element.ALIGN_LEFT);
//                    cellt.setBorder(Rectangle.BOTTOM);
//                    table4.addCell(cellt);
//
//                    phraset = new Phrase();
//                    phraset.add(new Chunk(actual.getCur(), f3_normal));
//                    cellt = new PdfPCell(phraset);
//                    cellt.setHorizontalAlignment(Element.ALIGN_LEFT);
//                    cellt.setBorder(Rectangle.BOTTOM);
//                    table4.addCell(cellt);
//
//                    phraset = new Phrase();
//                    phraset.add(new Chunk(actual.getKind(), f3_normal));
//                    cellt = new PdfPCell(phraset);
//                    cellt.setHorizontalAlignment(Element.ALIGN_LEFT);
//                    cellt.setBorder(Rectangle.BOTTOM);
//                    table4.addCell(cellt);
//
//                    phraset = new Phrase();
//                    phraset.add(new Chunk(Utility.formatMysqltoDisplay(actual.getAmountqty()), f3_normal));
//                    cellt = new PdfPCell(phraset);
//                    cellt.setHorizontalAlignment(Element.ALIGN_RIGHT);
//                    cellt.setBorder(Rectangle.BOTTOM);
//                    table4.addCell(cellt);
//
//                    phraset = new Phrase();
//                    phraset.add(new Chunk(Utility.formatMysqltoDisplay(actual.getRate()), f3_normal));
//                    cellt = new PdfPCell(phraset);
//                    cellt.setHorizontalAlignment(Element.ALIGN_RIGHT);
//                    cellt.setBorder(Rectangle.BOTTOM);
//                    table4.addCell(cellt);
//
//                    phraset = new Phrase();
//                    phraset.add(new Chunk(Utility.formatMysqltoDisplay(actual.getTotal()), f3_normal));
//                    cellt = new PdfPCell(phraset);
//                    cellt.setHorizontalAlignment(Element.ALIGN_RIGHT);
//                    cellt.setBorder(Rectangle.BOTTOM);
//                    table4.addCell(cellt);
//
//                    phraset = new Phrase();
//                    phraset.add(new Chunk(Utility.formatMysqltoDisplay(actual.getPerc()), f3_normal));
//                    cellt = new PdfPCell(phraset);
//                    cellt.setHorizontalAlignment(Element.ALIGN_RIGHT);
//                    cellt.setBorder(Rectangle.BOTTOM);
//                    table4.addCell(cellt);
//
//                    phraset = new Phrase();
//                    phraset.add(new Chunk(Utility.formatMysqltoDisplay(actual.getCommfee()), f3_normal));
//                    cellt = new PdfPCell(phraset);
//                    cellt.setHorizontalAlignment(Element.ALIGN_RIGHT);
//                    cellt.setBorder(Rectangle.BOTTOM);
//                    table4.addCell(cellt);
//
//                    phraset = new Phrase();
//                    phraset.add(new Chunk(Utility.formatMysqltoDisplay(actual.getRefundoff()), f3_normal));
//                    cellt = new PdfPCell(phraset);
//                    cellt.setHorizontalAlignment(Element.ALIGN_RIGHT);
//                    cellt.setBorder(Rectangle.BOTTOM);
//                    table4.addCell(cellt);
//
//                    phraset = new Phrase();
//                    phraset.add(new Chunk(Utility.formatMysqltoDisplay(actual.getPayinout()), f3_normal));
//                    cellt = new PdfPCell(phraset);
//                    cellt.setHorizontalAlignment(Element.ALIGN_RIGHT);
//                    cellt.setBorder(Rectangle.BOTTOM);
//                    table4.addCell(cellt);
//
//                    document.add(table4);
//
//                    if ((availableSpace < 80) || (j == (dati.size() - 1))) {
//
//                        table = new PdfPTable(3);
//
//                        table.setWidths(new int[]{24, 24, 2});
//                        table.getDefaultCell().setFixedHeight(10);
//                        cell = new PdfPCell();
//                        cell.setBorder(0);
//                        cell.setBorderWidthTop(1);
//                        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
//                        cell.setPhrase(new Phrase("", f3_normal));
//                        table.addCell(cell);
//                        String anno = StringUtils.substring(actual.getDate().split(" ")[0], 6);
//                        cell = new PdfPCell();
//                        cell.setBorder(0);
//                        cell.setBorderWidthTop(1);
//                        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//                        cell.setPhrase(new Phrase("Page " + anno + " / " + String.format("%d", pagestart + 1), f3_normal));
//                        table.addCell(cell);
//
//                        cell = new PdfPCell();
//                        cell.setBorder(0);
//                        cell.setBorderWidthTop(1);
//                        table.addCell(cell);
//                        table.setTotalWidth(document.getPageSize().getWidth()
//                                - document.leftMargin() - document.rightMargin());
//                        table.writeSelectedRows(0, +1, document.leftMargin(),
//                                document.bottomMargin(), wr.getDirectContent());
//
////                        document.add(table);
//                        // document.add(table4);
//                        pagestart++;
//
//                        document.newPage();
//                    }
//
//                    progressivostart++;
//
//                }
//
//            }
//
//            //chiusura documento
//            document.close();
//            wr.close();
//            ou.close();
////            System.out.println("rc.so.reportcentrale.C_TransactionRegisterDetail.receipt_2020() "+pdffile.getPath());
//            String base64 = new String(Base64.encodeBase64(FileUtils.readFileToByteArray(pdffile)));
//            pdffile.delete();
//            return base64;
//        } catch (DocumentException | IOException ex) {
//            Engine.insertTR("E", "System", Thread.currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
//        }
//        return null;
//    }
//
//    public String receipt(String path, C_TransactionRegisterDetail_value cmfb, ArrayList<String> colonne, int progressivostart, int pagestart) {
//
//        try {
//
//            File pdffile = new File(path + Utility.generaId(50) + "C_TransactionRegisterDetail.pdf");
//            OutputStream ou = new FileOutputStream(pdffile);
//            Document document = new Document(PageSize.A4.rotate(), 20, 20, 20, 20);
//            PdfWriter wr = PdfWriter.getInstance(document, ou);
//            document.open();
//
//            PdfPTable table = new PdfPTable(2);
//            table.setWidths(columnWidths0);
//            table.setWidthPercentage(100);
//            Phrase phrase1 = new Phrase();
//            phrase1.add(new Chunk(intestazionePdf, f3_bold));
//            PdfPCell cell1 = new PdfPCell(phrase1);
//            cell1.setBorder(Rectangle.NO_BORDER);
//            Paragraph pa1 = new Paragraph(new Phrase("", f3_bold));
//            pa1.setAlignment(Element.ALIGN_RIGHT);
//            PdfPCell cell2 = new PdfPCell(pa1);
//            cell2.setBorder(Rectangle.NO_BORDER);
//            cell2.setHorizontalAlignment(Element.ALIGN_RIGHT);
//
//            Phrase phrase4 = new Phrase();
//            phrase4.add(new Chunk("", f3_normal));
//            PdfPCell cell4 = new PdfPCell(phrase4);
//            cell4.setBorder(Rectangle.NO_BORDER);
//            table.addCell(cell1);
//            table.addCell(cell2);
//            table.addCell(cell4);
//            document.add(table);
//            vuoto.setFont(f3_normal);
//            document.add(vuoto);
//
//            ArrayList<C_TransactionRegisterDetail_value> dati = cmfb.getDati();
//
//            int startrow = 1;
//
//            if (dati.size() >= startrow) {
//
//                PdfPCell cell;
//                Phrase phraset;
//                PdfPCell cellt;
//                PdfPTable table3;
//
//                LineSeparator sep = new LineSeparator();
//                sep.setOffset(-2);
//                sep.setLineWidth((float) 0.5);
//
//                PdfPTable table4 = new PdfPTable(2);
//                table4.setWidths(columnWidths0);
//                table4.setWidthPercentage(100);
//
//                phraset = new Phrase();
//                phraset.add(new Chunk("", f3_bold));
//                cellt = new PdfPCell(phraset);
//                cellt.setHorizontalAlignment(Element.ALIGN_LEFT);
//                cellt.setBorder(Rectangle.BOTTOM);
//                table4.addCell(cellt);
//
//                phraset = new Phrase();
//                phraset.add(new Chunk("", f4_bold));
//                cellt = new PdfPCell(phraset);
//                cellt.setHorizontalAlignment(Element.ALIGN_LEFT);
//                cellt.setBorder(Rectangle.BOTTOM);
//                table4.addCell(cellt);
//
//                document.add(table4);
//
//                PdfPTable table2 = new PdfPTable(colonne.size());
//                table2.setWidths(columnWidths2);
//                table2.setWidthPercentage(100);
//
//                PdfPCell[] list = new PdfPCell[colonne.size()];
//                //mi scandisco le colonne
//                for (int c = 0; c < colonne.size(); c++) {
//                    Phrase phraset1 = new Phrase();
//                    phraset1.add(new Chunk(colonne.get(c), f4_bold));
//                    PdfPCell cellt1 = new PdfPCell(phraset1);
//                    cellt1.setHorizontalAlignment(Element.ALIGN_RIGHT);
//                    cellt1.setBorder(Rectangle.BOTTOM | Rectangle.TOP);
//                    cellt1.setFixedHeight(20f);
//                    cellt1.setBackgroundColor(BaseColor.LIGHT_GRAY);
//                    //   cellt1.setBorderWidth(0.7f);
//                    if (c == 4 || c == 5 || c == 6) {
//                        cellt1.setHorizontalAlignment(Element.ALIGN_LEFT);
//                    }
//                    list[c] = cellt1;
//                }
//
//                table3 = new PdfPTable(colonne.size());
//                table3.setWidths(columnWidths2);
//                table3.setWidthPercentage(100);
//
//                for (int z = 0; z < list.length; z++) {
//                    PdfPCell temp = (PdfPCell) (list[z]);
//                    table3.addCell(temp);
//                }
//
//                document.add(table3);
//
//                float availableSpace;
//
//                table4 = new PdfPTable(14);
//                table4.setWidths(columnWidths2);
//                table4.setWidthPercentage(100);
//
//                //for (int j = startrow - 1; j < dati.size(); j++) {
//                for (int j = 0; j < dati.size(); j++) {
//
//                    availableSpace = wr.getVerticalPosition(true) - document.bottomMargin();
//
//                    C_TransactionRegisterDetail_value actual = (C_TransactionRegisterDetail_value) dati.get(j);
//
//                    table4 = new PdfPTable(14);
//                    table4.setWidths(columnWidths2);
//                    table4.setWidthPercentage(100);
//
//                    phraset = new Phrase();
//                    phraset.add(new Chunk(String.valueOf(progressivostart + 1), f3_normal));
//                    cellt = new PdfPCell(phraset);
//                    cellt.setHorizontalAlignment(Element.ALIGN_RIGHT);
//                    cellt.setBorder(Rectangle.BOTTOM);
//                    table4.addCell(cellt);
//
//                    phraset = new Phrase();
//                    phraset.add(new Chunk(actual.getFiliale(), f3_normal));
//                    cellt = new PdfPCell(phraset);
//                    cellt.setHorizontalAlignment(Element.ALIGN_RIGHT);
//                    cellt.setBorder(Rectangle.BOTTOM);
//                    table4.addCell(cellt);
//
//                    phraset = new Phrase();
//                    phraset.add(new Chunk(actual.getTill(), f3_normal));
//                    cellt = new PdfPCell(phraset);
//                    cellt.setHorizontalAlignment(Element.ALIGN_RIGHT);
//                    cellt.setBorder(Rectangle.BOTTOM);
//                    table4.addCell(cellt);
//
//                    phraset = new Phrase();
//                    phraset.add(new Chunk(actual.getUser(), f3_normal));
//                    cellt = new PdfPCell(phraset);
//                    cellt.setHorizontalAlignment(Element.ALIGN_RIGHT);
//                    cellt.setBorder(Rectangle.BOTTOM);
//                    table4.addCell(cellt);
//
//                    phraset = new Phrase();
//                    phraset.add(new Chunk(actual.getDate(), f3_normal));
//                    cellt = new PdfPCell(phraset);
//                    cellt.setHorizontalAlignment(Element.ALIGN_LEFT);
//                    cellt.setBorder(Rectangle.BOTTOM);
//                    table4.addCell(cellt);
//
//                    phraset = new Phrase();
//                    phraset.add(new Chunk(actual.getCur(), f3_normal));
//                    cellt = new PdfPCell(phraset);
//                    cellt.setHorizontalAlignment(Element.ALIGN_LEFT);
//                    cellt.setBorder(Rectangle.BOTTOM);
//                    table4.addCell(cellt);
//
//                    phraset = new Phrase();
//                    phraset.add(new Chunk(actual.getKind(), f3_normal));
//                    cellt = new PdfPCell(phraset);
//                    cellt.setHorizontalAlignment(Element.ALIGN_LEFT);
//                    cellt.setBorder(Rectangle.BOTTOM);
//                    table4.addCell(cellt);
//
//                    phraset = new Phrase();
//                    phraset.add(new Chunk(Utility.formatMysqltoDisplay(actual.getAmountqty()), f3_normal));
//                    cellt = new PdfPCell(phraset);
//                    cellt.setHorizontalAlignment(Element.ALIGN_RIGHT);
//                    cellt.setBorder(Rectangle.BOTTOM);
//                    table4.addCell(cellt);
//
//                    phraset = new Phrase();
//                    phraset.add(new Chunk(Utility.formatMysqltoDisplay(actual.getRate()), f3_normal));
//                    cellt = new PdfPCell(phraset);
//                    cellt.setHorizontalAlignment(Element.ALIGN_RIGHT);
//                    cellt.setBorder(Rectangle.BOTTOM);
//                    table4.addCell(cellt);
//
//                    phraset = new Phrase();
//                    phraset.add(new Chunk(Utility.formatMysqltoDisplay(actual.getTotal()), f3_normal));
//                    cellt = new PdfPCell(phraset);
//                    cellt.setHorizontalAlignment(Element.ALIGN_RIGHT);
//                    cellt.setBorder(Rectangle.BOTTOM);
//                    table4.addCell(cellt);
//
//                    phraset = new Phrase();
//                    phraset.add(new Chunk(Utility.formatMysqltoDisplay(actual.getPerc()), f3_normal));
//                    cellt = new PdfPCell(phraset);
//                    cellt.setHorizontalAlignment(Element.ALIGN_RIGHT);
//                    cellt.setBorder(Rectangle.BOTTOM);
//                    table4.addCell(cellt);
//
//                    phraset = new Phrase();
//                    phraset.add(new Chunk(Utility.formatMysqltoDisplay(actual.getCommfee()), f3_normal));
//                    cellt = new PdfPCell(phraset);
//                    cellt.setHorizontalAlignment(Element.ALIGN_RIGHT);
//                    cellt.setBorder(Rectangle.BOTTOM);
//                    table4.addCell(cellt);
//
//                    phraset = new Phrase();
//                    phraset.add(new Chunk(Utility.formatMysqltoDisplay(actual.getRefundoff()), f3_normal));
//                    cellt = new PdfPCell(phraset);
//                    cellt.setHorizontalAlignment(Element.ALIGN_RIGHT);
//                    cellt.setBorder(Rectangle.BOTTOM);
//                    table4.addCell(cellt);
//
//                    phraset = new Phrase();
//                    phraset.add(new Chunk(Utility.formatMysqltoDisplay(actual.getPayinout()), f3_normal));
//                    cellt = new PdfPCell(phraset);
//                    cellt.setHorizontalAlignment(Element.ALIGN_RIGHT);
//                    cellt.setBorder(Rectangle.BOTTOM);
//                    table4.addCell(cellt);
//
//                    document.add(table4);
//
//                    if ((availableSpace < 80) || (j == (dati.size() - 1))) {
//
//                        table = new PdfPTable(3);
//
//                        table.setWidths(new int[]{24, 24, 2});
//                        table.getDefaultCell().setFixedHeight(10);
//                        cell = new PdfPCell();
//                        cell.setBorder(0);
//                        cell.setBorderWidthTop(1);
//                        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
//                        cell.setPhrase(new Phrase("", f3_normal));
//                        table.addCell(cell);
//
//                        cell = new PdfPCell();
//                        cell.setBorder(0);
//                        cell.setBorderWidthTop(1);
//                        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//                        cell.setPhrase(new Phrase(String.format("Page %d", pagestart + 1), f3_normal));
//                        table.addCell(cell);
//
//                        cell = new PdfPCell();
//                        cell.setBorder(0);
//                        cell.setBorderWidthTop(1);
//                        table.addCell(cell);
//                        table.setTotalWidth(document.getPageSize().getWidth()
//                                - document.leftMargin() - document.rightMargin());
//                        table.writeSelectedRows(0, +1, document.leftMargin(),
//                                document.bottomMargin(), wr.getDirectContent());
//
////                        document.add(table);
//                        // document.add(table4);
//                        pagestart++;
//
//                        document.newPage();
//                    }
//
//                    progressivostart++;
//
//                }
//
//            }
//
//            //chiusura documento
//            document.close();
//            wr.close();
//            ou.close();
//            String base64 = new String(Base64.encodeBase64(FileUtils.readFileToByteArray(pdffile)));
//            pdffile.delete();
//            return base64;
//        } catch (DocumentException | IOException ex) {
//            Engine.insertTR("E", "System", Thread.currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
//        }
//        return null;
//
//    }

    /**
     *
     * @param path
     * @param cmfb
     * @param colonne
     * @param progressivostart
     * @param pagestart
     * @return
     */
    public String receiptexcel(String path, C_TransactionRegisterDetail_value cmfb, ArrayList<String> colonne, int progressivostart, int pagestart) {

        try {

            File pdf = new File(path + generaId(50) + "C_TransactionRegisterDetail.xlsx");
            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet("C_TransactionRegisterDetail");
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
            style3.setAlignment(RIGHT);
            style3.setBorderTop(THIN);
            style3.setBorderBottom(THIN);

            XSSFCellStyle style3left = workbook.createCellStyle();
            style3left.setFont(font3);
            style3left.setAlignment(LEFT);
            style3left.setBorderTop(THIN);
            style3left.setBorderBottom(THIN);

            XSSFCellStyle style3center = workbook.createCellStyle();
            style3center.setFont(font3);
            style3center.setAlignment(CENTER);
            style3center.setBorderTop(THIN);
            style3center.setBorderBottom(THIN);

            XSSFFont font4 = workbook.createFont();
            font4.setFontName(FONT_ARIAL);
            font4.setFontHeightInPoints((short) 10);
            XSSFDataFormat DataFormat = (XSSFDataFormat) workbook.createDataFormat();

            XSSFCellStyle style4 = workbook.createCellStyle();
            style4.setAlignment(RIGHT);
            style4.setBorderTop(THIN);
            style4.setBorderBottom(THIN);

            XSSFCellStyle style4num = workbook.createCellStyle();
            style4num.setAlignment(RIGHT);
            style4num.setBorderTop(THIN);
            style4num.setBorderBottom(THIN);
            style4num.setDataFormat(DataFormat.getFormat(formatdataCell));

            XSSFCellStyle style4numR = workbook.createCellStyle();
            style4numR.setAlignment(RIGHT);
            style4numR.setBorderTop(THIN);
            style4numR.setBorderBottom(THIN);
            style4numR.setDataFormat(DataFormat.getFormat(formatdataCellRate));

            XSSFCellStyle style4INT = workbook.createCellStyle();

            style4INT.setDataFormat(DataFormat.getFormat(formatdataCellint));
            style4INT.setAlignment(RIGHT);
            style4INT.setBorderTop(THIN);
            style4INT.setBorderBottom(THIN);

            XSSFCellStyle style4left = workbook.createCellStyle();
            style4left.setAlignment(LEFT);
            style4left.setBorderTop(THIN);
            style4left.setBorderBottom(THIN);

            if (true) {

//                 Image image1 = Image.getInstance((("logocl.png")));
//            image1.scalePercent(60f);
//            document.add(image1);
                Row rowP = sheet.createRow((short) 1);

                Cell cl = rowP.createCell(1);
                cl.setCellStyle(style1);
                cl.setCellValue(intestazionePdf + " from " + cmfb.getDataDa() + " to  " + cmfb.getDataA());

            }

            //  document.add(table2);
            // document.add(sep);
            List<C_TransactionRegisterDetail_value> dati = cmfb.getDati();

            int startrow = 1;

            if (dati.size() >= startrow) {

                boolean ft = true;

//            float qta = 0, equiv = 0, comm = 0;
                if (ft) {

                    Row row66 = sheet.createRow((short) 5);

                    //mi scandisco le colonne
                    for (int j = 0; j < colonne.size(); j++) {
                        Cell cl7 = row66.createCell(j + 1);
                        cl7.setCellStyle(style3);
                        if (j == 4 || j == 5 || j == 6) {
                            cl7.setCellStyle(style3left);
                        }
                        cl7.setCellValue(colonne.get(j));
                    }
                }

                //for (int j = startrow - 1; j < dati.size(); j++) {
                int cntriga = 7;

                for (int j = 0; j < dati.size(); j++) {

                    cntriga++;
                    XSSFRow row6 = sheet.createRow(cntriga);

                    C_TransactionRegisterDetail_value actual = dati.get(j);

                    Cell f1bis = row6.createCell(1, NUMERIC);
                    f1bis.setCellStyle(style4INT);
                    f1bis.setCellValue((progressivostart + 1));

                    Cell f2 = row6.createCell(2);
                    f2.setCellStyle(style4);
                    f2.setCellValue(actual.getFiliale());

                    Cell f3 = row6.createCell(3);
                    f3.setCellStyle(style4);
                    f3.setCellValue(actual.getTill());

                    Cell f4 = row6.createCell(4);
                    f4.setCellStyle(style4);
                    f4.setCellValue(actual.getUser());

                    Cell f6 = row6.createCell(5);
                    f6.setCellStyle(style4left);
                    f6.setCellValue(actual.getDate());

                    Cell f7 = row6.createCell(6);
                    f7.setCellStyle(style4left);
                    f7.setCellValue(actual.getCur());

                    Cell f7bis = row6.createCell(7);
                    f7bis.setCellStyle(style4left);
                    f7bis.setCellValue(actual.getKind());

                    Cell f8 = row6.createCell(8, NUMERIC);
                    f8.setCellStyle(style4num);
                    f8.setCellValue(fd(actual.getAmountqty()));

                    Cell f9 = row6.createCell(9, NUMERIC);
                    f9.setCellStyle(style4numR);
                    f9.setCellValue(fd(actual.getRate()));

                    Cell f10 = row6.createCell(10, NUMERIC);
                    f10.setCellStyle(style4num);
                    f10.setCellValue(fd(actual.getTotal()));

                    Cell f11 = row6.createCell(11, NUMERIC);
                    f11.setCellStyle(style4num);
                    f11.setCellValue(fd(actual.getPerc()));

                    Cell f12 = row6.createCell(12, NUMERIC);
                    f12.setCellStyle(style4num);
                    f12.setCellValue(fd(actual.getCommfee()));

                    Cell f13 = row6.createCell(13, NUMERIC);
                    f13.setCellStyle(style4num);
                    f13.setCellValue(fd(actual.getRefundoff()));

                    Cell f14 = row6.createCell(14, NUMERIC);
                    f14.setCellStyle(style4num);
                    f14.setCellValue(fd(actual.getPayinout()));

                    progressivostart++;

                }

            }

            //chiusura documento
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

    public String receipt_2022(String path, C_TransactionRegisterDetail_value cmfb, ArrayList<String> colonne, int progressivostart, int pagestart) {
        try {
            File pdffile = new File(path + generaId() + "C_TransactionRegisterDetail.pdf");
            try (OutputStream ou = new FileOutputStream(pdffile)) {
                Document document = new Document(A4, 20, 20, 20, 20);
                PdfWriter wr = getInstance(document, ou);
                document.open();
                List<C_TransactionRegisterDetail_value> dati = cmfb.getDati();

                
                String anno = substring(dati.get(0).getDate().split(" ")[0], 6);
                PdfPTable table0 = new PdfPTable(2);
                table0.setWidths(columnWidths0);
                table0.setWidthPercentage(100);
                Phrase phrase1 = new Phrase();
                phrase1.add(new Chunk(intestazionePdf, f3_bold));
                PdfPCell cell1 = new PdfPCell(phrase1);
                cell1.setBorder(NO_BORDER);
                Paragraph pa1 = new Paragraph(new Phrase("Pagina " + anno + " / " + format("%d", pagestart + 1), f3_bold));
                pa1.setAlignment(ALIGN_RIGHT);
                PdfPCell cell2 = new PdfPCell(pa1);
                cell2.setBorder(NO_BORDER);
                cell2.setHorizontalAlignment(ALIGN_RIGHT);
                
                Phrase phrase4 = new Phrase();
                phrase4.add(new Chunk("", f3_normal));
                PdfPCell cell4 = new PdfPCell(phrase4);
                cell4.setBorder(NO_BORDER);
                table0.addCell(cell1);
                table0.addCell(cell2);
                table0.addCell(cell4);
                document.add(table0);
                vuoto.setFont(f3_normal);
                document.add(vuoto);
                
                
                
                
                int startrow = 1;
                
                if (dati.size() >= startrow) {
                    
                    Phrase phraset;
                    PdfPCell cellt;
                    PdfPTable table3;
                    
                    LineSeparator sep = new LineSeparator();
                    sep.setOffset(-2);
                    sep.setLineWidth((float) 0.5);
                    
                    PdfPTable table4 = new PdfPTable(2);
                    table4.setWidths(columnWidths0);
                    table4.setWidthPercentage(100);

                    phraset = new Phrase();
                    phraset.add(new Chunk("", f3_bold));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_LEFT);
                    cellt.setBorder(BOTTOM);
                    table4.addCell(cellt);

                    phraset = new Phrase();
                    phraset.add(new Chunk("", f4_bold));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_LEFT);
                    cellt.setBorder(BOTTOM);
                    table4.addCell(cellt);

                    document.add(table4);
                    
                    PdfPTable table2 = new PdfPTable(colonne.size());
                    table2.setWidths(columnWidths2);
                    table2.setWidthPercentage(100);
                    
                    PdfPCell[] list = new PdfPCell[colonne.size()];
                    //mi scandisco le colonne
                    for (int c1 = 0; c1 < colonne.size(); c1++) {
                        Phrase phraset1 = new Phrase();
                        phraset1.add(new Chunk(colonne.get(c1), f4_bold));
                        PdfPCell cellt1 = new PdfPCell(phraset1);
                        cellt1.setHorizontalAlignment(ALIGN_RIGHT);
                        cellt1.setBorder(BOTTOM | TOP);
                        cellt1.setFixedHeight(20f);
                        cellt1.setBackgroundColor(LIGHT_GRAY);
                        //   cellt1.setBorderWidth(0.7f);
                        if (c1 == 4 || c1 == 5 || c1 == 6) {
                            cellt1.setHorizontalAlignment(ALIGN_LEFT);
                        }
                        list[c1] = cellt1;
                    }

                    table3 = new PdfPTable(colonne.size());
                    table3.setWidths(columnWidths2);
                    table3.setWidthPercentage(100);
                    
                    for (PdfPCell list1 : list) {
                        PdfPCell temp = (PdfPCell) (list1);
                        table3.addCell(temp);
                    }
                    
                    document.add(table3);
                    
                    float availableSpace;
                    
                    table4 = new PdfPTable(14);
                    table4.setWidths(columnWidths2);
                    table4.setWidthPercentage(100);
                    
                    //for (int j = startrow - 1; j < dati.size(); j++) {
                    for (int j = 0; j < dati.size(); j++) {
                        
                        availableSpace = wr.getVerticalPosition(true) - document.bottomMargin();
                        
                        C_TransactionRegisterDetail_value actual = dati.get(j);
                        
                        table4 = new PdfPTable(14);
                        table4.setWidths(columnWidths2);
                        table4.setWidthPercentage(100);
                        
                        phraset = new Phrase();
                        phraset.add(new Chunk(valueOf(progressivostart + 1), f3_normal));
                        cellt = new PdfPCell(phraset);
                        cellt.setHorizontalAlignment(ALIGN_RIGHT);
                        cellt.setBorder(BOTTOM);
                        table4.addCell(cellt);
                        
                        phraset = new Phrase();
                        phraset.add(new Chunk(actual.getFiliale(), f3_normal));
                        cellt = new PdfPCell(phraset);
                        cellt.setHorizontalAlignment(ALIGN_RIGHT);
                        cellt.setBorder(BOTTOM);
                        table4.addCell(cellt);
                        
                        phraset = new Phrase();
                        phraset.add(new Chunk(actual.getTill(), f3_normal));
                        cellt = new PdfPCell(phraset);
                        cellt.setHorizontalAlignment(ALIGN_RIGHT);
                        cellt.setBorder(BOTTOM);
                        table4.addCell(cellt);
                        
                        phraset = new Phrase();
                        phraset.add(new Chunk(actual.getUser(), f3_normal));
                        cellt = new PdfPCell(phraset);
                        cellt.setHorizontalAlignment(ALIGN_RIGHT);
                        cellt.setBorder(BOTTOM);
                        table4.addCell(cellt);
                        
                        phraset = new Phrase();
                        phraset.add(new Chunk(actual.getDate(), f3_normal));
                        cellt = new PdfPCell(phraset);
                        cellt.setHorizontalAlignment(ALIGN_LEFT);
                        cellt.setBorder(BOTTOM);
                        table4.addCell(cellt);
                        
                        phraset = new Phrase();
                        phraset.add(new Chunk(actual.getCur(), f3_normal));
                        cellt = new PdfPCell(phraset);
                        cellt.setHorizontalAlignment(ALIGN_LEFT);
                        cellt.setBorder(BOTTOM);
                        table4.addCell(cellt);
                        
                        phraset = new Phrase();
                        phraset.add(new Chunk(actual.getKind(), f3_normal));
                        cellt = new PdfPCell(phraset);
                        cellt.setHorizontalAlignment(ALIGN_LEFT);
                        cellt.setBorder(BOTTOM);
                        table4.addCell(cellt);
                        
                        phraset = new Phrase();
                        phraset.add(new Chunk(formatMysqltoDisplay(actual.getAmountqty()), f3_normal));
                        cellt = new PdfPCell(phraset);
                        cellt.setHorizontalAlignment(ALIGN_RIGHT);
                        cellt.setBorder(BOTTOM);
                        table4.addCell(cellt);
                        
                        phraset = new Phrase();
                        phraset.add(new Chunk(formatMysqltoDisplay(actual.getRate()), f3_normal));
                        cellt = new PdfPCell(phraset);
                        cellt.setHorizontalAlignment(ALIGN_RIGHT);
                        cellt.setBorder(BOTTOM);
                        table4.addCell(cellt);
                        
                        phraset = new Phrase();
                        phraset.add(new Chunk(formatMysqltoDisplay(actual.getTotal()), f3_normal));
                        cellt = new PdfPCell(phraset);
                        cellt.setHorizontalAlignment(ALIGN_RIGHT);
                        cellt.setBorder(BOTTOM);
                        table4.addCell(cellt);
                        
                        phraset = new Phrase();
                        phraset.add(new Chunk(formatMysqltoDisplay(actual.getPerc()), f3_normal));
                        cellt = new PdfPCell(phraset);
                        cellt.setHorizontalAlignment(ALIGN_RIGHT);
                        cellt.setBorder(BOTTOM);
                        table4.addCell(cellt);
                        
                        phraset = new Phrase();
                        phraset.add(new Chunk(formatMysqltoDisplay(actual.getCommfee()), f3_normal));
                        cellt = new PdfPCell(phraset);
                        cellt.setHorizontalAlignment(ALIGN_RIGHT);
                        cellt.setBorder(BOTTOM);
                        table4.addCell(cellt);
                        
                        phraset = new Phrase();
                        phraset.add(new Chunk(formatMysqltoDisplay(actual.getRefundoff()), f3_normal));
                        cellt = new PdfPCell(phraset);
                        cellt.setHorizontalAlignment(ALIGN_RIGHT);
                        cellt.setBorder(BOTTOM);
                        table4.addCell(cellt);
                        
                        phraset = new Phrase();
                        phraset.add(new Chunk(formatMysqltoDisplay(actual.getPayinout()), f3_normal));
                        cellt = new PdfPCell(phraset);
                        cellt.setHorizontalAlignment(ALIGN_RIGHT);
                        cellt.setBorder(BOTTOM);
                        table4.addCell(cellt);
                        
                        document.add(table4);
                        
                        if ((availableSpace < 40) || (j == (dati.size() - 1))) {
                            pagestart++;
                            document.newPage();
                            if ((j != (dati.size() - 1))) {
                                table0 = new PdfPTable(2);
                                table0.setWidths(columnWidths0);
                                table0.setWidthPercentage(100);
                                phrase1 = new Phrase();
                                phrase1.add(new Chunk(intestazionePdf, f3_bold));
                                cell1 = new PdfPCell(phrase1);
                                cell1.setBorder(NO_BORDER);
                                pa1 = new Paragraph(new Phrase("Pagina " + anno + " / " + format("%d", pagestart + 1), f3_bold));
                                pa1.setAlignment(ALIGN_RIGHT);
                                cell2 = new PdfPCell(pa1);
                                cell2.setBorder(NO_BORDER);
                                cell2.setHorizontalAlignment(ALIGN_RIGHT);
                                phrase4 = new Phrase();
                                phrase4.add(new Chunk("", f3_normal));
                                cell4 = new PdfPCell(phrase4);
                                cell4.setBorder(NO_BORDER);
                                table0.addCell(cell1);
                                table0.addCell(cell2);
                                table0.addCell(cell4);
                                document.add(table0);
                                vuoto.setFont(f3_normal);
                                document.add(vuoto);
                                document.add(table3);
                            }
                        }
                        progressivostart++;
                    }
                }
                //chiusura documento
                document.close();
                wr.close();
            }
            String base64 = new String(encodeBase64(readFileToByteArray(pdffile)));
            pdffile.delete();
            return base64;
        } catch (DocumentException | IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }
    
}
