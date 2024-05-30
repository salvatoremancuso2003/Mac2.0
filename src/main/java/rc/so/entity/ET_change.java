package rc.so.entity;

import static rc.so.util.Utility.formatAL;
import java.util.ArrayList;

/**
 *
 * @author rcosco
 */
public class ET_change implements Comparable<ET_change> {

    String cod, id, filiale, user, till_from, fg_tofrom, fg_brba, cod_dest,
            idopen_from, dt_it, fg_annullato, del_dt, del_user, del_motiv,
            note, ip_oneri, filiale_in, id_in, cod_in;

    String valuta, supporto, ip_stock, ip_quantity, ip_rate, ip_total, ip_buyvalue, ip_spread;

    String ip_taglio;

    String nc_causal, type;

    String fg_stato, tr_dt, tr_user, tr_motiv;

    String auto;

    /**
     *
     * @param ty
     * @return
     */
    public static String typechangeno(String ty) {
        if (ty.equals("CH")) {
            return "Change";
        } else {
            return "No Change";
        }
    }

    /**
     *
     * @param fg_tofrom
     * @param fg_brba
     * @return
     */
    public String format_tofrom_brba(String fg_tofrom, String fg_brba) {
        if (fg_tofrom.equals("T")) {
            if (fg_brba.equals("BR")) {
                return "TO BRANCH";
            } else if (fg_brba.equals("BA")) {
                return "TO BANK";
            }
        } else if (fg_tofrom.equals("F")) {
            if (fg_brba.equals("BR")) {
                return "FROM BRANCH";
            } else if (fg_brba.equals("BA")) {
                return "FROM BANK";
            }
        }
        return "";
    }

    /**
     *
     * @param fg_tofrom
     * @param fg_brba
     * @param coddest
     * @param array_credit_card
     * @return
     */
    public static String format_tofrom_brba(String fg_tofrom, String fg_brba, String coddest, ArrayList<String[]> array_credit_card) {

        if (fg_tofrom.equals("T")) {
            if (fg_brba.equals("BR")) {
                return "TO BRANCH";
            } else if (fg_brba.equals("BA")) {

                for (int i = 0; i < array_credit_card.size(); i++) {
                    if (array_credit_card.get(i)[0].equals(coddest)) {
                        return "TO POS/Bank Account";
                    }
                }

                return "TO BANK";

            }
        } else if (fg_tofrom.equals("F")) {
            if (fg_brba.equals("BR")) {
                return "FROM BRANCH";
            } else if (fg_brba.equals("BA")) {
                return "FROM BANK";
            }
        }
        return "";
    }

    /**
     *
     * @param fg_tofrom
     * @param fg_brba
     * @param coddest
     * @param array_credit_card
     * @param array_bank
     * @return
     */
    public static String format_tofrom_brba_new(String fg_tofrom, String fg_brba, String coddest, ArrayList<String[]> array_credit_card,
             ArrayList<String[]> array_bank) {

        if (fg_tofrom.equals("T")) {
            if (fg_brba.equals("BR")) {
                return "TO BRANCH";
            } else if (fg_brba.equals("BA")) {
                for (int i = 0; i < array_credit_card.size(); i++) {
                    if (array_credit_card.get(i)[0].equals(coddest)) {
                        return "TO POS/Bank Account - " + coddest + ": " + array_credit_card.get(i)[1];
                    }
                }
                return "TO BANK - " + coddest + ": " + formatAL(coddest, array_bank, 1);
            }
        } else if (fg_tofrom.equals("F")) {
            if (fg_brba.equals("BR")) {
                return "FROM BRANCH";
            } else if (fg_brba.equals("BA")) {
                return "FROM BANK - " + coddest + ": " + formatAL(coddest, array_bank, 1);
            }
        }
        return "";
    }

    /**
     *
     * @param fg_brba
     * @return
     */
    public String format_tofrom(String fg_brba) {
        if (fg_brba.equals("T")) {
            return "TO";
        } else if (fg_brba.equals("F")) {
            return "FROM";
        }
        return "";
    }

    /**
     *
     * @param fg_annullato
     * @return
     */
    public String formatStatus(String fg_annullato) {
        if (fg_annullato != null) {
            if (fg_annullato.equals("0")) {
                return "<div class='font-green-jungle'>Active <i class='fa fa-check'></i></div>";
            }
            if (fg_annullato.equals("1")) {
                return "<div class='font-red'>Deleted <i class='fa fa-remove'></i></div>";
            }
        }
        return "-";
    }

    /**
     *
     * @param fg_annullato
     * @return
     */
    public String formatStatus_cru(String fg_annullato) {
        if (fg_annullato != null) {
            if (fg_annullato.equals("0")) {
                return "Active ";
            }
            if (fg_annullato.equals("1")) {
                return "Deleted ";
            }
        }
        return "-";
    }

    /**
     *
     * @return
     */
    public String getAuto() {
        return auto;
    }

    /**
     *
     * @param auto
     */
    public void setAuto(String auto) {
        this.auto = auto;
    }

    /**
     *
     * @return
     */
    public String getType() {
        return type;
    }

    /**
     *
     * @param type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     *
     * @return
     */
    public String getNc_causal() {
        return nc_causal;
    }

    /**
     *
     * @param nc_causal
     */
    public void setNc_causal(String nc_causal) {
        this.nc_causal = nc_causal;
    }

    /**
     *
     * @return
     */
    public String getCod_in() {
        return cod_in;
    }

    /**
     *
     * @param cod_in
     */
    public void setCod_in(String cod_in) {
        this.cod_in = cod_in;
    }

    /**
     *
     * @return
     */
    public String getFg_stato() {
        return fg_stato;
    }

    /**
     *
     * @param fg_stato
     */
    public void setFg_stato(String fg_stato) {
        this.fg_stato = fg_stato;
    }

    /**
     *
     * @return
     */
    public String getTr_dt() {
        return tr_dt;
    }

    /**
     *
     * @param tr_dt
     */
    public void setTr_dt(String tr_dt) {
        this.tr_dt = tr_dt;
    }

    /**
     *
     * @return
     */
    public String getTr_user() {
        return tr_user;
    }

    /**
     *
     * @param tr_user
     */
    public void setTr_user(String tr_user) {
        this.tr_user = tr_user;
    }

    /**
     *
     * @return
     */
    public String getTr_motiv() {
        return tr_motiv;
    }

    /**
     *
     * @param tr_motiv
     */
    public void setTr_motiv(String tr_motiv) {
        this.tr_motiv = tr_motiv;
    }

    /**
     *
     * @return
     */
    public String getIp_taglio() {
        return ip_taglio;
    }

    /**
     *
     * @param ip_taglio
     */
    public void setIp_taglio(String ip_taglio) {
        this.ip_taglio = ip_taglio;
    }

    /**
     *
     * @return
     */
    public String getValuta() {
        return valuta;
    }

    /**
     *
     * @param valuta
     */
    public void setValuta(String valuta) {
        this.valuta = valuta;
    }

    /**
     *
     * @return
     */
    public String getSupporto() {
        return supporto;
    }

    /**
     *
     * @param supporto
     */
    public void setSupporto(String supporto) {
        this.supporto = supporto;
    }

    /**
     *
     * @return
     */
    public String getIp_stock() {
        return ip_stock;
    }

    /**
     *
     * @param ip_stock
     */
    public void setIp_stock(String ip_stock) {
        this.ip_stock = ip_stock;
    }

    /**
     *
     * @return
     */
    public String getIp_quantity() {
        return ip_quantity;
    }

    /**
     *
     * @param ip_quantity
     */
    public void setIp_quantity(String ip_quantity) {
        this.ip_quantity = ip_quantity;
    }

