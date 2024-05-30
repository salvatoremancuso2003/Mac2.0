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
public class DailyBank {
    
    String bank, codice, ntrans, amount;

    /**
     *
     * @param bank
     * @param codice
     */
    public DailyBank(String bank, String codice) {
        this.bank = bank;
        this.codice = codice;
        this.ntrans = "0";
        this.amount = "0.00";
    }

    /**
     ** Constructor
     */
    public DailyBank() {
    }
    
    /**
     *
     * @param list
     * @param codice
     * @return
     */
    public static DailyBank get_obj(ArrayList<DailyBank> list, String codice){
        for(int i = 0;i<list.size();i++){
            if(list.get(i).getCodice().equals(codice)){
                return list.get(i);
            }
        }
        return null;
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
    public String getBank() {
        return bank;
    }

    /**
     *
     * @param bank
     */
    public void setBank(String bank) {
        this.bank = bank;
    }

    /**
     *
     * @return
     */
    public String getNtrans() {
        return ntrans;
    }

    /**
     *
     * @param ntrans
     */
    public void setNtrans(String ntrans) {
        this.ntrans = ntrans;
    }

    /**
     *
     * @return
     */
    public String getAmount() {
        return amount;
    }

    /**
     *
     * @param amount
     */
    public void setAmount(String amount) {
        this.amount = amount;
    }
    
    
}


