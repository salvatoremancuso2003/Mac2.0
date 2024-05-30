/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rc.so.excel;

import org.joda.time.DateTime;

/**
 *
 * @author rcosco
 */
public class CambioMTD_YTD {
    
    String filiale;
    String descr;
    
    DateTime datastart;
    DateTime dataend;
    
    String mtd_totale,mtd_totale_prevyear;
    String mtd_bdg;
    String mtd_proiezione_finemese,mtd_proiezione_budget_finemese;
    
    String buy,buy_prevyear,cc,cc_prevyear,buycc,buycc_prevyear,sell,sell_prevyear,totvol,totvol_prevyear;
    String trbuy,trbuy_prevyear,trcctr,trcc_prevyear,trbuycc,trbuycc_prevyear,trsell,trsell_prevyear,trtot,trtot_prevyear;
    String cop,fx;

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
    public String getDescr() {
        return descr;
    }

    /**
     *
     * @param descr
     */
    public void setDescr(String descr) {
        this.descr = descr;
    }

    /**
     *
     * @return
     */
    public DateTime getDatastart() {
        return datastart;
    }

    /**
     *
     * @param datastart
     */
    public void setDatastart(DateTime datastart) {
        this.datastart = datastart;
    }

    /**
     *
     * @return
     */
    public DateTime getDataend() {
        return dataend;
    }

    /**
     *
     * @param dataend
     */
    public void setDataend(DateTime dataend) {
        this.dataend = dataend;
    }

    /**
     *
     * @return
     */
    public String getMtd_totale() {
        return mtd_totale;
    }

    /**
     *
     * @param mtd_totale
     */
    public void setMtd_totale(String mtd_totale) {
        this.mtd_totale = mtd_totale;
    }

    /**
     *
     * @return
     */
    public String getMtd_totale_prevyear() {
        return mtd_totale_prevyear;
    }

    /**
     *
     * @param mtd_totale_prevyear
     */
    public void setMtd_totale_prevyear(String mtd_totale_prevyear) {
        this.mtd_totale_prevyear = mtd_totale_prevyear;
    }

    /**
     *
     * @return
     */
    public String getMtd_bdg() {
        return mtd_bdg;
    }

    /**
     *
     * @param mtd_bdg
     */
    public void setMtd_bdg(String mtd_bdg) {
        this.mtd_bdg = mtd_bdg;
    }

    /**
     *
     * @return
     */
    public String getMtd_proiezione_finemese() {
        return mtd_proiezione_finemese;
    }

    /**
     *
     * @param mtd_proiezione_finemese
     */
    public void setMtd_proiezione_finemese(String mtd_proiezione_finemese) {
        this.mtd_proiezione_finemese = mtd_proiezione_finemese;
    }

    /**
     *
     * @return
     */
    public String getMtd_proiezione_budget_finemese() {
        return mtd_proiezione_budget_finemese;
    }

    /**
     *
     * @param mtd_proiezione_budget_finemese
     */
    public void setMtd_proiezione_budget_finemese(String mtd_proiezione_budget_finemese) {
        this.mtd_proiezione_budget_finemese = mtd_proiezione_budget_finemese;
    }

    /**
     *
     * @return
     */
    public String getBuy() {
        return buy;
    }

    /**
     *
     * @param buy
     */
    public void setBuy(String buy) {
        this.buy = buy;
    }

    /**
     *
     * @return
     */
    public String getBuy_prevyear() {
        return buy_prevyear;
    }

    /**
     *
     * @param buy_prevyear
     */
    public void setBuy_prevyear(String buy_prevyear) {
        this.buy_prevyear = buy_prevyear;
    }

    /**
     *
     * @return
     */
    public String getCc() {
        return cc;
    }

    /**
     *
     * @param cc
     */
    public void setCc(String cc) {
        this.cc = cc;
    }

    /**
     *
     * @return
     */
    public String getCc_prevyear() {
        return cc_prevyear;
    }

    /**
     *
     * @param cc_prevyear
     */
    public void setCc_prevyear(String cc_prevyear) {
        this.cc_prevyear = cc_prevyear;
    }

    /**
     *
     * @return
     */
    public String getBuycc() {
        return buycc;
    }

    /**
     *
     * @param buycc
     */
    public void setBuycc(String buycc) {
        this.buycc = buycc;
    }

