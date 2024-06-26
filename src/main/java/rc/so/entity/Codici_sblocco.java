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
public class Codici_sblocco {
    
    String codice,dt_gen,fg_stato,user_gen,dt_utilizzo,ty_util,cod_tr;

    /**
     * Constructor
     */
    public Codici_sblocco() {
    }

    /**
     *
     * @param codice
     * @param dt_gen
     * @param fg_stato
     * @param user_gen
     * @param dt_utilizzo
     * @param ty_util
     * @param cod_tr
     */
    public Codici_sblocco(String codice, String dt_gen, String fg_stato, String user_gen, String dt_utilizzo, String ty_util,String cod_tr) {
        this.codice = codice;
        this.dt_gen = dt_gen;
        this.fg_stato = fg_stato;
        this.user_gen = user_gen;
        this.dt_utilizzo = dt_utilizzo;
        this.ty_util = ty_util;
        this.cod_tr = cod_tr;
    }
    
    /**
     *
     * @param ty_util
     * @return
     */
    public static String format_ty_util(String ty_util){
        if(ty_util.equals("00")){
            return "Refund";
        }else if(ty_util.equals("01")){
            return "Unlock Rate";
        }
        return ty_util;
    }
    
    /**
     *
     * @return
     */
    public String getCod_tr() {
        return cod_tr;
    }

    /**
     *
     * @param cod_tr
     */
    public void setCod_tr(String cod_tr) {
        this.cod_tr = cod_tr;
    }
    
    /**
     *
     * @return
     */
    public String getCodice() {
        return codice;
    }

    /**
     *
     * @param codice
     */
    public void setCodice(String codice) {
        this.codice = codice;
    }

    /**
     *
     * @return
     */
    public String getDt_gen() {
        return dt_gen;
    }

    /**
     *
     * @param dt_gen
     */
    public void setDt_gen(String dt_gen) {
        this.dt_gen = dt_gen;
    }

    /**
     *
     * @return
     */
    public String getFg_stato() {
        return fg_stato;
    }

    /**
     *
     * @param fg_stato
     */
    public void setFg_stato(String fg_stato) {
        this.fg_stato = fg_stato;
    }

    /**
     *
     * @return
     */
    public String getUser_gen() {
        return user_gen;
    }

    /**
     *
     * @param user_gen
     */
    public void setUser_gen(String user_gen) {
        this.user_gen = user_gen;
    }

    /**
     *
     * @return
     */
    public String getDt_utilizzo() {
        return dt_utilizzo;
    }

    /**
     *
     * @param dt_utilizzo
     */
    public void setDt_utilizzo(String dt_utilizzo) {
        this.dt_utilizzo = dt_utilizzo;
    }

    /**
     *
     * @return
     */
    public String getTy_util() {
        return ty_util;
    }

    /**
     *
     * @param ty_util
     */
    public void setTy_util(String ty_util) {
        this.ty_util = ty_util;
    }
    
    

}
