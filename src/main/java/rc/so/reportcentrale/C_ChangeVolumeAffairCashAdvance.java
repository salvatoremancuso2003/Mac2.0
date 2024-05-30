/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rc.so.reportcentrale;

import com.itextpdf.text.BadElementException;
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
import com.itextpdf.text.Image;
import static com.itextpdf.text.Image.getInstance;
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
import rc.so.db.Db_Master;
import rc.so.entity.Branch;
import static rc.so.util.Engine.getConf;
import static rc.so.util.Engine.insertTR;
import static rc.so.util.Utility.ff;
import static rc.so.util.Utility.formatMysqltoDisplay;
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
import static org.apache.commons.codec.binary.Base64.decodeBase64;
import static org.apache.commons.codec.binary.Base64.encodeBase64;
import static org.apache.commons.io.FileUtils.readFileToByteArray;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import static org.apache.poi.hssf.usermodel.HSSFFont.FONT_ARIAL;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import static org.apache.poi.ss.usermodel.BorderStyle.THIN;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Drawing;
import static org.apache.poi.ss.usermodel.HorizontalAlignment.LEFT;
import static org.apache.poi.ss.usermodel.HorizontalAlignment.RIGHT;
import org.apache.poi.ss.usermodel.Picture;
import org.apache.poi.ss.usermodel.Row;
import static org.apache.poi.ss.usermodel.Workbook.PICTURE_TYPE_PNG;

/**
 *
 * @author fplacanica
 */
public class C_ChangeVolumeAffairCashAdvance {

    //column

    /**
     *
     */
   public static final float[] columnWidths0 = new float[]{70f, 30f};

    /**
     *
     */
    public static final float[] columnWidths1 = new float[]{60f};

    /**
     *
     */
    public static float[] columnWidths2 = new float[]{30f, 10f, 10f, 10f, 10f, 10f, 10f};

    /**
     *
     */
    public static float[] columnWidths2False = new float[]{30f, 10f, 10f, 10f, 10f, 10f};

    /**
     *
     */
    public static float[] columnWidths3 = new float[]{10f, 10f, 10f, 10f, 10f, 10f, 10f, 10f, 10f, 10f, 10f, 10f, 10f, 10f, 10f, 10f, 10f, 10f, 10f};

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
    final String intestazionePdf = "Change Turnover - Cash Advance";
    Phrase vuoto = new Phrase("\n");

    //resource
    //public static final String logo = "web/resource/logocl.png";
//    public static final String logo = "img/logocl.png";
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

    private PdfPTable tableFinale = new PdfPTable(6);
    private PdfPTable tableFinaleColonne = new PdfPTable(6);
    private PdfPCell cellFinale = new PdfPCell();
    private PdfPCell cellFinaleColonne = new PdfPCell();
    private Phrase phraseFinale = new Phrase();

    double totBuy = 0;
    double totSell = 0;
    double tot = 0;
    int totNtrans = 0;
    double totcheckAdv = 0;

    /**
     ** Constructor
     */
    public C_ChangeVolumeAffairCashAdvance() {

        this.f0_bold = getFont(HELVETICA, WINANSI, 14.04f, BOLD);
        this.f1_bold = getFont(HELVETICA, WINANSI, 9.96f, BOLD);
        this.f2_bold = getFont(HELVETICA, WINANSI, 8f, BOLD);
        this.f3_bold = getFont(HELVETICA, WINANSI, 8f, BOLDITALIC);
        this.f4_bold = getFont(HELVETICA, WINANSI, 5f, BOLD);
        this.f1_normal = getFont(HELVETICA, WINANSI, 9.96f, NORMAL);
        this.f2_normal = getFont(HELVETICA, WINANSI, 8f, NORMAL);
        this.f3_normal = getFont(HELVETICA, WINANSI, 5f, NORMAL);

    }

