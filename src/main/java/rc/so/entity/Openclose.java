/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rc.so.entity;

import java.util.ArrayList;

/**
 *
 * @author rcosco
 */
public class Openclose {

    String filiale, cod, id, till, user, fg_tipo, data, errors; //generali

    String valuta, kind, value_op, num_kind_op, value_cl, num_kind_cl; //change

    String ip_taglio, ip_quantity, ip_value; //tagli

    String gruppo_nc, quantity_user, quantity_system; //nochange

    String carta_credito,ip_quantity_op,ip_value_op,ip_quantity_sys,ip_value_sys; //pos
    
    String foreign_tr,local_tr,stock_tr,cod_it,cod_itnc;//transfer on safe

    String note, tipo,total_diff,rate; //errors
    
    String fisico;
    
    /**
     * Constructor
     */
    public Openclose() {
    }
    
    /**
     *
     * @param filiale
     * @param cod
     * @param id
     * @param till
     * @param user
     * @param fg_tipo
     * @param data
     * @param errors
     */
    public Openclose(String filiale, String cod, String id, String till, String user, String fg_tipo, String data, String errors) {
        this.filiale = filiale;
        this.cod = cod;
        this.id = id;
        this.till = till;
        this.user = user;
        if (fg_tipo.equals("O")) {
            this.fg_tipo = "OPEN";
        } else if (fg_tipo.equals("C")) {
            this.fg_tipo = "CLOSE";
        }
        this.data = data;
        this.errors = formatErrors(errors);
    }

    /**
     *
     * @param al
     * @param cod
     * @return
     */
    public String formatDescTill(ArrayList<Till> al, String cod) {
        for (int i = 0; i < al.size(); i++) {
            if (al.get(i).getCod().equals(cod)) {
                return al.get(i).getName();
            }
        }
        return "-";
    }
    
    /**
     *
     * @param fg_tipo
     * @return
     */
    public String formatType(String fg_tipo) {
        if (fg_tipo != null) {
            if (fg_tipo.equals("OPEN")) {
                return "<span class='font-blue'>OPEN <i class='fa fa-level-up'></i></span>";
            } else if (fg_tipo.equals("CLOSE")) {
                return "<span class='font-green-sharp'>CLOSE <i class='fa fa-level-down'></i></span>";
            }
        }
        return "-";
    }

    /**
     *
     * @param fg_tipo
     * @return
     */
    public String formatType_cru(String fg_tipo) {
        if (fg_tipo != null) {
            if (fg_tipo.equals("OPEN")) {
                return "OPEN ";
            } else if (fg_tipo.equals("CLOSE")) {
                return "CLOSE ";
            }
        }
        return "-";
    }

    /**
     *
     * @param fg_tipo
     * @return
     */
    public static String formatType_r(String fg_tipo) {
        if (fg_tipo != null) {
            if (fg_tipo.equals("O")) {
                return "OPEN ";
            } else if (fg_tipo.equals("C")) {
                return "CLOSE ";
            }
        }
        return "-";
    }
    
    /**
     *
     * @param errors
     * @return
     */
    public String formatErrors(String errors) {
        if (errors != null) {
            if (errors.equals("N")) {
                return "<div class='font-green-jungle'>No Errors <i class='fa fa-check'></i></div>";
            } else if (errors.equals("Y")) {
                return "<div class='font-red'>Errors <i class='fa fa-remove'></i></div>";
            }
        }
        return "-";
    }
    
    /**
     *
     * @param errors
     * @return
     */
    public String formatErrors_cru(String errors) {
        if (errors != null) {            
            if (errors.contains("fa-check")) {
                return "No Errors";
            } else  {
                return "Errors ";
            }
        }
        return "-";
    }

    /**
     *
     * @param errors
     * @return
     */
    public static String formatTypeErrors(String errors) {
        if (errors != null) {
            switch (errors) {
                case "CH":
                    return "Figures";
                case "NC":
                    return "No Change";
                case "PO":
                    return "POS / Bank Account";
                default:
                    break;
            }
        }
        return "-";
    }

    /**
     *
     * @return
     */
    public String getFisico() {
        return fisico;
    }

    /**
     *
     * @param fisico
     */
    public void setFisico(String fisico) {
        this.fisico = fisico;
    }
    
    /**
     *
     * @return
     */
    public String getRate() {
        return rate;
    }

    /**
     *
     * @param rate
     */
    public void setRate(String rate) {
        this.rate = rate;
    }

    /**
     *
     * @return
     */
    public String getForeign_tr() {
        return foreign_tr;
    }

    /**
     *
     * @param foreign_tr
     */
    public void setForeign_tr(String foreign_tr) {
        this.foreign_tr = foreign_tr;
    }

    /**
     *
     * @return
     */
    public String getLocal_tr() {
        return local_tr;
    }

    /**
     *
     * @param local_tr
     */
    public void setLocal_tr(String local_tr) {
        this.local_tr = local_tr;
    }

    /**
     *
     * @return
     */
    public String getStock_tr() {
        return stock_tr;
    }

    /**
     *
     * @param stock_tr
     */
    public void setStock_tr(String stock_tr) {
        this.stock_tr = stock_tr;
    }

    /**
     *
     * @return
     */
    public String getCod_it() {
        return cod_it;
    }

    /**
     *
     * @param cod_it
     */
    public void setCod_it(String cod_it) {
        this.cod_it = cod_it;
    }
    
    /**
     *
     * @return
     */
    public String getTotal_diff() {
        return total_diff;
    }

    /**
     *
     * @param total_diff
     */
    public void setTotal_diff(String total_diff) {
        this.total_diff = total_diff;
    }
    
    /**
     *
     * @return
     */
    public String getTipo() {
        return tipo;
    }

    /**
     *
     * @param tipo
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    /**
     *
     * @return
     */
    public String getCarta_credito() {
        return carta_credito;
    }

    /**
     *
     * @param carta_credito
     */
    public void setCarta_credito(String carta_credito) {
        this.carta_credito = carta_credito;
    }

    /**
     *
     * @return
     */
    public String getNote() {
        return note;
    }

    /**
     *
     * @param note
     */
    public void setNote(String note) {
        this.note = note;
    }

    /**
     *
     * @return
     */
    public String getGruppo_nc() {
        return gruppo_nc;
    }

    /**
     *
     * @param gruppo_nc
     */
    public void setGruppo_nc(String gruppo_nc) {
        this.gruppo_nc = gruppo_nc;
    }

    /**
     *
     * @return
     */
    public String getQuantity_user() {
        return quantity_user;
    }

    /**
     *
     * @param quantity_user
     */
    public void setQuantity_user(String quantity_user) {
        this.quantity_user = quantity_user;
    }

    /**
     *
     * @return
     */
    public String getQuantity_system() {
        return quantity_system;
    }

    /**
     *
     * @param quantity_system
     */
    public void setQuantity_system(String quantity_system) {
        this.quantity_system = quantity_system;
    }

    /**
     *
     * @return
     */
    public String getIp_taglio() {
        return ip_taglio;
    }

    /**
     *
     * @param ip_taglio
     */
    public void setIp_taglio(String ip_taglio) {
        this.ip_taglio = ip_taglio;
    }

    /**
     *
     * @return
     */
    public String getIp_quantity() {
        return ip_quantity;
    }

    /**
     *
     * @param ip_quantity
     */
    public void setIp_quantity(String ip_quantity) {
        this.ip_quantity = ip_quantity;
    }

    /**
     *
     * @return
     */
    public String getIp_value() {
        return ip_value;
    }

    /**
     *
     * @param ip_value
     */
    public void setIp_value(String ip_value) {
        this.ip_value = ip_value;
    }

    /**
     *
     * @return
     */
    public String getValuta() {
        return valuta;
    }

    /**
     *
     * @param valuta
     */
    public void setValuta(String valuta) {
        this.valuta = valuta;
    }

    /**
     *
     * @return
     */
    public String getKind() {
        return kind;
    }

    /**
     *
     * @param kind
     */
    public void setKind(String kind) {
        this.kind = kind;
    }

    /**
     *
     * @return
     */
    public String getValue_op() {
        return value_op;
    }

    /**
     *
     * @param value_op
     */
    public void setValue_op(String value_op) {
        this.value_op = value_op;
    }

    /**
     *
     * @return
     */
    public String getNum_kind_op() {
        return num_kind_op;
    }

    /**
     *
     * @param num_kind_op
     */
    public void setNum_kind_op(String num_kind_op) {
        this.num_kind_op = num_kind_op;
    }

    /**
     *
     * @return
     */
    public String getValue_cl() {
        return value_cl;
    }

    /**
     *
     * @param value_cl
     */
    public void setValue_cl(String value_cl) {
        this.value_cl = value_cl;
    }

    /**
     *
     * @return
     */
    public String getNum_kind_cl() {
        return num_kind_cl;
    }

    /**
     *
     * @param num_kind_cl
     */
    public void setNum_kind_cl(String num_kind_cl) {
        this.num_kind_cl = num_kind_cl;
    }

    /**
     *
     * @return
     */
    public String getFiliale() {
        return filiale;
    }

    /**
     *
     * @param filiale
     */
    public void setFiliale(String filiale) {
        this.filiale = filiale;
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
    public String getId() {
        return id;
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
    public String getTill() {
        return till;
    }

    /**
     *
     * @param till
     */
    public void setTill(String till) {
        this.till = till;
    }

    /**
     *
     * @return
     */
    public String getUser() {
        return user;
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
    public String getFg_tipo() {
        return fg_tipo;
    }

    /**
     *
     * @param fg_tipo
     */
    public void setFg_tipo(String fg_tipo) {
        this.fg_tipo = fg_tipo;
    }

    /**
     *
     * @return
     */
    public String getData() {
        return data;
    }

    /**
     *
     * @param data
     */
    public void setData(String data) {
        this.data = data;
    }

    /**
     *
     * @return
     */
    public String getErrors() {
        return errors;
    }

    /**
     *
     * @param errors
     */
    public void setErrors(String errors) {
        this.errors = errors;
    }

    /**
     *
     * @return
     */
    public String getIp_quantity_op() {
        return ip_quantity_op;
    }

    /**
     *
     * @param ip_quantity_op
     */
    public void setIp_quantity_op(String ip_quantity_op) {
        this.ip_quantity_op = ip_quantity_op;
    }

    /**
     *
     * @return
     */
    public String getIp_value_op() {
        return ip_value_op;
    }

    /**
     *
     * @param ip_value_op
     */
    public void setIp_value_op(String ip_value_op) {
        this.ip_value_op = ip_value_op;
    }

    /**
     *
     * @return
     */
    public String getIp_quantity_sys() {
        return ip_quantity_sys;
    }

    /**
     *
     * @param ip_quantity_sys
     */
    public void setIp_quantity_sys(String ip_quantity_sys) {
        this.ip_quantity_sys = ip_quantity_sys;
    }

    /**
     *
     * @return
     */
    public String getIp_value_sys() {
        return ip_value_sys;
    }

    /**
     *
     * @param ip_value_sys
     */
    public void setIp_value_sys(String ip_value_sys) {
        this.ip_value_sys = ip_value_sys;
    }

    /**
     *
     * @return
     */
    public String getCod_itnc() {
        return cod_itnc;
    }

    /**
     *
     * @param cod_itnc
     */
    public void setCod_itnc(String cod_itnc) {
        this.cod_itnc = cod_itnc;
    }
    

}