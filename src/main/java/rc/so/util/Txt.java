/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rc.so.util;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import static com.itextpdf.text.FontFactory.getFont;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.RectangleReadOnly;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import static com.itextpdf.text.pdf.PdfWriter.getInstance;
import rc.so.entity.Branch;
import rc.so.entity.Ch_transaction;
import static rc.so.entity.Ch_transaction.formatType;
import rc.so.entity.Ch_transaction_value;
import rc.so.entity.Client;
import rc.so.entity.Office;
import static rc.so.util.Constant.is_CZ;
import static rc.so.util.Constant.patternnormdate;
import static rc.so.util.Constant.patternsqldate;
import static rc.so.util.Engine.get_branch;
import static rc.so.util.Engine.insertTR;
import static rc.so.util.Utility.fd;
import static rc.so.util.Utility.formatMysqltoDisplay;
import static rc.so.util.Utility.formatStringtoStringDate;
import static rc.so.util.Utility.getHeigth;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import static java.lang.String.valueOf;
import static java.lang.Thread.currentThread;
import java.nio.charset.Charset;
import static java.nio.charset.Charset.forName;
import static java.nio.file.Files.lines;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import static org.apache.commons.codec.binary.Base64.encodeBase64;
import static org.apache.commons.io.FileUtils.readFileToByteArray;
import org.joda.time.DateTime;

/**
 *
 * @author rcosco
 */
public class Txt {

    /**
     *
     */
    public static final int length = 38;

    /**
     *
     */
    public static final String separator = "|";

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

    /**
     *
     * @param pathout
     * @param ma
     * @param tra
     * @param cl
     * @param livalue
     * @return
     */
    public String scontrino_UK(String pathout, Office ma, Ch_transaction tra, Client cl, ArrayList<Ch_transaction_value> livalue) {
        try {
            Branch b1 = get_branch(tra.getFiliale());
//            Users op = Engine.get_user(tra.getUser());
            String outputfile = new DateTime().toString("yyMMddhhmmssSSS") + "_uk_scontrino.txt";
            File txt = new File(pathout + outputfile);
            try (FileOutputStream is = new FileOutputStream(txt)) {
                OutputStreamWriter osw = new OutputStreamWriter(is, "utf-8");
                BufferedWriter w = new BufferedWriter(osw);
                //intestazione
                w.write(prepareString(length, score));
                w.newLine();
                w.write(separator + prepareString(ma.getDe_office(), length - 2, nbsp) + separator);
                w.newLine();
                w.write(separator + prepareString(b1.getDe_branch(), length - 2, nbsp) + separator);
                w.newLine();
                w.write(separator + prepareString(b1.getAdd_via(), length - 2, nbsp) + separator);
                w.newLine();
                w.write(prepareString(length, score));
                w.newLine();
                w.write(prepareString(formatStringtoStringDate(tra.getData(), patternsqldate, patternnormdate), length, nbsp));
                w.newLine();
                //            w.write(prepareString("RECEIPT N. :", tra.getId(), length, nbsp));
//            w.newLine();
w.write(prepareString(length, nbsp));
w.newLine();
w.write(prepareString(formatType(tra.getTipotr()).toUpperCase(), length, false, true, nbsp));
w.newLine();
for (int i = 0; i < livalue.size(); i++) {
    Ch_transaction_value chv = livalue.get(i);
    w.newLine();
    w.write(prepareString("AMOUNT", formatMysqltoDisplay(chv.getQuantita()), length, nbsp));
    w.newLine();
    w.write(prepareString("EXCHANGE RATE", formatMysqltoDisplay(chv.getRate()), length, nbsp));
    w.newLine();
    w.write(prepareString("Currency", chv.getValuta(), length, nbsp));
    w.newLine();
    w.write(prepareString("COMMISSION", formatMysqltoDisplay(chv.getCom_perc()) + percent, length, nbsp));
    w.newLine();
    w.write(prepareString("FIXED FEE", formatMysqltoDisplay(chv.getFx_com()), length, nbsp));
    w.newLine();
    w.write(prepareString("RECEIVED", formatMysqltoDisplay(chv.getNet()), length, nbsp));
    w.newLine();
}   w.write(prepareString(length, dot));
w.newLine();
w.write(prepareString("TOTAL", formatMysqltoDisplay(tra.getPay()), length, nbsp));
w.newLine();
w.write(prepareString(length, score));
w.newLine();
w.write(prepareString(length, nbsp));
w.newLine();
w.write(prepareString(length, nbsp));
w.newLine();
w.write(prepareString("CASHIER:", tra.getUser(), length, nbsp));
w.newLine();
w.write(prepareString(length, nbsp));
w.newLine();
w.write(prepareString(length, nbsp));
w.newLine();
w.write(prepareString(length, "THANK YOU FOR YOUR VISIT AND GOOD BYE!"));
w.newLine();
w.write(prepareString(length, nbsp));
w.newLine();
w.write(prepareString("e-mail:", "info@maccorp.co.uk", length, nbsp));
w.newLine();
boolean bb_sb = false;
if (tra.getTipotr().equals("B")) {
    if (tra.getBb().equals("3") || tra.getBb().equals("4")) {
        bb_sb = true;
    }
} else {
    if (tra.getBb().equals("1") || tra.getBb().equals("2")) {
        bb_sb = true;
    }
}   if (bb_sb) {
    w.newLine();
    w.write(prepareString("Fidelity Code:", tra.getFiliale() + tra.getId(), length, nbsp));
    w.newLine();
}   w.close();
osw.close();
            }

            Font ft_2 = getFont("Courier", "Cp1252", 6.0F, 0);
            File pdf = new File(txt.getPath() + ".pdf");
            int si = 52 + (livalue.size() * 4);
            Rectangle rect = new RectangleReadOnly(150.0F, getHeigth(si));
            Document document = new Document(rect, 0.0F, 0.0F, 0.0F, 0.0F);
            try(OutputStream ou = new FileOutputStream(pdf); InputStream is2 = new FileInputStream(txt);){
            PdfWriter wr = getInstance(document, ou);
            wr.open();
            document.open();
            Charset charset = forName("CP1252");
            BufferedReader readbuffer = new BufferedReader(new InputStreamReader(is2, charset));
            int al = 3;
            PdfPTable table = new PdfPTable(1);
            table.setWidthPercentage(100.0F);
            String linea;
            while ((linea = readbuffer.readLine()) != null) {
                PdfPCell cell2 = new PdfPCell(new Phrase(linea, ft_2));
                cell2.setBorder(0);
                cell2.setHorizontalAlignment(al);
                cell2.setPaddingRight(0.0F);
                table.addCell(cell2);
            }
            document.add(table);
            document.close();
            wr.close();
            ou.close();
            is2.close();
            String base64 = new String(encodeBase64(readFileToByteArray(txt)));
//            String base64 = new String(Base64.encodeBase64(FileUtils.readFileToByteArray(pdf)));
            txt.delete();
            pdf.delete();
            return base64;
        }
        } catch (IOException | DocumentException ex) {
            insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
        }
        return null;
    }

    //16/01

