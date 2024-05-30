/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rc.so.db;

import static rc.so.util.Constant.test;
import static java.lang.Class.forName;
import java.sql.Connection;
import static java.sql.DriverManager.getConnection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import static rc.so.util.Constant.rb;

/**
 *
 * @author rcosco
 */
public class Db_Crm {

    Connection c = null;

    /**
     * Constructor
     */
    public Db_Crm() {
        try {
            String drivername = rb.getString("db.driver");
            String typedb = rb.getString("db.tipo");
            String user = rb.getString("us.db");
            String pwd = rb.getString("us.pwd");

            String host = rb.getString("db.ip") + "/mac_crm_prod";
            if (test) {
                host = rb.getString("db.ip") + "/mac_crm";
            }

            forName(drivername).newInstance();
            Properties p = new Properties();
            p.put("user", user);
            p.put("password", pwd);
            p.put("useUnicode", "true");
            p.put("characterEncoding", "UTF-8");
            p.put("useSSL", "false");
            p.put("connectTimeout", "1000");
            p.put("useUnicode", "true");
            p.put("useJDBCCompliantTimezoneShift", "true");
            p.put("useLegacyDatetimeCode", "false");
            p.put("serverTimezone", "Europe/Rome");
            this.c = getConnection("jdbc:" + typedb + ":" + host, p);
        } catch (Exception ex) {
            this.c = null;
            ex.printStackTrace();
        }
    }

    /**
     *
     * @return
     */
    public Connection getC() {
        return c;
    }

    /**
     *
     * @param c
     */
    public void setC(Connection c) {
        this.c = c;
    }

    /**
     * closeDB
     */
    public void closeDB() {
        try {
            if (this.c != null) {
                this.c.close();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public String get_ValueSettings(String id) {
        try {
            String sql = "SELECT value FROM settings WHERE id = ?";
            PreparedStatement ps = this.c.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString(1);
            }
            rs.close();
            ps.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

}
