/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rc.so.pdf;

import static com.google.common.base.Splitter.on;
import com.itextpdf.text.BaseColor;
import static com.itextpdf.text.BaseColor.DARK_GRAY;
import static com.itextpdf.text.BaseColor.LIGHT_GRAY;
import static com.itextpdf.text.BaseColor.WHITE;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import static com.itextpdf.text.Element.ALIGN_CENTER;
import static com.itextpdf.text.Element.ALIGN_LEFT;
import static com.itextpdf.text.Element.ALIGN_MIDDLE;
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
import static com.itextpdf.text.Rectangle.BOX;
import static com.itextpdf.text.Rectangle.LEFT;
import static com.itextpdf.text.Rectangle.NO_BORDER;
import static com.itextpdf.text.Rectangle.RIGHT;
import static com.itextpdf.text.Rectangle.TOP;
import static com.itextpdf.text.pdf.BaseFont.HELVETICA;
import static com.itextpdf.text.pdf.BaseFont.WINANSI;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import static com.itextpdf.text.pdf.PdfWriter.getInstance;
import rc.so.db.Db_Master;
import rc.so.entity.Agency;
import rc.so.entity.Booking;
import rc.so.entity.Branch;
import rc.so.entity.Ch_transaction;
import static rc.so.entity.Ch_transaction.formatSTATUS_BBSB;
import static rc.so.entity.Ch_transaction.formatType;
import rc.so.entity.Ch_transaction_value;
import rc.so.entity.Client;
import static rc.so.entity.Client_CZ.formatYN;
import rc.so.entity.Codici_sblocco;
import static rc.so.entity.Codici_sblocco.format_ty_util;
import rc.so.entity.Company;
import rc.so.entity.Currency;
import rc.so.entity.CustomerKind;
import rc.so.entity.Figures;
import rc.so.entity.NC_causal;
import rc.so.entity.NC_transaction;
import rc.so.entity.Office;
import rc.so.entity.Openclose;
import rc.so.entity.Till;
import rc.so.entity.VATcode;
import static rc.so.util.Constant.decimal;
import static rc.so.util.Constant.is_CZ;
import static rc.so.util.Constant.is_IT;
import static rc.so.util.Constant.newpread;
import static rc.so.util.Constant.patternnormdate;
import static rc.so.util.Constant.patternsqldate;
import static rc.so.util.Engine.agevolazioni_varie;
import static rc.so.util.Engine.credit_card;
import static rc.so.util.Engine.formatALCurrency;
import static rc.so.util.Engine.formatSex;
import static rc.so.util.Engine.getCity_apm;
import static rc.so.util.Engine.getCod_tr;
import static rc.so.util.Engine.getConf;
import static rc.so.util.Engine.getCurrency;
import static rc.so.util.Engine.getNC_causal;
import static rc.so.util.Engine.getRespCod;
import static rc.so.util.Engine.get_Companylist;
import static rc.so.util.Engine.get_agency;
import static rc.so.util.Engine.get_branch;
import static rc.so.util.Engine.get_country;
import static rc.so.util.Engine.get_customerKind;
import static rc.so.util.Engine.get_district;
import static rc.so.util.Engine.get_figures;
import static rc.so.util.Engine.get_national_office;
import static rc.so.util.Engine.get_prenot;
import static rc.so.util.Engine.get_single_Till;
import static rc.so.util.Engine.identificationCard;
import static rc.so.util.Engine.insertTR;
import static rc.so.util.Engine.internetbooking_ch;
import static rc.so.util.Engine.kind_payment;
import static rc.so.util.Engine.kindcommissionefissa;
import static rc.so.util.Engine.list_bankAccount;
import static rc.so.util.Engine.list_internetbooking;
import static rc.so.util.Engine.list_nc_causal_sell;
import static rc.so.util.Engine.list_nctransaction_wc_ti;
import static rc.so.util.Engine.list_nctransactionfromchange;
import static rc.so.util.Engine.list_only_company;
import static rc.so.util.Engine.query_Client_transaction;
import static rc.so.util.Engine.query_LOY_transaction;
import static rc.so.util.Engine.query_oc;
import static rc.so.util.Engine.query_transaction_ch;
import static rc.so.util.Engine.query_transaction_value;
import static rc.so.util.Engine.servizi_agg;
import static rc.so.util.Engine.undermincommjustify;
import static rc.so.util.Engine.unlockratejustify;
import static rc.so.util.Utility.fd;
import static rc.so.util.Utility.formatAL;
import static rc.so.util.Utility.formatDoubleforMysql;
import static rc.so.util.Utility.formatMysqltoDisplay;
import static rc.so.util.Utility.formatStringtoStringDate;
import static rc.so.util.Utility.getBase64;
import static rc.so.util.Utility.parseDoubleR;
import static rc.so.util.Utility.parseHTMLtoPDF;
import static rc.so.util.Utility.roundDoubleandFormat;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import static java.lang.Thread.currentThread;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import static org.apache.commons.codec.binary.Base64.decodeBase64;
import static org.apache.commons.codec.binary.Base64.encodeBase64;
import static org.apache.commons.io.FileUtils.readFileToByteArray;
import static org.apache.commons.lang3.StringUtils.replace;
import static org.apache.commons.text.StringEscapeUtils.unescapeHtml4;
import org.joda.time.DateTime;

/**
 *
 * @author rcosco
 */
public class Receipt {

    /**
     *
     */
    public static final float[] columnWidths00 = new float[]{80f, 30f};

    /**
     *
     */
    public static final float[] columnWidths0 = new float[]{30f, 80f};

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
    public static final float[] columnWidths3A = new float[]{30f, 60f, 30f};

    /**
     *
     */
    public static final float[] columnWidths6 = {30.0F, 50.0F, 20.0F};

    /**
     *
     */
    public static final float[] columnWidths3 = {50.0F, 80.0F, 40.0F, 50.0F, 30.0F, 30.0F, 30.0F, 50.0F};

    /**
     *
     */
    public static final float[] columnWidths7 = {35.0F, 65.0F};

    /**
     *
     */
    public static final float[] columnWidths4 = new float[]{60f, 60f, 60f, 60f};

    /**
     *
     */
    public static final float[] columnWidths4A = new float[]{60f, 60f, 30f, 30f};

    /**
     *
     */
    public static final float[] columnWidths5 = new float[]{60f, 60f, 60f, 60f, 60f};

    /**
     *
     */
    public static final float[] columnWidths3B = new float[]{60f, 60f, 120f};

    /**
     *
     */
    public static final float[] columnWidths3C = new float[]{60f, 60f, 60f};

    final String intestazionePdf = "To Branching Sheet";
    Phrase vuoto = new Phrase("\n");
    PdfPCell cellempty = new PdfPCell(new Phrase("\n"));

    /**
     *
     */
    public static final String br = "\n";

    /**
     *
     */
    public static final String blank = " ";

    Font f0_bold, f1_bold, f1_boldW, f2_bold, f1_normal, f2_normal, f3_normal, f3_bold, f4_bold, f5_normal, f5_bold, f6_normal, f6_bold;

    Font f3_italic;
    Font sign;

    /**
     * Constructor
     */
    public Receipt() {
        this.f0_bold = getFont(HELVETICA, WINANSI, 14.04f, BOLD);
        this.f1_bold = getFont(HELVETICA, WINANSI, 9.96f, BOLD);
        this.f1_boldW = getFont(HELVETICA, WINANSI, 9.96f, BOLD, WHITE);
        this.f2_bold = getFont(HELVETICA, WINANSI, 8f, BOLD);
        this.f3_bold = getFont(HELVETICA, WINANSI, 8f, BOLDITALIC);
        this.f4_bold = getFont(HELVETICA, WINANSI, 6.96f, BOLD);
        this.f1_normal = getFont(HELVETICA, WINANSI, 9.96f, NORMAL);
        this.f2_normal = getFont(HELVETICA, WINANSI, 8f, NORMAL);
        this.f3_normal = getFont(HELVETICA, WINANSI, 6.96f, NORMAL);
        this.f5_normal = getFont(HELVETICA, WINANSI, 6f, NORMAL);
        this.f5_bold = getFont(HELVETICA, WINANSI, 6f, BOLD);
        this.f6_normal = getFont(HELVETICA, WINANSI, 5.5f, NORMAL);
        this.f6_bold = getFont(HELVETICA, WINANSI, 5.5f, BOLD);
        this.f3_italic = getFont("Helvetica", "Cp1252", 6.96F, 2);
        this.sign = getFont("Helvetica", "Cp1252", 5.5F, 0, WHITE);
    }

    /**
     *
     * @param pathout
     * @param ma
     * @param tra
     * @param cl
     * @param livalue
     * @param br
     * @param fig
     * @param cur
     * @param marketing
     * @return
     */
    public String print_receipt_comm_not_zero(String pathout,
            Office ma, Ch_transaction tra,
            Client cl, ArrayList<Ch_transaction_value> livalue,
            Branch br,
            ArrayList<Figures> fig, ArrayList<Currency> cur, boolean marketing) {

        String textfidcode = "Fidelity Code:";
        String fidcode = tra.getFiliale() + tra.getId();
        String dateoper = tra.getData();
        String codoper = tra.getUser();
        String kind = "BANCONOTE / NOTES";
        if (tra.getTipotr().equals("S")) {
            kind = get_figures(fig, tra.getLocalfigures()).getDe_supporto();
        }
        String type = tra.formatType_pdf(tra.getTipotr());
        String clientname = cl.getCognome().toUpperCase() + " " + cl.getNome().toUpperCase();
        if (tra.getTipocliente().equals("003")) {
            Db_Master db1 = new Db_Master();
            String soc = db1.getNDGSocieta(cl.getCode());
            Company c = db1.get_Company(soc);
            db1.closeDB();
            if (c != null) {
                clientname = c.getRagione_sociale().toUpperCase();
            }
        }
        String netvalue = tra.getPay();
        String valuta = "€";

        try {

            String outputfile = new DateTime().toString("yyMMddhhmmssSSS") + "_receipt_commze.pdf";
            File pdf = new File(pathout + outputfile);
            Document document = new Document(A4, 20, 20, 20, 20);
            try(OutputStream ou = new FileOutputStream(pdf)){
            PdfWriter wr = getInstance(document, ou);
            document.open();
            PdfPTable table = new PdfPTable(3);
            table.setWidths(columnWidths3A);
            table.setWidthPercentage(100.0F);
            PdfPTable table1 = new PdfPTable(1);
            table1.setWidthPercentage(100.0F);
            Phrase phrase1 = new Phrase(ma.getDe_office(), f4_bold);
            PdfPCell cell1 = new PdfPCell();
            cell1.setBorder(0);
            cell1.setPadding(0);
            cell1.addElement(phrase1);
            table1.addCell(cell1);
            phrase1 = new Phrase(ma.getAdd_via(), f4_bold);
            cell1 = new PdfPCell();
            cell1.setBorder(0);
            cell1.setPadding(0);
            cell1.addElement(phrase1);
            table1.addCell(cell1);
            phrase1 = new Phrase(ma.getAdd_cap() + " " + ma.getAdd_city(), f4_bold);
            cell1 = new PdfPCell();
            cell1.setBorder(0);
            cell1.setPadding(0);
            cell1.addElement(phrase1);
            table1.addCell(cell1);
            phrase1 = new Phrase("P.IVA/C.F.: " + ma.getVat(), f4_bold);
            cell1 = new PdfPCell();
            cell1.setBorder(0);
            cell1.setPadding(0);
            cell1.addElement(phrase1);
            table1.addCell(cell1);
            cell1 = new PdfPCell();
            cell1.setBorder(0);
            cell1.setPadding(0);
            cell1.addElement(table1);
            table.addCell(cell1);

            table1 = new PdfPTable(1);
            table1.setWidthPercentage(100.0F);
            cell1 = new PdfPCell();
            cell1.setBorder(0);
            phrase1 = new Phrase(br.getDe_branch(), f2_bold);
            cell1.addElement(phrase1);
            table1.addCell(cell1);
            cell1 = new PdfPCell();
            cell1.setBorder(0);
            phrase1 = new Phrase((br.getAdd_via() + " " + br.getAdd_cap() + " " + br.getAdd_city()).trim(), f2_bold);
            cell1.addElement(phrase1);
            table1.addCell(cell1);

            cell1 = new PdfPCell();
            cell1.setBorder(0);
            cell1.addElement(table1);
            table.addCell(cell1);

            Image img = getInstance(decodeBase64(getConf("path.logocl")));
            img.scalePercent(40.0F);
            cell1 = new PdfPCell(img);
            cell1.setBorder(0);
            cell1.setHorizontalAlignment(ALIGN_RIGHT);
            table.addCell(cell1);
            document.add(table);

            table = new PdfPTable(2);
            table.setWidths(columnWidths1);
            table.setWidthPercentage(100.0F);
            cell1 = new PdfPCell(new Phrase("   "));
            cell1.setBorder(0);
            Paragraph pa = new Paragraph(new Phrase(textfidcode + " " + fidcode, this.f2_bold));
            pa.setAlignment(2);
            PdfPCell cell2 = new PdfPCell(pa);
            cell2.setBorder(0);
            cell2.setHorizontalAlignment(2);
            cell1 = new PdfPCell(new Phrase("   "));
            cell1.setBorder(0);
            table.addCell(cell1);
            table.addCell(cell2);
            document.add(table);
            table = new PdfPTable(1);
            table.setWidthPercentage(100.0F);
            table1 = new PdfPTable(4);
            table1.setWidthPercentage(100.0F);

            Phrase phrase = new Phrase();
            phrase.add(new Chunk("Data/Date: ", this.f2_bold));
            phrase.add(new Chunk(formatStringtoStringDate(dateoper, patternsqldate, patternnormdate), this.f2_normal));
            PdfPCell cell3 = new PdfPCell(phrase);
            cell3.setBorder(0);
            table1.addCell(cell3);

            phrase1 = new Phrase();
            phrase1.add(new Chunk("N.Operatore/Cashier: ", this.f2_bold));
            phrase1.add(new Chunk(codoper, this.f2_normal));
            cell3 = new PdfPCell(phrase1);
            cell3.setBorder(0);
            table1.addCell(cell3);

            cell3 = new PdfPCell(new Phrase(kind, this.f2_bold));
            cell3.setHorizontalAlignment(2);
            cell3.setBorder(0);
            table1.addCell(cell3);

            cell3 = new PdfPCell(new Phrase(type, this.f2_bold));
            cell3.setHorizontalAlignment(2);
            cell3.setBorder(0);
            table1.addCell(cell3);
            phrase = new Phrase();
            phrase.add(new Chunk("Cliente / Customer: ", this.f2_bold));
            phrase.add(new Chunk(clientname, this.f2_normal));
            cell3 = new PdfPCell(phrase);
            cell3.setColspan(2);
            cell3.setVerticalAlignment(5);
            cell3.setBorder(0);
            table1.addCell(cell3);

            cell3 = new PdfPCell(new Phrase("Totale Netto: € \nNet Value: € ", this.f2_bold));
            cell3.setHorizontalAlignment(2);
            cell3.setBorder(0);
            table1.addCell(cell3);

            cell3 = new PdfPCell(new Phrase(formatMysqltoDisplay(netvalue), this.f0_bold));
            cell3.setHorizontalAlignment(2);
            cell3.setBorder(0);
            table1.addCell(cell3);
            phrase = new Phrase();
            String city[] = getCity_apm(cl.getCitta());
            phrase.add(new Chunk((cl.getIndirizzo() + " " + city[1] + " " + get_country(cl.getNazione())[1]).toUpperCase(), this.f2_bold));
            cell3 = new PdfPCell(phrase);
            cell3.setColspan(4);
            cell3.setVerticalAlignment(5);
            cell3.setBorder(0);
            table1.addCell(cell3);

            String restochangeeti = " ";
            String restochangeval = " ";

            if (tra.getIntbook_type().equals("1")) {
                if (fd(tra.getIntbook_mac()) != 0.0) {
                    restochangeeti = "Resto/Change : " + valuta;
                    restochangeval = formatMysqltoDisplay(roundDoubleandFormat((fd(tra.getIntbook_mac()) * (-1.0)), 2));
                }
            }

            cell3 = new PdfPCell(new Phrase(restochangeeti, this.f2_bold));
            cell3.setHorizontalAlignment(2);
            cell3.setBorder(0);
            table1.addCell(cell3);
            cell3 = new PdfPCell(new Phrase(restochangeval, this.f2_bold));
            cell3.setHorizontalAlignment(2);
            cell3.setBorder(0);
            table1.addCell(cell3);

            phrase = new Phrase();
            phrase.add(new Chunk("Note: " + tra.getNote(), this.f2_bold));
            cell3 = new PdfPCell(phrase);
            cell3.setColspan(2);
            cell3.setVerticalAlignment(5);
            cell3.setBorder(0);
            table1.addCell(cell3);
            cell3 = new PdfPCell(new Phrase(" ", this.f2_bold));
            cell3.setHorizontalAlignment(2);
            cell3.setBorder(0);
            table1.addCell(cell3);
            cell3 = new PdfPCell(new Phrase(" ", this.f2_bold));
            cell3.setHorizontalAlignment(2);
            cell3.setBorder(0);
            table1.addCell(cell3);

            cell1 = new PdfPCell(table1);
            cell1.setBorder(15);
            cell1.setBorderWidth(0.6F);
            table.addCell(cell1);
            document.add(table);
            PdfPTable table2 = new PdfPTable(8);
            table2.setWidthPercentage(100.0F);
            table2.setWidths(columnWidths3);
            cell3 = new PdfPCell(new Phrase("Categoria", this.f2_bold));
            cell3.setHorizontalAlignment(0);
            cell3.setBorder(0);
            table2.addCell(cell3);
            cell3 = new PdfPCell(new Phrase("Valuta", this.f2_bold));
            cell3.setHorizontalAlignment(0);
            cell3.setBorder(0);
            table2.addCell(cell3);
            cell3 = new PdfPCell(new Phrase("Quantità", this.f2_bold));
            cell3.setHorizontalAlignment(0);
            cell3.setBorder(0);
            table2.addCell(cell3);
            cell3 = new PdfPCell(new Phrase("Cambio", this.f2_bold));
            cell3.setHorizontalAlignment(0);
            cell3.setBorder(0);
            table2.addCell(cell3);
            cell3 = new PdfPCell(new Phrase("% Com", this.f2_bold));
            cell3.setHorizontalAlignment(0);
            cell3.setBorder(0);
            table2.addCell(cell3);
            cell3 = new PdfPCell(new Phrase("C.Fissa", this.f2_bold));
            cell3.setHorizontalAlignment(0);
            cell3.setBorder(0);
            table2.addCell(cell3);
            if (tra.getBb().equals("1") || tra.getBb().equals("2")) {
                cell3 = new PdfPCell(new Phrase("Buy Back", this.f2_bold));
            } else {
                cell3 = new PdfPCell(new Phrase("", this.f2_bold));
            }
            cell3.setHorizontalAlignment(0);
            cell3.setBorder(0);
            table2.addCell(cell3);

            cell3 = new PdfPCell(new Phrase("Netto", this.f2_bold));
            cell3.setHorizontalAlignment(2);
            cell3.setBorder(0);
            table2.addCell(cell3);

            cell3 = new PdfPCell(new Phrase("Category", this.f3_italic));
            cell3.setHorizontalAlignment(0);
            cell3.setBorder(0);
            table2.addCell(cell3);
            cell3 = new PdfPCell(new Phrase("Currency", this.f3_italic));
            cell3.setHorizontalAlignment(0);
            cell3.setBorder(0);
            table2.addCell(cell3);
            cell3 = new PdfPCell(new Phrase("Quantity", this.f3_italic));
            cell3.setHorizontalAlignment(0);
            cell3.setBorder(0);
            table2.addCell(cell3);
            cell3 = new PdfPCell(new Phrase("Rate", this.f3_italic));
            cell3.setHorizontalAlignment(0);
            cell3.setBorder(0);
            table2.addCell(cell3);
            cell3 = new PdfPCell(new Phrase("% Fee", this.f3_italic));
            cell3.setHorizontalAlignment(0);
            cell3.setBorder(0);
            table2.addCell(cell3);
            cell3 = new PdfPCell(new Phrase("Fixed Fee", this.f3_italic));
            cell3.setHorizontalAlignment(0);
            cell3.setBorder(0);
            table2.addCell(cell3);
            if (tra.getBb().equals("1") || tra.getBb().equals("2")) {
                cell3 = new PdfPCell(new Phrase("", this.f3_italic));
            } else {
                cell3 = new PdfPCell(new Phrase(" ", this.f3_italic));
            }
            cell3.setHorizontalAlignment(0);
            cell3.setBorder(0);
            table2.addCell(cell3);
            cell3 = new PdfPCell(new Phrase("Net Value", this.f3_italic));
            cell3.setHorizontalAlignment(2);
            cell3.setBorder(0);
            table2.addCell(cell3);

            for (int x = 0; x < livalue.size(); x++) {
                Ch_transaction_value chv = livalue.get(x);
                Figures myfig = get_figures(fig, chv.getSupporto());

                cell3 = new PdfPCell(new Phrase(myfig.getDe_supporto(), this.f2_normal));
                cell3.setHorizontalAlignment(0);
                cell3.setBorder(0);
                table2.addCell(cell3);
                cell3 = new PdfPCell(new Phrase(chv.getValuta() + " - " + formatALCurrency(chv.getValuta(), cur), this.f2_normal));
                cell3.setHorizontalAlignment(0);
                cell3.setBorder(0);
                table2.addCell(cell3);
                cell3 = new PdfPCell(new Phrase(formatMysqltoDisplay(chv.getQuantita()), this.f2_normal));
                cell3.setHorizontalAlignment(0);
                cell3.setBorder(0);
                table2.addCell(cell3);
                cell3 = new PdfPCell(new Phrase(formatMysqltoDisplay(roundDoubleandFormat(fd(chv.getRate()), 8)), this.f2_normal));
                cell3.setHorizontalAlignment(0);
                cell3.setBorder(0);
                table2.addCell(cell3);
                cell3 = new PdfPCell(new Phrase(formatMysqltoDisplay(chv.getCom_perc()), this.f2_normal));
                cell3.setHorizontalAlignment(0);
                cell3.setBorder(0);
                table2.addCell(cell3);

                boolean isbb = false;
                if (chv.getBb().equals("Y")) {
                    double comnet = 0.00;
                    if (fd((chv.getFx_com())) != 0) {
                        isbb = true;
                        comnet = fd((chv.getFx_com())) - fd(formatDoubleforMysql(myfig.getBuy_back_commission()));
                    }
                    cell3 = new PdfPCell(new Phrase(formatMysqltoDisplay(roundDoubleandFormat(comnet, 2)), this.f2_normal));
                } else {
                    cell3 = new PdfPCell(new Phrase(formatMysqltoDisplay(chv.getFx_com()), this.f2_normal));
                }

                cell3.setHorizontalAlignment(0);
                cell3.setBorder(0);
                table2.addCell(cell3);
                if (chv.getBb().equals("Y")) {
                    if (isbb) {
                        cell3 = new PdfPCell(new Phrase(myfig.getBuy_back_commission(), this.f2_normal));
                    } else {
                        cell3 = new PdfPCell(new Phrase("0" + decimal + "00", this.f2_normal));
                    }
                } else {
                    cell3 = new PdfPCell(new Phrase(" ", this.f2_normal));
                }
                cell3.setHorizontalAlignment(0);
                cell3.setBorder(0);
                table2.addCell(cell3);
                cell3 = new PdfPCell(new Phrase(formatMysqltoDisplay(chv.getNet()), this.f2_normal));
                cell3.setHorizontalAlignment(2);
                cell3.setBorder(0);
                table2.addCell(cell3);
            }
            document.add(table2);

            document.add(new Phrase("\n"));
            document.add(new Phrase("\n"));

            document.add(new Paragraph("Il prezzo complessivo dell'operazione è di € / Total Price € : "
                    + formatMysqltoDisplay(roundDoubleandFormat(fd(tra.getCommission()) + fd(tra.getRound()), 2)), f3_normal));

            Office of = get_national_office();
            List<Element> p = parseHTMLtoPDF(of.getTxt_ricev_bb());

            if (tra.getBb().equals("1") || tra.getBb().equals("2")) {
                Paragraph paragraph = new Paragraph();
                for (int k = 0; k < p.size(); k++) {
                    paragraph.add((Element) p.get(k));
                }
                document.add(paragraph);
            }

            p = parseHTMLtoPDF(of.getTxt_ricev_1());
            Paragraph paragraph = new Paragraph();
            for (int k = 0; k < p.size(); k++) {
                paragraph.add((Element) p.get(k));
            }
            document.add(paragraph);

            if (cl.getPep().equals("N") || cl.getPep().equals("NO") || cl.getPep().equals("0")) {
                p = parseHTMLtoPDF(of.getTxt_nopep());
                paragraph = new Paragraph();
                for (int k = 0; k < p.size(); k++) {
                    paragraph.add((Element) p.get(k));
                }
                document.add(paragraph);
            }

            document.add(new Phrase("\n"));

            table2 = new PdfPTable(2);
            table2.setWidthPercentage(100.0F);
            table2.setWidths(columnWidths00);

            cell3 = new PdfPCell(new Phrase("Firma / Signature", this.f1_bold));
            cell3.setHorizontalAlignment(2);
            cell3.setBorder(0);
            table2.addCell(cell3);
            cell3 = new PdfPCell(new Phrase("`sigClie,uid=0,bio=0`", this.sign));
            cell3.setBorder(BOTTOM);
            table2.addCell(cell3);
            document.add(table2);
            document.add(new Phrase("\n"));

            if (marketing) {
                p = parseHTMLtoPDF(of.getTxt_ricev_2());
                paragraph = new Paragraph();
                for (int k = 0; k < p.size(); k++) {
                    paragraph.add((Element) p.get(k));
                }
                document.add(paragraph);
                document.add(new Phrase("\n"));

                table2 = new PdfPTable(2);
                table2.setWidthPercentage(100.0F);
                table2.setWidths(columnWidths00);

                cell3 = new PdfPCell(new Phrase("Firma / Signature", this.f1_bold));
                cell3.setHorizontalAlignment(2);
                cell3.setBorder(0);
                table2.addCell(cell3);
                cell3 = new PdfPCell(new Phrase("`sigCl2,uid=0,bio=0`", this.sign));
                cell3.setBorder(BOTTOM);
                table2.addCell(cell3);

                document.add(table2);

            }

            document.close();
            wr.close();
            ou.close();
            String base64 = getBase64(pdf, tra);
            pdf.delete();
            return base64;
        }
        } catch (Exception ex) {
            insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
        }
        return null;
    }

