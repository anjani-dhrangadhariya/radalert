package ch.sierre.hevs.iig;

import java.io.File;
import java.util.List;

import org.json.JSONObject;

public class ReportCompareRoot {

	public static void main(String[] args) {		
		// Get the API authorizations
		String authorization = ApiAuthorizations.authorize();
		
		// Pass the directory containing the reports
//		File radiologyReports = new File("/home/anjani/super_new_Workspace/rad-compare/src/main/resources/testInputFolder");
		File radiologyReports = new File("/home/anjani/super_new_Workspace/rad-compare/src/main/resources/radiology_reports");
		
		File[] individualReports = radiologyReports.listFiles();

		for (int indReport = 0; indReport < individualReports.length; indReport++) {

			if (individualReports[indReport] != null) {
				
				String filePath = "/home/anjani/super_new_Workspace/rad-compare/src/main/resources/minMaxMeSHPathFiles/";
				String filePath2 = "/home/anjani/super_new_Workspace/rad-compare/src/main/resources/allTermsFiles/";
				
			    int index = individualReports[indReport].toString().lastIndexOf('/');
			    String completefileName = filePath + individualReports[indReport].toString().substring(index + 1, individualReports[indReport].toString().length()).replace("doc", "txt");	    		
			    String completefileNameAllTerms = filePath2 + individualReports[indReport].toString().substring(index + 1, individualReports[indReport].toString().length()).replace("doc", "txt");
			    System.out.println(completefileName);
			    
				String indication = LoadRadiologyReports.getIndication(individualReports[indReport]);
				String conclusion = LoadRadiologyReports.getConclusion(individualReports[indReport]);
				
				// Process the strings to be correct for posting to API				
				String processedIndication = StringProcessing.processString(indication);
//				System.out.println(processedIndication);
				String processedConclusion = StringProcessing.processString(conclusion);

				
				if (!processedIndication.contains("empty") && !processedConclusion.contains("empty")) {
					
					JSONObject indTermsJSON = ApiRequests.getCISMeFData(authorization, processedIndication);
					JSONObject concluTermsJSON = ApiRequests.getCISMeFData(authorization, processedConclusion);
					
					// Treat the JSON to get all the term IDs
					List<String> allTermsReport_Indication = CISMeFResultProcessor.processCISMeFJsons(indTermsJSON);
					List<String> allTermsReport_Conclusion = CISMeFResultProcessor.processCISMeFJsons(concluTermsJSON);
				
					// Get MeSH paths for MeSH descriptors using HeToP service
					
					FileWriters.writeMeSHPaths(completefileName, "---------- Indication ----------\n\n");
					allTermsReport_Indication.forEach((temp) -> {
					if (temp.contains("MESH_DESCRIPTEUR")) {
						JSONObject indicationMeSHpaths = ApiRequests.getHeToPData(authorization, temp.split(":")[1]);
						String randomTemp = CompareMeSH.processMeSHpaths(indicationMeSHpaths);
						FileWriters.writeMeSHPaths(completefileName, randomTemp);
						}
					
					// Write other terms to the file XXX
//					System.out.println(temp);
					FileWriters.writeMeSHPaths(completefileNameAllTerms, temp);
					
					});

					FileWriters.writeMeSHPaths(completefileName, "\n---------- Conclusion ----------\n\n");
					allTermsReport_Conclusion.forEach((temp) -> {
					if (temp.contains("MESH_DESCRIPTEUR")) {
						JSONObject conclusionMeSHpaths = ApiRequests.getHeToPData(authorization, temp.split(":")[1]);
						String randomTemp = CompareMeSH.processMeSHpaths(conclusionMeSHpaths);
						FileWriters.writeMeSHPaths(completefileName, randomTemp);
						}
					
					// Write other terms to the file XXX
//					FileWriters.writeMeSHPaths(completefileNameAllTerms, temp);
					});
					
				}
			}
		}
	}
}