/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rc.so.excel;

/**
 *
 * @author rcosco
 */
public class BudgetTill implements Comparable<BudgetTill>{
    
    String filiale,giorno,giornoStringa;
    String cop,fx,tot;
    String cop_ap,fx_ap,tot_ap;
    String budget,gruppo;

    /**
     * Constructor
     */
    public BudgetTill() {
    }

    /**
     *
     * @param filiale
     * @param giorno
     * @param giornoStringa
     * @param cop
     * @param fx
     * @param tot
     * @param cop_ap
     * @param fx_ap
     * @param tot_ap
     * @param budget
     * @param gruppo
     */
    public BudgetTill(String filiale, String giorno, String giornoStringa, String cop, String fx, String tot, String cop_ap, String fx_ap, String tot_ap, String budget, String gruppo) {
        this.filiale = filiale;
        this.giorno = giorno;
        this.giornoStringa = giornoStringa;
        this.cop = cop;
        this.fx = fx;
        this.tot = tot;
        this.cop_ap = cop_ap;
        this.fx_ap = fx_ap;
        this.tot_ap = tot_ap;
        this.budget = budget;
        this.gruppo = gruppo;
    }

    /**
     *
     * @return
     */
    public String getGruppo() {
        return gruppo;
    }

    /**
     *
     * @param gruppo
     */
    public void setGruppo(String gruppo) {
        this.gruppo = gruppo;
    }

    /**
     *
     * @return
     */
    public String getCop_ap() {
        return cop_ap;
    }

    /**
     *
     * @param cop_ap
     */
    public void setCop_ap(String cop_ap) {
        this.cop_ap = cop_ap;
    }

    /**
     *
     * @return
     */
    public String getFx_ap() {
        return fx_ap;
    }

    /**
     *
     * @param fx_ap
     */
    public void setFx_ap(String fx_ap) {
        this.fx_ap = fx_ap;
    }

    /**
     *
     * @return
     */
    public String getTot_ap() {
        return tot_ap;
    }

    /**
     *
     * @param tot_ap
     */
    public void setTot_ap(String tot_ap) {
        this.tot_ap = tot_ap;
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
    public String getGiorno() {
        return giorno;
    }

    /**
     *
     * @param giorno
     */
    public void setGiorno(String giorno) {
        this.giorno = giorno;
    }

    /**
     *
     * @return
     */
    public String getGiornoStringa() {
        return giornoStringa;
    }

    /**
     *
     * @param giornoStringa
     */
    public void setGiornoStringa(String giornoStringa) {
        this.giornoStringa = giornoStringa;
    }

    /**
     *
     * @return
     */
    public String getCop() {
        return cop;
    }

    /**
     *
     * @param cop
     */
    public void setCop(String cop) {
        this.cop = cop;
    }

    /**
     *
     * @return
     */
    public String getFx() {
        return fx;
    }

    /**
     *
     * @param fx
     */
    public void setFx(String fx) {
        this.fx = fx;
    }

    /**
     *
     * @return
     */
    public String getTot() {
        return tot;
    }

    /**
     *
     * @param tot
     */
    public void setTot(String tot) {
        this.tot = tot;
    }
    
    /**
     *
     * @return
     */
    public String getBudget() {
        return budget;
    }

    /**
     *
     * @param budget
     */
    public void setBudget(String budget) {
        this.budget = budget;
    }

    @Override
    public int compareTo(BudgetTill o) {
        return this.gruppo.compareTo(o.getGruppo());
    }
    
}