    /**
     *
     * @param pathout
     * @param ma
     * @param tra
     * @param cl
     * @param livalue
     * @param br
     * @param fig
     * @param cur
     * @param marketing
     * @return
     */
    public String print_receipt_see(String pathout, Office ma, Ch_transaction tra, Client cl, ArrayList<Ch_transaction_value> livalue, Branch br,
            ArrayList<Figures> fig, ArrayList<Currency> cur, boolean marketing) {

        String textfidcode = "Fidelity Code:";
        String fidcode = tra.getFiliale() + tra.getId();

        String dateoper = tra.getData();
        String codoper = tra.getUser();
        String kind = "BANCONOTE / NOTES";
        if (tra.getTipotr().equals("S")) {
            kind = get_figures(fig, tra.getLocalfigures()).getDe_supporto();
        }
        String type = tra.formatType_pdf(tra.getTipotr());
        double sb_value = fd(formatDoubleforMysql(fig.get(0).getSell_back_commission()));

        String city[] = getCity_apm(cl.getCitta());
        String clientname = cl.getCognome().toUpperCase() + " " + cl.getNome().toUpperCase();
        String address = (cl.getIndirizzo() + " " + city[1] + " " + get_country(cl.getNazione())[1]).toUpperCase();

        if (tra.getTipocliente().equals("003")) {
            Db_Master db1 = new Db_Master();
            String soc = db1.getNDGSocieta(cl.getCode());
            Company c = db1.get_Company(soc);
            db1.closeDB();
            if (c != null) {
                clientname = c.getRagione_sociale().toUpperCase();
                String city2[] = getCity_apm(c.getCab_comune());
                address = (c.getIndirizzo() + " " + city2[1] + " " + get_country(c.getPaese_estero_residenza())[1]).toUpperCase();
            }
        }

        String netvalue = tra.getPay();
        String valuta = "€";

        try {
            String outputfile = new DateTime().toString("yyMMddhhmmssSSS") + "_receipt_see.pdf";
            File pdf = new File(pathout + outputfile);
            Document document = new Document(A4, 20, 20, 20, 20);
            try(OutputStream ou = new FileOutputStream(pdf)){
            PdfWriter wr = getInstance(document, ou);
            document.open();
            PdfPTable table = new PdfPTable(3);
            table.setWidths(columnWidths3A);
            table.setWidthPercentage(100.0F);
            PdfPTable table1 = new PdfPTable(1);
            table1.setWidthPercentage(100.0F);
            Phrase phrase1 = new Phrase(ma.getDe_office(), f4_bold);
            PdfPCell cell1 = new PdfPCell();
            cell1.setBorder(0);
            cell1.setPadding(0);
            cell1.addElement(phrase1);
            table1.addCell(cell1);
            phrase1 = new Phrase(ma.getAdd_via(), f4_bold);
            cell1 = new PdfPCell();
            cell1.setBorder(0);
            cell1.setPadding(0);
            cell1.addElement(phrase1);
            table1.addCell(cell1);
            phrase1 = new Phrase(ma.getAdd_cap() + " " + ma.getAdd_city(), f4_bold);
            cell1 = new PdfPCell();
            cell1.setBorder(0);
            cell1.setPadding(0);
            cell1.addElement(phrase1);
            table1.addCell(cell1);
            phrase1 = new Phrase("P.IVA/C.F.: " + ma.getVat(), f4_bold);
            cell1 = new PdfPCell();
            cell1.setBorder(0);
            cell1.setPadding(0);
            cell1.addElement(phrase1);
            table1.addCell(cell1);
            cell1 = new PdfPCell();
            cell1.setBorder(0);
            cell1.setPadding(0);
            cell1.addElement(table1);
            table.addCell(cell1);

            table1 = new PdfPTable(1);
            table1.setWidthPercentage(100.0F);
            cell1 = new PdfPCell();
            cell1.setBorder(0);
            phrase1 = new Phrase(br.getDe_branch(), f2_bold);
            cell1.addElement(phrase1);
            table1.addCell(cell1);
            cell1 = new PdfPCell();
            cell1.setBorder(0);
            phrase1 = new Phrase((br.getAdd_via() + " " + br.getAdd_cap() + " " + br.getAdd_city()).trim(), f2_bold);
            cell1.addElement(phrase1);
            table1.addCell(cell1);

            cell1 = new PdfPCell();
            cell1.setBorder(0);
            cell1.addElement(table1);
            table.addCell(cell1);

            Image img = getInstance(decodeBase64(getConf("path.logocl")));
            img.scalePercent(40.0F);
            cell1 = new PdfPCell(img);
            cell1.setBorder(0);
            cell1.setHorizontalAlignment(ALIGN_RIGHT);
            table.addCell(cell1);
            document.add(table);

            table = new PdfPTable(2);
            table.setWidths(columnWidths1);
            table.setWidthPercentage(100.0F);
            cell1 = new PdfPCell(new Phrase(" "));
            cell1.setBorder(0);
            Paragraph pa = new Paragraph(new Phrase(textfidcode + " " + fidcode, this.f2_bold));
            pa.setAlignment(2);
            PdfPCell cell2 = new PdfPCell(pa);
            cell2.setBorder(0);
            cell2.setHorizontalAlignment(2);
            table.addCell(cell1);
            table.addCell(cell2);
            document.add(table);
            table = new PdfPTable(1);
            table.setWidthPercentage(100.0F);
            table1 = new PdfPTable(4);
            table1.setWidthPercentage(100.0F);

            Phrase phrase = new Phrase();
            phrase.add(new Chunk("Data/Date: ", this.f2_bold));
            phrase.add(new Chunk(formatStringtoStringDate(dateoper, patternsqldate, patternnormdate), this.f2_normal));
            PdfPCell cell3 = new PdfPCell(phrase);
            cell3.setBorder(0);
            table1.addCell(cell3);

            phrase1 = new Phrase();
            phrase1.add(new Chunk("N.Operatore/Cashier: ", this.f2_bold));
            phrase1.add(new Chunk(codoper, this.f2_normal));
            cell3 = new PdfPCell(phrase1);
            cell3.setBorder(0);
            table1.addCell(cell3);

            cell3 = new PdfPCell(new Phrase(kind, this.f2_bold));
            cell3.setHorizontalAlignment(2);
            cell3.setBorder(0);
            table1.addCell(cell3);

            cell3 = new PdfPCell(new Phrase(type, this.f2_bold));
            cell3.setHorizontalAlignment(2);
            cell3.setBorder(0);
            table1.addCell(cell3);
            phrase = new Phrase();
            phrase.add(new Chunk("Cliente / Customer: ", this.f2_bold));
            phrase.add(new Chunk(clientname, this.f2_normal));
            cell3 = new PdfPCell(phrase);
            cell3.setColspan(2);
            cell3.setVerticalAlignment(5);
            cell3.setBorder(0);
            table1.addCell(cell3);

            cell3 = new PdfPCell(new Phrase("Totale Netto: € \nNet Value: € ", this.f2_bold));
            cell3.setHorizontalAlignment(2);
            cell3.setBorder(0);
            table1.addCell(cell3);

            cell3 = new PdfPCell(new Phrase(formatMysqltoDisplay(netvalue), this.f0_bold));
            cell3.setHorizontalAlignment(2);
            cell3.setBorder(0);
            table1.addCell(cell3);
            phrase = new Phrase();

            phrase.add(new Chunk(address, this.f2_bold));

            cell3 = new PdfPCell(phrase);
            cell3.setColspan(2);
            cell3.setVerticalAlignment(5);
            cell3.setBorder(0);
            table1.addCell(cell3);
            String restochangeeti = " ";
            String restochangeval = " ";

            if (tra.getIntbook_type().equals("1")) {
                if (fd(tra.getIntbook_mac()) != 0.0) {
                    restochangeeti = "Resto/Change : " + valuta;
                    restochangeval = formatMysqltoDisplay(roundDoubleandFormat((fd(tra.getIntbook_mac()) * (-1.0)), 2));
                }
            }

            cell3 = new PdfPCell(new Phrase(restochangeeti, this.f2_bold));
            cell3.setHorizontalAlignment(2);
            cell3.setBorder(0);
            table1.addCell(cell3);
            cell3 = new PdfPCell(new Phrase(restochangeval, this.f2_bold));
            cell3.setHorizontalAlignment(2);
            cell3.setBorder(0);
            table1.addCell(cell3);

            phrase = new Phrase();
            phrase.add(new Chunk("Note: " + tra.getNote(), this.f2_bold));
            cell3 = new PdfPCell(phrase);
            cell3.setColspan(2);
            cell3.setVerticalAlignment(5);
            cell3.setBorder(0);
            table1.addCell(cell3);
            cell3 = new PdfPCell(new Phrase(" ", this.f2_bold));
            cell3.setHorizontalAlignment(2);
            cell3.setBorder(0);
            table1.addCell(cell3);
            cell3 = new PdfPCell(new Phrase(" ", this.f2_bold));
            cell3.setHorizontalAlignment(2);
            cell3.setBorder(0);
            table1.addCell(cell3);

            cell1 = new PdfPCell(table1);
            cell1.setBorder(15);
            cell1.setBorderWidth(0.6F);
            table.addCell(cell1);
            document.add(table);
            PdfPTable table2 = new PdfPTable(8);
            table2.setWidthPercentage(100.0F);
            table2.setWidths(columnWidths3);
            cell3 = new PdfPCell(new Phrase("Categoria", this.f2_bold));
            cell3.setHorizontalAlignment(0);
            cell3.setBorder(0);
            table2.addCell(cell3);
            cell3 = new PdfPCell(new Phrase("Valuta", this.f2_bold));
            cell3.setHorizontalAlignment(0);
            cell3.setBorder(0);
            table2.addCell(cell3);
            cell3 = new PdfPCell(new Phrase("Quantità", this.f2_bold));
            cell3.setHorizontalAlignment(0);
            cell3.setBorder(0);
            table2.addCell(cell3);
            cell3 = new PdfPCell(new Phrase("Cambio", this.f2_bold));
            cell3.setHorizontalAlignment(0);
            cell3.setBorder(0);
            table2.addCell(cell3);
            cell3 = new PdfPCell(new Phrase("% Com", this.f2_bold));
            cell3.setHorizontalAlignment(0);
            cell3.setBorder(0);
            table2.addCell(cell3);
            cell3 = new PdfPCell(new Phrase("C.Fissa", this.f2_bold));
            cell3.setHorizontalAlignment(0);
            cell3.setBorder(0);
            table2.addCell(cell3);
            boolean bb_gen = false;
            boolean sb_gen = false;

            switch (tra.getBb()) {
                case "1":
                case "2":
                    bb_gen = true;
                    cell3 = new PdfPCell(new Phrase("Buy Back", this.f2_bold));
                    break;
                case "3":
                case "4":
                    sb_gen = true;
                    cell3 = new PdfPCell(new Phrase("Sell Back", this.f2_bold));
                    break;
                default:
                    cell3 = new PdfPCell(new Phrase("", this.f2_bold));
                    break;
            }

            cell3.setHorizontalAlignment(0);
            cell3.setBorder(0);
            table2.addCell(cell3);
            cell3 = new PdfPCell(new Phrase("Netto", this.f2_bold));
            cell3.setHorizontalAlignment(2);
            cell3.setBorder(0);
            table2.addCell(cell3);

            cell3 = new PdfPCell(new Phrase("Category", this.f3_italic));
            cell3.setHorizontalAlignment(0);
            cell3.setBorder(0);
            table2.addCell(cell3);
            cell3 = new PdfPCell(new Phrase("Currency", this.f3_italic));
            cell3.setHorizontalAlignment(0);
            cell3.setBorder(0);
            table2.addCell(cell3);
            cell3 = new PdfPCell(new Phrase("Quantity", this.f3_italic));
            cell3.setHorizontalAlignment(0);
            cell3.setBorder(0);
            table2.addCell(cell3);
            cell3 = new PdfPCell(new Phrase("Rate", this.f3_italic));
            cell3.setHorizontalAlignment(0);
            cell3.setBorder(0);
            table2.addCell(cell3);
            cell3 = new PdfPCell(new Phrase("% Fee", this.f3_italic));
            cell3.setHorizontalAlignment(0);
            cell3.setBorder(0);
            table2.addCell(cell3);
            cell3 = new PdfPCell(new Phrase("Fixed Fee", this.f3_italic));
            cell3.setHorizontalAlignment(0);
            cell3.setBorder(0);
            table2.addCell(cell3);
            cell3 = new PdfPCell(new Phrase("", this.f3_italic));
            cell3.setHorizontalAlignment(0);
            cell3.setBorder(0);
            table2.addCell(cell3);
            cell3 = new PdfPCell(new Phrase("Net Value", this.f3_italic));
            cell3.setHorizontalAlignment(2);
            cell3.setBorder(0);
            table2.addCell(cell3);

            for (int x = 0; x < livalue.size(); x++) {
                Ch_transaction_value chv = livalue.get(x);
                Figures myfig = get_figures(fig, chv.getSupporto());
                cell3 = new PdfPCell(new Phrase(myfig.getDe_supporto(), this.f2_normal));
                cell3.setHorizontalAlignment(0);
                cell3.setBorder(0);
                table2.addCell(cell3);
                cell3 = new PdfPCell(new Phrase(chv.getValuta() + " - " + formatALCurrency(chv.getValuta(), cur), this.f2_normal));
                cell3.setHorizontalAlignment(0);
                cell3.setBorder(0);
                table2.addCell(cell3);
                cell3 = new PdfPCell(new Phrase(formatMysqltoDisplay(chv.getQuantita()), this.f2_normal));
                cell3.setHorizontalAlignment(0);
                cell3.setBorder(0);
                table2.addCell(cell3);
                cell3 = new PdfPCell(new Phrase(formatMysqltoDisplay(roundDoubleandFormat(fd(chv.getRate()), 8)), this.f2_normal));
                cell3.setHorizontalAlignment(0);
                cell3.setBorder(0);
                table2.addCell(cell3);
                cell3 = new PdfPCell(new Phrase(formatMysqltoDisplay(chv.getCom_perc()), this.f2_normal));
                cell3.setHorizontalAlignment(0);
                cell3.setBorder(0);
                table2.addCell(cell3);
                boolean isbb = false;
                if (chv.getBb().equals("Y")) {
                    double comnet = 0.00;
                    if (fd((chv.getFx_com())) != 0) {
                        isbb = true;
                        if (bb_gen) {
                            comnet = fd((chv.getFx_com())) - fd(formatDoubleforMysql(myfig.getBuy_back_commission()));
                        } else {
                            comnet = fd((chv.getFx_com())) - sb_value;
                        }
                    }
                    cell3 = new PdfPCell(new Phrase(formatMysqltoDisplay(roundDoubleandFormat(comnet, 2)), this.f2_normal));
                } else {
                    cell3 = new PdfPCell(new Phrase(formatMysqltoDisplay(chv.getFx_com()), this.f2_normal));
                }
                cell3.setHorizontalAlignment(0);
                cell3.setBorder(0);
                table2.addCell(cell3);
                switch (chv.getBb()) {
                    case "Y":
                        if (isbb) {
                            if (bb_gen) {
                                cell3 = new PdfPCell(new Phrase(myfig.getBuy_back_commission(), this.f2_normal));
                            } else if (sb_gen) {
                                cell3 = new PdfPCell(new Phrase(formatMysqltoDisplay(roundDoubleandFormat(sb_value, 2)), this.f2_normal));
                            } else {
                                cell3 = new PdfPCell(new Phrase(" ", this.f2_normal));
                            }
                        } else {
                            cell3 = new PdfPCell(new Phrase("0" + decimal + "00", this.f2_normal));
                        }
                        break;
                    case "F":
                        cell3 = new PdfPCell(new Phrase("0" + decimal + "00", this.f2_normal));
                        break;
                    default:
                        cell3 = new PdfPCell(new Phrase(" ", this.f2_normal));
                        break;
                }
                cell3.setHorizontalAlignment(0);
                cell3.setBorder(0);
                table2.addCell(cell3);
                cell3 = new PdfPCell(new Phrase(formatMysqltoDisplay(chv.getNet()), this.f2_normal));
                cell3.setHorizontalAlignment(2);
                cell3.setBorder(0);
                table2.addCell(cell3);
            }

            document.add(table2);
            document.add(new Phrase("\n"));
            document.add(new Phrase("\n"));
            document.add(new Paragraph("Il prezzo complessivo dell'operazione è di € / Total Price € : "
                    + formatMysqltoDisplay(roundDoubleandFormat(fd(tra.getCommission()) + parseDoubleR(tra.getRound()), 2)).replaceAll("-", "").trim(), f3_normal));
            Office of = get_national_office();
            if (tra.getBb().equals("1") || tra.getBb().equals("2")) {
                List<Element> p = parseHTMLtoPDF(of.getTxt_ricev_bb());
                Paragraph paragraph = new Paragraph();
                for (int k = 0; k < p.size(); k++) {
                    paragraph.add((Element) p.get(k));
                }
                document.add(paragraph);
            } else if (tra.getBb().equals("3") || tra.getBb().equals("4")) {
                String newtxt = replace(of.getTxt_ricev_bb(), "BUYBACK", "SELLBACK");
                newtxt = replace(newtxt, "BUY BACK", "SELL BACK");
                newtxt = replace(newtxt, "BUY-BACK", "SELL-BACK");
                List<Element> p = parseHTMLtoPDF(newtxt);
                Paragraph paragraph = new Paragraph();
                for (int k = 0; k < p.size(); k++) {
                    paragraph.add((Element) p.get(k));
                }
                document.add(paragraph);
            }

            List<Element> p = parseHTMLtoPDF(of.getTxt_ricev_1());
            Paragraph paragraph = new Paragraph();
            for (int k = 0; k < p.size(); k++) {
//                document.add(p.get(k));
                paragraph.add(p.get(k));
            }
            document.add(paragraph);

            if (cl.getPep().equals("N") || cl.getPep().equals("NO") || cl.getPep().equals("0")) {
                p = parseHTMLtoPDF(of.getTxt_nopep());
                paragraph = new Paragraph();
                for (int k = 0; k < p.size(); k++) {
                    paragraph.add((Element) p.get(k));
                }
                document.add(paragraph);
            }

            document.add(new Phrase("\n"));

            table2 = new PdfPTable(2);
            table2.setWidthPercentage(100.0F);
            table2.setWidths(columnWidths00);

            cell3 = new PdfPCell(new Phrase("Firma / Signature", this.f1_bold));
            cell3.setHorizontalAlignment(2);
            cell3.setBorder(0);
            table2.addCell(cell3);
            cell3 = new PdfPCell(new Phrase("`sigClie,uid=0,bio=0`", this.sign));
            cell3.setBorder(BOTTOM);
            table2.addCell(cell3);

            document.add(table2);
            document.add(new Phrase("\n"));

            if (marketing) {
                paragraph = new Paragraph();
                p = parseHTMLtoPDF(of.getTxt_ricev_2());
                for (int k = 0; k < p.size(); k++) {
                    paragraph.add((Element) p.get(k));
                }
                document.add(paragraph);
                document.add(new Phrase("\n"));

                table2 = new PdfPTable(2);
                table2.setWidthPercentage(100.0F);
                table2.setWidths(columnWidths00);

                cell3 = new PdfPCell(new Phrase("Firma / Signature", this.f1_bold));
                cell3.setHorizontalAlignment(2);
                cell3.setBorder(0);
                table2.addCell(cell3);
                cell3 = new PdfPCell(new Phrase("`sigCl2,uid=0,bio=0`", this.sign));
                cell3.setBorder(BOTTOM);
                table2.addCell(cell3);

                document.add(table2);
            }
            document.close();
            wr.close();
            ou.close();
            String base64 = getBase64(pdf, tra);
            pdf.delete();
            return base64;
        }
        } catch (Exception ex) {
            insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
        }
        return null;
    }

    /**
     *
     * @param pathout
     * @param ma
     * @param tra
     * @param cl
     * @param livalue
     * @param br
     * @param fig
     * @param cur
     * @param va
     * @param ck
     * @param fidcode
     * @param netvalue
     * @param full
     * @param imponibile1
     * @param imponibile2
     * @param ismax
     * @param marketing
     * @return
     */
    public String print_credit_note(String pathout, Office ma, Ch_transaction tra,
            Client cl, ArrayList<Ch_transaction_value> livalue, Branch br,
            ArrayList<Figures> fig, ArrayList<Currency> cur, VATcode va, CustomerKind ck, String fidcode, String netvalue, boolean full,
            String imponibile1, String imponibile2, boolean ismax, boolean marketing) {

        String numeronotacredito = tra.getCn_number();
        Db_Master db0 = new Db_Master();
        String textfidcode = "Refund Code:";

//        String dateoper = db0.getDataF_NC(numeronotacredito);
        String dateoper = tra.getData();
        db0.closeDB();
        double sb_value = fd(formatDoubleforMysql(fig.get(0).getSell_back_commission()));
        String codoper = tra.getUser();
        String kind = "BANCONOTE / NOTES";
        if (tra.getTipotr().equals("S")) {
            kind = get_figures(fig, tra.getLocalfigures()).getDe_supporto();
        }
        String type = "";
        String clientname = cl.getCognome().toUpperCase() + " " + cl.getNome().toUpperCase();
        if (tra.getTipocliente().equals("003")) {
            Db_Master db1 = new Db_Master();
            String soc = db1.getNDGSocieta(cl.getCode());
            Company c = db1.get_Company(soc);
            db1.closeDB();
            if (c != null) {
                clientname = c.getRagione_sociale().toUpperCase();
            }
        }
        String valuta = "€";

        if (ismax) {
            double impo = fd(tra.getCommission()) + parseDoubleR(tra.getRound());
            String imponibile = formatMysqltoDisplay(roundDoubleandFormat(impo, 2)).replaceAll("-", "");
            imponibile1 = imponibile;
            imponibile2 = imponibile;
        }

        try {
            String outputfile = new DateTime().toString("yyMMddhhmmssSSS") + "_cnote.pdf";
            File pdf = new File(pathout + outputfile);
            Document document = new Document(A4, 20, 20, 20, 20);
            try(OutputStream ou = new FileOutputStream(pdf)){
            PdfWriter wr = getInstance(document, ou);
            document.open();
            PdfPTable table = new PdfPTable(3);
            table.setWidths(columnWidths3A);
            table.setWidthPercentage(100.0F);
            PdfPTable table1 = new PdfPTable(1);
            table1.setWidthPercentage(100.0F);
            Phrase phrase1 = new Phrase(ma.getDe_office(), f4_bold);
            PdfPCell cell1 = new PdfPCell();
            cell1.setBorder(0);
            cell1.setPadding(0);
            cell1.addElement(phrase1);
            table1.addCell(cell1);
            phrase1 = new Phrase(ma.getAdd_via(), f4_bold);
            cell1 = new PdfPCell();
            cell1.setBorder(0);
            cell1.setPadding(0);
            cell1.addElement(phrase1);
            table1.addCell(cell1);
            phrase1 = new Phrase(ma.getAdd_cap() + " " + ma.getAdd_city(), f4_bold);
            cell1 = new PdfPCell();
            cell1.setBorder(0);
            cell1.setPadding(0);
            cell1.addElement(phrase1);
            table1.addCell(cell1);
            phrase1 = new Phrase("P.IVA/C.F.: " + ma.getVat(), f4_bold);
            cell1 = new PdfPCell();
            cell1.setBorder(0);
            cell1.setPadding(0);
            cell1.addElement(phrase1);
            table1.addCell(cell1);
            cell1 = new PdfPCell();
            cell1.setBorder(0);
            cell1.setPadding(0);
            cell1.addElement(table1);
            table.addCell(cell1);

            table1 = new PdfPTable(1);
            table1.setWidthPercentage(100.0F);
            cell1 = new PdfPCell();
            cell1.setBorder(0);
            phrase1 = new Phrase(br.getDe_branch(), f2_bold);
            cell1.addElement(phrase1);
            table1.addCell(cell1);
            cell1 = new PdfPCell();
            cell1.setBorder(0);
            phrase1 = new Phrase((br.getAdd_via() + " " + br.getAdd_cap() + " " + br.getAdd_city()).trim(), f2_bold);
            cell1.addElement(phrase1);
            table1.addCell(cell1);

            cell1 = new PdfPCell();
            cell1.setBorder(0);
            cell1.addElement(table1);
            table.addCell(cell1);

            Image img = getInstance(decodeBase64(getConf("path.logocl")));
            img.scalePercent(40.0F);
            cell1 = new PdfPCell(img);
            cell1.setBorder(0);
            cell1.setHorizontalAlignment(ALIGN_RIGHT);
            table.addCell(cell1);
            document.add(table);

            table = new PdfPTable(2);
            table.setWidths(columnWidths7);
            table.setWidthPercentage(100.0F);
            cell1 = new PdfPCell(new Phrase());
            cell1.setBorder(0);
            Paragraph pa1 = new Paragraph(new Phrase("Nota Credito / Credit Note nr. " + numeronotacredito, this.f1_bold));
            pa1.setAlignment(2);
            PdfPCell cell2 = new PdfPCell(pa1);
            cell2.setBorder(0);
            cell2.setHorizontalAlignment(2);
            table.addCell(cell1);
            table.addCell(cell2);
            document.add(table);

            table = new PdfPTable(2);
            table.setWidths(columnWidths1);
            table.setWidthPercentage(100.0F);
            cell1 = new PdfPCell(new Phrase(""));
            cell1.setBorder(0);
            Paragraph pa = new Paragraph(new Phrase(textfidcode + " " + fidcode, this.f2_bold));
            pa.setAlignment(2);
            cell2 = new PdfPCell(pa);
            cell2.setBorder(0);
            cell2.setHorizontalAlignment(2);
            table.addCell(cell1);
            table.addCell(cell2);
            document.add(table);
            table = new PdfPTable(1);
            table.setWidthPercentage(100.0F);
            table1 = new PdfPTable(4);
            table1.setWidthPercentage(100.0F);
            Phrase phrase = new Phrase();
            phrase.add(new Chunk("Data/Date: ", this.f2_bold));
            phrase.add(new Chunk(formatStringtoStringDate(dateoper, patternsqldate, patternnormdate), this.f2_normal));
            PdfPCell cell3 = new PdfPCell(phrase);
            cell3.setBorder(0);
            table1.addCell(cell3);
            phrase1 = new Phrase();
            phrase1.add(new Chunk("N.Operatore/Cashier: ", this.f2_bold));
            phrase1.add(new Chunk(codoper, this.f2_normal));
            cell3 = new PdfPCell(phrase1);
            cell3.setBorder(0);
            table1.addCell(cell3);
            cell3 = new PdfPCell(new Phrase(kind, this.f2_bold));
            cell3.setHorizontalAlignment(2);
            cell3.setBorder(0);
            table1.addCell(cell3);
            cell3 = new PdfPCell(new Phrase(type, this.f2_bold));
            cell3.setHorizontalAlignment(2);
            cell3.setBorder(0);
            table1.addCell(cell3);
            phrase = new Phrase();
            phrase.add(new Chunk("Cliente / Customer: ", this.f2_bold));
            phrase.add(new Chunk(clientname, this.f2_normal));
            cell3 = new PdfPCell(phrase);
            cell3.setColspan(2);
            cell3.setVerticalAlignment(5);
            cell3.setBorder(0);
            table1.addCell(cell3);

            cell3 = new PdfPCell(new Phrase("Totale Netto: € \nNet Value: € ", this.f2_bold));
            cell3.setHorizontalAlignment(2);
            cell3.setBorder(0);
            table1.addCell(cell3);

            cell3 = new PdfPCell(new Phrase(formatMysqltoDisplay(netvalue), this.f0_bold));
            cell3.setHorizontalAlignment(2);
            cell3.setBorder(0);
            table1.addCell(cell3);
            phrase = new Phrase();
            String city[] = getCity_apm(cl.getCitta());
            phrase.add(new Chunk((cl.getIndirizzo() + " " + city[1] + " " + get_country(cl.getNazione())[1]).toUpperCase(), this.f2_bold));
            cell3 = new PdfPCell(phrase);
            cell3.setColspan(2);
            cell3.setVerticalAlignment(5);
            cell3.setBorder(0);
            table1.addCell(cell3);

//            cell3 = new PdfPCell(new Phrase("Resto/Change " + valuta + " ", this.f2_bold));
//            cell3.setHorizontalAlignment(2);
//            cell3.setBorder(0);
            cellempty.setBorder(0);
            table1.addCell(cellempty);

//            phrase = new Phrase();
//             phrase.add(new Chunk("Rimborso / Refund",this.f1_bold));
            cell3 = new PdfPCell(new Phrase(new Chunk("Rimborso / Refund", this.f1_bold)));
            cell3.setHorizontalAlignment(ALIGN_RIGHT);
            table1.addCell(cell3);

//            cell3 = new PdfPCell(new Phrase(changevalue, this.f2_bold));
//            cell3.setHorizontalAlignment(2);
//            cell3.setBorder(0);
            phrase = new Phrase();
            phrase.add(new Chunk("Note: " + tra.getNote(), this.f2_bold));
//            cell3 = new PdfPCell(phrase);
//            cell3.setColspan(2);
//            cell3.setVerticalAlignment(5);
//            cell3.setBorder(0);
//            table1.addCell(cell3);
//            cell3 = new PdfPCell(new Phrase(" ", this.f2_bold));
//            cell3.setHorizontalAlignment(2);
//            cell3.setBorder(0);
//            table1.addCell(cell3);
//            cell3 = new PdfPCell(new Phrase(" ", this.f2_bold));
//            cell3.setHorizontalAlignment(2);
//            cell3.setBorder(0);
//            table1.addCell(cell3);

            cell1 = new PdfPCell(table1);
            cell1.setBorder(15);
            cell1.setBorderWidth(0.6F);
            table.addCell(cell1);
            document.add(table);
            if (full) {

                PdfPTable table2 = new PdfPTable(8);
                table2.setWidthPercentage(100.0F);
                table2.setWidths(columnWidths3);
                cell3 = new PdfPCell(new Phrase("Categoria", this.f2_bold));
                cell3.setHorizontalAlignment(0);
                cell3.setBorder(0);
                table2.addCell(cell3);
                cell3 = new PdfPCell(new Phrase("Valuta", this.f2_bold));
                cell3.setHorizontalAlignment(0);
                cell3.setBorder(0);
                table2.addCell(cell3);
                cell3 = new PdfPCell(new Phrase("Quantità", this.f2_bold));
                cell3.setHorizontalAlignment(0);
                cell3.setBorder(0);
                table2.addCell(cell3);
                cell3 = new PdfPCell(new Phrase("Cambio", this.f2_bold));
                cell3.setHorizontalAlignment(0);
                cell3.setBorder(0);
                table2.addCell(cell3);
                cell3 = new PdfPCell(new Phrase("% Com", this.f2_bold));
                cell3.setHorizontalAlignment(0);
                cell3.setBorder(0);
                table2.addCell(cell3);
                cell3 = new PdfPCell(new Phrase("C.Fissa", this.f2_bold));
                cell3.setHorizontalAlignment(0);
                cell3.setBorder(0);
                table2.addCell(cell3);
                boolean bb_gen = false;
                boolean sb_gen = false;

                switch (tra.getBb()) {
                    case "1":
                    case "2":
                        bb_gen = true;
                        cell3 = new PdfPCell(new Phrase("Buy Back", this.f2_bold));
                        break;
                    case "3":
                    case "4":
                        sb_gen = true;
                        cell3 = new PdfPCell(new Phrase("Sell Back", this.f2_bold));
                        break;
                    default:
                        cell3 = new PdfPCell(new Phrase("", this.f2_bold));
                        break;
                }

                cell3.setHorizontalAlignment(0);
                cell3.setBorder(0);
                table2.addCell(cell3);
                cell3 = new PdfPCell(new Phrase("Netto", this.f2_bold));
                cell3.setHorizontalAlignment(2);
                cell3.setBorder(0);
                table2.addCell(cell3);

                cell3 = new PdfPCell(new Phrase("Category", this.f3_italic));
                cell3.setHorizontalAlignment(0);
                cell3.setBorder(0);
                table2.addCell(cell3);
                cell3 = new PdfPCell(new Phrase("Currency", this.f3_italic));
                cell3.setHorizontalAlignment(0);
                cell3.setBorder(0);
                table2.addCell(cell3);
                cell3 = new PdfPCell(new Phrase("Quantity", this.f3_italic));
                cell3.setHorizontalAlignment(0);
                cell3.setBorder(0);
                table2.addCell(cell3);
                cell3 = new PdfPCell(new Phrase("Rate", this.f3_italic));
                cell3.setHorizontalAlignment(0);
                cell3.setBorder(0);
                table2.addCell(cell3);
                cell3 = new PdfPCell(new Phrase("% Fee", this.f3_italic));
                cell3.setHorizontalAlignment(0);
                cell3.setBorder(0);
                table2.addCell(cell3);
                cell3 = new PdfPCell(new Phrase("Fixed Fee", this.f3_italic));
                cell3.setHorizontalAlignment(0);
                cell3.setBorder(0);
                table2.addCell(cell3);
                cell3 = new PdfPCell(new Phrase(" ", this.f3_italic));
                cell3.setHorizontalAlignment(0);
                cell3.setBorder(0);
                table2.addCell(cell3);
                cell3 = new PdfPCell(new Phrase("Net Value", this.f3_italic));
                cell3.setHorizontalAlignment(2);
                cell3.setBorder(0);
                table2.addCell(cell3);

                for (int x = 0; x < livalue.size(); x++) {
                    Ch_transaction_value chv = livalue.get(x);
                    Figures myfig = get_figures(fig, chv.getSupporto());
                    cell3 = new PdfPCell(new Phrase(myfig.getDe_supporto(), this.f2_normal));
                    cell3.setHorizontalAlignment(0);
                    cell3.setBorder(0);
                    table2.addCell(cell3);
                    cell3 = new PdfPCell(new Phrase(chv.getValuta() + " - " + formatALCurrency(chv.getValuta(), cur), this.f2_normal));
                    cell3.setHorizontalAlignment(0);
                    cell3.setBorder(0);
                    table2.addCell(cell3);
                    cell3 = new PdfPCell(new Phrase(formatMysqltoDisplay(chv.getQuantita()), this.f2_normal));
                    cell3.setHorizontalAlignment(0);
                    cell3.setBorder(0);
                    table2.addCell(cell3);
                    cell3 = new PdfPCell(new Phrase(formatMysqltoDisplay(roundDoubleandFormat(fd(chv.getRate()), 8)), this.f2_normal));
                    cell3.setHorizontalAlignment(0);
                    cell3.setBorder(0);
                    table2.addCell(cell3);
                    cell3 = new PdfPCell(new Phrase(formatMysqltoDisplay(chv.getCom_perc()), this.f2_normal));
                    cell3.setHorizontalAlignment(0);
                    cell3.setBorder(0);
                    table2.addCell(cell3);
                    boolean isbb = false;
                    if (chv.getBb().equals("Y")) {
                        double comnet = 0.00;
                        if (fd((chv.getFx_com())) != 0) {
                            isbb = true;
                            if (bb_gen) {
                                comnet = fd((chv.getFx_com())) - fd(formatDoubleforMysql(myfig.getBuy_back_commission()));
                            } else {
                                comnet = fd((chv.getFx_com())) - sb_value;
                            }
                        }
                        cell3 = new PdfPCell(new Phrase(formatMysqltoDisplay(roundDoubleandFormat(comnet, 2)), this.f2_normal));
                    } else {
                        cell3 = new PdfPCell(new Phrase(formatMysqltoDisplay(chv.getFx_com()), this.f2_normal));
                    }
                    cell3.setHorizontalAlignment(0);
                    cell3.setBorder(0);
                    table2.addCell(cell3);
                    switch (chv.getBb()) {
                        case "Y":
                            if (isbb) {
                                if (bb_gen) {
                                    cell3 = new PdfPCell(new Phrase(myfig.getBuy_back_commission(), this.f2_normal));
                                } else if (sb_gen) {
                                    cell3 = new PdfPCell(new Phrase(formatMysqltoDisplay(roundDoubleandFormat(sb_value, 2)), this.f2_normal));
                                } else {
                                    cell3 = new PdfPCell(new Phrase(" ", this.f2_normal));
                                }
                            } else {
                                cell3 = new PdfPCell(new Phrase("0" + decimal + "00", this.f2_normal));
                            }
                            break;
                        case "F":
                            cell3 = new PdfPCell(new Phrase("0" + decimal + "00", this.f2_normal));
                            break;
                        default:
                            cell3 = new PdfPCell(new Phrase(" ", this.f2_normal));
                            break;
                    }
                    cell3.setHorizontalAlignment(0);
                    cell3.setBorder(0);
                    table2.addCell(cell3);
                    cell3 = new PdfPCell(new Phrase(formatMysqltoDisplay(chv.getNet()), this.f2_normal));
                    cell3.setHorizontalAlignment(2);
                    cell3.setBorder(0);
                    table2.addCell(cell3);
                }
                document.add(table2);
            }//if full
            document.add(new Phrase("\n"));
            document.add(new Phrase("\n"));

            Office of = get_national_office();
            if (tra.getBb().equals("1") || tra.getBb().equals("2")) {
                List<Element> p = parseHTMLtoPDF(of.getTxt_ricev_bb());
                Paragraph paragraph = new Paragraph();
                for (int k = 0; k < p.size(); k++) {
                    paragraph.add((Element) p.get(k));
                }
                document.add(paragraph);
            } else if (tra.getBb().equals("3") || tra.getBb().equals("4")) {
                String newtxt = replace(of.getTxt_ricev_bb(), "BUYBACK", "SELLBACK");
                newtxt = replace(newtxt, "BUY BACK", "SELL BACK");
                newtxt = replace(newtxt, "BUY-BACK", "SELL-BACK");
                List<Element> p = parseHTMLtoPDF(newtxt);
                Paragraph paragraph = new Paragraph();
                for (int k = 0; k < p.size(); k++) {
                    paragraph.add((Element) p.get(k));
                }
                document.add(paragraph);
            }

            document.add(new Phrase("Riferimento transazione n. / Reference transaction no. " + tra.getFa_number() + " - "
                    + formatStringtoStringDate(tra.getData(), "yyyy-MM-dd HH:mm:ss", "dd/MM/yyyy"), this.f3_bold));

            document.add(new Phrase("\n"));
            document.add(new Phrase("\n"));

            String aliq = "";
            if (va  != null) {
                if (!va.getAliquota().equals("0.00")) {
                    aliq = "Aliquota: " + formatMysqltoDisplay(va.getAliquota());
                }
            }

            if (ismax) {
                double commANDround = fd(imponibile1);
                if (commANDround >= fd(ck.getIp_soglia_bollo())) {
                    document.add(new Phrase(ck.getDescr_bollo(), this.f3_normal));
                    document.add(new Phrase("\n"));
                }
            }

            if (va  != null) {
                String de1 = "Imponibile " + valuta + " " + imponibile1.replaceAll("-", "");
                String de2 = " (" + va.getDescrizione() + ") ";
                String de3 = aliq + " Totale Nota Credito / Total Credit Note " + valuta + ": " + imponibile2.replaceAll("-", "");
                document.add(new Paragraph(de1 + de2 + de3, this.f3_normal));
            }

            List<Element> p = parseHTMLtoPDF(of.getTxt_ricev_1());
            Paragraph paragraph = new Paragraph();
            for (int k = 0; k < p.size(); k++) {
                paragraph.add((Element) p.get(k));
            }
            document.add(paragraph);
            document.add(new Phrase("\n"));

            PdfPTable table2 = new PdfPTable(2);
            table2.setWidthPercentage(100.0F);
            table2.setWidths(columnWidths00);

            cell3 = new PdfPCell(new Phrase("Firma / Signature", this.f1_bold));
            cell3.setHorizontalAlignment(2);
            cell3.setBorder(0);
            table2.addCell(cell3);
            cell3 = new PdfPCell(new Phrase("`sigClie,uid=0,bio=0`", this.sign));
            cell3.setBorder(BOTTOM);
            table2.addCell(cell3);

            document.add(table2);

            document.add(new Phrase("\n"));

            if (marketing) {
                p = parseHTMLtoPDF(of.getTxt_ricev_2());
                paragraph = new Paragraph();
                for (int k = 0; k < p.size(); k++) {
                    paragraph.add((Element) p.get(k));
                }
                document.add(paragraph);
                document.add(new Phrase("\n"));

                table2 = new PdfPTable(2);
                table2.setWidthPercentage(100.0F);
                table2.setWidths(columnWidths00);

                cell3 = new PdfPCell(new Phrase("Firma / Signature", this.f1_bold));
                cell3.setHorizontalAlignment(2);
                cell3.setBorder(0);
                table2.addCell(cell3);
                cell3 = new PdfPCell(new Phrase("`sigCl2,uid=0,bio=0`", this.sign));
                cell3.setBorder(BOTTOM);
                table2.addCell(cell3);

                document.add(table2);
            }
            document.close();
            wr.close();
            ou.close();
            String base64 = getBase64(pdf, tra);
            pdf.delete();
            return base64;
        }
        } catch (Exception ex) {
            insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
        }
        return null;
    }

