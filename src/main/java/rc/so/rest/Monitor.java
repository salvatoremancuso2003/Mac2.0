/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rc.so.rest;

import rc.so.db.Db_Master;
import rc.so.entity.Branch;
import static rc.so.util.Constant.is_IT;
import static rc.so.util.Constant.patterndir;
import static rc.so.util.Constant.patternnormdate;
import static rc.so.util.Engine.insertTR;
import static rc.so.util.Utility.fd;
import java.io.File;
import static java.io.File.separator;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import static java.lang.Integer.parseInt;
import static java.lang.Math.ceil;
import static java.lang.String.format;
import static java.lang.Thread.currentThread;
import java.util.ArrayList;
import java.util.StringTokenizer;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import static org.apache.commons.lang3.StringUtils.deleteWhitespace;
import static org.apache.commons.lang3.StringUtils.replace;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.joda.time.DateTime;
import static org.joda.time.format.DateTimeFormat.forPattern;
import org.joda.time.format.DateTimeFormatter;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 * @author rcosco
 */
public class Monitor {

//    private static String formatValue(String value) {
//        double d1;
//        try {
//            d1 = Utility.fd(value);
//        } catch (NumberFormatException e) {
//            d1 = 0.000;
//        }
//        DecimalFormat formatter = (DecimalFormat) NumberFormat.getCurrencyInstance(Locale.ITALY);
//        DecimalFormatSymbols symbols = formatter.getDecimalFormatSymbols();
//        symbols.setCurrencySymbol(""); // Don't use null.
//        formatter.setDecimalFormatSymbols(symbols);
//        return value;
//    }
//
//    private static String addStandard(String buy_std, String cambio_bce) {
//        double d_rifbce = Utility.fd(cambio_bce);
//        double d_standard = Utility.fd(buy_std);
//        double tot_st = d_rifbce * (100 + d_standard) / 100;
//
////        tot_st = new BigDecimal(tot_st).setScale(3, RoundingMode.HALF_UP).doubleValue();
////        return tot_st + "";
//        return roundDoubleandFormat(tot_st, 3);
//    }
//    
//    
//    
//    
//    
//    
//    
//    public static boolean generateFile(String path, String filiale, ArrayList<Branch> allbr) {
//        Db_Master dbm = new Db_Master();
//        String host = dbm.getConf("path.monitor.host");
//        int port = parseIntR(dbm.getConf("path.monitor.port"));
//        String usr = dbm.getConf("path.monitor.user");
//        String psw = dbm.getConf("path.monitor.psw");
//        String dir = dbm.getConf("path.monitor.dir");
//        String config = dbm.getConf("path.monitor.config");
//        ArrayList<String[]> al = dbm.getValuteForMonitor(filiale);
//        dbm.closeDB();
//
//        if (al.size() > 0) {
//            if (filiale.equals("---")) {
//                boolean es1 = true;
//                for (int i = 0; i < allbr.size(); i++) {
//                    ArrayList<String[]> alinside = new ArrayList<>();
//                    String fil = allbr.get(i).getCod();
//                    for (int j = 0; j < al.size(); j++) {
//                        if (fil.equals(al.get(j)[9])) {
//                            alinside.add(al.get(j));
//                        }
//                    }
//                    if (alinside.size() > 0) {
//                        boolean es = printFile(path, fil, alinside, host, port, usr, psw, dir, config);
//                        if (!es) {
//                            es1 = false;
//                            break;
//                        }
//                    }
//                }
//                return es1;
//            } else {
//                return printFile(path, filiale, al, host, port, usr, psw, dir, config);
//            }
//        }
//        return false;
//    }
//
//    private static boolean printFile(String path, String filiale, ArrayList<String[]> al,
//            String host, int port, String usr, String psw, String dir, String config) {
//        try {
//            String directory_str = path + new DateTime().toString(patterndir);
//            new File(directory_str).mkdirs();
//            File xml_output = new File(directory_str + File.separator + filiale + ".xml");
//            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
//            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
//            Document doc = docBuilder.newDocument();
//            Element rootElement = doc.createElement("valute");
//            doc.appendChild(rootElement);
//            DateTime d = new DateTime();
//            String dataMillis = d.toString(patternnormdate);
//            DateTimeFormatter df = DateTimeFormat.forPattern(patternnormdate);
//            DateTime d2 = df.parseDateTime(dataMillis);
//            Element timestamp = doc.createElement("timestamp");
//            dataMillis = (d2.getMillis() + "").substring(0, 10);
//            timestamp.appendChild(doc.createTextNode(dataMillis));
//            rootElement.appendChild(timestamp);
//            for (int i = 0; i < al.size(); i++) {
//                if (!addStandard(al.get(i)[5], al.get(i)[2]).equals("0.0") && !addStandard(al.get(i)[8], al.get(i)[2]).equals("0.0")) {
//                    Element valuta = doc.createElement("valuta");
//                    rootElement.appendChild(valuta);
//                    // firstname elements
//                    Element code = doc.createElement("code");
//                    code.appendChild(doc.createTextNode(al.get(i)[0].toUpperCase()));
//                    valuta.appendChild(code);
//                    // lastname elements
//                    Element immagine = doc.createElement("immagine");
//                    immagine.appendChild(doc.createCDATASection(StringUtils.replace(config, "###", al.get(i)[0].toLowerCase())));//SETTARE VALORE
//                    valuta.appendChild(immagine);
//                    // nickname elements
//                    Element nome = doc.createElement("nome");
//                    nome.appendChild(doc.createTextNode(al.get(i)[1]));
//                    valuta.appendChild(nome);
//                    // salary elements
//                    Element buy = doc.createElement("buy");     // VALUTA
//                    if (al.get(i)[4].equals("0")) {
//                        buy.appendChild(doc.createTextNode(formatValue(addStandard(al.get(i)[5], al.get(i)[2]))));
//                    } else {
//                        buy.appendChild(doc.createTextNode(al.get(i)[4]));
//                    }
//                    valuta.appendChild(buy);
//                    // salary elements
//                    Element sell = doc.createElement("sell");   // VALUTA
//                    if (al.get(i)[7].equals("0")) {
//                        sell.appendChild(doc.createTextNode(formatValue(addStandard(al.get(i)[8], al.get(i)[2]))));
//                    } else {
//                        sell.appendChild(doc.createTextNode(al.get(i)[6]));
//                    }
//                    valuta.appendChild(sell);
//                    // salary elements
//                    Element alwaysVisible = doc.createElement("alwaysVisible");
//                    alwaysVisible.appendChild(doc.createTextNode("true"));
//                    valuta.appendChild(alwaysVisible);
//                }
//            }
//
//            TransformerFactory transformerFactory = TransformerFactory.newInstance();
//            Transformer transformer = transformerFactory.newTransformer();
//            DOMSource source = new DOMSource(doc);
//            StreamResult result = new StreamResult(xml_output);
//            transformer.transform(source, result);
//            if (!Constant.test) {
//                FTPClient ftpClient = ftpConnect(host, port, usr, psw);
//                if (ftpClient != null) {
//                    ftpChangeDir(ftpClient, dir);
//                    boolean es = ftpUploadFile(ftpClient, xml_output);
//                    ftpDisconnect(ftpClient);
//                    return es;
//                }
//            }
//
//            return true;
//        } catch (ParserConfigurationException | TransformerException ex) {
//            insertTR("E", "System", Thread.currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
//        }
//        return false;
//    }
//    
//    
//    
//    
    private static String formatValue(String value) {
        if (is_IT) {
            return value;
        } else {
            return formatMysqltoDisplayMONITOR(value);
        }
    }

