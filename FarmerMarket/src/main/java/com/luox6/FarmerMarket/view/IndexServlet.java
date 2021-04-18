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

@WebServlet(name = "indexServlet", value = "/index")
public class IndexServlet extends BaseServlet {
    private int PAGE_LIMIT = 25;

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/html");
        int page = 1;
        try {
            page = Integer.parseInt(request.getParameter("page"));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (page < 1) {
                page = 1;
            }
        }

        RequestDispatcher dispatcher;
        List<Farmer> f = getFarmerByPage(page);

        if (f.size() < 1) {
            dispatcher = request.getRequestDispatcher("_hidden/404.jsp");
        } else {
            int totalCount = getFarmerCount();
            int currentIdx = page * PAGE_LIMIT;
            request.setAttribute("has_next", currentIdx < totalCount);
            request.setAttribute("has_prev", page > 1);
            request.setAttribute("current_page", page);
            request.setAttribute("list", f);

            dispatcher = request.getRequestDispatcher("_hidden/index.jsp");
        }

        dispatcher.forward(request, response);
    }

    public void destroy() {

    }

    private int getFarmerCount() {
        int i = 0;
        try (Connection c = db.getConnection()) {
            PreparedStatement ps = c.prepareStatement("SELECT COUNT(*) as count FROM `data`");

            ResultSet res = ps.executeQuery();

            res.next();
            i = res.getInt("count");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return i;
    }

    private List<Farmer> getFarmerByPage(int page) {
        List<Farmer> f = new LinkedList<>();
        try (Connection c = db.getConnection()) {
            PreparedStatement ps = c.prepareStatement("WITH\n" +
                    "  rate AS (\n" +
                    "      SELECT FMID, ROUND(AVG(rating), 3) as avg_rating FROM `comments` GROUP BY FMID\n" +
                    "  )\n" +
                    "SELECT * FROM `data` LEFT JOIN rate ON data.FMID = rate.FMID LIMIT ? OFFSET ?");

            ps.setInt(1, PAGE_LIMIT);
            ps.setInt(2, (page - 1) * PAGE_LIMIT);

            ResultSet res = ps.executeQuery();


            while (res.next()) {
                Farmer farmer = new Farmer(res);
                farmer.setRating(res.getDouble("avg_rating"));
                f.add(farmer);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return f;
    }
}