/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vunt.order;

/**
 *
 * @author ADMIN
 */
public class OrderCreateError {
     private String nameOrderErrorl;
    private String addressError;
    private String quantityNotEnoughError;

    public OrderCreateError() {
    }

    /**
     * @return the nameOrderErrorl
     */
    public String getNameOrderErrorl() {
        return nameOrderErrorl;
    }

    /**
     * @param nameOrderErrorl the nameOrderErrorl to set
     */
    public void setNameOrderErrorl(String nameOrderErrorl) {
        this.nameOrderErrorl = nameOrderErrorl;
    }

    /**
     * @return the addressError
     */
    public String getAddressError() {
        return addressError;
    }

    /**
     * @param addressError the addressError to set
     */
    public void setAddressError(String addressError) {
        this.addressError = addressError;
    }

    /**
     * @return the quantityNotEnoughError
     */
    public String getQuantityNotEnoughError() {
        return quantityNotEnoughError;
    }

    /**
     * @param quantityNotEnoughError the quantityNotEnoughError to set
     */
    public void setQuantityNotEnoughError(String quantityNotEnoughError) {
        this.quantityNotEnoughError = quantityNotEnoughError;
    }
    
    
}
