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
public class Stock_report {

    String codice, filiale, till, codiceopenclose, tipo, cod_value, kind, quantity, total, rate, spread, user, data,codtr;

    /**
     *
     * @param codice
     * @param filiale
     * @param till
     * @param codiceopenclose
     * @param tipo
     * @param cod_value
     * @param kind
     * @param quantity
     * @param total
     * @param rate
     * @param spread
     * @param user
     * @param data
     * @param codtr
     */
    public Stock_report(String codice, String filiale, String till, String codiceopenclose, String tipo, 
            String cod_value, String kind, String quantity, String total, String rate, String spread, String user, String data, String codtr) {
        this.codice = codice;
        this.filiale = filiale;
        this.till = till;
        this.codiceopenclose = codiceopenclose;
        this.tipo = tipo;
        this.cod_value = cod_value;
        this.kind = kind;
        this.quantity = quantity;
        this.total = total;
        this.rate = rate;
        this.spread = spread;
        this.user = user;
        this.data = data;
        this.codtr = codtr;
    }

    /**
     * Constructor
     */
    public Stock_report() {
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
    public String getCodiceopenclose() {
        return codiceopenclose;
    }

    /**
     *
     * @param codiceopenclose
     */
    public void setCodiceopenclose(String codiceopenclose) {
        this.codiceopenclose = codiceopenclose;
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
    public String getCod_value() {
        return cod_value;
    }

    /**
     *
     * @param cod_value
     */
    public void setCod_value(String cod_value) {
        this.cod_value = cod_value;
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
    public String getTotal() {
        return total;
    }

    /**
     *
     * @param total
     */
    public void setTotal(String total) {
        this.total = total;
    }

    /**
     *
     * @return
     */
    public String getRate() {
        return rate;
    }

    /**
     *
     * @param rate
     */
    public void setRate(String rate) {
        this.rate = rate;
    }

    /**
     *
     * @return
     */
    public String getSpread() {
        return spread;
    }

    /**
     *
     * @param spread
     */
    public void setSpread(String spread) {
        this.spread = spread;
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

}
