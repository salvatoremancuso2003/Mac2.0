/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rc.so.report;

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
import static com.itextpdf.text.Rectangle.NO_BORDER;
import static com.itextpdf.text.Rectangle.TOP;
import static com.itextpdf.text.pdf.BaseFont.HELVETICA;
import static com.itextpdf.text.pdf.BaseFont.WINANSI;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import static com.itextpdf.text.pdf.PdfWriter.getInstance;
import com.itextpdf.text.pdf.draw.LineSeparator;
import static rc.so.util.Engine.insertTR;
import static rc.so.util.Utility.fd;
import static rc.so.util.Utility.formatDoubleforMysql;
import static rc.so.util.Utility.formatMysqltoDisplay;
import static rc.so.util.Utility.generaId;
import static rc.so.util.Utility.roundDoubleandFormat;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import static java.lang.Thread.currentThread;
import java.util.ArrayList;
import static org.apache.commons.codec.binary.Base64.encodeBase64;
import static org.apache.commons.io.FileUtils.readFileToByteArray;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import static org.apache.poi.hssf.usermodel.HSSFFont.FONT_ARIAL;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import static org.apache.poi.ss.usermodel.BorderStyle.MEDIUM;
import static org.apache.poi.ss.usermodel.BorderStyle.THIN;
import org.apache.poi.ss.usermodel.Cell;
import static org.apache.poi.ss.usermodel.HorizontalAlignment.LEFT;
import static org.apache.poi.ss.usermodel.HorizontalAlignment.RIGHT;
import org.apache.poi.ss.usermodel.Row;

/**
 *
 * @author rcosco
 */
public class Openclose_Anal {

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
    public static float[] columnWidths2 = new float[]{10f, 10f, 25f, 35f};

    /**
     *
     */
    public static float[] columnWidths4 = new float[]{20f, 25f, 35f};

    /**
     *
     */
    public static float[] columnWidths5 = new float[]{50f, 25f, 35f};

