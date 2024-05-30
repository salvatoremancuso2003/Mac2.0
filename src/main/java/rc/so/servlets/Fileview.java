/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rc.so.servlets;

import rc.so.db.Db_Master;
import rc.so.entity.Agency;
import rc.so.entity.BlacklistM;
import rc.so.entity.Booking;
import rc.so.entity.Branch;
import rc.so.entity.Ch_transaction;
import rc.so.entity.Ch_transaction_doc;
import rc.so.entity.Ch_transaction_value;
import rc.so.entity.Company;
import rc.so.entity.Currency;
import rc.so.entity.CustomerKind;
import rc.so.entity.ET_change;
import rc.so.entity.Figures;
import rc.so.entity.IT_change;
import rc.so.entity.NC_category;
import rc.so.entity.NC_causal;
import rc.so.entity.NC_transaction;
import rc.so.entity.Newsletters;
import rc.so.entity.Office;
import rc.so.entity.Openclose;
import rc.so.entity.Taglio;
import rc.so.entity.Till;
import rc.so.entity.Users;
import rc.so.entity.VATcode;
import rc.so.pdf.Pdf;
import static rc.so.pdf.Pdf.pdf_agency_list;
import static rc.so.pdf.Pdf.pdf_bank_list;
import static rc.so.pdf.Pdf.pdf_blackm_list;
import static rc.so.pdf.Pdf.pdf_branch_list;
import static rc.so.pdf.Pdf.pdf_city_list;
import static rc.so.pdf.Pdf.pdf_company_list;
import static rc.so.pdf.Pdf.pdf_creditcard_list;
import static rc.so.pdf.Pdf.pdf_currency_list;
import static rc.so.pdf.Pdf.pdf_district_list;
import static rc.so.pdf.Pdf.pdf_doctype_list;
import static rc.so.pdf.Pdf.pdf_es_paymat;
import static rc.so.pdf.Pdf.pdf_et_List;
import static rc.so.pdf.Pdf.pdf_figures_list;
import static rc.so.pdf.Pdf.pdf_fixcomm_list;
import static rc.so.pdf.Pdf.pdf_groupbr_list;
import static rc.so.pdf.Pdf.pdf_intbook_list;
import static rc.so.pdf.Pdf.pdf_it_List;
import static rc.so.pdf.Pdf.pdf_kindfixcomm_list;
import static rc.so.pdf.Pdf.pdf_kindtrans_list;
import static rc.so.pdf.Pdf.pdf_kycList;
import static rc.so.pdf.Pdf.pdf_lowcomm_list;
import static rc.so.pdf.Pdf.pdf_nations_list;
import static rc.so.pdf.Pdf.pdf_nc_category_cas;
import static rc.so.pdf.Pdf.pdf_nc_category_cat;
import static rc.so.pdf.Pdf.pdf_nc_viewbranch;
import static rc.so.pdf.Pdf.pdf_nl_view;
import static rc.so.pdf.Pdf.pdf_oc_list;
import static rc.so.pdf.Pdf.pdf_raterange_list;
import static rc.so.pdf.Pdf.pdf_safetill_list;
import static rc.so.pdf.Pdf.pdf_transactionList;
import static rc.so.pdf.Pdf.pdf_transactionncList;
import static rc.so.pdf.Pdf.pdf_users_list;
import static rc.so.pdf.Pdf.pdf_vatcode_list;
import static rc.so.pdf.Pdf.pdf_webtrans_List;
import rc.so.pdf.Receipt;
import rc.so.report.CartelloCambiUK;
import static rc.so.util.Constant.patternnormdate_filter;
import static rc.so.util.Constant.patternsql;
import static rc.so.util.Engine.country;
import static rc.so.util.Engine.credit_card;
import static rc.so.util.Engine.district;
import static rc.so.util.Engine.fixed_commission_range;
import static rc.so.util.Engine.formatBankBranchReport;
import static rc.so.util.Engine.get_NC_transaction;
import static rc.so.util.Engine.identificationCard;
import static rc.so.util.Engine.insertTR;
import static rc.so.util.Engine.kindcommissionefissa;
import static rc.so.util.Engine.li_vat;
import static rc.so.util.Engine.list_BlMacc;
import static rc.so.util.Engine.list_bank;
import static rc.so.util.Engine.list_branch;
import static rc.so.util.Engine.list_branch_completeAFTER311217;
import static rc.so.util.Engine.list_customerKind;
import static rc.so.util.Engine.list_group_branch;
import static rc.so.util.Engine.list_internetbooking;
import static rc.so.util.Engine.nc_kind;
import static rc.so.util.Engine.query_nc;
import static rc.so.util.Engine.query_nc_category_filial;
import static rc.so.util.Engine.query_nc_causal_filial;
import static rc.so.util.Engine.query_nc_onlycat;
import static rc.so.util.Engine.rate_range;
import static rc.so.util.Engine.undermincommjustify;
import static rc.so.util.Engine.verifySession;
import rc.so.util.Excel;
import static rc.so.util.Excel.es_paymat;
import static rc.so.util.Excel.excel_agency_list;
import static rc.so.util.Excel.excel_bank_list;
import static rc.so.util.Excel.excel_blackm_list;
import static rc.so.util.Excel.excel_branch_list;
import static rc.so.util.Excel.excel_city_list;
import static rc.so.util.Excel.excel_company_list;
import static rc.so.util.Excel.excel_creditcard_list;
import static rc.so.util.Excel.excel_currency_list;
import static rc.so.util.Excel.excel_district_list;
import static rc.so.util.Excel.excel_doctype_list;
import static rc.so.util.Excel.excel_et_list;
import static rc.so.util.Excel.excel_figures_list;
import static rc.so.util.Excel.excel_fixcomm_list;
import static rc.so.util.Excel.excel_groupbr_list;
import static rc.so.util.Excel.excel_intbook_list;
import static rc.so.util.Excel.excel_it_list;
import static rc.so.util.Excel.excel_kindfixcomm_list;
import static rc.so.util.Excel.excel_kindtrans_list;
import static rc.so.util.Excel.excel_kycList;
import static rc.so.util.Excel.excel_lowcomm_list;
import static rc.so.util.Excel.excel_nations_list;
import static rc.so.util.Excel.excel_nl_view;
import static rc.so.util.Excel.excel_oc_list;
import static rc.so.util.Excel.excel_raterange_list;
import static rc.so.util.Excel.excel_safetill_list;
import static rc.so.util.Excel.excel_transaction_allnccat;
import static rc.so.util.Excel.excel_transaction_list;
import static rc.so.util.Excel.excel_transactionnc_list;
import static rc.so.util.Excel.excel_users_list;
import static rc.so.util.Excel.excel_vatcode_list;
import static rc.so.util.Excel.excel_webtrans_list;
import static rc.so.util.Utility.formatStringtoStringDate_null;
import static rc.so.util.Utility.generaId;
import static rc.so.util.Utility.parseArrayValues;
import static rc.so.util.Utility.redirect;
import static rc.so.util.Utility.replaceApici;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import static java.lang.String.format;
import static java.lang.Thread.currentThread;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import static org.apache.commons.codec.binary.Base64.decodeBase64;
import static org.apache.commons.io.FileUtils.readFileToByteArray;
import static org.apache.commons.lang3.StringUtils.replace;
import org.joda.time.DateTime;
import rc.so.util.Utility;

