package ch.sierre.hevs.iig;

import java.io.BufferedReader;	
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

public class LoadBaseData {
	
	public static Multimap<String, String> loadMeSHTreeDict() {
		
		Multimap<String, String> meshTerms2TreeCodes = ArrayListMultimap.create();

		try {
			// Open the file
			FileInputStream fstream = new FileInputStream("/home/anjani/super_new_Workspace/RadtermsComparativeAnalysis/src/main/resources/meshDict.txt");
			BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

			String strLine;

			//Read File Line By Line
			while ((strLine = br.readLine()) != null)   {

			  String[] temp = strLine.split(" = ");
			  String[] meshTreecodes = temp[1].split("\\|");
		  
			  for (int i=0; i<meshTreecodes.length; i++) {
				  meshTerms2TreeCodes.put(meshTreecodes[i], temp[0]);
			  }
			}

			//Close the input stream
			fstream.close();
			
		} catch (IOException ioe) {
			System.out.println("Caught an IOException: " + ioe.getMessage());
		}		

		return meshTerms2TreeCodes;
	}
}
