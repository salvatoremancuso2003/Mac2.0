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
import static rc.so.util.Engine.insertTR;
import static rc.so.util.Utility.ff;
import static rc.so.util.Utility.generaId;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import static java.lang.Integer.parseInt;
import static java.lang.Thread.currentThread;
import java.util.ArrayList;
import static org.apache.commons.codec.binary.Base64.encodeBase64;
import static org.apache.commons.io.FileUtils.readFileToByteArray;

/**
 *
 * @author fplacanica
 */
public class PosBankAccount {

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

    /**
     *
     */
    public static float[] columnWidths3 = new float[]{60f, 60f,30f, 30f,30f, 30f,30f, 30f,30f, 30f};

    /**
     *
     */
    public static float[] columnWidthsBank = new float[]{60f, 60f,60f, 30f,30f, 30f,30f, 30f,30f, 30f};

    /**
     *
     */
    public static float[] columnWidthsBank1 = new float[]{60f, 60f,30f, 30f,30f, 30f,30f, 30f,30f, 30f};
    final String intestazionePdf = "Pos - Bank Account";
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
    public PosBankAccount() {

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
     * @param siq
     * @param array
     * @param colonne
     * @param datereport
     * @return
     */
    public String receipt(String path,PosBankAccount_value siq, ArrayList<String[]> array, ArrayList<String> colonne,String datereport) {

//        String outputfile = "PosBankAccount.pdf";

        columnWidths2 = new float[colonne.size()];

        for (int c = 0; c < colonne.size(); c++) {
            columnWidths2[c] = 10f;
        }

        try {
              File pdf = new File(path + generaId(50) + "PosBankAccount.pdf");
            Document document = new Document(A4, 20, 20, 20, 20);
            try(OutputStream ou = new FileOutputStream(pdf)){
            PdfWriter wr = getInstance(document, ou);
            document.open();

            PdfPTable table = new PdfPTable(2);
            table.setWidths(columnWidths0);
            table.setWidthPercentage(100);


            Phrase phrase1 = new Phrase();
            phrase1.add(new Chunk(intestazionePdf+  " "+ datereport, f3_bold));
            PdfPCell cell1 = new PdfPCell(phrase1);
            cell1.setBorder(NO_BORDER);

            Paragraph pa1 = new Paragraph(new Phrase("", f2_normal));
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
                cellt1.setFixedHeight(15f);
                cellt1.setBackgroundColor(LIGHT_GRAY);
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
            
            
            table3 = new PdfPTable(10);
            table3.setWidths(columnWidths3);
            table3.setWidthPercentage(100);

            //colonne
            Phrase phraset1 = new Phrase();
            phraset1.add(new Chunk("", f3_normal));
            PdfPCell cellt1 = new PdfPCell(phraset1);
            cellt1.setHorizontalAlignment(ALIGN_RIGHT);
            cellt1.setBorder(BOTTOM);
            cellt1.setFixedHeight(15f);
            cellt1.setBackgroundColor(LIGHT_GRAY);
            //cellt1.setBorder(Rectangle.BOTTOM);
            cellt1.setBorderWidth(0.7f);
            table3.addCell(cellt1);
            
           
            
            phraset1 = new Phrase();
            phraset1.add(new Chunk("", f3_normal));
            cellt1 = new PdfPCell(phraset1);
            cellt1.setHorizontalAlignment(ALIGN_RIGHT);
            cellt1.setBorder(BOTTOM);
            cellt1.setFixedHeight(10f);
            cellt1.setBackgroundColor(LIGHT_GRAY);
            //cellt1.setBorder(Rectangle.BOTTOM);
            cellt1.setBorderWidth(0.7f);
            table3.addCell(cellt1);

            phraset1 = new Phrase();
            phraset1.add(new Chunk("N. trans", f3_normal));
            cellt1 = new PdfPCell(phraset1);
            cellt1.setHorizontalAlignment(ALIGN_RIGHT);
            cellt1.setBorder(BOTTOM);
            cellt1.setFixedHeight(10f);
            cellt1.setBackgroundColor(LIGHT_GRAY);
            //cellt1.setBorder(Rectangle.BOTTOM);
            cellt1.setBorderWidth(0.7f);
            table3.addCell(cellt1);

            phraset1 = new Phrase();
            phraset1.add(new Chunk("Amount", f3_normal));
            cellt1 = new PdfPCell(phraset1);
            cellt1.setHorizontalAlignment(ALIGN_RIGHT);
            cellt1.setBorder(BOTTOM);
            cellt1.setFixedHeight(10f);
            cellt1.setBackgroundColor(LIGHT_GRAY);
            //cellt1.setBorder(Rectangle.BOTTOM);
            cellt1.setBorderWidth(0.7f);
            table3.addCell(cellt1);

            phraset1 = new Phrase();
            phraset1.add(new Chunk("N. trans", f3_normal));
            cellt1 = new PdfPCell(phraset1);
            cellt1.setHorizontalAlignment(ALIGN_RIGHT);
            cellt1.setBorder(BOTTOM);
            cellt1.setFixedHeight(10f);
            cellt1.setBackgroundColor(LIGHT_GRAY);
            //cellt1.setBorder(Rectangle.BOTTOM);
            cellt1.setBorderWidth(0.7f);
            table3.addCell(cellt1);
            
            phraset1 = new Phrase();
            phraset1.add(new Chunk("Amount", f3_normal));
            cellt1 = new PdfPCell(phraset1);
            cellt1.setHorizontalAlignment(ALIGN_RIGHT);
            cellt1.setBorder(BOTTOM);
            cellt1.setFixedHeight(10f);
            cellt1.setBackgroundColor(LIGHT_GRAY);
            //cellt1.setBorder(Rectangle.BOTTOM);
            cellt1.setBorderWidth(0.7f);
            table3.addCell(cellt1);
            
                phraset1 = new Phrase();
            phraset1.add(new Chunk("N. trans", f3_normal));
            cellt1 = new PdfPCell(phraset1);
            cellt1.setHorizontalAlignment(ALIGN_RIGHT);
            cellt1.setBorder(BOTTOM);
            cellt1.setFixedHeight(10f);
            cellt1.setBackgroundColor(LIGHT_GRAY);
            //cellt1.setBorder(Rectangle.BOTTOM);
            cellt1.setBorderWidth(0.7f);
            table3.addCell(cellt1);
            
            phraset1 = new Phrase();
            phraset1.add(new Chunk("Amount", f3_normal));
            cellt1 = new PdfPCell(phraset1);
            cellt1.setHorizontalAlignment(ALIGN_RIGHT);
            cellt1.setBorder(BOTTOM);
            cellt1.setFixedHeight(10f);
            cellt1.setBackgroundColor(LIGHT_GRAY);
            //cellt1.setBorder(Rectangle.BOTTOM);
            cellt1.setBorderWidth(0.7f);
            table3.addCell(cellt1);
            
                phraset1 = new Phrase();
            phraset1.add(new Chunk("N. trans", f3_normal));
            cellt1 = new PdfPCell(phraset1);
            cellt1.setHorizontalAlignment(ALIGN_RIGHT);
            cellt1.setBorder(BOTTOM);
            cellt1.setFixedHeight(10f);
            cellt1.setBackgroundColor(LIGHT_GRAY);
            //cellt1.setBorder(Rectangle.BOTTOM);
            cellt1.setBorderWidth(0.7f);
            table3.addCell(cellt1);
            
            phraset1 = new Phrase();
            phraset1.add(new Chunk("Amount", f3_normal));
            cellt1 = new PdfPCell(phraset1);
            cellt1.setHorizontalAlignment(ALIGN_RIGHT);
            cellt1.setBorder(BOTTOM);
            cellt1.setFixedHeight(10f);
            cellt1.setBackgroundColor(LIGHT_GRAY);
            //cellt1.setBorder(Rectangle.BOTTOM);
            cellt1.setBorderWidth(0.7f);
            table3.addCell(cellt1);
            
            document.add(table3);
            

            LineSeparator sep = new LineSeparator();
            sep.setOffset(-2);
            sep.setLineWidth((float) 0.5);

            //  document.add(table2);
            //  document.add(sep);
            ArrayList<PosBankAccount_value> dati = siq.getDati();
            Phrase phraset;
            PdfPCell cellt;

            table3 = new PdfPTable(10);
            table3.setWidths(columnWidths3);
            table3.setWidthPercentage(100);

            ArrayList<String[]> tot = new ArrayList<>();
            
            int totNTransCashAd=0;
            int totNTransCC=0;
            int totNTransBank=0;
            int totNTransTot=0;
            
            float totAmountCashAd=0;
            float totAmountCC=0;
            float totAmountBank=0;
            float totAmountTot=0;

            for (int i = 0; i < dati.size(); i++) {
               PosBankAccount_value temp = (PosBankAccount_value) dati.get(i);

                //popolamento tabella1
                
            phraset = new Phrase();
            phraset.add(new Chunk(temp.getDate(), f3_normal));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setBorder(BOTTOM);
            table3.addCell(cellt);

            phraset = new Phrase();
            phraset.add(new Chunk(temp.getPos(), f3_normal));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setBorder(BOTTOM);
            table3.addCell(cellt);
            
            
            phraset = new Phrase();
            phraset.add(new Chunk(temp.getnTransCA(), f3_normal));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setBorder(BOTTOM);
            table3.addCell(cellt);
            
            phraset = new Phrase();
            phraset.add(new Chunk(temp.getAmountCA(), f3_normal));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setBorder(BOTTOM);
            table3.addCell(cellt);
            
            phraset = new Phrase();
            phraset.add(new Chunk(temp.getnTransCC(), f3_normal));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setBorder(BOTTOM);
            table3.addCell(cellt);
            
            phraset = new Phrase();
            phraset.add(new Chunk(temp.getAmountCC(), f3_normal));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setBorder(BOTTOM);
            table3.addCell(cellt);
            
            phraset = new Phrase();
            phraset.add(new Chunk(temp.getnTransBank(), f3_normal));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setBorder(BOTTOM);
            table3.addCell(cellt);
            
            phraset = new Phrase();
            phraset.add(new Chunk(temp.getAmountBank(), f3_normal));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setBorder(BOTTOM);
            table3.addCell(cellt);
            
            phraset = new Phrase();
            phraset.add(new Chunk(parseInt(temp.getnTransCA())+parseInt(temp.getnTransCC())+parseInt(temp.getnTransBank())+"", f3_normal));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setBorder(BOTTOM);
            table3.addCell(cellt);
            
            phraset = new Phrase();
            phraset.add(new Chunk(ff(temp.getAmountCA())+ff(temp.getAmountCC())+ff(temp.getAmountBank())+"", f3_normal));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setBorder(BOTTOM);
            table3.addCell(cellt);
                
            totNTransCashAd+=parseInt(temp.getnTransCA());
            totNTransCC+=parseInt(temp.getnTransCC());
            totNTransBank+=parseInt(temp.getnTransBank());
            totNTransTot+=parseInt(temp.getnTransCA())+parseInt(temp.getnTransCC())+parseInt(temp.getnTransBank());
            
            totAmountCashAd+=ff(temp.getAmountCA());
            totAmountCC+=ff(temp.getAmountCC());
            totAmountBank+=ff(temp.getAmountBank());
            totAmountTot+=ff(temp.getAmountCA())+ff(temp.getAmountCC())+ff(temp.getAmountBank());

            }

            document.add(table3);
//          

            // linea totali
            table3 = new PdfPTable(10);
            table3.setWidths(columnWidths3);
            table3.setWidthPercentage(100);

            //linea totale per categoria
            LineSeparator ls = new LineSeparator();
            ls.setLineWidth(0.7f);
            document.add(ls);
            
           

            phraset = new Phrase();
            phraset.add(new Chunk("Total", f4_bold));
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
            phraset.add(new Chunk(totNTransCashAd+"", f4_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setBorder(BOTTOM);
            table3.addCell(cellt);
            
            phraset = new Phrase();
            phraset.add(new Chunk(totAmountCashAd+"", f4_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setBorder(BOTTOM);
            table3.addCell(cellt);
            
            
                    
                    
            
            phraset = new Phrase();
            phraset.add(new Chunk(totNTransCC+"", f4_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setBorder(BOTTOM);
            table3.addCell(cellt);
            
            phraset = new Phrase();
            phraset.add(new Chunk(totAmountCC+"", f4_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setBorder(BOTTOM);
            table3.addCell(cellt);

            phraset = new Phrase();
            phraset.add(new Chunk(totNTransBank+"", f4_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setBorder(BOTTOM);
            table3.addCell(cellt);
            
            phraset = new Phrase();
            phraset.add(new Chunk(totAmountBank+"", f4_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setBorder(BOTTOM);
            table3.addCell(cellt);
            
            phraset = new Phrase();
            phraset.add(new Chunk(totNTransTot+"", f4_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setBorder(BOTTOM);
            table3.addCell(cellt);
            
            phraset = new Phrase();
            phraset.add(new Chunk(totAmountTot+"", f4_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setBorder(BOTTOM);
            table3.addCell(cellt);
            

            document.add(table3);
            ls.setLineWidth(0.7f);
            document.add(ls);
            //fine totali
            
            
             vuoto.setFont(f3_normal);
            document.add(vuoto);
            
             //tabella bankaccount
             
             table3 = new PdfPTable(10);
            table3.setWidths(columnWidthsBank1);
            table3.setWidthPercentage(100);

            //colonne
            
            phraset1 = new Phrase();
            phraset1.add(new Chunk("Data", f4_bold));
            cellt1 = new PdfPCell(phraset1);
            cellt1.setHorizontalAlignment(ALIGN_RIGHT);
            cellt1.setBorder(TOP);
            cellt1.setBackgroundColor(LIGHT_GRAY);
            //cellt1.setBorder(Rectangle.BOTTOM);
            cellt1.setBorderWidth(0.7f);
            table3.addCell(cellt1);
            
            
            phraset1 = new Phrase();
            phraset1.add(new Chunk("08 - Bank Account", f4_bold));
            cellt1 = new PdfPCell(phraset1);
            cellt1.setHorizontalAlignment(ALIGN_RIGHT);
            cellt1.setBorder(TOP);
            cellt1.setBackgroundColor(LIGHT_GRAY);
            //cellt1.setBorder(Rectangle.BOTTOM);
            cellt1.setBorderWidth(0.7f);
            table3.addCell(cellt1);

            phraset1 = new Phrase();
            phraset1.add(new Chunk("Total", f4_bold));
            cellt1 = new PdfPCell(phraset1);
            cellt1.setHorizontalAlignment(ALIGN_RIGHT);
            cellt1.setBorder(TOP);
            cellt1.setBackgroundColor(LIGHT_GRAY);
            //cellt1.setBorder(Rectangle.BOTTOM);
            cellt1.setBorderWidth(0.7f);
            table3.addCell(cellt1);
            
              phraset1 = new Phrase();
            phraset1.add(new Chunk("", f4_bold));
            cellt1 = new PdfPCell(phraset1);
            cellt1.setHorizontalAlignment(ALIGN_RIGHT);
            cellt1.setBorder(TOP);
            cellt1.setBackgroundColor(LIGHT_GRAY);
            //cellt1.setBorder(Rectangle.BOTTOM);
            cellt1.setBorderWidth(0.7f);
            table3.addCell(cellt1);
            
              phraset1 = new Phrase();
            phraset1.add(new Chunk("", f4_bold));
            cellt1 = new PdfPCell(phraset1);
            cellt1.setHorizontalAlignment(ALIGN_RIGHT);
            cellt1.setBorder(NO_BORDER);
            table3.addCell(cellt1);
            
              phraset1 = new Phrase();
            phraset1.add(new Chunk("", f4_bold));
            cellt1 = new PdfPCell(phraset1);
            cellt1.setHorizontalAlignment(ALIGN_RIGHT);
            cellt1.setBorder(NO_BORDER);
            table3.addCell(cellt1);
            
              phraset1 = new Phrase();
            phraset1.add(new Chunk("", f4_bold));
            cellt1 = new PdfPCell(phraset1);
            cellt1.setHorizontalAlignment(ALIGN_RIGHT);
            cellt1.setBorder(NO_BORDER);
            table3.addCell(cellt1);
            
                phraset1 = new Phrase();
            phraset1.add(new Chunk("", f4_bold));
            cellt1 = new PdfPCell(phraset1);
            cellt1.setHorizontalAlignment(ALIGN_RIGHT);
            cellt1.setBorder(NO_BORDER);
            table3.addCell(cellt1);
            
                phraset1 = new Phrase();
            phraset1.add(new Chunk("", f4_bold));
            cellt1 = new PdfPCell(phraset1);
            cellt1.setHorizontalAlignment(ALIGN_RIGHT);
            cellt1.setBorder(NO_BORDER);
            table3.addCell(cellt1);
            
                phraset1 = new Phrase();
            phraset1.add(new Chunk("", f4_bold));
            cellt1 = new PdfPCell(phraset1);
            cellt1.setHorizontalAlignment(ALIGN_RIGHT);
            cellt1.setBorder(NO_BORDER);
            table3.addCell(cellt1);

            
            document.add(table3);
            
            // 2 riga intestazione
            
                 //2 tabella
            
            table3 = new PdfPTable(10);
            table3.setWidths(columnWidthsBank1);
            table3.setWidthPercentage(100);

            //colonne
            
            phraset1 = new Phrase();
            phraset1.add(new Chunk("", f3_normal));
            cellt1 = new PdfPCell(phraset1);
            cellt1.setHorizontalAlignment(ALIGN_RIGHT);
            cellt1.setBorder(BOTTOM);
            cellt1.setBackgroundColor(LIGHT_GRAY);
            //cellt1.setBorder(Rectangle.BOTTOM);
            cellt1.setBorderWidth(0.7f);
            table3.addCell(cellt1);
            
            
            phraset1 = new Phrase();
            phraset1.add(new Chunk("", f3_normal));
            cellt1 = new PdfPCell(phraset1);
            cellt1.setHorizontalAlignment(ALIGN_RIGHT);
            cellt1.setBorder(BOTTOM);
            cellt1.setBackgroundColor(LIGHT_GRAY);
            //cellt1.setBorder(Rectangle.BOTTOM);
            cellt1.setBorderWidth(0.7f);
            table3.addCell(cellt1);

            phraset1 = new Phrase();
            phraset1.add(new Chunk("N. trans", f3_normal));
            cellt1 = new PdfPCell(phraset1);
            cellt1.setHorizontalAlignment(ALIGN_RIGHT);
            cellt1.setBorder(BOTTOM);
            cellt1.setBackgroundColor(LIGHT_GRAY);
            //cellt1.setBorder(Rectangle.BOTTOM);
            cellt1.setBorderWidth(0.7f);
            table3.addCell(cellt1);

            phraset1 = new Phrase();
            phraset1.add(new Chunk("Amount", f3_normal));
            cellt1 = new PdfPCell(phraset1);
            cellt1.setHorizontalAlignment(ALIGN_RIGHT);
            cellt1.setBorder(BOTTOM);
            cellt1.setBackgroundColor(LIGHT_GRAY);
            //cellt1.setBorder(Rectangle.BOTTOM);
            cellt1.setBorderWidth(0.7f);
            table3.addCell(cellt1);
            
            phraset1 = new Phrase();
            phraset1.add(new Chunk("", f4_bold));
            cellt1 = new PdfPCell(phraset1);
            cellt1.setHorizontalAlignment(ALIGN_RIGHT);
            cellt1.setBorder(NO_BORDER);
            table3.addCell(cellt1);
            
            phraset1 = new Phrase();
            phraset1.add(new Chunk("", f4_bold));
            cellt1 = new PdfPCell(phraset1);
            cellt1.setHorizontalAlignment(ALIGN_RIGHT);
            cellt1.setBorder(NO_BORDER);
            table3.addCell(cellt1);
            
            phraset1 = new Phrase();
            phraset1.add(new Chunk("", f4_bold));
            cellt1 = new PdfPCell(phraset1);
            cellt1.setHorizontalAlignment(ALIGN_RIGHT);
            cellt1.setBorder(NO_BORDER);
            table3.addCell(cellt1);
            
            phraset1 = new Phrase();
            phraset1.add(new Chunk("", f4_bold));
            cellt1 = new PdfPCell(phraset1);
            cellt1.setHorizontalAlignment(ALIGN_RIGHT);
            cellt1.setBorder(NO_BORDER);
            table3.addCell(cellt1);
            
             phraset1 = new Phrase();
            phraset1.add(new Chunk("", f4_bold));
            cellt1 = new PdfPCell(phraset1);
            cellt1.setHorizontalAlignment(ALIGN_RIGHT);
            cellt1.setBorder(NO_BORDER);
            table3.addCell(cellt1);
            
             phraset1 = new Phrase();
            phraset1.add(new Chunk("", f4_bold));
            cellt1 = new PdfPCell(phraset1);
            cellt1.setHorizontalAlignment(ALIGN_RIGHT);
            cellt1.setBorder(NO_BORDER);
            table3.addCell(cellt1);

           
            
            document.add(table3);
            
            //fine intestazione tabella bank account
                        
            sep = new LineSeparator();
            sep.setOffset(-2);
            sep.setLineWidth((float) 0.5);


            table3 = new PdfPTable(10);
            table3.setWidths(columnWidthsBank1);
            table3.setWidthPercentage(100);

            
            //tot predisp.
            
           
            int bankTotNTransTot=0;
            float bankTotAmountTot=0;

            //popolamento tabella 2
            
            for(int i=0; i<array.size(); i++){
                String[] s=array.get(i);
              
            phraset = new Phrase();
            phraset.add(new Chunk(s[0], f3_normal));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setBorder(BOTTOM);
            table3.addCell(cellt);

            phraset = new Phrase();
            phraset.add(new Chunk(s[1], f3_normal));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setBorder(BOTTOM);
            table3.addCell(cellt);

            phraset = new Phrase();
            phraset.add(new Chunk(s[2], f3_normal));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setBorder(BOTTOM);
            table3.addCell(cellt);
           
            phraset = new Phrase();
            phraset.add(new Chunk(s[3], f4_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setBorder(BOTTOM);
            table3.addCell(cellt);
            
            phraset1 = new Phrase();
            phraset1.add(new Chunk("", f4_bold));
            cellt1 = new PdfPCell(phraset1);
            cellt1.setHorizontalAlignment(ALIGN_RIGHT);
            cellt1.setBorder(NO_BORDER);
            table3.addCell(cellt1);
            
            phraset1 = new Phrase();
            phraset1.add(new Chunk("", f4_bold));
            cellt1 = new PdfPCell(phraset1);
            cellt1.setHorizontalAlignment(ALIGN_RIGHT);
            cellt1.setBorder(NO_BORDER);
            table3.addCell(cellt1);
            
            phraset1 = new Phrase();
            phraset1.add(new Chunk("", f4_bold));
            cellt1 = new PdfPCell(phraset1);
            cellt1.setHorizontalAlignment(ALIGN_RIGHT);
            cellt1.setBorder(NO_BORDER);
            table3.addCell(cellt1);
            
            phraset1 = new Phrase();
            phraset1.add(new Chunk("", f4_bold));
            cellt1 = new PdfPCell(phraset1);
            cellt1.setHorizontalAlignment(ALIGN_RIGHT);
            cellt1.setBorder(NO_BORDER);
            table3.addCell(cellt1);
            
            phraset1 = new Phrase();
            phraset1.add(new Chunk("", f4_bold));
            cellt1 = new PdfPCell(phraset1);
            cellt1.setHorizontalAlignment(ALIGN_RIGHT);
            cellt1.setBorder(NO_BORDER);
            table3.addCell(cellt1);
            
            phraset1 = new Phrase();
            phraset1.add(new Chunk("", f4_bold));
            cellt1 = new PdfPCell(phraset1);
            cellt1.setHorizontalAlignment(ALIGN_RIGHT);
            cellt1.setBorder(NO_BORDER);
            table3.addCell(cellt1);
            
            bankTotNTransTot+=parseInt(s[2]);
            bankTotAmountTot+=ff(s[3]);
            
            }
            
             //fine 2 riga
            
            //totali
            phraset = new Phrase();
            phraset.add(new Chunk("Total", f4_bold));
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
            phraset.add(new Chunk(bankTotNTransTot+"", f4_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setBorder(BOTTOM);
            table3.addCell(cellt);

            phraset = new Phrase();
            phraset.add(new Chunk(bankTotAmountTot+"", f4_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setBorder(BOTTOM);
            table3.addCell(cellt);
            
            phraset1 = new Phrase();
            phraset1.add(new Chunk("", f4_bold));
            cellt1 = new PdfPCell(phraset1);
            cellt1.setHorizontalAlignment(ALIGN_RIGHT);
            cellt1.setBorder(NO_BORDER);
            table3.addCell(cellt1);
            
            phraset1 = new Phrase();
            phraset1.add(new Chunk("", f4_bold));
            cellt1 = new PdfPCell(phraset1);
            cellt1.setHorizontalAlignment(ALIGN_RIGHT);
            cellt1.setBorder(NO_BORDER);
            table3.addCell(cellt1);
            
            phraset1 = new Phrase();
            phraset1.add(new Chunk("", f4_bold));
            cellt1 = new PdfPCell(phraset1);
            cellt1.setHorizontalAlignment(ALIGN_RIGHT);
            cellt1.setBorder(NO_BORDER);
            table3.addCell(cellt1);
            
            phraset1 = new Phrase();
            phraset1.add(new Chunk("", f4_bold));
            cellt1 = new PdfPCell(phraset1);
            cellt1.setHorizontalAlignment(ALIGN_RIGHT);
            cellt1.setBorder(NO_BORDER);
            table3.addCell(cellt1);
            
            phraset1 = new Phrase();
            phraset1.add(new Chunk("", f4_bold));
            cellt1 = new PdfPCell(phraset1);
            cellt1.setHorizontalAlignment(ALIGN_RIGHT);
            cellt1.setBorder(NO_BORDER);
            table3.addCell(cellt1);
            
            phraset1 = new Phrase();
            phraset1.add(new Chunk("", f4_bold));
            cellt1 = new PdfPCell(phraset1);
            cellt1.setHorizontalAlignment(ALIGN_RIGHT);
            cellt1.setBorder(NO_BORDER);
            table3.addCell(cellt1);

            
            //fine tot
            
            document.add(table3);
             
             //fine tabella bankaccount

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
