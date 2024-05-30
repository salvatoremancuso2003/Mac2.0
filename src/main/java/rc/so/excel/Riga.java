/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rc.so.excel;

import java.util.ArrayList;

/**
 *
 * @author rcosco
 */
public class Riga {
    ArrayList<String> valori = new ArrayList<>();
    String desc = "";
    String formula = "Y";

    /**
     *
     * @return
     */
    public ArrayList<String> getValori() {
        return valori;
    }

    /**
     *
     * @param valori
     */
    public void setValori(ArrayList<String> valori) {
        this.valori = valori;
    }

    /**
     *
     * @return
     */
    public String getDesc() {
        return desc;
    }

    /**
     *
     * @param desc
     */
    public void setDesc(String desc) {
        this.desc = desc;
    }

    /**
     *
     * @return
     */
    public String getFormula() {
        return formula;
    }

    /**
     *
     * @param formula
     */
    public void setFormula(String formula) {
        this.formula = formula;
    }
}
