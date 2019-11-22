package marcelorafael.lab.jiratogglintegration.utils;

public class TogglUtils {
	public static String getIssueCode(String issueDescription) {
		return issueDescription.split(" ")[0];
	}
}
