/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rc.so.reportcentrale;

import rc.so.entity.Ch_transaction;
import rc.so.entity.Openclose;
import java.util.ArrayList;

/**
 *
 * @author srotella
 */
public class C_CashierDetails_value {
     String id_filiale, de_filiale,dataDa,dataA,user;
      
     
    
    ArrayList<Ch_transaction> datitran;
    ArrayList<Openclose> datierr;

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
    public ArrayList<Ch_transaction> getDatitran() {
        return datitran;
    }

    /**
     *
     * @param datitran
     */
    public void setDatitran(ArrayList<Ch_transaction> datitran) {
        this.datitran = datitran;
    }

    /**
     *
     * @return
     */
    public ArrayList<Openclose> getDatierr() {
        return datierr;
    }

    /**
     *
     * @param datierr
     */
    public void setDatierr(ArrayList<Openclose> datierr) {
        this.datierr = datierr;
    }
    
    
    
    
    
    
}
