/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rc.so.report;

import com.itextpdf.text.Chunk;
import static com.itextpdf.text.Chunk.TABBING;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import static com.itextpdf.text.Element.ALIGN_BOTTOM;
import static com.itextpdf.text.Element.ALIGN_CENTER;
import static com.itextpdf.text.Element.ALIGN_LEFT;
import static com.itextpdf.text.Element.ALIGN_MIDDLE;
import static com.itextpdf.text.Element.ALIGN_RIGHT;
import com.itextpdf.text.Font;
import static com.itextpdf.text.Font.BOLD;
import static com.itextpdf.text.Font.NORMAL;
import static com.itextpdf.text.FontFactory.getFont;
import com.itextpdf.text.Image;
import static com.itextpdf.text.Image.getInstance;
import static com.itextpdf.text.PageSize.A4;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import static com.itextpdf.text.Rectangle.NO_BORDER;
import static com.itextpdf.text.pdf.BaseFont.HELVETICA;
import static com.itextpdf.text.pdf.BaseFont.WINANSI;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import static com.itextpdf.text.pdf.PdfWriter.getInstance;
import com.itextpdf.text.pdf.draw.LineSeparator;
import rc.so.entity.Branch;
import rc.so.entity.Currency;
import rc.so.entity.Figures;
import static rc.so.util.Constant.patternnormdate_filter;
import static rc.so.util.Engine.getConf;
import static rc.so.util.Engine.get_figures;
import static rc.so.util.Engine.get_last_modify_rate;
import static rc.so.util.Engine.insertTR;
import static rc.so.util.Utility.formatMysqltoDisplay;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import static java.lang.Thread.currentThread;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import static org.apache.commons.codec.binary.Base64.decodeBase64;
import static org.apache.commons.codec.binary.Base64.encodeBase64;
import static org.apache.commons.io.FileUtils.readFileToByteArray;
import org.joda.time.DateTime;

/**
 *
 * @author srotella
 */
public class CartelloCambiUK {

    /**
     *
     */
    public static final float[] columnWidths0 = new float[]{45f, 55f};

    /**
     *
     */
    public static final float[] columnWidths5050 = new float[]{60f,1f, 40f};

    /**
     *
     */
    public static final float[] columnWidths0end = new float[]{1f, 60f, 30f, 1f};

    /**
     *
     */
    public static final float[] columnWidths1 = new float[]{60f, 10f};

    /**
     *
     */
    public static float[] columnWidths2 = new float[]{20f, 40f, 20f, 20f};

    /**
     *
     */
    public static float[] columnWidths3 = new float[]{60f, 20f, 20f};

    final String intestazionePdf = "NO REFUND AFTER TRANSACTION IS DONE";
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

    Font f3_normal, f3_bold, f5_bold;

    /**
     ** Constructor
     */
    public CartelloCambiUK() {
        this.f3_bold = getFont(HELVETICA, WINANSI, 6.8f, BOLD);
        this.f5_bold = getFont(HELVETICA, WINANSI, 6.2f, BOLD);
        this.f3_normal = getFont(HELVETICA, WINANSI, 6.5f, NORMAL);
    }

