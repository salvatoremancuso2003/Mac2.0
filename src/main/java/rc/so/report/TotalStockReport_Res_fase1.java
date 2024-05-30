/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rc.so.report;

/**
 *
 * @author rcosco
 */
public class TotalStockReport_Res_fase1 {
    String f_cod,f_data,f_id,f_user,f_fg_tipo,f_till;

    /**
     *
     * @param f_cod
     * @param f_data
     * @param f_id
     * @param f_user
     * @param f_fg_tipo
     * @param f_till
     */
    public TotalStockReport_Res_fase1(String f_cod, String f_data, String f_id, String f_user, String f_fg_tipo, String f_till) {
        this.f_cod = f_cod;
        this.f_data = f_data;
        this.f_id = f_id;
        this.f_user = f_user;
        this.f_fg_tipo = f_fg_tipo;
        this.f_till = f_till;
    }

    /**
     *
     * @return
     */
    public String getF_cod() {
        return f_cod;
    }

    /**
     *
     * @param f_cod
     */
    public void setF_cod(String f_cod) {
        this.f_cod = f_cod;
    }

    /**
     *
     * @return
     */
    public String getF_data() {
        return f_data;
    }

    /**
     *
     * @param f_data
     */
    public void setF_data(String f_data) {
        this.f_data = f_data;
    }

    /**
     *
     * @return
     */
    public String getF_id() {
        return f_id;
    }

    /**
     *
     * @param f_id
     */
    public void setF_id(String f_id) {
        this.f_id = f_id;
    }

    /**
     *
     * @return
     */
    public String getF_user() {
        return f_user;
    }

    /**
     *
     * @param f_user
     */
    public void setF_user(String f_user) {
        this.f_user = f_user;
    }

    /**
     *
     * @return
     */
    public String getF_fg_tipo() {
        return f_fg_tipo;
    }

    /**
     *
     * @param f_fg_tipo
     */
    public void setF_fg_tipo(String f_fg_tipo) {
        this.f_fg_tipo = f_fg_tipo;
    }

    /**
     *
     * @return
     */
    public String getF_till() {
        return f_till;
    }

    /**
     *
     * @param f_till
     */
    public void setF_till(String f_till) {
        this.f_till = f_till;
    }
}
