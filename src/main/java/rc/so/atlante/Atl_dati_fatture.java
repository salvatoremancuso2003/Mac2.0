/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rc.so.atlante;

import java.util.List;

/**
 *
 * @author rcosco
 */
public class Atl_dati_fatture {

    String cod, Idreg, branchid, sezionale, datereg, numreg, tipomov, stato, clientcode,dateoper;

    List<Atl_details_dati_fatture> details;
    List<Atl_detailsiva_dati_fatture> detailsiva;

    /**
     *
     * @param cod
     * @param Idreg
     * @param branchid
     * @param sezionale
     * @param datereg
     * @param numreg
     * @param tipomov
     * @param stato
     * @param dateoper
     * @param clientcode
     * @param details
     * @param detailsiva
     */
    public Atl_dati_fatture(String cod, String Idreg, String branchid, String sezionale, String datereg, String numreg, String tipomov, String stato, String dateoper, 
            String clientcode,
            List<Atl_details_dati_fatture> details,
            List<Atl_detailsiva_dati_fatture> detailsiva) {
        this.cod = cod;
        this.Idreg = Idreg;
        this.branchid = branchid;
        this.sezionale = sezionale;
        this.datereg = datereg;
        this.numreg = numreg;
        this.tipomov = tipomov;
        this.details = details;
        this.detailsiva = detailsiva;
        this.stato = stato;
        this.dateoper = dateoper;
        this.clientcode = clientcode;
    }

    /**
     *
     * @return
     */
    public String getClientcode() {
        return clientcode;
    }

    /**
     *
     * @param clientcode
     */
    public void setClientcode(String clientcode) {
        this.clientcode = clientcode;
    }
    
    /**
     *
     * @return
     */
    public String controlli() {
        return "OK";
    }
    
    /**
     *
     * @return
     */
    public String getStato() {
        return stato;
    }

    /**
     *
     * @param stato
     */
    public void setStato(String stato) {
        this.stato = stato;
    }

    /**
     *
     * @return
     */
    public String getDateoper() {
        return dateoper;
    }

    /**
     *
     * @param dateoper
     */
    public void setDateoper(String dateoper) {
        this.dateoper = dateoper;
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
    public String getIdreg() {
        return Idreg;
    }

    /**
     *
     * @param Idreg
     */
    public void setIdreg(String Idreg) {
        this.Idreg = Idreg;
    }

    /**
     *
     * @return
     */
    public String getBranchid() {
        return branchid;
    }

    /**
     *
     * @param branchid
     */
    public void setBranchid(String branchid) {
        this.branchid = branchid;
    }

    /**
     *
     * @return
     */
    public String getSezionale() {
        return sezionale;
    }

    /**
     *
     * @param sezionale
     */
    public void setSezionale(String sezionale) {
        this.sezionale = sezionale;
    }

    /**
     *
     * @return
     */
    public String getDatereg() {
        return datereg;
    }

    /**
     *
     * @param datereg
     */
    public void setDatereg(String datereg) {
        this.datereg = datereg;
    }

    /**
     *
     * @return
     */
    public String getNumreg() {
        return numreg;
    }

    /**
     *
     * @param numreg
     */
    public void setNumreg(String numreg) {
        this.numreg = numreg;
    }

    /**
     *
     * @return
     */
    public String getTipomov() {
        return tipomov;
    }

    /**
     *
     * @param tipomov
     */
    public void setTipomov(String tipomov) {
        this.tipomov = tipomov;
    }

    /**
     *
     * @return
     */
    public List<Atl_details_dati_fatture> getDetails() {
        return details;
    }

    /**
     *
     * @param details
     */
    public void setDetails(List<Atl_details_dati_fatture> details) {
        this.details = details;
    }

    /**
     *
     * @return
     */
    public List<Atl_detailsiva_dati_fatture> getDetailsiva() {
        return detailsiva;
    }

    /**
     *
     * @param detailsiva
     */
    public void setDetailsiva(List<Atl_detailsiva_dati_fatture> detailsiva) {
        this.detailsiva = detailsiva;
    }

}
