package rc.so.entity;

import rc.so.db.Db_Master;
import static rc.so.util.Engine.getNC_category;
import static rc.so.util.Engine.getNC_causal;
import static rc.so.util.Utility.removeDuplicatesAL;
import java.util.ArrayList;

/**
 *
 * @author rcosco
 */
public class Ch_transaction {

    String credccard_number;
    String cod;
    String id;
    String filiale;
    String tipotr;
    String user;
    String till;
    String data;
    String tipocliente;
    String id_open_till;
    String pay;
    String total;
    String fix;
    String com;
    String round;
    String commission;
    String spread_total;
    String note;
    String agency;
    String agency_cod;
    String localfigures;
    String pos;
    String intbook;
    String intbook_type;

    String intbook_1_tf;
    String intbook_1_mod;
    String intbook_1_val;

    String intbook_2_tf;
    String intbook_2_mod;
    String intbook_2_val;

    String intbook_3_tf;
    String intbook_3_mod;
    String intbook_3_val;

    String intbook_4_tf;
    String intbook_4_mod;
    String intbook_4_val;

    String intbook_5_tf;
    String intbook_5_mod;
    String intbook_5_val;

    String bb;
    String refund;

    String fa_number, cn_number;

    /**
     *
     * @return
     */
    public String getCredccard_number() {
        return credccard_number;
    }

    /**
     *
     * @param credccard_number
     */
    public void setCredccard_number(String credccard_number) {
        this.credccard_number = credccard_number;
    }

    /**
     *
     * @param bb_sb
     * @return
     */
    public static String formatSTATUS_BBSB(String bb_sb) {
        if (null == bb_sb) {
            return "NO";
        } else {
            switch (bb_sb) {
                case "Y":
                    return "YES";
                case "F":
                    return "FREE";
                default:
                    break;
            }
        }
        return "NO";
    }

    /**
     *
     * @param bb
     * @return
     */
    public static String formatBB(String bb) {
        if (bb.equals("1") || bb.equals("2")) {
            return "BB";
        }
        return "";
    }

    /**
     *
     * @param res
     * @return
     */
    public static String format_BB_SB(Ch_transaction res) {
        if (!res.getBb().equals("0")) {
            if (res.getTipotr().equals("B")) {
                switch (res.getBb()) {
                    case "1":
                        return "<span class='font-green-jungle ital'>&nbsp;&nbsp;<b>BB</b></span>";
                    case "3":
                        return "<span class='font-red ital'>&nbsp;&nbsp;<b>SB</b></span>";
                    case "4":
                        return "<span class='font-green-jungle ital'>&nbsp;&nbsp;<b>SB</b></span>";
                    default:
                        break;
                }
            } else {
                switch (res.getBb()) {
                    case "1":
                        return "<span class='font-red ital'>&nbsp;&nbsp;<b>BB</b></span>";
                    case "2":
                        return "<span class='font-green-jungle ital'>&nbsp;&nbsp;<b>BB</b></span>";
                    case "3":
                        return "<span class='font-green-jungle ital'>&nbsp;&nbsp;<b>SB</b></span>";
                    default:
                        break;
                }
            }
        }
        return "";
    }

    /**
     *
     * @param del
     * @param bb_testata
     * @param fatnum
     * @param supporto
     * @param bb_riga
     * @param modify
     * @return
     */
    public static String formatTilltr(String del, String bb_testata, String fatnum, String supporto, String bb_riga, boolean modify, Db_Master dbconn,
            String statusref, String codtr) {
        String start;
        if (fatnum.trim().equals("-")) {
            start = "C ";
        } else {
            start = "F ";
        }

        if (bb_testata.equals("1") || bb_testata.equals("2")) {
            if (bb_riga.equals("F") || bb_riga.equals("Y")) {
                start += "BB ";
            }
        } else if (bb_testata.equals("3") || bb_testata.equals("4")) {
            if (bb_riga.equals("F") || bb_riga.equals("Y")) {
                start += "SB ";
            }
        }

        if (supporto.equals("04")) {
            start += "CA ";
        }

        if (modify) {
            start += "M ";
        }

        if (del.equals("1")) {
            start += "D";
        } else {
            if (statusref.equals("1")) {
                Ch_transaction_refund ref = dbconn.get_refund_trans(codtr);
                if (ref != null) {
                    if (ref.getStatus().equals("1")) {
                        start += "R";
                    }
                }
            }
        }

        return start;
    }

