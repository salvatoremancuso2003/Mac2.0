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
public class C_FreeTax_BranchCausal_value  implements Comparable<C_FreeTax_BranchCausal_value>{
    
    
    
    
    String id_filiale, de_filiale,dataDa,dataA;

    String causal, branch, volPrevYear, volYear, volVal, volPerc, qtyPrevYear, qtyYear, qtyVal, qtyPerc;
    
    String volGMPrevYear, volGMYear, volGMVal, volGMPerc;
    
    String de_causal;
    
    
    
    boolean valuta;
    
    ArrayList<C_FreeTax_BranchCausal_value> dati;

    /**
     *
     * @return
     */
    public String getVolGMPrevYear() {
        return volGMPrevYear;
    }

    /**
     *
     * @param volGMPrevYear
     */
    public void setVolGMPrevYear(String volGMPrevYear) {
        this.volGMPrevYear = volGMPrevYear;
    }

    /**
     *
     * @return
     */
    public String getVolGMYear() {
        return volGMYear;
    }

    /**
     *
     * @param volGMYear
     */
    public void setVolGMYear(String volGMYear) {
        this.volGMYear = volGMYear;
    }

    /**
     *
     * @return
     */
    public String getVolGMVal() {
        return volGMVal;
    }

    /**
     *
     * @param volGMVal
     */
    public void setVolGMVal(String volGMVal) {
        this.volGMVal = volGMVal;
    }

    /**
     *
     * @return
     */
    public String getVolGMPerc() {
        return volGMPerc;
    }

    /**
     *
     * @param volGMPerc
     */
    public void setVolGMPerc(String volGMPerc) {
        this.volGMPerc = volGMPerc;
    }

    /**
     *
     * @return
     */
    public String getDe_causal() {
        return de_causal;
    }

    /**
     *
     * @param de_causal
     */
    public void setDe_causal(String de_causal) {
        this.de_causal = de_causal;
    }

    /**
     *
     * @return
     */
    public String getBranch() {
        return branch;
    }

    /**
     *
     * @param branch
     */
    public void setBranch(String branch) {
        this.branch = branch;
    }
    
    /**
     *
     * @return
     */
    public boolean isValuta() {
        return valuta;
    }

    /**
     *
     * @param valuta
     */
    public void setValuta(boolean valuta) {
        this.valuta = valuta;
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
    public String getDataDa() {
        return dataDa;
    }

    /**
     *
     * @param dataDa
     */
    public void setDataDa(String dataDa) {
        this.dataDa = dataDa;
    }

    /**
     *
     * @return
     */
    public String getDataA() {
        return dataA;
    }

    /**
     *
     * @param dataA
     */
    public void setDataA(String dataA) {
        this.dataA = dataA;
    }

    /**
     *
     * @return
     */
    public String getCausal() {
        return causal;
    }

    /**
     *
     * @param causal
     */
    public void setCausal(String causal) {
        this.causal = causal;
    }

    /**
     *
     * @return
     */
    public String getVolPrevYear() {
        return volPrevYear;
    }

    /**
     *
     * @param volPrevYear
     */
    public void setVolPrevYear(String volPrevYear) {
        this.volPrevYear = volPrevYear;
    }

    /**
     *
     * @return
     */
    public String getVolYear() {
        return volYear;
    }

    /**
     *
     * @param volYear
     */
    public void setVolYear(String volYear) {
        this.volYear = volYear;
    }

    /**
     *
     * @return
     */
    public String getVolVal() {
        return volVal;
    }

    /**
     *
     * @param volVal
     */
    public void setVolVal(String volVal) {
        this.volVal = volVal;
    }

    /**
     *
     * @return
     */
    public String getVolPerc() {
        return volPerc;
    }

    /**
     *
     * @param volPerc
     */
    public void setVolPerc(String volPerc) {
        this.volPerc = volPerc;
    }

    /**
     *
     * @return
     */
    public String getQtyPrevYear() {
        return qtyPrevYear;
    }

    /**
     *
     * @param qtyPrevYear
     */
    public void setQtyPrevYear(String qtyPrevYear) {
        this.qtyPrevYear = qtyPrevYear;
    }

    /**
     *
     * @return
     */
    public String getQtyYear() {
        return qtyYear;
    }

    /**
     *
     * @param qtyYear
     */
    public void setQtyYear(String qtyYear) {
        this.qtyYear = qtyYear;
    }

    /**
     *
     * @return
     */
    public String getQtyVal() {
        return qtyVal;
    }

    /**
     *
     * @param qtyVal
     */
    public void setQtyVal(String qtyVal) {
        this.qtyVal = qtyVal;
    }

    /**
     *
     * @return
     */
    public String getQtyPerc() {
        return qtyPerc;
    }

    /**
     *
     * @param qtyPerc
     */
    public void setQtyPerc(String qtyPerc) {
        this.qtyPerc = qtyPerc;
    }

    /**
     *
     * @return
     */
    public ArrayList<C_FreeTax_BranchCausal_value> getDati() {
        return dati;
    }

    /**
     *
     * @param dati
     */
    public void setDati(ArrayList<C_FreeTax_BranchCausal_value> dati) {
        this.dati = dati;
    }
    
    @Override
    public int compareTo(C_FreeTax_BranchCausal_value o) {
        return this.getDe_causal().compareTo(o.getDe_causal());
    }
    
}
