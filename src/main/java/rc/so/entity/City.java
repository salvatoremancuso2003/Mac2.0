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
public class City {
    String codice_avv_bancario,denominazione;

    /**
     *
     * @param codice_avv_bancario
     * @param denominazione
     */
    public City(String codice_avv_bancario, String denominazione) {
        this.codice_avv_bancario = codice_avv_bancario;
        this.denominazione = denominazione;
    }
    
    /**
     *
     * @return
     */
    public String getCodice_avv_bancario() {
        return codice_avv_bancario;
    }

    /**
     *
     * @param codice_avv_bancario
     */
    public void setCodice_avv_bancario(String codice_avv_bancario) {
        this.codice_avv_bancario = codice_avv_bancario;
    }

    /**
     *
     * @return
     */
    public String getDenominazione() {
        return denominazione;
    }

    /**
     *
     * @param denominazione
     */
    public void setDenominazione(String denominazione) {
        this.denominazione = denominazione;
    }
    
    
}