    /**
     *
     * @return
     */
    public String getBuycc_prevyear() {
        return buycc_prevyear;
    }

    /**
     *
     * @param buycc_prevyear
     */
    public void setBuycc_prevyear(String buycc_prevyear) {
        this.buycc_prevyear = buycc_prevyear;
    }

    /**
     *
     * @return
     */
    public String getSell() {
        return sell;
    }

    /**
     *
     * @param sell
     */
    public void setSell(String sell) {
        this.sell = sell;
    }

    /**
     *
     * @return
     */
    public String getSell_prevyear() {
        return sell_prevyear;
    }

    /**
     *
     * @param sell_prevyear
     */
    public void setSell_prevyear(String sell_prevyear) {
        this.sell_prevyear = sell_prevyear;
    }

    /**
     *
     * @return
     */
    public String getTotvol() {
        return totvol;
    }

    /**
     *
     * @param totvol
     */
    public void setTotvol(String totvol) {
        this.totvol = totvol;
    }

    /**
     *
     * @return
     */
    public String getTotvol_prevyear() {
        return totvol_prevyear;
    }

    /**
     *
     * @param totvol_prevyear
     */
    public void setTotvol_prevyear(String totvol_prevyear) {
        this.totvol_prevyear = totvol_prevyear;
    }

    /**
     *
     * @return
     */
    public String getTrbuy() {
        return trbuy;
    }

    /**
     *
     * @param trbuy
     */
    public void setTrbuy(String trbuy) {
        this.trbuy = trbuy;
    }

    /**
     *
     * @return
     */
    public String getTrbuy_prevyear() {
        return trbuy_prevyear;
    }

    /**
     *
     * @param trbuy_prevyear
     */
    public void setTrbuy_prevyear(String trbuy_prevyear) {
        this.trbuy_prevyear = trbuy_prevyear;
    }

    /**
     *
     * @return
     */
    public String getTrcctr() {
        return trcctr;
    }

    /**
     *
     * @param trcctr
     */
    public void setTrcctr(String trcctr) {
        this.trcctr = trcctr;
    }

    /**
     *
     * @return
     */
    public String getTrcc_prevyear() {
        return trcc_prevyear;
    }

    /**
     *
     * @param trcc_prevyear
     */
    public void setTrcc_prevyear(String trcc_prevyear) {
        this.trcc_prevyear = trcc_prevyear;
    }

    /**
     *
     * @return
     */
    public String getTrbuycc() {
        return trbuycc;
    }

    /**
     *
     * @param trbuycc
     */
    public void setTrbuycc(String trbuycc) {
        this.trbuycc = trbuycc;
    }

    /**
     *
     * @return
     */
    public String getTrbuycc_prevyear() {
        return trbuycc_prevyear;
    }

    /**
     *
     * @param trbuycc_prevyear
     */
    public void setTrbuycc_prevyear(String trbuycc_prevyear) {
        this.trbuycc_prevyear = trbuycc_prevyear;
    }

    /**
     *
     * @return
     */
    public String getTrsell() {
        return trsell;
    }

    /**
     *
     * @param trsell
     */
    public void setTrsell(String trsell) {
        this.trsell = trsell;
    }

    /**
     *
     * @return
     */
    public String getTrsell_prevyear() {
        return trsell_prevyear;
    }

    /**
     *
     * @param trsell_prevyear
     */
    public void setTrsell_prevyear(String trsell_prevyear) {
        this.trsell_prevyear = trsell_prevyear;
    }

    /**
     *
     * @return
     */
    public String getTrtot() {
        return trtot;
    }

    /**
     *
     * @param trtot
     */
    public void setTrtot(String trtot) {
        this.trtot = trtot;
    }

    /**
     *
     * @return
     */
    public String getTrtot_prevyear() {
        return trtot_prevyear;
    }

    /**
     *
     * @param trtot_prevyear
     */
    public void setTrtot_prevyear(String trtot_prevyear) {
        this.trtot_prevyear = trtot_prevyear;
    }

    /**
     *
     * @return
     */
    public String getCop() {
        return cop;
    }

    /**
     *
     * @param cop
     */
    public void setCop(String cop) {
        this.cop = cop;
    }

    /**
     *
     * @return
     */
    public String getFx() {
        return fx;
    }

    /**
     *
     * @param fx
     */
    public void setFx(String fx) {
        this.fx = fx;
    }
    
}