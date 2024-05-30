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
public class NoChangeFromBranchingSheet_value {

    String id_filiale, de_filiale, category, quantity, amount;
    String fromsafe, tobranch;
    String numtranfer;
    String auto;
    
    
    ArrayList<String> dati;

    /**
     *
     * @return
     */
    public String getAuto() {
        return auto;
    }

    /**
     *
     * @param auto
     */
    public void setAuto(String auto) {
        this.auto = auto;
    }

    /**
     *
     * @return
     */
    public ArrayList<String> getDati() {
        return dati;
    }

    /**
     *
     * @param dati
     */
    public void setDati(ArrayList<String> dati) {
        this.dati = dati;
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
    public String getCategory() {
        return category;
    }

    /**
     *
     * @param category
     */
    public void setCategory(String category) {
        this.category = category;
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
    public String getFromsafe() {
        return fromsafe;
    }

    /**
     *
     * @param fromsafe
     */
    public void setFromsafe(String fromsafe) {
        this.fromsafe = fromsafe;
    }

    /**
     *
     * @return
     */
    public String getTobranch() {
        return tobranch;
    }

    /**
     *
     * @param tobranch
     */
    public void setTobranch(String tobranch) {
        this.tobranch = tobranch;
    }

    /**
     *
     * @return
     */
    public String getNumtranfer() {
        return numtranfer;
    }

    /**
     *
     * @param numtranfer
     */
    public void setNumtranfer(String numtranfer) {
        this.numtranfer = numtranfer;
    }

    /**
     *
     * @return
     */
    public String getAmount() {
        return amount;
    }

    /**
     *
     * @param amount
     */
    public void setAmount(String amount) {
        this.amount = amount;
    }

}
