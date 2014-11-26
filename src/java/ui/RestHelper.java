/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Fredrik
 */
public class RestHelper {
    
    public static String getStringFromURL(String urlstring){
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
    
}