    /**
     *
     * @param cmfb
     * @param colonne
     * @param colonneriepologo
     * @param firstTime
     * @param lastTime
     * @param document
     * @param checkTrans
     * @param checkRiepilogo
     * @return
     * @throws DocumentException
     * @throws BadElementException
     * @throws IOException
     */
    public Document receipt(C_ChangeVolumeAffairCashAdvance_value cmfb, ArrayList<String> colonne,ArrayList<String> colonneriepologo, boolean firstTime, boolean lastTime, Document document, boolean checkTrans, boolean checkRiepilogo) throws DocumentException, BadElementException, IOException {

        String filiale = "";
        double totalBuy = 0;
        double totalSell = 0;
        double total = 0;
        double totalcheckAdv = 0;
        int nTrans = 0;
        try {

            if (firstTime) {

                Image img = getInstance(decodeBase64(getConf("path.logocl")));
                img.scalePercent(40.0F);
                document.add(img);

                PdfPTable table = new PdfPTable(1);
                table.setWidths(columnWidths1);
                table.setWidthPercentage(100);
                Phrase phrase1 = new Phrase();
                phrase1.add(new Chunk("MACCORP ITALIANA S.p.A", f3_bold));
                PdfPCell cell1 = new PdfPCell(phrase1);
                cell1.setBorder(NO_BORDER);

                table.addCell(cell1);
                document.add(table);

                table = new PdfPTable(2);
                table.setWidths(columnWidths0);
                table.setWidthPercentage(100);
                phrase1 = new Phrase();
                phrase1.add(new Chunk(intestazionePdf + " From " + cmfb.getDataDa() + " to " + cmfb.getDataA(), f4_bold));
                cell1 = new PdfPCell(phrase1);
                cell1.setBorder(NO_BORDER);
                Paragraph pa1 = new Paragraph(new Phrase("", f3_bold));
                pa1.setAlignment(ALIGN_RIGHT);
                PdfPCell cell2 = new PdfPCell(pa1);
                cell2.setBorder(NO_BORDER);
                cell2.setHorizontalAlignment(ALIGN_RIGHT);

                Phrase phrase4 = new Phrase();
                phrase4.add(new Chunk("", f3_normal));
                PdfPCell cell4 = new PdfPCell(phrase4);
                cell4.setBorder(NO_BORDER);
                table.addCell(cell1);
                table.addCell(cell2);
                table.addCell(cell4);
                document.add(table);
                vuoto.setFont(f3_normal);
                document.add(vuoto);

            } else {

                document.newPage();

                Image img = getInstance(decodeBase64(getConf("path.logocl")));
                img.scalePercent(40.0F);
                document.add(img);

                PdfPTable table = new PdfPTable(1);
                table.setWidths(columnWidths1);
                table.setWidthPercentage(100);
                Phrase phrase1 = new Phrase();
                phrase1.add(new Chunk("MACCORP ITALIANA S.p.A", f3_bold));
                PdfPCell cell1 = new PdfPCell(phrase1);
                cell1.setBorder(NO_BORDER);

                table.addCell(cell1);
                document.add(table);

                table = new PdfPTable(2);
                table.setWidths(columnWidths0);
                table.setWidthPercentage(100);
                phrase1 = new Phrase();
                phrase1.add(new Chunk(intestazionePdf+" From " + cmfb.getDataDa() + " to " + cmfb.getDataA(), f4_bold));
                cell1 = new PdfPCell(phrase1);
                cell1.setBorder(NO_BORDER);
                Paragraph pa1 = new Paragraph(new Phrase("", f3_normal));
                pa1.setAlignment(ALIGN_RIGHT);
                PdfPCell cell2 = new PdfPCell(pa1);
                cell2.setBorder(NO_BORDER);
                cell2.setHorizontalAlignment(ALIGN_RIGHT);

                Phrase phrase4 = new Phrase();
                phrase4.add(new Chunk("", f3_normal));
                PdfPCell cell4 = new PdfPCell(phrase4);
                cell4.setBorder(NO_BORDER);
                table.addCell(cell1);
                table.addCell(cell2);
                table.addCell(cell4);
                document.add(table);
                vuoto.setFont(f3_normal);
                document.add(vuoto);
            }

            if (true) {

                ArrayList<C_ChangeVolumeAffairCashAdvance_value> dati = cmfb.getDati();
                Phrase phraset;
                PdfPCell cellt;
                PdfPTable table3;

                float qta = 0, amount = 0;

                qta = 0;
                amount = 0;

                PdfPTable table2 = new PdfPTable(colonne.size());
                if (checkTrans) {
                    table2.setWidths(columnWidths2);
                } else {
                    table2.setWidths(columnWidths2False);
                }
                table2.setWidthPercentage(100);

                PdfPCell[] list = new PdfPCell[colonne.size()];
                //mi scandisco le colonne
                for (int c = 0; c < colonne.size(); c++) {
                    Phrase phraset1 = new Phrase();
                    phraset1.add(new Chunk(colonne.get(c), f4_bold));
                    PdfPCell cellt1 = new PdfPCell(phraset1);
                    cellt1.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt1.setBorder(BOTTOM | TOP);
                    cellt1.setFixedHeight(15f);
                    cellt1.setBackgroundColor(LIGHT_GRAY);
                    cellt1.setBorderWidth(0.7f);
                    if(c==0 || c==1){
                        cellt1.setHorizontalAlignment(ALIGN_LEFT);
                    }                    
                    list[c] = cellt1;
                }

                table3 = new PdfPTable(colonne.size());
                if (checkTrans) {
                    table3.setWidths(columnWidths2);
                } else {
                    table3.setWidths(columnWidths2False);
                }
                table3.setWidthPercentage(100);

                for (PdfPCell list1 : list) {
                    PdfPCell temp = (PdfPCell) (list1);
                    table3.addCell(temp);
                }

                document.add(table3);

                ArrayList<C_ChangeVolumeAffairCashAdvance_value> lista = cmfb.getDati();

                for (int x = 0; x < lista.size(); x++) {

                    C_ChangeVolumeAffairCashAdvance_value temp = (C_ChangeVolumeAffairCashAdvance_value) lista.get(x);

                    LineSeparator sep = new LineSeparator();
                    sep.setOffset(-2);
                    sep.setLineWidth((float) 0.5);

                    PdfPTable table4 = new PdfPTable(colonne.size());
                    if (checkTrans) {
                        table4.setWidths(columnWidths2);
                    } else {
                        table4.setWidths(columnWidths2False);
                    }
                    table4.setWidthPercentage(100);

                    phraset = new Phrase();
                    phraset.add(new Chunk(temp.getBranch(), f3_normal));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_LEFT);
                    cellt.setBorder(BOTTOM);
                    table4.addCell(cellt);

                    phraset = new Phrase();
                    phraset.add(new Chunk(temp.getDate(), f3_normal));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_LEFT);
                    cellt.setBorder(BOTTOM);
                    table4.addCell(cellt);

                    phraset = new Phrase();
                    phraset.add(new Chunk(formatMysqltoDisplay(temp.getBuy()), f3_normal));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setBorder(BOTTOM);
                    table4.addCell(cellt);

                    phraset = new Phrase();
                    phraset.add(new Chunk(formatMysqltoDisplay(temp.getSell()), f3_normal));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setBorder(BOTTOM);
                    table4.addCell(cellt);

                    phraset = new Phrase();
                    phraset.add(new Chunk(formatMysqltoDisplay(temp.getTotal()), f3_normal));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setBorder(BOTTOM);
                    table4.addCell(cellt);

                    phraset = new Phrase();
                    phraset.add(new Chunk(formatMysqltoDisplay(temp.getCashAdv()), f3_normal));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setBorder(BOTTOM);
                    table4.addCell(cellt);

                    if (checkTrans) {

                        phraset = new Phrase();
                        phraset.add(new Chunk(temp.getNoTrans(), f3_normal));
                        cellt = new PdfPCell(phraset);
                        cellt.setHorizontalAlignment(ALIGN_RIGHT);
                        cellt.setBorder(BOTTOM);
                        table4.addCell(cellt);

                    }

                    filiale = temp.getBranch();

                    totalBuy += ff(temp.getBuy());
                    totalSell += ff(temp.getSell());
                    total += ff(temp.getTotal());
                    nTrans += ff(temp.getNoTrans());
                    totalcheckAdv += ff(temp.getCashAdv());

                    document.add(table4);

                }
                
                
                 totalBuy =roundDouble(totalBuy,2);
                    totalSell =roundDouble(totalSell,2);
                    total =roundDouble(total,2);
                    totalcheckAdv =roundDouble(totalcheckAdv,2);
                

                //linea totale per valuta
                LineSeparator sep = new LineSeparator();
                sep.setLineWidth(0.7f);
                document.add(sep);

                PdfPTable table4 = new PdfPTable(colonne.size());
                if (checkTrans) {
                    table4.setWidths(columnWidths2);
                } else {
                    table4.setWidths(columnWidths2False);
                }
                table4.setWidthPercentage(100);

                phraset = new Phrase();
                phraset.add(new Chunk(filiale, f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_LEFT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk("Total", f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_LEFT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(totalBuy,2)), f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(totalSell,2)), f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(total,2)), f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(totalcheckAdv,2)), f4_bold));
