/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vunt.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Properties;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import vunt.utils.MyAppConstants;

/**
 *
 * @author ADMIN
 */
@WebServlet(name = "AuthLogoutServlet", urlPatterns = {"/AuthLogoutServlet"})
public class AuthLogoutServlet extends HttpServlet {

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
        
//        ServletContext context = request.getServletContext();
//        Properties siteMap = (Properties) context.getAttribute("SITE_MAP");
//        String url = siteMap.getProperty(MyAppConstants.SearchAccountFeatures.LOGIN_PAGE);
        
        String url = MyAppConstants.SearchAccountFeatures.LOGIN_PAGE;
        try {
            HttpSession session = request.getSession(false);
            //check session is existed
            if (session != null) {
                //if session existed, detroy it
//                session.setAttribute("USER", null);
                session.invalidate();
            }
            //get cookie to delete
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
//                for (Cookie cookie : cookies) {
//                    //delete it by override it because cookie always take the last cookie
//                    Cookie newCookie = new Cookie(cookie.getName(), "");
//                    cookie.setMaxAge(0);
//                    response.addCookie(newCookie);
//                }
                Cookie lastCookie = cookies[cookies.length-1];
                lastCookie.setMaxAge(0);
                response.addCookie(lastCookie);
            }
        } finally {
              response.sendRedirect(url);
//            RequestDispatcher rd = request.getRequestDispatcher(url);
//            rd.forward(request, response);
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
    }
}
