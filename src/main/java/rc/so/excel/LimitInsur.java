/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rc.so.excel;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import static org.apache.commons.lang3.builder.ToStringStyle.SHORT_PREFIX_STYLE;

/**
 *
 * @author rcosco
 */
public class LimitInsur {

    String filiale, giorno, giornoStringa;
    String cop, fx, tot, delta;

    /**
     * Constructor
     */
    public LimitInsur() {
    }

    /**
     *
     * @param filiale
     * @param giorno
     * @param giornoStringa
     * @param cop
     * @param fx
     * @param tot
     * @param delta
     */
    public LimitInsur(String filiale, String giorno, String giornoStringa, String cop, String fx, String tot, String delta) {
        this.filiale = filiale;
        this.giorno = giorno;
        this.giornoStringa = giornoStringa;
        this.cop = cop;
        this.fx = fx;
        this.tot = tot;
        this.delta = delta;
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
    public String getGiorno() {
        return giorno;
    }

    /**
     *
     * @param giorno
     */
    public void setGiorno(String giorno) {
        this.giorno = giorno;
    }

    /**
     *
     * @return
     */
    public String getGiornoStringa() {
        return giornoStringa;
    }

    /**
     *
     * @param giornoStringa
     */
    public void setGiornoStringa(String giornoStringa) {
        this.giornoStringa = giornoStringa;
    }

    /**
     *
     * @return
     */
    public String getCop() {
        return cop;
    }

    /**
     *
     * @param cop
     */
    public void setCop(String cop) {
        this.cop = cop;
    }

    /**
     *
     * @return
     */
    public String getFx() {
        return fx;
    }

    /**
     *
     * @param fx
     */
    public void setFx(String fx) {
        this.fx = fx;
    }

    /**
     *
     * @return
     */
    public String getTot() {
        return tot;
    }

    /**
     *
     * @param tot
     */
    public void setTot(String tot) {
        this.tot = tot;
    }

    /**
     *
     * @return
     */
    public String getDelta() {
        return delta;
    }

    /**
     *
     * @param delta
     */
    public void setDelta(String delta) {
        this.delta = delta;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, SHORT_PREFIX_STYLE);
    }

}
