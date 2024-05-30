package rc.so.util;

import static com.google.common.base.Splitter.on;
import static com.google.common.base.Splitter.onPattern;
import com.google.common.io.Files;
import static com.google.common.io.Files.asCharSource;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.itextpdf.text.Element;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.tool.xml.ElementList;
import static com.itextpdf.tool.xml.XMLWorkerHelper.parseToElementList;
import rc.so.db.Db_Master;
import rc.so.entity.Booking;
import rc.so.entity.Ch_transaction;
import rc.so.entity.Currency;
import rc.so.entity.MailObject;
import rc.so.entity.NC_category;
import rc.so.entity.NC_transaction;
import rc.so.entity.NC_vatcode;
import rc.so.entity.Stock;
import static rc.so.pdf.Pdf.printdeleted;
import static rc.so.util.Constant.charset;
import static rc.so.util.Constant.decimal;
import static rc.so.util.Constant.decimalformat;
import static rc.so.util.Constant.is_CZ;
import static rc.so.util.Constant.is_IT;
import static rc.so.util.Constant.is_UK;
import static rc.so.util.Constant.patternnormdate_f;
import static rc.so.util.Constant.patternnormdate_filter;
import static rc.so.util.Constant.patternsqldate;
import static rc.so.util.Constant.patterntsdate2;
import static rc.so.util.Constant.rateround;
//import static rc.so.util.Constant.rb;
import static rc.so.util.Constant.test;
import static rc.so.util.Constant.thousand;
import static rc.so.util.Engine.getNC_category;
import static rc.so.util.Engine.get_BCE;
import static rc.so.util.Engine.get_NC_transaction;
import static rc.so.util.Engine.get_NC_vatcode;
import static rc.so.util.Engine.get_ValueSettings;
import static rc.so.util.Engine.insertTR;
import static rc.so.util.Engine.isCentral;
import static rc.so.util.HtmlEncoder.escapeNonLatin;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import static java.lang.Boolean.valueOf;
import static java.lang.Double.parseDouble;
import static java.lang.Float.parseFloat;
import static java.lang.Integer.parseInt;
import static java.lang.Math.ceil;
import static java.lang.Math.round;
import static java.lang.String.format;
import static java.lang.String.valueOf;
import static java.lang.System.getProperty;
import static java.lang.System.out;
import static java.lang.Thread.currentThread;
import static java.lang.Thread.sleep;
import static java.lang.management.ManagementFactory.getPlatformMBeanServer;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import static java.math.BigDecimal.ROUND_HALF_DOWN;
import static java.math.RoundingMode.HALF_UP;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import static java.net.InetAddress.getByName;
import static java.net.InetAddress.getLocalHost;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.nio.charset.StandardCharsets.ISO_8859_1;
import static java.nio.charset.StandardCharsets.UTF_8;
import java.security.MessageDigest;
import static java.security.MessageDigest.getInstance;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.ArrayList;
import static java.util.Arrays.asList;
import static java.util.Collections.sort;
import static java.util.Comparator.naturalOrder;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import static java.util.Locale.ITALY;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.StringTokenizer;
import static java.util.regex.Pattern.matches;
import java.util.stream.Collectors;
import javax.activation.DataHandler;
import javax.mail.BodyPart;
import javax.mail.Message;
import static javax.mail.Message.RecipientType.TO;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import static javax.mail.Session.getDefaultInstance;
import static javax.mail.Transport.send;
import javax.mail.internet.InternetAddress;
import static javax.mail.internet.InternetAddress.parse;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import static javax.management.Query.attr;
import static javax.management.Query.match;
import static javax.management.Query.value;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import static javax.xml.parsers.DocumentBuilderFactory.newInstance;
import static org.apache.commons.codec.binary.Base64.decodeBase64;
import static org.apache.commons.codec.binary.Base64.encodeBase64;
import static org.apache.commons.codec.binary.Base64.encodeBase64String;
import static org.apache.commons.codec.digest.DigestUtils.md5Hex;
import org.apache.commons.compress.archivers.ArchiveOutputStream;
import org.apache.commons.compress.archivers.ArchiveStreamFactory;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import static org.apache.commons.fileupload.servlet.ServletFileUpload.isMultipartContent;
import org.apache.commons.io.FileUtils;
import static org.apache.commons.io.FileUtils.readFileToByteArray;
import static org.apache.commons.io.FileUtils.writeByteArrayToFile;
import static org.apache.commons.io.IOUtils.copy;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.StringEscapeUtils;

import static org.apache.commons.lang3.StringUtils.capitalize;
import static org.apache.commons.lang3.StringUtils.leftPad;
import static org.apache.commons.lang3.StringUtils.replace;
import static org.apache.commons.lang3.StringUtils.right;
import static org.apache.commons.lang3.StringUtils.stripAccents;
import static org.apache.commons.lang3.StringUtils.substring;
import static org.apache.commons.lang3.exception.ExceptionUtils.getStackTrace;
import static org.apache.commons.validator.routines.EmailValidator.getInstance;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import static org.apache.poi.ss.usermodel.CellType.NUMERIC;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.joda.time.DateTime;
import static org.joda.time.format.DateTimeFormat.forPattern;
import org.joda.time.format.DateTimeFormatter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import static org.json.JSONObject.NULL;
import static org.jsoup.Jsoup.parseBodyFragment;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import rc.so.entity.Ch_transaction_doc;
import static rc.so.util.Constant.fileNameMap;
import static rc.so.util.Constant.rb;

/**
 *
 * @author rcosco
 */
public class Utility {

    /**
     *
     * @param psw
     * @return
     */
    public static String convMd5(String psw) {
        try {
            MessageDigest md = getInstance("MD5");
            md.update(psw.getBytes());
            byte[] byteData = md.digest();
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < byteData.length; i++) {
                sb.append(Integer.toString((byteData[i] & 0xFF) + 256, 16).substring(1));
            }
            return sb.toString().trim();
        } catch (NoSuchAlgorithmException ex) {
            insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
        }
        return "-";
    }

    /**
     *
     * @return
     */
    public static String generaId() {
        String random = randomAlphanumeric(5).trim();
        return new DateTime().toString(patterntsdate2) + random;
    }

    /**
     *
     * @param length
     * @return
     */
    public static String generaId(int length) {
        String random = randomAlphanumeric(length - 15).trim();
        return new DateTime().toString(patterntsdate2) + random;
    }

    /**
     *
     * @param val
     * @return
     */
    public static boolean isNumeric(String val) {
        return StringUtils.isNumeric(val);
    }

    /**
     *
     * @param cf
     * @return
     */
    public static String calcolaSesso(String cf) {
        if (cf.length() == 16) {
            String day = cf.substring(9, 11);
            if (isNumeric(day)) {
                int d1;
                try {
                    d1 = parseInt(day);
                } catch (NumberFormatException ex) {
                    d1 = 35;
                    insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
                }
                if (d1 < 32) {
                    return "M";
                }
                if (d1 > 40) {
                    return "F";
                }
            }
        }
        return "KO";
    }

    /**
     *
     * @param request
     * @return
     */
    public static boolean detectMobile(HttpServletRequest request) {
        Enumeration headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = (String) headerNames.nextElement();
            String headerValue = request.getHeader(headerName);
            if ((headerName.equals("user-agent")) && (headerValue.toLowerCase().contains("android"))) {
                return true;
            }
        }

        return false;
    }

    /**
     *
     * @param pdffile
     * @return
     */
    public static boolean checkPDF(File pdffile) {
        if (pdffile.exists()) {
            try {
                int pag;
                try (InputStream is = new FileInputStream(pdffile)) {
                    PdfReader pdfReader = new PdfReader(is);
                    pag = pdfReader.getNumberOfPages();
                    pdfReader.close();
                }
                return pag > 0;
            } catch (IOException ex) {
            }
        }
        return false;
    }

    public static DateTime parseStringDate(String dat, String pattern1) {
        try {
            if (dat.length() == 21) {
                dat = dat.substring(0, 19);
            }
            if (dat.length() == pattern1.length()) {
                DateTimeFormatter formatter = forPattern(pattern1);
                return formatter.parseDateTime(dat);
            }
        } catch (IllegalArgumentException ex) {
            insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
        }
        return null;

    }

    /**
     *
     * @param dat
     * @param pattern1
     * @param pattern2
     * @return
     */
    public static String formatStringtoStringDate(String dat, String pattern1, String pattern2) {
        try {
            if (dat.length() == 21) {
                dat = dat.substring(0, 19);
            }
            if (dat.length() == pattern1.length()) {
                DateTimeFormatter formatter = forPattern(pattern1);
                DateTime dt = formatter.parseDateTime(dat);
                return dt.toString(pattern2, ITALY);
            }
        } catch (IllegalArgumentException ex) {
            insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
        }
        return dat;
    }

    /**
     *
     * @param dat
     * @param pattern1
     * @param pattern2
     * @return
     */
    public static String formatStringtoStringDate_null(String dat, String pattern1, String pattern2) {
        try {
            if (dat.length() == 21) {
                dat = dat.substring(0, 19);
            }
            if (dat.length() == pattern1.length()) {
                DateTimeFormatter formatter = forPattern(pattern1);
                DateTime dt = formatter.parseDateTime(dat);
                return dt.toString(pattern2, ITALY);
            }
        } catch (IllegalArgumentException le) {
        }
        return null;
    }

    /**
     *
     * @param from
     * @param to
     * @param pattern
     * @return
     */
    public static ArrayList<String> periodic(String from, String to, String pattern) {
        try {
            ArrayList<String> out = new ArrayList<>();
            DateTimeFormatter formatter = forPattern(pattern);
            DateTime dtFR = formatter.parseDateTime(from);
            DateTime dtTO = formatter.parseDateTime(to);
            while (dtFR.isBefore(dtTO)) {
                out.add(dtFR.toString(pattern));
                dtFR = dtFR.plusDays(1);
            }
            out.add(dtTO.toString(pattern));
            return out;
        } catch (Exception ex) {
        }
        return null;
    }

    /**
     *
     * @param l
     * @param loc
     */
    public static void removeDuplicatesAL_localcurr(ArrayList<String> l, String loc) {
        Iterator p = l.iterator();
        while (p.hasNext()) {
            Object op = p.next();
            Iterator q = l.iterator();
            Object oq = q.next();
            while (op != oq) {
                oq = q.next();
            }
            boolean b = q.hasNext();
            while (b) {
                oq = q.next();
                if (op.equals(oq)) {
                    p.remove();
                    b = false;
                } else {
                    b = q.hasNext();
                }
            }
        }

        sort(l);

        if (l.contains(loc)) {
            l.remove(loc);
            l.add(0, loc);
        }

    }

    /**
     *
     * @param l
     */
    public static void removeDuplicatesAL(ArrayList<String> l) {
//        int sizeInit = l.size();
        ArrayList<String> l2 = (ArrayList<String>) l.stream().sorted(naturalOrder()).distinct().collect(Collectors.toList());
        l.clear();
        l.addAll(l2);
//        return sizeInit != l2.size();
//        
//        
//
//        Iterator p = l.iterator();
//        while (p.hasNext()) {
//            Object op = p.next();
//            Iterator q = l.iterator();
//            Object oq = q.next();
//            while (op != oq) {
//                oq = q.next();
//            }
//            boolean b = q.hasNext();
//            while (b) {
//                oq = q.next();
//                if (op.equals(oq)) {
//                    p.remove();
//                    b = false;
//                } else {
//                    b = q.hasNext();
//                }
//            }
//        }
//
//        Collections.sort(l);
//
//        return sizeInit != l.size();
    }

    /**
     *
     * @param l
     * @return
     */
    public static boolean removeDuplicatesALAr(ArrayList<String[]> l) {
        int sizeInit = l.size();
        Iterator<String[]> p = l.iterator();
        while (p.hasNext()) {
            String[] op = p.next();
            Iterator<String[]> q = l.iterator();
            String[] oq = q.next();
            while (!op[0].equals(oq[0]) || !op[1].equals(oq[1])) {
                oq = q.next();
            }
            boolean b = q.hasNext();
            while (b) {
                oq = q.next();
                if (op[0].equals(oq[0]) && op[1].equals(oq[1])) {
                    p.remove();
                    b = false;
                } else {
                    b = q.hasNext();
                }
            }
        }
        return sizeInit != l.size();
    }

    /**
     *
     * @param l
     * @return
     */
    public static boolean removeDuplicatesALAr_3(ArrayList<String[]> l) {
        int sizeInit = l.size();
        Iterator<String[]> p = l.iterator();
        while (p.hasNext()) {
            String[] op = p.next();
            Iterator<String[]> q = l.iterator();
            String[] oq = q.next();
            while (!op[0].equals(oq[0]) || !op[1].equals(oq[1]) || !op[2].equals(oq[2])) {
                oq = q.next();
            }
            boolean b = q.hasNext();
            while (b) {
                oq = q.next();
                if (op[0].equals(oq[0]) && op[1].equals(oq[1]) && op[2].equals(oq[2])) {
                    p.remove();
                    b = false;
                } else {
                    b = q.hasNext();
                }
            }
        }
        return sizeInit != l.size();
    }

    /**
     *
     * @param al
     * @param cod
     * @return
     */
    public static String formatDesc(ArrayList<String[]> al, String cod) {
        for (int i = 0; i < al.size(); i++) {
            if (((String[]) al.get(i))[0].equals(cod)) {
                return ((String[]) al.get(i))[1];
            }
        }
        return "-";
    }

    /**
     *
     * @param input
     * @return
     */
    public static String correggiValueJS(String input) {
        input = input.trim();
        input = input.replaceAll("\"", "'");
        input = input.replaceAll("\n", "");
        input = input.replaceAll("\r", "");
        return input;
    }

    /**
     *
     * @param d
     * @param scale
     * @return
     */
    public static double roundDouble(double d, int scale) {
        d = new BigDecimal(d).setScale(scale, HALF_UP).doubleValue();
        return d;
    }

    /**
     *
     * @param d
     * @return
     */
    public static String formatDouble(double d) {
        NumberFormat formatter = new DecimalFormat(decimalformat);
        return formatter.format(d);
    }

    /**
     *
     * @param value
     * @param buy
     * @return
     */
    public static double parseDoubleR_CZ(String value, boolean buy) {
        if (!is_CZ) {
            return parseDoubleR(value);
        }

        double d1;
        try {
            d1 = parseDouble(value);
            if (buy) {
                d1 = d1 * (-1.0);
            }
        } catch (NumberFormatException e) {
            value = formatDoubleforMysql(value);
            return parseDoubleR_CZ(value, buy);
        }
        return d1;
    }

    /**
     *
     * @param value
     * @return
     */
    public static double parseDoubleR2(String value) {
        value = StringUtils.replace(value.trim(), "-", "").trim();
        double d1;
        try {
            d1 = parseDouble(StringUtils.deleteWhitespace(value.trim()).trim());
        } catch (Exception e) {
            value = formatDoubleforMysql(value.trim()).trim();
            return parseDouble(StringUtils.deleteWhitespace(value.trim()).trim());
        }
        return d1;
    }

    public static double parseDoubleR(String value) {
        if (containsInfinity(value)) {
            return 0.0D;
        }
        value = StringUtils.deleteWhitespace(value).replaceAll(" ", "").replaceAll("\\s+", "").replaceAll("-", "").trim();
        try {
            double d1 = Double.parseDouble(value);
            return d1;
        } catch (Exception ex) {
//            ex.printStackTrace();
//            log.log(Level.SEVERE, "{0}: {1}", new Object[]{ex.getStackTrace()[0].getMethodName(), ex.getMessage()});
            value = formatDoubleforMysql(value);
            return parseDouble(value);
        }

    }

    public static boolean containsInfinity(String ing) {
        String infinitySymbol;
        try {
            infinitySymbol = new String(String.valueOf(Character.toString('\u221E')).getBytes("UTF-8"), "UTF-8");
        } catch (Exception ex) {
            infinitySymbol = "?";
        }
        return ing.contains(infinitySymbol);
    }

    /**
     *
     * @param value
     * @param sep_dec
     * @return
     */
    public static String formatDouble(double value, String sep_dec) {
        String init = value + "";
        init = init.replaceAll("\\.", sep_dec);
        return init;
    }

    /**
     *
     * @param value
     * @return
     */
    public static String formatRa2(String value) {
        if (value.equals("") || value.equals("-")) {
            return value;
        }
        int l1 = value.length();
        double d1;
        try {
            d1 = parseDouble(value);
        } catch (NumberFormatException e) {
            d1 = 0.0D;
        }
        String out = valueOf(d1).replaceAll("\\.", decimal);
        while (out.length() < l1) {
            out = out + "0";
        }
        return out;
    }

    /**
     *
     * @param d1
     * @return
     */
