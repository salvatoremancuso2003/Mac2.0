/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rc.so.entity;

/**
 *
 * @author rcosco
 */
public class Codici_sblocco_file {
    
    String id,user,dest,numcod,pdf,excel,dt_oper,listcod;

    /**
     * Constructor
     */
    public Codici_sblocco_file() {
    }
    
    /**
     *
     * @param id
     * @param user
     * @param dest
     * @param numcod
     * @param pdf
     * @param excel
     * @param dt_oper
     * @param listcod
     */
    public Codici_sblocco_file(String id, String user, String dest, String numcod, String pdf, String excel, String dt_oper,String listcod) {
        this.id = id;
        this.user = user;
        this.dest = dest;
        this.numcod = numcod;
        this.pdf = pdf;
        this.excel = excel;
        this.dt_oper = dt_oper;
        this.listcod = listcod;
    }

    /**
     *
     * @return
     */
    public String getListcod() {
        return listcod;
    }

    /**
     *
     * @param listcod
     */
    public void setListcod(String listcod) {
        this.listcod = listcod;
    }

    /**
     *
     * @return
     */
    public String getId() {
        return id;
    }

    /**
     *
     * @param id
     */
    public void setId(String id) {
        this.id = id;
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
    public String getDest() {
        return dest;
    }

    /**
     *
     * @param dest
     */
    public void setDest(String dest) {
        this.dest = dest;
    }

    /**
     *
     * @return
     */
    public String getNumcod() {
        return numcod;
    }

    /**
     *
     * @param numcod
     */
    public void setNumcod(String numcod) {
        this.numcod = numcod;
    }

    /**
     *
     * @return
     */
    public String getPdf() {
        return pdf;
    }

    /**
     *
     * @param pdf
     */
    public void setPdf(String pdf) {
        this.pdf = pdf;
    }

    /**
     *
     * @return
     */
    public String getExcel() {
        return excel;
    }

    /**
     *
     * @param excel
     */
    public void setExcel(String excel) {
        this.excel = excel;
    }

    /**
     *
     * @return
     */
    public String getDt_oper() {
        return dt_oper;
    }

    /**
     *
     * @param dt_oper
     */
    public void setDt_oper(String dt_oper) {
        this.dt_oper = dt_oper;
    }
    
    

}
