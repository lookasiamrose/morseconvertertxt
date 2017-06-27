/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.polsl.java.lab.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Handles the conversion history and errors overview.
 *
 * @author Lukasz Jamroz
 * @version 1.1
 */
public class HistoryServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {

            String errorHistory = "";
            String cookiesFromHeaderStr = request.getHeader("cookie");
            String[] cks;
            if (cookiesFromHeaderStr != null) {
                cookiesFromHeaderStr = cookiesFromHeaderStr.replace("\"", "");
                cks = cookiesFromHeaderStr.split("=");
                errorHistory = errorHistory.concat(cks[1]);
            }
            String[] errorElements = errorHistory.split("%");

            out.println("<HTML><HEAD><TITLE>Result</TITLE></HEAD><BODY>");
            DatabaseController dbhandler = (DatabaseController) this.getServletConfig().getServletContext().getAttribute("dbhandler");
            try {
                if (dbhandler != null) {
                    ArrayList<String[]> history = dbhandler.selectAll();
                    out.println("<h3>History:</h3>");
                    if (history.size() > 0) {
                        out.println("<ol type=\"1\" reversed>\n");
                        for (int i = (history.size() - 1); i >= 0; i--) {
                            String[] elem = history.get(i);
                            out.print("<li>" + elem[1] + " -> <b>" + elem[0] + "</b></li>\n");
                        }
                        out.print("</ol><hr>");
                    } else {
                        out.print("empty <hr>");
                    }
                }
                out.println("<h3>Last Errors:</h3>");
                out.println("<ul>\n");
                for (String hisElement : errorElements) {
                    if (hisElement.length() > 0) {
                        out.print("<li>" + hisElement + "</li>\n");
                    }
                }
                out.print("</ul>");
                out.println("<br><form action=\"RemoveErrors\" method=\"POST\">"
                        + "    <button type=\"submit\" name=\"button\" value=\"removeErrors\">Clear Last Errors and History</button>\n</form>");
            } catch (SQLException sqlex) {
                out.println("<br><b>ERROR - database connection failure</b> " + sqlex.getMessage());
            }
            out.println("</BODY></HTML>");
            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method. Just passes the arguments to
     * processRequest method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method. Just passes the arguments to
     * processRequest method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Provides access to the conversion history and errors that accured.";
    }// </editor-fold>

}
