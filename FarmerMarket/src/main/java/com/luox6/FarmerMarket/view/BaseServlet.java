package com.luox6.FarmerMarket.view;

import com.luox6.FarmerMarket.DB;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

/**
 * BaseServlet extended by others
 */
public class BaseServlet extends HttpServlet {
    protected DB db;

    @Override
    public void init() throws ServletException {
        db = (DB) getServletContext().getAttribute("database");
    }

}
