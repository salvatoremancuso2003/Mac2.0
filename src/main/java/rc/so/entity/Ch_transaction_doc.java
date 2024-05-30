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
public class Ch_transaction_doc {

    String codice_documento, codtr, tipodoc, content, nomefile, data_load,client;
    
    String online;
    
    /**
     * Constructor
     */
    public Ch_transaction_doc() {
    }

    /**
     *
     * @param codice_documento
     * @param codtr
     * @param tipodoc
     * @param content
     * @param nomefile
     * @param data_load
     * @param client
     * @param online
     */
    public Ch_transaction_doc(String codice_documento, String codtr, String tipodoc, String content, String nomefile, String data_load, String client, String online) {
        this.codice_documento = codice_documento;
        this.codtr = codtr;
        this.tipodoc = tipodoc;
        this.content = content;
        this.nomefile = nomefile;
        this.data_load = data_load;
        this.client = client;
        this.online = online;
    }

    /**
     *
     * @return
     */
    public String getOnline() {
        return online;
    }

    /**
     *
     * @param online
     */
    public void setOnline(String online) {
        this.online = online;
    }

    /**
     *
     * @return
     */
    public String getClient() {
        return client;
    }

    /**
     *
     * @param client
     */
    public void setClient(String client) {
        this.client = client;
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
    public String getCodtr() {
        return codtr;
    }

    /**
     *
     * @param codtr
     */
    public void setCodtr(String codtr) {
        this.codtr = codtr;
    }

    /**
     *
     * @return
     */
    public String getTipodoc() {
        return tipodoc;
    }

    /**
     *
     * @param tipodoc
     */
    public void setTipodoc(String tipodoc) {
        this.tipodoc = tipodoc;
    }

    /**
     *
     * @return
     */
    public String getContent() {
        return content;
    }

    /**
     *
     * @param content
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     *
     * @return
     */
    public String getNomefile() {
        return nomefile;
    }

    /**
     *
     * @param nomefile
     */
    public void setNomefile(String nomefile) {
        this.nomefile = nomefile;
    }

    /**
     *
     * @return
     */
    public String getData_load() {
        return data_load;
    }

    /**
     *
     * @param data_load
     */
    public void setData_load(String data_load) {
        this.data_load = data_load;
    }
    
    
}
