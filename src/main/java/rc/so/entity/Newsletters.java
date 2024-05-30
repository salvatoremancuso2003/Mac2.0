package rc.so.entity;

import java.util.ArrayList;

/**
 *
 * @author rcosco
 */
public class Newsletters
{
  String cod;
  String titolo;
  String descr;
  String fileout;
  String dest;
  String dt_updatestart;
  String dt_upload;
  String user;
  String status;
  String dt_read;
  
    /**
     *
     * @return
     */
    public static ArrayList<String[]> list_status_news()
  {
    ArrayList<String[]> out = new ArrayList<>();
    String[] s1 = { "U", "Unreaded" };
    String[] s2 = { "R", "Readed" };
    out.add(s1);
    out.add(s2);
    return out;
  }
  
    /**
     *
     * @param status
     * @return
     */
    public String formatStatus(String status) {
    if (status == null) {
      status = "";
    }
    if (status.equals("R")) {
      return "<div class='font-green-jungle'>Read <i class='fa fa-check'></i></div>";
    }
    if (status.equals("U")) {
      return "<div class='font-blue'>Unreaded <i class='fa fa-hourglass-start'></i></div>";
    }
    
    return "Error";
  }
  
    /**
     *
     * @param status
     * @return
     */
    public String formatStatus_cru(String status) {
    if (status == null) {
      status = "";
    }
    if (status.equals("R")) {
      return "Read";
    }
    if (status.equals("U")) {
      return "Unreaded";
    }
    
    return "Error";
  }
  
    /**
     *
     * @return
     */
    public String getUser()
  {
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
    public String getDt_read() {
    return this.dt_read;
  }
  
    /**
     *
     * @param dt_read
     */
    public void setDt_read(String dt_read) {
    this.dt_read = dt_read;
  }
  
    /**
     *
     * @return
     */
    public String getCod() {
    return this.cod;
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
    public String getTitolo() {
    return this.titolo;
  }
  
    /**
     *
     * @param titolo
     */
    public void setTitolo(String titolo) {
    this.titolo = titolo;
  }
  
    /**
     *
     * @return
     */
    public String getDescr() {
    return this.descr;
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
    return this.fileout;
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
    public String getDest() {
    return this.dest;
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
    public String getDt_updatestart() {
    return this.dt_updatestart;
  }
  
    /**
     *
     * @param dt_updatestart
     */
    public void setDt_updatestart(String dt_updatestart) {
    this.dt_updatestart = dt_updatestart;
  }
  
    /**
     *
     * @return
     */
    public String getDt_upload() {
    return this.dt_upload;
  }
  
    /**
     *
     * @param dt_upload
     */
    public void setDt_upload(String dt_upload) {
    this.dt_upload = dt_upload;
  }
}


/* Location:              C:\Users\rcosco\Desktop\classes\!\entity\Newsletters.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */