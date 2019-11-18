package marcelorafael.lab;

import marcelorafael.lab.core.TogglCore;
import marcelorafael.lab.core.http.HttpMethod;

public class JiraToggleIntegrationStart {
	public static void main(String[] args) {
		TogglCore togglCore = new TogglCore();
		togglCore.processRequest("e8a6111980097c538ef06b2b1c089973", "/me/time_entries", HttpMethod.GET);
		System.out.println("Data: ");
		System.out.println(togglCore.getResultAsString());
	}
}
