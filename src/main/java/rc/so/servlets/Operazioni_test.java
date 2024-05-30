/*
 * To change this license header, choose License Headers in Project Properties.
 * To change get_spread template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rc.so.servlets;

import static com.google.common.base.Splitter.on;
import com.google.gson.Gson;
import rc.so.db.Db_Loy;
import rc.so.db.Db_Master;
import rc.so.entity.BlackListsAT;
import rc.so.entity.BlacklistM;
import rc.so.entity.Booking;
import rc.so.entity.Ch_transaction;
import rc.so.entity.Ch_transaction_value;
import rc.so.entity.Client;
import rc.so.entity.Client_CZ;
import rc.so.entity.CustomerKind;
import rc.so.entity.ET_change;
import rc.so.entity.NC_category;
import rc.so.entity.NC_causal;
import rc.so.entity.NC_transaction;
import rc.so.entity.Real_oc_change;
import rc.so.entity.Real_oc_pos;
import rc.so.entity.Stock;
import rc.so.entity.Stock_report;
import rc.so.entity.Till;
import static rc.so.rest.Sito.sendRequest_EDIT;
import static rc.so.util.Constant.codnaz;
import static rc.so.util.Constant.is_CZ;
import static rc.so.util.Constant.is_IT;
import static rc.so.util.Constant.is_UK;
import static rc.so.util.Constant.patternnormdate_filter;
import static rc.so.util.Constant.signoffline;
import rc.so.util.Engine;
import static rc.so.util.Engine.agevolazioni_varie;
import static rc.so.util.Engine.checkDeleteTR;
import static rc.so.util.Engine.deleteLinktransaction;
import static rc.so.util.Engine.getBookingJSON;
import static rc.so.util.Engine.getConf;
import static rc.so.util.Engine.getFil;
import static rc.so.util.Engine.getLastLink;
import static rc.so.util.Engine.getLink_last;
import static rc.so.util.Engine.getLocalFigures;
import static rc.so.util.Engine.getNC_category;
import static rc.so.util.Engine.getNC_causal;
import static rc.so.util.Engine.get_ET_change;
import static rc.so.util.Engine.get_soglia_CZ;
import static rc.so.util.Engine.insertLink;
import static rc.so.util.Engine.insertNC_transaction_WK;
import static rc.so.util.Engine.insertTR;
import static rc.so.util.Engine.insertTR_R;
import static rc.so.util.Engine.isBlockedOperation;
import static rc.so.util.Engine.is_marketing_OK;
import static rc.so.util.Engine.list_oc_nochange_real;
import static rc.so.util.Engine.list_stock;
import static rc.so.util.Engine.query_LOY_transaction;
import static rc.so.util.Engine.query_transaction_ch;
import static rc.so.util.Engine.query_transaction_ch_temp;
import static rc.so.util.Engine.rate_history_mod;
import static rc.so.util.Engine.remove_loy_assign_first_NEW;
import static rc.so.util.Engine.servizi_agg;
import static rc.so.util.Engine.stock_quantity;
import static rc.so.util.Engine.verificaBlackList;
import static rc.so.util.Engine.verifica_fa_nc;
import static rc.so.util.Engine.verifyBlacklistMac;
import static rc.so.util.Engine.verifySession;
import static rc.so.util.Utility.calcolaspread2021;
import static rc.so.util.Utility.fd;
import static rc.so.util.Utility.formatDoubleforMysql;
import static rc.so.util.Utility.formatMysqltoDisplay;
import static rc.so.util.Utility.generaId;
import static rc.so.util.Utility.getControvalore;
import static rc.so.util.Utility.getRequestValue;
import static rc.so.util.Utility.getStringUTF8;
import static rc.so.util.Utility.getValue_request;
import static rc.so.util.Utility.parseDoubleR;
import static rc.so.util.Utility.parseIntR;
import static rc.so.util.Utility.redirect;
import static rc.so.util.Utility.removeLast;
import static rc.so.util.Utility.roundDoubleandFormat;
import static rc.so.util.Utility.send_Mail_CONFERMA_TRX;
import static rc.so.util.Utility.sleeping;
import java.io.IOException;
import java.io.PrintWriter;
import static java.lang.Integer.parseInt;
import static java.lang.Runtime.getRuntime;
import static java.lang.String.valueOf;
import static java.lang.Thread.currentThread;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import static org.apache.commons.lang3.StringUtils.substring;
import org.joda.time.DateTime;
import static org.joda.time.format.DateTimeFormat.forPattern;
import org.joda.time.format.DateTimeFormatter;
import static rc.so.rest.Sito.sendRequest_EDIT_CB;
import static rc.so.rest.Sito.sendRequest_EDIT_WT;
import rc.so.util.Constant;

/**
 *
 * @author rcosco
 */
public class Operazioni_test extends HttpServlet {

