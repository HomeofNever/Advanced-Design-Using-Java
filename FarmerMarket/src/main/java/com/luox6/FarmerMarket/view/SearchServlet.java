package com.luox6.FarmerMarket.view;

import com.luox6.FarmerMarket.model.Farmer;

import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(name = "searchServlet", value = "/search")
public class SearchServlet extends BaseServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/html");

        RequestDispatcher dispatcher = request.getRequestDispatcher("_hidden/search.jsp");
        List<Farmer> ls = new LinkedList<>();
        String city = request.getParameter("city");
        String state = request.getParameter("state");
        String zipcode = request.getParameter("zipcode");
        String distance = request.getParameter("distance");
        city = city == null ? "" : city;
        state = state == null ? "" : state;
        zipcode = zipcode == null ? "" : zipcode;
        distance = distance == null ? "" : distance;
        request.setAttribute("city", city);
        request.setAttribute("state", state);
        request.setAttribute("zipcode", zipcode);
        request.setAttribute("distance", distance);

        if (!(zipcode.isEmpty() && city.isEmpty() && state.isEmpty() && distance.isEmpty())) {
            // Skip first visit
            try {
                if (zipcode.isEmpty() && (city.isEmpty() || state.isEmpty())) {
                    throw new Exception("Please enter full city&state or zipcode!");
                }

                int d = Integer.parseInt(distance);

                if (d < 0) {
                    throw new Exception("Distance should be at least 0!");
                }

                try (Connection c = db.getConnection()) {
                    PreparedStatement ps;
                    ResultSet rs;
                    /**
                     * Miles: https://gis.stackexchange.com/questions/31628/find-features-within-given-coordinates-and-distance-using-mysql
                     * X,Y: https://gis.stackexchange.com/questions/11626/does-y-mean-latitude-and-x-mean-longitude-in-every-gis-software/11628
                     */
                    if (!zipcode.isEmpty()) {
                        // ZipCode is unique
                        double lat;
                        double log;
                        ps = c.prepareStatement("SELECT latitude, longitude FROM zip_codes_states WHERE zip_code = ?");
                        ps.setString(1, zipcode);
                        rs = ps.executeQuery();
                        if (rs.next()) {
                            lat = rs.getDouble("latitude");
                            log = rs.getDouble("longitude");
                        } else {
                            throw new Exception("Please double check your zipcode: cannot locate!");
                        }
                        ps = c.prepareStatement("SELECT" +
                                "    *, ROUND(" +
                                "      3959 * acos (" +
                                "      cos ( radians( ? ) )" +
                                "      * cos( radians( y ) )" +
                                "      * cos( radians( x ) - radians( ? ) )" +
                                "      + sin ( radians( ? ) )" +
                                "      * sin( radians( y ) )" +
                                "    )" +
                                ", 3) AS distance " +
                                " FROM data " +
                                "HAVING distance <= ? " +
                                "ORDER BY distance");
                        ps.setDouble(1, lat);
                        ps.setDouble(2, log);
                        ps.setDouble(3, lat);
                        ps.setInt(4, d);
                    } else {
                        // City State combination may involve multiple location
                        // Use SQL procedure
                        ps = c.prepareStatement("call calc_distance(?, ?, ?)");
                        ps.setString(1, city);
                        ps.setString(2, state);
                        ps.setInt(3, d);
                    }
                    rs = ps.executeQuery();

                    while (rs.next()) {
                        Farmer farmer = new Farmer(rs);
                        ls.add(farmer);
                    }
                }
            } catch (NumberFormatException e) {
                request.setAttribute("error", "Unable to parse Distance!");
            } catch (Exception e) {
                e.printStackTrace();
                request.setAttribute("error", e.getMessage());
            }
        }

        request.setAttribute("ls", ls);
        dispatcher.forward(request, response);
    }

    public void destroy() {

    }
}