package ch.sierre.hevs.iig;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class CISMeFResultProcessor {
	
	public static ArrayList<String> countit = new ArrayList<String>();
	
	public static String jsonObject = "public class org.json.JSONObject"; 
	public static String jsonArray = "public class org.json.JSONArray";
	public static String jsonString = "public class java.lang.String";
	
	public static List<String> processCISMeFJsons(JSONObject cismefResult) {
		
		List<String> allTermsReport = new ArrayList<String>();
		
		JSONObject sentences = cismefResult.getJSONObject("cis:sentences");

		if (sentences.get("cis:sentence").getClass().toGenericString().equals(jsonObject)) {
			
			JSONObject singSent = sentences.getJSONObject("cis:sentence");
			
			// Get the indexations from single sentence (there can 1 or more indexations)
			Object indexations = singSent.getJSONObject("cis:indexations").get("cis:indexation");
			
			if (indexations.getClass().toGenericString().equals(jsonObject)) {

				JSONObject indiIndexations = singSent.getJSONObject("cis:indexations").getJSONObject("cis:indexation");
				
				String matchedTerm = indiIndexations.get("matchterms").toString();
				String cisMefId = indiIndexations.get("idcismef").toString();
				String idType = indiIndexations.get("typeid").toString();
				allTermsReport.add(matchedTerm + ":" + cisMefId + ":" + idType);
				
			}
			else if (indexations.getClass().toGenericString().equals(jsonArray)) {
				
				JSONArray multIndexations = singSent.getJSONObject("cis:indexations").getJSONArray("cis:indexation");
				for (int i = 0; i < multIndexations.length(); i++) {
					
					String matchedTerm = multIndexations.getJSONObject(i).get("matchterms").toString();
					String cisMefId = multIndexations.getJSONObject(i).get("idcismef").toString();
					String idType = multIndexations.getJSONObject(i).get("typeid").toString();
					allTermsReport.add(matchedTerm + ":" + cisMefId + ":" + idType);
					
				}
				
			}
			
		}
		else if (sentences.get("cis:sentence").getClass().toGenericString().equals(jsonArray)) {
			
			JSONArray MultSents = sentences.getJSONArray("cis:sentence");
			
			// Get the Indexations from multiple sentence (there can 1 or more indexations)
			for (int i = 0; i < MultSents.length(); i++) {
				
				if (MultSents.getJSONObject(i).get("cis:indexations").getClass().toGenericString().contains("String")) {
					// TODO : Ignore these because it is a blank string
				}
				else {
					
					
					Object indexations = MultSents.getJSONObject(i).getJSONObject("cis:indexations").get("cis:indexation");
				
					if (indexations.getClass().toGenericString().equals(jsonObject)) {

						JSONObject indiIndexations = MultSents.getJSONObject(i).getJSONObject("cis:indexations").getJSONObject("cis:indexation");
						
						String matchedTerm = indiIndexations.get("matchterms").toString();
						String cisMefId = indiIndexations.get("idcismef").toString();
						String idType = indiIndexations.get("typeid").toString();
						allTermsReport.add(matchedTerm + ":" + cisMefId + ":" + idType);
						
					}
					else if (indexations.getClass().toGenericString().equals(jsonArray)) {
						
						JSONArray multIndexations = MultSents.getJSONObject(i).getJSONObject("cis:indexations").getJSONArray("cis:indexation");
						for (int j = 0; j < multIndexations.length(); j++) {
							
							String matchedTerm = multIndexations.getJSONObject(j).get("matchterms").toString();
							String cisMefId = multIndexations.getJSONObject(j).get("idcismef").toString();
							String idType = multIndexations.getJSONObject(j).get("typeid").toString();
							allTermsReport.add(matchedTerm + ":" + cisMefId + ":" + idType);
							
						}
					}

				}
			}
		}

		return allTermsReport;
		
	}
}