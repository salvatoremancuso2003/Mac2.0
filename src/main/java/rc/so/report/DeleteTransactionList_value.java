/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rc.so.report;

import java.util.ArrayList;


/**
 *
 * @author srotella
 */
public class DeleteTransactionList_value {
    
     ArrayList<DeleteTransactionList_value> dati;   
    
    String id_filiale, de_filiale,till,user,notr,time,cur,kind,amount,rate,total,perc,comfree,payin,payout,customer,spread,fig;
    
    String transvalueresidentbuy,transvaluenonresidentbuy,commisionvaluetresidentbuy,commisionvaluenonresidentbuy,transactionnumberresidentbuy,transactionnumbernonresidentbuy,internetbookingamountyes,internetbookingnumberyes;
    String transvalueresidentsell,transvaluenonresidentsell,commisionvaluetresidentsell,commisionvaluenonresidentsell,transactionnumberresidentsell,transactionnumbernonresidentsell,internetbookingamountno,internetbookingnumberno;
    
    ArrayList<Double> footerdati;
    
    String residentnoresident="";
    
    String delete="";

    /**
     *
     * @return
     */
    public String getDelete() {
        return delete;
    }

    /**
     *
     * @param delete
     */
    public void setDelete(String delete) {
        this.delete = delete;
    }
    
    /**
     *
     * @return
     */
    public String getResidentnoresident() {
        return residentnoresident;
    }

    /**
     *
     * @param residentnoresident
     */
    public void setResidentnoresident(String residentnoresident) {
        this.residentnoresident = residentnoresident;
    }

    /**
     *
     * @return
     */
    public ArrayList<Double> getFooterdati() {
        return footerdati;
    }

    /**
     *
     * @param footerdati
     */
    public void setFooterdati(ArrayList<Double> footerdati) {
        this.footerdati = footerdati;
    }

    /**
     *
     * @return
     */
    public String getTransvalueresidentbuy() {
        return transvalueresidentbuy;
    }

    /**
     *
     * @param transvalueresidentbuy
     */
    public void setTransvalueresidentbuy(String transvalueresidentbuy) {
        this.transvalueresidentbuy = transvalueresidentbuy;
    }

    /**
     *
     * @return
     */
    public String getTransvaluenonresidentbuy() {
        return transvaluenonresidentbuy;
    }

    /**
     *
     * @param transvaluenonresidentbuy
     */
    public void setTransvaluenonresidentbuy(String transvaluenonresidentbuy) {
        this.transvaluenonresidentbuy = transvaluenonresidentbuy;
    }

    /**
     *
     * @return
     */
    public String getCommisionvaluetresidentbuy() {
        return commisionvaluetresidentbuy;
    }

    /**
     *
     * @param commisionvaluetresidentbuy
     */
    public void setCommisionvaluetresidentbuy(String commisionvaluetresidentbuy) {
        this.commisionvaluetresidentbuy = commisionvaluetresidentbuy;
    }

    /**
     *
     * @return
     */
    public String getCommisionvaluenonresidentbuy() {
        return commisionvaluenonresidentbuy;
    }

    /**
     *
     * @param commisionvaluenonresidentbuy
     */
    public void setCommisionvaluenonresidentbuy(String commisionvaluenonresidentbuy) {
        this.commisionvaluenonresidentbuy = commisionvaluenonresidentbuy;
    }

    /**
     *
     * @return
     */
    public String getTransactionnumberresidentbuy() {
        return transactionnumberresidentbuy;
    }

    /**
     *
     * @param transactionnumberresidentbuy
     */
    public void setTransactionnumberresidentbuy(String transactionnumberresidentbuy) {
        this.transactionnumberresidentbuy = transactionnumberresidentbuy;
    }

    /**
     *
     * @return
     */
    public String getTransactionnumbernonresidentbuy() {
        return transactionnumbernonresidentbuy;
    }

    /**
     *
     * @param transactionnumbernonresidentbuy
     */
    public void setTransactionnumbernonresidentbuy(String transactionnumbernonresidentbuy) {
        this.transactionnumbernonresidentbuy = transactionnumbernonresidentbuy;
    }

    /**
     *
     * @return
     */
    public String getInternetbookingamountyes() {
        return internetbookingamountyes;
    }

    /**
     *
     * @param internetbookingamountyes
     */
    public void setInternetbookingamountyes(String internetbookingamountyes) {
        this.internetbookingamountyes = internetbookingamountyes;
    }

    /**
     *
     * @return
     */
    public String getInternetbookingnumberyes() {
        return internetbookingnumberyes;
    }

    /**
     *
     * @param internetbookingnumberyes
     */
    public void setInternetbookingnumberyes(String internetbookingnumberyes) {
        this.internetbookingnumberyes = internetbookingnumberyes;
    }

    /**
     *
     * @return
     */
    public String getTransvalueresidentsell() {
        return transvalueresidentsell;
    }

    /**
     *
     * @param transvalueresidentsell
     */
    public void setTransvalueresidentsell(String transvalueresidentsell) {
        this.transvalueresidentsell = transvalueresidentsell;
    }

    /**
     *
     * @return
     */
    public String getTransvaluenonresidentsell() {
        return transvaluenonresidentsell;
    }

    /**
     *
     * @param transvaluenonresidentsell
     */
    public void setTransvaluenonresidentsell(String transvaluenonresidentsell) {
        this.transvaluenonresidentsell = transvaluenonresidentsell;
    }

    /**
     *
     * @return
     */
    public String getCommisionvaluetresidentsell() {
        return commisionvaluetresidentsell;
    }

    /**
     *
     * @param commisionvaluetresidentsell
     */
    public void setCommisionvaluetresidentsell(String commisionvaluetresidentsell) {
        this.commisionvaluetresidentsell = commisionvaluetresidentsell;
    }

