/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rc.so.atlante;

import static rc.so.util.Constant.test;
import static rc.so.util.Engine.insertTR;
import static java.lang.Class.forName;
import static java.lang.Thread.currentThread;
import java.sql.Connection;
import static java.sql.DriverManager.getConnection;
import java.sql.ResultSet;
import static java.sql.ResultSet.CONCUR_UPDATABLE;
import static java.sql.ResultSet.TYPE_SCROLL_INSENSITIVE;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;
import static rc.so.util.Constant.rb;

/**
 *
 * @author rcosco
 */
public class Db_ATL {

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
     * Constructor
     */
    public Db_ATL() {

        String drivername = rb.getString("db.driver");
        String typedb = rb.getString("db.tipo");
//        String drivername =  "org.mariadb.jdbc.Driver";
//        String typedb = "mariadb";

        String user = rb.getString("us.db");
        String pwd = rb.getString("us.pwd");

        String host = rb.getString("db.ip") + "/macatl";
        if (test) {
            host = rb.getString("db.ip") + "/macatl_test";
        }

        try {
            forName(drivername).newInstance();
            Properties p = new Properties();
            p.put("user", user);
            p.put("password", pwd);
            p.put("useUnicode", "true");
            p.put("characterEncoding", "UTF-8");
            p.put("useSSL", "false");
            this.c = getConnection("jdbc:" + typedb + ":" + host, p);
        } catch (Exception ex) {
            insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
        }
    }

    /**
     * close db
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
     * @param data
     * @param filiale
     * @return
     */
    public List<Atl_dati_clienti> atl_c1(String data, String filiale) {
        List<Atl_dati_clienti> out = new ArrayList<>();
        String sql2 = "SELECT * FROM dati_clienti WHERE clientcode IN (SELECT contoreg FROM details_dati_fatture "
                + "WHERE cod IN (SELECT cod FROM dati_fatture WHERE dateoper like '" + data
                + "%' AND cod LIKE '" + filiale + "%')) AND clientcode NOT IN (SELECT DISTINCT(contoreg) from details_dati_fatture WHERE category = 'TUR' "
                + "AND cod like'" + filiale + "%') GROUP BY clientcode;";
    
        try (Statement stmt = this.c.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
             ResultSet rs1 = stmt.executeQuery(sql2)) {
    
            while (rs1.next()) {
                Atl_dati_clienti df1 = new Atl_dati_clienti(
                        rs1.getString(1), rs1.getString(2), rs1.getString(3),
                        rs1.getString(4), rs1.getString(5), rs1.getString(6),
                        rs1.getString(7), rs1.getString(8), rs1.getString(9),
                        rs1.getString(10), rs1.getString(11)
                );
                out.add(df1);
            }
    
        } catch (SQLException ex) {
            insertTR("E", "System", Thread.currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
        }
        return out;
    }
    

    /**
     *
     * @param data
     * @param filiale
     * @return
     */
   public List<Atl_dati_fatture> atl_f1_P2(String data, String filiale) {
    List<Atl_dati_fatture> out = new ArrayList<>();
    List<Atl_details_dati_fatture> df = new ArrayList<>();
    List<Atl_detailsiva_dati_fatture> di = new ArrayList<>();

    String sql3 = "SELECT * FROM details_dati_fatture WHERE cod IN "
            + "(SELECT cod FROM dati_fatture WHERE datereg like '" + data + "%' AND tipomov='C' AND cod LIKE '" + filiale + "%') ORDER by cod,numriga";

    String sql1 = "SELECT * FROM dati_fatture WHERE datereg like '" + data + "%' AND tipomov='C' AND cod LIKE '" + filiale + "%' ORDER BY datereg";

    try (Statement stmt3 = this.c.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
         ResultSet rs3 = stmt3.executeQuery(sql3);
         Statement stmt1 = this.c.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
         ResultSet rs1 = stmt1.executeQuery(sql1)) {

        while (rs3.next()) {
            df.add(new Atl_details_dati_fatture(rs3.getString(1), rs3.getString(2), rs3.getString(3), rs3.getString(4),
                    rs3.getString(5), rs3.getString(6), rs3.getString(7), rs3.getString(8), rs3.getString(9),
                    rs3.getString(10), rs3.getString(11), rs3.getString(12), rs3.getString(13)));
        }

        while (rs1.next()) {
            String cod = rs1.getString(1);

            Atl_dati_fatture df1 = new Atl_dati_fatture(rs1.getString(1),
                    rs1.getString(2), rs1.getString(3), rs1.getString(4),
                    rs1.getString(5), rs1.getString(6),
                    rs1.getString(7), rs1.getString(8),
                    rs1.getString(10), rs1.getString(9),
                    df.stream().filter(d1 -> d1.getCod().equals(cod)).collect(Collectors.toList()),
                    di.stream().filter(d1 -> d1.getCod().equals(cod)).collect(Collectors.toList())
            );
            out.add(df1);
        }

    } catch (SQLException ex) {
        insertTR("E", "System", Thread.currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
    }

    return out;
}
    /**
     *
     * @param data
     * @param filiale
     * @return
     */
    public List<Atl_dati_fatture> atl_f1_P4(String data, String filiale) {
        List<Atl_dati_fatture> out = new ArrayList<>();
        List<Atl_details_dati_fatture> df = new ArrayList<>();
    
        String sql3 = "SELECT * FROM details_dati_fatture WHERE cod IN "
                + "(SELECT cod FROM dati_fatture WHERE datereg like '" + data + "%' "
                + "AND tipomov='G' AND cod LIKE '" + filiale + "%') ORDER by cod,numriga";
    
        String sql1 = "SELECT * FROM dati_fatture WHERE datereg like '" + data + "%' AND tipomov='G' AND cod LIKE '" + filiale + "%' ORDER BY datereg";
    
        try (Statement stmt3 = this.c.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
             ResultSet rs3 = stmt3.executeQuery(sql3);
             Statement stmt1 = this.c.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
             ResultSet rs1 = stmt1.executeQuery(sql1)) {
    
            while (rs3.next()) {
                df.add(new Atl_details_dati_fatture(rs3.getString(1), rs3.getString(2),
                        rs3.getString(3), rs3.getString(4), rs3.getString(5), rs3.getString(6),
                        rs3.getString(7), rs3.getString(8), rs3.getString(9), rs3.getString(10),
                        rs3.getString(11), rs3.getString(12), rs3.getString(13)));
            }
    
            while (rs1.next()) {
                String cod = rs1.getString(1);
    
                Atl_dati_fatture df1 = new Atl_dati_fatture(rs1.getString(1), rs1.getString(2),
                        rs1.getString(3), rs1.getString(4), rs1.getString(5), rs1.getString(6),
                        rs1.getString(7), rs1.getString(8), rs1.getString(10), rs1.getString(9),
                        df.stream().filter(d1 -> d1.getCod().equals(cod)).collect(Collectors.toList()),
                        new ArrayList<>()
                );
                out.add(df1);
            }
    
        } catch (SQLException ex) {
            insertTR("E", "System", Thread.currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
        }
    
        return out;
    }

    /**
     *
     * @param data
     * @param filiale
     * @return
     */
    public List<Atl_dati_fatture> atl_f1_P6(String data, String filiale) {
        List<Atl_dati_fatture> out = new ArrayList<>();
        List<Atl_details_dati_fatture> df = new ArrayList<>();
        List<Atl_detailsiva_dati_fatture> di = new ArrayList<>();
    
        String sql2 = "SELECT * FROM details_dativa_fatture WHERE cod IN "
                + "(SELECT cod FROM dati_fatture WHERE datereg like '" + data
                + "%' AND cod LIKE '" + filiale + "%' AND (tipomov='F' OR tipomov='N')) ORDER by cod,nrigaiva";
    
        String sql3 = "SELECT * FROM details_dati_fatture WHERE cod IN "
                + "(SELECT cod FROM dati_fatture WHERE datereg like '" + data
                + "%' AND cod LIKE '" + filiale + "%' AND (tipomov='F' OR tipomov='N'))"
                + " AND tipoconto <> 'I' ORDER by cod,numriga";
    
        String sql1 = "SELECT * FROM dati_fatture WHERE datereg like '" + data
                + "%' AND (tipomov='F' OR tipomov='N') AND cod LIKE '" + filiale + "%' ORDER BY datereg";
    
        try (Statement stmt2 = this.c.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
             ResultSet rs2 = stmt2.executeQuery(sql2);
             Statement stmt3 = this.c.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
             ResultSet rs3 = stmt3.executeQuery(sql3);
             Statement stmt1 = this.c.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
             ResultSet rs1 = stmt1.executeQuery(sql1)) {
    
            while (rs2.next()) {
                di.add(new Atl_detailsiva_dati_fatture(rs2.getString(1), rs2.getString(2), rs2.getString(3),
                        rs2.getString(4), rs2.getString(5), rs2.getString(6), rs2.getString(7)));
            }
    
            while (rs3.next()) {
                df.add(new Atl_details_dati_fatture(rs3.getString(1), rs3.getString(2), rs3.getString(3), rs3.getString(4),
                        rs3.getString(5), rs3.getString(6), rs3.getString(7), rs3.getString(8), rs3.getString(9),
                        rs3.getString(10), rs3.getString(11), rs3.getString(12), rs3.getString(13)));
            }
    
            while (rs1.next()) {
                String cod = rs1.getString(1);
                Atl_dati_fatture df1 = new Atl_dati_fatture(rs1.getString(1), rs1.getString(2), rs1.getString(3),
                        rs1.getString(4), rs1.getString(5), rs1.getString(6),
                        rs1.getString(7), rs1.getString(8),
                        rs1.getString(10), rs1.getString(9),
                        df.stream().filter(d1 -> d1.getCod().equals(cod)).collect(Collectors.toList()),
                        di.stream().filter(d1 -> d1.getCod().equals(cod)).collect(Collectors.toList())
                );
                out.add(df1);
            }
    
        } catch (SQLException ex) {
            insertTR("E", "System", Thread.currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
        }
    
        return out;
    }

}
