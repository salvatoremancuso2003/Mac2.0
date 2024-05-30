package rc.so.entity;

/**
 *
 * @author rcosco
 */
public class History
{
  String id;
  
  String codicesott;
  
  String user;
  String azione;
  String tipologia;
  String data;
  
    /**
     * Constructor
     */
    public History() {}
  
    /**
     *
     * @param codicesott
     * @param user
     * @param azione
     */
    public History(String codicesott, String user, String azione)
  {
    this.codicesott = codicesott;
    this.user = user;
    this.azione = azione;
  }
  
    /**
     *
     * @param id
     * @param codicesott
     * @param user
     * @param azione
     * @param tipologia
     * @param data
     */
    public History(String id, String codicesott, String user, String azione, String tipologia, String data) {
    this.id = id;
    this.codicesott = codicesott;
    this.user = user;
    this.azione = azione;
    this.tipologia = tipologia;
    this.data = data;
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
    public String getCodicesott() {
    return this.codicesott;
  }
  
    /**
     *
     * @param codicesott
     */
    public void setCodicesott(String codicesott) {
    this.codicesott = codicesott;
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
    public String getAzione() {
    return this.azione;
  }
  
    /**
     *
     * @param azione
     */
    public void setAzione(String azione) {
    this.azione = azione;
  }
  
    /**
     *
     * @return
     */
    public String getTipologia() {
    return this.tipologia;
  }
  
    /**
     *
     * @param tipologia
     */
    public void setTipologia(String tipologia) {
    this.tipologia = tipologia;
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


/* Location:              C:\Users\rcosco\Desktop\classes\!\entity\History.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */