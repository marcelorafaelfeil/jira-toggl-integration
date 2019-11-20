package marcelorafael.lab.jiratogglintegration;

import marcelorafael.lab.httpcommon.HttpMethod;
import marcelorafael.lab.httpcommon.HttpParams;
import marcelorafael.lab.httpcommon.URL;
import marcelorafael.lab.jira.core.JiraCore;
import marcelorafael.lab.jira.model.JiraIssue;
import marcelorafael.lab.jiratogglintegration.core.ConfigurationProperties;
import marcelorafael.lab.toggl.core.TogglCore;
import marcelorafael.lab.toggl.model.TogglEntries;

import java.time.Instant;

public class JiraToggleIntegrationStart {
	public static void main(String[] args) {
		JiraToggleIntegrationStart.jiraTest();
	}

	public static void togglTest() {
		URL url = new URL("/me/time_entries");
		TogglCore togglCore = new TogglCore(ConfigurationProperties.get("toggl.api_token"), url, HttpMethod.GET);
		togglCore.setParameters(new HttpParams().append("since", Instant.now().minusSeconds(500000).getEpochSecond()));
		togglCore.execute();
		TogglEntries[] data = togglCore.getResultAsObject(TogglEntries[].class);
	}

	public static void jiraTest() {
		URL url = new URL("/issue/UP-411");
		JiraCore jiraCore = new JiraCore(ConfigurationProperties.get("jira.username"), ConfigurationProperties.get("jira.password"), url, HttpMethod.GET);
		jiraCore.setParameters(new HttpParams().append("since", Instant.now().minusSeconds(500000).getEpochSecond()));
		jiraCore.execute();
		JiraIssue[] data = jiraCore.getResultAsObject(JiraIssue[].class);
	}
}
