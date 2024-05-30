package rc.so.entity;

import static rc.so.util.Constant.patternnormdate;
import static rc.so.util.Constant.patternsqldate;
import static rc.so.util.Utility.formatStringtoStringDate;

/**
 *
 * @author rcosco
 */
public class Ch_transaction_file {

    String id;
    String cod_tr;
    String directory;
    String filename;
    String size;
    String user;
    String status;
    String contatore;
    String data;
    String notes, old_kind, new_kind, old_ps, new_ps;
    
    /**
     *
     * @param cod_tr
     * @param notes
     * @param old_kind
     * @param new_kind
     * @param old_ps
     * @param new_ps
     * @param user
     * @param data
     */
    public Ch_transaction_file(String cod_tr, String notes, String old_kind, String new_kind, String old_ps, String new_ps, String user, String data) {
        this.cod_tr = cod_tr;
        this.user = user;
        this.data = formatStringtoStringDate(data, patternsqldate, patternnormdate);    
        this.notes = notes;    
        this.old_kind = old_kind;    
        this.new_kind = new_kind;    
        this.old_ps = old_ps;    
        this.new_ps = new_ps;    
    }

    /**
     * Constructor
     */
    public Ch_transaction_file() {
    }

    /**
     *
     * @param cod_tr
     * @param directory
     * @param filename
     * @param size
     * @param user
     * @param data
     */
    public Ch_transaction_file(String cod_tr, String directory, String filename, String size, String user, String data) {
        this.cod_tr = cod_tr;
        this.directory = directory;
        this.filename = filename;
        this.size = size;
        this.user = user;
        this.data = data;
    }

    /**
     *
     * @return
     */
    public String getNotes() {
        return notes;
    }

    /**
     *
     * @param notes
     */
    public void setNotes(String notes) {
        this.notes = notes;
    }

    /**
     *
     * @return
     */
    public String getOld_kind() {
        return old_kind;
    }

    /**
     *
     * @param old_kind
     */
    public void setOld_kind(String old_kind) {
        this.old_kind = old_kind;
    }

    /**
     *
     * @return
     */
    public String getNew_kind() {
        return new_kind;
    }

    /**
     *
     * @param new_kind
     */
    public void setNew_kind(String new_kind) {
        this.new_kind = new_kind;
    }

    /**
     *
     * @return
     */
    public String getOld_ps() {
        return old_ps;
    }

    /**
     *
     * @param old_ps
     */
    public void setOld_ps(String old_ps) {
        this.old_ps = old_ps;
    }

    /**
     *
     * @return
     */
    public String getNew_ps() {
        return new_ps;
    }

    /**
     *
     * @param new_ps
     */
    public void setNew_ps(String new_ps) {
        this.new_ps = new_ps;
    }

    /**
     *
     * @return
     */
    public String getId() {
        return this.id;
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
    public String getCod_tr() {
        return this.cod_tr;
    }

    /**
     *
     * @param cod_tr
     */
    public void setCod_tr(String cod_tr) {
        this.cod_tr = cod_tr;
    }

    /**
     *
     * @return
     */
    public String getDirectory() {
        return this.directory;
    }

    /**
     *
     * @param directory
     */
    public void setDirectory(String directory) {
        this.directory = directory;
    }

    /**
     *
     * @return
     */
    public String getFilename() {
        return this.filename;
    }

    /**
     *
     * @param filename
     */
    public void setFilename(String filename) {
        this.filename = filename;
    }

    /**
     *
     * @return
     */
    public String getSize() {
        return this.size;
    }

    /**
     *
     * @param size
     */
    public void setSize(String size) {
        this.size = size;
    }

    /**
     *
     * @return
     */
    public String getUser() {
        return this.user;
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
    public String getStatus() {
        return this.status;
    }

    /**
     *
     * @param status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     *
     * @return
     */
    public String getContatore() {
        return this.contatore;
    }

    /**
     *
     * @param contatore
     */
    public void setContatore(String contatore) {
        this.contatore = contatore;
    }

    /**
     *
     * @return
     */
    public String getData() {
        return this.data;
    }

    /**
     *
     * @param data
     */
    public void setData(String data) {
        this.data = data;
    }
}


/* Location:              C:\Users\rcosco\Desktop\classes\!\entity\Ch_transaction_file.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */
