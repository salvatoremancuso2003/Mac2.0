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
public class Atl_dati_clienti {

    String cod, clientcode, ragsoc1, ragsoc2, address, city, country, clientnumber;

    String zipcode, district, fatelet;

    /**
     *
     * @param cod
     * @param clientcode
     * @param ragsoc1
     * @param ragsoc2
     * @param address
     * @param city
     * @param country
     * @param clientnumber
     * @param zipcode
     * @param district
     * @param fatelet
     */
    public Atl_dati_clienti(String cod, String clientcode, String ragsoc1, String ragsoc2, String address, String city, String country, String clientnumber, String zipcode, String district, String fatelet) {
        this.cod = cod;
        this.clientcode = clientcode;
        this.ragsoc1 = ragsoc1;
        this.ragsoc2 = ragsoc2;
        this.address = address;
        this.city = city;
        this.country = country;
        this.clientnumber = clientnumber;
        this.zipcode = zipcode;
        this.district = district;
        this.fatelet = fatelet;
    }

    /**
     *
     * @return
     */
    public String getZipcode() {
        return zipcode;
    }

    /**
     *
     * @param zipcode
     */
    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    /**
     *
     * @return
     */
    public String getDistrict() {
        return district;
    }

    /**
     *
     * @param district
     */
    public void setDistrict(String district) {
        this.district = district;
    }

    /**
     *
     * @return
     */
    public String getFatelet() {
        return fatelet;
    }

    /**
     *
     * @param fatelet
     */
    public void setFatelet(String fatelet) {
        this.fatelet = fatelet;
    }

    /**
     *
     * @return
     */
    public String controlli_dati_clienti() {

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
    public String getClientcode() {
        return clientcode;
    }

    /**
     *
     * @param clientcode
     */
    public void setClientcode(String clientcode) {
        this.clientcode = clientcode;
    }

    /**
     *
     * @return
     */
    public String getRagsoc1() {
        return ragsoc1;
    }

    /**
     *
     * @param ragsoc1
     */
    public void setRagsoc1(String ragsoc1) {
        this.ragsoc1 = ragsoc1;
    }

    /**
     *
     * @return
     */
    public String getRagsoc2() {
        return ragsoc2;
    }

    /**
     *
     * @param ragsoc2
     */
    public void setRagsoc2(String ragsoc2) {
        this.ragsoc2 = ragsoc2;
    }

    /**
     *
     * @return
     */
    public String getAddress() {
        return address;
    }

    /**
     *
     * @param address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     *
     * @return
     */
    public String getCity() {
        return city;
    }

    /**
     *
     * @param city
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     *
     * @return
     */
    public String getCountry() {
        return country;
    }

    /**
     *
     * @param country
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     *
     * @return
     */
    public String getClientnumber() {
        return clientnumber;
    }

    /**
     *
     * @param clientnumber
     */
    public void setClientnumber(String clientnumber) {
        this.clientnumber = clientnumber;
    }

}
