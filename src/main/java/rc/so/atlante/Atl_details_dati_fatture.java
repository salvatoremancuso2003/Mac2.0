/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rc.so.atlante;

/**
 *
 * @author rcosco
 */
public class Atl_details_dati_fatture {

    String cod, numriga, category, casual, segnoreg, tipoconto, contoreg, desc, ivareg, datadoc, numdoc, modpaga, importo;

    /**
     *
     * @param cod
     * @param numriga
     * @param category
     * @param casual
     * @param segnoreg
     * @param tipoconto
     * @param contoreg
     * @param desc
     * @param ivareg
     * @param datadoc
     * @param numdoc
     * @param modpaga
     * @param importo
     */
    public Atl_details_dati_fatture(String cod, String numriga, String category, String casual, String segnoreg, String tipoconto, String contoreg, String desc, String ivareg, String datadoc, String numdoc, String modpaga, String importo) {
        this.cod = cod;
        this.numriga = numriga;
        this.category = category;
        this.casual = casual;
        this.segnoreg = segnoreg;
        this.tipoconto = tipoconto;
        this.contoreg = contoreg;
        this.desc = desc;
        this.ivareg = ivareg;
        this.datadoc = datadoc;
        this.numdoc = numdoc;
        this.modpaga = modpaga;
        this.importo = importo;
    }

    /**
     *
     * @return
     */
    public String controlli_details() {
        return "OK";
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
    public String getNumriga() {
        return numriga;
    }

    /**
     *
     * @param numriga
     */
    public void setNumriga(String numriga) {
        this.numriga = numriga;
    }

    /**
     *
     * @return
     */
    public String getCategory() {
        return category;
    }

    /**
     *
     * @param category
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     *
     * @return
     */
    public String getCasual() {
        return casual;
    }

    /**
     *
     * @param casual
     */
    public void setCasual(String casual) {
        this.casual = casual;
    }

    /**
     *
     * @return
     */
    public String getSegnoreg() {
        return segnoreg;
    }

    /**
     *
     * @param segnoreg
     */
    public void setSegnoreg(String segnoreg) {
        this.segnoreg = segnoreg;
    }

    /**
     *
     * @return
     */
    public String getTipoconto() {
        return tipoconto;
    }

    /**
     *
     * @param tipoconto
     */
    public void setTipoconto(String tipoconto) {
        this.tipoconto = tipoconto;
    }

    /**
     *
     * @return
     */
    public String getContoreg() {
        return contoreg;
    }

    /**
     *
     * @param contoreg
     */
    public void setContoreg(String contoreg) {
        this.contoreg = contoreg;
    }

    /**
     *
     * @return
     */
    public String getDesc() {
        return desc;
    }

    /**
     *
     * @param desc
     */
    public void setDesc(String desc) {
        this.desc = desc;
    }

    /**
     *
     * @return
     */
    public String getIvareg() {
        return ivareg;
    }

    /**
     *
     * @param ivareg
     */
    public void setIvareg(String ivareg) {
        this.ivareg = ivareg;
    }

    /**
     *
     * @return
     */
    public String getDatadoc() {
        return datadoc;
    }

    /**
     *
     * @param datadoc
     */
    public void setDatadoc(String datadoc) {
        this.datadoc = datadoc;
    }

    /**
     *
     * @return
     */
    public String getNumdoc() {
        return numdoc;
    }

    /**
     *
     * @param numdoc
     */
    public void setNumdoc(String numdoc) {
        this.numdoc = numdoc;
    }

    /**
     *
     * @return
     */
    public String getModpaga() {
        return modpaga;
    }

    /**
     *
     * @param modpaga
     */
    public void setModpaga(String modpaga) {
        this.modpaga = modpaga;
    }

    /**
     *
     * @return
     */
    public String getImporto() {
        return importo;
    }

    /**
     *
     * @param importo
     */
    public void setImporto(String importo) {
        this.importo = importo;
    }

}
