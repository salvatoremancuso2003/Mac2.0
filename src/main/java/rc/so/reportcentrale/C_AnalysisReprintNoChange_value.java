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
public class C_AnalysisReprintNoChange_value implements Comparable{
    
    String dataDa, dataA;
    
    String id_filiale, de_filiale;

    String date, dateOperation, userCod, user, branch, transaction, support, typeTransaction, causal, qty, price, total,reprintusercode,reprintuser;
    
    ArrayList<C_AnalysisReprintNoChange_value> dati;

    /**
     *
     * @return
     */
    public String getReprintusercode() {
        return reprintusercode;
    }

    /**
     *
     * @param reprintusercode
     */
    public void setReprintusercode(String reprintusercode) {
        this.reprintusercode = reprintusercode;
    }

    /**
     *
     * @return
     */
    public String getReprintuser() {
        return reprintuser;
    }

    /**
     *
     * @param reprintuser
     */
    public void setReprintuser(String reprintuser) {
        this.reprintuser = reprintuser;
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
    public String getDateOperation() {
        return dateOperation;
    }

    /**
     *
     * @param dateOperation
     */
    public void setDateOperation(String dateOperation) {
        this.dateOperation = dateOperation;
    }

    /**
     *
     * @return
     */
    public String getUserCod() {
        return userCod;
    }

    /**
     *
     * @param userCod
     */
    public void setUserCod(String userCod) {
        this.userCod = userCod;
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
    public String getTransaction() {
        return transaction;
    }

    /**
     *
     * @param transaction
     */
    public void setTransaction(String transaction) {
        this.transaction = transaction;
    }

    /**
     *
     * @return
     */
    public String getSupport() {
        return support;
    }

    /**
     *
     * @param support
     */
    public void setSupport(String support) {
        this.support = support;
    }

    /**
     *
     * @return
     */
    public String getTypeTransaction() {
        return typeTransaction;
    }

    /**
     *
     * @param typeTransaction
     */
    public void setTypeTransaction(String typeTransaction) {
        this.typeTransaction = typeTransaction;
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
    public String getPrice() {
        return price;
    }

    /**
     *
     * @param price
     */
    public void setPrice(String price) {
        this.price = price;
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
    public ArrayList<C_AnalysisReprintNoChange_value> getDati() {
        return dati;
    }

    /**
     *
     * @param dati
     */
    public void setDati(ArrayList<C_AnalysisReprintNoChange_value> dati) {
        this.dati = dati;
    }

   

    @Override
    public int compareTo(Object o) {
        C_AnalysisReprintNoChange_value ca = (C_AnalysisReprintNoChange_value)o;
        
        if(this.getTransaction().equals(ca.getTransaction())){                        
            if(this.getUserCod().equals(ca.getUserCod()) ){                
                return 0;                
            }
            else{                               
                return this.getUserCod().compareTo(ca.getUserCod());
            }            
        }
        else{
            return this.getTransaction().compareTo(ca.getTransaction());
        }
        
    }

}
