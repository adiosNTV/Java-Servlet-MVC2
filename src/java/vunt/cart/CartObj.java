/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vunt.cart;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import javax.naming.NamingException;
import vunt.detail.DetailDAO;
import vunt.detail.DetailDTO;
import vunt.product.ProductDAO;
import vunt.product.ProductDTO;

/**
 *
 * @author ADMIN
 */
public class CartObj {

    private Map<ProductDTO, Integer> items;

    public Map<ProductDTO, Integer> getItems() {
        return items;
    }

    public boolean addItemToCart(int sku, int quantity, CartItemsQuantityError error)
            throws SQLException, NamingException {
        boolean result = false;
        //1.check quantity > 0
        if (quantity <= 0) {
            return result;
        }
        //2.check existed items
        if (this.items == null) {
            this.items = new HashMap<>();
        }
        //3.call modal
        ProductDAO dao = new ProductDAO();
        ProductDTO dto = dao.searchItemBySku(sku);
        if (dto == null) {
            return result;
        }
        
        Integer checkQuantity = 0;
        //3.check existed item
        if (this.items.containsKey(dto)) {
            Integer currentQuantity = this.items.get(dto);
            checkQuantity = (Integer) quantity + currentQuantity;
            //check if add more item will make store not item to sell
            if(checkQuantity > dto.getQuantity()){
                error.setQuantityItemsError("Product "+dto.getName() + " is only have " + dto.getQuantity() + "available"
                        + " .You already have "+ currentQuantity + "in your cart!!!" );
                return result;
            }
            quantity = checkQuantity;
        }//end item has existed
        //4.update cart items
        this.items.put(dto, quantity);
        result = true;

        return result;
    }

    public boolean removeItemFromCart(int sku, Integer quantity)
            throws SQLException, NamingException {
        boolean result = false;
        //1. check quantity input
        if (quantity <= 0) {
            return result;
        }
        //2. Check existed items
        if (this.items == null) {
            return result;
        }

        ProductDAO dao = new ProductDAO();
        ProductDTO dto = dao.searchItemBySku(sku);
        //3. Check existed item
        if (dto == null) {
            return result;
        }
        if (!this.items.containsKey(dto)) {
            return result;
        }
        //4.Remove items
        int currentQuantity = this.items.get(dto);
        if (currentQuantity >= quantity) {
            quantity = currentQuantity - quantity;
        } else {
            return result;
        }
        if (quantity == 0) {
            this.items.remove(dto);
            // if there nothing left in Cart just remove it
            if (this.items.isEmpty()) {
                this.items = null;
            }

        } else {
            this.items.put(dto, quantity
            );
        }
        result = true;

        return result;
    }
    
    public boolean removeItemsFromCartAfterOrder(ProductDTO dto){
        boolean result = false;
        //1.check cart
        if (this.items == null) {
            return result;
        }
        if (this.items.containsKey(dto)) {
            this.items.remove(dto);
            // if there nothing left in Cart just remove it
            if (this.items.isEmpty()) {
                this.items = null;
            }
        }
        
        return true;
    }

    public boolean addItemsToDetai(String order_id)
            throws SQLException, NamingException {
        boolean result = false;
        DetailDAO dao = new DetailDAO();
        for (ProductDTO dto : this.items.keySet()) {
            float total = dto.getPrice() * this.items.get(dto);
            DetailDTO detailItem = new DetailDTO(0, order_id, dto.getSku(), dto.getQuantity(), dto.getPrice(), total);
            result = dao.addDetail(detailItem);
        }
        return result;
    }
}

