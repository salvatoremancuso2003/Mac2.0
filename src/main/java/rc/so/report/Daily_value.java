/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rc.so.report;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author srotella
 */
public class Daily_value implements Serializable{

    final long serialVersionUID = 42L;    
    
    String id_filiale, de_filiale, user, data;

    String purchTotal, purchComm, purchGrossTot, purchSpread, purchProfit;
    String salesTotal, salesComm, salesGrossTot, salesSpread, salesProfit;
    String cashAdNetTot, cashAdComm, cashAdGrossTot, cashAdSpread, cashAdProfit;
    String TotNetTot, TotComm, TotGrossTot, TotSpread, TotProfit;

    String baPurchTotal, baPurchSpread, baPurchCreditCard, baPurchTransfNotes, baPurchTransfOther;
    String baSalesTotal, baSalesSpread, baSalesCreditCard, baSalesTransfNotes, baSalesTransfOther;

    String braPurchTotal, braPurchSpread, braPurchLocalCurr;
    String braSalesTotal, braSalesSpread, braSalesLocalCurr;

    String groffTurnover, grossProfit, lastCashOnPrem, cashOnPrem, fx;
    String cashOnPremFromTrans, cashOnPremError, fxClosureErrorDeclared;

    String noTransPurch, noTransCC, noTransSales, total;

    String totPos, totAcc;

    String refund;

    ArrayList<DailyKind> dati;
    ArrayList<DailyCOP> datiCOP;
    ArrayList<DailyBank> datiBank;

    ArrayList<DailyKind> datiatl;

    String officesp;

    /**
     *
     * @return
     */
    public ArrayList<DailyKind> getDatiatl() {
        return datiatl;
    }

    /**
     *
     * @param datiatl
     */
    public void setDatiatl(ArrayList<DailyKind> datiatl) {
        this.datiatl = datiatl;
    }

    /**
     *
     * @return
     */
    public String getOfficesp() {
        return officesp;
    }

    /**
     *
     * @param officesp
     */
    public void setOfficesp(String officesp) {
        this.officesp = officesp;
    }

    /**
     *
     * @return
     */
    public String getRefund() {
        return refund;
    }

    /**
     *
     * @param refund
     */
    public void setRefund(String refund) {
        this.refund = refund;
    }

    /**
     *
     * @return
     */
    public ArrayList<DailyBank> getDatiBank() {
        return datiBank;
    }

