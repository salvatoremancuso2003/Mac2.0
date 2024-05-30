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
public class NoChangeInternalTransferList_value {
    
    
    String id_filiale, de_filiale,source,dest,progr,date,category,quantity,deleted,operatore;
       
    ArrayList<NoChangeInternalTransferList_value> dati;
    ArrayList<String> dati_string;

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
    public String getOperatore() {
        return operatore;
    }

    /**
     *
     * @param operatore
     */
    public void setOperatore(String operatore) {
        this.operatore = operatore;
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
    public String getSource() {
        return source;
    }

    /**
     *
     * @param source
     */
    public void setSource(String source) {
        this.source = source;
    }

    /**
     *
     * @return
     */
    public String getDest() {
        return dest;
    }

    /**
     *
     * @param dest
     */
    public void setDest(String dest) {
        this.dest = dest;
    }

    /**
     *
     * @return
     */
    public String getProgr() {
        return progr;
    }

    /**
     *
     * @param progr
     */
    public void setProgr(String progr) {
        this.progr = progr;
    }

    /**
     *
     * @return
     */
    public String getDate() {
        return date;
    }

    /**
     *
     * @param date
     */
    public void setDate(String date) {
        this.date = date;
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
    public String getQuantity() {
        return quantity;
    }

    /**
     *
     * @param quantity
     */
    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    /**
     *
     * @return
     */
    public String getDeleted() {
        return deleted;
    }

    /**
     *
     * @param deleted
     */
    public void setDeleted(String deleted) {
        this.deleted = deleted;
    }

    /**
     *
     * @return
     */
    public ArrayList<NoChangeInternalTransferList_value> getDati() {
        return dati;
    }

    /**
     *
     * @param dati
     */
    public void setDati(ArrayList<NoChangeInternalTransferList_value> dati) {
        this.dati = dati;
    }
    
    
}