    /**
     *
     * @param pathout
     * @param ma
     * @param tra
     * @param cl
     * @param livalue
     * @param br
     * @param fig
     * @param cur
     * @param va
     * @param ck
     * @param marketing
     * @return
     */
    public String print_bill_extrasee_bollo(String pathout, Office ma, Ch_transaction tra, Client cl, ArrayList<Ch_transaction_value> livalue,
            Branch br, ArrayList<Figures> fig, ArrayList<Currency> cur, VATcode va, CustomerKind ck, boolean marketing) {
        String textfidcode = "Fidelity Code:";
        String fidcode = tra.getFiliale() + tra.getId();
        String dateoper = tra.getData();
        String codoper = tra.getUser();
        String kind = "BANCONOTE / NOTES";

        if (tra.getTipotr().equals("S")) {
            kind = get_figures(fig, tra.getLocalfigures()).getDe_supporto();
        }

        double sb_value = fd(formatDoubleforMysql(fig.get(0).getSell_back_commission()));

        String type = tra.formatType_pdf(tra.getTipotr());
        String clientname = cl.getCognome().toUpperCase() + " " + cl.getNome().toUpperCase();
        if (tra.getTipocliente().equals("003")) {
            Db_Master db1 = new Db_Master();
            String soc = db1.getNDGSocieta(cl.getCode());
            Company c = db1.get_Company(soc);
            db1.closeDB();
            if (c != null) {
                clientname = c.getRagione_sociale().toUpperCase();
            }
        }
        String netvalue = tra.getPay();

        String valuta = "€";
        String numeronotacredito = tra.getFa_number();

        double impo = fd(tra.getCommission()) + parseDoubleR(tra.getRound());

        String imponibile = formatMysqltoDisplay(roundDoubleandFormat(impo, 2)).replaceAll("-", "");

        try {
            String outputfile = new DateTime().toString("yyMMddhhmmssSSS") + "_receipt_see.pdf";
            File pdf = new File(pathout + outputfile);
            Document document = new Document(A4, 20, 20, 20, 20);
            try(OutputStream ou = new FileOutputStream(pdf)){
            PdfWriter wr = getInstance(document, ou);
            document.open();
            PdfPTable table = new PdfPTable(3);
            table.setWidths(columnWidths3A);
            table.setWidthPercentage(100.0F);
            PdfPTable table1 = new PdfPTable(1);
            table1.setWidthPercentage(100.0F);
            Phrase phrase1 = new Phrase(ma.getDe_office(), f4_bold);
            PdfPCell cell1 = new PdfPCell();
            cell1.setBorder(0);
            cell1.setPadding(0);
            cell1.addElement(phrase1);
            table1.addCell(cell1);
            phrase1 = new Phrase(ma.getAdd_via(), f4_bold);
            cell1 = new PdfPCell();
            cell1.setBorder(0);
            cell1.setPadding(0);
            cell1.addElement(phrase1);
            table1.addCell(cell1);
            phrase1 = new Phrase(ma.getAdd_cap() + " " + ma.getAdd_city(), f4_bold);
            cell1 = new PdfPCell();
            cell1.setBorder(0);
            cell1.setPadding(0);
            cell1.addElement(phrase1);
            table1.addCell(cell1);
            phrase1 = new Phrase("P.IVA/C.F.: " + ma.getVat(), f4_bold);
            cell1 = new PdfPCell();
            cell1.setBorder(0);
            cell1.setPadding(0);
            cell1.addElement(phrase1);
            table1.addCell(cell1);
            cell1 = new PdfPCell();
            cell1.setBorder(0);
            cell1.setPadding(0);
            cell1.addElement(table1);
            table.addCell(cell1);

            table1 = new PdfPTable(1);
            table1.setWidthPercentage(100.0F);
            cell1 = new PdfPCell();
            cell1.setBorder(0);
            phrase1 = new Phrase(br.getDe_branch(), f2_bold);
            cell1.addElement(phrase1);
            table1.addCell(cell1);
            cell1 = new PdfPCell();
            cell1.setBorder(0);
            phrase1 = new Phrase((br.getAdd_via() + " " + br.getAdd_cap() + " " + br.getAdd_city()).trim(), f2_bold);
            cell1.addElement(phrase1);
            table1.addCell(cell1);

            cell1 = new PdfPCell();
            cell1.setBorder(0);
            cell1.addElement(table1);
            table.addCell(cell1);

            Image img = getInstance(decodeBase64(getConf("path.logocl")));
            img.scalePercent(40.0F);
            cell1 = new PdfPCell(img);
            cell1.setBorder(0);
            cell1.setHorizontalAlignment(ALIGN_RIGHT);
            table.addCell(cell1);
            document.add(table);

            table = new PdfPTable(2);
            table.setWidths(columnWidths7);
            table.setWidthPercentage(100.0F);
            cell1 = new PdfPCell(new Phrase());
            cell1.setBorder(0);
            Paragraph pa1 = new Paragraph(new Phrase("Fattura / Invoice nr. " + numeronotacredito, this.f1_bold));
            pa1.setAlignment(2);
            PdfPCell cell2 = new PdfPCell(pa1);
            cell2.setBorder(0);
            cell2.setHorizontalAlignment(2);
            table.addCell(cell1);
            table.addCell(cell2);
            document.add(table);

            table = new PdfPTable(2);
            table.setWidths(columnWidths1);
            table.setWidthPercentage(100.0F);
            cell1 = new PdfPCell(new Phrase(""));
            cell1.setBorder(0);
            Paragraph pa = new Paragraph(new Phrase(textfidcode + " " + fidcode, this.f2_bold));
            pa.setAlignment(2);
            cell2 = new PdfPCell(pa);
            cell2.setBorder(0);
            cell2.setHorizontalAlignment(2);
            table.addCell(cell1);
            table.addCell(cell2);
            document.add(table);
            table = new PdfPTable(1);
            table.setWidthPercentage(100.0F);
            table1 = new PdfPTable(4);
            table1.setWidthPercentage(100.0F);
            Phrase phrase = new Phrase();
            phrase.add(new Chunk("Data/Date: ", this.f2_bold));
            phrase.add(new Chunk(formatStringtoStringDate(dateoper, patternsqldate, patternnormdate), this.f2_normal));
            PdfPCell cell3 = new PdfPCell(phrase);
            cell3.setBorder(0);
            table1.addCell(cell3);
            phrase1 = new Phrase();
            phrase1.add(new Chunk("N.Operatore/Cashier: ", this.f2_bold));
            phrase1.add(new Chunk(codoper, this.f2_normal));
            cell3 = new PdfPCell(phrase1);
            cell3.setBorder(0);
            table1.addCell(cell3);
            cell3 = new PdfPCell(new Phrase(kind, this.f2_bold));
            cell3.setHorizontalAlignment(2);
            cell3.setBorder(0);
            table1.addCell(cell3);
            cell3 = new PdfPCell(new Phrase(type, this.f2_bold));
            cell3.setHorizontalAlignment(2);
            cell3.setBorder(0);
            table1.addCell(cell3);
            phrase = new Phrase();
            phrase.add(new Chunk("Cliente / Customer: ", this.f2_bold));
            phrase.add(new Chunk(clientname, this.f2_normal));
            cell3 = new PdfPCell(phrase);
            cell3.setColspan(2);
            cell3.setVerticalAlignment(5);
            cell3.setBorder(0);
            table1.addCell(cell3);

            cell3 = new PdfPCell(new Phrase("Totale Netto: € \nNet Value: € ", this.f2_bold));
            cell3.setHorizontalAlignment(2);
            cell3.setBorder(0);
            table1.addCell(cell3);

            cell3 = new PdfPCell(new Phrase(formatMysqltoDisplay(netvalue), this.f0_bold));
            cell3.setHorizontalAlignment(2);
            cell3.setBorder(0);
            table1.addCell(cell3);
            phrase = new Phrase();
            String city[] = getCity_apm(cl.getCitta());
            phrase.add(new Chunk((cl.getIndirizzo() + " " + city[1] + " " + get_country(cl.getNazione())[1]).toUpperCase(), this.f2_bold));
            cell3 = new PdfPCell(phrase);
            cell3.setColspan(2);
            cell3.setVerticalAlignment(5);
            cell3.setBorder(0);
            table1.addCell(cell3);

            String restochangeeti = " ";
            String restochangeval = " ";

            if (tra.getIntbook_type().equals("1")) {
                if (fd(tra.getIntbook_mac()) != 0.0) {
                    restochangeeti = "Resto/Change : " + valuta;
                    restochangeval = formatMysqltoDisplay(roundDoubleandFormat((fd(tra.getIntbook_mac()) * (-1.0)), 2));
                }
            }

            cell3 = new PdfPCell(new Phrase(restochangeeti, this.f2_bold));
            cell3.setHorizontalAlignment(2);
            cell3.setBorder(0);
            table1.addCell(cell3);
            cell3 = new PdfPCell(new Phrase(restochangeval, this.f2_bold));
            cell3.setHorizontalAlignment(2);
            cell3.setBorder(0);
            table1.addCell(cell3);

            phrase = new Phrase();
            phrase.add(new Chunk("Note: " + tra.getNote(), this.f2_bold));
            cell3 = new PdfPCell(phrase);
            cell3.setColspan(2);
            cell3.setVerticalAlignment(5);
            cell3.setBorder(0);
            table1.addCell(cell3);
            cell3 = new PdfPCell(new Phrase(" ", this.f2_bold));
            cell3.setHorizontalAlignment(2);
            cell3.setBorder(0);
            table1.addCell(cell3);
            cell3 = new PdfPCell(new Phrase(" ", this.f2_bold));
            cell3.setHorizontalAlignment(2);
            cell3.setBorder(0);
            table1.addCell(cell3);

            cell1 = new PdfPCell(table1);
            cell1.setBorder(15);
            cell1.setBorderWidth(0.6F);
            table.addCell(cell1);
            document.add(table);
            PdfPTable table2 = new PdfPTable(8);
            table2.setWidthPercentage(100.0F);
            table2.setWidths(columnWidths3);
            cell3 = new PdfPCell(new Phrase("Categoria", this.f2_bold));
            cell3.setHorizontalAlignment(0);
            cell3.setBorder(0);
            table2.addCell(cell3);
            cell3 = new PdfPCell(new Phrase("Valuta", this.f2_bold));
            cell3.setHorizontalAlignment(0);
            cell3.setBorder(0);
            table2.addCell(cell3);
            cell3 = new PdfPCell(new Phrase("Quantità", this.f2_bold));
            cell3.setHorizontalAlignment(0);
            cell3.setBorder(0);
            table2.addCell(cell3);
            cell3 = new PdfPCell(new Phrase("Cambio", this.f2_bold));
            cell3.setHorizontalAlignment(0);
            cell3.setBorder(0);
            table2.addCell(cell3);
            cell3 = new PdfPCell(new Phrase("% Com", this.f2_bold));
            cell3.setHorizontalAlignment(0);
            cell3.setBorder(0);
            table2.addCell(cell3);
            cell3 = new PdfPCell(new Phrase("C.Fissa", this.f2_bold));
            cell3.setHorizontalAlignment(0);
            cell3.setBorder(0);
            table2.addCell(cell3);

            boolean bb_gen = false;
            boolean sb_gen = false;

            switch (tra.getBb()) {
                case "1":
                case "2":
                    bb_gen = true;
                    cell3 = new PdfPCell(new Phrase("Buy Back", this.f2_bold));
                    break;
                case "3":
                case "4":
                    sb_gen = true;
                    cell3 = new PdfPCell(new Phrase("Sell Back", this.f2_bold));
                    break;
                default:
                    cell3 = new PdfPCell(new Phrase("", this.f2_bold));
                    break;
            }
            cell3.setHorizontalAlignment(0);
            cell3.setBorder(0);
            table2.addCell(cell3);
            cell3 = new PdfPCell(new Phrase("Netto", this.f2_bold));
            cell3.setHorizontalAlignment(2);
            cell3.setBorder(0);
            table2.addCell(cell3);

            cell3 = new PdfPCell(new Phrase("Category", this.f3_italic));
            cell3.setHorizontalAlignment(0);
            cell3.setBorder(0);
            table2.addCell(cell3);
            cell3 = new PdfPCell(new Phrase("Currency", this.f3_italic));
            cell3.setHorizontalAlignment(0);
            cell3.setBorder(0);
            table2.addCell(cell3);
            cell3 = new PdfPCell(new Phrase("Quantity", this.f3_italic));
            cell3.setHorizontalAlignment(0);
            cell3.setBorder(0);
            table2.addCell(cell3);
            cell3 = new PdfPCell(new Phrase("Rate", this.f3_italic));
            cell3.setHorizontalAlignment(0);
            cell3.setBorder(0);
            table2.addCell(cell3);
            cell3 = new PdfPCell(new Phrase("% Fee", this.f3_italic));
            cell3.setHorizontalAlignment(0);
            cell3.setBorder(0);
            table2.addCell(cell3);
            cell3 = new PdfPCell(new Phrase("Fixed Fee", this.f3_italic));
            cell3.setHorizontalAlignment(0);
            cell3.setBorder(0);
            table2.addCell(cell3);
            cell3 = new PdfPCell(new Phrase(" ", this.f3_italic));
            cell3.setHorizontalAlignment(0);
            cell3.setBorder(0);
            table2.addCell(cell3);
            cell3 = new PdfPCell(new Phrase("Net Value", this.f3_italic));
            cell3.setHorizontalAlignment(2);
            cell3.setBorder(0);
            table2.addCell(cell3);
            for (int x = 0; x < livalue.size(); x++) {
                Ch_transaction_value chv = livalue.get(x);
                Figures myfig = get_figures(fig, chv.getSupporto());
                cell3 = new PdfPCell(new Phrase(myfig.getDe_supporto(), this.f2_normal));
                cell3.setHorizontalAlignment(0);
                cell3.setBorder(0);
                table2.addCell(cell3);
                cell3 = new PdfPCell(new Phrase(chv.getValuta() + " - " + formatALCurrency(chv.getValuta(), cur), this.f2_normal));
                cell3.setHorizontalAlignment(0);
                cell3.setBorder(0);
                table2.addCell(cell3);
                cell3 = new PdfPCell(new Phrase(formatMysqltoDisplay(chv.getQuantita()), this.f2_normal));
                cell3.setHorizontalAlignment(0);
                cell3.setBorder(0);
                table2.addCell(cell3);
                cell3 = new PdfPCell(new Phrase(formatMysqltoDisplay(roundDoubleandFormat(fd(chv.getRate()), 8)), this.f2_normal));
                cell3.setHorizontalAlignment(0);
                cell3.setBorder(0);
                table2.addCell(cell3);
                cell3 = new PdfPCell(new Phrase(formatMysqltoDisplay(chv.getCom_perc()), this.f2_normal));
                cell3.setHorizontalAlignment(0);
                cell3.setBorder(0);
                table2.addCell(cell3);
                boolean isbb = false;

                if (chv.getBb().equals("Y")) {
                    double comnet = 0.00;
                    if (fd((chv.getFx_com())) != 0) {
                        isbb = true;
                        if (bb_gen) {
                            comnet = fd((chv.getFx_com())) - fd(formatDoubleforMysql(myfig.getBuy_back_commission()));
                        } else {
                            comnet = fd((chv.getFx_com())) - sb_value;
                        }
                    }
                    cell3 = new PdfPCell(new Phrase(formatMysqltoDisplay(roundDoubleandFormat(comnet, 2)), this.f2_normal));
                } else {
                    cell3 = new PdfPCell(new Phrase(formatMysqltoDisplay(chv.getFx_com()), this.f2_normal));
                }
                cell3.setHorizontalAlignment(0);
                cell3.setBorder(0);
                table2.addCell(cell3);
                switch (chv.getBb()) {
                    case "Y":
                        if (isbb) {
                            if (bb_gen) {
                                cell3 = new PdfPCell(new Phrase(myfig.getBuy_back_commission(), this.f2_normal));
                            } else if (sb_gen) {
                                cell3 = new PdfPCell(new Phrase(formatMysqltoDisplay(roundDoubleandFormat(sb_value, 2)), this.f2_normal));
                            } else {
                                cell3 = new PdfPCell(new Phrase(" ", this.f2_normal));
                            }
                        } else {
                            cell3 = new PdfPCell(new Phrase("0" + decimal + "00", this.f2_normal));
                        }
                        break;
                    case "F":
                        cell3 = new PdfPCell(new Phrase("0" + decimal + "00", this.f2_normal));
                        break;
                    default:
                        cell3 = new PdfPCell(new Phrase(" ", this.f2_normal));
                        break;
                }
                cell3.setHorizontalAlignment(0);
                cell3.setBorder(0);
                table2.addCell(cell3);
                cell3 = new PdfPCell(new Phrase(formatMysqltoDisplay(chv.getNet()), this.f2_normal));
                cell3.setHorizontalAlignment(2);
                cell3.setBorder(0);
                table2.addCell(cell3);
            }
            document.add(table2);

            document.add(new Phrase("\n"));
            document.add(new Phrase("\n"));

            if (impo >= fd(ck.getIp_soglia_bollo())) {
                document.add(new Phrase(ck.getDescr_bollo(), this.f3_normal));
                document.add(new Phrase("\n"));
            }

            String aliq = "";
            if (!va.getAliquota().equals("0.00")) {
                aliq = "Aliquota: " + formatMysqltoDisplay(va.getAliquota());
            }

            String de1 = "Imponibile " + valuta + " " + imponibile.replaceAll("-", "");
            String de2 = " (" + va.getDescrizione() + ") ";
            String de3 = aliq + " Totale Fattura / Total Invoice: " + imponibile.replaceAll("-", "");
            document.add(new Paragraph(de1 + de2 + de3, this.f3_normal));

            Office of = get_national_office();
            if (tra.getBb().equals("1") || tra.getBb().equals("2")) {
                List<Element> p = parseHTMLtoPDF(of.getTxt_ricev_bb());
                Paragraph paragraph = new Paragraph();
                for (int k = 0; k < p.size(); k++) {
                    paragraph.add((Element) p.get(k));
                }
                document.add(paragraph);
            } else if (tra.getBb().equals("3") || tra.getBb().equals("4")) {
                String newtxt = replace(of.getTxt_ricev_bb(), "BUYBACK", "SELLBACK");
                newtxt = replace(newtxt, "BUY BACK", "SELL BACK");
                newtxt = replace(newtxt, "BUY-BACK", "SELL-BACK");
                List<Element> p = parseHTMLtoPDF(newtxt);
                Paragraph paragraph = new Paragraph();
                for (int k = 0; k < p.size(); k++) {
                    paragraph.add((Element) p.get(k));
                }
                document.add(paragraph);
            }

            List<Element> p = parseHTMLtoPDF(of.getTxt_ricev_1());
            Paragraph paragraph = new Paragraph();
            for (int k = 0; k < p.size(); k++) {
                paragraph.add((Element) p.get(k));
            }
            document.add(paragraph);

            if (cl.getPep().equals("N") || cl.getPep().equals("NO") || cl.getPep().equals("0")) {
                p = parseHTMLtoPDF(of.getTxt_nopep());
                paragraph = new Paragraph();
                for (int k = 0; k < p.size(); k++) {
                    paragraph.add((Element) p.get(k));
                }
                document.add(paragraph);
            }
            document.add(new Phrase("\n"));
            table2 = new PdfPTable(2);
            table2.setWidthPercentage(100.0F);
            table2.setWidths(columnWidths00);

            cell3 = new PdfPCell(new Phrase("Firma / Signature", this.f1_bold));
            cell3.setHorizontalAlignment(2);
            cell3.setBorder(0);
            table2.addCell(cell3);
            cell3 = new PdfPCell(new Phrase("`sigClie,uid=0,bio=0`", this.sign));
            cell3.setBorder(BOTTOM);
            table2.addCell(cell3);

            document.add(table2);
            document.add(new Phrase("\n"));
            if (marketing) {
                p = parseHTMLtoPDF(of.getTxt_ricev_2());
                paragraph = new Paragraph();
                for (int k = 0; k < p.size(); k++) {
                    paragraph.add((Element) p.get(k));
                }
                document.add(paragraph);
                document.add(new Phrase("\n"));

                table2 = new PdfPTable(2);
                table2.setWidthPercentage(100.0F);
                table2.setWidths(columnWidths00);

                cell3 = new PdfPCell(new Phrase("Firma / Signature", this.f1_bold));
                cell3.setHorizontalAlignment(2);
                cell3.setBorder(0);
                table2.addCell(cell3);
                cell3 = new PdfPCell(new Phrase("`sigCl2,uid=0,bio=0`", this.sign));
                cell3.setBorder(BOTTOM);
                table2.addCell(cell3);

                document.add(table2);
            }

            document.close();
            wr.close();
            ou.close();
            String base64 = getBase64(pdf, tra);
            pdf.delete();
            return base64;
        }
        } catch (Exception ex) {
            insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
        }
        return null;
    }

