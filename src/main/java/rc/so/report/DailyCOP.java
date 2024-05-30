/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rc.so.report;

import java.util.ArrayList;

/**
 *
 * @author fplacanica
 */
public class DailyCOP {
    
    String pos,cashAdNtrans, cashAdAmount, ccNtrans, ccAmount, bankNtrans, bankAmount;
    String codice;
    String cashAdComm;
    
    String NC_ccNtrans, NC_ccAmount, NC_bankNtrans, NC_bankAmount;
    
    /**
     *
     * @param pos
     * @param codice
     */
    public DailyCOP(String pos, String codice) {
        this.pos = pos;
        this.cashAdNtrans = "0";
        this.cashAdAmount = "0.00";
        this.ccNtrans = "0";
        this.ccAmount = "0.00";
        this.bankNtrans = "0";
        this.bankAmount = "0.00";
        this.codice = codice;
        this.cashAdComm = "0.00";
        this.NC_ccNtrans ="0";
        this.NC_ccAmount ="0.00";
        this.NC_bankNtrans="0";
        this.NC_bankAmount="0.00";
    }
    
    /**
     *
     * @param list
     * @param codice
     * @return
     */
    public static DailyCOP get_obj(ArrayList<DailyCOP> list, String codice){
        for(int i = 0;i<list.size();i++){
            if(list.get(i).getCodice().equals(codice)){
                return list.get(i);
            }
        }
        return null;
    }
    
    /**
     ** Constructor
     */
    public DailyCOP() {
    }

    /**
     *
     * @return
     */
    public String getNC_ccNtrans() {
        return NC_ccNtrans;
    }

    /**
     *
     * @param NC_ccNtrans
     */
    public void setNC_ccNtrans(String NC_ccNtrans) {
        this.NC_ccNtrans = NC_ccNtrans;
    }

    /**
     *
     * @return
     */
    public String getNC_ccAmount() {
        return NC_ccAmount;
    }

    /**
     *
     * @param NC_ccAmount
     */
    public void setNC_ccAmount(String NC_ccAmount) {
        this.NC_ccAmount = NC_ccAmount;
    }

    /**
     *
     * @return
     */
    public String getNC_bankNtrans() {
        return NC_bankNtrans;
    }

    /**
     *
     * @param NC_bankNtrans
     */
    public void setNC_bankNtrans(String NC_bankNtrans) {
        this.NC_bankNtrans = NC_bankNtrans;
    }

    /**
     *
     * @return
     */
    public String getNC_bankAmount() {
        return NC_bankAmount;
    }

    /**
     *
     * @param NC_bankAmount
     */
    public void setNC_bankAmount(String NC_bankAmount) {
        this.NC_bankAmount = NC_bankAmount;
    }
    
    /**
     *
     * @return
     */
    public String getCodice() {
        return codice;
    }

    /**
     *
     * @param codice
     */
    public void setCodice(String codice) {
        this.codice = codice;
    }
    
    /**
     *
     * @return
     */
    public String getCashAdComm() {
        return cashAdComm;
    }

    /**
     *
     * @param cashAdComm
     */
    public void setCashAdComm(String cashAdComm) {
        this.cashAdComm = cashAdComm;
    }

    /**
     *
     * @return
     */
    public String getPos() {
        return pos;
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
    public String getCashAdNtrans() {
        return cashAdNtrans;
    }

    /**
     *
     * @param cashAdNtrans
     */
    public void setCashAdNtrans(String cashAdNtrans) {
        this.cashAdNtrans = cashAdNtrans;
    }

    /**
     *
     * @return
     */
    public String getCashAdAmount() {
        return cashAdAmount;
    }

    /**
     *
     * @param cashAdAmount
     */
    public void setCashAdAmount(String cashAdAmount) {
        this.cashAdAmount = cashAdAmount;
    }

    /**
     *
     * @return
     */
    public String getCcNtrans() {
        return ccNtrans;
    }

    /**
     *
     * @param ccNtrans
     */
    public void setCcNtrans(String ccNtrans) {
        this.ccNtrans = ccNtrans;
    }

    /**
     *
     * @return
     */
    public String getCcAmount() {
        return ccAmount;
    }

    /**
     *
     * @param ccAmount
     */
    public void setCcAmount(String ccAmount) {
        this.ccAmount = ccAmount;
    }

    /**
     *
     * @return
     */
    public String getBankNtrans() {
        return bankNtrans;
    }

    /**
     *
     * @param bankNtrans
     */
    public void setBankNtrans(String bankNtrans) {
        this.bankNtrans = bankNtrans;
    }

    /**
     *
     * @return
     */
    public String getBankAmount() {
        return bankAmount;
    }

    /**
     *
     * @param bankAmount
     */
    public void setBankAmount(String bankAmount) {
        this.bankAmount = bankAmount;
    }
    
    

       
    
}


