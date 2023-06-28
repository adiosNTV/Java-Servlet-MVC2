/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vunt.detail;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.naming.NamingException;
import vunt.cart.CartObj;
import vunt.order.OrderDTO;
import vunt.product.ProductDTO;
import vunt.utils.DBHelper;

/**
 *
 * @author ADMIN
 */
public class DetailDAO {

    public DetailDAO() {
    }

    private List<DetailDTO> detailList;

    public List<DetailDTO> getDetailList() {
        return detailList;
    }
    
    
    
    public boolean addDetail(DetailDTO dto)
            throws SQLException, NamingException {
        boolean result = false;
        Connection con = null;
        PreparedStatement stm = null;

        try {
            //1. connect to database
            con = DBHelper.getConnection();
            //2.write sql string
            if (con != null) {
                String sql = "Insert into [Detail]"
                        + "(order_id , sku , quantity, price, total)"
                        + "values(?, ?, ?, ?, ?)";
                //3.create statement
                stm = con.prepareStatement(sql);
                stm.setString(1, dto.getOrder_id());
                stm.setInt(2, dto.getSku());
                stm.setInt(3, dto.getQuantity());
                stm.setFloat(4, dto.getPrice());
                stm.setFloat(5, dto.getTotal());
                //4.handle result
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

    public boolean searchDetailByOrderID(String orderid)
            throws SQLException, NamingException {
        boolean result = false;
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            con = DBHelper.getConnection();
            if (con != null) {
                String sql = "Select id, sku , quantity , price, total "
                        + "FROM Detail "
                        + "where order_id = ?";
                stm = con.prepareStatement(sql);
                stm.setString(1, orderid);

                rs = stm.executeQuery();

                while (rs.next()) {
                    int id = rs.getInt("id");
                    int sku = rs.getInt("sku");
                    int quantity = rs.getInt("quantity");
                    float price = rs.getFloat("price");
                    float total = rs.getFloat("total");
                    DetailDTO dto = new DetailDTO(id, orderid, sku, quantity, price, total);
                    if(this.detailList==null){
                        detailList = new ArrayList<>();
                    }
                    detailList.add(dto);
                }
            }
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
