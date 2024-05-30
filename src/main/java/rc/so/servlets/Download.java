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
import rc.so.entity.Document;
import rc.so.entity.ET_change;
import static rc.so.entity.ET_change.format_tofrom_brba;
import rc.so.entity.Figures;
import rc.so.entity.NC_transaction;
import rc.so.entity.Newsletters;
import rc.so.entity.Office;
import rc.so.entity.VATcode;
import static rc.so.pdf.NewETReceipt.FROMBANK_pdf;
import static rc.so.pdf.NewETReceipt.FROMBRANCH_CHANGE_pdf;
import static rc.so.pdf.NewETReceipt.FROMBRANCH_NOCHANGE_pdf;
import static rc.so.pdf.NewETReceipt.TOBANK_POS_pdf;
import static rc.so.pdf.NewETReceipt.TOBANK_pdf;
import static rc.so.pdf.NewETReceipt.TOBRANCH_CHANGE_pdf;
import static rc.so.pdf.NewETReceipt.TOBRANCH_NOCHANGE_pdf;
import rc.so.pdf.NewReceipt_UK;
import rc.so.pdf.Pdf;
import static rc.so.pdf.Pdf.modulo_profilatura;
import static rc.so.pdf.Pdf.printdeleted;
import static rc.so.pdf.Pdf.refund;
import static rc.so.pdf.Pdf.refund_CZ;
import rc.so.pdf.Receipt;
import rc.so.report.FromBankingSheet;
import rc.so.report.FromBranchingSheet;
import rc.so.report.FromBranchingSheet_value;
import rc.so.report.NoChangeFromBranchingSheet_value;
import rc.so.report.NoChangeToBranchingSheet_value;
import rc.so.report.ToBankingSheet;
import rc.so.report.ToBankingSheet_value;
import rc.so.report.ToBranchingSheet;
import rc.so.report.ToBranchingSheet_value;
import rc.so.report.ToPOSBASheet;
import static rc.so.util.Constant.is_CZ;
import static rc.so.util.Constant.is_UK;
import static rc.so.util.Constant.newpread;
import static rc.so.util.Constant.patternnormdate;
import static rc.so.util.Constant.patternnormdate_f;
import static rc.so.util.Constant.patternnormdate_filter;
import static rc.so.util.Constant.patternsqldate;
import static rc.so.util.Engine.formatBankBranch;
import static rc.so.util.Engine.formatBankBranchReport;
import static rc.so.util.Engine.get_NC_transaction;
import static rc.so.util.Engine.insertTR;
import static rc.so.util.Engine.verifySession;
import static rc.so.util.Utility.formatMysqltoDisplay;
import static rc.so.util.Utility.formatStringtoStringDate;
import static rc.so.util.Utility.getBase64;
import static rc.so.util.Utility.redirect;
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
import static org.apache.commons.codec.binary.Base64.encodeBase64String;
import static org.apache.commons.io.FileUtils.readFileToByteArray;
import static org.apache.commons.lang3.StringUtils.replace;
import org.joda.time.DateTime;
import rc.so.util.Utility;

/**
 *
 * @author rcosco
 */
