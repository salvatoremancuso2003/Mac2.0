package rc.so.entity;

/**
 *
 * @author rcosco
 */
public class NC_causal implements Comparable<NC_causal>{

    String gruppo_nc;
    String causale_nc;
    String de_causale_nc;
    String fg_in_out;
    String ip_prezzo_nc;
    String fg_tipo_transazione_nc;
    String annullato;
    String fg_gruppo_stampa;
    String fg_batch;
    String fg_scontrino;
    String ticket_fee_type;
    String ticket_fee;
    String max_ticket;
    String nc_de;
    String bonus;
    String codice_integr;
    String filiale;
    String data;
    String paymat;
    String docric;
    
    /**
     *
     * @param annullato
     * @return
     */
    public String formatStatus(String annullato) {
        if (annullato == null) {
            annullato = "";
        }
        if (annullato.equals("0")) {
            return "<div class='font-green-jungle'>Enabled <i class='fa fa-check'></i></div>";
        }
        if (annullato.equals("1")) {
            return "<div class='font-red'>Disabled <i class='fa fa-close'></i></div>";
        }
        return annullato;
    }
    
    /**
     *
     * @param annullato
     * @return
     */
    public String formatStatus_cru(String annullato) {
        if (annullato == null) {
            annullato = "";
        }
        if (annullato.equals("0")) {
            return "Enabled ";
        }
        if (annullato.equals("1")) {
            return "Disabled ";
        }
        return annullato;
    }

    /**
     *
     * @return
     */
    public String getDocric() {
        return docric;
    }

    /**
     *
     * @param docric
     */
    public void setDocric(String docric) {
        this.docric = docric;
    }
    
    /**
     *
     * @return
     */
    public String getPaymat() {
        return paymat;
    }

    /**
     *
     * @param paymat
     */
    public void setPaymat(String paymat) {
        this.paymat = paymat;
    }

    /**
     *
     * @return
     */
    public String getData() {
        return data;
    }

    /**
     *
     * @param data
     */
    public void setData(String data) {
        this.data = data;
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
    public String getCodice_integr() {
        return codice_integr;
    }

    /**
     *
     * @param codice_integr
     */
    public void setCodice_integr(String codice_integr) {
        this.codice_integr = codice_integr;
    }

    /**
     *
     * @return
     */
    public String getBonus() {
        return bonus;
    }

    /**
     *
     * @param bonus
     */
    public void setBonus(String bonus) {
        this.bonus = bonus;
    }

    /**
     *
     * @return
     */
    public String getNc_de() {
        return this.nc_de;
    }

    /**
     *
     * @param nc_de
     */
    public void setNc_de(String nc_de) {
        this.nc_de = nc_de;
    }

    /**
     *
     * @return
     */
    public String getGruppo_nc() {
        return this.gruppo_nc;
    }

    /**
     *
     * @param gruppo_nc
     */
    public void setGruppo_nc(String gruppo_nc) {
        this.gruppo_nc = gruppo_nc;
    }

    /**
     *
     * @return
     */
    public String getCausale_nc() {
        return this.causale_nc;
    }

    /**
     *
     * @param causale_nc
     */
    public void setCausale_nc(String causale_nc) {
        this.causale_nc = causale_nc;
    }

    /**
     *
     * @return
     */
    public String getDe_causale_nc() {
        return this.de_causale_nc;
    }

    /**
     *
     * @param de_causale_nc
     */
    public void setDe_causale_nc(String de_causale_nc) {
        this.de_causale_nc = de_causale_nc;
    }

    /**
     *
     * @return
     */
    public String getFg_in_out() {
        return this.fg_in_out;
    }

    /**
     *
     * @param fg_in_out
     */
    public void setFg_in_out(String fg_in_out) {
        this.fg_in_out = fg_in_out;
    }

    /**
     *
     * @return
     */
    public String getIp_prezzo_nc() {
        return this.ip_prezzo_nc;
    }

    /**
     *
     * @param ip_prezzo_nc
     */
    public void setIp_prezzo_nc(String ip_prezzo_nc) {
        this.ip_prezzo_nc = ip_prezzo_nc;
    }

    /**
     *
     * @return
     */
    public String getFg_tipo_transazione_nc() {
        return this.fg_tipo_transazione_nc;
    }

    /**
     *
     * @param fg_tipo_transazione_nc
     */
    public void setFg_tipo_transazione_nc(String fg_tipo_transazione_nc) {
        this.fg_tipo_transazione_nc = fg_tipo_transazione_nc;
    }

    /**
     *
     * @return
     */
    public String getAnnullato() {
        return this.annullato;
    }

    /**
     *
     * @param annullato
     */
    public void setAnnullato(String annullato) {
        this.annullato = annullato;
    }

    /**
     *
     * @return
     */
    public String getFg_gruppo_stampa() {
        return this.fg_gruppo_stampa;
    }

    /**
     *
     * @param fg_gruppo_stampa
     */
    public void setFg_gruppo_stampa(String fg_gruppo_stampa) {
        this.fg_gruppo_stampa = fg_gruppo_stampa;
    }

    /**
     *
     * @return
     */
    public String getFg_batch() {
        return this.fg_batch;
    }

    /**
     *
     * @param fg_batch
     */
    public void setFg_batch(String fg_batch) {
        this.fg_batch = fg_batch;
    }

    /**
     *
     * @return
     */
    public String getFg_scontrino() {
        return this.fg_scontrino;
    }

    /**
     *
     * @param fg_scontrino
     */
    public void setFg_scontrino(String fg_scontrino) {
        this.fg_scontrino = fg_scontrino;
    }

    /**
     *
     * @return
     */
    public String getTicket_fee_type() {
        return ticket_fee_type;
    }

    /**
     *
     * @param ticket_fee_type
     */
    public void setTicket_fee_type(String ticket_fee_type) {
        this.ticket_fee_type = ticket_fee_type;
    }

    /**
     *
     * @return
     */
    public String getTicket_fee() {
        return this.ticket_fee;
    }

    /**
     *
     * @param ticket_fee
     */
    public void setTicket_fee(String ticket_fee) {
        this.ticket_fee = ticket_fee;
    }

    /**
     *
     * @return
     */
    public String getMax_ticket() {
        return this.max_ticket;
    }

    /**
     *
     * @param max_ticket
     */
    public void setMax_ticket(String max_ticket) {
        this.max_ticket = max_ticket;
    }
    
    @Override
    public int compareTo(NC_causal o) {
        return this.getDe_causale_nc().compareTo(o.getDe_causale_nc());
    }
}


/* Location:              C:\Users\rcosco\Desktop\classes\!\entity\NC_causal.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */
