/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vunt.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Properties;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import vunt.account.AccountDAO;
import vunt.account.AccountDTO;
import vunt.utils.MyAppConstants;

/**
 *
 * @author ADMIN
 */
@WebServlet(name = "TriggerServlet", urlPatterns = {"/TriggerServlet"})
public class TriggerServlet extends HttpServlet {

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
        ServletContext context = request.getServletContext();
        Properties siteMap = (Properties) context.getAttribute("SITE_MAP");
        String url = (String) siteMap.get(MyAppConstants.TriggerFeatures.LOGIN_PAGE);
//        Str = MyAppConstants.TriggerFeatures.LOGIN_PAGE;
        try {
            //1. get cookies
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                //2. get last cookies
                Cookie lastCookie = cookies[cookies.length - 1];
                String username = lastCookie.getName();
                String password = lastCookie.getValue();
                //3.Call modal
                AccountDAO dao = new AccountDAO();
                AccountDTO result = dao.getAccountByUsernameAndPassword(username, password);
                
                
                if (result!=null) {
//                    url = MyAppConstants.TriggerFeatures.SEARCH_ACTION;
                    url = (String) siteMap.get(MyAppConstants.TriggerFeatures.SEARCH_ACTION);
                    HttpSession session = request.getSession(true);
                    session.setAttribute("USER", result);
                }//end user had been authenticated
            }//end cookies has existed

        } catch (SQLException ex) {
            log("TriggerServlet _ SQL _ " + ex.getMessage());
        } catch (NamingException ex){
            log("TriggerServlet _ Naming _ " + ex.getMessage());
        }finally {
//            response.sendRedirect(url);
            RequestDispatcher rd = request.getRequestDispatcher(url);
            rd.forward(request, response);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
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
     * Handles the HTTP <code>POST</code> method.
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
        return "Short description";
    }// </editor-fold>

}
