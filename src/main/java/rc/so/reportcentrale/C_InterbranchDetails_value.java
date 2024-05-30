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
public class C_InterbranchDetails_value {
     String currency, kind, no, qty, rate, total;
     String currencyfrom, kindfrom, nofrom, qtyfrom, ratefrom, totalfrom;

    String code;

    ArrayList<C_InterbranchDetails_value> dati;

    /**
     *
     * @return
     */
    public String getCurrencyfrom() {
        return currencyfrom;
    }

    /**
     *
     * @param currencyfrom
     */
    public void setCurrencyfrom(String currencyfrom) {
        this.currencyfrom = currencyfrom;
    }

    /**
     *
     * @return
     */
    public String getKindfrom() {
        return kindfrom;
    }

    /**
     *
     * @param kindfrom
     */
    public void setKindfrom(String kindfrom) {
        this.kindfrom = kindfrom;
    }

    /**
     *
     * @return
     */
    public String getNofrom() {
        return nofrom;
    }

    /**
     *
     * @param nofrom
     */
    public void setNofrom(String nofrom) {
        this.nofrom = nofrom;
    }

    /**
     *
     * @return
     */
    public String getQtyfrom() {
        return qtyfrom;
    }

    /**
     *
     * @param qtyfrom
     */
    public void setQtyfrom(String qtyfrom) {
        this.qtyfrom = qtyfrom;
    }

    /**
     *
     * @return
     */
    public String getRatefrom() {
        return ratefrom;
    }

    /**
     *
     * @param ratefrom
     */
    public void setRatefrom(String ratefrom) {
        this.ratefrom = ratefrom;
    }

    /**
     *
     * @return
     */
    public String getTotalfrom() {
        return totalfrom;
    }

    /**
     *
     * @param totalfrom
     */
    public void setTotalfrom(String totalfrom) {
        this.totalfrom = totalfrom;
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
    public String getKind() {
        return kind;
    }

    /**
     *
     * @param kind
     */
    public void setKind(String kind) {
        this.kind = kind;
    }

    /**
     *
     * @return
     */
    public String getNo() {
        return no;
    }

    /**
     *
     * @param no
     */
    public void setNo(String no) {
        this.no = no;
    }

    /**
     *
     * @return
     */
    public String getQty() {
        return qty;
    }

    /**
     *
     * @param qty
     */
    public void setQty(String qty) {
        this.qty = qty;
    }

    /**
     *
     * @return
     */
    public String getRate() {
        return rate;
    }

    /**
     *
     * @param rate
     */
    public void setRate(String rate) {
        this.rate = rate;
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
