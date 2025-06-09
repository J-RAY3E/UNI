package org.example.UserUtils;

import java.io.Serializable;

public class UserManager implements Serializable {
    private static final long serialVersionUID = 1L;
    private String username;
    private String password;
    private Boolean is_Connected;
    private String privileges;

    public  UserManager(){
        this.username = null ;
        this.password = null;
        this.is_Connected = false;
        this.privileges = null;
    }

    public  UserManager(String username,String password,Boolean is_Connected,String privileges){
        this.username = username ;
        this.password = password;
        this.is_Connected = is_Connected;
        this.privileges = privileges;
    }

    public  void setUsername(String username){
        this.username = username;
    }
    public  String getUsername(){
        return this.username;
    }
    public  void setPassword(String password){
        this.password = password;
    }

    public  String getPassword(){
        return this.password;
    }

    public void  setIs_Connected(Boolean is_Connected){
        this.is_Connected = is_Connected;
    }

    public Boolean  getIs_Connected(){
        return this.is_Connected;
    }

    public  void setPrivileges(String privileges){
        this.privileges = privileges;
    }

    public String getPrivileges(){
        return this.privileges;
    }


}
