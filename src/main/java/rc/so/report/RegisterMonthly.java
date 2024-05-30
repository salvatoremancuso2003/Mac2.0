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
public class RegisterMonthly {

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
    public static final float[] columnWidths2 = new float[]{15f, 10f, 10f, 10f, 10f, 10f, 10f, 10f, 15f};

    /**
     *
     */
    public static final float[] columnWidths3 = new float[]{15f, 10f, 10f, 10f, 10f, 10f, 10f, 10f, 15f};
    final String intestazionePdf = "Register Total ";
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

    Font f0_bold, f1_bold, f2_bold, f1_normal, f2_normal, f3_normal, f3_bold, f4_bold, f5_bold, f5_normal;

    /**
     * Costructor
     */
    public RegisterMonthly() {

        cellavuota.setBorder(NO_BORDER);

        this.f0_bold = getFont(HELVETICA, WINANSI, 14.04f, BOLD);
        this.f1_bold = getFont(HELVETICA, WINANSI, 9.96f, BOLD);
        this.f2_bold = getFont(HELVETICA, WINANSI, 8f, BOLD);
        this.f3_bold = getFont(HELVETICA, WINANSI, 8f, BOLDITALIC);
        this.f4_bold = getFont(HELVETICA, WINANSI, 6.96f, BOLD);
        this.f1_normal = getFont(HELVETICA, WINANSI, 9.96f, NORMAL);
        this.f2_normal = getFont(HELVETICA, WINANSI, 8f, NORMAL);
        this.f3_normal = getFont(HELVETICA, WINANSI, 6.96f, NORMAL);
        this.f5_normal = getFont(HELVETICA, WINANSI, 6f, NORMAL);
        this.f5_bold = getFont(HELVETICA, WINANSI, 6f, BOLD);
    }

