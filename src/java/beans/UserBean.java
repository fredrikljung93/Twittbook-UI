/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import com.google.gson.Gson;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import session.RestHelper;
import session.StoredUser;

/**
 * Bean for representing a User
 * @author jonas_000
 */
@ManagedBean(name = "UserBean")
@RequestScoped
public class UserBean implements Serializable {

    private int id;
    private String username;
    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

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
/**
 * Returns all registered users
 * @return all registered users
 */
    public static ArrayList<UserBean> getAllUsers() {
        String result = RestHelper.getStringFromURL("http://a.fredrikljung.com:8080/Twittbook/webresources/rest/allusers");
        Gson gson = new Gson();
        UserBean[] userarray = gson.fromJson(result, UserBean[].class);
        ArrayList<UserBean> users = new ArrayList();
        for (UserBean u : userarray) {
            users.add(u);
        }
        return users;
    }
/**
 * Returns single user
 * @param userid id of user
 * @return user
 */
    public static UserBean getUser(int userid) {
        String result = RestHelper.getStringFromURL("http://a.fredrikljung.com:8080/Twittbook/webresources/rest/user?userId=" + userid);
        Gson gson = new Gson();
        UserBean user = gson.fromJson(result, UserBean.class);
        return user;
    }

    /**
     * Submits edited data in user
     * @return successcode
     */
    public String submitEditedUser() {
        StoredUser user = (StoredUser) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user");
        RestHelper.updateDescription(user.getId(), description);
        return "success";
    }
    /**
     * Fills bean with data from the current sessions user
     */
    public void fillBean(){
        StoredUser user = (StoredUser) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user");
        UserBean downloaded = getUser(user.getId());
        this.description=downloaded.getDescription();
        this.username=downloaded.getUsername();
        this.id=downloaded.getId();
    }
    

}
