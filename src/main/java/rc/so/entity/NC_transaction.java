package rc.so.entity;

import static rc.so.util.Constant.is_CZ;
import static rc.so.util.Constant.is_UK;

/**
 *
 * @author rcosco
 */
public class NC_transaction {

    String cod;
    String id;
    String filiale;
    String gruppo_nc;
    String causale_nc;
    String valuta;
    String supporto;
    String pos;
    String user;
    String till;
    String id_open_till;
    String data;
    String total;
    String commissione;
    String netto;
    String prezzo;
    String quantita;
    String fg_inout;
    String ricevuta;
    String mtcn;

    String del_fg;
    String del_dt;
    String del_user;
    String del_motiv;
    String fg_tipo_transazione_nc;

    String fg_dogana;
    String ass_idcode;
    String ass_startdate;
    String ass_enddate;
    String cl_cognome;
    String cl_nome;

    String cl_indirizzo;

    String posnum;

    String cl_citta;
    String cl_nazione;

    String percentiva;
    String bonus;

    String ch_transaction;

    String docrico;

    /**
     * Constructor
     */
    public NC_transaction() {

        if (is_UK) {
            this.valuta = "GBP";
        } else if (is_CZ) {
            this.valuta = "CZK";
        } else {
            this.valuta = "EUR";
        }

        this.supporto = "01";
        this.pos = "000";
        this.total = "0.00";
        this.commissione = "0.00";
        this.netto = "0.00";
        this.prezzo = "0.00";
        this.quantita = "0.00";
        this.fg_inout = "1";
        this.ricevuta = "-";
        this.mtcn = "-";
        this.del_fg = "0";
        this.del_user = "-";
        this.del_motiv = "-";
        this.fg_tipo_transazione_nc = "0";
        this.fg_dogana = "00";
        this.ass_idcode = "-";
        this.ass_startdate = "-";
        this.ass_enddate = "-";
        this.cl_cognome = "-";
        this.cl_nome = "-";
        this.cl_indirizzo = "-";
        this.cl_citta = "-";
        this.cl_nazione = "-";
        this.cl_cap = "-";
        this.cl_provincia = "-";
        this.cl_email = "-";
        this.cl_telefono = "-";
        this.note = "-";
        this.ti_diritti = "0.00";
        this.ti_ticket_fee = "0.00";
        this.posnum = "-";
        this.percentiva = "-";
        this.bonus = "0";
        this.ch_transaction = "-";
        this.docrico = "-";
    }

//  public NC_transaction(String filiale)
//  {
//    this.filiale = filiale;
//    this.cod = Utility.generaIdMAC(this.filiale);
//    this.id = setId_db();
//    this.gruppo_nc = "-";
//    this.causale_nc = "-";
//    this.valuta = "EUR";
//    this.supporto = "01";
//    this.pos = "000";
//    this.user = "-";
//    this.till = "-";
//    this.id_open_till = "-";
//    this.total = "0.00";
//    this.commissione = "0.00";
//    this.netto = "0.00";
//    this.prezzo = "0.00";
//    this.quantita = "0.00";
//    this.fg_inout = "1";
//    this.ricevuta = "-";
//    this.mtcn = "-";
//    this.del_fg = "0";
//    this.del_user = "-";
//    this.del_motiv = "-";
//    this.fg_tipo_transazione_nc = "0";
//    this.fg_dogana = "00";
//    this.ass_idcode = "-";
//    this.ass_startdate = "-";
//    this.ass_enddate = "-";
//    this.cl_cognome = "-";
//    this.cl_nome = "-";
//    this.cl_indirizzo = "-";
//    this.cl_citta = "-";
//    this.cl_nazione = "-";
//    this.cl_cap = "-";
//    this.cl_provincia = "-";
//    this.cl_email = "-";
//    this.cl_telefono = "-";
//    this.note = "-";
//    this.ti_diritti = "0.00";
//    this.ti_ticket_fee = "0.00";
//  }
//  
//  

