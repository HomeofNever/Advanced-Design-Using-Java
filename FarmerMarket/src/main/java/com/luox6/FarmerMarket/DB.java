package com.luox6.FarmerMarket;

import java.sql.*;
import com.mchange.v2.c3p0.*;

/**
 * ADT of a database
 */
public class DB {
    private String host;
    private int port;
    private String user;
    private String password;
    private String database;
    private ComboPooledDataSource cpds = new ComboPooledDataSource();

    /**
     * Default constructor from host, port, user and password
     *
     * @param host     host of database
     * @param port     port of database
     * @param user     user of database
     * @param password password of database
     */
    public DB(String host, int port, String user, String password, String database) {
        this.host = host;
        this.port = port;
        this.user = user;
        this.password = password;
        this.database = database;

        try {
            cpds.setDriverClass( "com.mysql.cj.jdbc.Driver" ); //loads the jdbc driver
            cpds.setJdbcUrl("jdbc:mysql://" + host + ":" + port + "/" + database);
            cpds.setUser(user);
            cpds.setPassword(password);
            cpds.setMaxStatements( 180 );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Close data source
     */
    public void close() {
        cpds.close();
    }

    /** Get a connection from the pool
     * @return Connection to the database
     * @throws SQLException
     */
    public Connection getConnection() throws SQLException {
        return cpds.getConnection();
    }

    /**
     * Main program to test a database
     *
     * @param args arguments
     */
    public static void main(String[] args) {
        DB db = new DB("localhost", 3306, "root", "example", "farmer");
        try(Connection c = db.getConnection()) {
            // Unsafe, just for testing
            ResultSet rset = c.createStatement().executeQuery("SELECT * FROM data WHERE Youtube<>''");
            int count = 0;
            while (rset.next()) {
                String city = rset.getString("city");
                System.out.println(city);
                count++;
            }
            System.out.println(count);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
