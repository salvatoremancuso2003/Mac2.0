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
import static com.itextpdf.text.Element.ALIGN_BOTTOM;
import static com.itextpdf.text.Element.ALIGN_LEFT;
import static com.itextpdf.text.Element.ALIGN_MIDDLE;
import static com.itextpdf.text.Element.ALIGN_RIGHT;
import com.itextpdf.text.Font;
import static com.itextpdf.text.Font.BOLD;
import static com.itextpdf.text.Font.BOLDITALIC;
import static com.itextpdf.text.Font.NORMAL;
import static com.itextpdf.text.FontFactory.getFont;
import static com.itextpdf.text.PageSize.A4;
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
import static rc.so.util.Utility.fd;
import static rc.so.util.Utility.formatMysqltoDisplay;
import static rc.so.util.Utility.generaId;
import static rc.so.util.Utility.roundDouble;
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
import static org.apache.poi.ss.usermodel.BorderStyle.THIN;
import org.apache.poi.ss.usermodel.Cell;
import static org.apache.poi.ss.usermodel.HorizontalAlignment.LEFT;
import static org.apache.poi.ss.usermodel.HorizontalAlignment.RIGHT;
import org.apache.poi.ss.usermodel.Row;

/**
 *
 * @author srotella
 */
public class RegisterSellMonthly {

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
    public static final float[] columnWidths2 = new float[]{15f, 10f, 10f, 10f, 10f, 10f, 10f, 10f, 10f, 10f};

    /**
     *
     */
    public static final float[] columnWidths3 = new float[]{15f, 10f, 10f, 10f, 10f, 10f, 10f, 10f, 10f, 10f};
    final String intestazionePdf = "Register Sales ";
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

    Font f0_bold, f1_bold, f2_bold, f1_normal, f2_normal, f3_bold, f5_bold, f5_normal;

    /**
     * Costructor
     */
    public RegisterSellMonthly() {

        cellavuota.setBorder(NO_BORDER);

        this.f0_bold = getFont(HELVETICA, WINANSI, 14.04f, BOLD);
        this.f1_bold = getFont(HELVETICA, WINANSI, 9.96f, BOLD);
        this.f2_bold = getFont(HELVETICA, WINANSI, 8f, BOLD);
        this.f3_bold = getFont(HELVETICA, WINANSI, 8f, BOLDITALIC);
//        this.f4_bold = FontFactory.getFont(BaseFont.HELVETICA, BaseFont.WINANSI, 6.96f, Font.BOLD);
        this.f1_normal = getFont(HELVETICA, WINANSI, 9.96f, NORMAL);
        this.f2_normal = getFont(HELVETICA, WINANSI, 8f, NORMAL);
//        this.f3_normal = FontFactory.getFont(BaseFont.HELVETICA, BaseFont.WINANSI, 6.96f, Font.NORMAL);
        this.f5_normal = getFont(HELVETICA, WINANSI, 6f, NORMAL);
        this.f5_bold = getFont(HELVETICA, WINANSI, 6f, BOLD);

    }

