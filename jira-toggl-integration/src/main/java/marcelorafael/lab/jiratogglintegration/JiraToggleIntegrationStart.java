package marcelorafael.lab.jiratogglintegration;

import marcelorafael.lab.httpcommon.HttpMethod;
import marcelorafael.lab.httpcommon.HttpParams;
import marcelorafael.lab.httpcommon.URL;
import marcelorafael.lab.jira.core.JiraConfiguration;
import marcelorafael.lab.jira.core.JiraCore;
import marcelorafael.lab.jira.model.JiraIssue;
import marcelorafael.lab.jiratogglintegration.core.ConfigurationProperties;
import marcelorafael.lab.jiratogglintegration.service.IntegrationService;
import marcelorafael.lab.toggl.core.TogglConfiguration;
import marcelorafael.lab.toggl.core.TogglCore;
import marcelorafael.lab.toggl.model.TimeEntries;

import java.time.Instant;

public class JiraToggleIntegrationStart {
	public static void main(String[] args) {
		TogglConfiguration.TOKEN = ConfigurationProperties.get("toggl.token");
		JiraConfiguration.USERNAME = ConfigurationProperties.get("jira.username");
		JiraConfiguration.PASSWORD = ConfigurationProperties.get("jira.password");

		IntegrationService integrationService = new IntegrationService();
		integrationService.doIntegration();
	}
}
