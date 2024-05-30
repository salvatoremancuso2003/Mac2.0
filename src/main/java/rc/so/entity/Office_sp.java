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
public class Office_sp {
    
    //dati
    String codice, filiale, till, type_oc, openclose_cod, openclose_id, total_fx, total_cod, total_grand, data;
    //valori
    String cod,currency,kind,quantity,av_buy,controv,timestamp;

    /**
     *
     * @param codice
     * @param filiale
     * @param till
     * @param type_oc
     * @param openclose_cod
     * @param openclose_id
     * @param total_fx
     * @param total_cod
     * @param total_grand
     * @param data
     */
    public Office_sp(String codice, String filiale, String till, String type_oc, String openclose_cod, String openclose_id, String total_fx, String total_cod, String total_grand, String data) {
        this.codice = codice;
        this.filiale = filiale;
        this.till = till;
        this.type_oc = type_oc;
        this.openclose_cod = openclose_cod;
        this.openclose_id = openclose_id;
        this.total_fx = total_fx;
        this.total_cod = total_cod;
        this.total_grand = total_grand;
        this.data = data;
    }

    /**
     *
     * @param cod
     * @param currency
     * @param kind
     * @param quantity
     * @param av_buy
     * @param controv
     * @param timestamp
     */
    public Office_sp(String cod, String currency, String kind, String quantity, String av_buy, String controv, String timestamp) {
        this.cod = cod;
        this.currency = currency;
        this.kind = kind;
        this.quantity = quantity;
        this.av_buy = av_buy;
        this.controv = controv;
        this.timestamp = timestamp;
    }
    
    /**
     *
     * @return
     */
    public String getCodice() {
        return codice;
    }

    /**
     *
     * @param codice
     */
    public void setCodice(String codice) {
        this.codice = codice;
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
    public String getType_oc() {
        return type_oc;
    }

    /**
     *
     * @param type_oc
     */
    public void setType_oc(String type_oc) {
        this.type_oc = type_oc;
    }

    /**
     *
     * @return
     */
    public String getOpenclose_cod() {
        return openclose_cod;
    }

    /**
     *
     * @param openclose_cod
     */
    public void setOpenclose_cod(String openclose_cod) {
        this.openclose_cod = openclose_cod;
    }

    /**
     *
     * @return
     */
    public String getOpenclose_id() {
        return openclose_id;
    }

    /**
     *
     * @param openclose_id
     */
    public void setOpenclose_id(String openclose_id) {
        this.openclose_id = openclose_id;
    }

    /**
     *
     * @return
     */
    public String getTotal_fx() {
        return total_fx;
    }

    /**
     *
     * @param total_fx
     */
    public void setTotal_fx(String total_fx) {
        this.total_fx = total_fx;
    }

    /**
     *
     * @return
     */
    public String getTotal_cod() {
        return total_cod;
    }

    /**
     *
     * @param total_cod
     */
    public void setTotal_cod(String total_cod) {
        this.total_cod = total_cod;
    }

    /**
     *
     * @return
     */
    public String getTotal_grand() {
        return total_grand;
    }

    /**
     *
     * @param total_grand
     */
    public void setTotal_grand(String total_grand) {
        this.total_grand = total_grand;
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
    public String getCod() {
        return cod;
    }

    /**
     *
     * @param cod
     */
    public void setCod(String cod) {
        this.cod = cod;
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
    public String getKind() {
        return kind;
    }

    /**
     *
     * @param kind
     */
    public void setKind(String kind) {
        this.kind = kind;
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
    public String getAv_buy() {
        return av_buy;
    }

    /**
     *
     * @param av_buy
     */
    public void setAv_buy(String av_buy) {
        this.av_buy = av_buy;
    }

    /**
     *
     * @return
     */
    public String getControv() {
        return controv;
    }

    /**
     *
     * @param controv
     */
    public void setControv(String controv) {
        this.controv = controv;
    }

    /**
     *
     * @return
     */
    public String getTimestamp() {
        return timestamp;
    }

    /**
     *
     * @param timestamp
     */
    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
    
    
    
    
}
