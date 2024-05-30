/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rc.so.report;

import java.util.ArrayList;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import static org.apache.commons.lang3.builder.ToStringStyle.JSON_STYLE;

/**
 *
 * @author srotella
 */
public class BranchStockInquiry_value {
    
    
    String id_filiale, de_filiale,currency,notes,eurotravel,travel,credit,personal,creditcop,bancomatcop;
    
    /**
     *
     * @return
     */
    public String getCurrency() {
        return currency;
    }

    /**
     *
     * @param currency
     */
    public void setCurrency(String currency) {
        this.currency = currency;
    }

    /**
     *
     * @return
     */
    public String getNotes() {
        return (notes);
    }

    /**
     *
     * @param notes
     */
    public void setNotes(String notes) {
        this.notes = notes;
    }

    /**
     *
     * @return
     */
    public String getEurotravel() {
        return (eurotravel);
    }

    /**
     *
     * @param eurotravel
     */
    public void setEurotravel(String eurotravel) {
        this.eurotravel = eurotravel;
    }

    /**
     *
     * @return
     */
    public String getTravel() {
        return (travel);
    }

    /**
     *
     * @param travel
     */
    public void setTravel(String travel) {
        this.travel = travel;
    }

    /**
     *
     * @return
     */
    public String getCredit() {
        return (credit);
    }

    /**
     *
     * @param credic
     */
    public void setCredit(String credic) {
        this.credit = credic;
    }

    /**
     *
     * @return
     */
    public String getPersonal() {
        return (personal);
    }

    /**
     *
     * @param personal
     */
    public void setPersonal(String personal) {
        this.personal = personal;
    }

    /**
     *
     * @return
     */
    public String getCreditcop() {
        return (creditcop);
    }

    /**
     *
     * @param creditcop
     */
    public void setCreditcop(String creditcop) {
        this.creditcop = creditcop;
    }

    /**
     *
     * @return
     */
    public String getBancomatcop() {
        return (bancomatcop);
    }

    /**
     *
     * @param bancomatcop
     */
    public void setBancomatcop(String bancomatcop) {
        this.bancomatcop = bancomatcop;
    }
    ArrayList<BranchStockInquiry_value> dati;
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
    public ArrayList<BranchStockInquiry_value> getDati() {
        return dati;
    }

    /**
     *
     * @param dati
     */
    public void setDati(ArrayList<BranchStockInquiry_value> dati) {
        this.dati = dati;
    }
    
    
    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, JSON_STYLE);
    }

}
