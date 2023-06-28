/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vunt.account;

import java.io.Serializable;
import vunt.utils.DBHelper;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;

/**
 *
 * @author natton
 */
public class AccountDAO implements Serializable{
    
    public AccountDTO getAccountByUsernameAndPassword(String username, String password) 
            throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        AccountDTO result = null;
        
        try {

            //1.Connect DB
            con = DBHelper.getConnection();
            if (con != null) {

                //2.Write SQL string
                String sql = "Select fullname, isAdmin "
                        + "From Account "
                        + "Where username = ? And password = ?";

                //3.Create Statement object
                stm = con.prepareStatement(sql);
                stm.setString(1, username);
                stm.setString(2, password);

                //4.Execute Statement to result
                rs = stm.executeQuery();
                
                //5.Process result
                if(rs.next()){
                    String fullname = rs.getString("fullname");
                    Boolean role = rs.getBoolean("isAdmin");       
                    result = new AccountDTO(username, password, fullname, role);
                    
                }
            }//end connection is existed
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return result;
    }
    
    public List<AccountDTO> searchAccountByFullName(String searchValue) 
            throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        List<AccountDTO> listAccount = new ArrayList<AccountDTO>();
        try {
            con = DBHelper.getConnection();
            if (con != null) {
                String sql = "SELECT username, password, fullname, isAdmin "
                        + "FROM Account "
                        + "WHERE fullname LIKE ?";
                stm = con.prepareStatement(sql);
                stm.setString(1, "%" + searchValue + "%");
                rs = stm.executeQuery();
                while (rs.next()) {
                    String username = rs.getString("username");
                    String password = rs.getString("password");
                    String fullname = rs.getString("fullname");
                    boolean isAdmin = rs.getBoolean("isAdmin");
                    AccountDTO dto = new AccountDTO(username, password, fullname, isAdmin);
                    listAccount.add(dto);
                }//end while rs not null
            }//end if con is not null
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return listAccount;
    }
    
    public boolean deleteAccount (String username)
        throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        boolean result = false;       
        try {
            //1.Connect DB
            con = DBHelper.getConnection();
            if (con != null) {
                //2.Write SQL string
                String sql = "Delete From Account "
                        + "Where username = ?";
                //3.Create Statement object
                stm = con.prepareStatement(sql);
                stm.setString(1, username);

                //4.Execute Statement to result
                int effectRows = stm.executeUpdate();
                //5.Process result
                if(effectRows > 0){
                    return true;
                }
            }//end connection is existed
        } finally {
           
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return false;
    }
    
    public boolean updateAccount(String username, String password, boolean isAdmin)
        throws SQLException, NamingException{
        Connection con = null;
        PreparedStatement stm = null;
        boolean result = false;
        System.out.println(isAdmin);
        try{
            //1. Connect DB
            con = DBHelper.getConnection();
            
            if(con != null){
                //2. Write SQL commend
                String sql = "Update Account "
                        + "Set password = ?, isAdmin = ? "
                        + "Where username = ?";
                //3. Create Statement Object
                stm = con.prepareStatement(sql);
                stm.setString(1, password);
                stm.setBoolean(2, isAdmin);
                stm.setString(3, username);
               
                //4. Execute Statement to get result
                int effectRows = stm.executeUpdate();
                //5. Process result
               if(effectRows > 0) {
                   result = true;
               }
            }//end connection is existed
            
        }finally{
            if(stm != null){
                stm.close();
            }
            if (con != null){
                con.close();
            }
        }
        return result;
    }
    
    
    public boolean createAccount(AccountDTO dto)
        throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        boolean result = false;
        
        try {

            //1.Connect DB
            con = DBHelper.getConnection();
            if (con != null) {

                //2.Write SQL string
                String sql = "Insert into Account("
                        + "username, password, fullname, isAdmin"
                        + ") Values("
                        + "?, ?, ?, ?"
                        + ")";

                //3.Create Statement object
                stm = con.prepareStatement(sql);
                stm.setString(1, dto.getUsername());
                stm.setString(2, dto.getPassword());
                stm.setString(3, dto.getFullname());
                stm.setBoolean(4, dto.isAdmin());

                //4.Execute Statement to result
                int effectRows = stm.executeUpdate();
                
                //5.Process result
                if(effectRows > 0){
                    result = true;
                    
                }
            }//end connection is existed
        } finally {
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return result;
    }
}