    //ALTRI PDF
    /**
     *
     * @param pathout
     * @param tipodoc
     * @param codoper
     * @param dt
     * @return
     */
    public String print_pdf_nodigital(String pathout, String tipodoc, String codoper, String dt) {
        try {
            String outputfile = new DateTime().toString("yyMMddhhmmssSSS") + tipodoc + ".pdf";
            File pdf = new File(pathout + outputfile);
            Document document = new Document(A4, 20, 20, 20, 20);
            try(OutputStream ou = new FileOutputStream(pdf)){
            PdfWriter wr = getInstance(document, ou);
            document.open();
            Image img = getInstance(decodeBase64(getConf("path.logocl")));
            img.scalePercent(40.0F);
            document.add(img);
            document.add(new Phrase("\n", this.f2_normal));
            document.add(new Phrase("\n", this.f2_normal));
            document.add(new Phrase("\n", this.f2_normal));
            document.add(new Phrase("\n", this.f2_normal));
            document.add(new Phrase("\n", this.f2_normal));
            document.add(new Phrase("THIS DOCUMENT IS SAVED ONLY IN PAPER.", this.f2_bold));
            document.add(new Phrase("\n", this.f2_normal));
            document.add(new Phrase("\n", this.f2_normal));
            document.add(new Phrase("USER: " + codoper, this.f2_bold));
            document.add(new Phrase("\n", this.f2_normal));
            document.add(new Phrase("\n", this.f2_normal));
            document.add(new Phrase("DATE: " + formatStringtoStringDate(dt, patternsqldate, patternnormdate), this.f2_bold));
            document.close();
            wr.close();
            ou.close();
            String base64 = new String(encodeBase64(readFileToByteArray(pdf)));
            pdf.delete();
            return base64;
            }
        } catch (Exception ex) {
            insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
        }
        return null;
    }

    /**
     *
     * @param pathout
     * @return
     */
    public String print_pdf_noresult(String pathout) {
        try {
            String outputfile = new DateTime().toString("yyMMddhhmmssSSS") + ".pdf";
            File pdf = new File(pathout + outputfile);
            Document document = new Document(A4, 20, 20, 20, 20);
            try(OutputStream ou = new FileOutputStream(pdf)){
            PdfWriter wr = getInstance(document, ou);
            document.open();
            Image img = getInstance(decodeBase64(getConf("path.logocl")));
            img.scalePercent(40.0F);
            document.add(img);
            document.add(new Phrase("\n", this.f2_normal));
            document.add(new Phrase("\n", this.f2_normal));
            document.add(new Phrase("\n", this.f2_normal));
            document.add(new Phrase("\n", this.f2_normal));
            document.add(new Phrase("\n", this.f2_normal));
            document.add(new Phrase("NO RESULT FOUND.", this.f2_bold));
            document.add(new Phrase("\n", this.f2_normal));
            document.add(new Phrase("\n", this.f2_normal));
            document.add(new Phrase("\n", this.f2_normal));
            document.add(new Phrase("\n", this.f2_normal));
            document.add(new Phrase("\n", this.f2_normal));
            document.close();
            wr.close();
            ou.close();
            String base64 = new String(encodeBase64(readFileToByteArray(pdf)));
            pdf.delete();
            return base64;
            }
        } catch (Exception ex) {
            insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
        }
        return null;
    }

