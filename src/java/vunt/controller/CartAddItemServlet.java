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
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import vunt.cart.CartItemsQuantityError;
import vunt.cart.CartObj;
import vunt.utils.MyAppConstants;

/**
 *
 * @author ADMIN
 */
@WebServlet(name = "CartAddItemServlet", urlPatterns = {"/CartAddItemServlet"})
public class CartAddItemServlet extends HttpServlet {

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
        String url = (String) siteMap.get(MyAppConstants.StoreFeatures.BOOK_STORE_ACTION);
        try{
            //1. Customer goes to cart place
            HttpSession session = request.getSession();
            CartItemsQuantityError error = new CartItemsQuantityError();
            //2. Customer takes his/her cart
            CartObj cart = (CartObj)session.getAttribute("CART");
            if(cart == null){
                cart = new CartObj();
            }//end cart has NOT existed
            //3.Customer drops item to cart
            String SkuString = request.getParameter("Sku");
            int Sku = Integer.parseInt(SkuString);
            int quantity = Integer.parseInt(request.getParameter("txtQuantity"));
            
            boolean result = cart.addItemToCart(Sku, quantity, error);// thay 1 bang 1 parameter
            if(result){
                session.setAttribute("CART", cart);
            }else{
                request.setAttribute("CART_QUANTITY_ERROR", error);
            }
            
            //4. Customer continuosly goes shopping           
        } catch (SQLException ex) {
            log("CartAddItemServlet _ SQL " + ex.getMessage());
        } catch (NamingException ex) {
            log("CartAddItemServlet _ Naming " + ex.getMessage());
        }finally{
            RequestDispatcher rd = request.getRequestDispatcher(url);
            rd.forward(request, response);
//              response.sendRedirect(url);
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
