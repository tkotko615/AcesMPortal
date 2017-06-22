package com.acesconn.acesmportal;

import android.annotation.SuppressLint;
import android.os.StrictMode;
import android.util.Log;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionClass {
    /*BI主機
    String ip = "192.168.0.58";
    String classs = "net.sourceforge.jtds.jdbc.Driver";
    String db = "stage";
    String un = "sa";
    String password = "sql#DSC";
*/

    //外網
    String ip = "www.acesconn.com";
    String classs = "net.sourceforge.jtds.jdbc.Driver";
    String db = "acesmis";
    String un = "sa";
    String password = "aces1234";


    @SuppressLint("NewApi")
    public Connection CONN() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection conn = null;
        String ConnURL = null;
        try {

            Class.forName(classs);
            ConnURL = "jdbc:jtds:sqlserver://" + ip + ";"
                    + "databaseName=" + db + ";user=" + un + ";password="
                    + password + ";";
            conn = DriverManager.getConnection(ConnURL);
        } catch (SQLException se) {
            Log.e("ERRO", se.getMessage());
        } catch (ClassNotFoundException e) {
            Log.e("ERRO", e.getMessage());
        } catch (Exception e) {
            Log.e("ERRO", e.getMessage());
        }
        return conn;
    }
}