    private static final String filiale = getFil()[0];

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void updatesito(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String user = (String) request.getSession().getAttribute("us_cod");
        if (user == null) {
            user = "9999";
        }

        String bookingvalue = request.getParameter("bookingvalue");
        String operation = request.getParameter("opr");
        String modified = request.getParameter("modified");
        String pay = request.getParameter("pay");
        String cod = request.getParameter("cod");
        Db_Master db0 = new Db_Master();
        Booking bo = db0.get_prenot(bookingvalue);

        db0.closeDB();

        insertTR("W", user, "RICHIESTA BOOKING " + operation + " - " + bookingvalue);

        String ok;
        if (operation.equals("DELETE")) {
            ok = checkDeleteTR(cod);
        } else {
            if (bo == null) {
                ok = "ERROR: BOOKING NOT FOUND.";
            } else {
                if (bo.getCanale().contains("1")) {
                    if (modified.equals("NO")) {
                        ok = sendRequest_EDIT(getBookingJSON(bo, "7", pay), bookingvalue); //CONFIRM
                    } else {
                        ok = sendRequest_EDIT(getBookingJSON(bo, "9", pay), bookingvalue); //CONFIRM MODIFIED
                    }
                } else if (bo.getCanale().contains("4")) {
                    ok = sendRequest_EDIT_CB(getBookingJSON(bo, "9", pay), bookingvalue); //CHE BANCA
                } else if (bo.getCanale().contains("6")) {
                    ok = sendRequest_EDIT(getBookingJSON(bo, "9", pay), bookingvalue); //MODIFIED
                } else if (bo.getCanale().contains("7")) {
                    ok = sendRequest_EDIT_WT(getBookingJSON(bo, "9", pay), bookingvalue); //MODIFIED
                } else {
                    try {
                        send_Mail_CONFERMA_TRX(bo);
                    } catch (Exception e1) {

                    }
                    ok = "OK";
                }
            }
        }

        if (ok.equals("")) {
            ok = "OK";  // NESSUNA AZIONE
        }

        insertTR("W", user, "RICHIESTA BOOKING " + bookingvalue + " -- " + ok);

        try (PrintWriter out = response.getWriter()) {
            out.print(valueOf(ok));
        }

    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void vercfanno(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String anno = request.getParameter("anno");
        String d1 = "01/01/19" + anno;
        DateTimeFormatter formatter = forPattern(patternnormdate_filter);
        DateTime nascita = formatter.parseDateTime(d1).plusYears(100);
        try (PrintWriter out = response.getWriter()) {
            if (nascita.isBeforeNow()) {
                out.print("20");
            } else {
                out.print("19");
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
    protected void controllaoccupato(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String usr = isBlockedOperation();
        try (PrintWriter out = response.getWriter()) {
            if (usr == null) {
                out.print("true");
            } else {
                out.print(usr);
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
    protected void verificasoglianagraficaUK(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        boolean exituk = false;
        if (is_UK) {
            String nuovasogliaUK_daverificare = getConf("thr.mini");
            if (nuovasogliaUK_daverificare.equals("-")) {
                nuovasogliaUK_daverificare = "1000.00";
            }
            double output = fd(formatDoubleforMysql(request.getParameter("total")));
            if (output > 0) {
                exituk = output >= fd(nuovasogliaUK_daverificare);
            }
        }
        try (PrintWriter out = response.getWriter()) {
            out.print(valueOf(exituk));
        }
    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void verificasoglianagrafica(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        double soglia = fd(request.getParameter("soglia"));
        double output = fd(formatDoubleforMysql(request.getParameter("total")));

        boolean exit = false;

        if (is_CZ) {
            String currency = request.getParameter("currency");
            if (currency != null) {
                double quantity = fd(formatDoubleforMysql(request.getParameter("quantity")));
                if (currency.equalsIgnoreCase("EUR")) {
                    if (quantity > 0) {
                        exit = quantity >= soglia;
                    }
                } else {
                    soglia = get_soglia_CZ(soglia);
                    if (output > 0) {
                        exit = output >= soglia;
                    }
                }
            }
        } else {
            soglia = get_soglia_CZ(soglia);
            if (output > 0) {
                exit = output >= soglia;
            }
        }

        try (PrintWriter out = response.getWriter()) {
            out.print(valueOf(exit));
        }
    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void controllaoccupato_till(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try (PrintWriter out = response.getWriter()) {
            boolean activefr = false;
            Db_Master db0 = new Db_Master();
            ArrayList<Till> array_till = db0.list_till_status("O", null, filiale);
            db0.closeDB();
            for (int j = 0; j < array_till.size(); j++) {
                if (array_till.get(j).getId_opcl().equals(request.getParameter("q"))) {
                    activefr = true;
                }
            }
            if (!activefr) {
                out.print("true1");
                out.close();
                return;
            }
            String user = (String) request.getSession().getAttribute("us_cod");
            if (user == null) {
                user = "9999";
            }
            String str = getLastLink(user);
            if (str != null) {
                out.print("true");
            } else {
                String usr = isBlockedOperation();
                if (usr != null) {
                    String codu = usr.split(" ")[0].trim();
                    if (codu.equals(user)) {
                        out.print("true");
                    } else {
                        out.print("false");
                    }
                } else {
                    out.print("false");
                }
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
    protected void verifyquantnoch(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String nc_cat1 = request.getParameter("nc_cat1");
        String id_open_till_v = request.getParameter("id_open_till_v");
        String quantity = formatDoubleforMysql(request.getParameter("quantity"));

        ArrayList<String[]> list_oc_nochange_open = list_oc_nochange_real(id_open_till_v);
        boolean ok = false;
        for (int i = 0; i < list_oc_nochange_open.size(); i++) {
            String nccat = list_oc_nochange_open.get(i)[1];
            String qua = list_oc_nochange_open.get(i)[2];
            if (nccat.equals(nc_cat1)) {
                ok = fd(quantity) <= fd(qua);
                break;
            }
        }

        try (PrintWriter out = response.getWriter()) {
            out.print(valueOf(ok));
        }

    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void check_changerate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String idval = request.getParameter("idval");

        Iterable<String> parameters = on(";").split(idval);
        Iterator<String> it = parameters.iterator();

        String code = it.next().trim();
        String kind = it.next().trim();
        String val = it.next().trim();
        Db_Master db = new Db_Master();
        boolean dividi = db.get_national_office().getChangetype().equals("/");
        db.closeDB();

        try (PrintWriter out = response.getWriter()) {
            String[] locfig = getLocalFigures();
            //
//
//        String ra = request.getParameter("ra");
//        String valuta = request.getParameter("va");
//
            Gson gson = new Gson();
            ArrayList<String> JSONRequest = new ArrayList<>();
            if (val.equals(locfig[0])) {
                JSONRequest.add(gson.toJson("falseV"));
                JSONRequest.add(gson.toJson("Local currency '" + val + "' are unable to change rate."));
            } else {
                ArrayList<String[]> history = rate_history_mod(code, kind, val);
                ArrayList<Stock> list_stock = list_stock(code, kind, val, "stock_story");
                ArrayList<String[]> list_et = stock_quantity(code, "ET", kind, val, "stock_story");
                ArrayList<String[]> list_se = stock_quantity(code, "SE", kind, val, "stock_story");
                double unloaded = 0.00;
                JSONRequest.add(gson.toJson("true"));
                JSONRequest.add(gson.toJson("ET2 - " + list_et.size()));
                JSONRequest.add(gson.toJson("SE2 - " + list_se.size()));
                JSONRequest.add(gson.toJson("ST1 - " + list_stock.size()));
                JSONRequest.add(gson.toJson("HI1 - " + history.size()));
                double e1 = 0.00;
                double e2 = 0.00;
                for (int i = 0; i < list_et.size(); i++) {
                    ET_change et11 = get_ET_change(list_et.get(i)[1]);
                    if (et11 != null) {
                        JSONRequest.add(gson.toJson(et11.getFiliale() + et11.getId()));
                    } else {
                        JSONRequest.add(gson.toJson(list_et.get(i)[1]));
                    }
                    JSONRequest.add(gson.toJson(list_et.get(i)[6]));
                    JSONRequest.add(gson.toJson(formatMysqltoDisplay(list_et.get(i)[5])));
                    JSONRequest.add(gson.toJson(formatMysqltoDisplay(list_et.get(i)[4])));
                    e1 = e1 + fd(list_et.get(i)[4]);
                    //new
                    double cv = getControvalore(fd(list_et.get(i)[4]), fd(list_et.get(i)[5]), dividi);
                    JSONRequest.add(gson.toJson(formatMysqltoDisplay(roundDoubleandFormat(cv, 2))));
                    e2 = e2 + cv;
                    //                JSONRequest.add(gson.toJson(formatMysqltoDisplay(roundDoubleandFormat(fd(list_et.get(i)[4]) / fd(list_et.get(i)[5]), 2))));
//                e2 = e2 + (fd(list_et.get(i)[4]) / fd(list_et.get(i)[5]));
                    unloaded = unloaded + fd(list_et.get(i)[4]);
                }
                JSONRequest.add(gson.toJson(""));
                JSONRequest.add(gson.toJson(""));
                JSONRequest.add(gson.toJson("Total"));
                JSONRequest.add(gson.toJson(formatMysqltoDisplay(roundDoubleandFormat(e1, 2))));
                JSONRequest.add(gson.toJson(formatMysqltoDisplay(roundDoubleandFormat(e2, 2))));
                JSONRequest.add(gson.toJson("---"));
                JSONRequest.add(gson.toJson("---"));
                double se1 = 0.00;
                double se2 = 0.00;
                for (int i = 0; i < list_se.size(); i++) {
                    Ch_transaction ch = query_transaction_ch(list_se.get(i)[1]);
                    if (ch != null) {
                        JSONRequest.add(gson.toJson(ch.getId()));
                    } else {
                        JSONRequest.add(gson.toJson(list_se.get(i)[1]));
                    }
                    JSONRequest.add(gson.toJson(list_se.get(i)[6]));
                    JSONRequest.add(gson.toJson(formatMysqltoDisplay(list_se.get(i)[5])));
                    JSONRequest.add(gson.toJson(formatMysqltoDisplay(list_se.get(i)[4])));
                    se1 = se1 + fd(list_se.get(i)[4]);
                    double cv = getControvalore(fd(list_se.get(i)[4]), fd(list_se.get(i)[5]), dividi);
                    JSONRequest.add(gson.toJson(formatMysqltoDisplay(roundDoubleandFormat(cv, 2))));
                    se2 = se2 + cv;
                    //                JSONRequest.add(gson.toJson(formatMysqltoDisplay(roundDoubleandFormat(fd(list_se.get(i)[4]) / fd(list_se.get(i)[5]), 2))));
//                se2 = se2 + (fd(list_se.get(i)[4]) / fd(list_se.get(i)[5]));
                    unloaded = unloaded + fd(list_se.get(i)[4]);
                }
                JSONRequest.add(gson.toJson(""));
                JSONRequest.add(gson.toJson(""));
                JSONRequest.add(gson.toJson("Total"));
                JSONRequest.add(gson.toJson(formatMysqltoDisplay(roundDoubleandFormat(se1, 2))));
                JSONRequest.add(gson.toJson(formatMysqltoDisplay(roundDoubleandFormat(se2, 2))));
                JSONRequest.add(gson.toJson("---"));
                double s1 = 0.00;
                double s2 = 0.00;
                double s3 = 0.00;
                for (int i = 0; i < list_stock.size(); i++) {
                    JSONRequest.add(gson.toJson(list_stock.get(i).getDate()));
                    JSONRequest.add(gson.toJson(formatMysqltoDisplay(list_stock.get(i).getRate())));
                    JSONRequest.add(gson.toJson(formatMysqltoDisplay(list_stock.get(i).getTotal())));
                    s1 = s1 + fd(list_stock.get(i).getTotal());

                    JSONRequest.add(gson.toJson(formatMysqltoDisplay(roundDoubleandFormat(unloaded, 2))));
                    s2 = s2 + fd(roundDoubleandFormat(unloaded, 2));

                    JSONRequest.add(gson.toJson(formatMysqltoDisplay(roundDoubleandFormat(fd(list_stock.get(i).getTotal()) - unloaded, 2))));
                    s3 = s3 + (fd(list_stock.get(i).getTotal()) - unloaded);
                }
                JSONRequest.add(gson.toJson(""));
                JSONRequest.add(gson.toJson("Total"));
                JSONRequest.add(gson.toJson(formatMysqltoDisplay(roundDoubleandFormat(s1, 2))));
                JSONRequest.add(gson.toJson(formatMysqltoDisplay(roundDoubleandFormat(s2, 2))));
                JSONRequest.add(gson.toJson(formatMysqltoDisplay(roundDoubleandFormat(s3, 2))));
                for (int i = 0; i < history.size(); i++) {
                    JSONRequest.add(gson.toJson(history.get(i)[3]));
                    JSONRequest.add(gson.toJson(formatMysqltoDisplay(history.get(i)[0])));
                    JSONRequest.add(gson.toJson(formatMysqltoDisplay(history.get(i)[1])));
                    JSONRequest.add(gson.toJson(history.get(i)[2]));
                }
            }
            out.print(JSONRequest);
        }
    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void get_spread(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String typeop = getRequestValue(request, "ettype");
        String tofrom = getRequestValue(request, "ettof");

        String t = request.getParameter("t");
        if (t == null) {
            t = "S";
        }

        String f1 = request.getParameter("f1");
        if (f1.contains(" ")) {
            f1 = f1.split(" ")[0].trim();
        }

        String ki = request.getParameter("ki");
        if (ki.contains(" ")) {
            ki = ki.split(" ")[0].trim();
        }

        boolean ok = true;
        String q1 = request.getParameter("q1");
        String r1 = request.getParameter("r1");
        String q2 = formatDoubleforMysql(q1);
        if (r1.equals("")) {
            r1 = "0";
            ok = false;
        }
        if (f1.equals("---")) {
            ok = false;
        }

        try {
            double dq2 = fd(q2);
            double dr1 = fd(r1);
            if (dq2 <= 0) {
                ok = false;
            }
            if (dr1 <= 0) {
                ok = false;
            }
        } catch (Exception e) {
            ok = false;
        }

//        Db_Master db01 = new Db_Master();
//        String valutalocale = db01.get_local_currency()[0];
//        boolean dividi = db01.get_national_office().getChangetype().equals("/");
//        Currency valutastraniera1 = db01.getCurrency(f1);
//        db01.closeDB();
//        boolean isvalutalocale = valutalocale.equals(f1);
//        double nuovorate = 0.00;
        Gson gson = new Gson();
        ArrayList<String> JSONRequest = new ArrayList<>();
//        DateTimeFormatter formatter = DateTimeFormat.forPattern(patternsqldate);
//        if (ok && !isvalutalocale) {
//
//            double contr = getControvalore(fd(q2), fd(r1), dividi);
//            if (t.equals("B")) {
//                double contr_BCE = getControvalore(fd(q2), fd(valutastraniera1.getCambio_bce()), dividi);
//                double spreadtotal = contr_BCE - contr;
//                JSONRequest.add(gson.toJson("OK"));
//                JSONRequest.add(gson.toJson(formatMysqltoDisplay(roundDoubleandFormat(spreadtotal, 2))));
//                JSONRequest.add(gson.toJson(formatMysqltoDisplay(roundDoubleandFormat(contr_BCE, 2))));
//                JSONRequest.add(gson.toJson(formatMysqltoDisplay(r1)));
//            } else {
//
//                Db_Master db1 = new Db_Master();
//                ArrayList<Stock> al = db1.list_stock(filiale, ki, f1);
//                db1.closeDB();
//
//                double quant_now = fd(q2);
//                double quant_check = quant_now;
//
//                DateTime inizioanno = new DateTime().withDayOfYear(1).withMillisOfDay(0);
//                for (int i = 0; i < al.size() && quant_check > 0; i++) {
//                    double vq1 = fd(al.get(i).getTotal());
//                    double vr1 = fd(al.get(i).getRate());
//
//                    if (vq1 > 0) {
//
//                        double oldrate;
//
//                        DateTime data_lotto = formatter.parseDateTime(StringUtils.substring(al.get(i).getDate(), 0, 19));
//
//                        boolean annoprecedente = data_lotto.isBefore(inizioanno);
//                        if (annoprecedente) {
//                            DateTime data_riferimento = inizioanno.minusDays(1);
//                            oldrate = fd(Engine.get_BCE(data_riferimento, valutastraniera1.getCode()));
//                        } else {
//
//                            boolean checkET = al.get(i).getTipostock().equals("ET");
//                            if (checkET) {
//                                Db_Master db2 = new Db_Master();
//                                checkET = db2.is_ET_FROMBRANCH(al.get(i).getIdoperation());
//                                db2.closeDB();
//                            }
//
//                            if (checkET) {
//                                oldrate = fd(al.get(i).getRate());
//                            } else {
//                                oldrate = fd(Engine.get_BCE(data_lotto, valutastraniera1.getCode()));
//                            }
//                        }
//
//                        if (oldrate == 0) {
//                            oldrate = vr1;
//                        }
//
//                        if (vq1 >= quant_check) {
//                            nuovorate += oldrate * quant_check;
//                            quant_check = 0.0;
//                        } else {
//                            nuovorate += oldrate * vq1;
//                            quant_check = quant_check - vq1;
//                        }
//                    }
//                }
//
//                nuovorate = Utility.roundDouble(nuovorate / quant_now, 8);
//                System.out.println("calcolato " + nuovorate);
//
//                if (tofrom.equals("T") && typeop.equals("BR")) {
//                    JSONRequest.add(gson.toJson("OK"));
//                    JSONRequest.add(gson.toJson("0,00"));
//                    JSONRequest.add(gson.toJson("0,00"));
//                    JSONRequest.add(gson.toJson(formatMysqltoDisplay(roundDoubleandFormat(nuovorate, 8))));
//                } else {
//                    double contr_CALCOLATO = getControvalore(fd(q2), nuovorate, dividi);
//                    System.out.println("A) " + fd(q2));
//                    System.out.println("B) " + nuovorate);
//                    System.out.println("C) " + contr);
//                    System.out.println("D) " + contr_CALCOLATO);
//                    double spreadtotal = contr - contr_CALCOLATO;
//                    JSONRequest.add(gson.toJson("OK"));
//                    JSONRequest.add(gson.toJson(formatMysqltoDisplay(roundDoubleandFormat(spreadtotal, 2))));
//                    JSONRequest.add(gson.toJson(formatMysqltoDisplay(roundDoubleandFormat(contr_CALCOLATO, 2))));
//                    JSONRequest.add(gson.toJson(formatMysqltoDisplay(r1)));
//                }
//            }
//        } else {
//            JSONRequest.add(gson.toJson("-"));
//            JSONRequest.add(gson.toJson("0,00"));
//            JSONRequest.add(gson.toJson("0,00"));
//            JSONRequest.add(gson.toJson("0,00"));
//        }

        if (ok) {
            List<String> output = calcolaspread2021(filiale, t, typeop, tofrom, f1, ki, q2, r1);
            if (output.isEmpty()) {
                JSONRequest.add(gson.toJson("-"));
                JSONRequest.add(gson.toJson("0,00"));
                JSONRequest.add(gson.toJson("0,00"));
                JSONRequest.add(gson.toJson("0,00"));
            } else {
                output.forEach(o1 -> {
                    JSONRequest.add(gson.toJson(o1));
                });
            }
        } else {
            JSONRequest.add(gson.toJson("-"));
            JSONRequest.add(gson.toJson("0,00"));
            JSONRequest.add(gson.toJson("0,00"));
            JSONRequest.add(gson.toJson("0,00"));
        }

        try (PrintWriter out = response.getWriter()) {
            out.print(JSONRequest);
        }

    }

    private boolean elimina(String user, String codtr) {

        Db_Master db = new Db_Master();
        String valutalocale = db.get_local_currency()[0];
        String dt_tr = db.getNow();
        Ch_transaction tra1 = db.query_transaction_ch(codtr);
        Ch_transaction tra = db.query_transaction_ch_temp(codtr);
        db.closeDB();

        if (tra1 != null) {
            // GIA' CONF NON POSSO ELIMINARLA PIU'
            return false;
        }
        if (tra == null || tra.getDel_fg().equals("1")) {
            // GIA' ELIMINATA
            return false;
        } else {

            Db_Master db2 = new Db_Master();
            ArrayList<Ch_transaction_value> val = db2.query_transaction_value(codtr);
            db2.closeDB();

            for (int i = 0; i < val.size(); i++) {
                if (val.get(i).getSupporto().equals("04") && tra.getTipotr().equals("B")) {

                    Real_oc_pos t1 = new Real_oc_pos();
                    t1.setFiliale(tra.getFiliale());
                    t1.setCod_oc(tra.getId_open_till());
                    t1.setValuta(val.get(i).getValuta());
                    t1.setKind(val.get(i).getSupporto());
                    t1.setCarta_credito(val.get(i).getPos());
                    t1.setData(dt_tr);
                    Db_Master db10 = new Db_Master();
                    t1.setIp_quantity(valueOf(parseIntR(db10.getField_real_oc_pos(t1, "ip_quantity", "0")) - 1));
                    t1.setIp_value(roundDoubleandFormat((fd(db10.getField_real_oc_pos(t1, "ip_value", "0.00")) - fd(val.get(i).getQuantita())), 2));
                    db10.update_real_oc_pos(t1);
                    db10.closeDB();

                } else {
                    Real_oc_change righe = new Real_oc_change();
                    righe.setFiliale(tra.getFiliale());
                    righe.setCod_oc(tra.getId_open_till());
                    righe.setValuta(val.get(i).getValuta());
                    righe.setKind(val.get(i).getSupporto());
                    righe.setData(dt_tr);
                    righe.setNum_kind_op("0");
                    Db_Master db10 = new Db_Master();
                    if (tra.getTipotr().equals("B")) {
                        righe.setValue_op(roundDoubleandFormat((fd(db10.getField_real_oc_change(righe, "value_op", "0.00"))
                                - fd(val.get(i).getQuantita())), 2));
                    } else {
                        righe.setValue_op(roundDoubleandFormat((fd(db10.getField_real_oc_change(righe, "value_op", "0.00"))
                                + fd(val.get(i).getQuantita())), 2));
                    }

                    db10.update_real_oc_change(righe);
                    db10.closeDB();
                }
            }
            if (tra.getTipotr().equals("S")) {
                Db_Master db10B = new Db_Master();
                ArrayList<String[]> listock = db10B.get_stock_quantity(codtr, "SE");
                ArrayList<String[]> damod = new ArrayList<>();
                for (int v = 0; v < listock.size(); v++) {
                    String[] vs = listock.get(v);
                    Stock st1 = db10B.get_stock(vs[0], "stock");
                    if (st1 != null) {
                        String[] v1 = {st1.getCodice(), "", "", st1.getCodice(), vs[1], st1.getRate(), "U", roundDoubleandFormat(fd(st1.getTotal()) + fd(vs[1]), 2)};
                        damod.add(v1);
                    } else {
                        st1 = db10B.get_stock(vs[0], "stock_story");
                        if (st1 != null) {
                            db10B.insertStock(st1);
                        }
                    }
                }
                db10B.updateStock(damod, false);
                db10B.closeDB();
                Db_Master db3 = new Db_Master();
                db3.delete_trans_stock(codtr, tra.getId_open_till());
                db3.closeDB();
            } else {
                Db_Master db4 = new Db_Master();
                db4.delete_trans_stock(codtr, tra.getId_open_till());
                db4.closeDB();
            }

            String paym = tra.getLocalfigures();
            if (paym.equals("-")) {
                paym = "01";
            }

            if (paym.equals("01")) {

                double tot = fd(tra.getPay());
                if (tra.getIntbook_type().equals("1") && tra.getTipotr().equals("S")) {
                    tot = fd(tra.getIntbook_mac());
                }

                Real_oc_change t1 = new Real_oc_change();
                t1.setFiliale(tra.getFiliale());
                t1.setCod_oc(tra.getId_open_till());
                t1.setValuta(valutalocale);
                t1.setKind(paym);
                t1.setData(dt_tr);
                t1.setNum_kind_op("0");
                Db_Master db5 = new Db_Master();
                if (tra.getTipotr().equals("B")) {
                    t1.setValue_op(roundDoubleandFormat((fd(db5.getField_real_oc_change(t1, "value_op", "0.00")) + tot), 2));
                } else {
                    t1.setValue_op(roundDoubleandFormat((fd(db5.getField_real_oc_change(t1, "value_op", "0.00")) - tot), 2));
                }
                db5.closeDB();

                Db_Master db6 = new Db_Master();
                db6.update_real_oc_change(t1);
                db6.closeDB();
            } else {
                Real_oc_pos t1 = new Real_oc_pos();
                t1.setFiliale(tra.getFiliale());
                t1.setCod_oc(tra.getId_open_till());
                t1.setValuta(valutalocale);
                t1.setKind(paym);
                t1.setCarta_credito(tra.getPos());
                t1.setData(dt_tr);
                Db_Master db10 = new Db_Master();
                t1.setIp_quantity(valueOf(parseIntR(db10.getField_real_oc_pos(t1, "ip_quantity", "0")) - 1));
                t1.setIp_value(roundDoubleandFormat((fd(db10.getField_real_oc_pos(t1, "ip_value", "0.00")) - fd(tra.getPay())), 2));
                db10.update_real_oc_pos(t1);
                db10.closeDB();
            }

            Db_Master db7 = new Db_Master();
            db7.delete_trans_stockreport(codtr, tra.getId_open_till());
            if (tra.getTipotr().equals("B")) {
                db7.libera_bb_transaction(codtr, user, "1");
            } else {
                db7.libera_bb_transaction(codtr, user, "3");
            }
            db7.closeDB();

            //23/07
            String t1 = "central";
            if (filiale.equals("000")) {
                t1 = null;
            }
            String loy = query_LOY_transaction(tra.getCod(), t1, filiale);
            remove_loy_assign_first_NEW(loy, tra.getCl_cod());//22/01

            //nuovo controllo
            if (!tra.getFa_number().equals("-") && !tra.getFa_number().equals("")) {
//            if (!tra.getFa_number().equals("-") && !tra.getFa_number().equals("") &&db2.esiste_successivo_con_fattura("ch_transaction_temp", codtr, tra.getFiliale())) {
                //CONFERMARE LA TRANS
                Db_Master db8 = new Db_Master();
                db8.confirm_transaction_change(codtr, tra.getFiliale());
                db8.closeDB();
                sleeping(1500);
                //ANNULLARLA DA SISTEMA
                Db_Master db9 = new Db_Master();
                db9.delete_transaction_ch(codtr, "ANNULLO AUTOMATICO DA SISTEMA - TRANSAZIONE NON CONFERMATA", user);
                db9.closeDB();
                //GENERARE NOTA CREDITO
                Db_Master db12 = new Db_Master();
                String invoice_number = db12.get_invoice_number(tra.getCod());
                db12.closeDB();

                Db_Master db13 = new Db_Master();
                db13.insert_inv_list(invoice_number, tra.getCod(), "N", "1", db13.getNow());
                db13.closeDB();

                Db_Master db14 = new Db_Master();
                db14.update_invoice_transaction(tra.getCod(), "ch_transaction", "cn_number", invoice_number);
                db14.closeDB();
                //VERIFICO SE PRESENTE FATTURA E NOTA CREDITO
                verifica_fa_nc(tra.getCod());

            } else {

                //ELIMINARE FATTURA
                Db_Master db8 = new Db_Master();
                db8.delete_trans_invoice(codtr);
                db8.delete_trans_docum(codtr);
                db8.delete_trans_value(codtr);
                db8.delete_trans_temp(codtr);
                db8.closeDB();
                //ELIMINA CLIENTE SE L'UNICA VOLTA
                Db_Master db12 = new Db_Master();
                db12.delete_Client(tra.getCl_cod());
                db12.closeDB();
            }
            Db_Master db12 = new Db_Master();
            db12.deleteLinktransaction(user, codtr);
//            //NEW
            db12.delete_transaction_nochfromsell(codtr);
            db12.liberaCodiciSblocco(codtr, user);
            db12.delete_marketing(codtr, user);
            db12.closeDB();
        }

        return false;
    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void del_ch_tr_buy(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String user = (String) request.getSession().getAttribute("us_cod");
        if (user == null) {
            user = "9999";
        }
        String codtr = request.getParameter("codtr");
//        String booking = request.getParameter("booking");
//        String codop = request.getParameter("codop");

        Db_Master db = new Db_Master();
        String[] cur_default = db.get_local_currency();
        String dt_tr = db.getNow();
        Ch_transaction tra = db.query_transaction_ch_temp(codtr);
        Ch_transaction tra1 = db.query_transaction_ch(codtr);

//        Booking bo = db.get_prenot(booking);
//        if (bo != null) {
////            boolean out = db.edit_stato_prenotazione_dafiliale(booking, "6");
////            if (bo.getCanale().contains("1")) {
////                try {
////                    sendRequest_SITO("editbooking", Engine.getBookingJSON(bo, "6"));
////                } catch (Exception e1) {
////                    Engine.insertTR("E", user, "ERROR EDIT BOOKING " + booking + ": " + codtr + " - " + e1.getMessage());
////                }
//////                boolean es1 = Website.cancel(booking, request);
//////                if (es1) {
//////                    Engine.insertTR("W", user, "website cancel booking " + booking + ": " + out);
//////                }
////            }
//        }
        db.closeDB();

        if (tra1 != null) {
            // GIA' CONFERMATA
        } else if (tra == null || tra.getDel_fg().equals("1")) {
            // GIA' ELIMINATA
        } else {
            String ok = checkDeleteTR(codtr);
            if (ok.equals("OK")) {
                Db_Master db2 = new Db_Master();
                ArrayList<Ch_transaction_value> val = db2.query_transaction_value(codtr);
                db2.closeDB();
                for (int i = 0; i < val.size(); i++) {
                    if (val.get(i).getSupporto().equals("04") && tra.getTipotr().equals("B")) {
                        Real_oc_pos t1 = new Real_oc_pos();
                        t1.setFiliale(tra.getFiliale());
                        t1.setCod_oc(tra.getId_open_till());
                        t1.setValuta(val.get(i).getValuta());
                        t1.setKind(val.get(i).getSupporto());
                        t1.setCarta_credito(val.get(i).getPos());
                        t1.setData(dt_tr);
                        Db_Master db10 = new Db_Master();
                        t1.setIp_quantity(valueOf(parseIntR(db10.getField_real_oc_pos(t1, "ip_quantity", "0")) - 1));
                        t1.setIp_value(roundDoubleandFormat((fd(db10.getField_real_oc_pos(t1, "ip_value", "0.00")) - fd(val.get(i).getQuantita())), 2));
                        db10.update_real_oc_pos(t1);
                        db10.closeDB();
                    } else {
                        Real_oc_change righe = new Real_oc_change();
                        righe.setFiliale(tra.getFiliale());
                        righe.setCod_oc(tra.getId_open_till());
                        righe.setValuta(val.get(i).getValuta());
                        righe.setKind(val.get(i).getSupporto());
                        righe.setData(dt_tr);
                        righe.setNum_kind_op("0");
                        Db_Master db3 = new Db_Master();
                        if (tra.getTipotr().equals("B")) {
                            righe.setValue_op(roundDoubleandFormat((fd(db3.getField_real_oc_change(righe, "value_op", "0.00"))
                                    - fd(val.get(i).getQuantita())), 2));
                        } else {
                            righe.setValue_op(roundDoubleandFormat((fd(db3.getField_real_oc_change(righe, "value_op", "0.00"))
                                    + fd(val.get(i).getQuantita())), 2));
                        }
                        db3.update_real_oc_change(righe);
                        db3.closeDB();
                    }
                }
                if (tra.getTipotr().equals("S")) {
                    Db_Master db10B = new Db_Master();
                    ArrayList<String[]> listock = db10B.get_stock_quantity(codtr, "SE");
                    ArrayList<String[]> damod = new ArrayList<>();
                    for (int v = 0; v < listock.size(); v++) {
                        String[] vs = listock.get(v);
                        Stock st1 = db10B.get_stock(vs[0], "stock");
                        if (st1 != null) {
                            String[] v1 = {st1.getCodice(), "", "", st1.getCodice(), vs[1], st1.getRate(), "U", roundDoubleandFormat(fd(st1.getTotal()) + fd(vs[1]), 2)};
                            damod.add(v1);
                        } else {
                            st1 = db10B.get_stock(vs[0], "stock_story");
                            if (st1 != null) {
                                db10B.insertStock(st1);
                            }
                        }
                    }
                    db10B.updateStock(damod, false);
                    db10B.closeDB();
                    Db_Master db4 = new Db_Master();
                    db4.delete_trans_stock(codtr, tra.getId_open_till());
                    db4.closeDB();
                } else {
                    Db_Master db5 = new Db_Master();
                    db5.delete_trans_stock(codtr, tra.getId_open_till());
                    db5.closeDB();
                }

                String paym = tra.getLocalfigures();
                if (paym.equals("-")) {
                    paym = "01";
                }

                if (paym.equals("01")) {

                    double tot = fd(tra.getPay());
                    if (tra.getIntbook_type().equals("1") && tra.getTipotr().equals("S")) {
                        tot = fd(tra.getIntbook_mac());
                    }

                    Real_oc_change t1 = new Real_oc_change();
                    t1.setFiliale(tra.getFiliale());
                    t1.setCod_oc(tra.getId_open_till());
                    t1.setValuta(cur_default[0]);
                    t1.setKind(paym);
                    t1.setData(dt_tr);
                    t1.setNum_kind_op("0");
                    Db_Master db6 = new Db_Master();
                    if (tra.getTipotr().equals("B")) {
                        t1.setValue_op(roundDoubleandFormat((fd(db6.getField_real_oc_change(t1, "value_op", "0.00")) + tot), 2));
                    } else {
                        t1.setValue_op(roundDoubleandFormat((fd(db6.getField_real_oc_change(t1, "value_op", "0.00")) - tot), 2));
                    }
                    db6.update_real_oc_change(t1);
                    db6.closeDB();
                } else {
                    Real_oc_pos t1 = new Real_oc_pos();
                    t1.setFiliale(tra.getFiliale());
                    t1.setCod_oc(tra.getId_open_till());
                    t1.setValuta(cur_default[0]);
                    t1.setKind(paym);
                    t1.setCarta_credito(tra.getPos());
                    t1.setData(dt_tr);
                    Db_Master db10 = new Db_Master();
                    t1.setIp_quantity(valueOf(parseInt(db10.getField_real_oc_pos(t1, "ip_quantity", "0")) - 1));
                    t1.setIp_value(roundDoubleandFormat((fd(db10.getField_real_oc_pos(t1, "ip_value", "0.00")) - fd(tra.getPay())), 2));
                    db10.update_real_oc_pos(t1);
                    db10.closeDB();
                }
                Db_Master db7 = new Db_Master();
                db7.delete_trans_stockreport(codtr, tra.getId_open_till());
                if (tra.getTipotr().equals("B")) {
                    db7.libera_bb_transaction(codtr, user, "1");
                } else {
                    db7.libera_bb_transaction(codtr, user, "3");
                }
                db7.closeDB();

                //23/07
                String t1 = "central";
                if (filiale.equals("000")) {
                    t1 = null;
                }
                String loy = query_LOY_transaction(tra.getCod(), t1, filiale);
                remove_loy_assign_first_NEW(loy, tra.getCl_cod());//22/01

                //nuovo controllo
                if (!tra.getFa_number().equals("-") && !tra.getFa_number().equals("")) {
//            if (!tra.getFa_number().equals("-") && !tra.getFa_number().equals("")&&db2.esiste_successivo_con_fattura("ch_transaction_temp", codtr, tra.getFiliale())) {
                    //CONFERMARE LA TRANS
                    Db_Master db8 = new Db_Master();
                    db8.confirm_transaction_change(codtr, tra.getFiliale());
                    db8.closeDB();
                    sleeping(1500);
                    //ANNULLARLA DA SISTEMA
                    Db_Master db9 = new Db_Master();
                    db9.delete_transaction_ch(codtr, "ANNULLO AUTOMATICO DA SISTEMA - TRANSAZIONE NON CONFERMATA", user);
                    db9.closeDB();
                    //GENERARE NOTA CREDITO
                    Db_Master db10 = new Db_Master();
                    String invoice_number = db10.get_invoice_number(tra.getCod());
                    db10.closeDB();
                    Db_Master db11 = new Db_Master();
                    db11.insert_inv_list(invoice_number, tra.getCod(), "N", "1", db11.getNow());
                    db11.closeDB();
                    Db_Master db12 = new Db_Master();
                    db12.update_invoice_transaction(tra.getCod(), "ch_transaction", "cn_number", invoice_number);
                    db12.closeDB();
                    //VERIFICO SE PRESENTE FATTURA E NOTA CREDITO
                    verifica_fa_nc(tra.getCod());

                } else {

//                    //ELIMINARE FATTURA
//                    Db_Master db12 = new Db_Master();
//                    db12.delete_trans_invoice(codtr);
//                    db12.delete_trans_docum(codtr);
//                    db12.delete_trans_value(codtr);
//                    db12.delete_trans_temp(codtr);
//                    //ELIMINA CLIENTE SE L'UNICA VOLTA
//                    db12.delete_Client(tra.getCl_cod());
//                    db12.closeDB();
                    //MODIFICA 26/07/23 TRACCIARE SEMPRE TRANSAZIONE
                    //CONFERMARE LA TRANS
                    Db_Master db8 = new Db_Master();
                    db8.confirm_transaction_change(codtr, tra.getFiliale());
                    db8.closeDB();
                    sleeping(1500);
                    //ANNULLARLA DA SISTEMA
                    Db_Master db9 = new Db_Master();
                    if (is_IT) {
                        db9.delete_transaction_ch(codtr, "ANNULLO AUTOMATICO DA SISTEMA - TRANSAZIONE NON CONFERMATA", user);
                    } else {
                        db9.delete_transaction_ch(codtr, "AUTOMATIC CANCELLATION BY SYSTEM - TRANSACTION NOT CONFIRMED", user);
                    }
                    db9.closeDB();

                }
                Db_Master db12 = new Db_Master();
                db12.deleteLinktransaction(user, codtr);
//            //NEW
                db12.delete_transaction_nochfromsell(codtr);
                db12.liberaCodiciSblocco(codtr, user);
                db12.delete_marketing(codtr, user);
                db12.closeDB();

                //  Viene annullata la transazione no change legata allo scarico automatico magazzino per le WK/TIC.
//                Db_Master db13 = new Db_Master();
//                List<String> li_tr = db13.get_WK_TI(tra);
//                li_tr.forEach(cod1 -> {
//                    remove_nochange_transaction(cod1,
//                            "The customer refused to give consent for data processing.",
//                            tra.getUser(), tra.getFiliale());
//                });
//                db13.closeDB();
            }
        }

        redirect(request, response, "transaction.jsp");

    }

//    private static void modifica_prenotazione_quando_confermata(Booking bo, Ch_transaction ch, ArrayList<Ch_transaction_value> list) {
//        try {
//            Db_Master db = new Db_Master();
//            db.edit_prenotazione_final(bo.getCod(), ch.getCommission(),
//                    ch.getPay(), list.get(0).getQuantita(), list.get(0).getRate());
//            db.closeDB();
//            JSONObject json = new JSONObject();
//            json.put("commission", ch.getCommission());
//            json.put("id", bo.getCod());
//            json.put("total", ch.getPay());
//            json.put("quantita", list.get(0).getQuantita());
//            json.put("rate", list.get(0).getRate());
//            sendRequest_SITO("finalizebooking", json.toString(), bo.getCod());
//        } catch (Exception ex) {
//            System.err.println("ERROR: " + ex.getMessage());
//        }
//    }
    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void conf_ch_tr_buy(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String user = (String) request.getSession().getAttribute("us_cod");
        if (user == null) {
            user = "9999";
        }
        String booking = request.getParameter("booking");
        String cod = request.getParameter("codtr");
        String fil = request.getParameter("filiale");
        Db_Master db = new Db_Master();
        Booking bo = db.get_prenot(booking);
        if (bo != null) {
            db.edit_stato_prenotazione_dafiliale(booking, "7");
            db.finalizza_prenotazione(booking, cod);
        }
        boolean es = db.confirm_transaction_change(cod, fil);
        db.closeDB();

        if (es) {
            deleteLinktransaction(user, cod);
            if (Constant.is_IT) {
                redirect(request, response, "transaction_redir.jsp?cod=" + cod);
            } else {
                redirect(request, response, "transaction.jsp?esito=ok");
            }
        } else {
            redirect(request, response, getLink_last(cod) + "&esito=ko&booking=" + booking);
        }
    }

    private ArrayList<String[]> nc_taxfree(Ch_transaction trans) {
        ArrayList<String[]> nc_taxfree = new ArrayList<>();
        if (trans.getIntbook_type().equals("1")) {
            if (!trans.getIntbook_1_tf().equals("-")) {
                if (!trans.getIntbook_1_val().equals("") && !trans.getIntbook_1_val().equals("0.00") && !trans.getIntbook_1_val().equals("0,00")) {
                    String[] val = {trans.getIntbook_1_tf(), trans.getIntbook_1_mod(), trans.getIntbook_1_val()};
                    nc_taxfree.add(val);
                }
            }
            if (!trans.getIntbook_2_tf().equals("-")) {
                if (!trans.getIntbook_2_val().equals("") && !trans.getIntbook_2_val().equals("0.00") && !trans.getIntbook_2_val().equals("0,00")) {
                    String[] val = {trans.getIntbook_2_tf(), trans.getIntbook_2_mod(), trans.getIntbook_2_val()};
                    nc_taxfree.add(val);
                }
            }
            if (!trans.getIntbook_3_tf().equals("-")) {
                if (!trans.getIntbook_3_val().equals("") && !trans.getIntbook_3_val().equals("0.00") && !trans.getIntbook_3_val().equals("0,00")) {
                    String[] val = {trans.getIntbook_3_tf(), trans.getIntbook_3_mod(), trans.getIntbook_3_val()};
                    nc_taxfree.add(val);
                }

            }
        }
        return nc_taxfree;
    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void transaction_Ch_BUY_webtrans(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String kycpres = getValue_request(request, "kycpres", true, "0");

        boolean modified = false;
        //NUOVO SERVIZI AGG
        ArrayList<String[]> agevol = agevolazioni_varie();
        StringBuilder agevol_active = new StringBuilder("");
        for (int x = 0; x < agevol.size(); x++) {
            if (request.getParameter("ag_" + agevol.get(x)[0]) != null) {
                if (request.getParameter("ag_" + agevol.get(x)[0]).equals("on")) {
                    agevol_active.append(agevol.get(x)[0]).append(";");
                }
            }
        }

        ArrayList<String[]> ser = servizi_agg();
        StringBuilder ser_active = new StringBuilder("");
//        ArrayList<String> ser_active = new ArrayList<>();
        for (int x = 0; x < ser.size(); x++) {
            if (request.getParameter("se_" + ser.get(x)[0]) != null) {
                if (request.getParameter("se_" + ser.get(x)[0]).equals("on")) {
                    ser_active.append(ser.get(x)[0]).append(";");
                }
            }
        }

        boolean sb = false;
        if (ser_active.toString().contains("SEB")) {
            sb = true;
        }

        // NUOVO INTBOOK
        String newintbook0 = request.getParameter("newintbook0");
        String newintbookCanale = request.getParameter("newintbookCanale");
        String newintbookCodice = request.getParameter("newintbookCodice");

        if (null == newintbook0) {
            newintbook0 = "0";
            newintbookCanale = "";
            newintbookCodice = "";
        } else {
            switch (newintbook0) {
                case "on":
                    newintbook0 = "1";
                    break;
                default:
                    newintbook0 = "0";
                    newintbookCanale = "";
                    newintbookCodice = "";
                    break;
            }
        }

        String ok = "0";

        String user = (String) request.getSession().getAttribute("us_cod");
        if (user == null) {
            user = "9999";
        }

        Db_Master db = new Db_Master();
        String[] cur_default = db.get_local_currency();
        String dt_tr = db.getNow();
        db.closeDB();

        String cod = request.getParameter("cod");

        String notesbigV = "0";

        String company = getValue_request(request, "company", false, "");

        String typerate = getValue_request(request, "typerate", false, "");
        String unlockCode_final = getValue_request(request, "unlockCode_final", false, "");
        String unlockratesel = getValue_request(request, "unlockratesel", false, "");

        String oldclient = getValue_request(request, "oldclient", false, "");
        String customerKind = getValue_request(request, "customerKind", false, "");
        String id_open_till = getValue_request(request, "id_open_till", false, "");

        String till = getValue_request(request, "till", false, "");
        String pay = getValue_request(request, "payout1", false, "");
        String total = getValue_request(request, "total0", false, "");
        String fix = getValue_request(request, "fix0", false, "");
        String com = getValue_request(request, "com0", false, "");
        String round = getValue_request(request, "round0", false, "");
        String commission = getValue_request(request, "commission0", false, "");
        String note = getValue_request(request, "note1", false, "");
        String agency = getValue_request(request, "agroy", false, "0");
        String agency_cod = getValue_request(request, "heavy_agency", false, "-");
        String cl_cf = getValue_request(request, "heavy_codfisc", false, "---");
        String heavy_surname = getValue_request(request, "heavy_surname", false, "");
        String heavy_name = getValue_request(request, "heavy_name", false, "");
        String heavy_sex = getValue_request(request, "heavy_sex", false, "M");
        String heavy_country = getValue_request(request, "heavy_country", false, "");
        String heavy_city = getValue_request(request, "heavy_city", false, "");
        String heavy_city_dis = getValue_request(request, "heavy_city_dis", false, "");
        String heavy_address = getValue_request(request, "heavy_address", false, "-");

        String heavy_zipcode = getValue_request(request, "heavy_zipcode", false, "");
        if (heavy_zipcode.length() > 10) {
            heavy_zipcode = substring(heavy_zipcode, 0, 10);
        }

        String heavy_district = getValue_request(request, "heavy_district", false, "");
        String heavy_district_dis = getValue_request(request, "heavy_district_dis", false, "");
        String heavy_pob_city = getValue_request(request, "heavy_pob_city", false, "");
        String heavy_pob_country = getValue_request(request, "heavy_pob_country", false, "");
        String heavy_pob_date = getValue_request(request, "heavy_pob_date", false, "");
        String heavy_identcard = getValue_request(request, "heavy_identcard", false, "");
        String heavy_numberidentcard = getValue_request(request, "heavy_numberidentcard", false, "");
        String heavy_issuedateidentcard = getValue_request(request, "heavy_issuedateidentcard", false, "");
        String heavy_exdateidentcard = getValue_request(request, "heavy_exdateidentcard", false, "");
        String heavy_issuedbyidentcard = getValue_request(request, "heavy_issuedbyidentcard", false, "");
        String heavy_issuedplaceidentcard = getValue_request(request, "heavy_issuedplaceidentcard", false, "");
        String heavy_email = getValue_request(request, "heavy_email", false, "");
        String heavy_phonenu = getValue_request(request, "heavy_phonenu", false, "");
        String heavy_pepI = getValue_request(request, "heavy_pepI", false, "");
        if (heavy_pepI.equals("")) {
            heavy_pepI = "NO";
        }
        String pr_nas1 = getValue_request(request, "heavy_pob_district", false, "---");
        String pr_nas2 = getValue_request(request, "heavy_pob_district_STR", false, "");
        if (is_IT && heavy_pob_country.equals(codnaz)) { //    ITALIA
            if (pr_nas1.equals("")) {
                pr_nas1 = pr_nas2;
            }
        } else {
            pr_nas1 = "---";
        }
        String cod_cliente = generaId(22) + filiale;

        //CZ
        boolean controllareCliente = true;
        boolean giuridica = false;
        String oldpercent_buy = "-";
        String oldpercent_sell = "-";

        if (!controllareCliente) {
            cod_cliente = "---";
            cl_cf = "---";
        } else {
            if (is_IT) {

                if (customerKind.equals("003")) { //persona giuridica
                    giuridica = true;
                    cod_cliente = company;
                    cl_cf = "---";
                } else if (!oldclient.equals("0")) {
                    cod_cliente = oldclient;
                    oldclient = "1";
                } else {
                    Db_Master db1 = new Db_Master(true);
                    if (db1.getC() == null) {
                        db1 = new Db_Master();
                    }
                    String cl_check = db1.get_codice_client(cl_cf, customerKind,
                            heavy_name, heavy_surname, heavy_pob_date, heavy_pob_country);
                    if (cl_check != null) {
                        cod_cliente = cl_check;
                        String[] percent = db1.getClientCommission(cod_cliente);
                        oldpercent_buy = percent[0];
                        oldpercent_sell = percent[1];
                        oldclient = "1";
                    }
                    db1.closeDB();
                }
                if (is_IT && heavy_pob_country.equals(codnaz)) { //ITALIA
                    if (pr_nas1 == null) {
                        pr_nas1 = pr_nas2;
                        if (pr_nas1 == null) {
                            pr_nas1 = "---";
                        }
                    }
                } else {
                    pr_nas1 = "---";
                }
            }

        }

        Ch_transaction trans = new Ch_transaction();
        trans.setTill(till);
        trans.setTipocliente(customerKind);
        trans.setId_open_till(id_open_till);
        trans.setPay(formatDoubleforMysql(pay));
        trans.setTotal(formatDoubleforMysql(total));
        trans.setFix(formatDoubleforMysql(fix));
        trans.setCom(formatDoubleforMysql(com));
        trans.setRound(formatDoubleforMysql(round));
        trans.setCommission(formatDoubleforMysql(commission));
        trans.setNote(substring(getStringUTF8(note), 0, 100));
        trans.setAgency(agency);
        trans.setAgency_cod(agency_cod);
        trans.setCl_cf(cl_cf);
        trans.setDel_fg("0");
        trans.setDel_dt("1901-01-01 00:00:00");
        trans.setDel_user("-");
        trans.setDel_motiv("-");

        trans.setIntbook(newintbook0);

        String tfnc0 = "0";

        String intbook_1 = "-";
        String code_intbook_1 = "-";
        String value_intbook_1 = "-";

        String intbook_2 = "-";
        String code_intbook_2 = "-";
        String value_intbook_2 = "-";

        String intbook_3 = "-";
        String code_intbook_3 = "-";
        String value_intbook_3 = "-";

        String macval = "-";
        String cusval = "-";

        trans.setIntbook_type(tfnc0);
        trans.setIntbook_1_mod(formatDoubleforMysql(code_intbook_1));
        trans.setIntbook_1_tf(intbook_1);
        trans.setIntbook_1_val(formatDoubleforMysql(value_intbook_1));
        trans.setIntbook_2_mod(formatDoubleforMysql(code_intbook_2));
        trans.setIntbook_2_tf(intbook_2);
        trans.setIntbook_2_val(formatDoubleforMysql(value_intbook_2));
        trans.setIntbook_3_mod(formatDoubleforMysql(code_intbook_3));
        trans.setIntbook_3_tf(intbook_3);
        trans.setIntbook_3_val(formatDoubleforMysql(value_intbook_3));
        trans.setIntbook_cli(formatDoubleforMysql(cusval));
        trans.setIntbook_mac(formatDoubleforMysql(macval));

        trans.setLocalfigures("-");
        trans.setPos("-");
        trans.setCredccard_number("-");

        trans.setData(dt_tr);
        trans.setFiliale(filiale);
        trans.setUser(user);
        trans.setCod(cod);

////        CLIENTE
        Client cl = new Client();
        cl.setCode(cod_cliente);
        cl.setCognome(heavy_surname);
        cl.setNome(heavy_name);
        cl.setSesso(heavy_sex);
        cl.setCodfisc(cl_cf);
        cl.setNazione(heavy_country);
        String heavy_city1 = heavy_city;
        if (heavy_city1.equals("")) {
            heavy_city1 = heavy_city_dis;
        }
        cl.setCitta(heavy_city1);
        cl.setIndirizzo(heavy_address);
        cl.setCap(heavy_zipcode);
        String pro = heavy_district;
        if (pro.trim().equals("")) {
            pro = heavy_district_dis;
            if (pro.trim().equals("")) {
                pro = "-";
            }
        }
        cl.setProvincia(pro);
        cl.setCitta_nascita(heavy_pob_city);
        cl.setProvincia_nascita(pr_nas1);
        cl.setNazione_nascita(heavy_pob_country);
        cl.setDt_nascita(heavy_pob_date);
        cl.setTipo_documento(heavy_identcard);
        cl.setNumero_documento(heavy_numberidentcard);
        cl.setDt_rilascio_documento(heavy_issuedateidentcard);
        cl.setDt_scadenza_documento(heavy_exdateidentcard);
        cl.setRilasciato_da_documento(heavy_issuedbyidentcard);
        cl.setLuogo_rilascio_documento(heavy_issuedplaceidentcard);
        cl.setEmail(heavy_email);
        cl.setTelefono(heavy_phonenu);

        cl.setPerc_buy("-");
        cl.setPerc_sell("-");

        cl.setTimestamp(dt_tr);

        cl.setPep(heavy_pepI);

        Db_Master db2 = new Db_Master();
        CustomerKind ck = db2.get_customerKind(customerKind);
        db2.closeDB();

        String esitobl = "NO";
        BlacklistM bm = null;
        BlackListsAT bl = null;

        if (!giuridica) {
            bm = verifyBlacklistMac(cl);
            bl = verificaBlackList(cod, "MACCORP", filiale, user, cl);
            esitobl = bl.getEsito();
        }

        boolean bbtot = false;
        boolean sbtot = false;

        if (esitobl.equals("1")) {
            ok = "kobl";
            request.getSession().setAttribute("msgbl1", "<br/><b>SURNAME:</b> " + heavy_surname + "<br/><b>NAME:</b> " + heavy_name);
        } else if (bm != null) {
            ok = "koblm&codbl=" + bm.getCode();
        } else {
            if (!giuridica) {
                if (bl != null) {
                    if (esitobl.equals("-1")) {
                        bl.setEsito("2");
                    }
                    trans.setBl_status(bl.getEsito());
                    trans.setBl_motiv(bl.getError());
                }
            } else {
                trans.setBl_status("2");
                trans.setBl_motiv("-");
            }

            trans.setTipotr("B");
            trans.setCl_cod(cod_cliente);
            trans.setRefund("0");
            trans.setFa_number("-");
            trans.setCn_number("-");
            trans.setHeavy_pepI("NO");

            ArrayList<Ch_transaction_value> list_row = new ArrayList<>();

            double newbuypercent = 0.00;
            double spread_total = 0.0;

            int ind = 1;// riga singola

            String totalriga = getValue_request(request, "total" + ind, false, "");
            if (!formatDoubleforMysql(totalriga).equals("0.00")) {
                String idriga = generaId(25);
                String codtr = trans.getCod();
                String numeroriga = valueOf(ind);
                String supportoriga = getValue_request(request, "kind" + ind, false, "");
                String posriga = getValue_request(request, "posvalue" + ind, false, "N");
                String posnumriga = getValue_request(request, "posnum" + ind, false, "");
                String valutariga = getValue_request(request, "figs" + ind, false, "");
                String quantitariga = getValue_request(request, "quantity" + ind, false, "");
                String rateriga = getValue_request(request, "rate" + ind, false, "");
                if (typerate.equals("UNLOCK")) {
                    rateriga = formatDoubleforMysql(getValue_request(request, "fieldrate" + ind, false, ""));
                }
                rateriga = removeLast(rateriga);

                String spreadriga = getValue_request(request, "spread" + ind, false, "KO");
                if (!spreadriga.equals("KO")) {

                    List<String> calcolospread = calcolaspread2021(
                            filiale,
                            "B",
                            "",
                            "",
                            valutariga,
                            supportoriga,
                            formatDoubleforMysql(quantitariga),
                            rateriga);
                    if (calcolospread.isEmpty()) {
                        spreadriga = formatDoubleforMysql(spreadriga);
                    } else {
                        try {
                            spreadriga = calcolospread.get(1).equals("0,00") ? "0.00" : formatDoubleforMysql(calcolospread.get(1));
                        } catch (Exception e) {
                            spreadriga = formatDoubleforMysql(spreadriga);
                        }
                    }
                }

                String roundvalue = getValue_request(request, "roundvalue" + ind, false, "");
                String com_percriga = getValue_request(request, "comperc" + ind, false, "");
                String com_perc_totriga = getValue_request(request, "totperc" + ind, false, "");
                String fx_commriga = getValue_request(request, "fxcomm" + ind, false, "");
                String tot_comriga = getValue_request(request, "totcomm" + ind, false, "");
                String netriga = getValue_request(request, "net" + ind, false, "");
                String kind_fix_commriga = getValue_request(request, "kindfixcomm" + ind, false, "-");
                String low_comm_juriga = getValue_request(request, "lowcommjus" + ind, false, "");

                String sb_bb = "N";

                if (sb) {
                    sb_bb = "Y";
                    sbtot = true;
                }

                String contr_valuta = cur_default[0];
                String contr_supporto = "01";
                String contr_quantita = trans.getPay();

                Ch_transaction_value chv = new Ch_transaction_value();
                chv.setId(idriga);
                chv.setCod_tr(codtr);
                chv.setNumeroriga(numeroriga);
                chv.setSupporto(supportoriga);
                chv.setPos(posriga);
                chv.setPosnum(posnumriga);
                chv.setValuta(valutariga);
                chv.setQuantita(formatDoubleforMysql(quantitariga));
                chv.setRate(rateriga);
                chv.setCom_perc(formatDoubleforMysql(com_percriga));
                newbuypercent = fd(formatDoubleforMysql(com_percriga));

                chv.setCom_perc_tot(formatDoubleforMysql(com_perc_totriga));
                chv.setFx_com(formatDoubleforMysql(fx_commriga));
                chv.setTot_com(formatDoubleforMysql(tot_comriga));
                chv.setNet(removeLast(formatDoubleforMysql(netriga)));
                chv.setSpread(spreadriga);
                chv.setTotal(formatDoubleforMysql(totalriga));
                chv.setKind_fix_comm(kind_fix_commriga);
                chv.setLow_com_ju(low_comm_juriga);
                chv.setBb(sb_bb);
                chv.setBb_fidcode("-");
                chv.setDt_tr(dt_tr);
                chv.setContr_valuta(contr_valuta);
                chv.setContr_supporto(contr_supporto);
                chv.setContr_quantita(contr_quantita);
                chv.setRoundvalue(roundvalue);
                chv.setDel_fg("0");
                chv.setDel_dt("1901-01-01 00:00:00");
                list_row.add(chv);

                spread_total = spread_total + fd(spreadriga);

            }

            if (newbuypercent > 0) {
                cl.setPerc_buy(roundDoubleandFormat(newbuypercent, 2));
            } else {
                cl.setPerc_buy(oldpercent_buy);
            }
            cl.setPerc_sell(oldpercent_sell);

            if (ok.equals("0")) {
                if (list_row.isEmpty()) {
                    ok = "koins2";
                }
            }

            if (ok.equals("0")) {
                Db_Master db3 = new Db_Master();
                ArrayList<String[]> real = db3.list_oc_change_real(id_open_till);

                db3.closeDB();
////                controlli cassa euro
                double start = 0.00;

                for (int i = 0; i < real.size(); i++) {
                    if (real.get(i)[2].equals(cur_default[0]) && real.get(i)[1].equals("01")) {
                        start = fd(real.get(i)[3]);
                        break;
                    }
                }
                if (start < fd(trans.getPay())) {
                    ok = "koquant";
                }
            }

            if (ok.equals("0")) { //settimanale
                double soglia = fd(ck.getIp_max_settimanale());
                soglia = get_soglia_CZ(soglia);
                if (fd(trans.getPay()) > soglia) {
                    ok = "komaxsett";
                }
                if (ok.equals("0")) {

                    //NUOVO 09/05
                    Db_Master db4A = new Db_Master();
                    double weekA = db4A.weekly_transaction(cod_cliente);
                    db4A.closeDB();

                    Db_Master db4 = new Db_Master(true);
                    if (db4.getC() == null) {
                        db4 = new Db_Master();
                    }
                    double week = db4.weekly_transaction_nofiliale(cod_cliente, trans.getFiliale());
//                        double week = db4.weekly_transaction(cod_cliente);
                    db4.closeDB();
                    double now = fd(trans.getPay()) + week + weekA;
//                        double now = fd(trans.getPay()) + week;

                    if (now > soglia) {
                        ok = "komaxsett";
                    }

                }
            }

            trans.setSpread_total(roundDoubleandFormat(spread_total, 2));

            trans.setBb(getStatus_BBSB(bbtot, sbtot));

            if (ok.equals("0")) {
                Db_Master db6 = new Db_Master();
                boolean es1 = db6.insert_transaction_change(trans, "ch_transaction_temp");
                db6.closeDB();
                if (es1) {
                    //internetbooking
                    Db_Master dbib = new Db_Master();
                    boolean esib = dbib.insert_InternetBookingCH(cod, newintbookCanale, newintbookCodice, user);
                    dbib.closeDB();

                    Db_Master db7 = new Db_Master();
                    boolean es2 = db7.insert_transaction_value(list_row);
                    db7.closeDB();
                    if (es2) {

                        insertStock_change(trans, list_row);

                        Db_Master db8 = new Db_Master();
                        Stock_report sr = db8.get_Stock_report(id_open_till, cur_default[0], "01", "CH", filiale, till);
                        db8.closeDB();
                        //  DIMINUISCO EURO
                        String codsr = filiale + generaId(47);
                        sr.setCodtr(cod);
                        sr.setCodice(codsr);
//                        double newtot = fd(sr.getTotal()) - fd(trans.getPay());
                        double newtot = -fd(trans.getPay());
                        sr.setTotal(roundDoubleandFormat(newtot, 2));
                        sr.setSpread("1.0000");
                        sr.setData(dt_tr);
                        sr.setQuantity("0");
                        sr.setUser(user);
                        Db_Master db9 = new Db_Master();
                        db9.insert_Stockreport(sr);
                        db9.closeDB();

                        for (int i = 0; i < list_row.size(); i++) {
                            Ch_transaction_value ctv = list_row.get(i);

                            Db_Master db8A = new Db_Master();
                            Stock_report srA = db8A.get_Stock_report(id_open_till, ctv.getValuta(), ctv.getSupporto(), "CH", filiale, till);
                            db8A.closeDB();

                            //  AUMENTO INCASSI
                            String codsrA = filiale + generaId(47);
                            srA.setCodtr(cod);
                            srA.setCodice(codsrA);
//                            double newtotA = fd(srA.getTotal()) + fd(ctv.getQuantita());
                            double newtotA = fd(ctv.getQuantita());
                            srA.setTotal(roundDoubleandFormat(newtotA, 2));
                            srA.setSpread("1.0000");
                            srA.setData(dt_tr);
                            srA.setQuantity("0");
                            srA.setUser(user);
                            Db_Master db9A = new Db_Master();
                            db9A.insert_Stockreport(srA);
                            db9A.closeDB();

                        }

                        for (int i = 0; i < list_row.size(); i++) {
                            Real_oc_change righe = new Real_oc_change();
                            righe.setFiliale(filiale);
                            righe.setCod_oc(id_open_till);
                            righe.setValuta(list_row.get(i).getValuta());
                            righe.setKind(list_row.get(i).getSupporto());
                            righe.setData(dt_tr);
                            righe.setNum_kind_op("0");
                            Db_Master db10 = new Db_Master();
                            righe.setValue_op(roundDoubleandFormat((fd(db10.getField_real_oc_change(righe, "value_op", "0.00")) + fd(list_row.get(i).getQuantita())), 2));

                            db10.update_real_oc_change(righe);
                            db10.closeDB();
                        }

                        Real_oc_change t1 = new Real_oc_change();
                        t1.setFiliale(filiale);
                        t1.setCod_oc(id_open_till);
                        t1.setValuta(cur_default[0]);
                        t1.setKind("01");
                        t1.setData(dt_tr);
                        t1.setNum_kind_op("0");
                        Db_Master db11 = new Db_Master();
                        t1.setValue_op(roundDoubleandFormat((fd(db11.getField_real_oc_change(t1, "value_op", "0.00")) - fd(trans.getPay())), 2));
                        db11.update_real_oc_change(t1);
                        db11.closeDB();

                        removeStock_Pay(trans, cur_default[0], "01", fd(trans.getPay()));

                        Db_Master db11a = new Db_Master();
                        boolean es3 = db11a.insert_anagrafica_client_ITA(cl, oldclient);
                        // 10-10 - INSERISCO IN CLIENT TRANSACTION
                        db11a.insert_client_transaction(cl, cod);
                        db11a.closeDB();
                        if (!es3) {
                            ok = "koins1";
                        }
                    } else {
                        ok = "koins2";
                    }
                } else {
                    ok = "koins3";
                }
            }
        }

        if (ok.equals("0")) {
            boolean es = generateInvoice(trans, "ch_transaction_temp");
            if (!es) {
                ok = "kofatt";
            }
        }

        String oldloy = getValue_request(request, "oldloy", false, "0");

        if (ok.equals("0")) {
            Db_Master dbp = new Db_Master();
            dbp.edit_prenotazione_agse(newintbookCodice, agevol_active.toString(), ser_active.toString());
            dbp.closeDB();

            String start = "transaction_ok_mod.jsp";
            if (signoffline) {
                start = "transaction_ok_mod_new.jsp";
            }
            String link = start + "?cod=" + cod + "&booking=" + newintbookCodice;
            if (modified) {
                link = link + "&modified=YES";
            }

            link = link + "&kycpres=" + kycpres;
            if (oldloy.equals("0")) {
                if (is_marketing_OK(cl.getCode())) {
                    oldloy = "1";
                }
            }
            link = link + "&oldloy=" + oldloy;

            insertLink(user, cod, 0, request, true, "Operazioni_test", link);
            redirect(request, response, link);
        } else {
            elimina(user, cod);
            request.getSession().setAttribute("esito_s", ok);
            redirect(request, response, "web_tran_buy.jsp?cod="
                    + newintbookCodice
                    + "&esito=" + ok);
        }

    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void transaction_Ch_SELL_webtrans(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // nuovo 12/3/20
        String oldloy = getValue_request(request, "oldloy", false, "0");

        // nuovo 23/12
        String welc = request.getParameter("welc");
        if (null == welc) {
            welc = "0";
        } else {
            switch (welc) {
                case "on":
                    welc = "1";
                    break;
                default:
                    welc = "0";
                    break;
            }
        }

        String fidel = request.getParameter("fidel");
        if (null == fidel) {
            fidel = "0";
        } else {
            switch (fidel) {
                case "on":
                    fidel = "1";
                    break;
                default:
                    fidel = "0";
                    break;
            }
        }

        //nuovo 11/04
        String kycpres = request.getParameter("kycpres");
        if (null == kycpres) {
            kycpres = "0";
        } else {
            switch (kycpres) {
                case "on":
                    kycpres = "1";
                    break;
                default:
                    kycpres = "0";
                    break;
            }
        }

        //NUOVO MODBOOK
        String modbook = request.getParameter("modbook");
        boolean modified = modbook.equals("YES");

        //NUOVO SERVIZI AGG
        ArrayList<String[]> agevol = agevolazioni_varie();
        StringBuilder agevol_active = new StringBuilder("");
        for (int x = 0; x < agevol.size(); x++) {
            if (request.getParameter("ag_" + agevol.get(x)[0]) != null) {
                if (request.getParameter("ag_" + agevol.get(x)[0]).equals("on")) {
                    agevol_active.append(agevol.get(x)[0]).append(";");
                }
            }
        }

        ArrayList<String[]> ser = servizi_agg();
        StringBuilder ser_active = new StringBuilder("");
//        ArrayList<String> ser_active = new ArrayList<>();
        for (int x = 0; x < ser.size(); x++) {
            if (request.getParameter("se_" + ser.get(x)[0]) != null) {
                if (request.getParameter("se_" + ser.get(x)[0]).equals("on")) {
                    ser_active.append(ser.get(x)[0]).append(";");
                }
            }
        }

        boolean bb = false;
        if (ser_active.toString().contains("BUB")) {
            bb = true;
        }
//         Utility.printRequest(request);
//        if (true) {
//            return;
//        }
        // NUOVO INTBOOK
        String newintbook0 = request.getParameter("newintbook0");
        String newintbookCanale = request.getParameter("newintbookCanale");
        String newintbookCodice = request.getParameter("newintbookCodice");

        if (null == newintbook0) {
            newintbook0 = "0";
            newintbookCanale = "";
            newintbookCodice = "";
        } else {
            switch (newintbook0) {
                case "on":
                    newintbook0 = "1";
                    break;
                default:
                    newintbook0 = "0";
                    newintbookCanale = "";
                    newintbookCodice = "";
                    break;
            }
        }

        String ok = "0";
        String user = (String) request.getSession().getAttribute("us_cod");
        if (user == null) {
            user = "9999";
        }

        Db_Master db = new Db_Master();

        String[] cur_default = db.get_local_currency();
        String dt_tr = db.getNow();

        String cod = request.getParameter("cod");

        //nuovo 19/08
        String notesbigV = request.getParameter("notesbigV");
        if (null == notesbigV) {
            notesbigV = "0";
        } else {
            switch (notesbigV) {
                case "on":
                    notesbigV = "1";
                    break;
                default:
                    notesbigV = "0";
                    break;
            }
        }

        if (notesbigV.equals("1")) {
            db.insert_valueNotesBig(cod, user);
        }
        db.closeDB();

        String typerate = request.getParameter("typerate");

        String unlockCode_final = request.getParameter("unlockCode_final");
        String unlockratesel = request.getParameter("unlockratesel");

        String id_open_till = request.getParameter("id_open_till");
        String till = request.getParameter("till");
        String customerKind = request.getParameter("customerKind");
        String payout1 = request.getParameter("payout1");

        String total0 = request.getParameter("total0");
        String fix0 = request.getParameter("fix0");
        String com0 = request.getParameter("com0");
        String round0 = request.getParameter("round0");
        String commission0 = request.getParameter("commission0");

        String note1 = request.getParameter("note1");

        String agency = request.getParameter("agroy");
        String agency_cod = request.getParameter("heavy_agency");
        String cl_cf = request.getParameter("heavy_codfisc");
        if (cl_cf == null) {
            cl_cf = "---";
        }
        String heavy_surname = request.getParameter("heavy_surname").trim();
        String heavy_name = request.getParameter("heavy_name").trim();
        String heavy_sex = request.getParameter("heavy_sex");
        if (heavy_sex == null || heavy_sex.equals("null")) {
            heavy_sex = "M";
        }
        String heavy_country = request.getParameter("heavy_country");
        String heavy_city = request.getParameter("heavy_city");
        String heavy_city_dis = request.getParameter("heavy_city_dis");

        String heavy_address = request.getParameter("heavy_address");
        if (heavy_address == null || heavy_address.equals("null")) {
            heavy_address = "-";
        }

        String heavy_zipcode = request.getParameter("heavy_zipcode");
        if (heavy_zipcode != null) {
            if (heavy_zipcode.trim().length() > 10) {
                heavy_zipcode = substring(heavy_zipcode.trim(), 0, 10);
            } else {
                heavy_zipcode = heavy_zipcode.trim();
            }
        }

        String heavy_district = request.getParameter("heavy_district");
        String heavy_district_dis = request.getParameter("heavy_district_dis");
        String heavy_pob_city = request.getParameter("heavy_pob_city");
        String heavy_pob_country = request.getParameter("heavy_pob_country").trim();
        String heavy_pob_date = request.getParameter("heavy_pob_date");
        String heavy_identcard = request.getParameter("heavy_identcard");
        String heavy_numberidentcard = request.getParameter("heavy_numberidentcard");
        String heavy_issuedateidentcard = request.getParameter("heavy_issuedateidentcard");
        String heavy_exdateidentcard = request.getParameter("heavy_exdateidentcard");
        String heavy_issuedbyidentcard = request.getParameter("heavy_issuedbyidentcard");
        String heavy_issuedplaceidentcard = request.getParameter("heavy_issuedplaceidentcard");
        String heavy_email = request.getParameter("heavy_email");
        String heavy_phonenu = request.getParameter("heavy_phonenu");
        String heavy_pepI = request.getParameter("heavy_pepI");
        if (heavy_pepI == null || heavy_pepI.trim().equals("") || heavy_pepI.trim().equals("null")) {
            heavy_pepI = "NO";
        }
        String pr_nas1 = request.getParameter("heavy_pob_district");
        String pr_nas2 = request.getParameter("heavy_pob_district_STR");
        if (is_IT && heavy_pob_country.equals(codnaz)) { //ITALIA 
            if (pr_nas1 == null) {
                pr_nas1 = pr_nas2;
                if (pr_nas1 == null) {
                    pr_nas1 = "---";
                }
            }
        } else {
            pr_nas1 = "---";
        }
        String company = request.getParameter("company");
        String oldclient = request.getParameter("oldclient");

        String cod_cliente = generaId(22) + filiale;

        boolean giuridica = false;

        String oldpercent_buy = "-";
        String oldpercent_sell = "-";

        if (customerKind.equals("003")) { //persona giuridica
            giuridica = true;
            cod_cliente = company;
            cl_cf = "---";
        } else if (!oldclient.equals("0")) {
            cod_cliente = oldclient;
            oldclient = "1";
        } else {
            Db_Master db1 = new Db_Master(true);
            if (db1.getC() == null) {
                db1 = new Db_Master();
            }
            String cl_check = db1.get_codice_client(cl_cf, customerKind,
                    heavy_name, heavy_surname, heavy_pob_date, heavy_pob_country);

            if (cl_check != null) {
                cod_cliente = cl_check;
                String[] percent = db1.getClientCommission(cod_cliente);
                oldpercent_buy = percent[0];
                oldpercent_sell = percent[1];
                oldclient = "1";
            }
            db1.closeDB();
        }

        if (agency == null) {
            agency = "0";
            agency_cod = "-";
        } else if (agency.equals("on")) {
            agency = "1";
        }

//        String totalspread = request.getParameter("totalspread");
        String kind_p1 = request.getParameter("kind_p1");

        String ban_1 = request.getParameter("ban_1");
        String posnum = request.getParameter("posnum");
        String cc_number = request.getParameter("cc_number");

        Ch_transaction trans = new Ch_transaction();
        trans.setTill(till);
        trans.setTipocliente(customerKind);
        trans.setId_open_till(id_open_till);
        trans.setPay(formatDoubleforMysql(payout1));
        trans.setTotal(formatDoubleforMysql(total0));
        trans.setFix(formatDoubleforMysql(fix0));
        trans.setCom(formatDoubleforMysql(com0));
        trans.setRound(formatDoubleforMysql(round0));
        trans.setCommission(formatDoubleforMysql(commission0));
        trans.setNote(substring(getStringUTF8(note1), 0, 100));
        trans.setAgency(agency);
        trans.setAgency_cod(agency_cod);
        trans.setCl_cf(cl_cf);
        trans.setDel_fg("0");
        trans.setDel_dt("1901-01-01 00:00:00");
        trans.setDel_user("-");
        trans.setDel_motiv("-");

//      trans.setIntbook("0");
        trans.setIntbook(newintbook0);

        String tfnc0 = "0";

        String intbook_1 = "-";
        String code_intbook_1 = "-";
        String value_intbook_1 = "-";

        String intbook_2 = "-";
        String code_intbook_2 = "-";
        String value_intbook_2 = "-";

        String intbook_3 = "-";
        String code_intbook_3 = "-";
        String value_intbook_3 = "-";

        String macval = "-";
        String cusval = "-";

        trans.setIntbook_type(tfnc0);
        trans.setIntbook_1_mod(formatDoubleforMysql(code_intbook_1));
        trans.setIntbook_1_tf(intbook_1);
        trans.setIntbook_1_val(formatDoubleforMysql(value_intbook_1));
        trans.setIntbook_2_mod(formatDoubleforMysql(code_intbook_2));
        trans.setIntbook_2_tf(intbook_2);
        trans.setIntbook_2_val(formatDoubleforMysql(value_intbook_2));
        trans.setIntbook_3_mod(formatDoubleforMysql(code_intbook_3));
        trans.setIntbook_3_tf(intbook_3);
        trans.setIntbook_3_val(formatDoubleforMysql(value_intbook_3));
        trans.setIntbook_cli(formatDoubleforMysql(cusval));
        trans.setIntbook_mac(formatDoubleforMysql(macval));

        ArrayList<String[]> nc_taxfree = nc_taxfree(trans);

        switch (kind_p1) {
            case "01":
                //            ban_1 = "-";
                posnum = "-";
                cc_number = "-";
                break;
            case "06":
                break;
            case "07":
                cc_number = "-";
                break;
            case "08":
                posnum = ban_1;
                cc_number = "-";
                break;
            default:
                break;
        }
        trans.setLocalfigures(kind_p1);
        trans.setPos(posnum);
        trans.setCredccard_number(cc_number);

        trans.setData(dt_tr);
        trans.setFiliale(filiale);
        trans.setUser(user);
        trans.setCod(cod);

        //CLIENTE
        Client cl = new Client();
        cl.setCode(cod_cliente);
        cl.setCognome(heavy_surname);
        cl.setNome(heavy_name);
        cl.setSesso(heavy_sex);
        cl.setCodfisc(cl_cf);
        cl.setNazione(heavy_country);
        String heavy_city1 = heavy_city;
        if (heavy_city1 == null) {
            heavy_city1 = heavy_city_dis;
        }
        cl.setCitta(heavy_city1);
        cl.setIndirizzo(heavy_address);
        cl.setCap(heavy_zipcode);
        String pro = heavy_district;
        if (pro == null) {
            pro = heavy_district_dis;
            if (pro == null) {
                pro = "-";
            }
        }
        cl.setProvincia(pro);
        cl.setCitta_nascita(heavy_pob_city);
        cl.setProvincia_nascita(pr_nas1);
        cl.setNazione_nascita(heavy_pob_country);
        cl.setDt_nascita(heavy_pob_date);
        cl.setTipo_documento(heavy_identcard);
        cl.setNumero_documento(heavy_numberidentcard);
        cl.setDt_rilascio_documento(heavy_issuedateidentcard);
        cl.setDt_scadenza_documento(heavy_exdateidentcard);
        cl.setRilasciato_da_documento(heavy_issuedbyidentcard);
        cl.setLuogo_rilascio_documento(heavy_issuedplaceidentcard);
        cl.setEmail(heavy_email);
        cl.setTelefono(heavy_phonenu);
        cl.setPerc_buy("-");
        cl.setPerc_sell("-");
        cl.setTimestamp(dt_tr);

        cl.setPep(heavy_pepI);

        if (kind_p1.equals("...")) {
            ok = "kopaym";
        }

        double spread_total = 0.00;

        if (ok.equals("0")) {
            Db_Master db2 = new Db_Master();
            CustomerKind ck = db2.get_customerKind(customerKind);
            db2.closeDB();

            String esitobl = "NO";
            BlacklistM bm = null;
            BlackListsAT bl = null;

            if (!giuridica) {
                bm = verifyBlacklistMac(cl);
                if (is_IT) {
                    bl = verificaBlackList(cod, "MACCORP", filiale, user, cl);
                    esitobl = bl.getEsito();
                } else {
                    trans.setBl_status("2");
                    trans.setBl_motiv("-");
                }
            }

            if (esitobl.equals("1")) {
                ok = "kobl";

                request.getSession().setAttribute("msgbl1", "<br/><b>SURNAME:</b> " + heavy_surname + "<br/><b>NAME:</b> " + heavy_name);

            } else if (bm != null) {
                ok = "koblm&codbl=" + bm.getCode();
            } else {
                if (!giuridica) {
                    if (bl != null) {
                        if (esitobl.equals("-1")) {
                            bl.setEsito("2");
                        }
                        trans.setBl_status(bl.getEsito());
                        trans.setBl_motiv(bl.getError());
                    }
                } else {
                    trans.setBl_status("2");
                    trans.setBl_motiv("-");
                }

                trans.setTipotr("S");
                trans.setCl_cod(cod_cliente);
                trans.setRefund("0");
                trans.setFa_number("-");
                trans.setCn_number("-");
                trans.setHeavy_pepI("NO");

                boolean bbtot = false;
                ArrayList<Ch_transaction_value> list_row = new ArrayList<>();

                double newsellpercent = 0.00;

                for (int k = 1; k < 6; k++) {

                    String totalriga = request.getParameter("total" + k);
                    if (!formatDoubleforMysql(totalriga).equals("0.00")) {

                        String idriga = generaId(25);
                        String codtr = trans.getCod();

                        String numeroriga = valueOf(k);

                        String supportoriga = request.getParameter("kind" + k);
                        String valutariga = request.getParameter("figs" + k);
                        String quantitariga = request.getParameter("quantity" + k);

                        String rateriga = request.getParameter("rate" + k);
                        if (typerate.equals("UNLOCK")) {
                            rateriga = formatDoubleforMysql(request.getParameter("fieldrate" + k));
                        }

                        String roundvalue = request.getParameter("roundvalue" + k);

                        String com_percriga = request.getParameter("comperc" + k);
                        String totpercriga = request.getParameter("totperc" + k);
                        String fx_commriga = request.getParameter("fxcomm" + k);
                        String totcomm1 = request.getParameter("totcomm" + k);
                        String net1 = request.getParameter("net" + k);
                        String spreadriga = getValue_request(request, "spread" + k, false, "KO");
                        if (!spreadriga.equals("KO")) {

                            //SELL
                            List<String> calcolospread = calcolaspread2021(
                                    filiale,
                                    "S",
                                    "",
                                    "",
                                    valutariga,
                                    supportoriga,
                                    formatDoubleforMysql(quantitariga),
                                    rateriga);
                            if (calcolospread.isEmpty()) {
                                spreadriga = formatDoubleforMysql(spreadriga);
                            } else {
                                try {
                                    spreadriga = calcolospread.get(1).equals("0,00") ? "0.00" : formatDoubleforMysql(calcolospread.get(1));
                                } catch (Exception e) {
                                    spreadriga = formatDoubleforMysql(spreadriga);
                                }
                            }

                        }

                        String kind_fix_commriga = request.getParameter("kindfixcomm" + k);
                        if (kind_fix_commriga == null) {
                            kind_fix_commriga = "-";
                        }
                        String low_comm_juriga = request.getParameter("lowcommjus" + k);
                        String bb1 = "N";

                        if (low_comm_juriga == null) {
                            low_comm_juriga = "";
                        }

                        if (bb) {
                            bb1 = "Y";
                            bbtot = true;
                        }

                        String contr_valuta = cur_default[0];
                        String contr_supporto = kind_p1;
                        String contr_quantita = trans.getPay();

                        Engine.get_spread(
                                valutariga, formatDoubleforMysql(quantitariga),
                                rateriga,
                                codtr,
                                "SE", filiale, supportoriga
                        );

                        if (!spreadriga.equals("KO")) {

                            Ch_transaction_value chv = new Ch_transaction_value();
                            chv.setId(idriga);
                            chv.setCod_tr(codtr);
                            chv.setNumeroriga(numeroriga);
                            chv.setSupporto(supportoriga);
                            chv.setPos("-");
                            chv.setPosnum("-");
                            chv.setValuta(valutariga);
                            chv.setQuantita(formatDoubleforMysql(quantitariga));
                            chv.setRate(rateriga);
                            chv.setCom_perc(formatDoubleforMysql(com_percriga));

                            if (k == 1) {
                                newsellpercent = fd(formatDoubleforMysql(com_percriga));
                            } else if (fd(formatDoubleforMysql(com_percriga)) < newsellpercent) {
                                newsellpercent = fd(formatDoubleforMysql(com_percriga));
                            }

                            chv.setCom_perc_tot(formatDoubleforMysql(totpercriga));
                            chv.setFx_com(formatDoubleforMysql(fx_commriga));
                            chv.setTot_com(formatDoubleforMysql(totcomm1));
                            chv.setNet(formatDoubleforMysql(net1));
                            chv.setSpread(spreadriga);
                            chv.setTotal(formatDoubleforMysql(totalriga));
                            chv.setKind_fix_comm(kind_fix_commriga);
                            chv.setLow_com_ju(low_comm_juriga);
                            chv.setBb(bb1);
                            chv.setBb_fidcode("-");
                            chv.setDt_tr(dt_tr);
                            chv.setContr_valuta(contr_valuta);
                            chv.setContr_supporto(contr_supporto);
                            chv.setContr_quantita(contr_quantita);
                            chv.setRoundvalue(roundvalue);
                            chv.setDel_fg("0");
                            chv.setDel_dt("1901-01-01 00:00:00");
                            list_row.add(chv);
                            spread_total = spread_total + fd(spreadriga);
                        }
                    }
                }

                if (newsellpercent > 0) {
                    cl.setPerc_sell(roundDoubleandFormat(newsellpercent, 2));
                } else {
                    cl.setPerc_sell(oldpercent_sell);
                }
                cl.setPerc_buy(oldpercent_buy);

                if (bbtot) {
                    trans.setBb("1");
                } else {
                    trans.setBb("0");
                }

                if (ok.equals("0")) {
                    if (list_row.isEmpty()) {
                        ok = "koins2";
                    }
                }

//                sadas
                if (ok.equals("0")) {
                    Db_Master db3 = new Db_Master();
                    ArrayList<String[]> real = db3.list_oc_change_real(id_open_till);
                    db3.closeDB();
                    //controlli cassa valute figures da dare al cliente

                    for (int k = 0; k < list_row.size(); k++) {
                        Ch_transaction_value chv = list_row.get(k);
                        double start = 0.00;
                        for (int i = 0; i < real.size(); i++) {
                            if (real.get(i)[2].equals(chv.getValuta()) && real.get(i)[1].equals(chv.getSupporto())) {
                                start = fd(real.get(i)[3]);
                                break;
                            }
                        }
                        if (start < fd(chv.getQuantita())) {
                            ok = "koquant";
                            break;
                        }
                    }

                }

                if (ok.equals("0")) { //settimanale
                    double soglia = fd(ck.getIp_max_settimanale());

                    soglia = get_soglia_CZ(soglia);

                    if (fd(trans.getPay()) > soglia) {
                        ok = "komaxsett";
                    }
                    if (ok.equals("0")) {

                        //NUOVO 09/05
                        Db_Master db4A = new Db_Master();
                        double weekA = db4A.weekly_transaction(cod_cliente);
                        db4A.closeDB();
                        Db_Master db4 = new Db_Master(true);
                        if (db4.getC() == null) {
                            db4 = new Db_Master();
                        }
                        double week = db4.weekly_transaction_nofiliale(cod_cliente, trans.getFiliale());
//                        double week = db4.weekly_transaction(cod_cliente);verificasoglianagrafica
                        db4.closeDB();
                        double now = fd(trans.getPay()) + week + weekA;
//                        double now = fd(trans.getPay()) + week;

                        if (now > soglia) {
                            ok = "komaxsett";
                        }
                    }
                }

                trans.setSpread_total(roundDoubleandFormat(spread_total, 2));

                if (ok.equals("0")) {
                    Db_Master db5 = new Db_Master();
                    boolean es1 = db5.insert_transaction_change(trans, "ch_transaction_temp");
                    db5.closeDB();
                    if (es1) {

                        if (newintbook0.equals("1")) {
                            Db_Master dbib = new Db_Master();
                            boolean esib = dbib.insert_InternetBookingCH(cod, newintbookCanale, newintbookCodice, user);
                            dbib.closeDB();
                        }

                        sleeping(1000);
                        Db_Master db6 = new Db_Master();
                        Ch_transaction tmp = db6.query_transaction_ch_temp(cod);
                        boolean es2 = db6.insert_transaction_value(list_row);
                        db6.closeDB();
                        if (es2) {

                            for (int i = 0; i < list_row.size(); i++) {
                                Ch_transaction_value ctv = list_row.get(i);

                                Db_Master db8A = new Db_Master();
                                Stock_report srA = db8A.get_Stock_report(id_open_till, ctv.getValuta(), ctv.getSupporto(), "CH", filiale, till);
                                db8A.closeDB();

                                //DIMINUISCO VALUTE
                                String codsrA = filiale + generaId(47);
                                srA.setCodtr(cod);
                                srA.setCodice(codsrA);
                                double newtotA = -fd(ctv.getQuantita());

                                srA.setTotal(roundDoubleandFormat(newtotA, 2));
                                srA.setSpread("1.0000");
                                srA.setData(dt_tr);
                                srA.setQuantity("0");
                                srA.setUser(user);
                                Db_Master db9A = new Db_Master();
                                db9A.insert_Stockreport(srA);
                                db9A.closeDB();

                                Db_Master db8 = new Db_Master();
                                Real_oc_change righe = new Real_oc_change();
                                righe.setFiliale(filiale);
                                righe.setCod_oc(id_open_till);
                                righe.setValuta(ctv.getValuta());
                                righe.setKind(ctv.getSupporto());
                                righe.setData(dt_tr);
                                righe.setNum_kind_op("0");
                                righe.setValue_op(roundDoubleandFormat((fd(db8.getField_real_oc_change(righe, "value_op", "0.00")) - fd(ctv.getQuantita())), 2));
                                db8.update_real_oc_change(righe);
                                db8.closeDB();

                            }

                            if (kind_p1.equals("01")) {
                                double newtot = fd(trans.getPay());
                                if (!nc_taxfree.isEmpty()) {
//                                    if (fd(formatDoubleforMysql(macval)) != 0) {
                                    newtot = fd(formatDoubleforMysql(macval));
//                                    }
                                }

                                insertStock_Pay(trans, cur_default[0], kind_p1, newtot);

                                Db_Master db7 = new Db_Master();
                                Stock_report sr = db7.get_Stock_report(id_open_till, cur_default[0], kind_p1, "CH", filiale, till);
                                db7.closeDB();

                                //  AUMENTO EURO
                                String codsr = filiale + generaId(47);
                                sr.setCodtr(cod);
                                sr.setCodice(codsr);

                                sr.setTotal(roundDoubleandFormat(newtot, 2));
                                sr.setSpread("1.0000");
                                sr.setData(dt_tr);
                                sr.setQuantity("0");
                                sr.setUser(user);
                                Db_Master db9 = new Db_Master();
                                db9.insert_Stockreport(sr);
                                db9.closeDB();

                                Real_oc_change t1 = new Real_oc_change();
                                t1.setFiliale(filiale);
                                t1.setCod_oc(id_open_till);
                                t1.setValuta(cur_default[0]);
                                t1.setKind(kind_p1);
                                t1.setData(dt_tr);
                                t1.setNum_kind_op("0");
                                Db_Master db9X = new Db_Master();
                                t1.setValue_op(roundDoubleandFormat((fd(db9X.getField_real_oc_change(t1, "value_op", "0.00")) + newtot), 2));
                                db9X.update_real_oc_change(t1);
                                db9X.closeDB();

                            } else {
                                Real_oc_pos t1 = new Real_oc_pos();
                                t1.setFiliale(filiale);
                                t1.setCod_oc(id_open_till);
                                t1.setValuta(cur_default[0]);
                                t1.setKind(kind_p1);
                                t1.setCarta_credito(posnum);
                                t1.setData(dt_tr);
                                Db_Master db10 = new Db_Master();
                                t1.setIp_quantity(valueOf(parseInt(db10.getField_real_oc_pos(t1, "ip_quantity", "0")) + 1));
                                t1.setIp_value(roundDoubleandFormat((fd(db10.getField_real_oc_pos(t1, "ip_value", "0.00")) + fd(trans.getPay())), 2));
                                db10.update_real_oc_pos(t1);
                                db10.closeDB();
                            }
                            if (!giuridica) {
                                Db_Master db11 = new Db_Master();
                                boolean es3 = db11.insert_anagrafica_client_ITA(cl, oldclient);
                                // 10-10 - INSERISCO IN CLIENT TRANSACTION
                                db11.insert_client_transaction(cl, cod);
                                db11.closeDB();
                                if (!es3) {
                                    ok = "koins";
                                }
                            }
                        }
                    }
                }
            }
        }

        if (ok.equals("0")) {
            if (typerate.equals("UNLOCK") || unlockCode_final.equals("UNLOCK")) {
                Db_Master db12 = new Db_Master();
                boolean esa = db12.use_codice_sblocco_NEW(dt_tr, "01", user, cod, unlockratesel);
                db12.closeDB();
                if (!esa) {
                    ok = "koins4";
                }
            }
        }

        if (ok.equals("0")) {
            boolean es = generateInvoice(trans, "ch_transaction_temp");
            if (!es) {
                ok = "kofatt";
            }
        }

        if (ok.equals("0")) {
            String loya = request.getParameter("loya");
            if (loya == null) {
                loya = "";
            }
            loya = loya.trim();
            if (!loya.equals("")) {
                Db_Loy dbl = new Db_Loy();
                if (dbl.getC() != null) {
                    String completo[] = dbl.getCodiceCompleto(loya);
                    if (completo != null) {
                        dbl.ins_mac_associate(cl.getCode(), completo[0]);
                        dbl.update_stato_codici(completo[0], "1");
                        Db_Master db12a = new Db_Master();
                        db12a.insertLoy_TR(cod, cl.getCode(), completo[0], user);
                        db12a.closeDB();

                        //  welcome
                        if (welc.equals("1")) {
                            Ch_transaction tmp = query_transaction_ch_temp(cod);
                            insertTR("W", (String) request.getSession().getAttribute("us_cod"),
                                    "VENDITA AUTOMATICA WELCOME KIT - TRANSAZIONE " + tmp.getId() + " CODICE: " + completo[0]);
                            insertNC_transaction_WK(true, tmp, cl, completo[0]);
                        }

                        if (fidel.equals("1")) {
                            Ch_transaction tmp = query_transaction_ch_temp(cod);
                            insertTR("W", (String) request.getSession().getAttribute("us_cod"),
                                    "VENDITA AUTOMATICA FIDEL. CARD - TRANSAZIONE " + tmp.getId() + " CODICE: " + completo[0]);
                            insertNC_transaction_WK(false, tmp, cl, completo[0]);
                        }

                    }
                    dbl.closeDB();
                }
            }
        }

        if (ok.equals("0")) {
            Db_Master dbp = new Db_Master();
            dbp.edit_prenotazione_agse(newintbookCodice, agevol_active.toString(), ser_active.toString());
            dbp.closeDB();

            String start = "transaction_ok_mod.jsp";
            if (signoffline) {
                start = "transaction_ok_mod_new.jsp";
            }
            String link = start + "?cod=" + cod + "&booking=" + newintbookCodice;
            if (modified) {
                link = link + "&modified=YES";
            }

            link = link + "&kycpres=" + kycpres;
            if (oldloy.equals("0")) {
                if (is_marketing_OK(cl.getCode())) {
                    oldloy = "1";
                }
            }
            link = link + "&oldloy=" + oldloy;

            insertLink(user, cod, 0, request, true, "Operazioni_test", link);
            redirect(request, response, link);
        } else {
            elimina(user, cod);
            request.getSession().setAttribute("esito_s", ok);
            redirect(request, response, "web_tran_sell.jsp?cod="
                    + newintbookCodice
                    + "&esito=" + ok);
        }

    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void transaction_Ch_SELL(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

//        Utility.printRequest(request);
//        if (true) {
//            return;
//        }
        // nuovo 23/12
        String welc = getValue_request(request, "welc", true, "0");
        String fidel = getValue_request(request, "fidel", true, "0");

        // NUOVO INTBOOK
        String newintbook0 = request.getParameter("newintbook0");

        String newintbookCanale = getValue_request(request, "newintbookCanale", false, "");
        String newintbookCodice = getValue_request(request, "newintbookCodice", false, "");

        if (null == newintbook0) {
            newintbook0 = "0";
            newintbookCanale = "";
            newintbookCodice = "";
        } else {
            switch (newintbook0) {
                case "on":
                    newintbook0 = "1";
                    break;
                default:
                    newintbook0 = "0";
                    newintbookCanale = "";
                    newintbookCodice = "";
                    break;
            }
        }

        //nuovo 11/04
        String kycpres = getValue_request(request, "kycpres", true, "0");

        String ok = "0";
        String user = (String) request.getSession().getAttribute("us_cod");
        if (user == null) {
            user = "9999";
        }

        Db_Master db = new Db_Master();
        String[] cur_default = db.get_local_currency();
        String dt_tr = db.getNow();
        db.closeDB();

        String cod = getValue_request(request, "cod", false, "");

        //nuovo 19/08
        String notesbigV = getValue_request(request, "notesbigV", true, "0");

        if (notesbigV.equals("1")) {
            Db_Master db1 = new Db_Master();
            db1.insert_valueNotesBig(cod, user);
            db1.closeDB();
        }

        String typerate = getValue_request(request, "typerate", false, "");
        String unlockCode_final = getValue_request(request, "unlockCode_final", false, "");
        String unlockratesel = getValue_request(request, "unlockratesel", false, "");
        String id_open_till = getValue_request(request, "id_open_till", false, "");
        String till = getValue_request(request, "till", false, "");
        String customerKind = getValue_request(request, "customerKind", false, "");
        String payout1 = getValue_request(request, "payout1", false, "");
        String total0 = getValue_request(request, "total0", false, "");
        String fix0 = getValue_request(request, "fix0", false, "");
        String com0 = getValue_request(request, "com0", false, "");
        String round0 = getValue_request(request, "round0", false, "");
        String commission0 = getValue_request(request, "commission0", false, "");
        String note1 = getValue_request(request, "note1", false, "");
        String agency = getValue_request(request, "agroy", false, "0");
        String agency_cod = getValue_request(request, "heavy_agency", false, "-");
        String cl_cf = getValue_request(request, "heavy_codfisc", false, "---");
        String heavy_surname = getValue_request(request, "heavy_surname", false, "");
        String heavy_name = getValue_request(request, "heavy_name", false, "");

        String heavy_sex = getValue_request(request, "heavy_sex", false, "M");

        if (!is_IT) {
            heavy_sex = getValue_request(request, "heavy_sex", false, "");
        }

        String heavy_country = getValue_request(request, "heavy_country", false, "");
        String heavy_city = getValue_request(request, "heavy_city", false, "");
        String heavy_city_dis = getValue_request(request, "heavy_city_dis", false, "");
        String heavy_address = getValue_request(request, "heavy_address", false, "-");

        String heavy_zipcode = getValue_request(request, "heavy_zipcode", false, "");
        if (heavy_zipcode.length() > 10) {
            heavy_zipcode = substring(heavy_zipcode, 0, 10);
        }

        String heavy_district = getValue_request(request, "heavy_district", false, "");
        String heavy_district_dis = getValue_request(request, "heavy_district_dis", false, "");
        String heavy_pob_city = getValue_request(request, "heavy_pob_city", false, "");
        String heavy_pob_country = getValue_request(request, "heavy_pob_country", false, "");
        String heavy_pob_date = getValue_request(request, "heavy_pob_date", false, "");
        String heavy_identcard = getValue_request(request, "heavy_identcard", false, "");
        String heavy_numberidentcard = getValue_request(request, "heavy_numberidentcard", false, "");
        String heavy_issuedateidentcard = getValue_request(request, "heavy_issuedateidentcard", false, "");
        String heavy_exdateidentcard = getValue_request(request, "heavy_exdateidentcard", false, "");
        String heavy_issuedbyidentcard = getValue_request(request, "heavy_issuedbyidentcard", false, "");
        String heavy_issuedplaceidentcard = getValue_request(request, "heavy_issuedplaceidentcard", false, "");
        String heavy_email = getValue_request(request, "heavy_email", false, "");
        String heavy_phonenu = getValue_request(request, "heavy_phonenu", false, "");
        String heavy_pepI = getValue_request(request, "heavy_pepI", false, "");
        if (heavy_pepI.equals("")) {
            heavy_pepI = "NO";
        }

        String pr_nas1 = getValue_request(request, "heavy_pob_district", false, "---");
        String pr_nas2 = getValue_request(request, "heavy_pob_district_STR", false, "");

        if (is_IT && heavy_pob_country.equals(codnaz)) { //    ITALIA
            if (pr_nas1.equals("")) {
                pr_nas1 = pr_nas2;
            }
        } else {
            pr_nas1 = "---";
        }

        String company = getValue_request(request, "company", false, "");
        String oldclient = getValue_request(request, "oldclient", false, "");
        String cod_cliente = generaId(22) + filiale;
        //CZ
        boolean controllareCliente = true;
        boolean op_sos = getValue_request(request, "op_sos");

        Client_CZ cz = null;
        if (!is_IT) {
            String showanagVALUE = request.getParameter("showanagVALUE");
            String showanagNEWUK = getValue_request(request, "showanagNEWUK", false, "NO");
            String heavy_pno1 = getValue_request(request, "heavy_pno1", false, "");
            String heavy_cz_country = getValue_request(request, "heavy_cz_country", false, "---");
            String heavy_cz_issuingcountry = getValue_request(request, "heavy_cz_issuingcountry", false, "---");
            String heavy_sanctions = getValue_request(request, "heavy_sanctions", false, "");
            String heavy_pep = getValue_request(request, "heavy_pep", false, "");
            String heavy_transactionre = getValue_request(request, "heavy_transactionre", false, "");
            String heavy_moneysource = getValue_request(request, "heavy_moneysource", false, "");
            String heavy_occupation = getValue_request(request, "heavy_occupation", false, "");
            String pep_position = getValue_request(request, "pep_position", false, "");
            String pep_country = getValue_request(request, "pep_country", false, "---");
            if (heavy_pep.equals("NO")) {
                pep_position = "";
                pep_country = "---";
            }

            if (showanagNEWUK.trim().equals("YES")) {
                //PARZIALE
                cz = new Client_CZ(cod_cliente, cod, showanagVALUE, "", heavy_cz_country,
                        heavy_cz_issuingcountry, "", "", "",
                        "", "false", "", "", "");
            } else if (showanagVALUE.trim().equals("none") && !op_sos) {
                controllareCliente = false;
            } else {
                cz = new Client_CZ(cod_cliente, cod, showanagVALUE, heavy_pno1, heavy_cz_country,
                        heavy_cz_issuingcountry, heavy_sanctions, heavy_pep, heavy_transactionre,
                        heavy_moneysource, valueOf(op_sos), heavy_occupation, pep_position, pep_country);
            }
        }
        boolean giuridica = false;
        String oldpercent_buy = "-";
        String oldpercent_sell = "-";

        if (!controllareCliente) {
            cod_cliente = "---";
            cl_cf = "---";
        } else {
            if (is_IT) {
                if (customerKind.equals("003")) { //persona giuridica
                    giuridica = true;
                    cod_cliente = company;
                    cl_cf = "---";
                } else if (!oldclient.equals("0")) {
                    cod_cliente = oldclient;
                    oldclient = "1";
                } else {
                    Db_Master db1 = new Db_Master(true);
                    if (db1.getC() == null) {
                        db1 = new Db_Master();
                    }
                    String cl_check = db1.get_codice_client(cl_cf, customerKind,
                            heavy_name, heavy_surname, heavy_pob_date, heavy_pob_country);
                    if (cl_check != null) {
                        cod_cliente = cl_check;
                        String[] percent = db1.getClientCommission(cod_cliente);
                        oldpercent_buy = percent[0];
                        oldpercent_sell = percent[1];
                        oldclient = "1";
                    }
                    db1.closeDB();
                }
                if (is_IT && heavy_pob_country.equals(codnaz)) { //ITALIA

                } else {
                    pr_nas1 = "---";
                }
            }

        }

//        if (agency == null) {
//            agency = "0";
//            agency_cod = "-";
//        } else if (agency.equals("on")) {
//            agency = "1";
//        }DSA
//        String totalspread = request.getParameter("totalspread");
        String kind_p1 = getValue_request(request, "kind_p1", false, "");
        String ban_1 = getValue_request(request, "ban_1", false, "");
        String posnum = getValue_request(request, "posnum", false, "");
        String cc_number = getValue_request(request, "cc_number", false, "");

        Ch_transaction trans = new Ch_transaction();
        trans.setTill(till);
        trans.setTipocliente(customerKind);
        trans.setId_open_till(id_open_till);
        trans.setPay(formatDoubleforMysql(payout1));
        trans.setTotal(formatDoubleforMysql(total0));
        trans.setFix(formatDoubleforMysql(fix0));
        trans.setCom(formatDoubleforMysql(com0));
        trans.setRound(formatDoubleforMysql(round0));
        trans.setCommission(formatDoubleforMysql(commission0));
        trans.setNote(substring(getStringUTF8(note1), 0, 100));
        trans.setAgency(agency);
        trans.setAgency_cod(agency_cod);
        trans.setCl_cf(cl_cf);
        trans.setDel_fg("0");
        trans.setDel_dt("1901-01-01 00:00:00");
        trans.setDel_user("-");
        trans.setDel_motiv("-");
        trans.setIntbook(newintbook0);
        String tfnc0 = getValue_request(request, "tfnc0", true, "0");

        String intbook_1 = getValue_request(request, "intbook_1", false, "...");
        String code_intbook_1 = getValue_request(request, "code_intbook_1", false, "-");
        String value_intbook_1 = getValue_request(request, "value_intbook_1", false, "-");
        if (intbook_1.equals("...")) {
            intbook_1 = "-";
            code_intbook_1 = "-";
            value_intbook_1 = "-";
        }

        String intbook_2 = getValue_request(request, "intbook_2", false, "...");
        String code_intbook_2 = getValue_request(request, "code_intbook_2", false, "-");
        String value_intbook_2 = getValue_request(request, "value_intbook_2", false, "-");
        if (intbook_2.equals("...")) {
            intbook_2 = "-";
            code_intbook_2 = "-";
            value_intbook_2 = "-";
        }

        String intbook_3 = getValue_request(request, "intbook_3", false, "...");
        String code_intbook_3 = getValue_request(request, "code_intbook_3", false, "-");
        String value_intbook_3 = getValue_request(request, "value_intbook_3", false, "-");
        if (intbook_3.equals("...")) {
            intbook_3 = "-";
            code_intbook_3 = "-";
            value_intbook_3 = "-";
        }

//        String intbook_4 = request.getParameter("intbook_4");
//        String code_intbook_4 = request.getParameter("code_intbook_4");
//        String value_intbook_4 = request.getParameter("value_intbook_4");
//
//        if (intbook_4 == null || intbook_4.equals("...")) {
//            intbook_4 = "-";
//            code_intbook_4 = "-";
//            value_intbook_4 = "-";
//        }
//
//        String intbook_5 = request.getParameter("intbook_5");
//        String code_intbook_5 = request.getParameter("code_intbook_5");
//        String value_intbook_5 = request.getParameter("value_intbook_5");
//
//        if (intbook_5 == null || intbook_5.equals("...")) {
//            intbook_5 = "-";
//            code_intbook_5 = "-";
//            value_intbook_5 = "-";
//        }
        String macval = getValue_request(request, "macval", false, "");
        String cusval = getValue_request(request, "cusval", false, "");

        if (tfnc0.equals("0")) {
            intbook_1 = "-";
            code_intbook_1 = "-";
            value_intbook_1 = "-";
            intbook_2 = "-";
            code_intbook_2 = "-";
            value_intbook_2 = "-";
            intbook_3 = "-";
            code_intbook_3 = "-";
            value_intbook_3 = "-";
//            intbook_4 = "-";
//            code_intbook_4 = "-";
//            value_intbook_4 = "-";
//            intbook_5 = "-";
//            code_intbook_5 = "-";
//            value_intbook_5 = "-";
            macval = "-";
            cusval = "-";
        }

        trans.setIntbook_type(tfnc0);
        trans.setIntbook_1_mod(formatDoubleforMysql(code_intbook_1));
        trans.setIntbook_1_tf(intbook_1);
        trans.setIntbook_1_val(formatDoubleforMysql(value_intbook_1));
        trans.setIntbook_2_mod(formatDoubleforMysql(code_intbook_2));
        trans.setIntbook_2_tf(intbook_2);
        trans.setIntbook_2_val(formatDoubleforMysql(value_intbook_2));
        trans.setIntbook_3_mod(formatDoubleforMysql(code_intbook_3));
        trans.setIntbook_3_tf(intbook_3);
        trans.setIntbook_3_val(formatDoubleforMysql(value_intbook_3));
        trans.setIntbook_cli(formatDoubleforMysql(cusval));
        trans.setIntbook_mac(formatDoubleforMysql(macval));

        ArrayList<String[]> nc_taxfree = nc_taxfree(trans);

        boolean bbtot = false;
        boolean sbtot = false;

        switch (kind_p1) {
            case "01":
                //            ban_1 = "-";
                posnum = "-";
                cc_number = "-";
                break;
            case "06":
                break;
            case "07":
                cc_number = "-";
                break;
            case "08":
                posnum = ban_1;
                cc_number = "-";
                break;
            default:
                break;
        }
        trans.setLocalfigures(kind_p1);
        trans.setPos(posnum);
        trans.setCredccard_number(cc_number);

        trans.setData(dt_tr);
        trans.setFiliale(filiale);
        trans.setUser(user);
        trans.setCod(cod);

        //CLIENTE
        Client cl = new Client();
        cl.setCode(cod_cliente);
        cl.setCognome(heavy_surname);
        cl.setNome(heavy_name);
        cl.setSesso(heavy_sex);
        cl.setCodfisc(cl_cf);
        cl.setNazione(heavy_country);
        String heavy_city1 = heavy_city;
        if (heavy_city1.equals("")) {
            heavy_city1 = heavy_city_dis;
        }
        cl.setCitta(heavy_city1);
        cl.setIndirizzo(heavy_address);
        cl.setCap(heavy_zipcode);
        String pro = heavy_district;
        if (pro.equals("")) {
            pro = heavy_district_dis;
            if (pro.equals("")) {
                pro = "-";
            }
        }
        cl.setProvincia(pro);
        cl.setCitta_nascita(heavy_pob_city);
        cl.setProvincia_nascita(pr_nas1);
        cl.setNazione_nascita(heavy_pob_country);
        cl.setDt_nascita(heavy_pob_date);
        cl.setTipo_documento(heavy_identcard);
        cl.setNumero_documento(heavy_numberidentcard);
        cl.setDt_rilascio_documento(heavy_issuedateidentcard);
        cl.setDt_scadenza_documento(heavy_exdateidentcard);
        cl.setRilasciato_da_documento(heavy_issuedbyidentcard);
        cl.setLuogo_rilascio_documento(heavy_issuedplaceidentcard);
        cl.setEmail(heavy_email);
        cl.setTelefono(heavy_phonenu);
        cl.setPerc_buy("-");
        cl.setPerc_sell("-");
        cl.setTimestamp(dt_tr);
        cl.setPep(heavy_pepI);

        if (kind_p1.equals("...")) {
            ok = "kopaym";
        }

        double spread_total = 0.00;

        if (ok.equals("0")) {
            Db_Master db2 = new Db_Master();
            CustomerKind ck = db2.get_customerKind(customerKind);
            db2.closeDB();

            String esitobl = "NO";
            BlacklistM bm = null;
            BlackListsAT bl = null;
            if (!giuridica) {
                bm = verifyBlacklistMac(cl);
                if (is_IT) {
                    bl = verificaBlackList(cod, "MACCORP", filiale, user, cl);
                    esitobl = bl.getEsito();
                } else {
                    trans.setBl_status("2");
                    trans.setBl_motiv("-");
                }
            }

            if (esitobl.equals("1")) {
                ok = "kobl";
                request.getSession().setAttribute("msgbl1", "<br/><b>SURNAME:</b> " + heavy_surname + "<br/><b>NAME:</b> " + heavy_name);
            } else if (bm != null) {
                ok = "koblm&codbl=" + bm.getCode();
            } else {

                if (!giuridica) {
                    if (bl != null) {
                        if (esitobl.equals("-1")) {
                            bl.setEsito("2");
                        }
                        trans.setBl_status(bl.getEsito());
                        trans.setBl_motiv(bl.getError());
                    }
                } else {
                    trans.setBl_status("2");
                    trans.setBl_motiv("-");
                }

                trans.setTipotr("S");
                trans.setCl_cod(cod_cliente);
                trans.setRefund("0");
                trans.setFa_number("-");
                trans.setCn_number("-");
                trans.setHeavy_pepI("NO");

                ArrayList<Ch_transaction_value> list_row = new ArrayList<>();

                double newsellpercent = 0.00;

                for (int k = 1; k < 6; k++) {

                    String totalriga = getValue_request(request, "total" + k, false, "");
                    if (!formatDoubleforMysql(totalriga).equals("0.00")) {
                        String idriga = generaId(25);
                        String codtr = trans.getCod();

                        String numeroriga = valueOf(k);

                        String supportoriga = getValue_request(request, "kind" + k, false, "");
                        String valutariga = getValue_request(request, "figs" + k, false, "");
                        String quantitariga = getValue_request(request, "quantity" + k, false, "");
                        String rateriga = getValue_request(request, "rate" + k, false, "");
                        if (typerate.equals("UNLOCK")) {
                            rateriga = formatDoubleforMysql(getValue_request(request, "fieldrate" + k, false, ""));
                        }
                        rateriga = removeLast(rateriga);

                        String roundvalue = getValue_request(request, "roundvalue" + k, false, "");
                        String com_percriga = getValue_request(request, "comperc" + k, false, "");
                        String totpercriga = getValue_request(request, "totperc" + k, false, "");
                        String fx_commriga = getValue_request(request, "fxcomm" + k, false, "");
                        String totcomm1 = getValue_request(request, "totcomm" + k, false, "");
                        String net1 = getValue_request(request, "net" + k, false, "");
                        String spreadriga = getValue_request(request, "spread" + k, false, "KO");
                        if (!spreadriga.equals("KO")) {
                            List<String> calcolospread = calcolaspread2021(
                                    filiale,
                                    "S",
                                    "",
                                    "",
                                    valutariga,
                                    supportoriga,
                                    formatDoubleforMysql(quantitariga),
                                    rateriga);
                            if (calcolospread.isEmpty()) {
                                spreadriga = formatDoubleforMysql(spreadriga);
                            } else {
                                try {
                                    spreadriga = calcolospread.get(1).equals("0,00") ? "0.00" : formatDoubleforMysql(calcolospread.get(1));
                                } catch (Exception e) {
                                    spreadriga = formatDoubleforMysql(spreadriga);
                                }
                            }
                        }

                        String kind_fix_commriga = getValue_request(request, "kindfixcomm" + k, false, "-");
                        String low_comm_juriga = getValue_request(request, "lowcommjus" + k, false, "");

                        String sb_bb = "N";
                        String bbriga = getValue_request(request, "bb" + k, false, "N");
                        String sbriga = getValue_request(request, "sb" + k, false, "N");
                        String sb_fidcoderiga = getValue_request(request, "fidcode" + k, false, "-");

                        if (!bbriga.equals("N")) {
                            bbtot = true;
                            sb_fidcoderiga = "-";
                            sb_bb = bbriga;
                        } else {
                            if (!sbriga.equals("N")) {
                                sbtot = true;
                                sb_bb = sbriga;
                            }
                        }

                        String contr_valuta = cur_default[0];
                        String contr_supporto = kind_p1;
                        String contr_quantita = trans.getPay();

                        //SELL
                        Engine.get_spread(
                                valutariga, formatDoubleforMysql(quantitariga),
                                rateriga,
                                codtr,
                                "SE", filiale, supportoriga
                        );

//                        if (filiale.startsWith("17")) {
//                            insertTR_R(user, "TRANSAZIONE SELL CODICE: " + cod + " SPREADRIGA " + spreadriga);
//                        }
                        if (!spreadriga.equals("KO")) {
                            Ch_transaction_value chv = new Ch_transaction_value();
                            chv.setId(idriga);
                            chv.setCod_tr(codtr);
                            chv.setNumeroriga(numeroriga);
                            chv.setSupporto(supportoriga);
                            chv.setPos("-");
                            chv.setPosnum("-");
                            chv.setValuta(valutariga);
                            chv.setQuantita(formatDoubleforMysql(quantitariga));
                            chv.setRate(rateriga);
                            chv.setCom_perc(formatDoubleforMysql(com_percriga));
                            if (k == 1) {
                                newsellpercent = fd(formatDoubleforMysql(com_percriga));
                            } else if (fd(formatDoubleforMysql(com_percriga)) < newsellpercent) {
                                newsellpercent = fd(formatDoubleforMysql(com_percriga));
                            }
                            chv.setCom_perc_tot(formatDoubleforMysql(totpercriga));
                            chv.setFx_com(formatDoubleforMysql(fx_commriga));
                            chv.setTot_com(formatDoubleforMysql(totcomm1));
                            chv.setNet(removeLast(formatDoubleforMysql(net1)));
                            chv.setSpread(spreadriga);
                            chv.setTotal(formatDoubleforMysql(totalriga));
                            chv.setKind_fix_comm(kind_fix_commriga);
                            chv.setLow_com_ju(low_comm_juriga);

                            chv.setBb(sb_bb);
                            chv.setBb_fidcode(sb_fidcoderiga);
                            chv.setDt_tr(dt_tr);
                            chv.setContr_valuta(contr_valuta);
                            chv.setContr_supporto(contr_supporto);
                            chv.setContr_quantita(contr_quantita);
                            chv.setRoundvalue(roundvalue);
                            chv.setDel_fg("0");
                            chv.setDel_dt("1901-01-01 00:00:00");
                            list_row.add(chv);
                            spread_total = spread_total + fd(spreadriga);
                        }
                    }
                }

                if (newsellpercent > 0) {
                    cl.setPerc_sell(roundDoubleandFormat(newsellpercent, 2));
                } else {
                    cl.setPerc_sell(oldpercent_sell);
                }
                cl.setPerc_buy(oldpercent_buy);

                if (ok.equals("0")) {
                    if (list_row.isEmpty()) {
                        ok = "koins2";
                    }
                }

                if (ok.equals("0")) {
                    Db_Master db3 = new Db_Master();
                    ArrayList<String[]> real = db3.list_oc_change_real(id_open_till);
                    db3.closeDB();
                    //controlli cassa valute figures da dare al cliente

                    for (int k = 0; k < list_row.size(); k++) {
                        Ch_transaction_value chv = list_row.get(k);
                        double start = 0.00;
                        for (int i = 0; i < real.size(); i++) {
                            if (real.get(i)[2].equals(chv.getValuta()) && real.get(i)[1].equals(chv.getSupporto())) {
                                start = fd(real.get(i)[3]);
                                break;
                            }
                        }
                        if (start < fd(chv.getQuantita())) {
                            ok = "koquant";
                            break;
                        }
                    }

                }

                if (ok.equals("0")) { //settimanale
                    double soglia = fd(ck.getIp_max_settimanale());
                    soglia = get_soglia_CZ(soglia);
                    if (fd(trans.getPay()) > soglia) {
                        ok = "komaxsett";
                    }
                    if (ok.equals("0")) {
                        //NUOVO 09/05
                        Db_Master db4A = new Db_Master();
                        double weekA = db4A.weekly_transaction(cod_cliente);
                        db4A.closeDB();
                        Db_Master db4 = new Db_Master(true);
                        if (db4.getC() == null) {
                            db4 = new Db_Master();
                        }
                        double week = db4.weekly_transaction_nofiliale(cod_cliente, trans.getFiliale());
//                        double week = db4.weekly_transaction(cod_cliente);
                        db4.closeDB();
                        double now = fd(trans.getPay()) + week + weekA;
//                        double now = fd(trans.getPay()) + week;

                        if (now > soglia) {
                            ok = "komaxsett";
                        }

                    }
                }

                trans.setSpread_total(roundDoubleandFormat(spread_total, 2));

                trans.setBb(getStatus_BBSB(bbtot, sbtot));

                if (ok.equals("0")) {
                    Db_Master db5 = new Db_Master();
                    boolean es1 = db5.insert_transaction_change(trans, "ch_transaction_temp");
                    db5.closeDB();
                    if (es1) {

                        if (newintbook0.equals("1")) {
                            Db_Master dbib = new Db_Master();
                            boolean esib = dbib.insert_InternetBookingCH(cod, newintbookCanale, newintbookCodice, user);
                            dbib.closeDB();
                        }

                        sleeping(1000);
                        Db_Master db6 = new Db_Master();
                        Ch_transaction tmp = db6.query_transaction_ch_temp(cod);
                        boolean es2 = db6.insert_transaction_value(list_row);
                        db6.closeDB();
                        if (es2) {

                            for (int i = 0; i < list_row.size(); i++) {
                                Ch_transaction_value ctv = list_row.get(i);

                                Db_Master db8A = new Db_Master();
                                Stock_report srA = db8A.get_Stock_report(id_open_till, ctv.getValuta(), ctv.getSupporto(), "CH", filiale, till);
                                db8A.closeDB();

                                //  DIMINUISCO VALUTE
                                String codsrA = filiale + generaId(47);
                                srA.setCodtr(cod);
                                srA.setCodice(codsrA);
                                double newtotA = -fd(ctv.getQuantita());

                                srA.setTotal(roundDoubleandFormat(newtotA, 2));
                                srA.setSpread("1.0000");
                                srA.setData(dt_tr);
                                srA.setQuantity("0");
                                srA.setUser(user);

                                Db_Master db9A = new Db_Master();
                                db9A.insert_Stockreport(srA);
                                db9A.closeDB();

                                Real_oc_change righe = new Real_oc_change();
                                righe.setFiliale(filiale);
                                righe.setCod_oc(id_open_till);
                                righe.setValuta(ctv.getValuta());
                                righe.setKind(ctv.getSupporto());
                                righe.setData(dt_tr);
                                righe.setNum_kind_op("0");
                                Db_Master db8 = new Db_Master();
                                righe.setValue_op(roundDoubleandFormat((fd(db8.getField_real_oc_change(righe, "value_op", "0.00")) - fd(ctv.getQuantita())), 2));
                                db8.update_real_oc_change(righe);
                                db8.closeDB();

                            }
                            if (kind_p1.equals("01")) {
                                double newtot = fd(trans.getPay());
                                if (!nc_taxfree.isEmpty()) {
//                                    if (fd(formatDoubleforMysql(macval)) != 0) {
                                    newtot = fd(formatDoubleforMysql(macval));
//                                    }
                                }

                                insertStock_Pay(trans, cur_default[0], kind_p1, newtot);

                                Db_Master db7 = new Db_Master();
                                Stock_report sr = db7.get_Stock_report(id_open_till, cur_default[0], kind_p1, "CH", filiale, till);
                                db7.closeDB();

                                //  AUMENTO EURO
                                String codsr = filiale + generaId(47);
                                sr.setCodtr(cod);
                                sr.setCodice(codsr);

                                sr.setTotal(roundDoubleandFormat(newtot, 2));
                                sr.setSpread("1.0000");
                                sr.setData(dt_tr);
                                sr.setQuantity("0");
                                sr.setUser(user);
                                Db_Master db9 = new Db_Master();
                                db9.insert_Stockreport(sr);
                                db9.closeDB();

                                Real_oc_change t1 = new Real_oc_change();
                                t1.setFiliale(filiale);
                                t1.setCod_oc(id_open_till);
                                t1.setValuta(cur_default[0]);
                                t1.setKind(kind_p1);
                                t1.setData(dt_tr);
                                t1.setNum_kind_op("0");
                                Db_Master db9X = new Db_Master();
                                t1.setValue_op(roundDoubleandFormat((fd(db9X.getField_real_oc_change(t1, "value_op", "0.00")) + newtot), 2));
                                db9X.update_real_oc_change(t1);
                                db9X.closeDB();

                            } else {
                                Real_oc_pos t1 = new Real_oc_pos();
                                t1.setFiliale(filiale);
                                t1.setCod_oc(id_open_till);
                                t1.setValuta(cur_default[0]);
                                t1.setKind(kind_p1);
                                t1.setCarta_credito(posnum);
                                t1.setData(dt_tr);
                                Db_Master db10 = new Db_Master();
                                t1.setIp_quantity(valueOf(parseInt(db10.getField_real_oc_pos(t1, "ip_quantity", "0")) + 1));
                                t1.setIp_value(roundDoubleandFormat((fd(db10.getField_real_oc_pos(t1, "ip_value", "0.00")) + fd(trans.getPay())), 2));
                                db10.update_real_oc_pos(t1);
                                db10.closeDB();
                            }
                            if (!giuridica) {
                                if (is_IT) {

                                    Db_Master db11a = new Db_Master();
                                    boolean es3 = db11a.insert_anagrafica_client_ITA(cl, oldclient);
                                    // 10-10 - INSERISCO IN CLIENT TRANSACTION
                                    db11a.insert_client_transaction(cl, cod);
                                    db11a.closeDB();
                                    if (!es3) {
                                        ok = "koins1";
                                    }
                                } else {
                                    if (controllareCliente) {
                                        if (cz != null) {
                                            cl.setRepceca(cz);
                                            Db_Master db11a = new Db_Master();
                                            // INSERISCO IN CLIENT TRANSACTION
                                            boolean ins_1 = true;
                                            if (is_CZ) {
                                                ins_1 = db11a.insert_client_transaction(cz, "client_cz");
                                            } else if (is_UK) {
                                                ins_1 = db11a.insert_client_transaction(cz, "client_uk");
                                            }
                                            // INSERISCO IN CLIENT TRANSACTION
                                            boolean ins_2 = db11a.insert_client_transaction(cl, cod);
                                            db11a.closeDB();
                                            if (!ins_1 || !ins_2) {
                                                ok = "koins1";
                                            }
                                        }
                                    }
                                }
                            }

                            if (ok.equals("0")) {
                                if (!nc_taxfree.isEmpty()) {
                                    Db_Master db12 = new Db_Master();
                                    ArrayList<NC_category> list_nc1 = db12.list_nc_category_enabled();
                                    ArrayList<NC_causal> list_nc2 = db12.list_nc_causal_enabled();
                                    db12.closeDB();
                                    for (int n = 0; n < nc_taxfree.size(); n++) {
                                        String[] val_nc = nc_taxfree.get(n);

                                        String cod_nc = "NC" + generaId(23);
                                        NC_causal nc2 = getNC_causal(list_nc2, val_nc[0], null);
                                        NC_category nc1 = getNC_category(list_nc1, nc2.getGruppo_nc());
                                        NC_transaction nct = new NC_transaction();
                                        nct.setCod(cod_nc);
                                        nct.setFiliale(filiale);
                                        nct.setUser(user);
                                        nct.setId_open_till(id_open_till);
                                        nct.setTill(till);
                                        nct.setNote("Tax Free No-Change Transaction: " + filiale + tmp.getId());
                                        nct.setGruppo_nc(nc1.getGruppo_nc());
                                        nct.setCausale_nc(nc2.getCausale_nc());
                                        nct.setPrezzo(nc2.getIp_prezzo_nc());
                                        nct.setFg_tipo_transazione_nc(nc2.getFg_tipo_transazione_nc());
                                        nct.setSupporto("01");
                                        nct.setValuta(cur_default[0]);

                                        nct.setPos("");
                                        nct.setPosnum("");

                                        nct.setQuantita((val_nc[2]));
                                        nct.setTotal(("-" + (val_nc[2])).trim());
                                        nct.setRicevuta(roundDoubleandFormat(fd(val_nc[1]), 0));

                                        nct.setFg_dogana("01"); //NO CUSTOMS
                                        nct.setCl_cognome(cl.getCognome());
                                        nct.setCl_nome(cl.getNome());
                                        nct.setCl_indirizzo(cl.getIndirizzo());
                                        nct.setCl_citta(cl.getCitta()); //FORMATTARE
                                        nct.setCl_nazione(cl.getNazione());//FORMATTARE
                                        nct.setCl_email(cl.getEmail());
                                        nct.setCl_telefono(cl.getTelefono());

                                        Db_Master db13 = new Db_Master();
                                        nct.setFg_inout(db13.getFg_inout_ncde(nct.getFg_tipo_transazione_nc(), nc2.getNc_de()));
                                        nct.setDel_dt("1901-01-01 00:00:00");
                                        nct.setDel_user("-");
                                        nct.setDel_motiv("-");
                                        nct.setDel_fg("0");
                                        nct.setBonus(nc2.getBonus());
                                        nct.setCh_transaction(tmp.getCod());
                                        db13.insert_NC_transaction(nct);
                                        db13.closeDB();
                                        sleeping(1000);
                                    }
                                }
                            }

                        }
                    }
                }

                //blocca fidelity cod
                if (ok.equals("0")) {
                    if (sbtot) {
                        for (int i = 0; i < list_row.size(); i++) {
                            boolean sbrow = list_row.get(i).getBb().equals("Y") || list_row.get(i).getBb().equals("F");
                            if (sbrow) {
                                String fidcode = list_row.get(i).getBb_fidcode();
                                if (fidcode.length() == 18) {
                                    String id_fil = fidcode.substring(0, 3);
                                    String id_tra = fidcode.substring(3);
                                    Db_Master db14 = new Db_Master();
                                    db14.update_status_bb_transaction(id_fil, id_tra, "4", user);
                                    db14.closeDB();
                                }
                            }
                        }
                    }
                }
            }
        }

        if (ok.equals("0")) {
            if (typerate.equals("UNLOCK") || unlockCode_final.equals("UNLOCK")) {
                Db_Master db12 = new Db_Master();
                boolean esa = db12.use_codice_sblocco_NEW(dt_tr, "01", user, cod, unlockratesel);
                db12.closeDB();
                if (!esa) {
                    ok = "koins4";
                }
            }
        }

        if (ok.equals("0")) {
            boolean es = generateInvoice(trans, "ch_transaction_temp");
            if (!es) {
                ok = "kofatt";
            }
        }

        //nuovo 07/05
        if (ok.equals("0")) {
            String loya = getValue_request(request, "loya", false, "");
            if (!loya.equals("")) {
                Db_Loy dbl = new Db_Loy();
                if (dbl.getC() != null) {
                    String completo[] = dbl.getCodiceCompleto(loya);
                    if (completo != null) {
                        dbl.ins_mac_associate(cl.getCode(), completo[0]);
                        dbl.update_stato_codici(completo[0], "1");

                        Db_Master db12a = new Db_Master();
                        db12a.insertLoy_TR(cod, cl.getCode(), completo[0], user);
                        db12a.closeDB();

                        //  welcome
                        if (welc.equals("1")) {
                            Ch_transaction tmp = query_transaction_ch_temp(cod);
                            insertTR("W", (String) request.getSession().getAttribute("us_cod"),
                                    "VENDITA AUTOMATICA WELCOME KIT - TRANSAZIONE " + tmp.getId() + " CODICE: " + completo[0]);
                            insertNC_transaction_WK(true, tmp, cl, completo[0]);
                        }

                        if (fidel.equals("1")) {
                            Ch_transaction tmp = query_transaction_ch_temp(cod);
                            insertTR("W", (String) request.getSession().getAttribute("us_cod"),
                                    "VENDITA AUTOMATICA FIDEL. CARD - TRANSAZIONE " + tmp.getId() + " CODICE: " + completo[0]);
                            insertNC_transaction_WK(false, tmp, cl, completo[0]);
                        }

                    }
                    dbl.closeDB();
                }
            }
        }

        if (ok.equals("0")) {
            String start = "transaction_ok_mod.jsp";
            if (signoffline) {
                start = "transaction_ok_mod_new.jsp";
            }
            String link = start + "?cod=" + cod + "&kycpres=" + kycpres;
            // nuovo 12/3/20
            String oldloy = getValue_request(request, "oldloy", false, "0");
            if (oldloy.equals("0")) {
                if (is_marketing_OK(cl.getCode())) {
                    oldloy = "1";
                }
            }
            link = link + "&oldloy=" + oldloy;

            insertLink(user, cod, 0, request, true, "Operazioni_test", link);
            redirect(request, response, link);
        } else {
            if (filiale.startsWith("17")) {
                insertTR_R(user, "TRANSAZIONE SELL CODICE: " + cod + " METODO ELIMINA");
            }
            elimina(user, cod);
            request.getSession().setAttribute("esito_s", ok);
            redirect(request, response, "transaction_s.jsp?esito=" + ok);
        }

    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void transaction_Ch_BUY(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // nuovo 23/12
        String welc = getValue_request(request, "welc", true, "0");
        String fidel = getValue_request(request, "fidel", true, "0");

        String ok = "0";
        String user = (String) request.getSession().getAttribute("us_cod");
        if (user == null) {
            user = "9999";
        }
        Db_Master db = new Db_Master();
        String[] cur_default = db.get_local_currency();
        String dt_tr = db.getNow();
        db.closeDB();

        String cod = request.getParameter("cod");

        // NUOVO INTBOOK
        String newintbook0 = request.getParameter("newintbook0");

        String newintbookCanale = getValue_request(request, "newintbookCanale", false, "");
        String newintbookCodice = getValue_request(request, "newintbookCodice", false, "");

        if (null == newintbook0) {
            newintbook0 = "0";
            newintbookCanale = "";
            newintbookCodice = "";
        } else {
            switch (newintbook0) {
                case "on":
                    newintbook0 = "1";
                    break;
                default:
                    newintbook0 = "0";
                    newintbookCanale = "";
                    newintbookCodice = "";
                    break;
            }
        }

        //nuovo 11/04
        String kycpres = getValue_request(request, "kycpres", true, "0");

        String company = getValue_request(request, "company", false, "");
        String typerate = getValue_request(request, "typerate", false, "");
        String unlockCode_final = getValue_request(request, "unlockCode_final", false, "");
        String unlockratesel = getValue_request(request, "unlockratesel", false, "");

        String oldclient = getValue_request(request, "oldclient", false, "");
        String tipocliente = getValue_request(request, "tipocliente", false, "");
        String id_open_till = getValue_request(request, "id_open_till", false, "");

        String till = getValue_request(request, "till", false, "");
        String pay = getValue_request(request, "payout1", false, "");
        String total = getValue_request(request, "total0", false, "");
        String fix = getValue_request(request, "fix0", false, "");
        String com = getValue_request(request, "com0", false, "");
        String round = getValue_request(request, "round0", false, "");
        String commission = getValue_request(request, "commission0", false, "");
        String note = getValue_request(request, "note1", false, "");
        String agency = getValue_request(request, "agroy", false, "0");
        String agency_cod = getValue_request(request, "heavy_agency", false, "-");
        String cl_cf = getValue_request(request, "heavy_codfisc", false, "---");

        String heavy_surname = getValue_request(request, "heavy_surname", false, "");
        String heavy_name = getValue_request(request, "heavy_name", false, "");
        String heavy_sex = getValue_request(request, "heavy_sex", false, "M");
        if (!is_IT) {
            heavy_sex = getValue_request(request, "heavy_sex", false, "");
        }

        String heavy_country = getValue_request(request, "heavy_country", false, "");
        String heavy_city = getValue_request(request, "heavy_city", false, "");
        String heavy_city_dis = getValue_request(request, "heavy_city_dis", false, "");
        String heavy_address = getValue_request(request, "heavy_address", false, "-");

        String heavy_zipcode = getValue_request(request, "heavy_zipcode", false, "");
        if (heavy_zipcode.length() > 10) {
            heavy_zipcode = substring(heavy_zipcode, 0, 10);
        }

        String heavy_district = getValue_request(request, "heavy_district", false, "");
        String heavy_district_dis = getValue_request(request, "heavy_district_dis", false, "");
        String heavy_pob_city = getValue_request(request, "heavy_pob_city", false, "");
        String heavy_pob_country = getValue_request(request, "heavy_pob_country", false, "");
        String heavy_pob_date = getValue_request(request, "heavy_pob_date", false, "");
        String heavy_identcard = getValue_request(request, "heavy_identcard", false, "");
        String heavy_numberidentcard = getValue_request(request, "heavy_numberidentcard", false, "");
        String heavy_issuedateidentcard = getValue_request(request, "heavy_issuedateidentcard", false, "");
        String heavy_exdateidentcard = getValue_request(request, "heavy_exdateidentcard", false, "");
        String heavy_issuedbyidentcard = getValue_request(request, "heavy_issuedbyidentcard", false, "");
        String heavy_issuedplaceidentcard = getValue_request(request, "heavy_issuedplaceidentcard", false, "");
        String heavy_email = getValue_request(request, "heavy_email", false, "");
        String heavy_phonenu = getValue_request(request, "heavy_phonenu", false, "");
        String heavy_pepI = getValue_request(request, "heavy_pepI", false, "");
        if (heavy_pepI.equals("")) {
            heavy_pepI = "NO";
        }
        String pr_nas1 = getValue_request(request, "heavy_pob_district", false, "---");
        String pr_nas2 = getValue_request(request, "heavy_pob_district_STR", false, "");
        if (is_IT && heavy_pob_country.equals(codnaz)) { //    ITALIA
            if (pr_nas1.equals("")) {
                pr_nas1 = pr_nas2;
            }
        } else {
            pr_nas1 = "---";
        }
        String cod_cliente = generaId(22) + filiale;

        //CZ
        boolean controllareCliente = true;
        boolean op_sos = getValue_request(request, "op_sos");

        Client_CZ cz = null;
        if (!is_IT) {
            String showanagVALUE = request.getParameter("showanagVALUE");
            String showanagNEWUK = getValue_request(request, "showanagNEWUK", false, "NO");
            String heavy_pno1 = getValue_request(request, "heavy_pno1", false, "");
            String heavy_cz_country = getValue_request(request, "heavy_cz_country", false, "---");
            String heavy_cz_issuingcountry = getValue_request(request, "heavy_cz_issuingcountry", false, "---");
            String heavy_sanctions = getValue_request(request, "heavy_sanctions", false, "");
            String heavy_pep = getValue_request(request, "heavy_pep", false, "");
            String heavy_transactionre = getValue_request(request, "heavy_transactionre", false, "");
            String heavy_moneysource = getValue_request(request, "heavy_moneysource", false, "");
            String heavy_occupation = getValue_request(request, "heavy_occupation", false, "");
            String pep_position = getValue_request(request, "pep_position", false, "");
            String pep_country = getValue_request(request, "pep_country", false, "---");
            if (heavy_pep.equals("NO")) {
                pep_position = "";
                pep_country = "---";
            }

            if (showanagNEWUK.trim().equals("YES")) {
                //PARZIALE
                cz = new Client_CZ(cod_cliente, cod, showanagVALUE, "", heavy_cz_country,
                        heavy_cz_issuingcountry, "", "", "",
                        "", "false", "", "", "");
            } else if (showanagVALUE.trim().equals("none") && !op_sos) {
                controllareCliente = false;
            } else {
                cz = new Client_CZ(cod_cliente, cod, showanagVALUE, heavy_pno1, heavy_cz_country,
                        heavy_cz_issuingcountry, heavy_sanctions, heavy_pep, heavy_transactionre,
                        heavy_moneysource, valueOf(op_sos), heavy_occupation, pep_position, pep_country);
            }
        }

        boolean giuridica = false;
        String oldpercent_buy = "-";
        String oldpercent_sell = "-";

        if (!controllareCliente) {
            cod_cliente = "---";
            cl_cf = "---";
        } else {
            if (is_IT) {

                if (tipocliente.equals("003")) { //persona giuridica
                    giuridica = true;
                    cod_cliente = company;
                    cl_cf = "---";
                } else if (!oldclient.equals("0")) {
                    cod_cliente = oldclient;
                    oldclient = "1";
                } else {
                    Db_Master db1 = new Db_Master(true);
                    if (db1.getC() == null) {
                        db1 = new Db_Master();
                    }
                    String cl_check = db1.get_codice_client(cl_cf, tipocliente,
                            heavy_name, heavy_surname, heavy_pob_date, heavy_pob_country);
                    if (cl_check != null) {
                        cod_cliente = cl_check;
                        String[] percent = db1.getClientCommission(cod_cliente);
                        oldpercent_buy = percent[0];
                        oldpercent_sell = percent[1];
                        oldclient = "1";
                    }
                    db1.closeDB();
                }
                if (is_IT && heavy_pob_country.equals(codnaz)) { //ITALIA
                    if (pr_nas1 == null) {
                        pr_nas1 = pr_nas2;
                        if (pr_nas1 == null) {
                            pr_nas1 = "---";
                        }
                    }
                } else {
                    pr_nas1 = "---";
                }
            }

        }

//        if (agency == null) {
//            agency = "0";
//            agency_cod = "-";
//        } else if (agency.equals("on")) {
//            agency = "1";
//        }
        Ch_transaction trans = new Ch_transaction();
        trans.setTill(till);
        trans.setTipocliente(tipocliente);
        trans.setId_open_till(id_open_till);
        trans.setPay(formatDoubleforMysql(pay));
        trans.setTotal(formatDoubleforMysql(total));
        trans.setFix(formatDoubleforMysql(fix));
        trans.setCom(formatDoubleforMysql(com));
        trans.setRound(formatDoubleforMysql(round));
        trans.setCommission(formatDoubleforMysql(commission));
        trans.setNote(substring(getStringUTF8(note), 0, 100));
        trans.setAgency(agency);
        trans.setAgency_cod(agency_cod);
        trans.setCl_cf(cl_cf);

        trans.setDel_fg("0");
        trans.setDel_dt("1901-01-01 00:00:00");
        trans.setDel_user("-");
        trans.setDel_motiv("-");

        trans.setSpread_total("-");
        trans.setLocalfigures("-");
        trans.setPos("-");

        trans.setIntbook(newintbook0);

        trans.setIntbook_type("-");
        trans.setIntbook_1_mod("-");
        trans.setIntbook_1_tf("-");
        trans.setIntbook_1_val("-");
        trans.setIntbook_2_mod("-");
        trans.setIntbook_2_tf("-");
        trans.setIntbook_2_val("-");
        trans.setIntbook_3_mod("-");
        trans.setIntbook_3_tf("-");
        trans.setIntbook_3_val("-");
        trans.setIntbook_cli("-");
        trans.setIntbook_mac("-");

        trans.setData(dt_tr);
        trans.setFiliale(filiale);
        trans.setUser(user);
        trans.setCod(cod);
        trans.setCredccard_number("-");

////        CLIENTE
        Client cl = new Client();
        cl.setCode(cod_cliente);
        cl.setCognome(heavy_surname);
        cl.setNome(heavy_name);
        cl.setSesso(heavy_sex);
        cl.setCodfisc(cl_cf);
        cl.setNazione(heavy_country);
        String heavy_city1 = heavy_city;
        if (heavy_city1.equals("")) {
            heavy_city1 = heavy_city_dis;
        }
        cl.setCitta(heavy_city1);
        cl.setIndirizzo(heavy_address);
        cl.setCap(heavy_zipcode);
        String pro = heavy_district;
        if (pro.trim().equals("")) {
            pro = heavy_district_dis;
            if (pro.trim().equals("")) {
                pro = "-";
            }
        }
        cl.setProvincia(pro);
        cl.setCitta_nascita(heavy_pob_city);
        cl.setProvincia_nascita(pr_nas1);
        cl.setNazione_nascita(heavy_pob_country);
        cl.setDt_nascita(heavy_pob_date);
        cl.setTipo_documento(heavy_identcard);
        cl.setNumero_documento(heavy_numberidentcard);
        cl.setDt_rilascio_documento(heavy_issuedateidentcard);
        cl.setDt_scadenza_documento(heavy_exdateidentcard);
        cl.setRilasciato_da_documento(heavy_issuedbyidentcard);
        cl.setLuogo_rilascio_documento(heavy_issuedplaceidentcard);
        cl.setEmail(heavy_email);
        cl.setTelefono(heavy_phonenu);

        cl.setTimestamp(dt_tr);

        cl.setPep(heavy_pepI);
        Db_Master db2 = new Db_Master();
        CustomerKind ck = db2.get_customerKind(tipocliente);
        db2.closeDB();

        String esitobl = "NO";
        BlacklistM bm = null;
        BlackListsAT bl = null;

        if (!giuridica) {
            bm = verifyBlacklistMac(cl);
            if (is_IT) {
                bl = verificaBlackList(cod, "MACCORP", filiale, user, cl);
                esitobl = bl.getEsito();
            } else {
                trans.setBl_status("2");
                trans.setBl_motiv("-");
            }
        }

        boolean bbtot = false;
        boolean sbtot = false;

        if (esitobl.equals("1")) {
            ok = "kobl";
            request.getSession().setAttribute("msgbl1", "<br/><b>SURNAME:</b> " + heavy_surname + "<br/><b>NAME:</b> " + heavy_name);
        } else if (bm != null) {
            ok = "koblm&codbl=" + bm.getCode();
        } else {
            if (!giuridica) {
                if (bl != null) {
                    if (esitobl.equals("-1")) {
                        bl.setEsito("2");
                    }
                    trans.setBl_status(bl.getEsito());
                    trans.setBl_motiv(bl.getError());
                }
            } else {
                trans.setBl_status("2");
                trans.setBl_motiv("-");
            }

            trans.setTipotr("B");
            trans.setCl_cod(cod_cliente);
            trans.setRefund("0");
            trans.setFa_number("-");
            trans.setCn_number("-");
            trans.setHeavy_pepI("NO");

            ArrayList<Ch_transaction_value> list_row = new ArrayList<>();

            double newbuypercent = 0.00;
            double spread_total = 0.0;
            for (int i = 1; i < 6; i++) {

                String totalriga = getValue_request(request, "total" + i, false, "");
                if (!formatDoubleforMysql(totalriga).equals("0.00")) {
                    String idriga = generaId(25);
                    String codtr = trans.getCod();
                    String numeroriga = valueOf(i);
                    String supportoriga = getValue_request(request, "kind" + i, false, "");
                    String posriga = getValue_request(request, "posvalue" + i, false, "N");
                    String posnumriga = getValue_request(request, "posnum" + i, false, "");
                    String valutariga = getValue_request(request, "figs" + i, false, "");
                    String quantitariga = getValue_request(request, "quantity" + i, false, "");
                    String rateriga = getValue_request(request, "rate" + i, false, "");
                    if (typerate.equals("UNLOCK")) {
                        rateriga = formatDoubleforMysql(getValue_request(request, "fieldrate" + i, false, ""));
                    }
                    rateriga = removeLast(rateriga);

                    String spreadriga = getValue_request(request, "spread" + i, false, "KO");
                    if (!spreadriga.equals("KO")) {

                        List<String> calcolospread = calcolaspread2021(
                                filiale,
                                "B",
                                "",
                                "",
                                valutariga,
                                supportoriga,
                                formatDoubleforMysql(quantitariga),
                                rateriga);
                        if (calcolospread.isEmpty()) {
                            spreadriga = formatDoubleforMysql(spreadriga);
                        } else {
                            try {
                                spreadriga = calcolospread.get(1).equals("0,00") ? "0.00" : formatDoubleforMysql(calcolospread.get(1));
                            } catch (Exception e) {
                                spreadriga = formatDoubleforMysql(spreadriga);
                            }
                        }

                    }

                    String roundvalue = getValue_request(request, "roundvalue" + i, false, "");
                    String com_percriga = getValue_request(request, "comperc" + i, false, "");
                    String com_perc_totriga = getValue_request(request, "totperc" + i, false, "");
                    String fx_commriga = getValue_request(request, "fxcomm" + i, false, "");
                    String tot_comriga = getValue_request(request, "totcomm" + i, false, "");
                    String netriga = getValue_request(request, "net" + i, false, "");

                    String kind_fix_commriga = getValue_request(request, "kindfixcomm" + i, false, "-");
                    String low_comm_juriga = getValue_request(request, "lowcommjus" + i, false, "");

                    String sb_bb = "N";
                    String bbriga = getValue_request(request, "bb" + i, false, "N");
                    String sbriga = getValue_request(request, "sb" + i, false, "N");
                    String bb_fidcoderiga = getValue_request(request, "fidcode" + i, false, "-");

                    if (!bbriga.equals("N")) {
                        bbtot = true;
                        sb_bb = bbriga;
                    } else {
                        if (!sbriga.equals("N")) {
                            sbtot = true;
                            sb_bb = sbriga;
                            bb_fidcoderiga = "-";
                        }
                    }

                    String contr_valuta = cur_default[0];
                    String contr_supporto = "01";
                    String contr_quantita = trans.getPay();

                    Ch_transaction_value chv = new Ch_transaction_value();
                    chv.setId(idriga);
                    chv.setCod_tr(codtr);
                    chv.setNumeroriga(numeroriga);
                    chv.setSupporto(supportoriga);
                    chv.setPos(posriga);
                    chv.setPosnum(posnumriga);
                    chv.setValuta(valutariga);
                    chv.setQuantita(formatDoubleforMysql(quantitariga));
                    chv.setRate(rateriga);
                    chv.setCom_perc(formatDoubleforMysql(com_percriga));
                    if (i == 1) {
                        newbuypercent = fd(formatDoubleforMysql(com_percriga));
                    } else if (fd(formatDoubleforMysql(com_percriga)) < newbuypercent) {
                        newbuypercent = fd(formatDoubleforMysql(com_percriga));
                    }

                    chv.setCom_perc_tot(formatDoubleforMysql(com_perc_totriga));
                    chv.setFx_com(formatDoubleforMysql(fx_commriga));
                    chv.setTot_com(formatDoubleforMysql(tot_comriga));
                    chv.setNet(removeLast(formatDoubleforMysql(netriga)));
                    chv.setSpread(spreadriga);
                    chv.setTotal(formatDoubleforMysql(totalriga));
                    chv.setKind_fix_comm(kind_fix_commriga);
                    chv.setLow_com_ju(low_comm_juriga);
                    chv.setBb(sb_bb);
                    chv.setBb_fidcode(bb_fidcoderiga);
                    chv.setDt_tr(dt_tr);
                    chv.setContr_valuta(contr_valuta);
                    chv.setContr_supporto(contr_supporto);
                    chv.setContr_quantita(contr_quantita);
                    chv.setRoundvalue(roundvalue);
                    chv.setDel_fg("0");
                    chv.setDel_dt("1901-01-01 00:00:00");
                    list_row.add(chv);

                    spread_total = spread_total + fd(spreadriga);

                }

            }

            if (newbuypercent > 0) {
                cl.setPerc_buy(roundDoubleandFormat(newbuypercent, 2));
            } else {
                cl.setPerc_buy(oldpercent_buy);
            }
            cl.setPerc_sell(oldpercent_sell);

            if (ok.equals("0")) {
                if (list_row.isEmpty()) {
                    ok = "koins2";
                }
            }

            if (ok.equals("0")) {
                Db_Master db3 = new Db_Master();
                ArrayList<String[]> real = db3.list_oc_change_real(id_open_till);

                db3.closeDB();
////                controlli cassa euro
                double start = 0.00;

                for (int i = 0; i < real.size(); i++) {
                    if (real.get(i)[2].equals(cur_default[0]) && real.get(i)[1].equals("01")) {
                        start = fd(real.get(i)[3]);
                        break;
                    }
                }
                if (start < fd(trans.getPay())) {
                    ok = "koquant";
                }
            }

            //blocca fidelity cod
            if (ok.equals("0")) {
                if (bbtot) {
                    for (int i = 0; i < list_row.size(); i++) {
                        boolean bbrow = list_row.get(i).getBb().equals("Y") || list_row.get(i).getBb().equals("F");
                        if (bbrow) {
                            String fidcode = list_row.get(i).getBb_fidcode();
                            if (fidcode.length() == 18) {
                                String id_fil = fidcode.substring(0, 3);
                                String id_tra = fidcode.substring(3);
                                Db_Master db14 = new Db_Master();
                                db14.update_status_bb_transaction(id_fil, id_tra, "2", user);
                                db14.closeDB();
                            }
                        }
                    }
                }
            }

            if (ok.equals("0")) { //settimanale
                double soglia = fd(ck.getIp_max_settimanale());
                soglia = get_soglia_CZ(soglia);
                if (fd(trans.getPay()) > soglia) {
                    ok = "komaxsett";
                }
                if (ok.equals("0")) {

                    //NUOVO 09/05
                    Db_Master db4A = new Db_Master();
                    double weekA = db4A.weekly_transaction(cod_cliente);
                    db4A.closeDB();

                    Db_Master db4 = new Db_Master(true);
                    if (db4.getC() == null) {
                        db4 = new Db_Master();
                    }
                    double week = db4.weekly_transaction_nofiliale(cod_cliente, trans.getFiliale());
//                        double week = db4.weekly_transaction(cod_cliente);
                    db4.closeDB();
                    double now = fd(trans.getPay()) + week + weekA;
//                        double now = fd(trans.getPay()) + week;

                    if (now > soglia) {
                        ok = "komaxsett";
                    }

                }
            }

            trans.setSpread_total(roundDoubleandFormat(spread_total, 2));

            trans.setBb(getStatus_BBSB(bbtot, sbtot));

            if (ok.equals("0")) {
                Db_Master db6 = new Db_Master();
                boolean es1 = db6.insert_transaction_change(trans, "ch_transaction_temp");
                db6.closeDB();
                if (es1) {

                    if (newintbook0.equals("1")) {
                        Db_Master dbib = new Db_Master();
                        boolean esib = dbib.insert_InternetBookingCH(cod, newintbookCanale, newintbookCodice, user);
                        dbib.closeDB();
                    }

                    Db_Master db7 = new Db_Master();
                    boolean es2 = db7.insert_transaction_value(list_row);
                    db7.closeDB();
                    if (es2) {

                        insertStock_change(trans, list_row);

                        Db_Master db8 = new Db_Master();
                        Stock_report sr = db8.get_Stock_report(id_open_till, cur_default[0], "01", "CH", filiale, till);
                        db8.closeDB();
                        //  DIMINUISCO EURO
                        String codsr = filiale + generaId(47);
                        sr.setCodtr(cod);
                        sr.setCodice(codsr);
//                        double newtot = fd(sr.getTotal()) - fd(trans.getPay());
                        double newtot = -fd(trans.getPay());
                        sr.setTotal(roundDoubleandFormat(newtot, 2));
                        sr.setSpread("1.0000");
                        sr.setData(dt_tr);
                        sr.setQuantity("0");
                        sr.setUser(user);
                        Db_Master db9 = new Db_Master();
                        db9.insert_Stockreport(sr);
                        db9.closeDB();

                        for (int i = 0; i < list_row.size(); i++) {
                            Ch_transaction_value ctv = list_row.get(i);

                            Db_Master db8A = new Db_Master();
                            Stock_report srA = db8A.get_Stock_report(id_open_till, ctv.getValuta(), ctv.getSupporto(), "CH", filiale, till);
                            db8A.closeDB();

                            //  AUMENTO INCASSI
                            String codsrA = filiale + generaId(47);
                            srA.setCodtr(cod);
                            srA.setCodice(codsrA);
//                            double newtotA = fd(srA.getTotal()) + fd(ctv.getQuantita());
                            double newtotA = fd(ctv.getQuantita());
                            srA.setTotal(roundDoubleandFormat(newtotA, 2));
                            srA.setSpread("1.0000");
                            srA.setData(dt_tr);
                            srA.setQuantity("0");
                            srA.setUser(user);
                            Db_Master db9A = new Db_Master();
                            db9A.insert_Stockreport(srA);
                            db9A.closeDB();

                        }

                        for (int i = 0; i < list_row.size(); i++) {

                            if (list_row.get(i).getSupporto().equals("04")) {

                                Real_oc_pos t1 = new Real_oc_pos();
                                t1.setFiliale(filiale);
                                t1.setCod_oc(id_open_till);
                                t1.setValuta(list_row.get(i).getValuta());
                                t1.setKind(list_row.get(i).getSupporto());
                                t1.setCarta_credito(list_row.get(i).getPos());
                                t1.setData(dt_tr);
                                Db_Master db10 = new Db_Master();
                                t1.setIp_quantity(valueOf(parseInt(db10.getField_real_oc_pos(t1, "ip_quantity", "0")) + 1));
                                t1.setIp_value(roundDoubleandFormat((fd(db10.getField_real_oc_pos(t1, "ip_value", "0.00")) + fd(list_row.get(i).getQuantita())), 2));
                                db10.update_real_oc_pos(t1);
                                db10.closeDB();

                            } else {
                                Real_oc_change righe = new Real_oc_change();
                                righe.setFiliale(filiale);
                                righe.setCod_oc(id_open_till);
                                righe.setValuta(list_row.get(i).getValuta());
                                righe.setKind(list_row.get(i).getSupporto());
                                righe.setData(dt_tr);
                                righe.setNum_kind_op("0");
                                Db_Master db10 = new Db_Master();
                                righe.setValue_op(roundDoubleandFormat((fd(db10.getField_real_oc_change(righe, "value_op", "0.00")) + fd(list_row.get(i).getQuantita())), 2));

                                db10.update_real_oc_change(righe);
                                db10.closeDB();
                            }
                        }

                        Real_oc_change t1 = new Real_oc_change();
                        t1.setFiliale(filiale);
                        t1.setCod_oc(id_open_till);
                        t1.setValuta(cur_default[0]);
                        t1.setKind("01");
                        t1.setData(dt_tr);
                        t1.setNum_kind_op("0");
                        Db_Master db11 = new Db_Master();
                        t1.setValue_op(roundDoubleandFormat((fd(db11.getField_real_oc_change(t1, "value_op", "0.00")) - fd(trans.getPay())), 2));
                        db11.update_real_oc_change(t1);
                        db11.closeDB();

                        removeStock_Pay(trans, cur_default[0], "01", fd(trans.getPay()));

                        if (!giuridica) {

                            if (is_IT) {

                                Db_Master db11a = new Db_Master();
                                boolean es3 = db11a.insert_anagrafica_client_ITA(cl, oldclient);
                                // 10-10 - INSERISCO IN CLIENT TRANSACTION
                                db11a.insert_client_transaction(cl, cod);
                                db11a.closeDB();
                                if (!es3) {
                                    ok = "koins1";
                                }
                            } else {
                                if (controllareCliente) {
                                    if (cz != null) {
                                        cl.setRepceca(cz);
                                        Db_Master db11a = new Db_Master();
                                        // INSERISCO IN CLIENT TRANSACTION
                                        boolean ins_1 = true;
                                        if (is_CZ) {
                                            ins_1 = db11a.insert_client_transaction(cz, "client_cz");
                                        } else if (is_UK) {
                                            ins_1 = db11a.insert_client_transaction(cz, "client_uk");
                                        }
                                        // INSERISCO IN CLIENT TRANSACTION
                                        boolean ins_2 = db11a.insert_client_transaction(cl, cod);
                                        db11a.closeDB();
                                        if (!ins_1 || !ins_2) {
                                            ok = "koins1";
                                        }
                                    }
                                }

                            }

                        }
                    } else {
                        ok = "koins2";
                    }
                } else {
                    ok = "koins3";
                }
            }
        }

        if (ok.equals("0")) {
            if (typerate.equals("UNLOCK") || unlockCode_final.equals("UNLOCK")) {
                Db_Master db12 = new Db_Master();
                boolean esa = db12.use_codice_sblocco_NEW(dt_tr, "01", user, cod, unlockratesel);
                db12.closeDB();
                if (!esa) {
                    ok = "koins4";
                }
            }
        }

        if (ok.equals("0")) {
            boolean es = generateInvoice(trans, "ch_transaction_temp");
            if (!es) {
                ok = "kofatt";
            }
        }

        //nuovo 07/05
        if (ok.equals("0")) {
            String loya = getValue_request(request, "loya", false, "");
            if (!loya.equals("")) {
                Db_Loy dbl = new Db_Loy();
                if (dbl.getC() != null) {
                    String completo[] = dbl.getCodiceCompleto(loya);
                    if (completo != null) {
                        dbl.ins_mac_associate(cl.getCode(), completo[0]);
                        dbl.update_stato_codici(completo[0], "1");
                        Db_Master db12a = new Db_Master();
                        db12a.insertLoy_TR(cod, cl.getCode(), completo[0], user);
                        db12a.closeDB();

                        //  welcome
                        if (welc.equals("1")) {
                            Ch_transaction tmp = query_transaction_ch_temp(cod);
                            insertTR("W", (String) request.getSession().getAttribute("us_cod"),
                                    "VENDITA AUTOMATICA WELCOME KIT - TRANSAZIONE " + tmp.getId() + " CODICE: " + completo[0]);
                            insertNC_transaction_WK(true, tmp, cl, completo[0]);
                        }

                        if (fidel.equals("1")) {
                            Ch_transaction tmp = query_transaction_ch_temp(cod);
                            insertTR("W", (String) request.getSession().getAttribute("us_cod"),
                                    "VENDITA AUTOMATICA FIDEL. CARD - TRANSAZIONE " + tmp.getId() + " CODICE: " + completo[0]);
                            insertNC_transaction_WK(false, tmp, cl, completo[0]);
                        }
                    }
                    dbl.closeDB();
                }
            }
        }
        // nuovo 12/3/20

        if (ok.equals("0")) {
            String start = "transaction_ok_mod.jsp";
            if (signoffline) {
                start = "transaction_ok_mod_new.jsp";
            }

            String link = start + "?cod=" + cod + "&kycpres=" + kycpres;

            String oldloy = getValue_request(request, "oldloy", false, "0");

            if (oldloy.equals("0")) {
                if (is_marketing_OK(cl.getCode())) {
                    oldloy = "1";
                }
            }

            link = link + "&oldloy=" + oldloy;

            insertLink(user, cod, 0, request, true, "Operazioni_test", link);
            redirect(request, response, link);
        } else {

            elimina(user, cod);

            request.getSession().setAttribute("esito_b", ok);
            redirect(request, response, "transaction_b.jsp?esito=" + ok);
        }

    }

    private String getStatus_BBSB(
            boolean bbtot,
            boolean sbtot
    ) {
        if (bbtot) {
            return "1";
        } else if (sbtot) {
            return "3";
        } else {
            return "0";
        }
    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void ch_tr_deletedENGSELL(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String newintbook0 = request.getParameter("newintbook0");
        String newintbookCanale = request.getParameter("newintbookCanale");
        String newintbookCodice = request.getParameter("newintbookCodice");
        if (null == newintbook0) {
            newintbook0 = "0";
            newintbookCanale = "";
            newintbookCodice = "";
        } else {
            switch (newintbook0) {
                case "on":
                    newintbook0 = "1";
                    break;
                default:
                    newintbook0 = "0";
                    newintbookCanale = "";
                    newintbookCodice = "";
                    break;
            }
        }

//        //nuovo 11/04
//        String kycpres = request.getParameter("kycpres");
//        if (kycpres == null) {
//            kycpres = "0";
//        } else if (kycpres.equals("on")) {
//            kycpres = "1";
//        } else {
//            kycpres = "0";
//        }
        String ok = "0";
        String user = (String) request.getSession().getAttribute("us_cod");
        if (user == null) {
            user = "9999";
        }

        Db_Master db = new Db_Master();
        String[] cur_default = db.get_local_currency();
        String dt_tr = db.getNow();
        db.closeDB();

        String cod = request.getParameter("cod");

        String typerate = request.getParameter("typerate");
        String unlockCode_final = request.getParameter("unlockCode_final");
        String unlockratesel = request.getParameter("unlockratesel");

        String id_open_till = request.getParameter("id_open_till");
        String till = request.getParameter("till");
        String customerKind = request.getParameter("customerKind");
        String payout1 = request.getParameter("payout1");

        String total0 = request.getParameter("total0");
        String fix0 = request.getParameter("fix0");
        String com0 = request.getParameter("com0");
        String round0 = request.getParameter("round0");
        String commission0 = request.getParameter("commission0");

        String note1 = request.getParameter("note1");

        String agency = "0";
        String agency_cod = "-";
        String cl_cf = "---";

        String heavy_surname = request.getParameter("heavy_surname").trim();
        String heavy_name = request.getParameter("heavy_name").trim();
        String heavy_sex = request.getParameter("heavy_sex");
        if (heavy_sex == null || heavy_sex.equals("null")) {
            heavy_sex = "M";
        }
        String heavy_country = request.getParameter("heavy_country");
        String heavy_city = request.getParameter("heavy_city");
        String heavy_city_dis = request.getParameter("heavy_city_dis");
        String heavy_address = request.getParameter("heavy_address");
        if (heavy_address == null || heavy_address.equals("null")) {
            heavy_address = "-";
        }

        String heavy_zipcode = request.getParameter("heavy_zipcode");
        if (heavy_zipcode != null) {
            if (heavy_zipcode.trim().length() > 10) {
                heavy_zipcode = substring(heavy_zipcode.trim(), 0, 10);
            } else {
                heavy_zipcode = heavy_zipcode.trim();
            }
        }
        String heavy_district = request.getParameter("heavy_district");
        String heavy_district_dis = request.getParameter("heavy_district_dis");
        String heavy_pob_city = request.getParameter("heavy_pob_city");
        String heavy_pob_country = request.getParameter("heavy_pob_country").trim();
        String heavy_pob_date = request.getParameter("heavy_pob_date").trim();
        String heavy_identcard = request.getParameter("heavy_identcard");
        String heavy_numberidentcard = request.getParameter("heavy_numberidentcard");
        String heavy_issuedateidentcard = request.getParameter("heavy_issuedateidentcard");
        String heavy_exdateidentcard = request.getParameter("heavy_exdateidentcard");
        String heavy_issuedbyidentcard = request.getParameter("heavy_issuedbyidentcard");
        String heavy_issuedplaceidentcard = request.getParameter("heavy_issuedplaceidentcard");
        String heavy_email = request.getParameter("heavy_email");
        String heavy_phonenu = request.getParameter("heavy_phonenu");
        String heavy_pepI = request.getParameter("heavy_pepI");
        if (heavy_pepI == null || heavy_pepI.trim().equals("") || heavy_pepI.trim().equals("null")) {
            heavy_pepI = "NO";
        }
        String pr_nas1 = "---";
        String pr_nas2 = "---";

        String company = request.getParameter("company");
        String oldclient = request.getParameter("oldclient");

        String cod_cliente = generaId(22) + filiale;

        //CZ
        String showanagVALUE = request.getParameter("showanagVALUE");
        String heavy_pno1 = request.getParameter("heavy_pno1");
        String heavy_cz_country = request.getParameter("heavy_cz_country");
        String heavy_cz_issuingcountry = request.getParameter("heavy_cz_issuingcountry");
        String heavy_sanctions = request.getParameter("heavy_sanctions");
        String heavy_pep = request.getParameter("heavy_pep");
        String heavy_transactionre = request.getParameter("heavy_transactionre");
        String heavy_moneysource = request.getParameter("heavy_moneysource");
        String heavy_occupation = request.getParameter("heavy_occupation");

        String pep_position = request.getParameter("pep_position");
        String pep_country = request.getParameter("pep_country");

        if (heavy_pep.equals("NO")) {
            pep_position = "";
            pep_country = "---";
        }

        boolean op_sos;
        if (request.getParameter("op_sos") == null) {
            op_sos = false;
        } else {
            op_sos = request.getParameter("op_sos").equals("on");
        }

        String oldpercent_buy = "-";
        String oldpercent_sell = "-";

        if (!oldclient.equals("0")) {
            cod_cliente = oldclient;
//            oldclient = "1";
        } else {
            Db_Master db1 = new Db_Master(true);
            if (db1.getC() == null) {
                db1 = new Db_Master();
            }
            String cl_check = db1.get_codice_client(cl_cf, customerKind,
                    heavy_name, heavy_surname, heavy_pob_date, heavy_pob_country);
            if (cl_check != null) {
                cod_cliente = cl_check;
                String[] percent = db1.getClientCommission(cod_cliente);
                oldpercent_buy = percent[0];
                oldpercent_sell = percent[1];
//                oldclient = "1";
            }
            db1.closeDB();
        }

        Client_CZ cz = new Client_CZ(cod_cliente, cod, showanagVALUE, heavy_pno1, heavy_cz_country,
                heavy_cz_issuingcountry, heavy_sanctions, heavy_pep, heavy_transactionre,
                heavy_moneysource, valueOf(op_sos), heavy_occupation, pep_position, pep_country);

        String kind_p1 = request.getParameter("kind_p1");
        String ban_1 = request.getParameter("ban_1");
        String posnum = request.getParameter("posnum");
        String cc_number = request.getParameter("cc_number");

        Ch_transaction trans = new Ch_transaction();
        trans.setTill(till);
        trans.setTipocliente(customerKind);
        trans.setId_open_till(id_open_till);
        trans.setPay(formatDoubleforMysql(payout1));
        trans.setTotal(formatDoubleforMysql(total0));
        trans.setFix(formatDoubleforMysql(fix0));
        trans.setCom(formatDoubleforMysql(com0));
        trans.setRound(formatDoubleforMysql(round0));
        trans.setCommission(formatDoubleforMysql(commission0));
        trans.setNote(substring(getStringUTF8(note1), 0, 100));
        trans.setAgency(agency);
        trans.setAgency_cod(agency_cod);
        trans.setCl_cf(cl_cf);

        trans.setDel_fg("0");
        trans.setDel_dt("1901-01-01 00:00:00");
        trans.setDel_user("-");
        trans.setDel_motiv("-");
        trans.setIntbook(newintbook0);

        String tfnc0 = request.getParameter("tfnc0");
        if (tfnc0 == null) {
            tfnc0 = "0";
        } else if (tfnc0.equalsIgnoreCase("on")) {
            tfnc0 = "1";
        } else {
            tfnc0 = "0";
        }

        String intbook_1 = request.getParameter("intbook_1");
        String code_intbook_1 = request.getParameter("code_intbook_1");
        String value_intbook_1 = request.getParameter("value_intbook_1");

        if (intbook_1.equals("...")) {
            intbook_1 = "-";
            code_intbook_1 = "-";
            value_intbook_1 = "-";
        }

        String intbook_2 = request.getParameter("intbook_2");
        String code_intbook_2 = request.getParameter("code_intbook_2");
        String value_intbook_2 = request.getParameter("value_intbook_2");

        if (intbook_2.equals("...")) {
            intbook_2 = "-";
            code_intbook_2 = "-";
            value_intbook_2 = "-";
        }

        String intbook_3 = request.getParameter("intbook_3");
        String code_intbook_3 = request.getParameter("code_intbook_3");
        String value_intbook_3 = request.getParameter("value_intbook_3");

        if (intbook_3.equals("...")) {
            intbook_3 = "-";
            code_intbook_3 = "-";
            value_intbook_3 = "-";
        }

        String macval = request.getParameter("macval");
        String cusval = request.getParameter("cusval");

        if (tfnc0.equals("0")) {
            intbook_1 = "-";
            code_intbook_1 = "-";
            value_intbook_1 = "-";
            intbook_2 = "-";
            code_intbook_2 = "-";
            value_intbook_2 = "-";
            intbook_3 = "-";
            code_intbook_3 = "-";
            value_intbook_3 = "-";
            macval = "-";
            cusval = "-";
        }

        trans.setIntbook_type(tfnc0);
        trans.setIntbook_1_mod(formatDoubleforMysql(code_intbook_1));
        trans.setIntbook_1_tf(intbook_1);
        trans.setIntbook_1_val(formatDoubleforMysql(value_intbook_1));
        trans.setIntbook_2_mod(formatDoubleforMysql(code_intbook_2));
        trans.setIntbook_2_tf(intbook_2);
        trans.setIntbook_2_val(formatDoubleforMysql(value_intbook_2));
        trans.setIntbook_3_mod(formatDoubleforMysql(code_intbook_3));
        trans.setIntbook_3_tf(intbook_3);
        trans.setIntbook_3_val(formatDoubleforMysql(value_intbook_3));
        trans.setIntbook_cli(formatDoubleforMysql(cusval));
        trans.setIntbook_mac(formatDoubleforMysql(macval));

        ArrayList<String[]> nc_taxfree = nc_taxfree(trans);

        boolean bbtot = false;
        boolean sbtot = false;

        switch (kind_p1) {
            case "01":
                posnum = "-";
                cc_number = "-";
                break;
            case "06":
                break;
            case "07":
                cc_number = "-";
                break;
            case "08":
                posnum = ban_1;
                cc_number = "-";
                break;
            default:
                break;
        }
        trans.setLocalfigures(kind_p1);
        trans.setPos(posnum);
        trans.setCredccard_number(cc_number);

        trans.setData(dt_tr);
        trans.setFiliale(filiale);
        trans.setUser(user);
        trans.setCod(cod);

        //CLIENTE
        Client cl = new Client();
        cl.setCode(cod_cliente);
        cl.setCognome(heavy_surname);
        cl.setNome(heavy_name);
        cl.setSesso(heavy_sex);
        cl.setCodfisc(cl_cf);
        cl.setNazione(heavy_country);
        String heavy_city1 = heavy_city;
        if (heavy_city1 == null) {
            heavy_city1 = heavy_city_dis;
        }
        cl.setCitta(heavy_city1);
        cl.setIndirizzo(heavy_address);
        cl.setCap(heavy_zipcode);
        String pro = heavy_district;
        if (pro == null) {
            pro = heavy_district_dis;
            if (pro == null) {
                pro = "-";
            }
        }
        cl.setProvincia(pro);
        cl.setCitta_nascita(heavy_pob_city);
        cl.setProvincia_nascita(pr_nas1);
        cl.setNazione_nascita(heavy_pob_country);
        cl.setDt_nascita(heavy_pob_date);
        cl.setTipo_documento(heavy_identcard);
        cl.setNumero_documento(heavy_numberidentcard);
        cl.setDt_rilascio_documento(heavy_issuedateidentcard);
        cl.setDt_scadenza_documento(heavy_exdateidentcard);
        cl.setRilasciato_da_documento(heavy_issuedbyidentcard);
        cl.setLuogo_rilascio_documento(heavy_issuedplaceidentcard);
        cl.setEmail(heavy_email);
        cl.setTelefono(heavy_phonenu);
        cl.setPerc_buy("-");
        cl.setPerc_sell("-");
        cl.setTimestamp(dt_tr);

        cl.setPep(heavy_pepI);

        if (kind_p1.equals("...")) {
            ok = "kopaym";
        }

        double spread_total = 0.00;

        if (ok.equals("0")) {

            String esitobl = "NO";
            BlacklistM bm = null;
            BlackListsAT bl = null;
            trans.setBl_status("2");
            trans.setBl_motiv("-");
            trans.setTipotr("S");
            trans.setCl_cod(cod_cliente);
            trans.setRefund("0");
            trans.setFa_number("-");
            trans.setCn_number("-");
            trans.setHeavy_pepI("NO");

            ArrayList<Ch_transaction_value> list_row = new ArrayList<>();

            double newsellpercent = 0.00;

            for (int k = 1; k < 6; k++) {

                String totalriga = request.getParameter("total" + k);
                if (!formatDoubleforMysql(totalriga).equals("0.00")) {

                    String idriga = generaId(25);
                    String codtr = trans.getCod();

                    String numeroriga = valueOf(k);

                    String supportoriga = request.getParameter("kind" + k);
                    String valutariga = request.getParameter("figs" + k);
                    String quantitariga = request.getParameter("quantity" + k);

                    String rateriga = request.getParameter("rate" + k);
                    if (typerate.equals("UNLOCK")) {
                        rateriga = formatDoubleforMysql(request.getParameter("fieldrate" + k));
                    }
                    rateriga = removeLast(rateriga);

                    String roundvalue = request.getParameter("roundvalue" + k);

                    String com_percriga = request.getParameter("comperc" + k);
                    String totpercriga = request.getParameter("totperc" + k);
                    String fx_commriga = request.getParameter("fxcomm" + k);
                    String totcomm1 = request.getParameter("totcomm" + k);
                    String net1 = request.getParameter("net" + k);
                    String spreadriga = getValue_request(request, "spread" + k, false, "KO");
                    if (!spreadriga.equals("KO")) {
                        List<String> calcolospread = calcolaspread2021(
                                filiale,
                                "S",
                                "",
                                "",
                                valutariga,
                                supportoriga,
                                formatDoubleforMysql(quantitariga),
                                rateriga);
                        if (calcolospread.isEmpty()) {
                            spreadriga = formatDoubleforMysql(spreadriga);
                        } else {
                            try {
                                spreadriga = calcolospread.get(1).equals("0,00") ? "0.00" : formatDoubleforMysql(calcolospread.get(1));
                            } catch (Exception e) {
                                spreadriga = formatDoubleforMysql(spreadriga);
                            }
                        }

                    }

                    String kind_fix_commriga = request.getParameter("kindfixcomm" + k);
                    if (kind_fix_commriga == null) {
                        kind_fix_commriga = "-";
                    }
                    String low_comm_juriga = request.getParameter("lowcommjus" + k);

                    if (low_comm_juriga == null) {
                        low_comm_juriga = "";
                    }

                    String sb_bb = "N";
                    String bbriga = request.getParameter("bb" + k);
                    String sbriga = request.getParameter("sb" + k);
                    String sb_fidcoderiga = request.getParameter("fidcode" + k);

                    if (bbriga == null) {
                        bbriga = "N";
                    }
                    if (sbriga == null) {
                        sbriga = "N";
                    }
                    if (!bbriga.equals("N")) {
                        bbtot = true;
                        sb_fidcoderiga = "-";
                        sb_bb = bbriga;
                    } else {
                        if (!sbriga.equals("N")) {
                            sbtot = true;
                            sb_bb = sbriga;
                        }
                    }

                    if (sb_fidcoderiga == null) {
                        sb_fidcoderiga = "-";
                    } else {
                        sb_fidcoderiga = sb_fidcoderiga.trim();
                    }

                    String contr_valuta = cur_default[0];
                    String contr_supporto = kind_p1;
                    String contr_quantita = trans.getPay();

                    if (!spreadriga.equals("KO")) {
                        Ch_transaction_value chv = new Ch_transaction_value();
                        chv.setId(idriga);
                        chv.setCod_tr(codtr);
                        chv.setNumeroriga(numeroriga);
                        chv.setSupporto(supportoriga);
                        chv.setPos("-");
                        chv.setPosnum("-");
                        chv.setValuta(valutariga);
                        chv.setQuantita(formatDoubleforMysql(quantitariga));
                        chv.setRate(rateriga);
                        chv.setCom_perc(formatDoubleforMysql(com_percriga));
                        if (k == 1) {
                            newsellpercent = fd(formatDoubleforMysql(com_percriga));
                        } else if (fd(formatDoubleforMysql(com_percriga)) < newsellpercent) {
                            newsellpercent = fd(formatDoubleforMysql(com_percriga));
                        }
                        chv.setCom_perc_tot(formatDoubleforMysql(totpercriga));
                        chv.setFx_com(formatDoubleforMysql(fx_commriga));
                        chv.setTot_com(formatDoubleforMysql(totcomm1));
                        chv.setNet(removeLast(formatDoubleforMysql(net1)));
                        chv.setSpread(spreadriga);
                        chv.setTotal(formatDoubleforMysql(totalriga));
                        chv.setKind_fix_comm(kind_fix_commriga);
                        chv.setLow_com_ju(low_comm_juriga);
                        chv.setBb(sb_bb);
                        chv.setBb_fidcode(sb_fidcoderiga);
                        chv.setDt_tr(dt_tr);
                        chv.setContr_valuta(contr_valuta);
                        chv.setContr_supporto(contr_supporto);
                        chv.setContr_quantita(contr_quantita);
                        chv.setRoundvalue(roundvalue);
                        chv.setDel_fg("0");
                        chv.setDel_dt("1901-01-01 00:00:00");
                        list_row.add(chv);
                        spread_total = spread_total + fd(spreadriga);
                    }
                }
            }

            if (newsellpercent > 0) {
                cl.setPerc_sell(roundDoubleandFormat(newsellpercent, 2));
            } else {
                cl.setPerc_sell(oldpercent_sell);
            }
            cl.setPerc_buy(oldpercent_buy);

            if (ok.equals("0")) {
                if (list_row.isEmpty()) {
                    ok = "koins2";
                }
            }

            trans.setSpread_total(roundDoubleandFormat(spread_total, 2));

            trans.setBb(getStatus_BBSB(bbtot, sbtot));

            if (ok.equals("0")) {
                Db_Master db5 = new Db_Master();
                boolean es1 = db5.insert_transaction_change(trans, "ch_transaction_temp");
                db5.closeDB();
                if (es1) {

                    if (newintbook0.equals("1")) {
                        Db_Master dbib = new Db_Master();
                        boolean esib = dbib.insert_InternetBookingCH(cod, newintbookCanale, newintbookCodice, user);
                        dbib.closeDB();
                    }

                    sleeping(1000);
                    Db_Master db6 = new Db_Master();
                    Ch_transaction tmp = db6.query_transaction_ch_temp(cod);
                    boolean es2 = db6.insert_transaction_value(list_row);
                    db6.closeDB();
                    if (es2) {
//                        if (cz != null) {
                        cl.setRepceca(cz);
                        Db_Master db11a = new Db_Master();
                        // INSERISCO IN CLIENT TRANSACTION
                        boolean ins_1 = true;
                        if (is_CZ) {
                            ins_1 = db11a.insert_client_transaction(cz, "client_cz");
                        } else if (is_UK) {
                            ins_1 = db11a.insert_client_transaction(cz, "client_uk");
                        }
                        // INSERISCO IN CLIENT TRANSACTION
                        boolean ins_2 = db11a.insert_client_transaction(cl, cod);
                        db11a.closeDB();
                        if (!ins_1 || !ins_2) {
                            ok = "koins1";
                        }
//                        }

                        if (ok.equals("0")) {
                            if (!nc_taxfree.isEmpty()) {
                                Db_Master db12 = new Db_Master();
                                ArrayList<NC_category> list_nc1 = db12.list_nc_category_enabled();
                                ArrayList<NC_causal> list_nc2 = db12.list_nc_causal_enabled();
                                db12.closeDB();
                                for (int n = 0; n < nc_taxfree.size(); n++) {
                                    String[] val_nc = nc_taxfree.get(n);

                                    String cod_nc = "NC" + generaId(23);
                                    NC_causal nc2 = getNC_causal(list_nc2, val_nc[0], null);
                                    NC_category nc1 = getNC_category(list_nc1, nc2.getGruppo_nc());
                                    NC_transaction nct = new NC_transaction();
                                    nct.setCod(cod_nc);
                                    nct.setFiliale(filiale);
                                    nct.setUser(user);
                                    nct.setId_open_till(id_open_till);
                                    nct.setTill(till);
                                    nct.setNote("Tax Free No-Change Transaction: " + filiale + tmp.getId());
                                    nct.setGruppo_nc(nc1.getGruppo_nc());
                                    nct.setCausale_nc(nc2.getCausale_nc());
                                    nct.setPrezzo(nc2.getIp_prezzo_nc());
                                    nct.setFg_tipo_transazione_nc(nc2.getFg_tipo_transazione_nc());
                                    nct.setSupporto("01");
                                    nct.setValuta(cur_default[0]);

                                    nct.setPos("");
                                    nct.setPosnum("");

                                    nct.setQuantita((val_nc[2]));
                                    nct.setTotal(("-" + (val_nc[2])).trim());
                                    nct.setRicevuta(roundDoubleandFormat(fd(val_nc[1]), 0));
                                    nct.setFg_dogana("01"); //NO CUSTOMS
                                    nct.setCl_cognome(cl.getCognome());
                                    nct.setCl_nome(cl.getNome());
                                    nct.setCl_indirizzo(cl.getIndirizzo());
                                    nct.setCl_citta(cl.getCitta()); //FORMATTARE
                                    nct.setCl_nazione(cl.getNazione());//FORMATTARE
                                    nct.setCl_email(cl.getEmail());
                                    nct.setCl_telefono(cl.getTelefono());

                                    Db_Master db13 = new Db_Master();
                                    nct.setFg_inout(db13.getFg_inout_ncde(nct.getFg_tipo_transazione_nc(), nc2.getNc_de()));
                                    nct.setDel_dt(dt_tr);
                                    nct.setDel_user(user);
                                    nct.setDel_motiv("TRANSACTION NOT SAVED - INTERNATIONAL SANCTIONS YES");
                                    nct.setDel_fg("1");
                                    nct.setBonus(nc2.getBonus());
                                    nct.setCh_transaction(tmp.getCod());
                                    db13.insert_NC_transaction(nct);
                                    db13.closeDB();
                                    sleeping(1000);
                                }
                            }
                        }

                    }
                }
            }
        }

        if (ok.equals("0")) {
            Db_Master db8 = new Db_Master();
            db8.confirm_transaction_change(cod, filiale);
            db8.closeDB();

            Db_Master db9 = new Db_Master();
            db9.delete_transaction_ch(cod, "TRANSACTION NOT SAVED - INTERNATIONAL SANCTIONS YES", user);
            db9.closeDB();
            redirect(request, response, "transaction.jsp?esito=kodeleng");
        } else {
            elimina(user, cod);
            request.getSession().setAttribute("esito_s", ok);
            redirect(request, response, "transaction_s.jsp?esito=" + ok);
        }

    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void ch_tr_deletedENG(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String ok = "0";
        String user = (String) request.getSession().getAttribute("us_cod");
        if (user == null) {
            user = "9999";
        }

        Db_Master db = new Db_Master();
        String dt_tr = db.getNow();
        db.closeDB();
        String cod = request.getParameter("cod");

        String typerate = request.getParameter("typerate");
        String tipocliente = request.getParameter("tipocliente");
        String id_open_till = request.getParameter("id_open_till");
        String till = request.getParameter("till");
        String pay = request.getParameter("payout1");
        String total = request.getParameter("total0");
        String fix = request.getParameter("fix0");
        String com = request.getParameter("com0");
        String round = request.getParameter("round0");
        String commission = request.getParameter("commission0");
        String note = request.getParameter("note1");
        String agency = "0";
        String agency_cod = "-";
        String cl_cf = "---";
        String heavy_surname = request.getParameter("heavy_surname");
        String heavy_name = request.getParameter("heavy_name");
        String heavy_sex = request.getParameter("heavy_sex");
        if (heavy_sex == null || heavy_sex.equals("null")) {
            heavy_sex = "M";
        }

        String heavy_country = request.getParameter("heavy_country");
        String heavy_city = request.getParameter("heavy_city");
        String heavy_city_dis = request.getParameter("heavy_city_dis");
        String heavy_address = request.getParameter("heavy_address");
        if (heavy_address == null || heavy_address.equals("null")) {
            heavy_address = "-";
        }
        String heavy_zipcode = request.getParameter("heavy_zipcode");

        if (heavy_zipcode != null) {
            if (heavy_zipcode.trim().length() > 10) {
                heavy_zipcode = substring(heavy_zipcode.trim(), 0, 10);
            } else {
                heavy_zipcode = heavy_zipcode.trim();
            }
        }

        String heavy_district = request.getParameter("heavy_district");
        String heavy_district_dis = request.getParameter("heavy_district_dis");
        String heavy_pob_city = request.getParameter("heavy_pob_city");
        String heavy_pob_country = request.getParameter("heavy_pob_country");
        String heavy_pob_date = request.getParameter("heavy_pob_date");
        String heavy_identcard = request.getParameter("heavy_identcard");
        String heavy_numberidentcard = request.getParameter("heavy_numberidentcard");
        String heavy_issuedateidentcard = request.getParameter("heavy_issuedateidentcard");
        String heavy_exdateidentcard = request.getParameter("heavy_exdateidentcard");
        String heavy_issuedbyidentcard = request.getParameter("heavy_issuedbyidentcard");
        String heavy_issuedplaceidentcard = request.getParameter("heavy_issuedplaceidentcard");
        String heavy_email = request.getParameter("heavy_email");
        String heavy_phonenu = request.getParameter("heavy_phonenu");
        String heavy_pepI = "NO";
        String pr_nas1 = request.getParameter("heavy_pob_district");
        String cod_cliente = generaId(22) + filiale;
        String oldclient = request.getParameter("oldclient");

        //CZ
        String showanagVALUE = request.getParameter("showanagVALUE");
        String heavy_pno1 = request.getParameter("heavy_pno1");
        String heavy_cz_country = request.getParameter("heavy_cz_country");
        String heavy_cz_issuingcountry = request.getParameter("heavy_cz_issuingcountry");
        String heavy_sanctions = request.getParameter("heavy_sanctions");
        String heavy_pep = request.getParameter("heavy_pep");
        String heavy_transactionre = request.getParameter("heavy_transactionre");
        String heavy_moneysource = request.getParameter("heavy_moneysource");
        String heavy_occupation = request.getParameter("heavy_occupation");

        String pep_position = request.getParameter("pep_position");
        String pep_country = request.getParameter("pep_country");

        if (heavy_pep.equals("NO")) {
            pep_position = "";
            pep_country = "---";
        }

        boolean op_sos;
        if (request.getParameter("op_sos") == null) {
            op_sos = false;
        } else {
            op_sos = request.getParameter("op_sos").equals("on");
        }

        String oldpercent_buy = "-";
        String oldpercent_sell = "-";

        if (!oldclient.equals("0")) {
            cod_cliente = oldclient;
//            oldclient = "1";
        } else {
            Db_Master db1 = new Db_Master(true);
            if (db1.getC() == null) {
                db1 = new Db_Master();
            }
            String cl_check = db1.get_codice_client(cl_cf, tipocliente,
                    heavy_name, heavy_surname, heavy_pob_date, heavy_pob_country);
            if (cl_check != null) {
                cod_cliente = cl_check;
//                oldclient = "1";
            }
            db1.closeDB();
        }

        Client_CZ cz = new Client_CZ(cod_cliente, cod, showanagVALUE, heavy_pno1, heavy_cz_country,
                heavy_cz_issuingcountry, heavy_sanctions, heavy_pep, heavy_transactionre,
                heavy_moneysource, valueOf(op_sos), heavy_occupation, pep_position, pep_country);

        Ch_transaction trans = new Ch_transaction();
        trans.setTill(till);
        trans.setTipocliente(tipocliente);
        trans.setId_open_till(id_open_till);
        trans.setPay(formatDoubleforMysql(pay));
        trans.setTotal(formatDoubleforMysql(total));
        trans.setFix(formatDoubleforMysql(fix));
        trans.setCom(formatDoubleforMysql(com));
        trans.setRound(formatDoubleforMysql(round));
        trans.setCommission(formatDoubleforMysql(commission));
        trans.setNote(substring(getStringUTF8(note), 0, 100));
        trans.setAgency(agency);
        trans.setAgency_cod(agency_cod);
        trans.setCl_cf(cl_cf);
        trans.setDel_fg("0");
        trans.setDel_dt("1901-01-01 00:00:00");
        trans.setDel_user("-");
        trans.setDel_motiv("-");
        trans.setSpread_total("-");
        trans.setLocalfigures("-");
        trans.setPos("-");
        trans.setIntbook("0");
        trans.setIntbook_type("-");
        trans.setIntbook_1_mod("-");
        trans.setIntbook_1_tf("-");
        trans.setIntbook_1_val("-");
        trans.setIntbook_2_mod("-");
        trans.setIntbook_2_tf("-");
        trans.setIntbook_2_val("-");
        trans.setIntbook_3_mod("-");
        trans.setIntbook_3_tf("-");
        trans.setIntbook_3_val("-");
        trans.setIntbook_cli("-");
        trans.setIntbook_mac("-");
        trans.setData(dt_tr);
        trans.setFiliale(filiale);
        trans.setUser(user);
        trans.setCod(cod);
        trans.setCredccard_number("-");
        trans.setBl_status("2");
        trans.setBl_motiv("-");
        trans.setBl_status("2");
        trans.setBl_motiv("-");
        trans.setTipotr("B");
        trans.setCl_cod(cod_cliente);
        trans.setRefund("0");
        trans.setFa_number("-");
        trans.setCn_number("-");
        trans.setHeavy_pepI("NO");

////        CLIENTE
        Client cl = new Client();
        cl.setCode(cod_cliente);
        cl.setCognome(heavy_surname);
        cl.setNome(heavy_name);
        cl.setSesso(heavy_sex);
        cl.setCodfisc(cl_cf);
        cl.setNazione(heavy_country);
        String heavy_city1 = heavy_city;
        if (heavy_city1 == null) {
            heavy_city1 = heavy_city_dis;
        }
        cl.setCitta(heavy_city1);
        cl.setIndirizzo(heavy_address);
        cl.setCap(heavy_zipcode);
        String pro = heavy_district;
        if (pro == null) {
            pro = heavy_district_dis;
            if (pro == null) {
                pro = "-";
            }
        }
        cl.setProvincia(pro);
        cl.setCitta_nascita(heavy_pob_city);
        cl.setProvincia_nascita(pr_nas1);
        cl.setNazione_nascita(heavy_pob_country);
        cl.setDt_nascita(heavy_pob_date);
        cl.setTipo_documento(heavy_identcard);
        cl.setNumero_documento(heavy_numberidentcard);
        cl.setDt_rilascio_documento(heavy_issuedateidentcard);
        cl.setDt_scadenza_documento(heavy_exdateidentcard);
        cl.setRilasciato_da_documento(heavy_issuedbyidentcard);
        cl.setLuogo_rilascio_documento(heavy_issuedplaceidentcard);
        cl.setEmail(heavy_email);
        cl.setTelefono(heavy_phonenu);

        cl.setTimestamp(dt_tr);

        cl.setPep(heavy_pepI);

        boolean bbtot = false;
        boolean sbtot = false;

        ArrayList<Ch_transaction_value> list_row = new ArrayList<>();

        double newbuypercent = 0.00;

        for (int i = 1; i < 6; i++) {

            String idriga = generaId(25);
            String codtr = trans.getCod();

            String spreadriga = "-";
            String numeroriga = valueOf(i);
            String supportoriga = request.getParameter("kind" + i);
            String posriga = request.getParameter("posvalue" + i);
            String posnumriga = request.getParameter("posnum" + i);
            String valutariga = request.getParameter("figs" + i);
            String quantitariga = request.getParameter("quantity" + i);

            String rateriga = request.getParameter("rate" + i);
            if (typerate.equals("UNLOCK")) {
                rateriga = formatDoubleforMysql(request.getParameter("fieldrate" + i));
            }
            rateriga = removeLast(rateriga);

            String roundvalue = request.getParameter("roundvalue" + i);

            String com_percriga = request.getParameter("comperc" + i);
            String com_perc_totriga = request.getParameter("totperc" + i);
            String fx_commriga = request.getParameter("fxcomm" + i);
            String tot_comriga = request.getParameter("totcomm" + i);
            String netriga = request.getParameter("net" + i);

            String totalriga = request.getParameter("total" + i);
            String kind_fix_commriga = request.getParameter("kindfixcomm" + i);
            if (kind_fix_commriga == null) {
                kind_fix_commriga = "-";
            }
            String low_comm_juriga = request.getParameter("lowcommjus" + i);

            if (posriga == null) {
                posriga = "N";
            }
            if (posnumriga == null) {
                posnumriga = "";
            }
            if (low_comm_juriga == null) {
                low_comm_juriga = "";
            }

            String sb_bb = "N";
            String bbriga = request.getParameter("bb" + i);
            String sbriga = request.getParameter("sb" + i);
            String bb_fidcoderiga = request.getParameter("fidcode" + i);

            if (bbriga == null) {
                bbriga = "N";
            }

            if (sbriga == null) {
                sbriga = "N";
            }

            if (!bbriga.equals("N")) {
                bbtot = true;
                sb_bb = bbriga;
            } else {
                if (!sbriga.equals("N")) {
                    sbtot = true;
                    sb_bb = sbriga;
                    bb_fidcoderiga = "-";
                }
            }

            if (bb_fidcoderiga == null) {
                bb_fidcoderiga = "-";
            } else {
                bb_fidcoderiga = bb_fidcoderiga.trim();
            }

            String contr_valuta = "GBP";
            String contr_supporto = "01";
            String contr_quantita = trans.getPay();

            if (!formatDoubleforMysql(totalriga).equals("0.00")) {
                Ch_transaction_value chv = new Ch_transaction_value();
                chv.setId(idriga);
                chv.setCod_tr(codtr);
                chv.setNumeroriga(numeroriga);
                chv.setSupporto(supportoriga);
                chv.setPos(posriga);
                chv.setPosnum(posnumriga);
                chv.setValuta(valutariga);
                chv.setQuantita(formatDoubleforMysql(quantitariga));
                chv.setRate(rateriga);
                chv.setCom_perc(formatDoubleforMysql(com_percriga));
                if (i == 1) {
                    newbuypercent = fd(formatDoubleforMysql(com_percriga));
                } else if (fd(formatDoubleforMysql(com_percriga)) < newbuypercent) {
                    newbuypercent = fd(formatDoubleforMysql(com_percriga));
                }

                chv.setCom_perc_tot(formatDoubleforMysql(com_perc_totriga));
                chv.setFx_com(formatDoubleforMysql(fx_commriga));
                chv.setTot_com(formatDoubleforMysql(tot_comriga));
                chv.setNet(removeLast(formatDoubleforMysql(netriga)));
                chv.setSpread(spreadriga);
                chv.setTotal(formatDoubleforMysql(totalriga));
                chv.setKind_fix_comm(kind_fix_commriga);
                chv.setLow_com_ju(low_comm_juriga);

                chv.setBb(sb_bb);
                chv.setBb_fidcode(bb_fidcoderiga);
                chv.setDt_tr(dt_tr);
                chv.setContr_valuta(contr_valuta);
                chv.setContr_supporto(contr_supporto);
                chv.setContr_quantita(contr_quantita);
                chv.setRoundvalue(roundvalue);
                chv.setDel_fg("0");
                chv.setDel_dt("1901-01-01 00:00:00");
                list_row.add(chv);
            }

        }

        if (newbuypercent > 0) {
            cl.setPerc_buy(roundDoubleandFormat(newbuypercent, 2));
        } else {
            cl.setPerc_buy(oldpercent_buy);
        }
        cl.setPerc_sell(oldpercent_sell);

        if (ok.equals("0")) {
            if (list_row.isEmpty()) {
                ok = "koins2";
            }
        }

        trans.setBb(getStatus_BBSB(bbtot, sbtot));

        if (ok.equals("0")) {
            Db_Master db6 = new Db_Master();
            boolean es1 = db6.insert_transaction_change(trans, "ch_transaction_temp");
            db6.closeDB();
            if (es1) {
                Db_Master db7 = new Db_Master();
                boolean es2 = db7.insert_transaction_value(list_row);
                db7.closeDB();
                if (es2) {
                    cl.setRepceca(cz);
                    Db_Master db11a = new Db_Master();
                    // INSERISCO IN CLIENT TRANSACTION
                    boolean ins_1 = true;
                    if (is_CZ) {
                        ins_1 = db11a.insert_client_transaction(cz, "client_cz");
                    } else if (is_UK) {
                        ins_1 = db11a.insert_client_transaction(cz, "client_uk");
                    }
                    // INSERISCO IN CLIENT TRANSACTION
                    boolean ins_2 = db11a.insert_client_transaction(cl, cod);
                    db11a.closeDB();
                    if (!ins_1 || !ins_2) {
                        ok = "koins1";
                    }
                } else {
                    ok = "koins2";
                }
            } else {
                ok = "koins3";
            }
        }

        if (ok.equals("0")) {
            Db_Master db8 = new Db_Master();
            db8.confirm_transaction_change(cod, filiale);
            db8.closeDB();
            Db_Master db9 = new Db_Master();
            db9.delete_transaction_ch(cod, "TRANSACTION NOT SAVED - INTERNATIONAL SANCTIONS YES", user);
            db9.closeDB();
            redirect(request, response, "transaction.jsp?esito=kodeleng");
        } else {
            elimina(user, cod);
            request.getSession().setAttribute("esito_b", ok);
            redirect(request, response, "transaction_b.jsp?esito=" + ok);
        }

    }

    private boolean generateInvoice(Ch_transaction it, String table) {
        if (!is_IT) {
            return true;
        }
        Db_Master db = new Db_Master();
        CustomerKind ck = db.get_customerKind(it.getTipocliente());
        db.closeDB();
        boolean generate = false;

        if (ck.getTipofat().equals("0")) { //invoice
            if ((fd(it.getCommission()) + parseDoubleR(it.getRound())) > 0) {
                generate = true;
            }
        }
        if (generate) {
            Db_Master db1 = new Db_Master();
            String invoice_number = db1.get_invoice_number(it.getCod());
            db1.closeDB();

            Db_Master db2 = new Db_Master();
            boolean es1 = db2.insert_inv_list(invoice_number, it.getCod(), "F", "1", it.getData());
            if (es1) {
                es1 = db2.update_invoice_transaction(it.getCod(), table, "fa_number", invoice_number);
            }
            db2.closeDB();
            return es1;
        } else {
            return true;
        }
    }

    private void insertStock_Pay(Ch_transaction trans, String cur_default, String kind, double pay) {
        Ch_transaction cc = query_transaction_ch_temp(trans.getCod());
        if (cc != null) {
            Stock st1 = new Stock();
            st1.setCodice("ST" + generaId(48));
            st1.setFiliale(cc.getFiliale());
            st1.setTipo("CH");
            st1.setTill(cc.getTill());
            st1.setIdoperation(cc.getCod());
            st1.setCodiceopenclose(cc.getId_open_till());
            st1.setTipostock("CH");
            st1.setCod_value(cur_default);
            st1.setKind(kind);
            st1.setTotal(roundDoubleandFormat(pay, 2));
            st1.setRate("1.00000000");
            st1.setControval(roundDoubleandFormat(pay, 2));
            st1.setUser(cc.getUser());
            st1.setDate(cc.getData());
            st1.setId_op(cc.getFiliale() + cc.getId());
            Db_Master db1 = new Db_Master();
            db1.insertStock(st1);
            db1.closeDB();
        }
    }

    private void removeStock_Pay(Ch_transaction trans, String cur_default, String kind, double pay) {
        Ch_transaction cc = query_transaction_ch_temp(trans.getCod());
        if (cc != null) {
            Stock st1 = new Stock();
            st1.setCodice("ST" + generaId(48));
            st1.setFiliale(cc.getFiliale());
            st1.setTipo("CH");
            st1.setTill(cc.getTill());
            st1.setIdoperation(cc.getCod());
            st1.setCodiceopenclose(cc.getId_open_till());
            st1.setTipostock("CH");
            st1.setCod_value(cur_default);
            st1.setKind(kind);
            st1.setTotal(roundDoubleandFormat(-pay, 2));
            st1.setRate("1.00000000");
            st1.setControval(roundDoubleandFormat(-pay, 2));
            st1.setUser(cc.getUser());
            st1.setDate(cc.getData());
            st1.setId_op(cc.getFiliale() + cc.getId());
            Db_Master db1 = new Db_Master();
            db1.insertStock(st1);
            db1.closeDB();
        }
    }

    private void insertStock_change(Ch_transaction trans, ArrayList<Ch_transaction_value> list_row) {
        Ch_transaction cc = query_transaction_ch_temp(trans.getCod());
        if (cc != null) {
            for (int i = 0; i < list_row.size(); i++) {
                Ch_transaction_value chv = list_row.get(i);
                Stock st1 = new Stock();
                st1.setCodice("ST" + generaId(48));
                st1.setFiliale(cc.getFiliale());
                st1.setTipo("CH");
                st1.setTill(cc.getTill());
                st1.setIdoperation(cc.getCod());
                st1.setCodiceopenclose(cc.getId_open_till());
                st1.setTipostock("CH");
                st1.setCod_value(chv.getValuta());
                st1.setKind(chv.getSupporto());
                st1.setTotal(chv.getQuantita());
                st1.setRate(chv.getRate());
                st1.setControval(chv.getTotal());
                st1.setUser(cc.getUser());
                st1.setDate(cc.getData());
                st1.setId_op(cc.getFiliale() + cc.getId());
                Db_Master db1 = new Db_Master();
                db1.insertStock(st1);
                db1.closeDB();
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
    protected void testopenie(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        getRuntime().exec("iexplore.exe http://google.com/");
    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void verificaID_OC_SINGLE(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        Utility.printRequest(request);

        String tillfrom = request.getParameter("tillfrom");
        String idfrom = request.getParameter("idfrom");

        Db_Master db = new Db_Master();
        String actual_id_F = db.getCodLastOpenclose(tillfrom);
        db.closeDB();

        String msg = "OK";

        if (actual_id_F == null) {
            msg = "Try again. Check 'Id open/close'. Please refresh this page.";
        } else {
            if (!idfrom.trim().equalsIgnoreCase(actual_id_F.trim())) {
                msg = "Try again. Check 'Id open/close'. Please refresh this page.";
            }
        }

        try (PrintWriter out = response.getWriter()) {
            out.print(msg);
        }

    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void verificalevelratepersoglia(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String max = formatDoubleforMysql(getRequestValue(request, "max"));
        int indexrate = parseIntR(getRequestValue(request, "indexrate")) + 1;

        Db_Master db = new Db_Master();
        double percent = db.getPercent_levelrate(indexrate);
        db.closeDB();

        String result = roundDoubleandFormat(fd(max) - (fd(max) * percent / 100.00), 2);

        try (PrintWriter out = response.getWriter()) {
            out.print(result);
        }
    }

    protected void verificaBLM(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String cod = request.getParameter("cod");
        Db_Master db = new Db_Master();
        String msg = db.get_TextBlMacc(cod);
        db.closeDB();

        try (PrintWriter out = response.getWriter()) {
            if (msg == null) {
                out.print("RAFOK");
            } else {
                out.print(msg);
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
    protected void verificaID_OC(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String tillfrom = request.getParameter("tillfrom");
        String idfrom = request.getParameter("idfrom");
        String tillto = request.getParameter("tillto");
        String idto = request.getParameter("idto");

        Db_Master db = new Db_Master();
        String actual_id_F = db.getCodLastOpenclose(tillfrom);
        String actual_id_T = db.getCodLastOpenclose(tillto);
        db.closeDB();

        String msg = "OK";

        if (actual_id_F == null) {
            msg = "Try again. Check 'Id open till - From'. Please refresh this page.";
        } else {
            if (!idfrom.trim().equalsIgnoreCase(actual_id_F.trim())) {
                msg = "Try again. Check 'Id open till - From'. Please refresh this page.";
            }
        }

        if (actual_id_T == null) {
            msg = "Try again. Check 'Id open till - To'. Please refresh this page.";
        } else {
            if (!idto.trim().equalsIgnoreCase(actual_id_T.trim())) {
                msg = "Try again. Check 'Id open till - To'. Please refresh this page.";
            }
        }

        try (PrintWriter out = response.getWriter()) {
            out.print(msg);
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
                switch (type) {
                    case "ch_tr_buy":
                        insertTR("W", (String) request.getSession().getAttribute("us_cod"), (String) request.getSession().getAttribute("us_fil") + " - TRANSAZIONE CHANGE BUY");
                        transaction_Ch_BUY(request, response);
                        break;
                    case "ch_tr_sell":
                        insertTR("W", (String) request.getSession().getAttribute("us_cod"), (String) request.getSession().getAttribute("us_fil") + " - TRANSAZIONE CHANGE SELL");
                        transaction_Ch_SELL(request, response);
                        break;
                    case "ch_tr_sell_webtr":
                        insertTR("W", (String) request.getSession().getAttribute("us_cod"), (String) request.getSession().getAttribute("us_fil") + " - TRANSAZIONE CHANGE SELL WEB");
                        transaction_Ch_SELL_webtrans(request, response);
                        break;
                    case "ch_tr_buy_webtr":
                        insertTR("W", (String) request.getSession().getAttribute("us_cod"), (String) request.getSession().getAttribute("us_fil") + " - TRANSAZIONE CHANGE BUY WEB");
                        transaction_Ch_BUY_webtrans(request, response);
                        break;
                    case "conf_ch_tr_buy":
                        insertTR("W", (String) request.getSession().getAttribute("us_cod"), (String) request.getSession().getAttribute("us_fil") + " - TRANSAZIONE CHANGE CONFERMA");
                        conf_ch_tr_buy(request, response);
                        break;
                    case "del_ch_tr_buy":
                        insertTR("W", (String) request.getSession().getAttribute("us_cod"), (String) request.getSession().getAttribute("us_fil") + " - TRANSAZIONE CHANGE NON CONFERMATA");
                        del_ch_tr_buy(request, response);
                        break;
                    case "get_spread":
                        get_spread(request, response);
                        break;
                    case "check_changerate":
                        insertTR("W", (String) request.getSession().getAttribute("us_cod"), (String) request.getSession().getAttribute("us_fil") + " - CHANGE RATE");
                        check_changerate(request, response);
                        break;
                    case "vercfanno":
                        insertTR("W", (String) request.getSession().getAttribute("us_cod"), (String) request.getSession().getAttribute("us_fil") + " - TRANSAZIONE CHANGE CONFERM DOC");
                        vercfanno(request, response);
                        break;
                    case "testopenie":
                        testopenie(request, response);
                        break;
                    case "verifyquantnoch":
                        verifyquantnoch(request, response);
                        break;
                    case "controllaoccupato":
                        controllaoccupato(request, response);
                        break;
                    case "verificasoglianagraficaUK":
                        verificasoglianagraficaUK(request, response);
                        break;
                    case "verificasoglianagrafica":
                        verificasoglianagrafica(request, response);
                        break;
                    case "verificaBLM":
                        verificaBLM(request, response);
                        break;
                    case "verificalevelratepersoglia":
                        verificalevelratepersoglia(request, response);
                        break;
                    case "verificaID_OC_SINGLE":
                        verificaID_OC_SINGLE(request, response);
                        break;
                    case "verificaID_OC":
                        verificaID_OC(request, response);
                        break;
                    case "controllaoccupato_till":
                        controllaoccupato_till(request, response);
                        break;
                    case "ch_tr_deletedENG":
                        ch_tr_deletedENG(request, response);
                        break;
                    case "updatesito":
                        updatesito(request, response);
                        break;
                    case "ch_tr_deletedENGSELL":
                        ch_tr_deletedENGSELL(request, response);
                        break;
                    default:
                        break;
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