    /**
     *
     * @param document
     */
    public void scriviIntestazioneColonne(Document document) {

        try {
            PdfPTable table2 = new PdfPTable(10);
            table2.setWidths(columnWidths3);
            table2.setWidthPercentage(100);

            Phrase phraset1 = new Phrase();
            phraset1.add(new Chunk(" ", f5_bold));
            PdfPCell cellt1 = new PdfPCell(phraset1);
            cellt1.setHorizontalAlignment(ALIGN_LEFT);
            cellt1.setVerticalAlignment(ALIGN_MIDDLE);
            cellt1.setBorder(BOTTOM | TOP);
            cellt1.setFixedHeight(20f);
            cellt1.setBackgroundColor(LIGHT_GRAY);

            Phrase phraset2 = new Phrase();
            phraset2.add(new Chunk("Currency", f5_bold));
            PdfPCell cellt2 = new PdfPCell(phraset2);
            cellt2.setHorizontalAlignment(ALIGN_LEFT);
            cellt2.setVerticalAlignment(ALIGN_MIDDLE);
            cellt2.setBorder(BOTTOM | TOP);
            cellt2.setFixedHeight(20f);
            cellt2.setBackgroundColor(LIGHT_GRAY);

            Phrase phraset5 = new Phrase();
            phraset5.add(new Chunk("Trans. to branches", f5_bold));
            PdfPCell cellt5 = new PdfPCell(phraset5);
            cellt5.setHorizontalAlignment(ALIGN_RIGHT);
            cellt5.setVerticalAlignment(ALIGN_MIDDLE);
            cellt5.setBorder(BOTTOM | TOP);
            cellt5.setFixedHeight(20f);
            cellt5.setBackgroundColor(LIGHT_GRAY);

            Phrase phraset4 = new Phrase();
            phraset4.add(new Chunk("Equivalent", f5_bold));
            PdfPCell cellt4 = new PdfPCell(phraset4);
            cellt4.setHorizontalAlignment(ALIGN_RIGHT);
            cellt4.setVerticalAlignment(ALIGN_MIDDLE);
            cellt4.setBorder(BOTTOM | TOP);
            cellt4.setFixedHeight(20f);
            cellt4.setBackgroundColor(LIGHT_GRAY);

            Phrase phraset7 = new Phrase();
            phraset7.add(new Chunk("Sales to client", f5_bold));
            PdfPCell cellt7 = new PdfPCell(phraset7);
            cellt7.setHorizontalAlignment(ALIGN_RIGHT);
            cellt7.setVerticalAlignment(ALIGN_MIDDLE);
            cellt7.setBorder(BOTTOM | TOP);
            cellt7.setFixedHeight(20f);
            cellt7.setBackgroundColor(LIGHT_GRAY);

            Phrase phraset8 = new Phrase();
            phraset8.add(new Chunk("Equivalent", f5_bold));
            PdfPCell cellt8 = new PdfPCell(phraset8);
            cellt8.setHorizontalAlignment(ALIGN_RIGHT);
            cellt8.setVerticalAlignment(ALIGN_MIDDLE);
            cellt8.setBorder(BOTTOM | TOP);
            cellt8.setFixedHeight(20f);
            cellt8.setBackgroundColor(LIGHT_GRAY);

            Phrase phraset9 = new Phrase();
            phraset9.add(new Chunk("Transf. to bank", f5_bold));
            PdfPCell cellt9 = new PdfPCell(phraset9);
            cellt9.setHorizontalAlignment(ALIGN_RIGHT);
            cellt9.setVerticalAlignment(ALIGN_MIDDLE);
            cellt9.setBorder(BOTTOM | TOP);
            cellt9.setFixedHeight(20f);
            cellt9.setBackgroundColor(LIGHT_GRAY);

            Phrase phraset11 = new Phrase();
            phraset11.add(new Chunk("Equivalent", f5_bold));
            PdfPCell cellt11 = new PdfPCell(phraset11);
            cellt11.setHorizontalAlignment(ALIGN_RIGHT);
            cellt11.setVerticalAlignment(ALIGN_MIDDLE);
            cellt11.setBorder(BOTTOM | TOP);
            cellt11.setFixedHeight(20f);
            cellt11.setBackgroundColor(LIGHT_GRAY);

            Phrase phraset12 = new Phrase();
            phraset12.add(new Chunk("Total Currency", f5_bold));
            PdfPCell cellt12 = new PdfPCell(phraset12);
            cellt12.setHorizontalAlignment(ALIGN_RIGHT);
            cellt12.setVerticalAlignment(ALIGN_MIDDLE);
            cellt12.setBorder(BOTTOM | TOP);
            cellt12.setFixedHeight(20f);
            cellt12.setBackgroundColor(LIGHT_GRAY);

            Phrase phraset13 = new Phrase();
            phraset13.add(new Chunk("Total", f5_bold));
            PdfPCell cellt13 = new PdfPCell(phraset13);
            cellt13.setHorizontalAlignment(ALIGN_RIGHT);
            cellt13.setVerticalAlignment(ALIGN_MIDDLE);
            cellt13.setBorder(BOTTOM | TOP);
            cellt13.setFixedHeight(20f);
            cellt13.setBackgroundColor(LIGHT_GRAY);

            table2.addCell(cellt1);
            table2.addCell(cellt2);
            table2.addCell(cellt5);
            table2.addCell(cellt4);
            table2.addCell(cellt7);
            table2.addCell(cellt8);
            table2.addCell(cellt9);
            table2.addCell(cellt11);
            table2.addCell(cellt12);
            table2.addCell(cellt13);

            document.add(table2);

        } catch (DocumentException ex) {
            insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
        }
    }

