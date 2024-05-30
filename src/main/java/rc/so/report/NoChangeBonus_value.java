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
public class NoChangeBonus_value {
    
    
    
    
    String id_filiale, de_filiale,user, periodo;

    ArrayList<NoChangeBonus_value> dati;
    ArrayList<String> dati_string;
    
    ArrayList<String> alcolonne;
    
    /**
     *
     * @return
     */
    public ArrayList<String> getDati_string() {
        return dati_string;
    }

    /**
     *
     * @param dati_string
     */
    public void setDati_string(ArrayList<String> dati_string) {
        this.dati_string = dati_string;
    }
    
    /**
     *
     * @return
     */
    public ArrayList<String> getAlcolonne() {
        return alcolonne;
    }

    /**
     *
     * @param alcolonne
     */
    public void setAlcolonne(ArrayList<String> alcolonne) {
        this.alcolonne = alcolonne;
    }
    
    /**
     *
     * @return
     */
    public String getPeriodo() {
        return periodo;
    }

    /**
     *
     * @param periodo
     */
    public void setPeriodo(String periodo) {
        this.periodo = periodo;
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
    public ArrayList<NoChangeBonus_value> getDati() {
        return dati;
    }

    /**
     *
     * @param dati
     */
    public void setDati(ArrayList<NoChangeBonus_value> dati) {
        this.dati = dati;
    }

    
    
}
