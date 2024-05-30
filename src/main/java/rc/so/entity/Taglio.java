/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rc.so.entity;

import static com.google.common.base.Splitter.on;
import static rc.so.util.Utility.fd;
import static rc.so.util.Utility.formatDoubleforMysql;
import static java.lang.Integer.parseInt;
import java.util.Iterator;

/**
 *
 * @author rcosco
 */
public class Taglio implements Comparable<Taglio> {

    String TipoProdotto;
    String brand;
    String brandcode;
    String resultCode, resultDesc;
    String codiceTaglio, descrizione, tipologia;

    /**
     *
     * @return
     */
    public String getBrandcode() {
        return brandcode;
    }

    /**
     *
     * @return
     */
    public String getTipoProdotto() {
        return TipoProdotto;
    }

    /**
     *
     * @param TipoProdotto
     */
    public void setTipoProdotto(String TipoProdotto) {
        this.TipoProdotto = TipoProdotto;
    }
    
    /**
     *
     * @param brandcode
     */
    public void setBrandcode(String brandcode) {
        this.brandcode = brandcode;
    }
    
    /**
     *
     * @return
     */
    public String getBrand() {
        return brand;
    }

    /**
     *
     * @param brand
     */
    public void setBrand(String brand) {
        this.brand = brand;
    }

    /**
     *
     * @return
     */
    public String getResultCode() {
        return resultCode;
    }

    /**
     *
     * @param resultCode
     */
    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    /**
     *
     * @return
     */
    public String getResultDesc() {
        return resultDesc;
    }

    /**
     *
     * @param resultDesc
     */
    public void setResultDesc(String resultDesc) {
        this.resultDesc = resultDesc;
    }

    /**
     *
     * @return
     */
    public String getCodiceTaglio() {
        return codiceTaglio;
    }

    /**
     *
     * @param codiceTaglio
     */
    public void setCodiceTaglio(String codiceTaglio) {
        this.codiceTaglio = codiceTaglio;
    }

    /**
     *
     * @return
     */
    public String getDescrizione() {
        return descrizione;
    }

    /**
     *
     * @param descrizione
     */
    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    /**
     *
     * @return
     */
    public String getTipologia() {
        return tipologia;
    }

    /**
     *
     * @param tipologia
     */
    public void setTipologia(String tipologia) {
        this.tipologia = tipologia;
    }

    @Override
    public int compareTo(Taglio o) {
        if (this.getBrand().compareTo(o.getBrand()) > 0) {
            return 1;
        } else if (this.getBrand().compareTo(o.getBrand()) < 0) {
            return -1;
        } else if (this.getDescrizione().contains(",") && o.getDescrizione().contains(",")) {
            String valore1 = "";
            Iterable<String> parameters = on(" ").split(this.getDescrizione());
            Iterator<String> it = parameters.iterator();
            while (it.hasNext()) {
                valore1 = it.next().trim();
            }
            String valore2 = "";
            Iterable<String> parameters2 = on(" ").split(o.getDescrizione());
            Iterator<String> it2 = parameters2.iterator();
            while (it2.hasNext()) {
                valore2 = it2.next().trim();
            }

            double d1 = fd(formatDoubleforMysql(valore1));
            double d2 = fd(formatDoubleforMysql(valore2));

            if (d1 > d2) {
                return 1;
            } else if (d1 < d2) {
                return -1;
            } else if (parseInt(this.getCodiceTaglio().trim()) > parseInt(o.getCodiceTaglio().trim())) {
                return 1;
            } else if (parseInt(this.getCodiceTaglio().trim()) < parseInt(o.getCodiceTaglio().trim())) {
                return -1;
            } else {
                return 0;
            }
        }
        return -1;
    }

}
