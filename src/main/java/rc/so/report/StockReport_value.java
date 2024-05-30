/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rc.so.report;

import static rc.so.util.Constant.patternnormdate;
import java.util.ArrayList;
import org.joda.time.DateTime;
import static org.joda.time.format.DateTimeFormat.forPattern;
import org.joda.time.format.DateTimeFormatter;

/**
 *
 * @author srotella
 */
public class StockReport_value implements Comparable{
    
    
    String id_filiale, de_filiale,dataDa,dataA,till,dateTime,categoryTrans,kindTrans, quantity, price, total,user, initialStock, actualStock, inout,annullato ;    
    
    ArrayList<StockReport_value> dati;

    /**
     *
     * @return
     */
    public String getInout() {
        return inout;
    }

    /**
     *
     * @param inout
     */
    public void setInout(String inout) {
        this.inout = inout;
    }

    /**
     *
     * @return
     */
    public String getAnnullato() {
        return annullato;
    }

    /**
     *
     * @param annullato
     */
    public void setAnnullato(String annullato) {
        this.annullato = annullato;
    }
    
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
    public String getDataDa() {
        return dataDa;
    }

    /**
     *
     * @param dataDa
     */
    public void setDataDa(String dataDa) {
        this.dataDa = dataDa;
    }

    /**
     *
     * @return
     */
    public String getDataA() {
        return dataA;
    }

    /**
     *
     * @param dataA
     */
    public void setDataA(String dataA) {
        this.dataA = dataA;
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
    public String getDateTime() {
        return dateTime;
    }

    /**
     *
     * @param dateTime
     */
    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
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
    public String getKindTrans() {
        return kindTrans;
    }

    /**
     *
     * @param kindTrans
     */
    public void setKindTrans(String kindTrans) {
        this.kindTrans = kindTrans;
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
    public String getPrice() {
        return price;
    }

    /**
     *
     * @param price
     */
    public void setPrice(String price) {
        this.price = price;
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
    public String getInitialStock() {
        return initialStock;
    }

    /**
     *
     * @param initialStock
     */
    public void setInitialStock(String initialStock) {
        this.initialStock = initialStock;
    }

    /**
     *
     * @return
     */
    public String getActualStock() {
        return actualStock;
    }

    /**
     *
     * @param actualStock
     */
    public void setActualStock(String actualStock) {
        this.actualStock = actualStock;
    }

    /**
     *
     * @return
     */
    public ArrayList<StockReport_value> getDati() {
        return dati;
    }

    /**
     *
     * @param dati
     */
    public void setDati(ArrayList<StockReport_value> dati) {
        this.dati = dati;
    }

    @Override
    public int compareTo(Object o) {
        DateTimeFormatter formatter = forPattern(patternnormdate);
        DateTime dt1 = formatter.parseDateTime(this.dateTime);
        DateTime dt2 = formatter.parseDateTime(((StockReport_value)o).getDateTime());
        return dt1.compareTo(dt2);
    }

    
    
}
