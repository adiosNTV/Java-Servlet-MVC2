/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vunt.account;

import java.io.Serializable;

/**
 *
 * @author natton
 */
public class AccountDTO implements Serializable{
    private String username;
    private String password;
    private String fullname;
    private boolean admin;

    public AccountDTO() {
    }

    public AccountDTO(String username, String password, String fullname, boolean admin) {
        this.username = username;
        this.password = password;
        this.fullname = fullname;
        this.admin = admin;
    }

    

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }
    /**
     * @return the admin
     */
    public boolean isAdmin() {
        return admin;
    }

    /**
     * @param admin the admin to set
     */
    public void setAdmin(boolean admin) {
        this.admin = admin;
    }
    
    
    
}
