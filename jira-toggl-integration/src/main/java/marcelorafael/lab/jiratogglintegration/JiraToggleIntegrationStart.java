package marcelorafael.lab.jiratogglintegration;

import marcelorafael.lab.jira.core.JiraConfiguration;
import marcelorafael.lab.jiratogglintegration.core.ConfigurationProperties;
import marcelorafael.lab.jiratogglintegration.service.IntegrationService;
import marcelorafael.lab.toggl.core.TogglConfiguration;

public class JiraToggleIntegrationStart {
	public static void main(String[] args) {
		TogglConfiguration.TOKEN = ConfigurationProperties.get("toggl.token");
		JiraConfiguration.USERNAME = ConfigurationProperties.get("jira.username");
		JiraConfiguration.PASSWORD = ConfigurationProperties.get("jira.password");

		IntegrationService integrationService = new IntegrationService();
		integrationService.executeIntegration();
	}
}
