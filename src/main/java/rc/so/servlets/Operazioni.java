package rc.so.servlets;

import static com.google.common.base.Splitter.on;
import com.google.gson.Gson;
import rc.so.atlante.Atl_dati_clienti;
import rc.so.atlante.Atl_dati_fatture;
import rc.so.atlante.Db_ATL;
import rc.so.db.Db_Loy;
import rc.so.db.Db_Master;
import rc.so.entity.Branch;
import rc.so.entity.Brand;
import rc.so.entity.CashAD_CZ;
import rc.so.entity.Ch_transaction;
import rc.so.entity.Ch_transaction_doc;
import rc.so.entity.Ch_transaction_refund;
import rc.so.entity.Ch_transaction_value;
import rc.so.entity.Client;
import rc.so.entity.Currency;
import rc.so.entity.CustomerKind;
import rc.so.entity.ET_change;
import rc.so.entity.IT_change;
import rc.so.entity.NC_category;
import rc.so.entity.NC_causal;
import rc.so.entity.NC_transaction;
import rc.so.entity.Newsletters;
import rc.so.entity.Openclose;
import rc.so.entity.Outputf;
import rc.so.entity.Paymat_conf;
import rc.so.entity.Real_oc_change;
import rc.so.entity.Real_oc_nochange;
import rc.so.entity.Real_oc_pos;
import rc.so.entity.Scontrino_Pa;
import rc.so.entity.Sizecuts;
import rc.so.entity.Stock;
import rc.so.entity.Stock_report;
import rc.so.entity.Taglio;
import rc.so.entity.Ticket;
import rc.so.entity.Till;
import rc.so.entity.Users;
import rc.so.entity.VATcode;
import rc.so.entity.VerificaRicarica;
import rc.so.esolver.Client_at;
import rc.so.esolver.Client_es;
import static rc.so.pdf.Pdf.refund;
import static rc.so.pdf.Pdf.scontrino_paymat;
import rc.so.pdf.Receipt;
import static rc.so.rest.Antiriciclaggio.anagrafica;
import static rc.so.rest.Antiriciclaggio.registrazione;
import static rc.so.rest.Monitor.uploadMonitor;
import rc.so.rest.Paymat_new;
import static rc.so.rest.Sign.verify_document;
import static rc.so.rest.Tangerine.login_TA;
import static rc.so.util.Constant.decimal;
import static rc.so.util.Constant.is_CZ;
import static rc.so.util.Constant.is_IT;
import static rc.so.util.Constant.is_UK;
import static rc.so.util.Constant.patterndir;
import static rc.so.util.Constant.patternnormdate;
import static rc.so.util.Constant.patternnormdate_filter;
import static rc.so.util.Constant.patternsql;
import static rc.so.util.Constant.patternsqldate;
import static rc.so.util.Constant.patternyear;
import rc.so.util.Engine;
import static rc.so.util.Engine.checkDeleteTR;
import static rc.so.util.Engine.delete_ET_change;
import static rc.so.util.Engine.delete_IT_change_temp;
import static rc.so.util.Engine.delete_IT_nochange;
import static rc.so.util.Engine.delete_IT_nochange_temp;
import static rc.so.util.Engine.delete_oc;
import static rc.so.util.Engine.formatBankBranchReport;
import static rc.so.util.Engine.getConf;
import static rc.so.util.Engine.getContainsTill;
import static rc.so.util.Engine.getCurrency;
import static rc.so.util.Engine.getFil;
import static rc.so.util.Engine.getFilialefromCod_ETchange;
import static rc.so.util.Engine.getIdfromCod_ETchange;
import static rc.so.util.Engine.getLink_last;
import static rc.so.util.Engine.getNow;
import static rc.so.util.Engine.get_ET_change;
import static rc.so.util.Engine.get_ET_change_tg;
import static rc.so.util.Engine.get_ET_change_value;
import static rc.so.util.Engine.get_ET_nochange_value;
import static rc.so.util.Engine.get_codclient;
import static rc.so.util.Engine.get_internal_transfer;
import static rc.so.util.Engine.get_internal_transfer_ch_tg;
import static rc.so.util.Engine.get_internal_transfer_ch_value;
import static rc.so.util.Engine.get_internal_transfer_noch_value;
import static rc.so.util.Engine.get_spread;
import static rc.so.util.Engine.insertTR;
import static rc.so.util.Engine.insert_excel_upl;
import static rc.so.util.Engine.insert_excel_upl_SP;
import static rc.so.util.Engine.insert_transaction_doc;
import static rc.so.util.Engine.insert_transaction_doc_FILIALE;
import static rc.so.util.Engine.kind_figures_openclose;
import static rc.so.util.Engine.list_ALL_till_enabled;
import static rc.so.util.Engine.list_CA_CZ;
import static rc.so.util.Engine.list_oc_nochange_real;
import static rc.so.util.Engine.list_till_status;
import static rc.so.util.Engine.query_Client_transaction;
import static rc.so.util.Engine.query_LOY_transaction;
import static rc.so.util.Engine.query_transaction_ch_temp;
import static rc.so.util.Engine.rate_range_enabled;
import static rc.so.util.Engine.remove_loy_assign_first_NEW;
import static rc.so.util.Engine.scalareErroridaStock;
import static rc.so.util.Engine.verifySession;
import rc.so.util.Utility;
import static rc.so.util.Utility.checkNumber;
import static rc.so.util.Utility.checkPDF;
import static rc.so.util.Utility.fd;
import static rc.so.util.Utility.formatDoubleforMysql;
import static rc.so.util.Utility.formatMysqltoDisplay;
import static rc.so.util.Utility.formatStringtoStringDate;
import static rc.so.util.Utility.formatStringtoStringDate_null;
import static rc.so.util.Utility.generaId;
import static rc.so.util.Utility.getControvalore;
import static rc.so.util.Utility.getControvaloreOFFSET;
import static rc.so.util.Utility.getDT;
import static rc.so.util.Utility.getStringBase64;
import static rc.so.util.Utility.getStringUTF8;
import static rc.so.util.Utility.getValue_request;
import static rc.so.util.Utility.parseDoubleR;
import static rc.so.util.Utility.parseIntR;
import static rc.so.util.Utility.redirect;
import static rc.so.util.Utility.removeLast;
import static rc.so.util.Utility.roundDoubleandFormat;
import static rc.so.util.Utility.sendPOS_transaction_new;
import static rc.so.util.Utility.sleeping;
import static rc.so.util.Utility.zipListFiles;
import java.io.File;
import static java.io.File.separator;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import static java.lang.Boolean.valueOf;
import static java.lang.Integer.parseInt;
import static java.lang.String.format;
import static java.lang.String.valueOf;
import static java.lang.Thread.currentThread;
import static java.lang.Thread.sleep;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.joda.time.DateTime;
import static java.nio.file.Files.probeContentType;
import static java.util.Collections.sort;
import static java.util.UUID.randomUUID;
import static java.util.stream.Collectors.toList;
import static org.apache.commons.codec.binary.Base64.decodeBase64;
import static org.apache.commons.codec.binary.Base64.encodeBase64;
import static org.apache.commons.fileupload.servlet.ServletFileUpload.isMultipartContent;
import static org.apache.commons.io.FileUtils.readFileToByteArray;
import static org.apache.commons.io.FilenameUtils.getExtension;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;
import static org.apache.commons.lang3.StringUtils.replace;
import static org.apache.commons.lang3.StringUtils.substring;

/**
 *
 * @author rcosco
 */
public class Operazioni extends HttpServlet {

