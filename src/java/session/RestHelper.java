
package session;

import beans.UserBean;
import beans.RegisterBean;
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
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;

/**Class to communicate with RESTful web services.*/
public class RestHelper {

    /**@param urlstring
     @return HTTP-response showing if request to web service was successful.*/
    public static String getStringFromURL(String urlstring) {
        String result = null;
        try {
            URL url = new URL(urlstring);
            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));

            result = in.readLine();
            in.close();
        } catch (MalformedURLException ex) {
            Logger.getLogger(UserBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(UserBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    /**@param useridint PK of User in DB.
     @param message message to be posted.
    @return String , response string after web service is requested.*/
    public static String publishPost(int useridint, String message) {
        try {
            HttpClient client = HttpClientBuilder.create().build();

            HttpPost httppost = new HttpPost("http://a.fredrikljung.com:8080/Twittbook/webresources/rest/publishpost");
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

            nameValuePairs.add(new BasicNameValuePair("userId", useridint + ""));
            nameValuePairs.add(new BasicNameValuePair("message", message));
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(nameValuePairs, "UTF-8");
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

    /**@param user helper class UserBean representing a newly created User.
     @return HTTP-response showing if web service call was successful.*/
    public static String registerUser(RegisterBean user) {
        try {
            HttpClient client = HttpClientBuilder.create().build();

            HttpPost httppost = new HttpPost("http://a.fredrikljung.com:8080/Twittbook/webresources/rest/register");
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

            nameValuePairs.add(new BasicNameValuePair("username", user.getUsername() + ""));
            nameValuePairs.add(new BasicNameValuePair("password", user.getPassword()));
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(nameValuePairs, "UTF-8");
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

    /**@param sender PK in DB of User.
     @param receiver PK in DB of User.
     @param message message being sent.
     @param subject title of the message being sent.
     @return HTTP-response showing if web service call was successful.*/
    public static String sendMessage(int sender, int receiver, String message, String subject) {
        try {
            HttpClient client = HttpClientBuilder.create().build();

            HttpPost httppost = new HttpPost("http://a.fredrikljung.com:8080/Twittbook/webresources/rest/sendpm");
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

            nameValuePairs.add(new BasicNameValuePair("receiver", receiver + ""));
            nameValuePairs.add(new BasicNameValuePair("message", message));
            nameValuePairs.add(new BasicNameValuePair("subject", subject));
            nameValuePairs.add(new BasicNameValuePair("sender", sender + ""));
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(nameValuePairs,"UTF-8");
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

    /**@param user PK in DB of USER.
     @param description
     @return HTTP-response showing if web service request was successful.
     Method is used to update the descruption in DB of the specific User.*/
    public static String updateDescription(int user, String description) {
        try {
            HttpClient client = HttpClientBuilder.create().build();

            HttpPost httppost = new HttpPost("http://a.fredrikljung.com:8080/Twittbook/webresources/rest/changedescription");
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

            nameValuePairs.add(new BasicNameValuePair("userId", user + ""));
            nameValuePairs.add(new BasicNameValuePair("description", description));
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(nameValuePairs,"UTF-8");
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
