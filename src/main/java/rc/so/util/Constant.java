package rc.so.util;

import java.net.FileNameMap;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;
import static java.util.ResourceBundle.getBundle;

/**
 *
 * @author rcosco
 */
public class Constant {

    public static final FileNameMap fileNameMap = URLConnection.getFileNameMap();

    public static final String eithcettaOS = "Tangerine";

    ////////////////////////////////////////////
    //CZ - MODIFICA EET
    public static final boolean czeet = false;
    ////////////////////////////////////////////
    //SPREAD
    public static final boolean newpread = true;
    ////////////////////////////////////////////
    //PAD
    public static final boolean newpad = false;
    public static final boolean aggiungifirma = true;

    ////////////////////////////////////////////////////////
    //UK - MODIFICA
    public static final boolean newuk = false;
    //  PAYMAT NASCONDI
    public static final boolean mostrapaymat = false;
    //EVOLUTIVE TREXLER//
    public static final boolean tr_1302 = false;
    public static final boolean tr_1303 = false;
    public static final boolean tr_1309 = false;
    public static final boolean tr_1310 = false;
    public static final boolean tr_1325 = false;
    public static final boolean tr_1331 = false;
    ////////////////////////////////////////////    

    /**
     *
     */
    public static final ResourceBundle rb = getBundle("conf.conf");
    /////////////////////

    /**
     *
     */
    public static final String patternhours_d = "HH:mm:ss";

    /**
     *
     */
    public static final String patternhours_d1 = "HH:mm";

    /**
     *
     */
    public static final String patterndir = "yyyyMMdd";

    /**
     *
     */
    public static final String patternyear = "yyyy";

    /**
     *
     */
    public static final String patternoam = "ddMMyyyy";

    /**
     *
     */
    public static final String patternnormdate_filter = "dd/MM/yyyy";

    /**
     *
     */
    public static final String pattermonthnorm = "MM/yyyy";

    /**
     *
     */
    public static final String patternnormdate_f = "dd/MM/yyyy HH:mm";

    /**
     *
     */
    public static final String patternnormdate = "dd/MM/yyyy HH:mm:ss";

    /**
     *
     */
    public static final String patternsqldate = "yyyy-MM-dd HH:mm:ss";

    /**
     *
     */
    public static final String patterneet = "yyyy-MM-dd'T'HH:mm:ssZ";

    /**
     *
     */
    public static final String patternsql = "yyyy-MM-dd";

    /**
     *
     */
    public static final String patternmonthsql = "yyyy-MM";

    /**
     *
     */
    public static final String patternsql_f = "yyyy-MM-dd HH:mm";

    /**
     *
     */
    public static final String patterntsdate = "yyyyMMddHHmmssSSS";

    /**
     *
     */
    public static final String patterntsdate2 = "yyMMddHHmmssSSS";

    /**
     *
     */
    public static final String patterntsdatecora = "yyyyMMddHHmmss";

    /**
     *
     */
    public static final String errorlogin = "/login.jsp?esito=";

    /**
     *
     */
    public static final String extsign = "_mac2";

    /**
     *
     */
    public static final String piva = rb.getString("piva");
    //

    /**
     *
     */
    public static final String nation = rb.getString("nation");
    //

    /**
     *
     */
    public static final boolean is_IT = nation.equals("IT");

    /**
     *
     */
    public static final boolean is_CZ = nation.equals("CZ");

    /**
     *
     */
    public static final boolean is_UK = nation.equals("GB");
    //

    /**
     *
     */
    public static final String version = rb.getString("version");

    /**
     *
     */
    public static final String codnaz = rb.getString("codnaz");

    /**
     *
     */
    public static final String localcurrency = rb.getString("localcurrency");

    /**
     *
     */
    public static final String thousand = rb.getString("thousand");

    /**
     *
     */
    public static final String decimal = rb.getString("decimal");

    /**
     *
     */
    public static final String iconcur = rb.getString("iconcur");

    /**
     *
     */
    public static final String ipcentral = rb.getString("ipcentral");

    /**
     *
     */
    public static final boolean test = rb.getString("test").equals("SI");

    /**
     *
     */
    public static final boolean signoffline = rb.getString("signoffline").equals("SI");

    /**
     *
     */
    public static final String tillopen = "<span class='label lable-sm label-success'>Open</span>";

    /**
     *
     */
    public static final String tillclose = "<span class='label lable-sm label-danger'>Closed</span>";

    /**
     *
     */
    public static final int rateround = 8;

    /**
     *
     */
    public static final int BUFFER_SIZE = 4096;

    /**
     *
     */
    public static final String bl_piat = "MACCORP";

    /**
     *
     */
    public static final String pdfext = ".pdf";

    /**
     *
     */
    public static final String xmlext = ".xml";

    /**
     *
     */
    public static final String equals = "=";

    /**
     *
     */
    public static final String point = ".";

    /**
     *
     */
    public static final String initialvalue = "-";

    /**
     *
     */
    public static final String zero = "0";

    /**
     *
     */
    public static final String one = "1";

    /**
     *
     */
    public static final String two = "2";

    /**
     *
     */
    public static final String comma = ",";

    /**
     *
     */
    public static final String zerodouble = "0.00";

    /**
     *
     */
    public static final String value_blank = "";

    /**
     *
     */
    public static final String zerodatesql = "1901-01-01 00:00:00";

    /**
     *
     */
    public static final String central_branch = "000";

    /**
     *
     */
    public static final String formatdataCell = "#,#.00";

    /**
     *
     */
    public static final String decimalformat = "#0.00";

    /**
     *
     */
    public static final String formatdataCellRate = "#,#.00000000";

    /**
     *
     */
    public static final String formatdataCellint = "#,#";

    /**
     *
     */
    public static final String charset = "UTF-8";

    public static final SimpleDateFormat df_ita = new SimpleDateFormat(patternnormdate);
}
