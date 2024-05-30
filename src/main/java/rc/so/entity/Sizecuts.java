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
public class Sizecuts {
    
    String filiale, valuta, ip_taglio, ip_taglio_MOD, fg_stato;

    /**
     *
     * @param filiale
     * @param valuta
     * @param ip_taglio
     * @param fg_stato
     */
    public Sizecuts(String filiale, String valuta, String ip_taglio, String fg_stato) {
        this.filiale = filiale;
        this.valuta = valuta;
        this.ip_taglio = ip_taglio;
        this.fg_stato = fg_stato;
    }

    /**
     * Constructor
     */
    public Sizecuts() {
    }

    /**
     *
     * @return
     */
    public String getIp_taglio_MOD() {
        return ip_taglio_MOD;
    }

    /**
     *
     * @param ip_taglio_MOD
     */
    public void setIp_taglio_MOD(String ip_taglio_MOD) {
        this.ip_taglio_MOD = ip_taglio_MOD;
    }
        
    /**
     *
     * @return
     */
    public String getFiliale() {
        return filiale;
    }

    /**
     *
     * @param filiale
     */
    public void setFiliale(String filiale) {
        this.filiale = filiale;
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
    public String getIp_taglio() {
        return ip_taglio;
    }

    /**
     *
     * @param ip_taglio
     */
    public void setIp_taglio(String ip_taglio) {
        this.ip_taglio = ip_taglio;
    }

    /**
     *
     * @return
     */
    public String getFg_stato() {
        return fg_stato;
    }

    /**
     *
     * @param fg_stato
     */
    public void setFg_stato(String fg_stato) {
        this.fg_stato = fg_stato;
    }
    
    
    
}
