/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rc.so.reportcentrale;

import com.itextpdf.text.BaseColor;
import static com.itextpdf.text.BaseColor.LIGHT_GRAY;
import com.itextpdf.text.Chunk;
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
import rc.so.db.Db_Master;
import rc.so.entity.Branch;

import static rc.so.util.Engine.formatBankBranchReport;
import static rc.so.util.Engine.insertTR;
import static rc.so.util.Utility.fd;
import static rc.so.util.Utility.ff;
import static rc.so.util.Utility.formatMysqltoDisplay;
import static rc.so.util.Utility.generaId;
import static rc.so.util.Utility.roundDouble;
import static rc.so.util.Utility.roundDoubleandFormat;
import java.awt.Color;
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
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import static org.apache.poi.ss.usermodel.BorderStyle.MEDIUM;
import static org.apache.poi.ss.usermodel.BorderStyle.THIN;
import static org.apache.poi.ss.usermodel.HorizontalAlignment.CENTER;
import static org.apache.poi.ss.usermodel.HorizontalAlignment.LEFT;
import static org.apache.poi.ss.usermodel.HorizontalAlignment.RIGHT;
import static org.apache.poi.ss.util.RegionUtil.setBorderBottom;
import static org.apache.poi.ss.util.RegionUtil.setBorderLeft;
import static org.apache.poi.ss.util.RegionUtil.setBorderRight;
import static org.apache.poi.ss.util.RegionUtil.setBorderTop;
/**
 *
 * @author fplacanica
 */
public class C_FreeTax_BranchCausal {

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
    public static float[] columnWidths2 = new float[]{40f, 15f, 10f, 10f, 10f, 10f, 10f, 10f, 10f, 10f,10f,10f,10f,10f};

    /**
     *
     */
    public static float[] columnWidths2int = new float[]{55f, 40f, 40f, 40f};

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
    final String intestazionePdf = "VAT Refund - Branch & Causal";
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

    double totaleVolPrevYear = 0;
    double totaleVolYear = 0;
    double totaleVolVal = 0;
    double totaleVolPerc = 0;
    double totaleqtyPrevYear = 0;
    double totaleqtyYear = 0;
    double totaleqtyVal = 0;
    double totaleqtyPerc = 0;

    double GPtotaleVolPrevYear = 0;
    double GPtotaleVolYear = 0;
    double GPtotaleVolVal = 0;
    double GPtotaleVolPerc = 0;
  

    double totaleEUROtotVolPrevYear = 0;
    double totaleEUROtotVolYear = 0;
    double totaleEUROtotVolVal = 0;
    double totaleEUROtotVolPerc = 0;
    double totaleEUROqtyPrevYear = 0;
    double totaleEUROqtyYear = 0;
    double totaleEUROqtyVal = 0;
    double totaleEUROqtyPerc = 0;

    double totaleVALUTAtotVolPrevYear = 0.00;
    double totaleVALUTAtotVolYear = 0.00;
    double totaleVALUTAtotVolVal = 0.00;
    double totaleVALUTAtotVolPerc = 0.00;
    double totaleVALUTAqtyPrevYear = 0.00;
    double totaleVALUTAqtyYear = 0.00;
    double totaleVALUTAqtyVal = 0.00;
    double totaleVALUTAqtyPerc = 0.00;

    double GPtotaleVALUTAtotVolPrevYear = 0.00;
    double GPtotaleVALUTAtotVolYear = 0.00;
    double GPtotaleVALUTAtotVolVal = 0.00;
    double GPtotaleVALUTAtotVolPerc = 0.00;
   
    /**
     ** Constructor
     */
    public C_FreeTax_BranchCausal() {

        this.f0_bold = getFont(HELVETICA, WINANSI, 14.04f, BOLD);
        this.f1_bold = getFont(HELVETICA, WINANSI, 9.96f, BOLD);
        this.f2_bold = getFont(HELVETICA, WINANSI, 8f, BOLD);
        this.f3_bold = getFont(HELVETICA, WINANSI, 6f, BOLDITALIC);
        this.f4_bold = getFont(HELVETICA, WINANSI, 5f, BOLD);
        this.f1_normal = getFont(HELVETICA, WINANSI, 9.96f, NORMAL);
        this.f2_normal = getFont(HELVETICA, WINANSI, 8f, NORMAL);
        this.f3_normal = getFont(HELVETICA, WINANSI, 5f, NORMAL);

    }

    /**
     *
     * @param cmfb
     * @param colonne
     * @param firstTime
     * @param lastTime
     * @param document
     * @return
     */
    public Document receipt(C_FreeTax_BranchCausal_value cmfb, ArrayList<String> colonne, boolean firstTime, boolean lastTime, Document document) {

        try {

            if (firstTime) {
                PdfPTable table = new PdfPTable(2);
                table.setWidths(columnWidths0);
                table.setWidthPercentage(100);
                Phrase phrase1 = new Phrase();
                phrase1.add(new Chunk(intestazionePdf + " From " + cmfb.getDataDa() + " to " + cmfb.getDataA(), f3_bold));
                PdfPCell cell1 = new PdfPCell(phrase1);
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

            }

            ArrayList<C_FreeTax_BranchCausal_value> dati = cmfb.getDati();

            PdfPCell cell;
            Phrase phraset;
            PdfPCell cellt;
            PdfPTable table3;

            double totVolPrevYear = 0;
            double totVolYear = 0;
            double totVolVal = 0;
            double totVolPerc = 0;
            double qtyPrevYear = 0;
            double qtyYear = 0;
            double qtyVal = 0;
            double qtyPerc = 0;
            
           

            double EUROtotVolPrevYear = 0;
            double EUROtotVolYear = 0;
            double EUROtotVolVal = 0;
            double EUROtotVolPerc = 0;
            double EUROqtyPrevYear = 0;
            double EUROqtyYear = 0;
            double EUROqtyVal = 0;
            double EUROqtyPerc = 0;

            double VALUTAtotVolPrevYear = 0;
            double VALUTAtotVolYear = 0;
            double VALUTAtotVolVal = 0;
            double VALUTAtotVolPerc = 0;
            double VALUTAqtyPrevYear = 0;
            double VALUTAqtyYear = 0;
            double VALUTAqtyVal = 0;
            double VALUTAqtyPerc = 0;

            double GPtotVolPrevYear = 0;
            double GPtotVolYear = 0;
            double GPtotVolVal = 0;
            double GPtotVolPerc = 0;
           

            double GPVALUTAtotVolPrevYear = 0;
            double GPVALUTAtotVolYear = 0;
            double GPVALUTAtotVolVal = 0;
            double GPVALUTAtotVolPerc = 0;
            

            boolean ft = true;

            if (ft) {

                //intestazione Volume - Quantità
                LineSeparator sep = new LineSeparator();
                sep.setOffset(-2);
                sep.setLineWidth((float) 0.5);

                PdfPTable table4 = new PdfPTable(4);
                table4.setWidths(columnWidths2int);
                table4.setWidthPercentage(100);

                phraset = new Phrase();
                phraset.add(new Chunk(cmfb.getId_filiale()+ " "+ cmfb.getDe_filiale(), f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_LEFT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk("Amount", f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_CENTER);
                cellt.setBorder(BOX);
                cellt.setBackgroundColor(new BaseColor(242, 242, 242));
                table4.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk("Forms Quantity", f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_CENTER);
                cellt.setBackgroundColor(new BaseColor(242, 242, 242));
                cellt.setBorder(BOX);
                table4.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk("Gross Profit", f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_CENTER);
                cellt.setBackgroundColor(new BaseColor(242, 242, 242));
                cellt.setBorder(BOX);
                table4.addCell(cellt);

                document.add(table4);

                //fine intestazione Volume - Quantità
                sep = new LineSeparator();
                sep.setOffset(-2);
                sep.setLineWidth((float) 0.5);

                PdfPTable table2 = new PdfPTable(colonne.size());
                table2.setWidths(columnWidths2);
                table2.setWidthPercentage(100);

                PdfPCell[] list = new PdfPCell[colonne.size()];
                //mi scandisco le colonne
                for (int c = 0; c < colonne.size(); c++) {
                    Phrase phraset1 = new Phrase();
                    phraset1.add(new Chunk(colonne.get(c), f4_bold));
                    PdfPCell cellt1 = new PdfPCell(phraset1);
                    cellt1.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt1.setBorder(BOTTOM | TOP);
                    cellt1.setFixedHeight(10f);
                    cellt1.setBackgroundColor(LIGHT_GRAY);
                    cellt1.setBorderWidth(0.7f);

                    list[c] = cellt1;
                }

                table3 = new PdfPTable(colonne.size());
                table3.setWidths(columnWidths2);
                table3.setWidthPercentage(100);

                for (PdfPCell list1 : list) {
                    PdfPCell temp = (PdfPCell) (list1);
                    table3.addCell(temp);
                }

                document.add(table3);
            }

            for (int j = 0; j < dati.size(); j++) {

                

                C_FreeTax_BranchCausal_value temp =  dati.get(j);

                //   listaTot.add(temp);
                LineSeparator sep = new LineSeparator();
                sep.setOffset(-2);
                sep.setLineWidth((float) 0.5);

                PdfPTable table4 = new PdfPTable(colonne.size());
                table4.setWidths(columnWidths2);
                table4.setWidthPercentage(100);

                phraset = new Phrase();
                phraset.add(new Chunk(temp.getCausal(), f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_LEFT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);

                if (temp.isValuta()) {
                    phraset = new Phrase();
                    phraset.add(new Chunk("Currency", f3_normal));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setBorder(BOTTOM);
                    table4.addCell(cellt);
                } else {
                    phraset = new Phrase();
                    phraset.add(new Chunk("Local Currency", f3_normal));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setBorder(BOTTOM);
                    table4.addCell(cellt);
                }

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(temp.getVolPrevYear()), f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(temp.getVolYear()), f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(temp.getVolVal()), f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(temp.getVolPerc()), f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(temp.getQtyPrevYear()), f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(temp.getQtyYear()), f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(temp.getQtyVal()), f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(temp.getQtyPerc()), f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);
                
                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(temp.getVolGMPrevYear()), f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);
                
                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(temp.getVolGMYear()), f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);
                
                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(temp.getVolGMVal()), f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);
                
                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(temp.getVolGMPerc()), f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);

                document.add(table4);

                totVolPrevYear += fd(temp.getVolPrevYear());
                totVolYear += fd(temp.getVolYear());
                totVolVal += fd(temp.getVolVal());
                totVolPerc += fd(temp.getVolPerc());
                qtyPrevYear += fd(temp.getQtyPrevYear());
                qtyYear += fd(temp.getQtyYear());
                qtyVal += fd(temp.getQtyVal());
                qtyPerc += fd(temp.getQtyPerc());
                
                
                   GPtotVolPrevYear += fd(temp.getVolGMPrevYear());
                GPtotVolYear += fd(temp.getVolGMYear());
                GPtotVolVal += fd(temp.getVolGMVal());
                GPtotVolPerc +=fd(temp.getVolGMPerc());
                

                if (temp.isValuta()) {
                    VALUTAtotVolPrevYear +=fd(temp.getVolPrevYear());
                    VALUTAtotVolYear += fd(temp.getVolYear());
                    VALUTAtotVolVal += fd(temp.getVolVal());
                    VALUTAtotVolPerc += fd(temp.getVolPerc());
                    VALUTAqtyPrevYear += fd(temp.getQtyPrevYear());
                    VALUTAqtyYear += fd(temp.getQtyYear());
                    VALUTAqtyVal += fd(temp.getQtyVal());
                    VALUTAqtyPerc += fd(temp.getQtyPerc());
                    
                     GPVALUTAtotVolPrevYear += fd(temp.getVolGMPrevYear());
                    GPVALUTAtotVolYear += fd(temp.getVolGMYear());
                    GPVALUTAtotVolVal += fd(temp.getVolGMVal());
                    GPVALUTAtotVolPerc += fd(temp.getVolGMPerc());
                    
                } else {
                    EUROtotVolPrevYear += fd(temp.getVolPrevYear());
                    EUROtotVolYear += fd(temp.getVolYear());
                    EUROtotVolVal += fd(temp.getVolVal());
                    EUROtotVolPerc += fd(temp.getVolPerc());
                    EUROqtyPrevYear += fd(temp.getQtyPrevYear());
                    EUROqtyYear += fd(temp.getQtyYear());
                    EUROqtyVal += fd(temp.getQtyVal());
                    EUROqtyPerc += fd(temp.getQtyPerc());
                }

                //totale generale                
                totaleVolPrevYear += fd(temp.getVolPrevYear());
                totaleVolYear += fd(temp.getVolYear());
                totaleVolVal += fd(temp.getVolVal());
                totaleVolPerc += fd(temp.getVolPerc());
                totaleqtyPrevYear += fd(temp.getQtyPrevYear());
                totaleqtyYear += fd(temp.getQtyYear());
                totaleqtyVal += fd(temp.getQtyVal());
                totaleqtyPerc += fd(temp.getQtyPerc());
                
                
                 GPtotaleVolPrevYear += fd(temp.getVolGMPrevYear());
                GPtotaleVolYear += fd(temp.getVolGMYear());
                GPtotaleVolVal += fd(temp.getVolGMVal());
                GPtotaleVolPerc += fd(temp.getVolGMPerc());
                

                if (temp.isValuta()) {
                    totaleVALUTAtotVolPrevYear += fd(temp.getVolPrevYear());
                    totaleVALUTAtotVolYear += fd(temp.getVolYear());
                    totaleVALUTAtotVolVal += fd(temp.getVolVal());
                    totaleVALUTAtotVolPerc += fd(temp.getVolPerc());
                    totaleVALUTAqtyPrevYear += fd(temp.getQtyPrevYear());
                    totaleVALUTAqtyYear += fd(temp.getQtyYear());
                    totaleVALUTAqtyVal += fd(temp.getQtyVal());
                    totaleVALUTAqtyPerc += fd(temp.getQtyPerc());
                    
                    
                      GPtotaleVALUTAtotVolPrevYear += fd(temp.getVolGMPrevYear());
                    GPtotaleVALUTAtotVolYear += fd(temp.getVolGMYear());
                    GPtotaleVALUTAtotVolVal += fd(temp.getVolGMVal());
                    GPtotaleVALUTAtotVolPerc += fd(temp.getVolGMPerc());
                    
                } else {
                    totaleEUROtotVolPrevYear += fd(temp.getVolPrevYear());
                    totaleEUROtotVolYear += fd(temp.getVolYear());
                    totaleEUROtotVolVal += fd(temp.getVolVal());
                    totaleEUROtotVolPerc += fd(temp.getVolPerc());
                    totaleEUROqtyPrevYear += fd(temp.getQtyPrevYear());
                    totaleEUROqtyYear += fd(temp.getQtyYear());
                    totaleEUROqtyVal += fd(temp.getQtyVal());
                    totaleEUROqtyPerc += fd(temp.getQtyPerc());
                }

            }

