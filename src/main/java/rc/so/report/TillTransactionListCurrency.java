/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rc.so.report;

import com.itextpdf.layout.borders.Border;
import static com.itextpdf.text.BaseColor.LIGHT_GRAY;
import com.itextpdf.text.Chunk;
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
import static com.itextpdf.text.PageSize.A4;
import com.itextpdf.text.Phrase;
import static com.itextpdf.text.Rectangle.BOTTOM;
import static com.itextpdf.text.Rectangle.BOX;
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
import static rc.so.util.Constant.patternnormdate;
import static rc.so.util.Constant.patternsqldate;
import static rc.so.util.Engine.insertTR;
import static rc.so.util.Utility.fd;
import static rc.so.util.Utility.formatMysqltoDisplay;
import static rc.so.util.Utility.formatStringtoStringDate;
import static rc.so.util.Utility.generaId;
import static rc.so.util.Utility.roundDouble;
import static rc.so.util.Utility.roundDoubleandFormat;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import static java.lang.String.valueOf;
import static java.lang.Thread.currentThread;
import java.util.ArrayList;
import static org.apache.commons.codec.binary.Base64.encodeBase64;
import static org.apache.commons.io.FileUtils.readFileToByteArray;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import static org.apache.poi.hssf.usermodel.HSSFFont.FONT_ARIAL;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import static org.apache.poi.ss.usermodel.BorderStyle.THIN;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import static org.apache.poi.ss.usermodel.HorizontalAlignment.LEFT;

/**
 *
 * @author vcrugliano
 */
public class TillTransactionListCurrency {

    //column

    /**
     *
     */
    public static final float[] columnWidths0 = new float[]{80f, 20f};

    /**
     *
     */
    public static final float[] columnWidths1 = new float[]{60f, 30f};

    /**
     *
     */
    public static final float[] columnWidths2 = new float[]{4f, 3f, 3f, 8f, 8f, 5f, 10f, 6f, 6f, 6f, 3.5f, 3f, 5f, 5f, 8f, 5f, 6f};

    /**
     *
     */
    public static final float[] columnWidths2A = new float[]{3f, 3f, 3f, 8f, 8f, 5f, 10f, 6f, 6f, 4f, 6.5f, 5f, 5f, 10f, 6f};

    /**
     *
     */
    public static final float[] columnWidths2B = new float[]{4f, 3f, 3f, 8f, 8f, 5f, 10f, 6f, 6f, 4f, 6.5f, 5f, 5f};

    /**
     *
     */
    public static final float[] columnWidths3 = new float[]{25f, 7.5f, 19f, 8f, 20f, 7f, 16f, 9f, 10f, 9f, 9f, 9f};

    /**
     *
     */
    public static final float[] columnWidths4 = new float[]{4f, 6f, 6f, 5f, 3.5f, 6f, 6f, 6f, 3.5f, 6f, 6f, 6f, 3.5f, 8f, 8f, 4f, 5f, 3.5f, 5f, 5f, 5f};

    /**
     *
     */
    public static final float[] columnWidths5 = new float[]{25f, 25f, 25f, 25f};

    /**
     *
     */
    public static final float[] columnWidths6 = new float[]{20f, 20f, 20f, 20f, 20f};

    final String intestazionePdf = "Till Transaction List by Currency  ";
    Phrase vuoto = new Phrase("\n");
    PdfPCell cellavuota = new PdfPCell(vuoto);

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

    Font f0_bold, f1_bold, f2_bold, f1_normal, f2_normal, f3_normal, f4_bold, f5_normal, f5_bold, f6_normal, f6_bold;

    /**
     * Costructor
     */
    public TillTransactionListCurrency() {

        cellavuota.setBorder(NO_BORDER);

        this.f0_bold = getFont(HELVETICA, WINANSI, 14.04f, BOLD);
        this.f1_bold = getFont(HELVETICA, WINANSI, 9.96f, BOLD);
        this.f2_bold = getFont(HELVETICA, WINANSI, 8f, BOLD);
        this.f4_bold = getFont(HELVETICA, WINANSI, 6.96f, BOLD);
        this.f1_normal = getFont(HELVETICA, WINANSI, 9.96f, NORMAL);
        this.f2_normal = getFont(HELVETICA, WINANSI, 8f, NORMAL);
        this.f3_normal = getFont(HELVETICA, WINANSI, 6.96f, NORMAL);
        this.f5_normal = getFont(HELVETICA, WINANSI, 6f, NORMAL);
        this.f5_bold = getFont(HELVETICA, WINANSI, 6f, BOLD);
        this.f6_normal = getFont(HELVETICA, WINANSI, 5.5f, NORMAL);
        this.f6_bold = getFont(HELVETICA, WINANSI, 5.5f, BOLD);

    }

    /**
     *
     * @param document
     */
    public void scriviIntestazioneColonne_CACZ(Document document) {
        try {
            PdfPTable table2 = new PdfPTable(15);
            table2.setWidths(columnWidths2A);
            table2.setWidthPercentage(100);

            Phrase phraset21 = new Phrase();
            phraset21.add(new Chunk("TRANSACTION CHANGE", f5_bold));
            PdfPCell cellt21 = new PdfPCell(phraset21);
            cellt21.setHorizontalAlignment(ALIGN_LEFT);
            cellt21.setVerticalAlignment(ALIGN_MIDDLE);
            cellt21.setBorder(BOTTOM | TOP);
            cellt21.setBackgroundColor(LIGHT_GRAY);
            cellt21.setColspan(6);
            table2.addCell(cellt21);
            phraset21 = new Phrase();
            phraset21.add(new Chunk("", f5_bold));
            cellt21 = new PdfPCell(phraset21);
            cellt21.setHorizontalAlignment(ALIGN_LEFT);
            cellt21.setVerticalAlignment(ALIGN_MIDDLE);
            cellt21.setBorder(BOTTOM | TOP);
            cellt21.setBackgroundColor(LIGHT_GRAY);
            table2.addCell(cellt21);
            table2.addCell(cellt21);
            table2.addCell(cellt21);
            table2.addCell(cellt21);
            table2.addCell(cellt21);
            table2.addCell(cellt21);
            table2.addCell(cellt21);
            table2.addCell(cellt21);
            table2.addCell(cellt21);

            Phrase phraset2 = new Phrase();
            phraset2.add(new Chunk("Type", f5_bold));
            PdfPCell cellt2 = new PdfPCell(phraset2);
            cellt2.setHorizontalAlignment(ALIGN_LEFT);
            cellt2.setVerticalAlignment(ALIGN_MIDDLE);
            cellt2.setBorder(BOTTOM | TOP);
            cellt2.setBackgroundColor(LIGHT_GRAY);
            table2.addCell(cellt2);

            phraset2 = new Phrase();
            phraset2.add(new Chunk("Till", f5_bold));
            cellt2 = new PdfPCell(phraset2);
            cellt2.setHorizontalAlignment(ALIGN_RIGHT);
            cellt2.setVerticalAlignment(ALIGN_MIDDLE);
            cellt2.setBorder(BOTTOM | TOP);
            cellt2.setBackgroundColor(LIGHT_GRAY);
            table2.addCell(cellt2);

            phraset2 = new Phrase();
            phraset2.add(new Chunk("User", f5_bold));
            cellt2 = new PdfPCell(phraset2);
            cellt2.setHorizontalAlignment(ALIGN_RIGHT);
            cellt2.setVerticalAlignment(ALIGN_MIDDLE);
            cellt2.setBorder(BOTTOM | TOP);
            cellt2.setBackgroundColor(LIGHT_GRAY);
            table2.addCell(cellt2);

            phraset2 = new Phrase();
            phraset2.add(new Chunk("No.Tr.", f5_bold));
            cellt2 = new PdfPCell(phraset2);
            cellt2.setHorizontalAlignment(ALIGN_RIGHT);
            cellt2.setVerticalAlignment(ALIGN_MIDDLE);
            cellt2.setBorder(BOTTOM | TOP);
            cellt2.setBackgroundColor(LIGHT_GRAY);
            table2.addCell(cellt2);

            phraset2 = new Phrase();
            phraset2.add(new Chunk("Date / Time", f5_bold));
            cellt2 = new PdfPCell(phraset2);
            cellt2.setHorizontalAlignment(ALIGN_LEFT);
            cellt2.setVerticalAlignment(ALIGN_MIDDLE);
            cellt2.setBorder(BOTTOM | TOP);
            cellt2.setBackgroundColor(LIGHT_GRAY);
            table2.addCell(cellt2);

            phraset2 = new Phrase();
            phraset2.add(new Chunk("Cur", f5_bold));
            cellt2 = new PdfPCell(phraset2);
            cellt2.setHorizontalAlignment(ALIGN_LEFT);
            cellt2.setVerticalAlignment(ALIGN_MIDDLE);
            cellt2.setBorder(BOTTOM | TOP);
            cellt2.setBackgroundColor(LIGHT_GRAY);
            table2.addCell(cellt2);

            phraset2 = new Phrase();
            phraset2.add(new Chunk("Kind", f5_bold));
            cellt2 = new PdfPCell(phraset2);
            cellt2.setHorizontalAlignment(ALIGN_LEFT);
            cellt2.setVerticalAlignment(ALIGN_MIDDLE);
            cellt2.setBorder(BOTTOM | TOP);
            cellt2.setBackgroundColor(LIGHT_GRAY);
            table2.addCell(cellt2);

            phraset2 = new Phrase();
            phraset2.add(new Chunk("Amount", f5_bold));
            cellt2 = new PdfPCell(phraset2);
            cellt2.setHorizontalAlignment(ALIGN_RIGHT);
            cellt2.setVerticalAlignment(ALIGN_MIDDLE);
            cellt2.setBorder(BOTTOM | TOP);
            cellt2.setBackgroundColor(LIGHT_GRAY);
            table2.addCell(cellt2);

            phraset2 = new Phrase();
            phraset2.add(new Chunk("Rate", f5_bold));
            cellt2 = new PdfPCell(phraset2);
            cellt2.setHorizontalAlignment(ALIGN_RIGHT);
            cellt2.setVerticalAlignment(ALIGN_MIDDLE);
            cellt2.setBorder(BOTTOM | TOP);
            cellt2.setBackgroundColor(LIGHT_GRAY);
            table2.addCell(cellt2);

            phraset2 = new Phrase();
            phraset2.add(new Chunk("Total", f5_bold));
            cellt2 = new PdfPCell(phraset2);
            cellt2.setHorizontalAlignment(ALIGN_RIGHT);
            cellt2.setVerticalAlignment(ALIGN_MIDDLE);
            cellt2.setBorder(BOTTOM | TOP);
            cellt2.setBackgroundColor(LIGHT_GRAY);
            table2.addCell(cellt2);

            phraset2 = new Phrase();
            phraset2.add(new Chunk("%", f5_bold));
            cellt2 = new PdfPCell(phraset2);
            cellt2.setHorizontalAlignment(ALIGN_RIGHT);
            cellt2.setVerticalAlignment(ALIGN_MIDDLE);
            cellt2.setBorder(BOTTOM | TOP);
            cellt2.setBackgroundColor(LIGHT_GRAY);
            table2.addCell(cellt2);

            phraset2 = new Phrase();
            phraset2.add(new Chunk("Comm.Fee", f5_bold));
            cellt2 = new PdfPCell(phraset2);
            cellt2.setHorizontalAlignment(ALIGN_RIGHT);
            cellt2.setVerticalAlignment(ALIGN_MIDDLE);
            cellt2.setBorder(BOTTOM | TOP);
            cellt2.setBackgroundColor(LIGHT_GRAY);
            table2.addCell(cellt2);

            phraset2 = new Phrase();
            phraset2.add(new Chunk("Round", f5_bold));
            cellt2 = new PdfPCell(phraset2);
            cellt2.setHorizontalAlignment(ALIGN_RIGHT);
            cellt2.setVerticalAlignment(ALIGN_MIDDLE);
            cellt2.setBorder(BOTTOM | TOP);
            cellt2.setBackgroundColor(LIGHT_GRAY);
            table2.addCell(cellt2);

            phraset2 = new Phrase();
            phraset2.add(new Chunk("Pay In / Pay Out", f5_bold));
            cellt2 = new PdfPCell(phraset2);
            cellt2.setHorizontalAlignment(ALIGN_RIGHT);
            cellt2.setVerticalAlignment(ALIGN_MIDDLE);
            cellt2.setBorder(BOTTOM | TOP);
            cellt2.setBackgroundColor(LIGHT_GRAY);
            table2.addCell(cellt2);

            phraset2 = new Phrase();
            phraset2.add(new Chunk("Pos / Bank Acc", f5_bold));
            cellt2 = new PdfPCell(phraset2);
            cellt2.setHorizontalAlignment(ALIGN_RIGHT);
            cellt2.setVerticalAlignment(ALIGN_MIDDLE);
            cellt2.setBorder(BOTTOM | TOP);
            cellt2.setBackgroundColor(LIGHT_GRAY);
            table2.addCell(cellt2);

            document.add(table2);
        } catch (DocumentException ex) {
            insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
        }
    }

    /**
     *
     * @param document
     */
    public void scriviIntestazioneColonne_CACZ_2(Document document) {
        try {
            PdfPTable table2 = new PdfPTable(13);
            table2.setWidths(columnWidths2B);
            table2.setWidthPercentage(100);

            Phrase phraset21 = new Phrase();
            phraset21.add(new Chunk("EXTERNAL TRANSFER - TO BANK", f5_bold));
            PdfPCell cellt21 = new PdfPCell(phraset21);
            cellt21.setHorizontalAlignment(ALIGN_LEFT);
            cellt21.setVerticalAlignment(ALIGN_MIDDLE);
            cellt21.setBorder(BOTTOM | TOP);
            cellt21.setBackgroundColor(LIGHT_GRAY);
            cellt21.setColspan(6);
            table2.addCell(cellt21);
            phraset21 = new Phrase();
            phraset21.add(new Chunk("", f5_bold));
            cellt21 = new PdfPCell(phraset21);
            cellt21.setHorizontalAlignment(ALIGN_LEFT);
            cellt21.setVerticalAlignment(ALIGN_MIDDLE);
            cellt21.setBorder(BOTTOM | TOP);
            cellt21.setBackgroundColor(LIGHT_GRAY);
            table2.addCell(cellt21);
            table2.addCell(cellt21);
            table2.addCell(cellt21);
            table2.addCell(cellt21);
            table2.addCell(cellt21);
            table2.addCell(cellt21);
            table2.addCell(cellt21);

            Phrase phraset2 = new Phrase();
            phraset2.add(new Chunk("Type", f5_bold));
            PdfPCell cellt2 = new PdfPCell(phraset2);
            cellt2.setHorizontalAlignment(ALIGN_LEFT);
            cellt2.setVerticalAlignment(ALIGN_MIDDLE);
            cellt2.setBorder(BOTTOM | TOP);
            cellt2.setBackgroundColor(LIGHT_GRAY);
            table2.addCell(cellt2);

            phraset2 = new Phrase();
            phraset2.add(new Chunk("Till", f5_bold));
            cellt2 = new PdfPCell(phraset2);
            cellt2.setHorizontalAlignment(ALIGN_RIGHT);
            cellt2.setVerticalAlignment(ALIGN_MIDDLE);
            cellt2.setBorder(BOTTOM | TOP);
            cellt2.setBackgroundColor(LIGHT_GRAY);
            table2.addCell(cellt2);

            phraset2 = new Phrase();
            phraset2.add(new Chunk("User", f5_bold));
            cellt2 = new PdfPCell(phraset2);
            cellt2.setHorizontalAlignment(ALIGN_RIGHT);
            cellt2.setVerticalAlignment(ALIGN_MIDDLE);
            cellt2.setBorder(BOTTOM | TOP);
            cellt2.setBackgroundColor(LIGHT_GRAY);
            table2.addCell(cellt2);

            phraset2 = new Phrase();
            phraset2.add(new Chunk("No.Tr.", f5_bold));
            cellt2 = new PdfPCell(phraset2);
            cellt2.setHorizontalAlignment(ALIGN_RIGHT);
            cellt2.setVerticalAlignment(ALIGN_MIDDLE);
            cellt2.setBorder(BOTTOM | TOP);
            cellt2.setBackgroundColor(LIGHT_GRAY);
            table2.addCell(cellt2);

            phraset2 = new Phrase();
            phraset2.add(new Chunk("Date / Time", f5_bold));
            cellt2 = new PdfPCell(phraset2);
            cellt2.setHorizontalAlignment(ALIGN_LEFT);
            cellt2.setVerticalAlignment(ALIGN_MIDDLE);
            cellt2.setBorder(BOTTOM | TOP);
            cellt2.setBackgroundColor(LIGHT_GRAY);
            table2.addCell(cellt2);

            phraset2 = new Phrase();
            phraset2.add(new Chunk("Cur", f5_bold));
            cellt2 = new PdfPCell(phraset2);
            cellt2.setHorizontalAlignment(ALIGN_LEFT);
            cellt2.setVerticalAlignment(ALIGN_MIDDLE);
            cellt2.setBorder(BOTTOM | TOP);
            cellt2.setBackgroundColor(LIGHT_GRAY);
            table2.addCell(cellt2);

            phraset2 = new Phrase();
            phraset2.add(new Chunk("Kind", f5_bold));
            cellt2 = new PdfPCell(phraset2);
            cellt2.setHorizontalAlignment(ALIGN_LEFT);
            cellt2.setVerticalAlignment(ALIGN_MIDDLE);
            cellt2.setBorder(BOTTOM | TOP);
            cellt2.setBackgroundColor(LIGHT_GRAY);
            table2.addCell(cellt2);

            phraset2 = new Phrase();
            phraset2.add(new Chunk("Amount", f5_bold));
            cellt2 = new PdfPCell(phraset2);
            cellt2.setHorizontalAlignment(ALIGN_RIGHT);
            cellt2.setVerticalAlignment(ALIGN_MIDDLE);
            cellt2.setBorder(BOTTOM | TOP);
            cellt2.setBackgroundColor(LIGHT_GRAY);
            table2.addCell(cellt2);

            phraset2 = new Phrase();
            phraset2.add(new Chunk("Rate", f5_bold));
            cellt2 = new PdfPCell(phraset2);
            cellt2.setHorizontalAlignment(ALIGN_RIGHT);
            cellt2.setVerticalAlignment(ALIGN_MIDDLE);
            cellt2.setBorder(BOTTOM | TOP);
            cellt2.setBackgroundColor(LIGHT_GRAY);
            table2.addCell(cellt2);

            phraset2 = new Phrase();
            phraset2.add(new Chunk("Equivalent", f5_bold));
            cellt2 = new PdfPCell(phraset2);
            cellt2.setHorizontalAlignment(ALIGN_RIGHT);
            cellt2.setVerticalAlignment(ALIGN_MIDDLE);
            cellt2.setBorder(BOTTOM | TOP);
            cellt2.setBackgroundColor(LIGHT_GRAY);
            table2.addCell(cellt2);

            phraset2 = new Phrase();
            phraset2.add(new Chunk("Buy Value", f5_bold));
            cellt2 = new PdfPCell(phraset2);
            cellt2.setHorizontalAlignment(ALIGN_RIGHT);
            cellt2.setVerticalAlignment(ALIGN_MIDDLE);
            cellt2.setBorder(BOTTOM | TOP);
            cellt2.setBackgroundColor(LIGHT_GRAY);
            table2.addCell(cellt2);

            phraset2 = new Phrase();
            phraset2.add(new Chunk("Spread", f5_bold));
            cellt2 = new PdfPCell(phraset2);
            cellt2.setHorizontalAlignment(ALIGN_RIGHT);
            cellt2.setVerticalAlignment(ALIGN_MIDDLE);
            cellt2.setBorder(BOTTOM | TOP);
            cellt2.setBackgroundColor(LIGHT_GRAY);
            table2.addCell(cellt2);

            phraset2 = new Phrase();
            phraset2.add(new Chunk("Pos / Bank Acc", f5_bold));
            cellt2 = new PdfPCell(phraset2);
            cellt2.setHorizontalAlignment(ALIGN_RIGHT);
            cellt2.setVerticalAlignment(ALIGN_MIDDLE);
            cellt2.setBorder(BOTTOM | TOP);
            cellt2.setBackgroundColor(LIGHT_GRAY);
            table2.addCell(cellt2);

            document.add(table2);
        } catch (DocumentException ex) {
            insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
        }
    }

