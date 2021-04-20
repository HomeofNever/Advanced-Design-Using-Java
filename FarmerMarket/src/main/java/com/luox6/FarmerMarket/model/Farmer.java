package com.luox6.FarmerMarket.model;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The farmer class to store the data of farmers
 */
public class Farmer {

    // private member variable
    private String marketName;
    private String website;
    private String street;
    private String city;
    private String county;
    private String state;
    private String zip;
    private String x;
    private String y;
    private String fmid;

    private double rating = 0;

    /**
     * The farmer constructor
     * @param r the certain result set
     * @throws SQLException when sql goes wrong
     */
    public Farmer(ResultSet r) throws SQLException {
        this.marketName = r.getString("MarketName");
        this.website = r.getString("Website");
        this.street = r.getString("street");
        this.city = r.getString("city");
        this.county = r.getString("County");
        this.state = r.getString("State");
        this.zip = r.getString("zip");
        this.x = r.getString("x");
        this.y = r.getString("y");
        this.fmid = r.getString("FMID");
    }

    public String getStreet() {
        return street;
    }

    public String getMarketName() {
        return marketName;
    }

    public String getState(){
        return state;
    }

    public String getZip(){
        return zip;
    }

    public String getWebsite(){
        return website;
    }

    public String getCity(){
        return city;
    }

    public String getFMID() {
        return fmid;
    }

    public double getRating() {
        return rating;
    }

    public String getX() {
        return x;
    }

    public String getY() {
        return y;
    }

    /** allow external rate setting **/
    public void setRating(double rating) {
        this.rating = rating;
    }
}