    /**
     *
     * @param path
     * @param list
     * @param list_all_figures
     * @param bra
     * @param listFixCOm
     * @return
     */
    public String receipt(String path, ArrayList<Currency> list, ArrayList<Figures> list_all_figures, Branch bra,ArrayList<String[]> listFixCOm) {

        try {

            Figures notes1 = get_figures(list_all_figures, "01");
            Figures ca4 = get_figures(list_all_figures, "04");

            DateFormat dateFormat = new SimpleDateFormat("ddddMMyyyy");
            Date date = new Date();
            File pdf = new File(path + "CartelloCambiUK" + "_" + dateFormat.format(date) + ".pdf");
            //   File pdf = new File(path + Utility.generaId(50) + "CartelloCambiUK.pdf");
            Document document = new Document(A4, 50, 50, 50, 50);
            try(OutputStream ou = new FileOutputStream(pdf)){
            PdfWriter wr = getInstance(document, ou);
            document.open();

            Image image1 = getInstance(decodeBase64(getConf("path.logocl")));
            image1.scalePercent(40f);
            image1.setAbsolutePosition(100, 800);
            document.add(image1);

            PdfPTable table = new PdfPTable(2);
            table.setWidths(columnWidths0);
            table.setWidthPercentage(80);

            Phrase phrase1 = new Phrase();
            phrase1.add(new Chunk("", f3_normal));
            PdfPCell cell1 = new PdfPCell(phrase1);
            cell1.setBorder(NO_BORDER);

            Paragraph pa1 = new Paragraph(new Phrase("", f3_normal));
            pa1.setAlignment(ALIGN_CENTER);
            PdfPCell cell2 = new PdfPCell(pa1);
            cell2.setBorder(NO_BORDER);
            cell2.setHorizontalAlignment(ALIGN_RIGHT);

            Phrase phrase3 = new Phrase();
            phrase3.add(new Chunk("\n", f3_normal));
            PdfPCell cell3 = new PdfPCell(phrase3);
            cell3.setBorder(NO_BORDER);

            Phrase phrase4 = new Phrase();
            phrase4.add(new Chunk("\n", f3_normal));
            PdfPCell cell4 = new PdfPCell(phrase4);
            cell4.setBorder(NO_BORDER);

            table.addCell(cell1);
            table.addCell(cell2);
            table.addCell(cell3);
            table.addCell(cell4);
//            document.add(table);

//            vuoto.setFont(f3_normal);
//            document.add(vuoto);

            PdfPTable table2 = new PdfPTable(4);
            table2.setWidths(columnWidths2);
            table2.setWidthPercentage(80);

            phrase1 = new Phrase();
            phrase1.add(new Chunk(bra.getCod() + " - " + bra.getDe_branch(), f3_bold));
            cell1 = new PdfPCell(phrase1);
            cell1.setHorizontalAlignment(ALIGN_LEFT);
            cell1.setColspan(2);
            cell1.setBorder(NO_BORDER);

            phrase3 = new Phrase();
            phrase3.add(new Chunk("Rates updated on: ", f3_bold));
            phrase3.add(TABBING);
            phrase3.add(new Chunk(get_last_modify_rate(bra.getCod()), f3_normal));
            cell3 = new PdfPCell(phrase3);
            cell3.setColspan(2);
            cell3.setHorizontalAlignment(ALIGN_RIGHT);
            cell3.setBorder(NO_BORDER);

            phrase4 = new Phrase();
            phrase4.add(new Chunk("Valid on: ", f3_bold));
            phrase4.add(TABBING);
            phrase4.add(TABBING);
            phrase4.add(new Chunk(new DateTime().toString(patternnormdate_filter), f3_normal));
            cell4 = new PdfPCell(phrase4);
            cell4.setBorder(NO_BORDER);
            cell4.setHorizontalAlignment(ALIGN_RIGHT);
            cell4.setColspan(2);

            Phrase phrase5 = new Phrase();
            phrase5.add(new Chunk(" ", f3_bold));
            PdfPCell cell5 = new PdfPCell(phrase5);
            cell5.setBorder(NO_BORDER);

            table2.addCell(cell1);
            
            table2.addCell(cell3);
            table2.addCell(cell5);
            table2.addCell(cell5);
            table2.addCell(cell4);
            
            document.add(table2);
            vuoto.setFont(f3_normal);
            document.add(vuoto);

            table2 = new PdfPTable(4);
            table2.setWidths(columnWidths2);
            table2.setWidthPercentage(80);

            phrase1 = new Phrase();
            phrase1.add(new Chunk("SWIFT", f3_bold));
            cell1 = new PdfPCell(phrase1);
            cell1.setHorizontalAlignment(ALIGN_LEFT);
            cell1.setBorder(NO_BORDER);

            Phrase phrase2 = new Phrase();
            phrase2.add(new Chunk("CURRENCY", f3_bold));
            cell2 = new PdfPCell(phrase2);
            cell2.setHorizontalAlignment(ALIGN_CENTER);
            cell2.setBorder(NO_BORDER);

            phrase3 = new Phrase();
            phrase3.add(new Chunk("WE BUY", f3_bold));
            cell3 = new PdfPCell(phrase3);
            cell3.setHorizontalAlignment(ALIGN_RIGHT);
            cell3.setBorder(NO_BORDER);

            phrase4 = new Phrase();
            phrase4.add(new Chunk("WE SELL", f3_bold));
            cell4 = new PdfPCell(phrase4);
            cell4.setHorizontalAlignment(ALIGN_RIGHT);
            cell4.setBorder(NO_BORDER);

            table2.addCell(cell1);
            table2.addCell(cell2);
            table2.addCell(cell3);
            table2.addCell(cell4);
            document.add(table2);
            // document.add(vuoto);

            table2 = new PdfPTable(4);
            table2.setWidths(columnWidths2);
            table2.setWidthPercentage(80);

            LineSeparator sep = new LineSeparator();
            sep.setOffset(-2);
            sep.setLineWidth((float) 0.5);

            Phrase phraset;
            PdfPCell cellt;

            for (int i = 0; i < list.size(); i++) {

                Currency temp = list.get(i);

                phraset = new Phrase();
                phraset.add(new Chunk(temp.getCode(), f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_LEFT);
                cellt.setBorder(NO_BORDER);
                table2.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(temp.getDescrizione(), f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_LEFT);
                cellt.setBorder(NO_BORDER);
                table2.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk((temp.getBuy_std_value()), f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(NO_BORDER);
                table2.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk((temp.getSell_std_value()), f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(NO_BORDER);
                table2.addCell(cellt);

                //if ((i > 0 && i % 20 == 0) || i == dati.size() - 1) {
            }
            
            table2.addCell(cell5);
            table2.addCell(cell5);
            table2.addCell(cell5);
            table2.addCell(cell5);

            document.add(table2);

//            document.add(vuoto);

            PdfPTable tableend = new PdfPTable(3);
            tableend.setWidths(columnWidths5050);
            tableend.setWidthPercentage(80);

            PdfPTable table3 = new PdfPTable(2);
            table3.setWidths(columnWidths1);
            table3.setWidthPercentage(100);

            cell1 = new PdfPCell(new Paragraph("MAXIMUM SELLING CHARGE FOR FOREIGN CURRENCY", f5_bold));
            cell1.setBorder(NO_BORDER);
            cell1.setVerticalAlignment(ALIGN_MIDDLE);
            cell1.setHorizontalAlignment(ALIGN_LEFT);
            table3.addCell(cell1);

            cell1 = new PdfPCell(new Paragraph(notes1.getCommissione_vendita() + " %", f5_bold));
            cell1.setBorder(NO_BORDER);
            cell1.setVerticalAlignment(ALIGN_MIDDLE);
            cell1.setHorizontalAlignment(ALIGN_RIGHT);
            table3.addCell(cell1);

            cell1 = new PdfPCell(new Paragraph("MAXIMUM BUYING CHARGE FOR FOREIGN EXCHANGE", f5_bold));
            cell1.setBorder(NO_BORDER);
            cell1.setVerticalAlignment(ALIGN_MIDDLE);
            cell1.setHorizontalAlignment(ALIGN_LEFT);
            table3.addCell(cell1);
            cell1 = new PdfPCell(new Paragraph(notes1.getCommissione_acquisto() + " %", f5_bold));
            cell1.setBorder(NO_BORDER);
            cell1.setVerticalAlignment(ALIGN_MIDDLE);
            cell1.setHorizontalAlignment(ALIGN_RIGHT);
            table3.addCell(cell1);

            cell1 = new PdfPCell(new Paragraph("MAXIMUM CHARGE FOR CREDIT CARD CASH ADVANCE", f5_bold));
            cell1.setBorder(NO_BORDER);
            cell1.setVerticalAlignment(ALIGN_MIDDLE);
            cell1.setHorizontalAlignment(ALIGN_LEFT);
            table3.addCell(cell1);
            cell1 = new PdfPCell(new Paragraph(ca4.getCommissione_acquisto() + " %", f5_bold));
            cell1.setBorder(NO_BORDER);
            cell1.setVerticalAlignment(ALIGN_MIDDLE);
            cell1.setHorizontalAlignment(ALIGN_RIGHT);
            table3.addCell(cell1);

            cell1 = new PdfPCell(vuoto);
            cell1.setBorder(NO_BORDER);
            table3.addCell(cell1);
            table3.addCell(cell1);

            cell1 = new PdfPCell(new Paragraph("OPTIONAL BUY BACK SERVICE", f5_bold));
            cell1.setBorder(NO_BORDER);
            cell1.setVerticalAlignment(ALIGN_MIDDLE);
            cell1.setHorizontalAlignment(ALIGN_LEFT);
            table3.addCell(cell1);
            cell1 = new PdfPCell(new Paragraph(notes1.getBuy_back_commission() + " £", f5_bold));
            cell1.setBorder(NO_BORDER);
            cell1.setVerticalAlignment(ALIGN_MIDDLE);
            cell1.setHorizontalAlignment(ALIGN_RIGHT);
            table3.addCell(cell1);

            cell1 = new PdfPCell(new Paragraph("OPTIONAL SELL BACK SERVICE", f5_bold));
            cell1.setBorder(NO_BORDER);
            cell1.setVerticalAlignment(ALIGN_MIDDLE);
            cell1.setHorizontalAlignment(ALIGN_LEFT);
            table3.addCell(cell1);
            cell1 = new PdfPCell(new Paragraph(notes1.getSell_back_commission() + " £", f5_bold));
            cell1.setBorder(NO_BORDER);
            cell1.setVerticalAlignment(ALIGN_MIDDLE);
            cell1.setHorizontalAlignment(ALIGN_RIGHT);
            table3.addCell(cell1);

            cell2 = new PdfPCell();
            cell2.addElement(new Paragraph(""));
            cell2.setBorderWidth(1F);
            cell2.addElement(table3);
            tableend.addCell(cell2);

            PdfPTable table4 = new PdfPTable(3);
            table4.setWidths(columnWidths3);
            table4.setWidthPercentage(100);

            cell1 = new PdfPCell(new Paragraph("HANDLING FEE ACCORDING\nTO AMOUNT CHANGED", f5_bold));
            cell1.setBorder(NO_BORDER);
            cell1.setVerticalAlignment(ALIGN_MIDDLE);
            cell1.setHorizontalAlignment(ALIGN_CENTER);
            cell1.setColspan(3);
            table4.addCell(cell1);

            cell1 = new PdfPCell(vuoto);
            cell1.setBorder(NO_BORDER);
            table4.addCell(cell1);

            cell1 = new PdfPCell(new Paragraph("BUY", f5_bold));
            cell1.setBorder(NO_BORDER);
            cell1.setVerticalAlignment(ALIGN_BOTTOM);
            cell1.setHorizontalAlignment(ALIGN_CENTER);
            table4.addCell(cell1);
            
            
            cell1 = new PdfPCell(new Paragraph("SELL", f5_bold));
            cell1.setBorder(NO_BORDER);
            cell1.setVerticalAlignment(ALIGN_BOTTOM);
            cell1.setHorizontalAlignment(ALIGN_CENTER);
            table4.addCell(cell1);
            
            
            
            for (int i = 0; i < listFixCOm.size(); i++) {
                String v1 = listFixCOm.get(i)[0];
                if (v1.equals("01")) {
                    String v2 = "From " + formatMysqltoDisplay(listFixCOm.get(i)[1]) + " £ to " + formatMysqltoDisplay(listFixCOm.get(i)[2] + " £");
                    String b1 = formatMysqltoDisplay(listFixCOm.get(i)[3]) + " £";
                    String s1 = formatMysqltoDisplay(listFixCOm.get(i)[4]) + " £";
                    cell1 = new PdfPCell(new Paragraph(v2, f5_bold));
                    cell1.setBorder(NO_BORDER);
                    cell1.setVerticalAlignment(ALIGN_MIDDLE);
                    cell1.setHorizontalAlignment(ALIGN_LEFT);
                    table4.addCell(cell1);
                    cell1 = new PdfPCell(new Paragraph(b1, f5_bold));
                    cell1.setBorder(NO_BORDER);
                    cell1.setVerticalAlignment(ALIGN_MIDDLE);
                    cell1.setHorizontalAlignment(ALIGN_CENTER);
                    table4.addCell(cell1);
                    cell1 = new PdfPCell(new Paragraph(s1, f5_bold));
                    cell1.setBorder(NO_BORDER);
                    cell1.setVerticalAlignment(ALIGN_MIDDLE);
                    cell1.setHorizontalAlignment(ALIGN_CENTER);
                    table4.addCell(cell1);
                }
            }
            
            
            
            
            
            
            
            cell2 = new PdfPCell();
            cell2.addElement(new Paragraph(""));
            cell2.setBorderWidth(1F);
            cell2.addElement(table4);
            
            PdfPCell cell44 = new PdfPCell();
            cell44.addElement(new Paragraph(""));
            cell44.setBorder(NO_BORDER);
            tableend.addCell(cell44);
            
            tableend.addCell(cell2);
            document.add(tableend);

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

}