    /**
     *
     * @param document
     */
    public void scriviIntestazioneColonne(Document document) {

        try {
            PdfPTable table2 = new PdfPTable(9);
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

            Phrase phraset3 = new Phrase();
            phraset3.add(new Chunk("Transf. From branches", f5_bold));
            PdfPCell cellt3 = new PdfPCell(phraset3);
            cellt3.setHorizontalAlignment(ALIGN_RIGHT);
            cellt3.setVerticalAlignment(ALIGN_MIDDLE);
            cellt3.setBorder(BOTTOM | TOP);
            cellt3.setFixedHeight(20f);
            cellt3.setBackgroundColor(LIGHT_GRAY);

            Phrase phraset5 = new Phrase();
            phraset5.add(new Chunk("Transf. To branches", f5_bold));
            PdfPCell cellt5 = new PdfPCell(phraset5);
            cellt5.setHorizontalAlignment(ALIGN_RIGHT);
            cellt5.setVerticalAlignment(ALIGN_MIDDLE);
            cellt5.setBorder(BOTTOM | TOP);
            cellt5.setFixedHeight(20f);
            cellt5.setBackgroundColor(LIGHT_GRAY);

            Phrase phraset7 = new Phrase();
            phraset7.add(new Chunk("Purch. from Client", f5_bold));
            PdfPCell cellt7 = new PdfPCell(phraset7);
            cellt7.setHorizontalAlignment(ALIGN_RIGHT);
            cellt7.setVerticalAlignment(ALIGN_MIDDLE);
            cellt7.setBorder(BOTTOM | TOP);
            cellt7.setFixedHeight(20f);
            cellt7.setBackgroundColor(LIGHT_GRAY);

            Phrase phraset8 = new Phrase();
            phraset8.add(new Chunk("Sales to Client", f5_bold));
            PdfPCell cellt8 = new PdfPCell(phraset8);
            cellt8.setHorizontalAlignment(ALIGN_RIGHT);
            cellt8.setVerticalAlignment(ALIGN_MIDDLE);
            cellt8.setBorder(BOTTOM | TOP);
            cellt8.setFixedHeight(20f);
            cellt8.setBackgroundColor(LIGHT_GRAY);

            Phrase phraset10 = new Phrase();
            phraset10.add(new Chunk("Trasf. from Bank", f5_bold));
            PdfPCell cellt10 = new PdfPCell(phraset10);
            cellt10.setHorizontalAlignment(ALIGN_RIGHT);
            cellt10.setVerticalAlignment(ALIGN_MIDDLE);
            cellt10.setBorder(BOTTOM | TOP);
            cellt10.setFixedHeight(20f);
            cellt10.setBackgroundColor(LIGHT_GRAY);

            Phrase phraset11 = new Phrase();
            phraset11.add(new Chunk("Trasf. to Bank", f5_bold));
            PdfPCell cellt11 = new PdfPCell(phraset11);
            cellt11.setHorizontalAlignment(ALIGN_RIGHT);
            cellt11.setVerticalAlignment(ALIGN_MIDDLE);
            cellt11.setBorder(BOTTOM | TOP);
            cellt11.setFixedHeight(20f);
            cellt11.setBackgroundColor(LIGHT_GRAY);

            Phrase phraset12 = new Phrase();
            phraset12.add(new Chunk("Equivalent", f5_bold));
            PdfPCell cellt12 = new PdfPCell(phraset12);
            cellt12.setHorizontalAlignment(ALIGN_RIGHT);
            cellt12.setVerticalAlignment(ALIGN_MIDDLE);
            cellt12.setBorder(BOTTOM | TOP);
            cellt12.setFixedHeight(20f);
            cellt12.setBackgroundColor(LIGHT_GRAY);

            table2.addCell(cellt1);
            table2.addCell(cellt2);
            table2.addCell(cellt3);
            table2.addCell(cellt5);
            table2.addCell(cellt7);
            table2.addCell(cellt8);
            table2.addCell(cellt10);
            table2.addCell(cellt11);
            table2.addCell(cellt12);

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
    public String receipt(String path, RegisterMonthly_value siq, String datareport1, String datereport2) {

//        String outputfile = "RegistroMensileUIC.pdf";
        try {
            File pdf = new File(path + generaId(50) + "RegistroMensileUIC.pdf");
            Document document = new Document(A4, 20, 20, 20, 20);
            try(OutputStream ou = new FileOutputStream(pdf)){
            PdfWriter wr = getInstance(document, ou);
            document.open();

            PdfPTable table = new PdfPTable(2);
            table.setWidths(columnWidths0);
            table.setWidthPercentage(100);

            Phrase phrase1 = new Phrase();
            phrase1.add(new Chunk(intestazionePdf + " From " + datareport1 + " to " + datereport2, f3_bold));
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

            vuoto.setFont(f3_normal);
            document.add(vuoto);

            scriviIntestazioneColonne(document);

            //Popolo la tabella
            PdfPTable table3, table6;
            ArrayList<RegisterMonthly_value> dati = siq.getDati();
            Phrase phraset;
            PdfPCell cellt;

            ArrayList<String> totacqdaclienteparziale = new ArrayList<>();
            ArrayList<String> totvenditeaclientiparziale = new ArrayList<>();


            double acqdaclientetot = 0;
            double venditeaclientetot = 0;

            table3 = new PdfPTable(9);
            table3.setWidths(columnWidths2);
            table3.setWidthPercentage(100);

            double tottrasfdadip = 0;
            double tottrasfadip = 0;
            double tottrasfdabanche = 0;
            double tottrasfabanche = 0;
            double totcontrovalore = 0;

            ArrayList<String> totpurchresident = new ArrayList<>();
            ArrayList<String> totpurchnoresident = new ArrayList<>();

            for (int i = 0; i < dati.size(); i++) {

                RegisterMonthly_value actual =  dati.get(i);
                RegisterMonthly_value prossimo =  dati.get(i);

                if (i < dati.size() - 1) {
                    prossimo =  dati.get(i + 1);
                }

                totacqdaclienteparziale.add(actual.getAcqdaclientiSenzaFormattazione());
                totvenditeaclientiparziale.add(actual.getVenditaaclientiSenzaFormattazione());

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
                phraset.add(new Chunk(formatMysqltoDisplay(actual.getAcqdaclienti()), f5_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setVerticalAlignment(ALIGN_BOTTOM);
                cellt.setBorder(NO_BORDER);
                table3.addCell(cellt);

                if (!(actual.getTipocliente().equals("2"))) {
                    totpurchnoresident.add(actual.getAcqdaclientiSenzaFormattazione());
                } else {
                    totpurchresident.add(actual.getAcqdaclientiSenzaFormattazione());
                }

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(actual.getVenditaaclienti()), f5_normal));
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
                phraset.add(new Chunk("", f5_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setVerticalAlignment(ALIGN_BOTTOM);
                cellt.setBorder(NO_BORDER);
                table3.addCell(cellt);

                if (!(prossimo.getValuta().equals(actual.getValuta())) || i == dati.size() - 1) {
                    //scrivo la riga del totale ma prima agigungo la tabella che ho creato

                    //calcolo il totale
                    for (int j = 0; j < totacqdaclienteparziale.size(); j++) {
                        acqdaclientetot += fd(("" + totacqdaclienteparziale.get(j)));
                        venditeaclientetot += fd(("" + totvenditeaclientiparziale.get(j)));
                    }

                    acqdaclientetot = roundDouble(acqdaclientetot, 2);
                    venditeaclientetot = roundDouble(venditeaclientetot, 2);

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
                    phraset.add(new Chunk(formatMysqltoDisplay(actual.getTrasfdadip()), f5_bold));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setVerticalAlignment(ALIGN_BOTTOM);
                    cellt.setBorder(NO_BORDER);
                    table3.addCell(cellt);

                    phraset = new Phrase();
                    phraset.add(new Chunk(formatMysqltoDisplay(actual.getTrasfadip()), f5_bold));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setVerticalAlignment(ALIGN_BOTTOM);
                    cellt.setBorder(NO_BORDER);
                    table3.addCell(cellt);

                    phraset = new Phrase();
                    phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(acqdaclientetot, 2)), f5_bold));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setVerticalAlignment(ALIGN_BOTTOM);
                    cellt.setBorder(NO_BORDER);
                    table3.addCell(cellt);

                    phraset = new Phrase();
                    phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(venditeaclientetot, 2)), f5_bold));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setVerticalAlignment(ALIGN_BOTTOM);
                    cellt.setBorder(NO_BORDER);
                    table3.addCell(cellt);

                    phraset = new Phrase();
                    phraset.add(new Chunk((formatMysqltoDisplay(actual.getTrasfdabanche())), f5_bold));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setVerticalAlignment(ALIGN_BOTTOM);
                    cellt.setBorder(NO_BORDER);
                    table3.addCell(cellt);

                    phraset = new Phrase();
                    phraset.add(new Chunk(formatMysqltoDisplay(actual.getTrasfabanche()), f5_bold));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setVerticalAlignment(ALIGN_BOTTOM);
                    cellt.setBorder(NO_BORDER);
                    table3.addCell(cellt);

                    phraset = new Phrase();
                    phraset.add(new Chunk(formatMysqltoDisplay(actual.getControvalore()), f5_bold));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setVerticalAlignment(ALIGN_BOTTOM);
                    cellt.setBorder(NO_BORDER);
                    table3.addCell(cellt);

                    document.add(table3);

                    tottrasfdadip += fd((actual.getTrasfdadipSenzaFormattazione()));
                    tottrasfadip += fd((actual.getTrasfadipSenzaFormattazione()));
                    tottrasfdabanche += fd((actual.getTrasfdabancheSenzaFormattazione()));
                    tottrasfabanche += fd((actual.getTrasfabancheSenzaFormattazione()));
                    totcontrovalore += fd((actual.getControvaloreSenzaFormattazione()));

                    tottrasfdadip = roundDouble(tottrasfdadip, 2);
                    tottrasfadip = roundDouble(tottrasfadip, 2);
                    tottrasfdabanche = roundDouble(tottrasfdabanche, 2);
                    tottrasfabanche = roundDouble(tottrasfabanche, 2);
                    totcontrovalore = roundDouble(totcontrovalore, 2);

                    LineSeparator ls = new LineSeparator();
                    ls.setLineWidth(0.7f);
                    ls.setOffset(-2);
                    document.add(ls);

                    table3 = new PdfPTable(9);
                    table3.setWidths(columnWidths2);
                    table3.setWidthPercentage(100);

                    acqdaclientetot = 0;
                    venditeaclientetot = 0;
                    totacqdaclienteparziale.removeAll(totacqdaclienteparziale);
                    totvenditeaclientiparziale.removeAll(totvenditeaclientiparziale);
                }

            }

            //qui scrivo l'ultima riga con la somma totale del controvalore
            table6 = new PdfPTable(9);
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
            //phraset.add(new Chunk(formatMysqltoDisplay(Utility.roundDoubleandFormat(tottrasfdadip,2)), f5_bold));
            phraset.add(new Chunk("", f5_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setVerticalAlignment(ALIGN_BOTTOM);
            cellt.setBorder(NO_BORDER);
            table6.addCell(cellt);

            phraset = new Phrase();
            //phraset.add(new Chunk(formatMysqltoDisplay(Utility.roundDoubleandFormat(tottrasfadip,2)), f5_bold));
            phraset.add(new Chunk("", f5_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setVerticalAlignment(ALIGN_BOTTOM);
            cellt.setBorder(NO_BORDER);
            table6.addCell(cellt);

            double acqdaclientitotale = 0;
            double venditeaclientitotale = 0;
//            for (int j = 0; j < totaleacqdacliente.size(); j++) {
//                acqdaclientitotale += Utility.fd(formatDoubleforMysql("" + totaleacqdacliente.get(j)));
//                venditeaclientitotale += Utility.fd(formatDoubleforMysql("" + totalevenditeaclienti.get(j)));
//            }
            double totpurchresnores = 0;
            for (int j = 0; j < totpurchnoresident.size(); j++) {
                totpurchresnores += fd(("" + totpurchnoresident.get(j)));
                //venditeaclientitotale += Utility.fd(formatDoubleforMysql("" + totalevenditeaclienti.get(j)));
            }
            for (int j = 0; j < totpurchresident.size(); j++) {
                totpurchresnores += fd(("" + totpurchresident.get(j)));
                //venditeaclientitotale += Utility.fd(formatDoubleforMysql("" + totalevenditeaclienti.get(j)));
            }

            phraset = new Phrase();
            //phraset.add(new Chunk(formatMysqltoDisplay(Utility.roundDoubleandFormat(totpurchresnores,2)), f5_bold));
            phraset.add(new Chunk("", f5_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setVerticalAlignment(ALIGN_BOTTOM);
            cellt.setBorder(NO_BORDER);
            table6.addCell(cellt);

            phraset = new Phrase();
            //phraset.add(new Chunk(formatMysqltoDisplay(Utility.roundDoubleandFormat(venditeaclientitotale,2)), f5_bold));
            phraset.add(new Chunk("", f5_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setVerticalAlignment(ALIGN_BOTTOM);
            cellt.setBorder(NO_BORDER);
            table6.addCell(cellt);

            phraset = new Phrase();
            //phraset.add(new Chunk(formatMysqltoDisplay(Utility.roundDoubleandFormat(tottrasfdabanche,2)), f5_bold));
            phraset.add(new Chunk("", f5_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setVerticalAlignment(ALIGN_BOTTOM);
            cellt.setBorder(NO_BORDER);
            table6.addCell(cellt);

            phraset = new Phrase();
            //  phraset.add(new Chunk(formatMysqltoDisplay(Utility.roundDoubleandFormat(tottrasfabanche,2)), f5_bold));
            phraset.add(new Chunk("", f5_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setVerticalAlignment(ALIGN_BOTTOM);
            cellt.setBorder(NO_BORDER);
            table6.addCell(cellt);

            phraset = new Phrase();
            phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(totcontrovalore, 2)), f5_bold));
            cellt = new PdfPCell(phraset);
            cellt.setHorizontalAlignment(ALIGN_RIGHT);
            cellt.setVerticalAlignment(ALIGN_BOTTOM);
            cellt.setBorder(NO_BORDER);
            table6.addCell(cellt);

            document.add(table6);

//////////////            //qui scrivo l'ultima riga con la somma totale del controvalore
//////////////            table6 = new PdfPTable(9);
//////////////            table6.setWidths(columnWidths2);
//////////////            table6.setWidthPercentage(100);
//////////////
//////////////            table6.addCell(cellavuota);
//////////////
//////////////            phraset = new Phrase();
//////////////            phraset.add(new Chunk("", f5_bold));
//////////////            cellt = new PdfPCell(phraset);
//////////////            cellt.setHorizontalAlignment(Element.ALIGN_RIGHT);
//////////////            cellt.setVerticalAlignment(Element.ALIGN_BOTTOM);
//////////////            cellt.setBorder(Rectangle.NO_BORDER);
//////////////            table6.addCell(cellt);
//////////////
//////////////            phraset = new Phrase();
//////////////            phraset.add(new Chunk("", f5_bold));
//////////////            cellt = new PdfPCell(phraset);
//////////////            cellt.setHorizontalAlignment(Element.ALIGN_RIGHT);
//////////////            cellt.setVerticalAlignment(Element.ALIGN_BOTTOM);
//////////////            cellt.setBorder(Rectangle.NO_BORDER);
//////////////            table6.addCell(cellt);
//////////////
//////////////            phraset = new Phrase();
//////////////            phraset.add(new Chunk("", f5_bold));
//////////////            cellt = new PdfPCell(phraset);
//////////////            cellt.setHorizontalAlignment(Element.ALIGN_RIGHT);
//////////////            cellt.setVerticalAlignment(Element.ALIGN_BOTTOM);
//////////////            cellt.setBorder(Rectangle.NO_BORDER);
//////////////            table6.addCell(cellt);
//////////////
//////////////            double venditaaclientetotresidenti = 0;
//////////////            double controvaloretotaleresidenti = 0;
//////////////            for (int j = 0; j < totalevenditeaclientiresidenti.size(); j++) {
//////////////                venditaaclientetotresidenti += Utility.fd(formatDoubleforMysql("" + totalevenditeaclientiresidenti.get(j)));
//////////////                controvaloretotaleresidenti += Utility.fd(formatDoubleforMysql("" + totalecontrovaloreresidenti.get(j)));
//////////////            }
//////////////
//////////////            double venditaaclientetotnonresidenti = 0;
//////////////            double controvaloretotalenonresidenti = 0;
//////////////            for (int j = 0; j < totalevenditeaclientinonresidenti.size(); j++) {
//////////////                venditaaclientetotnonresidenti += Utility.fd(formatDoubleforMysql("" + totalevenditeaclientinonresidenti.get(j)));
//////////////                controvaloretotalenonresidenti += Utility.fd(formatDoubleforMysql("" + totalecontrovalorenonresidenti.get(j)));
//////////////            }
//////////////            
//////////////            double totpurchres=0;
//////////////            for (int j = 0; j < totpurchresident.size(); j++) {
//////////////                totpurchres += Utility.fd(formatDoubleforMysql("" + totpurchresident.get(j)));
//////////////                //venditeaclientitotale += Utility.fd(formatDoubleforMysql("" + totalevenditeaclienti.get(j)));
//////////////            }
//////////////
//////////////            venditaaclientetotresidenti=Utility.roundDouble(venditaaclientetotresidenti, 2);
//////////////            controvaloretotaleresidenti=Utility.roundDouble(controvaloretotaleresidenti, 2);
//////////////            venditaaclientetotnonresidenti=Utility.roundDouble(venditaaclientetotnonresidenti, 2);
//////////////            controvaloretotalenonresidenti=Utility.roundDouble(controvaloretotalenonresidenti, 2);
//////////////            totpurchres=Utility.roundDouble(totpurchres, 2);
//////////////            
//////////////            
//////////////            phraset = new Phrase();
//////////////            phraset.add(new Chunk("Res.", f5_bold));
//////////////            cellt = new PdfPCell(phraset);
//////////////            cellt.setHorizontalAlignment(Element.ALIGN_RIGHT);
//////////////            cellt.setVerticalAlignment(Element.ALIGN_BOTTOM);
//////////////            cellt.setBorder(Rectangle.NO_BORDER);
//////////////            table6.addCell(cellt);
//////////////
//////////////            phraset = new Phrase();
//////////////           // phraset.add(new Chunk(formatMysqltoDisplay(String.valueOf(venditaaclientetotresidenti)), f5_bold));
//////////////            phraset.add(new Chunk(formatMysqltoDisplay(Utility.roundDoubleandFormat(totpurchres,2)), f5_bold));
//////////////            cellt = new PdfPCell(phraset);
//////////////            cellt.setHorizontalAlignment(Element.ALIGN_RIGHT);
//////////////            cellt.setVerticalAlignment(Element.ALIGN_BOTTOM);
//////////////            cellt.setBorder(Rectangle.NO_BORDER);
//////////////            table6.addCell(cellt);
//////////////
//////////////            phraset = new Phrase();
//////////////            phraset.add(new Chunk("", f5_bold));
//////////////            cellt = new PdfPCell(phraset);
//////////////            cellt.setHorizontalAlignment(Element.ALIGN_RIGHT);
//////////////            cellt.setVerticalAlignment(Element.ALIGN_BOTTOM);
//////////////            cellt.setBorder(Rectangle.NO_BORDER);
//////////////            table6.addCell(cellt);
//////////////
//////////////            phraset = new Phrase();
//////////////            phraset.add(new Chunk("", f5_bold));
//////////////            cellt = new PdfPCell(phraset);
//////////////            cellt.setHorizontalAlignment(Element.ALIGN_RIGHT);
//////////////            cellt.setVerticalAlignment(Element.ALIGN_BOTTOM);
//////////////            cellt.setBorder(Rectangle.NO_BORDER);
//////////////            table6.addCell(cellt);
//////////////
//////////////            phraset = new Phrase();
//////////////            phraset.add(new Chunk("", f5_bold));
//////////////            cellt = new PdfPCell(phraset);
//////////////            cellt.setHorizontalAlignment(Element.ALIGN_RIGHT);
//////////////            cellt.setVerticalAlignment(Element.ALIGN_BOTTOM);
//////////////            cellt.setBorder(Rectangle.NO_BORDER);
//////////////            table6.addCell(cellt);
//////////////
//////////////            LineSeparator ls = new LineSeparator();
//////////////
//////////////            //   document.add(ls);
//////////////            document.add(table6);
//////////////
//////////////            table6 = new PdfPTable(9);
//////////////            table6.setWidths(columnWidths2);
//////////////            table6.setWidthPercentage(100);
//////////////
//////////////            table6.addCell(cellavuota);
//////////////
//////////////            phraset = new Phrase();
//////////////            phraset.add(new Chunk("", f5_bold));
//////////////            cellt = new PdfPCell(phraset);
//////////////            cellt.setHorizontalAlignment(Element.ALIGN_RIGHT);
//////////////            cellt.setVerticalAlignment(Element.ALIGN_BOTTOM);
//////////////            cellt.setBorder(Rectangle.NO_BORDER);
//////////////            table6.addCell(cellt);
//////////////
//////////////            phraset = new Phrase();
//////////////            phraset.add(new Chunk("", f5_bold));
//////////////            cellt = new PdfPCell(phraset);
//////////////            cellt.setHorizontalAlignment(Element.ALIGN_RIGHT);
//////////////            cellt.setVerticalAlignment(Element.ALIGN_BOTTOM);
//////////////            cellt.setBorder(Rectangle.NO_BORDER);
//////////////            table6.addCell(cellt);
//////////////
//////////////            phraset = new Phrase();
//////////////            phraset.add(new Chunk("", f5_bold));
//////////////            cellt = new PdfPCell(phraset);
//////////////            cellt.setHorizontalAlignment(Element.ALIGN_RIGHT);
//////////////            cellt.setVerticalAlignment(Element.ALIGN_BOTTOM);
//////////////            cellt.setBorder(Rectangle.NO_BORDER);
//////////////            table6.addCell(cellt);
//////////////
//////////////            phraset = new Phrase();
//////////////            phraset.add(new Chunk("Non Res.", f5_bold));
//////////////            cellt = new PdfPCell(phraset);
//////////////            cellt.setHorizontalAlignment(Element.ALIGN_RIGHT);
//////////////            cellt.setVerticalAlignment(Element.ALIGN_BOTTOM);
//////////////            cellt.setBorder(Rectangle.BOTTOM);
//////////////            table6.addCell(cellt);
//////////////            
//////////////             double totpurchnores=0;
//////////////            for (int j = 0; j < totpurchnoresident.size(); j++) {
//////////////                totpurchnores += Utility.fd(formatDoubleforMysql("" + totpurchnoresident.get(j)));
//////////////                //venditeaclientitotale += Utility.fd(formatDoubleforMysql("" + totalevenditeaclienti.get(j)));
//////////////            }
//////////////            
//////////////            totpurchnores=Utility.roundDouble(totpurchnores, 2);
//////////////
//////////////            phraset = new Phrase();
//////////////            //phraset.add(new Chunk(formatMysqltoDisplay(String.valueOf(venditaaclientetotnonresidenti)), f5_bold));
//////////////            phraset.add(new Chunk(formatMysqltoDisplay(Utility.roundDoubleandFormat(totpurchnores,2)), f5_bold));
//////////////            cellt = new PdfPCell(phraset);
//////////////            cellt.setHorizontalAlignment(Element.ALIGN_RIGHT);
//////////////            cellt.setVerticalAlignment(Element.ALIGN_BOTTOM);
//////////////            cellt.setBorder(Rectangle.BOTTOM);
//////////////            table6.addCell(cellt);
//////////////
//////////////            phraset = new Phrase();
//////////////            phraset.add(new Chunk("", f5_bold));
//////////////            cellt = new PdfPCell(phraset);
//////////////            cellt.setHorizontalAlignment(Element.ALIGN_RIGHT);
//////////////            cellt.setVerticalAlignment(Element.ALIGN_BOTTOM);
//////////////            cellt.setBorder(Rectangle.NO_BORDER);
//////////////            table6.addCell(cellt);
//////////////
//////////////            phraset = new Phrase();
//////////////            phraset.add(new Chunk("", f5_bold));
//////////////            cellt = new PdfPCell(phraset);
//////////////            cellt.setHorizontalAlignment(Element.ALIGN_RIGHT);
//////////////            cellt.setVerticalAlignment(Element.ALIGN_BOTTOM);
//////////////            cellt.setBorder(Rectangle.NO_BORDER);
//////////////            table6.addCell(cellt);
//////////////
//////////////            phraset = new Phrase();
//////////////            phraset.add(new Chunk("", f5_bold));
//////////////            cellt = new PdfPCell(phraset);
//////////////            cellt.setHorizontalAlignment(Element.ALIGN_RIGHT);
//////////////            cellt.setVerticalAlignment(Element.ALIGN_BOTTOM);
//////////////            cellt.setBorder(Rectangle.NO_BORDER);
//////////////            table6.addCell(cellt);
//////////////
//////////////            document.add(table6);
//////////////
//////////////            table6 = new PdfPTable(9);
//////////////            table6.setWidths(columnWidths2);
//////////////            table6.setWidthPercentage(100);
//////////////
//////////////            table6.addCell(cellavuota);
//////////////
//////////////            phraset = new Phrase();
//////////////            phraset.add(new Chunk("", f5_bold));
//////////////            cellt = new PdfPCell(phraset);
//////////////            cellt.setHorizontalAlignment(Element.ALIGN_RIGHT);
//////////////            cellt.setVerticalAlignment(Element.ALIGN_BOTTOM);
//////////////            cellt.setBorder(Rectangle.NO_BORDER);
//////////////            table6.addCell(cellt);
//////////////
//////////////            phraset = new Phrase();
//////////////            phraset.add(new Chunk("", f5_bold));
//////////////            cellt = new PdfPCell(phraset);
//////////////            cellt.setHorizontalAlignment(Element.ALIGN_RIGHT);
//////////////            cellt.setVerticalAlignment(Element.ALIGN_BOTTOM);
//////////////            cellt.setBorder(Rectangle.NO_BORDER);
//////////////            table6.addCell(cellt);
//////////////
//////////////            phraset = new Phrase();
//////////////            phraset.add(new Chunk("", f5_bold));
//////////////            cellt = new PdfPCell(phraset);
//////////////            cellt.setHorizontalAlignment(Element.ALIGN_RIGHT);
//////////////            cellt.setVerticalAlignment(Element.ALIGN_BOTTOM);
//////////////            cellt.setBorder(Rectangle.NO_BORDER);
//////////////            table6.addCell(cellt);
//////////////
//////////////            phraset = new Phrase();
//////////////            phraset.add(new Chunk("Total", f5_bold));
//////////////            cellt = new PdfPCell(phraset);
//////////////            cellt.setHorizontalAlignment(Element.ALIGN_RIGHT);
//////////////            cellt.setVerticalAlignment(Element.ALIGN_BOTTOM);
//////////////            cellt.setBorder(Rectangle.NO_BORDER);
//////////////            table6.addCell(cellt);
//////////////
//////////////            phraset = new Phrase();
//////////////            //phraset.add(new Chunk(formatMysqltoDisplay(String.valueOf(venditaaclientetotnonresidenti + venditaaclientetotresidenti)), f5_bold));
//////////////            phraset.add(new Chunk(formatMysqltoDisplay(Utility.roundDoubleandFormat(totpurchres + totpurchresnores,2)), f5_bold));
//////////////            cellt = new PdfPCell(phraset);
//////////////            cellt.setHorizontalAlignment(Element.ALIGN_RIGHT);
//////////////            cellt.setVerticalAlignment(Element.ALIGN_BOTTOM);
//////////////            cellt.setBorder(Rectangle.NO_BORDER);
//////////////            table6.addCell(cellt);
//////////////
//////////////            phraset = new Phrase();
//////////////            phraset.add(new Chunk("", f5_bold));
//////////////            cellt = new PdfPCell(phraset);
//////////////            cellt.setHorizontalAlignment(Element.ALIGN_RIGHT);
//////////////            cellt.setVerticalAlignment(Element.ALIGN_BOTTOM);
//////////////            cellt.setBorder(Rectangle.NO_BORDER);
//////////////            table6.addCell(cellt);
//////////////
//////////////            phraset = new Phrase();
//////////////            phraset.add(new Chunk("", f5_bold));
//////////////            cellt = new PdfPCell(phraset);
//////////////            cellt.setHorizontalAlignment(Element.ALIGN_RIGHT);
//////////////            cellt.setVerticalAlignment(Element.ALIGN_BOTTOM);
//////////////            cellt.setBorder(Rectangle.NO_BORDER);
//////////////            table6.addCell(cellt);
//////////////
//////////////            phraset = new Phrase();
//////////////            phraset.add(new Chunk("", f5_bold));
//////////////            cellt = new PdfPCell(phraset);
//////////////            cellt.setHorizontalAlignment(Element.ALIGN_RIGHT);
//////////////            cellt.setVerticalAlignment(Element.ALIGN_BOTTOM);
//////////////            cellt.setBorder(Rectangle.NO_BORDER);
//////////////            table6.addCell(cellt);
//////////////
//////////////            document.add(table6);
//////////////            
//////////////            
//////////////            document.add(vuoto);
//////////////            
//////////////            //sales
//////////////            
//////////////             //qui scrivo l'ultima riga con la somma totale del controvalore
//////////////            table6 = new PdfPTable(9);
//////////////            table6.setWidths(columnWidths2);
//////////////            table6.setWidthPercentage(100);
//////////////
//////////////            table6.addCell(cellavuota);
//////////////
//////////////            phraset = new Phrase();
//////////////            phraset.add(new Chunk("Total", f5_bold));
//////////////            cellt = new PdfPCell(phraset);
//////////////            cellt.setHorizontalAlignment(Element.ALIGN_RIGHT);
//////////////            cellt.setVerticalAlignment(Element.ALIGN_BOTTOM);
//////////////            cellt.setBorder(Rectangle.NO_BORDER);
//////////////            table6.addCell(cellt);
//////////////
//////////////            phraset = new Phrase();
//////////////            phraset.add(new Chunk("", f5_bold));
//////////////            cellt = new PdfPCell(phraset);
//////////////            cellt.setHorizontalAlignment(Element.ALIGN_RIGHT);
//////////////            cellt.setVerticalAlignment(Element.ALIGN_BOTTOM);
//////////////            cellt.setBorder(Rectangle.NO_BORDER);
//////////////            table6.addCell(cellt);
//////////////
//////////////            phraset = new Phrase();
//////////////            phraset.add(new Chunk("", f5_bold));
//////////////            cellt = new PdfPCell(phraset);
//////////////            cellt.setHorizontalAlignment(Element.ALIGN_RIGHT);
//////////////            cellt.setVerticalAlignment(Element.ALIGN_BOTTOM);
//////////////            cellt.setBorder(Rectangle.NO_BORDER);
//////////////            table6.addCell(cellt);
//////////////
//////////////             acqdaclientitotale = 0;
//////////////             venditeaclientitotale = 0;
//////////////            for (int j = 0; j < totaleacqdacliente.size(); j++) {
//////////////                acqdaclientitotale += Utility.fd(formatDoubleforMysql("" + totaleacqdacliente.get(j)));
//////////////                venditeaclientitotale += Utility.fd(formatDoubleforMysql("" + totalevenditeaclienti.get(j)));
//////////////            }
//////////////            
//////////////            acqdaclientitotale=Utility.roundDouble(acqdaclientitotale, 2);
//////////////            venditeaclientitotale=Utility.roundDouble(venditeaclientitotale, 2);
//////////////            
//////////////            double totsalesresnores=0;
//////////////            for (int j = 0; j < totsalesnoresident.size(); j++) {
//////////////                totsalesresnores += Utility.fd(formatDoubleforMysql("" + totsalesnoresident.get(j)));
//////////////                //venditeaclientitotale += Utility.fd(formatDoubleforMysql("" + totalevenditeaclienti.get(j)));
//////////////            }
//////////////            for (int j = 0; j < totsalesresident.size(); j++) {
//////////////                totsalesresnores += Utility.fd(formatDoubleforMysql("" + totsalesresident.get(j)));
//////////////                //venditeaclientitotale += Utility.fd(formatDoubleforMysql("" + totalevenditeaclienti.get(j)));
//////////////            }
//////////////            
//////////////            totsalesresnores=Utility.roundDouble(totsalesresnores, 2);
//////////////
//////////////            phraset = new Phrase();
//////////////           // phraset.add(new Chunk(formatMysqltoDisplay(String.valueOf(acqdaclientitotale)), f5_bold));
//////////////            phraset.add(new Chunk(formatMysqltoDisplay(Utility.roundDoubleandFormat(totsalesresnores,2)), f5_bold));
//////////////            cellt = new PdfPCell(phraset);
//////////////            cellt.setHorizontalAlignment(Element.ALIGN_RIGHT);
//////////////            cellt.setVerticalAlignment(Element.ALIGN_BOTTOM);
//////////////            cellt.setBorder(Rectangle.NO_BORDER);
//////////////            table6.addCell(cellt);
//////////////
//////////////            phraset = new Phrase();
//////////////            phraset.add(new Chunk(formatMysqltoDisplay(Utility.roundDoubleandFormat(venditeaclientitotale,2)), f5_bold));
//////////////            cellt = new PdfPCell(phraset);
//////////////            cellt.setHorizontalAlignment(Element.ALIGN_RIGHT);
//////////////            cellt.setVerticalAlignment(Element.ALIGN_BOTTOM);
//////////////            cellt.setBorder(Rectangle.NO_BORDER);
//////////////            table6.addCell(cellt);
//////////////
//////////////            phraset = new Phrase();
//////////////            phraset.add(new Chunk("", f5_bold));
//////////////            cellt = new PdfPCell(phraset);
//////////////            cellt.setHorizontalAlignment(Element.ALIGN_RIGHT);
//////////////            cellt.setVerticalAlignment(Element.ALIGN_BOTTOM);
//////////////            cellt.setBorder(Rectangle.NO_BORDER);
//////////////            table6.addCell(cellt);
//////////////
//////////////            phraset = new Phrase();
//////////////            phraset.add(new Chunk("", f5_bold));
//////////////            cellt = new PdfPCell(phraset);
//////////////            cellt.setHorizontalAlignment(Element.ALIGN_RIGHT);
//////////////            cellt.setVerticalAlignment(Element.ALIGN_BOTTOM);
//////////////            cellt.setBorder(Rectangle.NO_BORDER);
//////////////            table6.addCell(cellt);
//////////////
//////////////            phraset = new Phrase();
//////////////            phraset.add(new Chunk("", f5_bold));
//////////////            cellt = new PdfPCell(phraset);
//////////////            cellt.setHorizontalAlignment(Element.ALIGN_RIGHT);
//////////////            cellt.setVerticalAlignment(Element.ALIGN_BOTTOM);
//////////////            cellt.setBorder(Rectangle.NO_BORDER);
//////////////            table6.addCell(cellt);
//////////////
//////////////            document.add(table6);
//////////////
//////////////            //qui scrivo l'ultima riga con la somma totale del controvalore
//////////////            table6 = new PdfPTable(9);
//////////////            table6.setWidths(columnWidths2);
//////////////            table6.setWidthPercentage(100);
//////////////
//////////////            table6.addCell(cellavuota);
//////////////
//////////////            phraset = new Phrase();
//////////////            phraset.add(new Chunk("", f5_bold));
//////////////            cellt = new PdfPCell(phraset);
//////////////            cellt.setHorizontalAlignment(Element.ALIGN_RIGHT);
//////////////            cellt.setVerticalAlignment(Element.ALIGN_BOTTOM);
//////////////            cellt.setBorder(Rectangle.NO_BORDER);
//////////////            table6.addCell(cellt);
//////////////
//////////////            phraset = new Phrase();
//////////////            phraset.add(new Chunk("", f5_bold));
//////////////            cellt = new PdfPCell(phraset);
//////////////            cellt.setHorizontalAlignment(Element.ALIGN_RIGHT);
//////////////            cellt.setVerticalAlignment(Element.ALIGN_BOTTOM);
//////////////            cellt.setBorder(Rectangle.NO_BORDER);
//////////////            table6.addCell(cellt);
//////////////
//////////////            phraset = new Phrase();
//////////////            phraset.add(new Chunk("", f5_bold));
//////////////            cellt = new PdfPCell(phraset);
//////////////            cellt.setHorizontalAlignment(Element.ALIGN_RIGHT);
//////////////            cellt.setVerticalAlignment(Element.ALIGN_BOTTOM);
//////////////            cellt.setBorder(Rectangle.NO_BORDER);
//////////////            table6.addCell(cellt);
//////////////
//////////////             venditaaclientetotresidenti = 0;
//////////////             controvaloretotaleresidenti = 0;
//////////////            for (int j = 0; j < totalevenditeaclientiresidenti.size(); j++) {
//////////////                venditaaclientetotresidenti += Utility.fd(formatDoubleforMysql("" + totalevenditeaclientiresidenti.get(j)));
//////////////                controvaloretotaleresidenti += Utility.fd(formatDoubleforMysql("" + totalecontrovaloreresidenti.get(j)));
//////////////            }
//////////////
//////////////             venditaaclientetotnonresidenti = 0;
//////////////             controvaloretotalenonresidenti = 0;
//////////////            for (int j = 0; j < totalevenditeaclientinonresidenti.size(); j++) {
//////////////                venditaaclientetotnonresidenti += Utility.fd(formatDoubleforMysql("" + totalevenditeaclientinonresidenti.get(j)));
//////////////                controvaloretotalenonresidenti += Utility.fd(formatDoubleforMysql("" + totalecontrovalorenonresidenti.get(j)));
//////////////            }
////////////// double totsalesres=0;
//////////////            for (int j = 0; j < totsalesresident.size(); j++) {
//////////////                totsalesres += Utility.fd(formatDoubleforMysql("" + totsalesresident.get(j)));
//////////////                //venditeaclientitotale += Utility.fd(formatDoubleforMysql("" + totalevenditeaclienti.get(j)));
//////////////            }
//////////////            
//////////////          venditaaclientetotresidenti=Utility.roundDouble(venditaaclientetotresidenti, 2);
//////////////          controvaloretotaleresidenti=Utility.roundDouble(controvaloretotaleresidenti, 2);
//////////////          venditaaclientetotnonresidenti=Utility.roundDouble(venditaaclientetotnonresidenti, 2);
//////////////          controvaloretotalenonresidenti=Utility.roundDouble(controvaloretotalenonresidenti, 2);
//////////////          totsalesres=Utility.roundDouble(totsalesres, 2);
//////////////            
//////////////            phraset = new Phrase();
//////////////            phraset.add(new Chunk("Res.", f5_bold));
//////////////            cellt = new PdfPCell(phraset);
//////////////            cellt.setHorizontalAlignment(Element.ALIGN_RIGHT);
//////////////            cellt.setVerticalAlignment(Element.ALIGN_BOTTOM);
//////////////            cellt.setBorder(Rectangle.NO_BORDER);
//////////////            table6.addCell(cellt);
//////////////
//////////////            phraset = new Phrase();
//////////////           // phraset.add(new Chunk(formatMysqltoDisplay(String.valueOf(venditaaclientetotresidenti)), f5_bold));
//////////////            phraset.add(new Chunk(formatMysqltoDisplay(Utility.roundDoubleandFormat(totsalesres,2)), f5_bold));
//////////////            cellt = new PdfPCell(phraset);
//////////////            cellt.setHorizontalAlignment(Element.ALIGN_RIGHT);
//////////////            cellt.setVerticalAlignment(Element.ALIGN_BOTTOM);
//////////////            cellt.setBorder(Rectangle.NO_BORDER);
//////////////            table6.addCell(cellt);
//////////////
//////////////            phraset = new Phrase();
//////////////            phraset.add(new Chunk("", f5_bold));
//////////////            cellt = new PdfPCell(phraset);
//////////////            cellt.setHorizontalAlignment(Element.ALIGN_RIGHT);
//////////////            cellt.setVerticalAlignment(Element.ALIGN_BOTTOM);
//////////////            cellt.setBorder(Rectangle.NO_BORDER);
//////////////            table6.addCell(cellt);
//////////////
//////////////            phraset = new Phrase();
//////////////            phraset.add(new Chunk("", f5_bold));
//////////////            cellt = new PdfPCell(phraset);
//////////////            cellt.setHorizontalAlignment(Element.ALIGN_RIGHT);
//////////////            cellt.setVerticalAlignment(Element.ALIGN_BOTTOM);
//////////////            cellt.setBorder(Rectangle.NO_BORDER);
//////////////            table6.addCell(cellt);
//////////////
//////////////            phraset = new Phrase();
//////////////            phraset.add(new Chunk("", f5_bold));
//////////////            cellt = new PdfPCell(phraset);
//////////////            cellt.setHorizontalAlignment(Element.ALIGN_RIGHT);
//////////////            cellt.setVerticalAlignment(Element.ALIGN_BOTTOM);
//////////////            cellt.setBorder(Rectangle.NO_BORDER);
//////////////            table6.addCell(cellt);
//////////////
//////////////             ls = new LineSeparator();
//////////////
//////////////            //   document.add(ls);
//////////////            document.add(table6);
//////////////
//////////////            table6 = new PdfPTable(9);
//////////////            table6.setWidths(columnWidths2);
//////////////            table6.setWidthPercentage(100);
//////////////
//////////////            table6.addCell(cellavuota);
//////////////
//////////////            phraset = new Phrase();
//////////////            phraset.add(new Chunk("", f5_bold));
//////////////            cellt = new PdfPCell(phraset);
//////////////            cellt.setHorizontalAlignment(Element.ALIGN_RIGHT);
//////////////            cellt.setVerticalAlignment(Element.ALIGN_BOTTOM);
//////////////            cellt.setBorder(Rectangle.NO_BORDER);
//////////////            table6.addCell(cellt);
//////////////
//////////////            phraset = new Phrase();
//////////////            phraset.add(new Chunk("", f5_bold));
//////////////            cellt = new PdfPCell(phraset);
//////////////            cellt.setHorizontalAlignment(Element.ALIGN_RIGHT);
//////////////            cellt.setVerticalAlignment(Element.ALIGN_BOTTOM);
//////////////            cellt.setBorder(Rectangle.NO_BORDER);
//////////////            table6.addCell(cellt);
//////////////
//////////////            phraset = new Phrase();
//////////////            phraset.add(new Chunk("", f5_bold));
//////////////            cellt = new PdfPCell(phraset);
//////////////            cellt.setHorizontalAlignment(Element.ALIGN_RIGHT);
//////////////            cellt.setVerticalAlignment(Element.ALIGN_BOTTOM);
//////////////            cellt.setBorder(Rectangle.NO_BORDER);
//////////////            table6.addCell(cellt);
//////////////
//////////////            phraset = new Phrase();
//////////////            phraset.add(new Chunk("Non Res.", f5_bold));
//////////////            cellt = new PdfPCell(phraset);
//////////////            cellt.setHorizontalAlignment(Element.ALIGN_RIGHT);
//////////////            cellt.setVerticalAlignment(Element.ALIGN_BOTTOM);
//////////////            cellt.setBorder(Rectangle.BOTTOM);
//////////////            table6.addCell(cellt);
//////////////            
//////////////            
//////////////            double totsalesnores=0;
//////////////            for (int j = 0; j < totsalesnoresident.size(); j++) {
//////////////                totsalesnores += Utility.fd(formatDoubleforMysql("" + totsalesnoresident.get(j)));
//////////////                //venditeaclientitotale += Utility.fd(formatDoubleforMysql("" + totalevenditeaclienti.get(j)));
//////////////            }
//////////////            
//////////////            totsalesnores=Utility.roundDouble(totsalesnores, 2);
//////////////
//////////////            phraset = new Phrase();
//////////////           // phraset.add(new Chunk(formatMysqltoDisplay(String.valueOf(venditaaclientetotnonresidenti)), f5_bold));
//////////////            phraset.add(new Chunk(formatMysqltoDisplay(Utility.roundDoubleandFormat(totsalesnores,2)), f5_bold));
//////////////            cellt = new PdfPCell(phraset);
//////////////            cellt.setHorizontalAlignment(Element.ALIGN_RIGHT);
//////////////            cellt.setVerticalAlignment(Element.ALIGN_BOTTOM);
//////////////            cellt.setBorder(Rectangle.BOTTOM);
//////////////            table6.addCell(cellt);
//////////////
//////////////            phraset = new Phrase();
//////////////            phraset.add(new Chunk("", f5_bold));
//////////////            cellt = new PdfPCell(phraset);
//////////////            cellt.setHorizontalAlignment(Element.ALIGN_RIGHT);
//////////////            cellt.setVerticalAlignment(Element.ALIGN_BOTTOM);
//////////////            cellt.setBorder(Rectangle.NO_BORDER);
//////////////            table6.addCell(cellt);
//////////////
//////////////            phraset = new Phrase();
//////////////            phraset.add(new Chunk("", f5_bold));
//////////////            cellt = new PdfPCell(phraset);
//////////////            cellt.setHorizontalAlignment(Element.ALIGN_RIGHT);
//////////////            cellt.setVerticalAlignment(Element.ALIGN_BOTTOM);
//////////////            cellt.setBorder(Rectangle.NO_BORDER);
//////////////            table6.addCell(cellt);
//////////////
//////////////            phraset = new Phrase();
//////////////            phraset.add(new Chunk("", f5_bold));
//////////////            cellt = new PdfPCell(phraset);
//////////////            cellt.setHorizontalAlignment(Element.ALIGN_RIGHT);
//////////////            cellt.setVerticalAlignment(Element.ALIGN_BOTTOM);
//////////////            cellt.setBorder(Rectangle.NO_BORDER);
//////////////            table6.addCell(cellt);
//////////////
//////////////            document.add(table6);
//////////////
//////////////            table6 = new PdfPTable(9);
//////////////            table6.setWidths(columnWidths2);
//////////////            table6.setWidthPercentage(100);
//////////////
//////////////            table6.addCell(cellavuota);
//////////////
//////////////            phraset = new Phrase();
//////////////            phraset.add(new Chunk("", f5_bold));
//////////////            cellt = new PdfPCell(phraset);
//////////////            cellt.setHorizontalAlignment(Element.ALIGN_RIGHT);
//////////////            cellt.setVerticalAlignment(Element.ALIGN_BOTTOM);
//////////////            cellt.setBorder(Rectangle.NO_BORDER);
//////////////            table6.addCell(cellt);
//////////////
//////////////            phraset = new Phrase();
//////////////            phraset.add(new Chunk("", f5_bold));
//////////////            cellt = new PdfPCell(phraset);
//////////////            cellt.setHorizontalAlignment(Element.ALIGN_RIGHT);
//////////////            cellt.setVerticalAlignment(Element.ALIGN_BOTTOM);
//////////////            cellt.setBorder(Rectangle.NO_BORDER);
//////////////            table6.addCell(cellt);
//////////////
//////////////            phraset = new Phrase();
//////////////            phraset.add(new Chunk("", f5_bold));
//////////////            cellt = new PdfPCell(phraset);
//////////////            cellt.setHorizontalAlignment(Element.ALIGN_RIGHT);
//////////////            cellt.setVerticalAlignment(Element.ALIGN_BOTTOM);
//////////////            cellt.setBorder(Rectangle.NO_BORDER);
//////////////            table6.addCell(cellt);
//////////////
//////////////            phraset = new Phrase();
//////////////            phraset.add(new Chunk("Total", f5_bold));
//////////////            cellt = new PdfPCell(phraset);
//////////////            cellt.setHorizontalAlignment(Element.ALIGN_RIGHT);
//////////////            cellt.setVerticalAlignment(Element.ALIGN_BOTTOM);
//////////////            cellt.setBorder(Rectangle.NO_BORDER);
//////////////            table6.addCell(cellt);
//////////////
//////////////            phraset = new Phrase();
//////////////            phraset.add(new Chunk(formatMysqltoDisplay(Utility.roundDoubleandFormat(venditaaclientetotnonresidenti + venditaaclientetotresidenti,2)), f5_bold));
//////////////            cellt = new PdfPCell(phraset);
//////////////            cellt.setHorizontalAlignment(Element.ALIGN_RIGHT);
//////////////            cellt.setVerticalAlignment(Element.ALIGN_BOTTOM);
//////////////            cellt.setBorder(Rectangle.NO_BORDER);
//////////////            table6.addCell(cellt);
//////////////
//////////////            phraset = new Phrase();
//////////////            phraset.add(new Chunk("", f5_bold));
//////////////            cellt = new PdfPCell(phraset);
//////////////            cellt.setHorizontalAlignment(Element.ALIGN_RIGHT);
//////////////            cellt.setVerticalAlignment(Element.ALIGN_BOTTOM);
//////////////            cellt.setBorder(Rectangle.NO_BORDER);
//////////////            table6.addCell(cellt);
//////////////
//////////////            phraset = new Phrase();
//////////////            phraset.add(new Chunk("", f5_bold));
//////////////            cellt = new PdfPCell(phraset);
//////////////            cellt.setHorizontalAlignment(Element.ALIGN_RIGHT);
//////////////            cellt.setVerticalAlignment(Element.ALIGN_BOTTOM);
//////////////            cellt.setBorder(Rectangle.NO_BORDER);
//////////////            table6.addCell(cellt);
//////////////
//////////////            phraset = new Phrase();
//////////////            phraset.add(new Chunk("", f5_bold));
//////////////            cellt = new PdfPCell(phraset);
//////////////            cellt.setHorizontalAlignment(Element.ALIGN_RIGHT);
//////////////            cellt.setVerticalAlignment(Element.ALIGN_BOTTOM);
//////////////            cellt.setBorder(Rectangle.NO_BORDER);
//////////////            table6.addCell(cellt);
//////////////
//////////////            document.add(table6);
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
    public String receiptexcel(String path, RegisterMonthly_value siq, String datareport1, String datereport2) {

//        String outputfile = "RegistroMensileUIC.pdf";
        try {
            File pdf = new File(path + generaId(50) + "RegistroMensileUICAcquisti.xls");
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet("RegisterMonthly");
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
            cl.setCellValue(intestazionePdf + " From " + datareport1 + " to " + datereport2);

//            Cell cl2 = rowP.createCell(6);
//            cl2.setCellStyle(style2);
//            cl2.setCellValue(datareport1);
            Row row = sheet.createRow((short) 3);
            row.createCell(1).setCellValue(siq.getId_filiale() + " " + siq.getDe_filiale());

            Row row6 = sheet.createRow((short) 5);

            Cell f2 = row6.createCell(2);
            f2.setCellStyle(style3left);
            f2.setCellValue("Currency");

            Cell f3 = row6.createCell(3);
            f3.setCellStyle(style3);
            f3.setCellValue("Transf. From branches");

            Cell f4 = row6.createCell(4);
            f4.setCellStyle(style3);
            f4.setCellValue("Transf. To branches");

            Cell f5 = row6.createCell(5);
            f5.setCellStyle(style3);
            f5.setCellValue("Purch. from Client");

            Cell f6 = row6.createCell(6);
            f6.setCellStyle(style3);
            f6.setCellValue("Sales to Client");

            Cell f7 = row6.createCell(7);
            f7.setCellStyle(style3);
            f7.setCellValue("Trasf. from Bank");

            Cell f8 = row6.createCell(8);
            f8.setCellStyle(style3);
            f8.setCellValue("Trasf. to Bank");

            Cell f9 = row6.createCell(9);
            f9.setCellStyle(style3);
            f9.setCellValue("Equivalent");

            //Popolo la tabella
            ArrayList<RegisterMonthly_value> dati = siq.getDati();

            ArrayList<String> totacqdaclienteparziale = new ArrayList<>();
            ArrayList<String> totvenditeaclientiparziale = new ArrayList<>();


            double acqdaclientetot = 0;
            double venditeaclientetot = 0;

            double tottrasfdadip = 0;
            double tottrasfadip = 0;
            double tottrasfdabanche = 0;
            double tottrasfabanche = 0;
            double totcontrovalore = 0;

            int cntriga = 7;

            for (int i = 0; i < dati.size(); i++) {
                cntriga++;
                Row row7 = sheet.createRow((short) cntriga);

                RegisterMonthly_value actual = dati.get(i);
                RegisterMonthly_value prossimo = dati.get(i);

                if (i < dati.size() - 1) {
                    prossimo =  dati.get(i + 1);
                }

                totacqdaclienteparziale.add(actual.getAcqdaclientiSenzaFormattazione());
                totvenditeaclientiparziale.add(actual.getVenditaaclientiSenzaFormattazione());

       
                Cell f11 = row7.createCell(1);
                f11.setCellStyle(style4left);
                f11.setCellValue(actual.getTipocliente());

                Cell f12 = row7.createCell(2);
                f12.setCellStyle(style4left);
                f12.setCellValue(actual.getValuta());

                Cell f13 = row7.createCell(5);
                f13.setCellStyle(style4);
                f13.setCellValue(formatMysqltoDisplay(actual.getAcqdaclienti()));
                Cell f14 = row7.createCell(6);
                f14.setCellStyle(style4);
                f14.setCellValue(formatMysqltoDisplay(actual.getVenditaaclienti()));

                if (!(prossimo.getValuta().equals(actual.getValuta())) || i == dati.size() - 1) {
                    //scrivo la riga del totale ma prima agigungo la tabella che ho creato

                    //calcolo il totale
                    for (int j = 0; j < totacqdaclienteparziale.size(); j++) {
                        acqdaclientetot += fd(("" + totacqdaclienteparziale.get(j)));
                        venditeaclientetot += fd(("" + totvenditeaclientiparziale.get(j)));
                    }

                    acqdaclientetot = roundDouble(acqdaclientetot, 2);
                    venditeaclientetot = roundDouble(venditeaclientetot, 2);

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
                    f18.setCellValue(formatMysqltoDisplay(actual.getTrasfdadip()));

                    Cell f19 = row8.createCell(4);
                    f19.setCellStyle(style3);
                    f19.setCellValue(formatMysqltoDisplay(actual.getTrasfadip()));

                    Cell f20 = row8.createCell(5);
                    f20.setCellStyle(style3);
                    f20.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(acqdaclientetot, 2)));

                    Cell f21 = row8.createCell(6);
                    f21.setCellStyle(style3);
                    f21.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(venditeaclientetot, 2)));

                    Cell f22 = row8.createCell(7);
                    f22.setCellStyle(style3);
                    f22.setCellValue(formatMysqltoDisplay(actual.getTrasfdabanche()));

                    Cell f23 = row8.createCell(8);
                    f23.setCellStyle(style3);
                    f23.setCellValue(formatMysqltoDisplay(actual.getTrasfabanche()));

                    Cell f24 = row8.createCell(9);
                    f24.setCellStyle(style3);
                    f24.setCellValue(formatMysqltoDisplay(actual.getControvalore()));

                    tottrasfdadip += fd((actual.getTrasfdadipSenzaFormattazione()));
                    tottrasfadip += fd((actual.getTrasfadipSenzaFormattazione()));
                    tottrasfdabanche += fd((actual.getTrasfdabancheSenzaFormattazione()));
                    tottrasfabanche += fd((actual.getTrasfabancheSenzaFormattazione()));
                    totcontrovalore += fd((actual.getControvaloreSenzaFormattazione()));

                    tottrasfdadip = roundDouble(tottrasfdadip, 2);
                    tottrasfadip = roundDouble(tottrasfadip, 2);
                    tottrasfdabanche = roundDouble(tottrasfdabanche, 2);
                    tottrasfabanche = roundDouble(tottrasfabanche, 2);
                    totcontrovalore = roundDouble(totcontrovalore, 2);

                    acqdaclientetot = 0;
                    venditeaclientetot = 0;
                    totacqdaclienteparziale.removeAll(totacqdaclienteparziale);
                    totvenditeaclientiparziale.removeAll(totvenditeaclientiparziale);

                    cntriga++;
                }

            }

            cntriga++;
            cntriga++;

            Row row9 = sheet.createRow((short) cntriga);

            Cell f26 = row9.createCell(2);
            f26.setCellStyle(style3left);
            f26.setCellValue("Total");

            Cell f33 = row9.createCell(9);
            f33.setCellStyle(style3);
            f33.setCellValue(formatMysqltoDisplay(roundDoubleandFormat(totcontrovalore, 2)));
            cntriga++;
            cntriga++;

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

            try (//
                    FileOutputStream out = new FileOutputStream(pdf)) {
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
