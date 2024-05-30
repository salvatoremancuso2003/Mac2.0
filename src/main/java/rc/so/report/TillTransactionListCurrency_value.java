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
public class TillTransactionListCurrency_value {
    
    
    ArrayList<TillTransactionListCurrency_value> dati;   
    
    String id_filiale, de_filiale,till,user,notr,time,cur,kind,amount,rate,total,perc,comfree,payinpayout,customer,spread,pos;
    
    String transactionnumberresidentbuy,transactionnumbernonresidentbuy;
    String transactionnumberresidentsell,transactionnumbernonresidentsell;
    
    ArrayList<String> footerdati;
    
    String residentnonresident;
    
    String type="";
    
    String round="";
    
     String posbuyamount,posbuynumber,possellamount,possellnumber;
     
      String bankbuyamount,bankbuynumber,banksellamount,banksellnumber;
      
      String internetbookingamountno,internetbookingnumberno,internetbookingamountyes,internetbookingnumberyes;
      
         String internetbooking;
         
          String transvalueresidentbuy,transvaluenonresidentbuy,commisionvaluetresidentbuy,commisionvaluenonresidentbuy;
    String transvalueresidentsell,transvaluenonresidentsell,commisionvaluetresidentsell,commisionvaluenonresidentsell;

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
    public String getInternetbooking() {
        return internetbooking;
    }

    /**
     *
     * @param internetbooking
     */
    public void setInternetbooking(String internetbooking) {
        this.internetbooking = internetbooking;
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
    public String getBankbuyamount() {
        return bankbuyamount;
    }

    /**
     *
     * @param bankbuyamount
     */
    public void setBankbuyamount(String bankbuyamount) {
        this.bankbuyamount = bankbuyamount;
    }

    /**
     *
     * @return
     */
    public String getBankbuynumber() {
        return bankbuynumber;
    }

    /**
     *
     * @param bankbuynumber
     */
    public void setBankbuynumber(String bankbuynumber) {
        this.bankbuynumber = bankbuynumber;
    }

    /**
     *
     * @return
     */
    public String getBanksellamount() {
        return banksellamount;
    }

    /**
     *
     * @param banksellamount
     */
    public void setBanksellamount(String banksellamount) {
        this.banksellamount = banksellamount;
    }

    /**
     *
     * @return
     */
    public String getBanksellnumber() {
        return banksellnumber;
    }

    /**
     *
     * @param banksellnumber
     */
    public void setBanksellnumber(String banksellnumber) {
        this.banksellnumber = banksellnumber;
    }

    /**
     *
     * @return
     */
    public String getRound() {
        return round;
    }

    /**
     *
     * @param round
     */
    public void setRound(String round) {
        this.round = round;
    }

    /**
     *
     * @return
     */
    public String getPosbuyamount() {
        return posbuyamount;
    }

    /**
     *
     * @param posbuyamount
     */
    public void setPosbuyamount(String posbuyamount) {
        this.posbuyamount = posbuyamount;
    }

    /**
     *
     * @return
     */
    public String getPosbuynumber() {
        return posbuynumber;
    }

    /**
     *
     * @param posbuynumber
     */
    public void setPosbuynumber(String posbuynumber) {
        this.posbuynumber = posbuynumber;
    }

    /**
     *
     * @return
     */
    public String getPossellamount() {
        return possellamount;
    }

    /**
     *
     * @param possellamount
     */
    public void setPossellamount(String possellamount) {
        this.possellamount = possellamount;
    }

    /**
     *
     * @return
     */
    public String getPossellnumber() {
        return possellnumber;
    }

    /**
     *
     * @param possellnumber
     */
    public void setPossellnumber(String possellnumber) {
        this.possellnumber = possellnumber;
    }
     
    /**
     *
     * @return
     */
    public String getResidentnonresident() {
        return residentnonresident;
    }

    /**
     *
     * @param residentnonresident
     */
    public void setResidentnonresident(String residentnonresident) {
        this.residentnonresident = residentnonresident;
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
    public ArrayList<String> getFooterdati() {
        return footerdati;
    }

    /**
     *
     * @param footerdati
     */
    public void setFooterdati(ArrayList<String> footerdati) {
        this.footerdati = footerdati;
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
    public ArrayList<TillTransactionListCurrency_value> getDati() {
        return dati;
    }

    /**
     *
     * @param dati
     */
    public void setDati(ArrayList<TillTransactionListCurrency_value> dati) {
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
    public String getPayinpayout() {
        return (payinpayout);
    }
    
    /**
     *
     * @return
     */
    public String getPayinpayoutSenzaFormattazione() {
        return payinpayout;
    }

    /**
     *
     * @param payinpayout
     */
    public void setPayinpayout(String payinpayout) {
        this.payinpayout = payinpayout;
    }

    /**
     *
     * @return
     */
    public String getType() {
        return type;
    }

    /**
     *
     * @param type
     */
    public void setType(String type) {
        this.type = type;
    }

    
}
