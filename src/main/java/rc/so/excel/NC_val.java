/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rc.so.excel;

/**
 *
 * @author rcosco
 */
public class NC_val{
    
    String cat,year,filiale;
    double quantity,tot;

    /**
     * Constructor
     */
    public NC_val() {
    }
    
    /**
     *
     * @param cat
     * @param year
     * @param filiale
     * @param quantity
     * @param tot
     */
    public NC_val(String cat, String year, String filiale, double quantity, double tot) {
        this.cat = cat;
        this.year = year;
        this.filiale = filiale;
        this.quantity = quantity;
        this.tot = tot;
    }
    
    /**
     *
     * @return
     */
    public String getYear() {
        return year;
    }

    /**
     *
     * @param year
     */
    public void setYear(String year) {
        this.year = year;
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
    public String getCat() {
        return cat;
    }

    /**
     *
     * @param cat
     */
    public void setCat(String cat) {
        this.cat = cat;
    }

    /**
     *
     * @return
     */
    public double getQuantity() {
        return quantity;
    }

    /**
     *
     * @param quantity
     */
    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    /**
     *
     * @return
     */
    public double getTot() {
        return tot;
    }

    /**
     *
     * @param tot
     */
    public void setTot(double tot) {
        this.tot = tot;
    }

    
    
}
