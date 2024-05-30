package rc.so.entity;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author rcosco
 */
public class Branchbudget {
    
    String codice,meseanno,branch,budg1,budg2,colonna1,colonna2,colonna3,colonna4,colonna5,timestamp;

    /**
     * Constructor
     */
    public Branchbudget() {
    }
    
    /**
     *
     * @param codice
     * @param meseanno
     * @param branch
     * @param budg1
     * @param budg2
     * @param colonna1
     * @param colonna2
     * @param colonna3
     * @param colonna4
     * @param colonna5
     * @param timestamp
     */
    public Branchbudget(String codice, String meseanno, String branch, String budg1, String budg2, String colonna1, String colonna2, String colonna3, String colonna4, String colonna5, String timestamp) {
        this.codice = codice;
        this.meseanno = meseanno;
        this.branch = branch;
        this.budg1 = budg1;
        this.budg2 = budg2;
        this.colonna1 = colonna1;
        this.colonna2 = colonna2;
        this.colonna3 = colonna3;
        this.colonna4 = colonna4;
        this.colonna5 = colonna5;
        this.timestamp = timestamp;
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
    public String getMeseanno() {
        return meseanno;
    }

    /**
     *
     * @param meseanno
     */
    public void setMeseanno(String meseanno) {
        this.meseanno = meseanno;
    }

    /**
     *
     * @return
     */
    public String getBranch() {
        return branch;
    }

    /**
     *
     * @param branch
     */
    public void setBranch(String branch) {
        this.branch = branch;
    }

    /**
     *
     * @return
     */
    public String getBudg1() {
        return budg1;
    }

    /**
     *
     * @param budg1
     */
    public void setBudg1(String budg1) {
        this.budg1 = budg1;
    }

    /**
     *
     * @return
     */
    public String getBudg2() {
        return budg2;
    }

    /**
     *
     * @param budg2
     */
    public void setBudg2(String budg2) {
        this.budg2 = budg2;
    }

    /**
     *
     * @return
     */
    public String getColonna1() {
        return colonna1;
    }

    /**
     *
     * @param colonna1
     */
    public void setColonna1(String colonna1) {
        this.colonna1 = colonna1;
    }

    /**
     *
     * @return
     */
    public String getColonna2() {
        return colonna2;
    }

    /**
     *
     * @param colonna2
     */
    public void setColonna2(String colonna2) {
        this.colonna2 = colonna2;
    }

    /**
     *
     * @return
     */
    public String getColonna3() {
        return colonna3;
    }

    /**
     *
     * @param colonna3
     */
    public void setColonna3(String colonna3) {
        this.colonna3 = colonna3;
    }

    /**
     *
     * @return
     */
    public String getColonna4() {
        return colonna4;
    }

    /**
     *
     * @param colonna4
     */
    public void setColonna4(String colonna4) {
        this.colonna4 = colonna4;
    }

    /**
     *
     * @return
     */
    public String getColonna5() {
        return colonna5;
    }

    /**
     *
     * @param colonna5
     */
    public void setColonna5(String colonna5) {
        this.colonna5 = colonna5;
    }

    /**
     *
     * @return
     */
    public String getTimestamp() {
        return timestamp;
    }

    /**
     *
     * @param timestamp
     */
    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
    
    

}
