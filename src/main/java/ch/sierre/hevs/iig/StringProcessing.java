package ch.sierre.hevs.iig;

public class StringProcessing {
	
	public static String processString(String inputString) {
		
		String tempInd = inputString.replaceAll("\\s+", " ").trim();
		String tempInd2 = tempInd.replace("/", " ");
		String tempInd3 = tempInd2.replace("?", "");
		
		return tempInd3;
	}

}