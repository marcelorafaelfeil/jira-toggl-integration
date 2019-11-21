package marcelorafael.lab.jiratogglintegration;

import marcelorafael.lab.httpcommon.HttpMethod;
import marcelorafael.lab.httpcommon.HttpParams;
import marcelorafael.lab.httpcommon.URL;
import marcelorafael.lab.jira.core.JiraConfiguration;
import marcelorafael.lab.jira.core.JiraCore;
import marcelorafael.lab.jira.model.JiraIssue;
import marcelorafael.lab.jira.payload.CommentPayload;
import marcelorafael.lab.jira.payload.WorklogPayload;
import marcelorafael.lab.jira.service.SearchTaskService;
import marcelorafael.lab.jira.service.WorklogService;
import marcelorafael.lab.jiratogglintegration.core.ConfigurationProperties;
import marcelorafael.lab.toggl.core.TogglConfiguration;
import marcelorafael.lab.toggl.core.TogglCore;
import marcelorafael.lab.toggl.model.TimeEntries;
import marcelorafael.lab.toggl.service.TogglEntriesService;

import java.time.*;
import java.util.List;

public class JiraToggleIntegrationStart {
	public static void main(String[] args) {
		TogglConfiguration.TOKEN = ConfigurationProperties.get("toggl.token");
		JiraConfiguration.USERNAME = ConfigurationProperties.get("jira.username");
		JiraConfiguration.PASSWORD = ConfigurationProperties.get("jira.password");

		TogglEntriesService togglEntriesServices = new TogglEntriesService();
		List<TimeEntries> timeEntries = togglEntriesServices.getTimeEntries(LocalDateTime.of(2019, Month.NOVEMBER, 20, 0, 0));

		/*timeEntries.forEach(t -> {
			int hours = t.getDuration()/3600;
			int minutes = (t.getDuration()%3600)/60;
			String time = hours + ":" + minutes;
			System.out.println("info: "+t.getDescription() + " : " + time);
		});*/

		SearchTaskService searchTaskService = new SearchTaskService();
		/*JiraIssue jiraIssue = searchTaskService.findIssue("UP-411");
		System.out.println(jiraIssue);*/

		CommentPayload commentPayload = new CommentPayload("Apontamento Toggl.");

		WorklogPayload worklogPayload = new WorklogPayload();
		worklogPayload.setTimeSpentSeconds((long)120);
		worklogPayload.setStarted(ZonedDateTime.of(2019, 11, 21, 18, 40, 0, 0, ZoneId.systemDefault()));
		worklogPayload.setComment("Apontamento Toggl.");

		WorklogService worklogService = new WorklogService();
		worklogService.addWorklog("UP-195", worklogPayload);
	}

	public static void togglTest() {
		URL url = new URL("/me/time_entries");
		TogglCore togglCore = new TogglCore(ConfigurationProperties.get("toggl.api_token"), url, HttpMethod.GET);
		togglCore.setParameters(new HttpParams().append("since", Instant.now().minusSeconds(500000).getEpochSecond()));
		togglCore.execute();
		TimeEntries[] data = togglCore.getResultAsObject(TimeEntries[].class);
	}

	public static void jiraTest() {
		URL url = new URL("/issue/UP-411");
		JiraCore jiraCore = new JiraCore(ConfigurationProperties.get("jira.username"), ConfigurationProperties.get("jira.password"), url, HttpMethod.GET);
		jiraCore.setParameters(new HttpParams().append("since", Instant.now().minusSeconds(500000).getEpochSecond()));
		jiraCore.execute();
		JiraIssue[] data = jiraCore.getResultAsObject(JiraIssue[].class);
	}
}
