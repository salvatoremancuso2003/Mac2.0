/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rc.so.entity;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import static org.apache.commons.lang3.builder.ToStringStyle.JSON_STYLE;

/**
 *
 * @author rcosco
 */
public class Stock {

    String codice, filiale, tipo, till, idoperation, codiceopenclose, tipostock, cod_value, kind, total, controval, rate, user, date;

    String id_op;

    /**
     *
     * @param codice
     * @param filiale
     * @param tipo
     * @param till
     * @param idoperation
     * @param codiceopenclose
     * @param tipostock
     * @param cod_value
     * @param kind
     * @param total
     * @param controval
     * @param rate
     * @param user
     * @param date
     * @param id_op
     */
    public Stock(String codice, String filiale, String tipo, String till, String idoperation, String codiceopenclose, String tipostock, String cod_value, String kind, String total, String controval, String rate, String user, String date, String id_op) {
        this.codice = codice;
        this.filiale = filiale;
        this.tipo = tipo;
        this.till = till;
        this.idoperation = idoperation;
        this.codiceopenclose = codiceopenclose;
        this.tipostock = tipostock;
        this.cod_value = cod_value;
        this.kind = kind;
        this.total = total;
        this.controval = controval;
        this.rate = rate;
        this.user = user;
        this.date = date;
        this.id_op = id_op;
    }

    /**
     *
     * @param cod_value
     * @param kind
     * @param total
     * @param controval
     */
    public Stock(String cod_value, String kind, String total, String controval) {
        this.cod_value = cod_value;
        this.kind = kind;
        this.total = total;
        this.controval = controval;
    }

    /**
     * Constructor
     */
    public Stock() {
    }

    /**
     *
     * @return
     */
    public String getId_op() {
        return id_op;
    }

    /**
     *
     * @param id_op
     */
    public void setId_op(String id_op) {
        this.id_op = id_op;
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
    public String getIdoperation() {
        return idoperation;
    }

    /**
     *
     * @param idoperation
     */
    public void setIdoperation(String idoperation) {
        this.idoperation = idoperation;
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
    public String getTipostock() {
        return tipostock;
    }

    /**
     *
     * @param tipostock
     */
    public void setTipostock(String tipostock) {
        this.tipostock = tipostock;
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
    public String getControval() {
        return controval;
    }

    /**
     *
     * @param controval
     */
    public void setControval(String controval) {
        this.controval = controval;
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
    
    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, JSON_STYLE);
    }
    
}