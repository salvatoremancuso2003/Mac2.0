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
public class C_Interbranch_value {
    
    
    String dataDa,dataA;

    String state, dateFrom, branchFrom, totFrom, dateTo, branchTo, totTo, difference,totransfno,fromtransno; 
    
    String code;
    String codefrom;
    
    String type;
    
    ArrayList<C_InterbranchDetails_value> dati;
    ArrayList<C_Interbranch_value> dati_2;

    /**
     *
     * @return
     */
    public ArrayList<C_Interbranch_value> getDati_2() {
        return dati_2;
    }

    /**
     *
     * @param dati_2
     */
    public void setDati_2(ArrayList<C_Interbranch_value> dati_2) {
        this.dati_2 = dati_2;
    }
        
    
    
    String localcurrency,foreigncurrency;

    /**
     *
     * @return
     */
    public String getCodefrom() {
        return codefrom;
    }

    /**
     *
     * @param codefrom
     */
    public void setCodefrom(String codefrom) {
        this.codefrom = codefrom;
    }

    /**
     *
     * @return
     */
    public String getLocalcurrency() {
        return localcurrency;
    }

    /**
     *
     * @param localcurrency
     */
    public void setLocalcurrency(String localcurrency) {
        this.localcurrency = localcurrency;
    }

    /**
     *
     * @return
     */
    public String getForeigncurrency() {
        return foreigncurrency;
    }

    /**
     *
     * @param foreigncurrency
     */
    public void setForeigncurrency(String foreigncurrency) {
        this.foreigncurrency = foreigncurrency;
    }
    
    /**
     *
     * @return
     */
    public String getTotransfno() {
        return totransfno;
    }

    /**
     *
     * @param totransfno
     */
    public void setTotransfno(String totransfno) {
        this.totransfno = totransfno;
    }

    /**
     *
     * @return
     */
    public String getFromtransno() {
        return fromtransno;
    }

    /**
     *
     * @param fromtransno
     */
    public void setFromtransno(String fromtransno) {
        this.fromtransno = fromtransno;
    }
    
    /**
     *
     * @return
     */
    public String getCode() {
        return code;
    }

    /**
     *
     * @param code
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     *
     * @return
     */
    public String getType() {
        return type;
    }

    /**
     *
     * @param type
     */
    public void setType(String type) {
        this.type = type;
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
    public String getState() {
        return state;
    }

    /**
     *
     * @param state
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     *
     * @return
     */
    public String getDateFrom() {
        return dateFrom;
    }

    /**
     *
     * @param dateFrom
     */
    public void setDateFrom(String dateFrom) {
        this.dateFrom = dateFrom;
    }

    /**
     *
     * @return
     */
    public String getBranchFrom() {
        return branchFrom;
    }

    /**
     *
     * @param branchFrom
     */
    public void setBranchFrom(String branchFrom) {
        this.branchFrom = branchFrom;
    }

    /**
     *
     * @return
     */
    public String getTotFrom() {
        return totFrom;
    }

    /**
     *
     * @param totFrom
     */
    public void setTotFrom(String totFrom) {
        this.totFrom = totFrom;
    }

    /**
     *
     * @return
     */
    public String getDateTo() {
        return dateTo;
    }

    /**
     *
     * @param dateTo
     */
    public void setDateTo(String dateTo) {
        this.dateTo = dateTo;
    }

    /**
     *
     * @return
     */
    public String getBranchTo() {
        return branchTo;
    }

    /**
     *
     * @param branchTo
     */
    public void setBranchTo(String branchTo) {
        this.branchTo = branchTo;
    }

    /**
     *
     * @return
     */
    public String getTotTo() {
        return totTo;
    }

    /**
     *
     * @param totTo
     */
    public void setTotTo(String totTo) {
        this.totTo = totTo;
    }

    /**
     *
     * @return
     */
    public String getDifference() {
        return difference;
    }

    /**
     *
     * @param difference
     */
    public void setDifference(String difference) {
        this.difference = difference;
    }

    /**
     *
     * @return
     */
    public ArrayList<C_InterbranchDetails_value> getDati() {
        return dati;
    }

    /**
     *
     * @param dati
     */
    public void setDati(ArrayList<C_InterbranchDetails_value> dati) {
        this.dati = dati;
    }

}
