package ch.sierre.hevs.iig;

import java.io.*;				
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;

public class LoadRadiologyReports {
	
	public static String getIndication(File indReports) {
		WordExtractor extractor = null;
		
		FileInputStream fis;
		try {
			fis = new FileInputStream(indReports.getAbsolutePath());
			HWPFDocument document = new HWPFDocument(fis);
			extractor = new WordExtractor(document);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String[] fileData = extractor.getParagraphText();
		List<String> wordList = Arrays.asList(fileData);

		List<String> trimmedStrings = wordList.stream().map(String::trim).collect(Collectors.toList());

		String startValue = "Indications";
		String stopValue = "Description";
		int indicatorStartIndex = trimmedStrings.indexOf(startValue);
		int indicatorStopIndex = trimmedStrings.indexOf(stopValue);

		StringBuilder indications = new StringBuilder();

		
		for (int i = indicatorStartIndex-3; i < indicatorStopIndex; i++) {
			if (trimmedStrings.get(i).toString().matches(".*\\w.*")) {
				
				String appendindication = null;
				if (i == indicatorStartIndex-3) {
					appendindication = trimmedStrings.get(i).toString().replaceAll("(\\s\\s\\sdu\\s.*)", "") + " ";
				}
				else {
					appendindication = trimmedStrings.get(i).toString() + " ";
				}
				
				indications.append(appendindication);
			}
		}

		if(!indications.toString().isEmpty()) {
			return indications.toString();
		}
		else {
			return "empty";
		}
	}
	
	public static String getConclusion(File indReports) {
		
		WordExtractor extractor = null;
		
		FileInputStream fis;
		try {
			fis = new FileInputStream(indReports.getAbsolutePath());
			HWPFDocument document = new HWPFDocument(fis);
			extractor = new WordExtractor(document);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String[] fileData = extractor.getParagraphText();
		List<String> wordList = Arrays.asList(fileData);

		List<String> trimmedStrings = wordList.stream().map(String::trim).collect(Collectors.toList());
		
		String startValue = "Conclusion";
		String alternativeStartValue = "Description et Conclusion";
		String stopValue = "Avec nos remerciements et nos meilleures salutations.";
		int conclusionStartIndex = trimmedStrings.indexOf(startValue);
		int conclusionStopIndex = trimmedStrings.indexOf(stopValue);
		int alternativeConclusionStartIndex = trimmedStrings.indexOf(alternativeStartValue);
		
		StringBuilder conclusions = new StringBuilder();
		
		if (trimmedStrings.contains("Conclusion")) {
			
			for (int i = conclusionStartIndex + 1; i < conclusionStopIndex; i++) {
				
				if (trimmedStrings.get(i).toString().matches(".*\\w.*")) {
					conclusions.append(trimmedStrings.get(i).toString());
					if (i != conclusionStopIndex - 1) {
						conclusions.append(" ");
					}
				}
				
			}
			
		}
		else if (trimmedStrings.contains("Description et Conclusion")) {
			
			for (int i = alternativeConclusionStartIndex + 1; i < conclusionStopIndex; i++) {
				
				if (trimmedStrings.get(i).toString().matches(".*\\w.*")) {
					conclusions.append(trimmedStrings.get(i).toString());
					if (i != conclusionStopIndex - 1) {
						conclusions.append(" ");
					}
				}
				
			}
			
		}
		
		if(!conclusions.toString().isEmpty()) {
			return conclusions.toString();
		}
		else {
			return "empty";
		}		
	}
}