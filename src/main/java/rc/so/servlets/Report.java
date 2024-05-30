/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rc.so.servlets;

import rc.so.db.Db_Master;
import rc.so.entity.Branch;
import rc.so.entity.ET_change;
import rc.so.entity.NC_category;
import rc.so.entity.Openclose;
import static rc.so.pdf.NewETReceipt.FROMBANK_pdf;
import static rc.so.pdf.NewETReceipt.FROMBANK_xls;
import static rc.so.pdf.NewETReceipt.FROMBRANCH_CHANGE_pdf;
import static rc.so.pdf.NewETReceipt.FROMBRANCH_CHANGE_xls;
import static rc.so.pdf.NewETReceipt.FROMBRANCH_NOCHANGE_pdf;
import static rc.so.pdf.NewETReceipt.FROMBRANCH_NOCHANGE_xls;
import static rc.so.pdf.NewETReceipt.TOBANK_POS_pdf;
import static rc.so.pdf.NewETReceipt.TOBANK_POS_xls;
import static rc.so.pdf.NewETReceipt.TOBANK_pdf;
import static rc.so.pdf.NewETReceipt.TOBANK_xls;
import static rc.so.pdf.NewETReceipt.TOBRANCH_CHANGE_pdf;
import static rc.so.pdf.NewETReceipt.TOBRANCH_CHANGE_xls;
import static rc.so.pdf.NewETReceipt.TOBRANCH_NOCHANGE_pdf;
import static rc.so.pdf.NewETReceipt.TOBRANCH_NOCHANGE_xls;
import rc.so.report.BranchStockInquiry;
import rc.so.report.BranchStockInquiry_value;
import rc.so.report.Daily;
import rc.so.report.Daily_value;
import rc.so.report.FromBankingSheet;
import rc.so.report.FromBranchingSheet;
import rc.so.report.FromBranchingSheet_value;
import rc.so.report.HeavyTransactionList;
import rc.so.report.HeavyTransactionList_value;
import rc.so.report.InsuranceTransactionList;
import rc.so.report.InsuranceTransactionList_value;
import rc.so.report.InternalTransferList;
import rc.so.report.InternalTransferList_value;
import rc.so.report.NoChangeBonus;
import rc.so.report.NoChangeBonus_value;
import rc.so.report.NoChangeCategoryTransactionList;
import rc.so.report.NoChangeCategoryTransactionList_value;
import rc.so.report.NoChangeFromBranchingSheet_value;
import rc.so.report.NoChangeInternalTransferList_value;
import rc.so.report.NoChangeToBranchingSheet_value;
import rc.so.report.NoChangeTransactionList;
import rc.so.report.NoChangeTransactionListForUser;
import rc.so.report.NoChangeTransactionListForUser_value;
import rc.so.report.NoChangeTransactionList_value;
import rc.so.report.OfficeStockPrice;
import rc.so.report.OfficeStockPrice_value;
import rc.so.report.Openclose_Anal;
import rc.so.report.Openclose_Anal_value;
import rc.so.report.Openclose_Anal_value_stock;
import rc.so.report.Openclose_Error;
import rc.so.report.Openclose_Error_value;
import rc.so.report.Openclose_Error_value_stock;
import rc.so.report.Openclose_Synt;
import rc.so.report.Openclose_Synt_value;
import rc.so.report.RegisterBuyMonthly;
import rc.so.report.RegisterBuyMonthly_value;
import rc.so.report.RegisterMonthly;
import rc.so.report.RegisterMonthly_value;
import rc.so.report.RegisterSellMonthly;
import rc.so.report.RegisterSellMonthly_value;
import rc.so.report.StockInquiry;
import rc.so.report.StockInquiry_value;
import rc.so.report.StockPrice;
import rc.so.report.StockPrice_value;
import rc.so.report.StockReport;
import rc.so.report.StockReport_value;
import rc.so.report.TaxFree;
import rc.so.report.TaxFree_value;
import rc.so.report.TillTransactionList;
import rc.so.report.TillTransactionListBB;
import rc.so.report.TillTransactionListBB_value;
import rc.so.report.TillTransactionListCurrency;
import rc.so.report.TillTransactionListCurrency_value;
import rc.so.report.TillTransactionList_value;
import rc.so.report.ToBankingSheet;
import rc.so.report.ToBankingSheet_value;
import rc.so.report.ToBranchingSheet;
import rc.so.report.ToBranchingSheet_value;
import rc.so.report.ToPOSBASheet;
import rc.so.report.TotalStockReport;
import rc.so.report.TotalStockReport_value;
import rc.so.report.WesternUnionService;
import rc.so.report.WesternUnionService_value;
import rc.so.reportcentrale.C_SizeAndQuantity;
import static rc.so.util.Constant.is_CZ;
import static rc.so.util.Constant.is_IT;
import static rc.so.util.Constant.is_UK;
import static rc.so.util.Constant.localcurrency;
import static rc.so.util.Constant.newpread;
import static rc.so.util.Constant.pattermonthnorm;
import static rc.so.util.Constant.patternmonthsql;
import static rc.so.util.Constant.patternnormdate;
import static rc.so.util.Constant.patternnormdate_f;
import static rc.so.util.Constant.patternnormdate_filter;
import static rc.so.util.Constant.patternsql;
import static rc.so.util.Constant.patternsql_f;
import static rc.so.util.Constant.patternsqldate;
import static rc.so.util.Engine.formatBankBranch;
import static rc.so.util.Engine.formatBankBranchReport;
import static rc.so.util.Engine.get_Branch;
import static rc.so.util.Engine.get_ET_change;
import static rc.so.util.Engine.insertTR;
import static rc.so.util.Engine.list_branch_completeAFTER311217;
import static rc.so.util.Engine.verifySession;
import static rc.so.util.Utility.formatMonthtoDate_null;
import static rc.so.util.Utility.formatStringtoStringDate;
import static rc.so.util.Utility.formatStringtoStringDate_null;
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
import static rc.so.util.Utility.parseString;
import static rc.so.util.Utility.redirect;
import static java.lang.String.format;
import static java.lang.System.setProperty;
import static java.lang.Thread.currentThread;
import java.util.List;
import static java.util.stream.Collectors.toList;
import static org.apache.commons.codec.binary.Base64.decodeBase64;
import static rc.so.util.Engine.getNowDT;
import rc.so.util.Utility;

/**
 *
 * @author rcosco
 */
