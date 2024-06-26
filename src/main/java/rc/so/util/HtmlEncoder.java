package rc.so.util;

import static rc.so.util.Engine.insertTR;
import static rc.so.util.Utility.generaId;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import static java.lang.Character.UnicodeBlock.BASIC_LATIN;
import static java.lang.Character.UnicodeBlock.of;
import static java.lang.Character.charCount;
import static java.lang.Character.codePointAt;
import static java.lang.Integer.toHexString;
import static java.lang.Thread.currentThread;
import static java.nio.charset.Charset.forName;
import static org.apache.commons.codec.binary.Base64.decodeBase64;
import static org.apache.commons.codec.binary.Base64.encodeBase64;
import static org.apache.commons.io.FileUtils.readFileToByteArray;

/**
 *
 * @author rcosco
 */
public final class HtmlEncoder {

    /**
     *
     * @return
     */
    public static String getMaschera2() {
        String ing = "<!doctype html>"
                + "<html>"
                + "<head>"
                + "<meta charset=\"utf-8\">"
                + "<title>M2</title>"
                + "</head>"
                + "<body style=\"font-size: 18.5px\">"
                + "Con la firma del presente modulo accetto le condizioni economiche applicate. "
                + "Dichiaro altresì di avere letto e compreso l'informativa sulla trasparenza dei servizi disponibile in agenzia, d.lgs n. 206/2005 "
                + "(Cod.Consumo), d.lgs. n 231/2007 e successive modifiche (AML), reg. UE n. 2016/679 (privacy), "
                + "art. 46 D.P.R. 28/05/00 n. 445 (autocertificazione cittadini stranieri)<br>"
                + "<i>By signing this form, I formally accept the applied rate and conditions applied. "
                + "I also declare that I have read and understood the transparency policy available at this branch. "
                + "LD No. 206/2005 (Consumer Code), LD No. 231/2007 and subsequent amendments (AML),  EU reg. No. 2016/679 (privacy), ), "
                + "art. 46 D.P.R. 28/05/00 No. 445 (auto certification foreign citizen)</i>"
                + "</body>"
                + "</html>";
        byte[] b = ing.getBytes(forName("UTF-8"));
        byte[] base64 = encodeBase64(b);
        return new String(base64);
    }

    /**
     *
     * @return
     */
    public static String getMaschera3() {
        String ing = "<!doctype html>"
                + "<html>"
                + "<head>"
                + "<meta charset=\"utf-8\">"
                + "<title>M2</title>"
                + "</head>"
                + "<body style=\"font-size: 20px\">"
                + "Io sottoscritto dichiaro di consentire al Titolare ed ai partner commerciali, opportunamente individuati nell'informativa di "
                + "cui all'art 13 del Regolamento UE n. 2016/679, di inviarmi materiale pubblicitario/promozionale, mediante mail, telefono, sms e posta cartacea.<br>"
                + "<i>I herewith declare that I allow the Owner and its  business partners, appropriately identified in the disclosure referred to Art. "
                + "13 of EU Regulation n°. 2016/679, to send me advertising or promotional material by email, telephone, text messages and paper-based mail.</i>"
                + "</body>"
                + "</html>";
        byte[] b = ing.getBytes(forName("UTF-8"));
        byte[] base64 = encodeBase64(b);
        return new String(base64);

    }

    /**
     *
     * @return
     */
    public static String getBase86customtagsPri() {
        String ing = "<!doctype html>"
                + "<html>"
                + "<head>"
                + "<meta charset=\"utf-8\">"
                + "<title>S</title>"
                + "</head>"
                + "<body style=\"font-size: 20px\">"
                + "Io sottoscritto acconsento al trattamento dei miei dati anagrafici (Privacy Policy disponibile in filiale)"
                + "<br><i>I, the undersigned, agree to allow my personal data processing (Privacy Policy here available)</i>"
                + "</body>"
                + "</html>";

        byte[] b = ing.getBytes(forName("UTF-8"));
        byte[] base64 = encodeBase64(b);
        return new String(base64);
//        return getStringBase64(new File("C:\\Maccorp\\pri.html"));
    }

