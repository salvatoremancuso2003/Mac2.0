/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rc.so.pdf;

import static com.itextpdf.text.BaseColor.BLUE;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import static com.itextpdf.text.Element.ALIGN_LEFT;
import static com.itextpdf.text.Element.ALIGN_RIGHT;
import com.itextpdf.text.Font;
import static com.itextpdf.text.Font.BOLD;
import static com.itextpdf.text.Font.BOLDITALIC;
import static com.itextpdf.text.Font.NORMAL;
import static com.itextpdf.text.Font.UNDERLINE;
import static com.itextpdf.text.FontFactory.getFont;
import com.itextpdf.text.Image;
import static com.itextpdf.text.Image.getInstance;
import static com.itextpdf.text.PageSize.A4;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import static com.itextpdf.text.pdf.BaseFont.HELVETICA;
import static com.itextpdf.text.pdf.BaseFont.WINANSI;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import static com.itextpdf.text.pdf.PdfWriter.getInstance;
import rc.so.db.Db_Master;
import rc.so.entity.Branch;
import rc.so.entity.Ch_transaction;
import static rc.so.entity.Ch_transaction.formatType;
import rc.so.entity.Ch_transaction_value;
import rc.so.entity.Client;
import rc.so.entity.Figures;
import rc.so.entity.Office;
import static rc.so.util.Constant.decimal;
import static rc.so.util.Constant.patternhours_d1;
import static rc.so.util.Constant.patternnormdate_filter;
import static rc.so.util.Constant.patternsqldate;
import static rc.so.util.Engine.getConf;
import static rc.so.util.Engine.get_figures;
import static rc.so.util.Engine.get_national_office;
import static rc.so.util.Engine.insertTR;
import static rc.so.util.Utility.fd;
import static rc.so.util.Utility.formatDoubleforMysql;
import static rc.so.util.Utility.formatMysqltoDisplay;
import static rc.so.util.Utility.formatStringtoStringDate;
import static rc.so.util.Utility.getBase64;
import static rc.so.util.Utility.parseHTMLtoPDF;
import static rc.so.util.Utility.roundDoubleandFormat;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import static java.lang.Thread.currentThread;
import java.util.ArrayList;
import java.util.List;
import static org.apache.commons.codec.binary.Base64.decodeBase64;
import org.joda.time.DateTime;

/**
 *
 * @author rcosco
 */
public class NewReceipt_UK {

    Phrase vuoto = new Phrase("\n");
    PdfPCell cellempty;

    /**
     *
     */
    public static final String br = "\n";

    /**
     *
     */
    public static final String blank = " ";

    /**
     *
     */
    public static final String nbsp = " ";

    /**
     *
     */
    public static final String comma = ",";

    /**
     *
     */
    public static final String dot = ".";

    /**
     *
     */
    public static final String score = "-";

    /**
     *
     */
    public static final String percent = "%";

    Font f0_bold, f1_bold, f2_bold, f1_normal, f2_normal, f3_normal, f3_bold, f4_bold, f5_normal, f5_bold, f6_normal, f6_bold;
    Font f3_italic;
    Font f4_boldBL;
    Font f0_boldBL;

    /**
     * Constructor
     */
    public NewReceipt_UK() {
        this.f0_bold = getFont(HELVETICA, WINANSI, 11.04f, BOLD | UNDERLINE);
        this.f0_boldBL = getFont(HELVETICA, WINANSI, 25.04f, BOLD | UNDERLINE, BLUE);
        this.f1_bold = getFont(HELVETICA, WINANSI, 9.96f, BOLD);
        this.f2_bold = getFont(HELVETICA, WINANSI, 8f, BOLD);
        this.f3_bold = getFont(HELVETICA, WINANSI, 8f, BOLDITALIC);
        this.f4_bold = getFont(HELVETICA, WINANSI, 6.96f, BOLD);
        this.f4_boldBL = getFont(HELVETICA, WINANSI, 6.96f, BOLD | UNDERLINE, BLUE);
        this.f5_bold = getFont(HELVETICA, WINANSI, 6f, BOLD);
        this.f1_normal = getFont(HELVETICA, WINANSI, 9.96f, NORMAL);
        this.f2_normal = getFont(HELVETICA, WINANSI, 8f, NORMAL);
        this.f3_normal = getFont(HELVETICA, WINANSI, 6.96f, NORMAL);
        this.f5_normal = getFont(HELVETICA, WINANSI, 6f, NORMAL);
        this.f6_normal = getFont(HELVETICA, WINANSI, 5.5f, NORMAL);
        this.f6_bold = getFont(HELVETICA, WINANSI, 5.5f, BOLD);
        this.f3_italic = getFont("Helvetica", "Cp1252", 6.96F, 2);
        this.cellempty = new PdfPCell(new Phrase("\n"));
        this.cellempty.setBorder(0);
    }