    /**
     *
     * @param pathout
     * @param ma
     * @param tra
     * @param cl
     * @param livalue
     * @param br
     * @return
     */
    public String prericevuta_CZ_BUY(String pathout, Office ma, Ch_transaction tra, Client cl, ArrayList<Ch_transaction_value> livalue, Branch br) {
        
        try {
            String outputfile = new DateTime().toString("yyMMddhhmmssSSS") + "_czb_preric_2502.txt";
            File txt = new File(pathout + outputfile);
            try (FileOutputStream is = new FileOutputStream(txt)) {
                OutputStreamWriter osw = new OutputStreamWriter(is, "utf-8");
                BufferedWriter w = new BufferedWriter(osw);
                w.write(prepareString(length, score));
                w.newLine();
                w.write(prepareString(ma.getDe_office().toUpperCase(), length, false, true, nbsp));
                w.newLine();
                w.write(prepareString("sidlo / official address".toUpperCase(), length, false, true, nbsp));
                w.newLine();
                w.write(prepareString((ma.getAdd_via() + comma + ma.getAdd_cap() + comma + ma.getAdd_city()).toUpperCase(), length, false, true, nbsp));
                w.newLine();
                w.write(prepareString(("IC / company ID: " + ma.getVat()).toUpperCase(), length, false, true, nbsp));
                w.newLine();
                w.newLine();
                w.write(prepareString("provozovna / branch ".toUpperCase() + br.getCod(), length, false, true, nbsp));
                w.newLine();
                w.write(prepareString((br.getAdd_via() + comma + br.getAdd_cap() + nbsp + br.getAdd_city()).toUpperCase(), length, false, true, nbsp));
                w.newLine();

                if (br.getOpening().equals("") || br.getOpening().equals("-")) {
                    
                } else {
                    w.write(prepareString(("OTEVIRACI DOBA / OPENING HOURS:").toUpperCase(), length, false, true, nbsp));
                    w.newLine();
                    w.write(prepareString((br.getOpening()).toUpperCase(), length, false, true, nbsp));
                    w.newLine();
                }
                
                w.write(prepareString("WWW.FOREXCHANGE.CZ", length, false, true, nbsp));
                w.newLine();
                w.write(prepareString("INFO@MACCORP.CZ", length, false, true, nbsp));
                w.newLine();
                w.write(prepareString(length, score));
                w.newLine();
                w.write(prepareString(formatStringtoStringDate(tra.getData(), patternsqldate, patternnormdate), tra.getUser(), length, nbsp));
                w.newLine();
                String typeround = "+";
                if (fd(tra.getRound()) < 0) {
                    typeround = "";
                }
                
                for (int i = 0; i < livalue.size(); i++) {
                    w.write(prepareString(tra.formatType_CZ(tra.getTipotr()).toUpperCase(), length, false, true, nbsp));
                    w.newLine();
                    Ch_transaction_value chv = livalue.get(i);
                    w.write(prepareString("MENA / CURRENCY", chv.getValuta() + " -> CZK", length, nbsp));
                    w.newLine();
                    w.write(prepareString(formatSupporto_CZ(chv.getSupporto()), length, false, true, nbsp));
                    w.newLine();
                    w.write(prepareString("MNOZSTVI / AMOUNT", formatMysqltoDisplay(chv.getQuantita()), length, nbsp));
                    w.newLine();
                    w.write(prepareString("KURZ / EXCHANGE RATE", formatMysqltoDisplay(chv.getRate()), length, nbsp));
                    w.newLine();
                    w.write(prepareString("POPLATEK / FEE", formatMysqltoDisplay(chv.getTot_com()) + " CZK", length, nbsp));
                    w.newLine();
                    w.write(prepareString("ZAOKROUHLENI/ROUND OFF", typeround + formatMysqltoDisplay(chv.getRoundvalue()) + " CZK", length, nbsp));
                    w.newLine();
                    w.newLine();
                    w.write(prepareString("CISTA CASTKA / NET", formatMysqltoDisplay(chv.getNet()) + " CZK", length, nbsp));
                    w.newLine();
                }
                w.newLine();
                w.newLine();
                w.newLine();
                w.write(prepareString("SVYM PODPISEM POTVRZUJI, ZE JSEM TYTO INFORMACE O SMENARENSKEM OBCHODU, OBDRZEL S DOSTATECNYM PREDSTIHEM PRED FINALNIM"
                        + " PROVEDENIM SMENARENSKEHO OBCHODU, A ZE SE VSEMI PODMINKAMI TOHOTO SMENARENSKEHO OBCHODU, UVEDENYMI V PISEMNE PODOBE NA"
                        + " ZVLASTNIM LISTU, KTERY JE NEDILNOU SOUCASTI TOHOTO DOKLADU, BEZVYHRADNE SOUHLASIM.", length, false, true, nbsp));
                
                w.newLine();
                w.newLine();
                w.write(prepareString("BY SIGNING I CONFIRM I RECEIVED THE FULL INFORMATION RELATED TO THE MONEY EXCHANGE"
                        + " TRANSACTION WELL IN ADVANCE BEFORE THE FINAL EXECUTION OF THE DEAL. I HEREBY EXPRESSLY AGREE"
                        + " WITH ALL TERMS AND CONDITIONS I RECEIVED IN WRITTEN FORM ON SEPARATED LIST, WHICH IS INTEGRAL PART OF THIS PRE-RECEIPT.", length, false, true, nbsp));
                w.newLine();
                w.newLine();
                w.newLine();
                
                w.write(prepareString("X __", length, false, true, "_"));
                w.newLine();
                w.write(prepareString("PODPIS / SIGNATURE", length, nbsp));
                w.close();
                osw.close();
            }
            String base64 = new String(encodeBase64(readFileToByteArray(txt)));
            //txt.delete();
            return base64;

        } catch (IOException ex) {
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
     * @return
     */
    public String prericevuta_CZ_SELL(String pathout, Office ma, Ch_transaction tra, Client cl, ArrayList<Ch_transaction_value> livalue, Branch br) {
        try {
            String outputfile = new DateTime().toString("yyMMddhhmmssSSS") + "_czS_preric_2502.txt";
            File txt = new File(pathout + outputfile);
            try (FileOutputStream is = new FileOutputStream(txt)) {
                OutputStreamWriter osw = new OutputStreamWriter(is, "utf-8");
                BufferedWriter w = new BufferedWriter(osw);
                w.write(prepareString(length, score));
                w.newLine();
                w.write(prepareString(ma.getDe_office().toUpperCase(), length, false, true, nbsp));
                w.newLine();
                w.write(prepareString("sidlo / official address".toUpperCase(), length, false, true, nbsp));
                w.newLine();
                w.write(prepareString((ma.getAdd_via() + comma + ma.getAdd_cap() + comma + ma.getAdd_city()).toUpperCase(), length, false, true, nbsp));
                w.newLine();
                w.write(prepareString(("IC / company ID: " + ma.getVat()).toUpperCase(), length, false, true, nbsp));
                w.newLine();
                w.newLine();
                w.write(prepareString("provozovna / branch ".toUpperCase() + br.getCod(), length, false, true, nbsp));
                w.newLine();
                w.write(prepareString((br.getAdd_via() + comma + br.getAdd_cap() + nbsp + br.getAdd_city()).toUpperCase(), length, false, true, nbsp));
                w.newLine();
                if (br.getOpening().equals("") || br.getOpening().equals("-")) {
                    
                } else {
                    w.write(prepareString(("OTEVIRACI DOBA / OPENING HOURS:").toUpperCase(), length, false, true, nbsp));
                    w.newLine();
                    w.write(prepareString((br.getOpening()).toUpperCase(), length, false, true, nbsp));
                    w.newLine();
                }
                w.write(prepareString("WWW.FOREXCHANGE.CZ", length, false, true, nbsp));
                w.newLine();
                w.write(prepareString("INFO@MACCORP.CZ", length, false, true, nbsp));
                w.newLine();
                w.write(prepareString(length, score));
                w.newLine();
                w.write(prepareString(formatStringtoStringDate(tra.getData(), patternsqldate, patternnormdate), tra.getUser(), length, nbsp));
                w.newLine();
                
                String typeround = "+";
                if (fd(tra.getRound()) < 0) {
                    typeround = "";
                }
                
                for (int i = 0; i < livalue.size(); i++) {
                    w.write(prepareString(tra.formatType_CZ(tra.getTipotr()).toUpperCase(), length, false, true, nbsp));
                    w.newLine();
                    Ch_transaction_value chv = livalue.get(i);
                    w.write(prepareString("MENA / CURRENCY", "CZK -> " + chv.getValuta(), length, nbsp));
                    w.newLine();
                    w.write(prepareString(formatSupporto_CZ(chv.getSupporto()), length, false, true, nbsp));
                    w.newLine();
                    w.write(prepareString("MNOZSTVI / AMOUNT", formatMysqltoDisplay(chv.getQuantita()), length, nbsp));
                    w.newLine();
                    w.write(prepareString("KURZ / EXCHANGE RATE", formatMysqltoDisplay(chv.getRate()), length, nbsp));
                    w.newLine();
                    w.write(prepareString("POPLATEK / FEE", formatMysqltoDisplay(chv.getTot_com()) + " CZK", length, nbsp));
                    w.newLine();
                    w.write(prepareString("ZAOKROUHLENI/ROUND OFF", typeround + formatMysqltoDisplay(chv.getRoundvalue()) + " CZK", length, nbsp));
                    w.newLine();
                    w.newLine();
                    w.write(prepareString("CISTA CASTKA / NET", formatMysqltoDisplay(chv.getNet()) + " CZK", length, nbsp));
                    w.newLine();
                }
                w.newLine();
                w.newLine();
                w.newLine();
                w.write(prepareString("SVYM PODPISEM POTVRZUJI, ZE JSEM TYTO INFORMACE O SMENARENSKEM OBCHODU, OBDRZEL S DOSTATECNYM PREDSTIHEM PRED FINALNIM"
                        + " PROVEDENIM SMENARENSKEHO OBCHODU, A ZE SE VSEMI PODMINKAMI TOHOTO SMENARENSKEHO OBCHODU, UVEDENYMI V PISEMNE PODOBE NA"
                        + " ZVLASTNIM LISTU, KTERY JE NEDILNOU SOUCASTI TOHOTO DOKLADU, BEZVYHRADNE SOUHLASIM.", length, false, true, nbsp));
                
                w.newLine();
                w.newLine();
                w.write(prepareString("BY SIGNING I CONFIRM I RECEIVED THE FULL INFORMATION RELATED TO THE MONEY EXCHANGE"
                        + " TRANSACTION WELL IN ADVANCE BEFORE THE FINAL EXECUTION OF THE DEAL. I HEREBY EXPRESSLY AGREE"
                        + " WITH ALL TERMS AND CONDITIONS I RECEIVED IN WRITTEN FORM ON SEPARATED LIST, WHICH IS INTEGRAL PART OF THIS PRE-RECEIPT.", length, false, true, nbsp));
                w.newLine();
                w.newLine();
                w.newLine();
                
                w.write(prepareString("X __", length, false, true, "_"));
                w.newLine();
                w.write(prepareString("PODPIS / SIGNATURE", length, nbsp));
                w.close();
                osw.close();
            }
            String base64 = new String(encodeBase64(readFileToByteArray(txt)));
            //txt.delete();
            return base64;

        } catch (IOException ex) {
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
     * @param storno
     * @return
     */
    public String ricevuta_CZ_SELL(String pathout, Office ma, Ch_transaction tra, Client cl, ArrayList<Ch_transaction_value> livalue, Branch br, boolean storno) {
        try {
            String outputfile = new DateTime().toString("yyMMddhhmmssSSS") + "_czS_scon_2502.txt";
            File txt = new File(pathout + outputfile);
            try (FileOutputStream is = new FileOutputStream(txt)) {
                OutputStreamWriter osw = new OutputStreamWriter(is, "utf-8");
                BufferedWriter w = new BufferedWriter(osw);
                w.write(prepareString(length, score));
                w.newLine();
                w.write(prepareString(ma.getDe_office().toUpperCase(), length, false, true, nbsp));
                w.newLine();
                w.write(prepareString("sidlo / official address".toUpperCase(), length, false, true, nbsp));
                w.newLine();
                w.write(prepareString((ma.getAdd_via() + comma + ma.getAdd_cap() + comma + ma.getAdd_city()).toUpperCase(), length, false, true, nbsp));
                w.newLine();
                w.write(prepareString(("IC / company ID: " + ma.getVat()).toUpperCase(), length, false, true, nbsp));
                w.newLine();
                w.newLine();
                w.write(prepareString("provozovna / branch ".toUpperCase() + br.getCod(), length, false, true, nbsp));
                w.newLine();
                w.write(prepareString((br.getAdd_via() + comma + br.getAdd_cap() + nbsp + br.getAdd_city()).toUpperCase(), length, false, true, nbsp));
                w.newLine();
                if (br.getOpening().equals("") || br.getOpening().equals("-")) {
                    
                } else {
                    w.write(prepareString(("OTEVIRACI DOBA / OPENING HOURS:").toUpperCase(), length, false, true, nbsp));
                    w.newLine();
                    w.write(prepareString((br.getOpening()).toUpperCase(), length, false, true, nbsp));
                    w.newLine();
                }
                w.write(prepareString("WWW.FOREXCHANGE.CZ", length, false, true, nbsp));
                w.newLine();
                w.write(prepareString("INFO@MACCORP.CZ", length, false, true, nbsp));
                w.newLine();
                w.write(prepareString(length, score));
                w.newLine();
                w.write(prepareString(formatStringtoStringDate(tra.getData(), patternsqldate, patternnormdate), tra.getUser(), length, nbsp));
                w.newLine();
                String typeround = "+";
                if (fd(tra.getRound()) < 0) {
                    typeround = "";
                }
                for (int i = 0; i < livalue.size(); i++) {
                    w.write(prepareString(tra.formatType_CZ(tra.getTipotr()).toUpperCase(), length, false, true, nbsp));
                    w.newLine();
                    Ch_transaction_value chv = livalue.get(i);
                    w.write(prepareString("MENA / CURRENCY", "CZK -> " + chv.getValuta(), length, nbsp));
                    w.newLine();
                    w.write(prepareString(formatSupporto_CZ(chv.getSupporto()), length, false, true, nbsp));
                    w.newLine();
                    w.write(prepareString("MNOZSTVI / AMOUNT", formatMysqltoDisplay(chv.getQuantita()), length, nbsp));
                    w.newLine();
                    w.write(prepareString("KURZ / EXCHANGE RATE", formatMysqltoDisplay(chv.getRate()), length, nbsp));
                    w.newLine();
                    w.write(prepareString("POPLATEK / FEE", formatMysqltoDisplay(chv.getTot_com()) + " CZK", length, nbsp));
                    w.newLine();
                    w.write(prepareString("ZAOKROUHLENI/ROUND OFF", typeround + formatMysqltoDisplay(chv.getRoundvalue()) + " CZK", length, nbsp));
                    w.newLine();
                    w.newLine();
                    w.write(prepareString("CISTA CASTKA / NET", formatMysqltoDisplay(chv.getNet()) + " CZK", length, nbsp));
                    w.newLine();
                }
                w.newLine();
                w.newLine();
                w.newLine();
                w.write(prepareString("VESKERE PODMINKY TOHOTO SMENARENSKEHO OBCHODU, VCETNE VYCTU PRAV ZAKAZNIKA A POSTUPU ODSTOUPENI OD SMENARENSKEHO"
                        + " OBCHODU, JSOU UVEDENE NA ZVLASTNIM LISTU, KTERY JE NEDILNOU SOUCASTI TOHOTO DOKLADU O SMENARENSKEM OBCHODU.", length, false, true, nbsp));
                
                w.newLine();
                w.newLine();
                w.write(prepareString("ALL TERMS AND CONDITIONS, RIGHTS OF CLIENT AND PROCESS OF REFUND OF THIS CHANGE TRANSACTION, "
                        + "ARE STATED IN WRITTEN FORM ON SEPARATED LIST, WHICH IS INTEGRAL PART OF THIS RECEIPT OF THIS CHANGE TRANSACTION.", length, false, true, nbsp));
                
                w.close();
                osw.close();
            }
            String base64 = new String(encodeBase64(readFileToByteArray(txt)));
            //txt.delete();
            return base64;
        } catch (IOException ex) {
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
     * @param storno
     * @return
     */
    public String ricevuta_CZ_BUY(String pathout, Office ma, Ch_transaction tra, Client cl, ArrayList<Ch_transaction_value> livalue, Branch br, boolean storno) {
        try {
            String outputfile = new DateTime().toString("yyMMddhhmmssSSS") + "_czb_scon_2502.txt";
            File txt = new File(pathout + outputfile);
            try (FileOutputStream is = new FileOutputStream(txt)) {
                OutputStreamWriter osw = new OutputStreamWriter(is, "utf-8");
                BufferedWriter w = new BufferedWriter(osw);
                w.write(prepareString(length, score));
                w.newLine();
                w.write(prepareString(ma.getDe_office().toUpperCase(), length, false, true, nbsp));
                w.newLine();
                w.write(prepareString("sidlo / official address".toUpperCase(), length, false, true, nbsp));
                w.newLine();
                w.write(prepareString((ma.getAdd_via() + comma + ma.getAdd_cap() + comma + ma.getAdd_city()).toUpperCase(), length, false, true, nbsp));
                w.newLine();
                w.write(prepareString(("IC / company ID: " + ma.getVat()).toUpperCase(), length, false, true, nbsp));
                w.newLine();
                w.newLine();
                w.write(prepareString("provozovna / branch ".toUpperCase() + br.getCod(), length, false, true, nbsp));
                w.newLine();
                w.write(prepareString((br.getAdd_via() + comma + br.getAdd_cap() + nbsp + br.getAdd_city()).toUpperCase(), length, false, true, nbsp));
                w.newLine();
                if (br.getOpening().equals("") || br.getOpening().equals("-")) {
                    
                } else {
                    w.write(prepareString(("OTEVIRACI DOBA / OPENING HOURS:").toUpperCase(), length, false, true, nbsp));
                    w.newLine();
                    w.write(prepareString((br.getOpening()).toUpperCase(), length, false, true, nbsp));
                    w.newLine();
                }
                w.write(prepareString("WWW.FOREXCHANGE.CZ", length, false, true, nbsp));
                w.newLine();
                w.write(prepareString("INFO@MACCORP.CZ", length, false, true, nbsp));
                w.newLine();
                w.write(prepareString(length, score));
                w.newLine();
                w.write(prepareString(formatStringtoStringDate(tra.getData(), patternsqldate, patternnormdate), tra.getUser(), length, nbsp));
                w.newLine();
                String typeround = "+";
                if (fd(tra.getRound()) < 0) {
                    typeround = "";
                }
                for (int i = 0; i < livalue.size(); i++) {
                    w.write(prepareString(tra.formatType_CZ(tra.getTipotr()).toUpperCase(), length, false, true, nbsp));
                    w.newLine();
                    Ch_transaction_value chv = livalue.get(i);
                    w.write(prepareString("MENA / CURRENCY", chv.getValuta() + " -> CZK", length, nbsp));
                    w.newLine();
                    w.write(prepareString(formatSupporto_CZ(chv.getSupporto()), length, false, true, nbsp));
                    w.newLine();
                    w.write(prepareString("MNOZSTVI / AMOUNT", formatMysqltoDisplay(chv.getQuantita()), length, nbsp));
                    w.newLine();
                    w.write(prepareString("KURZ / EXCHANGE RATE", formatMysqltoDisplay(chv.getRate()), length, nbsp));
                    w.newLine();
                    w.write(prepareString("POPLATEK / FEE", formatMysqltoDisplay(chv.getTot_com()) + " CZK", length, nbsp));
                    w.newLine();
                    w.write(prepareString("ZAOKROUHLENI/ROUND OFF", typeround + formatMysqltoDisplay(chv.getRoundvalue()) + " CZK", length, nbsp));
                    w.newLine();
                    w.newLine();
                    w.write(prepareString("CISTA CASTKA / NET", formatMysqltoDisplay(chv.getNet()) + " CZK", length, nbsp));
                    w.newLine();
                }
                w.newLine();
                w.newLine();
                w.newLine();
                w.write(prepareString("VESKERE PODMINKY TOHOTO SMENARENSKEHO OBCHODU, VCETNE VYCTU PRAV ZAKAZNIKA A POSTUPU ODSTOUPENI OD SMENARENSKEHO"
                        + " OBCHODU, JSOU UVEDENE NA ZVLASTNIM LISTU, KTERY JE NEDILNOU SOUCASTI TOHOTO DOKLADU O SMENARENSKEM OBCHODU.", length, false, true, nbsp));
                
                w.newLine();
                w.newLine();
                w.write(prepareString("ALL TERMS AND CONDITIONS, RIGHTS OF CLIENT AND PROCESS OF REFUND OF THIS CHANGE TRANSACTION, "
                        + "ARE STATED IN WRITTEN FORM ON SEPARATED LIST, WHICH IS INTEGRAL PART OF THIS RECEIPT OF THIS CHANGE TRANSACTION.", length, false, true, nbsp));
                
                w.close();
                osw.close();
            }
            String base64 = new String(encodeBase64(readFileToByteArray(txt)));
            //txt.delete();
            return base64;
        } catch (IOException ex) {
            insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
        }
        return null;
    }

    private String prepareString(int max, String separator) {
        String out = "";
        while (out.length() < max) {
            out = separator + out;
        }
        return out;
    }

    private String prepareString(String val, int max, boolean left, boolean right, String separator) {
        val = val.trim();
        if (val.length() > max) {
            return setWordWrap(val, max);
        }
        if (val.length() == max) {
            return val;
        }

        if (left) {
            while (val.length() < max) {
                val = separator + val;
            }
        } else if (right) {
            while (val.length() < max) {
                val = val + separator;
            }
        } else {
            while (val.length() < max) {
                val = val + separator;
            }
        }
        return val;

    }

    private static String setWordWrap(String lineaToWordWrap, int lenMax) {

        String returnValue;
        String ultimoCarattere;
        int indiceWrap = 0;
        int i;
// controllo se raggiunta lunghezza max
        if (lineaToWordWrap.length() > lenMax) {
// ciclo per l'intera lunghezza massima
            for (i = 0; i < lenMax; i++) {
// recupero nuovo indice
                indiceWrap = lenMax - i;
// recupera carattere al limite
                ultimoCarattere = valueOf(lineaToWordWrap.charAt(indiceWrap));
// se il carattere è uguale a spazio
                if (ultimoCarattere.equals(" ")) {
                    break;
                }
            } // fine ciclo
// controllo se raggiunto limite eseguo taglio anche se non ho trovato lo spazio
            if (i == lenMax) {
// recupero linea al limite
                returnValue = lineaToWordWrap.substring(0, lenMax).trim();
// aggiungo la successiva riga limitata a sua volta
                returnValue = returnValue + "\r\n" + setWordWrap(lineaToWordWrap.substring(lenMax).trim(), lenMax);
            } else {
// recupero riga limitata
                returnValue = lineaToWordWrap.substring(0, indiceWrap).trim();
// aggiungo la successiva riga limitata a sua volta
                returnValue = returnValue + "\r\n" + setWordWrap(lineaToWordWrap.substring(indiceWrap).trim(), lenMax);
            }
        } else {
// ritorno la linea senza alcuna limitazione
            returnValue = lineaToWordWrap;
        } // fine controllo se raggiunta lunghezza max
        return returnValue;
    }

    private String prepareString(String val, int max, String separator) {
        val = val.trim();
        if (val.length() > max) {
            val = val.substring(0, max);
        }
        if (val.length() == max) {
            return val;
        }

        int index = max / 2;

        String part1 = val.substring(0, val.length() / 2);
        String part2 = val.substring(val.length() / 2);

        while (part1.length() < index) {
            part1 = separator + part1;
        }
        while (part2.length() < index) {
            part2 = part2 + separator;
        }
        val = part1 + part2;
        return val;
    }

    private String prepareString(String s1, String s2, int max, String separator) {
        s1 = s1.trim();
        s2 = s2.trim();
        if ((s1.length() + s2.length()) < max) {
            int le = max - (s1.length() + s2.length());
            String out = "";
            while (out.length() < le) {
                out = out + separator;
            }
            return s1 + out + s2;
        }

        return s1;
    }

    private String formatSupporto_CZ(String su01) {
        switch (su01) {
            case "01":
                return "HOTOVOST / CASH";
            case "03":
                return "TRAVELLERS CHEQUE / TC";
            case "04":
                return "CASH ADVANCE / CC";
            default:
                return su01;
        }
    }

    /**
     *
     * @param f1
     * @return
     */
    public static boolean printFile(File f1) {
        try {
            FileOutputStream os;

            if (is_CZ) {
                os = new FileOutputStream("LPT3");
            } else {
                os = new FileOutputStream("LPT1");
            }
            try (PrintStream ps = new PrintStream(os)) {
                AtomicInteger i = new AtomicInteger(0);
                lines(f1.toPath()).forEach(re -> {
                    i.addAndGet(1);
                    if (re.trim().equals("")) {
                        ps.print("         ");
                    } else {
                        ps.print(re);
                    }
                    ps.print("\r\n");
                }); ps.print("\r\n");
                ps.print("\r\n");
                ps.print("\r\n");
                ps.print("\r\n");
                ps.print("\r\n");
                ps.print("\r\n");
                ps.print("\r\n");
                ps.print("\r\n");
                ps.print("\r\n");
                ps.print("\r\n");
            }
            return true;
        } catch (IOException ex) {
            insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
            return false;
        }

    }

}
