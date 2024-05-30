package rc.so.entity;

import java.util.HashMap;

/**
 *
 * @author rcosco
 */
public class Branch {

    String filiale;

    String cod;
    String de_branch;
    String add_city;
    String add_cap;
    String add_via;
    String fg_persgiur;
    String prov_raccval;
    String fg_annullato;
    String da_annull;
    String g01;
    String g02;
    String g03;
    String fg_modrate;
    String fg_agency;
    
    String fg_crm;
    
    String olta_user;
    String olta_pass;
    
    String pay_nomeazienda,pay_idazienda,pay_skin,pay_user,pay_password,pay_token,pay_terminale;
    
    String fg_pad;
    
    //evo
    String dt_start;
    String max_ass;
    String target;
    
    String brgr_01,brgr_02,brgr_03,brgr_04,brgr_05,brgr_06,brgr_07,brgr_08,brgr_09,brgr_10;
    
    String opening;
    
    
    HashMap<String, String> listagruppi = new HashMap<>();
    
    /**
     *
     * @return
     */
    public HashMap<String, String> getListagruppi() {
        return listagruppi;
    }

    /**
     * Constructor
     */
    public void setListagruppi() {
        this.listagruppi = new HashMap<>();
        this.listagruppi.put("01", this.brgr_01);
        this.listagruppi.put("02", this.brgr_02);
        this.listagruppi.put("03", this.brgr_03);
        this.listagruppi.put("04", this.brgr_04);
        this.listagruppi.put("05", this.brgr_05);
        this.listagruppi.put("06", this.brgr_06);
        this.listagruppi.put("07", this.brgr_07);
        this.listagruppi.put("08", this.brgr_08);
        this.listagruppi.put("09", this.brgr_09);
        this.listagruppi.put("10", this.brgr_10);
    }

    /**
     *
     * @return
     */
    public String getOpening() {
        return opening;
    }

    /**
     *
     * @param opening
     */
    public void setOpening(String opening) {
        this.opening = opening;
    }
    
    /**
     *
     * @return
     */
    public String getTarget() {
        return target;
    }

    /**
     *
     * @param target
     */
    public void setTarget(String target) {
        this.target = formatValue(target);
    }
    
    /**
     *
     * @return
     */
    public String getBrgr_01() {
        return brgr_01;
    }

    /**
     *
     * @param brgr_01
     */
    public void setBrgr_01(String brgr_01) {
        this.brgr_01 = formatValue(brgr_01);
    }

    /**
     *
     * @return
     */
    public String getBrgr_02() {
        return brgr_02;
    }

    /**
     *
     * @param brgr_02
     */
    public void setBrgr_02(String brgr_02) {
        this.brgr_02 = formatValue(brgr_02);
    }

    /**
     *
     * @return
     */
    public String getBrgr_03() {
        return brgr_03;
    }

    /**
     *
     * @param brgr_03
     */
    public void setBrgr_03(String brgr_03) {
        this.brgr_03 = formatValue(brgr_03);
    }

    /**
     *
     * @return
     */
    public String getBrgr_04() {
        return brgr_04;
    }

    /**
     *
     * @param brgr_04
     */
    public void setBrgr_04(String brgr_04) {
        this.brgr_04 = formatValue(brgr_04);
    }

    /**
     *
     * @return
     */
    public String getBrgr_05() {
        return brgr_05;
    }

    /**
     *
     * @param brgr_05
     */
    public void setBrgr_05(String brgr_05) {
        this.brgr_05 = formatValue(brgr_05);
    }

    /**
     *
     * @return
     */
    public String getBrgr_06() {
        return brgr_06;
    }

    /**
     *
     * @param brgr_06
     */
    public void setBrgr_06(String brgr_06) {
        this.brgr_06 = formatValue(brgr_06);
    }

    /**
     *
     * @return
     */
    public String getBrgr_07() {
        return brgr_07;
    }

    /**
     *
     * @param brgr_07
     */
    public void setBrgr_07(String brgr_07) {
        this.brgr_07 = formatValue(brgr_07);
    }

    /**
     *
     * @return
     */
    public String getBrgr_08() {
        return brgr_08;
    }

    /**
     *
     * @param brgr_08
     */
    public void setBrgr_08(String brgr_08) {
        this.brgr_08 = formatValue(brgr_08);
    }

    /**
     *
     * @return
     */
    public String getBrgr_09() {
        return brgr_09;
    }

    /**
     *
     * @param brgr_09
     */
    public void setBrgr_09(String brgr_09) {
        this.brgr_09 =formatValue(brgr_09);
    }

    /**
     *
     * @return
     */
    public String getBrgr_10() {
        return brgr_10;
    }

    /**
     *
     * @param brgr_10
     */
    public void setBrgr_10(String brgr_10) {
        this.brgr_10 = formatValue(brgr_10);
    }
    
    /**
     *
     * @return
     */
    public String getMax_ass() {
        return max_ass;
    }

    /**
     *
     * @param max_ass
     */
    public void setMax_ass(String max_ass) {
        this.max_ass = formatValue(max_ass);
    }
    
    /**
     *
     * @return
     */
    public String getDt_start() {
        return dt_start;
    }

    /**
     *
     * @param dt_start
     */
    public void setDt_start(String dt_start) {
        this.dt_start = formatValue(dt_start);
    }
    
    /**
     *
     * @return
     */
    public String getFg_pad() {
        return fg_pad;
    }

    /**
     *
     * @param fg_pad
     */
    public void setFg_pad(String fg_pad) {
        this.fg_pad = formatValue(fg_pad);
    }
    