    /**
     *
     * @param del
     * @param bb1
     * @param fatnum
     * @param supporto
     * @return
     */
    public static String formatTilltr(String del, String bb1, String fatnum, String supporto) {
        String start;

        if (fatnum.trim().equals("-")) {
            start = "C ";
        } else {
            start = "F ";
        }

        if (bb1.equals("1") || bb1.equals("2") || bb1.equals("Y")) {
            start += "BB ";
        }

        if (supporto.equals("04")) {
            start += "CA ";
        }

        if (del.equals("1")) {

            start += "D";

//            if (start.equals("")) {
//                 start += "D";
//            } else {
//                 start += "BB D";
//            }
        }

        return start;
    }

    /**
     *
     * @param tipotr
     * @param internetbooking
     * @param rimborso
     * @param cau1
     * @param cau2
     * @param cau3
     * @param listcat
     * @param listcaus
     * @return
     */
    public static String formatType_new(String tipotr, String internetbooking,
            String rimborso, String cau1, String cau2, String cau3,
            ArrayList<NC_category> listcat, ArrayList<NC_causal> listcaus) {
        if (tipotr != null) {
            if (tipotr.equals("B")) {
                if (internetbooking.equals("1")) {
                    return "BUY - ACQUISTO ON-LINE";
                } else {
                    return "BUY";
                }
            }
            if (tipotr.equals("S")) {
                if (internetbooking.equals("1")) {
                    return "SELL - VENDITA ON-LINE";
                } else if (rimborso.equals("0") || rimborso.equals("-")) {
                    return "SELL - VENDITA A SPORTELLO";
                } else if (rimborso.equals("1")) {
                    ArrayList<String> caus = new ArrayList<>();
                    if (!cau1.equals("-")) {
                        caus.add(getNC_causal(listcaus, cau1, null).getGruppo_nc());
                    }
                    if (!cau2.equals("-")) {
                        caus.add(getNC_causal(listcaus, cau2, null).getGruppo_nc());
                    }
                    if (!cau3.equals("-")) {
                        caus.add(getNC_causal(listcaus, cau3, null).getGruppo_nc());
                    }
                    removeDuplicatesAL(caus);

                    String out = "";
                    for (int c = 0; c < caus.size(); c++) {
                        out = out + getNC_category(listcat, caus.get(c)).getDe_gruppo_nc().toUpperCase() + " ";
                    }

                    return "SELL - RIMBORSO IVA : " + out.trim();
                }
            }
        }
        return "-";
    }

    /**
     *
     * @param tipotr
     * @return
     */
    public static String formatType(String tipotr) {
        if (tipotr != null) {
            if (tipotr.equals("B")) {
                return "Buy";
            }
            if (tipotr.equals("S")) {
                return "Sell";
            }
        }
        return "-";
    }

    /**
     *
     * @param tipotr
     * @return
     */
    public String formatType_CZ(String tipotr) {
        if (tipotr != null) {
            if (tipotr.equals("B")) {
                return "NAKUPUJEME / WE BUY";
            }
            if (tipotr.equals("S")) {
                return "PRODAVAME / WE SELL";
            }
        }
        return "-";
    }

    /**
     *
     * @param tipotr
     * @return
     */
    public String formatType_pdf(String tipotr) {
        if (tipotr != null) {
            if (tipotr.equals("B")) {
                return "Acquisto / Buy";
            }
            if (tipotr.equals("S")) {
                return "Vendita / Sell";
            }
        }
        return "-";
    }

    String heavy_pepI;

