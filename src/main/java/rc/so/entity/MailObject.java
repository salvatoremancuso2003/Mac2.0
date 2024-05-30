/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rc.so.entity;

/**
 *
 * @author rcosco
 */
public class MailObject {
    
    String OGGETTO, IDPRENOTAZIONE, INTRO, CORPO, RIEPILOGO, ALTREINFO, NVERDE, CHIUSURA;

    /**
     * Constructor
     */
    public MailObject() {
    }
    
    /**
     *
     * @return
     */
    public String getOGGETTO() {
        return OGGETTO;
    }

    /**
     *
     * @param OGGETTO
     */
    public void setOGGETTO(String OGGETTO) {
        this.OGGETTO = OGGETTO;
    }

    /**
     *
     * @return
     */
    public String getIDPRENOTAZIONE() {
        return IDPRENOTAZIONE;
    }

    /**
     *
     * @param IDPRENOTAZIONE
     */
    public void setIDPRENOTAZIONE(String IDPRENOTAZIONE) {
        this.IDPRENOTAZIONE = IDPRENOTAZIONE;
    }

    /**
     *
     * @return
     */
    public String getINTRO() {
        return INTRO;
    }

    /**
     *
     * @param INTRO
     */
    public void setINTRO(String INTRO) {
        this.INTRO = INTRO;
    }

    /**
     *
     * @return
     */
    public String getCORPO() {
        return CORPO;
    }

    /**
     *
     * @param CORPO
     */
    public void setCORPO(String CORPO) {
        this.CORPO = CORPO;
    }

    /**
     *
     * @return
     */
    public String getRIEPILOGO() {
        return RIEPILOGO;
    }

    /**
     *
     * @param RIEPILOGO
     */
    public void setRIEPILOGO(String RIEPILOGO) {
        this.RIEPILOGO = RIEPILOGO;
    }

    /**
     *
     * @return
     */
    public String getALTREINFO() {
        return ALTREINFO;
    }

    /**
     *
     * @param ALTREINFO
     */
    public void setALTREINFO(String ALTREINFO) {
        this.ALTREINFO = ALTREINFO;
    }

    /**
     *
     * @return
     */
    public String getNVERDE() {
        return NVERDE;
    }

    /**
     *
     * @param NVERDE
     */
    public void setNVERDE(String NVERDE) {
        this.NVERDE = NVERDE;
    }

    /**
     *
     * @return
     */
    public String getCHIUSURA() {
        return CHIUSURA;
    }

    /**
     *
     * @param CHIUSURA
     */
    public void setCHIUSURA(String CHIUSURA) {
        this.CHIUSURA = CHIUSURA;
    }

}
