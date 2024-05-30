/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rc.so.atlante;

/**
 *
 * @author rcosco
 */
public class Atl_detailsiva_dati_fatture {

    String cod, nrigaiva, contoiva, segnoiva, codeiva, imponibile, iva;

    /**
     *
     * @param cod
     * @param nrigaiva
     * @param contoiva
     * @param segnoiva
     * @param codeiva
     * @param imponibile
     * @param iva
     */
    public Atl_detailsiva_dati_fatture(String cod, String nrigaiva, String contoiva, String segnoiva, String codeiva, String imponibile, String iva) {
        this.cod = cod;
        this.nrigaiva = nrigaiva;
        this.contoiva = contoiva;
        this.segnoiva = segnoiva;
        this.codeiva = codeiva;
        this.imponibile = imponibile;
        this.iva = iva;
    }

    /**
     *
     * @return
     */
    public String controlli_details() {
        return "OK";
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
    public String getNrigaiva() {
        return nrigaiva;
    }

    /**
     *
     * @param nrigaiva
     */
    public void setNrigaiva(String nrigaiva) {
        this.nrigaiva = nrigaiva;
    }

    /**
     *
     * @return
     */
    public String getContoiva() {
        return contoiva;
    }

    /**
     *
     * @param contoiva
     */
    public void setContoiva(String contoiva) {
        this.contoiva = contoiva;
    }

    /**
     *
     * @return
     */
    public String getSegnoiva() {
        return segnoiva;
    }

    /**
     *
     * @param segnoiva
     */
    public void setSegnoiva(String segnoiva) {
        this.segnoiva = segnoiva;
    }

    /**
     *
     * @return
     */
    public String getCodeiva() {
        return codeiva;
    }

    /**
     *
     * @param codeiva
     */
    public void setCodeiva(String codeiva) {
        this.codeiva = codeiva;
    }

    /**
     *
     * @return
     */
    public String getImponibile() {
        return imponibile;
    }

    /**
     *
     * @param imponibile
     */
    public void setImponibile(String imponibile) {
        this.imponibile = imponibile;
    }

    /**
     *
     * @return
     */
    public String getIva() {
        return iva;
    }

    /**
     *
     * @param iva
     */
    public void setIva(String iva) {
        this.iva = iva;
    }

}