//    public static String formatRa1(double d1) {
//        DecimalFormat formatter = (DecimalFormat) getCurrencyInstance(ITALY);
//        DecimalFormatSymbols symbols = formatter.getDecimalFormatSymbols();
//        symbols.setCurrencySymbol("");
//        formatter.setDecimalFormatSymbols(symbols);
//        return formatter.format(d1).trim();
//    }
    /**
     *
     * @param cod
     * @param array
     * @return
     */
    public static String formatAL_city(String cod, ArrayList<String[]> array) {
        if (cod == null) {
            return "";
        }
        for (int i = 0; i < array.size(); i++) {
            if (cod.equals(((String[]) array.get(i))[0])) {
                return ((String[]) array.get(i))[1];
            }
        }
        return cod;
    }

    /**
     *
     * @param cod
     * @param array
     * @param index
     * @return
     */
    public static String formatAL(String cod, ArrayList<String[]> array, int index) {
        for (int i = 0; i < array.size(); i++) {
            if (cod.equals(((String[]) array.get(i))[0])) {
                return ((String[]) array.get(i))[index];
            }
        }
        return "-";
    }

    /**
     *
     * @param cod
     * @param array
     * @param index
     * @return
     */
    public static String formatALN(String cod, ArrayList<String[]> array, int index) {
        for (int i = 0; i < array.size(); i++) {
            if (cod.equals(((String[]) array.get(i))[0])) {
                return ((String[]) array.get(i))[index];
            }
        }
        return cod;
    }

    /**
     *
     * @param value
     * @return
     */
    public static String formatDouble_new(double value) {
        return format("%." + rateround + "f", new Object[]{value}).replaceAll("\\.", decimal);
    }

    /**
     *
     * @param value
     * @param pos
     * @return
     */
    public static String formatDouble_new(double value, int pos) {
        return format("%." + pos + "f", new Object[]{value}).replaceAll("\\.", decimal);
    }

    /**
     *
     * @param value
     * @param pos
     * @return
     */
    public static String formatDouble_newRa(double value, int pos) {
        String out = format("%." + pos + "f", value);
        return out;
    }

    /**
     *
     * @param fil
     * @return
     */
    public static String generaIdMAC(String fil) {
        String random = randomAlphanumeric(7).trim();
        return fil + new DateTime().toString(patterntsdate2) + random;
    }

    /**
     *
     * @param value
     * @param length
     * @param padder
     * @return
     */
    public static String fillLeftInt(int value, int length, String padder) {
        return leftPad(valueOf(value), length, padder);
    }

    /**
     *
     * @param value
     * @return
     */
    public static String formatDoubleforMysql(String value) {
        if (value == null) {
            return "0.00";
        }
        if (value.equals("-") || value.equals("")) {
            return "0.00";
        }
        String add = "";
        if (value.contains("-")) {
            add = "-";
            value = StringUtils.replace(value.trim(), "-", "").trim();
        }

        if (!value.equals("0.00")) {
            if (thousand.equals(".")) {
                if (value.contains(decimal)) {
                    if (decimal.equals(",")) {
                        value = StringUtils.replace(value, ".", "");
                        value = StringUtils.replace(value, ",", ".");
                    }
                } else {
                    if (is_CZ) {
                        if (value.contains(".")) {
                            List<String> ex = on(".").splitToList(value);
                            if (ex.size() == 2) {
                                String int1 = ex.get(0);
                                String dec1 = ex.get(1);
                                switch (dec1.length()) {
                                    case 2:
                                        return add + value;
                                    case 1:
                                        return int1 + "." + dec1 + "0";
                                    default:
                                        value = StringUtils.replace(value, ".", "");
                                        return add + value + ".00";
                                }
                            } else {
                                value = StringUtils.replace(value, ".", "");
                                return add + value + ".00";
                            }
                        }
                        return add + value;
                    } else {
                        value = StringUtils.replace(value, ".", "");
                        return add + value + ".00";
                    }
                }
            } else if (thousand.equals(",")) {
                if (value.contains(decimal)) {
                    if (decimal.equals(".")) {
                        value = value.replaceAll(",", "");
                    }
                } else {
                    value = value.replaceAll(",", "");
                    if (is_IT) {
                        return value + "00";
                    } else {
                        return value + ".00";
                    }
                }
            }
        }

        return add + value;

    }

    /**
     *
     * @param obj
     * @return
     */
    public static boolean checkIfnull(Object obj) {
        Class<?> c = obj.getClass();
        Field[] fields = c.getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                if (field.get(obj) == null) {
                    return false;
                }
            } catch (IllegalArgumentException | IllegalAccessException ex) {
                insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
            }
        }
        return true;
    }

    /**
     *
     * @param ing
     * @return
     */
    public static String formatUTFtoLatin(String ing) {
        try {
            StringBuilder sb = (StringBuilder) escapeNonLatin(ing, new StringBuilder());
            return sb.toString();
        } catch (IOException ex) {
        }
        return ing;
    }

    /**
     *
     * @param nome
     * @param cognome
     * @param cod
     * @return
     */
    public static String generateUsername(String nome, String cognome, String cod) {
        nome = stripAccents(nome).replaceAll("[^a-zA-Z0-9]", "").toLowerCase();
        cognome = stripAccents(cognome).replaceAll("[^a-zA-Z0-9]", "").toLowerCase();
        String result;
        if (nome.length() > 1) {
            result = nome.substring(0, 1);
        } else {
            result = randomAlphabetic(1).toLowerCase();
        }
        result = result + cognome + cod;
        return result;
    }

    /**
     *
     * @param ingr
     * @return
     */
    public static String getMd5(String ingr) {
        String md5 = md5Hex(ingr);
        return md5;
    }

    /**
     *
     * @return
     */
    public static String getDateStartNL() {
        DateTime el = new DateTime();
        el = el.plusDays(1);
        String out = el.toString(patternnormdate_filter) + " 00:00";
        return out;
    }

    /**
     *
     * @param file
     * @return
     */
    public static String getStringBase64(File file) {
        byte[] bytes = readBytesFromFileCod(file);
        if (bytes != null) {
            byte[] base64 = encodeBase64(bytes);
            return new String(base64);
        }
        return null;
    }

    /**
     *
     * @param file
     * @return
     */
    public static String getStringBase64_IO(File file) {
        try {
            byte[] bytes = readFileToByteArray(file);
            if (bytes != null) {
                byte[] base64 = encodeBase64(bytes);
                return new String(base64);
            }
        } catch (IOException io) {
        }
        return null;
    }

    private static byte[] readBytesFromFileCod(File file) {
        try {
            byte[] bytes;
            try (InputStream is = new FileInputStream(file)) {
                long length = file.length();
                if (length > 2147483647L) {
                    throw new IOException("Size out: " + file.getName());
                }
                bytes = new byte[(int) length];
                int offset = 0;
                int numRead = 0;
                while ((offset < bytes.length) && ((numRead = is.read(bytes, offset, bytes.length - offset)) >= 0)) {
                    offset += numRead;
                }
                if (offset < bytes.length) {
                    throw new IOException("Error length " + file.getName());
                }
            }
            return bytes;
        } catch (Exception localIOException) {
        }
        return null;
    }

    /**
     *
     * @param xml
     * @return
     */
    public static boolean checkXml(File xml) {
        try {
            DocumentBuilderFactory factory = newInstance();
            factory.setValidating(false);
            factory.setNamespaceAware(true);
            DocumentBuilder builder = factory.newDocumentBuilder();
            builder.setErrorHandler(new SimpleErrorHandler());
            try (InputStream is = new FileInputStream(xml)) {
                InputSource iss = new InputSource(is);
                builder.parse(iss);
            }
            return true;
        } catch (Exception ex) {
        }
        return false;
    }

    /**
     * @param pas
     * @return
     */
    public static boolean checkNumber(String pas) {
        char[] ch = "1234567890".toCharArray();
        for (int i = 0; i < ch.length; i++) {
            if (pas.contains(ch[i] + "")) {
                return true;
            }
        }
        return false;
    }

    /**
     *
     * @param pas
     * @return
     */
    public static boolean checkLetters(String pas) {
        return pas.matches(".*[a-zA-Z]+.*");
    }

    /**
     *
     * @param ing
     * @return
     */
    public static String correggi(String ing) {
        if (ing != null) {
            ing = ing.replaceAll("\n", "");
            ing = ing.replaceAll("\r", "");
            ing = ing.replaceAll("'", "\'");
            ing = ing.replaceAll("“", "\'");
            ing = ing.replaceAll("‘", "\'");
            ing = ing.replaceAll("”", "\'");
            return ing.replaceAll("\"", "\'");
        } else {
            return "-";
        }
    }

    /**
     *
     * @param a
     * @return
     */
    public static String correggiSql(String a) {
        char[] data = new char[a.length() + a.length() + a.length() + a.length()];
        int k = 0;
        String barra = "\\";
        String apo = "'";
        for (int y = 0; y < a.length(); y++) {
            data[k] = a.charAt(y);
            if (barra.equals(a.substring(y, y + 1))) {
                k++;
                data[k] = '\\';
            }
            if (apo.equals(a.substring(y, y + 1))) {
                data[k + 1] = a.charAt(y);
                data[k] = '\\';
                k++;
            }
            k++;
        }
        return valueOf(data, 0, k).trim();
    }

    /**
     *
     * @param request
     * @param fieldname
     * @return
     */
    public static String getRequestValue(HttpServletRequest request, String fieldname) {
        String out = request.getParameter(fieldname);
        if (out == null) {
            out = "";
        } else {
            out = out.trim();
        }
        return out;
    }

    /**
     *
     * @param ing
     * @return
     */
    public static String getStringUTF8(String ing) {
        return new String(ing.getBytes(ISO_8859_1), UTF_8);
    }

    /**
     *
     * @param request
     * @throws ServletException
     * @throws IOException
     */
    public static void printRequest(HttpServletRequest request) throws ServletException, IOException {
        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String paramName = parameterNames.nextElement();
            String[] paramValues = request.getParameterValues(paramName);
            for (String paramValue : paramValues) {
                out.println("NORMAL FIELD - " + paramName + " : " + new String(paramValue.getBytes(ISO_8859_1), UTF_8));
            }
        }
        boolean isMultipart = isMultipartContent(request);
        if (isMultipart) {
            try {
                FileItemFactory factory = new DiskFileItemFactory();
                ServletFileUpload upload = new ServletFileUpload(factory);
                List items = upload.parseRequest(request);
                Iterator iterator = items.iterator();
                while (iterator.hasNext()) {
                    FileItem item = (FileItem) iterator.next();
                    if (item.isFormField()) {
                        String fieldName = item.getFieldName();
                        String value = new String(item.getString().getBytes(ISO_8859_1), UTF_8);
                        out.println("MULTIPART FIELD - " + fieldName + " : " + value);
                    } else {
                        String fieldName = item.getFieldName();
                        String fieldValue = item.getName();
                        out.println("MULTIPART FILE - " + fieldName + " : " + fieldValue);
                    }
                }
            } catch (FileUploadException ex) {
                insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
            }
        }
    }

    /**
     *
     * @param value
     * @return
     */
    public static String formatDoubleToInt(String value) {
        if (value.contains(decimal)) {
            value = value.replaceAll(decimal, "\\.");
        }
        value = new StringTokenizer(value, ".").nextToken();
        return value;
    }

    /**
     *
     * @param value
     * @return
     */
    public static float parseFloat_M(String value) {
        String init = value;
        if (init.contains(decimal)) {
            init = init.replaceAll(decimal, "\\.");
        }
        float d1;
        try {
            d1 = ff(init);
        } catch (NumberFormatException ex) {
            insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
            d1 = 0;
        }
        return d1;

    }

    /**
     *
     * @param value
     * @param pos
     * @return
     */
    public static String formatFloat(float value, int pos) {
        return format("%." + pos + "f", new Object[]{value}).replaceAll("\\.", decimal);
    }

    /**
     *
     * @param mon
     * @param pattern1
     * @param patterncomplete
     * @param pattern2
     * @param add
     * @return
     */
    public static String formatMonthtoDate_null(String mon, String pattern1, String patterncomplete, String pattern2, String add) {
        try {
            if (mon.length() == pattern1.length()) {
                DateTimeFormatter formatter = forPattern(patterncomplete);
                DateTime dt = formatter.parseDateTime(add + mon);
                return dt.toString(pattern2, ITALY);
            }
        } catch (IllegalArgumentException localException) {
        }
        return null;
    }

    /**
     *
     * @param price
     * @return
     */
    public static String priceWithoutDecimal(int price) {
        NumberFormat nf1 = NumberFormat.getInstance();
        return nf1.format(1000);
    }

    /**
     *
     * @param day
     * @return
     */
    public static String[] getlastDayInterval(int day) {
        DateTime tod = new DateTime();
        DateTime el = tod.minusDays(day);
        String[] out = {el.toString(patternnormdate_filter), tod.toString(patternnormdate_filter)};
        return out;
    }

    /**
     *
     * @param si_t_old
     * @return
     */
    public static float ff(String si_t_old) {
        if (si_t_old == null) {
            return 0F;
        }
        float d1;
        si_t_old = si_t_old.replace(",", "").trim();
        try {
            d1 = parseFloat(si_t_old);
        } catch (NumberFormatException e) {
            d1 = 0F;
        }
        return d1;
    }

    /**
     *
     * @param si_t_old
     * @return
     */
    public static double fd(String si_t_old) {
        if (si_t_old == null) {
            return 0.0D;
        }
        double d1;
        si_t_old = si_t_old.replace(",", "").trim();
        try {
            d1 = parseDouble(si_t_old);
        } catch (NumberFormatException e) {
            d1 = 0.0D;
        }
        return d1;
    }

    /**
     *
     * @param s
     * @param interval
     * @return
     */
    public static String[] splitStringEvery(String s, int interval) {
        int arrayLength = (int) Math.ceil((double) s.length() / interval);
        String[] result = new String[arrayLength];
        int lastIndex = result.length - 1;
    
        for (int i = lastIndex; i >= 0; i--) {
            int start = i * interval;
            int end = Math.min(start + interval, s.length());
            result[i] = s.substring(start, end);
        }
    
        return result;
    }
    

    /**
     *
     * @param giorno
     * @return
     */
    public static String getGiornoAdeguatoAnnoPrecedente(String giorno) { //dd/MM/yyyy
        DateTimeFormatter formatter = forPattern(patternnormdate_filter);
        DateTime dtoggi = formatter.parseDateTime(giorno);
        String giornodellasettimanaoggi = dtoggi.dayOfWeek().getAsShortText(ITALY);

        DateTime dtannoscorso = dtoggi.minusYears(1);
        String giornodellasettimanaannoscorso = dtannoscorso.dayOfWeek().getAsShortText(ITALY);
        int add = 1;
        while (!giornodellasettimanaoggi.equals(giornodellasettimanaannoscorso)) {
            dtannoscorso = dtannoscorso.plusDays(add);
            giornodellasettimanaannoscorso = dtannoscorso.dayOfWeek().getAsShortText(ITALY);
        }
        return dtannoscorso.toString(patternnormdate_filter);
    }

    /**
     *
     * @param descr
     * @return
     */
    public static String[] descr_for_report(String descr) {
        Iterable<String> parameters = on(" ").split(descr);
        Iterator<String> it = parameters.iterator();
        String st1 = "";
        if (it.hasNext()) {
            st1 = it.next();
        }
        String st2 = "";
        while (it.hasNext()) {
            st2 = st2 + it.next() + " ";
        }
        st2 = st2.trim();
        String[] d = {st1, st2};
        return d;
    }

    /**
     *
     * @param si_t_old
     * @param length
     * @return
     */
    public static double fd(String si_t_old, int length) {
        double d1;
        si_t_old = replace(si_t_old, ",", "").trim();
        try {
            if (si_t_old.lastIndexOf(".") > 0) {
                if (si_t_old.substring(si_t_old.lastIndexOf(".") + 1).length() > length) {
                    si_t_old = replace(si_t_old, ".", "").trim();
                }
            }
            d1 = parseDouble(si_t_old);
        } catch (NumberFormatException e) {
            d1 = 0.0D;
        }
        return d1;
    }

    /**
     *
     * @param txt
     * @return
     */
    public static String convertApici(String txt) {
        txt = replace(txt, "'", "@");
        return txt;
    }

    /**
     *
     * @param txt
     * @return
     */
    public static String replaceApici(String txt) {
        txt = replace(txt, "@", "'");
        return txt;
    }

    /**
     *
     * @param txt
     * @return
     */
    public static String removeLast(String txt) {
        if (txt == null) {
            return null;
        }
        String last = right(txt.trim(), 1);
        if (last.equals(".") || last.equals(",")) {
            return txt.substring(0, txt.length() - 1);
        }
        return txt;
    }

    /**
     *
     * @param ing
     * @return
     */
    public static String formatMysqltoDisplay(String ing) {
        if (ing == null) {
            return "";
        }
        if (ing.trim().equals("") || ing.trim().equals("-")) {
            return "";
        }
        if (ing.length() == 0) {
            return "";
        }
        if (ing.trim().startsWith(".") || ing.trim().startsWith(",")) {
            return "0" + decimal + "00";
        }

        String start = ing.substring(0, 1);
        if (start.equals("-") || start.equals("+")) {
            ing = ing.replaceAll(start, "");
        } else {
            start = "";
        }

        String out = "";
        if (ing.contains(",")) {
            ing = ing.replaceAll(",", "");
        }
        if (ing.contains(".")) {
            String[] inter1 = splitStringEvery(ing.split("\\.")[0], 3);
            if (inter1.length > 1) {
                for (String inter11 : inter1) {
                    out = out + inter11 + thousand;
                }
            } else {
                out = inter1[0];
            }
            if (out.lastIndexOf(thousand) + 1 == out.length()) {
                out = out.substring(0, out.lastIndexOf(thousand));
            }

            String dec = "00";
            if (ing.split("\\.").length > 1) {
                dec = ing.split("\\.")[1];
            }
            out = out + decimal + dec;
        } else {

            String[] inter1 = splitStringEvery(ing, 3);
            if (inter1.length > 1) {
                for (String inter11 : inter1) {
                    out = out + inter11 + thousand;
                }
            } else {
                out = inter1[0];
            }
            if (out.lastIndexOf(thousand) + 1 == out.length()) {
                out = out.substring(0, out.lastIndexOf(thousand));
            }
        }
        return start + out;
    }

    /**
     *
     * @param ing
     * @return
     */
    public static String parseArrayValues(String[] ing) {
        String out = "";
        if (ing != null) {
            for (String ing1 : ing) {
                out = out + ing1 + ";";
            }
        }
        return out;
    }

    /**
     *
     * @param ing
     * @return
     */
    public static String[] parseArrayValues(ArrayList<String> ing) {
        String[] out = new String[ing.size()];
        for (int i = 0; i < out.length; i++) {
            out[i] = ing.get(i);
        }
        return out;
    }

    /**
     *
     * @param ing
     * @return
     */
    public static ArrayList<String> formatArrayValues(String[] ing) {
        ArrayList<String> out = new ArrayList<>();
        out.addAll(asList(ing));
        return out;
    }

    /**
     *
     * @param ing
     * @param separator
     * @return
     */
    public static String[] parseArrayValues(String ing, String separator) {
        Iterable<String> parameters = on(separator).split(ing);
        Iterator<String> it = parameters.iterator();
        int size = 0;
        while (it.hasNext()) {
            String val = it.next().trim();
            if (!val.equals("")) {
                size++;
            }
        }
        it = parameters.iterator();
        String[] out = new String[size];
        int pos = 0;
        while (it.hasNext()) {
            String val = it.next().trim();
            if (!val.equals("")) {
                out[pos] = val;
                pos++;
            }
        }

        return out;
    }

    /**
     *
     * @param value
     * @return
     */
    public static int parseIntR(String value) {
        if (value == null) {
            return 0;
        }
        value = value.replaceAll("-", "").trim();
        if (value.contains(".")) {
            StringTokenizer st = new StringTokenizer(value, ".");
            value = st.nextToken();
        }
        int d1;
        try {
            d1 = parseInt(value);
        } catch (NumberFormatException e) {
            d1 = 0;
        }
        return d1;
    }

    /**
     *
     * @param dest
     * @return
     */
    public static boolean online(String dest) {
        try {
            InetAddress address = getByName(dest);
            return address.isReachable(10000);
        } catch (UnknownHostException ex) {
        } catch (IOException ex) {
        }
        return false;
    }

    /**
     *
     * @param url
     * @return
     */
    public static boolean pingURL(String url) {
        url = url.replaceFirst("^https", "http");
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setConnectTimeout(10000);
            connection.setReadTimeout(10000);
            connection.setRequestMethod("HEAD");
            int responseCode = connection.getResponseCode();
            return (200 <= responseCode && responseCode <= 399);
        } catch (IOException ex) {
            insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": PING ERROR " + ex.getMessage());
            return false;
        }
    }

    /**
     *
     * @param url
     * @return
     */
    public static boolean pingIPR(String url) {
        try {
            InetAddress inet = getByName(url);
            return inet.isReachable(5000);
        } catch (IOException ex) {
            insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
            return false;
        }
    }

    /**
     *
     * @param ip
     * @return
     */
    public static boolean pingIP(String ip) {
        List<String> commands = new ArrayList<>();
        commands.add("ping");
        commands.add(ip);
        boolean exit = false;
        try {
            String s;
            ProcessBuilder pb = new ProcessBuilder(commands);
            Process process = pb.start();
            BufferedReader stdError;
            try (BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                stdError = new BufferedReader(new InputStreamReader(process.getErrorStream()));
                // read the output from the command
                while ((s = stdInput.readLine()) != null) {
                    if (s.toLowerCase().contains("richiesta scaduta") || s.toLowerCase().contains("request timed out") || s.toLowerCase().contains("could not find host")) {
                        exit = false;
                        break;
                    } else if (s.toLowerCase().contains("risposta da") || s.toLowerCase().contains("reply from") || s.toLowerCase().contains("sent = 4, received = 4")) {
                        exit = true;
                        break;
                    }
                }   // read any errors from the attempted command
                if (exit) {
                    while (stdError.readLine() != null) {
                        exit = false;
                        break;
                    }
                }
            }
            stdError.close();
        } catch (IOException ex) {
            exit = false;
        }
        return exit;
    }

    /**
     *
     * @param ip
     * @return
     */
    public static boolean pingIPONLINE(String ip) {
        if (isCentral()) {
            return true;
        }
        List<String> commands = new ArrayList<>();
        commands.add("ping");
        commands.add(ip);
        boolean exit = false;
        try {
            String s;
            ProcessBuilder pb = new ProcessBuilder(commands);
            Process process = pb.start();
            BufferedReader stdError;
            try (BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                stdError = new BufferedReader(new InputStreamReader(process.getErrorStream()));
                // read the output from the command
                while ((s = stdInput.readLine()) != null) {
                    if (s.toLowerCase().contains("non raggiungibile") || s.toLowerCase().contains("richiesta scaduta") || s.toLowerCase().contains("request timed out") || s.toLowerCase().contains("could not find host")) {
                        exit = false;
                        break;
                    } else if (s.toLowerCase().contains("risposta da") || s.toLowerCase().contains("reply from") || s.toLowerCase().contains("sent = 4, received = 4")) {
                        exit = true;
                        break;
                    }
                }   // read any errors from the attempted command
                if (exit) {
                    while (stdError.readLine() != null) {
                        exit = false;
                        break;
                    }
                }
            }
            stdError.close();
        } catch (IOException ex) {
            exit = false;
        }
        return exit;
    }

    /**
     *
     * @param righe
     * @return
     */
    public static float getHeigth(int righe) {
        float y = 750.0F;
        if (righe <= 65) {
            y = 400.0F;
        } else if ((righe > 65) && (righe <= 70)) {
            y = 440.0F;
        } else if ((righe > 70) && (righe <= 75)) {
            y = 470.0F;
        } else if ((righe > 75) && (righe <= 80)) {
            y = 510.0F;
        } else if ((righe > 80) && (righe <= 85)) {
            y = 540.0F;
        } else if ((righe > 85) && (righe <= 90)) {
            y = 570.0F;
        }
        return y;
    }

    /**
     *
     * @param add
     * @param minus
     * @param month
     * @param day
     * @param minutes
     * @param patternout
     * @return
     */
    public static String getDefaultDateAplly(boolean add, boolean minus, int month, int day, int minutes, String patternout) {
        DateTime dt = new DateTime();
        if (add) {
            if (month > 0) {
                dt = dt.plusMonths(month);
            }
            if (day > 0) {
                dt = dt.plusDays(day);
            }
            if (minutes > 0) {
                dt = dt.plusMinutes(minutes);
            }
        } else if (minus) {
            if (month > 0) {
                dt = dt.minusMonths(month);
            }
            if (day > 0) {
                dt = dt.minusDays(day);
            }
            if (minutes > 0) {
                dt = dt.minusMinutes(minutes);
            }
        }
        if (patternout == null) {
            patternout = patternnormdate_f;
        }

        return dt.toString(patternout);

    }

    /**
     *
     * @param value1
     * @param value2
     * @param diff
     * @param rate
     * @param dividi
     * @return
     */
    public static String getValueDiff_R(String value1, String value2, String diff, String rate, boolean dividi) {
        double diff1 = 0.0;
        if (fd(value1) > 0 && fd(value2) > 0) {
            diff1 = fd(value1) - fd(value2);
        }
        double out = 0.0;
        double rateValue = fd(rate); 
        
        if (dividi) {
            if (rateValue != 0) { 
                out = diff1 / rateValue;
            } else {
                throw new ArithmeticException("Division by zero error: rate value is zero.");
            }
        } else {
            out = diff1 * rateValue;
        }
    
        return roundDoubleandFormat(out, 2);
    }
    
    
    

    /**
     *
     * @param value1
     * @param value2
     * @param diff
     * @param rate
     * @param dividi
     * @return
     */
    public static String getValueDiff_NEW(String value1, String value2, String diff, String rate, boolean dividi) {
        if (fd(value2) == 0) {
            return formatRa1(0.0);
        }
    
        String add = (fd(value1) >= fd(value2)) ? "" : "-";
        double rateValue = fd(rate);
        double out;
    
        if (rateValue != 0) {
            if (dividi) {
                out = fd(diff) / rateValue;
            } else {
                out = fd(diff) * rateValue;
            }
        } else {
            out = fd(diff);
        }
    
        return add + formatMysqltoDisplay(roundDoubleandFormat(out, 2));
    }
    
    
    /**
     *
     * @param value1
     * @param value2
     * @param diff
     * @param rate
     * @param dividi
     * @return
     */
