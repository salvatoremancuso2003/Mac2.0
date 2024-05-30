/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rc.so.entity;

import static rc.so.util.Constant.is_CZ;
import static rc.so.util.Constant.patternnormdate_f;
import static rc.so.util.Constant.patternsqldate;
import static rc.so.util.Engine.get_BCE_CZ;
import static rc.so.util.Engine.query_transaction_value;
import static rc.so.util.Utility.fd;
import static rc.so.util.Utility.formatMysqltoDisplay;
import static rc.so.util.Utility.generaId;
import static rc.so.util.Utility.parseDoubleR;
import static rc.so.util.Utility.roundDoubleandFormat;
import java.math.BigDecimal;
import static java.math.RoundingMode.HALF_EVEN;
import static org.apache.commons.lang3.StringUtils.substring;
import org.joda.time.DateTime;
import static org.joda.time.format.DateTimeFormat.forPattern;
import org.joda.time.format.DateTimeFormatter;

/**
 *
 * @author rcosco
 */
public class Ch_transaction_refund {

    String cod, cod_tr, from, method, branch_cod, type, value, cod_usaegetta, status, user_refund, dt_refund, idopentill_refund, timestamp;

    String descrizione;

    /**
     * Constructor
     */
    public Ch_transaction_refund() {
    }

    /**
     *
     * @param cod
     * @param cod_tr
     * @param from
     * @param method
     * @param branch_cod
     * @param type
     * @param value
     * @param cod_usaegetta
     * @param status
     * @param user_refund
     * @param dt_refund
     * @param idopentill_refund
     * @param timestamp
     */
    public Ch_transaction_refund(String cod, String cod_tr, String from, String method, String branch_cod, String type, String value, String cod_usaegetta, String status, String user_refund, String dt_refund, String idopentill_refund, String timestamp) {
        this.cod = cod;
        this.cod_tr = cod_tr;
        this.from = from;
        this.method = method;
        this.branch_cod = branch_cod;
        this.type = type;
        this.value = value;
        this.cod_usaegetta = cod_usaegetta;
        this.status = status;
        this.user_refund = user_refund;
        this.dt_refund = dt_refund;
        this.idopentill_refund = idopentill_refund;
        this.timestamp = timestamp;
        this.descrizione = "";

    }

    /**
     *
     * @param tr
     */
    public Ch_transaction_refund(Ch_transaction tr) {
        this.cod = "REF" + generaId(47);
        this.cod_tr = tr.getCod();
        this.from = "CE";
        this.method = "BO";
        this.branch_cod = tr.getFiliale();
        this.type = "CO";
        this.value = roundDoubleandFormat(fd(tr.getCommission()) + parseDoubleR(tr.getRound()) + fd(tr.getSpread_total()), 2);
        this.descrizione = "";
        if (is_CZ) {
            this.value = getValueRefund_CZ(tr);
        }
        this.status = "0";

    }

    private String getValueRefund_CZ(Ch_transaction tr) {
        Ch_transaction_value va = query_transaction_value(tr.getCod()).get(0);
        DateTimeFormatter formatter = forPattern(patternsqldate);
        DateTime dt = formatter.parseDateTime(substring(tr.getData(), 0, 19));
        String cnb_rate = get_BCE_CZ(dt, va.getValuta());
        double original_controv = fd(va.getNet());
        double now_controv = fd(va.getQuantita()) * fd(cnb_rate);
        this.descrizione = "(CNB RATE: " + formatMysqltoDisplay(roundDoubleandFormat(fd(cnb_rate), 2)) + " - DATE: " + dt.minusDays(1).toString(patternnormdate_f) + ")";
        String value_refund;
        if (tr.getTipotr().equals("B")) {
            BigDecimal result = new BigDecimal(roundDoubleandFormat(now_controv - original_controv, 2));
            result = result.setScale(0, HALF_EVEN);
            value_refund = result.toPlainString();
        } else {
            BigDecimal result = new BigDecimal(roundDoubleandFormat(original_controv - now_controv, 2));
            result = result.setScale(0, HALF_EVEN);
            value_refund = result.toPlainString();
        }
        return roundDoubleandFormat(fd(value_refund), 2);
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
    public String getTimestamp() {
        return timestamp;
    }

    /**
     *
     * @param timestamp
     */
    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
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
    public String getCod_tr() {
        return cod_tr;
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
    public String getFrom() {
        return from;
    }

    /**
     *
     * @param from
     */
    public void setFrom(String from) {
        this.from = from;
    }

    /**
     *
     * @return
     */
    public String getMethod() {
        return method;
    }

    /**
     *
     * @param method
     */
    public void setMethod(String method) {
        this.method = method;
    }

    /**
     *
     * @return
     */
    public String getBranch_cod() {
        return branch_cod;
    }

    /**
     *
     * @param branch_cod
     */
    public void setBranch_cod(String branch_cod) {
        this.branch_cod = branch_cod;
    }

    /**
     *
     * @return
     */
    public String getType() {
        return type;
    }

    /**
     *
     * @param type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     *
     * @return
     */
    public String getValue() {
        return value;
    }

    /**
     *
     * @param value
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     *
     * @return
     */
    public String getCod_usaegetta() {
        return cod_usaegetta;
    }

    /**
     *
     * @param cod_usaegetta
     */
    public void setCod_usaegetta(String cod_usaegetta) {
        this.cod_usaegetta = cod_usaegetta;
    }

    /**
     *
     * @return
     */
    public String getStatus() {
        return status;
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
    public String getUser_refund() {
        return user_refund;
    }

    /**
     *
     * @param user_refund
     */
    public void setUser_refund(String user_refund) {
        this.user_refund = user_refund;
    }

    /**
     *
     * @return
     */
    public String getDt_refund() {
        return dt_refund;
    }

    /**
     *
     * @param dt_refund
     */
    public void setDt_refund(String dt_refund) {
        this.dt_refund = dt_refund;
    }

    /**
     *
     * @return
     */
    public String getIdopentill_refund() {
        return idopentill_refund;
    }

    /**
     *
     * @param idopentill_refund
     */
    public void setIdopentill_refund(String idopentill_refund) {
        this.idopentill_refund = idopentill_refund;
    }

}
