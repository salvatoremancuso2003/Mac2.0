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
public class C_freeTaxPivotTotale_value {
    
    
    String id_filiale, de_filiale,dataDa,dataA;

    String causal, branch, volPrevYear, volYear, volVal, volPerc, qtyPrevYear, qtyYear, qtyVal, qtyPerc;
    
    boolean valuta;
    
    ArrayList<C_freeTaxPivotTotale_value> dati;
    
    ArrayList<String> values;

    /**
     *
     * @return
     */
    public ArrayList<String> getValues() {
        return values;
    }

    /**
     *
     * @param values
     */
    public void setValues(ArrayList<String> values) {
        this.values = values;
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
    public ArrayList<C_freeTaxPivotTotale_value> getDati() {
        return dati;
    }

    /**
     *
     * @param dati
     */
    public void setDati(ArrayList<C_freeTaxPivotTotale_value> dati) {
        this.dati = dati;
    }

    
}
