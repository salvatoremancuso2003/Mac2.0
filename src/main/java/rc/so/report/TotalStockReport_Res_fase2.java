/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rc.so.report;

/**
 *
 * @author rcosco
 */
public class TotalStockReport_Res_fase2 {
    String quantity,cod_value,codiceopenclose,codtr,till;

    /**
     *
     * @param quantity
     * @param cod_value
     * @param codiceopenclose
     * @param codtr
     * @param till
     */
    public TotalStockReport_Res_fase2(String quantity, String cod_value, String codiceopenclose, String codtr, String till) {
        this.quantity = quantity;
        this.cod_value = cod_value;
        this.codiceopenclose = codiceopenclose;
        this.codtr = codtr;
        this.till = till;
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
    
    
    
}
