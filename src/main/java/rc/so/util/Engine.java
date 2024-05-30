package rc.so.util;

import static com.google.common.base.Splitter.on;
import static com.google.common.collect.Lists.newArrayList;
import com.google.common.util.concurrent.AtomicDouble;
import rc.so.db.Db_Crm;
import rc.so.db.Db_Loy;
import rc.so.db.Db_Master;
import rc.so.entity.Agency;
import rc.so.entity.BlackListsAT;
import rc.so.entity.BlacklistM;
import rc.so.entity.Booking;
import rc.so.entity.Branch;
import rc.so.entity.Branchbudget;
import rc.so.entity.CashAD_CZ;
import rc.so.entity.Ch_transaction;
import rc.so.entity.Ch_transaction_doc;
import rc.so.entity.Ch_transaction_file;
import rc.so.entity.Ch_transaction_refund;
import rc.so.entity.Ch_transaction_value;
import rc.so.entity.Client;
import rc.so.entity.Codici_sblocco;
import rc.so.entity.Codici_sblocco_file;
import rc.so.entity.Company;
import rc.so.entity.Company_attach;
import rc.so.entity.Currency;
import rc.so.entity.CustomerKind;
import rc.so.entity.ET_change;
import rc.so.entity.Figures;
import rc.so.entity.IT_change;
import rc.so.entity.Kyc_parameter;
import rc.so.entity.NC_category;
import rc.so.entity.NC_causal;
import rc.so.entity.NC_transaction;
import rc.so.entity.NC_vatcode;
import rc.so.entity.Newsletters;
import rc.so.entity.Office;
import rc.so.entity.Office_sp;
import rc.so.entity.Openclose;
import rc.so.entity.Paymat_conf;
import rc.so.entity.Rate_history;
import rc.so.entity.Real_oc_change;
import rc.so.entity.Real_oc_nochange;
import rc.so.entity.Real_oc_pos;
import rc.so.entity.Sizecuts;
import rc.so.entity.Stock;
import rc.so.entity.Stock_report;
import rc.so.entity.Till;
import rc.so.entity.Users;
import rc.so.entity.VATcode;
import rc.so.pdf.NewReceipt_CZ;
import rc.so.pdf.NewReceipt_UK;
import rc.so.pdf.Pdf;
import rc.so.pdf.Receipt;
import static rc.so.util.Constant.is_CZ;
import static rc.so.util.Constant.is_IT;
import static rc.so.util.Constant.is_UK;
import static rc.so.util.Constant.newpread;
import static rc.so.util.Constant.patternnormdate;
import static rc.so.util.Constant.patternnormdate_filter;
import static rc.so.util.Constant.patternsql;
import static rc.so.util.Constant.patternsqldate;
import static rc.so.util.Utility.fd;
import static rc.so.util.Utility.formatAL;
import static rc.so.util.Utility.formatMysqltoDisplay;
import static rc.so.util.Utility.formatStringtoStringDate_null;
import static rc.so.util.Utility.generaId;
import static rc.so.util.Utility.generateUsername;
import static rc.so.util.Utility.getCodBrowser;
import static rc.so.util.Utility.getControvalore;
import static rc.so.util.Utility.getDT;
import static rc.so.util.Utility.getFullUrl;
import static rc.so.util.Utility.getNodeValuefromName;
import static rc.so.util.Utility.parseDoubleR;
import static rc.so.util.Utility.parseIntR;
import static rc.so.util.Utility.parseString;
import static rc.so.util.Utility.roundDoubleandFormat;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import static java.lang.String.valueOf;
import static java.lang.System.err;
import static java.lang.Thread.currentThread;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import static java.sql.ResultSet.CONCUR_UPDATABLE;
import static java.sql.ResultSet.TYPE_SCROLL_INSENSITIVE;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import static java.util.Arrays.asList;
import java.util.List;
import static java.util.stream.Collectors.toList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import static javax.xml.parsers.DocumentBuilderFactory.newInstance;
import static org.apache.commons.codec.binary.Base64.encodeBase64String;
import static org.apache.commons.io.FileUtils.readFileToByteArray;
import static org.apache.commons.lang3.StringUtils.replace;
import static org.apache.commons.lang3.StringUtils.substring;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import static org.apache.http.impl.client.HttpClientBuilder.create;
import org.apache.http.message.BasicNameValuePair;
import org.joda.time.DateTime;
import static org.joda.time.format.DateTimeFormat.forPattern;
import org.joda.time.format.DateTimeFormatter;
import org.json.JSONObject;
import static org.jsoup.Jsoup.parse;
import org.w3c.dom.Document;

/**
 *
 * @author rcosco
 */
public class Engine {

    /**
     *
     * @return
     */
    public static String getOperator() {
        Db_Master db = new Db_Master();
        String out = db.logicoperator();
        db.closeDB();
        return out;
    }

    /**
     *
     * @param fil
     * @return
     */
    public static ArrayList<String[]> credit_card(String fil) {
        Db_Master db = new Db_Master();
        ArrayList<String[]> out = db.credit_card(fil);
        db.closeDB();
        return out;
    }

    /**
     *
     * @return
     */
    public static ArrayList<String[]> select_level_rate() {
        Db_Master db = new Db_Master();
        ArrayList<String[]> out = db.select_level_rate();
        db.closeDB();
        return out;
    }

    /**
     *
     * @param bo
     * @return
     */
    public static ArrayList<String[]> credit_card_enabled_WEB(Booking bo) {
        if (bo == null) {
            return new ArrayList<>();
        } else {
            switch (bo.getCanale()) {
                case "01":
                    return credit_card_enabled();
                case "05":
                case "06":
                case "07":
                    return credit_card_enabled_SELL();
                default:
                    return new ArrayList<>();
            }
        }
    }

    /**
     *
     * @return
     */
    public static ArrayList<String[]> credit_card_enabled_SELL() {
        Db_Master db = new Db_Master();
        ArrayList<String[]> out = db.credit_card_enabled_SELL();
        db.closeDB();
        return out;
    }

    /**
     *
     * @return
     */
    public static ArrayList<String[]> credit_card_enabled() {
        Db_Master db = new Db_Master();
        ArrayList<String[]> out = db.credit_card_enabled();
        db.closeDB();
        return out;
    }

    /**
     *
     * @return
     */
    public static ArrayList<String[]> agency() {
        Db_Master db = new Db_Master();
        ArrayList<String[]> out = db.agency();
        db.closeDB();
        return out;
    }

    /**
     *
     * @return
     */
    public static ArrayList<String[]> country() {
        Db_Master db = new Db_Master();
        ArrayList<String[]> out = db.country();
        db.closeDB();
        return out;
    }

    /**
     *
     * @return
     */
    public static ArrayList<String> country_ROGUE() {
        Db_Master db = new Db_Master();
        ArrayList<String> out = db.country_ROGUE();
        db.closeDB();
        return out;
    }

    /**
     *
     * @return
     */
    public static ArrayList<String[]> country_cf() {
        Db_Master db = new Db_Master();
        ArrayList<String[]> out = db.country_cf();
        db.closeDB();
        return out;
    }

    /**
     *
     * @return
     */
    public static ArrayList<String[]> district() {
        Db_Master db = new Db_Master();
        ArrayList<String[]> out = db.district();
        db.closeDB();
        return out;
    }

    /**
     *
     * @return
     */
    public static ArrayList<String[]> identificationCard() {
        Db_Master db = new Db_Master();
        ArrayList<String[]> out = db.identificationCard();
        db.closeDB();
        return out;
    }

    /**
     *
     * @return
     */
    public static ArrayList<String[]> city_Italy() {
        Db_Master db = new Db_Master();
        ArrayList<String[]> out = db.city_Italy();
        db.closeDB();
        return out;
    }

    /**
     *
     * @return
     */
    public static ArrayList<String[]> city_Italy_APM() {
        Db_Master db = new Db_Master();
        ArrayList<String[]> out = db.city_Italy_APM();
        db.closeDB();
        return out;
    }

    /**
     *
     * @return
     */
    public static ArrayList<String[]> kind() {
        Db_Master db = new Db_Master();
        ArrayList<String[]> out = db.kind();
        db.closeDB();
        return out;
    }

    /**
     *
     * @return
     */
    public static ArrayList<String[]> figures() {
        Db_Master db = new Db_Master();
        ArrayList<String[]> out = db.figures();
        db.closeDB();
        return out;
    }

    /**
     *
     * @return
     */
    public static ArrayList<String[]> undermincommjustify() {
        Db_Master db = new Db_Master();
        ArrayList<String[]> out = db.undermincommjustify();
        db.closeDB();
        return out;
    }

    public static ArrayList<String[]> unlockratejustify() {
        Db_Master db = new Db_Master();
        ArrayList<String[]> out = db.unlockratejustify();
        db.closeDB();
        return out;
    }

    /**
     *
     * @return
     */
    public static ArrayList<String[]> kindcommissionefissa() {
        Db_Master db = new Db_Master();
        ArrayList<String[]> out = db.kindcommissionefissa();
        db.closeDB();
        return out;
    }

    /**
     *
     * @param filiale
     * @return
     */
    public static ArrayList<String[]> kindcommissionefissa(String filiale) {
        Db_Master db = new Db_Master();
        ArrayList<String[]> out = db.kindcommissionefissa(filiale);
        db.closeDB();
        return out;
    }

    /**
     *
     * @return
     */
    public static ArrayList<String[]> fixed_commission_range() {
        Db_Master db = new Db_Master();
        ArrayList<String[]> out = db.fixed_commission_range();
        db.closeDB();
        return out;
    }

    /**
     *
     * @param filiale
     * @return
     */
    public static ArrayList<String[]> fixed_commission_range(String filiale) {
        Db_Master db = new Db_Master();
        ArrayList<String[]> out = db.fixed_commission_range(filiale);
        db.closeDB();
        return out;
    }

    /**
     *
     * @return
     */
    public static ArrayList<Till> listTill() {
        Db_Master db = new Db_Master();
        ArrayList<Till> out = db.list_ALLtill();
        db.closeDB();
        return out;
    }

    /**
     *
     * @param filiale
     * @return
     */
    public static ArrayList<Till> listTill(String filiale) {
        Db_Master db = new Db_Master();
        ArrayList<Till> out = db.list_ALLtill(filiale);
        db.closeDB();
        return out;
    }

    /**
     *
     * @return
     */
    public static ArrayList<Till> list_ALL_till_enabled() {
        Db_Master db = new Db_Master();
        ArrayList<Till> out = db.list_ALL_till_enabled();
        db.closeDB();
        return out;
    }

    /**
     *
     * @param cod
     * @param listTill
     * @return
     */
    public static Till getContainsTill(String cod, ArrayList<Till> listTill) {
        if ((cod != null) && (!cod.trim().equals(""))) {
            for (int i = 0; i < listTill.size(); i++) {
                if (((Till) listTill.get(i)).getCod().equals(cod)) {
                    return (Till) listTill.get(i);
                }
            }
        }
        return null;
    }

    /**
     *
     * @return
     */
    public static ArrayList<String[]> kind_figures_openclose() {
        Db_Master db = new Db_Master();
        ArrayList<String[]> out = db.kind_figures_openclose();
        db.closeDB();
        return out;
    }

    /**
     *
     * @param typeop
     * @param online
     * @return
     */
    public static ArrayList<String[]> kind_figures_openclose(String typeop, boolean online) {
        Db_Master db = new Db_Master();
        ArrayList<String[]> out = new ArrayList<>();
        ArrayList<String[]> in = db.kind_figures_openclose();
        for (int i = 0; i < in.size(); i++) {
            boolean add = false;
            if (typeop.equals("BA")) {
                add = in.get(i)[2].equals("01") || in.get(i)[2].equals("02") || in.get(i)[2].equals("03");
            } else if (typeop.equals("BR") && !online) {
                add = in.get(i)[2].equals("01") || in.get(i)[2].equals("02") || in.get(i)[2].equals("03");
            }
            if (add) {
                out.add(in.get(i));
            }
        }
        db.closeDB();
        return out;
    }

    // public static ArrayList<String[]> figures_sizecuts() {
    // Db_Master db = new Db_Master();
    // ArrayList<String[]> out = db.figures_sizecuts();
    // db.closeDB();
    // return out;
    // }
    /**
     *
     * @return
     */
    public static ArrayList<String[]> list_nochange() {
        Db_Master db = new Db_Master();
        ArrayList<String[]> out = db.list_nochange();
        db.closeDB();
        return out;
    }

    /**
     *
     * @return
     */
    public static ArrayList<String[]> list_nochange_all() {
        Db_Master db = new Db_Master();
        ArrayList<String[]> out = db.list_nochange_all();
        db.closeDB();
        return out;
    }

    /**
     *
     * @return
     */
    public static ArrayList<String[]> list_nc_inout() {
        Db_Master db = new Db_Master();
        ArrayList<String[]> out = db.list_nc_inout();
        db.closeDB();
        return out;
    }

    /**
     *
     * @return
     */
    public static ArrayList<String[]> listCompany() {
        Db_Master db = new Db_Master();
        ArrayList<String[]> out = db.listCompany();
        db.closeDB();
        return out;
    }

    /**
     *
     * @return
     */
    public static ArrayList<String[]> nc_kind() {
        Db_Master db = new Db_Master();
        ArrayList<String[]> out = db.nc_kind();
        db.closeDB();
        return out;
    }

    /**
     *
     * @return
     */
    public static ArrayList<String[]> nc_department() {
        Db_Master db = new Db_Master();
        ArrayList<String[]> out = db.nc_department();
        db.closeDB();
        return out;
    }

    /**
     *
     * @param nckind
     * @return
     */
    public static ArrayList<String[]> list_nc_descr(String nckind) {
        Db_Master db = new Db_Master();
        ArrayList<String[]> out = db.list_nc_descr(nckind);
        db.closeDB();
        return out;
    }

    /**
     *
     * @param nc
     * @return
     */
    public static ArrayList<String[]> kind_payment_nochange(NC_transaction nc) {
        Db_Master db = new Db_Master();
        ArrayList<String[]> start = db.kind_payment();
        ArrayList<String> nc_causal_payment_enabled = db.nc_causal_payment_enabled(nc.getCausale_nc());
        db.closeDB();
        ArrayList<String[]> out = new ArrayList<>();
        for (int i = 0; i < start.size(); i++) {
            String[] st = start.get(i);
            if (nc_causal_payment_enabled.contains(st[0])) {
                out.add(st);
            }
        }
        return out;
    }

    /**
     *
     * @return
     */
    public static ArrayList<String[]> kind_payment() {
        Db_Master db = new Db_Master();
        ArrayList<String[]> out = db.kind_payment();
        db.closeDB();
        return out;
    }

    /**
     *
     * @return
     */
    public static ArrayList<String[]> kind_payment_WEB() {
        Db_Master db = new Db_Master();
        ArrayList<String[]> out = db.kind_payment_WEB();
        db.closeDB();
        return out;
    }

    public static ArrayList<String[]> kind_payment_WEB(String canaleweb) {
        Db_Master db = new Db_Master();
        ArrayList<String[]> out = db.kind_payment_WEB(canaleweb);
        db.closeDB();
        return out;
    }

    /**
     *
     * @return
     */
    public static ArrayList<String[]> category_nations() {
        Db_Master db = new Db_Master();
        ArrayList<String[]> out = db.category_nations();
        db.closeDB();
        return out;
    }

    /**
     *
     * @param nc_code
     * @return
     */
    public static NC_category getNC_category(String nc_code) {
        Db_Master db = new Db_Master();
        NC_category nc = db.get_nc_category(nc_code);
        db.closeDB();
        return nc;
    }

    /**
     *
     * @param li
     * @param nc_code
     * @return
     */
    public static NC_category getNC_category(ArrayList<NC_category> li, String nc_code) {
        try {
            NC_category n = li.stream().filter(nc1 -> nc1.getGruppo_nc().equalsIgnoreCase(nc_code)).findAny()
                    .orElse(null);
            return n;
        } catch (Exception ex) {

        }
        return null;
    }

    /**
     *
     * @param li
     * @param nc_code
     * @param nc_category
     * @return
     */
    public static NC_causal getNC_causal(ArrayList<NC_causal> li, String nc_code, String nc_category) {
        try {
            if (nc_category == null) {
                NC_causal n = li.stream().filter(nc1 -> nc1.getCausale_nc().equalsIgnoreCase(nc_code)).findAny()
                        .orElse(null);
                return n;
            }
            NC_causal n = li.stream().filter(nc1 -> nc1.getCausale_nc().equalsIgnoreCase(nc_code)
                    && nc1.getGruppo_nc().equalsIgnoreCase(nc_category)).findAny().orElse(null);
            return n;
        } catch (Exception ex) {

        }
        return null;
    }

    // public static NC_category getNC_category(ArrayList<NC_category> li, String
    // nc_code) {
    // for (int i = 0; i < li.size(); i++) {
    // if (li.get(i).getGruppo_nc().equals(nc_code)) {
    // return li.get(i);
    // }
    // }
    // return null;
    // }
    // public static NC_causal getNC_causal(ArrayList<NC_causal> li, String nc_code)
    // {
    // for (int i = 0; i < li.size(); i++) {
    // if (li.get(i).getCausale_nc().equals(nc_code)) {
    // return li.get(i);
    // }
    // }
    // return null;
    // }
    /**
     *
     * @param nc_code
     * @return
     */
    public static NC_causal getNC_causal(String nc_code) {
        Db_Master db = new Db_Master();
        NC_causal nc = db.get_nc_causal(nc_code);
        db.closeDB();
        return nc;
    }

    /**
     *
     * @return
     */
    public static ArrayList<NC_category> list_nc_category_enabled() {
        Db_Master db = new Db_Master();
        ArrayList<NC_category> li = db.list_nc_category_enabled();
        db.closeDB();
        return li;
    }

    /**
     *
     * @return
     */
    public static ArrayList<NC_category> list_ALL_nc_category() {
        Db_Master db = new Db_Master();
        ArrayList<NC_category> li = db.list_ALL_nc_category();
        db.closeDB();
        return li;
    }

    /**
     *
     * @return
     */
    public static ArrayList<NC_category> query_nc_category_bonus() {
        Db_Master db = new Db_Master();
        ArrayList<NC_category> li = db.query_nc_category_bonus();
        db.closeDB();
        return li;
    }

    /**
     *
     * @param filiale
     * @param status
     * @return
     */
    public static ArrayList<NC_category> query_nc_category_filial(String filiale, String status) {
        Db_Master db = new Db_Master();
        ArrayList<NC_category> li = db.query_nc_category_filial(filiale, status);
        db.closeDB();
        return li;
    }

    /**
     *
     * @param filiale
     * @param status
     * @return
     */
    public static ArrayList<NC_causal> query_nc_causal_filial(String filiale, String status) {
        Db_Master db = new Db_Master();
        ArrayList<NC_causal> li = db.query_nc_causal_filial(filiale, status);
        db.closeDB();
        return li;
    }

    /**
     *
     * @param cat
     * @return
     */
    public static ArrayList<NC_category> query_nc_category_all(String cat) {
        Db_Master db = new Db_Master();
        ArrayList<NC_category> li = db.query_nc_category_all(cat);
        db.closeDB();
        return li;
    }

    /**
     *
     * @param cat
     * @return
     */
    public static ArrayList<NC_causal> query_nc_causal_all(String cat) {
        Db_Master db = new Db_Master();
        ArrayList<NC_causal> li = db.query_nc_causal_all(cat);
        db.closeDB();
        return li;
    }

    /**
     *
     * @return
     */
    public static ArrayList<NC_causal> list_nc_causal_enabled() {
        Db_Master db = new Db_Master();
        ArrayList<NC_causal> li = db.list_nc_causal_enabled();
        db.closeDB();
        return li;
    }

    /**
     *
     * @return
     */
    public static ArrayList<String[]> listcity() {
        Db_Master db = new Db_Master();
        ArrayList<String[]> li = db.listcity();
        db.closeDB();
        return li;
    }

    /**
     *
     * @param cod
     * @return
     */
    public static String[] getCity1(String cod) {
        Db_Master db = new Db_Master();
        String[] li = db.getcity_1(cod);
        db.closeDB();
        return li;
    }

    /**
     *
     * @param value
     * @return
     */
    public static String formatLocalCurrency(String value) {
        if (value == null) {
            value = "";
        }
        if (value.trim().equals("0")) {
            value = "";
        }
        if (value.trim().equals("1")) {
            value = "Currency";
        }
        return value;
    }

    /**
     *
     * @param annullato
     * @return
     */
    public static String formatStatus_general(String annullato) {
        if (annullato == null) {
            annullato = "";
        }
        if (annullato.equals("0")) {
            return "<div class='font-green-jungle'>Enabled <i class='fa fa-check'></i></div>";
        }
        if (annullato.equals("1")) {
            return "<div class='font-red'>Disabled <i class='fa fa-close'></i></div>";
        }
        return annullato;
    }

    /**
     *
     * @param status
     * @return
     */
    public static String formatStatus_bankaccount(String status) {
        if (status == null) {
            status = "";
        }
        if (status.equals("Y")) {
            return "<div class='font-green-jungle'>Yes <i class='fa fa-check'></i></div>";
        }
        if (status.equals("N")) {
            return "<div class='font-red'>No <i class='fa fa-close'></i></div>";
        }
        return status;
    }

    /**
     *
     * @param annullato
     * @return
     */
    public static String formatStatus_general_cru(String annullato) {
        if (annullato == null) {
            return "";
        }
        if (annullato.equals("0")) {
            return "Enabled";
        }
        if (annullato.equals("1")) {
            return "Disabled";
        }
        return annullato;
    }

