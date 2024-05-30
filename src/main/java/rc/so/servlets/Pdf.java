/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rc.so.servlets;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import static com.google.gson.JsonParser.parseString;
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
import rc.so.entity.VATcode;
import static rc.so.pdf.Pdf.modulo_profilatura;
import static rc.so.pdf.Pdf.refund;
import rc.so.pdf.Receipt;
import rc.so.util.Utility;

import static rc.so.util.Constant.newpad;
import static rc.so.util.Constant.patternnormdate;
import static rc.so.util.Constant.patternnormdate_filter;
import static rc.so.util.Constant.patternsqldate;
import static rc.so.util.Engine.formatBankBranchReport;
import static rc.so.util.Engine.insertTR;
import static rc.so.util.Engine.is_marketing_OK;
import static rc.so.util.Engine.query_LOY_transaction;
import static rc.so.util.Engine.query_transaction_ch_temp;
import static rc.so.util.Engine.remove_loy_assign_first_NEW;
import static rc.so.util.Engine.remove_nochange_transaction;
import static rc.so.util.Utility.formatMysqltoDisplay;
import static rc.so.util.Utility.formatStringtoStringDate;
import static rc.so.util.Utility.generaId;
import static rc.so.util.Utility.parseIntR;
import static rc.so.util.Utility.redirect;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import static java.lang.Thread.currentThread;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import static org.apache.commons.codec.binary.Base64.decodeBase64;
import org.joda.time.DateTime;

/**
 *
 * @author rcosco
 *
 *
 */
