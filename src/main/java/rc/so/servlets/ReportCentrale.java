package rc.so.servlets;

import rc.so.db.Db_Master;
import rc.so.entity.Branch;
import rc.so.entity.Ch_transaction;
import rc.so.entity.ET_change;
import rc.so.entity.Marketing;
import static rc.so.entity.Marketing.receiptexcel;
import rc.so.entity.NC_category;
import rc.so.entity.NC_causal;
import rc.so.entity.NC_transaction;
import rc.so.entity.Openclose;
import rc.so.excel.Dailyerror;
import static rc.so.pdf.NewETReceipt.complete;
import rc.so.pdf.Receipt;
import rc.so.report.BranchStockInquiry;
import rc.so.report.BranchStockInquiry_value;
import rc.so.report.CustomerTransactionList;
import rc.so.report.CustomerTransactionList_value;
import rc.so.report.Daily;
import rc.so.report.Daily_value;
import rc.so.report.OfficeStockPrice;
import rc.so.report.OfficeStockPriceBranch;
import rc.so.report.OfficeStockPriceBranch_value;
import rc.so.report.OfficeStockPrice_value;
import rc.so.report.Openclose_Error;
import rc.so.report.Openclose_Error_value;
import rc.so.report.Openclose_Error_value_stock;
import rc.so.report.StockPrice_value;
import rc.so.report.TillTransactionList;
import rc.so.report.TillTransactionListCurrency;
import rc.so.report.TillTransactionListCurrency_value;
import rc.so.report.TillTransactionList_value;
import rc.so.reportcentrale.C_AnalysisCurrency;
import rc.so.reportcentrale.C_AnalysisDetailsTransactionCertificationExtraCEEforBranchAndPeriod;
import rc.so.reportcentrale.C_AnalysisReprint;
import rc.so.reportcentrale.C_AnalysisReprintNoChange;
import rc.so.reportcentrale.C_BankBranch;
import rc.so.reportcentrale.C_CashierDetails;
import rc.so.reportcentrale.C_CashierPerformance;
import rc.so.reportcentrale.C_ChangeInternetBookingForBranches;
import rc.so.reportcentrale.C_ChangeMovimentDetailForBranches;
import rc.so.reportcentrale.C_ChangeMovimentForAgency;
import rc.so.reportcentrale.C_ChangeMovimentForBranches;
import rc.so.reportcentrale.C_ChangeVolumeAffair;
import rc.so.reportcentrale.C_ChangeVolumeAffairCashAdvance;
import rc.so.reportcentrale.C_CloseBranch;
import rc.so.reportcentrale.C_CloseBranch_value;
import rc.so.reportcentrale.C_CustomerCareRefund;
import rc.so.reportcentrale.C_FreeTax_AmountBranch;
import rc.so.reportcentrale.C_FreeTax_AmountNation;
import rc.so.reportcentrale.C_FreeTax_Analisys;
import rc.so.reportcentrale.C_FreeTax_Branch;
import rc.so.reportcentrale.C_FreeTax_BranchCausal;
import rc.so.reportcentrale.C_FreeTax_BranchCurrency;
import rc.so.reportcentrale.C_FreeTax_CausalBranch;
import rc.so.reportcentrale.C_FreeTax_NationAmount;
import rc.so.reportcentrale.C_Interbranch;
import rc.so.reportcentrale.C_InterbranchDetails;
import rc.so.reportcentrale.C_Interbranch_value;
import rc.so.reportcentrale.C_NoChangeMovimentForBranches;
import rc.so.reportcentrale.C_Openclose_error;
import rc.so.reportcentrale.C_SizeAndQuantity;
import rc.so.reportcentrale.C_StockInquiry;
import rc.so.reportcentrale.C_TransactionRegisterDetail;
import rc.so.reportcentrale.C_TransactionRegisterDetail_value;
import rc.so.reportcentrale.C_TransactionRegisterSummary;
import rc.so.reportcentrale.C_TransactionRegisterSummary_value;
import rc.so.reportcentrale.C_TrimestraleCZK;
import rc.so.reportcentrale.C_TrimestraleCZK_value;
import rc.so.reportcentrale.C_WesternUnion;
import rc.so.reportcentrale.C_WesternUnion_value;
import rc.so.reportcentrale.C_freeTaxPivotTotale;
import rc.so.reportcentrale.C_freeTaxPivotTotale_value;
import static rc.so.servlets.Report.getTimepicker;
import static rc.so.util.Constant.is_CZ;
import static rc.so.util.Constant.is_IT;
import static rc.so.util.Constant.is_UK;
import static rc.so.util.Constant.localcurrency;
import static rc.so.util.Constant.newpread;
import static rc.so.util.Constant.patternnormdate;
import static rc.so.util.Constant.patternnormdate_f;
import static rc.so.util.Constant.patternnormdate_filter;
import static rc.so.util.Constant.patternsql;
import static rc.so.util.Constant.patternsqldate;
import static rc.so.util.Constant.patterntsdate2;
import static rc.so.util.Engine.formatBankBranch;
import static rc.so.util.Engine.formatBankBranchReport;
import static rc.so.util.Engine.get_Branch;
import static rc.so.util.Engine.insertTR;
import static rc.so.util.Engine.list_branch_completeAFTER311217;
import static rc.so.util.Engine.verifySession;
import static rc.so.util.Excel.excel_reportpending;
import static rc.so.util.Excel.excel_transaction_listEVO;
import static rc.so.util.Excel.excel_transactionnc_list;
import static rc.so.util.Utility.formatArrayValues;
import static rc.so.util.Utility.formatDoubleforMysql;
import static rc.so.util.Utility.formatStringtoStringDate_null;
import static rc.so.util.Utility.generaId;
import static rc.so.util.Utility.getDT;
import static rc.so.util.Utility.getList_request;
import static rc.so.util.Utility.parseString;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.joda.time.DateTime;
import static rc.so.util.Utility.parseArrayValues;
import static rc.so.util.Utility.parseIntR;
import static rc.so.util.Utility.printRequest;
import static rc.so.util.Utility.redirect;
import static rc.so.util.Utility.replace_SU;
import java.io.File;
import static java.lang.Integer.parseInt;
import static java.lang.String.format;
import static java.lang.Thread.currentThread;
import static java.util.Collections.sort;
import java.util.Enumeration;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;
import static org.apache.commons.codec.binary.Base64.decodeBase64;
import static org.joda.time.format.DateTimeFormat.forPattern;
import org.joda.time.format.DateTimeFormatter;
import static rc.so.util.Constant.patternyear;
import rc.so.util.Utility;

/**
 *
 * @author rcosco
 */
public class ReportCentrale extends HttpServlet {

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void DailyCentral(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String d3 = Utility.sanitize(request.getParameter("d3"));
        String d4 = Utility.sanitize(request.getParameter("d4"));
        String Output = Utility.sanitize(request.getParameter("Output"));
        if (null == Output) {
            Output = "EXC";
        } else {
            switch (Output) {
                case "on":
                    Output = "PDF";
                    break;
                default:
                    Output = "EXC";
                    break;
            }
        }

        Db_Master dbm = new Db_Master();
        String path = Utility.sanitize(dbm.getPath("temp"));
        ArrayList<Branch> allenabledbr = dbm.list_branch_completeAFTER311217();
        String branch = Utility.sanitize(request.getParameter("branch"));
        ArrayList<String> br1 = parseString(branch, ",");
        if (br1.isEmpty()) {
            br1 = (ArrayList<String>) allenabledbr.stream().map(valore -> valore.getCod()).distinct().collect(toList());
        }

        DateTime start = getDT(d3, patternnormdate_filter);
        DateTime end = getDT(d4, patternnormdate_filter);

        ArrayList<DateTime> days = new ArrayList<>();
        days.add(start);
        while (start.isBefore(end)) {
            start = start.plusDays(1);
            days.add(start);
        }

        Daily bsi = new Daily();
        ArrayList<Daily_value> listd = new ArrayList<>();

        for (int c = 0; c < br1.size(); c++) {
            String[] fil = { br1.get(c), formatBankBranchReport(br1.get(c), "BR", null, allenabledbr) };
            for (int x = 0; x < days.size(); x++) {
                String date = days.get(x).toString(patternsql);
                String data1 = date + " 00:00";
                String data2 = date + " 23:59";
                Daily_value d = dbm.list_Daily_value(fil, data1, data2, false, false);
                if (d != null) {
                    listd.add(d);
                }
            }
        }
        dbm.closeDB();

        if (Output.equals("PDF")) {

            String base64 = Utility.sanitize(bsi.receipt(path, listd, d3, d4, true));
            if (base64 != null) {
                String headerKey = "Content-Disposition";
                String sanitized_d3 = Utility.sanitize(d3);
                String sanitized_d4 = Utility.sanitize(d4);
                String headerValue = format("attachment; filename=\"%s\"",
                        new Object[] { "Daily_" + sanitized_d3 + "_" + sanitized_d4 + ".pdf" });
                response.setHeader(headerKey, headerValue);
                try (OutputStream outStream = response.getOutputStream()) {
                    outStream.write(decodeBase64(base64.getBytes()));
                }
            } else {
                redirect(request, response, "page_404.html");
            }

        } else {
            String base64 = bsi.receiptexcel(path, listd, d3, d4);

            if (base64 != null) {
                String headerKey = "Content-Disposition";
                String sanitized_d3 = Utility.sanitize(d3);
                String sanitized_d4 = Utility.sanitize(d4);
                String headerValue = format("attachment; filename=\"%s\"",
                        new Object[] { "Daily_" + sanitized_d3 + "_" + sanitized_d4 + ".xlsx" });
                response.setHeader(headerKey, headerValue);
                try (OutputStream outStream = response.getOutputStream()) {
                    outStream.write(decodeBase64(base64.getBytes()));
                }
            } else {
                redirect(request, response, "page_404.html");
            }
        }

        // Daily_value d = dbm.list_Daily_value(fil, data1, data2, false);
    }

