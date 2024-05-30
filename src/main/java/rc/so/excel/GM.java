/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rc.so.excel;

import java.util.HashMap;

/**
 *
 * @author rcosco
 */
public class GM implements Comparable<GM>{
    
    String area;
    String riga1,riga2,riga3;
    String descr,formula;
    String budget,annoprec_adeguato,annoprec_alldata,ytd_finemese,ytd_budget,annoprec_ytd;

    /**
     * Constructor
     */
    public GM() {
        this.budget = "0.00";
        this.annoprec_adeguato = "0.00";
        this.annoprec_alldata = "0.00";
        this.ytd_finemese = "0.00";
        this.ytd_budget = "0.00";
        this.annoprec_ytd = "0.00";
    }
    
    
    HashMap<String, String> mappavalori = new HashMap<>();

    /**
     *
     * @return
     */
    public String getArea() {
        return area;
    }

    /**
     *
     * @param area
     */
    public void setArea(String area) {
        this.area = area;
    }

    /**
     *
     * @return
     */
    public String getBudget() {
        return budget;
    }

    /**
     *
     * @param budget
     */
    public void setBudget(String budget) {
        this.budget = budget;
    }

    /**
     *
     * @return
     */
    public String getAnnoprec_adeguato() {
        return annoprec_adeguato;
    }

    /**
     *
     * @param annoprec_adeguato
     */
    public void setAnnoprec_adeguato(String annoprec_adeguato) {
        this.annoprec_adeguato = annoprec_adeguato;
    }

    /**
     *
     * @return
     */
    public String getAnnoprec_alldata() {
        return annoprec_alldata;
    }

    /**
     *
     * @param annoprec_alldata
     */
    public void setAnnoprec_alldata(String annoprec_alldata) {
        this.annoprec_alldata = annoprec_alldata;
    }

    /**
     *
     * @return
     */
    public String getYtd_finemese() {
        return ytd_finemese;
    }

    /**
     *
     * @param ytd_finemese
     */
    public void setYtd_finemese(String ytd_finemese) {
        this.ytd_finemese = ytd_finemese;
    }

    /**
     *
     * @return
     */
    public String getYtd_budget() {
        return ytd_budget;
    }

    /**
     *
     * @param ytd_budget
     */
    public void setYtd_budget(String ytd_budget) {
        this.ytd_budget = ytd_budget;
    }

    /**
     *
     * @return
     */
    public String getAnnoprec_ytd() {
        return annoprec_ytd;
    }

    /**
     *
     * @param annoprec_ytd
     */
    public void setAnnoprec_ytd(String annoprec_ytd) {
        this.annoprec_ytd = annoprec_ytd;
    }
    
    /**
     *
     * @return
     */
    public String getFormula() {
        return formula;
    }

    /**
     *
     * @param formula
     */
    public void setFormula(String formula) {
        this.formula = formula;
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
    public HashMap<String, String> getMappavalori() {
        return mappavalori;
    }

    /**
     *
     * @param mappavalori
     */
    public void setMappavalori(HashMap<String, String> mappavalori) {
        this.mappavalori = mappavalori;
    }
    
    /**
     *
     * @return
     */
    public String getRiga1() {
        return riga1;
    }

    /**
     *
     * @param riga1
     */
    public void setRiga1(String riga1) {
        this.riga1 = riga1;
    }

    /**
     *
     * @return
     */
    public String getRiga2() {
        return riga2;
    }

    /**
     *
     * @param riga2
     */
    public void setRiga2(String riga2) {
        this.riga2 = riga2;
    }

    /**
     *
     * @return
     */
    public String getRiga3() {
        return riga3;
    }

    /**
     *
     * @param riga3
     */
    public void setRiga3(String riga3) {
        this.riga3 = riga3;
    }

    @Override
    public int compareTo(GM o) {
        return this.area.compareTo(o.getArea());
    }
    
}