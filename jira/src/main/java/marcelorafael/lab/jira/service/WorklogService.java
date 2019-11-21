package marcelorafael.lab.jira.service;

import marcelorafael.lab.httpcommon.HttpMethod;
import marcelorafael.lab.httpcommon.HttpParams;
import marcelorafael.lab.httpcommon.URL;
import marcelorafael.lab.jira.core.JiraService;
import marcelorafael.lab.jira.model.JiraIssue;
import marcelorafael.lab.jira.payload.WorklogPayload;

public class WorklogService extends JiraService {
	private final URL url = new URL("/issue/{issue}/worklog");

	public JiraIssue addWorklog(String issueCode, WorklogPayload worklogPayload) {
		this.url.append("issue", issueCode);
		HttpParams params = new HttpParams(worklogPayload);
		return this.run(this.url, HttpMethod.POST, params, JiraIssue.class);
	}
}
