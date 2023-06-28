/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vunt.receipt;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import vunt.product.ProductDTO;

/**
 *
 * @author ADMIN
 */
public class ReceiptList {
    private List<ReceiptObj> listReceipt;

    public List<ReceiptObj> getListReceipt() {
        return listReceipt;
    }
    
    public boolean addItemToReceipt(Map<ProductDTO , Integer> listItem){
        boolean result = false;
        if(listItem==null){
            return result;
        }
        
        if(this.listReceipt==null){
            this.listReceipt = new ArrayList<>();
        }
        
        for(ProductDTO dto : listItem.keySet()){
            String name = dto.getName();
            int quantity = listItem.get(dto);
            float price = dto.getPrice();
            float total = quantity*price;
            ReceiptObj receipt = new ReceiptObj(name, quantity, price,total);
            this.listReceipt.add(receipt);
        }
        result = true;
        return result;
    }
}
