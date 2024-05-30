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
public class C_NoChangeMovimentForBranches_value {
    
    
    String id_filiale, de_filiale,dataDa,dataA,category, causal, nTrans, qt,total,kind;    
    
    ArrayList<C_NoChangeMovimentForBranches_value> dati;

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
    public String getCategory() {
        return category;
    }

    /**
     *
     * @param category
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     *
     * @return
     */
    public String getCausal() {
        return causal;
    }

    /**
     *
     * @param causal
     */
    public void setCausal(String causal) {
        this.causal = causal;
    }

    /**
     *
     * @return
     */
    public String getnTrans() {
        return nTrans;
    }

    /**
     *
     * @param nTrans
     */
    public void setnTrans(String nTrans) {
        this.nTrans = nTrans;
    }

    /**
     *
     * @return
     */
    public String getQt() {
        return qt;
    }

    /**
     *
     * @param qt
     */
    public void setQt(String qt) {
        this.qt = qt;
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
    public ArrayList<C_NoChangeMovimentForBranches_value> getDati() {
        return dati;
    }

    /**
     *
     * @param dati
     */
    public void setDati(ArrayList<C_NoChangeMovimentForBranches_value> dati) {
        this.dati = dati;
    }

}