    /**
     *
     * @param document
     */
    public void scriviIntestazioneColonne(Document document) {

        try {
            PdfPTable table2 = new PdfPTable(17);
            table2.setWidths(columnWidths2);
            table2.setWidthPercentage(100);

            Phrase phraset2 = new Phrase();
            phraset2.add(new Chunk("Type", f5_bold));
            PdfPCell cellt2 = new PdfPCell(phraset2);
            cellt2.setHorizontalAlignment(ALIGN_LEFT);
            cellt2.setVerticalAlignment(ALIGN_MIDDLE);
            cellt2.setBorder(BOTTOM | TOP);
            cellt2.setBackgroundColor(LIGHT_GRAY);
            table2.addCell(cellt2);

            phraset2 = new Phrase();
            phraset2.add(new Chunk("Till", f5_bold));
            cellt2 = new PdfPCell(phraset2);
            cellt2.setHorizontalAlignment(ALIGN_RIGHT);
            cellt2.setVerticalAlignment(ALIGN_MIDDLE);
            cellt2.setBorder(BOTTOM | TOP);
            cellt2.setBackgroundColor(LIGHT_GRAY);
            table2.addCell(cellt2);

            phraset2 = new Phrase();
            phraset2.add(new Chunk("User", f5_bold));
            cellt2 = new PdfPCell(phraset2);
            cellt2.setHorizontalAlignment(ALIGN_RIGHT);
            cellt2.setVerticalAlignment(ALIGN_MIDDLE);
            cellt2.setBorder(BOTTOM | TOP);
            cellt2.setBackgroundColor(LIGHT_GRAY);
            table2.addCell(cellt2);

            phraset2 = new Phrase();
            phraset2.add(new Chunk("No.Tr.", f5_bold));
            cellt2 = new PdfPCell(phraset2);
            cellt2.setHorizontalAlignment(ALIGN_RIGHT);
            cellt2.setVerticalAlignment(ALIGN_MIDDLE);
            cellt2.setBorder(BOTTOM | TOP);
            cellt2.setBackgroundColor(LIGHT_GRAY);
            table2.addCell(cellt2);

            phraset2 = new Phrase();
            phraset2.add(new Chunk("Date / Time", f5_bold));
            cellt2 = new PdfPCell(phraset2);
            cellt2.setHorizontalAlignment(ALIGN_LEFT);
            cellt2.setVerticalAlignment(ALIGN_MIDDLE);
            cellt2.setBorder(BOTTOM | TOP);
            cellt2.setBackgroundColor(LIGHT_GRAY);
            table2.addCell(cellt2);

            phraset2 = new Phrase();
            phraset2.add(new Chunk("Cur", f5_bold));
            cellt2 = new PdfPCell(phraset2);
            cellt2.setHorizontalAlignment(ALIGN_LEFT);
            cellt2.setVerticalAlignment(ALIGN_MIDDLE);
            cellt2.setBorder(BOTTOM | TOP);
            cellt2.setBackgroundColor(LIGHT_GRAY);
            table2.addCell(cellt2);

            phraset2 = new Phrase();
            phraset2.add(new Chunk("Kind", f5_bold));
            cellt2 = new PdfPCell(phraset2);
            cellt2.setHorizontalAlignment(ALIGN_LEFT);
            cellt2.setVerticalAlignment(ALIGN_MIDDLE);
            cellt2.setBorder(BOTTOM | TOP);
            cellt2.setBackgroundColor(LIGHT_GRAY);
            table2.addCell(cellt2);

            phraset2 = new Phrase();
            phraset2.add(new Chunk("Amount", f5_bold));
            cellt2 = new PdfPCell(phraset2);
            cellt2.setHorizontalAlignment(ALIGN_RIGHT);
            cellt2.setVerticalAlignment(ALIGN_MIDDLE);
            cellt2.setBorder(BOTTOM | TOP);
            cellt2.setBackgroundColor(LIGHT_GRAY);
            table2.addCell(cellt2);

            phraset2 = new Phrase();
            phraset2.add(new Chunk("Rate", f5_bold));
            cellt2 = new PdfPCell(phraset2);
            cellt2.setHorizontalAlignment(ALIGN_RIGHT);
            cellt2.setVerticalAlignment(ALIGN_MIDDLE);
            cellt2.setBorder(BOTTOM | TOP);
            cellt2.setBackgroundColor(LIGHT_GRAY);
            table2.addCell(cellt2);

            phraset2 = new Phrase();
            phraset2.add(new Chunk("Total", f5_bold));
            cellt2 = new PdfPCell(phraset2);
            cellt2.setHorizontalAlignment(ALIGN_RIGHT);
            cellt2.setVerticalAlignment(ALIGN_MIDDLE);
            cellt2.setBorder(BOTTOM | TOP);
            cellt2.setBackgroundColor(LIGHT_GRAY);
            table2.addCell(cellt2);

            phraset2 = new Phrase();
            phraset2.add(new Chunk("%", f5_bold));
            cellt2 = new PdfPCell(phraset2);
            cellt2.setHorizontalAlignment(ALIGN_RIGHT);
            cellt2.setVerticalAlignment(ALIGN_MIDDLE);
            cellt2.setBorder(BOTTOM | TOP);
            cellt2.setBackgroundColor(LIGHT_GRAY);
            table2.addCell(cellt2);

            phraset2 = new Phrase();
            phraset2.add(new Chunk("Comm.Fee", f5_bold));
            cellt2 = new PdfPCell(phraset2);
            cellt2.setHorizontalAlignment(ALIGN_RIGHT);
            cellt2.setVerticalAlignment(ALIGN_MIDDLE);
            cellt2.setBorder(BOTTOM | TOP);
            cellt2.setBackgroundColor(LIGHT_GRAY);
            table2.addCell(cellt2);

            phraset2 = new Phrase();
            phraset2.add(new Chunk("Round", f5_bold));
            cellt2 = new PdfPCell(phraset2);
            cellt2.setHorizontalAlignment(ALIGN_RIGHT);
            cellt2.setVerticalAlignment(ALIGN_MIDDLE);
            cellt2.setBorder(BOTTOM | TOP);
            cellt2.setBackgroundColor(LIGHT_GRAY);
            table2.addCell(cellt2);

            phraset2 = new Phrase();
            phraset2.add(new Chunk("Pay In / Pay Out", f5_bold));
            cellt2 = new PdfPCell(phraset2);
            cellt2.setHorizontalAlignment(ALIGN_RIGHT);
            cellt2.setVerticalAlignment(ALIGN_MIDDLE);
            cellt2.setBorder(BOTTOM | TOP);
            cellt2.setBackgroundColor(LIGHT_GRAY);
            table2.addCell(cellt2);

            phraset2 = new Phrase();
            phraset2.add(new Chunk("Customer", f5_bold));
            cellt2 = new PdfPCell(phraset2);
            cellt2.setHorizontalAlignment(ALIGN_LEFT);
            cellt2.setVerticalAlignment(ALIGN_MIDDLE);
            cellt2.setBorder(BOTTOM | TOP);
            cellt2.setBackgroundColor(LIGHT_GRAY);
            table2.addCell(cellt2);

            phraset2 = new Phrase();
            phraset2.add(new Chunk("Spread", f5_bold));
            cellt2 = new PdfPCell(phraset2);
            cellt2.setHorizontalAlignment(ALIGN_RIGHT);
            cellt2.setVerticalAlignment(ALIGN_MIDDLE);
            cellt2.setBorder(BOTTOM | TOP);
            cellt2.setBackgroundColor(LIGHT_GRAY);
            table2.addCell(cellt2);

            phraset2 = new Phrase();
            phraset2.add(new Chunk("Pos / Bank Acc", f5_bold));
            cellt2 = new PdfPCell(phraset2);
            cellt2.setHorizontalAlignment(ALIGN_RIGHT);
            cellt2.setVerticalAlignment(ALIGN_MIDDLE);
            cellt2.setBorder(BOTTOM | TOP);
            cellt2.setBackgroundColor(LIGHT_GRAY);
            table2.addCell(cellt2);

            document.add(table2);

        } catch (DocumentException ex) {
            insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
        }
    }

