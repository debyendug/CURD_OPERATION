
package com.usermanage.bean;

import org.apache.catalina.User;


public class user {

    public static void insertUser(User newUser) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    private  int id;
    private String name;
    private String email;
    private int phone;

    
    //construcctor left only id; cz id is n autogenerateted 
    public user(String name, String email, int phone) {
        super();
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    
    //constructor for all 
    public user(int id, String name, String email, int phone) {
        super();
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    public user(int id, String name, String email) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
    //Getter and Setter methord
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }
    
    
}
