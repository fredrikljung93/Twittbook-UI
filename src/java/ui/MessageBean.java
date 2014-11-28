/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author Fredrik
 */
@ManagedBean(name = "MessageBean")
@RequestScoped
public class MessageBean {

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSender() {
        return sender;
    }

    public void setSender(int sender) {
        this.sender = sender;
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
    
    private int id;
    private int sender;
    private List<String> receivers;
    private String message;

    public List<String> getReceiver() {
        return receivers;
    }

    public void setReceiver(List<String> receivers) {
        this.receivers = receivers;
    }
    private String date;

    /**
     * Creates a new instance of MessageBean
     */
    public MessageBean() {
    }

    public String CreateMessage() {
        return "success";
    }
    
    public List<MessageBean> getInbox(){
        StoredUser receiver = (StoredUser) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user");
        String result=RestHelper.getStringFromURL("http://a.fredrikljung.com:8080/Twittbook/webresources/rest/inbox?userId="+receiver.getId());
        
        Gson gson = new Gson();
        MessageBean[] beanarray = gson.fromJson(result, MessageBean[].class);
        List<MessageBean> beanlist = new ArrayList<>();
        
        for(MessageBean bean: beanarray){
            beanlist.add(bean);
        }
        
        
        return beanlist;
    }

}
