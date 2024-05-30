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
public class Brand {
    
    String resultCode,resultDesc;
    String codiceBrand,descrizione,tipologia;

    /**
     *
     * @return
     */
    public String getResultCode() {
        return resultCode;
    }

    /**
     *
     * @param resultCode
     */
    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    /**
     *
     * @return
     */
    public String getResultDesc() {
        return resultDesc;
    }

    /**
     *
     * @param resultDesc
     */
    public void setResultDesc(String resultDesc) {
        this.resultDesc = resultDesc;
    }
    
    /**
     *
     * @return
     */
    public String getCodiceBrand() {
        return codiceBrand;
    }

    /**
     *
     * @param codiceBrand
     */
    public void setCodiceBrand(String codiceBrand) {
        this.codiceBrand = codiceBrand;
    }

    /**
     *
     * @return
     */
    public String getDescrizione() {
        return descrizione;
    }

    /**
     *
     * @param descrizione
     */
    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    /**
     *
     * @return
     */
    public String getTipologia() {
        return tipologia;
    }

    /**
     *
     * @param tipologia
     */
    public void setTipologia(String tipologia) {
        this.tipologia = tipologia;
    }
    
}