    /**
     *
     * @param del_fg
     * @return
     */
    public String formatStatus(String del_fg) {
        if (del_fg != null) {
            if (del_fg.equals("0")) {
                return "<div class='font-green-jungle'>Active <i class='fa fa-check'></i></div>";
            }
            if (del_fg.equals("1")) {
                return "<div class='font-red'>Deleted <i class='fa fa-remove'></i></div>";
            }
        }
        return "-";
    }

    /**
     *
     * @param del_fg
     * @return
     */
    public String formatStatus_cru(String del_fg) {
        if (del_fg != null) {
            if (del_fg.equals("0")) {
                return "Active";
            }
            if (del_fg.equals("1")) {
                return "Deleted";
            }
        }
        return "-";
    }

//    public String setId_db() {
//        Db_Master db = new Db_Master();
//        int value = db.getlastId_nc_trans();
//        db.closeDB();
//        if (value > -1) {
//            value++;
//            return Utility.fillLeftInt(value, 15, "0");
//        }
//        return "ERROR";
//    }
    String intbook_mac;
    String intbook_cli;

    /**
     *
     * @return
     */
    public String getBl_status() {
        return this.bl_status;
    }

    String cl_cf;

    /**
     *
     * @param bl_status
     */
    public void setBl_status(String bl_status) {
        this.bl_status = bl_status;
    }

    String cl_cod;

    /**
     *
     * @return
     */
    public String getBl_motiv() {
        return this.bl_motiv;
    }

    String del_fg;
    String del_dt;

    /**
     *
     * @param bl_motiv
     */
    public void setBl_motiv(String bl_motiv) {
        this.bl_motiv = bl_motiv;
    }

    String del_user;

    /**
     *
     * @return
     */
    public String getCod() {
        return this.cod;
    }

    String del_motiv;
    String bl_status;
    String bl_motiv;

    /**
     *
     * @param cod
     */
    public void setCod(String cod) {
        this.cod = cod;
    }

    /**
     *
     * @return
     */
    public String getId() {
        return this.id;
    }

    /**
     *
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     *
     * @return
     */
    public String getFiliale() {
        return this.filiale;
    }

    /**
     *
     * @param filiale
     */
    public void setFiliale(String filiale) {
        this.filiale = filiale;
    }

    /**
     *
     * @return
     */
    public String getTipotr() {
        return this.tipotr;
    }

    /**
     *
     * @param tipotr
     */
    public void setTipotr(String tipotr) {
        this.tipotr = tipotr;
    }

    /**
     *
     * @return
     */
    public String getUser() {
        return this.user;
    }

    /**
     *
     * @param user
     */
    public void setUser(String user) {
        this.user = user;
    }

    /**
     *
     * @return
     */
    public String getTill() {
        return this.till;
    }

    /**
     *
     * @param till
     */
    public void setTill(String till) {
        this.till = till;
    }

    /**
     *
     * @return
     */
    public String getData() {
        return this.data;
    }

    /**
     *
     * @param data
     */
    public void setData(String data) {
        this.data = data;
    }

    /**
     *
     * @return
     */
    public String getTipocliente() {
        return this.tipocliente;
    }

    /**
     *
     * @param tipocliente
     */
    public void setTipocliente(String tipocliente) {
        this.tipocliente = tipocliente;
    }

    /**
     *
     * @return
     */
    public String getId_open_till() {
        return this.id_open_till;
    }

    /**
     *
     * @param id_open_till
     */
    public void setId_open_till(String id_open_till) {
        this.id_open_till = id_open_till;
    }

    /**
     *
     * @return
     */
    public String getPay() {
        return this.pay;
    }

    /**
     *
     * @param pay
     */
    public void setPay(String pay) {
        this.pay = pay;
    }

    /**
     *
     * @return
     */
    public String getTotal() {
        return this.total;
    }

    /**
     *
     * @param total
     */
    public void setTotal(String total) {
        this.total = total;
    }

    /**
     *
     * @return
     */
    public String getFix() {
        return this.fix;
    }

    /**
     *
     * @param fix
     */
    public void setFix(String fix) {
        this.fix = fix;
    }

    /**
     *
     * @return
     */
    public String getCom() {
        return this.com;
    }

