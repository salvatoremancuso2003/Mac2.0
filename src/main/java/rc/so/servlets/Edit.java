package rc.so.servlets;

import rc.so.db.Db_Master;
import rc.so.entity.Agency;
import rc.so.entity.Aggiornamenti_mod;
import rc.so.entity.BlacklistM;
import rc.so.entity.Branch;
import rc.so.entity.Branchbudget;
import rc.so.entity.Ch_transaction;
import rc.so.entity.Ch_transaction_value;
import rc.so.entity.Client;
import rc.so.entity.Codici_sblocco_file;
import rc.so.entity.Company;
import rc.so.entity.Company_attach;
import rc.so.entity.Currency;
import rc.so.entity.CustomerKind;
import rc.so.entity.Figures;
import rc.so.entity.NC_category;
import rc.so.entity.NC_causal;
import rc.so.entity.NC_transaction;
import rc.so.entity.NC_vatcode;
import rc.so.entity.Office;
import rc.so.entity.Rate_history;
import rc.so.entity.Real_oc_change;
import rc.so.entity.Real_oc_pos;
import rc.so.entity.Sizecuts;
import rc.so.entity.Stock;
import rc.so.entity.Stock_report;
import rc.so.entity.Till;
import rc.so.entity.Users;
import rc.so.entity.VATcode;
import rc.so.pdf.Receipt;
import static rc.so.util.Constant.codnaz;
import static rc.so.util.Constant.is_CZ;
import static rc.so.util.Constant.is_IT;
import static rc.so.util.Constant.pattermonthnorm;
import static rc.so.util.Constant.patterndir;
import static rc.so.util.Constant.patternmonthsql;
import static rc.so.util.Constant.patternnormdate;
import static rc.so.util.Constant.patternnormdate_f;
import static rc.so.util.Constant.patternnormdate_filter;
import static rc.so.util.Constant.patternsql;
import static rc.so.util.Constant.patternsqldate;
import static rc.so.util.Engine.getLink_last;
import static rc.so.util.Engine.get_Agent_company;
import static rc.so.util.Engine.get_NC_transaction;
import static rc.so.util.Engine.insertTR;
import static rc.so.util.Engine.kind_payment;
import static rc.so.util.Engine.list_all_kind;
import static rc.so.util.Engine.query_Client_transaction;
import static rc.so.util.Engine.query_transaction_ch;
import static rc.so.util.Engine.query_transaction_value;
import static rc.so.util.Engine.verifySession;
import rc.so.util.Excel;
import rc.so.util.Utility;

import static rc.so.util.HtmlEncoder.base64HTML;
import static rc.so.util.HtmlEncoder.htmlMailPrenot;
import static rc.so.util.Utility.fd;
import static rc.so.util.Utility.formatDoubleforMysql;
import static rc.so.util.Utility.formatMysqltoDisplay;
import static rc.so.util.Utility.formatStringtoStringDate;
import static rc.so.util.Utility.formatStringtoStringDate_null;
import static rc.so.util.Utility.formatValue;
import static rc.so.util.Utility.generaId;
import static rc.so.util.Utility.generateUsername;
import static rc.so.util.Utility.getMd5;
import static rc.so.util.Utility.getRequestValue;
import static rc.so.util.Utility.getStringBase64;
import static rc.so.util.Utility.getStringUTF8;
import static rc.so.util.Utility.parseArrayValues;
import static rc.so.util.Utility.parseIntR;
import static rc.so.util.Utility.redirect;
import static rc.so.util.Utility.roundDoubleandFormat;
import static rc.so.util.Utility.sendMailHtmlNOBCC;
import static rc.so.util.Utility.sleeping;
import java.io.File;
import static java.io.File.separator;
import java.io.IOException;
import static java.lang.Integer.parseInt;
import static java.lang.String.join;
import static java.lang.String.valueOf;
import static java.lang.Thread.currentThread;
import static java.lang.Thread.sleep;
import static java.sql.ResultSet.CONCUR_UPDATABLE;
import static java.sql.ResultSet.TYPE_SCROLL_INSENSITIVE;
import java.sql.SQLException;
import java.util.ArrayList;
import static java.util.Arrays.asList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import static org.apache.commons.fileupload.servlet.ServletFileUpload.isMultipartContent;
import static org.apache.commons.io.FilenameUtils.getExtension;
import static org.apache.commons.lang3.StringUtils.remove;
import static org.apache.commons.lang3.StringUtils.replace;
import org.joda.time.DateTime;

/**
 *
 * @author rcosco
 */
public class Edit
        extends HttpServlet {

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void insert_new_Till(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String user = (String) request.getSession().getAttribute("us_cod");
        if (user == null) {
            user = "9999";
        }
        Db_Master dbm = new Db_Master();
        String filiale = dbm.getCodLocal(true)[0];
        String listbranch = request.getParameter("listbranch");
        String bra[];
        if (listbranch != null) {
            bra = parseArrayValues(listbranch, ";");
        } else {
            bra = parseArrayValues(dbm.list_branchcode_ENABLED());
        }
        String typetill = request.getParameter("typetill");
        if (typetill == null) {
            typetill = "1";
        } else if (typetill.trim().equalsIgnoreCase("on")) {
            typetill = "0";
        } else {
            typetill = "1";
        }
        String descr = request.getParameter("descr");

        String dtoper = new DateTime().toString(patternsqldate);

        boolean es = dbm.insert_new_Till(filiale, descr, typetill, user, dtoper);
        if (es) {
            for (String bra1 : bra) {
                if (!bra1.equals(filiale)) {
                    dbm.insert_new_Till(bra1, descr, typetill, user, dtoper);
                }
            }
        }
        dbm.closeDB();
        if (es) {
            redirect(request, response, "tb_edit_safetill.jsp?esito=ok");
        } else {
            redirect(request, response, "tb_edit_safetill.jsp?esito=koins");
        }
    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void edit_Till(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Db_Master dbm = new Db_Master();
        String listbranch = request.getParameter("listbranch");
        String view = request.getParameter("view");
        String fil = request.getParameter("fil");
        String filiale = dbm.getCodLocal(true)[0];
        String bra[];
        if (listbranch != null) {
            bra = parseArrayValues(listbranch, ";");
        } else {
            bra = parseArrayValues(dbm.list_branchcode_ENABLED());
        }

        String typetill = request.getParameter("typetill");
        if (typetill == null) {
            typetill = "1";
        } else if (typetill.trim().equalsIgnoreCase("on")) {
            typetill = "0";
        } else {
            typetill = "1";
        }
        String status = request.getParameter("status");
        if (status == null) {
            status = "1";
        } else if (status.trim().equalsIgnoreCase("on")) {
            status = "0";
        } else {
            status = "1";
        }
        String user = (String) request.getSession().getAttribute("us_cod");
        if (user == null) {
            user = "9999";
        }
        String dtoper = new DateTime().toString(patternsqldate);
        String descr = request.getParameter("descr");
        String sa_code = request.getParameter("sa_code");

        boolean es = dbm.update_Till(sa_code, filiale, descr, typetill, status, user, dtoper);
        if (es) {
            for (String bra1 : bra) {
                if (!bra1.equals(filiale)) {
                    dbm.update_Till(sa_code, bra1, descr, typetill, status, user, dtoper);
                }
            }
        }
        dbm.closeDB();
        if (es) {
            redirect(request, response, "tb_edit_safetill.jsp?view=" + view + "&fil=" + fil + "&sa_code=" + sa_code + "&esito=ok");
        } else {
            redirect(request, response, "tb_edit_safetill.jsp?view=" + view + "&fil=" + fil + "&sa_code=" + sa_code + "&esito=koins");
        }
    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void insert_new_District(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String user = (String) request.getSession().getAttribute("us_cod");
        if (user == null) {
            user = "9999";
        }
        String di_code = request.getParameter("di_code");
        String descr = request.getParameter("descr");
        Db_Master dbm = new Db_Master();
        String filiale = dbm.getCodLocal(true)[0];
        boolean es = dbm.insert_new_District(filiale, di_code, descr, user);
        dbm.closeDB();
        if (es) {
            redirect(request, response, "tb_edit_district.jsp?esito=ok");
        } else {
            redirect(request, response, "tb_edit_district.jsp?esito=koins");
        }
    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void edit_District(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String user = (String) request.getSession().getAttribute("us_cod");
        if (user == null) {
            user = "9999";
        }
        String idpr = request.getParameter("idpr");
        String di_code = request.getParameter("di_code");
        String descr = request.getParameter("descr");
        Db_Master dbm = new Db_Master();
        String filiale = dbm.getCodLocal(true)[0];
        boolean es = dbm.update_District(idpr, filiale, di_code, descr, user);
        dbm.closeDB();
        if (es) {
            redirect(request, response, "tb_edit_district.jsp?di_code=" + di_code + "&esito=ok");
        } else {
            redirect(request, response, "tb_edit_district.jsp?di_code=" + di_code + "&esito=koins");
        }
    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void insert_new_Nation(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String user = (String) request.getSession().getAttribute("us_cod");
        if (user == null) {
            user = "9999";
        }
        String na_code = request.getParameter("na_code");
        String descr = request.getParameter("descr");
        String area = request.getParameter("area");
        String isoc = request.getParameter("isoc");
        Db_Master dbm = new Db_Master();
        String filiale = dbm.getCodLocal(true)[0];
        boolean es = dbm.insert_new_Nation(filiale, na_code, descr, area, isoc, user);
        dbm.closeDB();
        if (es) {
            redirect(request, response, "tb_edit_nations.jsp?esito=ok");
        } else {
            redirect(request, response, "tb_edit_nations.jsp?esito=koins");
        }
    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void edit_Nation(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String user = (String) request.getSession().getAttribute("us_cod");
        if (user == null) {
            user = "9999";
        }
        String co_code = request.getParameter("na_code");
        String descr = request.getParameter("descr");
        String area = request.getParameter("area");
        String isoc = request.getParameter("isoc");
        Db_Master dbm = new Db_Master();
        String filiale = dbm.getCodLocal(true)[0];
        boolean es = dbm.update_Nation(filiale, co_code, descr, area, isoc, user);
        dbm.closeDB();
        if (es) {
            redirect(request, response, "tb_edit_nations.jsp?co_code=" + co_code + "&esito=ok");
        } else {
            redirect(request, response, "tb_edit_nations.jsp?co_code=" + co_code + "&esito=koins");
        }
    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void insert_new_Doctype(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String user = (String) request.getSession().getAttribute("us_cod");
        if (user == null) {
            user = "9999";
        }
        String doc_code = request.getParameter("doc_code");
        String descr = request.getParameter("descr");
        String oam = request.getParameter("oam");
        Db_Master dbm = new Db_Master();
        String filiale = dbm.getCodLocal(true)[0];
        boolean es = dbm.insert_new_Doctype(filiale, doc_code, descr, oam, user);
        dbm.closeDB();
        if (es) {
            redirect(request, response, "tb_edit_doctype.jsp?esito=ok");
        } else {
            redirect(request, response, "tb_edit_doctype.jsp?esito=koins");
        }
    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void edit_Doctype(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String user = (String) request.getSession().getAttribute("us_cod");
        if (user == null) {
            user = "9999";
        }
        String doc_code = request.getParameter("doc_code");
        String descr = request.getParameter("descr");
        String oam = request.getParameter("oam");
        Db_Master dbm = new Db_Master();
        String filiale = dbm.getCodLocal(true)[0];
        boolean es = dbm.update_Doctype(filiale, doc_code, descr, oam, user);
        dbm.closeDB();
        if (es) {
            redirect(request, response, "tb_edit_doctype.jsp?doc_code=" + doc_code + "&esito=ok");
        } else {
            redirect(request, response, "tb_edit_doctype.jsp?doc_code=" + doc_code + "&esito=koins");
        }
    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void ins_newunratecom(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String user = (String) request.getSession().getAttribute("us_cod");
        if (user == null) {
            user = "9999";
        }
        String descr = request.getParameter("descr");
        Db_Master dbm = new Db_Master();
        String filiale = dbm.getCodLocal(true)[0];
        boolean es = dbm.insert_new_unlockratejust(filiale, descr, user);
        dbm.closeDB();
        if (es) {
            redirect(request, response, "tb_edit_lowcommju.jsp?esito=ok");
        } else {
            redirect(request, response, "tb_edit_lowcommju.jsp?esito=koins");
        }
    }

    protected void insert_new_Lowcom(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String user = (String) request.getSession().getAttribute("us_cod");
        if (user == null) {
            user = "9999";
        }
        String descr = request.getParameter("descr");
        Db_Master dbm = new Db_Master();
        String filiale = dbm.getCodLocal(true)[0];
        boolean es = dbm.insert_new_Lowcom(filiale, descr, user);
        dbm.closeDB();
        if (es) {
            redirect(request, response, "tb_edit_unlockrateju.jsp?esito=ok");
        } else {
            redirect(request, response, "tb_edit_unlockrateju.jsp?esito=koins");
        }
    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void edit_Lowcom(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String user = (String) request.getSession().getAttribute("us_cod");
        if (user == null) {
            user = "9999";
        }
        String lo_code = request.getParameter("lo_code");
        String descr = request.getParameter("descr");
        String status = request.getParameter("status");
        if (status == null) {
            status = "1";
        } else if (status.trim().equalsIgnoreCase("on")) {
            status = "0";
        } else {
            status = "1";
        }

        Db_Master dbm = new Db_Master();
        String filiale = dbm.getCodLocal(true)[0];
        boolean es = dbm.update_Lowcom(filiale, lo_code, descr, status, user);
        dbm.closeDB();
        if (es) {
            redirect(request, response, "tb_edit_lowcommju.jsp?lo_code=" + lo_code + "&esito=ok");
        } else {
            redirect(request, response, "tb_edit_lowcommju.jsp?lo_code=" + lo_code + "&esito=koins");
        }
    }

    protected void edit_unrate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String user = (String) request.getSession().getAttribute("us_cod");
        if (user == null) {
            user = "9999";
        }
        String un_code = request.getParameter("un_code");
        String descr = request.getParameter("descr");
        String status = request.getParameter("status");
        if (status == null) {
            status = "1";
        } else if (status.trim().equalsIgnoreCase("on")) {
            status = "0";
        } else {
            status = "1";
        }

        Db_Master dbm = new Db_Master();
        String filiale = dbm.getCodLocal(true)[0];
        boolean es = dbm.update_unrate(filiale, un_code, descr, status, user);
        dbm.closeDB();
        if (es) {
            redirect(request, response, "tb_edit_unlockrateju.jsp?un_code=" + un_code + "&esito=ok");
        } else {
            redirect(request, response, "tb_edit_unlockrateju.jsp?un_code=" + un_code + "&esito=koins");
        }
    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void insert_new_Fixcomkind(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Db_Master dbm = new Db_Master();

        String filiale = dbm.getCodLocal(true)[0];
        String listbranch = request.getParameter("listbranch");
        String bra[];
        if (listbranch != null) {
            bra = parseArrayValues(listbranch, ";");
        } else {
            bra = parseArrayValues(dbm.list_branchcode_ENABLED());
        }

        String dt = new DateTime().toString(patternsqldate);

        String dt_val = request.getParameter("dt_val");
        if (dt_val == null) {
            dt_val = "";
        }

        String user = (String) request.getSession().getAttribute("us_cod");
        if (user == null) {
            user = "9999";
        }
        String descr = request.getParameter("descr");
        String value = request.getParameter("val");

        String es = dbm.insert_new_Fixcomkind(filiale, descr, formatDoubleforMysql(value), user, dt_val, dt);

        if (es.equals("0")) {
            for (String bra1 : bra) {
                if (!bra1.equals("000")) {
                    dbm.insert_new_Fixcomkind(bra1, descr, formatDoubleforMysql(value), user, dt_val, dt);
                }
            }
        }

        dbm.closeDB();

        switch (es) {
            case "0":
                redirect(request, response, "tb_edit_kindfixcomm.jsp?esito=ok");
                break;
            case "1":
                redirect(request, response, "tb_edit_kindfixcomm.jsp?esito=kodup");
                break;
            default:
                redirect(request, response, "tb_edit_kindfixcomm.jsp?esito=koins");
                break;
        }

    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void edit_Fixcomkind(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

//                Utility.printRequest(request);
//        if(true)
//            return;
        String user = (String) request.getSession().getAttribute("us_cod");
        if (user == null) {
            user = "9999";
        }
        String ki_code = request.getParameter("ki_code");
        String descr = request.getParameter("descr");
        String value = request.getParameter("val");
        String status = request.getParameter("status");
        if (status == null) {
            status = "1";
        } else if (status.trim().equalsIgnoreCase("on")) {
            status = "0";
        } else {
            status = "1";
        }
        Db_Master dbm = new Db_Master();

        String filiale = dbm.getCodLocal(true)[0];
        String listbranch = request.getParameter("listbranch");
        String bra[];
        if (listbranch != null) {
            bra = parseArrayValues(listbranch, ";");
        } else {
            bra = parseArrayValues(dbm.list_branchcode_ENABLED());
        }

        String dt = new DateTime().toString(patternsqldate);

        String dt_val = request.getParameter("dt_val");
        if (dt_val == null) {
            dt_val = "";
        }

        boolean es = dbm.update_Fixcomkind(filiale, ki_code, descr, formatDoubleforMysql(value), status,
                user, dt_val, dt);

        if (es) {
            for (String bra1 : bra) {
                dbm.update_Fixcomkind(bra1, ki_code, descr, formatDoubleforMysql(value), status, user, dt_val, dt);
            }
        }

        dbm.closeDB();
        if (es) {
            redirect(request, response, "tb_edit_kindfixcomm.jsp?ki_code=" + ki_code + "&esito=ok");
        } else {
            redirect(request, response, "tb_edit_kindfixcomm.jsp?ki_code=" + ki_code + "&esito=koins");
        }
    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void insert_new_Comfix(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String user = (String) request.getSession().getAttribute("us_cod");
        if (user == null) {
            user = "9999";
        }
        String kind = request.getParameter("kind0");
        String min = request.getParameter("min");
        String max = request.getParameter("max");
        String buy = request.getParameter("buy");
        String sell = request.getParameter("sell");
        String listbranch = request.getParameter("listbranch");
        Db_Master dbm = new Db_Master();
        String filiale = dbm.getCodLocal(true)[0];
        String bra[];
        if (listbranch != null) {
            bra = parseArrayValues(listbranch, ";");
        } else {
            bra = parseArrayValues(dbm.list_branchcode_ENABLED());
        }

        String dt = new DateTime().toString(patternsqldate);

        String dt_val = request.getParameter("dt_val");
        if (dt_val == null) {
            dt_val = "";
        }

        String es = dbm.insert_new_Fixcom(filiale, kind, formatDoubleforMysql(min), formatDoubleforMysql(max),
                formatDoubleforMysql(buy), formatDoubleforMysql(sell), user, dt_val, dt);

        if (es.equals("0")) {
            for (String bra1 : bra) {
                if (!bra1.equals("000")) {
                    dbm.insert_new_Fixcom(bra1, kind, formatDoubleforMysql(min), formatDoubleforMysql(max), formatDoubleforMysql(buy), formatDoubleforMysql(sell), user, dt_val, dt);
                }
            }
        }

        dbm.closeDB();

        switch (es) {
            case "0":
                redirect(request, response, "tb_edit_fixcomm.jsp?esito=ok");
                break;
            case "1":
                redirect(request, response, "tb_edit_fixcomm.jsp?esito=kodup");
                break;
            default:
                redirect(request, response, "tb_edit_fixcomm.jsp?esito=koins");
                break;
        }

    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void edit_wb_comfix(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String user = (String) request.getSession().getAttribute("us_cod");
        if (user == null) {
            user = "9999";
        }
        String dt = new DateTime().toString(patternsqldate);

        String dt_val = request.getParameter("dt_val");
        if (dt_val == null) {
            dt_val = "";
        }

        String min = request.getParameter("min");
        String max = request.getParameter("max");
        String sell = request.getParameter("sell");
        String min_old = request.getParameter("min_old");
        String status = request.getParameter("status");
        if (status == null) {
            status = "1";
        } else if (status.trim().equalsIgnoreCase("on")) {
            status = "0";
        } else {
            status = "1";
        }

        Db_Master dbm = new Db_Master();
        boolean esito = dbm.update_site_commissione_fissa("01", formatDoubleforMysql(min), formatDoubleforMysql(max),
                formatDoubleforMysql(sell), status, min_old, user, dt_val, dt);
        dbm.closeDB();
        redirect(request, response, "tb_wb_edit_fixcomm.jsp?fi_min=" + formatDoubleforMysql(min) + "&esito=" + esito);

    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void edit_Comfix(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String user = (String) request.getSession().getAttribute("us_cod");
        if (user == null) {
            user = "9999";
        }
        Db_Master dbm = new Db_Master();

        String filiale = dbm.getCodLocal(true)[0];
        String listbranch = request.getParameter("listbranch");
        String bra[];
        if (listbranch != null) {
            bra = parseArrayValues(listbranch, ";");
        } else {
            bra = parseArrayValues(dbm.list_branchcode_ENABLED());
        }

        String dt = new DateTime().toString(patternsqldate);

        String dt_val = request.getParameter("dt_val");
        if (dt_val == null) {
            dt_val = "";
        }

        String fi_code = request.getParameter("fi_code");
        String min = request.getParameter("min");
        String max = request.getParameter("max");
        String buy = request.getParameter("buy");
        String sell = request.getParameter("sell");
        String min_old = request.getParameter("min_old");
        String status = request.getParameter("status");
        if (status == null) {
            status = "1";
        } else if (status.trim().equalsIgnoreCase("on")) {
            status = "0";
        } else {
            status = "1";
        }

        boolean es = dbm.edit_Fixcom(filiale, fi_code, formatDoubleforMysql(min), formatDoubleforMysql(max),
                formatDoubleforMysql(buy), formatDoubleforMysql(sell), status, min_old, user, dt_val, dt);
        if (es) {
            for (String bra1 : bra) {
                dbm.edit_Fixcom(bra1, fi_code, formatDoubleforMysql(min), formatDoubleforMysql(max), formatDoubleforMysql(buy), formatDoubleforMysql(sell), status, min_old, user, dt_val, dt);
            }
        }

        dbm.closeDB();
        if (es) {
            redirect(request, response, "tb_edit_fixcomm.jsp?fi_code=" + fi_code + "&fi_min=" + formatDoubleforMysql(min) + "&fi_max=" + formatDoubleforMysql(max) + "&esito=ok");
        } else {
            redirect(request, response, "tb_edit_fixcomm.jsp?fi_code=" + fi_code + "&fi_min=" + formatDoubleforMysql(min) + "&fi_max=" + formatDoubleforMysql(max) + "&esito=koins");
        }
    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void insert_new_Kindtra(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String user = (String) request.getSession().getAttribute("us_cod");
        if (user == null) {
            user = "9999";
        }

        String dt_val = request.getParameter("dt_val");
        if (dt_val == null) {
            dt_val = "";
        }

        String descr = request.getParameter("descr");
        String thr_kyc = request.getParameter("thr_kyc");
        String maxweek = request.getParameter("maxweek");
        String thr_aml = request.getParameter("thr_aml");
        String thr_cee = request.getParameter("thr_cee");
        String list_type_kind = request.getParameter("list_type_kind");
        String list_type_customer = request.getParameter("list_type_customer");
        String list_category_nations = request.getParameter("list_category_nations");
        String ecee = request.getParameter("ecee");
        if (ecee == null) {
            ecee = "0";
        } else if (ecee.trim().equalsIgnoreCase("on")) {
            ecee = "1";
        } else {
            ecee = "0";
        }
        String uploadobbl = request.getParameter("uploadobbl");
        if (uploadobbl == null) {
            uploadobbl = "0";
        } else if (uploadobbl.trim().equalsIgnoreCase("on")) {
            uploadobbl = "1";
        } else {
            uploadobbl = "0";
        }
        String resid = request.getParameter("resid");
        if (resid == null) {
            resid = "1";
        } else if (resid.trim().equalsIgnoreCase("on")) {
            resid = "0";
        } else {
            resid = "1";
        }

        String list_type_selecttipov = request.getParameter("list_type_selecttipov");

        String vatcode = request.getParameter("vatcode");
        String thr_tax_disc = request.getParameter("thr_tax_disc");
        if (thr_tax_disc == null) {
            thr_tax_disc = "0.00";
        }
        String value_tax_disc = request.getParameter("value_tax_disc");
        if (value_tax_disc == null) {
            value_tax_disc = "0.00";
        }
        String des_tax_disc = request.getParameter("des_tax_disc");
        if (des_tax_disc == null) {
            des_tax_disc = "-";
        }

        String taxfree = request.getParameter("taxfree");
        if (taxfree == null) {
            taxfree = "0";
        } else if (taxfree.trim().equalsIgnoreCase("on")) {
            taxfree = "1";
        } else {
            taxfree = "0";
        }

        Db_Master dbm = new Db_Master();
        CustomerKind ck = new CustomerKind();
        ck.setTaxfree(taxfree);

        ck.setDe_tipologia_clienti(descr);
        ck.setFg_tipo_cliente(list_type_customer);
        ck.setFg_nazionalita(list_type_kind);
        ck.setIp_max_singola_transazione(formatDoubleforMysql(thr_kyc));
        ck.setIp_max_settimanale(formatDoubleforMysql(maxweek));
        ck.setFg_area_geografica(list_category_nations);
        ck.setStampa_autocertificazione(ecee);
        ck.setIp_soglia_antiriciclaggio(formatDoubleforMysql(thr_aml));
        ck.setIp_soglia_extraCEE_certification(formatDoubleforMysql(thr_cee));
        ck.setFg_annullato("0");
        ck.setFiliale(dbm.getCodLocal(true)[0]);
        ck.setFg_uploadobbl(uploadobbl);
        ck.setResident(resid);
        ck.setTipofat(formatValue(list_type_selecttipov, "0"));
        ck.setVatcode(formatValue(vatcode, "-"));
        ck.setIp_soglia_bollo(formatDoubleforMysql(thr_tax_disc));
        ck.setIp_value_bollo(formatDoubleforMysql(value_tax_disc));
        ck.setDescr_bollo(des_tax_disc);

        boolean es = dbm.insert_new_Kindtransaction(ck, user, dt_val);
        dbm.closeDB();
        if (es) {
            redirect(request, response, "tb_edit_kindtrans.jsp?esito=ok");
        } else {
            redirect(request, response, "tb_edit_kindtrans.jsp?esito=koins");
        }

    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void edit_Kindtra(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String dt_val = request.getParameter("dt_val");
        if (dt_val == null) {
            dt_val = "";
        }
        String user = (String) request.getSession().getAttribute("us_cod");
        if (user == null) {
            user = "9999";
        }

        String kt_code = request.getParameter("kt_code");
        String descr = request.getParameter("descr");
        String thr_kyc = request.getParameter("thr_kyc");
        String maxweek = request.getParameter("maxweek");
        String thr_aml = request.getParameter("thr_aml");
        String thr_cee = request.getParameter("thr_cee");
        String list_type_kind = request.getParameter("list_type_kind");
        String list_type_customer = request.getParameter("list_type_customer");
        String list_category_nations = request.getParameter("list_category_nations");
        String ecee = request.getParameter("ecee");
        if (ecee == null) {
            ecee = "0";
        } else if (ecee.trim().equalsIgnoreCase("on")) {
            ecee = "1";
        } else {
            ecee = "0";
        }
        String status = request.getParameter("status");
        if (status == null) {
            status = "1";
        } else if (status.trim().equalsIgnoreCase("on")) {
            status = "0";
        } else {
            status = "1";
        }
        String resid = request.getParameter("resid");
        if (resid == null) {
            resid = "1";
        } else if (resid.trim().equalsIgnoreCase("on")) {
            resid = "0";
        } else {
            resid = "1";
        }

        String uploadobbl = request.getParameter("uploadobbl");
        if (uploadobbl == null) {
            uploadobbl = "0";
        } else if (uploadobbl.trim().equalsIgnoreCase("on")) {
            uploadobbl = "1";
        } else {
            uploadobbl = "0";
        }
        String taxfree = request.getParameter("taxfree");
        if (taxfree == null) {
            taxfree = "0";
        } else if (taxfree.trim().equalsIgnoreCase("on")) {
            taxfree = "1";
        } else {
            taxfree = "0";
        }

        String list_type_selecttipov = request.getParameter("list_type_selecttipov");

        String vatcode = request.getParameter("vatcode");
        String thr_tax_disc = request.getParameter("thr_tax_disc");
        if (thr_tax_disc == null) {
            thr_tax_disc = "0.00";
        }
        String value_tax_disc = request.getParameter("value_tax_disc");
        if (value_tax_disc == null) {
            value_tax_disc = "0.00";
        }
        String des_tax_disc = request.getParameter("des_tax_disc");
        if (des_tax_disc == null) {
            des_tax_disc = "-";
        }

        Db_Master dbm = new Db_Master();

        CustomerKind ck = new CustomerKind();
        ck.setTaxfree(taxfree);
        ck.setTipologia_clienti(kt_code);
        ck.setDe_tipologia_clienti(descr);
        ck.setFg_tipo_cliente(list_type_customer);
        ck.setFg_nazionalita(list_type_kind);
        ck.setIp_max_singola_transazione(formatDoubleforMysql(thr_kyc));
        ck.setIp_max_settimanale(formatDoubleforMysql(maxweek));
        ck.setFg_area_geografica(list_category_nations);
        ck.setStampa_autocertificazione(ecee);
        ck.setIp_soglia_antiriciclaggio(formatDoubleforMysql(thr_aml));
        ck.setIp_soglia_extraCEE_certification(formatDoubleforMysql(thr_cee));
        ck.setFg_annullato(status);
        ck.setFiliale(dbm.getCodLocal(true)[0]);
        ck.setFg_uploadobbl(uploadobbl);
        ck.setResident(resid);

        ck.setTipofat(formatValue(list_type_selecttipov, "0"));
        ck.setVatcode(formatValue(vatcode, ""));

        ck.setIp_soglia_bollo(formatDoubleforMysql(thr_tax_disc));
        ck.setIp_value_bollo(formatDoubleforMysql(value_tax_disc));
        ck.setDescr_bollo(des_tax_disc);

        boolean es = dbm.edit_Kindtransaction(ck, user, dt_val);
        dbm.closeDB();

        String thr_mini = request.getParameter("thr_mini");
        if (thr_mini == null) {
            thr_mini = "1000.00";
        }
        String thr_quart = request.getParameter("thr_quart");
        if (thr_quart == null) {
            thr_quart = "2500.00";
        }

        Db_Master dbm0 = new Db_Master();
        dbm0.updateConfconAGG("thr.mini", thr_mini);
        dbm0.updateConfconAGG("thr.quart", thr_quart);
        dbm0.closeDB();

        if (es) {
            redirect(request, response, "tb_edit_kindtrans.jsp?kt_code=" + kt_code + "&esito=ok");
        } else {
            redirect(request, response, "tb_edit_kindtrans.jsp?kt_code=" + kt_code + "&esito=koins");
        }

    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void edit_Showag(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String status = request.getParameter("status");
        if (status == null) {
            status = "0";
        } else if (status.trim().equalsIgnoreCase("on")) {
            status = "1";
        } else {
            status = "0";
        }
        Db_Master dbm = new Db_Master();
        boolean es = dbm.edit_Showag(status);
        dbm.closeDB();
        redirect(request, response, "tb_agency.jsp");
    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void insert_new_Agency(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String user = (String) request.getSession().getAttribute("us_cod");
        if (user == null) {
            user = "9999";
        }
        String descr = request.getParameter("descr");
        String addr = request.getParameter("addr");
        String zipc = request.getParameter("zipc");
        String city = request.getParameter("city");
        String phone = request.getParameter("phone");
        String fax = request.getParameter("fax");
        String email = request.getParameter("email");
        Db_Master dbm = new Db_Master();
        String filiale = dbm.getCodLocal(true)[0];
        Agency ag = new Agency();
        ag.setDe_agenzia(descr);
        ag.setIndirizzo(addr);
        ag.setCap(zipc);
        ag.setCitta(city);
        ag.setTelefono(phone);
        ag.setFax(fax);
        ag.setEmail(email);
        ag.setFg_annullato("0");
        ag.setFiliale(filiale);

        boolean es = dbm.insert_new_Agency(ag, user);
        dbm.closeDB();
        if (es) {
            redirect(request, response, "tb_edit_agency.jsp?esito=ok");
        } else {
            redirect(request, response, "tb_edit_agency.jsp?esito=koins");
        }
    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void edit_Agency(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String user = (String) request.getSession().getAttribute("us_cod");
        if (user == null) {
            user = "9999";
        }
        String ag_code = request.getParameter("ag_code");
        String descr = request.getParameter("descr");
        String addr = request.getParameter("addr");
        String zipc = request.getParameter("zipc");
        String city = request.getParameter("city");
        String phone = request.getParameter("phone");
        String fax = request.getParameter("fax");
        String email = request.getParameter("email");

        String status = request.getParameter("status");
        if (status == null) {
            status = "1";
        } else if (status.trim().equalsIgnoreCase("on")) {
            status = "0";
        } else {
            status = "1";
        }

        Agency ag = new Agency();

        ag.setAgenzia(ag_code);
        ag.setDe_agenzia(descr);
        ag.setIndirizzo(addr);
        ag.setCap(zipc);
        ag.setCitta(city);
        ag.setTelefono(phone);
        ag.setFax(fax);
        ag.setEmail(email);
        ag.setFg_annullato(status);

        Db_Master dbm = new Db_Master();
        String filiale = dbm.getCodLocal(true)[0];
        ag.setFiliale(filiale);

        boolean es = dbm.edit_Agency(ag, user);
        dbm.closeDB();

        sleeping(1000);

        if (es) {
            redirect(request, response, "tb_edit_agency.jsp?ag_code=" + ag_code + "&esito=ok");
        } else {
            redirect(request, response, "tb_edit_agency.jsp?ag_code=" + ag_code + "&esito=koins");
        }
    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void insert_new_Intbook(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String user = (String) request.getSession().getAttribute("us_cod");
        if (user == null) {
            user = "9999";
        }
        String descr = request.getParameter("descr");
        Db_Master dbm = new Db_Master();
        String filiale = dbm.getCodLocal(true)[0];
        boolean es = dbm.insert_new_Intbooking(filiale, descr, user);
        dbm.closeDB();
        if (es) {
            redirect(request, response, "tb_edit_intbook.jsp?esito=ok");
        } else {
            redirect(request, response, "tb_edit_intbook.jsp?esito=koins");
        }
    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void edit_Intbook(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String user = (String) request.getSession().getAttribute("us_cod");
        if (user == null) {
            user = "9999";
        }
        String inboo_code = request.getParameter("inboo_code");
        String descr = request.getParameter("descr");
        Db_Master dbm = new Db_Master();
        String filiale = dbm.getCodLocal(true)[0];
        boolean es = dbm.edit_Intbooking(filiale, inboo_code, descr, user);
        dbm.closeDB();
        if (es) {
            redirect(request, response, "tb_edit_intbook.jsp?ib_code=" + inboo_code + "&esito=ok");
        } else {
            redirect(request, response, "tb_edit_intbook.jsp?ib_code=" + inboo_code + "&esito=koins");
        }
    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void insert_new_Credit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Db_Master dbm = new Db_Master();
        String user = (String) request.getSession().getAttribute("us_cod");
        if (user == null) {
            user = "9999";
        }
        String listbranch = request.getParameter("listbranch");
        String filiale = dbm.getCodLocal(true)[0];
        String bra[];
        if (listbranch != null) {
            bra = parseArrayValues(listbranch, ";");
        } else {
            bra = parseArrayValues(dbm.list_branchcode_ENABLED());
        }

        String dt = new DateTime().toString(patternsqldate);

        String descr = request.getParameter("descr");
        String cod = request.getParameter("cod");

        boolean es = dbm.insert_new_Credit(filiale, cod, descr, user, dt);
        if (es) {
            for (String bra1 : bra) {
                dbm.insert_new_Credit(bra1, cod, descr, user, dt);
            }
        }

        dbm.closeDB();
        if (es) {
            redirect(request, response, "tb_edit_creditcard.jsp?esito=ok");
        } else {
            redirect(request, response, "tb_edit_creditcard.jsp?esito=koins");
        }
    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void edit_Credit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String user = (String) request.getSession().getAttribute("us_cod");
        if (user == null) {
            user = "9999";
        }

        String descr = request.getParameter("descr");
        String cod = request.getParameter("cod");
        String status = request.getParameter("status");
        if (status == null) {
            status = "1";
        } else if (status.trim().equalsIgnoreCase("on")) {
            status = "0";
        } else {
            status = "1";
        }
        Db_Master dbm = new Db_Master();
        String listbranch = request.getParameter("listbranch");
        String filiale = dbm.getCodLocal(true)[0];
        String bra[];
        if (listbranch != null) {
            bra = parseArrayValues(listbranch, ";");
        } else {
            bra = parseArrayValues(dbm.list_branchcode_ENABLED());
        }

        String dt = new DateTime().toString(patternsqldate);

        boolean es = dbm.edit_Credit(filiale, cod, descr, status, user, dt);
        if (es) {
            for (String bra1 : bra) {
                dbm.edit_Credit(bra1, cod, descr, status, user, dt);
            }
        }
        dbm.closeDB();
        if (es) {
            redirect(request, response, "tb_edit_creditcard.jsp?cc_code=" + cod + "&esito=ok");
        } else {
            redirect(request, response, "tb_edit_creditcard.jsp?cc_code=" + cod + "&esito=koins");
        }
    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void insert_new_Figures(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String user = (String) request.getSession().getAttribute("us_cod");
        if (user == null) {
            user = "9999";
        }
        Db_Master dbm = new Db_Master();
        String listbranch = request.getParameter("listbranch");
        String filiale = dbm.getCodLocal(true)[0];
        String bra[];
        if (listbranch != null) {
            bra = parseArrayValues(listbranch, ";");
        } else {
            bra = parseArrayValues(dbm.list_branchcode_ENABLED());
        }

        String dt = new DateTime().toString(patternsqldate);

        String dt_val = request.getParameter("dt_val");
        if (dt_val == null) {
            dt_val = "";
        }

        String descr = request.getParameter("descr");
        String list_selectkind = request.getParameter("list_selectkind");
        String buy_com = request.getParameter("buy_com");
        String buy_thr = request.getParameter("buy_thr");
        String buy_fix = request.getParameter("buy_fix");
        String sel_com = request.getParameter("sel_com");
        String sel_thr = request.getParameter("sel_thr");
        String sel_fix = request.getParameter("sel_fix");

        String buy_back = request.getParameter("buy_bac");
        String sel_bac = request.getParameter("sel_bac");

        String pay = request.getParameter("pay");
        if (pay == null) {
            pay = "0";
        } else if (pay.trim().equalsIgnoreCase("on")) {
            pay = "1";
        } else {
            pay = "0";
        }
        String kindre = request.getParameter("kindre");
        if (kindre == null) {
            kindre = "0";
        } else if (kindre.trim().equalsIgnoreCase("on")) {
            kindre = "1";
        } else {
            kindre = "0";
        }

        String uploadobbl = request.getParameter("uploadobbl");
        if (uploadobbl == null) {
            uploadobbl = "0";
        } else if (uploadobbl.trim().equalsIgnoreCase("on")) {
            uploadobbl = "1";
        } else {
            uploadobbl = "0";
        }

        String upl_thr = request.getParameter("upl_thr");

        String buy = "0";

        String fix_com = "0.00";
        String sell = "0";

        switch (list_selectkind) {
            case "1":
                buy = "1";
                sell = "1";
                break;
            case "6":
            case "7":
            case "8":
                buy_com = "0.00";
                buy_thr = "0.00";
                buy_fix = "0.00";
                sel_com = "0.00";
                sel_thr = "0.00";
                sel_fix = "0.00";
                sel_bac = "0.00";
                break;
            case "2":
            case "3":
            case "4":
                buy = "1";
                sel_com = "0.00";
                sel_thr = "0.00";
                sel_fix = "0.00";
                sel_bac = "0.00";
                break;
            default:
                break;
        }

        Figures fi = new Figures();
        fi.setBuy(buy);
        fi.setSell(sell);
        fi.setBuy_back_commission(formatDoubleforMysql(buy_back));
        fi.setBuy_comm_soglia_causale(formatDoubleforMysql(buy_thr));
        fi.setCommissione_acquisto(formatDoubleforMysql(buy_com));
        fi.setCommissione_vendita(formatDoubleforMysql(sel_com));
        fi.setCommissione_fissa(formatDoubleforMysql(fix_com));
        fi.setDe_supporto(descr);
        fi.setFg_annullato("0");
        fi.setFg_sys_trans(list_selectkind);
        fi.setFg_tipo_incasso(pay);
        fi.setFiliale(filiale);
        fi.setFix_buy_commission(formatDoubleforMysql(buy_fix));
        fi.setFix_sell_commission(formatDoubleforMysql(sel_fix));
        fi.setSell_back_commission(formatDoubleforMysql(sel_bac));
        fi.setSell_comm_soglia_causale(formatDoubleforMysql(sel_thr));
        fi.setResidenti(kindre);
        fi.setFg_uploadobbl(uploadobbl);
        fi.setUpload_thr(formatDoubleforMysql(upl_thr));

        boolean es = dbm.insert_new_Figures(fi, user, dt);

        if (es) {

            for (String bra1 : bra) {
                fi = new Figures();
                fi.setBuy(buy);
                fi.setSell(sell);
                fi.setBuy_back_commission(formatDoubleforMysql(buy_back));
                fi.setBuy_comm_soglia_causale(formatDoubleforMysql(buy_thr));
                fi.setCommissione_acquisto(formatDoubleforMysql(buy_com));
                fi.setCommissione_vendita(formatDoubleforMysql(sel_com));
                fi.setCommissione_fissa(formatDoubleforMysql(fix_com));
                fi.setDe_supporto(descr);
                fi.setFg_annullato("0");
                fi.setFg_sys_trans(list_selectkind);
                fi.setFg_tipo_incasso(pay);
                fi.setFiliale(bra1);
                fi.setFix_buy_commission(formatDoubleforMysql(buy_fix));
                fi.setFix_sell_commission(formatDoubleforMysql(sel_fix));
                fi.setSell_back_commission(formatDoubleforMysql(sel_bac));
                fi.setSell_comm_soglia_causale(formatDoubleforMysql(sel_thr));
                fi.setResidenti(kindre);
                fi.setFg_uploadobbl(uploadobbl);
                fi.setUpload_thr(formatDoubleforMysql(upl_thr));
                fi.setDt_val(dt_val);
                //modificare
                if (!filiale.equals(bra1)) {
                    dbm.insert_new_Figures(fi, user, dt);
                }
            }

        }

        dbm.closeDB();
        if (es) {
            redirect(request, response, "tb_edit_figures.jsp?esito=ok");
        } else {
            redirect(request, response, "tb_edit_figures.jsp?esito=koins");
        }

    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void edit_Figures(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

//        printRequest(request);
        String user = (String) request.getSession().getAttribute("us_cod");
        if (user == null) {
            user = "9999";
        }
        Db_Master dbm = new Db_Master();
        String fil = request.getParameter("fil");
        String view = request.getParameter("view");

        String listbranch = request.getParameter("listbranch");
        String filiale = dbm.getCodLocal(true)[0];
        String bra[];
        if (listbranch != null) {
            bra = parseArrayValues(listbranch, ";");
        } else {
            bra = parseArrayValues(dbm.list_branchcode_ENABLED());
        }

        String dt = new DateTime().toString(patternsqldate);

        String dt_val = request.getParameter("dt_val");
        if (dt_val == null) {
            dt_val = "";
        }

        String fi_code = request.getParameter("fi_code");
        String descr = request.getParameter("descr");
        String list_selectkind = request.getParameter("list_selectkind");
        String buy_com = request.getParameter("buy_com");
        String buy_thr = request.getParameter("buy_thr");
        String buy_fix = request.getParameter("buy_fix");
        String sel_com = request.getParameter("sel_com");
        String sel_thr = request.getParameter("sel_thr");
        String sel_fix = request.getParameter("sel_fix");

        String buy_back = request.getParameter("buy_bac");
        String sel_bac = request.getParameter("sel_bac");

        String pay = request.getParameter("pay");
        if (pay == null) {
            pay = "0";
        } else if (pay.trim().equalsIgnoreCase("on")) {
            pay = "1";
        } else {
            pay = "0";
        }
        String kindre = request.getParameter("kindre");
        if (kindre == null) {
            kindre = "0";
        } else if (kindre.trim().equalsIgnoreCase("on")) {
            kindre = "1";
        } else {
            kindre = "0";
        }
        String uploadobbl = request.getParameter("uploadobbl");
        if (uploadobbl == null) {
            uploadobbl = "0";
        } else if (uploadobbl.trim().equalsIgnoreCase("on")) {
            uploadobbl = "1";
        } else {
            uploadobbl = "0";
        }
        String buy = "0";

        String fix_com = "0.00";
        String sell = "0";
        String upl_thr = request.getParameter("upl_thr");

        switch (list_selectkind) {
            case "1":
                buy = "1";
                sell = "1";
                break;
            case "6":
            case "7":
            case "8":
                buy_com = "0.00";
                buy_thr = "0.00";
                buy_fix = "0.00";
                sel_com = "0.00";
                sel_thr = "0.00";
                sel_fix = "0.00";
                sel_bac = "0.00";
                break;
            case "2":
            case "3":
            case "4":
                buy = "1";
                sel_com = "0.00";
                sel_thr = "0.00";
                sel_fix = "0.00";
                sel_bac = "0.00";
                break;
            default:
                break;
        }

        Figures fi = new Figures();

        fi.setSupporto(fi_code);
        fi.setBuy(buy);
        fi.setSell(sell);
        fi.setBuy_back_commission(buy_back);
        fi.setBuy_comm_soglia_causale(buy_thr);
        fi.setCommissione_acquisto(buy_com);
        fi.setCommissione_vendita(sel_com);
        fi.setCommissione_fissa(fix_com);
        fi.setDe_supporto(descr);
        fi.setFg_annullato("0");
        fi.setFg_sys_trans(list_selectkind);
        fi.setFg_tipo_incasso(pay);
        fi.setFiliale(filiale);
        fi.setFix_buy_commission(buy_fix);
        fi.setFix_sell_commission(sel_fix);
        fi.setSell_back_commission(sel_bac);
        fi.setSell_comm_soglia_causale(sel_thr);
        fi.setResidenti(kindre);
        fi.setFg_uploadobbl(uploadobbl);
        fi.setUpload_thr(formatDoubleforMysql(upl_thr));
        fi.setDt_val(dt_val);

        boolean es = dbm.edit_Figures(fi, user, dt);

        if (es) {
            for (String bra1 : bra) {
                fi.setSupporto(fi_code);
                fi.setBuy(buy);
                fi.setSell(sell);
                fi.setBuy_back_commission(buy_back);
                fi.setBuy_comm_soglia_causale(buy_thr);
                fi.setCommissione_acquisto(buy_com);
                fi.setCommissione_vendita(sel_com);
                fi.setCommissione_fissa(fix_com);
                fi.setDe_supporto(descr);
                fi.setFg_annullato("0");
                fi.setFg_sys_trans(list_selectkind);
                fi.setFg_tipo_incasso(pay);
                fi.setFiliale(bra1);
                fi.setFix_buy_commission(buy_fix);
                fi.setFix_sell_commission(sel_fix);
                fi.setSell_back_commission(sel_bac);
                fi.setSell_comm_soglia_causale(sel_thr);
                fi.setResidenti(kindre);
                fi.setFg_uploadobbl(uploadobbl);
                fi.setUpload_thr(formatDoubleforMysql(upl_thr));
                //modificare
                dbm.edit_Figures(fi, user, dt);
            }
        }
        dbm.closeDB();
        if (es) {
            redirect(request, response, "tb_edit_figures.jsp?view=" + view + "&fil=" + fil + "&fi_code=" + fi_code + "&esito=ok");
        } else {
            redirect(request, response, "tb_edit_figures.jsp?view=" + view + "&fil=" + fil + "&fi_code=" + fi_code + "&esito=koins");
        }
    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void ins_wb_services(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DateTime dt1 = new DateTime();
        String dt = dt1.toString(patternsqldate);
        String dt_val = dt1.toString(patternnormdate);
        String user = (String) request.getSession().getAttribute("us_cod");
        if (user == null) {
            user = "9999";
        }
        String fi_code = request.getParameter("fi_code");
        String descr = request.getParameter("descr");
        String typeval = request.getParameter("typeval");
        if (typeval == null) {
            typeval = "1";
        } else if (typeval.trim().equalsIgnoreCase("on")) {
            typeval = "0";
        } else {
            typeval = "1";
        }
        String perc_value = formatDoubleforMysql(request.getParameter("perc_value"));
        String euro_value = formatDoubleforMysql(request.getParameter("euro_value"));
        String sogliaminima = formatDoubleforMysql(request.getParameter("sogliaminima"));

        String data_start = formatStringtoStringDate(request.getParameter("data_start"), patternnormdate_filter, patternsql);
        String data_end = formatStringtoStringDate(request.getParameter("data_end"), patternnormdate_filter, patternsql);

        String currency = replace(request.getParameter("currency"), "---", "");
        String branch = replace(request.getParameter("branch"), "---", "");
        
        
        String buysell = getRequestValue(request, "buysell");
        
        String[] valori = {fi_code, descr, typeval, perc_value, euro_value, sogliaminima,
            data_start, data_end, currency, branch,buysell};
        
        Db_Master dbm = new Db_Master();
        boolean esito = dbm.insert_site_servizi_agg(valori, user, dt_val, dt);
        dbm.closeDB();
        redirect(request, response, "tb_wb_edit_services.jsp?code=" + fi_code + "&esito=" + esito);
    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void ins_wb_discount(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        DateTime dt1 = new DateTime();
        String dt = dt1.toString(patternsqldate);
        String dt_val = dt1.toString(patternnormdate);

        String user = (String) request.getSession().getAttribute("us_cod");
        if (user == null) {
            user = "9999";
        }

        String fi_code = request.getParameter("fi_code");
        String descr = request.getParameter("descr");

        String typeval = request.getParameter("typeval");
        if (typeval == null) {
            typeval = "1";
        } else if (typeval.trim().equalsIgnoreCase("on")) {
            typeval = "0";
        } else {
            typeval = "1";
        }

        String coupon = request.getParameter("coupon");
        if (coupon == null) {
            coupon = "N";
        } else if (coupon.trim().equalsIgnoreCase("on")) {
            coupon = "Y";
        } else {
            coupon = "N";
        }

        String perc_value = formatDoubleforMysql(request.getParameter("perc_value"));
        String euro_value = formatDoubleforMysql(request.getParameter("euro_value"));
        String sogliaminima = formatDoubleforMysql(request.getParameter("sogliaminima"));

        String data_start = formatStringtoStringDate(request.getParameter("data_start"), patternnormdate_filter, patternsql);
        String data_end = formatStringtoStringDate(request.getParameter("data_end"), patternnormdate_filter, patternsql);
        String currency = replace(request.getParameter("currency"), "---", "");
        String branch = replace(request.getParameter("branch"), "---", "");

        //      NUOVO   //
//        String cumulabile_con = StringUtils.replace(request.getParameter("cumulabile_con"), "---", "");
        String[] cumulabile_con = request.getParameterValues("cumulabile_con");
        StringBuilder cumulabile = new StringBuilder("");
        if (cumulabile_con != null) {
            for (String cumulabile_con1 : cumulabile_con) {
                cumulabile.append(cumulabile_con1 + ";");
            }
            if (cumulabile.toString().contains("---")) {
                cumulabile = new StringBuilder("---");
            }
        }
        //      NUOVO   //

        String buysell = getRequestValue(request, "buysell");
        
        String[] valori = {fi_code, descr, typeval, perc_value, euro_value, sogliaminima,
            data_start, data_end, currency, branch, cumulabile.toString(), coupon,buysell};

        Db_Master dbm = new Db_Master();
        boolean esito = dbm.insert_site_agevolazioni_varie(valori, user, dt_val, dt);
        dbm.closeDB();

        redirect(request, response, "tb_wb_edit_discounts.jsp?code=" + fi_code + "&esito=" + esito);

    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void ins_department(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String user = (String) request.getSession().getAttribute("us_cod");
        String de_code = request.getParameter("de_code");
        String nochange = request.getParameter("nochange");
        Db_Master dbm = new Db_Master();
        boolean esito = dbm.inser_department_NC(de_code, nochange, user);
        dbm.closeDB();
        redirect(request, response, "nc_edit_depertment.jsp?esito=" + esito);
    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void delete_depart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String user = (String) request.getSession().getAttribute("us_cod");
        String nochange = request.getParameter("nochange");
        Db_Master dbm = new Db_Master();
        boolean esito = dbm.delete_department_NC(nochange, user);
        dbm.closeDB();
        redirect(request, response, "nc_department.jsp?esito=" + esito);
    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void edit_department(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String user = (String) request.getSession().getAttribute("us_cod");
        String de_code = request.getParameter("de_code");
        String descr = request.getParameter("descr");
        Db_Master dbm = new Db_Master();
        boolean esito = dbm.update_department(de_code, descr, user);
        dbm.closeDB();
        redirect(request, response, "nc_edit_depertment.jsp?de_code=" + de_code + "&esito=" + esito);
    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void edit_wb_ratera(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String user = (String) request.getSession().getAttribute("us_cod");
        if (user == null) {
            user = "9999";
        }
        String dt = new DateTime().toString(patternsqldate);
        String dt_val = request.getParameter("dt_val");
        if (dt_val == null) {
            dt_val = "";
        }
        String ra_code = request.getParameter("ra_code");
        String min_old = request.getParameter("min_old");
        String min = formatDoubleforMysql(request.getParameter("min"));
        String max = formatDoubleforMysql(request.getParameter("max"));
        String sell = request.getParameter("sell");
        String[] valori = {ra_code, min, max, sell};
        Db_Master dbm = new Db_Master();
        boolean esito = dbm.update_site_level_rate(valori, min_old, user, dt_val, dt);
        dbm.closeDB();
        redirect(request, response, "tb_wb_edit_raterange.jsp?ra_code=" + ra_code + "&ra_min=" + min + "&esito=" + esito);
    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void edit_wb_services(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String user = (String) request.getSession().getAttribute("us_cod");
        if (user == null) {
            user = "9999";
        }
        String dt = new DateTime().toString(patternsqldate);
        String dt_val = request.getParameter("dt_val");
        if (dt_val == null) {
            dt_val = "";
        }

        String fi_code = request.getParameter("fi_code");
        String descr = request.getParameter("descr");

        String typeval = request.getParameter("typeval");
        if (typeval == null) {
            typeval = "1";
        } else if (typeval.trim().equalsIgnoreCase("on")) {
            typeval = "0";
        } else {
            typeval = "1";
        }
        String perc_value = formatDoubleforMysql(request.getParameter("perc_value"));
        String euro_value = formatDoubleforMysql(request.getParameter("euro_value"));
        String sogliaminima = formatDoubleforMysql(request.getParameter("sogliaminima"));

        String data_start = formatStringtoStringDate(request.getParameter("data_start"), patternnormdate_filter, patternsql);
        String data_end = formatStringtoStringDate(request.getParameter("data_end"), patternnormdate_filter, patternsql);

        String currency = replace(request.getParameter("currency"), "---", "");
        String branch = replace(request.getParameter("branch"), "---", "");

        String buysell = getRequestValue(request, "buysell");
        
        String[] valori = {fi_code, descr, typeval, perc_value, euro_value, sogliaminima, data_start, data_end, currency, branch, buysell};

        Db_Master dbm = new Db_Master();
        boolean esito = dbm.update_site_servizi_agg(valori, user, dt_val, dt);
        dbm.closeDB();

        redirect(request, response, "tb_wb_edit_services.jsp?code=" + fi_code + "&esito=" + esito);
    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void edit_wb_discount(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        Utility.printRequest(request);

        String user = (String) request.getSession().getAttribute("us_cod");
        if (user == null) {
            user = "9999";
        }
        String dt = new DateTime().toString(patternsqldate);
        String dt_val = request.getParameter("dt_val");
        if (dt_val == null) {
            dt_val = "";
        }

        String oldccod = request.getParameter("oldccod");
        String fi_code = request.getParameter("fi_code");
        String descr = request.getParameter("descr");

        String typeval = request.getParameter("typeval");
        if (typeval == null) {
            typeval = "1";
        } else if (typeval.trim().equalsIgnoreCase("on")) {
            typeval = "0";
        } else {
            typeval = "1";
        }

        String coupon = request.getParameter("coupon");
        if (coupon == null) {
            coupon = "N";
        } else if (coupon.trim().equalsIgnoreCase("on")) {
            coupon = "Y";
        } else {
            coupon = "N";
        }

        String perc_value = formatDoubleforMysql(request.getParameter("perc_value"));
        String euro_value = formatDoubleforMysql(request.getParameter("euro_value"));
        String sogliaminima = formatDoubleforMysql(request.getParameter("sogliaminima"));
        String data_start = formatStringtoStringDate(request.getParameter("data_start"), patternnormdate_filter, patternsql);
        String data_end = formatStringtoStringDate(request.getParameter("data_end"), patternnormdate_filter, patternsql);

        String currency = replace(request.getParameter("currency"), "---", "");
        String branch = replace(request.getParameter("branch"), "---", "");

        //      NUOVO   //
//        String cumulabile_con = StringUtils.replace(request.getParameter("cumulabile_con"), "---", "");
        String[] cumulabile_con = request.getParameterValues("cumulabile_con");
        StringBuilder cumulabile = new StringBuilder("");
        if (cumulabile_con != null) {
            for (String cumulabile_con1 : cumulabile_con) {
                cumulabile.append(cumulabile_con1 + ";");
            }

            if (cumulabile.toString().contains("---")) {
                cumulabile = new StringBuilder("---");
            }
        }
        //      NUOVO   //

        String buysell = getRequestValue(request, "buysell");
        
        String[] valori = {fi_code, descr, typeval, perc_value, euro_value, sogliaminima,
            data_start, data_end, currency, branch, cumulabile.toString(), oldccod, coupon, buysell
        };

        Db_Master dbm = new Db_Master();
        boolean esito = dbm.update_site_agevolazioni_varie(valori, user, dt_val, dt);
        dbm.closeDB();

        redirect(request, response, "tb_wb_edit_discounts.jsp?code=" + fi_code + "&esito=" + esito);

    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void edit_wb_error(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String user = (String) request.getSession().getAttribute("us_cod");
        if (user == null) {
            user = "9999";
        }
        String dt = new DateTime().toString(patternsqldate);
        String dt_val = request.getParameter("dt_val");
        if (dt_val == null) {
            dt_val = "";
        }
        String fi_code = request.getParameter("fi_code");
        String descr = request.getParameter("descr");
        String note = request.getParameter("note");
        String[] valori = {fi_code, descr, note};
        Db_Master dbm = new Db_Master();
        boolean esito = dbm.update_site_causali_variazioni(valori, user, dt_val, dt);
        dbm.closeDB();
        redirect(request, response, "tb_wb_edit_error.jsp?err_code=" + fi_code + "&esito=" + esito);
    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void edit_wb_figur(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String user = (String) request.getSession().getAttribute("us_cod");
        if (user == null) {
            user = "9999";
        }
        String dt = new DateTime().toString(patternsqldate);
        String dt_val = request.getParameter("dt_val");
        if (dt_val == null) {
            dt_val = "";
        }
        String fi_code = request.getParameter("fi_code");
        String sel_com = request.getParameter("sel_com");
        Db_Master dbm = new Db_Master();
        boolean esito = dbm.update_site_supporti(fi_code, formatDoubleforMysql(sel_com), user, dt_val, dt);
        dbm.closeDB();
        redirect(request, response, "tb_wb_edit_figures.jsp?fi_code=" + fi_code + "&esito=" + esito);
    }

    private ArrayList<Rate_history> isModify(HttpServletRequest request, String bra[], String user, String dt, boolean spread) {
        ArrayList<Rate_history> li = new ArrayList<>();
        String cur_code = request.getParameter("cur_code");
        String editce = request.getParameter("editce");
        String dt_val = request.getParameter("dt_val");

        if (null == editce) {
            if (spread) {

//                String typebuy_0 = request.getParameter("typebuy_0");
//                if (typebuy_0 == null) {
//                    typebuy_0 = "1";
//                } else if (typebuy_0.trim().equalsIgnoreCase("on")) {
//                    typebuy_0 = "0";
//                } else {
//                    typebuy_0 = "1";
//                }
//
//                String typesell_0 = request.getParameter("typesell_0");
//                if (typesell_0 == null) {
//                    typesell_0 = "1";
//                } else if (typesell_0.trim().equalsIgnoreCase("on")) {
//                    typesell_0 = "0";
//                } else {
//                    typesell_0 = "1";
//                }
//
//
//
//
//                
//                if (typebuy_0.equals("0")) { //perc
//                    
//                } else { //value
//
//                }
//                
//                if (typesell_0.equals("0")) { //perc
//
//                } else { //value
//
//                }
                for (String bra1 : bra) {
                    String msg = "Head Office edit 'Spread'";
                    Rate_history rh = new Rate_history(generaId(50), bra1, cur_code, "2", msg, user, dt);
                    li.add(rh);
                }

            }
        } else switch (editce) {
            case "0":
                String buy_std_val = formatDoubleforMysql(request.getParameter("buy_std_val"));
                String old_buy_std_val = request.getParameter("old_buy_std_val");
                if (fd(buy_std_val) != fd(old_buy_std_val)) {
                    for (String bra1 : bra) {
                        String msg = "Manual modify: buy: " + request.getParameter("buy_std_val");
                        Rate_history rh = new Rate_history(generaId(50), bra1, cur_code, "2", msg, user, dt);
                        li.add(rh);
                    }
                }
                String sell_std_val = formatDoubleforMysql(request.getParameter("sell_std_val"));
                String old_sell_std_val = request.getParameter("old_sell_std_val");
                if (fd(sell_std_val) != fd(old_sell_std_val)) {
                    for (String bra1 : bra) {
                        String msg = "Manual modify: sell: " + request.getParameter("sell_std_val");
                        Rate_history rh = new Rate_history(generaId(50), bra1, cur_code, "2", msg, user, dt);
                        li.add(rh);
                    }
                }
                break;
            case "1":
                String bce = formatDoubleforMysql(request.getParameter("bce"));
                String oldbce = request.getParameter("oldbce");
                if (fd(bce) != fd(oldbce)) {
                    for (String bra1 : bra) {
                        Db_Master dbm1 = new Db_Master();
                        String[] valorivaluta = dbm1.get_currency_filiale(bra1, cur_code);
                        dbm1.closeDB();
                        if (valorivaluta != null) {
                            double d_rifbce = fd(bce);
                            String buy_perc = valorivaluta[2];
                            String sel_perc = valorivaluta[4];
                            double d_standard_b = fd(buy_perc);
                            double d_standard_s = fd(sel_perc);
                            String tot_st_b = formatMysqltoDisplay(roundDoubleandFormat((d_rifbce * (100.0D + d_standard_b) / 100.0D), 8));
                            String tot_st_s = formatMysqltoDisplay(roundDoubleandFormat((d_rifbce * (100.0D + d_standard_s) / 100.0D), 8));
                            //  calcola buy/Sell
                            String msg = "Manual modify bce. <br>BCE value " + request.getParameter("bce") + "<br>Buy Std: " + tot_st_b + "<br>Sell Std: " + tot_st_s + "<br>Date validity: " + dt_val;
                            //String msg = "Manual modify bce. <br> BCE value " + request.getParameter("bce") + " <br>Date validity: " + dt_val;
                            Rate_history rh = new Rate_history(generaId(50), bra1, cur_code, "0", msg, user, dt);
                            li.add(rh);
                        }
                    }
                }
                break;
            default:
                break;
        }

        return li;
    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void edit_wb_spread(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

//        Utility.printRequest(request);
//        if (true) {
//            return;
//        }
        String buy = request.getParameter("buy");
        if (buy == null) {
            buy = "0";
        } else if (buy.trim().equalsIgnoreCase("on")) {
            buy = "1";
        } else {
            buy = "0";
        }

        String sell = request.getParameter("sell");
        if (sell == null) {
            sell = "0";
        } else if (sell.trim().equalsIgnoreCase("on")) {
            sell = "1";
        } else {
            sell = "0";
        }

        String codice_uic = buy + ";" + sell;

        String user = (String) request.getSession().getAttribute("us_cod");
        if (user == null) {
            user = "9999";
        }

        String datenow = new DateTime().toString(patternsqldate);

        String cur_code = request.getParameter("cur_code");

        String dt_val = request.getParameter("dt_val");
        if (dt_val == null) {
            dt_val = "";
        }

        String buy_std_perc = request.getParameter("buy_std_perc");
        String buy_l1 = request.getParameter("buy_l1");
        String buy_l2 = request.getParameter("buy_l2");
        String buy_l3 = request.getParameter("buy_l3");
        String buy_best = request.getParameter("buy_best");
        String sell_std_perc = request.getParameter("sell_std_perc");
        String sell_l1 = request.getParameter("sell_l1");
        String sell_l2 = request.getParameter("sell_l2");
        String sell_l3 = request.getParameter("sell_l3");
        String sell_be = request.getParameter("sell_be");

        String[] valori = {cur_code,
            formatDoubleforMysql(buy_std_perc), formatDoubleforMysql(buy_l1), formatDoubleforMysql(buy_l2),
            formatDoubleforMysql(buy_l3), formatDoubleforMysql(buy_best),
            formatDoubleforMysql(sell_std_perc), formatDoubleforMysql(sell_l1), formatDoubleforMysql(sell_l2),
            formatDoubleforMysql(sell_l3), formatDoubleforMysql(sell_be), codice_uic};

        Db_Master dbm = new Db_Master();

        boolean es = dbm.update_site_spread(valori, dt_val, datenow, user);

        dbm.closeDB();

        redirect(request, response, "tb_wb_edit_spread.jsp?cur_code=" + cur_code + "&esito=" + es);

    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void edit_spread(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String user = (String) request.getSession().getAttribute("us_cod");
        if (user == null) {
            user = "9999";
        }

        Db_Master dbm = new Db_Master();
        String listbranch = request.getParameter("listbranch");
        String bra[];
        if (listbranch != null) {
            bra = parseArrayValues(listbranch, ";");
        } else {
            bra = parseArrayValues(dbm.list_branchcode_ENABLED());
        }

        String dt = new DateTime().toString(patternsqldate);
        ArrayList<Rate_history> li_rh = isModify(request, bra, user, dt, true);

        String cur_code = request.getParameter("cur_code");

        String dt_val = request.getParameter("dt_val");
        if (dt_val == null) {
            dt_val = "";
        }

        String typebuy_0 = request.getParameter("typebuy_0");
        if (typebuy_0 == null) {
            typebuy_0 = "1";
        } else if (typebuy_0.trim().equalsIgnoreCase("on")) {
            typebuy_0 = "0";
        } else {
            typebuy_0 = "1";
        }

        String buy_std_perc = request.getParameter("buy_std_perc");
        String buy_std_val = request.getParameter("buy_std_val");

        String buy_l1 = request.getParameter("buy_l1");
        String buy_l2 = request.getParameter("buy_l2");
        String buy_l3 = request.getParameter("buy_l3");
        String buy_best = request.getParameter("buy_best");

        String typesell_0 = request.getParameter("typesell_0");
        if (typesell_0 == null) {
            typesell_0 = "1";
        } else if (typesell_0.trim().equalsIgnoreCase("on")) {
            typesell_0 = "0";
        } else {
            typesell_0 = "1";
        }

        String sell_std_perc = request.getParameter("sell_std_perc");
        String sell_std_val = request.getParameter("sell_std_val");

        String sell_l1 = request.getParameter("sell_l1");
        String sell_l2 = request.getParameter("sell_l2");
        String sell_l3 = request.getParameter("sell_l3");
        String sell_be = request.getParameter("sell_be");

        String error = "0";
        for (String bra1 : bra) {
            Currency cu = new Currency();
            cu.setCode(cur_code);
            cu.setFilial(bra1);
            cu.setBuy_std(formatDoubleforMysql(buy_std_perc));
            cu.setBuy_l1(formatDoubleforMysql(buy_l1));
            cu.setBuy_l2(formatDoubleforMysql(buy_l2));
            cu.setBuy_l3(formatDoubleforMysql(buy_l3));
            cu.setBuy_best(formatDoubleforMysql(buy_best));
            cu.setSell_std(formatDoubleforMysql(sell_std_perc));
            cu.setSell_l1(formatDoubleforMysql(sell_l1));
            cu.setSell_l2(formatDoubleforMysql(sell_l2));
            cu.setSell_l3(formatDoubleforMysql(sell_l3));
            cu.setSell_best(formatDoubleforMysql(sell_be));
            cu.setBuy_std_type(typebuy_0);
            cu.setBuy_std_value(formatDoubleforMysql(buy_std_val));
            cu.setSell_std_type(typesell_0);
            cu.setSell_std_value(formatDoubleforMysql(sell_std_val));
            cu.setDt_val(dt_val);
            boolean es = dbm.edit_Currency_spread(cu, user);
            if (!es) {
                error = "1";
            }
        }
        dbm.closeDB();
        if (error.equals("0")) {
            if (listbranch != null) {
                Db_Master dbm1 = new Db_Master();
                for (int i = 0; i < li_rh.size(); i++) {
                    dbm1.insert_ratehistory(li_rh.get(i), listbranch.replaceAll(";", ","));
                }
                dbm1.closeDB();
            }
            redirect(request, response, "tb_edit_spread.jsp?cur_code=" + cur_code + "&listbranch=" + listbranch + "&esito=ok");
        } else {
            redirect(request, response, "tb_edit_spread.jsp?cur_code=" + cur_code + "&listbranch=" + listbranch + "&esito=ko" + error);
        }
    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void edit_Currency(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //Utility.printRequest(request);
        String user = (String) request.getSession().getAttribute("us_cod");
        if (user == null) {
            user = "9999";
        }

        //21/05
        String sellba = request.getParameter("sellba");
        if (sellba == null) {
            sellba = "0";
        } else if (sellba.trim().equalsIgnoreCase("on")) {
            sellba = "1";
        } else {
            sellba = "0";
        }

        Db_Master dbm = new Db_Master();
        String listbranch = request.getParameter("listbranch");
        String filiale = dbm.getCodLocal(true)[0];
        boolean modifica_da_filiale = !filiale.equals("000");
        String bra[];
        if (listbranch != null) {
            bra = parseArrayValues(listbranch, ";");
        } else {
            bra = parseArrayValues(dbm.list_branchcode_ENABLED());
        }

//
        String dt = new DateTime().toString(patternsqldate);
        ArrayList<Rate_history> li_rh = isModify(request, bra, user, dt, false);

        String dt_val = request.getParameter("dt_val");
        if (dt_val == null) {
            dt_val = "";
        }
        String editce = request.getParameter("editce");
        String cur_code = request.getParameter("cur_code");
        String uic_code = request.getParameter("uic_code");
        String descr = request.getParameter("descr");
        String messa = request.getParameter("messa");
        String internal = request.getParameter("internal");
        if (internal == null) {
            internal = "0";
        } else if (internal.trim().equalsIgnoreCase("on")) {
            internal = "1";
        } else {
            internal = "0";
        }
//
        String bce = request.getParameter("bce");
        String oldbce = request.getParameter("oldbce");
        String buy = request.getParameter("buy");
        if (buy == null) {
            buy = "0";
        } else if (buy.trim().equalsIgnoreCase("on")) {
            buy = "1";
        } else {
            buy = "0";
        }
        String sell = request.getParameter("sell");
        if (sell == null) {
            sell = "0";
        } else if (sell.trim().equalsIgnoreCase("on")) {
            sell = "1";
        } else {
            sell = "0";
        }

        String typebuy_0 = request.getParameter("typebuy_0");
        if (typebuy_0 == null) {
            typebuy_0 = "1";
        } else if (typebuy_0.trim().equalsIgnoreCase("on")) {
            typebuy_0 = "0";
        } else {
            typebuy_0 = "1";
        }

        String buy_std_perc = request.getParameter("buy_std_perc");
        String buy_std_val = request.getParameter("buy_std_val");
        String old_buy_std_val = request.getParameter("old_buy_std_val");

        String buy_l1 = request.getParameter("buy_l1");
        String buy_l2 = request.getParameter("buy_l2");
        String buy_l3 = request.getParameter("buy_l3");
        String buy_best = request.getParameter("buy_best");

        String typesell_0 = request.getParameter("typesell_0");
        if (typesell_0 == null) {
            typesell_0 = "1";
        } else if (typesell_0.trim().equalsIgnoreCase("on")) {
            typesell_0 = "0";
        } else {
            typesell_0 = "1";
        }

        String sell_std_perc = request.getParameter("sell_std_perc");
        String sell_std_val = request.getParameter("sell_std_val");
        String old_sell_std_val = request.getParameter("old_sell_std_val");

        String sell_l1 = request.getParameter("sell_l1");
        String sell_l2 = request.getParameter("sell_l2");
        String sell_l3 = request.getParameter("sell_l3");
        String sell_be = request.getParameter("sell_be");
        String sell_ext = request.getParameter("sell_ext");

        ArrayList<String[]> list_kind = list_all_kind(filiale);
        ArrayList<String[]> list_kind_value = new ArrayList<>();
        for (int j = 0; j < list_kind.size(); j++) {
            String statkind = request.getParameter("statkind" + ((String[]) list_kind.get(j))[0]);
            if (statkind == null) {
                statkind = "0";
            } else if (statkind.trim().equalsIgnoreCase("on")) {
                statkind = "1";
            } else {
                statkind = "0";
            }
            String[] ris = {((String[]) list_kind.get(j))[0], statkind};
            list_kind_value.add(ris);
        }

        int sizecutindex = parseIntR(request.getParameter("sizecutindex"));

        List<Sizecuts> list_sizecut = new ArrayList<>();

//        ArrayList<String[]> list_sizecut = new ArrayList<>();
        for (int j = 0; j <= sizecutindex; j++) {
            String sold = request.getParameter("sizecut_old" + j);
            String snew = formatDoubleforMysql(request.getParameter("sizecut_" + j));
            if (sold == null) {
                sold = snew;
            }
            String status = request.getParameter("statsizecut_" + j);
            if (null == status) {
                status = "0";
            } else switch (status) {
                case "on":
                    status = "1";
                    break;
                default:
                    status = "0";
                    break;
            }

            Sizecuts sz = new Sizecuts("", cur_code, sold, status);
            sz.setIp_taglio_MOD(snew);
            list_sizecut.add(sz);
        }

        String error = "0";
        if (modifica_da_filiale) {
            insertTR("W", user, "MODIFY RATE FROM BRANCH - CURRENCY " + cur_code);
            boolean es = dbm.edit_Currency_dafiliale(formatDoubleforMysql(buy_std_val), formatDoubleforMysql(sell_std_val), filiale, cur_code, user);
            if (!es) {
                error = "1";
            }
        } else {
            for (String bra1 : bra) {
                Currency cu = new Currency();
                cu.setCode(cur_code);
                cu.setCambio_bce(formatDoubleforMysql(bce));
                cu.setDescrizione(descr);
                cu.setEnable_sellback(sellba);
                cu.setChange_sell(formatDoubleforMysql(sell_ext));
                cu.setUic(uic_code);
                cu.setMessage(messa);
                cu.setInternal_cur(internal);
                cu.setFilial(bra1);
                cu.setBuy_std(formatDoubleforMysql(buy_std_perc));
                cu.setBuy_l1(formatDoubleforMysql(buy_l1));
                cu.setBuy_l2(formatDoubleforMysql(buy_l2));
                cu.setBuy_l3(formatDoubleforMysql(buy_l3));
                cu.setBuy_best(formatDoubleforMysql(buy_best));
                cu.setSell_std(formatDoubleforMysql(sell_std_perc));
                cu.setSell_l1(formatDoubleforMysql(sell_l1));
                cu.setSell_l2(formatDoubleforMysql(sell_l2));
                cu.setSell_l3(formatDoubleforMysql(sell_l3));
                cu.setSell_best(formatDoubleforMysql(sell_be));
                cu.setEnable_buy(buy);
                cu.setEnable_sell(sell);
                cu.setBuy_std_type(typebuy_0);
                cu.setBuy_std_value(formatDoubleforMysql(buy_std_val));
                cu.setSell_std_type(typesell_0);
                cu.setSell_std_value(formatDoubleforMysql(sell_std_val));
                cu.setEditce(editce);
                cu.setDt_val(dt_val);
                boolean es;
                if (editce.equals("1")) {
                    es = dbm.edit_Currency_nospread(cu, user);
                } else {
                    es = dbm.edit_Currency(cu, user);
                }
                if (es) {
                    boolean es2 = dbm.edit_Currency_Supporti(cu, list_kind_value, user);
                    if (es2) {
                        boolean es3 = dbm.edit_Currency_Tagli(cu, list_sizecut, user);
                        if (!es3) {
                            error = "3";
                        }
                    } else {
                        error = "2";
                    }
                } else {
                    error = "1";
                }
            }
        }

        dbm.closeDB();

        insertTR("W", user, "MODIFY RATE END");

        if (error.equals("0")) {
            if (listbranch != null) {
                Db_Master dbm1 = new Db_Master();
                for (int i = 0; i < li_rh.size(); i++) {
                    dbm1.insert_ratehistory(li_rh.get(i), listbranch.replaceAll(";", ","));
                }
                dbm1.closeDB();
            }
            redirect(request, response, "tb_edit_currency.jsp?editce=" + editce + "&cur_code=" + cur_code + "&listbranch=" + listbranch + "&esito=ok");
        } else {
            redirect(request, response, "tb_edit_currency.jsp?editce=" + editce + "&cur_code=" + cur_code + "&listbranch=" + listbranch + "&esito=ko" + error);
        }

    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void insert_new_Bank(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String user = (String) request.getSession().getAttribute("us_cod");
        if (user == null) {
            user = "9999";
        }

        String ba_code = request.getParameter("ba_code");
        String descr = request.getParameter("descr");
        String esol = request.getParameter("esol");

        String bankaccount = request.getParameter("bankaccount");
        if (bankaccount == null) {
            bankaccount = "N";
        } else if (bankaccount.trim().equalsIgnoreCase("on")) {
            bankaccount = "Y";
        } else {
            bankaccount = "N";
        }
        Db_Master dbm = new Db_Master();
        String filiale = dbm.getCodLocal(true)[0];
        boolean es = dbm.insert_new_Bank(filiale, ba_code, descr, esol, user, bankaccount);
        dbm.closeDB();
        if (es) {
            redirect(request, response, "tb_edit_bank.jsp?esito=ok");
        } else {
            redirect(request, response, "tb_edit_bank.jsp?esito=koins");
        }
    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void edit_Bank(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String user = (String) request.getSession().getAttribute("us_cod");
        if (user == null) {
            user = "9999";
        }

        String oldba_code = request.getParameter("oldba_code");
        String ba_code = request.getParameter("ba_code");
        String descr = request.getParameter("descr");
        String esol = request.getParameter("esol");
        String datecanc = request.getParameter("datecanc");
        String oldstatus = request.getParameter("oldstatus");
        String status = request.getParameter("status");
        if (status == null) {
            status = "1";
        } else if (status.trim().equalsIgnoreCase("on")) {
            status = "0";
        } else {
            status = "1";
        }
        if (status.equals("1")) {
            if (oldstatus.equals("0")) {
                datecanc = new DateTime().toString("dd/MM/yyyy");
            }
        } else {
            datecanc = "-";
        }
        String bankaccount = request.getParameter("bankaccount");
        if (bankaccount == null) {
            bankaccount = "N";
        } else if (bankaccount.trim().equalsIgnoreCase("on")) {
            bankaccount = "Y";
        } else {
            bankaccount = "N";
        }
        Db_Master dbm = new Db_Master();
        String filiale = dbm.getCodLocal(true)[0];

        boolean es = dbm.edit_Bank(filiale, oldba_code, ba_code, descr, esol, datecanc, status, user, bankaccount);
        dbm.closeDB();
        if (es) {
            redirect(request, response, "tb_edit_bank.jsp?ba_code=" + ba_code + "&esito=ok");
        } else {
            redirect(request, response, "tb_edit_bank.jsp?ba_code=" + ba_code + "&esito=koins");
        }
    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void insert_new_Branch(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String user = (String) request.getSession().getAttribute("us_cod");
        if (user == null) {
            user = "9999";
        }

        String br_code = request.getParameter("br_code");
        String descr = request.getParameter("descr");
        String addr = request.getParameter("addr");
        String city = request.getParameter("city");
        String zipc = request.getParameter("zipc");
        String comp = request.getParameter("comp");
        if (comp == null) {
            comp = "0";
        } else if (comp.trim().equalsIgnoreCase("on")) {
            comp = "1";
        } else {
            comp = "0";
        }

        String age = request.getParameter("age");
        if (age == null) {
            age = "0";
        } else if (age.trim().equalsIgnoreCase("on")) {
            age = "1";
        } else {
            age = "0";
        }

        String datea = request.getParameter("datea");
        String max_ass = request.getParameter("max_ass");
        String target = request.getParameter("target");

        String rv = "-";
        String g01 = "-";
        String g02 = "-";
        String g03 = "-";

        String modra = request.getParameter("modra");
        if (modra == null) {
            modra = "0";
        } else if (modra.trim().equalsIgnoreCase("on")) {
            modra = "1";
        } else {
            modra = "0";
        }

        String crm = request.getParameter("crm");
        if (crm == null) {
            crm = "0";
        } else if (crm.trim().equalsIgnoreCase("on")) {
            crm = "1";
        } else {
            crm = "0";
        }
        String pad = request.getParameter("pad");
        if (pad == null) {
            pad = "0";
        } else if (pad.trim().equalsIgnoreCase("on")) {
            pad = "1";
        } else {
            pad = "0";
        }

//        String oltauser = request.getParameter("oltauser");
//        String oltapass = request.getParameter("oltapass");
//
//        String paymat1 = request.getParameter("paymat1");
//        String paymat2 = request.getParameter("paymat2");
//        String paymat3 = request.getParameter("paymat3");
//        String paymat4 = request.getParameter("paymat4");
//        String paymat5 = request.getParameter("paymat5");
//        String paymat6 = request.getParameter("paymat6");
//        String paymat7 = request.getParameter("paymat7");
        String brgr_01 = request.getParameter("brgr_01");
        String brgr_02 = request.getParameter("brgr_02");
        String brgr_03 = request.getParameter("brgr_03");
        String brgr_04 = request.getParameter("brgr_04");
        String brgr_05 = request.getParameter("brgr_05");
        String brgr_06 = request.getParameter("brgr_06");
        String brgr_07 = request.getParameter("brgr_07");
        String brgr_08 = request.getParameter("brgr_08");
        String brgr_09 = request.getParameter("brgr_09");
        String brgr_10 = request.getParameter("brgr_10");

        String oltauser = "-";
        String oltapass = "-";

        String paymat1 = "-";
        String paymat2 = "-";
        String paymat3 = "-";
        String paymat4 = "-";
        String paymat5 = "-";
        String paymat6 = "-";
        String paymat7 = "-";

        if (is_CZ) {
            paymat7 = request.getParameter("otime");
            if (paymat7 == null) {
                paymat7 = "-";
            }
        }

        Branch ba = new Branch();

        ba.setPay_nomeazienda(paymat1);
        ba.setPay_idazienda(paymat2);
        ba.setPay_skin(paymat3);
        ba.setPay_user(paymat4);
        ba.setPay_password(paymat5);
        ba.setPay_token(paymat6);
        ba.setPay_terminale(paymat7);
        Db_Master dbm = new Db_Master();
        ba.setCod(br_code);
        String filiale = dbm.getCodLocal(true)[0];
        ba.setFiliale(filiale);
        ba.setDe_branch(descr);
        ba.setAdd_city(city);
        ba.setAdd_cap(zipc);
        ba.setAdd_via(addr);
        ba.setFg_persgiur(comp);
        ba.setFg_agency(age);
        ba.setProv_raccval(rv);
        ba.setDa_annull("");
        ba.setFg_annullato("0");
        ba.setG01(g01);
        ba.setG02(g02);
        ba.setG03(g03);
        ba.setFg_modrate(modra);
        ba.setFg_crm(crm);
        ba.setFg_pad(pad);
        ba.setOlta_user(oltauser);
        ba.setOlta_pass(oltapass);

        ba.setBrgr_01(brgr_01);
        ba.setBrgr_02(brgr_02);
        ba.setBrgr_03(brgr_03);
        ba.setBrgr_04(brgr_04);
        ba.setBrgr_05(brgr_05);
        ba.setBrgr_06(brgr_06);
        ba.setBrgr_07(brgr_07);
        ba.setBrgr_08(brgr_08);
        ba.setBrgr_09(brgr_09);
        ba.setBrgr_10(brgr_10);
        ba.setDt_start(datea);
        ba.setMax_ass(formatDoubleforMysql(max_ass));
        ba.setTarget(formatDoubleforMysql(target));
        boolean es = dbm.insert_new_Branch(ba, user);
        dbm.closeDB();

        if (es) {
            String atl_code = request.getParameter("atl_code");
            if (atl_code != null) {
                Db_Master db0 = new Db_Master();
                boolean es1 = db0.mod_codice_ATL(br_code, atl_code, "branch_atl");
                db0.closeDB();
                if (es1) {
                    insertTR("W", user, "INSERITO/MODIFICATO CODICE ATLANTE BRANCH " + br_code + " : " + atl_code);
                }
            }
        }

        if (es) {
            redirect(request, response, "tb_edit_branch.jsp?esito=ok");
        } else {
            redirect(request, response, "tb_edit_branch.jsp?esito=koins");
        }
    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void edit_Branch(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String user = (String) request.getSession().getAttribute("us_cod");
        if (user == null) {
            user = "9999";
        }
        String br_code = request.getParameter("br_code");
        String descr = request.getParameter("descr");
        String addr = request.getParameter("addr");
        String city = request.getParameter("city");
        String zipc = request.getParameter("zipc");
        String comp = request.getParameter("comp");
        if (comp == null) {
            comp = "0";
        } else if (comp.trim().equalsIgnoreCase("on")) {
            comp = "1";
        } else {
            comp = "0";
        }
        String age = request.getParameter("age");
        if (age == null) {
            age = "0";
        } else if (age.trim().equalsIgnoreCase("on")) {
            age = "1";
        } else {
            age = "0";
        }
        String datea = request.getParameter("datea");
        String max_ass = request.getParameter("max_ass");
        String target = request.getParameter("target");

        String rv = "-";
        String g01 = "-";
        String g02 = "-";
        String g03 = "-";

        String oltauser = request.getParameter("oltauser");
        String oltapass = request.getParameter("oltapass");

        String datecanc = request.getParameter("datecanc");
        String oldstatus = request.getParameter("oldstatus");
        String status = request.getParameter("status");
        if (status == null) {
            status = "1";
        } else if (status.trim().equalsIgnoreCase("on")) {
            status = "0";
        } else {
            status = "1";
        }
        if (status.equals("1")) {
            if (oldstatus.equals("0")) {
                datecanc = new DateTime().toString("yyyy-MM-dd");
            }
        } else {
            datecanc = "-";
        }
        String modra = request.getParameter("modra");
        if (modra == null) {
            modra = "0";
        } else if (modra.trim().equalsIgnoreCase("on")) {
            modra = "1";
        } else {
            modra = "0";
        }
        String pad = request.getParameter("pad");
        if (pad == null) {
            pad = "0";
        } else if (pad.trim().equalsIgnoreCase("on")) {
            pad = "1";
        } else {
            pad = "0";
        }

        String crm = request.getParameter("crm");
        if (crm == null) {
            crm = "0";
        } else if (crm.trim().equalsIgnoreCase("on")) {
            crm = "1";
        } else {
            crm = "0";
        }

        String paymat1 = request.getParameter("paymat1");
        String paymat2 = request.getParameter("paymat2");
        String paymat3 = request.getParameter("paymat3");
        String paymat4 = request.getParameter("paymat4");
        String paymat5 = request.getParameter("paymat5");
        String paymat6 = request.getParameter("paymat6");
        String paymat7 = request.getParameter("paymat7");

        String brgr_01 = request.getParameter("brgr_01");
        String brgr_02 = request.getParameter("brgr_02");
        String brgr_03 = request.getParameter("brgr_03");
        String brgr_04 = request.getParameter("brgr_04");
        String brgr_05 = request.getParameter("brgr_05");
        String brgr_06 = request.getParameter("brgr_06");
        String brgr_07 = request.getParameter("brgr_07");
        String brgr_08 = request.getParameter("brgr_08");
        String brgr_09 = request.getParameter("brgr_09");
        String brgr_10 = request.getParameter("brgr_10");

        if (is_CZ) {
            paymat7 = request.getParameter("otime");
            if (paymat7 == null) {
                paymat7 = "";
            }
        }

        Db_Master dbm = new Db_Master();

        Branch ba = new Branch();

        ba.setPay_nomeazienda(paymat1);
        ba.setPay_idazienda(paymat2);
        ba.setPay_skin(paymat3);
        ba.setPay_user(paymat4);
        ba.setPay_password(paymat5);
        ba.setPay_token(paymat6);
        ba.setPay_terminale(paymat7);
        String filiale = dbm.getCodLocal(true)[0];
        ba.setFiliale(filiale);
        ba.setCod(br_code);
        ba.setDe_branch(descr);
        ba.setAdd_city(city);
        ba.setAdd_cap(zipc);
        ba.setAdd_via(addr);
        ba.setFg_persgiur(comp);
        ba.setFg_agency(age);
        ba.setProv_raccval(rv);
        ba.setDa_annull(datecanc);
        ba.setFg_annullato(status);
        ba.setG01(g01);
        ba.setG02(g02);
        ba.setG03(g03);
        ba.setFg_modrate(modra);
        ba.setFg_crm(crm);
        ba.setOlta_user(oltauser);
        ba.setOlta_pass(oltapass);
        ba.setFg_pad(pad);
        ba.setDt_start(datea);
        ba.setMax_ass(formatDoubleforMysql(max_ass));
        ba.setTarget(formatDoubleforMysql(target));
        ba.setBrgr_01(brgr_01);
        ba.setBrgr_02(brgr_02);
        ba.setBrgr_03(brgr_03);
        ba.setBrgr_04(brgr_04);
        ba.setBrgr_05(brgr_05);
        ba.setBrgr_06(brgr_06);
        ba.setBrgr_07(brgr_07);
        ba.setBrgr_08(brgr_08);
        ba.setBrgr_09(brgr_09);
        ba.setBrgr_10(brgr_10);

        boolean es = dbm.edit_Branch(ba, user);
        dbm.closeDB();

        if (es) {
            String atl_code = request.getParameter("atl_code");
            if (atl_code != null) {
                Db_Master db0 = new Db_Master();
                boolean es1 = db0.mod_codice_ATL(br_code, atl_code, "branch_atl");
                db0.closeDB();
                if (es1) {
                    insertTR("W", user, "INSERITO/MODIFICATO CODICE ATLANTE BRANCH " + br_code + " : " + atl_code);
                }
            }
        }

        if (es) {
            redirect(request, response, "tb_edit_branch.jsp?br_code=" + br_code + "&esito=ok");
        } else {
            redirect(request, response, "tb_edit_branch.jsp?br_code=" + br_code + "&esito=koins");
        }
    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void insert_new_Black(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String user = (String) request.getSession().getAttribute("us_cod");
        if (user == null) {
            user = "9999";
        }

        String surname = request.getParameter("surname").toUpperCase();
        String name = request.getParameter("name").toUpperCase();
        String sex = request.getParameter("sex");
        if (sex == null) {
            sex = "F";
        } else if (sex.trim().equalsIgnoreCase("on")) {
            sex = "M";
        } else {
            sex = "F";
        }
        String taxc = request.getParameter("taxc").toUpperCase();
        String country0 = request.getParameter("country0");
        String city0 = request.getParameter("city0");
        String addr = request.getParameter("addr");
        String zipc0 = request.getParameter("zipc0");
        String district0 = request.getParameter("district0");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String city1 = request.getParameter("city1");
        String district1 = request.getParameter("district1");
        String country1 = request.getParameter("country1");
        String date1 = request.getParameter("date1");
        String doctype = request.getParameter("doctype");
        String numdoc = request.getParameter("numdoc");
        String isdate = request.getParameter("isdate");
        String exdate = request.getParameter("exdate");
        String dociss = request.getParameter("dociss");
        String docplace = request.getParameter("docplace");
        String txtmsg = request.getParameter("txtmsg");
        DateTime dt1 = new DateTime();

        BlacklistM bl = new BlacklistM();
        bl.setCognome(surname);
        bl.setNome(name);
        bl.setSesso(sex);
        bl.setCodfisc(taxc);
        bl.setNazione(country0);
        bl.setCitta(city0);
        bl.setIndirizzo(addr);
        bl.setCap(zipc0);
        bl.setProvincia(district0);
        bl.setCitta_nascita(city1);
        bl.setProvincia_nascita(district1);
        bl.setNazione_nascita(country1);
        bl.setDt_nascita(date1);
        bl.setTipo_documento(doctype);
        bl.setNumero_documento(numdoc);
        bl.setDt_rilascio_documento(isdate);
        bl.setDt_scadenza_documento(exdate);
        bl.setRilasciato_da_documento(dociss);
        bl.setLuogo_rilascio_documento(docplace);
        bl.setEmail(email);
        bl.setTelefono(phone);
        bl.setDt_blocco(dt1.toString("yyyy-MM-dd"));
        bl.setFg_annullato("0");
        bl.setTimestamp(dt1.toString("yyyy-MM-dd HH:mm:ss"));
        bl.setText(txtmsg);

        Db_Master dbm = new Db_Master();
        boolean es = dbm.insert_new_Blacklist(bl, user);
        dbm.closeDB();
        if (es) {
            redirect(request, response, "tb_edit_blackM.jsp?esito=ok");
        } else {
            redirect(request, response, "tb_edit_blackM.jsp?esito=koins");
        }
    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void edit_Black(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String user = (String) request.getSession().getAttribute("us_cod");
        if (user == null) {
            user = "9999";
        }
        String bl_code = request.getParameter("bl_code");

        String surname = request.getParameter("surname").toUpperCase();
        String name = request.getParameter("name").toUpperCase();
        String sex = request.getParameter("sex");
        if (sex == null) {
            sex = "F";
        } else if (sex.trim().equalsIgnoreCase("on")) {
            sex = "M";
        } else {
            sex = "F";
        }

        String status = request.getParameter("status");
        if (status == null) {
            status = "1";
        } else if (status.trim().equalsIgnoreCase("on")) {
            status = "0";
        } else {
            status = "1";
        }

        String taxc = request.getParameter("taxc").toUpperCase();
        String country0 = request.getParameter("country0");
        String city0 = request.getParameter("city0");
        String addr = request.getParameter("addr");
        String zipc0 = request.getParameter("zipc0");
        String district0 = request.getParameter("district0");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String city1 = request.getParameter("city1");
        String district1 = request.getParameter("district1");
        String country1 = request.getParameter("country1");
        String date1 = request.getParameter("date1");
        String doctype = request.getParameter("doctype");
        String numdoc = request.getParameter("numdoc");
        String isdate = request.getParameter("isdate");
        String exdate = request.getParameter("exdate");
        String dociss = request.getParameter("dociss");
        String docplace = request.getParameter("docplace");
        String txtmsg = request.getParameter("txtmsg");
        DateTime dt1 = new DateTime();

        BlacklistM bl = new BlacklistM();
        bl.setCode(bl_code);
        bl.setCognome(surname);
        bl.setNome(name);
        bl.setSesso(sex);
        bl.setCodfisc(taxc);
        bl.setNazione(country0);
        bl.setCitta(city0);
        bl.setIndirizzo(addr);
        bl.setCap(zipc0);
        bl.setProvincia(district0);
        bl.setCitta_nascita(city1);
        bl.setProvincia_nascita(district1);
        bl.setNazione_nascita(country1);
        bl.setDt_nascita(date1);
        bl.setTipo_documento(doctype);
        bl.setNumero_documento(numdoc);
        bl.setDt_rilascio_documento(isdate);
        bl.setDt_scadenza_documento(exdate);
        bl.setRilasciato_da_documento(dociss);
        bl.setLuogo_rilascio_documento(docplace);
        bl.setEmail(email);
        bl.setTelefono(phone);
        bl.setDt_blocco(dt1.toString("yyyy-MM-dd"));
        bl.setFg_annullato(status);
        bl.setTimestamp(dt1.toString("yyyy-MM-dd HH:mm:ss"));
        bl.setText(txtmsg);
        Db_Master dbm = new Db_Master();
        boolean es = dbm.edit_Blacklist(bl, user);
        dbm.closeDB();
        if (es) {
            redirect(request, response, "tb_edit_blackM.jsp?bl_code=" + bl_code + "&esito=ok");
        } else {
            redirect(request, response, "tb_edit_blackM.jsp?bl_code=" + bl_code + "&esito=koins");
        }
    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void edit_Natoff(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String user = (String) request.getSession().getAttribute("us_cod");
        if (user == null) {
            user = "9999";
        }

        //NUOVO 22/07
        String kyc_abfreqdaily = request.getParameter("kyc_abfreqdaily");
        String kyc_abfreqweek = request.getParameter("kyc_abfreqweek");
        String kyc_abfreqmont = request.getParameter("kyc_abfreqmont");

        String kyc_abvolmont = request.getParameter("kyc_abvolmont");
        String kyc_abvolqua = request.getParameter("kyc_abvolqua");

        String kyc_rogvol = request.getParameter("kyc_rogvol");
        if (is_IT) {
            String[] kyc_rogcountry = request.getParameterValues("kyc_rogcountry");

            String[] kyc_okountry = request.getParameterValues("kyc_okountry");

            String kyc_fa = kyc_abfreqdaily + ";" + kyc_abfreqweek + ";" + kyc_abfreqmont + ";";
            String kyc_va = formatDoubleforMysql(kyc_abvolmont) + ";" + formatDoubleforMysql(kyc_abvolqua) + ";";
            String kyc_vro = formatDoubleforMysql(kyc_rogvol);
            String kyc_listro = join(";", asList(kyc_rogcountry));
            String kyc_listok = join(";", asList(kyc_okountry));

            Db_Master dbm0 = new Db_Master();
            dbm0.updatePath("kyc_fa", kyc_fa, user);
            dbm0.updatePath("kyc_va", kyc_va, user);
            dbm0.updatePath("kyc_vro", kyc_vro, user);
            dbm0.updatePath("kyc_listro", kyc_listro, user);
            dbm0.updatePath("kyc_listok", kyc_listok, user);
            dbm0.closeDB();

        }

//         printRequest(request);
//        if (true) {
//            return;
//        }
        Db_Master dbm1 = new Db_Master();
        String path = dbm1.getPath("temp");
        dbm1.closeDB();

        String cod = request.getParameter("cod");
        String descr = request.getParameter("descr");
        String addr = request.getParameter("addr");
        String city = request.getParameter("city");
        String zipc = request.getParameter("zipc");
        String piva = request.getParameter("piva");
        String regi = request.getParameter("regi");
        String rea = request.getParameter("rea");
        String change = request.getParameter("change");
        if (change == null) {
            change = "*";
        } else if (change.trim().equalsIgnoreCase("on")) {
            change = "/";
        } else {
            change = "*";
        }

        String ie1 = request.getParameter("ie1");
        if (ie1 == null) {
            ie1 = "0";
        } else if (ie1.trim().equalsIgnoreCase("on")) {
            ie1 = "1";
        } else {
            ie1 = "0";
        }
        String ed1 = request.getParameter("ed1");
        if (ed1 == null) {
            ed1 = "0";
        } else if (ed1.trim().equalsIgnoreCase("on")) {
            ed1 = "1";
        } else {
            ed1 = "0";
        }
        String ch1 = request.getParameter("ie1");
        if (ch1 == null) {
            ch1 = "0";
        } else if (ch1.trim().equalsIgnoreCase("on")) {
            ch1 = "1";
        } else {
            ch1 = "0";
        }

        String mf1 = request.getParameter("mf1");
        if (mf1 == null) {
            mf1 = "0";
        } else if (mf1.trim().equalsIgnoreCase("on")) {
            mf1 = "1";
        } else {
            mf1 = "0";
        }

//
        String decim = request.getParameter("decim");
        String url = request.getParameter("url");
        String bbdur = request.getParameter("bbdur");
        String txtrecp1 = request.getParameter("txtrecp1");
        String txtrecp2 = request.getParameter("txtrecp2");
        String txtrecp3 = request.getParameter("txtrecp3");
        String txtrecp4 = request.getParameter("txtrecp4");
        String txtthr1 = request.getParameter("txtthr1");
        String txtthr2 = request.getParameter("txtthr2");
        String minutes = request.getParameter("minutes");
        String kyc_mesi = request.getParameter("kyc_mesi");
        String kyc_soglia = request.getParameter("kyc_soglia");
        String risk_days = request.getParameter("risk_days");
        String risk_ntr = request.getParameter("risk_ntr");
        String risk_soglia = request.getParameter("risk_soglia");
//
        Office of = new Office();
        of.setCod(cod);
        of.setDe_office(descr);
        of.setAdd_city(city);
        of.setAdd_cap(zipc);
        of.setAdd_via(addr);
        of.setVat(piva);
        of.setReg_impr(regi);
        of.setRea(rea);
        of.setChangetype(change);
        of.setDecimalround(formatDoubleforMysql(decim));
        of.setUrl_bl(url);

        of.setTxt_ricev_1(base64HTML(path, txtrecp1));
        of.setTxt_ricev_2(base64HTML(path, txtrecp2));
        of.setTxt_alert_threshold_1(base64HTML(path, txtthr1));
        of.setTxt_alert_threshold_2(base64HTML(path, txtthr2));
        of.setTxt_ricev_bb(base64HTML(path, txtrecp3));
        of.setTxt_nopep(base64HTML(path, txtrecp4));

        of.setScadenza_bb(bbdur);
        of.setMinutes(minutes);
        of.setKyc_mesi(kyc_mesi);
        of.setKyc_soglia(formatDoubleforMysql(kyc_soglia));
        of.setRisk_days(risk_days);
        of.setRisk_ntr(risk_ntr);
        of.setRisk_soglia(formatDoubleforMysql(risk_soglia));

        Db_Master dbm = new Db_Master();
        boolean es = dbm.edit_Natoff(of, user);
        dbm.updatePath("IE", ie1, user);
        dbm.updatePath("ED", ed1, user);
        dbm.updatePath("CH", ch1, user);
        dbm.updatePath("FI", mf1, user);
        dbm.closeDB();
//
        if (es) {
            redirect(request, response, "tb_natoff.jsp?esito=ok");
        } else {
            redirect(request, response, "tb_natoff.jsp?esito=koins");
        }
    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void insert_new_Ratera(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String user = (String) request.getSession().getAttribute("us_cod");
        if (user == null) {
            user = "9999";
        }

        Db_Master dbm = new Db_Master();
        String listbranch = request.getParameter("listbranch");
        String filiale = dbm.getCodLocal(true)[0];
        String bra[];
        if (listbranch != null) {
            bra = parseArrayValues(listbranch, ";");
        } else {
            bra = parseArrayValues(dbm.list_branchcode_ENABLED());
        }

        String dt = new DateTime().toString(patternsqldate);

        String dt_val = request.getParameter("dt_val");
        if (dt_val == null) {
            dt_val = "";
        }

        String kind = request.getParameter("kind0");
        String min = request.getParameter("min");
        String max = request.getParameter("max");
        String buy = request.getParameter("buy");
        String sell = request.getParameter("sell");

        String es = dbm.insert_new_Ratera(filiale, kind, formatDoubleforMysql(min),
                formatDoubleforMysql(max), buy, sell, user, dt_val, dt);

        if (es.equals("0")) {
            for (String bra1 : bra) {
                dbm.insert_new_Ratera(bra1, kind, formatDoubleforMysql(min), formatDoubleforMysql(max), buy, sell, user, dt_val, dt);
            }
        }

        dbm.closeDB();
        switch (es) {
            case "0":
                redirect(request, response, "tb_edit_raterange.jsp?esito=ok");
                break;
            case "1":
                redirect(request, response, "tb_edit_raterange.jsp?esito=kodup");
                break;
            default:
                redirect(request, response, "tb_edit_raterange.jsp?esito=koins");
                break;
        }
    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void edit_Ratera(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String user = (String) request.getSession().getAttribute("us_cod");
        if (user == null) {
            user = "9999";
        }
        Db_Master dbm = new Db_Master();
        String filiale = dbm.getCodLocal(true)[0];
        String listbranch = request.getParameter("listbranch");
        String bra[];
        if (listbranch != null) {
            bra = parseArrayValues(listbranch, ";");
        } else {
            bra = parseArrayValues(dbm.list_branchcode_ENABLED());
        }

        String dt = new DateTime().toString(patternsqldate);

        String dt_val = request.getParameter("dt_val");
        if (dt_val == null) {
            dt_val = "";
        }

        String ra_code = request.getParameter("ra_code");
        String min = request.getParameter("min");
        String max = request.getParameter("max");
        String buy = request.getParameter("buy");
        String sell = request.getParameter("sell");
        String min_old = request.getParameter("min_old");
        String status = request.getParameter("status");
        if (status == null) {
            status = "1";
        } else if (status.trim().equalsIgnoreCase("on")) {
            status = "0";
        } else {
            status = "1";
        }
        boolean es = dbm.edit_Ratera(filiale, ra_code, formatDoubleforMysql(min),
                formatDoubleforMysql(max), buy, sell, status, min_old, user, dt_val, dt);

        if (es) {
            for (String bra1 : bra) {
                dbm.edit_Ratera(bra1, ra_code, formatDoubleforMysql(min), formatDoubleforMysql(max), buy, sell, status, min_old, user, dt_val, dt);
            }
        }

        dbm.closeDB();

        if (es) {
            redirect(request, response, "tb_edit_raterange.jsp?ra_code=" + ra_code + "&ra_min=" + formatDoubleforMysql(min) + "&esito=ok");
        } else {
            redirect(request, response, "tb_edit_raterange.jsp?ra_code=" + ra_code + "&ra_min=" + formatDoubleforMysql(min) + "&esito=koins");
        }
    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void insert_new_Groupbr(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

//        Utility.printRequest(request);
//        if(true)
//            return;
//        
        String user = (String) request.getSession().getAttribute("us_cod");
        if (user == null) {
            user = "9999";
        }
        String gr_code = request.getParameter("gr_code").toUpperCase();
        String typegr = request.getParameter("typegr");
        String descr = getStringUTF8(request.getParameter("descr")).toUpperCase();
//        String table = Engine.formatTableGroup(typegr);
        String table = "branchgroup";
        Db_Master dbm = new Db_Master();
        String es = dbm.insert_new_Groupbr(table, gr_code, typegr, descr, user);
        dbm.closeDB();
        switch (es) {
            case "0":
                redirect(request, response, "tb_edit_groupbr.jsp?esito=ok");
                break;
            case "1":
                redirect(request, response, "tb_edit_groupbr.jsp?esito=kodup");
                break;
            default:
                redirect(request, response, "tb_edit_groupbr.jsp?esito=koins");
                break;
        }
    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void edit_Groupbr(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

//        Utility.printRequest(request);
//        if(true)
//            return;
        String user = (String) request.getSession().getAttribute("us_cod");
        if (user == null) {
            user = "9999";
        }
        String gr_code = request.getParameter("gr_code");
        String typegr = request.getParameter("typegr");
        String descr = getStringUTF8(request.getParameter("descr")).toUpperCase();
        String table = "branchgroup";

        Db_Master dbm = new Db_Master();
        boolean es = dbm.edit_Groupbr2(table, gr_code, typegr, descr, user);
        dbm.closeDB();
        if (es) {
            redirect(request, response, "tb_edit_groupbr.jsp?gr_code=" + gr_code + "&esito=ok");
        } else {
            redirect(request, response, "tb_edit_groupbr.jsp?gr_code=" + gr_code + "&esito=koins");
        }
    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void insert_new_User(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

//        Utility.printRequest(request);
//        if (true) {
//            return;
//        }
        String user = (String) request.getSession().getAttribute("us_cod");
        if (user == null) {
            user = "9999";
        }

        String us_code = request.getParameter("us_code");
        while (us_code.length() < 4) {
            us_code = "0" + us_code;
        }

        String surname = request.getParameter("surname");
        String name = request.getParameter("name");
        String typeuser = request.getParameter("typeuser");
        String valid = request.getParameter("valid");
        String conto = request.getParameter("conto");
        String mail = request.getParameter("mail");

        Db_Master dbm = new Db_Master();
        String filiale = dbm.getCodLocal(true)[0];
        Users us = new Users();
        us.setFiliale(filiale);
        us.setDe_nome(name);
        us.setDe_cognome(surname);
        us.setValidita(valid);
        us.setConto(conto);
        us.setEmail(mail);
        us.setFg_tipo(typeuser);
//        us.setCod(dbm.getIdnewUser());
        us.setCod(us_code);
        us.setUsername(generateUsername(us.getDe_nome(), us.getDe_cognome(), us.getCod()));
        us.setPwd(getMd5("9999999a"));
        String es = dbm.insert_new_User(us, user);
        dbm.closeDB();

        //ATLACODE 04/04
        if (es.equals("0")) {
            String atl_code = request.getParameter("atl_code");
            if (atl_code != null) {
                Db_Master db0 = new Db_Master();
                boolean es1 = db0.mod_codice_ATL(us_code, atl_code, "user_atl");
                db0.closeDB();
                if (es1) {
                    insertTR("W", user, "INSERITO/MODIFICATO CODICE ATLANTE USER " + us_code + " : " + atl_code);
                }
            }
        }

        if (es.equals("0")) {
            redirect(request, response, "tb_edit_users.jsp?esito=ok");
        } else {
            redirect(request, response, "tb_edit_users.jsp?esito=koins");
        }
    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void edit_user_KYC(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String user = (String) request.getSession().getAttribute("us_cod");
        if (user == null) {
            user = "9999";
        }

        String us_code = request.getParameter("us_code");
        String status = request.getParameter("status");

        String sql;
        Db_Master dbm = new Db_Master();
        if (status.equals("1")) {
            sql = "INSERT INTO user_sito VALUES ('" + us_code + "','-','1','" + dbm.getNow() + "')";
        } else {
            sql = "DELETE FROM user_sito WHERE username = '" + us_code + "'";
        }
        boolean es = true;
        try {
            dbm.getC().createStatement(TYPE_SCROLL_INSENSITIVE, CONCUR_UPDATABLE).execute(Utility.sanitize(sql));
            dbm.insertTR("I", user, sql);
        } catch (SQLException ex) {
            if (!ex.getMessage().toLowerCase().contains("duplicate")) {
                es = false;
                dbm.insertTR("E", "System", ex.getMessage());
            }
        }
        dbm.closeDB();
        redirect(request, response, "fancy_message.jsp?esito=" + valueOf(es));
    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void edit_User(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

//        Utility.printRequest(request);
//        if (true) {
//            return;
//        }
        String user = (String) request.getSession().getAttribute("us_cod");
        if (user == null) {
            user = "9999";
        }

        String us_code = request.getParameter("us_code");
        String surname = request.getParameter("surname");
        String name = request.getParameter("name");
        String typeuser = request.getParameter("typeuser");
        String valid = request.getParameter("valid");
        String conto = request.getParameter("conto");
        String mail = request.getParameter("mail");
        String status = request.getParameter("status");
        Db_Master dbm = new Db_Master();
        Users us = new Users();
        String filiale = dbm.getCodLocal(true)[0];
        us.setFiliale(filiale);
        us.setDe_nome(name);
        us.setDe_cognome(surname);
        us.setValidita(valid);
        us.setConto(conto);
        us.setEmail(mail);
        us.setFg_tipo(typeuser);
        us.setCod(us_code);
        us.setFg_stato(status);
        boolean es = dbm.edit_User(us, user);
        dbm.closeDB();

        //ATLACODE 04/04
        if (es) {
            String atl_code = request.getParameter("atl_code");
            if (atl_code != null) {
                Db_Master db0 = new Db_Master();
                boolean es1 = db0.mod_codice_ATL(us_code, atl_code, "user_atl");
                db0.closeDB();
                if (es1) {
                    insertTR("W", user, "INSERITO/MODIFICATO CODICE ATLANTE USER " + us_code + " : " + atl_code);
                }
            }
        }

        if (es) {
            redirect(request, response, "tb_edit_users.jsp?us_code=" + us_code + "&esito=ok");
        } else {
            redirect(request, response, "tb_edit_users.jsp?us_code=" + us_code + "&esito=koins");
        }
    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void reset_Pswuser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String user = (String) request.getSession().getAttribute("us_cod");
        if (user == null) {
            user = "9999";
        }
        String us_code = request.getParameter("us_code");
        String newpass = request.getParameter("newpass");
        Db_Master dbm = new Db_Master();
        String filiale = dbm.getCodLocal(true)[0];
        boolean es = dbm.edit_Pswuser(filiale, us_code, newpass, user);
        dbm.closeDB();
        if (es) {
            redirect(request, response, "tb_edit_users.jsp?us_code=" + us_code + "&esito=okP");
        } else {
            redirect(request, response, "tb_edit_users.jsp?us_code=" + us_code + "&esito=koins");
        }
    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void insert_new_category(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        

        // 02/07
        String regi = request.getParameter("regi");
        if (regi == null) {
            regi = "0";
        } else if (regi.trim().equalsIgnoreCase("on")) {
            regi = "1";
        } else {
            regi = "0";
        }

        String department = "01";
        if (regi.equals("1")) {
            department = request.getParameter("department");
        }

//        
        Db_Master dbm = new Db_Master();
        String filiale = dbm.getCodLocal(true)[0];
        String listbranch = request.getParameter("listbranch");
        String bra[];
        if (listbranch != null) {
            bra = parseArrayValues(listbranch, ";");
        } else {
            bra = parseArrayValues(dbm.list_branchcode_ENABLED());
        }

        String dt = new DateTime().toString(patternsqldate);

        String dt_val = request.getParameter("dt_val");
        if (dt_val == null) {
            dt_val = "";
        }

        String user = (String) request.getSession().getAttribute("us_cod");
        if (user == null) {
            user = "9999";
        }
        String descr = getStringUTF8(request.getParameter("descr"));
        String kind_1 = request.getParameter("kind_1");
        String price = request.getParameter("price");
        String tfeevalue = request.getParameter("tfeevalue");

        String titype = request.getParameter("titype");
        if (titype == null) {
            titype = "0";
        } else if (titype.trim().equalsIgnoreCase("on")) {
            titype = "1";
        } else {
            titype = "0";
        }

        String maxti = request.getParameter("maxti");

        if (maxti == null || maxti.trim().equals("")) {
            maxti = "0";
        }

        String editti = request.getParameter("editti");
        if (editti == null) {
            editti = "0";
        } else if (editti.trim().equalsIgnoreCase("on")) {
            editti = "1";
        } else {
            editti = "0";
        }

        String esolv1 = request.getParameter("esolv1");
        String esolv2 = request.getParameter("esolv2");
        String headre = getStringUTF8(request.getParameter("headre"));
        String textre = getStringUTF8(request.getParameter("textre"));

        String corres = request.getParameter("corres");
        if (corres == null) {
            corres = "0";
        } else if (corres.trim().equalsIgnoreCase("on")) {
            corres = "1";
        } else {
            corres = "0";
        }
        String periva = request.getParameter("periva");
        String acode = request.getParameter("acode");

        String cod = request.getParameter("cod");

        if (!kind_1.equals("21")) { //    ticket
            tfeevalue = "0.00";
            maxti = "0";
            titype = "0";
            editti = "0";
        }

        if (kind_1.equals("7")) { //    internet point
            esolv1 = "";
            esolv2 = "";
            headre = "";
            textre = "";
        } else {
            corres = "0";
            periva = "0.00";
            acode = "-";
        }

        if (kind_1.equals("2")) {
            NC_vatcode vat1 = new NC_vatcode(cod);
            String accounting_1 = getRequestValue(request, "esolv1");
            if (regi.equals("0")) {
                vat1.setAccountingcode1(accounting_1);
                esolv1 = accounting_1;
                esolv2 = "";
            } else {
                String accounting_2 = getRequestValue(request, "esolv2");
                String vat_1 = getRequestValue(request, "v_esolv1");
                String vat_2 = getRequestValue(request, "v_esolv2");
                String price_1 = formatDoubleforMysql(getRequestValue(request, "am_esolv1"));
                String price_2 = formatDoubleforMysql(getRequestValue(request, "am_esolv2"));
                String dep_1 = getRequestValue(request, "department");
                String dep_2 = getRequestValue(request, "department2");
                vat1 = new NC_vatcode(cod, formatDoubleforMysql(price), accounting_1, vat_1, price_1, dep_1, accounting_2, vat_2, price_2, dep_2);
            }

            boolean es2 = dbm.update_NC_vatcode(vat1, "000", user, dt_val, dt);

            insertTR("W", user, "INSERIMENTO CATEGORIA " + cod + " - Esito VAT: " + es2);
        }

//        Utility.printRequest(request);
//        if (true) {
//            return;
//        }
        NC_category nc1 = new NC_category();
        nc1.setGruppo_nc(cod.toUpperCase());
        nc1.setConto_coge_01(esolv1);
        nc1.setConto_coge_02(esolv2);
        nc1.setDe_gruppo_nc(descr);
        nc1.setDe_riga(textre);
        nc1.setDe_scontrino(headre);
        nc1.setFg_tipo_transazione_nc(kind_1);
        nc1.setIp_prezzo_nc(formatDoubleforMysql(price));
        nc1.setMax_ticket(maxti);
        nc1.setTicket_fee(formatDoubleforMysql(tfeevalue));
        nc1.setTicket_fee_type(titype);
        nc1.setTicket_enabled(editti);
        nc1.setFiliale(filiale);
        nc1.setInt_code(acode);
        nc1.setInt_corrisp(corres);
        nc1.setInt_iva(formatDoubleforMysql(periva));
        nc1.setFg_registratore(regi);
        nc1.setDepartment(department);

        String es = dbm.insert_new_category(nc1, user, periva, cod);

        dbm.inser_department_NC(department, cod.toUpperCase(), user);

        if (es.equals("0")) {
            for (String bra1 : bra) {
                nc1 = new NC_category();
                nc1.setGruppo_nc(cod.toUpperCase());
                nc1.setConto_coge_01(esolv1);
                nc1.setConto_coge_02(esolv2);
                nc1.setDe_gruppo_nc(descr);
                nc1.setDe_riga(textre);
                nc1.setDe_scontrino(headre);
                nc1.setFg_tipo_transazione_nc(kind_1);
                nc1.setIp_prezzo_nc(formatDoubleforMysql(price));
                nc1.setMax_ticket(maxti);
                nc1.setTicket_fee(formatDoubleforMysql(tfeevalue));
                nc1.setTicket_fee_type(titype);
                nc1.setTicket_enabled(editti);
                nc1.setFiliale(bra1);
                nc1.setInt_code(acode);
                nc1.setInt_corrisp(corres);
                nc1.setInt_iva(formatDoubleforMysql(periva));
                nc1.setFg_registratore(regi);
                dbm.insert_new_category(nc1, user, dt_val, dt);
            }
        }

        dbm.closeDB();
        if (es.equals("0")) {
            redirect(request, response, "nc_edit_cat.jsp?esito=ok");
        } else {
            redirect(request, response, "nc_edit_cat.jsp?esito=koins" + es);
        }

    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void edit_category(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // 02/07
        String regi = request.getParameter("regi");
        if (regi == null) {
            regi = "0";
        } else if (regi.trim().equalsIgnoreCase("on")) {
            regi = "1";
        } else {
            regi = "0";
        }

        String department = "01";
        if (regi.equals("1")) {
            department = request.getParameter("department");
        }

        String user = (String) request.getSession().getAttribute("us_cod");
        if (user == null) {
            user = "9999";
        }
        Db_Master dbm = new Db_Master();
        String filiale = dbm.getCodLocal(true)[0];
        String listbranch = request.getParameter("listbranch");
        String bra[];
        if (listbranch != null) {
            bra = parseArrayValues(listbranch, ";");
        } else {
            bra = parseArrayValues(dbm.list_branchcode_ENABLED());
        }

        String dt = new DateTime().toString(patternsqldate);

        String dt_val = request.getParameter("dt_val");
        if (dt_val == null) {
            dt_val = "";
        }

        String descr = getStringUTF8(request.getParameter("descr"));
        String kind_1 = request.getParameter("kind0");
        String price = request.getParameter("price");
        String tfeevalue = request.getParameter("tfeevalue");

        String titype = request.getParameter("titype");
        if (titype == null) {
            titype = "0";
        } else if (titype.trim().equalsIgnoreCase("on")) {
            titype = "1";
        } else {
            titype = "0";
        }

        String status = request.getParameter("status");
        if (status == null) {
            status = "1";
        } else if (status.trim().equalsIgnoreCase("on")) {
            status = "0";
        } else {
            status = "1";
        }
        String maxti = request.getParameter("maxti");

        if (maxti == null || maxti.trim().equals("")) {
            maxti = "0";
        }

        String editti = request.getParameter("editti");
        if (editti == null) {
            editti = "0";
        } else if (editti.trim().equalsIgnoreCase("on")) {
            editti = "1";
        } else {
            editti = "0";
        }

        String esolv1 = request.getParameter("esolv1");
        String esolv2 = request.getParameter("esolv2");
        String headre = getStringUTF8(request.getParameter("headre"));
        String textre = getStringUTF8(request.getParameter("textre"));

        String corres = request.getParameter("corres");
        if (corres == null) {
            corres = "0";
        } else if (corres.trim().equalsIgnoreCase("on")) {
            corres = "1";
        } else {
            corres = "0";
        }

        String periva = request.getParameter("periva");
        String acode = request.getParameter("acode");

        if (kind_1.equals("7")) { //    internet point
            esolv1 = "";
            esolv2 = "";
            headre = "";
            textre = "";
        } else {
            corres = "0";
            periva = "0.00";
            acode = "-";
        }

        String cod = request.getParameter("cod");

        if (!kind_1.equals("21")) { //    ticket
            maxti = "0";
            titype = "0";
            editti = "0";
            tfeevalue = "0.00";
        }

        //NUOVO
        if (kind_1.equals("2")) {
            NC_vatcode vat1 = new NC_vatcode(cod);
            String accounting_1 = getRequestValue(request, "esolv1");
            if (regi.equals("0")) {
                vat1.setAccountingcode1(accounting_1);
                esolv1 = accounting_1;
                esolv2 = "";
            } else {
                String accounting_2 = getRequestValue(request, "esolv2");
                String vat_1 = getRequestValue(request, "v_esolv1");
                String vat_2 = getRequestValue(request, "v_esolv2");
                String price_1 = formatDoubleforMysql(getRequestValue(request, "am_esolv1"));
                String price_2 = formatDoubleforMysql(getRequestValue(request, "am_esolv2"));
                String dep_1 = getRequestValue(request, "department");
                String dep_2 = getRequestValue(request, "department2");
                vat1 = new NC_vatcode(cod, formatDoubleforMysql(price), accounting_1, vat_1, price_1, dep_1, accounting_2, vat_2, price_2, dep_2);
            }
            boolean es2 = dbm.update_NC_vatcode(vat1, "000", user, dt_val, dt);

            insertTR("W", user, "MODIFICA CATEGORIA " + cod + " - Esito VAT: " + es2);
        }

//        Utility.printRequest(request);
//        if (true) {
//            return;
//        }
        NC_category nc1 = new NC_category();
        nc1.setGruppo_nc(cod);
        nc1.setConto_coge_01(esolv1);
        nc1.setConto_coge_02(esolv2);
        nc1.setDe_gruppo_nc(descr);
        nc1.setDe_riga(textre);
        nc1.setDe_scontrino(headre);
        nc1.setFg_tipo_transazione_nc(kind_1);
        nc1.setIp_prezzo_nc(formatDoubleforMysql(price));
        nc1.setMax_ticket(maxti);
        nc1.setTicket_fee(formatDoubleforMysql(tfeevalue));
        nc1.setTicket_fee_type(titype);
        nc1.setTicket_enabled(editti);
        nc1.setFiliale(filiale);
        nc1.setAnnullato(status);
        nc1.setInt_code(acode);
        nc1.setInt_corrisp(corres);
        nc1.setInt_iva(formatDoubleforMysql(periva));
        nc1.setFg_registratore(regi);
        nc1.setDepartment(department);

        boolean es = dbm.edit_category(nc1, user, dt_val, dt);

        if (es) {
            for (String bra1 : bra) {
                nc1 = new NC_category();
                nc1.setGruppo_nc(cod);
                nc1.setConto_coge_01(esolv1);
                nc1.setConto_coge_02(esolv2);
                nc1.setDe_gruppo_nc(descr);
                nc1.setDe_riga(textre);
                nc1.setDe_scontrino(headre);
                nc1.setFg_tipo_transazione_nc(kind_1);
                nc1.setIp_prezzo_nc(formatDoubleforMysql(price));
                nc1.setMax_ticket(maxti);
                nc1.setTicket_fee(formatDoubleforMysql(tfeevalue));
                nc1.setTicket_fee_type(titype);
                nc1.setTicket_enabled(editti);
                nc1.setFiliale(bra1);
                nc1.setAnnullato(status);
                nc1.setInt_code(acode);
                nc1.setInt_corrisp(corres);
                nc1.setInt_iva(formatDoubleforMysql(periva));
                nc1.setFg_registratore(regi);
                nc1.setDepartment(department);
                dbm.edit_category(nc1, periva, dt_val, dt);
            }
        }

        dbm.closeDB();
        if (es) {
            redirect(request, response, "nc_edit_cat.jsp?esito=ok&nc_code=" + cod);
        } else {
            redirect(request, response, "nc_edit_cat.jsp?esito=koins&nc_code=" + cod);
        }

    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void insert_new_causal(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

//                Utility.printRequest(request);
//        if (true) {
//            return;
//        }
        String user = (String) request.getSession().getAttribute("us_cod");
        if (user == null) {
            user = "9999";
        }
        Db_Master db = new Db_Master();
        String filiale = db.getCodLocal(true)[0];
        String listbranch = request.getParameter("listbranch");
        String bra[];
        if (listbranch != null) {
            bra = parseArrayValues(listbranch, ";");
        } else {
            bra = parseArrayValues(db.list_branchcode_ENABLED());
        }

        String dt = new DateTime().toString(patternsqldate);

        String dt_val = request.getParameter("dt_val");
        if (dt_val == null) {
            dt_val = "";
        }

        String gruppo_nc = request.getParameter("gruppo_nc");
        String tipo_transazione_nc = request.getParameter("tipo_transazione_nc");
        String descrtype = request.getParameter("descrtype");
        String descr = getStringUTF8(request.getParameter("descr"));
        String price = request.getParameter("price");
        String bonus = request.getParameter("bonus");
        if (bonus == null) {
            bonus = "0";
        } else if (bonus.trim().equalsIgnoreCase("on")) {
            bonus = "1";
        } else {
            bonus = "0";
        }

        String dri = request.getParameter("dri");
        if (dri == null) {
            dri = "0";
        } else if (dri.trim().equalsIgnoreCase("on")) {
            dri = "1";
        } else {
            dri = "0";
        }

        String batch = request.getParameter("batch");
        if (batch == null) {
            batch = "0";
        } else if (batch.trim().equalsIgnoreCase("on")) {
            batch = "1";
        } else {
            batch = "0";
        }

        String print = request.getParameter("print");
        if (print == null) {
            print = "0";
        } else if (print.trim().equalsIgnoreCase("on")) {
            print = "1";
        } else {
            print = "0";
        }

        String tfeevalue = request.getParameter("tfeevalue");

        String titype = request.getParameter("titype");
        if (titype == null) {
            titype = "0";
        } else if (titype.trim().equalsIgnoreCase("on")) {
            titype = "1";
        } else {
            titype = "0";
        }

        String maxti = request.getParameter("maxti");
        if (maxti == null || maxti.trim().equals("")) {
            maxti = "0";
        }

        if (!tipo_transazione_nc.equals("21")) { //    ticket
            tfeevalue = "0.00";
            maxti = "0";
            titype = "0";
        }

        String paymat = request.getParameter("paymat");
        if (paymat == null) {
            paymat = "0";
        } else if (paymat.trim().equalsIgnoreCase("on")) {
            paymat = "1";
        } else {
            paymat = "0";
        }

        NC_causal nc1 = new NC_causal();

        nc1.setDe_causale_nc(descr);
        nc1.setFg_batch(batch);
        nc1.setFg_tipo_transazione_nc(tipo_transazione_nc);
        nc1.setFg_scontrino(print);
        nc1.setGruppo_nc(gruppo_nc);
        nc1.setIp_prezzo_nc(formatDoubleforMysql(price));
        nc1.setTicket_fee_type(titype);
        nc1.setMax_ticket(maxti);
        nc1.setTicket_fee(formatDoubleforMysql(tfeevalue));
        nc1.setNc_de(descrtype);
        nc1.setBonus(bonus);
        nc1.setFiliale(filiale);
        nc1.setData(db.getNow());
        nc1.setPaymat(paymat);
        nc1.setFg_in_out(db.getFg_inout_ncde(tipo_transazione_nc, descrtype));
        nc1.setAnnullato("0");
        nc1.setFg_gruppo_stampa("");
        nc1.setCodice_integr("-");
        nc1.setDocric(dri);
        boolean es;
        String nc_causal = db.insert_new_causal(nc1, user, dt_val, dt);
        if (nc_causal != null) {
            ArrayList<String[]> kind_payment = kind_payment();
            ArrayList<String[]> kind_payment_list = new ArrayList<>();
            for (int i = 0; i < kind_payment.size(); i++) {
                String pay = request.getParameter("status_" + kind_payment.get(i)[0]);
                if (pay == null) {
                    pay = "1";
                } else if (pay.trim().equalsIgnoreCase("on")) {
                    pay = "0";
                } else {
                    pay = "1";
                }

                String[] v = {nc1.getFiliale(), nc1.getGruppo_nc(),
                    nc_causal,
                    kind_payment.get(i)[0],
                    pay};
                kind_payment_list.add(v);

            }
            es = db.insert_nc_kind_payment(kind_payment_list, user);
        } else {
            es = false;
        }

        if (es) {
            for (String bra1 : bra) {
                if (!bra1.equals(filiale)) {
                    nc1 = new NC_causal();
                    nc1.setDe_causale_nc(descr);
                    nc1.setFg_batch(batch);
                    nc1.setFg_tipo_transazione_nc(tipo_transazione_nc);
                    nc1.setFg_scontrino(print);
                    nc1.setGruppo_nc(gruppo_nc);
                    nc1.setIp_prezzo_nc(formatDoubleforMysql(price));
                    nc1.setTicket_fee_type(titype);
                    nc1.setMax_ticket(maxti);
                    nc1.setTicket_fee(formatDoubleforMysql(tfeevalue));
                    nc1.setNc_de(descrtype);
                    nc1.setBonus(bonus);
                    nc1.setFiliale(bra1);
                    nc1.setData(db.getNow());
                    nc1.setPaymat(paymat);
                    nc1.setFg_in_out(db.getFg_inout_ncde(tipo_transazione_nc, descrtype));
                    nc1.setAnnullato("0");
                    nc1.setFg_gruppo_stampa("");
                    nc1.setCodice_integr("-");
                    nc1.setDocric(dri);
                    nc_causal = db.insert_new_causal(nc1, user, dt_val, dt);
                    if (nc_causal != null) {
                        ArrayList<String[]> kind_payment = kind_payment();
                        ArrayList<String[]> kind_payment_list = new ArrayList<>();
                        for (int i = 0; i < kind_payment.size(); i++) {
                            String pay = request.getParameter("status_" + kind_payment.get(i)[0]);
                            if (pay == null) {
                                pay = "1";
                            } else if (pay.trim().equalsIgnoreCase("on")) {
                                pay = "0";
                            } else {
                                pay = "1";
                            }

                            String[] v = {nc1.getFiliale(), nc1.getGruppo_nc(),
                                nc_causal,
                                kind_payment.get(i)[0],
                                pay};
                            kind_payment_list.add(v);

                        }
                        db.insert_nc_kind_payment(kind_payment_list, user);
                    }
                }
            }
        }

        db.closeDB();

        if (es) {
            redirect(request, response, "nc_edit_cas.jsp?esito=ok&nc_cat=" + gruppo_nc);
        } else {
            redirect(request, response, "nc_edit_cas.jsp?esito=koins&nc_cat=" + gruppo_nc);
        }
        /////////INSERT
    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void edit_causal(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

//        Utility.printRequest(request);
//        if (true) {
//            return;
//        }
        String user = (String) request.getSession().getAttribute("us_cod");
        if (user == null) {
            user = "9999";
        }
        Db_Master db = new Db_Master();
        String filiale = db.getCodLocal(true)[0];
        String listbranch = request.getParameter("listbranch");
        String bra[];
        if (listbranch != null) {
            bra = parseArrayValues(listbranch, ";");
        } else {
            bra = parseArrayValues(db.list_branchcode_ENABLED());
        }

        String dt = new DateTime().toString(patternsqldate);

        String dt_val = request.getParameter("dt_val");
        if (dt_val == null) {
            dt_val = "";
        }

        String gruppo_nc = request.getParameter("gruppo_nc");
        String cod = request.getParameter("cod");
        String tipo_transazione_nc = request.getParameter("tipo_transazione_nc");
        String descrtype = request.getParameter("descrtype");
        String descr = getStringUTF8(request.getParameter("descr"));
        String price = request.getParameter("price");
        String bonus = request.getParameter("bonus");
        if (bonus == null) {
            bonus = "0";
        } else if (bonus.trim().equalsIgnoreCase("on")) {
            bonus = "1";
        } else {
            bonus = "0";
        }

        String dri = request.getParameter("dri");
        if (dri == null) {
            dri = "0";
        } else if (dri.trim().equalsIgnoreCase("on")) {
            dri = "1";
        } else {
            dri = "0";
        }

        String batch = request.getParameter("batch");
        if (batch == null) {
            batch = "0";
        } else if (batch.trim().equalsIgnoreCase("on")) {
            batch = "1";
        } else {
            batch = "0";
        }

        String print = request.getParameter("print");
        if (print == null) {
            print = "0";
        } else if (print.trim().equalsIgnoreCase("on")) {
            print = "1";
        } else {
            print = "0";
        }

        String tfeevalue = request.getParameter("tfeevalue");

        String titype = request.getParameter("titype");
        if (titype == null) {
            titype = "0";
        } else if (titype.trim().equalsIgnoreCase("on")) {
            titype = "1";
        } else {
            titype = "0";
        }

        String maxti = request.getParameter("maxti");
        if (maxti == null || maxti.trim().equals("")) {
            maxti = "0";
        }

        if (!tipo_transazione_nc.equals("21")) { //    ticket
            tfeevalue = "0.00";
            maxti = "0";
            titype = "0";
        }

        String status = request.getParameter("status");
        if (status == null) {
            status = "1";
        } else if (status.trim().equalsIgnoreCase("on")) {
            status = "0";
        } else {
            status = "1";
        }

        String paymat = request.getParameter("paymat");
        if (paymat == null) {
            paymat = "0";
        } else if (paymat.trim().equalsIgnoreCase("on")) {
            paymat = "1";
        } else {
            paymat = "0";
        }

        NC_causal nc1 = new NC_causal();
        nc1.setCausale_nc(cod);
        nc1.setDe_causale_nc(descr);
        nc1.setFg_batch(batch);
        nc1.setFg_tipo_transazione_nc(tipo_transazione_nc);
        nc1.setFg_scontrino(print);
        nc1.setGruppo_nc(gruppo_nc);
        nc1.setIp_prezzo_nc(formatDoubleforMysql(price));
        nc1.setTicket_fee_type(titype);
        nc1.setMax_ticket(maxti);
        nc1.setTicket_fee(formatDoubleforMysql(tfeevalue));
        nc1.setNc_de(descrtype);
        nc1.setBonus(bonus);
        nc1.setFiliale(filiale);
        nc1.setData(db.getNow());
        nc1.setAnnullato(status);
        nc1.setPaymat(paymat);
        //altri
        nc1.setFg_in_out(db.getFg_inout_ncde(tipo_transazione_nc, descrtype));
        nc1.setFg_gruppo_stampa("");
        nc1.setCodice_integr("-");
        nc1.setDocric(dri);

        boolean es = db.edit_causal(nc1, user, dt_val, dt);

        ArrayList<String[]> nc_causal_payment = db.kind_payment();

        if (es) {
//            ArrayList<String[]> nc_causal_payment = db.nc_causal_payment(nc1.getFiliale(), nc1.getGruppo_nc(), nc1.getCausale_nc());
//            if (nc_causal_payment.isEmpty()) {
//                nc_causal_payment = db.kind_payment();
//            }

            ArrayList<String[]> kind_payment_list = new ArrayList<>();
            for (int i = 0; i < nc_causal_payment.size(); i++) {
                String pay = request.getParameter("status_" + nc_causal_payment.get(i)[0]);
                if (pay == null) {
                    pay = "1";
                } else if (pay.trim().equalsIgnoreCase("on")) {
                    pay = "0";
                } else {
                    pay = "1";
                }

                String[] v = {nc1.getFiliale(), nc1.getGruppo_nc(),
                    nc1.getCausale_nc(),
                    nc_causal_payment.get(i)[0],
                    pay};
                kind_payment_list.add(v);
            }
            es = db.insert_nc_kind_payment(kind_payment_list, user);
        }

        if (es) {
            for (String bra1 : bra) {
                if (!bra1.equals(filiale)) {
                    nc1 = new NC_causal();
                    nc1.setCausale_nc(cod);
                    nc1.setDe_causale_nc(descr);
                    nc1.setFg_batch(batch);
                    nc1.setFg_tipo_transazione_nc(tipo_transazione_nc);
                    nc1.setFg_scontrino(print);
                    nc1.setGruppo_nc(gruppo_nc);
                    nc1.setIp_prezzo_nc(formatDoubleforMysql(price));
                    nc1.setTicket_fee_type(titype);
                    nc1.setMax_ticket(maxti);
                    nc1.setTicket_fee(formatDoubleforMysql(tfeevalue));
                    nc1.setNc_de(descrtype);
                    nc1.setBonus(bonus);
                    nc1.setFiliale(bra1);
                    nc1.setData(db.getNow());
                    nc1.setAnnullato(status);
                    nc1.setPaymat(paymat);
                    //altri
                    nc1.setFg_in_out(db.getFg_inout_ncde(tipo_transazione_nc, descrtype));
                    nc1.setFg_gruppo_stampa("");
                    nc1.setCodice_integr("-");
                    nc1.setDocric(dri);
                    es = db.edit_causal(nc1, user, dt_val, dt);
                    if (es) {
//                        ArrayList<String[]> nc_causal_payment = db.nc_causal_payment(nc1.getFiliale(), nc1.getGruppo_nc(), nc1.getCausale_nc());
//                        if (nc_causal_payment.isEmpty()) {
//                            nc_causal_payment = db.kind_payment();
//                        }
ArrayList<String[]> kind_payment_list = new ArrayList<>();

for (int i = 0; i < nc_causal_payment.size(); i++) {
    String pay = request.getParameter("status_" + nc_causal_payment.get(i)[0]);
    if (pay == null) {
        pay = "1";
    } else if (pay.trim().equalsIgnoreCase("on")) {
        pay = "0";
    } else {
        pay = "1";
    }
    
    String[] v = {nc1.getFiliale(), nc1.getGruppo_nc(),
        nc1.getCausale_nc(),
        nc_causal_payment.get(i)[0],
        pay};
    kind_payment_list.add(v);
}
es = db.insert_nc_kind_payment(kind_payment_list, user);
                    }
                }
            }
        }

        db.closeDB();

        if (es) {
            redirect(request, response, "nc_edit_cas.jsp?esito=ok&nc_code=" + cod);
        } else {
            redirect(request, response, "nc_edit_cas.jsp?esito=koins&nc_code=" + cod);
        }

    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void delete_company_attach(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Db_Master db = new Db_Master();
        String cod = request.getParameter("cod");
        String co_cod = request.getParameter("co_code");
        db.delete_Company_attach(cod);
        db.closeDB();
        redirect(request, response, "tb_edit_company.jsp?co_code=" + co_cod);
    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void delete_agent(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Db_Master db = new Db_Master();
        String cod_ag = request.getParameter("cod_ag");
        String co_cod = request.getParameter("co_cod");
        Company ag = db.get_Agent(cod_ag);
        if (ag != null) {
            db.delete_Agent(ag);
        }
        db.closeDB();
        redirect(request, response, "tb_edit_company.jsp?co_code=" + co_cod);
    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void ins_comp(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String user = (String) request.getSession().getAttribute("us_cod");
        if (user == null) {
            user = "9999";
        }
        String msg = "0";

        String descr = getRequestValue(request, "descr");
        String vat = getRequestValue(request, "vat");
        String addr = getRequestValue(request, "addr");

        String cap = getRequestValue(request, "cap");
        String district = getRequestValue(request, "district");
        String city = getRequestValue(request, "city");
        String country = getRequestValue(request, "country");
        if (!country.equals(codnaz)) {
            cap = "";
            district = "";
            city = "";
        }

        String ag_surname = getRequestValue(request, "ag_surname");
        String ag_name = getRequestValue(request, "ag_name");
        String ag_sex = request.getParameter("ag_sex");
        if (ag_sex == null) {
            ag_sex = "F";
        } else {
            ag_sex = "M";
        }
        String ag_tax = getRequestValue(request, "ag_tax");
        String ag_addr = getRequestValue(request, "ag_addr");
        String ag_city = getRequestValue(request, "ag_city");
        String ag_cap = getRequestValue(request, "ag_cap");
        String ag_district = getRequestValue(request, "ag_district");
        String ag_country = getRequestValue(request, "ag_country");
        String ag_daob = getRequestValue(request, "ag_daob");
        String ag_ciob = getRequestValue(request, "ag_ciob");
        String ag_distrob = getRequestValue(request, "ag_distrob");
        String ag_docid = getRequestValue(request, "ag_docid");
        String ag_docnum = getRequestValue(request, "ag_docnum");
        String ag_issda = getRequestValue(request, "ag_issda");
        String ag_exda = getRequestValue(request, "ag_exda");
        String ag_issby = getRequestValue(request, "ag_issby");
        String ag_isspla = getRequestValue(request, "ag_isspla");

        if (!ag_country.equals(codnaz)) {
            ag_cap = "";
            ag_district = "";
            ag_city = "";
        }

        Db_Master db = new Db_Master();
        String filiale = db.getCodLocal(true)[0];
        String cod = db.getNextNgdCompany();
        Company co = new Company();
        co.setNdg(cod);
        co.setFiliale(filiale);
        co.setRagione_sociale(descr);
        co.setPaese_estero_residenza(country);
        co.setCab_comune(city);
        co.setCitta(city);
        co.setProvincia(district);
        co.setCap(cap);
        co.setCodice_fiscale(vat);
        co.setIndirizzo(addr);
        co.setFg_annullato("0");

        co = db.insert_Company(co, user);
        if (co != null) {
            while (!co.getNdg().equals(cod)) {
                co = db.insert_Company(co, user);
            }
            String codag = db.getNextNgdCompany();
            Company ag = new Company();
            ag.setNdg(codag);
            ag.setFiliale(filiale);
            ag.setNdg_rappresentante(co.getNdg());
            ag.setCognome(ag_surname);
            ag.setNome(ag_name);
            ag.setPaese_estero_residenza(ag_country);
            ag.setCab_comune(ag_city);
            ag.setProvincia(ag_district);
            ag.setIndirizzo(ag_addr);
            ag.setCap(ag_cap);
            ag.setCodice_fiscale(ag_tax);
            ag.setDt_nascita(ag_daob);
            ag.setComune_nascita(ag_ciob);
            ag.setTipo_documento(ag_docid);
            ag.setDt_rilascio(ag_issda);
            ag.setAutorita_rilascio(ag_issby);
            ag.setSesso(ag_sex);
            ag.setNumero_documento(ag_docnum);
            ag.setDt_scadenza(ag_exda);
            ag.setCod_provincia_nascita(ag_distrob);
            ag.setLuogo_rilascio_documento(ag_isspla);
            ag.setTipo_anagrafica("F");
            ag = db.insert_Agent(ag, user);
            if (ag != null) {
                while (!ag.getNdg().equals(codag)) {
                    ag = db.insert_Agent(ag, user);
                }

            } else {
                msg = "2";
            }
        } else {
            msg = "1";
        }
        db.closeDB();

        if (msg.equals("0")) {
            redirect(request, response, "tb_edit_company.jsp?esito=OK");
        } else {
            redirect(request, response, "tb_edit_company.jsp?esito=KO" + msg);
        }

    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void ins_agent(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String user = (String) request.getSession().getAttribute("us_cod");
        if (user == null) {
            user = "9999";
        }
        String msg = "0";
        String co_code = getRequestValue(request, "co_code");
        String ag_surname = getRequestValue(request, "ag_surname");
        String ag_name = getRequestValue(request, "ag_name");
        String ag_sex = request.getParameter("ag_sex");
        if (ag_sex == null) {
            ag_sex = "F";
        } else {
            ag_sex = "M";
        }
        String ag_tax = getRequestValue(request, "ag_tax");
        String ag_addr = getRequestValue(request, "ag_addr");
        String ag_city = getRequestValue(request, "ag_city");
        String ag_cap = getRequestValue(request, "ag_cap");
        String ag_district = getRequestValue(request, "ag_district");
        String ag_country = getRequestValue(request, "ag_country");
        String ag_daob = getRequestValue(request, "ag_daob");
        String ag_ciob = getRequestValue(request, "ag_ciob");
        String ag_distrob = getRequestValue(request, "ag_distrob");
        String ag_docid = getRequestValue(request, "ag_docid");
        String ag_docnum = getRequestValue(request, "ag_docnum");
        String ag_issda = getRequestValue(request, "ag_issda");
        String ag_exda = getRequestValue(request, "ag_exda");
        String ag_issby = getRequestValue(request, "ag_issby");
        String ag_isspla = getRequestValue(request, "ag_isspla");
        Db_Master db = new Db_Master();
        String filiale = db.getCodLocal(true)[0];
        String codag = db.getNextNgdCompany();
        Company ag = new Company();
        ag.setNdg(codag);
        ag.setFiliale(filiale);
        ag.setNdg_rappresentante(co_code);
        ag.setCognome(ag_surname);
        ag.setNome(ag_name);
        ag.setPaese_estero_residenza(ag_country);
        ag.setCab_comune(ag_city);
        ag.setProvincia(ag_district);
        ag.setIndirizzo(ag_addr);
        ag.setCap(ag_cap);
        ag.setCodice_fiscale(ag_tax);
        ag.setDt_nascita(formatStringtoStringDate(ag_daob, "dd/MM/yyyy", "yyyy-MM-dd"));
        ag.setComune_nascita(ag_ciob);
        ag.setTipo_documento(ag_docid);
        ag.setDt_rilascio(formatStringtoStringDate(ag_issda, "dd/MM/yyyy", "yyyy-MM-dd"));
        ag.setAutorita_rilascio(ag_issby);
        ag.setSesso(ag_sex);
        ag.setNumero_documento(ag_docnum);
        ag.setDt_scadenza(formatStringtoStringDate(ag_exda, "dd/MM/yyyy", "yyyy-MM-dd"));
        ag.setCod_provincia_nascita(ag_distrob);
        ag.setLuogo_rilascio_documento(ag_isspla);
        ag.setTipo_anagrafica("F");
        ag.setRagione_sociale((ag_surname + " " + ag_name).toUpperCase());
        db.insert_Agent(ag, user);
//        if (ag == null) {
//            while (!ag.getNdg().equals(codag)) {
//                ag = db.insert_Agent(ag, user);
//            }
//        }
        db.closeDB();
        if (msg.equals("0")) {
            redirect(request, response, "oper_es.jsp?esito=ins_agent_ok1");
        } else {
            redirect(request, response, "tb_edit_agent.jsp?co_code=" + co_code + "&esito=KO" + msg);
        }

    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void edit_comp(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//            
//        
//        Utility.printRequest(request);
//        
//        if(true)return;

        String user = (String) request.getSession().getAttribute("us_cod");
        if (user == null) {
            user = "9999";
        }
        Db_Master db = new Db_Master();
//        String pathtemp = "F:\\util\\temp\\";
        String pathtemp = db.getPath("temp");
        boolean ok = true;
        String msg = "0";
        DateTime now = new DateTime();
        String day = now.toString(patterndir);
        String upl = now.toString(patternsqldate);

        String com_code = "";
        String descr = "";
        String vat = "";
        String addr = "";
        String district = "";
        String cap = "";
        String city = "";
        String country = "";
        String status = "1";

        ArrayList<String[]> agentvalue = new ArrayList<>();
        ArrayList<Company_attach> listFile = new ArrayList<>();

        boolean isMultipart = isMultipartContent(request);
        if (isMultipart) {
            try {
                String extacc = db.getConf("ext.upl.filecompany");
                FileItemFactory factory = new DiskFileItemFactory();
                ServletFileUpload upload = new ServletFileUpload(factory);
                List items = upload.parseRequest(request);
                Iterator iterator = items.iterator();
                while (iterator.hasNext()) {
                    FileItem item = (FileItem) iterator.next();
                    if (item.isFormField()) {//CAMPI
                        if (item.getFieldName().equals("com_code")) {
                            com_code = item.getString().trim();
                        } else if (item.getFieldName().equals("descr")) {
                            descr = item.getString().trim();
                        } else if (item.getFieldName().equals("vat")) {
                            vat = item.getString().trim();
                        } else if (item.getFieldName().equals("addr")) {
                            addr = item.getString().trim();
                        } else if (item.getFieldName().equals("cap")) {
                            cap = item.getString().trim();
                        } else if (item.getFieldName().equals("district")) {
                            district = item.getString().trim();
                        } else if (item.getFieldName().equals("city")) {
                            city = item.getString().trim();
                        } else if (item.getFieldName().equals("status")) {
                            status = "0";
                        } else if (item.getFieldName().equals("country-1")) {
                            country = item.getString().trim();
                        } else if (item.getFieldName().startsWith("ag_")) {
                            StringTokenizer st = new StringTokenizer(item.getFieldName(), "_");
                            if (st.countTokens() == 3) {
                                st.nextToken();
                                String cod = st.nextToken();
                                String namefield = st.nextToken();
                                String valufield = item.getString().trim();
                                String[] ap = {cod, namefield, valufield};
                                agentvalue.add(ap);
                            }
                        }
                    } else {//FILE
                        String fileName = item.getName();
                        if (fileName != null) {
                            String fieldName = item.getFieldName().replaceAll("file1_", "").trim();
                            String codtemp = generaId(20);
                            File pathdir = new File(pathtemp + day);
                            pathdir.mkdirs();
                            String estensione = getExtension(fileName);
                            if (extacc.toLowerCase().contains(estensione.toLowerCase())) {
                                String name = codtemp + "_" + day + "." + estensione;
                                File filetemp = new File(pathdir + separator + name);
                                try {
                                    item.write(filetemp);
                                    String base64 = getStringBase64(filetemp);
                                    Company_attach ca = new Company_attach(codtemp, fieldName, fileName, base64, "0", upl);
                                    listFile.add(ca);
                                } catch (Exception ex) {
                                    insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
                                    msg = "1";
                                    ok = false;
                                }
                            } else {
                                msg = "1";
                                ok = false;
                            }
                        }
                    }
                }
            } catch (FileUploadException ex) {
                insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
            }

        }

        if (ok) {
            Company co = db.get_Company(com_code);
            if (co != null) {
                co.setRagione_sociale(descr);
                co.setCodice_fiscale(vat);
                co.setIndirizzo(addr);
                co.setPaese_estero_residenza(country);
                if (!country.equals(codnaz)) {
                    cap = "";
                    district = "";
                    city = "";
                }
                co.setCap(cap);
                co.setProvincia(district);
                co.setCab_comune(city);

                co.setFg_annullato(status);
                boolean es1 = db.edit_Company(co, user);
                if (es1) {
                    if (agentvalue.size() > 0) {
                        ArrayList<Company> listAgentOriginal = get_Agent_company(co.getNdg());
                        for (int i = 0; i < listAgentOriginal.size(); i++) {
                            Company ag = listAgentOriginal.get(i);
                            for (int j = 0; j < agentvalue.size(); j++) {
                                String[] val = agentvalue.get(j);
                                if (ag.getNdg().equals(val[0])) {
                                    if (val[1].equalsIgnoreCase("surname")) {
                                        ag.setCognome(val[2]);
                                    } else if (val[1].equalsIgnoreCase("name")) {
                                        ag.setNome(val[2]);
                                    } else if (val[1].equalsIgnoreCase("tax")) {
                                        ag.setCodice_fiscale(val[2]);
                                    } else if (val[1].equalsIgnoreCase("addr")) {
                                        ag.setIndirizzo(val[2]);
                                    } else if (val[1].equalsIgnoreCase("cap")) {
                                        ag.setCap(val[2]);
                                    } else if (val[1].equalsIgnoreCase("district")) {
                                        ag.setProvincia(val[2]);
                                    } else if (val[1].equalsIgnoreCase("city")) {
                                        ag.setCab_comune(val[2]);
                                    } else if (val[1].equalsIgnoreCase("country")) {
                                        ag.setPaese_estero_residenza(val[2]);
                                    } else if (val[1].equalsIgnoreCase("daofby")) {
                                        ag.setDt_nascita(formatStringtoStringDate(val[2], "dd/MM/yyyy", "yyyy-MM-dd"));
                                    } else if (val[1].equalsIgnoreCase("ciofby")) {
                                        ag.setComune_nascita(val[2]);
                                    } else if (val[1].equalsIgnoreCase("districtpob")) {
                                        ag.setCod_provincia_nascita(val[2]);
                                    } else if (val[1].equalsIgnoreCase("docid")) {
                                        ag.setTipo_documento(val[2]);
                                    } else if (val[1].equalsIgnoreCase("numdocid")) {
                                        ag.setNumero_documento(val[2]);
                                    } else if (val[1].equalsIgnoreCase("isdate")) {
                                        ag.setDt_rilascio(formatStringtoStringDate(val[2], "dd/MM/yyyy", "yyyy-MM-dd"));
                                    } else if (val[1].equalsIgnoreCase("exdate")) {
                                        ag.setDt_scadenza(formatStringtoStringDate(val[2], "dd/MM/yyyy", "yyyy-MM-dd"));
                                    } else if (val[1].equalsIgnoreCase("issby")) {
                                        ag.setAutorita_rilascio(val[2]);
                                    } else if (val[1].equalsIgnoreCase("issplace")) {
                                        ag.setLuogo_rilascio_documento(val[2]);
                                    } else if (val[1].equalsIgnoreCase("sex")) {
                                        ag.setSesso("M");
                                    }
                                }
                            }

                            if (!ag.getPaese_estero_residenza().equals(codnaz)) {
                                ag.setCap("");
                                ag.setProvincia("");
                                ag.setCab_comune("");
                            }
                            if (ag.getSesso() == null) {
                                ag.setSesso("F");
                            }

                            boolean es2 = db.edit_Agent(ag, user);
                            if (!es2) {
                                msg = "4";
                                break;
                            }
                        }
                    }

                    if (msg.equals("0")) {
                        for (int i = 0; i < listFile.size(); i++) {
                            Company_attach caa = listFile.get(i);
                            db.insert_Company_attach(caa, user);
                        }
                    }
                } else {
                    msg = "3";
                }

            } else {
                msg = "1";
            }

        } else {
            msg = "5";
        }
        db.closeDB();

        if (msg.equals("0")) {
            redirect(request, response, "tb_edit_company.jsp?co_code=" + com_code + "&esito=OK");
        } else {
            redirect(request, response, "tb_edit_company.jsp?co_code=" + com_code + "&esito=KO" + msg);
        }

    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void edit_booking(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String cod = request.getParameter("cod");
        String total = formatDoubleforMysql(request.getParameter("total"));
        String branch = request.getParameter("branch");
        String branch_or = request.getParameter("branch_or");
        String status = request.getParameter("status");
        String status_or = request.getParameter("status_or");

        if (status.equals(status_or)) {
            redirect(request, response, "web_tran_edit.jsp?cod=" + cod + "&esito=war");
        } else {
            Db_Master db = new Db_Master();
            boolean es = db.edit_prenotazione(cod, total, branch, branch_or, status);
            db.closeDB();
            if (es) {
                if (status.equals("2")) {
                    Db_Master db1 = new Db_Master();
                    String maildest = db1.get_mail_branch(branch);
                    Branch bbr = db1.get_branch(branch);
                    String dataritiro = formatStringtoStringDate(db1.get_prenot(cod).getDt_ritiro(), patternsql, patternnormdate_filter);
                    db1.closeDB();
                    String ogg = "Mac2.0 - New Online Reservation";
                    String txt = htmlMailPrenot();
                    txt = replace(txt, "@filialenome", bbr.getDe_branch().toUpperCase());
                    txt = replace(txt, "@dataprenot", dataritiro);
                    try {
                        sendMailHtmlNOBCC(maildest, ogg, txt);
                    } catch (Exception e) {
                    }
                }
                redirect(request, response, "web_tran_edit.jsp?cod=" + cod + "&esito=ok");
            } else {
                redirect(request, response, "web_tran_edit.jsp?cod=" + cod + "&esito=koins");
            }
        }

//        Db_Master db = new Db_Master();
//        boolean es = db.edit_prenot(cod, total, dt_ritiro, branch, status);
//        db.closeDB();
//        if (es) {
//            if (status.equals("3")) {
//                String[] s1 = Sito.cambia_stato_prenot(bookid, "cancel");
//            } else if (status.equals("2")) {
//                String[] s1 = Sito.cambia_stato_prenot(bookid, "success");
//            } else if (status.equals("4")) {
//                String[] s1 = Sito.cambia_stato_prenot(bookid, "reject");
//            }
//            redirect(request, response, "web_tran_edit.jsp?cod=" + cod + "&esito=ok");
//        } else {
//            redirect(request, response, "web_tran_edit.jsp?cod=" + cod + "&esito=koins");
//        }
    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void ins_vatcode(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String user = (String) request.getSession().getAttribute("us_cod");
        if (user == null) {
            user = "9999";
        }

        String va_code = request.getParameter("va_code");
        String descr = request.getParameter("descr");
        String esol = request.getParameter("esol");
        VATcode vt = new VATcode();

        vt.setCodice(va_code);
        vt.setDescrizione(descr);
        vt.setAliquota(formatDoubleforMysql(esol));

        boolean es = true;
        Db_Master db = new Db_Master();
        ArrayList<VATcode> array_va = db.li_vat(null);
        for (int i = 0; i < array_va.size(); i++) {
            if (array_va.get(i).getCodice().equals(va_code)) {
                es = false;
            }
        }
        if (es) {
            es = db.ins_vat(vt, user);
            try {
                sleep(1000);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        }
        db.closeDB();
        if (es) {
            redirect(request, response, "tb_edit_vatcode.jsp?esito=ok");
        } else {
            redirect(request, response, "tb_edit_vatcode.jsp?esito=koins");
        }
    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void edit_vatcode(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String user = (String) request.getSession().getAttribute("us_cod");
        if (user == null) {
            user = "9999";
        }
        String va_id = request.getParameter("va_id");
        String va_code = request.getParameter("va_code");
        String descr = request.getParameter("descr");
        String esol = request.getParameter("esol");
        String status = request.getParameter("status");
        if (status == null) {
            status = "1";
        } else if (status.trim().equalsIgnoreCase("on")) {
            status = "0";
        } else {
            status = "1";
        }
        String timestamp = new DateTime().toString(patternsqldate);

        VATcode vt = new VATcode(va_id, va_code, descr, formatDoubleforMysql(esol), status, timestamp);
        boolean es = true;
        Db_Master db = new Db_Master();

        ArrayList<VATcode> array_va = db.li_vat(null);
        for (int i = 0; i < array_va.size(); i++) {
            if (array_va.get(i).getCodice().equals(va_code)) {
                if (!array_va.get(i).getId().equals(va_id)) {
                    es = false;
                    break;
                }
            }
        }
        if (es) {
            es = db.edit_vat(vt, user);
            try {
                sleep(1000);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        }
        db.closeDB();
        if (es) {
            redirect(request, response, "tb_edit_vatcode.jsp?esito=ok&va_code=" + va_id);
        } else {
            redirect(request, response, "tb_edit_vatcode.jsp?esito=koins&va_code=" + va_id);
        }
    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void ins_unlock(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String user = (String) request.getSession().getAttribute("us_cod");
        if (user == null) {
            user = "9999";
        }
        int numb = parseInt(request.getParameter("numb"));
        String dest = request.getParameter("dest");

        Db_Master db = new Db_Master();
        Users us = db.get_user(dest);
        String pathtemp = db.getPath("temp");

        String listcod = db.create_codici_sblocco(numb, user);
        String timestamp = new DateTime().toString(patternnormdate_f);
        Codici_sblocco_file csf = new Codici_sblocco_file();
        csf.setUser(user);
        csf.setDest(us.getCod() + " - " + us.getUsername());
        csf.setNumcod(request.getParameter("numb"));
        String filepdf = new Receipt().print_code_unlock(pathtemp, listcod, timestamp, us.getCod() + " - " + us.getUsername());
        String xlsx = new Excel().print_code_unlock(pathtemp, listcod, timestamp, us.getCod() + " - " + us.getUsername());
        csf.setPdf(filepdf);
        csf.setExcel(xlsx);
        csf.setListcod(listcod);
        boolean es = db.insert_file_codici_sblocco(csf);
        db.closeDB();
        response.getWriter().print(es);

        if (es) {
            redirect(request, response, "tb_edit_unlock.jsp?esito=ok");
        } else {
            redirect(request, response, "tb_edit_unlock.jsp?esito=koins");
        }

    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void edit_city(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String user = (String) request.getSession().getAttribute("us_cod");
        if (user == null) {
            user = "9999";
        }
        String cit_code = request.getParameter("cit_code");
        String descr = request.getParameter("descr");

        Db_Master db = new Db_Master();
        boolean es = db.edit_city(cit_code, descr, "-", user);
        db.closeDB();
        if (es) {
            redirect(request, response, "tb_edit_city.jsp?esito=ok&cit_code=" + cit_code);
        } else {
            redirect(request, response, "tb_edit_city.jsp?esito=koins&cit_code=" + cit_code);
        }
    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void del_tr_doc(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

//        Utility.printRequest(request);
//        if(true)
//            return;
        String codtr_del = request.getParameter("codtr_del");
        String coddoc_del = request.getParameter("coddoc_del");
        Db_Master db = new Db_Master();
        boolean es = db.delete_doc_transaction(codtr_del, coddoc_del);
        db.closeDB();

//        String start = "transaction_ok_mod.jsp";
//        if (Constant.signoffline) {
//            start = "transaction_ok_mod_new.jsp";
//        }
        if (es) {

            redirect(request, response, getLink_last(Utility.sanitize(codtr_del)));
        } else {
            redirect(request, response, getLink_last(Utility.sanitize(codtr_del)) + "&esito=ko");
        }

    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void ins_city(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String user = (String) request.getSession().getAttribute("us_cod");
        if (user == null) {
            user = "9999";
        }
        String cit_code = request.getParameter("cit_code");
        String descr = request.getParameter("descr");

        Db_Master db = new Db_Master();
        boolean es = db.ins_city(cit_code, descr, "-", user);
        db.closeDB();
        if (es) {
            redirect(request, response, "tb_edit_city.jsp?esito=ok");
        } else {
            redirect(request, response, "tb_edit_city.jsp?esito=koins");
        }

    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void edit_r1_buy(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String user = (String) request.getSession().getAttribute("us_cod");
        if (user == null) {
            user = "9999";
        }
        Db_Master db = new Db_Master();
        boolean es = true;
        for (int i = 1; i < 7 && es; i++) {
            String idr = request.getParameter("idr_" + i);
            String r1 = formatDoubleforMysql(request.getParameter("r1_" + i));
            String r2 = formatDoubleforMysql(request.getParameter("r2_" + i));
            es = db.upd_fasce_cashier_perf(idr, r1, r2, user);
        }
        db.closeDB();

        if (es) {
            redirect(request, response, "tb_cashierperf.jsp?esito=ok");
        } else {
            redirect(request, response, "tb_cashierperf.jsp?esito=ko");
        }

    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void edit_accode(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String user = (String) request.getSession().getAttribute("us_cod");
        if (user == null) {
            user = "9999";
        }
        String cod = request.getParameter("cod");
        String descr = request.getParameter("descr");
        String acc = request.getParameter("acc");
        Db_Master db = new Db_Master();
        boolean es = db.edit_accode(cod, descr, acc, user);
        db.closeDB();

        if (es) {
            redirect(request, response, "tb_edit_esolv.jsp?esito=ok&es_code=" + cod);
        } else {
            redirect(request, response, "tb_edit_esolv.jsp?esito=ko&es_code=" + cod);
        }

    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void edit_r1_sell(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String user = (String) request.getSession().getAttribute("us_cod");
        if (user == null) {
            user = "9999";
        }
        Db_Master db = new Db_Master();
        boolean es = true;
        for (int i = 7; i < 13 && es; i++) {
            String idr = request.getParameter("idr_" + i);
            String r1 = formatDoubleforMysql(request.getParameter("r1_" + i));
            String r2 = formatDoubleforMysql(request.getParameter("r2_" + i));
            es = db.upd_fasce_cashier_perf(idr, r1, r2, user);
        }
        db.closeDB();

        if (es) {
            redirect(request, response, "tb_cashierperf.jsp?esito=ok");
        } else {
            redirect(request, response, "tb_cashierperf.jsp?esito=ko");
        }
    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void edit_branchbudget(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String user = (String) request.getSession().getAttribute("us_cod");
        if (user == null) {
            user = "9999";
        }

        String gr_code = request.getParameter("gr_code");
        String monthyear = formatStringtoStringDate_null(request.getParameter("monthyear"), pattermonthnorm, patternmonthsql);
        String branch = request.getParameter("branch");
        String budg = formatDoubleforMysql(request.getParameter("budg"));
        String budgon = formatDoubleforMysql(request.getParameter("budgon"));

        boolean es = false;

        if (monthyear != null) {
            String cod = "B" + generaId(19);
            Branchbudget bb = new Branchbudget(gr_code, monthyear, branch, budg, budgon, "-", "-", "-", "-", "-", "-");
            Db_Master db1 = new Db_Master();
            es = db1.update_Branchbudget(bb, user);
            db1.closeDB();
        }

        if (es) {
            redirect(request, response, "tb_edit_branchbudget.jsp?esito=ok&gr_code=" + gr_code);
        } else {
            redirect(request, response, "tb_edit_branchbudget.jsp?esito=ko&gr_code=" + gr_code);
        }

    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void ins_branchbudget(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        Utility.printRequest(request);
        String user = (String) request.getSession().getAttribute("us_cod");
        if (user == null) {
            user = "9999";
        }

        String gr_code = "B" + generaId(19);
        String monthyear = formatStringtoStringDate_null(request.getParameter("monthyear"), pattermonthnorm, patternmonthsql);
        String branch = request.getParameter("branch");
        String budg = formatDoubleforMysql(request.getParameter("budg"));
        String budgon = formatDoubleforMysql(request.getParameter("budgon"));

        boolean es = false;

        if (monthyear != null) {
            Branchbudget bb = new Branchbudget(gr_code, monthyear, branch, budg, budgon, "-", "-", "-", "-", "-", "-");
            Db_Master db1 = new Db_Master();
            es = db1.ins_Branchbudget(bb, user);
            db1.closeDB();
        }

        if (es) {
            redirect(request, response, "tb_edit_branchbudget.jsp?esito=ok&gr_code=" + gr_code);
        } else {
            redirect(request, response, "tb_edit_branchbudget.jsp?esito=ko&gr_code=" + gr_code);
        }

    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void edit_client(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String user = (String) request.getSession().getAttribute("us_cod");
        if (user == null) {
            user = "9999";
        }

//        Utility.printRequest(request);
        String tr_cod = request.getParameter("tr_cod");
        String cl_cod = request.getParameter("cl_cod");

        Ch_transaction tra = query_transaction_ch(tr_cod);
        Client clOR = query_Client_transaction(tr_cod, cl_cod);

        boolean es = false;

        if (tra == null || clOR == null) {
        } else {
            String codice_mod = generaId(75);
            String heavy_surname = request.getParameter("heavy_surname");
            String heavy_sex = request.getParameter("heavy_sex");
            String heavy_name = request.getParameter("heavy_name");
            String heavy_codfisc = request.getParameter("heavy_codfisc");
            String heavy_country = request.getParameter("heavy_country");
            String heavy_city = request.getParameter("heavy_city");
            String heavy_pepI = request.getParameter("heavy_pepI");
            String heavy_address = request.getParameter("heavy_address");
            String heavy_zipcode = request.getParameter("heavy_zipcode");
            String heavy_district = request.getParameter("heavy_district");
            String heavy_pob_city = request.getParameter("heavy_pob_city");
            String heavy_pob_district = request.getParameter("heavy_pob_district");
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

            Client cl = new Client();
            cl.setCode(cl_cod);
            cl.setCognome(heavy_surname);
            cl.setNome(heavy_name);
            cl.setSesso(heavy_sex);
            cl.setCodfisc(heavy_codfisc);
            cl.setNazione(heavy_country);
            cl.setCitta(heavy_city);
            cl.setIndirizzo(heavy_address);
            cl.setCap(heavy_zipcode);
            cl.setProvincia(heavy_district);
            cl.setCitta_nascita(heavy_pob_city);
            cl.setProvincia_nascita(heavy_pob_district);
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
            cl.setPep(heavy_pepI);

            cl.setPerc_buy(clOR.getPerc_buy());
            cl.setPerc_sell(clOR.getPerc_sell());
            cl.setTimestamp(clOR.getTimestamp());

            Db_Master db1 = new Db_Master();
            String datemod = db1.getNow();

            clOR.setCodicemodifica(codice_mod);
            clOR.setTipomodifica("O");
            clOR.setUsermodifica(user);
            clOR.setDatemodifica(datemod);
            cl.setCodicemodifica(codice_mod);
            cl.setTipomodifica("M");
            cl.setUsermodifica(user);
            cl.setDatemodifica(datemod);
            String dt_val = formatStringtoStringDate(datemod, patternsqldate, patternnormdate);
            es = db1.insert_kyc_modify_client(clOR, cl, tr_cod);
            db1.closeDB();
            sleeping(1000);
            Db_Master db2 = new Db_Master();
            Client kyc_modified_cod = db2.kyc_modified_cod(codice_mod);
            db2.closeDB();
            Iterator it = kyc_modified_cod.getModifiche().entrySet().iterator();
            if (it.hasNext()) {
                while (it.hasNext()) {
                    Map.Entry pair = (Map.Entry) it.next();
                    String campo = pair.getKey().toString();
                    String[] valori = (String[]) pair.getValue();
                    it.remove();

                    String update1 = "UPDATE anagrafica_client SET " + campo + " = \"" + valori[1] + "\" WHERE code ='" + cl_cod + "'";
                    String update2 = "UPDATE ch_transaction_client SET " + campo + " = \"" + valori[1] + "\" WHERE codtr ='" + tr_cod + "'";

                    Db_Master db3 = new Db_Master();
                    if (db3.getC() != null) {
                        try {
                            db3.getC().createStatement(TYPE_SCROLL_INSENSITIVE, CONCUR_UPDATABLE).execute(Utility.sanitize(update1));
                            db3.insert_aggiornamenti_mod(new Aggiornamenti_mod(
                                    generaId(50), tra.getFiliale(), dt_val, "0",
                                    "ST", update1, user, datemod));
                            db3.getC().createStatement(TYPE_SCROLL_INSENSITIVE, CONCUR_UPDATABLE).execute(Utility.sanitize(update2));
                            db3.insert_aggiornamenti_mod(new Aggiornamenti_mod(
                                    generaId(50), tra.getFiliale(), dt_val, "0",
                                    "ST", update2, user, datemod));
                        } catch (SQLException ex) {
                            insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
                            es = false;
                        }
                    }
                    db3.closeDB();
                }
            } else {
                es = false;
            }
        }
        if (es) {
            redirect(request, response, "page_ok.html");
        } else {
            redirect(request, response, "kyc_mod.jsp?cod=" + tr_cod);
        }
    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void edit_treshold(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        Utility.printRequest(request);
        String user = (String) request.getSession().getAttribute("us_cod");
        if (user == null) {
            user = "9999";
        }

        Enumeration<String> parameterNames = request.getParameterNames();

        Db_Master db1 = new Db_Master();
        while (parameterNames.hasMoreElements()) {
            String paramName = parameterNames.nextElement();
            if (!paramName.equals("type")) {
                String id = remove(paramName, "_perc_value");
                String percent = formatDoubleforMysql(getRequestValue(request, paramName));
                db1.edit_selectlevelrate(id, percent, user);
            }
        }
        db1.closeDB();

        redirect(request, response, "threshold_edit.jsp?esito=ok");

    }

    protected void editpaymode(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

//        Utility.printRequest(request);
        String user = (String) request.getSession().getAttribute("us_cod");
        if (user == null) {
            user = "9999";
        }
        String cod = request.getParameter("idtrdel");
        String idopen = request.getParameter("idopen");
        Db_Master db1 = new Db_Master();
        boolean activefr = false;
        ArrayList<Till> array_till = db1.list_till_status("O", user);
        for (int j = 0; j < array_till.size(); j++) {
            if (array_till.get(j).getId_opcl().equals(idopen)) {
                activefr = true;
                break;
            }
        }

        String valutalocale = db1.get_local_currency()[0];
        String dateOP = db1.getNow();
        db1.closeDB();

        if (!activefr) {
            redirect(request, response, "fancy_editpaym.jsp?esito=KOT&code=" + cod);
        } else {
            boolean mod = false;

            String tipotr = request.getParameter("tipotr");
            String cc_number = request.getParameter("cc_number");

            Ch_transaction tra = query_transaction_ch(cod);
            switch (tipotr) {
                case "B":
                    ArrayList<Ch_transaction_value> val = query_transaction_value(cod);
                    if (tra != null) {
                        for (int i = 0; i < 5; i++) {
                            String old_pos = request.getParameter("oldpos" + i);
                            if (old_pos != null) {
                                String new_pos = request.getParameter("newpos" + i);
                                String notes = request.getParameter("notes" + i);
                                if (!old_pos.equals(new_pos)) {
                                    String numeroriga = valueOf(i + 1);
                                    Db_Master db10 = new Db_Master();
                                    boolean esA = db10.update_modpay_CH_B(cod, new_pos, numeroriga, cc_number, user);
                                    boolean esB = db10.insert_modpay(cod, notes, "04", "04", old_pos, new_pos, user);
                                    Real_oc_pos t1 = new Real_oc_pos();
                                    t1.setFiliale(tra.getFiliale());
                                    t1.setCod_oc(tra.getId_open_till());
                                    t1.setValuta(val.get(i).getValuta());
                                    t1.setKind(val.get(i).getSupporto());
                                    t1.setCarta_credito(old_pos);
                                    t1.setData(dateOP);
                                    String or1 = db10.getField_real_oc_pos(t1, "ip_quantity", "0");
                                    String or2 = db10.getField_real_oc_pos(t1, "ip_value", "0.00");
                                    t1.setIp_quantity(valueOf(parseIntR(or1) - 1));
                                    t1.setIp_value(roundDoubleandFormat((fd(or2) - fd(val.get(i).getQuantita())), 2));
                                    boolean esC = db10.update_real_oc_pos(t1);
                                    Real_oc_pos t2 = new Real_oc_pos();
                                    t2.setFiliale(tra.getFiliale());
                                    t2.setCod_oc(tra.getId_open_till());
                                    t2.setValuta(val.get(i).getValuta());
                                    t2.setKind(val.get(i).getSupporto());
                                    t2.setCarta_credito(new_pos);
                                    t2.setData(dateOP);
                                    String or3 = db10.getField_real_oc_pos(t2, "ip_quantity", "0");
                                    String or4 = db10.getField_real_oc_pos(t2, "ip_value", "0.00");
                                    t2.setIp_quantity(valueOf(parseIntR(or3) + 1));
                                    t2.setIp_value(roundDoubleandFormat((fd(or4) + fd(val.get(i).getQuantita())), 2));
                                    boolean esD = db10.update_real_oc_pos(t2);
                                    db10.closeDB();
                                    mod = esA && esB && esC && esD;
                                }
                            }
                        }
                    }
                    break;
                case "S":
                    String old_kind = request.getParameter("old_kind");
                    String new_kind = request.getParameter("new_kind");
                    String old_pos = request.getParameter("old_pos");
                    String new_pos = request.getParameter("new_pos");
                    String old_ban = request.getParameter("old_ban");
                    String new_ban = request.getParameter("new_ban");
                    String notes = request.getParameter("notes");
                    String old_ps1 = "-", new_ps1 = "-";
                    if (old_kind.equals("06")) {
                        old_ps1 = old_pos;
                    } else if (old_kind.equals("08")) {
                        old_ps1 = old_ban;
                    }   if (tra != null) {
                        boolean err = false;
                        switch (new_kind) {
                            case "01":
                                break;
                            case "08":
                                if (!old_ban.equals(new_ban)) {
                                    old_ps1 = old_ban;
                                    new_ps1 = new_ban;
                                } else {
                                    err = true;
                                }       break;
                            default:
                                if (!old_pos.equals(new_pos)) {
                                    old_ps1 = old_pos;
                                    new_ps1 = new_pos;
                                } else {
                                    err = true;
                                }       break;
                        }
                        if (!new_kind.equals("06")) {
                            cc_number = "-";
                        }   if (!err) {
                            Db_Master db1A = new Db_Master();
                            boolean esA = db1A.update_modpay_CH_S(cod, new_kind, new_ps1, cc_number, user);
                            boolean esB = db1A.insert_modpay(cod, notes, old_kind, new_kind, old_ps1, new_ps1, user);
                            db1A.closeDB();
                            mod = esA && esB;
                        }   if (mod) {
                            if (old_kind.equals("01")) {
                                Real_oc_change t1 = new Real_oc_change();
                                t1.setFiliale(tra.getFiliale());
                                t1.setCod_oc(tra.getId_open_till());
                                t1.setValuta(valutalocale);
                                t1.setKind(old_kind);
                                t1.setNum_kind_op("0");
                                t1.setData(dateOP);
                                Db_Master db6 = new Db_Master();
                                String or1 = db6.getField_real_oc_change(t1, "value_op", "0.00");
                                t1.setValue_op(roundDoubleandFormat((fd(or1) - fd(tra.getPay())), 2));
                                mod = db6.update_real_oc_change(t1);
                                db6.closeDB();
                            } else {
                                Real_oc_pos t1 = new Real_oc_pos();
                                t1.setFiliale(tra.getFiliale());
                                t1.setCod_oc(tra.getId_open_till());
                                t1.setValuta(valutalocale);
                                t1.setKind(old_kind);
                                t1.setCarta_credito(old_ps1);
                                t1.setData(dateOP);
                                Db_Master db10 = new Db_Master();
                                String or1 = db10.getField_real_oc_pos(t1, "ip_quantity", "0");
                                String or2 = db10.getField_real_oc_pos(t1, "ip_value", "0.00");
                                t1.setIp_quantity(valueOf(parseInt(or1) - 1));
                                t1.setIp_value(roundDoubleandFormat((fd(or2) - fd(tra.getPay())), 2));
                                mod = db10.update_real_oc_pos(t1);
                                db10.closeDB();
                            }
                        }
                        if (mod) {
                            if (new_kind.equals("01")) {
                                Real_oc_change t1 = new Real_oc_change();
                                t1.setFiliale(tra.getFiliale());
                                t1.setCod_oc(tra.getId_open_till());
                                t1.setValuta(valutalocale);
                                t1.setKind(new_kind);
                                t1.setNum_kind_op("0");
                                t1.setData(dateOP);
                                Db_Master db6 = new Db_Master();
                                String old1 = db6.getField_real_oc_change(t1, "value_op", "0.00");
                                t1.setValue_op(roundDoubleandFormat((fd(old1) + fd(tra.getPay())), 2));
                                mod = db6.update_real_oc_change(t1);
                                db6.closeDB();
                            } else {
                                Real_oc_pos t1 = new Real_oc_pos();
                                t1.setFiliale(tra.getFiliale());
                                t1.setCod_oc(tra.getId_open_till());
                                t1.setValuta(valutalocale);
                                t1.setKind(new_kind);
                                t1.setCarta_credito(new_ps1);
                                t1.setData(dateOP);
                                Db_Master db10 = new Db_Master();
                                String or1 = db10.getField_real_oc_pos(t1, "ip_quantity", "0");
                                String or2 = db10.getField_real_oc_pos(t1, "ip_value", "0.00");
                                t1.setIp_quantity(valueOf(parseInt(or1) + 1));
                                t1.setIp_value(roundDoubleandFormat((fd(or2) + fd(tra.getPay())), 2));
                                mod = db10.update_real_oc_pos(t1);
                                db10.closeDB();
                            }
                        }
                        if (mod) {
                            if (old_kind.equals("01")) {

                                Stock st1 = new Stock();
                                st1.setCodice("ST" + generaId(48));
                                st1.setFiliale(tra.getFiliale());
                                st1.setTipo("CH");
                                st1.setTill(tra.getTill());
                                st1.setIdoperation(tra.getCod());
                                st1.setCodiceopenclose(tra.getId_open_till());
                                st1.setTipostock("CH");
                                st1.setCod_value(valutalocale);
                                st1.setKind(old_kind);
                                st1.setTotal(roundDoubleandFormat(-fd(tra.getPay()), 2));
                                st1.setRate("1.00000000");
                                st1.setControval(roundDoubleandFormat(-fd(tra.getPay()), 2));
                                st1.setUser(tra.getUser());
                                st1.setDate(dateOP);
                                st1.setId_op(tra.getFiliale() + tra.getId());
                                
                                Db_Master db01 = new Db_Master();
                                mod = db01.insertStock(st1);
                                db01.closeDB();

                                //REMOVE STOCK-REPORT
                                Db_Master db11 = new Db_Master();
                                db11.delete_trans_stockreport(cod, tra.getId_open_till(), valutalocale, old_kind);
                                db11.closeDB();
                                
                            }

                            if (new_kind.equals("01")) {
                                Stock st1 = new Stock();
                                st1.setCodice("ST" + generaId(48));
                                st1.setFiliale(tra.getFiliale());
                                st1.setTipo("CH");
                                st1.setTill(tra.getTill());
                                st1.setIdoperation(tra.getCod());
                                st1.setCodiceopenclose(tra.getId_open_till());
                                st1.setTipostock("CH");
                                st1.setCod_value(valutalocale);
                                st1.setKind(new_kind);
                                st1.setTotal(roundDoubleandFormat(fd(tra.getPay()), 2));
                                st1.setRate("1.00000000");
                                st1.setControval(roundDoubleandFormat(fd(tra.getPay()), 2));
                                st1.setUser(tra.getUser());
                                st1.setDate(dateOP);
                                st1.setId_op(tra.getFiliale() + tra.getId());
                                Db_Master db01 = new Db_Master();
                                mod = db01.insertStock(st1);
                                //INSERT STOCK-REPORT
                                Stock_report sr = db01.get_Stock_report(tra.getId_open_till(), valutalocale, new_kind, "CH", tra.getFiliale(), tra.getTill());
                                String codsr = tra.getFiliale() + generaId(47);
                                sr.setCodtr(cod);
                                sr.setCodice(codsr);
                                sr.setTotal(roundDoubleandFormat(fd(tra.getPay()), 2));
                                sr.setSpread("1.0000");
                                sr.setData(dateOP);
                                sr.setQuantity("0");
                                sr.setUser(user);
                                db01.insert_Stockreport(sr);
                                db01.closeDB();

                            }
                        }
                    }
                    break;
                case "NC":
                    NC_transaction nc = get_NC_transaction(cod);
                    if (nc != null) {
                        if (nc.getFg_inout().equals("1") || nc.getFg_inout().equals("3")) {
                            old_kind = request.getParameter("old_kind");
                            new_kind = request.getParameter("new_kind");
                            old_pos = request.getParameter("old_pos");
                            new_pos = request.getParameter("new_pos");
                            old_ban = request.getParameter("old_ban");
                            new_ban = request.getParameter("new_ban");
                            notes = request.getParameter("notes");
                            old_ps1 = "-";
                            new_ps1 = "-";
                            if (old_kind.equals("06")) {
                                old_ps1 = old_pos;
                            } else if (old_kind.equals("08")) {
                                old_ps1 = old_ban;
                            }   boolean err = false;
                            switch (new_kind) {
                                case "01":
                                    break;
                                case "08":
                                    if (!old_ban.equals(new_ban)) {
                                        old_ps1 = old_ban;
                                        new_ps1 = new_ban;
                                    } else {
                                        err = true;
                                    }       break;
                                default:
                                    if (!old_pos.equals(new_pos)) {
                                        old_ps1 = old_pos;
                                        new_ps1 = new_pos;
                                    } else {
                                        err = true;
                                    }       break;
                            }
                            if (!err) {
                                Db_Master db1A = new Db_Master();
                                boolean esA = db1A.update_modpay_NC(cod, new_kind, new_ps1, cc_number, user);
                                boolean esB = db1A.insert_modpay(cod, notes, old_kind, new_kind, old_ps1, new_ps1, user);
                                db1A.closeDB();
                                
                                mod = esA && esB;
                            }   if (mod) {
                                if (old_kind.equals("01")) {
                                    Real_oc_change t1 = new Real_oc_change();
                                    t1.setFiliale(nc.getFiliale());
                                    t1.setCod_oc(nc.getId_open_till());
                                    t1.setValuta(valutalocale);
                                    t1.setKind(old_kind);
                                    t1.setNum_kind_op("0");
                                    t1.setData(dateOP);
                                    Db_Master db6 = new Db_Master();
                                    String or1 = db6.getField_real_oc_change(t1, "value_op", "0.00");
                                    t1.setValue_op(roundDoubleandFormat((fd(or1) - fd(nc.getTotal())), 2));
                                    mod = db6.update_real_oc_change(t1);
                                    db6.closeDB();
                                } else {
                                    Real_oc_pos t1 = new Real_oc_pos();
                                    t1.setFiliale(nc.getFiliale());
                                    t1.setCod_oc(nc.getId_open_till());
                                    t1.setValuta(valutalocale);
                                    t1.setKind(old_kind);
                                    t1.setCarta_credito(old_ps1);
                                    t1.setData(dateOP);
                                    Db_Master db10 = new Db_Master();
                                    String or1 = db10.getField_real_oc_pos(t1, "ip_quantity", "0");
                                    String or2 = db10.getField_real_oc_pos(t1, "ip_value", "0.00");
                                    t1.setIp_quantity(valueOf(parseInt(or1) - 1));
                                    t1.setIp_value(roundDoubleandFormat((fd(or2) - fd(nc.getTotal())), 2));
                                    mod = db10.update_real_oc_pos(t1);
                                    db10.closeDB();
                                }
                            }
                            if (mod) {
                                if (new_kind.equals("01")) {
                                    Real_oc_change t1 = new Real_oc_change();
                                    t1.setFiliale(nc.getFiliale());
                                    t1.setCod_oc(nc.getId_open_till());
                                    t1.setValuta(valutalocale);
                                    t1.setKind(new_kind);
                                    t1.setNum_kind_op("0");
                                    t1.setData(dateOP);
                                    Db_Master db6 = new Db_Master();
                                    String old1 = db6.getField_real_oc_change(t1, "value_op", "0.00");
                                    t1.setValue_op(roundDoubleandFormat((fd(old1) + fd(nc.getTotal())), 2));
                                    mod = db6.update_real_oc_change(t1);
                                    db6.closeDB();
                                } else {
                                    Real_oc_pos t1 = new Real_oc_pos();
                                    t1.setFiliale(nc.getFiliale());
                                    t1.setCod_oc(nc.getId_open_till());
                                    t1.setValuta(valutalocale);
                                    t1.setKind(new_kind);
                                    t1.setCarta_credito(new_ps1);
                                    t1.setData(dateOP);
                                    Db_Master db10 = new Db_Master();
                                    String or1 = db10.getField_real_oc_pos(t1, "ip_quantity", "0");
                                    String or2 = db10.getField_real_oc_pos(t1, "ip_value", "0.00");
                                    t1.setIp_quantity(valueOf(parseInt(or1) + 1));
                                    t1.setIp_value(roundDoubleandFormat((fd(or2) + fd(nc.getTotal())), 2));
                                    mod = db10.update_real_oc_pos(t1);
                                    db10.closeDB();
                                }
                            }
                            if (mod) {
                                if (old_kind.equals("01")) {
                                    
                                    Stock st1 = new Stock();
                                    st1.setCodice("ST" + generaId(48));
                                    st1.setFiliale(nc.getFiliale());
                                    st1.setTipo("CH");
                                    st1.setTill(nc.getTill());
                                    st1.setIdoperation(nc.getCod());
                                    st1.setCodiceopenclose(nc.getId_open_till());
                                    st1.setTipostock("CH");
                                    st1.setCod_value(valutalocale);
                                    st1.setKind(old_kind);
                                    st1.setTotal(roundDoubleandFormat(-fd(nc.getTotal()), 2));
                                    st1.setRate("1.00000000");
                                    st1.setControval(roundDoubleandFormat(-fd(nc.getTotal()), 2));
                                    st1.setUser(nc.getUser());
                                    st1.setDate(dateOP);
                                    st1.setId_op(nc.getFiliale() + nc.getId());
                                    
                                    Db_Master db01 = new Db_Master();
                                    mod = db01.insertStock(st1);
                                    //REMOVE STOCK-REPORT
                                    db01.delete_trans_stockreport(cod, nc.getId_open_till(), valutalocale, old_kind);
                                    db01.closeDB();
                                    
                                }
                                
                                if (new_kind.equals("01")) {
                                    Stock st1 = new Stock();
                                    st1.setCodice("ST" + generaId(48));
                                    st1.setFiliale(nc.getFiliale());
                                    st1.setTipo("CH");
                                    st1.setTill(nc.getTill());
                                    st1.setIdoperation(nc.getCod());
                                    st1.setCodiceopenclose(nc.getId_open_till());
                                    st1.setTipostock("CH");
                                    st1.setCod_value(valutalocale);
                                    st1.setKind(new_kind);
                                    st1.setTotal(roundDoubleandFormat(fd(nc.getTotal()), 2));
                                    st1.setRate("1.00000000");
                                    st1.setControval(roundDoubleandFormat(fd(nc.getTotal()), 2));
                                    st1.setUser(nc.getUser());
                                    st1.setDate(dateOP);
                                    st1.setId_op(nc.getFiliale() + nc.getId());
                                    
                                    Db_Master db01 = new Db_Master();
                                    mod = db01.insertStock(st1);
                                    //INSERT STOCK-REPORT
                                    Stock_report sr = db01.get_Stock_report(nc.getId_open_till(), valutalocale, new_kind, "CH", nc.getFiliale(), nc.getTill());
                                    String codsr = nc.getFiliale() + generaId(47);
                                    sr.setCodtr(cod);
                                    sr.setCodice(codsr);
                                    sr.setTotal(roundDoubleandFormat(fd(nc.getTotal()), 2));
                                    sr.setSpread("1.0000");
                                    sr.setData(dateOP);
                                    sr.setQuantity("0");
                                    sr.setUser(user);
                                    db01.insert_Stockreport(sr);
                                    db01.closeDB();
                                    
                                }
                            }
                        }
                    }
                    break;
                default:
                    break;
            }
            if (mod) {
                redirect(request, response, "fancy_message.jsp?esito=true");
            } else {
                redirect(request, response, "fancy_editpaym.jsp?esito=KO&code=" + cod);
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
            } else {
                response.setContentType("text/html;charset=UTF-8");
//                request.setCharacterEncoding("UTF-8");
                String type = request.getParameter("type");
                switch (type) {
                    case "ins_newtill":
                        insert_new_Till(request, response);
                        break;
                    case "edit_till":
                        edit_Till(request, response);
                        break;
                    case "ins_newdistrict":
                        insert_new_District(request, response);
                        break;
                    case "edit_district":
                        edit_District(request, response);
                        break;
                    case "ins_newnation":
                        insert_new_Nation(request, response);
                        break;
                    case "edit_nation":
                        edit_Nation(request, response);
                        break;
                    case "ins_newdoctype":
                        insert_new_Doctype(request, response);
                        break;
                    case "edit_doctype":
                        edit_Doctype(request, response);
                        break;
                    case "ins_newlowcom":
                        insert_new_Lowcom(request, response);
                        break;
                    case "ins_newunratecom":
                        ins_newunratecom(request, response);
                        break;
                    case "edit_lowcom":
                        edit_Lowcom(request, response);
                        break;
                    case "edit_unrate":
                        edit_unrate(request, response);
                        break;
                    case "ins_newfixcom":
                        insert_new_Fixcomkind(request, response);
                        break;
                    case "edit_fixcom":
                        edit_Fixcomkind(request, response);
                        break;
                    case "ins_newcomfix":
                        insert_new_Comfix(request, response);
                        break;
                    case "edit_comfix":
                        edit_Comfix(request, response);
                        break;
                    case "ins_newkindtra":
                        insert_new_Kindtra(request, response);
                        break;
                    case "edit_kindtra":
                        edit_Kindtra(request, response);
                        break;
                    case "edit_showag":
                        edit_Showag(request, response);
                        break;
                    case "ins_agency":
                        insert_new_Agency(request, response);
                        break;
                    case "edit_agency":
                        edit_Agency(request, response);
                        break;
                    case "ins_intbook":
                        insert_new_Intbook(request, response);
                        break;
                    case "edit_intbook":
                        edit_Intbook(request, response);
                        break;
                    case "ins_credit":
                        insert_new_Credit(request, response);
                        break;
                    case "edit_credit":
                        edit_Credit(request, response);
                        break;
                    case "ins_figur":
                        insert_new_Figures(request, response);
                        break;
                    case "edit_figur":
                        edit_Figures(request, response);
                        break;
                    case "edit_curre":
                        edit_Currency(request, response);
                        break;
                    case "ins_bank":
                        insert_new_Bank(request, response);
                        break;
                    case "edit_bank":
                        edit_Bank(request, response);
                        break;
                    case "ins_branch":
                        insert_new_Branch(request, response);
                        break;
                    case "edit_branch":
                        edit_Branch(request, response);
                        break;
                    case "ins_black":
                        insert_new_Black(request, response);
                        break;
                    case "edit_black":
                        edit_Black(request, response);
                        break;
                    case "edit_natoff":
                        edit_Natoff(request, response);
                        break;
                    case "ins_ratera":
                        insert_new_Ratera(request, response);
                        break;
                    case "edit_ratera":
                        edit_Ratera(request, response);
                        break;
                    case "ins_groupbr":
                        insert_new_Groupbr(request, response);
                        break;
                    case "edit_groupbr":
                        edit_Groupbr(request, response);
                        break;
                    case "ins_user":
                        insert_new_User(request, response);
                        break;
                    case "edit_user_kyc":
                        edit_user_KYC(request, response);
                        break;
                    case "edit_user":
                        edit_User(request, response);
                        break;
                    case "reset_pswuser":
                        reset_Pswuser(request, response);
                        break;
                    case "new_category":
                        insert_new_category(request, response);
                        break;
                    case "edit_category":
                        edit_category(request, response);
                        break;
                    case "ins_causal":
                        insert_new_causal(request, response);
                        break;
                    case "edit_causal":
                        edit_causal(request, response);
                        break;
                    case "delete_company_attach":
                        delete_company_attach(request, response);
                        break;
                    case "delete_agent":
                        delete_agent(request, response);
                        break;
                    case "edit_comp":
                        edit_comp(request, response);
                        break;
                    case "ins_comp":
                        ins_comp(request, response);
                        break;
                    case "ins_agent":
                        ins_agent(request, response);
                        break;
                    case "edit_booking":
                        edit_booking(request, response);
                        break;
                    case "ins_unlock":
                        ins_unlock(request, response);
                        break;
                    case "edit_vatcode":
                        edit_vatcode(request, response);
                        break;
                    case "ins_vatcode":
                        ins_vatcode(request, response);
                        break;
                    case "edit_city":
                        edit_city(request, response);
                        break;
                    case "ins_city":
                        ins_city(request, response);
                        break;
                    case "del_tr_doc":
                        del_tr_doc(request, response);
                        break;
                    case "edit_r1_buy":
                        edit_r1_buy(request, response);
                        break;
                    case "edit_r1_sell":
                        edit_r1_sell(request, response);
                        break;
                    case "edit_accode":
                        edit_accode(request, response);
                        break;
                    case "edit_spread":
                        edit_spread(request, response);
//                } else if (type.equals("edit_macro")) {
//                    edit_macro(request, response);
                        break;
                    case "edit_branchbudget":
                        edit_branchbudget(request, response);
                        break;
                    case "edit_client":
                        edit_client(request, response);
                        break;
                //WEBSITE
                    case "ins_branchbudget":
                        ins_branchbudget(request, response);
                        break;
                    case "edit_wb_spread":
                        edit_wb_spread(request, response);
                        break;
                    case "edit_wb_comfix":
                        edit_wb_comfix(request, response);
                        break;
                    case "edit_wb_figur":
                        edit_wb_figur(request, response);
                        break;
                    case "edit_wb_error":
                        edit_wb_error(request, response);
                        break;
                    case "edit_wb_discount":
                        edit_wb_discount(request, response);
                        break;
                    case "ins_wb_discount":
                        ins_wb_discount(request, response);
                        break;
                    case "edit_wb_services":
                        edit_wb_services(request, response);
                        break;
                    case "ins_wb_services":
                        ins_wb_services(request, response);
                        break;
                    case "edit_wb_ratera":
                        edit_wb_ratera(request, response);
                        break;
                    case "edit_department":
                        edit_department(request, response);
                        break;
                    case "ins_department":
                        ins_department(request, response);
                        break;
                    case "editpaymode":
                        editpaymode(request, response);
                        break;
                    case "edit_treshold":
                        edit_treshold(request, response);
                        break;
                    case "delete_depart":
                        delete_depart(request, response);
                        break;
                    default:
                        break;
                }
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