    /**
     *
     * @param path
     * @param siq
     * @param datareport1
     * @param datereport2
     * @return
     */
    public String receipt(String path, RegisterSellMonthly_value siq, String datareport1,String datereport2) {

        // String outputfile = "RegistroMensileUICVendite.pdf";
        try {
            File pdf = new File(path + generaId(50) + "RegistroMensileUICVendite.pdf");
            Document document = new Document(A4, 20, 20, 20, 20);
            try(OutputStream ou = new FileOutputStream(pdf)){
            PdfWriter wr = getInstance(document, ou);
            document.open();

            PdfPTable table = new PdfPTable(2);
            table.setWidths(columnWidths0);
            table.setWidthPercentage(100);

            Phrase phrase1 = new Phrase();
            phrase1.add(new Chunk(intestazionePdf +" From "+ datareport1 + " to "+ datereport2, f3_bold));
            PdfPCell cell1 = new PdfPCell(phrase1);
            cell1.setBorder(NO_BORDER);

            Phrase phrase3 = new Phrase();
            phrase3.add(new Chunk("\n " + siq.getId_filiale() + " " + siq.getDe_filiale(), f5_normal));
            PdfPCell cell3 = new PdfPCell(phrase3);
            cell3.setBorder(NO_BORDER);

            table.addCell(cell1);
            table.addCell(cellavuota);
            table.addCell(cell3);
            table.addCell(cellavuota);
            document.add(table);
            vuoto.setFont(f5_normal);
            document.add(cellavuota);

            document.add(vuoto);

            scriviIntestazioneColonne(document);

            //Popolo la tabella
            PdfPTable table3, table6;
            ArrayList<RegisterSellMonthly_value> dati = siq.getDati();
            Phrase phraset;
            PdfPCell cellt;

            ArrayList<String> totavenditeaclienteparziale = new ArrayList<>();
            ArrayList<String> totcontrovaloreparziale = new ArrayList<>();

            ArrayList<String> totalevenditeaclientiresidenti = new ArrayList<>();
            ArrayList<String> totalecontrovaloreresidenti = new ArrayList<>();

            ArrayList<String> totalevenditeaclientinonresidenti = new ArrayList<>();
            ArrayList<String> totalecontrovalorenonresidenti = new ArrayList<>();

            double venditeaclientitot = 0;
            double controvaloretot = 0;

            table3 = new PdfPTable(10);
            table3.setWidths(columnWidths2);
            table3.setWidthPercentage(100);

            double totalesaldovaluta = 0, totalesaldo = 0;

            double totaletrasfadip2 = 0;
            double totaletrasfabanche2 = 0;
            double totalegeneralesaldo = 0;

            double tottranstobranches = 0, tottransfertobank = 0,totequivalenbranch=0;

            for (int i = 0; i < dati.size(); i++) {

                RegisterSellMonthly_value actual =  dati.get(i);
                RegisterSellMonthly_value prossimo =  dati.get(i);

                if (i < dati.size() - 1) {
                    prossimo =  dati.get(i + 1);
                }

                
                totavenditeaclienteparziale.add(actual.getVenditaclientiSenzaFormattazione());
                totcontrovaloreparziale.add(actual.getControvaloreSenzaFormattazione());

                if (!actual.getTipocliente().equalsIgnoreCase("Client non-resident")) {
                    totalevenditeaclientiresidenti.add(actual.getVenditaclientiSenzaFormattazione());
                    totalecontrovaloreresidenti.add(actual.getControvaloreSenzaFormattazione());
                } else {
                    totalevenditeaclientinonresidenti.add(actual.getVenditaclientiSenzaFormattazione());
                    totalecontrovalorenonresidenti.add(actual.getControvaloreSenzaFormattazione());
                }

                phraset = new Phrase();
                phraset.add(new Chunk(actual.getTipocliente(), f5_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_LEFT);
                cellt.setVerticalAlignment(ALIGN_BOTTOM);
                cellt.setBorder(NO_BORDER);
                table3.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(actual.getValuta(), f5_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_LEFT);
                cellt.setVerticalAlignment(ALIGN_BOTTOM);
                cellt.setBorder(NO_BORDER);
                table3.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk("", f5_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setVerticalAlignment(ALIGN_BOTTOM);
                cellt.setBorder(NO_BORDER);
                table3.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk("", f5_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setVerticalAlignment(ALIGN_BOTTOM);
                cellt.setBorder(NO_BORDER);
                table3.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(actual.getVenditaclienti()), f5_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setVerticalAlignment(ALIGN_BOTTOM);
                cellt.setBorder(NO_BORDER);
                table3.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(actual.getControvalore()), f5_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setVerticalAlignment(ALIGN_BOTTOM);
                cellt.setBorder(NO_BORDER);
                table3.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk("", f5_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setVerticalAlignment(ALIGN_BOTTOM);
                cellt.setBorder(NO_BORDER);
                table3.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk("", f5_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setVerticalAlignment(ALIGN_BOTTOM);
                cellt.setBorder(NO_BORDER);
                table3.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(actual.getSaldovaluta()), f5_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setVerticalAlignment(ALIGN_BOTTOM);
                cellt.setBorder(NO_BORDER);
                table3.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk("", f5_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setVerticalAlignment(ALIGN_BOTTOM);
                cellt.setBorder(NO_BORDER);
                table3.addCell(cellt);

                if (!(prossimo.getValuta().equals(actual.getValuta())) || i == dati.size() - 1) {
                    //scrivo la riga del totale ma prima agigungo la tabella che ho creato

                    //calcolo il totale
                    for (int j = 0; j < totavenditeaclienteparziale.size(); j++) {
                        venditeaclientitot += fd(((String) totavenditeaclienteparziale.get(j)));
                        controvaloretot += fd(((String) totcontrovaloreparziale.get(j)));
                    }
                    
                    venditeaclientitot = roundDouble(venditeaclientitot, 2);
                    controvaloretot = roundDouble(controvaloretot, 2);

                    phraset = new Phrase();
                    phraset.add(new Chunk("Total", f5_bold));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_LEFT);
                    cellt.setVerticalAlignment(ALIGN_BOTTOM);
                    cellt.setBorder(NO_BORDER);
                    table3.addCell(cellt);

                    phraset = new Phrase();
                    phraset.add(new Chunk(actual.getValuta(), f5_bold));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_LEFT);
                    cellt.setVerticalAlignment(ALIGN_BOTTOM);
                    cellt.setBorder(NO_BORDER);
                    table3.addCell(cellt);

                    phraset = new Phrase();
                    phraset.add(new Chunk(formatMysqltoDisplay(actual.getTrasfadip1()), f5_bold));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setVerticalAlignment(ALIGN_BOTTOM);
                    cellt.setBorder(NO_BORDER);
                    table3.addCell(cellt);

                    tottranstobranches += fd((actual.getTrasfadip1SenzaFormattazione()));
                    
                    tottranstobranches=roundDouble(tottranstobranches, 2);

                    phraset = new Phrase();
                    phraset.add(new Chunk(formatMysqltoDisplay(actual.getTrasfadip2()), f5_bold));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setVerticalAlignment(ALIGN_BOTTOM);
                    cellt.setBorder(NO_BORDER);
                    table3.addCell(cellt);

                    phraset = new Phrase();
                    phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(venditeaclientitot,2)), f5_bold));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setVerticalAlignment(ALIGN_BOTTOM);
                    cellt.setBorder(NO_BORDER);
                    table3.addCell(cellt);

                    phraset = new Phrase();
                    phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(controvaloretot,2)), f5_bold));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setVerticalAlignment(ALIGN_BOTTOM);
                    cellt.setBorder(NO_BORDER);
                    table3.addCell(cellt);

                    phraset = new Phrase();
                    phraset.add(new Chunk(formatMysqltoDisplay(actual.getTrasfabache1()), f5_bold));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setVerticalAlignment(ALIGN_BOTTOM);
                    cellt.setBorder(NO_BORDER);
                    table3.addCell(cellt);
                    tottransfertobank += fd((actual.getTrasfabache1()));
                    
                    tottransfertobank=roundDouble(tottransfertobank, 2);

                    phraset = new Phrase();
                    phraset.add(new Chunk(formatMysqltoDisplay(actual.getTrasfabacnhe2()), f5_bold));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setVerticalAlignment(ALIGN_BOTTOM);
                    cellt.setBorder(NO_BORDER);
                    table3.addCell(cellt);

                    totalesaldovaluta += venditeaclientitot + fd((actual.getTrasfadip1SenzaFormattazione()))+ fd((actual.getTrasfabache1SenzaFormattazione()));
                    totalesaldo += controvaloretot + fd((actual.getTrasfadip2SenzaFormattazione()))+ fd((actual.getTrasfabacnhe2SenzaFormattazione()));

                    totaletrasfabanche2 += fd((actual.getTrasfabacnhe2SenzaFormattazione()));
                    totaletrasfadip2 += fd((actual.getTrasfadip2SenzaFormattazione()));
                    
                    
                    
                    
                    totequivalenbranch+=totaletrasfabanche2;
                    
                    
                    totalesaldovaluta=roundDouble(totalesaldovaluta, 2);
                    totalesaldo=roundDouble(totalesaldo, 2);
                    totaletrasfabanche2=roundDouble(totaletrasfabanche2, 2);
                    totaletrasfadip2=roundDouble(totaletrasfadip2, 2);
                    totequivalenbranch=roundDouble(totequivalenbranch, 2);
                    
                    phraset = new Phrase();
                    phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(totalesaldovaluta,2)), f5_bold));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setVerticalAlignment(ALIGN_BOTTOM);
                    cellt.setBorder(NO_BORDER);
                    table3.addCell(cellt);

