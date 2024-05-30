/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rc.so.rest;

/**
 *
 * @author rcosco
 */
public class EsitoTransazioneResult_old {
    
    String TRANSACTIONCODE,DATAOPERAZIONE,STATUSCODE,ERRORCODE,ERRORMESSAGE,IDTRANSAZIONE;

    //scratch

    String TECHERRORMESSAGE, AMOUNT, NOT, PIN, SERIALNUMBER, DATASCADENZA;

    /**
     *
     * @param TRANSACTIONCODE
     * @param DATAOPERAZIONE
     * @param STATUSCODE
     * @param ERRORCODE
     * @param ERRORMESSAGE
     * @param TECHERRORMESSAGE
     * @param AMOUNT
     * @param NOT
     * @param PIN
     * @param SERIALNUMBER
     * @param DATASCADENZA
     */
    public EsitoTransazioneResult_old(String TRANSACTIONCODE, String DATAOPERAZIONE, String STATUSCODE, String ERRORCODE, String ERRORMESSAGE, 
            String TECHERRORMESSAGE, String AMOUNT, String NOT, String PIN, String SERIALNUMBER, String DATASCADENZA) {
        this.TRANSACTIONCODE = TRANSACTIONCODE;
        this.DATAOPERAZIONE = DATAOPERAZIONE;
        this.STATUSCODE = STATUSCODE;
        this.ERRORCODE = ERRORCODE;
        this.ERRORMESSAGE = ERRORMESSAGE;
        this.TECHERRORMESSAGE = TECHERRORMESSAGE;
        this.AMOUNT = AMOUNT;
        this.NOT = NOT;
        this.PIN = PIN;
        this.SERIALNUMBER = SERIALNUMBER;
        this.DATASCADENZA = DATASCADENZA;
    }
    
    /**
     *
     * @param TRANSACTIONCODE
     * @param DATAOPERAZIONE
     * @param STATUSCODE
     * @param ERRORCODE
     * @param ERRORMESSAGE
     * @param IDTRANSAZIONE
     */
    public EsitoTransazioneResult_old(String TRANSACTIONCODE, String DATAOPERAZIONE, String STATUSCODE, String ERRORCODE, String ERRORMESSAGE, String IDTRANSAZIONE) {
        this.TRANSACTIONCODE = TRANSACTIONCODE;
        this.DATAOPERAZIONE = DATAOPERAZIONE;
        this.STATUSCODE = STATUSCODE;
        this.ERRORCODE = ERRORCODE;
        this.ERRORMESSAGE = ERRORMESSAGE;
        this.IDTRANSAZIONE = IDTRANSAZIONE;
    }



    /**
     *
     * @return
     */
    public String getTRANSACTIONCODE() {
        return TRANSACTIONCODE;
    }

    /**
     *
     * @param TRANSACTIONCODE
     */
    public void setTRANSACTIONCODE(String TRANSACTIONCODE) {
        this.TRANSACTIONCODE = TRANSACTIONCODE;
    }

    /**
     *
     * @return
     */
    public String getDATAOPERAZIONE() {
        return DATAOPERAZIONE;
    }

    /**
     *
     * @param DATAOPERAZIONE
     */
    public void setDATAOPERAZIONE(String DATAOPERAZIONE) {
        this.DATAOPERAZIONE = DATAOPERAZIONE;
    }

    /**
     *
     * @return
     */
    public String getSTATUSCODE() {
        return STATUSCODE;
    }

    /**
     *
     * @param STATUSCODE
     */
    public void setSTATUSCODE(String STATUSCODE) {
        this.STATUSCODE = STATUSCODE;
    }

    /**
     *
     * @return
     */
    public String getERRORCODE() {
        return ERRORCODE;
    }

    /**
     *
     * @param ERRORCODE
     */
    public void setERRORCODE(String ERRORCODE) {
        this.ERRORCODE = ERRORCODE;
    }

    /**
     *
     * @return
     */
    public String getERRORMESSAGE() {
        return ERRORMESSAGE;
    }

    /**
     *
     * @param ERRORMESSAGE
     */
    public void setERRORMESSAGE(String ERRORMESSAGE) {
        this.ERRORMESSAGE = ERRORMESSAGE;
    }

    /**
     *
     * @return
     */
    public String getIDTRANSAZIONE() {
        return IDTRANSAZIONE;
    }

    /**
     *
     * @param IDTRANSAZIONE
     */
    public void setIDTRANSAZIONE(String IDTRANSAZIONE) {
        this.IDTRANSAZIONE = IDTRANSAZIONE;
    }

    /**
     *
     * @return
     */
    public String getTECHERRORMESSAGE() {
        return TECHERRORMESSAGE;
    }

    /**
     *
     * @param TECHERRORMESSAGE
     */
    public void setTECHERRORMESSAGE(String TECHERRORMESSAGE) {
        this.TECHERRORMESSAGE = TECHERRORMESSAGE;
    }

    /**
     *
     * @return
     */
    public String getAMOUNT() {
        return AMOUNT;
    }

    /**
     *
     * @param AMOUNT
     */
    public void setAMOUNT(String AMOUNT) {
        this.AMOUNT = AMOUNT;
    }

    /**
     *
     * @return
     */
    public String getNOT() {
        return NOT;
    }

    /**
     *
     * @param NOT
     */
    public void setNOT(String NOT) {
        this.NOT = NOT;
    }

    /**
     *
     * @return
     */
    public String getPIN() {
        return PIN;
    }

    /**
     *
     * @param PIN
     */
    public void setPIN(String PIN) {
        this.PIN = PIN;
    }

    /**
     *
     * @return
     */
    public String getSERIALNUMBER() {
        return SERIALNUMBER;
    }

    /**
     *
     * @param SERIALNUMBER
     */
    public void setSERIALNUMBER(String SERIALNUMBER) {
        this.SERIALNUMBER = SERIALNUMBER;
    }

    /**
     *
     * @return
     */
    public String getDATASCADENZA() {
        return DATASCADENZA;
    }

    /**
     *
     * @param DATASCADENZA
     */
    public void setDATASCADENZA(String DATASCADENZA) {
        this.DATASCADENZA = DATASCADENZA;
    }
    
    
    
}