    /**
     *
     * @return
     */
    public String getCommisionvaluenonresidentsell() {
        return commisionvaluenonresidentsell;
    }

    /**
     *
     * @param commisionvaluenonresidentsell
     */
    public void setCommisionvaluenonresidentsell(String commisionvaluenonresidentsell) {
        this.commisionvaluenonresidentsell = commisionvaluenonresidentsell;
    }

    /**
     *
     * @return
     */
    public String getTransactionnumberresidentsell() {
        return transactionnumberresidentsell;
    }

    /**
     *
     * @param transactionnumberresidentsell
     */
    public void setTransactionnumberresidentsell(String transactionnumberresidentsell) {
        this.transactionnumberresidentsell = transactionnumberresidentsell;
    }

    /**
     *
     * @return
     */
    public String getTransactionnumbernonresidentsell() {
        return transactionnumbernonresidentsell;
    }

    /**
     *
     * @param transactionnumbernonresidentsell
     */
    public void setTransactionnumbernonresidentsell(String transactionnumbernonresidentsell) {
        this.transactionnumbernonresidentsell = transactionnumbernonresidentsell;
    }

    /**
     *
     * @return
     */
    public String getInternetbookingamountno() {
        return internetbookingamountno;
    }

    /**
     *
     * @param internetbookingamountno
     */
    public void setInternetbookingamountno(String internetbookingamountno) {
        this.internetbookingamountno = internetbookingamountno;
    }

    /**
     *
     * @return
     */
    public String getInternetbookingnumberno() {
        return internetbookingnumberno;
    }

    /**
     *
     * @param internetbookingnumberno
     */
    public void setInternetbookingnumberno(String internetbookingnumberno) {
        this.internetbookingnumberno = internetbookingnumberno;
    }
    
    /**
     *
     * @return
     */
    public ArrayList<DeleteTransactionList_value> getDati() {
        return dati;
    }

    /**
     *
     * @param dati
     */
    public void setDati(ArrayList<DeleteTransactionList_value> dati) {
        this.dati = dati;
    }

    /**
     *
     * @return
     */
    public String getId_filiale() {
        return id_filiale;
    }

    /**
     *
     * @param id_filiale
     */
    public void setId_filiale(String id_filiale) {
        this.id_filiale = id_filiale;
    }

    /**
     *
     * @return
     */
    public String getDe_filiale() {
        return de_filiale;
    }

    /**
     *
     * @param de_filiale
     */
    public void setDe_filiale(String de_filiale) {
        this.de_filiale = de_filiale;
    }

    /**
     *
     * @return
     */
    public String getTill() {
        return till;
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
    public String getUser() {
        return user;
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
    public String getNotr() {
        return notr;
    }

    /**
     *
     * @param notr
     */
    public void setNotr(String notr) {
        this.notr = notr;
    }

    /**
     *
     * @return
     */
    public String getTime() {
        return time;
    }

    /**
     *
     * @param time
     */
    public void setTime(String time) {
        this.time = time;
    }

    /**
     *
     * @return
     */
    public String getCur() {
        return cur;
    }

    /**
     *
     * @param cur
     */
    public void setCur(String cur) {
        this.cur = cur;
    }

    /**
     *
     * @return
     */
    public String getKind() {
        return kind;
    }

    /**
     *
     * @param kind
     */
    public void setKind(String kind) {
        this.kind = kind;
    }

    /**
     *
     * @return
     */
    public String getAmount() {
        return (amount);
    }
    
    /**
     *
     * @return
     */
    public String getAmountSenzaFormattazione() {
        return (amount);
    }

    /**
     *
     * @param amount
     */
    public void setAmount(String amount) {
        this.amount = amount;
    }

    /**
     *
     * @return
     */
    public String getRate() {
        return (rate);
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
    public String getTotal() {
        return (total);
    }
    
    /**
     *
     * @return
     */
    public String getTotalSenzaFormattazione() {
        return (total);
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
    public String getPerc() {
        return (perc);
    }

    /**
     *
     * @param perc
     */
    public void setPerc(String perc) {
        this.perc = perc;
    }

    /**
     *
     * @return
     */
    public String getComfree() {
        return (comfree);
    }
    
    /**
     *
     * @return
     */
    public String getComfreeSenzaFormattazione() {
        return (comfree);
    }

    /**
     *
     * @param comfree
     */
    public void setComfree(String comfree) {
        this.comfree = comfree;
    }

    /**
     *
     * @return
     */
    public String getPayin() {
        return (payin);
    }
    
    /**
     *
     * @return
     */
    public String getPayinSenzaFormattazione() {
        return (payin);
    }

    /**
     *
     * @param payin
     */
    public void setPayin(String payin) {
        this.payin = payin;
    }

    /**
     *
     * @return
     */
    public String getPayout() {
        return (payout);
    }
    
    /**
     *
     * @return
     */
    public String getPayoutSenzaFormattazione() {
        return (payout);
    }

    /**
     *
     * @param payout
     */
    public void setPayout(String payout) {
        this.payout = payout;
    }

    /**
     *
     * @return
     */
    public String getCustomer() {
        return customer;
    }

    /**
     *
     * @param customer
     */
    public void setCustomer(String customer) {
        this.customer = customer;
    }

    /**
     *
     * @return
     */
    public String getSpread() {
        return (spread);
    }

    /**
     *
     * @param spread
     */
    public void setSpread(String spread) {
        this.spread = spread;
    }

    /**
     *
     * @return
     */
    public String getFig() {
        return fig;
    }

    /**
     *
     * @param fig
     */
    public void setFig(String fig) {
        this.fig = fig;
    }
}
