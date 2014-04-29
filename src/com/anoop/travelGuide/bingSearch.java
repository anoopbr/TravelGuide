package com.anoop.travelGuide;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.codec.binary.Base64;
import org.jsoup.Jsoup;
import org.w3c.dom.Document;
public class bingSearch {
    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        //--------------------------------------Bing search------------------------------
        String searchText = "yahoo travel Search Attractions in Seattle, WA, United States things_to_do-i";
        searchText = searchText.replaceAll(" ", "%20");
        String accountKey="J5d1j9h3oeuXW/B/es3IPwL1/tSfXFn1bHOl1waw2q0";
      
        byte[] accountKeyBytes = Base64.encodeBase64((accountKey + ":" + accountKey).getBytes());
        String accountKeyEnc = new String(accountKeyBytes);
        URL url;
        try {
            url = new URL(
                    "https://api.datamarket.azure.com/Data.ashx/Bing/Search/v1/Web?Query=%27" + searchText + "%27&$top=50&$format=Atom");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Authorization", "Basic " + accountKeyEnc);
        //conn.setRequestProperty("Accept", "application/json");
        BufferedReader br = new BufferedReader(new InputStreamReader(
                (conn.getInputStream())));
        StringBuilder sb = new StringBuilder();
        String output;
        System.out.println("Output from Server .... \n");
        char[] buffer = new char[4096];
        while ((output = br.readLine()) != null) {
            sb.append(output);
            System.out.println(output);
                //text.append(link + "\n\n\n");//Will print the google search links
            //}    
        }
       
        conn.disconnect();
       
        int find = sb.indexOf("<d:Url");
        int total = find + 1000;
        System.out.println("Find index: " + find);
        System.out.println("Total index: " + total);
        sb.getChars(find+35, total, buffer, 0);
        String str = new String(buffer);
       
        int find2 = str.indexOf("</d:Url>");
        int total2 = find2 + 400;
        System.out.println("Find index: " + find);
        System.out.println("Total index: " + total);
        char[] buffer2 = new char[1024];
        str.getChars(0, find2, buffer2 , 0);
        String str2 = new String(buffer2);
        str2 = Jsoup.parse(str2).text();   
        System.out.println(str2);

        
        
        } catch (MalformedURLException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
       
       
    }
   
}