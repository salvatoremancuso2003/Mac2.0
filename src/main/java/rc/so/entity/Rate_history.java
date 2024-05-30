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
public class Rate_history {
    // ENUM('0','1','2') NOT NULL DEFAULT '0' COMMENT '0 -modifica dello spot da parte della sede centrale; 1 - modifica da parte dell’amministratore della percentuale di standard; 2 - modifica da parte dell’operatore del rate standard',
    String codic,filiale,valuta,tipomod,modify,user,dt_mod;

    /**
     * Constructor
     */
    public Rate_history() {
    }

    /**
     *
     * @param codic
     * @param filiale
     * @param valuta
     * @param tipomod
     * @param modify
     * @param user
     * @param dt_mod
     */
    public Rate_history(String codic, String filiale, String valuta, String tipomod, String modify, String user, String dt_mod) {
        this.codic = codic;
        this.filiale = filiale;
        this.valuta = valuta;
        this.tipomod = tipomod;
        this.modify = modify;
        this.user = user;
        this.dt_mod = dt_mod;
    }
    
    /**
     *
     * @param ty
     * @return
     */
    public String formatType(String ty){
        switch (ty) {
            case "0":
                return "Modify BCE spot - Central";
            case "1":
                return "Modify Percent Standard - Admin";
            case "2":
                return "Modify Rate - Operator";
            default:
                break;
        }
        return ty;
    }
    
    /**
     *
     * @return
     */
    public String getCodic() {
        return codic;
    }

    /**
     *
     * @param codic
     */
    public void setCodic(String codic) {
        this.codic = codic;
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
    public String getValuta() {
        return valuta;
    }

    /**
     *
     * @param valuta
     */
    public void setValuta(String valuta) {
        this.valuta = valuta;
    }

    /**
     *
     * @return
     */
    public String getTipomod() {
        return tipomod;
    }

    /**
     *
     * @param tipomod
     */
    public void setTipomod(String tipomod) {
        this.tipomod = tipomod;
    }

    /**
     *
     * @return
     */
    public String getModify() {
        return modify;
    }

    /**
     *
     * @param modify
     */
    public void setModify(String modify) {
        this.modify = modify;
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
    public String getDt_mod() {
        return dt_mod;
    }

    /**
     *
     * @param dt_mod
     */
    public void setDt_mod(String dt_mod) {
        this.dt_mod = dt_mod;
    }
    
}
