/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rc.so.report;

import java.util.ArrayList;

/**
 *
 * @author srotella
 */
public class TotalStockReport_value {
    
    
    String id_filiale, de_filiale,data,till,categoryTrans,stock ;    
    
    ArrayList<TotalStockReport_value> dati;

    /**
     *
     * @return
     */
    public String getId_filiale() {
        return id_filiale;
    }

    /**
     *
     * @param id_filiale
     */
    public void setId_filiale(String id_filiale) {
        this.id_filiale = id_filiale;
    }

    /**
     *
     * @return
     */
    public String getDe_filiale() {
        return de_filiale;
    }

    /**
     *
     * @param de_filiale
     */
    public void setDe_filiale(String de_filiale) {
        this.de_filiale = de_filiale;
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
    public String getCategoryTrans() {
        return categoryTrans;
    }

    /**
     *
     * @param categoryTrans
     */
    public void setCategoryTrans(String categoryTrans) {
        this.categoryTrans = categoryTrans;
    }

    /**
     *
     * @return
     */
    public String getStock() {
        return stock;
    }

    /**
     *
     * @param stock
     */
    public void setStock(String stock) {
        this.stock = stock;
    }

    /**
     *
     * @return
     */
    public ArrayList<TotalStockReport_value> getDati() {
        return dati;
    }

    /**
     *
     * @param dati
     */
    public void setDati(ArrayList<TotalStockReport_value> dati) {
        this.dati = dati;
    }

   
    
}