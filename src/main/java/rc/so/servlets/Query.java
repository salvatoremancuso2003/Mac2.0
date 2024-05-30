package rc.so.servlets;

import com.google.gson.Gson;
import rc.so.db.Db_Loy;
import rc.so.db.Db_Master;
import rc.so.entity.Agency;
import rc.so.entity.Booking;
import rc.so.entity.Branch;
import rc.so.entity.Ch_transaction;
import static rc.so.entity.Ch_transaction.formatType;
import static rc.so.entity.Ch_transaction.format_BB_SB;
import rc.so.entity.Ch_transaction_doc;
import rc.so.entity.Ch_transaction_refund;
import rc.so.entity.Ch_transaction_value;
import rc.so.entity.City;
import rc.so.entity.Client;
import static rc.so.entity.Client_CZ.formatYN;
import rc.so.entity.Company;
import rc.so.entity.Currency;
import rc.so.entity.CustomerKind;
import rc.so.entity.ET_change;
import static rc.so.entity.ET_change.format_tofrom_brba;
import rc.so.entity.IT_change;
import rc.so.entity.NC_category;
import rc.so.entity.NC_causal;
import rc.so.entity.NC_transaction;
import rc.so.entity.Newsletters;
import rc.so.entity.Office;
import rc.so.entity.Openclose;
import rc.so.entity.Till;
import rc.so.entity.Users;
import static rc.so.util.Constant.ipcentral;
import static rc.so.util.Constant.is_CZ;
import static rc.so.util.Constant.is_IT;
import static rc.so.util.Constant.patternnormdate;
import static rc.so.util.Constant.patternsqldate;
import static rc.so.util.Constant.patternnormdate_filter;
import static rc.so.util.Constant.patternsql;
import static rc.so.util.Engine.checkSogliaValueofTransaction_client;
import static rc.so.util.Engine.formatALCurrency;
import static rc.so.util.Engine.formatALNC_category;
import static rc.so.util.Engine.formatALNC_causal;
import static rc.so.util.Engine.formatALNC_causal_ncde;
import static rc.so.util.Engine.formatBankBranch;
import static rc.so.util.Engine.formatStatus_general;
import static rc.so.util.Engine.getCity_apm;
import static rc.so.util.Engine.getConf;
import static rc.so.util.Engine.getFil;
import static rc.so.util.Engine.getNC_category;
import static rc.so.util.Engine.getNC_causal;
import static rc.so.util.Engine.get_Agent_company;
import static rc.so.util.Engine.get_Branch;
import static rc.so.util.Engine.get_ET_change_value;
import static rc.so.util.Engine.get_ET_nochange_value;
import static rc.so.util.Engine.get_customerKind;
import static rc.so.util.Engine.get_district;
import static rc.so.util.Engine.get_soglia_CZ;
import static rc.so.util.Engine.insertTR;
import static rc.so.util.Engine.isCentral;
import static rc.so.util.Engine.listaDocumentiAccettati;
import static rc.so.util.Engine.sito_stati;
import static rc.so.util.Engine.user_kyc;
import static rc.so.util.Engine.verifySession;
import static rc.so.util.Utility.correggi;
import static rc.so.util.Utility.fd;
import static rc.so.util.Utility.formatAL;
import static rc.so.util.Utility.formatDoubleforMysql;
import static rc.so.util.Utility.formatMysqltoDisplay;
import static rc.so.util.Utility.formatStringtoStringDate;
import static rc.so.util.Utility.formatStringtoStringDate_null;
import static rc.so.util.Utility.formatUTFtoLatin;
import static rc.so.util.Utility.getDT;
import static rc.so.util.Utility.getRequestValue;
import static rc.so.util.Utility.parseArrayValues;
import static rc.so.util.Utility.pingIPONLINE;
import static rc.so.util.Utility.redirect;
import static rc.so.util.Utility.removeDuplicatesAL;
import static rc.so.util.Utility.replaceApici;
import static rc.so.util.Utility.roundDoubleandFormat;
import static rc.so.util.Utility.visualizzaStringaMySQL;
import java.io.IOException;
import java.io.PrintWriter;
import static java.lang.Integer.MAX_VALUE;
import static java.lang.Thread.currentThread;
import java.util.ArrayList;
import java.util.List;
import static java.util.stream.Collectors.toList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import static org.apache.commons.lang3.StringUtils.replace;
import org.joda.time.DateTime;
import rc.so.util.Utility;

/**
 *
 * @author rcosco
 */
public class Query extends HttpServlet {

