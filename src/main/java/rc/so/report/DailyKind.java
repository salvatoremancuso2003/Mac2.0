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
public class DailyKind {
    

    
    String kind, toNrTran,toTotal, toLocalCurr, toCC, toB,fromNrTran, fromTotal, fromLocalCurr, fromCC, fromB;
    boolean to,from;
    
    ArrayList<String> dati;
    
    String etichetta1,etichetta2;
    
    /**
     *
     * @return
     */
    public String getEtichetta1() {
        return etichetta1;
    }

    /**
     *
     * @param etichetta1
     */
    public void setEtichetta1(String etichetta1) {
        this.etichetta1 = etichetta1;
    }

    /**
     *
     * @return
     */
    public String getEtichetta2() {
        return etichetta2;
    }

    /**
     *
     * @param etichetta2
     */
    public void setEtichetta2(String etichetta2) {
        this.etichetta2 = etichetta2;
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
     * @return
     */
    public String getToTotal() {
        return toTotal;
    }
    
    /**
     *
     * @param toTotal
     */
    public void setToTotal(String toTotal) {
        this.toTotal = toTotal;
    }

    /**
     *
     * @return
     */
    public String getFromTotal() {
        return fromTotal;
    }

    /**
     *
     * @param fromTotal
     */
    public void setFromTotal(String fromTotal) {
        this.fromTotal = fromTotal;
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
    public boolean isTo() {
        return to;
    }

    /**
     *
     * @param to
     */
    public void setTo(boolean to) {
        this.to = to;
    }

    /**
     *
     * @return
     */
    public boolean isFrom() {
        return from;
    }

    /**
     *
     * @param from
     */
    public void setFrom(boolean from) {
        this.from = from;
    }

    /**
     *
     * @return
     */
    public String getToNrTran() {
        return toNrTran;
    }

    /**
     *
     * @param toNrTran
     */
    public void setToNrTran(String toNrTran) {
        this.toNrTran = toNrTran;
    }

    /**
     *
     * @return
     */
    public String getToLocalCurr() {
        return toLocalCurr;
    }

    /**
     *
     * @param toLocalCurr
     */
    public void setToLocalCurr(String toLocalCurr) {
        this.toLocalCurr = toLocalCurr;
    }

    /**
     *
     * @return
     */
    public String getToCC() {
        return toCC;
    }

    /**
     *
     * @param toCC
     */
    public void setToCC(String toCC) {
        this.toCC = toCC;
    }

    /**
     *
     * @return
     */
    public String getToB() {
        return toB;
    }

    /**
     *
     * @param toB
     */
    public void setToB(String toB) {
        this.toB = toB;
    }

    /**
     *
     * @return
     */
    public String getFromNrTran() {
        return fromNrTran;
    }

    /**
     *
     * @param fromNrTran
     */
    public void setFromNrTran(String fromNrTran) {
        this.fromNrTran = fromNrTran;
    }

    /**
     *
     * @return
     */
    public String getFromLocalCurr() {
        return fromLocalCurr;
    }

    /**
     *
     * @param fromLocalCurr
     */
    public void setFromLocalCurr(String fromLocalCurr) {
        this.fromLocalCurr = fromLocalCurr;
    }

    /**
     *
     * @return
     */
    public String getFromCC() {
        return fromCC;
    }

    /**
     *
     * @param fromCC
     */
    public void setFromCC(String fromCC) {
        this.fromCC = fromCC;
    }

    /**
     *
     * @return
     */
    public String getFromB() {
        return fromB;
    }

    /**
     *
     * @param fromB
     */
    public void setFromB(String fromB) {
        this.fromB = fromB;
    }

    /**
     *
     * @return
     */
    public ArrayList<String> getDati() {
        return dati;
    }

    /**
     *
     * @param dati
     */
    public void setDati(ArrayList<String> dati) {
        this.dati = dati;
    }

    
    
    
}