public class Pdf extends HttpServlet {

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void versign(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        BufferedReader vìbr = new BufferedReader(new InputStreamReader(request.getInputStream()));
        String line;
        while ((line = vìbr.readLine()) != null) {
            insertTR("I", "SIGN", line);
        }

    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void addsign(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String json = new BufferedReader(new InputStreamReader(request.getInputStream())).readLine();

        if (json != null) {
            JsonElement je = parseString(json);
            JsonObject root = je.getAsJsonObject();
            String Token = root.get("Token").getAsString().trim();
            String codtr = root.get("codtr").getAsString().trim();
            String codcl = root.get("codcl").getAsString().trim();
            String typesign = root.get("typesign").getAsString().trim();
            String base64 = root.get("Payload").getAsString().trim();

            if (!base64.equals("")) {
                Db_Master db = new Db_Master();

                if (typesign.equals("_macnctaxf")) {
                    String dateoper = new DateTime().toString(patternsqldate);
                    String filename = new DateTime().toString("yyMMddHHmmssSSS") + codtr + typesign + ".pdf";
                    Ch_transaction_doc chd = new Ch_transaction_doc(Token, codtr, typesign, base64,
                            filename, dateoper, codcl, "Y");
                    db.insert_transaction_doc(chd);
                } else {
                    Ch_transaction tr = db.query_transaction_ch(codtr);
                    if (tr == null) {
                        tr = db.query_transaction_ch_temp(codtr);
                    }
                    if (tr != null) {
                        String dateoper = new DateTime().toString(patternsqldate);
                        String filename = new DateTime().toString("yyMMddHHmmssSSS") + codtr + typesign + ".pdf";
                        Ch_transaction_doc chd = new Ch_transaction_doc(Token, codtr, typesign, base64,
                                filename, dateoper, codcl, "Y");
                        db.insert_transaction_doc(chd);
                    }
                }
                db.closeDB();
            }

        }
    }

    private boolean getMarketingINFO(JsonArray Response) {
        try {
            return Response.get(2).getAsString().equalsIgnoreCase("OK");
        } catch (Exception e) {
            return false;
        }
    }

    private String getSignatureINFO(JsonObject root) {
        try {
            String Signature = root.get("Signature").getAsString();
            if (Signature != null) {
                return Signature.trim();
            }
        } catch (Exception e) {
        }
        return "";
    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void returnsign(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String json = new BufferedReader(new InputStreamReader(request.getInputStream())).readLine();

        if (json != null) {
            JsonElement je = parseString(json);
            JsonObject root = je.getAsJsonObject();
            JsonArray Response = root.getAsJsonArray("Response");
            int countsign = 0;
            for (int i = 0; i < Response.size(); i++) {
                if (Response.get(i).getAsString().equalsIgnoreCase("OK")) {
                    countsign++;
                }
            }
            String codtr = root.get("codtr").getAsString().trim();
            String codcl = root.get("codcl").getAsString().trim();
            String typesign = root.get("typesign").getAsString().trim();
            String Token = root.get("Token").getAsString().trim();
            String base64 = root.get("Payload").getAsString().trim();

            if (!base64.equals("")) {
                Db_Master db = new Db_Master();
                int firmeattese = 1;
                String fa1 = db.getConf(typesign);
                if (parseIntR(fa1) > 0) {
                    firmeattese = parseIntR(fa1);
                }

                if (countsign >= firmeattese) {

                    boolean marketingok = getMarketingINFO(Response);
                    if (marketingok) {
//                        db.insert_marketing_OK(codtr, codcl, "service");
                    } else {
                        if (newpad) {
                            //  Viene annullata la transazione no change legata allo scarico automatico magazzino per le WK/TIC.
                            Ch_transaction ch = query_transaction_ch_temp(codtr);
                            if (ch != null) {
                                List<String> li_tr = db.get_WK_TI(ch);
                                li_tr.forEach(cod1 -> {
                                    remove_nochange_transaction(cod1,
                                            "The customer refused to give consent for data processing.",
                                            ch.getUser(), ch.getFiliale());
                                });

                                //  Viene dissociato l’abbinamento cliente / codice loyalty e il codice viene reso nuovamente 
                                //  disponibile per essere associato ad un nuovo cliente.
                                String loy = query_LOY_transaction(ch.getCod(), null, ch.getFiliale());
                                remove_loy_assign_first_NEW(Utility.sanitize(loy), Utility.sanitize(ch.getCl_cod()));//  22/01
                            }
                        }
                    }

                    String Signature = getSignatureINFO(root);
                    if (!Signature.trim().equals("")) {
                        String ts = "_signobb";
                        String dateoper = new DateTime().toString(patternsqldate);
                        String filename = new DateTime().toString("yyMMddHHmmssSSS") + codtr + ts + ".png";
                        Ch_transaction_doc chd = new Ch_transaction_doc(generaId(50), codtr, ts, Signature,
                                filename, dateoper, codcl, "Y");
                        db.insert_transaction_doc_NOCENTRAL(chd);

                    }

                    if (typesign.equals("_macnctaxf")) {
                        String dateoper = new DateTime().toString(patternsqldate);
                        String filename = new DateTime().toString("yyMMddHHmmssSSS") + codtr + typesign + ".pdf";
                        Ch_transaction_doc chd = new Ch_transaction_doc(Token, codtr, typesign, base64,
                                filename, dateoper, codcl, "Y");
                        db.insert_transaction_doc(chd);
                    } else {
                        Ch_transaction tr = db.query_transaction_ch(codtr);
                        if (tr == null) {
                            tr = db.query_transaction_ch_temp(codtr);
                        }
                        if (tr != null) {
                            String dateoper = new DateTime().toString(patternsqldate);
                            String filename = new DateTime().toString("yyMMddHHmmssSSS") + codtr + typesign + ".pdf";
                            Ch_transaction_doc chd = new Ch_transaction_doc(Token, codtr, typesign, base64,
                                    filename, dateoper, codcl, "Y");
                            db.insert_transaction_doc(chd);
                            //FIRMA MARKETING ESCLUDENDO NOTA CREDITO
                            if (!typesign.toLowerCase().contains("credinot") && marketingok) {
                                db.insert_marketing_OK(codtr, codcl, "service");
                            }
                        }
                    }
                }
                db.closeDB();
            }
        }
    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void _macnctaxf(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String cod = request.getParameter("cod");
        Db_Master db = new Db_Master();
        NC_transaction nc = db.get_NC_transaction(cod);
        db.closeDB();
        String base64 = new rc.so.pdf.Pdf().receipt_nochange_anag(nc);
        if (base64 != null) {
            String cod1 = generaId(50);
            String payload = base64;
            HttpSession se = request.getSession();
            se.setAttribute("payload", payload);
            se.setAttribute("cod", cod1);
            se.setAttribute("typesi", request.getParameter("type"));
            se.setAttribute("codtr", cod);
            se.setAttribute("codcl", "NC");
            String oldloy = request.getParameter("oldloy");
            if (oldloy == null) {
                oldloy = "0";
            }
            redirect(request, response, "signature.jsp?oldloy=" + oldloy);
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
    protected void _macautoce(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String solofirma = request.getParameter("solofirma");
        if (solofirma == null) {
            solofirma = "NO";
        }
        Db_Master db = new Db_Master();
        String pathtemp = db.getPath("temp");
        String codtr = request.getParameter("codtr");
        String codcl = request.getParameter("codcl");
        String dt = request.getParameter("dt");
        Client cl = db.query_Client_transaction(codtr, codcl);
        Ch_transaction ct = db.query_transaction_ch(codtr);
        if (ct == null) {
            ct = db.query_transaction_ch_temp(codtr);
        }
        CustomerKind ck = db.get_customerKind(ct.getTipocliente());
        String dt_tr = formatStringtoStringDate(dt, patternsqldate, patternnormdate_filter);
        String base64 = new rc.so.pdf.Pdf().print_autocert(pathtemp, cl, dt_tr, ct, ck);
        db.closeDB();

        if (base64 != null) {
            String cod1 = generaId(50);
            String payload = base64;
            HttpSession se = request.getSession();
            se.setAttribute("payload", payload);
            se.setAttribute("cod", cod1);
            se.setAttribute("typesi", request.getParameter("type"));
            se.setAttribute("codtr", codtr);
            se.setAttribute("codcl", codcl);
            redirect(request, response, "signature.jsp?solofirma=" + solofirma);
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
    protected void _macprofcl(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String solofirma = request.getParameter("solofirma");
        if (solofirma == null) {
            solofirma = "NO";
        }

        Db_Master db = new Db_Master();
        String pathtemp = db.getPath("temp");
        String cod = request.getParameter("cod");
        Ch_transaction tra = db.query_transaction_ch(cod);
        if (tra == null) {
            tra = db.query_transaction_ch_temp(cod);
        }
        ArrayList<Ch_transaction_value> li = db.query_transaction_value(cod);
        Client cl = db.query_Client_transaction(cod, tra.getCl_cod());
        Office ma = db.get_national_office();
        Branch br = db.get_branch(tra.getFiliale());
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

        String base64 = modulo_profilatura(pathtemp, ma, tra, cl, li, br, iss, cust, cust_det, ann, main1, main1_val, main2, main2_val, main3, main3_val);

        db.closeDB();

        if (base64 != null) {
            String cod1 = generaId(50);
            String payload = base64;
            HttpSession se = request.getSession();
            se.setAttribute("payload", payload);
            se.setAttribute("cod", cod1);
            se.setAttribute("typesi", request.getParameter("type"));
            se.setAttribute("codtr", cod);
            se.setAttribute("codcl", tra.getCl_cod());

            redirect(request, response, "signature.jsp?solofirma=" + solofirma);
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
    protected void _macrecsee(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
        Branch br = db.get_branch(tra.getFiliale());
        ArrayList<Figures> fig = db.list_all_figures();
        ArrayList<Currency> cur = db.list_figures_query_edit(null);
        String base64_1 = new Receipt().print_receipt_see(pathtemp, ma, tra, cl, li, br, fig, cur, true);
        String base64_2 = new Receipt().print_receipt_see(pathtemp, ma, tra, cl, li, br, fig, cur, false);
        db.closeDB();

        if (base64_1 != null && base64_2 != null) {
            String cod = generaId(50);
            String payload = base64_1;
            String payload2 = base64_2;

            HttpSession se = request.getSession();
            se.setAttribute("payload", payload);
            se.setAttribute("payload2", payload2);
            se.setAttribute("cod", cod);
            se.setAttribute("typesi", request.getParameter("type"));
            se.setAttribute("codtr", codtr);
            se.setAttribute("codcl", codcl);
            String oldloy = "0";
            if (is_marketing_OK(cl.getCode())) {
                oldloy = "1";
            }
            redirect(request, response, "signature.jsp?oldloy=" + oldloy);
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
    protected void _maccredinot(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
        Branch br = db.get_branch(tra.getFiliale());
        ArrayList<Figures> fig = db.list_all_figures();
        ArrayList<Currency> cur = db.list_figures_query_edit(null);
        CustomerKind ck = db.get_customerKind(tra.getTipocliente());
        VATcode va  = db.get_vat_cod(ck.getVatcode());
        db.closeDB();

        String base64_1 = new Receipt().print_credit_note(pathtemp, ma, tra, cl, li, br,
                fig, cur, va, ck, tra.getFiliale() + tra.getId(), tra.getPay(),
                true, tra.getPay(), tra.getPay(), true, true);
        String base64_2 = new Receipt().print_credit_note(pathtemp, ma, tra, cl, li, br,
                fig, cur, va, ck, tra.getFiliale() + tra.getId(), tra.getPay(),
                true, tra.getPay(), tra.getPay(), true, false);

        if (base64_1 != null && base64_2 != null) {
            String cod = generaId(50);
            String payload = base64_1;
            String payload2 = base64_2;

            HttpSession se = request.getSession();
            se.setAttribute("payload", payload);
            se.setAttribute("payload2", payload2);
            se.setAttribute("cod", cod);
            se.setAttribute("typesi", request.getParameter("type"));
            se.setAttribute("codtr", codtr);
            se.setAttribute("codcl", codcl);
            String oldloy = "0";
            if (is_marketing_OK(codcl)) {
                oldloy = "1";
            }
            redirect(request, response, "signature.jsp?oldloy=" + oldloy);
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
    protected void _macrefund(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String cod_tr = Utility.sanitize(request.getParameter("codtr"));
        String datetime = new DateTime().toString(patternnormdate_filter);
        Db_Master db = new Db_Master();
        String pathtemp = Utility.sanitize(db.getPath("temp"));
        String localfig = db.get_local_currency()[0];
        Ch_transaction tr = db.query_transaction_ch(cod_tr);
        if (tr == null) {
            tr = db.query_transaction_ch_temp(cod_tr);
        }
        Client cl = db.query_Client_transaction(cod_tr, tr.getCl_cod());
        ArrayList<Branch> brl = db.list_branch();
        ArrayList<Ch_transaction_value> tr_value = db.query_transaction_value(cod_tr);
        Ch_transaction_refund ref = db.get_refund_trans(cod_tr);

        String base64_1;
        String base64_2 = null;
        Branch br = db.get_branch(tr.getFiliale());
        if (!tr.getCn_number().equals("") && !tr.getCn_number().equals("-")) {

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

            base64_1 = new Receipt().print_credit_note(pathtemp, ma, tr, cl, tr_value, br,
                    fig, cur, va, ck, tr.getFiliale() + tr.getId(),
                    net, complete, im1, im2, complete, true);

            base64_2 = new Receipt().print_credit_note(pathtemp, ma, tr, cl, tr_value, br,
                    fig, cur, va, ck, tr.getFiliale() + tr.getId(),
                    net, complete, im1, im2, complete, false);

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

            base64_1 = refund(pathtemp,
                    decodeBase64(model),
                    cl.getCognome() + " " + cl.getNome(),
                    importo,
                    tr.getFiliale() + tr.getId(),
                    formatStringtoStringDate(tr.getData(), patternsqldate, patternnormdate),
                    formatBankBranchReport(tr.getFiliale(), "BR", null, brl),
                    datetime);

        }

        if (base64_1 != null) {
            String cod = generaId(50);
            String payload = base64_1;
            String payload2 = base64_2;

            HttpSession se = request.getSession();
            se.setAttribute("payload", payload);
            se.setAttribute("payload2", payload2);
            se.setAttribute("cod", cod);
            se.setAttribute("typesi", "_macrefund");

            se.setAttribute("codtr", cod_tr);
            se.setAttribute("codcl", tr.getCl_cod());
            String oldloy = "0";
            if (is_marketing_OK(cl.getCode())) {
                oldloy = "1";
            }
            redirect(request, response, "signature.jsp?oldloy=" + oldloy);
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
    protected void _macfaexseebo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String codtr = Utility.sanitize(request.getParameter("codtr"));
        String codcl = Utility.sanitize(request.getParameter("codcl"));
        Db_Master db = new Db_Master();
        String pathtemp = Utility.sanitize(db.getPath("temp"));
        Ch_transaction tra = db.query_transaction_ch(codtr);
        if (tra == null) {
            tra = db.query_transaction_ch_temp(codtr);
        }
        ArrayList<Ch_transaction_value> li = db.query_transaction_value(codtr);
        Client cl = db.query_Client_transaction(codtr, codcl);
        Office ma = db.get_national_office();
        Branch br = db.get_branch(tra.getFiliale());
        ArrayList<Figures> fig = db.list_all_figures();
        ArrayList<Currency> cur = db.list_figures_query_edit(null);
        CustomerKind ck = db.get_customerKind(tra.getTipocliente());
        VATcode va  = db.get_vat_cod(ck.getVatcode());
        String base64_1 = new Receipt().print_bill_extrasee_bollo(pathtemp, ma, tra, cl, li, br, fig, cur, va, ck, true);
        String base64_2 = new Receipt().print_bill_extrasee_bollo(pathtemp, ma, tra, cl, li, br, fig, cur, va, ck, false);

        db.closeDB();

        if (base64_1 != null && base64_2 != null) {
            String cod = generaId(50);
            String payload = base64_1;
            String payload2 = base64_2;

            HttpSession se = request.getSession();
            se.setAttribute("payload", payload);
            se.setAttribute("payload2", payload2);
            se.setAttribute("cod", cod);
            se.setAttribute("typesi", request.getParameter("type"));
            se.setAttribute("codtr", codtr);
            se.setAttribute("codcl", codcl);
            String oldloy = "0";
            if (is_marketing_OK(cl.getCode())) {
                oldloy = "1";
            }
            redirect(request, response, "signature.jsp?oldloy=" + oldloy);
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
    protected void _macriseco(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
        Branch br = db.get_branch(tra.getFiliale());

        ArrayList<Figures> fig = db.list_all_figures();
        ArrayList<Currency> cur = db.list_figures_query_edit(null);
        String base64_1 = Utility.sanitize(new Receipt().print_receipt_comm_not_zero(pathtemp, ma, tra, cl, li, br, fig, cur, true));
        String base64_2 = Utility.sanitize(new Receipt().print_receipt_comm_not_zero(pathtemp, ma, tra, cl, li, br, fig, cur, false));
        db.closeDB();

        if (base64_1 != null && base64_2 != null) {
            String cod = generaId(50);
            String payload = base64_1;
            String payload2 = base64_2;

            HttpSession se = request.getSession();
            se.setAttribute("payload", payload);
            se.setAttribute("payload2", payload2);
            se.setAttribute("cod", cod);
            se.setAttribute("typesi", request.getParameter("type"));
            se.setAttribute("codtr", codtr);
            se.setAttribute("codcl", codcl);
            String oldloy = "0";
            if (is_marketing_OK(cl.getCode())) {
                oldloy = "1";
            }
            redirect(request, response, "signature.jsp?oldloy=" + oldloy);
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
    protected void _macreceita(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        redirect(request, response, "page_fnf.html");
    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void _macfaexsee(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

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
        Branch br = db.get_branch(tra.getFiliale());
        ArrayList<Figures> fig = db.list_all_figures();
        ArrayList<Currency> cur = db.list_figures_query_edit(null);
        CustomerKind ck = db.get_customerKind(tra.getTipocliente());
        VATcode va  = db.get_vat_cod(ck.getVatcode());
        String base64_1 = new Receipt().print_bill_extrasee_bollo(pathtemp, ma, tra, cl, li, br, fig, cur, va, ck, true);
        String base64_2 = new Receipt().print_bill_extrasee_bollo(pathtemp, ma, tra, cl, li, br, fig, cur, va, ck, false);
        db.closeDB();
        if (base64_1 != null && base64_2 != null) {
            String cod = generaId(50);
            String payload = base64_1;
            String payload2 = base64_2;

            HttpSession se = request.getSession();
            se.setAttribute("payload", payload);
            se.setAttribute("payload2", payload2);
            se.setAttribute("cod", cod);
            se.setAttribute("typesi", request.getParameter("type"));
            se.setAttribute("codtr", codtr);
            se.setAttribute("codcl", codcl);
            String oldloy = "0";
            if (is_marketing_OK(cl.getCode())) {
                oldloy = "1";
            }
            redirect(request, response, "signature.jsp?oldloy=" + oldloy);
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
            response.setContentType("text/html;charset=UTF-8");
//            request.setCharacterEncoding("UTF-8");
            String type = request.getParameter("type");
            switch (type) {
                case "returnsign":
                    returnsign(request, response);
                    break;
                case "_macfaexsee":
                    _macfaexsee(request, response);
                    break;
                case "_macprofcl":
                    _macprofcl(request, response);
                    break;
                case "_macautoce":
                    _macautoce(request, response);
                    break;
                case "_macnctaxf":
                    _macnctaxf(request, response);
                    break;
                case "_maccredinot":
                    _maccredinot(request, response);
                    break;
                case "_macrefund":
                    _macrefund(request, response);
                    break;
                case "_macfaexseebo":
                    _macfaexseebo(request, response);
                    break;
                case "_macreceita":
                    _macreceita(request, response);
                    break;
                case "_macriseco":
                    _macriseco(request, response);
                    break;
                case "addsign":
                    addsign(request, response);
                    break;
                case "versign":
                    versign(request, response);
                    break;
                case "_macrecsee":
                    _macrecsee(request, response);
                    break;
                default:
                    break;
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