    private static String addStandard(String buy_std, String cambio_bce) {
        double d_rifbce = fd(cambio_bce);
        double d_standard = fd(buy_std);
        double tot_st = d_rifbce * (100 + d_standard) / 100;
        return roundDoubleandFormat(tot_st, 3);
    }

    private static String formatMysqltoDisplayMONITOR(String ing) {

        String decimal = ".";
        String thousand = ",";

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
            String dec = ing.split("\\.")[1];
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

    private static String[] splitStringEvery(String s, int interval) {
        int arrayLength = (int) Math.ceil((double) s.length() / interval);
        String[] result = new String[arrayLength];
        int j = 0;
        int lastIndex = result.length - 1;
        
        for (int i = 0; i < lastIndex; i++) {
            result[i] = s.substring(j, j + interval);
            j += interval;
        }
        result[lastIndex] = s.substring(j);
        
        return result;
    }
    
    

    private static boolean generateFile(String path, String filiale, ArrayList<Branch> allbr, FTPClient ftpClient, String config) {
        Db_Master dbm = new Db_Master();
        ArrayList<String[]> al = dbm.getValuteForMonitor(filiale);
        dbm.closeDB();

        
        if (!al.isEmpty()) {

            if (filiale.equals("---")) {
                boolean es1 = true;
                if(allbr != null && !allbr.isEmpty()){
                for (int i = 0; i < allbr.size(); i++) {
                    ArrayList<String[]> alinside = new ArrayList<>();
                    String fil = allbr.get(i).getCod();
                    for (int j = 0; j < al.size(); j++) {
                        if (fil.equals(al.get(j)[9])) {
                            alinside.add(al.get(j));
                        }
                       
                    }
                    if (!alinside.isEmpty()) {
                        boolean es = printFile(path, fil, alinside, config, ftpClient);
                        if (!es) {
                            es1 = false;
                            break;
                        }
                    }
                }
                return es1;
            } else {
                return printFile(path, filiale, al, config, ftpClient);
            }
        }

        }
        return false;
    }

    private static boolean printFile(String path, String filiale, ArrayList<String[]> al, String config, FTPClient ftpClient) {
        try {
            String directory_str = path + new DateTime().toString(patterndir);
            new File(directory_str).mkdirs();
            File xml_output = new File(directory_str + separator + filiale + ".xml");
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.newDocument();
            Element rootElement = doc.createElement("valute");
            doc.appendChild(rootElement);
            DateTime d = new DateTime();
            String dataMillis = d.toString(patternnormdate);
            DateTimeFormatter df = forPattern(patternnormdate);
            DateTime d2 = df.parseDateTime(dataMillis);
            Element timestamp = doc.createElement("timestamp");
            dataMillis = (d2.getMillis() + "").substring(0, 10);
            timestamp.appendChild(doc.createTextNode(dataMillis));
            rootElement.appendChild(timestamp);
            for (int i = 0; i < al.size(); i++) {
                if (!addStandard(al.get(i)[5], al.get(i)[2]).equals("0.0") && !addStandard(al.get(i)[8], al.get(i)[2]).equals("0.0")) {
                    
                    //NUOVO CONTROLLO
                    if (al.get(i)[10].equals("0") && al.get(i)[11].equals("0")) {
                        continue;
                    }
                    
                    Element valuta = doc.createElement("valuta");
                    rootElement.appendChild(valuta);
                    // firstname elements
                    Element code = doc.createElement("code");
                    code.appendChild(doc.createTextNode(al.get(i)[0].toUpperCase()));
                    valuta.appendChild(code);
                    // lastname elements
                    Element immagine = doc.createElement("immagine");
                    immagine.appendChild(doc.createCDATASection(replace(config, "###", al.get(i)[0].toLowerCase())));//SETTARE VALORE
                    valuta.appendChild(immagine);
                    // nickname elements
                    Element nome = doc.createElement("nome");
                    nome.appendChild(doc.createTextNode(al.get(i)[1]));
                    valuta.appendChild(nome);
                    
                    Element buy = doc.createElement("buy");     // VALUTA
                    if (al.get(i)[10].equals("0")) {
                        buy.appendChild(doc.createTextNode(""));
                    } else {
                        if (al.get(i)[4].equals("0")) {
                            buy.appendChild(doc.createTextNode(formatValue(addStandard(al.get(i)[5], al.get(i)[2]))));
                        } else {
                            buy.appendChild(doc.createTextNode(al.get(i)[4]));
                        }
                    }
                    valuta.appendChild(buy);

                    // salary elements
                    Element sell = doc.createElement("sell");   // VALUTA
                    if (al.get(i)[11].equals("0")) {
                        sell.appendChild(doc.createTextNode(""));
                    } else {
                        if (al.get(i)[7].equals("0")) {
                            sell.appendChild(doc.createTextNode(formatValue(addStandard(al.get(i)[8], al.get(i)[2]))));
                        } else {
                            sell.appendChild(doc.createTextNode(al.get(i)[6]));
                        }
                    }
                    valuta.appendChild(sell);
                    // salary elements
                    Element alwaysVisible = doc.createElement("alwaysVisible");
                    alwaysVisible.appendChild(doc.createTextNode("true"));
                    valuta.appendChild(alwaysVisible);
                }
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(xml_output);
            transformer.transform(source, result);
            boolean es = ftpUploadFile(ftpClient, xml_output);
            return es;
        } catch (ParserConfigurationException | TransformerException ex) {
            insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
        }
        return false;
    }

    private static String roundDoubleandFormat(double d, int scale) {
        return replace(format("%." + scale + "f", d), ",", ".");
    }

    private static int parseIntR(String value) {
        value = value.replaceAll("-", "").trim();
        if (value.contains(".")) {
            StringTokenizer st = new StringTokenizer(value, ".");
            value = st.nextToken();
        }
        int d1;
        try {
            d1 = parseInt(value);
        } catch (NumberFormatException ex) {
            d1 = 0;
        }
        return d1;
    }

    private static FTPClient ftpConnect(String server, int port, String user, String pass) {
        try {
            FTPClient ftpClient = new FTPClient();
            ftpClient.connect(server, port);
            ftpClient.setDefaultTimeout(2000);
            ftpClient.enterLocalPassiveMode();
            boolean logi = ftpClient.login(user, pass);
            if (logi) {
                if (ftpClient.isConnected()) {
                    return ftpClient;
                }
            }
        } catch (IOException ex) {
            insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
        }
        return null;
    }

    private static boolean ftpChangeDir(FTPClient ftpClient, String dir) {
        if (ftpClient.isConnected()) {
            try {
                boolean es = ftpClient.changeWorkingDirectory(dir);
                return es;
            } catch (IOException ex) {
                insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
            }
        }
        return false;
    }

    private static boolean ftpUploadFile(FTPClient ftpClient, File fileup) {
        try {
            String firstRemoteFile = deleteWhitespace(fileup.getName());
            boolean done;
            try (InputStream inputStream = new FileInputStream(fileup)) {
                done = ftpClient.storeFile(firstRemoteFile, inputStream);
            }
            if (done) {
                long originalsize = fileup.length();
                FTPFile[] filenames = ftpClient.listFiles(firstRemoteFile);
                for (FTPFile filename : filenames) {
                    long destsize = filename.getSize();
                    long perce = originalsize * 5 / 100;
                    long range = originalsize - perce;
                    if (destsize > range) {
                        return true;
                    }
                }
            }
        } catch (FileNotFoundException ex) {
            insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
        } catch (IOException ex) {
            insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
        }
        return false;
    }

    private static boolean ftpDisconnect(FTPClient ftpClient) {
        try {
            if (ftpClient.isConnected()) {
                ftpClient.logout();
                ftpClient.disconnect();
            }
            return true;
        } catch (IOException ex) {
            insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
        }
        return false;
    }

    /**
     *
     * @param path
     * @param branch
     * @param brl
     * @return
     */
    public static boolean uploadMonitor(String path, String branch, ArrayList<Branch> brl) {

        String usr = "maccorpukXml";
        String psw = "maccorpuk2013";
        String host = "play.hddsvision.it";
        int port = 21;
        String dir = "valuteWidget";
        String config = "/mnt/usbdrive/ftp/palinsesto/valuteWidget_flags/###.png";

        if (is_IT) {
            Db_Master dbm = new Db_Master();
            host = dbm.getConf("path.monitor.host");
            port = parseIntR(dbm.getConf("path.monitor.port"));
            usr = dbm.getConf("path.monitor.user");
            psw = dbm.getConf("path.monitor.psw");
            dir = dbm.getConf("path.monitor.dir");
            config = dbm.getConf("path.monitor.config");
            dbm.closeDB();
        }

        boolean es = false;
        FTPClient ftpClient = ftpConnect(host, port, usr, psw);
        if (ftpClient != null) {
            ftpChangeDir(ftpClient, dir);
            if (brl != null) {
                es = generateFile(path, "---", brl, ftpClient, config);
            } else {
                es = generateFile(path, branch, brl, ftpClient, config);
            }
            ftpDisconnect(ftpClient);
        }
        return es;

    }

}