    /**
     *
     * @param pathout
     * @param cod
     * @return
     */
    public String transactionview(String pathout, String cod) {

        try {

            Ch_transaction tra = query_transaction_ch(cod);
            Branch br1 = get_branch(tra.getFiliale());
            ArrayList<Company> array_listCompany = list_only_company();
            ArrayList<String[]> array_identificationCard = identificationCard();
            ArrayList<String[]> array_credit_card = credit_card(tra.getFiliale());
            ArrayList<String[]> array_undermincommjustify = undermincommjustify();
            ArrayList<String[]> array_kindcommissionefissa = kindcommissionefissa();
            ArrayList<String[]> array_kind_pay = kind_payment();
            ArrayList<String[]> array_bankacc = list_bankAccount();
            ArrayList<String[]> array_unlockrate = unlockratejustify();
            Openclose oc = query_oc(tra.getId_open_till());
            Till t1 = get_single_Till(oc.getFiliale(), oc.getTill());
            CustomerKind ck = get_customerKind(tra.getTipocliente());
            Codici_sblocco cs1 = getCod_tr(cod, "01");
            String resp1 = "";
            if (cs1 != null) {
                resp1 = getRespCod(cs1.getCodice());
            }
            boolean showagency = tra.getAgency().equals("1");
            String inout = "Out";
            if (tra.getTipotr().equals("S")) {
                inout = "In";
            }
            String outputfile = new DateTime().toString("yyMMddhhmmssSSS") + ".pdf";
            File pdf = new File(pathout + outputfile);
            Document document = new Document(A4, 20, 20, 20, 20);
            try(OutputStream ou = new FileOutputStream(pdf)){
            PdfWriter wr = getInstance(document, ou);
            document.open();

            Image img = getInstance(decodeBase64(getConf("path.logocl")));
            img.scalePercent(40.0F);
            document.add(img);

            document.add(new Paragraph("Transaction Details", f0_bold));
            document.add(new Paragraph("\n"));

            PdfPTable table = new PdfPTable(2);
            table.setWidths(new float[]{80f, 20f});
            table.setWidthPercentage(100);

            table = new PdfPTable(4);
            table.setWidths(columnWidths4);
            table.setWidthPercentage(100);

            Phrase phrase1 = new Phrase();
            phrase1.add(new Chunk("Branch ID\n", f2_bold));
            phrase1.add(new Chunk(br1.getCod(), f2_normal));
            PdfPCell cell1 = new PdfPCell(phrase1);
            cell1.setBorder(BOTTOM | TOP | LEFT);
            table.addCell(cell1);

            phrase1 = new Phrase();
            phrase1.add(new Chunk("Branch Description \n", f2_bold));
            phrase1.add(new Chunk(br1.getDe_branch(), f2_normal));
            cell1 = new PdfPCell(phrase1);
            cell1.setBorder(BOTTOM | TOP);
            table.addCell(cell1);

            phrase1 = new Phrase();
            phrase1.add(new Chunk("Branch Address \n", f2_bold));
            phrase1.add(new Chunk(br1.getAdd_via() + " ; " + br1.getAdd_cap() + " ; " + br1.getAdd_city(), f2_normal));
            cell1 = new PdfPCell(phrase1);
            cell1.setColspan(2);
            cell1.setBorder(BOTTOM | TOP | RIGHT);
            table.addCell(cell1);

            phrase1 = new Phrase();
            phrase1.add(new Chunk("Code \n", f2_bold));
            phrase1.add(new Chunk(tra.getId(), f2_normal));
            cell1 = new PdfPCell(phrase1);
            cell1.setBorder(BOTTOM | TOP | LEFT);
            table.addCell(cell1);

            phrase1 = new Phrase();
            phrase1.add(new Chunk("Date \n", f2_bold));
            phrase1.add(new Chunk(formatStringtoStringDate(tra.getData(), patternsqldate, patternnormdate), f2_normal));

            cell1 = new PdfPCell(phrase1);
            cell1.setBorder(BOTTOM | TOP);
            table.addCell(cell1);
            phrase1 = new Phrase();
            phrase1.add(new Chunk("Operator \n", f2_bold));
            phrase1.add(new Chunk(tra.getUser(), this.f2_normal));
            cell1 = new PdfPCell(phrase1);
            cell1.setBorder(BOTTOM | TOP);
            table.addCell(cell1);
            phrase1 = new Phrase();
            phrase1.add(new Chunk("Status \n", f2_bold));
            phrase1.add(new Chunk(tra.formatStatus_cru(tra.getDel_fg()), f2_normal));

            cell1 = new PdfPCell(phrase1);
            cell1.setBorder(BOTTOM | TOP | RIGHT);
            table.addCell(cell1);
            document.add(table);

            document.add(new Paragraph("\n"));

            if (tra.getDel_fg().equals("1")) {
                table = new PdfPTable(3);
                table.setWidths(columnWidths3B);
                table.setWidthPercentage(100);

                phrase1 = new Phrase();
                phrase1.add(new Chunk("Delete Date \n", f2_bold));
                phrase1.add(new Chunk(formatStringtoStringDate(tra.getDel_dt(), patternsqldate, patternnormdate), this.f2_normal));

                cell1 = new PdfPCell(phrase1);
                cell1.setBorder(BOTTOM);
                table.addCell(cell1);

                phrase1 = new Phrase();
                phrase1.add(new Chunk("Delete Operator \n", f2_bold));
                phrase1.add(new Chunk(tra.getDel_user(), this.f2_normal));
                cell1 = new PdfPCell(phrase1);
                cell1.setBorder(BOTTOM);
                table.addCell(cell1);

                phrase1 = new Phrase();
                phrase1.add(new Chunk("Delete Motivation \n", f2_bold));
                phrase1.add(new Chunk(tra.getDel_motiv(), this.f2_normal));
                cell1 = new PdfPCell(phrase1);
                cell1.setBorder(BOTTOM);
                table.addCell(cell1);
                document.add(table);
            }
            if (cs1 != null) {

                String verifica = cs1.getCodice();
                if (cs1.getCodice().contains("###")) {
                    verifica = formatAL(on("###").splitToList(cs1.getCodice()).get(1), array_unlockrate, 1).toUpperCase();
                }

                table = new PdfPTable(4);
                table.setWidths(columnWidths4);
                table.setWidthPercentage(100);
                phrase1 = new Phrase();
                phrase1.add(new Chunk("Unlock Code / Motivation | Type \n", f2_bold));
                phrase1.add(new Chunk(verifica + " | " + format_ty_util(cs1.getTy_util()), this.f2_normal));
                cell1 = new PdfPCell(phrase1);
                cell1.setBorder(BOTTOM);
                table.addCell(cell1);
                phrase1 = new Phrase();
                phrase1.add(new Chunk("Date \n", f2_bold));
                phrase1.add(new Chunk(formatStringtoStringDate(cs1.getDt_utilizzo(), patternsqldate, patternnormdate), this.f2_normal));
                cell1 = new PdfPCell(phrase1);
                cell1.setBorder(BOTTOM);
                table.addCell(cell1);
                phrase1 = new Phrase();
                phrase1.add(new Chunk("Operator \n", f2_bold));
                phrase1.add(new Chunk(cs1.getUser_gen(), this.f2_normal));
                cell1 = new PdfPCell(phrase1);
                cell1.setBorder(BOTTOM);
                table.addCell(cell1);
                phrase1 = new Phrase();
                phrase1.add(new Chunk("Reference \n", f2_bold));
                phrase1.add(new Chunk(resp1, this.f2_normal));
                cell1 = new PdfPCell(phrase1);
                cell1.setBorder(BOTTOM);
                table.addCell(cell1);
                document.add(table);
            }

            table = new PdfPTable(4);
            table.setWidths(columnWidths4);
            table.setWidthPercentage(100);
            phrase1 = new Phrase();
            phrase1.add(new Chunk("Type \n", f2_bold));
            phrase1.add(new Chunk(formatType(tra.getTipotr()).toUpperCase(), this.f2_normal));
            cell1 = new PdfPCell(phrase1);
            cell1.setBorder(BOTTOM | TOP | LEFT);
            table.addCell(cell1);
            phrase1 = new Phrase();
            phrase1.add(new Chunk("Id open till \n", f2_bold));
            phrase1.add(new Chunk(oc.getId(), this.f2_normal));
            cell1 = new PdfPCell(phrase1);
            cell1.setBorder(BOTTOM | TOP | RIGHT);
            table.addCell(cell1);
            cellempty.setBorder(NO_BORDER);
            table.addCell(cellempty);
            table.addCell(cellempty);
            document.add(table);

            table = new PdfPTable(4);
            table.setWidths(columnWidths4);
            table.setWidthPercentage(100);
            phrase1 = new Phrase();
            phrase1.add(new Chunk("Till \n", f2_bold));
            phrase1.add(new Chunk(t1.getName(), this.f2_normal));
            cell1 = new PdfPCell(phrase1);
            cell1.setBorder(BOTTOM | TOP | LEFT | RIGHT);
            cell1.setColspan(2);
            table.addCell(cell1);
            table.addCell(cellempty);
            table.addCell(cellempty);
            document.add(table);

            table = new PdfPTable(4);
            table.setWidths(columnWidths4);
            table.setWidthPercentage(100);
            phrase1 = new Phrase();
            phrase1.add(new Chunk("Customer Kind \n", f2_bold));
            phrase1.add(new Chunk(ck.getDe_tipologia_clienti(), this.f2_normal));
            cell1 = new PdfPCell(phrase1);
            cell1.setBorder(BOTTOM | TOP | LEFT | RIGHT);
            cell1.setColspan(2);
            table.addCell(cell1);

            phrase1 = new Phrase("Pay " + inout + " : " + formatMysqltoDisplay(tra.getPay()), this.f0_bold);
            cell1 = new PdfPCell(phrase1);
            if (formatType(tra.getTipotr()).toUpperCase().equalsIgnoreCase("sell")) {
                cell1.setBackgroundColor(new BaseColor(42, 180, 192));
            } else {
                cell1.setBackgroundColor(new BaseColor(53, 152, 220));
            }
            cell1.setBorder(BOTTOM | TOP | RIGHT);
            cell1.setColspan(2);
            table.addCell(cell1);

            document.add(table);

            table = new PdfPTable(5);
            table.setWidths(columnWidths5);
            table.setWidthPercentage(100);
            phrase1 = new Phrase();
            phrase1.add(new Chunk("Total \n", f2_bold));
            phrase1.add(new Chunk(formatMysqltoDisplay(tra.getTotal()), this.f2_normal));
            cell1 = new PdfPCell(phrase1);
            cell1.setBorder(BOTTOM | LEFT);
            table.addCell(cell1);
            phrase1 = new Phrase();
            phrase1.add(new Chunk("Fix \n", f2_bold));
            phrase1.add(new Chunk(formatMysqltoDisplay(tra.getFix()), this.f2_normal));
            cell1 = new PdfPCell(phrase1);
            cell1.setBorder(BOTTOM);
            table.addCell(cell1);
            phrase1 = new Phrase();
            phrase1.add(new Chunk("Com \n", f2_bold));
            phrase1.add(new Chunk(formatMysqltoDisplay(tra.getCom()), this.f2_normal));
            cell1 = new PdfPCell(phrase1);
            cell1.setBorder(BOTTOM);
            table.addCell(cell1);
            phrase1 = new Phrase();
            phrase1.add(new Chunk("Round \n", f2_bold));
            phrase1.add(new Chunk(formatMysqltoDisplay(tra.getRound()), this.f2_normal));
            cell1 = new PdfPCell(phrase1);
            cell1.setBorder(BOTTOM);
            table.addCell(cell1);
            phrase1 = new Phrase();
            phrase1.add(new Chunk("Commission \n", f2_bold));
            phrase1.add(new Chunk(formatMysqltoDisplay(tra.getCommission()), this.f2_normal));
            cell1 = new PdfPCell(phrase1);
            cell1.setBorder(BOTTOM | RIGHT);
            table.addCell(cell1);
            document.add(table);

            table = new PdfPTable(1);
            table.setWidthPercentage(100);
            phrase1 = new Phrase();
            phrase1.add(new Chunk("Note \n", f2_bold));
            phrase1.add(new Chunk(unescapeHtml4(tra.getNote()), this.f2_normal));
            cell1 = new PdfPCell(phrase1);
            cell1.setBorder(BOTTOM | LEFT | RIGHT);
            table.addCell(cell1);
            document.add(table);

            document.add(new Paragraph("\n"));

            if (showagency) {
                Agency ag = get_agency(tra.getAgency_cod());
                table = new PdfPTable(1);
                table.setWidthPercentage(100);
                phrase1 = new Phrase();
                phrase1.add(new Chunk("Agency \n", f2_bold));
                phrase1.add(new Chunk(ag.getAgenzia() + " - " + ag.getDe_agenzia(), this.f2_normal));
                cell1 = new PdfPCell(phrase1);
                cell1.setBorder(BOX);
                table.addCell(cell1);
                document.add(table);
            }

            if (tra.getTipotr().equals("S")) {

                if (tra.getIntbook().equals("1")) {

                    ArrayList<String[]> array_intbook = list_internetbooking();
                    String[] internetbooking_ch = internetbooking_ch(tra.getCod());

                    table = new PdfPTable(4);
                    table.setWidths(columnWidths4);
                    table.setWidthPercentage(100);
                    phrase1 = new Phrase();
                    phrase1.add(new Chunk("Internet Booking", f2_bold));
                    cell1 = new PdfPCell(phrase1);
                    cell1.setBorder(BOTTOM | TOP | LEFT);
                    table.addCell(cell1);
                    phrase1 = new Phrase();
                    phrase1.add(new Chunk("Channel\n", f2_bold));
                    phrase1.add(new Chunk(formatAL(internetbooking_ch[0], array_intbook, 1), this.f2_normal));
                    cell1 = new PdfPCell(phrase1);
                    cell1.setBorder(BOTTOM | TOP);
                    table.addCell(cell1);
                    phrase1 = new Phrase();
                    phrase1.add(new Chunk("Code\n", f2_bold));
                    phrase1.add(new Chunk(internetbooking_ch[1], this.f2_normal));
                    cell1 = new PdfPCell(phrase1);
                    cell1.setBorder(BOTTOM | TOP | RIGHT);
                    table.addCell(cell1);
                    table.addCell(cellempty);
                    document.add(table);
                }

                if (tra.getIntbook_type().equals("1")) {
                    ArrayList<NC_causal> array_nc_causal = list_nc_causal_sell();
                    NC_causal nc1 = getNC_causal(array_nc_causal, tra.getIntbook_1_tf(), null);
                    NC_causal nc2 = getNC_causal(array_nc_causal, tra.getIntbook_2_tf(), null);
                    NC_causal nc3 = getNC_causal(array_nc_causal, tra.getIntbook_3_tf(), null);

                    NC_transaction ts1 = null;
                    NC_transaction ts2 = null;
                    NC_transaction ts3 = null;

                    String v1 = "-";
                    if (nc1 != null) {
                        v1 = tra.getIntbook_1_tf() + " - " + nc1.getDe_causale_nc();

                    }

                    String v2 = "-";
                    if (nc2 != null) {
                        v2 = tra.getIntbook_2_tf() + " - " + nc2.getDe_causale_nc();
                    }
                    String v3 = "-";
                    if (nc3 != null) {
                        v3 = tra.getIntbook_3_tf() + " - " + nc3.getDe_causale_nc();
                    }

                    table = new PdfPTable(4);
                    table.setWidths(columnWidths4);
                    table.setWidthPercentage(100);
                    phrase1 = new Phrase();
                    phrase1.add(new Chunk("Tax Free #1 \n", f2_bold));
                    phrase1.add(new Chunk(v1, this.f2_normal));
                    cell1 = new PdfPCell(phrase1);
                    cell1.setBorder(BOTTOM | TOP | LEFT);
                    table.addCell(cell1);
                    phrase1 = new Phrase();
                    phrase1.add(new Chunk("Mod. #1 \n", f2_bold));
                    phrase1.add(new Chunk(tra.getIntbook_1_mod(), this.f2_normal));
                    cell1 = new PdfPCell(phrase1);
                    cell1.setBorder(BOTTOM | TOP);
                    table.addCell(cell1);
                    phrase1 = new Phrase();
                    phrase1.add(new Chunk("Value. #1 \n", f2_bold));
                    phrase1.add(new Chunk(formatMysqltoDisplay(tra.getIntbook_1_val()), this.f2_normal));
                    cell1 = new PdfPCell(phrase1);
                    cell1.setBorder(BOTTOM | TOP | RIGHT);
                    table.addCell(cell1);
                    table.addCell(cellempty);
                    document.add(table);

                    table = new PdfPTable(4);
                    table.setWidths(columnWidths4);
                    table.setWidthPercentage(100);
                    phrase1 = new Phrase();
                    phrase1.add(new Chunk("Tax Free #2 \n", f2_bold));
                    phrase1.add(new Chunk(v2, this.f2_normal));
                    cell1 = new PdfPCell(phrase1);
                    cell1.setBorder(BOTTOM | LEFT);
                    table.addCell(cell1);
                    phrase1 = new Phrase();
                    phrase1.add(new Chunk("Mod. #2 \n", f2_bold));
                    phrase1.add(new Chunk(tra.getIntbook_2_mod(), this.f2_normal));
                    cell1 = new PdfPCell(phrase1);
                    cell1.setBorder(BOTTOM);
                    table.addCell(cell1);
                    phrase1 = new Phrase();
                    phrase1.add(new Chunk("Value. #2 \n", f2_bold));
                    phrase1.add(new Chunk(formatMysqltoDisplay(tra.getIntbook_2_val()), this.f2_normal));
                    cell1 = new PdfPCell(phrase1);
                    cell1.setBorder(BOTTOM);
                    table.addCell(cell1);
                    phrase1 = new Phrase();
                    phrase1.add(new Chunk("Maccorp \n", f2_bold));
                    phrase1.add(new Chunk(formatMysqltoDisplay(tra.getIntbook_mac()), this.f2_normal));
                    cell1 = new PdfPCell(phrase1);
                    cell1.setBorder(BOTTOM | RIGHT | TOP);
                    table.addCell(cell1);
                    document.add(table);
                    table = new PdfPTable(4);
                    table.setWidths(columnWidths4);
                    table.setWidthPercentage(100);
                    phrase1 = new Phrase();
                    phrase1.add(new Chunk("Tax Free #3 \n", f2_bold));
                    phrase1.add(new Chunk(v3, this.f2_normal));
                    cell1 = new PdfPCell(phrase1);
                    cell1.setBorder(BOTTOM | LEFT);
                    table.addCell(cell1);
                    phrase1 = new Phrase();
                    phrase1.add(new Chunk("Mod. #3 \n", f2_bold));
                    phrase1.add(new Chunk(tra.getIntbook_3_mod(), this.f2_normal));
                    cell1 = new PdfPCell(phrase1);
                    cell1.setBorder(BOTTOM);
                    table.addCell(cell1);
                    phrase1 = new Phrase();
                    phrase1.add(new Chunk("Value. #3 \n", f2_bold));
                    phrase1.add(new Chunk(formatMysqltoDisplay(tra.getIntbook_3_val()), this.f2_normal));
                    cell1 = new PdfPCell(phrase1);
                    cell1.setBorder(BOTTOM);
                    table.addCell(cell1);
                    phrase1 = new Phrase();
                    phrase1.add(new Chunk("Customer \n", f2_bold));
                    phrase1.add(new Chunk(formatMysqltoDisplay(tra.getIntbook_cli()), this.f2_normal));
                    cell1 = new PdfPCell(phrase1);
                    cell1.setBorder(BOTTOM | RIGHT);
                    table.addCell(cell1);

                    ArrayList<NC_transaction> linc = list_nctransactionfromchange(tra.getCod());

                    for (int c = 0; c < linc.size(); c++) {
                        phrase1 = new Phrase();
                        phrase1.add(new Chunk("ID Transaction No Change \n", f2_bold));
                        phrase1.add(new Chunk(linc.get(c).getId(), this.f2_normal));
                        cell1 = new PdfPCell(phrase1);
                        if (c == 0) {
                            cell1.setBorder(LEFT | BOTTOM | RIGHT);
                        } else {
                            cell1.setBorder(BOTTOM | RIGHT);
                        }

                        table.addCell(cell1);
                    }

                    int vuote = 4 - linc.size();

                    for (int c = 0; c < vuote; c++) {
                        table.addCell(cellempty);
                    }

//                    sadds
                    document.add(table);

                }
            }

            document.add(new Paragraph("\n"));
            phrase1 = new Phrase("Figures", f1_bold);
            table = new PdfPTable(1);
            table.setWidthPercentage(100f);
            cell1 = new PdfPCell(phrase1);
            cell1.setBorder(BOX);
            cell1.setHorizontalAlignment(ALIGN_LEFT);
            if (formatType(tra.getTipotr()).toUpperCase().equalsIgnoreCase("sell")) {
                cell1.setBackgroundColor(new BaseColor(42, 180, 192));
            } else {
                cell1.setBackgroundColor(new BaseColor(53, 152, 220));
            }
            table.addCell(cell1);
            document.add(table);

            ArrayList<Ch_transaction_value> listvalue = query_transaction_value(cod);
            for (int i = 0; i < listvalue.size(); i++) {
                Ch_transaction_value val = listvalue.get(i);
                Figures fi1 = get_figures(val.getSupporto(), tra.getFiliale());
                Currency cu1 = getCurrency(val.getValuta());

                String underminjust = formatAL(val.getLow_com_ju(), array_undermincommjustify, 1);
                String kindcom = formatAL(val.getKind_fix_comm(), array_kindcommissionefissa, 1);

                String bb_status = "N";
                String sb_status = "N";

                if (tra.getTipotr().equals("B")) {
                    String pos = "";
                    String ccnumb = "";
                    if (!val.getPos().equals("-") && !val.getPos().equals("N")) {
                        pos = formatAL(val.getPos(), array_credit_card, 1);
                        ccnumb = val.getPosnum();
                    }

                    if (tra.getBb().equals("1")) {
                        if (val.getBb().equals("Y") || val.getBb().equals("F")) {
                            bb_status = val.getBb();
                        }
                    }
                    if (tra.getBb().equals("3") || tra.getBb().equals("4")) {
                        if (val.getBb().equals("Y") || val.getBb().equals("F")) {
                            sb_status = val.getBb();
                        }
                    }

                    table = new PdfPTable(1);
                    table.setWidthPercentage(100);
//                    document.add(new Paragraph("\n"));
                    phrase1 = new Phrase("# " + (i + 1), this.f2_bold);
                    cell1 = new PdfPCell(phrase1);
                    cell1.setBorder(NO_BORDER);
                    cell1.setHorizontalAlignment(ALIGN_CENTER);
                    table.addCell(cell1);
                    document.add(table);

                    table = new PdfPTable(4);
                    table.setWidths(columnWidths4);
                    table.setWidthPercentage(100);
                    phrase1 = new Phrase();
                    phrase1.add(new Chunk("Kind \n", f2_bold));
                    phrase1.add(new Chunk(fi1.getDe_supporto(), this.f2_normal));
                    cell1 = new PdfPCell(phrase1);
                    cell1.setBorder(BOTTOM | TOP | LEFT);
                    table.addCell(cell1);
                    phrase1 = new Phrase();
                    phrase1.add(new Chunk("POS \n", f2_bold));
                    phrase1.add(new Chunk(pos, this.f2_normal));
                    cell1 = new PdfPCell(phrase1);
                    cell1.setBorder(BOTTOM | TOP);
                    table.addCell(cell1);
                    phrase1 = new Phrase();
                    phrase1.add(new Chunk("CC Number \n", f2_bold));
                    phrase1.add(new Chunk(ccnumb, this.f2_normal));
                    cell1 = new PdfPCell(phrase1);
                    cell1.setBorder(BOTTOM | TOP);
                    table.addCell(cell1);
                    phrase1 = new Phrase();
                    phrase1.add(new Chunk("Figures \n", f2_bold));
                    phrase1.add(new Chunk(cu1.getCode() + " - " + cu1.getDescrizione(), this.f2_normal));
                    cell1 = new PdfPCell(phrase1);
                    cell1.setBorder(BOTTOM | TOP | RIGHT);
                    table.addCell(cell1);

                    phrase1 = new Phrase();
                    phrase1.add(new Chunk("Quantity \n", f2_bold));
                    phrase1.add(new Chunk(formatMysqltoDisplay(val.getQuantita()), this.f2_normal));
                    cell1 = new PdfPCell(phrase1);
                    cell1.setBorder(BOTTOM | LEFT);
                    table.addCell(cell1);
                    phrase1 = new Phrase();
                    phrase1.add(new Chunk("Rate \n", f2_bold));
                    phrase1.add(new Chunk(formatMysqltoDisplay(val.getRate()), this.f2_normal));
                    cell1 = new PdfPCell(phrase1);
                    cell1.setBorder(BOTTOM);
                    table.addCell(cell1);
                    phrase1 = new Phrase();
                    phrase1.add(new Chunk("% Com. \n", f2_bold));
                    phrase1.add(new Chunk(formatMysqltoDisplay(val.getCom_perc()), this.f2_normal));
                    cell1 = new PdfPCell(phrase1);
                    cell1.setBorder(BOTTOM);
                    table.addCell(cell1);
                    phrase1 = new Phrase();
                    phrase1.add(new Chunk("% Tot. \n", f2_bold));
                    phrase1.add(new Chunk(formatMysqltoDisplay(val.getCom_perc_tot()), this.f2_normal));
                    cell1 = new PdfPCell(phrase1);
                    cell1.setBorder(BOTTOM | RIGHT);
                    table.addCell(cell1);

                    phrase1 = new Phrase();
                    phrase1.add(new Chunk("Fx Com. \n", f2_bold));
                    phrase1.add(new Chunk(formatMysqltoDisplay(val.getFx_com()), this.f2_normal));
                    cell1 = new PdfPCell(phrase1);
                    cell1.setBorder(BOTTOM | LEFT);
                    table.addCell(cell1);
                    phrase1 = new Phrase();
                    phrase1.add(new Chunk("Tot Com. \n", f2_bold));
                    phrase1.add(new Chunk(formatMysqltoDisplay(val.getTot_com()), this.f2_normal));
                    cell1 = new PdfPCell(phrase1);
                    cell1.setBorder(BOTTOM);
                    table.addCell(cell1);
                    phrase1 = new Phrase();
                    phrase1.add(new Chunk("Net \n", f2_bold));
                    phrase1.add(new Chunk(formatMysqltoDisplay(val.getNet()), this.f2_normal));
                    cell1 = new PdfPCell(phrase1);
                    cell1.setBorder(BOTTOM);
                    table.addCell(cell1);

                    if (newpread) {
                        phrase1 = new Phrase();
                        phrase1.add(new Chunk("Spread \n", f2_bold));
                        phrase1.add(new Chunk(formatMysqltoDisplay(val.getSpread()), this.f2_normal));
                        cell1 = new PdfPCell(phrase1);
                        cell1.setBorder(BOTTOM | RIGHT);
                        table.addCell(cell1);
                    }

                    phrase1 = new Phrase();
                    phrase1.add(new Chunk("Total \n", f2_bold));
                    phrase1.add(new Chunk(formatMysqltoDisplay(val.getTotal()), this.f2_normal));
                    cell1 = new PdfPCell(phrase1);
                    cell1.setBorder(BOTTOM | RIGHT);
                    if (newpread) {
                        cell1.setBorder(BOTTOM | LEFT);
                    }
                    table.addCell(cell1);

                    phrase1 = new Phrase();
                    phrase1.add(new Chunk("Kind Fix Com. \n", f2_bold));
                    phrase1.add(new Chunk(kindcom, this.f2_normal));
                    cell1 = new PdfPCell(phrase1);
                    cell1.setBorder(BOTTOM | LEFT);
                    if (newpread) {
                        cell1.setBorder(BOTTOM);
                    }
                    table.addCell(cell1);
                    phrase1 = new Phrase();
                    phrase1.add(new Chunk("Low Com. Justify \n", f2_bold));
                    phrase1.add(new Chunk(underminjust, this.f2_normal));
                    cell1 = new PdfPCell(phrase1);
                    cell1.setBorder(BOTTOM);
                    table.addCell(cell1);
                    phrase1 = new Phrase();
                    phrase1.add(new Chunk("BB \n", f2_bold));
                    phrase1.add(new Chunk(formatSTATUS_BBSB(bb_status), this.f2_normal));
                    cell1 = new PdfPCell(phrase1);

                    cell1.setBorder(BOTTOM);
                    if (newpread) {
                        cell1.setBorder(BOTTOM | RIGHT);
                    }
                    table.addCell(cell1);

                    phrase1 = new Phrase();
                    phrase1.add(new Chunk("Fidelity Code \n", f2_bold));
                    phrase1.add(new Chunk(val.getBb_fidcode(), this.f2_normal));
                    cell1 = new PdfPCell(phrase1);
                    cell1.setBorder(BOTTOM | RIGHT);
                    if (newpread) {
                        cell1.setBorder(BOTTOM | LEFT);
                    }
                    table.addCell(cell1);

                    phrase1 = new Phrase();
                    phrase1.add(new Chunk("SB \n", f2_bold));
                    phrase1.add(new Chunk(formatSTATUS_BBSB(sb_status), this.f2_normal));
                    cell1 = new PdfPCell(phrase1);
                    cell1.setBorder(LEFT | BOTTOM | RIGHT);
                    if (newpread) {
                        cell1.setBorder(BOTTOM | RIGHT);
                    }
                    table.addCell(cell1);
                    table.addCell(cellempty);
                    table.addCell(cellempty);
                    table.addCell(cellempty);
                    document.add(table);

                } else if (tra.getTipotr().equals("S")) {

                    if (tra.getIntbook().equals("1")) {
                        underminjust = "Internet Booking";
                        kindcom = "Internet Booking";
                    }

                    if (tra.getBb().equals("1") || tra.getBb().equals("2")) {
                        if (val.getBb().equals("Y") || val.getBb().equals("F")) {
                            bb_status = val.getBb();
                        }
                    }
                    if (tra.getBb().equals("3") || tra.getBb().equals("4")) {
                        if (val.getBb().equals("Y") || val.getBb().equals("F")) {
                            sb_status = val.getBb();
                        }
                    }

                    table = new PdfPTable(1);
                    table.setWidthPercentage(100);
//                    document.add(new Paragraph("\n"));
                    phrase1 = new Phrase("# " + (i + 1), this.f2_bold);
                    cell1 = new PdfPCell(phrase1);
                    cell1.setBorder(NO_BORDER);
                    cell1.setHorizontalAlignment(ALIGN_CENTER);
                    table.addCell(cell1);
                    document.add(table);

                    table = new PdfPTable(4);
                    table.setWidths(columnWidths4);
                    table.setWidthPercentage(100);
                    phrase1 = new Phrase();
                    phrase1.add(new Chunk("Kind \n", f2_bold));
                    phrase1.add(new Chunk(fi1.getDe_supporto(), this.f2_normal));
                    cell1 = new PdfPCell(phrase1);
                    cell1.setBorder(BOTTOM | TOP | LEFT);
                    table.addCell(cell1);
                    phrase1 = new Phrase();
                    phrase1.add(new Chunk("Figures \n", f2_bold));
                    phrase1.add(new Chunk(cu1.getCode() + " - " + cu1.getDescrizione(), this.f2_normal));
                    cell1 = new PdfPCell(phrase1);
                    cell1.setBorder(BOTTOM | TOP);
                    table.addCell(cell1);
                    phrase1 = new Phrase();
                    phrase1.add(new Chunk("Quantity \n", f2_bold));
                    phrase1.add(new Chunk(formatMysqltoDisplay(val.getQuantita()), this.f2_normal));
                    cell1 = new PdfPCell(phrase1);
                    cell1.setBorder(BOTTOM | TOP);
                    table.addCell(cell1);
                    phrase1 = new Phrase();
                    phrase1.add(new Chunk("Rate \n", f2_bold));
                    phrase1.add(new Chunk(formatMysqltoDisplay(val.getRate()), this.f2_normal));
                    cell1 = new PdfPCell(phrase1);
                    cell1.setBorder(BOTTOM | TOP | RIGHT);
                    table.addCell(cell1);

                    phrase1 = new Phrase();
                    phrase1.add(new Chunk("% Com. \n", f2_bold));
                    phrase1.add(new Chunk(formatMysqltoDisplay(val.getCom_perc()), this.f2_normal));
                    cell1 = new PdfPCell(phrase1);
                    cell1.setBorder(BOTTOM | LEFT);
                    table.addCell(cell1);
                    phrase1 = new Phrase();
                    phrase1.add(new Chunk("% Tot. \n", f2_bold));
                    phrase1.add(new Chunk(formatMysqltoDisplay(val.getCom_perc_tot()), this.f2_normal));
                    cell1 = new PdfPCell(phrase1);
                    cell1.setBorder(BOTTOM);
                    table.addCell(cell1);
                    phrase1 = new Phrase();
                    phrase1.add(new Chunk("Fx Com. \n", f2_bold));
                    phrase1.add(new Chunk(formatMysqltoDisplay(val.getFx_com()), this.f2_normal));
                    cell1 = new PdfPCell(phrase1);
                    cell1.setBorder(BOTTOM);
                    table.addCell(cell1);
                    phrase1 = new Phrase();
                    phrase1.add(new Chunk("Tot Com. \n", f2_bold));
                    phrase1.add(new Chunk(formatMysqltoDisplay(val.getTot_com()), this.f2_normal));
                    cell1 = new PdfPCell(phrase1);
                    cell1.setBorder(BOTTOM | RIGHT);
                    table.addCell(cell1);

                    phrase1 = new Phrase();
                    phrase1.add(new Chunk("Net \n", f2_bold));
                    phrase1.add(new Chunk(formatMysqltoDisplay(val.getNet()), this.f2_normal));
                    cell1 = new PdfPCell(phrase1);
                    cell1.setBorder(BOTTOM | LEFT);
                    table.addCell(cell1);
                    phrase1 = new Phrase();
                    phrase1.add(new Chunk("Spread \n", f2_bold));
                    phrase1.add(new Chunk(formatMysqltoDisplay(val.getSpread()), this.f2_normal));
                    cell1 = new PdfPCell(phrase1);
                    cell1.setBorder(BOTTOM);
                    table.addCell(cell1);
                    phrase1 = new Phrase();
                    phrase1.add(new Chunk("Total \n", f2_bold));
                    phrase1.add(new Chunk(formatMysqltoDisplay(val.getTotal()), this.f2_normal));
                    cell1 = new PdfPCell(phrase1);
                    cell1.setBorder(BOTTOM);
                    table.addCell(cell1);
                    phrase1 = new Phrase();
                    phrase1.add(new Chunk("Kind Fix Com. \n", f2_bold));
                    phrase1.add(new Chunk(kindcom, this.f2_normal));
                    cell1 = new PdfPCell(phrase1);
                    cell1.setBorder(BOTTOM | RIGHT);
                    table.addCell(cell1);

                    phrase1 = new Phrase();
                    phrase1.add(new Chunk("Low Com. Justify \n", f2_bold));
                    phrase1.add(new Chunk(underminjust, this.f2_normal));
                    cell1 = new PdfPCell(phrase1);
                    cell1.setBorder(BOTTOM | LEFT);
                    table.addCell(cell1);
                    phrase1 = new Phrase();
                    phrase1.add(new Chunk("BB \n", f2_bold));
                    phrase1.add(new Chunk(formatSTATUS_BBSB(bb_status), this.f2_normal));
                    cell1 = new PdfPCell(phrase1);
                    cell1.setBorder(BOTTOM);
                    table.addCell(cell1);

                    phrase1 = new Phrase();
                    phrase1.add(new Chunk("SB \n", f2_bold));
                    phrase1.add(new Chunk(formatSTATUS_BBSB(sb_status), this.f2_normal));
                    cell1 = new PdfPCell(phrase1);
                    cell1.setBorder(BOTTOM);
                    table.addCell(cell1);

                    phrase1 = new Phrase();
                    phrase1.add(new Chunk("Fidelity Code \n", f2_bold));
                    phrase1.add(new Chunk(val.getBb_fidcode(), this.f2_normal));
                    cell1 = new PdfPCell(phrase1);
                    cell1.setBorder(BOTTOM | RIGHT);
                    table.addCell(cell1);

                    document.add(table);

                }

            }

            ArrayList<String[]> agev = agevolazioni_varie();
            ArrayList<String[]> ser = servizi_agg();

            if (tra.getTipotr().equals("S")) {
                if (tra.getIntbook().equals("1")) {
                    String[] internetbooking_ch = internetbooking_ch(tra.getCod());
                    Booking bo = get_prenot(internetbooking_ch[1]);
                    if (bo != null) {
                        table = new PdfPTable(2);
                        table.setWidthPercentage(100f);
                        cell1 = new PdfPCell(new Phrase(blank));
                        cell1.setBorder(NO_BORDER);
                        table.addCell(cell1);
                        table.addCell(cell1);
                        phrase1 = new Phrase("Active Discounts", f1_bold);
                        cell1 = new PdfPCell(phrase1);
                        cell1.setBorder(BOX);
                        cell1.setHorizontalAlignment(ALIGN_CENTER);
                        cell1.setBackgroundColor(new BaseColor(77, 179, 162));
                        table.addCell(cell1);
                        phrase1 = new Phrase("Additional Products", f1_boldW);
                        cell1 = new PdfPCell(phrase1);
                        cell1.setBorder(BOX);
                        cell1.setHorizontalAlignment(ALIGN_CENTER);
                        cell1.setBackgroundColor(new BaseColor(47, 53, 59));
                        table.addCell(cell1);

                        document.add(table);

                        ArrayList<String[]> ad1 = new ArrayList<>();
                        ArrayList<String[]> se1 = new ArrayList<>();

                        for (int x = 0; x < agev.size(); x++) {
                            if (bo.getSconti().contains(agev.get(x)[0])) {
                                String vv;
                                String tipo = agev.get(x)[2];
                                String perc = agev.get(x)[3];
                                String valu = agev.get(x)[4];
                                if (tipo.equals("0")) {
                                    vv = roundDoubleandFormat((fd(bo.getTotal())
                                            / fd(bo.getRate())
                                            + fd(bo.getFx_comm()))
                                            * fd(perc) / 100.00, 2);
                                } else {
                                    vv = valu;
                                }
                                String[] o1 = {agev.get(x)[1], formatMysqltoDisplay(vv) + " €"};
                                ad1.add(o1);
                            }
                        }

                        for (int x = 0; x < ser.size(); x++) {
                            if (bo.getProdotti().contains(ser.get(x)[0])) {
                                String vv;
                                String tipo = ser.get(x)[2];
                                String perc = ser.get(x)[3];
                                String valu = ser.get(x)[4];
                                if (tipo.equals("0")) {
                                    vv = roundDoubleandFormat((fd(bo.getTotal())
                                            / fd(bo.getRate())
                                            + fd(bo.getFx_comm()))
                                            * fd(perc) / 100.00, 2);
                                } else {
                                    vv = valu;
                                }
                                String[] o1 = {ser.get(x)[1], formatMysqltoDisplay(vv) + " €"};
                                se1.add(o1);
                            }
                        }
                        int max = se1.size();
                        if (ad1.size() > max) {
                            max = ad1.size();
                        }

                        table = new PdfPTable(4);
                        table.setWidthPercentage(100f);
                        for (int x = 0; x < max; x++) {
                            String a1 = "";
                            String a2 = "";
                            if (ad1.size() > x) {
                                a1 = ad1.get(x)[0];
                                a2 = ad1.get(x)[1];
                            }
                            String s1 = "";
                            String s2 = "";
                            if (se1.size() > x) {
                                s1 = se1.get(x)[0];
                                s2 = se1.get(x)[1];
                            }

                            phrase1 = new Phrase();
                            phrase1.add(new Chunk(a1, f2_bold));
                            cell1 = new PdfPCell(phrase1);
                            cell1.setHorizontalAlignment(ALIGN_LEFT);
                            table.addCell(cell1);
                            phrase1 = new Phrase();
                            phrase1.add(new Chunk(a2, this.f2_normal));

                            cell1 = new PdfPCell(phrase1);
                            cell1.setHorizontalAlignment(ALIGN_RIGHT);
                            table.addCell(cell1);

                            phrase1 = new Phrase();
                            phrase1.add(new Chunk(s1, f2_bold));
                            cell1 = new PdfPCell(phrase1);
                            cell1.setHorizontalAlignment(ALIGN_LEFT);
                            table.addCell(cell1);
                            phrase1 = new Phrase();
                            phrase1.add(new Chunk(s2, this.f2_normal));
                            cell1 = new PdfPCell(phrase1);
                            cell1.setHorizontalAlignment(ALIGN_RIGHT);
                            table.addCell(cell1);
                        }

                        document.add(table);

                    }
                }
            }

            document.add(new Paragraph("\n"));

            if (!ck.getTipologia_clienti().equals("003")) {
                boolean view = true;
                Client cl = query_Client_transaction(tra.getCod(), tra.getCl_cod());
                String loy = query_LOY_transaction(tra.getCod());
                if (!is_IT) {
                    if (cl.getCode().equals("---") && cl.getRepceca() == null) {
                        view = false;
                    }
                }
                if (view) {

                    document.add(new Paragraph("", f1_bold));
                    phrase1 = new Phrase("KYC - Know Your Customer", f1_bold);
                    document.add(new Paragraph("\n"));
                    table = new PdfPTable(1);
                    table.setWidthPercentage(100f);
                    cell1 = new PdfPCell(phrase1);
                    cell1.setBorder(BOX);
                    cell1.setHorizontalAlignment(ALIGN_LEFT);
                    if (formatType(tra.getTipotr()).toUpperCase().equalsIgnoreCase("sell")) {
                        cell1.setBackgroundColor(new BaseColor(42, 180, 192));
                    } else {
                        cell1.setBackgroundColor(new BaseColor(53, 152, 220));
                    }
                    table.addCell(cell1);
                    document.add(table);

                    if (loy != null) {
                        table = new PdfPTable(3);
                        table.setWidths(columnWidths3C);
                        table.setWidthPercentage(100);

                        phrase1 = new Phrase();
                        phrase1.add(new Chunk("Loyalty Code \n", f2_bold));
                        phrase1.add(new Chunk(loy.toUpperCase(), this.f2_normal));
                        cell1 = new PdfPCell(phrase1);
                        cell1.setBorder(BOTTOM | LEFT | RIGHT);
                        table.addCell(cell1);

                        cellempty.setBorder(NO_BORDER);

                        ArrayList<NC_transaction> li_wc_ti = list_nctransaction_wc_ti(tra);
                        boolean wk = false;
                        String id_wk = "";
                        boolean tic = false;
                        String id_tic = "";
                        for (int v = 0; v < li_wc_ti.size(); v++) {
                            if (li_wc_ti.get(v).getGruppo_nc().equalsIgnoreCase("WELCOMEKIT") || li_wc_ti.get(v).getGruppo_nc().equalsIgnoreCase("WELKITSELL")) {
                                wk = true;
                                id_wk = li_wc_ti.get(v).getId();
                            } else if (li_wc_ti.get(v).getGruppo_nc().equalsIgnoreCase("TOPITACARD")) {
                                tic = true;
                                id_tic = li_wc_ti.get(v).getId();
                            }
                        }

                        if (wk) {
                            if (!id_wk.equals("")) {
                                phrase1 = new Phrase();
                                phrase1.add(new Chunk("Welcome Kit\n", f2_bold));
                                phrase1.add(new Chunk("ID Transaction No Change: " + id_wk, this.f2_normal));
                                cell1 = new PdfPCell(phrase1);
                                cell1.setBorder(BOTTOM | LEFT | RIGHT);
                                table.addCell(cell1);
                            } else {
                                table.addCell(cellempty);
                            }
                        } else {
                            table.addCell(cellempty);
                        }

                        if (tic) {
                            if (!id_tic.equals("")) {
                                phrase1 = new Phrase();
                                phrase1.add(new Chunk("Top Italy Card\n", f2_bold));
                                phrase1.add(new Chunk("ID Transaction No Change: " + id_tic, this.f2_normal));
                                cell1 = new PdfPCell(phrase1);
                                cell1.setBorder(BOTTOM | LEFT | RIGHT);
                                table.addCell(cell1);
                            } else {
                                table.addCell(cellempty);
                            }
                        } else {
                            table.addCell(cellempty);
                        }

                        document.add(table);

                    }

                    table = new PdfPTable(3);
                    table.setWidths(columnWidths3C);
                    table.setWidthPercentage(100);

                    phrase1 = new Phrase();
                    phrase1.add(new Chunk("Surname \n", f2_bold));
                    phrase1.add(new Chunk(cl.getCognome().toUpperCase(), this.f2_normal));
                    cell1 = new PdfPCell(phrase1);
                    cell1.setBorder(BOTTOM | LEFT);
                    table.addCell(cell1);
                    phrase1 = new Phrase();
                    phrase1.add(new Chunk("Sex \n", f2_bold));
                    phrase1.add(new Chunk(formatSex(cl.getSesso()).toUpperCase(), this.f2_normal));
                    cell1 = new PdfPCell(phrase1);
                    cell1.setBorder(BOTTOM | RIGHT | TOP);
                    table.addCell(cell1);
                    cellempty.setBorder(NO_BORDER);
                    table.addCell(cellempty);
                    document.add(table);

                    String country[] = get_country(cl.getNazione());
                    String city[] = getCity_apm(cl.getCitta());
                    String distr[] = get_district(cl.getProvincia());
                    String country_nas[] = get_country(cl.getNazione_nascita());
                    String city_nas[] = getCity_apm(cl.getCitta_nascita());
                    String distr_nas[] = get_district(cl.getProvincia_nascita());

                    String countrycz[] = null;
                    String countrycziss[] = null;
                    String countryczpep[] = null;
                    if (cl.getRepceca() != null) {
                        countrycz = get_country(cl.getRepceca().getHeavy_cz_country());
                        countrycziss = get_country(cl.getRepceca().getHeavy_cz_issuingcountry());
                        countryczpep = get_country(cl.getRepceca().getPep_country());
                    }

                    table = new PdfPTable(3);
                    table.setWidths(columnWidths3C);
                    table.setWidthPercentage(100);
                    phrase1 = new Phrase();
                    phrase1.add(new Chunk("Name \n", f2_bold));
                    phrase1.add(new Chunk(cl.getNome().toUpperCase(), this.f2_normal));
                    cell1 = new PdfPCell(phrase1);
                    cell1.setBorder(BOTTOM | LEFT);
                    table.addCell(cell1);
                    if (is_IT) {
                        phrase1 = new Phrase();
                        phrase1.add(new Chunk("Tax Code \n", f2_bold));
                        phrase1.add(new Chunk(cl.getCodfisc().toUpperCase(), this.f2_normal));
                        cell1 = new PdfPCell(phrase1);
                        cell1.setBorder(BOTTOM | RIGHT);
                        table.addCell(cell1);

                    } else if (is_CZ) {
                        String value_pno = "";
                        if (cl.getRepceca() != null) {
                            value_pno = cl.getRepceca().getHeavy_pno1().toUpperCase();
                        }
                        phrase1 = new Phrase();
                        phrase1.add(new Chunk("Personal No. \n", f2_bold));
                        phrase1.add(new Chunk(value_pno, this.f2_normal));
                        cell1 = new PdfPCell(phrase1);
                        cell1.setBorder(BOTTOM | RIGHT);
                        table.addCell(cell1);
                    } else {
                        cell1 = new PdfPCell(new Phrase(""));
                        cell1.setBorder(BOTTOM | LEFT);
                        table.addCell(cell1);
                    }

                    table.addCell(cellempty);
                    document.add(table);

                    table = new PdfPTable(3);
                    table.setWidths(columnWidths3C);
                    table.setWidthPercentage(100);
                    phrase1 = new Phrase();
                    phrase1.add(new Chunk("Country \n", f2_bold));
                    phrase1.add(new Chunk(country[1].toUpperCase(), this.f2_normal));
                    cell1 = new PdfPCell(phrase1);
                    cell1.setBorder(BOTTOM | LEFT);
                    table.addCell(cell1);
                    phrase1 = new Phrase();
                    phrase1.add(new Chunk("City \n", f2_bold));
                    phrase1.add(new Chunk(city[1].toUpperCase(), this.f2_normal));
                    cell1 = new PdfPCell(phrase1);
                    cell1.setBorder(BOTTOM);
                    table.addCell(cell1);
                    if (is_IT) {
                        phrase1 = new Phrase();
                        phrase1.add(new Chunk("Client is PEP \n", f2_bold));
                        phrase1.add(new Chunk(cl.getPep(), this.f2_normal));
                        cell1 = new PdfPCell(phrase1);
                        cell1.setBorder(BOTTOM | RIGHT | TOP);
                        table.addCell(cell1);
                    } else {
                        phrase1 = new Phrase();
                        phrase1.add(new Chunk("Country of Citizenship \n", f2_bold));
                        phrase1.add(new Chunk(countrycz[1].toUpperCase(), this.f2_normal));
                        cell1 = new PdfPCell(phrase1);
                        cell1.setBorder(BOTTOM | RIGHT | TOP);
                        table.addCell(cell1);
                    }
                    document.add(table);

                    table = new PdfPTable(2);
                    table.setWidths(columnWidths1);
                    table.setWidthPercentage(100);
                    phrase1 = new Phrase();
                    phrase1.add(new Chunk("Address \n", f2_bold));
                    phrase1.add(new Chunk(cl.getIndirizzo(), this.f2_normal));
                    cell1 = new PdfPCell(phrase1);
                    cell1.setBorder(BOTTOM | LEFT | RIGHT);
                    table.addCell(cell1);
                    table.addCell(cellempty);
                    document.add(table);

                    table = new PdfPTable(3);
                    table.setWidths(columnWidths3C);
                    table.setWidthPercentage(100);
                    phrase1 = new Phrase();
                    phrase1.add(new Chunk("Zip Code \n", f2_bold));
                    phrase1.add(new Chunk(cl.getCap(), this.f2_normal));
                    cell1 = new PdfPCell(phrase1);
                    cell1.setBorder(BOTTOM | LEFT);
                    table.addCell(cell1);
                    if (is_IT) {
                        phrase1 = new Phrase();
                        phrase1.add(new Chunk("District \n", f2_bold));
                        phrase1.add(new Chunk(distr[1], this.f2_normal));
                        cell1 = new PdfPCell(phrase1);
                        cell1.setBorder(BOTTOM | RIGHT);
                        table.addCell(cell1);
                    } else {
                        phrase1 = new Phrase("");
                        cell1 = new PdfPCell(phrase1);
                        cell1.setBorder(LEFT);
                        table.addCell(cell1);
                    }

                    table.addCell(cellempty);
                    document.add(table);

                    table = new PdfPTable(1);
                    table.setWidthPercentage(100);
                    phrase1 = new Phrase("Place of Birth", this.f1_bold);

                    cell1 = new PdfPCell(phrase1);
                    setBg(cell1);
                    // cell1.setBackgroundColor(new BaseColor(42,180,192));
                    cell1.setBorder(BOX);
                    cell1.setHorizontalAlignment(ALIGN_CENTER);
                    table.addCell(cell1);
                    document.add(table);
                    table = new PdfPTable(4);
                    table.setWidths(columnWidths4);
                    table.setWidthPercentage(100);
                    phrase1 = new Phrase();
                    phrase1.add(new Chunk("City \n", f2_bold));
                    phrase1.add(new Chunk(city_nas[1], this.f2_normal));
                    cell1 = new PdfPCell(phrase1);
                    // setBg(cell1);
                    cell1.setBorder(BOTTOM | LEFT | TOP);
                    table.addCell(cell1);

                    if (is_IT) {
                        phrase1 = new Phrase();
                        phrase1.add(new Chunk("District \n", f2_bold));
                        phrase1.add(new Chunk(distr_nas[1], this.f2_normal));
                        cell1 = new PdfPCell(phrase1);
                        cell1.setBorder(BOTTOM | TOP);
                        table.addCell(cell1);
                    } else {
                        phrase1 = new Phrase("");
                        cell1 = new PdfPCell(phrase1);
                        cell1.setBorder(BOTTOM | TOP);
                        table.addCell(cell1);
                    }

                    phrase1 = new Phrase();
                    phrase1.add(new Chunk("Country \n", f2_bold));
                    phrase1.add(new Chunk(country_nas[1], this.f2_normal));
                    cell1 = new PdfPCell(phrase1);
                    //  setBg(cell1);
                    cell1.setBorder(BOTTOM | TOP);
                    table.addCell(cell1);
                    phrase1 = new Phrase();
                    phrase1.add(new Chunk("Date \n", f2_bold));
                    phrase1.add(new Chunk(cl.getDt_nascita(), this.f2_normal));
                    cell1 = new PdfPCell(phrase1);
                    //  setBg(cell1);
                    cell1.setBorder(BOTTOM | RIGHT | TOP);
                    table.addCell(cell1);
                    document.add(table);
                    document.add(new Paragraph("\n"));

                    table = new PdfPTable(3);
                    table.setWidths(columnWidths3C);
                    table.setWidthPercentage(100);
                    phrase1 = new Phrase();
                    phrase1.add(new Chunk("Identification Card \n", f2_bold));
                    phrase1.add(new Chunk(formatAL(cl.getTipo_documento(), array_identificationCard, 1), this.f2_normal));
                    cell1 = new PdfPCell(phrase1);

                    if (countrycziss != null) {
                        cell1.setBorder(BOTTOM | LEFT | TOP);
                        table.addCell(cell1);
                        phrase1 = new Phrase();
                        phrase1.add(new Chunk("Issuing Country \n", f2_bold));
                        phrase1.add(new Chunk(countrycziss[1].toUpperCase(), this.f2_normal));
                        cell1 = new PdfPCell(phrase1);
                        cell1.setBorder(BOTTOM | TOP | RIGHT);
                        table.addCell(cell1);

                    } else {
                        cell1.setBorder(BOTTOM | LEFT | TOP | RIGHT);
                        table.addCell(cell1);
                        table.addCell(cellempty);
                    }

                    table.addCell(cellempty);

                    phrase1 = new Phrase();
                    phrase1.add(new Chunk("Number of Id. Card \n", f2_bold));
                    phrase1.add(new Chunk(cl.getNumero_documento(), this.f2_normal));
                    cell1 = new PdfPCell(phrase1);
                    cell1.setBorder(BOTTOM | LEFT);
                    table.addCell(cell1);

//                    if (Constant.is_IT) {
                    phrase1 = new Phrase();
                    phrase1.add(new Chunk("Issue Date \n", f2_bold));
                    phrase1.add(new Chunk(cl.getDt_rilascio_documento(), this.f2_normal));
                    cell1 = new PdfPCell(phrase1);
                    cell1.setBorder(BOTTOM | TOP);
                    table.addCell(cell1);

                    phrase1 = new Phrase();
                    phrase1.add(new Chunk("Expiration Date \n", f2_bold));
                    phrase1.add(new Chunk(cl.getDt_scadenza_documento(), this.f2_normal));
                    cell1 = new PdfPCell(phrase1);
                    cell1.setBorder(BOTTOM | RIGHT | TOP);
                    table.addCell(cell1);
//                    } else {
//                        phrase1 = new Phrase();
//                        phrase1.add(new Chunk("Expiration Date \n", f2_bold));
//                        phrase1.add(new Chunk(cl.getDt_scadenza_documento(), this.f2_normal));
//                        cell1 = new PdfPCell(phrase1);
//                        cell1.setBorder(Rectangle.BOTTOM | Rectangle.RIGHT | Rectangle.TOP);
//                        table.addCell(cell1);
//                        table.addCell(cellempty);
//                    }

                    phrase1 = new Phrase();
                    phrase1.add(new Chunk("Issued By \n", f2_bold));
                    phrase1.add(new Chunk(cl.getRilasciato_da_documento(), this.f2_normal));
                    cell1 = new PdfPCell(phrase1);
                    cell1.setBorder(BOTTOM | LEFT);
                    table.addCell(cell1);
                    phrase1 = new Phrase();
                    phrase1.add(new Chunk("Place of Issue \n", f2_bold));
                    phrase1.add(new Chunk(cl.getLuogo_rilascio_documento(), this.f2_normal));
                    cell1 = new PdfPCell(phrase1);
                    cell1.setBorder(BOTTOM | RIGHT);
                    table.addCell(cell1);
                    table.addCell(cellempty);
                    phrase1 = new Phrase();
                    phrase1.add(new Chunk("Email \n", f2_bold));
                    phrase1.add(new Chunk(cl.getEmail(), this.f2_normal));
                    cell1 = new PdfPCell(phrase1);
                    cell1.setBorder(BOTTOM | LEFT);
                    table.addCell(cell1);
                    phrase1 = new Phrase();
                    phrase1.add(new Chunk("Phone Number \n", f2_bold));
                    phrase1.add(new Chunk(cl.getTelefono(), this.f2_normal));
                    cell1 = new PdfPCell(phrase1);
                    cell1.setBorder(BOTTOM | RIGHT);
                    table.addCell(cell1);
                    table.addCell(cellempty);
                    document.add(table);

                    if (!is_IT) {
                        document.add(new Paragraph("\n"));
                        table = new PdfPTable(4);
                        table.setWidths(columnWidths4);
                        table.setWidthPercentage(100);
                        phrase1 = new Phrase();
                        phrase1.add(new Chunk("International Sanctions \n", f2_bold));
                        phrase1.add(new Chunk(cl.getRepceca().getHeavy_sanctions(), this.f2_normal));
                        cell1 = new PdfPCell(phrase1);
                        cell1.setBorder(BOTTOM | LEFT | TOP);
                        table.addCell(cell1);

                        if (cl.getRepceca().getHeavy_pep().equals("Y")) {
                            phrase1 = new Phrase();
                            phrase1.add(new Chunk("Client is PEP \n", f2_bold));
                            phrase1.add(new Chunk(formatYN(cl.getRepceca().getHeavy_pep()), this.f2_normal));
                            cell1 = new PdfPCell(phrase1);
                            cell1.setBorder(BOTTOM | TOP);
                            table.addCell(cell1);
                            phrase1 = new Phrase();
                            phrase1.add(new Chunk("Position of the PEP \n", f2_bold));
                            phrase1.add(new Chunk(cl.getRepceca().getPep_position(), this.f2_normal));
                            cell1 = new PdfPCell(phrase1);
                            cell1.setBorder(BOTTOM | TOP);
                            table.addCell(cell1);
                            phrase1 = new Phrase();
                            phrase1.add(new Chunk("Country PEP empowered \n", f2_bold));
                            phrase1.add(new Chunk(countryczpep[1].toUpperCase(), this.f2_normal));
                            cell1 = new PdfPCell(phrase1);
                            cell1.setBorder(BOTTOM | TOP | RIGHT);
                            table.addCell(cell1);

                        } else {
                            phrase1 = new Phrase();
                            phrase1.add(new Chunk("Client is PEP \n", f2_bold));
                            phrase1.add(new Chunk(formatYN(cl.getRepceca().getHeavy_pep()), this.f2_normal));
                            cell1 = new PdfPCell(phrase1);
                            cell1.setBorder(BOTTOM | TOP | RIGHT);
                            table.addCell(cell1);
                            table.addCell(cellempty);
                            table.addCell(cellempty);
                        }

                        phrase1 = new Phrase();
                        phrase1.add(new Chunk("Transaction Reason \n", f2_bold));
                        phrase1.add(new Chunk(cl.getRepceca().getHeavy_transactionre(), this.f2_normal));
                        cell1 = new PdfPCell(phrase1);
                        cell1.setBorder(BOTTOM | LEFT | TOP);
                        table.addCell(cell1);
                        phrase1 = new Phrase();
                        phrase1.add(new Chunk("Money Source \n", f2_bold));
                        phrase1.add(new Chunk(cl.getRepceca().getHeavy_moneysource(), this.f2_normal));
                        cell1 = new PdfPCell(phrase1);
                        cell1.setBorder(BOTTOM | TOP);
                        table.addCell(cell1);
                        phrase1 = new Phrase();
                        phrase1.add(new Chunk("Customer's Occupation \n", f2_bold));
                        phrase1.add(new Chunk(cl.getRepceca().getOccupation(), this.f2_normal));
                        cell1 = new PdfPCell(phrase1);
                        cell1.setBorder(BOTTOM | TOP);
                        table.addCell(cell1);
                        phrase1 = new Phrase();
                        phrase1.add(new Chunk("Suspicious Operation\n", f2_bold));
                        if (cl.getRepceca().getOp_sos().equalsIgnoreCase("Y")) {
                            phrase1.add(new Chunk("YES", this.f2_normal));
                        } else {
                            phrase1.add(new Chunk("NO", this.f2_normal));
                        }
                        cell1 = new PdfPCell(phrase1);
                        table.addCell(cell1);

//                        if (cl.getRepceca().getOp_sos().equalsIgnoreCase("Y")) {
//                            
//                        } else {
//                            cell1.setBorder(Rectangle.BOTTOM | Rectangle.RIGHT | Rectangle.TOP);
//                            table.addCell(cell1);
//                            table.addCell(cellempty);
//                        }
                        document.add(table);

                    }

                }

            } else {
                Company c = get_Companylist(array_listCompany, tra.getCl_cod());
                String var;
                if (c != null) {
                    var = c.getRagione_sociale() + " - " + c.getCognome() + " " + c.getNome();
                } else {
                    var = tra.getCl_cod();
                }

                table = new PdfPTable(1);
                table.setWidthPercentage(100);
                phrase1 = new Phrase();
                phrase1.add(new Chunk("Company \n", f2_bold));
                phrase1.add(new Chunk(var, this.f2_normal));
                cell1 = new PdfPCell(phrase1);
                cell1.setBorder(BOTTOM);
                cell1.setHorizontalAlignment(ALIGN_CENTER);
                table.addCell(cell1);
                document.add(table);

            }

            document.add(new Paragraph("", f1_bold));
            phrase1 = new Phrase("Payment Mode", f1_bold);
            document.add(new Paragraph("\n"));
            table = new PdfPTable(1);
            table.setWidthPercentage(100f);
            cell1 = new PdfPCell(phrase1);
            cell1.setBorder(BOX);
            cell1.setHorizontalAlignment(ALIGN_LEFT);
            if (formatType(tra.getTipotr()).toUpperCase().equalsIgnoreCase("sell")) {
                cell1.setBackgroundColor(new BaseColor(42, 180, 192));
            } else {
                cell1.setBackgroundColor(new BaseColor(53, 152, 220));
            }
            table.addCell(cell1);
            if (tra.getTipotr().equals("S")) {
                document.add(table);
            }

            table = new PdfPTable(3);
            table.setWidths(columnWidths3C);
            table.setWidthPercentage(100);
            phrase1 = new Phrase();
            phrase1.add(new Chunk("Local Figures \n", f2_bold));
            phrase1.add(new Chunk(formatAL(tra.getLocalfigures(), array_kind_pay, 1), this.f2_normal));
            cell1 = new PdfPCell(phrase1);
            if (tra.getLocalfigures().equals("01")) {
                cell1.setBorder(BOTTOM | LEFT | RIGHT);
            } else {
                cell1.setBorder(BOTTOM | LEFT);
            }
            table.addCell(cell1);

            switch (tra.getLocalfigures()) {
                case "08":
                    phrase1 = new Phrase();
                    phrase1.add(new Chunk("Bank \n", f2_bold));
                    phrase1.add(new Chunk(formatAL(tra.getPos(), array_bankacc, 1), this.f2_normal));
                    cell1 = new PdfPCell(phrase1);
                    cell1.setBorder(BOTTOM);
                    table.addCell(cell1);
                    break;
                case "06":
                case "07":
                    phrase1 = new Phrase();
                    phrase1.add(new Chunk("Pos \n", f2_bold));
                    phrase1.add(new Chunk(formatAL(tra.getPos(), array_credit_card, 1), this.f2_normal));
                    cell1 = new PdfPCell(phrase1);
                    cell1.setBorder(BOTTOM);
                    table.addCell(cell1);
                    break;
                default:
                    table.addCell(cellempty);
                    break;
            }

            if (tra.getLocalfigures().equals("06")) {
                phrase1 = new Phrase();
                phrase1.add(new Chunk("CC Number \n", f2_bold));
                phrase1.add(new Chunk(tra.getCredccard_number(), this.f2_normal));
                cell1 = new PdfPCell(phrase1);
                cell1.setBorder(BOTTOM | RIGHT);
                table.addCell(cell1);
            } else {
                table.addCell(cellempty);
            }

            if (tra.getTipotr().equals("S")) {
                document.add(table);
            }

            document.close();
            wr.close();
            ou.close();

            String base64 = getBase64(pdf, tra);
            pdf.delete();
            return base64;
        }
        } catch (Exception ex) {
            insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
        }
        return null;

    }

