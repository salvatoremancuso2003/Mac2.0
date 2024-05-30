package rc.so.servlets;

import rc.so.db.Db_Master;
import rc.so.entity.Branch;
import rc.so.entity.Branchbudget;
import rc.so.entity.NC_category;
import rc.so.entity.NC_transaction;
import static rc.so.excel.BudgetRep.create;
import rc.so.excel.BudgetTill;
import rc.so.excel.CambioMTD_YTD;
import rc.so.excel.Colonna;
import rc.so.excel.DailyChange;
import static rc.so.excel.DailyChangeExcel.create_cdc;
import static rc.so.excel.DailyChangeExcel.create_uk_contabilita;
import rc.so.excel.DailyChange_CG;
import rc.so.excel.DatiWU_MTD_YTD;
import rc.so.excel.GM;
import static rc.so.excel.Giornaliero.giornalieroCG;
import rc.so.excel.LimitInsur;
import static rc.so.excel.Massimali.create;
import rc.so.excel.NC_val;
import rc.so.excel.Riga;
import rc.so.report.DailyBank;
import rc.so.report.DailyKind;
import rc.so.report.Daily_value;
import static rc.so.util.Constant.patternnormdate_filter;
import static rc.so.util.Constant.patternsql;
import static rc.so.util.Engine.all_nc_category;
import static rc.so.util.Engine.getNC_category;
import static rc.so.util.Engine.get_Branch;
import static rc.so.util.Engine.insertTR;
import static rc.so.util.Engine.unlockratejustify;
import static rc.so.util.Engine.verifySession;
import static rc.so.util.Utility.descr_for_report;
import static rc.so.util.Utility.divisione_controllozero;
import static rc.so.util.Utility.fd;
import static rc.so.util.Utility.formatAL;
import static rc.so.util.Utility.formatArrayValues;
import static rc.so.util.Utility.formatMysqltoDisplay;
import static rc.so.util.Utility.formatStringtoStringDate_null;
import static rc.so.util.Utility.getGiornoAdeguatoAnnoPrecedente;
import static rc.so.util.Utility.parseDoubleR;
import static rc.so.util.Utility.parseIntR;
import static rc.so.util.Utility.redirect;
import static rc.so.util.Utility.removeDuplicatesAL;
import static rc.so.util.Utility.roundDoubleandFormat;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import static java.lang.String.format;
import static java.lang.String.valueOf;
import static java.lang.Thread.currentThread;
import java.util.ArrayList;
import static java.util.Collections.sort;
import java.util.HashMap;
import static java.util.Locale.ITALY;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import static org.apache.commons.codec.binary.Base64.decodeBase64;
import org.joda.time.DateTime;
import static org.joda.time.format.DateTimeFormat.forPattern;
import org.joda.time.format.DateTimeFormatter;
import static rc.so.excel.DailyChangeExcel.create_changeacc1;
import rc.so.util.Utility;

/**
 *
 * @author rcosco
 */
