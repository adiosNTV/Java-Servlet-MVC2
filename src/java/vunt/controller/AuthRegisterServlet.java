/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vunt.controller;

import java.io.IOException;
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
import vunt.account.AccountCreateError;
import vunt.account.AccountDAO;
import vunt.account.AccountDTO;
import vunt.utils.MyAppConstants;

/**
 *
 * @author ADMIN
 */
@WebServlet(name = "AuthRegisterServlet", urlPatterns = {"/AuthRegisterServlet"})
public class AuthRegisterServlet extends HttpServlet {

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
        String url = siteMap.getProperty(MyAppConstants.CreateAccountFeatures.ERROR_PAGE);
        String username = request.getParameter("txtUsername");
        String password = request.getParameter("txtPassword");
        String confirm = request.getParameter("txtConfirm");
        String fullname = request.getParameter("txtFullName");
        boolean foundError = false;
        boolean result = false;
        AccountCreateError errors = new AccountCreateError();
        
        
        try {
            //1.Check validation of all user's errors
            if(username.trim().length()<6 ||
                    username.trim().length() > 20){
                foundError = true;
                errors.setUsernameLengthError("User name is required input from 6 to 20 characters");
            }//end username is not obey required length
            if(password.trim().length()<6 ||
                    password.trim().length() > 30){
                foundError = true;
                errors.setPasswordLengthError("User password is required input from 6 to 30 characters");
            }//end password is not obey required length
            else if(!confirm.trim().equals(password.trim())){
                foundError = true;
                errors.setUsernameIsExisted("Confirm must matched password");
            }//end user is not obey required
            if(fullname.trim().length()<2 ||
                    password.trim().length() > 50){
                foundError = true;
                errors.setFullnameLengthError("Full name is required input from 2 to 50 characters");
            }//end password is not obey required length
            
            //2.
            //2.1 if user violated, system show error
            //2.2 otherwise call modal
            if(foundError){
                request.setAttribute("CREATE_ERROR", errors);
            }else{
                AccountDAO dao = new AccountDAO();
                AccountDTO dto = new AccountDTO(username, password, fullname, false);
                result = dao.createAccount(dto);
                
                if(result){
                    url = MyAppConstants.CreateAccountFeatures.SUCCESS_PAGE;
                }//end insert is success
            }
            
           
            
        }catch(SQLException ex){
            String msg = ex.getMessage();
            log("Create New Account_ SQL " + ex.getMessage());
            if(msg.contains("duplicate")){
                errors.setUsernameIsExisted(username + " is existed");
                request.setAttribute("CREATE_ERROR", errors);
            }
            
        }catch(NamingException ex){
            log("Create New Account_ Naming " + ex.getMessage());
        }finally{
            if(result){
                response.sendRedirect(url);
            }else{
            RequestDispatcher rd = request.getRequestDispatcher(url);
            rd.forward(request, response);
            }
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