//                phraset.add(new Chunk("", f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);

                if (checkTrans) {

                    phraset = new Phrase();
                    phraset.add(new Chunk(formatMysqltoDisplay(valueOf(nTrans)), f4_bold));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setBorder(BOTTOM);
                    table4.addCell(cellt);

                }

                document.add(table4);

                totBuy += totalBuy;
                totSell += totalSell;
                tot += total;
                   totcheckAdv += totalcheckAdv;
                totNtrans += nTrans;

                //fine totale
                vuoto.setFont(f3_normal);
                document.add(vuoto);
            }
            
            
             totBuy =roundDouble(totBuy,2);
                totSell  =roundDouble(totSell,2);
                tot  =roundDouble(tot,2);
            
            //tabella finale
            //tabella ultima pagina
            if (checkRiepilogo) {
                //tabella corretta
                //impostazione colonne
                if (true) {
                    tableFinaleColonne = new PdfPTable(colonne.size());
                    if (checkTrans) {
                        tableFinaleColonne.setWidths(columnWidths2);
                    } else {
                        tableFinaleColonne.setWidths(columnWidths2False);
                    }
                    tableFinaleColonne.setWidthPercentage(100);

                    PdfPCell[] listFinale = new PdfPCell[colonneriepologo.size()];
                    //mi scandisco le colonne
                    for (int c = 0; c < colonneriepologo.size(); c++) {
                        Phrase phraset1 = new Phrase();
                        phraset1.add(new Chunk(colonneriepologo.get(c), f4_bold));
                        PdfPCell cellt1 = new PdfPCell(phraset1);
                        cellt1.setHorizontalAlignment(ALIGN_RIGHT);
                        cellt1.setBorder(BOTTOM | TOP);
                        cellt1.setFixedHeight(15f);
                        cellt1.setBackgroundColor(LIGHT_GRAY);
                        cellt1.setBorderWidth(0.7f);
                        
                        if(c==0 || c==1){
                             cellt1.setHorizontalAlignment(ALIGN_LEFT);
                        }

                        listFinale[c] = cellt1;
                    }

                    tableFinaleColonne = new PdfPTable(colonne.size());
                    if (checkTrans) {
                        tableFinaleColonne.setWidths(columnWidths2);
                    } else {
                        tableFinaleColonne.setWidths(columnWidths2False);
                    }

                    tableFinaleColonne.setWidthPercentage(100);

                    for (PdfPCell listFinale1 : listFinale) {
                        PdfPCell temp = (PdfPCell) (listFinale1);
                        tableFinaleColonne.addCell(temp);
                    }

                    LineSeparator sep = new LineSeparator();
                    sep.setOffset(-2);
                    sep.setLineWidth((float) 0.5);

                    tableFinale = new PdfPTable(colonne.size());
                    if (checkTrans) {
                        tableFinale.setWidths(columnWidths2);
                    } else {
                        tableFinale.setWidths(columnWidths2False);
                    }
                    tableFinale.setWidthPercentage(100);

                }
                //fine impostazione colonne

                if (!lastTime) {
                    Phrase phraset = new Phrase();
                    phraset.add(new Chunk(filiale, f3_normal));
                    cellFinale = new PdfPCell(phraset);
                    cellFinale.setHorizontalAlignment(ALIGN_LEFT);
                    cellFinale.setBorder(BOTTOM);
                    tableFinale.addCell(cellFinale);

                    phraset = new Phrase();
                    phraset.add(new Chunk("", f3_normal));
                    cellFinale = new PdfPCell(phraset);
                    cellFinale.setHorizontalAlignment(ALIGN_RIGHT);
                    cellFinale.setBorder(BOTTOM);
                    tableFinale.addCell(cellFinale);

                    phraset = new Phrase();
                    phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(totalBuy,2)), f3_normal));
                    cellFinale = new PdfPCell(phraset);
                    cellFinale.setHorizontalAlignment(ALIGN_RIGHT);
                    cellFinale.setBorder(BOTTOM);
                    tableFinale.addCell(cellFinale);

                    phraset = new Phrase();
                    phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(totalSell,2)), f3_normal));
                    cellFinale = new PdfPCell(phraset);
                    cellFinale.setHorizontalAlignment(ALIGN_RIGHT);
                    cellFinale.setBorder(BOTTOM);
                    tableFinale.addCell(cellFinale);

                    phraset = new Phrase();
                    phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(total,2)), f3_normal));
                    cellFinale = new PdfPCell(phraset);
                    cellFinale.setHorizontalAlignment(ALIGN_RIGHT);
                    cellFinale.setBorder(BOTTOM);
                    tableFinale.addCell(cellFinale);

                    phraset = new Phrase();
                     phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(totalcheckAdv,2)), f3_normal));
