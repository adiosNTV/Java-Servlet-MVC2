/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vunt.utils;

/**
 *
 * @author Kieu Trong Khanh
 */
public class MyAppConstants {
    public class LoginFeatures {
        public static final String INVALID_PAGE = "invalid";
        public static final String SEARCH_ACTION = "searchAction";
    }
    
    public class TriggerFeatures{
        public static final String LOGIN_PAGE = "loginPage";
        public static final String SEARCH_ACTION = "searchAction";
    }
    
    public class SearchAccountFeatures {
        public static final String SEARCH_PAGE = "searchPage";
        public static final String UPDATE_ACTION = "updateAccountAction";
        public static final String DELETE_ACTION = "deleteAccountAction";
        public static final String LOGOUT_ACTION = "logoutAction";
        public static final String ERROR_PAGE = "errorPage";
        public static final String SEARCH_ACTION = "searchAction";
        public static final String LOGIN_PAGE = "loginPage";    
    }
    
    public class UpdateAccountFeatures{
        public static final String SEARCH_ACTION = "searchAction";
    }
    public class DeleteAccountFeatures{
        public static final String SEARCH_ACTION = "searchAction";
        public static final String LOGOUT_ACTION = "logoutAction";
    }
    
    public class StoreFeatures{
        public static final String BOOK_STORE_PAGE = "storePage";
        public static final String BOOK_STORE_ACTION = "storeView";
        public static final String BOOK_STORE_ADD_ITEMS_TO_CART= "cartAddItem";    
    }
    
    public class CartFeatures{
        public static final String DELETE_ITEM_PAGE = "cartPage";
    }
    
    public class CreateAccountFeatures{
        public static final String ERROR_PAGE = "registerPage";
        public static final String SUCCESS_PAGE = "loginPage";       
    }
    
    public class CheckOutFeatures{
        public static final String ERROR_PAGE = "cartPage";
        public static final String ORDER_PAGE = "orderPage";
        public static final String ORDER_ACTION = "orderAction";
        
    }
    
}
