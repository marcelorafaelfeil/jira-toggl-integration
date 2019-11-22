package marcelorafael.lab.jira.service;

import marcelorafael.lab.httpcommon.HttpMethod;
import marcelorafael.lab.httpcommon.HttpParams;
import marcelorafael.lab.httpcommon.URL;
import marcelorafael.lab.jira.core.JiraService;
import marcelorafael.lab.jira.model.JiraIssue;
import marcelorafael.lab.jira.model.ResponseWorklog;
import marcelorafael.lab.jira.payload.WorklogPayload;

import java.util.List;

public class WorklogService extends JiraService {
	private final String uri = "/issue/{issue}/worklog";

	public JiraIssue addWorklog(String issueCode, WorklogPayload worklogPayload) {
		URL url = new URL(uri);
		url.append("issue", issueCode);
		HttpParams params = new HttpParams(worklogPayload);
		return this.run(url, HttpMethod.POST, params, JiraIssue.class);
	}

	public ResponseWorklog getAllWorklogsOfIssue(String issueCode) {
		URL url = new URL(uri);
		url.append("issue", issueCode);
		return this.run(url, HttpMethod.GET, ResponseWorklog.class);
	}
}
