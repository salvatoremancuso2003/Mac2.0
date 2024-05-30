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
public class IT_change {
    
    String cod,id,filiale,user,till_from,till_to,
            idopen_from,idopen_to,dt_it,fg_annullato,
            del_dt,del_user,del_motiv;

    String valuta,supporto,quantita,totale,taglio,nc_causal;
    
    String typeop;
    
    /**
     *
     * @param fg_annullato
     * @return
     */
    public String formatStatus(String fg_annullato) {
        if (fg_annullato != null) {
            if (fg_annullato.equals("0")) {
                return "<div class='font-green-jungle'>Active <i class='fa fa-check'></i></div>";
            }
            if (fg_annullato.equals("1")) {
                return "<div class='font-red'>Deleted <i class='fa fa-remove'></i></div>";
            }
        }
        return "-";
    }
    
    /**
     *
     * @param fg_annullato
     * @return
     */
    public String formatStatus_cru(String fg_annullato) {
        if (fg_annullato != null) {
            if (fg_annullato.equals("0")) {
                return "Active ";
            }
            if (fg_annullato.equals("1")) {
                return "Deleted ";
            }
        }
        return "-";
    }

    /**
     *
     * @return
     */
    public String getTypeop() {
        return typeop;
    }

    /**
     *
     * @param typeop
     */
    public void setTypeop(String typeop) {
        this.typeop = typeop;
    }
    
    /**
     *
     * @return
     */
    public String getNc_causal() {
        return nc_causal;
    }

    /**
     *
     * @param nc_causal
     */
    public void setNc_causal(String nc_causal) {
        this.nc_causal = nc_causal;
    }

    /**
     *
     * @return
     */
    public String getTaglio() {
        return taglio;
    }

    /**
     *
     * @param taglio
     */
    public void setTaglio(String taglio) {
        this.taglio = taglio;
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
    public String getSupporto() {
        return supporto;
    }

    /**
     *
     * @param supporto
     */
    public void setSupporto(String supporto) {
        this.supporto = supporto;
    }

    /**
     *
     * @return
     */
    public String getQuantita() {
        return quantita;
    }

    /**
     *
     * @param quantita
     */
    public void setQuantita(String quantita) {
        this.quantita = quantita;
    }

    /**
     *
     * @return
     */
    public String getTotale() {
        return totale;
    }

    /**
     *
     * @param totale
     */
    public void setTotale(String totale) {
        this.totale = totale;
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
    public String getId() {
        return id;
    }

    /**
     *
     * @param id
     */
    public void setId(String id) {
        this.id = id;
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
    public String getTill_from() {
        return till_from;
    }

    /**
     *
     * @param till_from
     */
    public void setTill_from(String till_from) {
        this.till_from = till_from;
    }

    /**
     *
     * @return
     */
    public String getTill_to() {
        return till_to;
    }

    /**
     *
     * @param till_to
     */
    public void setTill_to(String till_to) {
        this.till_to = till_to;
    }

    /**
     *
     * @return
     */
    public String getIdopen_from() {
        return idopen_from;
    }

    /**
     *
     * @param idopen_from
     */
    public void setIdopen_from(String idopen_from) {
        this.idopen_from = idopen_from;
    }

    /**
     *
     * @return
     */
    public String getIdopen_to() {
        return idopen_to;
    }

    /**
     *
     * @param idopen_to
     */
    public void setIdopen_to(String idopen_to) {
        this.idopen_to = idopen_to;
    }

    /**
     *
     * @return
     */
    public String getDt_it() {
        return dt_it;
    }

    /**
     *
     * @param dt_it
     */
    public void setDt_it(String dt_it) {
        this.dt_it = dt_it;
    }

    /**
     *
     * @return
     */
    public String getFg_annullato() {
        return fg_annullato;
    }

    /**
     *
     * @param fg_annullato
     */
    public void setFg_annullato(String fg_annullato) {
        this.fg_annullato = fg_annullato;
    }

    /**
     *
     * @return
     */
    public String getDel_dt() {
        return del_dt;
    }

    /**
     *
     * @param del_dt
     */
    public void setDel_dt(String del_dt) {
        this.del_dt = del_dt;
    }

    /**
     *
     * @return
     */
    public String getDel_user() {
        return del_user;
    }

    /**
     *
     * @param del_user
     */
    public void setDel_user(String del_user) {
        this.del_user = del_user;
    }

    /**
     *
     * @return
     */
    public String getDel_motiv() {
        return del_motiv;
    }

    /**
     *
     * @param del_motiv
     */
    public void setDel_motiv(String del_motiv) {
        this.del_motiv = del_motiv;
    }
    
    

}
