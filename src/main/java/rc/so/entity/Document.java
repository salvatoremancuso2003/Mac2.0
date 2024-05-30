package rc.so.entity;

/**
 *
 * @author rcosco
 */
public class Document {

    String codice, descrizione, typesign, fg_stato;
    int place;
    boolean obbl;

    /**
     * Constructor
     */
    public Document() {
    }

    /**
     *
     * @param codice
     * @param descrizione
     * @param typesign
     * @param fg_stato
     * @param place
     * @param obbl
     */
    public Document(String codice, String descrizione, String typesign, String fg_stato, int place, boolean obbl) {
        this.codice = codice;
        this.descrizione = descrizione;
        this.typesign = typesign;
        this.fg_stato = fg_stato;
        this.place = place;
        this.obbl = obbl;
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
    public String getDescrizione() {
        return descrizione;
    }

    /**
     *
     * @param descrizione
     */
    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    /**
     *
     * @return
     */
    public String getTypesign() {
        return typesign;
    }

    /**
     *
     * @param typesign
     */
    public void setTypesign(String typesign) {
        this.typesign = typesign;
    }

    /**
     *
     * @return
     */
    public String getFg_stato() {
        return fg_stato;
    }

    /**
     *
     * @param fg_stato
     */
    public void setFg_stato(String fg_stato) {
        this.fg_stato = fg_stato;
    }

    /**
     *
     * @return
     */
    public int getPlace() {
        return place;
    }

    /**
     *
     * @param place
     */
    public void setPlace(int place) {
        this.place = place;
    }

    /**
     *
     * @return
     */
    public boolean isObbl() {
        return obbl;
    }

    /**
     *
     * @param obbl
     */
    public void setObbl(boolean obbl) {
        this.obbl = obbl;
    }

}