    /**
     *
     * @param pep
     * @return
     */
    public static String getBase86customtagsPEP(boolean pep) {
        if (pep) {
            String p1 = "<!doctype html>"
                    + "<html>"
                    + "<head>"
                    + "<meta charset=\"utf-8\">"
                    + "<title>S</title>"
                    + "</head>"
                    + "<body style=\"font-size: 20px\">"
                    + "Io sottoscritto accetto le condizioni economiche applicate e dichiaro di <strong>essere</strong> Persona Esposta Politicamente (PEP)"
                    + "<br><i>I, the undersigned, accept rates and conditions applied and I declare that </i><i>I’m a Politically Exposed Person (PEP)</i>"
                    + "</body>"
                    + "</html>";
            byte[] b = p1.getBytes(forName("UTF-8"));
            byte[] base64 = encodeBase64(b);
            return new String(base64);
//            return getStringBase64(new File("C:\\Maccorp\\pep.html"));
        } else {
            String p2 = "<!doctype html>"
                    + "<html>"
                    + "<head>"
                    + "<meta charset=\"utf-8\">"
                    + "<title>S</title>"
                    + "</head>\n"
                    + "<body style=\"font-size: 20px\">"
                    + "Io sottoscritto accetto le condizioni economiche applicate e dichiaro di <strong>non essere</strong> Persona Esposta Politicamente (PEP)"
                    + "<br> <i>I, the undersigned, accept rates and conditions applied and I declare that </i><i>I’m not a Politically Exposed Person (PEP)</i>"
                    + "</body>"
                    + "</html>";
            byte[] b = p2.getBytes(forName("UTF-8"));
            byte[] base64 = encodeBase64(b);
            return new String(base64);
//            return getStringBase64(new File("C:\\Maccorp\\nopep.html"));
        }
    }

    /**
     *
     * @param <T>
     * @param sequence
     * @param out
     * @return
     * @throws IOException
     */
    public static <T extends Appendable> T escapeNonLatin(CharSequence sequence, T out)
            throws IOException {
        for (int i = 0; i < sequence.length(); i++) {
            char ch = sequence.charAt(i);
            if (of(ch) == BASIC_LATIN) {
                out.append(ch);
            } else {
                int codepoint = codePointAt(sequence, i);
                i += charCount(codepoint) - 1;
                out.append("&#x");
                out.append(toHexString(codepoint));
                out.append(";");
            }
        }
        return out;
    }

    /**
     *
     * @param path
     * @param ing
     * @return
     */
    public static String base64HTML(String path, String ing) {
        if (ing == null) {
            return "-";
        }
        try {
            File f1 = new File(path + generaId(150) + ".html");
            try (FileOutputStream is = new FileOutputStream(f1)) {
                OutputStreamWriter osw = new OutputStreamWriter(is);
                BufferedWriter w = new BufferedWriter(osw);
                w.write(ing);
                w.close();
                osw.close();
            }
            String base64 = new String(encodeBase64(readFileToByteArray(f1)));
            f1.delete();
            return base64;
        } catch (FileNotFoundException ex) {
            insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
        } catch (IOException ex) {
            insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
        }
        return null;
    }

    /**
     *
     * @param base64
     * @return
     */
    public static String getBase64HTML(String base64) {
        byte[] ing = decodeBase64(base64.getBytes());
        String s = new String(ing);
        return s;
    }