    /**
     *
     * @param com
     */
    public void setCom(String com) {
        this.com = com;
    }

    /**
     *
     * @return
     */
    public String getRound() {
        return this.round;
    }

    /**
     *
     * @param round
     */
    public void setRound(String round) {
        this.round = round;
    }

    /**
     *
     * @return
     */
    public String getCommission() {
        return this.commission;
    }

    /**
     *
     * @param commission
     */
    public void setCommission(String commission) {
        this.commission = commission;
    }

    /**
     *
     * @return
     */
    public String getSpread_total() {
        return this.spread_total;
    }

    /**
     *
     * @param spread_total
     */
    public void setSpread_total(String spread_total) {
        this.spread_total = spread_total;
    }

    /**
     *
     * @return
     */
    public String getNote() {
        return this.note;
    }

    /**
     *
     * @param note
     */
    public void setNote(String note) {
        this.note = note;
    }

    /**
     *
     * @return
     */
    public String getAgency() {
        return this.agency;
    }

    /**
     *
     * @param agency
     */
    public void setAgency(String agency) {
        this.agency = agency;
    }

    /**
     *
     * @return
     */
    public String getAgency_cod() {
        return this.agency_cod;
    }

    /**
     *
     * @param agency_cod
     */
    public void setAgency_cod(String agency_cod) {
        this.agency_cod = agency_cod;
    }

    /**
     *
     * @return
     */
    public String getLocalfigures() {
        return this.localfigures;
    }

    /**
     *
     * @param localfigures
     */
    public void setLocalfigures(String localfigures) {
        this.localfigures = localfigures;
    }

    /**
     *
     * @return
     */
    public String getPos() {
        return this.pos;
    }

    /**
     *
     * @param pos
     */
    public void setPos(String pos) {
        this.pos = pos;
    }

    /**
     *
     * @return
     */
    public String getIntbook() {
        return this.intbook;
    }

    /**
     *
     * @param intbook
     */
    public void setIntbook(String intbook) {
        this.intbook = intbook;
    }

    /**
     *
     * @return
     */
    public String getIntbook_type() {
        return this.intbook_type;
    }

    /**
     *
     * @param intbook_type
     */
    public void setIntbook_type(String intbook_type) {
        this.intbook_type = intbook_type;
    }

    /**
     *
     * @return
     */
    public String getIntbook_1_tf() {
        return this.intbook_1_tf;
    }

    /**
     *
     * @param intbook_1_tf
     */
    public void setIntbook_1_tf(String intbook_1_tf) {
        this.intbook_1_tf = intbook_1_tf;
    }

    /**
     *
     * @return
     */
    public String getIntbook_2_val() {
        return this.intbook_2_val;
    }

    /**
     *
     * @param intbook_2_val
     */
    public void setIntbook_2_val(String intbook_2_val) {
        this.intbook_2_val = intbook_2_val;
    }

    /**
     *
     * @return
     */
    public String getIntbook_1_mod() {
        return this.intbook_1_mod;
    }

    /**
     *
     * @param intbook_1_mod
     */
    public void setIntbook_1_mod(String intbook_1_mod) {
        this.intbook_1_mod = intbook_1_mod;
    }

    /**
     *
     * @return
     */
    public String getIntbook_1_val() {
        return this.intbook_1_val;
    }

    /**
     *
     * @param intbook_1_val
     */
    public void setIntbook_1_val(String intbook_1_val) {
        this.intbook_1_val = intbook_1_val;
    }

    /**
     *
     * @return
     */
    public String getIntbook_2_tf() {
        return this.intbook_2_tf;
    }

    /**
     *
     * @param intbook_2_tf
     */
    public void setIntbook_2_tf(String intbook_2_tf) {
        this.intbook_2_tf = intbook_2_tf;
    }

    /**
     *
     * @return
     */
    public String getIntbook_2_mod() {
        return this.intbook_2_mod;
    }

    /**
     *
     * @param intbook_2_mod
     */
    public void setIntbook_2_mod(String intbook_2_mod) {
        this.intbook_2_mod = intbook_2_mod;
    }

