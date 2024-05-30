/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rc.so.entity;

/**
 *
 * @author rcosco
 */
public class VerificaRicarica {
    String resultCode,resultDesc;
    String idTranPaymat,idTranEsterno;

    /**
     *
     * @return
     */
    public String getResultCode() {
        return resultCode;
    }

    /**
     *
     * @param resultCode
     */
    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    /**
     *
     * @return
     */
    public String getResultDesc() {
        return resultDesc;
    }

    /**
     *
     * @param resultDesc
     */
    public void setResultDesc(String resultDesc) {
        this.resultDesc = resultDesc;
    }

    /**
     *
     * @return
     */
    public String getIdTranPaymat() {
        return idTranPaymat;
    }

    /**
     *
     * @param idTranPaymat
     */
    public void setIdTranPaymat(String idTranPaymat) {
        this.idTranPaymat = idTranPaymat;
    }

    /**
     *
     * @return
     */
    public String getIdTranEsterno() {
        return idTranEsterno;
    }

    /**
     *
     * @param idTranEsterno
     */
    public void setIdTranEsterno(String idTranEsterno) {
        this.idTranEsterno = idTranEsterno;
    }
    
}