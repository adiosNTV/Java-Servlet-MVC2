/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vunt.product;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;
import vunt.utils.DBHelper;

/**
 *
 * @author ADMIN
 */
public class ProductDAO {

    private List<ProductDTO> productList;

    public List<ProductDTO> getProductList() {
        return productList;
    }

    public void loadProduct()
            throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {

            //1.Connect DB
            con = DBHelper.getConnection();
            if (con != null) {

                //2.Write SQL string
                String sql = "Select Sku , name , description , price , quantity , status "
                        + "From Product "
                        + "Where status = 1 And quantity > 0";
                //3.Create Statement object
                stm = con.prepareStatement(sql);
                //4.Execute Statement to result
                rs = stm.executeQuery();

                //5.Process result
                while (rs.next()) {
                    int sku = rs.getInt("Sku");
                    String name = rs.getString("name");
                    String description = rs.getString("description");
                    float price = rs.getFloat("price");
                    int quantity = rs.getInt("quantity");
                    boolean status = rs.getBoolean("status");

                    ProductDTO dto = new ProductDTO(
                            sku, name, description, price, quantity, status);
                    if (this.productList == null) {
                        this.productList = new ArrayList<>();
                    }//end account list has NOT existed
                    this.productList.add(dto);
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
    }

    public ProductDTO searchItemBySku(int sku)
            throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        ProductDTO result = null;

        try {

            //1.Connect DB
            con = DBHelper.getConnection();
            if (con != null) {

                //2.Write SQL string
                String sql = "Select Sku , name , description , price , quantity , status "
                        + "From Product "
                        + "Where Sku = ? ";
                //3.Create Statement object
                stm = con.prepareStatement(sql);
                stm.setInt(1, sku);
                //4.Execute Statement to result
                rs = stm.executeQuery();

                //5.Process result
                if (rs.next()) {
                    int Sku = rs.getInt("Sku");
                    String name = rs.getString("name");
                    String description = rs.getString("description");
                    float price = rs.getFloat("price");
                    int quantity = rs.getInt("quantity");
                    boolean status = rs.getBoolean("status");
                    ProductDTO dto = new ProductDTO(
                            Sku, name, description, price, quantity, status);
                    result = dto;
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
    
    public boolean updateQuantity(int quantity, int sku)
        throws SQLException, NamingException{
        Connection con = null;
        PreparedStatement stm = null;
        boolean result = false;
        try{
            //1. Connect DB
            con = DBHelper.getConnection();
            
            if(con != null){
                //2. Write SQL commend
                if(quantity > 0 ){
                    String sql = "Update Product "
                            + "Set quantity = ? "
                            + "Where sku = ?";
                    //3. Create Statement Object
                    stm = con.prepareStatement(sql);
                    stm.setInt(1, quantity);
                    stm.setInt(2, sku);
                }
                if(quantity <= 0){
                    String sql = "Update Product "
                            + "Set status = ? "
                            + "Where sku = ?";
                    //3. Create Statement Object
                    stm = con.prepareStatement(sql);
                    stm.setBoolean(1, false);
                    stm.setInt(2, sku);
                }
               
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
}
