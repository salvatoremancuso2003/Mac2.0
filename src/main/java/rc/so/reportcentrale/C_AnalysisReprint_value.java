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
public class C_AnalysisReprint_value implements Comparable{
    
    String dataDa, dataA;
    
    String id_filiale, de_filiale;

    String date, dateOperation, userCod, user, branch, fidelityCode, till, typeOperation, amount, commission, note,reprintusercode,reprintuser;
    
    ArrayList<C_AnalysisReprint_value> dati;

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
    public String getFidelityCode() {
        return fidelityCode;
    }

    /**
     *
     * @param fidelityCode
     */
    public void setFidelityCode(String fidelityCode) {
        this.fidelityCode = fidelityCode;
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
    public String getTypeOperation() {
        return typeOperation;
    }

    /**
     *
     * @param typeOperation
     */
    public void setTypeOperation(String typeOperation) {
        this.typeOperation = typeOperation;
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
    public ArrayList<C_AnalysisReprint_value> getDati() {
        return dati;
    }

    /**
     *
     * @param dati
     */
    public void setDati(ArrayList<C_AnalysisReprint_value> dati) {
        this.dati = dati;
    }

    @Override
    public int compareTo(Object o) {
        C_AnalysisReprint_value ca = (C_AnalysisReprint_value)o;
        
        if(this.getFidelityCode().equals(ca.getFidelityCode())){                        
            if(this.getUserCod().equals(ca.getUserCod()) ){                
                return 0;                
            }
            else{                               
                return this.getUserCod().compareTo(ca.getUserCod());
            }            
        }
        else{
            return this.getFidelityCode().compareTo(ca.getFidelityCode());
        }
        
    }

   
}
