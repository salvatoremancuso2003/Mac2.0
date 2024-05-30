
package rc.so.entity;

/**
 *
 * @author rcosco
 */
public class Company_attach {
    
    String cod,code_ndg,descr,fileout,fg_annullato,dt_upload;

    /**
     * Constructor
     */
    public Company_attach() {
    }

    /**
     *
     * @param cod
     * @param code_ndg
     * @param descr
     * @param fileout
     * @param fg_annullato
     * @param dt_upload
     */
    public Company_attach(String cod, String code_ndg, String descr, String fileout, String fg_annullato,String dt_upload) {
        this.cod = cod;
        this.code_ndg = code_ndg;
        this.descr = descr;
        this.fileout = fileout;
        this.dt_upload = dt_upload;
        this.fg_annullato = fg_annullato;
    }

    /**
     *
     * @return
     */
    public String getFg_annullato() {
        return fg_annullato;
    }

    /**
     *
     * @param fg_annullato
     */
    public void setFg_annullato(String fg_annullato) {
        this.fg_annullato = fg_annullato;
    }
    
    /**
     *
     * @return
     */
    public String getCod() {
        return cod;
    }

    /**
     *
     * @param cod
     */
    public void setCod(String cod) {
        this.cod = cod;
    }

    /**
     *
     * @return
     */
    public String getCode_ndg() {
        return code_ndg;
    }

    /**
     *
     * @param code_ndg
     */
    public void setCode_ndg(String code_ndg) {
        this.code_ndg = code_ndg;
    }

    /**
     *
     * @return
     */
    public String getDescr() {
        return descr;
    }

    /**
     *
     * @param descr
     */
    public void setDescr(String descr) {
        this.descr = descr;
    }

    /**
     *
     * @return
     */
    public String getFileout() {
        return fileout;
    }

    /**
     *
     * @param fileout
     */
    public void setFileout(String fileout) {
        this.fileout = fileout;
    }

    /**
     *
     * @return
     */
    public String getDt_upload() {
        return dt_upload;
    }

    /**
     *
     * @param dt_upload
     */
    public void setDt_upload(String dt_upload) {
        this.dt_upload = dt_upload;
    }
    
    
}
