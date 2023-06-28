/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vunt.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Map;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import vunt.cart.CartObj;
import vunt.product.ProductDTO;
import vunt.utils.MyAppConstants;

/**
 *
 * @author ADMIN
 */
@WebServlet(name = "CartDeleteItemServlet", urlPatterns = {"/CartDeleteItemServlet"})
public class CartDeleteItemServlet extends HttpServlet {

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
        String url = MyAppConstants.CartFeatures.DELETE_ITEM_PAGE;
        try {
            //1. Customer goes to cart place
            HttpSession session = request.getSession(false);

            if (session == null) {
                return;
            }
            //2.Customer takes his/her cart
            CartObj cart = (CartObj) session.getAttribute("CART");
            if (cart == null) {
                return;
            }
            //3. Customer get all items
            Map<ProductDTO, Integer> items = cart.getItems();
            if (items == null) {
                return;
            }
            //4.Remove items from cart
            String[] selectedItems = request.getParameterValues("chkItem");
            if (selectedItems == null) {
                return;
            }
            for (String item : selectedItems) {
                int sku = Integer.parseInt(item);
                cart.removeItemFromCart(sku, 1);
            }
            session.setAttribute("CART", cart);

        } catch (SQLException ex) {
            log("CartDeleteItemServlet _ SQL " + ex.getMessage());
        } catch (NamingException ex) {
            log("CartDeleteItemServlet _ Naming " + ex.getMessage());
        } finally {
            //5. refresh page by calling view cart feature again using url rewritting
            response.sendRedirect(url);
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
