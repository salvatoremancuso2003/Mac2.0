package rc.so.util;

import rc.so.db.Db_Master;
import rc.so.entity.Agency;
import rc.so.entity.BlacklistM;
import rc.so.entity.Company;
import rc.so.entity.Currency;
import rc.so.entity.CustomerKind;
import rc.so.entity.Figures;
import rc.so.entity.NC_causal;
import rc.so.entity.Office;
import rc.so.entity.Till;
import java.util.ArrayList;
import javax.servlet.http.HttpSession;

/**
 *
 * @author rcosco
 */
public class List_new {

    String[] localcur = null;
    Office headhoffice = null;
    boolean companyenabled = false;
    boolean agencyenabled = false;
    ArrayList<CustomerKind> array_custkind = null;
    ArrayList<Figures> array_figbuy = null;
    ArrayList<Agency> array_agency = null;
    ArrayList<String[]> array_country = null;
    ArrayList<String[]> array_identificationCard = null;
    ArrayList<Currency> array_currency = null;
    ArrayList<String[]> array_rate_currency = null;
    ArrayList<String[]> array_credit_card = null;
    ArrayList<String[]> array_undermincommjustify = null;
    ArrayList<Company> array_listCompany = null;
    ArrayList<String[]> array_kindcommissionefissa = null;
    ArrayList<String[]> array_fixcommrange = null;
    ArrayList<String[]> array_raterange = null;
    ArrayList<String[]> array_kind_currency_enabled = null;
    ArrayList<String[]> array_currency_min_sizes = null;
    

    //ArrayList<Client> array_client = null;

    ArrayList<String[]> array_kind_pay = null;
    ArrayList<String[]> array_internetbooking = null;
    ArrayList<Figures> array_figsell = null;
    ArrayList<NC_causal> array_nc_causal = null;
    
    ArrayList<String[]> array_till = null;
    ArrayList<Till> array_till_open = null;
    ArrayList<String[]> array_list_oc_change = null;
    ArrayList<BlacklistM> array_blm = null;
    
    ArrayList<String[]> array_bb = null;
    ArrayList<String[]> array_district = null;
        
    String fil = null;
    
    /**
     *
     * @param type
     * @param se
     */
    public List_new(String type, HttpSession se) {
        Db_Master db = new Db_Master();
        this.fil = db.getCodLocal(false)[0];
        
        this.companyenabled = db.companyenabled(Utility.checkAttribute(se,"us_fil"));
        this.agencyenabled = db.agencyenabled(Utility.checkAttribute(se,"us_fil"));
        this.array_district = db.district();
        if (type.equals("B")) {
            //this.array_bb = db.list_BB_waiting();
            this.localcur = db.get_local_currency();
            this.headhoffice = db.get_national_office();
            this.array_custkind = db.list_customerKind_enabled();
            this.array_figbuy = db.list_figures_buy();
            this.array_country = db.country();
            this.array_agency = db.list_agency();
            this.array_identificationCard = db.identificationCard();
            this.array_currency = db.list_currency_buy_sell("1", "0",this.fil);
            this.array_rate_currency = db.rate_currency(this.array_currency, true, false);
            this.array_credit_card = db.credit_card_enabled();
            this.array_undermincommjustify = db.undermincommjustify();
            this.array_listCompany = db.get_list_Agent_company();
            this.array_kindcommissionefissa = db.kindcommissionefissaenabled();
            this.array_fixcommrange = db.fixed_commission_range_enabled();
            this.array_raterange = db.rate_range_enabled();
            this.array_kind_currency_enabled = db.kind_currency_enabled();
            this.array_currency_min_sizes = db.currency_min_sizes();
            this.array_internetbooking = db.list_internetbooking_SELL(); //13/12
            this.array_till = db.list_till_enabled();
            this.array_till_open = db.list_onlytill_status("O", Utility.checkAttribute(se,"us_cod"));
            this.array_list_oc_change = db.list_oc_change_real_user(Utility.checkAttribute(se,"us_cod"));
            this.array_blm = db.list_BlMacc(true);
        }
        if (type.equals("S")) {
            this.localcur = db.get_local_currency();

            this.headhoffice = db.get_national_office();
            this.array_custkind = db.list_customerKind_enabled();
            this.array_country = db.country();
            this.array_agency = db.list_agency();
            this.array_identificationCard = db.identificationCard();

            this.array_credit_card = db.credit_card_enabled_SELL();//13/12
            
            this.array_undermincommjustify = db.undermincommjustify();
            this.array_listCompany = db.get_list_Agent_company();
            this.array_currency_min_sizes = db.currency_min_sizes();
            this.array_kind_currency_enabled = db.kind_currency_enabled();
            this.array_kind_pay = db.kind_payment();
            this.array_figsell = db.list_figures_sell();
            
            this.array_internetbooking = db.list_internetbooking_SELL(); //13/12

            this.array_fixcommrange = db.fixed_commission_range_enabled();
            this.array_raterange = db.rate_range_enabled();
            this.array_currency = db.list_currency_buy_sell("0", "1",this.fil);
            
            this.array_rate_currency = db.rate_currency(this.array_currency, false, true);
            this.array_kindcommissionefissa = db.kindcommissionefissaenabled();
            this.array_nc_causal = db.list_nc_causal_sell();
            
            this.array_till = db.list_till_enabled();
            this.array_till_open = db.list_onlytill_status("O", Utility.checkAttribute(se, "us_cod"));
            this.array_blm = db.list_BlMacc(true);
            this.array_list_oc_change = db.list_oc_change_real();
        }

        db.closeDB();
    }

    /**
     *
     * @return
     */
    public String getFil() {
        return fil;
    }

    /**
     *
     * @param fil
     */
    public void setFil(String fil) {
        this.fil = fil;
    }
    
    /**
     *
     * @return
     */
    public ArrayList<String[]> getArray_district() {
        return array_district;
    }

    /**
     *
     * @param array_district
     */
    public void setArray_district(ArrayList<String[]> array_district) {
        this.array_district = array_district;
    }
    
    /**
     *
     * @param flag
     * @return
     */
    public ArrayList<String[]> getCountryFlag(String flag) {
        ArrayList<String[]> out = new ArrayList<>();
        for (int i = 0; i < this.array_country.size(); i++) {
            if (flag.equalsIgnoreCase(((String[]) this.array_country.get(i))[3])) {
                out.add(this.array_country.get(i));
            }
        }
        return out;
    }

    /**
     *
     * @param descrizioneNazione
     * @return
     */
    public String getNazioneCodice(String descrizioneNazione) {
        for (int i = 0; i < this.array_country.size(); i++) {
            if (descrizioneNazione.equalsIgnoreCase(((String[]) this.array_country.get(i))[1])) {
                return ((String[]) this.array_country.get(i))[0];
            }
        }
        return "000";
    }

    /**
     *
     * @return
     */
    public boolean isAgencyenabled() {
        return agencyenabled;
    }

    /**
     *
     * @param agencyenabled
     */
    public void setAgencyenabled(boolean agencyenabled) {
        this.agencyenabled = agencyenabled;
    }

    /**
     *
     * @return
     */
    public ArrayList<String[]> getArray_bb() {
        return array_bb;
    }

    /**
     *
     * @param array_bb
     */
    public void setArray_bb(ArrayList<String[]> array_bb) {
        this.array_bb = array_bb;
    }
    
    /**
     *
     * @return
     */
    public ArrayList<String[]> getArray_list_oc_change() {
        return array_list_oc_change;
    }

    /**
     *
     * @param array_list_oc_change
     */
    public void setArray_list_oc_change(ArrayList<String[]> array_list_oc_change) {
        this.array_list_oc_change = array_list_oc_change;
    }
    
    /**
     *
     * @return
     */
    public ArrayList<String[]> getArray_till() {
        return array_till;
    }

    /**
     *
     * @param array_till
     */
    public void setArray_till(ArrayList<String[]> array_till) {
        this.array_till = array_till;
    }

    /**
     *
     * @return
     */
    public ArrayList<Till> getArray_till_open() {
        return array_till_open;
    }

    /**
     *
     * @param array_till_open
     */
    public void setArray_till_open(ArrayList<Till> array_till_open) {
        this.array_till_open = array_till_open;
    }
    
    /**
     *
     * @return
     */
    public boolean isCompanyenabled() {
        return companyenabled;
    }

    /**
     *
     * @param companyenabled
     */
    public void setCompanyenabled(boolean companyenabled) {
        this.companyenabled = companyenabled;
    }
    
    /**
     *
     * @return
     */
    public ArrayList<NC_causal> getArray_nc_causal() {
        return this.array_nc_causal;
    }

    /**
     *
     * @return
     */
    public ArrayList<Figures> getArray_figsell() {
        return this.array_figsell;
    }

    /**
     *
     * @return
     */
    public ArrayList<String[]> getArray_internetbooking() {
        return this.array_internetbooking;
    }

    /**
     *
     * @return
     */
    public ArrayList<String[]> getArray_kind_pay() {
        return this.array_kind_pay;
    }

//    public ArrayList<Client> getArray_client() {
//        return this.array_client;
//    }

    /**
     *
     * @return
     */

    public ArrayList<String[]> getArray_currency_min_sizes() {
        return this.array_currency_min_sizes;
    }

    /**
     *
     * @return
     */
    public String[] getLocalcur() {
        return this.localcur;
    }

    /**
     *
     * @return
     */
    public ArrayList<String[]> getArray_kind_currency_enabled() {
        return this.array_kind_currency_enabled;
    }

    /**
     *
     * @return
     */
    public ArrayList<String[]> getArray_raterange() {
        return this.array_raterange;
    }

    /**
     *
     * @return
     */
    public Office getHeadhoffice() {
        return this.headhoffice;
    }

    /**
     *
     * @return
     */
    public ArrayList<String[]> getArray_fixcommrange() {
        return this.array_fixcommrange;
    }

    /**
     *
     * @return
     */
    public ArrayList<String[]> getArray_kindcommissionefissa() {
        return this.array_kindcommissionefissa;
    }

    /**
     *
     * @return
     */
    public ArrayList<Company> getArray_listCompany() {
        return this.array_listCompany;
    }

    /**
     *
     * @return
     */
    public ArrayList<String[]> getArray_undermincommjustify() {
        return this.array_undermincommjustify;
    }

    /**
     *
     * @return
     */
    public ArrayList<String[]> getArray_credit_card() {
        return this.array_credit_card;
    }

    /**
     *
     * @return
     */
    public ArrayList<String[]> getArray_rate_currency() {
        return this.array_rate_currency;
    }

    /**
     *
     * @return
     */
    public ArrayList<Currency> getArray_currency() {
        return this.array_currency;
    }

    /**
     *
     * @return
     */
    public ArrayList<String[]> getArray_identificationCard() {
        return this.array_identificationCard;
    }

    /**
     *
     * @return
     */
    public ArrayList<String[]> getArray_country() {
        return this.array_country;
    }

    /**
     *
     * @return
     */
    public ArrayList<CustomerKind> getArray_custkind() {
        return this.array_custkind;
    }

    /**
     *
     * @return
     */
    public ArrayList<Figures> getArray_figbuy() {
        return this.array_figbuy;
    }

    /**
     *
     * @return
     */
    public ArrayList<Agency> getArray_agency() {
        return this.array_agency;
    }

    /**
     *
     * @return
     */
    public ArrayList<BlacklistM> getArray_blm() {
        return array_blm;
    }

    /**
     *
     * @param array_blm
     */
    public void setArray_blm(ArrayList<BlacklistM> array_blm) {
        this.array_blm = array_blm;
    }
    
}