    /**
     *
     */
    public static final String filiale = getFil()[0];

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void query_nc_cat(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/plain; charset=ISO-8859-1");
        response.setCharacterEncoding("ISO-8859-1");
        String kind_1 = request.getParameter("kind_1");
        String search = request.getParameter("search");
        Db_Master db = new Db_Master();

        boolean central = isCentral();
        String tipo = (String) request.getSession().getAttribute("us_tip");
        if (tipo == null) {
            tipo = "";
        }

//        boolean central = true;
        ArrayList<NC_category> result = db.query_nc_category(kind_1);
        ArrayList<String[]> array_nc_kind = db.nc_kind();
        db.closeDB();
        try (PrintWriter out = response.getWriter()) {
            String inizio = "{ \"aaData\": [";
            String fine = "] }";
            String valore = "";
            if (result.size() > 0) {
                for (int i = 0; i < result.size(); i++) {
                    NC_category res = (NC_category) result.get(i);
                    String l1;
                    if (central && tipo.equals("3")) {
                        l1 = "<a href='nc_edit_cat.jsp?view=0&nc_code=" + res.getGruppo_nc() + "' class='btn btn-xs blue btn-outline btn-circle   fancyBoxRaf' "
                                + " container='body' data-trigger='hover' data-container='body' data-placement='top' data-content='Edit'><i class='fa fa-wrench'></i></a>";
                    } else {
                        l1 = "<a href='nc_edit_cat.jsp?view=1&nc_code=" + res.getGruppo_nc() + "' class='btn btn-xs blue btn-outline btn-circle popovers fancyBoxRaf' "
                                + " container='body' data-trigger='hover' data-container='body' data-placement='top' data-content='View'><i class='fa fa-eye'></i></a>";
                    }
                    String l2 = "<a href='nc_category.jsp?kind_1=" + kind_1 + "&search=" + search + "&show=" + search + "&codnc=" + res.getGruppo_nc() + "'" + " class='btn btn-xs blue btn-outline btn-circle popovers' container='body' data-trigger='hover' data-container='body'" + " data-placement='top' data-content='Show Causal'><i class='fa fa-search'></i></a>";
                    String az = "<div class='btn-group'>" + l1 + l2 + "</div>";
                    String s1 = replace(visualizzaStringaMySQL(res.getDe_gruppo_nc()), "€", "&#0128;");
                    valore = valore + " [ \"" + res.getGruppo_nc() + "\",\""
                            + s1 + "\",\"" + formatAL(res.getFg_tipo_transazione_nc(), array_nc_kind, 1) + "\",\"" + formatMysqltoDisplay(Utility.sanitize(res.getIp_prezzo_nc())) + "\",\"" + Utility.sanitize(res.formatStatus(res.getAnnullato())) + "\",\"" + az + "\"],";
                }
                String x = inizio + valore.substring(0, valore.length() - 1) + fine;
                out.print(x);
            } else {
                out.print(inizio + fine);
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
    protected void query_nc_caus(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain; charset=ISO-8859-1");
        response.setCharacterEncoding("ISO-8859-1");
        String codnc = request.getParameter("codnc");
        Db_Master db = new Db_Master();
        boolean central = isCentral();
        String tipo = (String) request.getSession().getAttribute("us_tip");
        if (tipo == null) {
            tipo = "";
        }

        ArrayList<NC_causal> result = db.query_nc_causal(codnc);
        db.closeDB();
        try (PrintWriter out = response.getWriter()) {
            String inizio = "{ \"aaData\": [";
            String fine = "] }";
            String valore = "";
            if (result.size() > 0) {
                for (int i = 0; i < result.size(); i++) {
                    NC_causal res = (NC_causal) result.get(i);
                    String az;
                    if (central && tipo.equals("3")) {
                        az = "<a href='nc_edit_cas.jsp?view=0&nc_code=" + res.getCausale_nc() + "' class='btn btn-sm blue-dark btn-outline btn-circle fancyBoxRaf'><i class='fa fa-wrench'></i> Edit</a>";
                    } else {
                        az = "<a href='nc_edit_cas.jsp?view=1&nc_code=" + res.getCausale_nc() + "' class='btn btn-sm blue-dark btn-outline btn-circle fancyBoxRaf'><i class='fa fa-eye'></i> View</a>";
                    }   //String s1 = visualizzaStringaMySQL(res.getDe_causale_nc());
                    String s1 = replace(visualizzaStringaMySQL(res.getDe_causale_nc()), "€", "&#0128;");
                    valore = valore + " [ \"" + res.getCausale_nc() + "\",\"" + s1
                            + "\",\"" + formatMysqltoDisplay(Utility.sanitize(res.getIp_prezzo_nc())) + "\",\""
                            + res.formatStatus(res.getAnnullato()) + "\",\"" + az + "\"],";
                }
                String x = inizio + valore.substring(0, valore.length() - 1) + fine;
                out.print(x);
            } else {
                out.print(inizio + fine);
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
    protected void query_tb_city(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain; charset=ISO-8859-1");
        response.setCharacterEncoding("ISO-8859-1");
        Db_Master db = new Db_Master();
        ArrayList<String[]> result = db.listcity();

        boolean central = isCentral();
        String tipo = (String) request.getSession().getAttribute("us_tip");
        if (tipo == null) {
            tipo = "";
        }

        db.closeDB();
        try (PrintWriter out = response.getWriter()) {
            String inizio = "{ \"aaData\": [";
            String fine = "] }";
            String valore = "";
            if (result.size() > 0) {
                for (int i = 0; i < result.size(); i++) {
                    String[] res = (String[]) result.get(i);

                    String az = "";
                    if (central && tipo.equals("3")) {
                        az = "<a href='tb_edit_city.jsp?cit_code=" + res[0] + "' class='btn btn-sm blue btn-outline btn-circle fancyBoxRaf'><i class='fa fa-wrench'></i> Edit</a>";
                    }

                    valore = valore + " [ \"" + res[0] + "\",\"" + res[1] + "\",\"" + az + "\"],";
                }

                String x = inizio + valore.substring(0, valore.length() - 1) + fine;
                out.print(x);
            } else {
                out.print(inizio + fine);
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
    protected void query_tb_spread(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain; charset=ISO-8859-1");
        response.setCharacterEncoding("ISO-8859-1");

        try (PrintWriter out = response.getWriter()) {
            String inizio = "{ \"aaData\": [";
            String fine = "] }";
            String valore = "";
            Db_Master db = new Db_Master();
            ArrayList<Branch> array_branch = db.list_branch_enabled();
            String listbranch = request.getParameter("listbranch");
            if (listbranch == null || listbranch.equals("null")) {
                listbranch = "";
                for (int j = 0; j < array_branch.size(); j++) {
                    listbranch = listbranch + array_branch.get(j).getCod() + ";";
                }
            }

            ArrayList<Currency> result = db.list_figures_query_edit(listbranch.split(";")[0]);
            if (result.isEmpty()) {
                result = db.list_figures_query_edit("000");
            }

            db.closeDB();

            if (result.size() > 0) {
                for (int i = 0; i < result.size(); i++) {
                    Currency res = (Currency) result.get(i);
                    String az = "<a href='tb_edit_spread.jsp?cur_code=" + res.getCode() + "&listbranch=" + listbranch + "' "
                            + "class='btn btn-sm btn-outline blue btn-circle fancyBoxRafreload'><i class='fa fa-wrench'></i> Edit</a></li>";
                    valore = valore + " [ \"" + res.getCode() + "\",\"" + res.getDescrizione() + "\",\""
                            + formatMysqltoDisplay(res.getCambio_bce()) + "\",\"" + az + "\"],";
                }

                String x = inizio + valore.substring(0, valore.length() - 1) + fine;
                out.print(x);
            } else {
                out.print(inizio + fine);
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
    protected void query_tb_currency(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain; charset=ISO-8859-1");
        response.setCharacterEncoding("ISO-8859-1");
        Db_Master db = new Db_Master();
        ArrayList<Branch> array_branch = db.list_branch_enabled();
        String[] fil = db.getCodLocal(false);
        Branch b = db.get_branch(fil[0]);
        ArrayList<Currency> result = db.list_figures_query_edit(fil[0]);
        db.closeDB();
        try (PrintWriter out = response.getWriter()) {
            String inizio = "{ \"aaData\": [";
            String fine = "] }";
            String valore = "";
            boolean central = Boolean.valueOf(request.getParameter("central"));
            boolean branchmodrate = false;
            String editce = request.getParameter("editce");
            if (editce == null) {
                editce = "0";
            }
            if (central) {
                editce = "1";
            }
            String listbranch = fil[0] + ";";
            if (editce.equals("1")) {
                listbranch = request.getParameter("listbranch");
                if (listbranch == null || listbranch.equals("null")) {
                    listbranch = "";
                    for (int j = 0; j < array_branch.size(); j++) {
                        listbranch = listbranch + array_branch.get(j).getCod() + ";";
                    }
                }
            } else {
                branchmodrate = b.getFg_modrate().equals("1");
            }
            if (result.size() > 0) {
                for (int i = 0; i < result.size(); i++) {
                    Currency res = (Currency) result.get(i);
                    String az;
                    if (!central) {
                        if (editce.equals("1")) {
                            az = "<a href='tb_edit_currency.jsp?view=0&editce=" + editce + "&cur_code=" + res.getCode() + "&listbranch=" + listbranch + "' "
                                    + "class='btn btn-sm btn-outline blue btn-circle fancyBoxRafreload'><i class='fa fa-wrench'></i> Edit</a></li>";
                        } else if (branchmodrate) {
                            az = "<a href='tb_edit_currency.jsp?view=0&editce=" + editce + "&cur_code=" + res.getCode() + "&listbranch=" + listbranch + "' "
                                    + "class='btn btn-sm btn-outline blue btn-circle fancyBoxRafreload'><i class='fa fa-wrench'></i> Edit</a></li>";
                        } else {
                            az = "<a href='tb_edit_currency.jsp?view=1&editce=" + editce + "&cur_code=" + res.getCode() + "&listbranch=" + listbranch + "' "
                                    + "class='btn btn-sm btn-outline blue btn-circle fancyBoxRaf'><i class='fa fa-eye'></i> View</a></li>";
                        }
                    } else {
                        az = "<a href='tb_edit_currency.jsp?view=0&editce=" + editce + "&cur_code=" + res.getCode() + "&listbranch=" + listbranch + "' "
                                + "class='btn btn-sm btn-outline blue btn-circle fancyBoxRafreload'><i class='fa fa-wrench'></i> Edit</a></li>";
                    }
                    valore = valore + " [ \"" + res.getCode() + "\",\"" + res.getDescrizione() + "\",\""
                            + formatMysqltoDisplay(res.getCambio_bce()) + "\",\"" + az + "\"],";
                }

                String x = inizio + valore.substring(0, valore.length() - 1) + fine;
                out.print(x);
            } else {
                out.print(inizio + fine);
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
    protected void query_tb_agency(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain; charset=ISO-8859-1");
        response.setCharacterEncoding("ISO-8859-1");
        Db_Master db = new Db_Master();
        ArrayList<Agency> result = db.list_agency();
        db.closeDB();
        boolean central = isCentral();
        String tipo = (String) request.getSession().getAttribute("us_tip");
        if (tipo == null) {
            tipo = "";
        }

        try (PrintWriter out = response.getWriter()) {
            String inizio = "{ \"aaData\": [";
            String fine = "] }";
            String valore = "";
            if (result.size() > 0) {
                for (int i = 0; i < result.size(); i++) {
                    Agency ck = (Agency) result.get(i);
                    String az;
                    if (central && tipo.equals("3")) {
                        az = "<a href='tb_edit_agency.jsp?view=0&ag_code=" + ck.getAgenzia() + "' class='btn btn-sm btn-outline blue btn-circle fancyBoxRafreload'><i class='fa fa-wrench'></i> Edit</a></li>";
                    } else {
                        az = "<a href='tb_edit_agency.jsp?view=1&ag_code=" + ck.getAgenzia() + "' class='btn btn-sm btn-outline blue btn-circle fancyBoxRaf'><i class='fa fa-eye'></i> View</a></li>";
                    }
                    valore = valore + " [ \"" + ck.getAgenzia() + "\",\"" + ck.getDe_agenzia() + "\",\"" + formatStatus_general(ck.getFg_annullato()) + "\",\"" + az + "\"],";
                }
                String x = inizio + valore.substring(0, valore.length() - 1) + fine;
                out.print(x);
            } else {
                out.print(inizio + fine);
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
    protected void query_tb_only_company(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain; charset=ISO-8859-1");
        response.setCharacterEncoding("ISO-8859-1");
        Db_Master db = new Db_Master();
        ArrayList<Company> result = db.list_only_company();
        boolean central = isCentral();
        String tipo = (String) request.getSession().getAttribute("us_tip");
        if (tipo == null) {
            tipo = "";
        }

        db.closeDB();
        try (PrintWriter out = response.getWriter()) {
            String inizio = "{ \"aaData\": [";
            String fine = "] }";
            String valore = "";
            if (result.size() > 0) {
                for (int i = 0; i < result.size(); i++) {
                    Company ck = (Company) result.get(i);
                    ArrayList<Company> listAgent = get_Agent_company(ck.getNdg());
                    String link2;
                    if (central && tipo.equals("3")) {
                        link2 = "<li><a href='tb_edit_company.jsp?view=0&co_code=" + ck.getNdg() + "' class='fancyBoxRafreload'><i class='fa fa-wrench'></i> Edit </a></li>";
                    } else {
                        link2 = "<li><a href='tb_edit_company.jsp?view=1&co_code=" + ck.getNdg() + "' class='fancyBoxRaf'><i class='fa fa-eye'></i> View </a></li>";

                    }
                    String link3 = "";
                    if (listAgent.size() < 2 && central && tipo.equals("3")) {
                        link3 = "<li><a href='tb_edit_agent.jsp?co_code=" + ck.getNdg() + "' class='fancyBoxRafreload'><i class='fa fa-plus'></i> Add Agent </a></li>";
                    }
                    String az = "<div class='btn-group'><a class='btn btn-sm blue btn-outline btn-circle' href='javascript:;' data-toggle='dropdown' ><i class='fa fa-wrench'></i></a><ul class='dropdown-menu' data-content='body'>" + link2 + link3 + "</ul></div>";
                    valore = valore + " [ \"" + ck.getNdg() + "\",\"" + ck.getRagione_sociale() + "\",\"" + formatStatus_general(ck.getFg_annullato()) + "\",\"" + az + "\"],";
                }
                String x = inizio + valore.substring(0, valore.length() - 1) + fine;
                out.print(x);
            } else {
                out.print(inizio + fine);
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
    protected void query_et_list(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain; charset=ISO-8859-1");
        response.setCharacterEncoding("ISO-8859-1");
        String typeop = request.getParameter("typeop");
        String d1 = request.getParameter("d1");
        String d2 = request.getParameter("d2");
        String branch = request.getParameter("branch").trim();

        Db_Master db = new Db_Master();
        ArrayList<String[]> array_credit_card = db.list_bank_pos_enabled();
        DateTime today = db.getCurdateDT();
        ArrayList<NC_category> nc_cat;
        if (branch != null && !branch.equals("") && !branch.equals("...")) {
            nc_cat = db.query_nc_category_filial(branch, null);
        } else {
            nc_cat = db.query_nc_category_filial("000", null);
        }
        ArrayList<ET_change> result = db.query_et(typeop, d1, d2, branch);
        db.closeDB();
        try (PrintWriter out = response.getWriter()) {
            String inizio = "{ \"aaData\": [";
            String fine = "] }";
            String valore = "";
            if (result.size() > 0) {
                for (int i = 0; i < result.size(); i++) {
                    ET_change res = result.get(i);
                    String dt_del = "";
                    if (res.getFg_annullato().equals("1")) {
                        dt_del = formatStringtoStringDate(res.getDel_dt(), patternsqldate, patternnormdate);
                    }
                    Db_Master db1 = new Db_Master();
                    ArrayList<Till> array_till = db1.list_till_status("O", null, res.getFiliale());
                    ArrayList<String> complete = db1.list_frombranch_used();
                    db1.closeDB();
                    boolean activefr = false;
                    for (int j = 0; j < array_till.size(); j++) {
                        if (array_till.get(j).getId_opcl().equals(res.getIdopen_from())) {
                            DateTime dt1 = getDT(res.getDt_it().substring(0, 10), patternsql);
                            activefr = today.isEqual(dt1);
                            break;
                        }
                    }
                    boolean istobranch = res.getFg_brba().equals("BR") && res.getFg_tofrom().equals("T");
                    String link_1 = "<div class='col-md-3'><form method='post' target='_blank' action='et_change_view.jsp'>"
                            + "<input type='hidden' name='cod' value='" + res.getCod() + "'/>"
                            + "<input type='hidden' name='typeop' value='" + res.getType() + "'/>"
                            + "<button type='submit' href='tb_figures.jsp' class='btn btn-sm btn-circle btn btn-icon-only white popovers' container='body' data-trigger='hover' "
                            + "data-container='body' data-placement='top' data-content='Show'><i class='fa fa-eye'></i></button></form></div>";
                    String link_2 = "<div class='col-md-3'><a href='Download?type=viewET_receipt&cod=" + res.getCod() + "&typeop=" + res.getType() + "' target='_blank' class='btn btn btn-icon-only btn-sm btn-circle white popovers' "
                            + "container='body' data-trigger='hover' data-container='body' data-placement='top' data-content='Reprint'><i class='fa fa-print'></i></a></div>";
                    String link_3 = "<div class='col-md-3'><a href='fancy_delet.jsp?code=" + res.getCod() + "&tobr=" + istobranch + "' "
                            + "class='btn btn-sm btn-circle btn btn-icon-only white popovers fancyBoxRafreload' container='body' data-trigger='hover' data-container='body' "
                            + "data-placement='top' data-content='Delete'><i class='fa fa-trash'></i></a></div>";
                    String link_4 = "<div class='col-md-3'>"
                            + "<a href='error_rate.jsp?code=" + res.getCod()
                            + "' class='btn btn-sm btn-circle white btn btn-icon-only popovers fancyBoxRafreload' "
                            + "container='body' data-trigger='hover' data-container='body' "
                            + "data-placement='top' data-content='Change Rate'><i class='fa fa-asterisk'></i></a>"
                            + "</div>";
                    if (res.getFg_brba().equals("BA")) {
                        if (!activefr || res.getFg_annullato().equals("1")) {
                            link_3 = "<div class='col-md-3'><a disabled "
                                    + "class='btn btn-sm btn-circle btn btn-icon-only white popovers' container='body' data-trigger='hover' data-container='body' "
                                    + "data-placement='top' data-content='Delete'><i class='fa fa-trash font-grey-silver'></i></a></div>";
                        }
                    } else if (res.getFg_brba().equals("BR")) {

                        if (!activefr || res.getFg_annullato().equals("1") || res.getFg_tofrom().equals("F")) {
                            link_3 = "<div class='col-md-3'><a disabled "
                                    + "class='btn btn-sm btn-circle white btn btn-icon-only popovers' container='body' data-trigger='hover' data-container='body' "
                                    + "data-placement='top' data-content='Delete'><i class='fa fa-trash font-grey-silver'></i></a></div>";
                        }
                    }
                    String auto = "";
                    if (!res.getAuto().equals("A")) {
                        auto = " <a href='*.jsp' class='font-red bold popovers fancyBoxRaf' "
                                + "container='body' data-trigger='hover' data-container='body' "
                                + "data-placement='top' data-content='Information' "
                                + "style='text-decoration:none;'>&nbsp;&nbspM&nbsp;&nbsp</a>";

                    }
                    if (res.getFg_tofrom().equals("T")) {
                        if (complete.contains(res.getCod_in()) || complete.contains(res.getCod())) {
                            link_3 = "<div class='col-md-3'><a disabled "
                                    + "class='btn btn-sm btn-circle btn btn-icon-only white popovers' container='body' data-trigger='hover' data-container='body' "
                                    + "data-placement='top' data-content='Delete'><i class='fa fa-trash font-grey-silver'></i></a></div>";
                        }
                    }
                    boolean isCentral = isCentral();
                    String tipologia = format_tofrom_brba(res.getFg_tofrom(), res.getFg_brba(), res.getCod_dest(), array_credit_card);
                    if (isCentral) {
                        link_3 = "<div class='col-md-3'><a disabled "
                                + "class='btn btn-sm btn-circle btn btn-icon-only white popovers' container='body' data-trigger='hover' data-container='body' "
                                + "data-placement='top' data-content='Delete'><i class='fa fa-trash font-grey-silver'></i></a></div>";
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
                            if (nc1 != null) {
                                double t2 = fd(val.get(x).getIp_quantity()) * fd(nc1.getIp_prezzo_nc());
                                t1 += t2;
                            }
                        }
                        total = formatMysqltoDisplay(roundDoubleandFormat(t1, 2));
                    }
                    //VERIFica NUOVA
//                if (Constant.newpread) {
                    boolean branchauto = res.getFg_brba().equals("BR")
                            && res.getAuto().equals("A");
                    if (branchauto
                            || isCentral
                            || tipologia.contains("POS")
                            || typeop.equals("NC")
                            || res.getFg_annullato().equals("1")) {
                        link_4 = "<div class='col-md-3'>"
                                + "<a disabled class='btn btn-icon-only btn-sm btn-circle white popovers' "
                                + "container='body' data-trigger='hover' data-container='body' "
                                + "data-placement='top' data-content='Change Rate'><i class='fa fa-asterisk font-grey-silver'></i></a>"
                                + "</div>";
                    } else {
                        link_4 = "<div class='col-md-3'>"
                                + "<a href='error_rate.jsp?code=" + res.getCod()
                                + "' class='btn btn-sm btn-circle white btn btn-icon-only popovers fancyBoxRafreload' "
                                + "container='body' data-trigger='hover' data-container='body' "
                                + "data-placement='top' data-content='Change Rate'><i class='fa fa-asterisk'></i></a>"
                                + "</div>";
                    }
//                }
                    String az = "<div='btn-group pull-left'>" + link_1 + link_2 + "<div class='clearfix'></div>" + link_3 + link_4 + "</div>";
                    valore = valore
                            + " [ \""
                            + az
                            + "\",\"" + res.getFiliale()
                            + "\",\"" + res.getId()
                            + "\",\"" + formatStringtoStringDate(res.getDt_it(), patternsqldate, patternnormdate) + "\",\"" + res.getUser() + auto + "\",\"" + tipologia + "\",\"" + res.getCod_dest() + "\",\"" + total + "\",\"" + dt_del + "\",\"" + res.formatStatus(res.getFg_annullato()) + "\"],";
                }
                String x = inizio + valore.substring(0, valore.length() - 1) + fine;
                out.print(x);
            } else {
                out.print(inizio + fine);
            }
            db.closeDB();
        }
    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void query_oc_list(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain; charset=ISO-8859-1");
        response.setCharacterEncoding("ISO-8859-1");

        String till = request.getParameter("till");
        String d1 = request.getParameter("d1");
        String d2 = request.getParameter("d2");
        String branch = request.getParameter("branch").trim();

        Db_Master db = new Db_Master();
        ArrayList<Openclose> result = db.query_oc(till, d1, d2, branch);
        db.closeDB();

        try (PrintWriter out = response.getWriter()) {
            String inizio = "{ \"aaData\": [";
            String fine = "] }";
            String valore = "";
            if (result.size() > 0) {
                for (int i = 0; i < result.size(); i++) {
                    Openclose res = result.get(i);
                    Db_Master db1 = new Db_Master();
                    ArrayList<Till> listTill = db1.list_ALLtill(res.getFiliale());
                    db1.closeDB();
                    String link = "<div class='col-md-4'><form method='post' target='_blank' action='oc_view.jsp'>"
                            + "<input type='hidden' name='cod' value='" + res.getCod() + "'/>"
                            + "<button type='submit' class='btn btn-sm btn-circle white popovers' container='body' data-trigger='hover' "
                            + "data-container='body' data-placement='top' data-content='Show'><i class='fa fa-eye'></i></button></form></div>";
                    String az = "<div class='btn-group'>" + link + "</div>";
                    valore = valore + " [ \""
                            + az + "\",\""
                            + res.getFiliale()
                            + "\",\"" + res.getId()
                            + "\",\"" + formatStringtoStringDate(res.getData(), patternsqldate, patternnormdate) + "\",\"" + res.getUser() + "\",\"" + res.formatDescTill(listTill, res.getTill()) + "\",\"" + res.formatType(res.getFg_tipo()) + "\",\"" + res.getErrors() + "\"],";
                }
                String x = inizio + valore.substring(0, valore.length() - 1) + fine;
                out.print(x);
            } else {
                out.print(inizio + fine);
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
    protected void query_it_list(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain; charset=ISO-8859-1");
        response.setCharacterEncoding("ISO-8859-1");
        String typeop = request.getParameter("typeop");
        String d1 = request.getParameter("d1");
        String d2 = request.getParameter("d2");
        String branch = request.getParameter("branch").trim();
//        String pdf = request.getParameter("pdf");

        Db_Master db = new Db_Master();
        ArrayList<IT_change> result = db.query_it(typeop, d1, d2, branch);
        String oggi = db.curdate();
        db.closeDB();
        try (PrintWriter out = response.getWriter()) {
            String inizio = "{ \"aaData\": [";
            String fine = "] }";
            String valore = "";
            if (result.size() > 0) {
                for (int i = 0; i < result.size(); i++) {
                    IT_change res = result.get(i);
                    String dt_del = "";
                    Db_Master db1 = new Db_Master();
                    ArrayList<Till> array_till = db1.list_till_status("O", null, res.getFiliale());
                    db1.closeDB();
                    String link2 = "<div class='col-md-4'><a href='fancy_delit.jsp?code=" + res.getCod() + "' "
                            + "class='btn btn-sm btn-circle white popovers fancyBoxRafreload' container='body' data-trigger='hover' data-container='body' "
                            + "data-placement='top' data-content='Delete'><i class='fa fa-trash'></i></a></div";
                    if (res.getFg_annullato().equals("1")) {
                        dt_del = formatStringtoStringDate(res.getDel_dt(), patternsqldate, patternnormdate);
                        link2 = "<div class='col-md-4'><a disabled "
                                + "class='btn btn-sm btn-circle white popovers' container='body' data-trigger='hover' data-container='body' "
                                + "data-placement='top' data-content='Delete'><i class='fa fa-trash'></i></a></div";
                    } else {
                        boolean activefr = false;
                        boolean activeto = false;

                        for (int j = 0; j < array_till.size(); j++) {
                            if (array_till.get(j).getId_opcl().equals(res.getIdopen_from())) {
                                activefr = true;
                                break;
                            }
                        }

                        for (int j = 0; j < array_till.size(); j++) {
                            if (array_till.get(j).getId_opcl().equals(res.getIdopen_to())) {
                                activeto = true;
                                break;
                            }
                        }
                        if (activefr && activeto) {

                        } else {
                            link2 = "<div class='col-md-4'><a disabled "
                                    + "class='btn btn-sm btn-circle white popovers' container='body' data-trigger='hover' data-container='body' "
                                    + "data-placement='top' data-content='Delete'><i class='fa fa-trash'></i></a></div";
                        }
                    }
                    String link1 = "<div class='col-md-4'><form method='post' target='_blank' action='it_change_view.jsp'>"
                            + "<input type='hidden' name='cod' value='" + res.getCod() + "'/>"
                            + "<input type='hidden' name='typeop' value='" + res.getTypeop() + "'/>"
                            + "<button type='submit' href='tb_figures.jsp' class='btn btn-sm btn-circle white popovers' container='body' data-trigger='hover' "
                            + "data-container='body' data-placement='top' data-content='Show'><i class='fa fa-eye'></i></button></form></div>";
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
                    boolean isCentral = isCentral();
                    if (isCentral) {
                        link2 = "<div class='col-md-4'><a disabled "
                                + "class='btn btn-sm btn-circle white popovers' container='body' data-trigger='hover' data-container='body' "
                                + "data-placement='top' data-content='Delete'><i class='fa fa-trash'></i></a></div";
                    } else {
                        if (!res.getDt_it().startsWith(oggi)) {
                            link2 = "<div class='col-md-4'><a disabled "
                                    + "class='btn btn-sm btn-circle white popovers' container='body' data-trigger='hover' data-container='body' "
                                    + "data-placement='top' data-content='Delete'>"
                                    + "<i class='fa fa-trash'></i></a></div";
                        }
                    }
                    String az = "<div class='btn-group'>" + link1 + link2 + "</div>";
                    valore = valore
                            + " [ \"" + az
                            + "\",\"" + res.getFiliale()
                            + "\",\"" + res.getId()
                            + "\",\"" + formatStringtoStringDate(res.getDt_it(), patternsqldate, patternnormdate) + "\",\"" + res.getUser() + "\",\"" + res.getTill_from() + "\",\"" + fr + "\",\"" + res.getTill_to() + "\",\"" + to + "\",\"" + dt_del + "\",\"" + res.formatStatus(res.getFg_annullato()) + "\"],";
                }
                String x = inizio + valore.substring(0, valore.length() - 1) + fine;
                out.print(x);
            } else {
                out.print(inizio + fine);
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
    protected void query_transaction_list(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String user = (String) request.getSession().getAttribute("us_cod");
        if (user == null) {
            user = "9999";
        }

        response.setContentType("text/plain; charset=ISO-8859-1");
        response.setCharacterEncoding("ISO-8859-1");

        String d1 = request.getParameter("d1");
        String d2 = request.getParameter("d2");

        String taxcode = replaceApici(request.getParameter("taxcode").trim());
        String surname = replaceApici(request.getParameter("surname").trim());
        String name = replaceApici(request.getParameter("name").trim());
        String branch = request.getParameter("branch").trim();

//        String pdf = request.getParameter("pdf");
        Db_Master db = new Db_Master();
        ArrayList<String> userkyc = db.list_kyc_enabled();
        DateTime today = db.getCurdateDT();
        ArrayList<Ch_transaction> result = db.query_transaction_ch_new(d1, d2, branch, surname, name, taxcode);
        db.closeDB();

        try (//        Db_Master db1 = new Db_Master();
                //        String base64 = Pdf.pdf_transactionList(new File(db1.getPath("temp") + pdf), result);
                //        db1.closeDB();
                //        if (base64 != null) {
                //            Db_Master db2 = new Db_Master();
                //            db2.insert_pdf_temp(pdf, base64);
                //            db2.closeDB();
                //        }
                PrintWriter out = response.getWriter()) {
            String inizio = "{ \"aaData\": [";
            String fine = "] }";
            String valore = "";
            boolean cruscottovisibile = false; // inserire controllo su utente abilitato
            if (filiale.equals("000")) {
                cruscottovisibile = userkyc.contains(request.getSession().getAttribute("us_user").toString());
            }
            boolean iscentral = isCentral();
            Db_Master db2 = new Db_Master();
            if (!result.isEmpty()) {
                for (int i = 0; i < result.size(); i++) {
                    Ch_transaction res = result.get(i);
                    boolean attivo;
                    boolean refunded = false;
                    boolean deleted = res.getDel_fg().equals("1");
                    String link1 = "<div class='col-md-3'><a href='transaction_view.jsp?cod=" + res.getCod() + "' target='_blank' "
                            + "class='btn btn-sm white popovers' container='body' data-trigger='hover' data-container='body' "
                            + "data-placement='top' data-content='Show'><i class='fa fa-eye'></i></a></div>";
                    String link2 = "<div class='col-md-3'><a href='transaction_reprint.jsp?cod=" + res.getCod() + "' "
                            + "class='btn btn-sm white fancyBoxRaf popovers' container='body' data-trigger='hover' "
                            + "data-container='body' data-placement='top' data-content='Reprint'><i class='fa fa-print'></i></a></div>";
                    boolean visualizzacentrale = false;
                    String claref = "";
                    if (res.getRefund().equals("1")) {
                        claref = "font-red";
                        refunded = true;
                    }
                    String link2a = "<div class='col-md-3'><a href='transaction_ref.jsp?code=" + res.getCod() + "'  onclick='return controllatilloccupato();'class='btn btn-sm white popovers "
                            + claref + " fancyBoxRafreload' container='body' data-trigger='hover' data-container='body' data-placement='top' data-content='Refund'>"
                            + "<i class='fa fa-history'></i></a></div>";
                    String link2b = "<a href='transaction_ref_branch.jsp?code=" + res.getCod() + "'  onclick='return controllatilloccupato();' class='btn btn-sm white popovers' target='_blank' container='body' data-trigger='hover' data-container='body' data-placement='top' data-content='Refund Branch'><i class='fa fa-user'></i></a>";
                    if (db2.getC() == null) {
                        db2 = new Db_Master();
                    }
                    Ch_transaction_refund ref = db2.get_refund_trans(res.getCod());
                    if (res.getRefund().equals("1")) {
                        if (ref != null) {
                            if (ref.getStatus().equals("1")) {
                                link2a = "";
                                link2b = "<a href='transaction_ref_branch.jsp?code=" + res.getCod() + "' "
                                        + "class='btn btn-sm white popovers font-green-jungle fancyBoxRaf' "
                                        + "container='body' data-trigger='hover' data-container='body' data-placement='top' "
                                        + "data-content='Refunded'><i class='fa fa-user'></i></a>";
                                visualizzacentrale = true;
                            } else {

                                link2b = "<a href='transaction_ref_branch.jsp?code=" + res.getCod() + "' onclick='return controllatilloccupato();' "
                                        + "class='btn btn-sm white popovers font-red' "
                                        + "target='_blank' container='body' data-trigger='hover' data-container='body' data-placement='top' "
                                        + "data-content='Refund Branch Authorized from Head Office'><i class='fa fa-user'></i></a>";
                            }
                        }
                    }
                    if (deleted) {
                        link2a = "";
                        link2b = "";
                    }
                    String modificasupporto = "<div class='col-md-3'>"
                            + "<a href='fancy_editpaym.jsp?code=" + res.getCod() + "' "
                            + "class='btn btn-sm white popovers fancyBoxRafreload' "
                            + "data-container='body' data-trigger='hover' "
                            + "data-placement='top' data-content='Edit Payment Mode'><i class='fa fa-edit'>"
                            + "</i></a></div>";
                    //                String link1 = "<div class='col-md-3'><a href='transaction_view.jsp?cod=" + res.getCod() + "' target='_blank' "
//                        + "class='btn btn-sm white popovers' container='body' data-trigger='hover' data-container='body' "
//                        + "data-placement='top' data-content='Show'><i class='fa fa-eye'></i></a></div>";
                    String link3 = "<div class='col-md-3'><a href='fancy_deltr.jsp?code=" + res.getCod() + "' "
                            + "class='btn btn-sm white popovers fancyBoxRafreload' container='body' data-trigger='hover' data-container='body' "
                            + "data-placement='top' data-content='Delete'><i class='fa fa-trash' onclick='return controllatilloccupato();'></i></a></div>";
                    if (!res.getFa_number().equals("-")) {
                        link3 = "<div class='col-md-3'><a href='fancy_deltr_cn.jsp?code=" + res.getCod() + "' "
                                + "class='btn btn-sm white popovers font-blue' container='body' data-trigger='hover' data-container='body' target='_blank' "
                                + "data-placement='top' data-content='Delete' onclick='return controllatilloccupato();'><i class='fa fa-trash'></i></a></div>";
                    }
                    String dt_del = "";
                    if (res.getDel_fg().equals("1") || refunded) {
                        if (res.getDel_fg().equals("1")) {
                            dt_del = formatStringtoStringDate(res.getDel_dt().split("\\.")[0], "yyyy-MM-dd HH:mm:ss", "dd/MM/yyyy HH:mm:ss");
                        }
                        attivo = false;
                        link3 = "<div class='col-md-3'><button class='btn btn-sm white popovers' container='body' data-trigger='hover' data-container='body' data-placement='top' data-content='Delete'><i class='fa fa-trash font-grey-silver'></i></button></div>";
                        modificasupporto = "";
                    } else {
                        boolean activefr = false;
                        if (db2.getC() == null) {
                            db2 = new Db_Master();
                        }
                        ArrayList<Till> array_till = db2.list_till_status("O", user, res.getFiliale());
                        for (int j = 0; j < array_till.size(); j++) {
                            if (array_till.get(j).getId_opcl().equals(res.getId_open_till())) {
                                DateTime dt1 = getDT(res.getData().substring(0, 10), patternsql);
                                activefr = today.isEqual(dt1);
                                break;
                            }
                        }
                        if (!activefr) {
                            link3 = "<div class='col-md-3'><button class='btn btn-sm white popovers' container='body' data-trigger='hover' data-container='body' data-placement='top' data-content='Delete'><i class='fa fa-trash font-grey-silver'></i></button></div>";
                            attivo = false;
                            modificasupporto = "";
                        } else {
                            attivo = true;
                        }
                    }
                    String bb = format_BB_SB(res);
                    String ib = "";
                    if (res.getIntbook().equals("1")) {
                        ib = "<span class='font-green-soft ital'><small>&nbsp;&nbsp;<i class='fa fa-italic'></i><i class='fa fa-bold'></i></small></span>";
                    }
                    if (db2.getC() == null) {
                        db2 = new Db_Master();
                    }   //Client cl = db2.query_Client(res.getCl_cod());
                    Client cl = db2.query_Client_transaction(res.getCod(), res.getCl_cod());
                    //                Openclose oc = db2.query_oc(res.getId_open_till());
//                String idoc = "-";
//                if (oc != null) {
//                    idoc = oc.getId();
//                }
                    if (iscentral) {
                        link3 = "<div class='col-md-3'><button class='btn btn-sm white popovers' container='body' data-trigger='hover' data-container='body' data-placement='top' data-content='Delete'><i class='fa fa-trash font-grey-silver'></i></button></div>";
                        link2b = "";
                        modificasupporto = "";
                    } else {
                        link2a = "";
                    }
                    if (attivo) {
                        link2b = "";
                        link2a = "";
                    }
                    if (visualizzacentrale) {
                        link2b = "<a href='transaction_ref_branch.jsp?code=" + res.getCod() + "' "
                                + "class='btn btn-sm white popovers font-green-jungle fancyBoxRaf' "
                                + "container='body' data-trigger='hover' data-container='body' data-placement='top' "
                                + "data-content='Refunded'><i class='fa fa-user'></i></a>";
                    }
                    String ca = "";
                    String pos = res.getPos();
                    if (res.getTipotr().equals("B")) {
                        ArrayList<Ch_transaction_value> va  = db2.query_transaction_value(res.getCod());
                        for (int f = 0; f < va.size(); f++) {
                            if (va.get(f).getSupporto().equals("04")) {
                                pos = va.get(f).getPos();
                                ca = "<span class='font-dark ital'><b> <u>CA</u> </b></span>";
                                break;
                            }
                        }

                        if (ca.equals("")) {
                            modificasupporto = "";
                        }

                    }
                    String link5 = "";
                    if (cruscottovisibile) {
                        link5 = "<div class='col-md-3'><a href='kyc_mod.jsp?cod=" + res.getCod() + "' target='_blank' class='btn btn-sm white popovers' container='body' "
                                + "data-trigger='hover' data-container='body' data-placement='top' data-content='Edit KYC'>"
                                + "<i class='fa fa-edit'></i></a></div>";
                    }   //link1  - VIEW TRANSACTION
                    //link2  - REPRINT
                    //link2a - REFUND DA CENTRALE (AUTORIZZAZIONE)
                    //link3  - DELETE
                    //link5  - EDIT KYC
                    //link2b - REFUND DA FILIALE (AUTORIZZAZIONE)
                    if (is_CZ) {
                        link2a = "";
                    }
                    if (res.getIntbook_type().equals("1")) { //SELL CON TAX FREE NON SI PUO' CAMBIARE
                        modificasupporto = "";
                    }
                    if (res.getPos().equals("999")) {
                        modificasupporto = "";
                    }   //                descr = Engine.formatBankBranchReport(oc.getCod_dest(), "BA", array_bank, null);

                    String link6 = "";
                    if (Utility.validatemail(cl.getEmail()) && is_IT) {
                        link6 = "<div class='col-md-3'><form method='POST' action='Operazioni' target='_blank'><input type='hidden' name='type' value='sendmailclient' /><input type='hidden' name='redir' value='no' />"
                                + "<input type='hidden' name='codtr' value='" + res.getCod() + "' /><input type='hidden' name='maildest' value='" + cl.getEmail() + "' />"
                                + "<button type='submit' class='btn btn-sm white popovers' container='body' data-trigger='hover' data-container='body' data-placement='top' data-content='SEND EMAIL WITH DOCUMENTS - Client Email: " + cl.getEmail() + "'>"
                                + "<i class='fa fa-envelope-o'></i></button></form></div>";
                    }

                    String az = "<div class='btn-group'>" + link1 + link2 + "<div class='clearfix'></div>" + link2a + link3 + "</div><div class='clearfix'></div>"
                            + link5 + modificasupporto + link6 + "</div>";
                    valore = valore + " [ \""
                            + az + "\",\""
                            + res.formatStatus(res.getDel_fg()) + "\",\""
                            + res.getFiliale() + "\",\"" + res.getId() + link2b + "\",\""
                            + formatStringtoStringDate(res.getData().split("\\.")[0], "yyyy-MM-dd HH:mm:ss", "dd/MM/yyyy HH:mm:ss") + "\",\""
                            + res.getTill() + "\",\""
                            + res.getUser() + "\",\""
                            + formatType(res.getTipotr()) + bb + ib + ca + "\",\""
                            + formatMysqltoDisplay(Utility.sanitize(res.getTotal())) + "\",\""
                            + formatMysqltoDisplay(Utility.sanitize(res.getPay())) + "\",\""
                            + formatMysqltoDisplay(Utility.sanitize(res.getCommission())) + "\",\""
                            + formatMysqltoDisplay(Utility.sanitize(res.getSpread_total())) + "\",\""
                            + res.getFa_number() + "\",\""
                            + res.getCn_number() + "\",\""
                            + dt_del + "\",\""
                            + cl.getCognome().toUpperCase() + "\",\""
                            + cl.getNome().toUpperCase()
                            + "\",\"" + cl.getCodfisc().toUpperCase()
                            + "\",\"" + pos
                            + "\"],";
                }
                db2.closeDB();
                String x = inizio + valore.substring(0, valore.length() - 1) + fine;
                out.print(x);
            } else {
                out.print(inizio + fine);
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
    protected void query_transaction_list_nc(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String user = (String) request.getSession().getAttribute("us_cod");
        if (user == null) {
            user = "9999";
        }

        response.setContentType("text/plain; charset=ISO-8859-1");
        response.setCharacterEncoding("ISO-8859-1");
        String d1 = request.getParameter("d1");
        String d2 = request.getParameter("d2");
        String branch = request.getParameter("branch").trim();

        String[] nc_cat1 = request.getParameterValues("nc_cat1");

        Db_Master db = new Db_Master();
        DateTime today = db.getCurdateDT();
        ArrayList<NC_category> array_nc_cat = db.list_ALL_nc_category(filiale);
        ArrayList<NC_causal> array_nc_caus = db.list_nc_causal_all(filiale);

        String list_nc_cat = "";
        if (nc_cat1 == null) {
            for (int i = 0; i < array_nc_cat.size(); i++) {
                list_nc_cat = list_nc_cat + array_nc_cat.get(i).getGruppo_nc() + ";";
            }
        } else {
            String totallist = parseArrayValues(nc_cat1);
            if (totallist.contains("...")) {
                for (int i = 0; i < array_nc_cat.size(); i++) {
                    list_nc_cat = list_nc_cat + array_nc_cat.get(i).getGruppo_nc() + ";";
                }
            } else {
                list_nc_cat = parseArrayValues(nc_cat1);
            }
        }

        ArrayList<String[]> array_nc_descr = db.list_nc_descr();
//        ArrayList<String> causal_paymat_enabled = db.causal_paymat_enabled();

        ArrayList<NC_transaction> result = db.query_NC_transaction(d1, d2, branch, list_nc_cat);
        db.closeDB();

        boolean iscentral = isCentral();
        try (PrintWriter out = response.getWriter()) {
            String inizio = "{ \"aaData\": [";
            String fine = "] }";
            String valore = "";
            if (result.size() > 0) {
                for (int i = 0; i < result.size(); i++) {
                    NC_transaction res = result.get(i);
                    String link1 = "<div class='col-md-4'><form method='post' target='_blank' action='nc_transaction_view.jsp'><input type='hidden' name='cod' value='" + res.getCod() + "'/>"
                            + "<button type='submit' href='tb_figures.jsp' class='btn btn-sm btn-circle white popovers' container='body' data-trigger='hover' "
                            + "data-container='body' data-placement='top' data-content='Show'><i class='fa fa-eye'></i></button></form></div>";
                    String link2 = "<div class='col-md-4'><a href='Download?type=view_reprint_nctr&cod=" + res.getCod() + "&view=print' target='_blank'"
                            + " class='btn btn-sm btn-circle white popovers' "
                            + "container='body' data-trigger='hover' data-container='body' data-placement='top' data-content='Reprint'><i class='fa fa-print'></i></a></div>";
                    String link3 = "<div class='col-md-4'><a href='fancy_deltrnoch.jsp?code=" + res.getCod() + "' "
                            + "class='btn btn-sm btn-circle white popovers fancyBoxRafreload' container='body' data-trigger='hover' data-container='body' "
                            + "data-placement='top' data-content='Delete'><i class='fa fa-trash'></i></a></div>";
                    String modificasupporto = "<div class='col-md-4'>"
                            + "<a href='fancy_editpaym.jsp?code=" + res.getCod() + "' "
                            + "class='btn btn-sm btn-circle white popovers fancyBoxRafreload' "
                            + "data-container='body' data-trigger='hover' "
                            + "data-placement='top' data-content='Edit Payment Mode'><i class='fa fa-edit'>"
                            + "</i></a></div>";
                    if (res.getDel_fg().equals("1")) {
                        link3 = "";
                        modificasupporto = "";
//                    link3 = "<div class='col-md-4'><a disabled "
//                            + "class='btn btn-sm white popovers fancyBoxRafreload' container='body' data-trigger='hover' data-container='body' "
//                            + "data-placement='top' data-content='Delete'><i class='fa fa-trash'></i></a></div";
                    } else {
                        boolean activefr = false;
                        Db_Master db1 = new Db_Master();
                        ArrayList<Till> array_till = db1.list_till_status("O", user, res.getFiliale());
                        //                  ArrayList<Till> array_till = db1.list_till_status("O", null, res.getFiliale());
                        db1.closeDB();
                        for (int j = 0; j < array_till.size(); j++) {
                            if (array_till.get(j).getId_opcl().equals(res.getId_open_till())) {
                                DateTime dt1 = getDT(res.getData().substring(0, 10), patternsql);
                                activefr = today.isEqual(dt1);
                                break;
                            }
                        }
                        if (!activefr) {
                            link3 = "";
//                        modificasupporto = "";
                        }
                    }
//                Db_Master db2 = new Db_Master();
//
////                Openclose oc = db2.query_oc(res.getId_open_till());
//                db2.closeDB();
//                String idoc = "-";
//                if (oc != null) {
//                    idoc = oc.getId();
//                }
                    NC_causal nccaus = getNC_causal(array_nc_caus, res.getCausale_nc(), res.getGruppo_nc());
                    boolean ext = nccaus.getPaymat().equals("1");
                    boolean uscitacass = nccaus.getFg_in_out().equals("2") || nccaus.getFg_in_out().equals("4");
//                boolean ext = causal_paymat_enabled.contains(res.getCausale_nc());
                    if (iscentral || ext || !res.getCh_transaction().equals("-")) {
                        link3 = "";
//                    modificasupporto = "";
                    }
                    if (uscitacass) {
                        modificasupporto = "";
                    }   //                
                    String q1 = formatMysqltoDisplay(roundDoubleandFormat(fd(res.getQuantita()), 0));
                    String p1 = formatMysqltoDisplay(Utility.sanitize(res.getPrezzo()));
                    String f1 = formatMysqltoDisplay("0.00");
                    switch (res.getFg_tipo_transazione_nc()) {
                        case "1":
                            q1 = "1";
                            p1 = formatMysqltoDisplay(Utility.sanitize(res.getNetto()));
                            f1 = formatMysqltoDisplay(Utility.sanitize(res.getCommissione()));
                            break;
                        case "3":
                            q1 = formatMysqltoDisplay(roundDoubleandFormat(fd(res.getRicevuta()), 0));
                            p1 = formatMysqltoDisplay(Utility.sanitize(res.getQuantita()));
                            break;
                        case "21":
                            String comm;
                            if (fd(res.getCommissione()) > 0) {
                                comm = res.getCommissione();
                            } else {
                                comm = res.getTi_ticket_fee();
                            }
                            f1 = formatMysqltoDisplay(Utility.sanitize(comm));
                            break;
                        default:
                            break;
                    }
                    String pos = res.getPos();
                    String stampascontrino = "";
                    NC_category nccat = getNC_category(array_nc_cat, res.getGruppo_nc());
                    if (is_IT && !iscentral && nccat.getFg_registratore().equals("1")) {
                        stampascontrino = "<div class='col-md-4'>"
                                + "<a href='fancy_printsc.jsp?cod=" + res.getCod() + "' target='_blank'"
                                + "class='btn btn-sm btn-circle white popovers' "
                                + "container='body' data-trigger='hover' data-container='body' "
                                + "data-placement='top' data-content='Print Fiscal Receipt'> "
                                + "<i class='fa fa-newspaper-o'></i></a></div>";
                    }
                    String az = "<div class='btn-group'>" + link1 + link2 + link3 + stampascontrino + modificasupporto + "</div>";
                    valore = valore + " [ \""
                            + az + "\",\""
                            + res.formatStatus(res.getDel_fg())
                            + "\",\""
                            + res.getFiliale()
                            + "\",\"" + res.getId()
                            + "\",\"" + formatStringtoStringDate(res.getData(), patternsqldate, patternnormdate) + "\",\"" + res.getTill() + "\",\"" + res.getUser() + "\",\"" + formatMysqltoDisplay(Utility.sanitize(res.getTotal())) + "\",\"" + q1 + "\",\"" + p1 + "\",\"" + f1 + "\",\"" + res.getGruppo_nc() + " - " + replace(visualizzaStringaMySQL(formatALNC_category(res.getGruppo_nc(), array_nc_cat)), "€", "&#0128;") + "\",\"" + res.getCausale_nc() + " - " + replace(visualizzaStringaMySQL(formatALNC_causal(res.getCausale_nc(), array_nc_caus)), "€", "&#0128;") + "\",\"" + visualizzaStringaMySQL(res.getCl_cognome() + " " + res.getCl_nome()) + "\",\"" + visualizzaStringaMySQL(formatALNC_causal_ncde(res.getCausale_nc(), array_nc_caus, array_nc_descr)) + "\",\"" + pos + "\"],";
                }
                String x = inizio + valore.substring(0, valore.length() - 1) + fine;
                out.print(x);
            } else {
                out.print(inizio + fine);
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
    protected void query_users_list_kyc(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain; charset=ISO-8859-1");
        response.setCharacterEncoding("ISO-8859-1");

        ArrayList<Users> result = user_kyc();

        try (PrintWriter out = response.getWriter()) {
            String inizio = "{ \"aaData\": [";
            String fine = "] }";
            String valore = "";
            if (result.size() > 0) {
                for (int i = 0; i < result.size(); i++) {
                    Users us = (Users) result.get(i);

                    String az = "<a href='tb_edit_users_kyc.jsp?us_code=" + us.getCod() + "' "
                            + "class='btn btn-sm blue btn-outline btn-circle fancyBoxRafreload'> "
                            + "<i class='fa fa-edit'></i> Edit </a>";

                    valore = valore + " [ \"" + us.getCod()
                            + "\",\"" + formatUTFtoLatin(visualizzaStringaMySQL(us.getUsername()))
                            + "\",\"" + formatUTFtoLatin(visualizzaStringaMySQL(us.getDe_cognome()))
                            + "\",\"" + formatUTFtoLatin(visualizzaStringaMySQL(us.getDe_nome()))
                            + "\",\"" + us.formatTypeuser(us.getFg_tipo())
                            + "\",\"" + us.formatStatususer(us.getFg_stato())
                            + "\",\"" + az + "\"],";
                }

                String x = inizio + valore.substring(0, valore.length() - 1) + fine;
                out.print(x);
            } else {
                out.print(inizio + fine);
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
    protected void query_users_list(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain; charset=ISO-8859-1");
        response.setCharacterEncoding("ISO-8859-1");
        //String pdf = request.getParameter("pdf");
        //String excel = request.getParameter("excel");
        Db_Master db = new Db_Master();

        ArrayList<Users> result = db.list_all_users();

        boolean central = isCentral();
        String tipo = (String) request.getSession().getAttribute("us_tip");
        if (tipo == null) {
            tipo = "";
        }

        db.closeDB();
        try (PrintWriter out = response.getWriter()) {
            String inizio = "{ \"aaData\": [";
            String fine = "] }";
            String valore = "";
            if (result.size() > 0) {
                for (int i = 0; i < result.size(); i++) {
                    Users us = (Users) result.get(i);
                    String az;
                    if (central && tipo.equals("3")) {
                        az = "<a href='tb_edit_users.jsp?fil=" + filiale + "&view=0&us_code=" + us.getCod() + "' class='btn btn-sm blue btn-outline btn-circle fancyBoxRafreload'><i class='fa fa-wrench'></i> Edit </a>";
                    } else {
                        az = "<a href='tb_edit_users.jsp?fil=" + filiale + "&view=1&us_code=" + us.getCod() + "' "
                                + "class='btn btn-sm blue btn-outline btn-circle fancyBoxRaf'><i class='fa fa-eye'></i> View </a>";
                    }
                    valore = valore + " [ \""
                            + us.getCod() + "\",\"" + formatUTFtoLatin(visualizzaStringaMySQL(us.getUsername())) + "\",\"" + formatUTFtoLatin(visualizzaStringaMySQL(us.getDe_cognome())) + "\",\"" + formatUTFtoLatin(visualizzaStringaMySQL(us.getDe_nome())) + "\",\"" + us.formatTypeuser(us.getFg_tipo()) + "\",\"" + us.formatStatususer(us.getFg_stato()) + "\",\"" + az + "\"],";
                }
                String x = inizio + valore.substring(0, valore.length() - 1) + fine;
                out.print(x);
            } else {
                out.print(inizio + fine);
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
    protected void query_newsletters(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain; charset=ISO-8859-1");
        response.setCharacterEncoding("ISO-8859-1");
        String user = (String) request.getSession().getAttribute("us_cod");
        if (user == null) {
            user = "0001";
        }

        String status = Utility.sanitize(request.getParameter("status"));
        String search = Utility.sanitize(request.getParameter("search"));

        Db_Master db = new Db_Master();
        ArrayList<Newsletters> result = db.query_newsletters(user, status);
        db.closeDB();
        try (PrintWriter out = response.getWriter()) {
            String inizio = "{ \"aaData\": [";
            String fine = "] }";
            String valore = "";
            if (result.size() > 0) {
                for (int i = 0; i < result.size(); i++) {
                    Newsletters res = (Newsletters) result.get(i);
                    String st = res.formatStatus(res.getStatus());
                    if (res.getStatus().equals("R")) {
                        st = st + " <span class='help-block'>" + formatStringtoStringDate(res.getDt_read(), "yyyy-MM-dd HH:mm:ss", "dd/MM/yyyy HH:mm:ss") + "</span>";
                    }
                    String l1 = "<a href='Download?type=viewNewsLetters&cod=" + res.getCod() + "&user=" + user + "' class='btn btn-xs blue btn-outline btn-circle popovers' " + " container='body' data-trigger='hover' data-container='body' data-placement='top' data-content='View pdf' target='_blank'><i class='fa fa-file-pdf-o'></i></a>";
                    String az = "<div class='btn-group'>" + l1 + "</div>";
                    valore = valore + " [ \"" + correggi(res.getTitolo()) + "\",\"" + correggi(res.getDescr()) + "\",\"" + formatStringtoStringDate(res.getDt_upload(), "yyyy-MM-dd HH:mm:ss", "dd/MM/yyyy HH:mm:ss") + "\",\"" + st + "\",\"" + az + "\"],";
                }
                String x = inizio + valore.substring(0, valore.length() - 1) + fine;
                out.print(x);
            } else {
                out.print(inizio + fine);
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
    protected void queryloy_ass(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain; charset=ISO-8859-1");
        response.setCharacterEncoding("ISO-8859-1");
//        String user = (String) request.getSession().getAttribute("us_cod");

        String err;
        String q = Utility.sanitize(request.getParameter("q"));
        Db_Loy dbl = new Db_Loy();
        //String completo = dbl.getCodiceCompleto(q, "1");
        String completo[] = dbl.getCodiceCompleto(Utility.sanitize(q));

        if (completo == null) {
            err = "ERROR: Loyalty Code not found.";
        } else {
            if (completo[1].equals("0")) {
                err = "Loyalty Code found. The code will be associated to the customer. Close this window and  click 'Assign New Code' to proceed.";
            } else {
                String codcl = Utility.sanitize(dbl.getCodiceCliente(completo[0]));
                if (codcl == null) {
                    err = "ERROR: Client not found. Try again.";
                } else {
                    Db_Master db = new Db_Master(true);
                    Client cl = db.query_Client_transaction(null, codcl);
                    db.closeDB();
                    if (cl == null) {
                        err = "ERROR: Client not found. Try again.";
                    } else {
                        err = "ERROR: This code is already associated to another customer. Close this window and retry using another Loyalty Code.";
                    }
                }
            }
        }
        dbl.closeDB();
        try (PrintWriter out = response.getWriter()) {
            out.print(err);
        }
    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void queryloy(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain; charset=ISO-8859-1");
        response.setCharacterEncoding("ISO-8859-1");
        String err = "0";
        String q = Utility.sanitize(request.getParameter("q"));
        Db_Loy dbl = new Db_Loy();
        String completo[] = dbl.getCodiceCompleto(Utility.sanitize(q));
        Gson gson = new Gson();
        ArrayList<String> JSONRequest = new ArrayList<>();
        if (completo == null) {
            err = "Loyalty Code not found.";
        } else {
            if (completo[1].equals("0")) {
                err = "Loyalty Code found. The code will be associated with the customer.";
            } else {
                String codcl = Utility.sanitize(dbl.getCodiceCliente(completo[0]));
                if (codcl == null) {
                    err = "ERROR: Client not found. Try again.";
                } else {
                    Db_Master db = new Db_Master(true);
                    Client cl = db.query_Client_transaction(null, codcl);
                    db.closeDB();
                    if (cl == null) {
                        err = "ERROR: Client not found. Try again.";
                    } else {
                        JSONRequest.add(gson.toJson(cl.getCognome()));
                        JSONRequest.add(gson.toJson(cl.getNome()));
                        JSONRequest.add(gson.toJson(cl.getNazione()));
                        JSONRequest.add(gson.toJson(cl.getCitta()));
                        JSONRequest.add(gson.toJson(cl.getIndirizzo()));
                        JSONRequest.add(gson.toJson(cl.getCap()));
                        JSONRequest.add(gson.toJson(cl.getProvincia()));
                        JSONRequest.add(gson.toJson(cl.getTipo_documento()));
                        JSONRequest.add(gson.toJson(cl.getNumero_documento()));
                        JSONRequest.add(gson.toJson(cl.getDt_rilascio_documento()));
                        JSONRequest.add(gson.toJson(cl.getDt_scadenza_documento()));
                        JSONRequest.add(gson.toJson(cl.getRilasciato_da_documento()));
                        JSONRequest.add(gson.toJson(cl.getLuogo_rilascio_documento()));
                        JSONRequest.add(gson.toJson(cl.getEmail()));
                        JSONRequest.add(gson.toJson(cl.getTelefono()));
                        JSONRequest.add(gson.toJson(cl.getCode()));
                        JSONRequest.add(gson.toJson(cl.getCodfisc())); //16
                        JSONRequest.add(gson.toJson(cl.getSesso()));//17
                        JSONRequest.add(gson.toJson(cl.getPep()));//18
                        JSONRequest.add(gson.toJson(cl.getCitta_nascita()));//19
                        JSONRequest.add(gson.toJson(cl.getProvincia_nascita()));//20
                        JSONRequest.add(gson.toJson(cl.getNazione_nascita()));//21
                        JSONRequest.add(gson.toJson(cl.getDt_nascita()));//22

                        String[] citta = getCity_apm(cl.getCitta());
                        if (citta != null) {
                            JSONRequest.add(gson.toJson(citta[1]));
                        } else {
                            JSONRequest.add(gson.toJson("-"));
                        } //23

                        String[] prov = get_district(cl.getProvincia());
                        if (prov != null) {
                            JSONRequest.add(gson.toJson(prov[1]));
                        } else {
                            JSONRequest.add(gson.toJson("-"));
                        } //24

                        String[] prov_na = get_district(cl.getProvincia_nascita());
                        if (prov_na != null) {
                            JSONRequest.add(gson.toJson(prov_na[1]));
                        } else {
                            JSONRequest.add(gson.toJson("-"));
                        } //25
                    }
                }
            }
        }
        dbl.closeDB();
        try (PrintWriter out = response.getWriter()) {
            if (err.equals("0")) {
                out.print(JSONRequest);
            } else {
                JSONRequest.add(gson.toJson(err));
                out.print(JSONRequest);
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
    protected void query_newsletters_man(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain; charset=ISO-8859-1");
        response.setCharacterEncoding("ISO-8859-1");
        String user = (String) request.getSession().getAttribute("us_cod");
        if (user == null) {
            user = "0001";
        }
        Db_Master db = new Db_Master();
        ArrayList<Newsletters> result = db.query_newsletters();
        db.closeDB();
        try (PrintWriter out = response.getWriter()) {
            String inizio = "{ \"aaData\": [";
            String fine = "] }";
            String valore = "";
            if (result.size() > 0) {
                for (int i = 0; i < result.size(); i++) {
                    Newsletters res = result.get(i);
                    String l1 = "<a href='Download?type=viewNewsLetters&cod=" + res.getCod() + "&user=" + user + "' "
                            + "class='btn btn-xs blue btn-outline btn-circle popovers' " + " container='body' data-trigger='hover' data-container='body' data-placement='top' data-content='View pdf' target='_blank'><i class='fa fa-file-pdf-o'></i></a>";
                    String l2 = "<a href='nl_user_status.jsp?cod=" + res.getCod() + "' "
                            + "class='btn btn-xs dark btn-outline btn-circle popovers fancyBoxRaf' " + " container='body' data-trigger='hover'"
                            + " data-container='body' data-placement='top' data-content='View Users Read/Unread' target='_blank'><i class='fa fa-user'></i></a>";
                    String l3 = "<a href='fancy_delnl.jsp?code=" + res.getCod() + "' "
                            + "class='btn btn-xs red btn-outline btn-circle popovers fancyBoxRafreload' "
                            + " container='body' data-trigger='hover' data-container='body' "
                            + "data-placement='top' data-content='Delete'><i class='fa fa-remove'></i></a>";
                    String az = "<div class='btn-group'>" + l1 + l2 + l3 + "</div>";
                    valore = valore + " [ \"" + correggi(res.getTitolo()) + "\",\"" + correggi(res.getDescr()) + "\",\"" + formatStringtoStringDate(res.getDt_upload(), "yyyy-MM-dd HH:mm:ss", "dd/MM/yyyy HH:mm:ss") + "\",\"" + az + "\"],";
                }
                String x = inizio + valore.substring(0, valore.length() - 1) + fine;
                out.print(x);
            } else {
                out.print(inizio + fine);
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
    protected void querycodcom_totalr(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Db_Master db = new Db_Master();
        ArrayList<String[]> result = db.city_Italy();
        db.closeDB();
        String q = request.getParameter("q");
        try (PrintWriter out = response.getWriter()) {
            if (result.size() > 0) {
                Gson gson = new Gson();
                ArrayList<String> JSONRequest = new ArrayList<>();
                boolean found = false;
                for (int i = 0; i < result.size(); i++) {
                    if (result.get(i)[0].equalsIgnoreCase(q)) {
                        JSONRequest.add(gson.toJson(result.get(i)[3]));
                        JSONRequest.add(gson.toJson(result.get(i)[2]));
                        found = true;
                        break;
                    }
                }
                if (found) {
                    out.print(JSONRequest);
                } else {
                    out.print("");
                }
            } else {
                out.print("");
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
    protected void querycodcom_distr(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String q = request.getParameter("q");
        Db_Master db = new Db_Master();
//        ArrayList<String[]> result = db.city_Italy();
        String result = Utility.sanitize(db.city_Italy(q));
        db.closeDB();
        try (PrintWriter out = response.getWriter()) {
            String inizio = "{ \"items\": [ ";
            String fine = "]}";
            if (result == null) {
                out.print(Utility.sanitize(inizio) + Utility.sanitize(fine));
            } else {
                out.print(Utility.sanitize(result));
            }
//        if (result.size() > 0) {
//            for (int i = 0; i < result.size(); i++) {
//                if (result.get(i)[0].equalsIgnoreCase(q)) {
//                    out.print(result.get(i)[3]);
//                    break;
//                }
//            }
//        } else {
//            out.print(inizio + fine);
//        }
        }
    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void querycodcom_city(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Db_Master db = new Db_Master();
        ArrayList<String[]> result = db.city_Italy();
        db.closeDB();
        String q = request.getParameter("q");
        try (PrintWriter out = response.getWriter()) {
            String inizio = "{ \"items\": [ ";
            String fine = "]}";
            if (result.size() > 0) {
                for (int i = 0; i < result.size(); i++) {
                    if (result.get(i)[0].equalsIgnoreCase(q)) {
                        out.print(result.get(i)[2]);
                        break;
                    }
                }
            } else {
                out.print(inizio + fine);
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
    protected void querycodcom_zip(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Db_Master db = new Db_Master();
        ArrayList<String[]> result = db.city_Italy();
        db.closeDB();
        String q = request.getParameter("q");
        try (PrintWriter out = response.getWriter()) {
            String inizio = "{ \"items\": [ ";
            String fine = "]}";
            if (result.size() > 0) {
                for (int i = 0; i < result.size(); i++) {
                    if (result.get(i)[5].equalsIgnoreCase(q)) {
                        out.print(result.get(i)[6]);
                        break;
                    }
                }
            } else {
                out.print(inizio + fine);
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
    protected void checkunlockcode(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        String codice = request.getParameter("q");
        Db_Master db = new Db_Master();
        boolean esito = db.codici_sblocco_isEnabled(codice);
        db.closeDB();
        out.print(Boolean.valueOf(esito));
    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void checkbranch(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Db_Master db = new Db_Master();

        PrintWriter out = response.getWriter();
        String d3 = request.getParameter("q");

        String data1 = formatStringtoStringDate_null(d3, patternnormdate_filter, patternsql);
        ArrayList<String> res = db.list_openclose_day(data1);
        ArrayList<Branch> br = db.list_branch_enabled();
        db.closeDB();

        Gson gson = new Gson();
        ArrayList<String> JSONRequest = new ArrayList<>();

        for (int i = 0; i < br.size(); i++) {
            if (!res.contains(br.get(i).getCod())) {
                JSONRequest.add(gson.toJson(formatBankBranch(br.get(i).getCod(), "BR", null, br, null).toUpperCase()));
            }
        }

        removeDuplicatesAL(JSONRequest);

        if (JSONRequest.size() > 0) {
            out.print(JSONRequest);
        } else {
            out.print("NESS");
        }

    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void checkquantitytill_SELL(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Db_Master db = new Db_Master();
        ArrayList<String[]> array_list_oc_change = db.list_oc_change_real_user(request.getSession().getAttribute("us_cod").toString());
        db.closeDB();

        boolean ok = true;
        String idopentillv = request.getParameter("idopentillv");
        for (int i = 1; i < 6 && ok; i++) {
            String kinfig = request.getParameter("kinfig" + i);
            String codfig = request.getParameter("codfig" + i);
            String quafig = request.getParameter("quafig" + i);
            if (kinfig != null
                    && !codfig.equals("")) {
                if (codfig.contains(" ")) {
                    codfig = request.getParameter("codfig" + i).split(" ")[0];
                }
                boolean found = false;
                for (int k = 0; k < array_list_oc_change.size() && ok; k++) {
                    String ch = array_list_oc_change.get(k)[0];
                    if (idopentillv.equals(ch)) {
                        if (kinfig.equals(array_list_oc_change.get(k)[1]) && codfig.equals(array_list_oc_change.get(k)[2])) {
                            found = true;
                            if (fd(formatDoubleforMysql(quafig)) > fd(array_list_oc_change.get(k)[3])) {
                                ok = false;
                            }
                        }
                    }
                }
                if (!found) {
                    ok = false;
                }
            }
        }

        try (PrintWriter out = response.getWriter()) {
            out.print(Boolean.valueOf(ok));
        }

    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void checkquantitytill_BUY(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Db_Master db = new Db_Master();
        ArrayList<String[]> array_list_oc_change = db.list_oc_change_real_user(request.getSession().getAttribute("us_cod").toString());
        String local_cur = db.get_local_currency()[0];
        db.closeDB();
        String idopentillv = request.getParameter("idopentillv");
        String payout1 = request.getParameter("payout1");
        boolean found = false;
        boolean ok = true;

        for (int i = 0; i < array_list_oc_change.size(); i++) {
            String ch = array_list_oc_change.get(i)[0];
            if (idopentillv.equals(ch)) {
                if (array_list_oc_change.get(i)[1].equals("01") && local_cur.equals(array_list_oc_change.get(i)[2])) {
                    found = true;
                    if (fd(formatDoubleforMysql(payout1)) > fd(array_list_oc_change.get(i)[3])) {
                        ok = false;
                    }
                }
            }
        }
        if (!found) {
            ok = false;
        }

        try (PrintWriter out = response.getWriter()) {
            out.print(Boolean.valueOf(ok));
        }
    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void check_quantity_WK(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String cat1 = request.getParameter("q1");
        String cat2 = request.getParameter("q2");

        boolean check1 = Boolean.valueOf(request.getParameter("c1"));
        boolean check2 = Boolean.valueOf(request.getParameter("c2"));

        String idopentillv = request.getParameter("idopentillv");
        Db_Master db6 = new Db_Master();
        ArrayList<String[]> list_oc_nochange = db6.list_oc_nochange_real(idopentillv);
        db6.closeDB();

        boolean ok = true;
        if (check1) {
            String quantold1 = "0";
            for (int i = 0; i < list_oc_nochange.size(); i++) {
                if (list_oc_nochange.get(i)[1].equalsIgnoreCase(cat1)) {
                    quantold1 = list_oc_nochange.get(i)[2];
                    break;
                }
            }
            if (fd(quantold1) < 1) {
                ok = false;
            }

        }

        if (check2) {
            String quantold2 = "0";
            for (int i = 0; i < list_oc_nochange.size(); i++) {
                if (list_oc_nochange.get(i)[1].equalsIgnoreCase(cat2)) {
                    quantold2 = list_oc_nochange.get(i)[2];
                    break;
                }
            }
            if (fd(quantold2) < 1) {
                ok = false;
            }
        }
        try (PrintWriter out = response.getWriter()) {
            out.print(ok);
        }

    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void query_quantity_WK(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Db_Master db = new Db_Master();
        ArrayList<Till> listTill = db.list_till_status(null, null, filiale);
        ArrayList<String[]> list_oc_nochange_open = db.list_oc_nochange_real();
        ArrayList<String[]> list_oc_nochange_closed = db.list_oc_nochange(2);
        db.closeDB();

        PrintWriter out = response.getWriter();
        String val_cat = request.getParameter("q");
        Gson gson = new Gson();
        ArrayList<String> JSONRequest = new ArrayList<>();

        for (int i = 0; i < listTill.size(); i++) {
            String n1 = listTill.get(i).getName();
            String id = listTill.get(i).getId_opcl();
            String qu1 = "0";
            if (listTill.get(i).getTy_opcl().equalsIgnoreCase("OPEN")) {
                for (int j = 0; j < list_oc_nochange_open.size(); j++) {
                    String idc = list_oc_nochange_open.get(j)[0];
                    String nccat = list_oc_nochange_open.get(j)[1];
                    String qua = list_oc_nochange_open.get(j)[2];
                    if (idc.equalsIgnoreCase(id) && val_cat.equalsIgnoreCase(nccat)) {
                        qu1 = qua;
                        break;
                    }
                }
            } else {
                for (int j = 0; j < list_oc_nochange_closed.size(); j++) {
                    String idc = list_oc_nochange_closed.get(j)[0];
                    String nccat = list_oc_nochange_closed.get(j)[1];
                    String qua = list_oc_nochange_closed.get(j)[2];
                    if (idc.equalsIgnoreCase(id) && val_cat.equalsIgnoreCase(nccat)) {
                        qu1 = qua;
                        break;
                    }
                }
            }

            qu1 = formatMysqltoDisplay(roundDoubleandFormat(fd(qu1), 0));

            JSONRequest.add(gson.toJson(n1));
            JSONRequest.add(gson.toJson(qu1));

        }

        if (JSONRequest.size() > 0) {
            out.print(JSONRequest);
        } else {
            out.print("");
        }

    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void queryquantitynochange(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Db_Master db = new Db_Master();

        ArrayList<Till> listTill = db.list_till_status(null, null, filiale);

        ArrayList<String[]> list_oc_nochange_open = db.list_oc_nochange_real();
        ArrayList<String[]> list_oc_nochange_closed = db.list_oc_nochange(2);

        db.closeDB();
        PrintWriter out = response.getWriter();
        String val_cat = request.getParameter("q");
        Gson gson = new Gson();
        ArrayList<String> JSONRequest = new ArrayList<>();

        for (int i = 0; i < listTill.size(); i++) {
            String n1 = listTill.get(i).getName();
            String id = listTill.get(i).getId_opcl();
            String qu1 = "0";
            if (listTill.get(i).getTy_opcl().equalsIgnoreCase("OPEN")) {
                for (int j = 0; j < list_oc_nochange_open.size(); j++) {
                    String idc = list_oc_nochange_open.get(j)[0];
                    String nccat = list_oc_nochange_open.get(j)[1];
                    String qua = list_oc_nochange_open.get(j)[2];
                    if (idc.equalsIgnoreCase(id) && val_cat.equalsIgnoreCase(nccat)) {
                        qu1 = qua;
                        break;
                    }
                }
            } else {
                for (int j = 0; j < list_oc_nochange_closed.size(); j++) {
                    String idc = list_oc_nochange_closed.get(j)[0];
                    String nccat = list_oc_nochange_closed.get(j)[1];
                    String qua = list_oc_nochange_closed.get(j)[2];
                    if (idc.equalsIgnoreCase(id) && val_cat.equalsIgnoreCase(nccat)) {
                        qu1 = qua;
                        break;
                    }
                }
            }

            qu1 = formatMysqltoDisplay(roundDoubleandFormat(fd(qu1), 0));

            JSONRequest.add(gson.toJson(n1));
            JSONRequest.add(gson.toJson(qu1));

        }

        if (JSONRequest.size() > 0) {
            out.print(JSONRequest);
        } else {
            out.print("");
        }

    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void queryfidelitycode(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Db_Master db = new Db_Master(true);

        if (db.getC() == null) {
            db = new Db_Master();
        }

        ArrayList<Currency> cu = db.list_currency(filiale);

        PrintWriter out = response.getWriter();
        String q = request.getParameter("q");
        if (q.length() == 18) {

            ArrayList<String[]> result = db.list_BB_waiting(q.substring(0, 3), q.substring(3));
            if (result.size() > 0) {
                Gson gson = new Gson();
                ArrayList<String> JSONRequest = new ArrayList<>();
                for (int i = 0; i < result.size(); i++) {
                    String[] valuebb = result.get(i);
                    String codice_tr = valuebb[0];
                    ArrayList<String[]> value = new ArrayList<>();
                    ArrayList<Ch_transaction_value> valori = db.query_transaction_value(codice_tr);
                    for (int c = 0; c < valori.size(); c++) {
                        if (valori.get(c).getBb().equals("Y") || valori.get(c).getBb().equals("F")) {
                            String[] valoridaimpostare = {valori.get(c).getValuta().trim(),
                                formatALCurrency(valori.get(c).getValuta().trim(), cu),
                                valori.get(c).getQuantita(), valori.get(c).getRate()};
                            value.add(valoridaimpostare);
                        }
                    }
//                  String idtr =  valuebb[1];
//                  String idfil = valuebb[2];

                    String idcl = valuebb[4];
                    String kind = valuebb[5];

//                    Client cl = db.query_Client(idcl);
                    Client cl = db.query_Client_transaction(codice_tr, idcl);

                    if (cl == null || cl.getCode().equals("---")) {
                        JSONRequest.add(gson.toJson(""));
                        JSONRequest.add(gson.toJson(""));
                        JSONRequest.add(gson.toJson(""));
                        JSONRequest.add(gson.toJson(""));
                        JSONRequest.add(gson.toJson(""));
                        JSONRequest.add(gson.toJson(""));
                        JSONRequest.add(gson.toJson(""));
                        JSONRequest.add(gson.toJson(""));
                        JSONRequest.add(gson.toJson(""));
                        JSONRequest.add(gson.toJson(""));
                        JSONRequest.add(gson.toJson(""));
                        JSONRequest.add(gson.toJson(""));
                        JSONRequest.add(gson.toJson(""));
                        JSONRequest.add(gson.toJson(""));
                        JSONRequest.add(gson.toJson(""));
                        JSONRequest.add(gson.toJson(""));
                        JSONRequest.add(gson.toJson(""));
                        JSONRequest.add(gson.toJson(""));
                        JSONRequest.add(gson.toJson(""));
                        JSONRequest.add(gson.toJson(""));
                        JSONRequest.add(gson.toJson(""));
                        JSONRequest.add(gson.toJson(""));
                        JSONRequest.add(gson.toJson(""));
                        JSONRequest.add(gson.toJson("-"));
                        JSONRequest.add(gson.toJson("-"));
                        JSONRequest.add(gson.toJson("-"));
                        JSONRequest.add(gson.toJson(kind));//26

                    } else {

                        JSONRequest.add(gson.toJson(cl.getCognome()));
                        JSONRequest.add(gson.toJson(cl.getNome()));
                        JSONRequest.add(gson.toJson(cl.getNazione()));
                        JSONRequest.add(gson.toJson(cl.getCitta()));
                        JSONRequest.add(gson.toJson(cl.getIndirizzo()));
                        JSONRequest.add(gson.toJson(cl.getCap()));
                        JSONRequest.add(gson.toJson(cl.getProvincia()));
                        JSONRequest.add(gson.toJson(cl.getTipo_documento()));
                        JSONRequest.add(gson.toJson(cl.getNumero_documento()));
                        JSONRequest.add(gson.toJson(cl.getDt_rilascio_documento()));
                        JSONRequest.add(gson.toJson(cl.getDt_scadenza_documento()));
                        JSONRequest.add(gson.toJson(cl.getRilasciato_da_documento()));
                        JSONRequest.add(gson.toJson(cl.getLuogo_rilascio_documento()));
                        JSONRequest.add(gson.toJson(cl.getEmail()));
                        JSONRequest.add(gson.toJson(cl.getTelefono()));
                        JSONRequest.add(gson.toJson(cl.getCode()));
                        JSONRequest.add(gson.toJson(cl.getCodfisc())); //16
                        JSONRequest.add(gson.toJson(cl.getSesso()));//17
                        JSONRequest.add(gson.toJson(cl.getPep()));//18
                        JSONRequest.add(gson.toJson(cl.getCitta_nascita()));//19
                        JSONRequest.add(gson.toJson(cl.getProvincia_nascita()));//20
                        JSONRequest.add(gson.toJson(cl.getNazione_nascita()));//21
                        JSONRequest.add(gson.toJson(cl.getDt_nascita()));//22

                        String[] citta = getCity_apm(cl.getCitta());
                        if (citta != null) {
                            JSONRequest.add(gson.toJson(citta[1]));
                        } else {
                            JSONRequest.add(gson.toJson("-"));
                        } //23

                        String[] prov = get_district(cl.getProvincia());
                        if (prov != null) {
                            JSONRequest.add(gson.toJson(prov[1]));
                        } else {
                            JSONRequest.add(gson.toJson("-"));
                        } //24

                        String[] prov_na = get_district(cl.getProvincia_nascita());
                        if (prov_na != null) {
                            JSONRequest.add(gson.toJson(prov_na[1]));
                        } else {
                            JSONRequest.add(gson.toJson("-"));
                        } //25

                        JSONRequest.add(gson.toJson(kind));//26

                    }

                    if (!is_IT && cl != null && cl.getRepceca() != null) {
                        JSONRequest.add(gson.toJson(cl.getRepceca().getHeavy_cz_country()));
                        JSONRequest.add(gson.toJson(cl.getRepceca().getHeavy_cz_issuingcountry()));
                        JSONRequest.add(gson.toJson(cl.getRepceca().getHeavy_sanctions()));
                        JSONRequest.add(gson.toJson(formatYN(cl.getRepceca().getHeavy_pep())));
                        JSONRequest.add(gson.toJson(cl.getRepceca().getHeavy_transactionre()));
                        JSONRequest.add(gson.toJson(cl.getRepceca().getHeavy_moneysource()));
                        JSONRequest.add(gson.toJson(cl.getRepceca().getOccupation()));

                        JSONRequest.add(gson.toJson(cl.getRepceca().getPep_position()));
                        JSONRequest.add(gson.toJson(cl.getRepceca().getPep_country()));
                    } else {
                        JSONRequest.add(gson.toJson(""));
                        JSONRequest.add(gson.toJson(""));
                        JSONRequest.add(gson.toJson(""));
                        JSONRequest.add(gson.toJson(""));
                        JSONRequest.add(gson.toJson(""));
                        JSONRequest.add(gson.toJson(""));
                        JSONRequest.add(gson.toJson(""));
                        JSONRequest.add(gson.toJson(""));
                        JSONRequest.add(gson.toJson(""));
                    }

                    JSONRequest.add(gson.toJson(value.size()));
                    for (int c = 0; c < value.size(); c++) {
                        JSONRequest.add(gson.toJson(value.get(c)[0]));
                        JSONRequest.add(gson.toJson(value.get(c)[1]));
                        JSONRequest.add(gson.toJson(value.get(c)[2]));
                        JSONRequest.add(gson.toJson(value.get(c)[3]));
                    }

                }
                if (JSONRequest.size() > 0) {
                    out.print(Utility.sanitize(JSONRequest.toString()));
                } else {
                    out.print("");
                }
            } else {
                out.print("");
            }
        } else {
            out.print("");
        }

        db.closeDB();

    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void queryfidelitycodesb(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Db_Master db1 = new Db_Master();
        ArrayList<Currency> cu = db1.list_currency_buy_sell("0", "1", filiale);
        ArrayList<String[]> best_rate = db1.rate_currency_best_SB(cu);
        db1.closeDB();

        PrintWriter out = response.getWriter();
        String q = request.getParameter("q");

//        q = convertFIDCODE(q);
        if (q.length() == 18) {

            Db_Master db = new Db_Master(true);
            if (db.getC() == null) {
                db = new Db_Master();
            }
            ArrayList<String[]> result = db.list_SB_waiting(q.substring(0, 3), q.substring(3));
            db.closeDB();

            if (result.size() > 0) {
                Gson gson = new Gson();
                ArrayList<String> JSONRequest = new ArrayList<>();
                for (int i = 0; i < result.size(); i++) {
                    String[] valuebb = result.get(i);
                    String codice_tr = valuebb[0];
                    ArrayList<String[]> value = new ArrayList<>();
                    for (int c = 0; c < best_rate.size(); c++) {
                        String[] valori = best_rate.get(c);
                        String[] valoridaimpostare = {
                            valori[2].trim(),
                            formatALCurrency(valori[2].trim(), cu),
                            valori[1],
                            valori[0]};
                        value.add(valoridaimpostare);
                    }
                    String idcl = valuebb[4];
                    String kind = valuebb[5];
                    Db_Master db2 = new Db_Master(true);
                    if (db2.getC() == null) {
                        db2 = new Db_Master();
                    }
                    Client cl = db2.query_Client_transaction(codice_tr, idcl);
                    db2.closeDB();

                    if (cl == null || cl.getCode().equals("---")) {
                        JSONRequest.add(gson.toJson(""));
                        JSONRequest.add(gson.toJson(""));
                        JSONRequest.add(gson.toJson(""));
                        JSONRequest.add(gson.toJson(""));
                        JSONRequest.add(gson.toJson(""));
                        JSONRequest.add(gson.toJson(""));
                        JSONRequest.add(gson.toJson(""));
                        JSONRequest.add(gson.toJson(""));
                        JSONRequest.add(gson.toJson(""));
                        JSONRequest.add(gson.toJson(""));
                        JSONRequest.add(gson.toJson(""));
                        JSONRequest.add(gson.toJson(""));
                        JSONRequest.add(gson.toJson(""));
                        JSONRequest.add(gson.toJson(""));
                        JSONRequest.add(gson.toJson(""));
                        JSONRequest.add(gson.toJson(""));
                        JSONRequest.add(gson.toJson(""));
                        JSONRequest.add(gson.toJson(""));
                        JSONRequest.add(gson.toJson(""));
                        JSONRequest.add(gson.toJson(""));
                        JSONRequest.add(gson.toJson(""));
                        JSONRequest.add(gson.toJson(""));
                        JSONRequest.add(gson.toJson(""));
                        JSONRequest.add(gson.toJson("-"));
                        JSONRequest.add(gson.toJson("-"));
                        JSONRequest.add(gson.toJson("-"));
                        JSONRequest.add(gson.toJson(kind));//26
                    } else {
                        JSONRequest.add(gson.toJson(cl.getCognome()));
                        JSONRequest.add(gson.toJson(cl.getNome()));
                        JSONRequest.add(gson.toJson(cl.getNazione()));
                        JSONRequest.add(gson.toJson(cl.getCitta()));
                        JSONRequest.add(gson.toJson(cl.getIndirizzo()));
                        JSONRequest.add(gson.toJson(cl.getCap()));
                        JSONRequest.add(gson.toJson(cl.getProvincia()));
                        JSONRequest.add(gson.toJson(cl.getTipo_documento()));
                        JSONRequest.add(gson.toJson(cl.getNumero_documento()));
                        JSONRequest.add(gson.toJson(cl.getDt_rilascio_documento()));
                        JSONRequest.add(gson.toJson(cl.getDt_scadenza_documento()));
                        JSONRequest.add(gson.toJson(cl.getRilasciato_da_documento()));
                        JSONRequest.add(gson.toJson(cl.getLuogo_rilascio_documento()));
                        JSONRequest.add(gson.toJson(cl.getEmail()));
                        JSONRequest.add(gson.toJson(cl.getTelefono()));
                        JSONRequest.add(gson.toJson(cl.getCode()));
                        JSONRequest.add(gson.toJson(cl.getCodfisc())); //16
                        JSONRequest.add(gson.toJson(cl.getSesso()));//17
                        JSONRequest.add(gson.toJson(cl.getPep()));//18
                        JSONRequest.add(gson.toJson(cl.getCitta_nascita()));//19
                        JSONRequest.add(gson.toJson(cl.getProvincia_nascita()));//20
                        JSONRequest.add(gson.toJson(cl.getNazione_nascita()));//21
                        JSONRequest.add(gson.toJson(cl.getDt_nascita()));//22

                        String[] citta = getCity_apm(cl.getCitta());
                        if (citta != null) {
                            JSONRequest.add(gson.toJson(citta[1]));
                        } else {
                            JSONRequest.add(gson.toJson("-"));
                        } //23

                        String[] prov = get_district(cl.getProvincia());
                        if (prov != null) {
                            JSONRequest.add(gson.toJson(prov[1]));
                        } else {
                            JSONRequest.add(gson.toJson("-"));
                        } //24

                        String[] prov_na = get_district(cl.getProvincia_nascita());
                        if (prov_na != null) {
                            JSONRequest.add(gson.toJson(prov_na[1]));
                        } else {
                            JSONRequest.add(gson.toJson("-"));
                        } //25

                        JSONRequest.add(gson.toJson(kind));//26

                    }

                    if (!is_IT && cl != null && cl.getRepceca() != null) {
                        JSONRequest.add(gson.toJson(cl.getRepceca().getHeavy_cz_country()));
                        JSONRequest.add(gson.toJson(cl.getRepceca().getHeavy_cz_issuingcountry()));
                        JSONRequest.add(gson.toJson(cl.getRepceca().getHeavy_sanctions()));
                        JSONRequest.add(gson.toJson(formatYN(cl.getRepceca().getHeavy_pep())));
                        JSONRequest.add(gson.toJson(cl.getRepceca().getHeavy_transactionre()));
                        JSONRequest.add(gson.toJson(cl.getRepceca().getHeavy_moneysource()));
                        JSONRequest.add(gson.toJson(cl.getRepceca().getOccupation()));
                        JSONRequest.add(gson.toJson(cl.getRepceca().getPep_position()));
                        JSONRequest.add(gson.toJson(cl.getRepceca().getPep_country()));
                    } else {
                        JSONRequest.add(gson.toJson(""));
                        JSONRequest.add(gson.toJson(""));
                        JSONRequest.add(gson.toJson(""));
                        JSONRequest.add(gson.toJson(""));
                        JSONRequest.add(gson.toJson(""));
                        JSONRequest.add(gson.toJson(""));
                        JSONRequest.add(gson.toJson(""));
                        JSONRequest.add(gson.toJson(""));
                        JSONRequest.add(gson.toJson(""));
                    }

                    JSONRequest.add(gson.toJson(value.size()));
                    for (int c = 0; c < value.size(); c++) {
                        JSONRequest.add(gson.toJson(value.get(c)[0]));
                        JSONRequest.add(gson.toJson(value.get(c)[1]));
                        JSONRequest.add(gson.toJson(value.get(c)[2]));
                        JSONRequest.add(gson.toJson(value.get(c)[3]));
                    }
                }
                if (JSONRequest.size() > 0) {
                    out.print(Utility.sanitize(JSONRequest.toString()));
                } else {
                    out.print("");
                }
            } else {
                out.print("");
            }
        } else {
            out.print("");
        }

    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void querycountry(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Db_Master db = new Db_Master();
        ArrayList<String[]> result = db.country_cf();
        db.closeDB();
        String q = request.getParameter("q");
        try (PrintWriter out = response.getWriter()) {
            String inizio = "{ \"items\": [ ";
            String fine = "]}";
            if (result.size() > 0) {
                for (int i = 0; i < result.size(); i++) {
                    if (result.get(i)[2].equalsIgnoreCase(q)) {
                        out.print(result.get(i)[0]);
                        break;
                    }
                }
            } else {
                out.print(inizio + fine);
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
    protected void city_select(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String q = request.getParameter("q");

        Db_Master db = new Db_Master();
//        ArrayList<String[]> result = db.city_Italy_APM_like(q);
//        ArrayList<String[]> result = db.city_Italy_APM();
        ArrayList<City> result = db.query_city_Italy(q);
        db.closeDB();
        try (PrintWriter out = response.getWriter()) {
            String inizio = "{ \"items\": [ ";
            String fine = "]}";
            String valore = "";
            if (result.size() > 0) {
                for (int i = 0; i < result.size(); i++) {

//                boolean presente;
//
//                if (Constant.tr_1323) {
//                    presente = result.get(i)[1].toLowerCase().startsWith(q.toLowerCase());
//                } else {
//                    presente = result.get(i)[1].toLowerCase().contains(q.toLowerCase());
//                }
//
//                if (presente) {
//                    valore = valore + "{"
//                            + "      \"id\": \"" + result.get(i)[0] + "\","
//                            + "      \"name\": \"" + result.get(i)[1] + "\","
//                            + "      \"full_name\": \"" + result.get(i)[1] + "\""
//                            + "},";
//                }
                    valore = valore + "{"
                            + "      \"id\": \"" + result.get(i).getCodice_avv_bancario() + "\","
                            + "      \"name\": \"" + result.get(i).getDenominazione() + "\","
                            + "      \"full_name\": \"" + result.get(i).getDenominazione() + "\""
                            + "},";

                }
                String x = inizio + valore.substring(0, valore.length() - 1) + fine;
                out.print(Utility.sanitize(x));
            } else {
                out.print(Utility.sanitize(inizio) + Utility.sanitize(fine));
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
    protected void district_select(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Db_Master db = new Db_Master();
        ArrayList<String[]> result = db.district();
        db.closeDB();
        String q = request.getParameter("q");
        try (PrintWriter out = response.getWriter()) {
            String inizio = "{ \"items\": [ ";
            String fine = "]}";
            String valore = "";
            if (result.size() > 0) {
                for (int i = 0; i < result.size(); i++) {
                    if (result.get(i)[1].toLowerCase().contains(q.toLowerCase())) {
                        valore = valore + "{"
                                + "      \"id\": \"" + result.get(i)[0] + "\","
                                + "      \"name\": \"" + result.get(i)[1] + "\","
                                + "      \"full_name\": \"" + result.get(i)[1] + "\""
                                + "    },";
                    }
                }
                if (valore.equals("")) {
                    out.print(inizio + fine);
                } else {
                    String x = inizio + valore.substring(0, valore.length() - 1) + fine;
                    out.print(x);
                }
            } else {
                out.print(inizio + fine);
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
    protected void kyc_list(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain; charset=ISO-8859-1");
        response.setCharacterEncoding("ISO-8859-1");

        String branch = request.getParameter("branch").trim();

        String d1 = request.getParameter("d1");
        String d2 = request.getParameter("d2");
        String cl_cog = replaceApici(request.getParameter("cl_cog"));
        String cl_na = replaceApici(request.getParameter("cl_na"));

        String data1 = formatStringtoStringDate_null(d1, patternnormdate_filter, patternsql);
        String data2 = formatStringtoStringDate_null(d2, patternnormdate_filter, patternsql);

        Db_Master db = new Db_Master();
        Office of = db.get_national_office();
        ArrayList<Ch_transaction_doc> result = db.query_kyc_list(data1, data2, cl_cog, cl_na, branch);
        db.closeDB();
        try (PrintWriter out = response.getWriter()) {
            String inizio = "{ \"aaData\": [";
            String fine = "] }";
            String valore = "";
            if (result.size() > 0) {
                for (int i = 0; i < result.size(); i++) {
                    Ch_transaction_doc doc = result.get(i);
                    Db_Master db1 = new Db_Master();
                    Ch_transaction tr = db1.query_transaction_ch(doc.getCodtr());
                    Client cl = db1.query_Client_transaction(tr.getCod(), doc.getClient());
                    db1.closeDB();
                    String az = "<form method='post' target='_blank' action='Download'>"
                            + "<input type='hidden' name='cod' value='" + doc.getCodice_documento() + "'/>"
                            + "<input type='hidden' name='type' value='view_doc_tr_attachments'/>"
                            + "<button type='submit' class='btn btn-sm btn-circle white popovers' container='body' data-trigger='hover' "
                            + "data-container='body' data-placement='top' data-content='View File'><i class='fa fa-file-pdf-o'></i></button></form>";
                    Db_Master db01 = new Db_Master();
                    String type = db01.getMotivation(doc.getCodtr()).toUpperCase();
                    db01.closeDB();
                    if (type.equals("-")) {
                        if (cl.getPep().equalsIgnoreCase("YES")) {
                            type = "PEP";
                        } else if (checkSogliaValueofTransaction_client(cl, of, tr)) {
                            type = "THRESHOLD";
                        } else {
                            type = "USER";
                        }
                    }
                    valore = valore
                            + " [ \""
                            + az + "\",\""
                            + tr.getFiliale()
                            + "\",\"" + cl.getCognome().toUpperCase()
                            + "\",\"" + cl.getNome().toUpperCase()
                            + "\",\"" + type
                            + "\",\"" + formatStringtoStringDate_null(doc.getData_load(), patternsqldate, patternnormdate)
                            + "\"],";
                }
                String x = inizio + valore.substring(0, valore.length() - 1) + fine;
                out.print(x);
            } else {
                out.print(inizio + fine);
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
    protected void prenot_list(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/plain; charset=ISO-8859-1");
        response.setCharacterEncoding("ISO-8859-1");
        Db_Master db = new Db_Master();
        String branch[];

        String tipoweb = request.getParameter("tipoweb");
        String br1 = request.getParameter("branch");

        if (br1 == null || br1.trim().equals("") || br1.trim().equals(";")) {
            branch = parseArrayValues(db.list_branchcode_completeAFTER311217());
        } else {
            branch = parseArrayValues(request.getParameter("branch"), ";");
        }

        String status = request.getParameter("status");
        String d1 = request.getParameter("d1");
        String d2 = request.getParameter("d2");

//        ArrayList<Booking> result = new ArrayList<>();
        ArrayList<Booking> result = db.query_prenot_list_new(branch, status, d1, d2, tipoweb);

        ArrayList<Branch> br = db.list_branch();

        Branch attuale = get_Branch(filiale, br);

        ArrayList<String[]> listastati = sito_stati(null, null, true);
        ArrayList<Currency> cu = db.list_figures();
        String curdate = db.getCurdateDT().toString(patternsql);
        db.closeDB();
        try (PrintWriter out = response.getWriter()) {
            String inizio = "{ \"aaData\": [";
            String fine = "] }";
            String valore = "";
            if (result.size() > 0) {
                for (int i = 0; i < result.size(); i++) {
                    Booking bo = result.get(i);

                    boolean buy = bo.getCod().startsWith("B");

                    String link1 = "<div class='col-md-4'><form method='post' target='_blank' action='web_tran_view.jsp'><input type='hidden' name='cod' value='" + bo.getCod() + "'/>"
                            + "<button type='submit' href='tb_figures.jsp' class='btn btn-sm btn-circle white popovers' container='body' data-trigger='hover' "
                            + "data-container='body' data-placement='top' data-content='Show'><i class='fa fa-eye'></i></button></form></div>";
                    String link2 = "";
//                String link2 = "<div class='col-md-4'>"
//                        + "<a class='btn btn-sm btn-circle white popovers' container='body' data-trigger='hover' "
//                        + "data-container='body' data-placement='top' data-content='Edit' disabled><i class='fa fa-wrench'></i></a></div>";
                    String link3 = "";
//                String link3 = "<div class='col-md-4'>"
//                        + "<a disabled class='btn btn-sm btn-circle white popovers' container='body' data-trigger='hover' "
//                        + "data-container='body' data-placement='top' data-content='Sell'><i class='fa fa-exchange'></i></1>"
//                        + "</div>";
                    if ((bo.getStato().equals("0") || bo.getStato().equals("3")) && attuale.getCod().equals(bo.getFiliale())) {
//                if (bo.getStato().equals("0") && attuale.getCod().equals(bo.getFiliale()) && bo.getDt_ritiro().equals(curdate)) {

                        if (buy) {
                            link3 = "<div class='col-md-4'>"
                                    + "<form method='post' target='_blank' action='web_tran_buy.jsp'><input type='hidden' name='cod' value='" + bo.getCod() + "'/>"
                                    + "<button type='submit' class='btn btn-sm btn-circle white'><i class='fa fa-euro'></i> BUY</button></form></div>";
                        } else {
                            link3 = "<div class='col-md-4'>"
                                    + "<form method='post' target='_blank' action='web_tran_sell.jsp'><input type='hidden' name='cod' value='" + bo.getCod() + "'/>"
                                    + "<button type='submit' class='btn btn-sm btn-circle white'><i class='fa fa-euro'></i> SELL</button></form></div>";
                        }

                    }   //                if (attuale.getFg_crm().equals("1") || attuale.getFg_persgiur().equals("1")) {
//                    link2 = "<div class='col-md-4'>"
//                            + "<a href='web_tran_edit.jsp?cod=" + bo.getCod() + "' class='btn btn-sm btn-circle white popovers fancyBoxRafreload' container='body' data-trigger='hover' "
//                            + "data-container='body' data-placement='top' data-content='Edit'><i class='fa fa-wrench'></i></a></div>";
//                }

                    String az = "<div class='btn-group'>" + link1 + link2 + link3 + "</div>";
                    valore = valore
                            + " [ \"" + (buy ? "BUY" : "SELL") + "\",\""
                            + formatBankBranch(bo.getFiliale(), "BR", null, br, null) + "\",\"" + bo.getCod() + "\",\"" + bo.getCurrency() + " - " + formatALCurrency(bo.getCurrency(), cu) + "\",\"" + formatMysqltoDisplay(Utility.sanitize(bo.getTotal())) + "\",\"" + formatStringtoStringDate(bo.getDt_ritiro(), patternsql, patternnormdate_filter) + "\",\"" + visualizzaStringaMySQL(bo.getCl_cognome().toUpperCase()) + "\",\"" + visualizzaStringaMySQL(bo.getCl_nome().toUpperCase()) + "\",\"" + formatAL(bo.getStato(), listastati, 2) + "\",\"" + az + "\"],";
                }
                String x = inizio + valore.substring(0, valore.length() - 1) + fine;
                out.print(x);
            } else {
                out.print(inizio + fine);
            }
        }
    }

    private static int findCombinationsCount(double amount, double coins[]) {
        return findCombinationsCount(amount, coins, 0);
    }

    private static int findCombinationsCount(double amount, double coins[], int checkFromIndex) {
        if (amount == 0) {
            return 1;
        } else if (amount < 0 || coins.length == checkFromIndex) {
            return 0;
        } else {
            int withFirstCoin = findCombinationsCount(amount - coins[checkFromIndex], coins, checkFromIndex);
            if (withFirstCoin > 0) {
                return 1;
            }
            int withoutFirstCoin = findCombinationsCount(amount, coins, checkFromIndex + 1);
            if (withoutFirstCoin > 0) {
                return 1;
            }

            return withFirstCoin + withoutFirstCoin;
        }
    }

    private static String[] verificataglivaluta(String valuta, String kind, String total) {

        if (!kind.equals("01")) {
            String[] out = {"true", "true"};
            return out;
        }

        Db_Master db01 = new Db_Master();
        List<String> tagli = db01.figures_sizecuts_enabled().stream().filter(t1 -> t1.getValuta().equals(valuta)).map(t1 -> t1.getIp_taglio()).collect(toList());
        db01.closeDB();

//        double target = fd(formatDoubleforMysql(total));
//        double[] numbers = new double[tagli.size()];
        String tagliopiùpiccolo = "";
        double tagliopiùpiccolo1 = 1.00;
        for (int y = 0; y < tagli.size(); y++) {
//            numbers[y] = fd(tagli.get(y));
            tagliopiùpiccolo = tagli.get(y);
            tagliopiùpiccolo1 = fd(tagli.get(y));
        }

        double tagliopiùpiccoloconfrontare = fd(roundDoubleandFormat(tagliopiùpiccolo1 * 100.00, 2));
        double residuo = fd(roundDoubleandFormat(fd(formatDoubleforMysql(total)) * 100.00, 2));

        double resto = fd(roundDoubleandFormat(residuo % tagliopiùpiccoloconfrontare, 2));

        if (resto == 0) {
            String[] out = {"true", "true"};
            return out;
        }

        int coins[] = new int[tagli.size()];
        int m = tagli.size();
        int V = new Double(residuo).intValue();

        double restotagli = 0;

        for (int y = 0; y < tagli.size(); y++) {
            coins[y] = new Double(fd(tagli.get(tagli.size() - 1 - y)) * 100.00).intValue();
            restotagli = restotagli + (fd(tagli.get(tagli.size() - 1 - y)) % tagliopiùpiccolo1);
        }

        if (restotagli == 0) {
            String[] out = {"false", formatMysqltoDisplay(Utility.sanitize(tagliopiùpiccolo))};
            return out;
        }

        boolean esnew = minCoins(coins, m, V);
        if (esnew) {
            String[] out = {"true", "true"};
            return out;
        } else {
            String[] out = {"false", formatMysqltoDisplay(Utility.sanitize(tagliopiùpiccolo))};
            return out;
        }

    }

    /**
     *
     * @param coins
     * @param m
     * @param V
     * @return
     */
    public static boolean minCoins(int coins[], int m, int V) {
        int table[] = new int[V + 1];
        table[0] = 0;
        for (int i = 1; i <= V; i++) {
            table[i] = MAX_VALUE;
        }
        for (int i = 1; i <= V; i++) {
            for (int j = 0; j < m; j++) {
                if (coins[j] <= i) {
                    int sub_res = table[i - (int) coins[j]];
                    if (sub_res != MAX_VALUE
                            && sub_res + 1 < table[i]) {
                        table[i] = sub_res + 1;
                    }

                }
            }
        }

        return table[V] != MAX_VALUE;
    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void checksizecurrency(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try (//        Utility.printRequest(request);
                PrintWriter out = response.getWriter()) {
            String total = request.getParameter("total");
            String pay = request.getParameter("pay");
            String localcur = request.getParameter("localcur");
            //        Db_Master db01 = new Db_Master();
//        ArrayList<String[]> listsize = db01.currency_min_sizes();
//        db01.closeDB();
            Gson gson = new Gson();
            ArrayList<String> JSONRequest = new ArrayList<>();
            String[] ou = verificataglivaluta(localcur, pay, total);
            boolean ok = Boolean.valueOf(ou[0]);
            if (!ok) {
                JSONRequest.add(gson.toJson("Cuts of currency " + localcur + " not available. Min size is " + ou[1] + " - Check currency tables."));
            } else {
                for (int i = 1; i < 6; i++) {
                    String kind = request.getParameter("kind" + i);
                    String figs = request.getParameter("figs" + i);
                    String quantity = request.getParameter("quantity" + i);
                    if (kind != null && !figs.equals("")) {
                        ou = verificataglivaluta(figs, kind, quantity);
                        ok = Boolean.valueOf(ou[0]);
                        if (!ok) {
                            JSONRequest.add(gson.toJson("Cuts of currency " + figs + " not available. Min size is " + ou[1] + " - Check currency tables."));
                            break;
                        }
                    }
                }
            }
            if (ok) {
                out.print("");
            } else {
                out.print(Utility.sanitize(JSONRequest.toString()));
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
    protected void sogliasetclient(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
        try (PrintWriter out = response.getWriter()) {
            String company = request.getParameter("company").trim();
            String doc = request.getParameter("doc").trim();
            String nome = request.getParameter("n").trim();
            String cognome = request.getParameter("c").trim();
            String datanascita = request.getParameter("d").trim();
            String nazionenascita = request.getParameter("naz").trim();
            String codfisc = request.getParameter("cf").trim();
            String tipocliente = request.getParameter("t").trim();
            String importo = request.getParameter("i").trim();
            Db_Master db01 = new Db_Master(true);
            if (db01.getC() == null) {
                db01 = new Db_Master();
            }
            String cl_check;

            if (tipocliente.equals("003")) {
                cl_check = company;
            } else {
                cl_check = db01.get_codice_client(codfisc, tipocliente, nome, cognome, datanascita, nazionenascita);
            }
            db01.closeDB();
            Gson gson = new Gson();
            ArrayList<String> JSONRequest = new ArrayList<>();
            Db_Master db2 = new Db_Master();
            CustomerKind ck = db2.get_customerKind(tipocliente);
            db2.closeDB();
            if (ck != null) {

                double soglia = fd(ck.getIp_max_settimanale());
                double soglia_new = fd(ck.getIp_soglia_extraCEE_certification());

                soglia = get_soglia_CZ(soglia);
                soglia_new = get_soglia_CZ(soglia_new);

                if (fd(formatDoubleforMysql(importo)) > soglia) {
                    //ERRORE
                    JSONRequest.add(gson.toJson("false1"));
                    JSONRequest.add(gson.toJson("You can not complete the transaction. The payout exceeds the Max Weekly Threshold ("
                            + formatMysqltoDisplay(Utility.sanitize(ck.getIp_max_settimanale())) + ")"));
                } else if (cl_check != null) {
                    Db_Master db4A = new Db_Master();
                    double weekA = db4A.weekly_transaction(cl_check);
                    db4A.closeDB();
                    Db_Master db4 = new Db_Master(true);
                    if (db4.getC() == null) {
                        db4 = new Db_Master();
                    }
                    double week = db4.weekly_transaction_nofiliale(cl_check, filiale);
                    db4.closeDB();
                    double now = fd(formatDoubleforMysql(importo)) + week + weekA;
                    if (now > soglia) {
                        //NUOVO 09/05

                        //ERRORE
                        Db_Master db5A = new Db_Master();
                        ArrayList<String> listA = db5A.weekly_transaction_string(cl_check);
                        db5A.closeDB();

                        Db_Master db5 = new Db_Master(true);
                        if (db5.getC() == null) {
                            db5 = new Db_Master();
                        }
                        ArrayList<String> listB = db5.weekly_transaction_string_nofiliale(cl_check, filiale);
                        db5.closeDB();

                        ArrayList<String> list = new ArrayList<>();
                        list.addAll(listA);
                        list.addAll(listB);

                        JSONRequest.add(gson.toJson("false2"));
                        JSONRequest.add(gson.toJson("The Max Weekly Threshold (" + formatMysqltoDisplay(Utility.sanitize(ck.getIp_max_settimanale()))
                                + ") has been exceeded. <b>List of transaction:</b> "));
                        JSONRequest.add(gson.toJson("<table class='table table-bordered'><tr><th>BRANCH ID</th><th>DATE</th><th>TYPE</th><th>VALUE</th></tr>"));
                        for (int i = 0; i < list.size(); i++) {
                            JSONRequest.add(gson.toJson(list.get(i)));
                        }
                        JSONRequest.add(gson.toJson("</table>"));
                    } else {
                        String output = "true";
                        String msg = "";
                        if (now > soglia_new && ck.getStampa_autocertificazione().equals("1")) {
                            if (!doc.equals("PS") && !doc.equals("PD")) {
                                output = "false3";
                                msg = "[WARNING!]: (Weekly Threshold) For amounts equal to or higher than "
                                        + formatMysqltoDisplay(Utility.sanitize(ck.getIp_soglia_extraCEE_certification()))
                                        + " Euro, the <b>PASSPORT</b> is the only acceptable I.D.";
                            }
                        }
                        JSONRequest.add(gson.toJson(output));
                        JSONRequest.add(gson.toJson(msg));
                    }
                } else {
                    String output = "true";
                    String msg = "";
                    if (fd(formatDoubleforMysql(importo)) > soglia_new && ck.getStampa_autocertificazione().equals("1")) {
                        if (!doc.equals("PS") && !doc.equals("PD")) {
                            output = "false3";
                            msg = "[WARNING!]: For amounts equal to or higher than "
                                    + formatMysqltoDisplay(Utility.sanitize(ck.getIp_soglia_extraCEE_certification())) + " Euro, the <b>PASSPORT</b> is the only acceptable I.D.";
                        }
                    }
                    JSONRequest.add(gson.toJson(output));
                    JSONRequest.add(gson.toJson(msg));
                }
            }
            out.print(Utility.sanitize(JSONRequest.toString()));
        }
    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void verificaclientstraniero(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String ti = request.getParameter("ti").trim();
        String co1 = request.getParameter("co1").trim();
        String na1 = request.getParameter("na1").trim();
        String nz1 = request.getParameter("nz1").trim();
        String dn1 = request.getParameter("dn1").trim();

        String[] buysell = {"-", "-"};
        Db_Master dblocal = new Db_Master();
        Client cl = dblocal.query_Client_STRANIERO(co1, na1, nz1, dn1);

        if (cl == null) {
            Db_Master db = new Db_Master(true);
            if (db.getC() == null) {
                db = new Db_Master();
            }
            cl = db.query_Client_STRANIERO(co1, na1, nz1, dn1);
            if (cl != null) {
                buysell = db.getClientCommission(cl.getCode());
            }
            db.closeDB();
        } else {
            buysell = dblocal.getClientCommission(cl.getCode());
        }
        dblocal.closeDB();

        try (//        Db_Master db = new Db_Master(true);
                //        if (db.getC() == null) {
                //            db = new Db_Master();
                //        }
                //        Client cl = db.query_Client_STRANIERO(co1, na1, nz1, dn1);
                //
                //        String[] buysell = {"-", "-"};
                //        if (cl != null) {
                //            buysell = db.getClientCommission(cl.getCode());
                //        }
                //
                //        db.closeDB();
                //
                //
                //        
                //        sdasasda
                //
                PrintWriter out = response.getWriter()) {
            Gson gson = new Gson();
            ArrayList<String> JSONRequest = new ArrayList<>();
            if (cl != null) {
                JSONRequest.add(gson.toJson("OK"));
                JSONRequest.add(gson.toJson(cl.getSesso()));
                JSONRequest.add(gson.toJson(cl.getNazione()));
                JSONRequest.add(gson.toJson(cl.getCitta()));
                JSONRequest.add(gson.toJson(cl.getPep()));
                JSONRequest.add(gson.toJson(cl.getIndirizzo()));
                JSONRequest.add(gson.toJson(cl.getCap()));
                JSONRequest.add(gson.toJson(cl.getCitta_nascita()));
                String[] prov = get_district(cl.getProvincia_nascita());
                if (prov != null) {
                    JSONRequest.add(gson.toJson(prov[1]));
                } else {
                    JSONRequest.add(gson.toJson("-"));
                }
                JSONRequest.add(gson.toJson(cl.getTipo_documento()));
                JSONRequest.add(gson.toJson(cl.getNumero_documento()));
                JSONRequest.add(gson.toJson(cl.getDt_rilascio_documento()));
                JSONRequest.add(gson.toJson(cl.getDt_scadenza_documento()));
                JSONRequest.add(gson.toJson(cl.getRilasciato_da_documento()));
                JSONRequest.add(gson.toJson(cl.getLuogo_rilascio_documento()));
                JSONRequest.add(gson.toJson(cl.getEmail()));
                JSONRequest.add(gson.toJson(cl.getTelefono()));
                if (ti.equals("B")) {
                    JSONRequest.add(gson.toJson(buysell[0]));
                } else {
                    JSONRequest.add(gson.toJson(buysell[1]));
                }
                JSONRequest.add(gson.toJson(cl.getCode()));
                Db_Loy dbl = new Db_Loy();
                if (dbl.getC() != null) {
                    String lo = dbl.getCodiceClienteAttivo(cl.getCode());
                    dbl.closeDB();
                    if (lo != null) {
                        JSONRequest.add(gson.toJson(lo));
                    } else {
                        JSONRequest.add(gson.toJson(""));
                    }
                } else {
                    JSONRequest.add(gson.toJson(""));
                }   //CZ - UK
                if (!is_IT && cl.getRepceca() != null) {
                    JSONRequest.add(gson.toJson(cl.getRepceca().getHeavy_cz_country()));
                    JSONRequest.add(gson.toJson(cl.getRepceca().getHeavy_cz_issuingcountry()));
                    JSONRequest.add(gson.toJson(cl.getRepceca().getHeavy_sanctions()));
                    JSONRequest.add(gson.toJson(formatYN(cl.getRepceca().getHeavy_pep())));
                    JSONRequest.add(gson.toJson(cl.getRepceca().getHeavy_transactionre()));
                    JSONRequest.add(gson.toJson(cl.getRepceca().getHeavy_moneysource()));
                    JSONRequest.add(gson.toJson(cl.getRepceca().getOccupation()));
                    JSONRequest.add(gson.toJson(cl.getRepceca().getPep_position()));
                    JSONRequest.add(gson.toJson(cl.getRepceca().getPep_country()));
                } else {
                    JSONRequest.add(gson.toJson(""));
                    JSONRequest.add(gson.toJson(""));
                    JSONRequest.add(gson.toJson(""));
                    JSONRequest.add(gson.toJson(""));
                    JSONRequest.add(gson.toJson(""));
                    JSONRequest.add(gson.toJson(""));
                    JSONRequest.add(gson.toJson(""));
                    JSONRequest.add(gson.toJson(""));
                    JSONRequest.add(gson.toJson(""));
                }
            } else {
                JSONRequest.add(gson.toJson("KO"));
            }
            out.print(Utility.sanitize(JSONRequest.toString()));
        }

    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void searchloy(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/plain; charset=ISO-8859-1");
        response.setCharacterEncoding("ISO-8859-1");
        //String user = (String) request.getSession().getAttribute("us_cod");

        String loy = request.getParameter("loy");
        String surname = request.getParameter("surname");
        String name = request.getParameter("name");
        String taxcode = request.getParameter("taxcode");

        Db_Master db = new Db_Master(true);
        ArrayList<String[]> result = db.query_LOY(loy, surname, name, taxcode);
        db.closeDB();

        try (PrintWriter out = response.getWriter()) {
            String inizio = "{ \"aaData\": [";
            String fine = "] }";
            String valore = "";
            if (result.size() > 0) {
                for (int i = 0; i < result.size(); i++) {
                    String[] output = result.get(i);
                    String link1 = "<div class='col-md-4'>"
                            + "<a href='tb_loyalty_list.jsp?codcl=" + output[4] + "' class='btn btn-sm btn-circle white popovers fancyBoxRafFULL' container='body' data-trigger='hover' "
                            + "data-container='body' data-placement='top' data-content='View List Transaction'><i class='fa fa-list'></i></a></div>";
                    String link2 = "<div class='col-md-4'>"
                            + "<a href='tb_loyalty_mod.jsp?codcl=" + output[4] + "' class='btn btn-sm btn-circle white popovers fancyBoxRafFULL' container='body' data-trigger='hover' "
                            + "data-container='body' data-placement='top' data-content='Assign NEW Loyalty Code'><i class='fa fa-wrench'></i></a></div>";

                    String az = "<div class='btn-group'>" + link1 + link2 + "</div>";
                    valore = valore + " [ \""
                            + output[0]
                            + "\",\"" + output[1]
                            + "\",\"" + output[2]
                            + "\",\"" + output[3]
                            + "\",\"" + az
                            + "\"],";
                }
                String x = inizio + valore.substring(0, valore.length() - 1) + fine;
                out.print(x);
            } else {
                out.print(inizio + fine);
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
    protected void verificanewuk(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String co1 = request.getParameter("co1").trim();
        String na1 = request.getParameter("na1").trim();
        String nz1 = request.getParameter("nz1").trim();
        String dn1 = request.getParameter("dn1").trim();
        String importo = request.getParameter("importo").trim();
//        String tipocliente = request.getParameter("customerKind").trim();

        Db_Master db = new Db_Master(true);
        if (db.getC() == null) {
            db = new Db_Master();
        }
        List<String> cl = db.cod_Client_STUK(co1, na1, nz1, dn1);
        db.closeDB();

        cl = cl.stream().distinct().collect(toList());

        try (PrintWriter out = response.getWriter()) {
            if (cl.size() > 0) {
                String nuovasogliatrimestrale = getConf("thr.quart");
                if (nuovasogliatrimestrale.equals("-")) {
                    nuovasogliatrimestrale = "2500.00";
                }
                double soglia_new = fd(nuovasogliatrimestrale);
                Db_Master db4A = new Db_Master();
                double weekA = db4A.quarterly_transaction(cl);
                db4A.closeDB();
                Db_Master db4 = new Db_Master(true);
                if (db4.getC() == null) {
                    db4 = new Db_Master();
                }
                double week = db4.quarterly_transaction_nofiliale(cl, filiale);
                db4.closeDB();
                double now = fd(formatDoubleforMysql(importo)) + week + weekA;
                if (now >= soglia_new) {
                    out.print("KO");
                } else {
                    //out.print("OK");
                    out.print("KO1");
                }
            } else {
                out.print("OK");
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
    protected void verificaclient(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String q = request.getParameter("q").trim();
        String ti = request.getParameter("ti").trim();
        String[] buysell = {"-", "-"};
        Db_Master dblocal = new Db_Master();
        Client cl = dblocal.query_Client_cf(q);

        if (cl == null) {
            Db_Master db = new Db_Master(true);
            if (db.getC() == null) {
                db = new Db_Master();
            }
            cl = db.query_Client_cf(q);
            if (cl != null) {
                buysell = db.getClientCommission(cl.getCode());
            }
            db.closeDB();
        } else {
            buysell = dblocal.getClientCommission(cl.getCode());
        }
        dblocal.closeDB();

        try (PrintWriter out = response.getWriter()) {
            if (cl != null) {
                Gson gson = new Gson();
                ArrayList<String> JSONRequest = new ArrayList<>();
                JSONRequest.add(gson.toJson(cl.getCognome()));
                JSONRequest.add(gson.toJson(cl.getNome()));
                JSONRequest.add(gson.toJson(cl.getNazione()));
                JSONRequest.add(gson.toJson(cl.getCitta()));
                JSONRequest.add(gson.toJson(cl.getIndirizzo()));
                JSONRequest.add(gson.toJson(cl.getCap()));
                JSONRequest.add(gson.toJson(cl.getProvincia()));
                JSONRequest.add(gson.toJson(cl.getTipo_documento()));
                JSONRequest.add(gson.toJson(cl.getNumero_documento()));
                JSONRequest.add(gson.toJson(cl.getDt_rilascio_documento()));
                JSONRequest.add(gson.toJson(cl.getDt_scadenza_documento()));
                JSONRequest.add(gson.toJson(cl.getRilasciato_da_documento()));
                JSONRequest.add(gson.toJson(cl.getLuogo_rilascio_documento()));
                JSONRequest.add(gson.toJson(cl.getEmail()));
                JSONRequest.add(gson.toJson(cl.getTelefono()));
                if (ti.equals("B")) {
                    JSONRequest.add(gson.toJson(buysell[0]));
                } else {
                    JSONRequest.add(gson.toJson(buysell[1]));
                }
                JSONRequest.add(gson.toJson(cl.getCode()));
                //
                JSONRequest.add(gson.toJson(cl.getCodfisc()));
                //
                String[] citta = getCity_apm(cl.getCitta());
                if (citta != null) {
                    JSONRequest.add(gson.toJson(citta[1]));
                } else {
                    JSONRequest.add(gson.toJson("-"));
                }
                String[] prov = get_district(cl.getProvincia());
                if (prov != null) {
                    JSONRequest.add(gson.toJson(prov[1]));
                } else {
                    JSONRequest.add(gson.toJson("-"));
                }
                JSONRequest.add(gson.toJson(cl.getPep()));
                Db_Loy dbl = new Db_Loy();
                if (dbl.getC() != null) {
                    String lo = dbl.getCodiceClienteAttivo(cl.getCode());
                    dbl.closeDB();
                    if (lo != null) {
                        JSONRequest.add(gson.toJson(lo));
                    } else {
                        JSONRequest.add(gson.toJson(""));
                    }
                }
                if (JSONRequest.size() > 0) {
                    out.print(Utility.sanitize(JSONRequest.toString()));
                } else {
                    out.print("");
                }
            } else {
                out.print("");
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
    protected void list_OPERATION_client(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Client cl_FI = null;

        String loy = Utility.sanitize(request.getParameter("loya"));
        Db_Loy dbl = new Db_Loy();
        String completo[] = dbl.getCodiceCompleto(Utility.sanitize(loy));
        if (completo == null) {
        } else {
            if (completo[1].equals("0")) {

            } else {
                String codcl = Utility.sanitize(dbl.getCodiceCliente(completo[0]));
                if (codcl == null) {

                } else {
                    Db_Master db = new Db_Master(true);
                    cl_FI = db.query_Client_transaction(null, codcl);
                    db.closeDB();
                }
            }
        }

        ArrayList<Ch_transaction_value> li_value = new ArrayList<>();

        //StringBuilder surname = new StringBuilder("");
        //StringBuilder name = new StringBuilder("");

        if (cl_FI == null) {

            String cf = request.getParameter("cf").trim();
            String co1 = request.getParameter("co1").trim();
            String na1 = request.getParameter("na1").trim();
            String nz1 = request.getParameter("nz1").trim();
            String dn1 = request.getParameter("dn1").trim();

            Db_Master dbcentral = new Db_Master(true);
            if (dbcentral.getC() == null) {
                dbcentral = new Db_Master();
            }

            Client cl_IT = null;

            if (cf == null || cf.trim().equals("")) {
            } else {
                cl_IT = dbcentral.query_Client_cf(cf);
            }

            Client cl_ST = dbcentral.query_Client_STRANIERO(co1, na1, nz1, dn1);

            if (cl_IT != null) {
                li_value = dbcentral.query_transaction_value_1558(cl_IT.getCode());
                //surname = surname.append(cl_IT.getCognome().toUpperCase().toString());
                //name = name.append(cl_IT.getNome().toUpperCase().toString());
                cl_FI = cl_IT;
            } else if (cl_ST != null) {
                li_value = dbcentral.query_transaction_value_1558(cl_ST.getCode());
                //surname = surname.append(cl_ST.getCognome().toUpperCase().toString());
                //name = name.append(cl_ST.getNome().toUpperCase().toString());
                cl_FI = cl_ST;
            }
            dbcentral.closeDB();

        } else {
            Db_Master dbcentral = new Db_Master(true);
            if (dbcentral.getC() == null) {
                dbcentral = new Db_Master();
            }

            li_value = dbcentral.query_transaction_value_1558(cl_FI.getCode());
            //surname.append(cl_FI.getCognome().toUpperCase().toString());
            //name.append(cl_FI.getNome().toUpperCase().toString());
            dbcentral.closeDB();
        }

        StringBuilder tab1 = new StringBuilder();
        if (cl_FI != null) {

            tab1.append("<h4 class='modal-title font-red uppercase'>Customer: <b>" + cl_FI.getCognome() + " " + cl_FI.getNome() + "</b></h4>"
                    + "<table class='table table-bordered table-responsive'>"
                    + "<tr>"
                    + "<th>BRANCH ID</th>"
                    + "<th>DATE</th>"
                    + "<th>OPERATOR</th>"
                    + "<th>TYPE</th>"
                    + "<th>CURRENCY</th>"
                    + "<th>QUANTITY</th>"
                    + "<th>RATE</th>"
                    + "<th>% COM.</th>"
                    + "<th>FX COM.</th>"
                    + "<th>NOTE</th>"
                    + "</tr>");

            li_value.forEach(v1 -> {
                tab1.append("<tr>"
                        + "<td>" + v1.getBranch() + "</td>"
                        + "<td>" + formatStringtoStringDate(v1.getDt_tr(), patternsqldate, patternnormdate) + "</td>"
                        + "<td>" + v1.getOperator() + "</td>"
                        + "<td>" + formatType(v1.getType()).toUpperCase() + "</td>"
                        + "<td>" + formatMysqltoDisplay(Utility.sanitize(v1.getValuta())) + "</td>"
                        + "<td>" + formatMysqltoDisplay(Utility.sanitize(v1.getQuantita())) + "</td>"
                        + "<td>" + formatMysqltoDisplay(Utility.sanitize(v1.getRate())) + "</td>"
                        + "<td>" + formatMysqltoDisplay(Utility.sanitize(v1.getCom_perc())) + "</td>"
                        + "<td>" + formatMysqltoDisplay(Utility.sanitize(v1.getFx_com())) + "</td>"
                        + "<td>" + v1.getNote() + "</td>"
                        + "</tr>");
            });

            tab1.append("</table>");

            tab1.append("<hr>");
            tab1.append("<form method='post' action='Fileview?type=listpdfclient&cod=" + cl_FI.getCode() + "' target='_blank'>"
                    + "<button type='submit' class='btn blue btn-outline'><i class='fa fa-file-pdf-o'></i> Export PDF</button></form>");

        }

        ArrayList<String> JSONRequest = new ArrayList<>();

        Gson gson = new Gson();

        if (li_value.isEmpty()) {
            JSONRequest.add(gson.toJson("false"));
        } else {
            JSONRequest.add("true");
            JSONRequest.add(gson.toJson(tab1.toString()));
        }

        try (PrintWriter out = response.getWriter()) {
            out.print(Utility.sanitize(JSONRequest.toString()));
        }

    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void verificaonline(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        boolean online = pingIPONLINE(ipcentral);
        try (PrintWriter out = response.getWriter()) {
            out.print(online);
        }
    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void doc_list_select(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (is_IT) {
            String customerKind = request.getParameter("customerKind");
            double payout1 = fd(formatDoubleforMysql(request.getParameter("payout1")));
            String heavy_country = request.getParameter("heavy_country");

            Db_Master db = new Db_Master();
            ArrayList<String[]> result = listaDocumentiAccettati(get_customerKind(customerKind), payout1, heavy_country, db.identificationCard());
            db.closeDB();

            try (PrintWriter out = response.getWriter()) {
                String inizio = "{ \"items\": [ ";
                String fine = "]}";
                String valore = "";
                if (result.size() > 0) {
                    for (int i = 0; i < result.size(); i++) {
                        valore = valore + "{"
                                + "      \"id\": \"" + result.get(i)[0] + "\","
                                + "      \"name\": \"" + result.get(i)[1] + "\","
                                + "      \"full_name\": \"" + result.get(i)[1] + "\""
                                + "},";

                    }
                    String x = inizio + valore.substring(0, valore.length() - 1) + fine;
                    out.print(Utility.sanitize(x));
                } else {
                    out.print(Utility.sanitize(inizio) + Utility.sanitize(fine));
                }
            }
        } else {
            Db_Master db = new Db_Master();
            ArrayList<String[]> result = db.identificationCard();
            db.closeDB();
            try (PrintWriter out = response.getWriter()) {
                String inizio = "{ \"items\": [ ";
                String fine = "]}";
                String valore = "";
                if (result.size() > 0) {
                    for (int i = 0; i < result.size(); i++) {
                        valore = valore + "{"
                                + "      \"id\": \"" + result.get(i)[0] + "\","
                                + "      \"name\": \"" + result.get(i)[1] + "\","
                                + "      \"full_name\": \"" + result.get(i)[1] + "\""
                                + "},";

                    }
                    String x = inizio + valore.substring(0, valore.length() - 1) + fine;
                    out.print(x);
                } else {
                    out.print(inizio + fine);
                }
            }
        }

    }

    protected void getbcevalue(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String valuecurrency = getRequestValue(request, "q");

        Db_Master db = new Db_Master();
        Currency c = db.getCurrency(valuecurrency);
        db.closeDB();

        if (c != null) {
            try (PrintWriter out = response.getWriter()) {
                out.print((formatMysqltoDisplay(roundDoubleandFormat(fd(c.getCambio_bce()), 8))));
            }
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
            }
            response.setContentType("text/html;charset=UTF-8");
            String type = request.getParameter("type");
            switch (type) {
                case "nc_cat":
                    query_nc_cat(request, response);
                    break;
                case "nc_caus":
                    query_nc_caus(request, response);
                    break;
                case "tb_city":
                    query_tb_city(request, response);
                    break;
                case "verificaonline":
                    verificaonline(request, response);
                    break;
                case "tb_currency":
                    query_tb_currency(request, response);
                    break;
                case "tb_spread":
                    query_tb_spread(request, response);
                    break;
                case "tb_agency":
                    query_tb_agency(request, response);
                    break;
                case "tb_company":
                    query_tb_only_company(request, response);
                    break;
                case "transaction_list":
                    query_transaction_list(request, response);
                    break;
                case "transaction_list_nc":
                    query_transaction_list_nc(request, response);
                    break;
                case "users_list_kyc":
                    query_users_list_kyc(request, response);
                    break;
                case "users_list":
                    query_users_list(request, response);
                    break;
                case "newsletter":
                    query_newsletters(request, response);
                    break;
                case "it_list":
                    query_it_list(request, response);
                    break;
                case "et_list":
                    query_et_list(request, response);
                    break;
                case "oc_list":
                    query_oc_list(request, response);
                    break;
                case "prenot_list":
                    prenot_list(request, response);
                    break;
                case "doc_list_select":
                    doc_list_select(request, response);
                    break;
                case "city_select":
                    city_select(request, response);
                    break;
                case "district_select":
                    district_select(request, response);
                    break;
                case "querycodcom_distr":
                    querycodcom_distr(request, response);
                    break;
                case "querycodcom_city":
                    querycodcom_city(request, response);
                    break;
                case "querycodcom_zip":
                    querycodcom_zip(request, response);
                    break;
                case "querycountry":
                    querycountry(request, response);
                    break;
                case "queryfidelitycode":
                    queryfidelitycode(request, response);
                    break;
                case "queryfidelitycodesb":
                    queryfidelitycodesb(request, response);
                    break;
                case "kyc_list":
                    kyc_list(request, response);
                    break;
                case "check_quantity_WK":
                    check_quantity_WK(request, response);
                    break;
                case "query_quantity_WK":
                    query_quantity_WK(request, response);
                    break;
                case "queryquantitynochange":
                    queryquantitynochange(request, response);
                    break;
                case "checkbranch":
                    checkbranch(request, response);
                    break;
                case "checkunlockcode":
                    checkunlockcode(request, response);
                    break;
                case "verificanewuk":
                    verificanewuk(request, response);
                    break;
                case "verificaclient":
                    verificaclient(request, response);
                    break;
                case "sogliasetclient":
                    sogliasetclient(request, response);
                    break;
                case "checksizecurrency":
                    checksizecurrency(request, response);
                    break;
                case "checkquantitytill_BUY":
                    checkquantitytill_BUY(request, response);
                    break;
                case "checkquantitytill_SELL":
                    checkquantitytill_SELL(request, response);
                    break;
                case "query_newsletters_man":
                    query_newsletters_man(request, response);
                    break;
                case "queryloy_ass":
                    queryloy_ass(request, response);
                    break;
                case "queryloy":
                    queryloy(request, response);
                    break;
                case "verificaclientstraniero":
                    verificaclientstraniero(request, response);
                    break;
                case "list_OPERATION_client":
                    list_OPERATION_client(request, response);
                    break;
                case "searchloy":
                    searchloy(request, response);
                    break;
                case "getbcevalue":
                    getbcevalue(request, response);
                    break;
                default:
                    break;
            }

        } catch (ServletException | IOException ex) {
            insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
        }
    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     *
     * @return
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