    /**
     *
     * @return
     */
    public static final String htmlMailResetPass() {
        String html = "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional //EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\"><html><head><title></title><meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"><meta name=\"viewport\" content=\"width=320, target-densitydpi=device-dpi\"><style type=\"text/css\">@media only screen and (max-width: 660px) { table[class=w0], td[class=w0] { width: 0 !important; }table[class=w10], td[class=w10], img[class=w10] { width:10px !important; }table[class=w15], td[class=w15], img[class=w15] { width:5px !important; }table[class=w30], td[class=w30], img[class=w30] { width:10px !important; }table[class=w60], td[class=w60], img[class=w60] { width:10px !important; }table[class=w125], td[class=w125], img[class=w125] { width:80px !important; }table[class=w130], td[class=w130], img[class=w130] { width:55px !important; }table[class=w140], td[class=w140], img[class=w140] { width:90px !important; }table[class=w160], td[class=w160], img[class=w160] { width:180px !important; }table[class=w170], td[class=w170], img[class=w170] { width:100px !important; }table[class=w180], td[class=w180], img[class=w180] { width:80px !important; }table[class=w195], td[class=w195], img[class=w195] { width:80px !important; }table[class=w220], td[class=w220], img[class=w220] { width:80px !important; }table[class=w240], td[class=w240], img[class=w240] { width:180px !important; }table[class=w255], td[class=w255], img[class=w255] { width:185px !important; }table[class=w275], td[class=w275], img[class=w275] { width:135px !important; }table[class=w280], td[class=w280], img[class=w280] { width:135px !important; }table[class=w300], td[class=w300], img[class=w300] { width:140px !important; }table[class=w325], td[class=w325], img[class=w325] { width:95px !important; }table[class=w360], td[class=w360], img[class=w360] { width:140px !important; }table[class=w410], td[class=w410], img[class=w410] { width:180px !important; }table[class=w470], td[class=w470], img[class=w470] { width:200px !important; }table[class=w580], td[class=w580], img[class=w580] { width:280px !important; }table[class=w640], td[class=w640], img[class=w640] { width:300px !important; }table[class*=hide], td[class*=hide], img[class*=hide], p[class*=hide], span[class*=hide] { display:none !important; }table[class=h0], td[class=h0] { height: 0 !important; }p[class=footer-content-left] { text-align: center !important; }#headline p { font-size: 30px !important; }.article-content, #left-sidebar{ -webkit-text-size-adjust: 90% !important; -ms-text-size-adjust: 90% !important; }.header-content, .footer-content-left {-webkit-text-size-adjust: 80% !important; -ms-text-size-adjust: 80% !important;}img { height: auto; line-height: 100%;}} /* Client-specific Styles */#outlook a { padding: 0; }	/* Force Outlook to provide a \"view in browser\" button. */body { width: 100% !important; }.ReadMsgBody { width: 100%; }.ExternalClass { width: 100%; display:block !important; } /* Force Hotmail to display emails at full width *//* Reset Styles *//* Add 100px so mobile switch bar doesn't cover street address. */body { background-color: #dedede; margin: 0; padding: 0; }img { outline: none; text-decoration: none; display: block;}br, strong br, b br, em br, i br { line-height:100%; }h1, h2, h3, h4, h5, h6 { line-height: 100% !important; -webkit-font-smoothing: antialiased; }h1 a, h2 a, h3 a, h4 a, h5 a, h6 a { color: blue !important; }h1 a:active, h2 a:active,  h3 a:active, h4 a:active, h5 a:active, h6 a:active {	color: red !important; }/* Preferably not the same color as the normal header link color.  There is limited support for psuedo classes in email clients, this was added just for good measure. */h1 a:visited, h2 a:visited,  h3 a:visited, h4 a:visited, h5 a:visited, h6 a:visited { color: purple !important; }/* Preferably not the same color as the normal header link color. There is limited support for psuedo classes in email clients, this was added just for good measure. */  table td, table tr { border-collapse: collapse; }.yshortcuts, .yshortcuts a, .yshortcuts a:link,.yshortcuts a:visited, .yshortcuts a:hover, .yshortcuts a span {color: black; text-decoration: none !important; border-bottom: none !important; background: none !important;}	/* Body text color for the New Yahoo.  This example sets the font of Yahoo's Shortcuts to black. *//* This most probably won't work in all email clients. Don't include code blocks in email. */code {white-space: normal;word-break: break-all;}#background-table { background-color: #dedede; }/* Webkit Elements */#top-bar { border-radius:6px 6px 0px 0px; -moz-border-radius: 6px 6px 0px 0px; -webkit-border-radius:6px 6px 0px 0px; -webkit-font-smoothing: antialiased; background-color: rgb(94, 115, 139); color: #ededed; }#top-bar a { font-weight: bold; color: #ffffff; text-decoration: none;}#footer { border-radius:0px 0px 6px 6px; -moz-border-radius: 0px 0px 6px 6px; -webkit-border-radius:0px 0px 6px 6px; -webkit-font-smoothing: antialiased; }/* Fonts and Content */body, td { font-family: HelveticaNeue, sans-serif; }.header-content, .footer-content-left, .footer-content-right { -webkit-text-size-adjust: none; -ms-text-size-adjust: none; }/* Prevent Webkit and Windows Mobile platforms from changing default font sizes on header and footer. */.header-content { font-size: 12px; color: #ededed; }.header-content a { font-weight: bold; color: #ffffff; text-decoration: none; }#headline p { color: #444444; font-family: HelveticaNeue, sans-serif; font-size: 36px; text-align: center; margin-top:0px; margin-bottom:30px; }#headline p a { color: #444444; text-decoration: none; }.article-title { font-size: 18px; line-height:24px; color: rgb(94, 115, 139); font-weight:bold; margin-top:0px; margin-bottom:18px; font-family: HelveticaNeue, sans-serif; }.article-title a { color: #b0b0b0; text-decoration: none; }.article-title.with-meta {margin-bottom: 0;}.article-meta { font-size: 13px; line-height: 20px; color: #ccc; font-weight: bold; margin-top: 0;}.article-content { font-size: 13px; line-height: 18px; color: #444444; margin-top: 0px; margin-bottom: 18px; font-family: HelveticaNeue, sans-serif; }.article-content a { color: #2f82de; font-weight:bold; text-decoration:none; }.article-content img { max-width: 100% }.article-content ol, .article-content ul { margin-top:0px; margin-bottom:18px; margin-left:19px; padding:0; }.article-content li { font-size: 13px; line-height: 18px; color: #444444; }.article-content li a { color: #2f82de; text-decoration:underline; }.article-content p {margin-bottom: 15px;}.footer-content-left { font-size: 12px; line-height: 15px; color: #ededed; margin-top: 0px; margin-bottom: 15px; float:center; }.footer-content-left a { color: #ffffff; font-weight: bold; text-decoration: none; }.footer-content-right { font-size: 11px; line-height: 16px; color: #ededed; margin-top: 0px; margin-bottom: 15px; }.footer-content-right a { color: #ffffff; font-weight: bold; text-decoration: none; }.link-title { font-size: 14px; line-height:24px; color: #b0b0b0; font-weight:bold; margin-top:0px; margin-bottom:18px; font-family: HelveticaNeue, sans-serif; }.link-title a {text-decoration: none; color: #b0b0b0;}#footer { background-color: rgb(94, 115, 139); color: #ededed; }#footer a { color: #ffffff; text-decoration: none; font-weight: bold; }#permission-reminder { white-space: normal; }#street-address { color: #b0b0b0; white-space: normal; }</style></head><body><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" id=\"background-table\" style=\"table-layout:fixed\" align=\"center\"><tbody><tr><td align=\"center\" bgcolor=\"#dedede\"><table class=\"w640\" style=\"margin:0 10px;\" width=\"640\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tbody><tr><td class=\"w640\" width=\"640\" height=\"20\"></td></tr><tr><td class=\"w640\" width=\"640\"><table id=\"top-bar\" class=\"w640\" width=\"640\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" bgcolor=\"#ffffff\"><tbody><tr><td class=\"w15\" width=\"15\"></td><td class=\"w325\" width=\"350\" valign=\"middle\" align=\"left\"><table class=\"w325\" width=\"350\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tbody><tr><td class=\"w325\" width=\"350\" height=\"8\"></td></tr></tbody></table><div class=\"header-content\"><br /></div><table class=\"w255\" width=\"255\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tbody><tr><td class=\"w255\" width=\"255\" height=\"8\"></td></tr></tbody></table></td><td class=\"w15\" width=\"15\"></td></tr></tbody></table></td></tr><tr><td id=\"header\" class=\"w640\" width=\"640\" align=\"center\" bgcolor=\"#ffffff\"></td></tr><tr><td class=\"w640\" width=\"640\" height=\"30\" bgcolor=\"#ffffff\"></td></tr><tr id=\"simple-content-row\"><td class=\"w640\" width=\"640\" bgcolor=\"#ffffff\"><table align=\"left\" class=\"w640\" width=\"640\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tbody><tr><td class=\"w30\" width=\"30\"></td><td class=\"w580\" width=\"580\"><repeater><layout label=\"Text only\"><table class=\"w580\" width=\"580\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tbody><tr><td class=\"w580\" width=\"580\"><p align=\"left\" class=\"article-title\">  <singleline label=\"Title\">Hi @username,</singleline></p><div align=\"left\" class=\"article-content\"><multiline label=\"Description\"> <p>your password has been reset.</p><p>The new password is as follows:</p><p><b>@password</b></p><p><u>The password must be changed at the first access.</u></p></multiline></div><div align=\"right\" class=\"article-content\"><multiline label=\"Description\">Best regards</multiline></div></td></tr><tr><td class=\"w580\" width=\"580\" height=\"10\"></td></tr></tbody></table></layout></repeater></td><td class=\"w30\" width=\"30\"></td></tr></tbody></table></td></tr><tr><td class=\"w640\" width=\"640\"><table id=\"footer\" class=\"w640\" width=\"640\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" bgcolor=\"#c7c7c7\"><tr><td class=\"w640\" width=\"640\"><p align=\"center\" class=\"footer-content-right\"><br>Please, do not reply to this email - Mac2.0</p></td></tr></table></td></tr></tbody></table></body></html>";
        return html;
    }