            totVolPrevYear = roundDouble(totVolPrevYear, 2);
            totVolYear = roundDouble(totVolYear, 2);
            totVolVal = roundDouble(totVolVal, 2);
            qtyPrevYear = roundDouble(qtyPrevYear, 2);
            qtyYear = roundDouble(qtyYear, 2);
            qtyVal = roundDouble(qtyVal, 2);
            
            
            GPtotVolPrevYear = roundDouble(GPtotVolPrevYear, 2);
            GPtotVolYear = roundDouble(GPtotVolYear, 2);
            GPtotVolVal = roundDouble(GPtotVolVal, 2);
            GPtotVolPerc = roundDouble(GPtotVolPerc, 2);
            

            VALUTAtotVolPrevYear = roundDouble(VALUTAtotVolPrevYear, 2);
            VALUTAtotVolYear = roundDouble(VALUTAtotVolYear, 2);
            VALUTAtotVolVal = roundDouble(VALUTAtotVolVal, 2);
            VALUTAqtyPrevYear = roundDouble(VALUTAqtyPrevYear, 2);
            VALUTAqtyYear = roundDouble(VALUTAqtyYear, 2);
            VALUTAqtyVal = roundDouble(VALUTAqtyVal, 2);
            
            
            GPVALUTAtotVolPrevYear = roundDouble(GPVALUTAtotVolPrevYear, 2);
            GPVALUTAtotVolYear = roundDouble(GPVALUTAtotVolYear, 2);
            GPVALUTAtotVolVal = roundDouble(GPVALUTAtotVolVal, 2);
            GPVALUTAtotVolPerc = roundDouble(GPVALUTAtotVolPerc, 2);
            

            EUROtotVolPrevYear = roundDouble(EUROtotVolPrevYear, 2);
            EUROtotVolYear = roundDouble(EUROtotVolYear, 2);
            EUROtotVolVal = roundDouble(EUROtotVolVal, 2);
            EUROqtyPrevYear = roundDouble(EUROqtyPrevYear, 2);
            EUROqtyYear = roundDouble(EUROqtyYear, 2);
            EUROqtyVal = roundDouble(EUROqtyVal, 2);

            totaleVolPrevYear = roundDouble(totaleVolPrevYear, 2);
            totaleVolYear = roundDouble(totaleVolYear, 2);
            totaleVolVal = roundDouble(totaleVolVal, 2);
            totaleVolPerc = roundDouble(totaleVolPerc, 2);
            totaleqtyPrevYear = roundDouble(totaleqtyPrevYear, 2);
            totaleqtyYear = roundDouble(totaleqtyYear, 2);
            totaleqtyVal = roundDouble(totaleqtyVal, 2);
            totaleqtyPerc = roundDouble(totaleqtyPerc, 2);
            
            
               GPtotaleVolPrevYear = roundDouble(GPtotaleVolPrevYear, 2);
            GPtotaleVolYear = roundDouble(GPtotaleVolYear, 2);
            GPtotaleVolVal = roundDouble(GPtotaleVolVal, 2);
            GPtotaleVolPerc = roundDouble(GPtotaleVolPerc, 2);

            totaleVALUTAtotVolPrevYear = roundDouble(totaleVALUTAtotVolPrevYear, 2);
            totaleVALUTAtotVolYear = roundDouble(totaleVALUTAtotVolYear, 2);
            totaleVALUTAtotVolVal = roundDouble(totaleVALUTAtotVolVal, 2);
            totaleVALUTAtotVolPerc = roundDouble(totaleVALUTAtotVolPerc, 2);
            totaleVALUTAqtyPrevYear = roundDouble(totaleVALUTAqtyPrevYear, 0);
            totaleVALUTAqtyYear = roundDouble(totaleVALUTAqtyYear, 0);
            totaleVALUTAqtyVal = roundDouble(totaleVALUTAqtyVal, 0);
            totaleVALUTAqtyPerc = roundDouble(totaleVALUTAqtyPerc, 2);
            
            
             GPtotaleVALUTAtotVolPrevYear = roundDouble(GPtotaleVALUTAtotVolPrevYear, 2);
            GPtotaleVALUTAtotVolYear = roundDouble(GPtotaleVALUTAtotVolYear, 2);
            GPtotaleVALUTAtotVolVal = roundDouble(GPtotaleVALUTAtotVolVal, 2);
            GPtotaleVALUTAtotVolPerc = roundDouble(GPtotaleVALUTAtotVolPerc, 2);
            

            totaleEUROtotVolPrevYear = roundDouble(totaleEUROtotVolPrevYear, 2);
            totaleEUROtotVolYear = roundDouble(totaleEUROtotVolYear, 2);
            totaleEUROtotVolVal = roundDouble(totaleEUROtotVolVal, 2);
            totaleEUROtotVolPerc = roundDouble(totaleEUROtotVolPerc, 2);
            totaleEUROqtyPrevYear = roundDouble(totaleEUROqtyPrevYear, 0);
            totaleEUROqtyYear = roundDouble(totaleEUROqtyYear, 0);
            totaleEUROqtyVal = roundDouble(totaleEUROqtyVal, 0);
            totaleEUROqtyPerc = roundDouble(totaleEUROqtyPerc, 2);

            //total EURO
            PdfPTable table4 = new PdfPTable(colonne.size());
            table4.setWidths(columnWidths2);
            table4.setWidthPercentage(100);

