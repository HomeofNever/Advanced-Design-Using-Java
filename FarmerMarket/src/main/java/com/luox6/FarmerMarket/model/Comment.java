package com.luox6.FarmerMarket.model;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The comment class to store the data of comments of a specific farmer
 */
public class Comment {

    // private member variable
    private String FMID;
    private String name;
    private int rating;
    private String description;

    /**
     * The farmer constructor
     * @param r the certain result set
     * @throws SQLException when sql goes wrong
     */
    public Comment(ResultSet r) throws SQLException {
        FMID = r.getString("FMID");
        name = r.getString("name");
        rating = r.getInt("rating");
        description = r.getString("description");
    }

    public int getRating() {
        return rating;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getFMID() {
        return FMID;
    }
}