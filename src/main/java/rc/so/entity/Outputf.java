/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rc.so.entity;

/**
 * Constructor
 * @author rcosco
 */
public class Outputf {

    String esito, error, docid, worid, linkweb, linkand, linkos, coddoc;

    /**
     * Constructor
     */
    public Outputf() {
    }

    String codice_documento, data_firma, firma, nomefile_firma;

    /**
     *
     * @param codice_documento
     * @param data_firma
     * @param error
     * @param esito
     * @param firma
     * @param nomefile_firma
     * @param worid
     */
    public Outputf(String codice_documento, String data_firma, String error, String esito, String firma, String nomefile_firma, String worid) {
        this.esito = esito;
        this.error = error;
        this.codice_documento = codice_documento;
        this.data_firma = data_firma;
        this.firma = firma;
        this.nomefile_firma = nomefile_firma;
        this.worid = worid;
    }

    /**
     *
     * @param esito
     * @param error
     * @param docid
     * @param worid
     * @param linkweb
     * @param linkand
     * @param linkos
     * @param coddoc
     */
    public Outputf(String esito, String error, String docid, String worid, String linkweb, String linkand, String linkos, String coddoc) {
        this.esito = esito;
        this.error = error;
        this.docid = docid;
        this.worid = worid;
        this.linkweb = linkweb;
        this.linkand = linkand;
        this.linkos = linkos;
        this.coddoc = coddoc;
    }

    /**
     *
     * @return
     */
    public String getCodice_documento() {
        return codice_documento;
    }

    /**
     *
     * @param codice_documento
     */
    public void setCodice_documento(String codice_documento) {
        this.codice_documento = codice_documento;
    }

    /**
     *
     * @return
     */
    public String getData_firma() {
        return data_firma;
    }

    /**
     *
     * @param data_firma
     */
    public void setData_firma(String data_firma) {
        this.data_firma = data_firma;
    }

    /**
     *
     * @return
     */
    public String getFirma() {
        return firma;
    }

    /**
     *
     * @param firma
     */
    public void setFirma(String firma) {
        this.firma = firma;
    }

    /**
     *
     * @return
     */
    public String getNomefile_firma() {
        return nomefile_firma;
    }

    /**
     *
     * @param nomefile_firma
     */
    public void setNomefile_firma(String nomefile_firma) {
        this.nomefile_firma = nomefile_firma;
    }
    
    /**
     *
     * @return
     */
    public String getEsito() {
        return esito;
    }

    /**
     *
     * @param esito
     */
    public void setEsito(String esito) {
        this.esito = esito;
    }

    /**
     *
     * @return
     */
    public String getError() {
        return error;
    }

    /**
     *
     * @param error
     */
    public void setError(String error) {
        this.error = error;
    }

    /**
     *
     * @return
     */
    public String getDocid() {
        return docid;
    }

    /**
     *
     * @param docid
     */
    public void setDocid(String docid) {
        this.docid = docid;
    }

    /**
     *
     * @return
     */
    public String getWorid() {
        return worid;
    }

    /**
     *
     * @param worid
     */
    public void setWorid(String worid) {
        this.worid = worid;
    }

    /**
     *
     * @return
     */
    public String getLinkweb() {
        return linkweb;
    }

    /**
     *
     * @param linkweb
     */
    public void setLinkweb(String linkweb) {
        this.linkweb = linkweb;
    }

    /**
     *
     * @return
     */
    public String getLinkand() {
        return linkand;
    }

    /**
     *
     * @param linkand
     */
    public void setLinkand(String linkand) {
        this.linkand = linkand;
    }

    /**
     *
     * @return
     */
    public String getLinkos() {
        return linkos;
    }

    /**
     *
     * @param linkos
     */
    public void setLinkos(String linkos) {
        this.linkos = linkos;
    }

    /**
     *
     * @return
     */
    public String getCoddoc() {
        return coddoc;
    }

    /**
     *
     * @param coddoc
     */
    public void setCoddoc(String coddoc) {
        this.coddoc = coddoc;
    }
}
