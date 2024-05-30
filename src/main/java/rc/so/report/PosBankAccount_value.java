/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rc.so.report;

import java.util.ArrayList;

/**
 *
 * @author srotella
 */
public class PosBankAccount_value {
    
    
    String id_filiale, de_filiale,user, periodo;
    String date, pos, nTransCA, amountCA, nTransCC, amountCC, nTransBank, amountBank;
    String dateBA, posBA;

    ArrayList<PosBankAccount_value> dati;

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
    public String getUser() {
        return user;
    }

    /**
     *
     * @param user
     */
    public void setUser(String user) {
        this.user = user;
    }

    /**
     *
     * @return
     */
    public String getPeriodo() {
        return periodo;
    }

    /**
     *
     * @param periodo
     */
    public void setPeriodo(String periodo) {
        this.periodo = periodo;
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
    public String getnTransCA() {
        return nTransCA;
    }

    /**
     *
     * @param nTransCA
     */
    public void setnTransCA(String nTransCA) {
        this.nTransCA = nTransCA;
    }

    /**
     *
     * @return
     */
    public String getAmountCA() {
        return amountCA;
    }

    /**
     *
     * @param amountCA
     */
    public void setAmountCA(String amountCA) {
        this.amountCA = amountCA;
    }

    /**
     *
     * @return
     */
    public String getnTransCC() {
        return nTransCC;
    }

    /**
     *
     * @param nTransCC
     */
    public void setnTransCC(String nTransCC) {
        this.nTransCC = nTransCC;
    }

    /**
     *
     * @return
     */
    public String getAmountCC() {
        return amountCC;
    }

    /**
     *
     * @param amountCC
     */
    public void setAmountCC(String amountCC) {
        this.amountCC = amountCC;
    }

    /**
     *
     * @return
     */
    public String getnTransBank() {
        return nTransBank;
    }

    /**
     *
     * @param nTransBank
     */
    public void setnTransBank(String nTransBank) {
        this.nTransBank = nTransBank;
    }

    /**
     *
     * @return
     */
    public String getAmountBank() {
        return amountBank;
    }

    /**
     *
     * @param amountBank
     */
    public void setAmountBank(String amountBank) {
        this.amountBank = amountBank;
    }

    /**
     *
     * @return
     */
    public String getDateBA() {
        return dateBA;
    }

    /**
     *
     * @param dateBA
     */
    public void setDateBA(String dateBA) {
        this.dateBA = dateBA;
    }

    /**
     *
     * @return
     */
    public String getPosBA() {
        return posBA;
    }

    /**
     *
     * @param posBA
     */
    public void setPosBA(String posBA) {
        this.posBA = posBA;
    }

    /**
     *
     * @return
     */
    public ArrayList<PosBankAccount_value> getDati() {
        return dati;
    }

    /**
     *
     * @param dati
     */
    public void setDati(ArrayList<PosBankAccount_value> dati) {
        this.dati = dati;
    }

 
    
    
}