    /**
     *
     * @return
     */
    public static final String htmlMailPrenot() {
        String html = "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional //EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\"><html><head><title></title><meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"><meta name=\"viewport\" content=\"width=320, target-densitydpi=device-dpi\"><style type=\"text/css\">@media only screen and (max-width: 660px) { table[class=w0], td[class=w0] { width: 0 !important; }table[class=w10], td[class=w10], img[class=w10] { width:10px !important; }table[class=w15], td[class=w15], img[class=w15] { width:5px !important; }table[class=w30], td[class=w30], img[class=w30] { width:10px !important; }table[class=w60], td[class=w60], img[class=w60] { width:10px !important; }table[class=w125], td[class=w125], img[class=w125] { width:80px !important; }table[class=w130], td[class=w130], img[class=w130] { width:55px !important; }table[class=w140], td[class=w140], img[class=w140] { width:90px !important; }table[class=w160], td[class=w160], img[class=w160] { width:180px !important; }table[class=w170], td[class=w170], img[class=w170] { width:100px !important; }table[class=w180], td[class=w180], img[class=w180] { width:80px !important; }table[class=w195], td[class=w195], img[class=w195] { width:80px !important; }table[class=w220], td[class=w220], img[class=w220] { width:80px !important; }table[class=w240], td[class=w240], img[class=w240] { width:180px !important; }table[class=w255], td[class=w255], img[class=w255] { width:185px !important; }table[class=w275], td[class=w275], img[class=w275] { width:135px !important; }table[class=w280], td[class=w280], img[class=w280] { width:135px !important; }table[class=w300], td[class=w300], img[class=w300] { width:140px !important; }table[class=w325], td[class=w325], img[class=w325] { width:95px !important; }table[class=w360], td[class=w360], img[class=w360] { width:140px !important; }table[class=w410], td[class=w410], img[class=w410] { width:180px !important; }table[class=w470], td[class=w470], img[class=w470] { width:200px !important; }table[class=w580], td[class=w580], img[class=w580] { width:280px !important; }table[class=w640], td[class=w640], img[class=w640] { width:300px !important; }table[class*=hide], td[class*=hide], img[class*=hide], p[class*=hide], span[class*=hide] { display:none !important; }table[class=h0], td[class=h0] { height: 0 !important; }p[class=footer-content-left] { text-align: center !important; }#headline p { font-size: 30px !important; }.article-content, #left-sidebar{ -webkit-text-size-adjust: 90% !important; -ms-text-size-adjust: 90% !important; }.header-content, .footer-content-left {-webkit-text-size-adjust: 80% !important; -ms-text-size-adjust: 80% !important;}img { height: auto; line-height: 100%;}} /* Client-specific Styles */#outlook a { padding: 0; }	/* Force Outlook to provide a \"view in browser\" button. */body { width: 100% !important; }.ReadMsgBody { width: 100%; }.ExternalClass { width: 100%; display:block !important; } /* Force Hotmail to display emails at full width *//* Reset Styles *//* Add 100px so mobile switch bar doesn't cover street address. */body { background-color: #dedede; margin: 0; padding: 0; }img { outline: none; text-decoration: none; display: block;}br, strong br, b br, em br, i br { line-height:100%; }h1, h2, h3, h4, h5, h6 { line-height: 100% !important; -webkit-font-smoothing: antialiased; }h1 a, h2 a, h3 a, h4 a, h5 a, h6 a { color: blue !important; }h1 a:active, h2 a:active,  h3 a:active, h4 a:active, h5 a:active, h6 a:active {	color: red !important; }/* Preferably not the same color as the normal header link color.  There is limited support for psuedo classes in email clients, this was added just for good measure. */h1 a:visited, h2 a:visited,  h3 a:visited, h4 a:visited, h5 a:visited, h6 a:visited { color: purple !important; }/* Preferably not the same color as the normal header link color. There is limited support for psuedo classes in email clients, this was added just for good measure. */  table td, table tr { border-collapse: collapse; }.yshortcuts, .yshortcuts a, .yshortcuts a:link,.yshortcuts a:visited, .yshortcuts a:hover, .yshortcuts a span {color: black; text-decoration: none !important; border-bottom: none !important; background: none !important;}	/* Body text color for the New Yahoo.  This example sets the font of Yahoo's Shortcuts to black. *//* This most probably won't work in all email clients. Don't include code blocks in email. */code {white-space: normal;word-break: break-all;}#background-table { background-color: #dedede; }/* Webkit Elements */#top-bar { border-radius:6px 6px 0px 0px; -moz-border-radius: 6px 6px 0px 0px; -webkit-border-radius:6px 6px 0px 0px; -webkit-font-smoothing: antialiased; background-color: #c7c7c7; color: #ededed; }#top-bar a { font-weight: bold; color: #ffffff; text-decoration: none;}#footer { border-radius:0px 0px 6px 6px; -moz-border-radius: 0px 0px 6px 6px; -webkit-border-radius:0px 0px 6px 6px; -webkit-font-smoothing: antialiased; }/* Fonts and Content */body, td { font-family: HelveticaNeue, sans-serif; }.header-content, .footer-content-left, .footer-content-right { -webkit-text-size-adjust: none; -ms-text-size-adjust: none; }/* Prevent Webkit and Windows Mobile platforms from changing default font sizes on header and footer. */.header-content { font-size: 12px; color: #ededed; }.header-content a { font-weight: bold; color: #ffffff; text-decoration: none; }#headline p { color: #444444; font-family: HelveticaNeue, sans-serif; font-size: 36px; text-align: center; margin-top:0px; margin-bottom:30px; }#headline p a { color: #444444; text-decoration: none; }.article-title { font-size: 18px; line-height:24px; color: #b0b0b0; font-weight:bold; margin-top:0px; margin-bottom:18px; font-family: HelveticaNeue, sans-serif; }.article-title a { color: #b0b0b0; text-decoration: none; }.article-title.with-meta {margin-bottom: 0;}.article-meta { font-size: 13px; line-height: 20px; color: #ccc; font-weight: bold; margin-top: 0;}.article-content { font-size: 13px; line-height: 18px; color: #444444; margin-top: 0px; margin-bottom: 18px; font-family: HelveticaNeue, sans-serif; }.article-content a { color: #2f82de; font-weight:bold; text-decoration:none; }.article-content img { max-width: 100% }.article-content ol, .article-content ul { margin-top:0px; margin-bottom:18px; margin-left:19px; padding:0; }.article-content li { font-size: 13px; line-height: 18px; color: #444444; }.article-content li a { color: #2f82de; text-decoration:underline; }.article-content p {margin-bottom: 15px;}.footer-content-left { font-size: 12px; line-height: 15px; color: #ededed; margin-top: 0px; margin-bottom: 15px; float:center; }.footer-content-left a { color: #ffffff; font-weight: bold; text-decoration: none; }.footer-content-right { font-size: 11px; line-height: 16px; color: #ededed; margin-top: 0px; margin-bottom: 15px; }.footer-content-right a { color: #ffffff; font-weight: bold; text-decoration: none; }.link-title { font-size: 14px; line-height:24px; color: #b0b0b0; font-weight:bold; margin-top:0px; margin-bottom:18px; font-family: HelveticaNeue, sans-serif; }.link-title a {text-decoration: none; color: #b0b0b0;}#footer { background-color: #c7c7c7; color: #ededed; }#footer a { color: #ffffff; text-decoration: none; font-weight: bold; }#permission-reminder { white-space: normal; }#street-address { color: #b0b0b0; white-space: normal; }</style></head><body><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" id=\"background-table\" style=\"table-layout:fixed\" align=\"center\"><tbody><tr><td align=\"center\" bgcolor=\"#dedede\"><table class=\"w640\" style=\"margin:0 10px;\" width=\"640\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tbody><tr><td class=\"w640\" width=\"640\" height=\"20\"></td></tr><tr><td class=\"w640\" width=\"640\"><table id=\"top-bar\" class=\"w640\" width=\"640\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" bgcolor=\"#ffffff\"><tbody><tr><td class=\"w15\" width=\"15\"></td><td class=\"w325\" width=\"350\" valign=\"middle\" align=\"left\"><table class=\"w325\" width=\"350\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tbody><tr><td class=\"w325\" width=\"350\" height=\"8\"></td></tr></tbody></table><div class=\"header-content\"><br /></div><table class=\"w255\" width=\"255\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tbody><tr><td class=\"w255\" width=\"255\" height=\"8\"></td></tr></tbody></table></td><td class=\"w15\" width=\"15\"></td></tr></tbody></table></td></tr><tr><td id=\"header\" class=\"w640\" width=\"640\" align=\"center\" bgcolor=\"#ffffff\"></td></tr><tr><td class=\"w640\" width=\"640\" height=\"30\" bgcolor=\"#ffffff\"></td></tr><tr id=\"simple-content-row\"><td class=\"w640\" width=\"640\" bgcolor=\"#ffffff\"><table align=\"left\" class=\"w640\" width=\"640\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tbody><tr><td class=\"w30\" width=\"30\"></td><td class=\"w580\" width=\"580\"><repeater><layout label=\"Text only\"><table class=\"w580\" width=\"580\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tbody><tr><td class=\"w580\" width=\"580\"><p align=\"left\" class=\"article-title\">  <singleline label=\"Title\">Hi @filialenome,</singleline></p><div align=\"left\" class=\"article-content\"><multiline label=\"Description\"> <p>present to inform you of the presence of a new online reservation active at your office, access Mac2.0 in the appropriate section to manage it.</p><p>The reservation is scheduled for the <b>@dataprenot</b>.</p></multiline></div><div align=\"right\" class=\"article-content\"><multiline label=\"Description\">Best Regards</multiline></div></td></tr><tr><td class=\"w580\" width=\"580\" height=\"10\"></td></tr></tbody></table></layout></repeater></td><td class=\"w30\" width=\"30\"></td></tr></tbody></table></td></tr><tr><td class=\"w640\" width=\"640\"><table id=\"footer\" class=\"w640\" width=\"640\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" bgcolor=\"#c7c7c7\"><tr><td class=\"w640\" width=\"640\"><p align=\"center\" class=\"footer-content-right\"><br></p></td></tr></table></td></tr></tbody></table></body></html>";
        return html;
    }

}
