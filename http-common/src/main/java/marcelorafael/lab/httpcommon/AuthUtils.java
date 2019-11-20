package marcelorafael.lab.httpcommon;

import java.util.Base64;

public class AuthUtils {
	public static String generateBasicAuthToken(String username, String password) {
		String auth = username + ":" + password;
		try {
			String authStringEncoded = Base64.getEncoder().encodeToString(auth.getBytes("ISO-8859-1"));
			return "Basic " + authStringEncoded;
		} catch (Exception e) {
			return null;
		}
	}
}
