package rc.so.entity;

/**
 *
 * @author rcosco
 */
public class Currency {

    String id;
    String filial;
    String code;
    String uic;
    String descrizione;
    String enable_sellback;
//    String change_buy;
    
    String change_sell;
    String message;
    String internal_cur;
    String enable_buy;
    String enable_sell;
    String buy_std;
    String buy_l1;
    String buy_l2;
    String buy_l3;
    String buy_best;
    
    //agg
    String editce;
    String dt_val;

    /**
     *
     * @return
     */
    public String getDt_val() {
        return dt_val;
    }

    /**
     *
     * @param dt_val
     */
    public void setDt_val(String dt_val) {
        this.dt_val = dt_val;
    }

    /**
     *
     * @return
     */
    public String getEditce() {
        return editce;
    }

    /**
     *
     * @param editce
     */
    public void setEditce(String editce) {
        this.editce = editce;
    }

    /**
     *
     * @return
     */
    public String getCambio_bce() {
        return this.cambio_bce;
    }

    String sell_std;

    /**
     *
     * @param cambio_bce
     */
    public void setCambio_bce(String cambio_bce) {
        this.cambio_bce = cambio_bce;
    }

    String sell_l1;

    /**
     *
     * @return
     */
    public String getBuy_std_type() {
        return this.buy_std_type;
    }

    String sell_l2;
    String sell_l3;
    String sell_best;

    /**
     *
     * @param buy_std_type
     */
    public void setBuy_std_type(String buy_std_type) {
        this.buy_std_type = buy_std_type;
    }

    String cambio_bce;

    /**
     *
     * @return
     */
    public String getBuy_std_value() {
        return this.buy_std_value;
    }

    String buy_std_type;

    /**
     *
     * @param buy_std_value
     */
    public void setBuy_std_value(String buy_std_value) {
        this.buy_std_value = buy_std_value;
    }

    String buy_std_value;

    /**
     *
     * @return
     */
    public String getSell_std_type() {
        return this.sell_std_type;
    }

    String sell_std_type;

    /**
     *
     * @param sell_std_type
     */
    public void setSell_std_type(String sell_std_type) {
        this.sell_std_type = sell_std_type;
    }

    String sell_std_value;

    /**
     *
     * @return
     */
    public String getSell_std_value() {
        return this.sell_std_value;
    }

    /**
     *
     * @param sell_std_value
     */
    public void setSell_std_value(String sell_std_value) {
        this.sell_std_value = sell_std_value;
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
    public String getFilial() {
        return this.filial;
    }

    /**
     *
     * @param filial
     */
    public void setFilial(String filial) {
        this.filial = filial;
    }

    /**
     *
     * @return
     */
    public String getCode() {
        return this.code;
    }

    /**
     *
     * @param code
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     *
     * @return
     */
    public String getUic() {
        return this.uic;
    }

    /**
     *
     * @param uic
     */
    public void setUic(String uic) {
        this.uic = uic;
    }

    /**
     *
     * @return
     */
    public String getDescrizione() {
        return this.descrizione;
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
    public String getEnable_sellback() {
        return enable_sellback;
    }

    /**
     *
     * @param enable_sellback
     */
    public void setEnable_sellback(String enable_sellback) {
        this.enable_sellback = enable_sellback;
    }

    /**
     *
     * @return
     */
    public String getChange_sell() {
        return this.change_sell;
    }

    /**
     *
     * @param change_sell
     */
    public void setChange_sell(String change_sell) {
        this.change_sell = change_sell;
    }

    /**
     *
     * @return
     */
    public String getInternal_cur() {
        return this.internal_cur;
    }

    /**
     *
     * @param internal_cur
     */
    public void setInternal_cur(String internal_cur) {
        this.internal_cur = internal_cur;
    }

    /**
     *
     * @return
     */
    public String getEnable_buy() {
        return this.enable_buy;
    }

    /**
     *
     * @param enable_buy
     */
    public void setEnable_buy(String enable_buy) {
        this.enable_buy = enable_buy;
    }

    /**
     *
     * @return
     */
    public String getEnable_sell() {
        return this.enable_sell;
    }

    /**
     *
     * @param enable_sell
     */
    public void setEnable_sell(String enable_sell) {
        this.enable_sell = enable_sell;
    }

    /**
     *
     * @return
     */
    public String getMessage() {
        return this.message;
    }

    /**
     *
     * @param message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     *
     * @return
     */
    public String getBuy_std() {
        return this.buy_std;
    }

    /**
     *
     * @param buy_std
     */
    public void setBuy_std(String buy_std) {
        this.buy_std = buy_std;
    }

    /**
     *
     * @return
     */
    public String getBuy_l1() {
        return this.buy_l1;
    }

    /**
     *
     * @param buy_l1
     */
    public void setBuy_l1(String buy_l1) {
        this.buy_l1 = buy_l1;
    }

    /**
     *
     * @return
     */
    public String getBuy_l2() {
        return this.buy_l2;
    }

    /**
     *
     * @param buy_l2
     */
    public void setBuy_l2(String buy_l2) {
        this.buy_l2 = buy_l2;
    }

    /**
     *
     * @return
     */
    public String getBuy_l3() {
        return this.buy_l3;
    }

    /**
     *
     * @param buy_l3
     */
    public void setBuy_l3(String buy_l3) {
        this.buy_l3 = buy_l3;
    }

    /**
     *
     * @return
     */
    public String getBuy_best() {
        return this.buy_best;
    }

    /**
     *
     * @param buy_best
     */
    public void setBuy_best(String buy_best) {
        this.buy_best = buy_best;
    }

    /**
     *
     * @return
     */
    public String getSell_std() {
        return this.sell_std;
    }

    /**
     *
     * @param sell_std
     */
    public void setSell_std(String sell_std) {
        this.sell_std = sell_std;
    }

    /**
     *
     * @return
     */
    public String getSell_l1() {
        return this.sell_l1;
    }

    /**
     *
     * @param sell_l1
     */
    public void setSell_l1(String sell_l1) {
        this.sell_l1 = sell_l1;
    }

    /**
     *
     * @return
     */
    public String getSell_l2() {
        return this.sell_l2;
    }

    /**
     *
     * @param sell_l2
     */
    public void setSell_l2(String sell_l2) {
        this.sell_l2 = sell_l2;
    }

    /**
     *
     * @return
     */
    public String getSell_l3() {
        return this.sell_l3;
    }

    /**
     *
     * @param sell_l3
     */
    public void setSell_l3(String sell_l3) {
        this.sell_l3 = sell_l3;
    }

    /**
     *
     * @return
     */
    public String getSell_best() {
        return this.sell_best;
    }

    /**
     *
     * @param sell_best
     */
    public void setSell_best(String sell_best) {
        this.sell_best = sell_best;
    }
}


/* Location:              C:\Users\rcosco\Desktop\classes\!\entity\Currency.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */
