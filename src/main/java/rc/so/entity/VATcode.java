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
public class VATcode {
    String id,codice,descrizione,aliquota,fg_annullato,dt;

    /**
     * Constructor
     */
    public VATcode() {
    }

    /**
     *
     * @param id
     * @param codice
     * @param descrizione
     * @param aliquota
     * @param fg_annullato
     * @param dt
     */
    public VATcode(String id, String codice, String descrizione, String aliquota, String fg_annullato, String dt) {
        this.id = id;
        this.codice = codice;
        this.descrizione = descrizione;
        this.aliquota = aliquota;
        this.fg_annullato = fg_annullato;
        this.dt = dt;
    }

    /**
     *
     * @return
     */
    public String getId() {
        return id;
    }

    /**
     *
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     *
     * @return
     */
    public String getCodice() {
        return codice;
    }

    /**
     *
     * @param codice
     */
    public void setCodice(String codice) {
        this.codice = codice;
    }

    /**
     *
     * @return
     */
    public String getDescrizione() {
        return descrizione;
    }

    /**
     *
     * @param descrizione
     */
    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    /**
     *
     * @return
     */
    public String getAliquota() {
        return aliquota;
    }

    /**
     *
     * @param aliquota
     */
    public void setAliquota(String aliquota) {
        this.aliquota = aliquota;
    }

    /**
     *
     * @return
     */
    public String getFg_annullato() {
        return fg_annullato;
    }

    /**
     *
     * @param fg_annullato
     */
    public void setFg_annullato(String fg_annullato) {
        this.fg_annullato = fg_annullato;
    }

    /**
     *
     * @return
     */
    public String getDt() {
        return dt;
    }

    /**
     *
     * @param dt
     */
    public void setDt(String dt) {
        this.dt = dt;
    }
    
    

}
