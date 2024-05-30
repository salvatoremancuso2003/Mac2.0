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
public class C_CasherOpenCloseError_value {

    String casher, user, safetill, data, operazione, tipo;
    String valuta, supporto, qtautente, qtasistema, cambio, contrdiff, qtadiff, note;

    ArrayList<C_CasherOpenCloseError_value> dati;
    ArrayList<C_CasherOpenCloseError_errore> dettaglierrore;

    String idfiliale, defiliale;

    /**
     *
     * @return
     */
    public String getIdfiliale() {
        return idfiliale;
    }

    /**
     *
     * @param idfiliale
     */
    public void setIdfiliale(String idfiliale) {
        this.idfiliale = idfiliale;
    }

    /**
     *
     * @return
     */
    public String getDefiliale() {
        return defiliale;
    }

    /**
     *
     * @param defiliale
     */
    public void setDefiliale(String defiliale) {
        this.defiliale = defiliale;
    }

    /**
     *
     * @return
     */
    public ArrayList<C_CasherOpenCloseError_errore> getDettaglierrore() {
        return dettaglierrore;
    }

    /**
     *
     * @param dettaglierrore
     */
    public void setDettaglierrore(ArrayList<C_CasherOpenCloseError_errore> dettaglierrore) {
        this.dettaglierrore = dettaglierrore;
    }
    
    /**
     ** Constructor
     */
    public C_CasherOpenCloseError_value() {
    }

    /**
     *
     * @return
     */
    public String getCasher() {
        return casher;
    }

    /**
     *
     * @param casher
     */
    public void setCasher(String casher) {
        this.casher = casher;
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
    public String getValuta() {
        return valuta;
    }

    /**
     *
     * @param valuta
     */
    public void setValuta(String valuta) {
        this.valuta = valuta;
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
    public String getQtautente() {
        return qtautente;
    }

    /**
     *
     * @param qtautente
     */
    public void setQtautente(String qtautente) {
        this.qtautente = qtautente;
    }

    /**
     *
     * @return
     */
    public String getQtasistema() {
        return qtasistema;
    }

    /**
     *
     * @param qtasistema
     */
    public void setQtasistema(String qtasistema) {
        this.qtasistema = qtasistema;
    }

    /**
     *
     * @return
     */
    public String getCambio() {
        return cambio;
    }

    /**
     *
     * @param cambio
     */
    public void setCambio(String cambio) {
        this.cambio = cambio;
    }

    /**
     *
     * @return
     */
    public String getContrdiff() {
        return contrdiff;
    }

    /**
     *
     * @param contrdiff
     */
    public void setContrdiff(String contrdiff) {
        this.contrdiff = contrdiff;
    }

    /**
     *
     * @return
     */
    public String getQtadiff() {
        return qtadiff;
    }

    /**
     *
     * @param qtadiff
     */
    public void setQtadiff(String qtadiff) {
        this.qtadiff = qtadiff;
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
    public ArrayList<C_CasherOpenCloseError_value> getDati() {
        return dati;
    }

    /**
     *
     * @param dati
     */
    public void setDati(ArrayList<C_CasherOpenCloseError_value> dati) {
        this.dati = dati;
    }

}
