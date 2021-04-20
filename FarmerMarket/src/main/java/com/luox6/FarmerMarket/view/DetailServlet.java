package com.luox6.FarmerMarket.view;

import com.luox6.FarmerMarket.DB;
import com.luox6.FarmerMarket.model.Comment;
import com.luox6.FarmerMarket.model.Farmer;

import java.io.*;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

/**
 * Detail page Controller
 */
@WebServlet(name = "detailServlet", value = "/detail")
public class DetailServlet extends BaseServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/html");

        RequestDispatcher dispatcher;
        String fmid = request.getParameter("fmid");
        Farmer f = getFarmerByFMID(fmid);

        if (f == null) {
            dispatcher = request.getRequestDispatcher("_hidden/404.jsp");
        } else {
            request.setAttribute("comments", getCommentsByFMID(fmid));
            request.setAttribute("farmer", f);

            dispatcher = request.getRequestDispatcher("_hidden/detail.jsp");
        }

        dispatcher.forward(request, response);
    }

    public void destroy() {

    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String fmid = request.getParameter("fmid");
        Farmer f = getFarmerByFMID(fmid);

        if (f == null) {
            response.sendError(404, "No Found");
        } else {
            try {
                int rating = Integer.parseInt(request.getParameter("rating"));
                if (rating < 1 || rating > 5) {
                    throw new Exception("Rating should between 1-5!");
                }
                String name = request.getParameter("name");
                if (name.length() > 32 || name.length() < 1) {
                    throw new Exception("Name should have 1-32 characters!");
                }
                String description = request.getParameter("description");
                try (Connection c = db.getConnection()) {
                    PreparedStatement ps = c.prepareStatement("INSERT INTO `comments`(`name`, `FMID`, `rating`, `description`) VALUES (?, ?, ?, ?)");
                    ps.setString(1, name);
                    ps.setString(2, f.getFMID());
                    ps.setInt(3, rating);
                    ps.setString(4, description);

                    ps.executeUpdate();

                    response.getOutputStream().print("success");
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
                response.getOutputStream().print("Unable to parse rating number!");
            } catch (SQLIntegrityConstraintViolationException e) {
                e.printStackTrace();
                response.getOutputStream().print("You have already left your comment!");
            } catch (Exception e) {
                e.printStackTrace();
                response.getOutputStream().print(e.getMessage());
            }
        }
    }

    private Farmer getFarmerByFMID(String FMID) {
        Farmer f = null;
        try (Connection c = db.getConnection()) {
            PreparedStatement ps = c.prepareStatement("WITH\n" +
                    "  rate AS (\n" +
                    "      SELECT FMID, ROUND(AVG(rating), 3) as avg_rating FROM `comments` GROUP BY FMID\n" +
                    "  )\n" +
                    "SELECT * FROM `data` LEFT JOIN rate ON data.FMID = rate.FMID WHERE data.FMID = ?");

            ps.setString(1, FMID);
            ResultSet res = ps.executeQuery();

            if (res.next()) {
                f = new Farmer(res);
                f.setRating(res.getDouble("avg_rating"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return f;
    }

    private List<Comment> getCommentsByFMID(String FMID) {
        List<Comment> ls = new LinkedList<>();
        try (Connection c = db.getConnection()) {
            PreparedStatement ps = c.prepareStatement("SELECT * FROM `comments` WHERE FMID = ?");

            ps.setString(1, FMID);
            ResultSet res = ps.executeQuery();

            while (res.next()) {
                ls.add(new Comment(res));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return ls;
    }

}