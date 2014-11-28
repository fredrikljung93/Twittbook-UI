/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author Fredrik
 */
@ManagedBean(name = "PostBean")
@RequestScoped
public class PostBean {

    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getUser() {
        return user;
    }

    public void setUser(int user) {
        this.user = user;
    }
    private String message;
    private String date;
    private int user;

    /**
     * Creates a new instance of PostBean
     */
    public PostBean() {
    }

    public String createPost(String messageparameter) {
        StoredUser user = (StoredUser) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user");
        if(message==null){
            System.out.println("Create post received null");
            return "";
        }
         System.out.println("Create post received "+message);
        if(message.equals("")){
            return "";
        }
        String result = RestHelper.publishPost(user.getId(), message);
        System.out.println("CreatePost result: "+result);
        return "success";
    }

    public static List<PostBean> getPostsFromUser(int userid) {
        
        String result = RestHelper.getStringFromURL("http://a.fredrikljung.com:8080/Twittbook/webresources/rest/feed?userId="+userid);
        System.out.println(result);
        Gson gson=  new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS").create();
        PostBean[] postarray = gson.fromJson(result, PostBean[].class);
        ArrayList<PostBean> list = new ArrayList<>();
        for(PostBean p:postarray){
            list.add(p);
        }
        Collections.reverse(list);
        return list;
    }

}
