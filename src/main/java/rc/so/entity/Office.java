package rc.so.entity;

import static rc.so.util.HtmlEncoder.getBase64HTML;

/**
 *
 * @author rcosco
 */
public class Office {

    String cod;
    String de_office;
    String add_city;
    String add_cap;
    String add_via;
    String vat;
    String reg_impr;
    String rea;
    String changetype;
    String decimalround;
    String url_bl;
    String txt_ricev_1;
    String txt_ricev_2;
    String txt_alert_threshold_1;
    String txt_alert_threshold_2;
    String txt_ricev_bb;
    String txt_nopep;
    
    String scadenza_bb;
    String showagency;
    String minutes;
    String kyc_mesi;
    String kyc_soglia;
    
    String risk_days;
    String risk_ntr;
    String risk_soglia;

    /**
     *
     * @return
     */
    public String getTxt_nopep() {
        return getBase64HTML(this.txt_nopep);
    }

    /**
     *
     * @return
     */
    public String n_getTxt_nopep() {
        return this.txt_nopep;
    }

    /**
     *
     * @param txt_nopep
     */
    public void setTxt_nopep(String txt_nopep) {
        this.txt_nopep = txt_nopep;
    }

    /**
     *
     * @return
     */
    public String getRisk_days() {
        return risk_days;
    }

    /**
     *
     * @param risk_days
     */
    public void setRisk_days(String risk_days) {
        this.risk_days = risk_days;
    }

    /**
     *
     * @return
     */
    public String getRisk_ntr() {
        return risk_ntr;
    }

    /**
     *
     * @param risk_ntr
     */
    public void setRisk_ntr(String risk_ntr) {
        this.risk_ntr = risk_ntr;
    }

    /**
     *
     * @return
     */
    public String getRisk_soglia() {
        return risk_soglia;
    }

    /**
     *
     * @param risk_soglia
     */
    public void setRisk_soglia(String risk_soglia) {
        this.risk_soglia = risk_soglia;
    }
    
    /**
     *
     * @return
     */
    public String getChangeOperator() {
        if (this.changetype.equals("*")) {
            return "/";
        }
        return "*";
    }

    /**
     *
     * @return
     */
    public String getKyc_mesi() {
        return kyc_mesi;
    }

    /**
     *
     * @param kyc_mesi
     */
    public void setKyc_mesi(String kyc_mesi) {
        this.kyc_mesi = kyc_mesi;
    }

    /**
     *
     * @return
     */
    public String getKyc_soglia() {
        return kyc_soglia;
    }

    /**
     *
     * @param kyc_soglia
     */
    public void setKyc_soglia(String kyc_soglia) {
        this.kyc_soglia = kyc_soglia;
    }

    /**
     *
     * @return
     */
    public String getMinutes() {
        return minutes;
    }

    /**
     *
     * @param minutes
     */
    public void setMinutes(String minutes) {
        this.minutes = minutes;
    }

    /**
     *
     * @return
     */
    public String getShowagency() {
        return this.showagency;
    }

    /**
     *
     * @param showagency
     */
    public void setShowagency(String showagency) {
        this.showagency = showagency;
    }

    /**
     *
     * @return
     */
    public String getTxt_ricev_bb() {
        return getBase64HTML(this.txt_ricev_bb);
    }

    /**
     *
     * @return
     */
    public String n_getTxt_ricev_bb() {
        return this.txt_ricev_bb;
    }

    /**
     *
     * @param txt_ricev_bb
     */
    public void setTxt_ricev_bb(String txt_ricev_bb) {
        this.txt_ricev_bb = txt_ricev_bb;
    }

    /**
     *
     * @return
     */
    public String getScadenza_bb() {
        return this.scadenza_bb;
    }

    /**
     *
     * @param scadenza_bb
     */
    public void setScadenza_bb(String scadenza_bb) {
        this.scadenza_bb = scadenza_bb;
    }

