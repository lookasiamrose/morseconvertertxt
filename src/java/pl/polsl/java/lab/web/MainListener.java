/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.polsl.java.lab.web;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import pl.polsl.java.lab.model.*;

/**
 * Web application lifecycle listener, which provides instances for the model and history.
 *
 * @author Lukasz Jamroz
 * @version 1.1
 */
@WebListener
public class MainListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        MorseConverter model = new MorseConverter();
        sce.getServletContext().setAttribute("model", model);
        
        DatabaseController dbhandler = new DatabaseController(
                sce.getServletContext().getInitParameter("databaseDriver"),
                sce.getServletContext().getInitParameter("databasePath"),
                sce.getServletContext().getInitParameter("databaseUser"),
                sce.getServletContext().getInitParameter("databasePassword"));
        sce.getServletContext().setAttribute("dbhandler", dbhandler);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        DatabaseController dbhandler = (DatabaseController) sce.getServletContext().getAttribute("dbhandler");
        dbhandler.close();
        System.out.println("Shutdown");
    }
}
