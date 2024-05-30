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
public class StockInquiry_value implements Comparable {

    String id_filiale, de_filiale, till, currency, notes, eurotravelcheques, travelcheques, creditcard, personalcheques, creditcardCOP, bancomatCOP;
    int columnnumber;
    
    ArrayList<StockInquiry_value> data = new ArrayList<>();
    ArrayList<String> dati_string = new ArrayList<>();

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
    public int getColumnnumber() {
        return columnnumber;
    }

    /**
     *
     * @param columnnumber
     */
    public void setColumnnumber(int columnnumber) {
        this.columnnumber = columnnumber;
    }

    /**
     *
     * @return
     */
    public ArrayList<StockInquiry_value> getData() {
        return data;
    }

    /**
     *
     * @param data
     */
    public void setData(ArrayList<StockInquiry_value> data) {
        this.data = data;
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
    public String getTill() {
        return till;
    }

    /**
     *
     * @param till
     */
    public void setTill(String till) {
        this.till = till;
    }

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
        return notes;
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
    public String getEurotravelcheques() {
        return eurotravelcheques;
    }

    /**
     *
     * @param aurotravelcheques
     */
    public void setEurotravelcheques(String aurotravelcheques) {
        this.eurotravelcheques = aurotravelcheques;
    }

    /**
     *
     * @return
     */
    public String getTravelcheques() {
        return travelcheques;
    }

    /**
     *
     * @param travelcheques
     */
    public void setTravelcheques(String travelcheques) {
        this.travelcheques = travelcheques;
    }

    /**
     *
     * @return
     */
    public String getCreditcard() {
        return creditcard;
    }

    /**
     *
     * @param creditcard
     */
    public void setCreditcard(String creditcard) {
        this.creditcard = creditcard;
    }

    /**
     *
     * @return
     */
    public String getPersonalcheques() {
        return personalcheques;
    }

    /**
     *
     * @param personalcheques
     */
    public void setPersonalcheques(String personalcheques) {
        this.personalcheques = personalcheques;
    }

    /**
     *
     * @return
     */
    public String getCreditcardCOP() {
        return creditcardCOP;
    }

    /**
     *
     * @param creditcardCOP
     */
    public void setCreditcardCOP(String creditcardCOP) {
        this.creditcardCOP = creditcardCOP;
    }

    /**
     *
     * @return
     */
    public String getBancomatCOP() {
        return bancomatCOP;
    }

    /**
     *
     * @param bancomatCOP
     */
    public void setBancomatCOP(String bancomatCOP) {
        this.bancomatCOP = bancomatCOP;
    }

    @Override
    public int compareTo(Object o) {
        StockInquiry_value v = (StockInquiry_value) o;
        return this.currency.compareTo(v.getCurrency());

    }

}
