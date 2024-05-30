package rc.so.entity;

/**
 *
 * @author rcosco
 */
public class CustomerKind {

    String filiale;
    String tipologia_clienti;
    String de_tipologia_clienti;
    String fg_nazionalita;
    String fg_tipo_cliente;
    String ip_max_singola_transazione;
    String ip_max_settimanale;
    String fg_area_geografica;
    String stampa_autocertificazione;
    String ip_soglia_antiriciclaggio;
    String ip_soglia_extraCEE_certification;
    String fg_annullato;
    String fg_uploadobbl;
    String taxfree;
    
    
    //
    String tipofat,vatcode,ip_soglia_bollo,ip_value_bollo,descr_bollo;
    String resident;

    /**
     *
     * @return
     */
    public String getTaxfree() {
        return taxfree;
    }

    /**
     *
     * @param taxfree
     */
    public void setTaxfree(String taxfree) {
        this.taxfree = taxfree;
    }
    
    /**
     *
     * @return
     */
    public String getResident() {
        return resident;
    }

    /**
     *
     * @param resident
     */
    public void setResident(String resident) {
        this.resident = resident;
    }
        
    /**
     *
     * @return
     */
    public String getTipofat() {
        return tipofat;
    }

    /**
     *
     * @param tipofat
     */
    public void setTipofat(String tipofat) {
        this.tipofat = tipofat;
    }

    /**
     *
     * @return
     */
    public String getVatcode() {
        return vatcode;
    }

    /**
     *
     * @param vatcode
     */
    public void setVatcode(String vatcode) {
        this.vatcode = vatcode;
    }

    /**
     *
     * @return
     */
    public String getIp_soglia_bollo() {
        return ip_soglia_bollo;
    }

    /**
     *
     * @param ip_soglia_bollo
     */
    public void setIp_soglia_bollo(String ip_soglia_bollo) {
        this.ip_soglia_bollo = ip_soglia_bollo;
    }

    /**
     *
     * @return
     */
    public String getIp_value_bollo() {
        return ip_value_bollo;
    }

    /**
     *
     * @param ip_value_bollo
     */
    public void setIp_value_bollo(String ip_value_bollo) {
        this.ip_value_bollo = ip_value_bollo;
    }

    /**
     *
     * @return
     */
    public String getDescr_bollo() {
        return descr_bollo;
    }

    /**
     *
     * @param descr_bollo
     */
    public void setDescr_bollo(String descr_bollo) {
        this.descr_bollo = descr_bollo;
    }
    
    /**
     *
     * @return
     */
    public String getFg_uploadobbl() {
        return fg_uploadobbl;
    }

    /**
     *
     * @param fg_uploadobbl
     */
    public void setFg_uploadobbl(String fg_uploadobbl) {
        this.fg_uploadobbl = fg_uploadobbl;
    }

    /**
     *
     * @return
     */
    public String getFg_annullato() {
        return this.fg_annullato;
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
    public String getFiliale() {
        return this.filiale;
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
    public String getTipologia_clienti() {
        return this.tipologia_clienti;
    }

    /**
     *
     * @param tipologia_clienti
     */
    public void setTipologia_clienti(String tipologia_clienti) {
        this.tipologia_clienti = tipologia_clienti;
    }

    /**
     *
     * @return
     */
    public String getDe_tipologia_clienti() {
        return this.de_tipologia_clienti;
    }

    /**
     *
     * @param de_tipologia_clienti
     */
    public void setDe_tipologia_clienti(String de_tipologia_clienti) {
        this.de_tipologia_clienti = de_tipologia_clienti;
    }

    /**
     *
     * @return
     */
    public String getFg_nazionalita() {
        return this.fg_nazionalita;
    }

    /**
     *
     * @param fg_nazionalita
     */
    public void setFg_nazionalita(String fg_nazionalita) {
        this.fg_nazionalita = fg_nazionalita;
    }

    /**
     *
     * @return
     */
    public String getFg_tipo_cliente() {
        return this.fg_tipo_cliente;
    }

    /**
     *
     * @param fg_tipo_cliente
     */
    public void setFg_tipo_cliente(String fg_tipo_cliente) {
        this.fg_tipo_cliente = fg_tipo_cliente;
    }

    /**
     *
     * @return
     */
    public String getIp_max_singola_transazione() {
        return this.ip_max_singola_transazione;
    }

    /**
     *
     * @param ip_max_singola_transazione
     */
    public void setIp_max_singola_transazione(String ip_max_singola_transazione) {
        this.ip_max_singola_transazione = ip_max_singola_transazione;
    }

    /**
     *
     * @return
     */
    public String getIp_max_settimanale() {
        return this.ip_max_settimanale;
    }

    /**
     *
     * @param ip_max_settimanale
     */
    public void setIp_max_settimanale(String ip_max_settimanale) {
        this.ip_max_settimanale = ip_max_settimanale;
    }

    /**
     *
     * @return
     */
    public String getFg_area_geografica() {
        return this.fg_area_geografica;
    }

    /**
     *
     * @param fg_area_geografica
     */
    public void setFg_area_geografica(String fg_area_geografica) {
        this.fg_area_geografica = fg_area_geografica;
    }

    /**
     *
     * @return
     */
    public String getStampa_autocertificazione() {
        return this.stampa_autocertificazione;
    }

    /**
     *
     * @param stampa_autocertificazione
     */
    public void setStampa_autocertificazione(String stampa_autocertificazione) {
        this.stampa_autocertificazione = stampa_autocertificazione;
    }

    /**
     *
     * @return
     */
    public String getIp_soglia_antiriciclaggio() {
        return this.ip_soglia_antiriciclaggio;
    }

    /**
     *
     * @param ip_soglia_antiriciclaggio
     */
    public void setIp_soglia_antiriciclaggio(String ip_soglia_antiriciclaggio) {
        this.ip_soglia_antiriciclaggio = ip_soglia_antiriciclaggio;
    }

    /**
     *
     * @return
     */
    public String getIp_soglia_extraCEE_certification() {
        return this.ip_soglia_extraCEE_certification;
    }

    /**
     *
     * @param ip_soglia_extraCEE_certification
     */
    public void setIp_soglia_extraCEE_certification(String ip_soglia_extraCEE_certification) {
        this.ip_soglia_extraCEE_certification = ip_soglia_extraCEE_certification;
    }
}