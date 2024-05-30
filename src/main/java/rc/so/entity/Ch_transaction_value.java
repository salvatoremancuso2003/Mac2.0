package rc.so.entity;

/**
 *
 * @author rcosco
 */
public class Ch_transaction_value implements Comparable<Ch_transaction_value>{
    Ch_transaction trorig;
    
    String id;
    String cod_tr;
    String numeroriga;
    String supporto;
    String pos;
    String posnum;
    String valuta;
    String quantita;
    String rate;
    String com_perc;
    String com_perc_tot;
    String fx_com;
    String tot_com;
    String net;
    String spread;
    String total;
    String roundvalue;
    
    String branch,type,operator,note;

    /**
     *
     * @return
     */
    public String getNote() {
        return note;
    }

    /**
     *
     * @param note
     */
    public void setNote(String note) {
        this.note = note;
    }

    /**
     *
     * @return
     */
    public String getOperator() {
        return operator;
    }

    /**
     *
     * @param operator
     */
    public void setOperator(String operator) {
        this.operator = operator;
    }
    
    /**
     *
     * @return
     */
    public String getBranch() {
        return branch;
    }

    /**
     *
     * @param branch
     */
    public void setBranch(String branch) {
        this.branch = branch;
    }

    /**
     *
     * @return
     */
    public String getType() {
        return type;
    }

    /**
     *
     * @param type
     */
    public void setType(String type) {
        this.type = type;
    }
    
    /**
     *
     * @return
     */
    public Ch_transaction getTrorig() {
        return trorig;
    }

    /**
     *
     * @return
     */
    public String getRoundvalue() {
        return roundvalue;
    }

    /**
     *
     * @param roundvalue
     */
    public void setRoundvalue(String roundvalue) {
        this.roundvalue = roundvalue;
    }

    /**
     *
     * @param trorig
     */
    public void setTrorig(Ch_transaction trorig) {
        this.trorig = trorig;
    }
    
    /**
     *
     * @return
     */
    public String getFx_com() {
        return this.fx_com;
    }

    String kind_fix_comm;

    /**
     *
     * @return
     */
    public String getPosnum() {
        return this.posnum;
    }

    String low_com_ju;

    /**
     *
     * @param posnum
     */
    public void setPosnum(String posnum) {
        this.posnum = posnum;
    }

    String bb;
    String bb_fidcode;
    String contr_valuta;

    /**
     *
     * @param fx_com
     */
    public void setFx_com(String fx_com) {
        this.fx_com = fx_com;
    }

    String contr_supporto;

    /**
     *
     * @return
     */
    public String getId() {
        return this.id;
    }

    String contr_quantita;

    /**
     *
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    String dt_tr;

    /**
     *
     * @return
     */
    public String getCod_tr() {
        return this.cod_tr;
    }

    String del_fg;

    /**
     *
     * @param cod_tr
     */
    public void setCod_tr(String cod_tr) {
        this.cod_tr = cod_tr;
    }

    String del_dt;

    /**
     *
     * @return
     */
    public String getNumeroriga() {
        return this.numeroriga;
    }

    /**
     *
     * @param numeroriga
     */
    public void setNumeroriga(String numeroriga) {
        this.numeroriga = numeroriga;
    }

    /**
     *
     * @return
     */
    public String getSupporto() {
        return this.supporto;
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
    public String getPos() {
        return this.pos;
    }

    /**
     *
     * @param pos
     */
    public void setPos(String pos) {
        this.pos = pos;
    }

    /**
     *
     * @return
     */
    public String getValuta() {
        return this.valuta;
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
    public String getQuantita() {
        return this.quantita;
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
    public String getRate() {
        return this.rate;
    }

    /**
     *
     * @param rate
     */
    public void setRate(String rate) {
        this.rate = rate;
    }

    /**
     *
     * @return
     */
    public String getCom_perc() {
        return this.com_perc;
    }

    /**
     *
     * @param com_perc
     */
    public void setCom_perc(String com_perc) {
        this.com_perc = com_perc;
    }

    /**
     *
     * @return
     */
    public String getCom_perc_tot() {
        return this.com_perc_tot;
    }

    /**
     *
     * @param com_perc_tot
     */
    public void setCom_perc_tot(String com_perc_tot) {
        this.com_perc_tot = com_perc_tot;
    }

    /**
     *
     * @return
     */
    public String getTot_com() {
        return this.tot_com;
    }

    /**
     *
     * @param tot_com
     */
    public void setTot_com(String tot_com) {
        this.tot_com = tot_com;
    }

    /**
     *
     * @return
     */
    public String getNet() {
        return this.net;
    }

    /**
     *
     * @param net
     */
    public void setNet(String net) {
        this.net = net;
    }

    /**
     *
     * @return
     */
    public String getSpread() {
        return this.spread;
    }

    /**
     *
     * @param spread
     */
    public void setSpread(String spread) {
        this.spread = spread;
    }

    /**
     *
     * @return
     */
    public String getTotal() {
        return this.total;
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
    public String getKind_fix_comm() {
        return this.kind_fix_comm;
    }

    /**
     *
     * @param kind_fix_comm
     */
    public void setKind_fix_comm(String kind_fix_comm) {
        this.kind_fix_comm = kind_fix_comm;
    }

    /**
     *
     * @return
     */
    public String getLow_com_ju() {
        return this.low_com_ju;
    }

    /**
     *
     * @param low_com_ju
     */
    public void setLow_com_ju(String low_com_ju) {
        this.low_com_ju = low_com_ju;
    }

    /**
     *
     * @return
     */
    public String getBb() {
        return this.bb;
    }

    /**
     *
     * @param bb
     */
    public void setBb(String bb) {
        this.bb = bb;
    }

    /**
     *
     * @return
     */
    public String getBb_fidcode() {
        return this.bb_fidcode;
    }

    /**
     *
     * @param bb_fidcode
     */
    public void setBb_fidcode(String bb_fidcode) {
        this.bb_fidcode = bb_fidcode;
    }

    /**
     *
     * @return
     */
    public String getContr_valuta() {
        return this.contr_valuta;
    }

    /**
     *
     * @param contr_valuta
     */
    public void setContr_valuta(String contr_valuta) {
        this.contr_valuta = contr_valuta;
    }

    /**
     *
     * @return
     */
    public String getContr_supporto() {
        return this.contr_supporto;
    }

    /**
     *
     * @param contr_supporto
     */
    public void setContr_supporto(String contr_supporto) {
        this.contr_supporto = contr_supporto;
    }

    /**
     *
     * @return
     */
    public String getContr_quantita() {
        return this.contr_quantita;
    }

    /**
     *
     * @param contr_quantita
     */
    public void setContr_quantita(String contr_quantita) {
        this.contr_quantita = contr_quantita;
    }

    /**
     *
     * @return
     */
    public String getDt_tr() {
        return this.dt_tr;
    }

    /**
     *
     * @param dt_tr
     */
    public void setDt_tr(String dt_tr) {
        this.dt_tr = dt_tr;
    }

    /**
     *
     * @return
     */
    public String getDel_fg() {
        return this.del_fg;
    }

    /**
     *
     * @param del_fg
     */
    public void setDel_fg(String del_fg) {
        this.del_fg = del_fg;
    }

    /**
     *
     * @return
     */
    public String getDel_dt() {
        return this.del_dt;
    }

    /**
     *
     * @param del_dt
     */
    public void setDel_dt(String del_dt) {
        this.del_dt = del_dt;
    }
    
    
    @Override
    public int compareTo(Ch_transaction_value o) {
        if (this.getValuta().compareTo(o.getValuta()) > 0) {
            return 1;
        } else if (this.getValuta().compareTo(o.getValuta()) < 0) {
            return -1;
        } else {
            return this.getSupporto().compareTo(o.getSupporto());
        }
    }
    
}


/* Location:              C:\Users\rcosco\Desktop\classes\!\entity\Ch_transaction_value.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */
