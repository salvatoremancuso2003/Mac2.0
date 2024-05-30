package rc.so.entity;

import static java.nio.charset.StandardCharsets.ISO_8859_1;
import java.util.HashMap;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import static org.apache.commons.lang3.builder.ToStringStyle.JSON_STYLE;

/**
 *
 * @author rcosco
 */
public class Client {

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
    String pep;
    String datatr;

    Client_CZ repceca;

    /**
     * Constructor
     */
    public Client() {
        this.repceca = null;
        this.cognome = "";
        this.nome = "";
        this.codfisc = "";
        this.code = "---";
        this.nazione = "";
        this.nazione_nascita = "";
        this.indirizzo = "";
        this.citta = "";
        this.sesso = "";

    }

    /**
     *
     * @return
     */
    public Client_CZ getRepceca() {
        return repceca;
    }

    /**
     *
     * @param repceca
     */
    public void setRepceca(Client_CZ repceca) {
        this.repceca = repceca;
    }

    /**
     *
     * @return
     */
    public String getDatatr() {
        return datatr;
    }

    /**
     *
     * @param datatr
     */
    public void setDatatr(String datatr) {
        this.datatr = datatr;
    }

    /**
     *
     * @return
     */
    public String getPep() {
        return pep;
    }

    /**
     *
     * @param pep
     */
    public void setPep(String pep) {
        this.pep = pep;
    }

    /**
     *
     * @return
     */
    public String getCode() {
        return this.code;
    }

    String numero_documento;

    /**
     *
     * @param code
     */
    public void setCode(String code) {
        this.code = code;
    }

    String dt_rilascio_documento;

    /**
     *
     * @return
     */
    public String getCognome() {
        return this.cognome;
    }

    String dt_scadenza_documento;
    String rilasciato_da_documento;
    String luogo_rilascio_documento;

    /**
     *
     * @param cognome
     */
    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    String email;

    /**
     *
     * @return
     */
    public String getNome() {
        return this.nome;
    }

    String telefono;

    /**
     *
     * @param nome
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    String perc_sell;

    /**
     *
     * @return
     */
    public String getSesso() {
        return this.sesso;
    }

    String perc_buy;

    /**
     *
     * @param sesso
     */
    public void setSesso(String sesso) {
        this.sesso = sesso;
    }

    String timestamp;

    String codicemodifica;
    String tipomodifica;
    String usermodifica;
    String datemodifica;

    /**
     *
     * @return
     */
    public String getTipomodifica() {
        return tipomodifica;
    }

    /**
     *
     * @param tipomodifica
     */
    public void setTipomodifica(String tipomodifica) {
        this.tipomodifica = tipomodifica;
    }

    /**
     *
     * @return
     */
    public String getUsermodifica() {
        return usermodifica;
    }

    /**
     *
     * @param usermodifica
     */
    public void setUsermodifica(String usermodifica) {
        this.usermodifica = usermodifica;
    }

    /**
     *
     * @return
     */
    public String getDatemodifica() {
        return datemodifica;
    }

    /**
     *
     * @param datemodifica
     */
    public void setDatemodifica(String datemodifica) {
        this.datemodifica = datemodifica;
    }

    /**
     *
     * @return
     */
    public String getCodicemodifica() {
        return codicemodifica;
    }

    /**
     *
     * @param codicemodifica
     */
    public void setCodicemodifica(String codicemodifica) {
        this.codicemodifica = codicemodifica;
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
        try {
            this.email = new String(email.getBytes(ISO_8859_1), "UTF-8");
        } catch (Exception ex) {
            this.email = "";
        }
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
    public String getPerc_sell() {
        return this.perc_sell;
    }

    /**
     *
     * @param perc_sell
     */
    public void setPerc_sell(String perc_sell) {
        this.perc_sell = perc_sell;
    }

    /**
     *
     * @return
     */
    public String getPerc_buy() {
        return this.perc_buy;
    }

    /**
     *
     * @param perc_buy
     */
    public void setPerc_buy(String perc_buy) {
        this.perc_buy = perc_buy;
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

    /**
     *
     */
    public HashMap<String, String[]> modifiche;

    /**
     *
     * @return
     */
    public HashMap<String, String[]> getModifiche() {
        return modifiche;
    }

    /**
     *
     * @param modifiche
     */
    public void setModifiche(HashMap<String, String[]> modifiche) {
        this.modifiche = modifiche;
    }

    
    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, JSON_STYLE);
    }
}
