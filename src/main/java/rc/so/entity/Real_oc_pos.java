package rc.so.entity;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import static org.apache.commons.lang3.builder.ToStringStyle.SHORT_PREFIX_STYLE;

/**
 *
 * @author rcosco
 */
public class Real_oc_pos {

    String id, filiale, cod_oc, valuta, kind, carta_credito, ip_quantity, ip_value, data;

    /**
     *
     * @return
     */
    public String getId() {
        return id;
    }

    /**
     *
     * @param id
     */
    public void setId(String id) {
        this.id = id;
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
    public String getCarta_credito() {
        return carta_credito;
    }

    /**
     *
     * @param carta_credito
     */
    public void setCarta_credito(String carta_credito) {
        this.carta_credito = carta_credito;
    }

    /**
     *
     * @return
     */
    public String getIp_quantity() {
        return ip_quantity;
    }

    /**
     *
     * @param ip_quantity
     */
    public void setIp_quantity(String ip_quantity) {
        this.ip_quantity = ip_quantity;
    }

    /**
     *
     * @return
     */
    public String getIp_value() {
        return ip_value;
    }

    /**
     *
     * @param ip_value
     */
    public void setIp_value(String ip_value) {
        this.ip_value = ip_value;
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
