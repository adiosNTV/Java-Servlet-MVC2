/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vunt.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
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
import vunt.order.OrderCreateError;
import vunt.order.OrderDAO;
import vunt.order.OrderDTO;
import vunt.product.ProductDAO;
import vunt.product.ProductDTO;
import vunt.utils.IDhelper;
import vunt.utils.MyAppConstants;

/**
 *
 * @author ADMIN
 */
@WebServlet(name = "CheckOutServlet", urlPatterns = {"/CheckOutServlet"})
public class CheckOutServlet extends HttpServlet {

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
        String url = (String) siteMap.get(MyAppConstants.CheckOutFeatures.ERROR_PAGE);
//        String url = MyAppConstants.CheckOutFeatures.ERROR_PAGE;
        String name = request.getParameter("txtNameOrder");
        String address = request.getParameter("txtAddress");
        OrderCreateError errors = new OrderCreateError();
        boolean foundError = false;
        try {
            //1.check validate name and address input
            if (name.trim().isEmpty()) {
                errors.setNameOrderErrorl("Name can't be empty !!!");
                foundError = true;
            }
            if (address.trim().isEmpty()) {
                errors.setAddressError("Address can't be empty !!!");
                foundError = true;
            }

            if (foundError) {
                //if found error, set error to attribute
                request.setAttribute("OrderError", errors);
            } else {
                //1. get cart
                HttpSession session = request.getSession(false);

                if (session == null) {
                    return;
                }
                //get items from cart
                CartObj cart = (CartObj) session.getAttribute("CART");
                if (cart == null) {
                    return;
                }
                Map<ProductDTO, Integer> items = cart.getItems();
                
                float total = 0;
                boolean error = false;
                if (items == null) {
                    return;
                }
                
                for (ProductDTO dto : items.keySet()) {
                    //check is there enough quantity to buy
                    if (dto.getQuantity() < items.get(dto)) {
                        errors.setQuantityNotEnoughError(dto.getName() + " only have " + dto.getQuantity() + " left");
                        error = true;
                        break;
                    } else {
                        //calculating total
                        total = total + dto.getPrice() * items.get(dto);
                    }
                }

                boolean result = false;
                String id = null;
                if (error) {
                    //if found error, set error to attribute
                    request.setAttribute("OrderError", errors);
                } else {
                    //generate id have 5 characters
                    id = IDhelper.generateID(); 
                    //taking the present time
                    Timestamp date = Timestamp.from(Instant.now());
                    //create order
                    OrderDTO dto = new OrderDTO(id, name, address, date, total);
                    //call modal
                    OrderDAO dao = new OrderDAO();
                    result = dao.addOrder(dto);
                    
                    boolean resultDetail= cart.addItemsToDetai( id);
                    if(resultDetail){
                        //if create order detail success, create detail
                        ProductDAO productDAO = new ProductDAO();
                        for(ProductDTO item : new HashMap<>(items).keySet()){
                            int quantity = item.getQuantity()-items.get(item);
                            productDAO.updateQuantity(quantity, item.getSku());
//                            cart.removeItemsFromCartAfterOrder(item);
                        }
                    }
                    if (result) {
//                        session.removeAttribute("CART");
                        //set attribute order to print receipt
                        request.setAttribute("ORDER", dto);
                        url = (String) siteMap.get(MyAppConstants.CheckOutFeatures.ORDER_ACTION);                       
                    }
                }
            }

        } catch (NamingException ex) {
            log("CheckOutServlet _ Naming _ " + ex.getMessage());
        } catch (SQLException ex) {
            log("CheckOutServlet _ SQL _ " + ex.getMessage());
        } finally {
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
