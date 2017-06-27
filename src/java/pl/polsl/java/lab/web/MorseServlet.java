/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.polsl.java.lab.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import pl.polsl.java.lab.model.*;

/**
 * Handles the conversion between Morse Code and Normal Sentence.
 *
 * @author Lukasz Jamroz
 * @version 1.1
 */
public class MorseServlet extends HttpServlet {

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
            String tekst;
            out.println("<HTML><HEAD><TITLE>Result</TITLE></HEAD><BODY>");
            tekst = request.getParameter("inputText");
            String target = request.getParameter("converterRadio");
            MorseConverter model = (MorseConverter) this.getServletConfig().getServletContext().getAttribute("model");
            DatabaseController dbhandler = (DatabaseController) this.getServletConfig().getServletContext().getAttribute("dbhandler");

            if (model != null && dbhandler != null && target != null) {
                try {
                    if (tekst != null) {
                        if (!tekst.isEmpty()) {
                            String result = "";
                            switch (target) {
                                case "morse":
                                    result = model.toMorse(tekst);
                                    out.println("Conversion to Morse code result:<br> " + result);
                                    dbhandler.insert(result,tekst);
                                    break;
                                case "ascii":
                                    result = model.toNormal(tekst);
                                    out.println("Conversion from Morse code result:<br> " + result);
                                    dbhandler.insert(result,tekst);
                                    break;
                                default:
                                    out.println("ERROR - unknown conversion target");
                            }
                        } else {
                            out.println("no data to convert");
                        }
                    } else {
                        out.println("ERROR no data handlers");
                    }
                } catch (MorseConversionException ex) {
                    out.println("ERROR - model exception " + ex.getMessage());

                    //Getting cookies from header due to a .getCookies method issue
                    String oldHistory = "";
                    String cookiesFromHeaderStr = request.getHeader("cookie");
                    String[] cks;
                    if (cookiesFromHeaderStr != null) {
                        cookiesFromHeaderStr = cookiesFromHeaderStr.replace("\"", "");
                        cks = cookiesFromHeaderStr.split("=");
                        oldHistory = oldHistory.concat(cks[1]);
                    }
                    tekst = "Untranslatable " + tekst + " - " + ex.getMessage();
                    oldHistory = oldHistory.concat(tekst.concat("%"));
                    Cookie newCookie = new Cookie("cHistory", oldHistory);
                    newCookie.setMaxAge(24 * 3600);
                    newCookie.setPath("/");
                    response.addCookie(newCookie);
                }
                catch(SQLException sqlex){
                    out.println("<br><b>ERROR - database connection failure</b> " + sqlex.getMessage());
                }
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
        return "This Servlet provides conversion between standard sentence and Morse code.";
    }

}