    /**
     *
     */
    public static float[] columnWidths3 = new float[]{15f, 10f, 15f, 10f, 15f, 15f};
    final String intestazionePdf = "Open / Close Analytical";
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
     * Costructor
     */
    public Openclose_Anal() {

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
     * @param osp
     * @param ocsa
     * @param ocsapos
     * @param ocsabank
     * @param colonne
     * @return
     */
    public String receipt(String path, Openclose_Anal_value osp, ArrayList<Openclose_Anal_value_stock> ocsa,
            ArrayList<Openclose_Anal_value_stock> ocsapos,
            ArrayList<Openclose_Anal_value_stock> ocsabank, ArrayList<String> colonne) {

        try {
            File pdf = new File(path + generaId(50) + "OpenCloseAnal.pdf");
            Document document = new Document(A4, 20, 20, 20, 20);
            try(OutputStream ou = new FileOutputStream(pdf)){
            PdfWriter wr = getInstance(document, ou);
            document.open();

            PdfPTable table = new PdfPTable(2);
            table.setWidths(columnWidths0);
            table.setWidthPercentage(100);

            Phrase phrase1 = new Phrase();
            phrase1.add(new Chunk(intestazionePdf, f3_bold));
            PdfPCell cell1 = new PdfPCell(phrase1);
            cell1.setBorder(NO_BORDER);

            Paragraph pa1 = new Paragraph(new Phrase(" ", f2_normal));
            pa1.setAlignment(ALIGN_RIGHT);
            PdfPCell cell2 = new PdfPCell(pa1);
            cell2.setBorder(NO_BORDER);
            cell2.setHorizontalAlignment(ALIGN_RIGHT);

            Phrase phrase3 = new Phrase();
            phrase3.add(new Chunk("\n " + osp.getId_filiale() + " " + osp.getDe_filiale(), f3_normal));
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

            table = new PdfPTable(6);
            table.setWidths(columnWidths3);
            table.setWidthPercentage(100);

            phrase1 = new Phrase();
            phrase1.add(new Chunk("Operation ", f4_bold));
            cell1 = new PdfPCell(phrase1);
            cell1.setBorder(NO_BORDER);
            cell1.setHorizontalAlignment(ALIGN_RIGHT);
            table.addCell(cell1);

            phrase1 = new Phrase();
            phrase1.add(new Chunk("Type ", f4_bold));
            cell1 = new PdfPCell(phrase1);
            cell1.setBorder(NO_BORDER);
            cell1.setHorizontalAlignment(ALIGN_LEFT);
            table.addCell(cell1);

            phrase1 = new Phrase();
            phrase1.add(new Chunk("Date ", f4_bold));
            cell1 = new PdfPCell(phrase1);
            cell1.setBorder(NO_BORDER);
            cell1.setHorizontalAlignment(ALIGN_LEFT);
            table.addCell(cell1);

            phrase1 = new Phrase();
            phrase1.add(new Chunk("User ", f4_bold));
            cell1 = new PdfPCell(phrase1);
            cell1.setBorder(NO_BORDER);
            cell1.setHorizontalAlignment(ALIGN_RIGHT);
            table.addCell(cell1);

            phrase1 = new Phrase();
            phrase1.add(new Chunk("Safe/Till ", f4_bold));
            cell1 = new PdfPCell(phrase1);
            cell1.setBorder(NO_BORDER);
            cell1.setHorizontalAlignment(ALIGN_RIGHT);
            table.addCell(cell1);

            phrase1 = new Phrase();
            phrase1.add(new Chunk("No. Error ", f4_bold));
            cell1 = new PdfPCell(phrase1);
            cell1.setBorder(NO_BORDER);
            cell1.setHorizontalAlignment(ALIGN_RIGHT);
            table.addCell(cell1);

            phrase1 = new Phrase();
            phrase1.add(new Chunk(osp.getOperazione(), f3_normal));
            cell1 = new PdfPCell(phrase1);
            cell1.setBorder(BOX);
            cell1.setHorizontalAlignment(ALIGN_RIGHT);
            table.addCell(cell1);

            String tipoesteso;
            if (osp.getTipo().trim().startsWith("C")) {
                tipoesteso = "Close";
            } else {
                tipoesteso = "Open";
            }

            phrase1 = new Phrase();
            phrase1.add(new Chunk(tipoesteso, f3_normal));
            cell1 = new PdfPCell(phrase1);
            cell1.setBorder(BOX);
            cell1.setHorizontalAlignment(ALIGN_LEFT);
            table.addCell(cell1);

            phrase1 = new Phrase();
            phrase1.add(new Chunk(osp.getData(), f3_normal));
            cell1 = new PdfPCell(phrase1);
            cell1.setBorder(BOX);
            cell1.setHorizontalAlignment(ALIGN_LEFT);
            table.addCell(cell1);

            phrase1 = new Phrase();
            phrase1.add(new Chunk(osp.getUser(), f3_normal));
            cell1 = new PdfPCell(phrase1);
            cell1.setBorder(BOX);
            cell1.setHorizontalAlignment(ALIGN_RIGHT);
            table.addCell(cell1);

            phrase1 = new Phrase();
            phrase1.add(new Chunk(osp.getSafetill(), f3_normal));
            cell1 = new PdfPCell(phrase1);
            cell1.setBorder(BOX);
            cell1.setHorizontalAlignment(ALIGN_RIGHT);
            table.addCell(cell1);

            phrase1 = new Phrase();
            phrase1.add(new Chunk(osp.getNumerrori(), f3_normal));
            cell1 = new PdfPCell(phrase1);
            cell1.setBorder(BOX);
            cell1.setHorizontalAlignment(ALIGN_RIGHT);
            table.addCell(cell1);

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
                //cellt1.setBorder(Rectangle.BOTTOM);
                cellt1.setBorderWidth(0.7f);
                if (j == 0) {
                    cellt1.setHorizontalAlignment(ALIGN_LEFT);
                }

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
            sep.setOffset(-2);
            sep.setLineWidth((float) 0.5);

            //  document.add(table2);
            //  document.add(sep);
            ArrayList<Openclose_Anal_value> dati = osp.getDati();
            PdfPCell cell;
            Phrase phraset;
            PdfPCell cellt;

            table3 = new PdfPTable(colonne.size());
            table3.setWidths(columnWidths2);
            table3.setWidthPercentage(100);

            double totalecop = 0; //totale controvalore della valuta locale
            double totalefx = 0;//totale controvalore delle valute diverse dalla valuta locale
            double totalegenerale = 0; //totale generale

            for (int i = 0; i < dati.size(); i++) {
                Openclose_Anal_value temp = dati.get(i);

                phraset = new Phrase();
                phraset.add(new Chunk(temp.getValuta(), f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_LEFT);
                cellt.setBorder(BOTTOM);
                table3.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(temp.getSuppporto(), f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table3.addCell(cellt);

                phraset = new Phrase();

                phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(fd(temp.getNumsupporti()), 0)), f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table3.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(temp.getImporto()), f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table3.addCell(cellt);

            }

            document.add(table3);

            vuoto.setFont(f3_normal);
            document.add(vuoto);

            //tabella POS
            table2 = new PdfPTable(3);
            table2.setWidths(columnWidths5);
            table2.setWidthPercentage(100);

            if (ocsapos.size() > 0) {

                Phrase phraset1 = new Phrase();
                phraset1.add(new Chunk("POS", f4_bold));
                PdfPCell cellt1 = new PdfPCell(phraset1);
                cellt1.setHorizontalAlignment(ALIGN_LEFT);
                cellt1.setBorder(BOTTOM | TOP);
                cellt1.setFixedHeight(20f);
                cellt1.setBackgroundColor(LIGHT_GRAY);
                cellt1.setBorderWidth(0.7f);
                table2.addCell(cellt1);

                phraset1 = new Phrase();
                phraset1.add(new Chunk("Quantity", f4_bold));
                cellt1 = new PdfPCell(phraset1);
                cellt1.setHorizontalAlignment(ALIGN_RIGHT);
                cellt1.setBorder(BOTTOM | TOP);
                cellt1.setFixedHeight(20f);
                cellt1.setBackgroundColor(LIGHT_GRAY);
                cellt1.setBorderWidth(0.7f);
                table2.addCell(cellt1);

                phraset1 = new Phrase();
                phraset1.add(new Chunk("Total", f4_bold));
                cellt1 = new PdfPCell(phraset1);
                cellt1.setHorizontalAlignment(ALIGN_RIGHT);
                cellt1.setBorder(BOTTOM | TOP);
                cellt1.setFixedHeight(20f);
                cellt1.setBackgroundColor(LIGHT_GRAY);
                cellt1.setBorderWidth(0.7f);
                table2.addCell(cellt1);

                document.add(table2);

                table3 = new PdfPTable(colonne.size());
                table3.setWidths(columnWidths2);
                table3.setWidthPercentage(100);

                document.add(table3);

                sep = new LineSeparator();
                sep.setOffset(-2);
                sep.setLineWidth((float) 0.5);

                table3 = new PdfPTable(3);
                table3.setWidths(columnWidths5);
                table3.setWidthPercentage(100);

                for (int i = 0; i < ocsapos.size(); i++) {
                    Openclose_Anal_value_stock temp = (Openclose_Anal_value_stock) ocsapos.get(i);

//                    if (list_oc_pos.get(i)[2].equals("04") && Constant.is_CZ) {
//                                                                String ba1 = list_oc_pos.get(i)[3].split("-")[0];
//                                                                String ba2 = list_oc_pos.get(i)[3].split("-")[1];
//                                                                formbacc = "<b>"+ba1+"</b> "+Utility.formatAL(ba1, cclist, 1)+" - <b>"+ba2+"</b> "+Engine.formatALCurrency(ba2, array_all_currency);
//                                                            }
                    phraset = new Phrase();
                    phraset.add(new Chunk(temp.getCategory(), f3_normal));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_LEFT);
                    cellt.setBorder(BOTTOM);
                    table3.addCell(cellt);

                    phraset = new Phrase();

                    phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(fd(formatDoubleforMysql(temp.getQuantity())), 0)), f3_normal));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setBorder(BOTTOM);
                    table3.addCell(cellt);

                    phraset = new Phrase();
                    phraset.add(new Chunk((temp.getAmount()), f3_normal));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setBorder(BOTTOM);
                    table3.addCell(cellt);

                }

                document.add(table3);

            }
            document.add(vuoto);

            //tabella bank Account
            table2 = new PdfPTable(3);
            table2.setWidths(columnWidths4);
            table2.setWidthPercentage(100);

            if (ocsabank.size() > 0) {

                Phrase phraset1 = new Phrase();
                phraset1.add(new Chunk("Bank Account", f4_bold));
                PdfPCell cellt1 = new PdfPCell(phraset1);
                cellt1.setHorizontalAlignment(ALIGN_LEFT);
                cellt1.setBorder(BOTTOM | TOP);
                cellt1.setFixedHeight(20f);
                cellt1.setBackgroundColor(LIGHT_GRAY);
                cellt1.setBorderWidth(0.7f);
                table2.addCell(cellt1);

                phraset1 = new Phrase();
                phraset1.add(new Chunk("Quantity", f4_bold));
                cellt1 = new PdfPCell(phraset1);
                cellt1.setHorizontalAlignment(ALIGN_RIGHT);
                cellt1.setBorder(BOTTOM | TOP);
                cellt1.setFixedHeight(20f);
                cellt1.setBackgroundColor(LIGHT_GRAY);
                cellt1.setBorderWidth(0.7f);
                table2.addCell(cellt1);

                phraset1 = new Phrase();
                phraset1.add(new Chunk("Total", f4_bold));
                cellt1 = new PdfPCell(phraset1);
                cellt1.setHorizontalAlignment(ALIGN_RIGHT);
                cellt1.setBorder(BOTTOM | TOP);
                cellt1.setFixedHeight(20f);
                cellt1.setBackgroundColor(LIGHT_GRAY);
                cellt1.setBorderWidth(0.7f);
                table2.addCell(cellt1);

                document.add(table2);

                table3 = new PdfPTable(colonne.size());
                table3.setWidths(columnWidths2);
                table3.setWidthPercentage(100);

                document.add(table3);

                sep = new LineSeparator();
                sep.setOffset(-2);
                sep.setLineWidth((float) 0.5);

                table3 = new PdfPTable(3);
                table3.setWidths(columnWidths4);
                table3.setWidthPercentage(100);

                for (int i = 0; i < ocsabank.size(); i++) {
                    Openclose_Anal_value_stock temp = (Openclose_Anal_value_stock) ocsabank.get(i);

                    phraset = new Phrase();
                    phraset.add(new Chunk(temp.getCategory(), f3_normal));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_LEFT);
                    cellt.setBorder(BOTTOM);
                    table3.addCell(cellt);

                    phraset = new Phrase();
                    phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(fd(formatDoubleforMysql(temp.getQuantity())), 0)), f3_normal));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setBorder(BOTTOM);
                    table3.addCell(cellt);

                    phraset = new Phrase();
                    phraset.add(new Chunk((temp.getAmount()), f3_normal));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setBorder(BOTTOM);
                    table3.addCell(cellt);

                }

                document.add(table3);
            }
            document.add(vuoto);

            //tabella no change
            table2 = new PdfPTable(3);
            table2.setWidths(columnWidths4);
            table2.setWidthPercentage(100);

            if (ocsa.size() > 0) {

                Phrase phraset1 = new Phrase();
                phraset1.add(new Chunk("Category", f4_bold));
                PdfPCell cellt1 = new PdfPCell(phraset1);
                cellt1.setHorizontalAlignment(ALIGN_LEFT);
                cellt1.setBorder(BOTTOM | TOP);
                cellt1.setFixedHeight(20f);
                cellt1.setBackgroundColor(LIGHT_GRAY);
                cellt1.setBorderWidth(0.7f);
                table2.addCell(cellt1);

                phraset1 = new Phrase();
                phraset1.add(new Chunk("Quantity", f4_bold));
                cellt1 = new PdfPCell(phraset1);
                cellt1.setHorizontalAlignment(ALIGN_RIGHT);
                cellt1.setBorder(BOTTOM | TOP);
                cellt1.setFixedHeight(20f);
                cellt1.setBackgroundColor(LIGHT_GRAY);
                cellt1.setBorderWidth(0.7f);
                table2.addCell(cellt1);

                phraset1 = new Phrase();
                phraset1.add(new Chunk("Total", f4_bold));
                cellt1 = new PdfPCell(phraset1);
                cellt1.setHorizontalAlignment(ALIGN_RIGHT);
                cellt1.setBorder(BOTTOM | TOP);
                cellt1.setFixedHeight(20f);
                cellt1.setBackgroundColor(LIGHT_GRAY);
                cellt1.setBorderWidth(0.7f);
                table2.addCell(cellt1);

                document.add(table2);

                table3 = new PdfPTable(colonne.size());
                table3.setWidths(columnWidths2);
                table3.setWidthPercentage(100);

                document.add(table3);

                sep = new LineSeparator();
                sep.setOffset(-2);
                sep.setLineWidth((float) 0.5);

                //  document.add(table2);
                //  document.add(sep);
                table3 = new PdfPTable(3);
                table3.setWidths(columnWidths4);
                table3.setWidthPercentage(100);

                for (int i = 0; i < ocsa.size(); i++) {
                    Openclose_Anal_value_stock temp = (Openclose_Anal_value_stock) ocsa.get(i);

                    phraset = new Phrase();
                    phraset.add(new Chunk(temp.getCategory(), f3_normal));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_LEFT);
                    cellt.setBorder(BOTTOM);
                    table3.addCell(cellt);

                    phraset = new Phrase();
                    phraset.add(new Chunk(formatMysqltoDisplay(temp.getQuantity()), f3_normal));
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

                }

                document.add(table3);

            }
            //fine tabella no change
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
     * @param osp
     * @param ocsa
     * @param ocsapos
     * @param ocsabank
     * @param colonne
     * @return
     */
    public String receiptExcel(String path, Openclose_Anal_value osp, ArrayList<Openclose_Anal_value_stock> ocsa, ArrayList<Openclose_Anal_value_stock> ocsapos, ArrayList<Openclose_Anal_value_stock> ocsabank, ArrayList<String> colonne) {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("OpenClose");
        try {
            //CREAZIONE FONT
            HSSFFont font = workbook.createFont();
            font.setFontName(FONT_ARIAL);
            font.setFontHeightInPoints((short) 12);
            font.setBold(true);

            HSSFCellStyle style1 = (HSSFCellStyle) workbook.createCellStyle();
            style1.setFont(font);

            HSSFFont font2 = workbook.createFont();
            font2.setFontName(FONT_ARIAL);
            font2.setFontHeightInPoints((short) 10);
            font2.setBold(true);

            HSSFCellStyle style2 = (HSSFCellStyle) workbook.createCellStyle();
            style2.setFont(font2);
            style2.setBorderTop(MEDIUM);
            style2.setBorderBottom(MEDIUM);
            style2.setAlignment(RIGHT);

            HSSFCellStyle style2left = (HSSFCellStyle) workbook.createCellStyle();
            style2left.setFont(font2);
            style2left.setBorderTop(MEDIUM);
            style2left.setBorderBottom(MEDIUM);
            style2left.setAlignment(LEFT);

            HSSFFont font3 = workbook.createFont();
            font3.setFontName(FONT_ARIAL);
            font3.setFontHeightInPoints((short) 10);
            font3.setBold(true);

            HSSFCellStyle style3 = (HSSFCellStyle) workbook.createCellStyle();
            style3.setFont(font3);
            style3.setAlignment(RIGHT);

            HSSFCellStyle style3left = (HSSFCellStyle) workbook.createCellStyle();
            style3left.setFont(font3);
            style3left.setAlignment(LEFT);

            HSSFFont font4 = workbook.createFont();
            font4.setFontName(FONT_ARIAL);
            font4.setFontHeightInPoints((short) 10);

            HSSFCellStyle style4 = (HSSFCellStyle) workbook.createCellStyle();
            style4.setAlignment(RIGHT);
            style4.setBorderTop(THIN);
            style4.setBorderBottom(THIN);

            HSSFCellStyle style4left = (HSSFCellStyle) workbook.createCellStyle();
            style4left.setAlignment(LEFT);
            style4left.setBorderTop(THIN);
            style4left.setBorderBottom(THIN);

            HSSFFont font5 = workbook.createFont();
            font5.setFontName(FONT_ARIAL);
            font5.setFontHeightInPoints((short) 10);
            //CON TUTTI I BORDI
            HSSFCellStyle style5 = (HSSFCellStyle) workbook.createCellStyle();
            style5.setAlignment(RIGHT);
            style5.setBorderTop(THIN);
            style5.setBorderBottom(THIN);
            style5.setBorderLeft(THIN);
            style5.setBorderRight(THIN);

            HSSFCellStyle style5left = (HSSFCellStyle) workbook.createCellStyle();
            style5left.setAlignment(LEFT);
            style5left.setBorderTop(THIN);
            style5left.setBorderBottom(THIN);
            style5left.setBorderLeft(THIN);
            style5left.setBorderRight(THIN);

            //FINE CREAZIONE FONT
            Row rowP = sheet.createRow((short) 1);
            Cell cl = rowP.createCell(1);
            cl.setCellStyle(style1);
            cl.setCellValue(intestazionePdf);
            Row row = sheet.createRow((short) 3);
            row.createCell(1).setCellValue(osp.getId_filiale() + " " + osp.getDe_filiale());
            row = sheet.createRow((short) 5);
            Cell cl3 = row.createCell(1);
            cl3.setCellStyle(style3);
            cl3.setCellValue("Operation");
            Cell cl4 = row.createCell(2);
            cl4.setCellStyle(style3left);
            cl4.setCellValue("Type");
            Cell cl5 = row.createCell(3);
            cl5.setCellStyle(style3left);
            cl5.setCellValue("Date");
            Cell cl6 = row.createCell(4);
            cl6.setCellStyle(style3);
            cl6.setCellValue("User");
            Cell cl7 = row.createCell(5);
            cl7.setCellStyle(style3);
            cl7.setCellValue("Safe/Till");
            Cell cl8 = row.createCell(6);
            cl8.setCellStyle(style3);
            cl8.setCellValue("No.Error");
            String tipoestesoval;
            if (osp.getTipo().startsWith("C")) {
                tipoestesoval = "Close";
            } else {
                tipoestesoval = "Open";
            }
            ArrayList<Openclose_Anal_value> datiosp = osp.getDati();
            row = sheet.createRow((short) 6);
            Cell cl9 = row.createCell(1);
            cl9.setCellStyle(style5);
            cl9.setCellValue(osp.getOperazione());
            Cell cl10 = row.createCell(2);
            cl10.setCellStyle(style5left);
            cl10.setCellValue(tipoestesoval);
            Cell cl11 = row.createCell(3);
            cl11.setCellStyle(style5left);
            cl11.setCellValue(osp.getData());
            Cell cl12 = row.createCell(4);
            cl12.setCellStyle(style5);
            cl12.setCellValue(osp.getUser());
            Cell cl13 = row.createCell(5);
            cl13.setCellStyle(style5);
            cl13.setCellValue(osp.getSafetill());
            Cell cl14 = row.createCell(6);
            cl14.setCellStyle(style5);
            cl14.setCellValue(osp.getNumerrori());
            row = sheet.createRow((short) 8);
            Cell cl15 = row.createCell(1);
            cl15.setCellStyle(style2left);
            Cell cl16 = row.createCell(2);
            Cell cl17 = row.createCell(3);
            Cell cl18 = row.createCell(4);
//            cl15.setCellStyle(style2);
            cl16.setCellStyle(style2);
            cl17.setCellStyle(style2);
            cl18.setCellStyle(style2);
            cl15.setCellValue("Currency");
            cl16.setCellValue("Kind");
            cl17.setCellValue("Quantity");
            cl18.setCellValue("Total");
            int riga = 9;
            for (int i = 0; i < datiosp.size(); i++) {
                row = sheet.createRow((short) riga);
                Openclose_Anal_value temp = datiosp.get(i);
                Cell a = row.createCell(1);
                a.setCellStyle(style4left);
                a.setCellValue(temp.getValuta());
                Cell b = row.createCell(2);
                b.setCellStyle(style4);
                b.setCellValue(temp.getSuppporto());
                Cell c = row.createCell(3);
                c.setCellStyle(style4);
                c.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(fd(temp.getNumsupporti()), 0)));
                Cell d = row.createCell(4);
                d.setCellStyle(style4);
                d.setCellValue(formatMysqltoDisplay(temp.getImporto()));
                riga++;
            }

            riga++;

            if (ocsapos.size() > 0) {

                row = sheet.createRow((short) riga);
                Cell cl19 = row.createCell(1);
                Cell cl20 = row.createCell(2);
                Cell cl21 = row.createCell(3);
                cl19.setCellStyle(style2left);
//                cl19.setCellStyle(style2);
                cl20.setCellStyle(style2);
                cl21.setCellStyle(style2);
                cl19.setCellValue("POS");
                cl20.setCellValue("Quantity");
                cl21.setCellValue("Total");
                riga++;
                for (int i = 0; i < ocsapos.size(); i++) {
                    Row row2 = sheet.createRow((short) riga);
                    Openclose_Anal_value_stock temp = (Openclose_Anal_value_stock) ocsapos.get(i);
                    Cell cl22 = row2.createCell(1);
                    Cell cl23 = row2.createCell(2);
                    Cell cl24 = row2.createCell(3);
                    cl22.setCellStyle(style4left);
                    cl23.setCellStyle(style4);
                    cl24.setCellStyle(style4);
                    cl22.setCellValue(temp.getCategory());
                    cl23.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(fd(formatDoubleforMysql(temp.getQuantity())), 0)));

                    cl24.setCellValue((temp.getAmount()));
                    riga++;
                }

            }

            riga++;

            if (ocsabank.size() > 0) {

                row = sheet.createRow((short) riga);
                Cell cl19b = row.createCell(1);
                Cell cl20b = row.createCell(2);
                Cell cl21b = row.createCell(3);
                cl19b.setCellStyle(style2left);
                cl20b.setCellStyle(style2);
                cl21b.setCellStyle(style2);
                cl19b.setCellValue("Bank Account");
                cl20b.setCellValue("Quantity");
                cl21b.setCellValue("Total");
                riga++;
                for (int i = 0; i < ocsabank.size(); i++) {
                    Row row2 = sheet.createRow((short) riga);
                    Openclose_Anal_value_stock temp = (Openclose_Anal_value_stock) ocsabank.get(i);
                    Cell cl22 = row2.createCell(1);
                    Cell cl23 = row2.createCell(2);
                    Cell cl24 = row2.createCell(3);
                    cl22.setCellStyle(style4left);
                    cl23.setCellStyle(style4);
                    cl24.setCellStyle(style4);
                    cl22.setCellValue(temp.getCategory());
                    cl23.setCellValue(temp.getQuantity());
                    cl24.setCellValue(formatMysqltoDisplay(temp.getAmount()));
                    riga++;
                }

            }

            if (ocsa.size() > 0) {

                riga++;
                row = sheet.createRow((short) riga);
                Cell cl19s = row.createCell(1);
                Cell cl20s = row.createCell(2);
                Cell cl21s = row.createCell(3);
                cl19s.setCellStyle(style2left);
                cl20s.setCellStyle(style2);
                cl21s.setCellStyle(style2);
                cl19s.setCellValue("Category");
                cl20s.setCellValue("Quantity");
                cl21s.setCellValue("Total");
                riga++;
                for (int i = 0; i < ocsa.size(); i++) {
                    Row row2 = sheet.createRow((short) riga);
                    Openclose_Anal_value_stock temp = (Openclose_Anal_value_stock) ocsa.get(i);
                    Cell cl22 = row2.createCell(1);
                    Cell cl23 = row2.createCell(2);
                    Cell cl24 = row2.createCell(3);
                    cl22.setCellStyle(style4left);
                    cl23.setCellStyle(style4);
                    cl24.setCellStyle(style4);
                    cl22.setCellValue(temp.getCategory());
                    cl23.setCellValue(temp.getQuantity());
                    cl24.setCellValue(formatMysqltoDisplay(temp.getAmount()));
                    riga++;
                }

            }
            sheet.autoSizeColumn(0);
            sheet.autoSizeColumn(1);
            sheet.autoSizeColumn(2);
            sheet.autoSizeColumn(3);
            sheet.autoSizeColumn(4);
            sheet.autoSizeColumn(5);
            sheet.autoSizeColumn(6);
            File f = new File(path + generaId(50) + "OpenCloseAnal.xls");
            try (FileOutputStream out = new FileOutputStream(f)) {
                workbook.write(out);
            }
            String base64 = new String(encodeBase64(readFileToByteArray(f)));
            f.delete();
            return base64;
        } catch (IOException ex) {
            insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
        }
        return null;
    }

}