public class Download
        extends HttpServlet {

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void viewPdf(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String cod = request.getParameter("cod");
        Db_Master db = new Db_Master();
        String base64 = Utility.sanitize(db.get_pdf_temp(cod));
        db.closeDB();
        insertTR("I", (String) request.getSession().getAttribute("us_cod"), (String) request.getSession().getAttribute("us_fil") + " - VIEW PDF FILE " + cod);
        if (base64 != null) {
            String headerKey = "Content-Disposition";
            String sanitizedcod = Utility.sanitize(cod);
            String headerValue = format("attachment; filename=\"%s\"", new Object[]{sanitizedcod + ".pdf"});
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
    protected void viewExcel(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String cod = request.getParameter("cod");
        Db_Master db = new Db_Master();
        String base64 = Utility.sanitize(db.get_pdf_temp(cod));
        db.closeDB();
        insertTR("I", (String) request.getSession().getAttribute("us_cod"), (String) request.getSession().getAttribute("us_fil") + " - VIEW EXCEL FILE " + cod);
        if (base64 != null) {
            String headerKey = "Content-Disposition";
            String sanitizedcod = Utility.sanitize(cod);
            String headerValue = format("attachment; filename=\"%s\"", new Object[]{sanitizedcod + ".xlsx"});
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
    protected void viewET_receipt(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String cod = request.getParameter("cod");
        Db_Master db = new Db_Master();
        ET_change et = db.get_ET_change(cod);
        db.closeDB();
        insertTR("I", (String) request.getSession().getAttribute("us_cod"), (String) request.getSession().getAttribute("us_fil") + " - VIEW RICEVUTA EXTERNAL TRANSFER: " + cod);
        if (et != null) {
            if (et.getFg_brba().equals("BA")) {
                viewET_frba_receipt(request, response);
            } else {
                viewET_frbr_receipt(request, response);
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
    protected void viewET_frbr_receipt(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String cod = Utility.sanitize(request.getParameter("cod"));
        String base64 = null;
        Db_Master db = new Db_Master();

        ET_change et = db.get_ET_change(cod);

        String pathtemp = Utility.sanitize(db.getPath("temp"));
        insertTR("I", Utility.sanitize((String) request.getSession().getAttribute("us_cod")), Utility.sanitize((String) request.getSession().getAttribute("us_fil")) + " - VIEW EXTERNAL TRANSFER FROM BRANCH " + cod);
        if (et != null) {
            Branch from = db.get_branch(et.getFiliale());
            Branch dest = db.get_branch(et.getCod_dest());

            if (et.getFg_tofrom().equals("T")) {
                ArrayList<ToBranchingSheet_value> dati = db.list_ToBranchingSheet_value(et);
                ArrayList<NoChangeToBranchingSheet_value> dati2 = db.list_ToBranchingSheet_valueNC(et);
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

                    pdf.setId_filiale(from.getCod());
                    pdf.setDe_filiale(from.getDe_branch());
                    pdf.setAuto(et.getAuto());
                    pdf.setFromsafe(from.getCod() + " - " + from.getDe_branch());
                    pdf.setTobranch(dest.getCod() + " - " + dest.getDe_branch());
                    pdf.setPinuser(et.getUser());
                    pdf.setNumtranfer(et.getId());
                    pdf.setNote(et.getNote());
                    pdf.setDati(dati);

                    if (newpread) {

                        if (dati.size() > 0) {
                            base64 = TOBRANCH_CHANGE_pdf(pathtemp, pdf, formatStringtoStringDate(et.getDt_it(), patternsqldate, patternnormdate));
                        } else {
                            base64 = TOBRANCH_NOCHANGE_pdf(pathtemp, pdf, formatStringtoStringDate(et.getDt_it(), patternsqldate, patternnormdate), dati2);
                        }
                    } else {
                        base64 = bsi.receipt(pathtemp, pdf, alcolonne, formatStringtoStringDate(et.getDt_it(), patternsqldate, patternnormdate), alcolonne2, dati2);
                    }

                    if (et.getFg_annullato().equals("1")) {
                        base64 = printdeleted(pathtemp, decodeBase64(base64), false, false);
                    }

                }

            } else {
                String note_fromBranch = db.note_fromBranch(et.getCod_in());
                ArrayList<FromBranchingSheet_value> dati = db.list_FromBranchingSheet_value(et);
                ArrayList<NoChangeFromBranchingSheet_value> dati2 = db.list_FromBranchingSheet_valueNC(et);
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
                    pdf.setId_filiale(from.getCod());
                    pdf.setDe_filiale(from.getDe_branch());
                    pdf.setFromsafe(from.getCod() + " - " + from.getDe_branch());
                    pdf.setTobranch(dest.getCod() + " - " + dest.getDe_branch());
                    pdf.setPinuser(et.getUser());
                    pdf.setNumtranfer(et.getId());
                    pdf.setNote(et.getNote());
                    pdf.setNoteFROM(note_fromBranch);
                    pdf.setDati(dati);

                    if (newpread) {
                        if (dati.size() > 0) {
                            base64 = Utility.sanitize(FROMBRANCH_CHANGE_pdf(pathtemp, pdf, formatStringtoStringDate(et.getDt_it(), patternsqldate, patternnormdate)));
                        } else {
                            base64 = FROMBRANCH_NOCHANGE_pdf(pathtemp, pdf, formatStringtoStringDate(et.getDt_it(), patternsqldate, patternnormdate), dati2);
                        }
                    } else {
                        base64 = bsi.receipt(pathtemp, pdf, alcolonne, formatStringtoStringDate(et.getDt_it(), patternsqldate, patternnormdate), alcolonne2, dati2);
                    }

                    if (et.getFg_annullato().equals("1")) {

                        base64 = Utility.sanitize(printdeleted(pathtemp, decodeBase64(base64), false, false));
                    }
                }

            }
        }

        db.closeDB();

        if (base64 != null) {
            String headerKey = "Content-Disposition";
            String headerValue = format("attachment; filename=\"%s\"", new Object[]{"BranchingSheet_" + new DateTime().toString("ddMMyyyy") + ".pdf"});
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
    protected void viewET_frba_receipt(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String cod = Utility.sanitize(request.getParameter("cod"));
        String namesheet = "BankingSheet_";
        Db_Master db = new Db_Master();
        ArrayList<String[]> array_credit_card = db.list_bank_pos_enabled();
        String pathtemp = Utility.sanitize(db.getPath("temp"));
        ET_change et = db.get_ET_change(cod);
        insertTR("I", (String) request.getSession().getAttribute("us_cod"), (String) request.getSession().getAttribute("us_fil") + " - VIEW EXTERNAL TRANSFER FROM BANK " + cod);
        String base64 = null;
        if (et != null) {
            ArrayList<String[]> array_bank = db.list_bank_enabled();
            String ba = formatBankBranch(et.getCod_dest(), "BA", array_bank, null, array_credit_card);
            ArrayList<Branch> allfill = db.list_branch_completeAFTER311217();

            if (et.getFg_tofrom().equals("T")) {
                ArrayList<ToBankingSheet_value> dati = db.list_ToBankingSheet_value(et);
                ArrayList<ToBankingSheet_value> datiPOS = db.list_POSBASheet_value(et);
                if (dati.size() > 0) {
                    ToBankingSheet bsi = new ToBankingSheet();
                    ToPOSBASheet bsi2 = new ToPOSBASheet();
                    boolean pos = false;
                    ArrayList<String> alcolonne = new ArrayList<>();
                    ToBankingSheet_value pdf = new ToBankingSheet_value();
                    pdf.setAuto(et.getAuto());
                    pdf.setId_filiale(et.getFiliale());
                    pdf.setDe_filiale(formatBankBranchReport(et.getFiliale(), "BR", null, allfill));
                    pdf.setFromsafe(et.getFiliale() + " - " + formatBankBranchReport(et.getFiliale(), "BR", null, allfill));
                    pdf.setTobank(et.getCod_dest() + " - " + ba);
                    pdf.setPinuser(et.getUser());
                    pdf.setNumtranfer(et.getId());
                    pdf.setNote(et.getNote());
                    if (format_tofrom_brba(et.getFg_tofrom(), et.getFg_brba(), et.getCod_dest(), array_credit_card).contains("POS")) {
                        pos = true;
                        namesheet = "ToPosBankAccountSheet_";
                        alcolonne.add("Currency");
                        alcolonne.add("Amount");
                        alcolonne.add("Amount");
                        alcolonne.add("Amount");
                        alcolonne.add("Amount");
                        alcolonne.add("Bank Total");
                        pdf.setDati(datiPOS);
                    } else {
                        alcolonne.add("Currency");
                        alcolonne.add("Amount");
                        alcolonne.add("Bank Rate");
                        alcolonne.add("Amount");
                        alcolonne.add("Bank Rate");
                        alcolonne.add("Bank Total");
                        alcolonne.add("Spread");
                        alcolonne.add("%");
                        pdf.setDati(dati);
                    }
                    if (!pos) {
                        if (newpread) {
                            base64 = TOBANK_pdf(pathtemp, pdf, formatStringtoStringDate(et.getDt_it(), patternsqldate, patternnormdate));
                        } else {
                            base64 = bsi.receipt(pathtemp, pdf, alcolonne, formatStringtoStringDate(et.getDt_it(), patternsqldate, patternnormdate));
                        }
                    } else {
                        if (newpread) {
                            base64 = TOBANK_POS_pdf(pathtemp, pdf, formatStringtoStringDate(et.getDt_it(), patternsqldate, patternnormdate));
                        } else {
                            base64 = bsi2.receipt(pathtemp, pdf, alcolonne, formatStringtoStringDate(et.getDt_it(), patternsqldate, patternnormdate));
                        }
                    }
                    if (et.getFg_annullato().equals("1")) {
                        base64 = printdeleted(pathtemp, decodeBase64(base64), false, false);
                    }
                }
            } else {
                ArrayList<FromBranchingSheet_value> dati1 = db.list_FromBranchingSheet_value(et);
                String note_fromBranch = db.note_fromBranch(et.getCod_in());
                if (dati1.size() > 0) {
                    if (newpread) {
                        ArrayList<ToBankingSheet_value> dati = db.list_ToBankingSheet_value(et);
                        ToBankingSheet_value pdf = new ToBankingSheet_value();
                        pdf.setAuto(et.getAuto());
                        pdf.setId_filiale(et.getFiliale());
                        pdf.setDe_filiale(formatBankBranchReport(et.getFiliale(), "BR", null, allfill));
                        pdf.setFromsafe(et.getFiliale() + " - " + formatBankBranchReport(et.getFiliale(), "BR", null, allfill));
                        pdf.setTobank(et.getCod_dest() + " - " + ba);
                        pdf.setPinuser(et.getUser());
                        pdf.setNumtranfer(et.getId());
                        pdf.setNote(et.getNote());
                        pdf.setDati(dati);
                        base64 = FROMBANK_pdf(pathtemp, pdf, formatStringtoStringDate(et.getDt_it(), patternsqldate, patternnormdate));
                    } else {
                        FromBankingSheet bsi = new FromBankingSheet();
                        FromBranchingSheet_value pdf = new FromBranchingSheet_value();
                        ArrayList<String> alcolonne = new ArrayList<>();
                        alcolonne.add("Currency");
                        alcolonne.add("Amount");
                        alcolonne.add("Bank Rate");
                        alcolonne.add("Amount");
                        alcolonne.add("Bank Rate");
                        alcolonne.add("Bank Total");
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
                        base64 = Utility.sanitize(bsi.receipt(pathtemp, pdf, alcolonne, formatStringtoStringDate(et.getDt_it(), patternsqldate, patternnormdate)));
                    }
                    if (et.getFg_annullato().equals("1")) {
                        base64 = Utility.sanitize(printdeleted(pathtemp, decodeBase64(base64), false, false));
                    }
                }
            }
        }

        db.closeDB();

        if (base64 != null) {
            String headerKey = "Content-Disposition";
            String headerValue = format("attachment; filename=\"%s\"", new Object[]{namesheet + new DateTime().toString("ddMMyyyy") + ".pdf"});
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
    protected void viewET_company_attach(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String cod = request.getParameter("cod");
        Db_Master db = new Db_Master();
        String base64 = Utility.sanitize(db.view_Company_attach(cod));
        db.closeDB();
        insertTR("I", (String) request.getSession().getAttribute("us_cod"), (String) request.getSession().getAttribute("us_fil") + " - VIEW COMPANY ATTACH " + cod);
        if (base64 != null) {
            String headerKey = "Content-Disposition";
            String sanitizedcod = Utility.sanitize(cod);
            String headerValue = format("attachments; filename=\"%s\"", new Object[]{sanitizedcod + ".pdf"});
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
    protected void view_reprint_nctr(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String cod = Utility.sanitize(request.getParameter("cod"));
        String view = Utility.sanitize(request.getParameter("view"));
        boolean ins = false;
        if (view == null) {
        } else {
            if (view.equals("print")) {
                ins = true;
            }
        }

        NC_transaction nc = get_NC_transaction(cod);
        insertTR("I", (String) request.getSession().getAttribute("us_cod"), (String) request.getSession().getAttribute("us_fil") + " - VIEW REPRINT NO CHANGE TRANS " + cod);
        if (nc != null) {

            Db_Master db = new Db_Master();
            if (ins) {
                db.insert_reprint(cod, "NC", (String) request.getSession().getAttribute("us_cod"));
            }

            //tax free verifico se è stato firmato
            Ch_transaction_doc firmato = db.get_doc_nc_firmato(cod, "_macnctaxf");
            String base64;
            if (firmato != null) {
                base64 = firmato.getContent();
                if (base64.startsWith("FILE[")) {
                    String pa1 = replace(base64, "FILE[", "");
                    File f = new File(Utility.sanitize(pa1));
                    base64 = Utility.sanitize(encodeBase64String(readFileToByteArray(f)));
                }
            } else {
                if (nc.getFg_tipo_transazione_nc().equals("1")
                        || nc.getFg_tipo_transazione_nc().equals("2")
                        || nc.getFg_tipo_transazione_nc().equals("4")
                        || nc.getFg_tipo_transazione_nc().equals("6")
                        || nc.getFg_tipo_transazione_nc().equals("7")) {
                    base64 = new Pdf().receipt_nochange_norm(nc);
                } else {
                    base64 = new Pdf().receipt_nochange_anag(nc);
                }
            }

            if (base64 != null) {
                String headerKey = "Content-Disposition";
                String sanitizedcod = Utility.sanitize(cod);
                String headerValue = format("attachments; filename=\"%s\"", new Object[]{sanitizedcod + ".pdf"});
                response.setHeader(headerKey, headerValue);
                try (OutputStream outStream = response.getOutputStream()) {
                    outStream.write(decodeBase64(base64));
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
    protected void view_list_cod(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String cod = request.getParameter("cod");
        String doc = request.getParameter("doc");
        String ext = "";
        if (doc.equals("pdf")) {
            ext = ".pdf";
        } else if (doc.equals("excel")) {
            ext = ".xlsx";
        }
        insertTR("I", (String) request.getSession().getAttribute("us_cod"), (String) request.getSession().getAttribute("us_fil") + " - VIEW FILE " + ext + ": " + cod);
        if (ext.equals("")) {
            redirect(request, response, "page_fnf.html");
        } else {
            Db_Master db = new Db_Master();
            String base64 = Utility.sanitize(db.view_file_codici_sblocco(cod, doc));
            db.closeDB();
            if (base64 != null) {
                String headerKey = "Content-Disposition";
                String sanitizedcod = Utility.sanitize(cod);
                String sanitizedext = Utility.sanitize(ext);
                String headerValue = format("attachments; filename=\"%s\"", new Object[]{sanitizedcod + sanitizedext});
                response.setHeader(headerKey, headerValue);
                try (OutputStream outStream = response.getOutputStream()) {
                    outStream.write(decodeBase64(base64.getBytes()));
                }
            } else {
                redirect(request, response, "page_fnf.html");
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
    protected void viewNewsLetters(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String cod = request.getParameter("cod");
        String user = request.getParameter("user");
        Db_Master db = new Db_Master();
        insertTR("I", (String) request.getSession().getAttribute("us_cod"), (String) request.getSession().getAttribute("us_fil") + " - VIEW NEWSLETTER FILE " + cod);
        Newsletters nl = db.get_newsletters(Utility.sanitize(cod), Utility.sanitize(user));
        if (nl != null) {
            boolean st = true;
            if (nl.getStatus().equals("U")) {
                DateTime now = new DateTime();
                String upl = now.toString("yyyy-MM-dd HH:mm:ss");
                st = db.update_newsletters(Utility.sanitize(cod), Utility.sanitize(user), "R", Utility.sanitize(upl));
            }
            db.closeDB();
            if (st) {
                String headerKey = "Content-Disposition";
                String sanitizedcod = Utility.sanitize(cod);
                String headerValue = format("attachments; filename=\"%s\"", new Object[]{sanitizedcod + ".pdf"});
                response.setHeader(headerKey, headerValue);
                try (OutputStream outStream = response.getOutputStream()) {
                    outStream.write(decodeBase64(nl.getFileout().getBytes()));
                }
            } else {
                redirect(request, response, "page_fnf.html");
            }
        } else {
            db.closeDB();
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
    protected void view_doc_tr_reprint(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        //Utility.printRequest(request);
        String user = (String) request.getSession().getAttribute("us_cod");
        if (user == null) {
            user = "9999";
        }

        String tr = Utility.sanitize(request.getParameter("tr"));
        String cod = Utility.sanitize(request.getParameter("cod"));
        Db_Master db = new Db_Master();
        String pathtemp = Utility.sanitize(db.getPath("temp"));
        ArrayList<Document> docall = db.list_typedoc_tra("1");
        Ch_transaction tra = db.query_transaction_ch(tr);
        if (tra == null) {
            tra = db.query_transaction_ch_temp(tr);
        }
        Ch_transaction_doc chd = db.get_tr_doc(cod);
        String base64 = null;
        String name = null;

        String content = "application/pdf";
        String display = "inline";

        insertTR("I", (String) request.getSession().getAttribute("us_cod"),
                (String) request.getSession().getAttribute("us_fil") + " - VIEW REPRINT TR DOC " + cod);
        if (chd != null) {
            if (chd.getOnline().equals("Y") || chd.getTipodoc().equals("_docrico")) {
                base64 = getBase64(Utility.sanitize(db.getPath("temp")), Utility.sanitize(chd.getContent()),tra, Utility.sanitize(chd.getTipodoc()));
            } else {
                if (is_CZ && chd.getTipodoc().equals("_macrefund")) {
                    ArrayList<Ch_transaction_value> tr_value = db.query_transaction_value(tr);
                    Ch_transaction_refund ref = db.get_refund_trans(tr);
                    Branch br = db.get_branch(tra.getFiliale());
                    Branch br2 = db.get_branch(ref.getBranch_cod());
                    base64 = refund_CZ(pathtemp, ref.getType().equals("CO"),
                            ref.getValue(), tra.getTipotr(),
                            tr_value.get(0).getNet(),
                            tr_value.get(0).getQuantita(),
                            tr_value.get(0).getValuta(),
                            tra.getId(),
                            formatStringtoStringDate(tra.getData(), patternsqldate, patternnormdate_f),
                            br.getCod() + " " + (br.getAdd_via() + ", " + br.getAdd_cap() + ", " + br.getAdd_city()).toUpperCase(),
                            br2.getCod() + " " + (br2.getAdd_via() + ", " + br2.getAdd_cap() + ", " + br2.getAdd_city()).toUpperCase(),
                            formatStringtoStringDate(ref.getDt_refund(), patternsqldate, patternnormdate_f));
                }

                for (int x = 0; x < docall.size(); x++) {

                    if (docall.get(x).getCodice().equals(chd.getTipodoc())) {

                        if (is_UK && chd.getTipodoc().equalsIgnoreCase("_receipt")) {
                            ArrayList<Figures> fig = db.list_all_figures();
                            Branch bra = db.get_branch(tra.getFiliale());
                            ArrayList<Ch_transaction_value> li = db.query_transaction_value(tra.getCod());
                            Client cl = db.query_Client_transaction(tra.getCod(), tra.getCl_cod());
                            Office ma = db.get_national_office();
                            base64 = new NewReceipt_UK().ricevuta_UK(pathtemp, ma, tra, cl, li, bra, fig);
                        } else if (docall.get(x).getTypesign().equals("billextrasee") || docall.get(x).getTypesign().equals("billextraseebollo")) {
                            ArrayList<Ch_transaction_value> li = db.query_transaction_value(tra.getCod());
                            Client cl = db.query_Client_transaction(tra.getCod(), tra.getCl_cod());
                            Office ma = db.get_national_office();
                            Branch br = db.get_branch(tra.getFiliale());
                            ArrayList<Figures> fig = db.list_all_figures();
                            ArrayList<Currency> cur = db.list_figures_query_edit(null);
                            CustomerKind ck = db.get_customerKind(tra.getTipocliente());
                            VATcode va  = db.get_vat_cod(ck.getVatcode());
                            base64 = new Receipt().print_bill_extrasee_bollo(pathtemp, ma, tra, cl, li, br, fig, cur, va, ck, true);

                        } else if (docall.get(x).getTypesign().equals("receiptcommnotzero")) {

                            ArrayList<Ch_transaction_value> li = db.query_transaction_value(tra.getCod());
                            Client cl = db.query_Client_transaction(tra.getCod(), tra.getCl_cod());
                            Office ma = db.get_national_office();
                            Branch br = db.get_branch(tra.getFiliale());
                            ArrayList<Figures> fig = db.list_all_figures();
                            ArrayList<Currency> cur = db.list_figures_query_edit(null);
                            base64 = new Receipt().print_receipt_comm_not_zero(pathtemp, ma, tra, cl, li, br, fig, cur, true);

                        } else if (docall.get(x).getTypesign().equals("receiptrefund")) {

                            String localfig = db.get_local_currency()[0];
                            Client cl = db.query_Client_transaction(tra.getCod(), tra.getCl_cod());
                            ArrayList<Branch> brl = db.list_branch();
                            ArrayList<Ch_transaction_value> tr_value = db.query_transaction_value(tra.getCod());
                            Ch_transaction_refund ref = db.get_refund_trans(tra.getCod());

                            String importo = "";
                            if (ref.getType().equals("CO")) {
                                for (int i = 0; i < tr_value.size(); i++) {
                                    importo = importo + formatMysqltoDisplay(tr_value.get(i).getQuantita()) + " " + tr_value.get(i).getValuta() + ", ";
                                }
                                if (importo.length() > 0) {
                                    importo = importo.trim();
                                    importo = importo.substring(0, importo.length() - 1);
                                }
                            } else if (ref.getType().equals("PA")) {
                                importo = Utility.sanitize(formatMysqltoDisplay(tra.getPay()) + " " + localfig);
                            }

                            String model = db.getConf("path.refund.pdf");

                            base64 = refund(pathtemp,
                                    decodeBase64(model),
                                    cl.getCognome() + " " + cl.getNome(),
                                    importo,
                                    tra.getFiliale() + tra.getId(),
                                    formatStringtoStringDate(tra.getData(), patternsqldate, patternnormdate),
                                    formatBankBranchReport(tra.getFiliale(), "BR", null, brl),
                                    new DateTime().toString(patternnormdate_filter));

                        } else if (docall.get(x).getTypesign().equals("receiptsee")) {
                            ArrayList<Ch_transaction_value> li = db.query_transaction_value(tra.getCod());
                            Client cl = db.query_Client_transaction(tra.getCod(), tra.getCl_cod());
                            Office ma = db.get_national_office();
                            Branch br = db.get_branch(tra.getFiliale());
                            ArrayList<Figures> fig = db.list_all_figures();
                            ArrayList<Currency> cur = db.list_figures_query_edit(null);
                            base64 = Utility.sanitize(new Receipt().print_receipt_see(pathtemp, ma, tra, cl, li, br, fig, cur, true));
                        } else if (docall.get(x).getTypesign().equals("receiptchangeIT")) {
//                            ArrayList<Ch_transaction_value> li = db.query_transaction_value(tra.getCod());
//                            Client cl = db.query_Client_transaction(tra.getCod(),tra.getCl_cod());
//                            Office ma = db.get_national_office();
//                            Branch br = db.get_branch(tra.getFiliale());
//                            ArrayList<Figures> fig = db.list_all_figures();
//                            ArrayList<Currency> cur = db.list_figures_query_edit(null);
//                            base64 = new Receipt().print_receipt_change_IT(pathtemp, ma, tra, cl, li, br, fig, cur);
                        } else if (docall.get(x).getTypesign().equals("creditnote")) {

                            ArrayList<Ch_transaction_value> li = db.query_transaction_value(tra.getCod());
                            Client cl = db.query_Client_transaction(tra.getCod(), tra.getCl_cod());
                            Office ma = db.get_national_office();
                            Branch br = db.get_branch(tra.getFiliale());
                            ArrayList<Figures> fig = db.list_all_figures();
                            ArrayList<Currency> cur = db.list_figures_query_edit(null);
                            CustomerKind ck = db.get_customerKind(tra.getTipocliente());
                            VATcode va  = db.get_vat_cod(ck.getVatcode());
                            base64 = Utility.sanitize(new Receipt().print_credit_note(pathtemp, ma, tra, cl, li, br, fig, cur, va, ck,
                                    tra.getFiliale() + tra.getId(), tra.getPay(), true, tra.getPay(), tra.getPay(), true, true));
                        } else if (docall.get(x).getTypesign().equals("autoc")) {
                            Client cl = db.query_Client_transaction(tra.getCod(), tra.getCl_cod());
                            CustomerKind ck = db.get_customerKind(tra.getTipocliente());
                            String dt_tr = formatStringtoStringDate(tra.getData(), patternsqldate, patternnormdate_filter);
                            base64 = Utility.sanitize(new Pdf().print_autocert(pathtemp, cl, dt_tr, tra, ck));
                        } else if (docall.get(x).getTypesign().equals("-")) {

                            ArrayList<Ch_transaction_value> li = db.query_transaction_value(tra.getCod());
                            Client cl = db.query_Client_transaction(tra.getCod(), tra.getCl_cod());
                            Office ma = db.get_national_office();
                            Branch br = db.get_branch(tra.getFiliale());
                            String[] pr_va = db.get_mod_profcl(tra.getCl_cod(), tra.getCod());
                            if (pr_va != null) {
                                String iss = pr_va[0];
                                String cust = pr_va[1];
                                String cust_det = pr_va[2];
                                String ann = pr_va[3];
                                String main1 = pr_va[4];
                                String main1_val = pr_va[5];
                                String main2 = pr_va[6];
                                String main2_val = pr_va[7];
                                String main3 = pr_va[8];
                                String main3_val = pr_va[9];
                                base64 = Utility.sanitize(modulo_profilatura(pathtemp, ma, tra, cl, li, br, iss, cust, cust_det, ann, main1, main1_val, main2, main2_val, main3, main3_val));
                            }
                        }
                    }
                }
            }

            name = chd.getNomefile();
            if (name.endsWith(".txt")) {
                content = "text/plain";
                display = "attachment";
            }

            if (chd.getTipodoc().equalsIgnoreCase("_macrecsee")
                    || chd.getTipodoc().equalsIgnoreCase("_macfaexsee")
                    || chd.getTipodoc().equalsIgnoreCase("_macfaexseebo")) {
                db.insert_reprint(tr, "CH", user);
            }
        }

        db.closeDB();

        if (base64 != null) {
            String headerKey = "Content-Disposition";
            String sanitizedname = Utility.sanitize(name);
            String sanitizedcontent= Utility.sanitize(content);
            String headerValue = format(display + "; filename=\"%s\"", new Object[]{sanitizedname});
            response.setHeader("Content-Type", sanitizedcontent);
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
    protected void view_doc_tr(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String cod = Utility.sanitize(request.getParameter("cod"));
        Db_Master db = new Db_Master();
        Ch_transaction_doc chd = db.get_tr_doc(cod);
        String base64 = null;
        String name = null;
        db.closeDB();

        String content = "application/pdf";
        String display = "inline";

        if (chd != null) {
            base64 = Utility.sanitize(chd.getContent());
            name = Utility.sanitize(chd.getNomefile());

            if (name.endsWith(".txt")) {
                content = "text/plain";
                display = "attachment";
            }

            if (base64.startsWith("FILE[")) {
                String pa1 = replace(base64, "FILE[", "");
                File f = new File(Utility.sanitize(pa1));
                base64 = encodeBase64String(readFileToByteArray(f));
            }
        }

        insertTR("I", (String) request.getSession().getAttribute("us_cod"), (String) request.getSession().getAttribute("us_fil") + " - VIEW DOCTR FILE " + cod);

        if (base64 != null) {
            String headerKey = "Content-Disposition";
            String sanitizedname = Utility.sanitize(name);
            String sanitizedcontent= Utility.sanitize(content);
            String headerValue = format(display + "; filename=\"%s\"", new Object[]{sanitizedname});
            response.setHeader("Content-Type", sanitizedcontent);
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
    protected void view_doc_tr_attachments(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String cod = Utility.sanitize(request.getParameter("cod"));
        Db_Master db = new Db_Master();
        Ch_transaction_doc chd = db.get_tr_doc(cod);
        String base64 = null;
        String name = null;
        db.closeDB();
        if (chd != null) {
            base64 = chd.getContent();
            name = chd.getNomefile();
            if (base64.startsWith("FILE[")) {
                String pa1 = replace(base64, "FILE[", "");
                File f = new File(Utility.sanitize(pa1));
                base64 = Utility.sanitize(encodeBase64String(readFileToByteArray(f)));
            }

        }
        insertTR("I", (String) request.getSession().getAttribute("us_cod"), (String) request.getSession().getAttribute("us_fil") + " - VIEW PDF FILEname " + name);
        if (base64 != null) {
            String headerKey = "Content-Disposition";
            String sanitizedname = Utility.sanitize(name);
            String headerValue = format("attachments; filename=\"%s\"", new Object[]{sanitizedname});
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
    protected void reprint_doc_tr(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String cod = Utility.sanitize(request.getParameter("cod"));
        Db_Master db = new Db_Master();
        Ch_transaction_doc chd = db.get_tr_doc(cod);
        String base64 = null;
        String name = null;
        db.closeDB();

        if (chd != null) {
            base64 = chd.getContent();
            name = chd.getNomefile();
            if (base64.startsWith("FILE[")) {
                String pa1 = replace(base64, "FILE[", "");
                File f = new File(Utility.sanitize(pa1));
                base64 = encodeBase64String(readFileToByteArray(f));
            }
        } else {
            Db_Master db1 = new Db_Master(true);
            chd = db1.get_tr_doc(cod);
            db1.closeDB();
            if (chd != null) {
                base64 = chd.getContent();
                name = chd.getNomefile();
                if (base64.startsWith("FILE[")) {
                    String pa1 = replace(base64, "FILE[", "");
                    File f = new File(Utility.sanitize(pa1));
                    base64 = Utility.sanitize(encodeBase64String(readFileToByteArray(f)));
                }
            }
        }

        insertTR("I", (String) request.getSession().getAttribute("us_cod"), (String) request.getSession().getAttribute("us_fil") + " - VIEW DOCTR FILEname " + name);
        if (base64 != null) {
            String headerKey = "Content-Disposition";
            String sanitizedname = Utility.sanitize(name);
            String headerValue = format("inline; filename=\"%s\"", new Object[]{sanitizedname});
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
            switch (type) {
                case "viewPdf":
                    viewPdf(request, response);
                    break;
                case "viewNewsLetters":
                    viewNewsLetters(request, response);
                    break;
                case "viewET_frbr_receipt":
                    viewET_frbr_receipt(request, response);
                    break;
                case "viewET_company_attach":
                    viewET_company_attach(request, response);
                    break;
                case "view_reprint_nctr":
                    view_reprint_nctr(request, response);
                    break;
                case "view_list_cod":
                    view_list_cod(request, response);
                    break;
                case "view_doc_tr":
                    view_doc_tr(request, response);
                    break;
                case "view_doc_tr_reprint":
                    view_doc_tr_reprint(request, response);
                    break;
                case "reprint_doc_tr":
                    reprint_doc_tr(request, response);
                    break;
                case "viewExcel":
                    viewExcel(request, response);
                    break;
                case "view_doc_tr_attachments":
                    view_doc_tr_attachments(request, response);
                    break;
                case "viewET_frba_receipt":
                    viewET_frba_receipt(request, response);
                    break;
                case "viewET_receipt":
                    viewET_receipt(request, response);
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


/* Location:              C:\Users\rcosco\Desktop\classes\!\servlets\Download.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */
