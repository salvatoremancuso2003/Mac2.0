/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rc.so.report;

import java.util.ArrayList;

/**
 *
 * @author rcosco
 */
public class Openclose_Error_value {
    String id_filiale, de_filiale,user,safetill,data,operazione,tipo;    
    String cod, type, currency, kind, nc, pos, total_diff, rate, till,note, quantityUser, quantitySystem, diffContr, diffAmount;
    String localamount;
    String amountuser,amountsystem,quantitydiff;
            
    String ncprice;
    
    ArrayList<Openclose_Error_value_stock> dati;

    /**
     *
     * @return
     */
    public String getNcprice() {
        return ncprice;
    }

    /**
     *
     * @param ncprice
     */
    public void setNcprice(String ncprice) {
        this.ncprice = ncprice;
    }
    
    /**
     *
     * @return
     */
    public String getAmountuser() {
        return amountuser;
    }

    /**
     *
     * @param amountuser
     */
    public void setAmountuser(String amountuser) {
        this.amountuser = amountuser;
    }

    /**
     *
     * @return
     */
    public String getAmountsystem() {
        return amountsystem;
    }

    /**
     *
     * @param amountsystem
     */
    public void setAmountsystem(String amountsystem) {
        this.amountsystem = amountsystem;
    }

    /**
     *
     * @return
     */
    public String getQuantitydiff() {
        return quantitydiff;
    }

    /**
     *
     * @param quantitydiff
     */
    public void setQuantitydiff(String quantitydiff) {
        this.quantitydiff = quantitydiff;
    }
    
    /**
     *
     * @return
     */
    public String getLocalamount() {
        return localamount;
    }

    /**
     *
     * @param localamount
     */
    public void setLocalamount(String localamount) {
        this.localamount = localamount;
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
    public String getSafetill() {
        return safetill;
    }

    /**
     *
     * @param safetill
     */
    public void setSafetill(String safetill) {
        this.safetill = safetill;
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
    public String getOperazione() {
        return operazione;
    }

    /**
     *
     * @param operazione
     */
    public void setOperazione(String operazione) {
        this.operazione = operazione;
    }

    /**
     *
     * @return
     */
    public String getTipo() {
        return tipo;
    }

    /**
     *
     * @param tipo
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    /**
     *
     * @return
     */
    public String getCod() {
        return cod;
    }

    /**
     *
     * @param cod
     */
    public void setCod(String cod) {
        this.cod = cod;
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
    public String getNc() {
        return nc;
    }

    /**
     *
     * @param nc
     */
    public void setNc(String nc) {
        this.nc = nc;
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
    public String getTotal_diff() {
        return total_diff;
    }

    /**
     *
     * @param total_diff
     */
    public void setTotal_diff(String total_diff) {
        this.total_diff = total_diff;
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
    public String getTill() {
        return till;
    }

    /**
     *
     * @param till
     */
    public void setTill(String till) {
        this.till = till;
    }

    /**
     *
     * @return
     */
    public ArrayList<Openclose_Error_value_stock> getDati() {
        return dati;
    }

    /**
     *
     * @param dati
     */
    public void setDati(ArrayList<Openclose_Error_value_stock> dati) {
        this.dati = dati;
    }

    /**
     *
     * @return
     */
    public String getNote() {
        return note;
    }

    /**
     *
     * @param note
     */
    public void setNote(String note) {
        this.note = note;
    }

    /**
     *
     * @return
     */
    public String getQuantityUser() {
        return quantityUser;
    }

    /**
     *
     * @param quantityUser
     */
    public void setQuantityUser(String quantityUser) {
        this.quantityUser = quantityUser;
    }

    /**
     *
     * @return
     */
    public String getQuantitySystem() {
        return quantitySystem;
    }

    /**
     *
     * @param quantitySystem
     */
    public void setQuantitySystem(String quantitySystem) {
        this.quantitySystem = quantitySystem;
    }

    /**
     *
     * @return
     */
    public String getDiffContr() {
        return diffContr;
    }

    /**
     *
     * @param diffContr
     */
    public void setDiffContr(String diffContr) {
        this.diffContr = diffContr;
    }

    /**
     *
     * @return
     */
    public String getDiffAmount() {
        return diffAmount;
    }

    /**
     *
     * @param diffAmount
     */
    public void setDiffAmount(String diffAmount) {
        this.diffAmount = diffAmount;
    }
}