                    phraset = new Phrase();
                    phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(totalesaldo,2)), f5_bold));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setVerticalAlignment(ALIGN_BOTTOM);
                    cellt.setBorder(NO_BORDER);
                    table3.addCell(cellt);
                    
                    document.add(table3);

                    LineSeparator ls = new LineSeparator();
                    ls.setLineWidth(0.7f);
                    ls.setOffset(-2);

                    document.add(ls);

                    table3 = new PdfPTable(10);
                    table3.setWidths(columnWidths2);
                    table3.setWidthPercentage(100);

                    venditeaclientitot = 0;
                    controvaloretot = 0;
                    totavenditeaclienteparziale.removeAll(totavenditeaclienteparziale);
                    totcontrovaloreparziale.removeAll(totcontrovaloreparziale);

                    totalegeneralesaldo += totalesaldo;
                    
                    totalegeneralesaldo=roundDouble(totalegeneralesaldo, 2);

                    totalesaldovaluta = 0;
                    totalesaldo = 0;
                }

            }

            //qui scrivo l'ultima riga con la somma totale del controvalore
            table6 = new PdfPTable(10);
            table6.setWidths(columnWidths2);
            table6.setWidthPercentage(100);

           

            phraset = new Phrase();
            phraset.add(new Chunk("Total", f5_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_LEFT);
            cellt.setVerticalAlignment(ALIGN_BOTTOM);
            cellt.setBorder(NO_BORDER);
            table6.addCell(cellt);
            
             table6.addCell(cellavuota);

            phraset = new Phrase();
            phraset.add(new Chunk("", f5_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setVerticalAlignment(ALIGN_BOTTOM);
            cellt.setBorder(NO_BORDER);
            table6.addCell(cellt);

            phraset = new Phrase();
            
            phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(totaletrasfadip2,2)), f5_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setVerticalAlignment(ALIGN_BOTTOM);
            cellt.setBorder(NO_BORDER);
            table6.addCell(cellt);

            double venditaaclientetotresidenti = 0;
            double controvaloretotaleresidenti = 0;
            for (int j = 0; j < totalevenditeaclientiresidenti.size(); j++) {
                venditaaclientetotresidenti += fd(((String) totalevenditeaclientiresidenti.get(j)));
                controvaloretotaleresidenti += fd(((String) totalecontrovaloreresidenti.get(j)));
            }

            double venditaaclientetotnonresidenti = 0;
            double controvaloretotalenonresidenti = 0;
            for (int j = 0; j < totalevenditeaclientinonresidenti.size(); j++) {
                venditaaclientetotnonresidenti += fd(((String) totalevenditeaclientinonresidenti.get(j)));
                controvaloretotalenonresidenti += fd(((String) totalecontrovalorenonresidenti.get(j)));
            }
            
            controvaloretotalenonresidenti=roundDouble(controvaloretotalenonresidenti, 2);
            controvaloretotaleresidenti=roundDouble(controvaloretotaleresidenti, 2);
            

            phraset = new Phrase();
            phraset.add(new Chunk("Res.", f5_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setVerticalAlignment(ALIGN_BOTTOM);
            cellt.setBorder(NO_BORDER);
            table6.addCell(cellt);

            phraset = new Phrase();
            phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(controvaloretotaleresidenti,2)), f5_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setVerticalAlignment(ALIGN_BOTTOM);
            cellt.setBorder(NO_BORDER);
            table6.addCell(cellt);

            phraset = new Phrase();
            phraset.add(new Chunk("", f5_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setVerticalAlignment(ALIGN_BOTTOM);
            cellt.setBorder(NO_BORDER);
            table6.addCell(cellt);

            phraset = new Phrase();
            phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(totaletrasfabanche2,2)), f5_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setVerticalAlignment(ALIGN_BOTTOM);
            cellt.setBorder(NO_BORDER);
            table6.addCell(cellt);

            phraset = new Phrase();
            phraset.add(new Chunk("", f5_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setVerticalAlignment(ALIGN_BOTTOM);
            cellt.setBorder(NO_BORDER);
            table6.addCell(cellt);

            phraset = new Phrase();
            phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(totalegeneralesaldo,2)), f5_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setVerticalAlignment(ALIGN_BOTTOM);
            cellt.setBorder(NO_BORDER);
            table6.addCell(cellt);

            LineSeparator ls = new LineSeparator();

            //   document.add(ls);
            document.add(table6);

            table6 = new PdfPTable(10);
            table6.setWidths(columnWidths2);
            table6.setWidthPercentage(100);

            table6.addCell(cellavuota);

            phraset = new Phrase();
            phraset.add(new Chunk("", f5_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setVerticalAlignment(ALIGN_BOTTOM);
            cellt.setBorder(NO_BORDER);
            table6.addCell(cellt);

            phraset = new Phrase();
            phraset.add(new Chunk("", f5_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setVerticalAlignment(ALIGN_BOTTOM);
            cellt.setBorder(NO_BORDER);
            table6.addCell(cellt);

            phraset = new Phrase();
            phraset.add(new Chunk("", f5_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setVerticalAlignment(ALIGN_BOTTOM);
            cellt.setBorder(NO_BORDER);
            table6.addCell(cellt);

            phraset = new Phrase();
            phraset.add(new Chunk("Non Res.", f5_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setVerticalAlignment(ALIGN_BOTTOM);
            cellt.setBorder(BOTTOM);
            table6.addCell(cellt);

            phraset = new Phrase();
            phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(controvaloretotalenonresidenti,2)), f5_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setVerticalAlignment(ALIGN_BOTTOM);
            cellt.setBorder(BOTTOM);
            table6.addCell(cellt);

            phraset = new Phrase();
            phraset.add(new Chunk("", f5_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setVerticalAlignment(ALIGN_BOTTOM);
            cellt.setBorder(NO_BORDER);
            table6.addCell(cellt);

            phraset = new Phrase();
            phraset.add(new Chunk("", f5_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setVerticalAlignment(ALIGN_BOTTOM);
            cellt.setBorder(NO_BORDER);
            table6.addCell(cellt);

            phraset = new Phrase();
            phraset.add(new Chunk("", f5_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setVerticalAlignment(ALIGN_BOTTOM);
            cellt.setBorder(NO_BORDER);
            table6.addCell(cellt);

            phraset = new Phrase();
            phraset.add(new Chunk("", f5_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setVerticalAlignment(ALIGN_BOTTOM);
            cellt.setBorder(NO_BORDER);
            table6.addCell(cellt);

            document.add(table6);

            table6 = new PdfPTable(10);
            table6.setWidths(columnWidths2);
            table6.setWidthPercentage(100);

            table6.addCell(cellavuota);

            phraset = new Phrase();
            phraset.add(new Chunk("", f5_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setVerticalAlignment(ALIGN_BOTTOM);
            cellt.setBorder(NO_BORDER);
            table6.addCell(cellt);

            phraset = new Phrase();
            phraset.add(new Chunk("", f5_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setVerticalAlignment(ALIGN_BOTTOM);
            cellt.setBorder(NO_BORDER);
            table6.addCell(cellt);

            phraset = new Phrase();
            phraset.add(new Chunk("", f5_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setVerticalAlignment(ALIGN_BOTTOM);
            cellt.setBorder(NO_BORDER);
            table6.addCell(cellt);

            phraset = new Phrase();
            phraset.add(new Chunk("Total", f5_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setVerticalAlignment(ALIGN_BOTTOM);
            cellt.setBorder(NO_BORDER);
            table6.addCell(cellt);

            phraset = new Phrase();
            phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(controvaloretotaleresidenti + controvaloretotalenonresidenti,2)), f5_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setVerticalAlignment(ALIGN_BOTTOM);
            cellt.setBorder(NO_BORDER);
            table6.addCell(cellt);

            phraset = new Phrase();
            phraset.add(new Chunk("", f5_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setVerticalAlignment(ALIGN_BOTTOM);
            cellt.setBorder(NO_BORDER);
            table6.addCell(cellt);

            phraset = new Phrase();
            phraset.add(new Chunk("", f5_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setVerticalAlignment(ALIGN_BOTTOM);
            cellt.setBorder(NO_BORDER);
            table6.addCell(cellt);

            phraset = new Phrase();
            phraset.add(new Chunk("", f5_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setVerticalAlignment(ALIGN_BOTTOM);
            cellt.setBorder(NO_BORDER);
            table6.addCell(cellt);

            phraset = new Phrase();
            phraset.add(new Chunk("", f5_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setVerticalAlignment(ALIGN_BOTTOM);
            cellt.setBorder(NO_BORDER);
            table6.addCell(cellt);

            document.add(table6);

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
     * @param datareport1
     * @param datereport2
     * @return
     */
    public String receiptexcel(String path, RegisterSellMonthly_value siq, String datareport1,String datereport2) {

        // String outputfile = "RegistroMensileUICVendite.pdf";
        try {
            File pdf = new File(path + generaId(50) + "RegistroMensileUICAcquisti.xls");
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet("RegisterSellMonthly");
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

            Row rowP = sheet.createRow((short) 1);

            Cell cl = rowP.createCell(1);
            cl.setCellStyle(style1);
            cl.setCellValue(intestazionePdf+ " from " + datareport1 + " to "+ datereport2);

//            Cell cl2 = rowP.createCell(6);
//            cl2.setCellStyle(style2);
//            cl2.setCellValue(datareport);

            Row row = sheet.createRow((short) 3);
            row.createCell(1).setCellValue(siq.getId_filiale() + " " + siq.getDe_filiale());

            Row row6 = sheet.createRow((short) 5);

            Cell f2 = row6.createCell(2);
            f2.setCellStyle(style3left);
            f2.setCellValue("Currency");

            Cell f3 = row6.createCell(3);
            f3.setCellStyle(style3);
            f3.setCellValue("Trans. to branches");

            Cell f4 = row6.createCell(4);
            f4.setCellStyle(style3);
            f4.setCellValue("Equivalent");

            Cell f5 = row6.createCell(5);
            f5.setCellStyle(style3);
            f5.setCellValue("Sales to client");

            Cell f6 = row6.createCell(6);
            f6.setCellStyle(style3);
            f6.setCellValue("Equivalent");

            Cell f7 = row6.createCell(7);
            f7.setCellStyle(style3);
            f7.setCellValue("Transf. to bank");

            Cell f8 = row6.createCell(8);
            f8.setCellStyle(style3);
            f8.setCellValue("Equivalent");

            Cell f9 = row6.createCell(9);
            f9.setCellStyle(style3);
            f9.setCellValue("Total Currency");

            Cell f10 = row6.createCell(10);
            f10.setCellStyle(style3);
            f10.setCellValue("Total");

            ArrayList<RegisterSellMonthly_value> dati = siq.getDati();

            ArrayList<String> totavenditeaclienteparziale = new ArrayList<>();
            ArrayList<String> totcontrovaloreparziale = new ArrayList<>();

            ArrayList<String> totalevenditeaclientiresidenti = new ArrayList<>();
            ArrayList<String> totalecontrovaloreresidenti = new ArrayList<>();

            ArrayList<String> totalevenditeaclientinonresidenti = new ArrayList<>();
            ArrayList<String> totalecontrovalorenonresidenti = new ArrayList<>();

            double venditeaclientitot = 0;
            double controvaloretot = 0;

            double totalesaldovaluta = 0, totalesaldo = 0;

            double totaletrasfadip2 = 0;
            double totaletrasfabanche2 = 0;
            double totalegeneralesaldo = 0;

            double tottranstobranches = 0, tottransfertobank = 0;
            int cntriga = 7;

            for (int i = 0; i < dati.size(); i++) {
                cntriga++;
                Row row7 = sheet.createRow((short) cntriga);

                RegisterSellMonthly_value actual =  dati.get(i);
                RegisterSellMonthly_value prossimo = dati.get(i);

                if (i < dati.size() - 1) {
                    prossimo =  dati.get(i + 1);
                }

                totavenditeaclienteparziale.add(actual.getVenditaclientiSenzaFormattazione());
                totcontrovaloreparziale.add(actual.getControvaloreSenzaFormattazione());

                if (!actual.getTipocliente().equalsIgnoreCase("Client non-resident")) {

                    totalevenditeaclientiresidenti.add(actual.getVenditaclientiSenzaFormattazione());
                    totalecontrovaloreresidenti.add(actual.getControvaloreSenzaFormattazione());
                } else {
                    totalevenditeaclientinonresidenti.add(actual.getVenditaclientiSenzaFormattazione());
                    totalecontrovalorenonresidenti.add(actual.getControvaloreSenzaFormattazione());
                }

                Cell f11 = row7.createCell(1);
                f11.setCellStyle(style4left);
                f11.setCellValue(actual.getTipocliente());

                Cell f12 = row7.createCell(2);
                f12.setCellStyle(style4left);
                f12.setCellValue(actual.getValuta());

                Cell f13 = row7.createCell(5);
                f13.setCellStyle(style4);
                f13.setCellValue(actual.getVenditaclienti());

                Cell f14 = row7.createCell(6);
                f14.setCellStyle(style4);
                f14.setCellValue(actual.getControvalore());

                Cell f15 = row7.createCell(9);
                f15.setCellStyle(style4);
                f15.setCellValue(actual.getSaldovaluta());

                cntriga++;

                if (!(prossimo.getValuta().equals(actual.getValuta())) || i == dati.size() - 1) {
                    //scrivo la riga del totale ma prima agigungo la tabella che ho creato

                    //calcolo il totale
                    for (int j = 0; j < totavenditeaclienteparziale.size(); j++) {
                        venditeaclientitot += fd(((String) totavenditeaclienteparziale.get(j)));
                        controvaloretot += fd(((String) totcontrovaloreparziale.get(j)));
                    }

                    venditeaclientitot=roundDouble(venditeaclientitot, 2);
                    controvaloretot=roundDouble(controvaloretot, 2);
                    
                    cntriga++;

                    Row row8 = sheet.createRow((short) cntriga);

                    Cell f16 = row8.createCell(1);
                    f16.setCellStyle(style3left);
                    f16.setCellValue("Total");

                    Cell f17 = row8.createCell(2);
                    f17.setCellStyle(style3left);
                    f17.setCellValue(actual.getValuta());

                    Cell f18 = row8.createCell(3);
                    f18.setCellStyle(style3);
                    f18.setCellValue(actual.getTrasfadip1());

                    Cell f19 = row8.createCell(4);
                    f19.setCellStyle(style3);
                    f19.setCellValue(actual.getTrasfadip2());

                    tottranstobranches += fd((actual.getTrasfadip1SenzaFormattazione()));
                    
                    tottranstobranches=roundDouble(tottranstobranches, 2);

                    Cell f20 = row8.createCell(5);
                    f20.setCellStyle(style3);
                    f20.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(venditeaclientitot,2)));

                    Cell f21 = row8.createCell(6);
                    f21.setCellStyle(style3);
                    f21.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(controvaloretot,2)));

                    Cell f22 = row8.createCell(7);
                    f22.setCellStyle(style3);
                    f22.setCellValue(actual.getTrasfabache1());

                   

                    tottransfertobank += fd((actual.getTrasfabache1SenzaFormattazione()));

   totalesaldovaluta += venditeaclientitot + fd((actual.getTrasfadip1SenzaFormattazione()))+ fd((actual.getTrasfabache1SenzaFormattazione()));
                    totalesaldo += controvaloretot + fd((actual.getTrasfadip2SenzaFormattazione()));

                    totaletrasfabanche2 += fd((actual.getTrasfabacnhe2SenzaFormattazione()));
                    totaletrasfadip2 += fd((actual.getTrasfadip2SenzaFormattazione()));
                    
                    
                    
                    tottransfertobank=roundDouble(tottransfertobank, 2);
                     totalesaldovaluta=roundDouble(totalesaldovaluta, 2);
                    totalesaldo=roundDouble(totalesaldo, 2);
                    totaletrasfabanche2=roundDouble(totaletrasfabanche2, 2);
                    totaletrasfadip2=roundDouble(totaletrasfadip2, 2);
               //     totequivalenbranch=Utility.roundDouble(totequivalenbranch, 2);

                     Cell f23 = row8.createCell(8);
                    f23.setCellStyle(style3);
                    f23.setCellValue(actual.getTrasfabacnhe2());
                    
                    Cell f24 = row8.createCell(9);
                    f24.setCellStyle(style3);
                    f24.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(totalesaldovaluta,2)));

                    Cell f25 = row8.createCell(10);
                    f25.setCellStyle(style3);
                    f25.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(totalesaldo,2)));

                    venditeaclientitot = 0;
                    controvaloretot = 0;
                    totavenditeaclienteparziale.removeAll(totavenditeaclienteparziale);
                    totcontrovaloreparziale.removeAll(totcontrovaloreparziale);

                    totalegeneralesaldo += totalesaldo;
                    
                    totalegeneralesaldo=roundDouble(totalegeneralesaldo, 2);

                    totalesaldovaluta = 0;
                    totalesaldo = 0;

                    cntriga++;

                }

            }

            cntriga++;
            cntriga++;

            Row row9 = sheet.createRow((short) cntriga);

            //qui scrivo l'ultima riga con la somma totale del controvalore
            Cell f26 = row9.createCell(1);
            f26.setCellStyle(style3left);
            f26.setCellValue("Total");

//            Cell f27 = row9.createCell(3);
//            f27.setCellStyle(style3);
//            f27.setCellValue(formatMysqltoDisplay(Utility.roundDoubleandFormat(tottranstobranches,2)));

            Cell f28 = row9.createCell(4);
            f28.setCellStyle(style3);
            f28.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(totaletrasfadip2,2)));

            double venditaaclientetotresidenti = 0;
            double controvaloretotaleresidenti = 0;
            for (int j = 0; j < totalevenditeaclientiresidenti.size(); j++) {
                venditaaclientetotresidenti += fd(((String) totalevenditeaclientiresidenti.get(j)));
                controvaloretotaleresidenti += fd(((String) totalecontrovaloreresidenti.get(j)));
            }

            double venditaaclientetotnonresidenti = 0;
            double controvaloretotalenonresidenti = 0;
            for (int j = 0; j < totalevenditeaclientinonresidenti.size(); j++) {
                venditaaclientetotnonresidenti += fd(((String) totalevenditeaclientinonresidenti.get(j)));
                controvaloretotalenonresidenti += fd(((String) totalecontrovalorenonresidenti.get(j)));
            }
            
            
            controvaloretotalenonresidenti=roundDouble(controvaloretotalenonresidenti, 2);
            controvaloretotaleresidenti=roundDouble(controvaloretotaleresidenti, 2);

            Cell f29 = row9.createCell(5);
            f29.setCellStyle(style3);
            f29.setCellValue("Res.");

            Cell f30 = row9.createCell(6);
            f30.setCellStyle(style3);
            f30.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(controvaloretotaleresidenti,2)));

//            Cell f31 = row9.createCell(7);
//            f31.setCellStyle(style3);
//            f31.setCellValue(formatMysqltoDisplay(Utility.roundDoubleandFormat(tottransfertobank,2)));

            Cell f32 = row9.createCell(8);
            f32.setCellStyle(style3);
            f32.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(totaletrasfabanche2,2)));

            Cell f33 = row9.createCell(10);
            f33.setCellStyle(style3);
            f33.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(totalegeneralesaldo,2)));

            cntriga++;
            cntriga++;

            Row row10 = sheet.createRow((short) cntriga);

            Cell f34 = row10.createCell(5);
            f34.setCellStyle(style3);
            f34.setCellValue("Non Res.");

            Cell f35 = row10.createCell(6);
            f35.setCellStyle(style3);
            f35.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(controvaloretotalenonresidenti,2)));

            cntriga++;
            cntriga++;

            Row row11 = sheet.createRow((short) cntriga);

            Cell f36 = row11.createCell(5);
            f36.setCellStyle(style3);
            f36.setCellValue("Total");

            Cell f37 = row11.createCell(6);
            f37.setCellStyle(style3);
            f37.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(controvaloretotaleresidenti + controvaloretotalenonresidenti,2)));

//            
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
