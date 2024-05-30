/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rc.so.reportcentrale;

import java.util.ArrayList;

/**
 *
 * @author srotella
 */
public class C_WesternUnion_value {
    String dataDa,dataA,data, toSend, nTransSend, toReceive, nTransReceive, total ;    
    
    boolean full;
    
    ArrayList<C_WesternUnion_value> dati;

    /**
     *
     * @return
     */
    public boolean isFull() {
        return full;
    }

    /**
     *
     * @param full
     */
    public void setFull(boolean full) {
        this.full = full;
    }

    /**
     *
     * @return
     */
    public String getDataDa() {
        return dataDa;
    }

    /**
     *
     * @param dataDa
     */
    public void setDataDa(String dataDa) {
        this.dataDa = dataDa;
    }

    /**
     *
     * @return
     */
    public String getDataA() {
        return dataA;
    }

    /**
     *
     * @param dataA
     */
    public void setDataA(String dataA) {
        this.dataA = dataA;
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
    public String getToSend() {
        return toSend;
    }

    /**
     *
     * @param toSend
     */
    public void setToSend(String toSend) {
        this.toSend = toSend;
    }

    /**
     *
     * @return
     */
    public String getnTransSend() {
        return nTransSend;
    }

    /**
     *
     * @param nTransSend
     */
    public void setnTransSend(String nTransSend) {
        this.nTransSend = nTransSend;
    }

    /**
     *
     * @return
     */
    public String getToReceive() {
        return toReceive;
    }

    /**
     *
     * @param toReceive
     */
    public void setToReceive(String toReceive) {
        this.toReceive = toReceive;
    }

    /**
     *
     * @return
     */
    public String getnTransReceive() {
        return nTransReceive;
    }

    /**
     *
     * @param nTransReceive
     */
    public void setnTransReceive(String nTransReceive) {
        this.nTransReceive = nTransReceive;
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
    public ArrayList<C_WesternUnion_value> getDati() {
        return dati;
    }

    /**
     *
     * @param dati
     */
    public void setDati(ArrayList<C_WesternUnion_value> dati) {
        this.dati = dati;
    }

}