    /**
     *
     * @return
     */
    public String getIp_rate() {
        return ip_rate;
    }

    /**
     *
     * @param ip_rate
     */
    public void setIp_rate(String ip_rate) {
        this.ip_rate = ip_rate;
    }

    /**
     *
     * @return
     */
    public String getIp_total() {
        return ip_total;
    }

    /**
     *
     * @param ip_total
     */
    public void setIp_total(String ip_total) {
        this.ip_total = ip_total;
    }

    /**
     *
     * @return
     */
    public String getIp_buyvalue() {
        return ip_buyvalue;
    }

    /**
     *
     * @param ip_buyvalue
     */
    public void setIp_buyvalue(String ip_buyvalue) {
        this.ip_buyvalue = ip_buyvalue;
    }

    /**
     *
     * @return
     */
    public String getIp_spread() {
        return ip_spread;
    }

    /**
     *
     * @param ip_spread
     */
    public void setIp_spread(String ip_spread) {
        this.ip_spread = ip_spread;
    }

    /**
     *
     * @return
     */
    public String getCod() {
        return cod;
    }

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
        return id;
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
        return filiale;
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
    public String getUser() {
        return user;
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
    public String getTill_from() {
        return till_from;
    }

    /**
     *
     * @param till_from
     */
    public void setTill_from(String till_from) {
        this.till_from = till_from;
    }

    /**
     *
     * @return
     */
    public String getFg_tofrom() {
        return fg_tofrom;
    }

    /**
     *
     * @param fg_tofrom
     */
    public void setFg_tofrom(String fg_tofrom) {
        this.fg_tofrom = fg_tofrom;
    }

    /**
     *
     * @return
     */
    public String getFg_brba() {
        return fg_brba;
    }

    /**
     *
     * @param fg_brba
     */
    public void setFg_brba(String fg_brba) {
        this.fg_brba = fg_brba;
    }

    /**
     *
     * @return
     */
    public String getCod_dest() {
        return cod_dest;
    }

    /**
     *
     * @param cod_dest
     */
    public void setCod_dest(String cod_dest) {
        this.cod_dest = cod_dest;
    }

    /**
     *
     * @return
     */
    public String getIdopen_from() {
        return idopen_from;
    }

    /**
     *
     * @param idopen_from
     */
    public void setIdopen_from(String idopen_from) {
        this.idopen_from = idopen_from;
    }

    /**
     *
     * @return
     */
    public String getDt_it() {
        return dt_it;
    }

    /**
     *
     * @param dt_it
     */
    public void setDt_it(String dt_it) {
        this.dt_it = dt_it;
    }

    /**
     *
     * @return
     */
    public String getFg_annullato() {
        return fg_annullato;
    }

    /**
     *
     * @param fg_annullato
     */
    public void setFg_annullato(String fg_annullato) {
        this.fg_annullato = fg_annullato;
    }

    /**
     *
     * @return
     */
    public String getDel_dt() {
        return del_dt;
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
        return del_user;
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
        return del_motiv;
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
    public String getNote() {
        return note;
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
    public String getIp_oneri() {
        return ip_oneri;
    }

    /**
     *
     * @param ip_oneri
     */
    public void setIp_oneri(String ip_oneri) {
        this.ip_oneri = ip_oneri;
    }

    /**
     *
     * @return
     */
    public String getFiliale_in() {
        return filiale_in;
    }

    /**
     *
     * @param filiale_in
     */
    public void setFiliale_in(String filiale_in) {
        this.filiale_in = filiale_in;
    }

    /**
     *
     * @return
     */
    public String getId_in() {
        return id_in;
    }

    /**
     *
     * @param id_in
     */
    public void setId_in(String id_in) {
        this.id_in = id_in;
    }

    @Override
    public int compareTo(ET_change o) {
        return this.filiale.compareTo(o.getFiliale());
    }

}