/**
 *
 * @author rcosco
 */
public class Fileview extends HttpServlet {

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void tb_users(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Db_Master db = new Db_Master();
        String path = db.getPath("temp");
        String value = request.getParameter("value");
        if (value == null) {
            value = "";
        }
        ArrayList<Users> result = db.list_all_users();
        db.closeDB();
        String base64 = null;

        String ext = "";
        String cod = generaId(75);
        if (value.equals("pdf")) {
            base64 = Utility.sanitize(pdf_users_list(new File(path + cod), result));
            ext = ".pdf";
        } else if (value.equals("excel")) {
            base64 = Utility.sanitize(excel_users_list(new File(path + cod), result));
            ext = ".xlsx";
        }

        if (base64 != null) {
            String headerKey = "Content-Disposition";
            String sanitizedcod = Utility.sanitize(cod);
            String sanitizedext = Utility.sanitize(ext);
            String headerValue = format("attachment; filename=\"%s\"", new Object[]{sanitizedcod + sanitizedext});
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
    protected void tb_figures(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Db_Master db = new Db_Master();
        String path = db.getPath("temp");
        String value = request.getParameter("value");
        if (value == null) {
            value = "";
        }

        String filiale = request.getParameter("filiale");

        ArrayList<Figures> result = db.list_figures(filiale);
        db.closeDB();
        String base64 = null;
        String ext = "";
        String cod = generaId(75);
        if (result.size() > 0) {
            if (value.equals("pdf")) {
                base64 = Utility.sanitize(pdf_figures_list(new File(path + cod), result, filiale));
                ext = ".pdf";
            } else if (value.equals("excel")) {
                base64 = Utility.sanitize(excel_figures_list(new File(path + cod), result, filiale));
                ext = ".xlsx";
            }
        } else {
            base64 = Utility.sanitize(new Receipt().print_pdf_noresult(path + cod));
            ext = ".pdf";
        }
        if (base64 != null) {
            String headerKey = "Content-Disposition";
            String sanitizedcod = Utility.sanitize(cod);
            String sanitizedext = Utility.sanitize(ext);
            String headerValue = format("attachment; filename=\"%s\"", new Object[]{sanitizedcod + sanitizedext});
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
    protected void tb_currency(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Db_Master db = new Db_Master();
        String path = db.getPath("temp");
        String value = request.getParameter("value");
        if (value == null) {
            value = "";
        }
        String filiale = db.getCodLocal(true)[0];
        ArrayList<Currency> result = db.list_figures_query_edit(filiale);
        db.closeDB();
        String cod = generaId(75);
        String base64 = null;
        String ext = "";
        if (result.size() > 0) {
            if (value.equals("pdf")) {
                base64 = Utility.sanitize(pdf_currency_list(new File(path + cod), result));
                ext = ".pdf";
            } else if (value.equals("excel")) {
                base64 = Utility.sanitize(excel_currency_list(new File(path + cod), result));
                ext = ".xlsx";
            }
        } else {
            base64 = Utility.sanitize(new Receipt().print_pdf_noresult(path + cod));
            ext = ".pdf";
        }
        if (base64 != null) {
            String headerKey = "Content-Disposition";
            String sanitizedcod = Utility.sanitize(cod);
            String sanitizedext = Utility.sanitize(ext);
            String headerValue = format("attachment; filename=\"%s\"", new Object[]{sanitizedcod + sanitizedext});
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
    protected void tb_safetill(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Db_Master db = new Db_Master();
        String path = db.getPath("temp");
        String value = request.getParameter("value");
        if (value == null) {
            value = "";
        }
        String filiale = request.getParameter("filiale");

        ArrayList<String[]> result = db.list_till(filiale);
        db.closeDB();
        String cod = generaId(75);
        String base64 = null;
        String ext = "";
        if (result.size() > 0) {
            if (value.equals("pdf")) {
                base64 = Utility.sanitize(pdf_safetill_list(new File(path + cod), result, filiale));
                ext = ".pdf";
            } else if (value.equals("excel")) {
                base64 = Utility.sanitize(excel_safetill_list(new File(path + cod), result, filiale));
                ext = ".xlsx";
            }
        } else {
            base64 = Utility.sanitize(new Receipt().print_pdf_noresult(path + cod));
            ext = ".pdf";
        }
        if (base64 != null) {
            String headerKey = "Content-Disposition";
            String sanitizedcod = Utility.sanitize(cod);
            String sanitizedext = Utility.sanitize(ext);
            String headerValue = format("attachment; filename=\"%s\"", new Object[]{sanitizedcod + sanitizedext});
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
    protected void tb_bank(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Db_Master db = new Db_Master();
        String path = db.getPath("temp");
        String value = request.getParameter("value");
        if (value == null) {
            value = "";
        }
        ArrayList<String[]> result = list_bank();
        db.closeDB();
        String cod = generaId(75);
        String base64 = null;
        String ext = "";
        if (result.size() > 0) {
            if (value.equals("pdf")) {
                base64 = Utility.sanitize(pdf_bank_list(new File(path + cod), result));
                ext = ".pdf";
            } else if (value.equals("excel")) {
                base64 = Utility.sanitize(excel_bank_list(new File(path + cod), result));
                ext = ".xlsx";
            }
        } else {
            base64 = Utility.sanitize(new Receipt().print_pdf_noresult(path + cod));
            ext = ".pdf";
        }
        if (base64 != null) {
            String headerKey = "Content-Disposition";
            String sanitizedcod = Utility.sanitize(cod);
            String sanitizedext = Utility.sanitize(ext);
            String headerValue = format("attachment; filename=\"%s\"", new Object[]{sanitizedcod + sanitizedext});
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
    protected void tb_fixcomm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Db_Master db = new Db_Master();
        String path = db.getPath("temp");
        String value = request.getParameter("value");
        if (value == null) {
            value = "";
        }
        ArrayList<String[]> result = fixed_commission_range();
        db.closeDB();
        String cod = generaId(75);
        String base64 = null;
        String ext = "";
        if (result.size() > 0) {
            if (value.equals("pdf")) {
                base64 = Utility.sanitize(pdf_fixcomm_list(new File(path + cod), result));
                ext = ".pdf";
            } else if (value.equals("excel")) {
                base64 = Utility.sanitize(excel_fixcomm_list(new File(path + cod), result));
                ext = ".xlsx";
            }
        } else {
            base64 = new Receipt().print_pdf_noresult(path + cod);
            ext = ".pdf";
        }
        if (base64 != null) {
            String headerKey = "Content-Disposition";
            String sanitizedcod = Utility.sanitize(cod);
            String sanitizedext = Utility.sanitize(ext);
            String headerValue = format("attachment; filename=\"%s\"", new Object[]{sanitizedcod + sanitizedext});
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
    protected void tb_kindfixcomm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Db_Master db = new Db_Master();
        String path = db.getPath("temp");
        String value = request.getParameter("value");
        if (value == null) {
            value = "";
        }
        ArrayList<String[]> result = kindcommissionefissa();
        db.closeDB();
        String cod = generaId(75);
        String base64 = null;
        String ext = "";
        if (result.size() > 0) {
            if (value.equals("pdf")) {
                base64 = Utility.sanitize(pdf_kindfixcomm_list(new File(path + cod), result));
                ext = ".pdf";
            } else if (value.equals("excel")) {
                base64 = Utility.sanitize(excel_kindfixcomm_list(new File(path + cod), result));
                ext = ".xlsx";
            }
        } else {
            base64 = Utility.sanitize(new Receipt().print_pdf_noresult(path + cod));
            ext = ".pdf";
        }
        if (base64 != null) {
            String headerKey = "Content-Disposition";
            String sanitizedcod = Utility.sanitize(cod);
            String sanitizedext = Utility.sanitize(ext);
            String headerValue = format("attachment; filename=\"%s\"", new Object[]{sanitizedcod + sanitizedext});
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
    protected void tb_raterange(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Db_Master db = new Db_Master();
        String path = db.getPath("temp");
        String value = request.getParameter("value");
        if (value == null) {
            value = "";
        }

        String filiale = request.getParameter("filiale");

        ArrayList<String[]> result = rate_range(filiale);
        db.closeDB();
        String cod = generaId(75);
        String base64 = null;
        String ext = "";
        if (result.size() > 0) {
            if (value.equals("pdf")) {
                base64 = Utility.sanitize(pdf_raterange_list(new File(path + cod), result, filiale));
                ext = ".pdf";
            } else if (value.equals("excel")) {
                base64 = Utility.sanitize(excel_raterange_list(new File(path + cod), result, filiale));
                ext = ".xlsx";
            }
        } else {
            base64 = Utility.sanitize(new Receipt().print_pdf_noresult(path + cod));
            ext = ".pdf";
        }
        if (base64 != null) {
            String headerKey = "Content-Disposition";
            String sanitizedcod = Utility.sanitize(cod);
            String sanitizedext = Utility.sanitize(ext);
            String headerValue = format("attachment; filename=\"%s\"", new Object[]{sanitizedcod + sanitizedext});
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
    protected void tb_lowcomm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Db_Master db = new Db_Master();
        String path = db.getPath("temp");
        String value = request.getParameter("value");
        if (value == null) {
            value = "";
        }

        ArrayList<String[]> result = undermincommjustify();
        db.closeDB();
        String cod = generaId(75);
        String base64 = null;
        String ext = "";
        if (result.size() > 0) {
            if (value.equals("pdf")) {
                base64 = Utility.sanitize(pdf_lowcomm_list(new File(path + cod), result));
                ext = ".pdf";
            } else if (value.equals("excel")) {
                base64 = Utility.sanitize(excel_lowcomm_list(new File(path + cod), result));
                ext = ".xlsx";
            }
        } else {
            base64 = Utility.sanitize(new Receipt().print_pdf_noresult(path + cod));
            ext = ".pdf";
        }
        if (base64 != null) {
            String headerKey = "Content-Disposition";
            String sanitizedcod = Utility.sanitize(cod);
            String sanitizedext = Utility.sanitize(ext);
            String headerValue = format("attachment; filename=\"%s\"", new Object[]{sanitizedcod + sanitizedext});
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
    protected void tb_doctype(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Db_Master db = new Db_Master();
        String path = db.getPath("temp");
        String value = request.getParameter("value");
        if (value == null) {
            value = "";
        }

        ArrayList<String[]> result = identificationCard();
        db.closeDB();
        String cod = generaId(75);
        String base64 = null;
        String ext = "";
        if (result.size() > 0) {
            if (value.equals("pdf")) {
                base64 = Utility.sanitize(pdf_doctype_list(new File(path + cod), result));
                ext = ".pdf";
            } else if (value.equals("excel")) {
                base64 = Utility.sanitize(excel_doctype_list(new File(path + cod), result));
                ext = ".xlsx";
            }
        } else {
            base64 = Utility.sanitize(new Receipt().print_pdf_noresult(path + cod));
            ext = ".pdf";
        }
        if (base64 != null) {
            String headerKey = "Content-Disposition";
            String sanitizedcod = Utility.sanitize(cod);
            String sanitizedext = Utility.sanitize(ext);
            String headerValue = format("attachment; filename=\"%s\"", new Object[]{sanitizedcod + sanitizedext});
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
    protected void tb_nations(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Db_Master db = new Db_Master();
        String path = db.getPath("temp");
        String value = request.getParameter("value");
        if (value == null) {
            value = "";
        }

        ArrayList<String[]> result = country();
        db.closeDB();
        String cod = generaId(75);
        String base64 = null;
        String ext = "";
        if (result.size() > 0) {
            if (value.equals("pdf")) {
                base64 = Utility.sanitize(pdf_nations_list(new File(path + cod), result));
                ext = ".pdf";
            } else if (value.equals("excel")) {
                base64 = Utility.sanitize(excel_nations_list(new File(path + cod), result));
                ext = ".xlsx";
            }
        } else {
            base64 = Utility.sanitize(new Receipt().print_pdf_noresult(path + cod));
            ext = ".pdf";
        }
        if (base64 != null) {
            String headerKey = "Content-Disposition";
            String sanitizedcod = Utility.sanitize(cod);
            String sanitizedext = Utility.sanitize(ext);
            String headerValue = format("attachment; filename=\"%s\"", new Object[]{sanitizedcod + sanitizedext});
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
    protected void tb_district(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Db_Master db = new Db_Master();
        String path = db.getPath("temp");
        String value = request.getParameter("value");
        if (value == null) {
            value = "";
        }

        ArrayList<String[]> result = district();
        db.closeDB();
        String cod = generaId(75);
        String base64 = null;
        String ext = "";
        if (result.size() > 0) {
            if (value.equals("pdf")) {
                base64 = Utility.sanitize(pdf_district_list(new File(path + cod), result));
                ext = ".pdf";
            } else if (value.equals("excel")) {
                base64 = Utility.sanitize(excel_district_list(new File(path + cod), result));
                ext = ".xlsx";
            }
        } else {
            base64 = Utility.sanitize(new Receipt().print_pdf_noresult(path + cod));
            ext = ".pdf";
        }
        if (base64 != null) {
            String headerKey = "Content-Disposition";
            String sanitizedcod = Utility.sanitize(cod);
            String sanitizedext = Utility.sanitize(ext);
            String headerValue = format("attachment; filename=\"%s\"", new Object[]{sanitizedcod + sanitizedext});
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
    protected void tb_city(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Db_Master db = new Db_Master();
        String path = db.getPath("temp");
        String value = request.getParameter("value");
        if (value == null) {
            value = "";
        }

        ArrayList<String[]> result = db.listcity();
        db.closeDB();
        String cod = generaId(75);
        String base64 = null;
        String ext = "";
        if (result.size() > 0) {
            if (value.equals("pdf")) {
                base64 = Utility.sanitize(pdf_city_list(new File(path + cod), result));
                ext = ".pdf";
            } else if (value.equals("excel")) {
                base64 = Utility.sanitize(excel_city_list(new File(path + cod), result));
                ext = ".xlsx";
            }
        } else {
            base64 = Utility.sanitize(new Receipt().print_pdf_noresult(path + cod));
            ext = ".pdf";
        }
        if (base64 != null) {
            String headerKey = "Content-Disposition";
            String sanitizedcod = Utility.sanitize(cod);
            String sanitizedext = Utility.sanitize(ext);
            String headerValue = format("attachment; filename=\"%s\"", new Object[]{sanitizedcod + sanitizedext});
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
    protected void tb_intbook(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Db_Master db = new Db_Master();
        String path = db.getPath("temp");
        String value = request.getParameter("value");
        if (value == null) {
            value = "";
        }

        ArrayList<String[]> result = list_internetbooking();
        db.closeDB();
        String cod = generaId(75);
        String base64 = null;
        String ext = "";
        if (result.size() > 0) {
            if (value.equals("pdf")) {
                base64 = Utility.sanitize(pdf_intbook_list(new File(path + cod), result));
                ext = ".pdf";
            } else if (value.equals("excel")) {
                base64 = Utility.sanitize(excel_intbook_list(new File(path + cod), result));
                ext = ".xlsx";
            }
        } else {
            base64 = Utility.sanitize(new Receipt().print_pdf_noresult(path + cod));
            ext = ".pdf";
        }
        if (base64 != null) {
            String headerKey = "Content-Disposition";
            String headerValue = format("attachment; filename=\"%s\"", new Object[]{cod + ext});
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
    protected void tb_agency(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Db_Master db = new Db_Master();
        String path = db.getPath("temp");
        String value = request.getParameter("value");
        if (value == null) {
            value = "";
        }

        ArrayList<Agency> result = db.list_agency();
        db.closeDB();
        String cod = generaId(75);
        String base64 = null;
        String ext = "";
        if (result.size() > 0) {
            if (value.equals("pdf")) {
                base64 = Utility.sanitize(pdf_agency_list(new File(path + cod), result));
                ext = ".pdf";
            } else if (value.equals("excel")) {
                base64 = Utility.sanitize(excel_agency_list(new File(path + cod), result));
                ext = ".xlsx";
            }
        } else {
            base64 = Utility.sanitize(new Receipt().print_pdf_noresult(path + cod));
            ext = ".pdf";
        }
        if (base64 != null) {
            String headerKey = "Content-Disposition";
            String headerValue = format("attachment; filename=\"%s\"", new Object[]{cod + ext});
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
    protected void tb_blackM(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Db_Master db = new Db_Master();
        String path = db.getPath("temp");
        String value = request.getParameter("value");
        if (value == null) {
            value = "";
        }

        ArrayList<BlacklistM> result = list_BlMacc();
        db.closeDB();
        String cod = generaId(75);
        String base64 = null;
        String ext = "";
        if (result.size() > 0) {
            if (value.equals("pdf")) {
                base64 = Utility.sanitize(pdf_blackm_list(new File(path + cod), result));
                ext = ".pdf";
            } else if (value.equals("excel")) {
                base64 = Utility.sanitize(excel_blackm_list(new File(path + cod), result));
                ext = ".xlsx";
            }
        } else {
            base64 = Utility.sanitize(new Receipt().print_pdf_noresult(path + cod));
            ext = ".pdf";
        }
        if (base64 != null) {
            String headerKey = "Content-Disposition";
            String headerValue = format("attachment; filename=\"%s\"", new Object[]{cod + ext});
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
    protected void tb_vatcode(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Db_Master db = new Db_Master();
        String path = db.getPath("temp");
        String value = request.getParameter("value");
        if (value == null) {
            value = "";
        }

        ArrayList<VATcode> result = li_vat(null);
        db.closeDB();
        String cod = generaId(75);
        String base64 = null;
        String ext = "";
        if (result.size() > 0) {
            if (value.equals("pdf")) {
                base64 = Utility.sanitize(pdf_vatcode_list(new File(path + cod), result));
                ext = ".pdf";
            } else if (value.equals("excel")) {
                base64 = Utility.sanitize(excel_vatcode_list(new File(path + cod), result));
                ext = ".xlsx";
            }
        } else {
            base64 = Utility.sanitize(new Receipt().print_pdf_noresult(path + cod));
            ext = ".pdf";
        }
        if (base64 != null) {
            String headerKey = "Content-Disposition";
            String headerValue = format("attachment; filename=\"%s\"", new Object[]{cod + ext});
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
    protected void tb_company(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Db_Master db = new Db_Master();
        String path = db.getPath("temp");
        String value = request.getParameter("value");
        if (value == null) {
            value = "";
        }

        ArrayList<Company> result = db.list_only_company();
        ArrayList<Company> resultagent = db.get_list_Agent();
        db.closeDB();
        String cod = generaId(75);
        String base64 = null;
        String ext = "";
        if (result.size() > 0) {
            if (value.equals("pdf")) {
                base64 = Utility.sanitize(pdf_company_list(new File(path + cod), result, resultagent));
                ext = ".pdf";
            } else if (value.equals("excel")) {
                base64 = Utility.sanitize(excel_company_list(new File(path + cod), result, resultagent));
                ext = ".xlsx";
            }
        } else {
            base64 = Utility.sanitize(new Receipt().print_pdf_noresult(path + cod));
            ext = ".pdf";
        }
        if (base64 != null) {
            String headerKey = "Content-Disposition";
            String headerValue = format("attachment; filename=\"%s\"", new Object[]{cod + ext});
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
    protected void transaction_list(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Db_Master db = new Db_Master();
        String path = Utility.sanitize(db.getPath("temp"));
        String value = Utility.sanitize(request.getParameter("value"));
        if (value == null) {
            value = "";
        }

        String d1 = Utility.sanitize(request.getParameter("d1"));
        String d2 = Utility.sanitize(request.getParameter("d2"));

        String taxcode = replaceApici(Utility.sanitize(request.getParameter("taxcode").trim()));
        String surname = replaceApici(Utility.sanitize(request.getParameter("surname").trim()));
        String name = replaceApici(Utility.sanitize(request.getParameter("name").trim()));
        String branch = Utility.sanitize(request.getParameter("branch").trim());

//        String pdf = request.getParameter("pdf");
//        DateTime today = db.getCurdateDT();
        ArrayList<Ch_transaction> result = db.query_transaction_ch_new(d1, d2, branch, surname, name, taxcode);
        db.closeDB();
        String cod = generaId(75);
        String base64 = null;
        String ext = "";
        if (result.size() > 0) {
            if (value.equals("pdf")) {
                base64 = Utility.sanitize(pdf_transactionList(path, result));
                ext = ".pdf";
            } else if (value.equals("excel")) {
                base64 = Utility.sanitize(excel_transaction_list(new File(path + cod), result));
                ext = ".xlsx";
            }
        } else {
            base64 = Utility.sanitize(new Receipt().print_pdf_noresult(path + cod));
            ext = ".pdf";
        }
        if (base64 != null) {
            String headerKey = "Content-Disposition";
            String headerValue = format("attachment; filename=\"%s\"", new Object[]{cod + ext});
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
    protected void transactionnc_list(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Db_Master db = new Db_Master();
        String path = Utility.sanitize(db.getPath("temp"));
        String value = Utility.sanitize(request.getParameter("value"));
        if (value == null) {
            value = "";
        }
        String d1 = Utility.sanitize(request.getParameter("d1"));
        String d2 = Utility.sanitize(request.getParameter("d2"));
        String branch = Utility.sanitize(request.getParameter("branch"));

        ArrayList<NC_category> array_nc_cat = db.list_nc_category_enabled();
        String[] nc_cat1 = request.getParameterValues("nc_cat1");
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

        ArrayList<NC_transaction> result = db.query_NC_transaction(d1, d2, branch, list_nc_cat);

        db.closeDB();
        String cod = generaId(75);
        String base64 = null;
        String ext = "";
        if (result.size() > 0) {
            if (value.equals("pdf")) {
                base64 = Utility.sanitize(pdf_transactionncList(new File(path + cod), result));
                ext = ".pdf";
            } else if (value.equals("excel")) {
                base64 = Utility.sanitize(excel_transactionnc_list(new File(path + cod), result));
                ext = ".xlsx";
            }
        } else {
            base64 = Utility.sanitize(new Receipt().print_pdf_noresult(path + cod));
            ext = ".pdf";
        }

        if (base64 != null) {
            String headerKey = "Content-Disposition";
            String headerValue = format("attachment; filename=\"%s\"", new Object[]{cod + ext});
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
    protected void kyc_list(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Db_Master db = new Db_Master();
        String path = db.getPath("temp");
        String value = request.getParameter("value");
        if (value == null) {
            value = "";
        }
        String branch = request.getParameter("branch");
        String d1 = request.getParameter("d1");
        String d2 = request.getParameter("d2");
        String cl_cog = replaceApici(request.getParameter("cl_cog"));
        String cl_na = replaceApici(request.getParameter("cl_na"));

        String data1 = formatStringtoStringDate_null(d1, patternnormdate_filter, patternsql);
        String data2 = formatStringtoStringDate_null(d2, patternnormdate_filter, patternsql);

        ArrayList<Ch_transaction_doc> result = db.query_kyc_list(data1, data2, cl_cog, cl_na, branch);
        db.closeDB();
        String cod = generaId(75);
        String base64 = null;
        String ext = "";
        if (result.size() > 0) {
            if (value.equals("pdf")) {
                base64 = Utility.sanitize(pdf_kycList(new File(path + cod), result));
                ext = ".pdf";
            } else if (value.equals("excel")) {
                base64 = Utility.sanitize(excel_kycList(new File(path + cod), result));
                ext = ".xlsx";
            }
        } else {
            base64 = Utility.sanitize(new Receipt().print_pdf_noresult(path + cod));
            ext = ".pdf";
        }
        if (base64 != null) {
            String headerKey = "Content-Disposition";
            String headerValue = format("attachment; filename=\"%s\"", new Object[]{cod + ext});
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
    protected void nc_category_cat(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Db_Master db = new Db_Master();
        String path = Utility.sanitize(db.getPath("temp"));
        String value = Utility.sanitize(request.getParameter("value"));
        if (value == null) {
            value = "";
        }
        String kind_1 = Utility.sanitize(request.getParameter("kind_1"));

//        boolean central = true;
        ArrayList<NC_category> result = db.query_nc_category(kind_1);
        db.closeDB();
        String cod = generaId(75);
        String base64 = null;
        String ext = "";
        if (result.size() > 0) {
            if (value.equals("pdf")) {
                base64 = Utility.sanitize(pdf_nc_category_cat(new File(path + cod), result));
                ext = ".pdf";
            } else if (value.equals("excel")) {
                base64 = Utility.sanitize(Excel.nc_category_cat(new File(path + cod), result));
                ext = ".xlsx";
            }
        } else {
            base64 = Utility.sanitize(new Receipt().print_pdf_noresult(path + cod));
            ext = ".pdf";
        }
        if (base64 != null) {
            String headerKey = "Content-Disposition";
            String headerValue = format("attachment; filename=\"%s\"", new Object[]{cod + ext});
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
    protected void nc_viewbranch(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Db_Master db = new Db_Master();
        String path = Utility.sanitize(db.getPath("temp"));
        String value = Utility.sanitize(request.getParameter("value"));
        if (value == null) {
            value = "";
        }
        String branch = Utility.sanitize(request.getParameter("branch"));

        String status = Utility.sanitize(request.getParameter("status"));
        ArrayList<String[]> array_nc_kind = nc_kind();

        ArrayList<NC_category> licateg = query_nc_category_filial(branch, status);
        ArrayList<NC_causal> licaus = query_nc_causal_filial(branch, status);
        ArrayList<String[]> result = query_nc(licateg, licaus, array_nc_kind);
        db.closeDB();
        String cod = generaId(75);
        String base64 = null;
        String ext = "";
        if (result.size() > 0) {
            if (value.equals("pdf")) {
                base64 = Utility.sanitize(pdf_nc_viewbranch(new File(path + cod), result));
                ext = ".pdf";
            } else if (value.equals("excel")) {
                base64 = Utility.sanitize(Excel.nc_viewbranch(new File(path + cod), result));
                ext = ".xlsx";
            }
        } else {
            base64 = Utility.sanitize(new Receipt().print_pdf_noresult(path + cod));
            ext = ".pdf";
        }
        if (base64 != null) {
            String headerKey = "Content-Disposition";
            String headerValue = format("attachment; filename=\"%s\"", new Object[]{cod + ext});
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
    protected void es_paym(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Db_Master db = new Db_Master();
        String path = db.getPath("temp");
        String value = request.getParameter("value");
        if (value == null) {
            value = "";
        }

        ArrayList<Taglio> result = db.list_paymat();
        db.closeDB();
        String cod = generaId(75);
        String base64 = null;
        String ext = "";
        if (result.size() > 0) {
            if (value.equals("pdf")) {
                base64 = Utility.sanitize(pdf_es_paymat(new File(path + cod), result));
                ext = ".pdf";
            } else if (value.equals("excel")) {
                base64 = Utility.sanitize(es_paymat(new File(path + cod), result));
                ext = ".xlsx";
            }
        } else {
            base64 = Utility.sanitize(new Receipt().print_pdf_noresult(path + cod));
            ext = ".pdf";
        }
        if (base64 != null) {
            String headerKey = "Content-Disposition";
            String headerValue = format("attachment; filename=\"%s\"", new Object[]{cod + ext});
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
    protected void nc_category_cas(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Db_Master db = new Db_Master();
        String path = Utility.sanitize(db.getPath("temp"));
        String value = Utility.sanitize(request.getParameter("value"));
        if (value == null) {
            value = "";
        }
        String codnc = Utility.sanitize(request.getParameter("codnc"));
        ArrayList<NC_causal> result = db.query_nc_causal(codnc);
        db.closeDB();
        String cod = generaId(75);
        String base64 = null;
        String ext = "";
        if (result.size() > 0) {
            if (value.equals("pdf")) {
                base64 = Utility.sanitize(pdf_nc_category_cas(new File(path + cod), result));
                ext = ".pdf";
            } else if (value.equals("excel")) {
                base64 = Utility.sanitize(Excel.nc_category_cas(new File(path + cod), result));
                ext = ".xlsx";
            }
        } else {
            base64 = Utility.sanitize(new Receipt().print_pdf_noresult(path + cod));
            ext = ".pdf";
        }
        if (base64 != null) {
            String headerKey = "Content-Disposition";
            String headerValue = format("attachment; filename=\"%s\"", new Object[]{cod + ext});
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
    protected void it_list(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Db_Master db = new Db_Master();
        String path = db.getPath("temp");
        String value = request.getParameter("value");
        if (value == null) {
            value = "";
        }
        String typeop = request.getParameter("typeop");
        String d1 = request.getParameter("d1");
        String d2 = request.getParameter("d2");
        String branch = request.getParameter("branch");

        ArrayList<IT_change> result = db.query_it(typeop, d1, d2, branch);
        db.closeDB();
        String cod = generaId(75);
        String base64 = null;
        String ext = "";
        if (result.size() > 0) {
            if (value.equals("pdf")) {
                base64 = Utility.sanitize(pdf_it_List(new File(path + cod), result));
                ext = ".pdf";
            } else if (value.equals("excel")) {
                base64 = Utility.sanitize(excel_it_list(new File(path + cod), result));
                ext = ".xlsx";
            }
        } else {
            base64 = Utility.sanitize(new Receipt().print_pdf_noresult(path + cod));
            ext = ".pdf";
        }
        if (base64 != null) {
            String headerKey = "Content-Disposition";
            String headerValue = format("attachment; filename=\"%s\"", new Object[]{cod + ext});
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
    protected void et_list(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Db_Master db = new Db_Master();
        String path = Utility.sanitize(db.getPath("temp"));
        String value = Utility.sanitize(request.getParameter("value"));
        if (value == null) {
            value = "";
        }
        String typeop = Utility.sanitize(request.getParameter("typeop"));
        String d1 = Utility.sanitize(request.getParameter("d1"));
        String d2 = Utility.sanitize(request.getParameter("d2"));
        String branch = Utility.sanitize(request.getParameter("branch"));
        ArrayList<NC_category> nc_cat;
        if (branch != null && !branch.equals("") && !branch.equals("...")) {
            nc_cat = db.query_nc_category_filial(branch, null);
        } else {
            nc_cat = db.query_nc_category_filial("000", null);
        }
        ArrayList<ET_change> result = db.query_et(typeop, d1, d2, branch);
        db.closeDB();
        String cod = generaId(75);
        String base64 = null;
        String ext = "";
        if (result.size() > 0) {
            if (value.equals("pdf")) {
                base64 = Utility.sanitize(pdf_et_List(new File(path + cod), result, typeop, nc_cat));
                ext = ".pdf";
            } else if (value.equals("excel")) {
                base64 = excel_et_list(new File(path + cod), result, typeop, nc_cat);
                ext = ".xlsx";
            }
        } else {
            base64 = Utility.sanitize(new Receipt().print_pdf_noresult(path + cod));
            ext = ".pdf";
        }
        if (base64 != null) {
            String headerKey = "Content-Disposition";
            String headerValue = format("attachment; filename=\"%s\"", new Object[]{cod + ext});
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
    protected void web_trans(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Db_Master db = new Db_Master();
        String path = Utility.sanitize(db.getPath("temp"));
        String value = Utility.sanitize(request.getParameter("value"));
        if (value == null) {
            value = "";
        }
        String status = Utility.sanitize(request.getParameter("status"));
        String d1 = Utility.sanitize(request.getParameter("d1"));
        String d2 = Utility.sanitize(request.getParameter("d2"));
        String branch[];
        String br1 = Utility.sanitize(request.getParameter("branch"));
        if (br1 == null || br1.trim().equals("")) {
            branch = parseArrayValues(db.list_branchcode_ENABLED());
        } else {
            branch = parseArrayValues(Utility.sanitize(request.getParameter("branch")), ";");
        }

        ArrayList<Booking> result = db.query_prenot_list(branch, status, null, d1, d2);
        ArrayList<Branch> br = db.list_branch();
        ArrayList<Currency> cu = db.list_figures();
        db.closeDB();
        String cod = generaId(75);
        String base64 = null;
        String ext = "";
        if (result.size() > 0) {
            if (value.equals("pdf")) {
                base64 = Utility.sanitize(pdf_webtrans_List(new File(path + cod), result, br, cu));
                ext = ".pdf";
            } else if (value.equals("excel")) {
                base64 =Utility.sanitize( excel_webtrans_list(new File(path + cod), result, br, cu));
                ext = ".xlsx";
            }
        } else {
            base64 = Utility.sanitize(new Receipt().print_pdf_noresult(path + cod));
            ext = ".pdf";
        }
        if (base64 != null) {
            String headerKey = "Content-Disposition";
            String headerValue = format("attachment; filename=\"%s\"", new Object[]{cod + ext});
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
    protected void nl_view(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Db_Master db = new Db_Master();
        String path = db.getPath("temp");
        String value = request.getParameter("value");
        if (value == null) {
            value = "";
        }
        String status = request.getParameter("status");
        String user = (String) request.getSession().getAttribute("us_cod");
        ArrayList<Newsletters> result = db.query_newsletters(user, status);
        db.closeDB();
        String cod = generaId(75);
        String base64 = null;
        String ext = "";
        if (result.size() > 0) {
            if (value.equals("pdf")) {
                base64 = Utility.sanitize(pdf_nl_view(new File(path + cod), result));
                ext = ".pdf";
            } else if (value.equals("excel")) {
                base64 = Utility.sanitize(excel_nl_view(new File(path + cod), result));
                ext = ".xlsx";
            }
        } else {
            base64 = Utility.sanitize(new Receipt().print_pdf_noresult(path + cod));
            ext = ".pdf";
        }
        if (base64 != null) {
            String headerKey = "Content-Disposition";
            String headerValue = format("attachment; filename=\"%s\"", new Object[]{cod + ext});
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
    protected void oc_list(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Db_Master db = new Db_Master();
        String path = db.getPath("temp");
        String value = request.getParameter("value");
        if (value == null) {
            value = "";
        }

        String d1 = request.getParameter("d1");
        String d2 = request.getParameter("d2");
        String till = request.getParameter("till");
        String branch = request.getParameter("branch");

        ArrayList<Openclose> result = db.query_oc(till, d1, d2, branch);
        ArrayList<Till> listTill = db.list_ALLtill();

        db.closeDB();
        String cod = generaId(75);
        String base64 = null;
        String ext = "";
        if (result.size() > 0) {
            if (value.equals("pdf")) {
                base64 = Utility.sanitize(pdf_oc_list(new File(path + cod), result, listTill));
                ext = ".pdf";
            } else if (value.equals("excel")) {
                base64 = Utility.sanitize(excel_oc_list(new File(path + cod), result, listTill));
                ext = ".xlsx";
            }
        } else {
            base64 = Utility.sanitize(new Receipt().print_pdf_noresult(path + cod));
            ext = ".pdf";
        }
        if (base64 != null) {
            String headerKey = "Content-Disposition";
            String headerValue = format("attachment; filename=\"%s\"", new Object[]{cod + ext});
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
    protected void tb_branch(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Db_Master db = new Db_Master();
        String path = db.getPath("temp");
        String value = request.getParameter("value");
        if (value == null) {
            value = "";
        }

        ArrayList<Branch> result = list_branch();
        db.closeDB();
        String cod = generaId(75);
        String base64 = null;
        String ext = "";
        if (!result.isEmpty()) {
            if (value.equals("pdf")) {
                base64 = Utility.sanitize(pdf_branch_list(new File(path + cod), result));
                ext = ".pdf";
            } else if (value.equals("excel")) {
                base64 = Utility.sanitize(excel_branch_list(new File(path + cod), result));
                ext = ".xlsx";
            }
        } else {
            base64 = Utility.sanitize(new Receipt().print_pdf_noresult(path + cod));
            ext = ".pdf";
        }
        if (base64 != null) {
            String headerKey = "Content-Disposition";
            String headerValue = format("attachment; filename=\"%s\"", new Object[]{cod + ext});
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
    protected void tb_kindtrans(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Db_Master db = new Db_Master();
        String path = db.getPath("temp");
        String value = request.getParameter("value");
        if (value == null) {
            value = "";
        }

        ArrayList<CustomerKind> result = list_customerKind();
        db.closeDB();
        String cod = generaId(75);
        String base64 = null;
        String ext = "";
        if (result.size() > 0) {
            if (value.equals("pdf")) {
                base64 = Utility.sanitize(pdf_kindtrans_list(new File(path + cod), result));
                ext = ".pdf";
            } else if (value.equals("excel")) {
                base64 = Utility.sanitize(excel_kindtrans_list(new File(path + cod), result));
                ext = ".xlsx";
            }
        } else {
            base64 = Utility.sanitize(new Receipt().print_pdf_noresult(path + cod));
            ext = ".pdf";
        }
        if (base64 != null) {
            String headerKey = "Content-Disposition";
            String headerValue = format("attachment; filename=\"%s\"", new Object[]{cod + ext});
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
    protected void tb_creditcard(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Db_Master db = new Db_Master();
        String path = db.getPath("temp");
        String value = request.getParameter("value");
        if (value == null) {
            value = "";
        }
        String filiale = request.getParameter("filiale");

        ArrayList<String[]> result = credit_card(filiale);
        db.closeDB();
        String cod = generaId(75);
        String base64 = null;
        String ext = "";
        if (result.size() > 0) {
            if (value.equals("pdf")) {
                base64 = Utility.sanitize(pdf_creditcard_list(new File(path + cod), result, filiale));
                ext = ".pdf";
            } else if (value.equals("excel")) {
                base64 = Utility.sanitize(excel_creditcard_list(new File(path + cod), result, filiale));
                ext = ".xlsx";
            }
        } else {
            base64 = Utility.sanitize(new Receipt().print_pdf_noresult(path + cod));
            ext = ".pdf";
        }
        if (base64 != null) {
            String headerKey = "Content-Disposition";
            String headerValue = format("attachment; filename=\"%s\"", new Object[]{cod + ext});
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
    protected void cart_change(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Db_Master db = new Db_Master();
        String path = Utility.sanitize(db.getPath("temp"));
        Office of = db.get_national_office();
        String filiale = Utility.sanitize(request.getParameter("filiale"));
        String[] fil = new String[2];
        fil[0] = filiale;
        fil[1] = formatBankBranchReport(filiale, "BR", null, list_branch_completeAFTER311217());
        ArrayList<Currency> list_all_currency = db.list_currency_cartellocambi(filiale);
        ArrayList<Figures> list_all_figures = db.list_figures(filiale);
        ArrayList<String[]> listFixCOm = db.fixed_commission_range(filiale);
        Branch bra = db.get_branch(filiale);
        db.closeDB();

        String base64 = null;
        String naz = Utility.sanitize(request.getParameter("naz"));

        switch (naz) {
            case "ITA":
                base64 = Utility.sanitize(new Pdf().cartelloCambiITA(path, list_all_currency, fil, listFixCOm, list_all_figures));
                break;
            case "CZ":
                base64 = Utility.sanitize(new Pdf().cartelloCambiCZ(path, list_all_currency, list_all_figures, of, bra));
                break;
            case "UK":
                base64 = Utility.sanitize(new CartelloCambiUK().receipt(path, list_all_currency, list_all_figures, bra, listFixCOm));
                break;
            default:
                break;
        }
        if (base64 != null) {
            String headerKey = "Content-Disposition";
            String sanitizednaz= Utility.sanitize(naz);
            String headerValue = format("attachment; filename=\"%s\"", new Object[]{new DateTime().toString("ddMMyyyy")
                + "_" + sanitizednaz + "_" + "CurrencyChangeList" + ".pdf"});
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
    protected void tb_groupbr(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Db_Master db = new Db_Master();
        String path = db.getPath("temp");
        String value = request.getParameter("value");
        if (value == null) {
            value = "";
        }

        ArrayList<String[]> g01 = list_group_branch(true, false, false, false);
        ArrayList<String[]> g02 = list_group_branch(false, true, false, false);
        ArrayList<String[]> g03 = list_group_branch(false, false, true, false);
        ArrayList<String[]> rv = list_group_branch(false, false, false, true);
        ArrayList<String[]> result = new ArrayList<>();
        result.addAll(rv);
        result.addAll(g01);
        result.addAll(g02);
        result.addAll(g03);

        db.closeDB();
        String cod = generaId(75);
        String base64 = null;
        String ext = "";
        if (result.size() > 0) {
            if (value.equals("pdf")) {
                base64 = Utility.sanitize(pdf_groupbr_list(new File(path + cod), result));
                ext = ".pdf";
            } else if (value.equals("excel")) {
                base64 = Utility.sanitize(excel_groupbr_list(new File(path + cod), result));
                ext = ".xlsx";
            }
        } else {
            base64 = Utility.sanitize(new Receipt().print_pdf_noresult(path + cod));
            ext = ".pdf";
        }
        if (base64 != null) {
            String headerKey = "Content-Disposition";
            String headerValue = format("attachment; filename=\"%s\"", new Object[]{cod + ext});
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
    protected void allnccat(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

//        redirect(request, response, "page_wip.html");
//        
//        
        Db_Master dbm = new Db_Master();
        String path = dbm.getPath("temp");
        ArrayList<String[]> array_nc_kind = dbm.nc_kind();
        ArrayList<NC_category> licateg = dbm.query_nc_category_all(null);
        ArrayList<NC_causal> licaus = dbm.query_nc_causal_all(null);
        dbm.closeDB();

        ArrayList<String[]> liout = query_nc_onlycat(licateg, licaus, array_nc_kind);
        String base64;
        String ext;
        String cod = generaId(75);
        if (liout.size() > 0) {
            ext = ".xlsx";
            base64 = Utility.sanitize(excel_transaction_allnccat(new File(path + cod + ext), liout));
        } else {
            ext = ".pdf";
            base64 = Utility.sanitize(new Receipt().print_pdf_noresult(path + cod + ext));
        }
        if (base64 != null) {
            String headerKey = "Content-Disposition";
            String headerValue = format("attachment; filename=\"%s\"", new Object[]{cod + ext});
            response.setHeader(headerKey, headerValue);
            try (OutputStream outStream = response.getOutputStream()) {
                outStream.write(decodeBase64(base64.getBytes()));
                outStream.flush();
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
    protected void listpdfclient(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String cod = Utility.sanitize(request.getParameter("cod"));

        if (cod != null) {
            Db_Master dbcentral = new Db_Master(true);
            if (dbcentral.getC() == null) {
                dbcentral = new Db_Master();
            }

            String cn = dbcentral.query_Client_transactionCN(null, cod);

            ArrayList<Ch_transaction_value> li_value = dbcentral.query_transaction_value_1558(cod);

            dbcentral.closeDB();
            Db_Master dbm = new Db_Master();
            String path = Utility.sanitize(dbm.getPath("temp"));
            dbm.closeDB();

            String base64 = Utility.sanitize(new Pdf().pdf_transactionList_1558(path, cn, li_value));

            if (base64 != null) {
                String headerKey = "Content-Disposition";
                String headerValue = format("attachment; filename=\"%s\"", new Object[]{new DateTime().toString("ddMMyyyyHHmmssSSS") + "Transactionview.pdf"});
                response.setHeader(headerKey, headerValue);
                try (OutputStream outStream = response.getOutputStream()) {
                    outStream.write(decodeBase64(base64.getBytes()));
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
    protected void transactionpdf(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String cod = Utility.sanitize(request.getParameter("cod"));
        if (cod != null) {
            Db_Master db = new Db_Master();
            String path = Utility.sanitize(db.getPath("temp"));
            db.closeDB();
            String base64 = Utility.sanitize(new Receipt().transactionview(path, cod));
            if (base64 != null) {
                String headerKey = "Content-Disposition";
                String headerValue = format("attachment; filename=\"%s\"", new Object[]{new DateTime().toString("ddMMyyyyHHmmssSSS") + "Transactionview.pdf"});
                response.setHeader(headerKey, headerValue);
                try (OutputStream outStream = response.getOutputStream()) {
                    outStream.write(decodeBase64(base64.getBytes()));
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
    protected void viewncfile(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String cod = request.getParameter("cod");
        NC_transaction nc = get_NC_transaction(Utility.sanitize(cod));
        if (nc != null) {
            if (nc.getDocrico() != null && !nc.getDocrico().equals("-")) {
                if (nc.getDocrico().contains(";")) {
                    String ext = nc.getDocrico().split(";")[0];
                    String base64 = Utility.sanitize(nc.getDocrico().split(";")[1]);
                    String headerKey = "Content-Disposition";
                    String headerValue = format("attachment; filename=\"%s\"", new Object[]{new DateTime().toString("ddMMyyyyHHmmssSSS") + "." + Utility.sanitize(ext)});
                    response.setHeader(headerKey, headerValue);
                    try (OutputStream outStream = response.getOutputStream()) {
                        outStream.write(decodeBase64(base64.getBytes()));
                    }
                } else {

                    if (nc.getDocrico().startsWith("FILE[")) {
                        String pa1 = replace(nc.getDocrico(), "FILE[", "");
                        File f = new File(Utility.sanitize(pa1));
                        String headerKey = "Content-Disposition";
                        String headerValue = format("attachment; filename=\"%s\"", new Object[]{new DateTime().toString("ddMMyyyyHHmmssSSS") + ".pdf"});
                        response.setHeader(headerKey, headerValue);
                        try (OutputStream outStream = response.getOutputStream()) {
                            outStream.write(readFileToByteArray(f));
                        }
                    } else {
                        String headerKey = "Content-Disposition";
                        String headerValue = format("attachment; filename=\"%s\"", new Object[]{new DateTime().toString("ddMMyyyyHHmmssSSS") + ".pdf"});
                        response.setHeader(headerKey, headerValue);
                        try (OutputStream outStream = response.getOutputStream()) {
                            outStream.write(decodeBase64(nc.getDocrico().getBytes()));
                        }
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
            }
            response.setContentType("text/html;charset=UTF-8");
//            request.setCharacterEncoding("UTF-8");
            String type = request.getParameter("type");
            if (type.equals("viewncfile")) {
                viewncfile(request, response);
            }
            if (type.equals("tb_users")) {
                tb_users(request, response);
            }
            if (type.equals("tb_figures")) {
                tb_figures(request, response);
            }
            if (type.equals("tb_currency")) {
                tb_currency(request, response);
            }
            if (type.equals("tb_safetill")) {
                tb_safetill(request, response);
            }
            if (type.equals("tb_bank")) {
                tb_bank(request, response);
            }
            if (type.equals("tb_branch")) {
                tb_branch(request, response);
            }
            if (type.equals("tb_groupbr")) {
                tb_groupbr(request, response);
            }
            if (type.equals("tb_creditcard")) {
                tb_creditcard(request, response);
            }
            if (type.equals("tb_kindtrans")) {
                tb_kindtrans(request, response);
            }
            if (type.equals("tb_fixcomm")) {
                tb_fixcomm(request, response);
            }
            if (type.equals("tb_kindfixcomm")) {
                tb_kindfixcomm(request, response);
            }
            if (type.equals("tb_raterange")) {
                tb_raterange(request, response);
            }
            if (type.equals("tb_lowcomm")) {
                tb_lowcomm(request, response);
            }
            if (type.equals("tb_doctype")) {
                tb_doctype(request, response);
            }
            if (type.equals("tb_nations")) {
                tb_nations(request, response);
            }
            if (type.equals("tb_district")) {
                tb_district(request, response);
            }
            if (type.equals("tb_city")) {
                tb_city(request, response);
            }
            if (type.equals("tb_agency")) {
                tb_agency(request, response);
            }
            if (type.equals("tb_intbook")) {
                tb_intbook(request, response);
            }
            if (type.equals("tb_blackM")) {
                tb_blackM(request, response);
            }
            if (type.equals("tb_vatcode")) {
                tb_vatcode(request, response);
            }
            if (type.equals("tb_company")) {
                tb_company(request, response);
            }
            if (type.equals("transaction_list")) {
                transaction_list(request, response);
            }
            if (type.equals("oc_list")) {
                oc_list(request, response);
            }
            if (type.equals("transactionnc_list")) {
                transactionnc_list(request, response);
            }
            if (type.equals("it_list")) {
                it_list(request, response);
            }
            if (type.equals("et_list")) {
                et_list(request, response);
            }
            if (type.equals("web_trans")) {
                web_trans(request, response);
            }
            if (type.equals("nlview")) {
                nl_view(request, response);
            }

            if (type.equals("nc_category_cat")) {
                nc_category_cat(request, response);
            }
            if (type.equals("nc_category_cas")) {
                nc_category_cas(request, response);
            }
            if (type.equals("nc_viewbranch")) {
                nc_viewbranch(request, response);
            }
            if (type.equals("es_paym")) {
                es_paym(request, response);
            }

            if (type.equals("cart_change")) {
                cart_change(request, response);
            }
            if (type.equals("kyc_list")) {
                kyc_list(request, response);
            }
            if (type.equals("transactionpdf")) {
                transactionpdf(request, response);
            }
            if (type.equals("listpdfclient")) {
                listpdfclient(request, response);
            }

            if (type.equals("allnccat")) {
                allnccat(request, response);
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
