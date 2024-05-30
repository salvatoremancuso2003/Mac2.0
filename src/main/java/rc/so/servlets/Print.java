package rc.so.servlets;

import rc.so.db.Db_Master;
import rc.so.entity.Branch;
import rc.so.entity.Ch_transaction;
import rc.so.entity.Ch_transaction_doc;
import rc.so.entity.Ch_transaction_refund;
import rc.so.entity.Ch_transaction_value;
import rc.so.entity.Client;
import rc.so.entity.Currency;
import rc.so.entity.CustomerKind;
import rc.so.entity.Figures;
import rc.so.entity.NC_transaction;
import rc.so.entity.Office;
import rc.so.entity.Outputf;
import rc.so.entity.Rate_history;
import rc.so.entity.Users;
import rc.so.entity.VATcode;
import rc.so.pdf.NewReceipt_CZ;
import rc.so.pdf.Pdf;
import static rc.so.pdf.Pdf.heavyUK;
import static rc.so.pdf.Pdf.modulo_profilatura;
import static rc.so.pdf.Pdf.refund;
import static rc.so.pdf.Pdf.refund_CZ;
import rc.so.pdf.Receipt;
import static rc.so.rest.Sign.sign_document;
import static rc.so.util.Constant.is_CZ;
import static rc.so.util.Constant.patternnormdate;
import static rc.so.util.Constant.patternnormdate_f;
import static rc.so.util.Constant.patternnormdate_filter;
import static rc.so.util.Constant.patternsql;
import static rc.so.util.Constant.patternsqldate;
import static rc.so.util.Engine.browserCopyLinkFirma;
import static rc.so.util.Engine.formatBankBranch;
import static rc.so.util.Engine.formatfromlist_doc;
import static rc.so.util.Engine.get_list_tr_doc;
import static rc.so.util.Engine.insertTR;
import static rc.so.util.Engine.insert_CZ_prereceipt;
import static rc.so.util.Engine.insert_CZ_receipt;
import static rc.so.util.Engine.verifySession;
import rc.so.util.Excel;
import rc.so.util.Txt;
import static rc.so.util.Txt.printFile;
import static rc.so.util.Utility.formatMysqltoDisplay;
import static rc.so.util.Utility.formatStringtoStringDate;
import static rc.so.util.Utility.formatStringtoStringDate_null;
import static rc.so.util.Utility.generaId;
import static rc.so.util.Utility.parseArrayValues;
import static rc.so.util.Utility.parseString;
import static rc.so.util.Utility.pingURL;
import static rc.so.util.Utility.redirect;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import static java.lang.Boolean.valueOf;
import static java.lang.String.format;
import static java.lang.Thread.currentThread;
import java.util.ArrayList;
import static java.util.stream.Collectors.toList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import static org.apache.commons.codec.binary.Base64.decodeBase64;
import static org.apache.commons.io.FileUtils.writeByteArrayToFile;
import org.joda.time.DateTime;
import rc.so.util.Utility;

/**
 *
 * @author rcosco
 */
public class Print extends HttpServlet {

    private static final String replace = "172.18.17.61";

    //private static final String replace = "172.31.224.25";
    //private static boolean onlyprint = true;
    //private static boolean onlyprint = Engine.get_branch(Engine.getFil()[0]).getFg_pad().equals("0");
    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void receiptcommnotzero(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String user = (String) request.getSession().getAttribute("us_cod");
        if (user == null) {
            user = "9999";
        }

        Db_Master db = new Db_Master();
        String bra_loc = db.getCodLocal(true)[0];
//        String pathtemp = "F:\\com\\";
        String pathtemp = Utility.sanitize(db.getPath("temp"));
        String codtr = Utility.sanitize(request.getParameter("codtr"));
        String codcl = Utility.sanitize(request.getParameter("codcl"));
        Ch_transaction tra = db.query_transaction_ch(codtr);
        if (tra == null) {
            tra = db.query_transaction_ch_temp(codtr);
        }
        ArrayList<Ch_transaction_value> li = db.query_transaction_value(codtr);
        Client cl = db.query_Client_transaction(codtr, codcl);
        Office ma = db.get_national_office();
        Branch br = db.get_branch(tra.getFiliale());
        boolean onlyprint = br.getFg_pad().equals("0");

        ArrayList<Figures> fig = db.list_all_figures();
        ArrayList<Currency> cur = db.list_figures_query_edit(null);
        String base64 = new Receipt().print_receipt_comm_not_zero(pathtemp, ma, tra, cl, li, br, fig, cur, true);
        db.closeDB();
        String filename = new DateTime().toString("yyMMddHHmmssSSS") + codtr + "_macriseco.pdf";
        if (base64 != null) {
            if (request.getParameter("down") != null && request.getParameter("down").equals("OK")) {
                onlyprint = true;
            } else if (request.getParameter("down") != null && request.getParameter("down").equals("OK1")) {

                String headerKey = "Content-Disposition";
                String sanitizedfilename = Utility.sanitizeFilename(filename);
                String headerValue = format("inline; filename=\"%s\"", new Object[]{sanitizedfilename});
                response.setHeader("Content-Type", "application/pdf");
                response.setHeader(headerKey, headerValue);
                try (OutputStream outStream = response.getOutputStream()) {
                    outStream.write(decodeBase64(base64.getBytes()));
                }
                return;
            }

            if (onlyprint) {
                String headerKey = "Content-Disposition";
                String sanitizedfilename = Utility.sanitizeFilename(filename);
                String headerValue = format("attachment; filename=\"%s\"", new Object[]{sanitizedfilename});
                response.setHeader("Content-Type", "application/pdf");
                response.setHeader(headerKey, headerValue);
                try (OutputStream outStream = response.getOutputStream()) {
                    outStream.write(decodeBase64(base64.getBytes()));
                }
            } else {
                boolean online = false;
                Outputf out = sign_document(pathtemp, codtr, user, bra_loc, "_macriseco", base64, filename);
                if (out != null) {
                    if (out.getEsito().equals("0")) {
                        online = true;
                    }
                } else {
                    out = new Outputf();
                }
                if (online) {
                    String link = out.getLinkweb().replaceAll("5.83.104.161", replace);

                    if (pingURL(link)) {
                        if (browserCopyLinkFirma(request)) {
                            request.getSession().setAttribute("linkfirma", link);
                            redirect(request, response, "pagesign.jsp");
                        } else {
                            redirect(request, response, link);
                        }
                    } else {
                        String headerKey = "Content-Disposition";
                        String sanitizedfilename = Utility.sanitizeFilename(filename);
                        String headerValue = format("inline; filename=\"%s\"", new Object[]{sanitizedfilename});
                        response.setHeader("Content-Type", "application/pdf");
                        response.setHeader(headerKey, headerValue);
                        try (OutputStream outStream = response.getOutputStream()) {
                            outStream.write(decodeBase64(base64.getBytes()));
                        }
                    }
                } else {
                    String headerKey = "Content-Disposition";
                    String sanitizedfilename = Utility.sanitizeFilename(filename);
                    String headerValue = format("inline; filename=\"%s\"", new Object[]{sanitizedfilename});
                    response.setHeader("Content-Type", "application/pdf");
                    response.setHeader(headerKey, headerValue);
                    try (OutputStream outStream = response.getOutputStream()) {
                        outStream.write(decodeBase64(base64.getBytes()));
                    }
                }
            }
        } else {
            redirect(request, response, "page_fnf.html");
        }
    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void receiptsee(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String user = (String) request.getSession().getAttribute("us_cod");
        if (user == null) {
            user = "9999";
        }
        Db_Master db = new Db_Master();
        String pathtemp = Utility.sanitize(db.getPath("temp"));
        String bra_loc = Utility.sanitize(db.getCodLocal(true)[0]);

        String codtr = Utility.sanitize(request.getParameter("codtr"));
        String codcl = Utility.sanitize(request.getParameter("codcl"));
        Ch_transaction tra = db.query_transaction_ch(codtr);
        if (tra == null) {
            tra = db.query_transaction_ch_temp(codtr);
        }

        ArrayList<Ch_transaction_value> li = db.query_transaction_value(codtr);
        Client cl = db.query_Client_transaction(codtr, codcl);
        Office ma = db.get_national_office();
        Branch br = db.get_branch(tra.getFiliale());
        boolean onlyprint = br.getFg_pad().equals("0");
        boolean print = onlyprint;
        if (Utility.sanitize(request.getParameter("req2")) != null) {
            print = valueOf(Utility.sanitize(request.getParameter("req2")));
        }

        ArrayList<Figures> fig = db.list_all_figures();
        ArrayList<Currency> cur = db.list_figures_query_edit(null);
        String base64 = Utility.sanitize(new Receipt().print_receipt_see(pathtemp, ma, tra, cl, li, br, fig, cur, true));
        db.closeDB();
        if (base64 != null) {
            String filename = new DateTime().toString("yyMMddHHmmssSSS") + codtr + "_macrecsee.pdf";
            if (request.getParameter("down") != null && request.getParameter("down").equals("OK")) {
                print = true;
            } else if (request.getParameter("down") != null && request.getParameter("down").equals("OK1")) {

                String headerKey = "Content-Disposition";
                String sanitizedfilename = Utility.sanitizeFilename(filename);
                String headerValue = format("inline; filename=\"%s\"", new Object[]{sanitizedfilename});
                response.setHeader("Content-Type", "application/pdf");
                response.setHeader(headerKey, headerValue);
                try (OutputStream outStream = response.getOutputStream()) {
                    outStream.write(decodeBase64(base64.getBytes()));
                }
                return;
            }

            if (print) {
                String headerKey = "Content-Disposition";
                String sanitizedfilename = Utility.sanitizeFilename(filename);
                String headerValue = format("attachment; filename=\"%s\"", new Object[]{sanitizedfilename});
                response.setHeader("Content-Type", "application/pdf");
                response.setHeader(headerKey, headerValue);
                try (OutputStream outStream = response.getOutputStream()) {
                    outStream.write(decodeBase64(base64.getBytes()));
                }
            } else {
                boolean online = false;

                Outputf out = sign_document(pathtemp, codtr, user, bra_loc, "_macrecsee", base64, filename);
                if (out != null) {
                    if (out.getEsito().equals("0")) {
                        online = true;
                    }
                } else {
                    out = new Outputf();
                }
                if (online) {
                    String link = out.getLinkweb().replaceAll("5.83.104.161", replace);
                    if (pingURL(link)) {
                        if (browserCopyLinkFirma(request)) {
                            request.getSession().setAttribute("linkfirma", link);
                            redirect(request, response, "pagesign.jsp");
                        } else {
                            redirect(request, response, link);
                        }
                    } else {
                        String headerKey = "Content-Disposition";
                        String sanitizedfilename = Utility.sanitizeFilename(filename);
                        String headerValue = format("inline; filename=\"%s\"", new Object[]{sanitizedfilename});
                        response.setHeader("Content-Type", "application/pdf");
                        response.setHeader(headerKey, headerValue);
                        try (OutputStream outStream = response.getOutputStream()) {
                            outStream.write(decodeBase64(base64.getBytes()));
                        }
                    }
                } else {
                    String headerKey = "Content-Disposition";
                    String sanitizedfilename = Utility.sanitizeFilename(filename);
                    String headerValue = format("inline; filename=\"%s\"", new Object[]{sanitizedfilename});
                    response.setHeader("Content-Type", "application/pdf");
                    response.setHeader(headerKey, headerValue);
                    try (OutputStream outStream = response.getOutputStream()) {
                        outStream.write(decodeBase64(base64.getBytes()));
                    }
                }
            }
        } else {
            redirect(request, response, "page_fnf.html");
        }
    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void sign_nochange_taxfree(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String user = (String) request.getSession().getAttribute("us_cod");
        if (user == null) {
            user = "9999";
        }

        String cod = Utility.sanitize(request.getParameter("cod"));

        Db_Master db = new Db_Master();

        NC_transaction nc = db.get_NC_transaction(cod);
        String bra_loc = db.getCodLocal(true)[0];
        boolean onlyprint = db.get_branch(bra_loc).getFg_pad().equals("0");
        String pathtemp = Utility.sanitize(db.getPath("temp"));
        db.closeDB();

        String base64 = new Pdf().receipt_nochange_anag(nc);

        if (base64 != null) {
            String filename = new DateTime().toString("yyMMddHHmmssSSS") + cod + "_macnctaxf.pdf";
            if (request.getParameter("down") != null && request.getParameter("down").equals("OK")) {
                onlyprint = true;
            } else if (request.getParameter("down") != null && request.getParameter("down").equals("OK1")) {
                String headerKey = "Content-Disposition";
                String sanitizedfilename = Utility.sanitizeFilename(filename);
                String headerValue = format("inline; filename=\"%s\"", new Object[]{sanitizedfilename});
                response.setHeader("Content-Type", "application/pdf");
                response.setHeader(headerKey, headerValue);
                try (OutputStream outStream = response.getOutputStream()) {
                    outStream.write(decodeBase64(base64.getBytes()));
                }
                return;
            }
            if (onlyprint) {
                String headerKey = "Content-Disposition";
                String sanitizedfilename = Utility.sanitizeFilename(filename);
                String headerValue = format("attachment; filename=\"%s\"", new Object[]{sanitizedfilename});
                response.setHeader("Content-Type", "application/pdf");
                response.setHeader(headerKey, headerValue);
                try (OutputStream outStream = response.getOutputStream()) {
                    outStream.write(decodeBase64(base64.getBytes()));
                }
            } else {
                boolean online = false;
                Outputf out = sign_document(pathtemp, cod, user, bra_loc, "_macnctaxf", base64, filename);
                if (out != null) {
                    if (out.getEsito().equals("0")) {
                        online = true;
                    }
                } else {
                    out = new Outputf();
                }
                if (online) {
                    String link = out.getLinkweb().replaceAll("5.83.104.161", replace);
                    if (pingURL(link)) {

                        //  user-agent: VALUE Mozilla/5.0 (Windows NT 6.3; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.113 Safari/537.36
                        //  user-agent: VALUE Mozilla/5.0 (Windows NT 6.3; WOW64; Trident/7.0; rv:11.0) like Gecko
                        //  user-agent: VALUE Mozilla/5.0 (Windows NT 10.0; WOW64; rv:55.0) Gecko/20100101 Firefox/55.0
                        if (browserCopyLinkFirma(request)) {
                            request.getSession().setAttribute("linkfirma", link);
                            redirect(request, response, "pagesign.jsp?nc=t&cod=" + cod);
                        } else {
                            redirect(request, response, link);
                        }

                    } else {
                        String headerKey = "Content-Disposition";
                        String sanitizedfilename = Utility.sanitizeFilename(filename);
                        String headerValue = format("inline; filename=\"%s\"", new Object[]{sanitizedfilename});
                        response.setHeader("Content-Type", "application/pdf");
                        response.setHeader(headerKey, headerValue);
                        try (OutputStream outStream = response.getOutputStream()) {
                            outStream.write(decodeBase64(base64.getBytes()));
                        }
                    }
                } else {
                    String headerKey = "Content-Disposition";
                    String sanitizedfilename = Utility.sanitizeFilename(filename);
                    String headerValue = format("inline; filename=\"%s\"", new Object[]{sanitizedfilename});
                    response.setHeader("Content-Type", "application/pdf");
                    response.setHeader(headerKey, headerValue);
                    try (OutputStream outStream = response.getOutputStream()) {
                        outStream.write(decodeBase64(base64.getBytes()));
                    }
                }
            }

        } else {
            redirect(request, response, "page_fnf.html");
        }
    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void autoc(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String user = (String) request.getSession().getAttribute("us_cod");
        if (user == null) {
            user = "9999";
        }

        Db_Master db = new Db_Master();
        String bra_loc = Utility.sanitize(db.getCodLocal(true)[0]);
        boolean onlyprint = db.get_branch(bra_loc).getFg_pad().equals("0");
        String pathtemp = Utility.sanitize(db.getPath("temp"));
        String codtr = Utility.sanitize(request.getParameter("codtr"));
        String codcl = Utility.sanitize(request.getParameter("codcl"));
        String dt = Utility.sanitize(request.getParameter("dt"));
        Client cl = db.query_Client_transaction(codtr, codcl);
        Ch_transaction ct = db.query_transaction_ch(codtr);
        if (ct == null) {
            ct = db.query_transaction_ch_temp(codtr);
        }
        CustomerKind ck = db.get_customerKind(ct.getTipocliente());
        String dt_tr = formatStringtoStringDate(dt, patternsqldate, patternnormdate_filter);
        String base64 = new Pdf().print_autocert(pathtemp, cl, dt_tr, ct, ck);
        db.closeDB();
        if (base64 != null) {
            String filename = new DateTime().toString("yyMMddHHmmssSSS") + codtr + "_macautoce.pdf";
            if (request.getParameter("down") != null && request.getParameter("down").equals("OK")) {
                onlyprint = true;
            } else if (request.getParameter("down") != null && request.getParameter("down").equals("OK1")) {

                String headerKey = "Content-Disposition";
                String sanitizedfilename = Utility.sanitizeFilename(filename);
                String headerValue = format("inline; filename=\"%s\"", new Object[]{sanitizedfilename});
                response.setHeader("Content-Type", "application/pdf");
                response.setHeader(headerKey, headerValue);
                try (OutputStream outStream = response.getOutputStream()) {
                    outStream.write(decodeBase64(base64.getBytes()));
                }
                return;
            }
            if (onlyprint) {
                String headerKey = "Content-Disposition";
                String sanitizedfilename = Utility.sanitizeFilename(filename);
                String headerValue = format("attachment; filename=\"%s\"", new Object[]{sanitizedfilename});
                response.setHeader("Content-Type", "application/pdf");
                response.setHeader(headerKey, headerValue);
                try (OutputStream outStream = response.getOutputStream()) {
                    outStream.write(decodeBase64(base64.getBytes()));
                }
            } else {
                boolean online = false;
                Outputf out = sign_document(pathtemp, codtr, user, bra_loc, "_macautoce", base64, filename);
                if (out != null) {
                    if (out.getEsito().equals("0")) {
                        online = true;
                    }
                } else {
                    out = new Outputf();
                }
                if (online) {
                    String link = out.getLinkweb().replaceAll("5.83.104.161", replace);
                    if (pingURL(link)) {

                        if (browserCopyLinkFirma(request)) {
                            request.getSession().setAttribute("linkfirma", link);
                            redirect(request, response, "pagesign.jsp");
                        } else {
                            redirect(request, response, link);
                        }
                    } else {
                        String headerKey = "Content-Disposition";
                        String sanitizedfilename = Utility.sanitizeFilename(filename);
                        String headerValue = format("inline; filename=\"%s\"", new Object[]{sanitizedfilename});
                        response.setHeader("Content-Type", "application/pdf");
                        response.setHeader(headerKey, headerValue);
                        try (OutputStream outStream = response.getOutputStream()) {
                            outStream.write(decodeBase64(base64.getBytes()));
                        }
                    }
                } else {
                    String headerKey = "Content-Disposition";
                    String sanitizedfilename = Utility.sanitizeFilename(filename);
                    String headerValue = format("inline; filename=\"%s\"", new Object[]{sanitizedfilename});
                    response.setHeader("Content-Type", "application/pdf");
                    response.setHeader(headerKey, headerValue);
                    try (OutputStream outStream = response.getOutputStream()) {
                        outStream.write(decodeBase64(base64.getBytes()));
                    }
                }
            }
        } else {
            redirect(request, response, "page_fnf.html");
        }
    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void creditnote(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String user = (String) request.getSession().getAttribute("us_cod");
        if (user == null) {
            user = "9999";
        }

        Db_Master db = new Db_Master();
        String bra_loc = Utility.sanitize(db.getCodLocal(true)[0]);
        String pathtemp = Utility.sanitize(db.getPath("temp"));
        String codtr = Utility.sanitize(request.getParameter("codtr"));
        String codcl = Utility.sanitize(request.getParameter("codcl"));
        Ch_transaction tra = db.query_transaction_ch(codtr);
        if (tra == null) {
            tra = db.query_transaction_ch_temp(codtr);
        }
        ArrayList<Ch_transaction_value> li = db.query_transaction_value(codtr);
        Client cl = db.query_Client_transaction(codtr, codcl);
        Office ma = db.get_national_office();
        Branch br = db.get_branch(tra.getFiliale());
        boolean onlyprint = br.getFg_pad().equals("0");
        ArrayList<Figures> fig = db.list_all_figures();
        ArrayList<Currency> cur = db.list_figures_query_edit(null);

        CustomerKind ck = db.get_customerKind(tra.getTipocliente());
        VATcode va  = db.get_vat_cod(ck.getVatcode());

        String base64 = new Receipt().print_credit_note(pathtemp, ma, tra, cl, li, br, fig, cur, va, ck,
                tra.getFiliale() + tra.getId(), tra.getPay(), true, tra.getPay(), tra.getPay(), true, true);
        //String base64 = new Receipt().print_credit_note(pathtemp, ma, tra, cl, li, br, fig, cur, null, ck, "refcode",  "netval", false);
        // String base64 = ""; //TO DO
        db.closeDB();
        String filename = new DateTime().toString("yyMMddHHmmssSSS") + codtr + "_maccredinot.pdf";
        if (base64 != null) {
            if (request.getParameter("down") != null && request.getParameter("down").equals("OK")) {
                onlyprint = true;
            } else if (request.getParameter("down") != null && request.getParameter("down").equals("OK1")) {

                String headerKey = "Content-Disposition";
                String sanitizedfilename = Utility.sanitizeFilename(filename);
                String headerValue = format("inline; filename=\"%s\"", new Object[]{sanitizedfilename});
                response.setHeader("Content-Type", "application/pdf");
                response.setHeader(headerKey, headerValue);
                try (OutputStream outStream = response.getOutputStream()) {
                    outStream.write(decodeBase64(base64.getBytes()));
                }
                return;
            }
            if (onlyprint) {
                String headerKey = "Content-Disposition";
                String sanitizedfilename = Utility.sanitizeFilename(filename);
                String headerValue = format("attachment; filename=\"%s\"", new Object[]{sanitizedfilename});
                response.setHeader("Content-Type", "application/pdf");
                response.setHeader(headerKey, headerValue);
                try (OutputStream outStream = response.getOutputStream()) {
                    outStream.write(decodeBase64(base64.getBytes()));
                }
            } else {
                boolean online = false;

                Outputf out = sign_document(pathtemp, codtr, user, bra_loc, "_maccredinot", base64, filename);
                if (out != null) {
                    if (out.getEsito().equals("0")) {
                        online = true;
                    }
                } else {
                    out = new Outputf();
                }
                if (online) {
                    String link = out.getLinkweb().replaceAll("5.83.104.161", replace);
                    if (pingURL(link)) {
                        if (browserCopyLinkFirma(request)) {
                            request.getSession().setAttribute("linkfirma", link);
                            redirect(request, response, "pagesign.jsp");
                        } else {
                            redirect(request, response, link);
                        }
                    } else {
                        String headerKey = "Content-Disposition";
                        String sanitizedfilename = Utility.sanitizeFilename(filename);
                        String headerValue = format("inline; filename=\"%s\"", new Object[]{sanitizedfilename});
                        response.setHeader("Content-Type", "application/pdf");
                        response.setHeader(headerKey, headerValue);
                        try (OutputStream outStream = response.getOutputStream()) {
                            outStream.write(decodeBase64(base64.getBytes()));
                        }
                    }
                } else {
                    String headerKey = "Content-Disposition";
                    String sanitizedfilename = Utility.sanitizeFilename(filename);
                    String headerValue = format("inline; filename=\"%s\"", new Object[]{sanitizedfilename});
                    response.setHeader("Content-Type", "application/pdf");
                    response.setHeader(headerKey, headerValue);
                    try (OutputStream outStream = response.getOutputStream()) {
                        outStream.write(decodeBase64(base64.getBytes()));
                    }
                }
            }
        } else {
            redirect(request, response, "page_fnf.html");
        }
    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void billextrasee(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Db_Master db = new Db_Master();
        String pathtemp = Utility.sanitize(db.getPath("temp"));
        String user = (String) request.getSession().getAttribute("us_cod");
        if (user == null) {
            user = "9999";
        }
        String bra_loc = Utility.sanitize(db.getCodLocal(true)[0]);
        String codtr = Utility.sanitize(request.getParameter("codtr"));
        String codcl = Utility.sanitize(request.getParameter("codcl"));
        Ch_transaction tra = db.query_transaction_ch(codtr);
        if (tra == null) {
            tra = db.query_transaction_ch_temp(codtr);
        }
        ArrayList<Ch_transaction_value> li = db.query_transaction_value(codtr);
        Client cl = db.query_Client_transaction(codtr, codcl);
        Office ma = db.get_national_office();
        Branch br = db.get_branch(tra.getFiliale());
        boolean onlyprint = br.getFg_pad().equals("0");
        ArrayList<Figures> fig = db.list_all_figures();
        ArrayList<Currency> cur = db.list_figures_query_edit(null);
        CustomerKind ck = db.get_customerKind(tra.getTipocliente());
        VATcode va  = db.get_vat_cod(ck.getVatcode());
        String base64 = Utility.sanitize(new Receipt().print_bill_extrasee_bollo(pathtemp, ma, tra, cl, li, br, fig, cur, va, ck, true));
        db.closeDB();
        String filename = new DateTime().toString("yyMMddHHmmssSSS") + codtr + "_macfaexsee.pdf";
        if (base64 != null) {
            if (request.getParameter("down") != null && request.getParameter("down").equals("OK")) {
                onlyprint = true;
            } else if (request.getParameter("down") != null && request.getParameter("down").equals("OK1")) {

                String cod = generaId(95);
                String payload = base64;

                redirect(request, response, "signature.jsp?cod=" + cod + "&payload=" + payload);

                return;
            }
            if (onlyprint) {
                String headerKey = "Content-Disposition";
                String sanitizedfilename = Utility.sanitizeFilename(filename);
                String headerValue = format("attachment; filename=\"%s\"", new Object[]{sanitizedfilename});
                response.setHeader("Content-Type", "application/pdf");
                response.setHeader(headerKey, headerValue);
                try (OutputStream outStream = response.getOutputStream()) {
                    outStream.write(decodeBase64(base64.getBytes()));
                }
            } else {

                boolean online = false;

                Outputf out = sign_document(pathtemp, codtr, user, bra_loc, "_macfaexsee", base64, filename);
                if (out != null) {
                    if (out.getEsito().equals("0")) {
                        online = true;
                    }
                } else {
                    out = new Outputf();
                }
                if (online) {
                    String link = out.getLinkweb().replaceAll("5.83.104.161", replace);

                    if (pingURL(link)) {
                        if (browserCopyLinkFirma(request)) {
                            request.getSession().setAttribute("linkfirma", link);
                            redirect(request, response, "pagesign.jsp");
                        } else {
                            redirect(request, response, link);
                        }
                    } else {

                        String headerKey = "Content-Disposition";
                        String sanitizedfilename = Utility.sanitizeFilename(filename);
                        String headerValue = format("inline; filename=\"%s\"", new Object[]{sanitizedfilename});
                        response.setHeader("Content-Type", "application/pdf");
                        response.setHeader(headerKey, headerValue);
                        try (OutputStream outStream = response.getOutputStream()) {
                            outStream.write(decodeBase64(base64.getBytes()));
                        }
                    }
                } else {
                    String headerKey = "Content-Disposition";
                    String sanitizedfilename = Utility.sanitizeFilename(filename);
                    String headerValue = format("inline; filename=\"%s\"", new Object[]{sanitizedfilename});
                    response.setHeader("Content-Type", "application/pdf");
                    response.setHeader(headerKey, headerValue);
                    try (OutputStream outStream = response.getOutputStream()) {
                        outStream.write(decodeBase64(base64.getBytes()));
                    }
                }
            }
        } else {
            redirect(request, response, "page_fnf.html");
        }
    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void receiptchangeIT(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        Db_Master db = new Db_Master();
//        String pathtemp = db.getPath("temp");
//        String user = (String) request.getSession().getAttribute("us_cod");
//        if (user == null) {
//            user = "9999";
//        }
//        String bra_loc = db.getCodLocal(true)[0];
//
//        String codtr = request.getParameter("codtr");
//        String codcl = request.getParameter("codcl");
//        Ch_transaction tra = db.query_transaction_ch(codtr);
//        if (tra == null) {
//            tra = db.query_transaction_ch_temp(codtr);
//        }
//        ArrayList<Ch_transaction_value> li = db.query_transaction_value(codtr);
//        Client cl = db.query_Client_transaction(codtr, codcl);
//        Office ma = db.get_national_office();
//        Branch br = db.get_branch(tra.getFiliale());
//        boolean onlyprint = br.getFg_pad().equals("0");
//        ArrayList<Figures> fig = db.list_all_figures();
//        ArrayList<Currency> cur = db.list_figures_query_edit(null);
//        String base64 = new Receipt().print_receipt_change_IT(pathtemp, ma, tra, cl, li, br, fig, cur);
//        db.closeDB();
//        String filename = new DateTime().toString("yyMMddHHmmssSSS") + codtr + "_macreceita.pdf";
//        if (base64 != null) {
//            if (request.getParameter("down") != null && request.getParameter("down").equals("OK")) {
//                onlyprint = true;
//            }
//            if (onlyprint) {
//                String headerKey = "Content-Disposition";
//                String headerValue = String.format("attachment; filename=\"%s\"", new Object[]{filename});
//                response.setHeader("Content-Type", "application/pdf");
//                response.setHeader(headerKey, headerValue);
//                OutputStream outStream = response.getOutputStream();
//                outStream.write(Base64.decodeBase64(base64.getBytes()));
//                outStream.close();
//            } else {
//                boolean online = false;
//
//                Outputf out = Sign.sign_document(pathtemp, codtr, user, bra_loc, "_macreceita", base64, filename);
//                if (out != null) {
//                    if (out.getEsito().equals("0")) {
//                        online = true;
//                    }
//                } else {
//                    out = new Outputf();
//                }
//                if (online) {
//                    String link = out.getLinkweb().replaceAll("5.83.104.161", replace);
//                    if (Utility.pingURL(link)) {
//                        if (browserCopyLinkFirma(request)) {
//                            request.getSession().setAttribute("linkfirma", link);
//                            redirect(request, response, "pagesign.jsp");
//                        } else {
//                            redirect(request, response, link);
//                        }
//                    } else {
//                        String headerKey = "Content-Disposition";
//                        String headerValue = String.format("inline; filename=\"%s\"", new Object[]{filename});
//                        response.setHeader("Content-Type", "application/pdf");
//                        response.setHeader(headerKey, headerValue);
//                        OutputStream outStream = response.getOutputStream();
//                        outStream.write(Base64.decodeBase64(base64.getBytes()));
//                        outStream.close();
//                    }
//                } else {
//                    String headerKey = "Content-Disposition";
//                    String headerValue = String.format("inline; filename=\"%s\"", new Object[]{filename});
//                    response.setHeader("Content-Type", "application/pdf");
//                    response.setHeader(headerKey, headerValue);
//                    OutputStream outStream = response.getOutputStream();
//                    outStream.write(Base64.decodeBase64(base64.getBytes()));
//                    outStream.close();
//                }
//            }
//        } else {
        redirect(request, response, "page_fnf.html");
//        }
    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void billextraseebollo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

//        Utility.printRequest(request);
        Db_Master db = new Db_Master();
        String pathtemp = Utility.sanitize(db.getPath("temp"));
        String user = (String) request.getSession().getAttribute("us_cod");
        if (user == null) {
            user = "9999";
        }
        String bra_loc = Utility.sanitize(db.getCodLocal(true)[0]);

        String codtr = Utility.sanitize(request.getParameter("codtr"));
        String codcl = Utility.sanitize(request.getParameter("codcl"));
        Ch_transaction tra = db.query_transaction_ch(codtr);
        if (tra == null) {
            tra = db.query_transaction_ch_temp(codtr);
        }
        ArrayList<Ch_transaction_value> li = db.query_transaction_value(codtr);
        Client cl = db.query_Client_transaction(codtr, codcl);
        Office ma = db.get_national_office();
        Branch br = db.get_branch(tra.getFiliale());
        boolean onlyprint = br.getFg_pad().equals("0");
        ArrayList<Figures> fig = db.list_all_figures();
        ArrayList<Currency> cur = db.list_figures_query_edit(null);

        CustomerKind ck = db.get_customerKind(tra.getTipocliente());
        VATcode va  = db.get_vat_cod(ck.getVatcode());
        String base64 = Utility.sanitize(new Receipt().print_bill_extrasee_bollo(pathtemp, ma, tra, cl, li, br, fig, cur, va, ck, true));
        db.closeDB();
        String filename = new DateTime().toString("yyMMddHHmmssSSS") + codtr + "_macfaexseebo.pdf";
        if (base64 != null) {
            if (request.getParameter("down") != null && request.getParameter("down").equals("OK")) {
                onlyprint = true;
            } else if (request.getParameter("down") != null && request.getParameter("down").equals("OK1")) {

                String headerKey = "Content-Disposition";
                String sanitizedfilename = Utility.sanitizeFilename(filename);
                String headerValue = format("inline; filename=\"%s\"", new Object[]{sanitizedfilename});
                response.setHeader("Content-Type", "application/pdf");
                response.setHeader(headerKey, headerValue);
                try (OutputStream outStream = response.getOutputStream()) {
                    outStream.write(decodeBase64(base64.getBytes()));
                }
                return;
            }
            if (onlyprint) {
                String headerKey = "Content-Disposition";
                String sanitizedfilename = Utility.sanitizeFilename(filename);
                String headerValue = format("attachment; filename=\"%s\"", new Object[]{sanitizedfilename});
                response.setHeader("Content-Type", "application/pdf");
                response.setHeader(headerKey, headerValue);
                try (OutputStream outStream = response.getOutputStream()) {
                    outStream.write(decodeBase64(base64.getBytes()));
                }
            } else {
                boolean online = false;

                Outputf out = sign_document(pathtemp, codtr, user, bra_loc, "_macfaexseebo", base64, filename);
                if (out != null) {
                    if (out.getEsito().equals("0")) {
                        online = true;
                    }
                } else {
                    out = new Outputf();
                }
                if (online) {
                    String link = out.getLinkweb().replaceAll("5.83.104.161", replace);
                    if (pingURL(link)) {
                        if (browserCopyLinkFirma(request)) {
                            request.getSession().setAttribute("linkfirma", link);
                            redirect(request, response, "pagesign.jsp");
                        } else {
                            redirect(request, response, link);
                        }
                    } else {
                        String headerKey = "Content-Disposition";
                        String sanitizedfilename = Utility.sanitizeFilename(filename);
                        String headerValue = format("inline; filename=\"%s\"", new Object[]{sanitizedfilename});
                        response.setHeader("Content-Type", "application/pdf");
                        response.setHeader(headerKey, headerValue);
                        try (OutputStream outStream = response.getOutputStream()) {
                            outStream.write(decodeBase64(base64.getBytes()));
                        }
                    }
                } else {
                    String headerKey = "Content-Disposition";
                    String sanitizedfilename = Utility.sanitizeFilename(filename);
                    String headerValue = format("inline; filename=\"%s\"", new Object[]{sanitizedfilename});
                    response.setHeader("Content-Type", "application/pdf");
                    response.setHeader(headerKey, headerValue);
                    try (OutputStream outStream = response.getOutputStream()) {
                        outStream.write(decodeBase64(base64.getBytes()));
                    }
                }
            }
        } else {
            redirect(request, response, "page_fnf.html");
        }
    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void scontrinoUK(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Db_Master db = new Db_Master();
        String pathtemp = Utility.sanitize(db.getPath("temp"));

        String codtr = Utility.sanitize(request.getParameter("codtr"));
        String codcl = Utility.sanitize(request.getParameter("codcl"));
        Ch_transaction tra = db.query_transaction_ch(codtr);
        if (tra == null) {
            tra = db.query_transaction_ch_temp(codtr);
        }
        ArrayList<Ch_transaction_value> li = db.query_transaction_value(codtr);
        Client cl = db.query_Client_transaction(codtr, codcl);
        Office ma = db.get_national_office();
        String base64 = Utility.sanitize(new Txt().scontrino_UK(pathtemp, ma, tra, cl, li));
        db.closeDB();

        if (base64 != null) {
            String filename = new DateTime().toString("yyMMddHHmmssSSS") + codtr + "_scuk.pdf";
            String headerKey = "Content-Disposition";
            String sanitizedfilename = Utility.sanitizeFilename(filename);
            String headerValue = format("inline; filename=\"%s\"", new Object[]{sanitizedfilename});
            response.setHeader("Content-Type", "application/pdf");
            response.setHeader(headerKey, headerValue);
            try (OutputStream outStream = response.getOutputStream()) {
                outStream.write(decodeBase64(base64.getBytes()));
            }
        } else {
            redirect(request, response, "page_fnf.html");
        }

    }

    /**
     *
     * @param codtr
     * @param codcl
     * @param type
     * @return
     * @throws ServletException
     * @throws IOException
     */
    protected String scontrino_CZ(String codtr, String codcl, String type) throws ServletException, IOException {

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
        String base64 = null;
        db.closeDB();

        if (type.equals("presell") || type.equals("prebuy")) {
            base64 = Utility.sanitize(new NewReceipt_CZ().prericevuta_CZ(pathtemp, ma, tra, cl, li, br));
        } else if (type.equals("czsell") || type.equals("czbuy")) {
            base64 = Utility.sanitize(new NewReceipt_CZ().ricevuta_CZ_DOUBLE(pathtemp, ma, tra, cl, li, br));
        }

        return base64;
    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void mod_prof_client(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Db_Master db = new Db_Master();
        String pathtemp = db.getPath("temp");
        String user = (String) request.getSession().getAttribute("us_cod");
        if (user == null) {
            user = "9999";
        }
        String bra_loc = db.getCodLocal(true)[0];

        String cod = request.getParameter("cod");
        Ch_transaction tra = db.query_transaction_ch(cod);
        if (tra == null) {
            tra = db.query_transaction_ch_temp(cod);
        }
        ArrayList<Ch_transaction_value> li = db.query_transaction_value(cod);
        Client cl = db.query_Client_transaction(cod, tra.getCl_cod());
        Office ma = db.get_national_office();
        Branch br = db.get_branch(tra.getFiliale());
        boolean onlyprint = br.getFg_pad().equals("0");
        String iss = request.getParameter("iss");
        String cust = request.getParameter("cust");
        String cust_det = request.getParameter("cust_det");
        String ann = request.getParameter("ann");
        String main1 = request.getParameter("main1");
        String main1_val = request.getParameter("main1_val");
        String main2 = request.getParameter("main2");
        String main2_val = request.getParameter("main2_val");
        String main3 = request.getParameter("main3");
        String main3_val = request.getParameter("main3_val");

        if (request.getParameter("down") != null && request.getParameter("down").equals("OK")) {
            iss = "";
            cust = "";
            cust_det = "";
            ann = "";
            main1 = "";
            main1_val = "";
            main2 = "";
            main2_val = "";
            main3 = "";
            main3_val = "";

        } else {
            db.insert_mod_profcl(tra.getCl_cod(), cod, iss, cust, cust_det, ann, main1, main1_val, main2, main2_val, main3, main3_val, new DateTime().toString(patternsqldate));
        }

        String base64 = Utility.sanitize(modulo_profilatura(pathtemp, ma, tra, cl, li, br, iss, cust, cust_det, ann, main1, main1_val, main2, main2_val, main3, main3_val));

        db.closeDB();
        String filename = new DateTime().toString("yyMMddHHmmssSSS") + cod + "_macprofcl.pdf";
        if (base64 != null) {
            if (request.getParameter("down") != null && request.getParameter("down").equals("OK")) {
                onlyprint = true;
            } else if (request.getParameter("down") != null && request.getParameter("down").equals("OK1")) {

                String headerKey = "Content-Disposition";
                String sanitizedfilename = Utility.sanitizeFilename(filename);
                String headerValue = format("inline; filename=\"%s\"", new Object[]{sanitizedfilename});
                response.setHeader("Content-Type", "application/pdf");
                response.setHeader(headerKey, headerValue);
                try (OutputStream outStream = response.getOutputStream()) {
                    outStream.write(decodeBase64(base64.getBytes()));
                }
                return;
            }
            if (onlyprint) {
                String headerKey = "Content-Disposition";
                String sanitizedfilename = Utility.sanitizeFilename(filename);
                String headerValue = format("inline; filename=\"%s\"", new Object[]{sanitizedfilename});
                response.setHeader("Content-Type", "application/pdf");
                response.setHeader(headerKey, headerValue);
                try (OutputStream outStream = response.getOutputStream()) {
                    outStream.write(decodeBase64(base64.getBytes()));
                }
            } else {
                boolean online = false;

                Outputf out = sign_document(pathtemp, cod, user, bra_loc, "_macprofcl", base64, filename);
                if (out != null) {
                    if (out.getEsito().equals("0")) {
                        online = true;
                    }
                } else {
                    out = new Outputf();
                }
                if (online) {
                    String link = out.getLinkweb().replaceAll("5.83.104.161", replace);
                    if (pingURL(link)) {
                        if (browserCopyLinkFirma(request)) {
                            request.getSession().setAttribute("linkfirma", link);
                            redirect(request, response, "pagesign.jsp");
                        } else {
                            redirect(request, response, link);
                        }
                    } else {
                        String headerKey = "Content-Disposition";
                        String sanitizedfilename = Utility.sanitizeFilename(filename);
                        String headerValue = format("inline; filename=\"%s\"", new Object[]{sanitizedfilename});
                        response.setHeader("Content-Type", "application/pdf");
                        response.setHeader(headerKey, headerValue);
                        try (OutputStream outStream = response.getOutputStream()) {
                            outStream.write(decodeBase64(base64.getBytes()));
                        }
                    }
                } else {
                    String headerKey = "Content-Disposition";
                    String sanitizedfilename = Utility.sanitizeFilename(filename);
                    String headerValue = format("inline; filename=\"%s\"", new Object[]{sanitizedfilename});
                    response.setHeader("Content-Type", "application/pdf");
                    response.setHeader(headerKey, headerValue);
                    try (OutputStream outStream = response.getOutputStream()) {
                        outStream.write(decodeBase64(base64.getBytes()));
                    }
                }
            }
        } else {
            redirect(request, response, "page_fnf.html");
        }

    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void receiptrefund(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String user = (String) request.getSession().getAttribute("us_cod");
        if (user == null) {
            user = "9999";
        }
        String cod_tr = Utility.sanitize(request.getParameter("codtr"));
        String datetime = new DateTime().toString(patternnormdate_filter);
        Db_Master db = new Db_Master();
        String pathtemp = Utility.sanitize(db.getPath("temp"));
        String localfig = Utility.sanitize(db.get_local_currency()[0]);
        String fili = Utility.sanitize(db.getCodLocal(true)[0]);
        Ch_transaction tr = db.query_transaction_ch(cod_tr);
        if (tr == null) {
            tr = db.query_transaction_ch_temp(cod_tr);
        }
        Client cl = db.query_Client_transaction(cod_tr, tr.getCl_cod());
        ArrayList<Branch> brl = db.list_branch();
        ArrayList<Ch_transaction_value> tr_value = db.query_transaction_value(cod_tr);
        Ch_transaction_refund ref = db.get_refund_trans(cod_tr);

        String base64;
        String filename;
        String typefile = "_macrefund";
        Branch br = db.get_branch(tr.getFiliale());
        boolean onlyprint = br.getFg_pad().equals("0");

        if (is_CZ) {

            Branch br2 = db.get_branch(ref.getBranch_cod());

            base64 = Utility.sanitize(refund_CZ(pathtemp, ref.getType().equals("CO"),
                    ref.getValue(), tr.getTipotr(),
                    tr_value.get(0).getNet(),
                    tr_value.get(0).getQuantita(),
                    tr_value.get(0).getValuta(),
                    tr.getId(),
                    formatStringtoStringDate(tr.getData(), patternsqldate, patternnormdate_f),
                    br.getCod() + " " + (br.getAdd_via() + ", " + br.getAdd_cap() + ", " + br.getAdd_city()).toUpperCase(),
                    br2.getCod() + " " + (br2.getAdd_via() + ", " + br2.getAdd_cap() + ", " + br2.getAdd_city()).toUpperCase(),
                    formatStringtoStringDate(ref.getDt_refund(), patternsqldate, patternnormdate_f)));

            filename = new DateTime().toString("yyMMddHHmmssSSS") + cod_tr + typefile + ".pdf";

        } else {
            if (!tr.getCn_number().equals("") && !tr.getCn_number().equals("-")) {

                typefile = "_maccredinot";

                Office ma = db.get_national_office();

                ArrayList<Figures> fig = db.list_all_figures();
                ArrayList<Currency> cur = db.list_figures_query_edit(null);
                CustomerKind ck = db.get_customerKind(tr.getTipocliente());
                VATcode va  = db.get_vat_cod(ck.getVatcode());

                boolean complete = false;
                String net = tr.getPay();
                String im1 = tr.getPay();
                String im2 = tr.getPay();

                if (ref.getType().equals("CO")) {
                    complete = true;
                } else {
                    net = ref.getValue();
                    im1 = ref.getValue();
                    im2 = ref.getValue();
                }

                base64 = new Receipt().print_credit_note(pathtemp, ma, tr, cl, tr_value, br,
                        fig, cur, va, ck, tr.getFiliale() + tr.getId(),
                        net, complete, im1, im2, complete, true);

                filename = new DateTime().toString("yyMMddHHmmssSSS") + cod_tr + typefile + ".pdf";

            } else {

                String importo = "";
                if (ref.getType().equals("CO") && tr.getTipotr().equals("B")) {

                    for (int i = 0; i < tr_value.size(); i++) {
                        importo = importo + formatMysqltoDisplay(Utility.sanitize(tr_value.get(i).getQuantita())) + " " + Utility.sanitize(tr_value.get(i).getValuta()) + ", ";
                    }
                    if (importo.length() > 0) {
                        importo = importo.trim();
                        importo = importo.substring(0, importo.length() - 1);
                    }

                } else {
                    importo = formatMysqltoDisplay(Utility.sanitize(ref.getValue())) + " " + Utility.sanitize(localfig);
                }

                String model = db.getConf("path.refund.pdf");

                base64 = refund(pathtemp,
                        decodeBase64(model),
                        cl.getCognome() + " " + cl.getNome(),
                        importo,
                        tr.getFiliale() + tr.getId(),
                        formatStringtoStringDate(tr.getData(), patternsqldate, patternnormdate),
                        formatBankBranch(tr.getFiliale(), "BR", null, brl, null),
                        datetime);
                filename = new DateTime().toString("yyMMddHHmmssSSS") + cod_tr + typefile + ".pdf";
            }
        }

        db.closeDB();

        if (base64 != null) {
            if (request.getParameter("down") != null && request.getParameter("down").equals("OK")) {
                onlyprint = true;
            } else if (request.getParameter("down") != null && request.getParameter("down").equals("OK1")) {

                String headerKey = "Content-Disposition";
                String sanitizedfilename = Utility.sanitizeFilename(filename);
                String headerValue = format("inline; filename=\"%s\"", new Object[]{sanitizedfilename});
                response.setHeader("Content-Type", "application/pdf");
                response.setHeader(headerKey, headerValue);
                try (OutputStream outStream = response.getOutputStream()) {
                    outStream.write(decodeBase64(base64.getBytes()));
                }
                return;
            }
            if (onlyprint) {
                String headerKey = "Content-Disposition";
                String sanitizedfilename = Utility.sanitizeFilename(filename);
                String headerValue = format("attachment; filename=\"%s\"", new Object[]{sanitizedfilename});
                response.setHeader("Content-Type", "application/pdf");
                response.setHeader(headerKey, headerValue);
                try (OutputStream outStream = response.getOutputStream()) {
                    outStream.write(decodeBase64(base64.getBytes()));
                }
            } else {
                boolean online = false;

                Outputf out = sign_document(pathtemp, cod_tr, user, fili, typefile, base64, filename);
                if (out != null) {
                    if (out.getEsito().equals("0")) {
                        online = true;
                    }
                } else {
                    out = new Outputf();
                }
                if (online) {
                    String link = out.getLinkweb().replaceAll("5.83.104.161", replace);
                    if (pingURL(link)) {
                        if (browserCopyLinkFirma(request)) {
                            request.getSession().setAttribute("linkfirma", link);
                            redirect(request, response, "pagesign.jsp");
                        } else {
                            redirect(request, response, link);
                        }
                    } else {
                        String headerKey = "Content-Disposition";
                        String sanitizedfilename = Utility.sanitizeFilename(filename);
                        String headerValue = format("inline; filename=\"%s\"", new Object[]{sanitizedfilename});
                        response.setHeader("Content-Type", "application/pdf");
                        response.setHeader(headerKey, headerValue);
                        try (OutputStream outStream = response.getOutputStream()) {
                            outStream.write(decodeBase64(base64.getBytes()));
                        }
                    }
                } else {
                    String headerKey = "Content-Disposition";
                    String sanitizedfilename = Utility.sanitizeFilename(filename);
                    String headerValue = format("inline; filename=\"%s\"", new Object[]{sanitizedfilename});
                    response.setHeader("Content-Type", "application/pdf");
                    response.setHeader(headerKey, headerValue);
                    try (OutputStream outStream = response.getOutputStream()) {
                        outStream.write(decodeBase64(base64.getBytes()));
                    }
                }
            }
        } else {
            redirect(request, response, "page_fnf.html");
        }
    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void excel_history_rate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Db_Master db = new Db_Master();
        String branch = parseArrayValues(request.getParameterValues("branch"));
        ArrayList<Branch> libr = db.list_branch_completeAFTER311217();
        ArrayList<String> br1 = parseString(branch, ";");
        if (br1.isEmpty()) {
            br1 = (ArrayList<String>) libr.stream().map(valore -> valore.getCod()).distinct().collect(toList());
        }

        String d3 = request.getParameter("d3");
        String d4 = request.getParameter("d4");
        String cur_code = request.getParameter("cur_code");

        String data1 = formatStringtoStringDate_null(d3, patternnormdate_filter, patternsql);
        String data2 = formatStringtoStringDate_null(d4, patternnormdate_filter, patternsql);

        ArrayList<Rate_history> re = new ArrayList<>();

        for (int x = 0; x < br1.size(); x++) {
            re.addAll(db.list_ratehistory(br1.get(x), cur_code, data1, data2));
        }

        String pathtemp = db.getPath("temp");
        Currency cu = db.getCurrency(cur_code);

        //pathtemp = "F:\\com\\";
        ArrayList<Users> lius = db.list_all_users();
        String datetime = new DateTime().toString(patternnormdate_filter);
        String base64 = Utility.sanitize(new Excel().print_excel_historyrate(pathtemp, re, datetime, libr, lius, cu));
        db.closeDB();

        if (base64 != null) {
            String filename = new DateTime().toString("yyMMddHHmmssSSS") + "excel_history_rate.xlsx";
            String headerKey = "Content-Disposition";
            String sanitizedfilename = Utility.sanitizeFilename(filename);
            String headerValue = format("attachment; filename=\"%s\"", new Object[]{sanitizedfilename});
            response.setHeader("Content-Type", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setHeader(headerKey, headerValue);
            try (OutputStream outStream = response.getOutputStream()) {
                outStream.write(decodeBase64(base64.getBytes()));
            }
        } else {
            redirect(request, response, "page_fnf.html");
        }

    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void reprintdocUKreceipt(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String cod = Utility.sanitize(request.getParameter("cod"));
        Db_Master db = new Db_Master();
        String pathtemp = Utility.sanitize(db.getPath("temp"));
        Ch_transaction_doc chd = db.get_tr_doc(cod);
        db.closeDB();
        if (chd != null) {
            File txt = new File(pathtemp + generaId(50) + "_" + Utility.sanitize(chd.getNomefile()));
            writeByteArrayToFile(txt, decodeBase64(Utility.sanitize(chd.getContent())));
            if (txt.canRead()) {
                boolean es = printFile(txt);
                insertTR("I", (String) request.getSession().getAttribute("us_cod"),
                        (String) request.getSession().getAttribute("us_fil") + " - PRINT RECEIPT UK " + cod + " : " + es);
            }
            redirect(request, response, "transaction_reprint.jsp?cod=" + chd.getCodtr());
        } else {
            redirect(request, response, "page_fnf.html");
        }
    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void printdocUKreceipt(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String cod = Utility.sanitize(request.getParameter("cod"));
        Db_Master db = new Db_Master();
        String pathtemp = Utility.sanitize(db.getPath("temp"));
        Ch_transaction_doc chd = db.get_tr_doc(cod);
        db.closeDB();
        if (chd != null) {
            File txt = new File(pathtemp + generaId(50) + "_" + Utility.sanitize(chd.getNomefile()));
            writeByteArrayToFile(txt, decodeBase64(Utility.sanitize(chd.getContent())));
            if (txt.canRead()) {
                boolean es = printFile(txt);
                insertTR("I", (String) request.getSession().getAttribute("us_cod"),
                        (String) request.getSession().getAttribute("us_fil") + " - PRINT RECEIPT UK " + cod + " : " + es);
            }
            redirect(request, response, "transaction_ok_mod_new.jsp?cod=" + chd.getCodtr());
        } else {
            redirect(request, response, "page_fnf.html");
        }

    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void heavytransactionform(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String codtr = Utility.sanitize(request.getParameter("codtr"));
        String codcl = Utility.sanitize(request.getParameter("codcl"));

        Db_Master db = new Db_Master();
        String pathout =Utility.sanitize(db.getPath("temp"));
        Ch_transaction tr = db.query_transaction_ch_temp(codtr);
        ArrayList<Ch_transaction_value> val = db.query_transaction_value(codtr);
        Client cl = db.query_Client_transaction(codtr, codcl);
        Branch br = db.get_branch(tr.getFiliale());
        String model = Utility.sanitize(db.getConf("path.heavyuk.pdf"));
        db.closeDB();

        byte[] pdfingresso = decodeBase64(model);
//      byte[] pdfingresso = FileUtils.readFileToByteArray(new File("C:\\mnt\\mac\\Heavy Transaction.pdf"));

        String base64 = heavyUK(pathout, pdfingresso, tr, val, cl, br);
        if (base64 != null) {
            String headerKey = "Content-Disposition";
            String headerValue = format("inline; filename=\"%s\"", new Object[]{new DateTime().toString("yyMMddHHmmssSSS") + ".pdf"});
            response.setHeader("Content-Type", "application/pdf");
            response.setHeader(headerKey, headerValue);
            try (OutputStream outStream = response.getOutputStream()) {
                outStream.write(decodeBase64(base64.getBytes()));
            }
        } else {
            redirect(request, response, "page_fnf.html");
        }

    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void prebuy(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String type = Utility.sanitize(request.getParameter("type"));
        String codtr = Utility.sanitize(request.getParameter("codtr"));
        String codcl = Utility.sanitize(request.getParameter("codcl"));

        String base64;
        ArrayList<Ch_transaction_doc> doc = get_list_tr_doc(codtr);
        Ch_transaction_doc dc1 = formatfromlist_doc(doc, "_prebuyl");
        if (dc1 == null) {
            insert_CZ_prereceipt(codtr, codcl);
            base64 = scontrino_CZ(codtr, codcl, type);
        } else {
            base64 = dc1.getContent();
        }

        String laststring = "_czb_preric.pdf";

        if (base64 != null) {
            Db_Master db = new Db_Master();
            String pathtemp = Utility.sanitize(db.getPath("temp"));
            db.closeDB();
            File txt = new File(pathtemp + generaId(50) + "_" + laststring);
            writeByteArrayToFile(txt, decodeBase64(base64));
            if (txt.canRead()) {

                if (request.getParameter("r") == null) {
                    boolean es = printFile(txt);
                    insertTR("I", (String) request.getSession().getAttribute("us_cod"),
                            (String) request.getSession().getAttribute("us_fil") + " - PRINT PRERECEIPT CZ " + codtr + " : " + es);
                    redirect(request, response, "transaction_ok_mod_new.jsp?cod=" + codtr);
                } else {
                    String headerKey = "Content-Disposition";
                    String headerValue = format("attachment; filename=\"%s\"", new Object[]{new DateTime().toString("yyMMddHHmmssSSS") + laststring});
                    response.setHeader("Content-Type", "text/plain");
                    response.setHeader(headerKey, headerValue);
                    try (OutputStream outStream = response.getOutputStream()) {
                        outStream.write(decodeBase64(base64.getBytes()));
                    }
                }
            } else {
                redirect(request, response, "page_fnf.html");
            }
        } else {
            redirect(request, response, "page_fnf.html");
        }

//        if (type.endsWith("1")) {
//            laststring = "_czs_preric.pdf";
//        } else if (type.endsWith("2")) {
//            laststring = "_czb_preric.pdf";
//        } else if (type.endsWith("3")) {
//            laststring = "_czs_scon.pdf";
//        } else if (type.endsWith("4")) {
//            laststring = "_czb_scon.pdf";
//        } else if (type.endsWith("5")) {
//            laststring = "_czs_annul.pdf";
//        } else if (type.endsWith("6")) {
//            laststring = "_czb_annul.pdf";
//        }
    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void presell(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String type = Utility.sanitize(request.getParameter("type"));
        String codtr = Utility.sanitize(request.getParameter("codtr"));
        String codcl = Utility.sanitize(request.getParameter("codcl"));

        String base64;
        ArrayList<Ch_transaction_doc> doc = get_list_tr_doc(codtr);
        Ch_transaction_doc dc1 = formatfromlist_doc(doc, "_presell");
        if (dc1 == null) {
            Utility.sanitize(insert_CZ_prereceipt(codtr, codcl));
            base64 = scontrino_CZ(codtr, codcl, type);
        } else {
            base64 = Utility.sanitize(dc1.getContent());
        }

        String laststring = "_czs_preric.pdf";

        if (base64 != null) {
            Db_Master db = new Db_Master();
            String pathtemp = Utility.sanitize(db.getPath("temp"));
            db.closeDB();
            File txt = new File(pathtemp + generaId(50) + "_" + laststring);
            writeByteArrayToFile(txt, decodeBase64(base64));
            if (txt.canRead()) {
                if (request.getParameter("r") == null) {
                    boolean es = printFile(txt);
                    insertTR("I", (String) request.getSession().getAttribute("us_cod"),
                            (String) request.getSession().getAttribute("us_fil") + " - PRINT PRERECEIPT CZ " + codtr + " : " + es);
                    redirect(request, response, "transaction_ok_mod_new.jsp?cod=" + codtr);
                } else {

                    String headerKey = "Content-Disposition";
                    String headerValue = format("attachment; filename=\"%s\"", new Object[]{new DateTime().toString("yyMMddHHmmssSSS") + laststring});
                    response.setHeader("Content-Type", "text/plain");
                    response.setHeader(headerKey, headerValue);
                    try (OutputStream outStream = response.getOutputStream()) {
                        outStream.write(decodeBase64(base64.getBytes()));
                    }
                }
            } else {
                redirect(request, response, "page_fnf.html");
            }
        } else {
            redirect(request, response, "page_fnf.html");
        }
    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void czsell(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String type = Utility.sanitize(request.getParameter("type"));
        String codtr = Utility.sanitize(request.getParameter("codtr"));
        String codcl = Utility.sanitize(request.getParameter("codcl"));

        String base64;
        ArrayList<Ch_transaction_doc> doc = get_list_tr_doc(codtr);
        Ch_transaction_doc dc1 = formatfromlist_doc(doc, "_czsell");
        if (dc1 == null) {
            Utility.sanitize(insert_CZ_receipt(codtr, codcl));
            base64 = scontrino_CZ(codtr, codcl, type);
        } else {
            base64 = Utility.sanitize(dc1.getContent());
        }

        String laststring = "_czs_scon.pdf";

        if (base64 != null) {
            Db_Master db = new Db_Master();
            String pathtemp = Utility.sanitize(db.getPath("temp"));
            db.closeDB();

            File txt = new File(pathtemp + generaId(50) + "_" + laststring);
            writeByteArrayToFile(txt, decodeBase64(base64));
            if (txt.canRead()) {

                if (request.getParameter("r") == null) {
                    boolean es = printFile(txt);
                    insertTR("I", (String) request.getSession().getAttribute("us_cod"),
                            (String) request.getSession().getAttribute("us_fil") + " - PRINT RECEIPT CZ " + codtr + " : " + es);
                    redirect(request, response, "transaction_ok_mod_new.jsp?cod=" + codtr);
                } else {
                    String headerKey = "Content-Disposition";
                    String headerValue = format("attachment; filename=\"%s\"", new Object[]{new DateTime().toString("yyMMddHHmmssSSS") + laststring});
                    response.setHeader("Content-Type", "text/plain");
                    response.setHeader(headerKey, headerValue);
                    try (OutputStream outStream = response.getOutputStream()) {
                        outStream.write(decodeBase64(base64.getBytes()));
                    }
                }
            } else {
                redirect(request, response, "page_fnf.html");
            }
        } else {
            redirect(request, response, "page_fnf.html");
        }
    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void czbuy(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String codtr = Utility.sanitize(request.getParameter("codtr"));
        String codcl = Utility.sanitize(request.getParameter("codcl"));

        Db_Master db = new Db_Master();
        String pathtemp = db.getPath("temp");
        db.closeDB();

        String base64;
        ArrayList<Ch_transaction_doc> doc = get_list_tr_doc(codtr);
        Ch_transaction_doc dc1 = formatfromlist_doc(doc, "_czbuy");
        if (dc1 == null) {
            base64 = Utility.sanitize(insert_CZ_receipt(codtr, codcl));
        } else {
            base64 = Utility.sanitize(dc1.getContent());
        }

        String laststring = "_czb_scon.pdf";

        if (base64 != null) {

            File txt = new File(pathtemp + generaId(50) + "_" + laststring);
            writeByteArrayToFile(txt, decodeBase64(base64));
            if (txt.canRead()) {

                if (request.getParameter("r") == null) {
                    boolean es = printFile(txt);
                    insertTR("I", (String) request.getSession().getAttribute("us_cod"),
                            (String) request.getSession().getAttribute("us_fil") + " - PRINT RECEIPT CZ " + codtr + " : " + es);
                    redirect(request, response, "transaction_ok_mod_new.jsp?cod=" + codtr);
                } else {
                    String headerKey = "Content-Disposition";
                    String headerValue = format("attachment; filename=\"%s\"", new Object[]{new DateTime().toString("yyMMddHHmmssSSS") + laststring});
                    response.setHeader("Content-Type", "text/plain");
                    response.setHeader(headerKey, headerValue);
                    try (OutputStream outStream = response.getOutputStream()) {
                        outStream.write(decodeBase64(base64.getBytes()));
                    }
                }
            } else {
                redirect(request, response, "page_fnf.html");
            }
        } else {
            redirect(request, response, "page_fnf.html");
        }
    }

    /**
     *
     * @param request
     * @param response
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) {
        try {
            String link_value = verifySession(request);
            if (link_value != null) {
                redirect(request, response, link_value);
            } else {
                response.setContentType("text/html;charset=UTF-8");
//                request.setCharacterEncoding("UTF-8");
                String type = request.getParameter("type");
                if (type.equals("autoc")) {
                    autoc(request, response);
                } else if (type.equals("receiptcommnotzero")) {
                    receiptcommnotzero(request, response);
                } else if (type.equals("receiptsee")) {
                    receiptsee(request, response);
                } else if (type.equals("creditnote")) {
                    creditnote(request, response);
                } else if (type.equals("billextrasee")) {
                    billextrasee(request, response);
                } else if (type.equals("billextraseebollo")) {
                    billextraseebollo(request, response);
                } else if (type.equals("receiptchangeIT")) {
                    receiptchangeIT(request, response);
                } else if (type.equals("scontrinoUK")) {
                    scontrinoUK(request, response);
                } else if (type.equals("mod_prof_client")) {
                    mod_prof_client(request, response);
                } else if (type.startsWith("excel_history_rate")) {
                    excel_history_rate(request, response);
                } else if (type.startsWith("receiptrefund")) {
                    receiptrefund(request, response);
                } else if (type.startsWith("sign_nochange_taxfree")) {
                    sign_nochange_taxfree(request, response);
                } else if (type.equals("prebuy")) {
                    prebuy(request, response);
                } else if (type.equals("czbuy")) {
                    czbuy(request, response);
                } else if (type.equals("czsell")) {
                    czsell(request, response);
                } else if (type.equals("reprintdocUKreceipt")) {
                    reprintdocUKreceipt(request, response);
                } else if (type.equals("printdocUKreceipt")) {
                    printdocUKreceipt(request, response);
                } else if (type.equals("_heavy")) {
                    heavytransactionform(request, response);
                } else if (type.equals("presell")) {
                    presell(request, response);
                }

            }
        } catch (ServletException | IOException ex) {
            insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