    /**
     *
     * @return
     */
    public String getCod() {
        return this.cod;
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
    public String getDe_office() {
        return this.de_office;
    }

    /**
     *
     * @param de_office
     */
    public void setDe_office(String de_office) {
        this.de_office = de_office;
    }

    /**
     *
     * @return
     */
    public String getAdd_city() {
        return this.add_city;
    }

    /**
     *
     * @param add_city
     */
    public void setAdd_city(String add_city) {
        this.add_city = add_city;
    }

    /**
     *
     * @return
     */
    public String getAdd_cap() {
        return this.add_cap;
    }

    /**
     *
     * @param add_cap
     */
    public void setAdd_cap(String add_cap) {
        this.add_cap = add_cap;
    }

    /**
     *
     * @return
     */
    public String getAdd_via() {
        return this.add_via;
    }

    /**
     *
     * @param add_via
     */
    public void setAdd_via(String add_via) {
        this.add_via = add_via;
    }

    /**
     *
     * @return
     */
    public String getVat() {
        return this.vat;
    }

    /**
     *
     * @param vat
     */
    public void setVat(String vat) {
        this.vat = vat;
    }

    /**
     *
     * @return
     */
    public String getReg_impr() {
        return this.reg_impr;
    }

    /**
     *
     * @param reg_impr
     */
    public void setReg_impr(String reg_impr) {
        this.reg_impr = reg_impr;
    }

    /**
     *
     * @return
     */
    public String getRea() {
        return this.rea;
    }

    /**
     *
     * @param rea
     */
    public void setRea(String rea) {
        this.rea = rea;
    }

    /**
     *
     * @return
     */
    public String getChangetype() {
        return this.changetype;
    }

    /**
     *
     * @param changetype
     */
    public void setChangetype(String changetype) {
        this.changetype = changetype;
    }

    /**
     *
     * @return
     */
    public String getDecimalround() {
        return this.decimalround;
    }

    /**
     *
     * @param decimalround
     */
    public void setDecimalround(String decimalround) {
        this.decimalround = decimalround;
    }

    /**
     *
     * @return
     */
    public String getUrl_bl() {
        return this.url_bl;
    }

    /**
     *
     * @param url_bl
     */
    public void setUrl_bl(String url_bl) {
        this.url_bl = url_bl;
    }

    /**
     *
     * @return
     */
    public String getTxt_ricev_1() {
        return getBase64HTML(this.txt_ricev_1);
    }
    
    /**
     *
     * @return
     */
    public String n_getTxt_ricev_1() {
        return this.txt_ricev_1;
    }
    
    /**
     *
     * @param txt_ricev_1
     */
    public void setTxt_ricev_1(String txt_ricev_1) {
        this.txt_ricev_1 = txt_ricev_1;
    }

    /**
     *
     * @return
     */
    public String getTxt_ricev_2() {
        return getBase64HTML(this.txt_ricev_2);
    }

    /**
     *
     * @return
     */
    public String n_getTxt_ricev_2() {
        return this.txt_ricev_2;
    }

    /**
     *
     * @param txt_ricev_2
     */
    public void setTxt_ricev_2(String txt_ricev_2) {
        this.txt_ricev_2 = txt_ricev_2;
    }

    /**
     *
     * @return
     */
    public String getTxt_alert_threshold_1() {
        return getBase64HTML(this.txt_alert_threshold_1);
    }

    /**
     *
     * @return
     */
    public String n_getTxt_alert_threshold_1() {
        return this.txt_alert_threshold_1;
    }

    /**
     *
     * @param txt_alert_threshold_1
     */
    public void setTxt_alert_threshold_1(String txt_alert_threshold_1) {
        this.txt_alert_threshold_1 = txt_alert_threshold_1;
    }

    /**
     *
     * @return
     */
    public String getTxt_alert_threshold_2() {
        return getBase64HTML(this.txt_alert_threshold_2);
    }

    /**
     *
     * @return
     */
    public String n_getTxt_alert_threshold_2() {
        return this.txt_alert_threshold_2;
    }

    /**
     *
     * @param txt_alert_threshold_2
     */
    public void setTxt_alert_threshold_2(String txt_alert_threshold_2) {
        this.txt_alert_threshold_2 = txt_alert_threshold_2;
    }
}


/* Location:              C:\Users\rcosco\Desktop\classes\!\entity\Office.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */
