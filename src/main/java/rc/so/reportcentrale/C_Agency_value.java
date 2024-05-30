/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rc.so.reportcentrale;

import java.util.ArrayList;

/**
 *
 * @author srotella
 */
public class C_Agency_value {
     
    String id_ag,de_ag;
    ArrayList<C_ChangeMovimentForAgency_value> dati;
    
    /**
     *
     * @return
     */
    public ArrayList<C_ChangeMovimentForAgency_value> getDati() {
        return dati;
    }

    /**
     *
     * @param dati
     */
    public void setDati(ArrayList<C_ChangeMovimentForAgency_value> dati) {
        this.dati = dati;
    }
    
    /**
     *
     * @return
     */
    public String getId_ag() {
        return id_ag;
    }

    /**
     *
     * @param id_ag
     */
    public void setId_ag(String id_ag) {
        this.id_ag = id_ag;
    }

    /**
     *
     * @return
     */
    public String getDe_ag() {
        return de_ag;
    }

    /**
     *
     * @param de_ag
     */
    public void setDe_ag(String de_ag) {
        this.de_ag = de_ag;
    }

 
    
    

}