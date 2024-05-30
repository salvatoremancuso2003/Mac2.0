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
public class C_CloseBranch_value {

    String data_a, data_da;
    String data;
    String codBranch, descrBranch, hClose, purch, sales, pos, fx, accCC, cashadvance, cashonprem;

    ArrayList<C_CloseBranch_value> dati;
    ArrayList<String> dati_string;
    ArrayList<String> alcolonne;

    /**
     *
     * @return
     */
    public ArrayList<String> getDati_string() {
        return dati_string;
    }

    /**
     *
     * @param dati_string
     */
    public void setDati_string(ArrayList<String> dati_string) {
        this.dati_string = dati_string;
    }
    
    /**
     *
     * @return
     */
    public ArrayList<String> getAlcolonne() {
        return alcolonne;
    }

    /**
     *
     * @param alcolonne
     */
    public void setAlcolonne(ArrayList<String> alcolonne) {
        this.alcolonne = alcolonne;
    }

    /**
     *
     * @return
     */
    public String getCashadvance() {
        return cashadvance;
    }

    /**
     *
     * @param cashadvance
     */
    public void setCashadvance(String cashadvance) {
        this.cashadvance = cashadvance;
    }

    /**
     *
     * @return
     */
    public String getCashonprem() {
        return cashonprem;
    }

    /**
     *
     * @param cashonprem
     */
    public void setCashonprem(String cashonprem) {
        this.cashonprem = cashonprem;
    }

    /**
     *
     * @return
     */
    public String getData_a() {
        return data_a;
    }

    /**
     *
     * @param data_a
     */
    public void setData_a(String data_a) {
        this.data_a = data_a;
    }

    /**
     *
     * @return
     */
    public String getData_da() {
        return data_da;
    }

    /**
     *
     * @param data_da
     */
    public void setData_da(String data_da) {
        this.data_da = data_da;
    }

    /**
     *
     * @return
     */
    public String getData() {
        return data;
    }

    /**
     *
     * @param data
     */
    public void setData(String data) {
        this.data = data;
    }

    /**
     *
     * @return
     */
    public String getCodBranch() {
        return codBranch;
    }

    /**
     *
     * @param codBranch
     */
    public void setCodBranch(String codBranch) {
        this.codBranch = codBranch;
    }

    /**
     *
     * @return
     */
    public String getDescrBranch() {
        return descrBranch;
    }

    /**
     *
     * @param descrBranch
     */
    public void setDescrBranch(String descrBranch) {
        this.descrBranch = descrBranch;
    }

    /**
     *
     * @return
     */
    public String gethClose() {
        return hClose;
    }

    /**
     *
     * @param hClose
     */
    public void sethClose(String hClose) {
        this.hClose = hClose;
    }

    /**
     *
     * @return
     */
    public String getPurch() {
        return purch;
    }

    /**
     *
     * @param purch
     */
    public void setPurch(String purch) {
        this.purch = purch;
    }

    /**
     *
     * @return
     */
    public String getSales() {
        return sales;
    }

    /**
     *
     * @param sales
     */
    public void setSales(String sales) {
        this.sales = sales;
    }

    /**
     *
     * @return
     */
    public String getPos() {
        return pos;
    }

    /**
     *
     * @param pos
     */
    public void setPos(String pos) {
        this.pos = pos;
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

    /**
     *
     * @return
     */
    public String getAccCC() {
        return accCC;
    }

    /**
     *
     * @param accCC
     */
    public void setAccCC(String accCC) {
        this.accCC = accCC;
    }

    /**
     *
     * @return
     */
    public ArrayList<C_CloseBranch_value> getDati() {
        return dati;
    }

    /**
     *
     * @param dati
     */
    public void setDati(ArrayList<C_CloseBranch_value> dati) {
        this.dati = dati;
    }

}