    /**
     *
     */
    public static final float[] columnWidths3 = new float[]{40f, 40f, 30f};

    /**
     *
     */
    public static final float[] columnWidths8 = new float[]{40f, 22f, 28f, 30f, 20f, 30f, 30f, 35f};

    private String formatSupportoPAG_uk(String su01) {
        
        
        
        switch (su01) {
            case "01":
            case "-":
                return "CASH";
            case "04":
            case "06":
            case "07":
                return "CREDIT CARD";
            default:
                return su01;
        }
    }

    /**
     *
     * @param pathout
     * @param ma
     * @param tra
     * @param cl
     * @param livalue
     * @param bra
     * @param fig
     * @return
     */
    public String ricevuta_UK(String pathout, Office ma, Ch_transaction tra, Client cl, ArrayList<Ch_transaction_value> livalue, Branch bra, ArrayList<Figures> fig) {

        try {
            String outputfile = new DateTime().toString("yyMMddhhmmssSSS") + "_UK_" + tra.getTipotr() + ".pdf";

            File pdf = new File(pathout + outputfile);
            Document document = new Document(A4, 20, 20, 20, 20);
            try(OutputStream ou = new FileOutputStream(pdf)){
            PdfWriter wr = getInstance(document, ou);
            document.open();

            PdfPTable table = new PdfPTable(3);
            table.setWidths(columnWidths3);
            table.setWidthPercentage(100.0F);

            PdfPCell cell1 = new PdfPCell();
            cell1.setBorder(0);
            cell1.setPadding(0);
            cell1.addElement(new Phrase(ma.getDe_office().toUpperCase(), f4_bold));
            cell1.addElement(new Phrase((ma.getAdd_via()).toUpperCase(), f4_bold));
            cell1.addElement(new Phrase((ma.getAdd_cap() + nbsp + ma.getAdd_city()).toUpperCase(), f4_bold));
            cell1.addElement(new Phrase(("Company number " + ma.getVat()).toUpperCase(), f4_bold));
            table.addCell(cell1);

            cell1 = new PdfPCell();
            cell1.setBorder(0);
            cell1.setPadding(0);
            cell1.addElement(new Phrase(bra.getCod() + " " + bra.getDe_branch().toUpperCase(), f4_bold));
            cell1.addElement(new Phrase((bra.getAdd_via()).toUpperCase(), f4_bold));
            cell1.addElement(new Phrase((bra.getAdd_cap() + nbsp + bra.getAdd_city()).toUpperCase(), f4_bold));
            table.addCell(cell1);

            Image img = getInstance(decodeBase64(getConf("path.logocl")));
            img.scalePercent(40.0F);
            cell1 = new PdfPCell(img);
            cell1.setBorder(0);
            cell1.setPadding(0);
            cell1.setHorizontalAlignment(ALIGN_RIGHT);
            table.addCell(cell1);
            table.addCell(cellempty);
            table.addCell(cellempty);
            table.addCell(cellempty);
            table.addCell(cellempty);
            table.addCell(cellempty);

            cell1 = new PdfPCell();
            cell1.setBorder(0);
            cell1.setPadding(0);

            Paragraph p1 = new Paragraph(new Phrase("Transaction nr: " + tra.getId() + nbsp + tra.getData().substring(0, 4) + nbsp + tra.getFiliale() + "\n"
                    + "Fidelity code: " + tra.getFiliale() + tra.getId(), f5_bold));
            p1.setAlignment(ALIGN_RIGHT);
            cell1.addElement(p1);
            cell1.setHorizontalAlignment(ALIGN_RIGHT);
            table.addCell(cell1);

            cell1 = new PdfPCell();
            cell1.setBorder(0);
            cell1.setPadding(0);
            cell1.addElement(new Phrase("Date: " + formatStringtoStringDate(tra.getData(), patternsqldate, patternnormdate_filter)
                    + "    Time: " + formatStringtoStringDate(tra.getData(), patternsqldate, patternhours_d1), f4_bold));
            table.addCell(cell1);
            cell1 = new PdfPCell();
            cell1.setBorder(0);
            cell1.setPadding(0);
            
            Figures myfig1;
            if(tra.getTipotr().equals("B")){
                myfig1 = get_figures(fig, "01");
            }else{
                myfig1 = get_figures(fig, tra.getLocalfigures());
            }
            
            cell1.addElement(new Phrase(
                    "USER: " + tra.getUser()
                    + "    " + myfig1.getDe_supporto().toUpperCase()
                    + "     " + formatType(tra.getTipotr()).toUpperCase(),
                    f4_bold));
            table.addCell(cell1);
            
            
            table.addCell(cellempty);
            table.addCell(cellempty);
            table.addCell(cellempty);

            cell1 = new PdfPCell();
            cell1.setPadding(0);
            cell1.setBorder(0);
            p1 = new Paragraph(new Phrase("Total net: £ " + formatMysqltoDisplay(tra.getPay()), f1_bold));
            p1.setAlignment(ALIGN_RIGHT);
            cell1.addElement(p1);
            cell1.setHorizontalAlignment(ALIGN_RIGHT);

            table.addCell(cell1);

            table.addCell(cellempty);
            table.addCell(cellempty);
            table.addCell(cellempty);

            document.add(table);

            table = new PdfPTable(8);
            table.setWidths(columnWidths8);
            table.setWidthPercentage(100.0F);

            cell1 = new PdfPCell();
            cell1.setPadding(0);
            cell1.setBorder(0);
            p1 = new Paragraph(new Phrase("Category", f3_bold));
            p1.setAlignment(ALIGN_LEFT);
            cell1.addElement(p1);
            table.addCell(cell1);

            cell1 = new PdfPCell();
            cell1.setPadding(0);
            cell1.setBorder(0);
            p1 = new Paragraph(new Phrase("Currency", f3_bold));
            p1.setAlignment(ALIGN_LEFT);
            cell1.addElement(p1);
            table.addCell(cell1);

            cell1 = new PdfPCell();
            cell1.setPadding(0);
            cell1.setBorder(0);
            p1 = new Paragraph(new Phrase("Quantity", f3_bold));
            p1.setAlignment(ALIGN_LEFT);
            cell1.addElement(p1);
            table.addCell(cell1);

            cell1 = new PdfPCell();
            cell1.setPadding(0);
            cell1.setBorder(0);
            p1 = new Paragraph(new Phrase("Rate", f3_bold));
            p1.setAlignment(ALIGN_LEFT);
            cell1.addElement(p1);
            table.addCell(cell1);

            cell1 = new PdfPCell();
            cell1.setPadding(0);
            cell1.setBorder(0);
            p1 = new Paragraph(new Phrase("% Fee", f3_bold));
            p1.setAlignment(ALIGN_LEFT);
            cell1.addElement(p1);
            table.addCell(cell1);

            cell1 = new PdfPCell();
            cell1.setPadding(0);
            cell1.setBorder(0);
            p1 = new Paragraph(new Phrase("Fixed fee", f3_bold));
            p1.setAlignment(ALIGN_LEFT);
            cell1.addElement(p1);
            table.addCell(cell1);

            cell1 = new PdfPCell();
            cell1.setPadding(0);
            cell1.setBorder(0);
            if (tra.getTipotr().equals("B")) {
                p1 = new Paragraph(new Phrase("Sell back", f3_bold));
            } else {
                p1 = new Paragraph(new Phrase("Buy back", f3_bold));
            }

            p1.setAlignment(ALIGN_LEFT);
            cell1.addElement(p1);
            table.addCell(cell1);

            cell1 = new PdfPCell();
            cell1.setPadding(0);
            cell1.setBorder(0);
            p1 = new Paragraph(new Phrase("Net", f3_bold));
            p1.setAlignment(ALIGN_LEFT);
            cell1.addElement(p1);
            table.addCell(cell1);

            for (int i = 0; i < livalue.size(); i++) {
                Ch_transaction_value chv = livalue.get(i);
                Figures myfig = get_figures(fig, chv.getSupporto());

                cell1 = new PdfPCell();
                cell1.setPadding(0);
                cell1.setBorder(0);
                p1 = new Paragraph(new Phrase(myfig.getDe_supporto().toUpperCase(), f5_normal));
                p1.setAlignment(ALIGN_LEFT);
                cell1.addElement(p1);
                table.addCell(cell1);

                cell1 = new PdfPCell();
                cell1.setPadding(0);
                cell1.setBorder(0);
                p1 = new Paragraph(new Phrase(chv.getValuta().toUpperCase(), f5_normal));
                p1.setAlignment(ALIGN_LEFT);
                cell1.addElement(p1);
                table.addCell(cell1);

                cell1 = new PdfPCell();
                cell1.setPadding(0);
                cell1.setBorder(0);
                p1 = new Paragraph(new Phrase(formatMysqltoDisplay(chv.getQuantita()), f5_normal));
                p1.setAlignment(ALIGN_LEFT);
                cell1.addElement(p1);
                table.addCell(cell1);

                cell1 = new PdfPCell();
                cell1.setPadding(0);
                cell1.setBorder(0);
                p1 = new Paragraph(new Phrase(formatMysqltoDisplay(chv.getRate()), f5_normal));
                p1.setAlignment(ALIGN_LEFT);
                cell1.addElement(p1);
                table.addCell(cell1);

                cell1 = new PdfPCell();
                cell1.setPadding(0);
                cell1.setBorder(0);
                p1 = new Paragraph(new Phrase(formatMysqltoDisplay(chv.getCom_perc()), f5_normal));
                p1.setAlignment(ALIGN_LEFT);
                cell1.addElement(p1);
                table.addCell(cell1);

                boolean isbb = false;
                if (chv.getBb().equals("Y")) {
                    double comnet = 0.00;
                    if (fd((chv.getFx_com())) != 0) {
                        isbb = true;
                        comnet = fd((chv.getFx_com())) - fd(formatDoubleforMysql(myfig.getBuy_back_commission()));
                    }
                    p1 = new Paragraph(new Phrase(formatMysqltoDisplay(roundDoubleandFormat(comnet, 2)), f5_normal));

                } else {
                    p1 = new Paragraph(new Phrase(formatMysqltoDisplay(chv.getFx_com()), f5_normal));
                }
                cell1 = new PdfPCell();
                cell1.setPadding(0);
                cell1.setBorder(0);
                p1.setAlignment(ALIGN_LEFT);
                cell1.addElement(p1);
                table.addCell(cell1);

                if (chv.getBb().equals("Y")) {
                    if (isbb) {
                        p1 = new Paragraph(new Phrase(myfig.getBuy_back_commission(), f5_normal));
                    } else {
                        p1 = new Paragraph(new Phrase("0" + decimal + "00", f5_normal));
                    }
                } else {
                    p1 = new Paragraph(new Phrase("0" + decimal + "00", f5_normal));
                }
                cell1 = new PdfPCell();
                cell1.setPadding(0);
                cell1.setBorder(0);
                p1.setAlignment(ALIGN_LEFT);
                cell1.addElement(p1);
                table.addCell(cell1);

                cell1 = new PdfPCell();
                cell1.setPadding(0);
                cell1.setBorder(0);
                p1 = new Paragraph(new Phrase(formatMysqltoDisplay(chv.getNet()), f5_normal));
                p1.setAlignment(ALIGN_LEFT);
                cell1.addElement(p1);
                table.addCell(cell1);

            }

            document.add(table);

            document.add(vuoto);

            table = new PdfPTable(1);
            table.setWidthPercentage(100.0F);

//            
//            
//            cell1 = new PdfPCell();
//            cell1.setPadding(0);
//            cell1.setBorder(0);
//
//            p1 = new Paragraph();
//            p1.add(new Phrase("Thank you for your visit and good bye. Contact us at ", this.f4_bold));
//            p1.add(new Phrase("info@maccorp.co.uk", this.f4_boldBL));
//            p1.add(new Phrase(".", this.f4_bold));
//            p1.setAlignment(Rectangle.ALIGN_LEFT);
//            cell1.addElement(p1);
//            table.addCell(cell1);
            Office of = get_national_office();

            cell1 = new PdfPCell();
            cell1.setPadding(0);
            cell1.setBorder(0);
            List<Element> p = parseHTMLtoPDF(of.getTxt_ricev_1());
            for (int k = 0; k < p.size(); k++) {
                cell1.addElement(p.get(k));
            }
            table.addCell(cell1);

            if (tra.getBb().equals("1") || tra.getBb().equals("2") || tra.getBb().equals("3") || tra.getBb().equals("4")) {
                table.addCell(cellempty);
                cell1 = new PdfPCell();
                cell1.setPadding(0);
                cell1.setBorder(0);

                p = parseHTMLtoPDF(of.getTxt_ricev_bb());
                for (int k = 0; k < p.size(); k++) {
                    cell1.addElement(p.get(k));
                }
                table.addCell(cell1);
            }

            table.addCell(cellempty);

            cell1 = new PdfPCell();
            cell1.setPadding(0);
            cell1.setBorder(0);
            p = parseHTMLtoPDF(of.getTxt_ricev_2());
            for (int k = 0; k < p.size(); k++) {
                cell1.addElement(p.get(k));
            }
            table.addCell(cell1);

//            cell1 = new PdfPCell();
//            cell1.setPadding(0);
//            cell1.setBorder(0);
//            p1 = new Paragraph();
//            p1.add(new Phrase("Other services available at Forexchange: Buy back, Sell back, on line currency booking service, currency home delivery, Western Union, VAT refund, City tours, Bus tickets, SIM cards.", this.f1_normal));
//            p1.setAlignment(Rectangle.ALIGN_LEFT);
//            cell1.addElement(p1);
//            table.addCell(cell1);
//            table.addCell(cellempty);
//            if (tra.getBb().equals("1") || tra.getBb().equals("2")) {
//                cell1 = new PdfPCell();
//                cell1.setPadding(0);
//                cell1.setBorder(0);
//                p1 = new Paragraph();
//                p1.add(new Phrase("SHOW THIS RECEIPT ON YOUR NEXT TRANSACTION AND YOU CAN CHANGE COMMISSION FREE!", this.f0_bold));
//                p1.setAlignment(Rectangle.ALIGN_CENTER);
//                cell1.addElement(p1);
//                cell1.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
//                table.addCell(cell1);
//            }
//            document.add(table);
//            document.add(vuoto);
//            document.add(vuoto);
//
//            table = new PdfPTable(1);
//            table.setWidthPercentage(100.0F);
//
//            cell1 = new PdfPCell();
//            cell1.setPadding(0);
//            cell1.setBorder(0);
//
//            p1 = new Paragraph(new Phrase("Book your travel money now", this.f2_normal));
//            p1.setAlignment(Rectangle.ALIGN_CENTER);
//            cell1.addElement(p1);
//            cell1.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
//            table.addCell(cell1);
//
//            table.addCell(cellempty);
//
//            cell1 = new PdfPCell();
//            cell1.setPadding(0);
//            cell1.setBorder(0);
//            p1 = new Paragraph(new Phrase("www.forexchange.co.uk", this.f0_boldBL));
//            p1.setAlignment(Rectangle.ALIGN_CENTER);
//            cell1.addElement(p1);
//            cell1.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
//            table.addCell(cell1);
//
            document.add(table);

            document.close();
            wr.close();
            ou.close();
            String base64 = getBase64(pdf, tra);
            pdf.delete();
            return base64;
}
        } catch (FileNotFoundException ex) {
            insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
        } catch (DocumentException | IOException ex) {
            insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
        }
        return null;
    }

    /**
     *
     * @param args
     */
    public static void main(String[] args) {

        String cod = "3501903281321552098vpwAeb";

        Db_Master db = new Db_Master();
        String pathtemp = db.getPath("temp");
        Ch_transaction tra = db.query_transaction_ch(cod);
        if (tra == null) {
            tra = db.query_transaction_ch_temp(cod);
        }
        ArrayList<Ch_transaction_value> li = db.query_transaction_value(cod);
        Client cl = db.query_Client_transaction(cod, "---");
        Office ma = db.get_national_office();
        ArrayList<Figures> fig = db.list_all_figures();
        Branch bra = db.get_branch(tra.getFiliale());
        db.closeDB();

        new NewReceipt_UK().ricevuta_UK(pathtemp, ma, tra, cl, li, bra, fig);

    }

}
