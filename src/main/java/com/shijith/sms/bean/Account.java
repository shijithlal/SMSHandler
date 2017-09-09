package com.shijith.sms.bean;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Account {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String auth_id;

    private String username;

    public Integer getId() {
        return id;
    }

    public String getAuth_id() {
        return auth_id;
    }

    public String getUsername() {
        return username;
    }



    public void setId(Integer id) {
        this.id = id;
    }

    public void setAuth_id(String auth_id) {
        this.auth_id = auth_id;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
