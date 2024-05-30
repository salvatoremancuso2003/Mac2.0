/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rc.so.entity;

import static org.apache.commons.lang3.StringUtils.leftPad;

/**
 *
 * @author rcosco
 */
public class Booking {

    String cod, filiale, currency, total, rate, fx_comm, sconti, prodotti,
            dt_ritiro, fg_ritiro_10,
            fg_sconto_giovani, fg_coupon, fg_fxbb, note, crm_note,
            cl_email, cl_nome,
            cl_cognome, cl_telefono, cl_tipologia, cl_aut,
            stato, cod_tr, dt_tr, crv, bookid, euro, canale;

    String dt;
    String pan;

    String cl_sesso, cl_dtnascita, cl_nazione, cl_city, cl_prov, cl_codfisc, cl_indirizzo, cl_indirizzocity, cl_indirizzoprov, cl_indirizzocap, cl_indirizzinazione;

    String cl_prov_D, cl_indirizzoprov_D;

    /**
     * Constructor
     */
    public Booking() {
    }

    /**
     *
     * @param st
     * @return
     */
    public String formatStatus(String st) {
        switch (st) {
            case "0":
                return "<span class='font-blue'>TO BE CONFIRMED <i class='fa fa-hourglass-start'></i></span>";
            case "1":
                return "<span class='font-blue-hoki'>CONFIRMED <i class='fa fa-spinner'></i></span>";
            case "2":
                return "<span class='font-green-jungle'>PROCESSED <i class='fa fa-check'></i></span>";
            case "3":
                return "<span class='font-red'>CANCELLED <i class='fa fa-window-close'></i></span>";
            case "4":
                return "<span class='font-red-haze'>REJECTED <i class='fa fa-trash-o'></i></span>";
            case "5":
                return "<span class='font-grey-salsa'>NOSHOW <i class='fa fa-ban'></i></span>";
            default:
                break;
        }
        return st;
    }

    /**
     *
     * @param st
     * @return
     */
    public String formatStatus_cru(String st) {
        switch (st) {
            case "0":
                return "TO BE CONFIRMED ";
            case "1":
                return "CONFIRMED ";
            case "2":
                return "PROCESSED ";
            case "3":
                return "CANCELLED ";
            case "4":
                return "REJECTED";
            case "5":
                return "NOSHOW ";
            default:
                break;
        }
        return st;
    }

    /**
     *
     * @param disp
     * @param today
     * @param tomorrow
     * @param past
     * @return
     */
    public String formatAlert(boolean disp, boolean today, boolean tomorrow, boolean past) {
        String out = "";
        if (disp) {
            out += "<span class='label label-success'><i class='fa fa-check'></i> Available</span>&nbsp;";
        } else {
            out += "<span class='label label-danger'><i class='fa fa-remove'></i> Not Available</span>&nbsp;";
        }
        if (today) {
            out += "<span class='label label-warning'><i class='fa fa-exclamation-triangle'></i> Today</span>&nbsp;";
        } else if (tomorrow) {
            out += "<span class='label label-info'><i class='fa fa-exclamation-circle'></i> Tomorrow</span>&nbsp;";
        } else if (past) {
            out += "<span class='label label-danger'><i class='fa fa-clock-o'></i> Expired</span>&nbsp;";
        }
        return out;

    }

    /**
     *
     * @param disp
     * @param today
     * @param tomorrow
     * @param past
     * @return
     */
    public String formatAlert_cru(boolean disp, boolean today, boolean tomorrow, boolean past) {
        String out = "";
        if (disp) {
            out += "Available";
        } else {
            out += "Not Available";
        }
        if (today) {
            out += "Today";
        } else if (tomorrow) {
            out += "Tomorrow";
        } else if (past) {
            out += "Expired";
        }
        return out;

    }

    /**
     *
     * @return
     */
    public String getCl_prov_D() {
        return cl_prov_D;
    }

    /**
     *
     * @param cl_prov_D
     */
    public void setCl_prov_D(String cl_prov_D) {
        this.cl_prov_D = cl_prov_D;
    }

    /**
     *
     * @return
     */
    public String getCl_indirizzoprov_D() {
        return cl_indirizzoprov_D;
    }