    /**
     *
     * @return
     */
    public String getFg_agency() {
        return fg_agency;
    }

    /**
     *
     * @param fg_agency
     */
    public void setFg_agency(String fg_agency) {
        this.fg_agency = formatValue(fg_agency);
    }

    /**
     *
     * @return
     */
    public String getPay_nomeazienda() {
        return pay_nomeazienda;
    }

    /**
     *
     * @param pay_nomeazienda
     */
    public void setPay_nomeazienda(String pay_nomeazienda) {
        this.pay_nomeazienda = formatValue(pay_nomeazienda);
    }

    /**
     *
     * @return
     */
    public String getPay_idazienda() {
        return pay_idazienda;
    }

    /**
     *
     * @param pay_idazienda
     */
    public void setPay_idazienda(String pay_idazienda) {
        this.pay_idazienda = formatValue(pay_idazienda);
    }

    /**
     *
     * @return
     */
    public String getPay_skin() {
        return pay_skin;
    }

    /**
     *
     * @param pay_skin
     */
    public void setPay_skin(String pay_skin) {
        this.pay_skin = formatValue(pay_skin);
    }

    /**
     *
     * @return
     */
    public String getPay_user() {
        return pay_user;
    }

    /**
     *
     * @param pay_user
     */
    public void setPay_user(String pay_user) {
        this.pay_user = formatValue(pay_user);
    }

    /**
     *
     * @return
     */
    public String getPay_password() {
        return pay_password;
    }

    /**
     *
     * @param pay_password
     */
    public void setPay_password(String pay_password) {
        this.pay_password = formatValue(pay_password);
    }

    /**
     *
     * @return
     */
    public String getPay_token() {
        return pay_token;
    }

    /**
     *
     * @param pay_token
     */
    public void setPay_token(String pay_token) {
        this.pay_token = formatValue(pay_token);
    }

    /**
     *
     * @return
     */
    public String getPay_terminale() {
        return pay_terminale;
    }

    /**
     *
     * @param pay_terminale
     */
    public void setPay_terminale(String pay_terminale) {
        this.pay_terminale = formatValue(pay_terminale);
    }
    
    /**
     *
     * @return
     */
    public String getOlta_user() {
        return olta_user;
    }

    /**
     *
     * @param olta_user
     */
    public void setOlta_user(String olta_user) {
        this.olta_user = formatValue(olta_user);
    }

    /**
     *
     * @return
     */
    public String getOlta_pass() {
        return olta_pass;
    }

    /**
     *
     * @param olta_pass
     */
    public void setOlta_pass(String olta_pass) {
        this.olta_pass = formatValue(olta_pass);
    }
    
    /**
     *
     * @return
     */
    public String getFg_crm() {
        return fg_crm;
    }

    /**
     *
     * @param fg_crm
     */
    public void setFg_crm(String fg_crm) {
        this.fg_crm = formatValue(fg_crm);
    }

    /**
     *
     * @return
     */
    public String getFg_modrate() {
        return fg_modrate;
    }

    /**
     *
     * @param fg_modrate
     */
    public void setFg_modrate(String fg_modrate) {
        this.fg_modrate = formatValue(fg_modrate);
    }
    
    /**
     *
     * @return
     */
    public String getG01() {
        return this.g01;
    }

    /**
     *
     * @param g01
     */
    public void setG01(String g01) {
        this.g01 = formatValue(g01);
    }

    /**
     *
     * @return
     */
    public String getG02() {
        return this.g02;
    }

    /**
     *
     * @param g02
     */
    public void setG02(String g02) {
        this.g02 = formatValue(g02);
    }

    /**
     *
     * @return
     */
    public String getG03() {
        return this.g03;
    }

    /**
     *
     * @param g03
     */
    public void setG03(String g03) {
        this.g03 = formatValue(g03);
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
    public String getDe_branch() {
        return this.de_branch;
    }

    /**
     *
     * @param de_branch
     */
    public void setDe_branch(String de_branch) {
        this.de_branch = formatValue(de_branch);
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
        this.add_city = formatValue(add_city);
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
        this.add_cap = formatValue(add_cap);
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
        this.add_via = formatValue(add_via);
    }

    /**
     *
     * @return
     */
    public String getFg_persgiur() {
        return this.fg_persgiur;
    }

    /**
     *
     * @param fg_persgiur
     */
    public void setFg_persgiur(String fg_persgiur) {
        this.fg_persgiur = formatValue(fg_persgiur);
    }

    /**
     *
     * @return
     */
    public String getProv_raccval() {
        return this.prov_raccval;
    }

    /**
     *
     * @param prov_raccval
     */
    public void setProv_raccval(String prov_raccval) {
        this.prov_raccval = formatValue(prov_raccval);
    }

    /**
     *
     * @return
     */
    public String getFg_annullato() {
        return this.fg_annullato;
    }

    /**
     *
     * @param fg_annullato
     */
    public void setFg_annullato(String fg_annullato) {
        this.fg_annullato = formatValue(fg_annullato);
    }

    /**
     *
     * @return
     */
    public String getDa_annull() {
        return this.da_annull;
    }

    /**
     *
     * @param da_annull
     */
    public void setDa_annull(String da_annull) {
        this.da_annull = formatValue(da_annull);
    }
    
    /**
     *
     * @param ing
     * @return
     */
    public String formatValue(String ing){
        if(ing == null){
            return "";
        }
        if(ing.equals("null")){
            return "";
        }
        return ing;
    }
    
    
}
