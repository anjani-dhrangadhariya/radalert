package ch.sierre.hevs.iig;

import java.util.Base64;

public class ApiAuthorizations {
	
	// Authorizations for CISMeF ecmt v3 and HeToP API's
	public static String authorize() {
		
		// Encoding the user name & password for authentication
		byte[] encodedBytes = Base64.getEncoder().encode("hevs:5ui98ae".getBytes());
		
		// Creating the authorization field
		String auth = "Basic " + new String(encodedBytes);
		
		return auth;
	}

}