    private static final String filiale = getFil()[0];

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void verificaeurotill(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Db_Master db = new Db_Master();
        String[] loc_cur = db.get_local_currency();
        NC_causal nc2 = db.get_nc_causal(request.getParameter("nc_caus1"));
        String inout = db.getFg_inout_ncde(nc2.getFg_tipo_transazione_nc(), nc2.getNc_de());
        ArrayList<String[]> array_list_oc_change = db.list_oc_change_real(request.getParameter("id_open_till"));
        db.closeDB();
        String disponibile = "0.00";
        try (PrintWriter out = response.getWriter()) {
            if (!nc2.getDe_causale_nc().toLowerCase().contains("acquisto")
                    && !nc2.getDe_causale_nc().toLowerCase().contains("buy")) {
                if (inout.equals("2") || inout.equals("4")) {
                    String richiesto = formatDoubleforMysql(request.getParameter("total1"));
                    for (int i = 0; i < array_list_oc_change.size(); i++) {
                        if (array_list_oc_change.get(i)[1].equals("01") && array_list_oc_change.get(i)[2].equalsIgnoreCase(loc_cur[0])) {
                            disponibile = array_list_oc_change.get(i)[3];
                        }
                    }
                    if (fd(richiesto) > fd(disponibile)) {
                        out.print("false");
                    } else {
                        out.print("true");
                    }
                } else {
                    out.print("true");
                }
            } else {
                out.print("true");
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
    protected void transaction_NC(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

//        Utility.printRequest(request);
//        if (true) {
//            return;
//        }
        String user = (String) request.getSession().getAttribute("us_cod");
        if (user == null) {
            user = "9999";
        }
        String ok = "0";

        Db_Master db = new Db_Master();
        ArrayList<Till> list_till = db.list_ALLtill();
        String pathtemp = db.getPath("temp");
        String[] loc_cur = db.get_local_currency();
        db.closeDB();

        String dateoper = new DateTime().toString(patternsqldate);
        String cod = "NC" + generaId(23);
        boolean isMultipart = isMultipartContent(request);
        File nomefile = null;
        String extacc = ".pdf,.jpeg.jpg";
        String extfile = "-";
        String nc_id_open_till = "";
        String nc_till = "";
        String nc_cat = "";
        String nc_caus = "";
        String nc_mtcn = "";
        String nc_wunet = "";
        String nc_wucom = "";
        String nc_recei = "";
        String nc_quantity = "";
        String nc_price = "";
        String nc_rights = "";
        String nc_tickfee = "";
        String nc_tickfee_t = "";

        String nc_total = "";
        String nc_total_sign = "";
        String nc_ass_idcode = "";
        String nc_ass_startdate = "";
        String nc_ass_enddate = "";
        String nc_dogana = "";
        String nc_heavy_surname = "";
        String nc_heavy_name = "";
        String nc_heavy_addr = "";
        String nc_heavy_city = "";
        String nc_heavy_country = "";
        String nc_heavy_zipcode = "";
        String nc_heavy_district = "";
        String nc_heavy_email = "";
        String nc_heavy_phone = "";
        String nc_note = "";
        String nc_paymode = "";
        String nc_pos = "";
        String nc_ban = "";
        String nc_posnum = "";
        String nc_percentiva = "";

        String loya = "";

        String scontr2 = "NO";

        if (isMultipart) {
            try {
                FileItemFactory factory = new DiskFileItemFactory();
                ServletFileUpload upload = new ServletFileUpload(factory);
                List items = upload.parseRequest(request);
                Iterator iterator = items.iterator();
                while (iterator.hasNext()) {
                    FileItem item = (FileItem) iterator.next();
                    if (!item.isFormField()) {
                        String fileName = item.getName();
                        if (fileName != null) {
                            File pathdir = new File(pathtemp);
                            pathdir.mkdirs();
                            String estensione = getExtension(fileName).toLowerCase();
                            if (extacc.toLowerCase().contains(estensione)) {
                                String name = generaId(99) + estensione;
                                nomefile = new File(pathtemp + name);
                                try {
                                    extfile = estensione;
                                    item.write(nomefile);
                                } catch (Exception ex) {
                                    ok = "F1";
                                    insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
                                    nomefile = null;
                                    break;
                                }
                            } else {
                                ok = "F2";
                                nomefile = null;
                                break;
                            }
                        }
                    } else if (item.getFieldName().equals("id_open_till")) {
                        nc_id_open_till = item.getString().trim();
                    } else if (item.getFieldName().equals("till")) {
                        nc_till = item.getString().trim();
                    } else if (item.getFieldName().equals("nc_cat1")) {
                        nc_cat = item.getString().trim();
                    } else if (item.getFieldName().equals("nc_caus1")) {
                        nc_caus = item.getString().trim();
                    } else if (item.getFieldName().equals("mtcn0")) {
                        nc_mtcn = item.getString().trim();
                    } else if (item.getFieldName().equals("wunet0")) {
                        nc_wunet = item.getString().trim();
                    } else if (item.getFieldName().equals("scontr2")) {
                        scontr2 = item.getString().trim();
                    } else if (item.getFieldName().equals("wucom0")) {
                        nc_wucom = item.getString().trim();
                    } else if (item.getFieldName().equals("recei0")) {
                        nc_recei = item.getString().trim();
                    } else if (item.getFieldName().equals("quantity0")) {
                        nc_quantity = item.getString().trim();
                    } else if (item.getFieldName().equals("price0")) {
                        nc_price = item.getString().trim();
                    } else if (item.getFieldName().equals("loya")) {
                        loya = item.getString().trim();
                    } else if (item.getFieldName().equals("rights0")) {
                        nc_rights = item.getString().trim();
                    } else if (item.getFieldName().equals("rights2")) {
                        nc_tickfee_t = item.getString().trim();
                    } else if (item.getFieldName().equals("rights1")) {
                        nc_tickfee = item.getString().trim();
                    } else if (item.getFieldName().equals("total1")) {
                        nc_total = item.getString().trim();
                    } else if (item.getFieldName().equals("totalsign1")) {
                        nc_total_sign = item.getString().trim();
                    } else if (item.getFieldName().equals("ass_idcode")) {
                        nc_ass_idcode = item.getString().trim();
                    } else if (item.getFieldName().equals("ass_startdate")) {
                        nc_ass_startdate = item.getString().trim();
                    } else if (item.getFieldName().equals("ass_enddate")) {
                        nc_ass_enddate = item.getString().trim();
                    } else if (item.getFieldName().equals("customs0")) {
                        nc_dogana = item.getString().trim();
                    } else if (item.getFieldName().equals("heavy_surname")) {
                        nc_heavy_surname = item.getString().trim();
                    } else if (item.getFieldName().equals("heavy_name")) {
                        nc_heavy_name = item.getString().trim();
                    } else if (item.getFieldName().equals("heavy_addr")) {
                        nc_heavy_addr = item.getString().trim();
                    } else if (item.getFieldName().equals("heavy_city")) {
                        nc_heavy_city = item.getString().trim();
                    } else if (item.getFieldName().equals("heavy_country")) {
                        nc_heavy_country = item.getString().trim();
                    } else if (item.getFieldName().equals("heavy_zipcode")) {
                        nc_heavy_zipcode = item.getString().trim();
                    } else if (item.getFieldName().equals("heavy_district")) {
                        nc_heavy_district = item.getString().trim();
                    } else if (item.getFieldName().equals("heavy_email")) {
                        nc_heavy_email = item.getString().trim();
                    } else if (item.getFieldName().equals("heavy_phone")) {
                        nc_heavy_phone = item.getString().trim();
                    } else if (item.getFieldName().equals("note0")) {
                        nc_note = item.getString().trim();
                    } else if (item.getFieldName().equals("kind_1")) {
                        nc_paymode = item.getString().trim();
                    } else if (item.getFieldName().equals("pos_1")) {
                        nc_pos = item.getString().trim();
                    } else if (item.getFieldName().equals("ban_1")) {
                        nc_ban = item.getString().trim();
                    } else if (item.getFieldName().equals("posnum")) {
                        nc_posnum = item.getString().trim();
                    } else if (item.getFieldName().equals("piva0")) {
                        nc_percentiva = item.getString().trim();
                    }

                }
            } catch (FileUploadException ex) {
                insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
                ok = "F3";
                nomefile = null;
            }
        }

        if (ok.equals("0")) {
            String base64 = "-";

            if (nomefile != null) {// file presente
                base64 = extfile + ";" + getStringBase64(nomefile);
            }

            Till tdest = getContainsTill(nc_till, list_till);
            Db_Master db1 = new Db_Master();
            NC_causal nc2 = db1.get_nc_causal(nc_caus);
            db1.closeDB();

            if (nc2.getDocric().equals("1")) {
                if (nomefile == null) {
                    ok = "F4";
                }
            } else {
                base64 = "-";
            }

            NC_transaction nct = new NC_transaction();
            nct.setDocrico(base64);
            nct.setCod(cod);
            nct.setFiliale(filiale);
            nct.setUser(user);
            nct.setId_open_till(nc_id_open_till);
            nct.setTill(nc_till);
            nct.setNote(substring(getStringUTF8(nc_note), 0, 100));
            nct.setGruppo_nc(nc_cat);
            nct.setCausale_nc(nc_caus);
            nct.setPrezzo(formatDoubleforMysql(nc_price));
            nct.setFg_tipo_transazione_nc(nc2.getFg_tipo_transazione_nc());
            nct.setSupporto(nc_paymode);
            nct.setValuta(loc_cur[0]);

            switch (nc_paymode) {
                case "06":
                    nct.setPos(nc_pos);
                    nct.setPosnum(nc_posnum);
                    break;
                case "07":
                    nct.setPos(nc_pos);
                    nct.setPosnum("");
                    break;
                case "08":
                    nct.setPos(nc_ban);
                    nct.setPosnum("");
                    break;
                default:
                    nct.setPos("");
                    nct.setPosnum("");
                    break;
            }

            String nc_kind_inout = nc2.getNc_de();
            switch (nct.getFg_tipo_transazione_nc()) {
                case "1":
                    //WESTERN UNION
                    nct.setNetto(formatDoubleforMysql(nc_wunet));
                    nct.setMtcn(nc_mtcn);
                    if (nc_kind_inout.equals("01")) {
                        nct.setTotal((nc_total_sign + formatDoubleforMysql(nc_total)).trim());
                    } else if (nc_kind_inout.equals("02")) {
                        nct.setTotal(formatDoubleforMysql(nc_total));
                        nct.setCommissione(formatDoubleforMysql(nc_wucom));
                    }
                    break;
                case "2":
                    //STOCK normali
                    nct.setQuantita(formatDoubleforMysql(nc_quantity));
                    nct.setPrezzo(formatDoubleforMysql(nc_price));
                    nct.setTotal((nc_total_sign + formatDoubleforMysql(nc_total)).trim());
                    break;
                case "3":
                    //TAX FREE
                    nct.setQuantita(formatDoubleforMysql(nc_quantity));
                    nct.setTotal((nc_total_sign + formatDoubleforMysql(nc_total)).trim());
                    nct.setRicevuta(nc_recei);
                    nct.setFg_dogana(nc_dogana);
                    nct.setCl_cognome(getStringUTF8(nc_heavy_surname));
                    nct.setCl_nome(getStringUTF8(nc_heavy_name));
                    nct.setCl_indirizzo(getStringUTF8(nc_heavy_addr));
                    nct.setCl_citta(getStringUTF8(nc_heavy_city));
                    nct.setCl_nazione(nc_heavy_country);
                    nct.setCl_email(getStringUTF8(nc_heavy_email));
                    nct.setCl_telefono(nc_heavy_phone);
                    nct.setPrezzo("0.00");
                    break;
                case "4":
                    //STOCK other
                    nct.setQuantita(formatDoubleforMysql(nc_quantity));
                    nct.setPrezzo(formatDoubleforMysql(nc_price));
                    nct.setTotal((nc_total_sign + formatDoubleforMysql(nc_total)).trim());
                    break;
                case "5":
                    //ASSICURAZ
                    nct.setQuantita(formatDoubleforMysql(nc_quantity));
                    nct.setPrezzo(formatDoubleforMysql(nc_price));
                    nct.setTotal((nc_total_sign + formatDoubleforMysql(nc_total)).trim());
                    nct.setAss_idcode(nc_ass_idcode);
                    nct.setAss_startdate(nc_ass_startdate);
                    nct.setAss_enddate(nc_ass_enddate);
                    nct.setCl_cognome(getStringUTF8(nc_heavy_surname));
                    nct.setCl_nome(getStringUTF8(nc_heavy_name));
                    nct.setCl_indirizzo(nc_heavy_addr);
                    nct.setCl_citta(getStringUTF8(nc_heavy_city));
                    nct.setCl_nazione(nc_heavy_country);
                    nct.setCl_cap(nc_heavy_zipcode);
                    nct.setCl_provincia(getStringUTF8(nc_heavy_district));
                    nct.setCl_email(getStringUTF8(nc_heavy_email));
                    nct.setCl_telefono(nc_heavy_phone);
                    break;
                case "6":
                    //anticipo dipendenti
                    nct.setQuantita(formatDoubleforMysql(nc_quantity));
                    nct.setPrezzo(formatDoubleforMysql(nc_price));
                    nct.setTotal((nc_total_sign + formatDoubleforMysql(nc_total)).trim());
                    break;
                case "21":
                    //TICKET
                    nct.setQuantita((nc_quantity));
                    nct.setPrezzo(formatDoubleforMysql(nc_price));
                    nct.setTi_diritti(formatDoubleforMysql(nc_rights));
                    nct.setCommissione(formatDoubleforMysql(nc_tickfee_t));
                    nct.setTi_ticket_fee(formatDoubleforMysql(nc_tickfee));
                    nct.setTotal((nc_total_sign + formatDoubleforMysql(nc_total)).trim());
                    break;
                case "7":
                    //internet point

                    nct.setQuantita(formatDoubleforMysql(nc_quantity));
                    nct.setPrezzo(formatDoubleforMysql(nc_price));
                    nct.setTotal((nc_total_sign + formatDoubleforMysql(nc_total)).trim());
                    nct.setPercentiva(formatDoubleforMysql(nc_percentiva));
                    break;
                default:
                    break;
            }
            Db_Master db3 = new Db_Master();
            nct.setFg_inout(db3.getFg_inout_ncde(nct.getFg_tipo_transazione_nc(), nc_kind_inout));
            db3.closeDB();
            nct.setDel_dt("1901-01-01 00:00:00");
            nct.setDel_user("-");
            nct.setDel_motiv("-");
            nct.setCh_transaction("-");
            nct.setDel_fg("0");
            nct.setBonus(nc2.getBonus());

            boolean totalzero = false;

            if (!nct.getFg_tipo_transazione_nc().equals("2")) {
                totalzero = formatDoubleforMysql(nc_total).equals("0.00");
            }

            if (totalzero) {
                ok = "1A";
            } else if (nct.getFg_inout().equals("1")) { //Money IN
                if (nct.getSupporto().equals("06") || nct.getSupporto().equals("07") || nct.getSupporto().equals("08")) {     //creditcard
                    Real_oc_pos rop = new Real_oc_pos();
                    rop.setFiliale(filiale);
                    rop.setCod_oc(nc_id_open_till);
                    rop.setValuta(loc_cur[0]);
                    rop.setKind(nc_paymode);
                    rop.setData(dateoper);
                    rop.setCarta_credito(nct.getPos());
                    Db_Master db4 = new Db_Master();
                    rop.setIp_quantity(valueOf(parseIntR(db4.getField_real_oc_pos(rop, "ip_quantity", "0")) + 1));
                    rop.setIp_value(roundDoubleandFormat((fd(db4.getField_real_oc_pos(rop, "ip_value", "0.00")) + fd(nct.getTotal())), 2));
                    db4.update_real_oc_pos(rop);
                    db4.closeDB();
                } else {

                    Real_oc_change to = new Real_oc_change();
                    to.setFiliale(filiale);
                    to.setCod_oc(nc_id_open_till);
                    to.setValuta(loc_cur[0]);
                    to.setKind(nc_paymode);
                    to.setData(dateoper);
                    to.setNum_kind_op("0");
                    Db_Master db5 = new Db_Master();
                    to.setValue_op(roundDoubleandFormat((fd(db5.getField_real_oc_change(to, "value_op", "0.00")) + fd(nct.getTotal())), 2));
                    db5.update_real_oc_change(to);
                    db5.closeDB();
                    Db_Master db8 = new Db_Master();
                    Stock_report sr = db8.get_Stock_report(nc_id_open_till, loc_cur[0], "01", "CH", filiale, nc_till);
                    db8.closeDB();
                    //  AUMENTO EURO
                    String codsr = filiale + generaId(47);
                    sr.setCodtr(cod);
                    sr.setCodice(codsr);
                    double newtot = fd(nct.getTotal());
                    sr.setTotal(roundDoubleandFormat(newtot, 2));
                    sr.setSpread("1.0000");
                    sr.setData(dateoper);
                    sr.setQuantity("0");
                    sr.setUser(user);
                    Db_Master db9 = new Db_Master();
                    db9.insert_Stockreport(sr);
                    db9.closeDB();

                    //ADD EURO STOCK
                    Stock st1 = new Stock();
                    st1.setCodice("ST" + generaId(48));
                    st1.setFiliale(filiale);
                    st1.setTipo("CH");
                    st1.setTill(nc_till);
                    st1.setIdoperation(cod);
                    st1.setCodiceopenclose(nc_id_open_till);
                    st1.setTipostock("CH");
                    st1.setCod_value(loc_cur[0]);
                    st1.setKind("01");
                    st1.setTotal(formatDoubleforMysql(nc_total));
                    st1.setRate("1.00000000");
                    st1.setControval(formatDoubleforMysql(nc_total));
                    st1.setUser(user);
                    st1.setDate(dateoper);
                    st1.setId_op(nc_id_open_till);
                    Db_Master db1a = new Db_Master();
                    db1a.insertStock(st1);
                    db1a.closeDB();

                }

            } else if (nct.getFg_inout().equals("2")) { //Money OUT
                String totold = "0.00";
                Db_Master db6 = new Db_Master();
                ArrayList<String[]> array_list_oc_change = db6.list_oc_change_real();
                db6.closeDB();
                for (int i = 0; i < array_list_oc_change.size(); i++) {
                    if (nc_id_open_till.equals(array_list_oc_change.get(i)[0])) {
                        if (array_list_oc_change.get(i)[1].equals("01") && array_list_oc_change.get(i)[2].equalsIgnoreCase(loc_cur[0])) {
                            totold = array_list_oc_change.get(i)[3];
                        }
                    }
                }

                if (fd(nct.getTotal()) >= fd(totold)) {
                    ok = "1Q";
                } else {
                    Real_oc_change to = new Real_oc_change();
                    to.setFiliale(filiale);
                    to.setCod_oc(nc_id_open_till);
                    to.setValuta(loc_cur[0]);
                    to.setKind("01");
                    to.setData(dateoper);
                    to.setNum_kind_op("0");
                    Db_Master db7 = new Db_Master();
                    to.setValue_op(roundDoubleandFormat((fd(db7.getField_real_oc_change(to, "value_op", "0.00")) - fd(formatDoubleforMysql(nc_total))), 2));
                    db7.update_real_oc_change(to);
                    db7.closeDB();
                    //euro stock meno
                    Db_Master dbS = new Db_Master();
                    ArrayList<Stock> al = dbS.list_stock(filiale, "01", loc_cur[0]);
                    ArrayList<String[]> damod = new ArrayList<>();
                    double quant_now = fd(formatDoubleforMysql(nc_total));
                    double quant_check = quant_now;
                    for (int i = 0; i < al.size() && quant_check > 0; i++) {
                        double vq1 = fd(al.get(i).getTotal());
                        if (vq1 >= quant_check) {
                            String[] v = {al.get(i).getCodice(), "", "", al.get(i).getCodice(), roundDoubleandFormat(quant_check, 2), al.get(i).getRate(), "U",
                                roundDoubleandFormat(vq1 - quant_check, 2)};
                            damod.add(v);
                            quant_check = 0.0;
                        } else {
                            String[] v = {al.get(i).getCodice(), "", "", al.get(i).getCodice(), roundDoubleandFormat(quant_check, 2), al.get(i).getRate(), "U", "0.00"};//07/11
//                            String[] v = {al.get(i).getCodice(), "", "", al.get(i).getCodice(), roundDoubleandFormat(quant_check, 2), al.get(i).getRate(), "D", ""};
                            damod.add(v);
                            quant_check = quant_check - vq1;
                        }
                    }
                    dbS.updateStock(damod, false);
                    dbS.closeDB();

                    Db_Master db8 = new Db_Master();
                    Stock_report sr = db8.get_Stock_report(nc_id_open_till, loc_cur[0], "01", "CH", filiale, nc_till);
                    db8.closeDB();
                    //  DIMINUISCO EURO
                    String codsr = filiale + generaId(47);
                    sr.setCodtr(cod);
                    sr.setCodice(codsr);
                    double newtot = -fd(formatDoubleforMysql(nc_total));
                    sr.setTotal(roundDoubleandFormat(newtot, 2));
                    sr.setSpread("1.0000");
                    sr.setData(dateoper);
                    sr.setQuantity("0");
                    sr.setUser(user);
                    Db_Master db9 = new Db_Master();
                    db9.insert_Stockreport(sr);
                    db9.closeDB();

                }
            } else if (nct.getFg_inout().equals("3")) { //Money IN - Stock OUT
                Db_Master db6 = new Db_Master();
                ArrayList<String[]> list_oc_nochange = db6.list_oc_nochange_real(nc_id_open_till);
                db6.closeDB();

                String quantold = "0";
                for (int i = 0; i < list_oc_nochange.size(); i++) {
                    if (list_oc_nochange.get(i)[1].equalsIgnoreCase(nct.getGruppo_nc())) {
                        quantold = list_oc_nochange.get(i)[2];
                        break;
                    }
                }
                if (fd(nct.getQuantita()) > fd(quantold)) {
                    ok = "1Q";
                } else {

////                diminuire no stock
                    Real_oc_nochange from = new Real_oc_nochange();
                    from.setFiliale(filiale);
                    from.setCod_oc(nc_id_open_till);
                    from.setGruppo_nc(nct.getGruppo_nc());
                    Db_Master db7 = new Db_Master();
                    from.setQuantity(valueOf(parseIntR(db7.getQuantity_real_oc_nochange(from)) - parseIntR(nct.getQuantita())));
                    from.setData(dateoper);
                    db7.update_real_oc_nochange(from);
                    db7.closeDB();
                    //DECREMENTARE
                    Db_Master db8 = new Db_Master();
                    Stock_report sr = db8.get_Stock_report(nc_id_open_till, nc_cat, "01", "NC", filiale, nc_till);
                    db8.closeDB();
                    String codsr = filiale + generaId(47);
                    sr.setCodtr(cod);
                    sr.setCodice(codsr);
                    sr.setTotal("0.00");
                    sr.setSpread("1.0000");
                    sr.setData(dateoper);
                    sr.setQuantity("-" + parseIntR(nct.getQuantita()));
                    sr.setUser(user);
                    Db_Master db9 = new Db_Master();
                    db9.insert_Stockreport(sr);
                    db9.closeDB();

                    if (nct.getSupporto().equals("06") || nct.getSupporto().equals("07") || nct.getSupporto().equals("08")) { //creditcard
                        Real_oc_pos rop = new Real_oc_pos();
                        rop.setFiliale(filiale);
                        rop.setCod_oc(nc_id_open_till);
                        rop.setValuta(loc_cur[0]);
                        rop.setKind(nc_paymode);
                        rop.setData(dateoper);
                        rop.setCarta_credito(nct.getPos());
                        Db_Master db10 = new Db_Master();
                        rop.setIp_quantity(valueOf(parseIntR(db10.getField_real_oc_pos(rop, "ip_quantity", "0")) + 1));
                        rop.setIp_value(roundDoubleandFormat((fd(db10.getField_real_oc_pos(rop, "ip_value", "0.00")) + fd(formatDoubleforMysql(nc_total).trim())), 2));

                        db10.update_real_oc_pos(rop);
                        db10.closeDB();
                    } else {

                        Real_oc_change to = new Real_oc_change();
                        to.setFiliale(filiale);
                        to.setCod_oc(nc_id_open_till);
                        to.setValuta(loc_cur[0]);
                        to.setKind(nc_paymode);
                        to.setData(dateoper);
                        to.setNum_kind_op("0");
                        Db_Master db10 = new Db_Master();
                        to.setValue_op(roundDoubleandFormat((fd(db10.getField_real_oc_change(to, "value_op", "0.00"))
                                + fd(formatDoubleforMysql(nc_total))), 2));
                        db10.update_real_oc_change(to);
                        db10.closeDB();

                        Db_Master db8A = new Db_Master();
                        Stock_report sr1 = db8A.get_Stock_report(nc_id_open_till, loc_cur[0], "01", "CH", filiale, nc_till);
                        db8A.closeDB();

                        //  AUMENTO EURO
                        String codsrA = filiale + generaId(47);
                        sr1.setCodtr(cod);
                        sr1.setCodice(codsrA);
                        double newtot = fd(formatDoubleforMysql(nc_total));
                        sr1.setTotal(roundDoubleandFormat(newtot, 2));
                        sr1.setSpread("1.0000");
                        sr1.setData(dateoper);
                        sr1.setQuantity("0");
                        sr1.setUser(user);
                        Db_Master db9A = new Db_Master();
                        db9A.insert_Stockreport(sr1);
                        db9A.closeDB();

                        //ADD EURO STOCK
                        Stock st1 = new Stock();
                        st1.setCodice("ST" + generaId(48));
                        st1.setFiliale(filiale);
                        st1.setTipo("CH");
                        st1.setTill(nc_till);
                        st1.setIdoperation(cod);
                        st1.setCodiceopenclose(nc_id_open_till);
                        st1.setTipostock("CH");
                        st1.setCod_value(loc_cur[0]);
                        st1.setKind("01");
                        st1.setTotal(formatDoubleforMysql(nc_total));
                        st1.setRate("1.00000000");
                        st1.setControval(formatDoubleforMysql(nc_total));
                        st1.setUser(user);
                        st1.setDate(dateoper);
                        st1.setId_op(nc_id_open_till);
                        Db_Master db1a = new Db_Master();
                        db1a.insertStock(st1);
                        db1a.closeDB();
                    }
                }
            } else if (nct.getFg_inout().equals("4")) { //Money OUT - Stock IN
                String totold = "0.00";
                Db_Master db10 = new Db_Master();
                ArrayList<String[]> array_list_oc_change = db10.list_oc_change_real();
                db10.closeDB();
                for (int i = 0; i < array_list_oc_change.size(); i++) {
                    if (nc_id_open_till.equals(array_list_oc_change.get(i)[0])) {

                        if (array_list_oc_change.get(i)[1].equals("01") && array_list_oc_change.get(i)[2].equalsIgnoreCase(loc_cur[0])) {
                            totold = array_list_oc_change.get(i)[3];
                        }
                    }
                }
                boolean checkquantity = true;

                if (!tdest.isSafe()) {
                    checkquantity = fd(nct.getTotal()) <= fd(totold);
                }
                if (!checkquantity) {
                    ok = "1Q";
                } else {
                    if (!tdest.isSafe()) {
                        Real_oc_change to = new Real_oc_change();
                        to.setFiliale(filiale);
                        to.setCod_oc(nc_id_open_till);
                        to.setValuta(loc_cur[0]);
                        to.setKind("01");
                        to.setData(dateoper);
                        to.setNum_kind_op("0");
                        Db_Master db11 = new Db_Master();
                        to.setValue_op(roundDoubleandFormat((fd(db11.getField_real_oc_change(to, "value_op", "0.00"))
                                - fd(formatDoubleforMysql(nc_total))), 2));
                        db11.update_real_oc_change(to);
                        db11.closeDB();

                        Db_Master db8A = new Db_Master();
                        Stock_report sr1 = db8A.get_Stock_report(nc_id_open_till, loc_cur[0], "01", "CH", filiale, nc_till);
                        db8A.closeDB();
                        //  AUMENTO EURO
                        String codsrA = filiale + generaId(47);
                        sr1.setCodtr(cod);
                        sr1.setCodice(codsrA);
                        double newtot = -fd(formatDoubleforMysql(nc_total));
                        sr1.setTotal(roundDoubleandFormat(newtot, 2));
                        sr1.setSpread("1.0000");
                        sr1.setData(dateoper);
                        sr1.setQuantity("0");
                        sr1.setUser(user);
                        Db_Master db9A = new Db_Master();
                        db9A.insert_Stockreport(sr1);
                        db9A.closeDB();

                        //euro stock meno
                        Db_Master dbS = new Db_Master();
                        ArrayList<Stock> al = dbS.list_stock(filiale, "01", loc_cur[0]);
                        ArrayList<String[]> damod = new ArrayList<>();
                        double quant_now = fd(formatDoubleforMysql(nc_total));
                        double quant_check = quant_now;
                        for (int i = 0; i < al.size() && quant_check > 0; i++) {
                            double vq1 = fd(al.get(i).getTotal());
                            if (vq1 >= quant_check) {
                                String[] v = {al.get(i).getCodice(), "", "", al.get(i).getCodice(), roundDoubleandFormat(quant_check, 2), al.get(i).getRate(), "U",
                                    roundDoubleandFormat(vq1 - quant_check, 2)};
                                damod.add(v);
                                quant_check = 0.0;
                            } else {
                                String[] v = {al.get(i).getCodice(), "", "", al.get(i).getCodice(), roundDoubleandFormat(quant_check, 2), al.get(i).getRate(), "U", "0.00"};//07/11
                                damod.add(v);
                                quant_check = quant_check - vq1;
                            }
                        }
                        dbS.updateStock(damod, false);
                        dbS.closeDB();

                    }
                    //aumentare no stock 
                    Real_oc_nochange from = new Real_oc_nochange();
                    from.setFiliale(filiale);
                    from.setCod_oc(nc_id_open_till);
                    from.setGruppo_nc(nct.getGruppo_nc());
                    Db_Master db12 = new Db_Master();
                    from.setQuantity(valueOf(parseIntR(db12.getQuantity_real_oc_nochange(from)) + parseIntR(nct.getQuantita())));
                    from.setData(dateoper);
                    db12.update_real_oc_nochange(from);
                    db12.closeDB();
                    //AUMENTARE
                    Db_Master db8 = new Db_Master();
                    Stock_report sr = db8.get_Stock_report(nc_id_open_till, nc_cat, "01", "NC", filiale, nc_till);
                    db8.closeDB();
                    String codsr = filiale + generaId(47);
                    sr.setCodtr(cod);
                    sr.setCodice(codsr);
                    sr.setTotal("0.00");
                    sr.setSpread("1.0000");
                    sr.setData(dateoper);
                    sr.setQuantity(valueOf(parseIntR(nct.getQuantita())));
                    sr.setUser(user);
                    Db_Master db9 = new Db_Master();
                    db9.insert_Stockreport(sr);
                    db9.closeDB();
                }
            }
            if (ok.equals("0")) {
                Db_Master db11 = new Db_Master();
                boolean es = db11.insert_NC_transaction(nct);
                db11.closeDB();
                if (!es) {
                    ok = "2A";
                } else {
//                    if (Constant.is_CZ) {
//                        invia(null, nct);
//                    }
                }
            }
        }

        //nuovo 18/05
        if (ok.equals("0")) {
            loya = loya.trim();
            if (!loya.equals("")) {
                Db_Loy dbl = new Db_Loy();
                if (dbl.getC() != null) {
                    String completo[] = dbl.getCodiceCompleto(loya);
                    String codcl = dbl.getCodiceCliente_pub(loya);
                    dbl.closeDB();
                    if (completo != null) {
                        Db_Master db12 = new Db_Master();
                        db12.insertLoy_TR(cod, codcl, completo[0], user);
                        db12.closeDB();
                    }
                }
            }
        }
        if (is_IT) {
            if (ok.equals("0") && scontr2.equals("SI")) {

                String resp = sendPOS_transaction_new(cod);
                request.getSession().setAttribute("pos1", resp);
                //OLD
//                    String resp = Utility.sendPOS_transaction(cod);
//                    request.getSession().setAttribute("pos1", resp);

            }
        }

        if (ok.equals("0")) {

            redirect(request, response, "nc_transaction_esito.jsp?esito=OK&cod=" + cod);
        } else {
            redirect(request, response, "nc_transaction.jsp?esito=" + ok);
        }

    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void del_transaction_Ch(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

//        Utility.printRequest(request);
//        if (true) {
//            return;
//        }
        String user = (String) request.getSession().getAttribute("us_cod");
        if (user == null) {
            user = "9999";
        }
        StringBuilder us1 = new StringBuilder(user);
        String code = request.getParameter("idtrdel");
        String motiv = request.getParameter("motiv1");

        Db_Master db1 = new Db_Master();
        String valutalocale = db1.get_local_currency()[0];
        String dt_tr = db1.getNow();
        Ch_transaction tra = db1.query_transaction_ch(code);
        if (tra == null) {
            tra = db1.query_transaction_ch_temp(code);
        }

        db1.closeDB();

        boolean del = false;

        String es = "0";
        boolean activefr = false;
        if (tra == null || tra.getDel_fg().equals("1")) {
            es = "kk";
            del = true;
        }

        if (es.equals("0") && tra != null) {
            Db_Master db0 = new Db_Master();
            DateTime today = db0.getCurdateDT();
            ArrayList<Till> array_till = db0.list_till_status("O", null, tra.getFiliale());
            db0.closeDB();
            for (int j = 0; j < array_till.size(); j++) {
                if (array_till.get(j).getId_opcl().equals(tra.getId_open_till())) {
                    DateTime dt1 = getDT(tra.getData().substring(0, 10), patternsql);
                    activefr = today.isEqual(dt1);
                    break;
                }
            }
            if (!activefr) {
                es = "CC";
            }
        }

        if (es.equals("0")) {
            String ok = checkDeleteTR(code);
            if (ok.equals("OK")) {
                es = "0";
            } else {
                es = "Q2";
            }
        }

        //System.out.println("rc.so.servlets.Operazioni.del_transaction_Ch() "+es);
//        if(true)return;
        if (es.equals("0")) {
            Db_Master db = new Db_Master();
            boolean es1 = db.delete_transaction_ch(code, motiv, user);
            db.closeDB();
            if (!es1) {
                es = "kk";
            }
        }

        if (es.equals("0")) {
            if (tra != null) {
                Db_Master db2 = new Db_Master();
                ArrayList<Ch_transaction_value> val = db2.query_transaction_value(code);
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
                    ArrayList<String[]> listock = db10B.get_stock_quantity(code, "SE");
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
                    db3.delete_trans_stock(code, tra.getId_open_till());
                    db3.closeDB();

                    Db_Master db4 = new Db_Master();
                    ArrayList<NC_transaction> linc = db4.list_nctransactionfromchange(code);
                    for (int c = 0; c < linc.size(); c++) {
                        db4.delete_transaction_noch(linc.get(c).getCod(), motiv, user);
                    }
                    db4.closeDB();
                } else {
                    Db_Master db5 = new Db_Master();
                    db5.delete_trans_stock(code, tra.getId_open_till());
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
                    t1.setValuta(valutalocale);
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
                    t1.setValuta(valutalocale);
                    t1.setKind(paym);
                    t1.setCarta_credito(tra.getPos());
                    t1.setData(dt_tr);
                    Db_Master db10 = new Db_Master();
                    t1.setIp_quantity(valueOf(parseInt(db10.getField_real_oc_pos(t1, "ip_quantity", "0")) - 1));
                    t1.setIp_value(roundDoubleandFormat((fd(db10.getField_real_oc_pos(t1, "ip_value", "0.00")) - fd(tra.getPay())), 2));
                    db10.update_real_oc_pos(t1);
                    db10.closeDB();
                }

                Db_Master db11 = new Db_Master();
                db11.delete_trans_stockreport(code, tra.getId_open_till());
                if (tra.getTipotr().equals("B")) {
                    db11.libera_bb_transaction(code, user, "1");
                } else {
                    db11.libera_bb_transaction(code, user, "3");
                }
                db11.closeDB();
                //23/07
                if (es.equals("0")) {
                    String t1 = "central";
                    if (filiale.equals("000")) {
                        t1 = null;
                    }
                    String loy = query_LOY_transaction(Utility.sanitize(tra.getCod()), Utility.sanitize(t1), Utility.sanitize(filiale));
                    remove_loy_assign_first_NEW(loy, Utility.sanitize(tra.getCl_cod())); //22/01
                }
            }
        }

        if (es.equals("0")) {
            if (tra != null) {

                //  Viene annullata la transazione no change legata allo scarico automatico magazzino per le WK/TIC.
                Db_Master dbc1 = new Db_Master();

                dbc1.delete_marketing(tra.getCod(), user);
//                List<String> li_tr = dbc1.get_WK_TI(tra);
//                li_tr.forEach(cod1 -> {
//                    remove_nochange_transaction(cod1,
//                            "The customer refused to give consent for data processing.",
//                            us1.toString(),
//                            filiale);
//                });
                dbc1.closeDB();

                if (!tra.getFa_number().equals("-") && !tra.getFa_number().equals("")) {
                    //if (generatenota.equals("yes") && !tra.getFa_number().equals("-") && !tra.getFa_number().equals("")) {
                    Db_Master dbc = new Db_Master();
                    String invoice_number = dbc.get_invoice_number(tra.getCod());
                    boolean es1 = dbc.insert_inv_list(invoice_number, tra.getCod(), "N", "1", dbc.getNow());
                    if (es1) {
                        es1 = dbc.update_invoice_transaction(tra.getCod(), "ch_transaction", "cn_number", invoice_number);
                    }
                    dbc.closeDB();
                    if (es1) {
                        redirect(request, response, "fancy_deltr_cn_sign.jsp?code=" + tra.getCod());
                    } else {
                        redirect(request, response, "fancy_deltr_cn.jsp?esito=KO&code=" + tra.getCod());
                    }
                } else {
                    redirect(request, response, "fancy_message.jsp?esito=" + es);
                }
            } else {
                redirect(request, response, "fancy_message.jsp?esito=" + es);
            }
        } else {
            if (!activefr) {
                redirect(request, response, "fancy_message.jsp?esito=CC");
            } else if (!del) {
                redirect(request, response, "fancy_message.jsp?esito=" + es);
            } else {
                redirect(request, response, "fancy_message.jsp?esito=kokk");
            }
        }

    }

    private String verificaQuantitaIT_ET_annullare(IT_change it, ET_change et) {

        if (it != null) {
            Db_Master db = new Db_Master();
            ArrayList<Till> array_till = db.list_till_status("O", null, it.getFiliale());
            db.closeDB();
            boolean activefr = false;
            boolean activeto = false;
            boolean issafeto = false;

            for (int j = 0; j < array_till.size(); j++) {
                if (array_till.get(j).getId_opcl().equals(it.getIdopen_from())) {
                    activefr = true;
                    break;
                }
            }

            for (int j = 0; j < array_till.size(); j++) {
                if (array_till.get(j).getId_opcl().equals(it.getIdopen_to())) {
                    activeto = true;
                    issafeto = array_till.get(j).isSafe();
                    break;
                }
            }

            if (activefr && activeto) {
                if (it.getTypeop().equals("CH")) {
                    ArrayList<IT_change> itv = get_internal_transfer_ch_value(it.getCod());
                    if (itv.size() > 0) {
                        for (int i = 0; i < itv.size(); i++) {
                            Db_Master db9B = new Db_Master();
                            Real_oc_change to = new Real_oc_change();
                            to.setFiliale(it.getFiliale());
                            to.setCod_oc(it.getIdopen_to());
                            to.setValuta(itv.get(i).getValuta());
                            to.setKind(itv.get(i).getSupporto());
                            to.setData(it.getDt_it());
                            String qu1 = db9B.getField_real_oc_change(to, "num_kind_op", "0");
                            String to1 = db9B.getField_real_oc_change(to, "value_op", "0.00");
                            db9B.closeDB();
                            if (issafeto) {
                                if (fd(qu1) < fd(itv.get(i).getQuantita())) {
                                    return "3A";
                                }
                            }
                            if (fd(to1) < fd(itv.get(i).getTotale())) {
                                return "3A";
                            }
                        }
                        return "0";
                    } else {
                        return "2A";
                    }
                } else {
                    //verifica disponibilità no change
                    ArrayList<IT_change> itv = get_internal_transfer_noch_value(it.getCod());
                    if (itv.size() > 0) {
                        for (int i = 0; i < itv.size(); i++) {
                            Db_Master db9B = new Db_Master();
                            Real_oc_nochange to = new Real_oc_nochange();
                            to.setFiliale(filiale);
                            to.setCod_oc(it.getIdopen_to());
                            to.setGruppo_nc(itv.get(i).getNc_causal());
                            String qu1 = db9B.getQuantity_real_oc_nochange(to);
                            db9B.closeDB();
                            if (fd(qu1) < fd(itv.get(i).getQuantita())) {
                                return "3A";
                            }
                        }
                        return "0";

                    } else {
                        return "2A";
                    }
                }

            }

        } else if (et != null) {
            Db_Master db = new Db_Master();
            ArrayList<Till> array_till = db.list_till_status("O", null, et.getFiliale());
            db.closeDB();
            boolean attivo = false;
            for (int j = 0; j < array_till.size(); j++) {
                if (array_till.get(j).getId_opcl().equals(et.getIdopen_from())) {
                    attivo = true;
                    break;
                }
            }
            if (attivo) {
                if (et.getFg_tofrom().equals("F")) {
                    if (et.getType().equals("CH")) {
                        ArrayList<ET_change> et_va = get_ET_change_value(et.getCod());
                        if (et_va.size() > 0) {
                            for (int i = 0; i < et_va.size(); i++) {
                                Db_Master db9B = new Db_Master();
                                Real_oc_change from = new Real_oc_change();
                                from.setFiliale(et.getFiliale());
                                from.setCod_oc(et.getIdopen_from());
                                from.setValuta(et_va.get(i).getValuta());
                                from.setKind(et_va.get(i).getSupporto());
                                from.setData(et.getDt_it());
                                String qu1 = db9B.getField_real_oc_change(from, "num_kind_op", "0");
                                String to1 = db9B.getField_real_oc_change(from, "value_op", "0.00");
                                db9B.closeDB();
                                if ((fd(qu1) < fd(et_va.get(i).getIp_stock())) || (fd(to1) < fd(et_va.get(i).getIp_quantity()))) {
                                    return "3A";
                                }
                            }
                        } else {
                            return "2A";
                        }
                    }
                }
                return "0";
            }
        }
        return "3";
    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void del_internal_transfer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String user = (String) request.getSession().getAttribute("us_cod");
        if (user == null) {
            user = "9999";
        }
        String code = request.getParameter("idtrdel");
        String motiv = request.getParameter("motiv1");
        IT_change it = get_internal_transfer(code);
        String ok = "0";

        if (it == null || it.getFg_annullato().equals("1")) {
            ok = "kk";
            it = new IT_change();
        }

        if (ok.equals("0")) {
            ok = verificaQuantitaIT_ET_annullare(it, null);
            if (ok.equals("0")) {
                Db_Master db = new Db_Master();
                ArrayList<Till> array_till = db.list_till_status("O", null, it.getFiliale());
                db.closeDB();

                boolean issafefrom = false;
                boolean issafeto = false;

                for (int j = 0; j < array_till.size(); j++) {
                    if (array_till.get(j).getId_opcl().equals(it.getIdopen_from())) {
                        issafefrom = array_till.get(j).isSafe();
                        break;
                    }
                }

                for (int j = 0; j < array_till.size(); j++) {
                    if (array_till.get(j).getId_opcl().equals(it.getIdopen_to())) {
                        issafeto = array_till.get(j).isSafe();
                        break;
                    }
                }

                Db_Master db1 = new Db_Master();
                boolean es = db1.delete_internal_transfer(code, motiv, user);
                db1.closeDB();
                if (es) {
                    if (it.getTypeop().equals("CH")) {
                        ArrayList<IT_change> itv = get_internal_transfer_ch_value(code);

                        for (int i = 0; i < itv.size(); i++) {

                            Db_Master db8 = new Db_Master();
                            Stock_report srF = db8.get_Stock_report(it.getIdopen_from(), itv.get(i).getValuta(), itv.get(i).getSupporto(), "CH", filiale, it.getTill_from());
                            Stock_report srT = db8.get_Stock_report(it.getIdopen_to(), itv.get(i).getValuta(), itv.get(i).getSupporto(), "CH", filiale, it.getTill_to());
                            db8.closeDB();

                            String codsrF = filiale + generaId(47);
                            srF.setCodtr(it.getCod());
                            srF.setCodice(codsrF);
                            double newtotA = fd(itv.get(i).getTotale());
                            srF.setTotal(roundDoubleandFormat(newtotA, 2));
                            srF.setSpread("1.0000");
                            srF.setData(it.getDt_it());
                            if (issafefrom) {
                                srF.setQuantity(roundDoubleandFormat(fd((itv.get(i).getQuantita())), 0));
                            } else {
                                srF.setQuantity("0");
                            }
                            srF.setUser(user);

                            String codsrT = filiale + generaId(47);
                            srT.setCodtr(it.getCod());
                            srT.setCodice(codsrT);
                            srT.setTotal(roundDoubleandFormat(-newtotA, 2));
                            srT.setSpread("1.0000");
                            srT.setData(it.getDt_it());
                            if (issafeto) {
                                srT.setQuantity(roundDoubleandFormat(-fd((itv.get(i).getQuantita())), 0));
                            } else {
                                srT.setQuantity("0");
                            }
                            srT.setUser(user);

                            Db_Master db9A = new Db_Master();
                            db9A.insert_Stockreport(srF);
                            db9A.insert_Stockreport(srT);
                            db9A.closeDB();

                            Db_Master db9B = new Db_Master();
                            Real_oc_change from = new Real_oc_change();
                            from.setFiliale(filiale);
                            from.setCod_oc(it.getIdopen_from());
                            from.setValuta(itv.get(i).getValuta());
                            from.setKind(itv.get(i).getSupporto());
                            from.setData(it.getDt_it());

                            if (issafefrom) {
                                from.setNum_kind_op(roundDoubleandFormat((fd(db9B.getField_real_oc_change(from, "num_kind_op", "0")) + fd((itv.get(i).getQuantita()))), 0));
                            } else {
                                from.setNum_kind_op("0");
                            }
                            from.setValue_op(roundDoubleandFormat((fd(db9B.getField_real_oc_change(from, "value_op", "0.00")) + fd((itv.get(i).getTotale()))), 2));

                            Real_oc_change to = new Real_oc_change();
                            to.setFiliale(filiale);
                            to.setCod_oc(it.getIdopen_to());
                            to.setValuta(itv.get(i).getValuta());
                            to.setKind(itv.get(i).getSupporto());
                            to.setData(it.getDt_it());
                            if (issafeto) {
                                to.setNum_kind_op(roundDoubleandFormat((fd(db9B.getField_real_oc_change(to, "num_kind_op", "0")) - fd((itv.get(i).getQuantita()))), 0));
                            } else {
                                to.setNum_kind_op("0");
                            }
                            to.setValue_op(roundDoubleandFormat((fd(db9B.getField_real_oc_change(to, "value_op", "0.00")) - fd((itv.get(i).getTotale()))), 2));

                            db9B.update_real_oc_change(from);
                            db9B.update_real_oc_change(to);
                            db9B.closeDB();
                        }

                        ArrayList<IT_change> it_tg = get_internal_transfer_ch_tg(code);

                        for (int h = 0; h < it_tg.size(); h++) {

                            if (issafefrom) {
                                //update real time tagli
                                Real_oc_change from_cuts = new Real_oc_change();
                                from_cuts.setFiliale(filiale);
                                from_cuts.setCod_oc(it.getIdopen_from());
                                from_cuts.setValuta(it_tg.get(h).getValuta());
                                from_cuts.setKind(it_tg.get(h).getSupporto());
                                from_cuts.setData(it.getDt_it());
                                from_cuts.setIp_taglio(it_tg.get(h).getTaglio());
                                Db_Master db9B = new Db_Master();
                                from_cuts.setNum_kind_op(roundDoubleandFormat((fd(db9B.getField_real_oc_change_cuts(from_cuts, "ip_quantity", "0")) + fd((it_tg.get(h).getQuantita()))), 0));
                                from_cuts.setValue_op(roundDoubleandFormat((fd(db9B.getField_real_oc_change_cuts(from_cuts, "ip_value", "0.00")) + fd((it_tg.get(h).getTotale()))), 2));
                                db9B.update_real_oc_change_cuts(from_cuts);
                                db9B.closeDB();
                            }
                            if (issafeto) {
                                Real_oc_change to_cuts = new Real_oc_change();
                                to_cuts.setFiliale(filiale);
                                to_cuts.setCod_oc(it.getIdopen_to());
                                to_cuts.setValuta(it_tg.get(h).getValuta());
                                to_cuts.setKind(it_tg.get(h).getSupporto());
                                to_cuts.setData(it.getDt_it());
                                to_cuts.setIp_taglio(it_tg.get(h).getTaglio());
                                Db_Master db9B = new Db_Master();
                                to_cuts.setNum_kind_op(roundDoubleandFormat((fd(db9B.getField_real_oc_change_cuts(to_cuts, "ip_quantity", "0")) - fd((it_tg.get(h).getQuantita()))), 0));
                                to_cuts.setValue_op(roundDoubleandFormat((fd(db9B.getField_real_oc_change_cuts(to_cuts, "ip_value", "0.00")) - fd((it_tg.get(h).getTotale()))), 2));
                                db9B.update_real_oc_change_cuts(to_cuts);
                                db9B.closeDB();
                            }

                        }

                    } else {
                        ArrayList<IT_change> itv = get_internal_transfer_noch_value(code);
                        for (int i = 0; i < itv.size(); i++) {
                            Db_Master db9B = new Db_Master();
                            Real_oc_nochange from = new Real_oc_nochange();
                            from.setFiliale(filiale);
                            from.setCod_oc(it.getIdopen_from());
                            from.setGruppo_nc(itv.get(i).getNc_causal());
                            from.setQuantity(valueOf(parseIntR(db9B.getQuantity_real_oc_nochange(from)) + parseIntR(itv.get(i).getQuantita())));
                            from.setData(it.getDt_it());
                            db9B.update_real_oc_nochange(from);
                            Real_oc_nochange to = new Real_oc_nochange();
                            to.setFiliale(filiale);
                            to.setCod_oc(it.getIdopen_to());
                            to.setGruppo_nc(itv.get(i).getNc_causal());
                            to.setQuantity(valueOf(parseIntR(db9B.getQuantity_real_oc_nochange(to)) - parseIntR(itv.get(i).getQuantita())));
                            to.setData(it.getDt_it());
                            db9B.update_real_oc_nochange(to);
                            db9B.closeDB();

                            Db_Master db8 = new Db_Master();
                            Stock_report srFR = db8.get_Stock_report(
                                    it.getIdopen_from(), itv.get(i).getNc_causal(),
                                    "01", "NC", it.getFiliale(), it.getTill_from()
                            );
                            Stock_report srTO = db8.get_Stock_report(
                                    it.getIdopen_to(), itv.get(i).getNc_causal(),
                                    "01", "NC", it.getFiliale(), it.getTill_to()
                            );
                            db8.closeDB();

                            String codsrFR = filiale + generaId(47);
                            srFR.setCodtr(it.getCod());
                            srFR.setCodice(codsrFR);
                            srFR.setTotal("0.00");
                            srFR.setSpread("1.0000");
                            srFR.setData(it.getDt_it());
                            srFR.setQuantity(valueOf(parseIntR(itv.get(i).getQuantita())));
                            srFR.setUser(it.getUser());
                            String codsrTO = filiale + generaId(47);
                            srTO.setCodtr(it.getCod());
                            srTO.setCodice(codsrTO);
                            srTO.setTotal("0.00");
                            srTO.setSpread("1.0000");
                            srTO.setData(it.getDt_it());
                            srTO.setQuantity("-" + valueOf(parseIntR(itv.get(i).getQuantita())));
                            srTO.setUser(it.getUser());
                            Db_Master db9A = new Db_Master();
                            db9A.insert_Stockreport(srFR);
                            db9A.insert_Stockreport(srTO);
                            db9A.closeDB();

                        }
                    }

                } else {
                    ok = "2";
                }
            }
        } else {
            ok = "3";
        }
        if (ok.equals("0")) {
            redirect(request, response, "fancy_message.jsp?esito=true");
        } else {
            redirect(request, response, "fancy_delit.jsp?esito=ko" + ok + "&code=" + code);
        }
    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void del_external_transfer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String user = (String) request.getSession().getAttribute("us_cod");
        if (user == null) {
            user = "9999";
        }
        String code = request.getParameter("idtrdel");
        String motiv = request.getParameter("motiv1");
        ET_change et = get_ET_change(code);
        String ok = "0";

        if (et == null || et.getFg_annullato().equals("1")) {
            ok = "kk";
            et = new ET_change();
        }
        if (ok.equals("0")) {
            ok = verificaQuantitaIT_ET_annullare(null, et);
            if (ok.equals("0")) {
                Db_Master db1 = new Db_Master();
                boolean es = db1.delete_external_transfer(code, motiv, user);
                db1.closeDB();
                if (es) {

                    if (et.getFg_tofrom().equals("T") && et.getFg_brba().equals("BR")) {
                        Db_Master dbr = new Db_Master();
                        boolean esx = dbr.update_et_tobranch(code, code, user, et.getDt_it(), et.getCod_dest());
                        dbr.closeDB();
                    }

                    if (et.getType().equals("CH")) {
                        ArrayList<ET_change> et_va = get_ET_change_value(code);
                        for (int i = 0; i < et_va.size(); i++) {
                            Real_oc_change from = new Real_oc_change();
                            from.setFiliale(et.getFiliale());
                            from.setCod_oc(et.getIdopen_from());
                            from.setValuta(et_va.get(i).getValuta());
                            from.setKind(et_va.get(i).getSupporto());
                            from.setData(et.getDt_it());
                            Db_Master db2 = new Db_Master();
                            if (et.getFg_tofrom().equals("T")) {
                                from.setNum_kind_op(roundDoubleandFormat(((fd(db2.getField_real_oc_change(from, "num_kind_op", "0.00"))) + fd((et_va.get(i).getIp_stock()))), 0));
                                from.setValue_op(roundDoubleandFormat((fd((db2.getField_real_oc_change(from, "value_op", "0.00"))) + fd(et_va.get(i).getIp_quantity())), 2));
                            } else {
                                from.setNum_kind_op(roundDoubleandFormat(((fd(db2.getField_real_oc_change(from, "num_kind_op", "0.00"))) - fd((et_va.get(i).getIp_stock()))), 0));
                                from.setValue_op(roundDoubleandFormat((fd((db2.getField_real_oc_change(from, "value_op", "0.00"))) - fd(et_va.get(i).getIp_quantity())), 2));
                            }
                            db2.update_real_oc_change(from);
                            db2.closeDB();

                            Db_Master db8 = new Db_Master();
                            Stock_report srA = db8.get_Stock_report(et.getIdopen_from(), et_va.get(i).getValuta(), et_va.get(i).getSupporto(), "CH", filiale, et.getTill_from());
                            db8.closeDB();
                            String codsrA = filiale + generaId(47);
                            srA.setCodtr(et.getCod());
                            srA.setCodice(codsrA);
                            if (et.getFg_tofrom().equals("T")) {
                                double newtotA = fd(et_va.get(i).getIp_quantity());
                                srA.setTotal(roundDoubleandFormat(newtotA, 2));
                                srA.setQuantity(roundDoubleandFormat(fd(et_va.get(i).getIp_stock()), 0));
                            } else {
                                double newtotA = -fd(et_va.get(i).getIp_quantity());
                                srA.setTotal(roundDoubleandFormat(newtotA, 2));
                                srA.setQuantity(roundDoubleandFormat(-fd(et_va.get(i).getIp_stock()), 0));
                            }
                            srA.setSpread("1.0000");
                            srA.setData(et.getDt_it());
                            srA.setUser(et.getUser());
                            Db_Master db9A = new Db_Master();
                            db9A.insert_Stockreport(srA);
                            db9A.delete_trans_stock(et.getCod(), et.getIdopen_from());
                            db9A.closeDB();
                        }

                        if (et.getFg_tofrom().equals("T")) {
                            Db_Master db10T = new Db_Master();
                            ArrayList<String[]> listock = db10T.get_stock_quantity(code, "ET");
                            ArrayList<String[]> damod = new ArrayList<>();
                            for (int v = 0; v < listock.size(); v++) {
                                String[] vs = listock.get(v);
                                Stock st1 = db10T.get_stock(vs[0], "stock");
                                if (st1 != null) {
                                    String[] v1 = {st1.getCodice(), "", "", st1.getCodice(), vs[1], st1.getRate(), "U", roundDoubleandFormat(fd(st1.getTotal()) + fd(vs[1]), 2)};
                                    damod.add(v1);
                                } else {
                                    st1 = db10T.get_stock(vs[0], "stock_story");
                                    if (st1 != null) {
                                        db10T.insertStock(st1);
                                    }
                                }
                            }
                            db10T.updateStock(damod, false);
                            db10T.closeDB();
                        }

                        ArrayList<ET_change> et_tg = get_ET_change_tg(code);
                        for (int i = 0; i < et_tg.size(); i++) {
                            Real_oc_change from_cuts = new Real_oc_change();
                            from_cuts.setFiliale(et.getFiliale());
                            from_cuts.setCod_oc(et.getIdopen_from());
                            from_cuts.setValuta(et_tg.get(i).getValuta());
                            from_cuts.setKind(et_tg.get(i).getSupporto());
                            from_cuts.setData(et.getDt_it());
                            from_cuts.setIp_taglio(et_tg.get(i).getIp_taglio());

                            Db_Master db2 = new Db_Master();
                            if (et.getFg_tofrom().equals("T")) {
                                from_cuts.setNum_kind_op(roundDoubleandFormat((fd(db2.getField_real_oc_change_cuts(from_cuts, "ip_quantity", "0.00"))
                                        + fd(et_tg.get(i).getIp_quantity())), 0));
                                from_cuts.setValue_op(roundDoubleandFormat((fd(db2.getField_real_oc_change_cuts(from_cuts, "ip_value", "0.00"))
                                        + fd(et_tg.get(i).getIp_total())), 2));
                            } else {
                                from_cuts.setNum_kind_op(roundDoubleandFormat((fd(db2.getField_real_oc_change_cuts(from_cuts, "ip_quantity", "0.00"))
                                        - fd(et_tg.get(i).getIp_quantity())), 0));
                                from_cuts.setValue_op(roundDoubleandFormat((fd(db2.getField_real_oc_change_cuts(from_cuts, "ip_value", "0.00"))
                                        - fd(et_tg.get(i).getIp_total())), 2));
                            }
                            db2.update_real_oc_change_cuts(from_cuts);
                            db2.closeDB();
                        }
                    } else {
                        ArrayList<ET_change> et_va = get_ET_nochange_value(code);
                        for (int i = 0; i < et_va.size(); i++) {
                            Db_Master db2 = new Db_Master();
                            Real_oc_nochange from = new Real_oc_nochange();
                            from.setFiliale(et.getFiliale());
                            from.setCod_oc(et.getIdopen_from());
                            from.setGruppo_nc(et_va.get(i).getNc_causal());
                            if (et.getFg_tofrom().equals("T")) {
                                from.setQuantity(valueOf(parseIntR(db2.getQuantity_real_oc_nochange(from)) + parseIntR(et_va.get(i).getIp_quantity())));
                            } else {
                                from.setQuantity(valueOf(parseIntR(db2.getQuantity_real_oc_nochange(from)) - parseIntR(et_va.get(i).getIp_quantity())));
                            }
                            from.setData(et_va.get(i).getDt_it());
                            db2.update_real_oc_nochange(from);
                            db2.closeDB();

                            //MODIFICO STOCK
                            Db_Master db8 = new Db_Master();
                            Stock_report srTO = db8.get_Stock_report(
                                    et.getIdopen_from(), et_va.get(i).getNc_causal(),
                                    "01", "NC", et.getFiliale(), et.getTill_from()
                            );
                            db8.closeDB();

                            String codsrTO = filiale + generaId(47);
                            srTO.setCodtr(et.getCod());
                            srTO.setCodice(codsrTO);
                            srTO.setTotal("0.00");
                            srTO.setSpread("1.0000");
                            srTO.setData(et.getDt_it());
                            String direction = "";
                            if (et.getFg_tofrom().equals("F")) {
                                direction = "-";
                            }
                            srTO.setQuantity(direction + valueOf(parseIntR(et_va.get(i).getIp_quantity())));
                            srTO.setUser(et.getUser());
                            Db_Master db9A = new Db_Master();
                            db9A.insert_Stockreport(srTO);
                            db9A.closeDB();
                        }
                    }
                } else {
                    ok = "2";
                }
            }
        }

        if (ok.equals("0")) {
            redirect(request, response, "fancy_message.jsp?esito=true");
        } else {
            redirect(request, response, "fancy_delet.jsp?esito=ko" + ok + "&code=" + code);
        }
    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void delete_transaction_noch(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String user = (String) request.getSession().getAttribute("us_cod");
        if (user == null) {
            user = "9999";
        }
        String code = request.getParameter("idtrdel");
        String motiv = request.getParameter("motiv1");
        Db_Master db = new Db_Master();
        DateTime today = db.getCurdateDT();
        String[] loc_cur = db.get_local_currency();
        String dateoper = new DateTime().toString(patternsqldate);
        NC_transaction nct = db.get_NC_transaction(code);
        db.closeDB();
        String ok = "0";

        if (nct == null || nct.getDel_fg().equals("1")) {
            ok = "kk";
        } else {

            boolean activefr = false;
            Db_Master db0 = new Db_Master();
            ArrayList<Till> array_till = db0.list_till_status("O", user, nct.getFiliale());
//            ArrayList<Till> array_till = db0.list_till_status("O", null, nct.getFiliale());
            db0.closeDB();
            for (int j = 0; j < array_till.size(); j++) {
                if (array_till.get(j).getId_opcl().equals(nct.getId_open_till())) {
                    DateTime dt1 = getDT(nct.getData().substring(0, 10), patternsql);
                    activefr = today.isEqual(dt1);
                    break;
                }
            }

            if (activefr) {

                Db_Master db1 = new Db_Master();
                ArrayList<Till> list_till = db1.list_ALLtill();
                db1.closeDB();
                Till tdest = getContainsTill(nct.getTill(), list_till);

                boolean controlliprima = false;

                String quantitaNCdaverificare = replace(nct.getQuantita(), "-", "").trim();
                String totaledausare = replace(nct.getTotal(), "-", "").trim();

                switch (nct.getFg_inout()) {
                    case "1":
                    case "3":
                        if (nct.getSupporto().equals("01")) {
                            //verifico solo euro in cassa
                            Real_oc_change to = new Real_oc_change();
                            to.setFiliale(nct.getFiliale());
                            to.setCod_oc(nct.getId_open_till());
                            to.setValuta(loc_cur[0]);
                            to.setKind(nct.getSupporto());
                            to.setData(dateoper);
                            to.setNum_kind_op("0");
                            Db_Master db2 = new Db_Master();
                            double euroincassa = fd(db2.getField_real_oc_change(to, "value_op", "0.00"));
                            db2.closeDB();
                            double eurodascalare = fd(totaledausare);
                            if (eurodascalare <= euroincassa) {
                                controlliprima = true;
                            }
                        } else {
                            controlliprima = true;
                        }
                        break;
                    case "2":
                        //nessun controllo perchè aggiungo solo euro
                        controlliprima = true;
                        break;
                    case "4":
                        Real_oc_nochange from = new Real_oc_nochange();
                        from.setFiliale(filiale);
                        from.setCod_oc(nct.getId_open_till());
                        from.setGruppo_nc(nct.getGruppo_nc());
                        Db_Master db2 = new Db_Master();
                        double ncincassa = fd(db2.getQuantity_real_oc_nochange(from));
                        db2.closeDB();
                        double ncdascalare = fd(quantitaNCdaverificare);
                        if (ncdascalare <= ncincassa) {
                            controlliprima = true;
                        }
                        break;
                    default:
                        break;
                }

                boolean es = true;

                if (controlliprima) {
                    Db_Master db3 = new Db_Master();
                    es = db3.delete_transaction_noch(code, motiv, user);
                    db3.closeDB();
                    if (es) {
                        switch (nct.getFg_inout()) {
                            case "1":
                                //Money IN
                                if (nct.getSupporto().equals("06") || nct.getSupporto().equals("07") || nct.getSupporto().equals("08")) {
                                    //creditcard
                                    Real_oc_pos rop = new Real_oc_pos();
                                    rop.setFiliale(nct.getFiliale());
                                    rop.setCod_oc(nct.getId_open_till());
                                    rop.setValuta(loc_cur[0]);
                                    rop.setKind(nct.getSupporto());
                                    rop.setData(dateoper);
                                    rop.setCarta_credito(nct.getPos());
                                    Db_Master db4 = new Db_Master();
                                    rop.setIp_quantity(valueOf(parseIntR(db4.getField_real_oc_pos(rop, "ip_quantity", "0")) - 1));
                                    rop.setIp_value(roundDoubleandFormat((fd(db4.getField_real_oc_pos(rop, "ip_value", "0.00")) - fd(totaledausare)), 2));
                                    db4.update_real_oc_pos(rop);
                                    db4.closeDB();
                                } else {
                                    Real_oc_change to = new Real_oc_change();
                                    to.setFiliale(nct.getFiliale());
                                    to.setCod_oc(nct.getId_open_till());
                                    to.setValuta(loc_cur[0]);
                                    to.setKind(nct.getSupporto());
                                    to.setData(dateoper);
                                    to.setNum_kind_op("0");
                                    Db_Master db4 = new Db_Master();
                                    to.setValue_op(roundDoubleandFormat((fd(db4.getField_real_oc_change(to, "value_op", "0.00")) - fd(totaledausare)), 2));
                                    db4.update_real_oc_change(to);
                                    db4.closeDB();

                                    Db_Master db8 = new Db_Master();
                                    Stock_report sr = db8.get_Stock_report(nct.getId_open_till(), loc_cur[0], "01", "CH", nct.getFiliale(), nct.getTill());
                                    db8.closeDB();
                                    //  DIMINIUSCO EURO
                                    String codsr = nct.getFiliale() + generaId(47);
                                    sr.setCodtr(nct.getCod());
                                    sr.setCodice(codsr);
                                    double newtot = fd(totaledausare);
                                    sr.setTotal(roundDoubleandFormat(-newtot, 2));
                                    sr.setSpread("1.0000");
                                    sr.setData(dateoper);
                                    sr.setQuantity("0");
                                    sr.setUser(user);
                                    Db_Master db9 = new Db_Master();
                                    db9.insert_Stockreport(sr);
                                    db9.closeDB();

                                    //euro stock meno
                                    Db_Master dbS = new Db_Master();
                                    ArrayList<Stock> al = dbS.list_stock(nct.getFiliale(), "01", loc_cur[0]);
                                    ArrayList<String[]> damod = new ArrayList<>();
                                    double quant_now = fd(totaledausare);
                                    double quant_check = quant_now;
                                    for (int i = 0; i < al.size() && quant_check > 0; i++) {
                                        double vq1 = fd(al.get(i).getTotal());
                                        if (vq1 >= quant_check) {
                                            String[] v = {al.get(i).getCodice(), "", "", al.get(i).getCodice(), roundDoubleandFormat(quant_check, 2), al.get(i).getRate(), "U",
                                                roundDoubleandFormat(vq1 - quant_check, 2)};
                                            damod.add(v);
                                            quant_check = 0.0;
                                        } else {
                                            String[] v = {al.get(i).getCodice(), "", "", al.get(i).getCodice(), roundDoubleandFormat(quant_check, 2), al.get(i).getRate(), "U", "0.00"};//07/11
                                            damod.add(v);
                                            quant_check = quant_check - vq1;
                                        }
                                    }
                                    dbS.updateStock(damod, false);
                                    dbS.closeDB();
                                }
                                break;
                            case "2": {
                                //Money OUT

                                Real_oc_change to = new Real_oc_change();
                                to.setFiliale(nct.getFiliale());
                                to.setCod_oc(nct.getId_open_till());
                                to.setValuta(loc_cur[0]);
                                to.setKind("01");
                                to.setData(dateoper);
                                to.setNum_kind_op("0");
                                Db_Master db5 = new Db_Master();
                                to.setValue_op(roundDoubleandFormat((fd(db5.getField_real_oc_change(to, "value_op", "0.00")) + fd(totaledausare)), 2));
                                db5.update_real_oc_change(to);
                                db5.closeDB();
                                //ADD EURO STOCK
                                Stock st1 = new Stock();
                                st1.setCodice("ST" + generaId(48));
                                st1.setFiliale(nct.getFiliale());
                                st1.setTipo("CH");
                                st1.setTill(nct.getSupporto());
                                st1.setIdoperation(nct.getCod());
                                st1.setCodiceopenclose(nct.getId_open_till());
                                st1.setTipostock("CH");
                                st1.setCod_value(loc_cur[0]);
                                st1.setKind("01");
                                st1.setTotal(totaledausare);
                                st1.setRate("1.00000000");
                                st1.setControval(totaledausare);
                                st1.setUser(user);
                                st1.setDate(dateoper);
                                st1.setId_op(nct.getId_open_till());
                                Db_Master db01 = new Db_Master();
                                db01.insertStock(st1);
                                db01.closeDB();
                                Db_Master db8 = new Db_Master();
                                Stock_report sr = db8.get_Stock_report(nct.getId_open_till(), loc_cur[0], "01", "CH", nct.getFiliale(), nct.getTill());
                                db8.closeDB();
                                //  AUMENTO EURO
                                String codsr = nct.getFiliale() + generaId(47);
                                sr.setCodtr(nct.getCod());
                                sr.setCodice(codsr);
                                sr.setTotal(roundDoubleandFormat(fd(totaledausare), 2));
                                sr.setSpread("1.0000");
                                sr.setData(dateoper);
                                sr.setQuantity("0");
                                sr.setUser(user);
                                Db_Master db9 = new Db_Master();
                                db9.insert_Stockreport(sr);
                                db9.closeDB();
                                break;
                            }
                            case "3": {
                                //Money IN - Stock OUT

////                AUMENTARE no stock
                                Real_oc_nochange from = new Real_oc_nochange();
                                from.setFiliale(nct.getFiliale());
                                from.setCod_oc(nct.getId_open_till());
                                from.setGruppo_nc(nct.getGruppo_nc());
                                Db_Master db6 = new Db_Master();
                                from.setQuantity(valueOf(parseIntR(db6.getQuantity_real_oc_nochange(from)) + parseIntR(nct.getQuantita())));
                                from.setData(dateoper);
                                db6.update_real_oc_nochange(from);
                                db6.closeDB();
//AUMENTARE
                                Db_Master db8 = new Db_Master();
                                Stock_report sr = db8.get_Stock_report(nct.getId_open_till(), nct.getGruppo_nc(), "01", "NC", nct.getFiliale(), nct.getTill());
                                db8.closeDB();
                                String codsr = nct.getFiliale() + generaId(47);
                                sr.setCodtr(nct.getCod());
                                sr.setCodice(codsr);
                                sr.setTotal("0.00");
                                sr.setSpread("1.0000");
                                sr.setData(dateoper);
                                sr.setQuantity("" + parseIntR(nct.getQuantita()));
                                sr.setUser(user);
                                Db_Master db9 = new Db_Master();
                                db9.insert_Stockreport(sr);
                                db9.closeDB();
                                if (nct.getSupporto().equals("06") || nct.getSupporto().equals("07") || nct.getSupporto().equals("08")) {
                                    //creditcard
                                    Real_oc_pos rop = new Real_oc_pos();
                                    rop.setFiliale(nct.getFiliale());
                                    rop.setCod_oc(nct.getId_open_till());
                                    rop.setValuta(loc_cur[0]);
                                    rop.setKind(nct.getSupporto());
                                    rop.setData(dateoper);
                                    rop.setCarta_credito(nct.getPos());
                                    Db_Master db5 = new Db_Master();
                                    rop.setIp_quantity(valueOf(parseIntR(db5.getField_real_oc_pos(rop, "ip_quantity", "0")) - 1));
                                    rop.setIp_value(roundDoubleandFormat((fd(db5.getField_real_oc_pos(rop, "ip_value", "0.00")) - fd(totaledausare)), 2));
                                    db5.update_real_oc_pos(rop);
                                    db5.closeDB();
                                } else {
                                    Real_oc_change to = new Real_oc_change();
                                    to.setFiliale(nct.getFiliale());
                                    to.setCod_oc(nct.getId_open_till());
                                    to.setValuta(loc_cur[0]);
                                    to.setKind(nct.getSupporto());
                                    to.setData(dateoper);
                                    to.setNum_kind_op("0");
                                    Db_Master db5 = new Db_Master();
                                    to.setValue_op(roundDoubleandFormat((fd(db5.getField_real_oc_change(to, "value_op", "0.00")) - fd(totaledausare)), 2));
                                    db5.update_real_oc_change(to);
                                    db5.closeDB();
                                    Db_Master db8A = new Db_Master();
                                    Stock_report sr1 = db8A.get_Stock_report(nct.getId_open_till(), loc_cur[0], "01", "CH", nct.getFiliale(), nct.getTill());
                                    db8A.closeDB();
                                    //  DIM EURO
                                    String codsrA = nct.getFiliale() + generaId(47);
                                    sr1.setCodtr(nct.getCod());
                                    sr1.setCodice(codsrA);
                                    double newtot = -fd(totaledausare);
                                    sr1.setTotal(roundDoubleandFormat(newtot, 2));
                                    sr1.setSpread("1.0000");
                                    sr1.setData(dateoper);
                                    sr1.setQuantity("0");
                                    sr1.setUser(user);
                                    Db_Master db9A = new Db_Master();
                                    db9A.insert_Stockreport(sr1);
                                    db9A.closeDB();
                                    //euro stock meno
                                    Db_Master dbS = new Db_Master();
                                    ArrayList<Stock> al = dbS.list_stock(nct.getFiliale(), "01", loc_cur[0]);
                                    ArrayList<String[]> damod = new ArrayList<>();
                                    double quant_now = fd(totaledausare);
                                    double quant_check = quant_now;
                                    for (int i = 0; i < al.size() && quant_check > 0; i++) {
                                        double vq1 = fd(al.get(i).getTotal());
                                        if (vq1 >= quant_check) {
                                            String[] v = {al.get(i).getCodice(), "", "", al.get(i).getCodice(), roundDoubleandFormat(quant_check, 2), al.get(i).getRate(), "U",
                                                roundDoubleandFormat(vq1 - quant_check, 2)};
                                            damod.add(v);
                                            quant_check = 0.0;
                                        } else {
                                            String[] v = {al.get(i).getCodice(), "", "", al.get(i).getCodice(), roundDoubleandFormat(quant_check, 2), al.get(i).getRate(), "U", "0.00"};//07/11
                                            damod.add(v);
                                            quant_check = quant_check - vq1;
                                        }
                                    }
                                    dbS.updateStock(damod, false);
                                    dbS.closeDB();
                                }
                                break;
                            }
                            case "4": {
                                //Money OUT - Stock IN

                                if (!tdest.isSafe()) {

                                    Real_oc_change to = new Real_oc_change();
                                    to.setFiliale(nct.getFiliale());
                                    to.setCod_oc(nct.getId_open_till());
                                    to.setValuta(loc_cur[0]);
                                    to.setKind("01");
                                    to.setData(dateoper);
                                    to.setNum_kind_op("0");
                                    Db_Master db5 = new Db_Master();
                                    to.setValue_op(roundDoubleandFormat((fd(db5.getField_real_oc_change(to, "value_op", "0.00")) + fd(totaledausare)), 2));
                                    db5.update_real_oc_change(to);
                                    db5.closeDB();

                                    Db_Master db8A = new Db_Master();
                                    Stock_report sr1 = db8A.get_Stock_report(nct.getId_open_till(), loc_cur[0], "01", "CH", nct.getFiliale(), nct.getTill());
                                    db8A.closeDB();

                                    //  AUM EURO
                                    String codsrA = nct.getFiliale() + generaId(47);
                                    sr1.setCodtr(nct.getCod());
                                    sr1.setCodice(codsrA);
                                    double newtot = fd(totaledausare);
                                    sr1.setTotal(roundDoubleandFormat(newtot, 2));
                                    sr1.setSpread("1.0000");
                                    sr1.setData(dateoper);
                                    sr1.setQuantity("0");
                                    sr1.setUser(user);
                                    Db_Master db9A = new Db_Master();
                                    db9A.insert_Stockreport(sr1);
                                    db9A.closeDB();

                                    //ADD EURO STOCK
                                    Stock st1 = new Stock();
                                    st1.setCodice("ST" + generaId(48));
                                    st1.setFiliale(nct.getFiliale());
                                    st1.setTipo("CH");
                                    st1.setTill(nct.getTill());
                                    st1.setIdoperation(nct.getCod());
                                    st1.setCodiceopenclose(nct.getId_open_till());
                                    st1.setTipostock("CH");
                                    st1.setCod_value(loc_cur[0]);
                                    st1.setKind("01");
                                    st1.setTotal(totaledausare);
                                    st1.setRate("1.00000000");
                                    st1.setControval(totaledausare);
                                    st1.setUser(user);
                                    st1.setDate(dateoper);
                                    st1.setId_op(nct.getId_open_till());
                                    Db_Master db7 = new Db_Master();
                                    db7.insertStock(st1);
                                    db7.closeDB();

                                }       //diminuire no stock 
                                Real_oc_nochange from = new Real_oc_nochange();
                                from.setFiliale(filiale);
                                from.setCod_oc(nct.getId_open_till());
                                from.setGruppo_nc(nct.getGruppo_nc());
                                Db_Master db8a = new Db_Master();
                                from.setQuantity(valueOf(parseIntR(db8a.getQuantity_real_oc_nochange(from)) - parseIntR(nct.getQuantita())));
                                from.setData(dateoper);
                                db8a.update_real_oc_nochange(from);
                                db8a.closeDB();
                                //AUMENTARE
                                Db_Master db8 = new Db_Master();
                                Stock_report sr = db8.get_Stock_report(nct.getId_open_till(), nct.getGruppo_nc(), "01", "NC", nct.getFiliale(), nct.getTill());
                                db8.closeDB();
                                String codsr = nct.getFiliale() + generaId(47);
                                sr.setCodtr(nct.getCod());
                                sr.setCodice(codsr);
                                sr.setTotal("0.00");
                                sr.setSpread("1.0000");
                                sr.setData(dateoper);
                                sr.setQuantity(valueOf(-parseIntR(nct.getQuantita())));
                                sr.setUser(user);
                                Db_Master db9 = new Db_Master();
                                db9.insert_Stockreport(sr);
                                db9.closeDB();
                                break;
                            }
                            default:
                                break;
                        }
                    }

                } else {
                    ok = "Q";
                }

                if (!es) {
                    ok = "1";
                }
            } else {
                ok = "CC";
            }
        }

        // raf
        redirect(request, response, "fancy_message.jsp?esito=" + ok);
    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void sendmailclient(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        String redir = getValue_request(request, "redir", false, "");
        String codtr = Utility.sanitize(getValue_request(request, "codtr", false, ""));
        String maildest = Utility.sanitize(getValue_request(request, "maildest", false, ""));
//            maildest = "developers@smartoop.it";
        Ch_transaction tr = Engine.query_transaction_ch(codtr);
        ArrayList<Ch_transaction_doc> doc = Engine.get_list_tr_doc_view(codtr);
        String idtr = tr.getFiliale() + tr.getId();
        boolean es = Utility.send_mail_documenti(idtr, maildest, doc);
        if (es) {
            redirect(request, response, "transaction_redir.jsp?cod=" + codtr + "&esito=OK");
        } else {
            redirect(request, response, "transaction_redir.jsp?cod=" + codtr + "&esito=KO");
        }
    }

    protected void upload_cur02(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Db_Master dbm = new Db_Master();
        String extacc = dbm.getConf("ext.excel.file");
        String path = dbm.getPath("temp");
        dbm.closeDB();
        String cod = generaId(50);
        DateTime now = new DateTime();

        String day = now.toString(patterndir);
//        String dt = now.toString(patternsqldate);

        String dtval = null;

        boolean isMultipart = isMultipartContent(request);
        File nomefile = null;
        String msg = "0";

        if (isMultipart) {
            try {
                FileItemFactory factory = new DiskFileItemFactory();
                ServletFileUpload upload = new ServletFileUpload(factory);
                List items = upload.parseRequest(request);
                Iterator iterator = items.iterator();
                while (iterator.hasNext()) {
                    FileItem item = (FileItem) iterator.next();
                    if (!item.isFormField()) {
                        String fileName = item.getName();
                        if (fileName != null) {
                            File pathdir = new File(path + day);
                            pathdir.mkdirs();
                            String estensione = getExtension(fileName);
                            if (extacc.toLowerCase().contains(estensione.toLowerCase())) {
                                String name = cod + "_" + day + estensione;
                                nomefile = new File(pathdir + separator + name);
                                try {
                                    item.write(nomefile);
                                    break;
                                } catch (Exception ex) {
                                    msg = "1";
                                    insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
                                    nomefile = null;
                                    break;
                                }
                            } else {
                                msg = "1";
                                nomefile = null;
                                break;
                            }
                        }
                    } else if (item.getFieldName().equals("dt_val")) {
                        dtval = item.getString() + ":00";

                    }
                }
            } catch (FileUploadException ex) {
                insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
                msg = "1";
                nomefile = null;
            }
        }

        if (nomefile != null && dtval != null) {
            insert_excel_upl_SP(generaId(50), "SP",
                    nomefile.getName(),
                    getStringBase64(nomefile),
                    session.getAttribute("us_cod") + " | " + session.getAttribute("us_user"), dtval);
        } else {
            msg = "1";
        }
        if (msg.equals("0")) {
            redirect(request, response, "tb_currency.jsp?esito=okcur2");
        } else {
            redirect(request, response, "tb_currency.jsp?esito=errcu" + msg);
        }

    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void upload_cur01(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Db_Master dbm = new Db_Master();
        String extacc = dbm.getConf("ext.excel.file");
        String path = dbm.getPath("temp");
        dbm.closeDB();
        String cod = generaId(50);
        DateTime now = new DateTime();
//        String dtst = now.plusDays(1).toString("dd/MM/yyyy") + " 00:01";

        String day = now.toString(patterndir);
//        String dt = now.toString(patternsqldate);

        String dtval = null;

        boolean isMultipart = isMultipartContent(request);
        File nomefile = null;
        String msg = "0";

        if (isMultipart) {
            try {
                FileItemFactory factory = new DiskFileItemFactory();
                ServletFileUpload upload = new ServletFileUpload(factory);
                List items = upload.parseRequest(request);
                Iterator iterator = items.iterator();
                while (iterator.hasNext()) {
                    FileItem item = (FileItem) iterator.next();
                    if (!item.isFormField()) {
                        String fileName = item.getName();
                        if (fileName != null) {
                            File pathdir = new File(path + day);
                            pathdir.mkdirs();
                            String estensione = getExtension(fileName);
                            if (extacc.toLowerCase().contains(estensione.toLowerCase())) {
                                String name = cod + "_" + day + estensione;
                                nomefile = new File(pathdir + separator + name);
                                try {
                                    item.write(nomefile);
                                    break;
                                } catch (Exception ex) {
                                    msg = "1";
                                    insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
                                    nomefile = null;
                                    break;
                                }
                            } else {
                                msg = "1";
                                nomefile = null;
                                break;
                            }
                        }
                    } else if (item.getFieldName().equals("dt_val")) {
                        dtval = item.getString() + ":00";

                    }
                }
            } catch (FileUploadException ex) {
                insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
                msg = "1";
                nomefile = null;
            }
        }

        if (nomefile != null && dtval != null) {

            insert_excel_upl(generaId(50), "CU",
                    nomefile.getName(),
                    getStringBase64(nomefile),
                    session.getAttribute("us_cod") + " | " + session.getAttribute("us_user"), dtval);

//            ArrayList<String[]> listcurr = Excel.getReadEXCELCURRENCY(nomefile);
//            if (listcurr != null) {
//                boolean es = true;
//                for (int i = 0; i < listcurr.size(); i++) {
//                    ArrayList<String> libr = dbm.list_branchcode();
//                    for (int j = 0; j < libr.size(); j++) {
//                        if (dbm.getPresenzaValuta(libr.get(j), listcurr.get(i)[0], listcurr.get(i)[1])) {
//                            es = dbm.update_change_BCE(libr.get(j), listcurr.get(i)[0], listcurr.get(i)[1], dtval, session.getAttribute("us_cod").toString());
//                            if (!es) {
//                                break;
//                            } else {
//                                String msg_rh = "Upload excel bce. <br> BCE value " + listcurr.get(i)[1] + " <br>Date validity: " + dtval;
//                                Rate_history rh = new Rate_history(generaId(50), libr.get(j),
//                                        listcurr.get(i)[0], "0", msg_rh, session.getAttribute("us_cod").toString(),
//                                        formatStringtoStringDate_null(dtval, patternnormdate, patternsqldate));
//                                dbm.insert_ratehistory(rh);
//                            }
//                        } else {
//                            es = false;
//                            break;
//                        }
//                    }
//                }
//                if (!es) {
//                    msg = "3";
//                }
//            } else {
//                msg = "2";
//            }
        } else {
            msg = "1";
        }

        if (msg.equals("0")) {
//            if (nomefile != null) {
//
//                Engine.insert_excel_upl(generaId(50), "CU", nomefile.getName(),
//                        getStringBase64(nomefile),
//                        session.getAttribute("us_cod") + " | " + session.getAttribute("us_user"));
//
//            }
            redirect(request, response, "tb_currency.jsp?esito=okcur1");
        } else {
            redirect(request, response, "tb_currency.jsp?esito=errcu" + msg);
        }

    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void uploadNL(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = (String) request.getSession().getAttribute("us_cod");
        if (username == null) {
            username = "9999";
        }

        String cod = generaId();
        String reci = "";
        String title = "";
        String descr = "";
        String dates = "";
        DateTime now = new DateTime();
        String day = now.toString(patterndir);
        String upl = now.toString(patternsqldate);

        boolean isMultipart = isMultipartContent(request);
        File nomefile = null;
        String msg = "0";
        if (isMultipart) {
            try {
                FileItemFactory factory = new DiskFileItemFactory();
                ServletFileUpload upload = new ServletFileUpload(factory);
                List items = upload.parseRequest(request);
                Iterator iterator = items.iterator();
                while (iterator.hasNext()) {
                    FileItem item = (FileItem) iterator.next();
                    if (item.isFormField()) {
                        if (item.getFieldName().equals("reci")) {
                            reci = item.getString().trim();
                        }
                        if (item.getFieldName().equals("title")) {
                            title = item.getString().trim();
                        }
                        if (item.getFieldName().equals("descr")) {
                            descr = item.getString().trim();
                        }
                        if (item.getFieldName().equals("dates")) {
                            dates = item.getString().trim();
                        }
                    } else {
                        String fileName = item.getName();
                        if (fileName != null) {
                            if (fileName.toLowerCase().endsWith(".pdf")) {
                                Db_Master dbm = new Db_Master();
                                File pathdir = new File(dbm.getPath("temp") + day);
                                dbm.closeDB();
                                pathdir.mkdirs();
                                String estensione = fileName.substring(fileName.lastIndexOf("."));
                                String name = cod + "_" + day + estensione;
                                nomefile = new File(pathdir + separator + name);
                                try {
                                    item.write(nomefile);
                                } catch (Exception ex) {
                                    msg = "1";
                                    insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
                                    nomefile = null;
                                }
                            } else {
                                msg = "1";
                                insertTR("E", "System", new Exception().getStackTrace()[0].getMethodName() + ": " + "File non concorde.");
                                nomefile = null;
                            }
                        }
                    }
                }
            } catch (FileUploadException ex) {
                insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
                msg = "1";
                nomefile = null;
            }
        }

        if (nomefile != null) {
            if (checkPDF(nomefile)) {
                String base64 = getStringBase64(nomefile);
                if (base64 != null) {
                    Newsletters nw = new Newsletters();
                    nw.setCod(cod);
                    nw.setDescr(descr);
                    nw.setDest(reci);
                    nw.setDt_updatestart(dates);
                    nw.setDt_upload(upl);
                    nw.setTitolo(title);
                    nw.setFileout(base64);
                    nw.setUser(username);
                    Db_Master dbm = new Db_Master();
                    boolean es = dbm.insert_new_News_UPL(nw);
                    dbm.closeDB();
                    if (es) {
                    } else {
                        msg = "5";
                    }
                } else {
                    msg = "3";
                }
            } else {
                msg = "4";
            }
        } else {
            msg = "2";
        }

        redirect(request, response, "nl_add.jsp?esito=" + msg);
    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void unlockBlockedOperation(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String page = Utility.sanitize(request.getParameter("so"));
        Db_Master db = new Db_Master();
        boolean es = db.updateBlockedOperation(session.getAttribute("us_cod") + " | " + session.getAttribute("us_user"), "0");
        db.closeDB();
        if (es) {
            redirect(request, response, page);
        } else {
            redirect(request, response, page + "?esito=" + es);
        }

    }

    private boolean checkquantity_nc(HttpServletRequest request, ArrayList<String[]> list_oc_nochange) {
        boolean output = false;
        for (int i = 0; i < list_oc_nochange.size(); i++) {
            String nc_quantnow = request.getParameter("nc_quantnow" + i);
            if (nc_quantnow != null) {
                if (!nc_quantnow.trim().equals("") && !nc_quantnow.trim().equals("0") && checkNumber(nc_quantnow.trim())) {
                    String nc_quantold = request.getParameter("nc_quantold" + i);
                    if (fd(nc_quantold) >= fd(formatDoubleforMysql(nc_quantnow))) {
                        output = true;
                    } else {
                        output = false;
                        break;
                    }
                }
            }
        }
        return output;
    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void it_nochange(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();
        String user = (String) session.getAttribute("us_cod");
        if (user == null) {
            user = "9999";
        }
        boolean quant = false;
//        Db_Master db = new Db_Master();
        String ok = "0";

        String idopentillfrom = request.getParameter("idopentillfrom");
        String idopentillto = request.getParameter("idopentillto");
        String tillfrom = request.getParameter("tillfrom");
        String tillto = request.getParameter("tillto");

        String cod = "ITN" + generaId(22);
        IT_change it = new IT_change();

        it.setTill_from(tillfrom);
        it.setTill_to(tillto);
        it.setIdopen_from(idopentillfrom);
        it.setIdopen_to(idopentillto);
        it.setCod(cod);
        it.setFiliale(filiale);
        it.setUser(user);
        it.setFg_annullato("0");

        //DEL
        it.setDel_dt("1901-01-01 00:00:00");
        it.setDel_user("-");
        it.setDel_motiv("-");
        String oper = new DateTime().toString(patternsqldate);
        ArrayList<String[]> list_oc_nochange = list_oc_nochange_real(idopentillfrom);

        boolean output = checkquantity_nc(request, list_oc_nochange);

        if (output) {
            Db_Master db1 = new Db_Master();
            boolean es = db1.insert_IT_change(it);
            db1.closeDB();
            if (es) {
                for (int i = 0; i < list_oc_nochange.size(); i++) {
                    String nc_quantnow = request.getParameter("nc_quantnow" + i);
                    if (nc_quantnow != null) {
                        nc_quantnow = valueOf(parseIntR(formatDoubleforMysql(request.getParameter("nc_quantnow" + i))));

                        if (!nc_quantnow.trim().equals("") && !nc_quantnow.trim().equals("0") && checkNumber(nc_quantnow.trim())) {
                            quant = true;
                            String nc_quantold = request.getParameter("nc_quantold" + i);
                            String nc_causal = request.getParameter("nc_causal" + i);

                            if (fd(nc_quantold) >= fd(formatDoubleforMysql(nc_quantnow))) {
                                IT_change it2 = new IT_change();
                                it2.setFiliale(filiale);
                                it2.setCod(cod);
                                it2.setQuantita(nc_quantnow.trim());
                                it2.setDt_it(oper);
                                it2.setNc_causal(nc_causal);
                                Db_Master db2 = new Db_Master();
                                boolean es2 = db2.insert_IT_nochange_value(it2);
                                db2.closeDB();
                                if (es2) {

                                    Real_oc_nochange from = new Real_oc_nochange();
                                    from.setFiliale(filiale);
                                    from.setCod_oc(idopentillfrom);
                                    from.setGruppo_nc(nc_causal);
                                    from.setQuantity(valueOf(parseIntR(nc_quantold) - parseIntR(nc_quantnow)));
                                    from.setData(oper);
                                    Db_Master db3 = new Db_Master();
                                    db3.update_real_oc_nochange(from);
                                    Real_oc_nochange to = new Real_oc_nochange();
                                    to.setFiliale(filiale);
                                    to.setCod_oc(idopentillto);
                                    to.setGruppo_nc(nc_causal);
                                    to.setQuantity(valueOf(parseIntR(db3.getQuantity_real_oc_nochange(to)) + parseIntR(nc_quantnow)));
                                    to.setData(oper);

                                    db3.update_real_oc_nochange(to);
                                    db3.closeDB();
                                    //DIMUISCO STOCK FROM
                                    Db_Master db8 = new Db_Master();
                                    Stock_report sr = db8.get_Stock_report(idopentillfrom, nc_causal, "01", "NC", filiale, tillfrom);
                                    Stock_report srTO = db8.get_Stock_report(idopentillto, nc_causal, "01", "NC", filiale, tillto);
                                    db8.closeDB();
                                    String codsr = filiale + generaId(47);
                                    sr.setCodtr(cod);
                                    sr.setCodice(codsr);
                                    sr.setTotal("0.00");
                                    sr.setSpread("1.0000");
                                    sr.setData(oper);
                                    sr.setQuantity(valueOf(-parseIntR(nc_quantnow)));
                                    sr.setUser(user);
                                    Db_Master db9 = new Db_Master();
                                    db9.insert_Stockreport(sr);
                                    db9.closeDB();

                                    //AUMENTO STOCK TO
                                    String codsrTO = filiale + generaId(47);
                                    srTO.setCodtr(cod);
                                    srTO.setCodice(codsrTO);
                                    srTO.setTotal("0.00");
                                    srTO.setSpread("1.0000");
                                    srTO.setData(oper);
                                    srTO.setQuantity(valueOf(parseIntR(nc_quantnow)));
                                    srTO.setUser(user);
                                    Db_Master db9A = new Db_Master();
                                    db9A.insert_Stockreport(srTO);
                                    db9A.closeDB();

                                } else {
                                    ok = "2IN"; //ko insert value
                                    break;
                                }
                            } else {
                                ok = "1Q"; //ko insert value
                                break;
                            }
                        }
                    }
                }
            } else {
                ok = "2IN"; //ko insert value
            }
        } else {
            ok = "Q";
        }
        Db_Master db4 = new Db_Master();
        db4.updateBlockedOperation(session.getAttribute("us_cod") + " | " + session.getAttribute("us_user"), "0");
        db4.closeDB();
        try {
            sleep(500);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();

        }

        if (ok.equals("Q")) {
            redirect(request, response, "it_nochange.jsp?search=sra1&idopentillfrom=" + idopentillfrom + "&idopentillto=" + idopentillto + "&tillfrom=" + tillfrom + "&tillto=" + tillto + "&esito=" + ok);
        } else if (ok.equals("0") && quant) {
            //delete temp
            delete_IT_nochange_temp(idopentillfrom, idopentillto, filiale);
            redirect(request, response, "it_nochange.jsp?esito=OK");
        } else {
            //delete all
            delete_IT_nochange(cod, idopentillfrom, idopentillto, filiale);
            redirect(request, response, "it_nochange.jsp?search=sra1&idopentillfrom=" + idopentillfrom + "&idopentillto=" + idopentillto + "&tillfrom=" + tillfrom + "&tillto=" + tillto + "&esito=" + ok);
        }
    }

    private boolean verifyquantIT(String safefr, String s_quantold, String s_quantnow) {
        if (!safefr.equals("true")) {
            return true;
        } else {
            return fd(s_quantold) >= fd(s_quantnow);
        }
    }

    private static boolean checkquantity(boolean add, String old, String now) {
        if (add) {
            return true;
        } else {
            try {
                return fd(old) >= fd(formatDoubleforMysql(now));
            } catch (Exception ex) {
            }
        }
        return false;
    }

    private String insert_ET_Change(HttpServletRequest request, ET_change et,
            boolean to, boolean fr, boolean bank, boolean branch,
            boolean online, boolean offline,
            String srcing, String bankbranch) {
        String ok = "0";
        boolean quant = false;
        String conf = request.getParameter("conf");
        if (conf == null) {
            conf = "NO";
        }
        if (conf.equals("NO")) {
            if (fr && branch && online) {
                //annulla
                insertTR("E", "service", "REJECT TRANSAZIONE DA etfrombranch");
            }
            ok = "0_1";
        } else if (to) {
            et.setId_in("-");
            et.setFiliale_in("-");
            Db_Master db0 = new Db_Master();
            boolean es = db0.insert_ET_change(et);
            db0.closeDB();
            if (es) {
                Db_Master db01 = new Db_Master();
                ArrayList<String[]> array_list_oc_change = db01.list_oc_change_real_et();
                List<Sizecuts> array_figures_sizecuts = db01.figures_sizecuts_enabled();
                db01.closeDB();
                for (int i = 0; i < array_list_oc_change.size(); i++) {

                    if (et.getIdopen_from().equals(array_list_oc_change.get(i)[0])) {
                        String currency = request.getParameter("currencyv" + i);
                        String kind = request.getParameter("kindv" + i);
                        String s_quantold = formatDoubleforMysql(request.getParameter("s_quantold" + i));
                        String s_totold = formatDoubleforMysql(request.getParameter("s_totold" + i));
                        String s_quantnow = request.getParameter("s_quantnow" + i);
                        String s_totnow = request.getParameter("s_totnow" + i);
                        String s_loc = request.getParameter("s_loc" + i);
                        if (s_loc == null) {
                            s_loc = "false";
                        }
                        String s_rate = request.getParameter("s_rate" + i);

                        if (s_loc.equals("true")) {
                            s_rate = "1" + decimal + "0000000";
                        }
                        String s_totv = request.getParameter("s_totv" + i);
                        String s_buyv = request.getParameter("s_buyv" + i);
                        String s_spread = getValue_request(request, "s_spread" + i, false, "KO");
                        if (!s_spread.equals("KO")) {
                            s_spread = formatDoubleforMysql(s_spread);
                        }

                        if (s_quantnow != null) {
                            if (!s_quantnow.trim().equals("") && !s_quantnow.trim().equals("0") && !s_quantnow.trim().equals("0.00")) {
                                if (checkquantity(false, (s_quantold), s_quantnow)) {
                                    if (checkquantity(false, (s_totold), s_totnow)) {

                                        quant = true;

                                        ET_change etv = new ET_change();
                                        etv.setFiliale(filiale);
                                        etv.setCod(et.getCod());
                                        etv.setValuta(currency);
                                        etv.setSupporto(kind);
                                        etv.setIp_stock(formatDoubleforMysql(s_quantnow));
                                        etv.setIp_quantity(formatDoubleforMysql(s_totnow));
                                        etv.setIp_rate(removeLast(formatDoubleforMysql(s_rate)));
                                        etv.setIp_total(formatDoubleforMysql(s_totv));
                                        etv.setIp_buyvalue(formatDoubleforMysql(s_buyv));

                                        get_spread(currency,
                                                formatDoubleforMysql(s_totnow),
                                                removeLast(formatDoubleforMysql(s_rate)),
                                                et.getCod(),
                                                "ET",
                                                filiale,
                                                kind);

                                        if (!s_spread.equals("KO")) {
                                            etv.setIp_spread(s_spread);
                                            etv.setDt_it(et.getDt_it());
                                            Db_Master db81 = new Db_Master();
                                            boolean es2 = db81.insert_ET_change_value(etv);
                                            db81.closeDB();
                                            if (es2) {

                                                Db_Master db8 = new Db_Master();
                                                Stock_report srA = db8.get_Stock_report(et.getIdopen_from(), currency, kind, "CH", filiale, et.getTill_from());
                                                db8.closeDB();

                                                String codsrA = filiale + generaId(47);
                                                srA.setCodtr(et.getCod());
                                                srA.setCodice(codsrA);
//                                                double newtotA = fd(srA.getTotal()) - fd(formatDoubleforMysql(s_totnow));
                                                double newtotA = -fd(formatDoubleforMysql(s_totnow));
                                                srA.setTotal(roundDoubleandFormat(newtotA, 2));
                                                srA.setSpread("1.0000");
                                                srA.setData(et.getDt_it());
//                                                srA.setQuantity(roundDoubleandFormat(fd(srA.getQuantity()) - fd(formatDoubleforMysql(s_quantnow)), 0));
                                                srA.setQuantity(roundDoubleandFormat(-fd(formatDoubleforMysql(s_quantnow)), 0));
                                                srA.setUser(et.getUser());

                                                Db_Master db9A = new Db_Master();
                                                db9A.insert_Stockreport(srA);
                                                db9A.closeDB();

                                                Real_oc_change from = new Real_oc_change();
                                                from.setFiliale(filiale);
                                                from.setCod_oc(et.getIdopen_from());
                                                from.setValuta(currency);
                                                from.setKind(kind);
                                                from.setData(et.getDt_it());

                                                from.setNum_kind_op(roundDoubleandFormat((fd(s_quantold) - fd(formatDoubleforMysql(s_quantnow))), 0));
                                                from.setValue_op(roundDoubleandFormat((fd((s_totold)) - fd(formatDoubleforMysql(s_totnow))), 2));
                                                Db_Master db51 = new Db_Master();
                                                db51.update_real_oc_change(from);
                                                db51.closeDB();

//                                                ArrayList<String> array_sizecuts = new ArrayList<>();
                                                String va1 = array_list_oc_change.get(i)[2];
                                                List<String> array_sizecuts = array_figures_sizecuts.stream().filter(t1 -> t1.getValuta().equals(va1)).map(t1 -> t1.getIp_taglio()).collect(toList());

//                                                for (int j = 0; j < array_figures_sizecuts.size(); j++) {
//                                                    if (array_figures_sizecuts.get(j).getValuta().equals()) {
//                                                        array_sizecuts.add(array_figures_sizecuts.get(j).getIp_taglio());
//                                                    }
//                                                }
                                                for (int j = 0; j < array_sizecuts.size(); j++) {
                                                    String quantnow = request.getParameter("quantnow" + i + "_" + j);
                                                    String totold = request.getParameter("totold" + i + "_" + j);
                                                    String quantold = request.getParameter("quantold" + i + "_" + j);
                                                    String totnow = request.getParameter("totnow" + i + "_" + j);
                                                    String sizecuts = request.getParameter("sizecuts" + i + "_" + j);
                                                    if (quantnow != null) {
                                                        if (!quantnow.trim().equals("") && !quantnow.trim().equals("0")) {
                                                            if (checkquantity(false, formatDoubleforMysql(quantold), quantnow)) {
                                                                if (checkquantity(false, formatDoubleforMysql(totold), (totnow))) {
                                                                    ET_change et3 = new ET_change();
                                                                    et3.setFiliale(filiale);
                                                                    et3.setCod(et.getCod());
                                                                    et3.setValuta(currency);
                                                                    et3.setSupporto(kind);
                                                                    et3.setIp_taglio(sizecuts);
                                                                    et3.setIp_quantity(formatDoubleforMysql(quantnow));
                                                                    et3.setIp_total(formatDoubleforMysql(totnow));
                                                                    et3.setDt_it(et.getDt_it());
                                                                    Db_Master db1 = new Db_Master();
                                                                    boolean es3 = db1.insert_ET_change_tg(et3);
                                                                    db1.closeDB();
                                                                    if (es3) {
                                                                        //update real time tagli
                                                                        Real_oc_change from_cuts = new Real_oc_change();
                                                                        from_cuts.setFiliale(filiale);
                                                                        from_cuts.setCod_oc(et.getIdopen_from());
                                                                        from_cuts.setValuta(currency);
                                                                        from_cuts.setKind(kind);
                                                                        from_cuts.setData(et.getDt_it());
                                                                        from_cuts.setIp_taglio(sizecuts);
                                                                        from_cuts.setNum_kind_op(roundDoubleandFormat((fd(formatDoubleforMysql(quantold)) - fd(formatDoubleforMysql(quantnow))), 0));
                                                                        from_cuts.setValue_op(roundDoubleandFormat((fd(formatDoubleforMysql(totold)) - fd(formatDoubleforMysql(totnow))), 2));
                                                                        Db_Master db2 = new Db_Master();
                                                                        db2.update_real_oc_change_cuts(from_cuts);
                                                                        db2.closeDB();
                                                                    } else {
                                                                        ok = "3IN"; //ko insert size
                                                                    }
                                                                } else {
                                                                    ok = "2T"; //total error size
                                                                }
                                                            } else {
                                                                ok = "2Q"; //quantity error size
                                                            }
                                                        }
                                                    }
                                                }
                                            } else {
                                                ok = "2IN"; //ko insert value
                                            }
                                        } else {
                                            ok = "2ES"; //ko insert value
                                        }
                                    } else {
                                        ok = "1T"; //total error
                                    }
                                } else {
                                    ok = "1Q"; //quantity error
                                }
                            }
                        }
                    }
                }
            } else {
                ok = "2IN"; //ko insert value
            }
            if (branch) {
                if (ok.equals("0")) {
                    Db_Master db1 = new Db_Master();
                    ET_change etnew = db1.get_ET_change(et.getCod());
                    db1.closeDB();
                    //newtr
                    etnew.setFg_stato("0");
                    etnew.setTr_dt("1901-01-01 00:00:00");
                    etnew.setTr_user("-");
                    etnew.setTr_motiv("-");
                    etnew.setType("CH");
                    Db_Master db2 = new Db_Master();
                    boolean etes = db2.insert_ET_change_frombranch(etnew);
                    db2.closeDB();
                    if (!etes) {
                        ok = "4IN"; //ko insert value
                    }
                    if (ok.equals("0")) {
                        ArrayList<ET_change> val_tobr = get_ET_change_value(et.getCod());
                        for (int x = 0; x < val_tobr.size(); x++) {
                            Db_Master db3 = new Db_Master();
                            boolean es2 = db3.insert_ET_change_value_tobr(val_tobr.get(x), etnew.getCod_dest());
                            db3.closeDB();
                            if (!es2) {
                                ok = "4IN"; //ko insert value
                                break;
                            }
                        }
                    }

                    if (ok.equals("0")) {
                        ArrayList<ET_change> tg_tobr = get_ET_change_tg(et.getCod());
                        for (int x = 0; x < tg_tobr.size(); x++) {
                            Db_Master db4 = new Db_Master();
                            boolean es3 = db4.insert_ET_change_tg_tobr(tg_tobr.get(x), etnew.getCod_dest());
                            db4.closeDB();
                            if (!es3) {
                                ok = "4IN"; //ko insert value
                                break;
                            }
                        }
                    }
                }
            }
        } else if (fr) {

            Db_Master db1 = new Db_Master();
            ArrayList<ET_change> array_frombranch = db1.get_ET_change_frombranch(filiale, null, "CH");
            db1.closeDB();
            if (bank) {
                et.setId_in("-");
                et.setFiliale_in("-");
            } else if (branch) {
                if (online) {
                    et.setId_in(getIdfromCod_ETchange(array_frombranch, srcing));
                    et.setFiliale_in(getFilialefromCod_ETchange(array_frombranch, srcing));
                } else if (offline) {
                    et.setId_in(srcing);
                    et.setFiliale_in(bankbranch);
                }

            }
            Db_Master db2 = new Db_Master();
            boolean es = db2.insert_ET_change(et);
            db2.closeDB();
            if (es) {

                if (branch && online) {
                    ArrayList<ET_change> val = get_ET_change_value(et.getCod_in());

                    for (int x = 0; x < val.size(); x++) {

                        quant = true;
                        ET_change etv = new ET_change();
                        etv.setFiliale(filiale);
                        etv.setCod(et.getCod());
                        etv.setValuta(val.get(x).getValuta());
                        etv.setSupporto(val.get(x).getSupporto());
                        etv.setIp_stock(val.get(x).getIp_stock());
                        etv.setIp_quantity(val.get(x).getIp_quantity());
                        etv.setIp_rate(val.get(x).getIp_rate());
                        etv.setIp_total(val.get(x).getIp_total());
                        etv.setIp_buyvalue("0.00");
                        etv.setIp_spread("0.00");
                        etv.setDt_it(et.getDt_it());
                        Db_Master db3 = new Db_Master();
                        boolean es2 = db3.insert_ET_change_value(etv);
                        db3.closeDB();
                        if (es2) {

                            Db_Master db8 = new Db_Master();
                            Stock_report srA = db8.get_Stock_report(et.getIdopen_from(), val.get(x).getValuta(), val.get(x).getSupporto(), "CH", filiale, et.getTill_from());
                            db8.closeDB();

                            String codsrA = filiale + generaId(47);
                            srA.setCodtr(et.getCod());
                            srA.setCodice(codsrA);
//                                double newtotA = fd(srA.getTotal()) + fd(formatDoubleforMysql(s_totnow));
                            double newtotA = fd(val.get(x).getIp_quantity());
                            srA.setTotal(roundDoubleandFormat(newtotA, 2));
                            srA.setSpread("1.0000");
                            srA.setData(et.getDt_it());
                            srA.setQuantity(roundDoubleandFormat(fd(val.get(x).getIp_stock()), 0));
//                                srA.setQuantity(roundDoubleandFormat(fd(srA.getQuantity()) + fd(formatDoubleforMysql(s_quantnow)), 0));
                            srA.setUser(et.getUser());
                            Db_Master db9A = new Db_Master();
                            db9A.insert_Stockreport(srA);
                            db9A.closeDB();

                            Real_oc_change from = new Real_oc_change();
                            from.setFiliale(filiale);
                            from.setCod_oc(et.getIdopen_from());
                            from.setValuta(val.get(x).getValuta());
                            from.setKind(val.get(x).getSupporto());
                            from.setData(et.getDt_it());
                            Db_Master db4 = new Db_Master();
                            from.setNum_kind_op(roundDoubleandFormat((fd(db4.getField_real_oc_change(from, "num_kind_op", "0")) + fd(val.get(x).getIp_stock())), 0));
                            from.setValue_op(roundDoubleandFormat((fd(db4.getField_real_oc_change(from, "value_op", "0.00")) + fd(val.get(x).getIp_quantity())), 2));
                            db4.update_real_oc_change(from);
                            db4.closeDB();

                            Stock st1 = new Stock();
                            st1.setCodice("ST" + generaId(48));
                            st1.setFiliale(filiale);
                            st1.setTipo("ET");
                            st1.setTill(et.getTill_from());
                            st1.setIdoperation(et.getCod());
                            st1.setCodiceopenclose(et.getIdopen_from());
                            st1.setTipostock("CH");
                            st1.setCod_value(val.get(x).getValuta());
                            st1.setKind(val.get(x).getSupporto());
                            st1.setTotal(val.get(x).getIp_quantity());
                            st1.setRate(val.get(x).getIp_rate());

                            st1.setControval(val.get(x).getIp_total());

                            st1.setUser(et.getUser());
                            st1.setDate(et.getDt_it());
                            Db_Master db11 = new Db_Master();
                            st1.setId_op(filiale + db11.get_ET_change(et.getCod()).getId());
                            db11.insertStock(st1);
                            db11.closeDB();

                        }
                    }

                    ArrayList<ET_change> tg = get_ET_change_tg(et.getCod_in());

                    for (int c = 0; c < tg.size(); c++) {
                        ET_change et3 = new ET_change();
                        et3.setFiliale(filiale);
                        et3.setCod(et.getCod());
                        et3.setValuta(tg.get(c).getValuta());
                        et3.setSupporto(tg.get(c).getSupporto());
                        et3.setIp_taglio(tg.get(c).getIp_taglio());
                        et3.setIp_quantity(tg.get(c).getIp_quantity());
                        et3.setIp_total(tg.get(c).getIp_total());
                        et3.setDt_it(et.getDt_it());
                        Db_Master db6 = new Db_Master();
                        boolean es3 = db6.insert_ET_change_tg(et3);
                        db6.closeDB();
                        if (es3) {
                            //update real time tagli
                            Real_oc_change from_cuts = new Real_oc_change();
                            from_cuts.setFiliale(filiale);
                            from_cuts.setCod_oc(et.getIdopen_from());
                            from_cuts.setValuta(tg.get(c).getValuta());
                            from_cuts.setKind(tg.get(c).getSupporto());
                            from_cuts.setData(et.getDt_it());
                            from_cuts.setIp_taglio(tg.get(c).getIp_taglio());
                            Db_Master db7 = new Db_Master();
                            from_cuts.setNum_kind_op(roundDoubleandFormat((fd(db7.getField_real_oc_change_cuts(from_cuts, "ip_quantity", "0.00")) + fd(tg.get(c).getIp_quantity())), 0));
                            from_cuts.setValue_op(roundDoubleandFormat((fd(db7.getField_real_oc_change_cuts(from_cuts, "ip_value", "0.00")) + fd(tg.get(c).getIp_total())), 2));
                            db7.update_real_oc_change_cuts(from_cuts);
                            db7.closeDB();

                        } else {
                            ok = "3IN"; //ko insert size
                        }
                    }

                } else {

                    ArrayList<String[]> array_kind_fingures_openclose = kind_figures_openclose(request.getParameter("typeopv"), online);

                    Db_Master db7 = new Db_Master();
                    List<Sizecuts> array_figures_sizecuts = db7.figures_sizecuts_enabled();
                    db7.closeDB();

                    for (int i = 0; i < array_kind_fingures_openclose.size(); i++) {

                        String currency = request.getParameter("currencyv" + i);
                        String kind = request.getParameter("kindv" + i);
                        String s_quantnow = request.getParameter("s_quantnow" + i);
                        String s_totnow = request.getParameter("s_totnow" + i);
                        String s_loc = request.getParameter("s_loc" + i);
                        if (s_loc == null) {
                            s_loc = "false";
                        }
                        String s_rate = request.getParameter("s_rate" + i);
                        String s_spread = request.getParameter("s_spread" + i);
                        if (s_loc.equals("true")) {
                            s_rate = "1" + decimal + "0000000";
                            s_spread = "0.00";
                        }
                        String s_totv = request.getParameter("s_totv" + i);
                        String s_buyv = request.getParameter("s_buyv" + i);
                        if (s_quantnow != null) {
                            if (!s_quantnow.trim().equals("") && !s_quantnow.trim().equals("0")) {
                                quant = true;
                                ET_change etv = new ET_change();
                                etv.setFiliale(filiale);
                                etv.setCod(et.getCod());
                                etv.setValuta(currency);
                                etv.setSupporto(kind);
                                etv.setIp_stock(formatDoubleforMysql(s_quantnow));
                                etv.setIp_quantity(formatDoubleforMysql(s_totnow));
                                etv.setIp_rate(removeLast(formatDoubleforMysql(s_rate)));
                                etv.setIp_total(formatDoubleforMysql(s_totv));
                                etv.setIp_buyvalue(formatDoubleforMysql(s_buyv));
                                etv.setIp_spread(formatDoubleforMysql(s_spread));
                                etv.setDt_it(et.getDt_it());
                                Db_Master db8 = new Db_Master();
                                boolean es2 = db8.insert_ET_change_value(etv);
                                db8.closeDB();
                                if (es2) {

                                    Db_Master db38 = new Db_Master();
                                    Stock_report srA = db38.get_Stock_report(et.getIdopen_from(), currency, kind, "CH", filiale, et.getTill_from());
                                    db38.closeDB();

                                    String codsrA = filiale + generaId(47);
                                    srA.setCodtr(et.getCod());
                                    srA.setCodice(codsrA);
//                                double newtotA = fd(srA.getTotal()) + fd(formatDoubleforMysql(s_totnow));
                                    double newtotA = fd(formatDoubleforMysql(s_totnow));
                                    srA.setTotal(roundDoubleandFormat(newtotA, 2));
                                    srA.setSpread("1.0000");
                                    srA.setData(et.getDt_it());
                                    srA.setQuantity(roundDoubleandFormat(fd(formatDoubleforMysql(s_quantnow)), 0));
//                                srA.setQuantity(roundDoubleandFormat(fd(srA.getQuantity()) + fd(formatDoubleforMysql(s_quantnow)), 0));
                                    srA.setUser(et.getUser());
                                    Db_Master db9A = new Db_Master();
                                    db9A.insert_Stockreport(srA);
                                    db9A.closeDB();

                                    Real_oc_change from = new Real_oc_change();
                                    from.setFiliale(filiale);
                                    from.setCod_oc(et.getIdopen_from());
                                    from.setValuta(currency);
                                    from.setKind(kind);
                                    from.setData(et.getDt_it());
                                    Db_Master db9 = new Db_Master();
                                    from.setNum_kind_op(roundDoubleandFormat((fd(db9.getField_real_oc_change(from, "num_kind_op", "0")) + fd(formatDoubleforMysql(s_quantnow))), 0));
                                    from.setValue_op(roundDoubleandFormat((fd(db9.getField_real_oc_change(from, "value_op", "0.00")) + fd(formatDoubleforMysql(s_totnow))), 2));
                                    db9.update_real_oc_change(from);
                                    db9.closeDB();

                                    Stock st1 = new Stock();
                                    st1.setCodice("ST" + generaId(48));
                                    st1.setFiliale(filiale);
                                    st1.setTipo("ET");
                                    st1.setTill(et.getTill_from());
                                    st1.setIdoperation(et.getCod());
                                    st1.setCodiceopenclose(et.getIdopen_from());
                                    st1.setTipostock("CH");
                                    st1.setCod_value(currency);
                                    st1.setKind(kind);
                                    st1.setTotal(formatDoubleforMysql(s_totnow));
                                    st1.setRate(removeLast(formatDoubleforMysql(s_rate)));

                                    st1.setControval(formatDoubleforMysql(s_totv));

                                    st1.setUser(et.getUser());
                                    st1.setDate(et.getDt_it());
                                    Db_Master db21 = new Db_Master();
                                    st1.setId_op(filiale + db21.get_ET_change(et.getCod()).getId());
                                    db21.insertStock(st1);
                                    db21.closeDB();

//                                    ArrayList<String> array_sizecuts = new ArrayList<>();
                                    String v1 = array_kind_fingures_openclose.get(i)[0];
                                    List<String> array_sizecuts = array_figures_sizecuts.stream().filter(t1 -> t1.getValuta().equals(v1)).map(t1 -> t1.getIp_taglio()).collect(toList());

//                                    for (int j = 0; j < array_figures_sizecuts.size(); j++) {
//                                        if (array_figures_sizecuts.get(j)[0].equals()) {
//                                            array_sizecuts.add(array_figures_sizecuts.get(j)[1]);
//                                        }
//                                    }
                                    for (int j = 0; j < array_sizecuts.size(); j++) {
                                        String quantnow = request.getParameter("quantnow" + i + "_" + j);
                                        String totnow = request.getParameter("totnow" + i + "_" + j);
                                        String sizecuts = request.getParameter("sizecuts" + i + "_" + j);

                                        if (quantnow != null) {
                                            if (!quantnow.trim().equals("") && !quantnow.trim().equals("0")) {
                                                ET_change et3 = new ET_change();
                                                et3.setFiliale(filiale);
                                                et3.setCod(et.getCod());
                                                et3.setValuta(currency);
                                                et3.setSupporto(kind);
                                                et3.setIp_taglio(sizecuts);
                                                et3.setIp_quantity(quantnow);
                                                et3.setIp_total(formatDoubleforMysql(totnow));
                                                et3.setDt_it(et.getDt_it());
                                                Db_Master db5 = new Db_Master();
                                                boolean es3 = db5.insert_ET_change_tg(et3);
                                                db5.closeDB();
                                                if (es3) {
                                                    //update real time tagli
                                                    Real_oc_change from_cuts = new Real_oc_change();
                                                    from_cuts.setFiliale(filiale);
                                                    from_cuts.setCod_oc(et.getIdopen_from());
                                                    from_cuts.setValuta(currency);
                                                    from_cuts.setKind(kind);
                                                    from_cuts.setData(et.getDt_it());
                                                    from_cuts.setIp_taglio(sizecuts);
                                                    Db_Master db6 = new Db_Master();
                                                    from_cuts.setNum_kind_op(roundDoubleandFormat((fd(db6.getField_real_oc_change_cuts(from_cuts, "ip_quantity", "0.00")) + fd(formatDoubleforMysql(quantnow))), 0));
                                                    from_cuts.setValue_op(roundDoubleandFormat((fd(db6.getField_real_oc_change_cuts(from_cuts, "ip_value", "0.00")) + fd(formatDoubleforMysql(totnow))), 2));

                                                    db6.update_real_oc_change_cuts(from_cuts);
                                                    db6.closeDB();
                                                } else {
                                                    ok = "3IN"; //ko insert size
                                                }
                                            }
                                        }
                                    }
                                } else {
                                    ok = "2IN"; //ko insert value
                                }
                            }
                        }
                    }
                }

            }
        }
        if (!quant) {
            ok = "3Q";
        }
        return ok;
    }

    private String insert_ET_Change_CHECK(HttpServletRequest request, Db_Master db, ET_change et,
            boolean to, boolean fr, boolean bank, boolean branch, boolean online, boolean offline,
            String srcing, String bankbranch) {
        String ok = "0";
        boolean quant = false;
        String conf = request.getParameter("conf");
        if (conf == null) {
            conf = "NO";
        }
        if (conf.equals("NO")) {
            ok = "0_1";
        } else if (to) {
            et.setId_in("-");
            et.setFiliale_in("-");
            ArrayList<String[]> array_list_oc_change = db.list_oc_change_real_et();

//            ArrayList<String[]> array_figures_sizecuts = db.figures_sizecuts();
            List<Sizecuts> array_figures_sizecuts = db.figures_sizecuts_enabled();

            for (int i = 0; i < array_list_oc_change.size(); i++) {
                if (et.getIdopen_from().equals(array_list_oc_change.get(i)[0])) {
                    String s_quantold = formatDoubleforMysql(request.getParameter("s_quantold" + i));
                    String s_totold = formatDoubleforMysql(request.getParameter("s_totold" + i));
                    String s_quantnow = request.getParameter("s_quantnow" + i);
                    String s_totnow = request.getParameter("s_totnow" + i);
                    if (s_quantnow != null) {
                        if (!s_quantnow.trim().equals("") && !s_quantnow.trim().equals("0") && !s_quantnow.trim().equals("0.00")) {
                            if (checkquantity(false, (s_quantold), s_quantnow)) {
                                if (checkquantity(false, (s_totold), s_totnow)) {
                                    quant = true;
                                    String v1 = array_list_oc_change.get(i)[2];
                                    List<String> array_sizecuts = array_figures_sizecuts.stream().filter(t1 -> t1.getValuta().equals(v1)).map(t1 -> t1.getIp_taglio()).collect(toList());

                                    for (int j = 0; j < array_sizecuts.size(); j++) {
                                        String quantnow = request.getParameter("quantnow" + i + "_" + j);
                                        String totold = request.getParameter("totold" + i + "_" + j);
                                        String quantold = request.getParameter("quantold" + i + "_" + j);
                                        String totnow = request.getParameter("totnow" + i + "_" + j);
                                        if (quantnow != null) {
                                            if (!quantnow.trim().equals("") && !quantnow.trim().equals("0")) {
                                                if (checkquantity(false, formatDoubleforMysql(quantold), quantnow)) {
                                                    if (checkquantity(false, formatDoubleforMysql(totold), (totnow))) {
                                                    } else {
                                                        ok = "2T"; //total error size
                                                    }
                                                } else {
                                                    ok = "2Q"; //quantity error size
                                                }
                                            }
                                        }
                                    }
                                } else {
                                    ok = "1T"; //total error
                                }
                            } else {
                                ok = "1Q"; //quantity error
                            }
                        }
                    }
                }
            }
        } else if (fr) {
            ArrayList<ET_change> array_frombranch = db.get_ET_change_frombranch(filiale, null, "CH");
            if (bank) {
                et.setId_in("-");
                et.setFiliale_in("-");
            } else if (branch) {
                if (online) {
                    et.setId_in(getIdfromCod_ETchange(array_frombranch, srcing));
                    et.setFiliale_in(getFilialefromCod_ETchange(array_frombranch, srcing));
                } else if (offline) {
                    et.setId_in(srcing);
                    et.setFiliale_in(bankbranch);
                }
            }
            if (branch && online) {
                ArrayList<ET_change> val = get_ET_change_value(et.getCod_in());
                for (ET_change val1 : val) {
                    quant = true;
                    break;
                }
            } else {
                ArrayList<String[]> array_kind_fingures_openclose = kind_figures_openclose(request.getParameter("typeopv"), online);
                for (int i = 0; i < array_kind_fingures_openclose.size(); i++) {
                    String s_quantnow = request.getParameter("s_quantnow" + i);
                    if (s_quantnow != null) {
                        if (!s_quantnow.trim().equals("") && !s_quantnow.trim().equals("0")) {
                            quant = true;
                        }
                    }
                }
            }

        }
        if (!quant) {
            ok = "3Q";
        }
        return ok;
    }

    private String insert_ET_Nochange(HttpServletRequest request, Db_Master db, ET_change et,
            boolean to, boolean fr, boolean branch, boolean online, boolean offline,
            String srcing, String bankbranch) {
        String ok = "0";
        boolean quant = false;
        String conf = request.getParameter("conf");
        if (conf == null) {
            conf = "NO";
        }
        if (conf.equals("NO")) {
            if (fr && branch && online) {
                insertTR("E", "service", "REJECT TRANSAZIONE DA etfrombranch");
            }
            ok = "0_1";
        } else if (to) {
            et.setId_in("-");
            et.setFiliale_in("-");
            boolean es = db.insert_ET_change(et);
            if (es) {
                ArrayList<String[]> list_oc_nochange = db.list_oc_nochange_real(et.getIdopen_from());
                for (int i = 0; i < list_oc_nochange.size(); i++) {
                    String nc_causal = request.getParameter("nc_causal" + i);
                    String nc_quantold = formatDoubleforMysql(request.getParameter("nc_quantold" + i));
                    String nc_quantnow = request.getParameter("nc_quantnow" + i);
                    if (nc_quantold != null) {
                        if (!nc_quantnow.trim().equals("") && !nc_quantnow.trim().equals("0")) {
                            if (checkquantity(false, nc_quantold, nc_quantnow)) {
                                quant = true;
                                ET_change etv = new ET_change();
                                etv.setFiliale(filiale);
                                etv.setCod(et.getCod());
                                etv.setNc_causal(nc_causal);
                                etv.setIp_quantity(formatDoubleforMysql(nc_quantnow));
                                etv.setDt_it(et.getDt_it());
                                boolean es2 = db.insert_ET_nochange_value(etv);
                                if (es2) {
                                    Real_oc_nochange from = new Real_oc_nochange();
                                    from.setFiliale(filiale);
                                    from.setCod_oc(et.getIdopen_from());
                                    from.setGruppo_nc(nc_causal);
                                    from.setQuantity(valueOf(parseIntR(nc_quantold) - parseIntR(formatDoubleforMysql(nc_quantnow))));
                                    from.setData(et.getDt_it());
                                    db.update_real_oc_nochange(from);

                                    Db_Master db8 = new Db_Master();
                                    Stock_report sr = db8.get_Stock_report(et.getIdopen_from(), nc_causal, "01", "NC", filiale, et.getTill_from());
                                    db8.closeDB();

                                    String codsr = filiale + generaId(47);
                                    sr.setCodtr(et.getCod());
                                    sr.setCodice(codsr);
                                    sr.setTotal("0.00");
                                    sr.setSpread("1.0000");
                                    sr.setData(et.getDt_it());

//                                    sr.setQuantity(String.valueOf(parseIntR(sr.getQuantity()) - parseIntR(nc_quantnow)));
                                    sr.setQuantity("-" + valueOf(parseIntR(formatDoubleforMysql(nc_quantnow))));

                                    sr.setUser(et.getUser());
                                    Db_Master db9 = new Db_Master();
                                    db9.insert_Stockreport(sr);
                                    db9.closeDB();

                                } else {
                                    ok = "2IN"; //ko insert value
                                }
                            } else {
                                ok = "1Q"; //quantity error
                            }
                        }
                    }
                }
                if (ok.equals("0")) {
                    ET_change etnew = db.get_ET_change(et.getCod());
                    //newtr
                    etnew.setFg_stato("0");
                    etnew.setTr_dt("1901-01-01 00:00:00");
                    etnew.setTr_user("-");
                    etnew.setTr_motiv("-");
                    etnew.setType("NC");
                    boolean etes = db.insert_ET_change_frombranch(etnew);
                    if (!etes) {
                        ok = "4IN"; //ko insert value
                    }

                    if (ok.equals("0")) {
                        ArrayList<ET_change> val_tobr = get_ET_nochange_value(et.getCod());
                        for (int x = 0; x < val_tobr.size(); x++) {
                            boolean es2 = db.insert_ET_nochange_value_tobr(val_tobr.get(x), etnew.getCod_dest());
                            if (!es2) {
                                ok = "4IN"; //ko insert value
                                break;
                            }
                        }
                    }

                }
            } else {
                ok = "2IN"; //ko insert value
            }

        } else if (fr) {
            ArrayList<ET_change> array_frombranch = db.get_ET_change_frombranch(filiale, null, "NC");
            if (online) {

                et.setId_in(getIdfromCod_ETchange(array_frombranch, srcing));
                et.setFiliale_in(getFilialefromCod_ETchange(array_frombranch, srcing));

                boolean es = db.insert_ET_change(et);
                if (es) {

                    ArrayList<ET_change> val = get_ET_nochange_value(srcing);

                    for (int i = 0; i < val.size(); i++) {
                        quant = true;
                        ET_change etv = new ET_change();
                        etv.setFiliale(filiale);
                        etv.setCod(et.getCod());
                        etv.setNc_causal(val.get(i).getNc_causal());
                        etv.setIp_quantity(val.get(i).getIp_quantity());
                        etv.setDt_it(et.getDt_it());
                        boolean es2 = db.insert_ET_nochange_value(etv);
                        if (es2) {
                            Real_oc_nochange from = new Real_oc_nochange();
                            from.setFiliale(filiale);
                            from.setCod_oc(et.getIdopen_from());
                            from.setGruppo_nc(val.get(i).getNc_causal());
                            from.setQuantity(valueOf(parseIntR(db.getQuantity_real_oc_nochange(from)) + parseIntR(val.get(i).getIp_quantity())));
                            from.setData(et.getDt_it());
                            db.update_real_oc_nochange(from);

                            Db_Master db8 = new Db_Master();
                            Stock_report srTO = db8.get_Stock_report(et.getIdopen_from(), val.get(i).getNc_causal(), "01", "NC", filiale, et.getTill_from());
                            db8.closeDB();
                            //AUMENTO STOCK TO
                            String codsrTO = filiale + generaId(47);
                            srTO.setCodtr(et.getCod());
                            srTO.setCodice(codsrTO);
                            srTO.setTotal("0.00");
                            srTO.setSpread("1.0000");
                            srTO.setData(et.getDt_it());
//                            srTO.setQuantity(String.valueOf(parseIntR(srTO.getQuantity()) + parseIntR(val.get(i).getIp_quantity())));
                            srTO.setQuantity(valueOf(parseIntR(val.get(i).getIp_quantity())));
                            srTO.setUser(et.getUser());
                            Db_Master db9A = new Db_Master();
                            db9A.insert_Stockreport(srTO);
                            db9A.closeDB();

                        } else {
                            ok = "2IN"; //ko insert value
                        }
                    }
                } else {
                    ok = "2IN"; //ko insert value
                }
            } else if (offline) {
                et.setId_in(srcing);
                et.setFiliale_in(bankbranch);

                boolean es = db.insert_ET_change(et);
                if (es) {
                    ArrayList<String[]> array_list_nochange = db.list_nochange();
                    for (int i = 0; i < array_list_nochange.size(); i++) {

                        String cod = array_list_nochange.get(i)[0];
                        String nc_causal = request.getParameter("nc_causal" + i);
                        String nc_quantnow = request.getParameter("nc_quantnow" + i);

                        if (nc_quantnow != null) {
                            if (!nc_quantnow.trim().equals("") && !nc_quantnow.trim().equals("0")) {
                                quant = true;
                                ET_change etv = new ET_change();
                                etv.setFiliale(filiale);
                                etv.setCod(et.getCod());
                                etv.setNc_causal(nc_causal);
                                etv.setIp_quantity(formatDoubleforMysql(nc_quantnow));
                                etv.setDt_it(et.getDt_it());
                                boolean es2 = db.insert_ET_nochange_value(etv);
                                if (es2) {
                                    Real_oc_nochange from = new Real_oc_nochange();
                                    from.setFiliale(filiale);
                                    from.setCod_oc(et.getIdopen_from());
                                    from.setGruppo_nc(nc_causal);
                                    from.setQuantity(valueOf(parseIntR(db.getQuantity_real_oc_nochange(from)) + parseIntR(formatDoubleforMysql(nc_quantnow))));
                                    from.setData(et.getDt_it());
                                    db.update_real_oc_nochange(from);

                                    Db_Master db8 = new Db_Master();
                                    Stock_report srTO = db8.get_Stock_report(et.getIdopen_from(), nc_causal, "01", "NC", filiale, et.getTill_from());
                                    db8.closeDB();
                                    //AUMENTO STOCK TO
                                    String codsrTO = filiale + generaId(47);
                                    srTO.setCodtr(et.getCod());
                                    srTO.setCodice(codsrTO);
                                    srTO.setTotal("0.00");
                                    srTO.setSpread("1.0000");
                                    srTO.setData(et.getDt_it());
//                                    srTO.setQuantity(String.valueOf(parseIntR(srTO.getQuantity()) + parseIntR(formatDoubleforMysql(nc_quantnow))));
                                    srTO.setQuantity(valueOf(parseIntR(formatDoubleforMysql(nc_quantnow))));
                                    srTO.setUser(et.getUser());
                                    Db_Master db9A = new Db_Master();
                                    db9A.insert_Stockreport(srTO);
                                    db9A.closeDB();

                                } else {
                                    ok = "2IN"; //ko insert value
                                }
                            }
                        }
                    }
                } else {
                    ok = "2IN"; //ko insert value
                }
            }
        }
        if (!quant) {
            ok = "3Q";
        }
        return ok;
    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void et_nochange(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

//        Utility.printRequest(request);
//        if (true) {
//            return;
//        }
        HttpSession session = request.getSession();
        String user = (String) session.getAttribute("us_cod");
        if (user == null) {
            user = "9999";
        }

        String oper = new DateTime().toString(patternsqldate);
        String cod = "ETN" + generaId(22);
        String bankbranch = request.getParameter("bankbranchv");
        String typeop = request.getParameter("typeop");
        String tofrom = request.getParameter("tofrom");
        String idopentillfrom = request.getParameter("idopentillfrom");
        String tillfrom = request.getParameter("tillfrom");
        String note = request.getParameter("note");
        String srcoff = request.getParameter("srcoff");
        if (srcoff == null) {
            srcoff = "OFFLINE";
        }

        String autman = request.getParameter("autman");
        if (autman == null) {
            autman = "A";
            srcoff = "ONLINE";
        } else {
            if (autman.equals("M")) {
                srcoff = "OFFLINE";
            }
        }

        ET_change et = new ET_change();
        et.setCod(cod);
        et.setFiliale(filiale);
        et.setUser(user);
        et.setTill_from(tillfrom);
        et.setFg_tofrom(tofrom);
        et.setFg_brba(typeop);
        et.setCod_dest(bankbranch);
        et.setIdopen_from(idopentillfrom);
        et.setDt_it(oper);
        et.setFg_annullato("0");
        et.setNote(getStringUTF8(note));
        et.setAuto(autman);

        //del
        et.setDel_dt("1901-01-01 00:00:00");
        et.setDel_user("-");
        et.setDel_motiv("-");

        //altri
        et.setIp_oneri("0.00");

        String srcing = request.getParameter("srcing");
        if (srcing == null) {
            srcing = "";
        }
        et.setCod_in(srcing);

        Db_Master db = new Db_Master();
        ArrayList<String[]> list_oc_nochange = db.list_oc_nochange_real(et.getIdopen_from());
        db.closeDB();
        boolean to = tofrom.equals("T");
        boolean fr = tofrom.equals("F");
        boolean branch = typeop.equals("BR");
        boolean online = srcoff.equals("ONLINE");
        boolean offline = srcoff.equals("OFFLINE");
        String ok = "0";
        if (to) {
            if (!checkquantity_nc(request, list_oc_nochange)) {
                ok = "Q";
            }
        }

        if (ok.equals("0")) {
            Db_Master db1 = new Db_Master();
            ok = insert_ET_Nochange(request, db1, et, to, fr, branch, online, offline, srcing, bankbranch);
            db1.closeDB();
        }

        if (ok.equals("0")) {
            //sblocca to branch della filiale di partenza
            if (fr && branch && online) {
                if (srcing.startsWith("ET")) {
                    Db_Master db1 = new Db_Master();
                    boolean esx = db1.update_et_tobranch(srcing, cod, user, oper, bankbranch);
                    db1.closeDB();
                    if (!esx) {
                        ok = "Q";
                    }
                }

            } //offline ver
        }
        Db_Master db2 = new Db_Master();
        db2.updateBlockedOperation(session.getAttribute("us_cod") + " | " + session.getAttribute("us_user"), "0");
        db2.closeDB();
        try {
            sleep(500);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }

        switch (ok) {
            case "Q":
                redirect(request, response, "et_nochange.jsp?search=sra1&idopentillfrom=" + idopentillfrom + "&bankbranch=" + bankbranch
                        + "&tofrom=" + tofrom + "&tillfrom=" + tillfrom + "&typeop="
                        + typeop + "&srcoff=" + srcoff + "&srcing=" + srcing + "&srcing2=" + srcing + "&esito=" + ok);
                break;
            case "0":
                delete_IT_nochange_temp(idopentillfrom, "", filiale);
                String pr = "N";
                if (tofrom.equals("T")) {
                    pr = "Y";
                }
                redirect(request, response, "et_nochange.jsp?esito=OK&cod=" + cod + "&pr=" + pr);
                break;
            default:
                delete_ET_change(cod, idopentillfrom, filiale);
                redirect(request, response, "et_nochange.jsp?search=sra1&idopentillfrom=" + idopentillfrom + "&bankbranch=" + bankbranch
                        + "&tofrom=" + tofrom + "&tillfrom=" + tillfrom + "&typeop=" + typeop + "&srcoff=" + srcoff + "&srcing=" + srcing + "&srcing2=" + srcing + "&esito=" + ok);
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
    protected void oc_open(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        Utility.printRequest(request);
//        if (true) {
//            return;
//        }

        String cod = "OCO" + generaId(22);
        String safetill = request.getParameter("safetill");

        String ok = "0";
        if (isopentill(safetill)) {
            ok = "n1";
        }
        HttpSession session = request.getSession();
        Db_Master db = new Db_Master();
        if (ok.equals("0")) {

//        Utility.printRequest(request);
//        if(true)
//            return;
            String user = (String) session.getAttribute("us_cod");
            if (user == null) {
                user = "9999";
            }

            String setall = request.getParameter("setall");

            if (setall.equals("1")) {
                Db_Master db1 = new Db_Master();
                db1.insert_valuekyc_Module(cod, user, "SELECTALL");
                db1.closeDB();
            }

            String date = new DateTime().toString(patternsqldate);

            boolean dividi = db.get_national_office().getChangetype().equals("/");

            String errorpresent = request.getParameter("errorpresent");

            String opencloseid = request.getParameter("opencloseid");

            ArrayList<String[]> al_kifi = new ArrayList<>();
            ArrayList<String[]> al_sicu = new ArrayList<>();
            ArrayList<String[]> al_noch = new ArrayList<>();

            ArrayList<String[]> array_kind_fingures_openclose = db.kind_figures_openclose();
            List<Sizecuts> array_figures_sizecuts = db.figures_sizecuts_enabled();

            ArrayList<String[]> array_list_nochange = db.list_nochange();

//        String errorsvalue = "N";
            for (int i = 0; i < array_kind_fingures_openclose.size(); i++) {

                String s_curr = request.getParameter("s_curr" + i);
                String s_kind = request.getParameter("s_kind" + i);
                String s_quantnow = request.getParameter("s_quantnow" + i);
                String s_totnow = request.getParameter("s_totnow" + i);
                String s_quantold = request.getParameter("s_quantold" + i);
                String s_totalold = request.getParameter("s_totalold" + i);
                String s_diffgap = request.getParameter("s_diffgap" + i);
                if (s_diffgap == null) {
                    s_diffgap = "0.00";
                }
                String s_differr = "N";
                if (!s_diffgap.equals("0.00") && !s_diffgap.equals("0,00")) {
//                errorsvalue = "Y";
                    s_differr = "Y";
                }

                String[] v1 = {s_curr, s_kind, s_quantnow, s_totnow, s_quantold, s_totalold, s_diffgap, s_differr};
                al_kifi.add(v1);

                if (quantityNotZERO(s_quantnow, s_totnow)) {

                    String v9 = array_kind_fingures_openclose.get(i)[0];
                    List<String> array_sizecuts = array_figures_sizecuts.stream().filter(t1 -> t1.getValuta().equals(v9)).map(t1 -> t1.getIp_taglio()).collect(toList());
                    for (int j = 0; j < array_sizecuts.size(); j++) {

                        String curr = request.getParameter("curr" + i + "_" + j);
                        String kind = request.getParameter("kind" + i + "_" + j);
                        String cuts = request.getParameter("cuts" + i + "_" + j);
                        String quantnow = request.getParameter("quantnow" + i + "_" + j);
                        String totnow = request.getParameter("totnow" + i + "_" + j);
                        String quantold = request.getParameter("quantold" + i + "_" + j);
                        String totold = request.getParameter("totold" + i + "_" + j);
                        String diffgapsize = request.getParameter("diffgapsize" + i + "_" + j);
                        if (diffgapsize == null) {
                            diffgapsize = "0.00";
                        }
                        String differr = "N";
                        if (!diffgapsize.equals("0.00") && !diffgapsize.equals("0,00")) {
//                    errorsvalue = "Y";
                            differr = "Y";
                        }
                        String[] v2 = {curr, kind, cuts, quantnow, totnow, quantold, totold, diffgapsize, differr};
                        al_sicu.add(v2);
                    }
                }

            }

            for (int i = 0; i < array_list_nochange.size(); i++) {

                String nc_cod = request.getParameter("nc_cod" + i);
                String nc_quantnow = request.getParameter("nc_quantnow" + i);
                String nc_quantold = request.getParameter("nc_quantold" + i);
                String nc_diffgap = request.getParameter("nc_diffgap" + i);
                String nc_differr = "N";
                if (nc_diffgap == null) {
                    nc_diffgap = "0.00";
                }
                if (!nc_diffgap.equals("0.00") && !nc_diffgap.equals("0,00")) {
//                errorsvalue = "Y";
                    nc_differr = "Y";
                }
                String[] v3 = {nc_cod, nc_quantnow, nc_quantold, nc_diffgap, nc_differr};
                al_noch.add(v3);
            }

            Openclose oc = new Openclose();
            oc.setFiliale(filiale);
            oc.setCod(cod);
            oc.setTill(safetill);
            oc.setUser(user);
            oc.setFg_tipo("O");
            oc.setData(date);
            oc.setErrors(errorpresent);
            oc.setForeign_tr("N");
            oc.setLocal_tr("N");
            oc.setStock_tr("n");
            oc.setCod_it("-");
            oc.setCod_itnc("-");

            boolean es = db.insert_new_Openclose(oc);

            if (es) {

                for (int i = 0; i < al_kifi.size(); i++) {
                    String[] v1 = al_kifi.get(i);

                    Currency cu = db.getCurrency(v1[0], filiale);

                    String s_totnow = v1[3];
                    String s_differr = v1[7];
                    if (s_totnow != null) {
                        if (!s_totnow.trim().equals("") && !s_totnow.trim().equals("0") && !s_totnow.trim().equals("0.00") && !s_totnow.trim().equals("0,00")) {
                            Openclose chv = new Openclose();
                            chv.setFiliale(filiale);
                            chv.setCod(cod);
                            chv.setValuta(v1[0]);
                            chv.setKind(v1[1]);
                            chv.setValue_op(formatDoubleforMysql(v1[3]));
                            chv.setNum_kind_op(formatDoubleforMysql(v1[2]));
                            chv.setValue_cl(v1[5]);
                            chv.setNum_kind_cl(v1[4]);
                            chv.setData(date);
                            chv.setFisico("S");
                            boolean es1 = db.insert_new_Openclose_value_ch(chv);
                            if (!es1) {
                                ok = "I2";
                                break;
                            }

                            Db_Master db8 = new Db_Master();
                            Stock_report sr = db8.get_Stock_report(opencloseid, v1[0], v1[1], "CH", filiale, safetill);
                            db8.closeDB();

                            String codsr = filiale + generaId(47);
                            sr.setCodtr(cod);
                            sr.setCodice(codsr);
                            sr.setTotal(formatDoubleforMysql(v1[3]));
                            sr.setSpread("1.0000");
                            sr.setData(date);
                            sr.setQuantity(formatDoubleforMysql(v1[2]));
                            sr.setUser(user);
                            Db_Master db9 = new Db_Master();
                            db9.insert_Stockreport(sr);
                            db9.closeDB();

                        }
                    }
                    if (s_differr.equals("Y")) {
                        //INSERT ERROR CON MOTIV
                        String err = request.getParameter("trfigerr_" + v1[0] + "_" + v1[1]);
                        if (err != null) {
                            Openclose cherr = new Openclose();
                            cherr.setFiliale(filiale);
                            cherr.setCod(cod);
                            cherr.setTipo("CH");
                            cherr.setValuta(v1[0]);
                            cherr.setKind(v1[1]);
                            cherr.setGruppo_nc("-");
                            cherr.setCarta_credito("-");
                            cherr.setNote(getStringUTF8(err));
                            cherr.setData(date);
                            cherr.setTotal_diff(formatDoubleforMysql(v1[6]));
                            String[] rate = db.rate_currency(cu, true, true);
                            cherr.setRate(rate[0]);
                            cherr.setQuantity_user(formatDoubleforMysql(v1[2]));
                            cherr.setIp_value_op(formatDoubleforMysql(v1[3]));
                            cherr.setQuantity_system(formatDoubleforMysql(v1[4]));
                            cherr.setIp_value_sys(formatDoubleforMysql(v1[5]));
                            cherr.setFisico("S");

                            boolean esE = db.insert_new_Openclose_value_err(cherr);
                            if (!esE) {
                                ok = "I4E";
                                break;
                            } else if (fd(formatDoubleforMysql(v1[6])) > 0) {
                                //NUOVO STOCK
                                Stock st1 = new Stock();
                                st1.setCodice("ST" + generaId(48));
                                st1.setFiliale(filiale);
                                st1.setTipo("OC");
                                st1.setTill(safetill);
                                st1.setIdoperation(cod);
                                st1.setCodiceopenclose(cod);
                                st1.setTipostock("CH");
                                st1.setCod_value(v1[0]);
                                st1.setKind(v1[1]);
                                st1.setTotal(roundDoubleandFormat(parseDoubleR(formatDoubleforMysql(v1[6])), 2));
                                st1.setRate(rate[0]);
                                st1.setControval(roundDoubleandFormat(getControvalore(parseDoubleR(formatDoubleforMysql(v1[6])), fd(rate[0]), dividi), 2));
                                st1.setUser(user);
                                st1.setDate(date);
                                Db_Master db1 = new Db_Master();
                                st1.setId_op(filiale + db1.query_oc(cod).getId());
                                db1.insertStock(st1);
                                db1.closeDB();

//                            Db_Master db8 = new Db_Master();
//                            ArrayList<Stock_report> li = db8.list_Stock_report(opencloseid);
//                            db8.closeDB();
//                            for (int x = 0; x < li.size(); x++) {
//                                }
//                            }
                            } else if (fd(formatDoubleforMysql(v1[6])) < 0) {
//                            Db_Master db8 = new Db_Master();
//                            ArrayList<Stock_report> li = db8.list_Stock_report(opencloseid);
//                            db8.closeDB();

                                scalareErroridaStock(filiale, v1[0],
                                        formatDoubleforMysql(v1[6]), v1[1]);

//                            for (int x = 0; x < li.size(); x++) {
//                                Stock_report sr = li.get(x);
//                                if (sr.getCod_value().equals(v1[0]) && sr.getKind().equals(v1[1])) {
//                            String codsr = filiale + generaId(47);
//                            sr.setCodtr(cod);
//                            sr.setCodice(codsr);
//                            double newtot = fd(sr.getTotal()) - parseDoubleR(formatDoubleforMysql(v1[6]));
//                            sr.setTotal(roundDoubleandFormat(newtot, 2));
//                            sr.setSpread("1.0000");
//                            sr.setData(date);
//                            sr.setQuantity("0");
//                            sr.setUser(user);
//                            Db_Master db9 = new Db_Master();
//                            db9.insert_Stockreport(sr);
//                            db9.closeDB();
//                                }
//                            }
                            }
                        }
                    }
                }
            }

            if (ok.equals("0")) {
                for (int i = 0; i < al_sicu.size(); i++) {
                    String[] v2 = al_sicu.get(i);
                    String s_totnow = v2[4];
                    String s_differr = v2[8];
                    if (s_totnow != null) {
                        if (!s_totnow.trim().equals("") && !s_totnow.trim().equals("0") && !s_totnow.trim().equals("0.00") && !s_totnow.trim().equals("0,00")) {
                            Openclose chtg = new Openclose();
                            chtg.setFiliale(filiale);
                            chtg.setCod(cod);
                            chtg.setValuta(v2[0]);
                            chtg.setKind(v2[1]);
                            chtg.setIp_taglio(v2[2]);
                            chtg.setIp_quantity(formatDoubleforMysql(v2[3]));
                            chtg.setIp_value(formatDoubleforMysql(s_totnow));
                            chtg.setData(date);
                            chtg.setFisico("S");

                            boolean es2 = db.insert_new_Openclose_value_tg(chtg);
                            if (!es2) {
                                ok = "I3";
                                break;
                            }
                        }
                    }
                }
            }

            if (ok.equals("0")) {

                for (int i = 0; i < al_noch.size(); i++) {
                    String[] v3 = al_noch.get(i);
                    String nc_quantnow = v3[1];
                    String nc_differr = v3[4];

                    if (nc_quantnow != null) {
                        if (!nc_quantnow.trim().equals("") && !nc_quantnow.trim().equals("0") && !nc_quantnow.trim().equals("0.00") && !nc_quantnow.trim().equals("0,00")) {
                            Openclose chnc = new Openclose();
                            chnc.setFiliale(filiale);
                            chnc.setCod(cod);
                            chnc.setGruppo_nc(v3[0]);
                            chnc.setQuantity_user(formatDoubleforMysql(nc_quantnow));
                            chnc.setQuantity_system(formatDoubleforMysql(v3[2]));
                            chnc.setData(date);
                            chnc.setFisico("S");
                            boolean es3 = db.insert_new_Openclose_value_nc(chnc);
                            if (!es3) {
                                ok = "I3";
                                break;
                            }

                            Db_Master db8 = new Db_Master();
                            Stock_report sr = db8.get_Stock_report(opencloseid, v3[0], "01", "NC", filiale, safetill);
                            db8.closeDB();

                            String codsr = filiale + generaId(47);
                            sr.setCodtr(cod);
                            sr.setCodice(codsr);
                            sr.setTotal(roundDoubleandFormat(fd(formatDoubleforMysql(nc_quantnow)), 2));
                            sr.setSpread("1.0000");
                            sr.setData(date);
                            sr.setQuantity(roundDoubleandFormat(fd(formatDoubleforMysql(nc_quantnow)), 2));
                            sr.setUser(user);
                            Db_Master db9 = new Db_Master();
                            db9.insert_Stockreport(sr);
                            db9.closeDB();

                        }
                    }

                    if (nc_differr.equals("Y")) {
                        //INSERT ERROR CON MOTIV
                        String err = request.getParameter("trnocerr_" + v3[0]);
                        if (err != null) {
                            Openclose cherr = new Openclose();
                            cherr.setFiliale(filiale);
                            cherr.setCod(cod);
                            cherr.setTipo("NC");
                            cherr.setValuta("-");
                            cherr.setKind("01");
                            cherr.setGruppo_nc(v3[0]);
                            cherr.setCarta_credito("-");
                            cherr.setNote(getStringUTF8(err));
                            cherr.setData(date);
                            cherr.setTotal_diff(formatDoubleforMysql(v3[3]));
                            cherr.setRate("0.00");

                            cherr.setQuantity_user(formatDoubleforMysql(nc_quantnow));
                            cherr.setIp_value_op("");
                            cherr.setQuantity_system(formatDoubleforMysql(v3[2]));
                            cherr.setIp_value_sys("");

                            boolean esE = db.insert_new_Openclose_value_err(cherr);
                            if (!esE) {
                                ok = "I4E";
                                break;
                            } else {
//                                } else if (fd(formatDoubleforMysql(v3[3])) > 0) {
                                //creazione stock no change
//                            Stock st1 = new Stock();
//                            st1.setCodice("ST" + generaId(48));
//                            st1.setFiliale(filiale);
//                            st1.setTipo("OC");
//                            st1.setTill(safetill);
//                            st1.setIdoperation(cod);
//                            st1.setCodiceopenclose(cod);
//                            st1.setTipostock("NC");
//                            st1.setCod_value(v3[0]);
//                            st1.setKind("-");
//                            st1.setTotal(roundDoubleandFormat(fd(formatDoubleforMysql(v3[3])), 2));
//                            st1.setRate("1.00000000");
//                            Db_Master db1 = new Db_Master();
//                            NC_category ncc = db1.get_nc_category(v3[0]);
//                            st1.setControval(roundDoubleandFormat((fd(formatDoubleforMysql(v3[3])) * fd(ncc.getIp_prezzo_nc())), 2));
//                            st1.setUser(user);
//                            st1.setDate(date);
//                            st1.setId_op(filiale + db1.query_oc(cod).getId());
//                            db1.insertStock(st1);
//                            db1.closeDB();

//
//                            } else if (fd(formatDoubleforMysql(v3[3])) < 0) {
//                                Db_Master db8 = new Db_Master();
//                                Stock_report sr = db8.get_Stock_report(cod, v3[0], "-", "NC", filiale, safetill);
//                                db8.closeDB();
//
//                                String codsr = filiale + generaId(47);
//                                sr.setCodtr(cod);
//                                sr.setCodice(codsr);
//                                sr.setTotal("-"+roundDoubleandFormat(fd(formatDoubleforMysql(v3[3])), 2));
//                                sr.setSpread("1.0000");
//                                sr.setData(date);
//                                sr.setQuantity("-"+roundDoubleandFormat(fd(formatDoubleforMysql(v3[3])), 2));
//                                sr.setUser(user);
//                                Db_Master db9 = new Db_Master();
//                                db9.insert_Stockreport(sr);
//                                db9.closeDB();
//                                
//                                
//                                
//                             
                            }
                        }
                    }
                }
            }

        } else {
            ok = "I1";
        }

        if (ok.equals("0")) {
            boolean es4 = db.insert_realtime_open(cod);
            if (!es4) {
                ok = "I5";
            }
        }

        db.updateBlockedOperation(session.getAttribute("us_cod") + " | " + session.getAttribute("us_user"), "0");

        try {
            sleep(500);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }

        db.closeDB();

        if (ok.equals("0")) {
            Db_Master db1 = new Db_Master();
            db1.generateandinsertStockPrice(db1.query_oc(cod), null);
            db1.closeDB();
            redirect(request, response, "ti_openclose.jsp?esito=OK");
        } else {
            //delete
            delete_oc(cod);
            redirect(request, response, "ti_openclose.jsp?esito=false" + ok);
//            redirect(request, response, "ti_open.jsp?tillselected=" + safetill + "&opencloseid&" + opencloseid + "esito=" + ok);
        }

    }

    private String[] transferonsafeafterclose(String foreign_tr, String local_tr, String stock_tr,
            ArrayList<String[]> al_kifi, ArrayList<String[]> al_sic, ArrayList<String[]> al_noch, String loc_val[],
            String safetill, String opencloseid, String idsafe, String idsafeopenclose, String user, Db_Master db, String codicechiusura) {

        String[] out = {"-", "-"};
        String ok = "0";
        String dt_oper = new DateTime().toString(patternsqldate);
        if (foreign_tr.equals("Y") || local_tr.equals("Y")) {

            String cod_it = "IT" + generaId(23);
            IT_change it = new IT_change();

            it.setTill_from(safetill);
            it.setTill_to(idsafe);
            it.setIdopen_from(opencloseid);
            it.setIdopen_to(idsafeopenclose);
            it.setCod(cod_it);
            it.setFiliale(filiale);
            it.setUser(user);
            it.setFg_annullato("0");

            //DEL
            it.setDel_dt("1901-01-01 00:00:00");
            it.setDel_user("-");
            it.setDel_motiv("-");

            boolean es = db.insert_IT_change(it);
            if (es) {

                for (int i = 0; i < al_kifi.size(); i++) {
                    String[] v1 = al_kifi.get(i);
                    String s_totnow = v1[3];
                    if (s_totnow != null) {
                        if (!s_totnow.trim().equals("") && !s_totnow.trim().equals("0") && !s_totnow.trim().equals("0.00") && !s_totnow.trim().equals("0,00")) {

                            String val = v1[0];
                            boolean add = false;

                            if (local_tr.equals("Y")) {
                                if (val.equals(loc_val[0]) && v1[1].equals("01")) {
                                    add = true;
                                }
                            }

                            if (!add) {
                                if (!val.equals(loc_val[0])) {
                                    add = foreign_tr.equals("Y");
                                } else if (v1[1].equals("02")) {
                                    add = foreign_tr.equals("Y");
                                }

                                if (is_UK) {
                                    if (v1[1].equals("03")) {
                                        add = foreign_tr.equals("Y");
                                    }
                                }
                            }

                            if (add) {

                                Db_Master db8 = new Db_Master();
                                Stock_report srF = db8.get_Stock_report(opencloseid, v1[0], v1[1], "CH", filiale, safetill);
                                Stock_report srT = db8.get_Stock_report(idsafeopenclose, v1[0], v1[1], "CH", filiale, idsafe);
                                db8.closeDB();

                                String codsrF = filiale + generaId(47);
                                srF.setCodtr(codicechiusura);
                                srF.setCodice(codsrF);
                                double newtotA = -fd(formatDoubleforMysql(s_totnow));
                                srF.setTotal(roundDoubleandFormat(newtotA, 2));
                                srF.setSpread("1.0000");
                                srF.setData(dt_oper);
                                srF.setQuantity("0");
                                srF.setUser(user);

                                String codsrT = filiale + generaId(47);
                                srT.setCodtr(cod_it);
                                srT.setCodice(codsrT);
                                double newtotT = fd(formatDoubleforMysql(s_totnow));
                                srT.setTotal(roundDoubleandFormat(newtotT, 2));
                                srT.setSpread("1.0000");
                                srT.setData(dt_oper);
                                srT.setQuantity(roundDoubleandFormat(fd(formatDoubleforMysql(v1[2])), 0));
                                srT.setUser(user);

                                Db_Master db9A = new Db_Master();
                                db9A.insert_Stockreport(srF);
                                db9A.insert_Stockreport(srT);
                                db9A.closeDB();

                                IT_change it2 = new IT_change();
                                it2.setFiliale(filiale);
                                it2.setCod(cod_it);
                                it2.setValuta(v1[0]);
                                it2.setSupporto(v1[1]);
                                it2.setQuantita(formatDoubleforMysql(v1[2]));
                                it2.setTotale(formatDoubleforMysql(s_totnow));
                                it2.setDt_it(dt_oper);
                                boolean es2 = db.insert_IT_change_value(it2);
                                if (es2) {
                                    Real_oc_change to = new Real_oc_change();
                                    to.setFiliale(filiale);
                                    to.setCod_oc(idsafeopenclose);
                                    to.setValuta(v1[0]);
                                    to.setKind(v1[1]);
                                    to.setData(dt_oper);

                                    to.setNum_kind_op(roundDoubleandFormat((fd(db.getField_real_oc_change(to, "num_kind_op", "0")) + fd(formatDoubleforMysql(v1[2]))), 0));
                                    to.setValue_op(roundDoubleandFormat((fd(db.getField_real_oc_change(to, "value_op", "0.00")) + fd(formatDoubleforMysql(s_totnow))), 2));

                                    db.update_real_oc_change(to);
                                }
                            }

//                            
                        }
                    }
                }

                for (int i = 0; i < al_sic.size(); i++) {
                    String[] v2 = al_sic.get(i);
                    String s_totnow = v2[4];
                    //String s_differr = v2[8];
                    if (s_totnow != null) {
                        if (!s_totnow.trim().equals("") && !s_totnow.trim().equals("0") && !s_totnow.trim().equals("0.00") && !s_totnow.trim().equals("0,00")) {

                            String val = v2[0];
                            boolean add = false;

                            if (local_tr.equals("Y")) {
                                if (val.equals(loc_val[0]) && v2[1].equals("01")) {
                                    add = true;
                                }
                            }

                            if (!add) {
                                if (!val.equals(loc_val[0])) {
                                    add = foreign_tr.equals("Y");
                                } else if (v2[1].equals("02")) {
                                    add = foreign_tr.equals("Y");
                                }
                            }

                            if (add) {
                                IT_change it3 = new IT_change();
                                it3.setFiliale(filiale);
                                it3.setCod(cod_it);
                                it3.setValuta(v2[0]);
                                it3.setSupporto(v2[1]);
                                it3.setTaglio(v2[2]);
                                it3.setQuantita(v2[3]);
                                it3.setTotale(formatDoubleforMysql(s_totnow));
                                it3.setDt_it(dt_oper);
                                boolean es3 = db.insert_IT_change_tg(it3);
                                if (es3) {
                                    Real_oc_change to_cuts = new Real_oc_change();
                                    to_cuts.setFiliale(filiale);
                                    to_cuts.setCod_oc(idsafeopenclose);
                                    to_cuts.setValuta(v2[0]);
                                    to_cuts.setKind(v2[1]);
                                    to_cuts.setData(dt_oper);
                                    to_cuts.setIp_taglio(v2[2]);

                                    to_cuts.setNum_kind_op(roundDoubleandFormat((fd(db.getField_real_oc_change_cuts(to_cuts, "ip_quantity", "0")) + fd(formatDoubleforMysql(v2[3]))), 0));
                                    to_cuts.setValue_op(roundDoubleandFormat((fd(db.getField_real_oc_change_cuts(to_cuts, "ip_value", "0.00")) + fd(formatDoubleforMysql(s_totnow))), 2));

                                    db.update_real_oc_change_cuts(to_cuts);
                                } else {
                                    ok = "3IN"; //ko insert size
                                }
                            }
                        }
                    }
                }
            } else {
                ok = "2IN"; //ko insert value
            }

            if (ok.equals("0")) {
                out[0] = cod_it;
            } else {
                //delete it
                out[0] = null;
            }

            ok = "0";

        }

        try {
            sleep(500);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }

        if (stock_tr.equals("Y")) {
            String cod_itn = "ITN" + generaId(22);
            IT_change it = new IT_change();
            it.setTill_from(safetill);
            it.setTill_to(idsafe);
            it.setIdopen_from(opencloseid);
            it.setIdopen_to(idsafeopenclose);
            it.setCod(cod_itn);
            it.setFiliale(filiale);
            it.setUser(user);
            it.setFg_annullato("0");
            //DEL
            it.setDel_dt("1901-01-01 00:00:00");
            it.setDel_user("-");
            it.setDel_motiv("-");

            boolean es = db.insert_IT_change(it);
            if (es) {
                for (int i = 0; i < al_noch.size(); i++) {
                    String[] v3 = al_noch.get(i);
                    String nc_quantnow = v3[1];
                    if (nc_quantnow != null) {
                        if (!nc_quantnow.trim().equals("") && !nc_quantnow.trim().equals("0") && !nc_quantnow.trim().equals("0.00") && !nc_quantnow.trim().equals("0,00")) {
                            IT_change it2 = new IT_change();
                            it2.setFiliale(filiale);
                            it2.setCod(cod_itn);
                            it2.setQuantita(formatDoubleforMysql(nc_quantnow.trim()));
                            it2.setDt_it(dt_oper);
                            it2.setNc_causal(v3[0]);
                            boolean es2 = db.insert_IT_nochange_value(it2);
                            if (es2) {
                                Real_oc_nochange to = new Real_oc_nochange();
                                to.setFiliale(filiale);
                                to.setCod_oc(idsafeopenclose);
                                to.setGruppo_nc(v3[0]);
                                to.setQuantity(valueOf(parseIntR(db.getQuantity_real_oc_nochange(to)) + parseIntR(formatDoubleforMysql(nc_quantnow))));
                                to.setData(dt_oper);
                                db.update_real_oc_nochange(to);
                            } else {
                                ok = "2IN"; //ko insert value
                                break;
                            }
                        }
                    }
                }
            } else {
                ok = "2IN"; //ko insert value
            }
            if (ok.equals("0")) {
                out[1] = cod_itn;
            } else {
                //delete it
                out[1] = null;
            }
        }
        return out;
    }

    private boolean isopentill(String safetill) {
        ArrayList<Till> list_ALL_till_enabled = list_ALL_till_enabled();
        ArrayList<Till> array_till = list_till_status(null, null);
        boolean isopen = false;
        for (int j = 0; j < list_ALL_till_enabled.size(); j++) {
            Till t0 = list_ALL_till_enabled.get(j);
            if (t0.getCod().equals(safetill)) {
                for (int i = 0; i < array_till.size(); i++) {
                    if (t0.getCod().equals(array_till.get(i).getCod())) {
                        if (array_till.get(i).getTy_opcl().equals("OPEN")) {
                            isopen = true;
                        }
                        break;
                    }
                }
            }
        }
        return isopen;
    }

    private boolean isclosetill(String safetill) {
        ArrayList<Till> list_ALL_till_enabled = list_ALL_till_enabled();
        ArrayList<Till> array_till = list_till_status(null, null);
        boolean isclose = false;
        for (int j = 0; j < list_ALL_till_enabled.size(); j++) {
            Till t0 = list_ALL_till_enabled.get(j);
            if (t0.getCod().equals(safetill)) {
                for (int i = 0; i < array_till.size(); i++) {
                    if (t0.getCod().equals(array_till.get(i).getCod())) {
                        if (array_till.get(i).getTy_opcl().equals("OPEN")) {
                        } else {
                            isclose = true;
                        }
                        break;
                    }
                }
            }
        }
        return isclose;
    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void oc_close(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

//        Utility.printRequest(request);
        String safetill = request.getParameter("safetill");

        String ok = "0";
        if (isclosetill(safetill)) {
            ok = "n1";
        }
        Db_Master db = new Db_Master();
        String cod = "OCC" + generaId(22);
        HttpSession session = request.getSession();

        if (ok.equals("0")) {

            String user = (String) session.getAttribute("us_cod");
            if (user == null) {
                user = "9999";
            }

            String setall = request.getParameter("setall");

            if (setall.equals("1")) {
                Db_Master db1 = new Db_Master();
                db1.insert_valuekyc_Module(cod, user, "SELECTALL");
                db1.closeDB();
            }

            String date = new DateTime().toString(patternsqldate);

            boolean dividi = db.get_national_office().getChangetype().equals("/");

            String loc_val[] = db.get_local_currency();

            String errorpresent = request.getParameter("errorpresent");

            String opencloseid = request.getParameter("opencloseid");

            String idsafe = request.getParameter("idsafe");
            String idsafeopenclose = request.getParameter("idsafeopenclose");

            ArrayList<String[]> al_kifi = new ArrayList<>();
            ArrayList<String[]> al_sicu = new ArrayList<>();
            ArrayList<String[]> al_noch = new ArrayList<>();
            ArrayList<String[]> al_posv = new ArrayList<>();

            ArrayList<String[]> array_kind_fingures_openclose = db.kind_figures_openclose();
            List<Sizecuts> array_figures_sizecuts = db.figures_sizecuts_enabled();
            ArrayList<String[]> array_list_nochange = db.list_nochange();
            ArrayList<String[]> array_list_kind_pos = db.list_kind_pos();

            String foreign_tr = request.getParameter("foreign_tr");
            if (foreign_tr == null) {
                foreign_tr = "N";
            } else if (foreign_tr.trim().equals("on")) {
                foreign_tr = "Y";
            } else {
                foreign_tr = "N";
            }
            String local_tr = request.getParameter("local_tr");
            if (local_tr == null) {
                local_tr = "N";
            } else if (local_tr.trim().equals("on")) {
                local_tr = "Y";
            } else {
                local_tr = "N";
            }
            String stock_tr = request.getParameter("stock_tr");
            if (stock_tr == null) {
                stock_tr = "N";
            } else if (stock_tr.trim().equals("on")) {
                stock_tr = "Y";
            } else {
                stock_tr = "N";
            }

            boolean itsafe = foreign_tr.equals("Y") || local_tr.equals("Y") || stock_tr.equals("Y");

            for (int i = 0; i < array_kind_fingures_openclose.size(); i++) {

                String s_curr = request.getParameter("s_curr" + i);
                String s_kind = request.getParameter("s_kind" + i);
                String s_quantnow = request.getParameter("s_quantnow" + i);
                String s_totnow = request.getParameter("s_totnow" + i);
                String s_quantold = request.getParameter("s_quantold" + i);
                String s_totalold = request.getParameter("s_totalold" + i);
                String s_diffgap = request.getParameter("s_diffgap" + i);
                if (s_diffgap == null) {
                    s_diffgap = "0.00";
                }
                String s_differr = "N";
                if (!s_diffgap.equals("0.00") && !s_diffgap.equals("0,00")) {
                    s_differr = "Y";
                }
                String[] v1 = {s_curr, s_kind, s_quantnow, s_totnow, s_quantold, s_totalold, s_diffgap, s_differr};
                al_kifi.add(v1);

                if (quantityNotZERO(s_quantnow, s_totnow)) { //13/11

                    String v9 = array_kind_fingures_openclose.get(i)[0];
                    List<String> array_sizecuts = array_figures_sizecuts.stream().filter(t1 -> t1.getValuta().equals(v9)).map(t1 -> t1.getIp_taglio()).collect(toList());

                    for (int j = 0; j < array_sizecuts.size(); j++) {

                        String curr = request.getParameter("curr" + i + "_" + j);
                        String kind = request.getParameter("kind" + i + "_" + j);
                        String cuts = request.getParameter("cuts" + i + "_" + j);
                        String quantnow = request.getParameter("quantnow" + i + "_" + j);
                        String totnow = request.getParameter("totnow" + i + "_" + j);
                        String quantold = request.getParameter("quantold" + i + "_" + j);
                        String totold = request.getParameter("totold" + i + "_" + j);
                        String diffgapsize = request.getParameter("diffgapsize" + i + "_" + j);
                        if (diffgapsize == null) {
                            diffgapsize = "0.00";
                        }
                        String differr = "N";
                        if (!diffgapsize.equals("0.00") && !diffgapsize.equals("0,00")) {
                            differr = "Y";
                        }
                        String[] v2 = {curr, kind, cuts, quantnow, totnow, quantold, totold, diffgapsize, differr};
                        al_sicu.add(v2);
                    }
                }

            }

            for (int i = 0; i < array_list_nochange.size(); i++) {

                String nc_cod = request.getParameter("nc_cod" + i);
                String nc_quantnow = request.getParameter("nc_quantnow" + i);
                String nc_quantold = request.getParameter("nc_quantold" + i);
                String nc_diffgap = request.getParameter("nc_diffgap" + i);
                String nc_differr = "N";
                if (!nc_diffgap.equals("0.00") && !nc_diffgap.equals("0,00")) {
                    nc_differr = "Y";
                }
                String[] v3 = {nc_cod, nc_quantnow, nc_quantold, nc_diffgap, nc_differr};
                al_noch.add(v3);
            }

            int sizepos = array_list_kind_pos.size();

            int sizeposCZ = 0;

            if (is_CZ) {
                List<CashAD_CZ> list_CA_CZ = list_CA_CZ(filiale, opencloseid, user);
                sizeposCZ = list_CA_CZ.size();
            }

            for (int i = 0; i < sizepos; i++) {
                String pos_ki = request.getParameter("pos_ki_" + i);
                if (pos_ki == null) {
                    continue;
                }
                String pos_cc = request.getParameter("pos_cc_" + i);
                String pos_quantnow = request.getParameter("pos_quantnow" + i);
                String pos_totnow = request.getParameter("pos_totnow" + i);
                String pos_quantold = request.getParameter("pos_quantold" + i);
                String pos_totold = request.getParameter("pos_totold" + i);
                String pos_diffgap = request.getParameter("pos_diffgap" + i);
                String pos_buy_value = "0.00";
                String pos_rate_value = "1.00000000";
                String pos_spread_value = "0.00";
                String pos_contr = "0.00";
                String pos_differr = "N";
                if (pos_diffgap != null && !pos_diffgap.equals("0.00") && !pos_diffgap.equals("0,00")) {
                    pos_differr = "Y";
                }
                String[] v4 = {pos_quantnow, pos_totnow, pos_quantold, pos_totold, pos_diffgap, pos_differr, pos_ki, pos_cc, pos_buy_value,
                    pos_spread_value, "", "", pos_rate_value, pos_contr};
                al_posv.add(v4);

            }

            for (int i = 0; i < sizeposCZ; i++) {
                String pos_ki = "04";
                String pos_cc = request.getParameter("pos_ki_P" + i) + "-" + request.getParameter("pos_cc_P" + i);
                String pos_quantnow = request.getParameter("pos_quantnowP" + i);
                String pos_totnow = request.getParameter("pos_totnowP" + i);
                String pos_quantold = request.getParameter("pos_quantoldP" + i);
                String pos_totold = request.getParameter("pos_totoldP" + i);
                String pos_diffgap = request.getParameter("pos_diffgapP" + i);
                String pos_buy_value = request.getParameter("pos_buyP" + i);
                String pos_rate_value = request.getParameter("pos_ratenowP" + i);
                String pos_spread_value = request.getParameter("pos_diffspreadP" + i);
                String pos_contr = request.getParameter("pos_contrP" + i);
                String pos_differr = "N";
                if (pos_diffgap != null && !pos_diffgap.equals("0.00") && !pos_diffgap.equals("0,00")) {
                    pos_differr = "Y";
                }
                String[] v4 = {pos_quantnow, pos_totnow, pos_quantold, pos_totold, pos_diffgap, pos_differr, pos_ki, pos_cc, pos_buy_value,
                    pos_spread_value, request.getParameter("pos_ki_P" + i), request.getParameter("pos_cc_P" + i), pos_rate_value, pos_contr};
                al_posv.add(v4);
            }
            Openclose oc = new Openclose();
            oc.setFiliale(filiale);
            oc.setCod(cod);
            oc.setTill(safetill);
            oc.setUser(user);
            oc.setFg_tipo("C");
            oc.setData(date);
            oc.setErrors(errorpresent);

            if (itsafe) {
                String[] cod_out = transferonsafeafterclose(foreign_tr, local_tr, stock_tr, al_kifi, al_sicu,
                        al_noch, loc_val, safetill, opencloseid, idsafe, idsafeopenclose, user, db, cod);
                oc.setCod_it(cod_out[0]);
                oc.setCod_itnc(cod_out[1]);
            } else {
                oc.setCod_it("-");
                oc.setCod_itnc("-");
            }

            oc.setForeign_tr(foreign_tr);
            oc.setLocal_tr(local_tr);
            oc.setStock_tr(stock_tr);

            boolean es = db.insert_new_Openclose(oc);
            if (es) {
                sleeping(500);
                for (int i = 0; i < al_kifi.size(); i++) {
                    String[] v1 = al_kifi.get(i);
                    Currency cu = db.getCurrency(v1[0], filiale);
                    String s_totnow = v1[3];
                    String s_totold = v1[5];
                    String s_differr = v1[7];
                    if (s_totnow != null) {
                        if (quantityNotZERO(s_totnow, s_totold)) {

                            String val = v1[0];
                            boolean add = false;

                            if (local_tr.equals("Y")) {
                                if (val.equals(loc_val[0]) && v1[1].equals("01")) {
                                    add = true;
                                }
                            }

                            if (!add) {
                                if (!val.equals(loc_val[0])) {
                                    add = foreign_tr.equals("Y");
                                } else if (v1[1].equals("02")) {
                                    add = foreign_tr.equals("Y");
                                }
                                if (is_UK) {
                                    if (v1[1].equals("03")) {
                                        add = foreign_tr.equals("Y");
                                    }
                                }
                            }

//                        if (!add) {
                            Openclose chv = new Openclose();
                            chv.setFiliale(filiale);
                            chv.setCod(cod);
                            chv.setValuta(v1[0]);
                            chv.setKind(v1[1]);
                            chv.setValue_op(formatDoubleforMysql(v1[3]));
                            chv.setNum_kind_op(formatDoubleforMysql(v1[2]));
                            chv.setValue_cl(v1[5]);
                            chv.setNum_kind_cl(v1[4]);
                            chv.setData(date);
                            if (add) {
                                chv.setFisico("N");
                            } else {
                                chv.setFisico("S");
                            }

                            boolean es1 = db.insert_new_Openclose_value_ch(chv);
                            if (!es1) {
                                ok = "I2";
                                break;
                            }

                            Db_Master db8 = new Db_Master();
                            Stock_report sr = db8.get_Stock_report(opencloseid, v1[0], v1[1], "CH", filiale, safetill);
                            db8.closeDB();
                            String codsr = filiale + generaId(47);
                            sr.setCodtr(cod);
                            sr.setCodice(codsr);
                            sr.setTotal(formatDoubleforMysql(v1[3]));
                            sr.setSpread("1.0000");
                            sr.setData(date);
                            sr.setQuantity(formatDoubleforMysql(v1[2]));
                            sr.setUser(user);
                            Db_Master db9 = new Db_Master();
                            db9.insert_Stockreport(sr);
                            db9.closeDB();

                        }
                    }
                    if (s_differr.equals("Y")) {
                        String err = request.getParameter("trfigerr_" + v1[0] + "_" + v1[1]);
                        if (err != null) {
                            Openclose cherr = new Openclose();
                            cherr.setFiliale(filiale);
                            cherr.setCod(cod);
                            cherr.setTipo("CH");
                            cherr.setValuta(v1[0]);
                            cherr.setKind(v1[1]);
                            cherr.setGruppo_nc("-");
                            cherr.setCarta_credito("-");
                            cherr.setNote(getStringUTF8(err));
                            cherr.setData(date);
                            cherr.setTotal_diff(formatDoubleforMysql(v1[6]));
                            String[] rate = db.rate_currency(cu, true, true);
                            cherr.setRate(rate[0]);

                            cherr.setQuantity_user(formatDoubleforMysql(v1[2]));
                            cherr.setIp_value_op(formatDoubleforMysql(v1[3]));
                            cherr.setQuantity_system(formatDoubleforMysql(v1[4]));
                            cherr.setIp_value_sys(formatDoubleforMysql(v1[5]));

                            boolean esE = db.insert_new_Openclose_value_err(cherr);
                            if (!esE) {
                                ok = "I4E";
                                break;
                            } else if (fd(formatDoubleforMysql(v1[6])) > 0) {
                                //NUOVO STOCK
                                Stock st1 = new Stock();
                                st1.setCodice("ST" + generaId(48));
                                st1.setFiliale(filiale);
                                st1.setTipo("OC");
                                st1.setTill(safetill);
                                st1.setIdoperation(cod);
                                st1.setCodiceopenclose(cod);
                                st1.setTipostock("CH");
                                st1.setCod_value(v1[0]);
                                st1.setKind(v1[1]);
                                st1.setTotal(roundDoubleandFormat(parseDoubleR(formatDoubleforMysql(v1[6])), 2));
                                st1.setRate(rate[0]);
                                st1.setControval(roundDoubleandFormat(getControvalore(parseDoubleR(formatDoubleforMysql(v1[6])), fd(rate[0]), dividi), 2));
                                st1.setUser(user);
                                st1.setDate(date);
                                Db_Master db1 = new Db_Master();
                                st1.setId_op(filiale + db1.query_oc(cod).getId());
                                db1.insertStock(st1);
                                db1.closeDB();

//                            Db_Master db8 = new Db_Master();
//                            ArrayList<Stock_report> li = db8.list_Stock_report(opencloseid);
//                            db8.closeDB();
//                                }
//                            }
                            } else if (fd(formatDoubleforMysql(v1[6])) < 0) {

                                scalareErroridaStock(filiale, v1[0], formatDoubleforMysql(v1[6]), v1[1]);

//                            Db_Master db8 = new Db_Master();
//                            ArrayList<Stock_report> li = db8.list_Stock_report(opencloseid);
//                            db8.closeDB();
//                            for (int x = 0; x < li.size(); x++) {
//                                Stock_report sr = li.get(x);
//                            Db_Master db8 = new Db_Master();
//                            Stock_report sr = db8.get_Stock_report(opencloseid, v1[0], v1[1], "CH", filiale, safetill);
//                            db8.closeDB();
////                                if (sr.getCod_value().equals(v1[0]) && sr.getKind().equals(v1[1])) {
//                            String codsr = filiale + generaId(47);
//                            sr.setCodtr(cod);
//                            sr.setCodice(codsr);
//                            double newtot = fd(sr.getTotal()) - parseDoubleR(formatDoubleforMysql(v1[6]));
//                            sr.setTotal(roundDoubleandFormat(newtot, 2));
//                            sr.setSpread("1.0000");
//                            sr.setData(date);
//                            sr.setQuantity("0");
//                            sr.setUser(user);
//                            Db_Master db9 = new Db_Master();
//                            db9.insert_Stockreport(sr);
//                            db9.closeDB();
//                                }
//                            }
                            }
                        }
                    }
                }

            }

            if (ok.equals("0")) {
                for (int i = 0; i < al_sicu.size(); i++) {
                    String[] v2 = al_sicu.get(i);
                    String s_totnow = v2[4];
//                    String s_differr = v2[8];
                    if (s_totnow != null) {
                        if (quantityNotZERO(s_totnow, null)) {
//                        if (!s_totnow.trim().equals("") && !s_totnow.trim().equals("0") && !s_totnow.trim().equals("0.00") && !s_totnow.trim().equals("0,00")) {

                            String val = v2[0];
                            boolean add = false;
//
                            if (local_tr.equals("Y")) {
                                if (val.equals(loc_val[0]) && v2[1].equals("01")) {
                                    add = true;
                                }
                            }

                            if (!add) {
                                if (!val.equals(loc_val[0])) {
                                    add = foreign_tr.equals("Y");
                                } else if (v2[1].equals("02")) {
                                    add = foreign_tr.equals("Y");
                                }
                            }
//
//                            if (!add) {
                            Openclose chtg = new Openclose();
                            chtg.setFiliale(filiale);
                            chtg.setCod(cod);
                            chtg.setValuta(v2[0]);
                            chtg.setKind(v2[1]);
                            chtg.setIp_taglio(v2[2]);
                            chtg.setIp_quantity(formatDoubleforMysql(v2[3]));
                            chtg.setIp_value(formatDoubleforMysql(s_totnow));
                            chtg.setData(date);

                            if (add) {
                                chtg.setFisico("N");
                            } else {
                                chtg.setFisico("S");
                            }

                            boolean es2 = db.insert_new_Openclose_value_tg(chtg);
                            if (!es2) {
                                ok = "I3";
                                break;
                            }

                        }
                    }
                }
            }

            if (ok.equals("0")) {

                for (int i = 0; i < al_noch.size(); i++) {
                    String[] v3 = al_noch.get(i);
                    String nc_quantnow = v3[1];
                    String nc_quantold = v3[2];
                    String nc_differr = v3[4];
                    if (nc_quantnow != null) {
                        if (quantityNotZERO(nc_quantnow, nc_quantold)) {
                            Openclose chnc = new Openclose();
                            chnc.setFiliale(filiale);
                            chnc.setCod(cod);
                            chnc.setGruppo_nc(v3[0]);
                            chnc.setQuantity_user(formatDoubleforMysql(nc_quantnow));
                            chnc.setQuantity_system(formatDoubleforMysql(v3[2]));
                            chnc.setData(date);

                            if (stock_tr.equals("Y")) {
                                chnc.setFisico("N");
                            } else {
                                chnc.setFisico("S");
                                Db_Master db8 = new Db_Master();
                                Stock_report sr = db8.get_Stock_report(opencloseid, v3[0], "01", "NC", filiale, safetill);
                                db8.closeDB();

                                String codsr = filiale + generaId(47);
                                sr.setCodtr(cod);
                                sr.setCodice(codsr);
                                sr.setTotal(roundDoubleandFormat(fd(formatDoubleforMysql((nc_quantnow))), 2));
                                sr.setSpread("1.0000");
                                sr.setData(date);
                                sr.setQuantity(roundDoubleandFormat(fd(formatDoubleforMysql((nc_quantnow))), 2));
                                sr.setUser(user);
                                Db_Master db9 = new Db_Master();
                                db9.insert_Stockreport(sr);
                                db9.closeDB();
                            }

                            boolean es3 = db.insert_new_Openclose_value_nc(chnc);
                            if (!es3) {
                                ok = "I3";
                                break;
                            }

                        }
                    }

                    if (nc_differr.equals("Y")) {
                        String err = request.getParameter("trnocerr_" + v3[0]);
                        if (err != null) {

                            Openclose cherr = new Openclose();
                            cherr.setFiliale(filiale);
                            cherr.setCod(cod);
                            cherr.setTipo("NC");
                            cherr.setValuta("-");
                            cherr.setKind("01");
                            cherr.setGruppo_nc(v3[0]);
                            cherr.setCarta_credito("-");
                            cherr.setNote(getStringUTF8(err));
                            cherr.setData(date);
                            cherr.setTotal_diff(formatDoubleforMysql(v3[3]));
                            cherr.setRate("0.00");
                            cherr.setQuantity_user(formatDoubleforMysql(nc_quantnow));
                            cherr.setIp_value_op("");
                            cherr.setQuantity_system(formatDoubleforMysql(v3[2]));
                            cherr.setIp_value_sys("");
                            boolean esE = db.insert_new_Openclose_value_err(cherr);
                            if (!esE) {
                                ok = "I4E";
                                break;
                            }
                        }
                    }
                }
            }

            if (ok.equals("0")) {
                for (int i = 0; i < al_posv.size(); i++) {
                    String[] v4 = al_posv.get(i);
                    String pos_totnow = formatDoubleforMysql(v4[1]);
                    String pos_totold = formatDoubleforMysql(v4[3]);
                    String pos_differr = v4[5];
                    if (pos_totnow != null) {
                        if (quantityNotZERO(pos_totnow, pos_totold)) {
//                        if (!pos_totnow.trim().equals("") && !pos_totnow.trim().equals("0") && !pos_totnow.trim().equals("0.00") && !pos_totnow.trim().equals("0,00")) {
                            Openclose posv = new Openclose();
                            posv.setFiliale(filiale);
                            posv.setCod(cod);
                            posv.setValuta(loc_val[0]);
                            posv.setKind(v4[6]);
                            posv.setCarta_credito(v4[7]);
                            posv.setIp_quantity_op(formatDoubleforMysql(v4[0]));
                            posv.setIp_value_op(formatDoubleforMysql(v4[1]));
                            posv.setIp_quantity_sys(formatDoubleforMysql(v4[2]));
                            posv.setIp_value_sys(formatDoubleforMysql(v4[3]));
                            posv.setData(date);

                            boolean es3 = db.insert_new_Openclose_pos(posv);
                            if (!es3) {
                                ok = "I5";
                                break;
                            } else {
                                if (is_CZ && posv.getKind().equals("04")) {

                                    ET_change et = new ET_change();
                                    et.setCod("ETCADV" + generaId(19));
                                    et.setFiliale(filiale);
                                    et.setUser(user);
                                    et.setTill_from(safetill);
                                    et.setFg_tofrom("T");
                                    et.setFg_brba("BA");
                                    et.setCod_dest(v4[10]);
                                    et.setIdopen_from(cod);
                                    et.setDt_it(date);
                                    et.setFg_annullato("0");
                                    et.setNote("CLOSE OPERATION CASH ADVANCE - " + v4[11]);
                                    //del
                                    et.setDel_dt("1901-01-01 00:00:00");
                                    et.setDel_user("-");
                                    et.setDel_motiv("-");
                                    //altri
                                    et.setIp_oneri("0.00");
                                    et.setCod_in("");
                                    et.setId_in("-");
                                    et.setFiliale_in("-");
                                    et.setAuto("A");

                                    boolean es1 = db.insert_ET_change(et);
                                    if (es1) {
                                        ET_change etv = new ET_change();
                                        etv.setFiliale(filiale);
                                        etv.setCod(et.getCod());
                                        etv.setValuta(v4[11]);
                                        etv.setSupporto(posv.getKind());
                                        etv.setIp_stock(formatDoubleforMysql(v4[0]));
                                        etv.setIp_quantity(formatDoubleforMysql(v4[1]));
                                        etv.setIp_rate(formatDoubleforMysql(v4[12]));
                                        etv.setIp_total(formatDoubleforMysql(v4[13]));
                                        etv.setIp_buyvalue(formatDoubleforMysql(v4[8]));
                                        etv.setIp_spread(formatDoubleforMysql(v4[9]));

                                        etv.setDt_it(et.getDt_it());
                                        db.insert_ET_change_value(etv);

//                                        
//                                        [
//                                                1, //  NUMERO 
//                                                5,00, //quantità
//                                                1, 
//                                                5,00, 
//                                                0,00, 
//                                        N, 
//                                        04,
//                                        600-EUR, 
//                                        101,82, 
//                                        -1,82, 
//                                        600, 
//                                        EUR, 
//                                        20,00000000, 
//                                        100,00
//                                        ]
                                    }

                                } else {
                                    ET_change et = new ET_change();
                                    et.setCod("ETPO" + generaId(21));
                                    et.setFiliale(filiale);
                                    et.setUser(user);
                                    et.setTill_from(safetill);
                                    et.setFg_tofrom("T");
                                    et.setFg_brba("BA");
                                    et.setCod_dest(v4[7]);
                                    et.setIdopen_from(cod);
                                    et.setDt_it(date);
                                    et.setFg_annullato("0");
                                    et.setNote("CLOSE OPERATION POS");
                                    //del
                                    et.setDel_dt("1901-01-01 00:00:00");
                                    et.setDel_user("-");
                                    et.setDel_motiv("-");
                                    //altri
                                    et.setIp_oneri("0.00");
                                    et.setCod_in("");
                                    et.setId_in("-");
                                    et.setFiliale_in("-");
                                    et.setAuto("A");

                                    boolean es1 = db.insert_ET_change(et);
                                    if (es1) {
                                        ET_change etv = new ET_change();
                                        etv.setFiliale(filiale);
                                        etv.setCod(et.getCod());
                                        etv.setValuta(loc_val[0]);
                                        etv.setSupporto(v4[6]);
                                        etv.setIp_stock(formatDoubleforMysql(v4[0]));
                                        etv.setIp_quantity(formatDoubleforMysql(v4[1]));
                                        etv.setIp_rate("1.00");
                                        etv.setIp_total(formatDoubleforMysql(v4[1]));
                                        etv.setIp_buyvalue("0.00");
                                        etv.setIp_spread("0.00");
                                        etv.setDt_it(et.getDt_it());
                                        db.insert_ET_change_value(etv);
                                    }
                                }
                            }
                        }
                    }

                    if (pos_differr.equals("Y")) {
                        String err = request.getParameter("trposerr_" + v4[6] + "-" + v4[7]);
                        String valuta = loc_val[0];
                        String carcre = v4[7];
                        if (err == null && is_CZ) {
                            err = request.getParameter("trposerr_" + v4[7]);
                            try {
                                valuta = v4[7].split("-")[1];
                                carcre = v4[7].split("-")[0];
                            } catch (Exception w) {
                                valuta = loc_val[0];
                                carcre = v4[7];
                            }
                        }

                        if (err != null) {
                            Openclose cherr = new Openclose();
                            cherr.setFiliale(filiale);
                            cherr.setCod(cod);
                            cherr.setTipo("PO");
                            cherr.setValuta(valuta);
                            cherr.setKind(v4[6]);
                            cherr.setGruppo_nc("-");
                            cherr.setCarta_credito(carcre);
                            cherr.setNote(getStringUTF8(err));
                            cherr.setData(date);
                            cherr.setTotal_diff(formatDoubleforMysql(v4[4]));
                            cherr.setRate(formatDoubleforMysql(v4[12]));
                            cherr.setQuantity_user(formatDoubleforMysql(v4[0]));
                            cherr.setIp_value_op(formatDoubleforMysql(v4[1]));
                            cherr.setQuantity_system(formatDoubleforMysql(v4[2]));
                            cherr.setIp_value_sys(formatDoubleforMysql(v4[3]));
                            boolean esE = db.insert_new_Openclose_value_err(cherr);
                            if (!esE) {
                                ok = "I5E";
                                break;
                            }
                        }
                    }
                }
            }
        } else {
            ok = "I1";
        }

        if (ok.equals("0")) {
            boolean es4 = db.delete_realtime_close(cod);
            if (!es4) {
                ok = "I5";
            }
        }

        db.updateBlockedOperation(session.getAttribute("us_cod") + " | " + session.getAttribute("us_user"), "0");

        try {
            sleep(500);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
        db.closeDB();

        if (ok.equals("0")) {
            Db_Master db1 = new Db_Master();
            db1.generateandinsertStockPrice(db1.query_oc(cod), null);
            db1.closeDB();

            redirect(request, response, "ti_openclose.jsp?esito=OK");
        } else {
            delete_oc(cod);
            redirect(request, response, "ti_openclose.jsp?esito=false" + ok);
//            redirect(request, response, "ti_close.jsp?tillselected=" + safetill + "&opencloseid&" + opencloseid + "esito=false" + ok);
        }

    }

    private boolean quantityNotZERO(String value1, String value2) {
        if (value1 != null && value2 != null) {
            return (!value1.trim().equals("") && !value1.trim().equals("0") && !value1.trim().equals("0.00") && !value1.trim().equals("0,00") && !value1.trim().equals("0"))
                    || (!value2.trim().equals("") && !value2.trim().equals("0") && !value2.trim().equals("0.00") && !value2.trim().equals("0,00") && !value2.trim().equals("0"));
        } else if (value1 != null) {
            return (!value1.trim().equals("") && !value1.trim().equals("0") && !value1.trim().equals("0.00") && !value1.trim().equals("0,00") && !value1.trim().equals("0"));
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
    protected void et_change(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

//        Utility.printRequest(request);
//        if (true) {
//            return;
//        }
        HttpSession session = request.getSession();

        String user = (String) session.getAttribute("us_cod");
        if (user == null) {
            user = "9999";
        }

//        
        String oper = new DateTime().toString(patternsqldate);
        String cod = "ET" + generaId(23);

        String bankbranch = request.getParameter("bankbranchv");
        String typeop = request.getParameter("typeopv");
        String tofrom = request.getParameter("tofrom");

        String idopentillfrom = request.getParameter("idopentillfrom");
        String tillfrom = request.getParameter("tillfrom");
        String note = request.getParameter("note");

        String srcoff = request.getParameter("srcoff");
        if (srcoff == null) {
            srcoff = "OFFLINE";
        }

        String autman = request.getParameter("autman");
        if (autman == null) {
            autman = "A";
            srcoff = "ONLINE";
        } else {
            if (autman.equals("M")) {
                srcoff = "OFFLINE";
            }
        }

        ET_change et = new ET_change();
        et.setCod(cod);
        et.setFiliale(filiale);
        et.setUser(user);
        et.setTill_from(tillfrom);
        et.setFg_tofrom(tofrom);
        et.setFg_brba(typeop);
        et.setCod_dest(bankbranch);
        et.setIdopen_from(idopentillfrom);
        et.setDt_it(oper);
        et.setFg_annullato("0");
        et.setNote(getStringUTF8(note));
        et.setAuto(autman);

        //del
        et.setDel_dt("1901-01-01 00:00:00");
        et.setDel_user("-");
        et.setDel_motiv("-");

        //altri
        et.setIp_oneri("0.00");

        String srcing = request.getParameter("srcing");
        if (srcing == null) {
            srcing = "";
        }
        et.setCod_in(srcing);

        boolean to = tofrom.equals("T");
        boolean fr = tofrom.equals("F");
        boolean bank = typeop.equals("BA");
        boolean branch = typeop.equals("BR");
        boolean online = srcoff.equals("ONLINE");
        boolean offline = srcoff.equals("OFFLINE");

        Db_Master db = new Db_Master();
        String check = insert_ET_Change_CHECK(request, db, et, to, fr, bank, branch, online, offline, srcing, bankbranch);
        String ok;
        if (check.equals("0")) {
            ok = insert_ET_Change(request, et, to, fr, bank, branch, online, offline, srcing, bankbranch);
        } else {
            ok = check;
        }
        db.closeDB();
        Db_Master db1 = new Db_Master();
        db1.updateBlockedOperation(session.getAttribute("us_cod") + " | " + session.getAttribute("us_user"), "0");
        db1.closeDB();
        if (ok.equals("0")) {
            //sblocca to branch della filiale di partenza
            if (fr && branch && online) {
                if (srcing.startsWith("ET")) {
                    Db_Master db2 = new Db_Master();
                    boolean esx = db2.update_et_tobranch(srcing, cod, user, oper, bankbranch);
                    db2.closeDB();
                    if (!esx) {
                        ok = "Q";
                    }
                }
            } //offline ver
        }

        try {
            sleep(500);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }

//////
        if (ok.equals("0")) {
//////            if (quant) {

            delete_IT_change_temp(idopentillfrom, "", filiale);
            String pr = "N";
            if (tofrom.equals("T") && typeop.equals("BR")) {
                pr = "Y";
            } else if (tofrom.equals("T") && typeop.equals("BA")) {
                pr = "Y2";
            }

            redirect(request, response, "et_change.jsp?esito=OK&cod=" + cod + "&pr=" + pr);

//////            } else {
//////                Engine.delete_ET_change(cod, idopentillfrom, filiale);
//////                redirect(request, response, "et_change.jsp?search=sra1&idopentillfrom=" + idopentillfrom + "&bankbranch=" + bankbranch + "&tofrom=" + tofrom + "&tillfrom=" + tillfrom + "&typeop=" + typeop + "&esito=3Q");
//////            }
        } else {
            delete_ET_change(cod, idopentillfrom, filiale);
            redirect(request, response, "et_change.jsp?search=sra1&idopentillfrom=" + idopentillfrom + "&bankbranch=" + bankbranch
                    + "&tofrom=" + tofrom + "&tillfrom=" + tillfrom + "&typeop=" + typeop + "&srcoff=" + srcoff + "&srcing=" + srcing + "&srcing2=" + srcing + "&esito=" + ok);
        }

    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void it_change(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // 
        HttpSession session = request.getSession();
        String user = (String) session.getAttribute("us_cod");
        if (user == null) {
            user = "9999";
        }

        String safefr = request.getParameter("safefr");
        String safeto = request.getParameter("safeto");

        String idopentillfrom = request.getParameter("idopentillfrom");
        String idopentillto = request.getParameter("idopentillto");

        String tillfrom = request.getParameter("tillfrom");
        String tillto = request.getParameter("tillto");

        String cod = "IT" + generaId(23);
        IT_change it = new IT_change();
        it.setTill_from(tillfrom);
        it.setTill_to(tillto);

        it.setIdopen_from(idopentillfrom);
        it.setIdopen_to(idopentillto);
        it.setCod(cod);
        it.setFiliale(filiale);
        it.setUser(user);
        it.setFg_annullato("0");

        it.setDel_dt("1901-01-01 00:00:00");
        it.setDel_user("-");
        it.setDel_motiv("-");
        String oper = new DateTime().toString(patternsqldate);

        ArrayList<String[]> al_kifi = new ArrayList<>();
        ArrayList<String[]> al_sicu = new ArrayList<>();
        Db_Master db = new Db_Master();
        ArrayList<String[]> array_list_oc_change = db.list_oc_change_real();

        List<Sizecuts> array_figures_sizecuts = db.figures_sizecuts_enabled();
        db.closeDB();
        ArrayList<String> array_sizecuts = new ArrayList<>();

        for (int i = 0; i < array_list_oc_change.size(); i++) {
            if (idopentillfrom.equals(array_list_oc_change.get(i)[0])) {
                String currency = request.getParameter("currency" + i);
                String kind = request.getParameter("kind" + i);
                String s_quantold = formatDoubleforMysql(request.getParameter("s_quantold" + i));
                String s_totold = formatDoubleforMysql(request.getParameter("s_totold" + i));
                String s_quantnow = formatDoubleforMysql(request.getParameter("s_quantnow" + i));
                String s_totnow = formatDoubleforMysql(request.getParameter("s_totnow" + i));
                String[] v1 = {currency, kind, s_quantold, s_totold, s_quantnow, s_totnow};
                al_kifi.add(v1);

                for (int j = 0; j < array_figures_sizecuts.size(); j++) {
                    if (array_figures_sizecuts.get(j).getValuta().equals(array_list_oc_change.get(i)[2])) {
                        array_sizecuts.add(array_figures_sizecuts.get(j).getIp_taglio());
                    }
                }
                for (int j = 0; j < array_sizecuts.size(); j++) {
                    String cuts_kind = request.getParameter("cuts_kind" + i + "_" + j);
                    String cuts_currency = request.getParameter("cuts_currency" + i + "_" + j);
                    String quantnow = formatDoubleforMysql(request.getParameter("quantnow" + i + "_" + j));
                    String totold = formatDoubleforMysql(request.getParameter("totold" + i + "_" + j));
                    String totnow = formatDoubleforMysql(request.getParameter("totnow" + i + "_" + j));
                    String sizecuts = request.getParameter("sizecuts" + i + "_" + j);
                    String quantold = formatDoubleforMysql(request.getParameter("quantold" + i + "_" + j));
                    String[] v2 = {cuts_currency, cuts_kind, sizecuts, quantnow, totnow, quantold, totold};
                    al_sicu.add(v2);
                }
            }
        }

        String ok = verifyAllQuantityITY(safefr, al_kifi, al_sicu);

        if (ok.equals("0")) {
            Db_Master db1 = new Db_Master();
            boolean es = db1.insert_IT_change(it);
            db1.closeDB();
            if (es) {
                for (int i = 0; i < array_list_oc_change.size(); i++) {
                    if (idopentillfrom.equals(array_list_oc_change.get(i)[0])) {
                        String currency = request.getParameter("currency" + i);
                        String kind = request.getParameter("kind" + i);
                        String s_quantold = formatDoubleforMysql(request.getParameter("s_quantold" + i));
                        String s_totold = formatDoubleforMysql(request.getParameter("s_totold" + i));
                        String s_quantnow = formatDoubleforMysql(request.getParameter("s_quantnow" + i));
                        String s_totnow = formatDoubleforMysql(request.getParameter("s_totnow" + i));
                        String[] v1 = {currency, kind, s_quantold, s_totold, s_quantnow, s_totnow};
                        al_kifi.add(v1);
                        if (s_quantnow != null) {
                            if (!s_quantnow.trim().equals("") && !s_quantnow.trim().equals("0") && !s_quantnow.trim().equals("0.00") && !s_quantnow.trim().equals("0,00")) {
                                if (verifyquantIT(safefr, s_quantold, s_quantnow)) {
                                    if (fd((s_totold)) >= fd((s_totnow))) {
                                        IT_change it2 = new IT_change();
                                        it2.setFiliale(filiale);
                                        it2.setCod(cod);
                                        it2.setValuta(currency);
                                        it2.setSupporto(kind);
                                        it2.setQuantita((s_quantnow));
                                        it2.setTotale((s_totnow));
                                        it2.setDt_it(oper);
                                        Db_Master db2 = new Db_Master();
                                        boolean es2 = db2.insert_IT_change_value(it2);
                                        db2.closeDB();
                                        if (es2) {

                                            Db_Master db8 = new Db_Master();
                                            Stock_report srF = db8.get_Stock_report(idopentillfrom, currency, kind, "CH", filiale, tillfrom);
                                            Stock_report srT = db8.get_Stock_report(idopentillto, currency, kind, "CH", filiale, tillto);
                                            db8.closeDB();

                                            String codsrF = filiale + generaId(47);
                                            srF.setCodtr(cod);
                                            srF.setCodice(codsrF);
                                            double newtotA = -fd((s_totnow));
                                            srF.setTotal(roundDoubleandFormat(newtotA, 2));
                                            srF.setSpread("1.0000");
                                            srF.setData(oper);
                                            if (safefr.equals("true")) {
                                                srF.setQuantity(roundDoubleandFormat(-fd((s_quantnow)), 0));
                                            } else {
                                                srF.setQuantity("0");
                                            }
                                            srF.setUser(user);

                                            String codsrT = filiale + generaId(47);
                                            srT.setCodtr(cod);
                                            srT.setCodice(codsrT);
                                            double newtotT = fd((s_totnow));
                                            srT.setTotal(roundDoubleandFormat(newtotT, 2));
                                            srT.setSpread("1.0000");
                                            srT.setData(oper);
                                            if (safeto.equals("true")) {
                                                srT.setQuantity(roundDoubleandFormat(fd((s_quantnow)), 0));
                                            } else {
                                                srT.setQuantity("0");
                                            }
                                            srT.setUser(user);

                                            Db_Master db9A = new Db_Master();
                                            db9A.insert_Stockreport(srF);
                                            db9A.insert_Stockreport(srT);
                                            db9A.closeDB();

                                            Real_oc_change from = new Real_oc_change();
                                            from.setFiliale(filiale);
                                            from.setCod_oc(idopentillfrom);
                                            from.setValuta(currency);
                                            from.setKind(kind);
                                            from.setData(oper);

                                            if (safefr.equals("true")) {
                                                from.setNum_kind_op(roundDoubleandFormat((fd(s_quantold) - fd((s_quantnow))), 0));
                                            } else {
                                                from.setNum_kind_op("0");
                                            }
                                            from.setValue_op(roundDoubleandFormat((fd(s_totold) - fd((s_totnow))), 2));

                                            Real_oc_change to = new Real_oc_change();
                                            to.setFiliale(filiale);
                                            to.setCod_oc(idopentillto);
                                            to.setValuta(currency);
                                            to.setKind(kind);
                                            to.setData(oper);
                                            Db_Master db3 = new Db_Master();
                                            if (safeto.equals("true")) {
                                                to.setNum_kind_op(roundDoubleandFormat((fd(db3.getField_real_oc_change(to, "num_kind_op", "0")) + fd((s_quantnow))), 0));
                                            } else {
                                                to.setNum_kind_op("0");
                                            }
                                            to.setValue_op(roundDoubleandFormat((fd(db3.getField_real_oc_change(to, "value_op", "0.00")) + fd((s_totnow))), 2));

                                            db3.update_real_oc_change(from);
                                            db3.update_real_oc_change(to);
                                            db3.closeDB();

                                            for (int j = 0; j < array_sizecuts.size(); j++) {
                                                String quantnow = formatDoubleforMysql(request.getParameter("quantnow" + i + "_" + j));
                                                String totold = formatDoubleforMysql(request.getParameter("totold" + i + "_" + j));
                                                String quantold = formatDoubleforMysql(request.getParameter("quantold" + i + "_" + j));
                                                String totnow = formatDoubleforMysql(request.getParameter("totnow" + i + "_" + j));
                                                String sizecuts = request.getParameter("sizecuts" + i + "_" + j);
                                                if (sizecuts != null) {
                                                    if (!quantnow.trim().equals("") && !quantnow.trim().equals("0") && !quantnow.trim().equals("0.00") && !quantnow.trim().equals("0,00")) {
                                                        if (verifyquantIT(safefr, quantold, quantnow)) {

//                                                            if (fd((totold)) >= fd((totnow))) {
                                                            IT_change it3 = new IT_change();
                                                            it3.setFiliale(filiale);
                                                            it3.setCod(cod);
                                                            it3.setValuta(currency);
                                                            it3.setSupporto(kind);
                                                            it3.setTaglio(sizecuts);
                                                            it3.setQuantita(quantnow);
                                                            it3.setTotale((totnow));
                                                            it3.setDt_it(oper);
                                                            Db_Master db4 = new Db_Master();
                                                            boolean es3 = db4.insert_IT_change_tg(it3);
                                                            db4.closeDB();
                                                            if (es3) {
                                                                if (safefr.equals("true")) {
                                                                    //update real time tagli
                                                                    Real_oc_change from_cuts = new Real_oc_change();
                                                                    from_cuts.setFiliale(filiale);
                                                                    from_cuts.setCod_oc(idopentillfrom);
                                                                    from_cuts.setValuta(currency);
                                                                    from_cuts.setKind(kind);
                                                                    from_cuts.setData(oper);
                                                                    from_cuts.setIp_taglio(sizecuts);

                                                                    from_cuts.setNum_kind_op(roundDoubleandFormat((fd(quantold) - fd((quantnow))), 0));
                                                                    from_cuts.setValue_op(roundDoubleandFormat((fd(totold) - fd((totnow))), 2));
                                                                    Db_Master db5 = new Db_Master();
                                                                    db5.update_real_oc_change_cuts(from_cuts);
                                                                    db5.closeDB();
                                                                }
                                                                if (safeto.equals("true")) {
                                                                    Real_oc_change to_cuts = new Real_oc_change();
                                                                    to_cuts.setFiliale(filiale);
                                                                    to_cuts.setCod_oc(idopentillto);
                                                                    to_cuts.setValuta(currency);
                                                                    to_cuts.setKind(kind);
                                                                    to_cuts.setData(oper);
                                                                    to_cuts.setIp_taglio(sizecuts);
                                                                    Db_Master db5 = new Db_Master();
                                                                    to_cuts.setNum_kind_op(roundDoubleandFormat((fd(db5.getField_real_oc_change_cuts(to_cuts, "ip_quantity", "0")) + fd((quantnow))), 0));
                                                                    to_cuts.setValue_op(roundDoubleandFormat((fd(db5.getField_real_oc_change_cuts(to_cuts, "ip_value", "0.00")) + fd((totnow))), 2));

                                                                    db5.update_real_oc_change_cuts(to_cuts);
                                                                    db5.closeDB();
                                                                }
                                                            }
//                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            } else {
                ok = "2IN"; //ko insert value
            }
        }
        Db_Master db6 = new Db_Master();
        db6.updateBlockedOperation(session.getAttribute("us_cod") + " | " + session.getAttribute("us_user"), "0");
        db6.closeDB();
        try {
            sleep(500);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();

        }

        if (ok.equals("0")) {
            delete_IT_change_temp(idopentillfrom, idopentillto, filiale);
            redirect(request, response, "it_change.jsp?esito=OK");
        } else {
            //Engine.delete_IT_change(cod, idopentillfrom, idopentillto, filiale);
            redirect(request, response, "it_change.jsp?search=sra1&idopentillfrom=" + idopentillfrom + "&idopentillto=" + idopentillto + "&tillfrom=" + tillfrom + "&tillto=" + tillto + "&esito=" + ok);
        }
    }

    private String verifyAllQuantityITY(String safefr, ArrayList<String[]> al_kifi, ArrayList<String[]> al_sicu) {
        boolean quant = false;
        for (int i = 0; i < al_kifi.size(); i++) {

            String[] v1 = al_kifi.get(i);
//            String[] v1 = {currency, kind, s_quantold, s_totold, s_quantnow, s_totnow};

            if (v1[4] != null) {
                if (!v1[4].trim().equals("") && !v1[4].trim().equals("0") && !v1[4].trim().equals("0.00") && !v1[4].trim().equals("0,00")) {
                    quant = true;
                    if (verifyquantIT(safefr, v1[2], v1[4])) {
                        if (fd((v1[3])) >= fd((v1[5]))) {
                            for (int j = 0; j < al_sicu.size(); j++) {
                                String v2[] = al_sicu.get(j);
                                //String[] v2 = {cuts_currency, cuts_kind, sizecuts, quantnow, totnow, quantold, totold};

                                if (v2[3] != null) {
                                    if (!v2[3].trim().equals("") && !v2[3].trim().equals("0")) {
                                        quant = true;
                                        if (verifyquantIT(safefr, (v2[5]), (v2[3]))) {
                                            if (verifyquantIT(safefr, (v2[6]), (v2[4]))) {

                                            } else {
                                                return "2T"; //total error size
                                            }
                                        } else {
                                            return "2Q"; //quantity error size
                                        }
                                    }
                                }
                            }
                        } else {
                            return "1T"; //total error
                        }
                    } else {
                        return "1Q"; //quantity error
                    }
                }
            }
        }

        if (!quant) {
            return "Q";
        }

        return "0";
    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void edit_ref(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//
//        Utility.printRequest(request);
//        if (true) {
//            return;
//        }
        boolean es = true;
        String cod_tr = request.getParameter("idtr");
        String enable = request.getParameter("status");
        if (null == enable) {
            enable = "0";
        } else {
            switch (enable) {
                case "on":
                    enable = "1";
                    break;
                default:
                    enable = "0";
                    break;
            }
        }

        String datetime = new DateTime().toString(patternnormdate_filter);
        Db_Master db = new Db_Master();
        String datadocumento = db.getNow();
        String pathtemp = db.getPath("temp");
        String model = db.getConf("path.refund.pdf");
        String localfig = db.get_local_currency()[0];
        Ch_transaction ch = db.query_transaction_ch(cod_tr);
        ArrayList<Branch> brl = db.list_branch();
        if (ch != null) {
            ArrayList<Ch_transaction_value> tr_value = db.query_transaction_value(cod_tr);
            Client cl = db.query_Client_transaction(cod_tr, ch.getCl_cod());

            if (enable.equals("1")) {
                //static
                String from = "CE";
                String idopentill_refund = "-";
                String cod_usaegetta = "-";
                String timestamp = new DateTime().toString(patternsqldate);
                String user_refund = "-";
                String dt_refund = "1901-01-01 00:00:00";
                boolean update = valueOf(request.getParameter("update"));

                //new
                String cod = request.getParameter("idref");
                String status = "0";

                String value = formatDoubleforMysql(request.getParameter("val"));
                String method = request.getParameter("method");
                if (null == method) {
                    method = "BO";
                } else {
                    switch (method) {
                        case "on":
                            method = "BR";
                            break;
                        default:
                            method = "BO";
                            break;
                    }
                }

                if (method.equals("BO")) {
                    HttpSession session = request.getSession();
                    String user = (String) session.getAttribute("us_cod");
                    if (user == null) {
                        user = "9999";
                    }
                    user_refund = user;
                    dt_refund = timestamp;
                    status = "1";
                }

                String type = request.getParameter("typeref");
                if (null == type) {
                    type = "PA";
                } else {
                    switch (type) {
                        case "on":
                            type = "CO";
                            break;
                        default:
                            type = "PA";
                            break;
                    }
                }

                if (type.equals("CO")) {
                    value = request.getParameter("completevalue");
                    //Chiedere value da rimborsare nei vari casi
                }

                Ch_transaction_refund ref = new Ch_transaction_refund(
                        cod, cod_tr, from, method, ch.getFiliale(),
                        type, value, cod_usaegetta, status, user_refund, dt_refund,
                        idopentill_refund, timestamp);

                es = db.edit_refund_trans(ref, update);

                String importo = "";
                if (ref.getType().equals("CO")) {
                    for (int i = 0; i < tr_value.size(); i++) {
                        importo = importo + formatMysqltoDisplay(Utility.sanitize(tr_value.get(i).getQuantita())) + " " + tr_value.get(i).getValuta() + ", ";
                    }
                    if (importo.length() > 0) {
                        importo = importo.trim();
                        importo = importo.substring(0, importo.length() - 1);
                    }
                } else if (ref.getType().equals("PA")) {
                    importo = formatMysqltoDisplay(Utility.sanitize(ref.getValue())) + " " + Utility.sanitize(localfig);
                }

                if (method.equals("BO")) {
                    String filename = new DateTime().toString("yyMMddHHmmssSSS") + cod_tr + "_macrefund.pdf";
                    String base64 = refund(pathtemp, decodeBase64(model), cl.getCognome() + " " + cl.getNome(),
                            importo, ch.getFiliale() + ch.getId(), formatStringtoStringDate(ch.getData(), patternsqldate, patternnormdate),
                            formatBankBranchReport(ch.getFiliale(), "BR", null, brl), datetime);
                    Ch_transaction_doc chd = new Ch_transaction_doc(generaId(50), cod_tr, "_macrefund", base64, filename, datadocumento, ch.getCl_cod(), "Y");
                    es = db.insert_transaction_doc_centrale(chd, ch.getFiliale());
                }
            }
            db.update_transaction_refund(cod_tr, enable, ch.getFiliale());
        }

        db.closeDB();
        if (es) {
            redirect(request, response, "transaction_ref.jsp?code=" + cod_tr + "&esito=ok");
        } else {
            redirect(request, response, "transaction_ref.jsp?code=" + cod_tr + "&esito=ko");
        }
    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void verify_sign(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String codtr = request.getParameter("codtr");
        String coddoc = request.getParameter("coddoc");
        Db_Master db = new Db_Master();
        String pathtemp = db.getPath("temp");

        Ch_transaction tr = db.query_transaction_ch(codtr);
        if (tr == null) {
            tr = db.query_transaction_ch_temp(codtr);
        }

        String codclient = tr.getCl_cod();

        //pathtemp = "F:\\com\\";
        db.closeDB();
        Outputf ou = verify_document(pathtemp, codtr, coddoc);
        String ok = "-1";
        if (ou != null) {
            if (ou.getEsito().equals("0")) {
                Ch_transaction_doc chd = new Ch_transaction_doc(ou.getCodice_documento(), codtr, coddoc, ou.getFirma(),
                        ou.getNomefile_firma(), ou.getData_firma(), codclient, "Y");
                boolean es = insert_transaction_doc(chd);
                if (es) {
                    ok = "0";
                }
            }
        }
        if (ok.equals("0") && ou != null) {
            String headerKey = "Content-Disposition";
            String headerValue = format("inline; filename=\"%s\"", new Object[]{ou.getNomefile_firma()});
            response.setHeader("Content-Type", "application/pdf");
            response.setHeader(headerKey, headerValue);
            try (OutputStream outStream = response.getOutputStream()) {
                outStream.write(decodeBase64(ou.getFirma().getBytes()));
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
    protected void verify_sign_nc(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String codtr = request.getParameter("codtr");
        String coddoc = request.getParameter("coddoc");
        Db_Master db = new Db_Master();
        String pathtemp = db.getPath("temp");
        //pathtemp = "F:\\com\\";
        db.closeDB();
        Outputf ou = verify_document(pathtemp, codtr, coddoc);
        String ok = "-1";
        if (ou != null) {
            if (ou.getEsito().equals("0")) {
                Ch_transaction_doc chd = new Ch_transaction_doc(ou.getCodice_documento(), codtr, coddoc, ou.getFirma(),
                        ou.getNomefile_firma(), ou.getData_firma(), "-", "Y");
                boolean es = insert_transaction_doc(chd);
                if (es) {
                    ok = "0";
                }
            }
        }
        if (ok.equals("0") && ou != null) {
            String headerKey = "Content-Disposition";
            String headerValue = format("inline; filename=\"%s\"", new Object[]{ou.getNomefile_firma()});
            response.setHeader("Content-Type", "application/pdf");
            response.setHeader(headerKey, headerValue);
            try (OutputStream outStream = response.getOutputStream()) {
                outStream.write(decodeBase64(ou.getFirma().getBytes()));
            }
        } else {
            redirect(request, response, "page_fnf.html");
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

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void insert_ref(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

//        Utility.printRequest(request);
//        if(true)return;
        HttpSession session = request.getSession();
        String user_refund = (String) session.getAttribute("us_cod");
        if (user_refund == null) {
            user_refund = "9999";
        }
        String idtr = request.getParameter("idtr");
        String idref = request.getParameter("idref");
        String value = request.getParameter("val");
        String idopentill = request.getParameter("idopentill");
        String till = request.getParameter("till");
        String type;
        boolean autonomo = ((String) request.getParameter("autonomo")).equals("SI");

        Db_Master db = new Db_Master();
        boolean dividi = db.get_national_office().getChangetype().equals("/");
        String codusagetta = "-";
        if (autonomo) {
            codusagetta = request.getParameter("codusagetta");
            type = request.getParameter("typeref");
            if (null == type) {
                type = "PA";
            } else {
                switch (type) {
                    case "on":
                        type = "CO";
                        break;
                    default:
                        type = "PA";
                        break;
                }
            }
            value = formatDoubleforMysql(value);
        } else {
            Ch_transaction_refund re = db.get_refund_trans(idtr);
            type = re.getType();
            value = re.getValue();
        }

        Ch_transaction tra = db.query_transaction_ch(idtr);
        ArrayList<Ch_transaction_value> list_row = db.query_transaction_value(idtr);
        ArrayList<String[]> array_list_oc_change = db.list_oc_change_real(idopentill);

        String dt_tr = db.getNow();
        String[] cur_default = db.get_local_currency();
        String err = "0";
        if (autonomo) {
            boolean codok = db.codici_sblocco_isEnabled(codusagetta);
            if (!codok) {
                err = "1";
            }
        }
        if (err.equals("0")) {
            //controllo quantità casse
            if (type != null) {
                if (type.equals("CO")) {
                    if (tra.getTipotr().equals("B")) {

                        // controllo valute che il cliente mi ha dato
                        boolean ok = true;
                        for (int k = 0; k < list_row.size(); k++) {
                            Ch_transaction_value chv = list_row.get(k);
                            if (!chv.getSupporto().equals("04")) {
                                double start = 0.00;
                                for (int i = 0; i < array_list_oc_change.size(); i++) {
                                    if (array_list_oc_change.get(i)[2].equals(chv.getValuta()) && array_list_oc_change.get(i)[1].equals(chv.getSupporto())) {
                                        start = fd(array_list_oc_change.get(i)[3]);
                                        break;
                                    }
                                }
                                if (start < fd(chv.getQuantita())) {
                                    ok = false;
                                    break;
                                }
                            }
                        }
                        if (!ok) {
                            err = "Q";
                        }

                    } else {
                        // controllo euro che il cliente mi ha dato
                        double dascalare = fd(tra.getPay());
                        boolean ok = true;
                        boolean found = false;
                        for (int i = 0; i < array_list_oc_change.size(); i++) {
                            if (array_list_oc_change.get(i)[1].equals("01") && cur_default[0].equals(array_list_oc_change.get(i)[2])) {
                                found = true;
                                if (dascalare > fd(array_list_oc_change.get(i)[3])) {
                                    ok = false;
                                }
                            }
                        }

                        if (!found) {
                            ok = false;
                        }
                        if (!ok) {
                            err = "Q";
                        }
                    }
                } else {
                    //verifico solo euro
                    boolean ok = true;
                    boolean found = false;
                    for (int i = 0; i < array_list_oc_change.size(); i++) {
                        String ch = array_list_oc_change.get(i)[0];
                        if (idopentill.equals(ch)) {
                            if (array_list_oc_change.get(i)[1].equals("01") && cur_default[0].equals(array_list_oc_change.get(i)[2])) {
                                found = true;
                                if (fd(value) > fd(array_list_oc_change.get(i)[3])) {
                                    ok = false;
                                }
                            }
                        }
                    }
                    if (!found) {
                        ok = false;
                    }
                    if (!ok) {
                        err = "Q";
                    }
                }

                if (err.equals("0")) {
                    if (type.equals("CO")) {
                        if (tra.getTipotr().equals("B")) {
                            double newtot = fd(tra.getPay());
                            insertStock_Pay(tra, cur_default[0], "01", newtot);
                            Db_Master db7 = new Db_Master();
                            Stock_report sr = db7.get_Stock_report(idopentill, cur_default[0], "01", "CH", filiale, till);
                            db7.closeDB();
                            //  AUMENTO EURO
                            String codsr = filiale + generaId(47);
                            sr.setCodtr(idtr);
                            sr.setCodice(codsr);
                            sr.setTotal(roundDoubleandFormat(newtot, 2));
                            sr.setSpread("1.0000");
                            sr.setData(dt_tr);
                            sr.setQuantity("0");
                            sr.setUser(user_refund);
                            Db_Master db9 = new Db_Master();
                            db9.insert_Stockreport(sr);
                            db9.closeDB();
                            Real_oc_change t1 = new Real_oc_change();
                            t1.setFiliale(filiale);
                            t1.setCod_oc(idopentill);
                            t1.setValuta(cur_default[0]);
                            t1.setKind("01");
                            t1.setData(dt_tr);
                            t1.setNum_kind_op("0");
                            Db_Master db9X = new Db_Master();
                            t1.setValue_op(roundDoubleandFormat((fd(db9X.getField_real_oc_change(t1, "value_op", "0.00")) + newtot), 2));
                            db9X.update_real_oc_change(t1);
                            db9X.closeDB();

                            for (int x = 0; x < list_row.size(); x++) {
                                Ch_transaction_value ctv = list_row.get(x);

                                //diminuisco stock valute da restituire
                                Db_Master dbS = new Db_Master();

                                ArrayList<Stock> al = dbS.list_stock(filiale, ctv.getSupporto(), ctv.getValuta());
                                ArrayList<String[]> damod = new ArrayList<>();
                                double quant_now = fd(ctv.getQuantita());
                                double quant_check = quant_now;
                                for (int i = 0; i < al.size() && quant_check > 0; i++) {
                                    double vq1 = fd(al.get(i).getTotal());
                                    if (vq1 >= quant_check) {
                                        String[] v = {al.get(i).getCodice(), "", "", al.get(i).getCodice(), roundDoubleandFormat(quant_check, 2), al.get(i).getRate(), "U",
                                            roundDoubleandFormat(vq1 - quant_check, 2)};
                                        damod.add(v);
                                        quant_check = 0.0;
                                    } else {
                                        String[] v = {al.get(i).getCodice(), "", "", al.get(i).getCodice(), roundDoubleandFormat(quant_check, 2), al.get(i).getRate(), "U", "0.00"};//07/11
                                        damod.add(v);
                                        quant_check = quant_check - vq1;
                                    }
                                }
                                dbS.updateStock(damod, false);
                                dbS.closeDB();

                                Db_Master db8A = new Db_Master();
                                Stock_report srA = db8A.get_Stock_report(idopentill, ctv.getValuta(), ctv.getSupporto(), "CH", filiale, till);
                                db8A.closeDB();

                                //  DIMINUISCO VALUTE
                                String codsrA = filiale + generaId(47);
                                srA.setCodtr(idtr);
                                srA.setCodice(codsrA);
                                double newtotA = -fd(ctv.getQuantita());

                                srA.setTotal(roundDoubleandFormat(newtotA, 2));
                                srA.setSpread("1.0000");
                                srA.setData(dt_tr);
                                srA.setQuantity("0");
                                srA.setUser(user_refund);
                                Db_Master db9A = new Db_Master();
                                db9A.insert_Stockreport(srA);
                                db9A.closeDB();

                                Db_Master db8 = new Db_Master();
                                Real_oc_change righe = new Real_oc_change();
                                righe.setFiliale(filiale);
                                righe.setCod_oc(idopentill);
                                righe.setValuta(ctv.getValuta());
                                righe.setKind(ctv.getSupporto());
                                righe.setData(dt_tr);
                                righe.setNum_kind_op("0");
                                righe.setValue_op(roundDoubleandFormat((fd(db8.getField_real_oc_change(righe, "value_op", "0.00")) - fd(ctv.getQuantita())), 2));
                                db8.update_real_oc_change(righe);
                                db8.closeDB();
                            }

                        } else {

                            //aumento valute che il cliente mi dà
                            for (int i = 0; i < list_row.size(); i++) {
                                Ch_transaction_value chv = list_row.get(i);

                                Stock st1 = new Stock();
                                st1.setCodice("ST" + generaId(48));
                                st1.setFiliale(tra.getFiliale());
                                st1.setTipo("CH");
                                st1.setTill(tra.getTill());
                                st1.setIdoperation(tra.getCod());
                                st1.setCodiceopenclose(tra.getId_open_till());
                                st1.setTipostock("CH");
                                st1.setCod_value(chv.getValuta());
                                st1.setKind(chv.getSupporto());
                                st1.setTotal(chv.getQuantita());
                                double oldrate = getControvaloreOFFSET(fd(chv.getQuantita()), (fd(chv.getTotal()) - fd(chv.getSpread())), dividi);
                                double oldcontrov = getControvalore(fd(chv.getQuantita()), oldrate, dividi);
                                st1.setRate(roundDoubleandFormat(oldrate, 8));
                                st1.setControval(roundDoubleandFormat(oldcontrov, 2));
                                st1.setUser(tra.getUser());
                                st1.setDate(tra.getData());
                                st1.setId_op(tra.getFiliale() + tra.getId());
                                Db_Master db1 = new Db_Master();
                                db1.insertStock(st1);
                                db1.closeDB();

                                Db_Master db8A = new Db_Master();
                                Stock_report srA = db8A.get_Stock_report(idopentill, chv.getValuta(), chv.getSupporto(), "CH", filiale, till);
                                db8A.closeDB();

                                //  AUMENTO INCASSI
                                String codsrA = filiale + generaId(47);
                                srA.setCodtr(idtr);
                                srA.setCodice(codsrA);
                                double newtotA = fd(chv.getQuantita());
                                srA.setTotal(roundDoubleandFormat(newtotA, 2));
                                srA.setSpread("1.0000");
                                srA.setData(dt_tr);
                                srA.setQuantity("0");
                                srA.setUser(user_refund);
                                Db_Master db9A = new Db_Master();
                                db9A.insert_Stockreport(srA);
                                db9A.closeDB();

                                Real_oc_change righe = new Real_oc_change();
                                righe.setFiliale(filiale);
                                righe.setCod_oc(idopentill);
                                righe.setValuta(chv.getValuta());
                                righe.setKind(chv.getSupporto());
                                righe.setData(dt_tr);
                                righe.setNum_kind_op("0");
                                Db_Master db10 = new Db_Master();
                                righe.setValue_op(roundDoubleandFormat((fd(db10.getField_real_oc_change(righe, "value_op", "0.00")) + fd(chv.getQuantita())), 2));

                                db10.update_real_oc_change(righe);
                                db10.closeDB();

                            }

                            //scalo euro da dare al cliente
                            double dascalare = fd(tra.getPay());

                            Db_Master db8 = new Db_Master();
                            Stock_report sr = db8.get_Stock_report(idopentill, cur_default[0], "01", "CH", tra.getFiliale(), till);
                            db8.closeDB();

                            //  DIMINUISCO EURO SR
                            String codsr = tra.getFiliale() + generaId(47);
                            sr.setCodtr(idtr);
                            sr.setCodice(codsr);
                            double newtot = -dascalare;
                            sr.setTotal(roundDoubleandFormat(newtot, 2));
                            sr.setSpread("1.0000");
                            sr.setData(dt_tr);
                            sr.setQuantity("0");
                            sr.setUser(user_refund);
                            Db_Master db9 = new Db_Master();
                            db9.insert_Stockreport(sr);
                            db9.closeDB();

                            //  DIMINUISCO EURO CASSA
                            Real_oc_change t1 = new Real_oc_change();
                            t1.setFiliale(tra.getFiliale());
                            t1.setCod_oc(idopentill);
                            t1.setValuta(cur_default[0]);
                            t1.setKind("01");
                            t1.setData(dt_tr);
                            t1.setNum_kind_op("0");
                            Db_Master db11 = new Db_Master();
                            t1.setValue_op(roundDoubleandFormat((fd(db11.getField_real_oc_change(t1, "value_op", "0.00")) - dascalare), 2));
                            db11.update_real_oc_change(t1);
                            db11.closeDB();

                            //  DIMINUISCO EURO STOCK
                            Db_Master dbS = new Db_Master();
                            ArrayList<Stock> al = dbS.list_stock(tra.getFiliale(), "01", cur_default[0]);
                            ArrayList<String[]> damod = new ArrayList<>();
                            double quant_now = dascalare;
                            double quant_check = quant_now;
                            for (int i = 0; i < al.size() && quant_check > 0; i++) {
                                double vq1 = fd(al.get(i).getTotal());
                                if (vq1 >= quant_check) {
                                    String[] v = {al.get(i).getCodice(), "", "", al.get(i).getCodice(), roundDoubleandFormat(quant_check, 2), al.get(i).getRate(), "U",
                                        roundDoubleandFormat(vq1 - quant_check, 2)};
                                    damod.add(v);
                                    quant_check = 0.0;
                                } else {
                                    String[] v = {al.get(i).getCodice(), "", "", al.get(i).getCodice(), roundDoubleandFormat(quant_check, 2), al.get(i).getRate(), "U", "0.00"};//07/11
                                    damod.add(v);
                                    quant_check = quant_check - vq1;
                                }
                            }
                            dbS.updateStock(damod, false);
                            dbS.closeDB();

                        }

                    } else {

                        Db_Master db8 = new Db_Master();
                        Stock_report sr = db8.get_Stock_report(idopentill, cur_default[0], "01", "CH", tra.getFiliale(), till);
                        db8.closeDB();

                        //  DIMINUISCO EURO SR
                        String codsr = tra.getFiliale() + generaId(47);
                        sr.setCodtr(idtr);
                        sr.setCodice(codsr);
                        double newtot = -fd(value);
                        sr.setTotal(roundDoubleandFormat(newtot, 2));
                        sr.setSpread("1.0000");
                        sr.setData(dt_tr);
                        sr.setQuantity("0");
                        sr.setUser(user_refund);
                        Db_Master db9 = new Db_Master();
                        db9.insert_Stockreport(sr);
                        db9.closeDB();

                        //  DIMINUISCO EURO CASSA
                        Real_oc_change t1 = new Real_oc_change();
                        t1.setFiliale(tra.getFiliale());
                        t1.setCod_oc(idopentill);
                        t1.setValuta(cur_default[0]);
                        t1.setKind("01");
                        t1.setData(dt_tr);
                        t1.setNum_kind_op("0");
                        Db_Master db11 = new Db_Master();
                        t1.setValue_op(roundDoubleandFormat((fd(db11.getField_real_oc_change(t1, "value_op", "0.00")) - fd(value)), 2));
                        db11.update_real_oc_change(t1);
                        db11.closeDB();

                        //  DIMINUISCO EURO STOCK
                        Db_Master dbS = new Db_Master();
                        ArrayList<Stock> al = dbS.list_stock(tra.getFiliale(), "01", cur_default[0]);
                        ArrayList<String[]> damod = new ArrayList<>();
                        double quant_now = fd(value);
                        double quant_check = quant_now;
                        for (int i = 0; i < al.size() && quant_check > 0; i++) {
                            double vq1 = fd(al.get(i).getTotal());
                            if (vq1 >= quant_check) {
                                String[] v = {al.get(i).getCodice(), "", "", al.get(i).getCodice(), roundDoubleandFormat(quant_check, 2), al.get(i).getRate(), "U",
                                    roundDoubleandFormat(vq1 - quant_check, 2)};
                                damod.add(v);
                                quant_check = 0.0;
                            } else {
                                String[] v = {al.get(i).getCodice(), "", "", al.get(i).getCodice(), roundDoubleandFormat(quant_check, 2), al.get(i).getRate(), "U", "0.00"};//07/11
                                damod.add(v);
                                quant_check = quant_check - vq1;
                            }
                        }
                        dbS.updateStock(damod, false);
                        dbS.closeDB();
                    }
                }
            }
        }

        if (err.equals("0")) {
            if (type != null) {
                if (type.equals("CO")) {
                    if (tra.getTipotr().equals("B")) {
                        value = "-" + tra.getPay();
                    } else {
                        value = tra.getPay();
                    }
                }
                if (autonomo) {
                    Ch_transaction_refund ref = new Ch_transaction_refund(idref, idtr, "FI", "BR", filiale, type, value, codusagetta, "1", user_refund, dt_tr, idopentill, dt_tr);
                    db.edit_refund_trans(ref, false);
                    db.update_transaction_refund(idtr, "1", filiale);
                    db.use_codice_sblocco(codusagetta, dt_tr, "00", user_refund, idtr);
                } else {
                    db.execute_transaction_refund(idref, value, idopentill, "-", dt_tr, user_refund, "1");
                }
            }

            // NON GENERARE NOTA DI CREDITO 08/02
//            if (!tra.getFa_number().equals("-") && !tra.getFa_number().equals("")) {
//                Db_Master dbc = new Db_Master();
//                String invoice_number = dbc.get_invoice_number(tra.getCod());
//                boolean es1 = dbc.insert_inv_list(invoice_number, tra.getCod(), "N", "1", dbc.getNow());
//                if (es1) {
//                    es1 = dbc.update_invoice_transaction(tra.getCod(), "ch_transaction", "cn_number", invoice_number);
//                }
//                dbc.closeDB();
//            }
            //
        }

        db.closeDB();

        if (err.equals("0")) {
            redirect(request, response, "transaction_ref_branch.jsp?code=" + idtr + "&esito=ok");
        } else {
            redirect(request, response, "transaction_ref_branch.jsp?code=" + idtr + "&esito=ko" + err);
        }
    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void conf_doc_tra(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = Utility.sanitize((String) request.getSession().getAttribute("us_cod"));
        if (username == null) {
            username = "9999";
        }
        Db_Master dbm = new Db_Master();

//        String pathtemp = "F:\\com\\";
        String pathtemp = Utility.sanitize(dbm.getPath("temp"));

        String codtr = Utility.sanitize(request.getParameter("codtr"));

        Ch_transaction tr1 = dbm.query_transaction_ch(codtr);
        if (tr1 == null) {
            tr1 = dbm.query_transaction_ch_temp(codtr);
        }

        String codclient = tr1.getCl_cod();
        String coddoc = Utility.sanitize(request.getParameter("coddoc"));
        String cod = generaId(50);
        String dateoper = new DateTime().toString(patternsqldate);
        String namefile = new DateTime().toString("yyMMddhhmmssSSS") + coddoc + ".pdf";
        String base64 = new Receipt().print_pdf_nodigital(pathtemp, coddoc, username, dateoper);
        String msg = "0";
        if (base64 != null) {
            Ch_transaction_doc chd = new Ch_transaction_doc(cod, codtr, coddoc, base64, namefile, dateoper, codclient, "N");
            boolean es = insert_transaction_doc(chd);
            if (!es) {
                msg = "2";
            }
        } else {
            msg = "1";
        }
        dbm.closeDB();

        if (msg.equals("0") && base64 != null) {
            String headerKey = "Content-Disposition";
            String sanitizednamefile = Utility.sanitizeFilename(namefile);
            String headerValue = format("inline; filename=\"%s\"", new Object[]{sanitizednamefile});
            response.setHeader("Content-Type", "application/pdf");
            response.setHeader(headerKey, headerValue);
            try (OutputStream outStream = response.getOutputStream()) {
                outStream.write(decodeBase64(base64.getBytes()));
            }
        } else {
            redirect(request, response, "transaction_rendiok.jsp?codtr=" + codtr + "&coddoc=" + coddoc);
        }

    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void upload_doc_tra(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Db_Master dbm = new Db_Master();
        String username = (String) request.getSession().getAttribute("us_cod");
        if (username == null) {
            username = "9999";
        }

        String base64 = null;

        String codtr = "";
        String coddoc = "";

        String mod = "false";

        String cod = generaId(50);
        String dateoper = new DateTime().toString(patternsqldate);
        String namefile = "";//

//        String pathtemp = "F:\\com\\";
        String pathtemp = dbm.getPath("temp");

        boolean isMultipart = isMultipartContent(request);
        String msg = "0";
        if (isMultipart) {
            try {
                FileItemFactory factory = new DiskFileItemFactory();
                ServletFileUpload upload = new ServletFileUpload(factory);
                List items = upload.parseRequest(request);
                Iterator iterator = items.iterator();
                while (iterator.hasNext()) {
                    FileItem item = (FileItem) iterator.next();
                    if (item.isFormField()) {
                        if (item.getFieldName().equals("codtr")) {
                            codtr = item.getString().trim();
                        }
                        if (item.getFieldName().equals("coddoc")) {
                            coddoc = item.getString().trim();
                        }
                        if (item.getFieldName().equals("mod")) {
                            mod = item.getString().trim();
                        }
                    } else {
                        String fileName = item.getName();
                        if (fileName != null) {
                            if (fileName.toLowerCase().endsWith(".pdf")) {

                                namefile = new DateTime().toString("yyMMddhhmmssSSS") + coddoc + ".pdf";
                                File pdf = new File(pathtemp + separator + namefile);
                                try {
                                    item.write(pdf);
                                } catch (Exception ex) {
                                    msg = "1";
                                    insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
                                    pdf = null;
                                }
                                if (pdf != null) {
                                    if (pdf.length() > 3145728) {
                                        insertTR("E", "System", new Exception().getStackTrace()[0].getMethodName() + ": " + " File troppo grande.");
                                        msg = "11";
                                        pdf = null;
                                    }
                                }

                                if (pdf != null) {
                                    base64 = new String(encodeBase64(readFileToByteArray(pdf)));
                                    pdf.delete();
                                }
                            } else {
                                msg = "1";
                                insertTR("E", "System", new Exception().getStackTrace()[0].getMethodName() + ": " + " File non concorde.");
                            }
                        }
                    }
                }
            } catch (FileUploadException ex) {
                insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
                msg = "1";
                base64 = null;
            }
        }

        if (msg.equals("0") && base64 != null) {
            String codcl = get_codclient(codtr);
            Ch_transaction_doc chd = new Ch_transaction_doc(cod, codtr, coddoc, base64, namefile, dateoper, codcl, "Y");
            if (mod.equals("true")) {
                boolean es = insert_transaction_doc_FILIALE(chd, codtr.substring(0, 3).trim());
                if (!es) {
                    msg = "2";
                } else {
                    String codice_mod = generaId(75);
                    String datemod = getNow();
                    Client clOR = query_Client_transaction(codtr, codcl);
                    clOR.setCodicemodifica(codice_mod);
                    clOR.setTipomodifica("D");
                    clOR.setUsermodifica(username);
                    clOR.setDatemodifica(datemod);
                    Db_Master db1 = new Db_Master();
                    es = db1.insert_kyc_modify_client(clOR, null, codtr);
                    db1.closeDB();
                    if (!es) {
                        msg = "2";
                    }
                }
            } else {
                boolean es = insert_transaction_doc(chd);
                if (!es) {
                    msg = "2";
                }
            }
        }
        dbm.closeDB();
        if (msg.equals("0") && base64 != null) {
            String headerKey = "Content-Disposition";
            String sanitizednamefile = Utility.sanitize(namefile);
            String headerValue = format("inline; filename=\"%s\"", new Object[]{sanitizednamefile});
            response.setHeader("Content-Type", "application/pdf");
            response.setHeader(headerKey, headerValue);
            try (OutputStream outStream = response.getOutputStream()) {
                outStream.write(decodeBase64(base64.getBytes()));
            }
        } else {
            redirect(request, response, "transaction_upldoc.jsp?codtr=" + codtr + "&coddoc=" + coddoc);
        }
    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void del_tr(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String codtr = Utility.sanitize(request.getParameter("codtr"));
        Db_Master dbm = new Db_Master();
        Ch_transaction tra = dbm.query_transaction_ch(codtr);
        if (tra == null) {
            tra = dbm.query_transaction_ch_temp(codtr);
        }
        boolean es;
        if (tra == null || tra.getDel_fg().equals("1")) {
            es = false;
        } else {
            es = dbm.delete_trans(codtr);
        }
        dbm.closeDB();
        if (es) {
            redirect(request, response, "transaction.jsp?esito=ok");
        } else {
//            String start = "transaction_ok_mod.jsp";
//            if (Constant.signoffline) {
//                start = "transaction_ok_mod_new.jsp";
//            }
            redirect(request, response, getLink_last(codtr) + "&esito=ko");
        }
    }

    private String createNC_PAYMAT(String user, Till t1, NC_causal nc2, String kind, String pos, String posnum, String loc_cur, String base64) {

        NC_transaction nct = new NC_transaction();
        String cod = "NC" + generaId(23);
        nct.setCod(cod);
        nct.setFiliale(filiale);
        nct.setUser(user);
        nct.setId_open_till(t1.getId_opcl());   //QUERY IDTILL APERTE DA USER
        nct.setTill(t1.getCod());               //QUERY CODTILL APERTE DA USER
        nct.setNote("");
        nct.setGruppo_nc(nc2.getGruppo_nc());
        nct.setCausale_nc(nc2.getCausale_nc());
        nct.setPrezzo(nc2.getIp_prezzo_nc());
        nct.setFg_tipo_transazione_nc(nc2.getFg_tipo_transazione_nc());
        nct.setSupporto(kind);
        nct.setPos(pos);
        nct.setPosnum(posnum);
        nct.setValuta(loc_cur);
        nct.setQuantita("1.00");
        nct.setTotal(nc2.getIp_prezzo_nc());
        nct.setCommissione("0.00");
        nct.setFg_inout("1"); //MONEY IN
        nct.setDel_dt("1901-01-01 00:00:00");
        nct.setDel_user("-");
        nct.setDel_motiv("-");
        nct.setDel_fg("0");

        if (base64 != null) {
            nct.setDocrico(base64);
        }

        Db_Master dbm = new Db_Master();
        String dateoper = dbm.getNow();
        boolean es = dbm.insert_NC_transaction(nct);
        dbm.closeDB();

        if (es) {
            if (nct.getSupporto().equals("06") || nct.getSupporto().equals("07") || nct.getSupporto().equals("08")) {     //creditcard
                Real_oc_pos rop = new Real_oc_pos();
                rop.setFiliale(nct.getFiliale());
                rop.setCod_oc(t1.getId_opcl());
                rop.setValuta(loc_cur);
                rop.setKind(nct.getSupporto());
                rop.setData(dateoper);
                rop.setCarta_credito(nct.getPos());
                Db_Master db1 = new Db_Master();
                rop.setIp_quantity(valueOf(parseIntR(db1.getField_real_oc_pos(rop, "ip_quantity", "0")) + 1));
                rop.setIp_value(roundDoubleandFormat((fd(db1.getField_real_oc_pos(rop, "ip_value", "0.00")) + fd(nct.getTotal())), 2));
                db1.update_real_oc_pos(rop);
                db1.closeDB();
            } else {
                Real_oc_change to = new Real_oc_change();
                to.setFiliale(nct.getFiliale());
                to.setCod_oc(t1.getId_opcl());
                to.setValuta(loc_cur);
                to.setKind(nct.getSupporto());
                to.setData(dateoper);
                to.setNum_kind_op("0");

                Db_Master db1 = new Db_Master();
                to.setValue_op(roundDoubleandFormat((fd(db1.getField_real_oc_change(to, "value_op", "0.00")) + fd(nct.getTotal())), 2));
                db1.update_real_oc_change(to);
                db1.closeDB();

                Db_Master db2 = new Db_Master();
                Stock_report sr = db2.get_Stock_report(t1.getId_opcl(), loc_cur, "01", "CH", nct.getFiliale(), t1.getCod());
                db2.closeDB();

                //  AUMENTO EURO
                String codsr = nct.getFiliale() + generaId(47);
                sr.setCodtr(cod);
                sr.setCodice(codsr);
                double newtot = fd(nct.getTotal());
                sr.setTotal(roundDoubleandFormat(newtot, 2));
                sr.setSpread("1.0000");
                sr.setData(dateoper);
                sr.setQuantity("0");
                sr.setUser(user);
                Db_Master db3 = new Db_Master();
                db3.insert_Stockreport(sr);
                db3.closeDB();

                //ADD EURO STOCK
                Stock st1 = new Stock();
                st1.setCodice("ST" + generaId(48));
                st1.setFiliale(nct.getFiliale());
                st1.setTipo("CH");
                st1.setTill(t1.getCod());
                st1.setIdoperation(cod);
                st1.setCodiceopenclose(t1.getId_opcl());
                st1.setTipostock("CH");
                st1.setCod_value(loc_cur);
                st1.setKind("01");
                st1.setTotal(nct.getTotal());
                st1.setRate("1.00000000");
                st1.setControval(nct.getTotal());
                st1.setUser(user);
                st1.setDate(dateoper);
                st1.setId_op(t1.getId_opcl());

                Db_Master db4 = new Db_Master();
                db4.insertStock(st1);
                db4.closeDB();
            }

        }
        return cod;
    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void exec_paymat(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

//        Utility.printRequest(request);
//        if(true)
//            return;
        String user = (String) request.getSession().getAttribute("us_cod");
        if (user == null) {
            user = "9999";
        }

        //user = "0011";
        Db_Master dbm = new Db_Master();
        String[] loc_cur = dbm.get_local_currency();
        String path = dbm.getPath("temp");
        //String path = "F:\\com\\";

//        Paymat_conf p_old = dbm.get_conf_paymat(false);//OLD
        Paymat_conf pc = dbm.get_conf_paymat(true);//NEW

        Till t1 = dbm.get_till_opened(user);
        dbm.closeDB();

        String paynew = request.getParameter("paynew");
        String bra = request.getParameter("bra").trim();
        String idbra = request.getParameter("idbra");
        String codtaglio = request.getParameter("codtaglio");
        String tipolo = request.getParameter("tipolo");
        String numb = request.getParameter("numb");
        //String desc = request.getParameter("desc");
        String desc = request.getParameter("desc");
        String tipoprodotto = request.getParameter("tipoprodotto");

        String kind = request.getParameter("kind_1");
        String pos = request.getParameter("pos_1");
        String posnum = request.getParameter("posnum");

        boolean pay_value = false;
        String err = "0";
        String msg = "";

        try {
            pay_value = valueOf(paynew);
        } catch (Exception e) {
            err = "1";
            msg = "Generic Error";
        }

        NC_causal nc2 = null;
        Db_Master dbc = new Db_Master(true);
        if (dbc.getC() != null) {
            String[] value_NC = dbc.get_temp_paymat(codtaglio);
            if (value_NC != null) {
                nc2 = dbc.get_nc_causal(value_NC[1]);
            }
            dbc.closeDB();
        }
        if (nc2 == null) {
            err = "1";
            msg = "No Change Causal not found, try again.";
        }

        if (t1 == null) {
            err = "1";
            msg = "No till opened.";
        }

        if (kind.equals("01")) {
            pos = "";
            posnum = "";
        }

        String cod = null;

        String idrich = "TESTMAC" + new DateTime().toString("yyyyMMddHHmmssSSS") + randomAlphanumeric(15).trim().toLowerCase();
        if (err.equals("0")) {
//            Till t0 = litill.get(0);
            if (pay_value) {
                Paymat_new pn = new Paymat_new();
                VerificaRicarica vr = pn.reserveRicaricaTelefonica(path, pc, idrich, idbra, pc.getValue_idterminale(), numb, codtaglio, "");
                if (vr.getResultCode().equals("0")) {
                    String idrich2 = "TESTMAC" + new DateTime().toString("yyyyMMddHHmmssSSS") + randomAlphanumeric(15).trim().toLowerCase();
                    Ticket tick = pn.confirmRicaricaTelefonica(path, pc, idrich2, idbra, idrich, pc.getValue_idterminale(), numb, codtaglio, "");
                    if (tick.getResultCode().equals("0")) {
                        String base64 = scontrino_paymat(path,
                                decodeBase64(getConf("path.pay.sc")),
                                new Scontrino_Pa(
                                        "MACCORP S.P.A.",
                                        tick.getDescrizioneProdotto().toUpperCase(),
                                        tick.getDataOraTransazione(),
                                        tick.getIdTerminale(),
                                        "",
                                        tick.getNumero_operazione(),
                                        tick.getId_autorizzazione(),
                                        tick.getIdTranPaymat(),
                                        tick.getImportoFacciale(),
                                        tick.getNumeroTelefonico(),
                                        tick.getNoteTransazione(),
                                        "MAC2.0", "HelpDesk Maccorp", "Powered by Cogetech S.p.A."));
                        cod = createNC_PAYMAT(user, t1, nc2, kind, pos, posnum, loc_cur[0], base64); //ADD NC 14/05

                    } else {
                        err = "1";
                        msg = "confirmRicaricaTelefonica: " + tick.getResultCode() + " - " + tick.getResultDesc();
                    }
                } else {
                    err = "1";
                    msg = "reserveRicaricaTelefonica: " + vr.getResultDesc();
                }
            } else {
                err = "1";
                msg = "Operation not found";
            }
        }

        if (err.equals("0")) {
            redirect(request, response, "nc_transaction_esito.jsp?esito=OK&cod=" + cod);
        } else {
            redirect(request, response, "es_confirmpaym.jsp?type=exec_paymat"
                    + "&paynew=" + paynew + "&bra=" + bra + "&idbra=" + idbra
                    + "&codtaglio=" + codtaglio + "&tipolo=" + tipolo
                    + "&desc=" + desc + "&tipoprodotto=" + tipoprodotto + "&esito=ko" + err + "&errmsg=" + msg);
        }

    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void coraexp(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String from = request.getParameter("from");
        String sendtype = request.getParameter("sendtype");
        String base64;
        if (sendtype.equals("0")) {
            try {
                Iterable<String> parameters = on("/").split(from);
                Iterator<String> it = parameters.iterator();
                String mese = it.next();
                String anno = it.next();
                from = anno + "-" + mese;
                Db_Master db = new Db_Master();
                base64 = Utility.sanitize(db.getCORA(from, sendtype));
                db.closeDB();
            } catch (Exception e) {
                base64 = null;
            }
        } else {
            Db_Master db = new Db_Master();
            base64 = Utility.sanitize(db.getCORA(from, sendtype));
            db.closeDB();
        }
        if (base64 != null) {
            String headerKey = "Content-Disposition";
            String sanitizedfrom = Utility.sanitize(from);
            String sanitizedsendtype = Utility.sanitize(sendtype);
            String headerValue = format("attachment; filename=\"%s\"", new Object[]{"CORA_" + sanitizedfrom + "_" + sanitizedsendtype + "_" + generaId(15) + ".zip"});
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
    protected void antiexp(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String from_ant = request.getParameter("from_ant");
        String to_ant = request.getParameter("to_ant");

        String sendtype = request.getParameter("sendtype");

        String data_from = formatStringtoStringDate_null(from_ant, patternnormdate_filter, patternsql);
        String data_to = formatStringtoStringDate_null(to_ant, patternnormdate_filter, patternsql);

        Db_Master db = new Db_Master();
        String path = db.getPath("temp");
        db.closeDB();

        String base64 = null;

        if (sendtype.equalsIgnoreCase("A")) {
            base64 = Utility.sanitize(anagrafica(path, data_from, data_to));
        } else if (sendtype.equalsIgnoreCase("R")) {
            base64 = Utility.sanitize(registrazione(path, data_from, data_to));
        }

        if (base64 != null) {
            String sanitizedsendtype = Utility.sanitize(sendtype);
            String name = new DateTime().toString("yyMMddhhmmssSSS") + "_ANTIR_" + sanitizedsendtype + ".xls";
            String headerKey = "Content-Disposition";
            String headerValue = format("attachment; filename=\"%s\"",
                    new Object[]{name});
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
    protected void esolv(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //Utility.printRequest(request);

        String from = Utility.sanitize(request.getParameter("from"));
        String branching = Utility.sanitize(request.getParameter("branch"));
        String data1 = Utility.sanitize(formatStringtoStringDate_null(from, patternnormdate_filter, patternsql));
        String anno = Utility.sanitize(formatStringtoStringDate_null(from, patternnormdate_filter, patternyear));
        if (branching.equals("---")) {
            branching = null;
        }
        Db_Master db = new Db_Master();
        String path = Utility.sanitizePath(db.getPath("temp"));
        db.closeDB();
        Db_Master db1 = new Db_Master();
        ArrayList<Branch> branch = db1.list_branch_completeAFTER311217();
        db1.closeDB();
        Db_Master db2 = new Db_Master();
        ArrayList<Ch_transaction> list_esolver_ch = db2.list_esolver_ch(data1, branch, branching);
        db2.closeDB();
        Db_Master db3 = new Db_Master();
        ArrayList<Ch_transaction_refund> list_esolver_refund = db3.list_esolver_refund(data1, branch, branching);
        db3.closeDB();
        Db_Master db4 = new Db_Master();
        ArrayList<NC_transaction> list_esolver_nc = db4.list_esolver_nc(data1, branch, branching);
        db4.closeDB();
        Db_Master db5 = new Db_Master();
        ArrayList<ET_change> list_esolver_et = db5.list_esolver_et(data1, branch, branching);
        db5.closeDB();
        Db_Master db6 = new Db_Master();
        ArrayList<Openclose> list_esolver_ocerr = db6.list_esolver_ocerr(data1, branch, branching);
        db6.closeDB();
        Db_Master db7 = new Db_Master();
        ArrayList<NC_causal> listcausal = db7.list_nc_causal_enabled();
        db7.closeDB();
        Db_Master db8 = new Db_Master();
        ArrayList<NC_category> listcategory = db8.list_nc_category_enabled();
        db8.closeDB();
        Db_Master db9 = new Db_Master();
        ArrayList<Users> listusers = db9.list_all_users();
        db9.closeDB();
        Db_Master db10 = new Db_Master();
        ArrayList<String[]> fatt_note = db10.get_fatt_note(data1, branch, branching);
        db10.closeDB();
        Db_Master db11 = new Db_Master();
        ArrayList<String[]> contabilita_codici = db11.contabilita();
        db11.closeDB();
        Db_Master db12 = new Db_Master();
        ArrayList<String[]> bank = db12.list_bank();
        db12.closeDB();
        Db_Master db13 = new Db_Master();
        ArrayList<String[]> country = db13.country();
        db13.closeDB();
        Db_Master db14 = new Db_Master();
        String valuta_locale = db14.get_local_currency()[0];
        db14.closeDB();
        Db_Master db15 = new Db_Master();
        boolean dividi = db15.get_national_office().getChangetype().equals("/");
        db15.closeDB();

        Db_Master db16 = new Db_Master();
        ArrayList<VATcode> listvat = db16.li_vat(null);
        CustomerKind ckres = db16.get_customerKind("002");
        db16.closeDB();

        Client_es es = new Client_es();
        Client_at at = new Client_at();

        ArrayList<File> list_f = new ArrayList<>();
        if (branching == null) {

            for (int i = 0; i < branch.size(); i++) {

                Branch b1 = branch.get(i);

                File base64_1 = es.FILEP1(path, from, anno, list_esolver_ch, list_esolver_nc, list_esolver_refund,
                        list_esolver_et, list_esolver_ocerr, listcategory, listcausal, listusers, b1, contabilita_codici, bank, valuta_locale, branch, dividi, ckres);
                File base64_2 = es.FILEP2(path, from, anno, list_esolver_ch, list_esolver_nc, listcategory, listcausal, b1,
                        contabilita_codici, bank, valuta_locale, branch, listvat, ckres);
                File base64_3 = es.FILEP3(path, from, anno, fatt_note, b1, contabilita_codici, bank, country, valuta_locale, branch);
                File base64_4 = es.FILEP4(path, from, anno, fatt_note, b1, contabilita_codici, bank, country, valuta_locale, branch);
                File base64_5 = es.FILEP5(path, from, anno, fatt_note, b1, contabilita_codici, bank, country, valuta_locale, branch);

                if (base64_1 != null) {
                    list_f.add(base64_1);
                }
                if (base64_2 != null) {
                    list_f.add(base64_2);
                }
                if (base64_3 != null) {
                    list_f.add(base64_3);
                }
                if (base64_4 != null) {
                    list_f.add(base64_4);
                }
                if (base64_5 != null) {
                    list_f.add(base64_5);
                }

                Db_ATL db17 = new Db_ATL();
                List<Atl_dati_fatture> daticorrispettivi_P2A = db17.atl_f1_P2(data1, b1.getCod());
                List<Atl_dati_fatture> daticollegati_P4A = db17.atl_f1_P4(data1, b1.getCod());
                List<Atl_dati_fatture> datifattura_P6A = db17.atl_f1_P6(data1, b1.getCod());
                List<Atl_dati_clienti> daticlienti_P7A = db17.atl_c1(data1, b1.getCod());
                db17.closeDB();
                //          //
                File base64_2A = at.FILEP2A(path, from, anno, b1, daticorrispettivi_P2A);
                File base64_4A = at.FILEP4A(path, from, anno, b1, daticollegati_P4A);
                File base64_6A = at.FILEP6A(path, from, b1, datifattura_P6A);
                File base64_7A = at.FILEP7A(path, from, b1, daticlienti_P7A);

                if (base64_2A != null) {
                    list_f.add(base64_2A);
                }
                if (base64_4A != null) {
                    list_f.add(base64_4A);
                }
                if (base64_6A != null) {
                    list_f.add(base64_6A);
                }
                if (base64_7A != null) {
                    list_f.add(base64_7A);
                }

            }
        } else {

            Db_Master db15A = new Db_Master();
            Branch b1 = db15A.get_branch(branching);
            db15A.closeDB();

            File base64_1 = es.FILEP1(path, from, anno, list_esolver_ch, list_esolver_nc, list_esolver_refund,
                    list_esolver_et, list_esolver_ocerr, listcategory, listcausal, listusers, b1, contabilita_codici, bank, valuta_locale, branch, dividi, ckres);
            File base64_2 = es.FILEP2(path, from, anno, list_esolver_ch, list_esolver_nc, listcategory, listcausal, b1,
                    contabilita_codici, bank, valuta_locale, branch, listvat, ckres);
            File base64_3 = es.FILEP3(path, from, anno, fatt_note, b1, contabilita_codici, bank, country, valuta_locale, branch);
            File base64_4 = es.FILEP4(path, from, anno, fatt_note, b1, contabilita_codici, bank, country, valuta_locale, branch);
            File base64_5 = es.FILEP5(path, from, anno, fatt_note, b1, contabilita_codici, bank, country, valuta_locale, branch);

            if (base64_1 != null) {
                list_f.add(base64_1);
            }
            if (base64_2 != null) {
                list_f.add(base64_2);
            }
            if (base64_3 != null) {
                list_f.add(base64_3);
            }
            if (base64_4 != null) {
                list_f.add(base64_4);
            }

            if (base64_5 != null) {
                list_f.add(base64_5);
            }

            Db_ATL db17 = new Db_ATL();
            List<Atl_dati_fatture> daticorrispettivi_P2A = db17.atl_f1_P2(data1, b1.getCod());
            List<Atl_dati_fatture> daticollegati_P4A = db17.atl_f1_P4(data1, b1.getCod());
            List<Atl_dati_fatture> datifattura_P6A = db17.atl_f1_P6(data1, b1.getCod());
            List<Atl_dati_clienti> daticlienti_P7A = db17.atl_c1(data1, b1.getCod());
            db17.closeDB();

            File base64_2A = at.FILEP2A(path, from, anno, b1, daticorrispettivi_P2A);
            File base64_4A = at.FILEP4A(path, from, anno, b1, daticollegati_P4A);
            File base64_6A = at.FILEP6A(path, from, b1, datifattura_P6A);
            File base64_7A = at.FILEP7A(path, from, b1, daticlienti_P7A);

            if (base64_2A != null) {
                list_f.add(base64_2A);
            }
            if (base64_4A != null) {
                list_f.add(base64_4A);
            }
            if (base64_6A != null) {
                list_f.add(base64_6A);
            }
            if (base64_7A != null) {
                list_f.add(base64_7A);
            }

            
        }

        if (list_f.isEmpty()) {
            redirect(request, response, "page_fnf.html");
        } else {
            String name;
            if (branching == null) {
                name = "ALL_" + replace(from, "/", "") + "_eSol_ZIP.zip";
            } else {
                name = branching + "_" + replace(from, "/", "") + "_eSol_ZIP.zip";
            }
            File zipout = new File(Utility.sanitizePath(path) + Utility.sanitize(name));
            boolean zipok = zipListFiles(list_f, zipout);
            if (zipok) {

                String base64 = getStringBase64(zipout);
                if (base64 != null) {
                    String headerKey = "Content-Disposition";
                    String sanitizedname = Utility.sanitize(name);
                    String headerValue = format("inline; filename=\"%s\"", new Object[]{sanitizedname});
                    response.setHeader("Content-Type", Utility.sanitize(probeContentType(zipout.toPath())));
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

    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void oamexp(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String from = Utility.sanitize(request.getParameter("from"));
        String tipoinvio = Utility.sanitize(request.getParameter("sendtype"));
        Iterable<String> parameters = on("/").split(from);
        Iterator<String> it = parameters.iterator();
        if (it.hasNext()) {
            String mese = it.next();
            String anno = it.next();
            Db_Master db = new Db_Master();
            String base64 = Utility.sanitize(db.getOAM(anno + "-" + mese, tipoinvio));
            db.closeDB();
            if (base64 != null) {
                String headerKey = "Content-Disposition";
                String sanitizedanno = Utility.sanitize(anno);
                String sanitizedmese = Utility.sanitize(mese);
                String headerValue = format("attachment; filename=\"%s\"", new Object[]{"OAM_" + sanitizedanno + sanitizedmese + "_" + generaId(15) + ".zip"});
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
    protected void monfil(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String branch = Utility.sanitize(request.getParameter("branch"));
        Db_Master dbm = new Db_Master();
        String path = Utility.sanitize(dbm.getPath("temp"));
        boolean es = uploadMonitor(path, branch, null);
        dbm.closeDB();
        if (es) {
            redirect(request, response, "fileinteg.jsp?esito=okfil");
        } else {
            redirect(request, response, "fileinteg.jsp?esito=kofil");
        }
    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void moncentr(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String branch = Utility.sanitize(request.getParameter("branch"));
        Db_Master dbm = new Db_Master();
        String path = Utility.sanitize(dbm.getPath("temp"));
        ArrayList<Branch> brl = null;
        if (branch.equals("---")) {
            brl = dbm.list_branch_enabled();
        }
        boolean es = uploadMonitor(path, branch, brl);
        dbm.closeDB();
        if (es) {
            redirect(request, response, "fileinteg.jsp?esito=okcen");
        } else {
            redirect(request, response, "fileinteg.jsp?esito=kocen");
        }

    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void list_paymat(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ArrayList<Taglio> total = new ArrayList<>();
        Db_Master dbc = new Db_Master(true);
        if (dbc.getC() == null) {
            dbc = new Db_Master();
        }
        ArrayList<String> litemp = dbc.list_temp_paymat();
        dbc.closeDB();

        Db_Master dbm = new Db_Master();
        String path = dbm.getPath("temp");

        Paymat_new pn = new Paymat_new();

        Paymat_conf pc = dbm.get_conf_paymat(true);

        if (pc != null) {
            String idrich = "TESTMAC" + new DateTime().toString("yyyyMMddHHmmssSSS") + randomAlphanumeric(15).trim().toLowerCase();
            ArrayList<Brand> infoBrandRicariche = pn.infoBrandRicariche(path, pc, idrich);
            if (!infoBrandRicariche.isEmpty()) { // Verifica se la lista non è vuota
                Brand esito = infoBrandRicariche.get(0);
                if (esito != null && "0".equals(esito.getResultCode())) { // Verifica che esito non sia nullo prima di chiamare getResultCode()
                    for (int i = 0; i < infoBrandRicariche.size(); i++) {
                        Brand b = infoBrandRicariche.get(i);
                        if (b != null && b.getCodiceBrand() != null) { // Verifica che b non sia nullo prima di accedere ai suoi attributi
                            ArrayList<Taglio> ta = pn.infoTagliRicariche(path, pc, randomUUID().toString(), b.getCodiceBrand(), b.getDescrizione());
                            for (int j = 0; j < ta.size(); j++) {
                                Taglio t1 = ta.get(j);
                                if (t1 != null && t1.getCodiceTaglio() != null) { // Verifica che t1 non sia nullo prima di accedere ai suoi attributi
                                    if (litemp.contains(t1.getCodiceTaglio())) {
                                        total.add(t1);
                                    }
                                }
                            }
                        }
                    }
                } else if (esito != null) { // Verifica che esito non sia nullo prima di accedere ai suoi attributi
                    insertTR("E", "System", new Exception().getStackTrace()[0].getMethodName() + ": " + esito.getResultCode() + " - " + esito.getResultDesc());
                }
            }
        }
        

        dbm.closeDB();

        sort(total);

        try (PrintWriter out = response.getWriter()) {
            String inizio = "{ \"aaData\": [";
            String fine = "] }";
            String valore = "";
            if (total.size() > 0) {
                for (int j = 0; j < total.size(); j++) {
                    Taglio t1 = total.get(j);

                    if (t1.getCodiceTaglio() != null) {
                        boolean paynew = true;
                        String codbra = t1.getBrandcode();
                        if (codbra == null) {
                            codbra = "";
                            paynew = false;
                        }
                        String tip = t1.getTipologia();
                        if (t1.getTipologia().equals("0")) {
                            tip = "";
                        }

////                    String az = "<button id='link_v_" + j + "' class='btn btn-sm blue-dark btn-outline btn-circle' onclick='return confnumb(this);'>"
////                            + "<i class='fa fa-pencil-square-o'></i> Execute</button>"
////                            + "<input type='hidden' id='link" + j + "' "
////                            + "value='Operazioni?type=exec_paymat&paynew=" + paynew + "&bra=" + t1.getBrand() + "&idbra="
////                            + codbra + "&codtaglio=" + t1.getCodiceTaglio() + "&tipolo=" + t1.getTipologia()
////                            + "&desc=" + t1.getDescrizione() + "&tipoprodotto=" + t1.getTipoProdotto() + "'/>"
////                            + "";
                        String az = "<form action='es_confirmpaym.jsp' method='post' target='_blank'> "
                                + "<input type='hidden' name='type' value='exec_paymat'/>"
                                + "<input type='hidden' name='paynew' value='" + paynew + "'/>"
                                + "<input type='hidden' name='bra' value='" + t1.getBrand() + "'/>"
                                + "<input type='hidden' name='idbra' value='" + codbra + "'/>"
                                + "<input type='hidden' name='codtaglio' value='" + t1.getCodiceTaglio() + "'/>"
                                + "<input type='hidden' name='tipolo' value='" + t1.getTipologia() + "'/>"
                                + "<input type='hidden' name='desc' value='" + t1.getDescrizione() + "'/>"
                                + "<input type='hidden' name='tipoprodotto' value='" + t1.getTipoProdotto() + "'/>"
                                + "<button type='submit' "
                                + "class='btn btn-sm blue-dark btn-outline btn-circle'>"
                                + "<i class='fa fa-pencil-square-o'></i> Execute</a></form>";

                        valore = valore + " [ \"" + t1.getBrand() + "\",\"" + t1.getDescrizione()
                                + "\",\"" + t1.getCodiceTaglio() + "\",\"" + az + "\"],";
                    }
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
    protected void changepos(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        Utility.printRequest(request);

        String user = (String) request.getSession().getAttribute("us_cod");
        if (user == null) {
            user = "9999";
        }

        String code = request.getParameter("code");
        String source = request.getParameter("source");
        Db_Master db0 = new Db_Master();
        boolean es = db0.cambia_dest_POS(code, source, user);
        db0.closeDB();

        if (es) {
            redirect(request, response, "changepos.jsp?code=" + code + "&esito=ok");
        } else {
            redirect(request, response, "changepos.jsp?code=" + code + "&esito=ko");
        }

    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void changerate(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

//        Utility.printRequest(request);
        String ok = "0";
        String user = (String) request.getSession().getAttribute("us_cod");
        if (user == null) {
            user = "9999";
        }

        String idval = request.getParameter("kind_1");
        String chra = formatDoubleforMysql(request.getParameter("chra"));

        Iterable<String> parameters = on(";").split(idval);
        Iterator<String> it = parameters.iterator();

        String code = it.next().trim();
        String kind = it.next().trim();
        String val = it.next().trim();

        String oldrate = null;

        Db_Master db = new Db_Master();
        boolean dividi = db.get_national_office().getChangetype().equals("/");
        ET_change etor = db.get_ET_change(code);
        if (etor != null) {
            ArrayList<ET_change> valori = db.get_ET_change_value(code);
            for (int x = 0; x < valori.size(); x++) {
                if (valori.get(x).getValuta().equals(val) && valori.get(x).getSupporto().equals(kind)) {
                    oldrate = valori.get(x).getIp_rate();
                    break;
                }
            }
        }
        db.closeDB();
        if (etor != null && oldrate != null) {

            if (fd(oldrate) != fd(chra)) {

                Db_Master db0 = new Db_Master();
                ArrayList<Stock> list_stock = db0.list_stock(code, "stock");
                ArrayList<Stock> list_stock_old = db0.list_stock(code, "stock_story");
                ArrayList<String[]> list_et = db0.stock_quantity(code, "ET", kind, val, "stock_story");
                ArrayList<String[]> list_se = db0.stock_quantity(code, "SE", kind, val, "stock_story");
                String[] his = {"HIR" + generaId(47), filiale, code, kind, val, oldrate,
                    chra, user, new DateTime().toString(patternsqldate)};
                ET_change etorig = db0.get_ET_change(code);
                db0.closeDB();

//                System.out.println("rc.so.servlets.Operazioni.changerate() "+list_stock.size());
//                
//                if(true)return;
//                
                if (etorig != null) {
                    Db_Master db1 = new Db_Master();
                    ArrayList<ET_change> va  = db1.get_ET_change_value(code);
                    for (int x = 0; x < va.size(); x++) {
                        if (va.get(x).getValuta().equals(val) && va.get(x).getSupporto().equals(kind)) {
                            double spreadnow;
                            double buyvalue = fd(va.get(x).getIp_buyvalue());
                            double totaleattuale = getControvalore(fd(va.get(x).getIp_quantity()), fd(chra), dividi);
                            if (etor.getFg_tofrom().equals("T")) {
                                spreadnow = totaleattuale - buyvalue;
                            } else {
                                spreadnow = buyvalue - totaleattuale;
                            }
                            db1.update_ET_changerate_Originale(va.get(x), chra,
                                    roundDoubleandFormat(totaleattuale, 2),
                                    roundDoubleandFormat(spreadnow, 2));
                        }
                    }
                    db1.closeDB();
                }

                //External originale
                Db_Master db1 = new Db_Master();
                if (etor.getFg_brba().equals("BR") && etor.getFg_tofrom().equals("F")) {
                    ArrayList<ET_change> valori_et_filialepartenza = db1.get_ET_change_value(etor.getCod_in());
                    for (int x = 0; x < valori_et_filialepartenza.size(); x++) {
                        if (valori_et_filialepartenza.get(x).getValuta().equals(val)
                                && valori_et_filialepartenza.get(x).getSupporto().equals(kind)) {

                            double buyvalue = fd(valori_et_filialepartenza.get(x).getIp_buyvalue());
                            double totaleattuale = getControvalore(fd(valori_et_filialepartenza.get(x).getIp_quantity()),
                                    fd(chra), dividi);
                            double spreadnow = totaleattuale - buyvalue;

                            db1.update_ET_changerate_Partenza(
                                    valori_et_filialepartenza.get(x),
                                    chra,
                                    roundDoubleandFormat(totaleattuale, 2),
                                    roundDoubleandFormat(spreadnow, 2));

                        }
                    }
                }
                //insert history
                db1.insert_history_changerate(his);
                db1.closeDB();

                //stock collegati
                Db_Master db10 = new Db_Master();
                for (int i = 0; i < list_stock.size(); i++) {
                    Stock s1 = list_stock.get(i);
                    if (s1.getCod_value().equals(val) && s1.getKind().equals(kind)) {
                        double total = fd(s1.getTotal());
                        double controv = getControvalore(total, fd(chra), dividi);
                        db10.update_stock_changerate(s1.getCodice(),
                                roundDoubleandFormat(controv, 2),
                                chra, "stock");
                    }
                }

                for (int i = 0; i < list_stock_old.size(); i++) {
                    Stock s1 = list_stock_old.get(i);
                    if (s1.getCod_value().equals(val) && s1.getKind().equals(kind)) {
                        double total = fd(s1.getTotal());
                        double controv = getControvalore(total, fd(chra), dividi);
                        db10.update_stock_changerate(s1.getCodice(),
                                roundDoubleandFormat(controv, 2),
                                chra, "stock_story");
                    }
                }

                db10.closeDB();
                //ext collegati
                Db_Master dba1 = new Db_Master();
                for (int i = 0; i < list_et.size(); i++) {
                    String codicedellostock = list_et.get(i)[0];
                    dba1.update_stockquantity_changerate(codicedellostock, chra);
                    ET_change et = dba1.get_ET_change(list_et.get(i)[1]);
                    if (et != null) {
                        ArrayList<ET_change> va  = dba1.get_ET_change_value(list_et.get(i)[1]);
                        for (int x = 0; x < va.size(); x++) {
                            if (va.get(x).getValuta().equals(val) && va.get(x).getSupporto().equals(kind)) {
                                double newbuyvalue = getControvalore(fd(va.get(x).getIp_quantity()), fd(chra), dividi);
                                double total = fd(va.get(x).getIp_total());
                                double spreadnow = total - newbuyvalue;
                                dba1.update_ET_changerate(va.get(x), chra, roundDoubleandFormat(newbuyvalue, 2), roundDoubleandFormat(spreadnow, 2));
                            }
                        }
                    }
                }
                dba1.closeDB();

                Db_Master dbaa = new Db_Master();
                //sell collegati
                for (int i = 0; i < list_se.size(); i++) {
                    String codicedellostock = list_se.get(i)[0];
                    dbaa.update_stockquantity_changerate(codicedellostock, chra);
                    Ch_transaction ch1 = dbaa.query_transaction_ch(list_se.get(i)[1]);
                    if (ch1 != null) {
                        ArrayList<Ch_transaction_value> va  = dbaa.query_transaction_value(list_se.get(i)[1]);
                        double spr1 = 0.00;
                        for (int x = 0; x < va.size(); x++) {
                            if (va.get(x).getValuta().equals(val) && va.get(x).getSupporto().equals(kind)) {
                                double newbuyvalue = getControvalore(fd(va.get(x).getQuantita()), fd(chra), dividi);
                                double total = fd(va.get(x).getTotal());
                                double spreadnow = total - newbuyvalue;
                                dbaa.update_SE_changerate_valori(va.get(x).getId(), roundDoubleandFormat(spreadnow, 2));
                                spr1 = spr1 + spreadnow;
                            } else {
                                spr1 = spr1 + fd(va.get(x).getSpread());
                            }
                        }
                        dbaa.update_SE_changerate(list_se.get(i)[1], roundDoubleandFormat(spr1, 2));
                    }
                }
                dbaa.closeDB();
            } else {
                ok = "1";
            }
        } else {
            ok = "2";
        }
        if (ok.equals("0")) {
            redirect(request, response, "error_rate.jsp?code=" + code + "&esito=ok");
        } else {
            redirect(request, response, "error_rate.jsp?code=" + code + "&esito=ko" + ok);
        }
    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void del_nl(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String user = (String) request.getSession().getAttribute("us_cod");
        String idnldel = request.getParameter("idnldel");
        Db_Master db1 = new Db_Master();
        boolean es = db1.delete_newsletters(idnldel, user);
        db1.closeDB();
        if (es) {
            insertTR("W", user, (String) request.getSession().getAttribute("us_fil") + " - ELIMINAZIONE NEWSLETTER");
        }
        redirect(request, response, "fancy_message.jsp?esito=" + es);
    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void set_external_ok(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String user = (String) request.getSession().getAttribute("us_cod");
        if (user == null) {
            user = "9999";
        }
        String codext = request.getParameter("codext");
        String ok = "0";
        ET_change et = get_ET_change(codext);
        if (et == null) {
            ok = "tt";
        } else {
            Db_Master db1 = new Db_Master();
            String dt1 = db1.getNow();
            boolean ex = db1.update_et_tobranch_ok(codext, et.getFiliale(), et.getCod_dest(), user, dt1);
            db1.closeDB();
            if (!ex) {
                ok = "tt1";
            }
        }

        redirect(request, response, "fancy_setextok.jsp?cod=" + codext + "&esito=" + ok);

    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void loy_assign_new(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String codcl = Utility.sanitize(request.getParameter("codcl"));
        String loya = Utility.sanitize(request.getParameter("loya"));
        boolean es = false;
        if (loya == null) {
            loya = "";
        }
        if (!loya.equals("")) {
            Db_Loy dbl = new Db_Loy();
            if (dbl.getC() != null) {
                String completo[] = dbl.getCodiceCompleto(loya);
                if (completo != null) {
                    dbl.set_stato_associaz_cliente(codcl, "0");
                    dbl.ins_mac_associate(codcl, completo[0]);
                    dbl.update_stato_codici(completo[0], "1");
                    es = true;
                }
                dbl.closeDB();
            }
        }
        if (es) {
            redirect(request, response, "fancy_message.jsp?esito=" + es);
        } else {
            redirect(request, response, "tb_loyalty_mod.jsp?codcl=" + codcl + "&esito=ko");
        }
    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void verificaRate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String tipotr = request.getParameter("tipotr");
        String kind = request.getParameter("kind");
        String cur = request.getParameter("cur");
        String rate = request.getParameter("rate");
        String controvcheck = request.getParameter("controvcheck");

        List<String> output = new ArrayList<>();

        Currency c1 = getCurrency(cur);

        if (c1 == null) {

        } else {
            boolean is_unitario = kind.equals("02") || kind.equals("04");
            if (is_CZ) {
                is_unitario = kind.equals("02");
            }
            if (is_unitario) {
                //ADD 1.000000
                output.add("0#1#Standard: 1" + decimal + "00000000#SI");
            } else {

                int numrate = 0;
                int numratemax = 0;
                double mxv = 0.00;

                ArrayList<String[]> array_rate_range = rate_range_enabled(kind);
                for (int i = 0; i < array_rate_range.size(); i++) {
                    if (fd(controvcheck) >= fd(array_rate_range.get(i)[1]) && fd(controvcheck) <= fd(array_rate_range.get(i)[2])) {
                        numrate = parseIntR(array_rate_range.get(i)[3]);
                    }
                    mxv = fd(array_rate_range.get(i)[2]);
                    numratemax = parseIntR(array_rate_range.get(i)[3]);
                }
                if (fd(controvcheck) > mxv) {
                    numrate = numratemax;
                }

                ArrayList<Currency> list = new ArrayList<>();
                list.add(c1);

                if (tipotr.equals("B")) {
                    Db_Master db1 = new Db_Master();
                    ArrayList<String[]> array_rate_currency = db1.rate_currency(list, true, false);
                    db1.closeDB();
                    for (int j = 0; j < array_rate_currency.size() && j < numrate; j++) {
                        if (array_rate_currency.get(j)[0].equals(rate)) {
                            output.add(j + "#" + array_rate_currency.get(j)[0] + "#" + array_rate_currency.get(j)[1] + "#SI");
                        } else {
                            output.add(j + "#" + array_rate_currency.get(j)[0] + "#" + array_rate_currency.get(j)[1] + "#NO");
                        }
                    }
                } else {
                    Db_Master db1 = new Db_Master();
                    ArrayList<String[]> array_rate_currency = db1.rate_currency(list, false, true);
                    db1.closeDB();
                    for (int j = 0; j < array_rate_currency.size() && j < numrate; j++) {
                        if (array_rate_currency.get(j)[0].equals(rate)) {
                            output.add(j + "#" + array_rate_currency.get(j)[0] + "#" + array_rate_currency.get(j)[1] + "#SI");
                        } else {
                            output.add(j + "#" + array_rate_currency.get(j)[0] + "#" + array_rate_currency.get(j)[1] + "#NO");
                        }
                    }
                }
            }
        }

        Gson gson = new Gson();
        ArrayList<String> JSONRequest = new ArrayList<>();

        if (output.isEmpty()) {
            JSONRequest.add(gson.toJson("false"));
        } else {
            JSONRequest.add(gson.toJson("true"));
            output.forEach(re -> {
                JSONRequest.add(gson.toJson(re));
            });
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
    protected void logintangerine(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain");
        String username = Utility.sanitize(request.getSession().getAttribute("us_user").toString());
        try (PrintWriter pw = response.getWriter()) {
            pw.println(login_TA(Utility.sanitize(username), Utility.sanitize(filiale)));
        }
    }

    protected void upload_doc_tra_ref(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String base64 = null;
        String codtr = "";
        String coddoc = "";
        String cod = generaId(50);
        String dateoper = new DateTime().toString(patternsqldate);
        String namefile = "";//
//        String pathtemp = "F:\\com\\";
        Db_Master dbm = new Db_Master();
        String pathtemp = dbm.getPath("temp");
        dbm.closeDB();

        boolean isMultipart = isMultipartContent(request);
        String msg = "0";
        if (isMultipart) {
            try {
                FileItemFactory factory = new DiskFileItemFactory();
                ServletFileUpload upload = new ServletFileUpload(factory);
                List items = upload.parseRequest(request);
                Iterator iterator = items.iterator();
                while (iterator.hasNext()) {
                    FileItem item = (FileItem) iterator.next();
                    if (item.isFormField()) {
                        if (item.getFieldName().equals("codtr")) {
                            codtr = item.getString().trim();
                        }
                        if (item.getFieldName().equals("coddoc")) {
                            coddoc = item.getString().trim();
                        }
                    } else {
                        String fileName = item.getName();
                        if (fileName != null) {
                            if (fileName.toLowerCase().endsWith(".pdf")) {

                                namefile = new DateTime().toString("yyMMddhhmmssSSS") + coddoc + ".pdf";
                                File pdf = new File(pathtemp + separator + namefile);
                                try {
                                    item.write(pdf);
                                } catch (Exception ex) {
                                    msg = "1";
                                    insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
                                    pdf = null;
                                }
                                if (pdf != null) {
                                    if (pdf.length() > 3145728) {
                                        insertTR("E", "System", new Exception().getStackTrace()[0].getMethodName() + ": " + " File troppo grande.");
                                        msg = "11";
                                        pdf = null;
                                    }
                                }

                                if (pdf != null) {
                                    base64 = new String(encodeBase64(readFileToByteArray(pdf)));
                                    pdf.delete();
                                }
                            } else {
                                msg = "1";
                                insertTR("E", "System", new Exception().getStackTrace()[0].getMethodName() + ": " + "File non concorde.");
                            }
                        }
                    }
                }
            } catch (FileUploadException ex) {
                insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
                msg = "1";
                base64 = null;
            }
        }

        if (msg.equals("0") && base64 != null) {
            Ch_transaction_doc chd = new Ch_transaction_doc(cod, codtr, coddoc, base64, namefile, dateoper, get_codclient(codtr), "Y");
            boolean es = insert_transaction_doc(chd);
            if (!es) {
                msg = "2";
            }
        }

        if (msg.equals("0") && base64 != null) {
            redirect(request, response, "transaction_ref_branch.jsp?code=" + codtr + "&esito=ok");
        } else {
            redirect(request, response, "transaction_ref_branch.jsp?code=" + codtr + "&esito=ko");
        }
    }

    /**
     *
     * @param request
     * @param response
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("text/html;charset=UTF-8");
        try {
            String link_value = verifySession(request);
            if (link_value != null) {
                redirect(request, response, link_value);
            } else {
                String type = request.getParameter("type");
                switch (type) {
                    case "nc_tr":
                        insertTR("W", (String) request.getSession().getAttribute("us_cod"), (String) request.getSession().getAttribute("us_fil") + " - TRANSAZIONE NO CHANGE");
                        transaction_NC(request, response);
                        break;
                    case "del_itr":
                        insertTR("W", (String) request.getSession().getAttribute("us_cod"), (String) request.getSession().getAttribute("us_fil") + " - ELIMINAZIONE INTERNAL TRANSFER");
                        del_internal_transfer(request, response);
                        break;
                    case "del_etr":
                        insertTR("W", (String) request.getSession().getAttribute("us_cod"), (String) request.getSession().getAttribute("us_fil") + " - ELIMINAZIONE EXTERNAL TRANSFER");
                        del_external_transfer(request, response);
                        break;
                    case "del_tr_ch":
                        insertTR("W", (String) request.getSession().getAttribute("us_cod"), (String) request.getSession().getAttribute("us_fil") + " - ELIMINAZIONE TRANSAZIONE CHANGE");
                        del_transaction_Ch(request, response);
                        break;
                    case "del_tr_noch":
                        insertTR("W", (String) request.getSession().getAttribute("us_cod"), (String) request.getSession().getAttribute("us_fil") + " - ELIMINAZIONE TRANSAZIONE NO CHANGE");
                        delete_transaction_noch(request, response);
                        break;
                    case "upload_nl":
                        insertTR("W", (String) request.getSession().getAttribute("us_cod"), (String) request.getSession().getAttribute("us_fil") + " - CARICAMENTO NEWSLETTER");
                        uploadNL(request, response);
                        break;
                    case "unlock_it_et":
                        insertTR("W", (String) request.getSession().getAttribute("us_cod"), (String) request.getSession().getAttribute("us_fil") + " - SBLOCCO OPERAZIONI PENDING");
                        unlockBlockedOperation(request, response);
                        break;
                    case "it_change":
                        insertTR("W", (String) request.getSession().getAttribute("us_cod"), (String) request.getSession().getAttribute("us_fil") + " - INTERNAL TRANSFER CHANGE");
                        it_change(request, response);
                        break;
                    case "it_nochange":
                        insertTR("W", (String) request.getSession().getAttribute("us_cod"), (String) request.getSession().getAttribute("us_fil") + " - INTERNAL TRANSFER NO CHANGE");
                        it_nochange(request, response);
                        break;
                    case "et_change":
                        insertTR("W", (String) request.getSession().getAttribute("us_cod"), (String) request.getSession().getAttribute("us_fil") + " - EXTERNAL TRANSFER CHANGE");
                        et_change(request, response);
                        break;
                    case "et_nochange":
                        insertTR("W", (String) request.getSession().getAttribute("us_cod"), (String) request.getSession().getAttribute("us_fil") + " - EXTERNAL TRANSFER NO CHANGE");
                        et_nochange(request, response);
                        break;
                    case "upload_cur01":
                        insertTR("W", (String) request.getSession().getAttribute("us_cod"), (String) request.getSession().getAttribute("us_fil") + " - UPLOAD TASSI CAMBIO");
                        upload_cur01(request, response);
                        break;
                    case "oc_close":
                        insertTR("W", (String) request.getSession().getAttribute("us_cod"), (String) request.getSession().getAttribute("us_fil") + " - CLOSE CASSA");
                        oc_close(request, response);
                        break;
                    case "oc_open":
                        insertTR("W", (String) request.getSession().getAttribute("us_cod"), (String) request.getSession().getAttribute("us_fil") + " - VERIFICA CF");
                        oc_open(request, response);
                        break;
                    case "verificaRate":
                        verificaRate(request, response);
                        break;
                    case "edit_ref":
                        insertTR("W", (String) request.getSession().getAttribute("us_cod"), (String) request.getSession().getAttribute("us_fil") + " - EDIT RIMBORSO");
                        edit_ref(request, response);
                        break;
                    case "insert_ref":
                        insertTR("W", (String) request.getSession().getAttribute("us_cod"), (String) request.getSession().getAttribute("us_fil") + " - INSERT RIMBORSO");
                        insert_ref(request, response);
                        break;
                    case "verify_sign":
                        verify_sign(request, response);
                        break;
                    case "verify_sign_nc":
                        verify_sign_nc(request, response);
                        break;
                    case "upload_doc_tra":
                        insertTR("W", (String) request.getSession().getAttribute("us_cod"), (String) request.getSession().getAttribute("us_fil") + " - UPLOAD DOC TRANSACTION");
                        upload_doc_tra(request, response);
                        break;
                    case "conf_doc_tra":
                        insertTR("W", (String) request.getSession().getAttribute("us_cod"), (String) request.getSession().getAttribute("us_fil") + " - TRANSAZIONE CHANGE BUY");
                        conf_doc_tra(request, response);
                        break;
                    case "del_tr":
                        insertTR("W", (String) request.getSession().getAttribute("us_cod"), (String) request.getSession().getAttribute("us_fil") + " - TRANSAZIONE NON CONFERMATA");
                        del_tr(request, response);
                        break;
                    case "list_paymat":
                        list_paymat(request, response);
                        break;
                    case "exec_paymat":
                        exec_paymat(request, response);
                        break;
                    case "oamexp":
                        oamexp(request, response);
                        break;
                    case "esolv":
                        esolv(request, response);
                        break;
                    case "coraexp":
                        coraexp(request, response);
                        break;
                    case "monfil":
                        monfil(request, response);
                        break;
                    case "moncentr":
                        moncentr(request, response);
                        break;
                    case "changerate":
                        changerate(request, response);
                        break;
                    case "antiexp":
                        antiexp(request, response);
                        break;
                    case "changepos":
                        changepos(request, response);
                        break;
                    case "set_external_ok":
                        insertTR("W", (String) request.getSession().getAttribute("us_cod"), (String) request.getSession().getAttribute("us_fil") + " - SET STATUS EXTERNAL OK");
                        set_external_ok(request, response);
                        break;
                    case "verificaeurotill":
                        verificaeurotill(request, response);
                        break;
                    case "del_nl":
                        del_nl(request, response);
                        break;
                    case "loy_assign_new":
                        loy_assign_new(request, response);
                        break;
                    case "logintangerine":
                        logintangerine(request, response);
                        break;
                    case "upload_doc_tra_ref":
                        upload_doc_tra_ref(request, response);
                        break;
                    case "upload_cur02":
                        insertTR("W", (String) request.getSession().getAttribute("us_cod"),
                                (String) request.getSession().getAttribute("us_fil") + " - UPLOAD SPREAD EXCEL");
                        upload_cur02(request, response);
                        break;
                    case "sendmailclient":
                        insertTR("W", (String) request.getSession().getAttribute("us_cod"),
                                (String) request.getSession().getAttribute("us_fil") + " - SEND EMAIL CLIENT SPRRAD EXCEL");
                        sendmailclient(request, response);
                        break;
                    default:
                        break;
                }
            }
        } catch (Exception ex) {
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
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
