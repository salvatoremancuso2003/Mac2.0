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
public class C_TrimestraleCZK_value {
    String dataDa,dataA,valuta, acquisti, vendita;   
    String branch;
    
    ArrayList<C_TrimestraleCZK_value> dati;

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
    public String getAcquisti() {
        return acquisti;
    }

    /**
     *
     * @param acquisti
     */
    public void setAcquisti(String acquisti) {
        this.acquisti = acquisti;
    }

    /**
     *
     * @return
     */
    public String getVendita() {
        return vendita;
    }

    /**
     *
     * @param vendita
     */
    public void setVendita(String vendita) {
        this.vendita = vendita;
    }

    /**
     *
     * @return
     */
    public ArrayList<C_TrimestraleCZK_value> getDati() {
        return dati;
    }

    /**
     *
     * @param dati
     */
    public void setDati(ArrayList<C_TrimestraleCZK_value> dati) {
        this.dati = dati;
    }

}
