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
public class CashAD_CZ {
    
    String pos,posdescr,valuta,rate,quantity,total;
    String ratebce;

    /**
     *
     * @param pos
     * @param posdescr
     * @param valuta
     * @param rate
     * @param quantity
     * @param total
     */
    public CashAD_CZ(String pos, String posdescr, String valuta, String rate, String quantity, String total) {
        this.pos = pos;
        this.posdescr = posdescr;
        this.valuta = valuta;
        this.rate = rate;
        this.ratebce = rate;
        this.quantity = quantity;
        this.total = total;
    }

    public String getRatebce() {
        return ratebce;
    }

    public void setRatebce(String ratebce) {
        this.ratebce = ratebce;
    }
    

    /**
     * Constructor
     */
    public CashAD_CZ() {
    }

    /**
     *
     * @return
     */
    public String getPos() {
        return pos;
    }

    /**
     *
     * @param pos
     */
    public void setPos(String pos) {
        this.pos = pos;
    }

    /**
     *
     * @return
     */
    public String getPosdescr() {
        return posdescr;
    }

    /**
     *
     * @param posdescr
     */
    public void setPosdescr(String posdescr) {
        this.posdescr = posdescr;
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
    
    
    
    
}