    private static void setBg(PdfPCell cell) {
        cell.setBackgroundColor(LIGHT_GRAY);
        cell.setBorderColor(DARK_GRAY);
        cell.setVerticalAlignment(ALIGN_MIDDLE);
        cell.setHorizontalAlignment(ALIGN_CENTER);
    }

    /**
     *
     * @param pathout
     * @param listcod
     * @param date
     * @param dest
     * @return
     */
    public String print_code_unlock(String pathout, String listcod, String date, String dest) {
        try {
            cellempty.setBorder(NO_BORDER);
            String outputfile = new DateTime().toString("yyMMddhhmmssSSS") + "_code_unlock.pdf";
            File pdf = new File(pathout + outputfile);
            Document document = new Document(A4, 20, 20, 20, 20);
            try(OutputStream ou = new FileOutputStream(pdf)){
            PdfWriter wr = getInstance(document, ou);
            document.open();
            Image img = getInstance(decodeBase64(getConf("path.logocl")));
            img.scalePercent(40.0F);
            document.add(img);
            PdfPTable table = new PdfPTable(2);
            table.setWidths(columnWidths1);
            table.setWidthPercentage(100);
            table.addCell(cellempty);
            table.addCell(cellempty);
            Phrase phrase1 = new Phrase();
            phrase1.add(new Chunk("Date: ", f0_bold));
            PdfPCell cell1 = new PdfPCell(phrase1);
            cell1.setBorder(NO_BORDER);
            table.addCell(cell1);
            phrase1 = new Phrase();
            phrase1.add(new Chunk(date, f0_bold));
            cell1 = new PdfPCell(phrase1);
            cell1.setBorder(NO_BORDER);
            table.addCell(cell1);
            phrase1 = new Phrase();
            phrase1.add(new Chunk("LIST UNLOCK CODES", f0_bold));
            cell1 = new PdfPCell(phrase1);
            cell1.setBorder(NO_BORDER);
            table.addCell(cellempty);
            table.addCell(cellempty);
            table.addCell(cellempty);
            table.addCell(cellempty);
            table.addCell(cellempty);
            table.addCell(cellempty);
            table.addCell(cell1);
            table.addCell(cellempty);
            table.addCell(cellempty);
            table.addCell(cellempty);
            table.addCell(cellempty);
            table.addCell(cellempty);
            if (listcod != null) {
                StringTokenizer tok = new StringTokenizer(listcod, ";");
                if (tok.countTokens() > 0) {
                    while (tok.hasMoreElements()) {
                        phrase1 = new Phrase();
                        phrase1.add(new Chunk(tok.nextToken(), f1_bold));
                        cell1 = new PdfPCell(phrase1);
                        cell1.setBorder(NO_BORDER);
                        table.addCell(cell1);
                        table.addCell(cellempty);
                    }
                }
            }

            table.addCell(cellempty);
            table.addCell(cellempty);

            phrase1 = new Phrase();
            phrase1.add(new Chunk("Assigned To: ", f0_bold));
            cell1 = new PdfPCell(phrase1);
            cell1.setBorder(NO_BORDER);
            table.addCell(cell1);
            phrase1 = new Phrase();
            phrase1.add(new Chunk(dest, f0_bold));
            cell1 = new PdfPCell(phrase1);
            cell1.setBorder(NO_BORDER);
            table.addCell(cell1);

            document.add(table);
            document.close();
            wr.close();
            ou.close();
            String base64 = new String(encodeBase64(readFileToByteArray(pdf)));
            pdf.delete();
            return base64;
        }
        } catch (Exception ex) {
            insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
        }
        return null;

    }

}
