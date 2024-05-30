/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rc.so.report;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author rcosco
 */
public class Openclose_Error_value_stock {
    List<Openclose_Error_value> dati = new ArrayList<>();
    String operazione = "";
    
    String id_filiale, de_filiale,user;
    
    /**
     *
     * @param da
     * @param operazione
     */
    public Openclose_Error_value_stock(ArrayList<Openclose_Error_value> da, String operazione) {
        this.dati = da;
        this.operazione = operazione;
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
    public List<Openclose_Error_value> getDati() {
        return dati;
    }

    /**
     *
     * @param dati
     */
    public void setDati(List<Openclose_Error_value> dati) {
        this.dati = dati;
    }

}
