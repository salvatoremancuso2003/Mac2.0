package rc.so.entity;

/**
 *
 * @author rcosco
 */
public class BlacklistM {

    String code;
    String cognome;
    String nome;
    String sesso;
    String codfisc;
    String nazione;
    String citta;
    String indirizzo;
    String cap;
    String provincia;
    String citta_nascita;
    String provincia_nascita;
    String nazione_nascita;
    String dt_nascita;
    String tipo_documento;
    String numero_documento;
    
    /**
     *
     * @return
     */
    public String getText() {
        return this.text;
    }
    
    /**
     *
     * @return
     */
    public String getText_cru() {
        return this.text.replaceAll("<[^>]*>","");
    }

    String dt_rilascio_documento;

    /**
     *
     * @param text
     */
    public void setText(String text) {
        this.text = text;
    }

    String dt_scadenza_documento;

    /**
     *
     * @return
     */
    public String getCode() {
        return this.code;
    }

    String rilasciato_da_documento;
    String luogo_rilascio_documento;
    String email;

    /**
     *
     * @param code
     */
    public void setCode(String code) {
        this.code = code;
    }

    String telefono;

    /**
     *
     * @return
     */
    public String getCognome() {
        return this.cognome;
    }

    String dt_blocco;

    /**
     *
     * @param cognome
     */
    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    String fg_annullato;

    /**
     *
     * @return
     */
    public String getNome() {
        return this.nome;
    }

    String timestamp;

    /**
     *
     * @param nome
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    String text;

    /**
     *
     * @return
     */
    public String getSesso() {
        return this.sesso;
    }

    /**
     *
     * @param sesso
     */
    public void setSesso(String sesso) {
        this.sesso = sesso;
    }

    /**
     *
     * @return
     */
    public String getCodfisc() {
        return this.codfisc;
    }

    /**
     *
     * @param codfisc
     */
    public void setCodfisc(String codfisc) {
        this.codfisc = codfisc;
    }

    /**
     *
     * @return
     */
    public String getNazione() {
        return this.nazione;
    }

    /**
     *
     * @param nazione
     */
    public void setNazione(String nazione) {
        this.nazione = nazione;
    }

    /**
     *
     * @return
     */
    public String getCitta() {
        return this.citta;
    }

    /**
     *
     * @param citta
     */
    public void setCitta(String citta) {
        this.citta = citta;
    }

    /**
     *
     * @return
     */
    public String getIndirizzo() {
        return this.indirizzo;
    }

    /**
     *
     * @param indirizzo
     */
    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }

    /**
     *
     * @return
     */
    public String getCap() {
        return this.cap;
    }

    /**
     *
     * @param cap
     */
    public void setCap(String cap) {
        this.cap = cap;
    }

    /**
     *
     * @return
     */
    public String getProvincia() {
        return this.provincia;
    }

    /**
     *
     * @param provincia
     */
    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    /**
     *
     * @return
     */
    public String getCitta_nascita() {
        return this.citta_nascita;
    }

    /**
     *
     * @param citta_nascita
     */
    public void setCitta_nascita(String citta_nascita) {
        this.citta_nascita = citta_nascita;
    }

    /**
     *
     * @return
     */
    public String getProvincia_nascita() {
        return this.provincia_nascita;
    }

    /**
     *
     * @param provincia_nascita
     */
    public void setProvincia_nascita(String provincia_nascita) {
        this.provincia_nascita = provincia_nascita;
    }

    /**
     *
     * @return
     */
    public String getNazione_nascita() {
        return this.nazione_nascita;
    }

    /**
     *
     * @param nazione_nascita
     */
    public void setNazione_nascita(String nazione_nascita) {
        this.nazione_nascita = nazione_nascita;
    }

    /**
     *
     * @return
     */
    public String getDt_nascita() {
        return this.dt_nascita;
    }

    /**
     *
     * @param dt_nascita
     */
    public void setDt_nascita(String dt_nascita) {
        this.dt_nascita = dt_nascita;
    }

    /**
     *
     * @return
     */
    public String getTipo_documento() {
        return this.tipo_documento;
    }

    /**
     *
     * @param tipo_documento
     */
    public void setTipo_documento(String tipo_documento) {
        this.tipo_documento = tipo_documento;
    }

    /**
     *
     * @return
     */
    public String getNumero_documento() {
        return this.numero_documento;
    }

    /**
     *
     * @param numero_documento
     */
    public void setNumero_documento(String numero_documento) {
        this.numero_documento = numero_documento;
    }

    /**
     *
     * @return
     */
    public String getDt_rilascio_documento() {
        return this.dt_rilascio_documento;
    }

    /**
     *
     * @param dt_rilascio_documento
     */
    public void setDt_rilascio_documento(String dt_rilascio_documento) {
        this.dt_rilascio_documento = dt_rilascio_documento;
    }

    /**
     *
     * @return
     */
    public String getDt_scadenza_documento() {
        return this.dt_scadenza_documento;
    }

    /**
     *
     * @param dt_scadenza_documento
     */
    public void setDt_scadenza_documento(String dt_scadenza_documento) {
        this.dt_scadenza_documento = dt_scadenza_documento;
    }

    /**
     *
     * @return
     */
    public String getRilasciato_da_documento() {
        return this.rilasciato_da_documento;
    }

    /**
     *
     * @param rilasciato_da_documento
     */
    public void setRilasciato_da_documento(String rilasciato_da_documento) {
        this.rilasciato_da_documento = rilasciato_da_documento;
    }

    /**
     *
     * @return
     */
    public String getLuogo_rilascio_documento() {
        return this.luogo_rilascio_documento;
    }

    /**
     *
     * @param luogo_rilascio_documento
     */
    public void setLuogo_rilascio_documento(String luogo_rilascio_documento) {
        this.luogo_rilascio_documento = luogo_rilascio_documento;
    }

    /**
     *
     * @return
     */
    public String getEmail() {
        return this.email;
    }

    /**
     *
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     *
     * @return
     */
    public String getTelefono() {
        return this.telefono;
    }

    /**
     *
     * @param telefono
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    /**
     *
     * @return
     */
    public String getDt_blocco() {
        return this.dt_blocco;
    }

    /**
     *
     * @param dt_blocco
     */
    public void setDt_blocco(String dt_blocco) {
        this.dt_blocco = dt_blocco;
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
    public String getTimestamp() {
        return this.timestamp;
    }

    /**
     *
     * @param timestamp
     */
    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}


/* Location:              C:\Users\rcosco\Desktop\classes\!\entity\BlacklistM.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */
