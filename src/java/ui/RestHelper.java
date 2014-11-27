/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;

/**
 *
 * @author Fredrik
 */
public class RestHelper {

    public static String getStringFromURL(String urlstring) {
        String result = null;
        try {
            URL url = new URL(urlstring);
            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));

            if ((result = in.readLine()) != null) {
                System.out.println(result);
            }
            in.close();
        } catch (MalformedURLException ex) {
            Logger.getLogger(UserBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(UserBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public static String register(String username, String password) {
        HttpClient client = HttpClientBuilder.create().build();

        HttpPost httppost = new HttpPost("addlinkhere");
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();

        builder.addTextBody("username", username);
        builder.addTextBody("password", password);
        httppost.setEntity((HttpEntity) builder.build());

        HttpResponse response = null;
        try {
            response = client.execute(httppost);
        } catch (IOException ex) {
            Logger.getLogger(RestHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
        HttpEntity resEntity = response.getEntity();
        return response.getEntity().toString();
    }

    public static String publishPost(int useridint, String message) {
        try {
            HttpClient client = HttpClientBuilder.create().build();
            
            HttpPost httppost = new HttpPost("http://a.fredrikljung.com:8080/Twittbook/webresources/rest/publishpost");
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            
            nameValuePairs.add(new BasicNameValuePair("userId", useridint + ""));
            nameValuePairs.add(new BasicNameValuePair("message", message));
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(nameValuePairs);
            System.out.println("Chosen conten type: " + entity.getContentType());
            httppost.setEntity(entity);
            
            HttpResponse response = null;
            try {
                response = client.execute(httppost);
            } catch (IOException ex) {
                System.out.println("IOEXCEPTION");
                System.out.println("Exception message: " + ex.getMessage());
            }
            HttpEntity resEntity = response.getEntity();
            return response.toString();
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(RestHelper.class.getName()).log(Level.SEVERE, null, ex);
            return "failure";
        }
    }

}
