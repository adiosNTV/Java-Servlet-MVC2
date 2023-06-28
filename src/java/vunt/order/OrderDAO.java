/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vunt.order;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import javax.naming.NamingException;
import vunt.utils.DBHelper;

/**
 *
 * @author ADMIN
 */
public class OrderDAO {

    public boolean addOrder(OrderDTO dto)
            throws SQLException, NamingException {
        boolean result = false;
        Connection con = null;
        PreparedStatement stm = null;

        try {
            con = DBHelper.getConnection();
            if (con != null) {
                String sql = "Insert into [Order]"
                        + "(id , name, address , date , total) "
                        + "values( ?, ?, ?, ?, ?)";
                stm = con.prepareStatement(sql);
                stm.setString(1, dto.getOrderId());
                stm.setString(2, dto.getName());
                stm.setString(3, dto.getAddress());
                stm.setTimestamp(4, dto.getDate());
                stm.setFloat(5, dto.getTotal());

                int effectiveRow = stm.executeUpdate();
                if (effectiveRow > 0) {
                    result = true;
                }
            }

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
    
    public OrderDTO searchOrderById(String order_id)
            throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        OrderDTO result = null;

        try {

            //1.Connect DB
            con = DBHelper.getConnection();
            if (con != null) {

                //2.Write SQL string
                String sql = "Select id , name , address , date , total "
                        + "From Order "
                        + "Where id = ? ";
                //3.Create Statement object
                stm = con.prepareStatement(sql);
                stm.setString(1, order_id);
                //4.Execute Statement to result
                rs = stm.executeQuery();

                //5.Process result
                if (rs.next()) {
                    String name = rs.getString("name");
                    String address = rs.getString("address");
                    Timestamp date = rs.getTimestamp("date");
                    float total = rs.getFloat("total");
                    result = new OrderDTO(order_id, name, address, date, total);
                }//end traverse rs to EOF
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
    
}
