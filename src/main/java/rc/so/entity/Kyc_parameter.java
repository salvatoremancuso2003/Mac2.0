/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rc.so.entity;

import static com.google.common.base.Splitter.on;
import static com.google.common.collect.Lists.newArrayList;
import rc.so.db.Db_Master;
import java.util.List;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import static org.apache.commons.lang3.builder.ToStringStyle.SHORT_PREFIX_STYLE;

/**
 *
 * @author rcosco
 */
public class Kyc_parameter {

    String kyc_fa1, kyc_fa2, kyc_fa3;
    String kyc_va1, kyc_va2;
    String kyc_vro;
    List<String> kyc_listro;
    List<String> kyc_listok;

    int op_giorn, op_sett, op_mensili;
    double vol_weekly, vol_mensile, vol_trimest;

    /**
     *
     * @param op_giorn
     * @param op_sett
     * @param op_mensili
     * @param vol_weekly
     * @param vol_mensile
     * @param vol_trimest
     */
    public Kyc_parameter(int op_giorn, int op_sett, int op_mensili, double vol_weekly, double vol_mensile, double vol_trimest) {
        this.op_giorn = op_giorn;
        this.op_sett = op_sett;
        this.op_mensili = op_mensili;
        this.vol_weekly = vol_weekly;
        this.vol_mensile = vol_mensile;
        this.vol_trimest = vol_trimest;
    }

    /**
     * Constructor
     */
    public Kyc_parameter() {
        Db_Master db = new Db_Master();
        String kyc_fa = db.getPath("kyc_fa");
        String kyc_va = db.getPath("kyc_va");
        String kyc_list = db.getPath("kyc_listro");
        String kyc_listok1 = db.getPath("kyc_listok");
        this.kyc_vro = db.getPath("kyc_vro");
        db.closeDB();

        this.kyc_fa1 = kyc_fa.split(";")[0];
        this.kyc_fa2 = kyc_fa.split(";")[1];
        this.kyc_fa3 = kyc_fa.split(";")[2];

        this.kyc_va1 = kyc_va.split(";")[0];
        this.kyc_va2 = kyc_va.split(";")[1];

        this.kyc_listro = newArrayList(on(";").split(kyc_list));
        this.kyc_listok = newArrayList(on(";").split(kyc_listok1));

    }

    /**
     *
     * @param index
     * @return
     */
    public static String format_Type_KYC(int index) {
        if (index == 0) {
            return "Abnormal Frequency Daily";
        } else if (index == 1) {
            return "Abnormal Frequency Weekly";
        } else if (index == 2) {
            return "Abnormal Frequency Monthly";
        } else if (index == 3) {
            return "Abnormal Volume Monthly";
        } else if (index == 4) {
            return "Abnormal Volume Quarterly";
        } else if (index == 5) {
            return "Volume 'Rogue Country' Weekly";
        } else if (index == 6) {
            return "Big Notes";
        } else if (index == 7) {
            return "PEP";
        } else if (index == 8) {
            return "USER";
        } else if (index == 9) {
            return "THRESHOLD";
        }
        return "-";
    }

    /**
     *
     * @return
     */
    public List<String> getKyc_listok() {
        return kyc_listok;
    }

    /**
     *
     * @param kyc_listok
     */
    public void setKyc_listok(List<String> kyc_listok) {
        this.kyc_listok = kyc_listok;
    }

    /**
     *
     * @return
     */
    public int getOp_giorn() {
        return op_giorn;
    }

    /**
     *
     * @param op_giorn
     */
    public void setOp_giorn(int op_giorn) {
        this.op_giorn = op_giorn;
    }

    /**
     *
     * @return
     */
    public int getOp_sett() {
        return op_sett;
    }

    /**
     *
     * @param op_sett
     */
    public void setOp_sett(int op_sett) {
        this.op_sett = op_sett;
    }

    /**
     *
     * @return
     */
    public int getOp_mensili() {
        return op_mensili;
    }

    /**
     *
     * @param op_mensili
     */
    public void setOp_mensili(int op_mensili) {
        this.op_mensili = op_mensili;
    }

    /**
     *
     * @return
     */
    public double getVol_weekly() {
        return vol_weekly;
    }

    /**
     *
     * @param vol_weekly
     */
    public void setVol_weekly(double vol_weekly) {
        this.vol_weekly = vol_weekly;
    }

    /**
     *
     * @return
     */
    public double getVol_mensile() {
        return vol_mensile;
    }

    /**
     *
     * @param vol_mensile
     */
    public void setVol_mensile(double vol_mensile) {
        this.vol_mensile = vol_mensile;
    }

    /**
     *
     * @return
     */
    public double getVol_trimest() {
        return vol_trimest;
    }

    /**
     *
     * @param vol_trimest
     */
    public void setVol_trimest(double vol_trimest) {
        this.vol_trimest = vol_trimest;
    }

    /**
     *
     * @return
     */
    public String getKyc_fa1() {
        return kyc_fa1;
    }

    /**
     *
     * @param kyc_fa1
     */
    public void setKyc_fa1(String kyc_fa1) {
        this.kyc_fa1 = kyc_fa1;
    }

    /**
     *
     * @return
     */
    public String getKyc_fa2() {
        return kyc_fa2;
    }

    /**
     *
     * @param kyc_fa2
     */
    public void setKyc_fa2(String kyc_fa2) {
        this.kyc_fa2 = kyc_fa2;
    }

    /**
     *
     * @return
     */
    public String getKyc_fa3() {
        return kyc_fa3;
    }

    /**
     *
     * @param kyc_fa3
     */
    public void setKyc_fa3(String kyc_fa3) {
        this.kyc_fa3 = kyc_fa3;
    }

    /**
     *
     * @return
     */
    public String getKyc_va1() {
        return kyc_va1;
    }

    /**
     *
     * @param kyc_va1
     */
    public void setKyc_va1(String kyc_va1) {
        this.kyc_va1 = kyc_va1;
    }

    /**
     *
     * @return
     */
    public String getKyc_va2() {
        return kyc_va2;
    }

    /**
     *
     * @param kyc_va2
     */
    public void setKyc_va2(String kyc_va2) {
        this.kyc_va2 = kyc_va2;
    }

    /**
     *
     * @return
     */
    public String getKyc_vro() {
        return kyc_vro;
    }

    /**
     *
     * @param kyc_vro
     */
    public void setKyc_vro(String kyc_vro) {
        this.kyc_vro = kyc_vro;
    }

    /**
     *
     * @return
     */
    public List<String> getKyc_listro() {
        return kyc_listro;
    }

    /**
     *
     * @param kyc_listro
     */
    public void setKyc_listro(List<String> kyc_listro) {
        this.kyc_listro = kyc_listro;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, SHORT_PREFIX_STYLE);
    }
}
