package rc.so.entity;

import java.util.ArrayList;

/**
 *
 * @author rcosco
 */
public class Till
{
  String cod;
  String name;
  boolean safe;
  boolean open;
  String fil_opcl;
  String id_opcl;
  String date_opcl;
  String cod_opcl;
  String us_opcl;
  String ty_opcl;
  String labeltools;
  String labelbutton;
  String classbutton;
  String classicon;
  
    /**
     *
     * @param cod
     * @param name
     * @param safe
     * @param open
     */
    public Till(String cod, String name, boolean safe, boolean open) {
    this.cod = cod;
    this.name = name;
    this.safe = safe;
    this.open = open;
    
    if (this.open) {
      this.labeltools = "Close this";
      this.labelbutton = "Open";
      this.classbutton = "green-jungle";
      this.classicon = "fa-check";
    } else {
      this.labeltools = "Open this";
      this.labelbutton = "Closed";
      this.classbutton = "red";
      this.classicon = "fa-remove";
    }
  }
  
    /**
     *
     * @param fil_opcl
     * @param id_opcl
     * @param date_opcl
     * @param cod_opcl
     * @param us_opcl
     * @param ty_opcl
     * @param cod
     * @param name
     * @param safe
     */
    public Till(String fil_opcl, String id_opcl, String date_opcl, String cod_opcl, String us_opcl, String ty_opcl, String cod, String name, boolean safe)
  {
    this.fil_opcl = fil_opcl;
    this.id_opcl = id_opcl;
    this.date_opcl = date_opcl;
    this.cod_opcl = cod_opcl;
    this.us_opcl = us_opcl;
    this.ty_opcl = ty_opcl;
    this.cod = cod;
    this.name = name;
    if (this.ty_opcl.equals("O")) {
      this.ty_opcl = "OPEN";
      this.labeltools = "Close this";
      this.labelbutton = "Open";
      this.classbutton = "green-jungle";
      this.classicon = "fa-check";
    }
    if (this.ty_opcl.equals("C")) {
      this.ty_opcl = "CLOSE";
      this.labeltools = "Open this";
      this.labelbutton = "Closed";
      this.classbutton = "red";
      this.classicon = "fa-remove";
    }
    
    this.safe = safe;
  }
  
    /**
     *
     * @param al
     * @param cod
     * @return
     */
    public static boolean isSafeTill(ArrayList<Till> al, String cod)
  {
    for (int i = 0; i < al.size(); i++) {
      if (((Till)al.get(i)).getCod().equals(cod)) {
        return ((Till)al.get(i)).isSafe();
      }
    }
    return false;
  }
  
    /**
     *
     * @param al
     * @param cod
     * @return
     */
    public static String formatDescTill(ArrayList<Till> al, String cod) {
    for (int i = 0; i < al.size(); i++) {
      if (((Till)al.get(i)).getCod().equals(cod)) {
        return ((Till)al.get(i)).getName();
      }
    }
    return "-";
  }
  
    /**
     * Constructor
     */
    public Till() {}
  
    /**
     *
     * @return
     */
    public String getLabeltools()
  {
    return this.labeltools;
  }
  
    /**
     *
     * @param labeltools
     */
    public void setLabeltools(String labeltools) {
    this.labeltools = labeltools;
  }
  
    /**
     *
     * @return
     */
    public String getLabelbutton() {
    return this.labelbutton;
  }
  
    /**
     *
     * @param labelbutton
     */
    public void setLabelbutton(String labelbutton) {
    this.labelbutton = labelbutton;
  }
  
    /**
     *
     * @return
     */
    public String getClassbutton() {
    return this.classbutton;
  }
  
    /**
     *
     * @param classbutton
     */
    public void setClassbutton(String classbutton) {
    this.classbutton = classbutton;
  }
  
    /**
     *
     * @return
     */
    public String getClassicon() {
    return this.classicon;
  }
  
    /**
     *
     * @param classicon
     */
    public void setClassicon(String classicon) {
    this.classicon = classicon;
  }
  
    /**
     *
     * @return
     */
    public String getFil_opcl() {
    return this.fil_opcl;
  }
  
    /**
     *
     * @param fil_opcl
     */
    public void setFil_opcl(String fil_opcl) {
    this.fil_opcl = fil_opcl;
  }
  
    /**
     *
     * @return
     */
    public String getId_opcl() {
    return this.id_opcl;
  }
  
    /**
     *
     * @param id_opcl
     */
    public void setId_opcl(String id_opcl) {
    this.id_opcl = id_opcl;
  }
  
    /**
     *
     * @return
     */
    public String getDate_opcl() {
    return this.date_opcl;
  }
  
    /**
     *
     * @param date_opcl
     */
    public void setDate_opcl(String date_opcl) {
    this.date_opcl = date_opcl;
  }
  
    /**
     *
     * @return
     */
    public String getCod_opcl() {
    return this.cod_opcl;
  }
  
    /**
     *
     * @param cod_opcl
     */
    public void setCod_opcl(String cod_opcl) {
    this.cod_opcl = cod_opcl;
  }
  
    /**
     *
     * @return
     */
    public String getUs_opcl() {
    return this.us_opcl;
  }
  
    /**
     *
     * @param us_opcl
     */
    public void setUs_opcl(String us_opcl) {
    this.us_opcl = us_opcl;
  }
  
    /**
     *
     * @return
     */
    public String getTy_opcl() {
    return this.ty_opcl;
  }
  
    /**
     *
     * @param ty_opcl
     */
    public void setTy_opcl(String ty_opcl) {
    this.ty_opcl = ty_opcl;
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
    public String getName() {
    return this.name;
  }
  
    /**
     *
     * @param name
     */
    public void setName(String name) {
    this.name = name;
  }
  
    /**
     *
     * @return
     */
    public boolean isSafe() {
    return this.safe;
  }
  
    /**
     *
     * @param safe
     */
    public void setSafe(boolean safe) {
    this.safe = safe;
  }
  
    /**
     *
     * @return
     */
    public boolean isOpen() {
    return this.open;
  }
  
    /**
     *
     * @param open
     */
    public void setOpen(boolean open) {
    this.open = open;
  }
}


/* Location:              C:\Users\rcosco\Desktop\classes\!\entity\Till.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */