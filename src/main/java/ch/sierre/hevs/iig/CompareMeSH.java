package ch.sierre.hevs.iig;

import java.util.ArrayList;	
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.google.common.collect.Multimap;

public class CompareMeSH {
	
	// A dictionary that maps MeSH tree codes to the MeSH terms
	public static Multimap<String, String> meshTerms2TreeCodes;
	
	public static String processMeSHpaths(JSONObject meSHpath) {
		
		// Load the dictionary (MeSH tree code : MeSH term) from the file
		meshTerms2TreeCodes = LoadBaseData.loadMeSHTreeDict();
		
		JSONArray hierarchies = meSHpath.getJSONObject("cis:dboResp").getJSONObject("cis:dbo").getJSONObject("cis:hies").getJSONArray("cis:hie");
		
		List<String> keyList =  new ArrayList<String>();
		
		for (int i = 0; i < hierarchies.length(); i++) {
			if (hierarchies.getJSONObject(i).has("cis:tree")) {
				String key = hierarchies.getJSONObject(i).get("cis:tree").toString();
				keyList.add(key);
			}
		}
		
		String maxKey = Collections.max(keyList, Comparator.comparing(s -> s.length()));
		String minKey = Collections.min(keyList, Comparator.comparing(s -> s.length()));
		String temp = maxKey + " = " + meshTerms2TreeCodes.get(maxKey) + " , " + minKey + " = " + meshTerms2TreeCodes.get(minKey);
		
		return temp;
	}
}
