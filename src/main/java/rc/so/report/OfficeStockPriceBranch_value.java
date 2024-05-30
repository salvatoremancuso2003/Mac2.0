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
public class OfficeStockPriceBranch_value {
    
    
    
    String id_filiale, de_filiale,currency,decurrency,supporto,qta,medioacq,controvalore,gruppo;
    String localcurrency;
    
    ArrayList<String[]> dati;

    /**
     *
     * @return
     */
    public String getGruppo() {
        return gruppo;
    }

    /**
     *
     * @param gruppo
     */
    public void setGruppo(String gruppo) {
        this.gruppo = gruppo;
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
    public String getDecurrency() {
        return decurrency;
    }

    /**
     *
     * @param decurrency
     */
    public void setDecurrency(String decurrency) {
        this.decurrency = decurrency;
    }

    /**
     *
     * @return
     */
    public String getSupporto() {
        return supporto;
    }

    /**
     *
     * @param supporto
     */
    public void setSupporto(String supporto) {
        this.supporto = supporto;
    }

    /**
     *
     * @return
     */
    public String getQta() {
        return (qta);
    }

    /**
     *
     * @return
     */
    public String getQtaSenzaFormattazione() {
        return (qta);
    }
    
    /**
     *
     * @param qta
     */
    public void setQta(String qta) {
        this.qta = qta;
    }

    /**
     *
     * @return
     */
    public String getMedioacq() {
        return (medioacq);
    }

    /**
     *
     * @param medioacq
     */
    public void setMedioacq(String medioacq) {
        this.medioacq = medioacq;
    }

    /**
     *
     * @return
     */
    public String getControvalore() {
        return (controvalore);
    }

    /**
     *
     * @return
     */
    public String getControvaloreSenzaFormattazione() {
        return (controvalore.replace(",", "."));
    }

    /**
     *
     * @param controvalore
     */
    public void setControvalore(String controvalore) {
        this.controvalore = controvalore;
    }

    /**
     *
     * @return
     */
    public ArrayList<String[]> getDati() {
        return dati;
    }

    /**
     *
     * @param dati
     */
    public void setDati(ArrayList<String[]> dati) {
        this.dati = dati;
    }

 

    
    
}