    /**
     *
     * @param code
     * @param lista
     * @return
     */
    public static String formatCountry_cru(String code, ArrayList<String[]> lista) {
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i)[0].trim().equals(code.trim())) {
                return lista.get(i)[1];
            }
        }
        return "-";
    }

    /**
     *
     * @param cod
     * @return
     */
    public static Currency getCurrency(String cod) {
        Db_Master db = new Db_Master();
        Currency cu = db.getCurrency(cod);
        db.closeDB();
        return cu;
    }

    /**
     *
     * @param cod
     * @param filiale
     * @return
     */
    public static Currency getCurrency(String cod, String filiale) {
        Db_Master db = new Db_Master();
        Currency cu = db.getCurrency(cod, filiale);
        db.closeDB();
        return cu;
    }

    /**
     *
     * @param cod
     * @param filiale
     * @return
     */
    public static ArrayList<String[]> list_kind_currency(String cod, String filiale) {
        Db_Master db = new Db_Master();
        ArrayList<String[]> li = db.list_kind_currency(cod, filiale);
        db.closeDB();
        return li;
    }

    /**
     *
     * @param filiale
     * @return
     */
    public static ArrayList<String[]> list_all_kind(String filiale) {
        Db_Master db = new Db_Master();
        ArrayList<String[]> out = db.list_all_kind(filiale);
        db.closeDB();
        return out;
    }

    /**
     *
     * @return
     */
    public static ArrayList<String[]> list_till() {
        Db_Master db = new Db_Master();
        ArrayList<String[]> out = db.list_till();
        db.closeDB();
        return out;
    }

    /**
     *
     * @param fil
     * @return
     */
    public static ArrayList<String[]> list_till(String fil) {
        Db_Master db = new Db_Master();
        ArrayList<String[]> out = db.list_till(fil);
        db.closeDB();
        return out;
    }

    /**
     *
     * @return
     */
    public static ArrayList<String[]> list_till_enabled() {
        Db_Master db = new Db_Master();
        ArrayList<String[]> out = db.list_till_enabled();
        db.closeDB();
        return out;
    }

    /**
     *
     * @return
     */
    public static ArrayList<String[]> list_bank() {
        Db_Master db = new Db_Master();
        ArrayList<String[]> out = db.list_bank();
        db.closeDB();
        return out;
    }

    /**
     *
     * @return
     */
    public static ArrayList<String[]> list_type_till() {
        ArrayList<String[]> out = new ArrayList<>();
        String[] v1 = { "0", "Safe" };
        String[] v2 = { "1", "Till" };
        out.add(v1);
        out.add(v2);
        return out;
    }

    /**
     *
     * @return
     */
    public static ArrayList<String[]> list_change_type() {
        ArrayList<String[]> out = new ArrayList<>();
        String[] v1 = { "/", "/" };
        String[] v2 = { "*", "*" };
        out.add(v1);
        out.add(v2);
        return out;
    }

    /**
     *
     * @return
     */
    public static ArrayList<Figures> list_all_figures() {
        Db_Master db = new Db_Master();
        ArrayList<Figures> out = db.list_all_figures();
        db.closeDB();
        return out;
    }

    /**
     *
     * @return
     */
    public static ArrayList<Figures> list_figures_buy() {
        Db_Master db = new Db_Master();
        ArrayList<Figures> out = db.list_figures_buy();
        db.closeDB();
        return out;
    }

    /**
     *
     * @param cod
     * @return
     */
    public static ArrayList<Figures> list_figures(String cod) {
        Db_Master db = new Db_Master();
        ArrayList<Figures> out = db.list_figures(cod);
        db.closeDB();
        return out;
    }

    /**
     *
     * @param li
     * @param cod
     * @return
     */
    public static Figures get_figures(ArrayList<Figures> li, String cod) {
        for (int i = 0; i < li.size(); i++) {
            if (li.get(i).getSupporto().equals(cod)) {
                return li.get(i);
            }
        }
        Figures vuoto = new Figures();
        vuoto.setDe_supporto("-");
        return vuoto;
    }

    // public static Figures get_figures(String cod) {
    // Db_Master db = new Db_Master();
    // Figures out = db.get_figures(cod);
    // db.closeDB();
    // return out;
    // }
    /**
     *
     * @param cod
     * @param filiale
     * @return
     */
    public static Figures get_figures(String cod, String filiale) {
        Db_Master db = new Db_Master();
        Figures out = db.get_figures(cod, filiale);
        db.closeDB();
        return out;
    }

    /**
     *
     * @param cod
     * @param filiale
     * @param type
     * @return
     */
    public static Figures get_figures(String cod, String filiale, String type) {
        Db_Master db = new Db_Master();
        if (type != null) {
            if (type.equals("central")) {
                db = new Db_Master(true);
                if (db.getC() == null) {
                    db = new Db_Master();
                }
            }
        }
        Figures out = db.get_figures(cod, filiale);
        db.closeDB();
        return out;
    }

    /**
     *
     * @return
     */
    public static List<Sizecuts> figures_sizecuts_enabled() {
        Db_Master db = new Db_Master();
        List<Sizecuts> out = db.figures_sizecuts_enabled();
        db.closeDB();
        return out;
    }

    /**
     *
     * @param cod
     * @param fil
     * @return
     */
    public static ArrayList<Sizecuts> getfigures_sizecuts_all(String cod, String fil) {
        Db_Master db = new Db_Master();
        ArrayList<Sizecuts> out = db.getfigures_sizecuts_all(cod, fil);
        db.closeDB();
        return out;
    }

    /**
     *
     * @param cod
     * @param fil
     * @return
     */
    public static ArrayList<Sizecuts> getfigures_sizecuts_enabled(String cod, String fil) {
        Db_Master db = new Db_Master();
        ArrayList<Sizecuts> out = db.getfigures_sizecuts_enabled(cod, fil);
        db.closeDB();
        return out;
    }

    // public static ArrayList<String> getfigures_sizecuts(String cod, String fil) {
    // Db_Master db = new Db_Master();
    // ArrayList<String> out = db.getfigures_sizecuts(cod, fil);
    // db.closeDB();
    // return out;
    // }
    /**
     *
     * @return
     */
    public static ArrayList<String[]> list_internetbooking() {
        Db_Master db = new Db_Master();
        ArrayList<String[]> out = db.list_internetbooking();
        db.closeDB();
        return out;
    }

    /**
     *
     * @return
     */
    public static ArrayList<String[]> list_type_customer() {
        Db_Master db = new Db_Master();
        ArrayList<String[]> out = db.list_type_customer();
        db.closeDB();
        return out;
    }

    /**
     *
     * @return
     */
    public static ArrayList<String[]> list_type_selecttipov() {
        Db_Master db = new Db_Master();
        ArrayList<String[]> out = db.list_type_selecttipov();
        db.closeDB();
        return out;
    }

    /**
     *
     * @return
     */
    public static ArrayList<String[]> list_type_kind() {
        Db_Master db = new Db_Master();
        ArrayList<String[]> out = db.list_type_kind();
        db.closeDB();
        return out;
    }

    /**
     *
     * @return
     */
    public static ArrayList<String[]> list_selectkind() {
        Db_Master db = new Db_Master();
        ArrayList<String[]> out = db.list_selectkind();
        db.closeDB();
        return out;
    }

    /**
     *
     * @param filiale
     * @return
     */
    public static ArrayList<String[]> rate_range(String filiale) {
        Db_Master db = new Db_Master();
        ArrayList<String[]> out = db.rate_range(filiale);
        db.closeDB();
        return out;
    }

    /**
     *
     * @param kind
     * @return
     */
    public static ArrayList<String[]> rate_range_enabled(String kind) {
        Db_Master db = new Db_Master();
        ArrayList<String[]> out = db.rate_range_enabled(kind);
        db.closeDB();
        return out;
    }

    /**
     *
     * @return
     */
    public static ArrayList<String[]> rate_range_enabled() {
        Db_Master db = new Db_Master();
        ArrayList<String[]> out = db.rate_range_enabled();
        db.closeDB();
        return out;
    }

    /**
     *
     * @return
     */
    public static ArrayList<String[]> contabilita() {
        Db_Master db = new Db_Master();
        ArrayList<String[]> out = db.contabilita();
        db.closeDB();
        return out;
    }

    /**
     *
     * @return
     */
    public static ArrayList<CustomerKind> list_customerKind() {
        Db_Master db = new Db_Master();
        ArrayList<CustomerKind> out = db.list_customerKind();
        db.closeDB();
        return out;
    }

    /**
     *
     * @param cod
     * @return
     */
    public static CustomerKind get_customerKind(String cod) {
        Db_Master db = new Db_Master();
        CustomerKind out = db.get_customerKind(cod);
        db.closeDB();
        return out;
    }

    /**
     *
     * @param cod
     * @param type
     * @return
     */
    public static CustomerKind get_customerKind(String cod, String type) {
        Db_Master db = new Db_Master();
        if (type != null) {
            if (type.equals("central")) {
                db = new Db_Master(true);
                if (db.getC() == null) {
                    db = new Db_Master();
                }
            }
        }
        CustomerKind out = db.get_customerKind(cod);
        db.closeDB();
        return out;
    }

    /**
     *
     * @param out
     * @param cod
     * @return
     */
    public static CustomerKind get_customerKind(ArrayList<CustomerKind> out, String cod) {
        for (int i = 0; i < out.size(); i++) {
            if (out.get(i).getTipologia_clienti().equals(cod)) {
                return out.get(i);
            }
        }
        return null;
    }

    /**
     *
     * @param kind
     * @param kindcode
     * @return
     */
    public static int index_containsKind(ArrayList<String[]> kind, String kindcode) {
        for (int i = 0; i < kind.size(); i++) {
            if (((String[]) kind.get(i))[0].equals(kindcode)) {
                return i;
            }
        }
        return -1;
    }

    /**
     *
     * @param kind
     * @param min
     * @param max
     * @return
     */
    public static String[] get_fixed_commission_range(String kind, String min, String max) {
        Db_Master db = new Db_Master();
        String[] out = db.get_fixed_commission_range(kind, min, max);
        db.closeDB();
        return out;
    }

    /**
     *
     * @param kind
     * @param min
     * @param max
     * @param filiale
     * @return
     */
    public static String[] get_fixed_commission_range(String kind, String min, String max, String filiale) {
        Db_Master db = new Db_Master();
        String[] out = db.get_fixed_commission_range(kind, min, max, filiale);
        db.closeDB();
        return out;
    }

    /**
     *
     * @param kind
     * @param min
     * @param filiale
     * @return
     */
    public static String[] get_rate_range(String kind, String min, String filiale) {
        Db_Master db = new Db_Master();
        String[] out = db.get_rate_range(kind, min, filiale);
        db.closeDB();
        return out;
    }

    /**
     *
     * @return
     */
    public static ArrayList<Agency> list_agency() {
        Db_Master db = new Db_Master();
        ArrayList<Agency> out = db.list_agency();
        db.closeDB();
        return out;
    }

    /**
     *
     * @param cod
     * @return
     */
    public static Agency get_agency(String cod) {
        Db_Master db = new Db_Master();
        Agency out = db.get_agency(cod);
        db.closeDB();
        return out;
    }

    /**
     *
     * @param cod
     * @param type
     * @return
     */
    public static Agency get_agency(String cod, String type) {
        Db_Master db = new Db_Master();
        if (type != null) {
            if (type.equals("central")) {
                db = new Db_Master(true);
                if (db.getC() == null) {
                    db = new Db_Master();
                }
            }
        }
        Agency out = db.get_agency(cod);
        db.closeDB();
        return out;
    }

    /**
     *
     * @return
     */
    public static ArrayList<Branch> list_branch() {
        Db_Master db = new Db_Master();
        ArrayList<Branch> out = db.list_branch();
        db.closeDB();
        return out;
    }

    /**
     *
     * @param cod
     * @return
     */
    public static Branch get_branch(String cod) {
        Db_Master db = new Db_Master();

        Branch out = db.get_branch(cod);
        db.closeDB();
        return out;
    }

    /**
     *
     * @param cod
     * @param type
     * @return
     */
    public static Branch get_branch(String cod, String type) {
        Db_Master db = new Db_Master();
        if (type != null) {
            if (type.equals("central")) {
                db = new Db_Master(true);
                if (db.getC() == null) {
                    db = new Db_Master();
                }
            }
        }
        Branch out = db.get_branch(cod);
        db.closeDB();
        return out;
    }

    /**
     *
     * @return
     */
    public static Office get_national_office() {
        Db_Master db = new Db_Master();
        Office out = db.get_national_office();
        db.closeDB();
        return out;
    }

    /**
     *
     * @return
     */
    public static ArrayList<Company> list_only_company() {
        Db_Master db = new Db_Master();
        ArrayList<Company> out = db.list_only_company();
        db.closeDB();
        return out;
    }

    /**
     *
     * @param cod
     * @return
     */
    public static Company get_Company(String cod) {
        Db_Master db = new Db_Master();
        Company out = db.get_Company(cod);
        db.closeDB();
        return out;
    }

    /**
     *
     * @param cod
     * @return
     */
    public static ArrayList<Company> get_Agent_company(String cod) {
        Db_Master db = new Db_Master();
        ArrayList<Company> out = db.get_Agent_company(cod);
        db.closeDB();
        return out;
    }

    /**
     *
     * @param cod
     * @return
     */
    public static Company get_Agent(String cod) {
        Db_Master db = new Db_Master();
        Company out = db.get_Agent(cod);
        db.closeDB();
        return out;
    }

    /**
     *
     * @return
     */
    public static ArrayList<BlacklistM> list_BlMacc() {
        Db_Master db = new Db_Master();
        ArrayList<BlacklistM> out = db.list_BlMacc(false);
        db.closeDB();
        return out;
    }

    /**
     *
     * @param cod
     * @return
     */
    public static BlacklistM get_list_BlMacc(String cod) {
        Db_Master db = new Db_Master();
        BlacklistM out = db.get_list_BlMacc(cod);
        db.closeDB();
        return out;
    }

    /**
     *
     * @return
     */
    public static String[] getLocalFigures() {
        Db_Master db = new Db_Master();
        String[] localcur = db.get_local_currency();
        db.closeDB();
        return localcur;
    }

    /**
     *
     * @return
     */
    public static String getLocalFiguresDescr() {
        Db_Master db = new Db_Master();
        String[] localcur = db.get_local_currency();
        String descr = db.getCurrency(localcur[0]).getDescrizione();
        db.closeDB();
        return descr;
    }

    /**
     *
     * @param st
     * @param user
     * @return
     */
    public static ArrayList<Till> list_till_status(String st, String user) {
        Db_Master db = new Db_Master();
        ArrayList<Till> out = db.list_till_status(st, user);
        db.closeDB();
        return out;
    }

    /**
     *
     * @param st
     * @param user
     * @param filiale
     * @return
     */
    public static ArrayList<Till> list_till_status(String st, String user, String filiale) {
        Db_Master db = new Db_Master();
        ArrayList<Till> out = db.list_till_status_new(st, user, filiale);
        db.closeDB();
        return out;
    }

    /**
     *
     * @param st
     * @param user
     * @param filiale
     * @param type
     * @return
     */
    public static ArrayList<Till> list_till_status(String st, String user, String filiale, String type) {
        Db_Master db = new Db_Master();
        if (type != null) {
            if (type.equals("central")) {
                db = new Db_Master(true);
                if (db.getC() == null) {
                    db = new Db_Master();
                }
            }
        }
        ArrayList<Till> out = db.list_till_status_new(st, user, filiale);
        db.closeDB();
        return out;
    }

    /**
     *
     * @param user
     * @return
     */
    public static Till get_till_opened(String user) {
        Db_Master db = new Db_Master();
        Till out = db.get_till_opened(user);
        db.closeDB();
        return out;
    }

    /**
     *
     * @param listTill
     * @param safe_1
     * @return
     */
    public static boolean containsSafe(ArrayList<Till> listTill, Till safe_1) {
        for (int i = 0; i < listTill.size(); i++) {
            Till sa = listTill.get(i);
            if (sa.getCod().equals(safe_1.getCod())) {
                return true;
            }
        }
        return false;
    }

    /**
     *
     * @param filiale
     * @param codtill
     * @return
     */
    public static Till get_single_Till(String filiale, String codtill) {
        Db_Master db = new Db_Master();
        Till t = db.get_single_Till(filiale, codtill);
        db.closeDB();
        return t;
    }

    /**
     *
     * @param filiale
     * @param codtill
     * @param type
     * @return
     */
    public static Till get_single_Till(String filiale, String codtill, String type) {
        Db_Master db = new Db_Master();
        if (type != null) {
            if (type.equals("central")) {
                db = new Db_Master(true);
                if (db.getC() == null) {
                    db = new Db_Master();
                }
            }
        }
        Till t = db.get_single_Till(filiale, codtill);
        db.closeDB();
        return t;
    }

    /**
     *
     * @return
     */
    public static Till getSafe() {
        Db_Master db = new Db_Master();
        ArrayList<Till> array_till = db.list_till_status(null, null);
        db.closeDB();
        for (int i = 0; i < array_till.size(); i++) {
            Till sa = array_till.get(i);
            if (sa.isSafe()) {
                return sa;
            }
        }
        return null;
    }

    /**
     *
     * @param filiale
     * @return
     */
    public static Till getSafe(String filiale) {
        Db_Master db = new Db_Master();
        ArrayList<Till> array_till = db.list_till_status(null, null, filiale);
        // ArrayList<Till> array_till = db.list_ALLtill(filiale));
        db.closeDB();
        for (int i = 0; i < array_till.size(); i++) {
            Till sa = array_till.get(i);
            if (sa.isSafe()) {
                return sa;
            }
        }
        return null;
    }

    /**
     *
     * @return
     */
    public static ArrayList<String[]> list_oc_change() {
        Db_Master db = new Db_Master();
        ArrayList<String[]> out = db.list_oc_change();
        db.closeDB();
        return out;
    }

    /**
     *
     * @param cod_oc
     * @return
     */
    public static ArrayList<String[]> list_oc_change(String cod_oc) {
        Db_Master db = new Db_Master();
        ArrayList<String[]> out = db.list_oc_change(cod_oc);
        db.closeDB();
        return out;
    }

    /**
     *
     * @param cod_oc
     * @return
     */
    public static ArrayList<String[]> list_oc_change_fisici(String cod_oc) {
        Db_Master db = new Db_Master();
        ArrayList<String[]> out = db.list_oc_change_fisici(cod_oc);
        db.closeDB();
        return out;
    }

    /**
     *
     * @return
     */
    public static ArrayList<String[]> list_oc_change_real() {
        Db_Master db = new Db_Master();
        ArrayList<String[]> out = db.list_oc_change_real();
        db.closeDB();
        return out;
    }

    /**
     *
     * @return
     */
    public static ArrayList<String[]> list_oc_change_real_et() {
        Db_Master db = new Db_Master();
        ArrayList<String[]> out = db.list_oc_change_real_et();
        db.closeDB();
        return out;
    }

    /**
     *
     * @param cod
     * @return
     */
    public static ArrayList<String[]> list_oc_change_real(String cod) {
        Db_Master db = new Db_Master();
        ArrayList<String[]> out = db.list_oc_change_real(cod);
        db.closeDB();
        return out;
    }

    /**
     *
     * @return
     */
    public static ArrayList<Currency> list_all_currency() {
        Db_Master db = new Db_Master();
        ArrayList<Currency> out = db.list_figures();
        db.closeDB();
        return out;
    }

    /**
     *
     * @param cod
     * @param array
     * @return
     */
    public static String formatALCurrency(String cod, ArrayList<Currency> array) {
        for (int i = 0; i < array.size(); i++) {
            if (cod.equals(((Currency) array.get(i)).getCode())) {
                return ((Currency) array.get(i)).getDescrizione();
            }
        }
        return "-";
    }

    /**
     *
     * @param cod
     * @param array
     * @return
     */
    public static Currency getALCurrency(String cod, ArrayList<Currency> array) {
        for (int i = 0; i < array.size(); i++) {
            if (cod.equals(((Currency) array.get(i)).getCode())) {
                return array.get(i);
            }
        }
        return null;
    }

    /**
     *
     * @param cod_oc
     * @return
     */
    public static ArrayList<String[]> list_oc_change_cuts(String cod_oc) {
        Db_Master db = new Db_Master();
        ArrayList<String[]> out = db.list_oc_change_cuts(cod_oc);
        db.closeDB();
        return out;
    }

    /**
     *
     * @param cod_oc
     * @return
     */
    public static ArrayList<String[]> list_oc_change_cuts_fisici(String cod_oc) {
        Db_Master db = new Db_Master();
        ArrayList<String[]> out = db.list_oc_change_cuts_fisici(cod_oc);
        db.closeDB();
        return out;
    }

    /**
     *
     * @param cod_oc
     * @return
     */
    public static ArrayList<String[]> list_oc_change_cuts_real(String cod_oc) {
        Db_Master db = new Db_Master();
        ArrayList<String[]> out = db.list_oc_change_cuts_real(cod_oc);
        db.closeDB();
        return out;
    }

    /**
     *
     * @param cod_oc
     * @return
     */
    public static ArrayList<String[]> list_oc_change_cuts_real_et(String cod_oc) {
        Db_Master db = new Db_Master();
        ArrayList<String[]> out = db.list_oc_change_cuts_real_et(cod_oc);
        db.closeDB();
        return out;
    }

    /**
     *
     * @param cod_oc
     * @return
     */
    public static ArrayList<String[]> list_oc_nochange(String cod_oc) {
        Db_Master db = new Db_Master();
        ArrayList<String[]> out = db.list_oc_nochange(cod_oc);
        db.closeDB();
        return out;
    }

    /**
     *
     * @param cod_oc
     * @return
     */
    public static ArrayList<String[]> list_oc_nochange_fisici(String cod_oc) {
        Db_Master db = new Db_Master();
        ArrayList<String[]> out = db.list_oc_nochange_fisici(cod_oc);
        db.closeDB();
        return out;
    }

    /**
     *
     * @param cod_oc
     * @return
     */
    public static ArrayList<String[]> list_oc_errors(String cod_oc) {
        Db_Master db = new Db_Master();
        ArrayList<String[]> out = db.list_oc_errors(cod_oc);
        db.closeDB();
        return out;
    }

    /**
     *
     * @param day
     * @return
     */
    public static ArrayList<String[]> list_oc_nochange(int day) {
        Db_Master db = new Db_Master();
        ArrayList<String[]> out = db.list_oc_nochange(day);
        db.closeDB();
        return out;
    }

    /**
     *
     * @param cod_oc
     * @return
     */
    public static ArrayList<String[]> list_oc_nochange_real(String cod_oc) {
        Db_Master db = new Db_Master();
        ArrayList<String[]> out = db.list_oc_nochange_real(cod_oc);
        db.closeDB();
        return out;
    }

    /**
     *
     * @param cod
     * @return
     */
    public static Client query_Client(String cod) {
        Db_Master db = new Db_Master();
        Client out = db.query_Client(cod);
        db.closeDB();
        return out;
    }

    /**
     *
     * @param codtr
     * @param cod
     * @return
     */
    public static Client query_Client_transaction(String codtr, String cod) {
        Db_Master db = new Db_Master();
        Client out = db.query_Client_transaction(codtr, cod);
        out.setRepceca(db.query_Client_NOITA(codtr, cod));
        db.closeDB();
        return out;
    }

    /**
     *
     * @param codtr
     * @param cod
     * @param type
     * @return
     */
    public static Client query_Client_transaction(String codtr, String cod, String type) {
        Db_Master db = new Db_Master();
        if (type != null) {
            if (type.equals("central")) {
                db = new Db_Master(true);
                if (db.getC() == null) {
                    db = new Db_Master();
                }
            }
        }
        Client out = db.query_Client_transaction(codtr, cod);
        out.setRepceca(db.query_Client_NOITA(codtr, cod));
        db.closeDB();
        return out;
    }

    /**
     *
     * @param cod
     * @return
     */
    public static Client query_Client_central(String cod) {
        Db_Master db = new Db_Master(true);
        if (db.getC() == null) {
            db = new Db_Master();
        }
        Client out = db.query_Client(cod);
        db.closeDB();
        return out;
    }

    /**
     *
     * @param cod_oc
     * @return
     */
    public static ArrayList<String[]> list_oc_pos(String cod_oc) {
        Db_Master db = new Db_Master();
        ArrayList<String[]> out = db.list_oc_pos(cod_oc);
        db.closeDB();
        return out;
    }

    /**
     *
     * @param cod_oc
     * @return
     */
    public static ArrayList<String[]> list_oc_allpos(String cod_oc) {
        Db_Master db = new Db_Master();
        ArrayList<String[]> out = db.list_oc_allpos(cod_oc);
        db.closeDB();
        return out;
    }

    /**
     *
     * @return
     */
    public static ArrayList<String[]> list_oc_nochange_real() {
        Db_Master db = new Db_Master();
        ArrayList<String[]> out = db.list_oc_nochange_real();
        db.closeDB();
        return out;
    }

    /**
     *
     * @return
     */
    public static ArrayList<NC_category> all_nc_category() {
        Db_Master db = new Db_Master();
        ArrayList<NC_category> out = db.query_nc_category("");
        db.closeDB();
        return out;
    }

    /**
     *
     * @return
     */
    public static ArrayList<NC_causal> all_nc_causal() {
        Db_Master db = new Db_Master();
        ArrayList<NC_causal> out = db.query_nc_causal("");
        db.closeDB();
        return out;
    }

    /**
     *
     * @param cod
     * @param array
     * @return
     */
    public static String formatALNC_category(String cod, ArrayList<NC_category> array) {
        for (int i = 0; i < array.size(); i++) {
            if (cod.equals(array.get(i).getGruppo_nc())) {
                return array.get(i).getDe_gruppo_nc();
            }
        }
        return "-";
    }

    /**
     *
     * @param cod
     * @param array
     * @return
     */
    public static String formatAL_Till(String cod, ArrayList<Till> array) {
        for (int i = 0; i < array.size(); i++) {
            if (cod.equals(array.get(i).getCod())) {
                return array.get(i).getName();
            }
        }
        return "-";
    }

    /**
     *
     * @param cod
     * @param array
     * @return
     */
    public static String formatALNC_causal(String cod, ArrayList<NC_causal> array) {
        for (int i = 0; i < array.size(); i++) {
            if (cod.equals(array.get(i).getCausale_nc())) {
                return array.get(i).getDe_causale_nc();
            }
        }
        return "-";
    }

    /**
     *
     * @param cod
     * @param array
     * @return
     */
    public static boolean isExternalServicesCausal(String cod, ArrayList<NC_causal> array) {
        for (int i = 0; i < array.size(); i++) {
            if (cod.equals(array.get(i).getCausale_nc())) {
                return array.get(i).getPaymat().equals("1");
            }
        }
        return false;
    }

    /**
     *
     * @param cod
     * @param array
     * @param array_nc_descr
     * @return
     */
    public static String formatALNC_causal_ncde(String cod, ArrayList<NC_causal> array,
            ArrayList<String[]> array_nc_descr) {
        for (int i = 0; i < array.size(); i++) {
            if (cod.equals(array.get(i).getCausale_nc())) {
                for (int j = 0; j < array_nc_descr.size(); j++) {
                    if (array.get(i).getNc_de().equals(array_nc_descr.get(j)[0])) {
                        return array_nc_descr.get(j)[1];
                    }
                }
            }
        }
        return "-";
    }

    /**
     *
     * @return
     */
    public static ArrayList<String[]> list_bank_enabled() {
        Db_Master db = new Db_Master();
        ArrayList<String[]> out = db.list_bank_enabled();
        db.closeDB();
        return out;
    }

    /**
     *
     * @return
     */
    public static ArrayList<String[]> list_only_bank_enabled() {
        Db_Master db = new Db_Master();
        ArrayList<String[]> out = db.list_only_bank_enabled();
        db.closeDB();
        return out;
    }

    /**
     *
     * @return
     */
    public static ArrayList<String[]> list_only_bank_enabled_new() {
        Db_Master db = new Db_Master();
        ArrayList<String[]> out = db.list_only_bank_enabled_new();
        db.closeDB();
        return out;
    }

    /**
     *
     * @return
     */
    public static ArrayList<Branch> list_branch_enabled() {
        Db_Master db = new Db_Master();
        ArrayList<Branch> out = db.list_branch_enabled();
        db.closeDB();
        return out;
    }

    /**
     *
     * @return
     */
    public static ArrayList<Branch> list_branch_completeAFTER311217() {
        Db_Master db = new Db_Master();
        ArrayList<Branch> out = db.list_branch_completeAFTER311217();
        db.closeDB();
        return out;
    }

    /**
     *
     * @param cod
     * @return
     */
    public static String formatBankBranch(String cod) {
        if (cod != null) {
            if (cod.equals("BA")) {
                return "Bank";
            }
            if (cod.equals("BR")) {
                return "Branch";
            }
            if (cod.equals("PO")) {
                return "POS / BANK ACCOUNT";
            }
        }
        return "";
    }

    /**
     *
     * @param cod
     * @param type
     * @param array_bank
     * @param array_branch
     * @param credit_card
     * @return
     */
    public static String formatBankBranch(String cod, String type, ArrayList<String[]> array_bank,
            ArrayList<Branch> array_branch, ArrayList<String[]> credit_card) {
        if ((cod != null)
                && (type != null)) {
            if (type.equals("BA")) {
                for (int j = 0; j < array_bank.size(); j++) {
                    if (cod.equals(((String[]) array_bank.get(j))[0])) {
                        return ((String[]) array_bank.get(j))[1].toUpperCase();
                    }
                }

                for (int j = 0; j < credit_card.size(); j++) {
                    if (cod.equals(((String[]) credit_card.get(j))[0])) {
                        return ((String[]) credit_card.get(j))[1].toUpperCase();
                    }
                }

            }

            if (type.equals("BR")) {
                if (cod.equals("000")) {
                    return "000 - HEAD OFFICE";
                }
                for (int j = 0; j < array_branch.size(); j++) {

                    if (cod.equals((array_branch.get(j)).getCod())) {
                        return (array_branch.get(j)).getCod() + " - "
                                + (array_branch.get(j)).getDe_branch().toUpperCase();
                    }
                }
            }
        }

        return "";
    }

    /**
     *
     * @param cod
     * @param type
     * @param array_bank
     * @param array_branch
     * @return
     */
    public static String formatBankBranchReport(String cod, String type, ArrayList<String[]> array_bank,
            ArrayList<Branch> array_branch) {
        if ((cod != null)
                && (type != null)) {
            if (type.equals("BA")) {
                for (int j = 0; j < array_bank.size(); j++) {
                    if (cod.equals(((String[]) array_bank.get(j))[0])) {
                        return ((String[]) array_bank.get(j))[1].toUpperCase();
                    }
                }
            }
            if (type.equals("BR")) {
                for (int j = 0; j < array_branch.size(); j++) {
                    if (cod.equals((array_branch.get(j)).getCod())) {
                        return (array_branch.get(j)).getDe_branch();
                    }
                }
            }
        }

        return "";
    }

    /**
     *
     * @param cod
     * @param array_branch
     * @return
     */
    public static Branch get_Branch(String cod, ArrayList<Branch> array_branch) {

        // if (cod.equals("000")) {
        // Branch br = new Branch();
        // br.setCod("000");
        // br.setDe_branch("CENTRAL");
        // br.setG01("CENTR");
        // return br;
        // }
        for (int j = 0; j < array_branch.size(); j++) {
            if (cod.equals((array_branch.get(j)).getCod())) {
                return (array_branch.get(j));
            }
        }

        return null;
    }

    /**
     *
     * @param cod
     * @return
     */
    public static Ch_transaction query_transaction_ch(String cod) {
        Db_Master db = new Db_Master();
        Ch_transaction out = db.query_transaction_ch(cod);
        db.closeDB();
        return out;
    }

    /**
     *
     * @param cod
     * @param type
     * @return
     */
    public static Ch_transaction query_transaction_ch(String cod, String type) {
        Db_Master db = new Db_Master();
        if (type != null) {
            if (type.equals("central")) {
                db = new Db_Master(true);
                if (db.getC() == null) {
                    db = new Db_Master();
                }
            }
        }

        Ch_transaction out = db.query_transaction_ch(cod);
        db.closeDB();
        return out;
    }

    /**
     *
     * @param cod
     * @return
     */
    public static Ch_transaction query_transaction_ch_temp(String cod) {
        Db_Master db = new Db_Master();
        Ch_transaction out = db.query_transaction_ch_temp(cod);
        db.closeDB();
        return out;
    }

    /**
     *
     * @param g01
     * @param g02
     * @param g03
     * @param rv
     * @return
     */
    public static ArrayList<String[]> list_group_branch(boolean g01, boolean g02, boolean g03, boolean rv) {
        ArrayList<String[]> out = new ArrayList<>();
        Db_Master db = new Db_Master();
        if (g01) {
            out = db.list_group_branch("select_g01");
        } else if (g02) {
            out = db.list_group_branch("select_g02");
        } else if (g03) {
            out = db.list_group_branch("select_g03");
        } else if (rv) {
            out = db.list_group_branch("select_rv");
        }
        db.closeDB();
        return out;
    }

    /**
     *
     * @return
     */
    public static ArrayList<String[]> list_group_branch() {
        Db_Master db = new Db_Master();
        ArrayList<String[]> out = db.list_group_branch("selectgrouptype");
        db.closeDB();
        return out;
    }

    /**
     *
     * @return
     */
    public static ArrayList<String[]> list_branch_group() {
        Db_Master db = new Db_Master();
        ArrayList<String[]> out = db.list_branch_group();
        db.closeDB();
        return out;
    }

    /**
     *
     * @return
     */
    public static ArrayList<String[]> selectgroupbranch() {
        Db_Master db = new Db_Master();
        ArrayList<String[]> out = db.selectgroupbranch();
        db.closeDB();
        return out;
    }

    /**
     *
     * @param table
     * @return
     */
    public static String formatTypeGroup(String table) {
        if (table.equals("select_g01")) {
            return "1";
        }
        if (table.equals("select_g02")) {
            return "2";
        }
        if (table.equals("select_g03")) {
            return "3";
        }
        if (table.equals("select_rv")) {
            return "4";
        }
        return "-";
    }

    /**
     *
     * @param type
     * @return
     */
    public static String formatTableGroup(String type) {
        if (type.equals("1")) {
            return "select_g01";
        }
        if (type.equals("2")) {
            return "select_g02";
        }
        if (type.equals("3")) {
            return "select_g03";
        }
        if (type.equals("4")) {
            return "select_rv";
        }
        return "-";
    }

    /**
     *
     * @param cod
     * @return
     */
    public static Users get_user(String cod) {
        Db_Master db = new Db_Master();
        Users out = db.get_user(cod);
        db.closeDB();
        return out;
    }

    /**
     *
     * @param cod
     * @return
     */
    public static String get_user_ATL(String cod) {
        Db_Master db = new Db_Master(true);
        if (db.getC() == null) {
            return "";
        }
        String out = db.get_codice_ATL(cod, "user_atl");
        db.closeDB();
        return out;
    }

    /**
     *
     * @param cod
     * @return
     */
    public static String get_branch_ATL(String cod) {
        Db_Master db = new Db_Master(true);
        if (db.getC() == null) {
            return "";
        }
        String out = db.get_codice_ATL(cod, "branch_atl");
        db.closeDB();
        return out;
    }

    /**
     *
     * @param cod
     * @param li
     * @return
     */
    public static Users get_user(String cod, ArrayList<Users> li) {
        for (int i = 0; i < li.size(); i++) {
            if (li.get(i).getCod().equals(cod)) {
                return li.get(i);
            }
        }
        return null;
    }

    /**
     *
     * @param us
     * @param li
     * @return
     */
    public static Users get_username(String us, ArrayList<Users> li) {
        for (int i = 0; i < li.size(); i++) {
            if (li.get(i).getUsername().equals(us)) {
                return li.get(i);
            }
        }
        return null;
    }

    /**
     *
     * @return
     */
    public static ArrayList<Users> list_all_users() {
        Db_Master db = new Db_Master();
        ArrayList<Users> result = db.list_all_users();
        db.closeDB();
        return result;
    }

    /**
     *
     * @return
     */
    public static ArrayList<Users> list_all_users_show() {
        Db_Master db = new Db_Master();
        ArrayList<Users> result = db.list_all_users_show();
        db.closeDB();

        return result;
    }

    /**
     *
     * @return
     */
    public static ArrayList<Users> list_all_users_enabled() {
        Db_Master db = new Db_Master();
        ArrayList<Users> result = db.list_all_users_enabled();
        db.closeDB();
        return result;
    }

    /**
     *
     * @param tr_cod
     * @param piattaforma
     * @param filale
     * @param usercode
     * @param cl
     * @return
     */
    public static BlackListsAT verificaBlackList(
            String tr_cod, String piattaforma,
            String filale, String usercode,
            Client cl) {

        BlackListsAT bl = new BlackListsAT();
        bl.setError("-");
        bl.setTransaction_code(tr_cod);
        bl.setNomefile("-");
        bl.setSoggetto(cl.getCognome() + " " + cl.getNome());

        try {
            Db_Master db = new Db_Master();
            Office of = db.get_national_office();
            String method = db.getPath("servlet.blacklist");
            method = method.split("\\?")[0];
            db.closeDB();
            String macurl = of.getUrl_bl();
            if (!macurl.endsWith("/")) {
                macurl = macurl + "/";
            }
            String fileURL = macurl + method;
            String XML_DATA;
            try (CloseableHttpClient client = create().build()) {
                HttpPost post = new HttpPost(fileURL);
                List<NameValuePair> nameValuePairs = new ArrayList<>();
                nameValuePairs.add(new BasicNameValuePair("Piattaforma", piattaforma));
                nameValuePairs.add(new BasicNameValuePair("Committente", filale));
                nameValuePairs.add(new BasicNameValuePair("Operatore", usercode));
                nameValuePairs.add(new BasicNameValuePair("Cognome", cl.getCognome()));
                nameValuePairs.add(new BasicNameValuePair("Nome", cl.getNome()));
                // RICHIESTA GUERRISI 07-06-2023
                // if (cl.getCodfisc() != null) {
                // nameValuePairs.add(new BasicNameValuePair("CodiceFiscale",
                // cl.getCodfisc().replaceAll("-", "")));
                // }
                // nameValuePairs.add(new BasicNameValuePair("DataDiNascita",
                // cl.getDt_nascita()));
                // nameValuePairs.add(new BasicNameValuePair("PaeseNascita",
                // cl.getNazione_nascita()));
                // nameValuePairs.add(new BasicNameValuePair("NumeroDocumento",
                // cl.getNumero_documento()));
                // nameValuePairs.add(new BasicNameValuePair("PaeseResidenza",
                // cl.getNazione()));
                //
                post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                HttpResponse response = client.execute(post);
                BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
                XML_DATA = "";
                String line;
                while ((line = rd.readLine()) != null) {
                    XML_DATA += line.trim();
                }
            }
            if (!XML_DATA.equals("")) {
                DocumentBuilderFactory factory = newInstance();
                DocumentBuilder builder1 = factory.newDocumentBuilder();
                Document doc = builder1.parse(new ByteArrayInputStream(XML_DATA.getBytes()));
                bl.setCodiceTransazione(getNodeValuefromName(doc, "CodiceTransazione", 0));
                bl.setDataTransazione(getNodeValuefromName(doc, "DataTransazione", 0));
                bl.setEsito(getNodeValuefromName(doc, "Esito", 0));
            } else {
                bl.setEsito("2");
            }
        } catch (Exception ex) {
            bl.setError("Exception: " + ex.getMessage());
            bl.setEsito("2");
            insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
        }
        return bl;
    }

    /**
     *
     * @param ses
     * @return
     */
    public static int[] countNews(HttpSession ses) {
        String user = (String) ses.getAttribute("us_cod");
        if (user == null) {
            int[] ou = { 0, 0 };
            return ou;
        }
        Db_Master db = new Db_Master();
        int[] result = db.countNews(user);
        db.closeDB();
        return result;
    }

    /**
     *
     * @return
     */
    public static String[] getFil() {
        Db_Master db = new Db_Master();
        String[] fil = db.getCodLocal(false);
        db.closeDB();
        return fil;
    }

    /**
     *
     * @param se
     * @return
     */
    public static String getStringHeader(HttpSession se) {
        if (se.getAttribute("us_cod") == null) {
            // return null
            return "019 - Roma Termini | 9999 - alena1598";
        } else {
            return se.getAttribute("us_fil") + " - " + se.getAttribute("us_nfi") + " | " + se.getAttribute("us_cod")
                    + " - " + se.getAttribute("us_user");
        }
    }

    /**
     *
     * @param se
     * @return
     */
    public static boolean companyenabled(HttpSession se) {
        Db_Master db = new Db_Master();
        boolean en = db.companyenabled(Utility.checkAttribute(se, "us_fil"));
        db.closeDB();
        return en;
    }

    /**
     *
     * @param filiale
     * @param gruppo_nc
     * @param causale_nc
     * @return
     */
    public static ArrayList<String[]> nc_causal_payment(String filiale, String gruppo_nc, String causale_nc) {
        Db_Master db = new Db_Master();
        ArrayList<String[]> out = db.nc_causal_payment(filiale, gruppo_nc, causale_nc);
        db.closeDB();
        return out;
    }

    /**
     *
     * @param filiale
     * @param gruppo_nc
     * @param causale_nc
     * @return
     */
    public static ArrayList<String[]> nc_causal_payment_support(String filiale, String gruppo_nc, String causale_nc) {
        Db_Master db = new Db_Master();
        ArrayList<String[]> out = db.nc_causal_payment_support(filiale, gruppo_nc, causale_nc);
        db.closeDB();
        return out;
    }

    /**
     *
     * @return
     */
    public static ArrayList<String[]> nc_causal_payment_enabled() {
        Db_Master db = new Db_Master();
        ArrayList<String[]> out = db.nc_causal_payment_enabled();
        db.closeDB();
        return out;
    }

    /**
     *
     * @return
     */
    public static String isBlockedOperation() {
        Db_Master db = new Db_Master();
        String usr = db.isBlockedOperation();
        db.closeDB();
        return usr;
    }

    /**
     *
     * @param session
     * @return
     */
    public static boolean insertBlockedOperation(HttpSession session) {
        Db_Master db = new Db_Master();
        boolean usr = db
                .insertBlockedOperation(session.getAttribute("us_cod") + " | " + session.getAttribute("us_user"));
        db.closeDB();
        return usr;
    }

    /**
     *
     * @param cod
     * @param idfrom
     * @param idto
     * @param filiale
     * @return
     */
    public static boolean delete_IT_change(String cod, String idfrom, String idto, String filiale) {
        Db_Master db = new Db_Master();
        boolean es = db.delete_IT_change(cod, idfrom, idto, filiale);
        db.closeDB();
        return es;
    }

    /**
     *
     * @param cod
     * @param idfrom
     * @param filiale
     * @return
     */
    public static boolean delete_ET_change(String cod, String idfrom, String filiale) {
        Db_Master db = new Db_Master();
        boolean es = db.delete_ET_change(cod, idfrom, filiale);
        db.closeDB();
        return es;
    }

    /**
     *
     * @param cod
     * @param idfrom
     * @param filiale
     * @return
     */
    public static boolean delete_ET_nochange(String cod, String idfrom, String filiale) {
        Db_Master db = new Db_Master();
        boolean es = db.delete_ET_nochange(cod, idfrom, filiale);
        db.closeDB();
        return es;
    }

    /**
     *
     * @param idfrom
     * @param idto
     * @param filiale
     * @return
     */
    public static boolean delete_IT_change_temp(String idfrom, String idto, String filiale) {
        Db_Master db = new Db_Master();
        boolean es = db.delete_IT_change_temp(idfrom, idto, filiale);
        db.closeDB();
        return es;
    }

    /**
     *
     * @param idfrom
     * @param idto
     * @param filiale
     * @return
     */
    public static boolean delete_ET_change_temp(String idfrom, String idto, String filiale) {
        Db_Master db = new Db_Master();
        boolean es = db.delete_ET_change_temp(idfrom, idto, filiale);
        db.closeDB();
        return es;
    }

    /**
     *
     * @param idfrom
     * @param idto
     * @param filiale
     * @return
     */
    public static boolean delete_IT_nochange_temp(String idfrom, String idto, String filiale) {
        Db_Master db = new Db_Master();
        boolean es = db.delete_IT_nochange_temp(idfrom, idto, filiale);
        db.closeDB();
        return es;
    }

    /**
     *
     * @param cod
     * @param idfrom
     * @param idto
     * @param filiale
     * @return
     */
    public static boolean delete_IT_nochange(String cod, String idfrom, String idto, String filiale) {
        Db_Master db = new Db_Master();
        boolean es = db.delete_IT_nochange(cod, idfrom, idto, filiale);
        db.closeDB();
        return es;
    }

    /**
     *
     * @param cod
     * @return
     */
    public static ET_change get_ET_change(String cod) {
        Db_Master db = new Db_Master();
        ET_change es = db.get_ET_change(cod);
        db.closeDB();
        return es;
    }

    /**
     *
     * @param cod
     * @return
     */
    public static ArrayList<ET_change> get_ET_nochange_value(String cod) {
        Db_Master db = new Db_Master();
        ArrayList<ET_change> es = db.get_ET_nochange_value(cod);
        db.closeDB();
        return es;
    }

    /**
     *
     * @param cod
     * @return
     */
    public static String note_fromBranch(String cod) {
        Db_Master db = new Db_Master();
        String es = db.note_fromBranch(cod);
        db.closeDB();
        return es;
    }

    /**
     *
     * @param cod
     * @return
     */
    public static ArrayList<ET_change> get_ET_change_value(String cod) {
        Db_Master db = new Db_Master();
        ArrayList<ET_change> es = db.get_ET_change_value(cod);
        db.closeDB();
        return es;
    }

    /**
     *
     * @param cod
     * @return
     */
    public static ArrayList<ET_change> get_ET_change_tg(String cod) {
        Db_Master db = new Db_Master();
        ArrayList<ET_change> es = db.get_ET_change_tg(cod);
        db.closeDB();
        return es;
    }

    /**
     *
     * @param fil
     * @param stato
     * @param type
     * @return
     */
    public static ArrayList<ET_change> get_ET_change_frombranch(String fil, String stato, String type) {
        Db_Master db = new Db_Master();
        ArrayList<ET_change> es = db.get_ET_change_frombranch(fil, stato, type);
        db.closeDB();
        return es;
    }

    /**
     *
     * @param ndg
     * @param stato
     * @return
     */
    public static ArrayList<Company_attach> company_listAttachment(String ndg, String stato) {
        Db_Master db = new Db_Master();
        ArrayList<Company_attach> es = db.listAttachment(ndg, stato);
        db.closeDB();
        return es;
    }

    /**
     *
     * @param et
     * @param cod
     * @return
     */
    public static String getIdfromCod_ETchange(ArrayList<ET_change> et, String cod) {
        for (int i = 0; i < et.size(); i++) {
            if (et.get(i).getCod().equals(cod)) {
                return et.get(i).getId();
            }
        }
        return "-";
    }

    /**
     *
     * @param et
     * @param cod
     * @return
     */
    public static String getFilialefromCod_ETchange(ArrayList<ET_change> et, String cod) {
        for (int i = 0; i < et.size(); i++) {
            if (et.get(i).getCod().equals(cod)) {
                return et.get(i).getFiliale();
            }
        }
        return "-";
    }

    /**
     *
     * @param id
     * @param base64
     */
    public static void insertConf(String id, String base64) {
        Db_Master db = new Db_Master();
        db.insertConf(id, base64);
        db.closeDB();
    }

    /**
     *
     * @param id
     * @param base64
     */
    public static void insertConfconAGG(String id, String base64) {
        Db_Master db = new Db_Master(true);
        db.insertConfconAGG(id, base64);
        db.closeDB();
    }

    /**
     *
     * @param id
     * @param base64
     */
    public static void updateConfconAGG(String id, String base64) {
        Db_Master db = new Db_Master(true);
        db.updateConfconAGG(id, base64);
        db.closeDB();
    }

    public static void updateConf(String id, String base64) {
        Db_Master db = new Db_Master(true);
        db.updateConf(id, base64);
        db.closeDB();
    }

    /**
     *
     * @param id
     * @return
     */
    public static String getConf(String id) {
        Db_Master db = new Db_Master();
        String out = db.getConf(id);
        db.closeDB();
        return out;
    }

    public static String getConfCentral(String id) {
        Db_Master db = new Db_Master(true);
        String out = db.getConf(id);
        db.closeDB();
        return out;
    }

    /**
     *
     * @param id
     * @return
     */
    public static String getPath(String id) {
        Db_Master db = new Db_Master();
        String out = db.getPath(id);
        db.closeDB();
        return out;
    }

    /**
     *
     * @param cod
     * @return
     */
    public static NC_transaction get_NC_transaction(String cod) {
        Db_Master db = new Db_Master();
        NC_transaction nc = db.get_NC_transaction(cod);
        db.closeDB();
        return nc;
    }

    /**
     *
     * @param cod
     * @param type
     * @return
     */
    public static NC_transaction get_NC_transaction(String cod, String type) {
        Db_Master db = new Db_Master();

        if (type != null) {
            if (type.equals("central")) {
                db = new Db_Master(true);
                if (db.getC() == null) {
                    db = new Db_Master();
                }
            }
        }
        NC_transaction nc = db.get_NC_transaction(cod);
        db.closeDB();
        return nc;
    }

    /**
     *
     * @param cod
     * @return
     */
    public static String format_FG_dogana(String cod) {

        switch (cod) {
            case "---":
                return cod;
            case "00":
                return "Customs";
            case "01":
                return "No Customs";
            default:
                break;
        }
        return cod;
    }

    /**
     *
     * @param cod
     * @return
     */
    public static IT_change get_internal_transfer(String cod) {
        Db_Master db = new Db_Master();
        IT_change it = db.get_internal_transfer(cod);
        db.closeDB();
        return it;
    }

    /**
     *
     * @param cod
     * @return
     */
    public static ArrayList<IT_change> get_internal_transfer_ch_value(String cod) {
        Db_Master db = new Db_Master();
        ArrayList<IT_change> es = db.get_internal_transfer_ch_value(cod);
        db.closeDB();
        return es;
    }

    /**
     *
     * @param cod
     * @return
     */
    public static ArrayList<IT_change> get_internal_transfer_noch_value(String cod) {
        Db_Master db = new Db_Master();
        ArrayList<IT_change> es = db.get_internal_transfer_noch_value(cod);
        db.closeDB();
        return es;
    }

    /**
     *
     * @param cod
     * @return
     */
    public static ArrayList<IT_change> get_internal_transfer_ch_tg(String cod) {
        Db_Master db = new Db_Master();
        ArrayList<IT_change> es = db.get_internal_transfer_ch_tg(cod);
        db.closeDB();
        return es;
    }

    /**
     *
     * @return
     */
    public static ArrayList<String[]> list_kind_pos() {
        Db_Master db = new Db_Master();
        ArrayList<String[]> es = db.list_kind_pos();
        db.closeDB();
        return es;
    }

    /**
     *
     * @param session
     * @return
     */
    public static boolean isBlockedOperationUser(HttpSession session) {
        Db_Master db = new Db_Master();
        boolean es = db
                .isBlockedOperationUser(session.getAttribute("us_cod") + " | " + session.getAttribute("us_user"));
        db.closeDB();
        return es;
    }

    /**
     *
     * @return
     */
    public static String get_last_excel_upl() {
        Db_Master db = new Db_Master();
        String es = db.get_last_excel_upl();
        db.closeDB();
        return es;
    }

    /**
     *
     * @return
     */
    public static String get_last_excel_rep() {
        Db_Master db = new Db_Master();
        String es = db.get_last_excel_rep();
        db.closeDB();
        return es;
    }

    /**
     *
     * @param filiale
     * @return
     */
    public static String get_last_modify_rate(String filiale) {
        Db_Master db = new Db_Master();
        String es = db.get_last_modify_rate(filiale);
        db.closeDB();
        return es;
    }

    /**
     *
     * @param cod
     * @param type
     * @param filename
     * @param fileout
     * @param user
     * @param dt_start
     */
    public static void insert_excel_upl(String cod, String type, String filename, String fileout, String user,
            String dt_start) {
        Db_Master db = new Db_Master();
        db.insert_excel_upl(cod, type, filename, fileout, user, dt_start);
        db.closeDB();
    }

    /**
     *
     * @param cod
     * @param type
     * @param filename
     * @param fileout
     * @param user
     * @param dt_start
     */
    public static void insert_excel_upl_SP(String cod, String type, String filename,
            String fileout, String user, String dt_start) {
        Db_Master db = new Db_Master();
        db.insert_excel_upl_SP(cod, type, filename, fileout, user, dt_start);
        db.closeDB();
    }

    /**
     *
     * @return
     */
    public static String getNow_norm() {
        Db_Master db = new Db_Master();
        DateTime es = db.getNowDT();
        db.closeDB();
        return es.toString(patternnormdate);
    }

    /**
     *
     * @return
     */
    public static String getNow_filter() {
        return new DateTime().toString(patternnormdate_filter);
    }

    /**
     *
     * @param cod_oc
     * @return
     */
    public static Openclose query_oc(String cod_oc) {
        Db_Master db = new Db_Master();
        Openclose es = db.query_oc(cod_oc);
        db.closeDB();
        return es;
    }

    /**
     *
     * @param cod_oc
     * @param type
     * @return
     */
    public static Openclose query_oc(String cod_oc, String type) {
        Db_Master db = new Db_Master();
        if (type != null) {
            if (type.equals("central")) {
                db = new Db_Master(true);
                if (db.getC() == null) {
                    db = new Db_Master();
                }
            }
        }
        Openclose es = db.query_oc(cod_oc);
        db.closeDB();
        return es;
    }

    /**
     *
     * @param cod
     * @return
     */
    public static boolean delete_oc(String cod) {
        Db_Master db = new Db_Master();
        boolean es = db.delete_oc(cod);
        db.closeDB();
        return es;
    }

    /**
     *
     * @param cod
     * @return
     */
    public static Booking get_prenot(String cod) {
        Db_Master db = new Db_Master();
        Booking es = db.get_prenot(cod);
        db.closeDB();
        return es;
    }

    /**
     *
     * @param cod
     * @return
     */
    public static ArrayList<String[]> search_curr_till(String cod) {
        Db_Master db = new Db_Master();
        ArrayList<String[]> li = db.search_curr_till(cod);
        // ArrayList<String[]> li = new ArrayList<>();
        // String[] ou = {"BBD - Dollaro Barbados","000 - Safe","OPEN","15","750.00"};
        // li.add(ou);
        db.closeDB();
        return li;
    }

    /**
     *
     * @param dtr
     * @return
     */
    public static boolean isToday(String dtr) {
        String td = new DateTime().toString(patternsql);
        return td.equals(dtr);
    }

    /**
     *
     * @param dtr
     * @return
     */
    public static boolean isTomorrow(String dtr) {
        String td = new DateTime().plusDays(1).toString(patternsql);
        return td.equals(dtr);
    }

    /**
     *
     * @param bo
     * @return
     */
    public static boolean isAvaliable(Booking bo) {
        Db_Master db = new Db_Master();
        ArrayList<String[]> elenco = db.valuePrenot();
        db.closeDB();
        for (int i = 0; i < elenco.size(); i++) {
            if (elenco.get(i)[0].equals(bo.getFiliale()) && elenco.get(i)[1].equals(bo.getCurrency())
                    && elenco.get(i)[2].equals(bo.getDt_ritiro())) {
                return elenco.get(i)[4].equals("OK");
            }
        }
        return false;
    }

    /**
     *
     * @param dtr
     * @return
     */
    public static boolean isPast(String dtr) {
        try {
            DateTimeFormatter formatter = forPattern(patternsql);
            DateTime dt = formatter.parseDateTime(dtr);
            return dt.isBefore(new DateTime());
        } catch (Exception localException) {
        }
        return false;
    }

    /**
     *
     * @param statusini
     * @param type
     * @param query
     * @return
     */
    public static ArrayList<String[]> sito_stati(String statusini, String type, boolean query) {
        ArrayList<String[]> out = new ArrayList<>();

        String[] a1 = { "0", "", "<span class='font-blue-hoki'>CONFIRMED <i class='fa fa-spinner'></i></span>" };
        String[] a4 = { "5", "", "<span class='font-red-haze'>REJECTED <i class='fa fa-trash-o'></i></span>" };
        String[] a3 = { "6", "", "<span class='font-red'>CANCELLED <i class='fa fa-window-close'></i></span>" };
        String[] a0 = { "8", "", "<span class='font-grey-salsa'>EXPIRED <i class='fa fa-ban'></i></span>" };
        String[] a5 = { "3", "", "<span class='font-grey-salsa'>NOSHOW <i class='fa fa-ban'></i></span>" };
        String[] a2 = { "7", "", "<span class='font-green-jungle'>PROCESSED <i class='fa fa-check'></i></span>" };

        out.add(a1);
        out.add(a4);
        out.add(a3);
        out.add(a0);
        out.add(a5);
        out.add(a2);

        // Db_Master db = new Db_Master();
        // ArrayList<String[]> li = db.sito_stati();
        // db.closeDB();
        //
        // if (query) {
        // out = li;
        // } else {
        // for (int x = 0; x < li.size(); x++) {
        // if (x == 0 || x == 1 || x == 3) {
        // out.add(li.get(x));
        // }
        // }
        // }
        return out;
    }

    /**
     *
     * @param cl
     * @return
     */
    public static BlacklistM verifyBlacklistMac(Client cl) {
        Db_Master db = new Db_Master();
        ArrayList<BlacklistM> bliniziale = db.list_BlMacc(true);
        db.closeDB();
        for (int i = 0; i < bliniziale.size(); i++) {
            BlacklistM b_daconfr = bliniziale.get(i);
            if (b_daconfr.getNome().trim().equalsIgnoreCase(cl.getNome())
                    && b_daconfr.getCognome().trim().equalsIgnoreCase(cl.getCognome())
                    && b_daconfr.getSesso().trim().equalsIgnoreCase(cl.getSesso())) {
                boolean ver = true;
                if (!b_daconfr.getCodfisc().trim().equals("") && !b_daconfr.getCodfisc().trim().equals("-")) {
                    if (!b_daconfr.getCodfisc().trim().equalsIgnoreCase(cl.getCodfisc())) {
                        ver = false;
                    }
                }
                if (!b_daconfr.getNazione().trim().equals("") && !b_daconfr.getNazione().trim().equals("-")) {
                    if (!b_daconfr.getNazione().trim().equalsIgnoreCase(cl.getNazione())) {
                        ver = false;
                    }
                }
                if (!b_daconfr.getCitta().trim().equals("") && !b_daconfr.getCitta().trim().equals("-")) {
                    if (!b_daconfr.getCitta().trim().equalsIgnoreCase(cl.getCitta())) {
                        ver = false;
                    }
                }
                if (!b_daconfr.getIndirizzo().trim().equals("") && !b_daconfr.getIndirizzo().trim().equals("-")) {
                    if (!b_daconfr.getIndirizzo().trim().equalsIgnoreCase(cl.getIndirizzo())) {
                        ver = false;
                    }
                }
                if (!b_daconfr.getCap().trim().equals("") && !b_daconfr.getCap().trim().equals("-")) {
                    if (!b_daconfr.getCap().trim().equalsIgnoreCase(cl.getCap())) {
                        ver = false;
                    }
                }
                if (!b_daconfr.getProvincia().trim().equals("") && !b_daconfr.getProvincia().trim().equals("-")) {
                    if (!b_daconfr.getProvincia().trim().equalsIgnoreCase(cl.getProvincia())) {
                        ver = false;
                    }
                }
                if (!b_daconfr.getCitta_nascita().trim().equals("")
                        && !b_daconfr.getCitta_nascita().trim().equals("-")) {
                    if (!b_daconfr.getCitta_nascita().trim().equalsIgnoreCase(cl.getCitta_nascita())) {
                        ver = false;
                    }
                }
                if (!b_daconfr.getProvincia_nascita().trim().equals("")
                        && !b_daconfr.getProvincia_nascita().trim().equals("-")) {
                    if (!b_daconfr.getProvincia_nascita().trim().equalsIgnoreCase(cl.getProvincia_nascita())) {
                        ver = false;
                    }
                }
                if (!b_daconfr.getNazione_nascita().trim().equals("")
                        && !b_daconfr.getNazione_nascita().trim().equals("-")) {
                    if (!b_daconfr.getNazione_nascita().trim().equalsIgnoreCase(cl.getNazione_nascita())) {
                        ver = false;
                    }
                }
                if (!b_daconfr.getDt_nascita().trim().equals("") && !b_daconfr.getDt_nascita().trim().equals("-")) {
                    if (!b_daconfr.getDt_nascita().trim().equalsIgnoreCase(cl.getDt_nascita())) {
                        ver = false;
                    }
                }
                if (!b_daconfr.getTipo_documento().trim().equals("")
                        && !b_daconfr.getTipo_documento().trim().equals("-")) {
                    if (!b_daconfr.getTipo_documento().trim().equalsIgnoreCase(cl.getTipo_documento())) {
                        ver = false;
                    }
                }
                if (!b_daconfr.getNumero_documento().trim().equals("")
                        && !b_daconfr.getNumero_documento().trim().equals("-")) {
                    if (!b_daconfr.getNumero_documento().trim().equalsIgnoreCase(cl.getNumero_documento())) {
                        ver = false;
                    }
                }
                if (!b_daconfr.getDt_rilascio_documento().trim().equals("")
                        && !b_daconfr.getDt_rilascio_documento().trim().equals("-")) {
                    if (!b_daconfr.getDt_rilascio_documento().trim().equalsIgnoreCase(cl.getDt_rilascio_documento())) {
                        ver = false;
                    }
                }
                if (!b_daconfr.getDt_scadenza_documento().trim().equals("")
                        && !b_daconfr.getDt_scadenza_documento().trim().equals("-")) {
                    if (!b_daconfr.getDt_scadenza_documento().trim().equalsIgnoreCase(cl.getDt_scadenza_documento())) {
                        ver = false;
                    }
                }
                if (!b_daconfr.getRilasciato_da_documento().trim().equals("")
                        && !b_daconfr.getRilasciato_da_documento().trim().equals("-")) {
                    if (!b_daconfr.getRilasciato_da_documento().trim()
                            .equalsIgnoreCase(cl.getRilasciato_da_documento())) {
                        ver = false;
                    }
                }
                if (!b_daconfr.getLuogo_rilascio_documento().trim().equals("")
                        && !b_daconfr.getLuogo_rilascio_documento().trim().equals("-")) {
                    if (!b_daconfr.getLuogo_rilascio_documento().trim()
                            .equalsIgnoreCase(cl.getLuogo_rilascio_documento())) {
                        ver = false;
                    }
                }
                if (ver) {
                    return b_daconfr;
                }
            }
        }
        return null;
    }

    /**
     *
     * @param cod_tr
     * @return
     */
    public static Ch_transaction_refund get_refund_trans(String cod_tr) {
        Db_Master db = new Db_Master();
        Ch_transaction_refund re = db.get_refund_trans(cod_tr);
        db.closeDB();
        return re;
    }

    /**
     *
     * @return
     */
    public static ArrayList<Codici_sblocco_file> list_codici_sblocco_file() {
        Db_Master db = new Db_Master();
        ArrayList<Codici_sblocco_file> re = db.list_codici_sblocco_file();
        db.closeDB();
        return re;
    }

    /**
     *
     * @param ctd
     * @return
     */
    public static boolean insert_transaction_doc(Ch_transaction_doc ctd) {
        Db_Master db = new Db_Master();
        boolean es = db.insert_transaction_doc(ctd);
        db.closeDB();
        return es;
    }

    /**
     *
     * @param ctd
     * @param filiale
     * @return
     */
    public static boolean insert_transaction_doc_FILIALE(Ch_transaction_doc ctd, String filiale) {
        Db_Master db = new Db_Master();
        boolean es = db.insert_transaction_doc_FILIALE(ctd, filiale);
        db.closeDB();
        return es;
    }

    /**
     *
     * @param codtr
     * @return
     */
    public static ArrayList<Ch_transaction_doc> get_list_tr_doc(String codtr) {
        Db_Master db = new Db_Master();
        ArrayList<Ch_transaction_doc> re = db.get_list_tr_doc(codtr);
        db.closeDB();
        return re;
    }

    /**
     *
     * @param codtr
     * @return
     */
    public static ArrayList<Ch_transaction_doc> get_list_tr_doc_view(String codtr) {
        Db_Master db = new Db_Master();
        ArrayList<Ch_transaction_doc> re = db.get_list_tr_doc_view(codtr);
        db.closeDB();
        return re;
    }

    /**
     *
     * @param doc
     * @param tipodoc
     * @return
     */
    public static Ch_transaction_doc formatfromlist_doc(ArrayList<Ch_transaction_doc> doc, String tipodoc) {
        for (int i = 0; i < doc.size(); i++) {
            if (tipodoc.equals(doc.get(i).getTipodoc())) {
                return doc.get(i);
            }
        }
        return null;
    }

    /**
     *
     * @param codtr
     * @return
     */
    public static String get_codclient(String codtr) {
        Db_Master db = new Db_Master();
        Ch_transaction tr = db.query_transaction_ch(codtr);
        if (tr == null) {
            tr = db.query_transaction_ch_temp(codtr);
        }
        String codclient = tr.getCl_cod();
        db.closeDB();
        return codclient;
    }

    /**
     *
     * @param re
     * @param type
     * @return
     */
    public static rc.so.entity.Document get_ypedoc_tra(ArrayList<rc.so.entity.Document> re, String type) {
        for (int i = 0; i < re.size(); i++) {
            if (re.get(i).getCodice().equals(type)) {
                return re.get(i);
            }
        }
        return null;
    }

    /**
     *
     * @return
     */
    public static ArrayList<rc.so.entity.Document> list_all_ypedoc_tra() {
        Db_Master db = new Db_Master();
        ArrayList<rc.so.entity.Document> re = db.list_typedoc_tra(null);
        db.closeDB();
        return re;
    }

    /**
     *
     * @param cod
     */
    public static void verifica_fa_nc(String cod) {

        Db_Master db = new Db_Master();
        String pathtemp = db.getPath("temp");
        Ch_transaction it = db.query_transaction_ch(cod);
        if (it == null) {
            it = db.query_transaction_ch_temp(cod);
        }
        ArrayList<Ch_transaction_doc> doc = db.get_list_tr_doc(it.getCod());
        ArrayList<Ch_transaction_value> li = db.query_transaction_value(it.getCod());
        Client cl = db.query_Client_transaction(cod, it.getCl_cod());
        Office ma = db.get_national_office();
        Branch br = db.get_branch(it.getFiliale());
        ArrayList<Figures> fig = db.list_all_figures();
        ArrayList<Currency> cur = db.list_figures_query_edit(null);
        CustomerKind ck = db.get_customerKind(it.getTipocliente());
        VATcode va = db.get_vat_cod(ck.getVatcode());
        String datadocumento = db.getNow();
        db.closeDB();

        boolean verificafattura = !it.getFa_number().trim().equals("-") && !it.getFa_number().trim().equals("");
        boolean verificanc = !it.getCn_number().trim().equals("-") && !it.getCn_number().trim().equals("");

        boolean contienefattura = false;
        boolean contienenc = false;

        for (int i = 0; i < doc.size(); i++) {
            if (doc.get(i).getTipodoc().equals("_macfaexsee") || doc.get(i).getTipodoc().equals("_macfaexseebo")) {
                contienefattura = true;
            }
            if (doc.get(i).getTipodoc().equals("_maccredinot")) {
                contienenc = true;
            }
        }

        if (verificafattura) {
            if (!contienefattura) {
                if ((fd(it.getCommission()) + parseDoubleR(it.getRound())) >= fd(ck.getIp_soglia_bollo())) {
                    // bollo
                    String filename = new DateTime().toString("yyMMddHHmmssSSS") + cod + "_macfaexseebo.pdf";
                    String base64 = new Receipt().print_bill_extrasee_bollo(pathtemp, ma, it, cl, li, br, fig, cur, va,
                            ck, true);
                    Ch_transaction_doc chd = new Ch_transaction_doc(generaId(50), cod, "_macfaexseebo", base64,
                            filename, datadocumento, it.getCl_cod(), "Y");
                    boolean es = insert_transaction_doc(chd);
                    if (es) {
                        insertTR("W", "service", "INSERITA FATTURA CON IMPOSTA DI BOLLO");
                    }
                } else {
                    // senza bollo
                    String filename = new DateTime().toString("yyMMddHHmmssSSS") + cod + "_macfaexsee.pdf";
                    String base64 = new Receipt().print_bill_extrasee_bollo(pathtemp, ma, it, cl, li, br, fig, cur, va,
                            ck, true);
                    Ch_transaction_doc chd = new Ch_transaction_doc(generaId(50), cod, "_macfaexsee", base64, filename,
                            datadocumento, it.getCl_cod(), "Y");
                    boolean es = insert_transaction_doc(chd);
                    if (es) {
                        insertTR("W", "service", "INSERITA FATTURA");
                    }
                }
            }
        }
        if (verificanc) {
            if (!contienenc) {
                String filename = new DateTime().toString("yyMMddHHmmssSSS") + cod + "_maccredinot.pdf";
                String base64 = new Receipt().print_credit_note(pathtemp, ma, it, cl, li, br, fig, cur, va, ck,
                        it.getFiliale() + it.getId(), it.getPay(), true, it.getPay(), it.getPay(), true, true);
                Ch_transaction_doc chd = new Ch_transaction_doc(generaId(50), cod, "_maccredinot", base64, filename,
                        datadocumento, it.getCl_cod(), "Y");
                boolean es = insert_transaction_doc(chd);
                if (es) {
                    insertTR("W", "service", "INSERITA NOTACREDITO");
                }
            }
        }
    }

    /**
     *
     * @param it
     * @param list_value
     * @param cl
     * @param kycpres
     * @return
     */
    public static ArrayList<rc.so.entity.Document> list_typedoc_tra(Ch_transaction it,
            ArrayList<Ch_transaction_value> list_value, Client cl, String kycpres) {
        ArrayList<rc.so.entity.Document> out = new ArrayList<>();
        Db_Master db = new Db_Master();
        ArrayList<rc.so.entity.Document> re = db.list_typedoc_tra("1");
        CustomerKind ck = db.get_customerKind(it.getTipocliente());
        Office of = db.get_national_office();
        db.closeDB();

        for (int i = 0; i < re.size(); i++) {
            rc.so.entity.Document o1 = re.get(i);
            String cod = o1.getCodice();
            switch (cod) {
                case "_macrecsee":
                    if (ck.getTipofat().equals("0")) { // invoice
                        if ((fd(it.getCommission()) + parseDoubleR(it.getRound())) == 0) {
                            o1.setObbl(true);
                            out.add(o1);
                        }
                    } else if (ck.getTipofat().equals("1")) { // no invoice
                        o1.setObbl(true);
                        out.add(o1);
                    }
                    break;
                case "_macfaexsee":
                    if (ck.getTipofat().equals("0")) { // invoice
                        if ((fd(it.getCommission()) + parseDoubleR(it.getRound())) > 0) {
                            if ((fd(it.getCommission()) + parseDoubleR(it.getRound())) < fd(ck.getIp_soglia_bollo())) {
                                o1.setObbl(true);
                                out.add(o1);
                            }
                        }
                    }
                    break;
                case "_macfaexseebo":
                    if (ck.getTipofat().equals("0")) { // invoice
                        if ((fd(it.getCommission()) + parseDoubleR(it.getRound())) >= fd(ck.getIp_soglia_bollo())) {
                            o1.setObbl(true);
                            out.add(o1);
                        }
                    }
                    break;
                case "_macprofcl":
                    if (cl != null) {
                        // if (Constant.co_auto) {
                        Kyc_parameter kyc_pram = new Kyc_parameter();
                        Kyc_parameter result = list_transaction_kyc(cl.getCode(), it);

                        boolean condizione_1 = result.getOp_giorn() >= parseIntR(kyc_pram.getKyc_fa1());
                        boolean condizione_2 = result.getOp_sett() >= parseIntR(kyc_pram.getKyc_fa2());
                        boolean condizione_3 = result.getOp_mensili() >= parseIntR(kyc_pram.getKyc_fa3());
                        boolean condizione_4 = result.getVol_mensile() >= fd(kyc_pram.getKyc_va1());
                        boolean condizione_5 = result.getVol_trimest() >= fd(kyc_pram.getKyc_va2());
                        boolean condizione_6 = result.getVol_weekly() >= fd(kyc_pram.getKyc_vro())
                                && kyc_pram.getKyc_listro().contains(cl.getNazione());
                        boolean condizione_7 = transaction_NotesBIG(it);

                        boolean condizione_old_1 = cl.getPep().equals("YES") || cl.getPep().equals("1");
                        boolean condizione_old_2 = kycpres.equals("1");
                        boolean condizione_old_3 = checkSogliaValueofTransaction_client(cl, of, it);

                        if (condizione_1
                                || condizione_2
                                || condizione_3
                                || condizione_4
                                || condizione_5
                                || condizione_6
                                || condizione_7
                                || condizione_old_1
                                || condizione_old_2
                                || condizione_old_3) {

                            o1.setObbl(true);
                            out.add(o1);

                            String d1 = condizione_1
                                    + ";" + condizione_2
                                    + ";" + condizione_3
                                    + ";" + condizione_4
                                    + ";" + condizione_5
                                    + ";" + condizione_6
                                    + ";" + condizione_7
                                    + ";" + condizione_old_1
                                    + ";" + condizione_old_2
                                    + ";" + condizione_old_3;

                            Db_Master db00 = new Db_Master();
                            db00.insert_valuekyc_Module(it.getCod(), it.getUser(), d1);
                            db00.closeDB();
                        }

                        // } else {
                        // if (cl.getPep().equals("YES") || cl.getPep().equals("1") ||
                        // kycpres.equals("1")) {
                        // o1.setObbl(true);
                        // out.add(o1);
                        // } else if (checkSogliaValueofTransaction_client(cl, of, it)) {
                        // o1.setObbl(true);
                        // out.add(o1);
                        // }
                        // }
                        // o1.setObbl(true);
                        // out.add(o1);
                    }
                    break;
                case "_docrico":
                    if (is_CZ) {
                        boolean soglia = false;
                        boolean euro = false;
                        for (int c = 0; c < list_value.size(); c++) {
                            if (list_value.get(c).getValuta().toUpperCase().equals("EUR")) {
                                euro = true;
                                if (fd(list_value.get(c).getQuantita()) >= fd(ck.getIp_soglia_antiriciclaggio())) {
                                    soglia = true;
                                    break;
                                }
                            }
                        }

                        if (euro) {
                            if (soglia) {
                                o1.setObbl(true);
                                out.add(o1);
                                continue;
                            }
                        } else {

                            soglia = false;

                            double sogl = get_soglia_CZ(fd(ck.getIp_soglia_antiriciclaggio()));

                            if (it.getTipotr().equals("B")) {
                                if (fd(it.getTotal()) >= (sogl)) {
                                    soglia = true;
                                }
                            } else if (it.getTipotr().equals("S")) {
                                if (fd(it.getPay()) >= (sogl)) {
                                    soglia = true;
                                }
                            }

                            if (soglia) {
                                o1.setObbl(true);
                                out.add(o1);
                                continue;
                            }
                        }

                        boolean clientpep = false;
                        if (cl != null) {
                            if (cl.getPep() != null) {
                                if (cl.getPep().equals("1")) {
                                    clientpep = true;
                                }
                            }
                        }

                        if (ck.getFg_uploadobbl().equals("1")) {
                            o1.setObbl(true);
                            out.add(o1);
                        } else if (clientpep) {
                            out.add(o1);
                        } else if (kycpres.equals("1")) {
                            out.add(o1);
                        }
                    } else {
                        boolean soglia = false;
                        double sogl = fd(ck.getIp_soglia_antiriciclaggio());
                        sogl = get_soglia_CZ(sogl);
                        if (ck.getFg_uploadobbl().equals("1")) {
                            o1.setObbl(true);
                            soglia = true;
                        } else if (it.getTipotr().equals("B")) {
                            if ((fd(it.getTotal())) >= (sogl)) {
                                o1.setObbl(true);
                                soglia = true;
                            }
                        } else if (it.getTipotr().equals("S")) {
                            if ((fd(it.getPay())) >= (sogl)) {
                                o1.setObbl(true);
                                soglia = true;
                            }
                        }
                        if (is_IT) {
                            out.add(o1);
                        } else if (is_CZ) {
                        } else if (is_UK) {
                            if (soglia) {
                                out.add(o1);
                            } else {
                                if (cl != null && !cl.getSesso().trim().equals("")) {
                                    o1.setObbl(true);
                                    out.add(o1);
                                }
                            }
                        }
                    }
                    break;
                case "_heavy":
                    boolean soglia = false;
                    if (it.getTipotr().equals("B")) {
                        if ((fd(it.getTotal())) >= (fd(ck.getIp_soglia_antiriciclaggio()))) {
                            o1.setObbl(true);
                            soglia = true;
                        }
                    } else if (it.getTipotr().equals("S")) {
                        if ((fd(it.getPay())) >= (fd(ck.getIp_soglia_antiriciclaggio()))) {
                            o1.setObbl(true);
                            soglia = true;
                        }
                    }
                    if (is_UK) {
                        if (soglia) {
                            out.add(o1);
                        } else {
                            if (cl != null && !cl.getSesso().trim().equals("")) {
                                o1.setObbl(true);
                                out.add(o1);
                            }
                        }
                    }
                    break;
                case "_macautoce":
                    // MODIFICA 30/09
                    if (ck.getTipofat().equals("0")) { // invoice
                        if ((fd(it.getCommission()) + parseDoubleR(it.getRound())) > 0) {
                            o1.setObbl(true);
                            out.add(o1);
                        }
                    }
                    if (!o1.isObbl()) {
                        // else if (ck.getTipofat().equals("1")) { //no invoice
                        if (ck.getStampa_autocertificazione().equals("1")) {

                            // double controv = fd(it.getTotal());
                            // if (it.getTipotr().equals("S")) {
                            // controv = fd(it.getPay());
                            // }
                            // if (controv >= fd(ck.getIp_soglia_extraCEE_certification())) {
                            // o1.setObbl(true);
                            // out.add(o1);
                            // }
                            double sogliam1 = fd(ck.getIp_soglia_extraCEE_certification());

                            String importo = "0.00";
                            if (it.getTipotr().equals("B")) {
                                importo = it.getTotal();
                            } else if (it.getTipotr().equals("S")) {
                                importo = it.getPay();
                            }
                            Db_Master db4A = new Db_Master();
                            double weekA = db4A.weekly_transaction(it.getCl_cod());
                            db4A.closeDB();
                            Db_Master db4 = new Db_Master(true);
                            if (db4.getC() == null) {
                                db4 = new Db_Master();
                            }
                            double week = db4.weekly_transaction_nofiliale(it.getCl_cod(), it.getFiliale());
                            db4.closeDB();

                            double now = fd(importo) + week + weekA;
                            if (now > sogliam1) {
                                o1.setObbl(true);
                                out.add(o1);
                            }
                        }
                    }
                    // CZ
                    break;
                case "_prebuy":
                    if (it.getTipotr().equals("B")) {
                        o1.setObbl(true);
                        out.add(o1);
                    }
                    break;
                case "_czbuy":
                    if (it.getTipotr().equals("B")) {
                        o1.setObbl(true);
                        out.add(o1);
                    }
                    break;
                case "_presell":
                    if (it.getTipotr().equals("S")) {
                        o1.setObbl(true);
                        out.add(o1);
                    }
                    break;
                case "_czsell":
                    if (it.getTipotr().equals("S")) {
                        o1.setObbl(true);
                        out.add(o1);
                    }
                    // UK
                    break;
                case "_receipt":
                    o1.setObbl(false);
                    out.add(o1);
                    break;
                default:
                    break;
            }

        }
        return out;
    }

    /**
     *
     * @param tra
     * @return
     */
    public static boolean transaction_NotesBIG(Ch_transaction tra) {
        Db_Master db = new Db_Master();
        boolean isNotesBig = db.isNotesBig(tra.getCod());
        db.closeDB();
        return isNotesBig;

    }

    /**
     *
     * @param clcode
     * @param tra
     * @return
     */
    public static Kyc_parameter list_transaction_kyc(String clcode, Ch_transaction tra) {
        Db_Master db = new Db_Master(true);
        if (db.getC() == null) {
            db = new Db_Master();
        }
        Kyc_parameter re = db.list_transaction_kyc(clcode, tra);
        db.closeDB();
        return re;
    }

    /**
     *
     * @param cl
     * @param of
     * @param it
     * @return
     */
    public static boolean checkSogliaValueofTransaction_client(Client cl, Office of, Ch_transaction it) {
        Db_Master db = new Db_Master(true);
        if (db.getC() == null) {
            db = new Db_Master();
        }
        boolean re = db.checkSogliaValueofTransaction_client(cl, of, it);
        db.closeDB();
        return re;
    }

    /**
     *
     * @param cod
     * @return
     */
    public static String[] get_typedoc_tra(String cod) {
        Db_Master db = new Db_Master();
        String[] re = db.get_typedoc_tra(cod);
        db.closeDB();
        return re;
    }

    /**
     *
     * @param cod
     * @return
     */
    public static String[] get_site_agevolazioni_varie(String cod) {
        Db_Master db = new Db_Master();
        String[] re = db.get_site_agevolazioni_varie(cod);
        db.closeDB();
        return re;
    }

    /**
     *
     * @param cod
     * @return
     */
    public static String[] get_site_servizi_agg(String cod) {
        Db_Master db = new Db_Master();
        String[] re = db.get_site_servizi_agg(cod);
        db.closeDB();
        return re;
    }

    /**
     *
     * @param filiale
     * @param valuta
     * @return
     */
    public static ArrayList<Rate_history> list_ratehistory(String filiale, String valuta) {
        Db_Master db = new Db_Master();
        ArrayList<Rate_history> re = db.list_ratehistory(filiale, valuta, null, null);
        db.closeDB();
        return re;
    }

    /**
     *
     * @param st
     * @return
     */
    public static ArrayList<VATcode> li_vat(String st) {
        Db_Master db = new Db_Master();
        ArrayList<VATcode> re = db.li_vat(st);
        db.closeDB();
        return re;
    }

    /**
     *
     * @param cod
     * @return
     */
    public static VATcode get_vat(String cod) {
        Db_Master db = new Db_Master();
        VATcode re = db.get_vat(cod);
        db.closeDB();
        return re;
    }

    /**
     *
     * @param cod
     * @param valist
     * @return
     */
    public static VATcode get_vat(String cod, ArrayList<VATcode> valist) {

        for (int i = 0; i < valist.size(); i++) {
            if (valist.get(i).getCodice().equals(cod)) {
                return valist.get(i);
            }
        }

        return null;
    }

    /**
     *
     * @return
     */
    public static boolean isCentral() {
        String[] val = getFil();
        if (val != null) {
            return getFil()[0].equals("000");
        }
        return false;
    }

    /**
     *
     * @param licateg
     * @param licaus
     * @param array_nc_kind
     * @return
     */
    public static ArrayList<String[]> query_nc(ArrayList<NC_category> licateg, ArrayList<NC_causal> licaus,
            ArrayList<String[]> array_nc_kind) {
        ArrayList<String[]> li = new ArrayList<>();
        for (int i = 0; i < licateg.size(); i++) {
            NC_category ncc = licateg.get(i);

            String codkind = formatAL(ncc.getFg_tipo_transazione_nc(), array_nc_kind, 1);

            String[] o1 = { ncc.getGruppo_nc(), "", ncc.getDe_gruppo_nc(), codkind,
                    formatMysqltoDisplay(ncc.getIp_prezzo_nc()), ncc.formatStatus(ncc.getAnnullato()),
                    "<a href='nc_edit_cat.jsp?view=1&nc_code=" + ncc.getGruppo_nc()
                            + "' class='btn btn-sm blue btn-outline btn-circle popovers fancyBoxRaf' "
                            + "' class='btn btn-sm blue-dark btn-outline btn-circle fancyBoxRaf'><i class='fa fa-eye'></i> View</a>" };
            li.add(o1);

            for (int x = 0; x < licaus.size(); x++) {
                NC_causal nca = licaus.get(x);
                if (nca.getGruppo_nc().equals(ncc.getGruppo_nc())) {
                    String o2[] = { nca.getGruppo_nc(), nca.getCausale_nc(), nca.getDe_causale_nc(), codkind,
                            formatMysqltoDisplay(nca.getIp_prezzo_nc()), nca.formatStatus(nca.getAnnullato()),
                            "<a href='nc_edit_cas.jsp?view=1&nc_code=" + nca.getCausale_nc()
                                    + "' class='btn btn-sm blue btn-outline btn-circle fancyBoxRaf'><i class='fa fa-eye'></i> View</a>" };
                    li.add(o2);

                }
            }
        }
        return li;
    }

    /**
     *
     * @param licateg
     * @param licaus
     * @param array_nc_kind
     * @return
     */
    public static ArrayList<String[]> query_nc_onlycat(ArrayList<NC_category> licateg, ArrayList<NC_causal> licaus,
            ArrayList<String[]> array_nc_kind) {
        ArrayList<String[]> li = new ArrayList<>();
        for (int i = 0; i < licateg.size(); i++) {
            NC_category ncc = licateg.get(i);

            String codkind = formatAL(ncc.getFg_tipo_transazione_nc(), array_nc_kind, 1);

            String[] o1 = { ncc.getGruppo_nc(), "", ncc.getDe_gruppo_nc(), codkind,
                    formatMysqltoDisplay(ncc.getIp_prezzo_nc()), ncc.formatStatus(ncc.getAnnullato()),
                    ncc.getFiliale() };
            li.add(o1);

            for (int x = 0; x < licaus.size(); x++) {
                NC_causal nca = licaus.get(x);
                if (nca.getGruppo_nc().equals(ncc.getGruppo_nc())) {
                    String o2[] = { nca.getGruppo_nc(), nca.getCausale_nc(), nca.getDe_causale_nc(), codkind,
                            formatMysqltoDisplay(nca.getIp_prezzo_nc()), nca.formatStatus(nca.getAnnullato()),
                            nca.getFiliale() };
                    if (ncc.getFiliale().equals(nca.getFiliale())) {
                        li.add(o2);
                    }

                }
            }
        }
        return li;
    }

    /**
     *
     * @param datad1
     * @param datad2
     * @param filiale
     * @return
     */
    public static ArrayList<Openclose> list_openclose_report(String datad1, String datad2, String filiale) {
        String d1 = formatStringtoStringDate_null(datad1, patternnormdate_filter, patternsql);
        String d2 = formatStringtoStringDate_null(datad2, patternnormdate_filter, patternsql);
        Db_Master db = new Db_Master();
        ArrayList<Openclose> li = db.list_openclose_report(filiale, d1, d2);
        db.closeDB();
        return li;
    }

    /**
     *
     * @param datad1
     * @param datad2
     * @param dest
     * @param branch
     * @param bank
     * @param type
     * @param filiale
     * @param ba_pos
     * @param chnc
     * @return
     */
    public static ArrayList<ET_change> list_ET_change_report(String datad1, String datad2, String dest, boolean branch,
            boolean bank, String type, String filiale, String ba_pos, String chnc) {
        String d1 = formatStringtoStringDate_null(datad1, patternnormdate_filter, patternsql);
        String d2 = formatStringtoStringDate_null(datad2, patternnormdate_filter, patternsql);
        Db_Master db = new Db_Master();
        ArrayList<ET_change> li = db.list_ET_change_report(d1, d2, filiale, dest, branch, bank, type, ba_pos, chnc);
        db.closeDB();
        return li;
    }

    /**
     *
     * @param datad1
     * @param datad2
     * @param typeft
     * @param typedest
     * @param branch
     * @return
     */
    public static ArrayList<ET_change> list_ET_change_report_central(String datad1, String datad2, String typeft,
            String typedest, String branch) {
        String d1 = formatStringtoStringDate_null(datad1, patternnormdate_filter, patternsql);
        String d2 = formatStringtoStringDate_null(datad2, patternnormdate_filter, patternsql);
        Db_Master db = new Db_Master();
        ArrayList<String> br1 = parseString(branch, ",");
        if (br1.isEmpty()) {
            br1 = db.list_branchcode_completeAFTER311217();
        }
        ArrayList<ET_change> li = db.list_ET_change_report(d1, d2, br1, typeft, typedest);
        db.closeDB();
        return li;
    }

    /**
     *
     * @param list
     * @param filiale
     * @param cod
     * @param valuta
     * @param kind
     * @return
     */
    public static String[] format_list_oc_change(ArrayList<String[]> list, String filiale, String cod, String valuta,
            String kind) {
        for (int i = 0; i < list.size(); i++) {
            String[] value = list.get(i);
            if (value[7].equals(filiale) && value[0].equals(cod) && value[2].equals(valuta) && value[1].equals(kind)) {
                return value;
            }
        }
        return null;
    }

    /**
     *
     * @param list
     * @param filiale
     * @param cod
     * @param grupponc
     * @return
     */
    public static String[] format_list_oc_nochange(ArrayList<String[]> list, String filiale, String cod,
            String grupponc) {
        for (int i = 0; i < list.size(); i++) {
            String[] value = list.get(i);

            if (value[4].equals(filiale) && value[0].equals(cod) && value[1].equals(grupponc)) {
                return value;
            }
        }
        return null;
    }

    /**
     *
     * @param list
     * @param filiale
     * @param cod
     * @param valuta
     * @param kind
     * @param pos
     * @return
     */
    public static String[] format_list_oc_pos(ArrayList<String[]> list, String filiale, String cod, String valuta,
            String kind, String pos) {
        for (int i = 0; i < list.size(); i++) {
            String[] value = list.get(i);
            if (value[8].equals(filiale) && value[0].equals(cod) && value[1].equals(valuta) && value[2].equals(kind)
                    && value[3].equals(pos)) {
                return value;
            }
        }
        return null;
    }

    /**
     *
     * @param k
     * @param list_nc_descr
     * @return
     */
    public static String[] getinoutNC(String k, ArrayList<String[]> list_nc_descr) {
        String in = "";
        String out = "";
        for (int j = 0; j < list_nc_descr.size(); j++) {
            if (k.equals(list_nc_descr.get(j)[2])) {
                if (list_nc_descr.get(j)[3].equals("1") || list_nc_descr.get(j)[3].equals("3")) {
                    in = list_nc_descr.get(j)[0];
                }
                if (list_nc_descr.get(j)[3].equals("2") || list_nc_descr.get(j)[3].equals("4")) {
                    out = list_nc_descr.get(j)[0];
                }
            }
        }
        String[] output = { k, in, out };
        return output;

    }

    /**
     *
     * @param type
     * @return
     */
    public static String formatTypeTransaction_stockprice(String type) {
        switch (type) {
            case "CH":
            case "NC":
                return "Transaction";
            case "ET":
                return "Ext. Transfert";
            case "IT":
                return "Int. Transfert";
            case "OC":
                return "Open/Close Error";
            default:
                break;
        }
        return type;
    }

    /**
     *
     * @return
     */
    public static ArrayList<String[]> list_bankAccount() {
        Db_Master db = new Db_Master();
        ArrayList<String[]> out = db.list_bankAccount();
        db.closeDB();
        return out;
    }

    /**
     *
     * @return
     */
    public static ArrayList<Branchbudget> list_Branchbudget() {
        Db_Master db = new Db_Master();
        ArrayList<Branchbudget> out = db.list_Branchbudget();
        db.closeDB();
        return out;
    }

    /**
     *
     * @param cod
     * @return
     */
    public static Branchbudget list_Branchbudget(String cod) {
        Db_Master db = new Db_Master();
        Branchbudget out = db.list_Branchbudget(cod);
        db.closeDB();
        return out;
    }

    /**
     *
     * @return
     */
    public static ArrayList<String[]> list_bankAccountPOS() {
        Db_Master db = new Db_Master();
        ArrayList<String[]> out = db.list_bank_pos_enabled();
        db.closeDB();
        return out;
    }

    /**
     *
     * @return
     */
    public static ArrayList<String[]> list_fasce_cashier_perf() {
        Db_Master db = new Db_Master();
        ArrayList<String[]> out = db.list_fasce_cashier_perf(null, null);
        db.closeDB();
        return out;
    }

    /**
     *
     * @return
     */
    public static ArrayList<String[]> indice_rischio() {
        Db_Master db = new Db_Master();
        ArrayList<String[]> out = db.indice_rischio();
        db.closeDB();
        return out;
    }

    /**
     *
     * @return
     */
    public static ArrayList<String[]> indice_rischio_new() {
        Db_Master db = new Db_Master();
        ArrayList<String[]> out = db.indice_rischio_new();
        db.closeDB();
        return out;
    }

    /**
     *
     * @param cod_tr
     * @return
     */
    public static ArrayList<Ch_transaction_value> query_transaction_value(String cod_tr) {
        Db_Master db = new Db_Master();
        ArrayList<Ch_transaction_value> out = db.query_transaction_value(cod_tr);
        db.closeDB();
        return out;
    }

    /**
     *
     * @param cod_tr
     * @param type
     * @return
     */
    public static ArrayList<Ch_transaction_value> query_transaction_value(String cod_tr, String type) {
        Db_Master db = new Db_Master();
        if (type != null) {
            if (type.equals("central")) {
                db = new Db_Master(true);
                if (db.getC() == null) {
                    db = new Db_Master();
                }
            }
        }
        ArrayList<Ch_transaction_value> out = db.query_transaction_value(cod_tr);
        db.closeDB();
        return out;
    }

    /**
     *
     * @param user
     * @return
     */
    public static String getLastLink(String user) {
        Db_Master dbm = new Db_Master();
        String li = dbm.getLastLink(user);
        dbm.closeDB();
        return li;
    }

    /**
     *
     * @param user
     * @param codtr
     */
    public static void deleteLinktransaction(String user, String codtr) {
        Db_Master dbm = new Db_Master();
        dbm.deleteLinktransaction(user, codtr);
        dbm.closeDB();
    }

    /**
     *
     * @param user
     */
    public static void deleteLinktransaction(String user) {
        Db_Master dbm = new Db_Master();
        dbm.deleteLinktransaction(user);
        dbm.closeDB();
    }

    /**
     *
     * @param user
     * @param codtr
     * @param priorita
     * @param request
     */
    public static void insertLink(String user, String codtr, int priorita, HttpServletRequest request) {
        Db_Master dbm = new Db_Master();
        dbm.insertLink(user, getFullUrl(request), codtr, priorita);
        dbm.closeDB();
    }

    /**
     *
     * @param user
     * @param codtr
     * @param priorita
     * @param request
     * @param replace
     * @param from
     * @param to
     */
    public static void insertLink(String user, String codtr, int priorita, HttpServletRequest request, boolean replace,
            String from, String to) {
        String link = getFullUrl(request, replace, from, to);
        Db_Master dbm = new Db_Master();
        dbm.insertLink(user, link, codtr, priorita);
        dbm.closeDB();
    }

    /**
     *
     * @param s
     * @return
     */
    public static String formatSex(String s) {
        if (s.equalsIgnoreCase("M")) {
            return "Male";
        } else if (s.equalsIgnoreCase("F")) {
            return "Female";
        } else if (s.equalsIgnoreCase("O")) {
            return "Other";
        }
        return s;
    }

    /**
     *
     * @param co
     * @return
     */
    public static String[] get_country(String co) {
        Db_Master db = new Db_Master();
        String[] out = db.get_country(co);
        db.closeDB();
        if (out == null) {
            String[] o1 = { co, co, co, co };
            return o1;
        }
        return out;
    }

    /**
     *
     * @param co
     * @return
     */
    public static String[] getCity_apm(String co) {
        Db_Master db = new Db_Master();
        String[] out = db.getCity_apm(co);
        db.closeDB();
        if (out == null) {
            String[] o1 = { co, co, "-" };
            return o1;
        }
        return out;
    }

    /**
     *
     * @param cod_tr
     * @param tipoutil
     * @return
     */
    public static Codici_sblocco getCod_tr(String cod_tr, String tipoutil) {
        Db_Master db = new Db_Master();
        Codici_sblocco cs = db.getCod_tr(cod_tr, tipoutil);
        db.closeDB();
        return cs;
    }

    /**
     *
     * @param cod_tr
     * @param tipoutil
     * @param type
     * @return
     */
    public static Codici_sblocco getCod_tr(String cod_tr, String tipoutil, String type) {
        Db_Master db = new Db_Master();
        if (type != null) {
            if (type.equals("central")) {
                db = new Db_Master(true);
                if (db.getC() == null) {
                    db = new Db_Master();
                }
            }
        }
        Codici_sblocco cs = db.getCod_tr(cod_tr, tipoutil);
        db.closeDB();
        return cs;
    }

    /**
     *
     * @param co
     * @return
     */
    public static String[] get_district(String co) {
        if (co == null || co.equals("---")) {
            if (is_IT) {
                String[] o1 = { "EE", "EE", "-" };
                return o1;
            } else {
                String[] o1 = { "", "", "-" };
                return o1;
            }
        }
        Db_Master db = new Db_Master();
        String[] out = db.get_district(co);
        db.closeDB();
        if (out == null) {
            if (is_IT) {
                String[] o1 = { co, co, "-" };
                return o1;
            } else {
                String[] o1 = { "", "", "-" };
                return o1;
            }
        }
        return out;
    }

    /**
     *
     * @param cod
     * @return
     */
    public static String getRespCod(String cod) {
        Db_Master db = new Db_Master();
        String resp = db.getRespCod(cod);
        db.closeDB();
        return resp;
    }

    /**
     *
     * @param cod
     * @param type
     * @return
     */
    public static String getRespCod(String cod, String type) {
        Db_Master db = new Db_Master();
        if (type != null) {
            if (type.equals("central")) {
                db = new Db_Master(true);
                if (db.getC() == null) {
                    db = new Db_Master();
                }
            }
        }
        String resp = db.getRespCod(cod);
        db.closeDB();
        return resp;
    }

    /**
     *
     * @param li
     * @param cod
     * @return
     */
    public static Company get_Companylist(ArrayList<Company> li, String cod) {
        for (int i = 0; i < li.size(); i++) {
            if (li.get(i).getNdg().equals(cod)) {
                return li.get(i);
            }
        }
        return null;
    }

    /**
     *
     * @param id_oper
     * @param tipo
     * @param kind
     * @param valuta
     * @param table
     * @return
     */
    public static ArrayList<String[]> stock_quantity(String id_oper, String tipo, String kind, String valuta,
            String table) {
        Db_Master db = new Db_Master();
        ArrayList<String[]> out = db.stock_quantity(id_oper, tipo, kind, valuta, table);
        db.closeDB();
        return out;
    }

    /**
     *
     * @param id_oper
     * @param kind
     * @param valuta
     * @return
     */
    public static ArrayList<String[]> rate_history_mod(String id_oper, String kind, String valuta) {
        Db_Master db = new Db_Master();
        ArrayList<String[]> out = db.rate_history_mod(id_oper, kind, valuta);
        db.closeDB();
        return out;
    }

    /**
     *
     * @param cod_tr
     * @return
     */
    public static ArrayList<Stock> list_stock(String cod_tr) {
        Db_Master db = new Db_Master();
        ArrayList<Stock> out = db.list_stock(cod_tr, "stock");
        db.closeDB();
        return out;
    }

    /**
     *
     * @param cod_tr
     * @param kind
     * @param valuta
     * @param table
     * @return
     */
    public static ArrayList<Stock> list_stock(String cod_tr, String kind, String valuta, String table) {
        Db_Master db = new Db_Master();
        ArrayList<Stock> out = db.list_stock(cod_tr, table);
        db.closeDB();
        ArrayList<Stock> def = new ArrayList<>();
        for (int i = 0; i < out.size(); i++) {
            if (out.get(i).getKind().equals(kind)
                    && out.get(i).getCod_value().equals(valuta)) {
                def.add(out.get(i));
            }
        }
        return def;
    }

    /**
     *
     * @param newprotocol
     * @return
     */
    public static Paymat_conf get_conf_paymat(boolean newprotocol) {
        Db_Master db = new Db_Master();
        Paymat_conf out = db.get_conf_paymat(newprotocol);
        db.closeDB();
        return out;
    }

    /**
     *
     * @param figures
     * @param quantity
     * @param rate
     * @param codtransaction
     * @param tipo
     * @param filiale
     * @param kind
     */
    public static void get_spread(String figures, String quantity, String rate, String codtransaction,
            String tipo, String filiale, String kind) {
        boolean ok = true;
        try {
            double dq2 = fd(quantity);
            double dr1 = fd(rate);
            if (dq2 <= 0) {
                ok = false;
            }
            if (dr1 <= 0) {
                ok = false;
            }
        } catch (Exception e) {
            ok = false;
        }
        if (ok) {
            Db_Master db = new Db_Master();
            boolean dividi = db.get_national_office().getChangetype().equals("/");
            ArrayList<Stock> al = db.list_stock(filiale, kind, figures);
            db.closeDB();
            double quant_now = fd(quantity);
            double rate_now = fd(rate);
            double quant_check = quant_now;
            double spread_stock = 0.0;
            ArrayList<String[]> damod = new ArrayList<>();
            for (int i = 0; i < al.size() && quant_check > 0; i++) {
                String cod = al.get(i).getCodice();
                String newcod = "SQ" + generaId(48);
                double vq1 = fd(al.get(i).getTotal());
                double vr1 = fd(al.get(i).getRate());
                if (vq1 > 0) {
                    if (vq1 >= quant_check) {
                        double contr1 = getControvalore(quant_check, vr1, dividi);
                        spread_stock = spread_stock + contr1;
                        String[] v = { newcod, codtransaction, tipo, cod, roundDoubleandFormat(quant_check, 2),
                                al.get(i).getRate(), "U", roundDoubleandFormat(vq1 - quant_check, 2) };
                        damod.add(v);
                        quant_check = 0.0;
                    } else {
                        double contr1 = getControvalore(vq1, vr1, dividi);
                        spread_stock = spread_stock + contr1;
                        // String[] v = {newcod, codtransaction, tipo, cod,
                        // roundDoubleandFormat(quant_check, 2), al.get(i).getRate(), "D", ""};
                        String[] v = { newcod, codtransaction, tipo, cod, roundDoubleandFormat(vq1, 2),
                                al.get(i).getRate(), "U", "0.00" };// 07/11
                        damod.add(v);
                        quant_check = quant_check - vq1;
                    }
                }
            }

            if (quant_check == 0) {
                double spreadtotal = getControvalore(quant_now, rate_now, dividi) - spread_stock;
                Db_Master db1 = new Db_Master();
                boolean es = db1.updateStock(damod, true);
                db1.closeDB();
                //
                // if (es) {
                // return roundDoubleandFormat(spreadtotal, 2);
                // } else {
                // return "KO";
                // }
            }
        }

        // return "0.00";
    }

    /**
     *
     * @param filiale
     * @param figures
     * @param quantity
     * @param kind
     */
    public static void scalareErroridaStock(String filiale, String figures, String quantity, String kind) {
        boolean ok = true;
        try {
            double dq2 = -fd(quantity);
            if (dq2 < 0) {
                return;
            }
        } catch (Exception e) {
            ok = false;
        }

        if (ok) {
            Db_Master db = new Db_Master();
            ArrayList<Stock> al = db.list_stock(filiale, kind, figures);
            // ArrayList<Stock> al = db.list_stock(filiale, "01", figures);
            db.closeDB();

            double quant_now = -fd(quantity);
            double quant_check = quant_now;

            ArrayList<String[]> damod = new ArrayList<>();
            for (int i = 0; i < al.size() && quant_check > 0; i++) {
                String cod = al.get(i).getCodice();
                String newcod = "SQ" + generaId(48);
                double vq1 = fd(al.get(i).getTotal());
                if (vq1 >= quant_check) {
                    String[] v = { newcod, "", "", cod, roundDoubleandFormat(quant_check, 2), al.get(i).getRate(),
                            "U", roundDoubleandFormat(vq1 - quant_check, 2) };
                    damod.add(v);
                    quant_check = 0.0;
                } else {
                    String[] v = { newcod, "", "", cod, roundDoubleandFormat(quant_check, 2), al.get(i).getRate(),
                            "U", "0.00" };// 07/11
                    damod.add(v);
                    quant_check = quant_check - vq1;
                }
            }
            if (quant_check == 0) {
                Db_Master db1 = new Db_Master();
                boolean es = db1.updateStock(damod, false);
                db1.closeDB();
            }
        }
    }

    /**
     *
     * @param req
     * @return
     */
    public static String verifySession(HttpServletRequest req) {
        if (req.getSession() != null) {
            String tip = (String) req.getSession().getAttribute("us_tip");
            String cod = (String) req.getSession().getAttribute("us_cod");
            if (tip == null || cod == null) {
                return "login.jsp";
            }
        } else {
            return "login.jsp";
        }
        return null;
    }

    /**
     *
     * @param req
     * @return
     */
    public static String verifyUser(HttpServletRequest req) {
        if (req.getSession() != null) {
            String tip = (String) req.getSession().getAttribute("us_tip");
            if (tip != null) {
                try {
                    String uri = req.getRequestURI();
                    String pageName = uri.substring(uri.lastIndexOf("/") + 1).trim();
                    Db_Master db = new Db_Master();
                    boolean auth = db.is_authorized(pageName, tip);
                    db.closeDB();
                    if (!auth) {
                        return "page_403.html";
                    }
                } catch (Exception e) {
                    return "login.jsp";
                }
            } else {
                return "login.jsp";
            }
        } else {
            return "login.jsp";
        }
        return null;
    }

    /**
     *
     * @return
     */
    public static ArrayList<NC_causal> list_nc_causal_sell() {
        Db_Master db = new Db_Master();
        ArrayList<NC_causal> out = db.list_nc_causal_sell();
        db.closeDB();
        return out;
    }

    /**
     *
     * @param type
     * @return
     */
    public static ArrayList<NC_causal> list_nc_causal_sell(String type) {
        Db_Master db = new Db_Master();
        if (type != null) {
            if (type.equals("central")) {
                db = new Db_Master(true);
                if (db.getC() == null) {
                    db = new Db_Master();
                }
            }
        }
        ArrayList<NC_causal> out = db.list_nc_causal_sell();
        db.closeDB();
        return out;
    }

    /**
     *
     * @param cod
     * @return
     */
    public static ArrayList<NC_transaction> list_nctransactionfromchange(String cod) {
        Db_Master db = new Db_Master();
        ArrayList<NC_transaction> out = db.list_nctransactionfromchange(cod);
        db.closeDB();
        return out;
    }

    /**
     *
     * @param tra
     * @return
     */
    public static ArrayList<NC_transaction> list_nctransaction_wc_ti(Ch_transaction tra) {
        Db_Master db = new Db_Master();
        ArrayList<NC_transaction> out = db.list_nctransaction_wc_ti(tra);
        db.closeDB();
        return out;
    }

    /**
     *
     * @param cod
     * @param type
     * @return
     */
    public static ArrayList<NC_transaction> list_nctransactionfromchange(String cod, String type) {
        Db_Master db = new Db_Master();
        if (type != null) {
            if (type.equals("central")) {
                db = new Db_Master(true);
                if (db.getC() == null) {
                    db = new Db_Master();
                }
            }
        }
        ArrayList<NC_transaction> out = db.list_nctransactionfromchange(cod);
        db.closeDB();
        return out;
    }

    /**
     *
     * @return
     */
    public static ArrayList<String[]> getIpFiliale() {
        Db_Master db = new Db_Master();
        ArrayList<String[]> out = db.getIpFiliale();
        db.closeDB();
        return out;
    }

    /**
     *
     * @param cod
     * @param stato
     * @return
     */
    public static ArrayList<String[]> query_cs(String cod, String stato) {
        Db_Master db = new Db_Master();
        ArrayList<String[]> out = db.query_cs(cod, stato);
        db.closeDB();
        return out;
    }

    /**
     *
     * @param filiale
     * @param data
     * @return
     */
    public static ArrayList<Office_sp> list_query_officesp(String filiale, String data) {
        Db_Master db = new Db_Master();
        ArrayList<Office_sp> out = db.list_query_officesp(filiale, data);
        db.closeDB();
        return out;
    }

    /**
     *
     * @param user
     * @return
     */
    public static String insertLogin(String user) {
        Db_Master db = new Db_Master();
        String out = db.insertLogin(user);
        db.closeDB();
        return out;
    }

    /**
     *
     * @param cambiobce
     * @param rateexternal
     * @return
     */
    public static String rate_sell_external(String cambiobce, String rateexternal) {
        try {
            double d_rifbce = fd(cambiobce);
            double d_standard = fd(rateexternal);
            double tot_st = d_rifbce * (100.0D + d_standard) / 100.0D;
            return roundDoubleandFormat(tot_st, 8);
        } catch (Exception ex) {
            insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
        }
        return "1.00000000";
    }

    /**
     *
     * @param stato
     * @return
     */
    public static String formatStatusCodiceSblocco(String stato) {
        if (stato.equals("E")) {
            return "UNUSED";
        } else if (stato.equals("U")) {
            return "USED";
        }
        return stato;
    }

    /**
     *
     * @param us
     * @return
     */
    public static String formatUsageCodiceSblocco(String us) {
        if (us.equals("00")) {
            return "REFUND TRANSACTION";
        } else if (us.equals("01")) {
            return "UNLOCK RATE";
        }
        return us;
    }

    /**
     *
     * @param fil
     * @return
     */
    public static String alert_sogliaCOPFX(String fil) {
        Db_Master db = new Db_Master();
        String out = db.alert_sogliaCOPFX(fil);
        db.closeDB();
        return out;
    }

    /**
     *
     * @param cod
     * @return
     */
    public static String query_LOY_transaction(String cod) {
        Db_Master db = new Db_Master();
        String out = db.query_LOY_transaction(cod);
        db.closeDB();
        return out;
    }

    /**
     *
     * @param cod
     * @param type
     * @param fil
     * @return
     */
    public static String query_LOY_transaction(String cod, String type, String fil) {
        Db_Master db = new Db_Master();
        if (!fil.equals("000")) {
            if (type != null) {
                if (!type.equals("central")) {
                    db = new Db_Master(true);
                    if (db.getC() == null) {
                        db = new Db_Master();
                    }
                }
            }
        }
        String out = db.query_LOY_transaction(cod);
        db.closeDB();
        return out;
    }

    /**
     *
     * @param cod_doc
     * @return
     */
    public static Ch_transaction_doc get_tr_doc(String cod_doc) {
        Db_Master db = new Db_Master();
        Ch_transaction_doc out = db.get_tr_doc(cod_doc);
        db.closeDB();
        return out;
    }

    /**
     *
     * @param codtr
     * @param tipodoc
     * @return
     */
    public static Ch_transaction_doc get_tr_doc(String codtr, String tipodoc) {
        Db_Master db = new Db_Master();
        Ch_transaction_doc out = db.get_tr_doc(codtr, tipodoc);
        db.closeDB();
        return out;
    }

    /**
     *
     * @param codtr
     * @param tipodoc
     * @return
     */
    public static Ch_transaction_doc get_tr_doc_central(String codtr, String tipodoc) {
        Db_Master db = new Db_Master(true);
        if (db.getC() == null) {
            db = new Db_Master();
        }
        Ch_transaction_doc out = db.get_tr_doc(codtr, tipodoc);
        db.closeDB();
        return out;
    }

    /**
     *
     * @param cod
     * @return
     */
    public static Codici_sblocco getCods(String cod) {
        Db_Master db = new Db_Master();
        Codici_sblocco out = db.getCods(cod);
        db.closeDB();
        return out;
    }

    /**
     *
     * @param request
     * @return
     */
    public static boolean browserCopyLinkFirma(HttpServletRequest request) {
        String codBrowser = getCodBrowser(request);
        Db_Master db = new Db_Master();
        String abilitato = db.getPath(codBrowser);
        db.closeDB();
        return abilitato.equals("0");
    }

    /**
     *
     * @return
     */
    public static ArrayList<String[]> list_bank_pos_enabled() {

        Db_Master db = new Db_Master();
        ArrayList<String[]> out = db.list_bank_pos_enabled();
        db.closeDB();
        return out;
    }

    /**
     *
     * @param cod
     * @return
     */
    public static ArrayList<Newsletters> query_newsletters_user(String cod) {
        Db_Master db = new Db_Master();
        ArrayList<Newsletters> out = db.query_newsletters_user(cod);
        db.closeDB();
        return out;
    }

    /**
     *
     * @param cod
     * @return
     */
    public static ArrayList<Newsletters> query_newsletters_user_mod(String cod) {
        Db_Master db = new Db_Master();
        ArrayList<Newsletters> out = db.query_newsletters_user_mod(cod);
        db.closeDB();
        return out;
    }

    /**
     *
     * @param cod
     * @return
     */
    public static Newsletters get_newsletters(String cod) {
        Db_Master db = new Db_Master();
        Newsletters out = db.get_newsletters(cod);
        db.closeDB();
        return out;
    }

    /**
     *
     * @return
     */
    public static ArrayList<ET_change> query_et_pending() {
        Db_Master db = new Db_Master();
        ArrayList<ET_change> out = db.query_et_pending();
        db.closeDB();
        return out;
    }

    /**
     *
     * @return
     */
    public static ArrayList<String[]> list_ncmacro() {
        Db_Master db = new Db_Master();
        ArrayList<String[]> out = db.list_ncmacro();
        db.closeDB();
        return out;
    }

    /**
     *
     * @return
     */
    public static ArrayList<String[]> list_department() {
        Db_Master db = new Db_Master();
        ArrayList<String[]> out = db.list_department();
        db.closeDB();
        return out;
    }

    /**
     *
     * @return
     */
    public static ArrayList<String[]> list_department_NC() {
        Db_Master db = new Db_Master();
        ArrayList<String[]> out = db.list_department_NC();
        db.closeDB();
        return out;
    }

    /**
     *
     * @param type
     * @param user
     * @param descr
     */
    public static void insertTR(String type, String user, String descr) {
        Db_Master db = new Db_Master();
        db.insertTR(type, user, descr);
        db.closeDB();
    }

    /**
     *
     * @param user
     * @param descr
     */
    public static void insertTR_R(String user, String descr) {
        Db_Master db = new Db_Master();
        db.insertTR("R", user, descr);
        db.closeDB();
    }

    /**
     *
     * @param li1
     * @param li2
     * @return
     */
    public static String getStringCurrency(ArrayList<String> li1, ArrayList<Currency> li2) {
        String out = "";
        for (int l = 0; l < li1.size(); l++) {
            for (int i = 0; i < li2.size(); i++) {
                if (li1.get(l).equals(li2.get(i).getCode())) {
                    out = out + li2.get(i).getCode() + "-" + li2.get(i).getDescrizione() + "; ";
                }
            }
        }
        return out.trim();
    }

    /**
     *
     * @param li1
     * @param li2
     * @return
     */
    public static String getStringFigures(ArrayList<String> li1, ArrayList<Figures> li2) {
        String out = "";
        for (int l = 0; l < li1.size(); l++) {
            for (int i = 0; i < li2.size(); i++) {
                if (li1.get(l).equals(li2.get(i).getSupporto())) {
                    out = out + li2.get(i).getDe_supporto() + "; ";
                }
            }
        }
        return out.trim();
    }

    /**
     *
     * @return
     */
    public static ArrayList<Users> user_kyc() {
        ArrayList<Users> out = new ArrayList<>();
        Db_Master db = new Db_Master();
        ArrayList<Users> result = db.list_all_users();
        ArrayList<String> l1 = db.list_kyc_enabled();
        db.closeDB();
        for (int y = 0; y < result.size(); y++) {
            Users us1 = result.get(y);
            if (us1.getFg_tipo().equals("2") || us1.getFg_tipo().equals("3")) {
                Users us2 = us1;
                if (l1.contains(us2.getUsername())) {
                    us2.setFg_stato("1");
                } else {
                    us2.setFg_stato("0");
                }
                out.add(us2);
            }
        }
        return out;

    }

    /**
     *
     * @param cod_tr
     * @return
     */
    public static boolean have_kyc_modified(String cod_tr) {
        Db_Master db = new Db_Master();
        boolean result = db.have_kyc_modified(cod_tr);
        db.closeDB();
        return result;
    }

    /**
     *
     * @param tr_cod
     * @return
     */
    public static ArrayList<Client> list_kyc_modified(String tr_cod) {
        Db_Master db = new Db_Master();
        ArrayList<Client> result = db.list_kyc_modified(tr_cod);
        db.closeDB();
        return result;
    }

    /**
     *
     * @param cod_tr
     * @return
     */
    public static String[] internetbooking_ch(String cod_tr) {
        Db_Master db = new Db_Master();
        String[] result = db.internetbooking_ch(cod_tr);
        db.closeDB();
        return result;
    }

    /**
     *
     * @return
     */
    public static String getNow() {
        Db_Master db = new Db_Master();
        String result = db.getNow();
        db.closeDB();
        return result;
    }

    public static DateTime getNowDT() {
        Db_Master db = new Db_Master();
        DateTime result = db.getNowDT();
        db.closeDB();
        return result;
    }

    /**
     *
     * @return
     */
    public static ArrayList<String[]> agevolazioni_varie() {
        Db_Master db = new Db_Master();
        ArrayList<String[]> result = db.agevolazioni_varie();
        db.closeDB();
        return result;
    }

    /**
     *
     * @param data
     * @param BUYSELL
     * @return
     */
    public static ArrayList<String[]> agevolazioni_varie(String data, String BUYSELL) {
        Db_Master db = new Db_Master();
        ArrayList<String[]> result = db.agevolazioni_varie(data, BUYSELL);
        db.closeDB();
        return result;
    }

    /**
     *
     * @return
     */
    public static ArrayList<String[]> list_spread() {
        Db_Master db = new Db_Master();
        ArrayList<String[]> result = db.list_spread();
        db.closeDB();
        return result;
    }

    /**
     *
     * @return
     */
    public static ArrayList<String[]> list_supporti() {
        Db_Master db = new Db_Master();
        ArrayList<String[]> result = db.list_supporti();
        db.closeDB();
        return result;
    }

    /**
     *
     * @return
     */
    public static ArrayList<String[]> list_agevolazioni_varie() {
        Db_Master db = new Db_Master();
        ArrayList<String[]> result = db.list_agevolazioni_varie();
        db.closeDB();
        return result;
    }

    /**
     *
     * @return
     */
    public static ArrayList<String[]> list_site_levelrate() {
        Db_Master db = new Db_Master();
        ArrayList<String[]> result = db.list_site_levelrate();
        db.closeDB();
        return result;
    }

    /**
     *
     * @param valuta
     * @return
     */
    public static String[] get_site_spread(String valuta) {
        Db_Master db = new Db_Master();
        String[] result = db.get_site_spread(valuta);
        db.closeDB();
        return result;
    }

    /**
     *
     * @param minimo
     * @return
     */
    public static String[] get_site_levelrate(String minimo) {
        Db_Master db = new Db_Master();
        String[] result = db.get_site_levelrate(minimo);
        db.closeDB();
        return result;
    }

    /**
     *
     * @param minimo
     * @return
     */
    public static String[] get_site_commissione_fissa(String minimo) {
        Db_Master db = new Db_Master();
        String[] result = db.get_site_commissione_fissa(minimo);
        db.closeDB();
        return result;
    }

    /**
     *
     * @param cod
     * @return
     */
    public static String[] get_site_supporti(String cod) {
        Db_Master db = new Db_Master();
        String[] result = db.get_site_supporti(cod);
        db.closeDB();
        return result;
    }

    /**
     *
     * @param cod
     * @return
     */
    public static String[] get_causali_variazioni(String cod) {
        Db_Master db = new Db_Master();
        String[] result = db.get_causali_variazioni(cod);
        db.closeDB();
        return result;
    }

    /**
     *
     * @return
     */
    public static ArrayList<String[]> list_servizi_agg() {
        Db_Master db = new Db_Master();
        ArrayList<String[]> result = db.list_servizi_agg();
        db.closeDB();
        return result;
    }

    /**
     *
     * @return
     */
    public static ArrayList<String[]> list_causali_variazioni() {
        Db_Master db = new Db_Master();
        ArrayList<String[]> result = db.list_causali_variazioni();
        db.closeDB();
        return result;
    }

    /**
     *
     * @return
     */
    public static ArrayList<String[]> list_commissione_fissa() {
        Db_Master db = new Db_Master();
        ArrayList<String[]> result = db.list_commissione_fissa();
        db.closeDB();
        return result;
    }

    /**
     *
     * @param data
     * @param BUYSELL
     * @return
     */
    public static ArrayList<String[]> servizi_agg(String data, String BUYSELL) {
        Db_Master db = new Db_Master();
        ArrayList<String[]> result = db.servizi_agg(data, BUYSELL);
        db.closeDB();
        return result;
    }

    /**
     *
     * @return
     */
    public static ArrayList<String[]> servizi_agg() {
        Db_Master db = new Db_Master();
        ArrayList<String[]> result = db.servizi_agg();
        db.closeDB();
        return result;
    }

    /**
     *
     * @return
     */
    public static ArrayList<String[]> history_BB() {
        Db_Master db = new Db_Master();
        ArrayList<String[]> result = db.history_BB();
        db.closeDB();
        return result;
    }

    /**
     *
     * @param history_BB
     * @param dt_tr
     * @param f
     * @return
     */
    public static String get_Value_history_BB(ArrayList<String[]> history_BB, DateTime dt_tr, Figures f) {
        for (int x = 0; x < history_BB.size(); x++) {
            String[] va = history_BB.get(x);
            DateTime dt1 = getDT(va[0], patternsqldate);
            DateTime dt2 = getDT(va[1], patternsqldate);
            if (dt_tr.isAfter(dt1) && dt_tr.isBefore(dt2)) {
                return va[2];
            }
        }
        return f.getBuy_back_commission();
    }

    /**
     *
     * @param clientcode
     * @return
     */
    public static ArrayList<NC_transaction> query_NC_transaction_codcl(String clientcode) {
        Db_Master db = new Db_Master();
        ArrayList<NC_transaction> result = db.query_NC_transaction_codcl(clientcode);
        db.closeDB();
        return result;
    }

    /**
     *
     * @param clientcode
     * @return
     */
    public static ArrayList<NC_transaction> query_NC_transaction_codcl_centr(String clientcode) {
        Db_Master db = new Db_Master(true);
        if (db.getC() == null) {
            db = new Db_Master();
        }
        ArrayList<NC_transaction> result = db.query_NC_transaction_codcl(clientcode);
        db.closeDB();
        return result;
    }

    /**
     *
     * @param clientcode
     * @return
     */
    public static ArrayList<Ch_transaction> query_transaction_ch_CLIENT(String clientcode) {
        Db_Master db = new Db_Master();
        ArrayList<Ch_transaction> result = db.query_transaction_ch_CLIENT(clientcode);
        db.closeDB();
        return result;
    }

    /**
     *
     * @param clientcode
     * @return
     */
    public static ArrayList<Ch_transaction> query_transaction_ch_CLIENT_central(String clientcode) {
        Db_Master db = new Db_Master(true);
        if (db.getC() == null) {
            db = new Db_Master();
        }
        ArrayList<Ch_transaction> result = db.query_transaction_ch_CLIENT(clientcode);
        db.closeDB();
        return result;
    }

    /**
     *
     * @param clientcode
     * @return
     */
    public static String getCodiceClienteAttivo(String clientcode) {
        Db_Loy dbl = new Db_Loy();
        String loycode = dbl.getCodiceClienteAttivo(clientcode);
        dbl.closeDB();
        return loycode;
    }

    /**
     *
     * @param cod
     * @return
     */
    public static String get_department_NC(String cod) {
        Db_Master dbl = new Db_Master();
        String dep = dbl.get_department_NC(cod);
        dbl.closeDB();
        return dep;
    }

    public static String get_department_NC(String cod, Db_Master dbl) {
        String dep = dbl.get_department_NC(cod);
        return dep;
    }

    /**
     *
     * @param data
     * @return
     */
    public static boolean data_scadenza_attiva(String data) {
        Db_Master db = new Db_Master();
        DateTime now = db.getCurdateDT();
        db.closeDB();
        DateTime da = getDT(data, patternnormdate_filter);
        return (now.isBefore(da) || now.isEqual(da));
    }

    /**
     *
     * @param codice
     * @return
     */
    public static String[] get_temp_paymat(String codice) {
        Db_Master dbl = new Db_Master();
        String[] value = dbl.get_temp_paymat(codice);
        dbl.closeDB();
        return value;
    }

    /**
     *
     * @param codtr
     * @param codcl
     * @return
     */
    public static String insert_CZ_receipt(String codtr, String codcl) {

        String cod = generaId(50);
        String coddoc;
        String dateoper = new DateTime().toString(patternsqldate);

        Db_Master db = new Db_Master();
        String pathtemp = db.getPath("temp");
        Ch_transaction tra = db.query_transaction_ch(codtr);
        if (tra == null) {
            tra = db.query_transaction_ch_temp(codtr);
        }
        ArrayList<Ch_transaction_value> li = db.query_transaction_value(codtr);
        Client cl = db.query_Client_transaction(codtr, codcl);
        Office ma = db.get_national_office();
        Branch br = db.get_branch(tra.getFiliale());
        String base64;

        if (tra.getTipotr().equals("B")) {
            coddoc = "_czbuy";
        } else {
            coddoc = "_czsell";
        }
        base64 = new NewReceipt_CZ().ricevuta_CZ_DOUBLE(pathtemp, ma, tra, cl, li, br);

        if (base64 != null) {
            String namefile = new DateTime().toString("yyMMddhhmmssSSS") + coddoc + ".pdf";
            Ch_transaction_doc chd = new Ch_transaction_doc(cod, codtr, coddoc, base64, namefile, dateoper, codcl, "Y");
            boolean es = db.insert_transaction_doc(chd);
        }

        db.closeDB();
        return base64;
    }

    /**
     *
     * @param codtr
     * @param codcl
     * @return
     */
    public static String insert_CZ_prereceipt(String codtr, String codcl) {

        String cod = generaId(50);
        String coddoc;
        String dateoper = new DateTime().toString(patternsqldate);

        Db_Master db = new Db_Master();
        String pathtemp = db.getPath("temp");
        Ch_transaction tra = db.query_transaction_ch(codtr);
        if (tra == null) {
            tra = db.query_transaction_ch_temp(codtr);
        }
        ArrayList<Ch_transaction_value> li = db.query_transaction_value(codtr);
        Client cl = db.query_Client_transaction(codtr, codcl);
        Office ma = db.get_national_office();
        Branch br = db.get_branch(tra.getFiliale());
        String base64;

        if (tra.getTipotr().equals("B")) {
            coddoc = "_prebuy";
        } else {
            coddoc = "_presell";
        }
        base64 = new NewReceipt_CZ().prericevuta_CZ(pathtemp, ma, tra, cl, li, br);

        if (base64 != null) {
            String namefile = new DateTime().toString("yyMMddhhmmssSSS") + coddoc + ".pdf";
            Ch_transaction_doc chd = new Ch_transaction_doc(cod, codtr, coddoc, base64, namefile, dateoper, codcl, "Y");
            boolean es = db.insert_transaction_doc(chd);
        }

        db.closeDB();
        return base64;
    }

    /**
     *
     * @param codtr
     * @param codcl
     */
    public static void insert_UK_receipt(String codtr, String codcl) {
        String cod = generaId(50);
        String coddoc = "_receipt";
        String dateoper = new DateTime().toString(patternsqldate);
        String base64;
        Db_Master db = new Db_Master();
        String pathtemp = db.getPath("temp");
        Ch_transaction tra = db.query_transaction_ch(codtr);
        if (tra == null) {
            tra = db.query_transaction_ch_temp(codtr);
        }
        ArrayList<Ch_transaction_value> li = db.query_transaction_value(codtr);
        Client cl = db.query_Client_transaction(codtr, codcl);
        Office ma = db.get_national_office();

        String namefile = new DateTime().toString("yyMMddhhmmssSSS") + coddoc + ".pdf";
        ArrayList<Figures> fig = db.list_all_figures();
        Branch bra = db.get_branch(tra.getFiliale());
        base64 = new NewReceipt_UK().ricevuta_UK(pathtemp, ma, tra, cl, li, bra, fig);

        Ch_transaction_doc chd = new Ch_transaction_doc(cod, codtr, coddoc, base64, namefile, dateoper, codcl, "Y");
        boolean es = db.insert_transaction_doc(chd);
        db.closeDB();
    }

    /**
     *
     * @param loya
     * @param codcl
     */
    public static void remove_loy_assign_first_NEW(String loya, String codcl) {
        if (loya != null) {
            Db_Loy dbl = new Db_Loy();
            if (dbl.getC() != null) {
                String completo[] = dbl.getCodiceCompleto(loya);
                if (completo != null) {
                    boolean isfirst = dbl.is_firsttransaction_cod(completo[0]);
                    if (isfirst) {
                        dbl.set_stato_associaz_cliente(codcl, "0");
                        dbl.update_stato_codici(completo[0], "0");
                        dbl.remove_mac_associate(codcl, completo[0]);
                        Db_Master db0 = new Db_Master();
                        db0.libera_loyalty_FIRST(completo[0], "service");
                        db0.closeDB();
                    }
                }
            }
            dbl.closeDB();
        }
    }

    /**
     *
     * @param loya
     * @param codcl
     */
    public static void remove_loy_assign_first(String loya, String codcl) {
        if (loya != null) {
            Db_Loy dbl = new Db_Loy();
            if (dbl.getC() != null) {
                boolean isfirst = dbl.is_firsttransaction(codcl);
                if (isfirst) {
                    String completo[] = dbl.getCodiceCompleto(loya);
                    if (completo != null) {
                        dbl.set_stato_associaz_cliente(codcl, "0");

                        dbl.update_stato_codici(completo[0], "0");
                        dbl.remove_mac_associate(codcl, completo[0]);

                        Db_Master db0 = new Db_Master();
                        db0.libera_loyalty_FIRST(completo[0], "service");
                        db0.closeDB();
                    }
                }
                dbl.closeDB();
            }
        }
    }

    private static List<String> getListTable() {
        List<String> tabelle = new ArrayList<>();
        tabelle.add("ch_transaction");
        tabelle.add("ch_transaction_temp");
        tabelle.add("et_change");
        tabelle.add("it_change");
        tabelle.add("oc_lista");
        tabelle.add("office_sp");
        tabelle.add("ch_transaction_doc");
        tabelle.add("ch_transaction_refund");
        tabelle.add("ch_transaction_file");
        tabelle.add("ch_transaction_valori");
        tabelle.add("et_change_tg");
        tabelle.add("et_change_valori");
        tabelle.add("et_frombranch");
        tabelle.add("et_nochange_valori");
        tabelle.add("it_change_tg");
        tabelle.add("it_change_valori");
        tabelle.add("it_nochange_valori");
        tabelle.add("oc_change");
        tabelle.add("oc_change_tg");
        tabelle.add("oc_errors");
        tabelle.add("oc_nochange");
        tabelle.add("oc_pos");
        tabelle.add("real_oc_change");
        tabelle.add("real_oc_change_tg");
        tabelle.add("real_oc_nochange");
        tabelle.add("real_oc_pos");
        tabelle.add("office_sp_valori");
        tabelle.add("forex_prenot");
        tabelle.add("inv_incremental");
        tabelle.add("inv_list");
        tabelle.add("nc_transaction");
        tabelle.add("rate_history");
        tabelle.add("rate_history_mod");
        tabelle.add("stock");
        tabelle.add("stock_report");
        tabelle.add("stock_story");
        tabelle.add("stock_quantity");
        tabelle.add("ch_transaction_doc_story");
        tabelle.add("ch_transaction_file_story");
        tabelle.add("ch_transaction_refund_story");
        tabelle.add("ch_transaction_story");
        tabelle.add("ch_transaction_valori_story");
        tabelle = tabelle.stream().distinct().collect(toList());
        return tabelle;
    }

    /**
     *
     * @param fil
     * @param db
     */
    public static void delete_volatili_WEBAPP(String fil, Db_Master db) {
        List<String> tabelle = getListTable();
        String dd1 = db.getConf("date.del");

        ////////////////////////////////////////////
        if (dd1.equals("-")) {
            err.println("date.del NOT FOUND");
            return;
        }
        ////////////////////////////////////////////

        String now1 = db.getNow();
        DateTime dts1t = forPattern(patternsql).parseDateTime(dd1);
        DateTime dtno1 = forPattern(patternsqldate).parseDateTime(now1.substring(0, 19));
        if (db.getC() == null) {
            err.println(fil + " NON RAGGIUNGIBILE");
        } else {
            try {
                String delcodicisblocco = "DELETE FROM codici_sblocco WHERE codice in (SELECT c1.codice from codici_sblocco c1, codici_sblocco_file c2 WHERE c1.fg_stato='E' AND c2.dest ='0000 - utest0000' AND c2.listcod like concat('%',c1.codice,'%'))";
                db.getC().createStatement(TYPE_SCROLL_INSENSITIVE, CONCUR_UPDATABLE).executeUpdate(delcodicisblocco);
            } catch (SQLException e) {
            }
            if (dts1t.isBefore(dtno1)) {
                for (int f = 0; f < tabelle.size(); f++) {
                    try {
                        String table = tabelle.get(f);
                        ResultSet rs = db.getC().createStatement(TYPE_SCROLL_INSENSITIVE, CONCUR_UPDATABLE)
                                .executeQuery("SELECT * FROM " + table + " LIMIT 1");
                        ResultSetMetaData rsmd = rs.getMetaData();
                        int numerocolonne = rsmd.getColumnCount();
                        String fieldsel = "";
                        for (int x = 1; x <= numerocolonne; x++) {
                            if (rsmd.getColumnName(x).equals("data")
                                    || rsmd.getColumnName(x).equals("dt_it")
                                    || rsmd.getColumnName(x).equals("dt_refund")
                                    || rsmd.getColumnName(x).equals("data_load")
                                    || rsmd.getColumnName(x).equals("dt_tr")
                                    || rsmd.getColumnName(x).equals("date")
                                    || rsmd.getColumnName(x).equals("timestamp")
                                    || rsmd.getColumnName(x).equals("dt")
                                    || rsmd.getColumnName(x).equals("dt_mod")) {
                                fieldsel = rsmd.getColumnName(x);
                                break;
                            }
                        }
                        if (!fieldsel.equals("")) {
                            String sel = "SELECT * FROM " + table + " WHERE " + fieldsel + " < '" + dd1
                                    + " 00:00:00' LIMIT 1";
                            ResultSet rs1 = db.getC().createStatement(TYPE_SCROLL_INSENSITIVE, CONCUR_UPDATABLE)
                                    .executeQuery(sel);
                            if (rs1.next()) {
                                String del = "DELETE FROM " + table + " WHERE " + fieldsel + " < '" + dd1
                                        + " 00:00:00'";
                                db.getC().createStatement(TYPE_SCROLL_INSENSITIVE, CONCUR_UPDATABLE).execute(del);
                            } else {
                                err.println(sel + ": NOT FOUND");
                            }
                        } else {
                            for (int x = 1; x <= numerocolonne; x++) {
                                err.println("table " + table + " column: " + rsmd.getColumnName(x));
                            }
                        }
                    } catch (SQLException e) {
                        err.println("ERROR: " + e.getMessage());
                    }
                }
            }
        }
    }

    /**
     *
     * @return
     */
    public static double get_soglia_CZ() {
        if (is_CZ) {
            Currency euro = getCurrency("EUR");
            return fd(euro.getCambio_bce());
        } else {
            return 1.0;
        }
    }

    /**
     *
     * @param soglia
     * @return
     */
    public static double get_soglia_CZ(double soglia) {
        if (is_CZ) {
            Currency euro = getCurrency("EUR");
            double out = soglia * fd(euro.getCambio_bce());
            return out;
        } else {
            return soglia;
        }

    }

    /**
     * generateUsernameONESHOT
     */
    public static void generateUsernameONESHOT() {
        Db_Master db1 = new Db_Master();
        ArrayList<Users> li = db1.list_all_users_enabled();
        li.forEach(us1 -> {
            if (!us1.getCod().equals("0000")) {
                try {
                    String username = generateUsername(us1.getDe_nome(), us1.getDe_cognome(), us1.getCod());
                    String upd = "UPDATE users SET username = ?  WHERE cod = ?";
                    PreparedStatement ps = db1.getC().prepareStatement(upd, TYPE_SCROLL_INSENSITIVE, CONCUR_UPDATABLE);
                    ps.setString(1, username);
                    ps.setString(2, us1.getCod());
                    int x = ps.executeUpdate();

                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        db1.closeDB();
    }

    /**
     *
     * @param filiale
     * @param codiceopenclose
     * @param user
     * @return
     */
    public static List<CashAD_CZ> list_CA_CZ(String filiale, String codiceopenclose, String user) {
        List<CashAD_CZ> ca1 = new ArrayList<>();
        List<CashAD_CZ> ca3 = new ArrayList<>();
        List<String> cartevendute = new ArrayList<>();
        List<String> valutevendute = new ArrayList<>();
        Db_Master db1 = new Db_Master();
        ArrayList<String[]> cc = db1.credit_card(filiale);

        ArrayList<Currency> cu1 = db1.list_figures();

        try {
            String sql = "SELECT v.pos,v.valuta,v.rate,v.quantita FROM ch_transaction c, ch_transaction_valori v WHERE c.id_open_till = ? "
                    + "AND v.supporto = ? AND c.user = ? AND c.cod=v.cod_tr AND c.del_fg = ?";
            PreparedStatement ps = db1.getC().prepareStatement(sql, TYPE_SCROLL_INSENSITIVE, CONCUR_UPDATABLE);
            ps.setString(1, codiceopenclose);
            ps.setString(2, "04");
            ps.setString(3, user);
            ps.setString(4, "0");
            ResultSet rs1 = ps.executeQuery();
            while (rs1.next()) {
                cartevendute.add(rs1.getString(1));
                valutevendute.add(rs1.getString(2));
                CashAD_CZ cz1 = new CashAD_CZ(rs1.getString(1), "", rs1.getString(2), rs1.getString(3), "1",
                        rs1.getString(4));
                Currency valuta = cu1.stream().filter(va1 -> va1.getCode().equalsIgnoreCase(cz1.getValuta())).findAny()
                        .orElse(null);
                if (valuta != null && newpread) {
                    cz1.setRatebce(roundDoubleandFormat(fd(valuta.getCambio_bce()), 8));
                }
                ca1.add(cz1);
            }
            rs1.close();
            ps.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        db1.closeDB();

        List<String> cartevendute2 = cartevendute.stream().distinct().collect(toList());
        List<String> valutevendute2 = valutevendute.stream().distinct().collect(toList());

        cartevendute2.forEach(carta1 -> {
            valutevendute2.forEach(valuta1 -> {
                List<CashAD_CZ> ca2 = ca1.stream()
                        .filter(cash1 -> cash1.getPos().equals(carta1) && cash1.getValuta().equals(valuta1))
                        .collect(toList());
                CashAD_CZ v1 = new CashAD_CZ(carta1, formatAL(carta1, cc, 1), valuta1, "", valueOf(ca2.size()), "");
                AtomicDouble total = new AtomicDouble(0.0);
                AtomicDouble rate = new AtomicDouble(0.0);
                ca2.forEach(cad -> {
                    total.addAndGet(fd(cad.getTotal()));
                    rate.addAndGet(fd(cad.getRate()));
                });
                v1.setTotal(roundDoubleandFormat(total.get(), 2));
                v1.setRate(roundDoubleandFormat(rate.get() / fd(valueOf(ca2.size())), 8));
                Currency valuta = cu1.stream().filter(va1 -> va1.getCode().equalsIgnoreCase(v1.getValuta())).findAny()
                        .orElse(null);
                if (valuta != null && newpread) {
                    v1.setRatebce(roundDoubleandFormat(fd(valuta.getCambio_bce()), 8));
                }
                ca3.add(v1);
            });
        });
        return ca3;
    }

    /**
     *
     * @param date
     * @param value
     * @return
     */
    public static String get_BCE_CZ(DateTime date, String value) {
        Db_Master db1 = new Db_Master(true);
        if (db1.getC() == null) {
            db1 = new Db_Master();
        }
        String out = db1.get_BCE_CZ(date, value);
        db1.closeDB();
        return out;
    }

    public static String get_BCE(DateTime date, String value) {
        Db_Master db1 = new Db_Master();
        String out = db1.get_BCE(date, value);
        db1.closeDB();
        if (out.equals("0")) {
            db1 = new Db_Master(true);
            out = db1.get_BCE(date, value);
            db1.closeDB();
        }
        return out;
    }

    public static String get_BCE_central(DateTime date, String value) {
        Db_Master db1 = new Db_Master(true);
        if (db1.getC() == null) {
            db1 = new Db_Master();
        }
        String out = db1.get_BCE_central(date, value);
        db1.closeDB();
        return out;
    }

    /**
     *
     * @param date
     * @param value
     * @return
     */
    // public static String get_BCE(DateTime date, String value) {
    // Db_Master db1 = new Db_Master(true);
    // if (db1.getC() == null) {
    // db1 = new Db_Master();
    // }
    // String out = db1.get_BCE(date, value);
    // db1.closeDB();
    // return out;
    // }
    /**
     *
     * @param cod
     * @return
     */
    public static String view_reprint_nctr(String cod) {
        String base64 = null;
        NC_transaction nc = get_NC_transaction(cod);
        if (nc != null) {
            Db_Master db = new Db_Master();
            Ch_transaction_doc firmato = db.get_doc_nc_firmato(cod, "_macnctaxf");
            if (firmato != null) {
                base64 = firmato.getContent();
                if (base64.startsWith("FILE[")) {
                    String pa1 = replace(base64, "FILE[", "");
                    File f = new File(pa1);
                    try {
                        base64 = encodeBase64String(readFileToByteArray(f));
                    } catch (IOException ex) {
                        base64 = null;
                    }
                }
            } else {
                if (nc.getFg_tipo_transazione_nc().equals("1")
                        || nc.getFg_tipo_transazione_nc().equals("2")
                        || nc.getFg_tipo_transazione_nc().equals("4")
                        || nc.getFg_tipo_transazione_nc().equals("6")
                        || nc.getFg_tipo_transazione_nc().equals("7")) {
                    base64 = new Pdf().receipt_nochange_norm(nc);
                } else {
                    base64 = new Pdf().receipt_nochange_anag(nc);
                }
            }

        }

        return base64;
    }

    /**
     *
     * @param tipocliente
     * @param importototale
     * @param nazionecliente
     * @param identificationCard
     * @return
     */
    public static ArrayList<String[]> listaDocumentiAccettati(
            CustomerKind tipocliente,
            double importototale,
            String nazionecliente,
            ArrayList<String[]> identificationCard) {

        if (is_CZ || is_UK) {
            return identificationCard;
        }

        ArrayList<String[]> identificationCardFinal = new ArrayList<>();
        String[] none = { "---", "None" };
        identificationCardFinal.add(none);

        if (nazionecliente == null || nazionecliente.equals("---")) {
            return identificationCardFinal;
        }

        if (tipocliente.getFg_area_geografica().equals("1")) { // RESIDENTE ITA
            if (importototale > 0 && importototale < 3000.00) {
                String[] compliant = { "CI", "PS", "PD", "PN", "PT", "SO", "TM", "LP", "PA" };
                List<String> compliantlist = asList(compliant);
                for (int i = 0; i < identificationCard.size(); i++) {
                    if (compliantlist.contains(identificationCard.get(i)[0])) {
                        identificationCardFinal.add(identificationCard.get(i));
                    }
                }
            }
        } else if (importototale >= 3000.00) {
            String[] compliant = { "PS", "PD" };
            List<String> compliantlist = asList(compliant);
            for (int i = 0; i < identificationCard.size(); i++) {
                if (compliantlist.contains(identificationCard.get(i)[0])) {
                    identificationCardFinal.add(identificationCard.get(i));
                }
            }
        } else if (tipocliente.getFg_area_geografica().equals("2")) { // RESIDENTE SEE
            if (importototale > 0 && importototale < 3000.00) {
                String[] compliant = { "CI", "PS", "PD", "PT", "CC" };
                List<String> compliantlist = asList(compliant);
                for (int i = 0; i < identificationCard.size(); i++) {
                    if (compliantlist.contains(identificationCard.get(i)[0])) {
                        identificationCardFinal.add(identificationCard.get(i));
                    }
                }
            }
        } else if (tipocliente.getFg_area_geografica().equals("3")) { // RESIDENTE EXTRASEE

            String kyc_list = getPath("kyc_listro");
            String kyc_listok1 = getPath("kyc_listok");

            List<String> kyc_listro = newArrayList(on(";").split(kyc_list));
            List<String> kyc_listok = newArrayList(on(";").split(kyc_listok1));

            // String[] nazioni_1 = {"007", "011", "013", "103", "114", "088", "084", "046",
            // "147", "069", "078", "071", "037"};
            // String[] nazioni_2 = {"002", "274", "074", "159", "039", "038", "136", "065",
            // "132", "121", "042"};
            if (kyc_listok.contains(nazionecliente)) {
                if (importototale > 0 && importototale < 3000.00) {
                    String[] compliant = { "CI", "PS", "PD", "PT", "CC", "SO" };
                    List<String> compliantlist = asList(compliant);
                    for (int i = 0; i < identificationCard.size(); i++) {
                        if (compliantlist.contains(identificationCard.get(i)[0])) {
                            identificationCardFinal.add(identificationCard.get(i));
                        }
                    }
                }
            } else if (kyc_listro.contains(nazionecliente)) {
                String[] compliant = { "PS", "PD" };
                List<String> compliantlist = asList(compliant);
                for (int i = 0; i < identificationCard.size(); i++) {
                    if (compliantlist.contains(identificationCard.get(i)[0])) {
                        identificationCardFinal.add(identificationCard.get(i));
                    }
                }
            } else {
                if (importototale > 0 && importototale < 3000.00) {
                    String[] compliant = { "PS", "PD", "CC", "SO" };
                    List<String> compliantlist = asList(compliant);
                    for (int i = 0; i < identificationCard.size(); i++) {
                        if (compliantlist.contains(identificationCard.get(i)[0])) {
                            identificationCardFinal.add(identificationCard.get(i));
                        }
                    }
                }
            }
        }

        return identificationCardFinal;

    }

    /**
     *
     * @param cod_client
     * @return
     */
    public static boolean is_marketing_OK(String cod_client) {
        Db_Master db1 = new Db_Master(true);
        if (db1.getC() == null) {
            db1 = new Db_Master();
        }

        boolean m1 = db1.is_marketing_OK(cod_client);
        db1.closeDB();
        return m1;
    }

    /**
     *
     * @param cod_oc
     * @return
     */
    public static boolean is_selectAll_OC(String cod_oc) {
        Db_Master db1 = new Db_Master();
        boolean m1 = db1.is_selectAll_OC(cod_oc);
        db1.closeDB();
        return m1;
    }

    /**
     *
     * @param note
     * @param maxlength
     * @return
     */
    public static String visualNote(String note, int maxlength) {
        try {
            return substring(parse(note).text(), 0, maxlength);
        } catch (Exception ex) {
            err.println("ERROR: " + ex.getMessage());
        }
        return note;
    }

    /**
     *
     * @param old_b
     * @param stato
     * @param totale
     * @return
     */
    public static String getBookingJSON(Booking old_b, String stato, String totale) {
        try {
            JSONObject json = new JSONObject();
            JSONObject json_cl = new JSONObject();
            json_cl.put("surname", old_b.getCl_cognome());
            json_cl.put("name", old_b.getCl_nome());
            json_cl.put("telephone", old_b.getCl_telefono());
            json_cl.put("email", old_b.getCl_email());
            json.put("commission", old_b.getFx_comm());
            json.put("agency", old_b.getFiliale());
            json.put("from_import", "");
            json.put("from_rate", "");
            json.put("id", old_b.getCod());
            json.put("note", old_b.getNote());
            json.put("status", stato);
            json.put("total", totale);
            json.put("agevolazioni", old_b.getSconti());
            json.put("serviziagg", old_b.getProdotti());
            json.put("tipologia", old_b.getCl_tipologia());
            json.put("canale", old_b.getCanale());
            json.put("to_rate", old_b.getCurrency());
            json.put("quantita", old_b.getTotal());
            json.put("rate", old_b.getRate());
            json.put("dt_tr", old_b.getDt_ritiro());
            json.put("user_data", json_cl);
            return json.toString();
        } catch (Exception ex) {
            err.println("ERROR: " + ex.getMessage());
        }
        return null;
    }

    /**
     *
     * @param cod
     * @return
     */
    public static ArrayList<Ch_transaction_file> list_transaction_modify(String cod) {
        Db_Master db1 = new Db_Master();
        ArrayList<Ch_transaction_file> m1 = db1.list_transaction_modify(cod);
        db1.closeDB();
        return m1;
    }

    /**
     *
     * @param codbo
     * @return
     */
    public static String[] get_internetbooking_tr(String codbo) {
        Db_Master db1 = new Db_Master();
        String[] m1 = db1.get_internetbooking_tr(codbo);
        db1.closeDB();
        return m1;
    }

    /**
     *
     * @param wk
     * @param tr
     * @param cl
     * @param loycode
     * @return
     */
    public static boolean insertNC_transaction_WK(boolean wk, Ch_transaction tr, Client cl, String loycode) {

        String nccat;
        String notes;
        if (wk) { // WELCOME KIT
            if (tr.getTipotr().equals("B")) {
                nccat = "WELCOMEKIT";
            } else {
                nccat = "WELKITSELL";
            }
            notes = "Welcome Kit Transaction From: " + tr.getFiliale() + tr.getId();
        } else {
            nccat = "TOPITACARD";
            notes = "Top Italy Card Transaction From: " + tr.getFiliale() + tr.getId();
        }

        String cod = "NC" + generaId(23);
        String dateoper = new DateTime().toString(patternsqldate);
        NC_transaction nct = new NC_transaction();
        nct.setGruppo_nc(nccat);
        Db_Master db12a = new Db_Master();
        String[] loc_cur = db12a.get_local_currency();
        NC_causal nc2 = db12a.get_causale_vendita(tr.getFiliale(), nccat);
        db12a.closeDB();

        if (nc2 == null) {
            return false;
        } else {
            nct.setDocrico("-");
            nct.setCod(cod);
            nct.setFiliale(tr.getFiliale());
            nct.setUser(tr.getUser());
            nct.setId_open_till(tr.getId_open_till());
            nct.setTill(tr.getTill());
            nct.setNote(notes);
            nct.setCausale_nc(nc2.getCausale_nc());
            nct.setPrezzo("0.00");
            nct.setFg_tipo_transazione_nc(nc2.getFg_tipo_transazione_nc());
            nct.setFg_inout(nc2.getFg_in_out());

            nct.setSupporto(tr.getLocalfigures());
            nct.setValuta(loc_cur[0]);

            switch (tr.getLocalfigures()) {
                case "06":
                    nct.setPos(tr.getPos());
                    nct.setPosnum("");
                    break;
                case "07":
                    nct.setPos(tr.getPos());
                    nct.setPosnum("");
                    break;
                case "08":
                    nct.setPos(tr.getPos());
                    nct.setPosnum("");
                    break;
                default:
                    nct.setPos("");
                    nct.setPosnum("");
                    break;
            }

            nct.setQuantita("1.00");
            nct.setPrezzo("0.00");
            nct.setTotal("0.00");

            nct.setDel_dt("1901-01-01 00:00:00");
            nct.setDel_user("-");
            nct.setDel_motiv("-");
            nct.setCh_transaction("-");
            nct.setDel_fg("0");

            nct.setFg_dogana("01"); // NO CUSTOMS
            nct.setCl_cognome(cl.getCognome());
            nct.setCl_nome(cl.getNome());
            nct.setCl_indirizzo(cl.getIndirizzo());
            nct.setCl_citta(cl.getCitta()); // FORMATTARE
            nct.setCl_nazione(cl.getNazione());// FORMATTARE
            nct.setCl_email(cl.getEmail());
            nct.setCl_telefono(cl.getTelefono());

            nct.setBonus(nc2.getBonus());

            Db_Master db6 = new Db_Master();
            ArrayList<String[]> list_oc_nochange = db6.list_oc_nochange_real(tr.getId_open_till());
            db6.closeDB();

            String quantold = "0";
            for (int i = 0; i < list_oc_nochange.size(); i++) {
                if (list_oc_nochange.get(i)[1].equalsIgnoreCase(nct.getGruppo_nc())) {
                    quantold = list_oc_nochange.get(i)[2];
                    break;
                }
            }
            if (fd(quantold) >= 1.0) {

                Real_oc_nochange from = new Real_oc_nochange();
                from.setFiliale(tr.getFiliale());
                from.setCod_oc(tr.getId_open_till());
                from.setGruppo_nc(nct.getGruppo_nc());
                Db_Master db7 = new Db_Master();
                from.setQuantity(
                        valueOf(parseIntR(db7.getQuantity_real_oc_nochange(from)) - parseIntR(nct.getQuantita())));
                from.setData(dateoper);
                db7.update_real_oc_nochange(from);
                db7.closeDB();

                // DECREMENTARE
                Db_Master db8 = new Db_Master();
                Stock_report sr = db8.get_Stock_report(tr.getId_open_till(), nccat, "01", "NC", tr.getFiliale(),
                        tr.getTill());
                db8.closeDB();

                String codsr = tr.getFiliale() + generaId(47);
                sr.setCodtr(cod);
                sr.setCodice(codsr);
                sr.setTotal("0.00");
                sr.setSpread("1.0000");
                sr.setData(dateoper);
                sr.setQuantity("-" + parseIntR(nct.getQuantita()));
                sr.setUser(tr.getUser());
                Db_Master db9 = new Db_Master();
                db9.insert_Stockreport(sr);
                db9.closeDB();

                if (nct.getSupporto().equals("06") || nct.getSupporto().equals("07")
                        || nct.getSupporto().equals("08")) { // creditcard
                    Real_oc_pos rop = new Real_oc_pos();
                    rop.setFiliale(tr.getFiliale());
                    rop.setCod_oc(tr.getId_open_till());
                    rop.setValuta(loc_cur[0]);
                    rop.setKind(nct.getSupporto());
                    rop.setData(dateoper);
                    rop.setCarta_credito(nct.getPos());
                    Db_Master db10 = new Db_Master();
                    rop.setIp_quantity(valueOf(parseIntR(db10.getField_real_oc_pos(rop, "ip_quantity", "0")) + 1));
                    rop.setIp_value(db10.getField_real_oc_pos(rop, "ip_value", "0.00"));
                    db10.update_real_oc_pos(rop);
                    db10.closeDB();
                } else {
                    Real_oc_change to = new Real_oc_change();
                    to.setFiliale(tr.getFiliale());
                    to.setCod_oc(tr.getId_open_till());
                    to.setValuta(loc_cur[0]);
                    to.setKind(nct.getSupporto());
                    to.setData(dateoper);
                    to.setNum_kind_op("0");
                    Db_Master db10 = new Db_Master();
                    to.setValue_op(db10.getField_real_oc_change(to, "value_op", "0.00"));
                    db10.update_real_oc_change(to);
                    db10.closeDB();
                }

                Db_Master db11 = new Db_Master();
                boolean es = db11.insert_NC_transaction(nct);
                db11.closeDB();

                if (es) {
                    Db_Master db12ac = new Db_Master();
                    db12ac.insertLoy_TR(cod, cl.getCode(), loycode, tr.getUser());
                    db12ac.closeDB();
                }

                return es;
            } else {
                return false;
            }
        }
    }

    /**
     *
     * @param code
     * @param motiv
     * @param user
     * @param filiale
     * @return
     */
    public static String remove_nochange_transaction(String code, String motiv, String user, String filiale) {
        Db_Master db = new Db_Master();
        DateTime today = db.getCurdateDT();
        String[] loc_cur = db.get_local_currency();
        String dateoper = new DateTime().toString(patternsqldate);
        NC_transaction nct = db.get_NC_transaction(code);
        db.closeDB();
        String ok = "0";

        if (nct == null || nct.getDel_fg().equals("1")) {
            ok = "kk";
        } else {

            boolean activefr = false;
            Db_Master db0 = new Db_Master();
            ArrayList<Till> array_till = db0.list_till_status("O", user, nct.getFiliale());
            db0.closeDB();
            for (int j = 0; j < array_till.size(); j++) {
                if (array_till.get(j).getId_opcl().equals(nct.getId_open_till())) {
                    DateTime dt1 = getDT(nct.getData().substring(0, 10), patternsql);
                    activefr = today.isEqual(dt1);
                    break;
                }
            }

            if (activefr) {

                Db_Master db1 = new Db_Master();
                ArrayList<Till> list_till = db1.list_ALLtill();
                db1.closeDB();
                Till tdest = getContainsTill(nct.getTill(), list_till);

                boolean controlliprima = false;

                String quantitaNCdaverificare = replace(nct.getQuantita(), "-", "").trim();
                String totaledausare = replace(nct.getTotal(), "-", "").trim();

                switch (nct.getFg_inout()) {
                    case "1":
                    case "3":
                        if (nct.getSupporto().equals("01")) {
                            // verifico solo euro in cassa
                            Real_oc_change to = new Real_oc_change();
                            to.setFiliale(nct.getFiliale());
                            to.setCod_oc(nct.getId_open_till());
                            to.setValuta(loc_cur[0]);
                            to.setKind(nct.getSupporto());
                            to.setData(dateoper);
                            to.setNum_kind_op("0");
                            Db_Master db2 = new Db_Master();
                            double euroincassa = fd(db2.getField_real_oc_change(to, "value_op", "0.00"));
                            db2.closeDB();
                            double eurodascalare = fd(totaledausare);
                            if (eurodascalare <= euroincassa) {
                                controlliprima = true;
                            }
                        } else {
                            controlliprima = true;
                        }
                        break;
                    case "2":
                        // nessun controllo perchè aggiungo solo euro
                        controlliprima = true;
                        break;
                    case "4":
                        Real_oc_nochange from = new Real_oc_nochange();
                        from.setFiliale(filiale);
                        from.setCod_oc(nct.getId_open_till());
                        from.setGruppo_nc(nct.getGruppo_nc());
                        Db_Master db2 = new Db_Master();
                        double ncincassa = fd(db2.getQuantity_real_oc_nochange(from));
                        db2.closeDB();
                        double ncdascalare = fd(quantitaNCdaverificare);
                        if (ncdascalare <= ncincassa) {
                            controlliprima = true;
                        }
                        break;
                    default:
                        break;
                }

                boolean es = true;

                if (controlliprima) {
                    Db_Master db3 = new Db_Master();
                    es = db3.delete_transaction_noch(code, motiv, user);
                    db3.closeDB();
                    if (es) {
                        switch (nct.getFg_inout()) {
                            case "1":
                                // Money IN
                                if (nct.getSupporto().equals("06") || nct.getSupporto().equals("07")
                                        || nct.getSupporto().equals("08")) {
                                    // creditcard
                                    Real_oc_pos rop = new Real_oc_pos();
                                    rop.setFiliale(nct.getFiliale());
                                    rop.setCod_oc(nct.getId_open_till());
                                    rop.setValuta(loc_cur[0]);
                                    rop.setKind(nct.getSupporto());
                                    rop.setData(dateoper);
                                    rop.setCarta_credito(nct.getPos());
                                    Db_Master db4 = new Db_Master();
                                    rop.setIp_quantity(
                                            valueOf(parseIntR(db4.getField_real_oc_pos(rop, "ip_quantity", "0")) - 1));
                                    rop.setIp_value(roundDoubleandFormat(
                                            (fd(db4.getField_real_oc_pos(rop, "ip_value", "0.00")) - fd(totaledausare)),
                                            2));
                                    db4.update_real_oc_pos(rop);
                                    db4.closeDB();
                                } else {
                                    Real_oc_change to = new Real_oc_change();
                                    to.setFiliale(nct.getFiliale());
                                    to.setCod_oc(nct.getId_open_till());
                                    to.setValuta(loc_cur[0]);
                                    to.setKind(nct.getSupporto());
                                    to.setData(dateoper);
                                    to.setNum_kind_op("0");
                                    Db_Master db4 = new Db_Master();
                                    to.setValue_op(roundDoubleandFormat(
                                            (fd(db4.getField_real_oc_change(to, "value_op", "0.00"))
                                                    - fd(totaledausare)),
                                            2));
                                    db4.update_real_oc_change(to);
                                    db4.closeDB();

                                    Db_Master db8 = new Db_Master();
                                    Stock_report sr = db8.get_Stock_report(nct.getId_open_till(), loc_cur[0], "01",
                                            "CH", nct.getFiliale(), nct.getTill());
                                    db8.closeDB();
                                    // DIMINIUSCO EURO
                                    String codsr = nct.getFiliale() + generaId(47);
                                    sr.setCodtr(nct.getCod());
                                    sr.setCodice(codsr);
                                    double newtot = fd(totaledausare);
                                    sr.setTotal(roundDoubleandFormat(-newtot, 2));
                                    sr.setSpread("1.0000");
                                    sr.setData(dateoper);
                                    sr.setQuantity("0");
                                    sr.setUser(user);
                                    Db_Master db9 = new Db_Master();
                                    db9.insert_Stockreport(sr);
                                    db9.closeDB();

                                    // euro stock meno
                                    Db_Master dbS = new Db_Master();
                                    ArrayList<Stock> al = dbS.list_stock(nct.getFiliale(), "01", loc_cur[0]);
                                    ArrayList<String[]> damod = new ArrayList<>();
                                    double quant_now = fd(totaledausare);
                                    double quant_check = quant_now;
                                    for (int i = 0; i < al.size() && quant_check > 0; i++) {
                                        double vq1 = fd(al.get(i).getTotal());
                                        if (vq1 >= quant_check) {
                                            String[] v = { al.get(i).getCodice(), "", "", al.get(i).getCodice(),
                                                    roundDoubleandFormat(quant_check, 2), al.get(i).getRate(), "U",
                                                    roundDoubleandFormat(vq1 - quant_check, 2) };
                                            damod.add(v);
                                            quant_check = 0.0;
                                        } else {
                                            String[] v = { al.get(i).getCodice(), "", "", al.get(i).getCodice(),
                                                    roundDoubleandFormat(quant_check, 2), al.get(i).getRate(), "U",
                                                    "0.00" };// 07/11
                                            damod.add(v);
                                            quant_check = quant_check - vq1;
                                        }
                                    }
                                    dbS.updateStock(damod, false);
                                    dbS.closeDB();
                                }
                                break;
                            case "2": {
                                // Money OUT

                                Real_oc_change to = new Real_oc_change();
                                to.setFiliale(nct.getFiliale());
                                to.setCod_oc(nct.getId_open_till());
                                to.setValuta(loc_cur[0]);
                                to.setKind("01");
                                to.setData(dateoper);
                                to.setNum_kind_op("0");
                                Db_Master db5 = new Db_Master();
                                to.setValue_op(roundDoubleandFormat(
                                        (fd(db5.getField_real_oc_change(to, "value_op", "0.00")) + fd(totaledausare)),
                                        2));
                                db5.update_real_oc_change(to);
                                db5.closeDB();
                                // ADD EURO STOCK
                                Stock st1 = new Stock();
                                st1.setCodice("ST" + generaId(48));
                                st1.setFiliale(nct.getFiliale());
                                st1.setTipo("CH");
                                st1.setTill(nct.getSupporto());
                                st1.setIdoperation(nct.getCod());
                                st1.setCodiceopenclose(nct.getId_open_till());
                                st1.setTipostock("CH");
                                st1.setCod_value(loc_cur[0]);
                                st1.setKind("01");
                                st1.setTotal(totaledausare);
                                st1.setRate("1.00000000");
                                st1.setControval(totaledausare);
                                st1.setUser(user);
                                st1.setDate(dateoper);
                                st1.setId_op(nct.getId_open_till());
                                Db_Master db01 = new Db_Master();
                                db01.insertStock(st1);
                                db01.closeDB();
                                Db_Master db8 = new Db_Master();
                                Stock_report sr = db8.get_Stock_report(nct.getId_open_till(), loc_cur[0], "01", "CH",
                                        nct.getFiliale(), nct.getTill());
                                db8.closeDB();
                                // AUMENTO EURO
                                String codsr = nct.getFiliale() + generaId(47);
                                sr.setCodtr(nct.getCod());
                                sr.setCodice(codsr);
                                sr.setTotal(roundDoubleandFormat(fd(totaledausare), 2));
                                sr.setSpread("1.0000");
                                sr.setData(dateoper);
                                sr.setQuantity("0");
                                sr.setUser(user);
                                Db_Master db9 = new Db_Master();
                                db9.insert_Stockreport(sr);
                                db9.closeDB();
                                break;
                            }
                            case "3": {
                                // Money IN - Stock OUT

                                //// AUMENTARE no stock
                                Real_oc_nochange from = new Real_oc_nochange();
                                from.setFiliale(nct.getFiliale());
                                from.setCod_oc(nct.getId_open_till());
                                from.setGruppo_nc(nct.getGruppo_nc());
                                Db_Master db6 = new Db_Master();
                                from.setQuantity(valueOf(parseIntR(db6.getQuantity_real_oc_nochange(from))
                                        + parseIntR(nct.getQuantita())));
                                from.setData(dateoper);
                                db6.update_real_oc_nochange(from);
                                db6.closeDB();
                                // AUMENTARE
                                Db_Master db8 = new Db_Master();
                                Stock_report sr = db8.get_Stock_report(nct.getId_open_till(), nct.getGruppo_nc(), "01",
                                        "NC", nct.getFiliale(), nct.getTill());
                                db8.closeDB();
                                String codsr = nct.getFiliale() + generaId(47);
                                sr.setCodtr(nct.getCod());
                                sr.setCodice(codsr);
                                sr.setTotal("0.00");
                                sr.setSpread("1.0000");
                                sr.setData(dateoper);
                                sr.setQuantity("" + parseIntR(nct.getQuantita()));
                                sr.setUser(user);
                                Db_Master db9 = new Db_Master();
                                db9.insert_Stockreport(sr);
                                db9.closeDB();
                                if (nct.getSupporto().equals("06") || nct.getSupporto().equals("07")
                                        || nct.getSupporto().equals("08")) {
                                    // creditcard
                                    Real_oc_pos rop = new Real_oc_pos();
                                    rop.setFiliale(nct.getFiliale());
                                    rop.setCod_oc(nct.getId_open_till());
                                    rop.setValuta(loc_cur[0]);
                                    rop.setKind(nct.getSupporto());
                                    rop.setData(dateoper);
                                    rop.setCarta_credito(nct.getPos());
                                    Db_Master db5 = new Db_Master();
                                    rop.setIp_quantity(
                                            valueOf(parseIntR(db5.getField_real_oc_pos(rop, "ip_quantity", "0")) - 1));
                                    rop.setIp_value(roundDoubleandFormat(
                                            (fd(db5.getField_real_oc_pos(rop, "ip_value", "0.00")) - fd(totaledausare)),
                                            2));
                                    db5.update_real_oc_pos(rop);
                                    db5.closeDB();
                                } else {
                                    Real_oc_change to = new Real_oc_change();
                                    to.setFiliale(nct.getFiliale());
                                    to.setCod_oc(nct.getId_open_till());
                                    to.setValuta(loc_cur[0]);
                                    to.setKind(nct.getSupporto());
                                    to.setData(dateoper);
                                    to.setNum_kind_op("0");
                                    Db_Master db5 = new Db_Master();
                                    to.setValue_op(roundDoubleandFormat(
                                            (fd(db5.getField_real_oc_change(to, "value_op", "0.00"))
                                                    - fd(totaledausare)),
                                            2));
                                    db5.update_real_oc_change(to);
                                    db5.closeDB();
                                    Db_Master db8A = new Db_Master();
                                    Stock_report sr1 = db8A.get_Stock_report(nct.getId_open_till(), loc_cur[0], "01",
                                            "CH", nct.getFiliale(), nct.getTill());
                                    db8A.closeDB();
                                    // DIM EURO
                                    String codsrA = nct.getFiliale() + generaId(47);
                                    sr1.setCodtr(nct.getCod());
                                    sr1.setCodice(codsrA);
                                    double newtot = -fd(totaledausare);
                                    sr1.setTotal(roundDoubleandFormat(newtot, 2));
                                    sr1.setSpread("1.0000");
                                    sr1.setData(dateoper);
                                    sr1.setQuantity("0");
                                    sr1.setUser(user);
                                    Db_Master db9A = new Db_Master();
                                    db9A.insert_Stockreport(sr1);
                                    db9A.closeDB();
                                    // euro stock meno
                                    Db_Master dbS = new Db_Master();
                                    ArrayList<Stock> al = dbS.list_stock(nct.getFiliale(), "01", loc_cur[0]);
                                    ArrayList<String[]> damod = new ArrayList<>();
                                    double quant_now = fd(totaledausare);
                                    double quant_check = quant_now;
                                    for (int i = 0; i < al.size() && quant_check > 0; i++) {
                                        double vq1 = fd(al.get(i).getTotal());
                                        if (vq1 >= quant_check) {
                                            String[] v = { al.get(i).getCodice(), "", "", al.get(i).getCodice(),
                                                    roundDoubleandFormat(quant_check, 2), al.get(i).getRate(), "U",
                                                    roundDoubleandFormat(vq1 - quant_check, 2) };
                                            damod.add(v);
                                            quant_check = 0.0;
                                        } else {
                                            String[] v = { al.get(i).getCodice(), "", "", al.get(i).getCodice(),
                                                    roundDoubleandFormat(quant_check, 2), al.get(i).getRate(), "U",
                                                    "0.00" };// 07/11
                                            damod.add(v);
                                            quant_check = quant_check - vq1;
                                        }
                                    }
                                    dbS.updateStock(damod, false);
                                    dbS.closeDB();
                                }
                                break;
                            }
                            case "4": {
                                // Money OUT - Stock IN

                                if (!tdest.isSafe()) {

                                    Real_oc_change to = new Real_oc_change();
                                    to.setFiliale(nct.getFiliale());
                                    to.setCod_oc(nct.getId_open_till());
                                    to.setValuta(loc_cur[0]);
                                    to.setKind("01");
                                    to.setData(dateoper);
                                    to.setNum_kind_op("0");
                                    Db_Master db5 = new Db_Master();
                                    to.setValue_op(roundDoubleandFormat(
                                            (fd(db5.getField_real_oc_change(to, "value_op", "0.00"))
                                                    + fd(totaledausare)),
                                            2));
                                    db5.update_real_oc_change(to);
                                    db5.closeDB();

                                    Db_Master db8A = new Db_Master();
                                    Stock_report sr1 = db8A.get_Stock_report(nct.getId_open_till(), loc_cur[0], "01",
                                            "CH", nct.getFiliale(), nct.getTill());
                                    db8A.closeDB();

                                    // AUM EURO
                                    String codsrA = nct.getFiliale() + generaId(47);
                                    sr1.setCodtr(nct.getCod());
                                    sr1.setCodice(codsrA);
                                    double newtot = fd(totaledausare);
                                    sr1.setTotal(roundDoubleandFormat(newtot, 2));
                                    sr1.setSpread("1.0000");
                                    sr1.setData(dateoper);
                                    sr1.setQuantity("0");
                                    sr1.setUser(user);
                                    Db_Master db9A = new Db_Master();
                                    db9A.insert_Stockreport(sr1);
                                    db9A.closeDB();

                                    // ADD EURO STOCK
                                    Stock st1 = new Stock();
                                    st1.setCodice("ST" + generaId(48));
                                    st1.setFiliale(nct.getFiliale());
                                    st1.setTipo("CH");
                                    st1.setTill(nct.getTill());
                                    st1.setIdoperation(nct.getCod());
                                    st1.setCodiceopenclose(nct.getId_open_till());
                                    st1.setTipostock("CH");
                                    st1.setCod_value(loc_cur[0]);
                                    st1.setKind("01");
                                    st1.setTotal(totaledausare);
                                    st1.setRate("1.00000000");
                                    st1.setControval(totaledausare);
                                    st1.setUser(user);
                                    st1.setDate(dateoper);
                                    st1.setId_op(nct.getId_open_till());
                                    Db_Master db7 = new Db_Master();
                                    db7.insertStock(st1);
                                    db7.closeDB();

                                } // diminuire no stock
                                Real_oc_nochange from = new Real_oc_nochange();
                                from.setFiliale(filiale);
                                from.setCod_oc(nct.getId_open_till());
                                from.setGruppo_nc(nct.getGruppo_nc());
                                Db_Master db8a = new Db_Master();
                                from.setQuantity(valueOf(parseIntR(db8a.getQuantity_real_oc_nochange(from))
                                        - parseIntR(nct.getQuantita())));
                                from.setData(dateoper);
                                db8a.update_real_oc_nochange(from);
                                db8a.closeDB();
                                // AUMENTARE
                                Db_Master db8 = new Db_Master();
                                Stock_report sr = db8.get_Stock_report(nct.getId_open_till(), nct.getGruppo_nc(), "01",
                                        "NC", nct.getFiliale(), nct.getTill());
                                db8.closeDB();
                                String codsr = nct.getFiliale() + generaId(47);
                                sr.setCodtr(nct.getCod());
                                sr.setCodice(codsr);
                                sr.setTotal("0.00");
                                sr.setSpread("1.0000");
                                sr.setData(dateoper);
                                sr.setQuantity(valueOf(-parseIntR(nct.getQuantita())));
                                sr.setUser(user);
                                Db_Master db9 = new Db_Master();
                                db9.insert_Stockreport(sr);
                                db9.closeDB();
                                break;
                            }
                            default:
                                break;
                        }
                    }

                } else {
                    ok = "Q";
                }

                if (!es) {
                    ok = "1";
                }
            } else {
                ok = "CC";
            }
        }
        return ok;
    }

    /**
     *
     * @param codtr
     * @return
     */
    public static String getLink_last(String codtr) {
        Db_Master db8 = new Db_Master();
        String sr = db8.getLink_last(codtr);
        db8.closeDB();
        return sr;
    }

    /**
     *
     * @param cod
     * @return
     */
    public static NC_vatcode get_NC_vatcode(String cod) {
        Db_Master db8 = new Db_Master();
        NC_vatcode sr = db8.get_NC_vatcode(cod);
        db8.closeDB();
        return sr;
    }

    public static String get_ValueSettings(String id) {
        Db_Crm db8 = new Db_Crm();
        String sr = db8.get_ValueSettings(id);
        db8.closeDB();
        return sr;
    }

    public static String checkDeleteTR(String codtr) {
        String ok = "OK";
        try {
            Db_Master db6 = new Db_Master();
            Ch_transaction tmp = db6.query_transaction_ch(codtr);
            if (tmp == null) {
                tmp = db6.query_transaction_ch_temp(codtr);
            }
            String valutalocale = db6.get_local_currency()[0];
            db6.closeDB();
            if (tmp == null || tmp.getDel_fg().equals("1")) {
                ok = "ERROR: TRANSACTION ALREADY DELETED.";
            } else {
                switch (tmp.getTipotr()) {
                    case "B":
                        Db_Master db2 = new Db_Master();
                        ArrayList<Ch_transaction_value> val = db2.query_transaction_value(codtr);
                        db2.closeDB();
                        for (int x = 0; x < val.size(); x++) {
                            if (val.get(x).getSupporto().equals("04")) {
                                Real_oc_pos t1 = new Real_oc_pos();
                                t1.setFiliale(tmp.getFiliale());
                                t1.setCod_oc(tmp.getId_open_till());
                                t1.setValuta(val.get(x).getValuta());
                                t1.setKind(val.get(x).getSupporto());
                                t1.setCarta_credito(val.get(x).getPos());
                                Db_Master db3 = new Db_Master();
                                int numero = parseIntR(db3.getField_real_oc_pos(t1, "ip_quantity", "0"));
                                double incassa = fd(db3.getField_real_oc_pos(t1, "ip_value", "0.00"));
                                db3.closeDB();
                                if (numero < 1 || incassa < fd(val.get(x).getQuantita())) {
                                    ok = "ERROR: Quantity of figures exceeds the amount available in this till.";
                                    break;
                                }
                            } else {
                                Real_oc_change to = new Real_oc_change();
                                to.setFiliale(tmp.getFiliale());
                                to.setCod_oc(tmp.getId_open_till());
                                to.setValuta(val.get(x).getValuta());
                                to.setKind(val.get(x).getSupporto());
                                to.setData(tmp.getData());
                                to.setNum_kind_op("0");
                                Db_Master db3 = new Db_Master();
                                double incassa = fd(db3.getField_real_oc_change(to, "value_op", "0.00"));
                                db3.closeDB();
                                if (incassa < fd(val.get(x).getQuantita())) {
                                    ok = "ERROR: Quantity of figures exceeds the amount available in this till.";
                                    break;
                                }
                            }
                        }
                        break;
                    case "S":
                        if (tmp.getLocalfigures().equals("01")) {
                            Real_oc_change to = new Real_oc_change();
                            to.setFiliale(tmp.getFiliale());
                            to.setCod_oc(tmp.getId_open_till());
                            to.setValuta(valutalocale);
                            to.setKind("01");
                            to.setData(tmp.getData());
                            to.setNum_kind_op("0");
                            Db_Master db3 = new Db_Master();
                            double incassa = fd(db3.getField_real_oc_change(to, "value_op", "0.00"));
                            db3.closeDB();
                            if (incassa < fd(tmp.getPay())) {
                                ok = "ERROR: Quantity of figures exceeds the amount available in this till.";
                            }
                        }
                        break;
                    default:
                        ok = "ERROR: TRANSACTION ERROR.";
                        break;
                }
            }
        } catch (Exception e) {
            ok = "ERROR: TRANSACTION ERROR.";
        }
        return ok;
    }

    public static List<String> lista_anni_SP() {
        List<String> out = new ArrayList<>();
        out.add("2021");
        out.add("2022");
        out.add("2023");
        out.add("2024");
        out.add("2025");
        return out;
    }

}