public class Gestione extends HttpServlet {

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void change1(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        Utility.printRequest(request);
//        if(true)
//            return;

        String deleted = request.getParameter("deleted");
        if (null == deleted) {
            deleted = "0";
        } else {
            switch (deleted) {
                case "on":
                    deleted = "1";
                    break;
                default:
                    deleted = "0";
                    break;
            }
        }

//        String branch = request.getParameter("branch");
        ArrayList<String> brList = new ArrayList<>();

        String[] branch2 = request.getParameterValues("branch");

        if (branch2 != null && !branch2[0].equals("null")) {
            brList = formatArrayValues(branch2);
        }

        String mese = request.getParameter("date2");

        String gr1 = request.getParameter("gr1");
        String[] gr2 = request.getParameterValues("gr2");
        ArrayList<String> gr02list = new ArrayList<>();
        if (gr2 != null) {
            gr02list = formatArrayValues(gr2);
        }

        ArrayList<String> filialidacontrollare = brList;

        if (filialidacontrollare.isEmpty()) {
            Db_Master db1 = new Db_Master();
            filialidacontrollare = db1.estraiFIlialidaGruppi(gr1, gr02list);
            db1.closeDB();
        }

        DateTimeFormatter formatter = forPattern(patternnormdate_filter);
        DateTime dtstart = formatter.parseDateTime("01/" + mese);
        DateTime dtlast = formatter.parseDateTime(dtstart.dayOfMonth().getMaximumValue() + "/" + mese);

        Db_Master db = new Db_Master();
        ArrayList<String[]> array_unlockrate = unlockratejustify();
        ArrayList<Branch> allbr = db.list_branch_completeAFTER311217();
        String data1 = dtstart.toString(patternsql);
        String data2 = dtlast.toString(patternsql);
        ArrayList<DailyChange_CG> output = new ArrayList<>();
        for (int i = 0; i < filialidacontrollare.size(); i++) {
            String filiale1 = filialidacontrollare.get(i);
            Branch b1 = get_Branch(filiale1, allbr);
            ArrayList<DailyChange_CG> f1 = db.list_DailyChange_CG(b1, data1, data2, deleted.equals("1"));
            output.addAll(f1);
        }
        String pathtemp = db.getPath("temp");
        db.closeDB();

        String base64 = Utility.sanitize(create_cdc(pathtemp, output, array_unlockrate));

        if (base64 != null) {
            String headerKey = "Content-Disposition";
            String sanitizedmese = Utility.sanitize(mese);
            String headerValue = format("attachment; filename=\"%s\"", new Object[]{"ControlloDiGestione_Report1_" + sanitizedmese + ".xlsx"});
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
    protected void change(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

//        Utility.printRequest(request);
//        String branch = request.getParameter("branch");
        ArrayList<String> brList = new ArrayList<>();

        String[] branch2 = request.getParameterValues("branch");

        if (branch2 != null && !branch2[0].equals("null")) {
            brList = formatArrayValues(branch2);
        }

        String mese = request.getParameter("date2");

        String gr1 = request.getParameter("gr1");
        //String gr2 = request.getParameter("gr2");

        String[] gr2 = request.getParameterValues("gr2");
        ArrayList<String> gr02list = new ArrayList<>();

        if (gr2 != null) {
            gr02list = formatArrayValues(gr2);
        }

        ArrayList<String> filialidacontrollare = brList;

        if (filialidacontrollare.isEmpty()) {
            Db_Master db1 = new Db_Master();
            filialidacontrollare = db1.estraiFIlialidaGruppi(gr1, gr02list);
            db1.closeDB();
        }
        DateTimeFormatter formatter = forPattern(patternnormdate_filter);
        DateTime dtstart = formatter.parseDateTime("01/" + mese);

        DateTime oggi = new DateTime();
        DateTime dtnow = oggi.minusDays(1);

        String monthstring = dtstart.monthOfYear().getAsText(ITALY).toUpperCase();
        ArrayList<String> giornidacontrollare = new ArrayList<>();
        giornidacontrollare.add(dtstart.toString(patternnormdate_filter));

        for (int i = 1; i < 31; i++) {
            DateTime dtcontrollo = dtstart.plusDays(i);
            if (dtcontrollo.monthOfYear().getAsText(ITALY).toUpperCase().equalsIgnoreCase(monthstring)) {
                giornidacontrollare.add(dtcontrollo.toString(patternnormdate_filter));

            } else {
                giornidacontrollare.add("");
            }
        }

        Db_Master db = new Db_Master();
        ArrayList<Branch> allbr = db.list_branch_completeAFTER311217();
        ArrayList<DailyChange> output = new ArrayList<>();
        for (int i = 0; i < filialidacontrollare.size(); i++) {

            String filiale1 = filialidacontrollare.get(i);
            Branch b1 = get_Branch(filiale1, allbr);
            String[] ff = {filiale1, b1.getDe_branch()};

            for (int v = 0; v < giornidacontrollare.size(); v++) {
                if (!giornidacontrollare.get(v).equals("")) {
                    if (formatter.parseDateTime(giornidacontrollare.get(v)).isBefore(dtnow)) {
                        String datadac = formatStringtoStringDate_null(giornidacontrollare.get(v), patternnormdate_filter, patternsql);

                        Daily_value dv = db.list_Daily_value(ff, datadac + " 00:00", datadac + " 23:59", false, false);

                        ArrayList<DailyKind> lik = dv.getDati();
                        for (int t = 0; t < lik.size(); t++) {
                            DailyKind wu1 = lik.get(t);
                            if (wu1.getKind().toLowerCase().contains("western")) {

                            }

                        }

                        DailyChange dc1 = new DailyChange();
                        dc1.setFiliale(ff[0]);
                        dc1.setDescr(ff[1]);
                        dc1.setData(giornidacontrollare.get(v));
                        dc1.setVOLUMEAC((dv.getPurchGrossTot()));
                        dc1.setVOLUMECA((dv.getCashAdGrossTot()));
                        dc1.setVOLUMETC("");//verificare
                        dc1.setTRANSTC("");//verificare
                        dc1.setCOMMTC("");//verificare
                        dc1.setTRANSAC((dv.getNoTransPurch()));
                        dc1.setTRANSCA((dv.getNoTransCC()));

//                        dv.getPurchSpread();
//                        dv.getCashAdSpread();
                        //OLD
                        //dc1.setCOMMAC((dv.getPurchProfit()));
                        //dc1.setCOMMCA((dv.getCashAdProfit()));
                        //NEW
                        dc1.setCOMMAC(dv.getPurchComm());
                        dc1.setSPREADAC(dv.getPurchSpread());

                        dc1.setCOMMCA(dv.getCashAdComm());
                        dc1.setSPREADCA(dv.getCashAdSpread());

                        dc1.setSPREADBR((dv.getBraSalesSpread()));
                        dc1.setSPREADBA((dv.getBaSalesSpread()));
                        dc1.setTOTTRANSACQ((roundDoubleandFormat(fd(dv.getNoTransPurch()) + fd(dv.getNoTransCC()), 0)));
                        double totvolacq = fd(dv.getPurchGrossTot()) + fd(dv.getCashAdGrossTot());
                        dc1.setTOTVOLACQ((roundDoubleandFormat(totvolacq, 2)));
                        double totgmacq = fd(dv.getBaSalesSpread()) + fd(dv.getBraSalesSpread()) + fd(dv.getCashAdProfit()) + fd(dv.getPurchProfit()) + fd("");//verificare
                        dc1.setTOTGMACQ((roundDoubleandFormat(totgmacq, 2)));

                        dc1.setPERCACQ((roundDoubleandFormat(100.00 * divisione_controllozero(totgmacq, totvolacq), 2)));

                        double volonl = 0.00;
                        ArrayList<DailyBank> lib = dv.getDatiBank();
                        int ntron = 0;
                        for (int o = 0; o < lib.size(); o++) {
                            volonl += fd(lib.get(o).getAmount());
                            ntron += parseIntR(lib.get(o).getNtrans());
                        }

                        dc1.setVOLUMEVENDOFF((roundDoubleandFormat(fd(dv.getSalesGrossTot()) - volonl, 2)));
                        dc1.setVOLUMEONL((roundDoubleandFormat(volonl, 2)));

                        dc1.setVOLUMERIVA("");//verificare
                        dc1.setTRANSVENDOFF((valueOf(parseIntR(dv.getNoTransSales()) - ntron)));
                        dc1.setTRANSONL((valueOf(ntron)));
                        dc1.setTRANSRIVA("");//verificare
                        dc1.setCOMMVENDOFF((dv.getSalesComm()));
                        dc1.setSPREADVEND((dv.getSalesSpread()));
                        dc1.setCOMMONL("");//verificare
                        dc1.setCOMMRIVA("");//verificare
                        dc1.setTOTVOLVEN((dv.getSalesGrossTot()));
                        dc1.setTOTTRANSVEN((dv.getNoTransSales()));

                        double gmven = fd(dv.getSalesComm())
                                + fd(dv.getSalesSpread())
                                + fd("")
                                + fd("");//verificare
                        dc1.setTOTGMVEN((roundDoubleandFormat(gmven, 2)));

                        double percven = 0.00;
                        if (fd(dv.getSalesGrossTot()) > 0.00) {
                            percven = 100.00 * divisione_controllozero(gmven, fd(dv.getSalesGrossTot()));
                        }

                        dc1.setPERCVEN((roundDoubleandFormat(percven, 2)));
                        double totvol = fd(dv.getSalesGrossTot()) + fd(dv.getPurchGrossTot()) + fd(dv.getCashAdGrossTot());
                        dc1.setTOTVOL((roundDoubleandFormat(totvol, 2)));

                        dc1.setTOTTRANS((roundDoubleandFormat(fd(dv.getNoTransSales()) + fd(dv.getNoTransPurch()) + fd(dv.getNoTransCC()), 0)));
                        dc1.setTOTGM((dv.getGrossProfit()));

                        double percvend = 0.00;
                        if (totvol > 0) {
                            percvend = 100.00 * divisione_controllozero(fd(dv.getGrossProfit()), totvol);
                        }

                        dc1.setPERCVEND((roundDoubleandFormat(percvend, 2)));
                        dc1.setCOP((dv.getCashOnPrem()));
                        dc1.setTOBANKCOP((dv.getBaSalesTransfNotes()));
                        dc1.setFRBANKCOP((dv.getBaPurchTransfNotes()));
                        dc1.setTOBRCOP((dv.getBraSalesLocalCurr()));
                        dc1.setFRBRCOP((dv.getBraPurchLocalCurr()));
                        dc1.setOCERRCOP((dv.getCashOnPremError()));
                        dc1.setFX((dv.getFx()));
                        dc1.setTOBANKFX((dv.getBaSalesTotal()));
                        dc1.setFRBANKFX((dv.getBaPurchTotal()));
                        dc1.setTOBRFX((dv.getBraSalesTotal()));
                        dc1.setFRBRFX((dv.getBraPurchTotal()));
                        dc1.setOCERRFX((dv.getFxClosureErrorDeclared()));
                        output.add(dc1);
                    }
                }
            }
        }
        String pathtemp = db.getPath("temp");
        db.closeDB();

        String base64 = create_changeacc1(pathtemp, output);

        if (base64 != null) {
            String headerKey = "Content-Disposition";
            String sanitizedmese = Utility.sanitize(mese);
            String headerValue = format("attachment; filename=\"%s\"", new Object[]{"Contabilità_Report1_" + sanitizedmese + ".xlsx"});
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
    protected void massimale(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

//        String branch = request.getParameter("branch");
        ArrayList<String> brList = new ArrayList<>();

        String[] branch2 = request.getParameterValues("branch");

        if (branch2 != null && !branch2[0].equals("null")) {
            brList = formatArrayValues(branch2);
        }

        String mese = request.getParameter("date2");

        String gr1 = request.getParameter("gr1");
        //String gr2 = request.getParameter("gr2");

        String[] gr2 = request.getParameterValues("gr2");
        ArrayList<String> gr02list = new ArrayList<>();

        if (gr2 != null) {
            gr02list = formatArrayValues(gr2);
        }

        DateTimeFormatter formatter = forPattern(patternnormdate_filter);
        DateTime dtstart = formatter.parseDateTime("01/" + mese);

        DateTime oggi = new DateTime();
        DateTime dtnow = oggi.minusDays(1);
        ArrayList<String> giornidacontrollare = new ArrayList<>();
        ArrayList<String> giornidacontrollarestring = new ArrayList<>();

        giornidacontrollare.add(dtstart.toString(patternnormdate_filter));
        giornidacontrollarestring.add(dtstart.dayOfWeek().getAsShortText(ITALY));

        for (int i = 1; i < 31; i++) {
            DateTime dtcontrollo = dtstart.plusDays(i);
            if (dtcontrollo.isBefore(dtnow)) {
                giornidacontrollare.add(dtcontrollo.toString(patternnormdate_filter));
                giornidacontrollarestring.add(dtcontrollo.dayOfWeek().getAsShortText(ITALY));
            }
        }

        Db_Master db = new Db_Master();
        ArrayList<Branch> allbr = db.list_branch_completeAFTER311217();
        ArrayList<String> filialidacontrollare = brList;
        if (filialidacontrollare.isEmpty()) {
            filialidacontrollare = db.estraiFIlialidaGruppi(gr1, gr02list);
        }
        ArrayList<LimitInsur> li = new ArrayList<>();
        ArrayList<Branch> fil1 = new ArrayList<>();
        for (int i = 0; i < filialidacontrollare.size(); i++) {
            String filiale1 = filialidacontrollare.get(i);
            Branch b1 = get_Branch(filiale1, allbr);
            fil1.add(b1);
            String[] ff = {filiale1, b1.getDe_branch()};
            double maxfil = fd(b1.getMax_ass());
            for (int v = 0; v < giornidacontrollare.size(); v++) {
                String datadac = formatStringtoStringDate_null(giornidacontrollare.get(v), patternnormdate_filter, patternsql);
                Daily_value dv = db.list_Daily_value(ff, datadac + " 00:00", datadac + " 23:59", false, true);
                double totalgtot = fd(dv.getCashOnPrem()) + fd(dv.getFx());
                double delta = maxfil - totalgtot;
                LimitInsur l = new LimitInsur(filiale1,
                        giornidacontrollare.get(v), giornidacontrollarestring.get(v),
                        dv.getCashOnPrem(), dv.getFx(), roundDoubleandFormat(totalgtot, 2), roundDoubleandFormat(delta, 2));
//                out.println("rc.so.servlets.Gestione.massimale() "+l.toString());
                li.add(l);
            }

        }

        String pathtemp = db.getPath("temp");
        db.closeDB();

        ArrayList<String> primatabella = new ArrayList<>();
        ArrayList<String> secondatabella = new ArrayList<>();
        ArrayList<String> terzatabella = new ArrayList<>();
        ArrayList<String> quartatabella = new ArrayList<>();

//        primatabella.add("COP\nMassimale");
        primatabella.add("COP");
//        primatabella.add("");

//        secondatabella.add("FX\nMassimale");
        secondatabella.add("FX");
//        secondatabella.add("");

        terzatabella.add("TOTALE");
//        terzatabella.add("TOTALE\nMassimale");
//        terzatabella.add("");

        quartatabella.add("DELTA CASSA\nVS\nMASSIMALI");
//        quartatabella.add("");

        for (int i = 0; i < fil1.size(); i++) {
            Branch b1 = fil1.get(i);
            primatabella.add(b1.getCod());
            primatabella.add(b1.getDe_branch());
            primatabella.add(b1.getMax_ass());
            secondatabella.add(b1.getCod());
            secondatabella.add(b1.getDe_branch());
            secondatabella.add(b1.getMax_ass());
            terzatabella.add(b1.getCod());
            terzatabella.add(b1.getDe_branch());
            terzatabella.add(b1.getMax_ass());
            quartatabella.add(b1.getCod());
            quartatabella.add(b1.getDe_branch());
            quartatabella.add(b1.getMax_ass());

//            primatabella.add(b1.getCod() + "\n" + b1.getDe_branch() + "\n" + formatMysqltoDisplay(b1.getMax_ass()));
//            secondatabella.add(b1.getCod() + "\n" + b1.getDe_branch() + "\n" + formatMysqltoDisplay(b1.getMax_ass()));
//            terzatabella.add(b1.getCod() + "\n" + b1.getDe_branch() + "\n" + formatMysqltoDisplay(b1.getMax_ass()));
//            quartatabella.add(b1.getCod() + "\n" + b1.getDe_branch() + "\n" + formatMysqltoDisplay(b1.getMax_ass()));
        }

        String base64 = Utility.sanitize(create(pathtemp, primatabella, secondatabella, terzatabella, quartatabella,
                li, fil1, giornidacontrollare, giornidacontrollarestring));

        if (base64 != null) {
            String headerKey = "Content-Disposition";
            String sanitizedmese = Utility.sanitize(mese);
            String headerValue = format("attachment; filename=\"%s\"", new Object[]{"Controllodigestione_Massimali_" + sanitizedmese + ".xlsx"});
            response.setHeader(headerKey, headerValue);
            try (OutputStream outStream = response.getOutputStream()) {
                outStream.write(decodeBase64(base64.getBytes()));
            }
        } else {
            redirect(request, response, "page_404.html");
        }

        //redirect(request, response, "page_404.html");
    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void budget(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        ArrayList<String> brList = new ArrayList<>();

        String[] branch2 = request.getParameterValues("branch");

        if (branch2 != null && !branch2[0].equals("null")) {
            brList = formatArrayValues(branch2);
        }

        String mese = request.getParameter("date2");

        String gr1 = request.getParameter("gr1");
        //String gr2 = request.getParameter("gr2");

        String[] gr2 = request.getParameterValues("gr2");
        ArrayList<String> gr02list = new ArrayList<>();

        if (gr2 != null) {
            gr02list = formatArrayValues(gr2);
        }

        DateTimeFormatter formatter = forPattern(patternnormdate_filter);
        DateTime dtstart = formatter.parseDateTime("01/" + mese);
        String yearstring_prev = dtstart.minusYears(1).year().getAsText(ITALY).toUpperCase();
        String mesemysql = dtstart.toString("yyyy-MM");

        DateTime oggi = new DateTime();
        DateTime dtnow = oggi.minusDays(1);

        ArrayList<String> giornidacontrollare = new ArrayList<>();
        ArrayList<String> giornidacontrollarestring = new ArrayList<>();

        giornidacontrollare.add(dtstart.toString(patternnormdate_filter));
        giornidacontrollarestring.add(dtstart.dayOfWeek().getAsShortText(ITALY));

        for (int i = 1; i < 31; i++) {
            DateTime dtcontrollo = dtstart.plusDays(i);
            if (dtcontrollo.isBefore(dtnow)) {
                giornidacontrollare.add(dtcontrollo.toString(patternnormdate_filter));
                giornidacontrollarestring.add(dtcontrollo.dayOfWeek().getAsShortText(ITALY));
            }
        }

        Db_Master db = new Db_Master();
        ArrayList<String[]> list_group = db.list_branch_group();
        ArrayList<Branch> allbr = db.list_branch_completeAFTER311217();
        ArrayList<String> filialidacontrollare = brList;
        if (filialidacontrollare.isEmpty()) {
            filialidacontrollare = db.estraiFIlialidaGruppi(gr1, gr02list);
        }
        ArrayList<BudgetTill> li = new ArrayList<>();
        ArrayList<Branch> fil1 = new ArrayList<>();

        ArrayList<String> gruppidascrivere = new ArrayList<>();

        for (int i = 0; i < filialidacontrollare.size(); i++) {
            String filiale1 = filialidacontrollare.get(i);
            Branch b1 = get_Branch(filiale1, allbr);
            fil1.add(b1);
            gruppidascrivere.add(b1.getBrgr_01());
            String[] ff = {filiale1, b1.getDe_branch()};
            for (int v = 0; v < giornidacontrollare.size(); v++) {
                String datadac = formatStringtoStringDate_null(giornidacontrollare.get(v), patternnormdate_filter, patternsql);
                Daily_value dv = db.list_Daily_value(ff, datadac + " 00:00", datadac + " 23:59", false, true);
                double totalgtot = fd(dv.getCashOnPrem()) + fd(dv.getFx());
                Branchbudget bb1 = db.get_branch_budget(filiale1, mesemysql);
                String budget;
                if (bb1 == null) {
                    budget = "0.00";
                } else {
                    budget = bb1.getBudg1();
                }

                String giornoannoscorsoadeguato = getGiornoAdeguatoAnnoPrecedente(giornidacontrollare.get(v));
                String datadac_giornoannoscorsoadeguato = formatStringtoStringDate_null(giornoannoscorsoadeguato, patternnormdate_filter, patternsql);
                Daily_value dv_giornoannoscorsoadeguato = db.list_Daily_value(ff, datadac_giornoannoscorsoadeguato + " 00:00",
                        datadac_giornoannoscorsoadeguato + " 23:59", false, true);

                double totalgtot_ap = fd(dv_giornoannoscorsoadeguato.getCashOnPrem()) + fd(dv_giornoannoscorsoadeguato.getFx());

                BudgetTill bt = new BudgetTill(filiale1, giornidacontrollare.get(v),
                        giornidacontrollarestring.get(v),
                        dv.getCashOnPrem(), dv.getFx(),
                        roundDoubleandFormat(totalgtot, 2),
                        dv_giornoannoscorsoadeguato.getCashOnPrem(), dv_giornoannoscorsoadeguato.getFx(),
                        roundDoubleandFormat(totalgtot_ap, 2), budget, b1.getBrgr_01());

                li.add(bt);
            }

        }
        String pathtemp = db.getPath("temp");
        db.closeDB();

        removeDuplicatesAL(gruppidascrivere);
        sort(li);

        ArrayList<String> primatabella = new ArrayList<>();
        ArrayList<String> secondatabella = new ArrayList<>();
        ArrayList<String> terzatabella = new ArrayList<>();

        primatabella.add("Aree\nCOP");
        primatabella.add("");

        secondatabella.add("Aree\nFX");
        secondatabella.add("");

        terzatabella.add("TOTALE\nCasse");
        terzatabella.add("");

        for (int x = 0; x < gruppidascrivere.size(); x++) {
            String gr = gruppidascrivere.get(x);
            String descrizionearea = formatAL(gr, list_group, 1);

            for (int i = 0; i < fil1.size(); i++) {
                Branch b1 = fil1.get(i);
                if (b1.getBrgr_01().equals(gr)) {
                    primatabella.add(b1.getCod() + "\n" + b1.getDe_branch());
                    secondatabella.add(b1.getCod() + "\n" + b1.getDe_branch());
                    terzatabella.add(b1.getCod() + "\n" + b1.getDe_branch());
                }

            }
            primatabella.add("TOT\n" + descrizionearea);
            secondatabella.add("TOT\n" + descrizionearea);
            terzatabella.add("TOT\n" + descrizionearea);
            primatabella.add("TOT\n" + yearstring_prev);
            primatabella.add("DIFFERENZA\nVS " + yearstring_prev);
            secondatabella.add("TOT\n" + yearstring_prev);
            secondatabella.add("DIFFERENZA\nVS " + yearstring_prev);
            terzatabella.add("TOT\n" + yearstring_prev);
            terzatabella.add("DIFFERENZA\nVS " + yearstring_prev);
            terzatabella.add("BUDGET");
            terzatabella.add("DIFFERENZA\nVS BUDGET");

        }

        String base64 = Utility.sanitize(create(pathtemp, primatabella, secondatabella, terzatabella,
                li, fil1, giornidacontrollare, giornidacontrollarestring, gruppidascrivere, list_group));

        if (base64 != null) {
            String headerKey = "Content-Disposition";
            String sanitizedmese = Utility.sanitize(mese);
            String headerValue = format("attachment; filename=\"%s\"", new Object[]{"Controllodigestione_BudgetDiCassa_" + sanitizedmese + ".xlsx"});
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
    protected void citta(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        redirect(request, response, "page_404.html");
    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void redemption(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        redirect(request, response, "page_404.html");
    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void online(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        redirect(request, response, "page_404.html");
    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void fasceorarie(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        String[] fasce = request.getParameterValues("fasce");
//        ArrayList<String> f_li = new ArrayList<>();
//        if (fasce.length > 0) {
//            boolean all = false;
//            for (int y = 0; y < fasce.length; y++) {
//                if (fasce[y].equals("--")) {
//                    all = true;
//                    break;
//                } else {
//                    f_li.add(fasce[y]);
//                }
//            }
//
//            if (all) {
//                f_li = new ArrayList<>();
//                f_li.add("0001");
//                f_li.add("0102");
//                f_li.add("0203");
//                f_li.add("0304");
//                f_li.add("0405");
//                f_li.add("0506");
//                f_li.add("0607");
//                f_li.add("0708");
//                f_li.add("0809");
//                f_li.add("0910");
//                f_li.add("1011");
//                f_li.add("1112");
//                f_li.add("1213");
//                f_li.add("1314");
//                f_li.add("1415");
//                f_li.add("1516");
//                f_li.add("1617");
//                f_li.add("1718");
//                f_li.add("1819");
//                f_li.add("1920");
//                f_li.add("2021");
//                f_li.add("2122");
//                f_li.add("2223");
//                f_li.add("2324");
//            }
//        }
//
//        ArrayList<String> brList = new ArrayList<>();
//
//        String[] branch2 = request.getParameterValues("branch");
//
//        if (branch2 != null) {
//            brList = Utility.formatArrayValues(branch2);
//        }
//
//        String mese = request.getParameter("date2");
//
//        String gr1 = request.getParameter("gr1");
//
//        String[] gr2 = request.getParameterValues("gr2");
//        ArrayList<String> gr02list = new ArrayList<>();
//
//        if (gr2 != null) {
//            gr02list = Utility.formatArrayValues(gr2);
//        }
//
//        DateTimeFormatter formatter = DateTimeFormat.forPattern(patternnormdate_filter);
//        DateTime dtstart = formatter.parseDateTime("01/" + mese);
//
//        DateTime oggi = new DateTime();
//        DateTime dtnow = oggi.minusDays(1);
//
//        ArrayList<String> giornidacontrollare = new ArrayList<>();
////        ArrayList<String> giornidacontrollarestring = new ArrayList<>();
//
//        giornidacontrollare.add(dtstart.toString(patternnormdate_filter));
////        giornidacontrollarestring.add(dtstart.dayOfWeek().getAsShortText(Locale.ITALY));
//
//        for (int i = 1; i < 31; i++) {
//            DateTime dtcontrollo = dtstart.plusDays(i);
//            if (dtcontrollo.isBefore(dtnow)) {
//                giornidacontrollare.add(dtcontrollo.toString(patternnormdate_filter));
////                giornidacontrollarestring.add(dtcontrollo.dayOfWeek().getAsShortText(Locale.ITALY));
//            }
//        }
//
//        Db_Master db = new Db_Master();
//        ArrayList<Branch> allbr = db.list_branch_enabled();
//        ArrayList<String> filialidacontrollare = brList;
//        if (filialidacontrollare.isEmpty()) {
//            filialidacontrollare = db.estraiFIlialidaGruppi(gr1, gr02list);
//        }
////        ArrayList<Branch> fil1 = new ArrayList<>();
//
////        ArrayList<String[]> t1 = new ArrayList<>();
//
//        for (int i = 0; i < filialidacontrollare.size(); i++) {
//            String filiale1 = filialidacontrollare.get(i);
//            Branch b1 = Engine.get_Branch(filiale1, allbr);
//            fil1.add(b1);
//            String[] ff = {filiale1, b1.getDe_branch()};
//            for (int v = 0; v < giornidacontrollare.size(); v++) {
//                try {
//                    String datadac = formatStringtoStringDate_null(giornidacontrollare.get(v), patternnormdate_filter, patternsql);
//                    String sql = "SELECT * FROM ch_transaction tr1 WHERE tr1.del_fg='0' AND tr1.filiale = '" + filiale1 + "' ";
//                    sql = sql + "AND tr1.data >= '" + datadac + " 00:00:00' ";
//                    sql = sql + "AND tr1.data <= '" + datadac + " 23:59:59' ";
//                    sql = sql + " ORDER BY tr1.data";
//                    ResultSet rs = db.getC().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE).executeQuery(sql);
//                    while (rs.next()) {
//                        String[] val = {giornidacontrollare.get(v), filiale1, rs.getString("tr1.tipotr"),
//                            rs.getString("tr1.data").split(" ")[1], rs.getString("tr1.total"), rs.getString("tr1.pay"),
//                            rs.getString("tr1.commission"), rs.getString("tr1.round")};
//                        t1.add(val);
//                    }
//                    //sdasdasda
//                } catch (SQLException ex) {
//                    Engine.insertTR("E", "System", Thread.currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
//                }
//            }
//        }
////
////        for (int v = 0; v < giornidacontrollare.size(); v++) {
////            String gg = giornidacontrollare.get(v);
////
////            for (int y = 0; y < fil1.size(); y++) {
////
////                Branch b = fil1.get(y);
////
////                for (int c = 0; c < f_li.size(); c++) {
////                    String fascia = f_li.get(c);
////                    for (int i = 0; i < t1.size(); i++) {
////                        String[] val = t1.get(i);
////                        if (val[0].equals(gg) && b.getCod().equals(val[1])) {
////
////                        }
////
////                    }
////                }
////            }
////        }
//
//        db.closeDB();

        redirect(request, response, "page_404.html");
    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void operatore(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        redirect(request, response, "page_404.html");
    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void buyback(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

//        String prevyear = request.getParameter("prevyear");
//        if (prevyear == null) {
//            prevyear = "0";
//        } else if (prevyear.equals("on")) {
//            prevyear = "1";
//        } else {
//            prevyear = "0";
//        }
//
//        String brgr = request.getParameter("brgr");
//        if (brgr == null) {
//            brgr = "0";
//        } else if (brgr.equals("on")) {
//            brgr = "1";
//        } else {
//            brgr = "0";
//        }
//
////        String branch = request.getParameter("branch");
//        ArrayList<String> brList = new ArrayList<>();
//
//        String[] branch2 = request.getParameterValues("branch");
//
//        if (branch2 != null) {
//            brList = Utility.formatArrayValues(branch2);
//        }
//
//        String mese = request.getParameter("date2");
//
//        String gr1 = request.getParameter("gr1");
//        //String gr2 = request.getParameter("gr2");
//
//        String[] gr2 = request.getParameterValues("gr2");
//        ArrayList<String> gr02list = new ArrayList<>();
//
//        if (gr2 != null) {
//            gr02list = Utility.formatArrayValues(gr2);
//        }
//
//        DateTimeFormatter formatter = DateTimeFormat.forPattern(patternnormdate_filter);
//        DateTime dtstart = formatter.parseDateTime("01/" + mese);
//
//        DateTime oggi = new DateTime();
//        DateTime dtnow = oggi.minusDays(1);
    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void giornalieroMOD(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        Utility.printRequest(request);

        //ArrayList<String> brList = new ArrayList<>();

       // String[] branch2 = request.getParameterValues("branch");

        //if (branch2 != null && !branch2[0].equals("null")) {
            //brList = formatArrayValues(branch2);
       // }

       // String mese = request.getParameter("date2");

       // String gr1 = request.getParameter("gr1");
        //String gr2 = request.getParameter("gr2");

        //String[] gr2 = request.getParameterValues("gr2");
        //ArrayList<String> gr02list = new ArrayList<>();

       // if (gr2 != null) {
           // gr02list = formatArrayValues(gr2);
       // }

        //Db_Master db = new Db_Master();
        //String valutalocale = db.get_local_currency()[0];
        //boolean dividi = db.get_national_office().getChangetype().equals("/");
        //InputStream is = new ByteArrayInputStream(decodeBase64(db.getConf("path.giorn")));
        //db.closeDB();

        //String base64 = null;

        //if (base64 != null) {
            //String headerKey = "Content-Disposition";
            //String sanitizedmese = Utility.sanitize(mese);
            //String headerValue = format("attachment; filename=\"%s\"", new Object[]{"Controllodigestione_Giornaliero_" + sanitizedmese + ".xlsx"});
           // response.setHeader(headerKey, headerValue);
            //try (OutputStream outStream = response.getOutputStream()) {
             //   outStream.write(decodeBase64(base64.getBytes()));
           // }
       // } else {
           // redirect(request, response, "page_404.html");
       // }

        redirect(request, response, "page_404.html");

    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void contabilitauk(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        ArrayList<String> brList = new ArrayList<>();

        String[] branch2 = request.getParameterValues("branch");

        if (branch2 != null && !branch2[0].equals("null")) {
            brList = formatArrayValues(branch2);
        }

        String mese = request.getParameter("date2");

        String gr1 = request.getParameter("gr1");

        String[] gr2 = request.getParameterValues("gr2");
        ArrayList<String> gr02list = new ArrayList<>();

        if (gr2 != null) {
            gr02list = formatArrayValues(gr2);
        }

        ArrayList<String> filialidacontrollare = brList;

        if (filialidacontrollare.isEmpty()) {
            Db_Master db1 = new Db_Master();
            filialidacontrollare = db1.estraiFIlialidaGruppi(gr1, gr02list);
            db1.closeDB();
        }

        DateTimeFormatter formatter = forPattern(patternnormdate_filter);
        DateTime dtstart = formatter.parseDateTime("01/" + mese);

        DateTime oggi = new DateTime();
        DateTime dtnow = oggi.minusDays(1);

        String monthstring = dtstart.monthOfYear().getAsText(ITALY).toUpperCase();
        ArrayList<String> giornidacontrollare = new ArrayList<>();
        giornidacontrollare.add(dtstart.toString(patternnormdate_filter));

        for (int i = 1; i < 31; i++) {
            DateTime dtcontrollo = dtstart.plusDays(i);
            if (dtcontrollo.monthOfYear().getAsText(ITALY).toUpperCase().equalsIgnoreCase(monthstring) && dtcontrollo.isBefore(dtnow)) {
                giornidacontrollare.add(dtcontrollo.toString(patternnormdate_filter));
            }
        }

        String base64 = create_uk_contabilita(filialidacontrollare, giornidacontrollare);

        if (base64 != null) {
            String headerKey = "Content-Disposition";
            String sanitizedmese = Utility.sanitize(mese);
            String headerValue = format("attachment; filename=\"%s\"", new Object[]{"UK_Report_Change_Accounting_" + sanitizedmese + ".xlsx"});
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
    protected void giornaliero(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

//        Stopwatch stopwatch = Stopwatch.createStarted();
        ArrayList<String> brList = new ArrayList<>();

        String[] branch2 = request.getParameterValues("branch");

        if (branch2 != null && !branch2[0].equals("null")) {
            brList = formatArrayValues(branch2);
        }

        String mese = request.getParameter("date2");

        String gr1 = request.getParameter("gr1");
        //String gr2 = request.getParameter("gr2");

        String[] gr2 = request.getParameterValues("gr2");
        ArrayList<String> gr02list = new ArrayList<>();

        if (gr2 != null) {
            gr02list = formatArrayValues(gr2);
        }

        ArrayList<String> filialidacontrollare = brList;

        if (filialidacontrollare.isEmpty()) {
            Db_Master db1 = new Db_Master();
            filialidacontrollare = db1.estraiFIlialidaGruppi(gr1, gr02list);
            db1.closeDB();
        }

        DateTimeFormatter formatter = forPattern(patternnormdate_filter);
        DateTimeFormatter formattersql = forPattern(patternsql);
        DateTime dtstart = formatter.parseDateTime("01/" + mese);

        String mesemysql = dtstart.toString("yyyy-MM");

        String dataultimogiornomeseprecedente = dtstart.minusDays(1).toString(patternsql);

        DateTime oggi = new DateTime();
        DateTime dtnow = oggi.minusDays(1);

        String monthstring = dtstart.monthOfYear().getAsText(ITALY).toUpperCase();
        String yearstring = dtstart.year().getAsText(ITALY).toUpperCase();
        String yearstring_prev = dtstart.minusYears(1).year().getAsText(ITALY).toUpperCase();

        String primogiornoanno = yearstring + "-01-01";
        String primogiornoannoprec = yearstring_prev + "-01-01";
        String dataultimogiornomeseprecedenteannoprecedente = dtstart.minusDays(1).minusYears(1).toString(patternsql);

        ArrayList<String> giornidacontrollare = new ArrayList<>();
        giornidacontrollare.add(dtstart.toString(patternnormdate_filter));

        DateTime ultimogiornocontrollato = new DateTime();

        for (int i = 1; i < 31; i++) {
            DateTime dtcontrollo = dtstart.plusDays(i);
            if (dtcontrollo.monthOfYear().getAsText(ITALY).toUpperCase().equalsIgnoreCase(monthstring)) {
                giornidacontrollare.add(dtcontrollo.toString(patternnormdate_filter));
            } else {
                giornidacontrollare.add("");
            }
        }

        ArrayList<Colonna> elencovalori = new ArrayList<>();

        ArrayList<String> valori = new ArrayList<>();
        Colonna c = new Colonna();
        valori.add("GM");
        valori.add(monthstring);
        valori.add(yearstring);
        valori.addAll(giornidacontrollare);
        valori.add("");
        valori.add("Totale");
        valori.add("Proiezione Fine mese");
        valori.add("BUDGET");
        valori.add("+/- Var.");
        valori.add("%");
        valori.add("ANNO PREC.(adeguato)");
        valori.add("PESO % gg sul mese");
        valori.add(yearstring_prev + " ALLA DATA");
        valori.add("+/- Var.");
        valori.add("%");
        valori.add("YTD a fine mese " + yearstring);
        valori.add("YTD BDG");
        valori.add("+/- Var.");
        valori.add("%");
        valori.add("YTD " + yearstring_prev);
        valori.add("+/- Var.");
        valori.add("%");
        c.setValori(valori);
        c.setFormula("N");
        elencovalori.add(c);

        Db_Master db = new Db_Master();
        String pathtemp = db.getPath("temp");
        ArrayList<Branch> allbr = db.list_branch_completeAFTER311217();
        ArrayList<String[]> list_group = db.list_branch_group();

        ArrayList<GM> listarisultati = new ArrayList<>();
        ArrayList<GM> listarisultatiALTRECOLONNE = new ArrayList<>();
        ArrayList<GM> listarisultatiFILIALI = new ArrayList<>();
        ArrayList<GM> listarisultatiAREA = new ArrayList<>();

        GM fil2 = new GM();
        fil2.setRiga1("");
        fil2.setDescr("");
        fil2.setFormula("N");
        listarisultatiALTRECOLONNE.add(fil2);

        fil2 = new GM();
        fil2.setRiga1("CAMBIO");
        fil2.setRiga2("USD");
        fil2.setRiga3("");
        fil2.setDescr("CAMBIO");
        fil2.setFormula("N");
        HashMap<String, String> mappavaloriALTRI = fil2.getMappavalori();

        GM tot = new GM();
        tot.setRiga1("CM");
        tot.setRiga2("TOTALE");
        tot.setRiga3("GENERALE");
        tot.setDescr("TOTALE GENERALE");
        tot.setFormula("Y");
        HashMap<String, String> mappavaloriTOT = tot.getMappavalori();

        GM totold = new GM();
        totold.setRiga1("TOTALE");
        totold.setRiga2(yearstring_prev);
        totold.setRiga3("");
        totold.setDescr("");
        totold.setFormula("N");
        HashMap<String, String> mappavaloriTOTOLD = totold.getMappavalori();

        GM vargiorni = new GM();
        vargiorni.setRiga1("% VAR GIORNI");
        vargiorni.setRiga2(yearstring_prev + "/" + yearstring);
        vargiorni.setRiga3("");
        vargiorni.setDescr("%");
        vargiorni.setFormula("N");
        HashMap<String, String> mappavalorivargiorni = vargiorni.getMappavalori();

        GM varmtd1 = new GM();
        varmtd1.setRiga1("% VAR MTD");
        varmtd1.setRiga2(yearstring_prev + "/" + yearstring);
        varmtd1.setRiga3("");
        varmtd1.setDescr("%");
        varmtd1.setFormula("N");
        HashMap<String, String> mappavalorivarmtd = varmtd1.getMappavalori();

        GM gcop = new GM();
        gcop.setRiga1("GIACENZA");
        gcop.setRiga2("COP");
        gcop.setRiga3("");
        gcop.setDescr("");
        gcop.setFormula("N");
        HashMap<String, String> mappavalorigcop = gcop.getMappavalori();

        GM gfx = new GM();
        gfx.setRiga1("GIACENZA");
        gfx.setRiga2("FX");
        gfx.setRiga3("");
        gfx.setDescr("");
        gfx.setFormula("N");
        HashMap<String, String> mappavalorigfx = gfx.getMappavalori();

        GM gtot = new GM();
        gtot.setRiga1("GIACENZA");
        gtot.setRiga2("TOT");
        gtot.setRiga3("");
        gtot.setDescr("");
        gtot.setFormula("N");
        HashMap<String, String> mappavalorigtot = gtot.getMappavalori();

        ArrayList<CambioMTD_YTD> listdatifoglio2 = new ArrayList<>();
        ArrayList<CambioMTD_YTD> listdatifoglio3 = new ArrayList<>();
        ArrayList<DatiWU_MTD_YTD> listdatifoglio4 = new ArrayList<>();
        ArrayList<DatiWU_MTD_YTD> listdatifoglio5 = new ArrayList<>();

        ArrayList<String> filiali_mtd = new ArrayList<>();
        ArrayList<String> filiali_ytd = new ArrayList<>();

        ArrayList<String> listcat_mtd = new ArrayList<>();
        ArrayList<String> listcat_ytd = new ArrayList<>();
        ArrayList<NC_val> nc_mtd = new ArrayList<>();
        ArrayList<NC_val> nc_ytd = new ArrayList<>();

        Db_Master dbnc = new Db_Master();

        for (int i = 0; i < filialidacontrollare.size(); i++) {

            String filiale1 = filialidacontrollare.get(i);
            Branch b1 = get_Branch(filiale1, allbr);

            double MTDbuy = 0.00, MTDbuy_prevyear = 0.00, MTDcc = 0.00, MTDcc_prevyear = 0.00, MTDsell = 0.00, MTDsell_prevyear = 0.00;
            int MTDtrbuy = 0, MTDtrbuy_prevyear = 0, MTDtrcctr = 0, MTDtrcc_prevyear = 0, MTDtrsell = 0, MTDtrsell_prevyear = 0;

            double YTDbuy = 0.00, YTDbuy_prevyear = 0.00, YTDcc = 0.00, YTDcc_prevyear = 0.00, YTDsell = 0.00, YTDsell_prevyear = 0.00;
            int YTDtrbuy = 0, YTDtrbuy_prevyear = 0, YTDtrcctr = 0, YTDtrcc_prevyear = 0, YTDtrsell = 0, YTDtrsell_prevyear = 0;

            double MTDvolumesend = 0.00, MTDvolumesend_prevyear = 0.00, MTDvolumerec = 0.00, MTDvolumerec_prevyear = 0.00, MTDvolumetot = 0.00, MTDvolumetot_prevyear = 0.00;
            int MTDtrsend = 0, MTDtrsend_prevyear = 0, MTDtrrec = 0, MTDtrrec_prevyear = 0, MTDtrtot = 0, MTDtrtot_prevyear = 0;

            double YTDvolumesend = 0.00, YTDvolumesend_prevyear = 0.00, YTDvolumerec = 0.00, YTDvolumerec_prevyear = 0.00, YTDvolumetot = 0.00, YTDvolumetot_prevyear = 0.00;
            int YTDtrsend = 0, YTDtrsend_prevyear = 0, YTDtrrec = 0, YTDtrrec_prevyear = 0, YTDtrtot = 0, YTDtrtot_prevyear = 0;

            CambioMTD_YTD mtd_1 = new CambioMTD_YTD();

            mtd_1.setFiliale(filiale1);
            mtd_1.setDescr(b1.getDe_branch());
            mtd_1.setDatastart(dtstart);
            mtd_1.setDataend(dtstart);

            CambioMTD_YTD ytd_1 = new CambioMTD_YTD();

            ytd_1.setFiliale(filiale1);
            ytd_1.setDescr(b1.getDe_branch());
            ytd_1.setDatastart(dtstart);
            ytd_1.setDataend(dtstart);

            DatiWU_MTD_YTD wumtd = new DatiWU_MTD_YTD();
            DatiWU_MTD_YTD wuytd = new DatiWU_MTD_YTD();

            wumtd.setFiliale(filiale1);
            wumtd.setDescr(b1.getDe_branch());
            wumtd.setDatastart(dtstart);
            wumtd.setDataend(dtstart);

            wuytd.setFiliale(filiale1);
            wuytd.setDescr(b1.getDe_branch());
            wuytd.setDatastart(dtstart);
            wuytd.setDataend(dtstart);

            Branchbudget bb1 = db.get_branch_budget(filiale1, mesemysql);
            ArrayList<Branchbudget> listabudget = db.get_branch_budget_YTD(filiale1, yearstring);

            GM area = new GM();
            area.setArea(b1.getBrgr_01());
            area.setRiga1("TOT");
            String descrizionearea = formatAL(b1.getBrgr_01(), list_group, 1);
            area.setRiga2("AREA");
            area.setRiga3(descrizionearea);
            area.setDescr("R");
            area.setFormula("Y");
            boolean fo = false;
            for (int y = 0; y < listarisultatiAREA.size(); y++) {
                GM areap = listarisultatiAREA.get(y);
                if (areap.getArea().equals(area.getArea())) {
                    area = areap;
                    fo = true;
                }
            }

            if (!fo) {
                listarisultatiAREA.add(area);
            }

            String[] ff = {filiale1, b1.getDe_branch()};

            String[] descr_for_report = descr_for_report(b1.getDe_branch());

            GM fil1 = new GM();
            fil1.setArea(b1.getBrgr_01());
            fil1.setRiga1(filiale1);
            fil1.setRiga2(descr_for_report[0]);
            fil1.setRiga3(descr_for_report[1]);
            fil1.setDescr("");
            fil1.setFormula("Y");

            if (bb1 == null) {
                fil1.setBudget("0.00");
            } else {
                fil1.setBudget(bb1.getBudg1());
            }

            area.setBudget(roundDoubleandFormat(fd(area.getBudget()) + fd(fil1.getBudget()), 2));
            tot.setBudget(roundDoubleandFormat(fd(tot.getBudget()) + fd(fil1.getBudget()), 2));

            double total = 0.00;
            double annoprec_adeguato = 0.00;
            double annoprec_alldata = 0.00;

            double ytd_finemese;
            double annoprec_ytd = 0.00;

            Daily_value dv_tuttoanno = db.list_Daily_value(ff, primogiornoanno + " 00:00", dataultimogiornomeseprecedente + " 23:59", false, true);
            if (dv_tuttoanno != null) {
                YTDbuy = fd(dv_tuttoanno.getPurchTotal()) + fd(dv_tuttoanno.getPurchComm());
                YTDcc = fd(dv_tuttoanno.getCashAdNetTot()) + fd(dv_tuttoanno.getCashAdComm());
                YTDsell = fd(dv_tuttoanno.getSalesTotal()) - fd(dv_tuttoanno.getSalesComm());
                YTDtrbuy = parseIntR(dv_tuttoanno.getNoTransPurch());
                YTDtrcctr = parseIntR(dv_tuttoanno.getNoTransCC());
                YTDtrsell = parseIntR(dv_tuttoanno.getNoTransSales());

                ArrayList<DailyKind> lik = dv_tuttoanno.getDati();
                for (int t = 0; t < lik.size(); t++) {
                    DailyKind wu1 = lik.get(t);
                    if (wu1.getKind().toLowerCase().contains("western")) {

                        YTDvolumesend = YTDvolumesend + parseDoubleR(wu1.getFromTotal());
                        YTDvolumerec = YTDvolumerec + parseDoubleR(wu1.getToTotal());

                        YTDtrsend = YTDtrsend + parseIntR(wu1.getFromNrTran());
                        YTDtrrec = YTDtrrec + parseIntR(wu1.getToNrTran());

                        break;
                    }

                }

            }

            Daily_value dv_tuttoanno_precedente = db.list_Daily_value(ff, primogiornoannoprec + " 00:00", dataultimogiornomeseprecedenteannoprecedente + " 23:59", false, true);
            if (dv_tuttoanno_precedente != null) {
                annoprec_ytd = fd(dv_tuttoanno_precedente.getGrossProfit());
                YTDbuy_prevyear = fd(dv_tuttoanno_precedente.getPurchTotal()) + fd(dv_tuttoanno_precedente.getPurchComm());
                YTDcc_prevyear = fd(dv_tuttoanno_precedente.getCashAdNetTot()) + fd(dv_tuttoanno_precedente.getCashAdComm());
                YTDsell_prevyear = fd(dv_tuttoanno_precedente.getSalesTotal()) - fd(dv_tuttoanno_precedente.getSalesComm());
                YTDtrbuy_prevyear = parseIntR(dv_tuttoanno_precedente.getNoTransPurch());
                YTDtrcc_prevyear = parseIntR(dv_tuttoanno_precedente.getNoTransCC());
                YTDtrsell_prevyear = parseIntR(dv_tuttoanno_precedente.getNoTransSales());

                ArrayList<DailyKind> lik = dv_tuttoanno_precedente.getDati();
                for (int t = 0; t < lik.size(); t++) {
                    DailyKind wu1 = lik.get(t);
                    if (wu1.getKind().toLowerCase().contains("western")) {
                        YTDvolumesend_prevyear = YTDvolumesend_prevyear + parseDoubleR(wu1.getFromTotal());
                        YTDvolumerec_prevyear = YTDvolumerec_prevyear + parseDoubleR(wu1.getToTotal());
                        YTDtrsend_prevyear = YTDtrsend_prevyear + parseIntR(wu1.getFromNrTran());
                        YTDtrrec_prevyear = YTDtrrec_prevyear + parseIntR(wu1.getToNrTran());
                        break;
                    }
                }

            }

            double ytd_budget = 0.00;

            for (int x = 0; x < listabudget.size(); x++) {
                ytd_budget = ytd_budget + fd(listabudget.get(x).getBudg1());
            }

            double tot_varmtd = 0.00;
            double totold_varmtd = 0.00;
            double varmtd = 0.00;

            DateTime d0 = null;
            DateTime d0pv = null;

            HashMap<String, String> mappavalori = fil1.getMappavalori();
            HashMap<String, String> mappavaloriAREA = area.getMappavalori();
            for (int v = 0; v < giornidacontrollare.size(); v++) {
                if (!giornidacontrollare.get(v).equals("")) {
                    String giornoannoscorsoadeguato = getGiornoAdeguatoAnnoPrecedente(giornidacontrollare.get(v));
                    String datadac_giornoannoscorsoadeguato = formatStringtoStringDate_null(giornoannoscorsoadeguato, patternnormdate_filter, patternsql);
                    Daily_value dv_giornoannoscorsoadeguato = db.list_Daily_value(ff, datadac_giornoannoscorsoadeguato + " 00:00", datadac_giornoannoscorsoadeguato + " 23:59", false, true);
                    annoprec_adeguato = annoprec_adeguato + fd(dv_giornoannoscorsoadeguato.getGrossProfit());

                    if (formatter.parseDateTime(giornidacontrollare.get(v)).isBefore(dtnow)) {
                        d0 = formatter.parseDateTime(giornidacontrollare.get(v));
                        d0pv = formatter.parseDateTime(giornoannoscorsoadeguato);

                        String valoreareaTOTOLD = mappavaloriTOTOLD.get(giornidacontrollare.get(v));
                        if (valoreareaTOTOLD == null) {
                            mappavaloriTOTOLD.put(giornidacontrollare.get(v), dv_giornoannoscorsoadeguato.getGrossProfit());
                        } else {
                            double valoreaggiornato = fd(dv_giornoannoscorsoadeguato.getGrossProfit()) + fd(valoreareaTOTOLD);
                            mappavaloriTOTOLD.put(giornidacontrollare.get(v), roundDoubleandFormat(valoreaggiornato, 2));
                        }

                        ultimogiornocontrollato = formatter.parseDateTime(giornidacontrollare.get(v));
                        annoprec_alldata = annoprec_alldata + fd(dv_giornoannoscorsoadeguato.getGrossProfit());
                        String datadac = formatStringtoStringDate_null(giornidacontrollare.get(v), patternnormdate_filter, patternsql);
                        Daily_value dv = db.list_Daily_value(ff, datadac + " 00:00", datadac + " 23:59", false, true);

                        ArrayList<DailyKind> lik = dv.getDati();
                        for (int t = 0; t < lik.size(); t++) {
                            DailyKind wu1 = lik.get(t);
                            if (wu1.getKind().toLowerCase().contains("western")) {

                                MTDvolumesend = MTDvolumesend + parseDoubleR(wu1.getFromTotal());
                                MTDvolumerec = MTDvolumerec + parseDoubleR(wu1.getToTotal());

                                MTDtrsend = MTDtrsend + parseIntR(wu1.getFromNrTran());
                                MTDtrrec = MTDtrrec + parseIntR(wu1.getToNrTran());

                                break;
                            }

                        }

                        lik = dv_giornoannoscorsoadeguato.getDati();
                        for (int t = 0; t < lik.size(); t++) {
                            DailyKind wu1 = lik.get(t);
                            if (wu1.getKind().toLowerCase().contains("western")) {
                                MTDvolumesend_prevyear = MTDvolumesend_prevyear + parseDoubleR(wu1.getFromTotal());
                                MTDvolumerec_prevyear = MTDvolumerec_prevyear + parseDoubleR(wu1.getToTotal());
                                MTDtrsend_prevyear = MTDtrsend_prevyear + parseIntR(wu1.getFromNrTran());
                                MTDtrrec_prevyear = MTDtrrec_prevyear + parseIntR(wu1.getToNrTran());
                                break;
                            }

                        }

                        MTDvolumetot = MTDvolumesend + MTDvolumerec;
                        MTDtrtot = MTDtrsend + MTDtrrec;
                        MTDvolumetot_prevyear = MTDvolumesend_prevyear + MTDvolumerec_prevyear;
                        MTDtrtot_prevyear = MTDtrsend_prevyear + MTDtrrec_prevyear;

                        MTDbuy = MTDbuy + fd(dv.getPurchTotal()) + fd(dv.getPurchComm());
                        MTDcc = MTDcc + fd(dv.getCashAdNetTot()) + fd(dv.getCashAdComm());
                        MTDsell = MTDsell + fd(dv.getSalesTotal()) - fd(dv.getSalesComm());

                        MTDtrbuy = MTDtrbuy + parseIntR(dv.getNoTransPurch());
                        MTDtrcctr = MTDtrcctr + parseIntR(dv.getNoTransCC());
                        MTDtrsell = MTDtrsell + parseIntR(dv.getNoTransSales());

                        MTDbuy_prevyear = MTDbuy_prevyear + fd(dv_giornoannoscorsoadeguato.getPurchTotal()) + fd(dv_giornoannoscorsoadeguato.getPurchComm());
                        MTDcc_prevyear = MTDcc_prevyear + fd(dv_giornoannoscorsoadeguato.getCashAdNetTot()) + fd(dv_giornoannoscorsoadeguato.getCashAdComm());
                        MTDsell_prevyear = MTDsell_prevyear + fd(dv_giornoannoscorsoadeguato.getSalesTotal()) - fd(dv_giornoannoscorsoadeguato.getSalesComm());

                        MTDtrbuy_prevyear = MTDtrbuy_prevyear + parseIntR(dv_giornoannoscorsoadeguato.getNoTransPurch());
                        MTDtrcc_prevyear = MTDtrcc_prevyear + parseIntR(dv_giornoannoscorsoadeguato.getNoTransCC());
                        MTDtrsell_prevyear = MTDtrsell_prevyear + parseIntR(dv_giornoannoscorsoadeguato.getNoTransSales());

                        total = total + fd(dv.getGrossProfit());
                        mappavalori.put(giornidacontrollare.get(v), dv.getGrossProfit());

                        double totalgtot = fd(dv.getCashOnPrem()) + fd(dv.getFx());

                        String valorigcop = mappavalorigcop.get(giornidacontrollare.get(v));
                        if (valorigcop == null) {
                            mappavalorigcop.put(giornidacontrollare.get(v), dv.getCashOnPrem());
                        } else {
                            double valoreaggiornato = fd(dv.getCashOnPrem()) + fd(valorigcop);
                            mappavalorigcop.put(giornidacontrollare.get(v), roundDoubleandFormat(valoreaggiornato, 2));
                        }
                        //mappavalorigcop.put(giornidacontrollare.get(v), dv.getCashOnPremFromTrans());

                        String valorigfx = mappavalorigfx.get(giornidacontrollare.get(v));
                        if (valorigfx == null) {
                            mappavalorigfx.put(giornidacontrollare.get(v), dv.getFx());
                        } else {
                            double valoreaggiornato = fd(dv.getFx()) + fd(valorigfx);
                            mappavalorigfx.put(giornidacontrollare.get(v), roundDoubleandFormat(valoreaggiornato, 2));
                        }
//                        mappavalorigfx.put(giornidacontrollare.get(v), dv.getFx());

                        String valorigtot = mappavalorigtot.get(giornidacontrollare.get(v));
                        if (valorigtot == null) {
                            mappavalorigtot.put(giornidacontrollare.get(v), roundDoubleandFormat(totalgtot, 2));
                        } else {
                            double valoreaggiornato = totalgtot + fd(valorigtot);
                            mappavalorigtot.put(giornidacontrollare.get(v), roundDoubleandFormat(valoreaggiornato, 2));
                        }

//                        mappavalorigtot.put(giornidacontrollare.get(v), roundDoubleandFormat(totalgtot, 2));
                        String valorearea = mappavaloriAREA.get(giornidacontrollare.get(v));
                        if (valorearea == null) {
                            mappavaloriAREA.put(giornidacontrollare.get(v), dv.getGrossProfit());
                        } else {
                            double valoreaggiornato = fd(dv.getGrossProfit()) + fd(valorearea);
                            mappavaloriAREA.put(giornidacontrollare.get(v), roundDoubleandFormat(valoreaggiornato, 2));
                        }

                        String valoretotale = mappavaloriTOT.get(giornidacontrollare.get(v));
                        if (valoretotale == null) {
                            mappavaloriTOT.put(giornidacontrollare.get(v), dv.getGrossProfit());
                        } else {
                            double valoreaggiornato = fd(dv.getGrossProfit()) + fd(valoretotale);
                            mappavaloriTOT.put(giornidacontrollare.get(v), roundDoubleandFormat(valoreaggiornato, 2));
                        }

                        double totgen = fd(mappavaloriTOT.get(giornidacontrollare.get(v)));
                        double totannprec = fd(mappavaloriTOTOLD.get(giornidacontrollare.get(v)));
                        String valoredainserire = "100,00%";
                        if (totannprec != 0) {
                            valoredainserire = formatMysqltoDisplay(roundDoubleandFormat(((divisione_controllozero(totgen, totannprec)) - 1), 2)) + "%";
                        } else {
                            if (totgen == 0) {
                                valoredainserire = "0,00%";
                            }
                        }

                        tot_varmtd = tot_varmtd + totgen;
                        totold_varmtd = totold_varmtd + totannprec;

                        String valoredainserire2 = "100,00%";
                        if (totold_varmtd != 0) {
                            valoredainserire2 = formatMysqltoDisplay(roundDoubleandFormat(((divisione_controllozero(tot_varmtd, totold_varmtd)) - 1), 2)) + "%";
                        } else {
                            if (tot_varmtd == 0) {
                                valoredainserire2 = "0,00%";
                            }
                        }
                        mappavalorivarmtd.put(giornidacontrollare.get(v), valoredainserire2);

                        mappavalorivargiorni.put(giornidacontrollare.get(v), valoredainserire);
                        mappavaloriALTRI.put(giornidacontrollare.get(v), db.get_BCE_USD(datadac));

                        mtd_1.setFx(dv.getFx());
                        mtd_1.setCop(dv.getCashOnPrem());

                    } else {
                        mappavalori.put(giornidacontrollare.get(v), " ");
                        mappavaloriAREA.put(giornidacontrollare.get(v), " ");
                        mappavaloriTOT.put(giornidacontrollare.get(v), " ");
                        mappavaloriALTRI.put(giornidacontrollare.get(v), " ");
                        mappavaloriTOTOLD.put(giornidacontrollare.get(v), " ");
                        mappavalorivargiorni.put(giornidacontrollare.get(v), " ");
                    }
                    //annoprecadeguato
                }
            }
            fil1.setAnnoprec_adeguato(roundDoubleandFormat(annoprec_adeguato, 2));

            area.setAnnoprec_adeguato(roundDoubleandFormat(fd(area.getAnnoprec_adeguato()) + fd(fil1.getAnnoprec_adeguato()), 2));
            tot.setAnnoprec_adeguato(roundDoubleandFormat(fd(tot.getAnnoprec_adeguato()) + fd(fil1.getAnnoprec_adeguato()), 2));

            fil1.setAnnoprec_alldata(roundDoubleandFormat(annoprec_alldata, 2));

            area.setAnnoprec_alldata(area.getAnnoprec_alldata() + fd(fil1.getAnnoprec_alldata()));
            tot.setAnnoprec_alldata(tot.getAnnoprec_alldata() + fd(fil1.getAnnoprec_alldata()));

            area.setAnnoprec_adeguato(roundDoubleandFormat(fd(area.getAnnoprec_adeguato()) + fd(fil1.getAnnoprec_adeguato()), 2));
            tot.setAnnoprec_adeguato(roundDoubleandFormat(fd(tot.getAnnoprec_adeguato()) + fd(fil1.getAnnoprec_adeguato()), 2));

            if (annoprec_adeguato == 0 || annoprec_alldata == 0) {
                annoprec_alldata = 1.0;
                annoprec_adeguato = 1.0;
            }

            ytd_finemese = divisione_controllozero(total, (divisione_controllozero(annoprec_alldata, annoprec_adeguato))) * 100;

            fil1.setYtd_finemese(roundDoubleandFormat(ytd_finemese, 2));
            fil1.setYtd_budget(roundDoubleandFormat(ytd_budget, 2));
            fil1.setAnnoprec_ytd(roundDoubleandFormat(annoprec_ytd, 2));

            area.setYtd_finemese(roundDoubleandFormat(fd(area.getYtd_finemese()) + fd(fil1.getYtd_finemese()), 2));
            area.setYtd_budget(roundDoubleandFormat(fd(area.getYtd_budget()) + fd(fil1.getYtd_budget()), 2));
            area.setAnnoprec_ytd(roundDoubleandFormat(fd(area.getAnnoprec_ytd()) + fd(fil1.getAnnoprec_ytd()), 2));

            tot.setYtd_finemese(roundDoubleandFormat(fd(tot.getYtd_finemese()) + fd(fil1.getYtd_finemese()), 2));
            tot.setYtd_budget(roundDoubleandFormat(fd(tot.getYtd_budget()) + fd(fil1.getYtd_budget()), 2));
            tot.setAnnoprec_ytd(roundDoubleandFormat(fd(tot.getAnnoprec_ytd()) + fd(fil1.getAnnoprec_ytd()), 2));

            listarisultatiFILIALI.add(fil1);

            mtd_1.setMtd_totale(roundDoubleandFormat(total, 2));
            mtd_1.setMtd_totale_prevyear(roundDoubleandFormat(annoprec_alldata, 2));
            mtd_1.setMtd_bdg(roundDoubleandFormat(fd(fil1.getBudget()) * (divisione_controllozero(annoprec_alldata, annoprec_adeguato)), 2));
            double d1 = total * (divisione_controllozero(annoprec_adeguato, annoprec_alldata)) * 100.0;
            mtd_1.setMtd_proiezione_finemese(roundDoubleandFormat(d1, 2));
            mtd_1.setMtd_proiezione_budget_finemese(roundDoubleandFormat(fd(fil1.getBudget()), 2));
            mtd_1.setBuy(roundDoubleandFormat(MTDbuy, 2));
            mtd_1.setBuy_prevyear(roundDoubleandFormat(MTDbuy_prevyear, 2));
            mtd_1.setCc(roundDoubleandFormat(MTDcc, 2));
            mtd_1.setCc_prevyear(roundDoubleandFormat(MTDcc_prevyear, 2));
            mtd_1.setBuycc(roundDoubleandFormat(MTDbuy + MTDcc, 2));
            mtd_1.setBuycc_prevyear(roundDoubleandFormat(MTDbuy_prevyear + MTDcc_prevyear, 2));
            mtd_1.setSell(roundDoubleandFormat(MTDsell, 2));
            mtd_1.setSell_prevyear(roundDoubleandFormat(MTDsell_prevyear, 2));
            mtd_1.setTotvol(roundDoubleandFormat(MTDbuy + MTDcc + MTDsell, 2));
            mtd_1.setTotvol_prevyear(roundDoubleandFormat(MTDbuy_prevyear + MTDcc_prevyear + MTDsell_prevyear, 2));
            mtd_1.setTrbuy(valueOf(MTDtrbuy));
            mtd_1.setTrbuy_prevyear(valueOf(MTDtrbuy_prevyear));
            mtd_1.setTrcctr(valueOf(MTDtrcctr));
            mtd_1.setTrcc_prevyear(valueOf(MTDtrcc_prevyear));
            mtd_1.setTrbuycc(valueOf(MTDtrbuy + MTDtrcctr));
            mtd_1.setTrbuycc_prevyear(valueOf(MTDtrbuy_prevyear + MTDtrcc_prevyear));
            mtd_1.setTrsell(valueOf(MTDtrsell));
            mtd_1.setTrsell_prevyear(valueOf(MTDtrsell_prevyear));
            mtd_1.setTrtot(valueOf(MTDtrbuy + MTDtrcctr + MTDtrsell));
            mtd_1.setTrtot_prevyear(valueOf(MTDtrbuy_prevyear + MTDtrcc_prevyear + MTDtrsell_prevyear));

            listdatifoglio2.add(mtd_1);

            ytd_1.setMtd_totale(roundDoubleandFormat(ytd_finemese, 2));
            ytd_1.setMtd_totale_prevyear(roundDoubleandFormat(annoprec_ytd, 2));
            ytd_1.setMtd_bdg(roundDoubleandFormat(ytd_budget, 2));
            ytd_1.setBuy(roundDoubleandFormat(YTDbuy + MTDbuy, 2));
            ytd_1.setBuy_prevyear(roundDoubleandFormat(YTDbuy_prevyear + MTDbuy_prevyear, 2));
            ytd_1.setCc(roundDoubleandFormat(YTDcc + MTDcc, 2));
            ytd_1.setCc_prevyear(roundDoubleandFormat(YTDcc_prevyear + MTDcc_prevyear, 2));
            ytd_1.setBuycc(roundDoubleandFormat(YTDbuy + YTDcc + MTDbuy + MTDcc, 2));
            ytd_1.setBuycc_prevyear(roundDoubleandFormat(YTDbuy_prevyear + YTDcc_prevyear + MTDbuy_prevyear + MTDcc_prevyear, 2));
            ytd_1.setSell(roundDoubleandFormat(YTDsell + MTDsell, 2));
            ytd_1.setSell_prevyear(roundDoubleandFormat(YTDsell_prevyear + MTDsell_prevyear, 2));
            ytd_1.setTotvol(roundDoubleandFormat(YTDbuy + YTDcc + YTDsell + MTDbuy + MTDcc + MTDsell, 2));
            ytd_1.setTotvol_prevyear(roundDoubleandFormat(YTDbuy_prevyear + YTDcc_prevyear + YTDsell_prevyear + MTDbuy_prevyear + MTDcc_prevyear + MTDsell_prevyear, 2));
            ytd_1.setTrbuy(valueOf(YTDtrbuy + MTDtrbuy));
            ytd_1.setTrbuy_prevyear(valueOf(YTDtrbuy_prevyear + MTDtrbuy_prevyear));
            ytd_1.setTrcctr(valueOf(YTDtrcctr + MTDtrcctr));
            ytd_1.setTrcc_prevyear(valueOf(YTDtrcc_prevyear + MTDtrcc_prevyear));
            ytd_1.setTrbuycc(valueOf(YTDtrbuy + YTDtrcctr + MTDtrbuy + MTDtrcctr));
            ytd_1.setTrbuycc_prevyear(valueOf(YTDtrbuy_prevyear + YTDtrcc_prevyear + MTDtrbuy_prevyear + MTDtrcc_prevyear));
            ytd_1.setTrsell(valueOf(YTDtrsell + MTDtrsell));
            ytd_1.setTrsell_prevyear(valueOf(YTDtrsell_prevyear + MTDtrsell_prevyear));
            ytd_1.setTrtot(valueOf(YTDtrbuy + YTDtrcctr + YTDtrsell + MTDtrbuy + MTDtrcctr + MTDtrsell));
            ytd_1.setTrtot_prevyear(valueOf(YTDtrbuy_prevyear + YTDtrcc_prevyear + YTDtrsell_prevyear + MTDtrbuy_prevyear + MTDtrcc_prevyear + MTDtrsell_prevyear));

            listdatifoglio3.add(ytd_1);

            wumtd.setVolumesend(roundDoubleandFormat(MTDvolumesend, 2));
            wumtd.setVolumesend_prevyear(roundDoubleandFormat(MTDvolumesend_prevyear, 2));
            wumtd.setVolumerec(roundDoubleandFormat(MTDvolumerec, 2));
            wumtd.setVolumerec_prevyear(roundDoubleandFormat(MTDvolumerec_prevyear, 2));
            wumtd.setVolumetot(roundDoubleandFormat(MTDvolumetot, 2));
            wumtd.setVolumetot_prevyear(roundDoubleandFormat(MTDvolumetot_prevyear, 2));
            wumtd.setTrsend(valueOf(MTDtrsend));
            wumtd.setTrsend_prevyear(valueOf(MTDtrsend_prevyear));
            wumtd.setTrrec(valueOf(MTDtrrec));
            wumtd.setTrrec_prevyear(valueOf(MTDtrrec_prevyear));
            wumtd.setTrtot(valueOf(MTDtrtot));
            wumtd.setTrtot_prevyear(valueOf(MTDtrtot_prevyear));

            listdatifoglio4.add(wumtd);

            wuytd.setVolumesend(roundDoubleandFormat(YTDvolumesend + MTDvolumesend, 2));
            wuytd.setVolumesend_prevyear(roundDoubleandFormat(YTDvolumesend_prevyear + MTDvolumesend_prevyear, 2));
            wuytd.setVolumerec(roundDoubleandFormat(YTDvolumerec + MTDvolumerec, 2));
            wuytd.setVolumerec_prevyear(roundDoubleandFormat(YTDvolumerec_prevyear + MTDvolumerec_prevyear, 2));
            wuytd.setVolumetot(roundDoubleandFormat(YTDvolumesend + YTDvolumerec + MTDvolumetot, 2));
            wuytd.setVolumetot_prevyear(roundDoubleandFormat(YTDvolumesend_prevyear + YTDvolumerec_prevyear + MTDvolumetot_prevyear, 2));
            wuytd.setTrsend(valueOf(YTDtrsend + MTDtrsend));
            wuytd.setTrsend_prevyear(valueOf(YTDtrsend_prevyear + MTDtrsend_prevyear));
            wuytd.setTrrec(valueOf(YTDtrrec + MTDtrrec));
            wuytd.setTrrec_prevyear(valueOf(YTDtrrec_prevyear + MTDtrrec_prevyear));
            wuytd.setTrtot(valueOf(YTDtrsend + YTDtrrec + MTDtrtot));
            wuytd.setTrtot_prevyear(valueOf(YTDtrsend_prevyear + YTDtrrec_prevyear + MTDtrtot_prevyear));

            listdatifoglio5.add(wuytd);

            if (d0 != null && d0pv != null) {

                ArrayList<NC_transaction> nc_list = dbnc.query_NC_transaction_enable("01/01/" + yearstring, d0.toString(patternnormdate_filter), filiale1);
                ArrayList<NC_transaction> nc_list_prevyear = dbnc.query_NC_transaction_enable("01/01/" + yearstring_prev,
                        d0pv.toString(patternnormdate_filter), filiale1);
//                dbnc.closeDB();

                for (int d = 0; d < nc_list.size(); d++) {
                    NC_transaction nc = nc_list.get(d);

                    if (!nc.getFg_tipo_transazione_nc().equals("1") && !nc.getTotal().contains("-")) {
                        DateTime d1o = formattersql.parseDateTime(nc.getData().split(" ")[0]);
                        double q = fd(nc.getQuantita().replaceAll("-", ""));
                        double t = fd(nc.getTotal().replaceAll("-", ""));
                        NC_val ncv = new NC_val(nc.getGruppo_nc(), yearstring, filiale1, q, t);
                        NC_val ncv1 = new NC_val(nc.getGruppo_nc(), yearstring, filiale1, q, t);

                        if (d1o.isAfter(dtstart) || d1o.isEqual(dtstart)) {

                            listcat_mtd.add(nc.getGruppo_nc());
                            filiali_mtd.add(nc.getFiliale());
                            boolean found = false;
                            for (int rr = 0; rr < nc_mtd.size(); rr++) {
                                NC_val vx = nc_mtd.get(rr);
                                if (vx.getCat().equals(nc.getGruppo_nc()) && vx.getYear().equals(yearstring) && vx.getFiliale().equals(filiale1)) {
                                    double nq = q + vx.getQuantity();
                                    double nt = t + vx.getTot();
                                    vx.setQuantity(nq);
                                    vx.setTot(nt);

                                    found = true;
                                    break;
                                }
                            }
                            if (!found) {
                                nc_mtd.add(ncv);
                            }
                        }

                        boolean found = false;
                        for (int rr = 0; rr < nc_ytd.size(); rr++) {
                            NC_val vx = nc_ytd.get(rr);
                            if (vx.getCat().equals(nc.getGruppo_nc()) && vx.getYear().equals(yearstring) && vx.getFiliale().equals(filiale1)) {
                                double nq = q + vx.getQuantity();
                                double nt = t + vx.getTot();
                                vx.setQuantity(nq);
                                vx.setTot(nt);
                                found = true;
                                break;
                            }
                        }
                        if (!found) {
                            nc_ytd.add(ncv1);
                        }

                        listcat_ytd.add(nc.getGruppo_nc());
                        filiali_ytd.add(nc.getFiliale());
                    }

                }

                for (int d = 0; d < nc_list_prevyear.size(); d++) {
                    NC_transaction nc = nc_list_prevyear.get(d);

//                    if (!nc.getFg_tipo_transazione_nc().equals("1") && !nc.getTotal().contains("-")) {
                    DateTime d1o = formattersql.parseDateTime(nc.getData().split(" ")[0]);
                    double q = fd(nc.getQuantita().replaceAll("-", ""));
                    double t = fd(nc.getTotal().replaceAll("-", ""));
                    NC_val ncv = new NC_val(nc.getGruppo_nc(), yearstring, filiale1, q, t);

                    if (d1o.isAfter(dtstart) || d1o.isEqual(dtstart)) {
                        listcat_mtd.add(nc.getGruppo_nc());
                        boolean found = false;
                        for (int rr = 0; rr < nc_mtd.size(); rr++) {
                            NC_val vx = nc_mtd.get(rr);
                            if (vx.getCat().equals(nc.getGruppo_nc()) && vx.getYear().equals(yearstring) && vx.getFiliale().equals(filiale1)) {
                                double nq = q + vx.getQuantity();
                                double nt = t + vx.getTot();
                                vx.setQuantity(nq);
                                vx.setTot(nt);
                                found = true;
                                break;
                            }
                        }
                        if (!found) {
                            nc_mtd.add(ncv);
                        }
                    }

                    boolean found = false;
                    for (int rr = 0; rr < nc_ytd.size(); rr++) {
                        NC_val vx = nc_ytd.get(rr);
                        if (vx.getCat().equals(nc.getGruppo_nc()) && vx.getYear().equals(yearstring) && vx.getFiliale().equals(filiale1)) {
                            double nq = q + vx.getQuantity();
                            double nt = t + vx.getTot();
                            vx.setQuantity(nq);
                            vx.setTot(nt);
                            found = true;
                            break;
                        }
                    }
                    if (!found) {
                        nc_ytd.add(ncv);
                    }

                    listcat_ytd.add(nc.getGruppo_nc());
//                    }
                }
            }
        }

        dbnc.closeDB();

        removeDuplicatesAL(listcat_mtd);
        removeDuplicatesAL(listcat_ytd);
        removeDuplicatesAL(filiali_mtd);
        removeDuplicatesAL(filiali_ytd);
        sort(listarisultatiFILIALI);

        db.closeDB();

        for (int x = 0; x < listarisultatiFILIALI.size() - 1; x++) {
            listarisultati.add(listarisultatiFILIALI.get(x));
            if (!listarisultatiFILIALI.get(x).getArea().equals(listarisultatiFILIALI.get(x + 1).getArea())) {
                for (int y = 0; y < listarisultatiAREA.size(); y++) {
                    if (listarisultatiAREA.get(y).getArea().equals(listarisultatiFILIALI.get(x).getArea())) {
                        listarisultati.add(listarisultatiAREA.get(y));
                        listarisultatiAREA.remove(y);
                        break;
                    }
                }
            }
        }

        listarisultati.add(listarisultatiFILIALI.get(listarisultatiFILIALI.size() - 1));
        listarisultati.addAll(listarisultatiAREA);

        listarisultatiALTRECOLONNE.add(fil2);
        listarisultati.addAll(listarisultatiALTRECOLONNE);
        listarisultati.add(tot);
        GM fil1 = new GM();
        fil1.setRiga1("EVENTI");
        fil1.setDescr("");
        fil1.setFormula("N");
        listarisultati.add(fil1);
        listarisultati.add(totold);
        listarisultati.add(vargiorni);
        listarisultati.add(varmtd1);
        fil1 = new GM();
        fil1.setRiga1("");
        fil1.setDescr("");
        fil1.setFormula("N");
        listarisultati.add(fil1);
        listarisultati.add(gcop);
        listarisultati.add(gfx);
        listarisultati.add(gtot);

        for (int v = 0; v < listarisultati.size(); v++) {
            GM filialedascrivere = listarisultati.get(v);
            valori = new ArrayList<>();
            c = new Colonna();

            valori.add(filialedascrivere.getRiga1());
            if (!filialedascrivere.getRiga1().equals("")) {
                valori.add(filialedascrivere.getRiga2());
                valori.add(filialedascrivere.getRiga3());
                for (int x = 0; x < giornidacontrollare.size(); x++) {
                    String value = filialedascrivere.getMappavalori().get(giornidacontrollare.get(x));
                    if (value == null) {
                        value = "";
                    }
                    valori.add(value);
                }
                if (filialedascrivere.getFormula().equals("Y")) {
                    valori.add("");
                    valori.add("");
                    valori.add("");
                    if (filialedascrivere.getBudget() == null) {
                        valori.add("0.00");
                    } else {
                        valori.add(filialedascrivere.getBudget());
                    }
                    valori.add("");
                    valori.add("");
                    if (filialedascrivere.getAnnoprec_adeguato() == null) {
                        valori.add("0.00");
                    } else {
                        valori.add(filialedascrivere.getAnnoprec_adeguato());
                    }
                    valori.add("");
                    if (filialedascrivere.getAnnoprec_alldata() == null) {
                        valori.add("0.00");
                    } else {
                        valori.add(filialedascrivere.getAnnoprec_alldata());
                    }
                    valori.add("");
                    valori.add("");
                    if (filialedascrivere.getYtd_finemese() == null) {
                        valori.add("0.00");
                    } else {
                        valori.add(filialedascrivere.getYtd_finemese());
                    }
                    if (filialedascrivere.getYtd_budget() == null) {
                        valori.add("0.00");
                    } else {
                        valori.add(filialedascrivere.getYtd_budget());
                    }
                    valori.add("");
                    valori.add("");
                    if (filialedascrivere.getAnnoprec_ytd() == null) {
                        valori.add("0.00");
                    } else {
                        valori.add(filialedascrivere.getAnnoprec_ytd());
                    }
                }
            }

            c.setFormula(filialedascrivere.getFormula());
            c.setDesc(filialedascrivere.getDescr());
            c.setValori(valori);
            elencovalori.add(c);
        }

        //FOGLIO 2 MTD
        ArrayList<Riga> elencovaloririgafoglio2 = new ArrayList<>();
        ArrayList<String> valorifoglio2 = new ArrayList<>();
        Riga r = new Riga();
        valorifoglio2.add(valueOf(dtstart.dayOfMonth().getMaximumValue()));
        r.setValori(valorifoglio2);
        r.setFormula("N");
        elencovaloririgafoglio2.add(r);
        r = new Riga();
        valorifoglio2 = new ArrayList<>();
        valorifoglio2.add(valueOf(ultimogiornocontrollato.dayOfMonth().get()));
        r.setValori(valorifoglio2);
        r.setFormula("N");
        elencovaloririgafoglio2.add(r);

        ArrayList<String> valorifoglio1 = new ArrayList<>();
        r = new Riga();
        valorifoglio1.add("");
        valorifoglio1.add("DATI AGGIORNATI AL");
        r.setValori(valorifoglio1);
        r.setFormula("N");
        elencovaloririgafoglio2.add(r);

        valorifoglio1 = new ArrayList<>();
        r = new Riga();
        valorifoglio1.add("");
        valorifoglio1.add(oggi.toString(patternnormdate_filter));
        valorifoglio1.add("GM MTD " + yearstring);
        valorifoglio1.add("GM MTD " + yearstring_prev);
        valorifoglio1.add("DIFF");
        valorifoglio1.add("DIFF %");
        valorifoglio1.add("BDG MTD");
        valorifoglio1.add("DIFF");
        valorifoglio1.add("DIFF %");
        valorifoglio1.add("GM " + yearstring + " PROIEZIONE FINE MESE");
        valorifoglio1.add("BDG MENSILE");
        valorifoglio1.add("DIFF");
        valorifoglio1.add("DIFF %");
        valorifoglio1.add("BUY " + yearstring);
        valorifoglio1.add("BUY " + yearstring_prev);
        valorifoglio1.add("DIFF");
        valorifoglio1.add("BUY DIFF %");
        valorifoglio1.add("C.C. " + yearstring);
        valorifoglio1.add("C.C. " + yearstring_prev);
        valorifoglio1.add("DIFF");
        valorifoglio1.add("C.C. DIFF %");
        valorifoglio1.add("BUY + C.C. " + yearstring);
        valorifoglio1.add("BUY + C.C. " + yearstring_prev);
        valorifoglio1.add("DIFF");
        valorifoglio1.add("BUY + C.C. DIFF %");
        valorifoglio1.add("SELL " + yearstring);
        valorifoglio1.add("SELL " + yearstring_prev);
        valorifoglio1.add("DIFF");
        valorifoglio1.add("SELL DIFF %");
        valorifoglio1.add("TOT VOLUMI " + yearstring);
        valorifoglio1.add("TOT VOLUMI " + yearstring_prev);
        valorifoglio1.add("DIFF");
        valorifoglio1.add("DIFF %");
        valorifoglio1.add("TRANS BUY " + yearstring);
        valorifoglio1.add("TRANS BUY " + yearstring_prev);
        valorifoglio1.add("DIFF %");
        valorifoglio1.add("TRANS C.C. " + yearstring);
        valorifoglio1.add("TRANS C.C. " + yearstring_prev);
        valorifoglio1.add("DIFF %");
        valorifoglio1.add("TRANS BUY + C.C. " + yearstring);
        valorifoglio1.add("TRANS BUY + C.C. " + yearstring_prev);
        valorifoglio1.add("DIFF %");
        valorifoglio1.add("TRANS SELL " + yearstring);
        valorifoglio1.add("TRANS SELL " + yearstring_prev);
        valorifoglio1.add("DIFF %");
        valorifoglio1.add("TRANS TOTAL " + yearstring);
        valorifoglio1.add("TRANS TOTAL " + yearstring_prev);
        valorifoglio1.add("DIFF %");
        valorifoglio1.add("RICAVO MEDIO " + yearstring);
        valorifoglio1.add("RICAVO MEDIO " + yearstring_prev);
        valorifoglio1.add("DIFF %");
        valorifoglio1.add("RICAVO GG " + yearstring);
        valorifoglio1.add("RICAVO GG " + yearstring_prev);
        valorifoglio1.add("DIFF %");
        valorifoglio1.add("RICAVO GG DA BUDGET " + yearstring_prev);
        valorifoglio1.add("DIFF %");
        valorifoglio1.add("% GM TOT " + yearstring);
        valorifoglio1.add("% GM TOT " + yearstring_prev);
        valorifoglio1.add("DIFF %");
        valorifoglio1.add("VOLUME MEDIO " + yearstring);
        valorifoglio1.add("VOLUME MEDIO  " + yearstring_prev);
        valorifoglio1.add("DIFF %");
        valorifoglio1.add("TRANS MEDIE " + yearstring);
        valorifoglio1.add("TRANS MEDIE " + yearstring_prev);
        valorifoglio1.add("DIFF %");
        valorifoglio1.add("COP " + yearstring);
        valorifoglio1.add("FX " + yearstring);
        r.setValori(valorifoglio1);
        r.setFormula("");
        elencovaloririgafoglio2.add(r);

        valorifoglio1 = new ArrayList<>();
        r = new Riga();
        valorifoglio1.add("");
        valorifoglio1.add("");
        r.setValori(valorifoglio1);
        r.setFormula("N");
        elencovaloririgafoglio2.add(r);

        for (int x = 0; x < listdatifoglio2.size(); x++) {

            CambioMTD_YTD fi = listdatifoglio2.get(x);

            valorifoglio1 = new ArrayList<>();
            r = new Riga();

            valorifoglio1.add(fi.getFiliale());
            valorifoglio1.add(fi.getDescr());
            valorifoglio1.add(fi.getMtd_totale());
            valorifoglio1.add(fi.getMtd_totale_prevyear());
            double d10 = fd(fi.getMtd_totale()) - fd(fi.getMtd_totale_prevyear());
            double d10p = divisione_controllozero(d10, fd(fi.getMtd_totale_prevyear()));

            valorifoglio1.add(roundDoubleandFormat(d10, 2));
            valorifoglio1.add(roundDoubleandFormat(d10p, 2));
            valorifoglio1.add(fi.getMtd_bdg());
            double d11 = fd(fi.getMtd_totale()) - fd(fi.getMtd_bdg());
            double d11p = divisione_controllozero(d11, fd(fi.getMtd_bdg()));
            valorifoglio1.add(roundDoubleandFormat(d11, 2));
            valorifoglio1.add(roundDoubleandFormat(d11p, 2));

            valorifoglio1.add(fi.getMtd_proiezione_finemese());
            valorifoglio1.add(fi.getMtd_proiezione_budget_finemese());
            double d12 = fd(fi.getMtd_proiezione_finemese()) - fd(fi.getMtd_proiezione_budget_finemese());
            double d12p = divisione_controllozero(d12, fd(fi.getMtd_proiezione_budget_finemese()));
            valorifoglio1.add(roundDoubleandFormat(d12, 2));
            valorifoglio1.add(roundDoubleandFormat(d12p, 2));

            valorifoglio1.add(fi.getBuy());
            valorifoglio1.add(fi.getBuy_prevyear());
            double d13 = fd(fi.getBuy()) - fd(fi.getBuy_prevyear());
            double d13p = divisione_controllozero(d13, fd(fi.getBuy_prevyear()));
            valorifoglio1.add(roundDoubleandFormat(d13, 2));
            valorifoglio1.add(roundDoubleandFormat(d13p, 2));

            valorifoglio1.add(fi.getCc());
            valorifoglio1.add(fi.getCc_prevyear());
            double d14 = fd(fi.getCc()) - fd(fi.getCc_prevyear());
            double d14p = divisione_controllozero(d14, fd(fi.getCc_prevyear()));
            valorifoglio1.add(roundDoubleandFormat(d14, 2));
            valorifoglio1.add(roundDoubleandFormat(d14p, 2));

            valorifoglio1.add(fi.getBuycc());
            valorifoglio1.add(fi.getBuycc_prevyear());
            double d15 = fd(fi.getBuycc()) - fd(fi.getBuycc_prevyear());
            double d15p = divisione_controllozero(d15, fd(fi.getBuycc_prevyear()));
            valorifoglio1.add(roundDoubleandFormat(d15, 2));
            valorifoglio1.add(roundDoubleandFormat(d15p, 2));

            valorifoglio1.add(fi.getSell());
            valorifoglio1.add(fi.getSell_prevyear());
            double d16 = fd(fi.getSell()) - fd(fi.getSell_prevyear());
            double d16p = divisione_controllozero(d16, fd(fi.getSell_prevyear()));
            valorifoglio1.add(roundDoubleandFormat(d16, 2));
            valorifoglio1.add(roundDoubleandFormat(d16p, 2));

            valorifoglio1.add(fi.getTotvol());
            valorifoglio1.add(fi.getTotvol_prevyear());
            double d17 = fd(fi.getTotvol()) - fd(fi.getTotvol_prevyear());
            double d17p = divisione_controllozero(d17, fd(fi.getTotvol_prevyear()));
            valorifoglio1.add(roundDoubleandFormat(d17, 2));
            valorifoglio1.add(roundDoubleandFormat(d17p, 2));

            valorifoglio1.add(fi.getTrbuy());
            valorifoglio1.add(fi.getTrbuy_prevyear());
            double d18p = (divisione_controllozero(fd(fi.getTrbuy()), fd(fi.getTrbuy_prevyear()))) - 1.00;
            valorifoglio1.add(roundDoubleandFormat(d18p, 2));

            valorifoglio1.add(fi.getTrcctr());
            valorifoglio1.add(fi.getTrcc_prevyear());
            double d19p = (divisione_controllozero(fd(fi.getTrcctr()), fd(fi.getTrcc_prevyear()))) - 1.00;
            valorifoglio1.add(roundDoubleandFormat(d19p, 2));

            valorifoglio1.add(fi.getTrbuycc());
            valorifoglio1.add(fi.getTrbuycc_prevyear());
            double d20p = (divisione_controllozero(fd(fi.getTrbuycc()), fd(fi.getTrbuycc_prevyear()))) - 1.00;
            valorifoglio1.add(roundDoubleandFormat(d20p, 2));

            valorifoglio1.add(fi.getTrsell());
            valorifoglio1.add(fi.getTrsell_prevyear());
            double d21p = (divisione_controllozero(fd(fi.getTrsell()), fd(fi.getTrsell_prevyear()))) - 1.00;
            valorifoglio1.add(roundDoubleandFormat(d21p, 2));

            valorifoglio1.add(fi.getTrtot());
            valorifoglio1.add(fi.getTrtot_prevyear());
            double d22p = (divisione_controllozero(fd(fi.getTrtot()), fd(fi.getTrtot_prevyear()))) - 1.00;
            valorifoglio1.add(roundDoubleandFormat(d22p, 2));

            double d23 = divisione_controllozero(fd(fi.getMtd_totale()), fd(fi.getTrtot()));
            valorifoglio1.add(roundDoubleandFormat(d23, 2));
            double d24 = divisione_controllozero(fd(fi.getMtd_totale_prevyear()), fd(fi.getTrtot_prevyear()));
            valorifoglio1.add(roundDoubleandFormat(d24, 2));
            double d24p = (divisione_controllozero(d23, d24)) - 1.00;
            valorifoglio1.add(roundDoubleandFormat(d24p, 2));

            double d25 = divisione_controllozero(fd(fi.getMtd_totale()), fd(valueOf(ultimogiornocontrollato.dayOfMonth().get())));
            valorifoglio1.add(roundDoubleandFormat(d25, 2));

            double d26 = divisione_controllozero(fd(fi.getMtd_totale_prevyear()), fd(valueOf(ultimogiornocontrollato.dayOfMonth().get())));
            valorifoglio1.add(roundDoubleandFormat(d26, 2));
            double d26p = (divisione_controllozero(d25, d26)) - 1;
            valorifoglio1.add(roundDoubleandFormat(d26p, 2));

            double d27 = divisione_controllozero(fd(fi.getMtd_bdg()), fd(valueOf(dtstart.dayOfMonth().getMaximumValue())));
            valorifoglio1.add(roundDoubleandFormat(d27, 2));
            double d27p = (divisione_controllozero(d25, d27)) - 1.00;
            valorifoglio1.add(roundDoubleandFormat(d27p, 2));
            double d28 = divisione_controllozero(fd(fi.getMtd_totale()), fd(fi.getTrtot()));
            valorifoglio1.add(roundDoubleandFormat(d28, 2));
            double d29 = divisione_controllozero(fd(fi.getMtd_totale_prevyear()), fd(fi.getTrtot_prevyear()));
            valorifoglio1.add(roundDoubleandFormat(d29, 2));
            double d29p = (divisione_controllozero(d28, d29)) - 1.00;
            valorifoglio1.add(roundDoubleandFormat(d29p, 2));

            double d30 = divisione_controllozero(fd(fi.getTotvol()), fd(fi.getTrtot()));
            double d31 = divisione_controllozero(fd(fi.getTotvol_prevyear()), fd(fi.getTrtot_prevyear()));
            double d31p = (divisione_controllozero(d30, d31)) - 1.00;
            valorifoglio1.add(roundDoubleandFormat(d30, 2));
            valorifoglio1.add(roundDoubleandFormat(d31, 2));
            valorifoglio1.add(roundDoubleandFormat(d31p, 2));

            double d32 = divisione_controllozero(fd(fi.getTrtot()), fd(valueOf(ultimogiornocontrollato.dayOfMonth().get())));
            double d33 = divisione_controllozero(fd(fi.getTrtot_prevyear()), fd(valueOf(ultimogiornocontrollato.dayOfMonth().get())));
            double d33p = (divisione_controllozero(d32, d33)) - 1.00;
            valorifoglio1.add(roundDoubleandFormat(d32, 2));
            valorifoglio1.add(roundDoubleandFormat(d33, 2));
            valorifoglio1.add(roundDoubleandFormat(d33p, 2));

            valorifoglio1.add(fi.getCop());
            valorifoglio1.add(fi.getFx());

            r.setValori(valorifoglio1);
            r.setFormula("N");
            elencovaloririgafoglio2.add(r);
        }

        ArrayList<Riga> elencovaloririgafoglio3 = new ArrayList<>();

        ArrayList<String> valorifoglio3 = new ArrayList<>();
        r = new Riga();
        valorifoglio3.add(valueOf(dtstart.dayOfMonth().getMaximumValue()));
        r.setValori(valorifoglio3);
        r.setFormula("N");
        elencovaloririgafoglio3.add(r);

        valorifoglio3 = new ArrayList<>();
        r = new Riga();
        valorifoglio3.add(valueOf(ultimogiornocontrollato.dayOfMonth().get()));
        r.setValori(valorifoglio3);
        r.setFormula("N");
        elencovaloririgafoglio3.add(r);

        valorifoglio3 = new ArrayList<>();
        r = new Riga();
        valorifoglio3.add("");
        valorifoglio3.add("DATI AGGIORNATI AL");
        r.setValori(valorifoglio3);
        r.setFormula("N");
        elencovaloririgafoglio3.add(r);

        valorifoglio3 = new ArrayList<>();
        r = new Riga();
        valorifoglio3.add("");
        valorifoglio3.add(oggi.toString(patternnormdate_filter));
        valorifoglio3.add("GM YTD " + yearstring);
        valorifoglio3.add("GM YTD " + yearstring_prev);
        valorifoglio3.add("DIFF");
        valorifoglio3.add("DIFF %");
        valorifoglio3.add("BDG YTD");
        valorifoglio3.add("DIFF");
        valorifoglio3.add("DIFF %");
        valorifoglio3.add("GM " + yearstring);
        valorifoglio3.add("BDG");
        valorifoglio3.add("DIFF");
        valorifoglio3.add("DIFF %");
        valorifoglio3.add("BUY " + yearstring);
        valorifoglio3.add("BUY " + yearstring_prev);
        valorifoglio3.add("DIFF");
        valorifoglio3.add("BUY DIFF %");

        valorifoglio3.add("C.C. " + yearstring);
        valorifoglio3.add("C.C. " + yearstring_prev);
        valorifoglio3.add("DIFF");
        valorifoglio3.add("C.C. DIFF %");
        valorifoglio3.add("BUY + C.C. " + yearstring);
        valorifoglio3.add("BUY + C.C. " + yearstring_prev);
        valorifoglio3.add("DIFF");
        valorifoglio3.add("BUY + C.C. DIFF %");
        valorifoglio3.add("SELL " + yearstring);
        valorifoglio3.add("SELL " + yearstring_prev);
        valorifoglio3.add("DIFF");
        valorifoglio3.add("SELL DIFF %");
        valorifoglio3.add("TOT VOLUMI " + yearstring);
        valorifoglio3.add("TOT VOLUMI " + yearstring_prev);
        valorifoglio3.add("DIFF");
        valorifoglio3.add("TRANS BUY " + yearstring);
        valorifoglio3.add("TRANS BUY " + yearstring_prev);
        valorifoglio3.add("DIFF %");
        valorifoglio3.add("TRANS C.C. " + yearstring);
        valorifoglio3.add("TRANS C.C. " + yearstring_prev);
        valorifoglio3.add("DIFF %");
        valorifoglio3.add("TRANS BUY + C.C. " + yearstring);
        valorifoglio3.add("TRANS BUY + C.C. " + yearstring_prev);
        valorifoglio3.add("DIFF %");
        valorifoglio3.add("TRANS SELL " + yearstring);
        valorifoglio3.add("TRANS SELL " + yearstring_prev);
        valorifoglio3.add("DIFF %");
        valorifoglio3.add("TRANS TOTAL " + yearstring);
        valorifoglio3.add("TRANS TOTAL " + yearstring_prev);
        valorifoglio3.add("DIFF %");
        valorifoglio3.add("RICAVO MEDIO " + yearstring);
        valorifoglio3.add("RICAVO MEDIO " + yearstring_prev);
        valorifoglio3.add("DIFF %");
        valorifoglio3.add("RICAVO GG " + yearstring);
        valorifoglio3.add("RICAVO GG " + yearstring_prev);
        valorifoglio3.add("DIFF %");
        valorifoglio3.add("RICAVO GG DA BUDGET " + yearstring_prev);
        valorifoglio3.add("DIFF %");
        valorifoglio3.add("% GM TOT " + yearstring);
        valorifoglio3.add("% GM TOT " + yearstring_prev);
        valorifoglio3.add("DIFF %");
        valorifoglio3.add("VOLUME MEDIO " + yearstring);
        valorifoglio3.add("VOLUME MEDIO  " + yearstring_prev);
        valorifoglio3.add("DIFF %");
        valorifoglio3.add("");
        r.setValori(valorifoglio3);
        r.setFormula("");
        elencovaloririgafoglio3.add(r);

        valorifoglio3 = new ArrayList<>();
        r = new Riga();
        valorifoglio3.add("");
        valorifoglio3.add("");
        r.setValori(valorifoglio3);
        r.setFormula("N");
        elencovaloririgafoglio3.add(r);

        for (int x = 0; x < listdatifoglio3.size(); x++) {

            CambioMTD_YTD fi = listdatifoglio3.get(x);

            valorifoglio3 = new ArrayList<>();
            r = new Riga();

            valorifoglio3.add(fi.getFiliale());
            valorifoglio3.add(fi.getDescr());
            valorifoglio3.add(fi.getMtd_totale());
            valorifoglio3.add(fi.getMtd_totale_prevyear());
            double d10 = fd(fi.getMtd_totale()) - fd(fi.getMtd_totale_prevyear());
            double d10p = divisione_controllozero(d10, fd(fi.getMtd_totale_prevyear()));
            valorifoglio3.add(roundDoubleandFormat(d10, 2));
            valorifoglio3.add(roundDoubleandFormat(d10p, 2));
            valorifoglio3.add(fi.getMtd_bdg());
            double d11 = fd(fi.getMtd_totale()) - fd(fi.getMtd_bdg());
            double d11p = divisione_controllozero(d11, fd(fi.getMtd_bdg()));
            valorifoglio3.add(roundDoubleandFormat(d11, 2));
            valorifoglio3.add(roundDoubleandFormat(d11p, 2));

            valorifoglio3.add(fi.getBuy());
            valorifoglio3.add(fi.getBuy_prevyear());
            double d13 = fd(fi.getBuy()) - fd(fi.getBuy_prevyear());
            double d13p = divisione_controllozero(d13, fd(fi.getBuy_prevyear()));
            valorifoglio3.add(roundDoubleandFormat(d13, 2));
            valorifoglio3.add(roundDoubleandFormat(d13p, 2));

            valorifoglio3.add(fi.getCc());
            valorifoglio3.add(fi.getCc_prevyear());
            double d14 = fd(fi.getCc()) - fd(fi.getCc_prevyear());
            double d14p = divisione_controllozero(d14, fd(fi.getCc_prevyear()));
            valorifoglio3.add(roundDoubleandFormat(d14, 2));
            valorifoglio3.add(roundDoubleandFormat(d14p, 2));

            valorifoglio3.add(fi.getBuycc());
            valorifoglio3.add(fi.getBuycc_prevyear());
            double d15 = fd(fi.getBuycc()) - fd(fi.getBuycc_prevyear());
            double d15p = divisione_controllozero(d15, fd(fi.getBuycc_prevyear()));
            valorifoglio3.add(roundDoubleandFormat(d15, 2));
            valorifoglio3.add(roundDoubleandFormat(d15p, 2));

            valorifoglio3.add(fi.getSell());
            valorifoglio3.add(fi.getSell_prevyear());
            double d16 = fd(fi.getSell()) - fd(fi.getSell_prevyear());
            double d16p = divisione_controllozero(d16, fd(fi.getSell_prevyear()));
            valorifoglio3.add(roundDoubleandFormat(d16, 2));
            valorifoglio3.add(roundDoubleandFormat(d16p, 2));

            valorifoglio3.add(fi.getTotvol());
            valorifoglio3.add(fi.getTotvol_prevyear());
            double d17 = fd(fi.getTotvol()) - fd(fi.getTotvol_prevyear());
            double d17p = divisione_controllozero(d17, fd(fi.getTotvol_prevyear()));
            valorifoglio3.add(roundDoubleandFormat(d17, 2));
            valorifoglio3.add(roundDoubleandFormat(d17p, 2));

            valorifoglio3.add(fi.getTrbuy());
            valorifoglio3.add(fi.getTrbuy_prevyear());
            double d18p = (divisione_controllozero(fd(fi.getTrbuy()), fd(fi.getTrbuy_prevyear()))) - 1.00;
            valorifoglio3.add(roundDoubleandFormat(d18p, 2));

            valorifoglio3.add(fi.getTrcctr());
            valorifoglio3.add(fi.getTrcc_prevyear());
            double d19p = (divisione_controllozero(fd(fi.getTrcctr()), fd(fi.getTrcc_prevyear()))) - 1.00;
            valorifoglio3.add(roundDoubleandFormat(d19p, 2));

            valorifoglio3.add(fi.getTrbuycc());
            valorifoglio3.add(fi.getTrbuycc_prevyear());
            double d20p = (divisione_controllozero(fd(fi.getTrbuycc()), fd(fi.getTrbuycc_prevyear()))) - 1.00;
            valorifoglio3.add(roundDoubleandFormat(d20p, 2));

            valorifoglio3.add(fi.getTrsell());
            valorifoglio3.add(fi.getTrsell_prevyear());
            double d21p = (divisione_controllozero(fd(fi.getTrsell()), fd(fi.getTrsell_prevyear()))) - 1.00;
            valorifoglio3.add(roundDoubleandFormat(d21p, 2));

            valorifoglio3.add(fi.getTrtot());
            valorifoglio3.add(fi.getTrtot_prevyear());
            double d22p = (divisione_controllozero(fd(fi.getTrtot()), fd(fi.getTrtot_prevyear()))) - 1.00;
            valorifoglio3.add(roundDoubleandFormat(d22p, 2));

            double d23 = divisione_controllozero(fd(fi.getMtd_totale()), fd(fi.getTrtot()));
            valorifoglio3.add(roundDoubleandFormat(d23, 2));
            double d24 = divisione_controllozero(fd(fi.getMtd_totale_prevyear()), fd(fi.getTrtot_prevyear()));
            valorifoglio3.add(roundDoubleandFormat(d24, 2));
            double d24p = (divisione_controllozero(d23, d24)) - 1.00;
            valorifoglio3.add(roundDoubleandFormat(d24p, 2));

            double d25 = divisione_controllozero(fd(fi.getMtd_totale()), fd(valueOf(ultimogiornocontrollato.dayOfMonth().get())));
            valorifoglio3.add(roundDoubleandFormat(d25, 2));

            double d26 = divisione_controllozero(fd(fi.getMtd_totale_prevyear()), fd(valueOf(ultimogiornocontrollato.dayOfMonth().get())));
            valorifoglio3.add(roundDoubleandFormat(d26, 2));
            double d26p = (divisione_controllozero(d25, d26)) - 1;
            valorifoglio3.add(roundDoubleandFormat(d26p, 2));

            double d27 = divisione_controllozero(fd(fi.getMtd_bdg()), fd(valueOf(dtstart.dayOfMonth().getMaximumValue())));
            valorifoglio3.add(roundDoubleandFormat(d27, 2));
            double d27p = (divisione_controllozero(d25, d27)) - 1.00;
            valorifoglio3.add(roundDoubleandFormat(d27p, 2));
            double d28 = divisione_controllozero(fd(fi.getMtd_totale()), fd(fi.getTrtot()));
            valorifoglio3.add(roundDoubleandFormat(d28, 2));
            double d29 = divisione_controllozero(fd(fi.getMtd_totale_prevyear()), fd(fi.getTrtot_prevyear()));
            valorifoglio3.add(roundDoubleandFormat(d29, 2));
            double d29p = (divisione_controllozero(d28, d29)) - 1.00;
            valorifoglio3.add(roundDoubleandFormat(d29p, 2));

            double d30 = divisione_controllozero(fd(fi.getTotvol()), fd(fi.getTrtot()));
            double d31 = divisione_controllozero(fd(fi.getTotvol_prevyear()), fd(fi.getTrtot_prevyear()));
            double d31p = (divisione_controllozero(d30, d31)) - 1.00;
            valorifoglio3.add(roundDoubleandFormat(d30, 2));
            valorifoglio3.add(roundDoubleandFormat(d31, 2));
            valorifoglio3.add(roundDoubleandFormat(d31p, 2));

            double d32 = divisione_controllozero(fd(fi.getTrtot()), fd(valueOf(ultimogiornocontrollato.dayOfMonth().get())));
            double d33 = divisione_controllozero(fd(fi.getTrtot_prevyear()), fd(valueOf(ultimogiornocontrollato.dayOfMonth().get())));
            double d33p = (divisione_controllozero(d32, d33)) - 1.00;
            valorifoglio3.add(roundDoubleandFormat(d32, 2));
            valorifoglio3.add(roundDoubleandFormat(d33, 2));
            valorifoglio3.add(roundDoubleandFormat(d33p, 2));

            r.setValori(valorifoglio3);
            r.setFormula("N");
            elencovaloririgafoglio3.add(r);
        }

        ArrayList<Riga> elencovaloririgafoglio4 = new ArrayList<>();

        ArrayList<String> valorifoglio4 = new ArrayList<>();
        r = new Riga();
        valorifoglio4.add("");
        r.setValori(valorifoglio4);
        r.setFormula("N");
        elencovaloririgafoglio4.add(r);

        valorifoglio4 = new ArrayList<>();
        r = new Riga();
        valorifoglio4.add("");
        r.setValori(valorifoglio4);
        r.setFormula("N");
        elencovaloririgafoglio4.add(r);

        valorifoglio4 = new ArrayList<>();
        r = new Riga();
        valorifoglio4.add("");
        valorifoglio4.add("DATI AGGIORNATI AL");
        r.setValori(valorifoglio4);
        r.setFormula("N");
        elencovaloririgafoglio4.add(r);

        valorifoglio4 = new ArrayList<>();
        r = new Riga();
        valorifoglio4.add("");
        valorifoglio4.add(oggi.toString(patternnormdate_filter));
        valorifoglio4.add("WU VOLUME SEND " + yearstring);
        valorifoglio4.add("WU VOLUME SEND " + yearstring_prev);
        valorifoglio4.add("DIFF %");
        valorifoglio4.add("WU VOLUME RECEIVE " + yearstring);
        valorifoglio4.add("WU VOLUME RECEIVE " + yearstring_prev);
        valorifoglio4.add("DIFF %");
        valorifoglio4.add("WU VOLUME TOT " + yearstring);
        valorifoglio4.add("WU VOLUME TOT " + yearstring_prev);
        valorifoglio4.add("DIFF %");
        valorifoglio4.add("TRANS WU SEND " + yearstring);
        valorifoglio4.add("TRANS WU SEND " + yearstring_prev);
        valorifoglio4.add("DIFF %");
        valorifoglio4.add("TRANS WU RECEIVE " + yearstring);
        valorifoglio4.add("TRANS WU RECEIVE " + yearstring_prev);
        valorifoglio4.add("DIFF %");
        valorifoglio4.add("TRANS WU TOTALI " + yearstring);
        valorifoglio4.add("TRANS WU TOTALI " + yearstring_prev);
        valorifoglio4.add("DIFF %");

        r.setValori(valorifoglio4);
        r.setFormula("");
        elencovaloririgafoglio4.add(r);

        valorifoglio4 = new ArrayList<>();
        r = new Riga();
        valorifoglio4.add("");
        valorifoglio4.add("");
        r.setValori(valorifoglio4);
        r.setFormula("N");
        elencovaloririgafoglio4.add(r);

        for (int x = 0; x < listdatifoglio4.size(); x++) {
            DatiWU_MTD_YTD wu1 = listdatifoglio4.get(x);
            valorifoglio4 = new ArrayList<>();
            r = new Riga();

            valorifoglio4.add(wu1.getFiliale());
            valorifoglio4.add(wu1.getDescr());

            valorifoglio4.add(wu1.getVolumesend());
            valorifoglio4.add(wu1.getVolumesend_prevyear());
            double p1 = (divisione_controllozero(fd(wu1.getVolumesend()), fd(wu1.getVolumesend_prevyear()))) - 1.0;
            valorifoglio4.add(roundDoubleandFormat(p1, 2));

            valorifoglio4.add(wu1.getVolumerec());
            valorifoglio4.add(wu1.getVolumerec_prevyear());
            double p2 = (divisione_controllozero(fd(wu1.getVolumerec()), fd(wu1.getVolumerec_prevyear()))) - 1.0;
            valorifoglio4.add(roundDoubleandFormat(p2, 2));

            valorifoglio4.add(wu1.getVolumetot());
            valorifoglio4.add(wu1.getVolumetot_prevyear());
            double p3 = (divisione_controllozero(fd(wu1.getVolumetot()), fd(wu1.getVolumetot_prevyear()))) - 1.0;
            valorifoglio4.add(roundDoubleandFormat(p3, 2));

            valorifoglio4.add(wu1.getTrsend());
            valorifoglio4.add(wu1.getTrsend_prevyear());
            double p4 = (divisione_controllozero(fd(wu1.getTrsend()), fd(wu1.getTrsend_prevyear()))) - 1.0;
            valorifoglio4.add(roundDoubleandFormat(p4, 2));

            valorifoglio4.add(wu1.getTrrec());
            valorifoglio4.add(wu1.getTrrec_prevyear());
            double p5 = (divisione_controllozero(fd(wu1.getTrrec()), fd(wu1.getTrrec_prevyear()))) - 1.0;
            valorifoglio4.add(roundDoubleandFormat(p5, 2));

            valorifoglio4.add(wu1.getTrtot());
            valorifoglio4.add(wu1.getTrtot_prevyear());
            double p6 = (divisione_controllozero(fd(wu1.getTrtot()), fd(wu1.getTrtot_prevyear()))) - 1.0;
            valorifoglio4.add(roundDoubleandFormat(p6, 2));

            r.setValori(valorifoglio4);
            r.setFormula("N");
            elencovaloririgafoglio4.add(r);

        }

        ArrayList<Riga> elencovaloririgafoglio5 = new ArrayList<>();

        ArrayList<String> valorifoglio5 = new ArrayList<>();
        r = new Riga();
        valorifoglio5.add("");
        r.setValori(valorifoglio5);
        r.setFormula("N");
        elencovaloririgafoglio5.add(r);

        valorifoglio5 = new ArrayList<>();
        r = new Riga();
        valorifoglio5.add("");
        r.setValori(valorifoglio5);
        r.setFormula("N");
        elencovaloririgafoglio5.add(r);

        valorifoglio5 = new ArrayList<>();
        r = new Riga();
        valorifoglio5.add("");
        valorifoglio5.add("DATI DA GEN AL");
        r.setValori(valorifoglio5);
        r.setFormula("N");
        elencovaloririgafoglio5.add(r);

        valorifoglio5 = new ArrayList<>();
        r = new Riga();
        valorifoglio5.add("");
        valorifoglio5.add(oggi.toString(patternnormdate_filter));
        valorifoglio5.add("WU VOLUME SEND " + yearstring);
        valorifoglio5.add("WU VOLUME SEND " + yearstring_prev);
        valorifoglio5.add("DIFF %");
        valorifoglio5.add("WU VOLUME RECEIVE " + yearstring);
        valorifoglio5.add("WU VOLUME RECEIVE " + yearstring_prev);
        valorifoglio5.add("DIFF %");
        valorifoglio5.add("WU VOLUME TOT " + yearstring);
        valorifoglio5.add("WU VOLUME TOT " + yearstring_prev);
        valorifoglio5.add("DIFF %");
        valorifoglio5.add("TRANS WU SEND " + yearstring);
        valorifoglio5.add("TRANS WU SEND " + yearstring_prev);
        valorifoglio5.add("DIFF %");
        valorifoglio5.add("TRANS WU RECEIVE " + yearstring);
        valorifoglio5.add("TRANS WU RECEIVE " + yearstring_prev);
        valorifoglio5.add("DIFF %");
        valorifoglio5.add("TRANS WU TOTALI " + yearstring);
        valorifoglio5.add("TRANS WU TOTALI " + yearstring_prev);
        valorifoglio5.add("DIFF %");

        r.setValori(valorifoglio5);
        r.setFormula("");
        elencovaloririgafoglio5.add(r);

        valorifoglio5 = new ArrayList<>();
        r = new Riga();
        valorifoglio5.add("");
        valorifoglio5.add("");
        r.setValori(valorifoglio5);
        r.setFormula("N");
        elencovaloririgafoglio5.add(r);

        for (int x = 0; x < listdatifoglio5.size(); x++) {
            DatiWU_MTD_YTD wu1 = listdatifoglio5.get(x);
            valorifoglio5 = new ArrayList<>();
            r = new Riga();

            valorifoglio5.add(wu1.getFiliale());
            valorifoglio5.add(wu1.getDescr());

            valorifoglio5.add(wu1.getVolumesend());
            valorifoglio5.add(wu1.getVolumesend_prevyear());
            double p1 = (divisione_controllozero(fd(wu1.getVolumesend()), fd(wu1.getVolumesend_prevyear()))) - 1.0;
            valorifoglio5.add(roundDoubleandFormat(p1, 2));

            valorifoglio5.add(wu1.getVolumerec());
            valorifoglio5.add(wu1.getVolumerec_prevyear());
            double p2 = (divisione_controllozero(fd(wu1.getVolumerec()), fd(wu1.getVolumerec_prevyear()))) - 1.0;
            valorifoglio5.add(roundDoubleandFormat(p2, 2));

            valorifoglio5.add(wu1.getVolumetot());
            valorifoglio5.add(wu1.getVolumetot_prevyear());
            double p3 = (divisione_controllozero(fd(wu1.getVolumetot()), fd(wu1.getVolumetot_prevyear()))) - 1.0;
            valorifoglio5.add(roundDoubleandFormat(p3, 2));

            valorifoglio5.add(wu1.getTrsend());
            valorifoglio5.add(wu1.getTrsend_prevyear());
            double p4 = (divisione_controllozero(fd(wu1.getTrsend()), fd(wu1.getTrsend_prevyear()))) - 1.0;
            valorifoglio5.add(roundDoubleandFormat(p4, 2));

            valorifoglio5.add(wu1.getTrrec());
            valorifoglio5.add(wu1.getTrrec_prevyear());
            double p5 = (divisione_controllozero(fd(wu1.getTrrec()), fd(wu1.getTrrec_prevyear()))) - 1.0;
            valorifoglio5.add(roundDoubleandFormat(p5, 2));

            valorifoglio5.add(wu1.getTrtot());
            valorifoglio5.add(wu1.getTrtot_prevyear());
            double p6 = (divisione_controllozero(fd(wu1.getTrtot()), fd(wu1.getTrtot_prevyear()))) - 1.0;
            valorifoglio5.add(roundDoubleandFormat(p6, 2));

            r.setValori(valorifoglio5);
            r.setFormula("N");
            elencovaloririgafoglio5.add(r);

        }

        ArrayList<Riga> elencovaloririgafoglio6 = new ArrayList<>();
        ArrayList<String> valorifoglio6 = new ArrayList<>();
        r = new Riga();
        valorifoglio6.add(valueOf(dtstart.dayOfMonth().getMaximumValue()));
        r.setValori(valorifoglio6);
        r.setFormula("N");
        elencovaloririgafoglio6.add(r);

        valorifoglio6 = new ArrayList<>();
        r = new Riga();
        valorifoglio6.add(valueOf(ultimogiornocontrollato.dayOfMonth().get()));

        ArrayList<NC_category> listnccat = all_nc_category();
        for (int c1 = 0; c1 < listcat_mtd.size(); c1++) {
            valorifoglio6.add("");
            valorifoglio6.add("CAUSALE VENDITA PER " + getNC_category(listnccat, listcat_mtd.get(c1)).getDe_gruppo_nc());
            valorifoglio6.add("");
            valorifoglio6.add("");
            valorifoglio6.add("");
            valorifoglio6.add("");
        }
        r.setValori(valorifoglio6);
        r.setFormula("N");
        elencovaloririgafoglio6.add(r);

        valorifoglio6 = new ArrayList<>();
        r = new Riga();

        valorifoglio6.add("");
        valorifoglio6.add("DATI AGGIORNATI AL");
        r.setValori(valorifoglio6);
        r.setFormula("N");
        elencovaloririgafoglio6.add(r);

        valorifoglio6 = new ArrayList<>();
        r = new Riga();
        valorifoglio6.add("");
        valorifoglio6.add(oggi.toString(patternnormdate_filter));
        r.setValori(valorifoglio6);
        r.setFormula("");
        elencovaloririgafoglio6.add(r);

        valorifoglio6 = new ArrayList<>();
        r = new Riga();
        valorifoglio6.add("");
        valorifoglio6.add("");
        for (String listcat_mtd1 : listcat_mtd) {
            valorifoglio6.add("VOL. " + yearstring);
            valorifoglio6.add("VOL. " + yearstring_prev);
            valorifoglio6.add("DIFF. %");
            valorifoglio6.add("TRANS. " + yearstring);
            valorifoglio6.add("TRANS. " + yearstring_prev);
            valorifoglio6.add("DIFF. %");
        }
        r.setValori(valorifoglio6);
        r.setFormula("N");
        elencovaloririgafoglio6.add(r);

        for (int e = 0; e < filiali_mtd.size(); e++) {
            valorifoglio6 = new ArrayList<>();
            r = new Riga();
            String fil = filiali_mtd.get(e);
            Branch b1 = get_Branch(fil, allbr);
            valorifoglio6.add(fil);
            valorifoglio6.add(b1.getDe_branch());
            for (int c1 = 0; c1 < listcat_mtd.size(); c1++) {
                String cat = listcat_mtd.get(c1);
                double v1 = 0.00;
                double v2 = 0.00;
                double t1 = 0.00;
                double t2 = 0.00;
                for (int i = 0; i < nc_mtd.size(); i++) {
                    NC_val vv = nc_mtd.get(i);
                    if (vv.getFiliale().equals(fil) && vv.getCat().equals(cat)) {
                        if (vv.getYear().equals(yearstring)) {
                            v1 = vv.getTot();
                            t1 = vv.getQuantity();
                        } else if (vv.getYear().equals(yearstring_prev)) {
                            v2 = vv.getTot();
                            t2 = vv.getQuantity();
                        }
                    }
                }
                valorifoglio6.add(roundDoubleandFormat(v1, 2));
                valorifoglio6.add(roundDoubleandFormat(v2, 2));
                double p1 = (divisione_controllozero(v1, v2)) - 1.0;
                valorifoglio6.add(roundDoubleandFormat(p1, 2));
                valorifoglio6.add(roundDoubleandFormat(t1, 2));
                valorifoglio6.add(roundDoubleandFormat(t2, 2));
                double p2 = (divisione_controllozero(t1, t2)) - 1.0;
                valorifoglio6.add(roundDoubleandFormat(p2, 2));
            }
            r.setValori(valorifoglio6);
            r.setFormula("N");
            elencovaloririgafoglio6.add(r);
        }

        ArrayList<Riga> elencovaloririgafoglio7 = new ArrayList<>();
        ArrayList<String> valorifoglio7 = new ArrayList<>();
        r = new Riga();
        valorifoglio7.add(valueOf(dtstart.dayOfMonth().getMaximumValue()));
        r.setValori(valorifoglio7);
        r.setFormula("N");
        elencovaloririgafoglio7.add(r);

        valorifoglio7 = new ArrayList<>();
        r = new Riga();
        valorifoglio7.add(valueOf(ultimogiornocontrollato.dayOfMonth().get()));

        for (int c1 = 0; c1 < listcat_ytd.size(); c1++) {
            valorifoglio7.add("");
            valorifoglio7.add("CAUSALE VENDITA PER " + getNC_category(listnccat, listcat_ytd.get(c1)).getDe_gruppo_nc());
            valorifoglio7.add("");
            valorifoglio7.add("");
            valorifoglio7.add("");
            valorifoglio7.add("");
        }
        r.setValori(valorifoglio7);
        r.setFormula("N");
        elencovaloririgafoglio7.add(r);

        valorifoglio7 = new ArrayList<>();
        r = new Riga();

        valorifoglio7.add("");
        valorifoglio7.add("DATI AGGIORNATI AL");
        r.setValori(valorifoglio7);
        r.setFormula("N");
        elencovaloririgafoglio7.add(r);

        valorifoglio7 = new ArrayList<>();
        r = new Riga();
        valorifoglio7.add("");
        valorifoglio7.add(oggi.toString(patternnormdate_filter));
        r.setValori(valorifoglio7);
        r.setFormula("");
        elencovaloririgafoglio7.add(r);

        valorifoglio7 = new ArrayList<>();
        r = new Riga();
        valorifoglio7.add("");
        valorifoglio7.add("");
        for (String listcat_ytd1 : listcat_ytd) {
            valorifoglio7.add("VOL. " + yearstring);
            valorifoglio7.add("VOL. " + yearstring_prev);
            valorifoglio7.add("DIFF. %");
            valorifoglio7.add("TRANS. " + yearstring);
            valorifoglio7.add("TRANS. " + yearstring_prev);
            valorifoglio7.add("DIFF. %");
        }
        r.setValori(valorifoglio7);
        r.setFormula("N");
        elencovaloririgafoglio7.add(r);

        for (int e = 0; e < filiali_ytd.size(); e++) {
            valorifoglio7 = new ArrayList<>();
            r = new Riga();
            String fil = filiali_ytd.get(e);
            Branch b1 = get_Branch(fil, allbr);
            valorifoglio7.add(fil);
            valorifoglio7.add(b1.getDe_branch());
            for (int c1 = 0; c1 < listcat_ytd.size(); c1++) {
                String cat = listcat_ytd.get(c1);
                double v1 = 0.00;
                double v2 = 0.00;
                double t1 = 0.00;
                double t2 = 0.00;
                for (int i = 0; i < nc_ytd.size(); i++) {
                    NC_val vv = nc_ytd.get(i);
                    if (vv.getFiliale().equals(fil) && vv.getCat().equals(cat)) {
                        if (vv.getYear().equals(yearstring)) {
                            v1 = vv.getTot();
                            t1 = vv.getQuantity();
                        } else if (vv.getYear().equals(yearstring_prev)) {
                            v2 = vv.getTot();
                            t2 = vv.getQuantity();
                        }
                    }
                }
                valorifoglio7.add(roundDoubleandFormat(v1, 2));
                valorifoglio7.add(roundDoubleandFormat(v2, 2));
                double p1 = (divisione_controllozero(v1, v2)) - 1.0;
                valorifoglio7.add(roundDoubleandFormat(p1, 2));
                valorifoglio7.add(roundDoubleandFormat(t1, 2));
                valorifoglio7.add(roundDoubleandFormat(t2, 2));
                double p2 = (divisione_controllozero(t1, t2)) - 1.0;
                valorifoglio7.add(roundDoubleandFormat(p2, 2));
            }
            r.setValori(valorifoglio7);
            r.setFormula("N");
            elencovaloririgafoglio7.add(r);
        }

        String base64 = Utility.sanitize(giornalieroCG(pathtemp, dtstart.dayOfMonth().getMaximumValue(), ultimogiornocontrollato.dayOfMonth().get(),
                elencovalori, elencovaloririgafoglio2, elencovaloririgafoglio3,
                elencovaloririgafoglio4, elencovaloririgafoglio5,
                elencovaloririgafoglio6, elencovaloririgafoglio7));

        if (base64 != null) {
            String headerKey = "Content-Disposition";
            String sanitizedmese = Utility.sanitize(mese);
            String headerValue = format("attachment; filename=\"%s\"", new Object[]{"Controllodigestione_Giornaliero_" + sanitizedmese + ".xlsx"});
            response.setHeader(headerKey, headerValue);
            try (OutputStream outStream = response.getOutputStream()) {
                outStream.write(decodeBase64(base64.getBytes()));
            }
        } else {
            redirect(request, response, "page_404.html");
        }
    }

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
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

            String user = (String) request.getSession().getAttribute("us_cod");
            insertTR("W", user, "Generate report (GESTIONE) code: " + type);

            switch (type) {
                case "giornaliero":
                    giornaliero(request, response);
                    //giornalieroMOD(request, response);
                    break;
                case "change1":
                    change1(request, response);
                    break;
                case "change":
                    change(request, response);
                    break;
                case "massimale":
                    massimale(request, response);
                    break;
                case "budget":
                    budget(request, response);
                    break;
                case "citta":
                    citta(request, response);
                    break;
                case "redemption":
                    redemption(request, response);
                    break;
                case "online":
                    online(request, response);
                    break;
                case "fasceorarie":
                    fasceorarie(request, response);
                    break;
                case "buyback":
                    buyback(request, response);
                    break;
                case "contabilitauk":
                    contabilitauk(request, response);
                    break;
                case "operatore":
                    operatore(request, response);
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