    /**
     *
     * @param datiBank
     */
    public void setDatiBank(ArrayList<DailyBank> datiBank) {
        this.datiBank = datiBank;
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
    public String getData() {
        return data;
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
    public String getPurchTotal() {
        return purchTotal;
    }

    /**
     *
     * @param purchTotal
     */
    public void setPurchTotal(String purchTotal) {
        this.purchTotal = purchTotal;
    }

    /**
     *
     * @return
     */
    public String getPurchComm() {
        return purchComm;
    }

    /**
     *
     * @param purchComm
     */
    public void setPurchComm(String purchComm) {
        this.purchComm = purchComm;
    }

    /**
     *
     * @return
     */
    public String getPurchGrossTot() {
        return purchGrossTot;
    }

    /**
     *
     * @param purchGrossTot
     */
    public void setPurchGrossTot(String purchGrossTot) {
        this.purchGrossTot = purchGrossTot;
    }

    /**
     *
     * @return
     */
    public String getPurchSpread() {
        return purchSpread;
    }

    /**
     *
     * @param purchSpread
     */
    public void setPurchSpread(String purchSpread) {
        this.purchSpread = purchSpread;
    }

    /**
     *
     * @return
     */
    public String getPurchProfit() {
        return purchProfit;
    }

    /**
     *
     * @param purchProfit
     */
    public void setPurchProfit(String purchProfit) {
        this.purchProfit = purchProfit;
    }

    /**
     *
     * @return
     */
    public String getSalesTotal() {
        return salesTotal;
    }

    /**
     *
     * @param salesTotal
     */
    public void setSalesTotal(String salesTotal) {
        this.salesTotal = salesTotal;
    }

    /**
     *
     * @return
     */
    public String getSalesComm() {
        return salesComm;
    }

    /**
     *
     * @param salesComm
     */
    public void setSalesComm(String salesComm) {
        this.salesComm = salesComm;
    }

    /**
     *
     * @return
     */
    public String getSalesGrossTot() {
        return salesGrossTot;
    }

    /**
     *
     * @param salesGrossTot
     */
    public void setSalesGrossTot(String salesGrossTot) {
        this.salesGrossTot = salesGrossTot;
    }

    /**
     *
     * @return
     */
    public String getSalesSpread() {
        return salesSpread;
    }

    /**
     *
     * @param salesSpread
     */
    public void setSalesSpread(String salesSpread) {
        this.salesSpread = salesSpread;
    }

    /**
     *
     * @return
     */
    public String getSalesProfit() {
        return salesProfit;
    }

    /**
     *
     * @param salesProfit
     */
    public void setSalesProfit(String salesProfit) {
        this.salesProfit = salesProfit;
    }

    /**
     *
     * @return
     */
    public String getCashAdNetTot() {
        return cashAdNetTot;
    }

    /**
     *
     * @param cashAdNetTot
     */
    public void setCashAdNetTot(String cashAdNetTot) {
        this.cashAdNetTot = cashAdNetTot;
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
    public String getCashAdGrossTot() {
        return cashAdGrossTot;
    }

    /**
     *
     * @param cashAdGrossTot
     */
    public void setCashAdGrossTot(String cashAdGrossTot) {
        this.cashAdGrossTot = cashAdGrossTot;
    }

    /**
     *
     * @return
     */
    public String getCashAdSpread() {
        return cashAdSpread;
    }

    /**
     *
     * @param cashAdSpread
     */
    public void setCashAdSpread(String cashAdSpread) {
        this.cashAdSpread = cashAdSpread;
    }

    /**
     *
     * @return
     */
    public String getCashAdProfit() {
        return cashAdProfit;
    }

    /**
     *
     * @param cashAdProfit
     */
    public void setCashAdProfit(String cashAdProfit) {
        this.cashAdProfit = cashAdProfit;
    }

    /**
     *
     * @return
     */
    public String getTotNetTot() {
        return TotNetTot;
    }

    /**
     *
     * @param TotNetTot
     */
    public void setTotNetTot(String TotNetTot) {
        this.TotNetTot = TotNetTot;
    }

    /**
     *
     * @return
     */
    public String getTotComm() {
        return TotComm;
    }

    /**
     *
     * @param TotComm
     */
    public void setTotComm(String TotComm) {
        this.TotComm = TotComm;
    }

    /**
     *
     * @return
     */
    public String getTotGrossTot() {
        return TotGrossTot;
    }

    /**
     *
     * @param TotGrossTot
     */
    public void setTotGrossTot(String TotGrossTot) {
        this.TotGrossTot = TotGrossTot;
    }

    /**
     *
     * @return
     */
    public String getTotSpread() {
        return TotSpread;
    }

    /**
     *
     * @param TotSpread
     */
    public void setTotSpread(String TotSpread) {
        this.TotSpread = TotSpread;
    }

    /**
     *
     * @return
     */
    public String getTotProfit() {
        return TotProfit;
    }

    /**
     *
     * @param TotProfit
     */
    public void setTotProfit(String TotProfit) {
        this.TotProfit = TotProfit;
    }

    /**
     *
     * @return
     */
    public String getBaPurchTotal() {
        return baPurchTotal;
    }

    /**
     *
     * @param baPurchTotal
     */
    public void setBaPurchTotal(String baPurchTotal) {
        this.baPurchTotal = baPurchTotal;
    }

    /**
     *
     * @return
     */
    public String getBaPurchSpread() {
        return baPurchSpread;
    }

    /**
     *
     * @param baPurchSpread
     */
    public void setBaPurchSpread(String baPurchSpread) {
        this.baPurchSpread = baPurchSpread;
    }

    /**
     *
     * @return
     */
    public String getBaPurchCreditCard() {
        return baPurchCreditCard;
    }

    /**
     *
     * @param baPurchCreditCard
     */
    public void setBaPurchCreditCard(String baPurchCreditCard) {
        this.baPurchCreditCard = baPurchCreditCard;
    }

    /**
     *
     * @return
     */
    public String getBaPurchTransfNotes() {
        return baPurchTransfNotes;
    }

    /**
     *
     * @param baPurchTransfNotes
     */
    public void setBaPurchTransfNotes(String baPurchTransfNotes) {
        this.baPurchTransfNotes = baPurchTransfNotes;
    }

    /**
     *
     * @return
     */
    public String getBaPurchTransfOther() {
        return baPurchTransfOther;
    }

    /**
     *
     * @param baPurchTransfOther
     */
    public void setBaPurchTransfOther(String baPurchTransfOther) {
        this.baPurchTransfOther = baPurchTransfOther;
    }

    /**
     *
     * @return
     */
    public String getBaSalesTotal() {
        return baSalesTotal;
    }

    /**
     *
     * @param baSalesTotal
     */
    public void setBaSalesTotal(String baSalesTotal) {
        this.baSalesTotal = baSalesTotal;
    }

    /**
     *
     * @return
     */
    public String getBaSalesSpread() {
        return baSalesSpread;
    }

    /**
     *
     * @param baSalesSpread
     */
    public void setBaSalesSpread(String baSalesSpread) {
        this.baSalesSpread = baSalesSpread;
    }

    /**
     *
     * @return
     */
    public String getBaSalesCreditCard() {
        return baSalesCreditCard;
    }

    /**
     *
     * @param baSalesCreditCard
     */
    public void setBaSalesCreditCard(String baSalesCreditCard) {
        this.baSalesCreditCard = baSalesCreditCard;
    }

    /**
     *
     * @return
     */
    public String getBaSalesTransfNotes() {
        return baSalesTransfNotes;
    }

    /**
     *
     * @param baSalesTransfNotes
     */
    public void setBaSalesTransfNotes(String baSalesTransfNotes) {
        this.baSalesTransfNotes = baSalesTransfNotes;
    }

    /**
     *
     * @return
     */
    public String getBaSalesTransfOther() {
        return baSalesTransfOther;
    }

    /**
     *
     * @param baSalesTransfOther
     */
    public void setBaSalesTransfOther(String baSalesTransfOther) {
        this.baSalesTransfOther = baSalesTransfOther;
    }

    /**
     *
     * @return
     */
    public String getBraPurchTotal() {
        return braPurchTotal;
    }

    /**
     *
     * @param braPurchTotal
     */
    public void setBraPurchTotal(String braPurchTotal) {
        this.braPurchTotal = braPurchTotal;
    }

    /**
     *
     * @return
     */
    public String getBraPurchSpread() {
        return braPurchSpread;
    }

    /**
     *
     * @param braPurchSpread
     */
    public void setBraPurchSpread(String braPurchSpread) {
        this.braPurchSpread = braPurchSpread;
    }

    /**
     *
     * @return
     */
    public String getBraPurchLocalCurr() {
        return braPurchLocalCurr;
    }

    /**
     *
     * @param braPurchLocalCurr
     */
    public void setBraPurchLocalCurr(String braPurchLocalCurr) {
        this.braPurchLocalCurr = braPurchLocalCurr;
    }

    /**
     *
     * @return
     */
    public String getBraSalesTotal() {
        return braSalesTotal;
    }

    /**
     *
     * @param braSalesTotal
     */
    public void setBraSalesTotal(String braSalesTotal) {
        this.braSalesTotal = braSalesTotal;
    }

    /**
     *
     * @return
     */
    public String getBraSalesSpread() {
        return braSalesSpread;
    }

    /**
     *
     * @param braSalesSpread
     */
    public void setBraSalesSpread(String braSalesSpread) {
        this.braSalesSpread = braSalesSpread;
    }

    /**
     *
     * @return
     */
    public String getBraSalesLocalCurr() {
        return braSalesLocalCurr;
    }

    /**
     *
     * @param braSalesLocalCurr
     */
    public void setBraSalesLocalCurr(String braSalesLocalCurr) {
        this.braSalesLocalCurr = braSalesLocalCurr;
    }

    /**
     *
     * @return
     */
    public String getGroffTurnover() {
        return groffTurnover;
    }

    /**
     *
     * @param groffTurnover
     */
    public void setGroffTurnover(String groffTurnover) {
        this.groffTurnover = groffTurnover;
    }

    /**
     *
     * @return
     */
    public String getGrossProfit() {
        return grossProfit;
    }

    /**
     *
     * @param grossProfit
     */
    public void setGrossProfit(String grossProfit) {
        this.grossProfit = grossProfit;
    }

    /**
     *
     * @return
     */
    public String getLastCashOnPrem() {
        return lastCashOnPrem;
    }

    /**
     *
     * @param lastCashOnPrem
     */
    public void setLastCashOnPrem(String lastCashOnPrem) {
        this.lastCashOnPrem = lastCashOnPrem;
    }

    /**
     *
     * @return
     */
    public String getCashOnPrem() {
        return cashOnPrem;
    }

    /**
     *
     * @param cashOnPrem
     */
    public void setCashOnPrem(String cashOnPrem) {
        this.cashOnPrem = cashOnPrem;
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
    public String getCashOnPremFromTrans() {
        return cashOnPremFromTrans;
    }

    /**
     *
     * @param cashOnPremFromTrans
     */
    public void setCashOnPremFromTrans(String cashOnPremFromTrans) {
        this.cashOnPremFromTrans = cashOnPremFromTrans;
    }

    /**
     *
     * @return
     */
    public String getCashOnPremError() {
        return cashOnPremError;
    }

    /**
     *
     * @param cashOnPremError
     */
    public void setCashOnPremError(String cashOnPremError) {
        this.cashOnPremError = cashOnPremError;
    }

    /**
     *
     * @return
     */
    public String getFxClosureErrorDeclared() {
        return fxClosureErrorDeclared;
    }

    /**
     *
     * @param fxClosureErrorDeclared
     */
    public void setFxClosureErrorDeclared(String fxClosureErrorDeclared) {
        this.fxClosureErrorDeclared = fxClosureErrorDeclared;
    }

    /**
     *
     * @return
     */
    public String getNoTransPurch() {
        return noTransPurch;
    }

    /**
     *
     * @param noTransPurch
     */
    public void setNoTransPurch(String noTransPurch) {
        this.noTransPurch = noTransPurch;
    }

    /**
     *
     * @return
     */
    public String getNoTransCC() {
        return noTransCC;
    }

    /**
     *
     * @param noTransCC
     */
    public void setNoTransCC(String noTransCC) {
        this.noTransCC = noTransCC;
    }

    /**
     *
     * @return
     */
    public String getNoTransSales() {
        return noTransSales;
    }

    /**
     *
     * @param noTransSales
     */
    public void setNoTransSales(String noTransSales) {
        this.noTransSales = noTransSales;
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
    public String getTotPos() {
        return totPos;
    }

    /**
     *
     * @param totPos
     */
    public void setTotPos(String totPos) {
        this.totPos = totPos;
    }

    /**
     *
     * @return
     */
    public String getTotAcc() {
        return totAcc;
    }

    /**
     *
     * @param totAcc
     */
    public void setTotAcc(String totAcc) {
        this.totAcc = totAcc;
    }

    /**
     *
     * @return
     */
    public ArrayList<DailyKind> getDati() {
        return dati;
    }

    /**
     *
     * @param dati
     */
    public void setDati(ArrayList<DailyKind> dati) {
        this.dati = dati;
    }

    /**
     *
     * @return
     */
    public ArrayList<DailyCOP> getDatiCOP() {
        return datiCOP;
    }

    /**
     *
     * @param datiCOP
     */
    public void setDatiCOP(ArrayList<DailyCOP> datiCOP) {
        this.datiCOP = datiCOP;
    }


}