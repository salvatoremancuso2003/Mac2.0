/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rc.so.report;

import java.util.ArrayList;

/**
 *
 * @author rcosco
 */
public class Openclose_Synt_value {
    String id_filiale, de_filiale,user,safetill,data,operazione,tipo,numerrori;    
    
    ArrayList<Openclose_Synt_value> dati;

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
    public String getNumerrori() {
        return numerrori;
    }

    /**
     *
     * @param numerrori
     */
    public void setNumerrori(String numerrori) {
        this.numerrori = numerrori;
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
    public ArrayList<Openclose_Synt_value> getDati() {
        return dati;
    }

    /**
     *
     * @param dati
     */
    public void setDati(ArrayList<Openclose_Synt_value> dati) {
        this.dati = dati;
    }
}
