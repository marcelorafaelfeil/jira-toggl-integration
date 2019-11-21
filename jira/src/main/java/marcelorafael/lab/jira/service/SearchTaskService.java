package marcelorafael.lab.jira.service;

import marcelorafael.lab.httpcommon.HttpMethod;
import marcelorafael.lab.httpcommon.URL;
import marcelorafael.lab.jira.core.JiraService;
import marcelorafael.lab.jira.model.JiraIssue;

public class SearchTaskService extends JiraService {
	private final String URL = "/issue";

	public JiraIssue findIssue(String issueCode) {
		URL url = new URL(this.URL + "/" + issueCode);
		return this.run(url, HttpMethod.GET, JiraIssue.class);
	}
}
