package com.luox6.FarmerMarket.ContextListener;

import com.luox6.FarmerMarket.Config;
import com.luox6.FarmerMarket.DB;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

public class DBContext implements ServletContextListener {
    private final DB db = new DB(Config.DB_ADDRESS, Config.DB_PORT, Config.DB_USER, Config.DB_PASSWORD, Config.DB_DATABASE);;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("Initializing Database Connection Pool...");
        sce.getServletContext().setAttribute("database", db);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        db.close();
    }
}