    /**
     *
     * @param path
     * @param siq
     * @param datereport1
     * @param datereport2
     * @return
     */
    public String receiptexcel(String path, TillTransactionListCurrency_value siq, String datereport1, String datereport2) {

        //String outputfile = "TillTransactionListCurrency.pdf";
        try {
            File pdf = new File(path + generaId(50) + "TillTransactionlistCurrency.xls");
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet("TillTransactionlistCurrency");
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
            style3left.setAlignment(LEFT);
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
            style4left.setAlignment(LEFT);
            style4left.setBorderTop(THIN);
            style4left.setBorderBottom(THIN);

            Row rowP = sheet.createRow((short) 1);

            Cell cl = rowP.createCell(1);
            cl.setCellStyle(style1);
            cl.setCellValue(intestazionePdf + " From " + datereport1 + " To " + datereport2);

//            Cell cl2 = rowP.createCell(6);
//            cl2.setCellStyle(style2);
//            cl2.setCellValue(datereport);
            Row row = sheet.createRow((short) 3);
            row.createCell(1).setCellValue(siq.getId_filiale() + " " + siq.getDe_filiale());

            //  ArrayList<String> dati = siq.getDati();
            Row row6 = sheet.createRow((short) 5);

            Cell f2 = row6.createCell(1);
            f2.setCellStyle(style3left);
            f2.setCellValue("Type");

            Cell f3 = row6.createCell(2);
            f3.setCellStyle(style3);
            f3.setCellValue("Till");

            Cell f4 = row6.createCell(3);
            f4.setCellStyle(style3);
            f4.setCellValue("User");

            Cell f5 = row6.createCell(4);
            f5.setCellStyle(style3);
            f5.setCellValue("No.Tr.");

            Cell f6 = row6.createCell(5);
            f6.setCellStyle(style3left);
            f6.setCellValue("Date / Time");

            Cell f7 = row6.createCell(6);
            f7.setCellStyle(style3left);
            f7.setCellValue("Cur");

            Cell f8 = row6.createCell(7);
            f8.setCellStyle(style3left);
            f8.setCellValue("Kind");

            Cell f9 = row6.createCell(8);
            f9.setCellStyle(style3);
            f9.setCellValue("Amount");

            Cell f10 = row6.createCell(9);
            f10.setCellStyle(style3);
            f10.setCellValue("Rate");

            Cell f11 = row6.createCell(10);
            f11.setCellStyle(style3);
            f11.setCellValue("Total");

            Cell f12 = row6.createCell(11);
            f12.setCellStyle(style3);
            f12.setCellValue("%");

            Cell f13 = row6.createCell(12);
            f13.setCellStyle(style3);
            f13.setCellValue("Comm.Fee");

            Cell f131 = row6.createCell(13);
            f131.setCellStyle(style3);
            f131.setCellValue("Round");

            Cell f14 = row6.createCell(14);
            f14.setCellStyle(style3);
            f14.setCellValue("Pay In / Pay Out");

            Cell f15 = row6.createCell(15);
            f15.setCellStyle(style3left);
            f15.setCellValue("Customer");

            Cell f16 = row6.createCell(16);
            f16.setCellStyle(style3);
            f16.setCellValue("Spread");

            Cell f17 = row6.createCell(17);
            f17.setCellStyle(style3);
            f17.setCellValue("Pos / Bank Acc");

            //Popolo la tabella
            ArrayList<TillTransactionListCurrency_value> dati = siq.getDati();
            int cntriga = 7;

            double amount = 0, totale = 0, commfee = 0, payout = 0;

            double totaleresidentbuy = 0, totalenonresidentbuy = 0, totaleresidentsell = 0, totalenonresidentsell = 0, totaleresidentcommfeebuy = 0, totalenonresidentcommfeebuy = 0, totaleresidentcommfeesell = 0, totalenonresidentcommfeesell = 0;

            double totalepayinoutgeneral = 0;

            double totround = 0;

            for (int i = 0; i < dati.size() - 1; i++) {

                cntriga++;
                Row row7 = sheet.createRow((short) cntriga);

                TillTransactionListCurrency_value actual = dati.get(i);
                TillTransactionListCurrency_value prossimo = dati.get(i + 1);

                if (!actual.getType().contains("D")) {

                    amount += fd(actual.getAmountSenzaFormattazione());
                    totale += fd(actual.getTotalSenzaFormattazione());
                    commfee += fd(actual.getComfreeSenzaFormattazione());
                    payout += fd(actual.getPayinpayoutSenzaFormattazione());
                }

                Cell f18 = row7.createCell(1);
                f18.setCellStyle(style4left);
                f18.setCellValue(actual.getType());

                Cell f19 = row7.createCell(2);
                f19.setCellStyle(style4);
                f19.setCellValue(actual.getTill());

                Cell f20 = row7.createCell(3);
                f20.setCellStyle(style4);
                f20.setCellValue(actual.getUser());

                Cell f21 = row7.createCell(4);
                f21.setCellStyle(style4);
                f21.setCellValue(actual.getNotr());

                Cell f22 = row7.createCell(5);
                f22.setCellStyle(style4left);
                f22.setCellValue(formatStringtoStringDate(actual.getTime(), patternsqldate, patternnormdate));

                Cell f23 = row7.createCell(6);
                f23.setCellStyle(style4left);
                f23.setCellValue(actual.getCur());

                Cell f24 = row7.createCell(7);
                f24.setCellStyle(style4left);
                f24.setCellValue(actual.getKind());

                if (actual.getKind().contains("Buy") && !actual.getType().contains("D")) {
                    if (actual.getResidentnonresident().equalsIgnoreCase("Resident")) {
                        totaleresidentbuy += fd(actual.getTotalSenzaFormattazione());
                        totaleresidentcommfeebuy += (fd(actual.getComfreeSenzaFormattazione()) + fd(actual.getRound()));
                    } else {
                        totalenonresidentcommfeebuy += (fd(actual.getComfreeSenzaFormattazione()) + fd(actual.getRound()));
                        totalenonresidentbuy += fd(actual.getTotalSenzaFormattazione());
                    }
                } else if (actual.getKind().contains("Sell") && !actual.getType().contains("D")) {
                    if (actual.getResidentnonresident().equalsIgnoreCase("Resident")) {
                        totaleresidentsell += fd(actual.getTotalSenzaFormattazione());
                        totaleresidentcommfeesell += (fd(actual.getComfreeSenzaFormattazione()) + fd(actual.getRound()));
                    } else {
                        totalenonresidentsell += fd(actual.getTotalSenzaFormattazione());
                        totalenonresidentcommfeesell += (fd(actual.getComfreeSenzaFormattazione()) + fd(actual.getRound()));
                    }
                }

                Cell f25 = row7.createCell(8);
                f25.setCellStyle(style4);
                f25.setCellValue(formatMysqltoDisplay(actual.getAmount()));

                Cell f26 = row7.createCell(9);
                f26.setCellStyle(style4);
                f26.setCellValue(formatMysqltoDisplay(actual.getRate()));

                Cell f27 = row7.createCell(10);
                f27.setCellStyle(style4);
                f27.setCellValue(formatMysqltoDisplay(actual.getTotal()));

                Cell f28 = row7.createCell(11);
                f28.setCellStyle(style4);
                f28.setCellValue(formatMysqltoDisplay(actual.getPerc()));

                Cell f29 = row7.createCell(12);
                f29.setCellStyle(style4);
                f29.setCellValue(formatMysqltoDisplay(actual.getComfree()));

                Cell f291 = row7.createCell(13);
                f291.setCellStyle(style4);
                f291.setCellValue(formatMysqltoDisplay(actual.getRound()));

                if (!actual.getType().contains("D")) {

                    totround = totround + fd(actual.getRound());
//                totround = Utility.roundDouble(totround, 2);
                }

                Cell f30 = row7.createCell(14);
                f30.setCellStyle(style4);

                if (actual.getKind().contains("Sell")) {

                    f30.setCellValue("+" + formatMysqltoDisplay(actual.getPayinpayout()));

                    if (!actual.getType().contains("D")) {
                        totalepayinoutgeneral += fd(actual.getPayinpayoutSenzaFormattazione());
                    }
                } else {

                    f30.setCellValue("-" + formatMysqltoDisplay(actual.getPayinpayout()));
                    if (!actual.getType().contains("D")) {
                        totalepayinoutgeneral -= fd(actual.getPayinpayoutSenzaFormattazione());
                    }
                }

                Cell f31 = row7.createCell(15);
                f31.setCellStyle(style4left);
                f31.setCellValue(actual.getCustomer());

                Cell f32 = row7.createCell(16);
                f32.setCellStyle(style4);
                f32.setCellValue(formatMysqltoDisplay(actual.getSpread()));

                Cell f33 = row7.createCell(17);
                f33.setCellStyle(style4);
                f33.setCellValue(actual.getPos());

                cntriga++;
                cntriga++;

                Row row8 = sheet.createRow((short) cntriga);

                if (!prossimo.getCur().equals(actual.getCur())) {

                    for (int h = 0; h < columnWidths2.length; h++) {

                        if (h == 7) {

                            Cell f34 = row8.createCell(h + 1);
                            f34.setCellStyle(style3);
                            f34.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(amount, 2)));

                        } else if (h == 9) {

                            Cell f34 = row8.createCell(h + 1);
                            f34.setCellStyle(style3);
                            f34.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(totale, 2)));

                        } else if (h == 11) {

                            Cell f34 = row8.createCell(h + 1);
                            f34.setCellStyle(style3);
                            f34.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(commfee, 2)));

                        } else if (h == 12) {

                            Cell f34 = row8.createCell(h + 1);
                            f34.setCellStyle(style3);
                            f34.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(totround, 2)));

                        } else if (h == 13) {

                            Cell f34 = row8.createCell(h + 1);
                            f34.setCellStyle(style3);
                            f34.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(totalepayinoutgeneral, 2)));

                        } else {

//                            phraset = new Phrase();
//                            phraset.add(new Chunk("", f5_normal));
//                            cellt = new PdfPCell(phraset);
//                            cellt.setHorizontalAlignment(Element.ALIGN_RIGHT);
//                            cellt.setVerticalAlignment(Element.ALIGN_BOTTOM);
//                            cellt.setBorder(Rectangle.NO_BORDER);
//                            table3.addCell(cellt);
                        }

                    }

                    cntriga++;

                    amount = 0;
                    totale = 0;
                    commfee = 0;
                    payout = 0;
                    totalepayinoutgeneral = 0;
                    totround = 0;

                }

                cntriga++;
                Row row9 = sheet.createRow((short) cntriga);

                if (i == dati.size() - 2) {

                    //  totalepayinoutgeneral = 0;
                    if (!prossimo.getType().contains("D")) {

                        amount += fd(prossimo.getAmountSenzaFormattazione());
                        totale += fd(prossimo.getTotalSenzaFormattazione());
                        commfee += fd(prossimo.getComfreeSenzaFormattazione());
                        payout += fd(prossimo.getPayinpayoutSenzaFormattazione());
                    }

                    Cell f34 = row9.createCell(1);
                    f34.setCellStyle(style4left);
                    f34.setCellValue(prossimo.getType());

                    Cell f35 = row9.createCell(2);
                    f35.setCellStyle(style4);
                    f35.setCellValue(prossimo.getTill());

                    Cell f36 = row9.createCell(3);
                    f36.setCellStyle(style4);
                    f36.setCellValue(prossimo.getUser());

                    Cell f37 = row9.createCell(4);
                    f37.setCellStyle(style4);
                    f37.setCellValue(prossimo.getNotr());

                    Cell f38 = row9.createCell(5);
                    f38.setCellStyle(style4left);
                    f38.setCellValue(formatStringtoStringDate(prossimo.getTime(), patternsqldate, patternnormdate));

                    Cell f39 = row9.createCell(6);
                    f39.setCellStyle(style4left);
                    f39.setCellValue(prossimo.getCur());

                    Cell f40 = row9.createCell(7);
                    f40.setCellStyle(style4left);
                    f40.setCellValue(prossimo.getKind());

                    Cell f41 = row9.createCell(8);
                    f41.setCellStyle(style4);
                    f41.setCellValue(formatMysqltoDisplay(prossimo.getAmount()));

                    Cell f42 = row9.createCell(9);
                    f42.setCellStyle(style4);
                    f42.setCellValue(formatMysqltoDisplay(prossimo.getRate()));

                    Cell f43 = row9.createCell(10);
                    f43.setCellStyle(style4);
                    f43.setCellValue(formatMysqltoDisplay(prossimo.getTotal()));

                    Cell f44 = row9.createCell(11);
                    f44.setCellStyle(style4);
                    f44.setCellValue(formatMysqltoDisplay(prossimo.getPerc()));

                    Cell f45 = row9.createCell(12);
                    f45.setCellStyle(style4);
                    f45.setCellValue(formatMysqltoDisplay(prossimo.getComfree()));

                    Cell f451 = row9.createCell(13);
                    f451.setCellStyle(style4);
                    f451.setCellValue(formatMysqltoDisplay(prossimo.getRound()));

                    totround = totround + fd((valueOf(prossimo.getRound())));
//                    totround = Utility.roundDouble(totround, 2);

                    if (prossimo.getKind().contains("Buy") && !prossimo.getType().contains("D")) {
                        if (prossimo.getResidentnonresident().equalsIgnoreCase("Resident")) {
                            totaleresidentbuy += fd(prossimo.getTotalSenzaFormattazione());
                            totaleresidentcommfeebuy += (fd(prossimo.getComfreeSenzaFormattazione()) + fd(prossimo.getRound()));

                        } else {
                            totalenonresidentcommfeebuy += (fd(prossimo.getComfreeSenzaFormattazione()) + fd(prossimo.getRound()));
                            totalenonresidentbuy += fd(prossimo.getTotalSenzaFormattazione());
                        }
                    }
                    if (prossimo.getKind().contains("Sell") && !prossimo.getType().contains("D")) {
                        if (prossimo.getResidentnonresident().equalsIgnoreCase("Resident")) {
                            totaleresidentsell += fd(prossimo.getTotalSenzaFormattazione());
                            totaleresidentcommfeesell += (fd(prossimo.getComfreeSenzaFormattazione()) + fd(prossimo.getRound()));
                        } else {
                            totalenonresidentsell += fd(prossimo.getTotalSenzaFormattazione());
                            totalenonresidentcommfeesell += (fd(prossimo.getComfreeSenzaFormattazione()) + fd(prossimo.getRound()));
                        }
                    }

                    if (prossimo.getKind().contains("S")) {

                        Cell f46 = row9.createCell(14);
                        f46.setCellStyle(style4);
                        f46.setCellValue("+" + formatMysqltoDisplay(prossimo.getPayinpayout()));
                        if (!prossimo.getType().contains("D")) {
                            totalepayinoutgeneral += fd(prossimo.getPayinpayoutSenzaFormattazione());
                        }
                    } else {

                        Cell f46 = row9.createCell(14);
                        f46.setCellStyle(style4);
                        f46.setCellValue("-" + formatMysqltoDisplay(prossimo.getPayinpayout()));
                        if (!prossimo.getType().contains("D")) {
                            totalepayinoutgeneral -= fd(prossimo.getPayinpayoutSenzaFormattazione());
                        }
                    }

                    Cell f47 = row9.createCell(15);
                    f47.setCellStyle(style4left);
                    f47.setCellValue(prossimo.getCustomer());

                    Cell f48 = row9.createCell(16);
                    f48.setCellStyle(style4);
                    f48.setCellValue(formatMysqltoDisplay(prossimo.getSpread()));

                    Cell f49 = row9.createCell(17);
                    f49.setCellStyle(style4);
                    f49.setCellValue(prossimo.getPos());

                    cntriga++;
                    cntriga++;
                    Row row9bis = sheet.createRow((short) cntriga);

                    for (int h = 0; h < columnWidths2.length; h++) {

                        if (h == 7) {

                            Cell f50 = row9bis.createCell(h + 1);
                            f50.setCellStyle(style3);
                            f50.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(amount, 2)));

                        } else if (h == 9) {

                            Cell f50 = row9bis.createCell(h + 1);
                            f50.setCellStyle(style3);
                            f50.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(totale, 2)));

                        } else if (h == 11) {

                            Cell f50 = row9bis.createCell(h + 1);
                            f50.setCellStyle(style3);
                            f50.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(commfee, 2)));

                        } else if (h == 12) {

                            Cell f50 = row9bis.createCell(h + 1);
                            f50.setCellStyle(style3);
                            f50.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(totround, 2)));

                        } else if (h == 13) {

                            Cell f50 = row9bis.createCell(h + 1);
                            f50.setCellStyle(style3);
                            f50.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(totalepayinoutgeneral, 2)));

                        } else {

//                            phraset = new Phrase();
//                            phraset.add(new Chunk("", f5_normal));
//                            cellt = new PdfPCell(phraset);
//                            cellt.setHorizontalAlignment(Element.ALIGN_RIGHT);
//                            cellt.setVerticalAlignment(Element.ALIGN_BOTTOM);
//                            cellt.setBorder(Rectangle.NO_BORDER);
//                            table3.addCell(cellt);
                        }

                    }

                    cntriga++;

                }

            }

            cntriga++;
            cntriga++;
            Row row9 = sheet.createRow((short) cntriga);

            Cell f38 = row9.createCell(3);
            f38.setCellStyle(style3);
            f38.setCellValue("Transaction value");

            Cell f41 = row9.createCell(7);
            f41.setCellStyle(style3);
            f41.setCellValue("Commission Value + Round");

            Cell f42 = row9.createCell(11);
            f42.setCellStyle(style3);
            f42.setCellValue("Transacion Number");

            Cell f421 = row9.createCell(14);
            f421.setCellStyle(style3);
            f421.setCellValue("Sell / Internet Booking");

            Cell f422 = row9.createCell(17);
            f422.setCellStyle(style3);
            f422.setCellValue("POS");

            Cell f4221 = row9.createCell(21);
            f4221.setCellStyle(style3);
            f4221.setCellValue("Bank Account");

            cntriga++;
            Row row10 = sheet.createRow((short) cntriga);

            Cell f43 = row10.createCell(2);
            f43.setCellStyle(style3);
            f43.setCellValue("Resident");

            Cell f44 = row10.createCell(3);
            f44.setCellStyle(style3);
            f44.setCellValue("Non resident");

            Cell f45 = row10.createCell(4);
            f45.setCellStyle(style3);
            f45.setCellValue("Total");

            Cell f46 = row10.createCell(6);
            f46.setCellStyle(style3);
            f46.setCellValue("Resident");

            Cell f47 = row10.createCell(7);
            f47.setCellStyle(style3);
            f47.setCellValue("Non resident");

            Cell f48 = row10.createCell(8);
            f48.setCellStyle(style3);
            f48.setCellValue("Total");

            Cell f49 = row10.createCell(10);
            f49.setCellStyle(style3);
            f49.setCellValue("Resident");

            Cell f50 = row10.createCell(11);
            f50.setCellStyle(style3);
            f50.setCellValue("Non resident");

            Cell f51 = row10.createCell(12);
            f51.setCellStyle(style3);
            f51.setCellValue("Total");

            Cell f52 = row10.createCell(14);
            f52.setCellStyle(style3);
            f52.setCellValue("Amount");

            Cell f53 = row10.createCell(15);
            f53.setCellStyle(style3);
            f53.setCellValue("#");

            Cell f54 = row10.createCell(17);
            f54.setCellStyle(style3);
            f54.setCellValue("Amount");

            Cell f55 = row10.createCell(18);
            f55.setCellStyle(style3);
            f55.setCellValue("#");

            Cell f541 = row10.createCell(20);
            f541.setCellStyle(style3);
            f541.setCellValue("Amount");

            Cell f551 = row10.createCell(21);
            f551.setCellStyle(style3);
            f551.setCellValue("#");

            cntriga++;
            Row row11 = sheet.createRow((short) cntriga);

            int cntvalue = 0;

            ArrayList<String> datifooter = siq.getFooterdati();

            ArrayList<Double> totaleresidentnonresperbuy = new ArrayList<>();
            ArrayList<Double> totaleresidentnonrespersell = new ArrayList<>();
            ArrayList<Double> totaledeitotalibuy = new ArrayList<>();
            ArrayList<Double> totaledeitotalisell = new ArrayList<>();

            int cnt = 0;

            for (int n = 0; n < columnWidths4.length; n++) {

                if (n == 4 || n == 8) {

//                    
//                    phraset = new Phrase();
//                    phraset.add(new Chunk("", f5_bold));
//                    cellt = new PdfPCell(phraset);
//                    cellt.setHorizontalAlignment(Element.ALIGN_RIGHT);
//                    cellt.setVerticalAlignment(Element.ALIGN_BOTTOM);
//                    cellt.setBorder(Rectangle.BOX);
//                    table3.addCell(cellt);
                } else if (n == 0) {

                    Cell f56 = row11.createCell(n + 1);
                    f56.setCellStyle(style3);
                    f56.setCellValue("buy");

                } else if (n == 12) {

                    Cell f56 = row11.createCell(n + 1);
                    f56.setCellStyle(style3);
                    f56.setCellValue("yes");

                } else if (n == 15 || n == 18) {

                    Cell f56 = row11.createCell(n + 1);
                    f56.setCellStyle(style3);
                    f56.setCellValue("buy");

                } //else (cntvalue % 2 == 1) {                    
                else if (n == 11) {

                    double s2 = fd(datifooter.get(cntvalue - 2));
                    double s1 = fd(datifooter.get(cntvalue - 1));
                    double tot = s1 + s2;

                    totaledeitotalibuy.add(tot);
                    //    totaleresidentnonresperbuy.add(s2);
                    //  totaleresidentnonresperbuy.add(s1);

                    Cell f56 = row11.createCell(n + 1);
                    f56.setCellStyle(style4);
                    f56.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(tot, 0)));

                } else if (n == 1) {

                    Cell f56 = row11.createCell(n + 1);
                    f56.setCellStyle(style4);
                    f56.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(totaleresidentbuy, 3)));

                } else if (n == 2) {

                    Cell f56 = row11.createCell(n + 1);
                    f56.setCellStyle(style4);
                    f56.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(totalenonresidentbuy, 2)));

                } else if (n == 3) {

                    double totalebuytrans = totaleresidentbuy + totalenonresidentbuy;

                    Cell f56 = row11.createCell(n + 1);
                    f56.setCellStyle(style4);
                    f56.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(totalebuytrans, 2)));

                } else if (n == 5) {

                    Cell f56 = row11.createCell(n + 1);
                    f56.setCellStyle(style4);
                    f56.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(totaleresidentcommfeebuy, 2)));

                } else if (n == 6) {

                    Cell f56 = row11.createCell(n + 1);
                    f56.setCellStyle(style4);
                    f56.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(totalenonresidentcommfeebuy, 2)));

                } else if (n == 7) {
                    double totalebuycommfee = totaleresidentcommfeebuy + totalenonresidentcommfeebuy;

                    Cell f56 = row11.createCell(n + 1);
                    f56.setCellStyle(style4);
                    f56.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(totalebuycommfee, 2)));

                } else {

                    totaleresidentnonresperbuy.add(fd(datifooter.get(cntvalue)));

                    Cell f56 = row11.createCell(n + 1);
                    f56.setCellStyle(style4);
                    f56.setCellValue(formatMysqltoDisplay(datifooter.get(cntvalue)));

                    cntvalue++;
                }

            }

            cntriga++;
            Row row12 = sheet.createRow((short) cntriga);

            for (int n = 0; n < columnWidths4.length; n++) {

                if (n == 4 || n == 8) {
//                    phraset = new Phrase();
//                    phraset.add(new Chunk("", f5_bold));
//                    cellt = new PdfPCell(phraset);
//                    cellt.setHorizontalAlignment(Element.ALIGN_RIGHT);
//                    cellt.setVerticalAlignment(Element.ALIGN_BOTTOM);
//                    cellt.setBorder(Rectangle.BOX);
//                    table3.addCell(cellt);
                } else if (n == 0) {

                    Cell f56 = row12.createCell(n + 1);
                    f56.setCellStyle(style3);
                    f56.setCellValue("sell");

                } else if (n == 12) {

                    Cell f56 = row12.createCell(n + 1);
                    f56.setCellStyle(style3);
                    f56.setCellValue("no");

                } else if (n == 15 || n == 18) {

                    Cell f56 = row12.createCell(n + 1);
                    f56.setCellStyle(style3);
                    f56.setCellValue("sell");

                } else if (n == 11) {
                    double s2 = fd(datifooter.get(cntvalue - 2));
                    double s1 = fd(datifooter.get(cntvalue - 1));
                    double tot = s1 + s2;

                    totaledeitotalisell.add(tot);
                    //  totaleresidentnonrespersell.add(s2);
                    //totaleresidentnonrespersell.add(s1);

                    Cell f56 = row12.createCell(n + 1);
                    f56.setCellStyle(style4);
                    f56.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(tot, 0)));

                } else if (n == 1) {

                    Cell f56 = row12.createCell(n + 1);
                    f56.setCellStyle(style4);
                    f56.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(totaleresidentsell, 2)));

                } else if (n == 2) {

                    Cell f56 = row12.createCell(n + 1);
                    f56.setCellStyle(style4);
                    f56.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(totalenonresidentsell, 2)));

                } else if (n == 3) {

                    double totaleselltrans = totaleresidentsell + totalenonresidentsell;
                    Cell f56 = row12.createCell(n + 1);
                    f56.setCellStyle(style4);
                    f56.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(totaleselltrans, 2)));

                } else if (n == 5) {

                    Cell f56 = row12.createCell(n + 1);
                    f56.setCellStyle(style4);
                    f56.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(totaleresidentcommfeesell, 2)));

                } else if (n == 6) {

                    Cell f56 = row12.createCell(n + 1);
                    f56.setCellStyle(style4);
                    f56.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(totalenonresidentcommfeesell, 2)));

                } else if (n == 7) {
                    double totalesellcommfee = totaleresidentcommfeesell + totalenonresidentcommfeesell;

                    Cell f56 = row12.createCell(n + 1);
                    f56.setCellStyle(style4);
                    f56.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(totalesellcommfee, 2)));

                } else {
                    totaleresidentnonrespersell.add(fd(datifooter.get(cntvalue)));

                    Cell f56 = row12.createCell(n + 1);
                    f56.setCellStyle(style4);
                    f56.setCellValue(formatMysqltoDisplay(datifooter.get(cntvalue)));

                    cntvalue++;
                }

            }

            cntriga++;
            Row row13 = sheet.createRow((short) cntriga);

            int cnttotali = 0;
            int cntresnonres = 0;

            for (int n = 0; n < columnWidths4.length; n++) {

                if (n == 4 || n == 8) {
//                    phraset = new Phrase();
//                    phraset.add(new Chunk("", f5_bold));
//                    cellt = new PdfPCell(phraset);
//                    cellt.setHorizontalAlignment(Element.ALIGN_RIGHT);
//                    cellt.setVerticalAlignment(Element.ALIGN_BOTTOM);
//                    cellt.setBorder(Rectangle.BOX);
//                    table3.addCell(cellt);

                } else if (n == 0) {

                    Cell f56 = row13.createCell(n + 1);
                    f56.setCellStyle(style3);
                    f56.setCellValue("total");

                } else if (n == 12) {

                    Cell f56 = row13.createCell(n + 1);
                    f56.setCellStyle(style3);
                    f56.setCellValue("total");

                } else if (n == 15 || n == 18) {

                    Cell f56 = row13.createCell(n + 1);
                    f56.setCellStyle(style3);
                    f56.setCellValue("total");

                } else if (n == 11) {

                    double tot1 = (double) totaledeitotalibuy.get(cnttotali);
                    double tot2 = (double) totaledeitotalisell.get(cnttotali);

                    Cell f56 = row13.createCell(n + 1);
                    f56.setCellStyle(style4);
                    f56.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(tot1 + tot2, 0)));

                    cnttotali++;
                } else if (n == 1) {

                    double totaleresidentsellbuy = totaleresidentbuy + totaleresidentsell;

                    Cell f56 = row13.createCell(n + 1);
                    f56.setCellStyle(style4);
                    f56.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(totaleresidentsellbuy, 2)));

                } else if (n == 2) {

                    double totalenonresidentbuysell = totalenonresidentbuy + totalenonresidentsell;

                    Cell f56 = row13.createCell(n + 1);
                    f56.setCellStyle(style4);
                    f56.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(totalenonresidentbuysell, 2)));

                } else if (n == 3) {

                    double totalebuysellgenerale = totaleresidentbuy + totalenonresidentbuy + totaleresidentsell + totalenonresidentsell;

                    Cell f56 = row13.createCell(n + 1);
                    f56.setCellStyle(style4);
                    f56.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(totalebuysellgenerale, 2)));

                } else if (n == 5) {

                    double totaleresidentcommfeebuysell = totaleresidentcommfeebuy + totaleresidentcommfeesell;

                    Cell f56 = row13.createCell(n + 1);
                    f56.setCellStyle(style4);
                    f56.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(totaleresidentcommfeebuysell, 2)));

                } else if (n == 6) {
                    double totalenonresidentcommfeebuysell = totalenonresidentcommfeebuy + totalenonresidentcommfeesell;

                    Cell f56 = row13.createCell(n + 1);
                    f56.setCellStyle(style4);
                    f56.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(totalenonresidentcommfeebuysell, 2)));

                } else if (n == 7) {
                    double totalegeneralecommfee = totaleresidentcommfeesell + totaleresidentcommfeebuy + totalenonresidentcommfeebuy + totalenonresidentcommfeesell;

                    Cell f56 = row13.createCell(n + 1);
                    f56.setCellStyle(style4);
                    f56.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(totalegeneralecommfee, 2)));

                } else {
                    double tot3 = (double) (totaleresidentnonresperbuy.get(cntresnonres));
                    double tot4 = (double) (totaleresidentnonrespersell.get(cntresnonres));

                    Cell f56 = row13.createCell(n + 1);
                    f56.setCellStyle(style4);
                    if (n == 9 || n == 10 || n == 14 || n == 17 || n == 20) {
                        f56.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(tot3 + tot4, 0)));
                    } else {
                        f56.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(tot3 + tot4, 2)));
                    }

                    cntresnonres++;
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

    /**
     *
     * @param path
     * @param siq
     * @param datereport1
     * @param datereport2
     * @return
     */
    public String receipt(String path, TillTransactionListCurrency_value siq, String datereport1, String datereport2) {

        //String outputfile = "TillTransactionListCurrency.pdf";
        try {
            File pdf = new File(path + generaId(50) + "TillTransactionListCurrency.pdf");
            Document document = new Document(A4.rotate(), 20, 20, 20, 20);
            try(OutputStream ou = new FileOutputStream(pdf)){
            PdfWriter wr = getInstance(document, ou);
            document.open();

            PdfPTable table = new PdfPTable(2);
            table.setWidths(columnWidths0);
            table.setWidthPercentage(100);

            Phrase phrase1 = new Phrase();
            phrase1.add(new Chunk(intestazionePdf + " From " + datereport1 + " To " + datereport2, f2_bold));
            PdfPCell cell1 = new PdfPCell(phrase1);
            cell1.setBorder(NO_BORDER);

            Phrase phrase3 = new Phrase();
            phrase3.add(new Chunk("\n " + siq.getId_filiale() + " " + siq.getDe_filiale(), f3_normal));
            PdfPCell cell3 = new PdfPCell(phrase3);
            cell3.setBorder(NO_BORDER);

            table.addCell(cell1);
            table.addCell(cellavuota);
            table.addCell(cell3);
            table.addCell(cellavuota);
            document.add(table);
            vuoto.setFont(f3_normal);
            document.add(cellavuota);

            document.add(vuoto);

            scriviIntestazioneColonne(document);

            //Popolo la tabella
            PdfPTable table3, table6;
            ArrayList<TillTransactionListCurrency_value> dati = siq.getDati();
            Phrase phraset;
            PdfPCell cellt;

            table3 = new PdfPTable(17);
            table3.setWidths(columnWidths2);
            table3.setWidthPercentage(100);

            double amount = 0.00, totale = 0.00, commfee = 0.00, payout = 0.00;

            double totaleresidentbuy = 0.00, totalenonresidentbuy = 0.00, totaleresidentsell = 0.00, totalenonresidentsell = 0, totaleresidentcommfeebuy = 0, totalenonresidentcommfeebuy = 0, totaleresidentcommfeesell = 0, totalenonresidentcommfeesell = 0;

            double totalepayinoutgeneral = 0.00;

            double totround = 0.00;

            for (int i = 0; i < dati.size() - 1; i++) {

                TillTransactionListCurrency_value actual = (TillTransactionListCurrency_value) dati.get(i);
                TillTransactionListCurrency_value prossimo = (TillTransactionListCurrency_value) dati.get(i + 1);

                if (!actual.getType().contains("D")) {

                    amount += fd(actual.getAmountSenzaFormattazione());
                    totale += fd(actual.getTotalSenzaFormattazione());
                    commfee += fd(actual.getComfreeSenzaFormattazione());
                    payout += fd(actual.getPayinpayoutSenzaFormattazione());
                }

                phraset = new Phrase();
                phraset.add(new Chunk(actual.getType(), f5_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_LEFT);
                cellt.setVerticalAlignment(ALIGN_BOTTOM);
                cellt.setBorder(NO_BORDER);
                table3.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(actual.getTill(), f5_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setVerticalAlignment(ALIGN_BOTTOM);
                cellt.setBorder(NO_BORDER);
                table3.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(actual.getUser(), f5_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setVerticalAlignment(ALIGN_BOTTOM);
                cellt.setBorder(NO_BORDER);
                table3.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(actual.getNotr(), f5_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setVerticalAlignment(ALIGN_BOTTOM);
                cellt.setBorder(NO_BORDER);
                table3.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(formatStringtoStringDate(actual.getTime(), patternsqldate, patternnormdate), f5_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_LEFT);
                cellt.setVerticalAlignment(ALIGN_BOTTOM);
                cellt.setBorder(NO_BORDER);
                table3.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(actual.getCur(), f5_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_LEFT);
                cellt.setVerticalAlignment(ALIGN_BOTTOM);
                cellt.setBorder(NO_BORDER);
                table3.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(actual.getKind(), f5_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_LEFT);
                cellt.setVerticalAlignment(ALIGN_BOTTOM);
                cellt.setBorder(NO_BORDER);
                table3.addCell(cellt);
                if (actual.getKind().contains("Buy") && !actual.getType().contains("D")) {
                    if (actual.getResidentnonresident().equalsIgnoreCase("Resident")) {
                        totaleresidentbuy += fd(actual.getTotalSenzaFormattazione());
                        totaleresidentcommfeebuy += (fd(actual.getComfreeSenzaFormattazione()) + fd(actual.getRound()));
                    } else {
                        totalenonresidentcommfeebuy += (fd(actual.getComfreeSenzaFormattazione()) + fd(actual.getRound()));
                        totalenonresidentbuy += fd(actual.getTotalSenzaFormattazione());
                    }
                } else if (actual.getKind().contains("Sell") && !actual.getType().contains("D")) {
                    if (actual.getResidentnonresident().equalsIgnoreCase("Resident")) {
                        totaleresidentsell += fd(actual.getTotalSenzaFormattazione());
                        totaleresidentcommfeesell += (fd(actual.getComfreeSenzaFormattazione()) + fd(actual.getRound()));
                    } else {
                        totalenonresidentsell += fd(actual.getTotalSenzaFormattazione());
                        totalenonresidentcommfeesell += (fd(actual.getComfreeSenzaFormattazione()) + fd(actual.getRound()));
                    }
                }

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(actual.getAmount()), f5_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setVerticalAlignment(ALIGN_BOTTOM);
                cellt.setBorder(NO_BORDER);
                table3.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(actual.getRate()), f5_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setVerticalAlignment(ALIGN_BOTTOM);
                cellt.setBorder(NO_BORDER);
                table3.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(actual.getTotal()), f5_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setVerticalAlignment(ALIGN_BOTTOM);
                cellt.setBorder(NO_BORDER);
                table3.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(actual.getPerc()), f5_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setVerticalAlignment(ALIGN_BOTTOM);
                cellt.setBorder(NO_BORDER);
                table3.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(actual.getComfree()), f5_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setVerticalAlignment(ALIGN_BOTTOM);
                cellt.setBorder(NO_BORDER);
                table3.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(actual.getRound()), f5_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setVerticalAlignment(ALIGN_BOTTOM);
                cellt.setBorder(NO_BORDER);
                table3.addCell(cellt);

                if (!actual.getType().contains("D")) {
                    totround = totround + fd((valueOf(actual.getRound())));
                    totround = roundDouble(totround, 2);
                }

                if (actual.getKind().contains("Sell")) {

                    phraset = new Phrase();
                    phraset.add(new Chunk("+" + formatMysqltoDisplay(actual.getPayinpayout()), f5_normal));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setVerticalAlignment(ALIGN_BOTTOM);
                    cellt.setBorder(NO_BORDER);
                    table3.addCell(cellt);

                    if (!actual.getType().contains("D")) {

                        totalepayinoutgeneral += fd(actual.getPayinpayoutSenzaFormattazione());
                    }
                } else {

                    phraset = new Phrase();
                    phraset.add(new Chunk("-" + formatMysqltoDisplay(actual.getPayinpayout()), f5_normal));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setVerticalAlignment(ALIGN_BOTTOM);
                    cellt.setBorder(NO_BORDER);
                    table3.addCell(cellt);
                    if (!actual.getType().contains("D")) {
                        totalepayinoutgeneral -= fd(actual.getPayinpayoutSenzaFormattazione());
                    }
                }

                phraset = new Phrase();
                phraset.add(new Chunk(actual.getCustomer(), f5_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_LEFT);
                cellt.setVerticalAlignment(ALIGN_RIGHT);
                cellt.setBorder(NO_BORDER);
                table3.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(actual.getSpread()), f5_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setVerticalAlignment(ALIGN_BOTTOM);
                cellt.setBorder(NO_BORDER);
                table3.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(actual.getPos(), f5_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setVerticalAlignment(ALIGN_BOTTOM);
                cellt.setBorder(NO_BORDER);
                table3.addCell(cellt);

                document.add(table3);

                table3 = new PdfPTable(17);
                table3.setWidths(columnWidths2);
                table3.setWidthPercentage(100);

                if (!prossimo.getCur().equals(actual.getCur())) {

                    for (int h = 0; h < columnWidths2.length; h++) {

                        if (h == 7) {
                            phraset = new Phrase();
                            phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(amount, 2)), f5_bold));
                            cellt = new PdfPCell(phraset);
                            cellt.setHorizontalAlignment(ALIGN_RIGHT);
                            cellt.setVerticalAlignment(ALIGN_BOTTOM);
                            cellt.setBorder(NO_BORDER);
                            table3.addCell(cellt);
                        } else if (h == 9) {
                            phraset = new Phrase();
                            phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(totale, 2)), f5_bold));
                            cellt = new PdfPCell(phraset);
                            cellt.setHorizontalAlignment(ALIGN_RIGHT);
                            cellt.setVerticalAlignment(ALIGN_BOTTOM);
                            cellt.setBorder(NO_BORDER);
                            table3.addCell(cellt);
                        } else if (h == 11) {
                            phraset = new Phrase();
                            phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(commfee, 2)), f5_bold));
                            cellt = new PdfPCell(phraset);
                            cellt.setHorizontalAlignment(ALIGN_RIGHT);
                            cellt.setVerticalAlignment(ALIGN_BOTTOM);
                            cellt.setBorder(NO_BORDER);
                            table3.addCell(cellt);
                        } else if (h == 12) {
                            phraset = new Phrase();
                            phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(totround, 2)), f5_bold));
                            cellt = new PdfPCell(phraset);
                            cellt.setHorizontalAlignment(ALIGN_RIGHT);
                            cellt.setVerticalAlignment(ALIGN_BOTTOM);
                            cellt.setBorder(NO_BORDER);
                            table3.addCell(cellt);
                        } else if (h == 13) {
                            phraset = new Phrase();
                            phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(totalepayinoutgeneral, 2)), f5_bold));
                            cellt = new PdfPCell(phraset);
                            cellt.setHorizontalAlignment(ALIGN_RIGHT);
                            cellt.setVerticalAlignment(ALIGN_BOTTOM);
                            cellt.setBorder(NO_BORDER);
                            table3.addCell(cellt);
                        } else {
                            phraset = new Phrase();
                            phraset.add(new Chunk("", f5_bold));
                            cellt = new PdfPCell(phraset);
                            cellt.setHorizontalAlignment(ALIGN_RIGHT);
                            cellt.setVerticalAlignment(ALIGN_BOTTOM);
                            cellt.setBorder(NO_BORDER);
                            table3.addCell(cellt);

                        }

                    }

                    document.add(table3);

                    LineSeparator ls = new LineSeparator();
                    ls.setLineWidth(0.5f);
                    document.add(ls);
                    ls.setOffset(-1.5f);
                    document.add(ls);

                    table3 = new PdfPTable(17);
                    table3.setWidths(columnWidths2);
                    table3.setWidthPercentage(100);

                    amount = 0;
                    totale = 0;
                    commfee = 0;
                    payout = 0;
                    totalepayinoutgeneral = 0;

                }

                if (i == dati.size() - 2) {

                    //  totalepayinoutgeneral = 0;
                    // totround = 0;
                    if (!prossimo.getType().equals("D")) {

                        amount += fd(prossimo.getAmountSenzaFormattazione());
                        totale += fd(prossimo.getTotalSenzaFormattazione());
                        commfee += fd(prossimo.getComfreeSenzaFormattazione());
                        payout += fd(prossimo.getPayinpayoutSenzaFormattazione());

                    }

                    phraset = new Phrase();
                    phraset.add(new Chunk(prossimo.getType(), f5_normal));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_LEFT);
                    cellt.setVerticalAlignment(ALIGN_BOTTOM);
                    cellt.setBorder(NO_BORDER);
                    table3.addCell(cellt);

                    phraset = new Phrase();
                    phraset.add(new Chunk(prossimo.getTill(), f5_normal));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setVerticalAlignment(ALIGN_BOTTOM);
                    cellt.setBorder(NO_BORDER);
                    table3.addCell(cellt);

                    phraset = new Phrase();
                    phraset.add(new Chunk(prossimo.getUser(), f5_normal));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setVerticalAlignment(ALIGN_BOTTOM);
                    cellt.setBorder(NO_BORDER);
                    table3.addCell(cellt);

                    phraset = new Phrase();
                    phraset.add(new Chunk(prossimo.getNotr(), f5_normal));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setVerticalAlignment(ALIGN_BOTTOM);
                    cellt.setBorder(NO_BORDER);
                    table3.addCell(cellt);

                    phraset = new Phrase();
                    phraset.add(new Chunk(formatStringtoStringDate(prossimo.getTime(), patternsqldate, patternnormdate), f5_normal));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_LEFT);
                    cellt.setVerticalAlignment(ALIGN_BOTTOM);
                    cellt.setBorder(NO_BORDER);
                    table3.addCell(cellt);

                    phraset = new Phrase();
                    phraset.add(new Chunk(prossimo.getCur(), f5_normal));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_LEFT);
                    cellt.setVerticalAlignment(ALIGN_BOTTOM);
                    cellt.setBorder(NO_BORDER);
                    table3.addCell(cellt);

                    phraset = new Phrase();
                    phraset.add(new Chunk(prossimo.getKind(), f5_normal));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_LEFT);
                    cellt.setVerticalAlignment(ALIGN_BOTTOM);
                    cellt.setBorder(NO_BORDER);
                    table3.addCell(cellt);

                    phraset = new Phrase();
                    phraset.add(new Chunk(formatMysqltoDisplay(prossimo.getAmount()), f5_normal));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setVerticalAlignment(ALIGN_BOTTOM);
                    cellt.setBorder(NO_BORDER);
                    table3.addCell(cellt);

                    phraset = new Phrase();
                    phraset.add(new Chunk(formatMysqltoDisplay(prossimo.getRate()), f5_normal));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setVerticalAlignment(ALIGN_BOTTOM);
                    cellt.setBorder(NO_BORDER);
                    table3.addCell(cellt);

                    phraset = new Phrase();
                    phraset.add(new Chunk(formatMysqltoDisplay(prossimo.getTotal()), f5_normal));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setVerticalAlignment(ALIGN_BOTTOM);
                    cellt.setBorder(NO_BORDER);
                    table3.addCell(cellt);

                    phraset = new Phrase();
                    phraset.add(new Chunk(formatMysqltoDisplay(prossimo.getPerc()), f5_normal));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setVerticalAlignment(ALIGN_BOTTOM);
                    cellt.setBorder(NO_BORDER);
                    table3.addCell(cellt);

                    phraset = new Phrase();
                    phraset.add(new Chunk(formatMysqltoDisplay(prossimo.getComfree()), f5_normal));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setVerticalAlignment(ALIGN_BOTTOM);
                    cellt.setBorder(NO_BORDER);
                    table3.addCell(cellt);

                    phraset = new Phrase();
                    phraset.add(new Chunk(formatMysqltoDisplay(prossimo.getRound()), f5_normal));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setVerticalAlignment(ALIGN_BOTTOM);
                    cellt.setBorder(NO_BORDER);
                    table3.addCell(cellt);

                    totround = totround + fd((valueOf(actual.getRound())));
                    totround = roundDouble(totround, 2);

                    if (prossimo.getKind().contains("Buy") && !prossimo.getType().contains("D")) {
                        if (prossimo.getResidentnonresident().equalsIgnoreCase("Resident")) {
                            totaleresidentbuy += fd(prossimo.getTotalSenzaFormattazione());
                            totaleresidentcommfeebuy += (fd(prossimo.getComfreeSenzaFormattazione() + fd(prossimo.getRound())));

                        } else {
                            totalenonresidentcommfeebuy += (fd(prossimo.getComfreeSenzaFormattazione()) + fd(prossimo.getRound()));
                            totalenonresidentbuy += fd(prossimo.getTotalSenzaFormattazione());
                        }
                    }
                    if (prossimo.getKind().contains("Sell") && !prossimo.getType().contains("D")) {
                        if (prossimo.getResidentnonresident().equalsIgnoreCase("Resident")) {

                            totaleresidentsell += fd(prossimo.getTotalSenzaFormattazione());
                            totaleresidentcommfeesell += (fd(prossimo.getComfreeSenzaFormattazione()) + fd(prossimo.getRound()));
                        } else {
                            totalenonresidentsell += fd(prossimo.getTotalSenzaFormattazione());
                            totalenonresidentcommfeesell += (fd(prossimo.getComfreeSenzaFormattazione()) + fd(prossimo.getRound()));
                        }
                    }

                    if (prossimo.getKind().contains("S")) {

                        phraset = new Phrase();
                        phraset.add(new Chunk("+" + formatMysqltoDisplay(prossimo.getPayinpayout()), f5_normal));
                        cellt = new PdfPCell(phraset);
                        cellt.setHorizontalAlignment(ALIGN_RIGHT);
                        cellt.setVerticalAlignment(ALIGN_BOTTOM);
                        cellt.setBorder(NO_BORDER);
                        table3.addCell(cellt);

                        if (!prossimo.getType().contains("D")) {
                            totalepayinoutgeneral += fd(prossimo.getPayinpayoutSenzaFormattazione());
                        }
                    } else {

                        phraset = new Phrase();
                        phraset.add(new Chunk("-" + formatMysqltoDisplay(prossimo.getPayinpayout()), f5_normal));
                        cellt = new PdfPCell(phraset);
                        cellt.setHorizontalAlignment(ALIGN_RIGHT);
                        cellt.setVerticalAlignment(ALIGN_BOTTOM);
                        cellt.setBorder(NO_BORDER);
                        table3.addCell(cellt);
                        if (!prossimo.getType().contains("D")) {
                            totalepayinoutgeneral -= fd(prossimo.getPayinpayoutSenzaFormattazione());
                        }
                    }

                    phraset = new Phrase();
                    phraset.add(new Chunk(prossimo.getCustomer(), f5_normal));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_LEFT);
                    cellt.setVerticalAlignment(ALIGN_BOTTOM);
                    cellt.setBorder(NO_BORDER);
                    table3.addCell(cellt);

                    phraset = new Phrase();
                    phraset.add(new Chunk(formatMysqltoDisplay(prossimo.getSpread()), f5_normal));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setVerticalAlignment(ALIGN_BOTTOM);
                    cellt.setBorder(NO_BORDER);
                    table3.addCell(cellt);

                    phraset = new Phrase();
                    phraset.add(new Chunk(prossimo.getPos(), f5_normal));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setVerticalAlignment(ALIGN_BOTTOM);
                    cellt.setBorder(NO_BORDER);
                    table3.addCell(cellt);

                    for (int h = 0; h < columnWidths2.length; h++) {

                        if (h == 7) {
                            phraset = new Phrase();
                            phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(amount, 2)), f5_bold));
                            cellt = new PdfPCell(phraset);
                            cellt.setHorizontalAlignment(ALIGN_RIGHT);
                            cellt.setVerticalAlignment(ALIGN_BOTTOM);
                            cellt.setBorder(NO_BORDER);
                            table3.addCell(cellt);
                        } else if (h == 9) {
                            phraset = new Phrase();
                            phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(totale, 2)), f5_bold));
                            cellt = new PdfPCell(phraset);
                            cellt.setHorizontalAlignment(ALIGN_RIGHT);
                            cellt.setVerticalAlignment(ALIGN_BOTTOM);
                            cellt.setBorder(NO_BORDER);
                            table3.addCell(cellt);
                        } else if (h == 11) {
                            phraset = new Phrase();
                            phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(commfee, 2)), f5_bold));
                            cellt = new PdfPCell(phraset);
                            cellt.setHorizontalAlignment(ALIGN_RIGHT);
                            cellt.setVerticalAlignment(ALIGN_BOTTOM);
                            cellt.setBorder(NO_BORDER);
                            table3.addCell(cellt);
                        } else if (h == 12) {
                            phraset = new Phrase();
                            phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(totround, 2)), f5_bold));
                            cellt = new PdfPCell(phraset);
                            cellt.setHorizontalAlignment(ALIGN_RIGHT);
                            cellt.setVerticalAlignment(ALIGN_BOTTOM);
                            cellt.setBorder(NO_BORDER);
                            table3.addCell(cellt);
                        } else if (h == 13) {
                            phraset = new Phrase();
                            phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(totalepayinoutgeneral, 2)), f5_bold));
                            cellt = new PdfPCell(phraset);
                            cellt.setHorizontalAlignment(ALIGN_RIGHT);
                            cellt.setVerticalAlignment(ALIGN_BOTTOM);
                            cellt.setBorder(NO_BORDER);
                            table3.addCell(cellt);
                        } else {

                            phraset = new Phrase();
                            phraset.add(new Chunk("", f5_normal));
                            cellt = new PdfPCell(phraset);
                            cellt.setHorizontalAlignment(ALIGN_RIGHT);
                            cellt.setVerticalAlignment(ALIGN_BOTTOM);
                            cellt.setBorder(NO_BORDER);
                            table3.addCell(cellt);

                        }

                    }

                    document.add(table3);

                    table3 = new PdfPTable(17);
                    table3.setWidths(columnWidths2);
                    table3.setWidthPercentage(100);

                }

            }

            //   document.add(table3);
            LineSeparator ls = new LineSeparator();
            ls.setLineWidth(0.5f);
            document.add(ls);
            ls.setOffset(-1.5f);
            document.add(ls);

            table3 = new PdfPTable(21);
            table3.setWidths(columnWidths4);
            table3.setWidthPercentage(100);

            phraset = new Phrase();
            phraset.add(new Chunk("Transaction value", f5_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_CENTER);
            cellt.setVerticalAlignment(ALIGN_BOTTOM);
            cellt.setBorder(NO_BORDER);
            cellt.setColspan(4);
            table3.addCell(cellt);

//            phraset = new Phrase();
//            phraset.add(new Chunk("", f5_bold));
//            cellt = new PdfPCell(phraset);
//            cellt.setHorizontalAlignment(Element.ALIGN_RIGHT);
//            cellt.setVerticalAlignment(Element.ALIGN_BOTTOM);
//            cellt.setBorder(Rectangle.NO_BORDER);
//            table3.addCell(cellt);
            phraset = new Phrase();
            phraset.add(new Chunk("Commissions value + Round", f5_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_CENTER);
            cellt.setVerticalAlignment(ALIGN_BOTTOM);
            cellt.setBorder(NO_BORDER);
            cellt.setColspan(4);
            table3.addCell(cellt);

//            phraset = new Phrase();
//            phraset.add(new Chunk("", f5_bold));
//            cellt = new PdfPCell(phraset);
//            cellt.setHorizontalAlignment(Element.ALIGN_RIGHT);
//            cellt.setVerticalAlignment(Element.ALIGN_BOTTOM);
//            cellt.setBorder(Rectangle.NO_BORDER);
//            table3.addCell(cellt);
            phraset = new Phrase();
            phraset.add(new Chunk("Transaction number", f5_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_CENTER);
            cellt.setVerticalAlignment(ALIGN_BOTTOM);
            cellt.setBorder(NO_BORDER);
            cellt.setColspan(4);
            table3.addCell(cellt);

//            phraset = new Phrase();
//            phraset.add(new Chunk("", f5_bold));
//            cellt = new PdfPCell(phraset);
//            cellt.setHorizontalAlignment(Element.ALIGN_RIGHT);
//            cellt.setVerticalAlignment(Element.ALIGN_BOTTOM);
//            cellt.setBorder(Rectangle.NO_BORDER);
//            table3.addCell(cellt);
            phraset = new Phrase();
            phraset.add(new Chunk("Sell / Internet booking", f5_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_CENTER);
            cellt.setVerticalAlignment(ALIGN_BOTTOM);
            cellt.setBorder(NO_BORDER);
            cellt.setColspan(3);
            table3.addCell(cellt);

//            phraset = new Phrase();
//            phraset.add(new Chunk("", f5_bold));
//            cellt = new PdfPCell(phraset);
//            cellt.setHorizontalAlignment(Element.ALIGN_RIGHT);
//            cellt.setVerticalAlignment(Element.ALIGN_BOTTOM);
//            cellt.setBorder(Rectangle.NO_BORDER);
//            table3.addCell(cellt);
            phraset = new Phrase();
            phraset.add(new Chunk("POS", f5_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_CENTER);
            cellt.setVerticalAlignment(ALIGN_BOTTOM);
            cellt.setBorder(NO_BORDER);
            cellt.setColspan(3);
            table3.addCell(cellt);

//            phraset = new Phrase();
//            phraset.add(new Chunk("", f5_bold));
//            cellt = new PdfPCell(phraset);
//            cellt.setHorizontalAlignment(Element.ALIGN_RIGHT);
//            cellt.setVerticalAlignment(Element.ALIGN_BOTTOM);
//            cellt.setBorder(Rectangle.NO_BORDER);
//            table3.addCell(cellt);
//            phraset = new Phrase();
//            phraset.add(new Chunk("", f5_bold));
//            cellt = new PdfPCell(phraset);
//            cellt.setHorizontalAlignment(Element.ALIGN_RIGHT);
//            cellt.setVerticalAlignment(Element.ALIGN_BOTTOM);
//            cellt.setBorder(Rectangle.NO_BORDER);
//            table3.addCell(cellt);
            phraset = new Phrase();
            phraset.add(new Chunk("Bank Account", f5_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_CENTER);
            cellt.setVerticalAlignment(ALIGN_BOTTOM);
            cellt.setBorder(NO_BORDER);
            cellt.setColspan(3);
            table3.addCell(cellt);

            table3.setSpacingBefore(15);

            document.add(table3);

            table3 = new PdfPTable(21);
            table3.setWidths(columnWidths4);
            table3.setWidthPercentage(100);

            phraset = new Phrase();
            phraset.add(new Chunk("", f5_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setVerticalAlignment(ALIGN_BOTTOM);
            cellt.setBorder(NO_BORDER);
            table3.addCell(cellt);

            phraset = new Phrase();
            phraset.add(new Chunk("Resident", f5_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setVerticalAlignment(ALIGN_BOTTOM);
            cellt.setBorder(BOX);
            table3.addCell(cellt);

            phraset = new Phrase();
            phraset.add(new Chunk("Non resident", f5_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setVerticalAlignment(ALIGN_BOTTOM);
            cellt.setBorder(BOX);
            table3.addCell(cellt);

            phraset = new Phrase();
            phraset.add(new Chunk("Total", f5_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setVerticalAlignment(ALIGN_BOTTOM);
            cellt.setBorder(BOX);
            table3.addCell(cellt);

            phraset = new Phrase();
            phraset.add(new Chunk("", f5_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setVerticalAlignment(ALIGN_BOTTOM);
            cellt.setBorder(NO_BORDER);
            table3.addCell(cellt);

            phraset = new Phrase();
            phraset.add(new Chunk("Resident", f5_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setVerticalAlignment(ALIGN_BOTTOM);
            cellt.setBorder(BOX);
            table3.addCell(cellt);

            phraset = new Phrase();
            phraset.add(new Chunk("Non resident", f5_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setVerticalAlignment(ALIGN_BOTTOM);
            cellt.setBorder(BOX);
            table3.addCell(cellt);

            phraset = new Phrase();
            phraset.add(new Chunk("Total", f5_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setVerticalAlignment(ALIGN_BOTTOM);
            cellt.setBorder(BOX);
            table3.addCell(cellt);

            phraset = new Phrase();
            phraset.add(new Chunk("", f5_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setVerticalAlignment(ALIGN_BOTTOM);
            cellt.setBorder(NO_BORDER);
            table3.addCell(cellt);

            phraset = new Phrase();
            phraset.add(new Chunk("Resident", f5_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setVerticalAlignment(ALIGN_BOTTOM);
            cellt.setBorder(BOX);
            table3.addCell(cellt);

            phraset = new Phrase();
            phraset.add(new Chunk("Non resident", f5_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setVerticalAlignment(ALIGN_BOTTOM);
            cellt.setBorder(BOX);
            table3.addCell(cellt);

            phraset = new Phrase();
            phraset.add(new Chunk("Total", f5_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setVerticalAlignment(ALIGN_BOTTOM);
            cellt.setBorder(BOX);
            table3.addCell(cellt);

            phraset = new Phrase();
            phraset.add(new Chunk("", f5_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setVerticalAlignment(ALIGN_BOTTOM);
            cellt.setBorder(NO_BORDER);
            table3.addCell(cellt);

            phraset = new Phrase();
            phraset.add(new Chunk("Amount", f5_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setVerticalAlignment(ALIGN_BOTTOM);
            cellt.setBorder(BOX);
            table3.addCell(cellt);

            phraset = new Phrase();
            phraset.add(new Chunk("#", f5_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setVerticalAlignment(ALIGN_BOTTOM);
            cellt.setBorder(BOX);
            table3.addCell(cellt);

            phraset = new Phrase();
            phraset.add(new Chunk("", f5_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setVerticalAlignment(ALIGN_BOTTOM);
            cellt.setBorder(NO_BORDER);
            table3.addCell(cellt);

            phraset = new Phrase();
            phraset.add(new Chunk("Amount", f5_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setVerticalAlignment(ALIGN_BOTTOM);
            cellt.setBorder(BOX);
            table3.addCell(cellt);

            phraset = new Phrase();
            phraset.add(new Chunk("#", f5_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setVerticalAlignment(ALIGN_BOTTOM);
            cellt.setBorder(BOX);
            table3.addCell(cellt);

            phraset = new Phrase();
            phraset.add(new Chunk("", f5_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setVerticalAlignment(ALIGN_BOTTOM);
            cellt.setBorder(NO_BORDER);
            table3.addCell(cellt);

            phraset = new Phrase();
            phraset.add(new Chunk("Amount", f5_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setVerticalAlignment(ALIGN_BOTTOM);
            cellt.setBorder(BOX);
            table3.addCell(cellt);

            phraset = new Phrase();
            phraset.add(new Chunk("#", f5_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setVerticalAlignment(ALIGN_BOTTOM);
            cellt.setBorder(BOX);
            table3.addCell(cellt);

            document.add(table3);

            table3 = new PdfPTable(21);
            table3.setWidths(columnWidths4);
            table3.setWidthPercentage(100);
            int cntvalue = 0;

            ArrayList<String> datifooter = siq.getFooterdati();
            ArrayList<Double> totaleresidentnonresperbuy = new ArrayList<>();
            ArrayList<Double> totaleresidentnonrespersell = new ArrayList<>();
            ArrayList<Double> totaledeitotalibuy = new ArrayList<>();
            ArrayList<Double> totaledeitotalisell = new ArrayList<>();

            int cnt = 0;

            for (int n = 0; n < columnWidths4.length; n++) {

                if (n == 4 || n == 8) {
                    phraset = new Phrase();
                    phraset.add(new Chunk("", f5_bold));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setVerticalAlignment(ALIGN_BOTTOM);
                    cellt.setBorder(BOX);
                    table3.addCell(cellt);

                } else if (n == 0) {

                    phraset = new Phrase();
                    phraset.add(new Chunk("buy", f5_bold));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setVerticalAlignment(ALIGN_BOTTOM);
                    cellt.setBorder(BOX);
                    table3.addCell(cellt);

                } else if (n == 12) {
                    phraset = new Phrase();
                    phraset.add(new Chunk("yes", f5_bold));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setVerticalAlignment(ALIGN_BOTTOM);
                    cellt.setBorder(BOX);
                    table3.addCell(cellt);

                } else if (n == 15 || n == 18) {
                    phraset = new Phrase();
                    phraset.add(new Chunk("buy", f5_bold));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setVerticalAlignment(ALIGN_BOTTOM);
                    cellt.setBorder(BOX);
                    table3.addCell(cellt);

                } //else (cntvalue % 2 == 1) {                    
                else if (n == 11) {

                    double s2 = fd(datifooter.get(cntvalue - 2));
                    double s1 = fd(datifooter.get(cntvalue - 1));
                    double tot = s1 + s2;

                    totaledeitotalibuy.add(tot);
                    //    totaleresidentnonresperbuy.add(s2);
                    //  totaleresidentnonresperbuy.add(s1);

                    //devo metttere il totale
                    phraset = new Phrase();
                    phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(tot, 0)), f6_bold));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setVerticalAlignment(ALIGN_BOTTOM);
                    cellt.setBorder(NO_BORDER);
                    table3.addCell(cellt);
                } else if (n == 1) {

                    phraset = new Phrase();
                    phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(totaleresidentbuy, 2)), f6_bold));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setVerticalAlignment(ALIGN_BOTTOM);
                    cellt.setBorder(NO_BORDER);
                    if (n == columnWidths4.length - 1) {
                        cellt.setBorder(RIGHT);
                    }
                    table3.addCell(cellt);

                } else if (n == 2) {

                    phraset = new Phrase();
                    phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(totalenonresidentbuy, 2)), f6_bold));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setVerticalAlignment(ALIGN_BOTTOM);
                    cellt.setBorder(NO_BORDER);
                    if (n == columnWidths4.length - 1) {
                        cellt.setBorder(RIGHT);
                    }
                    table3.addCell(cellt);

                } else if (n == 3) {

                    double totalebuytrans = totaleresidentbuy + totalenonresidentbuy;
                    phraset = new Phrase();
                    phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(totalebuytrans, 2)), f6_bold));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setVerticalAlignment(ALIGN_BOTTOM);
                    cellt.setBorder(NO_BORDER);
                    if (n == columnWidths4.length - 1) {
                        cellt.setBorder(Border.DASHED);
                    }
                    table3.addCell(cellt);

                } else if (n == 5) {

                    phraset = new Phrase();
                    phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(totaleresidentcommfeebuy, 2)), f6_bold));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setVerticalAlignment(ALIGN_BOTTOM);
                    cellt.setBorder(NO_BORDER);
                    if (n == columnWidths4.length - 1) {
                        cellt.setBorder(RIGHT);
                    }
                    table3.addCell(cellt);

                } else if (n == 6) {

                    phraset = new Phrase();
                    phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(totalenonresidentcommfeebuy, 2)), f6_bold));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setVerticalAlignment(ALIGN_BOTTOM);
                    cellt.setBorder(NO_BORDER);
                    if (n == columnWidths4.length - 1) {
                        cellt.setBorder(RIGHT);
                    }
                    table3.addCell(cellt);

                } else if (n == 7) {
                    double totalebuycommfee = totaleresidentcommfeebuy + totalenonresidentcommfeebuy;
                    phraset = new Phrase();
                    phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(totalebuycommfee, 2)), f6_bold));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setVerticalAlignment(ALIGN_BOTTOM);
                    cellt.setBorder(NO_BORDER);
                    if (n == columnWidths4.length - 1) {
                        cellt.setBorder(RIGHT);
                    }
                    table3.addCell(cellt);

                } else {

                    totaleresidentnonresperbuy.add(fd(datifooter.get(cntvalue)));
                    phraset = new Phrase();
                    phraset.add(new Chunk(formatMysqltoDisplay(datifooter.get(cntvalue)), f6_bold));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setVerticalAlignment(ALIGN_BOTTOM);
                    cellt.setBorder(NO_BORDER);
                    if (n == columnWidths4.length - 1) {
                        cellt.setBorder(RIGHT);
                    }
                    table3.addCell(cellt);
                    cntvalue++;
                }

            }

            document.add(table3);

            table3 = new PdfPTable(21);
            table3.setWidths(columnWidths4);
            table3.setWidthPercentage(100);

            for (int n = 0; n < columnWidths4.length; n++) {

                if (n == 4 || n == 8) {
                    phraset = new Phrase();
                    phraset.add(new Chunk("", f5_bold));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setVerticalAlignment(ALIGN_BOTTOM);
                    cellt.setBorder(BOX);
                    table3.addCell(cellt);
                } else if (n == 0) {

                    phraset = new Phrase();
                    phraset.add(new Chunk("sell", f5_bold));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setVerticalAlignment(ALIGN_BOTTOM);
                    cellt.setBorder(BOX);
                    table3.addCell(cellt);
                } else if (n == 12) {
                    phraset = new Phrase();
                    phraset.add(new Chunk("no", f5_bold));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setVerticalAlignment(ALIGN_BOTTOM);
                    cellt.setBorder(BOX);
                    table3.addCell(cellt);
                } else if (n == 15 || n == 18) {
                    phraset = new Phrase();
                    phraset.add(new Chunk("sell", f5_bold));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setVerticalAlignment(ALIGN_BOTTOM);
                    cellt.setBorder(BOX);
                    table3.addCell(cellt);
                } else if (n == 11) {
                    double s2 = fd(datifooter.get(cntvalue - 2));
                    double s1 = fd(datifooter.get(cntvalue - 1));
                    double tot = s1 + s2;

                    totaledeitotalisell.add(tot);
                    //  totaleresidentnonrespersell.add(s2);
                    //totaleresidentnonrespersell.add(s1);

                    //devo metttere il totale
                    phraset = new Phrase();
                    phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(tot, 0)), f6_bold));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setVerticalAlignment(ALIGN_BOTTOM);
                    cellt.setBorder(NO_BORDER);
                    table3.addCell(cellt);
                } else if (n == 1) {

                    phraset = new Phrase();
                    phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(totaleresidentsell, 2)), f6_bold));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setVerticalAlignment(ALIGN_BOTTOM);
                    cellt.setBorder(NO_BORDER);
                    if (n == columnWidths4.length - 1) {
                        cellt.setBorder(RIGHT);
                    }
                    table3.addCell(cellt);
                } else if (n == 2) {

                    phraset = new Phrase();
                    phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(totalenonresidentsell, 2)), f6_bold));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setVerticalAlignment(ALIGN_BOTTOM);
                    cellt.setBorder(NO_BORDER);
                    if (n == columnWidths4.length - 1) {
                        cellt.setBorder(RIGHT);
                    }
                    table3.addCell(cellt);
                } else if (n == 3) {

                    double totaleselltrans = totaleresidentsell + totalenonresidentsell;
                    phraset = new Phrase();
                    phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(totaleselltrans, 2)), f6_bold));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setVerticalAlignment(ALIGN_BOTTOM);
                    cellt.setBorder(NO_BORDER);
                    if (n == columnWidths4.length - 1) {
                        cellt.setBorder(RIGHT);
                    }
                    table3.addCell(cellt);
                } else if (n == 5) {

                    phraset = new Phrase();
                    phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(totaleresidentcommfeesell, 2)), f6_bold));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setVerticalAlignment(ALIGN_BOTTOM);
                    cellt.setBorder(NO_BORDER);
                    if (n == columnWidths4.length - 1) {
                        cellt.setBorder(RIGHT);
                    }
                    table3.addCell(cellt);
                } else if (n == 6) {

                    phraset = new Phrase();
                    phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(totalenonresidentcommfeesell, 2)), f6_bold));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setVerticalAlignment(ALIGN_BOTTOM);
                    cellt.setBorder(NO_BORDER);
                    if (n == columnWidths4.length - 1) {
                        cellt.setBorder(RIGHT);
                    }
                    table3.addCell(cellt);
                } else if (n == 7) {
                    double totalesellcommfee = totaleresidentcommfeesell + totalenonresidentcommfeesell;
                    phraset = new Phrase();
                    phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(totalesellcommfee, 2)), f6_bold));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setVerticalAlignment(ALIGN_BOTTOM);
                    cellt.setBorder(NO_BORDER);
                    if (n == columnWidths4.length - 1) {
                        cellt.setBorder(RIGHT);
                    }
                    table3.addCell(cellt);
                } else {
                    totaleresidentnonrespersell.add(fd(datifooter.get(cntvalue)));
                    phraset = new Phrase();
                    phraset.add(new Chunk(formatMysqltoDisplay(datifooter.get(cntvalue)), f6_bold));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setVerticalAlignment(ALIGN_BOTTOM);
                    cellt.setBorder(NO_BORDER);
                    if (n == columnWidths4.length - 1) {
                        cellt.setBorder(RIGHT);
                    }
                    table3.addCell(cellt);
                    cntvalue++;
                }

            }

            document.add(table3);

            table3 = new PdfPTable(21);
            table3.setWidths(columnWidths4);
            table3.setWidthPercentage(100);

            int cnttotali = 0;
            int cntresnonres = 0;

            for (int n = 0; n < columnWidths4.length; n++) {

                if (n == 4 || n == 8) {
                    phraset = new Phrase();
                    phraset.add(new Chunk("", f5_bold));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setVerticalAlignment(ALIGN_BOTTOM);
                    cellt.setBorder(BOX);
                    table3.addCell(cellt);

                } else if (n == 0) {
                    phraset = new Phrase();
                    phraset.add(new Chunk("total", f5_bold));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setVerticalAlignment(ALIGN_BOTTOM);
                    cellt.setBorder(BOX);
                    table3.addCell(cellt);
                } else if (n == 12) {
                    phraset = new Phrase();
                    phraset.add(new Chunk("total", f5_bold));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setVerticalAlignment(ALIGN_BOTTOM);
                    cellt.setBorder(BOX);
                    table3.addCell(cellt);
                } else if (n == 15 || n == 18) {
                    phraset = new Phrase();
                    phraset.add(new Chunk("total", f5_bold));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setVerticalAlignment(ALIGN_BOTTOM);
                    cellt.setBorder(BOX);
                    table3.addCell(cellt);
                } else if (n == 11) {

                    double tot1 = (double) totaledeitotalibuy.get(cnttotali);
                    double tot2 = (double) totaledeitotalisell.get(cnttotali);

                    phraset = new Phrase();
                    phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(tot1 + tot2, 0)), f6_bold));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setVerticalAlignment(ALIGN_BOTTOM);
                    cellt.setBorder(BOX);
                    if (n == columnWidths4.length - 1) {
                        cellt.setBorder(RIGHT | BOX);
                    }
                    table3.addCell(cellt);
                    cnttotali++;
                } else if (n == 1) {

                    double totaleresidentsellbuy = totaleresidentbuy + totaleresidentsell;
                    phraset = new Phrase();
                    phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(totaleresidentsellbuy, 2)), f6_bold));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setVerticalAlignment(ALIGN_BOTTOM);
                    cellt.setBorder(BOX);
                    if (n == columnWidths4.length - 1) {
                        cellt.setBorder(RIGHT);
                    }
                    table3.addCell(cellt);

                } else if (n == 2) {

                    double totalenonresidentbuysell = totalenonresidentbuy + totalenonresidentsell;
                    phraset = new Phrase();
                    phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(totalenonresidentbuysell, 2)), f6_bold));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setVerticalAlignment(ALIGN_BOTTOM);
                    cellt.setBorder(BOX);
                    if (n == columnWidths4.length - 1) {
                        cellt.setBorder(RIGHT);
                    }
                    table3.addCell(cellt);

                } else if (n == 3) {

                    double totalebuysellgenerale = totaleresidentbuy + totalenonresidentbuy + totaleresidentsell + totalenonresidentsell;
                    phraset = new Phrase();
                    phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(totalebuysellgenerale, 2)), f6_bold));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setVerticalAlignment(ALIGN_BOTTOM);
                    cellt.setBorder(BOX);
                    if (n == columnWidths4.length - 1) {
                        cellt.setBorder(RIGHT);
                    }
                    table3.addCell(cellt);

                } else if (n == 5) {

                    double totaleresidentcommfeebuysell = totaleresidentcommfeesell + totaleresidentcommfeebuy;
                    phraset = new Phrase();
                    phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(totaleresidentcommfeebuysell, 2)), f6_bold));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setVerticalAlignment(ALIGN_BOTTOM);
                    cellt.setBorder(BOX);
                    if (n == columnWidths4.length - 1) {
                        cellt.setBorder(RIGHT);
                    }
                    table3.addCell(cellt);

                } else if (n == 6) {
                    double totalenonresidentcommfeebuysell = totalenonresidentcommfeebuy + totalenonresidentcommfeesell;
                    phraset = new Phrase();
                    phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(totalenonresidentcommfeebuysell, 2)), f6_bold));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setVerticalAlignment(ALIGN_BOTTOM);
                    cellt.setBorder(BOX);
                    if (n == columnWidths4.length - 1) {
                        cellt.setBorder(RIGHT);
                    }
                    table3.addCell(cellt);
                } else if (n == 7) {
                    double totalegeneralecommfee = totaleresidentcommfeesell + totaleresidentcommfeebuy + totalenonresidentcommfeebuy + totalenonresidentcommfeesell;
                    phraset = new Phrase();
                    phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(totalegeneralecommfee, 2)), f6_bold));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setVerticalAlignment(ALIGN_BOTTOM);
                    cellt.setBorder(BOX);
                    if (n == columnWidths4.length - 1) {
                        cellt.setBorder(RIGHT);
                    }
                    table3.addCell(cellt);
                } else {
                    double tot3 = (double) (totaleresidentnonresperbuy.get(cntresnonres));
                    double tot4 = (double) (totaleresidentnonrespersell.get(cntresnonres));

                    phraset = new Phrase();
                    if (n == 9 || n == 10 || n == 14 || n == 17 || n == 20) {
                        phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(tot3 + tot4, 0)), f6_bold));
                    } else {
                        phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(tot3 + tot4, 2)), f6_bold));
                    }

                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setVerticalAlignment(ALIGN_BOTTOM);
                    cellt.setBorder(BOX);
                    if (n == columnWidths4.length - 1) {
                        cellt.setBorder(RIGHT | BOX);
                    }
                    table3.addCell(cellt);
                    cntresnonres++;
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
     * @param ba
     * @param datereport1
     * @param datereport2
     * @return
     */
    public String receiptpdf_CACZ(String path, TillTransactionListCurrency_value siq, TillTransactionListCurrency_value ba, String datereport1, String datereport2) {
        try {

            File pdf = new File(path + generaId(50) + "receiptexcel_CACZ.pdf");
            Document document = new Document(A4.rotate(), 20, 20, 20, 20);
            try(OutputStream ou = new FileOutputStream(pdf)){
            PdfWriter wr = getInstance(document, ou);
            document.open();

            PdfPTable table = new PdfPTable(2);
            table.setWidths(columnWidths0);
            table.setWidthPercentage(100);

            Phrase phrase1 = new Phrase();
            phrase1.add(new Chunk(intestazionePdf + " From " + datereport1 + " To " + datereport2, f2_bold));
            PdfPCell cell1 = new PdfPCell(phrase1);
            cell1.setBorder(NO_BORDER);

            Phrase phrase3 = new Phrase();
            phrase3.add(new Chunk("\n " + siq.getId_filiale() + " " + siq.getDe_filiale(), f3_normal));
            PdfPCell cell3 = new PdfPCell(phrase3);
            cell3.setBorder(NO_BORDER);

            table.addCell(cell1);
            table.addCell(cellavuota);
            table.addCell(cell3);
            table.addCell(cellavuota);
            document.add(table);
            vuoto.setFont(f3_normal);
            document.add(cellavuota);

            document.add(vuoto);

            scriviIntestazioneColonne_CACZ(document);

            PdfPTable table3, table6;
            ArrayList<TillTransactionListCurrency_value> dati = siq.getDati();
            Phrase phraset;
            PdfPCell cellt;

            table3 = new PdfPTable(15);
            table3.setWidths(columnWidths2A);
            table3.setWidthPercentage(100);

            double amount = 0, totale = 0, commfee = 0, payout = 0;

            double totalepayinoutgeneral = 0;

            double totround = 0;

            for (int i = 0; (i < dati.size() - 1) || (dati.size() == 1 && i == 0); i++) {

                TillTransactionListCurrency_value actual = dati.get(i);

                TillTransactionListCurrency_value prossimo = null;

                if (dati.size() > 1) {
                    prossimo = dati.get(i + 1);
                }

                if (!actual.getType().contains("D")) {

                    amount += fd(actual.getAmountSenzaFormattazione());
                    totale += fd(actual.getTotalSenzaFormattazione());
                    commfee += fd(actual.getComfreeSenzaFormattazione());
                    payout += fd(actual.getPayinpayoutSenzaFormattazione());
                }

                phraset = new Phrase();
                phraset.add(new Chunk(actual.getType(), f5_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_LEFT);
                cellt.setVerticalAlignment(ALIGN_BOTTOM);
                cellt.setBorder(NO_BORDER);
                table3.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(actual.getTill(), f5_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setVerticalAlignment(ALIGN_BOTTOM);
                cellt.setBorder(NO_BORDER);
                table3.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(actual.getUser(), f5_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setVerticalAlignment(ALIGN_BOTTOM);
                cellt.setBorder(NO_BORDER);
                table3.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(actual.getNotr(), f5_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setVerticalAlignment(ALIGN_BOTTOM);
                cellt.setBorder(NO_BORDER);
                table3.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(formatStringtoStringDate(actual.getTime(), patternsqldate, patternnormdate), f5_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_LEFT);
                cellt.setVerticalAlignment(ALIGN_BOTTOM);
                cellt.setBorder(NO_BORDER);
                table3.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(actual.getCur(), f5_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_LEFT);
                cellt.setVerticalAlignment(ALIGN_BOTTOM);
                cellt.setBorder(NO_BORDER);
                table3.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(actual.getKind(), f5_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_LEFT);
                cellt.setVerticalAlignment(ALIGN_BOTTOM);
                cellt.setBorder(NO_BORDER);
                table3.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(actual.getAmount()), f5_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setVerticalAlignment(ALIGN_BOTTOM);
                cellt.setBorder(NO_BORDER);
                table3.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(actual.getRate()), f5_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setVerticalAlignment(ALIGN_BOTTOM);
                cellt.setBorder(NO_BORDER);
                table3.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(actual.getTotal()), f5_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setVerticalAlignment(ALIGN_BOTTOM);
                cellt.setBorder(NO_BORDER);
                table3.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(actual.getPerc()), f5_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setVerticalAlignment(ALIGN_BOTTOM);
                cellt.setBorder(NO_BORDER);
                table3.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(actual.getComfree()), f5_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setVerticalAlignment(ALIGN_BOTTOM);
                cellt.setBorder(NO_BORDER);
                table3.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(actual.getRound()), f5_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setVerticalAlignment(ALIGN_BOTTOM);
                cellt.setBorder(NO_BORDER);
                table3.addCell(cellt);

                if (!actual.getType().contains("D")) {
                    totround = totround + fd(actual.getRound());
                }

                if (actual.getKind().contains("Sell")) {
                } else {

                    phraset = new Phrase();
                    phraset.add(new Chunk("-" + formatMysqltoDisplay(actual.getPayinpayout()), f5_normal));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setVerticalAlignment(ALIGN_BOTTOM);
                    cellt.setBorder(NO_BORDER);
                    table3.addCell(cellt);
                    if (!actual.getType().contains("D")) {
                        totalepayinoutgeneral -= fd(actual.getPayinpayoutSenzaFormattazione());
                    }
                }

                phraset = new Phrase();
                phraset.add(new Chunk(actual.getPos(), f5_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setVerticalAlignment(ALIGN_BOTTOM);
                cellt.setBorder(NO_BORDER);
                table3.addCell(cellt);

                if (prossimo != null && !prossimo.getCur().equals(actual.getCur())) {

                    for (int h = 0; h < columnWidths2A.length; h++) {

                        if (h == 5) {
                            phraset = new Phrase();
                            phraset.add(new Chunk(formatMysqltoDisplay(actual.getCur()), f5_bold));
                            cellt = new PdfPCell(phraset);
                            cellt.setHorizontalAlignment(ALIGN_LEFT);
                            cellt.setVerticalAlignment(ALIGN_BOTTOM);
                            cellt.setBorder(BOTTOM);
                            table3.addCell(cellt);
                        } else if (h == 7) {

                            phraset = new Phrase();
                            phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(amount, 2)), f5_bold));
                            cellt = new PdfPCell(phraset);
                            cellt.setHorizontalAlignment(ALIGN_RIGHT);
                            cellt.setVerticalAlignment(ALIGN_BOTTOM);
                            cellt.setBorder(BOTTOM);
                            table3.addCell(cellt);

                        } else if (h == 9) {

                            phraset = new Phrase();
                            phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(totale, 2)), f5_bold));
                            cellt = new PdfPCell(phraset);
                            cellt.setHorizontalAlignment(ALIGN_RIGHT);
                            cellt.setVerticalAlignment(ALIGN_BOTTOM);
                            cellt.setBorder(BOTTOM);
                            table3.addCell(cellt);
                        } else if (h == 11) {
                            phraset = new Phrase();
                            phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(commfee, 2)), f5_bold));
                            cellt = new PdfPCell(phraset);
                            cellt.setHorizontalAlignment(ALIGN_RIGHT);
                            cellt.setVerticalAlignment(ALIGN_BOTTOM);
                            cellt.setBorder(BOTTOM);
                            table3.addCell(cellt);
                        } else if (h == 12) {
                            phraset = new Phrase();
                            phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(totround, 2)), f5_bold));
                            cellt = new PdfPCell(phraset);
                            cellt.setHorizontalAlignment(ALIGN_RIGHT);
                            cellt.setVerticalAlignment(ALIGN_BOTTOM);
                            cellt.setBorder(BOTTOM);
                            table3.addCell(cellt);
                        } else if (h == 13) {
                            phraset = new Phrase();
                            phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(totalepayinoutgeneral, 2)), f5_bold));
                            cellt = new PdfPCell(phraset);
                            cellt.setHorizontalAlignment(ALIGN_RIGHT);
                            cellt.setVerticalAlignment(ALIGN_BOTTOM);
                            cellt.setBorder(BOTTOM);
                            table3.addCell(cellt);
                        } else {
                            phraset = new Phrase();
                            phraset.add(new Chunk("", f5_bold));
                            cellt = new PdfPCell(phraset);
                            cellt.setHorizontalAlignment(ALIGN_RIGHT);
                            cellt.setVerticalAlignment(ALIGN_BOTTOM);
                            cellt.setBorder(BOTTOM);
                            table3.addCell(cellt);
                        }

                    }
                    amount = 0;
                    totale = 0;
                    commfee = 0;
                    payout = 0;
                    totalepayinoutgeneral = 0;
                    totround = 0;

                }

                if ((dati.size() == 1 && i == 0) || (i == dati.size() - 2)) {

                    //  totalepayinoutgeneral = 0;
                    if (prossimo != null && !prossimo.getType().contains("D")) {

                        amount += fd(prossimo.getAmountSenzaFormattazione());
                        totale += fd(prossimo.getTotalSenzaFormattazione());
                        commfee += fd(prossimo.getComfreeSenzaFormattazione());
                        payout += fd(prossimo.getPayinpayoutSenzaFormattazione());

                        phraset = new Phrase();
                        phraset.add(new Chunk(prossimo.getType(), f5_normal));
                        cellt = new PdfPCell(phraset);
                        cellt.setHorizontalAlignment(ALIGN_LEFT);
                        cellt.setVerticalAlignment(ALIGN_BOTTOM);
                        cellt.setBorder(NO_BORDER);
                        table3.addCell(cellt);

                        phraset = new Phrase();
                        phraset.add(new Chunk(prossimo.getTill(), f5_normal));
                        cellt = new PdfPCell(phraset);
                        cellt.setHorizontalAlignment(ALIGN_RIGHT);
                        cellt.setVerticalAlignment(ALIGN_BOTTOM);
                        cellt.setBorder(NO_BORDER);
                        table3.addCell(cellt);

                        phraset = new Phrase();
                        phraset.add(new Chunk(prossimo.getUser(), f5_normal));
                        cellt = new PdfPCell(phraset);
                        cellt.setHorizontalAlignment(ALIGN_RIGHT);
                        cellt.setVerticalAlignment(ALIGN_BOTTOM);
                        cellt.setBorder(NO_BORDER);
                        table3.addCell(cellt);

                        phraset = new Phrase();
                        phraset.add(new Chunk(prossimo.getNotr(), f5_normal));
                        cellt = new PdfPCell(phraset);
                        cellt.setHorizontalAlignment(ALIGN_RIGHT);
                        cellt.setVerticalAlignment(ALIGN_BOTTOM);
                        cellt.setBorder(NO_BORDER);
                        table3.addCell(cellt);

                        phraset = new Phrase();
                        phraset.add(new Chunk(formatStringtoStringDate(prossimo.getTime(), patternsqldate, patternnormdate), f5_normal));
                        cellt = new PdfPCell(phraset);
                        cellt.setHorizontalAlignment(ALIGN_LEFT);
                        cellt.setVerticalAlignment(ALIGN_BOTTOM);
                        cellt.setBorder(NO_BORDER);
                        table3.addCell(cellt);

                        phraset = new Phrase();
                        phraset.add(new Chunk(prossimo.getCur(), f5_normal));
                        cellt = new PdfPCell(phraset);
                        cellt.setHorizontalAlignment(ALIGN_LEFT);
                        cellt.setVerticalAlignment(ALIGN_BOTTOM);
                        cellt.setBorder(NO_BORDER);
                        table3.addCell(cellt);

                        phraset = new Phrase();
                        phraset.add(new Chunk(prossimo.getKind(), f5_normal));
                        cellt = new PdfPCell(phraset);
                        cellt.setHorizontalAlignment(ALIGN_LEFT);
                        cellt.setVerticalAlignment(ALIGN_BOTTOM);
                        cellt.setBorder(NO_BORDER);
                        table3.addCell(cellt);

                        phraset = new Phrase();
                        phraset.add(new Chunk(formatMysqltoDisplay(prossimo.getAmount()), f5_normal));
                        cellt = new PdfPCell(phraset);
                        cellt.setHorizontalAlignment(ALIGN_RIGHT);
                        cellt.setVerticalAlignment(ALIGN_BOTTOM);
                        cellt.setBorder(NO_BORDER);
                        table3.addCell(cellt);

                        phraset = new Phrase();
                        phraset.add(new Chunk(formatMysqltoDisplay(prossimo.getRate()), f5_normal));
                        cellt = new PdfPCell(phraset);
                        cellt.setHorizontalAlignment(ALIGN_RIGHT);
                        cellt.setVerticalAlignment(ALIGN_BOTTOM);
                        cellt.setBorder(NO_BORDER);
                        table3.addCell(cellt);

                        phraset = new Phrase();
                        phraset.add(new Chunk(formatMysqltoDisplay(prossimo.getTotal()), f5_normal));
                        cellt = new PdfPCell(phraset);
                        cellt.setHorizontalAlignment(ALIGN_RIGHT);
                        cellt.setVerticalAlignment(ALIGN_BOTTOM);
                        cellt.setBorder(NO_BORDER);
                        table3.addCell(cellt);

                        phraset = new Phrase();
                        phraset.add(new Chunk(formatMysqltoDisplay(prossimo.getPerc()), f5_normal));
                        cellt = new PdfPCell(phraset);
                        cellt.setHorizontalAlignment(ALIGN_RIGHT);
                        cellt.setVerticalAlignment(ALIGN_BOTTOM);
                        cellt.setBorder(NO_BORDER);
                        table3.addCell(cellt);

                        phraset = new Phrase();
                        phraset.add(new Chunk(formatMysqltoDisplay(prossimo.getComfree()), f5_normal));
                        cellt = new PdfPCell(phraset);
                        cellt.setHorizontalAlignment(ALIGN_RIGHT);
                        cellt.setVerticalAlignment(ALIGN_BOTTOM);
                        cellt.setBorder(NO_BORDER);
                        table3.addCell(cellt);

                        phraset = new Phrase();
                        phraset.add(new Chunk(formatMysqltoDisplay(prossimo.getRound()), f5_normal));
                        cellt = new PdfPCell(phraset);
                        cellt.setHorizontalAlignment(ALIGN_RIGHT);
                        cellt.setVerticalAlignment(ALIGN_BOTTOM);
                        cellt.setBorder(NO_BORDER);
                        table3.addCell(cellt);

                        totround = totround + fd(prossimo.getRound());

                        phraset = new Phrase();
                        phraset.add(new Chunk("- " + formatMysqltoDisplay(prossimo.getPayinpayout()), f5_normal));
                        cellt = new PdfPCell(phraset);
                        cellt.setHorizontalAlignment(ALIGN_RIGHT);
                        cellt.setVerticalAlignment(ALIGN_BOTTOM);
                        cellt.setBorder(NO_BORDER);
                        table3.addCell(cellt);

                        if (!prossimo.getType().contains("D")) {
                            totalepayinoutgeneral -= fd(prossimo.getPayinpayoutSenzaFormattazione());
                        }

                        phraset = new Phrase();
                        phraset.add(new Chunk(prossimo.getPos(), f5_normal));
                        cellt = new PdfPCell(phraset);
                        cellt.setHorizontalAlignment(ALIGN_RIGHT);
                        cellt.setVerticalAlignment(ALIGN_BOTTOM);
                        cellt.setBorder(NO_BORDER);
                        table3.addCell(cellt);

                    }

                    for (int h = 0; h < columnWidths2A.length; h++) {

                        if (h == 5) {

                            phraset = new Phrase();
                            if (prossimo != null && !prossimo.getType().contains("D")) {
                                phraset.add(new Chunk(prossimo.getCur(), f5_bold));
                            } else {
                                phraset.add(new Chunk(actual.getCur(), f5_bold));
                            }
                            cellt = new PdfPCell(phraset);
                            cellt.setHorizontalAlignment(ALIGN_LEFT);
                            cellt.setVerticalAlignment(ALIGN_BOTTOM);
                            cellt.setBorder(BOTTOM);
                            table3.addCell(cellt);
                        } else if (h == 7) {

                            phraset = new Phrase();
                            phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(amount, 2)), f5_bold));
                            cellt = new PdfPCell(phraset);
                            cellt.setHorizontalAlignment(ALIGN_RIGHT);
                            cellt.setVerticalAlignment(ALIGN_BOTTOM);
                            cellt.setBorder(BOTTOM);
                            table3.addCell(cellt);

                        } else if (h == 9) {

                            phraset = new Phrase();
                            phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(totale, 2)), f5_bold));
                            cellt = new PdfPCell(phraset);
                            cellt.setHorizontalAlignment(ALIGN_RIGHT);
                            cellt.setVerticalAlignment(ALIGN_BOTTOM);
                            cellt.setBorder(BOTTOM);
                            table3.addCell(cellt);

                        } else if (h == 11) {
                            phraset = new Phrase();
                            phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(commfee, 2)), f5_bold));
                            cellt = new PdfPCell(phraset);
                            cellt.setHorizontalAlignment(ALIGN_RIGHT);
                            cellt.setVerticalAlignment(ALIGN_BOTTOM);
                            cellt.setBorder(BOTTOM);
                            table3.addCell(cellt);
                        } else if (h == 12) {

                            phraset = new Phrase();
                            phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(totround, 2)), f5_bold));
                            cellt = new PdfPCell(phraset);
                            cellt.setHorizontalAlignment(ALIGN_RIGHT);
                            cellt.setVerticalAlignment(ALIGN_BOTTOM);
                            cellt.setBorder(BOTTOM);
                            table3.addCell(cellt);
                        } else if (h == 13) {

                            phraset = new Phrase();
                            phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(totalepayinoutgeneral, 2)), f5_bold));
                            cellt = new PdfPCell(phraset);
                            cellt.setHorizontalAlignment(ALIGN_RIGHT);
                            cellt.setVerticalAlignment(ALIGN_BOTTOM);
                            cellt.setBorder(BOTTOM);
                            table3.addCell(cellt);

                        } else {
                            phraset = new Phrase();
                            phraset.add(new Chunk("", f5_bold));
                            cellt = new PdfPCell(phraset);
                            cellt.setHorizontalAlignment(ALIGN_RIGHT);
                            cellt.setVerticalAlignment(ALIGN_BOTTOM);
                            cellt.setBorder(BOTTOM);
                            table3.addCell(cellt);
                        }

                    }

                }

            }
            document.add(table3);

            if (ba != null) {
                document.add(vuoto);

                scriviIntestazioneColonne_CACZ_2(document);

                table6 = new PdfPTable(13);
                table6.setWidths(columnWidths2B);
                table6.setWidthPercentage(100);

                amount = 0;
                totale = 0;
                commfee = 0;
                payout = 0;
                dati = ba.getDati();

                for (int i = 0; (i < dati.size() - 1) || (dati.size() == 1 && i == 0); i++) {

                    TillTransactionListCurrency_value actual = (TillTransactionListCurrency_value) dati.get(i);

                    TillTransactionListCurrency_value prossimo = null;

                    if (dati.size() > 1) {
                        prossimo = (TillTransactionListCurrency_value) dati.get(i + 1);
                    }

                    if (!actual.getType().contains("D")) {

                        amount += fd(actual.getAmountSenzaFormattazione());
                        totale += fd(actual.getTotalSenzaFormattazione());
                        commfee += fd(actual.getComfreeSenzaFormattazione());
                        payout += fd(actual.getPayinpayoutSenzaFormattazione());
                    }

                    phraset = new Phrase();
                    phraset.add(new Chunk(actual.getType(), f5_normal));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_LEFT);
                    cellt.setVerticalAlignment(ALIGN_BOTTOM);
                    cellt.setBorder(NO_BORDER);
                    table6.addCell(cellt);

                    phraset = new Phrase();
                    phraset.add(new Chunk(actual.getTill(), f5_normal));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setVerticalAlignment(ALIGN_BOTTOM);
                    cellt.setBorder(NO_BORDER);
                    table6.addCell(cellt);

                    phraset = new Phrase();
                    phraset.add(new Chunk(actual.getUser(), f5_normal));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setVerticalAlignment(ALIGN_BOTTOM);
                    cellt.setBorder(NO_BORDER);
                    table6.addCell(cellt);

                    phraset = new Phrase();
                    phraset.add(new Chunk(actual.getNotr(), f5_normal));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setVerticalAlignment(ALIGN_BOTTOM);
                    cellt.setBorder(NO_BORDER);
                    table6.addCell(cellt);

                    phraset = new Phrase();
                    phraset.add(new Chunk(formatStringtoStringDate(actual.getTime(), patternsqldate, patternnormdate), f5_normal));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_LEFT);
                    cellt.setVerticalAlignment(ALIGN_BOTTOM);
                    cellt.setBorder(NO_BORDER);
                    table6.addCell(cellt);

                    phraset = new Phrase();
                    phraset.add(new Chunk(actual.getCur(), f5_normal));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_LEFT);
                    cellt.setVerticalAlignment(ALIGN_BOTTOM);
                    cellt.setBorder(NO_BORDER);
                    table6.addCell(cellt);

                    phraset = new Phrase();
                    phraset.add(new Chunk(actual.getKind(), f5_normal));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_LEFT);
                    cellt.setVerticalAlignment(ALIGN_BOTTOM);
                    cellt.setBorder(NO_BORDER);
                    table6.addCell(cellt);

                    phraset = new Phrase();
                    phraset.add(new Chunk(formatMysqltoDisplay(actual.getAmount()), f5_normal));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setVerticalAlignment(ALIGN_BOTTOM);
                    cellt.setBorder(NO_BORDER);
                    table6.addCell(cellt);

                    phraset = new Phrase();
                    phraset.add(new Chunk(formatMysqltoDisplay(actual.getRate()), f5_normal));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setVerticalAlignment(ALIGN_BOTTOM);
                    cellt.setBorder(NO_BORDER);
                    table6.addCell(cellt);

                    phraset = new Phrase();
                    phraset.add(new Chunk(formatMysqltoDisplay(actual.getTotal()), f5_normal));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setVerticalAlignment(ALIGN_BOTTOM);
                    cellt.setBorder(NO_BORDER);
                    table6.addCell(cellt);

                    phraset = new Phrase();
                    phraset.add(new Chunk(formatMysqltoDisplay(actual.getPayinpayout()), f5_normal));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setVerticalAlignment(ALIGN_BOTTOM);
                    cellt.setBorder(NO_BORDER);
                    table6.addCell(cellt);

                    phraset = new Phrase();
                    phraset.add(new Chunk(formatMysqltoDisplay(actual.getSpread()), f5_normal));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setVerticalAlignment(ALIGN_BOTTOM);
                    cellt.setBorder(NO_BORDER);
                    table6.addCell(cellt);

                    phraset = new Phrase();
                    phraset.add(new Chunk(actual.getPos(), f5_normal));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setVerticalAlignment(ALIGN_BOTTOM);
                    cellt.setBorder(NO_BORDER);
                    table6.addCell(cellt);

                    if (prossimo != null && !prossimo.getCur().equals(actual.getCur())) {

                        for (int h = 0; h < columnWidths2B.length; h++) {

                            if (h == 5) {
                                phraset = new Phrase();
                                phraset.add(new Chunk(actual.getCur(), f5_bold));
                                cellt = new PdfPCell(phraset);
                                cellt.setHorizontalAlignment(ALIGN_LEFT);
                                cellt.setVerticalAlignment(ALIGN_BOTTOM);
                                cellt.setBorder(BOTTOM);
                                table6.addCell(cellt);
                            } else if (h == 7) {
                                phraset = new Phrase();
                                phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(amount, 2)), f5_bold));
                                cellt = new PdfPCell(phraset);
                                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                                cellt.setVerticalAlignment(ALIGN_BOTTOM);
                                cellt.setBorder(BOTTOM);
                                table6.addCell(cellt);
                            } else if (h == 9) {
                                phraset = new Phrase();
                                phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(totale, 2)), f5_bold));
                                cellt = new PdfPCell(phraset);
                                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                                cellt.setVerticalAlignment(ALIGN_BOTTOM);
                                cellt.setBorder(BOTTOM);
                                table6.addCell(cellt);
                            } else {
                                phraset = new Phrase();
                                phraset.add(new Chunk("", f5_bold));
                                cellt = new PdfPCell(phraset);
                                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                                cellt.setVerticalAlignment(ALIGN_BOTTOM);
                                cellt.setBorder(BOTTOM);
                                table6.addCell(cellt);
                            }
                        }
                        amount = 0;
                        totale = 0;
                        commfee = 0;
                        payout = 0;
                    }

                    if ((dati.size() == 1 && i == 0) || (i == dati.size() - 2)) {

                        //  totalepayinoutgeneral = 0;
                        if (prossimo != null && !prossimo.getType().contains("D")) {

                            amount += fd(prossimo.getAmountSenzaFormattazione());
                            totale += fd(prossimo.getTotalSenzaFormattazione());

                            phraset = new Phrase();
                            phraset.add(new Chunk(prossimo.getType(), f5_normal));
                            cellt = new PdfPCell(phraset);
                            cellt.setHorizontalAlignment(ALIGN_LEFT);
                            cellt.setVerticalAlignment(ALIGN_BOTTOM);
                            cellt.setBorder(NO_BORDER);
                            table6.addCell(cellt);

                            phraset = new Phrase();
                            phraset.add(new Chunk(prossimo.getTill(), f5_normal));
                            cellt = new PdfPCell(phraset);
                            cellt.setHorizontalAlignment(ALIGN_RIGHT);
                            cellt.setVerticalAlignment(ALIGN_BOTTOM);
                            cellt.setBorder(NO_BORDER);
                            table6.addCell(cellt);

                            phraset = new Phrase();
                            phraset.add(new Chunk(prossimo.getUser(), f5_normal));
                            cellt = new PdfPCell(phraset);
                            cellt.setHorizontalAlignment(ALIGN_RIGHT);
                            cellt.setVerticalAlignment(ALIGN_BOTTOM);
                            cellt.setBorder(NO_BORDER);
                            table6.addCell(cellt);

                            phraset = new Phrase();
                            phraset.add(new Chunk(prossimo.getNotr(), f5_normal));
                            cellt = new PdfPCell(phraset);
                            cellt.setHorizontalAlignment(ALIGN_RIGHT);
                            cellt.setVerticalAlignment(ALIGN_BOTTOM);
                            cellt.setBorder(NO_BORDER);
                            table6.addCell(cellt);

                            phraset = new Phrase();
                            phraset.add(new Chunk(formatStringtoStringDate(prossimo.getTime(), patternsqldate, patternnormdate), f5_normal));
                            cellt = new PdfPCell(phraset);
                            cellt.setHorizontalAlignment(ALIGN_LEFT);
                            cellt.setVerticalAlignment(ALIGN_BOTTOM);
                            cellt.setBorder(NO_BORDER);
                            table6.addCell(cellt);

                            phraset = new Phrase();
                            phraset.add(new Chunk(prossimo.getCur(), f5_normal));
                            cellt = new PdfPCell(phraset);
                            cellt.setHorizontalAlignment(ALIGN_LEFT);
                            cellt.setVerticalAlignment(ALIGN_BOTTOM);
                            cellt.setBorder(NO_BORDER);
                            table6.addCell(cellt);

                            phraset = new Phrase();
                            phraset.add(new Chunk(prossimo.getKind(), f5_normal));
                            cellt = new PdfPCell(phraset);
                            cellt.setHorizontalAlignment(ALIGN_LEFT);
                            cellt.setVerticalAlignment(ALIGN_BOTTOM);
                            cellt.setBorder(NO_BORDER);
                            table6.addCell(cellt);

                            phraset = new Phrase();
                            phraset.add(new Chunk(formatMysqltoDisplay(prossimo.getAmount()), f5_normal));
                            cellt = new PdfPCell(phraset);
                            cellt.setHorizontalAlignment(ALIGN_RIGHT);
                            cellt.setVerticalAlignment(ALIGN_BOTTOM);
                            cellt.setBorder(NO_BORDER);
                            table6.addCell(cellt);

                            phraset = new Phrase();
                            phraset.add(new Chunk(formatMysqltoDisplay(prossimo.getRate()), f5_normal));
                            cellt = new PdfPCell(phraset);
                            cellt.setHorizontalAlignment(ALIGN_RIGHT);
                            cellt.setVerticalAlignment(ALIGN_BOTTOM);
                            cellt.setBorder(NO_BORDER);
                            table6.addCell(cellt);

                            phraset = new Phrase();
                            phraset.add(new Chunk(formatMysqltoDisplay(prossimo.getTotal()), f5_normal));
                            cellt = new PdfPCell(phraset);
                            cellt.setHorizontalAlignment(ALIGN_RIGHT);
                            cellt.setVerticalAlignment(ALIGN_BOTTOM);
                            cellt.setBorder(NO_BORDER);
                            table6.addCell(cellt);

                            phraset = new Phrase();
                            phraset.add(new Chunk(formatMysqltoDisplay(prossimo.getPayinpayout()), f5_normal));
                            cellt = new PdfPCell(phraset);
                            cellt.setHorizontalAlignment(ALIGN_RIGHT);
                            cellt.setVerticalAlignment(ALIGN_BOTTOM);
                            cellt.setBorder(NO_BORDER);
                            table6.addCell(cellt);

                            phraset = new Phrase();
                            phraset.add(new Chunk(formatMysqltoDisplay(prossimo.getSpread()), f5_normal));
                            cellt = new PdfPCell(phraset);
                            cellt.setHorizontalAlignment(ALIGN_RIGHT);
                            cellt.setVerticalAlignment(ALIGN_BOTTOM);
                            cellt.setBorder(NO_BORDER);
                            table6.addCell(cellt);

                            phraset = new Phrase();
                            phraset.add(new Chunk(prossimo.getPos(), f5_normal));
                            cellt = new PdfPCell(phraset);
                            cellt.setHorizontalAlignment(ALIGN_RIGHT);
                            cellt.setVerticalAlignment(ALIGN_BOTTOM);
                            cellt.setBorder(NO_BORDER);
                            table6.addCell(cellt);

                        }

                        for (int h = 0; h < columnWidths2B.length; h++) {

                            if (h == 5) {
                                phraset = new Phrase();

                                if (prossimo != null && !prossimo.getType().contains("D")) {
                                    phraset.add(new Chunk(prossimo.getCur(), f5_bold));
                                } else {
                                    phraset.add(new Chunk(actual.getCur(), f5_bold));
                                }

                                cellt = new PdfPCell(phraset);
                                cellt.setHorizontalAlignment(ALIGN_LEFT);
                                cellt.setVerticalAlignment(ALIGN_BOTTOM);
                                cellt.setBorder(BOTTOM);
                                table6.addCell(cellt);
                            } else if (h == 7) {

                                phraset = new Phrase();
                                phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(amount, 2)), f5_bold));
                                cellt = new PdfPCell(phraset);
                                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                                cellt.setVerticalAlignment(ALIGN_BOTTOM);
                                cellt.setBorder(BOTTOM);
                                table6.addCell(cellt);
                            } else if (h == 9) {
                                phraset = new Phrase();
                                phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(totale, 2)), f5_bold));
                                cellt = new PdfPCell(phraset);
                                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                                cellt.setVerticalAlignment(ALIGN_BOTTOM);
                                cellt.setBorder(BOTTOM);
                                table6.addCell(cellt);
                            } else {
                                phraset = new Phrase();
                                phraset.add(new Chunk("", f5_bold));
                                cellt = new PdfPCell(phraset);
                                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                                cellt.setVerticalAlignment(ALIGN_BOTTOM);
                                cellt.setBorder(BOTTOM);
                                table6.addCell(cellt);
                            }
                        }

                    }

                }
                document.add(table6);
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
     * @param ba
     * @param datereport1
     * @param datereport2
     * @return
     */
    public String receiptexcel_CACZ(String path, TillTransactionListCurrency_value siq, TillTransactionListCurrency_value ba, String datereport1, String datereport2) {

        try {
            File pdf = new File(path + generaId(50) + "_receiptexcel_CACZ.xls");
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet("TillTransactionlistCurrency");

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
            style3left.setAlignment(LEFT);
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
            style4left.setAlignment(LEFT);
            style4left.setBorderTop(THIN);
            style4left.setBorderBottom(THIN);

            Row rowP = sheet.createRow((short) 1);

            Cell cl = rowP.createCell(1);
            cl.setCellStyle(style1);
            cl.setCellValue((intestazionePdf + " From " + datereport1 + " To " + datereport2).toUpperCase());
            sheet.addMergedRegion(new CellRangeAddress(1, 1, 1, 7));

            Row row = sheet.createRow((short) 2);
            row.createCell(1).setCellValue(siq.getId_filiale() + " - " + siq.getDe_filiale().toUpperCase());
            sheet.addMergedRegion(new CellRangeAddress(1, 1, 1, 7));

            Row row5 = sheet.createRow((short) 4);
            Cell clrow5 = row5.createCell(1);
            clrow5.setCellStyle(style1);
            clrow5.setCellValue("TRANSACTION CHANGE");
            sheet.addMergedRegion(new CellRangeAddress(4, 4, 1, 7));

            Row row6 = sheet.createRow((short) 5);

            Cell f2 = row6.createCell(1);
            f2.setCellStyle(style3left);
            f2.setCellValue("Type");

            Cell f3 = row6.createCell(2);
            f3.setCellStyle(style3);
            f3.setCellValue("Till");

            Cell f4 = row6.createCell(3);
            f4.setCellStyle(style3);
            f4.setCellValue("User");

            Cell f5 = row6.createCell(4);
            f5.setCellStyle(style3);
            f5.setCellValue("No.Tr.");

            Cell f6 = row6.createCell(5);
            f6.setCellStyle(style3left);
            f6.setCellValue("Date / Time");

            Cell f7 = row6.createCell(6);
            f7.setCellStyle(style3left);
            f7.setCellValue("Cur");

            Cell f8 = row6.createCell(7);
            f8.setCellStyle(style3left);
            f8.setCellValue("Kind");

            Cell f9 = row6.createCell(8);
            f9.setCellStyle(style3);
            f9.setCellValue("Amount");

            Cell f10 = row6.createCell(9);
            f10.setCellStyle(style3);
            f10.setCellValue("Rate");

            Cell f11 = row6.createCell(10);
            f11.setCellStyle(style3);
            f11.setCellValue("Total");

            Cell f12 = row6.createCell(11);
            f12.setCellStyle(style3);
            f12.setCellValue("%");

            Cell f13 = row6.createCell(12);
            f13.setCellStyle(style3);
            f13.setCellValue("Comm.Fee");

            Cell f131 = row6.createCell(13);
            f131.setCellStyle(style3);
            f131.setCellValue("Round");

            Cell f14 = row6.createCell(14);
            f14.setCellStyle(style3);
            f14.setCellValue("Pay In / Pay Out");

            Cell f17 = row6.createCell(15);
            f17.setCellStyle(style3);
            f17.setCellValue("Pos / Bank Acc");

            //Popolo la tabella
            ArrayList<TillTransactionListCurrency_value> dati = siq.getDati();

            int cntriga = 7;

            double amount = 0, totale = 0, commfee = 0, payout = 0;

            double totalepayinoutgeneral = 0;

            double totround = 0;

            for (int i = 0; (i < dati.size() - 1) || (dati.size() == 1 && i == 0); i++) {

                cntriga++;
                Row row7 = sheet.createRow((short) cntriga);

                TillTransactionListCurrency_value actual = dati.get(i);

                TillTransactionListCurrency_value prossimo = null;

                if (dati.size() > 1) {
                    prossimo = dati.get(i + 1);
                }

                if (!actual.getType().contains("D")) {

                    amount += fd(actual.getAmountSenzaFormattazione());
                    totale += fd(actual.getTotalSenzaFormattazione());
                    commfee += fd(actual.getComfreeSenzaFormattazione());
                    payout += fd(actual.getPayinpayoutSenzaFormattazione());
                }

                Cell f18 = row7.createCell(1);
                f18.setCellStyle(style4left);
                f18.setCellValue(actual.getType());

                Cell f19 = row7.createCell(2);
                f19.setCellStyle(style4);
                f19.setCellValue(actual.getTill());

                Cell f20 = row7.createCell(3);
                f20.setCellStyle(style4);
                f20.setCellValue(actual.getUser());

                Cell f21 = row7.createCell(4);
                f21.setCellStyle(style4);
                f21.setCellValue(actual.getNotr());

                Cell f22 = row7.createCell(5);
                f22.setCellStyle(style4left);
                f22.setCellValue(formatStringtoStringDate(actual.getTime(), patternsqldate, patternnormdate));

                Cell f23 = row7.createCell(6);
                f23.setCellStyle(style4left);
                f23.setCellValue(actual.getCur());

                Cell f24 = row7.createCell(7);
                f24.setCellStyle(style4left);
                f24.setCellValue(actual.getKind());

                Cell f25 = row7.createCell(8);
                f25.setCellStyle(style4);
                f25.setCellValue(formatMysqltoDisplay(actual.getAmount()));

                Cell f26 = row7.createCell(9);
                f26.setCellStyle(style4);
                f26.setCellValue(formatMysqltoDisplay(actual.getRate()));

                Cell f27 = row7.createCell(10);
                f27.setCellStyle(style4);
                f27.setCellValue(formatMysqltoDisplay(actual.getTotal()));

                Cell f28 = row7.createCell(11);
                f28.setCellStyle(style4);
                f28.setCellValue(formatMysqltoDisplay(actual.getPerc()));

                Cell f29 = row7.createCell(12);
                f29.setCellStyle(style4);
                f29.setCellValue(formatMysqltoDisplay(actual.getComfree()));

                Cell f291 = row7.createCell(13);
                f291.setCellStyle(style4);
                f291.setCellValue(formatMysqltoDisplay(actual.getRound()));

                if (!actual.getType().contains("D")) {
                    totround = totround + fd(actual.getRound());
                }

                Cell f30 = row7.createCell(14);
                f30.setCellStyle(style4);
                if (actual.getKind().contains("Sell")) {
                } else {

                    f30.setCellValue("-" + formatMysqltoDisplay(actual.getPayinpayout()));
                    if (!actual.getType().contains("D")) {
                        totalepayinoutgeneral -= fd(actual.getPayinpayoutSenzaFormattazione());
                    }
                }

                Cell f33 = row7.createCell(15);
                f33.setCellStyle(style4);
                f33.setCellValue(actual.getPos());

                cntriga++;
                cntriga++;

                Row row8 = sheet.createRow((short) cntriga);

                if (prossimo != null && !prossimo.getCur().equals(actual.getCur())) {

                    for (int h = 0; h < columnWidths2.length; h++) {

                        if (h == 5) {
                            Cell f50 = row8.createCell(h + 1);
                            f50.setCellStyle(style3left);
                            f50.setCellValue(actual.getCur());
                        } else if (h == 7) {

                            Cell f34 = row8.createCell(h + 1);
                            f34.setCellStyle(style3);
                            f34.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(amount, 2)));

                        } else if (h == 9) {

                            Cell f34 = row8.createCell(h + 1);
                            f34.setCellStyle(style3);
                            f34.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(totale, 2)));

                        } else if (h == 11) {

                            Cell f34 = row8.createCell(h + 1);
                            f34.setCellStyle(style3);
                            f34.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(commfee, 2)));

                        } else if (h == 12) {

                            Cell f34 = row8.createCell(h + 1);
                            f34.setCellStyle(style3);
                            f34.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(totround, 2)));

                        } else if (h == 13) {

                            Cell f34 = row8.createCell(h + 1);
                            f34.setCellStyle(style3);
                            f34.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(totalepayinoutgeneral, 2)));
                        }

                    }

                    cntriga++;

                    amount = 0;
                    totale = 0;
                    commfee = 0;
                    payout = 0;
                    totalepayinoutgeneral = 0;
                    totround = 0;

                }

                cntriga++;
                Row row9 = sheet.createRow((short) cntriga);

                if ((dati.size() == 1 && i == 0) || (i == dati.size() - 2)) {

                    //  totalepayinoutgeneral = 0;
                    if (prossimo != null && !prossimo.getType().contains("D")) {

                        amount += fd(prossimo.getAmountSenzaFormattazione());
                        totale += fd(prossimo.getTotalSenzaFormattazione());
                        commfee += fd(prossimo.getComfreeSenzaFormattazione());
                        payout += fd(prossimo.getPayinpayoutSenzaFormattazione());

                        Cell f34 = row9.createCell(1);
                        f34.setCellStyle(style4left);
                        f34.setCellValue(prossimo.getType());

                        Cell f35 = row9.createCell(2);
                        f35.setCellStyle(style4);
                        f35.setCellValue(prossimo.getTill());

                        Cell f36 = row9.createCell(3);
                        f36.setCellStyle(style4);
                        f36.setCellValue(prossimo.getUser());

                        Cell f37 = row9.createCell(4);
                        f37.setCellStyle(style4);
                        f37.setCellValue(prossimo.getNotr());

                        Cell f38 = row9.createCell(5);
                        f38.setCellStyle(style4left);
                        f38.setCellValue(formatStringtoStringDate(prossimo.getTime(), patternsqldate, patternnormdate));

                        Cell f39 = row9.createCell(6);
                        f39.setCellStyle(style4left);
                        f39.setCellValue(prossimo.getCur());

                        Cell f40 = row9.createCell(7);
                        f40.setCellStyle(style4left);
                        f40.setCellValue(prossimo.getKind());

                        Cell f41 = row9.createCell(8);
                        f41.setCellStyle(style4);
                        f41.setCellValue(formatMysqltoDisplay(prossimo.getAmount()));

                        Cell f42 = row9.createCell(9);
                        f42.setCellStyle(style4);
                        f42.setCellValue(formatMysqltoDisplay(prossimo.getRate()));

                        Cell f43 = row9.createCell(10);
                        f43.setCellStyle(style4);
                        f43.setCellValue(formatMysqltoDisplay(prossimo.getTotal()));

                        Cell f44 = row9.createCell(11);
                        f44.setCellStyle(style4);
                        f44.setCellValue(formatMysqltoDisplay(prossimo.getPerc()));

                        Cell f45 = row9.createCell(12);
                        f45.setCellStyle(style4);
                        f45.setCellValue(formatMysqltoDisplay(prossimo.getComfree()));

                        Cell f451 = row9.createCell(13);
                        f451.setCellStyle(style4);
                        f451.setCellValue(formatMysqltoDisplay(prossimo.getRound()));

                        totround = totround + fd((valueOf(prossimo.getRound())));

                        Cell f46 = row9.createCell(14);
                        f46.setCellStyle(style4);
                        f46.setCellValue("-" + formatMysqltoDisplay(prossimo.getPayinpayout()));
                        if (!prossimo.getType().contains("D")) {
                            totalepayinoutgeneral -= fd(prossimo.getPayinpayoutSenzaFormattazione());
                        }

                        Cell f49 = row9.createCell(15);
                        f49.setCellStyle(style4);
                        f49.setCellValue(prossimo.getPos());

                        cntriga++;
                        cntriga++;

                    }

                    Row row9bis = sheet.createRow((short) cntriga);

                    for (int h = 0; h < columnWidths2.length; h++) {

                        if (h == 5) {
                            Cell f50 = row9bis.createCell(h + 1);
                            f50.setCellStyle(style3left);
                            if (prossimo != null && !prossimo.getType().contains("D")) {
                                f50.setCellValue(prossimo.getCur());
                            } else {
                                f50.setCellValue(actual.getCur());
                            }

                        } else if (h == 7) {

                            Cell f50 = row9bis.createCell(h + 1);
                            f50.setCellStyle(style3);
                            f50.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(amount, 2)));

                        } else if (h == 9) {

                            Cell f50 = row9bis.createCell(h + 1);
                            f50.setCellStyle(style3);
                            f50.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(totale, 2)));

                        } else if (h == 11) {

                            Cell f50 = row9bis.createCell(h + 1);
                            f50.setCellStyle(style3);
                            f50.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(commfee, 2)));

                        } else if (h == 12) {

                            Cell f50 = row9bis.createCell(h + 1);
                            f50.setCellStyle(style3);
                            f50.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(totround, 2)));

                        } else if (h == 13) {

                            Cell f50 = row9bis.createCell(h + 1);
                            f50.setCellStyle(style3);
                            f50.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(totalepayinoutgeneral, 2)));

                        }

                    }

                    cntriga++;

                }

            }

            if (ba != null) {
                cntriga++;

                Row rowN = sheet.createRow((short) cntriga);
                Cell clrowN = rowN.createCell(1);
                clrowN.setCellStyle(style1);
                clrowN.setCellValue("EXTERNAL TRANSFER - TO BANK");
                sheet.addMergedRegion(new CellRangeAddress(cntriga, cntriga, 1, 7));

                cntriga++;

                row6 = sheet.createRow((short) cntriga);

                cntriga++;

                f2 = row6.createCell(1);
                f2.setCellStyle(style3left);
                f2.setCellValue("Type");

                f3 = row6.createCell(2);
                f3.setCellStyle(style3);
                f3.setCellValue("Till");

                f4 = row6.createCell(3);
                f4.setCellStyle(style3);
                f4.setCellValue("User");

                f5 = row6.createCell(4);
                f5.setCellStyle(style3);
                f5.setCellValue("No.Tr.");

                f6 = row6.createCell(5);
                f6.setCellStyle(style3left);
                f6.setCellValue("Date / Time");

                f7 = row6.createCell(6);
                f7.setCellStyle(style3left);
                f7.setCellValue("Cur");

                f8 = row6.createCell(7);
                f8.setCellStyle(style3left);
                f8.setCellValue("Kind");

                f9 = row6.createCell(8);
                f9.setCellStyle(style3);
                f9.setCellValue("Amount");

                f10 = row6.createCell(9);
                f10.setCellStyle(style3);
                f10.setCellValue("Rate");

                f11 = row6.createCell(10);
                f11.setCellStyle(style3);
                f11.setCellValue("Equivalent");

                f14 = row6.createCell(11);
                f14.setCellStyle(style3);
                f14.setCellValue("Buy Value");

                f14 = row6.createCell(12);
                f14.setCellStyle(style3);
                f14.setCellValue("SPREAD");

                f17 = row6.createCell(13);
                f17.setCellStyle(style3);
                f17.setCellValue("Pos / Bank Acc");

                amount = 0;
                totale = 0;
                commfee = 0;
                payout = 0;

                dati = ba.getDati();

                for (int i = 0; (i < dati.size() - 1) || (dati.size() == 1 && i == 0); i++) {

                    cntriga++;
                    Row row7 = sheet.createRow((short) cntriga);

                    TillTransactionListCurrency_value actual = (TillTransactionListCurrency_value) dati.get(i);

                    TillTransactionListCurrency_value prossimo = null;

                    if (dati.size() > 1) {
                        prossimo = (TillTransactionListCurrency_value) dati.get(i + 1);
                    }

                    if (!actual.getType().contains("D")) {

                        amount += fd(actual.getAmountSenzaFormattazione());
                        totale += fd(actual.getTotalSenzaFormattazione());
                        commfee += fd(actual.getComfreeSenzaFormattazione());
                        payout += fd(actual.getPayinpayoutSenzaFormattazione());
                    }

                    Cell f18 = row7.createCell(1);
                    f18.setCellStyle(style4left);
                    f18.setCellValue(actual.getType());

                    Cell f19 = row7.createCell(2);
                    f19.setCellStyle(style4);
                    f19.setCellValue(actual.getTill());

                    Cell f20 = row7.createCell(3);
                    f20.setCellStyle(style4);
                    f20.setCellValue(actual.getUser());

                    Cell f21 = row7.createCell(4);
                    f21.setCellStyle(style4);
                    f21.setCellValue(actual.getNotr());

                    Cell f22 = row7.createCell(5);
                    f22.setCellStyle(style4left);
                    f22.setCellValue(formatStringtoStringDate(actual.getTime(), patternsqldate, patternnormdate));

                    Cell f23 = row7.createCell(6);
                    f23.setCellStyle(style4left);
                    f23.setCellValue(actual.getCur());

                    Cell f24 = row7.createCell(7);
                    f24.setCellStyle(style4left);
                    f24.setCellValue(actual.getKind());

                    Cell f25 = row7.createCell(8);
                    f25.setCellStyle(style4);
                    f25.setCellValue(formatMysqltoDisplay(actual.getAmount()));

                    Cell f26 = row7.createCell(9);
                    f26.setCellStyle(style4);
                    f26.setCellValue(formatMysqltoDisplay(actual.getRate()));

                    Cell f27 = row7.createCell(10);
                    f27.setCellStyle(style4);
                    f27.setCellValue(formatMysqltoDisplay(actual.getTotal()));

                    Cell f28 = row7.createCell(11);
                    f28.setCellStyle(style4);
                    f28.setCellValue(formatMysqltoDisplay(actual.getPayinpayout()));

                    Cell f30 = row7.createCell(12);
                    f30.setCellStyle(style4);
                    f30.setCellValue(formatMysqltoDisplay(actual.getSpread()));

                    Cell f33 = row7.createCell(13);
                    f33.setCellStyle(style4);
                    f33.setCellValue(actual.getPos());

                    cntriga++;
                    cntriga++;

                    Row row8 = sheet.createRow((short) cntriga);

                    if (prossimo != null && !prossimo.getCur().equals(actual.getCur())) {

                        for (int h = 0; h < columnWidths2.length; h++) {

                            if (h == 5) {
                                Cell f50 = row8.createCell(h + 1);
                                f50.setCellStyle(style3left);
                                f50.setCellValue(actual.getCur());
                            } else if (h == 7) {

                                Cell f34 = row8.createCell(h + 1);
                                f34.setCellStyle(style3);
                                f34.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(amount, 2)));

                            } else if (h == 9) {

                                Cell f34 = row8.createCell(h + 1);
                                f34.setCellStyle(style3);
                                f34.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(totale, 2)));

                            }

                        }

                        cntriga++;

                        amount = 0;
                        totale = 0;
                        commfee = 0;
                        payout = 0;

                    }

                    cntriga++;
                    Row row9 = sheet.createRow((short) cntriga);

                    if ((dati.size() == 1 && i == 0) || (i == dati.size() - 2)) {

                        //  totalepayinoutgeneral = 0;
                        if (prossimo != null && !prossimo.getType().contains("D")) {

                            amount += fd(prossimo.getAmountSenzaFormattazione());
                            totale += fd(prossimo.getTotalSenzaFormattazione());

                            Cell f34 = row9.createCell(1);
                            f34.setCellStyle(style4left);
                            f34.setCellValue(prossimo.getType());

                            Cell f35 = row9.createCell(2);
                            f35.setCellStyle(style4);
                            f35.setCellValue(prossimo.getTill());

                            Cell f36 = row9.createCell(3);
                            f36.setCellStyle(style4);
                            f36.setCellValue(prossimo.getUser());

                            Cell f37 = row9.createCell(4);
                            f37.setCellStyle(style4);
                            f37.setCellValue(prossimo.getNotr());

                            Cell f38 = row9.createCell(5);
                            f38.setCellStyle(style4left);
                            f38.setCellValue(formatStringtoStringDate(prossimo.getTime(), patternsqldate, patternnormdate));

                            Cell f39 = row9.createCell(6);
                            f39.setCellStyle(style4left);
                            f39.setCellValue(prossimo.getCur());

                            Cell f40 = row9.createCell(7);
                            f40.setCellStyle(style4left);
                            f40.setCellValue(prossimo.getKind());

                            Cell f41 = row9.createCell(8);
                            f41.setCellStyle(style4);
                            f41.setCellValue(formatMysqltoDisplay(prossimo.getAmount()));

                            Cell f42 = row9.createCell(9);
                            f42.setCellStyle(style4);
                            f42.setCellValue(formatMysqltoDisplay(prossimo.getRate()));

                            Cell f43 = row9.createCell(10);
                            f43.setCellStyle(style4);
                            f43.setCellValue(formatMysqltoDisplay(prossimo.getTotal()));

                            Cell f44 = row9.createCell(11);
                            f44.setCellStyle(style4);
                            f44.setCellValue(formatMysqltoDisplay(prossimo.getPayinpayout()));

                            Cell f46 = row9.createCell(12);
                            f46.setCellStyle(style4);
                            f46.setCellValue(formatMysqltoDisplay(prossimo.getSpread()));

                            Cell f49 = row9.createCell(13);
                            f49.setCellStyle(style4);
                            f49.setCellValue(prossimo.getPos());

                            cntriga++;
                            cntriga++;

                        }

                        Row row9bis = sheet.createRow((short) cntriga);

                        for (int h = 0; h < columnWidths2.length; h++) {

                            if (h == 5) {
                                Cell f50 = row9bis.createCell(h + 1);
                                f50.setCellStyle(style3left);
                                f50.setCellValue(actual.getCur());
                            } else if (h == 7) {

                                Cell f50 = row9bis.createCell(h + 1);
                                f50.setCellStyle(style3);
                                f50.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(amount, 2)));

                            } else if (h == 9) {

                                Cell f50 = row9bis.createCell(h + 1);
                                f50.setCellStyle(style3);
                                f50.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(totale, 2)));

                            }
                        }

                        cntriga++;

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
            sheet.autoSizeColumn(15);
            sheet.autoSizeColumn(16);
            sheet.autoSizeColumn(17);
            sheet.autoSizeColumn(18);
            sheet.autoSizeColumn(19);
            sheet.autoSizeColumn(20);
            sheet.autoSizeColumn(21);
            sheet.autoSizeColumn(22);
            sheet.autoSizeColumn(23);

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
