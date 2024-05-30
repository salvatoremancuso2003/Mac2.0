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
public class Aggiornamenti_mod {
    
    String cod,filiale,dt_start,fg_stato,tipost,action,user,timestamp;

    /**
     *
     * @param cod
     * @param filiale
     * @param dt_start
     * @param fg_stato
     * @param tipost
     * @param action
     * @param user
     * @param timestamp
     */
    public Aggiornamenti_mod(String cod, String filiale, String dt_start, String fg_stato, String tipost, String action, String user, String timestamp) {
        this.cod = cod;
        this.filiale = filiale;
        this.dt_start = dt_start;
        this.fg_stato = fg_stato;
        this.tipost = tipost;
        this.action = action;
        this.user = user;
        this.timestamp = timestamp;
    }

    /**
     * Constructor
     */
    public Aggiornamenti_mod() {
    }

    /**
     *
     * @return
     */
    public String getCod() {
        return cod;
    }

    /**
     *
     * @param cod
     */
    public void setCod(String cod) {
        this.cod = cod;
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
    public String getDt_start() {
        return dt_start;
    }

    /**
     *
     * @param dt_start
     */
    public void setDt_start(String dt_start) {
        this.dt_start = dt_start;
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
    public String getTipost() {
        return tipost;
    }

    /**
     *
     * @param tipost
     */
    public void setTipost(String tipost) {
        this.tipost = tipost;
    }

    /**
     *
     * @return
     */
    public String getAction() {
        return action;
    }

    /**
     *
     * @param action
     */
    public void setAction(String action) {
        this.action = action;
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
    public String getTimestamp() {
        return timestamp;
    }

    /**
     *
     * @param timestamp
     */
    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
    
}