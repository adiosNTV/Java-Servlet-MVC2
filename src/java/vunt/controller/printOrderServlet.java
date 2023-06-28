/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vunt.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import vunt.cart.CartObj;
import vunt.detail.DetailDAO;
import vunt.detail.DetailDTO;
import vunt.order.OrderDAO;
import vunt.order.OrderDTO;
import vunt.product.ProductDAO;
import vunt.product.ProductDTO;
import vunt.receipt.ReceiptList;
import vunt.receipt.ReceiptObj;
import vunt.utils.MyAppConstants;

/**
 *
 * @author ADMIN
 */
@WebServlet(name = "printOrderServlet", urlPatterns = {"/printOrderServlet"})
public class printOrderServlet extends HttpServlet {

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
        String url = (String) siteMap.get(MyAppConstants.CheckOutFeatures.ORDER_PAGE);
        String orderId = request.getParameter("txtOrderId");
        try {
            
            HttpSession session = request.getSession(false);
            //check session
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
            ReceiptList receipt = new ReceiptList();
            
            boolean checkAddReceipt = receipt.addItemToReceipt(items);
            if (checkAddReceipt) {
                List<ReceiptObj> listReceipt = receipt.getListReceipt();
                session.setAttribute("RECEIPT", listReceipt);
                //clear cart
                for (ProductDTO item : new HashMap<>(items).keySet()) {
                    //remove item from cart
                    cart.removeItemsFromCartAfterOrder(item);
                }

            }
            //detroy cart
            session.setAttribute("CART", null);

            //4.call Modal
            //4.1
            OrderDAO dao = new OrderDAO();
//            DetailDAO detailDAO = new DetailDAO();
//            ProductDAO productDAO = new ProductDAO();
            //4.2
            OrderDTO dto = dao.searchOrderById(orderId);
//            detailDAO.searchDetailByOrderID(orderId);
//            List<DetailDTO> detailList = detailDAO.getDetailList();

            //4.3 send object to View
            request.setAttribute("ORDER", dto);
//            request.setAttribute("DETAIL_LIST", detailList);
            //end search value at least one character
        } catch (SQLException ex) {
            log("PrintOrderServlet _ SQL _ " + ex.getMessage());
        } catch (NamingException ex) {
            log("PrintOrderServlet _ Naming _ " + ex.getMessage());
        } finally {
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
