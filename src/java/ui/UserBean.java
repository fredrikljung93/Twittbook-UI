/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author jonas_000
 */
@ManagedBean(name = "UserBean")
@SessionScoped
public class UserBean implements Serializable {

    private int id;
    private String username;

    public UserBean() {
    }

    public UserBean(int id, String username) {
        this.id = id;
        this.username = username;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public static ArrayList<UserBean> getAllUsers() {
        String result = RestGetter.getStringFromURL("http://a.fredrikljung.com:8080/Twittbook/webresources/rest/allusers");
        Gson gson = new Gson();
        UserBean[] userarray = gson.fromJson(result, UserBean[].class);
        ArrayList<UserBean> users = new ArrayList();
        for(UserBean u:userarray){
            users.add(u);
        }
        return users;
    }

    public static UserBean getUser(int userid) {
        return new UserBean(1, "kalle");
    }

}
