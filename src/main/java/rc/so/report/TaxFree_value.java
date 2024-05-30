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
public class TaxFree_value {

    String id_filiale, de_filiale, dataDa, dataA, data, totRimb, noTimbro, siTimbro, noDogana, siDogana, totModuli;

    ArrayList<TaxFree_value> dati;

    ArrayList<String[]> categoryList;

    /**
     *
     * @return
     */
    public ArrayList<String[]> getCategoryList() {
        return categoryList;
    }

    /**
     *
     * @param categoryList
     */
    public void setCategoryList(ArrayList<String[]> categoryList) {
        this.categoryList = categoryList;
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
    public ArrayList<TaxFree_value> getDati() {
        return dati;
    }

    /**
     *
     * @param dati
     */
    public void setDati(ArrayList<TaxFree_value> dati) {
        this.dati = dati;
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
    public String getTotRimb() {
        return totRimb;
    }

    /**
     *
     * @param totRimb
     */
    public void setTotRimb(String totRimb) {
        this.totRimb = totRimb;
    }

    /**
     *
     * @return
     */
    public String getNoTimbro() {
        return noTimbro;
    }

    /**
     *
     * @param noTimbro
     */
    public void setNoTimbro(String noTimbro) {
        this.noTimbro = noTimbro;
    }

    /**
     *
     * @return
     */
    public String getSiTimbro() {
        return siTimbro;
    }

    /**
     *
     * @param siTimbro
     */
    public void setSiTimbro(String siTimbro) {
        this.siTimbro = siTimbro;
    }

    /**
     *
     * @return
     */
    public String getNoDogana() {
        return noDogana;
    }

    /**
     *
     * @param noDogana
     */
    public void setNoDogana(String noDogana) {
        this.noDogana = noDogana;
    }

    /**
     *
     * @return
     */
    public String getSiDogana() {
        return siDogana;
    }

    /**
     *
     * @param siDogana
     */
    public void setSiDogana(String siDogana) {
        this.siDogana = siDogana;
    }

    /**
     *
     * @return
     */
    public String getTotModuli() {
        return totModuli;
    }

    /**
     *
     * @param totModuli
     */
    public void setTotModuli(String totModuli) {
        this.totModuli = totModuli;
    }

}
