/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.Collections;
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
        String year = date.substring(0, 4);
        String month = date.substring(5, 7);
        String day= date.substring(8, 10);
        String hour= date.substring(11, 13);
        String minute= date.substring(14, 16);
        return year+"-"+month+"-"+day+" "+hour+":"+minute;
    }

    public void setDate(String date) {
        this.date = date;
    }

    private int id;
    private int sender;
    private List<String> receivers;

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
    private String subject;
    private String message;
    private int receiver;

    public int getReceiver() {
        return receiver;
    }

    public void setReceiver(int receiver) {
        this.receiver = receiver;
    }

    public List<String> getReceivers() {
        return receivers;
    }

    public void setReceivers(List<String> receivers) {
        this.receivers = receivers;
    }
    private String date;

    /**
     * Creates a new instance of MessageBean
     */
    public MessageBean() {
    }

    public String sendMessage() {
        StoredUser sender = (StoredUser) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user");
        String result;
        for (String receiver : receivers) {
            result = RestHelper.sendMessage(sender.getId(), Integer.parseInt(receiver), this.message);
        }
        return "success";
    }

    public List<MessageBean> getInbox() {
        StoredUser receiver = (StoredUser) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user");
        String result = RestHelper.getStringFromURL("http://a.fredrikljung.com:8080/Twittbook/webresources/rest/inbox?userId=" + receiver.getId());

        Gson gson = new Gson();
        MessageBean[] beanarray = gson.fromJson(result, MessageBean[].class);
        List<MessageBean> beanlist = new ArrayList<>();

        for (MessageBean bean : beanarray) {
            beanlist.add(bean);
        }
        Collections.reverse(beanlist);
        return beanlist;
    }

    public List<MessageBean> getOutbox() {
        StoredUser sender = (StoredUser) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user");
        String result = RestHelper.getStringFromURL("http://a.fredrikljung.com:8080/Twittbook/webresources/rest/outbox?userId=" + sender.getId());

        Gson gson = new Gson();
        MessageBean[] beanarray = gson.fromJson(result, MessageBean[].class);
        List<MessageBean> beanlist = new ArrayList<>();

        for (MessageBean bean : beanarray) {
            beanlist.add(bean);
        }
        Collections.reverse(beanlist);
        return beanlist;
    }

}