    /**
     *
     * @param cod
     * @param id
     * @param filiale
     * @param gruppo_nc
     * @param causale_nc
     * @param valuta
     * @param supporto
     * @param pos
     * @param user
     * @param till
     * @param data
     * @param total
     * @param commissione
     * @param netto
     * @param prezzo
     * @param quantita
     * @param fg_inout
     * @param ricevuta
     * @param mtcn
     * @param del_fg
     * @param del_dt
     * @param del_user
     * @param del_motiv
     * @param fg_tipo_transazione_nc
     * @param fg_dogana
     * @param ass_idcode
     * @param ass_startdate
     * @param ass_enddate
     * @param cl_cognome
     * @param cl_nome
     * @param cl_indirizzo
     * @param cl_citta
     * @param cl_nazione
     * @param cl_cap
     * @param cl_provincia
     * @param cl_email
     * @param cl_telefono
     * @param note
     * @param ti_diritti
     * @param ti_ticket_fee
     * @param id_open_till
     * @param posnum
     * @param percentiva
     * @param bonus
     * @param ch_transaction
     * @param docrico
     */
    public NC_transaction(String cod, String id, String filiale, String gruppo_nc, String causale_nc,
            String valuta, String supporto, String pos, String user, String till, String data,
            String total, String commissione, String netto, String prezzo, String quantita, String fg_inout, String ricevuta,
            String mtcn, String del_fg, String del_dt, String del_user, String del_motiv, String fg_tipo_transazione_nc,
            String fg_dogana, String ass_idcode, String ass_startdate, String ass_enddate, String cl_cognome, String cl_nome,
            String cl_indirizzo, String cl_citta, String cl_nazione, String cl_cap, String cl_provincia, String cl_email,
            String cl_telefono, String note, String ti_diritti, String ti_ticket_fee, String id_open_till, String posnum, String percentiva, String bonus, String ch_transaction, String docrico) {
        this.cod = cod;
        this.id = id;
        this.filiale = filiale;
        this.gruppo_nc = gruppo_nc;
        this.causale_nc = causale_nc;
        this.valuta = valuta;
        this.supporto = supporto;
        this.pos = pos;
        this.user = user;
        this.till = till;
        this.id_open_till = id_open_till;
        this.data = data;
        this.total = total;
        this.commissione = commissione;
        this.netto = netto;
        this.prezzo = prezzo;
        this.quantita = quantita;
        this.fg_inout = fg_inout;
        this.ricevuta = ricevuta;
        this.mtcn = mtcn;
        this.del_fg = del_fg;
        this.del_dt = del_dt;
        this.del_user = del_user;
        this.del_motiv = del_motiv;
        this.fg_tipo_transazione_nc = fg_tipo_transazione_nc;
        this.fg_dogana = fg_dogana;
        this.ass_idcode = ass_idcode;
        this.ass_startdate = ass_startdate;
        this.ass_enddate = ass_enddate;
        this.cl_cognome = cl_cognome;
        this.cl_nome = cl_nome;
        this.cl_indirizzo = cl_indirizzo;
        this.cl_citta = cl_citta;
        this.cl_nazione = cl_nazione;
        this.cl_cap = cl_cap;
        this.cl_provincia = cl_provincia;
        this.cl_email = cl_email;
        this.cl_telefono = cl_telefono;
        this.note = note;
        this.ti_diritti = ti_diritti;
        this.ti_ticket_fee = ti_ticket_fee;
        this.posnum = posnum;
        this.percentiva = percentiva;
        this.bonus = bonus;
        this.ch_transaction = ch_transaction;
        this.docrico = docrico;
    }

    /**
     *
     * @return
     */
    public String getDocrico() {
        return docrico;
    }

    /**
     *
     * @param docrico
     */
    public void setDocrico(String docrico) {
        this.docrico = docrico;
    }

    /**
     *
     * @return
     */
    public String getCh_transaction() {
        return ch_transaction;
    }

