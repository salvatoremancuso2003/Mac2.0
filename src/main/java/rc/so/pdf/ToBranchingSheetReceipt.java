/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rc.so.pdf;

import static rc.so.util.Utility.formatMysqltoDisplay;
import java.util.ArrayList;

/**
 *
 * @author vcrugliano
 */
public class ToBranchingSheetReceipt {
    

    
    String id_filiale, de_filiale,currency,amount,barchrate,eurotravelamount,eurotravelbrchrate,perscheamount,perchebrchrate,ccamount,ccbrchrate,branchtotal,spread,perc;
    String fromsafe,tobranch;
    String pinuser;
    String numtranfer;

    /**
     *
     * @return
     */
    public String getNumTransfer() {
        return numtranfer;
    }

    /**
     *
     * @param numtranfer
     */
    public void setNumtranfer(String numtranfer) {
        this.numtranfer = numtranfer;
    }
    
    
    
    ArrayList<String> dati;

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
        return formatMysqltoDisplay(branchtotal);
    }
    
    /**
     *
     * @return
     */
    public String getBranchtotalSenzaFormattazione() {
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
    public String getSpreadSenzaFormattazione() {
        return spread;
    }
    
    /**
     *
     * @return
     */
    public String getSpread() {
        return formatMysqltoDisplay(spread);
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
    public String getPercSenzaFormattazione() {
        return perc;
    }
    
    /**
     *
     * @return
     */
    public String getPerc() {
        return formatMysqltoDisplay(perc);
    }

    /**
     *
     * @param perc
     */
    public void setPerc(String perc) {
        this.perc = perc;
    }

    
    
}