    // protected void C_CasherPerformanceR(HttpServletRequest request,
    // HttpServletResponse response) throws ServletException, IOException {
    //
    // }
    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void C_AnalysisDetailsTransactionCertificationExtraCEEforBranchAndPeriodR(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {

        String d3 = request.getParameter("d3");
        String d4 = request.getParameter("d4");
        String data1 = formatStringtoStringDate_null(d3, patternnormdate_filter, patternsql);
        String data2 = formatStringtoStringDate_null(d4, patternnormdate_filter, patternsql);
        String tipor = request.getParameter("tipor");
        String Output = request.getParameter("Output");
        if (null == Output) {
            Output = "EXC";
        } else {
            switch (Output) {
                case "on":
                    Output = "PDF";
                    break;
                default:
                    Output = "EXC";
                    break;
            }
        }
        Db_Master dbm = new Db_Master();
        ArrayList<Branch> allenabledbr = dbm.list_branch_completeAFTER311217();
        String branch = request.getParameter("branch");
        ArrayList<String> br1 = parseString(branch, ",");
        if (br1.isEmpty()) {
            br1 = (ArrayList<String>) allenabledbr.stream().map(valore -> valore.getCod()).distinct().collect(toList());
        }
        String path = dbm.getPath("temp");
        // path = "F:\\com\\";

        dbm.closeDB();

        ArrayList<String> alcolonne = new ArrayList<>();
        //
        alcolonne.add("Branch");
        alcolonne.add("Type");
        alcolonne.add("Transaction");
        alcolonne.add("Date");
        alcolonne.add("User");
        alcolonne.add("Currency");
        alcolonne.add("Kind");
        alcolonne.add("Qty");
        alcolonne.add("Equivalent");
        alcolonne.add("Customer");
        alcolonne.add("Comm");

        if (Output.equals("PDF")) {

            String base64 = Utility
                    .sanitize(new C_AnalysisDetailsTransactionCertificationExtraCEEforBranchAndPeriod().main(path,
                            d3, d4, data1, data2, alcolonne, br1, allenabledbr, tipor));
            if (base64 != null) {
                String headerKey = "Content-Disposition";
                String sanitized_d3 = Utility.sanitize(d3);
                String sanitized_d4 = Utility.sanitize(d4);
                String headerValue = format("attachment; filename=\"%s\"",
                        new Object[] { "C_AnalysisDetailsTransactionCertificationforBranchAndPeriod_" + sanitized_d3
                                + "_" + sanitized_d4 + ".pdf" });
                response.setHeader(headerKey, headerValue);
                try (OutputStream outStream = response.getOutputStream()) {
                    outStream.write(decodeBase64(base64.getBytes()));
                }
            } else {
                redirect(request, response, "page_404.html");
            }

        } else {

            String base64 = Utility
                    .sanitize(new C_AnalysisDetailsTransactionCertificationExtraCEEforBranchAndPeriod().mainexcel(path,
                            d3, d4, data1, data2, alcolonne, br1, allenabledbr, tipor));

            if (base64 != null) {
                String headerKey = "Content-Disposition";
                String sanitized_d3 = Utility.sanitize(d3);
                String sanitized_d4 = Utility.sanitize(d4);
                String headerValue = format("attachment; filename=\"%s\"",
                        new Object[] { "C_AnalysisDetailsTransactionCertificationforBranchAndPeriod_" + sanitized_d3
                                + "_" + sanitized_d4 + ".xls" });
                response.setHeader(headerKey, headerValue);
                try (OutputStream outStream = response.getOutputStream()) {
                    outStream.write(decodeBase64(base64.getBytes()));
                }
            } else {
                redirect(request, response, "page_404.html");
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
    protected void C_AnalysisCurrencyR(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String d3 = request.getParameter("d3");
        String d4 = request.getParameter("d4");

        String Output = request.getParameter("Output");
        if (null == Output) {
            Output = "EXC";
        } else {
            switch (Output) {
                case "on":
                    Output = "PDF";
                    break;
                default:
                    Output = "EXC";
                    break;
            }
        }
        Db_Master dbm = new Db_Master();
        ArrayList<Branch> allenabledbr = dbm.list_branch_completeAFTER311217();
        String branch = request.getParameter("branch");
        ArrayList<String> br1 = parseString(branch, ",");
        if (br1.isEmpty()) {
            br1 = (ArrayList<String>) allenabledbr.stream().map(valore -> valore.getCod()).distinct().collect(toList());
        }
        String path = dbm.getPath("temp");
        // path = "F:\\com\\";
        dbm.closeDB();
        String data1 = formatStringtoStringDate_null(d3, patternnormdate_filter, patternsql);
        String data2 = formatStringtoStringDate_null(d4, patternnormdate_filter, patternsql);
        ArrayList<String> alcolonne = new ArrayList<>();

        alcolonne.add("Currency");
        alcolonne.add("From Branch");
        alcolonne.add("Buy");
        alcolonne.add("From Bank");
        alcolonne.add("Open Close Error");
        alcolonne.add("Total IN");
        alcolonne.add("Amount");
        alcolonne.add("To Branch");
        alcolonne.add("Sell");
        alcolonne.add("To Bank");
        alcolonne.add("Total OUT");
        alcolonne.add("Amount");

        if (Output.equals("PDF")) {
            String base64 = Utility
                    .sanitize(new C_AnalysisCurrency().main(path, d3, d4, data1, data2, alcolonne, br1, allenabledbr));
            if (base64 != null) {
                String headerKey = "Content-Disposition";
                String sanitized_d3 = Utility.sanitize(d3);
                String sanitized_d4 = Utility.sanitize(d4);
                String headerValue = format("attachment; filename=\"%s\"",
                        new Object[] { "C_AnalysisCurrency_" + sanitized_d3 + "_" + sanitized_d4 + ".pdf" });
                response.setHeader(headerKey, headerValue);
                try (OutputStream outStream = response.getOutputStream()) {
                    outStream.write(decodeBase64(base64.getBytes()));
                }
            } else {
                redirect(request, response, "page_404.html");
            }
        } else {

            String base64 = Utility.sanitize(
                    new C_AnalysisCurrency().mainexcel(path, d3, d4, data1, data2, alcolonne, br1, allenabledbr));

            if (base64 != null) {
                String headerKey = "Content-Disposition";
                String sanitized_d3 = Utility.sanitize(d3);
                String sanitized_d4 = Utility.sanitize(d4);
                String headerValue = format("attachment; filename=\"%s\"",
                        new Object[] { "C_AnalysisCurrency_" + sanitized_d3 + "_" + sanitized_d4 + ".xls" });
                response.setHeader(headerKey, headerValue);
                try (OutputStream outStream = response.getOutputStream()) {
                    outStream.write(decodeBase64(base64.getBytes()));
                }
            } else {
                redirect(request, response, "page_404.html");
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
    protected void C_AnalysisReprintNoChangeR(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String d3 = request.getParameter("d3");
        String d4 = request.getParameter("d4");

        String Output = request.getParameter("Output");
        if (null == Output) {
            Output = "EXC";
        } else {
            switch (Output) {
                case "on":
                    Output = "PDF";
                    break;
                default:
                    Output = "EXC";
                    break;
            }
        }
        Db_Master dbm = new Db_Master();
        ArrayList<Branch> allenabledbr = dbm.list_branch_completeAFTER311217();
        String branch = request.getParameter("branch");
        ArrayList<String> br1 = parseString(branch, ",");
        boolean full = false;
        if (br1.isEmpty()) {
            full = true;
            br1 = (ArrayList<String>) allenabledbr.stream().map(valore -> valore.getCod()).distinct().collect(toList());
        }
        String path = dbm.getPath("temp");
        // path = "F:\\com\\";

        dbm.closeDB();
        String data1 = formatStringtoStringDate_null(d3, patternnormdate_filter, patternsql);
        String data2 = formatStringtoStringDate_null(d4, patternnormdate_filter, patternsql);
        ArrayList<String> alcolonne = new ArrayList<>();

        alcolonne.add("Reprint Date");
        alcolonne.add("Transaction Date");
        alcolonne.add("User Code");
        alcolonne.add("User");
        alcolonne.add("Reprint User Code");
        alcolonne.add("Reprint User");
        alcolonne.add("Branch");
        alcolonne.add("Transaction");
        alcolonne.add("Support");
        alcolonne.add("Till");
        alcolonne.add("Causal");
        alcolonne.add("Qty");
        alcolonne.add("Price");
        alcolonne.add("Total");

        if (Output.equals("PDF")) {
            String base64 = Utility
                    .sanitize(new C_AnalysisReprintNoChange().main(path, d3, d4, data1, data2, alcolonne, br1));
            if (base64 != null) {
                String headerKey = "Content-Disposition";
                String sanitized_d3 = Utility.sanitize(d3);
                String sanitized_d4 = Utility.sanitize(d4);
                String headerValue = format("attachment; filename=\"%s\"",
                        new Object[] { "C_AnalysisReprintNoChange_" + sanitized_d3 + "_" + sanitized_d4 + ".pdf" });
                response.setHeader(headerKey, headerValue);
                try (OutputStream outStream = response.getOutputStream()) {
                    outStream.write(decodeBase64(base64.getBytes()));
                }
            } else {
                redirect(request, response, "page_404.html");
            }
        } else {

            String base64 = Utility
                    .sanitize(new C_AnalysisReprintNoChange().mainexcel(path, d3, d4, data1, data2, alcolonne, br1));
            if (base64 != null) {
                String headerKey = "Content-Disposition";
                String sanitized_d3 = Utility.sanitize(d3);
                String sanitized_d4 = Utility.sanitize(d4);
                String headerValue = format("attachment; filename=\"%s\"",
                        new Object[] { "C_AnalysisReprintNoChange_" + sanitized_d3 + "_" + sanitized_d4 + ".xls" });
                response.setHeader(headerKey, headerValue);
                try (OutputStream outStream = response.getOutputStream()) {
                    outStream.write(decodeBase64(base64.getBytes()));
                }
            } else {
                redirect(request, response, "page_404.html");
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
    protected void C_AnalysisReprintR(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String d3 = request.getParameter("d3");
        String d4 = request.getParameter("d4");

        String Output = request.getParameter("Output");
        if (null == Output) {
            Output = "EXC";
        } else {
            switch (Output) {
                case "on":
                    Output = "PDF";
                    break;
                default:
                    Output = "EXC";
                    break;
            }
        }
        Db_Master dbm = new Db_Master();
        String branch = request.getParameter("branch");
        ArrayList<String> br1 = parseString(branch, ",");
        if (br1.isEmpty()) {
            br1 = dbm.list_branchcode_completeAFTER311217();
        }
        ArrayList<StockPrice_value> dati = new ArrayList<>();
        String path = dbm.getPath("temp");
        // path = "F:\\com\\";

        ArrayList<String> alcolonne = new ArrayList<>();
        alcolonne.add("Reprint Date");
        alcolonne.add("Transaction Date");
        alcolonne.add("User Code");
        alcolonne.add("User");
        alcolonne.add("Reprint User Code");
        alcolonne.add("Reprint User");
        alcolonne.add("Branch");
        alcolonne.add("Transaction");
        alcolonne.add("Till");
        alcolonne.add("Type Operation");
        alcolonne.add("Amount");
        alcolonne.add("Commission");
        alcolonne.add("Note");

        dbm.closeDB();
        String data1 = formatStringtoStringDate_null(d3, patternnormdate_filter, patternsql);
        String data2 = formatStringtoStringDate_null(d4, patternnormdate_filter, patternsql);

        // StockPrice sp = new StockPrice();
        // StockPrice_value pdf = new StockPrice_value();
        // pdf.setId_filiale(fil[0]);
        // pdf.setDe_filiale(fil[1]);
        // pdf.setDati(dati);
        if (Output.equals("PDF")) {

            String base64 = Utility.sanitize(new C_AnalysisReprint().main(path, d3, d4, data1, data2, alcolonne, br1));
            if (base64 != null) {
                String headerKey = "Content-Disposition";
                String sanitized_d3 = Utility.sanitize(d3);
                String sanitized_d4 = Utility.sanitize(d4);
                String headerValue = format("attachment; filename=\"%s\"",
                        new Object[] { "C_AnalysisReprint_" + sanitized_d3 + "_" + sanitized_d4 + ".pdf" });
                response.setHeader(headerKey, headerValue);
                try (OutputStream outStream = response.getOutputStream()) {
                    outStream.write(decodeBase64(base64.getBytes()));
                }
            } else {
                redirect(request, response, "page_404.html");
            }

        } else {

            String base64 = Utility
                    .sanitize(new C_AnalysisReprint().mainexcel(path, d3, d4, data1, data2, alcolonne, br1));

            if (base64 != null) {
                String headerKey = "Content-Disposition";
                String sanitized_d3 = Utility.sanitize(d3);
                String sanitized_d4 = Utility.sanitize(d4);
                String headerValue = format("attachment; filename=\"%s\"",
                        new Object[] { "C_AnalysisReprint_" + sanitized_d3 + "_" + sanitized_d4 + ".xls" });
                response.setHeader(headerKey, headerValue);
                try (OutputStream outStream = response.getOutputStream()) {
                    outStream.write(decodeBase64(base64.getBytes()));
                }
            } else {
                redirect(request, response, "page_404.html");
                // }
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
    protected void C_CustomerCareRefundR(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String d3 = request.getParameter("d3");
        String d4 = request.getParameter("d4");

        String Output = request.getParameter("Output");
        if (null == Output) {
            Output = "EXC";
        } else {
            switch (Output) {
                case "on":
                    Output = "PDF";
                    break;
                default:
                    Output = "EXC";
                    break;
            }
        }
        Db_Master dbm = new Db_Master();
        ArrayList<Branch> allbr = dbm.list_branch_completeAFTER311217();
        String branch = request.getParameter("branch");
        ArrayList<String> br1 = parseString(branch, ",");
        boolean full = false;
        if (br1.isEmpty()) {
            full = true;
            br1 = (ArrayList<String>) allbr.stream().map(valore -> valore.getCod()).distinct().collect(toList());
        }

        String path = dbm.getPath("temp");
        // path = "F:\\com\\";

        ArrayList<String> alcolonne = new ArrayList<>();
        alcolonne.add("Refund Date");
        alcolonne.add("Refund User Code");
        alcolonne.add("Transaction Date");
        alcolonne.add("User Code");
        alcolonne.add("Branch");
        alcolonne.add("Transaction");
        alcolonne.add("Type Operation");
        alcolonne.add("Amount");
        alcolonne.add("Commission");
        alcolonne.add("Type Refund");
        alcolonne.add("Refund Method");
        alcolonne.add("Refund Amount");
        alcolonne.add("Authorization code");
        // alcolonne.add("Note");

        dbm.closeDB();
        String data1 = formatStringtoStringDate_null(d3, patternnormdate_filter, patternsql);
        String data2 = formatStringtoStringDate_null(d4, patternnormdate_filter, patternsql);

        if (Output.equals("PDF")) {
            String base64 = Utility
                    .sanitize(new C_CustomerCareRefund().main(path, d3, d4, data1, data2, alcolonne, br1, allbr));
            if (base64 != null) {
                String headerKey = "Content-Disposition";
                String sanitized_d3 = Utility.sanitize(d3);
                String sanitized_d4 = Utility.sanitize(d4);
                String headerValue = format("attachment; filename=\"%s\"",
                        new Object[] { "C_CustomerCareRefund_" + sanitized_d3 + "_" + sanitized_d4 + ".pdf" });
                response.setHeader(headerKey, headerValue);
                try (OutputStream outStream = response.getOutputStream()) {
                    outStream.write(decodeBase64(base64.getBytes()));
                }
            } else {
                redirect(request, response, "page_404.html");
            }

        } else {

            String base64 = Utility
                    .sanitize(new C_CustomerCareRefund().mainexcel(path, d3, d4, data1, data2, alcolonne, br1, allbr));

            if (base64 != null) {
                String headerKey = "Content-Disposition";
                String sanitized_d3 = Utility.sanitize(d3);
                String sanitized_d4 = Utility.sanitize(d4);
                String headerValue = format("attachment; filename=\"%s\"",
                        new Object[] { "C_CustomerCareRefund_" + sanitized_d3 + "_" + sanitized_d4 + ".xls" });
                response.setHeader(headerKey, headerValue);
                try (OutputStream outStream = response.getOutputStream()) {
                    outStream.write(decodeBase64(base64.getBytes()));
                }
            } else {
                redirect(request, response, "page_404.html");
                // }
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
    protected void C_ChangeInternetBookingForBranchesR(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String d3 = request.getParameter("d3");
        String d4 = request.getParameter("d4");

        String data1 = formatStringtoStringDate_null(d3, patternnormdate_filter, patternsql);
        String data2 = formatStringtoStringDate_null(d4, patternnormdate_filter, patternsql);

        String Output = request.getParameter("Output");
        if (null == Output) {
            Output = "EXC";
        } else {
            switch (Output) {
                case "on":
                    Output = "PDF";
                    break;
                default:
                    Output = "EXC";
                    break;
            }
        }
        Db_Master dbm = new Db_Master();
        ArrayList<Branch> allenabledbr = dbm.list_branch_completeAFTER311217();
        String branch = request.getParameter("branch");
        ArrayList<String> br1 = parseString(branch, ",");
        boolean full = false;
        if (br1.isEmpty()) {
            full = true;
            br1 = (ArrayList<String>) allenabledbr.stream().map(valore -> valore.getCod()).distinct().collect(toList());
        }
        String path = dbm.getPath("temp");
        // path = "F:\\com\\";
        dbm.closeDB();

        ArrayList<String> alcolonne = new ArrayList<>();
        alcolonne.add("Transaction");
        alcolonne.add("Date");
        alcolonne.add("User");
        alcolonne.add("Currency");
        alcolonne.add("Support");
        alcolonne.add("Qty");
        alcolonne.add("Amount Local Currency");
        alcolonne.add("%Com.");
        alcolonne.add("Amount Com.");
        alcolonne.add("PAY-IN/PAY-OUT");
        alcolonne.add("Net+Comm");
        alcolonne.add("Spread");
        alcolonne.add("Comm. Fix");
        alcolonne.add("Customer Kind");
        alcolonne.add("Code");
        alcolonne.add("Description");

        if (Output.equals("PDF")) {

            String base64 = Utility.sanitize(new C_ChangeInternetBookingForBranches().main(path, d3, d4, data1, data2,
                    alcolonne, br1, allenabledbr));
            if (base64 != null) {
                String headerKey = "Content-Disposition";
                String sanitized_d3 = Utility.sanitize(d3);
                String sanitized_d4 = Utility.sanitize(d4);
                String headerValue = format("attachment; filename=\"%s\"", new Object[] {
                        "C_ChangeInternetBookingForBranches_" + sanitized_d3 + "_" + sanitized_d4 + ".pdf" });
                response.setHeader(headerKey, headerValue);
                try (OutputStream outStream = response.getOutputStream()) {
                    outStream.write(decodeBase64(base64.getBytes()));
                }
            } else {
                redirect(request, response, "page_404.html");
            }

        } else {

            String base64 = Utility.sanitize(new C_ChangeInternetBookingForBranches().mainexcel(path, d3, d4, data1,
                    data2, alcolonne, br1, allenabledbr));

            if (base64 != null) {
                String headerKey = "Content-Disposition";
                String sanitized_d3 = Utility.sanitize(d3);
                String sanitized_d4 = Utility.sanitize(d4);
                String headerValue = format("attachment; filename=\"%s\"", new Object[] {
                        "C_ChangeInternetBookingForBranches_" + sanitized_d3 + "_" + sanitized_d4 + ".xls" });
                response.setHeader(headerKey, headerValue);
                try (OutputStream outStream = response.getOutputStream()) {
                    outStream.write(decodeBase64(base64.getBytes()));
                }
            } else {
                redirect(request, response, "page_404.html");
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
    protected void C_ChangeMovimentDetailForBranchesR(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String d3 = request.getParameter("d3");
        String d4 = request.getParameter("d4");

        String Output = request.getParameter("Output");
        if (null == Output) {
            Output = "EXC";
        } else {
            switch (Output) {
                case "on":
                    Output = "PDF";
                    break;
                default:
                    Output = "EXC";
                    break;
            }
        }

        String data1 = formatStringtoStringDate_null(d3, patternnormdate_filter, patternsql);
        String data2 = formatStringtoStringDate_null(d4, patternnormdate_filter, patternsql);

        Db_Master dbm = new Db_Master();
        ArrayList<Branch> allenabledbr = dbm.list_branch_completeAFTER311217();
        String branch = request.getParameter("branch");
        ArrayList<String> br1 = parseString(branch, ",");
        boolean full = false;
        if (br1.isEmpty()) {
            full = true;
            br1 = (ArrayList<String>) allenabledbr.stream().map(valore -> valore.getCod()).distinct().collect(toList());
        }
        String path = dbm.getPath("temp");
        // path = "F:\\com\\";
        dbm.closeDB();

        ArrayList<String> alcolonne = new ArrayList<>();
        alcolonne.add("Branch");
        alcolonne.add("Transation");
        alcolonne.add("Date");
        alcolonne.add("User");
        alcolonne.add("Currency");
        alcolonne.add("Kind");
        alcolonne.add("Quantity");
        alcolonne.add("Rate");
        alcolonne.add("Amount");
        alcolonne.add("%Com.");
        alcolonne.add("Comm.");
        alcolonne.add("Pay In/Out");
        alcolonne.add("Buy");
        alcolonne.add("Spread");
        alcolonne.add("Com. Fix");
        alcolonne.add("Customer");
        alcolonne.add("Delete");
        alcolonne.add("Internet Booking");

        if (Output.equals("PDF")) {
            String base64 = Utility.sanitize(new C_ChangeMovimentDetailForBranches().main(path, d3, d4, data1, data2,
                    alcolonne, br1, allenabledbr));

            if (base64 != null) {
                String headerKey = "Content-Disposition";
                String sanitized_d3 = Utility.sanitize(d3);
                String sanitized_d4 = Utility.sanitize(d4);
                String headerValue = format("attachment; filename=\"%s\"", new Object[] {
                        "C_ChangeMovimentDetailForBranches_" + sanitized_d3 + "_" + sanitized_d4 + ".pdf" });
                response.setHeader(headerKey, headerValue);
                try (OutputStream outStream = response.getOutputStream()) {
                    outStream.write(decodeBase64(base64.getBytes()));
                }
            } else {
                redirect(request, response, "page_404.html");
            }
        } else {

            String base64 = Utility.sanitize(new C_ChangeMovimentDetailForBranches().mainexcel(path, d3, d4, data1,
                    data2, alcolonne, br1, allenabledbr));

            if (base64 != null) {
                String headerKey = "Content-Disposition";
                String sanitized_d3 = Utility.sanitize(d3);
                String sanitized_d4 = Utility.sanitize(d4);
                String headerValue = format("attachment; filename=\"%s\"", new Object[] {
                        "C_ChangeMovimentDetailForBranches_" + sanitized_d3 + "_" + sanitized_d4 + ".xls" });
                response.setHeader(headerKey, headerValue);
                try (OutputStream outStream = response.getOutputStream()) {
                    outStream.write(decodeBase64(base64.getBytes()));
                }
            } else {
                redirect(request, response, "page_404.html");
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
    protected void C_CashierDetails(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String d3 = Utility.sanitize(request.getParameter("d3"));
        String d4 = Utility.sanitize(request.getParameter("d4"));
        String data1 = formatStringtoStringDate_null(d3, patternnormdate_filter, patternsql);
        String data2 = formatStringtoStringDate_null(d4, patternnormdate_filter, patternsql);

        Db_Master dbm = new Db_Master();
        ArrayList<Branch> allenabledbr = dbm.list_branch_completeAFTER311217();
        String branch = Utility.sanitize(request.getParameter("branch"));
        ArrayList<String> br1 = parseString(branch, ",");
        boolean full = false;
        if (br1.isEmpty()) {
            full = true;
            br1 = (ArrayList<String>) allenabledbr.stream().map(valore -> valore.getCod()).distinct().collect(toList());
        }

        String path = Utility.sanitize(dbm.getPath("temp"));

        // path = "F:\\com\\";
        dbm.closeDB();

        ArrayList<String> alcolonnetran = new ArrayList<>();
        alcolonnetran.add("Branch");
        alcolonnetran.add("User");
        alcolonnetran.add("Operation");
        alcolonnetran.add("Kind");
        alcolonnetran.add("Total");
        alcolonnetran.add("Ip % Comm.");
        alcolonnetran.add("% Comm.");
        alcolonnetran.add("Tot. Comm.");
        alcolonnetran.add("Round");
        alcolonnetran.add("Fix Comm.");
        alcolonnetran.add("Deleted");
        alcolonnetran.add("Client");

        ArrayList<String> alcolonneopenclose = new ArrayList<>();
        alcolonneopenclose.add("Branch");
        alcolonneopenclose.add("User");
        alcolonneopenclose.add("Operation");
        alcolonneopenclose.add("Total Error");

        String base64 = Utility.sanitize(new C_CashierDetails().mainexcel(path, d3, d4, data1, data2, alcolonnetran,
                alcolonneopenclose, br1, allenabledbr));
        if (base64 != null) {
            String headerKey = "Content-Disposition";
            String sanitized_d3 = Utility.sanitize(d3);
            String sanitized_d4 = Utility.sanitize(d4);
            String headerValue = format("attachment; filename=\"%s\"",
                    new Object[] { "C_CashierDetails_" + sanitized_d3 + "_" + sanitized_d4 + ".xls" });
            response.setHeader(headerKey, headerValue);
            try (OutputStream outStream = response.getOutputStream()) {
                outStream.write(decodeBase64(base64.getBytes()));
            }
        } else {
            redirect(request, response, "page_404.html");
        }

    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void C_CashierPerformance(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String d3 = request.getParameter("d3");
        String d4 = request.getParameter("d4");
        String bss = request.getParameter("bss");

        String data1 = formatStringtoStringDate_null(d3, patternnormdate_filter, patternsql);
        String data2 = formatStringtoStringDate_null(d4, patternnormdate_filter, patternsql);

        String Output = request.getParameter("Output");
        if (null == Output) {
            Output = "EXC";
        } else {
            switch (Output) {
                case "on":
                    Output = "PDF";
                    break;
                default:
                    Output = "EXC";
                    break;
            }
        }
        Db_Master dbm = new Db_Master();
        ArrayList<Branch> allenabledbr = dbm.list_branch_completeAFTER311217();
        String branch = request.getParameter("branch");
        ArrayList<String> br1 = parseString(branch, ",");

        boolean full = false;
        if (br1.isEmpty()) {
            full = true;
            br1 = (ArrayList<String>) allenabledbr.stream().map(valore -> valore.getCod()).distinct().collect(toList());
        }

        String path = dbm.getPath("temp");

        ArrayList<String[]> fasce = dbm.list_fasce_cashier_perf(bss, null);

        // path = "F:\\com\\";
        dbm.closeDB();

        ArrayList<String> alcolonne = new ArrayList<>();
        alcolonne.add("User");
        alcolonne.add("0%");
        if (bss.equals("BS")) {
            alcolonne.add("NEG");
        } else {
            for (int i = 0; i < fasce.size(); i++) {
                alcolonne.add(fasce.get(i)[1] + "-" + fasce.get(i)[2]);
            }
        }
        alcolonne.add("Full");
        alcolonne.add("#Trans.");
        alcolonne.add("NFF");
        alcolonne.add("DEL");
        alcolonne.add("Volume");
        alcolonne.add("Com+Fix");
        alcolonne.add("%Media");
        alcolonne.add("Val.Medio");
        alcolonne.add("Com.Media");
        alcolonne.add("#ERR");
        alcolonne.add("tot. ERR");

        if (Output.equals("PDF")) {

            String base64 = Utility.sanitize(new C_CashierPerformance().main(path, d3, d4, data1, data2, bss, fasce,
                    alcolonne, br1, allenabledbr));
            if (base64 != null) {
                String headerKey = "Content-Disposition";
                String sanitized_d3 = Utility.sanitize(d3);
                String sanitized_d4 = Utility.sanitize(d4);
                String headerValue = format("attachment; filename=\"%s\"",
                        new Object[] { "C_CashierPerformance_" + sanitized_d3 + "_" + sanitized_d4 + ".pdf" });
                response.setHeader(headerKey, headerValue);
                try (OutputStream outStream = response.getOutputStream()) {
                    outStream.write(decodeBase64(base64.getBytes()));
                }
            } else {
                redirect(request, response, "page_404.html");
            }
        } else {

            String base64 = Utility.sanitize(new C_CashierPerformance().mainexcel(path, d3, d4, data1, data2, bss,
                    fasce, alcolonne, br1, allenabledbr));

            if (base64 != null) {
                String headerKey = "Content-Disposition";
                String sanitized_d3 = Utility.sanitize(d3);
                String sanitized_d4 = Utility.sanitize(d4);
                String headerValue = format("attachment; filename=\"%s\"",
                        new Object[] { "C_CashierPerformance_" + sanitized_d3 + "_" + sanitized_d4 + ".xls" });
                response.setHeader(headerKey, headerValue);
                try (OutputStream outStream = response.getOutputStream()) {
                    outStream.write(decodeBase64(base64.getBytes()));
                }
            } else {
                redirect(request, response, "page_404.html");
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
    protected void C_ChangeMovimentForAgencyR(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String d3 = Utility.sanitize(request.getParameter("d3"));
        String d4 = Utility.sanitize(request.getParameter("d4"));
        String data1 = formatStringtoStringDate_null(d3, patternnormdate_filter, patternsql);
        String data2 = formatStringtoStringDate_null(d4, patternnormdate_filter, patternsql);

        String Output = request.getParameter("Output");
        if (null == Output) {
            Output = "EXC";
        } else {
            switch (Output) {
                case "on":
                    Output = "PDF";
                    break;
                default:
                    Output = "EXC";
                    break;
            }
        }
        Db_Master dbm = new Db_Master();
        ArrayList<Branch> allenabledbr = dbm.list_branch_completeAFTER311217();
        String branch = Utility.sanitize(request.getParameter("branch"));
        ArrayList<String> br1 = parseString(branch, ",");
        boolean full = false;
        if (br1.isEmpty()) {
            full = true;
            br1 = (ArrayList<String>) allenabledbr.stream().map(valore -> valore.getCod()).distinct().collect(toList());
        }

        String path = dbm.getPath("temp");
        // path = "F:\\com\\";

        dbm.closeDB();

        ArrayList<String> alcolonne = new ArrayList<>();

        alcolonne.add("Branch");
        alcolonne.add("Transation");
        alcolonne.add("Date");
        alcolonne.add("User");
        alcolonne.add("Currency");
        alcolonne.add("Kind");
        alcolonne.add("Quantity");
        alcolonne.add("Amount");
        alcolonne.add("%Com.");
        alcolonne.add("Comm.");
        alcolonne.add("Pay In/Out");
        alcolonne.add("Buy");
        alcolonne.add("Spread");
        alcolonne.add("Com. Fix");
        alcolonne.add("Customer");
        alcolonne.add("Delete");

        if (Output.equals("PDF")) {

            String base64 = Utility.sanitize(
                    new C_ChangeMovimentForAgency().main(path, d3, d4, data1, data2, alcolonne, br1, allenabledbr));
            if (base64 != null) {
                String headerKey = "Content-Disposition";
                String sanitized_d3 = Utility.sanitize(d3);
                String sanitized_d4 = Utility.sanitize(d4);
                String headerValue = format("attachment; filename=\"%s\"",
                        new Object[] { "C_ChangeMovimentForAgency_" + sanitized_d3 + "_" + sanitized_d4 + ".pdf" });
                response.setHeader(headerKey, headerValue);
                try (OutputStream outStream = response.getOutputStream()) {
                    outStream.write(decodeBase64(base64.getBytes()));
                }
            } else {
                redirect(request, response, "page_404.html");
            }
        } else {

            String base64 = Utility.sanitize(new C_ChangeMovimentForAgency().mainexcel(path, d3, d4, data1, data2,
                    alcolonne, br1, allenabledbr));

            if (base64 != null) {
                String headerKey = "Content-Disposition";
                String sanitized_d3 = Utility.sanitize(d3);
                String sanitized_d4 = Utility.sanitize(d4);
                String headerValue = format("attachment; filename=\"%s\"",
                        new Object[] { "C_ChangeMovimentForAgency_" + sanitized_d3 + "_" + sanitized_d4 + ".xls" });
                response.setHeader(headerKey, headerValue);
                try (OutputStream outStream = response.getOutputStream()) {
                    outStream.write(decodeBase64(base64.getBytes()));
                }
            } else {
                redirect(request, response, "page_404.html");
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
    protected void C_ChangeMovimentForBranchesR(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String d3 = Utility.sanitize(request.getParameter("d3"));
        String d4 = Utility.sanitize(request.getParameter("d4"));

        String Output = Utility.sanitize(request.getParameter("Output"));
        if (null == Output) {
            Output = "EXC";
        } else {
            switch (Output) {
                case "on":
                    Output = "PDF";
                    break;
                default:
                    Output = "EXC";
                    break;
            }
        }
        Db_Master dbm = new Db_Master();
        ArrayList<Branch> allenabledbr = dbm.list_branch_completeAFTER311217();
        String branch = Utility.sanitize(request.getParameter("branch"));
        ArrayList<String> br1 = parseString(branch, ",");
        boolean full = false;
        if (br1.isEmpty()) {
            full = true;
            br1 = (ArrayList<String>) allenabledbr.stream().map(valore -> valore.getCod()).distinct().collect(toList());
        }
        String path = Utility.sanitize(dbm.getPath("temp"));
        // path = "F:\\com\\";
        dbm.closeDB();

        String data1 = formatStringtoStringDate_null(d3, patternnormdate_filter, patternsql);
        String data2 = formatStringtoStringDate_null(d4, patternnormdate_filter, patternsql);

        ArrayList<String> alcolonne = new ArrayList<>();

        alcolonne.add("Currency");
        alcolonne.add("Kind");
        alcolonne.add("Quantity");
        alcolonne.add("Amount");
        alcolonne.add("% Average Total Comm");
        alcolonne.add("Total Amount Comm.");
        alcolonne.add("Pay In/Out");
        alcolonne.add("Buy");
        alcolonne.add("Spread");
        alcolonne.add("Comm. Fix");

        if (Output.equals("PDF")) {

            String base64 = Utility.sanitize(
                    new C_ChangeMovimentForBranches().main(path, d3, d4, data1, data2, alcolonne, br1, allenabledbr));
            if (base64 != null) {
                String headerKey = "Content-Disposition";
                String sanitized_d3 = Utility.sanitize(d3);
                String sanitized_d4 = Utility.sanitize(d4);
                String headerValue = format("attachment; filename=\"%s\"",
                        new Object[] { "C_ChangeMovimentForBranches_" + sanitized_d3 + "_" + sanitized_d4 + ".pdf" });
                response.setHeader(headerKey, headerValue);
                try (OutputStream outStream = response.getOutputStream()) {
                    outStream.write(decodeBase64(base64.getBytes()));
                }
            } else {
                redirect(request, response, "page_404.html");
            }

        } else {

            String base64 = Utility.sanitize(new C_ChangeMovimentForBranches().mainexcel(path, d3, d4, data1, data2,
                    alcolonne, br1, allenabledbr));
            if (base64 != null) {
                String headerKey = "Content-Disposition";
                String sanitized_d3 = Utility.sanitize(d3);
                String sanitized_d4 = Utility.sanitize(d4);
                String headerValue = format("attachment; filename=\"%s\"",
                        new Object[] { "C_ChangeMovimentForBranches_" + sanitized_d3 + "_" + sanitized_d4 + ".xls" });
                response.setHeader(headerKey, headerValue);
                try (OutputStream outStream = response.getOutputStream()) {
                    outStream.write(decodeBase64(base64.getBytes()));
                }
            } else {
                redirect(request, response, "page_404.html");
                // }
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
    protected void C_ChangeVolumeAffairR(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String d3 = Utility.sanitize(request.getParameter("d3"));
        String d4 = Utility.sanitize(request.getParameter("d4"));

        String Output = Utility.sanitize(request.getParameter("Output"));
        if (null == Output) {
            Output = "EXC";
        } else {
            switch (Output) {
                case "on":
                    Output = "PDF";
                    break;
                default:
                    Output = "EXC";
                    break;
            }
        }

        String Sho = Utility.sanitize(request.getParameter("Show"));
        if (null == Sho) {
            Sho = "N";
        } else {
            switch (Sho) {
                case "on":
                    Sho = "Y";
                    break;
                default:
                    Sho = "N";
                    break;
            }
        }
        String Summ = Utility.sanitize(request.getParameter("Summ"));
        if (null == Summ) {
            Summ = "N";
        } else {
            switch (Summ) {
                case "on":
                    Summ = "Y";
                    break;
                default:
                    Summ = "N";
                    break;
            }
        }
        boolean checkTrans = Sho.equals("Y");
        boolean riepilogo = Summ.equals("Y");
        String data1 = formatStringtoStringDate_null(d3, patternnormdate_filter, patternsql);
        String data2 = formatStringtoStringDate_null(d4, patternnormdate_filter, patternsql);

        Db_Master dbm = new Db_Master();
        ArrayList<Branch> allenabledbr = dbm.list_branch_completeAFTER311217();
        String branch = Utility.sanitize(request.getParameter("branch"));
        ArrayList<String> br1 = parseString(branch, ",");
        boolean full = false;
        if (br1.isEmpty()) {
            full = true;
            br1 = (ArrayList<String>) allenabledbr.stream().map(valore -> valore.getCod()).distinct().collect(toList());
        }

        ArrayList<String> alcolonne = new ArrayList<>();

        if (checkTrans) {
            alcolonne.add("Branch");
            alcolonne.add("Date");
            alcolonne.add("Buy");
            alcolonne.add("Sell");
            alcolonne.add("Total");
            alcolonne.add("No. Transaction");
        } else {
            alcolonne.add("Branch");
            alcolonne.add("Date");
            alcolonne.add("Buy");
            alcolonne.add("Sell");
            alcolonne.add("Total");
        }

        ArrayList<String> alcolonneriepilogo = new ArrayList<>();

        if (checkTrans) {
            alcolonneriepilogo.add("Branch");
            alcolonneriepilogo.add("");
            alcolonneriepilogo.add("Buy");
            alcolonneriepilogo.add("Sell");
            alcolonneriepilogo.add("Total");
            alcolonneriepilogo.add("No. Transaction");
        } else {
            alcolonneriepilogo.add("Branch");
            alcolonneriepilogo.add("");
            alcolonneriepilogo.add("Buy");
            alcolonneriepilogo.add("Sell");
            alcolonneriepilogo.add("Total");
        }

        String path = Utility.sanitize(dbm.getPath("temp"));
        // path = "F:\\com\\";
        dbm.closeDB();
        if (Output.equals("PDF")) {
            String base64 = Utility.sanitize(new C_ChangeVolumeAffair().main(path, d3, d4, data1, data2, alcolonne,
                    alcolonneriepilogo, br1, checkTrans, riepilogo, allenabledbr));
            if (base64 != null) {
                String headerKey = "Content-Disposition";
                String sanitized_d3 = Utility.sanitize(d3);
                String sanitized_d4 = Utility.sanitize(d4);
                String headerValue = format("attachment; filename=\"%s\"",
                        new Object[] { "C_ChangeVolumeAffair_" + sanitized_d3 + "_" + sanitized_d4 + ".pdf" });
                response.setHeader(headerKey, headerValue);
                try (OutputStream outStream = response.getOutputStream()) {
                    outStream.write(decodeBase64(base64.getBytes()));
                }
            } else {
                redirect(request, response, "page_404.html");
            }
        } else {

            String base64 = Utility.sanitize(new C_ChangeVolumeAffair().mainexcel(path, d3, d4, data1, data2, alcolonne,
                    alcolonneriepilogo, br1, checkTrans, riepilogo, allenabledbr));

            if (base64 != null) {
                String headerKey = "Content-Disposition";
                String sanitized_d3 = Utility.sanitize(d3);
                String sanitized_d4 = Utility.sanitize(d4);
                String headerValue = format("attachment; filename=\"%s\"",
                        new Object[] { "C_ChangeVolumeAffair_" + sanitized_d3 + "_" + sanitized_d4 + ".xls" });
                response.setHeader(headerKey, headerValue);
                try (OutputStream outStream = response.getOutputStream()) {
                    outStream.write(decodeBase64(base64.getBytes()));
                }
            } else {
                redirect(request, response, "page_404.html");
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
    protected void C_ChangeVolumeAffairCashAdvanceR(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String d3 = Utility.sanitize(request.getParameter("d3"));
        String d4 = Utility.sanitize(request.getParameter("d4"));

        String Output = Utility.sanitize(request.getParameter("Output"));
        if (null == Output) {
            Output = "EXC";
        } else {
            switch (Output) {
                case "on":
                    Output = "PDF";
                    break;
                default:
                    Output = "EXC";
                    break;
            }
        }
        String Sho = Utility.sanitize(request.getParameter("Show"));
        if (null == Sho) {
            Sho = "N";
        } else {
            switch (Sho) {
                case "on":
                    Sho = "Y";
                    break;
                default:
                    Sho = "N";
                    break;
            }
        }
        String Summ = Utility.sanitize(request.getParameter("Summ"));
        if (null == Summ) {
            Summ = "N";
        } else {
            switch (Summ) {
                case "on":
                    Summ = "Y";
                    break;
                default:
                    Summ = "N";
                    break;
            }
        }
        boolean checkTrans = Sho.equals("Y");
        boolean riepilogo = Summ.equals("Y");
        String data1 = formatStringtoStringDate_null(d3, patternnormdate_filter, patternsql);
        String data2 = formatStringtoStringDate_null(d4, patternnormdate_filter, patternsql);

        Db_Master dbm = new Db_Master();
        ArrayList<Branch> allenabledbr = dbm.list_branch_completeAFTER311217();
        String branch = Utility.sanitize(request.getParameter("branch"));
        ArrayList<String> br1 = parseString(branch, ",");
        boolean full = false;
        if (br1.isEmpty()) {
            full = true;
            br1 = (ArrayList<String>) allenabledbr.stream().map(valore -> valore.getCod()).distinct().collect(toList());
        }

        String path = Utility.sanitize(dbm.getPath("temp"));
        // path = "F:\\com\\";

        dbm.closeDB();

        ArrayList<String> alcolonne = new ArrayList<>();

        if (checkTrans) {
            alcolonne.add("Branch");
            alcolonne.add("Date");
            alcolonne.add("Buy");
            alcolonne.add("Sell");
            alcolonne.add("Total");
            alcolonne.add("Cash Advance Commission");
            alcolonne.add("No. Transaction");
        } else {
            alcolonne.add("Branch");
            alcolonne.add("Date");
            alcolonne.add("Buy");
            alcolonne.add("Sell");
            alcolonne.add("Total");
            alcolonne.add("Cash Advance Commission");
        }

        ArrayList<String> alcolonneriepilogo = new ArrayList<>();

        if (checkTrans) {
            alcolonneriepilogo.add("Branch");
            alcolonneriepilogo.add("");
            alcolonneriepilogo.add("Buy");
            alcolonneriepilogo.add("Sell");
            alcolonneriepilogo.add("Total");
            alcolonneriepilogo.add("Cash Advance Commission");
            alcolonneriepilogo.add("No. Transaction");
        } else {
            alcolonneriepilogo.add("Branch");
            alcolonneriepilogo.add("");
            alcolonneriepilogo.add("Buy");
            alcolonneriepilogo.add("Sell");
            alcolonneriepilogo.add("Total");
            alcolonneriepilogo.add("Cash Advance Commission");
        }

        if (Output.equals("PDF")) {
            String base64 = Utility
                    .sanitize(new C_ChangeVolumeAffairCashAdvance().main(path, d3, d4, data1, data2, alcolonne,
                            alcolonneriepilogo, br1, checkTrans, riepilogo, allenabledbr));
            if (base64 != null) {
                String headerKey = "Content-Disposition";
                String sanitized_d3 = Utility.sanitize(d3);
                String sanitized_d4 = Utility.sanitize(d4);
                String headerValue = format("attachment; filename=\"%s\"", new Object[] {
                        "C_ChangeVolumeAffairCashAdvance_" + sanitized_d3 + "_" + sanitized_d4 + ".pdf" });
                response.setHeader(headerKey, headerValue);
                try (OutputStream outStream = response.getOutputStream()) {
                    outStream.write(decodeBase64(base64.getBytes()));
                }
            } else {
                redirect(request, response, "page_404.html");
            }

        } else {

            String base64 = Utility.sanitize(new C_ChangeVolumeAffairCashAdvance().mainexcel(path, d3, d4, data1, data2,
                    alcolonne, alcolonneriepilogo, br1, checkTrans, riepilogo, allenabledbr));

            if (base64 != null) {
                String headerKey = "Content-Disposition";
                String sanitized_d3 = Utility.sanitize(d3);
                String sanitized_d4 = Utility.sanitize(d4);
                String headerValue = format("attachment; filename=\"%s\"", new Object[] {
                        "C_ChangeVolumeAffairCashAdvance_" + sanitized_d3 + "_" + sanitized_d4 + ".xls" });
                response.setHeader(headerKey, headerValue);
                try (OutputStream outStream = response.getOutputStream()) {
                    outStream.write(decodeBase64(base64.getBytes()));
                }
            } else {
                redirect(request, response, "page_404.html");
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
    protected void C_CloseBranchR(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String d3 = request.getParameter("d3");
        // String d4 = request.getParameter("d4");

        String data1 = formatStringtoStringDate_null(d3, patternnormdate_filter, patternsql);
        // String data2 = formatStringtoStringDate_null(d4, patternnormdate_filter,
        // patternsql);

        // ArrayList<String> dates = new ArrayList<>();
        // if(data1!=null&&data2!=null){
        // dates = periodic(data1,data2, patternsql);
        // }
        String Output = request.getParameter("Output");
        if (null == Output) {
            Output = "EXC";
        } else {
            switch (Output) {
                case "on":
                    Output = "PDF";
                    break;
                default:
                    Output = "EXC";
                    break;
            }
        }
        Db_Master dbm = new Db_Master();
        ArrayList<Branch> allenabledbr = dbm.list_branch_completeAFTER311217();
        String branch = request.getParameter("branch");
        ArrayList<String> br1 = parseString(branch, ",");
        boolean full = false;
        if (br1.isEmpty()) {
            full = true;
            br1 = (ArrayList<String>) allenabledbr.stream().map(valore -> valore.getCod()).distinct().collect(toList());
        }
        String path = dbm.getPath("temp");
        // path = "F:\\com\\";

        ArrayList<C_CloseBranch_value> dati = dbm.list_C_CloseBranch_value(data1, br1, allenabledbr);

        dbm.closeDB();
        if (!dati.isEmpty()) {
            C_CloseBranch nctl = new C_CloseBranch();

            C_CloseBranch_value pdf = new C_CloseBranch_value();

            pdf.setData_da(d3);
            pdf.setData_a(d3);
            pdf.setDati(dati);
            if (Output.equals("PDF")) {

                String base64 = Utility.sanitize(nctl.receipt(path, pdf));
                if (base64 != null) {
                    String headerKey = "Content-Disposition";
                    String sanitized_d3 = Utility.sanitize(d3);
                    String headerValue = format("attachment; filename=\"%s\"",
                            new Object[] { "C_CloseBranch_" + sanitized_d3 + ".pdf" });
                    response.setHeader(headerKey, headerValue);
                    try (OutputStream outStream = response.getOutputStream()) {
                        outStream.write(decodeBase64(base64.getBytes()));
                    }
                } else {
                    redirect(request, response, "page_404.html");
                }
            } else {
                //
                String base64 = Utility.sanitize(nctl.receiptexcel(path, pdf));

                if (base64 != null) {
                    String headerKey = "Content-Disposition";
                    String sanitized_d3 = Utility.sanitize(d3);
                    String headerValue = format("attachment; filename=\"%s\"",
                            new Object[] { "C_CloseBranch_" + sanitized_d3 + ".xls" });
                    response.setHeader(headerKey, headerValue);
                    try (OutputStream outStream = response.getOutputStream()) {
                        outStream.write(decodeBase64(base64.getBytes()));
                    }
                } else {
                    redirect(request, response, "page_404.html");
                }

            }
        } else {
            redirect(request, response, "page_404.html");
        }

    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void C_InterbranchR(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String d3 = request.getParameter("d3");
        String d4 = request.getParameter("d4");
        String data1 = formatStringtoStringDate_null(d3, patternnormdate_filter, patternsql);
        String data2 = formatStringtoStringDate_null(d4, patternnormdate_filter, patternsql);

        String curr = request.getParameter("curr");
        String[] state = request.getParameterValues("state");
        ArrayList<String> state_1 = new ArrayList<>();
        String diff = request.getParameter("diff");

        if (state == null) {
            state_1.add("OK");
            state_1.add("KOF");
            state_1.add("KOT");
        } else {
            state_1 = formatArrayValues(state);
        }

        String Output = request.getParameter("Output");
        if (null == Output) {
            Output = "EXC";
        } else {
            switch (Output) {
                case "on":
                    Output = "PDF";
                    break;
                default:
                    Output = "EXC";
                    break;
            }
        }
        Db_Master dbm = new Db_Master();
        String branch = request.getParameter("branch");
        ArrayList<String> br1 = parseString(branch, ",");
        if (br1.isEmpty()) {
            br1 = dbm.list_branchcode_completeAFTER311217();
        }
        String path = dbm.getPath("temp");
        // path = "F:\\com\\";
        ArrayList<C_Interbranch_value> dati = dbm.list_C_Interbranch_value(data1, data2, br1, curr, state_1, diff);
        dbm.closeDB();
        C_Interbranch_value pdf = new C_Interbranch_value();
        pdf.setDataDa(d3);
        pdf.setDataA(d4);
        pdf.setDati_2(dati);
        if (dati.size() > 0) {

            ArrayList<String> alcolonne = new ArrayList<>();
            alcolonne.add("State");
            alcolonne.add("Date To");
            alcolonne.add("To transf. No.");
            alcolonne.add("Branch To");
            alcolonne.add("Amount To");
            alcolonne.add("Date From");
            alcolonne.add("Branch From");
            alcolonne.add("From Transf. No.");
            alcolonne.add("Amount From");
            alcolonne.add("Difference");
            if (Output.equals("PDF")) {
                String base64 = Utility.sanitize(new C_Interbranch().main(path, pdf, alcolonne, diff.equals("01")));
                if (base64 != null) {
                    String headerKey = "Content-Disposition";
                    String sanitized_d3 = Utility.sanitize(d3);
                    String sanitized_d4 = Utility.sanitize(d4);
                    String headerValue = format("attachment; filename=\"%s\"",
                            new Object[] { "C_Interbranch_" + sanitized_d3 + "_" + sanitized_d4 + ".pdf" });
                    response.setHeader(headerKey, headerValue);
                    try (OutputStream outStream = response.getOutputStream()) {
                        outStream.write(decodeBase64(base64.getBytes()));
                    }
                } else {
                    redirect(request, response, "page_404.html");
                }

            } else {
                String base64 = Utility
                        .sanitize(new C_Interbranch().mainexcel(path, pdf, alcolonne, diff.equals("01")));
                if (base64 != null) {
                    String headerKey = "Content-Disposition";
                    String sanitized_d3 = Utility.sanitize(d3);
                    String sanitized_d4 = Utility.sanitize(d4);
                    String headerValue = format("attachment; filename=\"%s\"",
                            new Object[] { "C_Interbranch_" + sanitized_d3 + "_" + sanitized_d4 + ".xls" });
                    response.setHeader(headerKey, headerValue);
                    try (OutputStream outStream = response.getOutputStream()) {
                        outStream.write(decodeBase64(base64.getBytes()));
                    }
                } else {
                    redirect(request, response, "page_404.html");
                }
            }

        } else {

            redirect(request, response, "page_404.html");
        }

    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void C_InterbranchDetailsR(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String d3 = request.getParameter("d3");
        String d4 = request.getParameter("d4");
        String data1 = formatStringtoStringDate_null(d3, patternnormdate_filter, patternsql);
        String data2 = formatStringtoStringDate_null(d4, patternnormdate_filter, patternsql);

        String curr = request.getParameter("curr");
        String[] state = request.getParameterValues("state");
        ArrayList<String> state_1 = new ArrayList<>();
        String diff = request.getParameter("diff");

        if (state == null) {
            state_1.add("OK");
            state_1.add("KOF");
            state_1.add("KOT");
        } else {
            state_1 = formatArrayValues(state);
        }

        String Output = request.getParameter("Output");
        if (null == Output) {
            Output = "EXC";
        } else {
            switch (Output) {
                case "on":
                    Output = "PDF";
                    break;
                default:
                    Output = "EXC";
                    break;
            }
        }
        Db_Master dbm = new Db_Master();
        String branch = request.getParameter("branch");
        ArrayList<String> br1 = parseString(branch, ",");
        if (br1.isEmpty()) {
            br1 = dbm.list_branchcode_completeAFTER311217();
        }
        String path = dbm.getPath("temp");
        // path = "F:\\com\\";
        ArrayList<C_Interbranch_value> dati = dbm.list_C_InterbranchDetails_value(data1, data2, br1, curr, state_1,
                diff);
        dbm.closeDB();

        if (dati.size() > 0) {
            C_Interbranch_value pdf = new C_Interbranch_value();
            pdf.setDataDa(d3);
            pdf.setDataA(d4);
            pdf.setDati_2(dati);
            ArrayList<String> alcolonne = new ArrayList<>();
            alcolonne.add("State");
            alcolonne.add("Date To");
            alcolonne.add("To transf. No.");
            alcolonne.add("Branch To");
            alcolonne.add("Amount To");
            alcolonne.add("Date From");
            alcolonne.add("From Transf. No.");
            alcolonne.add("Branch From");
            alcolonne.add("Amount From");
            alcolonne.add("Difference");

            if (Output.equals("PDF")) {

                String base64 = Utility
                        .sanitize(new C_InterbranchDetails().main(path, pdf, alcolonne, diff.equals("01")));
                if (base64 != null) {
                    String headerKey = "Content-Disposition";
                    String sanitized_d3 = Utility.sanitize(d3);
                    String sanitized_d4 = Utility.sanitize(d4);
                    String headerValue = format("attachment; filename=\"%s\"",
                            new Object[] { "C_InterbranchDetails_" + sanitized_d3 + "_" + sanitized_d4 + ".pdf" });
                    response.setHeader(headerKey, headerValue);
                    try (OutputStream outStream = response.getOutputStream()) {
                        outStream.write(decodeBase64(base64.getBytes()));
                    }
                } else {
                    redirect(request, response, "page_404.html");
                }

            } else {

                String base64 = Utility
                        .sanitize(new C_InterbranchDetails().mainexcel(path, pdf, alcolonne, diff.equals("01")));

                if (base64 != null) {
                    String headerKey = "Content-Disposition";
                    String sanitized_d3 = Utility.sanitize(d3);
                    String sanitized_d4 = Utility.sanitize(d4);
                    String headerValue = format("attachment; filename=\"%s\"",
                            new Object[] { "C_InterbranchDetails_" + sanitized_d3 + "_" + sanitized_d4 + ".xls" });
                    response.setHeader(headerKey, headerValue);
                    try (OutputStream outStream = response.getOutputStream()) {
                        outStream.write(decodeBase64(base64.getBytes()));
                    }
                } else {
                    redirect(request, response, "page_404.html");
                }
            }
        } else {
            redirect(request, response, "page_404.html");
        }
    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void C_NoChangeMovimentForBranchesR(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String d3 = request.getParameter("d3");
        String d4 = request.getParameter("d4");

        String Output = request.getParameter("Output");
        if (null == Output) {
            Output = "EXC";
        } else {
            switch (Output) {
                case "on":
                    Output = "PDF";
                    break;
                default:
                    Output = "EXC";
                    break;
            }
        }
        Db_Master dbm = new Db_Master();
        ArrayList<Branch> allenabledbr = dbm.list_branch_completeAFTER311217();
        String branch = request.getParameter("branch");
        ArrayList<String> br1 = parseString(branch, ",");
        boolean full = false;
        if (br1.isEmpty()) {
            full = true;
            br1 = (ArrayList<String>) allenabledbr.stream().map(valore -> valore.getCod()).distinct().collect(toList());
        }
        String path = dbm.getPath("temp");
        // path = "F:\\com\\";

        String data1 = formatStringtoStringDate_null(d3, patternnormdate_filter, patternsql);
        String data2 = formatStringtoStringDate_null(d4, patternnormdate_filter, patternsql);

        dbm.closeDB();
        ArrayList<String> alcolonne = new ArrayList<>();
        alcolonne.add("Category");
        alcolonne.add("Causal");
        alcolonne.add("No. Trans.");
        alcolonne.add("Quantity");
        alcolonne.add("Total");

        if (Output.equals("PDF")) {

            String base64 = Utility.sanitize(
                    new C_NoChangeMovimentForBranches().main(path, d3, d4, data1, data2, alcolonne, br1, allenabledbr));
            if (base64 != null) {
                String headerKey = "Content-Disposition";
                String sanitized_d3 = Utility.sanitize(d3);
                String sanitized_d4 = Utility.sanitize(d4);
                String headerValue = format("attachment; filename=\"%s\"",
                        new Object[] { "C_NoChangeMovimentForBranches_" + sanitized_d3 + "_" + sanitized_d4 + ".pdf" });
                response.setHeader(headerKey, headerValue);
                try (OutputStream outStream = response.getOutputStream()) {
                    outStream.write(decodeBase64(base64.getBytes()));
                }
            } else {
                redirect(request, response, "page_404.html");
            }

        } else {

            String base64 = Utility.sanitize(new C_NoChangeMovimentForBranches().mainexcel(path, d3, d4, data1, data2,
                    alcolonne, br1, allenabledbr));

            if (base64 != null) {
                String headerKey = "Content-Disposition";
                String sanitized_d3 = Utility.sanitize(d3);
                String sanitized_d4 = Utility.sanitize(d4);
                String headerValue = format("attachment; filename=\"%s\"",
                        new Object[] { "C_NoChangeMovimentForBranches_" + sanitized_d3 + "_" + sanitized_d4 + ".xls" });
                response.setHeader(headerKey, headerValue);
                try (OutputStream outStream = response.getOutputStream()) {
                    outStream.write(decodeBase64(base64.getBytes()));
                }
            } else {
                redirect(request, response, "page_404.html");
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
    protected void C_SizeAndQuantityR(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String d3 = request.getParameter("d3");
        String data1 = formatStringtoStringDate_null(d3, patternnormdate_filter, patternsql);
        String curr = request.getParameter("curr");
        String Output = request.getParameter("Output");
        if (null == Output) {
            Output = "EXC";
        } else {
            switch (Output) {
                case "on":
                    Output = "PDF";
                    break;
                default:
                    Output = "EXC";
                    break;
            }
        }
        Db_Master dbm = new Db_Master();
        ArrayList<Branch> allenabledbr = dbm.list_branch_completeAFTER311217();
        String branch = request.getParameter("branch");
        ArrayList<String> br1 = parseString(branch, ",");
        boolean full = false;
        if (br1.isEmpty()) {
            full = true;
            br1 = (ArrayList<String>) allenabledbr.stream().map(valore -> valore.getCod()).distinct().collect(toList());
        }
        String path = dbm.getPath("temp");
        dbm.closeDB();

        ArrayList<String> alcolonne = new ArrayList<>();//
        alcolonne.add("Kind");
        alcolonne.add("Size");
        alcolonne.add("Quantity");
        alcolonne.add("Total");

        if (Output.equals("PDF")) {

            String base64 = Utility
                    .sanitize(new C_SizeAndQuantity().main(path, d3, data1, alcolonne, br1, allenabledbr, curr));
            if (base64 != null) {
                String headerKey = "Content-Disposition";
                String sanitized_d3 = Utility.sanitize(d3);
                String headerValue = format("attachment; filename=\"%s\"",
                        new Object[] { "C_SizeAndQuantity_" + sanitized_d3 + ".pdf" });
                response.setHeader(headerKey, headerValue);
                try (OutputStream outStream = response.getOutputStream()) {
                    outStream.write(decodeBase64(base64.getBytes()));
                }
            } else {
                redirect(request, response, "page_404.html");
            }
        } else {

            String base64 = Utility
                    .sanitize(new C_SizeAndQuantity().mainexcel(path, d3, data1, alcolonne, br1, allenabledbr, curr));

            if (base64 != null) {
                String headerKey = "Content-Disposition";
                String sanitized_d3 = Utility.sanitize(d3);
                String headerValue = format("attachment; filename=\"%s\"",
                        new Object[] { "C_SizeAndQuantity_" + sanitized_d3 + ".xls" });
                response.setHeader(headerKey, headerValue);
                try (OutputStream outStream = response.getOutputStream()) {
                    outStream.write(decodeBase64(base64.getBytes()));
                }
            } else {
                redirect(request, response, "page_404.html");
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
    protected void C_StockInquiryR(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String d3 = request.getParameter("d3");
        String d4 = request.getParameter("d4");
        String dt_tot = d3 + " " + d4;

        String data1 = formatStringtoStringDate_null(dt_tot, patternnormdate, patternsqldate);
        String Output = request.getParameter("Output");
        if (null == Output) {
            Output = "EXC";
        } else {
            switch (Output) {
                case "on":
                    Output = "PDF";
                    break;
                default:
                    Output = "EXC";
                    break;
            }
        }

        Db_Master dbm = new Db_Master();
        ArrayList<Branch> allenabledbr = dbm.list_branch_completeAFTER311217();
        String branch = request.getParameter("branch");
        ArrayList<String> br1 = parseString(branch, ",");
        boolean full = false;
        if (br1.isEmpty()) {
            full = true;
            br1 = (ArrayList<String>) allenabledbr.stream().map(valore -> valore.getCod()).distinct().collect(toList());
        }
        String path = dbm.getPath("temp");
        // path = "F:\\com\\";
        dbm.closeDB();
        C_StockInquiry nctl = new C_StockInquiry();
        ArrayList<String> alcolonne = new ArrayList<>();
        alcolonne.add("Till/Safe");
        alcolonne.add("Currency");
        alcolonne.add("Notes");
        alcolonne.add("Euro Travel Cheques");
        alcolonne.add("Travel Cheques");

        ArrayList<String[]> filiali = new ArrayList<>();
        for (int i = 0; i < br1.size(); i++) {
            String[] a = { br1.get(i), formatBankBranch(br1.get(i), "BR", null, allenabledbr, null) };
            filiali.add(a);
        }

        if (Output.equals("PDF")) {
            String base64 = Utility.sanitize(nctl.main(path, data1, d3, alcolonne, filiali));
            if (base64 != null) {
                String headerKey = "Content-Disposition";
                String sanitized_dt_tot = Utility.sanitize(dt_tot);
                String headerValue = format("attachment; filename=\"%s\"",
                        new Object[] { "C_StockInquiry_" + sanitized_dt_tot + ".pdf" });
                response.setHeader(headerKey, headerValue);
                try (OutputStream outStream = response.getOutputStream()) {
                    outStream.write(decodeBase64(base64.getBytes()));
                }
            } else {
                redirect(request, response, "page_404.html");
            }
        } else {

            String base64 = Utility.sanitize(nctl.mainexcel(path, data1, d3, alcolonne, filiali));

            if (base64 != null) {
                String headerKey = "Content-Disposition";
                String sanitized_dt_tot = Utility.sanitize(dt_tot);
                String headerValue = format("attachment; filename=\"%s\"",
                        new Object[] { "C_StockInquiry_" + sanitized_dt_tot + ".xls" });
                response.setHeader(headerKey, headerValue);
                try (OutputStream outStream = response.getOutputStream()) {
                    outStream.write(decodeBase64(base64.getBytes()));
                }
            } else {
                redirect(request, response, "page_404.html");
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
    protected void C_WesternUnionR(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //
        Db_Master dbm = new Db_Master();
        ArrayList<Branch> allenabledbr = dbm.list_branch_completeAFTER311217();
        String d3 = request.getParameter("d3");
        String d4 = request.getParameter("d4");
        String branch = request.getParameter("branch");
        ArrayList<String> br1 = parseString(branch, ",");
        boolean full = false;
        if (br1.isEmpty()) {
            full = true;
            br1 = (ArrayList<String>) allenabledbr.stream().map(valore -> valore.getCod()).distinct().collect(toList());
        }

        String data1 = formatStringtoStringDate_null(d3, patternnormdate_filter, patternsql);
        String data2 = formatStringtoStringDate_null(d4, patternnormdate_filter, patternsql);
        String[] nc_cat1 = request.getParameterValues("nc_cat1");
        String list_nc_cat = "";
        if (nc_cat1 == null) {
            ArrayList<NC_category> array_nc_cat = dbm.list_nc_category_enabled();
            for (int i = 0; i < array_nc_cat.size(); i++) {
                if (array_nc_cat.get(i).getFg_tipo_transazione_nc().equals("1")) {
                    list_nc_cat = list_nc_cat + array_nc_cat.get(i).getGruppo_nc() + ";";
                }
            }
        } else {
            String totallist = parseArrayValues(nc_cat1);
            if (totallist.contains("...")) {
                ArrayList<NC_category> array_nc_cat = dbm.list_nc_category_enabled();
                for (int i = 0; i < array_nc_cat.size(); i++) {
                    if (array_nc_cat.get(i).getFg_tipo_transazione_nc().equals("1")) {
                        list_nc_cat = list_nc_cat + array_nc_cat.get(i).getGruppo_nc() + ";";
                    }
                }
            } else {
                list_nc_cat = parseArrayValues(nc_cat1);
            }
        }

        String Output = request.getParameter("Output");
        if (null == Output) {
            Output = "EXC";
        } else {
            switch (Output) {
                case "on":
                    Output = "PDF";
                    break;
                default:
                    Output = "EXC";
                    break;
            }
        }

        String path = dbm.getPath("temp");
        // path = "F:\\com\\";
        ArrayList<C_WesternUnion_value> dati = dbm.list_C_WesternUnion_value(data1, data2, br1, list_nc_cat);
        dbm.closeDB();
        if (dati.size() > 0) {
            C_WesternUnion nctl = new C_WesternUnion();
            ArrayList<String> alcolonne = new ArrayList<>();
            alcolonne.add("Date");
            alcolonne.add("To Send");
            alcolonne.add("N. Trans.");
            alcolonne.add("To Receive");
            alcolonne.add("N. Trans");
            alcolonne.add("W.U. Cash Balance");
            C_WesternUnion_value pdf = new C_WesternUnion_value();
            pdf.setFull(full);
            pdf.setDataDa(d3);
            pdf.setDataA(d4);
            pdf.setDati(dati);

            ArrayList<String[]> filiali = new ArrayList<>();

            for (int i = 0; i < br1.size(); i++) {
                String[] a = { br1.get(i), formatBankBranch(br1.get(i), "BR", null, allenabledbr, null) };
                filiali.add(a);
            }

            if (Output.equals("PDF")) {

                String base64 = Utility.sanitize(nctl.receipt(path, pdf, alcolonne, filiali));
                if (base64 != null) {
                    String headerKey = "Content-Disposition";
                    String sanitized_d3 = Utility.sanitize(d3);
                    String sanitized_d4 = Utility.sanitize(d4);
                    String headerValue = format("attachment; filename=\"%s\"",
                            new Object[] { "C_WesternUnion_" + sanitized_d3 + "_" + sanitized_d4 + ".pdf" });
                    response.setHeader(headerKey, headerValue);
                    try (OutputStream outStream = response.getOutputStream()) {
                        outStream.write(decodeBase64(base64.getBytes()));
                    }
                } else {
                    redirect(request, response, "page_404.html");
                }
            } else {

                String base64 = Utility.sanitize(nctl.receiptexcel(path, pdf, alcolonne, filiali));

                if (base64 != null) {
                    String headerKey = "Content-Disposition";
                    String sanitized_d3 = Utility.sanitize(d3);
                    String sanitized_d4 = Utility.sanitize(d4);
                    String headerValue = format("attachment; filename=\"%s\"",
                            new Object[] { "C_WesternUnion_" + sanitized_d3 + "_" + sanitized_d4 + ".xls" });
                    response.setHeader(headerKey, headerValue);
                    try (OutputStream outStream = response.getOutputStream()) {
                        outStream.write(decodeBase64(base64.getBytes()));
                    }
                } else {
                    redirect(request, response, "page_404.html");
                }
            }
        } else {
            redirect(request, response, "page_404.html");
        }
    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void C_CasherOpenCloseErrorR(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String d3 = request.getParameter("d3");
        String d4 = request.getParameter("d4");

        String data1 = formatStringtoStringDate_null(d3, patternnormdate_filter, patternsql);
        String data2 = formatStringtoStringDate_null(d4, patternnormdate_filter, patternsql);

        String Output = request.getParameter("Output");
        if (null == Output) {
            Output = "EXC";
        } else {
            switch (Output) {
                case "on":
                    Output = "PDF";
                    break;
                default:
                    Output = "EXC";
                    break;
            }
        }

        if (Output.equals("EXC")) {
            C_OpenCloseError(request, response, true);
        } else {
            Db_Master dbm = new Db_Master();
            ArrayList<Branch> allenabledbr = dbm.list_branch_completeAFTER311217();
            String branch = request.getParameter("branch");
            ArrayList<String> br1 = parseString(branch, ",");
            boolean full = false;
            if (br1.isEmpty()) {
                full = true;
                br1 = (ArrayList<String>) allenabledbr.stream().map(valore -> valore.getCod()).distinct()
                        .collect(toList());
            }
            String path = dbm.getPath("temp");
            // path = "F:\\com\\";
            ArrayList<Openclose> list = dbm.list_openclose_errors_report(br1, data1, data2);
            dbm.closeDB();
            if (list.size() > 0) {
                Openclose_Error osp = new Openclose_Error();
                ArrayList<Openclose_Error_value> pdfl = new ArrayList<>();
                Openclose_Error_value pdf = new Openclose_Error_value();

                ArrayList<String> alcolonne = new ArrayList<>();
                alcolonne.add("User");
                alcolonne.add("Safe/Till");
                alcolonne.add("Date");
                alcolonne.add("Operation");
                alcolonne.add("Type");
                alcolonne.add("Branch");

                ArrayList<String> alcolonnedetaglierrore = new ArrayList<>();
                alcolonnedetaglierrore.add("Currency");
                alcolonnedetaglierrore.add("Kind");
                alcolonnedetaglierrore.add("Quantity User");
                alcolonnedetaglierrore.add("Quantity System");
                alcolonnedetaglierrore.add("Quantity Difference");
                alcolonnedetaglierrore.add("Rate");
                if (is_IT) {
                    alcolonnedetaglierrore.add("Difference (Euro)");
                } else if (is_CZ) {
                    alcolonnedetaglierrore.add("Difference (CZK)");
                } else if (is_UK) {
                    alcolonnedetaglierrore.add("Difference (GBP)");
                }
                alcolonnedetaglierrore.add("Note");

                ArrayList<String> alcolonnedetaglierroreNoChange = new ArrayList<>();
                alcolonnedetaglierroreNoChange.add("Category");
                alcolonnedetaglierroreNoChange.add("Quantity User");
                alcolonnedetaglierroreNoChange.add("Quantity System");
                alcolonnedetaglierroreNoChange.add("Diff");
                alcolonnedetaglierroreNoChange.add("Amount");
                alcolonnedetaglierroreNoChange.add("Note");

                ArrayList<String> alcolonnedetaglierrorePos = new ArrayList<>();
                alcolonnedetaglierrorePos.add("POS/Bank Account");
                alcolonnedetaglierrorePos.add("Quantity User");
                alcolonnedetaglierrorePos.add("Amount User");
                alcolonnedetaglierrorePos.add("Quantity System");
                alcolonnedetaglierrorePos.add("Amount System");
                alcolonnedetaglierrorePos.add("Quantity Diff");
                alcolonnedetaglierrorePos.add("Amount Diff");
                alcolonnedetaglierrorePos.add("Note");

                ArrayList<Openclose_Error_value_stock> datifilialeperreport = new ArrayList<>();
                Db_Master dbm1 = new Db_Master();
                for (int i = 0; i < list.size(); i++) {
                    Openclose co = list.get(i);
                    ArrayList<String[]> list_oc_errors = dbm1.list_oc_errors(co.getCod());
                    ArrayList<Openclose_Error_value> dati = dbm1.list_openclose_errors_report(co, list_oc_errors,
                            allenabledbr, null);
                    if (dati.size() > 0) {
                        Openclose_Error_value_stock dpf = new Openclose_Error_value_stock(dati, co.getId());
                        datifilialeperreport.add(dpf);
                    }
                }
                dbm1.closeDB();
                pdf.setDati(datifilialeperreport);
                pdfl.add(pdf);
                String base64 = Utility.sanitize(osp.receipt(path, pdfl, alcolonne, alcolonnedetaglierrore,
                        alcolonnedetaglierroreNoChange, alcolonnedetaglierrorePos, d3, d4, true));
                if (base64 != null) {
                    String headerKey = "Content-Disposition";
                    String sanitized_d3 = Utility.sanitize(d3);
                    String sanitized_d4 = Utility.sanitize(d4);
                    String headerValue = format("attachment; filename=\"%s\"",
                            new Object[] { "C_CasherOpenCloseError_" + sanitized_d3 + "_" + sanitized_d4 + ".pdf" });
                    response.setHeader(headerKey, headerValue);
                    try (OutputStream outStream = response.getOutputStream()) {
                        outStream.write(decodeBase64(base64.getBytes()));
                    }
                } else {
                    redirect(request, response, "page_404.html");
                }
            } else {
                redirect(request, response, "page_404.html");
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
    protected void C_TransactionRegisterDetail(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String d3 = request.getParameter("d3");
        String d4 = request.getParameter("d4");
        String spage = request.getParameter("spage");

        try {
            int p = parseInt(spage);
        } catch (NumberFormatException e) {
            spage = "0";
        }

        String srow = request.getParameter("srow");

        try {
            int p = parseInt(srow);
        } catch (NumberFormatException e) {
            srow = "0";
        }

        String data1 = formatStringtoStringDate_null(d3, patternnormdate_filter, patternsql);
        String data2 = formatStringtoStringDate_null(d4, patternnormdate_filter, patternsql);

        String Output = request.getParameter("Output");
        if (null == Output) {
            Output = "EXC";
        } else {
            switch (Output) {
                case "on":
                    Output = "PDF";
                    break;
                default:
                    Output = "EXC";
                    break;
            }
        }
        Db_Master dbm = new Db_Master();
        ArrayList<Branch> allenabledbr = dbm.list_branch_completeAFTER311217();
        String branch = request.getParameter("branch");
        ArrayList<String> br1 = parseString(branch, ",");
        if (br1.isEmpty()) {
            br1 = (ArrayList<String>) allenabledbr.stream().map(valore -> valore.getCod()).distinct().collect(toList());
        }
        String path = dbm.getPath("temp");
        // path = "F:\\com\\";

        ArrayList<String> alcolonne = new ArrayList<>();
        alcolonne.add("Progr.");
        alcolonne.add("Filiale");
        alcolonne.add("Till");
        alcolonne.add("User");
        alcolonne.add("Date");
        alcolonne.add("Cur");
        alcolonne.add("Kind Description");
        alcolonne.add("Amount Qty");
        alcolonne.add("Rate");
        alcolonne.add("Total");
        alcolonne.add("%");
        alcolonne.add("Comm.Fee");
        alcolonne.add("Round Off");
        alcolonne.add("Pay In/Out");

        C_TransactionRegisterDetail_value pdf = new C_TransactionRegisterDetail_value();

        ArrayList<C_TransactionRegisterDetail_value> dati = dbm.list_C_TransactionRegisterDetail_value(data1, data2,
                br1);
        pdf.setDataDa(d3);
        pdf.setDataA(d4);
        pdf.setDati(dati);

        dbm.closeDB();

        if (dati.size() > 0) {

            if (Output.equals("PDF")) {
                String base64 = Utility.sanitize(new C_TransactionRegisterDetail().receipt_2022(path, pdf, alcolonne,
                        parseIntR(srow), parseIntR(spage)));
                if (base64 != null) {
                    String headerKey = "Content-Disposition";
                    String sanitized_d3 = Utility.sanitize(d3);
                    String sanitized_d4 = Utility.sanitize(d4);
                    String headerValue = format("attachment; filename=\"%s\"",
                            new Object[] { "TransactionRegisterDetail_" + sanitized_d3 + "_" + sanitized_d4 + ".pdf" });
                    response.setHeader(headerKey, headerValue);
                    try (OutputStream outStream = response.getOutputStream()) {
                        outStream.write(decodeBase64(base64.getBytes()));
                    }
                } else {
                    redirect(request, response, "page_404.html");
                }
            } else {

                String base64 = Utility.sanitize(new C_TransactionRegisterDetail().receiptexcel(path, pdf, alcolonne,
                        parseInt(srow), parseInt(spage)));

                if (base64 != null) {
                    String headerKey = "Content-Disposition";
                    String sanitized_d3 = Utility.sanitize(d3);
                    String sanitized_d4 = Utility.sanitize(d4);
                    String headerValue = format("attachment; filename=\"%s\"", new Object[] {
                            "TransactionRegisterDetail_" + sanitized_d3 + "_" + sanitized_d4 + ".xlsx" });
                    response.setHeader(headerKey, headerValue);
                    try (OutputStream outStream = response.getOutputStream()) {
                        outStream.write(decodeBase64(base64.getBytes()));
                    }
                } else {
                    redirect(request, response, "page_404.html");
                }
            }
        } else {
            redirect(request, response, "page_404.html");
        }

    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void C_TransactionRegisterSummary(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String d3 = request.getParameter("d3");
        String d4 = request.getParameter("d4");
        String spage = request.getParameter("spage");

        try {
            int p = parseInt(spage);
        } catch (NumberFormatException e) {
            spage = "0";
        }

        String data1 = formatStringtoStringDate_null(d3, patternnormdate_filter, patternsql);
        String data2 = formatStringtoStringDate_null(d4, patternnormdate_filter, patternsql);

        String Output = request.getParameter("Output");
        if (null == Output) {
            Output = "EXC";
        } else {
            switch (Output) {
                case "on":
                    Output = "PDF";
                    break;
                default:
                    Output = "EXC";
                    break;
            }
        }
        Db_Master dbm = new Db_Master();
        ArrayList<Branch> allenabledbr = dbm.list_branch_completeAFTER311217();
        String branch = request.getParameter("branch");
        ArrayList<String> br1 = parseString(branch, ",");
        if (br1.isEmpty()) {
            br1 = (ArrayList<String>) allenabledbr.stream().map(valore -> valore.getCod()).distinct().collect(toList());
        }
        String path = dbm.getPath("temp");
        // path = "F:\\com\\";

        ArrayList<String> alcolonne = new ArrayList<>();//
        alcolonne.add("Num");
        alcolonne.add("Tot");
        alcolonne.add("Comm");
        alcolonne.add("Num");
        alcolonne.add("Tot");
        alcolonne.add("Comm");
        alcolonne.add("Num");
        alcolonne.add("Tot");
        alcolonne.add("Comm");
        alcolonne.add("No");
        alcolonne.add("Amount");
        alcolonne.add("No");
        alcolonne.add("Amount");
        alcolonne.add("No");
        alcolonne.add("Amount");
        alcolonne.add("No");
        alcolonne.add("Amount");
        alcolonne.add("No");
        alcolonne.add("Amount");
        alcolonne.add("No");
        alcolonne.add("Amount");
        alcolonne.add("No");
        alcolonne.add("Amount");
        alcolonne.add("No");
        alcolonne.add("Amount");
        alcolonne.add("No");
        alcolonne.add("Amount");
        alcolonne.add("No");
        alcolonne.add("Amount");
        alcolonne.add("No");
        alcolonne.add("Amount");

        C_TransactionRegisterSummary_value pdf = new C_TransactionRegisterSummary_value();
        ArrayList<C_TransactionRegisterSummary_value> dati = dbm.list_C_TransactionRegisterSummary_value(data1, data2,
                br1, allenabledbr);
        pdf.setDataDa(d3);
        pdf.setDataA(d4);
        pdf.setDati(dati);

        dbm.closeDB();
        if (Output.equals("PDF")) {

            String base64 = Utility
                    .sanitize(new C_TransactionRegisterSummary().receipt(path, pdf, alcolonne, parseInt(spage)));
            if (base64 != null) {
                String headerKey = "Content-Disposition";
                String sanitized_d3 = Utility.sanitize(d3);
                String sanitized_d4 = Utility.sanitize(d4);
                String headerValue = format("attachment; filename=\"%s\"",
                        new Object[] { "TransactionRegisterSummary_" + sanitized_d3 + "_" + sanitized_d4 + ".pdf" });
                response.setHeader(headerKey, headerValue);
                try (OutputStream outStream = response.getOutputStream()) {
                    outStream.write(decodeBase64(base64.getBytes()));
                }
            } else {
                redirect(request, response, "page_404.html");
            }
        } else {

            String base64 = new C_TransactionRegisterSummary().receiptexcel(path, pdf, alcolonne, parseInt(spage));

            if (base64 != null) {
                String headerKey = "Content-Disposition";
                String sanitized_d3 = Utility.sanitize(d3);
                String sanitized_d4 = Utility.sanitize(d4);
                String headerValue = format("attachment; filename=\"%s\"",
                        new Object[] { "TransactionRegisterSummary_" + sanitized_d3 + "_" + sanitized_d4 + ".xls" });
                response.setHeader(headerKey, headerValue);
                try (OutputStream outStream = response.getOutputStream()) {
                    outStream.write(decodeBase64(base64.getBytes()));
                }
            } else {
                redirect(request, response, "page_404.html");
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
    protected void C_FreeTax_BranchCurrency(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String d3 = request.getParameter("d3");
        String d4 = request.getParameter("d4");

        String Output = request.getParameter("Output");
        if (null == Output) {
            Output = "EXC";
        } else {
            switch (Output) {
                case "on":
                    Output = "PDF";
                    break;
                default:
                    Output = "EXC";
                    break;
            }
        }
        Db_Master dbm = new Db_Master();
        ArrayList<Branch> allenabledbr = dbm.list_branch_completeAFTER311217();
        String branch = request.getParameter("branch");
        ArrayList<String> br1 = parseString(branch, ",");
        if (br1.isEmpty()) {
            br1 = (ArrayList<String>) allenabledbr.stream().map(valore -> valore.getCod()).distinct().collect(toList());
        }
        String path = dbm.getPath("temp");
        // path = "F:\\com\\";
        String data1 = formatStringtoStringDate_null(d3, patternnormdate_filter, patternsql);
        String data2 = formatStringtoStringDate_null(d4, patternnormdate_filter, patternsql);
        dbm.closeDB();

        ArrayList<String> alcolonne = new ArrayList<>();

        alcolonne.add("");
        alcolonne.add("");

        alcolonne.add(new DateTime().minusYears(1).toString(patternyear));
        alcolonne.add(new DateTime().toString(patternyear));
        alcolonne.add("+/- Val");
        alcolonne.add("+/-%");

        alcolonne.add(new DateTime().minusYears(1).toString(patternyear));
        alcolonne.add(new DateTime().toString(patternyear));
        alcolonne.add("+/- Val");
        alcolonne.add("+/-%");

        if (Output.equals("PDF")) {
            String base64 = Utility.sanitize(new C_FreeTax_BranchCurrency().main(
                    path, d3, d4, data1, data2,
                    alcolonne, br1, allenabledbr));
            if (base64 != null) {
                String headerKey = "Content-Disposition";
                String sanitized_d3 = Utility.sanitize(d3);
                String sanitized_d4 = Utility.sanitize(d4);
                String headerValue = format("attachment; filename=\"%s\"",
                        new Object[] { "C_VatRefund_BranchCurrency_" + sanitized_d3 + "_" + sanitized_d4 + ".pdf" });
                response.setHeader(headerKey, headerValue);
                try (OutputStream outStream = response.getOutputStream()) {
                    outStream.write(decodeBase64(base64.getBytes()));
                }
            } else {
                redirect(request, response, "page_404.html");
            }
        } else {
            String base64 = Utility.sanitize(new C_FreeTax_BranchCurrency().mainexcel(
                    path, d3, d4, data1, data2,
                    alcolonne, br1, allenabledbr));

            if (base64 != null) {
                String headerKey = "Content-Disposition";
                String sanitized_d3 = Utility.sanitize(d3);
                String sanitized_d4 = Utility.sanitize(d4);
                String headerValue = format("attachment; filename=\"%s\"",
                        new Object[] { "C_VatRefund_BranchCurrency_" + sanitized_d3 + "_" + sanitized_d4 + ".xls" });
                response.setHeader(headerKey, headerValue);
                try (OutputStream outStream = response.getOutputStream()) {
                    outStream.write(decodeBase64(base64.getBytes()));
                }
            } else {
                redirect(request, response, "page_404.html");
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
    protected void C_FreeTax_CausalBranch(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String d3 = request.getParameter("d3");
        String d4 = request.getParameter("d4");

        String Output = request.getParameter("Output");
        if (null == Output) {
            Output = "EXC";
        } else {
            switch (Output) {
                case "on":
                    Output = "PDF";
                    break;
                default:
                    Output = "EXC";
                    break;
            }
        }
        Db_Master dbm = new Db_Master();
        ArrayList<Branch> allenabledbr = dbm.list_branch_completeAFTER311217();
        ArrayList<NC_causal> freetaxlist = dbm.list_nc_causal_enabled_freetax();
        String branch = request.getParameter("branch");
        ArrayList<String> br1 = parseString(branch, ",");
        if (br1.isEmpty()) {
            br1 = (ArrayList<String>) allenabledbr.stream().map(valore -> valore.getCod()).distinct().collect(toList());
        }
        String path = dbm.getPath("temp");
        // path = "F:\\com\\";
        String data1 = formatStringtoStringDate_null(d3, patternnormdate_filter, patternsql);
        String data2 = formatStringtoStringDate_null(d4, patternnormdate_filter, patternsql);
        dbm.closeDB();
        ArrayList<String> alcolonne = new ArrayList<>();
        alcolonne.add("");
        alcolonne.add("");
        alcolonne.add(new DateTime().minusYears(1).toString(patternyear));
        alcolonne.add(new DateTime().toString(patternyear));
        alcolonne.add("+/- Val");
        alcolonne.add("+/-%");
        alcolonne.add(new DateTime().minusYears(1).toString(patternyear));
        alcolonne.add(new DateTime().toString(patternyear));
        alcolonne.add("+/- Val");
        alcolonne.add("+/-%");

        if (Output.equals("PDF")) {
            String base64 = Utility.sanitize(new C_FreeTax_CausalBranch().main(
                    path, d3, d4, data1, data2,
                    alcolonne, br1, allenabledbr, freetaxlist));
            if (base64 != null) {
                String headerKey = "Content-Disposition";
                String sanitized_d3 = Utility.sanitize(d3);
                String sanitized_d4 = Utility.sanitize(d4);
                String headerValue = format("attachment; filename=\"%s\"",
                        new Object[] { "C_VatRefund_CausalBranch_" + sanitized_d3 + "_" + sanitized_d4 + ".pdf" });
                response.setHeader(headerKey, headerValue);
                try (OutputStream outStream = response.getOutputStream()) {
                    outStream.write(decodeBase64(base64.getBytes()));
                }
            } else {
                redirect(request, response, "page_404.html");
            }
        } else {
            String base64 = Utility.sanitize(new C_FreeTax_CausalBranch().mainexcel(
                    path, d3, d4, data1, data2,
                    alcolonne, br1, allenabledbr, freetaxlist));

            if (base64 != null) {
                String headerKey = "Content-Disposition";
                String sanitized_d3 = Utility.sanitize(d3);
                String sanitized_d4 = Utility.sanitize(d4);
                String headerValue = format("attachment; filename=\"%s\"",
                        new Object[] { "C_VatRefund_CausalBranch_" + sanitized_d3 + "_" + sanitized_d4 + ".xls" });
                response.setHeader(headerKey, headerValue);
                try (OutputStream outStream = response.getOutputStream()) {
                    outStream.write(decodeBase64(base64.getBytes()));
                }
            } else {
                redirect(request, response, "page_404.html");
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
    protected void C_FreeTax_BranchCausal(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String d3 = request.getParameter("d3");
        String d4 = request.getParameter("d4");

        String Output = request.getParameter("Output");
        if (null == Output) {
            Output = "EXC";
        } else {
            switch (Output) {
                case "on":
                    Output = "PDF";
                    break;
                default:
                    Output = "EXC";
                    break;
            }
        }
        Db_Master dbm = new Db_Master();
        ArrayList<Branch> allenabledbr = dbm.list_branch_completeAFTER311217();

        String branch = request.getParameter("branch");
        ArrayList<String> br1 = parseString(branch, ",");
        if (br1.isEmpty()) {
            br1 = (ArrayList<String>) allenabledbr.stream().map(valore -> valore.getCod()).distinct().collect(toList());
        }
        String path = dbm.getPath("temp");
        // path = "F:\\com\\";
        String data1 = formatStringtoStringDate_null(d3, patternnormdate_filter, patternsql);
        String data2 = formatStringtoStringDate_null(d4, patternnormdate_filter, patternsql);
        dbm.closeDB();

        ArrayList<String> alcolonne = new ArrayList<>();
        alcolonne.add("");
        alcolonne.add("");
        alcolonne.add(new DateTime().minusYears(1).toString(patternyear));
        alcolonne.add(new DateTime().toString(patternyear));
        alcolonne.add("+/- Val");
        alcolonne.add("+/-%");
        alcolonne.add(new DateTime().minusYears(1).toString(patternyear));
        alcolonne.add(new DateTime().toString(patternyear));
        alcolonne.add("+/- Val");
        alcolonne.add("+/-%");
        alcolonne.add(new DateTime().minusYears(1).toString(patternyear));
        alcolonne.add(new DateTime().toString(patternyear));
        alcolonne.add("+/- Val");
        alcolonne.add("+/-%");

        if (Output.equals("PDF")) {
            String base64 = Utility.sanitize(new C_FreeTax_BranchCausal().main(
                    path, d3, d4, data1, data2,
                    alcolonne, br1, allenabledbr));
            if (base64 != null) {
                String headerKey = "Content-Disposition";
                String sanitized_d3 = Utility.sanitize(d3);
                String sanitized_d4 = Utility.sanitize(d4);
                String headerValue = format("attachment; filename=\"%s\"",
                        new Object[] { "C_VatRefund_BranchCausal_" + sanitized_d3 + "_" + sanitized_d4 + ".pdf" });
                response.setHeader(headerKey, headerValue);
                try (OutputStream outStream = response.getOutputStream()) {
                    outStream.write(decodeBase64(base64.getBytes()));
                }
            } else {
                redirect(request, response, "page_404.html");
            }
        } else {
            String base64 = Utility.sanitize(new C_FreeTax_BranchCausal().mainexcel(
                    path, d3, d4, data1, data2,
                    alcolonne, br1, allenabledbr));

            if (base64 != null) {
                String headerKey = "Content-Disposition";
                String sanitized_d3 = Utility.sanitize(d3);
                String sanitized_d4 = Utility.sanitize(d4);
                String headerValue = format("attachment; filename=\"%s\"",
                        new Object[] { "C_VatRefund_BranchCausal_" + sanitized_d3 + "_" + sanitized_d4 + ".xls" });
                response.setHeader(headerKey, headerValue);
                try (OutputStream outStream = response.getOutputStream()) {
                    outStream.write(decodeBase64(base64.getBytes()));
                }
            } else {
                redirect(request, response, "page_404.html");
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
    protected void C_freeTaxPivotTotale(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String d3 = request.getParameter("d3");
        String d4 = request.getParameter("d4");

        Db_Master dbm = new Db_Master();
        ArrayList<Branch> allenabledbr = dbm.list_branch_completeAFTER311217();
        ArrayList<NC_causal> ftena = dbm.list_nc_causal_enabled_freetax();
        String branch = request.getParameter("branch");
        ArrayList<String> br1 = parseString(branch, ",");
        if (br1.isEmpty()) {
            br1 = (ArrayList<String>) allenabledbr.stream().map(valore -> valore.getCod()).distinct().collect(toList());
        }
        String path = dbm.getPath("temp");
        // path = "F:\\com\\";
        String data1 = formatStringtoStringDate_null(d3, patternnormdate_filter, patternsql);
        String data2 = formatStringtoStringDate_null(d4, patternnormdate_filter, patternsql);

        DateTimeFormatter formatter = forPattern(patternsql);

        String year1 = formatter.parseDateTime(data1).year().getAsShortText();
        String year2 = formatter.parseDateTime(data2).minusYears(1).year().getAsShortText();

        C_freeTaxPivotTotale_value cft = new C_freeTaxPivotTotale_value();
        ArrayList<C_freeTaxPivotTotale_value> dati = dbm.list_C_freeTaxPivotTotale_value(br1, data1, data2,
                allenabledbr, ftena);
        cft.setDataDa(d3);
        cft.setDataA(d4);
        cft.setDati(dati);
        dbm.closeDB();

        ArrayList<String> alcolonneanni = new ArrayList<>();
        ArrayList<String> alcolonne = new ArrayList<>();
        ArrayList<String> alcolonneetic = new ArrayList<>();
        alcolonneetic.add("Branch");
        alcolonneetic.add("");

        for (int i = 0; i < ftena.size(); i++) {
            NC_causal nc = ftena.get(i);
            alcolonne.add(nc.getCausale_nc() + " " + nc.getDe_causale_nc());
            alcolonneanni.add(year1);
            alcolonneanni.add(year2);
            alcolonneetic.add("Amount");
            alcolonneetic.add("Forms Quantity");
            alcolonneetic.add("Amount");
            alcolonneetic.add("Forms Quantity");
        }

        if (!dati.isEmpty()) {
            String base64 = Utility.sanitize(
                    new C_freeTaxPivotTotale().receiptexcel(path, cft, alcolonne, alcolonneanni, alcolonneetic));

            if (base64 != null) {
                String headerKey = "Content-Disposition";
                String sanitized_d3 = Utility.sanitize(d3);
                String sanitized_d4 = Utility.sanitize(d4);
                String headerValue = format("attachment; filename=\"%s\"",
                        new Object[] { "C_VatRefundPivotTotale_" + sanitized_d3 + "_" + sanitized_d4 + ".xlsx" });
                response.setHeader(headerKey, headerValue);
                try (OutputStream outStream = response.getOutputStream()) {
                    outStream.write(decodeBase64(base64.getBytes()));
                }
            } else {
                redirect(request, response, "page_404.html");
            }
        } else {
            redirect(request, response, "page_404.html");
        }

    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void Marketing(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Db_Master dbm = new Db_Master();
        List<Marketing> complete = dbm.list_consensi_attivi();
        String path = dbm.getPath("temp");
        dbm.closeDB();

        if (complete.isEmpty()) {
            redirect(request, response, "page_404.html");
        } else {
            String base64 = Utility.sanitize(receiptexcel(path, complete));
            if (base64 != null) {
                String headerKey = "Content-Disposition";
                String headerValue = format("attachment; filename=\"%s\"",
                        new Object[] { "Marketing_OK_" + new DateTime().toString(patterntsdate2) + ".xlsx" });
                response.setHeader(headerKey, headerValue);
                try (OutputStream outStream = response.getOutputStream()) {
                    outStream.write(decodeBase64(base64.getBytes()));
                }
            } else {
                redirect(request, response, "page_404.html");
            }
        }

        // System.out.println("rc.so.servlets.ReportCentrale.Marketing()
        // "+complete.size());
    }

    protected void HistoricalStockPrice(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Utility.printRequest(request);

        String year = request.getParameter("year");
        String Output = request.getParameter("Output");
        if (null == Output) {
            Output = "EXC";
        } else {
            switch (Output) {
                case "on":
                    Output = "PDF";
                    break;
                default:
                    Output = "EXC";
                    break;
            }
        }

        if (Output.equals("PDF")) {
            Db_Master dbm = new Db_Master();
            String base64 = Utility.sanitize(dbm.getConf("stockprice." + year + ".pdf"));
            dbm.closeDB();

            if (base64 != null) {
                String headerKey = "Content-Disposition";
                String sanitizedyear = Utility.sanitize(year);
                String headerValue = format("attachment; filename=\"%s\"",
                        new Object[] { "HistoricalStockPrice_" + sanitizedyear + ".pdf" });
                response.setHeader(headerKey, headerValue);
                try (OutputStream outStream = response.getOutputStream()) {
                    outStream.write(decodeBase64(base64.getBytes()));
                }
            } else {
                redirect(request, response, "page_404.html");
            }
        } else {
            Db_Master dbm = new Db_Master();
            String base64 = Utility.sanitize(dbm.getConf("stockprice." + year + ".excel"));
            dbm.closeDB();

            if (base64 != null) {
                String headerKey = "Content-Disposition";
                String sanitizedyear = Utility.sanitize(year);
                String headerValue = format("attachment; filename=\"%s\"",
                        new Object[] { "HistoricalStockPrice_" + sanitizedyear + ".xlsx" });
                response.setHeader(headerKey, headerValue);
                try (OutputStream outStream = response.getOutputStream()) {
                    outStream.write(decodeBase64(base64.getBytes()));
                }
            } else {
                redirect(request, response, "page_404.html");
            }
        }

        // redirect(request, response, "page_404.html");
    }

    protected void BranchStockInquiryA(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String d3 = request.getParameter("d3");
        String d4 = getTimepicker(request.getParameter("d4"));
        String dt_tot = d3 + " " + d4;
        String Output = request.getParameter("Output");
        if (null == Output) {
            Output = "EXC";
        } else {
            switch (Output) {
                case "on":
                    Output = "PDF";
                    break;
                default:
                    Output = "EXC";
                    break;
            }
        }
        String data1 = formatStringtoStringDate_null(dt_tot, patternnormdate, patternsqldate);
        Db_Master dbm = new Db_Master();
        ArrayList<Branch> allenabledbr = dbm.list_branch_completeAFTER311217();
        String branch = request.getParameter("branch");
        List<String> br1 = parseString(branch, ",");
        if (br1.isEmpty()) {
            br1 = allenabledbr.stream().map(valore -> valore.getCod()).distinct().collect(toList());
        }
        String path = dbm.getPath("temp");
        dbm.closeDB();

        ArrayList<String> alcolonne = new ArrayList<>();
        alcolonne.add("Currency");
        alcolonne.add("Notes");
        if (is_IT) {
            alcolonne.add("Euro Travel Cheques");
        } else {
            alcolonne.add("");
        }
        alcolonne.add("Travel Cheques");
        alcolonne.add("BCE Rate");
        alcolonne.add("Countervalue");

        // ArrayList<BranchStockInquiry_value> dati =
        // dbm.list_BranchStockInquiry_value(fil, data1, "CH");
        ArrayList<BranchStockInquiry_value> complete = new ArrayList<>();
        for (int i = 0; i < br1.size(); i++) {
            try {
                String cod1 = br1.get(i);
                String desc1 = null;
                if (cod1 != null) {
                    Optional<Branch> branchOptional = allenabledbr.stream()
                            .filter(c1 -> c1.getCod().equals(cod1))
                            .findAny();
                    if (branchOptional.isPresent()) {
                        desc1 = branchOptional.get().getDe_branch(); 
                    }
                }

                String[] fil = { br1.get(i), desc1 };
                Db_Master dbm1 = new Db_Master();
                ArrayList<BranchStockInquiry_value> dati = dbm1.list_BranchStockInquiry_value(fil, data1, "CH");
                dbm1.closeDB();
                if (!dati.isEmpty()) {
                    BranchStockInquiry_value pdf = new BranchStockInquiry_value();
                    pdf.setId_filiale(fil[0]);
                    pdf.setDe_filiale(fil[1]);
                    pdf.setDati(dati);
                    complete.add(pdf);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        BranchStockInquiry bsi = new BranchStockInquiry();
        if (Output.equals("PDF")) {

            String base64 = Utility.sanitize(bsi.receipt_new_multi(path, complete, alcolonne, dt_tot));
            if (base64 != null) {
                String headerKey = "Content-Disposition";
                String sanitized_dt_tot = Utility.sanitize(dt_tot);
                String headerValue = format("attachment; filename=\"%s\"",
                        new Object[] { "BranchStockInquiry_" + sanitized_dt_tot + ".pdf" });
                response.setHeader(headerKey, headerValue);
                try (OutputStream outStream = response.getOutputStream()) {
                    outStream.write(decodeBase64(base64.getBytes()));
                }
            } else {
                redirect(request, response, "page_404.html");
            }
        } else {
            String base64 = Utility.sanitize(bsi.receiptexcel_new(path, complete, alcolonne, dt_tot));
            if (base64 != null) {
                String headerKey = "Content-Disposition";
                String sanitized_dt_tot = Utility.sanitize(dt_tot);
                String headerValue = format("attachment; filename=\"%s\"",
                        new Object[] { "BranchStockInquiry_" + sanitized_dt_tot + ".xlsx" });
                response.setHeader(headerKey, headerValue);
                try (OutputStream outStream = response.getOutputStream()) {
                    outStream.write(decodeBase64(base64.getBytes()));
                }
            } else {
                redirect(request, response, "page_404.html");
            }
        }

    }

    protected void DailyError(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String d3 = Utility.sanitize(request.getParameter("d3"));
        String d4 = Utility.sanitize(request.getParameter("d4"));

        Db_Master dbm = new Db_Master();
        ArrayList<Branch> allenabledbr = dbm.list_branch_completeAFTER311217();
        String branch = Utility.sanitize(request.getParameter("branch"));
        ArrayList<String> br1 = parseString(branch, ",");
        if (br1.isEmpty()) {
            br1 = (ArrayList<String>) allenabledbr.stream().map(valore -> valore.getCod()).distinct().collect(toList());
        }
        String path = Utility.sanitize(dbm.getPath("temp"));
        // path = "F:\\com\\";
        String data1 = formatStringtoStringDate_null(d3, patternnormdate_filter, patternsql);
        String data2 = formatStringtoStringDate_null(d4, patternnormdate_filter, patternsql);
        dbm.closeDB();

        String base64 = Utility.sanitize(new Dailyerror().create(path, br1, data1, data2));

        if (base64 != null) {
            String headerKey = "Content-Disposition";
            String sanitized_d3 = Utility.sanitize(d3);
            String sanitized_d4 = Utility.sanitize(d4);
            String headerValue = format("attachment; filename=\"%s\"",
                    new Object[] { "C_DailyError_" + sanitized_d3 + "_" + sanitized_d4 + ".xlsx" });
            response.setHeader(headerKey, headerValue);
            try (OutputStream outStream = response.getOutputStream()) {
                outStream.write(decodeBase64(base64.getBytes()));
            }
        } else {
            redirect(request, response, "page_404.html");
        }

    }

    protected void C_FreeTax_Branch(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String d3 = request.getParameter("d3");
        String d4 = request.getParameter("d4");

        String Output = request.getParameter("Output");
        if (null == Output) {
            Output = "EXC";
        } else {
            switch (Output) {
                case "on":
                    Output = "PDF";
                    break;
                default:
                    Output = "EXC";
                    break;
            }
        }

        Db_Master dbm = new Db_Master();
        ArrayList<Branch> allenabledbr = dbm.list_branch_completeAFTER311217();
        String branch = request.getParameter("branch");
        ArrayList<String> br1 = parseString(branch, ",");
        if (br1.isEmpty()) {
            br1 = (ArrayList<String>) allenabledbr.stream().map(valore -> valore.getCod()).distinct().collect(toList());
        }
        String path = dbm.getPath("temp");
        // path = "F:\\com\\";
        String data1 = formatStringtoStringDate_null(d3, patternnormdate_filter, patternsql);
        String data2 = formatStringtoStringDate_null(d4, patternnormdate_filter, patternsql);
        dbm.closeDB();

        ArrayList<String> alcolonne = new ArrayList<>();

        alcolonne.add("");
        alcolonne.add("");
        alcolonne.add(new DateTime().minusYears(1).toString(patternyear));
        alcolonne.add(new DateTime().toString(patternyear));
        alcolonne.add("+/- Val");
        alcolonne.add("+/-%");

        alcolonne.add(new DateTime().minusYears(1).toString(patternyear));
        alcolonne.add(new DateTime().toString(patternyear));
        alcolonne.add("+/- Val");
        alcolonne.add("+/-%");

        alcolonne.add(new DateTime().minusYears(1).toString(patternyear));
        alcolonne.add(new DateTime().toString(patternyear));
        alcolonne.add("+/- Val");
        alcolonne.add("+/-%");

        if (Output.equals("PDF")) {
            String base64 = Utility
                    .sanitize(new C_FreeTax_Branch().main(path, d3, d4, data1, data2, alcolonne, br1, allenabledbr));
            if (base64 != null) {
                String headerKey = "Content-Disposition";
                String sanitized_d3 = Utility.sanitize(d3);
                String sanitized_d4 = Utility.sanitize(d4);
                String headerValue = format("attachment; filename=\"%s\"",
                        new Object[] { "C_VatRefund_Branch_" + sanitized_d3 + "_" + sanitized_d4 + ".pdf" });
                response.setHeader(headerKey, headerValue);
                try (OutputStream outStream = response.getOutputStream()) {
                    outStream.write(decodeBase64(base64.getBytes()));
                }
            } else {
                redirect(request, response, "page_404.html");
            }
        } else {
            String base64 = Utility.sanitize(
                    new C_FreeTax_Branch().mainexcel(path, d3, d4, data1, data2, alcolonne, br1, allenabledbr));

            if (base64 != null) {
                String headerKey = "Content-Disposition";
                String sanitized_d3 = Utility.sanitize(d3);
                String sanitized_d4 = Utility.sanitize(d4);
                String headerValue = format("attachment; filename=\"%s\"",
                        new Object[] { "C_VatRefund_Branch_" + sanitized_d3 + "_" + sanitized_d4 + ".xls" });
                response.setHeader(headerKey, headerValue);
                try (OutputStream outStream = response.getOutputStream()) {
                    outStream.write(decodeBase64(base64.getBytes()));
                }
            } else {
                redirect(request, response, "page_404.html");
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
    protected void C_FreeTax_AmountBranch(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String d3 = request.getParameter("d3");
        String d4 = request.getParameter("d4");

        String Output = request.getParameter("Output");
        if (null == Output) {
            Output = "EXC";
        } else {
            switch (Output) {
                case "on":
                    Output = "PDF";
                    break;
                default:
                    Output = "EXC";
                    break;
            }
        }
        Db_Master dbm = new Db_Master();
        ArrayList<Branch> allenabledbr = dbm.list_branch_completeAFTER311217();
        String branch = request.getParameter("branch");
        ArrayList<String> br1 = parseString(branch, ",");
        if (br1.isEmpty()) {
            br1 = (ArrayList<String>) allenabledbr.stream().map(valore -> valore.getCod()).distinct().collect(toList());
        }
        String path = dbm.getPath("temp");
        // path = "F:\\com\\";
        String data1 = formatStringtoStringDate_null(d3, patternnormdate_filter, patternsql);
        String data2 = formatStringtoStringDate_null(d4, patternnormdate_filter, patternsql);
        dbm.closeDB();

        ArrayList<String> alcolonne = new ArrayList<>();

        alcolonne.add("");
        alcolonne.add("");
        alcolonne.add(new DateTime().minusYears(1).toString(patternyear));
        alcolonne.add(new DateTime().toString(patternyear));
        alcolonne.add("+/- Val");
        alcolonne.add("+/-%");

        alcolonne.add(new DateTime().minusYears(1).toString(patternyear));
        alcolonne.add(new DateTime().toString(patternyear));
        alcolonne.add("+/- Val");
        alcolonne.add("+/-%");

        if (Output.equals("PDF")) {
            String base64 = Utility.sanitize(
                    new C_FreeTax_AmountBranch().main(path, d3, d4, data1, data2, alcolonne, br1, allenabledbr));
            if (base64 != null) {
                String headerKey = "Content-Disposition";
                String sanitized_d3 = Utility.sanitize(d3);
                String sanitized_d4 = Utility.sanitize(d4);
                String headerValue = format("attachment; filename=\"%s\"",
                        new Object[] { "C_VatRefund_CurrencyBranch_" + sanitized_d3 + "_" + sanitized_d4 + ".pdf" });
                response.setHeader(headerKey, headerValue);
                try (OutputStream outStream = response.getOutputStream()) {
                    outStream.write(decodeBase64(base64.getBytes()));
                }
            } else {
                redirect(request, response, "page_404.html");
            }
        } else {
            String base64 = Utility.sanitize(
                    new C_FreeTax_AmountBranch().mainexcel(path, d3, d4, data1, data2, alcolonne, br1, allenabledbr));

            if (base64 != null) {
                String headerKey = "Content-Disposition";
                String sanitized_d3 = Utility.sanitize(d3);
                String sanitized_d4 = Utility.sanitize(d4);
                String headerValue = format("attachment; filename=\"%s\"",
                        new Object[] { "C_VatRefund_CurrencyBranch_" + sanitized_d3 + "_" + sanitized_d4 + ".xls" });
                response.setHeader(headerKey, headerValue);
                try (OutputStream outStream = response.getOutputStream()) {
                    outStream.write(decodeBase64(base64.getBytes()));
                }
            } else {
                redirect(request, response, "page_404.html");
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
    protected void C_FreeTax_NationAmount(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String d3 = request.getParameter("d3");
        String d4 = request.getParameter("d4");

        String Output = request.getParameter("Output");
        if (null == Output) {
            Output = "EXC";
        } else {
            switch (Output) {
                case "on":
                    Output = "PDF";
                    break;
                default:
                    Output = "EXC";
                    break;
            }
        }
        Db_Master dbm = new Db_Master();
        ArrayList<String[]> nat = dbm.country();
        String branch = request.getParameter("branch");
        ArrayList<String> br1 = parseString(branch, ",");
        boolean full = false;
        if (br1.isEmpty()) {
            full = true;
            br1 = dbm.list_branchcode_completeAFTER311217();
        }
        String path = dbm.getPath("temp");
        // path = "F:\\com\\";
        String data1 = formatStringtoStringDate_null(d3, patternnormdate_filter, patternsql);
        String data2 = formatStringtoStringDate_null(d4, patternnormdate_filter, patternsql);
        dbm.closeDB();

        ArrayList<String> alcolonne = new ArrayList<>();

        alcolonne.add("");
        alcolonne.add("");
        alcolonne.add(new DateTime().minusYears(1).toString(patternyear));
        alcolonne.add(new DateTime().toString(patternyear));
        alcolonne.add("+/- Val");
        alcolonne.add("+/-%");

        alcolonne.add(new DateTime().minusYears(1).toString(patternyear));
        alcolonne.add(new DateTime().toString(patternyear));
        alcolonne.add("+/- Val");
        alcolonne.add("+/-%");

        if (Output.equals("PDF")) {
            String base64 = Utility
                    .sanitize(new C_FreeTax_NationAmount().main(path, d3, d4, data1, data2, alcolonne, br1, nat));
            if (base64 != null) {
                String headerKey = "Content-Disposition";
                String sanitized_d3 = Utility.sanitize(d3);
                String sanitized_d4 = Utility.sanitize(d4);
                String headerValue = format("attachment; filename=\"%s\"",
                        new Object[] { "C_VAT_Refund_Nation_Currency_" + sanitized_d3 + "_" + sanitized_d4 + ".pdf" });
                response.setHeader(headerKey, headerValue);
                try (OutputStream outStream = response.getOutputStream()) {
                    outStream.write(decodeBase64(base64.getBytes()));
                }
            } else {
                redirect(request, response, "page_404.html");
            }
        } else {
            String base64 = Utility
                    .sanitize(new C_FreeTax_NationAmount().mainexcel(path, d3, d4, data1, data2, alcolonne, br1, nat));

            if (base64 != null) {
                String headerKey = "Content-Disposition";
                String sanitized_d3 = Utility.sanitize(d3);
                String sanitized_d4 = Utility.sanitize(d4);
                String headerValue = format("attachment; filename=\"%s\"",
                        new Object[] { "C_VAT_Refund_Nation_Currency_" + sanitized_d3 + "_" + sanitized_d4 + ".xls" });
                response.setHeader(headerKey, headerValue);
                try (OutputStream outStream = response.getOutputStream()) {
                    outStream.write(decodeBase64(base64.getBytes()));
                }
            } else {
                redirect(request, response, "page_404.html");
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
    protected void C_FreeTax_AmountNation(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String d3 = request.getParameter("d3");
        String d4 = request.getParameter("d4");

        String Output = request.getParameter("Output");
        if (null == Output) {
            Output = "EXC";
        } else {
            switch (Output) {
                case "on":
                    Output = "PDF";
                    break;
                default:
                    Output = "EXC";
                    break;
            }
        }
        Db_Master dbm = new Db_Master();
        ArrayList<String[]> nat = dbm.country();
        String branch = request.getParameter("branch");
        ArrayList<String> br1 = parseString(branch, ",");
        boolean full = false;
        if (br1.isEmpty()) {
            full = true;
            br1 = dbm.list_branchcode_completeAFTER311217();
        }
        String path = dbm.getPath("temp");
        // path = "F:\\com\\";
        String data1 = formatStringtoStringDate_null(d3, patternnormdate_filter, patternsql);
        String data2 = formatStringtoStringDate_null(d4, patternnormdate_filter, patternsql);
        dbm.closeDB();

        ArrayList<String> alcolonne = new ArrayList<>();

        alcolonne.add("");
        alcolonne.add("");
        alcolonne.add(new DateTime().minusYears(1).toString(patternyear));
        alcolonne.add(new DateTime().toString(patternyear));
        alcolonne.add("+/- Val");
        alcolonne.add("+/-%");

        alcolonne.add(new DateTime().minusYears(1).toString(patternyear));
        alcolonne.add(new DateTime().toString(patternyear));
        alcolonne.add("+/- Val");
        alcolonne.add("+/-%");

        if (Output.equals("PDF")) {
            String base64 = Utility
                    .sanitize(new C_FreeTax_AmountNation().main(path, d3, d4, data1, data2, alcolonne, br1, nat));
            if (base64 != null) {
                String headerKey = "Content-Disposition";
                String sanitized_d3 = Utility.sanitize(d3);
                String sanitized_d4 = Utility.sanitize(d4);
                String headerValue = format("attachment; filename=\"%s\"",
                        new Object[] { "C_VAT_Refund_Currency_Nation" + sanitized_d3 + "_" + sanitized_d4 + ".pdf" });
                response.setHeader(headerKey, headerValue);
                try (OutputStream outStream = response.getOutputStream()) {
                    outStream.write(decodeBase64(base64.getBytes()));
                }
            } else {
                redirect(request, response, "page_404.html");
            }
        } else {
            String base64 = Utility
                    .sanitize(new C_FreeTax_AmountNation().mainexcel(path, d3, d4, data1, data2, alcolonne, br1, nat));

            if (base64 != null) {
                String headerKey = "Content-Disposition";
                String sanitized_d3 = Utility.sanitize(d3);
                String sanitized_d4 = Utility.sanitize(d4);
                String headerValue = format("attachment; filename=\"%s\"",
                        new Object[] { "C_VAT_Refund_Currency_Nation" + sanitized_d3 + "_" + sanitized_d4 + ".xls" });
                response.setHeader(headerKey, headerValue);
                try (OutputStream outStream = response.getOutputStream()) {
                    outStream.write(decodeBase64(base64.getBytes()));
                }
            } else {
                redirect(request, response, "page_404.html");
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
    protected void C_FreeTax_Analisys(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String d3 = request.getParameter("d3");
        String d4 = request.getParameter("d4");

        String Output = request.getParameter("Output");
        if (null == Output) {
            Output = "EXC";
        } else {
            switch (Output) {
                case "on":
                    Output = "PDF";
                    break;
                default:
                    Output = "EXC";
                    break;
            }
        }
        Db_Master dbm = new Db_Master();
        ArrayList<Branch> allenabledbr = dbm.list_branch_completeAFTER311217();
        String branch = request.getParameter("branch");
        ArrayList<String> br1 = parseString(branch, ",");
        boolean full = false;
        if (br1.isEmpty()) {
            full = true;
            br1 = (ArrayList<String>) allenabledbr.stream().map(valore -> valore.getCod()).distinct().collect(toList());
        }
        String path = dbm.getPath("temp");
        // path = "F:\\com\\";
        String data1 = formatStringtoStringDate_null(d3, patternnormdate_filter, patternsql);
        String data2 = formatStringtoStringDate_null(d4, patternnormdate_filter, patternsql);
        dbm.closeDB();

        ArrayList<String> alcolonne = new ArrayList<>();
        alcolonne.add("");
        alcolonne.add(new DateTime().minusYears(1).toString(patternyear));
        alcolonne.add(new DateTime().toString(patternyear));
        alcolonne.add("+/- Val");
        alcolonne.add("+/-%");
        alcolonne.add(new DateTime().minusYears(1).toString(patternyear));
        alcolonne.add(new DateTime().toString(patternyear));
        alcolonne.add("+/- Val");
        alcolonne.add("+/-%");

        if (Output.equals("PDF")) {
            String base64 = Utility
                    .sanitize(new C_FreeTax_Analisys().main(path, d3, d4, data1, data2, alcolonne, br1, allenabledbr));
            if (base64 != null) {
                String headerKey = "Content-Disposition";
                String sanitized_d3 = Utility.sanitize(d3);
                String sanitized_d4 = Utility.sanitize(d4);
                String headerValue = format("attachment; filename=\"%s\"",
                        new Object[] { "VatRefund_Analisys_" + sanitized_d3 + "_" + sanitized_d4 + ".pdf" });
                response.setHeader(headerKey, headerValue);
                try (OutputStream outStream = response.getOutputStream()) {
                    outStream.write(decodeBase64(base64.getBytes()));
                }
            } else {
                redirect(request, response, "page_404.html");
            }
        } else {
            String base64 = Utility.sanitize(
                    new C_FreeTax_Analisys().mainexcel(path, d3, d4, data1, data2, alcolonne, br1, allenabledbr));

            if (base64 != null) {
                String headerKey = "Content-Disposition";
                String sanitized_d3 = Utility.sanitize(d3);
                String sanitized_d4 = Utility.sanitize(d4);
                String headerValue = format("attachment; filename=\"%s\"",
                        new Object[] { "VatRefund_Analisys_" + sanitized_d3 + "_" + sanitized_d4 + ".xls" });
                response.setHeader(headerKey, headerValue);
                try (OutputStream outStream = response.getOutputStream()) {
                    outStream.write(decodeBase64(base64.getBytes()));
                }
            } else {
                redirect(request, response, "page_404.html");
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
    protected void C_BankBranch(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String d3 = Utility.sanitize(request.getParameter("d3"));
        String d4 = Utility.sanitize(request.getParameter("d4"));
        String Output = Utility.sanitize(request.getParameter("Output"));
        if (null == Output) {
            Output = "EXC";
        } else {
            switch (Output) {
                case "on":
                    Output = "PDF";
                    break;
                default:
                    Output = "EXC";
                    break;
            }
        }

        Db_Master dbm = new Db_Master();
        ArrayList<ET_change> codlist = new ArrayList<>();
        String path = Utility.sanitize(dbm.getPath("temp"));
        // path = "F:\\com\\";
        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String paramName = parameterNames.nextElement();
            String[] paramValues = request.getParameterValues(paramName);

            for (String paramValue : paramValues) {
                paramName = Utility.sanitize(paramName);
                paramValue = Utility.sanitize(paramValue);

                if (paramName.startsWith("check_") && !paramName.equals("check_0")) {
                    if (paramValue.equalsIgnoreCase("on")) {
                        ET_change et1 = dbm.get_ET_change(replace_SU(paramName, "check_", ""));
                        if (et1 != null) {
                            codlist.add(et1);
                        }
                    }
                }
            }
        }

        sort(codlist);

        ArrayList<Branch> br = dbm.list_branch();
        ArrayList<String[]> array_bank = dbm.list_bank();
        ArrayList<String[]> array_credit_card = dbm.list_bank_pos_enabled();
        dbm.closeDB();
        if (Output.equals("PDF")) {
            String base64;

            if (newpread) {
                base64 = complete(path, codlist, br, array_bank, array_credit_card, true);
            } else {
                base64 = Utility.sanitize(new C_BankBranch().main(path, codlist, br, array_bank, array_credit_card));
            }

            if (base64 != null) {
                String headerKey = "Content-Disposition";
                String sanitized_d3 = Utility.sanitize(d3);
                String sanitized_d4 = Utility.sanitize(d4);
                String headerValue = format("attachment; filename=\"%s\"",
                        new Object[] { "C_BankBranch_" + sanitized_d3 + "_" + sanitized_d4 + ".pdf" });
                response.setHeader(headerKey, headerValue);
                try (OutputStream outStream = response.getOutputStream()) {
                    outStream.write(decodeBase64(base64.getBytes()));
                }
            } else {
                redirect(request, response, "page_404.html");
            }
        } else {

            String base64;
            if (newpread) {
                base64 = Utility.sanitize(complete(path, codlist, br, array_bank, array_credit_card, false));
            } else {
                base64 = Utility
                        .sanitize(new C_BankBranch().mainexcel(path, codlist, br, array_bank, array_credit_card));
            }
            if (base64 != null) {
                String headerKey = "Content-Disposition";
                String sanitized_d3 = Utility.sanitize(d3);
                String sanitized_d4 = Utility.sanitize(d4);
                String headerValue = format("attachment; filename=\"%s\"",
                        new Object[] { "C_BankBranch_" + sanitized_d3 + "_" + sanitized_d4 + ".xlsx" });
                response.setHeader(headerKey, headerValue);
                try (OutputStream outStream = response.getOutputStream()) {
                    outStream.write(decodeBase64(base64.getBytes()));
                }
            } else {
                redirect(request, response, "page_404.html");
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
    protected void C_OfficeStockPrice_now(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String datareport = new DateTime().toString(patternnormdate);
        String Output = request.getParameter("Output");
        if (null == Output) {
            Output = "EXC";
        } else {
            switch (Output) {
                case "on":
                    Output = "PDF";
                    break;
                default:
                    Output = "EXC";
                    break;
            }
        }

        Db_Master dbm = new Db_Master();
        ArrayList<Branch> allenabledbr = dbm.list_branch_completeAFTER311217();
        String branch = request.getParameter("branch");
        ArrayList<String> br1 = parseString(branch, ",");
        if (br1.isEmpty()) {
            br1 = (ArrayList<String>) allenabledbr.stream().map(valore -> valore.getCod()).distinct().collect(toList());
        }
        String localcur = dbm.get_local_currency()[0];
        ArrayList<String> alcolonne = new ArrayList<>();
        alcolonne.add("Currency");
        alcolonne.add("Kind");
        alcolonne.add("Quantity");

        if (newpread) {
            alcolonne.add("BCE Rate – Report Date");
        } else {
            alcolonne.add("Average buy");
        }

        alcolonne.add(localcurrency);
        String path = dbm.getPath("temp");
        OfficeStockPrice osp = new OfficeStockPrice();
        ArrayList<OfficeStockPrice_value> daticentrale = new ArrayList<>();
        for (int x = 0; x < br1.size(); x++) {
            String[] fil = { br1.get(x), formatBankBranchReport(br1.get(x), "BR", null, allenabledbr), "---" };
            OfficeStockPrice_value pdf = new OfficeStockPrice_value();
            pdf.setLocalcurrency(localcur);
            pdf.setId_filiale(fil[0]);
            pdf.setDe_filiale(fil[1]);
            pdf.setGruppo(fil[2]);
            ArrayList<OfficeStockPrice_value> dati = dbm.list_OfficeStockPrice_value(fil[0]);
            pdf.setDati(dati);
            if (dati.size() > 0) {
                pdf.setData(datareport);
                daticentrale.add(pdf);
            }
        }
        dbm.closeDB();
        //
        if (daticentrale.isEmpty()) {
            redirect(request, response, "page_404.html");
        } else if (Output.equals("PDF")) {
            String base64 = Utility.sanitize(osp.receiptcentrale(path, daticentrale, alcolonne, datareport));
            if (base64 != null) {
                String headerKey = "Content-Disposition";
                String sanitized_datareport = Utility.sanitize(datareport);
                String headerValue = format("attachment; filename=\"%s\"",
                        new Object[] { "C_OfficeStockPrice_" + sanitized_datareport + ".pdf" });
                response.setHeader(headerKey, headerValue);
                try (OutputStream outStream = response.getOutputStream()) {
                    outStream.write(decodeBase64(base64.getBytes()));
                }
            } else {
                redirect(request, response, "page_404.html");
            }
        } else {
            String base64 = Utility.sanitize(osp.receiptcentraleexcel(path, daticentrale, alcolonne, datareport));

            if (base64 != null) {
                String headerKey = "Content-Disposition";
                String sanitized_datareport = Utility.sanitize(datareport);
                String headerValue = format("attachment; filename=\"%s\"",
                        new Object[] { "C_OfficeStockPrice_" + sanitized_datareport + ".xls" });
                response.setHeader(headerKey, headerValue);
                try (OutputStream outStream = response.getOutputStream()) {
                    outStream.write(decodeBase64(base64.getBytes()));
                }
            } else {
                redirect(request, response, "page_404.html");
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
    protected void C_OfficeStockPrice(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String d3 = request.getParameter("d3");
        String d4 = request.getParameter("d4");
        String datareport = formatStringtoStringDate_null(d3 + " " + d4, patternnormdate, patternsqldate);

        String Output = request.getParameter("Output");
        if (null == Output) {
            Output = "EXC";
        } else {
            switch (Output) {
                case "on":
                    Output = "PDF";
                    break;
                default:
                    Output = "EXC";
                    break;
            }
        }

        Db_Master dbm = new Db_Master();
        ArrayList<Branch> allenabledbr = dbm.list_branch_completeAFTER311217();
        String branch = request.getParameter("branch");
        ArrayList<String> br1 = parseString(branch, ",");
        boolean full = false;
        if (br1.isEmpty()) {
            full = true;
            br1 = (ArrayList<String>) allenabledbr.stream().map(valore -> valore.getCod()).distinct().collect(toList());
        }
        String localcur = dbm.get_local_currency()[0];
        ArrayList<String> alcolonne = new ArrayList<>();
        alcolonne.add("Currency");
        alcolonne.add("Kind");
        alcolonne.add("Quantity");

        if (newpread) {
            alcolonne.add("BCE Rate – Report Date");
        } else {
            alcolonne.add("Average buy");
        }

        alcolonne.add(localcurrency);
        String path = dbm.getPath("temp");
        OfficeStockPrice osp = new OfficeStockPrice();
        ArrayList<OfficeStockPrice_value> daticentrale = new ArrayList<>();
        for (int x = 0; x < br1.size(); x++) {
            String[] fil = { br1.get(x), formatBankBranchReport(br1.get(x), "BR", null, allenabledbr), "---" };
            OfficeStockPrice_value pdf = new OfficeStockPrice_value();
            pdf.setLocalcurrency(localcur);
            pdf.setId_filiale(fil[0]);
            pdf.setDe_filiale(fil[1]);
            pdf.setGruppo(fil[2]);
            String codsp = dbm.getLastCod_officesp(fil[0], d3, datareport);
            if (codsp != null) {
                ArrayList<OfficeStockPrice_value> dati = dbm.list_OfficeStockPrice_value(codsp, fil[0]);
                pdf.setDati(dati);
                if (dati.size() > 0) {
                    pdf.setData(dati.get(0).getData());
                    daticentrale.add(pdf);
                }
            }
        }
        dbm.closeDB();
        //
        if (daticentrale.isEmpty()) {
            redirect(request, response, "page_404.html");
        } else if (Output.equals("PDF")) {
            String base64 = Utility.sanitize(osp.receiptcentrale(path, daticentrale, alcolonne, d3 + " " + d4));
            if (base64 != null) {
                String headerKey = "Content-Disposition";
                String sanitized_d3 = Utility.sanitize(d3);
                String sanitized_d4 = Utility.sanitize(d4);
                String headerValue = format("attachment; filename=\"%s\"",
                        new Object[] { "C_OfficeStockPrice_" + sanitized_d3 + " " + sanitized_d4 + ".pdf" });
                response.setHeader(headerKey, headerValue);
                try (OutputStream outStream = response.getOutputStream()) {
                    outStream.write(decodeBase64(base64.getBytes()));
                }
            } else {
                redirect(request, response, "page_404.html");
            }
        } else {
            String base64 = Utility.sanitize(osp.receiptcentraleexcel(path, daticentrale, alcolonne, d3 + " " + d4));

            if (base64 != null) {
                String headerKey = "Content-Disposition";
                String sanitized_d3 = Utility.sanitize(d3);
                String sanitized_d4 = Utility.sanitize(d4);
                String headerValue = format("attachment; filename=\"%s\"",
                        new Object[] { "C_OfficeStockPrice_" + sanitized_d3 + " " + sanitized_d4 + ".xls" });
                response.setHeader(headerKey, headerValue);
                try (OutputStream outStream = response.getOutputStream()) {
                    outStream.write(decodeBase64(base64.getBytes()));
                }
            } else {
                redirect(request, response, "page_404.html");
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
    protected void C_OfficeStockPriceCurrency(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String datareport = new DateTime().toString(patternnormdate_f);

        String Output = request.getParameter("Output");
        if (null == Output) {
            Output = "EXC";
        } else {
            switch (Output) {
                case "on":
                    Output = "PDF";
                    break;
                default:
                    Output = "EXC";
                    break;
            }
        }

        Db_Master dbm = new Db_Master();
        ArrayList<Branch> allenabledbr = dbm.list_branch_completeAFTER311217();
        String branch = request.getParameter("branch");
        ArrayList<String> br1 = parseString(branch, ",");
        boolean full = false;
        if (br1.isEmpty()) {
            full = true;
            br1 = (ArrayList<String>) allenabledbr.stream().map(valore -> valore.getCod()).distinct().collect(toList());
        }

        // br1.add("000");
        String path = dbm.getPath("temp");
        // path = "F:\\com\\";

        OfficeStockPriceBranch pdf = new OfficeStockPriceBranch();

        ArrayList<String> alcolonne = new ArrayList<>();
        alcolonne.add("Branch");
        alcolonne.add("Quantity");

        if (newpread) {
            alcolonne.add("BCE Rate – Report Date");
        } else {
            alcolonne.add("Average buy");
        }

        alcolonne.add(localcurrency);

        ArrayList<OfficeStockPriceBranch_value> dati = dbm.list_OfficeStockPrice_value_branch(br1, allenabledbr);

        dbm.closeDB();

        // if(true)
        // return;
        if (Output.equals("PDF")) {

            String base64 = Utility.sanitize(pdf.receiptcentrale(path, dati, alcolonne, datareport));
            if (base64 != null) {
                String headerKey = "Content-Disposition";
                String sanitized_datareport = Utility.sanitize(datareport);
                String headerValue = format("attachment; filename=\"%s\"",
                        new Object[] { "C_OfficeStockPriceCurrency_" + sanitized_datareport + ".pdf" });
                response.setHeader(headerKey, headerValue);
                try (OutputStream outStream = response.getOutputStream()) {
                    outStream.write(decodeBase64(base64.getBytes()));
                }
            } else {
                redirect(request, response, "page_404.html");
            }
        } else {
            String base64 = Utility.sanitize(pdf.receiptcentraleexcel(path, dati, alcolonne, datareport));
            //
            if (base64 != null) {
                String headerKey = "Content-Disposition";
                String sanitized_datareport = Utility.sanitize(datareport);
                String headerValue = format("attachment; filename=\"%s\"",
                        new Object[] { "C_OfficeStockPriceCurrency_" + sanitized_datareport + ".xls" });
                response.setHeader(headerKey, headerValue);
                try (OutputStream outStream = response.getOutputStream()) {
                    outStream.write(decodeBase64(base64.getBytes()));
                }
            } else {
                redirect(request, response, "page_404.html");
            }
        }
    }

    /**
     *
     * @param request
     * @param response
     * @param cashier
     * @throws ServletException
     * @throws IOException
     */
    protected void C_OpenCloseError(HttpServletRequest request, HttpServletResponse response, boolean cashier)
            throws ServletException, IOException {

        String Show = request.getParameter("Show");

        boolean change = false;
        boolean nochange = false;

        if (Show == null) {
            Show = "---";
        }

        if (Show.equals("---") || Show.equals("01")) {
            change = true;
        }
        if (Show.equals("---") || Show.equals("02")) {
            nochange = true;
        }

        String thres = formatDoubleforMysql(request.getParameter("thres"));

        String d3 = request.getParameter("d3");
        String d4 = request.getParameter("d4");

        String data1 = formatStringtoStringDate_null(d3, patternnormdate_filter, patternsql);
        String data2 = formatStringtoStringDate_null(d4, patternnormdate_filter, patternsql);

        String Output = request.getParameter("Output");
        if (null == Output) {
            Output = "EXC";
        } else {
            switch (Output) {
                case "on":
                    Output = "PDF";
                    break;
                default:
                    Output = "EXC";
                    break;
            }
        }

        Db_Master dbm = new Db_Master();
        ArrayList<Branch> allenabledbr = dbm.list_branch_completeAFTER311217();
        String branch = request.getParameter("branch");
        ArrayList<String> br1 = parseString(branch, ",");
        boolean full = false;
        if (br1.isEmpty()) {
            full = true;
            br1 = (ArrayList<String>) allenabledbr.stream().map(valore -> valore.getCod()).distinct().collect(toList());
        }
        String path = dbm.getPath("temp");
        // path = "F:\\com\\";
        dbm.closeDB();

        if (Output.equals("PDF")) {

            String base64 = Utility.sanitize(new C_Openclose_error().main(path, d3, d4, data1, data2, change, nochange,
                    thres, br1, allenabledbr));
            if (base64 != null) {
                String headerKey = "Content-Disposition";
                String sanitized_d3 = Utility.sanitize(d3);
                String sanitized_d4 = Utility.sanitize(d4);
                String headerValue = format("attachment; filename=\"%s\"",
                        new Object[] { "C_OpenCloseError_" + sanitized_d3 + "_" + sanitized_d4 + ".pdf" });
                response.setHeader(headerKey, headerValue);
                try (OutputStream outStream = response.getOutputStream()) {
                    outStream.write(decodeBase64(base64.getBytes()));
                }
            } else {
                redirect(request, response, "page_404.html");
            }
        } else {

            ArrayList<Openclose_Error_value> osplistCOMPLETE = new ArrayList<>();
            ArrayList<Openclose_Error_value> completapercashier = new ArrayList<>();

            Db_Master dbm0 = new Db_Master();
            for (int b = 0; b < br1.size(); b++) {
                Openclose_Error_value pdf = new Openclose_Error_value();

                Branch bb = get_Branch(br1.get(b), allenabledbr);

                pdf.setId_filiale(bb.getCod());
                pdf.setDe_filiale(bb.getDe_branch());

                ArrayList<Openclose> list = dbm0.list_openclose_errors_report(br1.get(b), data1, data2);
                ArrayList<Openclose_Error_value_stock> datifilialeperreport = new ArrayList<>();
                Db_Master dbm1 = new Db_Master();
                for (int i = 0; i < list.size(); i++) {
                    Openclose co = list.get(i);
                    ArrayList<String[]> list_oc_errors = dbm1.list_oc_errors(co.getCod());
                    ArrayList<Openclose_Error_value> dati = dbm1.list_openclose_errors_report(co, list_oc_errors,
                            allenabledbr, thres);
                    if (dati.size() > 0) {
                        Openclose_Error_value_stock dpf = new Openclose_Error_value_stock(dati, co.getId());
                        datifilialeperreport.add(dpf);
                        completapercashier.addAll(dati);
                    }
                }
                dbm1.closeDB();
                pdf.setDati(datifilialeperreport);
                if (datifilialeperreport.size() > 0) {
                    osplistCOMPLETE.add(pdf);
                }
            }
            String localcurrency = dbm0.get_local_currency()[0];
            dbm0.closeDB();

            Openclose_Error osp = new Openclose_Error();
            String base64 = Utility.sanitize(
                    osp.main_Excel(path, osplistCOMPLETE, d3, d4, localcurrency, cashier, completapercashier));

            if (base64 != null) {
                String headerKey = "Content-Disposition";
                String sanitized_d3 = Utility.sanitize(d3);
                String sanitized_d4 = Utility.sanitize(d4);
                String headerValue = format("attachment; filename=\"%s\"",
                        new Object[] { "OpencloseErrors" + "_" + sanitized_d3 + "_" + sanitized_d4 + "_" + ".xlsx" });
                response.setHeader(headerKey, headerValue);
                try (OutputStream outStream = response.getOutputStream()) {
                    outStream.write(decodeBase64(base64.getBytes()));
                }
            } else {
                redirect(request, response, "page_404.html");
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
    protected void QuarterlyCZK(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String branch = request.getParameter("branch");

        String d3 = request.getParameter("d3");
        String d4 = request.getParameter("d4");

        String data1 = formatStringtoStringDate_null(d3, patternnormdate_filter, patternsql);
        String data2 = formatStringtoStringDate_null(d4, patternnormdate_filter, patternsql);

        String Output = request.getParameter("Output");
        if (null == Output) {
            Output = "EXC";
        } else {
            switch (Output) {
                case "on":
                    Output = "PDF";
                    break;
                default:
                    Output = "EXC";
                    break;
            }
        }

        String spage = "0";

        ArrayList<String> alcolonne = new ArrayList<>();
        alcolonne.add("Valuta");
        alcolonne.add("Nákup");
        alcolonne.add("Prodej");
        alcolonne.add("Saldo");

        Db_Master dbm = new Db_Master();
        ArrayList<Branch> allenabledbr = dbm.list_branch_completeAFTER311217();
        String path = dbm.getPath("temp");
        // path = "F:\\com\\";
        ArrayList<C_TrimestraleCZK_value> dati = dbm.getC_TrimestraleCZK(data1, data2, branch);

        dbm.closeDB();

        if (!dati.isEmpty()) {
            C_TrimestraleCZK nctl = new C_TrimestraleCZK();
            C_TrimestraleCZK_value pdf = new C_TrimestraleCZK_value();
            pdf.setDataDa(d3);
            pdf.setDataA(d4);
            pdf.setBranch(formatBankBranch(branch, "BR", null, allenabledbr, null));
            pdf.setDati(dati);
            if (Output.equals("PDF")) {
                String base64 = Utility
                        .sanitize(nctl.receipt(path, pdf, alcolonne, data2.substring(0, 4), parseInt(spage)));
                if (base64 != null) {
                    String headerKey = "Content-Disposition";
                    String sanitized_d3 = Utility.sanitize(d3);
                    String sanitized_d4 = Utility.sanitize(d4);
                    String headerValue = format("attachment; filename=\"%s\"",
                            new Object[] { "QuarterlyCZK_" + sanitized_d3 + "_" + sanitized_d4 + ".pdf" });
                    response.setHeader(headerKey, headerValue);
                    try (OutputStream outStream = response.getOutputStream()) {
                        outStream.write(decodeBase64(base64.getBytes()));
                    }
                } else {
                    redirect(request, response, "page_404.html");
                }
            } else {
                String base64 = Utility
                        .sanitize(nctl.receiptexcel(path, pdf, alcolonne, data2.substring(0, 4), parseInt(spage)));

                if (base64 != null) {
                    String headerKey = "Content-Disposition";
                    String sanitized_d3 = Utility.sanitize(d3);
                    String sanitized_d4 = Utility.sanitize(d4);
                    String headerValue = format("attachment; filename=\"%s\"",
                            new Object[] { "QuarterlyCZK_" + sanitized_d3 + "_" + sanitized_d4 + ".xls" });
                    response.setHeader(headerKey, headerValue);
                    try (OutputStream outStream = response.getOutputStream()) {
                        outStream.write(decodeBase64(base64.getBytes()));
                    }
                } else {
                    redirect(request, response, "page_404.html");
                }
            }

        } else {
            redirect(request, response, "page_404.html");
        }

    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void CustomerTransactionList_s(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // String thres = request.getParameter("thres");
        //
        // String d3 = request.getParameter("d3");
        // String d4 = request.getParameter("d4");
        //
        // String data1 = formatStringtoStringDate_null(d3, patternnormdate_filter,
        // patternsql);
        // String data2 = formatStringtoStringDate_null(d4, patternnormdate_filter,
        // patternsql);
        //
        // String Output = request.getParameter("Output");
        // if (Output == null) {
        // Output = "EXC";
        // } else if (Output.equals("on")) {
        // Output = "PDF";
        // } else {
        // Output = "EXC";
        // }
        //
        // Db_Master dbm = new Db_Master();
        // ArrayList<Branch> allenabledbr = dbm.list_branch_completeAFTER311217();
        // String branch = request.getParameter("branch");
        // ArrayList<String> br1 = parseString(branch, ",");
        // boolean full = false;
        // if (br1.isEmpty()) {
        // full = true;
        // br1 = (ArrayList) allenabledbr.stream().map(valore ->
        // valore.getCod()).distinct().collect(Collectors.toList());
        // }
        // String path = dbm.getPath("temp");
        // //path = "F:\\com\\";
        //
        // ArrayList<CustomerTransactionList_value> dati =
        // dbm.list_CustomerTransactionList_value(br1, data1, data2, allenabledbr, null,
        // thres);
        // dbm.closeDB();
        //
        // if (dati.size() > 0) {
        //
        // if (Output.equals("PDF")) {
        // String base64 = new CustomerTransactionList().receipt(path, dati, d3, d4,
        // "Risk Assessment Index (Number of Transactions)", "Threshold selected: " +
        // thres);
        // if (base64 != null) {
        // String headerKey = "Content-Disposition";
        // String headerValue = String.format("attachment; filename=\"%s\"", new
        // Object[]{"CustomerTransactionList_s" + d3 + "_" + d4 + ".pdf"});
        // response.setHeader(headerKey, headerValue);
        // OutputStream outStream = response.getOutputStream();
        // outStream.write(Base64.decodeBase64(base64.getBytes()));
        // outStream.close();
        // } else {
        // redirect(request, response, "page_404.html");
        // }
        // } else {
        // String base64 = new CustomerTransactionList().receiptexcel(path, dati, d3,
        // d4,
        // "Risk Assessment Index (Number of Transactions)", "Threshold selected: " +
        // thres);
        //
        // if (base64 != null) {
        // String headerKey = "Content-Disposition";
        // String headerValue = String.format("attachment; filename=\"%s\"", new
        // Object[]{"CustomerTransactionList_s" + d3 + "_" + d4 + ".xls"});
        // response.setHeader(headerKey, headerValue);
        // OutputStream outStream = response.getOutputStream();
        // outStream.write(Base64.decodeBase64(base64.getBytes()));
        // outStream.close();
        // } else {
        // redirect(request, response, "page_404.html");
        // }
        // }
        //
        // } else {
        // redirect(request, response, "page_404.html");
        // }
    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void CustomerTransactionList_AF(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        printRequest(request);
        //
        // if(true)
        // return;

        String frequency = Utility.sanitize(request.getParameter("frequency"));

        String d3 = Utility.sanitize(request.getParameter("d3"));
        String d4 = Utility.sanitize(request.getParameter("d4"));

        String data1 = formatStringtoStringDate_null(d3, patternnormdate_filter, patternsql);
        String data2 = formatStringtoStringDate_null(d4, patternnormdate_filter, patternsql);

        String Output = Utility.sanitize(request.getParameter("Output"));
        if (null == Output) {
            Output = "EXC";
        } else {
            switch (Output) {
                case "on":
                    Output = "PDF";
                    break;
                default:
                    Output = "EXC";
                    break;
            }
        }

        Db_Master dbm = new Db_Master();
        ArrayList<Branch> allenabledbr = dbm.list_branch_completeAFTER311217();
        String branch = Utility.sanitize(request.getParameter("branch"));
        ArrayList<String> br1 = parseString(branch, ",");
        boolean full = false;
        if (br1.isEmpty()) {
            full = true;
            br1 = (ArrayList<String>) allenabledbr.stream().map(valore -> valore.getCod()).distinct().collect(toList());
        }
        String path = Utility.sanitize(dbm.getPath("temp"));
        ArrayList<CustomerTransactionList_value> dati = dbm.list_CustomerTransactionList_NEW(br1, data1, data2,
                allenabledbr, frequency);
        dbm.closeDB();

        String namereport = "Risk Assessment Index";
        String freq = "";
        switch (frequency) {
            case "D":
                namereport += " - Abnormal Frequency";
                freq = "Frequency selected: Daily";
                break;
            case "W":
                namereport += " - Abnormal Frequency";
                freq = "Frequency selected: Weekly";
                break;
            case "M":
                namereport += " - Abnormal Frequency";
                freq = "Frequency selected: Monthly";
                break;
            case "M1":
                namereport += " - Abnormal Volume";
                freq = "Frequency selected: Monthly";
                break;
            case "Q":
                namereport += " - Abnormal Volume";
                freq = "Frequency selected: Quarterly";
                break;
            case "RC":
                namereport += " - Threshold Volume 'Rogue Country'";
                freq = "";
                break;
            case "TH":
                namereport += " - Threshold";
                freq = "";
                break;
            default:
                break;
        }

        if (dati.size() > 0) {
            if (Output.equals("PDF")) {
                String base64 = Utility.sanitize(
                        new CustomerTransactionList().receipt(path, dati, d3, d4, namereport, freq, frequency));
                if (base64 != null) {
                    String headerKey = "Content-Disposition";
                    String sanitized_d3 = Utility.sanitize(d3);
                    String sanitized_d4 = Utility.sanitize(d4);
                    String headerValue = format("attachment; filename=\"%s\"",
                            new Object[] { "RiskAssessmentIndex_" + sanitized_d3 + "_" + sanitized_d4 + ".pdf" });
                    response.setHeader(headerKey, headerValue);
                    try (OutputStream outStream = response.getOutputStream()) {
                        outStream.write(decodeBase64(base64.getBytes()));
                    }
                } else {
                    redirect(request, response, "page_404.html");
                }
            } else {
                String base64 = Utility.sanitize(
                        new CustomerTransactionList().receiptexcel(path, dati, d3, d4, namereport, freq, frequency));

                if (base64 != null) {
                    String headerKey = "Content-Disposition";
                    String sanitized_d3 = Utility.sanitize(d3);
                    String sanitized_d4 = Utility.sanitize(d4);
                    String headerValue = format("attachment; filename=\"%s\"",
                            new Object[] { "RiskAssessmentIndex_" + sanitized_d3 + "_" + sanitized_d4 + ".xls" });
                    response.setHeader(headerKey, headerValue);
                    try (OutputStream outStream = response.getOutputStream()) {
                        outStream.write(decodeBase64(base64.getBytes()));
                    }
                } else {
                    redirect(request, response, "page_404.html");
                }
            }
        } else {
            redirect(request, response, "page_404.html");
        }

    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void CustomerTransactionList_t(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // String tran = request.getParameter("tran");
        //
        // String d3 = request.getParameter("d3");
        // String d4 = request.getParameter("d4");
        //
        // String data1 = formatStringtoStringDate_null(d3, patternnormdate_filter,
        // patternsql);
        // String data2 = formatStringtoStringDate_null(d4, patternnormdate_filter,
        // patternsql);
        //
        // String Output = request.getParameter("Output");
        // if (Output == null) {
        // Output = "EXC";
        // } else if (Output.equals("on")) {
        // Output = "PDF";
        // } else {
        // Output = "EXC";
        // }
        //
        // Db_Master dbm = new Db_Master();
        // ArrayList<Branch> allenabledbr = dbm.list_branch_completeAFTER311217();
        // String branch = request.getParameter("branch");
        // ArrayList<String> br1 = parseString(branch, ",");
        // boolean full = false;
        // if (br1.isEmpty()) {
        // full = true;
        // br1 = (ArrayList) allenabledbr.stream().map(valore ->
        // valore.getCod()).distinct().collect(Collectors.toList());
        // }
        // String path = dbm.getPath("temp");
        // //path = "F:\\com\\";
        //
        // ArrayList<CustomerTransactionList_value> dati =
        // dbm.list_CustomerTransactionList_value(br1, data1, data2, allenabledbr, tran,
        // null);
        // dbm.closeDB();
        //
        // if (dati.size() > 0) {
        //
        // if (Output.equals("PDF")) {
        // String base64 = new CustomerTransactionList().receipt(path, dati, d3, d4,
        // "Risk Assessment Index (Number of Transactions)", "");
        // if (base64 != null) {
        // String headerKey = "Content-Disposition";
        // String headerValue = String.format("attachment; filename=\"%s\"", new
        // Object[]{"CustomerTransactionList_t" + d3 + "_" + d4 + ".pdf"});
        // response.setHeader(headerKey, headerValue);
        // OutputStream outStream = response.getOutputStream();
        // outStream.write(Base64.decodeBase64(base64.getBytes()));
        // outStream.close();
        // } else {
        // redirect(request, response, "page_404.html");
        // }
        // } else {
        // String base64 = new CustomerTransactionList().receiptexcel(path, dati, d3,
        // d4, "Risk Assessment Index (Number of Transactions)", "");
        //
        // if (base64 != null) {
        // String headerKey = "Content-Disposition";
        // String headerValue = String.format("attachment; filename=\"%s\"", new
        // Object[]{"CustomerTransactionList_t_" + d3 + "_" + d4 + ".xls"});
        // response.setHeader(headerKey, headerValue);
        // OutputStream outStream = response.getOutputStream();
        // outStream.write(Base64.decodeBase64(base64.getBytes()));
        // outStream.close();
        // } else {
        // redirect(request, response, "page_404.html");
        // }
        // }
        // } else {
        // redirect(request, response, "page_404.html");
        // }
    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void lnch(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String vatref = request.getParameter("vatref");
        if (null == vatref) {
            vatref = "NO";
        } else {
            switch (vatref) {
                case "on":
                    vatref = "SI";
                    break;
                default:
                    vatref = "NO";
                    break;
            }
        }

        String d3 = request.getParameter("d3");
        String d4 = request.getParameter("d4");

        String data1 = formatStringtoStringDate_null(d3, patternnormdate_filter, patternsql);
        String data2 = formatStringtoStringDate_null(d4, patternnormdate_filter, patternsql);

        Db_Master dbm = new Db_Master();

        String path = dbm.getPath("temp");
        String branch = request.getParameter("branch");
        ArrayList<String> br1 = parseString(branch, ",");
        if (br1.isEmpty()) {
            br1 = dbm.list_branchcode_completeAFTER311217();
        }
        ArrayList<NC_transaction> result = dbm.query_NC_transaction_NEW(data1, data2, br1, vatref);
        dbm.closeDB();

        String cod = generaId(75);
        String base64;
        String ext;
        if (result.size() > 0) {
            base64 = Utility.sanitize(excel_transactionnc_list(new File(path + cod), result));
            ext = ".xlsx";
        } else {
            base64 = Utility.sanitize(new Receipt().print_pdf_noresult(path + cod));
            ext = ".pdf";
        }

        if (base64 != null) {
            String headerKey = "Content-Disposition";
            String sanitized_cod = Utility.sanitize(cod);
            String sanitized_ext = Utility.sanitize(ext);
            String headerValue = format("attachment; filename=\"%s\"", new Object[] { sanitized_cod + sanitized_ext });
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
    protected void lpos(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String cred = request.getParameter("cred");

        String d3 = request.getParameter("d3");
        String d4 = request.getParameter("d4");

        String data1 = formatStringtoStringDate_null(d3, patternnormdate_filter, patternsql);
        String data2 = formatStringtoStringDate_null(d4, patternnormdate_filter, patternsql);

        Db_Master dbm = new Db_Master();
        String path = dbm.getPath("temp");
        ArrayList<Branch> allenabledbr = dbm.list_branch_completeAFTER311217();
        String branch = request.getParameter("branch");
        ArrayList<String> br1 = parseString(branch, ",");
        if (br1.isEmpty()) {
            br1 = (ArrayList<String>) allenabledbr.stream().map(valore -> valore.getCod()).distinct().collect(toList());
        }

        TillTransactionList_value change = dbm.list_newreport_value_pos(br1, data1, data2, cred, allenabledbr);
        dbm.closeDB();

        String base64 = null;

        if (change != null) {
            base64 = Utility.sanitize(
                    new TillTransactionList().receiptexcel_newreport(path, change, d3, d4, "List Transaction POS", 0));
        }

        if (base64 != null) {
            String headerKey = "Content-Disposition";
            String sanitized_d3 = Utility.sanitize(d3);
            String sanitized_d4 = Utility.sanitize(d4);
            String headerValue = format("attachment; filename=\"%s\"",
                    new Object[] { "ListTransactionPOS_" + sanitized_d3 + "_" + sanitized_d4 + ".xls" });
            response.setHeader(headerKey, headerValue);
            try (OutputStream outStream = response.getOutputStream()) {
                outStream.write(decodeBase64(base64.getBytes()));
            }
        } else {
            redirect(request, response, "page_404.html");
        }

    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void lcha(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String d3 = Utility.sanitize(request.getParameter("d3"));
        String d4 = Utility.sanitize(request.getParameter("d4"));

        String data1 = formatStringtoStringDate_null(d3, patternnormdate_filter, patternsql);
        String data2 = formatStringtoStringDate_null(d4, patternnormdate_filter, patternsql);

        Db_Master dbm = new Db_Master();
        String path = Utility.sanitize(dbm.getPath("temp"));
        String branch = Utility.sanitize(request.getParameter("branch"));
        ArrayList<String> br1 = parseString(branch, ",");
        boolean full = false;
        if (br1.isEmpty()) {
            full = true;
            br1 = dbm.list_branchcode_completeAFTER311217();
        }
        List<Marketing> list_consensi_attivi = dbm.list_consensi_attivi();
        ArrayList<Ch_transaction> result = dbm.query_transaction_ch_new(data1, data2, br1);

        dbm.closeDB();

        String cod = generaId(75);
        String base64;
        String ext;
        if (result.size() > 0) {
            base64 = excel_transaction_listEVO(new File(path + cod), result, list_consensi_attivi);
            ext = ".xlsx";
        } else {
            base64 = new Receipt().print_pdf_noresult(path + cod);
            ext = ".pdf";
        }
        if (base64 != null) {
            String headerKey = "Content-Disposition";
            String sanitized_cod = Utility.sanitize(cod);
            String sanitized_ext = Utility.sanitize(ext);
            String headerValue = format("attachment; filename=\"%s\"", new Object[] { sanitized_cod + sanitized_ext });
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
    protected void CACZ(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String d3 = Utility.sanitize(request.getParameter("d3"));
        String d4 = Utility.sanitize(request.getParameter("d4"));

        String Output = Utility.sanitize(request.getParameter("Output"));
        if (null == Output) {
            Output = "EXC";
        } else {
            switch (Output) {
                case "on":
                    Output = "PDF";
                    break;
                default:
                    Output = "EXC";
                    break;
            }
        }

        String data1 = formatStringtoStringDate_null(d3, patternnormdate_filter, patternsql);
        String data2 = formatStringtoStringDate_null(d4, patternnormdate_filter, patternsql);

        Db_Master dbm = new Db_Master();
        String path = Utility.sanitize(dbm.getPath("temp"));
        // path = "F:\\com\\";
        String filiale = Utility.sanitize(request.getParameter("branch"));
        String fil[] = new String[2];
        if (filiale == null || filiale.equals("")) {
            fil = dbm.getCodLocal(false);
        } else {
            fil[0] = filiale;
            fil[1] = formatBankBranchReport(filiale, "BR", null, list_branch_completeAFTER311217());
        }
        TillTransactionListCurrency_value pdf = dbm.list_TillTransactionList_value_CACZ(fil, data1, data2);
        TillTransactionListCurrency_value pdf2 = dbm.list_TillTransactionList_TOBANK_CACZ(fil, data1, data2);
        dbm.closeDB();

        if (pdf != null || pdf2 != null) {

            TillTransactionListCurrency rma = new TillTransactionListCurrency();

            if (Output.equals("PDF")) {
                String base64 = Utility.sanitize(rma.receiptpdf_CACZ(path, pdf, pdf2, d3, d4));
                if (base64 != null) {
                    String headerKey = "Content-Disposition";
                    String sanitized_d3 = Utility.sanitize(d3);
                    String sanitized_d4 = Utility.sanitize(d4);
                    String headerValue = format("attachment; filename=\"%s\"",
                            new Object[] { "TillTransactionListCACZ_" + sanitized_d3 + "_" + sanitized_d4 + ".pdf" });
                    response.setHeader(headerKey, headerValue);
                    try (OutputStream outStream = response.getOutputStream()) {
                        outStream.write(decodeBase64(base64.getBytes()));
                    }
                } else {
                    // redirect(request, response, "page_404.html");
                }
            } else {
                String base64 = Utility.sanitize(rma.receiptexcel_CACZ(path, pdf, pdf2, d3, d4));
                if (base64 != null) {
                    String headerKey = "Content-Disposition";
                    String sanitized_d3 = Utility.sanitize(d3);
                    String sanitized_d4 = Utility.sanitize(d4);
                    String headerValue = format("attachment; filename=\"%s\"",
                            new Object[] { "TillTransactionListCACZ_" + sanitized_d3 + "_" + sanitized_d4 + ".xls" });
                    response.setHeader(headerKey, headerValue);
                    try (OutputStream outStream = response.getOutputStream()) {
                        outStream.write(decodeBase64(base64.getBytes()));
                    }
                } else {
                    redirect(request, response, "page_404.html");
                }
            }

            if (true) {
                return;
            }

        } else {
            redirect(request, response, "page_404.html");
        }

    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void pending(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String d3 = request.getParameter("d3");
        String d4 = request.getParameter("d4");
        String data1 = formatStringtoStringDate_null(d3, patternnormdate_filter, patternsql);
        String data2 = formatStringtoStringDate_null(d4, patternnormdate_filter, patternsql);

        List<String> operator = getList_request(request, "operator");

        Db_Master dbm = new Db_Master();
        String path = dbm.getPath("temp");
        String branch = request.getParameter("branch");
        ArrayList<String> br1 = parseString(branch, ",");
        if (br1.isEmpty()) {
            br1 = dbm.list_branchcode_completeAFTER311217();
        }
        if (operator.isEmpty()) {
            operator = dbm.list_all_users_show().stream().map(u -> u.getCod()).collect(toList());
        }
        ArrayList<String[]> result = dbm.list_pending(data1, data2, br1, operator);
        dbm.closeDB();

        String cod = generaId(75);
        String base64 = null;
        if (result.size() > 0) {
            base64 = Utility.sanitize(excel_reportpending(new File(path + cod), result));
        }

        if (base64 != null) {
            String headerKey = "Content-Disposition";
            String sanitized_cod = Utility.sanitize(cod);
            String headerValue = format("attachment; filename=\"%s\"", new Object[] { sanitized_cod + ".xlsx" });
            response.setHeader(headerKey, headerValue);
            try (OutputStream outStream = response.getOutputStream()) {
                outStream.write(decodeBase64(base64.getBytes()));
            }
        } else {
            redirect(request, response, "page_404.html");
        }

    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void TillTransactionList(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String d3 = Utility.sanitize(request.getParameter("d3"));
        String d4 = Utility.sanitize(request.getParameter("d4"));
        String Output = Utility.sanitize(request.getParameter("Output"));
        if (null == Output) {
            Output = "EXC";
        } else {
            switch (Output) {
                case "on":
                    Output = "PDF";
                    break;
                default:
                    Output = "EXC";
                    break;
            }
        }

        String data1 = formatStringtoStringDate_null(d3, patternnormdate_filter, patternsql);
        String data2 = formatStringtoStringDate_null(d4, patternnormdate_filter, patternsql);

        Db_Master dbm = new Db_Master();
        String path = Utility.sanitize(dbm.getPath("temp"));
        // path = "F:\\com\\";
        String filiale = Utility.sanitize(request.getParameter("branch"));
        String fil[] = new String[2];
        if (filiale == null || filiale.equals("")) {
            fil = dbm.getCodLocal(false);
        } else {
            fil[0] = filiale;
            fil[1] = formatBankBranchReport(filiale, "BR", null, list_branch_completeAFTER311217());
        }
        TillTransactionList_value pdf = dbm.list_TillTransactionList_value(fil, data1, data2);
        dbm.closeDB();

        if (pdf != null) {
            TillTransactionList rma = new TillTransactionList();

            ArrayList<String> datifooter = new ArrayList<>();
            datifooter.add(pdf.getTransactionnumberresidentbuy());
            datifooter.add(pdf.getTransactionnumbernonresidentbuy());
            datifooter.add(pdf.getInternetbookingamountyes());
            datifooter.add(pdf.getInternetbookingnumberyes());
            datifooter.add(pdf.getPosbuyamount());
            datifooter.add(pdf.getPosbuynumber());
            datifooter.add(pdf.getBankbuyamount());
            datifooter.add(pdf.getBankbuynumber());
            datifooter.add(pdf.getTransactionnumberresidentsell());
            datifooter.add(pdf.getTransactionnumbernonresidentsell());
            datifooter.add(pdf.getInternetbookingamountno());
            datifooter.add(pdf.getInternetbookingnumberno());
            datifooter.add(pdf.getPossellamount());
            datifooter.add(pdf.getPossellnumber());
            datifooter.add(pdf.getBanksellamount());
            datifooter.add(pdf.getBanksellnumber());

            pdf.setFooterdati(datifooter);
            if (Output.equals("PDF")) {

                String base64 = Utility.sanitize(rma.receipt(path, pdf, d3, d4, "Till Transaction List", 0));
                if (base64 != null) {
                    String headerKey = "Content-Disposition";
                    String sanitized_d3 = Utility.sanitize(d3);
                    String sanitized_d4 = Utility.sanitize(d4);
                    String headerValue = format("attachment; filename=\"%s\"",
                            new Object[] { "TillTransactionList_" + sanitized_d3 + "_" + sanitized_d4 + ".pdf" });
                    response.setHeader(headerKey, headerValue);
                    try (OutputStream outStream = response.getOutputStream()) {
                        outStream.write(decodeBase64(base64.getBytes()));
                    }
                } else {
                    redirect(request, response, "page_404.html");
                }

            } else {

                String base64 = Utility.sanitize(rma.receiptexcel(path, pdf, d3, d4, "Till Transaction List", 0));

                if (base64 != null) {
                    String headerKey = "Content-Disposition";
                    String sanitized_d3 = Utility.sanitize(d3);
                    String sanitized_d4 = Utility.sanitize(d4);
                    String headerValue = format("attachment; filename=\"%s\"",
                            new Object[] { "TillTransactionList_" + sanitized_d3 + "_" + sanitized_d4 + ".xls" });
                    response.setHeader(headerKey, headerValue);
                    try (OutputStream outStream = response.getOutputStream()) {
                        outStream.write(decodeBase64(base64.getBytes()));
                    }
                } else {
                    redirect(request, response, "page_404.html");
                }

            }
        } else {
            redirect(request, response, "page_404.html");
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
            // request.setCharacterEncoding("UTF-8");
            String type = request.getParameter("type");

            String user = (String) request.getSession().getAttribute("us_cod");
            insertTR("W", user, "Generate report code: " + type);

            if (type.equals("lpos")) {
                lpos(request, response);
            }
            if (type.equals("lnch")) {
                lnch(request, response);
            }
            if (type.equals("lcha")) {
                lcha(request, response);
            }
            // if (type.equals("C_CasherPerformance")) {
            // C_CasherPerformanceR(request, response);
            // }
            if (type.equals("C_AnalysisCurrency")) {
                C_AnalysisCurrencyR(request, response);
            }
            if (type.equals("C_AnalysisDetailsTransactionCertificationExtraCEEforBranchAndPeriod")) {
                C_AnalysisDetailsTransactionCertificationExtraCEEforBranchAndPeriodR(request, response);
            }
            if (type.equals("C_AnalysisReprint")) {
                C_AnalysisReprintR(request, response);
            }
            if (type.equals("C_AnalysisReprintNoChange")) {
                C_AnalysisReprintNoChangeR(request, response);
            }
            // if (type.equals("C_CasherPerformance")) {
            // C_CasherPerformanceR(request, response);
            // }
            if (type.equals("C_ChangeInternetBookingForBranches")) {
                C_ChangeInternetBookingForBranchesR(request, response);
            }
            if (type.equals("C_ChangeMovimentDetailForBranches")) {
                C_ChangeMovimentDetailForBranchesR(request, response);
            }
            if (type.equals("C_ChangeMovimentForAgency")) {
                C_ChangeMovimentForAgencyR(request, response);
            }
            if (type.equals("C_ChangeMovimentForBranches")) {
                C_ChangeMovimentForBranchesR(request, response);
            }
            if (type.equals("C_ChangeVolumeAffair")) {
                C_ChangeVolumeAffairR(request, response);
            }
            if (type.equals("C_ChangeVolumeAffairCashAdvance")) {
                C_ChangeVolumeAffairCashAdvanceR(request, response);
            }
            if (type.equals("C_CloseBranch")) {
                C_CloseBranchR(request, response);
            }
            if (type.equals("C_Interbranch")) {
                C_InterbranchR(request, response);
            }
            if (type.equals("C_InterbranchDetails")) {
                C_InterbranchDetailsR(request, response);
            }
            if (type.equals("C_NoChangeMovimentForBranches")) {
                C_NoChangeMovimentForBranchesR(request, response);
            }
            if (type.equals("C_SizeAndQuantity")) {
                C_SizeAndQuantityR(request, response);
            }
            if (type.equals("C_StockInquiry")) {
                C_StockInquiryR(request, response);
            }
            if (type.equals("C_WesternUnion")) {
                C_WesternUnionR(request, response);
            }

            if (type.equals("C_FreeTax_AmountBranch")) {
                C_FreeTax_AmountBranch(request, response);
            }

            if (type.equals("C_CasherOpenCloseError")) {

                C_CasherOpenCloseErrorR(request, response);
            }

            if (type.equals("C_CashierPerformance")) {
                C_CashierPerformance(request, response);
            }
            if (type.equals("C_CashierDetails")) {
                C_CashierDetails(request, response);
            }
            if (type.equals("C_TransactionRegisterSummary")) {
                C_TransactionRegisterSummary(request, response);
            }
            if (type.equals("C_TransactionRegisterDetail")) {
                C_TransactionRegisterDetail(request, response);
            }
            if (type.equals("C_FreeTax_Analisys")) {
                C_FreeTax_Analisys(request, response);
            }
            if (type.equals("C_FreeTax_Branch")) {
                C_FreeTax_Branch(request, response);
            }
            if (type.equals("C_FreeTax_BranchCausal")) {
                C_FreeTax_BranchCausal(request, response);
            }
            if (type.equals("C_FreeTax_CausalBranch")) {
                C_FreeTax_CausalBranch(request, response);
            }
            if (type.equals("C_FreeTax_BranchCurrency")) {
                C_FreeTax_BranchCurrency(request, response);
            }
            if (type.equals("C_FreeTax_AmountNation")) {
                C_FreeTax_AmountNation(request, response);
            }
            if (type.equals("C_FreeTax_NationAmount")) {
                C_FreeTax_NationAmount(request, response);
            }
            if (type.equals("C_freeTaxPivotTotale")) {
                C_freeTaxPivotTotale(request, response);
            }
            if (type.equals("C_BankBranch")) {
                C_BankBranch(request, response);
            }
            if (type.equals("C_OfficeStockPrice")) {
                C_OfficeStockPrice(request, response);
            }
            if (type.equals("C_OfficeStockPrice_now")) {
                C_OfficeStockPrice_now(request, response);
            }

            if (type.equals("C_OfficeStockPriceCurrency")) {
                C_OfficeStockPriceCurrency(request, response);
            }
            if (type.equals("C_OpenCloseError")) {
                C_OpenCloseError(request, response, false);
            }
            if (type.equals("QuarterlyCZK")) {
                QuarterlyCZK(request, response);
            }
            if (type.equals("C_CustomerCareRefund")) {
                C_CustomerCareRefundR(request, response);
            }
            if (type.equals("CustomerTransactionList_t")) {
                CustomerTransactionList_t(request, response);
            }
            if (type.equals("CustomerTransactionList_s")) {

            }
            if (type.equals("CustomerTransactionList_AV")) {
                CustomerTransactionList_AF(request, response);
            }
            if (type.equals("CustomerTransactionList_AF")) {
                CustomerTransactionList_AF(request, response);
            }
            if (type.equals("DailyCentral")) {
                DailyCentral(request, response);
            }
            if (type.equals("pending")) {
                pending(request, response);
            }

            if (type.equals("CACZ")) {
                CACZ(request, response);
            }
            if (type.equals("DailyError")) {
                DailyError(request, response);
            }
            if (type.equals("HistoricalStockPrice")) {
                HistoricalStockPrice(request, response);
            }
            if (type.equals("Marketing")) {
                Marketing(request, response);
            }
            if (type.equals("BranchStockInquiryA")) {
                BranchStockInquiryA(request, response);
            }

        } catch (ServletException | IOException ex) {
            insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the
    // + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
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
