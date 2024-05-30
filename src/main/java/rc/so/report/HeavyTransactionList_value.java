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
public class HeavyTransactionList_value {
    ArrayList<HeavyTransactionList_value> dati;   
    
    String id_filiale, de_filiale,clinetname,address,birthplaceday,taxcode,documentnumber,documentdataente,currencylocale;
  
    ArrayList<TransactionforHeavyTransactionList> transactionlist;
    
    String codicenazione="";

    /**
     *
     * @return
     */
    public String getCurrencylocale() {
        return currencylocale;
    }

    /**
     *
     * @param currencylocale
     */
    public void setCurrencylocale(String currencylocale) {
        this.currencylocale = currencylocale;
    }

    /**
     *
     * @return
     */
    public String getCodicenazione() {
        return codicenazione;
    }

    /**
     *
     * @param codicenazione
     */
    public void setCodicenazione(String codicenazione) {
        this.codicenazione = codicenazione;
    }
    
    /**
     *
     * @return
     */
    public ArrayList<HeavyTransactionList_value> getDati() {
        return dati;
    }

    /**
     *
     * @param dati
     */
    public void setDati(ArrayList<HeavyTransactionList_value> dati) {
        this.dati = dati;
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
    public String getClinetname() {
        return clinetname;
    }

    /**
     *
     * @param clinetname
     */
    public void setClinetname(String clinetname) {
        this.clinetname = clinetname;
    }

    /**
     *
     * @return
     */
    public String getAddress() {
        return address;
    }

    /**
     *
     * @param address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     *
     * @return
     */
    public String getBirthplaceday() {
        return birthplaceday;
    }

    /**
     *
     * @param birthplaceday
     */
    public void setBirthplaceday(String birthplaceday) {
        this.birthplaceday = birthplaceday;
    }

    /**
     *
     * @return
     */
    public String getTaxcode() {
        return taxcode;
    }

    /**
     *
     * @param taxcode
     */
    public void setTaxcode(String taxcode) {
        this.taxcode = taxcode;
    }

    /**
     *
     * @return
     */
    public String getDocumentnumber() {
        return documentnumber;
    }

    /**
     *
     * @param documentnumber
     */
    public void setDocumentnumber(String documentnumber) {
        this.documentnumber = documentnumber;
    }

    /**
     *
     * @return
     */
    public String getDocumentdataente() {
        return documentdataente;
    }

    /**
     *
     * @param documentdataente
     */
    public void setDocumentdataente(String documentdataente) {
        this.documentdataente = documentdataente;
    }

    /**
     *
     * @return
     */
    public ArrayList<TransactionforHeavyTransactionList> getTransactionlist() {
        return transactionlist;
    }

    /**
     *
     * @param transactionlist
     */
    public void setTransactionlist(ArrayList<TransactionforHeavyTransactionList> transactionlist) {
        this.transactionlist = transactionlist;
    }

    
}
