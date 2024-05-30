/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rc.so.db;

import static rc.so.util.Engine.insertTR;
import static java.lang.Class.forName;
import static java.lang.Thread.currentThread;
import java.sql.Connection;
import static java.sql.DriverManager.getConnection;
import java.sql.PreparedStatement;
import static java.sql.ResultSet.CONCUR_UPDATABLE;
import static java.sql.ResultSet.TYPE_SCROLL_INSENSITIVE;
import java.sql.SQLException;
import java.util.Properties;
import static rc.so.util.Constant.rb;

/**
 *
 * @author rcosco
 */
public class Db_EET {

    /**
     *
     */
    public Connection c = null;

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
     ** Constructor
     */
    public Db_EET() {
        try {
            String drivername = rb.getString("db.driver");
            String typedb = rb.getString("db.tipo");
            String user = rb.getString("us.db");
            String pwd = rb.getString("us.pwd");
            String host = rb.getString("db.ip") + "/maccorpeet";
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
        }
    }

    /**
     * close
     */
    public void closeDB() {
        try {
            if (this.c != null) {
                this.c.close();
            }
        } catch (SQLException ex) {
            insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
        }
    }

    /**
     *
     * @param cod
     * @param field
     * @param value
     * @return
     */
    public boolean update_Dati(String cod, String field, String value) {

        String upd = "UPDATE dati SET " + field + " = ? WHERE cod = ?";
        try {
            PreparedStatement ps = this.c.prepareStatement(upd, TYPE_SCROLL_INSENSITIVE, CONCUR_UPDATABLE);
            ps.setString(1, value);
            ps.setString(2, cod);
            ps.executeUpdate();
            ps.close();
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

}
