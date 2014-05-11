package com.anoop.travelGuide;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.apache.commons.codec.binary.Base64;
import org.jsoup.Jsoup;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

@SuppressWarnings("serial")
public class TravelGuideServlet extends HttpServlet {
	String keyword = "";
	String yahooTravelUrl = "";
	String virtualTravelUrl = "";
	String tripAdvisorTravelUrl = "";
	final List<String> YTValues = new ArrayList<String>();
	final List<String> VTValues = new ArrayList<String>();
	final List<String> TAValues = new ArrayList<String>();
	final List<String> Places = new ArrayList<String>();

	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		resp.setContentType("text/plain");
		resp.getWriter().println("Hello, world");
		keyword = req.getParameter("keyword");
//		keyword = "points of interest " + keyword;
		keyword = "yahoo travel Search Attractions in " + keyword;
		System.out.println("[KEYWORD] :: " + keyword);
		String output = searchBing();
		try {
			YTValues.clear();
			VTValues.clear();
			TAValues.clear();
			Places.clear();
			getYQLResults(yahooTravelUrl);
			getVTResults(virtualTravelUrl);
			getTAResults(tripAdvisorTravelUrl);
			System.out.println(YTValues);
			System.out.println(YTValues.size());
			System.out.println(VTValues);
			System.out.println(VTValues.size());
			System.out.println(TAValues);
			System.out.println(TAValues.size());
			for (String x : VTValues){
				   if (!Places.contains(x))
				      Places.add(x);
				   else System.out.println("[Duplicates] "+x);
				}
			for (String x : YTValues){
				   if (!Places.contains(x))
				      Places.add(x);
				   else System.out.println("[Duplicates] "+x);
				}
			for (String x : TAValues){
				   if (!Places.contains(x))
				      Places.add(x);
				   else System.out.println("[Duplicates] "+x);
				}
			System.out.println(Places);
			System.out.println(Places.size());
		} catch (SAXException | ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (output != null) {
			resp.getWriter().println(output);
		} else {
//			resp.getWriter().println("Hello, world");
		}

		// HttpSession sess = req.getSession(true);
		// sess.setAttribute("yahooTravelUrl",yahooTravelUrl);
		// sess.setAttribute("virtualTravelUrl",virtualTravelUrl);

		// RequestDispatcher rd = req.getRequestDispatcher("search.jsp");
		// rd.forward(req, resp);
		
		req.setAttribute("Places" , Places);
//		resp.sendRedirect("search.jsp");
		
		RequestDispatcher dispatcher = req.getRequestDispatcher("search.jsp");
		dispatcher.forward(req,resp);

		keyword = "";
		yahooTravelUrl = "";
		virtualTravelUrl = "";
		tripAdvisorTravelUrl = "";

	}

	public String searchBing() {
		// TODO Auto-generated method stub
		// --------------------------------------Bing
		// search------------------------------
		String searchText = keyword;
		searchText = searchText.replaceAll(" ", "%20");
		String accountKey = "J5d1j9h3oeuXW/B/es3IPwL1/tSfXFn1bHOl1waw2q0";

		byte[] accountKeyBytes = Base64
				.encodeBase64((accountKey + ":" + accountKey).getBytes());
		String accountKeyEnc = new String(accountKeyBytes);
		URL url;
		try {
			url = new URL(
					"https://api.datamarket.azure.com/Data.ashx/Bing/Search/v1/Web?Query=%27"
							+ searchText + "%27&$top=50&$format=Atom");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Authorization", "Basic " + accountKeyEnc);
			// conn.setRequestProperty("Accept", "application/json");
			BufferedReader br = new BufferedReader(new InputStreamReader(
					(conn.getInputStream())));
			
			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(conn.getInputStream());
			// get the first element
			Element element = doc.getDocumentElement();
			
			System.out.println();

			// get all child nodes
			NodeList nodes = element.getChildNodes();

			NodeList nList = doc.getElementsByTagName("m:properties");

			for (int temp = 0; temp < nList.getLength(); temp++) {

				Node nNode = nList.item(temp);

				NodeList cList = doc.getElementsByTagName("content");

				Element eElement = (Element) nNode;

				String link = eElement.getElementsByTagName("d:Url").item(0)
						.getTextContent();

				if (link.contains("https://travel.yahoo.com/p-travelguide-")) {
					if (link.contains("_things_to_do-i")) {
						if (yahooTravelUrl == "") {
							yahooTravelUrl = link;
							System.out.println("yahooTravelUrl URL : "
									+ yahooTravelUrl);
						}
					}
				}else if (link.contains("http://www.virtualtourist.com/travel/")) {
					if (link.contains("Things_To_Do-")) {
						if (virtualTravelUrl == "") {
							virtualTravelUrl = link;
							System.out.println("virtualTravelUrlURL : "
									+ virtualTravelUrl);
						}
					}
				}else if (link.contains("http://www.tripadvisor.com/Attractions-")) {
					if (link.contains("-Activities-")) {
						if (tripAdvisorTravelUrl == "") {
							tripAdvisorTravelUrl = link;
							System.out.println("tripAdvisorTravelUrl : "
									+ tripAdvisorTravelUrl);
						}
					}
				}else{
					System.out.println("link : "
							+ link);
				}
			}

			StringBuilder sb = new StringBuilder();
			String output;
			System.out.println("Output from Server .... \n");
			char[] buffer = new char[4096];
			while ((output = br.readLine()) != null) {
				sb.append(output);
			}
			System.out.println(sb.toString());
			conn.disconnect();
			return sb.toString();
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;

	}

	public void getYQLResults(String url) throws SAXException,
			ParserConfigurationException {
		try {
			String href = "https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20html%20where%20url%3D%22"
					+ url
					+ "%22%20and%0A%20%20%20%20%20%20xpath%3D'%2F%2Fh3%5B%40class%3D%22My-0%20Py-0%20Fz-m%20Fw-b%20Ell%22%5D%2Fa%2Fspan'&diagnostics=true";
			URL myURL = new URL(href);

			System.err.println("[URL] :: " + href);
			URLConnection myURLConnection = myURL.openConnection();
			myURLConnection.connect();
			BufferedReader in = new BufferedReader(new InputStreamReader(
					myURLConnection.getInputStream()));
			String inputLine;
			StringBuilder sb = new StringBuilder();
			while ((inputLine = in.readLine()) != null) {
				System.out.println("[YQL] " + inputLine);
				sb.append(inputLine);
			}

			System.out.println("[YQL - last] " + sb.toString());
			final Pattern pattern = Pattern.compile("<span>(.+?)</span>");
			final Matcher matcher = pattern.matcher(sb.toString());
			while (matcher.find()) {
				YTValues.add(matcher.group(1).trim());
			}

			in.close();
		} catch (MalformedURLException e) {
			// new URL() failed
			// ...
		} catch (IOException e) {
			// openConnection() failed
			// ...
		}
	}
	
	public void getVTResults(String url) throws SAXException, ParserConfigurationException{
		try {
			String href = "https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20html%20where%20url%3D%22"
					+ url
					+ "%22%20and%0A%20%20%20%20%20%20xpath%3D'%2F%2Fdiv%5B%40class%3D%22tip-content%22%5D%2Fh4%2Fa'&diagnostics=true";
			
			URL myURL = new URL(href);
		    
		    System.err.println("[URL] :: "+href);
		    URLConnection myURLConnection = myURL.openConnection();
		    myURLConnection.connect();
			BufferedReader in = new BufferedReader(new InputStreamReader(
					myURLConnection.getInputStream()));
			String inputLine;
			StringBuilder sb = new StringBuilder();
			while ((inputLine = in.readLine()) != null){
				System.out.println("[VT] " + inputLine);
				sb.append(inputLine);
			}

			System.out.println("[VT - last] "+sb.toString());
			final Pattern pattern = Pattern.compile("<a(.+?)</a>");
			final Matcher matcher = pattern.matcher(sb.toString());
			while (matcher.find()) {
				final Pattern pattern1 = Pattern.compile("\">(.+?)$");
				final Matcher matcher1 = pattern1.matcher(matcher.group(1));
				while (matcher1.find()) {
					String string = matcher1.group(1);
					if(Character.isDigit(string.charAt(0))){
						String[] parts = string.split("\\.");
						String part2 = parts[1];
						VTValues.add(part2.trim());
					}
				}
			}
			
			in.close();
		} 
		catch (MalformedURLException e) { 
		    // new URL() failed
		    // ...
		} 
		catch (IOException e) {   
		    // openConnection() failed
		    // ...
		}
	}
	
	public void getTAResults(String url) throws SAXException, ParserConfigurationException{
		try {
			String href = "https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20html%20where%20url%3D%22"
					+ url
					+ "%22%20and%0A%20%20%20%20%20%20xpath%3D'%2F%2Fdiv%5B%40class%3D%22quality%20easyClear%22%5D%2Fa'&diagnostics=true";
			URL myURL = new URL(href);
		    
		    System.err.println("[URL] :: "+href);
		    URLConnection myURLConnection = myURL.openConnection();
		    myURLConnection.connect();
			BufferedReader in = new BufferedReader(new InputStreamReader(
					myURLConnection.getInputStream()));
			String inputLine;
			StringBuilder sb = new StringBuilder();
			while ((inputLine = in.readLine()) != null){
				System.out.println("[TA] " + inputLine);
				sb.append(inputLine);
			}

			System.out.println("[TA - last] "+sb.toString());
			final Pattern pattern = Pattern.compile("<a(.+?)</a>");
			final Matcher matcher = pattern.matcher(sb.toString());
			while (matcher.find()) {
				final Pattern pattern1 = Pattern.compile("\">(.+?)$");
				final Matcher matcher1 = pattern1.matcher(matcher.group(1));
				while (matcher1.find()) {
					String string = matcher1.group(1);
					TAValues.add(string.trim());
				}
			}
			
			in.close();
		} 
		catch (MalformedURLException e) { 
		    // new URL() failed
		    // ...
		} 
		catch (IOException e) {   
		    // openConnection() failed
		    // ...
		}
	}
}
