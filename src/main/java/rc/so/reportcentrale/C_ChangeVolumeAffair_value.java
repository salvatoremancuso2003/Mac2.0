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
public class C_ChangeVolumeAffair_value {
    
    String dataDa, dataA;
    
    String id_filiale, de_filiale;

    String branch, date, buy,sell,total,noTrans;
    
    ArrayList<C_ChangeVolumeAffair_value> dati;

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
    public String getDate() {
        return date;
    }

    /**
     *
     * @param date
     */
    public void setDate(String date) {
        this.date = date;
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
    public String getTotal() {
        return total;
    }

    /**
     *
     * @param total
     */
    public void setTotal(String total) {
        this.total = total;
    }

    /**
     *
     * @return
     */
    public String getNoTrans() {
        return noTrans;
    }

    /**
     *
     * @param noTrans
     */
    public void setNoTrans(String noTrans) {
        this.noTrans = noTrans;
    }

    /**
     *
     * @return
     */
    public ArrayList<C_ChangeVolumeAffair_value> getDati() {
        return dati;
    }

    /**
     *
     * @param dati
     */
    public void setDati(ArrayList<C_ChangeVolumeAffair_value> dati) {
        this.dati = dati;
    }

    
   
}