public class Report extends HttpServlet {

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void Openclose_Error(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String d4 = Utility.sanitize(request.getParameter("d4"));
        String d5 = Utility.sanitize(request.getParameter("d5"));
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
        //path = "F:\\com\\";

        String filiale = Utility.sanitize(request.getParameter("branch"));
        String fil[] = new String[2];
        if (filiale == null || filiale.equals("")) {
            fil = dbm.getCodLocal(false);
        } else {
            fil[0] = filiale;
            fil[1] = formatBankBranchReport(filiale, "BR", null, list_branch_completeAFTER311217());
        }

        String data1 = formatStringtoStringDate_null(d4, patternnormdate_filter, patternsql);
        String data2 = formatStringtoStringDate_null(d5, patternnormdate_filter, patternsql);
        ArrayList<Branch> listallbr = dbm.list_branch_completeAFTER311217();
        ArrayList<Openclose> list = dbm.list_openclose_errors_report(fil[0], data1, data2);
        dbm.closeDB();
        if (list.size() > 0) {
            Openclose_Error osp = new Openclose_Error();

            ArrayList<Openclose_Error_value> pdfl = new ArrayList<>();
            Openclose_Error_value pdf = new Openclose_Error_value();

            pdf.setId_filiale(fil[0]);
            pdf.setDe_filiale(fil[1]);

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

            String localcurrency = dbm1.get_local_currency()[0];

            for (int i = 0; i < list.size(); i++) {
                Openclose co = list.get(i);
                ArrayList<String[]> list_oc_errors = dbm1.list_oc_errors(co.getCod());
                ArrayList<Openclose_Error_value> dati = dbm1.list_openclose_errors_report(co, list_oc_errors, listallbr, null);
                if (dati.size() > 0) {
                    Openclose_Error_value_stock dpf = new Openclose_Error_value_stock(dati, co.getId());
                    datifilialeperreport.add(dpf);
                }
            }
            dbm1.closeDB();
            pdf.setDati(datifilialeperreport);
            pdfl.add(pdf);
            if (Output.equals("PDF")) {
                String base64 = Utility.sanitize(osp.receipt(path, pdfl, alcolonne, alcolonnedetaglierrore, alcolonnedetaglierroreNoChange, alcolonnedetaglierrorePos, d4, d5, false));

                if (base64 != null) {
                    String headerKey = "Content-Disposition";
                    String sanitized_d4 = Utility.sanitize(d4);
                    String sanitized_d5 = Utility.sanitize(d5);
                    String headerValue = format("attachment; filename=\"%s\"", new Object[]{"OpencloseErrors" + "_" + sanitized_d4 + "_" + sanitized_d5 + ".pdf"});
                    response.setHeader(headerKey, headerValue);
                    try (OutputStream outStream = response.getOutputStream()) {
                        outStream.write(decodeBase64(base64.getBytes()));
                    }
                } else {
                    redirect(request, response, "page_404.html");
                }
            } else {

                String base64 = Utility.sanitize(osp.main_Excel(path, pdfl, d4, d5, localcurrency, false, null));

                if (base64 != null) {
                    String headerKey = "Content-Disposition";
                    String sanitized_d4 = Utility.sanitize(d4);
                    String sanitized_d5 = Utility.sanitize(d5);
                    String headerValue = format("attachment; filename=\"%s\"", new Object[]{"OpencloseErrors" + "_" + sanitized_d4 + "_" + sanitized_d5 + "_" + ".xlsx"});
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
    protected void Openclose_Anal(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String idopenclose = Utility.sanitize(request.getParameter("idopenclose"));
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

        ArrayList<String> alcolonne = new ArrayList<>();
        alcolonne.add("Currency");
        alcolonne.add("Kind");
        alcolonne.add("Quantity");
        alcolonne.add("Total");

        Db_Master dbm = new Db_Master();
        String path = Utility.sanitize(dbm.getPath("temp"));
        //path = "F:\\com\\";
        String filiale = Utility.sanitize(request.getParameter("branch"));
        String fil[] = new String[2];
        if (filiale == null || filiale.equals("")) {
            fil = dbm.getCodLocal(false);
        } else {
            fil[0] = filiale;
            fil[1] = formatBankBranchReport(filiale, "BR", null, list_branch_completeAFTER311217());
        }

        Openclose_Anal osp = new Openclose_Anal();
        Openclose oc = dbm.query_oc(idopenclose);
        Openclose_Anal_value pdf = dbm.list_Openclose_Anal_value(oc, fil);
        ArrayList<Openclose_Anal_value> dati = dbm.list_Openclose_Anal_value(oc);
        ArrayList<Openclose_Anal_value_stock> datiStock = dbm.list_Openclose_Anal_value_stock(oc);
        ArrayList<Openclose_Anal_value_stock> datiStockpos = dbm.list_Openclose_Anal_value_stockpos(oc);
        ArrayList<Openclose_Anal_value_stock> datiStockbank = dbm.list_Openclose_Anal_value_stockbank(oc);
        pdf.setDati(dati);
        dbm.closeDB();

        if (dati.size() > 0) {

            if (Output.equals("PDF")) {

                String base64 = Utility.sanitize(osp.receipt(path, pdf, datiStock, datiStockpos, datiStockbank, alcolonne));
                if (base64 != null) {
                    String headerKey = "Content-Disposition";
                    String headerValue = format("attachment; filename=\"%s\"", new Object[]{"OpencloseAnalytical" + "_" + new DateTime().toString("ddMMyyyy") + "_" + ".pdf"});
                    response.setHeader(headerKey, headerValue);
                    try (OutputStream outStream = response.getOutputStream()) {
                        outStream.write(decodeBase64(base64.getBytes()));
                    }
                } else {
                    redirect(request, response, "page_404.html");
                }
            } else {

                String base64 = Utility.sanitize(osp.receiptExcel(path, pdf, datiStock, datiStockpos, datiStockbank, alcolonne));
                if (base64 != null) {
                    String headerKey = "Content-Disposition";
                    String headerValue = format("attachment; filename=\"%s\"", new Object[]{"OpencloseAnalytical" + "_" + new DateTime().toString("ddMMyyyy") + "_" + ".xls"});
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
    protected void Openclose_Synt(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String datereport = Utility.sanitize(request.getParameter("d3"));
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
        //path = "F:\\com\\";
        String filiale = Utility.sanitize(request.getParameter("branch"));
        String fil[] = new String[2];
        if (filiale == null || filiale.equals("")) {
            fil = dbm.getCodLocal(false);
        } else {
            fil[0] = filiale;
            fil[1] = formatBankBranchReport(filiale, "BR", null, list_branch_completeAFTER311217());
        }

        ArrayList<Openclose_Synt_value> dati = dbm.list_Openclose_Synt_value(fil[0], datereport);
        dbm.closeDB();

        if (dati.size() > 0) {
            Openclose_Synt osp = new Openclose_Synt();
            Openclose_Synt_value pdf = new Openclose_Synt_value();
            ArrayList<String> alcolonne = new ArrayList<>();
            alcolonne.add("User");
            alcolonne.add("Safe/Till");
            alcolonne.add("Date");
            alcolonne.add("Operation");
            alcolonne.add("Type");
            alcolonne.add("No.Error");
            pdf.setId_filiale(fil[0]);
            pdf.setDe_filiale(fil[1]);
            pdf.setDati(dati);
            if (Output.equals("PDF")) {

                String base64 = Utility.sanitize(osp.receipt(path, pdf, alcolonne, datereport));

                if (base64 != null) {
                    String headerKey = "Content-Disposition";
                    String sanitized_datereport = Utility.sanitize(datereport);
                    String headerValue = format("attachment; filename=\"%s\"", new Object[]{"OpencloseSynthetical" + "_" + sanitized_datereport + ".pdf"});
                    response.setHeader(headerKey, headerValue);
                    try (OutputStream outStream = response.getOutputStream()) {
                        outStream.write(decodeBase64(base64.getBytes()));
                    }
                } else {
                    redirect(request, response, "page_404.html");
                }

            } else {

                String base64 = Utility.sanitize(osp.receiptExcel(path, pdf, alcolonne, datereport));
                if (base64 != null) {
                    String headerKey = "Content-Disposition";
                    String sanitized_datereport = Utility.sanitize(datereport);
                    String headerValue = format("attachment; filename=\"%s\"", new Object[]{"OpencloseSyntheticalt" + "_" + sanitized_datereport + ".xls"});
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
    protected void RegisterMonthly(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String datereport1 = Utility.sanitize(request.getParameter("d3"));
        String datereport2 = Utility.sanitize(request.getParameter("d4"));
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
        String data1 = formatStringtoStringDate_null(datereport1, patternnormdate_filter, patternsql);
        String data2 = formatStringtoStringDate_null(datereport2, patternnormdate_filter, patternsql);

        Db_Master dbm = new Db_Master();
        String path = Utility.sanitize(dbm.getPath("temp"));
        //path = "F:\\com\\";
        String filiale = Utility.sanitize(request.getParameter("branch"));
        String fil[] = new String[2];
        if (filiale == null || filiale.equals("")) {
            fil = dbm.getCodLocal(false);
        } else {
            fil[0] = filiale;
            fil[1] = formatBankBranchReport(filiale, "BR", null, list_branch_completeAFTER311217());
        }

        ArrayList<RegisterMonthly_value> dati = dbm.list_RegisterMonthly_value(fil[0], data1, data2);
        dbm.closeDB();

        if (dati.size() > 0) {
            RegisterMonthly rma = new RegisterMonthly();
            RegisterMonthly_value pdf = new RegisterMonthly_value();
            pdf.setId_filiale(fil[0]);
            pdf.setDe_filiale(fil[1]);
            pdf.setDati(dati);

            if (Output.equals("PDF")) {
                String base64 = Utility.sanitize(rma.receipt(path, pdf, datereport1, datereport2));
                if (base64 != null) {
                    String headerKey = "Content-Disposition";
                    String sanitized_datereport1 = Utility.sanitize(datereport1);
                    String sanitized_datereport2 = Utility.sanitize(datereport2);
                    String headerValue = format("attachment; filename=\"%s\"", new Object[]{"RegisterUICFull" + "_" + sanitized_datereport1 + "_" + sanitized_datereport2 + ".pdf"});
                    response.setHeader(headerKey, headerValue);
                    try (OutputStream outStream = response.getOutputStream()) {
                        outStream.write(decodeBase64(base64.getBytes()));
                    }
                } else {
                    redirect(request, response, "page_404.html");
                }

            } else {

                String base64 = Utility.sanitize(rma.receiptexcel(path, pdf, datereport1, datereport2));

                if (base64 != null) {
                    String headerKey = "Content-Disposition";
                    String sanitized_datereport1 = Utility.sanitize(datereport1);
                    String sanitized_datereport2 = Utility.sanitize(datereport2);
                    String headerValue = format("attachment; filename=\"%s\"", new Object[]{"RegisterUICFull" + "_" + sanitized_datereport1 + "_" + sanitized_datereport2 + ".xls"});
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
    protected void RegisterSellMonthlyr(HttpServletRequest request, HttpServletResponse response)
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
        //path = "F:\\com\\";
        String filiale = Utility.sanitize(request.getParameter("branch"));
        String fil[] = new String[2];
        if (filiale == null || filiale.equals("")) {
            fil = dbm.getCodLocal(false);
        } else {
            fil[0] = filiale;
            fil[1] = formatBankBranchReport(filiale, "BR", null, list_branch_completeAFTER311217());
        }
        ArrayList<RegisterSellMonthly_value> dati = dbm.list_RegisterSellMonthly_value(fil[0], data1, data2);
        dbm.closeDB();

        if (dati.size() > 0) {
            RegisterSellMonthly rma = new RegisterSellMonthly();
            RegisterSellMonthly_value pdf = new RegisterSellMonthly_value();
            pdf.setId_filiale(fil[0]);
            pdf.setDe_filiale(fil[1]);
            pdf.setDati(dati);

            if (Output.equals("PDF")) {

                String base64 = Utility.sanitize(rma.receipt(path, pdf, d3, d4));
                if (base64 != null) {
                    String headerKey = "Content-Disposition";
                    String sanitized_d3 = Utility.sanitize(d3);
                    String sanitized_d4 = Utility.sanitize(d4);
                    String headerValue = format("attachment; filename=\"%s\"", new Object[]{"RegisterUICSell_" + sanitized_d3 + "_" + sanitized_d4 + ".pdf"});
                    response.setHeader(headerKey, headerValue);
                    try (OutputStream outStream = response.getOutputStream()) {
                        outStream.write(decodeBase64(base64.getBytes()));
                    }
                } else {
                    redirect(request, response, "page_404.html");
                }

            } else {

                String base64 = Utility.sanitize(rma.receiptexcel(path, pdf, d3, d4));

                if (base64 != null) {
                    String headerKey = "Content-Disposition";
                    String sanitized_d3 = Utility.sanitize(d3);
                    String sanitized_d4 = Utility.sanitize(d4);
                    String headerValue = format("attachment; filename=\"%s\"", new Object[]{"RegisterUICSell_" + sanitized_d3 + "_" + sanitized_d4 + ".xls"});
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
    protected void RegisterBuyMonthly(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

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
        //path = "F:\\com\\";
        String filiale = Utility.sanitize(request.getParameter("branch"));
        String fil[] = new String[2];
        if (filiale == null || filiale.equals("")) {
            fil = dbm.getCodLocal(false);
        } else {
            fil[0] = filiale;
            fil[1] = formatBankBranchReport(filiale, "BR", null, list_branch_completeAFTER311217());
        }
        ArrayList<RegisterBuyMonthly_value> dati = dbm.list_RegisterBuyMonthly_value(fil[0], data1, data2);
        dbm.closeDB();

        if (dati.size() > 0) {
            RegisterBuyMonthly rma = new RegisterBuyMonthly();
            RegisterBuyMonthly_value pdf = new RegisterBuyMonthly_value();
            ArrayList<String> colonne = new ArrayList<>();
            colonne.add(" ");
            colonne.add("Currency");
            colonne.add("Transfer from user");
            colonne.add("Purchases by Customer in Currency");
            colonne.add("Equivalent");
            colonne.add("Transert from Bank");
            colonne.add("Total Currency");
            colonne.add("Total");
            pdf.setId_filiale(fil[0]);
            pdf.setDe_filiale(fil[1]);
            pdf.setDati(dati);

            if (Output.equals("PDF")) {

                String base64 = Utility.sanitize(rma.receipt(path, pdf, colonne, d3, d4));
                if (base64 != null) {
                    String headerKey = "Content-Disposition";
                    String sanitized_d3 = Utility.sanitize(d3);
                    String sanitized_d4 = Utility.sanitize(d4);
                    String headerValue = format("attachment; filename=\"%s\"", new Object[]{"RegisterUICBuy_" + sanitized_d3 + "_" + sanitized_d4 + ".pdf"});
                    response.setHeader(headerKey, headerValue);
                    try (OutputStream outStream = response.getOutputStream()) {
                        outStream.write(decodeBase64(base64.getBytes()));
                    }
                } else {
                    redirect(request, response, "page_404.html");
                }

            } else {

                String base64 = Utility.sanitize(rma.receiptExcel(path, pdf, colonne, d3, d4));

                if (base64 != null) {
                    String headerKey = "Content-Disposition";
                    String sanitized_d3 = Utility.sanitize(d3);
                    String sanitized_d4 = Utility.sanitize(d4);
                    String headerValue = format("attachment; filename=\"%s\"", new Object[]{"RegisterUICBuy_" + sanitized_d3 + "_" + sanitized_d4 + ".xls"});
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
    protected void SanctionAttempts(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

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
        //path = "F:\\com\\";
        String filiale = Utility.sanitize(request.getParameter("branch"));
        String fil[] = new String[2];
        if (filiale == null || filiale.equals("")) {
            fil = dbm.getCodLocal(false);
        } else {
            fil[0] = filiale;
            fil[1] = formatBankBranchReport(filiale, "BR", null, list_branch_completeAFTER311217());
        }
        TillTransactionList_value pdf = dbm.list_SanctionAttempts(fil, data1, data2);
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

                String base64 = Utility.sanitize(rma.receipt(path, pdf, d3, d4, "Sanction Attempts", 0));
                if (base64 != null) {
                    String headerKey = "Content-Disposition";
                    String sanitized_d3 = Utility.sanitize(d3);
                    String sanitized_d4 = Utility.sanitize(d4);
                    String headerValue = format("attachment; filename=\"%s\"", new Object[]{"SanctionAttempts_" + sanitized_d3 + "_" + sanitized_d4 + ".pdf"});
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
                    String headerValue = format("attachment; filename=\"%s\"", new Object[]{"TillTransactionList_" + sanitized_d3 + "_" + sanitized_d4 + ".xls"});
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
    protected void TillTransactionList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

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
        //path = "F:\\com\\";
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
                    String headerValue = format("attachment; filename=\"%s\"", new Object[]{"TillTransactionList_" + sanitized_d3 + "_" + sanitized_d4 + ".pdf"});
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
                    String headerValue = format("attachment; filename=\"%s\"", new Object[]{"TillTransactionList_" + sanitized_d3 + "_" + sanitized_d4 + ".xls"});
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
    protected void TillTransactionListCurrency(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

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

        ArrayList<TillTransactionListCurrency_value> dati = new ArrayList<>();
        Db_Master dbm = new Db_Master();
        String path = Utility.sanitize(dbm.getPath("temp"));
        //path = "F:\\com\\";
        String filiale = Utility.sanitize(request.getParameter("branch"));
        String fil[] = new String[2];
        if (filiale == null || filiale.equals("")) {
            fil = dbm.getCodLocal(false);
        } else {
            fil[0] = filiale;
            fil[1] = formatBankBranchReport(filiale, "BR", null, list_branch_completeAFTER311217());
        }
        TillTransactionListCurrency_value pdf = dbm.list_TillTransactionListCurrency_value(fil, data1, data2);
        dbm.closeDB();

        if (pdf != null) {
            TillTransactionListCurrency rma = new TillTransactionListCurrency();
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

                String base64 = Utility.sanitize(rma.receipt(path, pdf, d3, d4));
                if (base64 != null) {
                    String headerKey = "Content-Disposition";
                    String sanitized_d3 = Utility.sanitize(d3);
                    String sanitized_d4 = Utility.sanitize(d4);
                    String headerValue = format("attachment; filename=\"%s\"", new Object[]{"TillTransactionListCurrency_" + sanitized_d3 + "_" + sanitized_d4 + ".pdf"});
                    response.setHeader(headerKey, headerValue);
                    try (OutputStream outStream = response.getOutputStream()) {
                        outStream.write(decodeBase64(base64.getBytes()));
                    }
                } else {
                    redirect(request, response, "page_404.html");
                }

            } else {

                String base64 = Utility.sanitize(rma.receiptexcel(path, pdf, d3, d4));

                if (base64 != null) {
                    String headerKey = "Content-Disposition";
                    String sanitized_d3 = Utility.sanitize(d3);
                    String sanitized_d4 = Utility.sanitize(d4);
                    String headerValue = format("attachment; filename=\"%s\"", new Object[]{"TillTransactionListCurrency_" + sanitized_d3 + "_" + sanitized_d4 + ".xls"});
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
    protected void HeavyTransactionList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

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
        //path = "F:\\com\\";
        String filiale = Utility.sanitize(request.getParameter("branch"));
        String fil[] = new String[2];
        if (filiale == null || filiale.equals("")) {
            fil = dbm.getCodLocal(false);
        } else {
            fil[0] = filiale;
            fil[1] = formatBankBranchReport(filiale, "BR", null, list_branch_completeAFTER311217());
        }
        ArrayList<HeavyTransactionList_value> dati = dbm.list_HeavyTransactionList_value(fil, data1, data2);
        dbm.closeDB();

        if (dati.size() > 0) {
            HeavyTransactionList rma = new HeavyTransactionList();
            HeavyTransactionList_value pdf = new HeavyTransactionList_value();
            pdf.setId_filiale(fil[0]);
            pdf.setDe_filiale(fil[1]);
            pdf.setDati(dati);
            if (Output.equals("PDF")) {

                String base64 = Utility.sanitize(rma.receipt(path, pdf, d3, d4));

                if (base64 != null) {
                    String headerKey = "Content-Disposition";
                    String sanitized_d3 = Utility.sanitize(d3);
                    String sanitized_d4 = Utility.sanitize(d4);
                    String headerValue = format("attachment; filename=\"%s\"", new Object[]{"HeavyTransactionList_" + sanitized_d3 + "_" + sanitized_d4 + ".pdf"});
                    response.setHeader(headerKey, headerValue);
                    try (OutputStream outStream = response.getOutputStream()) {
                        outStream.write(decodeBase64(base64.getBytes()));
                    }
                } else {
                    redirect(request, response, "page_404.html");
                }

            } else {

                String base64 = Utility.sanitize(rma.receiptexcel(path, pdf, d3, d4));

                if (base64 != null) {
                    String headerKey = "Content-Disposition";
                    String sanitized_d3 = Utility.sanitize(d3);
                    String sanitized_d4 = Utility.sanitize(d4);
                    String headerValue = format("attachment; filename=\"%s\"", new Object[]{"HeavyTransactionList_" + sanitized_d3 + "_" + sanitized_d4 + ".xls"});
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
    protected void DeleteTransactionList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
        //path = "F:\\com\\";
        String filiale = request.getParameter("branch");
        String fil[] = new String[2];
        if (filiale == null || filiale.equals("")) {
            fil = dbm.getCodLocal(false);
        } else {
            fil[0] = filiale;
            fil[1] = formatBankBranchReport(filiale, "BR", null, list_branch_completeAFTER311217());
        }
        TillTransactionList_value pdf = dbm.list_DeleteTransactionList_value(fil, data1, data2);
        dbm.closeDB();

        if (pdf != null) {
            TillTransactionList rma = new TillTransactionList(true);

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

                String base64 = rma.receipt(path, pdf, d3, d4, "Delete Transaction List", 0);
                if (base64 != null) {
                    String headerKey = "Content-Disposition";
                    String sanitized_d3 = Utility.sanitize(d3);
                    String sanitized_d4 = Utility.sanitize(d4);
                    String headerValue = format("attachment; filename=\"%s\"", new Object[]{"DeleteTransactionList_" + sanitized_d3 + "_" + sanitized_d4 + ".pdf"});
                    response.setHeader(headerKey, headerValue);
                    try (OutputStream outStream = response.getOutputStream()) {
                        outStream.write(decodeBase64(base64.getBytes()));
                    }
                } else {
                    redirect(request, response, "page_404.html");
                }

            } else {

                String base64 = Utility.sanitize(rma.receiptexcel(path, pdf, d3, d4, "Delete Transaction List", 0));

                if (base64 != null) {
                    String headerKey = "Content-Disposition";
                    String sanitized_d3 = Utility.sanitize(d3);
                    String sanitized_d4 = Utility.sanitize(d4);
                    String headerValue = format("attachment; filename=\"%s\"", new Object[]{"DeleteTransactionList_" + sanitized_d3 + "_" + sanitized_d4 + ".xls"});
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
    protected void ToBranchingSheet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String idopenclose = Utility.sanitize(request.getParameter("idopenclose"));
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

        ET_change et = get_ET_change(idopenclose);

        if (et.getFg_tofrom().equals("T")) {
            Db_Master dbm = new Db_Master();
            String path = Utility.sanitize(dbm.getPath("temp"));
            Branch dest = dbm.get_branch(et.getCod_dest());
            //path = "F:\\com\\";
            String filiale = Utility.sanitize(request.getParameter("branch"));
            String fil[] = new String[2];
            if (filiale == null || filiale.equals("")) {
                fil[0] = et.getFiliale();
                fil[1] = formatBankBranchReport(et.getFiliale(), "BR", null, list_branch_completeAFTER311217());
            } else {
                fil[0] = filiale;
                fil[1] = formatBankBranchReport(filiale, "BR", null, list_branch_completeAFTER311217());
            }

            ArrayList<ToBranchingSheet_value> dati = dbm.list_ToBranchingSheet_value(et);
            ArrayList<NoChangeToBranchingSheet_value> dati2 = dbm.list_ToBranchingSheet_valueNC(et);
            dbm.closeDB();
            if (dati.size() > 0 || dati2.size() > 0) {
                ToBranchingSheet bsi = new ToBranchingSheet();
                ToBranchingSheet_value pdf = new ToBranchingSheet_value();
                ArrayList<String> alcolonne = new ArrayList<>();
                alcolonne.add("Currency");
                alcolonne.add("Amount");
                alcolonne.add("Branch Rate");
                alcolonne.add("Amount");
                alcolonne.add("Branch Rate");
                alcolonne.add("Branch Total");
                alcolonne.add("Spread");
                alcolonne.add("%");

                ArrayList<String> alcolonne2 = new ArrayList<>();
                alcolonne2.add("Category");
                alcolonne2.add("Quantity");
                alcolonne2.add("Amount");
                alcolonne2.add("Total");

                pdf.setId_filiale(fil[0]);
                pdf.setDe_filiale(fil[1]);
                pdf.setFromsafe(fil[0] + " - " + fil[1]);
                pdf.setTobranch(dest.getCod() + " - " + dest.getDe_branch());
                pdf.setPinuser(et.getUser());
                pdf.setNumtranfer(et.getId());
                pdf.setNote(et.getNote());
                pdf.setDati(dati);
                if (Output.equals("PDF")) {
                    String base64;
                    if (newpread) {
                        if (dati.size() > 0) {
                            base64 = Utility.sanitize(TOBRANCH_CHANGE_pdf(path, pdf, formatStringtoStringDate(et.getDt_it(), patternsqldate, patternnormdate)));
                        } else {
                            base64 = Utility.sanitize(TOBRANCH_NOCHANGE_pdf(path, pdf, formatStringtoStringDate(et.getDt_it(), patternsqldate, patternnormdate), dati2));
                        }
                    } else {
                        base64 = Utility.sanitize(bsi.receipt(path, pdf, alcolonne, formatStringtoStringDate(et.getDt_it(), patternsqldate, patternnormdate), alcolonne2, dati2));
                    }
                    if (base64 != null) {
                        String headerKey = "Content-Disposition";
                        String headerValue = format("attachment; filename=\"%s\"", new Object[]{"ToBranchingSheet_" + new DateTime().toString("ddMMyyyy") + ".pdf"});
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
                        if (dati.size() > 0) {
                            base64 = Utility.sanitize(TOBRANCH_CHANGE_xls(path, pdf, formatStringtoStringDate(et.getDt_it(), patternsqldate, patternnormdate)));
                        } else {
                            base64 = Utility.sanitize(TOBRANCH_NOCHANGE_xls(path, pdf, formatStringtoStringDate(et.getDt_it(), patternsqldate, patternnormdate), dati2));
                        }
                    } else {
                        base64 = bsi.receiptexcel(path, pdf, alcolonne, formatStringtoStringDate(et.getDt_it(), patternsqldate, patternnormdate), alcolonne2, dati2);
                    }
                    if (base64 != null) {
                        String headerKey = "Content-Disposition";
                        String headerValue = format("attachment; filename=\"%s\"", new Object[]{"ToBranchingSheet_" + new DateTime().toString("ddMMyyyy") + ".xlsx"});
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
        } else {
            Db_Master dbm = new Db_Master();

            String note_fromBranch = dbm.note_fromBranch(et.getCod_in());

            ArrayList<FromBranchingSheet_value> dati = dbm.list_FromBranchingSheet_value(et);
            ArrayList<NoChangeFromBranchingSheet_value> dati2 = dbm.list_FromBranchingSheet_valueNC(et);
            Branch dest = dbm.get_branch(et.getCod_dest());
            String path = Utility.sanitize(dbm.getPath("temp"));
            //path = "F:\\com\\";
            String filiale = Utility.sanitize(request.getParameter("branch"));
            String fil[] = new String[2];
            if (filiale == null || filiale.equals("")) {
                fil[0] = et.getFiliale();
                fil[1] = formatBankBranchReport(et.getFiliale(), "BR", null, list_branch_completeAFTER311217());
            } else {
                fil[0] = filiale;
                fil[1] = formatBankBranchReport(filiale, "BR", null, list_branch_completeAFTER311217());
            }
            dbm.closeDB();
            if (dati.size() > 0 || dati2.size() > 0) {
                FromBranchingSheet bsi = new FromBranchingSheet();

                FromBranchingSheet_value pdf = new FromBranchingSheet_value();
                ArrayList<String> alcolonne = new ArrayList<>();
                alcolonne.add("Currency");
                alcolonne.add("Amount");
                alcolonne.add("Branch Rate");
                alcolonne.add("Amount");
                alcolonne.add("Branch Rate");
                alcolonne.add("Branch Total");
                ArrayList<String> alcolonne2 = new ArrayList<>();
                alcolonne2.add("Category");
                alcolonne2.add("Quantity");
                alcolonne2.add("Amount");
                alcolonne2.add("Total");
                pdf.setAuto(et.getAuto());
                pdf.setId_filiale(fil[0]);
                pdf.setDe_filiale(fil[1]);
                pdf.setFromsafe(fil[0] + " - " + fil[1]);
                pdf.setTobranch(dest.getCod() + " - " + dest.getDe_branch());
                pdf.setPinuser(et.getUser());
                pdf.setNumtranfer(et.getId());
                pdf.setNote(et.getNote());
                pdf.setNoteFROM(note_fromBranch);
                pdf.setDati(dati);

                if (Output.equals("PDF")) {

                    String base64;

                    if (newpread) {
                        if (dati.size() > 0) {
                            base64 = FROMBRANCH_CHANGE_pdf(path, pdf, formatStringtoStringDate(et.getDt_it(), patternsqldate, patternnormdate));
                        } else {
                            base64 = FROMBRANCH_NOCHANGE_pdf(path, pdf, formatStringtoStringDate(et.getDt_it(), patternsqldate, patternnormdate), dati2);
                        }
                    } else {
                        base64 = bsi.receipt(path, pdf, alcolonne, formatStringtoStringDate(et.getDt_it(), patternsqldate, patternnormdate), alcolonne2, dati2);
                    }

                    if (base64 != null) {
                        String headerKey = "Content-Disposition";
                        String headerValue = format("attachment; filename=\"%s\"", new Object[]{"FromBranchingSheet_" + new DateTime().toString("ddMMyyyy") + ".pdf"});
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
                        if (dati.size() > 0) {
                            base64 = Utility.sanitize(FROMBRANCH_CHANGE_xls(path, pdf, formatStringtoStringDate(et.getDt_it(), patternsqldate, patternnormdate)));
                        } else {
                            base64 = Utility.sanitize(FROMBRANCH_NOCHANGE_xls(path, pdf, formatStringtoStringDate(et.getDt_it(), patternsqldate, patternnormdate), dati2));
                        }
                    } else {
                        base64 = Utility.sanitize(bsi.receiptexcel(path, pdf, alcolonne, formatStringtoStringDate(et.getDt_it(), patternsqldate, patternnormdate), alcolonne2, dati2));
                    }

                    if (base64 != null) {
                        String headerKey = "Content-Disposition";
                        String headerValue = format("attachment; filename=\"%s\"", new Object[]{"FromBranchingSheet_" + new DateTime().toString("ddMMyyyy") + ".xlsx"});
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
    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void ToBankingSheet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

//        Utility.printRequest(request);
        String typeop = request.getParameter("typeop");
        String idopenclose = request.getParameter("idopenclose");

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
        ArrayList<String[]> array_bank = dbm.list_bank_enabled();
        ArrayList<String[]> array_credit_card = dbm.list_bank_pos_enabled();
        ET_change et = dbm.get_ET_change(idopenclose);
        if (et != null) {
            String ba = formatBankBranch(et.getCod_dest(), "BA", array_bank, null, array_credit_card);
            String path = dbm.getPath("temp");
            //path = "F:\\com\\";
            ArrayList<Branch> allfill = dbm.list_branch_completeAFTER311217();
            ArrayList<ToBankingSheet_value> dati = dbm.list_ToBankingSheet_value(et);
            ArrayList<ToBankingSheet_value> datiPOS = dbm.list_POSBASheet_value(et);
            dbm.closeDB();

            if (typeop.equalsIgnoreCase("POS / BANK ACCOUNT") || typeop.equalsIgnoreCase("PO")) {
                if (datiPOS.size() > 0) {

                    ToPOSBASheet bsi = new ToPOSBASheet();
                    ToBankingSheet_value pdf = new ToBankingSheet_value();
                    ArrayList<String> alcolonne = new ArrayList<>();
                    alcolonne.add("Currency");
                    alcolonne.add("Amount");
                    alcolonne.add("Amount");
                    alcolonne.add("Amount");
                    alcolonne.add("Amount");
                    alcolonne.add("Bank Total");
                    pdf.setId_filiale(et.getFiliale());
                    pdf.setDe_filiale(formatBankBranchReport(et.getFiliale(), "BR", null, allfill));

                    pdf.setFromsafe(et.getFiliale() + " - " + formatBankBranchReport(et.getFiliale(), "BR", null, allfill));
                    pdf.setTobank(et.getCod_dest() + " - " + ba);
                    pdf.setPinuser(et.getUser());
                    pdf.setNumtranfer(et.getId());
                    pdf.setDati(datiPOS);

                    if (Output.equals("PDF")) {
                        String base64;
                        if (newpread) {
                            base64 = Utility.sanitize(TOBANK_POS_pdf(path, pdf, formatStringtoStringDate(et.getDt_it(), patternsqldate, patternnormdate)));
                        } else {
                            base64 = Utility.sanitize(bsi.receipt(path, pdf, alcolonne,
                                    formatStringtoStringDate(et.getDt_it(), patternsqldate, patternnormdate)));

                        }

                        if (base64 != null) {
                            String headerKey = "Content-Disposition";
                            String headerValue = format("attachment; filename=\"%s\"", new Object[]{"ToPosBankAccountSheet_" + new DateTime().toString("ddMMyyyy") + ".pdf"});
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
                            base64 = Utility.sanitize(TOBANK_POS_xls(path, pdf, formatStringtoStringDate(et.getDt_it(), patternsqldate, patternnormdate)));
                        } else {
                            base64 = Utility.sanitize(bsi.receiptexcel(path, pdf, alcolonne, formatStringtoStringDate(et.getDt_it(), patternsqldate, patternnormdate)));
                        }
                        if (base64 != null) {
                            String headerKey = "Content-Disposition";
                            String headerValue = format("attachment; filename=\"%s\"", new Object[]{"ToPosBankAccountSheet_" + new DateTime().toString("ddMMyyyy") + ".xlsx"});
                            response.setHeader(headerKey, headerValue);
                            try (OutputStream outStream = response.getOutputStream()) {
                                outStream.write(decodeBase64(base64.getBytes()));
                            }
                        } else {
                            redirect(request, response, "page_404.html");
                        }

                    }

                }
            } else if (et.getFg_tofrom().equals("T")) {
                if (dati.size() > 0) {
                    ToBankingSheet bsi = new ToBankingSheet();
                    ToBankingSheet_value pdf = new ToBankingSheet_value();
                    ArrayList<String> alcolonne = new ArrayList<>();
                    alcolonne.add("Currency");
                    alcolonne.add("Amount");
                    alcolonne.add("Bank Rate");
                    alcolonne.add("Amount");
                    alcolonne.add("Bank Rate");
                    alcolonne.add("Bank Total");
                    alcolonne.add("Spread");
                    alcolonne.add("%");
                    pdf.setId_filiale(et.getFiliale());
                    pdf.setDe_filiale(formatBankBranchReport(et.getFiliale(), "BR", null, allfill));
                    pdf.setAuto(et.getAuto());
                    pdf.setFromsafe(et.getFiliale() + " - " + formatBankBranchReport(et.getFiliale(), "BR", null, allfill));
                    pdf.setTobank(et.getCod_dest() + " - " + ba);
                    pdf.setPinuser(et.getUser());
                    pdf.setNumtranfer(et.getId());
                    pdf.setNote(et.getNote());
                    pdf.setDati(dati);

                    if (Output.equals("PDF")) {
                        String base64;
                        if (newpread) {
                            base64 = Utility.sanitize(TOBANK_pdf(path, pdf, formatStringtoStringDate(et.getDt_it(), patternsqldate, patternnormdate)));
                        } else {

                            base64 = Utility.sanitize(bsi.receipt(path, pdf, alcolonne,
                                    formatStringtoStringDate(et.getDt_it(), patternsqldate, patternnormdate)));
                        }
                        if (base64 != null) {
                            String headerKey = "Content-Disposition";
                            String headerValue = format("attachment; filename=\"%s\"", new Object[]{"ToBankingSheet_" + new DateTime().toString("ddMMyyyy") + ".pdf"});
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
                            base64 = Utility.sanitize(TOBANK_xls(path, pdf, formatStringtoStringDate(et.getDt_it(), patternsqldate, patternnormdate)));
                        } else {
                            base64 = Utility.sanitize(bsi.receiptexcel(path, pdf, alcolonne, formatStringtoStringDate(et.getDt_it(), patternsqldate, patternnormdate)));
                        }
                        if (base64 != null) {
                            String headerKey = "Content-Disposition";
                            String headerValue = format("attachment; filename=\"%s\"", new Object[]{"ToBankingSheet_" + new DateTime().toString("ddMMyyyy") + ".xlsx"});
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

            } else {
                dbm = new Db_Master();
                String note_fromBranch = dbm.note_fromBranch(et.getCod_in());
                ArrayList<FromBranchingSheet_value> dati1 = dbm.list_FromBranchingSheet_value(et);
                ArrayList<ToBankingSheet_value> datix = dbm.list_ToBankingSheet_value(et);
                Branch dest = dbm.get_branch(et.getCod_dest());
                path = dbm.getPath("temp");
                //path = "F:\\com\\";
                allfill = dbm.list_branch_completeAFTER311217();

                dbm.closeDB();
                if (dati1.size() > 0 || datix.size() > 0) {
                    FromBankingSheet bsi = new FromBankingSheet();
                    ArrayList<String> alcolonne = new ArrayList<>();
                    alcolonne.add("Currency");
                    alcolonne.add("Amount");
                    alcolonne.add("Bank Rate");
                    alcolonne.add("Amount");
                    alcolonne.add("Bank Rate");
                    alcolonne.add("Bank Total");
                    if (Output.equals("PDF")) {
                        String base64;
                        if (newpread) {
                            ToBankingSheet_value pdf = new ToBankingSheet_value();
                            pdf.setAuto(et.getAuto());
                            pdf.setId_filiale(et.getFiliale());
                            pdf.setDe_filiale(formatBankBranchReport(et.getFiliale(), "BR", null, allfill));
                            pdf.setFromsafe(et.getFiliale() + " - " + formatBankBranchReport(et.getFiliale(), "BR", null, allfill));
                            pdf.setTobank(et.getCod_dest() + " - " + ba);
                            pdf.setPinuser(et.getUser());
                            pdf.setNumtranfer(et.getId());
                            pdf.setNote(et.getNote());
                            pdf.setDati(datix);
                            base64 = Utility.sanitize(FROMBANK_pdf(path, pdf, formatStringtoStringDate(et.getDt_it(), patternsqldate, patternnormdate)));
                        } else {

                            FromBranchingSheet_value pdf = new FromBranchingSheet_value();

                            pdf.setAuto(et.getAuto());
                            pdf.setId_filiale(et.getFiliale());
                            pdf.setDe_filiale(formatBankBranchReport(et.getFiliale(), "BR", null, allfill));
                            pdf.setFromsafe(et.getFiliale() + " - " + formatBankBranchReport(et.getFiliale(), "BR", null, allfill));
                            pdf.setTobranch(et.getCod_dest() + " - " + ba);
                            pdf.setPinuser(et.getUser());
                            pdf.setNumtranfer(et.getId());
                            pdf.setNote(et.getNote());
                            pdf.setNoteFROM(note_fromBranch);
                            pdf.setDati(dati1);
                            base64 = Utility.sanitize(bsi.receipt(path, pdf, alcolonne, formatStringtoStringDate(et.getDt_it(), patternsqldate, patternnormdate)));
                        }

                        if (base64 != null) {
                            String headerKey = "Content-Disposition";
                            String headerValue = format("attachment; filename=\"%s\"", new Object[]{"FromBankingSheet_" + new DateTime().toString("ddMMyyyy") + ".pdf"});
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
                            ToBankingSheet_value pdf = new ToBankingSheet_value();
                            pdf.setAuto(et.getAuto());
                            pdf.setId_filiale(et.getFiliale());
                            pdf.setDe_filiale(formatBankBranchReport(et.getFiliale(), "BR", null, allfill));
                            pdf.setFromsafe(et.getFiliale() + " - " + formatBankBranchReport(et.getFiliale(), "BR", null, allfill));
                            pdf.setTobank(et.getCod_dest() + " - " + ba);
                            pdf.setPinuser(et.getUser());
                            pdf.setNumtranfer(et.getId());
                            pdf.setNote(et.getNote());
                            pdf.setDati(datix);
                            base64 = Utility.sanitize(FROMBANK_xls(path, pdf, formatStringtoStringDate(et.getDt_it(), patternsqldate, patternnormdate)));
                        } else {

                            FromBranchingSheet_value pdf = new FromBranchingSheet_value();

                            pdf.setAuto(et.getAuto());
                            pdf.setId_filiale(et.getFiliale());
                            pdf.setDe_filiale(formatBankBranchReport(et.getFiliale(), "BR", null, allfill));
                            pdf.setFromsafe(et.getFiliale() + " - " + formatBankBranchReport(et.getFiliale(), "BR", null, allfill));
                            pdf.setTobranch(et.getCod_dest() + " - " + ba);
                            pdf.setPinuser(et.getUser());
                            pdf.setNumtranfer(et.getId());
                            pdf.setNote(et.getNote());
                            pdf.setNoteFROM(note_fromBranch);
                            pdf.setDati(dati1);
                            base64 = Utility.sanitize(bsi.receiptexcel(path, pdf, alcolonne, formatStringtoStringDate(et.getDt_it(), patternsqldate, patternnormdate)));
                        }

                        if (base64 != null) {
                            String headerKey = "Content-Disposition";
                            String headerValue = format("attachment; filename=\"%s\"", new Object[]{"FromBankingSheet_" + new DateTime().toString("ddMMyyyy") + ".xls"});
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
        } else {
            dbm.closeDB();
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
    protected void InternalTransferList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

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
        String path = dbm.getPath("temp");
        //path = "F:\\com\\";
        String filiale = request.getParameter("branch");
        String fil[] = new String[2];
        if (filiale == null || filiale.equals("")) {
            fil = dbm.getCodLocal(false);
        } else {
            fil[0] = filiale;
            fil[1] = formatBankBranchReport(filiale, "BR", null, list_branch_completeAFTER311217());
        }
        ArrayList<InternalTransferList_value> dati = dbm.list_InternalTransferList_value(fil[0], data1, data2);
        ArrayList<NoChangeInternalTransferList_value> dati2 = dbm.list_InternalTransferList_valueNC(fil[0], data1, data2);
        dbm.closeDB();

        if (dati.size() > 0 || dati2.size() > 0) {
            InternalTransferList bsi = new InternalTransferList();
            InternalTransferList_value pdf = new InternalTransferList_value();
            NoChangeInternalTransferList_value pdf2 = new NoChangeInternalTransferList_value();
            ArrayList<String> alcolonne = new ArrayList<>();
            alcolonne.add("Source");
            alcolonne.add("Dest.");
            alcolonne.add("Progr.");
            alcolonne.add("Date");
            alcolonne.add("Currency");
            alcolonne.add("Notes");
            alcolonne.add("Euro TC / Travel Cheques");
            alcolonne.add("User");
            alcolonne.add("Deleted");

            ArrayList<String> alcolonne2 = new ArrayList<>();
            alcolonne2.add("Source");
            alcolonne2.add("Dest.");
            alcolonne2.add("Progr.");
            alcolonne2.add("Date");
            alcolonne2.add("Category");
            alcolonne2.add("Quantity");
            alcolonne2.add("User");
            alcolonne2.add("Deleted");

            pdf.setId_filiale(fil[0]);
            pdf.setDe_filiale(fil[1]);
            pdf.setDati(dati);
            pdf2.setDati(dati2);
            if (Output.equals("PDF")) {

                String base64 = Utility.sanitize(bsi.receipt(path, pdf, alcolonne, d3, d4, alcolonne2, pdf2));
                if (base64 != null) {
                    String headerKey = "Content-Disposition";
                    String sanitized_d3 = Utility.sanitize(d3);
                    String sanitized_d4 = Utility.sanitize(d4);
                    String headerValue = format("attachment; filename=\"%s\"", new Object[]{"InternalTransferList_" + sanitized_d3 + "_" + sanitized_d4 + ".pdf"});
                    response.setHeader(headerKey, headerValue);
                    try (OutputStream outStream = response.getOutputStream()) {
                        outStream.write(decodeBase64(base64.getBytes()));
                    }
                } else {
                    redirect(request, response, "page_404.html");
                }

            } else {

                String base64 = Utility.sanitize(bsi.receiptexcel(path, pdf, alcolonne, d3, d4, alcolonne2, pdf2));

                if (base64 != null) {
                    String headerKey = "Content-Disposition";
                    String sanitized_d3 = Utility.sanitize(d3);
                    String sanitized_d4 = Utility.sanitize(d4);
                    String headerValue = format("attachment; filename=\"%s\"", new Object[]{"InternalTransferList_" + sanitized_d3 + "_" + sanitized_d4 + ".xls"});
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
    protected void Dailynow(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String Output = request.getParameter("Output");
        DateTime now = getNowDT();

        String datareport1 = now.toString(patternnormdate_filter);
        String datareport2 = now.toString(patternnormdate);
        String data1 = now.toString(patternsql) + " 00:00";
        String data2 = now.toString(patternsql_f);

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
        String filiale = request.getParameter("branch");
        String fil[] = new String[2];
        if (filiale == null || filiale.equals("")) {
            fil = dbm.getCodLocal(false);
        } else {
            fil[0] = filiale;
            fil[1] = formatBankBranchReport(filiale, "BR", null, list_branch_completeAFTER311217());
        }

        String path = dbm.getPath("temp");

        Daily_value d = dbm.list_Daily_value(fil, data1, data2, true, false);
        dbm.closeDB();

        dbm.closeDB();

        if (d != null) {
            Daily bsi = new Daily();
            ArrayList<Daily_value> listd = new ArrayList<>();
            listd.add(d);
            if (Output.equals("PDF")) {

                String base64 = Utility.sanitize(bsi.receipt(path, listd, datareport1, datareport2, false));
                if (base64 != null) {
                    String headerKey = "Content-Disposition";
                    String sanitized_datareport1 = Utility.sanitize(datareport1);
                    String sanitized_datareport2 = Utility.sanitize(datareport2);
                    String headerValue = format("attachment; filename=\"%s\"", new Object[]{"Daily_" + sanitized_datareport1 + "_" + sanitized_datareport2 + ".pdf"});
                    response.setHeader(headerKey, headerValue);
                    try (OutputStream outStream = response.getOutputStream()) {
                        outStream.write(decodeBase64(base64.getBytes()));
                    }
                } else {
                    redirect(request, response, "page_404.html");
                }

            } else {

                String base64 = Utility.sanitize(bsi.receiptexcel(path, listd, datareport1, datareport2));

                if (base64 != null) {
                    String headerKey = "Content-Disposition";
                    String sanitized_datareport1 = Utility.sanitize(datareport1);
                    String sanitized_datareport2 = Utility.sanitize(datareport2);
                    String headerValue = format("attachment; filename=\"%s\"", new Object[]{"Daily_" + sanitized_datareport1 + "_" + sanitized_datareport2 + ".xlsx"});
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
    protected void Daily(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String res_type = Utility.sanitize(request.getParameter("res_type"));
        if (null == res_type) {
            res_type = "M";
        } else {
            switch (res_type) {
                case "on":
                    res_type = "D";
                    break;
                default:
                    res_type = "M";
                    break;
            }
        }

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
        String data1;
        String data2;

        if (res_type.equals("D")) {
            data1 = formatStringtoStringDate_null(d3.substring(0, 10) + " 00:00", patternnormdate_f, patternsql_f);
            data2 = formatStringtoStringDate_null(d3, patternnormdate_f, patternsql_f);
            d4 = d3;
            d3 = d3.substring(0, 10) + " 00:00";
        } else {
            data1 = formatStringtoStringDate_null(d3, patternnormdate_filter, patternsql) + " 00:00";
            data2 = formatStringtoStringDate_null(d4, patternnormdate_filter, patternsql) + " 23:59";
        }

////        //ArrayList<Daily_value> dati = new ArrayList<>();
        Db_Master dbm = new Db_Master();
        String path = Utility.sanitize(dbm.getPath("temp"));
        //path = "F:\\com\\";
        String filiale = Utility.sanitize(request.getParameter("branch"));
        String fil[] = new String[2];
        if (filiale == null || filiale.equals("")) {
            fil = dbm.getCodLocal(false);
        } else {
            fil[0] = filiale;
            fil[1] = formatBankBranchReport(filiale, "BR", null, list_branch_completeAFTER311217());
        }
////
        Daily_value d = dbm.list_Daily_value(fil, data1, data2, false, false);
        dbm.closeDB();
        if (d != null) {
            Daily bsi = new Daily();
            ArrayList<Daily_value> listd = new ArrayList<>();
            listd.add(d);
            if (Output.equals("PDF")) {

                String base64 = Utility.sanitize(bsi.receipt(path, listd, d3, d4, false));
                if (base64 != null) {
                    String headerKey = "Content-Disposition";
                    String sanitized_d3 = Utility.sanitize(d3);
                    String sanitized_d4 = Utility.sanitize(d4);
                    String headerValue = format("attachment; filename=\"%s\"", new Object[]{"Daily_" + sanitized_d3 + "_" + sanitized_d4 + ".pdf"});
                    response.setHeader(headerKey, headerValue);
                    try (OutputStream outStream = response.getOutputStream()) {
                        outStream.write(decodeBase64(base64.getBytes()));
                    }
                } else {
                    redirect(request, response, "page_404.html");
                }

            } else {

                String base64 = Utility.sanitize(bsi.receiptexcel(path, listd, d3, d4));

                if (base64 != null) {
                    String headerKey = "Content-Disposition";
                    String sanitized_d3 = Utility.sanitize(d3);
                    String sanitized_d4 = Utility.sanitize(d4);
                    String headerValue = format("attachment; filename=\"%s\"", new Object[]{"Daily_" + sanitized_d3 + "_" + sanitized_d4 + ".xlsx"});
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
    protected void NoChangeTransactionList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

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

        List<String> listmodify = dbm.list_transaction_modify_nc();

        ArrayList<Branch> listbr = dbm.list_branch_completeAFTER311217();

        String path = Utility.sanitize(dbm.getPath("temp"));

        String branch = Utility.sanitize(request.getParameter("branch"));
        ArrayList<String> br1 = parseString(branch, ",");
        if (br1.isEmpty()) {
            br1 = (ArrayList<String>) listbr.stream().map(valore -> valore.getCod()).distinct().collect(toList());
        }

        String[] nc_cat1 = request.getParameterValues("nc_cat1");
        String list_nc_cat = "";
        if (nc_cat1 == null) {
            ArrayList<NC_category> array_nc_cat = dbm.list_nc_category_enabled();
            for (int i = 0; i < array_nc_cat.size(); i++) {
                list_nc_cat = list_nc_cat + array_nc_cat.get(i).getGruppo_nc() + ";";
            }
        } else {
            String totallist = parseArrayValues(nc_cat1);
            if (totallist.contains("...")) {
                ArrayList<NC_category> array_nc_cat = dbm.list_nc_category_enabled();
                for (int i = 0; i < array_nc_cat.size(); i++) {
                    list_nc_cat = list_nc_cat + array_nc_cat.get(i).getGruppo_nc() + ";";
                }
            } else {
                list_nc_cat = parseArrayValues(nc_cat1);
            }
        }

        ArrayList<String> alcolonne = new ArrayList<>();
        alcolonne.add("");
        alcolonne.add("Till");
        alcolonne.add("ID");
        alcolonne.add("Date Time");
        alcolonne.add("Category of Transaction");
        alcolonne.add("Kind of Transaction");
        alcolonne.add("Note");
        alcolonne.add("Quantity");
        alcolonne.add("Total");
        alcolonne.add("User");

        NoChangeTransactionList nctl = new NoChangeTransactionList();
        ArrayList<NoChangeTransactionList_value> list = new ArrayList<>();
        for (int x = 0; x < br1.size(); x++) {
            String[] fil = {br1.get(x), formatBankBranchReport(br1.get(x), "BR", null, listbr)};
            NoChangeTransactionList_value pdf = new NoChangeTransactionList_value();
            ArrayList<NoChangeTransactionList_value> dati = dbm.list_NoChangeTransactionList_value(fil[0], data1, data2, list_nc_cat);
            pdf.setId_filiale(fil[0]);
            pdf.setDe_filiale(fil[1]);
            pdf.setDataDa(d3);
            pdf.setDataA(d4);
            pdf.setDati(dati);
            if (!dati.isEmpty()) {
                list.add(pdf);
            }
        }
        dbm.closeDB();
        if (list.size() > 0) {
            if (Output.equals("PDF")) {

                String base64 = Utility.sanitize(nctl.receipt(path, list, alcolonne, d3, listmodify));
                if (base64 != null) {
                    String headerKey = "Content-Disposition";
                    String sanitized_d3 = Utility.sanitize(d3);
                    String sanitized_d4 = Utility.sanitize(d4);
                    String headerValue = format("attachment; filename=\"%s\"", new Object[]{"NoChangeTransactionList_" + sanitized_d3 + "_" + sanitized_d4 + ".pdf"});
                    response.setHeader(headerKey, headerValue);
                    try (OutputStream outStream = response.getOutputStream()) {
                        outStream.write(decodeBase64(base64.getBytes()));
                    }
                } else {
                    redirect(request, response, "page_404.html");
                }

            } else {

                String base64 = Utility.sanitize(nctl.receiptexcel(path, list, alcolonne, d3, listmodify));

                if (base64 != null) {
                    String headerKey = "Content-Disposition";
                    String sanitized_d3 = Utility.sanitize(d3);
                    String sanitized_d4 = Utility.sanitize(d4);
                    String headerValue = format("attachment; filename=\"%s\"", new Object[]{"NoChangeTransactionList_" + sanitized_d3 + "_" + sanitized_d4 + ".xls"});
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
    protected void NoChangeCategoryTransactionList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Db_Master dbm = new Db_Master();
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
        String[] nc_cat1 = request.getParameterValues("nc_cat1");
        String list_nc_cat = "";
        if (nc_cat1 == null) {
            ArrayList<NC_category> array_nc_cat = dbm.list_nc_category_enabled();
            for (int i = 0; i < array_nc_cat.size(); i++) {
                list_nc_cat = list_nc_cat + array_nc_cat.get(i).getGruppo_nc() + ";";
            }
        } else {
            String totallist = parseArrayValues(nc_cat1);
            if (totallist.contains("...")) {
                ArrayList<NC_category> array_nc_cat = dbm.list_nc_category_enabled();
                for (int i = 0; i < array_nc_cat.size(); i++) {
                    list_nc_cat = list_nc_cat + array_nc_cat.get(i).getGruppo_nc() + ";";
                }
            } else {
                list_nc_cat = parseArrayValues(nc_cat1);
            }
        }

        ArrayList<Branch> listbr = dbm.list_branch_completeAFTER311217();

        String path = Utility.sanitize(dbm.getPath("temp"));
        //path = "F:\\com\\";
        String branch = Utility.sanitize(request.getParameter("branch"));
        ArrayList<String> br1 = parseString(branch, ",");
        if (br1.isEmpty()) {
            br1 = (ArrayList<String>) listbr.stream().map(valore -> valore.getCod()).distinct().collect(toList());
        }

        ArrayList<String> alcolonne = new ArrayList<>();
        alcolonne.add("");
        alcolonne.add("Till");
        alcolonne.add("Date Time");
        alcolonne.add("Kind of Transaction");
        alcolonne.add("Note");
        alcolonne.add("Quantity");
        alcolonne.add("Total");
        alcolonne.add("Fee");
        alcolonne.add("Details");
        alcolonne.add("User");

        ArrayList<NoChangeCategoryTransactionList_value> pdflist = new ArrayList<>();
        NoChangeCategoryTransactionList nctl = new NoChangeCategoryTransactionList();

        for (int x = 0; x < br1.size(); x++) {
            String[] fil = {br1.get(x), formatBankBranchReport(br1.get(x), "BR", null, listbr)};
            ArrayList<NoChangeCategoryTransactionList_value> dati = dbm.list_NoChangeCategoryTransactionList_value(br1.get(x), data1, data2, list_nc_cat);
            NoChangeCategoryTransactionList_value pdf = new NoChangeCategoryTransactionList_value();
            pdf.setId_filiale(fil[0]);
            pdf.setDe_filiale(fil[1]);
            pdf.setDataDa(d3);
            pdf.setDataA(d4);
            pdf.setDati(dati);
            if (dati.size() > 0) {
                pdflist.add(pdf);
            }

        }
        dbm.closeDB();

        if (pdflist.size() > 0) {

            if (Output.equals("PDF")) {
                String base64 = Utility.sanitize(nctl.receipt(path, pdflist, alcolonne, d3, d4));
                if (base64 != null) {
                    String headerKey = "Content-Disposition";
                    String sanitized_d3 = Utility.sanitize(d3);
                    String sanitized_d4 = Utility.sanitize(d4);
                    String headerValue = format("attachment; filename=\"%s\"", new Object[]{"NoChangeCategoryTransactionList_" + sanitized_d3 + "_" + sanitized_d4 + ".pdf"});
                    response.setHeader(headerKey, headerValue);
                    try (OutputStream outStream = response.getOutputStream()) {
                        outStream.write(decodeBase64(base64.getBytes()));
                    }
                } else {
                    redirect(request, response, "page_404.html");
                }

            } else {

                String base64 = Utility.sanitize(nctl.receiptexcel(path, pdflist, alcolonne, d3, d4));

                if (base64 != null) {
                    String headerKey = "Content-Disposition";
                    String sanitized_d3 = Utility.sanitize(d3);
                    String sanitized_d4 = Utility.sanitize(d4);
                    String headerValue = format("attachment; filename=\"%s\"", new Object[]{"NoChangeCategoryTransactionList_" + sanitized_d3 + "_" + sanitized_d4 + ".xls"});
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
    protected void StockReport(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

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
        String[] nc_cat1 = request.getParameterValues("nc_cat1");
        String list_nc_cat = "";
        if (nc_cat1 == null) {
            ArrayList<NC_category> array_nc_cat = dbm.list_nc_category_enabled();
            for (int i = 0; i < array_nc_cat.size(); i++) {
                if (array_nc_cat.get(i).getFg_tipo_transazione_nc().equals("2")) {
                    list_nc_cat = list_nc_cat + array_nc_cat.get(i).getGruppo_nc() + ";";
                }
            }

        } else {
            String totallist = parseArrayValues(nc_cat1);
            if (totallist.contains("...")) {
                ArrayList<NC_category> array_nc_cat = dbm.list_nc_category_enabled();
                for (int i = 0; i < array_nc_cat.size(); i++) {
                    if (array_nc_cat.get(i).getFg_tipo_transazione_nc().equals("2")) {
                        list_nc_cat = list_nc_cat + array_nc_cat.get(i).getGruppo_nc() + ";";
                    }
                }
            } else {
                list_nc_cat = parseArrayValues(nc_cat1);
            }
        }
        String data1 = formatStringtoStringDate_null(d3, patternnormdate_filter, patternsql);
        String data2 = formatStringtoStringDate_null(d4, patternnormdate_filter, patternsql);

        String path = Utility.sanitize(dbm.getPath("temp"));
        //path = "F:\\com\\";
        ArrayList<Branch> listbr = dbm.list_branch_completeAFTER311217();
        String branch = Utility.sanitize(request.getParameter("branch"));
        ArrayList<String> br1 = parseString(branch, ",");
        if (br1.isEmpty()) {
            br1 = (ArrayList<String>) listbr.stream().map(valore -> valore.getCod()).distinct().collect(toList());
        }

        ArrayList<StockReport_value> pdf = new ArrayList<>();

        for (int x = 0; x < br1.size(); x++) {
            String[] fil = {br1.get(x), formatBankBranchReport(br1.get(x), "BR", null, listbr)};
            ArrayList<StockReport_value> pdf2 = dbm.list_StockReport_value(fil, data1, data2, list_nc_cat, d3, d4);
            if (pdf2.size() > 0) {
                pdf.addAll(pdf2);
            }
        }
        dbm.closeDB();

        if (pdf.size() > 0) {
            StockReport nctl = new StockReport();
            ArrayList<String> alcolonne = new ArrayList<>();
            alcolonne.add("");
            alcolonne.add("Till");
            alcolonne.add("Date Time");
            alcolonne.add("Kind of Transaction");
            alcolonne.add("Quantity");
            alcolonne.add("Price");
            alcolonne.add("Total");
            alcolonne.add("User");
            if (Output.equals("PDF")) {

                String base64 = Utility.sanitize(nctl.receipt(path, pdf, alcolonne, d3, d4));
                if (base64 != null) {
                    String headerKey = "Content-Disposition";
                    String sanitized_d3 = Utility.sanitize(d3);
                    String sanitized_d4 = Utility.sanitize(d4);
                    String headerValue = format("attachment; filename=\"%s\"", new Object[]{"StockReport_" + sanitized_d3 + "_" + sanitized_d4 + ".pdf"});
                    response.setHeader(headerKey, headerValue);
                    try (OutputStream outStream = response.getOutputStream()) {
                        outStream.write(decodeBase64(base64.getBytes()));
                    }
                } else {
                    redirect(request, response, "page_404.html");
                }

            } else {

                String base64 = Utility.sanitize(nctl.receiptexcel(path, pdf, alcolonne, d3, d4));

                if (base64 != null) {
                    String headerKey = "Content-Disposition";
                    String sanitized_d3 = Utility.sanitize(d3);
                    String sanitized_d4 = Utility.sanitize(d4);
                    String headerValue = format("attachment; filename=\"%s\"", new Object[]{"StockReport_" + sanitized_d3 + "_" + sanitized_d4 + ".xls"});
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
    protected void TotalStockReport(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

//        Utility.printRequest(request);
//        if(true)
//            return;
        String d3 = Utility.sanitize(request.getParameter("d3"));
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
        ArrayList<NC_category> array_nc_cat = dbm.list_nc_category_enabled();
        String[] nc_cat1 = request.getParameterValues("nc_cat1");
        String list_nc_cat = "";
        if (nc_cat1 == null) {

            for (int i = 0; i < array_nc_cat.size(); i++) {
                if (array_nc_cat.get(i).getFg_tipo_transazione_nc().equals("2")) {

                    list_nc_cat = list_nc_cat + array_nc_cat.get(i).getGruppo_nc() + ";";
                }
            }

        } else {
            String totallist = parseArrayValues(nc_cat1);
            if (totallist.contains("...")) {
                for (int i = 0; i < array_nc_cat.size(); i++) {
                    if (array_nc_cat.get(i).getFg_tipo_transazione_nc().equals("2")) {

                        list_nc_cat = list_nc_cat + array_nc_cat.get(i).getGruppo_nc() + ";";
                    }
                }
            } else {
                list_nc_cat = parseArrayValues(nc_cat1);
            }
        }

        ArrayList<String> listNCcat = parseString(list_nc_cat, ";");

        String data1 = formatStringtoStringDate_null(d3, patternnormdate_f, patternsql_f);
        ArrayList<Branch> listbr = dbm.list_branch_completeAFTER311217();
        String path = Utility.sanitize(dbm.getPath("temp"));
        //path = "F:\\com\\";
        String branch = Utility.sanitize(request.getParameter("branch"));
        ArrayList<String> br1 = parseString(branch, ",");
        if (br1.isEmpty()) {
            br1 = (ArrayList<String>) listbr.stream().map(valore -> valore.getCod()).distinct().collect(toList());
        }

        ArrayList<String> alcolonne = new ArrayList<>();
        alcolonne.add("Kind of Category");
        alcolonne.add("Stock");

        dbm.closeDB();

        TotalStockReport tsr = new TotalStockReport();
        ArrayList<TotalStockReport_value> pdflist = new ArrayList<>();
        for (int x = 0; x < br1.size(); x++) {
            Db_Master dbm1 = new Db_Master();
            String[] fil = {br1.get(x), formatBankBranchReport(br1.get(x), "BR", null, listbr)};
            ArrayList<TotalStockReport_value> dati = dbm1.list_TotalStockReport_value_smart(fil, data1, listNCcat, array_nc_cat);
            dbm1.closeDB();

            TotalStockReport_value pdf = new TotalStockReport_value();
            pdf.setId_filiale(fil[0]);
            pdf.setDe_filiale(fil[1]);
            pdf.setData(d3);
            pdf.setDati(dati);
            if (dati.size() > 0) {
                pdflist.add(pdf);
            }

        }
        if (pdflist.size() > 0) {

            if (Output.equals("PDF")) {
                String base64 = Utility.sanitize(tsr.receipt(path, pdflist, alcolonne, d3));
                if (base64 != null) {
                    String headerKey = "Content-Disposition";
                    String sanitized_d3 = Utility.sanitize(d3);
                    String headerValue = format("attachment; filename=\"%s\"", new Object[]{"TotalStockReport_" + sanitized_d3 + ".pdf"});
                    response.setHeader(headerKey, headerValue);
                    try (OutputStream outStream = response.getOutputStream()) {
                        outStream.write(decodeBase64(base64.getBytes()));
                    }
                } else {
                    redirect(request, response, "page_404.html");
                }
            } else {
                String base64 = Utility.sanitize(tsr.receiptexcel(path, pdflist, alcolonne, d3));
                if (base64 != null) {
                    String headerKey = "Content-Disposition";
                    String sanitized_d3 = Utility.sanitize(d3);
                    String headerValue = format("attachment; filename=\"%s\"", new Object[]{"TotalStockReport_" + sanitized_d3 + ".xls"});
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
    protected void WesternUnionService(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

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
        ArrayList<Branch> listbr = dbm.list_branch_completeAFTER311217();
        String path = Utility.sanitize(dbm.getPath("temp"));
        //path = "F:\\com\\";
        String branch = Utility.sanitize(request.getParameter("branch"));
        ArrayList<String> br1 = parseString(branch, ",");
        if (br1.isEmpty()) {
            br1 = (ArrayList<String>) listbr.stream().map(valore -> valore.getCod()).distinct().collect(toList());
        }

        ArrayList<String> alcolonne = new ArrayList<>();
        alcolonne.add("");
        alcolonne.add("Till");
        alcolonne.add("Date Time");
        alcolonne.add("MTCN");
        alcolonne.add("Note");
        alcolonne.add("");
        alcolonne.add("SEND");
        alcolonne.add("RECEIVE");
        alcolonne.add("Commission WU Send");
        alcolonne.add("User");

        WesternUnionService nctl = new WesternUnionService();
        ArrayList<WesternUnionService_value> pdflist = new ArrayList<>();

        for (int x = 0; x < br1.size(); x++) {
            String[] fil = {br1.get(x), formatBankBranchReport(br1.get(x), "BR", null, listbr)};
            ArrayList<WesternUnionService_value> dati = dbm.list_WesternUnionService_value(fil[0], data1, data2, list_nc_cat);
            WesternUnionService_value pdf = new WesternUnionService_value();
            pdf.setId_filiale(fil[0]);
            pdf.setDe_filiale(fil[1]);
            pdf.setDataDa(d3);
            pdf.setDataA(d4);
            pdf.setDati(dati);
            if (dati.size() > 0) {
                pdflist.add(pdf);
            }
        }

        dbm.closeDB();

        if (pdflist.size() > 0) {

//        ArrayList<rc.so.util.WesternUnionService> dati = new ArrayList<>();
//        dati = Engine.getWesternUnionService();
            if (Output.equals("PDF")) {

                String base64 = Utility.sanitize(nctl.receipt(path, pdflist, alcolonne, d3, d4));
                if (base64 != null) {
                    String headerKey = "Content-Disposition";
                    String sanitized_d3 = Utility.sanitize(d3);
                    String sanitized_d4 = Utility.sanitize(d4);
                    String headerValue = format("attachment; filename=\"%s\"", new Object[]{"WesternUnionService_" + sanitized_d3 + "_" + sanitized_d4 + ".pdf"});
                    response.setHeader(headerKey, headerValue);
                    try (OutputStream outStream = response.getOutputStream()) {
                        outStream.write(decodeBase64(base64.getBytes()));
                    }
                } else {
                    redirect(request, response, "page_404.html");
                }

            } else {

                String base64 = Utility.sanitize(nctl.receiptexcel(path, pdflist, alcolonne, d3, d4));

                if (base64 != null) {
                    String headerKey = "Content-Disposition";
                    String sanitized_d3 = Utility.sanitize(d3);
                    String sanitized_d4 = Utility.sanitize(d4);
                    String headerValue = format("attachment; filename=\"%s\"", new Object[]{"WesternUnionService_" + sanitized_d3 + "_" + sanitized_d4 + ".xls"});
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
    protected void InsuranceTransactionList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

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

        String data1 = formatStringtoStringDate_null(d3, patternnormdate_filter, patternsql);
        String data2 = formatStringtoStringDate_null(d4, patternnormdate_filter, patternsql);
        String[] nc_cat1 = request.getParameterValues("nc_cat1");
        String list_nc_cat = "";
        if (nc_cat1 == null) {
            ArrayList<NC_category> array_nc_cat = dbm.list_nc_category_enabled();
            for (int i = 0; i < array_nc_cat.size(); i++) {
                if (array_nc_cat.get(i).getFg_tipo_transazione_nc().equals("5")) {
                    list_nc_cat = list_nc_cat + array_nc_cat.get(i).getGruppo_nc() + ";";
                }
            }
        } else {
            String totallist = parseArrayValues(nc_cat1);
            if (totallist.contains("...")) {
                ArrayList<NC_category> array_nc_cat = dbm.list_nc_category_enabled();
                for (int i = 0; i < array_nc_cat.size(); i++) {
                    if (array_nc_cat.get(i).getFg_tipo_transazione_nc().equals("5")) {
                        list_nc_cat = list_nc_cat + array_nc_cat.get(i).getGruppo_nc() + ";";
                    }
                }
            } else {
                list_nc_cat = parseArrayValues(nc_cat1);
            }
        }
        ArrayList<String> alcolonne = new ArrayList<>();
        alcolonne.add("");
        alcolonne.add("Till");
        alcolonne.add("Date Time");
        alcolonne.add("Kind of Transaction");
        alcolonne.add("Insurance Number");
        alcolonne.add("Description");
        alcolonne.add("Start Date - End Date");
        alcolonne.add("Total");
        alcolonne.add("User");
        String path = Utility.sanitize(dbm.getPath("temp"));
        InsuranceTransactionList nctl = new InsuranceTransactionList();
        ArrayList<InsuranceTransactionList_value> pdflist = new ArrayList<>();
        if (!list_nc_cat.equals("")) {
            ArrayList<Branch> listbr = dbm.list_branch_completeAFTER311217();
            //path = "F:\\com\\";
            String branch = Utility.sanitize(request.getParameter("branch"));
            ArrayList<String> br1 = parseString(branch, ",");
            if (br1.isEmpty()) {
                br1 = (ArrayList<String>) listbr.stream().map(valore -> valore.getCod()).distinct().collect(toList());
            }

            for (int x = 0; x < br1.size(); x++) {
                String[] fil = {br1.get(x), formatBankBranchReport(br1.get(x), "BR", null, listbr)};
                ArrayList<InsuranceTransactionList_value> dati = dbm.list_InsuranceTransactionList_value(fil[0], data1, data2, list_nc_cat);
                InsuranceTransactionList_value pdf = new InsuranceTransactionList_value();

                pdf.setId_filiale(fil[0]);
                pdf.setDe_filiale(fil[1]);
                pdf.setDataDa(d3);
                pdf.setDataA(d4);
                pdf.setDati(dati);
                if (dati.size() > 0) {
                    pdflist.add(pdf);
                }

            }
        }

        dbm.closeDB();

        if (pdflist.size() > 0) {

//        ArrayList<InsuranceTransactionList_value> dati = new ArrayList<>();
//        dati = Engine.getInsuranceTransactionList();
            if (Output.equals("PDF")) {

                String base64 = Utility.sanitize(nctl.receipt(path, pdflist, alcolonne, d3, d4));
                if (base64 != null) {
                    String headerKey = "Content-Disposition";
                    String sanitized_d3 = Utility.sanitize(d3);
                    String sanitized_d4 = Utility.sanitize(d4);
                    String headerValue = format("attachment; filename=\"%s\"", new Object[]{"InsuranceTransactionList_" + sanitized_d3 + "_" + sanitized_d4 + ".pdf"});
                    response.setHeader(headerKey, headerValue);
                    try (OutputStream outStream = response.getOutputStream()) {
                        outStream.write(decodeBase64(base64.getBytes()));
                    }
                } else {
                    redirect(request, response, "page_404.html");
                }

            } else {

                String base64 = Utility.sanitize(nctl.receiptexcel(path, pdflist, alcolonne, d3, d4));

                if (base64 != null) {
                    String headerKey = "Content-Disposition";
                    String sanitized_d3 = Utility.sanitize(d3);
                    String sanitized_d4 = Utility.sanitize(d4);
                    String headerValue = format("attachment; filename=\"%s\"", new Object[]{"InsuranceTransactionList_" + sanitized_d3 + "_" + sanitized_d4 + ".xls"});
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
    protected void NoChangeTransactionListForUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

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

        String data1 = formatStringtoStringDate_null(d3, patternnormdate_filter, patternsql);
        String data2 = formatStringtoStringDate_null(d4, patternnormdate_filter, patternsql);
        String[] nc_cat1 = request.getParameterValues("nc_cat1");
        String list_nc_cat = "";
        if (nc_cat1 == null) {
            ArrayList<NC_category> array_nc_cat = dbm.list_nc_category_enabled();
            for (int i = 0; i < array_nc_cat.size(); i++) {

                list_nc_cat = list_nc_cat + array_nc_cat.get(i).getGruppo_nc() + ";";
            }
        } else {
            String totallist = parseArrayValues(nc_cat1);
            if (totallist.contains("...")) {
                ArrayList<NC_category> array_nc_cat = dbm.list_nc_category_enabled();
                for (int i = 0; i < array_nc_cat.size(); i++) {
                    list_nc_cat = list_nc_cat + array_nc_cat.get(i).getGruppo_nc() + ";";
                }
            } else {
                list_nc_cat = parseArrayValues(nc_cat1);
            }
        }
        String path = Utility.sanitize(dbm.getPath("temp"));
        //path = "F:\\com\\";
        ArrayList<Branch> listbr = dbm.list_branch_completeAFTER311217();
        String branch = Utility.sanitize(request.getParameter("branch"));
        ArrayList<String> br1 = parseString(branch, ",");
        if (br1.isEmpty()) {
            br1 = (ArrayList<String>) listbr.stream().map(valore -> valore.getCod()).distinct().collect(toList());
        }

        NoChangeTransactionListForUser nctl = new NoChangeTransactionListForUser();
        ArrayList<NoChangeTransactionListForUser_value> pdflist = new ArrayList<>();

        ArrayList<String> alcolonne = new ArrayList<>();
        alcolonne.add("");
        alcolonne.add("User");
        alcolonne.add("Category of Transaction");
        alcolonne.add("Till");
        alcolonne.add("Date Time");
        alcolonne.add("Kind of Transaction");
        alcolonne.add("Note");
        alcolonne.add("Quantity");
        alcolonne.add("Total");

        for (int x = 0; x < br1.size(); x++) {
            String[] fil = {br1.get(x), formatBankBranchReport(br1.get(x), "BR", null, listbr)};
            ArrayList<NoChangeTransactionListForUser_value> dati = dbm.list_NoChangeTransactionListForUser_value(fil[0], data1, data2, list_nc_cat);
            NoChangeTransactionListForUser_value pdf = new NoChangeTransactionListForUser_value();
            pdf.setId_filiale(fil[0]);
            pdf.setDe_filiale(fil[1]);
            pdf.setDataDa(d3);
            pdf.setDataA(d4);
            pdf.setDati(dati);
            if (dati.size() > 0) {
                pdflist.add(pdf);
            }

        }

        dbm.closeDB();

        if (pdflist.size() > 0) {
            if (Output.equals("PDF")) {

                String base64 = Utility.sanitize(nctl.receipt(path, pdflist, alcolonne, d3, d4));
                if (base64 != null) {
                    String headerKey = "Content-Disposition";
                    String sanitized_d3 = Utility.sanitize(d3);
                    String sanitized_d4 = Utility.sanitize(d4);
                    String headerValue = format("attachment; filename=\"%s\"", new Object[]{"NoChangeTransactionListForUser_" + sanitized_d3 + "_" + sanitized_d4 + ".pdf"});
                    response.setHeader(headerKey, headerValue);
                    try (OutputStream outStream = response.getOutputStream()) {
                        outStream.write(decodeBase64(base64.getBytes()));
                    }
                } else {
                    redirect(request, response, "page_404.html");
                }

            } else {

                String base64 = Utility.sanitize(nctl.receiptexcel(path, pdflist, alcolonne, d3, d4));

                if (base64 != null) {
                    String headerKey = "Content-Disposition";
                    String sanitized_d3 = Utility.sanitize(d3);
                    String sanitized_d4 = Utility.sanitize(d4);
                    String headerValue = format("attachment; filename=\"%s\"", new Object[]{"NoChangeTransactionListForUser_" + sanitized_d3 + "_" + sanitized_d4 + ".xls"});
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
    protected void TaxFree(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

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

        String data1 = formatStringtoStringDate_null(d3, patternnormdate_filter, patternsql);
        String data2 = formatStringtoStringDate_null(d4, patternnormdate_filter, patternsql);
        String[] nc_cat1 = request.getParameterValues("nc_cat1");
        String list_nc_cat = "";
        if (nc_cat1 == null) {
            ArrayList<NC_category> array_nc_cat = dbm.list_nc_category_enabled();
            for (int i = 0; i < array_nc_cat.size(); i++) {
                if (array_nc_cat.get(i).getFg_tipo_transazione_nc().equals("3")) {
                    list_nc_cat = list_nc_cat + array_nc_cat.get(i).getGruppo_nc() + ";";
                }
            }
        } else {
            String totallist = parseArrayValues(nc_cat1);
            if (totallist.contains("...")) {
                ArrayList<NC_category> array_nc_cat = dbm.list_nc_category_enabled();
                for (int i = 0; i < array_nc_cat.size(); i++) {
                    if (array_nc_cat.get(i).getFg_tipo_transazione_nc().equals("3")) {
                        list_nc_cat = list_nc_cat + array_nc_cat.get(i).getGruppo_nc() + ";";
                    }
                }
            } else {
                list_nc_cat = parseArrayValues(nc_cat1);
            }
        }
        ArrayList<Branch> listbr = dbm.list_branch_completeAFTER311217();
        String path = Utility.sanitize(dbm.getPath("temp"));
        //path = "F:\\com\\";
        String branch = Utility.sanitize(request.getParameter("branch"));
        ArrayList<String> br1 = parseString(branch, ",");
        if (br1.isEmpty()) {
            br1 = (ArrayList<String>) listbr.stream().map(valore -> valore.getCod()).distinct().collect(toList());
        }

        ArrayList<String> alcolonne = new ArrayList<>();
        alcolonne.add("Date");
        alcolonne.add("Category");
        alcolonne.add("Total Refund");
        alcolonne.add("Custom");
        alcolonne.add("No Custom");
        alcolonne.add("Total Mod.");

        TaxFree nctl = new TaxFree();
        ArrayList<TaxFree_value> pdflist = new ArrayList<>();
        for (int x = 0; x < br1.size(); x++) {
            String[] fil = {br1.get(x), formatBankBranchReport(br1.get(x), "BR", null, listbr)};
            ArrayList<TaxFree_value> dati = dbm.list_TaxFree_value(fil[0], data1, data2, list_nc_cat);
            TaxFree_value pdf = new TaxFree_value();
            pdf.setId_filiale(fil[0]);
            pdf.setDe_filiale(fil[1]);
            pdf.setDataDa(d3);
            pdf.setDataA(d4);
            pdf.setDati(dati);
            if (dati.size() > 0) {
                pdflist.add(pdf);
            }

        }

        dbm.closeDB();

        if (pdflist.size() > 0) {

//        ArrayList<TaxFree_value> dati = new ArrayList<>();
//        dati = Engine.getTaxFree();
            if (Output.equals("PDF")) {

                String base64 = Utility.sanitize(nctl.receipt(path, pdflist, alcolonne, d3, d4));

                if (base64 != null) {
                    String headerKey = "Content-Disposition";
                    String sanitized_d3 = Utility.sanitize(d3);
                    String sanitized_d4 = Utility.sanitize(d4);
                    String headerValue = format("attachment; filename=\"%s\"", new Object[]{"SummaryTaxFree_" + sanitized_d3 + "_" + sanitized_d4 + ".pdf"});
                    response.setHeader(headerKey, headerValue);
                    try (OutputStream outStream = response.getOutputStream()) {
                        outStream.write(decodeBase64(base64.getBytes()));
                    }
                } else {
                    redirect(request, response, "page_404.html");
                }

            } else {

                String base64 = Utility.sanitize(nctl.receiptexcel(path, pdflist, alcolonne, d3, d4));

                if (base64 != null) {
                    String headerKey = "Content-Disposition";
                    String sanitized_d3 = Utility.sanitize(d3);
                    String sanitized_d4 = Utility.sanitize(d4);
                    String headerValue = format("attachment; filename=\"%s\"", new Object[]{"SummaryTaxFree_" + sanitized_d3 + "_" + sanitized_d4 + ".xls"});
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
    protected void BBTransactionList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
        //path = "F:\\com\\";
        ArrayList<Branch> allenabledbr = dbm.list_branch_completeAFTER311217();
        String filiale = Utility.sanitize(request.getParameter("branch"));
        String fil[] = new String[2];
        if (filiale == null || filiale.equals("")) {
            fil = dbm.getCodLocal(false);
        } else {
            fil[0] = filiale;
            fil[1] = formatBankBranchReport(filiale, "BR", null, allenabledbr);
        }

        ArrayList<String> myfil = new ArrayList<>();
        myfil.add(fil[0]);

        TillTransactionList_value pdf = dbm.list_BBTransactionList_value(myfil, data1, data2, null, allenabledbr);
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

                String base64 = Utility.sanitize(rma.receipt(path, pdf, d3, d4, "BuyBack Transaction List ", 1));//ultimo paramento se 1 è un BBtransactionlist
                if (base64 != null) {
                    String headerKey = "Content-Disposition";
                    String sanitized_d3 = Utility.sanitize(d3);
                    String sanitized_d4 = Utility.sanitize(d4);
                    String headerValue = format("attachment; filename=\"%s\"", new Object[]{"BBTransactionList_" + sanitized_d3 + "_" + sanitized_d4 + ".pdf"});
                    response.setHeader(headerKey, headerValue);
                    try (OutputStream outStream = response.getOutputStream()) {
                        outStream.write(decodeBase64(base64.getBytes()));
                    }
                } else {
                    redirect(request, response, "page_404.html");
                }

            } else {

                String base64 = Utility.sanitize(rma.receiptexcel(path, pdf, d3, d4, "BuyBack Transaction List ", 1));

                if (base64 != null) {
                    String headerKey = "Content-Disposition";
                    String sanitized_d3 = Utility.sanitize(d3);
                    String sanitized_d4 = Utility.sanitize(d4);
                    String headerValue = format("attachment; filename=\"%s\"", new Object[]{"BBTransactionList_" + sanitized_d3 + "_" + sanitized_d4 + ".xls"});
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
    protected void SBTransactionListGroup(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

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

        ArrayList<Branch> allenabledbr = dbm.list_branch_completeAFTER311217();
        String branch = Utility.sanitize(request.getParameter("branch"));
        ArrayList<String> br1 = parseString(branch, ",");

        if (br1.isEmpty()) {
            br1 = (ArrayList<String>) allenabledbr.stream().map(valore -> valore.getCod()).distinct().collect(toList());
        }

        TillTransactionListBB_value pdfsell = dbm.list_SBTransactionList(br1, data1, data2, allenabledbr);

        dbm.closeDB();

        TillTransactionListBB rma = new TillTransactionListBB();

        if (pdfsell != null) {
            ArrayList<String> datifooter = new ArrayList<>();
            datifooter.add(pdfsell.getTransactionnumberresidentbuy());
            datifooter.add(pdfsell.getTransactionnumbernonresidentbuy());
            datifooter.add(pdfsell.getInternetbookingamountyes());
            datifooter.add(pdfsell.getInternetbookingnumberyes());
            datifooter.add(pdfsell.getPosbuyamount());
            datifooter.add(pdfsell.getPosbuynumber());
            datifooter.add(pdfsell.getBankbuyamount());
            datifooter.add(pdfsell.getBankbuynumber());
            datifooter.add(pdfsell.getTransactionnumberresidentsell());
            datifooter.add(pdfsell.getTransactionnumbernonresidentsell());
            datifooter.add(pdfsell.getInternetbookingamountno());
            datifooter.add(pdfsell.getInternetbookingnumberno());
            datifooter.add(pdfsell.getPossellamount());
            datifooter.add(pdfsell.getPossellnumber());
            datifooter.add(pdfsell.getBanksellamount());
            datifooter.add(pdfsell.getBanksellnumber());
            pdfsell.setFooterdati(datifooter);
            if (Output.equals("PDF")) {
                String base64 = Utility.sanitize(rma.receipt(path, pdfsell, d3, d4, "SellBack Transaction List - Group By Sell-Buy"));
                if (base64 != null) {
                    String headerKey = "Content-Disposition";
                    String sanitized_d3 = Utility.sanitize(d3);
                    String sanitized_d4 = Utility.sanitize(d4);
                    String headerValue = format("attachment; filename=\"%s\"", new Object[]{"SBTransactionList_" + sanitized_d3 + "_" + sanitized_d4 + ".pdf"});
                    response.setHeader(headerKey, headerValue);
                    try (OutputStream outStream = response.getOutputStream()) {
                        outStream.write(decodeBase64(base64.getBytes()));
                    }
                } else {
                    redirect(request, response, "page_404.html");
                }
            } else {
                String base64 = Utility.sanitize(rma.receiptexcel(path, pdfsell, d3, d4, "SellBack Transaction List - Group By Sell-Buy"));
                if (base64 != null) {
                    String headerKey = "Content-Disposition";
                    String sanitized_d3 = Utility.sanitize(d3);
                    String sanitized_d4 = Utility.sanitize(d4);
                    String headerValue = format("attachment; filename=\"%s\"", new Object[]{"SBTransactionList_" + sanitized_d3 + "_" + sanitized_d4 + ".xlsx"});
                    response.setHeader(headerKey, headerValue);
                    try (OutputStream outStream = response.getOutputStream()) {
                        outStream.write(decodeBase64(base64.getBytes()));
                    }
                } else {
                    redirect(request, response, "page_404.html");
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
    protected void BBTransactionListGroup(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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

        ArrayList<Branch> allenabledbr = dbm.list_branch_completeAFTER311217();
        String branch = Utility.sanitize(request.getParameter("branch"));
        ArrayList<String> br1 = parseString(branch, ",");

        if (br1.isEmpty()) {
            br1 = (ArrayList<String>) allenabledbr.stream().map(valore -> valore.getCod()).distinct().collect(toList());
        }
        TillTransactionListBB_value pdfsell = dbm.list_BBTransactionList_mod(br1, data1, data2, allenabledbr);
        dbm.closeDB();
        TillTransactionListBB rma = new TillTransactionListBB();
        if (pdfsell != null) {
            ArrayList<String> datifooter = new ArrayList<>();
            datifooter.add(pdfsell.getTransactionnumberresidentbuy());
            datifooter.add(pdfsell.getTransactionnumbernonresidentbuy());
            datifooter.add(pdfsell.getInternetbookingamountyes());
            datifooter.add(pdfsell.getInternetbookingnumberyes());
            datifooter.add(pdfsell.getPosbuyamount());
            datifooter.add(pdfsell.getPosbuynumber());
            datifooter.add(pdfsell.getBankbuyamount());
            datifooter.add(pdfsell.getBankbuynumber());
            datifooter.add(pdfsell.getTransactionnumberresidentsell());
            datifooter.add(pdfsell.getTransactionnumbernonresidentsell());
            datifooter.add(pdfsell.getInternetbookingamountno());
            datifooter.add(pdfsell.getInternetbookingnumberno());
            datifooter.add(pdfsell.getPossellamount());
            datifooter.add(pdfsell.getPossellnumber());
            datifooter.add(pdfsell.getBanksellamount());
            datifooter.add(pdfsell.getBanksellnumber());
//            pdfbuy.setFooterdati(datifooter);
            pdfsell.setFooterdati(datifooter);
            if (Output.equals("PDF")) {

                String base64 = Utility.sanitize(rma.receipt(path, pdfsell, d3, d4, "BuyBack Transaction List - Group By Buy-Sell "));
                if (base64 != null) {
                    String headerKey = "Content-Disposition";
                    String sanitized_d3 = Utility.sanitize(d3);
                    String sanitized_d4 = Utility.sanitize(d4);
                    String headerValue = format("attachment; filename=\"%s\"", new Object[]{"BBTransactionList_" + sanitized_d3 + "_" + sanitized_d4 + ".pdf"});
                    response.setHeader(headerKey, headerValue);
                    try (OutputStream outStream = response.getOutputStream()) {
                        outStream.write(decodeBase64(base64.getBytes()));
                    }
                } else {
                    redirect(request, response, "page_404.html");
                }

            } else {

                String base64 = Utility.sanitize(rma.receiptexcel(path, pdfsell, d3, d4, "BuyBack Transaction List - Group By Buy-Sell "));

                if (base64 != null) {
                    String headerKey = "Content-Disposition";
                    String sanitized_d3 = Utility.sanitize(d3);
                    String sanitized_d4 = Utility.sanitize(d4);
                    String headerValue = format("attachment; filename=\"%s\"", new Object[]{"BBTransactionList_" + sanitized_d3 + "_" + sanitized_d4 + ".xlsx"});
                    response.setHeader(headerKey, headerValue);
                    try (OutputStream outStream = response.getOutputStream()) {
                        outStream.write(decodeBase64(base64.getBytes()));
                    }
                } else {
                    redirect(request, response, "page_404.html");
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
    protected void BranchStockInquiryA(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String d3 = Utility.sanitize(request.getParameter("d3"));
        String d4 = getTimepicker(Utility.sanitize(request.getParameter("d4")));
        String dt_tot = d3 + " " + d4;

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
        String data1 = formatStringtoStringDate_null(dt_tot, patternnormdate, patternsqldate);

        Db_Master dbm = new Db_Master();
        String path = Utility.sanitize(dbm.getPath("temp"));

        String filiale = Utility.sanitize(request.getParameter("branch"));
        String fil[] = new String[2];
        if (filiale == null || filiale.equals("")) {
            fil = dbm.getCodLocal(false);
        } else {
            fil[0] = filiale;
            fil[1] = formatBankBranchReport(filiale, "BR", null, list_branch_completeAFTER311217());
        }
        ArrayList<BranchStockInquiry_value> dati = dbm.list_BranchStockInquiry_value(fil, data1, "CH");
        dbm.closeDB();
        if (dati.size() > 0) {

            BranchStockInquiry bsi = new BranchStockInquiry();
            BranchStockInquiry_value pdf = new BranchStockInquiry_value();
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

            pdf.setId_filiale(fil[0]);
            pdf.setDe_filiale(fil[1]);
            pdf.setDati(dati);

            if (Output.equals("PDF")) {
                String base64 = Utility.sanitize(bsi.receipt_new(path, pdf, alcolonne, dt_tot));
                if (base64 != null) {
                    String headerKey = "Content-Disposition";
                    String sanitized_dt_tot = Utility.sanitize(dt_tot);
                    String headerValue = format("attachment; filename=\"%s\"", new Object[]{"BranchStockInquiry_" + sanitized_dt_tot + ".pdf"});
                    response.setHeader(headerKey, headerValue);
                    try (OutputStream outStream = response.getOutputStream()) {
                        outStream.write(decodeBase64(base64.getBytes()));
                    }
                } else {
                    redirect(request, response, "page_404.html");
                }

            } else {

                ArrayList<BranchStockInquiry_value> complete = new ArrayList<>();
                complete.add(pdf);
                String base64 = Utility.sanitize(bsi.receiptexcel_new(path, complete, alcolonne, dt_tot));
                if (base64 != null) {
                    String headerKey = "Content-Disposition";
                    String sanitized_dt_tot = Utility.sanitize(dt_tot);
                    String headerValue = format("attachment; filename=\"%s\"", new Object[]{"BranchStockInquiry_" + sanitized_dt_tot + ".xlsx"});
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

    protected void BranchStockInquiry(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String d3 = Utility.sanitize(request.getParameter("d3"));
        String d4 = getTimepicker(Utility.sanitize(request.getParameter("d4")));
        String dt_tot = d3 + " " + d4;

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
        String data1 = formatStringtoStringDate_null(dt_tot, patternnormdate, patternsqldate);

        Db_Master dbm = new Db_Master();
        String path = Utility.sanitize(dbm.getPath("temp"));
        String filiale = Utility.sanitize(request.getParameter("branch"));
        String fil[] = new String[2];
        if (filiale == null || filiale.equals("")) {
            fil = dbm.getCodLocal(false);
        } else {
            fil[0] = filiale;
            fil[1] = formatBankBranchReport(filiale, "BR", null, list_branch_completeAFTER311217());
        }
        ArrayList<BranchStockInquiry_value> dati = dbm.list_BranchStockInquiry_value(fil, data1, "CH");
        dbm.closeDB();
        if (dati.size() > 0) {
            BranchStockInquiry bsi = new BranchStockInquiry();
            BranchStockInquiry_value pdf = new BranchStockInquiry_value();
            ArrayList<String> alcolonne = new ArrayList<>();
            alcolonne.add("Currency");
            alcolonne.add("Notes");
            if (is_IT) {
                alcolonne.add("Euro Travel Cheques");
            } else {
                alcolonne.add("");
            }
            alcolonne.add("Travel Cheques");
            pdf.setId_filiale(fil[0]);
            pdf.setDe_filiale(fil[1]);
            pdf.setDati(dati);
            if (Output.equals("PDF")) {
                String base64 = Utility.sanitize(bsi.receipt(path, pdf, alcolonne, dt_tot));
                if (base64 != null) {
                    String headerKey = "Content-Disposition";
                    String sanitized_dt_tot = Utility.sanitize(dt_tot);
                    String headerValue = format("attachment; filename=\"%s\"", new Object[]{"BranchStockInquiry_" + sanitized_dt_tot + ".pdf"});
                    response.setHeader(headerKey, headerValue);
                    try (OutputStream outStream = response.getOutputStream()) {
                        outStream.write(decodeBase64(base64.getBytes()));
                    }
                } else {
                    redirect(request, response, "page_404.html");
                }

            } else {

                String base64 = Utility.sanitize(bsi.receiptexcel(path, pdf, alcolonne, dt_tot));

                if (base64 != null) {
                    String headerKey = "Content-Disposition";
                    String sanitized_dt_tot = Utility.sanitize(dt_tot);
                    String headerValue = format("attachment; filename=\"%s\"", new Object[]{"BranchStockInquiry_" + sanitized_dt_tot + ".xls"});
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
    protected void NoChangeBonus(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

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

        String data1 = formatMonthtoDate_null(d3, pattermonthnorm, patternnormdate_filter, patternmonthsql, "01/");

        Db_Master dbm = new Db_Master();

        String[] nc_cat1 = request.getParameterValues("nc_cat1");
        String list_nc_categ = "";
        if (nc_cat1 == null) {
            ArrayList<NC_category> array_nc_caus = dbm.query_nc_category_bonus();
            for (int i = 0; i < array_nc_caus.size(); i++) {
                list_nc_categ = list_nc_categ + array_nc_caus.get(i).getGruppo_nc() + ";";

            }
        } else {
            String totallist = parseArrayValues(nc_cat1);
            if (totallist.contains("...")) {
                ArrayList<NC_category> array_nc_caus = dbm.query_nc_category_bonus();
                for (int i = 0; i < array_nc_caus.size(); i++) {
                    list_nc_categ = list_nc_categ + array_nc_caus.get(i).getGruppo_nc() + ";";
                }
            } else {
                list_nc_categ = parseArrayValues(nc_cat1);
            }
        }

        String path = Utility.sanitize(dbm.getPath("temp"));
        //path = "F:\\com\\";

        String branch = Utility.sanitize(request.getParameter("branch"));
        ArrayList<String> br1 = parseString(branch, ",");
        ArrayList<Branch> allfill = dbm.list_branch_completeAFTER311217();
        if (br1.isEmpty()) {
            br1 = (ArrayList<String>) allfill.stream().map(valore -> valore.getCod()).distinct().collect(toList());
        }

        NoChangeBonus_value pdf = dbm.list_NoChangeBonus_value(br1, data1, d3, list_nc_categ);
        dbm.closeDB();
        if (!pdf.getDati().isEmpty()) {
            NoChangeBonus bsi = new NoChangeBonus();
            if (Output.equals("PDF")) {

                String base64 = Utility.sanitize(bsi.receipt(path, pdf, pdf.getAlcolonne(), d3, d4, br1, allfill));
                if (base64 != null) {
                    String headerKey = "Content-Disposition";
                    String sanitized_d3 = Utility.sanitize(d3);
                    String headerValue = format("attachment; filename=\"%s\"", new Object[]{"NoChangeBonus_" + sanitized_d3 + ".pdf"});
                    response.setHeader(headerKey, headerValue);
                    try (OutputStream outStream = response.getOutputStream()) {
                        outStream.write(decodeBase64(base64.getBytes()));
                    }
                } else {
                    redirect(request, response, "page_404.html");
                }

            } else {

                String base64 = Utility.sanitize(bsi.receiptexcel(path, pdf, pdf.getAlcolonne(), d3, d4, br1, allfill));

                if (base64 != null) {
                    String headerKey = "Content-Disposition";
                    String sanitized_d3 = Utility.sanitize(d3);
                    String headerValue = format("attachment; filename=\"%s\"", new Object[]{"NoChangeBonus_" + sanitized_d3 + ".xls"});
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
    protected void OfficeStockPrice_now(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String Output = Utility.sanitize(request.getParameter("Output"));
        String filiale = Utility.sanitize(request.getParameter("branch"));
        DateTime now = getNowDT();
        String datareport = now.toString(patternnormdate);

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
        String localcur = dbm.get_local_currency()[0];
        ArrayList<Branch> listallbr = dbm.list_branch_completeAFTER311217();
        ArrayList<OfficeStockPrice_value> dati = dbm.list_OfficeStockPrice_value(filiale);
        dbm.closeDB();

        if (dati.size() > 0) {
            OfficeStockPrice osp = new OfficeStockPrice();
            OfficeStockPrice_value pdf = new OfficeStockPrice_value();
            pdf.setLocalcurrency(localcur);
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
            pdf.setId_filiale(filiale);
            pdf.setDe_filiale(get_Branch(filiale, listallbr).getDe_branch());
            pdf.setDati(dati);
            if (Output.equals("PDF")) {
                String base64 = Utility.sanitize(osp.receipt(path, pdf, alcolonne, datareport));
                if (base64 != null) {
                    String headerKey = "Content-Disposition";
                    String sanitized_datareport = Utility.sanitize(datareport);
                    String headerValue = format("attachment; filename=\"%s\"", new Object[]{"OfficeStockPrice_" + sanitized_datareport + ".pdf"});
                    response.setHeader(headerKey, headerValue);
                    try (OutputStream outStream = response.getOutputStream()) {
                        outStream.write(decodeBase64(base64.getBytes()));
                    }
                } else {
                    redirect(request, response, "page_404.html");
                }
            } else {
                String base64 = Utility.sanitize(osp.receiptexcel(path, pdf, alcolonne, datareport));
                if (base64 != null) {
                    String headerKey = "Content-Disposition";
                    String sanitized_datareport = Utility.sanitize(datareport);
                    String headerValue = format("attachment; filename=\"%s\"", new Object[]{"OfficeStockPrice_" + sanitized_datareport + ".xls"});
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
    protected void OfficeStockPrice(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String Output = Utility.sanitize(request.getParameter("Output"));
        String spres = Utility.sanitize(request.getParameter("spres"));
        String filiale = Utility.sanitize(request.getParameter("branch"));
        String d3 = Utility.sanitize(request.getParameter("d3"));
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
        String valutalocale = dbm.get_local_currency()[0];
        String path = dbm.getPath("temp");
////        //path = "F:\\com\\";
        ArrayList<Branch> listallbr = dbm.list_branch_completeAFTER311217();

        ArrayList<OfficeStockPrice_value> dati = dbm.list_OfficeStockPrice_value(spres, filiale);
        dbm.closeDB();
        if (dati.size() > 0) {
            OfficeStockPrice osp = new OfficeStockPrice();
            OfficeStockPrice_value pdf = new OfficeStockPrice_value();
            pdf.setLocalcurrency(valutalocale);
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
            pdf.setId_filiale(filiale);
            pdf.setDe_filiale(get_Branch(filiale, listallbr).getDe_branch());
            pdf.setDati(dati);
            if (Output.equals("PDF")) {
                String base64 = Utility.sanitize(osp.receipt(path, pdf, alcolonne, dati.get(0).getData()));
                if (base64 != null) {
                    String headerKey = "Content-Disposition";
                    String sanitized_d3 = Utility.sanitize(d3);
                    String headerValue = format("attachment; filename=\"%s\"", new Object[]{"OfficeStockPrice_" + sanitized_d3 + ".pdf"});
                    response.setHeader(headerKey, headerValue);
                    try (OutputStream outStream = response.getOutputStream()) {
                        outStream.write(decodeBase64(base64.getBytes()));
                    }
                } else {
                    redirect(request, response, "page_404.html");
                }
            } else {
                String base64 = Utility.sanitize(osp.receiptexcel(path, pdf, alcolonne, d3));
                if (base64 != null) {
                    String headerKey = "Content-Disposition";
                    String sanitized_d3 = Utility.sanitize(d3);
                    String headerValue = format("attachment; filename=\"%s\"", new Object[]{"OfficeStockPrice_" + sanitized_d3 + ".xls"});
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
     * @param d4
     * @return
     */
    public static String getTimepicker(String d4) {
        if (d4.contains(":")) {
            String h = d4.split(":")[0];
            int n = parseIntR(h);
            if (n < 10) {
                return "0" + n + ":" + d4.split(":")[1] + ":" + d4.split(":")[2];
            }
        }
        return d4;

    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void StockInquiry(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String d3 = Utility.sanitize(request.getParameter("d3"));
        String d4 = getTimepicker(Utility.sanitize(request.getParameter("d4")));
        String dt_tot = d3 + " " + d4;

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

        String data1 = formatStringtoStringDate_null(dt_tot, patternnormdate, patternsqldate);
        Db_Master dbm = new Db_Master();
        String path = Utility.sanitize(dbm.getPath("temp"));
        //path = "F:\\com\\";
        String filiale = Utility.sanitize(request.getParameter("branch"));
        String fil[] = new String[2];
        if (filiale == null || filiale.equals("")) {
            fil = dbm.getCodLocal(false);
        } else {
            fil[0] = filiale;
            fil[1] = formatBankBranchReport(filiale, "BR", null, list_branch_completeAFTER311217());
        }
        ArrayList<StockInquiry_value> dati = dbm.list_StockInquiry_value(fil, data1, "CH");
        dbm.closeDB();
        if (dati.size() > 0) {
            ArrayList<String> alcolonne = new ArrayList<>();
            alcolonne.add("Till/Safe");
            alcolonne.add("Currency");
            alcolonne.add("Notes");
            if (is_IT) {
                alcolonne.add("Euro Travel Cheques");
            } else {
                alcolonne.add("");
            }
            alcolonne.add("Travel Cheques");

            StockInquiry siq = new StockInquiry();
            if (Output.equals("PDF")) {

                String base64 = Utility.sanitize(siq.receipt(path, dati, alcolonne, dt_tot));
                if (base64 != null) {
                    String headerKey = "Content-Disposition";
                    String sanitized_dt_tot = Utility.sanitize(dt_tot);
                    String headerValue = format("attachment; filename=\"%s\"", new Object[]{"StockInquiry_" + sanitized_dt_tot + ".pdf"});
                    response.setHeader(headerKey, headerValue);
                    try (OutputStream outStream = response.getOutputStream()) {
                        outStream.write(decodeBase64(base64.getBytes()));
                    }
                } else {
                    redirect(request, response, "page_404.html");
                }
            } else {

                String base64 = Utility.sanitize(siq.receiptexcel(path, dati, alcolonne, d3));

                if (base64 != null) {
                    String headerKey = "Content-Disposition";
                    String sanitized_dt_tot = Utility.sanitize(dt_tot);
                    String headerValue = format("attachment; filename=\"%s\"", new Object[]{"StockInquiry_" + sanitized_dt_tot + ".xls"});
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
    protected void StockPrice(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        DateTime now = getNowDT();
        String datareport = now.toString(patternnormdate);
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
        String filiale = Utility.sanitize(request.getParameter("branch"));
        String fil[] = new String[2];
        if (filiale == null || filiale.equals("")) {
            fil = dbm.getCodLocal(false);
        } else {
            fil[0] = filiale;
            fil[1] = formatBankBranchReport(filiale, "BR", null, list_branch_completeAFTER311217());
        }
        ArrayList<StockPrice_value> dati = dbm.list_StockPrice_value(fil);
        dbm.closeDB();
        if (dati.size() > 0) {
            StockPrice sp = new StockPrice();
            StockPrice_value pdf = new StockPrice_value();
            pdf.setId_filiale(fil[0]);
            pdf.setDe_filiale(fil[1]);
            pdf.setDati(dati);
            if (Output.equals("PDF")) {
                String base64 = Utility.sanitize(sp.receipt(path, pdf, datareport));
                if (base64 != null) {
                    String headerKey = "Content-Disposition";
                    String sanitized_datareport = Utility.sanitize(datareport);
                    String headerValue = format("attachment; filename=\"%s\"", new Object[]{"StockPrice_" + sanitized_datareport + ".pdf"});
                    response.setHeader(headerKey, headerValue);
                    try (OutputStream outStream = response.getOutputStream()) {
                        outStream.write(decodeBase64(base64.getBytes()));
                    }
                } else {
                    redirect(request, response, "page_404.html");
                }
            } else {
                String base64 = Utility.sanitize(sp.receiptexcel(path, pdf, datareport));
                if (base64 != null) {
                    String headerKey = "Content-Disposition";
                    String sanitized_datareport = Utility.sanitize(datareport);
                    String headerValue = format("attachment; filename=\"%s\"", new Object[]{"StockPrice_" + sanitized_datareport + ".xls"});
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
    protected void SizeQuantity(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String d3 = Utility.sanitize(request.getParameter("d3"));
        String data1 = formatStringtoStringDate_null(d3, patternnormdate_filter, patternsql);
        String curr = Utility.sanitize(request.getParameter("curr"));
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
//        ArrayList<String> br1 = parseString(branch, ",");
        String filiale = Utility.sanitize(request.getParameter("branch"));
        String fil[] = new String[2];
        if (filiale == null || filiale.equals("")) {
            fil = dbm.getCodLocal(false);
        } else {
            fil[0] = filiale;
            fil[1] = formatBankBranchReport(filiale, "BR", null, list_branch_completeAFTER311217());
        }
        String path = dbm.getPath("temp");

        ArrayList<String> br1 = new ArrayList<>();
        br1.add(fil[0]);

        dbm.closeDB();

        ArrayList<String> alcolonne = new ArrayList<>();//
        alcolonne.add("Kind");
        alcolonne.add("Size");
        alcolonne.add("Quantity");
        alcolonne.add("Total");

        if (Output.equals("PDF")) {
            String base64 = Utility.sanitize(new C_SizeAndQuantity().main(path, d3, data1, alcolonne, br1, allenabledbr, curr));
            if (base64 != null) {
                String headerKey = "Content-Disposition";
                String sanitized_d3 = Utility.sanitize(d3);
                String headerValue = format("attachment; filename=\"%s\"", new Object[]{"C_SizeAndQuantity_" + sanitized_d3 + ".pdf"});
                response.setHeader(headerKey, headerValue);
                try (OutputStream outStream = response.getOutputStream()) {
                    outStream.write(decodeBase64(base64.getBytes()));
                }
            } else {
                redirect(request, response, "page_404.html");
            }
        } else {

            String base64 = Utility.sanitize(new C_SizeAndQuantity().mainexcel(path, d3, data1, alcolonne, br1, allenabledbr, curr));

            if (base64 != null) {
                String headerKey = "Content-Disposition";
                String sanitized_d3 = Utility.sanitize(d3);
                String headerValue = format("attachment; filename=\"%s\"", new Object[]{"C_SizeAndQuantity_" + sanitized_d3 + ".xls"});
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
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) {
        try {
            setProperty("java.awt.headless", "true");

            String link_value = verifySession(request);
            if (link_value != null) {
                redirect(request, response, link_value);
            }
            response.setContentType("text/html;charset=UTF-8");
//            request.setCharacterEncoding("UTF-8");
            String type = Utility.sanitize(request.getParameter("type"));

            String user = (String) request.getSession().getAttribute("us_cod");
            insertTR("W", user, "Generate report code: " + type);

            if (type.equals("Openclose_Synt")) {
                Openclose_Synt(request, response);
            }
            if (type.equals("Openclose_Anal")) {
                Openclose_Anal(request, response);
            }
            if (type.equals("Openclose_Error")) {
                Openclose_Error(request, response);
            }
            if (type.equals("RegisterMonthly")) {
                RegisterMonthly(request, response);
            }
            if (type.equals("RegisterSellMonthly")) {
                RegisterSellMonthlyr(request, response);
            }
            if (type.equals("RegisterBuyMonthly")) {
                RegisterBuyMonthly(request, response);
            }
            if (type.equals("TillTransactionList")) {
                TillTransactionList(request, response);
            }
            if (type.equals("TillTransactionListCurrency")) {
                TillTransactionListCurrency(request, response);
            }
            if (type.equals("HeavyTransactionList")) {
                HeavyTransactionList(request, response);
            }
            if (type.equals("DeleteTransactionList")) {
                DeleteTransactionList(request, response);
            }
            if (type.equals("ToBranchingSheet")) {
                ToBranchingSheet(request, response);
            }
            if (type.equals("ToBankingSheet")) {
                ToBankingSheet(request, response);
            }
            if (type.equals("InternalTransferList")) {
                InternalTransferList(request, response);
            }
            if (type.equals("Daily")) {
                Daily(request, response);
            }
            if (type.equals("Dailynow")) {
                Dailynow(request, response);
            }
            if (type.equals("NoChangeTransactionList")) {
                NoChangeTransactionList(request, response);
            }
            if (type.equals("NoChangeCategoryTransactionList")) {
                NoChangeCategoryTransactionList(request, response);
            }
            if (type.equals("StockReport")) {
                StockReport(request, response);
            }
            if (type.equals("TotalStockReport")) {
                TotalStockReport(request, response);
            }
            if (type.equals("WesternUnionService")) {
                WesternUnionService(request, response);
            }
            if (type.equals("InsuranceTransactionList")) {
                InsuranceTransactionList(request, response);
            }
            if (type.equals("NoChangeTransactionListForUser")) {
                NoChangeTransactionListForUser(request, response);
            }
            if (type.equals("TaxFree")) {
                TaxFree(request, response);
            }
            if (type.equals("BBTransactionList")) {
                BBTransactionList(request, response);
            }
            if (type.equals("BBTransactionListGroup")) {
                BBTransactionListGroup(request, response);
            }
            if (type.equals("SBTransactionListGroup")) {
                SBTransactionListGroup(request, response);
            }
            if (type.equals("BranchStockInquiry")) {
                BranchStockInquiry(request, response);
            }
            if (type.equals("BranchStockInquiryA")) {
                BranchStockInquiryA(request, response);
            }

            if (type.equals("NoChangeBonus")) {
                NoChangeBonus(request, response);
            }

            if (type.equals("OfficeStockPrice")) {
                OfficeStockPrice(request, response);
            }
            if (type.equals("OfficeStockPrice_now")) {
                OfficeStockPrice_now(request, response);
            }

            if (type.equals("StockInquiry")) {
                StockInquiry(request, response);
            }
            if (type.equals("StockPrice")) {
                StockPrice(request, response);
            }
            if (type.equals("SizeQuantity")) {
                SizeQuantity(request, response);
            }
            if (type.equals("SanctionAttempts")) {
                SanctionAttempts(request, response);
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