//                    phraset.add(new Chunk("", f3_normal));
                    cellFinale = new PdfPCell(phraset);
                    cellFinale.setHorizontalAlignment(ALIGN_RIGHT);
                    cellFinale.setBorder(BOTTOM);
                    tableFinale.addCell(cellFinale);

                    if (checkTrans) {
                        phraset = new Phrase();
                        phraset.add(new Chunk(formatMysqltoDisplay(valueOf(nTrans)), f3_normal));
                        cellFinale = new PdfPCell(phraset);
                        cellFinale.setHorizontalAlignment(ALIGN_RIGHT);
                        cellFinale.setBorder(BOTTOM);
                        tableFinale.addCell(cellFinale);

                    }
                }

                //fine tabella corretta
                //fine 1 parte
                if (lastTime) {
                    //linea totale
                    LineSeparator sep = new LineSeparator();
                    sep.setLineWidth(0.7f);
                    document.add(sep);

                    Phrase phraset = new Phrase();
                    phraset.add(new Chunk(filiale, f4_bold));
                    cellFinale = new PdfPCell(phraset);
                    cellFinale.setHorizontalAlignment(ALIGN_LEFT);
                    cellFinale.setBorder(BOTTOM);
                    tableFinale.addCell(cellFinale);

                    phraset = new Phrase();
                    phraset.add(new Chunk("Total", f4_bold));
                    cellFinale = new PdfPCell(phraset);
                    cellFinale.setHorizontalAlignment(ALIGN_LEFT);
                    cellFinale.setBorder(BOTTOM);
                    tableFinale.addCell(cellFinale);

                    phraset = new Phrase();
                    phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(totBuy,2)), f4_bold));
                    cellFinale = new PdfPCell(phraset);
                    cellFinale.setHorizontalAlignment(ALIGN_RIGHT);
                    cellFinale.setBorder(BOTTOM);
                    tableFinale.addCell(cellFinale);

                    phraset = new Phrase();
                    phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(totSell,2)), f4_bold));
                    cellFinale = new PdfPCell(phraset);
                    cellFinale.setHorizontalAlignment(ALIGN_RIGHT);
                    cellFinale.setBorder(BOTTOM);
                    tableFinale.addCell(cellFinale);

                    phraset = new Phrase();
                    phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(tot,2)), f4_bold));
                    cellFinale = new PdfPCell(phraset);
                    cellFinale.setHorizontalAlignment(ALIGN_RIGHT);
                    cellFinale.setBorder(BOTTOM);
                    tableFinale.addCell(cellFinale);

                    phraset = new Phrase();
                    phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(totcheckAdv,2)), f4_bold));
                    cellFinale = new PdfPCell(phraset);
                    cellFinale.setHorizontalAlignment(ALIGN_RIGHT);
                    cellFinale.setBorder(BOTTOM);
                    tableFinale.addCell(cellFinale);

                    if (checkTrans) {
                        phraset = new Phrase();
                        phraset.add(new Chunk(formatMysqltoDisplay(valueOf(totNtrans)), f4_bold));
                        cellFinale = new PdfPCell(phraset);
                        cellFinale.setHorizontalAlignment(ALIGN_RIGHT);
                        cellFinale.setBorder(BOTTOM);
                        tableFinale.addCell(cellFinale);

                    }
                    //fine totale

                    document.add(tableFinaleColonne);
                    document.add(tableFinale);
                    vuoto.setFont(f3_normal);
                    document.add(vuoto);
                }

                //fine 2 parte tabella finale
            }
        } catch (DocumentException ex) {
            insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
        }
        return document;
    }

    /**
     *
     * @param path
     * @param d3
     * @param d4
     * @param data1
     * @param data2
     * @param alcolonne
     * @param colonneriepilogo
     * @param filiali
     * @param checkTrans
     * @param checkRiepilogo
     * @param allenabledbr
     * @return
     */
    public String main(String path, String d3, String d4, String data1, String data2, ArrayList<String> alcolonne,ArrayList<String> colonneriepilogo, ArrayList<String> filiali,
            boolean checkTrans, boolean checkRiepilogo, ArrayList<Branch> allenabledbr) {
        try {
            C_ChangeVolumeAffairCashAdvance nctl = new C_ChangeVolumeAffairCashAdvance();

            C_ChangeVolumeAffairCashAdvance_value pdf = new C_ChangeVolumeAffairCashAdvance_value();

            boolean firstTime = true;
            boolean lastTime = false;

            File pdffile = new File(path + generaId(50) + "C_ChangeVolumeAffairCashAdvance.pdf");
            try(OutputStream ou = new FileOutputStream(pdffile)){
            Document document = new Document(A4.rotate(), 20, 20, 20, 20);
            PdfWriter wr = getInstance(document, ou);
            document.open();

            //ciclo per ogni filiale
            int count = filiali.size();
            boolean one = false;
            Db_Master dbm = new Db_Master();
            for (int i = 0; i < count; i++) {

                ArrayList<C_ChangeVolumeAffairCashAdvance_value> dati = dbm.getC_ChangeVolumeAffairCashAdvance(data1, data2, filiali.get(i), allenabledbr);

                pdf.setDataDa(d3);
                pdf.setDataA(d4);
                pdf.setDati(dati);

                if (i == count - 1) {
                    lastTime = true;
                }
                if (!dati.isEmpty()) {
                    document = nctl.receipt(pdf, alcolonne,colonneriepilogo, firstTime, lastTime, document, checkTrans, checkRiepilogo);
                    one = true;
                    firstTime = false;
                }
                

            }
            dbm.closeDB();
            //chiusura documento
            if (one) {
                document.close();
                wr.close();
                ou.close();
                String base64 = new String(encodeBase64(readFileToByteArray(pdffile)));
                pdffile.delete();
                return base64;
            } else {
                document.add(new Paragraph("\n"));
                document.close();
                wr.close();
                ou.close();
                pdffile.delete();
                return null;
            }
        }
        } catch (DocumentException | IOException ex) {
            insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
        }
        return null;

    }
    
    /**
     *
     * @param cmfb
     * @param colonne
     * @param colonneriepilogo
     * @param firstTime
     * @param lastTime
     * @param checkTrans
     * @param checkRiepilogo
     * @param sheet
     * @param cntriga
     * @param style1
     * @param style2
     * @param style3
     * @param style4
     * @param style3left
     * @param style4left
     * @return
     */
    public int receiptexcel(C_ChangeVolumeAffairCashAdvance_value cmfb, ArrayList<String> colonne,ArrayList<String> colonneriepilogo, boolean firstTime, boolean lastTime, boolean checkTrans, boolean checkRiepilogo, HSSFSheet sheet, int cntriga, HSSFCellStyle style1, HSSFCellStyle style2, HSSFCellStyle style3, HSSFCellStyle style4,HSSFCellStyle style3left, HSSFCellStyle style4left) {

        double totalBuy = 0;
        double totalSell = 0;
        double totalcheckAdv = 0;
        double total = 0;
        int nTrans = 0;

        String filiale = "";

        try {

            if (firstTime) {

//                Image img = Image.getInstance(Base64.decodeBase64(Engine.getConf("path.logocl")));
//                img.scalePercent(40.0F);
//                document.add(img);
                cntriga++;

                Row rowP = sheet.createRow((short) cntriga);

                Cell cl = rowP.createCell(1);
                cl.setCellStyle(style1);
                cl.setCellValue("MACCORP ITALIANA S.p.A");

                cntriga++;

                Row rowP1 = sheet.createRow((short) cntriga);

                Cell cl1 = rowP1.createCell(1);
                cl1.setCellStyle(style1);
                cl1.setCellValue(intestazionePdf + " From " + cmfb.getDataDa() + " to " + cmfb.getDataA());

                cntriga++;

            } else {

//                Image img = Image.getInstance(Base64.decodeBase64(Engine.getConf("path.logocl")));
//                img.scalePercent(40.0F);
//                document.add(img);
                Row rowP2 = sheet.createRow((short) cntriga);

                Cell cl = rowP2.createCell(1);
                cl.setCellStyle(style1);
                cl.setCellValue("MACCORP ITALIANA S.p.A");

                cntriga++;

                Row rowP3 = sheet.createRow((short) cntriga);

                Cell cl1 = rowP3.createCell(1);
                cl1.setCellStyle(style1);
                cl1.setCellValue(intestazionePdf + " From " + cmfb.getDataDa() + " to " + cmfb.getDataA());

                cntriga++;

//                document.newPage();
            }

            if (true) {

                ArrayList<C_ChangeVolumeAffairCashAdvance_value> dati = cmfb.getDati();

                double qta = 0, amount = 0;

                PdfPTable table2 = new PdfPTable(colonne.size());
                if (checkTrans) {
                    table2.setWidths(columnWidths2);
                } else {
                    table2.setWidths(columnWidths2False);
                }
                table2.setWidthPercentage(100);

                cntriga++;

                Row row66 = sheet.createRow((short) cntriga);
                //mi scandisco le colonne
                for (int c = 0; c < colonne.size(); c++) {
                    Cell cl7 = row66.createCell(c + 1);
                    cl7.setCellStyle(style3);
                    if(c==0 || c==1){
                    cl7.setCellStyle(style3left);    
                    }
                    cl7.setCellValue(colonne.get(c));
                }

                ArrayList<C_ChangeVolumeAffairCashAdvance_value> lista = cmfb.getDati();

                for (int x = 0; x < lista.size(); x++) {

                    C_ChangeVolumeAffairCashAdvance_value temp = lista.get(x);
                    cntriga++;

                    Row row6 = sheet.createRow((short) cntriga);

                    Cell f1bis = row6.createCell(1);
                    f1bis.setCellStyle(style4left);
                    f1bis.setCellValue(temp.getBranch());

                    Cell f2 = row6.createCell(2);
                    f2.setCellStyle(style4left);
                    f2.setCellValue(temp.getDate());

                    Cell f3 = row6.createCell(3);
                    f3.setCellStyle(style4);
                    f3.setCellValue(temp.getBuy());

                    Cell f4 = row6.createCell(4);
                    f4.setCellStyle(style4);
                    f4.setCellValue(temp.getSell());

                    Cell f5 = row6.createCell(5);
                    f5.setCellStyle(style4);
                    f5.setCellValue(temp.getTotal());
                    
                      Cell f5bis = row6.createCell(6);
                    f5bis.setCellStyle(style4);
                    f5bis.setCellValue(temp.getCashAdv());
                    

                    if (checkTrans) {

                        Cell f6 = row6.createCell(7);
                        f6.setCellStyle(style4);
                        f6.setCellValue(temp.getNoTrans());

                    }

                    filiale = temp.getBranch();

                    totalBuy += ff(temp.getBuy());
                    totalSell += ff(temp.getSell());
                    total += ff(temp.getTotal());
                    nTrans += ff(temp.getNoTrans());
                       totalcheckAdv += ff(temp.getCashAdv());

                }
                
                 totalBuy =roundDouble(totalBuy, 2);
                    totalSell =roundDouble(totalSell, 2);
                    total =roundDouble(total, 2);
                    totalcheckAdv =roundDouble(totalcheckAdv, 2);

                cntriga++;

                Row row6 = sheet.createRow((short) cntriga);

                Cell f1bis = row6.createCell(1);
                f1bis.setCellStyle(style4left);
                f1bis.setCellValue(filiale);

                Cell f2 = row6.createCell(2);
                f2.setCellStyle(style3left);
                f2.setCellValue("Totale");

                Cell f3 = row6.createCell(3);
                f3.setCellStyle(style3);
                f3.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(totalBuy,2)));

                Cell f4 = row6.createCell(4);
                f4.setCellStyle(style3);
                f4.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(totalSell,2)));

                Cell f5 = row6.createCell(5);
                f5.setCellStyle(style3);
                f5.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(total,2)));
                
                 Cell f5bis = row6.createCell(6);
                f5bis.setCellStyle(style3);
                f5bis.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(totalcheckAdv,2)));

                if (checkTrans) {

                    Cell f6 = row6.createCell(7);
                    f6.setCellStyle(style3);
                    f6.setCellValue(formatMysqltoDisplay(valueOf(nTrans)));

                }

                totBuy += totalBuy;
                totSell += totalSell;
                tot += total;
                totNtrans += nTrans;

            }
            
              totBuy =roundDouble(totBuy, 2);
                totSell =roundDouble(totSell, 2);
                tot =roundDouble(tot, 2);

            //tabella finale
            //tabella ultima pagina
            if (checkRiepilogo) {

                //tabella corretta
                //impostazione colonne
                if (true) {

                    cntriga++;
                    cntriga++;
                    cntriga++;

                    Row row8 = sheet.createRow((short) cntriga);

                    //mi scandisco le colonne
                    for (int c = 0; c < colonneriepilogo.size(); c++) {
                        Cell cl7 = row8.createCell(c + 1);
                        cl7.setCellStyle(style3);
                        if(c==0 || c==1){
                        cl7.setCellStyle(style3left);    
                        }
                        cl7.setCellValue(colonneriepilogo.get(c));
                    }

                }
                //fine impostazione colonne

                if (!lastTime) {

                    cntriga++;

                    Row row9 = sheet.createRow((short) cntriga);

                    Cell f1bis = row9.createCell(1);
                    f1bis.setCellStyle(style3left);
                    f1bis.setCellValue(filiale);

                    Cell f2 = row9.createCell(3);
                    f2.setCellStyle(style3);
                    f2.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(totalBuy,2)));

                    Cell f4 = row9.createCell(4);
                    f4.setCellStyle(style3);
                    f4.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(totalSell,2)));

                    Cell f5 = row9.createCell(5);
                    f5.setCellStyle(style3);
                    f5.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(total,2)));
                    
                    Cell f6b = row9.createCell(6);
                    f6b.setCellStyle(style3);
                    f6b.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(totalcheckAdv,2)));

                    if (checkTrans) {

                        Cell f6 = row9.createCell(7);
                        f6.setCellStyle(style3);
                        f6.setCellValue(formatMysqltoDisplay(valueOf(nTrans)));

                    }

                }

                //fine tabella corretta
                //fine 1 parte
                if (lastTime) {

                    cntriga++;

                    Row row10 = sheet.createRow((short) cntriga);

                     Cell f2a = row10.createCell(1);
                    f2a.setCellStyle(style3left);
                    f2a.setCellValue(filiale);
                    
                    Cell f2 = row10.createCell(2);
                    f2.setCellStyle(style3left);
                    f2.setCellValue("Total");

                    Cell f3 = row10.createCell(3);
                    f3.setCellStyle(style3);
                    f3.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(totBuy,2)));

                    Cell f4 = row10.createCell(4);
                    f4.setCellStyle(style3);
                    f4.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(totSell,2)));

                    Cell f5 = row10.createCell(5);
                    f5.setCellStyle(style3);
                    f5.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(tot,2)));
                    
                    Cell f5b = row10.createCell(6);
                    f5b.setCellStyle(style3);
                    f5b.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(totalcheckAdv,2)));

                    if (checkTrans) {

                        Cell f6 = row10.createCell(7);
                        f6.setCellStyle(style3);
                        f6.setCellValue(formatMysqltoDisplay(valueOf(totNtrans)));

                    }
                    //fine totale

                }
                //fine 2 parte tabella finale
            }

            cntriga++;
            cntriga++;
            cntriga++;
            return cntriga;

        } catch (Exception e) {

        }
        return cntriga;

    }

    /**
     *
     * @param path
     * @param d3
     * @param d4
     * @param data1
     * @param data2
     * @param alcolonne
     * @param colonneriepilogo
     * @param filiali
     * @param checkTrans
     * @param checkRiepilogo
     * @param allenabledbr
     * @return
     */
    public String mainexcel(String path, String d3, String d4, String data1, String data2, ArrayList<String> alcolonne,ArrayList<String> colonneriepilogo, ArrayList<String> filiali,
            boolean checkTrans, boolean checkRiepilogo, ArrayList<Branch> allenabledbr) {
        try {
            File pdffile = new File(path + generaId(50) + "ChangeTurnoverCashAdvance.xls");
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet("ChangeTurnoverCashAdvance");
            byte[] bytes = decodeBase64(getConf("path.logocl"));
            int pictureIdx = workbook.addPicture(bytes, PICTURE_TYPE_PNG);
            CreationHelper helper = workbook.getCreationHelper();
            Drawing drawing = sheet.createDrawingPatriarch();
            ClientAnchor anchor = helper.createClientAnchor();
            anchor.setCol1(2); //Column B
            anchor.setRow1(1); //Row 3
            anchor.setCol2(6); //Column C
            anchor.setRow2(4); //Row 4
            Picture pict = drawing.createPicture(anchor, pictureIdx);

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
            style3.setAlignment(RIGHT);
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
            style4.setAlignment(RIGHT);
            style4.setBorderTop(THIN);
            style4.setBorderBottom(THIN);
            
             HSSFCellStyle style4left = (HSSFCellStyle) workbook.createCellStyle();
            style4left.setAlignment(LEFT);
            style4left.setBorderTop(THIN);
            style4left.setBorderBottom(THIN);

            C_ChangeVolumeAffairCashAdvance nctl = new C_ChangeVolumeAffairCashAdvance();
            C_ChangeVolumeAffairCashAdvance_value pdf = new C_ChangeVolumeAffairCashAdvance_value();

            // boolean check=false; //check nTransazioni si/no
//
//        ArrayList<String> filiali = Engine.getC_Branch_ChangeVolumeAffair();
            Db_Master dbm = new Db_Master();
            boolean firstTime = true;
            boolean lastTime = false;
            //ciclo per ogni filiale
            int count = filiali.size();

//            if (checkRiepilogo) {
//                count += 1;
//            }
            boolean one = false;
            int nriga = 5;
            for (int i = 0; i < count; i++) {

                ArrayList<C_ChangeVolumeAffairCashAdvance_value> dati = dbm.getC_ChangeVolumeAffairCashAdvance(data1, data2, filiali.get(i), allenabledbr);
                pdf.setDataDa(d3);
                pdf.setDataA(d4);
                pdf.setDati(dati);
                if (i == count - 1) {
                    lastTime = true;
                }
                if (!dati.isEmpty()) {
                    nriga = nctl.receiptexcel(pdf, alcolonne,colonneriepilogo, firstTime, lastTime, checkTrans, checkRiepilogo, sheet, nriga, style1, style2, style3, style4,style3left, style4left);
                    one = true;
                    firstTime = false;
                }
                

            }

            dbm.closeDB();
            //chiusura documento
            if (one) {
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

                try (FileOutputStream out = new FileOutputStream(pdffile)) {
                    workbook.write(out);
                }
                String base64 = new String(encodeBase64(readFileToByteArray(pdffile)));
                pdffile.delete();
                return base64;
            } else {
   pdffile.delete();
                return null;
            }

        } catch (IOException ex) {
            insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
        }
        return null;

    }

}
