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
public class C_CustomerCareRefund_value {

    String dataDa, dataA;

    String id_filiale, de_filiale;

    String refunddate = "";
    String transactiondate ="";
    String usercode ="";
    String refundusercode ="";
    String branch ="";
    String transaction ="";
    String typeoperation ="";
    String amount ="";
    String commission ="";
    String typerefund ="";
    String refundmethod ="";
    String refundamount ="";
    String authcode ="";
    String note ="";
    
    ArrayList<C_CustomerCareRefund_value> dati;
    
    /**
     *
     * @param ti
     * @return
     */
    public String format_tipo(String ti){
        if(ti.equals("CO")){
            return "Complete";
        }else{
            return "Partial";
        }
    }
    
    /**
     *
     * @param ti
     * @return
     */
    public String format_transf(String ti){
        if(ti.equals("BO")){
            return "Bank Transfer";
        }else{
            return "Branch";
        }
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
    public String getRefunddate() {
        return refunddate;
    }

    /**
     *
     * @param refunddate
     */
    public void setRefunddate(String refunddate) {
        this.refunddate = refunddate;
    }

    /**
     *
     * @return
     */
    public String getTransactiondate() {
        return transactiondate;
    }

    /**
     *
     * @param transactiondate
     */
    public void setTransactiondate(String transactiondate) {
        this.transactiondate = transactiondate;
    }

    /**
     *
     * @return
     */
    public String getUsercode() {
        return usercode;
    }

    /**
     *
     * @param usercode
     */
    public void setUsercode(String usercode) {
        this.usercode = usercode;
    }

    /**
     *
     * @return
     */
    public String getRefundusercode() {
        return refundusercode;
    }

    /**
     *
     * @param refundusercode
     */
    public void setRefundusercode(String refundusercode) {
        this.refundusercode = refundusercode;
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
    public String getTypeoperation() {
        return typeoperation;
    }

    /**
     *
     * @param typeoperation
     */
    public void setTypeoperation(String typeoperation) {
        this.typeoperation = typeoperation;
    }

    /**
     *
     * @return
     */
    public String getAmount() {
        return amount;
    }

    /**
     *
     * @param amount
     */
    public void setAmount(String amount) {
        this.amount = amount;
    }

    /**
     *
     * @return
     */
    public String getCommission() {
        return commission;
    }

    /**
     *
     * @param commission
     */
    public void setCommission(String commission) {
        this.commission = commission;
    }

    /**
     *
     * @return
     */
    public String getTyperefund() {
        return typerefund;
    }

    /**
     *
     * @param typerefund
     */
    public void setTyperefund(String typerefund) {
        this.typerefund = typerefund;
    }

    /**
     *
     * @return
     */
    public String getRefundmethod() {
        return refundmethod;
    }

    /**
     *
     * @param refundmethod
     */
    public void setRefundmethod(String refundmethod) {
        this.refundmethod = refundmethod;
    }

    /**
     *
     * @return
     */
    public String getRefundamount() {
        return refundamount;
    }

    /**
     *
     * @param refundamount
     */
    public void setRefundamount(String refundamount) {
        this.refundamount = refundamount;
    }

    /**
     *
     * @return
     */
    public String getAuthcode() {
        return authcode;
    }

    /**
     *
     * @param authcode
     */
    public void setAuthcode(String authcode) {
        this.authcode = authcode;
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
    public ArrayList<C_CustomerCareRefund_value> getDati() {
        return dati;
    }

    /**
     *
     * @param dati
     */
    public void setDati(ArrayList<C_CustomerCareRefund_value> dati) {
        this.dati = dati;
    }

}