    /**
     *
     * @param cl_indirizzoprov_D
     */
    public void setCl_indirizzoprov_D(String cl_indirizzoprov_D) {
        this.cl_indirizzoprov_D = cl_indirizzoprov_D;
    }

    /**
     *
     * @return
     */
    public String getCl_sesso() {
        return cl_sesso;
    }

    /**
     *
     * @param cl_sesso
     */
    public void setCl_sesso(String cl_sesso) {
        this.cl_sesso = cl_sesso;
    }

    /**
     *
     * @return
     */
    public String getCl_dtnascita() {
        return cl_dtnascita;
    }

    /**
     *
     * @param cl_dtnascita
     */
    public void setCl_dtnascita(String cl_dtnascita) {
        this.cl_dtnascita = cl_dtnascita;
    }

    /**
     *
     * @return
     */
    public String getCl_nazione() {
        return cl_nazione;
    }

    /**
     *
     * @param cl_nazione
     */
    public void setCl_nazione(String cl_nazione) {

        cl_nazione = leftPad(cl_nazione, 3, "0");

        this.cl_nazione = cl_nazione;
    }

    /**
     *
     * @return
     */
    public String getCl_city() {
        return cl_city;
    }

    /**
     *
     * @param cl_city
     */
    public void setCl_city(String cl_city) {
        this.cl_city = cl_city;
    }

    /**
     *
     * @return
     */
    public String getCl_prov() {
        return cl_prov;
    }

    /**
     *
     * @param cl_prov
     */
    public void setCl_prov(String cl_prov) {
        this.cl_prov = cl_prov;
    }

    /**
     *
     * @return
     */
    public String getCl_codfisc() {
        return cl_codfisc;
    }

    /**
     *
     * @param cl_codfisc
     */
    public void setCl_codfisc(String cl_codfisc) {
        this.cl_codfisc = cl_codfisc;
    }

