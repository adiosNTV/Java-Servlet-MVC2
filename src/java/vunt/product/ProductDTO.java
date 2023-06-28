/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vunt.product;

/**
 *
 * @author ADMIN
 */
public class ProductDTO {
    private int sku ;
    private String name;
    private String description;
    private float price;
    private int quantity;
    private boolean status;

    public ProductDTO() {
    }

    public ProductDTO(int sku, String name, String description, float price, int quantity, boolean status) {
        this.sku = sku;
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.status = status;
    }

    /**
     * @return the sku
     */
    public int getSku() {
        return sku;
    }

    /**
     * @param sku the sku to set
     */
    public void setSku(int sku) {
        this.sku = sku;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the price
     */
    public float getPrice() {
        return price;
    }

    /**
     * @param price the price to set
     */
    public void setPrice(float price) {
        this.price = price;
    }

    /**
     * @return the quantity
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * @param quantity the quantity to set
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * @return the status
     */
    public boolean isStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(boolean status) {
        this.status = status;
    }
    
    @Override
    public boolean equals(Object o) {
        ProductDTO dto = (ProductDTO) o;
//        this.sku = Sku;
//        this.name = name;
//        this.description = description;
//        this.price = price;
//        this.quantity = quantity;
//        this.status = status;
        if(this.sku != dto.sku){
            return false;
        }
        if(!this.name.equals(dto.name)){
            return false;
        }
        if(!this.description.equals(dto.description)){
            return false;
        }
        if(this.price != dto.price){
            return false;
        }
        if(this.quantity != dto.quantity){
            return false;
        }
        if(this.status!=dto.status){
            return false;
        }
        
        return true; //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int hashCode() {
        int result = 12;
        result=result*sku;
        return result; //To change body of generated methods, choose Tools | Templates.
    }
}