            phraset = new Phrase();
            phraset.add(new Chunk("", f4_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setBorder(NO_BORDER);
            table4.addCell(cellt);

            phraset = new Phrase();
            phraset.add(new Chunk("Total Local Currency Amount", f4_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setBorder(NO_BORDER);
            cellt.setBackgroundColor(new BaseColor(242, 242, 242));
            table4.addCell(cellt);

            phraset = new Phrase();
            phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(EUROtotVolPrevYear, 2)) + "", f4_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setBorder(NO_BORDER);
            cellt.setBackgroundColor(new BaseColor(242, 242, 242));
            table4.addCell(cellt);

            phraset = new Phrase();
            phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(EUROtotVolYear, 2)) + "", f4_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setBorder(NO_BORDER);
            cellt.setBackgroundColor(new BaseColor(242, 242, 242));
            table4.addCell(cellt);

            phraset = new Phrase();
            phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(EUROtotVolVal, 2)) + "", f4_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setBorder(NO_BORDER);
            cellt.setBackgroundColor(new BaseColor(242, 242, 242));
            table4.addCell(cellt);

            phraset = new Phrase();
            if (EUROtotVolPrevYear > 0 && EUROtotVolYear > 0) {
                phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat((EUROtotVolVal / EUROtotVolPrevYear) * 100, 2)) + "", f4_bold));
            } else {
                phraset.add(new Chunk("0,00", f4_bold));
            }
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setBorder(NO_BORDER);
            cellt.setBackgroundColor(new BaseColor(242, 242, 242));
            table4.addCell(cellt);

            phraset = new Phrase();
            phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(EUROqtyPrevYear, 0)) + "", f4_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setBorder(NO_BORDER);
            cellt.setBackgroundColor(new BaseColor(242, 242, 242));
            table4.addCell(cellt);

            phraset = new Phrase();
            phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(EUROqtyYear, 0)) + "", f4_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setBorder(NO_BORDER);
            cellt.setBackgroundColor(new BaseColor(242, 242, 242));
            table4.addCell(cellt);

            phraset = new Phrase();
            phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(EUROqtyVal, 0)) + "", f4_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setBorder(NO_BORDER);
            cellt.setBackgroundColor(new BaseColor(242, 242, 242));
            table4.addCell(cellt);

            phraset = new Phrase();
            if (EUROqtyPrevYear > 0 && EUROqtyYear > 0) {
                phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat((EUROqtyVal / EUROqtyPrevYear) * 100, 2)) + "", f4_bold));
            } else {
                phraset.add(new Chunk("0,00", f4_bold));
            }
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setBorder(NO_BORDER);
            cellt.setBackgroundColor(new BaseColor(242, 242, 242));
            table4.addCell(cellt);
            
            
             phraset = new Phrase();
            phraset.add(new Chunk(formatMysqltoDisplay("0.00"), f4_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setBorder(NO_BORDER);
            cellt.setBackgroundColor(new BaseColor(242, 242, 242));
            table4.addCell(cellt);

            
            phraset = new Phrase();
            phraset.add(new Chunk(formatMysqltoDisplay("0.00"), f4_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setBorder(NO_BORDER);
            cellt.setBackgroundColor(new BaseColor(242, 242, 242));
            table4.addCell(cellt);

            
            phraset = new Phrase();
            phraset.add(new Chunk(formatMysqltoDisplay("0.00"), f4_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setBorder(NO_BORDER);
            cellt.setBackgroundColor(new BaseColor(242, 242, 242));
            table4.addCell(cellt);

            
            phraset = new Phrase();
            phraset.add(new Chunk(formatMysqltoDisplay("0.00"), f4_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setBorder(NO_BORDER);
            cellt.setBackgroundColor(new BaseColor(242, 242, 242));
            table4.addCell(cellt);

            
            
            

            document.add(table4);

            //fine total
            //total EURO perc
            table4 = new PdfPTable(colonne.size());
            table4.setWidths(columnWidths2);
            table4.setWidthPercentage(100);

            phraset = new Phrase();
            phraset.add(new Chunk("", f4_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setBorder(NO_BORDER);
            table4.addCell(cellt);

            phraset = new Phrase();
            phraset.add(new Chunk("Total Local Currency %", f4_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setBorder(NO_BORDER);
            cellt.setBackgroundColor(new BaseColor(230, 230, 240));
            table4.addCell(cellt);

            phraset = new Phrase();
            if (totVolPrevYear != 0) {
                phraset.add(new Chunk((formatMysqltoDisplay(roundDoubleandFormat((100 * EUROtotVolPrevYear) / totVolPrevYear, 2))) + "", f4_bold));
            } else {
                phraset.add(new Chunk("0,00", f4_bold));
            }
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setBorder(NO_BORDER);
            cellt.setBackgroundColor(new BaseColor(230, 230, 240));
            table4.addCell(cellt);

            phraset = new Phrase();
            if (totVolYear != 0) {
                phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(((100 * EUROtotVolYear) / totVolYear), 2)) + "", f4_bold));
            } else {
                phraset.add(new Chunk("0,00", f4_bold));
            }

            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setBorder(NO_BORDER);
            cellt.setBackgroundColor(new BaseColor(230, 230, 240));
            table4.addCell(cellt);

            phraset = new Phrase();
            phraset.add(new Chunk("", f4_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setBorder(NO_BORDER);
            cellt.setBackgroundColor(new BaseColor(230, 230, 240));
            table4.addCell(cellt);

            phraset = new Phrase();
            phraset.add(new Chunk("", f4_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setBorder(NO_BORDER);
            cellt.setBackgroundColor(new BaseColor(230, 230, 240));
            table4.addCell(cellt);

            phraset = new Phrase();
            if (qtyPrevYear != 0) {
                phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(((100 * EUROqtyPrevYear) / qtyPrevYear), 2)) + "", f4_bold));
            } else {
                phraset.add(new Chunk("0,00", f4_bold));
            }

            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setBorder(NO_BORDER);
            cellt.setBackgroundColor(new BaseColor(230, 230, 240));
            table4.addCell(cellt);

            phraset = new Phrase();
            if (qtyYear != 0) {
                phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(((100 * EUROqtyYear) / qtyYear), 2)) + "", f4_bold));
            } else {
                phraset.add(new Chunk("0,00", f4_bold));
            }
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setBorder(NO_BORDER);
            cellt.setBackgroundColor(new BaseColor(230, 230, 240));
            table4.addCell(cellt);

            phraset = new Phrase();
            phraset.add(new Chunk("", f4_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setBorder(NO_BORDER);
            cellt.setBackgroundColor(new BaseColor(230, 230, 240));
            table4.addCell(cellt);

            phraset = new Phrase();
            phraset.add(new Chunk("", f4_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setBorder(NO_BORDER);
            cellt.setBackgroundColor(new BaseColor(230, 230, 240));
            table4.addCell(cellt);
            
            phraset = new Phrase();
            phraset.add(new Chunk(formatMysqltoDisplay("0.00"), f4_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setBorder(NO_BORDER);
            cellt.setBackgroundColor(new BaseColor(242, 242, 242));
            table4.addCell(cellt);

            
            phraset = new Phrase();
            phraset.add(new Chunk(formatMysqltoDisplay("0.00"), f4_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setBorder(NO_BORDER);
            cellt.setBackgroundColor(new BaseColor(242, 242, 242));
            table4.addCell(cellt);

            
            phraset = new Phrase();
            phraset.add(new Chunk(formatMysqltoDisplay("0.00"), f4_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setBorder(NO_BORDER);
            cellt.setBackgroundColor(new BaseColor(242, 242, 242));
            table4.addCell(cellt);

            
            phraset = new Phrase();
            phraset.add(new Chunk(formatMysqltoDisplay("0.00"), f4_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setBorder(NO_BORDER);
            cellt.setBackgroundColor(new BaseColor(242, 242, 242));
            table4.addCell(cellt);


            document.add(table4);

            //fine total perc
            //total VALUTA
            table4 = new PdfPTable(colonne.size());
            table4.setWidths(columnWidths2);
            table4.setWidthPercentage(100);

            phraset = new Phrase();
            phraset.add(new Chunk("", f4_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setBorder(NO_BORDER);
            table4.addCell(cellt);

            phraset = new Phrase();
            phraset.add(new Chunk("Total Currency Amount", f4_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setBorder(NO_BORDER);
            cellt.setBackgroundColor(new BaseColor(242, 242, 242));
            table4.addCell(cellt);

            phraset = new Phrase();
            phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(VALUTAtotVolPrevYear, 2)) + "", f4_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setBorder(NO_BORDER);
            cellt.setBackgroundColor(new BaseColor(242, 242, 242));
            table4.addCell(cellt);

            phraset = new Phrase();
            phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(VALUTAtotVolYear, 2)) + "", f4_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setBorder(NO_BORDER);
            cellt.setBackgroundColor(new BaseColor(242, 242, 242));
            table4.addCell(cellt);

            phraset = new Phrase();
            phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(VALUTAtotVolVal, 2)) + "", f4_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setBorder(NO_BORDER);
            cellt.setBackgroundColor(new BaseColor(242, 242, 242));
            table4.addCell(cellt);

            phraset = new Phrase();
            if (VALUTAtotVolPrevYear > 0 && VALUTAtotVolYear > 0) {
                phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat((VALUTAtotVolVal / VALUTAtotVolPrevYear) * 100, 2)) + "", f4_bold));
            } else {
                phraset.add(new Chunk("0,00", f4_bold));
            }
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setBorder(NO_BORDER);
            cellt.setBackgroundColor(new BaseColor(242, 242, 242));
            table4.addCell(cellt);

            phraset = new Phrase();
            phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(VALUTAqtyPrevYear, 0)) + "", f4_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setBorder(NO_BORDER);
            cellt.setBackgroundColor(new BaseColor(242, 242, 242));
            table4.addCell(cellt);

            phraset = new Phrase();
            phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(VALUTAqtyYear, 0)) + "", f4_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setBorder(NO_BORDER);
            cellt.setBackgroundColor(new BaseColor(242, 242, 242));
            table4.addCell(cellt);

            phraset = new Phrase();
            phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(VALUTAqtyVal, 0)) + "", f4_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setBorder(NO_BORDER);
            cellt.setBackgroundColor(new BaseColor(242, 242, 242));
            table4.addCell(cellt);

            phraset = new Phrase();
            if (VALUTAqtyPrevYear > 0 && VALUTAqtyYear > 0) {
                phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat((VALUTAqtyVal / VALUTAqtyPrevYear) * 100, 2)) + "", f4_bold));
            } else {
                phraset.add(new Chunk("0,00", f4_bold));
            }
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setBorder(NO_BORDER);
            cellt.setBackgroundColor(new BaseColor(242, 242, 242));
            table4.addCell(cellt);
            
            
            phraset = new Phrase();
            phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(GPVALUTAtotVolPrevYear, 2)) + "", f4_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setBorder(NO_BORDER);
            cellt.setBackgroundColor(new BaseColor(242, 242, 242));
            table4.addCell(cellt);
            
             phraset = new Phrase();
            phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(GPVALUTAtotVolYear, 2)) + "", f4_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setBorder(NO_BORDER);
            cellt.setBackgroundColor(new BaseColor(242, 242, 242));
            table4.addCell(cellt);
            
             phraset = new Phrase();
            phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(GPVALUTAtotVolVal, 2)) + "", f4_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setBorder(NO_BORDER);
            cellt.setBackgroundColor(new BaseColor(242, 242, 242));
            table4.addCell(cellt);
            
             phraset = new Phrase();
            phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(GPVALUTAtotVolPerc, 2)) + "", f4_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setBorder(NO_BORDER);
            cellt.setBackgroundColor(new BaseColor(242, 242, 242));
            table4.addCell(cellt);

            document.add(table4);

            //fine total
            //total VALUTA perc
            table4 = new PdfPTable(colonne.size());
            table4.setWidths(columnWidths2);
            table4.setWidthPercentage(100);

            phraset = new Phrase();
            phraset.add(new Chunk("", f4_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setBorder(NO_BORDER);
            table4.addCell(cellt);

            phraset = new Phrase();
            phraset.add(new Chunk("Total Currency %", f4_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setBorder(NO_BORDER);
            cellt.setBackgroundColor(new BaseColor(230, 230, 240));
            table4.addCell(cellt);

            phraset = new Phrase();
            if (totVolPrevYear != 0) {
                phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(((100 * VALUTAtotVolPrevYear) / totVolPrevYear), 2)) + "", f4_bold));
            } else {
                phraset.add(new Chunk("0,00", f4_bold));
            }
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setBorder(NO_BORDER);
            cellt.setBackgroundColor(new BaseColor(230, 230, 240));
            table4.addCell(cellt);

            phraset = new Phrase();
            if (totVolYear != 0) {
                phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(((100 * VALUTAtotVolYear) / totVolYear), 2)) + "", f4_bold));
            } else {
                phraset.add(new Chunk("0,00", f4_bold));
            }
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setBorder(NO_BORDER);
            cellt.setBackgroundColor(new BaseColor(230, 230, 240));
            table4.addCell(cellt);

            phraset = new Phrase();
            phraset.add(new Chunk("", f4_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setBorder(NO_BORDER);
            cellt.setBackgroundColor(new BaseColor(230, 230, 240));
            table4.addCell(cellt);

            phraset = new Phrase();
            phraset.add(new Chunk("", f4_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setBorder(NO_BORDER);
            cellt.setBackgroundColor(new BaseColor(230, 230, 240));
            table4.addCell(cellt);

            phraset = new Phrase();
            if (qtyPrevYear != 0) {
                phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(((100 * VALUTAqtyPrevYear) / qtyPrevYear), 2)) + "", f4_bold));
            } else {
                phraset.add(new Chunk("0,00", f4_bold));
            }
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setBorder(NO_BORDER);
            cellt.setBackgroundColor(new BaseColor(230, 230, 240));
            table4.addCell(cellt);

            phraset = new Phrase();
            if (qtyYear != 0) {
                phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(((100 * VALUTAqtyYear) / qtyYear), 2)) + "", f4_bold));
            } else {
                phraset.add(new Chunk("0,00", f4_bold));
            }
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setBorder(NO_BORDER);
            cellt.setBackgroundColor(new BaseColor(230, 230, 240));
            table4.addCell(cellt);

            phraset = new Phrase();
            phraset.add(new Chunk("", f4_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setBorder(NO_BORDER);
            cellt.setBackgroundColor(new BaseColor(230, 230, 240));
            table4.addCell(cellt);

            phraset = new Phrase();
            phraset.add(new Chunk("", f4_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setBorder(NO_BORDER);
            cellt.setBackgroundColor(new BaseColor(230, 230, 240));
            table4.addCell(cellt);
            
            
             phraset = new Phrase();
            if (GPtotVolPrevYear != 0) {
                phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(((100 * GPVALUTAtotVolPrevYear) / GPtotVolPrevYear), 2)) + "", f4_bold));
            } else {
                phraset.add(new Chunk("0,00", f4_bold));
            }
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setBorder(NO_BORDER);
            cellt.setBackgroundColor(new BaseColor(230, 230, 240));
            table4.addCell(cellt);
            
            
             phraset = new Phrase();
            if (GPtotVolYear != 0) {
                phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(((100 * GPVALUTAtotVolYear) / GPtotVolYear), 2)) + "", f4_bold));
            } else {
                phraset.add(new Chunk("0,00", f4_bold));
            }
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setBorder(NO_BORDER);
            cellt.setBackgroundColor(new BaseColor(230, 230, 240));
            table4.addCell(cellt);
            
             phraset = new Phrase();
            if (GPtotVolVal != 0) {
                phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(((100 * GPVALUTAtotVolVal) / GPtotVolVal), 2)) + "", f4_bold));
            } else {
                phraset.add(new Chunk("0,00", f4_bold));
            }
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setBorder(NO_BORDER);
            cellt.setBackgroundColor(new BaseColor(230, 230, 240));
            table4.addCell(cellt);
            
             phraset = new Phrase();
            if (GPtotVolPerc != 0) {
                phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(((100 * GPVALUTAtotVolPerc) / GPtotVolPerc), 2)) + "", f4_bold));
            } else {
                phraset.add(new Chunk("0,00", f4_bold));
            }
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setBorder(NO_BORDER);
            cellt.setBackgroundColor(new BaseColor(230, 230, 240));
            table4.addCell(cellt);
            
            

            document.add(table4);

            //fine total perc
            //total
            table4 = new PdfPTable(colonne.size());
            table4.setWidths(columnWidths2);
            table4.setWidthPercentage(100);

            phraset = new Phrase();
            phraset.add(new Chunk("", f4_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setBorder(BOTTOM);
            table4.addCell(cellt);

            phraset = new Phrase();
            phraset.add(new Chunk("Grand Total", f4_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setBackgroundColor(new BaseColor(242, 242, 242));
            cellt.setBorder(BOTTOM);
            table4.addCell(cellt);

            phraset = new Phrase();
            phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(totVolPrevYear, 2)) + "", f4_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setBackgroundColor(new BaseColor(242, 242, 242));
            cellt.setBorder(BOTTOM);
            table4.addCell(cellt);

            phraset = new Phrase();
            phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(totVolYear, 2)) + "", f4_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setBackgroundColor(new BaseColor(242, 242, 242));
            cellt.setBorder(BOTTOM);
            table4.addCell(cellt);

            phraset = new Phrase();
            phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(totVolVal, 2)) + "", f4_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setBackgroundColor(new BaseColor(242, 242, 242));
            cellt.setBorder(BOTTOM);
            table4.addCell(cellt);

            phraset = new Phrase();
            if (totVolPrevYear > 0 && totVolYear > 0) {
                phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat((totVolVal / totVolPrevYear) * 100, 2)) + "", f4_bold));
            } else {
                phraset.add(new Chunk("0,00", f4_bold));
            }
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setBackgroundColor(new BaseColor(242, 242, 242));
            cellt.setBorder(BOTTOM);
            table4.addCell(cellt);

            phraset = new Phrase();
            phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(qtyPrevYear, 0)) + "", f4_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setBackgroundColor(new BaseColor(242, 242, 242));
            cellt.setBorder(BOTTOM);
            table4.addCell(cellt);

            phraset = new Phrase();
            phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(qtyYear, 0)) + "", f4_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setBackgroundColor(new BaseColor(242, 242, 242));
            cellt.setBorder(BOTTOM);
            table4.addCell(cellt);

            phraset = new Phrase();
            phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(qtyVal, 0)) + "", f4_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setBorder(BOTTOM);
            cellt.setBackgroundColor(new BaseColor(242, 242, 242));
            table4.addCell(cellt);

            phraset = new Phrase();
            if (qtyPrevYear > 0 && qtyYear > 0) {
                phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat((qtyVal / qtyPrevYear) * 100, 2)) + "", f4_bold));
            } else {
                phraset.add(new Chunk("0,00", f4_bold));
            }
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setBorder(BOTTOM);
            cellt.setBackgroundColor(new BaseColor(242, 242, 242));
            table4.addCell(cellt);
            
            
            phraset = new Phrase();
            phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(GPtotVolPrevYear, 2)) + "", f4_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setBackgroundColor(new BaseColor(242, 242, 242));
            cellt.setBorder(BOTTOM);
            table4.addCell(cellt);
            
            phraset = new Phrase();
            phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(GPtotVolYear, 2)) + "", f4_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setBackgroundColor(new BaseColor(242, 242, 242));
            cellt.setBorder(BOTTOM);
            table4.addCell(cellt);
            
             phraset = new Phrase();
            phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(GPtotVolVal, 2)) + "", f4_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setBackgroundColor(new BaseColor(242, 242, 242));
            cellt.setBorder(BOTTOM);
            table4.addCell(cellt);
            
             phraset = new Phrase();
            phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(GPtotVolPerc, 2)) + "", f4_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setBackgroundColor(new BaseColor(242, 242, 242));
            cellt.setBorder(BOTTOM);
            table4.addCell(cellt);
            
            

            document.add(table4);

            //fine total
            vuoto.setFont(f3_normal);
            document.add(vuoto);
            document.add(vuoto);

            if (lastTime) {
                //totale generale

                //total EURO
                LineSeparator sep = new LineSeparator();
                sep.setOffset(-1);
                sep.setLineWidth((float) 0.7);
                document.add(sep);

                table4 = new PdfPTable(colonne.size());
                table4.setWidths(columnWidths2);
                table4.setWidthPercentage(100);

                phraset = new Phrase();
                phraset.add(new Chunk("", f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(NO_BORDER);
                table4.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk("Total Local Currency Amount", f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(NO_BORDER);
                cellt.setBackgroundColor(new BaseColor(242, 242, 242));
                table4.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(totaleEUROtotVolPrevYear, 2)) + "", f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(NO_BORDER);
                cellt.setBackgroundColor(new BaseColor(242, 242, 242));
                table4.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(totaleEUROtotVolYear, 2)) + "", f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(NO_BORDER);
                cellt.setBackgroundColor(new BaseColor(242, 242, 242));
                table4.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(totaleEUROtotVolVal, 2)) + "", f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(NO_BORDER);
                cellt.setBackgroundColor(new BaseColor(242, 242, 242));
                table4.addCell(cellt);

                phraset = new Phrase();
                if (totaleEUROtotVolPrevYear > 0 && totaleEUROtotVolYear > 0) {
                    phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat((totaleEUROtotVolVal / totaleEUROtotVolPrevYear) * 100, 2)) + "", f4_bold));
                } else {
                    phraset.add(new Chunk("0,00", f4_bold));
                }
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(NO_BORDER);
                cellt.setBackgroundColor(new BaseColor(242, 242, 242));
                table4.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(totaleEUROqtyPrevYear,0)) + "", f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(NO_BORDER);
                cellt.setBackgroundColor(new BaseColor(242, 242, 242));
                table4.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(totaleEUROqtyYear, 0)) + "", f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(NO_BORDER);
                cellt.setBackgroundColor(new BaseColor(242, 242, 242));
                table4.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(totaleEUROqtyVal, 0)) + "", f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(NO_BORDER);
                cellt.setBackgroundColor(new BaseColor(242, 242, 242));
                table4.addCell(cellt);

                phraset = new Phrase();
                if (totaleEUROqtyPrevYear > 0 && totaleEUROqtyYear > 0) {
                    phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(totaleEUROqtyPerc, 2)) + "", f4_bold));
                } else {
                    phraset.add(new Chunk("0,00", f4_bold));
                }
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(NO_BORDER);
                cellt.setBackgroundColor(new BaseColor(242, 242, 242));
                table4.addCell(cellt);
                
                
                
                 phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(("0.00")) + "", f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(NO_BORDER);
                cellt.setBackgroundColor(new BaseColor(242, 242, 242));
                table4.addCell(cellt);
                
                 phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(("0.00")) + "", f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(NO_BORDER);
                cellt.setBackgroundColor(new BaseColor(242, 242, 242));
                table4.addCell(cellt);
                
                 phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(("0.00")) + "", f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(NO_BORDER);
                cellt.setBackgroundColor(new BaseColor(242, 242, 242));
                table4.addCell(cellt);
                
                 phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(("0.00")) + "", f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(NO_BORDER);
                cellt.setBackgroundColor(new BaseColor(242, 242, 242));
                table4.addCell(cellt);
                

                document.add(table4);

                //fine total
                //total EURO perc
                table4 = new PdfPTable(colonne.size());
                table4.setWidths(columnWidths2);
                table4.setWidthPercentage(100);

                phraset = new Phrase();
                phraset.add(new Chunk("", f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(NO_BORDER);
                table4.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk("Total Local Currency %", f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(NO_BORDER);
                cellt.setBackgroundColor(new BaseColor(230, 230, 240));
                table4.addCell(cellt);

                phraset = new Phrase();
                if (totaleVolPrevYear != 0) {
                    phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(((100 * totaleEUROtotVolPrevYear) / totaleVolPrevYear), 2)) + "", f4_bold));
                } else {
                    phraset.add(new Chunk("0,00", f4_bold));
                }
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(NO_BORDER);
                cellt.setBackgroundColor(new BaseColor(230, 230, 240));
                table4.addCell(cellt);

                phraset = new Phrase();
                if (totaleVolYear != 0) {
                    phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(((100 * totaleEUROtotVolYear) / totaleVolYear), 2)) + "", f4_bold));
                } else {
                    phraset.add(new Chunk("0,00", f4_bold));
                }
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(NO_BORDER);
                cellt.setBackgroundColor(new BaseColor(230, 230, 240));
                table4.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk("", f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(NO_BORDER);
                cellt.setBackgroundColor(new BaseColor(230, 230, 240));
                table4.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk("", f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(NO_BORDER);
                cellt.setBackgroundColor(new BaseColor(230, 230, 240));
                table4.addCell(cellt);

                phraset = new Phrase();
                if (totaleqtyPrevYear != 0) {
                    phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(((100 * totaleEUROqtyPrevYear) / totaleqtyPrevYear), 2)) + "", f4_bold));
                } else {
                    phraset.add(new Chunk("0,00", f4_bold));
                }
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(NO_BORDER);
                cellt.setBackgroundColor(new BaseColor(230, 230, 240));
                table4.addCell(cellt);

                phraset = new Phrase();
                if (totaleqtyYear != 0) {
                    phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(((100 * totaleEUROqtyYear) / totaleqtyYear), 2)) + "", f4_bold));
                } else {
                    phraset.add(new Chunk("0,00", f4_bold));
                }
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(NO_BORDER);
                cellt.setBackgroundColor(new BaseColor(230, 230, 240));
                table4.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk("", f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(NO_BORDER);
                cellt.setBackgroundColor(new BaseColor(230, 230, 240));
                table4.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk("", f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(NO_BORDER);
                cellt.setBackgroundColor(new BaseColor(230, 230, 240));
                table4.addCell(cellt);
                
                
                 phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(("0.00")) + "", f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(NO_BORDER);
                cellt.setBackgroundColor(new BaseColor(242, 242, 242));
                table4.addCell(cellt);
                
                 phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(("0.00")) + "", f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(NO_BORDER);
                cellt.setBackgroundColor(new BaseColor(242, 242, 242));
                table4.addCell(cellt);
                
                 phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(("0.00")) + "", f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(NO_BORDER);
                cellt.setBackgroundColor(new BaseColor(242, 242, 242));
                table4.addCell(cellt);
                
                 phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(("0.00")) + "", f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(NO_BORDER);
                cellt.setBackgroundColor(new BaseColor(242, 242, 242));
                table4.addCell(cellt);
                

                document.add(table4);

                //fine total perc
                //total VALUTA
                table4 = new PdfPTable(colonne.size());
                table4.setWidths(columnWidths2);
                table4.setWidthPercentage(100);

                phraset = new Phrase();
                phraset.add(new Chunk("", f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(NO_BORDER);
                table4.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk("Total Currency Amount", f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(NO_BORDER);
                cellt.setBackgroundColor(new BaseColor(242, 242, 242));
                table4.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(totaleVALUTAtotVolPrevYear, 2)) + "", f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(NO_BORDER);
                cellt.setBackgroundColor(new BaseColor(242, 242, 242));
                table4.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(totaleVALUTAtotVolYear, 2)) + "", f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(NO_BORDER);
                cellt.setBackgroundColor(new BaseColor(242, 242, 242));
                table4.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(totaleVALUTAtotVolVal, 2)) + "", f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(NO_BORDER);
                cellt.setBackgroundColor(new BaseColor(242, 242, 242));
                table4.addCell(cellt);

                phraset = new Phrase();
                if (totaleVALUTAtotVolPrevYear > 0 && totaleVALUTAtotVolYear > 0) {
                    phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat((totaleVALUTAtotVolVal / totaleVALUTAtotVolPrevYear) * 100, 2)) + "", f4_bold));
                } else {
                    phraset.add(new Chunk("0,00", f4_bold));
                }
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(NO_BORDER);
                cellt.setBackgroundColor(new BaseColor(242, 242, 242));
                table4.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(totaleVALUTAqtyPrevYear, 0)) + "", f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(NO_BORDER);
                cellt.setBackgroundColor(new BaseColor(242, 242, 242));
                table4.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(totaleVALUTAqtyYear, 0)) + "", f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(NO_BORDER);
                cellt.setBackgroundColor(new BaseColor(242, 242, 242));
                table4.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(totaleVALUTAqtyVal, 0)) + "", f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(NO_BORDER);
                cellt.setBackgroundColor(new BaseColor(242, 242, 242));
                table4.addCell(cellt);

                phraset = new Phrase();
                if (totaleVALUTAqtyPrevYear > 0 && totaleVALUTAqtyYear > 0) {
                    phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat((totaleVALUTAqtyVal / totaleVALUTAqtyPrevYear) * 100, 2)) + "", f4_bold));
                } else {
                    phraset.add(new Chunk("0,00", f4_bold));
                }
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(NO_BORDER);
                cellt.setBackgroundColor(new BaseColor(242, 242, 242));
                table4.addCell(cellt);
                
                
                
                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(GPtotaleVALUTAtotVolPrevYear, 2)) + "", f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(NO_BORDER);
                cellt.setBackgroundColor(new BaseColor(242, 242, 242));
                table4.addCell(cellt);
                
                  phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(GPtotaleVALUTAtotVolYear, 2)) + "", f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(NO_BORDER);
                cellt.setBackgroundColor(new BaseColor(242, 242, 242));
                table4.addCell(cellt);
                
                
                  phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(GPtotaleVALUTAtotVolVal, 2)) + "", f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(NO_BORDER);
                cellt.setBackgroundColor(new BaseColor(242, 242, 242));
                table4.addCell(cellt);
                
                
                  phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(GPtotaleVALUTAtotVolPerc, 2)) + "", f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(NO_BORDER);
                cellt.setBackgroundColor(new BaseColor(242, 242, 242));
                table4.addCell(cellt);
                

                document.add(table4);

                //fine total
                //total VALUTA perc
                table4 = new PdfPTable(colonne.size());
                table4.setWidths(columnWidths2);
                table4.setWidthPercentage(100);

                phraset = new Phrase();
                phraset.add(new Chunk("", f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(NO_BORDER);
                table4.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk("Total Currency %", f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(NO_BORDER);
                cellt.setBackgroundColor(new BaseColor(230, 230, 240));
                table4.addCell(cellt);

                phraset = new Phrase();
                if (totaleVolPrevYear != 0) {
                    phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(((100 * totaleVALUTAtotVolPrevYear) / totaleVolPrevYear), 2)) + "", f4_bold));
                } else {
                    phraset.add(new Chunk("0,00", f4_bold));
                }
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(NO_BORDER);
                cellt.setBackgroundColor(new BaseColor(230, 230, 240));
                table4.addCell(cellt);

                phraset = new Phrase();
                if (totaleVolYear != 0) {
                    phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(((100 * totaleVALUTAtotVolYear) / totaleVolYear), 2)) + "", f4_bold));
                } else {
                    phraset.add(new Chunk("0,00", f4_bold));
                }
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(NO_BORDER);
                cellt.setBackgroundColor(new BaseColor(230, 230, 240));
                table4.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk("", f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(NO_BORDER);
                cellt.setBackgroundColor(new BaseColor(230, 230, 240));
                table4.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk("", f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(NO_BORDER);
                cellt.setBackgroundColor(new BaseColor(230, 230, 240));
                table4.addCell(cellt);

                phraset = new Phrase();
                if (totaleqtyPrevYear != 0) {
                    phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(((100 * totaleVALUTAqtyPrevYear) / totaleqtyPrevYear), 2)) + "", f4_bold));
                } else {
                    phraset.add(new Chunk("0,00", f4_bold));
                }
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(NO_BORDER);
                cellt.setBackgroundColor(new BaseColor(230, 230, 240));
                table4.addCell(cellt);

                phraset = new Phrase();
                if (totaleqtyYear != 0) {
                    phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(((100 * totaleVALUTAqtyYear) / totaleqtyYear), 2)) + "", f4_bold));
                } else {
                    phraset.add(new Chunk("0,00", f4_bold));
                }
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(NO_BORDER);
                cellt.setBackgroundColor(new BaseColor(230, 230, 240));
                table4.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk("", f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(NO_BORDER);
                cellt.setBackgroundColor(new BaseColor(230, 230, 240));
                table4.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk("", f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(NO_BORDER);
                cellt.setBackgroundColor(new BaseColor(230, 230, 240));
                table4.addCell(cellt);
                
                
                
                phraset = new Phrase();
                if (GPtotaleVolPrevYear != 0) {
                    phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(((100 * GPtotaleVALUTAtotVolPrevYear) / GPtotaleVolPrevYear), 2)) + "", f4_bold));
                } else {
                    phraset.add(new Chunk("0,00", f4_bold));
                }
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(NO_BORDER);
                cellt.setBackgroundColor(new BaseColor(230, 230, 240));
                table4.addCell(cellt);
                
                phraset = new Phrase();
                if (GPtotaleVolYear != 0) {
                    phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(((100 * GPtotaleVALUTAtotVolYear) / GPtotaleVolYear), 2)) + "", f4_bold));
                } else {
                    phraset.add(new Chunk("0,00", f4_bold));
                }
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(NO_BORDER);
                cellt.setBackgroundColor(new BaseColor(230, 230, 240));
                table4.addCell(cellt);
                
                phraset = new Phrase();
                if (GPtotaleVolVal != 0) {
                    phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(((100 * GPtotaleVALUTAtotVolVal) / GPtotaleVolVal), 2)) + "", f4_bold));
                } else {
                    phraset.add(new Chunk("0,00", f4_bold));
                }
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(NO_BORDER);
                cellt.setBackgroundColor(new BaseColor(230, 230, 240));
                table4.addCell(cellt);
                
                 phraset = new Phrase();
                if (GPtotaleVolPerc != 0) {
                    phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(((100 * GPtotaleVALUTAtotVolPerc) / GPtotaleVolPerc), 2)) + "", f4_bold));
                } else {
                    phraset.add(new Chunk("0,00", f4_bold));
                }
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(NO_BORDER);
                cellt.setBackgroundColor(new BaseColor(230, 230, 240));
                table4.addCell(cellt);
                

                document.add(table4);

                //fine total perc
                //total
                table4 = new PdfPTable(colonne.size());
                table4.setWidths(columnWidths2);
                table4.setWidthPercentage(100);

                phraset = new Phrase();
                phraset.add(new Chunk("", f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);

                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk("Grand Total", f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBackgroundColor(new BaseColor(242, 242, 242));
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(totaleVolPrevYear, 2)) + "", f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBackgroundColor(new BaseColor(242, 242, 242));
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(totaleVolYear, 2)) + "", f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBackgroundColor(new BaseColor(242, 242, 242));
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(totaleVolVal, 2)) + "", f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBackgroundColor(new BaseColor(242, 242, 242));
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);

                phraset = new Phrase();
                if (totaleVolPrevYear > 0 && totaleVolYear > 0) {
                    phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat((totaleVolVal / totaleVolPrevYear) * 100, 2)) + "", f4_bold));
                } else {
                    phraset.add(new Chunk("0,00", f4_bold));
                }
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBackgroundColor(new BaseColor(242, 242, 242));
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(totaleqtyPrevYear, 0)) + "", f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBackgroundColor(new BaseColor(242, 242, 242));
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(totaleqtyYear, 0)) + "", f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBackgroundColor(new BaseColor(242, 242, 242));
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(totaleqtyVal, 0)) + "", f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                cellt.setBackgroundColor(new BaseColor(242, 242, 242));
                table4.addCell(cellt);

                phraset = new Phrase();
                if (totaleqtyPrevYear > 0 && totaleqtyYear > 0) {
                    phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat((totaleqtyVal / totaleqtyPrevYear) * 100, 2)) + "", f4_bold));
                } else {
                    phraset.add(new Chunk("0,00", f4_bold));
                }
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                cellt.setBackgroundColor(new BaseColor(242, 242, 242));
                table4.addCell(cellt);
                
                
                 phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(GPtotaleVolPrevYear, 2)) + "", f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBackgroundColor(new BaseColor(242, 242, 242));
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);
                
                
                 phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(GPtotaleVolYear, 2)) + "", f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBackgroundColor(new BaseColor(242, 242, 242));
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);
                
                
                 phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(GPtotaleVolVal, 2)) + "", f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBackgroundColor(new BaseColor(242, 242, 242));
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);
                
                 phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(GPtotaleVolPerc, 2)) + "", f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBackgroundColor(new BaseColor(242, 242, 242));
                cellt.setBorder(BOTTOM);
                table4.addCell(cellt);

                document.add(table4);

                //fine total
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
     * @param filiali
     * @param br
     * @return
     */
    public String main(String path, String d3, String d4,
            String data1, String data2,
            ArrayList<String> alcolonne, ArrayList<String> filiali, ArrayList<Branch> br) {
        try {
            C_FreeTax_BranchCausal nctl = new C_FreeTax_BranchCausal();

            C_FreeTax_BranchCausal_value pdf = new C_FreeTax_BranchCausal_value();

            boolean firstTime = true;
            boolean lastTime = false;

            File pdffile = new File(path + generaId(50) + "C_FreeTax_BranchCausal.pdf");
            try(OutputStream ou = new FileOutputStream(pdffile)){
            Document document = new Document(A4.rotate(), 20, 20, 20, 20);
            PdfWriter wr = getInstance(document, ou);
            document.open();

            Db_Master dbm = new Db_Master();
            boolean find = false;

            for (int i = 0; i < filiali.size(); i++) {

                ArrayList<C_FreeTax_BranchCausal_value> dati = dbm.list_C_FreeTax_BranchCausal_value(filiali.get(i), data1, data2, br);
                if (dati.size() > 0) {
                    find = true;
                }
                pdf.setDataDa(d3);
                pdf.setDataA(d4);
                pdf.setDati(dati);
                pdf.setId_filiale(filiali.get(i));
                pdf.setDe_filiale(formatBankBranchReport(filiali.get(i), "BR", null, br));

                if (i == filiali.size() - 1) {
                    lastTime = true;
                }

                document = nctl.receipt(pdf, alcolonne, firstTime, lastTime, document);

                firstTime = false;

            }

            dbm.closeDB();


            if (find) {
                //chiusura documento
                document.close();
                wr.close();
                ou.close();
                String base64 = new String(encodeBase64(readFileToByteArray(pdffile)));
                pdffile.delete();
                return base64;
            } else {

                document.add(new Paragraph("CZZ"));
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
     * @param firstTime
     * @param lastTime
     * @param sheet
     * @param cntriga
     * @param style1
     * @param style2
     * @param style3
     * @param style4
     * @param style3left
     * @param style4left
     * @param style3center
     * @return
     */
    public int receiptexcel(C_FreeTax_BranchCausal_value cmfb, ArrayList<String> colonne, boolean firstTime, boolean lastTime, HSSFSheet sheet, int cntriga, HSSFCellStyle style1, HSSFCellStyle style2, HSSFCellStyle style3, HSSFCellStyle style4, HSSFCellStyle style3left, HSSFCellStyle style4left, HSSFCellStyle style3center) {

        try {

            if (firstTime) {
                Row rowP = sheet.createRow((short) cntriga);

                Cell cl = rowP.createCell(1);
                cl.setCellStyle(style1);
                cl.setCellValue(intestazionePdf + " From " + cmfb.getDataDa() + " to " + cmfb.getDataA());

                cntriga++;
                cntriga++;

            }

            ArrayList<C_FreeTax_BranchCausal_value> dati = cmfb.getDati();

            double totVolPrevYear = 0;
            double totVolYear = 0;
            double totVolVal = 0;
            double totVolPerc = 0;
            double qtyPrevYear = 0;
            double qtyYear = 0;
            double qtyVal = 0;
            double qtyPerc = 0;

            double GPtotVolPrevYear = 0;
            double GPtotVolYear = 0;
            double GPtotVolVal = 0;
            double GPtotVolPerc = 0;

            double EUROtotVolPrevYear = 0;
            double EUROtotVolYear = 0;
            double EUROtotVolVal = 0;
            double EUROtotVolPerc = 0;
            double EUROqtyPrevYear = 0;
            double EUROqtyYear = 0;
            double EUROqtyVal = 0;
            double EUROqtyPerc = 0;

            double VALUTAtotVolPrevYear = 0;
            double VALUTAtotVolYear = 0;
            double VALUTAtotVolVal = 0;
            double VALUTAtotVolPerc = 0;
            double VALUTAqtyPrevYear = 0;
            double VALUTAqtyYear = 0;
            double VALUTAqtyVal = 0;
            double VALUTAqtyPerc = 0;

            double GPVALUTAtotVolPrevYear = 0;
            double GPVALUTAtotVolYear = 0;
            double GPVALUTAtotVolVal = 0;
            double GPVALUTAtotVolPerc = 0;

            boolean ft = true;

            if (ft) {

                //intestazione Volume - Quantità
                cntriga++;
                Row row66 = sheet.createRow((short) cntriga);

                Cell f1bis = row66.createCell(1);
                f1bis.setCellStyle(style3left);
                f1bis.setCellValue(cmfb.getId_filiale());

                Cell f1bis222 = row66.createCell(2);
                f1bis222.setCellStyle(style3left);
                f1bis222.setCellValue(cmfb.getDe_filiale());

                Cell f1bis1 = row66.createCell(3);
                f1bis1.setCellStyle(style3center);
                f1bis1.setCellValue("Amount");

                Cell f1bis2 = row66.createCell(7);
                f1bis2.setCellStyle(style3center);
                f1bis2.setCellValue("Forms Quantity");

                Cell f1bis22 = row66.createCell(11);
                f1bis22.setCellStyle(style3center);
                f1bis22.setCellValue("Gross Profit");

                CellRangeAddress cellRangeAddress = new CellRangeAddress(cntriga, cntriga, 3, 6);
                setBorderTop(MEDIUM, cellRangeAddress, sheet);
                setBorderBottom(MEDIUM, cellRangeAddress, sheet);
                setBorderLeft(MEDIUM, cellRangeAddress, sheet);
                setBorderRight(MEDIUM, cellRangeAddress, sheet);
                sheet.addMergedRegion(cellRangeAddress);
                cellRangeAddress = new CellRangeAddress(cntriga, cntriga, 7, 10);
                setBorderTop(MEDIUM, cellRangeAddress, sheet);
                setBorderBottom(MEDIUM, cellRangeAddress, sheet);
                setBorderLeft(MEDIUM, cellRangeAddress, sheet);
                setBorderRight(MEDIUM, cellRangeAddress, sheet);
                sheet.addMergedRegion(cellRangeAddress);
                cellRangeAddress = new CellRangeAddress(cntriga, cntriga, 11, 14);
                setBorderTop(MEDIUM, cellRangeAddress, sheet);
                setBorderBottom(MEDIUM, cellRangeAddress, sheet);
                setBorderLeft(MEDIUM, cellRangeAddress, sheet);
                setBorderRight(MEDIUM, cellRangeAddress, sheet);
                sheet.addMergedRegion(cellRangeAddress);

                //fine intestazione Volume - Quantità
                cntriga++;

                cntriga++;

                Row row22 = sheet.createRow((short) cntriga);
                //mi scandisco le colonne
                for (int c = 0; c < colonne.size(); c++) {
                    Cell cl7 = row22.createCell(c + 1);
                    cl7.setCellStyle(style3);
                    cl7.setCellValue(colonne.get(c));
                }

                cntriga++;
            }

            for (int j = 0; j < dati.size(); j++) {

                cntriga++;
                Row row6 = sheet.createRow((short) cntriga);

//                 EUROtotVolPrevYear = 0;
//             EUROtotVolYear = 0;
//             EUROtotVolVal = 0;
//             EUROtotVolPerc = 0;
//             EUROqtyPrevYear = 0;
//             EUROqtyYear = 0;
//             EUROqtyVal = 0;
//             EUROqtyPerc = 0;
//
//             VALUTAtotVolPrevYear = 0;
//             VALUTAtotVolYear = 0;
//             VALUTAtotVolVal = 0;
//             VALUTAtotVolPerc = 0;
//             VALUTAqtyPrevYear = 0;
//             VALUTAqtyYear = 0;
//             VALUTAqtyVal = 0;
//             VALUTAqtyPerc = 0;
//             
//              GPVALUTAtotVolPrevYear = 0;
//             GPVALUTAtotVolYear = 0;
//             GPVALUTAtotVolVal = 0;
//             GPVALUTAtotVolPerc = 0;
                C_FreeTax_BranchCausal_value temp =  dati.get(j);

                //   listaTot.add(temp);
                Cell f1bis = row6.createCell(1);
                f1bis.setCellStyle(style4left);
                f1bis.setCellValue(temp.getCausal());

                Cell f2 = row6.createCell(2);
                f2.setCellStyle(style4left);
                if (temp.isValuta()) {
                    f2.setCellValue("Currency");
                } else {
                    f2.setCellValue("Local Currency");
                }

                Cell f3 = row6.createCell(3);
                f3.setCellStyle(style4);
                f3.setCellValue(formatMysqltoDisplay(temp.getVolPrevYear()));

                Cell f4 = row6.createCell(4);
                f4.setCellStyle(style4);
                f4.setCellValue(formatMysqltoDisplay(temp.getVolYear()));

                Cell f4b = row6.createCell(5);
                f4b.setCellStyle(style4);
                f4b.setCellValue(formatMysqltoDisplay(temp.getVolVal()));

                Cell f5 = row6.createCell(6);
                f5.setCellStyle(style4);
                f5.setCellValue(formatMysqltoDisplay(temp.getVolPerc()));

                Cell f6 = row6.createCell(7);
                f6.setCellStyle(style4);
                f6.setCellValue(formatMysqltoDisplay(temp.getQtyPrevYear()));

                Cell f7 = row6.createCell(8);
                f7.setCellStyle(style4);
                f7.setCellValue(formatMysqltoDisplay(temp.getQtyYear()));

                Cell f8 = row6.createCell(9);
                f8.setCellStyle(style4);
                f8.setCellValue(formatMysqltoDisplay(temp.getQtyVal()));

                Cell f9 = row6.createCell(10);
                f9.setCellStyle(style4);
                f9.setCellValue(formatMysqltoDisplay(temp.getQtyPerc()));

                Cell f911 = row6.createCell(11);
                f911.setCellStyle(style4);
                f911.setCellValue(formatMysqltoDisplay(temp.getVolGMPrevYear()));

                Cell f912 = row6.createCell(12);
                f912.setCellStyle(style4);
                f912.setCellValue(formatMysqltoDisplay(temp.getVolGMYear()));

                Cell f913 = row6.createCell(13);
                f913.setCellStyle(style4);
                f913.setCellValue(formatMysqltoDisplay(temp.getVolGMVal()));

                Cell f914 = row6.createCell(14);
                f914.setCellStyle(style4);
                f914.setCellValue(formatMysqltoDisplay(temp.getVolGMPerc()));

                totVolPrevYear += ff(temp.getVolPrevYear());
                totVolYear += ff(temp.getVolYear());
                totVolVal += ff(temp.getVolVal());
                totVolPerc += ff(temp.getVolPerc());
                qtyPrevYear += ff(temp.getQtyPrevYear());
                qtyYear += ff(temp.getQtyYear());
                qtyVal += ff(temp.getQtyVal());
                qtyPerc += ff(temp.getQtyPerc());

                GPtotVolPrevYear += ff(temp.getVolGMPrevYear());
                GPtotVolYear += ff(temp.getVolGMYear());
                GPtotVolVal += ff(temp.getVolGMVal());
                GPtotVolPerc += ff(temp.getVolGMPerc());

                if (temp.isValuta()) {
                    VALUTAtotVolPrevYear += ff(temp.getVolPrevYear());
                    VALUTAtotVolYear += ff(temp.getVolYear());
                    VALUTAtotVolVal += ff(temp.getVolVal());
                    VALUTAtotVolPerc += ff(temp.getVolPerc());
                    VALUTAqtyPrevYear += ff(temp.getQtyPrevYear());
                    VALUTAqtyYear += ff(temp.getQtyYear());
                    VALUTAqtyVal += ff(temp.getQtyVal());
                    VALUTAqtyPerc += ff(temp.getQtyPerc());

                    GPVALUTAtotVolPrevYear += ff(temp.getVolGMPrevYear());
                    GPVALUTAtotVolYear += ff(temp.getVolGMYear());
                    GPVALUTAtotVolVal += ff(temp.getVolGMVal());
                    GPVALUTAtotVolPerc += ff(temp.getVolGMPerc());

                } else {
                    EUROtotVolPrevYear += ff(temp.getVolPrevYear());
                    EUROtotVolYear += ff(temp.getVolYear());
                    EUROtotVolVal += ff(temp.getVolVal());
                    EUROtotVolPerc += ff(temp.getVolPerc());
                    EUROqtyPrevYear += ff(temp.getQtyPrevYear());
                    EUROqtyYear += ff(temp.getQtyYear());
                    EUROqtyVal += ff(temp.getQtyVal());
                    EUROqtyPerc += ff(temp.getQtyPerc());
                }

                //totale generale                
                totaleVolPrevYear += ff(temp.getVolPrevYear());
                totaleVolYear += ff(temp.getVolYear());
                totaleVolVal += ff(temp.getVolVal());
                totaleVolPerc += ff(temp.getVolPerc());
                totaleqtyPrevYear += ff(temp.getQtyPrevYear());
                totaleqtyYear += ff(temp.getQtyYear());
                totaleqtyVal += ff(temp.getQtyVal());
                totaleqtyPerc += ff(temp.getQtyPerc());

                GPtotaleVolPrevYear += ff(temp.getVolGMPrevYear());
                GPtotaleVolYear += ff(temp.getVolGMYear());
                GPtotaleVolVal += ff(temp.getVolGMVal());
                GPtotaleVolPerc += ff(temp.getVolGMPerc());

                if (temp.isValuta()) {
                    totaleVALUTAtotVolPrevYear += ff(temp.getVolPrevYear());
                    totaleVALUTAtotVolYear += ff(temp.getVolYear());
                    totaleVALUTAtotVolVal += ff(temp.getVolVal());
                    totaleVALUTAtotVolPerc += ff(temp.getVolPerc());
                    totaleVALUTAqtyPrevYear += ff(temp.getQtyPrevYear());
                    totaleVALUTAqtyYear += ff(temp.getQtyYear());
                    totaleVALUTAqtyVal += ff(temp.getQtyVal());
                    totaleVALUTAqtyPerc += ff(temp.getQtyPerc());

                    GPtotaleVALUTAtotVolPrevYear += ff(temp.getVolGMPrevYear());
                    GPtotaleVALUTAtotVolYear += ff(temp.getVolGMYear());
                    GPtotaleVALUTAtotVolVal += ff(temp.getVolGMVal());
                    GPtotaleVALUTAtotVolPerc += ff(temp.getVolGMPerc());

                } else {
                    totaleEUROtotVolPrevYear += ff(temp.getVolPrevYear());
                    totaleEUROtotVolYear += ff(temp.getVolYear());
                    totaleEUROtotVolVal += ff(temp.getVolVal());
                    totaleEUROtotVolPerc += ff(temp.getVolPerc());
                    totaleEUROqtyPrevYear += ff(temp.getQtyPrevYear());
                    totaleEUROqtyYear += ff(temp.getQtyYear());
                    totaleEUROqtyVal += ff(temp.getQtyVal());
                    totaleEUROqtyPerc += ff(temp.getQtyPerc());
                }

            }

            //total EURO
            totVolPrevYear = roundDouble(totVolPrevYear, 2);
            totVolYear = roundDouble(totVolYear, 2);
            totVolVal = roundDouble(totVolVal, 2);
            qtyPrevYear = roundDouble(qtyPrevYear, 2);
            qtyYear = roundDouble(qtyYear, 2);
            qtyVal = roundDouble(qtyVal, 2);

            GPtotVolPrevYear = roundDouble(GPtotVolPrevYear, 2);
            GPtotVolYear = roundDouble(GPtotVolYear, 2);
            GPtotVolVal = roundDouble(GPtotVolVal, 2);
            GPtotVolPerc = roundDouble(GPtotVolPerc, 2);

            VALUTAtotVolPrevYear = roundDouble(VALUTAtotVolPrevYear, 2);
            VALUTAtotVolYear = roundDouble(VALUTAtotVolYear, 2);
            VALUTAtotVolVal = roundDouble(VALUTAtotVolVal, 2);
            VALUTAqtyPrevYear = roundDouble(VALUTAqtyPrevYear, 2);
            VALUTAqtyYear = roundDouble(VALUTAqtyYear, 2);
            VALUTAqtyVal = roundDouble(VALUTAqtyVal, 2);

            GPVALUTAtotVolPrevYear = roundDouble(GPVALUTAtotVolPrevYear, 2);
            GPVALUTAtotVolYear = roundDouble(GPVALUTAtotVolYear, 2);
            GPVALUTAtotVolVal = roundDouble(GPVALUTAtotVolVal, 2);
            GPVALUTAtotVolPerc = roundDouble(GPVALUTAtotVolPerc, 2);

            EUROtotVolPrevYear = roundDouble(EUROtotVolPrevYear, 2);
            EUROtotVolYear = roundDouble(EUROtotVolYear, 2);
            EUROtotVolVal = roundDouble(EUROtotVolVal, 2);
            EUROqtyPrevYear = roundDouble(EUROqtyPrevYear, 2);
            EUROqtyYear = roundDouble(EUROqtyYear, 2);
            EUROqtyVal = roundDouble(EUROqtyVal, 2);

            totaleVolPrevYear = roundDouble(totaleVolPrevYear, 2);
            totaleVolYear = roundDouble(totaleVolYear, 2);
            totaleVolVal = roundDouble(totaleVolVal, 2);
            totaleVolPerc = roundDouble(totaleVolPerc, 2);
            totaleqtyPrevYear = roundDouble(totaleqtyPrevYear, 0);
            totaleqtyYear = roundDouble(totaleqtyYear, 0);
            totaleqtyVal = roundDouble(totaleqtyVal, 0);
            totaleqtyPerc = roundDouble(totaleqtyPerc, 2);

            GPtotaleVolPrevYear = roundDouble(GPtotaleVolPrevYear, 2);
            GPtotaleVolYear = roundDouble(GPtotaleVolYear, 2);
            GPtotaleVolVal = roundDouble(GPtotaleVolVal, 2);
            GPtotaleVolPerc = roundDouble(GPtotaleVolPerc, 2);

            totaleVALUTAtotVolPrevYear = roundDouble(totaleVALUTAtotVolPrevYear, 2);
            totaleVALUTAtotVolYear = roundDouble(totaleVALUTAtotVolYear, 2);
            totaleVALUTAtotVolVal = roundDouble(totaleVALUTAtotVolVal, 2);
            totaleVALUTAtotVolPerc = roundDouble(totaleVALUTAtotVolPerc, 2);
            totaleVALUTAqtyPrevYear = roundDouble(totaleVALUTAqtyPrevYear,0);
            totaleVALUTAqtyYear = roundDouble(totaleVALUTAqtyYear, 0);
            totaleVALUTAqtyVal = roundDouble(totaleVALUTAqtyVal, 0);
            totaleVALUTAqtyPerc = roundDouble(totaleVALUTAqtyPerc, 2);

            GPtotaleVALUTAtotVolPrevYear = roundDouble(GPtotaleVALUTAtotVolPrevYear, 2);
            GPtotaleVALUTAtotVolYear = roundDouble(GPtotaleVALUTAtotVolYear, 2);
            GPtotaleVALUTAtotVolVal = roundDouble(GPtotaleVALUTAtotVolVal, 2);
            GPtotaleVALUTAtotVolPerc = roundDouble(GPtotaleVALUTAtotVolPerc, 2);

            totaleEUROtotVolPrevYear = roundDouble(totaleEUROtotVolPrevYear, 2);
            totaleEUROtotVolYear = roundDouble(totaleEUROtotVolYear, 2);
            totaleEUROtotVolVal = roundDouble(totaleEUROtotVolVal, 2);
            totaleEUROtotVolPerc = roundDouble(totaleEUROtotVolPerc, 2);
            totaleEUROqtyPrevYear = roundDouble(totaleEUROqtyPrevYear, 0);
            totaleEUROqtyYear = roundDouble(totaleEUROqtyYear,0);
            totaleEUROqtyVal = roundDouble(totaleEUROqtyVal, 0);
            totaleEUROqtyPerc = roundDouble(totaleEUROqtyPerc, 2);

            cntriga++;
            Row row6 = sheet.createRow((short) cntriga);

            Cell f2 = row6.createCell(2);
            f2.setCellStyle(style3left);
            f2.setCellValue("Total Local Currency Amount");

            Cell f3 = row6.createCell(3);
            f3.setCellStyle(style3);
            f3.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(EUROtotVolPrevYear, 2)));

            Cell f4 = row6.createCell(4);
            f4.setCellStyle(style3);
            f4.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(EUROtotVolYear, 2)));

            Cell f5 = row6.createCell(5);
            f5.setCellStyle(style3);
            f5.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(EUROtotVolVal, 2)));

            Cell f6 = row6.createCell(6);
            f6.setCellStyle(style3);
            if (EUROtotVolPrevYear > 0 && EUROtotVolYear > 0) {
                f6.setCellValue(formatMysqltoDisplay(roundDoubleandFormat((EUROtotVolVal / EUROtotVolPrevYear) * 100, 2)));
            } else {
                f6.setCellValue("0,00");
            }

            Cell f6b = row6.createCell(7);
            f6b.setCellStyle(style3);
            f6b.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(EUROqtyPrevYear, 0)));

            Cell f7 = row6.createCell(8);
            f7.setCellStyle(style3);
            f7.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(EUROqtyYear, 0)));

            Cell f8 = row6.createCell(9);
            f8.setCellStyle(style3);
            f8.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(EUROqtyVal, 0)));

            Cell f9 = row6.createCell(10);
            f9.setCellStyle(style3);
            if (EUROqtyPrevYear > 0 && EUROqtyYear > 0) {
                f9.setCellValue(formatMysqltoDisplay(roundDoubleandFormat((EUROqtyVal / EUROqtyPrevYear) * 100, 2)));
            } else {
                f9.setCellValue("0,00");
            }

            Cell f811 = row6.createCell(11);
            f811.setCellStyle(style3);
            f811.setCellValue(formatMysqltoDisplay("0.00"));

            Cell f812 = row6.createCell(12);
            f812.setCellStyle(style3);
            f812.setCellValue(formatMysqltoDisplay("0.00"));

            Cell f813 = row6.createCell(13);
            f813.setCellStyle(style3);
            f813.setCellValue(formatMysqltoDisplay("0.00"));

            Cell f814 = row6.createCell(14);
            f814.setCellStyle(style3);
            f814.setCellValue(formatMysqltoDisplay("0.00"));

            //fine total
            //total EURO perc
            cntriga++;
            Row row9 = sheet.createRow((short) cntriga);

            Cell f92 = row9.createCell(2);
            f92.setCellStyle(style3left);
            f92.setCellValue("Total Local Currency %");

            Cell f93 = row9.createCell(3);
            f93.setCellStyle(style3);
            if (totVolPrevYear != 0) {
                f93.setCellValue(formatMysqltoDisplay(roundDoubleandFormat((100 * EUROtotVolPrevYear) / totVolPrevYear, 2)));
            } else {
                f93.setCellValue("0,00");
            }

            Cell f94 = row9.createCell(4);
            f94.setCellStyle(style3);
            if (totVolYear != 0) {
                f94.setCellValue(formatMysqltoDisplay(roundDoubleandFormat((100 * EUROtotVolYear) / totVolYear, 2)));
            } else {
                f94.setCellValue("0,00");
            }

            Cell f95 = row9.createCell(7);
            f95.setCellStyle(style3);
            if (qtyPrevYear != 0) {
                f95.setCellValue(formatMysqltoDisplay(roundDoubleandFormat((100 * EUROqtyPrevYear) / qtyPrevYear, 2)));
            } else {
                f95.setCellValue("0,00");
            }

            Cell f96 = row9.createCell(8);
            f96.setCellStyle(style3);
            if (qtyYear != 0) {
                f96.setCellValue(formatMysqltoDisplay(roundDoubleandFormat((100 * EUROqtyYear) / qtyYear, 2)));
            } else {
                f96.setCellValue("0,00");
            }

            Cell f8111 = row9.createCell(11);
            f8111.setCellStyle(style3);
            f8111.setCellValue(formatMysqltoDisplay("0.00"));

            Cell f8121 = row9.createCell(12);
            f8121.setCellStyle(style3);
            f8121.setCellValue(formatMysqltoDisplay("0.00"));

            Cell f8131 = row9.createCell(13);
            f8131.setCellStyle(style3);
            f8131.setCellValue(formatMysqltoDisplay("0.00"));

            Cell f8141 = row9.createCell(14);
            f8141.setCellStyle(style3);
            f8141.setCellValue(formatMysqltoDisplay("0.00"));

            //fine total perc
            //total VALUTA
            cntriga++;

            Row row10 = sheet.createRow((short) cntriga);

            Cell f32 = row10.createCell(2);
            f32.setCellStyle(style3left);
            f32.setCellValue("Total Currency Amount");

            Cell f43 = row10.createCell(3);
            f43.setCellStyle(style3);
            f43.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(VALUTAtotVolPrevYear, 2)));

            Cell f44 = row10.createCell(4);
            f44.setCellStyle(style3);
            f44.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(VALUTAtotVolYear, 2)));

            Cell f45 = row10.createCell(5);
            f45.setCellStyle(style3);
            f45.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(VALUTAtotVolVal, 2)));

            Cell f46 = row10.createCell(6);
            f46.setCellStyle(style3);
            if (VALUTAtotVolPrevYear > 0 && VALUTAtotVolYear > 0) {
                f46.setCellValue(formatMysqltoDisplay(roundDoubleandFormat((VALUTAtotVolVal / VALUTAtotVolPrevYear) * 100, 2)));
            } else {
                f46.setCellValue("0,00");
            }

            Cell f47 = row10.createCell(7);
            f47.setCellStyle(style3);
            f47.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(VALUTAqtyPrevYear, 0)));

            Cell f48 = row10.createCell(8);
            f48.setCellStyle(style3);
            f48.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(VALUTAqtyYear, 0)));

            Cell f49 = row10.createCell(9);
            f49.setCellStyle(style3);
            f49.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(VALUTAqtyVal, 0)));

            Cell f50 = row10.createCell(10);
            f50.setCellStyle(style3);
            if (VALUTAqtyPrevYear > 0 && VALUTAqtyYear > 0) {
                f50.setCellValue(formatMysqltoDisplay(roundDoubleandFormat((VALUTAqtyVal / VALUTAqtyPrevYear) * 100, 2)));
            } else {
                f50.setCellValue("0,00");
            }

            Cell f81a = row10.createCell(11);
            f81a.setCellStyle(style3);
            f81a.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(GPVALUTAtotVolPrevYear, 2)));

            Cell f82a = row10.createCell(12);
            f82a.setCellStyle(style3);
            f82a.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(GPVALUTAtotVolYear, 2)));

            Cell f83a = row10.createCell(13);
            f83a.setCellStyle(style3);
            f83a.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(GPVALUTAtotVolVal, 2)));

            Cell f84a = row10.createCell(14);
            f84a.setCellStyle(style3);
            f84a.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(GPVALUTAtotVolPerc, 2)));

            //fine total
            //total VALUTA perc
            cntriga++;
            Row row11 = sheet.createRow((short) cntriga);

            Cell f52 = row11.createCell(2);
            f52.setCellStyle(style3left);
            f52.setCellValue("Total Currency %");

            Cell f53 = row11.createCell(3);
            f53.setCellStyle(style3);
            if (totVolPrevYear != 0) {
                f53.setCellValue(formatMysqltoDisplay(roundDoubleandFormat((100 * VALUTAtotVolPrevYear) / totVolPrevYear, 2)));
            } else {
                f53.setCellValue("0,00");
            }

            Cell f54 = row11.createCell(4);
            f54.setCellStyle(style3);
            if (totVolYear != 0) {
                f54.setCellValue(formatMysqltoDisplay(roundDoubleandFormat((100 * VALUTAtotVolYear) / totVolYear, 2)));
            } else {
                f54.setCellValue("0,00");
            }

            Cell f57 = row11.createCell(7);
            f57.setCellStyle(style3);
            if (qtyPrevYear != 0) {
                f57.setCellValue(formatMysqltoDisplay(roundDoubleandFormat((100 * VALUTAqtyPrevYear) / qtyPrevYear, 2)));
            } else {
                f57.setCellValue("0,00");
            }

            Cell f58 = row11.createCell(8);
            f58.setCellStyle(style3);
            if (qtyYear != 0) {
                f58.setCellValue(formatMysqltoDisplay(roundDoubleandFormat((100 * VALUTAqtyYear) / qtyYear, 2)));
            } else {
                f58.setCellValue("0,00");
            }

            Cell f11a = row11.createCell(11);
            f11a.setCellStyle(style3);
            if (GPtotVolPrevYear != 0) {
                f11a.setCellValue(formatMysqltoDisplay(roundDoubleandFormat((100 * GPVALUTAtotVolPrevYear) / GPtotVolPrevYear, 2)));
            } else {
                f11a.setCellValue("0,00");
            }

            Cell f12a = row11.createCell(12);
            f12a.setCellStyle(style3);
            if (GPtotVolYear != 0) {
                f12a.setCellValue(formatMysqltoDisplay(roundDoubleandFormat((100 * GPVALUTAtotVolYear) / GPtotVolYear, 2)));
            } else {
                f12a.setCellValue("0,00");
            }

            Cell f13a = row11.createCell(13);
            f13a.setCellStyle(style3);
            if (GPtotVolVal != 0) {
                f13a.setCellValue(formatMysqltoDisplay(roundDoubleandFormat((100 * GPVALUTAtotVolVal) / GPtotVolVal, 2)));
            } else {
                f13a.setCellValue("0,00");
            }

            Cell f14a = row11.createCell(14);
            f14a.setCellStyle(style3);
            if (GPtotVolPerc != 0) {
                f14a.setCellValue(formatMysqltoDisplay(roundDoubleandFormat((100 * GPVALUTAtotVolPerc) / GPtotVolPerc, 2)));
            } else {
                f14a.setCellValue("0,00");
            }

            //fine total perc
            //total
            cntriga++;
            Row row12 = sheet.createRow((short) cntriga);

            Cell f62 = row12.createCell(2);
            f62.setCellStyle(style3left);
            f62.setCellValue("Grand Total");

            Cell f63 = row12.createCell(3);
            f63.setCellStyle(style3);
            f63.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(totVolPrevYear, 2)));

            Cell f64 = row12.createCell(4);
            f64.setCellStyle(style3);
            f64.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(totVolYear, 2)));

            Cell f65 = row12.createCell(5);
            f65.setCellStyle(style3);
            f65.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(totVolVal, 2)));

            Cell f66 = row12.createCell(6);
            f66.setCellStyle(style3);
            if (totVolPrevYear > 0 && totVolYear > 0) {
                f66.setCellValue(formatMysqltoDisplay(roundDoubleandFormat((totVolVal / totVolPrevYear) * 100, 2)));
            } else {
                f66.setCellValue("0,00");
            }

            Cell f67 = row12.createCell(7);
            f67.setCellStyle(style3);
            f67.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(qtyPrevYear, 0)));

            Cell f68 = row12.createCell(8);
            f68.setCellStyle(style3);
            f68.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(qtyYear, 0)));

            Cell f69 = row12.createCell(9);
            f69.setCellStyle(style3);
            f69.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(qtyVal, 0)));

            Cell f70 = row12.createCell(10);
            f70.setCellStyle(style3);
            if (qtyPrevYear > 0 && qtyYear > 0) {
                f70.setCellValue(formatMysqltoDisplay(roundDoubleandFormat((qtyVal / qtyPrevYear) * 100, 2)));
            } else {
                f70.setCellValue("0,00");
            }

            Cell f65a = row12.createCell(11);
            f65a.setCellStyle(style3);
            f65a.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(GPtotVolPrevYear, 2)));

            Cell f65b = row12.createCell(12);
            f65b.setCellStyle(style3);
            f65b.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(GPtotVolYear, 2)));

            Cell f65c = row12.createCell(13);
            f65c.setCellStyle(style3);
            f65c.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(GPtotVolVal, 2)));

            Cell f65d = row12.createCell(14);
            f65d.setCellStyle(style3);
            f65d.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(GPtotVolPerc, 2)));

            //fine total
            if (lastTime) {
                //totale generale

                //total EURO
                cntriga++;
                cntriga++;
                cntriga++;
                cntriga++;
                Row row13 = sheet.createRow((short) cntriga);

                Cell f72 = row13.createCell(2);
                f72.setCellStyle(style3left);
                f72.setCellValue("Total Local Currency Amount");

                Cell f73 = row13.createCell(3);
                f73.setCellStyle(style3);
                f73.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(totaleEUROtotVolPrevYear, 2)));

                Cell f74 = row13.createCell(4);
                f74.setCellStyle(style3);
                f74.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(totaleEUROtotVolYear, 2)));

                Cell f75 = row13.createCell(5);
                f75.setCellStyle(style3);
                f75.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(totaleEUROtotVolVal, 2)));

                Cell f76 = row13.createCell(6);
                f76.setCellStyle(style3);
                if (totaleEUROtotVolPrevYear > 0 && totaleEUROtotVolYear > 0) {
                    f76.setCellValue(formatMysqltoDisplay(roundDoubleandFormat((totaleEUROtotVolVal / totaleEUROtotVolPrevYear) * 100, 2)));
                } else {
                    f76.setCellValue("0,00");
                }

                Cell f77 = row13.createCell(7);
                f77.setCellStyle(style3);
                f77.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(totaleEUROqtyPrevYear, 0)));

                Cell f78 = row13.createCell(8);
                f78.setCellStyle(style3);
                f78.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(totaleEUROqtyYear, 0)));

                Cell f79 = row13.createCell(9);
                f79.setCellStyle(style3);
                f79.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(totaleEUROqtyVal, 0)));

                Cell f80 = row13.createCell(10);
                f80.setCellStyle(style3);
                if (totaleEUROqtyPrevYear > 0 && totaleEUROqtyYear > 0) {
                    f80.setCellValue(formatMysqltoDisplay(roundDoubleandFormat((totaleEUROqtyVal / totaleEUROqtyPrevYear) * 100, 2)));
                } else {
                    f80.setCellValue("0,00");
                }

                Cell f8a = row13.createCell(11);
                f8a.setCellStyle(style3);
                f8a.setCellValue(formatMysqltoDisplay("0.00"));

                Cell f8b = row13.createCell(12);
                f8b.setCellStyle(style3);
                f8b.setCellValue(formatMysqltoDisplay("0.00"));

                Cell f8c = row13.createCell(13);
                f8c.setCellStyle(style3);
                f8c.setCellValue(formatMysqltoDisplay("0.00"));

                Cell f8d = row13.createCell(14);
                f8d.setCellStyle(style3);
                f8d.setCellValue(formatMysqltoDisplay("0.00"));

                //fine total
                //total EURO perc
                cntriga++;
                Row row14 = sheet.createRow((short) cntriga);

                Cell f82 = row14.createCell(2);
                f82.setCellStyle(style3left);
                f82.setCellValue("Total Local Currency %");

                Cell f83 = row14.createCell(3);
                f83.setCellStyle(style3);
                if (totaleVolPrevYear != 0) {
                    f83.setCellValue(formatMysqltoDisplay(roundDoubleandFormat((100 * totaleEUROtotVolPrevYear) / totaleVolPrevYear, 2)));
                } else {
                    f83.setCellValue("0,00");
                }

                Cell f84 = row14.createCell(4);
                f84.setCellStyle(style3);
                if (totaleVolYear != 0) {
                    f84.setCellValue(formatMysqltoDisplay(roundDoubleandFormat((100 * totaleEUROtotVolYear) / totaleVolYear, 2)));
                } else {
                    f84.setCellValue("0,00");
                }

                Cell f87 = row14.createCell(7);
                f87.setCellStyle(style3);
                if (totaleqtyPrevYear != 0) {
                    f87.setCellValue(formatMysqltoDisplay(roundDoubleandFormat((100 * totaleEUROqtyPrevYear) / totaleqtyPrevYear, 2)));
                } else {
                    f87.setCellValue("0,00");
                }

                Cell f88 = row14.createCell(8);
                f88.setCellStyle(style3);
                if (totaleqtyYear != 0) {
                    f88.setCellValue(formatMysqltoDisplay(roundDoubleandFormat((100 * totaleEUROqtyYear) / totaleqtyYear, 2)));
                } else {
                    f88.setCellValue("0,00");
                }

                
                
                Cell f8aa = row14.createCell(11);
                f8aa.setCellStyle(style3);
                f8aa.setCellValue(formatMysqltoDisplay("0.00"));

                Cell f8bb = row14.createCell(12);
                f8bb.setCellStyle(style3);
                f8bb.setCellValue(formatMysqltoDisplay("0.00"));

                Cell f8cc = row14.createCell(13);
                f8cc.setCellStyle(style3);
                f8cc.setCellValue(formatMysqltoDisplay("0.00"));

                Cell f8dd = row14.createCell(14);
                f8dd.setCellStyle(style3);
                f8dd.setCellValue(formatMysqltoDisplay("0.00"));

                //fine total perc
                //total VALUTA
                cntriga++;
                Row row15 = sheet.createRow((short) cntriga);

                Cell f442 = row15.createCell(2);
                f442.setCellStyle(style3left);
                f442.setCellValue("Total Currency Amount");

                Cell f443 = row15.createCell(3);
                f443.setCellStyle(style3);
                f443.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(totaleVALUTAtotVolPrevYear, 2)));

                Cell f444 = row15.createCell(4);
                f444.setCellStyle(style3);
                f444.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(totaleVALUTAtotVolYear, 2)));

                Cell f445 = row15.createCell(5);
                f445.setCellStyle(style3);
                f445.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(totaleVALUTAtotVolVal, 2)));

                Cell f446 = row15.createCell(6);
                f446.setCellStyle(style3);
                if (totaleVALUTAtotVolPrevYear > 0 && totaleVALUTAtotVolYear > 0) {
                    f446.setCellValue(formatMysqltoDisplay(roundDoubleandFormat((totaleVALUTAtotVolVal / totaleVALUTAtotVolPrevYear) * 100, 2)));
                } else {
                    f446.setCellValue("0,00");
                }

                Cell f447 = row15.createCell(7);
                f447.setCellStyle(style3);
                f447.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(totaleVALUTAqtyPrevYear, 0)));

                Cell f448 = row15.createCell(8);
                f448.setCellStyle(style3);
                f448.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(totaleVALUTAqtyYear, 0)));

                Cell f449 = row15.createCell(9);
                f449.setCellStyle(style3);
                f449.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(totaleVALUTAqtyVal, 0)));

                Cell f450 = row15.createCell(10);
                f450.setCellStyle(style3);
                if (totaleVALUTAqtyPrevYear > 0 && totaleVALUTAqtyYear > 0) {
                    f450.setCellValue(formatMysqltoDisplay(roundDoubleandFormat((totaleVALUTAqtyVal / totaleVALUTAqtyPrevYear) * 100, 2)));
                } else {
                    f450.setCellValue("0,00");
                }

                
                Cell fa = row15.createCell(11);
                fa.setCellStyle(style3);
                fa.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(GPtotaleVALUTAtotVolPrevYear, 2)));

                Cell fb = row15.createCell(12);
                fb.setCellStyle(style3);
                fb.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(GPtotaleVALUTAtotVolYear, 2)));

                Cell fc = row15.createCell(13);
                fc.setCellStyle(style3);
                fc.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(GPtotaleVALUTAtotVolVal, 2)));

                Cell fd = row15.createCell(14);
                fd.setCellStyle(style3);
                fd.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(GPtotaleVALUTAtotVolPerc, 2)));
                
                
                //fine total
                //total VALUTA perc
                cntriga++;
                Row row16 = sheet.createRow((short) cntriga);

                Cell f332 = row16.createCell(2);
                f332.setCellStyle(style3left);
                f332.setCellValue("Total Currency %");

                Cell f333 = row16.createCell(3);
                f333.setCellStyle(style3);
                if (totaleVolPrevYear != 0) {
                    f333.setCellValue(formatMysqltoDisplay(roundDoubleandFormat((100 * totaleVALUTAtotVolPrevYear) / totaleVolPrevYear, 2)));
                } else {
                    f333.setCellValue("0,00");
                }

                Cell f334 = row16.createCell(4);
                f334.setCellStyle(style3);
                if (totaleVolYear != 0) {
                    f334.setCellValue(formatMysqltoDisplay(roundDoubleandFormat((100 * totaleVALUTAtotVolYear) / totaleVolYear, 2)));
                } else {
                    f334.setCellValue("0,00");
                }

                Cell f337 = row16.createCell(7);
                f337.setCellStyle(style3);
                if (totaleqtyPrevYear != 0) {
                    f337.setCellValue(formatMysqltoDisplay(roundDoubleandFormat((100 * totaleVALUTAqtyPrevYear) / totaleqtyPrevYear, 2)));
                } else {
                    f337.setCellValue("0,00");
                }

                Cell f338 = row16.createCell(8);
                f338.setCellStyle(style3);
                if (totaleqtyYear != 0) {
                    f338.setCellValue(formatMysqltoDisplay(roundDoubleandFormat((100 * totaleVALUTAqtyYear) / totaleqtyYear, 2)));
                } else {
                    f338.setCellValue("0,00");
                }
                
                
                
                
                
                Cell faq = row16.createCell(11);
                faq.setCellStyle(style3);
                if (GPtotaleVolPrevYear != 0) {
                    faq.setCellValue(formatMysqltoDisplay(roundDoubleandFormat((100 * GPtotaleVALUTAtotVolPrevYear) / GPtotaleVolPrevYear, 2)));
                } else {
                    faq.setCellValue("0,00");
                }

                Cell fbq = row16.createCell(12);
                fbq.setCellStyle(style3);
                if (GPtotaleVolYear != 0) {
                    fbq.setCellValue(formatMysqltoDisplay(roundDoubleandFormat((100 * GPtotaleVALUTAtotVolYear) / GPtotaleVolYear, 2)));
                } else {
                    fbq.setCellValue("0,00");
                }

                Cell fcq = row16.createCell(13);
                fcq.setCellStyle(style3);
                if (GPtotaleVolVal != 0) {
                    fcq.setCellValue(formatMysqltoDisplay(roundDoubleandFormat((100 * GPtotaleVALUTAtotVolVal) / GPtotaleVolVal, 2)));
                } else {
                    fcq.setCellValue("0,00");
                }

                Cell fdq = row16.createCell(14);
                fdq.setCellStyle(style3);
                if (GPtotaleVolPerc != 0) {
                    fdq.setCellValue(formatMysqltoDisplay(roundDoubleandFormat((100 * GPtotaleVALUTAtotVolPerc) / GPtotaleVolPerc, 2)));
                } else {
                    fdq.setCellValue("0,00");
                }
                
                
                

                //fine total perc
                //total
                cntriga++;
                Row row17 = sheet.createRow((short) cntriga);

                Cell f432 = row17.createCell(2);
                f432.setCellStyle(style3left);
                f432.setCellValue("Grand Total");

                Cell f433 = row17.createCell(3);
                f433.setCellStyle(style3);
                f433.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(totaleVolPrevYear, 2)));

                Cell f434 = row17.createCell(4);
                f434.setCellStyle(style3);
                f434.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(totaleVolYear, 2)));

                Cell f435 = row17.createCell(5);
                f435.setCellStyle(style3);
                f435.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(totaleVolVal, 2)));

                Cell f436 = row17.createCell(6);
                f436.setCellStyle(style3);
                if (totaleVolPrevYear > 0 && totaleVolYear > 0) {
                    f436.setCellValue(formatMysqltoDisplay(roundDoubleandFormat((totaleVolVal / totaleVolPrevYear) * 100, 2)));
                } else {
                    f436.setCellValue("0,00");
                }

                Cell f437 = row17.createCell(7);
                f437.setCellStyle(style3);
                f437.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(totaleqtyPrevYear, 0)));

                Cell f438 = row17.createCell(8);
                f438.setCellStyle(style3);
                f438.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(totaleqtyYear, 0)));

                Cell f439 = row17.createCell(9);
                f439.setCellStyle(style3);
                f439.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(totaleqtyVal, 0)));

                Cell f440 = row17.createCell(10);
                f440.setCellStyle(style3);
                if (totaleqtyPrevYear > 0 && totaleqtyYear > 0) {
                    f440.setCellValue(formatMysqltoDisplay(roundDoubleandFormat((totaleqtyVal / totaleqtyPrevYear) * 100, 2)));
                } else {
                    f440.setCellValue("0,00");
                }
                
                
                
                
                Cell f439a = row17.createCell(11);
                f439a.setCellStyle(style3);
                f439a.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(GPtotaleVolPrevYear, 2)));
                
                Cell f439b = row17.createCell(12);
                f439b.setCellStyle(style3);
                f439b.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(GPtotaleVolYear, 2)));
                
                Cell f439c = row17.createCell(13);
                f439c.setCellStyle(style3);
                f439c.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(GPtotaleVolVal, 2)));
                
                Cell f439d = row17.createCell(14);
                f439d.setCellStyle(style3);
                f439d.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(GPtotaleVolPerc, 2)));
                

                //fine total
            }

        } catch (Exception ex) {
            insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
        }

        cntriga++;
        cntriga++;
        cntriga++;
        cntriga++;
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
     * @param filiali
     * @param br
     * @return
     */
    public String mainexcel(String path, String d3, String d4,
            String data1, String data2,
            ArrayList<String> alcolonne, ArrayList<String> filiali, ArrayList<Branch> br) {
        try {
            File pdffile = new File(path + generaId(50) + "C_VatRefund_BranchCausal.xls");
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet("C_VatRefund_BranchCausal");

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

            HSSFCellStyle style3center = (HSSFCellStyle) workbook.createCellStyle();
            style3center.setFont(font3);
            style3center.setAlignment(CENTER);
            style3center.setBorderTop(THIN);
            style3center.setBorderBottom(THIN);

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

            C_FreeTax_BranchCausal nctl = new C_FreeTax_BranchCausal();

            C_FreeTax_BranchCausal_value pdf = new C_FreeTax_BranchCausal_value();

            boolean firstTime = true;
            boolean lastTime = false;

            Db_Master dbm = new Db_Master();
            int nriga = 1;
            boolean find = false;

            for (int i = 0; i < filiali.size(); i++) {

                ArrayList<C_FreeTax_BranchCausal_value> dati = dbm.list_C_FreeTax_BranchCausal_value(filiali.get(i), data1, data2, br);
                if (dati.size() > 0) {
                    find = true;
                }
                pdf.setDataDa(d3);
                pdf.setDataA(d4);
                pdf.setDati(dati);
                pdf.setId_filiale(filiali.get(i));
                pdf.setDe_filiale(formatBankBranchReport(filiali.get(i), "BR", null, br));

                if (i == filiali.size() - 1) {
                    lastTime = true;
                }

                nriga = nctl.receiptexcel(pdf, alcolonne, firstTime, lastTime, sheet, nriga, style1, style2, style3, style4, style3left, style4left, style3center);

                firstTime = false;

            }

            if (find) {

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