    /**
     *
     * @return
     */
    public String getCl_indirizzo() {
        return cl_indirizzo;
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
    public String getCl_indirizzocity() {
        return cl_indirizzocity;
    }

    /**
     *
     * @param cl_indirizzocity
     */
    public void setCl_indirizzocity(String cl_indirizzocity) {
        this.cl_indirizzocity = cl_indirizzocity;
    }

    /**
     *
     * @return
     */
    public String getCl_indirizzoprov() {
        return cl_indirizzoprov;
    }

    /**
     *
     * @param cl_indirizzoprov
     */
    public void setCl_indirizzoprov(String cl_indirizzoprov) {
        this.cl_indirizzoprov = cl_indirizzoprov;
    }

    /**
     *
     * @return
     */
    public String getCl_indirizzocap() {
        return cl_indirizzocap;
    }

    /**
     *
     * @param cl_indirizzocap
     */
    public void setCl_indirizzocap(String cl_indirizzocap) {
        if (cl_indirizzocap == null || cl_indirizzocap.equals("00000")) {
            this.cl_indirizzocap = "";
        } else {
            this.cl_indirizzocap = cl_indirizzocap.trim();
        }
    }

    /**
     *
     * @return
     */
    public String getCl_indirizzinazione() {
        return cl_indirizzinazione;
    }

    /**
     *
     * @param cl_indirizzinazione
     */
    public void setCl_indirizzinazione(String cl_indirizzinazione) {
        cl_indirizzinazione = leftPad(cl_indirizzinazione, 3, "0");
        this.cl_indirizzinazione = cl_indirizzinazione;
    }

    /**
     *
     * @return
     */
    public String getPan() {
        return pan;
    }

    /**
     *
     * @param pan
     */
    public void setPan(String pan) {
        this.pan = pan;
    }

    /**
     *
     * @return
     */
    public String getCrm_note() {
        return crm_note;
    }

    /**
     *
     * @param crm_note
     */
    public void setCrm_note(String crm_note) {
        this.crm_note = crm_note;
    }

    /**
     *
     * @return
     */
    public String getCanale() {
        return canale;
    }

    /**
     *
     * @param canale
     */
    public void setCanale(String canale) {
        this.canale = canale;
    }

    /**
     *
     * @return
     */
    public String getDt() {
        return dt;
    }

    /**
     *
     * @param dt
     */
    public void setDt(String dt) {
        this.dt = dt;
    }

    /**
     *
     * @return
     */
    public String getEuro() {
        return euro;
    }

    /**
     *
     * @param euro
     */
    public void setEuro(String euro) {
        this.euro = euro;
    }

    /**
     *
     * @return
     */
    public String getCrv() {
        return crv;
    }

    /**
     *
     * @param crv
     */
    public void setCrv(String crv) {
        this.crv = crv;
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
    public String getCurrency() {
        return currency;
    }

    /**
     *
     * @param currency
     */
    public void setCurrency(String currency) {
        this.currency = currency;
    }

    /**
     *
     * @return
     */
    public String getTotal() {
        return total;
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
    public String getDt_ritiro() {
        return dt_ritiro;
    }

    /**
     *
     * @param dt_ritiro
     */
    public void setDt_ritiro(String dt_ritiro) {
        this.dt_ritiro = dt_ritiro;
    }

    /**
     *
     * @return
     */
    public String getFg_ritiro_10() {
        return fg_ritiro_10;
    }

    /**
     *
     * @param fg_ritiro_10
     */
    public void setFg_ritiro_10(String fg_ritiro_10) {
        this.fg_ritiro_10 = fg_ritiro_10;
    }

    /**
     *
     * @return
     */
    public String getFg_sconto_giovani() {
        return fg_sconto_giovani;
    }

    /**
     *
     * @param fg_sconto_giovani
     */
    public void setFg_sconto_giovani(String fg_sconto_giovani) {
        this.fg_sconto_giovani = fg_sconto_giovani;
    }

    /**
     *
     * @return
     */
    public String getFg_coupon() {
        return fg_coupon;
    }

    /**
     *
     * @param fg_coupon
     */
    public void setFg_coupon(String fg_coupon) {
        this.fg_coupon = fg_coupon;
    }

    /**
     *
     * @return
     */
    public String getFg_fxbb() {
        return fg_fxbb;
    }

    /**
     *
     * @param fg_fxbb
     */
    public void setFg_fxbb(String fg_fxbb) {
        this.fg_fxbb = fg_fxbb;
    }

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
    public String getCl_email() {
        return cl_email;
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
    public String getRate() {
        return rate;
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
    public String getFx_comm() {
        return fx_comm;
    }

    /**
     *
     * @param fx_comm
     */
    public void setFx_comm(String fx_comm) {
        this.fx_comm = fx_comm;
    }

    /**
     *
     * @return
     */
    public String getSconti() {
        return sconti;
    }

    /**
     *
     * @param sconti
     */
    public void setSconti(String sconti) {
        this.sconti = sconti;
    }

    /**
     *
     * @return
     */
    public String getCl_nome() {
        return cl_nome;
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
    public String getCl_cognome() {
        return cl_cognome;
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
    public String getCl_telefono() {
        return cl_telefono;
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
    public String getCl_tipologia() {
        return cl_tipologia;
    }

    /**
     *
     * @param cl_tipologia
     */
    public void setCl_tipologia(String cl_tipologia) {
        this.cl_tipologia = cl_tipologia;
    }

    /**
     *
     * @return
     */
    public String getCl_aut() {
        return cl_aut;
    }

    /**
     *
     * @param cl_aut
     */
    public void setCl_aut(String cl_aut) {
        this.cl_aut = cl_aut;
    }

    /**
     *
     * @return
     */
    public String getStato() {
        return stato;
    }

    /**
     *
     * @param stato
     */
    public void setStato(String stato) {
        this.stato = stato;
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
    public String getDt_tr() {
        return dt_tr;
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
    public String getBookid() {
        return bookid;
    }

    /**
     *
     * @param bookid
     */
    public void setBookid(String bookid) {
        this.bookid = bookid;
    }

    /**
     *
     * @return
     */
    public String getProdotti() {
        return prodotti;
    }

    /**
     *
     * @param prodotti
     */
    public void setProdotti(String prodotti) {
        this.prodotti = prodotti;
    }

}