//    public static String getValueDiff(String value1, String value2, String diff, String rate, boolean dividi) {
//        if (fd(value2) == 0) {
//            return formatRa1(0.0).trim();
//        }
//        String add;
//        if (fd(value1) >= fd(value2)) {
//            add = "";
//        } else {
//            add = "-";
//        }
//        double out;
//        if (dividi) {
//            out = fd(diff) / fd(rate);
//        } else {
//            out = fd(diff) * fd(rate);
//        }
//        return add + formatRa1(out).trim();
//    }
    public static String getValueDiff(String value1, String value2,
            String diff, String rate, boolean dividi) {

        if (fd(value2) == 0 || fd(rate) == 0) {
            return formatRa1(0.0);
        }

        String add;
        if (fd(value1) >= fd(value2)) {
            add = "";
        } else {
            add = "-";
        }

        double out;
        if (dividi) {
            double divider = fd(rate);
            if (divider == 0) {
                return "0.00";
            } else {
                out = fd(diff) / divider;
            }
        } else {
            out = fd(diff) * fd(rate);
        }

        String output = formatRa1(out);
        if (output.contains("-")) {
            return StringUtils.deleteWhitespace(output.trim());
        } else {
            return add + StringUtils.deleteWhitespace(output.trim());
        }
    }

    public static String formatRa1(double d1) {
        if (d1 >= 0) {
            NumberFormat nf = NumberFormat.getNumberInstance(Locale.ITALY);
            nf.setGroupingUsed(true);
            return StringUtils.deleteWhitespace(nf.format(d1)).trim();
        } else {
            DecimalFormat formatter = (DecimalFormat) NumberFormat.getCurrencyInstance(Locale.ITALY);
            DecimalFormatSymbols symbols = formatter.getDecimalFormatSymbols();
            symbols.setCurrencySymbol("");
            formatter.setDecimalFormatSymbols(symbols);
            String out = StringUtils.replace(formatter.format(d1), " ", "").trim();
            out = StringUtils.replace(out, " ", "").trim();
            out = StringUtils.replace(out, "\n", "").trim();
            out = StringUtils.replace(out, "\r", "").trim();
            out = StringUtils.replace(out, "\t", "").trim();
            return StringUtils.deleteWhitespace(out).trim();
        }
    }

    /**
     *
     * @param primo
     * @param secondo
     * @param dividi
     * @return
     */
    public static double getControvalore(double primo, double secondo, boolean dividi) {
        if (secondo == 0) {
            return 0.0;
        }
        if (dividi) {
            return primo / secondo;
        } else {
            return primo * secondo;
        }
    }

    /**
     *
     * @param primo
     * @param secondo
     * @param dividi
     * @return
     */
    public static double getControvaloreOFFSET(double primo, double secondo, boolean dividi) {
        if (secondo == 0) {
            return 0.0;
        }
        if (dividi) {
            return primo / secondo;
        } else {
            return secondo / primo;
        }
    }

    /**
     *
     * @param ing
     * @param separator
     * @return
     */
    public static ArrayList<String> parseString(String ing, String separator) {
        ArrayList<String> out = new ArrayList<>();
        if (ing != null) {
            Iterable<String> ite = onPattern(separator).split(ing);
            Iterator<String> it = ite.iterator();
            while (it.hasNext()) {
                String v = it.next().trim();
                if (!v.equals("")) {
                    out.add(v);
                }
            }
        }
        return out;
    }

    /**
     *
     * @param request
     * @return
     */
    public static String getFullUrl(HttpServletRequest request) {
        StringBuffer requestURL = request.getRequestURL();
        String queryString = request.getQueryString();
        if (queryString == null) {
            return requestURL.toString();
        } else {
            return requestURL.append('?').append(queryString).toString();
        }
    }

    /**
     *
     * @param d
     * @param scale
     * @return
     */
    public static String roundDoubleandFormat(double d, int scale) {
        return replace(BigDecimal.valueOf(BigDecimal.valueOf(d).setScale(scale + 1, ROUND_HALF_DOWN).doubleValue()).setScale(scale, ROUND_HALF_DOWN).toPlainString(), ",", ".");
    }

    public static String roundDoubleandFormat_ES(double d, int scale) {
        return replace(format("%." + scale + "f", d), ",", ".");
    }

//    public static String roundDoubleandFormat(double d, int scale) {
//        return StringUtils.replace(String.format("%." + scale + "f", new BigDecimal(d).setScale(scale, RoundingMode.HALF_DOWN).doubleValue()), ",", ".");
////        return StringUtils.replace(String.format("%." + scale + "f", d), ",", ".");
//    }
    /**
     *
     * @param request
     * @param replace
     * @param from
     * @param to
     * @return
     */
    public static String getFullUrl(HttpServletRequest request, boolean replace, String from, String to) {
        StringBuffer requestURL = request.getRequestURL();
        String parte = requestURL.toString();
        if (replace) {
            parte = replace(parte, from, to);
            return parte;
        }
        String queryString = request.getQueryString();
        if (queryString == null) {
            return requestURL.toString();
        } else {
            return requestURL.append('?').append(queryString).toString();
        }
    }

    /**
     *
     * @param inString
     * @param from
     * @param to
     * @return
     */
    public static String replace_SU(String inString, String from, String to) {
        return replace(inString, from, to).trim();
    }

    /**
     *
     * @param doc
     * @param name
     * @param index
     * @return
     */
    public static String getNodeValuefromName(Document doc, String name, int index) {
        if (doc.getElementsByTagName(name).item(index) != null) {
            return doc.getElementsByTagName(name).item(index).getTextContent().trim();
        }
        return "";
    }

    /**
     *
     * @param obj
     */
    public static void printJSONObject(JSONObject obj) {
        try {
            Iterator<String> i = obj.keys();
            while (i.hasNext()) {
                String v = i.next();
                if (obj.get(v) instanceof JSONArray) {
                    out.println("Name: " + v + " - type: JSONArray - Size: " + obj.getJSONArray(v).length());
                } else if (obj.get(v) instanceof Boolean) {
                    out.println("Name: " + v + " - type: Boolean - Value: " + obj.getBoolean(v));
                } else if (obj.get(v) instanceof String) {
                    out.println("Name: " + v + " - type: String - Value: " + obj.getString(v));
                } else {
                    out.println("Name: " + v + " - CLASS: " + obj.get(v).getClass());
                }
            }
        } catch (JSONException ex) {
            insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
        }
    }

    /**
     *
     * @param pathout
     * @param base64ing
     * @param tra
     * @param tipodoc
     * @return
     * @throws IOException
     */
    public static String getBase64(String pathout, String base64ing, Ch_transaction tra, String tipodoc) throws IOException {
        if (tra != null) {
            if (tra.getDel_fg().equals("0")) {
                if (base64ing.startsWith("FILE[")) {
                    String pa1 = replace(base64ing, "FILE[", "");
                    File f = new File(pa1);
                    return encodeBase64String(readFileToByteArray(f));
                }
                return base64ing;
            } else {
                byte pdfing[];
                if (base64ing.startsWith("FILE[")) {
                    String pa1 = replace(base64ing, "FILE[", "");
                    File f = new File(pa1);
                    pdfing = readFileToByteArray(f);
                } else {
                    pdfing = decodeBase64(base64ing);
                }

                boolean doubleimage = is_CZ && (tipodoc.equalsIgnoreCase("_czbuy") || tipodoc.equalsIgnoreCase("_czsell"));
                boolean printtop = is_CZ && (tipodoc.equalsIgnoreCase("_prebuy") || tipodoc.equalsIgnoreCase("_presell"));

                String base64 = printdeleted(pathout, pdfing, doubleimage, printtop);
                if (base64 != null) {
                    return base64;
                }
            }
        }
        return base64ing;
    }

    /**
     *
     * @param millsec
     */
    public static void sleeping(int millsec) {
        try {
            sleep(millsec);
        } catch (InterruptedException ex) {
            insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
            Thread.currentThread().interrupt();
        }
    }

    /**
     *
     * @param ho
     * @return
     */
    public static String getHost(String ho) {
        if (ho.contains("//") && ho.contains(":")) {
            return ho.split("/")[2].split(":")[0].trim();
        }
        return null;
    }

    /**
     *
     * @param html
     * @return
     */
    public static List<Element> parseHTMLtoPDF(String html) {
        try {
            org.jsoup.nodes.Document doc = parseBodyFragment(html.trim());
            html = "<div style='text-align: justify'>" + doc.toString() + "</div>";
            html = replace(html, "<br>", "<br/>");
            if (!is_IT) {
                html = replace(html, "<p>", "<p  style='margin-bottom:3px;'>");
            }
            List<com.itextpdf.text.Element> p1 = parseToElementList(html, null);
            return p1;
        } catch (Exception ex) {
            ex.printStackTrace();
            insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
            return new ElementList();
        }
    }

    /**
     *
     * @param start
     * @param pattern
     * @return
     */
    public static DateTime getDT(String start, String pattern) {
        if (start.length() == 21) {
            start = start.substring(0, 19);
        }
        if (start.length() == pattern.length()) {
            DateTimeFormatter formatter = forPattern(pattern);
            return formatter.parseDateTime(start);
        }
        return null;
    }

    /**
     *
     * @param start
     * @param pattern
     * @param days
     * @return
     */
    public static String subDays(String start, String pattern, int days) {
        DateTimeFormatter formatter = forPattern(pattern);
        DateTime dt = formatter.parseDateTime(start);
        return dt.minusDays(days).toString(pattern);
    }

    /**
     *
     * @param request
     * @param response
     * @param destination
     * @throws ServletException
     * @throws IOException
     */
    public static void redirect(HttpServletRequest request, HttpServletResponse response, String destination) throws ServletException, IOException {
        if (response.isCommitted()) {
            RequestDispatcher dispatcher = request.getRequestDispatcher(destination);
            dispatcher.forward(request, response);
        } else {
            response.sendRedirect(destination);
        }
    }

    /**
     *
     * @param request
     * @return
     */
    public static boolean isfirefox(HttpServletRequest request) {
        String userAgent = request.getHeader("User-Agent");
        return !userAgent.toLowerCase().contains("Chrome");
    }

    /**
     *
     * @param request
     * @return
     */
    public static String getCodBrowser(HttpServletRequest request) {
        String bw = request.getHeader("user-agent");
        if (bw.equals("")) {
            bw = request.getHeader("User-Agent");
        }
        if (bw.toLowerCase().trim().contains("edge")) {
            return "ED";
        } else if (bw.toLowerCase().trim().contains("chrome")) {
            return "CH";
        } else if (bw.toLowerCase().trim().contains("firefox")) {
            return "FI";
        } else if (bw.toLowerCase().trim().contains("gecko")) {
            return "IE";
        }
        return "IE";
    }

    /**
     *
     * @param ext
     * @return
     */
    public static String getMimeTypefromEXT(String ext) {
        switch (ext) {
            case "pdf":
                return "application/pdf";
            case "jpeg":
            case "jpg":
                return "image/jpeg";
            default:
                return null;
        }
    }

    /**
     *
     * @param ing
     * @return
     */
    public static String visualizzaStringaMySQL(String ing) {
        if (ing == null) {
            return "";
        }
        ing = replace(ing, "\\'", "'");
        ing = replace(ing, "\'", "'");
        ing = replace(ing, "\"", "'");
        return ing.trim();
    }

    /**
     *
     * @param files
     * @param targetZipFile
     * @return
     */
    public static boolean zipListFiles(List<File> files, File targetZipFile) {
        Path basePath = targetZipFile.getParentFile().toPath().normalize();  // Define the base path
    
        try (OutputStream out = new FileOutputStream(targetZipFile);
             ArchiveOutputStream os = new ArchiveStreamFactory().createArchiveOutputStream(ArchiveStreamFactory.ZIP, out)) {
    
            for (File file : files) {
                Path filePath = file.toPath().normalize();
                if (!filePath.startsWith(basePath)) {
                    throw new SecurityException("Path traversal attempt detected for file: " + file);
                }
    
                os.putArchiveEntry(new ZipArchiveEntry(filePath.toFile(), filePath.getFileName().toString()));
    
                try (FileInputStream fis = new FileInputStream(file)) {
                    copy(fis, os);
                }
                os.closeArchiveEntry();
            }
    
            return targetZipFile.length() > 0;
        } catch (Exception ex) {
            // Log the error as required
            insertTR("E", "System", Thread.currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
            return false;
        }
    }
    
    private static void copy(InputStream input, OutputStream output) throws IOException {
        byte[] buffer = new byte[1024];
        int length;
        while ((length = input.read(buffer)) != -1) {
            output.write(buffer, 0, length);
        }
    }

    /**
     *
     * @return @throws MalformedObjectNameException
     * @throws NullPointerException
     * @throws UnknownHostException
     */
    public static String createServerReturnSignLink() throws MalformedObjectNameException, NullPointerException, UnknownHostException {
        MBeanServer beanServer = getPlatformMBeanServer();
        Set<ObjectName> objectNames = beanServer.queryNames(new ObjectName("*:type=Connector,*"),
                match(attr("protocol"), value("HTTP/1.1")));
        String host = getLocalHost().getHostAddress();
        String port = objectNames.iterator().next().getKeyProperty("port");
        return "http://" + host.trim() + ":" + port.trim() + "/Mac2.0/Pdf?type=returnsign";
    }

    /**
     *
     * @return @throws MalformedObjectNameException
     * @throws NullPointerException
     * @throws UnknownHostException
     */
    public static String createServerReturnADDLink() throws MalformedObjectNameException, NullPointerException, UnknownHostException {
        MBeanServer beanServer = getPlatformMBeanServer();
        Set<ObjectName> objectNames = beanServer.queryNames(new ObjectName("*:type=Connector,*"),
                match(attr("protocol"), value("HTTP/1.1")));
        String host = getLocalHost().getHostAddress();
        String port = objectNames.iterator().next().getKeyProperty("port");
        return "http://" + host.trim() + ":" + port.trim() + "/Mac2.0/Pdf?type=addsign";
    }

    /**
     *
     * @return @throws MalformedObjectNameException
     * @throws NullPointerException
     * @throws UnknownHostException
     */
    public static String createServerReturnADDAddress() throws MalformedObjectNameException, NullPointerException, UnknownHostException {
        MBeanServer beanServer = getPlatformMBeanServer();
        Set<ObjectName> objectNames = beanServer.queryNames(new ObjectName("*:type=Connector,*"),
                match(attr("protocol"), value("HTTP/1.1")));
        String host = getLocalHost().getHostAddress();
        String port = objectNames.iterator().next().getKeyProperty("port");
        return "http://" + host.trim() + ":" + port.trim() + "/Mac2.0/Scan?type=readADDR";
    }

    /**
     *
     * @param codid
     * @return
     * @throws MalformedObjectNameException
     * @throws NullPointerException
     * @throws UnknownHostException
     */
    public static String createServerReturnScannerLink(String codid) throws MalformedObjectNameException, NullPointerException, UnknownHostException {
        MBeanServer beanServer = getPlatformMBeanServer();
        Set<ObjectName> objectNames = beanServer.queryNames(new ObjectName("*:type=Connector,*"),
                match(attr("protocol"), value("HTTP/1.1")));
        String host = getLocalHost().getHostAddress();
        String port = objectNames.iterator().next().getKeyProperty("port");
        return "http://" + host.trim() + ":" + port.trim() + "/Mac2.0/Scan?type=scannerdocument&trid=" + codid;
    }

    /**
     *
     * @param str
     * @return
     */
    public static boolean haveLetter(String str) {
        return matches("[a-zA-Z]+", str);
    }

    /**
     *
     * @param num
     * @param den
     * @return
     */
    public static double divisione_controllozero(double num, double den) {
        double out = 0.0;
        if (den == 0.0) {
            return out;
        }
        try {
            out = num / den;
        } catch (ArithmeticException e) {
            return 0.00;
        }
        return out;
    }

    /**
     *
     * @param email
     * @return
     */
    public static boolean validatemail(String email) {
        if (email == null) {
            return false;
        }
        if (email.trim().equals("")) {
            return false;
        }
        email = email.toLowerCase().trim();
        return getInstance().isValid(email);
    }

    /**
     *
     * @param dest
     * @param oggetto
     * @param testo
     * @return
     */
    public static boolean sendMailHtmlNOBCC(String dest, String oggetto, String testo) {
        try {

            Db_Master dbcentr = new Db_Master(true);

            String host = dbcentr.getPath("mail.host");
            String port = dbcentr.getPath("mail.port");
            String user = dbcentr.getPath("mail.user");
            String pass = dbcentr.getPath("mail.pass");

            dbcentr.closeDB();

            Properties props = new Properties();
            props.put("mail.smtp.host", host);
            props.put("mail.smtp.ssl.enable", "true");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.port", port);
            props.put("mail.smtp.timeout", 3000);
            props.put("mail.smtp.connectiontimeout", 3000);

            Session session = getDefaultInstance(
                    props,
                    new javax.mail.Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(user, pass);
                }
            });
            Message message = new MimeMessage(session);
            InternetAddress froms = new InternetAddress(user);
            froms.setPersonal("Noreply Mac2.0");
            message.setFrom(froms);
            message.setRecipients(TO, parse(dest));
            message.setSubject(oggetto);
            Multipart mp = new MimeMultipart();
            MimeBodyPart mbp1 = new MimeBodyPart();
            mbp1.setContent(testo, "text/html");
            mp.addBodyPart(mbp1);
            message.setContent(mp);
            send(message);
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
//            insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
        }
        return false;
    }

    /**
     *
     * @param dest
     * @param oggetto
     * @param testo
     * @param fileDaAllegare
     * @return
     */
    public static boolean sendMailHtml(String dest, String oggetto, String testo, File fileDaAllegare) {

        Db_Master dbcentr = new Db_Master(true);

        String host = dbcentr.getPath("mail.host");
        String port = dbcentr.getPath("mail.port");
        String user = dbcentr.getPath("mail.user");
        String pass = dbcentr.getPath("mail.pass");
//        String bcc1 = dbcentr.getPath("mail.bcc1");
//        String bcc2 = dbcentr.getPath("mail.bcc2");

        dbcentr.closeDB();

        Properties props = new Properties();
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.ssl.enable", "true");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", port);
        props.put("mail.smtp.timeout", 3000);
        props.put("mail.smtp.connectiontimeout", 3000);
        Session session = getDefaultInstance(props, new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(user, pass);
            }
        });
        try {
            Message message = new MimeMessage(session);

            InternetAddress froms = new InternetAddress(user);
            froms.setPersonal("Noreply Mac2.0");
            message.setFrom(froms);
            message.setRecipients(TO, parse(dest));

//            if (bcc1 != null && !bcc1.equals("")) {
//                InternetAddress[] bcc = new InternetAddress[2];
//                bcc[0] = new InternetAddress(bcc1);
//                bcc[1] = new InternetAddress(bcc2);
//                message.setRecipients(BCC, bcc);
//            }
            message.setSubject(oggetto);
            Multipart mp = new MimeMultipart();
            MimeBodyPart mbp1 = new MimeBodyPart();
            mbp1.setContent(testo, "text/html");
            mp.addBodyPart(mbp1);
            message.setContent(mp);

            send(message);
            return true;
        } catch (Exception ex) {
            insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
        }
        return false;
    }

    /**
     *
     * @param json
     * @return
     * @throws JSONException
     */
    public static Map<String, Object> jsonToMap(Object json) throws JSONException {
        if (json instanceof JSONObject) {
            return _jsonToMap_((JSONObject) json);
        } else if (json instanceof String) {
            JSONObject jsonObject = new JSONObject((String) json);
            return _jsonToMap_(jsonObject);
        }
        return null;
    }

    private static Map<String, Object> _jsonToMap_(JSONObject json) throws JSONException {
        Map<String, Object> retMap = new HashMap<>();
        if (json != NULL) {
            retMap = toMap(json);
        }
        return retMap;
    }

    private static Map<String, Object> toMap(JSONObject object) throws JSONException {
        Map<String, Object> map = new HashMap<>();

        Iterator<String> keysItr = object.keys();
        while (keysItr.hasNext()) {
            String key = keysItr.next();
            Object value = object.get(key);

            if (value instanceof JSONArray) {
                value = toList((JSONArray) value);
            } else if (value instanceof JSONObject) {
                value = toMap((JSONObject) value);
            }
            map.put(key, value);
        }
        return map;
    }

    /**
     *
     * @param array
     * @return
     * @throws JSONException
     */
    public static List<Object> toList(JSONArray array) throws JSONException {
        List<Object> list = new ArrayList<>();
        for (int i = 0; i < array.length(); i++) {
            Object value = array.get(i);
            if (value instanceof JSONArray) {
                value = toList((JSONArray) value);
            } else if (value instanceof JSONObject) {
                value = toMap((JSONObject) value);
            }
            list.add(value);
        }
        return list;
    }

    /**
     *
     * @param url
     * @param param
     * @return
     */
    public static String sendPostRequest(String url, String param) {
        try {
            URLConnection connection = new URL(url).openConnection();
            connection.setDoOutput(true);
            connection.setRequestProperty("Accept-Charset", charset);
            connection.setRequestProperty("Content-Type", "application/json;charset=" + charset);
            try (OutputStream output = connection.getOutputStream()) {
                output.write(param.getBytes(charset));
            }
            InputStream response = connection.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(response));
            String line;
            if ((line = rd.readLine()) != null) {
                return line;
            }
            response.close();
            rd.close();
        } catch (IOException ex) {
            insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
            return "Error: " + ex.getMessage();
        }
        return "Error: Response not found.";
    }

    /**
     *
     * @param cod_nc
     * @return
     */
    public static String sendPOS_transaction_new(String cod_nc) {
        try {
            NC_transaction ncc1 = get_NC_transaction(cod_nc);
            if (ncc1 != null) {

                String token = getIdScontrino(ncc1);
                JSONObject json1 = new JSONObject();
                json1.put("Command", "POS");
                json1.put("Token", token);
                json1.put("Payment", ncc1.getSupporto());

                String t1 = ncc1.getTotal();
                if (ncc1.getFg_tipo_transazione_nc().equals("21")) {
                    t1 = ncc1.getCommissione();
                }

                NC_category cat1 = getNC_category(ncc1.getGruppo_nc());
                String d1 = replace(visualizzaStringaMySQL(cat1.getDe_gruppo_nc()), "€", "&#0128;");

                if (is_IT && ncc1.getFg_tipo_transazione_nc().equals("2")) {
                    NC_vatcode vat = get_NC_vatcode(cat1.getGruppo_nc());
                    if (vat.getAccountingcode1().equals("")) {
                        JSONObject jo = new JSONObject();
                        jo.put("Quantity", 1); // ADD SIMONE 230818
                        jo.put("R", cat1.getDepartment());
                        jo.put("Description", d1);
                        jo.put("Price", t1);
                        JSONArray ja = new JSONArray();
                        ja.put(jo);
                        json1.put("Records", ja);
                    } else {
                        if (vat.getAccountingcode2().equals("")) {
                            JSONObject jo = new JSONObject();
                            jo.put("Quantity", 1); // ADD SIMONE 230818
                            jo.put("R", cat1.getDepartment());
                            jo.put("Description", d1);
                            jo.put("Price", t1);
                            JSONArray ja = new JSONArray();
                            ja.put(jo);
                            json1.put("Records", ja);
                        } else {
                            double tot = fd(t1);
                            double prop_1 = fd(roundDoubleandFormat(fd(ncc1.getQuantita()) * fd(vat.getPrice1()), 2));
                            double prop_2 = fd(roundDoubleandFormat(fd(ncc1.getQuantita()) * fd(vat.getPrice2()), 2));
                            if (tot == (prop_1 + prop_2)) {
                                JSONObject jo1 = new JSONObject();
                                jo1.put("Quantity", 1); // ADD SIMONE 230818
                                jo1.put("R", vat.getDepartment1());
                                jo1.put("Description", d1 + " " + vat.getVatcode1());
                                jo1.put("Price", roundDoubleandFormat(prop_1, 2));

                                JSONObject jo2 = new JSONObject();
                                jo2.put("Quantity", 1); // ADD SIMONE 230818
                                jo2.put("R", vat.getDepartment2());
                                jo2.put("Description", d1 + " " + vat.getVatcode2());
                                jo2.put("Price", roundDoubleandFormat(prop_2, 2));
                                JSONArray ja = new JSONArray();
                                ja.put(jo1);
                                ja.put(jo2);
                                json1.put("Records", ja);

                            } else {
                                JSONObject jo = new JSONObject();
                                jo.put("Quantity", 1); // ADD SIMONE 230818
                                jo.put("R", cat1.getDepartment());
                                jo.put("Description", d1);
                                jo.put("Price", t1);
                                JSONArray ja = new JSONArray();
                                ja.put(jo);
                                json1.put("Records", ja);
                            }
                        }

                    }
                } else {
                    JSONObject jo = new JSONObject();
                    jo.put("Quantity", 1); // ADD SIMONE 230818
                    jo.put("R", cat1.getDepartment());
                    jo.put("Description", d1);
                    jo.put("Price", t1);
                    JSONArray ja = new JSONArray();
                    ja.put(jo);
                    json1.put("Records", ja);
                }
                String link = "http://localhost:9001/pos";
                String resp = sendPostRequest(link, json1.toString());
                String Success = "";
                String Message = "";
                if (resp.startsWith("Error")) {
                    return resp;
                } else {
                    Map<String, Object> hashMap = new HashMap<>(jsonToMap(resp));
                    for (Map.Entry<String, Object> entry : hashMap.entrySet()) {
                        if (entry.getKey().equals("Success")) {
                            Success = entry.getValue().toString().trim();
                        } else if (entry.getKey().equals("Message")) {
                            Message = entry.getValue().toString().trim();
                        }
                    }
                    if (valueOf(Success)) {
                        return "OK";
                    } else {
                        return "Error: " + removeNewline(Message);
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
        }
        return null;
    }

    private static void processJsonElement(JsonElement e) {
        if (e.isJsonArray()) {
            processJsonArray(e.getAsJsonArray());
        } else if (e.isJsonNull()) {
            processJsonNull(e.getAsJsonNull());
        } else if (e.isJsonObject()) {
            processJsonObject(e.getAsJsonObject());
        } else if (e.isJsonPrimitive()) {
            processJsonPrimitive(e.getAsJsonPrimitive());
        }
    }

    private static void processJsonArray(JsonArray a) {
        for (JsonElement e : a) {
            processJsonElement(e);
        }
    }

    private static void processJsonNull(JsonNull n) {
        out.println("null || : " + n);
    }

    private static void processJsonObject(JsonObject o) {
        Set<Map.Entry<String, JsonElement>> members = o.entrySet();
        members.stream().map(e -> {
            out.println("Processing object member: " + e.getKey());
            return e;
        }).forEachOrdered(e -> {
            processJsonElement(e.getValue());
        });
    }

    private static void processJsonPrimitive(JsonPrimitive p) {
        out.println("Primitive || :" + p);
    }

    /**
     *
     * @param cod_nc
     * @return
     */
    public static String sendPOS_transaction(String cod_nc) {
        try {
            NC_transaction ncc1 = get_NC_transaction(cod_nc);
            if (ncc1 != null) {

                NC_category cat1 = getNC_category(ncc1.getGruppo_nc());

                String token = getIdScontrino(ncc1);
                JSONObject json1 = new JSONObject();
                json1.put("Command", "POS");
                json1.put("Token", token);
                json1.put("Payment", ncc1.getSupporto());

                String d1 = replace(visualizzaStringaMySQL(cat1.getDe_gruppo_nc()), "€", "&#0128;");

//                String d1 = StringUtils.replace(visualizzaStringaMySQL(formatALNC_category(ncc1.getGruppo_nc(),
//                        list_nc_category_enabled())), "€", "&#0128;");
//                String q1;
                String t1 = ncc1.getTotal();

                // ADD SIMONE
                //NC_category nccsr = Engine.getNC_category(ncc1.getGruppo_nc());
                //if (!nccsr.getConto_coge_02().equals("")) {
                if (ncc1.getFg_tipo_transazione_nc().equals("21")) {
                    t1 = ncc1.getCommissione();
                }
                //FINE SIMONE

//                if (ncc1.getFg_tipo_transazione_nc().equals("1")) {
//                    q1 = "1";
//                } else if (ncc1.getFg_tipo_transazione_nc().equals("3")) {
//                    q1 = formatMysqltoDisplay(roundDoubleandFormat(fd(ncc1.getRicevuta()), 0));
//                }
                JSONObject jo = new JSONObject();
                //jo.put("Quantity", q1); //COSCO
                jo.put("Quantity", 1); // ADD SIMONE 230818
                jo.put("R", cat1.getDepartment());
//                jo.put("R", get_department_NC(ncc1.getGruppo_nc()));
                jo.put("Description", d1);
                jo.put("Price", t1);
                JSONArray ja = new JSONArray();
                ja.put(jo);
                json1.put("Records", ja);
                String link = "http://localhost:9001/pos";
                String resp = sendPostRequest(link, json1.toString());
                String Success = "";
                String Message = "";
                if (resp.startsWith("Error")) {
                    return resp;
                } else {
                    Map<String, Object> hashMap = new HashMap<>(jsonToMap(resp));
                    for (Map.Entry<String, Object> entry : hashMap.entrySet()) {
                        if (entry.getKey().equals("Success")) {
                            Success = entry.getValue().toString().trim();
                        } else if (entry.getKey().equals("Message")) {
                            Message = entry.getValue().toString().trim();
                        }
                    }
                    if (Boolean.parseBoolean(Success)) {
                        return "OK";
                    } else {
                        return "Error: " + removeNewline(Message);
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
        }
        return null;
    }

    /**
     *
     * @param ing
     * @return
     */
    public static String removeNewline(String ing) {
        ing = replace(ing, getProperty("line.separator"), " ").trim();
        ing = replace(ing, "\n", " ").trim();
        ing = replace(ing, "\r", " ").trim();
        ing = replace(ing, "\t", " ").trim();
        return ing;
    }

    /**
     *
     * @param nc
     * @return
     */
    public static String getIdScontrino(NC_transaction nc) {
        return nc.getFiliale() + "/" + valueOf(parseIntR(nc.getId()));
    }

    //SCAN
    /**
     *
     * @param ing
     * @return
     */
    public static String formatTypeDOC(String ing) {
        if (ing.equalsIgnoreCase("P")) {
            return "PS";
        }
        return ing;
    }

    /**
     *
     * @param ing
     * @return
     */
    public static String formatdatePassportCosco(String ing) {
        String f1 = "yyMMdd";
        String f2 = "dd/MM/yyyy";
        if (ing.length() == f1.length()) {
            DateTimeFormatter formatter = forPattern(f1);
            DateTime dt = formatter.parseDateTime(ing);
            return dt.toString(f2, ITALY).trim();
        }
        return ing.trim();
    }

    /**
     *
     * @param ing
     * @return
     */
    public static String formatdatePassport(String ing) {
        String f1 = "yyMMdd";
        String f2 = "dd/MM/yyyy";
        try {
            if (ing.length() == f1.length()) {
                DateTimeFormatter formatter = forPattern(f1);
                DateTime dt = formatter.parseDateTime(ing);
                return dt.toString(f2, ITALY).trim();
            }
            return ing.trim();
        } catch (IllegalArgumentException e) {
            return "";
        }

    }

    /**
     *
     * @return
     */
    public static String getHostCentrale() {
        String hostSTART = rb.getString("db.ip");
        if (is_UK) {
            if (test) {
                return hostSTART + "/maccorpuk";
            } else {
                return hostSTART + "/maccorpukprod";
            }
        } else if (is_CZ) {
            if (test) {
                return hostSTART + "/maccorpcz";
            } else {
                return hostSTART + "/maccorpczprod";
            }
        } else {
            if (test) {
                return hostSTART + "/maccorp";
            } else {
                return hostSTART + "/maccorpita";
            }
        }
    }

//    public static String setHostInternational(String ing, boolean fi) {
//        if (fi) {
//            return ing;
//        }
//        if (Constant.is_UK) {
//            ing = StringUtils.replace(ing, "maccorpita", "maccorpuk");
//            if (ing.endsWith("maccorp")) {
//                ing = ing + "uk";
//            }
//        } else if (Constant.is_CZ) {
//            ing = StringUtils.replace(ing, "maccorpita", "maccorpcz");
//            if (ing.endsWith("maccorp")) {
//                ing = ing + "cz";
//            }
//        }
//        return ing;
//    }
    /**
     *
     * @param date
     * @param pattern
     * @return
     */
    public static DateTime fDate_get_last_modify_rate(String date, String pattern) {
        DateTimeFormatter formatter = forPattern(pattern);
        if (pattern.length() == date.length()) {
            return formatter.parseDateTime(date);
        } else {
            date = date + ":00";
            return formatter.parseDateTime(date);
        }
    }

    /**
     *
     * @param ing
     * @param defaultvalue
     * @return
     */
    public static String formatValue(String ing, String defaultvalue) {
        if (ing == null) {
            return defaultvalue;
        }
        if (ing.equals("null")) {
            return defaultvalue;
        }
        return ing;
    }

    /**
     *
     * @param pdf
     * @param tra
     * @return
     * @throws IOException
     */
    public static String getBase64(File pdf, Ch_transaction tra) throws IOException {

        if (tra != null) {
            if (tra.getDel_fg().equals("0")) {
                return new String(encodeBase64(readFileToByteArray(pdf)));
            } else {
                String base64 = printdeleted(pdf, false, false);
                if (base64 != null) {
                    return base64;
                }
            }
        }
        return new String(encodeBase64(readFileToByteArray(pdf)));
    }

    /**
     *
     * @param input
     * @return
     */
    public static String convertFIDCODE(String input) {
        if (input == null) {
            return "";
        }
        if (input.length() != 18) {
            String br = substring(input, 0, 3);
            String id = substring(input, 3);
            String id_F = leftPad(id, 15, "0");
            String complete = br + id_F;
            return complete;
        }
        return input;
    }

    /**
     *
     * @param sheet
     * @param index
     * @return
     */
    public static Row getRow(Sheet sheet, int index) {
        Row r = sheet.getRow(index);
        if (r == null) {
            r = sheet.createRow(index);
        }
        return r;
    }

    /**
     *
     * @param row
     * @param index
     * @param ct
     * @return
     */
    public static Cell getCell(Row row, int index, CellType ct) {
        Cell cell1 = row.getCell(index);
        if (cell1 == null || ct.equals(NUMERIC)) {
            cell1 = row.createCell(index, ct);
        }
        return cell1;
    }

    /**
     *
     * @param row
     * @param index
     * @return
     */
    public static Cell getCell(Row row, int index) {
        Cell cell1 = row.getCell(index);
        if (cell1 == null) {
            cell1 = row.createCell(index);
        }
        return cell1;
    }

    /**
     *
     * @param ingresso
     * @return
     */
    public static String formatperOAM(String ingresso) {
        List<Character> charsS = "¬¹²²¨~`!#$%^&*+=-[]();,/{}|\":<>?£,.àáâãäçèéêëìíîïñòóôõöùúûüýÿÀÁÂÃÄÇÈÉÊËÌÍÎÏÑÒÓÔÕÖÙÚÛÜ_Ý°èéòàù§*ç@|!£$%&/()=?^€ì".chars().mapToObj(e -> (char) e).collect(Collectors.toList());
        List<Character> chars1 = "ÀÁÂÃÄÅàáâãäåÒÓÔÕÕÖØòóôõöøÈÉÊËèéêëðÇçÐÌÍÎÏìíîïÙÚÛÜùúûüÑñŠšŸÿýŽž".chars().mapToObj(e -> (char) e).collect(Collectors.toList());
        List<Character> char_ingresso = ingresso.chars().mapToObj(e -> (char) e).collect(Collectors.toList());
        StringBuilder out = new StringBuilder("");
        char_ingresso.forEach(char1 -> {
            if (chars1.contains(char1)) {
                out.append(stripAccents(char1.toString().trim())).append("'");
            } else {
                if (!charsS.contains(char1)) {
                    out.append(char1);
                }
            }
        });
        return out.toString().toUpperCase().trim();
    }

    /**
     *
     * @param request
     * @param name
     * @return
     */
    public static List<String> getList_request(HttpServletRequest request, String name) {
        List<String> out = new ArrayList<>();
        try {
            String[] output = request.getParameterValues(name);
            if (output != null) {
                return asList(output);
            }
        } catch (Exception e) {
        }
        return out;
    }

    /**
     *
     * @param total
     * @param percentiva
     * @return
     */
    public static double calcolaIva(double total, double percentiva) {
        double t = 100.00;
        double out = total / ((t + percentiva) / t);
        return total - out;
    }

    /**
     *
     * @param ing
     * @return
     */
    public static String[] verificaClientNumber(String ing) {
        String out[] = {"", ""};
        if (ing != null) {
            if (ing.trim().length() == 16) {
                out[0] = ing.trim().toUpperCase();
            } else if (ing.trim().length() == 11 || ing.trim().length() == 13) {
                out[1] = ing.trim().toUpperCase();
            }
        }
        return out;
    }

    /**
     *
     * @param from
     * @return
     */
    public static DateTime getDateRif(String from) {
        try {
            DateTimeFormatter formatter = forPattern(patternnormdate_filter);
            DateTime dt = formatter.parseDateTime(from);
            return dt;
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     *
     * @param ing
     * @return
     */
    public static String get_altezza_textarea(String ing) {
        try {
            int length = ing.trim().length();
            if (length >= 100) {
                double total = length / 100.00;
                int t1 = (int) round(total);
                return valueOf(t1);
            }
        } catch (Exception ex) {

        }

        return "1";
    }

    /**
     *
     * @param jObj
     * @param findKey
     * @return
     */
    public static String json_getRecurseValue(JSONObject jObj, String findKey) {
        List<JsonValue> strOut = new ArrayList<>();
        List<String> lst = new ArrayList<>();
        findKeysOfJsonObject(jObj, lst, strOut);
        StringBuilder out = new StringBuilder("");
        strOut.forEach(valore -> {
            if (valore.getKey().equalsIgnoreCase(findKey)) {
                out.append(valore.getValue());
            }
        });
        return out.toString();
    }

    private static List<String> findKeysOfJsonArray(JSONArray jsonIn, List<String> keys, List<JsonValue> strOut) {
        List<String> keysFromArr = new ArrayList<>();
        if (jsonIn != null && jsonIn.length() != 0) {
            for (int i = 0; i < jsonIn.length(); i++) {
                JSONObject jsonObjIn = jsonIn.getJSONObject(i);
                keysFromArr = findKeysOfJsonObject(jsonObjIn, keys, strOut);
            }
        }
        return keysFromArr;
    }

    /**
     *
     * @param jsonIn
     * @param keys
     * @param strOut
     * @return
     */
    public static List<String> findKeysOfJsonObject(JSONObject jsonIn, List<String> keys, List<JsonValue> strOut) {
        Iterator<String> itr = jsonIn.keys();
        List<String> keysFromObj = makeList(itr);
        keys.addAll(keysFromObj);
        itr = jsonIn.keys();
        while (itr.hasNext()) {
            String itrStr = itr.next();
            JSONObject jsout;
            JSONArray jsArr;
            if (jsonIn.get(itrStr).getClass() == JSONObject.class) {
                jsout = jsonIn.getJSONObject(itrStr);
                findKeysOfJsonObject(jsout, keys, strOut);
            } else if (jsonIn.get(itrStr).getClass() == JSONArray.class) {
                jsArr = jsonIn.getJSONArray(itrStr);
                keys.addAll(findKeysOfJsonArray(jsArr, keys, strOut));
            } else if (jsonIn.get(itrStr).getClass() == String.class) {
                strOut.add(new JsonValue(itrStr, jsonIn.getString(itrStr).trim()));
            }
        }
        return keys;
    }

    private static List<String> makeList(Iterator<String> iter) {
        List<String> list = new ArrayList<>();
        while (iter.hasNext()) {
            list.add(iter.next());
        }
        return list;
    }

    /**
     *
     * @param base
     * @param fieldname
     * @return
     */
    public static String getJsonString(JSONObject base, String fieldname) {
        try {
            Object o1 = base.get(fieldname);
            if (o1 != null) {
                return o1.toString().trim();
            }
        } catch (Exception e) {

        }
        return "";
    }

    /**
     *
     * @param input
     * @param fieldname
     * @return
     */
    public static String getJsonString(String input, String fieldname) {
        try {
            JSONObject base = new JSONObject(input);
            Object o1 = base.get(fieldname);
            if (o1 != null) {
                return o1.toString().trim();
            }
        } catch (Exception e) {
        }
        return "";
    }

    public static boolean getValue_request(HttpServletRequest request, String name) {
        if (request.getParameter(name) == null) {
            return false;
        } else {
            return request.getParameter(name).equals("on");
        }
    }

    public static String getValue_request(HttpServletRequest request, String name, boolean checkbox, String defaultvalue) {
        if (checkbox) {
            String out = request.getParameter(name);
            if (null == out) {
                out = "0";
            } else {
                switch (out) {
                    case "on":
                        out = "1";
                        break;
                    default:
                        out = "0";
                        break;
                }
            }
            return out;
        }
        String out = request.getParameter(name);
        if (out == null || out.equals("null")) {
            return defaultvalue;
        }
        return out.trim();
    }

    /**
     * createCERT_EET
     */
    public static void createCERT_EET() {

        String file1 = "C:\\Maccorp\\eet\\keys\\rca15_rsa.der";
        String file2 = "C:\\Maccorp\\eet\\keys\\2qca16_rsa.der";
        try {
            String base641 = encodeBase64String(readFileToByteArray(new File(file1)));
            String base642 = encodeBase64String(readFileToByteArray(new File(file2)));
            out.println("rca15_rsa () " + base641);
            out.println("2qca16_rsa () " + base642);
//                Database db1 = new Database();
//                System.out.println("rc.so.exe.Create_CERT.main() " + db1.updateCERT(filiale, base64));
//                db1.closeDB();
        } catch (IOException ex) {
        }
    }

    public static boolean send_Mail_CONFERMA_TRX(Booking bo) {
        MailObject mo = new MailObject();
        mo.setOGGETTO("Ritiro Prenotazione");
        mo.setIDPRENOTAZIONE(bo.getCod());
        mo.setINTRO("Gentile " + capitalize(bo.getCl_nome()) + " " + capitalize(bo.getCl_cognome()) + ",");
        mo.setCORPO("la tua prenotazione è stata ritirata&#33;");
        mo.setRIEPILOGO("");
        mo.setALTREINFO("");
        mo.setNVERDE("Per richiedere assistenza contatta il nostro Numero Verde 800 30 53 57");
        mo.setCHIUSURA("Buonviaggio dal team di Forexchange &#38; Travel, i tuoi assistenti di viaggio.");
        try {
            String pathTemp = "/mnt/temp/";
            String mail = get_ValueSettings("TM1");
            File temp = new File(pathTemp + generaId(75) + "_temp.html");
            writeByteArrayToFile(temp, decodeBase64(mail));
            String content = asCharSource(temp, UTF_8).read();
            content = replace(content, "{{DESC_PRENOTAZIONE}}", "Prenotazione n.");
            content = replace(content, "{{ID PRENOTAZIONE}}", mo.getIDPRENOTAZIONE());
            content = replace(content, "{{TESTO INTRO}}", mo.getINTRO());
            content = replace(content, "{{TESTO CORPO}}", mo.getCORPO());
            content = replace(content, "{{TESTO RIEPILOGO}}", mo.getRIEPILOGO());
            content = replace(content, "{{TESTO ALTRE INFO}}", mo.getALTREINFO());
            content = replace(content, "{{TESTO NUMERO VERDE}}", mo.getNVERDE());
            content = replace(content, "{{TESTO CHIUSURA}}", mo.getCHIUSURA());
            if (getInstance().isValid(bo.getCl_email())) {
                boolean es = sendMailHtml(bo.getCl_email(), mo.getOGGETTO(), content, null);
                if (es) {
                    insertTR("W", "site", bo.getCod() + " MAIL CONFIRM SEND.");
                }
                return es;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public static List<String> calcolaspread2021(
            String filiale,
            String buysell,
            String typeop,
            String tofrom,
            String figures,
            String kind,
            String quantity,
            String rate) {

        List<String> output = new ArrayList<>();
//        System.out.println("rc.so.util.Utility.calcolaspread2021(1) " + kind);
//        System.out.println("rc.so.util.Utility.calcolaspread2021(2) " + figures);
//        System.out.println("rc.so.util.Utility.calcolaspread2021(3) " + quantity);
//        System.out.println("rc.so.util.Utility.calcolaspread2021(4) " + rate);
        Db_Master db01 = new Db_Master();
        String valutalocale = db01.get_local_currency()[0];
        boolean dividi = db01.get_national_office().getChangetype().equals("/");
        Currency valutastraniera1 = db01.getCurrency(figures);
        db01.closeDB();
        boolean isvalutalocale = valutalocale.equals(figures);
        double nuovorate = 0.00;

        DateTimeFormatter formatter = forPattern(patternsqldate);
        double quant_now = fd(quantity);
        if (!isvalutalocale) {
            double contr = getControvalore(quant_now, fd(rate), dividi);
            if (buysell.equals("B")) {
                double contr_BCE = getControvalore(quant_now, fd(valutastraniera1.getCambio_bce()), dividi);
                double spreadtotal = contr_BCE - contr;
                output.add(("OK"));
                output.add((formatMysqltoDisplay(roundDoubleandFormat(spreadtotal, 2))));
                output.add((formatMysqltoDisplay(roundDoubleandFormat(contr_BCE, 2))));
                output.add((formatMysqltoDisplay(rate)));
            } else {

                Db_Master db1 = new Db_Master();
                ArrayList<Stock> al = db1.list_stock(filiale, kind, figures);
                db1.closeDB();

                double quant_check = quant_now;

                DateTime inizioanno = new DateTime().withDayOfYear(1).withMillisOfDay(0);
                for (int i = 0; i < al.size() && quant_check > 0; i++) {
                    double vq1 = fd(al.get(i).getTotal());
                    double vr1 = fd(al.get(i).getRate());

                    if (vq1 > 0) {

                        double oldrate;

                        DateTime data_lotto = formatter.parseDateTime(substring(al.get(i).getDate(), 0, 19));

                        boolean annoprecedente = data_lotto.isBefore(inizioanno);
                        if (annoprecedente) {
                            DateTime data_riferimento = inizioanno.minusDays(1);
                            oldrate = fd(get_BCE(data_riferimento, valutastraniera1.getCode()));
                        } else {

                            boolean checkET = al.get(i).getTipostock().equals("ET");
                            if (checkET) {
                                Db_Master db2 = new Db_Master();
                                checkET = db2.is_ET_FROMBRANCH(al.get(i).getIdoperation());
                                db2.closeDB();
                            }

                            if (checkET) {
                                oldrate = fd(al.get(i).getRate());
                            } else {
                                oldrate = fd(get_BCE(data_lotto, valutastraniera1.getCode()));
                            }
                        }

                        if (oldrate == 0) {
                            oldrate = vr1;
                        }

                        if (vq1 >= quant_check) {
                            nuovorate += oldrate * quant_check;
                            quant_check = 0.0;
                        } else {
                            nuovorate += oldrate * vq1;
                            quant_check = quant_check - vq1;
                        }
                    }
                }

                if (quant_now != 0) {
                    nuovorate = roundDouble(nuovorate / quant_now, 8);
                } else {
                    nuovorate = 0.0; 
                }
                
                if (tofrom.equals("T") && typeop.equals("BR")) {
                    output.add(("OK"));
                    output.add(("0,00"));
                    output.add(("0,00"));
                    output.add((formatMysqltoDisplay(roundDoubleandFormat(nuovorate, 8))));
                } else {
                    double contr_CALCOLATO = getControvalore(quant_now, nuovorate, dividi);
                    double spreadtotal = contr - contr_CALCOLATO;
                    output.add(("OK"));
                    output.add((formatMysqltoDisplay(roundDoubleandFormat(spreadtotal, 2))));
                    output.add((formatMysqltoDisplay(roundDoubleandFormat(contr_CALCOLATO, 2))));
                    output.add((formatMysqltoDisplay(rate)));
                }
            }
        } else {
            output.add(("-"));
            output.add(("0,00"));
            output.add(("0,00"));
            output.add(("0,00"));
        }

//        System.out.println("rc.so.util.Utility.calcolaspread2021(5) "+output.toString());
        return output;

    }

    public static String estraiEccezione(Exception ec1) {
        try {
            return ec1.getStackTrace()[0].getMethodName() + " - " + getStackTrace(ec1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ec1.getMessage();

    }

    public static String checkAttribute(HttpSession session, String attribute) {
        try {
            if (session.getAttribute(attribute) != null) {
                return String.valueOf(session.getAttribute(attribute));
            }
        } catch (Exception e) {
        }
        return "";
    }

    public static String sanitize(String input) {
        if (input == null) {
            return null;
        }
        input = input.replaceAll("[^a-zA-Z0-9\\.\\-]", "_");
        input = input.replaceAll("[\r\n]", "");
        return StringEscapeUtils.escapeHtml4(input);
    }

    public static String sanitizePath(String input) {
        input = input.replaceAll("\\.\\./", "")
                     .replaceAll("~", "") 
                     .replaceAll("\\\\", "/");

        input = input.replaceAll("[^a-zA-Z0-9_./-]", "");
        Path sanitizedPath = Paths.get(input).normalize();
        
        return sanitizedPath.toString();
    }

    public static String sanitizeFilename(String filename) {
        return filename.replaceAll("[^a-zA-Z0-9._-]", "_");
    }

    private static void addAttachment(Multipart multipart, byte[] content, String filename) {
        try {
            String contenttype = fileNameMap.getContentTypeFor(filename);
            ByteArrayDataSource source = new ByteArrayDataSource(content, contenttype);
            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName(filename);
            multipart.addBodyPart(messageBodyPart);
        } catch (Exception ex) {
            insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
        }
    }

    public static boolean send_mail_documenti(String idtr, String maildest, ArrayList<Ch_transaction_doc> doc) {
        try {
            Db_Master db1 = new Db_Master(true);
            String pathtemp = db1.getPath("temp");
            String html = db1.getConf("mail.template.inviodoc");

            String host = db1.getPath("mail.host");
            String port = db1.getPath("mail.port");
            String user = db1.getPath("mail.user");
            String pass = db1.getPath("mail.pass");

            db1.closeDB();

            if (html.equals("-")) {
                return false;
            }

            File temp = new File(pathtemp + generaId(75) + "_temp.html");
            FileUtils.writeByteArrayToFile(temp, html.getBytes());
            String content = Files.asCharSource(temp, StandardCharsets.UTF_8).read();
            content = StringUtils.replace(content, "{{ID PRENOTAZIONE}}", idtr);

            Properties props = new Properties();
            props.put("mail.smtp.host", host);
            props.put("mail.smtp.ssl.enable", "true");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.port", port);
            props.put("mail.smtp.timeout", 3000);
            props.put("mail.smtp.connectiontimeout", 3000);
            Session session = getDefaultInstance(
                    props,
                    new javax.mail.Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(user, pass);
                }
            });
            Message message = new MimeMessage(session);
            InternetAddress froms = new InternetAddress(user);
            froms.setPersonal("Noreply Mac2.0");
            message.setFrom(froms);
            message.setRecipients(TO, parse(maildest));
            message.setSubject("Invio documenti transazione di cambio valuta n. " + idtr + " - "
                    + "Sending currency exchange transaction documents no. " + idtr);
            Multipart mp = new MimeMultipart();
            MimeBodyPart mbp1 = new MimeBodyPart();
            mbp1.setContent(content, "text/html");
            mp.addBodyPart(mbp1);

            for (Ch_transaction_doc d1 : doc) {
                if (!d1.getTipodoc().equals("_docrico")) {
                    String base64 = d1.getContent();
                    String name = d1.getNomefile();
                    if (base64.startsWith("FILE[")) {
                        String pa1 = replace(base64, "FILE[", "");
                        File f = new File(pa1);
                        base64 = encodeBase64String(readFileToByteArray(f));
                    }
                    addAttachment(mp, decodeBase64(base64.getBytes()), name);
                }
            }

            message.setContent(mp);
            send(message);
            return true;
        } catch (Exception ex) {
            insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
        }
        return false;
    }
}

class JsonValue {

    String key, value;

    public JsonValue(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