    /**
     *
     * @param ch_transaction
     */
    public void setCh_transaction(String ch_transaction) {
        this.ch_transaction = ch_transaction;
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
    public String getPercentiva() {
        return percentiva;
    }

//  private String setId_db()
//  {
//    Db_Master db = new Db_Master();
//    int value = db.getlastId_nc_trans();
//    db.closeDB();
//    if (value > -1) {
//      value++;
//      return Utility.fillLeftInt(value, 15, "0");
//    }
//    return "ERROR";
//  }

    /**
     *
     * @param del_fg
     * @return
     */
    public String formatStatus(String del_fg) {
        if (del_fg != null) {
            if (del_fg.equals("0")) {
                return "<div class='font-green-jungle'>Active <i class='fa fa-check'></i></div>";
            }
            if (del_fg.equals("1")) {
                return "<div class='font-red'>Deleted <i class='fa fa-remove'></i></div>";
            }
        }
        return "-";
    }

    /**
     *
     * @param del_fg
     * @return
     */
    public String formatStatus_cru(String del_fg) {
        if (del_fg != null) {
            if (del_fg.equals("0")) {
                return "Active";
            }
            if (del_fg.equals("1")) {
                return "Deleted";
            }
        }
        return "-";
    }

    /**
     *
     * @param percentiva
     */
    public void setPercentiva(String percentiva) {
        this.percentiva = percentiva;
    }

    /**
     *
     * @return
     */
    public String getPosnum() {
        return posnum;
    }

    /**
     *
     * @param posnum
     */
    public void setPosnum(String posnum) {
        this.posnum = posnum;
    }

    /**
     *
     * @return
     */
    public String getId_open_till() {
        return this.id_open_till;
    }

    String cl_cap;

    /**
     *
     * @param id_open_till
     */
    public void setId_open_till(String id_open_till) {
        this.id_open_till = id_open_till;
    }

    String cl_provincia;

    /**
     *
     * @return
     */
    public String getCod() {
        return this.cod;
    }

    String cl_email;
    String cl_telefono;
    String note;

    /**
     *
     * @param cod
     */
    public void setCod(String cod) {
        this.cod = cod;
    }

    String ti_diritti;

    /**
     *
     * @return
     */
    public String getId() {
        return this.id;
    }

    String ti_ticket_fee;

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
    public String getUser() {
        return this.user;
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
    public String getTill() {
        return this.till;
    }

    /**
     *
     * @param till
     */
    public void setTill(String till) {
        this.till = till;
    }

    /**
     *
     * @return
     */
    public String getData() {
        return this.data;
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
    public String getCommissione() {
        return this.commissione;
    }

    /**
     *
     * @param commissione
     */
    public void setCommissione(String commissione) {
        this.commissione = commissione;
    }

    /**
     *
     * @return
     */
    public String getNetto() {
        return this.netto;
    }

    /**
     *
     * @param netto
     */
    public void setNetto(String netto) {
        this.netto = netto;
    }

    /**
     *
     * @return
     */
    public String getPrezzo() {
        return this.prezzo;
    }

    /**
     *
     * @param prezzo
     */
    public void setPrezzo(String prezzo) {
        this.prezzo = prezzo;
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
    public String getFg_inout() {
        return this.fg_inout;
    }

    /**
     *
     * @param fg_inout
     */
    public void setFg_inout(String fg_inout) {
        this.fg_inout = fg_inout;
    }

    /**
     *
     * @return
     */
    public String getRicevuta() {
        return this.ricevuta;
    }

    /**
     *
     * @param ricevuta
     */
    public void setRicevuta(String ricevuta) {
        this.ricevuta = ricevuta;
    }

    /**
     *
     * @return
     */
    public String getMtcn() {
        return this.mtcn;
    }

    /**
     *
     * @param mtcn
     */
    public void setMtcn(String mtcn) {
        this.mtcn = mtcn;
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

    /**
     *
     * @return
     */
    public String getDel_user() {
        return this.del_user;
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
        return this.del_motiv;
    }

    /**
     *
     * @param del_motiv
     */
    public void setDel_motiv(String del_motiv) {
        this.del_motiv = del_motiv;
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
    public String getFg_dogana() {
        return this.fg_dogana;
    }

    /**
     *
     * @param fg_dogana
     */
    public void setFg_dogana(String fg_dogana) {
        this.fg_dogana = fg_dogana;
    }

    /**
     *
     * @return
     */
    public String getAss_idcode() {
        return this.ass_idcode;
    }

    /**
     *
     * @param ass_idcode
     */
    public void setAss_idcode(String ass_idcode) {
        this.ass_idcode = ass_idcode;
    }

    /**
     *
     * @return
     */
    public String getAss_startdate() {
        return this.ass_startdate;
    }

    /**
     *
     * @param ass_startdate
     */
    public void setAss_startdate(String ass_startdate) {
        this.ass_startdate = ass_startdate;
    }

    /**
     *
     * @return
     */
    public String getAss_enddate() {
        return this.ass_enddate;
    }

    /**
     *
     * @param ass_enddate
     */
    public void setAss_enddate(String ass_enddate) {
        this.ass_enddate = ass_enddate;
    }

    /**
     *
     * @return
     */
    public String getCl_cognome() {
        return this.cl_cognome;
    }

    /**
     *
     * @param cl_cognome
     */
    public void setCl_cognome(String cl_cognome) {
        this.cl_cognome = cl_cognome;
    }

    /**
     *
     * @return
     */
    public String getCl_nome() {
        return this.cl_nome;
    }

    /**
     *
     * @param cl_nome
     */
    public void setCl_nome(String cl_nome) {
        this.cl_nome = cl_nome;
    }

    /**
     *
     * @return
     */
    public String getCl_indirizzo() {
        return this.cl_indirizzo;
    }

    /**
     *
     * @param cl_indirizzo
     */
    public void setCl_indirizzo(String cl_indirizzo) {
        this.cl_indirizzo = cl_indirizzo;
    }

    /**
     *
     * @return
     */
    public String getCl_citta() {
        return this.cl_citta;
    }

    /**
     *
     * @param cl_citta
     */
    public void setCl_citta(String cl_citta) {
        this.cl_citta = cl_citta;
    }

    /**
     *
     * @return
     */
    public String getCl_nazione() {
        return this.cl_nazione;
    }

    /**
     *
     * @param cl_nazione
     */
    public void setCl_nazione(String cl_nazione) {
        this.cl_nazione = cl_nazione;
    }

    /**
     *
     * @return
     */
    public String getCl_cap() {
        return this.cl_cap;
    }

    /**
     *
     * @param cl_cap
     */
    public void setCl_cap(String cl_cap) {
        this.cl_cap = cl_cap;
    }

    /**
     *
     * @return
     */
    public String getCl_provincia() {
        return this.cl_provincia;
    }

    /**
     *
     * @param cl_provincia
     */
    public void setCl_provincia(String cl_provincia) {
        this.cl_provincia = cl_provincia;
    }

    /**
     *
     * @return
     */
    public String getCl_email() {
        return this.cl_email;
    }

    /**
     *
     * @param cl_email
     */
    public void setCl_email(String cl_email) {
        this.cl_email = cl_email;
    }

    /**
     *
     * @return
     */
    public String getCl_telefono() {
        return this.cl_telefono;
    }

    /**
     *
     * @param cl_telefono
     */
    public void setCl_telefono(String cl_telefono) {
        this.cl_telefono = cl_telefono;
    }

    /**
     *
     * @return
     */
    public String getNote() {
        return this.note;
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
    public String getTi_diritti() {
        return this.ti_diritti;
    }

    /**
     *
     * @param ti_diritti
     */
    public void setTi_diritti(String ti_diritti) {
        this.ti_diritti = ti_diritti;
    }

    /**
     *
     * @return
     */
    public String getTi_ticket_fee() {
        return this.ti_ticket_fee;
    }

    /**
     *
     * @param ti_ticket_fee
     */
    public void setTi_ticket_fee(String ti_ticket_fee) {
        this.ti_ticket_fee = ti_ticket_fee;
    }
}


/* Location:              C:\Users\rcosco\Desktop\classes\!\entity\NC_transaction.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */
