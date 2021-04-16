package com.luox6.FarmerMarket;

import java.sql.*;

/**
 * ADT of a database
 */
public class DB {
    private String host;
    private String port;
    private String user;
    private String password;

    /**
     * Default constructor from host, port, user and password
     *
     * @param host     host of database
     * @param port     port of database
     * @param user     user of database
     * @param password password of database
     */
    public DB(String host, String port, String user, String password) {
        this.host = host;
        this.port = port;
        this.user = user;
        this.password = password;
    }

    /**
     * Check if database is connectable to db_name
     *
     * @param db_name name of database to connect
     * @return boolean if database is connectable
     */
    public boolean isConnectable(String db_name) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }

        String URL = "jdbc:mysql://" + host + ":" + port + "/" + db_name;
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            conn = DriverManager.getConnection(URL, user, password);
            stmt = conn.createStatement();
            stmt.close();
            conn.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Execute cmd as query in database
     *
     * @param cmd query command
     * @return ResultSet of the result of query
     */
    public ResultSet runQuery(String cmd) {
        String URL = "jdbc:mysql://" + host + ": " + port + "/farmer";
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            conn = DriverManager.getConnection(URL, user, password);
            stmt = conn.createStatement();
            rs = stmt.executeQuery(cmd);
            return rs;
        } catch (SQLException e) {
            return null;
        }
    }

    /**
     * Main program to test a database
     *
     * @param args arguments
     */
    public static void main(String[] args) {
        DB db = new DB("localhost", "3306", "root", "example");
        System.out.println(db.isConnectable("farmer"));
        ResultSet rset = db.runQuery("SELECT * FROM data WHERE Youtube<>''");
        try {
            int count = 0;
            while (rset.next()) {
                String city = rset.getString("city");
                System.out.println(city);
                count++;
            }
            System.out.println(count);
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
    }
}
