package ch.sierre.hevs.iig;

import java.io.BufferedReader;	
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import org.json.JSONObject;
import org.json.XML;

public class ApiRequests {
	
	// Post the API request to CISMeF ecmt v3 server and return the results
	public static JSONObject getCISMeFData(String authorization, String inputString) {
		
		JSONObject xmlJSONObj = new JSONObject();
		URL urlIndic;
		try {
			String indicationStringEncoded = URLEncoder.encode(inputString, "UTF-8");
			
			String urlecmt = "https://ecmt.chu-rouen.fr/CISMeFecmtservice/REST/getAutomaticIndexingWithOptions/at=false&a=false&d=false&r=false&c=false&sn=false&p=false&e=&f=/" + indicationStringEncoded;
			
			
			urlIndic = new URL(urlecmt);
			URLConnection urlConnIndic = urlIndic.openConnection();
			
			urlConnIndic.setRequestProperty("Authorization", authorization);
			urlConnIndic.setDoOutput(true);
			urlConnIndic.setAllowUserInteraction(false);
			
			// Retrieving XML outputs in a single String format
			BufferedReader brIndic = new BufferedReader(new InputStreamReader(urlConnIndic.getInputStream()));
			StringBuilder sb = new StringBuilder();
			String line = brIndic.readLine();
			while (line != null) {
				sb.append(line).append("\n");
				line = brIndic.readLine();
			}
			
			String xml2String = sb.toString(); // Replace CTRL-Char
			
			// convert xml string into json
			xmlJSONObj = XML.toJSONObject(xml2String);
			
			brIndic.close();
			
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException ioe) {
			System.out.println("Caught an IOException: " + ioe.getMessage());
		}
		
		return xmlJSONObj;
	}
	
	public static JSONObject getHeToPData(String authorization, String inputString) {
		JSONObject xmlJSONObj = new JSONObject();
		URL urlIndic;
		try {
			String indicationStringEncoded = URLEncoder.encode(inputString, "UTF-8");
			String urlecmt = "https://www.hetop.eu/CISMeFhetopservice/REST/getConceptHierarchiesAscendants/" + indicationStringEncoded + "/fr";
			urlIndic = new URL(urlecmt);
			URLConnection urlConnIndic = urlIndic.openConnection();
			urlConnIndic.setRequestProperty("Authorization", authorization);
			urlConnIndic.setDoOutput(true);
			urlConnIndic.setAllowUserInteraction(false);
			// Retrieving XML outputs in a single String format
			BufferedReader brIndic = new BufferedReader(new InputStreamReader(urlConnIndic.getInputStream()));
			StringBuilder sb = new StringBuilder();
			String line = brIndic.readLine();
			while (line != null) {
				sb.append(line).append("\n");
				line = brIndic.readLine();
			}
			String xml2String = sb.toString(); // Replace CTRL-Char
			// convert xml string into json
			xmlJSONObj = XML.toJSONObject(xml2String);
			brIndic.close();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException ioe) {
			System.out.println("Caught an IOException: " + ioe.getMessage());
		}
		
//		System.out.println(xmlJSONObj);
		return xmlJSONObj;
	}
}