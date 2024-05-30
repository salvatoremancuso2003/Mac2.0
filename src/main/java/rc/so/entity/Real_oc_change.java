/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rc.so.entity;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import static org.apache.commons.lang3.builder.ToStringStyle.SHORT_PREFIX_STYLE;

/**
 *
 * @author rcosco
 */
public class Real_oc_change {

    String filiale, cod_oc, valuta, kind, value_op, num_kind_op, data;
    
    String ip_taglio;

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
    public String getCod_oc() {
        return cod_oc;
    }

    /**
     *
     * @param cod_oc
     */
    public void setCod_oc(String cod_oc) {
        this.cod_oc = cod_oc;
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
    public String getValue_op() {
        return value_op;
    }

    /**
     *
     * @param value_op
     */
    public void setValue_op(String value_op) {
        this.value_op = value_op;
    }

    /**
     *
     * @return
     */
    public String getNum_kind_op() {
        return num_kind_op;
    }

    /**
     *
     * @param num_kind_op
     */
    public void setNum_kind_op(String num_kind_op) {
        this.num_kind_op = num_kind_op;
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
       
    
    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, SHORT_PREFIX_STYLE);
    }
}