    /**
     *
     * @return
     */
    public String getIntbook_3_tf() {
        return this.intbook_3_tf;
    }

    /**
     *
     * @param intbook_3_tf
     */
    public void setIntbook_3_tf(String intbook_3_tf) {
        this.intbook_3_tf = intbook_3_tf;
    }

    /**
     *
     * @return
     */
    public String getIntbook_3_mod() {
        return this.intbook_3_mod;
    }

    /**
     *
     * @param intbook_3_mod
     */
    public void setIntbook_3_mod(String intbook_3_mod) {
        this.intbook_3_mod = intbook_3_mod;
    }

    /**
     *
     * @return
     */
    public String getIntbook_3_val() {
        return this.intbook_3_val;
    }

    /**
     *
     * @param intbook_3_val
     */
    public void setIntbook_3_val(String intbook_3_val) {
        this.intbook_3_val = intbook_3_val;
    }

    /**
     *
     * @return
     */
    public String getIntbook_mac() {
        return this.intbook_mac;
    }

    /**
     *
     * @param intbook_mac
     */
    public void setIntbook_mac(String intbook_mac) {
        this.intbook_mac = intbook_mac;
    }

    /**
     *
     * @return
     */
    public String getIntbook_cli() {
        return this.intbook_cli;
    }

    /**
     *
     * @param intbook_cli
     */
    public void setIntbook_cli(String intbook_cli) {
        this.intbook_cli = intbook_cli;
    }

    /**
     *
     * @return
     */
    public String getCl_cf() {
        return this.cl_cf;
    }

    /**
     *
     * @param cl_cf
     */
    public void setCl_cf(String cl_cf) {
        this.cl_cf = cl_cf;
    }

    /**
     *
     * @return
     */
    public String getCl_cod() {
        return this.cl_cod;
    }

    /**
     *
     * @param cl_cod
     */
    public void setCl_cod(String cl_cod) {
        this.cl_cod = cl_cod;
    }

    /**
     *
     * @return
     */
    public String getDel_fg() {
        return this.del_fg;
    }

    /**
     *
     * @param del_fg
     */
    public void setDel_fg(String del_fg) {
        this.del_fg = del_fg;
    }

    /**
     *
     * @return
     */
    public String getDel_dt() {
        return this.del_dt;
    }

    /**
     *
     * @param del_dt
     */
    public void setDel_dt(String del_dt) {
        this.del_dt = del_dt;
    }

    /**
     *
     * @return
     */
    public String getDel_user() {
        return this.del_user;
    }

    /**
     *
     * @param del_user
     */
    public void setDel_user(String del_user) {
        this.del_user = del_user;
    }

    /**
     *
     * @return
     */
    public String getDel_motiv() {
        return this.del_motiv;
    }

    /**
     *
     * @param del_motiv
     */
    public void setDel_motiv(String del_motiv) {
        this.del_motiv = del_motiv;
    }

    /**
     *
     * @return
     */
    public String getHeavy_pepI() {
        return heavy_pepI;
    }

    /**
     *
     * @param heavy_pepI
     */
    public void setHeavy_pepI(String heavy_pepI) {
        this.heavy_pepI = heavy_pepI;
    }

    /**
     *
     * @return
     */
    public String getBb() {
        return bb;
    }

    /**
     *
     * @param bb
     */
    public void setBb(String bb) {
        this.bb = bb;
    }

    /**
     *
     * @return
     */
    public String getRefund() {
        return refund;
    }

    /**
     *
     * @param refund
     */
    public void setRefund(String refund) {
        this.refund = refund;
    }

    /**
     *
     * @return
     */
    public String getFa_number() {
        return fa_number;
    }

    /**
     *
     * @param fa_number
     */
    public void setFa_number(String fa_number) {
        this.fa_number = fa_number;
    }

    /**
     *
     * @return
     */
    public String getCn_number() {
        return cn_number;
    }

    /**
     *
     * @param cn_number
     */
    public void setCn_number(String cn_number) {
        this.cn_number = cn_number;
    }

}
