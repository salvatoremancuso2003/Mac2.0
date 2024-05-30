/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rc.so.reportcentrale;

import java.util.ArrayList;

/**
 *
 * @author srotella
 */
public class C_BankBranch_value {
    
    String id_filiale, de_filiale,currency,amount,barchrate,eurotravelamount,
            eurotravelbrchrate,perscheamount,perchebrchrate,ccamount,ccbrchrate,branchtotal,spread,perc;
    String fromsafe,tobranch;
    String pinuser;
    String numtranfer;
    
    String bnkrate,eurotravelbnkrate,perchebnkrate,ccbnkrate,bnktotal;
    String tobank;
  
    
    ArrayList<String> dati;
    
    String note;

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
    public String getBnkrate() {
        return bnkrate;
    }

    /**
     *
     * @param bnkrate
     */
    public void setBnkrate(String bnkrate) {
        this.bnkrate = bnkrate;
    }

    /**
     *
     * @return
     */
    public String getEurotravelbnkrate() {
        return eurotravelbnkrate;
    }

    /**
     *
     * @param eurotravelbnkrate
     */
    public void setEurotravelbnkrate(String eurotravelbnkrate) {
        this.eurotravelbnkrate = eurotravelbnkrate;
    }

    /**
     *
     * @return
     */
    public String getPerchebnkrate() {
        return perchebnkrate;
    }

    /**
     *
     * @param perchebnkrate
     */
    public void setPerchebnkrate(String perchebnkrate) {
        this.perchebnkrate = perchebnkrate;
    }

    /**
     *
     * @return
     */
    public String getCcbnkrate() {
        return ccbnkrate;
    }

    /**
     *
     * @param ccbnkrate
     */
    public void setCcbnkrate(String ccbnkrate) {
        this.ccbnkrate = ccbnkrate;
    }

    /**
     *
     * @return
     */
    public String getBnktotal() {
        return bnktotal;
    }

    /**
     *
     * @param bnktotal
     */
    public void setBnktotal(String bnktotal) {
        this.bnktotal = bnktotal;
    }

    /**
     *
     * @return
     */
    public String getTobank() {
        return tobank;
    }

    /**
     *
     * @param tobank
     */
    public void setTobank(String tobank) {
        this.tobank = tobank;
    }

    /**
     *
     * @return
     */
    public ArrayList<String> getDati() {
        return dati;
    }

    /**
     *
     * @param dati
     */
    public void setDati(ArrayList<String> dati) {
        this.dati = dati;
    }
    
    /**
     *
     * @return
     */
    public String getId_filiale() {
        return id_filiale;
    }

    /**
     *
     * @param id_filiale
     */
    public void setId_filiale(String id_filiale) {
        this.id_filiale = id_filiale;
    }

    /**
     *
     * @return
     */
    public String getDe_filiale() {
        return de_filiale;
    }

    /**
     *
     * @param de_filiale
     */
    public void setDe_filiale(String de_filiale) {
        this.de_filiale = de_filiale;
    }

    /**
     *
     * @return
     */
    public String getCurrency() {
        return currency;
    }

    /**
     *
     * @param currency
     */
    public void setCurrency(String currency) {
        this.currency = currency;
    }

    /**
     *
     * @return
     */
    public String getAmount() {
        return amount;
    }

    /**
     *
     * @param amount
     */
    public void setAmount(String amount) {
        this.amount = amount;
    }

    /**
     *
     * @return
     */
    public String getBarchrate() {
        return barchrate;
    }

    /**
     *
     * @param barchrate
     */
    public void setBarchrate(String barchrate) {
        this.barchrate = barchrate;
    }

    /**
     *
     * @return
     */
    public String getEurotravelamount() {
        return eurotravelamount;
    }

    /**
     *
     * @param eurotravelamount
     */
    public void setEurotravelamount(String eurotravelamount) {
        this.eurotravelamount = eurotravelamount;
    }

    /**
     *
     * @return
     */
    public String getEurotravelbrchrate() {
        return eurotravelbrchrate;
    }

    /**
     *
     * @param eurotravelbrchrate
     */
    public void setEurotravelbrchrate(String eurotravelbrchrate) {
        this.eurotravelbrchrate = eurotravelbrchrate;
    }

    /**
     *
     * @return
     */
    public String getPerscheamount() {
        return perscheamount;
    }

    /**
     *
     * @param perscheamount
     */
    public void setPerscheamount(String perscheamount) {
        this.perscheamount = perscheamount;
    }

    /**
     *
     * @return
     */
    public String getPerchebrchrate() {
        return perchebrchrate;
    }

    /**
     *
     * @param perchebrchrate
     */
    public void setPerchebrchrate(String perchebrchrate) {
        this.perchebrchrate = perchebrchrate;
    }

    /**
     *
     * @return
     */
    public String getCcamount() {
        return ccamount;
    }

    /**
     *
     * @param ccamount
     */
    public void setCcamount(String ccamount) {
        this.ccamount = ccamount;
    }

    /**
     *
     * @return
     */
    public String getCcbrchrate() {
        return ccbrchrate;
    }

    /**
     *
     * @param ccbrchrate
     */
    public void setCcbrchrate(String ccbrchrate) {
        this.ccbrchrate = ccbrchrate;
    }

    /**
     *
     * @return
     */
    public String getBranchtotal() {
        return branchtotal;
    }

    /**
     *
     * @param branchtotal
     */
    public void setBranchtotal(String branchtotal) {
        this.branchtotal = branchtotal;
    }

    /**
     *
     * @return
     */
    public String getSpread() {
        return spread;
    }

    /**
     *
     * @param spread
     */
    public void setSpread(String spread) {
        this.spread = spread;
    }

    /**
     *
     * @return
     */
    public String getPerc() {
        return perc;
    }

    /**
     *
     * @param perc
     */
    public void setPerc(String perc) {
        this.perc = perc;
    }

    /**
     *
     * @return
     */
    public String getFromsafe() {
        return fromsafe;
    }

    /**
     *
     * @param fromsafe
     */
    public void setFromsafe(String fromsafe) {
        this.fromsafe = fromsafe;
    }

    /**
     *
     * @return
     */
    public String getTobranch() {
        return tobranch;
    }

    /**
     *
     * @param tobranch
     */
    public void setTobranch(String tobranch) {
        this.tobranch = tobranch;
    }

    /**
     *
     * @return
     */
    public String getPinuser() {
        return pinuser;
    }

    /**
     *
     * @param pinuser
     */
    public void setPinuser(String pinuser) {
        this.pinuser = pinuser;
    }

    /**
     *
     * @return
     */
    public String getNumtranfer() {
        return numtranfer;
    }

    /**
     *
     * @param numtranfer
     */
    public void setNumtranfer(String numtranfer) {
        this.numtranfer = numtranfer;
    }
    
    
    
}
