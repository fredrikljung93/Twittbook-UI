/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import com.google.gson.Gson;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import session.RestHelper;
import session.StoredUser;

/**
 * Bean for register
 * @author Fredrik
 */
@ManagedBean(name = "RegisterBean")
@RequestScoped
public class RegisterBean {

    private String username;
    private String password;
    private String email;
    private String repeatedPassword;

    /**
     * Creates a new instance of RegisterBean
     */
    public RegisterBean() {
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRepeatedPassword() {
        return repeatedPassword;
    }

    public void setRepeatedPassword(String repeatedPassword) {
        this.repeatedPassword = repeatedPassword;
    }
 /**
  * Registers user
  * @return resultcode
  */
    public String Register() {

        if (!password.equals(repeatedPassword)) {
            return "unmatchedpasswords";
        }
        String result = RestHelper.registerUser(this);
        if (!result.equalsIgnoreCase("failure")) {
            return "success?faces-redirect=true";
        }
        return "failure?faces-redirect=true";

    }
    /**
     * Checks if username is valid regarding username length and uniqueness
     * @param params not used
     * @return resultcode
     */
    public String validateUsername(String params){
        if(username==null){
                  System.out.println("checkifusernamexists received null");  
                  return "";
        }
                System.out.println("checkifusernamexists received "+username);
        
        if(username.equals("")){
            return "";
        }
        if(username.length()<3){
            return "Username needs to be at least three characters long";
        }
        String result = RestHelper.getStringFromURL("http://a.fredrikljung.com:8080/Twittbook/webresources/rest/userbyname?username="+username);
        Gson gson = new Gson();
        StoredUser user = gson.fromJson(result, StoredUser.class);
        if(user==null){
            return "Username "+username+"  is available!";
        }
        else{
            return "Username "+username+"  is already in use!";
        }
    }

}
