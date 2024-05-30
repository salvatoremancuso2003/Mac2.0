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
public class Paymat_conf {

    String url, serverURI, value_callerId, value_skin, value_userIP, value_token, value_idAziendaDistributore,
            value_idAziendaEsercente, value_CAB, value_sk, value_idterminale;

    /**
     * Constructor
     */
    public Paymat_conf() {
    }

    /**
     *
     * @param url
     * @param serverURI
     * @param value_callerId
     * @param value_skin
     * @param value_userIP
     * @param value_token
     * @param value_idAziendaDistributore
     * @param value_idAziendaEsercente
     * @param value_CAB
     * @param value_sk
     * @param value_idterminale
     */
    public Paymat_conf(String url, String serverURI, String value_callerId, String value_skin, String value_userIP, 
            String value_token, String value_idAziendaDistributore, String value_idAziendaEsercente, String value_CAB, 
            String value_sk, String value_idterminale) {
        this.url = url;
        this.serverURI = serverURI;
        this.value_callerId = value_callerId;
        this.value_skin = value_skin;
        this.value_userIP = value_userIP;
        this.value_token = value_token;
        this.value_idAziendaDistributore = value_idAziendaDistributore;
        this.value_idAziendaEsercente = value_idAziendaEsercente;
        this.value_CAB = value_CAB;
        this.value_sk = value_sk;
        this.value_idterminale = value_idterminale;
    }
    
    /**
     *
     * @return
     */
    public String getUrl() {
        return url;
    }

    /**
     *
     * @param url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     *
     * @return
     */
    public String getServerURI() {
        return serverURI;
    }

    /**
     *
     * @param serverURI
     */
    public void setServerURI(String serverURI) {
        this.serverURI = serverURI;
    }

    /**
     *
     * @return
     */
    public String getValue_callerId() {
        return value_callerId;
    }

    /**
     *
     * @param value_callerId
     */
    public void setValue_callerId(String value_callerId) {
        this.value_callerId = value_callerId;
    }

    /**
     *
     * @return
     */
    public String getValue_skin() {
        return value_skin;
    }

    /**
     *
     * @param value_skin
     */
    public void setValue_skin(String value_skin) {
        this.value_skin = value_skin;
    }

    /**
     *
     * @return
     */
    public String getValue_userIP() {
        return value_userIP;
    }

    /**
     *
     * @param value_userIP
     */
    public void setValue_userIP(String value_userIP) {
        this.value_userIP = value_userIP;
    }

    /**
     *
     * @return
     */
    public String getValue_token() {
        return value_token;
    }

    /**
     *
     * @param value_token
     */
    public void setValue_token(String value_token) {
        this.value_token = value_token;
    }

    /**
     *
     * @return
     */
    public String getValue_idAziendaDistributore() {
        return value_idAziendaDistributore;
    }

    /**
     *
     * @param value_idAziendaDistributore
     */
    public void setValue_idAziendaDistributore(String value_idAziendaDistributore) {
        this.value_idAziendaDistributore = value_idAziendaDistributore;
    }

    /**
     *
     * @return
     */
    public String getValue_idAziendaEsercente() {
        return value_idAziendaEsercente;
    }

    /**
     *
     * @param value_idAziendaEsercente
     */
    public void setValue_idAziendaEsercente(String value_idAziendaEsercente) {
        this.value_idAziendaEsercente = value_idAziendaEsercente;
    }

    /**
     *
     * @return
     */
    public String getValue_CAB() {
        return value_CAB;
    }

    /**
     *
     * @param value_CAB
     */
    public void setValue_CAB(String value_CAB) {
        this.value_CAB = value_CAB;
    }

    /**
     *
     * @return
     */
    public String getValue_sk() {
        return value_sk;
    }

    /**
     *
     * @param value_sk
     */
    public void setValue_sk(String value_sk) {
        this.value_sk = value_sk;
    }

    /**
     *
     * @return
     */
    public String getValue_idterminale() {
        return value_idterminale;
    }

    /**
     *
     * @param value_idterminale
     */
    public void setValue_idterminale(String value_idterminale) {
        this.value_idterminale = value_idterminale;
    }

}
