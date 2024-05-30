package rc.so.pdf;

import com.itextpdf.text.BaseColor;
import static com.itextpdf.text.BaseColor.BLACK;
import static com.itextpdf.text.BaseColor.BLUE;
import static com.itextpdf.text.BaseColor.GRAY;
import static com.itextpdf.text.BaseColor.LIGHT_GRAY;
import static com.itextpdf.text.BaseColor.RED;
import static com.itextpdf.text.BaseColor.WHITE;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import static com.itextpdf.text.Element.ALIGN_BOTTOM;
import static com.itextpdf.text.Element.ALIGN_CENTER;
import static com.itextpdf.text.Element.ALIGN_LEFT;
import static com.itextpdf.text.Element.ALIGN_MIDDLE;
import static com.itextpdf.text.Element.ALIGN_RIGHT;
import static com.itextpdf.text.Element.ALIGN_TOP;
import com.itextpdf.text.Font;
import static com.itextpdf.text.Font.BOLD;
import static com.itextpdf.text.Font.BOLDITALIC;
import static com.itextpdf.text.Font.NORMAL;
import static com.itextpdf.text.FontFactory.getFont;
import com.itextpdf.text.Image;
import static com.itextpdf.text.Image.getInstance;
import static com.itextpdf.text.PageSize.A3;
import static com.itextpdf.text.PageSize.A4;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import static com.itextpdf.text.Rectangle.BOTTOM;
import static com.itextpdf.text.Rectangle.NO_BORDER;
import static com.itextpdf.text.Rectangle.TOP;
import com.itextpdf.text.RectangleReadOnly;
import com.itextpdf.text.pdf.AcroFields;
import static com.itextpdf.text.pdf.BaseFont.CP1250;
import static com.itextpdf.text.pdf.BaseFont.HELVETICA;
import static com.itextpdf.text.pdf.BaseFont.IDENTITY_H;
import static com.itextpdf.text.pdf.BaseFont.WINANSI;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfGState;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfWriter;
import static com.itextpdf.text.pdf.PdfWriter.getInstance;
import static com.itextpdf.tool.xml.XMLWorkerHelper.getInstance;
import rc.so.db.Db_Master;
import rc.so.entity.Agency;
import rc.so.entity.BlacklistM;
import rc.so.entity.Booking;
import rc.so.entity.Branch;
import rc.so.entity.Ch_transaction;
import static rc.so.entity.Ch_transaction.formatType;
import rc.so.entity.Ch_transaction_doc;
import rc.so.entity.Ch_transaction_value;
import rc.so.entity.Client;
import static rc.so.entity.Client_CZ.formatYN;
import rc.so.entity.Company;
import rc.so.entity.Currency;
import rc.so.entity.CustomerKind;
import rc.so.entity.ET_change;
import rc.so.entity.Figures;
import rc.so.entity.IT_change;
import rc.so.entity.NC_category;
import rc.so.entity.NC_causal;
import rc.so.entity.NC_transaction;
import rc.so.entity.Newsletters;
import rc.so.entity.Office;
import rc.so.entity.Openclose;
import rc.so.entity.Scontrino_Pa;
import rc.so.entity.Taglio;
import rc.so.entity.Till;
import rc.so.entity.Users;
import static rc.so.entity.Users.formatValidity;
import rc.so.entity.VATcode;
import static rc.so.util.Constant.codnaz;
import static rc.so.util.Constant.czeet;
import static rc.so.util.Constant.is_CZ;
import static rc.so.util.Constant.is_IT;
import static rc.so.util.Constant.is_UK;
import static rc.so.util.Constant.patternnormdate;
import static rc.so.util.Constant.patternnormdate_filter;
import static rc.so.util.Constant.patternsql;
import static rc.so.util.Constant.patternsqldate;
import static rc.so.util.Constant.signoffline;
import static rc.so.util.Engine.category_nations;
import static rc.so.util.Engine.country;
import static rc.so.util.Engine.formatALCurrency;
import static rc.so.util.Engine.formatALNC_category;
import static rc.so.util.Engine.formatALNC_causal;
import static rc.so.util.Engine.formatALNC_causal_ncde;
import static rc.so.util.Engine.formatBankBranch;
import static rc.so.util.Engine.formatCountry_cru;
import static rc.so.util.Engine.formatStatus_general;
import static rc.so.util.Engine.formatStatus_general_cru;
import static rc.so.util.Engine.getCity_apm;
import static rc.so.util.Engine.getConf;
import static rc.so.util.Engine.getFil;
import static rc.so.util.Engine.getNC_category;
import static rc.so.util.Engine.get_ET_change_value;
import static rc.so.util.Engine.get_ET_nochange_value;
import static rc.so.util.Engine.get_country;
import static rc.so.util.Engine.get_figures;
import static rc.so.util.Engine.get_last_modify_rate;
import static rc.so.util.Engine.insertTR;
import static rc.so.util.Engine.isAvaliable;
import static rc.so.util.Engine.isPast;
import static rc.so.util.Engine.isToday;
import static rc.so.util.Engine.isTomorrow;
import static rc.so.util.Engine.list_all_kind;
import static rc.so.util.Engine.list_type_customer;
import static rc.so.util.Engine.list_type_kind;
import static rc.so.util.Engine.list_type_till;
import static rc.so.util.Engine.nc_kind;
import static rc.so.util.Engine.query_LOY_transaction;
import static rc.so.util.Engine.query_transaction_value;
import static rc.so.util.Engine.selectgroupbranch;
import static rc.so.util.Utility.correggi;
import static rc.so.util.Utility.fd;
import static rc.so.util.Utility.formatAL;
import static rc.so.util.Utility.formatMysqltoDisplay;
import static rc.so.util.Utility.formatStringtoStringDate;
import static rc.so.util.Utility.formatStringtoStringDate_null;
import static rc.so.util.Utility.generaId;
import static rc.so.util.Utility.getBase64;
import static rc.so.util.Utility.getStringBase64;
import static rc.so.util.Utility.roundDoubleandFormat;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import static java.lang.Thread.currentThread;
import java.nio.charset.Charset;
import static java.nio.charset.Charset.forName;
import java.util.ArrayList;
import java.util.List;
import static org.apache.commons.codec.binary.Base64.decodeBase64;
import static org.apache.commons.codec.binary.Base64.encodeBase64;
import static org.apache.commons.io.FileUtils.readFileToByteArray;
import static org.apache.commons.io.FileUtils.writeByteArrayToFile;
import static org.apache.commons.lang3.StringUtils.replace;
import static org.apache.commons.lang3.StringUtils.substring;
import org.joda.time.DateTime;

/**
 *
 * @author rcosco
 */
public class Pdf {

    /**
     *
     */
    public static final float[] columnWidths0 = {30.0F, 60.0F};

    /**
     *
     */
    public static final float[] columnWidths1 = {80.0F, 20.0F};

    /**
     *
     */
    public static final float[] columnWidths1bis = {60.0F, 10.0F, 30.0F};

    /**
     *
     */
    public static final float[] columnWidths2 = {60.0F, 60.0F, 40.0F, 40.0F, 40.0F, 40.0F, 40.0F, 40.0F};

    /**
     *
     */
    public static final float[] columnWidths3 = {50.0F, 80.0F, 25.0F, 40.0F, 25.0F, 25.0F, 30.0F, 50.0F};

    /**
     *
     */
    public static final float[] columnWidths4 = {60.0F, 40.0F};

    /**
     *
     */
    public static final float[] columnWidths5 = {50.0F, 50.0F};

    /**
     *
     */
    public static final String br = "\n";

    Font f0_bold, f1_bold, f2_bold, f1_normal, f1_italic, f2_normal, f3_normal, f3_italic, ft_1, ft_2,
            autoc_1, autoc_1_n, autoc_2, autoc_3, autoc_1_it, autoc_2_it, sign, autoc_3_it, autoc_2_normal, autoc_3_normal;

    private static final String filiale = getFil()[0];

    /**
     * Constructor
     */
    public Pdf() {
        this.f0_bold = getFont("Helvetica", "Cp1252", 14.04F, 1);
        this.f1_bold = getFont("Helvetica", "Cp1252", 9.96F, 1);
        this.f2_bold = getFont("Helvetica", "Cp1252", 8.0F, 1);
        this.f1_normal = getFont("Helvetica", "Cp1252", 9.96F, 0);
        this.f2_normal = getFont("Helvetica", "Cp1252", 8.0F, 0);
        this.f3_normal = getFont("Helvetica", "Cp1252", 6.96F, 0);
        this.f3_italic = getFont("Helvetica", "Cp1252", 6.96F, 2);
        this.ft_1 = getFont("Courier", "Cp1252", 7.0F, 0);
        this.ft_2 = getFont("Courier", "Cp1252", 6.0F, 0);
        this.f1_italic = getFont("Helvetica", "Cp1252", 9.96F, 2);
        this.autoc_1 = getFont("Helvetica", "Cp1252", 12.0F, 3);
        this.autoc_1_n = getFont("Helvetica", "Cp1252", 12.0F, 0);
        this.autoc_2 = getFont("Helvetica", "Cp1252", 12.0F, 3, RED);
        this.autoc_3 = getFont("Helvetica", "Cp1252", 12.0F, 3, BLUE);
        this.autoc_1_it = getFont("Helvetica", "Cp1252", 12.0F, 2);
        this.autoc_2_it = getFont("Helvetica", "Cp1252", 12.0F, 2, RED);
        this.autoc_3_it = getFont("Helvetica", "Cp1252", 12.0F, 2, BLUE);
        this.autoc_2_normal = getFont("Helvetica", "Cp1252", 9.96F, 0, RED);
        this.autoc_3_normal = getFont("Helvetica", "Cp1252", 9.96F, 0, BLUE);
        //this.sign = FontFactory.getFont("Helvetica", "Cp1252", 12.0F, 2, BaseColor.WHITE);
        //this.sign = FontFactory.getFont("Helvetica", "Cp1252", 6.96F, 2, BaseColor.WHITE);
        this.sign = getFont("Helvetica", "Cp1252", 5.5F, 0, WHITE);
    }

    //inserire tag
    /**
     *
     * @param tr
     * @return
     */
    public String receipt_nochange_anag(NC_transaction tr) {
//        List<String> eet_code = get_row_codici(null);
//        if (czeet) {
//            //VERIFICA EET
//            eet_code = get_row_codici(invia(null, tr));
//        }
        List<String> eet_code = new ArrayList<>();
        eet_code.add("");
        eet_code.add("");
        Db_Master db = new Db_Master();
        String pathtemp = db.getPath("temp");
        ArrayList<NC_category> array_nc_cat = db.list_nc_category_enabled();

        NC_category cat = getNC_category(array_nc_cat, tr.getGruppo_nc());
        ArrayList<NC_causal> array_nc_caus = db.list_nc_causal_enabled();
        Branch bra = db.get_branch(tr.getFiliale());
        Office of = db.get_national_office();
        db.closeDB();
        String outputfile = tr.getCod() + new DateTime().toString("yyMMddHHmmssSSS") + "_receipt_nc_an.pdf";
        String nomefil = bra.getDe_branch().trim();
        String indfil = bra.getAdd_via() + " " + bra.getAdd_cap() + " " + bra.getAdd_city();
        String piva = of.getVat().trim();
        String data = formatStringtoStringDate(tr.getData(), patternsqldate, patternnormdate);
        String oper = tr.getUser().trim();
        String total = formatMysqltoDisplay(tr.getTotal().trim()).replaceAll("-", "");
        String header = formatALNC_category(tr.getGruppo_nc(), array_nc_cat).trim();
        String nc_causal = formatALNC_causal(tr.getCausale_nc(), array_nc_caus).trim();

        String cogn = tr.getCl_cognome().trim();
        String nome = tr.getCl_nome().trim();
        String addr = tr.getCl_indirizzo().trim();
        String city = tr.getCl_citta().trim();
        String count = tr.getCl_nazione().trim();
        String email = tr.getCl_email().trim();
        String phone = tr.getCl_telefono().trim();
        String txt = cat.getDe_riga();
        String sal = "GRAZIE E ARRIVEDERCI / THANK YOU AND GOODBYE";
        if (is_UK) {
            sal = "THANK YOU AND GOODBYE";
        } else if (is_CZ) {
            sal = "DEKUJEME A NASHLEDANOU / THANK YOU AND GOODBYE";
        }
        try {
            Document document = new Document(A4, 20.0F, 20.0F, 20.0F, 20.0F);
            File pdf = new File(pathtemp + outputfile);
            try(OutputStream ou = new FileOutputStream(pdf)){
            PdfWriter wr = getInstance(document, ou);
            wr.open();
            document.open();
            PdfPTable table = new PdfPTable(2);
            table.setWidths(columnWidths0);
            table.setWidthPercentage(100.0F);
//            Image img = Image.getInstance("web/resource/logocl.png");
            Image img = getInstance(decodeBase64(getConf("path.logocl")));
            img.scalePercent(40.0F);
            PdfPCell cell1 = new PdfPCell(img);
            cell1.setBorder(0);
            PdfPCell cell2 = new PdfPCell(new Phrase(nomefil.trim() + "\n" + indfil.trim() + "\n" + piva.trim(), this.f1_bold));
            cell2.setBorder(0);
            table.addCell(cell1);
            table.addCell(cell2);
            document.add(table);
            document.add(new Phrase("\n", this.f1_bold));
            document.add(new Phrase("\n", this.f1_bold));
            if (is_IT) {
                document.add(new Phrase("Data / Date: " + data, this.f2_bold));
            } else if (is_CZ) {
                document.add(new Phrase("Datum / Date: " + data, this.f2_bold));
            } else if (is_UK) {
                document.add(new Phrase("Date: " + data, this.f2_bold));
            }
            document.add(new Phrase("\n", this.f1_bold));
            if (is_IT) {
                document.add(new Phrase("Operatore / Cashier: " + oper, this.f2_bold));
            } else if (is_CZ) {
                document.add(new Phrase("Pokladní / Cashier: " + oper, this.f2_bold));
            } else if (is_UK) {
                document.add(new Phrase("Cashier: " + oper, this.f2_bold));
            }
            document.add(new Phrase("\n", this.f1_bold));
            document.add(new Phrase("\n", this.f1_bold));
            document.add(new Phrase(header, this.f1_bold));
            document.add(new Phrase("\n", this.f1_bold));
            document.add(new Phrase(nc_causal, this.f1_bold));
            document.add(new Phrase("\n", this.f1_bold));
            document.add(new Phrase("\n", this.f1_bold));
            if (is_IT) {
                document.add(new Phrase("Totale / Total: € " + total, this.f1_bold));
            } else if (is_CZ) {
                document.add(new Phrase("Celkem / Total: CZK " + total, this.f2_bold));
            } else if (is_UK) {
                document.add(new Phrase("Total: GBP " + total, this.f2_bold));
            }
            table = new PdfPTable(2);
            table.setWidths(columnWidths0);
            table.setWidthPercentage(100.0F);
            if (is_IT) {
                cell2 = new PdfPCell(new Phrase("Cognome / Surname", this.f1_bold));
            } else if (is_CZ) {
                cell2 = new PdfPCell(new Phrase("Prijmeni / Surname", this.f1_bold));
            } else {
                cell2 = new PdfPCell(new Phrase("Surname", this.f1_bold));
            }
            cell2.setBorder(0);
            table.addCell(cell2);
            cell2 = new PdfPCell(new Phrase(cogn, this.f1_normal));
            cell2.setBorder(0);
            table.addCell(cell2);
            if (is_IT) {
                cell2 = new PdfPCell(new Phrase("Nome / Name", this.f1_bold));
            } else if (is_CZ) {
                cell2 = new PdfPCell(new Phrase("Jmeno / Name", this.f1_bold));
            } else {
                cell2 = new PdfPCell(new Phrase("Name", this.f1_bold));
            }
            cell2.setBorder(0);
            table.addCell(cell2);
            cell2 = new PdfPCell(new Phrase(nome, this.f1_normal));
            cell2.setBorder(0);
            table.addCell(cell2);
            if (is_IT) {
                cell2 = new PdfPCell(new Phrase("Indirizzo / Address", this.f1_bold));
            } else if (is_CZ) {
                cell2 = new PdfPCell(new Phrase("Adresa / Address", this.f1_bold));
            } else {
                cell2 = new PdfPCell(new Phrase("Address", this.f1_bold));
            }
            cell2.setBorder(0);
            table.addCell(cell2);
            cell2 = new PdfPCell(new Phrase(addr, this.f1_normal));
            cell2.setBorder(0);
            table.addCell(cell2);
            if (is_IT) {
                cell2 = new PdfPCell(new Phrase("Città / City", this.f1_bold));
            } else if (is_CZ) {
                cell2 = new PdfPCell(new Phrase("Mesto / City", this.f1_bold));
            } else {
                cell2 = new PdfPCell(new Phrase("City", this.f1_bold));
            }
            cell2.setBorder(0);
            table.addCell(cell2);
            cell2 = new PdfPCell(new Phrase(city, this.f1_normal));
            cell2.setBorder(0);
            table.addCell(cell2);
            if (is_IT) {
                cell2 = new PdfPCell(new Phrase("Nazione / Country", this.f1_bold));
            } else if (is_CZ) {
                cell2 = new PdfPCell(new Phrase("Zeme / Country", this.f1_bold));
            } else {
                cell2 = new PdfPCell(new Phrase("Country", this.f1_bold));
            }
            cell2.setBorder(0);
            table.addCell(cell2);
            cell2 = new PdfPCell(new Phrase(get_country(count)[1].toUpperCase(), this.f1_normal));
            cell2.setBorder(0);
            table.addCell(cell2);
            cell2 = new PdfPCell(new Phrase("Email", this.f1_bold));
            if (is_CZ) {
                cell2 = new PdfPCell(new Phrase("Email / Email", this.f1_bold));
            }
            cell2.setBorder(0);
            table.addCell(cell2);
            cell2 = new PdfPCell(new Phrase(email, this.f1_normal));
            cell2.setBorder(0);
            table.addCell(cell2);
            if (is_IT) {
                cell2 = new PdfPCell(new Phrase("Telefono / Phone Number", this.f1_bold));
            } else if (is_CZ) {
                cell2 = new PdfPCell(new Phrase("Telefonni cislo / Phone Number", this.f1_bold));
            } else {
                cell2 = new PdfPCell(new Phrase("Phone Number", this.f1_bold));
            }
            cell2.setBorder(0);
            table.addCell(cell2);
            cell2 = new PdfPCell(new Phrase(phone, this.f1_normal));
            cell2.setBorder(0);
            table.addCell(cell2);
            document.add(table);

            if (!txt.equals("")) {
                table = new PdfPTable(1);
                table.setWidthPercentage(100.0F);
                cell2 = new PdfPCell(new Phrase(txt, this.f1_normal));
                cell2.setBorder(0);
                table.addCell(cell2);
                document.add(table);
            }

            document.add(new Phrase("\n", this.f1_bold));
            if (is_IT) {
                document.add(new Phrase("Ricevuto / Received: € " + total, this.f1_bold));
            } else if (is_CZ) {
                document.add(new Phrase("Zaplaceno / Paid: CZK " + total, this.f1_bold));
            } else if (is_UK) {
                document.add(new Phrase("Received: GBP " + total, this.f1_bold));
            }

            if (!tr.getNote().equals("")) {
                document.add(new Phrase("\n", this.f1_bold));
                document.add(new Phrase("\n", this.f1_bold));
                if (is_IT) {
                    document.add(new Phrase("Note / Annotations: " + tr.getNote(), this.f1_bold));
                } else if (is_CZ) {
                    document.add(new Phrase("Poznamky / Notes: " + tr.getNote(), this.f1_bold));
                } else if (is_UK) {
                    document.add(new Phrase("Annotations: " + tr.getNote(), this.f1_bold));
                }
                document.add(new Phrase("\n", this.f1_bold));
            }

            document.add(new Phrase("\n", this.f1_bold));
            if (czeet) {
                document.add(new Phrase("\n", this.f1_bold));
                document.add(new Phrase(eet_code.get(0), this.f1_bold));
                document.add(new Phrase("\n", this.f1_bold));
                document.add(new Phrase(eet_code.get(1), this.f1_bold));
                document.add(new Phrase("\n", this.f1_bold));
            }

            table = new PdfPTable(1);
            table.setWidthPercentage(100.0F);

            cell2 = new PdfPCell(new Phrase(sal, this.f1_bold));
            cell2.setHorizontalAlignment(1);
            cell2.setBorder(0);
            table.addCell(cell2);
            document.add(table);
            table = new PdfPTable(1);
            table.setWidthPercentage(100.0F);
            if (is_IT) {
                cell2 = new PdfPCell(new Phrase("Firma / Signature", this.f1_bold));
            } else if (is_CZ) {
                cell2 = new PdfPCell(new Phrase("Podpis / Signature", this.f1_bold));
            } else {
                cell2 = new PdfPCell(new Phrase("Signature", this.f1_bold));
            }
            cell2.setHorizontalAlignment(2);
            cell2.setBorder(0);
            table.addCell(cell2);
            document.add(table);
            table = new PdfPTable(1);
            table.setWidthPercentage(100.0F);
            //cell2 = new PdfPCell(new Phrase("RAF", this.sign));
            cell2 = new PdfPCell(new Phrase("`sigClie,uid=0,bio=0`", this.sign));
            cell2.setColspan(2);
            cell2.setHorizontalAlignment(2);
            cell2.setBorder(0);
            table.addCell(cell2);
            cell2 = new PdfPCell(new Phrase("_____________________", this.f1_bold));
            cell2.setColspan(2);
            cell2.setHorizontalAlignment(2);
            cell2.setBorder(0);
            table.addCell(cell2);
            document.add(table);
            document.close();
            wr.close();
            ou.close();
            return getStringBase64(pdf);
        }
        } catch (IOException | DocumentException ex) {
            insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
            ex.printStackTrace();
        }

        return null;
    }

    /**
     *
     * @param tr
     * @return
     */
    public String receipt_nochange_norm(NC_transaction tr) {
        List<String> eet_code = new ArrayList<>();
        eet_code.add("");
        eet_code.add("");

        Db_Master db = new Db_Master();
        String pathtemp = db.getPath("temp");
        ArrayList<NC_category> array_nc_cat = db.list_nc_category_enabled();

        NC_category cat = getNC_category(array_nc_cat, tr.getGruppo_nc());

        ArrayList<NC_causal> array_nc_caus = db.list_nc_causal_enabled();
        Branch bra = db.get_branch(tr.getFiliale());
        Office of = db.get_national_office();
        db.closeDB();
        String outputfile = tr.getCod() + new DateTime().toString("yyMMddHHmmssSSS") + "_receipt_nc_an.pdf";
        String nomefil = bra.getDe_branch().trim();
        String indfil = bra.getAdd_via() + " " + bra.getAdd_cap() + " " + bra.getAdd_city();
        String piva = of.getVat().trim();
        String data = formatStringtoStringDate(tr.getData(), patternsqldate, patternnormdate);
        String oper = tr.getUser().trim();
        String total = formatMysqltoDisplay(tr.getTotal().trim()).replaceAll("-", "");
        String header = formatALNC_category(tr.getGruppo_nc(), array_nc_cat).trim();
        String nc_causal = formatALNC_causal(tr.getCausale_nc(), array_nc_caus).trim();
        String txt = cat.getDe_riga();

        String sal = "GRAZIE E ARRIVEDERCI / THANK YOU AND GOODBYE";

        if (is_UK) {
            sal = "THANK YOU AND GOODBYE";
        } else if (is_CZ) {
            sal = "DEKUJEME A NASHLEDANOU / THANK YOU AND GOODBYE";
        }

        db.closeDB();
        try {
            Document document = new Document(A4, 20.0F, 20.0F, 20.0F, 20.0F);
            File pdf = new File(pathtemp + outputfile);
            try(OutputStream ou = new FileOutputStream(pdf)){
            PdfWriter wr = getInstance(document, ou);
            wr.open();
            document.open();
            PdfPTable table = new PdfPTable(2);
            table.setWidths(columnWidths0);
            table.setWidthPercentage(100.0F);
            Image img = getInstance(decodeBase64(getConf("path.logocl")));
            img.scalePercent(40.0F);
            PdfPCell cell1 = new PdfPCell(img);
            cell1.setBorder(0);
            PdfPCell cell2 = new PdfPCell(new Phrase(nomefil.trim() + "\n" + indfil.trim() + "\n" + piva.trim(), this.f1_bold));
            cell2.setBorder(0);
            table.addCell(cell1);
            table.addCell(cell2);
            document.add(table);
            document.add(new Phrase("\n", this.f1_bold));
            document.add(new Phrase("\n", this.f1_bold));

            if (is_IT) {
                document.add(new Phrase("Data / Date: " + data, this.f2_bold));
            } else if (is_CZ) {
                document.add(new Phrase("Datum / Date: " + data, this.f2_bold));
            } else if (is_UK) {
                document.add(new Phrase("Date: " + data, this.f2_bold));
            }

            document.add(new Phrase("\n", this.f1_bold));

            if (is_IT) {
                document.add(new Phrase("Operatore / Cashier: " + oper, this.f2_bold));
            } else if (is_CZ) {
                document.add(new Phrase("Pokladní / Cashier: " + oper, this.f2_bold));
            } else if (is_UK) {
                document.add(new Phrase("Cashier: " + oper, this.f2_bold));
            }

            document.add(new Phrase("\n", this.f1_bold));
            document.add(new Phrase("\n", this.f1_bold));
            document.add(new Phrase(header, this.f1_bold));
            document.add(new Phrase("\n", this.f1_bold));
            document.add(new Phrase(nc_causal, this.f1_bold));
            document.add(new Phrase("\n", this.f1_bold));
            document.add(new Phrase("\n", this.f1_bold));

            if (is_IT) {
                document.add(new Phrase("Totale / Total: € " + total, this.f1_bold));
            } else if (is_CZ) {
                document.add(new Phrase("Celkem / Total: CZK " + total, this.f1_bold));
            } else if (is_UK) {
                document.add(new Phrase("Total: GBP " + total, this.f1_bold));
            }

            if (!txt.equals("")) {
                table = new PdfPTable(1);
                table.setWidthPercentage(100.0F);
                cell2 = new PdfPCell(new Phrase(txt, this.f1_normal));
                cell2.setBorder(0);
                table.addCell(cell2);
                document.add(table);
            }
            document.add(new Phrase("\n", this.f1_bold));
            if (is_IT) {
                document.add(new Phrase("Pagato / Paid: € " + total, this.f1_bold));
            } else if (is_CZ) {
                document.add(new Phrase("Zaplaceno / Paid: CZK " + total, this.f1_bold));
            } else if (is_UK) {
                document.add(new Phrase("Paid: GBP " + total, this.f1_bold));
            }

            if (!tr.getNote().equals("")) {
                document.add(new Phrase("\n", this.f1_bold));
                document.add(new Phrase("\n", this.f1_bold));
                if (is_IT) {
                    document.add(new Phrase("Note / Annotations: " + tr.getNote(), this.f1_bold));
                } else if (is_CZ) {
                    document.add(new Phrase("Poznamky / Notes: " + tr.getNote(), this.f1_bold));
                } else if (is_UK) {
                    document.add(new Phrase("Annotations: " + tr.getNote(), this.f1_bold));
                }
                document.add(new Phrase("\n", this.f1_bold));
            }
            document.add(new Phrase("\n", this.f1_bold));

            if (czeet) {
                document.add(new Phrase("\n", this.f1_bold));
                document.add(new Phrase(eet_code.get(0), this.f1_bold));
                document.add(new Phrase("\n", this.f1_bold));
                document.add(new Phrase(eet_code.get(1), this.f1_bold));
            }

            table = new PdfPTable(1);
            table.setWidthPercentage(100.0F);

            cell2 = new PdfPCell(new Phrase(sal, this.f1_bold));
            cell2.setHorizontalAlignment(1);
            cell2.setBorder(0);
            table.addCell(cell2);
            document.add(table);
            document.close();
            wr.close();
            ou.close();
            return getStringBase64(pdf);
        }
        } catch (IOException | DocumentException ex) {
            insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());

        }
        return null;
    }

    /**
     *
     * @param fileing
     * @param heigth
     */
    public void txt_to_pdf(String fileing, int heigth) {
        String outputfile = fileing + ".pdf";
        try {
            Rectangle rect = new RectangleReadOnly(150.0F, heigth);
            Document document = new Document(rect, 0.0F, 0.0F, 0.0F, 0.0F);
            try(OutputStream ou = new FileOutputStream(outputfile); InputStream is = new FileInputStream(fileing)){
            PdfWriter wr = getInstance(document, ou);
            wr.open();
            document.open();
            Charset charset = forName("CP1252");
            BufferedReader readbuffer = new BufferedReader(new InputStreamReader(is, charset));
            int al = 3;

            PdfPTable table = new PdfPTable(1);
            table.setWidthPercentage(100.0F);
            String linea;
            while ((linea = readbuffer.readLine()) != null) {
                PdfPCell cell2 = new PdfPCell(new Phrase(linea, this.ft_2));
                cell2.setBorder(0);
                cell2.setHorizontalAlignment(al);
                cell2.setPaddingRight(0.0F);
                table.addCell(cell2);
            }
            document.add(table);
            document.close();
            wr.close();
            ou.close();
            is.close();
            readbuffer.close();
        }
        } catch (IOException | DocumentException ex) {
            insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());

        }
    }

    /**
     *
     * @param pathtemp
     * @param result
     * @return
     */
    public static String pdf_transactionList(String pathtemp, ArrayList<Ch_transaction> result) {
        if (result.size() > 0) {
            try {

                String fil = getFil()[0];

                float[] columnWidths00 = new float[]{80f, 20f};
                float[] columnWidths20 = new float[]{5f, 4f, 8f, 8f, 3f, 5f, 3f, 6f, 6f, 8f, 5.5f, 7f, 7f, 8f, 11f, 11f, 10f, 10f, 10f};

                // final String intestazionePdf = "TillTransactionList ";
                Phrase vuoto = new Phrase("\n");
                PdfPCell cellavuota = new PdfPCell(vuoto);

                boolean aggiungidelete = false;

                //resource
//    public static final String logo = "web/resource/logocl.png";
                //other
                Font f0_bold, f1_bold, f2_bold, f1_normal, f2_normal, f3_normal, f3_bold, f4_bold, f5_normal, f5_bold, f6_normal, f6_bold;

                cellavuota.setBorder(NO_BORDER);

                f0_bold = getFont(HELVETICA, WINANSI, 14.04f, BOLD);
                f1_bold = getFont(HELVETICA, WINANSI, 9.96f, BOLD);
                f2_bold = getFont(HELVETICA, WINANSI, 8f, BOLD);
                f3_bold = getFont(HELVETICA, WINANSI, 8f, BOLDITALIC);
                f4_bold = getFont(HELVETICA, WINANSI, 6.96f, BOLD);
                f1_normal = getFont(HELVETICA, WINANSI, 9.96f, NORMAL);
                f2_normal = getFont(HELVETICA, WINANSI, 8f, NORMAL);
                f3_normal = getFont(HELVETICA, WINANSI, 6.96f, NORMAL);
                f5_normal = getFont(HELVETICA, WINANSI, 6f, NORMAL);
                f5_bold = getFont(HELVETICA, WINANSI, 6f, BOLD);
                f6_normal = getFont(HELVETICA, WINANSI, 5.5f, NORMAL);
                f6_bold = getFont(HELVETICA, WINANSI, 5.5f, BOLD);

                File pdf = new File(pathtemp + generaId(50) + "TillTransactionList.pdf");
                Document document = new Document(A4.rotate(), 20, 20, 20, 20);
                try(OutputStream ou = new FileOutputStream(pdf)){
                PdfWriter wr = getInstance(document, ou);
                document.open();

                PdfPTable table = new PdfPTable(2);
                table.setWidths(columnWidths00);
                table.setWidthPercentage(100);

                Phrase phrase1 = new Phrase();
                phrase1.add(new Chunk("Transaction List ", f3_bold));
                PdfPCell cell1 = new PdfPCell(phrase1);
                cell1.setBorder(NO_BORDER);

                Phrase phrase3 = new Phrase();
                phrase3.add(new Chunk("" + "", f3_normal));
                PdfPCell cell3 = new PdfPCell(phrase3);
                cell3.setBorder(NO_BORDER);

                table.addCell(cell1);
                table.addCell(cellavuota);
                table.addCell(cell3);
                table.addCell(cellavuota);
                document.add(table);
                vuoto.setFont(f3_normal);
                // document.add(cellavuota);

                // document.add(vuoto);
                PdfPTable table2 = new PdfPTable(19);
                table2.setWidths(columnWidths20);
                table2.setWidthPercentage(100);

                Phrase phraset2 = new Phrase();

                phraset2.add(new Chunk("Status", f5_bold));

                PdfPCell cellt2 = new PdfPCell(phraset2);
                cellt2.setHorizontalAlignment(ALIGN_LEFT);
                cellt2.setVerticalAlignment(ALIGN_MIDDLE);
                cellt2.setBorder(BOTTOM | TOP);
                cellt2.setBackgroundColor(LIGHT_GRAY);
                table2.addCell(cellt2);

                phraset2 = new Phrase();
                phraset2.add(new Chunk("Branch ID", f5_bold));
                cellt2 = new PdfPCell(phraset2);
                cellt2.setHorizontalAlignment(ALIGN_RIGHT);
                cellt2.setVerticalAlignment(ALIGN_MIDDLE);
                cellt2.setBorder(BOTTOM | TOP);
                cellt2.setBackgroundColor(LIGHT_GRAY);
                table2.addCell(cellt2);

                phraset2 = new Phrase();
                phraset2.add(new Chunk("Code", f5_bold));
                cellt2 = new PdfPCell(phraset2);
                cellt2.setHorizontalAlignment(ALIGN_RIGHT);
                cellt2.setVerticalAlignment(ALIGN_MIDDLE);
                cellt2.setBorder(BOTTOM | TOP);
                cellt2.setBackgroundColor(LIGHT_GRAY);
                table2.addCell(cellt2);

                phraset2 = new Phrase();
                phraset2.add(new Chunk("Date", f5_bold));
                cellt2 = new PdfPCell(phraset2);
                cellt2.setHorizontalAlignment(ALIGN_RIGHT);
                cellt2.setVerticalAlignment(ALIGN_MIDDLE);
                cellt2.setBorder(BOTTOM | TOP);
                cellt2.setBackgroundColor(LIGHT_GRAY);
                table2.addCell(cellt2);

                phraset2 = new Phrase();
                phraset2.add(new Chunk("Till", f5_bold));
                cellt2 = new PdfPCell(phraset2);
                cellt2.setHorizontalAlignment(ALIGN_LEFT);
                cellt2.setVerticalAlignment(ALIGN_MIDDLE);
                cellt2.setBorder(BOTTOM | TOP);
                cellt2.setBackgroundColor(LIGHT_GRAY);
                table2.addCell(cellt2);

                phraset2 = new Phrase();
                phraset2.add(new Chunk("Operator", f5_bold));
                cellt2 = new PdfPCell(phraset2);
                cellt2.setHorizontalAlignment(ALIGN_LEFT);
                cellt2.setVerticalAlignment(ALIGN_MIDDLE);
                cellt2.setBorder(BOTTOM | TOP);
                cellt2.setBackgroundColor(LIGHT_GRAY);
                table2.addCell(cellt2);

                phraset2 = new Phrase();
                phraset2.add(new Chunk("Type", f5_bold));
                cellt2 = new PdfPCell(phraset2);
                cellt2.setHorizontalAlignment(ALIGN_LEFT);
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
                phraset2.add(new Chunk("Net", f5_bold));
                cellt2 = new PdfPCell(phraset2);
                cellt2.setHorizontalAlignment(ALIGN_RIGHT);
                cellt2.setVerticalAlignment(ALIGN_MIDDLE);
                cellt2.setBorder(BOTTOM | TOP);
                cellt2.setBackgroundColor(LIGHT_GRAY);
                table2.addCell(cellt2);

                phraset2 = new Phrase();
                phraset2.add(new Chunk("Commission", f5_bold));
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
                phraset2.add(new Chunk("Invoice", f5_bold));
                cellt2 = new PdfPCell(phraset2);
                cellt2.setHorizontalAlignment(ALIGN_RIGHT);
                cellt2.setVerticalAlignment(ALIGN_MIDDLE);
                cellt2.setBorder(BOTTOM | TOP);
                cellt2.setBackgroundColor(LIGHT_GRAY);
                table2.addCell(cellt2);

                phraset2 = new Phrase();
                phraset2.add(new Chunk("Credit Note", f5_bold));
                cellt2 = new PdfPCell(phraset2);
                cellt2.setHorizontalAlignment(ALIGN_RIGHT);
                cellt2.setVerticalAlignment(ALIGN_MIDDLE);
                cellt2.setBorder(BOTTOM | TOP);
                cellt2.setBackgroundColor(LIGHT_GRAY);
                table2.addCell(cellt2);

                phraset2 = new Phrase();
                phraset2.add(new Chunk("Date Deleted", f5_bold));
                cellt2 = new PdfPCell(phraset2);
                cellt2.setHorizontalAlignment(ALIGN_RIGHT);
                cellt2.setVerticalAlignment(ALIGN_MIDDLE);
                cellt2.setBorder(BOTTOM | TOP);
                cellt2.setBackgroundColor(LIGHT_GRAY);
                table2.addCell(cellt2);

                phraset2 = new Phrase();
                phraset2.add(new Chunk("Client Surname", f5_bold));
                cellt2 = new PdfPCell(phraset2);
                cellt2.setHorizontalAlignment(ALIGN_LEFT);
                cellt2.setVerticalAlignment(ALIGN_MIDDLE);
                cellt2.setBorder(BOTTOM | TOP);
                cellt2.setBackgroundColor(LIGHT_GRAY);
                table2.addCell(cellt2);

                phraset2 = new Phrase();
                phraset2.add(new Chunk("Client Name", f5_bold));
                cellt2 = new PdfPCell(phraset2);
                cellt2.setHorizontalAlignment(ALIGN_LEFT);
                cellt2.setVerticalAlignment(ALIGN_MIDDLE);
                cellt2.setBorder(BOTTOM | TOP);
                cellt2.setBackgroundColor(LIGHT_GRAY);
                table2.addCell(cellt2);

                phraset2 = new Phrase();
                phraset2.add(new Chunk("Client Tax Code", f5_bold));
                cellt2 = new PdfPCell(phraset2);
                cellt2.setHorizontalAlignment(ALIGN_LEFT);
                cellt2.setVerticalAlignment(ALIGN_MIDDLE);
                cellt2.setBorder(BOTTOM | TOP);
                cellt2.setBackgroundColor(LIGHT_GRAY);
                table2.addCell(cellt2);

                phraset2 = new Phrase();
                phraset2.add(new Chunk("Pos/Bank Account", f5_bold));
                cellt2 = new PdfPCell(phraset2);
                cellt2.setHorizontalAlignment(ALIGN_LEFT);
                cellt2.setVerticalAlignment(ALIGN_MIDDLE);
                cellt2.setBorder(BOTTOM | TOP);
                cellt2.setBackgroundColor(LIGHT_GRAY);
                table2.addCell(cellt2);

                phraset2 = new Phrase();
                phraset2.add(new Chunk("Loyalty Code", f5_bold));
                cellt2 = new PdfPCell(phraset2);
                cellt2.setHorizontalAlignment(ALIGN_LEFT);
                cellt2.setVerticalAlignment(ALIGN_MIDDLE);
                cellt2.setBorder(BOTTOM | TOP);
                cellt2.setBackgroundColor(LIGHT_GRAY);
                table2.addCell(cellt2);

                document.add(table2);

                PdfPTable table3;
                //ArrayList<String> dati = siq.getDati();
                Phrase phraset;
                PdfPCell cellt;

                table3 = new PdfPTable(19);
                table3.setWidths(columnWidths20);
                table3.setWidthPercentage(100);

                for (int i = 0; i < result.size(); i++) {

                    Ch_transaction res = (Ch_transaction) result.get(i);
                    String dt_del = "";
                    Db_Master db2 = new Db_Master();
                    Client cl = db2.query_Client_transaction(res.getCod(), res.getCl_cod());
                    db2.closeDB();

                    if (res.getDel_fg().equals("1")) {
                        dt_del = formatStringtoStringDate(res.getDel_dt().split("\\.")[0], "yyyy-MM-dd HH:mm:ss", "dd/MM/yyyy HH:mm:ss");
                    }

                    phraset = new Phrase();
                    phraset.add(new Chunk(res.formatStatus_cru(res.getDel_fg()), f5_bold));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_LEFT);
                    cellt.setVerticalAlignment(ALIGN_BOTTOM);
                    cellt.setBorder(NO_BORDER);
                    table3.addCell(cellt);

                    phraset = new Phrase();
                    phraset.add(new Chunk(res.getFiliale(), f5_normal));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setVerticalAlignment(ALIGN_BOTTOM);
                    cellt.setBorder(NO_BORDER);
                    table3.addCell(cellt);

                    phraset = new Phrase();
                    phraset.add(new Chunk(res.getId(), f5_normal));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setVerticalAlignment(ALIGN_BOTTOM);
                    cellt.setBorder(NO_BORDER);
                    table3.addCell(cellt);

                    phraset = new Phrase();
                    phraset.add(new Chunk(formatStringtoStringDate(res.getData().split("\\.")[0], "yyyy-MM-dd HH:mm:ss", "dd/MM/yyyy HH:mm:ss"), f5_normal));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setVerticalAlignment(ALIGN_BOTTOM);
                    cellt.setBorder(NO_BORDER);
                    table3.addCell(cellt);

                    phraset = new Phrase();
                    phraset.add(new Chunk(res.getTill(), f5_normal));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_LEFT);
                    cellt.setVerticalAlignment(ALIGN_BOTTOM);
                    cellt.setBorder(NO_BORDER);
                    table3.addCell(cellt);

                    phraset = new Phrase();
                    phraset.add(new Chunk(res.getUser(), f5_normal));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_LEFT);
                    cellt.setVerticalAlignment(ALIGN_BOTTOM);
                    cellt.setBorder(NO_BORDER);
                    table3.addCell(cellt);

                    String pos = res.getPos();
                    String ca = "";
                    if (res.getTipotr().equals("B")) {
                        ArrayList<Ch_transaction_value> va  = query_transaction_value(res.getCod());
                        for (int f = 0; f < va.size(); f++) {
                            if (va.get(f).getSupporto().equals("04")) {
                                pos = va.get(f).getPos();
                                ca = " CA ";
                                break;
                            }
                        }
                    }

                    phraset = new Phrase();
                    phraset.add(new Chunk(formatType(res.getTipotr()) + ca, f5_normal));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_LEFT);
                    cellt.setVerticalAlignment(ALIGN_BOTTOM);
                    cellt.setBorder(NO_BORDER);
                    table3.addCell(cellt);

                    phraset = new Phrase();
                    phraset.add(new Chunk(formatMysqltoDisplay(res.getTotal()), f5_normal));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setVerticalAlignment(ALIGN_BOTTOM);
                    cellt.setBorder(NO_BORDER);
                    table3.addCell(cellt);

                    phraset = new Phrase();
                    phraset.add(new Chunk(formatMysqltoDisplay(res.getPay()), f5_normal));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setVerticalAlignment(ALIGN_BOTTOM);
                    cellt.setBorder(NO_BORDER);
                    table3.addCell(cellt);

                    phraset = new Phrase();
                    phraset.add(new Chunk(formatMysqltoDisplay(res.getCommission()), f5_normal));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setVerticalAlignment(ALIGN_BOTTOM);
                    cellt.setBorder(NO_BORDER);
                    table3.addCell(cellt);

                    phraset = new Phrase();
                    phraset.add(new Chunk(formatMysqltoDisplay(res.getSpread_total()), f5_normal));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setVerticalAlignment(ALIGN_BOTTOM);
                    cellt.setBorder(NO_BORDER);
                    table3.addCell(cellt);

                    phraset = new Phrase();
                    phraset.add(new Chunk(res.getFa_number(), f5_normal));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setVerticalAlignment(ALIGN_BOTTOM);
                    cellt.setBorder(NO_BORDER);
                    table3.addCell(cellt);

                    phraset = new Phrase();
                    phraset.add(new Chunk(res.getCn_number(), f5_normal));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setVerticalAlignment(ALIGN_BOTTOM);
                    cellt.setBorder(NO_BORDER);
                    table3.addCell(cellt);

                    phraset = new Phrase();
                    phraset.add(new Chunk(dt_del, f5_normal));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setVerticalAlignment(ALIGN_BOTTOM);
                    cellt.setBorder(NO_BORDER);
                    table3.addCell(cellt);

                    phraset = new Phrase();
                    phraset.add(new Chunk(cl.getCognome().toUpperCase(), f5_normal));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_LEFT);
                    cellt.setVerticalAlignment(ALIGN_BOTTOM);
                    cellt.setBorder(NO_BORDER);
                    table3.addCell(cellt);

                    phraset = new Phrase();
                    phraset.add(new Chunk(cl.getNome().toUpperCase(), f5_normal));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_LEFT);
                    cellt.setVerticalAlignment(ALIGN_BOTTOM);
                    cellt.setBorder(NO_BORDER);
                    table3.addCell(cellt);

                    phraset = new Phrase();
                    phraset.add(new Chunk(cl.getCodfisc().toUpperCase(), f5_normal));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_LEFT);
                    cellt.setVerticalAlignment(ALIGN_BOTTOM);
                    cellt.setBorder(NO_BORDER);
                    table3.addCell(cellt);

                    phraset = new Phrase();
                    phraset.add(new Chunk(pos, f5_normal));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_LEFT);
                    cellt.setVerticalAlignment(ALIGN_BOTTOM);
                    cellt.setBorder(NO_BORDER);
                    table3.addCell(cellt);

                    String loy = query_LOY_transaction(res.getCod(), "central", fil);
                    if (loy == null) {
                        loy = "";
                    }

                    phraset = new Phrase();
                    phraset.add(new Chunk(loy, f5_normal));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_LEFT);
                    cellt.setVerticalAlignment(ALIGN_BOTTOM);
                    cellt.setBorder(NO_BORDER);
                    table3.addCell(cellt);

                }

                document.add(table3);

//////                Document document = new Document(PageSize.A4.rotate(), 20.0F, 20.0F, 20.0F, 20.0F);
//////                OutputStream ou = new FileOutputStream(outputfile);
//////                PdfWriter wr = PdfWriter.getInstance(document, ou);
//////                wr.open();
//////                document.open();
//////                String header = "<html><head><title>Mac 2.0</title><style type=\"text/css\">@import url(http://fonts.googleapis.com/css?family=Roboto:400,500,700,300,100);body {font-family: \"Roboto\", helvetica, arial, sans-serif;font-size: 7px;font-weight: 50;text-rendering: optimizeLegibility;}div.table-title {\tfont-family: \"Roboto\", helvetica, arial, sans-serif;\n  text-align:center;\n  margin: auto;\n  max-width: 600px;\n  padding:5px;\n  width: 90%;\n  font-size: 12px;}.table-title h3 {color: #fafafa;font-size: 14px;font-weight: 400;font-style:normal;font-family: \"Roboto\", helvetica, arial, sans-serif;text-shadow: -1px -1px 1px rgba(0, 0, 0, 0.1);text-transform:uppercase;}.table-fill {background: white;border-radius:3px;border-collapse: collapse;height: 320px;margin: auto;max-width: 600px;padding:5px;width: 100%;box-shadow: 0 5px 10px rgba(0, 0, 0, 0.1);animation: float 5s infinite;\n}\n \nth {\n  color:#000000;;\n  background:#91949A;\n  font-size:12px;\n  font-weight: 50;\n  padding:10px;\n  text-align:left;\n  text-shadow: 0 1px 1px rgba(0, 0, 0, 0.1);\n  vertical-align:middle;\n}\n\nth:first-child {\n  border-top-left-radius:3px;\n}\ntr {\n  border-top: 1px solid #C1C3D1;\n  border-bottom-: 1px solid #C1C3D1;\n  color:#666B85;\n  font-size:11px;\n  font-weight:normal;\n  text-shadow: 0 1px 1px rgba(256, 256, 256, 0.1);\n}\n \ntr:hover td {\n  background:#4E5066;\n  color:#FFFFFF;\n  border-top: 1px solid #22262e;\n  border-bottom: 1px solid #22262e;\n}\n \ntr:first-child {\n  border-top:none;\n}\n\ntr:last-child {\n  border-bottom:none;\n}\n \ntr:nth-child(odd) td {\n  background:#EBEBEB;\n}\n \ntr:nth-child(odd):hover td {\n  background:#4E5066;\n}\n\ntr:last-child td:first-child {\n  border-bottom-left-radius:3px;\n}\n \ntr:last-child td:last-child {\n  border-bottom-right-radius:3px;\n}\n \ntd {\n  background:#FFFFFF;\n padding:10;  vertical-align:middle;\n  font-weight:50;\n  font-size:11px;\n  text-shadow: -1px -1px 1px rgba(0, 0, 0, 0.1);\n  border-bottom: 1px solid #C1C3D1;\n\nth.text-left {\n  text-align: left;\n}\n\nth.text-center {\n  text-align: center;\n}\n\nth.text-right {\n  text-align: right;\n}\n\ntd.text-left {\n  text-align: left;\n}\n\ntd.text-center {\n  text-align: center;\n}\n\ntd.text-right {  text-align: right;}</style></head><body>";
//////
//////                String sttable = "<div class=\"table-title\">Transaction List  </div><table class=\"table-fill\"><thead>";
//////
//////                String thead = "<tr>"
//////                        + "<th class=\"text-left\">Status</th>"
//////                        + "<th class=\"text-left\">Branch ID</th>"
//////                        + "<th class=\"text-left\">Code</th>"
//////                        + "<th class=\"text-left\">Date</th>"
//////                        + "<th class=\"text-left\">Till</th>"
//////                        + "<th class=\"text-left\">Operator</th>"
//////                        + "<th class=\"text-left\">Type</th>"
//////                        + "<th class=\"text-left\">Total</th>"
//////                        + "<th class=\"text-left\">Net</th>"
//////                        + "<th class=\"text-left\">Commission</th>"
//////                        + "<th class=\"text-left\">Spread</th>"
//////                        + "<th class=\"text-left\">Invoice</th>"
//////                        + "<th class=\"text-left\">Credit Note</th>"
//////                        + "<th class=\"text-left\">Date Deleted</th>"
//////                        + "<th class=\"text-left\">Client Surname</th>"
//////                        + "<th class=\"text-left\">Client Name</th>"
//////                        + "<th class=\"text-left\">Client Tax Code</th>"
//////                        + "</tr></thead><tbody>";
//////
//////                String content = "";
//////
//////                for (int i = 0; i < result.size(); i++) {
//////                    Ch_transaction res = (Ch_transaction) result.get(i);
//////                    String dt_del = "";
//////                    Db_Master db2 = new Db_Master();
//////                    Client cl = db2.query_Client_transaction(res.getCod(), res.getCl_cod());
//////                    db2.closeDB();
//////
//////                    if (res.getDel_fg().equals("1")) {
//////                        dt_del = formatStringtoStringDate(res.getDel_dt().split("\\.")[0], "yyyy-MM-dd HH:mm:ss", "dd/MM/yyyy HH:mm:ss");
//////                    }
//////
//////                    content = content + "<tr>"
//////                            + "<td class=\"text-left\">" + res.formatStatus(res.getDel_fg()) + "</td>\n"
//////                            + "<td class=\"text-left\">" + res.getFiliale() + "</td>\n"
//////                            + "<td class=\"text-left\">" + res.getId() + "</td>\n"
//////                            + "<td class=\"text-left\">" + formatStringtoStringDate(res.getData().split("\\.")[0], "yyyy-MM-dd HH:mm:ss", "dd/MM/yyyy HH:mm:ss")
//////                            + "</td>\n" + "<td class=\"text-left\">" + res.getTill()
//////                            + "</td>\n" + "<td class=\"text-left\">" + res.getUser()
//////                            + "</td>\n" + "<td class=\"text-left\">" + res.formatType(res.getTipotr())
//////                            + "</td>\n" + "<td class=\"text-left\">" + formatMysqltoDisplay(res.getTotal())
//////                            + "</td>\n" + "<td class=\"text-left\">" + formatMysqltoDisplay(res.getPay())
//////                            + "</td>\n" + "<td class=\"text-left\">" + formatMysqltoDisplay(res.getCommission())
//////                            + "</td>\n" + "<td class=\"text-left\">" + formatMysqltoDisplay(res.getSpread_total())
//////                            + "</td>\n" + "<td class=\"text-left\">" + res.getFa_number()
//////                            + "</td>\n" + "<td class=\"text-left\">" + res.getCn_number()
//////                            + "</td>\n" + "<td class=\"text-left\">" + dt_del
//////                            + "</td>\n" + "<td class=\"text-left\">" + cl.getCognome().toUpperCase()
//////                            + "</td>\n" + "<td class=\"text-left\">" + cl.getNome().toUpperCase()
//////                            + "</td>\n" + "<td class=\"text-left\">" + cl.getCodfisc().toUpperCase()
//////                            + "</td>\n" + "</tr>\n";
//////                }
//////
//////                String endtable = "</tbody></table>";
//////                String endbody = "</body></html>";
//////
//////                String html = header + sttable + thead + content + endtable + endbody;
//////                InputStream is = new ByteArrayInputStream(html.getBytes());
//////                XMLWorkerHelper.getInstance().parseXHtml(wr, document, is);
//////
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
        }
        return null;
    }

    /**
     *
     * @param outputfile
     * @param result
     * @return
     */
    public static String pdf_nc_category_cat(File outputfile, ArrayList<NC_category> result) {
        if (result.size() > 0) {
            try (OutputStream ou = new FileOutputStream(outputfile)) {
                ArrayList<String[]> array_nc_kind = nc_kind();
                Document document = new Document(A4.rotate(), 20.0F, 20.0F, 20.0F, 20.0F);
                PdfWriter wr = getInstance(document, ou);
                wr.open();
                document.open();
                String header = "<html><head><title>Mac 2.0</title><style type=\"text/css\">@import url(http://fonts.googleapis.com/css?family=Roboto:400,500,700,300,100);body {font-family: \"Roboto\", helvetica, arial, sans-serif;font-size: 10px;font-weight: 50;text-rendering: optimizeLegibility;}div.table-title {\tfont-family: \"Roboto\", helvetica, arial, sans-serif;\n  text-align:center;\n  margin: auto;\n  max-width: 600px;\n  padding:5px;\n  width: 90%;\n  font-size: 20px;}.table-title h3 {color: #fafafa;font-size: 30px;font-weight: 400;font-style:normal;font-family: \"Roboto\", helvetica, arial, sans-serif;text-shadow: -1px -1px 1px rgba(0, 0, 0, 0.1);text-transform:uppercase;}.table-fill {background: white;border-radius:3px;border-collapse: collapse;height: 320px;margin: auto;max-width: 600px;padding:5px;width: 100%;box-shadow: 0 5px 10px rgba(0, 0, 0, 0.1);animation: float 5s infinite;\n}\n \nth {\n  color:#000000;;\n  background:#91949A;\n  font-size:12px;\n  font-weight: 100;\n  padding:10px;\n  text-align:left;\n  text-shadow: 0 1px 1px rgba(0, 0, 0, 0.1);\n  vertical-align:middle;\n}\n\nth:first-child {\n  border-top-left-radius:3px;\n}\ntr {\n  border-top: 1px solid #C1C3D1;\n  border-bottom-: 1px solid #C1C3D1;\n  color:#666B85;\n  font-size:11px;\n  font-weight:normal;\n  text-shadow: 0 1px 1px rgba(256, 256, 256, 0.1);\n}\n \ntr:hover td {\n  background:#4E5066;\n  color:#FFFFFF;\n  border-top: 1px solid #22262e;\n  border-bottom: 1px solid #22262e;\n}\n \ntr:first-child {\n  border-top:none;\n}\n\ntr:last-child {\n  border-bottom:none;\n}\n \ntr:nth-child(odd) td {\n  background:#EBEBEB;\n}\n \ntr:nth-child(odd):hover td {\n  background:#4E5066;\n}\n\ntr:last-child td:first-child {\n  border-bottom-left-radius:3px;\n}\n \ntr:last-child td:last-child {\n  border-bottom-right-radius:3px;\n}\n \ntd {\n  background:#FFFFFF;\n padding:10;  vertical-align:middle;\n  font-weight:300;\n  font-size:11px;\n  text-shadow: -1px -1px 1px rgba(0, 0, 0, 0.1);\n  border-bottom: 1px solid #C1C3D1;\n\nth.text-left {\n  text-align: left;\n}\n\nth.text-center {\n  text-align: center;\n}\n\nth.text-right {\n  text-align: right;\n}\n\ntd.text-left {\n  text-align: left;\n}\n\ntd.text-center {\n  text-align: center;\n}\n\ntd.text-right {  text-align: right;}</style></head><body>";

                String sttable = "<div class=\"table-title\">NO Change Category List  </div><table class=\"table-fill\"><thead>";

                String thead = "<tr><th class=\"text-left\">Code</th>"
                        + "<th class=\"text-left\">Description</th>"
                        + "<th class=\"text-left\">Kind</th>"
                        + "<th class=\"text-left\">Price</th>"
                        + "<th class=\"text-left\">Status/Close</th>"
                        + "<th class=\"text-left\">Accountign Code #1</th>"
                        + "<th class=\"text-left\">Accounting Code #2</th>"
                        + "<th class=\"text-left\">Ticket Fee</th>"
                        + "<th class=\"text-left\">Currency Percent</th>"
                        + "<th class=\"text-left\">Max Ticket</th>"
                        + "<th class=\"text-left\">Edit Ticket Fee</th>"
                        + "<th class=\"text-left\">Receipt Header</th>"
                        + "<th class=\"text-left\">Receipt Test</th>"
                        + "</tr></thead><tbody>";

                String content = "";

                for (int i = 0; i < result.size(); i++) {
                    NC_category res = (NC_category) result.get(i);

                    String tty = "";
                    if (res.getTicket_fee_type().equals("1")) {
                        tty = "checked";
                    }
                    String editfee = "Disabled";
                    if (res.getTicket_enabled().equals("1")) {
                        editfee = "Enabled";
                    }

                    content = content + "<tr>\n<td class=\"text-left\">" + res.getGruppo_nc()
                            + "</td>\n" + "<td class=\"text-left\">" + res.getDe_gruppo_nc()
                            + "</td>\n" + "<td class=\"text-left\">" + formatAL(res.getFg_tipo_transazione_nc(), array_nc_kind, 1)
                            + "</td>\n" + "<td class=\"text-left\">" + formatMysqltoDisplay(res.getIp_prezzo_nc())
                            + "</td>\n" + "<td class=\"text-left\">" + res.formatStatus(res.getAnnullato())
                            + "</td>\n" + "<td class=\"text-left\">" + res.getConto_coge_01()
                            + "</td>\n" + "<td class=\"text-left\">" + res.getConto_coge_02()
                            + "</td>\n" + "<td class=\"text-left\">" + res.getTicket_fee()
                            + "</td>\n" + "<td class=\"text-left\">" + tty
                            + "</td>\n" + "<td class=\"text-left\">" + res.getMax_ticket()
                            + "</td>\n" + "<td class=\"text-left\">" + editfee
                            + "</td>\n" + "<td class=\"text-left\">" + res.getDe_scontrino()
                            + "</td>\n" + "<td class=\"text-left\">" + res.getDe_riga()
                            + "</td>\n" + "</tr>\n";
                }

                String endtable = "</tbody></table>";
                String endbody = "</body></html>";

                String html = header + sttable + thead + content + endtable + endbody;
                InputStream is = new ByteArrayInputStream(html.getBytes());
                getInstance().parseXHtml(wr, document, is);

                document.close();
                wr.close();
                ou.close();
                is.close();

                String base64 = new String(encodeBase64(readFileToByteArray(outputfile)));
                outputfile.delete();
                return base64;
            } catch (IOException | DocumentException ex) {
                insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());

            }
        }
        return null;
    }

    /**
     *
     * @param outputfile
     * @param result
     * @return
     */
    public static String pdf_nc_viewbranch(File outputfile, ArrayList<String[]> result) {
        if (result.size() > 0) {
            try (OutputStream ou = new FileOutputStream(outputfile)) {
                Document document = new Document(A4.rotate(), 20.0F, 20.0F, 20.0F, 20.0F); 
                PdfWriter wr = getInstance(document, ou);
                wr.open();
                document.open();
                String header = "<html><head><title>Mac 2.0</title><style type=\"text/css\">@import url(http://fonts.googleapis.com/css?family=Roboto:400,500,700,300,100);body {font-family: \"Roboto\", helvetica, arial, sans-serif;font-size: 10px;font-weight: 400;text-rendering: optimizeLegibility;}div.table-title {\tfont-family: \"Roboto\", helvetica, arial, sans-serif;\n  text-align:center;\n  margin: auto;\n  max-width: 600px;\n  padding:5px;\n  width: 90%;\n  font-size: 20px;}.table-title h3 {color: #fafafa;font-size: 30px;font-weight: 400;font-style:normal;font-family: \"Roboto\", helvetica, arial, sans-serif;text-shadow: -1px -1px 1px rgba(0, 0, 0, 0.1);text-transform:uppercase;}.table-fill {background: white;border-radius:3px;border-collapse: collapse;height: 320px;margin: auto;max-width: 600px;padding:5px;width: 100%;box-shadow: 0 5px 10px rgba(0, 0, 0, 0.1);animation: float 5s infinite;\n}\n \nth {\n  color:#000000;;\n  background:#91949A;\n  font-size:8px;\n  font-weight: 30;\n  padding:10px;\n  text-align:left;\n  text-shadow: 0 1px 1px rgba(0, 0, 0, 0.1);\n  vertical-align:middle;\n}\n\nth:first-child {\n  border-top-left-radius:3px;\n}\ntr {\n  border-top: 1px solid #C1C3D1;\n  border-bottom-: 1px solid #C1C3D1;\n  color:#666B85;\n  font-size:11px;\n  font-weight:normal;\n  text-shadow: 0 1px 1px rgba(256, 256, 256, 0.1);\n}\n \ntr:hover td {\n  background:#4E5066;\n  color:#FFFFFF;\n  border-top: 1px solid #22262e;\n  border-bottom: 1px solid #22262e;\n}\n \ntr:first-child {\n  border-top:none;\n}\n\ntr:last-child {\n  border-bottom:none;\n}\n \ntr:nth-child(odd) td {\n  background:#EBEBEB;\n}\n \ntr:nth-child(odd):hover td {\n  background:#4E5066;\n}\n\ntr:last-child td:first-child {\n  border-bottom-left-radius:3px;\n}\n \ntr:last-child td:last-child {\n  border-bottom-right-radius:3px;\n}\n \ntd {\n  background:#FFFFFF;\n padding:10;  vertical-align:middle;\n  font-weight:300;\n  font-size:11px;\n  text-shadow: -1px -1px 1px rgba(0, 0, 0, 0.1);\n  border-bottom: 1px solid #C1C3D1;\n\nth.text-left {\n  text-align: left;\n}\n\nth.text-center {\n  text-align: center;\n}\n\nth.text-right {\n  text-align: right;\n}\n\ntd.text-left {\n  text-align: left;\n}\n\ntd.text-center {\n  text-align: center;\n}\n\ntd.text-right {  text-align: right;}</style></head><body>";

                String sttable = "<div class=\"table-title\">NO Change ViewBranch List  </div><table class=\"table-fill\"><thead>";

                String thead = "<tr><th class=\"text-left\">Category</th>"
                        + "<th class=\"text-left\">Causal</th>"
                        + "<th class=\"text-left\">Description</th>"
                        + "<th class=\"text-left\">Kind</th>"
                        + "<th class=\"text-left\">Price</th>"
                        + "<th class=\"text-left\">Status</th>"
                        + "</tr></thead><tbody>";

                String content = "";

                for (int i = 0; i < result.size(); i++) {
                    String[] res = result.get(i);
                    String classval = "";
                    if (res[1].equals("")) {
                        classval = "style='background:#B8B9C1;'";
                    }

                    content = content + "<tr>\n<td class=\"text-left\" " + classval + ">" + res[0]
                            + "</td>\n" + "<td class=\"text-left\"" + classval + ">" + res[1]
                            + "</td>\n" + "<td class=\"text-left\"" + classval + ">" + res[2]
                            + "</td>\n" + "<td class=\"text-left\"" + classval + ">" + res[3]
                            + "</td>\n" + "<td class=\"text-left\"" + classval + ">" + res[4]
                            + "</td>\n" + "<td class=\"text-left\"" + classval + ">" + res[5]
                            + "</td>\n" + "</tr>\n";

                }

                String endtable = "</tbody></table>";
                String endbody = "</body></html>";

                String html = header + sttable + thead + content + endtable + endbody;
                InputStream is = new ByteArrayInputStream(html.getBytes());
                getInstance().parseXHtml(wr, document, is);

                document.close();
                wr.close();
                ou.close();
                is.close();

                String base64 = new String(encodeBase64(readFileToByteArray(outputfile)));
                outputfile.delete();
                return base64;
            } catch (IOException | DocumentException ex) {
                insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
            }
        }
        return null;
    }

    /**
     *
     * @param outputfile
     * @param result
     * @return
     */
    public static String pdf_es_paymat(File outputfile, ArrayList<Taglio> result) {
        if (result.size() > 0) {
            try (OutputStream ou = new FileOutputStream(outputfile)) {
                Document document = new Document(A3.rotate(), 20.0F, 20.0F, 20.0F, 20.0F);
                PdfWriter wr = getInstance(document, ou);
                wr.open();
                document.open();
                String header = "<html><head><title>Mac 2.0</title><style type=\"text/css\">@import url(http://fonts.googleapis.com/css?family=Roboto:400,500,700,300,100);body {font-family: \"Roboto\", helvetica, arial, sans-serif;font-size: 8px;font-weight: 50;}div.table-title {\tfont-family: \"Roboto\", helvetica, arial, sans-serif;\n  text-align:center;\n  margin: auto;\n  max-width: 600px;\n  padding:5px;\n  width: 90%;\n  font-size: 12px;}.table-title h3 {color: #fafafa;font-size: 8px;font-style:small;font-family: \"Roboto\", helvetica, arial, sans-serif;text-shadow: -1px -1px 1px rgba(0, 0, 0, 0.1);text-transform:uppercase;}.table-fill {background: white;border-radius:3px;border-collapse: collapse;height: 320px;margin: auto;max-width: 600px;padding:5px;width: 100%;box-shadow: 0 5px 10px rgba(0, 0, 0, 0.1);animation: float 5s infinite;\n}\n \nth {\n  color:#000000;;\n  background:#91949A;\n  font-size:8px;\n  font-weight: 50;\n  padding:5px;\n  text-align:left;\n  text-shadow: 0 1px 1px rgba(0, 0, 0, 0.1);\n  vertical-align:middle;\n}\n\nth:first-child {\n  border-top-left-radius:3px;\n}\ntr {\n  border-top: 1px solid #C1C3D1;\n  border-bottom-: 1px solid #C1C3D1;\n  color:#666B85;\n  font-size:8px;\n  font-weight:small;\n  text-shadow: 0 1px 1px rgba(256, 256, 256, 0.1);\n}\n \ntr:hover td {\n  background:#4E5066;\n  color:#FFFFFF;\n  border-top: 1px solid #22262e;\n  border-bottom: 1px solid #22262e;\n}\n \ntr:first-child {\n  border-top:none;\n}\n\ntr:last-child {\n  border-bottom:none;\n}\n \ntr:nth-child(odd) td {\n  background:#EBEBEB;\n}\n \ntr:nth-child(odd):hover td {\n  background:#4E5066;\n}\n\ntr:last-child td:first-child {\n  border-bottom-left-radius:3px;\n}\n \ntr:last-child td:last-child {\n  border-bottom-right-radius:3px;\n}\n \ntd {\n  background:#FFFFFF;\n padding:10;  vertical-align:middle;\n  font-weight:50;\n  font-size:8px;\n  text-shadow: -1px -1px 1px rgba(0, 0, 0, 0.1);\n  border-bottom: 1px solid #C1C3D1;\n\nth.text-left {\n  text-align: left;\n}\n\nth.text-center {\n  text-align: center;\n}\n\nth.text-right {\n  text-align: right;\n}\n\ntd.text-left {\n  text-align: left;\n}\n\ntd.text-center {\n  text-align: center;\n}\n\ntd.text-right {  text-align: right;}</style></head><body>";

                String sttable = "<div class=\"table-title\">External Services Paymat List  </div><table class=\"table-fill\"><thead>";

                String thead = "<tr><th class=\"text-left\">Brand</th>"
                        + "<th class=\"text-left\">Description</th>"
                        + "<th class=\"text-left\">Code</th>"
                        + "</tr></thead><tbody>";

                String content = "";

                for (int i = 0; i < result.size(); i++) {
                    Taglio res = result.get(i);

                    String tip = res.getTipologia();
                    if (res.getTipologia().equals("0")) {
                        tip = "";
                    }

                    content = content + "<tr>\n<td class=\"text-left\" >" + res.getBrand()
                            + "</td>\n" + "<td class=\"text-left\">" + res.getDescrizione() + " " + tip
                            + "</td>\n" + "<td class=\"text-left\">" + res.getCodiceTaglio()
                            + "</td>\n" + "</tr>\n";

                }

                String endtable = "</tbody></table>";
                String endbody = "</body></html>";

                String html = header + sttable + thead + content + endtable + endbody;
                InputStream is = new ByteArrayInputStream(html.getBytes());
                getInstance().parseXHtml(wr, document, is);

                document.close();
                wr.close();
                ou.close();
                is.close();

                String base64 = new String(encodeBase64(readFileToByteArray(outputfile)));
                outputfile.delete();
                return base64;
            } catch (IOException | DocumentException ex) {
                insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
            }
        }
        return null;
    }

    /**
     *
     * @param outputfile
     * @param result
     * @return
     */
    public static String pdf_nc_category_cas(File outputfile, ArrayList<NC_causal> result) {
        if (result.size() > 0) {
            try (OutputStream ou = new FileOutputStream(outputfile)) {
                ArrayList<String[]> array_nc_kind = nc_kind();

                Document document = new Document(A4.rotate(), 20.0F, 20.0F, 20.0F, 20.0F);
                PdfWriter wr = getInstance(document, ou);
                wr.open();
                document.open();
                String header = "<html><head><title>Mac 2.0</title><style type=\"text/css\">@import url(http://fonts.googleapis.com/css?family=Roboto:400,500,700,300,100);body {font-family: \"Roboto\", helvetica, arial, sans-serif;font-size: 10px;font-weight: 400;text-rendering: optimizeLegibility;}div.table-title {\tfont-family: \"Roboto\", helvetica, arial, sans-serif;\n  text-align:center;\n  margin: auto;\n  max-width: 600px;\n  padding:5px;\n  width: 90%;\n  font-size: 20px;}.table-title h3 {color: #fafafa;font-size: 30px;font-weight: 400;font-style:normal;font-family: \"Roboto\", helvetica, arial, sans-serif;text-shadow: -1px -1px 1px rgba(0, 0, 0, 0.1);text-transform:uppercase;}.table-fill {background: white;border-radius:3px;border-collapse: collapse;height: 320px;margin: auto;max-width: 600px;padding:5px;width: 100%;box-shadow: 0 5px 10px rgba(0, 0, 0, 0.1);animation: float 5s infinite;\n}\n \nth {\n  color:#000000;;\n  background:#91949A;\n  font-size:12px;\n  font-weight: 100;\n  padding:10px;\n  text-align:left;\n  text-shadow: 0 1px 1px rgba(0, 0, 0, 0.1);\n  vertical-align:middle;\n}\n\nth:first-child {\n  border-top-left-radius:3px;\n}\ntr {\n  border-top: 1px solid #C1C3D1;\n  border-bottom-: 1px solid #C1C3D1;\n  color:#666B85;\n  font-size:11px;\n  font-weight:normal;\n  text-shadow: 0 1px 1px rgba(256, 256, 256, 0.1);\n}\n \ntr:hover td {\n  background:#4E5066;\n  color:#FFFFFF;\n  border-top: 1px solid #22262e;\n  border-bottom: 1px solid #22262e;\n}\n \ntr:first-child {\n  border-top:none;\n}\n\ntr:last-child {\n  border-bottom:none;\n}\n \ntr:nth-child(odd) td {\n  background:#EBEBEB;\n}\n \ntr:nth-child(odd):hover td {\n  background:#4E5066;\n}\n\ntr:last-child td:first-child {\n  border-bottom-left-radius:3px;\n}\n \ntr:last-child td:last-child {\n  border-bottom-right-radius:3px;\n}\n \ntd {\n  background:#FFFFFF;\n padding:10;  vertical-align:middle;\n  font-weight:300;\n  font-size:11px;\n  text-shadow: -1px -1px 1px rgba(0, 0, 0, 0.1);\n  border-bottom: 1px solid #C1C3D1;\n\nth.text-left {\n  text-align: left;\n}\n\nth.text-center {\n  text-align: center;\n}\n\nth.text-right {\n  text-align: right;\n}\n\ntd.text-left {\n  text-align: left;\n}\n\ntd.text-center {\n  text-align: center;\n}\n\ntd.text-right {  text-align: right;}</style></head><body>";

                String sttable = "<div class=\"table-title\">NO Change Causal List  </div><table class=\"table-fill\"><thead>";

                String thead = "<tr><th class=\"text-left\">Code</th>"
                        + "<th class=\"text-left\">Description</th>"
                        + "<th class=\"text-left\">Price</th>"
                        + "<th class=\"text-left\">Status</th>"
                        + "<th class=\"text-left\">Kind</th>"
                        + "<th class=\"text-left\">Category</th>"
                        + "<th class=\"text-left\">Ticket Fee</th>"
                        + "<th class=\"text-left\">Max Ticket</th>"
                        + "<th class=\"text-left\">External Services</th>"
                        + "<th class=\"text-left\">Bonus</th>"
                        + "<th class=\"text-left\">Print Receipt</th>"
                        + "</tr></thead><tbody>";

                String content = "";

                for (int i = 0; i < result.size(); i++) {
                    NC_causal res = (NC_causal) result.get(i);

                    NC_category ncc = getNC_category(res.getGruppo_nc());
                    String cat = ncc.getDe_gruppo_nc();

                    String bns = "Disabled";
                    if (res.getBonus().equals("1")) {
                        bns = "Enabled";
                    }

                    String btc = "Disabled";
                    if (res.getFg_batch().equals("1")) {
                        btc = "Enabled";
                    }

                    String sct = "Disabled";
                    if (res.getFg_scontrino().equals("1")) {
                        sct = "Enabled";
                    }

                    String pm = "Disabled";
                    if (res.getPaymat().equals("1")) {
                        pm = "Enabled";
                    }

                    content = content + "<tr>\n<td class=\"text-left\">" + res.getCausale_nc()
                            + "</td>\n" + "<td class=\"text-left\">" + res.getDe_causale_nc()
                            + "</td>\n" + "<td class=\"text-left\">" + formatMysqltoDisplay(res.getIp_prezzo_nc())
                            + "</td>\n" + "<td class=\"text-left\">" + res.formatStatus(res.getAnnullato())
                            + "</td>\n" + "<td class=\"text-left\">" + formatAL(res.getFg_tipo_transazione_nc(), array_nc_kind, 1)
                            + "</td>\n" + "<td class=\"text-left\">" + cat
                            + "</td>\n" + "<td class=\"text-left\">" + res.getTicket_fee()
                            + "</td>\n" + "<td class=\"text-left\">" + res.getMax_ticket()
                            + "</td>\n" + "<td class=\"text-left\">" + pm
                            + "</td>\n" + "<td class=\"text-left\">" + bns
                            + "</td>\n" + "<td class=\"text-left\">" + sct
                            + "</td>\n" + "</tr>\n";
                }

                String endtable = "</tbody></table>";
                String endbody = "</body></html>";

                String html = header + sttable + thead + content + endtable + endbody;
                InputStream is = new ByteArrayInputStream(html.getBytes());
                getInstance().parseXHtml(wr, document, is);

                document.close();
                wr.close();
                ou.close();
                is.close();

                String base64 = new String(encodeBase64(readFileToByteArray(outputfile)));
                outputfile.delete();
                return base64;
            } catch (IOException | DocumentException ex) {
                insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
            }
        }
        return null;
    }

    /**
     *
     * @param outputfile
     * @param result
     * @return
     */
    public static String pdf_transactionncList(File outputfile, ArrayList<NC_transaction> result) {
        if (result.size() > 0) {
            try (OutputStream ou = new FileOutputStream(outputfile)) {

                Db_Master db = new Db_Master();
                ArrayList<NC_category> array_nc_cat = db.list_nc_category_enabled();
                ArrayList<NC_causal> array_nc_caus = db.list_nc_causal_enabled();
                ArrayList<String[]> array_nc_descr = db.list_nc_descr();
                boolean iscentral = db.getCodLocal(false)[0].equals("000");
                db.closeDB();
                Document document = new Document(A4.rotate(), 20.0F, 20.0F, 20.0F, 20.0F);
                PdfWriter wr = getInstance(document, ou);
                wr.open();
                document.open();
                String header = "<html><head><title>Mac 2.0</title><style type=\"text/css\">@import url(http://fonts.googleapis.com/css?family=Roboto:400,500,700,300,100);body {font-family: \"Roboto\", helvetica, arial, sans-serif;font-size: 10px;font-weight: 400;text-rendering: optimizeLegibility;}div.table-title {\tfont-family: \"Roboto\", helvetica, arial, sans-serif;\n  text-align:center;\n  margin: auto;\n  max-width: 600px;\n  padding:5px;\n  width: 90%;\n  font-size: 20px;}.table-title h3 {color: #fafafa;font-size: 30px;font-weight: 400;font-style:normal;font-family: \"Roboto\", helvetica, arial, sans-serif;text-shadow: -1px -1px 1px rgba(0, 0, 0, 0.1);text-transform:uppercase;}.table-fill {background: white;border-radius:3px;border-collapse: collapse;height: 320px;margin: auto;max-width: 600px;padding:5px;width: 100%;box-shadow: 0 5px 10px rgba(0, 0, 0, 0.1);animation: float 5s infinite;\n}\n \nth {\n  color:#000000;;\n  background:#91949A;\n  font-size:12px;\n  font-weight: 100;\n  padding:10px;\n  text-align:left;\n  text-shadow: 0 1px 1px rgba(0, 0, 0, 0.1);\n  vertical-align:middle;\n}\n\nth:first-child {\n  border-top-left-radius:3px;\n}\ntr {\n  border-top: 1px solid #C1C3D1;\n  border-bottom-: 1px solid #C1C3D1;\n  color:#666B85;\n  font-size:11px;\n  font-weight:normal;\n  text-shadow: 0 1px 1px rgba(256, 256, 256, 0.1);\n}\n \ntr:hover td {\n  background:#4E5066;\n  color:#FFFFFF;\n  border-top: 1px solid #22262e;\n  border-bottom: 1px solid #22262e;\n}\n \ntr:first-child {\n  border-top:none;\n}\n\ntr:last-child {\n  border-bottom:none;\n}\n \ntr:nth-child(odd) td {\n  background:#EBEBEB;\n}\n \ntr:nth-child(odd):hover td {\n  background:#4E5066;\n}\n\ntr:last-child td:first-child {\n  border-bottom-left-radius:3px;\n}\n \ntr:last-child td:last-child {\n  border-bottom-right-radius:3px;\n}\n \ntd {\n  background:#FFFFFF;\n padding:10;  vertical-align:middle;\n  font-weight:300;\n  font-size:11px;\n  text-shadow: -1px -1px 1px rgba(0, 0, 0, 0.1);\n  border-bottom: 1px solid #C1C3D1;\n\nth.text-left {\n  text-align: left;\n}\n\nth.text-center {\n  text-align: center;\n}\n\nth.text-right {\n  text-align: right;\n}\n\ntd.text-left {\n  text-align: left;\n}\n\ntd.text-center {\n  text-align: center;\n}\n\ntd.text-right {  text-align: right;}</style></head><body>";

                String sttable = "<div class=\"table-title\">Transaction No Change List  </div><table class=\"table-fill\"><thead>";

                String thead
                        = "<tr><th class=\"text-left\">Status</th>"
                        + "<th class=\"text-left\">Branch ID</th>"
                        + "<th class=\"text-left\">Code</th>"
                        + "<th class=\"text-left\">Date</th>"
                        + "<th class=\"text-left\">Till</th>"
                        + "<th class=\"text-left\">User</th>"
                        + "<th class=\"text-left\">Total</th>"
                        + "<th class=\"text-left\">Quantity</th>"
                        + "<th class=\"text-left\">Price</th>"
                        + "<th class=\"text-left\">Fee</th>"
                        + "<th class=\"text-left\">Category</th>"
                        + "<th class=\"text-left\">Causal</th>"
                        + "<th class=\"text-left\">Client</th>"
                        + "<th class=\"text-left\">Type</th>"
                        + "<th class=\"text-left\">Pos/Bank Account</th>"
                        + "<th class=\"text-left\">Loyalty Code</th>"
                        + "</tr></thead><tbody>";

                String content = "";

                for (int i = 0; i < result.size(); i++) {

                    NC_transaction res = (NC_transaction) result.get(i);

                    String q1 = formatMysqltoDisplay(roundDoubleandFormat(fd(res.getQuantita()), 0));
                    String p1 = formatMysqltoDisplay(res.getPrezzo());
                    String f1 = formatMysqltoDisplay("0.00");
                    String pos = res.getPos();
                    switch (res.getFg_tipo_transazione_nc()) {
                        case "1":
                            q1 = "1";
                            p1 = formatMysqltoDisplay(res.getNetto());
                            f1 = formatMysqltoDisplay(res.getCommissione());
                            break;
                        case "3":
                            q1 = formatMysqltoDisplay(roundDoubleandFormat(fd(res.getRicevuta()), 0));
                            p1 = formatMysqltoDisplay(res.getQuantita());
                            break;
                        case "21":
                            String comm;
                            if (fd(res.getCommissione()) > 0) {
                                comm = res.getCommissione();
                            } else {
                                comm = res.getTi_ticket_fee();
                            }
                            f1 = formatMysqltoDisplay(comm);
                            break;
                        default:
                            break;
                    }

                    String loy;
                    if (iscentral) {
                        loy = query_LOY_transaction(res.getCod(), null, "000");
                    } else {
                        loy = query_LOY_transaction(res.getCod(), "central", "111");
                    }
                    if (loy == null) {
                        loy = "";
                    }

                    content = content + "<tr>\n"
                            + "<td class=\"text-left\">" + res.formatStatus(res.getDel_fg())
                            + "</td>\n" + "<td class=\"text-left\">" + res.getFiliale()
                            + "</td>\n" + "<td class=\"text-left\">" + res.getId()
                            + "</td>\n" + "<td class=\"text-left\">" + formatStringtoStringDate(res.getData(), patternsqldate, patternnormdate)
                            + "</td>\n" + "<td class=\"text-left\">" + res.getTill()
                            + "</td>\n" + "<td class=\"text-left\">" + res.getUser()
                            + "</td>\n" + "<td class=\"text-left\">" + formatMysqltoDisplay(res.getTotal())
                            + "</td>\n" + "<td class=\"text-left\">" + q1
                            + "</td>\n" + "<td class=\"text-left\">" + p1
                            + "</td>\n" + "<td class=\"text-left\">" + f1
                            + "</td>\n" + "<td class=\"text-left\">" + res.getGruppo_nc() + " - " + formatALNC_category(res.getGruppo_nc(), array_nc_cat)
                            + "</td>\n" + "<td class=\"text-left\">" + res.getCausale_nc() + " - " + formatALNC_causal(res.getCausale_nc(), array_nc_caus)
                            + "</td>\n" + "<td class=\"text-left\">" + res.getCl_cognome() + " " + res.getCl_nome()
                            + "</td>\n" + "<td class=\"text-left\">" + formatALNC_causal_ncde(res.getCausale_nc(), array_nc_caus, array_nc_descr)
                            + "</td>\n" + "<td class=\"text-left\">" + pos
                            + "</td>\n" + "<td class=\"text-left\">" + loy
                            + "</td>\n" + "</tr>\n";
                }

                String endtable = "</tbody></table>";
                String endbody = "</body></html>";

                String html = header + sttable + thead + content + endtable + endbody;
                InputStream is = new ByteArrayInputStream(html.getBytes());
                getInstance().parseXHtml(wr, document, is);

                document.close();
                wr.close();
                ou.close();
                is.close();

                String base64 = new String(encodeBase64(readFileToByteArray(outputfile)));
                outputfile.delete();
                return base64;
            } catch (IOException | DocumentException ex) {
                insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
            }
        }
        return null;
    }

    /**
     *
     * @param outputfile
     * @param result
     * @return
     */
    public static String pdf_kycList(File outputfile, ArrayList<Ch_transaction_doc> result) {
        if (result.size() > 0) {
            try (OutputStream ou = new FileOutputStream(outputfile)) {
                Document document = new Document(A4.rotate(), 20.0F, 20.0F, 20.0F, 20.0F);
                PdfWriter wr = getInstance(document, ou);
                wr.open();
                document.open();
                String header = "<html><head><title>Mac 2.0</title><style type=\"text/css\">@import url(http://fonts.googleapis.com/css?family=Roboto:400,500,700,300,100);body {font-family: \"Roboto\", helvetica, arial, sans-serif;font-size: 10px;font-weight: 400;text-rendering: optimizeLegibility;}div.table-title {\tfont-family: \"Roboto\", helvetica, arial, sans-serif;\n  text-align:center;\n  margin: auto;\n  max-width: 600px;\n  padding:5px;\n  width: 90%;\n  font-size: 20px;}.table-title h3 {color: #fafafa;font-size: 30px;font-weight: 400;font-style:normal;font-family: \"Roboto\", helvetica, arial, sans-serif;text-shadow: -1px -1px 1px rgba(0, 0, 0, 0.1);text-transform:uppercase;}.table-fill {background: white;border-radius:3px;border-collapse: collapse;height: 320px;margin: auto;max-width: 600px;padding:5px;width: 100%;box-shadow: 0 5px 10px rgba(0, 0, 0, 0.1);animation: float 5s infinite;\n}\n \nth {\n  color:#000000;;\n  background:#91949A;\n  font-size:12px;\n  font-weight: 100;\n  padding:10px;\n  text-align:left;\n  text-shadow: 0 1px 1px rgba(0, 0, 0, 0.1);\n  vertical-align:middle;\n}\n\nth:first-child {\n  border-top-left-radius:3px;\n}\ntr {\n  border-top: 1px solid #C1C3D1;\n  border-bottom-: 1px solid #C1C3D1;\n  color:#666B85;\n  font-size:11px;\n  font-weight:normal;\n  text-shadow: 0 1px 1px rgba(256, 256, 256, 0.1);\n}\n \ntr:hover td {\n  background:#4E5066;\n  color:#FFFFFF;\n  border-top: 1px solid #22262e;\n  border-bottom: 1px solid #22262e;\n}\n \ntr:first-child {\n  border-top:none;\n}\n\ntr:last-child {\n  border-bottom:none;\n}\n \ntr:nth-child(odd) td {\n  background:#EBEBEB;\n}\n \ntr:nth-child(odd):hover td {\n  background:#4E5066;\n}\n\ntr:last-child td:first-child {\n  border-bottom-left-radius:3px;\n}\n \ntr:last-child td:last-child {\n  border-bottom-right-radius:3px;\n}\n \ntd {\n  background:#FFFFFF;\n padding:10;  vertical-align:middle;\n  font-weight:300;\n  font-size:11px;\n  text-shadow: -1px -1px 1px rgba(0, 0, 0, 0.1);\n  border-bottom: 1px solid #C1C3D1;\n\nth.text-left {\n  text-align: left;\n}\n\nth.text-center {\n  text-align: center;\n}\n\nth.text-right {\n  text-align: right;\n}\n\ntd.text-left {\n  text-align: left;\n}\n\ntd.text-center {\n  text-align: center;\n}\n\ntd.text-right {  text-align: right;}</style></head><body>";

                String sttable = "<div class=\"table-title\"> K.Y.C. List  </div><table class=\"table-fill\"><thead>";

                String thead = "<tr><th class=\"text-left\">Branch ID</th>"
                        + "<th class=\"text-left\">Client Surname</th>"
                        + "<th class=\"text-left\">Client Name</th>"
                        + "<th class=\"text-left\">Motivation</th>"
                        + "<th class=\"text-left\">Document Date</th>"
                        + "</tr></thead><tbody>";

                String content = "";

                for (int i = 0; i < result.size(); i++) {
                    Ch_transaction_doc res = (Ch_transaction_doc) result.get(i);
                    Db_Master db1 = new Db_Master();
                    Ch_transaction tr = db1.query_transaction_ch(res.getCodtr());
                    Client cl = db1.query_Client_transaction(res.getCodtr(), res.getClient());
                    db1.closeDB();

                    String type;

                    if (cl.getPep().equalsIgnoreCase("YES")) {
                        type = "PEP";
                    } else {
                        type = "THRESHOLD";
                    }

                    content = content + "<tr>\n<td class=\"text-left\">" + tr.getFiliale()
                            + "</td>\n" + "<td class=\"text-left\">" + cl.getCognome().toUpperCase()
                            + "</td>\n" + "<td class=\"text-left\">" + cl.getNome().toUpperCase()
                            + "</td>\n" + "<td class=\"text-left\">" + type
                            + "</td>\n" + "<td class=\"text-left\">" + formatStringtoStringDate_null(res.getData_load(), patternsqldate, patternnormdate)
                            + "</td>\n" + "</tr>\n";
                }

                String endtable = "</tbody></table>";
                String endbody = "</body></html>";

                String html = header + sttable + thead + content + endtable + endbody;
                InputStream is = new ByteArrayInputStream(html.getBytes());
                getInstance().parseXHtml(wr, document, is);

                document.close();
                wr.close();
                ou.close();
                is.close();

                String base64 = new String(encodeBase64(readFileToByteArray(outputfile)));
                outputfile.delete();
                return base64;
            } catch (IOException | DocumentException ex) {
                insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
            }
        }
        return null;
    }

    /**
     *
     * @param outputfile
     * @param result
     * @return
     */
    public static String pdf_it_List(File outputfile, ArrayList<IT_change> result) {
        if (result.size() > 0) {
            try (OutputStream ou = new FileOutputStream(outputfile)) {

                Document document = new Document(A4.rotate(), 20.0F, 20.0F, 20.0F, 20.0F);
                PdfWriter wr = getInstance(document, ou);
                wr.open();
                document.open();
                String header = "<html><head><title>Mac 2.0</title><style type=\"text/css\">@import url(http://fonts.googleapis.com/css?family=Roboto:400,500,700,300,100);body {font-family: \"Roboto\", helvetica, arial, sans-serif;font-size: 10px;font-weight: 400;text-rendering: optimizeLegibility;}div.table-title {\tfont-family: \"Roboto\", helvetica, arial, sans-serif;\n  text-align:center;\n  margin: auto;\n  max-width: 600px;\n  padding:5px;\n  width: 90%;\n  font-size: 20px;}.table-title h3 {color: #fafafa;font-size: 30px;font-weight: 400;font-style:normal;font-family: \"Roboto\", helvetica, arial, sans-serif;text-shadow: -1px -1px 1px rgba(0, 0, 0, 0.1);text-transform:uppercase;}.table-fill {background: white;border-radius:3px;border-collapse: collapse;height: 320px;margin: auto;max-width: 600px;padding:5px;width: 100%;box-shadow: 0 5px 10px rgba(0, 0, 0, 0.1);animation: float 5s infinite;\n}\n \nth {\n  color:#000000;;\n  background:#91949A;\n  font-size:12px;\n  font-weight: 100;\n  padding:10px;\n  text-align:left;\n  text-shadow: 0 1px 1px rgba(0, 0, 0, 0.1);\n  vertical-align:middle;\n}\n\nth:first-child {\n  border-top-left-radius:3px;\n}\ntr {\n  border-top: 1px solid #C1C3D1;\n  border-bottom-: 1px solid #C1C3D1;\n  color:#666B85;\n  font-size:11px;\n  font-weight:normal;\n  text-shadow: 0 1px 1px rgba(256, 256, 256, 0.1);\n}\n \ntr:hover td {\n  background:#4E5066;\n  color:#FFFFFF;\n  border-top: 1px solid #22262e;\n  border-bottom: 1px solid #22262e;\n}\n \ntr:first-child {\n  border-top:none;\n}\n\ntr:last-child {\n  border-bottom:none;\n}\n \ntr:nth-child(odd) td {\n  background:#EBEBEB;\n}\n \ntr:nth-child(odd):hover td {\n  background:#4E5066;\n}\n\ntr:last-child td:first-child {\n  border-bottom-left-radius:3px;\n}\n \ntr:last-child td:last-child {\n  border-bottom-right-radius:3px;\n}\n \ntd {\n  background:#FFFFFF;\n padding:10;  vertical-align:middle;\n  font-weight:300;\n  font-size:11px;\n  text-shadow: -1px -1px 1px rgba(0, 0, 0, 0.1);\n  border-bottom: 1px solid #C1C3D1;\n\nth.text-left {\n  text-align: left;\n}\n\nth.text-center {\n  text-align: center;\n}\n\nth.text-right {\n  text-align: right;\n}\n\ntd.text-left {\n  text-align: left;\n}\n\ntd.text-center {\n  text-align: center;\n}\n\ntd.text-right {  text-align: right;}</style></head><body>";

                String sttable = "<div class=\"table-title\">Internal Transfer List </div><table class=\"table-fill\"><thead>";

                String thead = "<tr><th class=\"text-left\">Branch ID</th>"
                        + "<th class=\"text-left\">Code</th>"
                        + "<th class=\"text-left\">Date</th>"
                        + "<th class=\"text-left\">Operator</th>"
                        + "<th class=\"text-left\">From</th>"
                        + "<th class=\"text-left\">Id open till - from</th>"
                        + "<th class=\"text-left\">To</th>"
                        + "<th class=\"text-left\">Id open till - to</th>"
                        + "<th class=\"text-left\">Date Deleted</th>"
                        + "<th class=\"text-left\">Status</th>"
                        + "</tr></thead><tbody>";

                String content = "";

                for (int i = 0; i < result.size(); i++) {
                    IT_change res = (IT_change) result.get(i);

                    String dt_del = "";

                    if (res.getFg_annullato().equals("1")) {
                        dt_del = formatStringtoStringDate(res.getDel_dt(), patternsqldate, patternnormdate);

                    }

                    Db_Master db2 = new Db_Master();
                    String fr = "-";
                    String to = "-";
                    Openclose oc_fr = db2.query_oc(res.getIdopen_from());
                    if (oc_fr != null) {
                        fr = oc_fr.getId();
                    }
                    Openclose oc_to = db2.query_oc(res.getIdopen_to());
                    if (oc_to != null) {
                        to = oc_to.getId();
                    }
                    db2.closeDB();

                    content = content + "<tr>\n<td class=\"text-left\">" + res.getFiliale()
                            + "</td>\n" + "<td class=\"text-left\">" + res.getId()
                            + "</td>\n" + "<td class=\"text-left\">" + formatStringtoStringDate(res.getDt_it(), patternsqldate, patternnormdate)
                            + "</td>\n" + "<td class=\"text-left\">" + res.getUser()
                            + "</td>\n" + "<td class=\"text-left\">" + res.getTill_from()
                            + "</td>\n" + "<td class=\"text-left\">" + fr
                            + "</td>\n" + "<td class=\"text-left\">" + res.getTill_to()
                            + "</td>\n" + "<td class=\"text-left\">" + to
                            + "</td>\n" + "<td class=\"text-left\">" + dt_del
                            + "</td>\n" + "<td class=\"text-left\">" + res.formatStatus(res.getFg_annullato())
                            + "</td>\n" + "</tr>\n";
                }

                String endtable = "</tbody></table>";
                String endbody = "</body></html>";

                String html = header + sttable + thead + content + endtable + endbody;
                InputStream is = new ByteArrayInputStream(html.getBytes());
                getInstance().parseXHtml(wr, document, is);

                document.close();
                wr.close();
                ou.close();
                is.close();

                String base64 = new String(encodeBase64(readFileToByteArray(outputfile)));
                outputfile.delete();
                return base64;
            } catch (IOException | DocumentException ex) {
                insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
            }
        }
        return null;
    }

    /**
     *
     * @param outputfile
     * @param result
     * @param typeop
     * @param nc_cat
     * @return
     */
    public static String pdf_et_List(File outputfile, ArrayList<ET_change> result, String typeop, ArrayList<NC_category> nc_cat) {
        if (result.size() > 0) {
            try (OutputStream ou = new FileOutputStream(outputfile)) {

                Document document = new Document(A4.rotate(), 20.0F, 20.0F, 20.0F, 20.0F);
                PdfWriter wr = getInstance(document, ou);
                wr.open();
                document.open();
                String header = "<html><head><title>Mac 2.0</title><style type=\"text/css\">@import url(http://fonts.googleapis.com/css?family=Roboto:400,500,700,300,100);body {font-family: \"Roboto\", helvetica, arial, sans-serif;font-size: 10px;font-weight: 400;text-rendering: optimizeLegibility;}div.table-title {\tfont-family: \"Roboto\", helvetica, arial, sans-serif;\n  text-align:center;\n  margin: auto;\n  max-width: 600px;\n  padding:5px;\n  width: 90%;\n  font-size: 20px;}.table-title h3 {color: #fafafa;font-size: 30px;font-weight: 400;font-style:normal;font-family: \"Roboto\", helvetica, arial, sans-serif;text-shadow: -1px -1px 1px rgba(0, 0, 0, 0.1);text-transform:uppercase;}.table-fill {background: white;border-radius:3px;border-collapse: collapse;height: 320px;margin: auto;max-width: 600px;padding:5px;width: 100%;box-shadow: 0 5px 10px rgba(0, 0, 0, 0.1);animation: float 5s infinite;\n}\n \nth {\n  color:#000000;;\n  background:#91949A;\n  font-size:12px;\n  font-weight: 100;\n  padding:10px;\n  text-align:left;\n  text-shadow: 0 1px 1px rgba(0, 0, 0, 0.1);\n  vertical-align:middle;\n}\n\nth:first-child {\n  border-top-left-radius:3px;\n}\ntr {\n  border-top: 1px solid #C1C3D1;\n  border-bottom-: 1px solid #C1C3D1;\n  color:#666B85;\n  font-size:11px;\n  font-weight:normal;\n  text-shadow: 0 1px 1px rgba(256, 256, 256, 0.1);\n}\n \ntr:hover td {\n  background:#4E5066;\n  color:#FFFFFF;\n  border-top: 1px solid #22262e;\n  border-bottom: 1px solid #22262e;\n}\n \ntr:first-child {\n  border-top:none;\n}\n\ntr:last-child {\n  border-bottom:none;\n}\n \ntr:nth-child(odd) td {\n  background:#EBEBEB;\n}\n \ntr:nth-child(odd):hover td {\n  background:#4E5066;\n}\n\ntr:last-child td:first-child {\n  border-bottom-left-radius:3px;\n}\n \ntr:last-child td:last-child {\n  border-bottom-right-radius:3px;\n}\n \ntd {\n  background:#FFFFFF;\n padding:10;  vertical-align:middle;\n  font-weight:300;\n  font-size:11px;\n  text-shadow: -1px -1px 1px rgba(0, 0, 0, 0.1);\n  border-bottom: 1px solid #C1C3D1;\n\nth.text-left {\n  text-align: left;\n}\n\nth.text-center {\n  text-align: center;\n}\n\nth.text-right {\n  text-align: right;\n}\n\ntd.text-left {\n  text-align: left;\n}\n\ntd.text-center {\n  text-align: center;\n}\n\ntd.text-right {  text-align: right;}</style></head><body>";

                String sttable = "<div class=\"table-title\">External Transfer List </div><table class=\"table-fill\"><thead>";

                String thead = "<tr><th class=\"text-left\">Branch ID</th>"
                        + "<th class=\"text-left\">Code</th>"
                        + "<th class=\"text-left\">Date</th>"
                        + "<th class=\"text-left\">Operator</th>"
                        + "<th class=\"text-left\">Type</th>"
                        + "<th class=\"text-left\">Other Branch/Bank/POS ID</th>"
                        + "<th class=\"text-left\">Total</th>"
                        + "<th class=\"text-left\">Date Deleted</th>"
                        + "<th class=\"text-left\">Status</th>"
                        + "</tr></thead><tbody>";

                String content = "";

                for (int i = 0; i < result.size(); i++) {
                    ET_change res = (ET_change) result.get(i);

                    String dt_del = "";

                    if (res.getFg_annullato().equals("1")) {
                        dt_del = formatStringtoStringDate(res.getDel_dt(), patternsqldate, patternnormdate);

                    }

                    String total;
                    if (typeop.equals("CH")) {
                        double t1 = 0.00;
                        ArrayList<ET_change> val = get_ET_change_value(res.getCod());
                        for (int x = 0; x < val.size(); x++) {
                            t1 += fd(val.get(x).getIp_total());
                        }
                        total = formatMysqltoDisplay(roundDoubleandFormat(t1, 2));
                    } else {
                        double t1 = 0.00;
                        ArrayList<ET_change> val = get_ET_nochange_value(res.getCod());
                        for (int x = 0; x < val.size(); x++) {
                            NC_category nc1 = getNC_category(nc_cat, val.get(x).getNc_causal());
                            double t2 = fd(val.get(x).getIp_quantity()) * fd(nc1.getIp_prezzo_nc());
                            t1 += t2;
                        }
                        total = formatMysqltoDisplay(roundDoubleandFormat(t1, 2));
                    }

                    content = content + "<tr>\n<td class=\"text-left\">" + res.getFiliale()
                            + "</td>\n" + "<td class=\"text-left\">" + res.getId()
                            + "</td>\n" + "<td class=\"text-left\">" + formatStringtoStringDate(res.getDt_it(), patternsqldate, patternnormdate)
                            + "</td>\n" + "<td class=\"text-left\">" + res.getUser()
                            + "</td>\n" + "<td class=\"text-left\">" + res.format_tofrom_brba(res.getFg_tofrom(), res.getFg_brba())
                            + "</td>\n" + "<td class=\"text-left\">" + res.getCod_dest()
                            + "</td>\n" + "<td class=\"text-left\">" + total
                            + "</td>\n" + "<td class=\"text-left\">" + dt_del
                            + "</td>\n" + "<td class=\"text-left\">" + res.formatStatus(res.getFg_annullato())
                            + "</td>\n" + "</tr>\n";
                }

                String endtable = "</tbody></table>";
                String endbody = "</body></html>";

                String html = header + sttable + thead + content + endtable + endbody;
                InputStream is = new ByteArrayInputStream(html.getBytes());
                getInstance().parseXHtml(wr, document, is);

                document.close();
                wr.close();
                ou.close();
                is.close();

                String base64 = new String(encodeBase64(readFileToByteArray(outputfile)));
                outputfile.delete();
                return base64;
            } catch (IOException | DocumentException ex) {
                insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
            }
        }
        return null;
    }

    /**
     *
     * @param outputfile
     * @param result
     * @param br
     * @param cu
     * @return
     */
    public static String pdf_webtrans_List(File outputfile, ArrayList<Booking> result, ArrayList<Branch> br, ArrayList<Currency> cu) {
        if (result.size() > 0) {
            try (OutputStream ou = new FileOutputStream(outputfile)) {

                Document document = new Document(A4.rotate(), 20.0F, 20.0F, 20.0F, 20.0F);
                PdfWriter wr = getInstance(document, ou);
                wr.open();
                document.open();
                String header = "<html><head><title>Mac 2.0</title><style type=\"text/css\">@import url(http://fonts.googleapis.com/css?family=Roboto:400,500,700,300,100);body {font-family: \"Roboto\", helvetica, arial, sans-serif;font-size: 10px;font-weight: 400;text-rendering: optimizeLegibility;}div.table-title {\tfont-family: \"Roboto\", helvetica, arial, sans-serif;\n  text-align:center;\n  margin: auto;\n  max-width: 600px;\n  padding:5px;\n  width: 90%;\n  font-size: 20px;}.table-title h3 {color: #fafafa;font-size: 30px;font-weight: 400;font-style:normal;font-family: \"Roboto\", helvetica, arial, sans-serif;text-shadow: -1px -1px 1px rgba(0, 0, 0, 0.1);text-transform:uppercase;}.table-fill {background: white;border-radius:3px;border-collapse: collapse;height: 320px;margin: auto;max-width: 600px;padding:5px;width: 100%;box-shadow: 0 5px 10px rgba(0, 0, 0, 0.1);animation: float 5s infinite;\n}\n \nth {\n  color:#000000;;\n  background:#91949A;\n  font-size:12px;\n  font-weight: 100;\n  padding:10px;\n  text-align:left;\n  text-shadow: 0 1px 1px rgba(0, 0, 0, 0.1);\n  vertical-align:middle;\n}\n\nth:first-child {\n  border-top-left-radius:3px;\n}\ntr {\n  border-top: 1px solid #C1C3D1;\n  border-bottom-: 1px solid #C1C3D1;\n  color:#666B85;\n  font-size:11px;\n  font-weight:normal;\n  text-shadow: 0 1px 1px rgba(256, 256, 256, 0.1);\n}\n \ntr:hover td {\n  background:#4E5066;\n  color:#FFFFFF;\n  border-top: 1px solid #22262e;\n  border-bottom: 1px solid #22262e;\n}\n \ntr:first-child {\n  border-top:none;\n}\n\ntr:last-child {\n  border-bottom:none;\n}\n \ntr:nth-child(odd) td {\n  background:#EBEBEB;\n}\n \ntr:nth-child(odd):hover td {\n  background:#4E5066;\n}\n\ntr:last-child td:first-child {\n  border-bottom-left-radius:3px;\n}\n \ntr:last-child td:last-child {\n  border-bottom-right-radius:3px;\n}\n \ntd {\n  background:#FFFFFF;\n padding:10;  vertical-align:middle;\n  font-weight:300;\n  font-size:11px;\n  text-shadow: -1px -1px 1px rgba(0, 0, 0, 0.1);\n  border-bottom: 1px solid #C1C3D1;\n\nth.text-left {\n  text-align: left;\n}\n\nth.text-center {\n  text-align: center;\n}\n\nth.text-right {\n  text-align: right;\n}\n\ntd.text-left {\n  text-align: left;\n}\n\ntd.text-center {\n  text-align: center;\n}\n\ntd.text-right {  text-align: right;}</style></head><body>";

                String sttable = "<div class=\"table-title\">Website Transaction List </div><table class=\"table-fill\"><thead>";

                String thead = "<tr><th class=\"text-left\">Alert</th>"
                        + "<th class=\"text-left\">Branch</th>"
                        + "<th class=\"text-left\">Currency</th>"
                        + "<th class=\"text-left\">Total</th>"
                        + "<th class=\"text-left\">Date</th>"
                        + "<th class=\"text-left\">Surname</th>"
                        + "<th class=\"text-left\">Name</th>"
                        + "<th class=\"text-left\">Status</th>"
                        + "</tr></thead><tbody>";

                String content = "";

                for (int i = 0; i < result.size(); i++) {
                    Booking res = (Booking) result.get(i);

                    String alert = "";
                    if (!res.getStato().equals("1")) {

                    } else {
                        alert = res.formatAlert(isAvaliable(res), isToday(res.getDt_ritiro()), isTomorrow(res.getDt_ritiro()), isPast(res.getDt_ritiro()));
                    }

                    content = content + "<tr>\n<td class=\"text-left\">" + alert
                            + "</td>\n" + "<td class=\"text-left\">" + formatBankBranch(res.getFiliale(), "BR", null, br, null)
                            + "</td>\n" + "<td class=\"text-left\">" + res.getCurrency() + " - " + formatALCurrency(res.getCurrency(), cu)
                            + "</td>\n" + "<td class=\"text-left\">" + formatMysqltoDisplay(res.getTotal())
                            + "</td>\n" + "<td class=\"text-left\">" + formatStringtoStringDate(res.getDt_ritiro(), patternsql, patternnormdate_filter)
                            + "</td>\n" + "<td class=\"text-left\">" + res.getCl_cognome()
                            + "</td>\n" + "<td class=\"text-left\">" + res.getCl_nome()
                            + "</td>\n" + "<td class=\"text-left\">" + res.formatStatus(res.getStato())
                            + "</td>\n" + "</tr>\n";
                }

                String endtable = "</tbody></table>";
                String endbody = "</body></html>";

                String html = header + sttable + thead + content + endtable + endbody;
                InputStream is = new ByteArrayInputStream(html.getBytes());
                getInstance().parseXHtml(wr, document, is);

                document.close();
                wr.close();
                ou.close();
                is.close();

                String base64 = new String(encodeBase64(readFileToByteArray(outputfile)));
                outputfile.delete();
                return base64;
            } catch (IOException | DocumentException ex) {
                insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
            }
        }
        return null;
    }

    /**
     *
     * @param outputfile
     * @param result
     * @return
     */
    public static String pdf_nl_view(File outputfile, ArrayList<Newsletters> result) {
        if (result.size() > 0) {
            try (OutputStream ou = new FileOutputStream(outputfile)) {
                Document document = new Document(A4.rotate(), 20.0F, 20.0F, 20.0F, 20.0F);
                PdfWriter wr = getInstance(document, ou);
                wr.open();
                document.open();
                String header = "<html><head><title>Mac 2.0</title><style type=\"text/css\">@import url(http://fonts.googleapis.com/css?family=Roboto:400,500,700,300,100);body {font-family: \"Roboto\", helvetica, arial, sans-serif;font-size: 10px;font-weight: 400;text-rendering: optimizeLegibility;}div.table-title {\tfont-family: \"Roboto\", helvetica, arial, sans-serif;\n  text-align:center;\n  margin: auto;\n  max-width: 600px;\n  padding:5px;\n  width: 90%;\n  font-size: 20px;}.table-title h3 {color: #fafafa;font-size: 30px;font-weight: 400;font-style:normal;font-family: \"Roboto\", helvetica, arial, sans-serif;text-shadow: -1px -1px 1px rgba(0, 0, 0, 0.1);text-transform:uppercase;}.table-fill {background: white;border-radius:3px;border-collapse: collapse;height: 320px;margin: auto;max-width: 600px;padding:5px;width: 100%;box-shadow: 0 5px 10px rgba(0, 0, 0, 0.1);animation: float 5s infinite;\n}\n \nth {\n  color:#000000;;\n  background:#91949A;\n  font-size:12px;\n  font-weight: 100;\n  padding:10px;\n  text-align:left;\n  text-shadow: 0 1px 1px rgba(0, 0, 0, 0.1);\n  vertical-align:middle;\n}\n\nth:first-child {\n  border-top-left-radius:3px;\n}\ntr {\n  border-top: 1px solid #C1C3D1;\n  border-bottom-: 1px solid #C1C3D1;\n  color:#666B85;\n  font-size:11px;\n  font-weight:normal;\n  text-shadow: 0 1px 1px rgba(256, 256, 256, 0.1);\n}\n \ntr:hover td {\n  background:#4E5066;\n  color:#FFFFFF;\n  border-top: 1px solid #22262e;\n  border-bottom: 1px solid #22262e;\n}\n \ntr:first-child {\n  border-top:none;\n}\n\ntr:last-child {\n  border-bottom:none;\n}\n \ntr:nth-child(odd) td {\n  background:#EBEBEB;\n}\n \ntr:nth-child(odd):hover td {\n  background:#4E5066;\n}\n\ntr:last-child td:first-child {\n  border-bottom-left-radius:3px;\n}\n \ntr:last-child td:last-child {\n  border-bottom-right-radius:3px;\n}\n \ntd {\n  background:#FFFFFF;\n padding:10;  vertical-align:middle;\n  font-weight:300;\n  font-size:11px;\n  text-shadow: -1px -1px 1px rgba(0, 0, 0, 0.1);\n  border-bottom: 1px solid #C1C3D1;\n\nth.text-left {\n  text-align: left;\n}\n\nth.text-center {\n  text-align: center;\n}\n\nth.text-right {\n  text-align: right;\n}\n\ntd.text-left {\n  text-align: left;\n}\n\ntd.text-center {\n  text-align: center;\n}\n\ntd.text-right {  text-align: right;}</style></head><body>";

                String sttable = "<div class=\"table-title\">Newsletter List </div><table class=\"table-fill\"><thead>";

                String thead = "<tr><th class=\"text-left\">Title</th>"
                        + "<th class=\"text-left\">Description</th>"
                        + "<th class=\"text-left\">Date</th>"
                        + "<th class=\"text-left\">Status</th>"
                        + "</tr></thead><tbody>";

                String content = "";

                for (int i = 0; i < result.size(); i++) {
                    Newsletters res = (Newsletters) result.get(i);

                    String st = res.formatStatus(res.getStatus());
                    if (res.getStatus().equals("R")) {
                        st = st + " <span class='help-block'>" + formatStringtoStringDate(res.getDt_read(), "yyyy-MM-dd HH:mm:ss", "dd/MM/yyyy HH:mm:ss") + "</span>";
                    }

                    content = content + "<tr>\n<td class=\"text-left\">" + correggi(res.getTitolo())
                            + "</td>\n" + "<td class=\"text-left\">" + correggi(res.getDescr())
                            + "</td>\n" + "<td class=\"text-left\">" + formatStringtoStringDate(res.getDt_upload(), "yyyy-MM-dd HH:mm:ss", "dd/MM/yyyy HH:mm:ss")
                            + "</td>\n" + "<td class=\"text-left\">" + st
                            + "</td>\n" + "</tr>\n";
                }

                String endtable = "</tbody></table>";
                String endbody = "</body></html>";

                String html = header + sttable + thead + content + endtable + endbody;
                InputStream is = new ByteArrayInputStream(html.getBytes());
                getInstance().parseXHtml(wr, document, is);

                document.close();
                wr.close();
                ou.close();
                is.close();

                String base64 = new String(encodeBase64(readFileToByteArray(outputfile)));
                outputfile.delete();
                return base64;
            } catch (IOException | DocumentException ex) {
                insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
            }
        }
        return null;
    }

    /**
     *
     * @param outputfile
     * @param result
     * @param listTill
     * @return
     */
    public static String pdf_oc_list(File outputfile, ArrayList<Openclose> result, ArrayList<Till> listTill) {
        if (result.size() > 0) {

            try (OutputStream ou = new FileOutputStream(outputfile)) {
                Document document = new Document(A4.rotate(), 20.0F, 20.0F, 20.0F, 20.0F);
                PdfWriter wr = getInstance(document, ou);
                wr.open();
                document.open();
                String header = "<html><head><title>Mac 2.0</title><style type=\"text/css\">@import url(http://fonts.googleapis.com/css?family=Roboto:400,500,700,300,100);body {font-family: \"Roboto\", helvetica, arial, sans-serif;font-size: 10px;font-weight: 400;text-rendering: optimizeLegibility;}div.table-title {\tfont-family: \"Roboto\", helvetica, arial, sans-serif;\n  text-align:center;\n  margin: auto;\n  max-width: 600px;\n  padding:5px;\n  width: 90%;\n  font-size: 20px;}.table-title h3 {color: #fafafa;font-size: 30px;font-weight: 400;font-style:normal;font-family: \"Roboto\", helvetica, arial, sans-serif;text-shadow: -1px -1px 1px rgba(0, 0, 0, 0.1);text-transform:uppercase;}.table-fill {background: white;border-radius:3px;border-collapse: collapse;height: 320px;margin: auto;max-width: 600px;padding:5px;width: 100%;box-shadow: 0 5px 10px rgba(0, 0, 0, 0.1);animation: float 5s infinite;\n}\n \nth {\n  color:#000000;;\n  background:#91949A;\n  font-size:12px;\n  font-weight: 100;\n  padding:10px;\n  text-align:left;\n  text-shadow: 0 1px 1px rgba(0, 0, 0, 0.1);\n  vertical-align:middle;\n}\n\nth:first-child {\n  border-top-left-radius:3px;\n}\ntr {\n  border-top: 1px solid #C1C3D1;\n  border-bottom-: 1px solid #C1C3D1;\n  color:#666B85;\n  font-size:11px;\n  font-weight:normal;\n  text-shadow: 0 1px 1px rgba(256, 256, 256, 0.1);\n}\n \ntr:hover td {\n  background:#4E5066;\n  color:#FFFFFF;\n  border-top: 1px solid #22262e;\n  border-bottom: 1px solid #22262e;\n}\n \ntr:first-child {\n  border-top:none;\n}\n\ntr:last-child {\n  border-bottom:none;\n}\n \ntr:nth-child(odd) td {\n  background:#EBEBEB;\n}\n \ntr:nth-child(odd):hover td {\n  background:#4E5066;\n}\n\ntr:last-child td:first-child {\n  border-bottom-left-radius:3px;\n}\n \ntr:last-child td:last-child {\n  border-bottom-right-radius:3px;\n}\n \ntd {\n  background:#FFFFFF;\n padding:10;  vertical-align:middle;\n  font-weight:300;\n  font-size:11px;\n  text-shadow: -1px -1px 1px rgba(0, 0, 0, 0.1);\n  border-bottom: 1px solid #C1C3D1;\n\nth.text-left {\n  text-align: left;\n}\n\nth.text-center {\n  text-align: center;\n}\n\nth.text-right {\n  text-align: right;\n}\n\ntd.text-left {\n  text-align: left;\n}\n\ntd.text-center {\n  text-align: center;\n}\n\ntd.text-right {  text-align: right;}</style></head><body>";

                String sttable = "<div class=\"table-title\">Open/Close List  </div><table class=\"table-fill\"><thead>";

                String thead = "<tr><th class=\"text-left\">Branch ID</th>"
                        + "<th class=\"text-left\">Code</th>"
                        + "<th class=\"text-left\">Date</th>"
                        + "<th class=\"text-left\">Operator</th>"
                        + "<th class=\"text-left\">Till/Close</th>"
                        + "<th class=\"text-left\">Type</th>"
                        + "<th class=\"text-left\">Errors</th>"
                        + "</tr></thead><tbody>";

                String content = "";

                for (int i = 0; i < result.size(); i++) {
                    Openclose res = (Openclose) result.get(i);

                    content = content + "<tr>\n<td class=\"text-left\">" + res.getFiliale()
                            + "</td>\n<td class=\"text-left\">" + res.getId()
                            + "</td>\n" + "<td class=\"text-left\">" + formatStringtoStringDate(res.getData(), patternsqldate, patternnormdate)
                            + "</td>\n" + "<td class=\"text-left\">" + res.getUser()
                            + "</td>\n" + "<td class=\"text-left\">" + res.formatDescTill(listTill, res.getTill())
                            + "</td>\n" + "<td class=\"text-left\">" + res.formatType(res.getFg_tipo())
                            + "</td>\n" + "<td class=\"text-left\">" + res.getErrors()
                            + "</td>\n" + "</tr>\n";
                }

                String endtable = "</tbody></table>";
                String endbody = "</body></html>";

                String html = header + sttable + thead + content + endtable + endbody;
                InputStream is = new ByteArrayInputStream(html.getBytes());
                getInstance().parseXHtml(wr, document, is);

                document.close();
                wr.close();
                ou.close();
                is.close();

                String base64 = new String(encodeBase64(readFileToByteArray(outputfile)));
                outputfile.delete();
                return base64;
            } catch (IOException | DocumentException ex) {
                insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
            }
        }
        return null;
    }

    /**
     *
     * @param outputfile
     * @param result
     * @return
     */
    public static String pdf_users_list(File outputfile, ArrayList<Users> result) {
        if (result.size() > 0) {

            Db_Master db1 = new Db_Master(true);
            ArrayList<String[]> out1 = db1.get_list_codice_ATL();
            db1.closeDB();

            try (OutputStream ou = new FileOutputStream(outputfile)) { 
                Document document = new Document(A4.rotate(), 20.0F, 20.0F, 20.0F, 20.0F);
                document.addTitle("Mac 2.0");
                document.addAuthor("Seta s.r.l.");
                PdfWriter wr = getInstance(document, ou);
                wr.open();
                document.open();
                String header = "<html><head><title>Mac 2.0</title><style type=\"text/css\">@import url(http://fonts.googleapis.com/css?family=Roboto:400,500,700,300,100);body {font-family: \"Roboto\", helvetica, arial, sans-serif;font-size: 10px;font-weight: 400;text-rendering: optimizeLegibility;}div.table-title {\tfont-family: \"Roboto\", helvetica, arial, sans-serif;\n  text-align:center;\n  margin: auto;\n  max-width: 600px;\n  padding:5px;\n  width: 90%;\n  font-size: 20px;}.table-title h3 {color: #fafafa;font-size: 30px;font-weight: 400;font-style:normal;font-family: \"Roboto\", helvetica, arial, sans-serif;text-shadow: -1px -1px 1px rgba(0, 0, 0, 0.1);text-transform:uppercase;}.table-fill {background: white;border-radius:3px;border-collapse: collapse;height: 320px;margin: auto;max-width: 600px;padding:5px;width: 100%;box-shadow: 0 5px 10px rgba(0, 0, 0, 0.1);animation: float 5s infinite;\n}\n \nth {\n  color:#000000;;\n  background:#91949A;\n  font-size:12px;\n  font-weight: 100;\n  padding:10px;\n  text-align:left;\n  text-shadow: 0 1px 1px rgba(0, 0, 0, 0.1);\n  vertical-align:middle;\n}\n\nth:first-child {\n  border-top-left-radius:3px;\n}\ntr {\n  border-top: 1px solid #C1C3D1;\n  border-bottom-: 1px solid #C1C3D1;\n  color:#666B85;\n  font-size:11px;\n  font-weight:normal;\n  text-shadow: 0 1px 1px rgba(256, 256, 256, 0.1);\n}\n \ntr:hover td {\n  background:#4E5066;\n  color:#FFFFFF;\n  border-top: 1px solid #22262e;\n  border-bottom: 1px solid #22262e;\n}\n \ntr:first-child {\n  border-top:none;\n}\n\ntr:last-child {\n  border-bottom:none;\n}\n \ntr:nth-child(odd) td {\n  background:#EBEBEB;\n}\n \ntr:nth-child(odd):hover td {\n  background:#4E5066;\n}\n\ntr:last-child td:first-child {\n  border-bottom-left-radius:3px;\n}\n \ntr:last-child td:last-child {\n  border-bottom-right-radius:3px;\n}\n \ntd {\n  background:#FFFFFF;\n padding:10;  vertical-align:middle;\n  font-weight:300;\n  font-size:11px;\n  text-shadow: -1px -1px 1px rgba(0, 0, 0, 0.1);\n  border-bottom: 1px solid #C1C3D1;\n\nth.text-left {\n  text-align: left;\n}\n\nth.text-center {\n  text-align: center;\n}\n\nth.text-right {\n  text-align: right;\n}\n\ntd.text-left {\n  text-align: left;\n}\n\ntd.text-center {\n  text-align: center;\n}\n\ntd.text-right {  text-align: right;}</style></head><body>";

                String sttable = "<div class=\"table-title\">Users List - Branch: " + filiale + " </div><table class=\"table-fill\"><thead>";

                String thead = "<tr><th class=\"text-left\">Code</th><th class=\"text-left\">Username</th><th class=\"text-left\">Surname</th><th class=\"text-left\">Name</th><th class=\"text-left\">Type</th><th class=\"text-left\">Status</th><th class=\"text-left\">Validity Password (Day)</th><th class=\"text-left\">Email address</th><th class=\"text-left\">Accounting Code</th></tr></thead><tbody>";
                if (is_IT) {
                    thead = "<tr><th class=\"text-left\">Code</th><th class=\"text-left\">Username</th><th class=\"text-left\">Surname</th><th class=\"text-left\">Name</th><th class=\"text-left\">Type</th><th class=\"text-left\">Status</th><th class=\"text-left\">Validity Password (Day)</th><th class=\"text-left\">Email address</th><th class=\"text-left\">Accounting Code</th><th class=\"text-left\">Code Atlante</th></tr></thead><tbody>";
                }
                String content = "";

                for (int i = 0; i < result.size(); i++) {
                    Users res = (Users) result.get(i);
//                    String dt_del = "";
//                    String val=res.getValidita();
//                    if(val.equalsIgnoreCase("0")){
//                        val="Unlimited";
//                    }

                    if (is_IT) {
                        content = content + "<tr>\n<td class=\"text-left\">" + res.getCod() + "</td>\n"
                                + "<td class=\"text-left\">" + res.getUsername()
                                + "</td>\n" + "<td class=\"text-left\">" + res.getDe_cognome()
                                + "</td>\n" + "<td class=\"text-left\">" + res.getDe_nome()
                                + "</td>\n" + "<td class=\"text-left\">" + res.formatTypeuser(res.getFg_tipo())
                                + "</td>\n" + "<td class=\"text-left\">" + res.formatStatususer(res.getFg_stato())
                                + "</td>\n" + "<td class=\"text-left\">" + formatValidity(res.getValidita())
                                + "</td>\n" + "<td class=\"text-left\">" + res.getEmail()
                                + "</td>\n" + "<td class=\"text-left\">" + res.getConto()
                                + "</td>\n" + "<td class=\"text-left\">" + formatAL(res.getCod(), out1, 1)
                                + "</td>\n" + "</tr>\n";
                    } else {

                        content = content + "<tr>\n<td class=\"text-left\">" + res.getCod() + "</td>\n"
                                + "<td class=\"text-left\">" + res.getUsername()
                                + "</td>\n" + "<td class=\"text-left\">" + res.getDe_cognome()
                                + "</td>\n" + "<td class=\"text-left\">" + res.getDe_nome()
                                + "</td>\n" + "<td class=\"text-left\">" + res.formatTypeuser(res.getFg_tipo())
                                + "</td>\n" + "<td class=\"text-left\">" + res.formatStatususer(res.getFg_stato())
                                + "</td>\n" + "<td class=\"text-left\">" + formatValidity(res.getValidita())
                                + "</td>\n" + "<td class=\"text-left\">" + res.getEmail()
                                + "</td>\n" + "<td class=\"text-left\">" + res.getConto()
                                + "</td>\n" + "</tr>\n";
                    }
                }

                String endtable = "</tbody></table>";
                String endbody = "</body></html>";

                String html = header + sttable + thead + content + endtable + endbody;
                InputStream is = new ByteArrayInputStream(html.getBytes());
                getInstance().parseXHtml(wr, document, is);

                document.close();
                wr.close();
                ou.close();
                is.close();

                String base64 = new String(encodeBase64(readFileToByteArray(outputfile)));
                outputfile.delete();
                return base64;
            } catch (IOException | DocumentException ex) {
                insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
            }
        }
        return null;
    }

    /**
     *
     * @param outputfile
     * @param result
     * @param filiale
     * @return
     */
    public static String pdf_figures_list(File outputfile, ArrayList<Figures> result, String filiale) {
        if (result.size() > 0) {
            try (OutputStream ou = new FileOutputStream(outputfile)) {
                Document document = new Document(A4.rotate(), 20.0F, 20.0F, 20.0F, 20.0F);
                document.addTitle("Mac 2.0");
                document.addAuthor("Seta s.r.l.");
                PdfWriter wr = getInstance(document, ou);
                wr.open();
                document.open();
                String header = "<html><head><title>Mac 2.0</title><style type=\"text/css\">@import url(http://fonts.googleapis.com/css?family=Roboto:400,500,700,300,100);body {font-family: \"Roboto\", helvetica, arial, sans-serif;font-size: 10px;font-weight: 400;text-rendering: optimizeLegibility;}div.table-title {\tfont-family: \"Roboto\", helvetica, arial, sans-serif;\n  text-align:center;\n  margin: auto;\n  max-width: 600px;\n  padding:5px;\n  width: 90%;\n  font-size: 20px;}.table-title h3 {color: #fafafa;font-size: 30px;font-weight: 400;font-style:normal;font-family: \"Roboto\", helvetica, arial, sans-serif;text-shadow: -1px -1px 1px rgba(0, 0, 0, 0.1);text-transform:uppercase;}.table-fill {background: white;border-radius:3px;border-collapse: collapse;height: 320px;margin: auto;max-width: 600px;padding:5px;width: 100%;box-shadow: 0 5px 10px rgba(0, 0, 0, 0.1);animation: float 5s infinite;\n}\n \nth {\n  color:#000000;;\n  background:#91949A;\n  font-size:12px;\n  font-weight: 100;\n  padding:10px;\n  text-align:left;\n  text-shadow: 0 1px 1px rgba(0, 0, 0, 0.1);\n  vertical-align:middle;\n}\n\nth:first-child {\n  border-top-left-radius:3px;\n}\ntr {\n  border-top: 1px solid #C1C3D1;\n  border-bottom-: 1px solid #C1C3D1;\n  color:#666B85;\n  font-size:11px;\n  font-weight:normal;\n  text-shadow: 0 1px 1px rgba(256, 256, 256, 0.1);\n}\n \ntr:hover td {\n  background:#4E5066;\n  color:#FFFFFF;\n  border-top: 1px solid #22262e;\n  border-bottom: 1px solid #22262e;\n}\n \ntr:first-child {\n  border-top:none;\n}\n\ntr:last-child {\n  border-bottom:none;\n}\n \ntr:nth-child(odd) td {\n  background:#EBEBEB;\n}\n \ntr:nth-child(odd):hover td {\n  background:#4E5066;\n}\n\ntr:last-child td:first-child {\n  border-bottom-left-radius:3px;\n}\n \ntr:last-child td:last-child {\n  border-bottom-right-radius:3px;\n}\n \ntd {\n  background:#FFFFFF;\n padding:10;  vertical-align:middle;\n  font-weight:300;\n  font-size:11px;\n  text-shadow: -1px -1px 1px rgba(0, 0, 0, 0.1);\n  border-bottom: 1px solid #C1C3D1;\n\nth.text-left {\n  text-align: left;\n}\n\nth.text-center {\n  text-align: center;\n}\n\nth.text-right {\n  text-align: right;\n}\n\ntd.text-left {\n  text-align: left;\n}\n\ntd.text-center {\n  text-align: center;\n}\n\ntd.text-right {  text-align: right;}</style></head><body>";
                String sttable = "<div class=\"table-title\">Figures List - Branch: " + filiale + " </div><table class=\"table-fill\"><thead>";
                String thead = "<tr><th class=\"text-left\">Code</th><th class=\"text-left\">Description</th><th class=\"text-left\">Kind</th><th class=\"text-left\">Buy Commission</th><th class=\"text-left\">Threshold Buy</th><th class=\"text-left\">Buy Fix Commission</th><th class=\"text-left\">Sell Commission</th><th class=\"text-left\">Threshold Sell</th><th class=\"text-left\">Sell Fix Commission</th><th class=\"text-left\">Sell Back Commission</th><th class=\"text-left\">Payment Mode </th><th class=\"text-left\">Sell Kind Resident</th><th class=\"text-left\">Upload Required</th><th class=\"text-left\">Upload Threshold</th></tr></thead><tbody>";
                String content = "";

                Db_Master db = new Db_Master();
                ArrayList<String[]> elencokind = db.list_selectkind();
                db.closeDB();

                for (int i = 0; i < result.size(); i++) {
                    Figures res = (Figures) result.get(i);
                    String kind = "";
                    for (int x = 0; x < elencokind.size(); x++) {
                        if (elencokind.get(x)[0].equals(res.getFg_sys_trans())) {
                            kind = elencokind.get(x)[1];
                        }
                    }
                    content = content + "<tr>\n<td class=\"text-left\">" + res.getSupporto() + "</td>\n"
                            + "<td class=\"text-left\">" + res.getDe_supporto()
                            + "</td>\n" + "<td class=\"text-left\">" + kind
                            + "</td>\n" + "<td class=\"text-left\">" + res.getCommissione_acquisto()
                            + "</td>\n" + "<td class=\"text-left\">" + res.getBuy_comm_soglia_causale()
                            + "</td>\n" + "<td class=\"text-left\">" + res.getFix_buy_commission()
                            + "</td>\n" + "<td class=\"text-left\">" + res.getCommissione_vendita()
                            + "</td>\n" + "<td class=\"text-left\">" + res.getSell_comm_soglia_causale()
                            + "</td>\n" + "<td class=\"text-left\">" + res.getFix_sell_commission()
                            + "</td>\n" + "<td class=\"text-left\">" + res.getBuy_back_commission();
                    if (res.getFg_tipo_incasso().equals("0")) {
                        content += "</td>\n" + "<td class=\"text-left\">" + "Disabled";
                    } else {
                        content += "</td>\n" + "<td class=\"text-left\">" + "Enabled";
                    }
                    if (res.getResidenti().equals("0")) {
                        content += "</td>\n" + "<td class=\"text-left\">" + "Disabled";
                    } else {
                        content += "</td>\n" + "<td class=\"text-left\">" + "Enabled";
                    }
                    if (res.getFg_uploadobbl().equals("0")) {
                        content += "</td>\n" + "<td class=\"text-left\">" + "Disabled";
                    } else {
                        content += "</td>\n" + "<td class=\"text-left\">" + "Enabled";
                    }
                    content += "</td>\n" + "<td class=\"text-left\">" + res.getUpload_thr()
                            + "</td>\n" + "</tr>\n";
                }

                String endtable = "</tbody></table>";
                String endbody = "</body></html>";
                String html = header + sttable + thead + content + endtable + endbody;
                InputStream is = new ByteArrayInputStream(html.getBytes());
                getInstance().parseXHtml(wr, document, is);
                document.close();
                wr.close();
                ou.close();
                is.close();
                String base64 = new String(encodeBase64(readFileToByteArray(outputfile)));
                outputfile.delete();
                return base64;
            } catch (IOException | DocumentException ex) {
                insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
            }
        }
        return null;
    }

    /**
     *
     * @param outputfile
     * @param result
     * @return
     */
    public static String pdf_currency_list(File outputfile, ArrayList<Currency> result) {
        if (result.size() > 0) {
            try (OutputStream ou = new FileOutputStream(outputfile)) {
                Document document = new Document(A4.rotate(), 20.0F, 20.0F, 20.0F, 20.0F);
                document.addTitle("Mac 2.0");
                document.addAuthor("Seta s.r.l.");
                PdfWriter wr = getInstance(document, ou);
                wr.open();
                document.open();
                String header = "<html><head><title>Mac 2.0</title><style type=\"text/css\">@import url(http://fonts.googleapis.com/css?family=Roboto:400,500,700,300,100);body {font-family: \"Roboto\", helvetica, arial, sans-serif;font-size: 10px;font-weight: 400;text-rendering: optimizeLegibility;}div.table-title {\tfont-family: \"Roboto\", helvetica, arial, sans-serif;\n  text-align:center;\n  margin: auto;\n  max-width: 600px;\n  padding:5px;\n  width: 90%;\n  font-size: 20px;}.table-title h3 {color: #fafafa;font-size: 30px;font-weight: 400;font-style:normal;font-family: \"Roboto\", helvetica, arial, sans-serif;text-shadow: -1px -1px 1px rgba(0, 0, 0, 0.1);text-transform:uppercase;}.table-fill {background: white;border-radius:3px;border-collapse: collapse;height: 320px;margin: auto;max-width: 600px;padding:5px;width: 100%;box-shadow: 0 5px 10px rgba(0, 0, 0, 0.1);animation: float 5s infinite;\n}\n \nth {\n  color:#000000;;\n  background:#91949A;\n  font-size:12px;\n  font-weight: 100;\n  padding:10px;\n  text-align:left;\n  text-shadow: 0 1px 1px rgba(0, 0, 0, 0.1);\n  vertical-align:middle;\n}\n\nth:first-child {\n  border-top-left-radius:3px;\n}\ntr {\n  border-top: 1px solid #C1C3D1;\n  border-bottom-: 1px solid #C1C3D1;\n  color:#666B85;\n  font-size:11px;\n  font-weight:normal;\n  text-shadow: 0 1px 1px rgba(256, 256, 256, 0.1);\n}\n \ntr:hover td {\n  background:#4E5066;\n  color:#FFFFFF;\n  border-top: 1px solid #22262e;\n  border-bottom: 1px solid #22262e;\n}\n \ntr:first-child {\n  border-top:none;\n}\n\ntr:last-child {\n  border-bottom:none;\n}\n \ntr:nth-child(odd) td {\n  background:#EBEBEB;\n}\n \ntr:nth-child(odd):hover td {\n  background:#4E5066;\n}\n\ntr:last-child td:first-child {\n  border-bottom-left-radius:3px;\n}\n \ntr:last-child td:last-child {\n  border-bottom-right-radius:3px;\n}\n \ntd {\n  background:#FFFFFF;\n padding:10;  vertical-align:middle;\n  font-weight:300;\n  font-size:11px;\n  text-shadow: -1px -1px 1px rgba(0, 0, 0, 0.1);\n  border-bottom: 1px solid #C1C3D1;\n\nth.text-left {\n  text-align: left;\n}\n\nth.text-center {\n  text-align: center;\n}\n\nth.text-right {\n  text-align: right;\n}\n\ntd.text-left {\n  text-align: left;\n}\n\ntd.text-center {\n  text-align: center;\n}\n\ntd.text-right {  text-align: right;}</style></head><body>";
                String sttable = "<div class=\"table-title\">Currency List -  Branch: " + filiale + " </div><table class=\"table-fill\"><thead>";
                String thead = "<tr><th class=\"text-left\">Code</th><th class=\"text-left\">Description</th><th class=\"text-left\">BCE Rate</th><th class=\"text-left\">Enable Buy</th><th class=\"text-left\">Enable Sell</th><th class=\"text-left\">Buy Std Type</th><th class=\"text-left\">Sell Std Type</th></tr></thead><tbody>";
                String content = "";

                for (int i = 0; i < result.size(); i++) {
                    Currency res = (Currency) result.get(i);

                    content = content + "<tr>\n<td class=\"text-left\">" + res.getCode() + "</td>\n"
                            + "<td class=\"text-left\">" + res.getDescrizione()
                            + "</td>\n" + "<td class=\"text-left\">" + res.getCambio_bce()
                            + "</td>\n" + "<td class=\"text-left\">" + res.getEnable_buy()
                            + "</td>\n" + "<td class=\"text-left\">" + res.getEnable_sell();
                    if (res.getBuy_std_type().equals("0")) {
                        content += "</td>\n" + "<td class=\"text-left\">" + "Disabled";
                    } else {
                        content += "</td>\n" + "<td class=\"text-left\">" + "Enabled";
                    }
                    if (res.getSell_std_type().equals("0")) {
                        content += "</td>\n" + "<td class=\"text-left\">" + "Disabled";
                    } else {
                        content += "</td>\n" + "<td class=\"text-left\">" + "Enabled";
                    }
                    content += "</td>\n" + "</tr>\n";
                }

                String endtable = "</tbody></table>";
                String endbody = "</body></html>";
                String html = header + sttable + thead + content + endtable + endbody;
                InputStream is = new ByteArrayInputStream(html.getBytes());
                getInstance().parseXHtml(wr, document, is);
                document.close();
                wr.close();
                ou.close();
                is.close();
                String base64 = new String(encodeBase64(readFileToByteArray(outputfile)));
                outputfile.delete();
                return base64;
            } catch (IOException | DocumentException ex) {
                insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
            }
        }
        return null;
    }

    /**
     *
     * @param outputfile
     * @param result
     * @param filiale
     * @return
     */
    public static String pdf_safetill_list(File outputfile, ArrayList<String[]> result, String filiale) {
        if (result.size() > 0) {
            try (OutputStream ou = new FileOutputStream(outputfile)) {
                ArrayList<String[]> array_type_till = list_type_till();
                Document document = new Document(A4.rotate(), 20.0F, 20.0F, 20.0F, 20.0F);
                document.addTitle("Mac 2.0");
                document.addAuthor("Seta s.r.l.");       
                PdfWriter wr = getInstance(document, ou);
                wr.open();
                document.open();
                String header = "<html><head><title>Mac 2.0</title><style type=\"text/css\">@import url(http://fonts.googleapis.com/css?family=Roboto:400,500,700,300,100);body {font-family: \"Roboto\", helvetica, arial, sans-serif;font-size: 10px;font-weight: 400;text-rendering: optimizeLegibility;}div.table-title {\tfont-family: \"Roboto\", helvetica, arial, sans-serif;\n  text-align:center;\n  margin: auto;\n  max-width: 600px;\n  padding:5px;\n  width: 90%;\n  font-size: 20px;}.table-title h3 {color: #fafafa;font-size: 30px;font-weight: 400;font-style:normal;font-family: \"Roboto\", helvetica, arial, sans-serif;text-shadow: -1px -1px 1px rgba(0, 0, 0, 0.1);text-transform:uppercase;}.table-fill {background: white;border-radius:3px;border-collapse: collapse;height: 320px;margin: auto;max-width: 600px;padding:5px;width: 100%;box-shadow: 0 5px 10px rgba(0, 0, 0, 0.1);animation: float 5s infinite;\n}\n \nth {\n  color:#000000;;\n  background:#91949A;\n  font-size:12px;\n  font-weight: 100;\n  padding:10px;\n  text-align:left;\n  text-shadow: 0 1px 1px rgba(0, 0, 0, 0.1);\n  vertical-align:middle;\n}\n\nth:first-child {\n  border-top-left-radius:3px;\n}\ntr {\n  border-top: 1px solid #C1C3D1;\n  border-bottom-: 1px solid #C1C3D1;\n  color:#666B85;\n  font-size:11px;\n  font-weight:normal;\n  text-shadow: 0 1px 1px rgba(256, 256, 256, 0.1);\n}\n \ntr:hover td {\n  background:#4E5066;\n  color:#FFFFFF;\n  border-top: 1px solid #22262e;\n  border-bottom: 1px solid #22262e;\n}\n \ntr:first-child {\n  border-top:none;\n}\n\ntr:last-child {\n  border-bottom:none;\n}\n \ntr:nth-child(odd) td {\n  background:#EBEBEB;\n}\n \ntr:nth-child(odd):hover td {\n  background:#4E5066;\n}\n\ntr:last-child td:first-child {\n  border-bottom-left-radius:3px;\n}\n \ntr:last-child td:last-child {\n  border-bottom-right-radius:3px;\n}\n \ntd {\n  background:#FFFFFF;\n padding:10;  vertical-align:middle;\n  font-weight:300;\n  font-size:11px;\n  text-shadow: -1px -1px 1px rgba(0, 0, 0, 0.1);\n  border-bottom: 1px solid #C1C3D1;\n\nth.text-left {\n  text-align: left;\n}\n\nth.text-center {\n  text-align: center;\n}\n\nth.text-right {\n  text-align: right;\n}\n\ntd.text-left {\n  text-align: left;\n}\n\ntd.text-center {\n  text-align: center;\n}\n\ntd.text-right {  text-align: right;}</style></head><body>";
                String sttable = "<div class=\"table-title\">SafeTill List - Branch: " + filiale + "</div><table class=\"table-fill\"><thead>";
                String thead = "<tr><th class=\"text-left\">Code</th><th class=\"text-left\">Description</th><th class=\"text-left\">Type</th><th class=\"text-left\">Status</th></tr></thead><tbody>";
                String content = "";

                for (int i = 0; i < result.size(); i++) {
                    String[] res = (String[]) result.get(i);

                    content = content + "<tr>\n<td class=\"text-left\">" + res[0] + "</td>\n"
                            + "<td class=\"text-left\">" + res[1]
                            + "</td>\n" + "<td class=\"text-left\">" + formatAL(res[2], array_type_till, 1)
                            + "</td>\n" + "<td class=\"text-left\">" + formatStatus_general(res[3]);
                    content += "</td>\n" + "</tr>\n";
                }

                String endtable = "</tbody></table>";
                String endbody = "</body></html>";
                String html = header + sttable + thead + content + endtable + endbody;
                InputStream is = new ByteArrayInputStream(html.getBytes());
                getInstance().parseXHtml(wr, document, is);
                document.close();
                wr.close();
                ou.close();
                is.close();
                String base64 = new String(encodeBase64(readFileToByteArray(outputfile)));
                outputfile.delete();
                return base64;
            } catch (IOException | DocumentException ex) {
                insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
            }
        }
        return null;
    }

    /**
     *
     * @param outputfile
     * @param result
     * @return
     */
    public static String pdf_bank_list(File outputfile, ArrayList<String[]> result) {
        if (result.size() > 0) {
            try (OutputStream ou = new FileOutputStream(outputfile)) {

                Document document = new Document(A4.rotate(), 20.0F, 20.0F, 20.0F, 20.0F);
                document.addTitle("Mac 2.0");
                document.addAuthor("Seta s.r.l.");
                PdfWriter wr = getInstance(document, ou);
                wr.open();
                document.open();
                String header = "<html><head><title>Mac 2.0</title><style type=\"text/css\">@import url(http://fonts.googleapis.com/css?family=Roboto:400,500,700,300,100);body {font-family: \"Roboto\", helvetica, arial, sans-serif;font-size: 10px;font-weight: 400;text-rendering: optimizeLegibility;}div.table-title {\tfont-family: \"Roboto\", helvetica, arial, sans-serif;\n  text-align:center;\n  margin: auto;\n  max-width: 600px;\n  padding:5px;\n  width: 90%;\n  font-size: 20px;}.table-title h3 {color: #fafafa;font-size: 30px;font-weight: 400;font-style:normal;font-family: \"Roboto\", helvetica, arial, sans-serif;text-shadow: -1px -1px 1px rgba(0, 0, 0, 0.1);text-transform:uppercase;}.table-fill {background: white;border-radius:3px;border-collapse: collapse;height: 320px;margin: auto;max-width: 600px;padding:5px;width: 100%;box-shadow: 0 5px 10px rgba(0, 0, 0, 0.1);animation: float 5s infinite;\n}\n \nth {\n  color:#000000;;\n  background:#91949A;\n  font-size:12px;\n  font-weight: 100;\n  padding:10px;\n  text-align:left;\n  text-shadow: 0 1px 1px rgba(0, 0, 0, 0.1);\n  vertical-align:middle;\n}\n\nth:first-child {\n  border-top-left-radius:3px;\n}\ntr {\n  border-top: 1px solid #C1C3D1;\n  border-bottom-: 1px solid #C1C3D1;\n  color:#666B85;\n  font-size:11px;\n  font-weight:normal;\n  text-shadow: 0 1px 1px rgba(256, 256, 256, 0.1);\n}\n \ntr:hover td {\n  background:#4E5066;\n  color:#FFFFFF;\n  border-top: 1px solid #22262e;\n  border-bottom: 1px solid #22262e;\n}\n \ntr:first-child {\n  border-top:none;\n}\n\ntr:last-child {\n  border-bottom:none;\n}\n \ntr:nth-child(odd) td {\n  background:#EBEBEB;\n}\n \ntr:nth-child(odd):hover td {\n  background:#4E5066;\n}\n\ntr:last-child td:first-child {\n  border-bottom-left-radius:3px;\n}\n \ntr:last-child td:last-child {\n  border-bottom-right-radius:3px;\n}\n \ntd {\n  background:#FFFFFF;\n padding:10;  vertical-align:middle;\n  font-weight:300;\n  font-size:11px;\n  text-shadow: -1px -1px 1px rgba(0, 0, 0, 0.1);\n  border-bottom: 1px solid #C1C3D1;\n\nth.text-left {\n  text-align: left;\n}\n\nth.text-center {\n  text-align: center;\n}\n\nth.text-right {\n  text-align: right;\n}\n\ntd.text-left {\n  text-align: left;\n}\n\ntd.text-center {\n  text-align: center;\n}\n\ntd.text-right {  text-align: right;}</style></head><body>";
                String sttable = "<div class=\"table-title\">Bank List - Branch: " + filiale + " </div><table class=\"table-fill\"><thead>";
                String thead = "<tr><th class=\"text-left\">Code</th><th class=\"text-left\">Description</th><th class=\"text-left\">Accounting Code</th><th class=\"text-left\">Status</th><th class=\"text-left\">Date Of Cancellation</th></tr></thead><tbody>";
                String content = "";

                for (int i = 0; i < result.size(); i++) {
                    String[] res = (String[]) result.get(i);

                    content = content + "<tr>\n<td class=\"text-left\">" + res[0] + "</td>\n"
                            + "<td class=\"text-left\">" + res[1]
                            + "</td>\n" + "<td class=\"text-left\">" + res[2]
                            + "</td>\n" + "<td class=\"text-left\">" + formatStatus_general_cru(res[4])
                            + "</td>\n" + "<td class=\"text-left\">" + res[3];
                    content += "</td>\n" + "</tr>\n";
                }

                String endtable = "</tbody></table>";
                String endbody = "</body></html>";
                String html = header + sttable + thead + content + endtable + endbody;
                InputStream is = new ByteArrayInputStream(html.getBytes());
                getInstance().parseXHtml(wr, document, is);
                document.close();
                wr.close();
                ou.close();
                is.close();
                String base64 = new String(encodeBase64(readFileToByteArray(outputfile)));
                outputfile.delete();
                return base64;
            } catch (IOException | DocumentException ex) {
                insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
            }
        }
        return null;
    }

    /**
     *
     * @param outputfile
     * @param result
     * @return
     */
    public static String pdf_fixcomm_list(File outputfile, ArrayList<String[]> result) {
        if (result.size() > 0) {
            try (OutputStream ou = new FileOutputStream(outputfile)) {
                ArrayList<String[]> array_kind = list_all_kind(filiale);
                Document document = new Document(A4.rotate(), 20.0F, 20.0F, 20.0F, 20.0F);
                document.addTitle("Mac 2.0");
                document.addAuthor("Seta s.r.l.");
                PdfWriter wr = getInstance(document, ou);
                wr.open();
                document.open();
                String header = "<html><head><title>Mac 2.0</title><style type=\"text/css\">@import url(http://fonts.googleapis.com/css?family=Roboto:400,500,700,300,100);body {font-family: \"Roboto\", helvetica, arial, sans-serif;font-size: 10px;font-weight: 400;text-rendering: optimizeLegibility;}div.table-title {\tfont-family: \"Roboto\", helvetica, arial, sans-serif;\n  text-align:center;\n  margin: auto;\n  max-width: 600px;\n  padding:5px;\n  width: 90%;\n  font-size: 20px;}.table-title h3 {color: #fafafa;font-size: 30px;font-weight: 400;font-style:normal;font-family: \"Roboto\", helvetica, arial, sans-serif;text-shadow: -1px -1px 1px rgba(0, 0, 0, 0.1);text-transform:uppercase;}.table-fill {background: white;border-radius:3px;border-collapse: collapse;height: 320px;margin: auto;max-width: 600px;padding:5px;width: 100%;box-shadow: 0 5px 10px rgba(0, 0, 0, 0.1);animation: float 5s infinite;\n}\n \nth {\n  color:#000000;;\n  background:#91949A;\n  font-size:12px;\n  font-weight: 100;\n  padding:10px;\n  text-align:left;\n  text-shadow: 0 1px 1px rgba(0, 0, 0, 0.1);\n  vertical-align:middle;\n}\n\nth:first-child {\n  border-top-left-radius:3px;\n}\ntr {\n  border-top: 1px solid #C1C3D1;\n  border-bottom-: 1px solid #C1C3D1;\n  color:#666B85;\n  font-size:11px;\n  font-weight:normal;\n  text-shadow: 0 1px 1px rgba(256, 256, 256, 0.1);\n}\n \ntr:hover td {\n  background:#4E5066;\n  color:#FFFFFF;\n  border-top: 1px solid #22262e;\n  border-bottom: 1px solid #22262e;\n}\n \ntr:first-child {\n  border-top:none;\n}\n\ntr:last-child {\n  border-bottom:none;\n}\n \ntr:nth-child(odd) td {\n  background:#EBEBEB;\n}\n \ntr:nth-child(odd):hover td {\n  background:#4E5066;\n}\n\ntr:last-child td:first-child {\n  border-bottom-left-radius:3px;\n}\n \ntr:last-child td:last-child {\n  border-bottom-right-radius:3px;\n}\n \ntd {\n  background:#FFFFFF;\n padding:10;  vertical-align:middle;\n  font-weight:300;\n  font-size:11px;\n  text-shadow: -1px -1px 1px rgba(0, 0, 0, 0.1);\n  border-bottom: 1px solid #C1C3D1;\n\nth.text-left {\n  text-align: left;\n}\n\nth.text-center {\n  text-align: center;\n}\n\nth.text-right {\n  text-align: right;\n}\n\ntd.text-left {\n  text-align: left;\n}\n\ntd.text-center {\n  text-align: center;\n}\n\ntd.text-right {  text-align: right;}</style></head><body>";
                String sttable = "<div class=\"table-title\">Fix Commission List - Branch: " + filiale + " </div><table class=\"table-fill\"><thead>";
                String thead = "<tr><th class=\"text-left\">Kind</th><th class=\"text-left\">Min</th><th class=\"text-left\">Max</th><th class=\"text-left\">Buy</th><th class=\"text-left\">Sell</th><th class=\"text-left\">Status</th></tr></thead><tbody>";
                String content = "";

                for (int i = 0; i < result.size(); i++) {
                    String[] res = (String[]) result.get(i);

                    content = content + "<tr>\n<td class=\"text-left\">" + formatAL(res[0], array_kind, 1)
                            + "</td>\n" + "<td class=\"text-left\">" + res[1]
                            + "</td>\n" + "<td class=\"text-left\">" + res[2]
                            + "</td>\n" + "<td class=\"text-left\">" + res[3]
                            + "</td>\n" + "<td class=\"text-left\">" + res[4]
                            + "</td>\n" + "<td class=\"text-left\">" + formatStatus_general(res[5]);
                    content += "</td>\n" + "</tr>\n";
                }

                String endtable = "</tbody></table>";
                String endbody = "</body></html>";
                String html = header + sttable + thead + content + endtable + endbody;
                InputStream is = new ByteArrayInputStream(html.getBytes());
                getInstance().parseXHtml(wr, document, is);
                document.close();
                wr.close();
                ou.close();
                is.close();
                String base64 = new String(encodeBase64(readFileToByteArray(outputfile)));
                outputfile.delete();
                return base64;
            } catch (IOException | DocumentException ex) {
                insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
            }
        }
        return null;
    }

    /**
     *
     * @param outputfile
     * @param result
     * @return
     */
    public static String pdf_kindfixcomm_list(File outputfile, ArrayList<String[]> result) {
        if (result.size() > 0) {
            try (OutputStream ou = new FileOutputStream(outputfile)) {

                Document document = new Document(A4.rotate(), 20.0F, 20.0F, 20.0F, 20.0F);
                document.addTitle("Mac 2.0");
                document.addAuthor("Seta s.r.l.");
                PdfWriter wr = getInstance(document, ou);
                wr.open();
                document.open();
                String header = "<html><head><title>Mac 2.0</title><style type=\"text/css\">@import url(http://fonts.googleapis.com/css?family=Roboto:400,500,700,300,100);body {font-family: \"Roboto\", helvetica, arial, sans-serif;font-size: 10px;font-weight: 400;text-rendering: optimizeLegibility;}div.table-title {\tfont-family: \"Roboto\", helvetica, arial, sans-serif;\n  text-align:center;\n  margin: auto;\n  max-width: 600px;\n  padding:5px;\n  width: 90%;\n  font-size: 20px;}.table-title h3 {color: #fafafa;font-size: 30px;font-weight: 400;font-style:normal;font-family: \"Roboto\", helvetica, arial, sans-serif;text-shadow: -1px -1px 1px rgba(0, 0, 0, 0.1);text-transform:uppercase;}.table-fill {background: white;border-radius:3px;border-collapse: collapse;height: 320px;margin: auto;max-width: 600px;padding:5px;width: 100%;box-shadow: 0 5px 10px rgba(0, 0, 0, 0.1);animation: float 5s infinite;\n}\n \nth {\n  color:#000000;;\n  background:#91949A;\n  font-size:12px;\n  font-weight: 100;\n  padding:10px;\n  text-align:left;\n  text-shadow: 0 1px 1px rgba(0, 0, 0, 0.1);\n  vertical-align:middle;\n}\n\nth:first-child {\n  border-top-left-radius:3px;\n}\ntr {\n  border-top: 1px solid #C1C3D1;\n  border-bottom-: 1px solid #C1C3D1;\n  color:#666B85;\n  font-size:11px;\n  font-weight:normal;\n  text-shadow: 0 1px 1px rgba(256, 256, 256, 0.1);\n}\n \ntr:hover td {\n  background:#4E5066;\n  color:#FFFFFF;\n  border-top: 1px solid #22262e;\n  border-bottom: 1px solid #22262e;\n}\n \ntr:first-child {\n  border-top:none;\n}\n\ntr:last-child {\n  border-bottom:none;\n}\n \ntr:nth-child(odd) td {\n  background:#EBEBEB;\n}\n \ntr:nth-child(odd):hover td {\n  background:#4E5066;\n}\n\ntr:last-child td:first-child {\n  border-bottom-left-radius:3px;\n}\n \ntr:last-child td:last-child {\n  border-bottom-right-radius:3px;\n}\n \ntd {\n  background:#FFFFFF;\n padding:10;  vertical-align:middle;\n  font-weight:300;\n  font-size:11px;\n  text-shadow: -1px -1px 1px rgba(0, 0, 0, 0.1);\n  border-bottom: 1px solid #C1C3D1;\n\nth.text-left {\n  text-align: left;\n}\n\nth.text-center {\n  text-align: center;\n}\n\nth.text-right {\n  text-align: right;\n}\n\ntd.text-left {\n  text-align: left;\n}\n\ntd.text-center {\n  text-align: center;\n}\n\ntd.text-right {  text-align: right;}</style></head><body>";
                String sttable = "<div class=\"table-title\">Kind Fix Commission List - Branch: " + filiale + " </div><table class=\"table-fill\"><thead>";
                String thead = "<tr><th class=\"text-left\">Code</th><th class=\"text-left\">Description</th><th class=\"text-left\">Value</th><th class=\"text-left\">Status</th></tr></thead><tbody>";
                String content = "";

                for (int i = 0; i < result.size(); i++) {
                    String[] res = (String[]) result.get(i);

                    content = content + "<tr>\n<td class=\"text-left\">" + res[0]
                            + "</td>\n" + "<td class=\"text-left\">" + res[1]
                            + "</td>\n" + "<td class=\"text-left\">" + res[2]
                            + "</td>\n" + "<td class=\"text-left\">" + formatStatus_general(res[3]);
                    content += "</td>\n" + "</tr>\n";
                }

                String endtable = "</tbody></table>";
                String endbody = "</body></html>";
                String html = header + sttable + thead + content + endtable + endbody;
                InputStream is = new ByteArrayInputStream(html.getBytes());
                getInstance().parseXHtml(wr, document, is);
                document.close();
                wr.close();
                ou.close();
                is.close();
                String base64 = new String(encodeBase64(readFileToByteArray(outputfile)));
                outputfile.delete();
                return base64;
            } catch (IOException | DocumentException ex) {
                insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
            }
        }
        return null;
    }

    /**
     *
     * @param outputfile
     * @param result
     * @param filiale
     * @return
     */
    public static String pdf_raterange_list(File outputfile, ArrayList<String[]> result, String filiale) {
        if (result.size() > 0) {
            try (OutputStream ou = new FileOutputStream(outputfile)) {
                ArrayList<String[]> array_kind = list_all_kind(filiale);
                Document document = new Document(A4.rotate(), 20.0F, 20.0F, 20.0F, 20.0F);
                document.addTitle("Mac 2.0");
                document.addAuthor("Seta s.r.l.");
                PdfWriter wr = getInstance(document, ou);
                wr.open();
                document.open();
                String header = "<html><head><title>Mac 2.0</title><style type=\"text/css\">@import url(http://fonts.googleapis.com/css?family=Roboto:400,500,700,300,100);body {font-family: \"Roboto\", helvetica, arial, sans-serif;font-size: 10px;font-weight: 400;text-rendering: optimizeLegibility;}div.table-title {\tfont-family: \"Roboto\", helvetica, arial, sans-serif;\n  text-align:center;\n  margin: auto;\n  max-width: 600px;\n  padding:5px;\n  width: 90%;\n  font-size: 20px;}.table-title h3 {color: #fafafa;font-size: 30px;font-weight: 400;font-style:normal;font-family: \"Roboto\", helvetica, arial, sans-serif;text-shadow: -1px -1px 1px rgba(0, 0, 0, 0.1);text-transform:uppercase;}.table-fill {background: white;border-radius:3px;border-collapse: collapse;height: 320px;margin: auto;max-width: 600px;padding:5px;width: 100%;box-shadow: 0 5px 10px rgba(0, 0, 0, 0.1);animation: float 5s infinite;\n}\n \nth {\n  color:#000000;;\n  background:#91949A;\n  font-size:12px;\n  font-weight: 100;\n  padding:10px;\n  text-align:left;\n  text-shadow: 0 1px 1px rgba(0, 0, 0, 0.1);\n  vertical-align:middle;\n}\n\nth:first-child {\n  border-top-left-radius:3px;\n}\ntr {\n  border-top: 1px solid #C1C3D1;\n  border-bottom-: 1px solid #C1C3D1;\n  color:#666B85;\n  font-size:11px;\n  font-weight:normal;\n  text-shadow: 0 1px 1px rgba(256, 256, 256, 0.1);\n}\n \ntr:hover td {\n  background:#4E5066;\n  color:#FFFFFF;\n  border-top: 1px solid #22262e;\n  border-bottom: 1px solid #22262e;\n}\n \ntr:first-child {\n  border-top:none;\n}\n\ntr:last-child {\n  border-bottom:none;\n}\n \ntr:nth-child(odd) td {\n  background:#EBEBEB;\n}\n \ntr:nth-child(odd):hover td {\n  background:#4E5066;\n}\n\ntr:last-child td:first-child {\n  border-bottom-left-radius:3px;\n}\n \ntr:last-child td:last-child {\n  border-bottom-right-radius:3px;\n}\n \ntd {\n  background:#FFFFFF;\n padding:10;  vertical-align:middle;\n  font-weight:300;\n  font-size:11px;\n  text-shadow: -1px -1px 1px rgba(0, 0, 0, 0.1);\n  border-bottom: 1px solid #C1C3D1;\n\nth.text-left {\n  text-align: left;\n}\n\nth.text-center {\n  text-align: center;\n}\n\nth.text-right {\n  text-align: right;\n}\n\ntd.text-left {\n  text-align: left;\n}\n\ntd.text-center {\n  text-align: center;\n}\n\ntd.text-right {  text-align: right;}</style></head><body>";
                String sttable = "<div class=\"table-title\">Level Rate List - Branch: " + filiale + "</div><table class=\"table-fill\"><thead>";
                String thead = "<tr><th class=\"text-left\">Kind</th><th class=\"text-left\">Min</th><th class=\"text-left\">Max</th><th class=\"text-left\">Buy</th><th class=\"text-left\">Sell</th><th class=\"text-left\">Status</th></tr></thead><tbody>";
                String content = "";

                for (int i = 0; i < result.size(); i++) {
                    String[] res = (String[]) result.get(i);

                    content = content + "<tr>\n<td class=\"text-left\">" + formatAL(res[0], array_kind, 1)
                            + "</td>\n" + "<td class=\"text-left\">" + res[1]
                            + "</td>\n" + "<td class=\"text-left\">" + res[2]
                            + "</td>\n" + "<td class=\"text-left\">" + res[3]
                            + "</td>\n" + "<td class=\"text-left\">" + res[4]
                            + "</td>\n" + "<td class=\"text-left\">" + formatStatus_general(res[5]);
                    content += "</td>\n" + "</tr>\n";
                }

                String endtable = "</tbody></table>";
                String endbody = "</body></html>";
                String html = header + sttable + thead + content + endtable + endbody;
                InputStream is = new ByteArrayInputStream(html.getBytes());
                getInstance().parseXHtml(wr, document, is);
                document.close();
                wr.close();
                ou.close();
                is.close();
                String base64 = new String(encodeBase64(readFileToByteArray(outputfile)));
                outputfile.delete();
                return base64;
            } catch (IOException | DocumentException ex) {
                insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
            }
        }
        return null;
    }

    /**
     *
     * @param outputfile
     * @param result
     * @return
     */
    public static String pdf_lowcomm_list(File outputfile, ArrayList<String[]> result) {
        if (result.size() > 0) {
            try (OutputStream ou = new FileOutputStream(outputfile)) {
                ArrayList<String[]> array_kind = list_all_kind(filiale);
                Document document = new Document(A4.rotate(), 20.0F, 20.0F, 20.0F, 20.0F);
                document.addTitle("Mac 2.0");
                document.addAuthor("Seta s.r.l.");
                PdfWriter wr = getInstance(document, ou);
                wr.open();
                document.open();
                String header = "<html><head><title>Mac 2.0</title><style type=\"text/css\">@import url(http://fonts.googleapis.com/css?family=Roboto:400,500,700,300,100);body {font-family: \"Roboto\", helvetica, arial, sans-serif;font-size: 10px;font-weight: 400;text-rendering: optimizeLegibility;}div.table-title {\tfont-family: \"Roboto\", helvetica, arial, sans-serif;\n  text-align:center;\n  margin: auto;\n  max-width: 600px;\n  padding:5px;\n  width: 90%;\n  font-size: 20px;}.table-title h3 {color: #fafafa;font-size: 30px;font-weight: 400;font-style:normal;font-family: \"Roboto\", helvetica, arial, sans-serif;text-shadow: -1px -1px 1px rgba(0, 0, 0, 0.1);text-transform:uppercase;}.table-fill {background: white;border-radius:3px;border-collapse: collapse;height: 320px;margin: auto;max-width: 600px;padding:5px;width: 100%;box-shadow: 0 5px 10px rgba(0, 0, 0, 0.1);animation: float 5s infinite;\n}\n \nth {\n  color:#000000;;\n  background:#91949A;\n  font-size:12px;\n  font-weight: 100;\n  padding:10px;\n  text-align:left;\n  text-shadow: 0 1px 1px rgba(0, 0, 0, 0.1);\n  vertical-align:middle;\n}\n\nth:first-child {\n  border-top-left-radius:3px;\n}\ntr {\n  border-top: 1px solid #C1C3D1;\n  border-bottom-: 1px solid #C1C3D1;\n  color:#666B85;\n  font-size:11px;\n  font-weight:normal;\n  text-shadow: 0 1px 1px rgba(256, 256, 256, 0.1);\n}\n \ntr:hover td {\n  background:#4E5066;\n  color:#FFFFFF;\n  border-top: 1px solid #22262e;\n  border-bottom: 1px solid #22262e;\n}\n \ntr:first-child {\n  border-top:none;\n}\n\ntr:last-child {\n  border-bottom:none;\n}\n \ntr:nth-child(odd) td {\n  background:#EBEBEB;\n}\n \ntr:nth-child(odd):hover td {\n  background:#4E5066;\n}\n\ntr:last-child td:first-child {\n  border-bottom-left-radius:3px;\n}\n \ntr:last-child td:last-child {\n  border-bottom-right-radius:3px;\n}\n \ntd {\n  background:#FFFFFF;\n padding:10;  vertical-align:middle;\n  font-weight:300;\n  font-size:11px;\n  text-shadow: -1px -1px 1px rgba(0, 0, 0, 0.1);\n  border-bottom: 1px solid #C1C3D1;\n\nth.text-left {\n  text-align: left;\n}\n\nth.text-center {\n  text-align: center;\n}\n\nth.text-right {\n  text-align: right;\n}\n\ntd.text-left {\n  text-align: left;\n}\n\ntd.text-center {\n  text-align: center;\n}\n\ntd.text-right {  text-align: right;}</style></head><body>";
                String sttable = "<div class=\"table-title\">Low Commission Justify List - Branch: " + filiale + " </div><table class=\"table-fill\"><thead>";
                String thead = "<tr><th class=\"text-left\">Code</th><th class=\"text-left\">Description</th><th class=\"text-left\">Status</th></tr></thead><tbody>";
                String content = "";

                for (int i = 0; i < result.size(); i++) {
                    String[] res = (String[]) result.get(i);

                    content = content + "<tr>\n<td class=\"text-left\">" + res[0]
                            + "</td>\n" + "<td class=\"text-left\">" + res[1]
                            + "</td>\n" + "<td class=\"text-left\">" + formatStatus_general(res[2]);
                    content += "</td>\n" + "</tr>\n";
                }

                String endtable = "</tbody></table>";
                String endbody = "</body></html>";
                String html = header + sttable + thead + content + endtable + endbody;
                InputStream is = new ByteArrayInputStream(html.getBytes());
                getInstance().parseXHtml(wr, document, is);
                document.close();
                wr.close();
                ou.close();
                is.close();
                String base64 = new String(encodeBase64(readFileToByteArray(outputfile)));
                outputfile.delete();
                return base64;
            } catch (IOException | DocumentException ex) {
                insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
            }
        }
        return null;
    }

    /**
     *
     * @param outputfile
     * @param result
     * @return
     */
    public static String pdf_doctype_list(File outputfile, ArrayList<String[]> result) {
        if (result.size() > 0) {
            try (OutputStream ou = new FileOutputStream(outputfile)) {
                ArrayList<String[]> array_kind = list_all_kind(filiale);
                Document document = new Document(A4.rotate(), 20.0F, 20.0F, 20.0F, 20.0F);
                document.addTitle("Mac 2.0");
                document.addAuthor("Seta s.r.l.");
                PdfWriter wr = getInstance(document, ou);
                wr.open();
                document.open();
                String header = "<html><head><title>Mac 2.0</title><style type=\"text/css\">@import url(http://fonts.googleapis.com/css?family=Roboto:400,500,700,300,100);body {font-family: \"Roboto\", helvetica, arial, sans-serif;font-size: 10px;font-weight: 400;text-rendering: optimizeLegibility;}div.table-title {\tfont-family: \"Roboto\", helvetica, arial, sans-serif;\n  text-align:center;\n  margin: auto;\n  max-width: 600px;\n  padding:5px;\n  width: 90%;\n  font-size: 20px;}.table-title h3 {color: #fafafa;font-size: 30px;font-weight: 400;font-style:normal;font-family: \"Roboto\", helvetica, arial, sans-serif;text-shadow: -1px -1px 1px rgba(0, 0, 0, 0.1);text-transform:uppercase;}.table-fill {background: white;border-radius:3px;border-collapse: collapse;height: 320px;margin: auto;max-width: 600px;padding:5px;width: 100%;box-shadow: 0 5px 10px rgba(0, 0, 0, 0.1);animation: float 5s infinite;\n}\n \nth {\n  color:#000000;;\n  background:#91949A;\n  font-size:12px;\n  font-weight: 100;\n  padding:10px;\n  text-align:left;\n  text-shadow: 0 1px 1px rgba(0, 0, 0, 0.1);\n  vertical-align:middle;\n}\n\nth:first-child {\n  border-top-left-radius:3px;\n}\ntr {\n  border-top: 1px solid #C1C3D1;\n  border-bottom-: 1px solid #C1C3D1;\n  color:#666B85;\n  font-size:11px;\n  font-weight:normal;\n  text-shadow: 0 1px 1px rgba(256, 256, 256, 0.1);\n}\n \ntr:hover td {\n  background:#4E5066;\n  color:#FFFFFF;\n  border-top: 1px solid #22262e;\n  border-bottom: 1px solid #22262e;\n}\n \ntr:first-child {\n  border-top:none;\n}\n\ntr:last-child {\n  border-bottom:none;\n}\n \ntr:nth-child(odd) td {\n  background:#EBEBEB;\n}\n \ntr:nth-child(odd):hover td {\n  background:#4E5066;\n}\n\ntr:last-child td:first-child {\n  border-bottom-left-radius:3px;\n}\n \ntr:last-child td:last-child {\n  border-bottom-right-radius:3px;\n}\n \ntd {\n  background:#FFFFFF;\n padding:10;  vertical-align:middle;\n  font-weight:300;\n  font-size:11px;\n  text-shadow: -1px -1px 1px rgba(0, 0, 0, 0.1);\n  border-bottom: 1px solid #C1C3D1;\n\nth.text-left {\n  text-align: left;\n}\n\nth.text-center {\n  text-align: center;\n}\n\nth.text-right {\n  text-align: right;\n}\n\ntd.text-left {\n  text-align: left;\n}\n\ntd.text-center {\n  text-align: center;\n}\n\ntd.text-right {  text-align: right;}</style></head><body>";
                String sttable = "<div class=\"table-title\">Identity Document List - Branch: " + filiale + " </div><table class=\"table-fill\"><thead>";
                String thead = "<tr><th class=\"text-left\">Code</th><th class=\"text-left\">Description</th><th class=\"text-left\">OAM Code</th></tr></thead><tbody>";
                String content = "";

                for (int i = 0; i < result.size(); i++) {
                    String[] res = (String[]) result.get(i);

                    content = content + "<tr>\n<td class=\"text-left\">" + res[0]
                            + "</td>\n" + "<td class=\"text-left\">" + res[1]
                            + "</td>\n" + "<td class=\"text-left\">" + res[2];
                    content += "</td>\n" + "</tr>\n";
                }

                String endtable = "</tbody></table>";
                String endbody = "</body></html>";
                String html = header + sttable + thead + content + endtable + endbody;
                InputStream is = new ByteArrayInputStream(html.getBytes());
                getInstance().parseXHtml(wr, document, is);
                document.close();
                wr.close();
                ou.close();
                is.close();
                String base64 = new String(encodeBase64(readFileToByteArray(outputfile)));
                outputfile.delete();
                return base64;
            } catch (IOException | DocumentException ex) {
                insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
            }
        }
        return null;
    }

    /**
     *
     * @param outputfile
     * @param result
     * @return
     */
    public static String pdf_nations_list(File outputfile, ArrayList<String[]> result) {
        if (result.size() > 0) {
            try (OutputStream ou = new FileOutputStream(outputfile)) {
                ArrayList<String[]> array_category_nations = category_nations();
                Document document = new Document(A4.rotate(), 20.0F, 20.0F, 20.0F, 20.0F);
                document.addTitle("Mac 2.0");
                document.addAuthor("Seta s.r.l.");
                PdfWriter wr = getInstance(document, ou);
                wr.open();
                document.open();
                String header = "<html><head><title>Mac 2.0</title><style type=\"text/css\">@import url(http://fonts.googleapis.com/css?family=Roboto:400,500,700,300,100);body {font-family: \"Roboto\", helvetica, arial, sans-serif;font-size: 10px;font-weight: 400;text-rendering: optimizeLegibility;}div.table-title {\tfont-family: \"Roboto\", helvetica, arial, sans-serif;\n  text-align:center;\n  margin: auto;\n  max-width: 600px;\n  padding:5px;\n  width: 90%;\n  font-size: 20px;}.table-title h3 {color: #fafafa;font-size: 30px;font-weight: 400;font-style:normal;font-family: \"Roboto\", helvetica, arial, sans-serif;text-shadow: -1px -1px 1px rgba(0, 0, 0, 0.1);text-transform:uppercase;}.table-fill {background: white;border-radius:3px;border-collapse: collapse;height: 320px;margin: auto;max-width: 600px;padding:5px;width: 100%;box-shadow: 0 5px 10px rgba(0, 0, 0, 0.1);animation: float 5s infinite;\n}\n \nth {\n  color:#000000;;\n  background:#91949A;\n  font-size:12px;\n  font-weight: 100;\n  padding:10px;\n  text-align:left;\n  text-shadow: 0 1px 1px rgba(0, 0, 0, 0.1);\n  vertical-align:middle;\n}\n\nth:first-child {\n  border-top-left-radius:3px;\n}\ntr {\n  border-top: 1px solid #C1C3D1;\n  border-bottom-: 1px solid #C1C3D1;\n  color:#666B85;\n  font-size:11px;\n  font-weight:normal;\n  text-shadow: 0 1px 1px rgba(256, 256, 256, 0.1);\n}\n \ntr:hover td {\n  background:#4E5066;\n  color:#FFFFFF;\n  border-top: 1px solid #22262e;\n  border-bottom: 1px solid #22262e;\n}\n \ntr:first-child {\n  border-top:none;\n}\n\ntr:last-child {\n  border-bottom:none;\n}\n \ntr:nth-child(odd) td {\n  background:#EBEBEB;\n}\n \ntr:nth-child(odd):hover td {\n  background:#4E5066;\n}\n\ntr:last-child td:first-child {\n  border-bottom-left-radius:3px;\n}\n \ntr:last-child td:last-child {\n  border-bottom-right-radius:3px;\n}\n \ntd {\n  background:#FFFFFF;\n padding:10;  vertical-align:middle;\n  font-weight:300;\n  font-size:11px;\n  text-shadow: -1px -1px 1px rgba(0, 0, 0, 0.1);\n  border-bottom: 1px solid #C1C3D1;\n\nth.text-left {\n  text-align: left;\n}\n\nth.text-center {\n  text-align: center;\n}\n\nth.text-right {\n  text-align: right;\n}\n\ntd.text-left {\n  text-align: left;\n}\n\ntd.text-center {\n  text-align: center;\n}\n\ntd.text-right {  text-align: right;}</style></head><body>";
                String sttable = "<div class=\"table-title\">Country List - Branch: " + filiale + " </div><table class=\"table-fill\"><thead>";
                String thead = "<tr><th class=\"text-left\">Code</th><th class=\"text-left\">Description</th><th class=\"text-left\">ISO Code</th><th class=\"text-left\">Area</th></tr></thead><tbody>";
                String content = "";

                for (int i = 0; i < result.size(); i++) {
                    String[] res = (String[]) result.get(i);

                    content = content + "<tr>\n<td class=\"text-left\">" + res[0]
                            + "</td>\n" + "<td class=\"text-left\">" + res[1]
                            + "</td>\n" + "<td class=\"text-left\">" + res[2]
                            + "</td>\n" + "<td class=\"text-left\">" + formatAL(res[3], array_category_nations, 1);
                    content += "</td>\n" + "</tr>\n";
                }

                String endtable = "</tbody></table>";
                String endbody = "</body></html>";
                String html = header + sttable + thead + content + endtable + endbody;
                InputStream is = new ByteArrayInputStream(html.getBytes());
                getInstance().parseXHtml(wr, document, is);
                document.close();
                wr.close();
                ou.close();
                is.close();
                String base64 = new String(encodeBase64(readFileToByteArray(outputfile)));
                outputfile.delete();
                return base64;
            } catch (IOException | DocumentException ex) {
                insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
            }
        }
        return null;
    }

    /**
     *
     * @param outputfile
     * @param result
     * @return
     */
    public static String pdf_district_list(File outputfile, ArrayList<String[]> result) {
        if (result.size() > 0) {
            try (OutputStream ou = new FileOutputStream(outputfile)) {
                ArrayList<String[]> array_category_nations = category_nations();
                Document document = new Document(A4.rotate(), 20.0F, 20.0F, 20.0F, 20.0F);
                document.addTitle("Mac 2.0");
                document.addAuthor("Seta s.r.l.");
                PdfWriter wr = getInstance(document, ou);
                wr.open();
                document.open();
                String header = "<html><head><title>Mac 2.0</title><style type=\"text/css\">@import url(http://fonts.googleapis.com/css?family=Roboto:400,500,700,300,100);body {font-family: \"Roboto\", helvetica, arial, sans-serif;font-size: 10px;font-weight: 400;text-rendering: optimizeLegibility;}div.table-title {\tfont-family: \"Roboto\", helvetica, arial, sans-serif;\n  text-align:center;\n  margin: auto;\n  max-width: 600px;\n  padding:5px;\n  width: 90%;\n  font-size: 20px;}.table-title h3 {color: #fafafa;font-size: 30px;font-weight: 400;font-style:normal;font-family: \"Roboto\", helvetica, arial, sans-serif;text-shadow: -1px -1px 1px rgba(0, 0, 0, 0.1);text-transform:uppercase;}.table-fill {background: white;border-radius:3px;border-collapse: collapse;height: 320px;margin: auto;max-width: 600px;padding:5px;width: 100%;box-shadow: 0 5px 10px rgba(0, 0, 0, 0.1);animation: float 5s infinite;\n}\n \nth {\n  color:#000000;;\n  background:#91949A;\n  font-size:12px;\n  font-weight: 100;\n  padding:10px;\n  text-align:left;\n  text-shadow: 0 1px 1px rgba(0, 0, 0, 0.1);\n  vertical-align:middle;\n}\n\nth:first-child {\n  border-top-left-radius:3px;\n}\ntr {\n  border-top: 1px solid #C1C3D1;\n  border-bottom-: 1px solid #C1C3D1;\n  color:#666B85;\n  font-size:11px;\n  font-weight:normal;\n  text-shadow: 0 1px 1px rgba(256, 256, 256, 0.1);\n}\n \ntr:hover td {\n  background:#4E5066;\n  color:#FFFFFF;\n  border-top: 1px solid #22262e;\n  border-bottom: 1px solid #22262e;\n}\n \ntr:first-child {\n  border-top:none;\n}\n\ntr:last-child {\n  border-bottom:none;\n}\n \ntr:nth-child(odd) td {\n  background:#EBEBEB;\n}\n \ntr:nth-child(odd):hover td {\n  background:#4E5066;\n}\n\ntr:last-child td:first-child {\n  border-bottom-left-radius:3px;\n}\n \ntr:last-child td:last-child {\n  border-bottom-right-radius:3px;\n}\n \ntd {\n  background:#FFFFFF;\n padding:10;  vertical-align:middle;\n  font-weight:300;\n  font-size:11px;\n  text-shadow: -1px -1px 1px rgba(0, 0, 0, 0.1);\n  border-bottom: 1px solid #C1C3D1;\n\nth.text-left {\n  text-align: left;\n}\n\nth.text-center {\n  text-align: center;\n}\n\nth.text-right {\n  text-align: right;\n}\n\ntd.text-left {\n  text-align: left;\n}\n\ntd.text-center {\n  text-align: center;\n}\n\ntd.text-right {  text-align: right;}</style></head><body>";
                String sttable = "<div class=\"table-title\">District List - Branch: " + filiale + " </div><table class=\"table-fill\"><thead>";
                String thead = "<tr><th class=\"text-left\">Code</th><th class=\"text-left\">Description</th></tr></thead><tbody>";
                String content = "";

                for (int i = 0; i < result.size(); i++) {
                    String[] res = (String[]) result.get(i);

                    content = content + "<tr>\n<td class=\"text-left\">" + res[0]
                            + "</td>\n" + "<td class=\"text-left\">" + res[1];
                    content += "</td>\n" + "</tr>\n";
                }

                String endtable = "</tbody></table>";
                String endbody = "</body></html>";
                String html = header + sttable + thead + content + endtable + endbody;
                InputStream is = new ByteArrayInputStream(html.getBytes());
                getInstance().parseXHtml(wr, document, is);
                document.close();
                wr.close();
                ou.close();
                is.close();
                String base64 = new String(encodeBase64(readFileToByteArray(outputfile)));
                outputfile.delete();
                return base64;
            } catch (IOException | DocumentException ex) {
                insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
            }
        }
        return null;
    }

    /**
     *
     * @param outputfile
     * @param result
     * @return
     */
    public static String pdf_city_list(File outputfile, ArrayList<String[]> result) {
        if (result.size() > 0) {
            try (OutputStream ou = new FileOutputStream(outputfile)) {

                Document document = new Document(A4.rotate(), 20.0F, 20.0F, 20.0F, 20.0F);
                document.addTitle("Mac 2.0");
                document.addAuthor("Seta s.r.l.");
                PdfWriter wr = getInstance(document, ou);
                wr.open();
                document.open();
                String header = "<html><head><title>Mac 2.0</title><style type=\"text/css\">@import url(http://fonts.googleapis.com/css?family=Roboto:400,500,700,300,100);body {font-family: \"Roboto\", helvetica, arial, sans-serif;font-size: 10px;font-weight: 400;text-rendering: optimizeLegibility;}div.table-title {\tfont-family: \"Roboto\", helvetica, arial, sans-serif;\n  text-align:center;\n  margin: auto;\n  max-width: 600px;\n  padding:5px;\n  width: 90%;\n  font-size: 20px;}.table-title h3 {color: #fafafa;font-size: 30px;font-weight: 400;font-style:normal;font-family: \"Roboto\", helvetica, arial, sans-serif;text-shadow: -1px -1px 1px rgba(0, 0, 0, 0.1);text-transform:uppercase;}.table-fill {background: white;border-radius:3px;border-collapse: collapse;height: 320px;margin: auto;max-width: 600px;padding:5px;width: 100%;box-shadow: 0 5px 10px rgba(0, 0, 0, 0.1);animation: float 5s infinite;\n}\n \nth {\n  color:#000000;;\n  background:#91949A;\n  font-size:12px;\n  font-weight: 100;\n  padding:10px;\n  text-align:left;\n  text-shadow: 0 1px 1px rgba(0, 0, 0, 0.1);\n  vertical-align:middle;\n}\n\nth:first-child {\n  border-top-left-radius:3px;\n}\ntr {\n  border-top: 1px solid #C1C3D1;\n  border-bottom-: 1px solid #C1C3D1;\n  color:#666B85;\n  font-size:11px;\n  font-weight:normal;\n  text-shadow: 0 1px 1px rgba(256, 256, 256, 0.1);\n}\n \ntr:hover td {\n  background:#4E5066;\n  color:#FFFFFF;\n  border-top: 1px solid #22262e;\n  border-bottom: 1px solid #22262e;\n}\n \ntr:first-child {\n  border-top:none;\n}\n\ntr:last-child {\n  border-bottom:none;\n}\n \ntr:nth-child(odd) td {\n  background:#EBEBEB;\n}\n \ntr:nth-child(odd):hover td {\n  background:#4E5066;\n}\n\ntr:last-child td:first-child {\n  border-bottom-left-radius:3px;\n}\n \ntr:last-child td:last-child {\n  border-bottom-right-radius:3px;\n}\n \ntd {\n  background:#FFFFFF;\n padding:10;  vertical-align:middle;\n  font-weight:300;\n  font-size:11px;\n  text-shadow: -1px -1px 1px rgba(0, 0, 0, 0.1);\n  border-bottom: 1px solid #C1C3D1;\n\nth.text-left {\n  text-align: left;\n}\n\nth.text-center {\n  text-align: center;\n}\n\nth.text-right {\n  text-align: right;\n}\n\ntd.text-left {\n  text-align: left;\n}\n\ntd.text-center {\n  text-align: center;\n}\n\ntd.text-right {  text-align: right;}</style></head><body>";
                String sttable = "<div class=\"table-title\">City List - Branch: " + filiale + " </div><table class=\"table-fill\"><thead>";
                String thead = "<tr><th class=\"text-left\">Code</th><th class=\"text-left\">Description</th></tr></thead><tbody>";
                String content = "";

                for (int i = 0; i < result.size(); i++) {
                    String[] res = (String[]) result.get(i);

                    content = content + "<tr>\n<td class=\"text-left\">" + res[0]
                            + "</td>\n" + "<td class=\"text-left\">" + res[1];
                    content += "</td>\n" + "</tr>\n";
                }

                String endtable = "</tbody></table>";
                String endbody = "</body></html>";
                String html = header + sttable + thead + content + endtable + endbody;
                InputStream is = new ByteArrayInputStream(html.getBytes());
                getInstance().parseXHtml(wr, document, is);
                document.close();
                wr.close();
                ou.close();
                is.close();
                String base64 = new String(encodeBase64(readFileToByteArray(outputfile)));
                outputfile.delete();
                return base64;
            } catch (IOException | DocumentException ex) {
                insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
            }
        }
        return null;
    }

    /**
     *
     * @param outputfile
     * @param result
     * @return
     */
    public static String pdf_intbook_list(File outputfile, ArrayList<String[]> result) {
        if (result.size() > 0) {
            try (OutputStream ou = new FileOutputStream(outputfile)) {

                Document document = new Document(A4.rotate(), 20.0F, 20.0F, 20.0F, 20.0F);
                document.addTitle("Mac 2.0");
                document.addAuthor("Seta s.r.l.");
                PdfWriter wr = getInstance(document, ou);
                wr.open();
                document.open();
                String header = "<html><head><title>Mac 2.0</title><style type=\"text/css\">@import url(http://fonts.googleapis.com/css?family=Roboto:400,500,700,300,100);body {font-family: \"Roboto\", helvetica, arial, sans-serif;font-size: 10px;font-weight: 400;text-rendering: optimizeLegibility;}div.table-title {\tfont-family: \"Roboto\", helvetica, arial, sans-serif;\n  text-align:center;\n  margin: auto;\n  max-width: 600px;\n  padding:5px;\n  width: 90%;\n  font-size: 20px;}.table-title h3 {color: #fafafa;font-size: 30px;font-weight: 400;font-style:normal;font-family: \"Roboto\", helvetica, arial, sans-serif;text-shadow: -1px -1px 1px rgba(0, 0, 0, 0.1);text-transform:uppercase;}.table-fill {background: white;border-radius:3px;border-collapse: collapse;height: 320px;margin: auto;max-width: 600px;padding:5px;width: 100%;box-shadow: 0 5px 10px rgba(0, 0, 0, 0.1);animation: float 5s infinite;\n}\n \nth {\n  color:#000000;;\n  background:#91949A;\n  font-size:12px;\n  font-weight: 100;\n  padding:10px;\n  text-align:left;\n  text-shadow: 0 1px 1px rgba(0, 0, 0, 0.1);\n  vertical-align:middle;\n}\n\nth:first-child {\n  border-top-left-radius:3px;\n}\ntr {\n  border-top: 1px solid #C1C3D1;\n  border-bottom-: 1px solid #C1C3D1;\n  color:#666B85;\n  font-size:11px;\n  font-weight:normal;\n  text-shadow: 0 1px 1px rgba(256, 256, 256, 0.1);\n}\n \ntr:hover td {\n  background:#4E5066;\n  color:#FFFFFF;\n  border-top: 1px solid #22262e;\n  border-bottom: 1px solid #22262e;\n}\n \ntr:first-child {\n  border-top:none;\n}\n\ntr:last-child {\n  border-bottom:none;\n}\n \ntr:nth-child(odd) td {\n  background:#EBEBEB;\n}\n \ntr:nth-child(odd):hover td {\n  background:#4E5066;\n}\n\ntr:last-child td:first-child {\n  border-bottom-left-radius:3px;\n}\n \ntr:last-child td:last-child {\n  border-bottom-right-radius:3px;\n}\n \ntd {\n  background:#FFFFFF;\n padding:10;  vertical-align:middle;\n  font-weight:300;\n  font-size:11px;\n  text-shadow: -1px -1px 1px rgba(0, 0, 0, 0.1);\n  border-bottom: 1px solid #C1C3D1;\n\nth.text-left {\n  text-align: left;\n}\n\nth.text-center {\n  text-align: center;\n}\n\nth.text-right {\n  text-align: right;\n}\n\ntd.text-left {\n  text-align: left;\n}\n\ntd.text-center {\n  text-align: center;\n}\n\ntd.text-right {  text-align: right;}</style></head><body>";
                String sttable = "<div class=\"table-title\">Internet Booking List - Branch: " + filiale + " </div><table class=\"table-fill\"><thead>";
                String thead = "<tr><th class=\"text-left\">Code</th><th class=\"text-left\">Description</th></tr></thead><tbody>";
                String content = "";

                for (int i = 0; i < result.size(); i++) {
                    String[] res = (String[]) result.get(i);

                    content = content + "<tr>\n<td class=\"text-left\">" + res[0]
                            + "</td>\n" + "<td class=\"text-left\">" + res[1];
                    content += "</td>\n" + "</tr>\n";
                }

                String endtable = "</tbody></table>";
                String endbody = "</body></html>";
                String html = header + sttable + thead + content + endtable + endbody;
                InputStream is = new ByteArrayInputStream(html.getBytes());
                getInstance().parseXHtml(wr, document, is);
                document.close();
                wr.close();
                ou.close();
                is.close();
                String base64 = new String(encodeBase64(readFileToByteArray(outputfile)));
                outputfile.delete();
                return base64;
            } catch (IOException | DocumentException ex) {
                insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
            }
        }
        return null;
    }

    /**
     *
     * @param outputfile
     * @param result
     * @return
     */
    public static String pdf_agency_list(File outputfile, ArrayList<Agency> result) {
        if (result.size() > 0) {
            try (OutputStream ou= new FileOutputStream(outputfile)) {

                Document document = new Document(A4.rotate(), 20.0F, 20.0F, 20.0F, 20.0F);
                document.addTitle("Mac 2.0");
                document.addAuthor("Seta s.r.l.");
                PdfWriter wr = getInstance(document, ou);
                wr.open();
                document.open();
                String header = "<html><head><title>Mac 2.0</title><style type=\"text/css\">@import url(http://fonts.googleapis.com/css?family=Roboto:400,500,700,300,100);body {font-family: \"Roboto\", helvetica, arial, sans-serif;font-size: 10px;font-weight: 400;text-rendering: optimizeLegibility;}div.table-title {\tfont-family: \"Roboto\", helvetica, arial, sans-serif;\n  text-align:center;\n  margin: auto;\n  max-width: 600px;\n  padding:5px;\n  width: 90%;\n  font-size: 20px;}.table-title h3 {color: #fafafa;font-size: 30px;font-weight: 400;font-style:normal;font-family: \"Roboto\", helvetica, arial, sans-serif;text-shadow: -1px -1px 1px rgba(0, 0, 0, 0.1);text-transform:uppercase;}.table-fill {background: white;border-radius:3px;border-collapse: collapse;height: 320px;margin: auto;max-width: 600px;padding:5px;width: 100%;box-shadow: 0 5px 10px rgba(0, 0, 0, 0.1);animation: float 5s infinite;\n}\n \nth {\n  color:#000000;;\n  background:#91949A;\n  font-size:12px;\n  font-weight: 100;\n  padding:10px;\n  text-align:left;\n  text-shadow: 0 1px 1px rgba(0, 0, 0, 0.1);\n  vertical-align:middle;\n}\n\nth:first-child {\n  border-top-left-radius:3px;\n}\ntr {\n  border-top: 1px solid #C1C3D1;\n  border-bottom-: 1px solid #C1C3D1;\n  color:#666B85;\n  font-size:11px;\n  font-weight:normal;\n  text-shadow: 0 1px 1px rgba(256, 256, 256, 0.1);\n}\n \ntr:hover td {\n  background:#4E5066;\n  color:#FFFFFF;\n  border-top: 1px solid #22262e;\n  border-bottom: 1px solid #22262e;\n}\n \ntr:first-child {\n  border-top:none;\n}\n\ntr:last-child {\n  border-bottom:none;\n}\n \ntr:nth-child(odd) td {\n  background:#EBEBEB;\n}\n \ntr:nth-child(odd):hover td {\n  background:#4E5066;\n}\n\ntr:last-child td:first-child {\n  border-bottom-left-radius:3px;\n}\n \ntr:last-child td:last-child {\n  border-bottom-right-radius:3px;\n}\n \ntd {\n  background:#FFFFFF;\n padding:10;  vertical-align:middle;\n  font-weight:300;\n  font-size:11px;\n  text-shadow: -1px -1px 1px rgba(0, 0, 0, 0.1);\n  border-bottom: 1px solid #C1C3D1;\n\nth.text-left {\n  text-align: left;\n}\n\nth.text-center {\n  text-align: center;\n}\n\nth.text-right {\n  text-align: right;\n}\n\ntd.text-left {\n  text-align: left;\n}\n\ntd.text-center {\n  text-align: center;\n}\n\ntd.text-right {  text-align: right;}</style></head><body>";
                String sttable = "<div class=\"table-title\">Agency List - Branch: " + filiale + " </div><table class=\"table-fill\"><thead>";
                String thead = "<tr><th class=\"text-left\">Code</th>"
                        + "<th class=\"text-left\">Description</th>"
                        + "<th class=\"text-left\">Address</th>"
                        + "<th class=\"text-left\">Zip Code</th>"
                        + "<th class=\"text-left\">City</th>"
                        + "<th class=\"text-left\">Phone number</th>"
                        + "<th class=\"text-left\">Fax number</th>"
                        + "<th class=\"text-left\">Email address</th>"
                        + "<th class=\"text-left\">Status</th>"
                        + "</tr></thead><tbody>";
                String content = "";

                for (int i = 0; i < result.size(); i++) {
                    Agency res = (Agency) result.get(i);

                    content = content + "<tr>\n<td class=\"text-left\">" + res.getAgenzia()
                            + "</td>\n" + "<td class=\"text-left\">" + res.getDe_agenzia()
                            + "</td>\n" + "<td class=\"text-left\">" + res.getIndirizzo()
                            + "</td>\n" + "<td class=\"text-left\">" + res.getCap()
                            + "</td>\n" + "<td class=\"text-left\">" + res.getCitta()
                            + "</td>\n" + "<td class=\"text-left\">" + res.getTelefono()
                            + "</td>\n" + "<td class=\"text-left\">" + res.getFax()
                            + "</td>\n" + "<td class=\"text-left\">" + res.getEmail()
                            + "</td>\n" + "<td class=\"text-left\">" + formatStatus_general(res.getFg_annullato());
                    content += "</td>\n" + "</tr>\n";
                }

                String endtable = "</tbody></table>";
                String endbody = "</body></html>";
                String html = header + sttable + thead + content + endtable + endbody;
                InputStream is = new ByteArrayInputStream(html.getBytes());
                getInstance().parseXHtml(wr, document, is);
                document.close();
                wr.close();
                ou.close();
                is.close();
                String base64 = new String(encodeBase64(readFileToByteArray(outputfile)));
                outputfile.delete();
                return base64;
            } catch (IOException | DocumentException ex) {
                insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
            }
        }
        return null;
    }

    /**
     *
     * @param outputfile
     * @param result
     * @return
     */
    public static String pdf_blackm_list(File outputfile, ArrayList<BlacklistM> result) {
        if (result.size() > 0) {
            try (OutputStream ou = new FileOutputStream(outputfile)) {

                Document document = new Document(A3.rotate(), 20.0F, 20.0F, 20.0F, 20.0F);
                document.addTitle("Mac 2.0");
                document.addAuthor("Seta s.r.l.");
                PdfWriter wr = getInstance(document, ou);
                wr.open();
                document.open();
                String header = "<html><head><title>Mac 2.0</title><style type=\"text/css\">@import url(http://fonts.googleapis.com/css?family=Roboto:400,500,700,300,100);body {font-family: \"Roboto\", helvetica, arial, sans-serif;font-size: 10px;font-weight: 400;text-rendering: optimizeLegibility;}div.table-title {\tfont-family: \"Roboto\", helvetica, arial, sans-serif;\n  text-align:center;\n  margin: auto;\n  max-width: 600px;\n  padding:5px;\n  width: 90%;\n  font-size: 20px;}.table-title h3 {color: #fafafa;font-size: 30px;font-weight: 400;font-style:normal;font-family: \"Roboto\", helvetica, arial, sans-serif;text-shadow: -1px -1px 1px rgba(0, 0, 0, 0.1);text-transform:uppercase;}.table-fill {background: white;border-radius:3px;border-collapse: collapse;height: 320px;margin: auto;max-width: 600px;padding:5px;width: 100%;box-shadow: 0 5px 10px rgba(0, 0, 0, 0.1);animation: float 5s infinite;\n}\n \nth {\n  color:#000000;;\n  background:#91949A;\n  font-size:12px;\n  font-weight: 100;\n  padding:10px;\n  text-align:left;\n  text-shadow: 0 1px 1px rgba(0, 0, 0, 0.1);\n  vertical-align:middle;\n}\n\nth:first-child {\n  border-top-left-radius:3px;\n}\ntr {\n  border-top: 1px solid #C1C3D1;\n  border-bottom-: 1px solid #C1C3D1;\n  color:#666B85;\n  font-size:11px;\n  font-weight:normal;\n  text-shadow: 0 1px 1px rgba(256, 256, 256, 0.1);\n}\n \ntr:hover td {\n  background:#4E5066;\n  color:#FFFFFF;\n  border-top: 1px solid #22262e;\n  border-bottom: 1px solid #22262e;\n}\n \ntr:first-child {\n  border-top:none;\n}\n\ntr:last-child {\n  border-bottom:none;\n}\n \ntr:nth-child(odd) td {\n  background:#EBEBEB;\n}\n \ntr:nth-child(odd):hover td {\n  background:#4E5066;\n}\n\ntr:last-child td:first-child {\n  border-bottom-left-radius:3px;\n}\n \ntr:last-child td:last-child {\n  border-bottom-right-radius:3px;\n}\n \ntd {\n  background:#FFFFFF;\n padding:10;  vertical-align:middle;\n  font-weight:300;\n  font-size:11px;\n  text-shadow: -1px -1px 1px rgba(0, 0, 0, 0.1);\n  border-bottom: 1px solid #C1C3D1;\n\nth.text-left {\n  text-align: left;\n}\n\nth.text-center {\n  text-align: center;\n}\n\nth.text-right {\n  text-align: right;\n}\n\ntd.text-left {\n  text-align: left;\n}\n\ntd.text-center {\n  text-align: center;\n}\n\ntd.text-right {  text-align: right;}</style></head><body>";
                String sttable = "<div class=\"table-title\">BlackList Maccorp - Branch: " + filiale + "  </div><table class=\"table-fill\"><thead>";
                String thead = "<tr>"
                        + "<th class=\"text-center\" colspan='11' style=' text-align:center;'> Basic </th>"
                        + "<th class=\"text-center\" colspan='3' style=' text-align:center;'> Place Of Birth </th>"
                        + "<th class=\"text-center\" colspan='6' style=' text-align:center;'>Dcument Identity </th>"
                        + "<th class=\"text-center\">Message </th>"
                        + "</tr>"
                        + "<tr>"
                        + "<th class=\"text-left\">Status</th>"
                        + "<th class=\"text-left\">Code</th>"
                        + "<th class=\"text-left\">Surname</th>"
                        + "<th class=\"text-left\">Name</th>"
                        + "<th class=\"text-left\">Sex</th>"
                        + "<th class=\"text-left\">Tax Code</th>"
                        + "<th class=\"text-left\">Country</th>"
                        + "<th class=\"text-left\">City</th>"
                        + "<th class=\"text-left\">Address</th>"
                        + "<th class=\"text-left\">Email</th>"
                        + "<th class=\"text-left\">Phone Number</th>"
                        + "<th class=\"text-left\">City Birth</th>"
                        + "<th class=\"text-left\">Country Birth</th>"
                        + "<th class=\"text-left\">Date Birth</th>"
                        + "<th class=\"text-left\">Identification Card</th>"
                        + "<th class=\"text-left\">Number</th>"
                        + "<th class=\"text-left\">Issue Date</th>"
                        + "<th class=\"text-left\">Expiration Date</th>"
                        + "<th class=\"text-left\">Issued By</th>"
                        + "<th class=\"text-left\">Place of Issue</th>"
                        + "<th class=\"text-left\">Message</th>"
                        + "</tr></thead><tbody>";
                String content = "";

                for (int i = 0; i < result.size(); i++) {
                    BlacklistM res = (BlacklistM) result.get(i);

                    content = content + "<tr>\n<td class=\"text-left\">" + formatStatus_general(res.getFg_annullato())
                            + "</td>\n" + "<td class=\"text-left\">" + res.getCode()
                            + "</td>\n" + "<td class=\"text-left\">" + res.getCognome()
                            + "</td>\n" + "<td class=\"text-left\">" + res.getNome()
                            + "</td>\n" + "<td class=\"text-left\">" + res.getSesso()
                            + "</td>\n" + "<td class=\"text-left\">" + res.getCodfisc()
                            + "</td>\n" + "<td class=\"text-left\">" + res.getNazione()
                            + "</td>\n" + "<td class=\"text-left\">" + res.getCitta()
                            + "</td>\n" + "<td class=\"text-left\">" + res.getIndirizzo()
                            + "</td>\n" + "<td class=\"text-left\">" + res.getEmail()
                            + "</td>\n" + "<td class=\"text-left\">" + res.getTelefono()
                            + "</td>\n" + "<td class=\"text-left\">" + res.getCitta_nascita()
                            + "</td>\n" + "<td class=\"text-left\">" + res.getNazione_nascita()
                            + "</td>\n" + "<td class=\"text-left\">" + res.getDt_nascita()
                            + "</td>\n" + "<td class=\"text-left\">" + res.getTipo_documento()
                            + "</td>\n" + "<td class=\"text-left\">" + res.getNumero_documento()
                            + "</td>\n" + "<td class=\"text-left\">" + res.getDt_rilascio_documento()
                            + "</td>\n" + "<td class=\"text-left\">" + res.getDt_scadenza_documento()
                            + "</td>\n" + "<td class=\"text-left\">" + res.getRilasciato_da_documento()
                            + "</td>\n" + "<td class=\"text-left\">" + res.getLuogo_rilascio_documento()
                            + "</td>\n" + "<td class=\"text-left\">" + res.getText();

                    content += "</td>\n" + "</tr>\n";
                }

                String endtable = "</tbody></table>";
                String endbody = "</body></html>";
                String html = header + sttable + thead + content + endtable + endbody;
                InputStream is = new ByteArrayInputStream(html.getBytes());
                getInstance().parseXHtml(wr, document, is);
                document.close();
                wr.close();
                ou.close();
                is.close();
                String base64 = new String(encodeBase64(readFileToByteArray(outputfile)));
                outputfile.delete();
                return base64;
            } catch (IOException | DocumentException ex) {
                insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
            }
        }
        return null;
    }

    /**
     *
     * @param outputfile
     * @param result
     * @return
     */
    public static String pdf_branch_list(File outputfile, ArrayList<Branch> result) {
        if (result.size() > 0) {
            try (OutputStream ou = new FileOutputStream(outputfile)) {

                Document document = new Document(A4.rotate(), 20.0F, 20.0F, 20.0F, 20.0F);
                document.addTitle("Mac 2.0");
                document.addAuthor("Seta s.r.l.");
                PdfWriter wr = getInstance(document, ou);
                wr.open();
                document.open();
                String header = "<html><head><title>Mac 2.0</title><style type=\"text/css\">@import url(http://fonts.googleapis.com/css?family=Roboto:400,500,700,300,100);body {font-family: \"Roboto\", helvetica, arial, sans-serif;font-size: 10px;font-weight: 400;text-rendering: optimizeLegibility;}div.table-title {\tfont-family: \"Roboto\", helvetica, arial, sans-serif;\n  text-align:center;\n  margin: auto;\n  max-width: 600px;\n  padding:5px;\n  width: 90%;\n  font-size: 20px;}.table-title h3 {color: #fafafa;font-size: 30px;font-weight: 400;font-style:normal;font-family: \"Roboto\", helvetica, arial, sans-serif;text-shadow: -1px -1px 1px rgba(0, 0, 0, 0.1);text-transform:uppercase;}.table-fill {background: white;border-radius:3px;border-collapse: collapse;height: 320px;margin: auto;max-width: 600px;padding:5px;width: 100%;box-shadow: 0 5px 10px rgba(0, 0, 0, 0.1);animation: float 5s infinite;\n}\n \nth {\n  color:#000000;;\n  background:#91949A;\n  font-size:12px;\n  font-weight: 100;\n  padding:10px;\n  text-align:left;\n  text-shadow: 0 1px 1px rgba(0, 0, 0, 0.1);\n  vertical-align:middle;\n}\n\nth:first-child {\n  border-top-left-radius:3px;\n}\ntr {\n  border-top: 1px solid #C1C3D1;\n  border-bottom-: 1px solid #C1C3D1;\n  color:#666B85;\n  font-size:11px;\n  font-weight:normal;\n  text-shadow: 0 1px 1px rgba(256, 256, 256, 0.1);\n}\n \ntr:hover td {\n  background:#4E5066;\n  color:#FFFFFF;\n  border-top: 1px solid #22262e;\n  border-bottom: 1px solid #22262e;\n}\n \ntr:first-child {\n  border-top:none;\n}\n\ntr:last-child {\n  border-bottom:none;\n}\n \ntr:nth-child(odd) td {\n  background:#EBEBEB;\n}\n \ntr:nth-child(odd):hover td {\n  background:#4E5066;\n}\n\ntr:last-child td:first-child {\n  border-bottom-left-radius:3px;\n}\n \ntr:last-child td:last-child {\n  border-bottom-right-radius:3px;\n}\n \ntd {\n  background:#FFFFFF;\n padding:10;  vertical-align:middle;\n  font-weight:300;\n  font-size:11px;\n  text-shadow: -1px -1px 1px rgba(0, 0, 0, 0.1);\n  border-bottom: 1px solid #C1C3D1;\n\nth.text-left {\n  text-align: left;\n}\n\nth.text-center {\n  text-align: center;\n}\n\nth.text-right {\n  text-align: right;\n}\n\ntd.text-left {\n  text-align: left;\n}\n\ntd.text-center {\n  text-align: center;\n}\n\ntd.text-right {  text-align: right;}</style></head><body>";
                String sttable = "<div class=\"table-title\">Branch List - Branch: " + filiale + " </div><table class=\"table-fill\"><thead>";
                String thead = "<tr><th class=\"text-left\">Code</th><th class=\"text-left\">Description</th><th class=\"text-left\">Status</th><th class=\"text-left\">Address</th><th class=\"text-left\"City</th><th class=\"text-left\">Zip Code</th><th class=\"text-left\">Company Operation</th><th class=\"text-left\">Edit Rate</th><th class=\"text-left\">CRM</th><th class=\"text-left\">Distinct Collection Center</th><th class=\"text-left\">Group 01</th><th class=\"text-left\">Group 02</th><th class=\"text-left\">Group 03</th><th class=\"text-left\">Date Of Cancellation</th></tr></thead><tbody>";
                String content = "";

                for (int i = 0; i < result.size(); i++) {
                    Branch res = (Branch) result.get(i);

                    content = content + "<tr>\n<td class=\"text-left\">" + res.getCod() + "</td>\n"
                            + "<td class=\"text-left\">" + res.getDe_branch()
                            + "</td>\n" + "<td class=\"text-left\">" + formatStatus_general_cru(res.getFg_annullato())
                            + "</td>\n" + "<td class=\"text-left\">" + res.getAdd_via()
                            + "</td>\n" + "<td class=\"text-left\">" + res.getAdd_city()
                            + "</td>\n" + "<td class=\"text-left\">" + res.getAdd_cap();

                    if (res.getFg_persgiur().equals("0")) {
                        content += "</td>\n" + "<td class=\"text-left\">" + "Disabled";
                    } else {
                        content += "</td>\n" + "<td class=\"text-left\">" + "Enabled";
                    }
                    if (res.getFg_modrate().equals("0")) {
                        content += "</td>\n" + "<td class=\"text-left\">" + "Disabled";
                    } else {
                        content += "</td>\n" + "<td class=\"text-left\">" + "Enabled";
                    }
                    if (res.getFg_crm().equals("0")) {
                        content += "</td>\n" + "<td class=\"text-left\">" + "Disabled";
                    } else {
                        content += "</td>\n" + "<td class=\"text-left\">" + "Enabled";
                    }

                    content += "</td>\n" + "<td class=\"text-left\">" + res.getProv_raccval()
                            + "</td>\n" + "<td class=\"text-left\">" + res.getG01()
                            + "</td>\n" + "<td class=\"text-left\">" + res.getG02()
                            + "</td>\n" + "<td class=\"text-left\">" + res.getG03()
                            + "</td>\n" + "<td class=\"text-left\">" + res.getDa_annull();

                    content += "</td>\n" + "</tr>\n";
                }

                String endtable = "</tbody></table>";
                String endbody = "</body></html>";
                String html = header + sttable + thead + content + endtable + endbody;
                InputStream is = new ByteArrayInputStream(html.getBytes());
                getInstance().parseXHtml(wr, document, is);
                document.close();
                wr.close();
                ou.close();
                is.close();
                String base64 = new String(encodeBase64(readFileToByteArray(outputfile)));
                outputfile.delete();
                return base64;
            } catch (IOException | DocumentException ex) {
                insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
            }
        }
        return null;
    }

    /**
     *
     * @param outputfile
     * @param result
     * @return
     */
    public static String pdf_vatcode_list(File outputfile, ArrayList<VATcode> result) {
        if (result.size() > 0) {
            try (OutputStream ou = new FileOutputStream(outputfile)) {

                Document document = new Document(A4.rotate(), 20.0F, 20.0F, 20.0F, 20.0F);
                document.addTitle("Mac 2.0");
                document.addAuthor("Seta s.r.l.");
                PdfWriter wr = getInstance(document, ou);
                wr.open();
                document.open();
                String header = "<html><head><title>Mac 2.0</title><style type=\"text/css\">@import url(http://fonts.googleapis.com/css?family=Roboto:400,500,700,300,100);body {font-family: \"Roboto\", helvetica, arial, sans-serif;font-size: 10px;font-weight: 400;text-rendering: optimizeLegibility;}div.table-title {\tfont-family: \"Roboto\", helvetica, arial, sans-serif;\n  text-align:center;\n  margin: auto;\n  max-width: 600px;\n  padding:5px;\n  width: 90%;\n  font-size: 20px;}.table-title h3 {color: #fafafa;font-size: 30px;font-weight: 400;font-style:normal;font-family: \"Roboto\", helvetica, arial, sans-serif;text-shadow: -1px -1px 1px rgba(0, 0, 0, 0.1);text-transform:uppercase;}.table-fill {background: white;border-radius:3px;border-collapse: collapse;height: 320px;margin: auto;max-width: 600px;padding:5px;width: 100%;box-shadow: 0 5px 10px rgba(0, 0, 0, 0.1);animation: float 5s infinite;\n}\n \nth {\n  color:#000000;;\n  background:#91949A;\n  font-size:12px;\n  font-weight: 100;\n  padding:10px;\n  text-align:left;\n  text-shadow: 0 1px 1px rgba(0, 0, 0, 0.1);\n  vertical-align:middle;\n}\n\nth:first-child {\n  border-top-left-radius:3px;\n}\ntr {\n  border-top: 1px solid #C1C3D1;\n  border-bottom-: 1px solid #C1C3D1;\n  color:#666B85;\n  font-size:11px;\n  font-weight:normal;\n  text-shadow: 0 1px 1px rgba(256, 256, 256, 0.1);\n}\n \ntr:hover td {\n  background:#4E5066;\n  color:#FFFFFF;\n  border-top: 1px solid #22262e;\n  border-bottom: 1px solid #22262e;\n}\n \ntr:first-child {\n  border-top:none;\n}\n\ntr:last-child {\n  border-bottom:none;\n}\n \ntr:nth-child(odd) td {\n  background:#EBEBEB;\n}\n \ntr:nth-child(odd):hover td {\n  background:#4E5066;\n}\n\ntr:last-child td:first-child {\n  border-bottom-left-radius:3px;\n}\n \ntr:last-child td:last-child {\n  border-bottom-right-radius:3px;\n}\n \ntd {\n  background:#FFFFFF;\n padding:10;  vertical-align:middle;\n  font-weight:300;\n  font-size:11px;\n  text-shadow: -1px -1px 1px rgba(0, 0, 0, 0.1);\n  border-bottom: 1px solid #C1C3D1;\n\nth.text-left {\n  text-align: left;\n}\n\nth.text-center {\n  text-align: center;\n}\n\nth.text-right {\n  text-align: right;\n}\n\ntd.text-left {\n  text-align: left;\n}\n\ntd.text-center {\n  text-align: center;\n}\n\ntd.text-right {  text-align: right;}</style></head><body>";
                String sttable = "<div class=\"table-title\">VAT Code List - Branch: " + filiale + " </div><table class=\"table-fill\"><thead>";
                String thead = "<tr><th class=\"text-left\">Code</th><th class=\"text-left\">Description</th><th class=\"text-left\">Rate</th><th class=\"text-left\">Status</th></tr></thead><tbody>";
                String content = "";

                for (int i = 0; i < result.size(); i++) {
                    VATcode res = (VATcode) result.get(i);

                    content = content + "<tr>\n<td class=\"text-left\">" + res.getCodice() + "</td>\n"
                            + "<td class=\"text-left\">" + res.getDescrizione()
                            + "</td>\n" + "<td class=\"text-left\">" + formatMysqltoDisplay(res.getAliquota())
                            + "</td>\n" + "<td class=\"text-left\">" + formatStatus_general(res.getFg_annullato());

                    content += "</td>\n" + "</tr>\n";
                }

                String endtable = "</tbody></table>";
                String endbody = "</body></html>";
                String html = header + sttable + thead + content + endtable + endbody;
                InputStream is = new ByteArrayInputStream(html.getBytes());
                getInstance().parseXHtml(wr, document, is);
                document.close();
                wr.close();
                ou.close();
                is.close();
                String base64 = new String(encodeBase64(readFileToByteArray(outputfile)));
                outputfile.delete();
                return base64;
            } catch (IOException | DocumentException ex) {
                insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
            }
        }
        return null;
    }

    /**
     *
     * @param outputfile
     * @param result
     * @param resultagent
     * @return
     */
    public static String pdf_company_list(File outputfile, ArrayList<Company> result, ArrayList<Company> resultagent) {
        if (result.size() > 0) {
            try (OutputStream ou = new FileOutputStream(outputfile)) {
                ArrayList<String[]> country = country();
                Document document = new Document(A3.rotate(), 20.0F, 20.0F, 20.0F, 20.0F);
                document.addTitle("Mac 2.0");
                document.addAuthor("Seta s.r.l.");
                PdfWriter wr = getInstance(document, ou);
                wr.open();
                document.open();
                String header = "<html><head><title>Mac 2.0</title><style type=\"text/css\">@import url(http://fonts.googleapis.com/css?family=Roboto:400,500,700,300,100);body {font-family: \"Roboto\", helvetica, arial, sans-serif;font-size: 10px;font-weight: 400;text-rendering: optimizeLegibility;}div.table-title {\tfont-family: \"Roboto\", helvetica, arial, sans-serif;\n  text-align:center;\n  margin: auto;\n  max-width: 600px;\n  padding:5px;\n  width: 90%;\n  font-size: 20px;}.table-title h3 {color: #fafafa;font-size: 30px;font-weight: 400;font-style:normal;font-family: \"Roboto\", helvetica, arial, sans-serif;text-shadow: -1px -1px 1px rgba(0, 0, 0, 0.1);text-transform:uppercase;}.table-fill {background: white;border-radius:3px;border-collapse: collapse;height: 320px;margin: auto;max-width: 600px;padding:5px;width: 100%;box-shadow: 0 5px 10px rgba(0, 0, 0, 0.1);animation: float 5s infinite;\n}\n \nth {\n  color:#000000;;\n  background:#91949A;\n  font-size:12px;\n  font-weight: 100;\n  padding:10px;\n  text-align:left;\n  text-shadow: 0 1px 1px rgba(0, 0, 0, 0.1);\n  vertical-align:middle;\n}\n\nth:first-child {\n  border-top-left-radius:3px;\n}\ntr {\n  border-top: 1px solid #C1C3D1;\n  border-bottom-: 1px solid #C1C3D1;\n  color:#666B85;\n  font-size:11px;\n  font-weight:normal;\n  text-shadow: 0 1px 1px rgba(256, 256, 256, 0.1);\n}\n \ntr:hover td {\n  background:#4E5066;\n  color:#FFFFFF;\n  border-top: 1px solid #22262e;\n  border-bottom: 1px solid #22262e;\n}\n \ntr:first-child {\n  border-top:none;\n}\n\ntr:last-child {\n  border-bottom:none;\n}\n \ntr:nth-child(odd) td {\n  background:#EBEBEB;\n}\n \ntr:nth-child(odd):hover td {\n  background:#4E5066;\n}\n\ntr:last-child td:first-child {\n  border-bottom-left-radius:3px;\n}\n \ntr:last-child td:last-child {\n  border-bottom-right-radius:3px;\n}\n \ntd {\n  background:#FFFFFF;\n padding:10;  vertical-align:middle;\n  font-weight:300;\n  font-size:11px;\n  text-shadow: -1px -1px 1px rgba(0, 0, 0, 0.1);\n  border-bottom: 1px solid #C1C3D1;\n\nth.text-left {\n  text-align: left;\n}\n\nth.text-center {\n  text-align: center;\n}\n\nth.text-right {\n  text-align: right;\n}\n\ntd.text-left {\n  text-align: left;\n}\n\ntd.text-center {\n  text-align: center;\n}\n\ntd.text-right {  text-align: right;}</style></head><body>";
                String sttable = "<div class=\"table-title\">Company List - Branch: " + filiale + " </div><table class=\"table-fill\">";

                String thead;
                String theadagent;
                String content;
                String contentagent;
                String htmltemp = "";
                String endtable = "</tbody>";
                String endbody = "</table></body></html>";
                for (int i = 0; i < result.size(); i++) {
                    Company res = (Company) result.get(i);

                    thead = "<tr><th colspan='19' style='text-align:center;'>Company</th></tr>"
                            + "<tr>"
                            + "<th class=\"text-left\" colspan='3'></th>"
                            + "<th class=\"text-left\">Status</th>"
                            + "<th class=\"text-left\">Code</th>"
                            + "<th class=\"text-left\">Description</th>"
                            + "<th class=\"text-left\">VAT Code</th>"
                            + "<th class=\"text-left\">Address</th>"
                            + "<th class=\"text-left\">Zip Code</th>"
                            + "<th class=\"text-left\">District</th>"
                            + "<th class=\"text-left\">CAB City</th>"
                            + "<th class=\"text-left\" >Country</th>"
                            + "<th class=\"text-left\" colspan='7'></th>"
                            + "</tr><tbody>";

                    content
                            = "<tr>\n <td  colspan='3'>"
                            + "</td>\n <td class=\"text-left\" >" + formatStatus_general(res.getFg_annullato())
                            + "</td>\n" + "<td class=\"text-left\">" + res.getNdg()
                            + "</td>\n" + "<td class=\"text-left\">" + res.getRagione_sociale()
                            + "</td>\n" + "<td class=\"text-left\">" + res.getCodice_fiscale()
                            + "</td>\n" + "<td class=\"text-left\">" + res.getIndirizzo()
                            + "</td>\n" + "<td class=\"text-left\">" + res.getCap()
                            + "</td>\n" + "<td class=\"text-left\">" + res.getProvincia()
                            + "</td>\n" + "<td class=\"text-left\">" + res.getCab_comune() + " - " + res.getCitta()
                            + "</td>\n" + "<td class=\"text-left\">" + formatCountry_cru(res.getPaese_estero_residenza(), country)
                            + "</td>\n" + "<td  colspan='7'>";
                    content += "</td>\n" + "</tr>\n";

                    theadagent = "";
                    contentagent = "";
                    boolean first = true;
                    for (int j = 0; j < resultagent.size(); j++) {
                        Company ag1 = (Company) resultagent.get(i);
                        if (resultagent.get(j).getNdg_rappresentante().equals(res.getNdg())) {
                            if (first) {
                                theadagent = "<tr><th colspan='19'style='text-align:center;background:#B8B9C1;'>Agent</th></tr>"
                                        + "<tr>"
                                        + "<th class=\"text-left\" style='background:#B8B9C1;'>Code</th>"
                                        + "<th class=\"text-left\" style='background:#B8B9C1;'>Surname</th>"
                                        + "<th class=\"text-left\" style='background:#B8B9C1;'>Name</th>"
                                        + "<th class=\"text-left\" style='background:#B8B9C1;'>Sex</th>"
                                        + "<th class=\"text-left\" style='background:#B8B9C1;'>Tax Code</th>"
                                        + "<th class=\"text-left\" style='background:#B8B9C1;'>Address</th>"
                                        + "<th class=\"text-left\" style='background:#B8B9C1;'>Zip Code</th>"
                                        + "<th class=\"text-left\" style='background:#B8B9C1;'>District</th>"
                                        + "<th class=\"text-left\" style='background:#B8B9C1;'>CAB - City</th>"
                                        + "<th class=\"text-left\" style='background:#B8B9C1;'>Country</th>"
                                        + "<th class=\"text-left\" style='background:#B8B9C1;'>Date Of Birth</th>"
                                        + "<th class=\"text-left\" style='background:#B8B9C1;'>City Of Birth</th>"
                                        + "<th class=\"text-left\" style='background:#B8B9C1;'>District Of Birth</th>"
                                        + "<th class=\"text-left\" style='background:#B8B9C1;'>Document IDentity</th>"
                                        + "<th class=\"text-left\" style='background:#B8B9C1;'>Number Doc ID</th>"
                                        + "<th class=\"text-left\" style='background:#B8B9C1;'>Issue Date</th>"
                                        + "<th class=\"text-left\" style='background:#B8B9C1;'>Expiration Date</th>"
                                        + "<th class=\"text-left\" style='background:#B8B9C1;'>Issued by</th>"
                                        + "<th class=\"text-left\" style='background:#B8B9C1;'>Place OF issue</th>"
                                        + "</tr>";
                                first = false;
                            }
                            contentagent += "<tr>\n<td class=\"text-left\">" + ag1.getNdg()
                                    + "</td>\n" + "<td class=\"text-left\">" + ag1.getCognome()
                                    + "</td>\n" + "<td class=\"text-left\">" + ag1.getNome()
                                    + "</td>\n" + "<td class=\"text-left\">" + ag1.getSesso()
                                    + "</td>\n" + "<td class=\"text-left\">" + ag1.getCodice_fiscale()
                                    + "</td>\n" + "<td class=\"text-left\">" + ag1.getIndirizzo()
                                    + "</td>\n" + "<td class=\"text-left\">" + ag1.getCap()
                                    + "</td>\n" + "<td class=\"text-left\">" + ag1.getProvincia()
                                    + "</td>\n" + "<td class=\"text-left\">" + ag1.getCab_comune()
                                    + "</td>\n" + "<td class=\"text-left\">" + ag1.getPaese_estero_residenza()
                                    + "</td>\n" + "<td class=\"text-left\">" + ag1.getDt_nascita()
                                    + "</td>\n" + "<td class=\"text-left\">" + ag1.getComune_nascita()
                                    + "</td>\n" + "<td class=\"text-left\">" + ag1.getCod_provincia_nascita()
                                    + "</td>\n" + "<td class=\"text-left\">" + ag1.getTipo_documento()
                                    + "</td>\n" + "<td class=\"text-left\">" + ag1.getNumero_documento()
                                    + "</td>\n" + "<td class=\"text-left\">" + ag1.getDt_rilascio()
                                    + "</td>\n" + "<td class=\"text-left\">" + ag1.getDt_scadenza()
                                    + "</td>\n" + "<td class=\"text-left\">" + ag1.getAutorita_rilascio()
                                    + "</td>\n" + "<td class=\"text-left\">" + ag1.getLuogo_rilascio_documento();
                            contentagent += "</td>\n" + "</tr>\n";
                        }
                    }

//                        htmltemp += sttable + thead + content + sttableAgent + theadagent + contentagent + endtableagent + endtable;
                    htmltemp += thead + content + theadagent + contentagent + endtable;

                }

                String html = header + sttable + htmltemp + endbody;
                InputStream is = new ByteArrayInputStream(html.getBytes());
                getInstance().parseXHtml(wr, document, is);
                document.close();
                wr.close();
                ou.close();
                is.close();
                String base64 = new String(encodeBase64(readFileToByteArray(outputfile)));
                outputfile.delete();
                return base64;
            } catch (IOException | DocumentException ex) {
                insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
            }
        }
        return null;
    }

    /**
     *
     * @param outputfile
     * @param result
     * @return
     */
    public static String pdf_kindtrans_list(File outputfile, ArrayList<CustomerKind> result) {
        if (result.size() > 0) {
            try (OutputStream ou = new FileOutputStream(outputfile)) {
                ArrayList<String[]> list_type_customer = list_type_customer();
                ArrayList<String[]> list_type_kind = list_type_kind();
                ArrayList<String[]> list_category_nations = category_nations();
                Document document = new Document(A4.rotate(), 20.0F, 20.0F, 20.0F, 20.0F);
                document.addTitle("Mac 2.0");
                document.addAuthor("Seta s.r.l.");
                PdfWriter wr = getInstance(document, ou);
                wr.open();
                document.open();
                String header = "<html><head><title>Mac 2.0</title><style type=\"text/css\">@import url(http://fonts.googleapis.com/css?family=Roboto:400,500,700,300,100);body {font-family: \"Roboto\", helvetica, arial, sans-serif;font-size: 10px;font-weight: 400;text-rendering: optimizeLegibility;}div.table-title {\tfont-family: \"Roboto\", helvetica, arial, sans-serif;\n  text-align:center;\n  margin: auto;\n  max-width: 600px;\n  padding:5px;\n  width: 90%;\n  font-size: 20px;}.table-title h3 {color: #fafafa;font-size: 30px;font-weight: 400;font-style:normal;font-family: \"Roboto\", helvetica, arial, sans-serif;text-shadow: -1px -1px 1px rgba(0, 0, 0, 0.1);text-transform:uppercase;}.table-fill {background: white;border-radius:3px;border-collapse: collapse;height: 320px;margin: auto;max-width: 600px;padding:5px;width: 100%;box-shadow: 0 5px 10px rgba(0, 0, 0, 0.1);animation: float 5s infinite;\n}\n \nth {\n  color:#000000;;\n  background:#91949A;\n  font-size:12px;\n  font-weight: 100;\n  padding:10px;\n  text-align:left;\n  text-shadow: 0 1px 1px rgba(0, 0, 0, 0.1);\n  vertical-align:middle;\n}\n\nth:first-child {\n  border-top-left-radius:3px;\n}\ntr {\n  border-top: 1px solid #C1C3D1;\n  border-bottom-: 1px solid #C1C3D1;\n  color:#666B85;\n  font-size:11px;\n  font-weight:normal;\n  text-shadow: 0 1px 1px rgba(256, 256, 256, 0.1);\n}\n \ntr:hover td {\n  background:#4E5066;\n  color:#FFFFFF;\n  border-top: 1px solid #22262e;\n  border-bottom: 1px solid #22262e;\n}\n \ntr:first-child {\n  border-top:none;\n}\n\ntr:last-child {\n  border-bottom:none;\n}\n \ntr:nth-child(odd) td {\n  background:#EBEBEB;\n}\n \ntr:nth-child(odd):hover td {\n  background:#4E5066;\n}\n\ntr:last-child td:first-child {\n  border-bottom-left-radius:3px;\n}\n \ntr:last-child td:last-child {\n  border-bottom-right-radius:3px;\n}\n \ntd {\n  background:#FFFFFF;\n padding:10;  vertical-align:middle;\n  font-weight:300;\n  font-size:11px;\n  text-shadow: -1px -1px 1px rgba(0, 0, 0, 0.1);\n  border-bottom: 1px solid #C1C3D1;\n\nth.text-left {\n  text-align: left;\n}\n\nth.text-center {\n  text-align: center;\n}\n\nth.text-right {\n  text-align: right;\n}\n\ntd.text-left {\n  text-align: left;\n}\n\ntd.text-center {\n  text-align: center;\n}\n\ntd.text-right {  text-align: right;}</style></head><body>";
                String sttable = "<div class=\"table-title\">Kind Of Transaction List - Branch: " + filiale + " </div><table class=\"table-fill\"><thead>";
                String thead = "<tr>"
                        + "<th class=\"text-left\">Code</th>"
                        + "<th class=\"text-left\">Description</th>"
                        + "<th class=\"text-left\">REsident/Not Resident</th>"
                        + "<th class=\"text-left\">Customer Type</th>"
                        + "<th class=\"text-left\">KYC Threshold </th>"
                        + "<th class=\"text-left\">Max Weekly</th>"
                        + "<th class=\"text-left\">Status</th>"
                        + "<th class=\"text-left\">AML Threshold</th>"
                        + "<th class=\"text-left\">Geographic Area</th>"
                        + "<th class=\"text-left\">Print ExtraUE Certification</th>"
                        + "<th class=\"text-left\">ExtraUE Certification Threshold</th>"
                        + "<th class=\"text-left\">Upload Required</th>"
                        + "<th class=\"text-left\">Sales Type</th>"
                        + "<th class=\"text-left\">VAT Code</th>"
                        + "<th class=\"text-left\">Stamp Duty Threshold</th>"
                        + "<th class=\"text-left\">Stamp Duty Value</th>"
                        + "<th class=\"text-left\">Stamp Duty Description</th>"
                        + "</tr></thead><tbody>";
                String content = "";

                for (int i = 0; i < result.size(); i++) {
                    CustomerKind res = (CustomerKind) result.get(i);

                    content = content + "<tr>\n<td class=\"text-left\">" + res.getTipologia_clienti()
                            + "</td>\n" + "<td class=\"text-left\">" + res.getDe_tipologia_clienti()
                            + "</td>\n" + "<td class=\"text-left\">" + formatAL(res.getFg_nazionalita(), list_type_kind, 1)
                            + "</td>\n" + "<td class=\"text-left\">" + formatAL(res.getFg_tipo_cliente(), list_type_customer, 1)
                            + "</td>\n" + "<td class=\"text-left\">" + res.getIp_max_singola_transazione()
                            + "</td>\n" + "<td class=\"text-left\">" + res.getIp_max_settimanale()
                            + "</td>\n" + "<td class=\"text-left\">" + formatStatus_general(res.getFg_annullato())
                            + "</td>\n" + "<td class=\"text-left\">" + res.getIp_soglia_antiriciclaggio()
                            + "</td>\n" + "<td class=\"text-left\">" + formatAL(res.getFg_area_geografica(), list_category_nations, 1)
                            + "</td>\n" + "<td class=\"text-left\">" + res.getStampa_autocertificazione()
                            + "</td>\n" + "<td class=\"text-left\">" + res.getIp_soglia_extraCEE_certification()
                            + "</td>\n" + "<td class=\"text-left\">" + res.getFg_uploadobbl()
                            + "</td>\n" + "<td class=\"text-left\">" + res.getTipofat()
                            + "</td>\n" + "<td class=\"text-left\">" + res.getVatcode()
                            + "</td>\n" + "<td class=\"text-left\">" + res.getIp_soglia_bollo()
                            + "</td>\n" + "<td class=\"text-left\">" + res.getIp_value_bollo()
                            + "</td>\n" + "<td class=\"text-left\">" + res.getDescr_bollo();

                    content += "</td>\n" + "</tr>\n";
                }

                String endtable = "</tbody></table>";
                String endbody = "</body></html>";
                String html = header + sttable + thead + content + endtable + endbody;
                InputStream is = new ByteArrayInputStream(html.getBytes());
                getInstance().parseXHtml(wr, document, is);
                document.close();
                wr.close();
                ou.close();
                is.close();
                String base64 = new String(encodeBase64(readFileToByteArray(outputfile)));
                outputfile.delete();
                return base64;
            } catch (IOException | DocumentException ex) {
                insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
            }
        }
        return null;
    }

    /**
     *
     * @param outputfile
     * @param result
     * @return
     */
    public static String pdf_groupbr_list(File outputfile, ArrayList<String[]> result) {
        if (result.size() > 0) {
            try (OutputStream ou = new FileOutputStream(outputfile)) {
                ArrayList<String[]> typegroup = selectgroupbranch();
                Document document = new Document(A4.rotate(), 20.0F, 20.0F, 20.0F, 20.0F);
                document.addTitle("Mac 2.0");
                document.addAuthor("Seta s.r.l.");
                PdfWriter wr = getInstance(document, ou);
                wr.open();
                document.open();
                String header = "<html><head><title>Mac 2.0</title><style type=\"text/css\">@import url(http://fonts.googleapis.com/css?family=Roboto:400,500,700,300,100);body {font-family: \"Roboto\", helvetica, arial, sans-serif;font-size: 10px;font-weight: 400;text-rendering: optimizeLegibility;}div.table-title {\tfont-family: \"Roboto\", helvetica, arial, sans-serif;\n  text-align:center;\n  margin: auto;\n  max-width: 600px;\n  padding:5px;\n  width: 90%;\n  font-size: 20px;}.table-title h3 {color: #fafafa;font-size: 30px;font-weight: 400;font-style:normal;font-family: \"Roboto\", helvetica, arial, sans-serif;text-shadow: -1px -1px 1px rgba(0, 0, 0, 0.1);text-transform:uppercase;}.table-fill {background: white;border-radius:3px;border-collapse: collapse;height: 320px;margin: auto;max-width: 600px;padding:5px;width: 100%;box-shadow: 0 5px 10px rgba(0, 0, 0, 0.1);animation: float 5s infinite;\n}\n \nth {\n  color:#000000;;\n  background:#91949A;\n  font-size:12px;\n  font-weight: 100;\n  padding:10px;\n  text-align:left;\n  text-shadow: 0 1px 1px rgba(0, 0, 0, 0.1);\n  vertical-align:middle;\n}\n\nth:first-child {\n  border-top-left-radius:3px;\n}\ntr {\n  border-top: 1px solid #C1C3D1;\n  border-bottom-: 1px solid #C1C3D1;\n  color:#666B85;\n  font-size:11px;\n  font-weight:normal;\n  text-shadow: 0 1px 1px rgba(256, 256, 256, 0.1);\n}\n \ntr:hover td {\n  background:#4E5066;\n  color:#FFFFFF;\n  border-top: 1px solid #22262e;\n  border-bottom: 1px solid #22262e;\n}\n \ntr:first-child {\n  border-top:none;\n}\n\ntr:last-child {\n  border-bottom:none;\n}\n \ntr:nth-child(odd) td {\n  background:#EBEBEB;\n}\n \ntr:nth-child(odd):hover td {\n  background:#4E5066;\n}\n\ntr:last-child td:first-child {\n  border-bottom-left-radius:3px;\n}\n \ntr:last-child td:last-child {\n  border-bottom-right-radius:3px;\n}\n \ntd {\n  background:#FFFFFF;\n padding:10;  vertical-align:middle;\n  font-weight:300;\n  font-size:11px;\n  text-shadow: -1px -1px 1px rgba(0, 0, 0, 0.1);\n  border-bottom: 1px solid #C1C3D1;\n\nth.text-left {\n  text-align: left;\n}\n\nth.text-center {\n  text-align: center;\n}\n\nth.text-right {\n  text-align: right;\n}\n\ntd.text-left {\n  text-align: left;\n}\n\ntd.text-center {\n  text-align: center;\n}\n\ntd.text-right {  text-align: right;}</style></head><body>";
                String sttable = "<div class=\"table-title\">Branch Group List - Branch: " + filiale + " </div><table class=\"table-fill\"><thead>";
                String thead = "<tr><th class=\"text-left\">Code</th><th class=\"text-left\">Description</th><th class=\"text-left\">Type</th></tr></thead><tbody>";
                String content = "";

                for (int i = 0; i < result.size(); i++) {
                    String[] res = (String[]) result.get(i);

                    String type = formatAL(res[0], result, 3);
                    String typedes = formatAL(type, typegroup, 1);

                    content = content + "<tr>\n<td class=\"text-left\">" + res[0] + "</td>\n"
                            + "<td class=\"text-left\">" + res[1]
                            + "</td>\n" + "<td class=\"text-left\">" + typedes;
                    content += "</td>\n" + "</tr>\n";

                }

                String endtable = "</tbody></table>";
                String endbody = "</body></html>";
                String html = header + sttable + thead + content + endtable + endbody;
                InputStream is = new ByteArrayInputStream(html.getBytes());
                getInstance().parseXHtml(wr, document, is);
                document.close();
                wr.close();
                ou.close();
                is.close();
                String base64 = new String(encodeBase64(readFileToByteArray(outputfile)));
                outputfile.delete();
                return base64;
            } catch (IOException | DocumentException ex) {
                insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
            }
        }
        return null;
    }

    /**
     *
     * @param outputfile
     * @param result
     * @param filiale
     * @return
     */
    public static String pdf_creditcard_list(File outputfile, ArrayList<String[]> result, String filiale) {
        if (result.size() > 0) {
            try (OutputStream ou = new FileOutputStream(outputfile)) {
                ArrayList<String[]> typegroup = selectgroupbranch();
                Document document = new Document(A4.rotate(), 20.0F, 20.0F, 20.0F, 20.0F);
                document.addTitle("Mac 2.0");
                document.addAuthor("Seta s.r.l.");
                PdfWriter wr = getInstance(document, ou);
                wr.open();
                document.open();
                String header = "<html><head><title>Mac 2.0</title><style type=\"text/css\">@import url(http://fonts.googleapis.com/css?family=Roboto:400,500,700,300,100);body {font-family: \"Roboto\", helvetica, arial, sans-serif;font-size: 10px;font-weight: 400;text-rendering: optimizeLegibility;}div.table-title {\tfont-family: \"Roboto\", helvetica, arial, sans-serif;\n  text-align:center;\n  margin: auto;\n  max-width: 600px;\n  padding:5px;\n  width: 90%;\n  font-size: 20px;}.table-title h3 {color: #fafafa;font-size: 30px;font-weight: 400;font-style:normal;font-family: \"Roboto\", helvetica, arial, sans-serif;text-shadow: -1px -1px 1px rgba(0, 0, 0, 0.1);text-transform:uppercase;}.table-fill {background: white;border-radius:3px;border-collapse: collapse;height: 320px;margin: auto;max-width: 600px;padding:5px;width: 100%;box-shadow: 0 5px 10px rgba(0, 0, 0, 0.1);animation: float 5s infinite;\n}\n \nth {\n  color:#000000;;\n  background:#91949A;\n  font-size:12px;\n  font-weight: 100;\n  padding:10px;\n  text-align:left;\n  text-shadow: 0 1px 1px rgba(0, 0, 0, 0.1);\n  vertical-align:middle;\n}\n\nth:first-child {\n  border-top-left-radius:3px;\n}\ntr {\n  border-top: 1px solid #C1C3D1;\n  border-bottom-: 1px solid #C1C3D1;\n  color:#666B85;\n  font-size:11px;\n  font-weight:normal;\n  text-shadow: 0 1px 1px rgba(256, 256, 256, 0.1);\n}\n \ntr:hover td {\n  background:#4E5066;\n  color:#FFFFFF;\n  border-top: 1px solid #22262e;\n  border-bottom: 1px solid #22262e;\n}\n \ntr:first-child {\n  border-top:none;\n}\n\ntr:last-child {\n  border-bottom:none;\n}\n \ntr:nth-child(odd) td {\n  background:#EBEBEB;\n}\n \ntr:nth-child(odd):hover td {\n  background:#4E5066;\n}\n\ntr:last-child td:first-child {\n  border-bottom-left-radius:3px;\n}\n \ntr:last-child td:last-child {\n  border-bottom-right-radius:3px;\n}\n \ntd {\n  background:#FFFFFF;\n padding:10;  vertical-align:middle;\n  font-weight:300;\n  font-size:11px;\n  text-shadow: -1px -1px 1px rgba(0, 0, 0, 0.1);\n  border-bottom: 1px solid #C1C3D1;\n\nth.text-left {\n  text-align: left;\n}\n\nth.text-center {\n  text-align: center;\n}\n\nth.text-right {\n  text-align: right;\n}\n\ntd.text-left {\n  text-align: left;\n}\n\ntd.text-center {\n  text-align: center;\n}\n\ntd.text-right {  text-align: right;}</style></head><body>";
                String sttable = "<div class=\"table-title\">Credit Card List - Branch: " + filiale + " </div><table class=\"table-fill\"><thead>";
                String thead = "<tr><th class=\"text-left\">Code</th><th class=\"text-left\">Description</th><th class=\"text-left\">Status</th></tr></thead><tbody>";
                String content = "";

                for (int i = 0; i < result.size(); i++) {
                    String[] res = (String[]) result.get(i);

                    content = content + "<tr>\n<td class=\"text-left\">" + res[0] + "</td>\n"
                            + "<td class=\"text-left\">" + res[1]
                            + "</td>\n" + "<td class=\"text-left\">" + formatStatus_general(res[2]);
                    content += "</td>\n" + "</tr>\n";
                }

                String endtable = "</tbody></table>";
                String endbody = "</body></html>";
                String html = header + sttable + thead + content + endtable + endbody;
                InputStream is = new ByteArrayInputStream(html.getBytes());
                getInstance().parseXHtml(wr, document, is);
                document.close();
                wr.close();
                ou.close();
                is.close();
                String base64 = new String(encodeBase64(readFileToByteArray(outputfile)));
                outputfile.delete();
                return base64;
            } catch (IOException | DocumentException ex) {
                insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
            }
        }
        return null;
    }

    /**
     *
     * @param pathout
     * @param cl
     * @param dt_tr
     * @param ch
     * @param ck
     * @return
     */
    public String print_autocert(String pathout, Client cl, String dt_tr, Ch_transaction ch, CustomerKind ck) {
        String outputfile = new DateTime().toString("yyMMddhhmmssSSS") + "_autocert.pdf";
        File pdf = new File(pathout + outputfile);
        String cognome = cl.getCognome().toUpperCase();
        String nome = cl.getNome().toUpperCase();

        String country_nas[] = get_country(cl.getNazione_nascita());
        String country_luo[] = get_country(cl.getNazione());

//        String stato =  cl.getNazione_nascita().toUpperCase();
        String stato_nas = country_nas[1].toUpperCase();
        String stato_luo = country_luo[1].toUpperCase();

        try (OutputStream ou = new FileOutputStream(pdf)) {
            Document document = new Document(A4, 20.0F, 20.0F, 20.0F, 20.0F);
            PdfWriter wr = getInstance(document, ou);
            wr.open();
            document.open();
            PdfPTable table = new PdfPTable(1);
            table.setWidthPercentage(100.0F);
            PdfPCell cell2 = new PdfPCell(new Phrase("(EN) SELF-CERTIFICATION FOR CASH PAYMENTS BY FOREIGN CITIZENS", this.autoc_1));
            cell2.setBorder(0);
            cell2.setHorizontalAlignment(1);
            table.addCell(cell2);
            cell2 = new PdfPCell(new Phrase("(ES) AUTO-CERTIFICACIÓN DE OPERACIONES EN EFECTIVO POR PARTE DE CIUDADANOS EXTRANJEROS", this.autoc_2));
            cell2.setBorder(0);
            cell2.setHorizontalAlignment(1);
            table.addCell(cell2);
            cell2 = new PdfPCell(new Phrase("(IT) AUTOCERTIFICAZIONE PER TRANSAZIONI IN CONTANTI DA PARTE DI CITTADINI STRANIERI", this.autoc_3));
            cell2.setBorder(0);
            cell2.setHorizontalAlignment(1);
            table.addCell(cell2);
            cell2 = new PdfPCell(new Phrase("(art. 46 D.P.R. 28 dicembre 2000 n. 445)", this.f1_italic));
            cell2.setBorder(0);
            cell2.setHorizontalAlignment(1);
            table.addCell(cell2);
            cell2 = new PdfPCell(new Phrase("\n", this.autoc_1_it));
            cell2.setBorder(0);
            cell2.setHorizontalAlignment(1);
            table.addCell(cell2);
            cell2 = new PdfPCell(new Phrase("(EN) Aware of the responsibilities and penalties provided by the law for false statements and false declarations, under my own responsibility - art. 76, D.P.R. no 445/2000", this.f1_normal));
            cell2.setBorder(0);
            cell2.setHorizontalAlignment(1);
            table.addCell(cell2);
            cell2 = new PdfPCell(new Phrase("(ES) Consciente de las responsabilidades penales y sanciones establecidas por la ley, por falso testimonio y declaraciones falsas, bajo mi propia responsabilidad - art. 76 D.P.R. 445/2000", this.autoc_2_normal));
            cell2.setBorder(0);
            cell2.setHorizontalAlignment(1);
            table.addCell(cell2);
            cell2 = new PdfPCell(new Phrase("(IT) Consapevole delle responsabilità e delle sanzioni penali stabilite dalla legge per false attestazioni e mendaci dichiarazioni, sotto la mia personale responsabilità - art. 76 D.P.R. 445/2000", this.autoc_3_normal));
            cell2.setBorder(0);
            cell2.setHorizontalAlignment(1);
            table.addCell(cell2);
            cell2 = new PdfPCell(new Phrase("\n", this.autoc_1_it));
            cell2.setBorder(0);
            cell2.setHorizontalAlignment(1);
            table.addCell(cell2);
            cell2 = new PdfPCell(new Phrase("(EN) I, the undersigned, DECLARE that for tax purposes, as a private entity, my residence is from outside Italy and my personal data are as follows:", this.autoc_1_it));
            cell2.setBorder(0);
            cell2.setHorizontalAlignment(1);
            table.addCell(cell2);
            cell2 = new PdfPCell(new Phrase("(ES) DECLARO que, como persona privada, a efects impositivos,  mi residencia se encuentra fuera de Italia y mis datos personales para la operacion "
                    + "realizada son los siguientes:", this.autoc_2_it));
            cell2.setBorder(0);
            cell2.setHorizontalAlignment(1);
            table.addCell(cell2);
            cell2 = new PdfPCell(new Phrase("(IT) Io sottoscritto/a DICHIARO che, in qualità di soggetto privato, ai fini fiscali la mia residenza è al di fuori dello Stato Italiano "
                    + "e i miei dati anagrafici per l'operazione effettuata sono i seguenti:", this.autoc_3_it));
            cell2.setBorder(0);
            cell2.setHorizontalAlignment(1);
            table.addCell(cell2);
            cell2 = new PdfPCell(new Phrase("\n", this.autoc_1_it));
            cell2.setBorder(0);
            cell2.setHorizontalAlignment(1);
            table.addCell(cell2);
            document.add(table);

            String soglia = formatMysqltoDisplay(ck.getIp_soglia_extraCEE_certification());

            PdfPTable table1 = new PdfPTable(2);
            table1.setWidthPercentage(100.0F);
            table1.setWidths(columnWidths4);
            cell2 = new PdfPCell(new Phrase("Surname, Apellidos (Cognome)", this.f1_italic));
            cell2.setBorder(0);
            table1.addCell(cell2);
            cell2 = new PdfPCell(new Phrase(cognome, this.autoc_1_n));
            cell2.setBorder(0);
            table1.addCell(cell2);
            cell2 = new PdfPCell(new Phrase("Given Names, Nombres (Nome)", this.f1_italic));
            cell2.setBorder(0);
            table1.addCell(cell2);
            cell2 = new PdfPCell(new Phrase(nome, this.autoc_1_n));
            cell2.setBorder(0);
            table1.addCell(cell2);
            cell2 = new PdfPCell(new Phrase("Foreign Country of birth, Lugar de Nacimiento (Stato Estero di nascita) ", this.f1_italic));
            cell2.setBorder(0);
            table1.addCell(cell2);
            cell2 = new PdfPCell(new Phrase(stato_nas, this.autoc_1_n));
            cell2.setBorder(0);
            table1.addCell(cell2);
            cell2 = new PdfPCell(new Phrase("Birth date, Fecha de Nacimiento (Il)", this.f1_italic));
            cell2.setBorder(0);
            table1.addCell(cell2);
            cell2 = new PdfPCell(new Phrase(cl.getDt_nascita(), this.autoc_1_n));
            cell2.setBorder(0);
            table1.addCell(cell2);
            cell2 = new PdfPCell(new Phrase("Residence, Lugar de Residencia (Residenza)", this.f1_italic));
            cell2.setBorder(0);
            table1.addCell(cell2);
            String city[] = getCity_apm(cl.getCitta());
            cell2 = new PdfPCell(new Phrase(city[1].toUpperCase(), this.autoc_1_n));
            cell2.setBorder(0);
            table1.addCell(cell2);
            cell2 = new PdfPCell(new Phrase("Foreign Country, Pais, (Stato)", this.f1_italic));
            cell2.setBorder(0);
            table1.addCell(cell2);
            cell2 = new PdfPCell(new Phrase(stato_luo, this.autoc_1_n));
            cell2.setBorder(0);
            table1.addCell(cell2);
            cell2 = new PdfPCell(new Phrase("Address, Domicilio (Indirizzo)", this.f1_italic));
            cell2.setBorder(0);
            table1.addCell(cell2);
            cell2 = new PdfPCell(new Phrase(cl.getIndirizzo().toUpperCase(), this.autoc_1_n));
            cell2.setBorder(0);
            table1.addCell(cell2);
            cell2 = new PdfPCell(new Phrase("Passport Number , Pasaporte (N. Passaporto)", this.f1_italic));
            cell2.setBorder(0);
            table1.addCell(cell2);
            cell2 = new PdfPCell(new Phrase(cl.getNumero_documento().toUpperCase(), this.autoc_1_n));
            cell2.setBorder(0);
            table1.addCell(cell2);
            cell2 = new PdfPCell(new Phrase("Issued, Emitido el (Emesso il)", this.f1_italic));
            cell2.setBorder(0);
            table1.addCell(cell2);
            cell2 = new PdfPCell(new Phrase(cl.getDt_rilascio_documento(), this.autoc_1_n));
            cell2.setBorder(0);
            table1.addCell(cell2);
            cell2 = new PdfPCell(new Phrase("(Attached Copy just for amounts over € " + soglia + " - Copia adjunta para importes superiores a € "
                    + soglia + " - Copia Allegata per importi superiori a € " + soglia + ")", this.f1_italic));
            cell2.setBorder(0);
            cell2.setColspan(2);
            table1.addCell(cell2);
            document.add(table1);
            table = new PdfPTable(1);
            table.setWidthPercentage(100.0F);
            cell2 = new PdfPCell(new Phrase("(EN) For all the amounts above € " + soglia + " I, the undersigned DECLARE, under my own responsibility, not to "
                    + "be an Italian citizen and that my tax residence is outside Italy at the above address.", this.autoc_1_it));
            cell2.setBorder(0);
            cell2.setHorizontalAlignment(1);
            table.addCell(cell2);
            cell2 = new PdfPCell(new Phrase("(ES) Para importes superiores a € " + soglia + ", DECLARO bajo mi propia responsabilidad de no ser ciudadano "
                    + "italiano, y que mi residencia fiscal se encuentra fuera de Italia en el domicilio anteriormente indicado.", this.autoc_2_it));
            cell2.setBorder(0);
            cell2.setHorizontalAlignment(1);
            table.addCell(cell2);
            cell2 = new PdfPCell(new Phrase("(IT) Per importi superiori a € " + soglia + " io sottoscritto DICHIARO, sotto la mia personale responsabilità, di non "
                    + "essere Cittadino Italiano, che la mia residenza fiscale è al di fuori dello Spazio Economico Europeo all'indirizzo sopra indicato.", this.autoc_3_it));
            cell2.setBorder(0);
            cell2.setHorizontalAlignment(1);
            table.addCell(cell2);
            cell2 = new PdfPCell(new Phrase("\n", this.autoc_1_it));
            cell2.setBorder(0);
            cell2.setHorizontalAlignment(1);
            table.addCell(cell2);
            document.add(table);

            PdfPTable table2 = new PdfPTable(2);
            table2.setWidthPercentage(100.0F);
            table2.setWidths(columnWidths0);

            cell2 = new PdfPCell(new Phrase("Date - Fecha - Data", this.autoc_1_it));
            cell2.setBorder(0);
            cell2.setHorizontalAlignment(0);
            table2.addCell(cell2);

            Phrase phrase = new Phrase();
            phrase.add(new Chunk("Signature (Firma)", this.autoc_1_it));
//            phrase.add(new Chunk("`sigClie,uid=0,bio=0`", this.sign));
            cell2 = new PdfPCell(phrase);
            cell2.setBorder(0);
            cell2.setHorizontalAlignment(1);
            table2.addCell(cell2);

            cell2 = new PdfPCell(new Phrase(dt_tr, this.autoc_1_it));
            cell2.setBorder(0);
            cell2.setHorizontalAlignment(0);
            table2.addCell(cell2);

//            phrase = new Phrase();
//            phrase.add(new Chunk(" (Signature Full and Readable - Legible - Leggibile)", this.f3_italic));
//            phrase.add(new Chunk("`sigClie,uid=0,bio=0`", this.sign));
//            cell2 = new PdfPCell(new Phrase(" (Full and Readable - Legible - Leggibile)", this.f3_italic));
//            cell2 = new PdfPCell(phrase);
//            cell2.setBorder(Rectangle.TOP);
//            cell2.setHorizontalAlignment(1);
//            table2.addCell(cell2);
            Phrase vuoto = new Phrase("\n");
            PdfPCell cellavuota = new PdfPCell(vuoto);
            cellavuota.setBorder(NO_BORDER);

//            table2.addCell(cellavuota);
//            table2.addCell(cellavuota);
            phrase = new Phrase();
            phrase.add(new Chunk("`sigClie,uid=0,bio=0`", this.sign));
            cell2 = new PdfPCell(phrase);

            cell2.setBorder(0);
            cell2.setHorizontalAlignment(1);
            table2.addCell(cell2);

            document.add(table2);
            document.add(new Phrase("\n"));
            table = new PdfPTable(1);
            table.setWidthPercentage(100.0F);
            cell2 = new PdfPCell(new Phrase("(EN) Exempt from stamp duty and signature authentication according to art. 37, paragraph 1, D.P.R. no 445/2000.", this.f2_normal));
            cell2.setBorder(0);
            cell2.setHorizontalAlignment(1);
            table.addCell(cell2);
            cell2 = new PdfPCell(new Phrase("(ES) Libre de certificación de firmas y sello en virtud del art. 37,1, D.P.R. n. 445/2000.", this.f2_normal));
            cell2.setBorder(0);
            cell2.setHorizontalAlignment(1);
            table.addCell(cell2);
            cell2 = new PdfPCell(new Phrase("(IT) Esente da autentica di firma e da bollo ai sensi dell'art. 37, comma 1, del D.P.R. n. 445/2000.", this.f2_normal));
            cell2.setBorder(0);
            cell2.setHorizontalAlignment(1);
            table.addCell(cell2);
            cell2 = new PdfPCell(new Phrase("(EN) In accordance with the Legislative Decree no 196/2003 - Privacy Policy, the above data are collected under the provisions applicable to the proceedings for which are required and will be used exclusively for that purpose.", this.f2_normal));
            cell2.setBorder(0);
            cell2.setHorizontalAlignment(1);
            table.addCell(cell2);
            cell2 = new PdfPCell(new Phrase("(ES) De acuerdo con el Decreto Ley 196/2003 - Ley sobre la Privacidad - Los datos indicados anteriormente son recogidos en base a la legislacion vigente a fin de concluir el procedimiento por el cual son requeridos y seran utilizados exclusivamente para este fin.", this.f2_normal));
            cell2.setBorder(0);
            cell2.setHorizontalAlignment(1);
            table.addCell(cell2);
            cell2 = new PdfPCell(new Phrase("(IT) Ai sensi del D.L. 196/2003 - Legge sulla Privacy - i dati soprariportati sono raccolti in base alle disposizioni vigenti ai fini del procedimento per il quale sono richiesti e verranno utilizzati esclusivamente per tale scopo.", this.f2_normal));
            cell2.setBorder(0);
            cell2.setHorizontalAlignment(1);
            table.addCell(cell2);
            document.add(table);
            document.add(new Phrase(ch.getFiliale() + " " + ch.getId(), this.f3_italic));
            document.close();
            wr.close();
            ou.close();
            String base64 = getBase64(pdf, ch);
            pdf.delete();
            return base64;
        } catch (IOException | DocumentException ex) {
            insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
        }
        return null;
    }
//    public String print_autocert(String pathout, Client cl, String dt_tr, Ch_transaction ch, CustomerKind ck) {
//        String outputfile = new DateTime().toString("yyMMddhhmmssSSS") + "_autocert.pdf";
//        File pdf = new File(pathout + outputfile);
//        String cognome = cl.getCognome().toUpperCase();
//        String nome = cl.getNome().toUpperCase();
//
//        String country_nas[] = Engine.get_country(cl.getNazione_nascita());
//        String country_luo[] = Engine.get_country(cl.getNazione());
//
////        String stato =  cl.getNazione_nascita().toUpperCase();
//        String stato_nas = country_nas[1].toUpperCase();
//        String stato_luo = country_luo[1].toUpperCase();
//
//        try {
//            Document document = new Document(PageSize.A4, 20.0F, 20.0F, 20.0F, 20.0F);
//            OutputStream ou = new FileOutputStream(pdf);
//            PdfWriter wr = PdfWriter.getInstance(document, ou);
//            wr.open();
//            document.open();
//            PdfPTable table = new PdfPTable(1);
//            table.setWidthPercentage(100.0F);
//            PdfPCell cell2 = new PdfPCell(new Phrase("(EN) SELF-CERTIFICATION FOR CASH PAYMENTS BY FOREIGN CITIZENS (EXTRA EU-SEE)", this.autoc_1));
//            cell2.setBorder(0);
//            cell2.setHorizontalAlignment(1);
//            table.addCell(cell2);
//            cell2 = new PdfPCell(new Phrase("(ES) AUTO-CERTIFICACIÓN DE OPERACIONES EN EFECTIVO POR PARTE "
//                    + "DE CIUDADANOS EXTRANJEROS (EXTRA UE-SEE)", this.autoc_2));
//            cell2.setBorder(0);
//            cell2.setHorizontalAlignment(1);
//            table.addCell(cell2);
//            cell2 = new PdfPCell(new Phrase("(IT) AUTOCERTIFICAZIONE PER TRANSAZIONI IN CONTANTI DA PARTE DI CITTADINI "
//                    + "STRANIERI (EXTRA UE-SEE)", this.autoc_3));
//            cell2.setBorder(0);
//            cell2.setHorizontalAlignment(1);
//            table.addCell(cell2);
//            cell2 = new PdfPCell(new Phrase("(art. 46 D.P.R. 28 dicembre 2000 n. 445)", this.f1_italic));
//            cell2.setBorder(0);
//            cell2.setHorizontalAlignment(1);
//            table.addCell(cell2);
//            cell2 = new PdfPCell(new Phrase("\n", this.autoc_1_it));
//            cell2.setBorder(0);
//            cell2.setHorizontalAlignment(1);
//            table.addCell(cell2);
//            cell2 = new PdfPCell(new Phrase("(EN) Aware of the responsibilities and penalties provided by the law for false statements and false declarations, under my own responsibility - art. 76, D.P.R. no 445/2000", this.f1_normal));
//            cell2.setBorder(0);
//            cell2.setHorizontalAlignment(1);
//            table.addCell(cell2);
//            cell2 = new PdfPCell(new Phrase("(ES) Consciente de las responsabilidades penales y sanciones establecidas por la ley, por falso testimonio y declaraciones falsas, bajo mi propia responsabilidad - art. 76 D.P.R. 445/2000", this.autoc_2_normal));
//            cell2.setBorder(0);
//            cell2.setHorizontalAlignment(1);
//            table.addCell(cell2);
//            cell2 = new PdfPCell(new Phrase("(IT) Consapevole delle responsabilità e delle sanzioni penali stabilite dalla legge per false attestazioni e mendaci dichiarazioni, sotto la mia personale responsabilità - art. 76 D.P.R. 445/2000", this.autoc_3_normal));
//            cell2.setBorder(0);
//            cell2.setHorizontalAlignment(1);
//            table.addCell(cell2);
//            cell2 = new PdfPCell(new Phrase("\n", this.autoc_1_it));
//            cell2.setBorder(0);
//            cell2.setHorizontalAlignment(1);
//            table.addCell(cell2);
//            cell2 = new PdfPCell(new Phrase("(EN) I, the undersigned, DECLARE that for tax purposes, as a private entity, my residence is from outside the European Economic Area (EEA) and my personal data are as follows:", this.autoc_1_it));
//            cell2.setBorder(0);
//            cell2.setHorizontalAlignment(1);
//            table.addCell(cell2);
//            cell2 = new PdfPCell(new Phrase("(ES) DECLARO que, como persona privada, a efects impositivos,  mi residencia se encuentra fuera del Espacio Economico Europeo y mis datos personales para la operacion "
//                    + "realizada son los siguientes:", this.autoc_2_it));
//            cell2.setBorder(0);
//            cell2.setHorizontalAlignment(1);
//            table.addCell(cell2);
//            cell2 = new PdfPCell(new Phrase("(IT) Io sottoscritto/a DICHIARO che, in qualità di soggetto privato, ai fini fiscali la mia residenza è al di fuori dello Spazio Economico Europeo "
//                    + "e i miei dati anagrafici per l'operazione effettuata sono i seguenti:", this.autoc_3_it));
//            cell2.setBorder(0);
//            cell2.setHorizontalAlignment(1);
//            table.addCell(cell2);
//            cell2 = new PdfPCell(new Phrase("\n", this.autoc_1_it));
//            cell2.setBorder(0);
//            cell2.setHorizontalAlignment(1);
//            table.addCell(cell2);
//            document.add(table);
//
//            String soglia = Utility.formatMysqltoDisplay(ck.getIp_soglia_extraCEE_certification());
//
//            PdfPTable table1 = new PdfPTable(2);
//            table1.setWidthPercentage(100.0F);
//            table1.setWidths(columnWidths4);
//            cell2 = new PdfPCell(new Phrase("Surname, Apellidos (Cognome)", this.f1_italic));
//            cell2.setBorder(0);
//            table1.addCell(cell2);
//            cell2 = new PdfPCell(new Phrase(cognome, this.autoc_1_n));
//            cell2.setBorder(0);
//            table1.addCell(cell2);
//            cell2 = new PdfPCell(new Phrase("Given Names, Nombres (Nome)", this.f1_italic));
//            cell2.setBorder(0);
//            table1.addCell(cell2);
//            cell2 = new PdfPCell(new Phrase(nome, this.autoc_1_n));
//            cell2.setBorder(0);
//            table1.addCell(cell2);
//            cell2 = new PdfPCell(new Phrase("Foreign Country of birth, Lugar de Nacimiento (Stato Estero di nascita) ", this.f1_italic));
//            cell2.setBorder(0);
//            table1.addCell(cell2);
//            cell2 = new PdfPCell(new Phrase(stato_nas, this.autoc_1_n));
//            cell2.setBorder(0);
//            table1.addCell(cell2);
//            cell2 = new PdfPCell(new Phrase("Birth date, Fecha de Nacimiento (Il)", this.f1_italic));
//            cell2.setBorder(0);
//            table1.addCell(cell2);
//            cell2 = new PdfPCell(new Phrase(cl.getDt_nascita(), this.autoc_1_n));
//            cell2.setBorder(0);
//            table1.addCell(cell2);
//            cell2 = new PdfPCell(new Phrase("Residence, Lugar de Residencia (Residenza)", this.f1_italic));
//            cell2.setBorder(0);
//            table1.addCell(cell2);
//            String city[] = Engine.getCity_apm(cl.getCitta());
//            cell2 = new PdfPCell(new Phrase(city[1].toUpperCase(), this.autoc_1_n));
//            cell2.setBorder(0);
//            table1.addCell(cell2);
//            cell2 = new PdfPCell(new Phrase("Foreign Country, Pais, (Stato)", this.f1_italic));
//            cell2.setBorder(0);
//            table1.addCell(cell2);
//            cell2 = new PdfPCell(new Phrase(stato_luo, this.autoc_1_n));
//            cell2.setBorder(0);
//            table1.addCell(cell2);
//            cell2 = new PdfPCell(new Phrase("Address, Domicilio (Indirizzo)", this.f1_italic));
//            cell2.setBorder(0);
//            table1.addCell(cell2);
//            cell2 = new PdfPCell(new Phrase(cl.getIndirizzo().toUpperCase(), this.autoc_1_n));
//            cell2.setBorder(0);
//            table1.addCell(cell2);
//            cell2 = new PdfPCell(new Phrase("Passport Number , Pasaporte (N. Passaporto)", this.f1_italic));
//            cell2.setBorder(0);
//            table1.addCell(cell2);
//            cell2 = new PdfPCell(new Phrase(cl.getNumero_documento().toUpperCase(), this.autoc_1_n));
//            cell2.setBorder(0);
//            table1.addCell(cell2);
//            cell2 = new PdfPCell(new Phrase("Issued, Emitido el (Emesso il)", this.f1_italic));
//            cell2.setBorder(0);
//            table1.addCell(cell2);
//            cell2 = new PdfPCell(new Phrase(cl.getDt_rilascio_documento(), this.autoc_1_n));
//            cell2.setBorder(0);
//            table1.addCell(cell2);
//            cell2 = new PdfPCell(new Phrase("(Attached Copy just for amounts over € " + soglia + " - Copia adjunta para importes superiores a € "
//                    + soglia + " - Copia Allegata per importi superiori a € " + soglia + ")", this.f1_italic));
//            cell2.setBorder(0);
//            cell2.setColspan(2);
//            table1.addCell(cell2);
//            document.add(table1);
//            table = new PdfPTable(1);
//            table.setWidthPercentage(100.0F);
//            cell2 = new PdfPCell(new Phrase("(EN) For all the amounts above € " + soglia + " I, the undersigned DECLARE, under my own responsibility, not to "
//                    + "be an Italian citizen, nor a EU/EEA citizen and that my tax residence is outside the European Economic Area "
//                    + "at the above address.", this.autoc_1_it));
//            cell2.setBorder(0);
//            cell2.setHorizontalAlignment(1);
//            table.addCell(cell2);
//            cell2 = new PdfPCell(new Phrase("(ES) Para importes superiores a € " + soglia + ", DECLARO bajo mi propia responsabilidad de no ser ciudadano "
//                    + "italiano, ni de ningún país de la Unión Europea o del Espacio Económico Europeo, y que mi residencia fiscal se encuentra fuera del Espacio Económico Europeo en el domicilio anteriormente indicado.", this.autoc_2_it));
//            cell2.setBorder(0);
//            cell2.setHorizontalAlignment(1);
//            table.addCell(cell2);
//            cell2 = new PdfPCell(new Phrase("(IT) Per importi superiori a € " + soglia + " io sottoscritto DICHIARO, sotto la mia personale responsabilità, di non "
//                    + "essere Cittadino Italiano, nè di uno dei Paesi dell'Unione Europea o dello Spazio Economico Europeo e "
//                    + "che la mia residenza fiscale è al di fuori dello Spazio Economico Europeo all'indirizzo sopra indicato.", this.autoc_3_it));
//            cell2.setBorder(0);
//            cell2.setHorizontalAlignment(1);
//            table.addCell(cell2);
//            cell2 = new PdfPCell(new Phrase("\n", this.autoc_1_it));
//            cell2.setBorder(0);
//            cell2.setHorizontalAlignment(1);
//            table.addCell(cell2);
//            document.add(table);
//            PdfPTable table2 = new PdfPTable(2);
//            table2.setWidthPercentage(100.0F);
//            table2.setWidths(columnWidths0);
//            cell2 = new PdfPCell(new Phrase(dt_tr, this.autoc_1_it));
//            cell2.setBorder(0);
//            cell2.setHorizontalAlignment(0);
//            table2.addCell(cell2);
//
//            Phrase phrase = new Phrase();
//            phrase.add(new Chunk("Signature (Firma)", this.autoc_1_it));
////            phrase.add(new Chunk("`sigClie,uid=0,bio=0`", this.sign));
//            cell2 = new PdfPCell(phrase);
//            cell2.setBorder(0);
//            cell2.setHorizontalAlignment(0);
//            table2.addCell(cell2);
//            cell2 = new PdfPCell(new Phrase("(Date - Fecha - Data)", this.f3_italic));
//            cell2.setBorder(0);
//            cell2.setHorizontalAlignment(0);
//            table2.addCell(cell2);
//
//            phrase = new Phrase();
//            phrase.add(new Chunk(" (Signature Full and Readable - Legible - Leggibile)", this.f3_italic));
////            phrase.add(new Chunk("`sigClie,uid=0,bio=0`", this.sign));
//
////            cell2 = new PdfPCell(new Phrase(" (Full and Readable - Legible - Leggibile)", this.f3_italic));
//            cell2 = new PdfPCell(phrase);
//
//            cell2.setBorder(Rectangle.TOP);
//            cell2.setHorizontalAlignment(1);
//            table2.addCell(cell2);
//
//            Phrase vuoto = new Phrase("\n");
//            PdfPCell cellavuota = new PdfPCell(vuoto);
//            cellavuota.setBorder(Rectangle.NO_BORDER);
//
//            table2.addCell(cellavuota);
//            phrase = new Phrase();
//            phrase.add(new Chunk("`sigClie,uid=0,bio=0`", this.sign));
//            cell2 = new PdfPCell(phrase);
//
//            cell2.setBorder(0);
//            cell2.setHorizontalAlignment(1);
//            table2.addCell(cell2);
//
//            document.add(table2);
//            //document.add(new Phrase("\n"));
//            table = new PdfPTable(1);
//            table.setWidthPercentage(100.0F);
//            cell2 = new PdfPCell(new Phrase("(EN) Exempt from stamp duty and signature authentication according to art. 37, paragraph 1, D.P.R. no 445/2000.", this.f2_normal));
//            cell2.setBorder(0);
//            cell2.setHorizontalAlignment(1);
//            table.addCell(cell2);
//            cell2 = new PdfPCell(new Phrase("(ES) Libre de certificación de firmas y sello en virtud del art. 37,1, D.P.R. n. 445/2000.", this.f2_normal));
//            cell2.setBorder(0);
//            cell2.setHorizontalAlignment(1);
//            table.addCell(cell2);
//            cell2 = new PdfPCell(new Phrase("(IT) Esente da autentica di firma e da bollo ai sensi dell'art. 37, comma 1, del D.P.R. n. 445/2000.", this.f2_normal));
//            cell2.setBorder(0);
//            cell2.setHorizontalAlignment(1);
//            table.addCell(cell2);
//            cell2 = new PdfPCell(new Phrase("(EN) In accordance with the Legislative Decree no 196/2003 - Privacy Policy, the above data are collected under the provisions applicable to the proceedings for which are required and will be used exclusively for that purpose.", this.f2_normal));
//            cell2.setBorder(0);
//            cell2.setHorizontalAlignment(1);
//            table.addCell(cell2);
//            cell2 = new PdfPCell(new Phrase("(ES) De acuerdo con el Decreto Ley 196/2003 - Ley sobre la Privacidad - Los datos indicados anteriormente son recogidos en base a la legislacion vigente a fin de concluir el procedimiento por el cual son requeridos y seran utilizados exclusivamente para este fin.", this.f2_normal));
//            cell2.setBorder(0);
//            cell2.setHorizontalAlignment(1);
//            table.addCell(cell2);
//            cell2 = new PdfPCell(new Phrase("(IT) Ai sensi del D.L. 196/2003 - Legge sulla Privacy - i dati soprariportati sono raccolti in base alle disposizioni vigenti ai fini del procedimento per il quale sono richiesti e verranno utilizzati esclusivamente per tale scopo.", this.f2_normal));
//            cell2.setBorder(0);
//            cell2.setHorizontalAlignment(1);
//            table.addCell(cell2);
//            document.add(table);
//            document.add(new Phrase(ch.getFiliale() + " " + ch.getId(), this.f3_italic));
//            document.close();
//            wr.close();
//            ou.close();
//            String base64 = getBase64(pdf, ch);
//            pdf.delete();
//            return base64;
//        } catch (IOException | DocumentException ex) {
//            insertTR("E", "System", Thread.currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
//        }
//        return null;
//    }

    /**
     *
     * @param pathout
     * @param pdfingresso
     * @param cl
     * @return
     */
    public static File popolapdf(String pathout, File pdfingresso, Client cl) {
        try {
            File pdfOut;
            try (InputStream is = new FileInputStream(pdfingresso)) {
                PdfReader reader = new PdfReader(is);
                pdfOut = new File(pathout + new DateTime().toString("ddMMyyyyHHmmSSS") + "1.down.pdf");
                try(OutputStream os = new FileOutputStream(pdfOut)){
                PdfStamper stamper = new PdfStamper(reader, os);
                AcroFields af = stamper.getAcroFields();
                popolacampo(af, "nome", cl.getNome());
                popolacampo(af, "sessoM", "On");
                stamper.setFormFlattening(true);
                stamper.close();
                os.close();
                reader.close();
            }
        }
            return pdfOut;
        } catch (IOException | DocumentException ex) {
            insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
        }
        return null;
    }

    /**
     *
     * @param af
     * @param nome
     * @param valore
     * @throws DocumentException
     * @throws IOException
     */
    public static void popolacampo(AcroFields af, String nome, String valore) throws DocumentException, IOException {
        af.setField(nome, valore);
        af.setFieldProperty(nome, "setfflags", 1, null);
    }

    /**
     *
     * @param pathout
     * @param ma
     * @param tra
     * @param cl
     * @param livalue
     * @param br
     * @param iss
     * @param cust
     * @param cust_det
     * @param ann
     * @param main1
     * @param main1_val
     * @param main2
     * @param main2_val
     * @param main3
     * @param main3_val
     * @return
     */
    public static String modulo_profilatura(String pathout, Office ma, Ch_transaction tra, Client cl,
            ArrayList<Ch_transaction_value> livalue, Branch br, String iss, String cust, String cust_det, String ann, String main1,
            String main1_val, String main2, String main2_val, String main3, String main3_val) {

        Db_Master db = new Db_Master();
        ArrayList<String[]> array_identificationCard = db.identificationCard();
        ArrayList<String[]> array_country_cf = db.country();
        db.closeDB();

        try {
//            PdfReader reader = new PdfReader("C:\\Users\\rcosco\\Desktop\\nuovomoduloprofilatura.pdf");
            PdfReader reader = new PdfReader(decodeBase64(getConf("path.profcl")));

            String outputfile = new DateTime().toString("yyMMddhhmmssSSS") + "_clientmod.pdf";
            File pdf = new File(pathout + outputfile);
            try(OutputStream os = new FileOutputStream(pdf)){
            PdfStamper stamper = new PdfStamper(reader, os);
            AcroFields af = stamper.getAcroFields();

            popolacampo(af, "cognome", cl.getCognome());
            popolacampo(af, "nome", cl.getNome());
            if (cl.getSesso().equals("M")) {
                popolacampo(af, "sessoM", "On");
            } else {
                popolacampo(af, "sessoF", "On");
            }
            String city[] = getCity_apm(cl.getCitta());

            String citta = city[1];

            String naz = formatAL(cl.getNazione(), array_country_cf, 1);
            if (naz.equals("-")) {
                naz = cl.getNazione();
            }

            popolacampo(af, "indirizzo", cl.getIndirizzo() + " " + cl.getCap());
            popolacampo(af, "cap", cl.getCap());
            popolacampo(af, "stato", naz);
            popolacampo(af, "nazione", naz);
            popolacampo(af, "citta", citta);
            popolacampo(af, "provincia", cl.getProvincia());
            popolacampo(af, "codfisc", cl.getCodfisc());
            popolacampo(af, "citta_nascita", cl.getCitta_nascita());
            popolacampo(af, "dt_nascita", cl.getDt_nascita());
            popolacampo(af, "provincia_nascita", cl.getProvincia_nascita());

            String docid;
            String docname = "";
            String docname_eng = formatAL(cl.getTipo_documento(), array_identificationCard, 1);

            switch (cl.getTipo_documento()) {
                case "CI":
                    docid = "01";
                    break;
                case "PS":
                    docid = "02";
                    break;
                case "PT":
                    docid = "03";
                    break;
                default:
                    docid = "04";
                    docname = docname_eng;
                    break;
            }

            popolacampo(af, "tipo_documento", docid);
            popolacampo(af, "tipo_documento_specificare", docname);
            popolacampo(af, "tipo_documento_eng", docname_eng);

            popolacampo(af, "numero_documento", cl.getNumero_documento());
            popolacampo(af, "dt_scadenza_documento", cl.getDt_scadenza_documento());

            popolacampo(af, "rilasciato_da_documento", iss);
            popolacampo(af, "rilasciato_da_documento_eng", cl.getRilasciato_da_documento());

            if (cl.getNazione().equals(codnaz)) {
                popolacampo(af, "luogo_rilascio_documento", cl.getLuogo_rilascio_documento());
            } else {
                popolacampo(af, "stato_rilascio_documento", cl.getLuogo_rilascio_documento());
            }

            if (cl.getPep().equals("Y") || cl.getPep().equals("YES") || cl.getPep().equals("1")) {
                popolacampo(af, "soggettoPEP", "On");
            } else {
                popolacampo(af, "soggettononPEP", "On");
            }

            switch (cust) {
                case "01":
                    popolacampo(af, "lavdip", "On");
                    popolacampo(af, "lavdip_spec", cust_det);
                    break;
                case "02":
                    popolacampo(af, "nonprofessionale", "On");
                    popolacampo(af, "house_eng", "On");
                    break;
                case "07":
                    popolacampo(af, "nonprofessionale", "On");
                    popolacampo(af, "house_stud", "On");
                    break;
                case "04":
                    popolacampo(af, "nonprofessionale", "On");
                    popolacampo(af, "house_dis", "On");
                    break;
                case "05":
                    popolacampo(af, "lavaut", "On");
                    popolacampo(af, "lavaut_spec", cust_det);
                    break;
                case "03":
                    popolacampo(af, "pensionato", "On");
                    break;
                case "06":
                    popolacampo(af, "impprof", "On");
                    popolacampo(af, "impprof_spec", cust_det);
                    break;
                default:
                    popolacampo(af, "altro", "On");
                    popolacampo(af, "altro_spec", cust_det);
                    break;
            }
            switch (ann) {
                case "01":
                    popolacampo(af, "minore", "On");
                    break;
                case "02":
                    popolacampo(af, "compresa1", "On");
                    break;
                case "03":
                    popolacampo(af, "compresa2", "On");
                    break;
                case "04":
                    popolacampo(af, "compresa3", "On");
                    break;
                case "05":
                    popolacampo(af, "superiore", "On");
                    break;
                default:
                    break;
            }

            if (main1.equals("02")) {
                popolacampo(af, "motivotran", "On");
                popolacampo(af, "motivotran_spec", main1_val);
            }
            if (main2.equals("02")) {
                popolacampo(af, "provfondi", "On");
                popolacampo(af, "provfondi_spec", main2_val);
            }

            if (main3.equals("02")) {
                popolacampo(af, "utilfondi", "On");
                popolacampo(af, "utilfondi_spec", main3_val);
            }

            String date = formatStringtoStringDate(tra.getData(), patternsqldate, patternnormdate_filter);

            popolacampo(af, "luogodata", br.getAdd_city() + ", " + date);

            popolacampo(af, "luogo", br.getAdd_city());
            popolacampo(af, "data", date);

            popolacampo(af, "cognome", cl.getCognome());
            popolacampo(af, "nome", cl.getNome());

            popolacampo(af, "titolare", cl.getCognome() + " " + cl.getNome());

            popolacampo(af, "firmatitolare", "`sigClie,uid=0,bio=0`");

            stamper.setFormFlattening(true);
            stamper.close();
            os.close();
            reader.close();
            String base64 = getBase64(pdf, tra);
            pdf.delete();
            return base64;
        }
        } catch (IOException | DocumentException ex) {
            ex.printStackTrace();
            insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
        }
        return null;
    }

    /**
     *
     * @param listcurrency
     * @return
     */
    public static ArrayList<Currency> order_currencyCZ(ArrayList<Currency> listcurrency) {

        for (int i = 0; i < listcurrency.size(); i++) {
            if (listcurrency.get(i).getCode().equals("EUR") || listcurrency.get(i).getCode().equals("GBP") || listcurrency.get(i).getCode().equals("USD")) {
//                System.out.println(listcurrency.get(i).getCode() + " rc.so.pdf.Pdf.order_currencyCZ(1) " + i);
            }
        }

        for (int i = 0; i < listcurrency.size(); i++) {
            if (!listcurrency.get(i).getCode().equals("EUR") && !listcurrency.get(i).getCode().equals("GBP") && !listcurrency.get(i).getCode().equals("USD")) {
//                System.out.println(listcurrency.get(i).getCode() + " rc.so.pdf.Pdf.order_currencyCZ(2) " + i);
            }
        }

        return listcurrency;
    }

    /**
     *
     * @param pathout
     * @param listcurrency
     * @param list_all_figures
     * @param of
     * @param br
     * @return
     */
    public String cartelloCambiCZ(String pathout, ArrayList<Currency> listcurrency, ArrayList<Figures> list_all_figures, Office of, Branch br) {
        try {
            float[] columnWidths_ta = {20.0F, 70.0F, 40.0F, 40.0F};
            float[] columnWidths_new1 = {30.0F, 50.0F};

            Font fontheader2 = getFont("conf/FreeSans.ttf", IDENTITY_H, 14.0F, BOLD, BLACK);

            Font fontheader = getFont("Calibri", CP1250, 15.0F, BOLD, BLACK);
//            Font fontheader1 = FontFactory.getFont("Calibri", BaseFont.IDENTITY_H, 13.0F, Font.BOLDITALIC, BaseColor.BLACK);

            Font fontn3 = getFont("Calibri", CP1250, 10.0F, NORMAL, BLACK);
            Font fontn2a = getFont("Calibri", CP1250, 10.0F, BOLDITALIC, BLACK);
            Font fontn2 = getFont("Calibri", CP1250, 10.0F, BOLD, BLACK);
            Font fontn1 = getFont("conf/FreeSans.ttf", IDENTITY_H, 11.0F, BOLD, BLACK);

            String outputfile = new DateTime().toString("yyMMddhhmmssSSS") + "_cccz.pdf";
            File pdf = new File(pathout + outputfile);
            Document document = new Document(A4, 30.0F, 30.0F, 30.0F, 30.0F);
            document.addTitle("Mac 2.0");
            document.addAuthor("Seta s.r.l.");

            try(OutputStream ou = new FileOutputStream(pdf)){
            PdfWriter wr = getInstance(document, ou);
            wr.open();
            document.open();

            PdfPCell blank = new PdfPCell(new Phrase("", this.f3_italic));
            blank.setBorder(NO_BORDER);
            blank.setFixedHeight(15);

            PdfPTable table = new PdfPTable(2);
            table.setWidths(columnWidths_new1);
            table.setWidthPercentage(100.0F);
            Image img = getInstance(decodeBase64(getConf("path.logocl")));
            img.scalePercent(50.0F);

            PdfPCell cell1 = new PdfPCell(img);
            cell1.setBorder(0);
            cell1.setHorizontalAlignment(ALIGN_LEFT);
            table.addCell(cell1);

            cell1 = new PdfPCell(new Paragraph("KURZOVNÍ LÍSTEK\nEXCHANGE RATE LIST", fontheader));
            cell1.setBorder(0);
            cell1.setVerticalAlignment(ALIGN_MIDDLE);
            table.addCell(cell1);
            cell1 = new PdfPCell(new Phrase("", fontheader));
            cell1.setBorder(0);
            table.addCell(cell1);

            cell1 = new PdfPCell(new Paragraph(of.getDe_office() + " I\u010C " + of.getVat(), fontheader2));
            cell1.setBorder(0);
            cell1.setVerticalAlignment(ALIGN_MIDDLE);
            table.addCell(cell1);

            document.add(table);

            document.add(new Phrase("\n"));

            table = new PdfPTable(4);
            table.setWidths(columnWidths_ta);
            table.setWidthPercentage(100.0F);

            cell1 = new PdfPCell(new Paragraph(br.getCod() + " - " + br.getDe_branch().toUpperCase(), fontn1));
            cell1.setBorder(NO_BORDER);
            cell1.setVerticalAlignment(ALIGN_MIDDLE);
            cell1.setHorizontalAlignment(ALIGN_LEFT);
            cell1.setColspan(2);
            cell1.setRowspan(2);
            table.addCell(cell1);
            cell1 = new PdfPCell(new Paragraph("Poslední aktualizace:\nLast update:", fontn1));

            cell1.setBorder(NO_BORDER);
            cell1.setVerticalAlignment(ALIGN_TOP);
            cell1.setHorizontalAlignment(ALIGN_RIGHT);
            table.addCell(cell1);
            cell1 = new PdfPCell(new Paragraph(get_last_modify_rate(br.getCod()), fontn2));
            cell1.setBorder(NO_BORDER);
            cell1.setVerticalAlignment(ALIGN_MIDDLE);
            cell1.setHorizontalAlignment(ALIGN_LEFT);
            table.addCell(cell1);
            cell1 = new PdfPCell(new Paragraph("Platnost/Valid on:", fontn1));
            cell1.setBorder(NO_BORDER);
            cell1.setVerticalAlignment(ALIGN_MIDDLE);
            cell1.setHorizontalAlignment(ALIGN_RIGHT);
            table.addCell(cell1);
            cell1 = new PdfPCell(new Paragraph(new DateTime().toString("dd/MM/yyyy"), fontn2));
            cell1.setBorder(NO_BORDER);
            cell1.setVerticalAlignment(ALIGN_MIDDLE);
            cell1.setHorizontalAlignment(ALIGN_LEFT);
            table.addCell(cell1);

            table.addCell(blank);
            table.addCell(blank);
            table.addCell(blank);
            table.addCell(blank);

            cell1 = new PdfPCell();
            Paragraph p1 = new Paragraph("MĚNA / CURRENCY", fontn1);
            p1.setAlignment(ALIGN_CENTER);
            Paragraph p2 = new Paragraph("\nFX", fontn1);
            p2.setAlignment(ALIGN_LEFT);
            cell1.addElement(p1);
            cell1.addElement(p2);
            cell1.setBorder(TOP | BOTTOM);
            cell1.setBorderWidth(1);
            cell1.setColspan(2);
            table.addCell(cell1);

            cell1 = new PdfPCell();
            p1 = new Paragraph("NAKUPUJEME\nWE BUY", fontn1);
            p1.setAlignment(ALIGN_CENTER);
            p2 = new Paragraph("FX -> CZK", fontn1);
            p2.setAlignment(ALIGN_CENTER);
            cell1.addElement(p1);
            cell1.addElement(p2);
            cell1.setBorder(TOP | BOTTOM);
            cell1.setBorderWidth(1);
            table.addCell(cell1);
            cell1 = new PdfPCell();
            p1 = new Paragraph("PROD\u00C1V\u00C1ME\nWE SELL", fontn1);
            p1.setAlignment(ALIGN_CENTER);
            p2 = new Paragraph("CZK -> FX", fontn1);
            p2.setAlignment(ALIGN_CENTER);
            cell1.addElement(p1);
            cell1.addElement(p2);
            cell1.setBorder(TOP | BOTTOM);
            cell1.setBorderWidth(1);
            table.addCell(cell1);

            table.addCell(blank);
            table.addCell(blank);
            table.addCell(blank);
            table.addCell(blank);

            document.add(table);

            table = new PdfPTable(4);
            table.setWidths(columnWidths_ta);
            table.setWidthPercentage(100.0F);

            for (int i = 0; i < listcurrency.size(); i++) {
                Currency cu = listcurrency.get(i);
                if (cu.getCode().equals("EUR") || cu.getCode().equals("GBP") || cu.getCode().equals("USD")) {
                    if (!cu.getInternal_cur().equals("1")) {
                        cell1 = new PdfPCell(new Paragraph(cu.getCode(), fontn2));
                        cell1.setBorder(NO_BORDER);
                        cell1.setVerticalAlignment(ALIGN_MIDDLE);
                        cell1.setHorizontalAlignment(ALIGN_LEFT);
                        table.addCell(cell1);
                        cell1 = new PdfPCell(new Paragraph(cu.getDescrizione(), fontn2));
                        cell1.setVerticalAlignment(ALIGN_MIDDLE);
                        cell1.setHorizontalAlignment(ALIGN_LEFT);
                        cell1.setBorder(NO_BORDER);
                        table.addCell(cell1);
                        cell1 = new PdfPCell(new Paragraph(cu.getBuy_std_value() + " CZK", fontn2));
//                        cell1 = new PdfPCell(new Paragraph(formatMysqltoDisplay(roundDoubleandFormat(fd(formatDoubleforMysql(cu.getBuy_std_value())), 3)) + " CZK", fontn2));                        
                        cell1.setVerticalAlignment(ALIGN_MIDDLE);
                        cell1.setHorizontalAlignment(ALIGN_RIGHT);
                        cell1.setBorder(NO_BORDER);
                        table.addCell(cell1);
                        cell1 = new PdfPCell(new Paragraph(cu.getSell_std_value() + " CZK", fontn2));
//                        cell1 = new PdfPCell(new Paragraph(formatMysqltoDisplay(roundDoubleandFormat(fd(formatDoubleforMysql(cu.getSell_std_value())), 3)) + " CZK", fontn2));
                        cell1.setVerticalAlignment(ALIGN_MIDDLE);
                        cell1.setHorizontalAlignment(ALIGN_RIGHT);
                        cell1.setBorder(NO_BORDER);
                        table.addCell(cell1);
                    }
                }
            }

            table.addCell(blank);
            table.addCell(blank);
            table.addCell(blank);
            table.addCell(blank);

            document.add(table);

            table = new PdfPTable(4);
            table.setWidths(columnWidths_ta);
            table.setWidthPercentage(100.0F);

            for (int i = 0; i < listcurrency.size(); i++) {
                Currency cu = listcurrency.get(i);
                if (!cu.getCode().equals("EUR") && !cu.getCode().equals("GBP") && !cu.getCode().equals("USD")) {
                    if (!cu.getInternal_cur().equals("1")) {
                        cell1 = new PdfPCell(new Paragraph(cu.getCode(), fontn3));
                        cell1.setVerticalAlignment(ALIGN_MIDDLE);
                        cell1.setHorizontalAlignment(ALIGN_LEFT);
                        cell1.setBorder(NO_BORDER);
                        table.addCell(cell1);
                        cell1 = new PdfPCell(new Paragraph(cu.getDescrizione(), fontn3));
                        cell1.setVerticalAlignment(ALIGN_MIDDLE);
                        cell1.setHorizontalAlignment(ALIGN_LEFT);
                        cell1.setBorder(NO_BORDER);
                        table.addCell(cell1);
                        //cell1 = new PdfPCell(new Paragraph(formatMysqltoDisplay(roundDoubleandFormat(fd(formatDoubleforMysql(cu.getBuy_std_value())), 3)) + " CZK", fontn3));
                        cell1 = new PdfPCell(new Paragraph(cu.getBuy_std_value() + " CZK", fontn3));
                        cell1.setVerticalAlignment(ALIGN_MIDDLE);
                        cell1.setHorizontalAlignment(ALIGN_RIGHT);
                        cell1.setBorder(NO_BORDER);
                        table.addCell(cell1);
                        cell1 = new PdfPCell(new Paragraph((cu.getSell_std_value()) + " CZK", fontn3));
//                        cell1 = new PdfPCell(new Paragraph(formatMysqltoDisplay(roundDoubleandFormat(fd(formatDoubleforMysql(cu.getSell_std_value())), 3)) + " CZK", fontn3));
                        cell1.setVerticalAlignment(ALIGN_MIDDLE);
                        cell1.setHorizontalAlignment(ALIGN_RIGHT);
                        cell1.setBorder(NO_BORDER);
                        table.addCell(cell1);
                    }
                }
            }

            document.add(table);
            document.add(new Phrase("\n"));

            table = new PdfPTable(1);
            table.setWidthPercentage(100.0F);
            cell1 = new PdfPCell(new Paragraph(
                    "Bez poplatku. No commission.\n\n"
                    //                              + "Dohoda o výhodnějším směnném kurzu je možná pouze před provedením transakce.\n"
                    //                    + "Variation of the change rate may be agreed before the transaction only.\n"
                    + "Všechny kurzy jsou v CZK\nAll rates are in CZK\n"
                    //                    + "\nVeškeré podmínky směnárenského obchodu jsou uvedeny na vedlejším samostatném listě tohoto kurzovního lístku.\n\n"
                    //                    + "All therms and condition about change transaction are listed on separated side document, which is part of Currency change list."
                    //                    
                    + "\nVeškeré podmínky smenárenského obchodu, vcetne výctu práv zákazníka a postupu odstoupení od smenárenského "
                    + "obchodu jsou uvedeny na strane 2 tohoto kurzovního lístku.\n\n"
                    + "All terms and conditions, rights of the customer and refund process of this change transaction are listed on page 2 of this Currency change list.",
                    fontn2a));
            cell1.setVerticalAlignment(ALIGN_MIDDLE);
            cell1.setHorizontalAlignment(ALIGN_CENTER);
            cell1.setBorder(NO_BORDER);
            table.addCell(cell1);
            cell1 = new PdfPCell(new Paragraph(
                    "\nStrana (Page) 1/2", fontn2a));
            cell1.setVerticalAlignment(ALIGN_MIDDLE);
            cell1.setHorizontalAlignment(ALIGN_RIGHT);
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
        } catch (IOException | DocumentException ex) {
            insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
        }

        return null;
    }

    /**
     *
     * @param pathout
     * @param listcurrency
     * @param fil
     * @param listFixCOm
     * @param list_all_figures
     * @return
     */
    public String cartelloCambiITA(String pathout, ArrayList<Currency> listcurrency, String fil[], ArrayList<String[]> listFixCOm, ArrayList<Figures> list_all_figures) {
        try {
            float[] columnWidths_new = {50.0F, 50.0F};
            float[] columnWidths_new3 = {49.0F, 1F, 50.0F};
            float[] columnWidths_ta = {40.0F, 80.0F, 50.0F, 50.0F};
            Font fontheader = getFont("Helvetica", "Cp1252", 18.0F, 0, WHITE);
            Font fontn1 = getFont("Helvetica", "Cp1252", 13.0F, BOLD, BLACK);
            Font fontn2 = getFont("Helvetica", "Cp1252", 7.0F, BOLD, BLACK);
            Font fontn3A = getFont("Helvetica", "Cp1252", 5.0F, NORMAL, BLACK);
            Font fontn2_blue = getFont("Helvetica", "Cp1252", 7.0F, BOLD, new BaseColor(0, 76, 153));

            String outputfile = new DateTime().toString("yyMMddhhmmssSSS") + "_ccita.pdf";
            File pdf = new File(pathout + outputfile);
            Document document = new Document(A4, 20.0F, 20.0F, 20.0F, 20.0F);
            document.addTitle("Mac 2.0");
            document.addAuthor("Seta s.r.l.");
            try(OutputStream ou = new FileOutputStream(pdf)){
            PdfWriter wr = getInstance(document, ou);
            wr.open();
            document.open();
            while (listcurrency.size() > 0) {
                PdfPTable table = new PdfPTable(1);
                table.setWidthPercentage(100.0F);
                Image img = getInstance(decodeBase64(getConf("path.logocl")));
                img.scalePercent(40.0F);
                PdfPCell cell1 = new PdfPCell(img);
                cell1.setBorder(0);
                cell1.setHorizontalAlignment(ALIGN_CENTER);
                table.addCell(cell1);
                table.setSpacingAfter(5f);
                document.add(table);

                table = new PdfPTable(2);
                table.setWidths(columnWidths_new);
                table.setWidthPercentage(100.0F);
                cell1 = new PdfPCell(new Paragraph("TASSI DI CAMBIO – EXCHANGE RATES", fontheader));
                cell1.setBorder(0);
                cell1.setColspan(2);
                cell1.setBackgroundColor(GRAY);
                cell1.setVerticalAlignment(ALIGN_CENTER);
                cell1.setHorizontalAlignment(ALIGN_CENTER);
                cell1.setFixedHeight(30F);
                table.addCell(cell1);

                document.add(table);
                table = new PdfPTable(2);
                table.setWidths(columnWidths_new);
                table.setWidthPercentage(100.0F);
                cell1 = new PdfPCell(new Paragraph(fil[0] + " - " + fil[1], fontn2));
                cell1.setBorder(0);
                table.addCell(cell1);
                cell1 = new PdfPCell(new Paragraph(get_last_modify_rate(fil[0]), fontn2));
                cell1.setBorder(0);
                cell1.setHorizontalAlignment(ALIGN_RIGHT);
                table.addCell(cell1);
                document.add(table);
                table = new PdfPTable(1);
                table.setWidthPercentage(100.0F);
                cell1 = new PdfPCell(new Paragraph("La nostra valutazione del giorno – Our Company Rates", fontn1));
                cell1.setBorder(0);
                cell1.setHorizontalAlignment(ALIGN_CENTER);
                table.addCell(cell1);
                table.setSpacingAfter(10f);
                document.add(table);
                table = new PdfPTable(4);
                table.setWidths(columnWidths_ta);
                table.setWidthPercentage(100.0F);
                cell1 = new PdfPCell(new Paragraph("Valuta - Currency", fontn1));
                cell1.setBackgroundColor(GRAY);
                cell1.setVerticalAlignment(ALIGN_CENTER);
                cell1.setHorizontalAlignment(ALIGN_CENTER);
                cell1.setColspan(2);
                table.addCell(cell1);
                cell1 = new PdfPCell(new Paragraph("Tasso di acquisto\nBuy Rate", fontn2));
                cell1.setBackgroundColor(GRAY);
                cell1.setVerticalAlignment(ALIGN_CENTER);
                cell1.setHorizontalAlignment(ALIGN_CENTER);
                table.addCell(cell1);
                cell1 = new PdfPCell(new Paragraph("Tasso di vendita\nSell Rate", fontn2));
                cell1.setBackgroundColor(GRAY);
                cell1.setVerticalAlignment(ALIGN_CENTER);
                cell1.setHorizontalAlignment(ALIGN_CENTER);
                table.addCell(cell1);
                int index = 0;
                while (!listcurrency.isEmpty() && index < 60) {
                    Currency cu = listcurrency.get(0);
                    listcurrency.remove(0);
                    index++;
                    if (cu.getInternal_cur().equals("1")) {

                    } else {
                        cell1 = new PdfPCell(new Paragraph(cu.getCode(), fontn2));
                        cell1.setVerticalAlignment(ALIGN_MIDDLE);
                        cell1.setHorizontalAlignment(ALIGN_CENTER);
                        table.addCell(cell1);
                        cell1 = new PdfPCell(new Paragraph(cu.getDescrizione(), fontn2_blue));
                        cell1.setVerticalAlignment(ALIGN_MIDDLE);
                        cell1.setHorizontalAlignment(ALIGN_CENTER);
                        table.addCell(cell1);
                        cell1 = new PdfPCell(new Paragraph((cu.getBuy_std_value()), fontn2));
                        cell1.setVerticalAlignment(ALIGN_MIDDLE);
                        cell1.setHorizontalAlignment(ALIGN_CENTER);
                        table.addCell(cell1);
                        cell1 = new PdfPCell(new Paragraph((cu.getSell_std_value()), fontn2));
                        cell1.setVerticalAlignment(ALIGN_MIDDLE);
                        cell1.setHorizontalAlignment(ALIGN_CENTER);
                        table.addCell(cell1);
                    }
                }
                table.setSpacingAfter(15f);
                document.add(table);
                if (!listcurrency.isEmpty()) {
                    document.newPage();
                }
            }

            document.add(new Phrase("\n", this.ft_2));

            PdfPTable table = new PdfPTable(3);
            table.setWidthPercentage(100.0F);
            table.setWidths(columnWidths_new3);

            PdfPTable table1 = new PdfPTable(2);
            table1.setWidths(columnWidths1);
            table1.setWidthPercentage(100.0F);

            PdfPCell cell1 = new PdfPCell(new Paragraph("\n", fontn3A));
            cell1.setVerticalAlignment(ALIGN_MIDDLE);
            cell1.setHorizontalAlignment(ALIGN_RIGHT);
            cell1.setBorder(NO_BORDER);
            table1.addCell(cell1);
            table1.addCell(cell1);

            cell1 = new PdfPCell(new Paragraph("Costo massimo di acquisto banconote\nMaximum buying charge for notes", fontn2));
            cell1.setVerticalAlignment(ALIGN_MIDDLE);
            cell1.setHorizontalAlignment(ALIGN_CENTER);
            cell1.setBorder(NO_BORDER);
            table1.addCell(cell1);
            cell1 = new PdfPCell(new Paragraph((get_figures(list_all_figures, "01").getCommissione_acquisto()) + " %", fontn2));
            cell1.setVerticalAlignment(ALIGN_MIDDLE);
            cell1.setHorizontalAlignment(ALIGN_RIGHT);
            cell1.setBorder(NO_BORDER);
            table1.addCell(cell1);

            cell1 = new PdfPCell(new Paragraph("\n", fontn3A));
            cell1.setVerticalAlignment(ALIGN_MIDDLE);
            cell1.setHorizontalAlignment(ALIGN_RIGHT);
            cell1.setBorder(NO_BORDER);
            table1.addCell(cell1);
            table1.addCell(cell1);

//            cell1 = new PdfPCell(new Paragraph("Costo massimo di acquisto\nEuro Traveller’s Cheque\nMaximum buying charge for Euro TC", fontn2));
//            cell1.setBorder(Rectangle.NO_BORDER);
//            cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
//            cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
//            table1.addCell(cell1);
//
//            cell1 = new PdfPCell(new Paragraph((Engine.get_figures(list_all_figures, "02").getCommissione_acquisto()) + " %", fontn2));
//            cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
//            cell1.setHorizontalAlignment(Element.ALIGN_RIGHT);
//            cell1.setBorder(Rectangle.NO_BORDER);
//            table1.addCell(cell1);
//            cell1 = new PdfPCell(new Paragraph("\n", fontn3A));
//            cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
//            cell1.setHorizontalAlignment(Element.ALIGN_RIGHT);
//            cell1.setBorder(Rectangle.NO_BORDER);
//            table1.addCell(cell1);
//            table1.addCell(cell1);

            cell1 = new PdfPCell(new Paragraph("Costo massimo per anticipo contanti\ncon Carta di Credito\nMaximum charge for Credit Card cash advance", fontn2));
            cell1.setBorder(NO_BORDER);
            cell1.setVerticalAlignment(ALIGN_MIDDLE);
            cell1.setHorizontalAlignment(ALIGN_CENTER);
            table1.addCell(cell1);
            cell1 = new PdfPCell(new Paragraph((get_figures(list_all_figures, "04").getCommissione_acquisto()) + " %", fontn2));
            cell1.setVerticalAlignment(ALIGN_MIDDLE);
            cell1.setHorizontalAlignment(ALIGN_RIGHT);
            cell1.setBorder(NO_BORDER);
            table1.addCell(cell1);

            cell1 = new PdfPCell(new Paragraph("\n", fontn3A));
            cell1.setVerticalAlignment(ALIGN_MIDDLE);
            cell1.setHorizontalAlignment(ALIGN_RIGHT);
            cell1.setBorder(NO_BORDER);
            table1.addCell(cell1);
            table1.addCell(cell1);

            cell1 = new PdfPCell(new Paragraph("Costo massimo di vendita banconote\nMaximum selling charge for notes", fontn2));
            cell1.setBorder(NO_BORDER);
            cell1.setVerticalAlignment(ALIGN_MIDDLE);
            cell1.setHorizontalAlignment(ALIGN_CENTER);
            table1.addCell(cell1);
            cell1 = new PdfPCell(new Paragraph((get_figures(list_all_figures, "01").getCommissione_vendita()) + " %", fontn2));
            cell1.setVerticalAlignment(ALIGN_MIDDLE);
            cell1.setHorizontalAlignment(ALIGN_RIGHT);
            cell1.setBorder(NO_BORDER);
            table1.addCell(cell1);
            cell1 = new PdfPCell();
            cell1.addElement(table1);
            cell1.setBorderWidth(2);
            table.addCell(cell1);

            table1 = new PdfPTable(3);
            table1.setWidths(columnWidths1bis);
            table1.setWidthPercentage(100.0F);
            cell1 = new PdfPCell(new Paragraph("Commissione Fissa\n"
                    + "Variabile in base all’importo convertito\n"
                    + "Fixed fee based on the exchanged amount", fontn2));
            cell1.setBorder(NO_BORDER);
            cell1.setColspan(2);
            cell1.setVerticalAlignment(ALIGN_MIDDLE);
            cell1.setHorizontalAlignment(ALIGN_CENTER);
            cell1.setColspan(3);
            table1.addCell(cell1);

            for (int i = 0; i < listFixCOm.size(); i++) {
                String v1 = listFixCOm.get(i)[0];
                if (v1.equals("01")) {
                    String v2 = "Da " + formatMysqltoDisplay(listFixCOm.get(i)[1]) + " € a " + formatMysqltoDisplay(listFixCOm.get(i)[2] + " €");
                    String v3 = formatMysqltoDisplay(listFixCOm.get(i)[3]) + " €";
                    cell1 = new PdfPCell(new Paragraph(v2, fontn2));
                    cell1.setBorder(NO_BORDER);
                    cell1.setVerticalAlignment(ALIGN_MIDDLE);
                    cell1.setHorizontalAlignment(ALIGN_LEFT);
                    table1.addCell(cell1);
                    cell1 = new PdfPCell(new Paragraph("", fontn2));
                    cell1.setBorder(NO_BORDER);
                    cell1.setVerticalAlignment(ALIGN_MIDDLE);
                    cell1.setHorizontalAlignment(ALIGN_LEFT);
                    table1.addCell(cell1);
                    cell1 = new PdfPCell(new Paragraph(v3, fontn2));
                    cell1.setBorder(NO_BORDER);
                    cell1.setVerticalAlignment(ALIGN_MIDDLE);
                    cell1.setHorizontalAlignment(ALIGN_LEFT);
                    table1.addCell(cell1);
                }
            }

            cell1 = new PdfPCell(new Paragraph("Costo del servizio riacquisto valuta\nBuy Back fixed fee", fontn2));
            cell1.setBorder(NO_BORDER);
            cell1.setVerticalAlignment(ALIGN_MIDDLE);
            cell1.setHorizontalAlignment(ALIGN_LEFT);
            table1.addCell(cell1);
            cell1 = new PdfPCell(new Paragraph("", fontn3A));
            cell1.setBorder(NO_BORDER);
            cell1.setVerticalAlignment(ALIGN_MIDDLE);
            cell1.setHorizontalAlignment(ALIGN_LEFT);
            table1.addCell(cell1);
            cell1 = new PdfPCell(new Paragraph(get_figures(list_all_figures, "01").getBuy_back_commission() + " €", fontn2));
            cell1.setBorder(NO_BORDER);
            cell1.setVerticalAlignment(ALIGN_MIDDLE);
            cell1.setHorizontalAlignment(ALIGN_LEFT);
            table1.addCell(cell1);

            cell1 = new PdfPCell(new Paragraph("Costo del servizio rivendita valuta\nSell Back fixed fee", fontn2));
            cell1.setBorder(NO_BORDER);
            cell1.setVerticalAlignment(ALIGN_MIDDLE);
            cell1.setHorizontalAlignment(ALIGN_LEFT);
            table1.addCell(cell1);
            cell1 = new PdfPCell(new Paragraph("", fontn3A));
            cell1.setBorder(NO_BORDER);
            cell1.setVerticalAlignment(ALIGN_MIDDLE);
            cell1.setHorizontalAlignment(ALIGN_LEFT);
            table1.addCell(cell1);
            cell1 = new PdfPCell(new Paragraph(get_figures(list_all_figures, "01").getSell_back_commission() + " €", fontn2));
            cell1.setBorder(NO_BORDER);
            cell1.setVerticalAlignment(ALIGN_MIDDLE);
            cell1.setHorizontalAlignment(ALIGN_LEFT);
            table1.addCell(cell1);

            cell1 = new PdfPCell();
            cell1.addElement(table1);

            cell1.setBorderWidth(2);

            PdfPCell cell2 = new PdfPCell();
            cell2.addElement(new Paragraph(""));
            cell2.setBorder(NO_BORDER);
            table.addCell(cell2);

            table.addCell(cell1);
            cell1 = new PdfPCell(new Phrase("\n"));
            cell1.setBorder(0);
            table.addCell(cell1);
            cell2 = new PdfPCell();
            cell2.addElement(new Paragraph(""));
            cell2.setBorder(NO_BORDER);
            table.addCell(cell2);

            table.addCell(cell2);
            table.addCell(cell2);
            table.addCell(cell2);

            cell1 = new PdfPCell(new Paragraph("Timbro e Firma\n\n\n\n\n\n", fontn1));
            cell1.setBorderWidth(3F);
            cell1.setHorizontalAlignment(ALIGN_CENTER);
            table.addCell(cell1);

            document.add(table);

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
     * @param pathout
     * @param pdfingresso
     * @param tra
     * @param valori
     * @param cl
     * @param br
     * @return
     */
    public static String heavyUK(String pathout, byte[] pdfingresso, Ch_transaction tra, ArrayList<Ch_transaction_value> valori, Client cl, Branch br) {

        try {
            PdfReader reader = new PdfReader(pdfingresso);
            File pdfOut = new File(pathout + new DateTime().toString("ddMMyyyyHHmmSSS") + ".down.pdf");
            try(OutputStream os = new FileOutputStream(pdfOut)){
            PdfStamper stamper = new PdfStamper(reader, os);
            AcroFields af = stamper.getAcroFields();

            popolacampo(af, "sanction", "YES");

            popolacampo(af, "date", formatStringtoStringDate(tra.getData(), patternsqldate, patternnormdate));
            popolacampo(af, "occupation", cl.getRepceca().getOccupation().toUpperCase());
            popolacampo(af, "source", cl.getRepceca().getHeavy_moneysource().toUpperCase());
            popolacampo(af, "reason", cl.getRepceca().getHeavy_transactionre().toUpperCase());

            popolacampo(af, "suspicious", formatYN(cl.getRepceca().getOp_sos().toUpperCase()));
            popolacampo(af, "number", tra.getId());

            if (tra.getTipotr().equals("B")) {
                popolacampo(af, "gbp", formatMysqltoDisplay(tra.getTotal()));
            } else {
                popolacampo(af, "gbp", formatMysqltoDisplay(tra.getPay()));
            }

            popolacampo(af, "branch", br.getCod() + " - " + br.getDe_branch().toUpperCase());
            popolacampo(af, "coduser", tra.getUser());

            StringBuilder currency = new StringBuilder("");
            valori.forEach(va1 -> {
                currency.append(va1.getValuta()).append(" - ").append(formatMysqltoDisplay(va1.getQuantita())).append(" / ");
            });

            String cu1 = currency.toString();
            if (cu1.endsWith("/ ")) {
                cu1 = substring(cu1, 0, cu1.length() - 2);
            }
            popolacampo(af, "currency", cu1.trim().toUpperCase());

            stamper.setFormFlattening(true);
            stamper.close();
            os.close();
            reader.close();
            String base64 = new String(encodeBase64(readFileToByteArray(pdfOut)));
            pdfOut.delete();
            return base64;
        }
        } catch (IOException | DocumentException ex) {
            insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
        }
        return null;
    }

    /**
     *
     * @param pathout
     * @param pdfingresso
     * @param clientname
     * @param importo
     * @param numtrans
     * @param datetrans
     * @param debranch
     * @param daterefund
     * @return
     */
    public static String refund(String pathout, byte[] pdfingresso, String clientname,
            String importo, String numtrans,
            String datetrans, String debranch, String daterefund) {
        try {
            PdfReader reader = new PdfReader(pdfingresso);
            File pdfOut = new File(pathout + new DateTime().toString("ddMMyyyyHHmmSSS") + "2.down.pdf");
            try(OutputStream os = new FileOutputStream(pdfOut)){
            PdfStamper stamper = new PdfStamper(reader, os);
            AcroFields af = stamper.getAcroFields();

            popolacampo(af, "name", clientname);

            popolacampo(af, "importo", importo);

            popolacampo(af, "numtrans", numtrans);
            popolacampo(af, "datetrans", datetrans);
            popolacampo(af, "branch", debranch);
            popolacampo(af, "daterefund", daterefund);

            if (is_UK) {
                popolacampo(af, "importo", replace(importo, "GBP", "").trim());
                String[] fi = getFil();
                popolacampo(af, "branchdest", (fi[0] + " - " + fi[1]).toUpperCase());
            }

            //popolacampo(af, "f1", "`sigMac,uid=0,bio=0`");
            popolacampo(af, "f1", "`sigClie,uid=0,bio=0`");

//            field.setFlags(PdfFormField.FLAGS_PRINT); 
            if (signoffline) {
                stamper.setFormFlattening(true);
            }
            stamper.close();
            os.close();
            reader.close();
            String base64 = new String(encodeBase64(readFileToByteArray(pdfOut)));
            pdfOut.delete();
            return base64;
        }
        } catch (IOException | DocumentException ex) {
            insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
        }
        return null;
    }

    /**
     *
     * @param pathout
     * @param total
     * @param refundvalue
     * @param tipotr
     * @param pay
     * @param quantita
     * @param valuta
     * @param numtrans
     * @param datetrans
     * @param debranchtrans
     * @param debranchrefund
     * @param daterefund
     * @return
     */
    public static String refund_CZ(String pathout,
            boolean total,
            String refundvalue,
            String tipotr,
            String pay,
            String quantita,
            String valuta,
            String numtrans,
            String datetrans,
            String debranchtrans,
            String debranchrefund,
            String daterefund
    ) {
        try {
            byte[] pdfingresso;

            if (total) {
                pdfingresso = decodeBase64(getConf("path.refund.cz.tot"));
//                pdfingresso = FileUtils.readFileToByteArray(new File("C:\\mnt\\CZ_refund_TOTAL.pdf"));
            } else {
//                pdfingresso = FileUtils.readFileToByteArray(new File("C:\\mnt\\CZ_refund_PARTIAL.pdf"));
                pdfingresso = decodeBase64(getConf("path.refund.cz.par"));
            }

            PdfReader reader = new PdfReader(pdfingresso);
            File pdfOut = new File(pathout + new DateTime().toString("ddMMyyyyHHmmSSS") + "_r.down.pdf");
            try(OutputStream os = new FileOutputStream(pdfOut)){
            PdfStamper stamper = new PdfStamper(reader, os);
            AcroFields af = stamper.getAcroFields();

            if (tipotr.equals("B")) {
                popolacampo(af, "tipotrCZ", "NÁKUP");
                popolacampo(af, "tipotrENG", "BUY");
                if (total) {
                    popolacampo(af, "dalclienteCZ", formatMysqltoDisplay(pay) + " CZK");
                    popolacampo(af, "dalclienteENG", formatMysqltoDisplay(pay) + " CZK");
                    popolacampo(af, "dalcassiereCZ", formatMysqltoDisplay(quantita) + " " + valuta);
                    popolacampo(af, "dalcassiereENG", formatMysqltoDisplay(quantita) + " " + valuta);
                } else {
                    popolacampo(af, "dalcassiereCZ", formatMysqltoDisplay(refundvalue) + " CZK");
                    popolacampo(af, "dalcassiereENG", formatMysqltoDisplay(refundvalue) + " CZK");
                }

            } else {
                popolacampo(af, "tipotrCZ", "PRODEJ");
                popolacampo(af, "tipotrENG", "SELL");
                if (total) {
                    popolacampo(af, "dalcassiereCZ", formatMysqltoDisplay(pay) + " CZK");
                    popolacampo(af, "dalcassiereENG", formatMysqltoDisplay(pay) + " CZK");
                    popolacampo(af, "dalclienteCZ", formatMysqltoDisplay(quantita) + " " + valuta);
                    popolacampo(af, "dalclienteENG", formatMysqltoDisplay(quantita) + " " + valuta);
                } else {
                    popolacampo(af, "dalcassiereCZ", formatMysqltoDisplay(refundvalue) + " CZK");
                    popolacampo(af, "dalcassiereENG", formatMysqltoDisplay(refundvalue) + " CZK");
                }
            }

            popolacampo(af, "id_tra", numtrans);
            popolacampo(af, "data_tra", datetrans);
            popolacampo(af, "branch_tra", debranchtrans);
            popolacampo(af, "branch_ref", debranchrefund);
            popolacampo(af, "data_ref", daterefund);

            stamper.setFormFlattening(true);
            stamper.close();
            os.close();
            reader.close();
            String base64 = new String(encodeBase64(readFileToByteArray(pdfOut)));
            pdfOut.delete();
            return base64;
        }
        } catch (IOException | DocumentException ex) {
            insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
        }
        return null;
    }

//    public static void main(String[] args) {
//
//        String pathout = "C:\\mnt\\";
//        boolean total = false;
//        String tipotr = "S";
//        String refundvalue = "52.00";
//        String pay = "500.00";
//        String quantita = "100.00";
//        String valuta = "EUR";
//        String numtrans = "574";
//        String datetrans = "01/04/2019 18:14";
//        String debranchtrans = "319 - Hrabuvka, Horni 1471/57 70200 Ostrava";
//        String debranchrefund = "306 - Wilsonova 8 11000 Praha 1";
//        String daterefund = "03/04/2019 14:17";
//
//        refund_CZ(pathout, total, refundvalue, tipotr, pay, quantita, valuta, numtrans, datetrans, debranchtrans, debranchrefund, daterefund);
//
//    }
    /**
     *
     * @param pathout
     * @param pdfingresso
     * @param sc
     * @return
     */
    public static String scontrino_paymat(String pathout, byte[] pdfingresso, Scontrino_Pa sc) {
        try {
            PdfReader reader = new PdfReader(pdfingresso);
            File pdfOut = new File(pathout + new DateTime().toString("ddMMyyyyHHmmSSS") + "1.down.pdf");
            try(OutputStream os = new FileOutputStream(pdfOut)){
            PdfStamper stamper = new PdfStamper(reader, os);
            AcroFields af = stamper.getAcroFields();

            af.setField("Text1", sc.getText1());
            af.setField("Text2", sc.getText2());
            af.setField("Text3", sc.getText3());
            af.setField("Text4", sc.getText4());
            af.setField("Text5", sc.getText5());
            af.setField("Text6", sc.getText6());
            af.setField("Text7", sc.getText7());
            af.setField("Note", sc.getNote());
            af.setField("title", sc.getTitle());
            af.setField("cat", sc.getCat());
            af.setField("data", sc.getData());
            af.setField("l1", sc.getL1());
            af.setField("l2", sc.getL2());
            af.setField("l3", sc.getL3());

            stamper.setFormFlattening(true);
            stamper.close();
            os.close();
            reader.close();
            String base64 = new String(encodeBase64(readFileToByteArray(pdfOut)));
            pdfOut.delete();
            return base64;
            }
        } catch (IOException | DocumentException ex) {
            insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
        }
        return null;
    }

    /**
     *
     * @param pdfing
     * @param doubleimage
     * @param printtop
     * @return
     */
    public static String printdeleted(File pdfing, boolean doubleimage, boolean printtop) {
        try {
            File pdfout = new File(pdfing.getPath() + "rc.del.pdf");
            OutputStream os;
            try (InputStream is = new FileInputStream(pdfing)) {
                com.itextpdf.text.pdf.PdfReader reader = new com.itextpdf.text.pdf.PdfReader(is);
                int n = reader.getNumberOfPages();
                os = new FileOutputStream(pdfout);
                PdfStamper stamper = new PdfStamper(reader, os);
                Image img = getInstance(decodeBase64(getConf("path.del.img")));
                float w = img.getScaledWidth();
                float h = img.getScaledHeight();
                PdfGState gs1 = new PdfGState();
                gs1.setFillOpacity(0.3f);
                for (int i = 1; i <= n; i++) {
                    Rectangle pagesize = reader.getPageSizeWithRotation(i);
                    float x = (pagesize.getLeft() + pagesize.getRight()) / 2;
                    float y = (pagesize.getTop() + pagesize.getBottom()) / 2;
                    PdfContentByte over = stamper.getOverContent(i);
                    over.saveState();
                    over.setGState(gs1);
                    over.addImage(img, w, 0, 0, h, x - (w / 2), y - (h / 2));
                    over.restoreState();
                }   stamper.close();
                reader.close();
            }
            os.close();
            String base64 = new String(encodeBase64(readFileToByteArray(pdfout)));
            pdfout.delete();
            return base64;
        } catch (FileNotFoundException ex) {
            insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
        } catch (IOException | DocumentException ex) {
            insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
        }
        return null;
    }

    /**
     *
     * @param pathout
     * @param pdfing
     * @param doubleimage
     * @param printtop
     * @return
     */
    public static String printdeleted(String pathout, byte[] pdfing, boolean doubleimage, boolean printtop) {
        try {

            File pdfout = new File(pathout + new DateTime().toString("ddMMyyyyHHmmSSS") + "1rc.del.pdf");
            File img1 = new File(pathout + new DateTime().toString("ddMMyyyyHHmmSSS") + "1rc.del.png");

            writeByteArrayToFile(img1, decodeBase64(getConf("path.del.img")));

            com.itextpdf.text.pdf.PdfReader reader = new com.itextpdf.text.pdf.PdfReader(pdfing);
            int n = reader.getNumberOfPages();
            try(OutputStream os = new FileOutputStream(pdfout)){
            PdfStamper stamper = new PdfStamper(reader, os);
            Image img = getInstance(decodeBase64(getConf("path.del.img")));
            float w = img.getScaledWidth();
            float h = img.getScaledHeight();
            PdfGState gs1 = new PdfGState();
            gs1.setFillOpacity(0.3f);
            if (doubleimage) {
                for (int i = 1; i <= n; i++) {
                    Rectangle pagesize = reader.getPageSizeWithRotation(i);
                    float x = (pagesize.getLeft() + pagesize.getRight()) / 2;

                    float y1 = (pagesize.getTop() + pagesize.getBottom()) / 4;
                    float y2 = (pagesize.getTop() + pagesize.getBottom()) / 4 * 3;

                    PdfContentByte over = stamper.getOverContent(i);
                    over.saveState();
                    over.setGState(gs1);
                    over.addImage(img, w, 0, 0, h, x - (w / 2), y1 - (h / 2));
                    over.addImage(img, w, 0, 0, h, x - (w / 2), y2 - (h / 2));
                    over.restoreState();
                }
            } else if (printtop) {
                for (int i = 1; i <= n; i++) {
                    Rectangle pagesize = reader.getPageSizeWithRotation(i);
                    float x = (pagesize.getLeft() + pagesize.getRight()) / 2;
                    float y = (pagesize.getTop() + pagesize.getBottom()) / 4 * 3;
                    PdfContentByte over = stamper.getOverContent(i);
                    over.saveState();
                    over.setGState(gs1);
                    over.addImage(img, w, 0, 0, h, x - (w / 2), y - (h / 2));
                    over.restoreState();
                }
            } else {
                for (int i = 1; i <= n; i++) {
                    Rectangle pagesize = reader.getPageSizeWithRotation(i);
                    float x = (pagesize.getLeft() + pagesize.getRight()) / 2;
                    float y = (pagesize.getTop() + pagesize.getBottom()) / 2;
                    PdfContentByte over = stamper.getOverContent(i);
                    over.saveState();
                    over.setGState(gs1);
                    over.addImage(img, w, 0, 0, h, x - (w / 2), y - (h / 2));
                    over.restoreState();
                }
            }
            stamper.close();
            reader.close();
            os.close();
            String base64 = new String(encodeBase64(readFileToByteArray(pdfout)));
            pdfout.delete();
//            System.out.println("rc.so.pdf.Pdf.printdeleted() "+pdfout.getPath());
            return base64;
        }
        } catch (Exception ex) {
            insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
        }
        return null;
    }

    /**
     *
     * @param pathtemp
     * @param nomecognome
     * @param result
     * @return
     */
    public String pdf_transactionList_1558(String pathtemp, String nomecognome, ArrayList<Ch_transaction_value> result) {
        if (result.size() > 0) {
            try {

                float[] columnWidths00 = new float[]{80f, 20f};
                float[] columnWidths20 = new float[]{5f, 8f, 5f, 5f, 5f, 5f, 5f, 6f, 6f, 8f};

                Phrase vuoto = new Phrase("\n");
                PdfPCell cellavuota = new PdfPCell(vuoto);

                cellavuota.setBorder(NO_BORDER);

                Font f3_bold = getFont(HELVETICA, WINANSI, 8f, BOLDITALIC);
                Font f3_normal = getFont(HELVETICA, WINANSI, 6.96f, NORMAL);
                Font f5_normal = getFont(HELVETICA, WINANSI, 6f, NORMAL);
                Font f5_bold = getFont(HELVETICA, WINANSI, 6f, BOLD);

                File pdf = new File(pathtemp + generaId(50) + "TillTransactionList.pdf");
                Document document = new Document(A4, 20, 20, 20, 20);
                try(OutputStream ou = new FileOutputStream(pdf)){
                PdfWriter wr = getInstance(document, ou);
                document.open();

                PdfPTable table = new PdfPTable(2);
                table.setWidths(columnWidths00);
                table.setWidthPercentage(100);

                Phrase phrase1 = new Phrase();
                phrase1.add(new Chunk("Transaction List - Customer: " + nomecognome, f3_bold));
                PdfPCell cell1 = new PdfPCell(phrase1);
                cell1.setBorder(NO_BORDER);

                Phrase phrase3 = new Phrase();
                phrase3.add(new Chunk("", f3_normal));
                PdfPCell cell3 = new PdfPCell(phrase3);
                cell3.setBorder(NO_BORDER);

                table.addCell(cell1);
                table.addCell(cellavuota);
                table.addCell(cell3);
                table.addCell(cellavuota);
                document.add(table);
                vuoto.setFont(f3_normal);
                // document.add(cellavuota);

                // document.add(vuoto);
                PdfPTable table2 = new PdfPTable(10);
                table2.setWidths(columnWidths20);
                table2.setWidthPercentage(100);

                Phrase phraset2 = new Phrase();

                phraset2.add(new Chunk("BRANCH ID", f5_bold));
                PdfPCell cellt2 = new PdfPCell(phraset2);
                cellt2.setHorizontalAlignment(ALIGN_LEFT);
                cellt2.setVerticalAlignment(ALIGN_MIDDLE);
                cellt2.setBorder(BOTTOM | TOP);
                cellt2.setBackgroundColor(LIGHT_GRAY);
                table2.addCell(cellt2);

                phraset2 = new Phrase();
                phraset2.add(new Chunk("DATE", f5_bold));
                cellt2 = new PdfPCell(phraset2);
                cellt2.setHorizontalAlignment(ALIGN_LEFT);
                cellt2.setVerticalAlignment(ALIGN_MIDDLE);
                cellt2.setBorder(BOTTOM | TOP);
                cellt2.setBackgroundColor(LIGHT_GRAY);
                table2.addCell(cellt2);

                phraset2 = new Phrase();
                phraset2.add(new Chunk("OPERATOR", f5_bold));
                cellt2 = new PdfPCell(phraset2);
                cellt2.setHorizontalAlignment(ALIGN_LEFT);
                cellt2.setVerticalAlignment(ALIGN_MIDDLE);
                cellt2.setBorder(BOTTOM | TOP);
                cellt2.setBackgroundColor(LIGHT_GRAY);
                table2.addCell(cellt2);

                phraset2 = new Phrase();
                phraset2.add(new Chunk("TYPE", f5_bold));
                cellt2 = new PdfPCell(phraset2);
                cellt2.setHorizontalAlignment(ALIGN_LEFT);
                cellt2.setVerticalAlignment(ALIGN_MIDDLE);
                cellt2.setBorder(BOTTOM | TOP);
                cellt2.setBackgroundColor(LIGHT_GRAY);
                table2.addCell(cellt2);

                phraset2 = new Phrase();
                phraset2.add(new Chunk("CURRENCY", f5_bold));
                cellt2 = new PdfPCell(phraset2);
                cellt2.setHorizontalAlignment(ALIGN_RIGHT);
                cellt2.setVerticalAlignment(ALIGN_MIDDLE);
                cellt2.setBorder(BOTTOM | TOP);
                cellt2.setBackgroundColor(LIGHT_GRAY);
                table2.addCell(cellt2);

                phraset2 = new Phrase();
                phraset2.add(new Chunk("QUANTITY", f5_bold));
                cellt2 = new PdfPCell(phraset2);
                cellt2.setHorizontalAlignment(ALIGN_RIGHT);
                cellt2.setVerticalAlignment(ALIGN_MIDDLE);
                cellt2.setBorder(BOTTOM | TOP);
                cellt2.setBackgroundColor(LIGHT_GRAY);
                table2.addCell(cellt2);

                phraset2 = new Phrase();
                phraset2.add(new Chunk("RATE", f5_bold));
                cellt2 = new PdfPCell(phraset2);
                cellt2.setHorizontalAlignment(ALIGN_RIGHT);
                cellt2.setVerticalAlignment(ALIGN_MIDDLE);
                cellt2.setBorder(BOTTOM | TOP);
                cellt2.setBackgroundColor(LIGHT_GRAY);
                table2.addCell(cellt2);

                phraset2 = new Phrase();
                phraset2.add(new Chunk("% COM.", f5_bold));
                cellt2 = new PdfPCell(phraset2);
                cellt2.setHorizontalAlignment(ALIGN_RIGHT);
                cellt2.setVerticalAlignment(ALIGN_MIDDLE);
                cellt2.setBorder(BOTTOM | TOP);
                cellt2.setBackgroundColor(LIGHT_GRAY);
                table2.addCell(cellt2);

                phraset2 = new Phrase();
                phraset2.add(new Chunk("FX COM.", f5_bold));
                cellt2 = new PdfPCell(phraset2);
                cellt2.setHorizontalAlignment(ALIGN_RIGHT);
                cellt2.setVerticalAlignment(ALIGN_MIDDLE);
                cellt2.setBorder(BOTTOM | TOP);
                cellt2.setBackgroundColor(LIGHT_GRAY);
                table2.addCell(cellt2);

                phraset2 = new Phrase();
                phraset2.add(new Chunk("NOTE", f5_bold));
                cellt2 = new PdfPCell(phraset2);
                cellt2.setHorizontalAlignment(ALIGN_LEFT);
                cellt2.setVerticalAlignment(ALIGN_MIDDLE);
                cellt2.setBorder(BOTTOM | TOP);
                cellt2.setBackgroundColor(LIGHT_GRAY);
                table2.addCell(cellt2);

                document.add(table2);

                PdfPTable table3;
                //ArrayList<String> dati = siq.getDati();
                Phrase phraset;
                PdfPCell cellt;

                table3 = new PdfPTable(10);
                table3.setWidths(columnWidths20);
                table3.setWidthPercentage(100);

                for (int i = 0; i < result.size(); i++) {

                    Ch_transaction_value v1 = result.get(i);

                    phraset = new Phrase();
                    phraset.add(new Chunk(v1.getBranch(), f5_normal));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_LEFT);
                    cellt.setVerticalAlignment(ALIGN_BOTTOM);
                    cellt.setBorder(NO_BORDER);
                    table3.addCell(cellt);

                    phraset = new Phrase();
                    phraset.add(new Chunk(formatStringtoStringDate(v1.getDt_tr(), patternsqldate, patternnormdate), f5_normal));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_LEFT);
                    cellt.setVerticalAlignment(ALIGN_BOTTOM);
                    cellt.setBorder(NO_BORDER);
                    table3.addCell(cellt);

                    phraset = new Phrase();
                    phraset.add(new Chunk(v1.getOperator(), f5_normal));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_LEFT);
                    cellt.setVerticalAlignment(ALIGN_BOTTOM);
                    cellt.setBorder(NO_BORDER);
                    table3.addCell(cellt);

                    phraset = new Phrase();
                    phraset.add(new Chunk(formatType(v1.getType()).toUpperCase(), f5_normal));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_LEFT);
                    cellt.setVerticalAlignment(ALIGN_BOTTOM);
                    cellt.setBorder(NO_BORDER);
                    table3.addCell(cellt);

                    phraset = new Phrase();
                    phraset.add(new Chunk(formatMysqltoDisplay(v1.getValuta()), f5_normal));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setVerticalAlignment(ALIGN_BOTTOM);
                    cellt.setBorder(NO_BORDER);
                    table3.addCell(cellt);

                    phraset = new Phrase();
                    phraset.add(new Chunk(formatMysqltoDisplay(v1.getQuantita()), f5_normal));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setVerticalAlignment(ALIGN_BOTTOM);
                    cellt.setBorder(NO_BORDER);
                    table3.addCell(cellt);

                    phraset = new Phrase();
                    phraset.add(new Chunk(formatMysqltoDisplay(v1.getRate()), f5_normal));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setVerticalAlignment(ALIGN_BOTTOM);
                    cellt.setBorder(NO_BORDER);
                    table3.addCell(cellt);

                    phraset = new Phrase();
                    phraset.add(new Chunk(formatMysqltoDisplay(v1.getCom_perc()), f5_normal));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setVerticalAlignment(ALIGN_BOTTOM);
                    cellt.setBorder(NO_BORDER);
                    table3.addCell(cellt);

                    phraset = new Phrase();
                    phraset.add(new Chunk(formatMysqltoDisplay(v1.getFx_com()), f5_normal));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_RIGHT);
                    cellt.setVerticalAlignment(ALIGN_BOTTOM);
                    cellt.setBorder(NO_BORDER);
                    table3.addCell(cellt);

                    phraset = new Phrase();
                    phraset.add(new Chunk(v1.getNote(), f5_normal));
                    cellt = new PdfPCell(phraset);
                    cellt.setHorizontalAlignment(ALIGN_LEFT);
                    cellt.setVerticalAlignment(ALIGN_BOTTOM);
                    cellt.setBorder(NO_BORDER);
                    table3.addCell(cellt);

                }

                document.add(table3);

                document.close();
                wr.close();
                ou.close();

                String base64 = new String(encodeBase64(readFileToByteArray(pdf)));
                pdf.delete();
                return base64;
            }
            } catch (IOException | DocumentException ex) {
                ex.printStackTrace();
//                insertTR("E", "System", Thread.currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());

            }
        }
        return null;
    }

}
